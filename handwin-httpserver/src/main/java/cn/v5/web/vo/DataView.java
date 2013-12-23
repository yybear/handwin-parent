package cn.v5.web.vo;

/**
 * @author qgan
 * @version 2013年12月16日 下午7:07:36
 */
public class DataView {

	/**
	 * 错误状态码.如果操作成功,则为0.
	 */
	private int errno = 0;
	/**
	 * 错误消息.如果errno为0,则该属性可以不存在或为空值(客户端会直接忽略该属性)
	 */
	private String msg;
	/**
	 * 错误的详细内容.如果有.如果errno不为零,则该属性可用来存储服务器端错误的详细信息,如堆栈.
	 */
	private String detail;

	/**
	 * 结果返回的业务数据.如果errno不为0,则该属性会被客户端直接忽略.
	 */
	private Object data;

	public int getErrno() {
		return errno;
	}

	public void setErrno(int errno) {
		this.errno = errno;
	}

	public String getMsg() {
		/*
		 * if (StringUtils.isBlank(msg)) msg = NLS.getMessage("error." + errno);
		 */
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DataView [errno=" + errno + ", msg=" + msg + ", detail="
				+ detail + ", data=" + data + "]";
	}

	public DataView() {
		super();
	}

	public DataView(int errno, Object data) {
		super();
		this.errno = errno;
		this.data = data;
	}

	public DataView(int errno) {
		super();
		this.errno = errno;
	}
}
