package ru.perm.v.el59.office.iproviders;

import java.util.Date;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.HistoryTag;
import ru.el59.office.db.Shop;
import ru.el59.office.db.Tovar;

public interface IHistoryTagProvider extends IGenericDao<HistoryTag, Long> {
   HistoryTag getByTovarShopDate(Tovar tovar, Shop shop, Date ddate);
   HistoryTag getByNnumShopCodDate(Integer nnum, Shop shop, Date ddate);
}
