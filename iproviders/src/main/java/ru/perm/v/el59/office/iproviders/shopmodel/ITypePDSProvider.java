package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.shopmodel.TypePDS;

public interface ITypePDSProvider extends IGenericDao<TypePDS, Long> {
   List<TypePDS> getAll();

   boolean isPDS(Integer var1);

   List<Integer> getListNnumPDS();
}
