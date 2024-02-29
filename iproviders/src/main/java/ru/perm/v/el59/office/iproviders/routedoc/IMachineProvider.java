package ru.perm.v.el59.office.iproviders.routedoc;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.routedoc.Machine;

public interface IMachineProvider extends IGenericDao<Machine, Long> {
   List<Machine> getAll();

   Machine getDefaultMachine();
}
