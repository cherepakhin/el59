package ru.perm.v.el59.office.db.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class ItemDTO extends AUIBean implements Serializable {
   private static final long serialVersionUID = -8860582516200004703L;
   @UI(
      readonly = false,
      title = "Товар",
      visible = true,
      complex = true,
      width = 30
   )
   private String description = "";
   @UI(
      readonly = false,
      title = "К-во",
      visible = true,
      width = 8,
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
   private BigDecimal price = new BigDecimal("0.00");

   public ItemDTO() {
      this.description = "";
      this.qty = new BigDecimal("0.00");
      this.price = new BigDecimal("0.00");
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }

   public BigDecimal getPrice() {
      return this.price;
   }

   public void setPrice(BigDecimal price) {
      this.price = price;
   }

   public static String getDescriptionClass() {
      return "Строка документа";
   }
}
