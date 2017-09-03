package wang.wincent.winapp.job.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.wincent.winapp.job.entity.ScheduleJobLogEntity;
import wang.wincent.winapp.job.mapper.ScheduleJobLogMapper;
import wang.wincent.winapp.job.service.ScheduleJobLogService;

import java.util.List;
import java.util.Map;



@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {


	@Autowired
	private ScheduleJobLogMapper scheduleJobLogMapper;
	
	@Override
	public ScheduleJobLogEntity queryObject(Long jobId) {
		return scheduleJobLogMapper.queryObject(jobId);
	}

	@Override
	public List<ScheduleJobLogEntity> queryList(Map<String, Object> map) {
		return scheduleJobLogMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return scheduleJobLogMapper.queryTotal(map);
	}

	@Override
	public void save(ScheduleJobLogEntity log) {
		scheduleJobLogMapper.save(log);
	}

}
