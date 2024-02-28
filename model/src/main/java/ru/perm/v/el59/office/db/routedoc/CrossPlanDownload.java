package ru.perm.v.el59.office.db.routedoc;

import java.math.BigDecimal;
import java.util.Date;
import ru.perm.v.el59.dao.AEntity;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class CrossPlanDownload extends AEntity implements Comparable<CrossPlanDownload> {
   private static final long serialVersionUID = 6710126031827478746L;
   @UI(
      readonly = true,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = true,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private String shop;
   @UI(
      readonly = false,
      title = "1",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal sum1 = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Факт",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal sum1Fact = new BigDecimal("0.00");
   private PlanDownload PlanDownload1;
   @UI(
      readonly = false,
      title = "2",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal sum2 = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Факт",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal sum2Fact = new BigDecimal("0.00");
   private PlanDownload PlanDownload2;
   @UI(
      readonly = true,
      title = "Итого",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal sumAll = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Факт",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal sumAllFact = new BigDecimal("0.00");

   public static String getDescriptionClass() {
      return "Класс для создания сводной таблицы ";
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public String getShop() {
      return this.shop;
   }

   public void setShop(String shop) {
      this.shop = shop;
   }

   public BigDecimal getSum1() {
      return this.sum1;
   }

   public void setSum1(BigDecimal sum1) {
      this.sum1 = sum1;
      this.setSumAll(sum1.add(this.getSum2()));
   }

   public BigDecimal getSum2() {
      return this.sum2;
   }

   public void setSum2(BigDecimal sum2) {
      this.sum2 = sum2;
      this.setSumAll(sum2.add(this.getSum1()));
   }

   public BigDecimal getSumAll() {
      return this.sumAll;
   }

   public void setSumAll(BigDecimal sumAll) {
      this.sumAll = sumAll;
   }

   public PlanDownload getPlanDownload1() {
      return this.PlanDownload1;
   }

   public void setPlanDownload1(PlanDownload PlanDownload1) {
      this.PlanDownload1 = PlanDownload1;
   }

   public PlanDownload getPlanDownload2() {
      return this.PlanDownload2;
   }

   public void setPlanDownload2(PlanDownload PlanDownload2) {
      this.PlanDownload2 = PlanDownload2;
   }

   public int compareTo(CrossPlanDownload o) {
      int ret = this.getDdate().compareTo(o.getDdate());
      return ret == 0 ? this.getShop().compareTo(o.getShop()) : ret;
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (this.ddate == null ? 0 : this.ddate.hashCode());
      result = 31 * result + (this.shop == null ? 0 : this.shop.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         CrossPlanDownload other = (CrossPlanDownload)obj;
         if (this.ddate == null) {
            if (other.ddate != null) {
               return false;
            }
         } else if (!this.ddate.equals(other.ddate)) {
            return false;
         }

         if (this.shop == null) {
            if (other.shop != null) {
               return false;
            }
         } else if (!this.shop.equals(other.shop)) {
            return false;
         }

         return true;
      }
   }

   public BigDecimal getSum1Fact() {
      return this.sum1Fact;
   }

   public void setSum1Fact(BigDecimal sum1Fact) {
      this.sum1Fact = sum1Fact;
      this.setSumAllFact(sum1Fact.add(this.getSum2Fact()));
   }

   public BigDecimal getSum2Fact() {
      return this.sum2Fact;
   }

   public void setSum2Fact(BigDecimal sum2Fact) {
      this.sum2Fact = sum2Fact;
      this.setSumAllFact(sum2Fact.add(this.getSum1Fact()));
   }

   public BigDecimal getSumAllFact() {
      return this.sumAllFact;
   }

   public void setSumAllFact(BigDecimal sumAllFact) {
      this.sumAllFact = sumAllFact;
   }
}
