package ru.el59.office.shopmodel;

import java.io.Serializable;
import java.math.BigDecimal;
import ru.el59.dao.AEntity;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class TypePDS extends AEntity implements Serializable {
   private static final long serialVersionUID = 6674958584032411655L;
   @UI(
      readonly = false,
      title = "От",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal min = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "До",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal max = new BigDecimal("0.00");
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
      title = "Срок(2 или 3 года)",
      visible = true,
      width = 3,
      justify = Justify.RIGHT
   )
   private Integer year = 2;
   @UI(
      readonly = false,
      title = "Ном.номер",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private Integer nnum = 98320001;

   public TypePDS() {
      this.name = "?";
   }

   public static String getDescriptionClass() {
      return "Диапазоны ПДС";
   }

   public BigDecimal getMin() {
      return this.min;
   }

   public void setMin(BigDecimal min) {
      this.min = min;
   }

   public BigDecimal getMax() {
      return this.max;
   }

   public void setMax(BigDecimal max) {
      this.max = max;
   }

   public BigDecimal getCena() {
      return this.cena;
   }

   public void setCena(BigDecimal cena) {
      this.cena = cena;
   }

   public Integer getYear() {
      return this.year;
   }

   public void setYear(Integer year) {
      this.year = year;
   }

   public Integer getNnum() {
      return this.nnum;
   }

   public void setNnum(Integer nnum) {
      this.nnum = nnum;
   }
}
