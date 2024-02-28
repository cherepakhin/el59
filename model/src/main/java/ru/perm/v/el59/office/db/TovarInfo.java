package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class TovarInfo extends AUIBean implements Serializable, Cloneable, ITovar {
   private static final long serialVersionUID = -6645553389191433565L;
   @UI(
      readonly = false,
      title = "Н.номер",
      visible = true,
      width = 9,
      justify = Justify.RIGHT
   )
   protected Integer nnum;
   @UI(
      readonly = false,
      title = "Товар",
      visible = true,
      width = 10
   )
   private Tovar tovar;
   private String info = "";
   @UI(
      readonly = false,
      title = "Дата изменений",
      visible = true,
      width = 10
   )
   private Date dateChanged = new Date();
   @UI(
      readonly = true,
      title = "Ценник",
      visible = true,
      width = 10
   )
   private List<FeaturePrice> listFeaturePrice = new ArrayList();
   @UI(
      readonly = true,
      title = "Характеристики",
      visible = true,
      width = 10
   )
   private List<Feature> listFeature = new ArrayList();
   @UI(
      readonly = true,
      title = "Фотографии",
      visible = true,
      width = 10
   )
   private List<Photo> listPhoto = new ArrayList();
   @UI(
      readonly = true,
      title = "Размеры",
      visible = true,
      width = 10
   )
   private List<Units> units = new ArrayList();
   @UI(
      readonly = true,
      title = "К-во фотографий",
      visible = true,
      width = 3,
      justify = Justify.RIGHT
   )
   private Integer qtyPhoto = 0;
   @UI(
      readonly = true,
      title = "К-во харак-к сайта",
      visible = true,
      width = 3,
      justify = Justify.RIGHT
   )
   private Integer qtyFeatures = 0;
   @UI(
      readonly = true,
      title = "К-во харак-к ценника",
      visible = true,
      width = 3,
      justify = Justify.RIGHT
   )
   private Integer qtyFeaturesAnnot = 0;
   @UI(
      readonly = true,
      title = "К-во осн-х хар-к",
      visible = true,
      width = 3,
      justify = Justify.RIGHT
   )
   private Integer qtyMainFeature = 0;
   @UI(
      readonly = true,
      title = "Не установлено осн-х хар-к",
      visible = true,
      width = 3,
      justify = Justify.RIGHT
   )
   private Integer qtyErrMainFeature = -1;
   @UI(
      readonly = true,
      title = "Группа",
      visible = true,
      width = 100
   )
   private String pathGroup = "";
   @UI(
      readonly = true,
      title = "Ссылка",
      visible = true,
      width = 100
   )
   private String link = "";
   @UI(
      readonly = true,
      title = "Изменил",
      visible = true,
      justify = Justify.LEFT
   )
   private Manager manager;
   @UI(
      readonly = true,
      title = "Двойник?",
      visible = true,
      justify = Justify.LEFT,
      width = 4
   )
   private Boolean isDublicate = false;

   public String getInfo() {
      return this.info;
   }

   public void setInfo(String info) {
      this.info = info;
   }

   public static String getDescriptionClass() {
      return "Описание товара";
   }

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
      this.nnum = tovar.getNnum();
   }

   public int hashCode() {
      int result = 31 + (this.nnum == null ? 0 : this.nnum.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         TovarInfo other = (TovarInfo)obj;
         if (this.nnum == null) {
            if (other.nnum != null) {
               return false;
            }
         } else if (!this.nnum.equals(other.nnum)) {
            return false;
         }

         return true;
      }
   }

   public List<Units> getUnits() {
      return this.units;
   }

   public void setUnits(List<Units> units) {
      this.units = units;
   }

   public Integer getNnum() {
      return this.nnum;
   }

   public void setNnum(Integer nnum) {
      this.nnum = nnum;
   }

   public void addFeature(Feature feature) {
      feature.setTovarInfo(this);
      if (this.listFeature.contains(feature)) {
         this.listFeature.remove(feature);
      }

      this.listFeature.add(feature);
   }

   public void removeFeature(Feature feature) {
      if (this.listFeature.contains(feature)) {
         this.listFeature.remove(feature);
      }

   }

   public void addFeaturePrice(FeaturePrice featurePrice) {
      featurePrice.setTovarInfo(this);
      if (this.listFeaturePrice.contains(featurePrice)) {
         this.listFeaturePrice.remove(featurePrice);
      }

      this.listFeaturePrice.add(featurePrice);
   }

   public void removeFeaturePrice(FeaturePrice featurePrice) {
      if (this.listFeaturePrice.contains(featurePrice)) {
         this.listFeaturePrice.remove(featurePrice);
      }

   }

   public void addPhoto(Photo photo) {
      if (!this.listPhoto.contains(photo)) {
         photo.setTovarInfo(this);
      }
   }

   public void removePhoto(Photo photo) {
      if (this.listPhoto.contains(photo)) {
         this.listPhoto.remove(photo);
      }

   }

   public void addUnits(Units unit) {
      unit.setTovarInfo(this);
      if (!this.units.contains(unit)) {
         this.units.add(unit);
      }

   }

   public void removeUnits(Units unit) {
      if (this.units.contains(unit)) {
         this.units.remove(unit);
      }

   }

   public TovarInfo clone() throws CloneNotSupportedException {
      TovarInfo newTovarInfo = new TovarInfo();
      newTovarInfo.setInfo(this.info);
      Iterator i$ = this.listFeature.iterator();

      while(i$.hasNext()) {
         Feature f = (Feature)i$.next();
         newTovarInfo.addFeature(f.clone());
      }

      i$ = this.listFeaturePrice.iterator();

      while(i$.hasNext()) {
         FeaturePrice f = (FeaturePrice)i$.next();
         newTovarInfo.addFeaturePrice(f.clone());
      }

      return newTovarInfo;
   }

   public void addFeatures(List<Feature> addListFeature) {
      Iterator i$ = addListFeature.iterator();

      while(i$.hasNext()) {
         Feature feature = (Feature)i$.next();
         this.addFeature(feature);
      }

   }

   public FeaturePrice getFeaturePriceValByCod(String code) {
      Iterator iter = this.listFeaturePrice.iterator();

      FeaturePrice f;
      do {
         if (!iter.hasNext()) {
            f = new FeaturePrice();
            f.setCode(code);
            f.setName(ConstAnnotFeature.getName(code));
            f.setVal("");
            this.addFeaturePrice(f);
            return f;
         }

         f = (FeaturePrice)iter.next();
      } while(f.getCode() == null || !f.getCode().equals(code));

      return f;
   }

   public Date getDateChanged() {
      return this.dateChanged;
   }

   public void setDateChanged(Date dateChanged) {
      this.dateChanged = dateChanged;
   }

   public Integer getQtyFeatures() {
      return this.qtyFeatures;
   }

   public void setQtyFeatures(Integer qtyFeatures) {
      this.qtyFeatures = qtyFeatures;
   }

   public Integer getQtyFeaturesAnnot() {
      return this.qtyFeaturesAnnot;
   }

   public void setQtyFeaturesAnnot(Integer qtyFeaturesAnnot) {
      this.qtyFeaturesAnnot = qtyFeaturesAnnot;
   }

   public List<Feature> getListFeature() {
      return this.listFeature;
   }

   public void setListFeature(List<Feature> listFeature) {
      this.listFeature = listFeature;
   }

   public List<Photo> getListPhoto() {
      return this.listPhoto;
   }

   public void setListPhoto(List<Photo> listPhoto) {
      this.listPhoto = listPhoto;
   }

   public List<FeaturePrice> getListFeaturePrice() {
      return this.listFeaturePrice;
   }

   public void setListFeaturePrice(List<FeaturePrice> listFeaturePrice) {
      this.listFeaturePrice = listFeaturePrice;
   }

   public String getPathGroup() {
      GroupTovar g = this.tovar.getGroup();

      for(this.pathGroup = g.getName(); g.getParent() != null && !g.getParent().getName().equals("Все"); this.pathGroup = g.getName() + " | " + this.pathGroup) {
         g = g.getParent();
      }

      return this.pathGroup;
   }

   public void setPathGroup(String pathGroup) {
      this.pathGroup = pathGroup;
   }

   public Integer getQtyPhoto() {
      return this.qtyPhoto;
   }

   public void setQtyPhoto(Integer qtyPhoto) {
      this.qtyPhoto = qtyPhoto;
   }

   public String getLink() {
      return this.link;
   }

   public void setLink(String link) {
      this.link = link;
   }

   public Manager getManager() {
      return this.manager;
   }

   public void setManager(Manager manager) {
      this.manager = manager;
   }

   public Integer getQtyErrMainFeature() {
      return this.qtyErrMainFeature;
   }

   public void setQtyErrMainFeature(Integer qtyErrMainFeature) {
      this.qtyErrMainFeature = qtyErrMainFeature;
   }

   public Integer getQtyMainFeature() {
      return this.qtyMainFeature;
   }

   public void setQtyMainFeature(Integer qtyMainFeature) {
      this.qtyMainFeature = qtyMainFeature;
   }

   public Boolean getIsDublicate() {
      return this.isDublicate;
   }

   public void setIsDublicate(Boolean isDublicate) {
      this.isDublicate = isDublicate;
   }
}
