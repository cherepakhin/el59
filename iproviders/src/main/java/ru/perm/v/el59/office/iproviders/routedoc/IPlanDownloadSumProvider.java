package ru.perm.v.el59.office.iproviders.routedoc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.iproviders.critery.PlanDownloadCritery;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Doc;
import ru.perm.v.el59.office.db.DocFile;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.TypeFile;
import ru.perm.v.el59.office.db.routedoc.PlanDownload;
import ru.perm.v.el59.office.db.routedoc.PlanDownloadSum;

public interface IPlanDownloadSumProvider extends IGenericDao<PlanDownloadSum, Long> {
   PlanDownloadSum createPlanDownloadSum(Shop var1, Date var2, Contragent var3, Manager var4) throws Exception;

   void delete(CrossPlanDownloadSum var1, Contragent var2) throws Exception;

   int deleteForPlanDownload(PlanDownload var1) throws Exception;

   List<CrossPlanDownloadSum> getByPlanDownload(PlanDownloadCritery var1) throws Exception;

   void deleteDoc(Doc var1) throws Exception;

   PlanDownloadSum createPlanDownloadSum(Shop var1, PlanDownload var2, Contragent var3, Manager var4) throws Exception;

   PlanDownloadSum getPlanDownloadSum(Shop var1, PlanDownload var2, Contragent var3);

   void delete(Shop var1, PlanDownload var2, Contragent var3) throws Exception;

   DocFile addFile(Manager var1, Date var2, Shop var3, Contragent var4, boolean var5, String var6, BigDecimal var7, String var8, String var9, TypeFile var10, byte[] var11, boolean var12) throws Exception;
}
