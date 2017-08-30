package wang.wincent.winapp.base.mapper;

import wang.wincent.winapp.base.entity.SysUserTokenEntity;

/**
 * 系统用户Token
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface SysUserTokenMapper extends BaseMapper<SysUserTokenEntity> {
    
    SysUserTokenEntity queryByUserId(Long userId);

    SysUserTokenEntity queryByToken(String token);
	
}
