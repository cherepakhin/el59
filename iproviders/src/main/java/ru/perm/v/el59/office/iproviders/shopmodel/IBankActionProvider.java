package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.CreditBank;
import ru.el59.office.shopmodel.BankAction;

public interface IBankActionProvider extends IGenericDao<BankAction, Long> {
   List<BankAction> getAll(CreditBank var1);
}
