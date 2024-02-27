package ru.perm.v.el59.dto;

public class ReasonDTO extends AEntityDTO {
   private static final long serialVersionUID = -2340887801927290548L;
   private Boolean isexpense = false;
   private Integer znak = 1;
   private String korrorder;
   private String humanZnak;
   private String attachment = "";
   private boolean cashBookDoc = true;
   protected static String descriptionClass = "���������";

   public String getDescriptionClass() {
      return descriptionClass;
   }

   public Boolean getIsexpense() {
      return this.isexpense;
   }

   public void setIsexpense(Boolean isexpense) {
      this.isexpense = isexpense;
   }

   public Integer getZnak() {
      return this.znak;
   }

   public void setZnak(Integer znak) {
      this.znak = znak;
   }

   public String getHumanZnak() {
      return this.znak < 0 ? "������" : "������";
   }

   public void setHumanZnak(String humanZnak) {
      this.humanZnak = humanZnak;
   }

   public String getAttachment() {
      return this.attachment;
   }

   public void setAttachment(String attachment) {
      this.attachment = attachment;
   }

   public String getKorrorder() {
      return this.korrorder;
   }

   public void setKorrorder(String korrorder) {
      this.korrorder = korrorder;
   }

   public boolean isCashBookDoc() {
      return this.cashBookDoc;
   }

   public void setCashBookDoc(boolean cashBookDoc) {
      this.cashBookDoc = cashBookDoc;
   }
}
