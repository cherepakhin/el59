package ru.perm.v.el59.office.iproviders.shopmodel;


import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.dto.DocDetailDTO;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.iproviders.critery.DocDetailCritery;
import ru.perm.v.el59.office.shopmodel.DocDetail;
import ru.perm.v.el59.office.shopmodel.DocTitle;

import java.util.List;

public interface IDocDetailProvider extends IGenericDao<DocDetail, Long> {
   DocDetail getByDTO(DocDetailDTO var1, Shop var2);

   DocDetail getByDTO(DocDetailDTO var1, String var2);

   List<DocDetail> getByDocTitle(DocTitle var1);

   List<DocDetail> getControlPrice(DocDetailCritery var1);
}
