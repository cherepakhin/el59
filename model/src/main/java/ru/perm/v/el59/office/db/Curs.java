package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Curs implements Serializable {
   private static final long serialVersionUID = -2244428147170023262L;
   private Integer n;
   private Date ddate = new Date();
   private BigDecimal rub = new BigDecimal("0.00");

   public Integer getN() {
      return this.n;
   }

   public void setN(Integer n) {
      this.n = n;
   }

   public Curs() {
      this.ddate = new Date();
      this.rub = new BigDecimal(1.0D);
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public BigDecimal getRub() {
      return this.rub;
   }

   public void setRub(BigDecimal rub) {
      this.rub = rub;
   }
}
