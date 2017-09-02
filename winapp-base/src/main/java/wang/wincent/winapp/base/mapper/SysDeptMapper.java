package wang.wincent.winapp.base.mapper;

import wang.wincent.winapp.base.entity.SysDeptEntity;
import wang.wincent.winapp.common.mapper.BaseMapper;

import java.util.List;

/**
 * 部门管理
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysDeptMapper extends BaseMapper<SysDeptEntity> {

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);
}
