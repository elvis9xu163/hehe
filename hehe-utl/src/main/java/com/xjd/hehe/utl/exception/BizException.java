package com.xjd.hehe.utl.exception;

import java.util.Arrays;

public class BizException extends RuntimeException {
	private static final long serialVersionUID = -1L;

	protected String code;
	protected Object[] args;
	protected String msg;
	protected String originalCode;
	protected String orginalMsg;

	public BizException(String code) {
		this(code, null, null, null, null, null);
	}

	public BizException(String code, Object[] args) {
		this(code, args, null, null, null, null);
	}

	public BizException(String code, String message) {
		this(code, null, message, null, null, null);
	}

	public BizException(String code, Object[] args, String message) {
		this(code, args, message, null, null, null);
	}

	public BizException(String code, String originalCode, String orginalMsg) {
		this(code, null, null, null, originalCode, orginalMsg);
	}

	public BizException(String code, Object[] args, String originalCode, String orginalMsg) {
		this(code, args, null, null, originalCode, orginalMsg);
	}

	public BizException(String code, String message, String originalCode, String orginalMsg) {
		this(code, null, message, null, originalCode, orginalMsg);
	}

	public BizException(String code, Object[] args, String message, String originalCode, String orginalMsg) {
		this(code, args, message, null, originalCode, orginalMsg);
	}

	public BizException(String code, Throwable cause) {
		this(code, null, null, cause, null, null);
	}

	public BizException(String code, Object[] args, Throwable cause) {
		this(code, args, null, cause, null, null);
	}

	public BizException(String code, String message, Throwable cause) {
		this(code, null, message, cause, null, null);
	}

	public BizException(String code, Object[] args, String message, Throwable cause, String originalCode,
			String orginalMsg) {
		super(cause);
		this.code = code;
		this.args = args;
		this.msg = message;
		this.originalCode = originalCode;
		this.orginalMsg = orginalMsg;
	}

	public String getCode() {
		return code;
	}

	public Object[] getArgs() {
		return args;
	}

	public String getMsg() {
		return msg;
	}

	public String getOriginalCode() {
		return originalCode;
	}

	public String getOrginalMsg() {
		return orginalMsg;
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(getClass().getSimpleName());
		buf.append("[code='" + getCode() + "'");
		buf.append(", args=" + Arrays.toString(args));
		buf.append(", msg='" + msg + "'");
		buf.append(", originalCode='" + originalCode + "'");
		buf.append(", orginalMsg='" + orginalMsg + "']");
		String message = getLocalizedMessage();
		buf.append((message != null) ? ": " + message : "");
		StackTraceElement[] traces = getStackTrace();
		buf.append(traces.length == 0 ? "" : ": at " + traces[0]);
		return buf.toString();
	}
}
