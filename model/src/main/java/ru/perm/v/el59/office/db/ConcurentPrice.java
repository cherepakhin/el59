package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConcurentPrice implements Serializable {
   private static final long serialVersionUID = 4047486895879792572L;
   private Long n;
   private BigDecimal cena = new BigDecimal("0.00");
   private Tovar tovar;
   private Date ddate;
   private Concurent concurent;

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public BigDecimal getCena() {
      return this.cena;
   }

   public void setCena(BigDecimal cena) {
      this.cena = cena;
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

   public Concurent getConcurent() {
      return this.concurent;
   }

   public void setConcurent(Concurent concurent) {
      this.concurent = concurent;
   }
}
