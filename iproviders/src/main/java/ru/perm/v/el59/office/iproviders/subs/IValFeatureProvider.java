package ru.perm.v.el59.office.iproviders.subs;

import ru.el59.dao.IGenericDao;
import ru.el59.office.db.subs.CorrectionVal;
import ru.el59.office.db.subs.ValFeature;

public interface IValFeatureProvider extends IGenericDao<ValFeature, Long> {
   CorrectionVal addCorrectionVal(ValFeature var1, String var2) throws Exception;

   void delCorrectionVal(ValFeature var1, String var2) throws Exception;

   ValFeature init(Long var1);

   boolean existCorrectName(ValFeature var1, String var2);
}
