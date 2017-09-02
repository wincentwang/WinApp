package wang.wincent.winapp.job.mapper;

import wang.wincent.winapp.common.mapper.BaseMapper;
import wang.wincent.winapp.job.entity.ScheduleJobEntity;

import java.util.Map;

/**
 * 定时任务
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */

public interface ScheduleJobMapper extends BaseMapper<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);


}
