package webstmt.utils;

public class PathUtil 
{
	private static final String loginNamePlaceholder = "[__id__]";
	
	public static String convertToPhysicalPath(String htmlImagePath, String physicalPath, String loginUsername)
	{
		String imageActualPhysicalPath = getUserUploadBasePhysicalPath(physicalPath, loginUsername);
		String imageFileName =htmlImagePath.substring(htmlImagePath.lastIndexOf("/")+1);
		String imagePhysicalPath = imageActualPhysicalPath+imageFileName;
		
		return imagePhysicalPath;
	}
	
	public static String getUserUploadBasePhysicalPath(String physicalPath, String loginUsername)
	{
		String pathPrefix = physicalPath.substring(0, physicalPath.lastIndexOf(loginNamePlaceholder));
		String pathPostfix = physicalPath.substring(physicalPath.lastIndexOf(loginNamePlaceholder)+loginNamePlaceholder.length());
		
		String actualPhysicalPath = pathPrefix + loginUsername + pathPostfix;
		
		return actualPhysicalPath;
	}
}
