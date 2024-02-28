package ru.el59.office.iproviders.routedoc;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.routedoc.Driver;

public interface IDriverProvider extends IGenericDao<Driver, Long> {
   List<Driver> getAll();

   Driver getDefaultDriver();
}
