package ru.perm.v.el59.office.iproviders.web;

import java.util.Date;
import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.web.DocWItem;
import ru.el59.office.db.web.RouteDoc;

public interface IRouteDocProvider extends IGenericDao<RouteDoc, Long> {
   List<RouteDoc> getInPeriod(Date var1, Date var2);

   void deleteDocWItemFromRouteDoc(DocWItem var1);
}
