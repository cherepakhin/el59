package ru.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.BonusK;

public interface IBonuskProvider extends IGenericDao<BonusK, Long> {
   List<BonusK> getAll();
}
