package wang.wincent.winapp.base.service;

import wang.wincent.winapp.base.entity.SysUserEntity;
import wang.wincent.winapp.base.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public interface ShiroService  {

    Set<String> getUserPermissions(long userId);

    SysUserTokenEntity queryByToken(String token);

    SysUserEntity queryUser(Long userId);

}
