package ru.perm.v.el59.office.shopmodel;

public class PayPBank extends Payment {
   private static final long serialVersionUID = 249386448597369763L;
   private String platdoc;

   public static String getDescriptionClass() {
      return "Безнал";
   }

   public String getPlatdoc() {
      return this.platdoc;
   }

   public void setPlatdoc(String platdoc) {
      this.platdoc = platdoc;
   }
}
