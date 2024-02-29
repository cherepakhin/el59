package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.Brand;

public interface IBrandProvider extends IGenericDao<Brand, Long> {
   List<Brand> getAll();
}
