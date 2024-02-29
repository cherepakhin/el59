package ru.perm.v.el59.office.iproviders;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.HistoryTag;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.Tovar;

import java.util.Date;
//import ru.perm.v.el59.dao.IGenericDao;
//import ru.perm.v.el59.office.db.HistoryTag; ru.perm.v.el59 ru.perm.v.el59
//import ru.perm.v.el59.office.db.Shop;
//import ru.perm.v.el59.office.db.Tovar;

public interface IHistoryTagProvider extends IGenericDao<HistoryTag, Long> {
   HistoryTag getByTovarShopDate(Tovar tovar, Shop shop, Date ddate);
   HistoryTag getByNnumShopCodDate(Integer nnum, Shop shop, Date ddate);
}
