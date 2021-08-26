package webstmt.service.sys;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	private static final List<String> SUPPORT_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png", "image/gif");
	private static final Map<Integer, String> ERROR_MAP;
	
	private static final int ERROR_CODE_INVALID_TYPE = 500011;
	private static final int ERROR_CODE_EMPTY_FILE = 500012;
	private static final int ERROR_CODE_FAIL_SAVE_FILE = 500013;
	
	static {
		ERROR_MAP = new HashMap<Integer, String>();
		
		ERROR_MAP.put(ERROR_CODE_INVALID_TYPE, "Invalid File Type!");
		ERROR_MAP.put(ERROR_CODE_EMPTY_FILE, "Empty File!");
		ERROR_MAP.put(ERROR_CODE_FAIL_SAVE_FILE, "Fail to save file!");
	}
	
	public static boolean isUploadError(String errorMessage) {
		
		return ERROR_MAP.containsValue(errorMessage);
	}
	
	@Value("${user.upload.tmp}")
	private String path;
	
	public String uploadImage(MultipartFile file) {
		
		String contentType = file.getContentType();
		
		if(!SUPPORT_TYPES.contains(contentType)) {
			return ERROR_MAP.get(ERROR_CODE_INVALID_TYPE);
		}
		
		try {
			
			BufferedImage image = ImageIO.read(file.getInputStream());
			if(image==null) {
				return ERROR_MAP.get(ERROR_CODE_EMPTY_FILE);
			}
			
			String fileName = file.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			String newFileName = UUID.randomUUID()+suffix;
			
			File dest = new File(path + newFileName);
			
			file.transferTo(dest);
			
			return newFileName;
			
		} catch (IOException e) {
			
			e.printStackTrace();
			return ERROR_MAP.get(ERROR_CODE_FAIL_SAVE_FILE);
		}	
	}
}
