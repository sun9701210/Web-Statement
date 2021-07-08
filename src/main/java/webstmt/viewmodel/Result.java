package webstmt.viewmodel;

public class Result {

	private int code;
	private String msg;
	private Object data;
	
	
	
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



	public static Result result(int code, String msg, Object data) {
		Result r = new Result();

		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		
		return r;
	}
}