package ru.perm.v.el59.office.iproviders.subs;

import ru.el59.dao.IGenericDao;
import ru.el59.office.db.GroupTovar;
import ru.el59.office.db.subs.GroupTovarMainFeature;

public interface IGroupTovarMainFeatureProvider extends IGenericDao<GroupTovarMainFeature, Long> {
   GroupTovarMainFeature getByNameGroupTovar(String var1);

   GroupTovarMainFeature init(GroupTovarMainFeature var1);

   GroupTovarMainFeature createForGroupTovar(GroupTovar var1) throws Exception;

   GroupTovarMainFeature getByGroupTovar(GroupTovar var1);

   GroupTovarMainFeature getGroupTovarMainFeatureByGroupTovar(GroupTovar var1);
}
