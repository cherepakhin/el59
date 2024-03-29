package ru.perm.v.el59.office.iproviders.service;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.service.LOPT;

public interface ILOPTDao extends IGenericDao<LOPT, Long> {
   LOPT create(LOPT var1, String var2) throws Exception;

   LOPT getByN(Long var1);
}
