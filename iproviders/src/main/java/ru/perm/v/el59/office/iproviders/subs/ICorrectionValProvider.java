package ru.perm.v.el59.office.iproviders.subs;

import java.util.List;
import java.util.Set;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.subs.CorrectionVal;
import ru.perm.v.el59.office.db.subs.ValFeature;

public interface ICorrectionValProvider extends IGenericDao<CorrectionVal, Long> {
   void delete(Set<CorrectionVal> var1) throws Exception;

   List<String> getPossible(ValFeature var1);
}
