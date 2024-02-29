package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.shopmodel.Discount;

public interface IDiscountProvider extends IGenericDao<Discount, Long> {
   List<Discount> getWorkedToday(Boolean var1);
}
