package ru.perm.v.el59.office.util;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ExcelReport {

	/**
	 * Генерация excel отчета
	 * 
	 * @param inputFile
	 * @param beans
	 * @return
	 * @throws IOException
	 */
	public byte[] build(String inputFile, Map<String, Object> beans)
			throws IOException, InvalidFormatException {
//		XLSTransformer transformer = new XLSTransformer();
//		File tmpFile = getTempXlsFile();
//		transformer.transformXLS(inputFile, beans, tmpFile.getPath());
//		byte[] data = FileUtils.readFileToByteArray(tmpFile);
		byte[] data = "".getBytes();
		return data;
	}

	private File getTempXlsFile() throws IOException {
		File tmpFile = File.createTempFile("out", ".xls");
		tmpFile.deleteOnExit();
//		ret = tmpFile.getPath();
		return tmpFile;
	}
}
