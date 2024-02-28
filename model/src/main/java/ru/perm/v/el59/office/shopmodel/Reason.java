package ru.perm.v.el59.office.shopmodel;

import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.ui.UI;

public class Reason extends AEntity {
   private static final long serialVersionUID = -2340887801927290548L;
   @UI(
      readonly = true,
      title = "Нужна статья расхода?",
      visible = true,
      width = 4
   )
   private Boolean isexpense = false;
   @UI(
      readonly = false,
      title = "Доход(+1)/Расход(-1)",
      visible = true,
      width = 3
   )
   private Integer znak = 1;
   @UI(
      readonly = false,
      title = "Кор.счет",
      visible = true,
      width = 20
   )
   private String korrorder = "";
   @UI(
      readonly = false,
      title = "Приложения",
      visible = true,
      width = 10
   )
   private String attachment = "";
   @UI(
      readonly = false,
      title = "Отражать в кас.книге?",
      visible = true,
      width = 3
   )
   private boolean cashBookDoc = true;

   public static String getDescriptionClass() {
      return "Основание";
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
