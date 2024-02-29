package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.shopmodel.TypeCash;

public interface ITypeCashProvider extends IGenericDao<TypeCash, Long> {
   List<TypeCash> getAll();
}
