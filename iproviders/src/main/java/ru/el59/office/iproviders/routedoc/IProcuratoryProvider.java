package ru.el59.office.iproviders.routedoc;

import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.Manager;
import ru.el59.office.db.Shop;
import ru.el59.office.db.routedoc.Procuratory;

public interface IProcuratoryProvider extends IGenericDao<Procuratory, Long> {
   Procuratory create(Shop var1, Contragent var2, Contragent var3, String var4, Integer var5, Manager var6) throws Exception;

   Integer getDefaultLimitationDays();

   Integer getNextNumberForShop(Shop var1);
}
