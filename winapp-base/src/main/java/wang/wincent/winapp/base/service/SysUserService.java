package wang.wincent.winapp.base.service;

import wang.wincent.winapp.base.entity.SysUserEntity;

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
public interface SysUserService {

	List<String> queryAllPerms(Long userId);
	
	List<Long> queryAllMenuId(Long userId);

	SysUserEntity queryByUserName(String username);

	SysUserEntity queryObject(Long userId);
	
	List<SysUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysUserEntity user);
	
	void update(SysUserEntity user);
	
	void deleteBatch(Long[] userIds);

	int updatePassword(Long userId, String password, String newPassword);

    List<Long> queryUserIdByDeptId(long deptId);
}
