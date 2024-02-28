package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.PriceType;
import ru.el59.office.db.Shop;

public interface IPriceTypeProvider extends IGenericDao<PriceType, Long> {
   List<PriceType> getAll();

   List<PriceType> getIsBase(Boolean var1);

   PriceType init(PriceType var1);

   List<PriceType> getByShop(List<Shop> var1);

   List<PriceType> init(List<PriceType> var1);
}
