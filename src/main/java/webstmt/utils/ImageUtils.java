package webstmt.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtils 
{
	private static Logger log = LoggerFactory.getLogger(ImageUtils.class);
	
	public static boolean imageTagExists(String rawHtml)
	{
		return rawHtml.contains("<img");
	}
	
	public static String convertImageBytesToBase64String(String storedImagePath)
	{
		String extension = storedImagePath.substring(storedImagePath.lastIndexOf(".")+1);
		
		InputStream in = null;
		
		byte[] data = null;
		
		try 
		{
			in = new FileInputStream(storedImagePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		}
		catch (IOException e) 
		{
			log.error("Fail to read image file - "+storedImagePath,e);
		}
		
		String base64String = DatatypeConverter.printBase64Binary(data);
		
		return "data:image/"+extension+";base64,"+base64String;
	}
	
	public static String imagePathConvert(String storedImagePath, String storedRootPath)
	{
		return storedImagePath.substring(storedRootPath.length());
	}
}
