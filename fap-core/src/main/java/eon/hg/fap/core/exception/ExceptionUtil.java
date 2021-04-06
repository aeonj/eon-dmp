package eon.hg.fap.core.exception;

/**
 * 	全局异常记录类
 * @author GPZ
 *
 */
public class ExceptionUtil {

	/**
	 * 	错误编码
	 */
	private String errorCode;
	
	/**
	 * 	错误信息
	 */
	private String errorMsg;
	
	/**
	 * 	备注
	 */
	private String remark;
	
	/**
	 * 	是否继续
	 */
	private boolean isContinue;
	
	/**
	 * 	线程变量
	 */
	private final static ThreadLocal<ExceptionUtil> threadLocal = new ThreadLocal<ExceptionUtil>();
	
	/**
	 * 	初始化
	 * @return
	 */
    public static ExceptionUtil init() {
    	ExceptionUtil exception = new ExceptionUtil();
    	threadLocal.set(exception);
        return exception;
    }
    
    /**
     * 	获取错误信息
     * @return
     */
    public static ExceptionUtil get() {
    	return threadLocal.get();
    }
    
    /**
     * 	写错误信息
     * @param exception
     */
    public static void set(ExceptionUtil exception) {
    	threadLocal.set(exception);
    }
    
    /**
     * 	清除错误信息
     */
    public static void remove() {
    	threadLocal.remove();
    }

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isContinue() {
		return isContinue;
	}

	public void setContinue(boolean isContinue) {
		this.isContinue = isContinue;
	}

}
