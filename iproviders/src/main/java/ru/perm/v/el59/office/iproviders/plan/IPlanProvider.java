package ru.perm.v.el59.office.iproviders.plan;

import java.util.Date;
import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.perm.v.el59.office.critery.MoveCritery;
import ru.perm.v.el59.office.critery.PlanCritery;
import ru.el59.office.db.Move;
import ru.el59.office.db.Shop;
import ru.el59.office.db.SummaInOut;
import ru.el59.office.db.plan.Plan;
import ru.el59.office.db.plan.UptRptAsp;

public interface IPlanProvider extends IGenericDao<Plan, Long> {
   List<Plan> createForAllShop(int var1, int var2) throws Exception;

   Plan fillFact(PlanCritery var1) throws Exception;

   List<Integer> getExcludeNnumFromZP();

   List<String> getExcludeForSeller();

   List<String> getExcludeGroupTovarForAccPDS();

   SummaInOut getSumFact(MoveCritery var1);

   List<Move> getMovePlan(Plan var1) throws Exception;

   List<UptRptAsp> getUptRptAsp(Date var1, Date var2, List<Shop> var3);
}
