package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class DocItem extends AUIBean implements Serializable, ITovar {
   private static final long serialVersionUID = -2133026032296504882L;
   private Long n;
   @UI(
      readonly = false,
      title = "Документ",
      visible = true,
      complex = true,
      width = 10
   )
   private Doc doc = new Doc();
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
      width = 6,
      justify = Justify.RIGHT
   )
   private BigDecimal qty = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "К-во заказано",
      visible = true,
      width = 6,
      justify = Justify.RIGHT
   )
   private BigDecimal qtyZakaz = new BigDecimal("0.00");
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
      title = "Сумма",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summa = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Аналог",
      visible = true,
      width = 20
   )
   private String analog = "";
   @UI(
      readonly = false,
      title = "Объем",
      visible = true,
      width = 10
   )
   private BigDecimal volume = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Комментарий",
      visible = true,
      width = 20
   )
   private String comment = "";

   public static String getDescriptionClass() {
      return "Строка документа";
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public Doc getDoc() {
      return this.doc;
   }

   public void setDoc(Doc doc) {
      this.doc = doc;
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

   public BigDecimal getQtyZakaz() {
      return this.qtyZakaz;
   }

   public void setQtyZakaz(BigDecimal qtyZakaz) {
      this.qtyZakaz = qtyZakaz;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
      if (this.qty != null && this.qty.compareTo(BigDecimal.ZERO) != 0 && summa.compareTo(BigDecimal.ZERO) > 0) {
         this.cena = summa.divide(this.qty, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
      }

   }

   public String getAnalog() {
      return this.analog;
   }

   public void setAnalog(String analog) {
      this.analog = analog;
   }

   public BigDecimal getVolume() {
      return this.getTovar().getGroup().getVolume().multiply(this.getQty());
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
         DocItem other = (DocItem)obj;
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

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }
}
