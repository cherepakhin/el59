package ru.perm.v.el59.office.iproviders.plan;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.plan.Plan;
import ru.perm.v.el59.office.db.plan.TDocTabel;

public interface ITDocTabelProvider extends IGenericDao<TDocTabel, Long> {
   TDocTabel create(TDocTabel var1, Manager var2) throws Exception;

   List<TDocTabel> getByPlan(Plan var1);
}
