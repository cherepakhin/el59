package ru.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.TypeFile;

public interface ITypeFileProvider extends IGenericDao<TypeFile, Long> {
   TypeFile getNullTypeFile();

   List<TypeFile> getAll();
}
