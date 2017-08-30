package wang.wincent.winapp.base.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.wincent.winapp.base.entity.SysRoleEntity;
import wang.wincent.winapp.base.mapper.SysRoleMapper;
import wang.wincent.winapp.base.service.SysRoleDeptService;
import wang.wincent.winapp.base.service.SysRoleMenuService;
import wang.wincent.winapp.base.service.SysRoleService;
import wang.wincent.winapp.base.service.SysUserService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	@Autowired
	private SysUserService sysUserService;

	@Override
	public SysRoleEntity queryObject(Long roleId) {
		return sysRoleMapper.queryObject(roleId);
	}

	@Override
	public List<SysRoleEntity> queryList(Map<String, Object> map) {
		return sysRoleMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysRoleMapper.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysRoleEntity role) {
		role.setCreateTime(new Date());
		sysRoleMapper.save(role);
		
		//保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		//保存角色与部门关系
		sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
	}

	@Override
	@Transactional
	public void update(SysRoleEntity role) {
		sysRoleMapper.update(role);
		
		//更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		//保存角色与部门关系
		sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] roleIds) {
		sysRoleMapper.deleteBatch(roleIds);
	}

}
