package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.CauseNoPay;

public interface ICauseNoPayProvider extends IGenericDao<CauseNoPay, Long> {
   List<CauseNoPay> getCauseNoPayAll();
}
