package ru.perm.v.el59.office.iproviders.web;

import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.Feature;
//import ru.perm.v.el59.office.db.Feature; ru.perm.v.el59.office.db. ru.perm.v.el59.office.db.
import ru.perm.v.el59.office.db.TovarInfo;
//import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.db.web.SubsFeature;
//import ru.perm.v.el59.office.db.web.SubsFeature;

public interface ISubsFeatureProvider extends IGenericDao<SubsFeature, Long> {
   int process(List<SubsFeature> var1);

   int processByAll();

   List<SubsFeature> getAll();

   List<SubsFeature> getByCrritery(Object var1);

   List<Feature> getFeaturesBySubsFeature(SubsFeature var1);

   List<Feature> getFeaturesBySubsFeature(List<SubsFeature> var1);

   int processTovarInfo(TovarInfo var1);
}
