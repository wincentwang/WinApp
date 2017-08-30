package wang.wincent.winapp.base.service;

import java.util.List;


/**
 * 用户与角色对应关系
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysUserRoleService {
	
	void saveOrUpdate(Long userId, List<Long> roleIdList);
	
	List<Long> queryRoleIdList(Long userId);
	
	void delete(Long userId);
}
