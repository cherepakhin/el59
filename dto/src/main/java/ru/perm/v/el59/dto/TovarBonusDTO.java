package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TovarBonusDTO implements Serializable {
   private static final long serialVersionUID = -5129863700495484820L;
   private Long n;
   private Integer nnum;
   private BigDecimal percent;
   private Boolean increase;

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public BigDecimal getPercent() {
      return this.percent;
   }

   public void setPercent(BigDecimal percent) {
      this.percent = percent;
   }

   public Integer getNnum() {
      return this.nnum;
   }

   public void setNnum(Integer nnum) {
      this.nnum = nnum;
   }

   public Boolean getIncrease() {
      return this.increase;
   }

   public void setIncrease(Boolean increase) {
      this.increase = increase;
   }
}
