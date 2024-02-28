package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Brand;

public interface IBrandProvider extends IGenericDao<Brand, Long> {
   List<Brand> getAll();
}
