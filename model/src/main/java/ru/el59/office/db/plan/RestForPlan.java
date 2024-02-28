package ru.el59.office.db.plan;

import java.io.Serializable;
import java.math.BigDecimal;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class RestForPlan extends AUIBean implements Serializable {
   private static final long serialVersionUID = 8237305085095738146L;
   @UI(
      readonly = true,
      title = "Номер",
      visible = true,
      width = 10
   )
   protected Long n;
   @UI(
      readonly = true,
      title = "Название",
      visible = true,
      width = 10
   )
   protected String name = "";
   @UI(
      readonly = false,
      title = "План",
      visible = false,
      justify = Justify.RIGHT
   )
   private Plan plan = new Plan();
   @UI(
      readonly = true,
      title = "Себ-ть",
      visible = true,
      justify = Justify.RIGHT,
      width = 10
   )
   private BigDecimal summain = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Процент",
      visible = true,
      justify = Justify.RIGHT,
      width = 10
   )
   private BigDecimal percent = new BigDecimal("0.00");

   public Plan getPlan() {
      return this.plan;
   }

   public void setPlan(Plan plan) {
      this.plan = plan;
   }

   public BigDecimal getSummain() {
      return this.summain;
   }

   public void setSummain(BigDecimal summain) {
      this.summain = summain;
   }

   public BigDecimal getPercent() {
      return this.percent;
   }

   public void setPercent(BigDecimal percent) {
      this.percent = percent;
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

   public static String getDescriptionClass() {
      return "Остатки по плану";
   }
}
