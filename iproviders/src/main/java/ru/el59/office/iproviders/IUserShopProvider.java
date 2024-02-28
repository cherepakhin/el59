package ru.el59.office.iproviders;

import ru.el59.dao.IGenericDao;
import ru.el59.office.db.UserShop;

public interface IUserShopProvider extends IGenericDao<UserShop, Long> {
   UserShop getNullUserShop() throws Exception;
}
