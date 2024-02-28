package ru.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.GroupContragent;
import ru.el59.office.db.Shop;
import ru.perm.v.el59.dto.ContragentDTO;

public interface IContragentProvider extends IGenericDao<Contragent, Long> {
   List<Contragent> getAll(GroupContragent var1);

   Contragent getByDTO(ContragentDTO var1, Shop var2) throws Exception;

   List<Contragent> getSuppliers();

   List<Contragent> getSupplier(String var1);

   String getNameGroupSupplier();

   void addToGroupContragent(Contragent var1, String var2) throws Exception;

   void removeFromGroupContragent(Contragent var1, String var2) throws Exception;

   String getDefaultGroupContragent();
}
