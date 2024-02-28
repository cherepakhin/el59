package ru.el59.office.db.plan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import ru.el59.office.db.Dolgnost;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class UserZP extends AUIBean implements Serializable {
   private static final long serialVersionUID = 7393773205556669984L;
   @UI(
      readonly = true,
      title = "Номер",
      visible = true,
      width = 7
   )
   protected Long n = 0L;
   @UI(
      readonly = true,
      title = "Ф.И.О.",
      visible = true,
      width = 20
   )
   private String name = "";
   @UI(
      readonly = true,
      title = "Имя в БЭСТ",
      visible = true,
      width = 20
   )
   private String namebest;
   private Plan plan;
   @UI(
      readonly = true,
      title = "Должность",
      visible = true,
      width = 10
   )
   private Dolgnost dolgnost;
   @UI(
      readonly = true,
      title = "Продажи",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaBonus = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Продажи для расчета аксов и ПДС",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaBonusForAccPDS = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Продажи(сс)",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaBonusin = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "ПДС",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaPDS = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "ПДС(cc)",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaPDSin = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "ПДС(бонус)",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaPDSBonus = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Акс-ры",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaAcc = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Акс-ры(сс)",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaAccin = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Акс-ры(бонус)",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaAccBonus = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Акс-ры(бонус)",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaAccBonusAll = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Основной товар",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summa20kop = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "К-во смен",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal qtysmen = new BigDecimal("0.00");
   private List<Tabel> tabel = new ArrayList();

   public BigDecimal getSummaAccBonusAll() {
      return this.summaAccBonusAll;
   }

   public void setSummaAccBonusAll(BigDecimal summaAccBonusAll) {
      this.summaAccBonusAll = summaAccBonusAll;
   }

   public UserZP() {
      this.name = "";
      this.summaBonus = new BigDecimal("0.00");
      this.summaBonusForAccPDS = new BigDecimal("0.00");
      this.summaPDS = new BigDecimal("0.00");
      this.summaPDSin = new BigDecimal("0.00");
      this.summaPDSBonus = new BigDecimal("0.00");
      this.summaAcc = new BigDecimal("0.00");
      this.summaAccin = new BigDecimal("0.00");
      this.summaAccBonus = new BigDecimal("0.00");
      this.dolgnost = new Dolgnost();
      this.summa20kop = new BigDecimal("0.00");
      this.qtysmen = new BigDecimal("0.00");
      this.namebest = "";
   }

   public Plan getPlan() {
      return this.plan;
   }

   public void setPlan(Plan plan) {
      this.plan = plan;
   }

   public Dolgnost getDolgnost() {
      return this.dolgnost;
   }

   public void setDolgnost(Dolgnost dolgnost) {
      this.dolgnost = dolgnost;
   }

   public BigDecimal getSummaBonus() {
      return this.summaBonus;
   }

   public void setSummaBonus(BigDecimal summaBonus) {
      this.summaBonus = summaBonus;
   }

   public BigDecimal getSummaPDS() {
      return this.summaPDS;
   }

   public void setSummaPDS(BigDecimal summaPDS) {
      this.summaPDS = summaPDS;
   }

   public BigDecimal getSummaAcc() {
      return this.summaAcc;
   }

   public void setSummaAcc(BigDecimal summaAcc) {
      this.summaAcc = summaAcc;
   }

   public BigDecimal getSumma20kop() {
      return this.summa20kop;
   }

   public void setSumma20kop(BigDecimal summa20kop) {
      this.summa20kop = summa20kop;
   }

   public BigDecimal getQtysmen() {
      return this.qtysmen;
   }

   public void setQtysmen(BigDecimal qtysmen) {
      this.qtysmen = qtysmen;
   }

   public BigDecimal getSummaAccin() {
      return this.summaAccin;
   }

   public void setSummaAccin(BigDecimal summaAccin) {
      this.summaAccin = summaAccin;
   }

   public BigDecimal getSummaBonusin() {
      return this.summaBonusin;
   }

   public void setSummaBonusin(BigDecimal summaBonusin) {
      this.summaBonusin = summaBonusin;
   }

   public BigDecimal getSummaPDSin() {
      return this.summaPDSin;
   }

   public void setSummaPDSin(BigDecimal summaPDSin) {
      this.summaPDSin = summaPDSin;
   }

   public BigDecimal getSummaPDSBonus() {
      return this.summaPDSBonus;
   }

   public void setSummaPDSBonus(BigDecimal summaPDSBonus) {
      this.summaPDSBonus = summaPDSBonus;
   }

   public BigDecimal getSummaAccBonus() {
      return this.summaAccBonus;
   }

   public void setSummaAccBonus(BigDecimal summaAccBonus) {
      this.summaAccBonus = summaAccBonus;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String toString() {
      return this.name;
   }

   public String getContent() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int hashCode() {
      int result = 31 + (this.n == null ? 0 : this.n.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         UserZP other = (UserZP)obj;
         if (this.n == null) {
            if (other.n != null) {
               return false;
            }
         } else if (!this.n.equals(other.n)) {
            return false;
         }

         return true;
      }
   }

   public List<Tabel> getTabel() {
      return this.tabel;
   }

   public void setTabel(List<Tabel> tabel) {
      this.tabel = tabel;
   }

   public void addTabel(Tabel _tabel) {
      if (this.tabel == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         _tabel.setUserzp(this);
         this.tabel.add(_tabel);
      }
   }

   public void delTabel(Tabel _tabel) {
      if (this.tabel == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         this.tabel.remove(_tabel);
      }
   }

   public String getNamebest() {
      return this.namebest;
   }

   public void setNamebest(String namebest) {
      this.namebest = namebest;
   }

   public static String getDescriptionClass() {
      return "Зарплата сотрудника";
   }

   public BigDecimal getSummaBonusForAccPDS() {
      return this.summaBonusForAccPDS;
   }

   public void setSummaBonusForAccPDS(BigDecimal summaBonusForAccPDS) {
      this.summaBonusForAccPDS = summaBonusForAccPDS;
   }
}
