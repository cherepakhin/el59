package ru.perm.v.el59.office.iproviders;

import java.util.List;
import java.util.Map;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.TypeStock;

public interface ITypeStockProvider extends IGenericDao<TypeStock, Long> {
   List<TypeStock> getAll();

   Map<Long, TypeStock> getAllAsHashBestChr();
}
