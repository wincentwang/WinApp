package wang.wincent.winapp.base.mapper;

import java.util.List;


import wang.wincent.winapp.base.entity.SysDictEntity;
import wang.wincent.winapp.common.mapper.BaseMapper;

/**
 * 通用字典
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysDictMapper extends BaseMapper<SysDictEntity> {

	List<SysDictEntity> listNotDict();
	
	int countMacroChildren(Long typeId);


}
