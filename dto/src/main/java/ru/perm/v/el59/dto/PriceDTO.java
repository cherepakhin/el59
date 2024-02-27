package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class PriceDTO implements Serializable {
   private static final long serialVersionUID = 7444116057663492803L;
   private Long n = 0L;
   private Integer nnum;
   private BigDecimal cena = new BigDecimal("0.00");
   private Long priceType = 0L;
   private Date ddate;
   private String manager = "";

   public Integer getNnum() {
      return this.nnum;
   }

   public void setNnum(Integer nnum) {
      this.nnum = nnum;
   }

   public BigDecimal getCena() {
      return this.cena;
   }

   public void setCena(BigDecimal cena) {
      this.cena = cena;
   }

   public Long getPriceType() {
      return this.priceType;
   }

   public void setPriceType(Long priceType) {
      this.priceType = priceType;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public String getManager() {
      return this.manager;
   }

   public void setManager(String manager) {
      this.manager = manager;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }
}
