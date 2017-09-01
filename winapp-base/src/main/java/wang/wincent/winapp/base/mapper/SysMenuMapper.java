package wang.wincent.winapp.base.mapper;

import wang.wincent.winapp.base.entity.SysMenuEntity;
import wang.wincent.winapp.common.mapper.BaseMapper;

import java.util.List;

/**
 * 菜单管理
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();
	
	/**
	 * 查询用户的权限列表
	 */
	List<SysMenuEntity> queryUserList(Long userId);
}
