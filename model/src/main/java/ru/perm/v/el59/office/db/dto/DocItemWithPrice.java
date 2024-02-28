package ru.perm.v.el59.office.db.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import ru.perm.v.el59.office.db.DocItem;
import ru.perm.v.el59.office.db.ITovar;
import ru.perm.v.el59.office.db.Price;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class DocItemWithPrice extends AUIBean implements Serializable, ITovar {
   private static final long serialVersionUID = -8892041480647159115L;
   @UI(
      readonly = false,
      title = "Товар",
      visible = true,
      complex = true,
      width = 10
   )
   private DocItem docItem;
   @UI(
      readonly = false,
      title = "Цена",
      visible = true,
      complex = true,
      width = 10
   )
   private Price price;
   @UI(
      readonly = false,
      title = "Маржа",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal k = new BigDecimal("0.00");

   public DocItem getDocItem() {
      return this.docItem;
   }

   public void setDocItem(DocItem docItem) {
      this.docItem = docItem;
   }

   public static String getDescriptionClass() {
      return "Товар с прайсом";
   }

   public Price getPrice() {
      return this.price;
   }

   public void setPrice(Price price) {
      this.price = price;
   }

   public BigDecimal getK() {
      BigDecimal cena = this.getPrice().getCena();
      BigDecimal cenaDocItem = this.getDocItem().getCena();
      if (cena != null && cenaDocItem != null) {
         if (cena.compareTo(BigDecimal.ZERO) == 0) {
            if (cena.compareTo(BigDecimal.ZERO) != 0 && cenaDocItem.compareTo(BigDecimal.ZERO) != 0) {
               this.k = cena.divide(cenaDocItem, RoundingMode.HALF_UP).multiply(new BigDecimal("100.00")).subtract(new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP);
               return this.k;
            } else {
               this.k = new BigDecimal("0.00");
               return this.k;
            }
         } else if (cenaDocItem.compareTo(BigDecimal.ZERO) == 0) {
            this.k = new BigDecimal("0.00");
            return this.k;
         } else {
            this.k = cena.divide(cenaDocItem, RoundingMode.HALF_UP).multiply(new BigDecimal("100.00")).subtract(new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP);
            return this.k;
         }
      } else {
         this.k = new BigDecimal("0.00");
         return this.k;
      }
   }

   public Tovar getTovar() {
      return this.getDocItem().getTovar();
   }

   public void setK(BigDecimal k) {
      this.k = k;
   }
}
