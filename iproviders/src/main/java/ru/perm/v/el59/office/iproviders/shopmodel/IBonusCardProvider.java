package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.Date;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.shopmodel.BonusCard;

public interface IBonusCardProvider extends IGenericDao<BonusCard, Long> {
   void create(Shop var1, Date var2, Integer var3, String var4) throws Exception;

   BonusCard getByStroke(String var1);
}
