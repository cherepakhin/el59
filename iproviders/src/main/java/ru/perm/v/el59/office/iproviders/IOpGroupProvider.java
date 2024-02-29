package ru.perm.v.el59.office.iproviders;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.OpGroup;

public interface IOpGroupProvider extends IGenericDao<OpGroup, Long> {
   String[] getAsStringArray();
}
