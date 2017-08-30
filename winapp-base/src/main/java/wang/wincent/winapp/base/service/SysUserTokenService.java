package wang.wincent.winapp.base.service;

import wang.wincent.winapp.base.entity.SysUserTokenEntity;
import wang.wincent.winapp.base.utils.Result;

/**
 * 用户Token
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysUserTokenService {

	SysUserTokenEntity queryByUserId(Long userId);

	SysUserTokenEntity queryByToken(String token);
	
	void save(SysUserTokenEntity token);
	
	void update(SysUserTokenEntity token);

	Result createToken(long userId);

}
