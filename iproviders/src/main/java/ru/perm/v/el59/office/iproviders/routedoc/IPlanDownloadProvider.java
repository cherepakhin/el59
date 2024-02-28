package ru.perm.v.el59.office.iproviders.routedoc;

import java.util.Date;
import java.util.List;
import java.util.Map;
import ru.el59.dao.IGenericDao;
import ru.perm.v.el59.office.iproviders.critery.PlanDownloadCritery;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.GroupContragent;
import ru.el59.office.db.Manager;
import ru.el59.office.db.Shop;
import ru.el59.office.db.routedoc.CrossPlanDownload;
import ru.el59.office.db.routedoc.PlanDownload;

public interface IPlanDownloadProvider extends IGenericDao<PlanDownload, Long> {
   List<PlanDownload> getInPeriod(Date var1, List<Shop> var2);

   List<GroupContragent> getListGroupContragent();

   boolean isExist(PlanDownload var1);

   List<PlanDownload> getLastPlanDownload();

   List<PlanDownload> getPrevLastPlanDownload(List<PlanDownload> var1);

   void createForAllShop(Date var1, Manager var2) throws Exception;

   void delete(List<CrossPlanDownload> var1) throws Exception;

   List<Date> getLastDate(Integer var1);

   String checkDelete(List<CrossPlanDownload> var1);

   PlanDownload getBy(Shop var1, Date var2, Contragent var3);

   Map<Shop, Map<GroupContragent, PlanDownload>> getPivot(PlanDownloadCritery var1) throws Exception;
}
