package ru.perm.v.el59.office.iproviders.service;

import java.util.List;
import ru.el59.office.db.service.ITDoc;
import ru.el59.office.db.service.LOPT;
import ru.el59.office.db.service.TDoc;
import ru.perm.v.el59.office.iproviders.RequestMessage;

public interface IControllerTDoc {
   List<TDoc> getChilds(TDoc var1);

   RequestMessage getRequestMessage(LOPT var1, RequestMessage var2, String var3) throws Exception;

   ITDoc loadContent(TDoc var1);

   void delete(LOPT var1, ITDoc var2) throws Exception;
}
