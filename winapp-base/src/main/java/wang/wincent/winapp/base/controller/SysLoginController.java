package wang.wincent.winapp.base.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wang.wincent.winapp.base.annotation.SysLog;
import wang.wincent.winapp.base.entity.SysUserEntity;
import wang.wincent.winapp.base.service.SysUserService;
import wang.wincent.winapp.base.service.SysUserTokenService;
import wang.wincent.winapp.base.shiro.ShiroUtils;
import wang.wincent.winapp.common.utils.Result;


import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;


/**
 * 登录相关
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
@RestController
public class SysLoginController {

	@Autowired
	private Producer producer;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;

	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		//生成文字验证码
		String text = producer.createText();
		//生成图片验证码
		BufferedImage image = producer.createImage(text);
		//保存到shiro session
		ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录
	 */
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public Map<String, Object> login(String username, String password, String captcha)throws IOException {
		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if(!captcha.equalsIgnoreCase(kaptcha)){
			return Result.error("验证码不正确");
		}
		try{
			Subject subject = ShiroUtils.getSubject();
			//sha256加密
			SysUserEntity user = sysUserService.queryByUserName(username);
			if(null!=user){
				password = new Sha256Hash(password,user.getSalt()).toHex();
			}
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
		}catch (UnknownAccountException e) {
			return Result.error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
			return Result.error(e.getMessage());
		}catch (LockedAccountException e) {
			return Result.error(e.getMessage());
		}catch (AuthenticationException e) {
			return Result.error("账户验证失败");
		}
		return Result.ok();
	}

	@SysLog("用户退出")
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		ShiroUtils.logout();
		return "redirect:login.html";
	}

	public static void main(String[] args) {
		System.out.println(new Sha256Hash("admin").toHex());
	}
}
