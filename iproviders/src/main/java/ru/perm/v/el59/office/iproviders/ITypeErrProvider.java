package ru.perm.v.el59.office.iproviders;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.TypeErr;

import java.util.List;

public interface ITypeErrProvider extends IGenericDao<TypeErr, Long> {
   List<ru.perm.v.el59.office.db.TypeErr> getAll();
}
