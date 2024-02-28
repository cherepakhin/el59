package ru.el59.office.iproviders.service;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.service.TypeTDocImage;

public interface ITypeTDocImageProvider extends IGenericDao<TypeTDocImage, Long> {
   List<TypeTDocImage> getAll();
}
