package wang.wincent.winapp.base.mapper;

import wang.wincent.winapp.base.entity.SysUserRoleEntity;
import java.util.List;

/**
 * 用户与角色对应关系
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleEntity> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
}
