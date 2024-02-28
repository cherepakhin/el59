package ru.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;

public class SummaInOut implements Serializable {
   private static final long serialVersionUID = 5395264008675346835L;
   private BigDecimal summain = new BigDecimal("0.00");
   private BigDecimal summaout = new BigDecimal("0.00");

   public BigDecimal getSummain() {
      return this.summain == null ? new BigDecimal("0.00") : this.summain;
   }

   public void setSummain(BigDecimal summain) {
      this.summain = summain;
   }

   public BigDecimal getSummaout() {
      return this.summaout == null ? new BigDecimal("0.00") : this.summaout;
   }

   public void setSummaout(BigDecimal summaout) {
      this.summaout = summaout;
   }
}
