package ru.perm.v.el59.office.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

public class UnZip {
	private static Logger LOGGER = Logger.getLogger(UnZip.class);

	public static final void writeFile(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);
		in.close();
		out.close();
	}

	/**
	 * String zipFileName = "C:/myFolder/myZip.zip"; String directoryToExtractTo
	 * = "C:/myFolder/unzipped/";
	 * 
	 * @param zipFileName
	 * @param directoryToExtractTo
	 */
	public static List<String> unzipMyZip(String zipFileName,
			String directoryToExtractTo) {
		Enumeration entriesEnum;
		ZipFile zipFile;
		List<String> ret = new ArrayList<String>();
		try {
			zipFile = new ZipFile(zipFileName);
			entriesEnum = zipFile.entries();

			File directory = new File(directoryToExtractTo);

			/**
			 * Check if the directory to extract to exists
			 */
			if (!directory.exists()) {
				/**
				 * If not, create a new one.
				 */
				new File(directoryToExtractTo).mkdir();
				LOGGER.info("...Directory Created -" + directoryToExtractTo);
			}
			while (entriesEnum.hasMoreElements()) {
				try {
					ZipEntry entry = (ZipEntry) entriesEnum.nextElement();

					if (entry.isDirectory()) {
						/**
						 * Currently not unzipping the directory structure. All
						 * the files will be unzipped in a Directory
						 * 
						 **/
					} else {
						LOGGER.info("Extracting file: " + entry.getName());
						/**
						 * The following logic will just extract the file name
						 * and discard the directory
						 */
						int index = 0;
						String name = entry.getName();
						index = entry.getName().lastIndexOf("/");
						if (index > 0 && index != name.length())
							name = entry.getName().substring(index + 1);

						LOGGER.info(name);
						LOGGER.info("Extracting " + directoryToExtractTo
								+ File.separator + name);
						ret.add(directoryToExtractTo + File.separator + name);
						writeFile(zipFile.getInputStream(entry),
								new BufferedOutputStream(new FileOutputStream(
										directoryToExtractTo + File.separator
												+ name)));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			zipFile.close();
		} catch (IOException ioe) {
			LOGGER.error("Some Exception Occurred:",ioe);
			ioe.printStackTrace();
			return null;
		}
		return ret;
	}
}
