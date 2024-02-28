package ru.perm.v.el59.office.iproviders;

import java.util.Map;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Operation;

public interface IOperationProvider extends IGenericDao<Operation, Long> {
   Map<String, Operation> getAllAsHashBestChr();

   Map<String, Operation> getHashChrOperation();
}
