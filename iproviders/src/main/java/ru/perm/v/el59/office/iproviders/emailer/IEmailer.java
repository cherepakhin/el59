package ru.perm.v.el59.office.iproviders.emailer;

import java.util.List;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.dto.FileAttach;

public interface IEmailer {
   String ORDER = "order";
   String REESTR = "reestr";
   String FORDBF = "fordbf";
   String PRICE = "price";
   String WEB = "web";

   String send(Manager var1, String var2, String var3, String var4, List<FileAttach> var5);
}
