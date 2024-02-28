package ru.perm.v.el59.office.iproviders.shopmodel;

import java.math.BigDecimal;
import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.shopmodel.TovarBonus;

public interface ITovarBonusProvider extends IGenericDao<TovarBonus, Long> {
   void deleteList(List<TovarBonus> var1) throws Exception;

   void create(List<Integer> var1, BigDecimal var2) throws Exception;
}
