package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.Dolgnost;

public interface IDolgnostProvider extends IGenericDao<Dolgnost, Long> {
   List<Dolgnost> getAll();
}
