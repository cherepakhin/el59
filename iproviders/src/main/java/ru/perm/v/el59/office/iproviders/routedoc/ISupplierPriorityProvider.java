package ru.perm.v.el59.office.iproviders.routedoc;

import java.util.List;
import java.util.Map;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.routedoc.SupplierPriority;

public interface ISupplierPriorityProvider extends IGenericDao<SupplierPriority, Long> {
   List<SupplierPriority> getAll();

   Map<Contragent, SupplierPriority> getHashAll();

   List<Contragent> getContragents(List<Contragent> var1);
}
