package wang.wincent.winapp.base.controller;


import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.wincent.winapp.base.annotation.SysLog;
import wang.wincent.winapp.base.entity.SysMenuEntity;
import wang.wincent.winapp.base.service.ShiroService;
import wang.wincent.winapp.base.service.SysMenuService;
import wang.wincent.winapp.common.utils.Constants;
import wang.wincent.winapp.common.utils.Result;
import wang.wincent.winapp.common.utils.WinAppException;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 系统菜单
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private ShiroService shiroService;

	/**
	 * 导航菜单
	 */
	@RequestMapping("/nav")
	public Result nav(){
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
		Set<String> permissions = shiroService.getUserPermissions(getUserId());
		return Result.ok().put("menuList", menuList).put("permissions", permissions);
	}
	
	/**
	 * 所有菜单列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public List<SysMenuEntity> list(){
		List<SysMenuEntity> menuList = sysMenuService.queryList(new HashMap<>());

		return menuList;
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public Result select(){
		//查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();
		
		//添加顶级菜单
		SysMenuEntity root = new SysMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		
		return Result.ok().put("menuList", menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	public Result info(@PathVariable("menuId") Long menuId){
		SysMenuEntity menu = sysMenuService.queryObject(menuId);
		return Result.ok().put("menu", menu);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存菜单")
	@RequestMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public Result save(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);
		
		sysMenuService.save(menu);
		
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改菜单")
	@RequestMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public Result update(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);
				
		sysMenuService.update(menu);
		
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除菜单")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:menu:delete")
	public Result delete(long menuId){
		if(menuId <= 31){
			return Result.error("系统菜单，不能删除");
		}

		//判断是否有子菜单或按钮
		List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return Result.error("请先删除子菜单或按钮");
		}

		sysMenuService.deleteBatch(new Long[]{menuId});
		
		return Result.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new WinAppException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new WinAppException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == Constants.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new WinAppException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = Constants.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenuEntity parentMenu = sysMenuService.queryObject(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == Constants.MenuType.CATALOG.getValue() ||
				menu.getType() == Constants.MenuType.MENU.getValue()){
			if(parentType != Constants.MenuType.CATALOG.getValue()){
				throw new WinAppException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == Constants.MenuType.BUTTON.getValue()){
			if(parentType != Constants.MenuType.MENU.getValue()){
				throw new WinAppException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
