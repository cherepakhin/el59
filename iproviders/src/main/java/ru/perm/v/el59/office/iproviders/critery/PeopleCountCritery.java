package ru.perm.v.el59.office.iproviders.critery;

import java.io.Serializable;
import ru.el59.office.db.PeopleCount;

public class PeopleCountCritery extends ShopPeriodCritery implements Serializable {
   private static final long serialVersionUID = -3564228476508122632L;

   public PeopleCountCritery(PeopleCount pc) {
      this.fromdate = pc.getDdate();
      this.todate = pc.getDdate();
      if (pc.getShop() != null) {
         this.shops.add(pc.getShop());
      }

   }

   public PeopleCountCritery() {
   }
}
