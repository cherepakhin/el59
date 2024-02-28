package ru.el59.office.iproviders;

import java.util.Date;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.HistoryPrice;

public interface IHistoryPriceProvider extends IGenericDao<HistoryPrice, Long> {
   HistoryPrice getByTovarShopDate(Integer var1, String var2, Date var3) throws Exception;
}
