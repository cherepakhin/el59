package ru.perm.v.el59.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BonusCardMoveDTO extends AEntityDTO {
   private static final long serialVersionUID = -3175323605566853592L;
   private Date ddate = new Date();
   private BigDecimal summa = new BigDecimal("0.00");
   private DocTitleDTO docTitle;
   private BonusCardDTO bonusCard;
   private String comment = "";
   private Boolean active = true;
   private Boolean deleted = false;
   protected static String descriptionClass = "�������� �� ������";

   public String getDescriptionClass() {
      return descriptionClass;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public BigDecimal getSumma() {
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
   }

   public DocTitleDTO getDocTitle() {
      return this.docTitle;
   }

   public void setDocTitle(DocTitleDTO docTitle) {
      this.docTitle = docTitle;
   }

   public BonusCardDTO getBonusCard() {
      return this.bonusCard;
   }

   public void setBonusCard(BonusCardDTO bonusCard) {
      this.bonusCard = bonusCard;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Boolean getActive() {
      return this.active;
   }

   public void setActive(Boolean active) {
      this.active = active;
   }

   public Boolean getDeleted() {
      return this.deleted;
   }

   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }
}
