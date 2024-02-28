package ru.perm.v.el59.office.dozer.converter;

import org.dozer.CustomConverter;

public class ConverterStringYNToBoolean implements CustomConverter {
	public Object convert(Object destination, Object source, Class destClass,
			Class sourceClass) {
		if (source == null) {
			return null;
		}
		if (source instanceof String) {
			String s = (String) source;
			return s.toUpperCase().equals("Y");
		}
		return null;
	}
}
