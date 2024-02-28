package ru.el59.office.iproviders.service;

import ru.el59.dao.IGenericDao;
import ru.el59.office.db.service.TDocImage;

public interface ITDocImageProvider extends IGenericDao<TDocImage, Long> {
   byte[] getBody(TDocImage var1) throws Exception;
}
