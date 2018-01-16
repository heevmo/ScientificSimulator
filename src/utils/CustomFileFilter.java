package utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * 
 * @author Alex
 *
 */
public class CustomFileFilter extends FileFilter {
	
	private String extension;
	
	public CustomFileFilter(String extension) {
		this.extension = extension;
	}
	@Override
	public boolean accept(File f) {

		if (f.isDirectory()) {
			return true;
		}

		String name = f.getName();
		String extension = FileExtension.getFileExtension(name);
		if (extension == null) {
			return false;
		}

		if (extension.equals(this.extension)) {
			return true;
		}

		return false;
	}

	@Override
	public String getDescription() {
		return "*." + this.extension;
	}
}