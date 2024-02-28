package ru.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.GroupContragent;

public interface IGroupContragentProvider extends IGenericDao<GroupContragent, Long> {
   List<GroupContragent> getAll();
}
