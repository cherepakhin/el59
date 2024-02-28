package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.shopmodel.Expense;

public interface IExpenseProvider extends IGenericDao<Expense, Long> {
   List<Expense> getAll();

   List<Expense> getPositive();

   List<Expense> getNegative();
}
