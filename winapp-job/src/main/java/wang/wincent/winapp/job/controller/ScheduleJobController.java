package wang.wincent.winapp.job.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.wincent.winapp.common.utils.PageUtils;
import wang.wincent.winapp.common.utils.Query;
import wang.wincent.winapp.common.utils.Result;
import wang.wincent.winapp.common.utils.ValidatorUtils;
import wang.wincent.winapp.job.entity.ScheduleJobEntity;
import wang.wincent.winapp.job.service.ScheduleJobService;

import java.util.List;
import java.util.Map;


/**
 * 定时任务
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {

	@Autowired
	private ScheduleJobService scheduleJobService;
	
	/**
	 * 定时任务列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:list")
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<ScheduleJobEntity> jobList = scheduleJobService.queryList(query);
		int total = scheduleJobService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(jobList, total, query.getLimit(), query.getPage());
		return Result.ok().put("page", pageUtil);
	}
	
	/**
	 * 定时任务信息
	 */
	@RequestMapping("/info/{jobId}")
	@RequiresPermissions("sys:schedule:info")
	public Result info(@PathVariable("jobId") Long jobId){
		ScheduleJobEntity schedule = scheduleJobService.queryObject(jobId);
		
		return Result.ok().put("schedule", schedule);
	}
	
	/**
	 * 保存定时任务
	 */
//	@SysLog("保存定时任务")
	@RequestMapping("/save")
	@RequiresPermissions("sys:schedule:save")
	public Result save(@RequestBody ScheduleJobEntity scheduleJob){
		ValidatorUtils.validateEntity(scheduleJob);
		scheduleJobService.save(scheduleJob);
		return Result.ok();
	}
	
	/**
	 * 修改定时任务
	 */
//	@SysLog("修改定时任务")
	@RequestMapping("/update")
	@RequiresPermissions("sys:schedule:update")
	public Result update(@RequestBody ScheduleJobEntity scheduleJob){
		ValidatorUtils.validateEntity(scheduleJob);
		scheduleJobService.update(scheduleJob);
		return Result.ok();
	}
	
	/**
	 * 删除定时任务
	 */
//	@SysLog("删除定时任务")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:schedule:delete")
	public Result delete(@RequestBody Long[] jobIds){
		scheduleJobService.deleteBatch(jobIds);
		return Result.ok();
	}
	
	/**
	 * 立即执行任务
	 */
//	@SysLog("立即执行任务")
	@RequestMapping("/run")
	@RequiresPermissions("sys:schedule:run")
	public Result run(@RequestBody Long[] jobIds){
		scheduleJobService.run(jobIds);
		return Result.ok();
	}
	
	/**
	 * 暂停定时任务
	 */
//	@SysLog("暂停定时任务")
	@RequestMapping("/pause")
	@RequiresPermissions("sys:schedule:pause")
	public Result pause(@RequestBody Long[] jobIds){
		scheduleJobService.pause(jobIds);
		return Result.ok();
	}
	
	/**
	 * 恢复定时任务
	 */
//	@SysLog("恢复定时任务")
	@RequestMapping("/resume")
	@RequiresPermissions("sys:schedule:resume")
	public Result resume(@RequestBody Long[] jobIds){
		scheduleJobService.resume(jobIds);
		return Result.ok();
	}

}
