package wang.wincent.winapp.base.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.wincent.winapp.base.entity.SysLogEntity;
import wang.wincent.winapp.base.mapper.SysLogMapper;
import wang.wincent.winapp.base.service.SysLogService;

import java.util.List;
import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	private SysLogMapper sysLogMapper;
	
	@Override
	public SysLogEntity queryObject(Long id){
		return sysLogMapper.queryObject(id);
	}
	
	@Override
	public List<SysLogEntity> queryList(Map<String, Object> map){
		return sysLogMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysLogMapper.queryTotal(map);
	}
	
	@Override
	public void save(SysLogEntity sysLog){
		sysLogMapper.save(sysLog);
	}
	
	@Override
	public void delete(Long id){
		sysLogMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysLogMapper.deleteBatch(ids);
	}
	
}
