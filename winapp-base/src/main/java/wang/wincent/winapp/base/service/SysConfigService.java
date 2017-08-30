package wang.wincent.winapp.base.service;


import wang.wincent.winapp.base.entity.SysConfigEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysConfigService extends BaseService<SysConfigEntity>{

	public void save(SysConfigEntity config);

	public void update(SysConfigEntity config);

	public void updateValueByKey(String key, String value);
	
	public void deleteBatch(Long[] ids);

	public List<SysConfigEntity> queryList(Map<String, Object> map);

	public int queryTotal(Map<String, Object> map);
	
	public SysConfigEntity queryObject(Long id);
	
	public String getValue(String key, String defaultValue);
	
	public <T> T getConfigObject(String key, Class<T> clazz);
	
}
