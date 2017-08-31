package wang.wincent.winapp.job.utils;


import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;
import wang.wincent.winapp.common.utils.SpringContextUtils;
import wang.wincent.winapp.common.utils.WinAppException;

import java.lang.reflect.Method;

/**
 * 执行定时任务
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public class ScheduleRunnable implements Runnable {
	private Object target;
	private Method method;
	private String params;
	
	public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
		this.target = SpringContextUtils.getBean(beanName);
		this.params = params;
		
		if(StringUtils.isNotBlank(params)){
			this.method = target.getClass().getDeclaredMethod(methodName, String.class);
		}else{
			this.method = target.getClass().getDeclaredMethod(methodName);
		}
	}

	@Override
	public void run() {
		try {
			ReflectionUtils.makeAccessible(method);
			if(StringUtils.isNotBlank(params)){
				method.invoke(target, params);
			}else{
				method.invoke(target);
			}
		}catch (Exception e) {
			throw new WinAppException("执行定时任务失败", e);
		}
	}

}
