package ru.perm.v.el59.dto;

import java.io.Serializable;

public class TovarDTO implements Serializable {
   private static final long serialVersionUID = 4415209744498076492L;
   protected Integer nnum;
   protected String group;
   protected String name = "";
   private String brand = "";
   private String thing = "";
   private String ed = "шт.";
   private Integer category = 0;
   private String dateinsert;
   private String annotation = "";

   public Integer getNnum() {
      return this.nnum;
   }

   public void setNnum(Integer nnum) {
      this.nnum = nnum;
   }

   public String getGroup() {
      return this.group;
   }

   public void setGroup(String group) {
      this.group = group;
   }

   public String getBrand() {
      return this.brand;
   }

   public void setBrand(String brand) {
      this.brand = brand;
   }

   public String getThing() {
      return this.thing;
   }

   public void setThing(String thing) {
      this.thing = thing;
   }

   public String getEd() {
      return this.ed;
   }

   public void setEd(String ed) {
      this.ed = ed;
   }

   public Integer getCategory() {
      return this.category;
   }

   public void setCategory(Integer category) {
      this.category = category;
   }

   public String getAnnotation() {
      return this.annotation;
   }

   public void setAnnotation(String annotation) {
      this.annotation = annotation;
   }

   public String getDateinsert() {
      return this.dateinsert;
   }

   public void setDateinsert(String dateinsert) {
      this.dateinsert = dateinsert;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
