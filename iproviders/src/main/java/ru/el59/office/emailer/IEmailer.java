package ru.el59.office.emailer;

import java.util.List;
import ru.el59.office.db.Manager;
import ru.el59.office.db.dto.FileAttach;

public interface IEmailer {
   String ORDER = "order";
   String REESTR = "reestr";
   String FORDBF = "fordbf";
   String PRICE = "price";
   String WEB = "web";

   String send(Manager var1, String var2, String var3, String var4, List<FileAttach> var5);
}
