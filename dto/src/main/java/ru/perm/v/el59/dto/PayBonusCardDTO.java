package ru.perm.v.el59.dto;

public class PayBonusCardDTO extends PaymentDTO implements Cloneable {
   private static final long serialVersionUID = 470744132983507896L;
   public static String descriptionClass = "������";
   private BonusCardMoveDTO bonusCardMove;

   public String getDescriptionClass() {
      return descriptionClass;
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public BonusCardMoveDTO getBonusCardMove() {
      return this.bonusCardMove;
   }

   public void setBonusCardMove(BonusCardMoveDTO bonusCardMove) {
      this.bonusCardMove = bonusCardMove;
   }
}
