package ru.perm.v.el59.office.iproviders.web;

import java.math.BigDecimal;
import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.web.DocW;
import ru.perm.v.el59.office.db.web.DocWInfo;
import ru.perm.v.el59.office.shopmodel.DocTitle;

public interface IDocWProvider extends IGenericDao<DocW, Long> {
   void recalc(DocW var1) throws Exception;

   List<DocWInfo> getDocWInfo(DocWCritery var1);

   DocTitle getDocTitleByDocW(DocW var1);

   BigDecimal getPay(DocW var1);

   List<DocWInfo> getOnlyOpen(List<Shop> var1);
}
