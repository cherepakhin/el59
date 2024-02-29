package ru.perm.v.el59.office.dozer.converter;

import org.dozer.CustomConverter;
import ru.perm.v.el59.office.db.Tovar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ConverterListTovarToListInteger implements CustomConverter {
	public Object convert(Object destination, Object source, Class destClass,
			Class sourceClass) {
		if (source == null) {
			return null;
		}
		if (source instanceof Collection) {
			Collection list = (Collection) source;
			List<Integer> ret = new ArrayList<Integer>();
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Tovar tovar = (Tovar) iter.next();
				ret.add(tovar.getNnum());
			}
			return ret;
		}
		return null;
	}
}
