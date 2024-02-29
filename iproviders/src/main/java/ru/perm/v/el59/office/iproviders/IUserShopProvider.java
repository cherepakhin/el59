package ru.perm.v.el59.office.iproviders;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.UserShop;

public interface IUserShopProvider extends IGenericDao<UserShop, Long> {
   UserShop getNullUserShop() throws Exception;
}
