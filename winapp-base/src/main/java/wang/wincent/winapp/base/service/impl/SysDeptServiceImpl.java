package wang.wincent.winapp.base.service.impl;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.wincent.winapp.base.entity.SysDeptEntity;
import wang.wincent.winapp.base.mapper.SysDeptMapper;
import wang.wincent.winapp.base.service.SysDeptService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {

	@Autowired
	private SysDeptMapper sysDeptMapper;
	
	@Override
	public SysDeptEntity queryObject(Long deptId){
		return sysDeptMapper.queryObject(deptId);
	}
	
	@Override
	public List<SysDeptEntity> queryList(Map<String, Object> map){
		return sysDeptMapper.queryList(map);
	}
	
	@Override
	public void save(SysDeptEntity sysDept){
		sysDeptMapper.save(sysDept);
	}
	
	@Override
	public void update(SysDeptEntity sysDept){
		sysDeptMapper.update(sysDept);
	}
	
	@Override
	public void delete(Long deptId){
		sysDeptMapper.delete(deptId);
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		return sysDeptMapper.queryDetpIdList(parentId);
	}

	@Override
	public String getSubDeptIdList(Long deptId){
		//部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		//获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		//添加本部门
		deptIdList.add(deptId);

		String deptFilter = StringUtils.join(deptIdList, ",");
		return deptFilter;
	}

	/**
	 * 递归
	 */
	public void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
		for(Long deptId : subIdList){
			List<Long> list = queryDetpIdList(deptId);
			if(list.size() > 0){
				getDeptTreeList(list, deptIdList);
			}
			deptIdList.add(deptId);
		}
	}
}
