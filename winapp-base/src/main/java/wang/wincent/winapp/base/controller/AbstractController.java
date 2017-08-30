package wang.wincent.winapp.base.controller;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wang.wincent.winapp.base.entity.SysUserEntity;

/**
 * Controller公共组件
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected Long getDeptId() {
		return getUser().getDeptId();
	}
}
