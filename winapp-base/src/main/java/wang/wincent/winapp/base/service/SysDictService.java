package wang.wincent.winapp.base.service;

import java.util.List;

import wang.wincent.winapp.base.entity.SysDictEntity;

/**
 * 通用字典
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysDictService {

	List<SysDictEntity> listDict();

	List<SysDictEntity> listNotDict();

	void saveDict(SysDictEntity dict);

	SysDictEntity getObjectById(Long id);

	int updateDict(SysDictEntity dict);

	int batchRemove(Long[] id);

	boolean hasChildren(Long[] id);
	
}
