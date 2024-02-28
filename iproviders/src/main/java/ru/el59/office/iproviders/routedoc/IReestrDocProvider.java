package ru.el59.office.iproviders.routedoc;

import ru.el59.dao.IGenericDao;
import ru.el59.office.db.DocFile;
import ru.el59.office.db.Manager;
import ru.el59.office.db.routedoc.ReestrDoc;

public interface IReestrDocProvider extends IGenericDao<ReestrDoc, Long> {
   String pay(ReestrDoc var1, Boolean var2, Manager var3) throws Exception;

   String agree(ReestrDoc var1, Boolean var2, Manager var3) throws Exception;

   String delete(ReestrDoc var1, Manager var2) throws Exception;

   ReestrDoc initListDocFile(ReestrDoc var1);

   ReestrDoc addDocFile(ReestrDoc var1, DocFile var2) throws Exception;

   ReestrDoc removeDocFile(ReestrDoc var1, DocFile var2) throws Exception;

   void removeDocFile(DocFile var1) throws Exception;
}
