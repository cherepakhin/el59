package ru.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class LocalPrice implements Serializable, ITovar {
   private static final long serialVersionUID = -2338125125538741225L;
   private Long n;
   private Tovar tovar = new Tovar();
   protected BigDecimal cena = new BigDecimal("0.00");
   private Shop shop = new Shop();
   private Date ddate = new Date();

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public BigDecimal getCena() {
      return this.cena;
   }

   public void setCena(BigDecimal cena) {
      this.cena = cena;
      this.ddate = new Date();
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public void setNacenka(BigDecimal nacenka) {
      BigDecimal c = nacenka.add(new BigDecimal("100.00")).divide(new BigDecimal("100.00"), RoundingMode.HALF_UP).multiply(this.tovar.getCena0()).setScale(2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
      this.setCena(c);
   }

   public BigDecimal getNacenka() {
      return this.cena.compareTo(BigDecimal.ZERO) != 0 && this.tovar.getCena0().compareTo(BigDecimal.ZERO) != 0 ? this.cena.divide(this.tovar.getCena0(), RoundingMode.HALF_UP).multiply(new BigDecimal("100.00")).subtract(new BigDecimal("100.00")) : new BigDecimal("0.00");
   }
}
