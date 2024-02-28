package ru.perm.v.el59.office.iproviders;


import ru.el59.office.db.TypeFile;
import ru.perm.v.el59.dao.IGenericDao;

import java.util.List;

public interface ITypeFileProvider extends IGenericDao<TypeFile, Long> {
   TypeFile getNullTypeFile();

   List<ru.perm.v.el59.office.db.TypeFile> getAll();
}
