package ru.perm.v.el59.office.iproviders;

import java.io.IOException;
import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Analog;
import ru.el59.office.db.Manager;
import ru.el59.office.db.Tovar;
import ru.el59.office.db.dto.TTovar;

public interface IAnalogProvider extends IGenericDao<Analog, Long> {
   Tovar getByNameAnalog(String var1);

   TTovar fillTTovar(TTovar var1) throws IOException;

   void change(String var1, Tovar var2, Manager var3) throws Exception;

   List<TTovar> fillListTTovar(List<TTovar> var1) throws IOException;

   void incrementCounter();

   void resetCounter();

   int getCounter();

   void recreateIndex() throws IOException;
}
