package ru.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.SetTovar;
import ru.el59.office.db.Tovar;

public interface ISetTovarProvider extends IGenericDao<SetTovar, Long> {
   List<Tovar> loadByName(String var1);

   List<SetTovar> getAll();

   void addTovarNnum(SetTovar var1, Integer var2) throws Exception;

   void removeByNnum(SetTovar var1, Integer var2) throws Exception;

   void addTovar(SetTovar var1, Tovar var2) throws Exception;

   void removeTovar(SetTovar var1, Tovar var2) throws Exception;
}
