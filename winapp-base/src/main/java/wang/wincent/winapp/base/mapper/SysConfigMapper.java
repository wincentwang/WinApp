package wang.wincent.winapp.base.mapper;

import org.apache.ibatis.annotations.Param;
import wang.wincent.winapp.base.entity.SysConfigEntity;
import wang.wincent.winapp.common.mapper.BaseMapper;

/**
 * 系统配置信息
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysConfigMapper extends BaseMapper<SysConfigEntity> {
	
	/**
	 * 根据key，查询value
	 */
	String queryByKey(String paramKey);
	
	/**
	 * 根据key，更新value
	 */
	int updateValueByKey(@Param("key") String key, @Param("value") String value);
	
}
