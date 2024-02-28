package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;

public class AbcdParam implements Serializable {
   private static final long serialVersionUID = 1169414103896152040L;
   private Long n = -1L;
   private String name = "";
   private BigDecimal Akobor = new BigDecimal("0.00");
   private BigDecimal Bkobor = new BigDecimal("0.00");
   private BigDecimal Dkobor = new BigDecimal("0.00");
   private BigDecimal Ckobor = new BigDecimal("0.00");
   private BigDecimal Amarga = new BigDecimal("0.00");
   private BigDecimal Bmarga = new BigDecimal("0.00");
   private BigDecimal Dmarga = new BigDecimal("0.00");
   private BigDecimal Cmarga = new BigDecimal("0.00");
   private BigDecimal weidth = new BigDecimal("0.00");

   public BigDecimal getWeidth() {
      return this.weidth;
   }

   public void setWeidth(BigDecimal weidth) {
      this.weidth = weidth;
   }

   public AbcdParam() {
      this.name = "_";
      this.Akobor = new BigDecimal("0.00");
      this.Bkobor = new BigDecimal("0.00");
      this.Dkobor = new BigDecimal("0.00");
      this.Ckobor = new BigDecimal("0.00");
      this.Amarga = new BigDecimal("0.00");
      this.Bmarga = new BigDecimal("0.00");
      this.Dmarga = new BigDecimal("0.00");
      this.Cmarga = new BigDecimal("0.00");
      this.weidth = new BigDecimal("0.00");
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public BigDecimal getAkobor() {
      return this.Akobor;
   }

   public void setAkobor(BigDecimal akobor) {
      this.Akobor = akobor;
   }

   public BigDecimal getBkobor() {
      return this.Bkobor;
   }

   public void setBkobor(BigDecimal bkobor) {
      this.Bkobor = bkobor;
   }

   public BigDecimal getDkobor() {
      return this.Dkobor;
   }

   public void setDkobor(BigDecimal dkobor) {
      this.Dkobor = dkobor;
   }

   public BigDecimal getAmarga() {
      return this.Amarga;
   }

   public void setAmarga(BigDecimal amarga) {
      this.Amarga = amarga;
   }

   public BigDecimal getBmarga() {
      return this.Bmarga;
   }

   public void setBmarga(BigDecimal bmarga) {
      this.Bmarga = bmarga;
   }

   public BigDecimal getDmarga() {
      return this.Dmarga;
   }

   public void setDmarga(BigDecimal dmarga) {
      this.Dmarga = dmarga;
   }

   public String calc(BigDecimal kobor, BigDecimal marga) {
      if (kobor.compareTo(this.Akobor) >= 0 && marga.compareTo(this.Akobor) >= 0 && this.Akobor.compareTo(BigDecimal.ZERO) != 0 && this.Amarga.compareTo(BigDecimal.ZERO) != 0) {
         return "A";
      } else if (kobor.compareTo(this.Bkobor) >= 0 && marga.compareTo(this.Bmarga) >= 0 && this.Bkobor.compareTo(BigDecimal.ZERO) != 0 && this.Bmarga.compareTo(BigDecimal.ZERO) != 0) {
         return "B";
      } else {
         return kobor.compareTo(this.Ckobor) >= 0 && marga.compareTo(this.Cmarga) >= 0 && this.Ckobor.compareTo(BigDecimal.ZERO) != 0 && this.Cmarga.compareTo(BigDecimal.ZERO) != 0 ? "C" : "D";
      }
   }

   public BigDecimal getCmarga() {
      return this.Cmarga;
   }

   public void setCmarga(BigDecimal cmarga) {
      this.Cmarga = cmarga;
   }

   public BigDecimal getCkobor() {
      return this.Ckobor;
   }

   public void setCkobor(BigDecimal ckobor) {
      this.Ckobor = ckobor;
   }
}
