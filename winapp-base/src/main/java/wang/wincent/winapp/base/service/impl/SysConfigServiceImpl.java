package wang.wincent.winapp.base.service.impl;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.wincent.winapp.base.entity.SysConfigEntity;
import wang.wincent.winapp.base.mapper.SysConfigMapper;
import wang.wincent.winapp.base.service.SysConfigService;
import wang.wincent.winapp.base.utils.JSONUtils;
import wang.wincent.winapp.base.utils.WinAppException;

import java.util.List;
import java.util.Map;

@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {

	@Autowired
	private SysConfigMapper sysConfigMapper;
	
	@Override
	@Transactional
	public void save(SysConfigEntity config) {
		sysConfigMapper.save(config);
	}

	@Override
	public void update(SysConfigEntity config) {
		sysConfigMapper.update(config);
	}

	@Override
	public void updateValueByKey(String key, String value) {
		sysConfigMapper.updateValueByKey(key, value);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		sysConfigMapper.deleteBatch(ids);
	}

	@Override
	public List<SysConfigEntity> queryList(Map<String, Object> map) {
		return sysConfigMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysConfigMapper.queryTotal(map);
	}

	@Override
	public SysConfigEntity queryObject(Long id) {
		return sysConfigMapper.queryObject(id);
	}

	@Override
	public String getValue(String key, String defaultValue) {
		String value = sysConfigMapper.queryByKey(key);
		if(StringUtils.isBlank(value)){
			return defaultValue;
		}
		return value;
	}
	
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key, null);
		if(StringUtils.isNotBlank(value)){
			return (T) JSONUtils.jsonToBean(value, clazz);
		}
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new WinAppException("获取参数失败");
		}
	}
}
