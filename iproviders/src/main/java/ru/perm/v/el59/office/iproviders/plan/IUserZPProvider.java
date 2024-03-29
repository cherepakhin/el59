package ru.perm.v.el59.office.iproviders.plan;

import java.io.Serializable;
import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.plan.Plan;
import ru.perm.v.el59.office.db.plan.UserZP;

//import ru.perm.v.el59.office.db.plan.UserZP;

public interface IUserZPProvider extends IGenericDao<UserZP, Long> {
   List<UserZP> getByNameAndPlan(String var1, Plan var2);

   UserZP loadTabel(Serializable var1);

   List<UserZP> loadTabel(List<UserZP> var1);
}
