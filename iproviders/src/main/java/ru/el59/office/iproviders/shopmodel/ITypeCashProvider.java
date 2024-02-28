package ru.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.shopmodel.TypeCash;

public interface ITypeCashProvider extends IGenericDao<TypeCash, Long> {
   List<TypeCash> getAll();
}
