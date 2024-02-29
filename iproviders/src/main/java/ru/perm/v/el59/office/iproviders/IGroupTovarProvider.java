package ru.perm.v.el59.office.iproviders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.iproviders.critery.GroupTovarCritery;
import ru.perm.v.el59.office.db.BonusK;
import ru.perm.v.el59.office.db.GroupTovar;

public interface IGroupTovarProvider extends IGenericDao<GroupTovar, String> {
   ArrayList<GroupTovar> getByEnd(String var1);

   ArrayList<GroupTovar> getAll();

   GroupTovar getByCod(String var1);

   GroupTovar getTree(GroupTovar var1);

   GroupTovar getChildsOfParent(GroupTovar var1);

   void deleteChilds(GroupTovar var1);

   String getNewCodChildForParent(GroupTovar var1);

   List<GroupTovar> getByCritery(GroupTovarCritery var1);

   void updateChildBonusK(GroupTovar var1, BonusK var2) throws Exception;

   BigDecimal getMinNacenka(GroupTovar var1);

   boolean isParent(GroupTovar var1, GroupTovar var2);

   GroupTovar initChilds(GroupTovar var1);

   GroupTovar getGroupByTrade(String var1);
}
