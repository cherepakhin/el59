package ru.perm.v.el59.office.dozer.converter;

import org.dozer.CustomConverter;
import ru.perm.v.el59.office.util.Helper;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ConverterStringToBigDecimal implements CustomConverter {
	DecimalFormat df = new DecimalFormat("0.00");

	public Object convert(Object destination, Object source, Class destClass,
			Class sourceClass) {
		if (source == null) {
			return null;
		}
		BigDecimal ret = Helper.getBigDecimal(source);
		return ret;
	}
}
