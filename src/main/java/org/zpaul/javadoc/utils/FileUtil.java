package org.zpaul.javadoc.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static List<File> listFiles(File dir, FileFilter fileFilter, boolean includeSubDirectories) {
		List<File> result = new ArrayList<>(1024);
		innerListFiles(result, dir, fileFilter, includeSubDirectories);
		return result;
	}

	private static void innerListFiles(List<File> result, File directory, FileFilter fileFilter, boolean includeSubDirectories) {
		File[] files = directory.listFiles(fileFilter);
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					if (includeSubDirectories) {
						result.add(file);
					}
					innerListFiles(result, file, fileFilter, includeSubDirectories);
				} else {
					result.add(file);
				}
			}
		}
	}

	public static void writeFile(File file, String content) {
		if (content == null) {
			return;
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file, false);
			out.write(content.getBytes(StandardCharsets.UTF_8));
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}