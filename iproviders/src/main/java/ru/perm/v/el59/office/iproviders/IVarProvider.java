package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Var;

public interface IVarProvider extends IGenericDao<Var, Long> {
   Object setValue(String var1, Object var2) throws Exception;

   List<Var> getAll();

   Object getValue(String var1);
}
