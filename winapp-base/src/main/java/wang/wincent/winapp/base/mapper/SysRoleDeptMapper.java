package wang.wincent.winapp.base.mapper;

import wang.wincent.winapp.base.entity.SysRoleDeptEntity;
import wang.wincent.winapp.common.mapper.BaseMapper;

import java.util.List;

/**
 * 角色与部门对应关系
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDeptEntity> {
	
	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<Long> queryDeptIdList(Long roleId);
}
