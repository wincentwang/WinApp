package wang.wincent.winapp.base.service;


import wang.wincent.winapp.base.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysMenuService {
	

	List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

	List<SysMenuEntity> queryListParentId(Long parentId);
	
	List<SysMenuEntity> queryNotButtonList();

	List<SysMenuEntity> getUserMenuList(Long userId);
	
	SysMenuEntity queryObject(Long menuId);

	List<SysMenuEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysMenuEntity menu);

	void update(SysMenuEntity menu);

	void deleteBatch(Long[] menuIds);
	
	List<SysMenuEntity> queryUserList(Long userId);
}
