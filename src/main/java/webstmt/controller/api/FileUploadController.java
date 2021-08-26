package webstmt.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import webstmt.service.sys.FileUploadService;
import webstmt.viewmodel.MapResult;

@RestController
@RequestMapping("/apis/vue-element-admin/fileupload")
public class FileUploadController {

	@Autowired
	private FileUploadService uploadService;
	
//	@Value("${config.virtual.url}")
//	private String virtualUrl;
	
	@PostMapping("imageUpload")
	@ResponseBody
	public Map<String, Object> image(@RequestParam("file") MultipartFile file) {
		
		System.out.println("hanlding image upload");		
		
		MapResult result = MapResult.newInstance(20000, "succ");
		
		String filename = uploadService.uploadImage(file);
		
		if(FileUploadService.isUploadError(filename)) {
			result = MapResult.newInstance(50000, "error");
			result.setNode("files", null);
			result.setNode("message", filename);
		}
		
//		result.setNode("location", virtualUrl + filename);
		result.setNode("files", "file", filename);
		
		return result.getMap();
	}
}
