package ru.el59.office.db.dto.elxml;

import java.io.Serializable;

public class FeaturePrice implements Serializable {
   private static final long serialVersionUID = 2319169338266885011L;
   private String Feature_Code;
   private String Name;
   private String Value;
   private String Priority;

   public String getFeature_Code() {
      return this.Feature_Code;
   }

   public void setFeature_Code(String featureCode) {
      this.Feature_Code = featureCode;
   }

   public String getName() {
      return this.Name;
   }

   public void setName(String name) {
      this.Name = name;
   }

   public String getValue() {
      return this.Value;
   }

   public void setValue(String value) {
      this.Value = value;
   }

   public String getPriority() {
      return this.Priority;
   }

   public void setPriority(String priority) {
      this.Priority = priority;
   }
}
