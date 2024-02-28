package ru.el59.office.iproviders;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Formula;

public interface IFormulaProvider extends IGenericDao<Formula, Long> {
   List<Formula> getAll();

   List<Formula> getWorked();
}
