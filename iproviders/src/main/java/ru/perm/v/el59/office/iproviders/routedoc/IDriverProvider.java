package ru.perm.v.el59.office.iproviders.routedoc;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.routedoc.Driver;

public interface IDriverProvider extends IGenericDao<Driver, Long> {
   List<Driver> getAll();

   Driver getDefaultDriver();
}
