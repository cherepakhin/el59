package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.shopmodel.PodCardRange;

public interface IPodCardRangeProvider extends IGenericDao<PodCardRange, Long> {
   List<PodCardRange> getAll();
}
