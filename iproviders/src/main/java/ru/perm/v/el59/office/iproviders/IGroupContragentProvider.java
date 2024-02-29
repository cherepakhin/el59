package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.GroupContragent;

public interface IGroupContragentProvider extends IGenericDao<GroupContragent, Long> {
   List<GroupContragent> getAll();
}
