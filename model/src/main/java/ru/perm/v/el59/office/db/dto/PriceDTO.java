package ru.perm.v.el59.office.db.dto;

import java.math.BigDecimal;

public class PriceDTO {
   private Integer nnum = 0;
   private BigDecimal price = new BigDecimal("0.00");

   public Integer getNnum() {
      return this.nnum;
   }

   public void setNnum(Integer nnum) {
      this.nnum = nnum;
   }

   public BigDecimal getPrice() {
      return this.price;
   }

   public void setPrice(BigDecimal price) {
      this.price = price;
   }
}
