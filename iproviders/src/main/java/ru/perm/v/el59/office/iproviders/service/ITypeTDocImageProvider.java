package ru.perm.v.el59.office.iproviders.service;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.service.TypeTDocImage;

public interface ITypeTDocImageProvider extends IGenericDao<TypeTDocImage, Long> {
   List<TypeTDocImage> getAll();
}
