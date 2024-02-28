package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HistoryPrice implements Serializable, ITovar {
   private static final long serialVersionUID = 5671841452084135917L;
   private Long n;
   private Tovar tovar;
   private Date ddate = new Date();
   private BigDecimal oldcena = new BigDecimal("0.00");
   private PriceType pricetype;

   public PriceType getPriceType() {
      return this.pricetype;
   }

   public void setPriceType(PriceType pricetype) {
      this.pricetype = pricetype;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public BigDecimal getOldcena() {
      return this.oldcena;
   }

   public void setOldcena(BigDecimal oldcena) {
      this.oldcena = oldcena;
   }
}
