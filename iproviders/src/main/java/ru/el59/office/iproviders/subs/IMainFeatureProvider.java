package ru.el59.office.iproviders.subs;

import java.util.List;
import java.util.Set;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.GroupTovar;
import ru.el59.office.db.TovarInfo;
import ru.el59.office.db.subs.CorrectionName;
import ru.el59.office.db.subs.CorrectionVal;
import ru.el59.office.db.subs.MainFeature;
import ru.el59.office.db.subs.ValFeature;

public interface IMainFeatureProvider extends IGenericDao<MainFeature, Long> {
   MainFeature init(MainFeature var1);

   MainFeature createForGroupTovar(GroupTovar var1, String var2, String var3) throws Exception;

   MainFeature createForGroupTovar(GroupTovar var1, String var2, String var3, String var4, String var5, String var6, String var7) throws Exception;

   CorrectionName addCorrectionName(MainFeature var1, String var2) throws Exception;

   ValFeature addValFeature(MainFeature var1, String var2, String var3) throws Exception;

   void delValFeature(ValFeature var1) throws Exception;

   CorrectionVal addCorrectionVal(ValFeature var1, String var2) throws Exception;

   MainFeature getByGroupTovarAndCorrectinName(GroupTovar var1, String var2);

   MainFeature getByGroupTovarAndManFeatureName(GroupTovar var1, String var2);

   TovarInfo substituteMainFeature(TovarInfo var1) throws Exception;

   List<MainFeature> getMainFeatureForGroupTovar(GroupTovar var1) throws Exception;

   List<MainFeature> getUncomleateSubstities(TovarInfo var1) throws Exception;

   void deleteCorrectionName(CorrectionName var1) throws Exception;

   void delete(Set<MainFeature> var1) throws Exception;

   void deleteValFeature(Set<ValFeature> var1) throws Exception;

   void deleteCorrectionName(Set<CorrectionName> var1) throws Exception;

   Integer doSubsMainFeature(MainFeature var1);

   Integer doSubsMainFeature(GroupTovar var1) throws Exception;

   void down(MainFeature var1) throws Exception;

   void up(MainFeature var1) throws Exception;

   int resortFeaturesByListTovarInfo(List<TovarInfo> var1);

   int resortFeaturesByListNnnum(List<Integer> var1);

   boolean copyMainFeature(GroupTovar var1, GroupTovar var2, List<MainFeature> var3) throws Exception;
}
