package ru.perm.v.el59.office.iproviders.shopmodel;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.shopmodel.Expense;

import java.util.List;
//import ru.perm.v.el59.dao.IGenericDao;import ru.perm.v.el59.dao.IGenericDao;
//import ru.perm.v.el59.office.shopmodel.Expense;

public interface IExpenseProvider extends IGenericDao<Expense, Long> {
   List<Expense> getAll();

   List<Expense> getPositive();

   List<Expense> getNegative();
}
