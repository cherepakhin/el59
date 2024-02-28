package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.perm.v.el59.office.critery.DocDetailCritery;
import ru.el59.office.db.Shop;
import ru.el59.office.shopmodel.DocDetail;
import ru.el59.office.shopmodel.DocTitle;
import ru.perm.v.el59.dto.DocDetailDTO;
public interface IDocDetailProvider extends IGenericDao<DocDetail, Long> {
   DocDetail getByDTO(DocDetailDTO var1, Shop var2);

   DocDetail getByDTO(DocDetailDTO var1, String var2);

   List<DocDetail> getByDocTitle(DocTitle var1);

   List<DocDetail> getControlPrice(DocDetailCritery var1);
}
