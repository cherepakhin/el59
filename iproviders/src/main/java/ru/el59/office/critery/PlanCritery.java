package ru.el59.office.critery;

import java.io.Serializable;
import java.util.Date;
import ru.el59.office.db.Shop;
import ru.el59.office.db.plan.Plan;

public class PlanCritery implements Serializable {
   private static final long serialVersionUID = 4097024720395914108L;
   public Shop shop;
   public Integer year;
   public Integer month;
   public Date fromDate;
   public Date toDate;

   public PlanCritery(Plan plan) {
      this.shop = plan.getShop();
      this.year = plan.getYear();
      this.month = plan.getMonth();
   }

   public PlanCritery() {
   }
}
