package ru.perm.v.el59.office.dao.impl;

import ru.perm.v.el59.dto.dao.CommonCritery;
import ru.el59.office.db.TypeFile;
import ru.perm.v.el59.dto.office.iproviders.ITypeFileProvider;

import java.util.List;

public class TypeFileProvider extends GenericDaoHibernateImpl<TypeFile, Long>
		implements ITypeFileProvider {

	private TypeFile nullTypeFile;

	public TypeFileProvider(Class<TypeFile> type) {
		super(type);
	}

	@Override
	public List<TypeFile> getAll() {
		return getByCritery(new CommonCritery(""));
	}

	@Override
	public TypeFile getNullTypeFile() {
		if (nullTypeFile == null) {
			nullTypeFile = read(0L);
			if (nullTypeFile == null) {
				nullTypeFile = new TypeFile();
				nullTypeFile.setN(0L);
				nullTypeFile.setName("");
				try {
					create(nullTypeFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return nullTypeFile;
	}

}
