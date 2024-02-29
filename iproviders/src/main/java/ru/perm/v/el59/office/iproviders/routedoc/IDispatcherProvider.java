package ru.perm.v.el59.office.iproviders.routedoc;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.routedoc.Dispatcher;

public interface IDispatcherProvider extends IGenericDao<Dispatcher, Long> {
   List<Dispatcher> getAll();

   Dispatcher getDefaultDispatcher();
}
