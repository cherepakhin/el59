package ru.el59.office.shopmodel;

import java.math.BigDecimal;

public class PayBonusCard extends Payment implements Cloneable {
   private static final long serialVersionUID = -1607663646371105525L;
   private BonusCardMove bonusCardMove;

   public static String getDescriptionClass() {
      return "Бонусы";
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public BonusCardMove getBonusCardMove() {
      return this.bonusCardMove;
   }

   public void setBonusCardMove(BonusCardMove bonusCardMove) {
      this.bonusCardMove = bonusCardMove;
   }

   public BigDecimal getNal() {
      return new BigDecimal("0.00");
   }
}
