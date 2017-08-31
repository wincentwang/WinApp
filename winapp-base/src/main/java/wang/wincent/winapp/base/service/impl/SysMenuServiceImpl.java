package wang.wincent.winapp.base.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.wincent.winapp.base.entity.SysMenuEntity;
import wang.wincent.winapp.base.mapper.SysMenuMapper;
import wang.wincent.winapp.base.service.SysMenuService;
import wang.wincent.winapp.base.service.SysUserService;
import wang.wincent.winapp.common.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for(SysMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId) {
		return sysMenuMapper.queryListParentId(parentId);
	}

	@Override
	public List<SysMenuEntity> queryNotButtonList() {
		return sysMenuMapper.queryNotButtonList();
	}

	@Override
	public List<SysMenuEntity> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constants.SUPER_ADMIN){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}
	
	@Override
	public SysMenuEntity queryObject(Long menuId) {
		return sysMenuMapper.queryObject(menuId);
	}

	@Override
	public List<SysMenuEntity> queryList(Map<String, Object> map) {
		return sysMenuMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysMenuMapper.queryTotal(map);
	}

	@Override
	public void save(SysMenuEntity menu) {
		sysMenuMapper.save(menu);
	}

	@Override
	public void update(SysMenuEntity menu) {
		sysMenuMapper.update(menu);
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] menuIds) {
		sysMenuMapper.deleteBatch(menuIds);
	}
	
	@Override
	public List<SysMenuEntity> queryUserList(Long userId) {
		return sysMenuMapper.queryUserList(userId);
	}

	private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList){
		List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
		getMenuTreeList(menuList, menuIdList);
		return menuList;
	}

	private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList){
		List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();
		for(SysMenuEntity entity : menuList){
			if(entity.getType() == Constants.MenuType.CATALOG.getValue()){//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		return subMenuList;
	}
}
