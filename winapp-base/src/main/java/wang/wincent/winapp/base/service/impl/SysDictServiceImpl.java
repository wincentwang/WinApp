package wang.wincent.winapp.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.wincent.winapp.base.entity.SysDictEntity;
import wang.wincent.winapp.base.mapper.SysDictMapper;
import wang.wincent.winapp.base.service.SysDictService;
import wang.wincent.winapp.common.utils.CommonUtils;


/**
 * 通用字典
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
@Service("sysDictService")
public class SysDictServiceImpl implements SysDictService {


	@Autowired
	private SysDictMapper sysDictMapper;

	@Override
	public List<SysDictEntity> listDict() {
		return sysDictMapper.list();
	}

	@Override
	public List<SysDictEntity> listNotDict() {
		return sysDictMapper.listNotDict();
	}

	@Override
	public void saveDict(SysDictEntity dict) {
		sysDictMapper.save(dict);
	}

	@Override
	public SysDictEntity getObjectById(Long id) {
		return sysDictMapper.getObjectById(id);
	}

	@Override
	public int updateDict(SysDictEntity macro) {
		return sysDictMapper.update(macro);
	}

	@Override
	public int batchRemove(Long[] id) {
		return sysDictMapper.batchRemove(id);
	}

	@Override
	public boolean hasChildren(Long[] id) {
		for(Long typeId : id) {
			int count = sysDictMapper.countMacroChildren(typeId);
			if(CommonUtils.isIntThanZero(count)) {
				return true;
			}
		}
		return false;
	}


}
