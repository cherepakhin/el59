package ru.el59.office.iproviders.subs;

import java.util.List;
import java.util.Set;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.subs.CorrectionVal;
import ru.el59.office.db.subs.ValFeature;

public interface ICorrectionValProvider extends IGenericDao<CorrectionVal, Long> {
   void delete(Set<CorrectionVal> var1) throws Exception;

   List<String> getPossible(ValFeature var1);
}
