package ru.perm.v.el59.office.iproviders.routedoc;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.routedoc.TemplatePathPage;

public interface ITemplatePathPageProvider extends IGenericDao<TemplatePathPage, Long> {
   List<TemplatePathPage> getAll();

   TemplatePathPage initialize(TemplatePathPage var1);

   TemplatePathPage addShop(TemplatePathPage var1, Shop var2) throws Exception;

   TemplatePathPage removeShop(TemplatePathPage var1, Shop var2) throws Exception;

   TemplatePathPage addContragent(TemplatePathPage var1, Contragent var2) throws Exception;

   TemplatePathPage removeContragent(TemplatePathPage var1, Contragent var2) throws Exception;

   List<TemplatePathPage> getWorked();
}
