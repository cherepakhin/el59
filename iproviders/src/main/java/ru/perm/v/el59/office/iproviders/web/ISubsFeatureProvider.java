package ru.perm.v.el59.office.iproviders.web;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Feature;
import ru.el59.office.db.TovarInfo;
import ru.el59.office.db.web.SubsFeature;

public interface ISubsFeatureProvider extends IGenericDao<SubsFeature, Long> {
   int process(List<SubsFeature> var1);

   int processByAll();

   List<SubsFeature> getAll();

   List<SubsFeature> getByCrritery(Object var1);

   List<Feature> getFeaturesBySubsFeature(SubsFeature var1);

   List<Feature> getFeaturesBySubsFeature(List<SubsFeature> var1);

   int processTovarInfo(TovarInfo var1);
}
