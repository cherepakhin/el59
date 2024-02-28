package ru.perm.v.el59.office.iproviders.routedoc;

import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Doc;
import ru.el59.office.db.routedoc.RouteJob;

public interface IRouteJobProvider extends IGenericDao<RouteJob, Long> {
   void deleteByDoc(Doc var1) throws Exception;
}
