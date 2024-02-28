package ru.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import ru.el59.ui.AUIBean;
import ru.el59.ui.UI;

public class DiapazonK extends AUIBean implements Serializable {
   private static final long serialVersionUID = 8106316270151532559L;
   private Long n;
   private BonusK bonusk;
   @UI(
      readonly = false,
      title = "От",
      visible = true,
      width = 10
   )
   private BigDecimal minPlan = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "До",
      visible = true,
      width = 10
   )
   private BigDecimal maxPlan = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Коэф-т",
      visible = true,
      width = 5
   )
   private BigDecimal k = new BigDecimal("0.00");

   public static String getDescriptionClass() {
      return "Диапазон";
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public BigDecimal getMinPlan() {
      return this.minPlan;
   }

   public void setMinPlan(BigDecimal minPlan) {
      this.minPlan = minPlan;
   }

   public BigDecimal getMaxPlan() {
      return this.maxPlan;
   }

   public void setMaxPlan(BigDecimal maxPlan) {
      this.maxPlan = maxPlan;
   }

   public BigDecimal getK() {
      return this.k;
   }

   public void setK(BigDecimal k) {
      this.k = k;
   }

   public DiapazonK() {
      this.minPlan = new BigDecimal("0.00");
      this.maxPlan = new BigDecimal("0.00");
      this.k = new BigDecimal("0.00");
   }

   public BonusK getBonusk() {
      return this.bonusk;
   }

   public void setBonusk(BonusK bonusk) {
      this.bonusk = bonusk;
   }
}
