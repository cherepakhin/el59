package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.GroupTovar;
import ru.el59.office.db.SetGroupTovar;

public interface ISetGroupTovarProvider extends IGenericDao<SetGroupTovar, Long> {
   String getByCodesCritery(List<GroupTovar> var1);

   String[] getAsStringArray();

   List<SetGroupTovar> getAll();

   SetGroupTovar load(Long var1);
}
