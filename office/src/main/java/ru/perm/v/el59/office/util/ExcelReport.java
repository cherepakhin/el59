package ru.perm.v.el59.office.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class ExcelReport {

	/**
	 * Генерация excel отчета
	 * 
	 * @param inputFile
	 * @param beans
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws ParsePropertyException
	 */
	public byte[] build(String inputFile, Map<String, Object> beans)
			throws IOException, ParsePropertyException, InvalidFormatException {
		XLSTransformer transformer = new XLSTransformer();
		File tmpFile = getTempXlsFile();
		transformer.transformXLS(inputFile, beans, tmpFile.getPath());
		byte[] data = FileUtils.readFileToByteArray(tmpFile);
		return data;
	}

	private File getTempXlsFile() throws IOException {
		File tmpFile = File.createTempFile("out", ".xls");
		tmpFile.deleteOnExit();
//		ret = tmpFile.getPath();
		return tmpFile;
	}
}
