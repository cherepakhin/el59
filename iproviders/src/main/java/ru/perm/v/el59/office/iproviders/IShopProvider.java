package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.Shop;

public interface IShopProvider extends IGenericDao<Shop, String> {
   Shop getNullShop() throws Exception;

   List<Shop> getWorkedShop();

   List<Shop> getManagedShop(Manager var1);
}
