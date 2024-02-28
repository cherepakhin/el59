package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.TypeErr;

public interface ITypeErrProvider extends IGenericDao<TypeErr, Long> {
   List<ru.perm.v.el59.office.db.TypeErr> getAll();
}
