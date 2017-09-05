package wang.wincent.winapp.base.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.wincent.winapp.base.entity.SysDeptEntity;
import wang.wincent.winapp.base.service.SysDeptService;
import wang.wincent.winapp.base.service.SysUserService;
import wang.wincent.winapp.common.utils.Constants;
import wang.wincent.winapp.common.utils.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 部门管理
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {

	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:dept:list")
	public List<SysDeptEntity> list(){
		Map<String, Object> map = new HashMap<>();
		//如果不是超级管理员，则只能查询本部门及子部门数据
		if(getUserId() != Constants.SUPER_ADMIN){
			map.put("deptFilter", sysDeptService.getSubDeptIdList(getDeptId()));
		}
		List<SysDeptEntity> deptList = sysDeptService.queryList(map);

		return deptList;
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:dept:select")
	public Result select(){
		Map<String, Object> map = new HashMap<>();
		//如果不是超级管理员，则只能查询本部门及子部门数据
		if(getUserId() != Constants.SUPER_ADMIN){
			map.put("deptFilter", sysDeptService.getSubDeptIdList(getDeptId()));
		}
		List<SysDeptEntity> deptList = sysDeptService.queryList(map);

		//添加一级部门
		if(getUserId() == Constants.SUPER_ADMIN){
			SysDeptEntity root = new SysDeptEntity();
			root.setDeptId(0L);
			root.setName("一级部门");
			root.setParentId(-1L);
			root.setOpen(true);
			deptList.add(root);
		}

		return Result.ok().put("deptList", deptList);
	}

	/**
	 * 上级部门Id(管理员则为0)
	 */
	@RequestMapping("/info")
	@RequiresPermissions("sys:dept:list")
	public Result info(){
		long deptId = 0;
		if(getUserId() != Constants.SUPER_ADMIN){
			SysDeptEntity dept = sysDeptService.queryObject(getDeptId());
			deptId = dept.getParentId();
		}

		return Result.ok().put("deptId", deptId);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{deptId}")
	@RequiresPermissions("sys:dept:info")
	public Result info(@PathVariable("deptId") Long deptId){
		SysDeptEntity dept = sysDeptService.queryObject(deptId);
		
		return Result.ok().put("dept", dept);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:dept:save")
	public Result save(@RequestBody SysDeptEntity dept){
		sysDeptService.save(dept);
		
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:dept:update")
	public Result update(@RequestBody SysDeptEntity dept){
		sysDeptService.update(dept);
		
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:dept:delete")
	public Result delete(long deptId){
		//判断是否有子部门
		List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
		if(deptList.size() > 0){
			return Result.error("请先删除子部门");
		}
		List<Long> userList=sysUserService.queryUserIdByDeptId(deptId);
		if(userList.size() > 0){
			return Result.error("该部门有成员不可以删除");
		}
		sysDeptService.delete(deptId);
		return Result.ok();
	}
	
}
