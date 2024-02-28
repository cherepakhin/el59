package ru.perm.v.el59.office.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class Helper {
	private final static Logger LOG = Logger.getLogger(Helper.class); 
	private static SimpleDateFormat sdf;

	public static Date getNullHour(Date ddate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ddate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static byte[] getImageData(String pathImage, String basePath)
			throws IOException {
		if (!org.apache.commons.lang.StringUtils.isEmpty(pathImage)
				&& !org.apache.commons.lang.StringUtils.isEmpty(basePath)) {
			if (!basePath.endsWith(File.separator)) {
				basePath = basePath + File.separator;
			}
			String fullFileName = basePath + pathImage;
			byte[] data = FileUtils.readFileToByteArray(new File(fullFileName));
			return data;
		} else {
			byte[] data = {};
			return data;
		}
	}

	public static Date getNullDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2001);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();

	}

	public static SimpleDateFormat getDateFornmatter() {
		if (sdf == null) {
			if (sdf == null)
				sdf = new SimpleDateFormat("dd.MM.yyyy");
		}
		return sdf;
	}

	public static String normalizeStringForNumber(String str) {
		str=str.replaceAll("\u00a0","");
		str=str.replaceAll(" ","");
		str=str.replaceAll(",",".");
		return str;
	}

	public static BigDecimal getBigDecimal(Object value) {
		BigDecimal newValue = new BigDecimal("0.00");
		try {
			newValue = new BigDecimal(normalizeStringForNumber((String) value));
		} catch (Exception e) {
			LOG.error("Failed convert to BigDecimal"+value);
			LOG.error(e);
			e.printStackTrace();
		}
		return newValue;
	}
	
	/**
	 * Удаляет с конца 00. Нужно для групп.
	 * @param cod
	 * @return
	 */
	public static String clear00(String cod) {
		if(cod.endsWith("00")) {
			int pos = cod.lastIndexOf("00");
			cod=cod.substring(0, pos);
			cod=clear00(cod);
		}
		return cod;
	}
}
