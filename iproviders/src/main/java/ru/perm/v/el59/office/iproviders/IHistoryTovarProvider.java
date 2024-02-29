package ru.perm.v.el59.office.iproviders;

import java.util.Date;
import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.HistoryTovar;
import ru.perm.v.el59.office.db.dto.ChangeGroup;

public interface IHistoryTovarProvider extends IGenericDao<HistoryTovar, Long> {
   List<ChangeGroup> getChangeGroup(Date var1, Date var2);
}
