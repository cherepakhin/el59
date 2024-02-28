package ru.perm.v.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.Price;
import ru.el59.office.db.RestSupplier;
import ru.el59.office.db.RestXls;
import ru.el59.office.db.web.SummarySite;
import ru.perm.v.el59.office.exception.MessageException;

public interface IRestSupplierProvider extends IGenericDao<RestSupplier, Long> {
   List<Integer> getDistinctListNnum();

   List<RestXls> loadFromXls(List<RestXls> var1, Contragent var2) throws MessageException, Exception;

   List<SummarySite> getSummarySite();

   List<Price> changeCenaIn(List<Price> var1);

   void clearAllRest();

   List<Contragent> getCurrentSuppliers();
}
