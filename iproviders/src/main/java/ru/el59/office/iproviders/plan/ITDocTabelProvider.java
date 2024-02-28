package ru.el59.office.iproviders.plan;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Manager;
import ru.el59.office.db.plan.Plan;
import ru.el59.office.db.plan.TDocTabel;

public interface ITDocTabelProvider extends IGenericDao<TDocTabel, Long> {
   TDocTabel create(TDocTabel var1, Manager var2) throws Exception;

   List<TDocTabel> getByPlan(Plan var1);
}
