package ru.perm.v.el59.office.iproviders.critery;

import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.plan.Plan;

import java.io.Serializable;
import java.util.Date;

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
