package ru.perm.v.el59.office.db.dto.elxml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Good implements Serializable {
   private static final long serialVersionUID = 7487880123984203736L;
   private String GoodCode;
   private String Name;
   private String Brand_Code;
   private String G_Type_code;
   private String GG_Code;
   private String Matkl;
   private String GA_Code;
   private String Category;
   private String Name_Eng;
   private String HSCode;
   private String Mecat;
   private String G_Status;
   private List<Units> units = new ArrayList();
   private List<FeaturePrice> features = new ArrayList();
   private List<Werks> werks;

   public String getGoodCode() {
      return this.GoodCode;
   }

   public void setGoodCode(String goodCode) {
      this.GoodCode = goodCode;
   }

   public String getName() {
      return this.Name;
   }

   public void setName(String name) {
      this.Name = name;
   }

   public String getBrand_Code() {
      return this.Brand_Code;
   }

   public void setBrand_Code(String brandCode) {
      this.Brand_Code = brandCode;
   }

   public String getG_Type_code() {
      return this.G_Type_code;
   }

   public void setG_Type_code(String gTypeCode) {
      this.G_Type_code = gTypeCode;
   }

   public String getGA_Code() {
      return this.GA_Code;
   }

   public void setGA_Code(String gACode) {
      this.GA_Code = gACode;
   }

   public String getName_Eng() {
      return this.Name_Eng;
   }

   public void setName_Eng(String nameEng) {
      this.Name_Eng = nameEng;
   }

   public String getHSCode() {
      return this.HSCode;
   }

   public void setHSCode(String hSCode) {
      this.HSCode = hSCode;
   }

   public String getMecat() {
      return this.Mecat;
   }

   public void setMecat(String mecat) {
      this.Mecat = mecat;
   }

   public String getG_Status() {
      return this.G_Status;
   }

   public void setG_Status(String gStatus) {
      this.G_Status = gStatus;
   }

   public List<Units> getUnits() {
      return this.units;
   }

   public void setUnits(List<Units> units) {
      this.units = units;
   }

   public String getCategory() {
      return this.Category;
   }

   public void setCategory(String category) {
      this.Category = category;
   }

   public List<Werks> getWerks() {
      return this.werks;
   }

   public void setWerks(List<Werks> werks) {
      this.werks = werks;
   }

   public String getGG_Code() {
      return this.GG_Code;
   }

   public void setGG_Code(String gGCode) {
      this.GG_Code = gGCode;
   }

   public String getMatkl() {
      return this.Matkl;
   }

   public void setMatkl(String matkl) {
      this.Matkl = matkl;
   }

   public List<FeaturePrice> getFeatures() {
      return this.features;
   }

   public void setFeatures(List<FeaturePrice> features) {
      this.features = features;
   }
}
