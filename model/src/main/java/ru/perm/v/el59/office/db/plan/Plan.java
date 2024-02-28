package ru.perm.v.el59.office.db.plan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class Plan extends AUIBean implements Serializable {
   private static final long serialVersionUID = 1021167189739642524L;
   @UI(
      readonly = true,
      title = "Номер",
      visible = true,
      width = 6
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
      title = "Месяц",
      visible = true
   )
   private Integer month;
   @UI(
      readonly = false,
      title = "Год",
      visible = true
   )
   private Integer year;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true
   )
   private Shop shop;
   @UI(
      readonly = false,
      title = "План продаж",
      visible = true,
      justify = Justify.RIGHT
   )
   private BigDecimal plansummaout = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "План себ-ти",
      visible = true,
      justify = Justify.RIGHT
   )
   private BigDecimal plansummain = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "План маржинального дохода",
      visible = true,
      justify = Justify.RIGHT
   )
   private BigDecimal planmarga = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Вес от ТО",
      visible = true,
      justify = Justify.RIGHT
   )
   private BigDecimal kTO = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Вес от маржи",
      visible = true,
      justify = Justify.RIGHT
   )
   private BigDecimal kMarga = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Факт продаж",
      visible = true,
      justify = Justify.RIGHT
   )
   private BigDecimal summaout = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Факт себ-ти",
      visible = true,
      justify = Justify.RIGHT
   )
   private BigDecimal summain = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Фонд директора",
      visible = true,
      justify = Justify.RIGHT
   )
   private BigDecimal fond = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Факт ПДС",
      visible = true,
      justify = Justify.RIGHT
   )
   private BigDecimal pds = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Факт ПДС(сс)",
      visible = true,
      justify = Justify.RIGHT
   )
   private BigDecimal pdsin = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Факт акс-ры",
      visible = true,
      justify = Justify.RIGHT
   )
   private BigDecimal acc = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Факт акс-ры(сс)",
      visible = true,
      justify = Justify.RIGHT
   )
   private BigDecimal accin = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Дата расчета",
      visible = true
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "Плановое к-во смен",
      visible = true,
      justify = Justify.RIGHT
   )
   private BigDecimal planqtysmen = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Расчет закрыт",
      visible = true
   )
   private Boolean closed = false;
   @UI(
      readonly = true,
      title = "Факт,продажи %",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal factPercentSummaBonus = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "План,акс,руб",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal planAcc = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "План,ПДС,руб",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal planPDS = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Факт,акс %",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal factPercentAcc = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Отклонения,акс %",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal diffPercentAcc = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Факт,ПДС %",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal factPercentPDS = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Отклонение ПДС %",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal diffPercentPDS = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "План на сегодня,руб",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal currentPlan = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "План на сегодня акс-ы,руб",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal currentPlanAcc = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "План на сегодня ПДС,руб",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal currentPlanPDS = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Факт маржа,%",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal factMarga = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Отклонение ТО %",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal diffPercentSummaout = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Отклонение ТО,руб",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal diffSummaout = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "% план,ПДС",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal planPercentPDS = new BigDecimal("0.04");
   @UI(
      readonly = true,
      title = "% план,акс",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal planPercentAcc = new BigDecimal("0.10");
   @UI(
      readonly = true,
      title = "Начало периода",
      visible = true,
      width = 10
   )
   private Date fromDate;
   @UI(
      readonly = true,
      title = "Конец периода",
      visible = true,
      width = 10
   )
   private Date toDate;
   private Plan parent;
   private List<Plan> dayplan = new ArrayList();
   private List<RestForPlan> restforplan = new ArrayList();

   public Boolean getClosed() {
      return this.closed;
   }

   public void setClosed(Boolean closed) {
      this.closed = closed;
   }

   public BigDecimal getkTO() {
      return this.kTO;
   }

   public void setkTO(BigDecimal kTO) {
      this.kTO = kTO;
   }

   public BigDecimal getkMarga() {
      return this.kMarga;
   }

   public void setkMarga(BigDecimal kMarga) {
      this.kMarga = kMarga;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public Integer getMonth() {
      return this.month;
   }

   public void setMonth(Integer month) {
      this.month = month;
      this.setDate();
   }

   public Plan() {
      this.plansummaout = new BigDecimal("0.00");
      this.plansummain = new BigDecimal("0.00");
      this.summaout = new BigDecimal("0.00");
      this.summain = new BigDecimal("0.00");
      this.kMarga = new BigDecimal("0.00");
      this.kTO = new BigDecimal("0.00");
      this.fond = new BigDecimal("0.00");
      this.pds = new BigDecimal("0.00");
      this.acc = new BigDecimal("0.00");
      this.planmarga = new BigDecimal("0.00");
      this.planqtysmen = new BigDecimal("0.00");
      this.closed = false;
      this.shop = new Shop();
      this.planPercentPDS = new BigDecimal("0.04");
      this.planPercentAcc = new BigDecimal("0.11");
   }

   public Integer getYear() {
      return this.year;
   }

   public void setYear(Integer year) {
      this.year = year;
      this.setDate();
   }

   public BigDecimal getSummaout() {
      return this.summaout;
   }

   public void setSummaout(BigDecimal summaout) {
      this.summaout = summaout;
   }

   public BigDecimal getSummain() {
      return this.summain;
   }

   public void setSummain(BigDecimal summain) {
      this.summain = summain;
   }

   public BigDecimal getPlansummaout() {
      return this.plansummaout;
   }

   public void setPlansummaout(BigDecimal plansummaout) {
      this.plansummaout = plansummaout;
      this.calcplansummain();
   }

   private void calcplansummain() {
      if (this.plansummaout.compareTo(BigDecimal.ZERO) > 0 && this.planmarga.compareTo(BigDecimal.ZERO) > 0) {
         BigDecimal _plansumin = this.plansummaout.multiply((new BigDecimal("1.00")).subtract(this.planmarga.divide(new BigDecimal("100.00"), RoundingMode.HALF_UP))).setScale(2, RoundingMode.HALF_UP);
         this.setPlansummain(_plansumin);
      }

   }

   public BigDecimal getPlansummain() {
      return this.plansummain;
   }

   public void setPlansummain(BigDecimal plansummain) {
      this.plansummain = plansummain;
   }

   public BigDecimal getFond() {
      return this.fond;
   }

   public void setFond(BigDecimal fond) {
      this.fond = fond;
   }

   public BigDecimal getPds() {
      return this.pds;
   }

   public void setPds(BigDecimal pds) {
      this.pds = pds;
   }

   public BigDecimal getAcc() {
      return this.acc;
   }

   public void setAcc(BigDecimal acc) {
      this.acc = acc;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public BigDecimal getPlanmarga() {
      return this.planmarga;
   }

   public void setPlanmarga(BigDecimal planmarga) {
      this.planmarga = planmarga;
      this.calcplansummain();
   }

   public BigDecimal getPdsin() {
      return this.pdsin;
   }

   public void setPdsin(BigDecimal pdsin) {
      this.pdsin = pdsin;
   }

   public BigDecimal getAccin() {
      return this.accin;
   }

   public void setAccin(BigDecimal accin) {
      this.accin = accin;
   }

   public BigDecimal getFactPercentSummaBonus() {
      if (this.getPlansummaout().compareTo(BigDecimal.ZERO) != 0) {
         this.factPercentSummaBonus = this.getSummaout().divide(this.getPlansummaout(), RoundingMode.HALF_UP).multiply(new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP);
      } else {
         this.factPercentSummaBonus = new BigDecimal("0.00");
      }

      return this.factPercentSummaBonus;
   }

   public BigDecimal getPlanAcc() {
      this.planAcc = this.getPlansummaout().multiply(this.getPlanPercentAcc());
      return this.planAcc;
   }

   public BigDecimal getPlanPDS() {
      this.planPDS = this.getPlansummaout().multiply(this.getPlanPercentPDS());
      return this.planPDS;
   }

   public BigDecimal getFactPercentAcc() {
      if (this.getPlanAcc().compareTo(BigDecimal.ZERO) != 0) {
         this.factPercentAcc = this.getAcc().divide(this.getPlanAcc(), RoundingMode.HALF_UP).multiply(new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP);
      } else {
         this.factPercentAcc = new BigDecimal("0.00");
      }

      return this.factPercentAcc;
   }

   public BigDecimal getFactPercentPDS() {
      if (this.getPlanPDS().compareTo(BigDecimal.ZERO) != 0) {
         this.factPercentPDS = this.getPds().divide(this.getPlanPDS(), RoundingMode.HALF_UP).multiply(new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP);
      } else {
         this.factPercentPDS = new BigDecimal("0.00");
      }

      return this.factPercentPDS;
   }

   public BigDecimal getPlanPercentPDS() {
      return this.planPercentPDS;
   }

   public BigDecimal getPlanPercentAcc() {
      return this.planPercentAcc;
   }

   public BigDecimal getDiffPercentAcc() {
      return this.diffPercentAcc;
   }

   public BigDecimal getDiffPercentPDS() {
      return this.diffPercentPDS;
   }

   public Date getMinDate() {
      return this.getFromDate();
   }

   public Date getMaxDate() {
      Calendar c = Calendar.getInstance();
      c.clear();
      c.setTime(this.getToDate());
      c.add(6, -1);
      return c.getTime();
   }

   public BigDecimal getCurrentValue(BigDecimal value) {
      Calendar c = Calendar.getInstance();
      Date today = new Date();
      if (today.getTime() > this.getToDate().getTime()) {
         c.clear();
         c.setTime(this.getToDate());
      } else {
         c.clear();
         c.setTime(today);
      }

      c.roll(6, -1);
      int MAXDAY_OF_MONTH = c.getActualMaximum(5);
      int DAY_OF_MONTH = c.get(5);
      value = value.divide(new BigDecimal(MAXDAY_OF_MONTH), RoundingMode.HALF_UP).multiply(new BigDecimal(DAY_OF_MONTH)).setScale(2, RoundingMode.HALF_UP);
      return value;
   }

   public BigDecimal getCurrentPlan() {
      if (this.currentPlan.compareTo(BigDecimal.ZERO) == 0) {
         this.currentPlan = this.getCurrentValue(this.getPlansummaout());
      }

      return this.currentPlan;
   }

   public BigDecimal getDiffSummaout() {
      this.diffSummaout = this.getSummaout().subtract(this.getCurrentPlan());
      return this.diffSummaout;
   }

   public void calc() {
      this.getFactMarga();
      this.getDiffPercentSummaout();
      this.getFactPercentPDS();
      this.getFactPercentAcc();
      this.getFactPercentSummaBonus();
   }

   public BigDecimal getDiffPercentSummaout() {
      if (this.getCurrentPlan().compareTo(BigDecimal.ZERO) != 0) {
         this.diffPercentSummaout = this.getDiffSummaout().divide(this.getCurrentPlan(), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
      } else {
         this.diffPercentSummaout = new BigDecimal("0.00");
      }

      return this.diffPercentSummaout;
   }

   public BigDecimal getPlanqtysmen() {
      return this.planqtysmen;
   }

   public void setPlanqtysmen(BigDecimal planqtysmen) {
      this.planqtysmen = planqtysmen;
   }

   public BigDecimal getCurrentPlanAcc() {
      if (this.currentPlanAcc.compareTo(BigDecimal.ZERO) == 0) {
         this.currentPlanAcc = this.getCurrentValue(this.getPlanAcc());
      }

      return this.currentPlanAcc;
   }

   public BigDecimal getCurrentPlanPDS() {
      if (this.currentPlanPDS.compareTo(BigDecimal.ZERO) == 0) {
         this.currentPlanPDS = this.getCurrentValue(this.getPlanPDS());
      }

      return this.currentPlanPDS;
   }

   public BigDecimal getFactMarga() {
      if (this.factMarga.compareTo(BigDecimal.ZERO) == 0) {
         if (this.getSummaout().compareTo(BigDecimal.ZERO) <= 0 || this.getSummain().compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.00");
         }

         this.factMarga = this.getSummaout().subtract(this.getSummain()).divide(this.getSummaout(), RoundingMode.HALF_UP).multiply(new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP);
      }

      return this.factMarga;
   }

   public Date getFromDate() {
      if (this.fromDate == null) {
         Calendar c = Calendar.getInstance();
         c.clear();
         c.set(this.year, this.month - 1, 1);
         this.setFromDate(c.getTime());
      }

      return this.fromDate;
   }

   public Date getToDate() {
      if (this.toDate == null) {
         Calendar c = Calendar.getInstance();
         c.clear();
         c.set(this.year, this.month, 1);
         this.setToDate(c.getTime());
      }

      return this.toDate;
   }

   private void setDate() {
      if (this.month != null && this.year != null) {
         this.getFromDate();
         this.getToDate();
      }

   }

   public void setFromDate(Date fromDate) {
      this.fromDate = fromDate;
   }

   public void setToDate(Date toDate) {
      this.toDate = toDate;
   }

   public Plan getParent() {
      return this.parent;
   }

   public void setParent(Plan parent) {
      this.parent = parent;
   }

   public List<Plan> getDayplan() {
      return this.dayplan;
   }

   public void setDayplan(List<Plan> dayplan) {
      this.dayplan = dayplan;
   }

   public void addDayplan(Plan _dayplan) {
      _dayplan.setParent(this);
      this.dayplan.add(_dayplan);
   }

   public void removeDayplan(Plan _dayplan) {
      this.dayplan.remove(_dayplan);
   }

   public List<RestForPlan> getRestforplan() {
      return this.restforplan;
   }

   public void setRestforplan(List<RestForPlan> restforplan) {
      this.restforplan = restforplan;
   }

   public void addRestforplan(RestForPlan _r) {
      _r.setPlan(this);
      this.restforplan.add(_r);
   }

   public void removeRestforplan(RestForPlan _r) {
      this.restforplan.remove(_r);
   }

   public void setPlanPercentPDS(BigDecimal planPercentPDS) {
      this.planPercentPDS = planPercentPDS;
   }

   public void setPlanPercentAcc(BigDecimal planPercentAcc) {
      this.planPercentAcc = planPercentAcc;
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
      return "План";
   }
}
