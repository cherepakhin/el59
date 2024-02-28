package ru.perm.v.el59.office.iproviders.plan;

import java.util.List;
import ru.perm.v.el59.office.critery.PlanCritery;
import ru.el59.office.db.Move;
import ru.el59.office.db.plan.Plan;
import ru.el59.office.db.plan.UserZP;

public interface IControllerZP {
   void calc(PlanCritery var1) throws Exception;

   void get(Plan var1);

   List<Move> getMoveUser(UserZP var1);

   List<UserZP> loadTabel(List<UserZP> var1);
}
