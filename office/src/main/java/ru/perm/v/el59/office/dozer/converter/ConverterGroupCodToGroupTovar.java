package ru.perm.v.el59.office.dozer.converter;

import org.dozer.CustomConverter;
import ru.el59.office.db.GroupTovar;

public class ConverterGroupCodToGroupTovar implements CustomConverter {
	public Object convert(Object destination, Object source, Class destClass,
			Class sourceClass) {
		if (source == null) {
			return null;
		}
		if (source instanceof String) {
			String s = "0" + source;
			String ss = s.substring(0, s.length() - 2);
			ss = ss + "00";
			GroupTovar g = new GroupTovar();
			g.setCod(ss);
			return g;
		}
		return null;
	}
}
