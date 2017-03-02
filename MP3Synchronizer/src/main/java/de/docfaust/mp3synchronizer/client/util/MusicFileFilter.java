package de.docfaust.mp3synchronizer.client.util;

import java.io.File;
import java.io.FileFilter;

import de.docfaust.mp3synchronizer.client.model.ExtensionsModel;

/**
 * FileFilter, der nur die Erweiterungen aus dem ExtensionModel zulässt.
 * 
 * @author XHU1011
 * @see ExtensionsModel
 */
public class MusicFileFilter implements FileFilter {
	/**
	 * Konstante für den Punkt, der die Erweiterungen trennt.
	 */
	public static final String DOT = ".";

	/**
	 * Tests whether or not the specified abstract pathname should be included
	 * in a pathname list.
	 * 
	 * @param file
	 *            The abstract pathname to be tested
	 * @return <code>true</code> if and only if <code>pathname</code> should be
	 *         included
	 */
	public final boolean accept(final File file) {
		if (file.isDirectory()) {
			return true;
		}
		String path = file.getAbsolutePath();
		String extension = path.substring(path.lastIndexOf(DOT) + 1);
		if (ExtensionsModel.getInstance().getExtensions()
				.contains(extension.toUpperCase())) {
			return true;
		}
		return false;
	}
}
