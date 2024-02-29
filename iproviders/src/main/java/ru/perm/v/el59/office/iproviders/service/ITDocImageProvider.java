package ru.perm.v.el59.office.iproviders.service;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.service.TDocImage;

public interface ITDocImageProvider extends IGenericDao<TDocImage, Long> {
   byte[] getBody(TDocImage var1) throws Exception;
}
