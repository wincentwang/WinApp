package wang.wincent.winapp.base.service.impl;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.wincent.winapp.base.entity.SysMenuEntity;
import wang.wincent.winapp.base.entity.SysUserEntity;
import wang.wincent.winapp.base.entity.SysUserTokenEntity;
import wang.wincent.winapp.base.mapper.SysMenuMapper;
import wang.wincent.winapp.base.mapper.SysUserMapper;
import wang.wincent.winapp.base.mapper.SysUserTokenMapper;
import wang.wincent.winapp.base.service.ShiroService;
import wang.wincent.winapp.common.utils.Constants;

import java.util.*;

@Service("shiroService")
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;
        //系统管理员，拥有最高权限
        if(userId == Constants.SUPER_ADMIN){
            List<SysMenuEntity> menuList = sysMenuMapper.queryList(new HashMap<>());
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenMapper.queryByToken(token);
    }

    @Override
    public SysUserEntity queryUser(Long userId) {
        return sysUserMapper.queryObject(userId);
    }
}
