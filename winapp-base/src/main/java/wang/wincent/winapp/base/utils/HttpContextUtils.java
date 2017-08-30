package wang.wincent.winapp.base.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * http上下文
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public class HttpContextUtils {

	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
}
