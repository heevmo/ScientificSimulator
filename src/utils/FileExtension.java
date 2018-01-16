package utils;

/**
 * 
 * @author Alex
 *
 */
public class FileExtension {
	
	public static String getFileExtension(String name) {
		
		int pointIndex = name.lastIndexOf(".");
		
		if (pointIndex == -1)
			return null;
		
		if (pointIndex == name.length() - 1)
			return null;
		
		return name.substring(pointIndex + 1, name.length()).toLowerCase();
	}
	
	public static String trimExtension(String name) {
		int pointIndex = name.indexOf(".");
		return name.substring(0, pointIndex);
	}
	
}
