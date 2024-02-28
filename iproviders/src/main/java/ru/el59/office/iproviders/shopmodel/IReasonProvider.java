package ru.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.shopmodel.Reason;
import ru.el59.office.shopmodel.TypePayment;

public interface IReasonProvider extends IGenericDao<Reason, Long> {
   List<Reason> getAll();

   List<TypePayment> getRealReason();
}
