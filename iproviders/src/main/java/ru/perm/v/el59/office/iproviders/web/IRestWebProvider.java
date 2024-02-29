package ru.perm.v.el59.office.iproviders.web;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.db.web.RestWeb;
import ru.perm.v.el59.office.db.web.TypeSite;

public interface IRestWebProvider extends IGenericDao<RestWeb, Long> {
   List<Tovar> getListTovarForDelete();

   void addTovarForDelete(Tovar var1) throws Exception;

   void addTovarForDelete(List<Tovar> var1) throws Exception;

   void removeTovarForDelete(List<Tovar> var1) throws Exception;

   void removeTovarForDelete(Tovar var1) throws Exception;

   List<RestWeb> getListForSite(TypeSite var1);

   List<Integer> getListLiderSaleForSite();
}
