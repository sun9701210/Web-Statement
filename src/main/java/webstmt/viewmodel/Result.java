package webstmt.viewmodel;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

	public void setData(String nodeName, Object data) {
		
		if(this.data == null) {
			this.data = data;
		} else if(this.data instanceof Map) {
			
			((Map<String, Object>)this.data).put(nodeName, data);
			
		} else {
			
			//create a map to save data
			this.data = new HashMap<String, Object>();
			((Map<String,Object>) this.data).put(nodeName, data);
			
		}
	}
	
	public void setData(String parentNode, String nodeName, Object data) {
		
		if(this.data == null) {
			this.data = data;
		} else if(this.data instanceof Map) {
			
			this.data = findParent(parentNode, (Map<String, Object>) this.data);
			((Map<String, Object>)this.data).put(nodeName, data);
			
		} else {
			
			//create a map to save data
			this.data = new HashMap<String, Object>();
			((Map<String,Object>) this.data).put(nodeName, data);
			
		}
	}
	
	private Map<String, Object> findParent(String parentNode, Map<String, Object> tree) {
		
		if(tree.isEmpty()) return tree;
		
		if(tree.containsKey(parentNode) && tree.get(parentNode) instanceof Map) {
			
			return (Map<String, Object>) tree.get(parentNode);
			
		} else {
			
			Map parent = null;
			for (Entry<String, Object> entry : tree.entrySet()) {
				
				if(entry.getValue() instanceof Map) {
					parent = findParent(parentNode, (Map<String, Object>) entry.getValue());
				}
			}
			
			if(parent == null) {
				parent = tree;
			}
			
			return parent;
		}
	}


	public static Result result(int code, String msg, Object data) {
		Result r = new Result();

		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		
		return r;
	}
	
	public static Result result(int code, String msg) {
		
		Result r = new Result();
		
		r.setCode(code);
		r.setMsg(msg);
		
		return r;
	}
}