package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.Doc;
import ru.perm.v.el59.office.db.DocItem;
import ru.perm.v.el59.office.db.PriceType;
import ru.perm.v.el59.office.db.dto.DocItemWithPrice;
import ru.perm.v.el59.office.db.dto.TTovar;

public interface IDocItemProvider extends IGenericDao<DocItem, Long> {
   void deleteByDoc(Doc var1);

   List<DocItemWithPrice> getDocItemForPrice(Doc var1, PriceType var2) throws Exception;

   DocItem getNullDocItem();

   void createByListTTovar(Doc var1, List<TTovar> var2) throws Exception;

   void delete(List<DocItem> var1) throws Exception;
}
