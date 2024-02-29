package ru.perm.v.el59.office.iproviders.shopmodel;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.dto.DocTitleDTO;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.shopmodel.DocTitle;

public interface IDocTitleProvider extends IGenericDao<DocTitle, Long> {
   DocTitle getByDTO(DocTitleDTO var1, Shop var2);

   DocTitle getByDTO(DocTitleDTO var1, String var2);

   String getNameOrderW();

   String getNameReal();

   String getNameOrder();

   DocTitle getNullDocTitle();

   String getNameInInvoice();

   String getNameOutInvoice();
}
