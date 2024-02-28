package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.shopmodel.TypeOperation;

public interface ITypeOperationProvider extends IGenericDao<TypeOperation, Long> {
   List<TypeOperation> getAll();
}
