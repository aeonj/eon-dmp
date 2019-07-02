package eon.hg.fap.core.exception;


import eon.hg.fap.common.CommUtil;

/**
 * <b>系统异常类</b>
 * 
 * @author OSWorks-XC
 */
public class AeonException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AeonException() {
		super();
	}

	/**
	 * 支持传参数给异常描述字符串进行合并
	 * 
	 * @param errID
	 * @param args
	 */
	public AeonException(int errID, Object... args) {
		super("异常编号：" + errID);
		ExceptionVO vo = ExceptionInfoUtil.getExceptionInfo(String.valueOf(errID));
		if (CommUtil.isNotEmpty(vo)) {
			String errMsg = "异常编号：" + errID;
			//errMsg = errMsg + "\n异常摘要：" + VelocityHelper.merge(vo.getInfo(), args);
			errMsg = errMsg + "\n异常排查建议：" + vo.getSuggest() ;
			errMsg = errMsg + "\n异常详细堆栈信息";
			System.out.println(errMsg);
		} else {
			System.out.println("没有查询到异常编号为[" + errID + "]的异常配置信息。");
		}
	}

	/**
	 * 根据异常ID获取异常相关信息
	 * 
	 * @param errID
	 */
	public AeonException(int errID) {
		super("异常编号：" + errID);
		ExceptionVO vo = ExceptionInfoUtil.getExceptionInfo(String.valueOf(errID));
		if (CommUtil.isNotEmpty(vo)) {
			String errMsg = "异常编号：" + errID;
			//errMsg = errMsg + "\n异常摘要：" + VelocityHelper.merge(vo.getInfo(), "");
			errMsg = errMsg + "\n异常排查建议：" + vo.getSuggest() ;
			errMsg = errMsg + "\n异常详细堆栈信息";
			System.out.println(errMsg);
		} else {
			System.out.println("没有查询到异常编号为[" + errID + "]的异常配置信息。");
		}
	}
	
	/**
	 * 直接打印简单信息
	 * 
	 * @param pMsg
	 */
	public AeonException(String pMsg) {
		super(pMsg);
	}

	/**
	 * 直接打印简单信息和异常堆栈
	 * 
	 * @param pMsg
	 * @param pNestedException
	 */
	public AeonException(String pMsg, Throwable pNestedException) {
		super(pMsg);
		pNestedException.printStackTrace();
	}
	
	/**
	 * 直接打印异常堆栈
	 * 
	 * @param pNestedException
	 */
	public AeonException(Throwable pNestedException) {
		super(pNestedException);
		pNestedException.printStackTrace();
	}

}
