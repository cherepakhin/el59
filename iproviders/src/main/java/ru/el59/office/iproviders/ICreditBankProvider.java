package ru.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.CreditBank;

public interface ICreditBankProvider extends IGenericDao<CreditBank, Long> {
   List<CreditBank> getAll();
}
