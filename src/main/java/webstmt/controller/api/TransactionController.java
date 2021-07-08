package webstmt.controller.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/vue-element-admin/transaction")
public class TransactionController {

	@GetMapping("list")
	@ResponseBody
	public Map<String, Object> list()
	{
		Map<String, Object> responseData = new HashMap<String, Object>();
		Map<String, Object> response = new HashMap<>();
		
		List<Map<String, Object>> items = new ArrayList<>();
		
		Map<String, Object> item1 = new HashMap<>();
		item1.put("order_no", "12345");
		item1.put("timestamp", new Date());
		item1.put("username", "ABCDE");
		item1.put("price", 112233.44);
		item1.put("status", "success");
		items.add(item1);
		
		Map<String, Object> item2 = new HashMap<>();
		item2.put("order_no", "67890");
		item2.put("timestamp", new Date());
		item2.put("username", "FGHIJ");
		item2.put("price", 556677.88);
		item2.put("status", "pending");
		items.add(item2);
		
		responseData.put("items", items);
		responseData.put("total", items.size());
		
		response.put("data", responseData);
		response.put("code", 20000);
		response.put("msg", "Succ");
		
		return response;
	}
}
