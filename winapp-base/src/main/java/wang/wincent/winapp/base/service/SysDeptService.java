package wang.wincent.winapp.base.service;

import wang.wincent.winapp.base.entity.SysDeptEntity;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysDeptService extends BaseService<SysDeptEntity>{
	
	SysDeptEntity queryObject(Long deptId);
	
	List<SysDeptEntity> queryList(Map<String, Object> map);

	void save(SysDeptEntity sysDept);
	
	void update(SysDeptEntity sysDept);
	
	void delete(Long deptId);

	List<Long> queryDetpIdList(Long parentId);

	String getSubDeptIdList(Long deptId);

}
