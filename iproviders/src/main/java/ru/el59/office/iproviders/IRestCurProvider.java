package ru.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.GroupTovar;
import ru.el59.office.db.RestCur;
import ru.el59.office.db.Shop;
import ru.el59.office.db.SummaInOut;

public interface IRestCurProvider extends IGenericDao<RestCur, Long> {
   SummaInOut getSummaInOut(Shop var1, List<GroupTovar> var2);

   List<Integer> getDistinctListNnum();

   void execproc(String var1);
}
