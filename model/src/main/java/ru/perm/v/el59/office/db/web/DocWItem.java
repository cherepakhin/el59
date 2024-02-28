package ru.perm.v.el59.office.db.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.office.db.DocItem;
import ru.perm.v.el59.office.db.ITovar;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.shopmodel.DocDetail;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class DocWItem extends AEntity implements ITovar {
   private static final long serialVersionUID = 500391308094779654L;
   @UI(
      readonly = false,
      title = "Документ",
      visible = true,
      complex = true,
      width = 10
   )
   private DocW docw = new DocW();
   @UI(
      readonly = false,
      title = "Товар",
      visible = true,
      complex = true,
      width = 10
   )
   private Tovar tovar = new Tovar();
   @UI(
      readonly = false,
      title = "К-во",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal qty = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Цена",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal cena = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Предполагаемая дата отгрузки",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private Date ddatePlanOut = new Date();
   @UI(
      readonly = false,
      title = "Сумма",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summa = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Заказ",
      visible = true,
      complex = true,
      width = 10
   )
   private DocItem docItem;
   @UI(
      readonly = false,
      title = "Приход",
      visible = true,
      complex = true,
      width = 10
   )
   private DocDetail docDetailInput;
   @UI(
      readonly = false,
      title = "Выдача",
      visible = true,
      complex = true,
      width = 10
   )
   private DocDetail docDetailOut;
   @UI(
      readonly = false,
      title = "Удален?",
      visible = true,
      width = 4
   )
   private Boolean deleted = false;

   public static String getDescriptionClass() {
      return "Строка документа";
   }

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
   }

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.calcSumma();
      this.qty = qty;
   }

   public void calcSumma() {
      if (this.qty != null && this.cena != null) {
         this.summa = this.qty.multiply(this.cena);
      }

   }

   public BigDecimal getCena() {
      return this.cena;
   }

   public void setCena(BigDecimal cena) {
      this.calcSumma();
      this.cena = cena;
   }

   public BigDecimal getSumma() {
      this.calcSumma();
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
      if (this.qty != null && this.qty.compareTo(BigDecimal.ZERO) != 0 && summa.compareTo(BigDecimal.ZERO) > 0) {
         this.cena = summa.divide(this.qty, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
      }

   }

   public DocW getDocw() {
      return this.docw;
   }

   public void setDocw(DocW docw) {
      this.docw = docw;
   }

   public DocItem getDocItem() {
      return this.docItem;
   }

   public void setDocItem(DocItem docItem) {
      this.docItem = docItem;
   }

   public DocDetail getDocDetailInput() {
      return this.docDetailInput;
   }

   public void setDocDetailInput(DocDetail docDetailInput) {
      this.docDetailInput = docDetailInput;
   }

   public Date getDdatePlanOut() {
      return this.ddatePlanOut;
   }

   public void setDdatePlanOut(Date ddatePlanOut) {
      this.ddatePlanOut = ddatePlanOut;
   }

   public Boolean getDeleted() {
      return this.deleted;
   }

   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }

   public DocDetail getDocDetailOut() {
      return this.docDetailOut;
   }

   public void setDocDetailOut(DocDetail docDetailOut) {
      this.docDetailOut = docDetailOut;
   }
}
