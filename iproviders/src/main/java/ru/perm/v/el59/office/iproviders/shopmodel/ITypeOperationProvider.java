package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
//import ru.perm.v.el59.office.shopmodel.TypeOperation;
import ru.perm.v.el59.office.shopmodel.TypeOperation;

public interface ITypeOperationProvider extends IGenericDao<TypeOperation, Long> {
   List<TypeOperation> getAll();
}
