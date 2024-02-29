package ru.perm.v.el59.office.iproviders.subs;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.subs.CorrectionName;
import ru.perm.v.el59.office.db.subs.MainFeature;

public interface ICorrectionNameProvider extends IGenericDao<CorrectionName, Long> {
   List<String> getPossible(MainFeature var1);
}
