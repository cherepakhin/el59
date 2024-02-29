package ru.perm.v.el59.office.iproviders.plan;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.plan.TypeTabel;
import ru.perm.v.el59.office.db.plan.TypeTabelVal;

public interface ITypeTabelProvider extends IGenericDao<TypeTabel, Long> {
   List<TypeTabel> getAll();

   List<TypeTabelVal> getListModel(String var1);
}
