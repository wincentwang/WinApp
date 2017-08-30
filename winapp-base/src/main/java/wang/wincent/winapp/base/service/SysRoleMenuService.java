package wang.wincent.winapp.base.service;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysRoleMenuService {
	
	void saveOrUpdate(Long roleId, List<Long> menuIdList);

	List<Long> queryMenuIdList(Long roleId);
	
}
