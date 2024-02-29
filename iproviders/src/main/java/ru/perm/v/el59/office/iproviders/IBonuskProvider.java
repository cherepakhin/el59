package ru.perm.v.el59.office.iproviders;

import java.util.List;
//import ru.perm.v.el59.dao.IGenericDao;
//import ru.perm.v.el59.office.db.BonusK;
//import ru.perm.v.el59.office.db.BonusK;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.BonusK;
//import ru.perm.v.el59.office.;

public interface IBonuskProvider extends IGenericDao<BonusK, Long> {
   List<BonusK> getAll();
}
