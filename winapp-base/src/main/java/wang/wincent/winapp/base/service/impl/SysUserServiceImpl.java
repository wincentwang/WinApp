package wang.wincent.winapp.base.service.impl;


import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.wincent.winapp.base.entity.SysUserEntity;
import wang.wincent.winapp.base.mapper.SysUserMapper;
import wang.wincent.winapp.base.service.SysRoleService;
import wang.wincent.winapp.base.service.SysUserRoleService;
import wang.wincent.winapp.base.service.SysUserService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysRoleService sysRoleService;

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserMapper.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserMapper.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserMapper.queryByUserName(username);
	}
	
	@Override
	public SysUserEntity queryObject(Long userId) {
		return sysUserMapper.queryObject(userId);
	}

	@Override
	public List<SysUserEntity> queryList(Map<String, Object> map){
		return sysUserMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysUserMapper.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		sysUserMapper.save(user);
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		}
		sysUserMapper.update(user);
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] userId) {
		sysUserMapper.deleteBatch(userId);
	}

	@Override
	public int updatePassword(Long userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysUserMapper.updatePassword(map);
	}

}
