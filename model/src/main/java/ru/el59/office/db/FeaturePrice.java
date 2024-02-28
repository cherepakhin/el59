package ru.el59.office.db;

import java.io.Serializable;
import ru.el59.ui.AUIBean;
import ru.el59.ui.UI;

public class FeaturePrice extends AUIBean implements Serializable, Cloneable {
   private static final long serialVersionUID = 6885609626843368253L;
   private TovarInfo tovarInfo;
   @UI(
      readonly = false,
      title = "Характеристика",
      visible = true,
      width = 20
   )
   private String name = "";
   @UI(
      readonly = false,
      title = "Значение",
      visible = true,
      width = 20
   )
   private String val = "";
   @UI(
      readonly = false,
      title = "Код Эльдорадо",
      visible = true,
      width = 10
   )
   private String code = "";

   public static String getDescriptionClass() {
      return "Характеристика товара";
   }

   public String getVal() {
      return this.val;
   }

   public void setVal(String val) {
      this.val = val;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public int hashCode() {
      int result = 31 + (this.code == null ? 0 : this.code.hashCode());
      result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
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
         FeaturePrice other = (FeaturePrice)obj;
         if (this.code == null) {
            if (other.code != null) {
               return false;
            }
         } else if (!this.code.equals(other.code)) {
            return false;
         }

         if (this.code != null && other.code != null && this.code.equals(other.code)) {
            return true;
         } else {
            if (this.name == null) {
               if (other.name != null) {
                  return false;
               }
            } else if (!this.name.equals(other.name)) {
               return false;
            }

            return true;
         }
      }
   }

   public FeaturePrice clone() throws CloneNotSupportedException {
      FeaturePrice f = new FeaturePrice();
      f.setCode(this.code);
      f.setName(this.name);
      f.setVal(this.val);
      return f;
   }

   public TovarInfo getTovarInfo() {
      return this.tovarInfo;
   }

   public void setTovarInfo(TovarInfo tovarInfo) {
      this.tovarInfo = tovarInfo;
   }
}
