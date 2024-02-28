package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.shopmodel.TypePayment;

public interface ITypePaymentProvider extends IGenericDao<TypePayment, Long> {
   List<TypePayment> getAll();
}
