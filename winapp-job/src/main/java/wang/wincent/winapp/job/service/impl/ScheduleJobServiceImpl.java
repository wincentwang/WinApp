package wang.wincent.winapp.job.service.impl;


import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.wincent.winapp.common.utils.Constants;
import wang.wincent.winapp.job.entity.ScheduleJobEntity;
import wang.wincent.winapp.job.mapper.ScheduleJobMapper;
import wang.wincent.winapp.job.service.ScheduleJobService;
import wang.wincent.winapp.job.utils.ScheduleUtils;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {

	@Autowired
    private Scheduler scheduler;

	@Autowired
	private ScheduleJobMapper scheduleJobMapper;
	
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<ScheduleJobEntity> scheduleJobList = scheduleJobMapper.queryList(new HashMap<>());
		for(ScheduleJobEntity scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
		}
	}
	
	@Override
	public ScheduleJobEntity queryObject(Long jobId) {
		return scheduleJobMapper.queryObject(jobId);
	}

	@Override
	public List<ScheduleJobEntity> queryList(Map<String, Object> map) {
		return scheduleJobMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return scheduleJobMapper.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(ScheduleJobEntity scheduleJob) {
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(Constants.ScheduleStatus.NORMAL.getValue());
		scheduleJobMapper.save(scheduleJob);
        
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }
	
	@Override
	@Transactional
	public void update(ScheduleJobEntity scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

		scheduleJobMapper.update(scheduleJob);
    }

	@Override
	@Transactional
    public void deleteBatch(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    	}
    	
    	//删除数据
		scheduleJobMapper.deleteBatch(jobIds);
	}

	@Override
    public int updateBatch(Long[] jobIds, int status){
    	Map<String, Object> map = new HashMap<>();
    	map.put("list", jobIds);
    	map.put("status", status);
    	return scheduleJobMapper.updateBatch(map);
    }
    
	@Override
	@Transactional
    public void run(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.run(scheduler, queryObject(jobId));
    	}
    }

	@Override
	@Transactional
    public void pause(Long[] jobIds) {
        for(Long jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}
        
    	updateBatch(jobIds, Constants.ScheduleStatus.PAUSE.getValue());
    }

	@Override
	@Transactional
    public void resume(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.resumeJob(scheduler, jobId);
    	}

    	updateBatch(jobIds, Constants.ScheduleStatus.NORMAL.getValue());
    }
    
}
