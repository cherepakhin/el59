package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TovarInfoDTO implements Serializable {
   private static final long serialVersionUID = 2515915275320283885L;
   private Integer nnum = 0;
   private List<FeaturePriceDTO> listFeaturePrice = new ArrayList();
   private List<FeatureDTO> listFeature = new ArrayList();
   private String info = "";
   private Date dateChanged;

   public Integer getNnum() {
      return this.nnum;
   }

   public void setNnum(Integer nnum) {
      this.nnum = nnum;
   }

   public List<FeaturePriceDTO> getListFeaturePrice() {
      return this.listFeaturePrice;
   }

   public void setListFeaturePrice(List<FeaturePriceDTO> listFeaturePrice) {
      this.listFeaturePrice = listFeaturePrice;
   }

   public List<FeatureDTO> getListFeature() {
      return this.listFeature;
   }

   public void setListFeature(List<FeatureDTO> listFeature) {
      this.listFeature = listFeature;
   }

   public String getInfo() {
      return this.info;
   }

   public void setInfo(String info) {
      this.info = info;
   }

   public Date getDateChanged() {
      return this.dateChanged;
   }

   public void setDateChanged(Date dateChanged) {
      this.dateChanged = dateChanged;
   }
}
