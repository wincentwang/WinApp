package wang.wincent.winapp.base.mapper;

import wang.wincent.winapp.base.entity.SysRoleMenuEntity;
import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);
}
