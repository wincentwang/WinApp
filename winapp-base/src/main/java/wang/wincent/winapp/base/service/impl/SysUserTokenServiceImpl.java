package wang.wincent.winapp.base.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.wincent.winapp.base.entity.SysUserTokenEntity;
import wang.wincent.winapp.base.mapper.SysUserTokenMapper;
import wang.wincent.winapp.base.oauth2.TokenGenerator;
import wang.wincent.winapp.base.service.SysUserTokenService;
import wang.wincent.winapp.common.utils.Result;

import java.util.Date;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl implements SysUserTokenService {

	@Autowired
	private SysUserTokenMapper sysUserTokenMapper;
	//12小时后过期
	private final static int EXPIRE = 3600 * 12;

	@Override
	public SysUserTokenEntity queryByUserId(Long userId) {
		return sysUserTokenMapper.queryByUserId(userId);
	}

	@Override
	public SysUserTokenEntity queryByToken(String token) {
		return sysUserTokenMapper.queryByToken(token);
	}

	@Override
	public void save(SysUserTokenEntity token){
		sysUserTokenMapper.save(token);
	}
	
	@Override
	public void update(SysUserTokenEntity token){
		sysUserTokenMapper.update(token);
	}

	@Override
	public Result createToken(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();
		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
		//判断是否生成过token
		SysUserTokenEntity tokenEntity = queryByUserId(userId);
		if(tokenEntity == null){
			tokenEntity = new SysUserTokenEntity();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//保存token
			save(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//更新token
			update(tokenEntity);
		}
		Result r = Result.ok().put("token", token).put("expire", EXPIRE);
		return r;
	}
}
