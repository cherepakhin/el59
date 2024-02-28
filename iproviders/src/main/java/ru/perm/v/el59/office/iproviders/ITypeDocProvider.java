package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.TypeDoc;

public interface ITypeDocProvider extends IGenericDao<TypeDoc, Long> {
   List<TypeDoc> getAll();
}
