package ru.el59.office.iproviders.web;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.DocItem;
import ru.el59.office.db.Shop;
import ru.el59.office.db.web.DocW;
import ru.el59.office.db.web.DocWItem;
import ru.el59.office.db.web.DocWItemInfo;
import ru.el59.office.shopmodel.DocDetail;

public interface IDocWItemProvider extends IGenericDao<DocWItem, Long> {
   void deleteByDocW(DocW var1);

   List<DocWItemInfo> getDocWItemInfo(DocWCritery var1);

   void linkToDocWItem(DocDetail var1) throws Exception;

   DocWItem getNullDocWItem();

   List<DocWItemInfo> getListDocWItemInfo(List<DocWItem> var1);

   void unLinkDocWItem(List<DocItem> var1);

   List<DocWItemInfo> getOnlyOpen(Shop var1);

   List<DocWItem> getInitializedByCritery(DocWItemCritery var1);
}
