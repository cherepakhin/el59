package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.shopmodel.Reason;
import ru.perm.v.el59.office.shopmodel.TypePayment;

public interface IReasonProvider extends IGenericDao<Reason, Long> {
   List<Reason> getAll();

   List<TypePayment> getRealReason();
}
