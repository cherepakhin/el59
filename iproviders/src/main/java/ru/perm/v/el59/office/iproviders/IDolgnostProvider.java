package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Dolgnost;

public interface IDolgnostProvider extends IGenericDao<Dolgnost, Long> {
   List<Dolgnost> getAll();
}
