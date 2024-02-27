package ru.perm.v.el59.dto;

public class ExpenseDTO extends AEntityDTO {
   private static final long serialVersionUID = -1421833392179287085L;
   private Integer znak = 1;
   private String humanZnak;
   protected static String descriptionClass = "Статья расхода";

   public String getDescriptionClass() {
      return descriptionClass;
   }

   public Integer getZnak() {
      return this.znak;
   }

   public void setZnak(Integer znak) {
      this.znak = znak;
   }

   public String getHumanZnak() {
      return this.znak < 0 ? "Расход" : "Приход";
   }

   public void setHumanZnak(String humanZnak) {
      this.humanZnak = humanZnak;
   }
}
