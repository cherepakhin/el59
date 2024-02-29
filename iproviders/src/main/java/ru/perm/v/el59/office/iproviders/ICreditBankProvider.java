package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.CreditBank;

public interface ICreditBankProvider extends IGenericDao<CreditBank, Long> {
   List<CreditBank> getAll();
}
