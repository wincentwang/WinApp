package wang.wincent.winapp.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.wincent.winapp.base.mapper.SysRoleDeptMapper;
import wang.wincent.winapp.base.service.SysRoleDeptService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 角色与部门对应关系
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl implements SysRoleDeptService {
	@Autowired
	private SysRoleDeptMapper sysRoleDeptDao;

	@Override
	@Transactional
	public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
		//先删除角色与菜单关系
		sysRoleDeptDao.delete(roleId);

		if(deptIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", roleId);
		map.put("deptIdList", deptIdList);
		sysRoleDeptDao.save(map);
	}

	@Override
	public List<Long> queryDeptIdList(Long roleId) {
		return sysRoleDeptDao.queryDeptIdList(roleId);
	}

}
