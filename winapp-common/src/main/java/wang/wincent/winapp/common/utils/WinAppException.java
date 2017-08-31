package wang.wincent.winapp.common.utils;


/**
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
public class WinAppException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = 500;
    
    public WinAppException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public WinAppException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public WinAppException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public WinAppException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
