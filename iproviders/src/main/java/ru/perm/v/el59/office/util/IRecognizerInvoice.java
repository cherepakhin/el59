package ru.perm.v.el59.office.util;

import java.util.List;
import ru.perm.v.el59.office.db.Doc;
import ru.perm.v.el59.office.db.dto.TTovar;

public interface IRecognizerInvoice {
   List<TTovar> fillListTTovar(List<TTovar> var1);

   List<TTovar> recognize(List<TTovar> var1, Doc var2) throws Exception;

   int getCounter();
}
