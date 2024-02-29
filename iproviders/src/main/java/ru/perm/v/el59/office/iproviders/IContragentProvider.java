package ru.perm.v.el59.office.iproviders;

import java.util.List;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.dto.ContragentDTO;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.GroupContragent;
import ru.perm.v.el59.office.db.Shop;

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
