package webstmt.viewmodel;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapResult {

	private Map<String, Object> root;
	
	private MapResult() {
		root = new HashMap<String, Object>();
	}
	
	public static MapResult newInstance(int code, String msg) {
		
		MapResult r = new MapResult();
		
		r.root.put("code", code);
		r.root.put("msg", msg);
		
		return r;
	}
	
	public Map<String, Object> getMap() {
		
		return this.root;
	}
	
	public void setNode(String nodeName, Object value) {
		
		this.root.put(nodeName, value);
	}
	
	public void setNode(String parentNode, String nodeName, Object value) {
		
		Map<String, Object> parent = findParent(parentNode, this.root);
		
		parent.put(nodeName, value);
	}
	
	private Map<String, Object> findParent(String parentNode, Map<String, Object> tree) {
		
		//find parent recursively
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
				parent = new HashMap<String, Object>();
				tree.put(parentNode, parent);
			}
			
			return parent;
		}
	}
}
