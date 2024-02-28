package ru.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.shopmodel.Discount;

public interface IDiscountProvider extends IGenericDao<Discount, Long> {
   List<Discount> getWorkedToday(Boolean var1);
}
