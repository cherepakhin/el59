package ru.perm.v.el59.office.iproviders;

import bsh.EvalError;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Formula;
import ru.el59.office.db.Price;
import ru.el59.office.db.PriceType;
import ru.el59.office.db.Shop;
import ru.el59.office.db.dto.PriceDbf;

public interface IPriceProvider extends Serializable, IGenericDao<Price, Long> {
   Price update(PriceType var1, Integer var2, BigDecimal var3) throws Exception;

   Price update(String var1, Integer var2, BigDecimal var3) throws Exception;

   String getNameDefaultPrice();

   void save(List<Price> var1) throws Exception;

   String getNamePriceW();

   List<Price> getPrice(Shop var1, Date var2, Date var3);

   Integer setToZeroOldLocalPrice();

   List<Price> getPrice(PriceType var1, Date var2, Date var3);

   List<PriceDbf> getPriceDbf(PriceType var1, Shop var2, Date var3, Date var4, Formula var5) throws EvalError;

   void update(Price var1, BigDecimal var2) throws Exception;
}
