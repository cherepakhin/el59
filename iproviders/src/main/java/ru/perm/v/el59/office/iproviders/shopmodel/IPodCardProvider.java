package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.Date;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Shop;
import ru.el59.office.shopmodel.PodCard;

public interface IPodCardProvider extends IGenericDao<PodCard, Long> {
   void create(Shop var1, Date var2, Integer var3, String var4) throws Exception;

   PodCard getByStroke(String var1);
}
