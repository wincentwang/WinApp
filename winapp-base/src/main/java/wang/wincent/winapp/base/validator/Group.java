package wang.wincent.winapp.base.validator;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
