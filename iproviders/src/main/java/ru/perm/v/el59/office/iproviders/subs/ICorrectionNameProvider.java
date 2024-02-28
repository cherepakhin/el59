package ru.perm.v.el59.office.iproviders.subs;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.subs.CorrectionName;
import ru.el59.office.db.subs.MainFeature;

public interface ICorrectionNameProvider extends IGenericDao<CorrectionName, Long> {
   List<String> getPossible(MainFeature var1);
}
