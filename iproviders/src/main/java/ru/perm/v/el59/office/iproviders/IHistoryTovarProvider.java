package ru.perm.v.el59.office.iproviders;

import java.util.Date;
import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.HistoryTovar;
import ru.el59.office.db.dto.ChangeGroup;

public interface IHistoryTovarProvider extends IGenericDao<HistoryTovar, Long> {
   List<ChangeGroup> getChangeGroup(Date var1, Date var2);
}
