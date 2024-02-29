package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.Manager;

public interface IManagerProvider extends IGenericDao<Manager, Long> {
   List<String> getUsers();

   Manager checkPassword(String var1, String var2);

   Manager getNullManager();

   List<Manager> getWorked();
}
