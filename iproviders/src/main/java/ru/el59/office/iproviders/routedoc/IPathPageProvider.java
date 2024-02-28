package ru.el59.office.iproviders.routedoc;

import java.util.Date;
import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Doc;
import ru.el59.office.db.Manager;
import ru.el59.office.db.routedoc.PathPage;
import ru.el59.office.db.routedoc.RouteJob;
import ru.el59.office.db.routedoc.TemplatePathPage;

public interface IPathPageProvider extends IGenericDao<PathPage, Long> {
   List<PathPage> getInPeriod(Date var1, Date var2);

   PathPage deleteRouteJobFromPathPage(RouteJob var1, PathPage var2) throws Exception;

   List<RouteJob> getRouteJob(PathPage var1);

   PathPage createFromListDoc(List<Doc> var1, Manager var2) throws Exception;

   PathPage addDoc(List<Doc> var1, PathPage var2) throws Exception;

   PathPage addRouteJobToPathPage(RouteJob var1, PathPage var2) throws Exception;

   void up(RouteJob var1) throws Exception;

   void down(RouteJob var1) throws Exception;

   void createByTemplateWorked(Manager var1) throws Exception;

   PathPage createByTemplate(TemplatePathPage var1, Manager var2) throws Exception;

   void createInCurrentPathPage(Doc var1) throws Exception;

   void delete(Doc var1) throws Exception;

   boolean checkExist(PathPage var1);
}
