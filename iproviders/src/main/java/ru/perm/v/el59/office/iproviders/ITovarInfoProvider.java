package ru.perm.v.el59.office.iproviders;

import java.io.IOException;
import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.iproviders.critery.FeatureCritery;
import ru.perm.v.el59.office.db.Feature;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.Photo;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.db.dto.elxml.Good;

public interface ITovarInfoProvider extends IGenericDao<TovarInfo, Integer> {
   String createByListGood(List<Good> var1, String var2) throws Exception;

   List<String> getNotLoadedFiles(List<String> var1);

   TovarInfo clearFeaturesWeb(TovarInfo var1) throws Exception;

   TovarInfo deletePhoto(TovarInfo var1);

   byte[] getDataPhoto(Photo var1) throws IOException, Exception;

   void addPhoto(TovarInfo var1, Photo var2, byte[] var3, Manager var4) throws IOException, Exception;

   String getDirForPhoto(Integer var1) throws Exception;

   String getDirForPhoto() throws Exception;

   String getXmlCommerceMLByListTovarInfo(List<TovarInfo> var1) throws Exception;

   String getXmlCommerceByTovarCritery() throws Exception;

   void fillEmpty() throws Exception;

   void createByNnum(Integer var1) throws Exception;

   List<Integer> getEmptyNnumTovarInfo();

   String getAnnotation(Tovar var1);

   String getAnnotation(Integer var1);

   List<Feature> getFeatureByCritery(FeatureCritery var1);

   List<TovarInfo> getFromRestWeb();

   void calcMainFeature(TovarInfo var1) throws Exception;

   TovarInfo generateTagPrice(TovarInfo var1) throws Exception;
}
