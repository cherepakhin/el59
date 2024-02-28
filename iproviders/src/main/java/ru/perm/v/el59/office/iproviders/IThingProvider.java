package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Thing;

public interface IThingProvider extends IGenericDao<Thing, Long> {
   List<Thing> getAll();

   String createFullDescription(String var1);
}
