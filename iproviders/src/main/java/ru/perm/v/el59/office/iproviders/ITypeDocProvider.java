package ru.perm.v.el59.office.iproviders;

import ru.perm.v.dao.IGenericDao;
import ru.perm.v.el59.office.db.TypeDoc;

import java.util.List;

public interface ITypeDocProvider extends IGenericDao<TypeDoc, Long> {
   List<ru.perm.v.el59.office.db.TypeDoc> getAll();
}
