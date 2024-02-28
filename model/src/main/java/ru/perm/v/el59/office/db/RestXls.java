package ru.perm.v.el59.office.db;

import java.math.BigDecimal;
import ru.perm.v.el59.dao.AEntity;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class RestXls extends AEntity {
   private static final long serialVersionUID = 546773718435471567L;
   @UI(
      readonly = false,
      title = "Н.номер",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private String nnum = "";
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

   public String getNnum() {
      return this.nnum;
   }

   public void setNnum(String nnum) {
      this.nnum = nnum;
   }

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }

   public BigDecimal getCena() {
      return this.cena;
   }

   public void setCena(BigDecimal cena) {
      this.cena = cena;
   }

   public static String getDescriptionClass() {
      return "Остатки поставщиков";
   }
}
