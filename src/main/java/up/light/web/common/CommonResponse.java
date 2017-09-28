package up.light.web.common;

/**
 * 统一返回格式
 */
public class CommonResponse {
	// 业务成功
	public static final int CODE_SUCCESS = 1000;
	// 认证及授权失败，100X
	public static final int CODE_UNAUTH = 1001;
	public static final int CODE_NO_ACCESS = 1002;
	// 参数错误，200X
	public static final int CODE_INVALID_PARAM = 2001;
	// 请求错误，400X
	public static final int CODE_NOT_FOUND = 4001;
	public static final int CODE_NO_METHOD = 4002;
	// 服务器内部错误，500X
	public static final int CODE_INTERNAL_ERROR = 5001;

	private int code;
	private String msg;
	private Object data;

	public static CommonResponse success(String msg, Object data) {
		CommonResponse resp = new CommonResponse(CODE_SUCCESS, msg, data);
		return resp;
	}

	public static CommonResponse invalidParam(String msg) {
		CommonResponse resp = new CommonResponse(CODE_INVALID_PARAM, msg, null);
		return resp;
	}

	public static CommonResponse error(int code, String msg) {
		return new CommonResponse(code, msg, null);
	}

	public static CommonResponse create(int code, String msg, Object data) {
		return new CommonResponse(code, msg, data);
	}

	public CommonResponse(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
