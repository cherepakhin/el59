package ru.perm.v.el59.office.db.routedoc;

import java.math.BigDecimal;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.ui.UI;

public class Machine extends AEntity {
   private static final long serialVersionUID = -6190415052578720896L;
   @UI(
      readonly = false,
      title = "Гос.номер",
      visible = true,
      complex = true,
      width = 10
   )
   private String num = "";
   @UI(
      readonly = false,
      title = "Объем",
      visible = true,
      complex = true,
      width = 10
   )
   private BigDecimal volume = new BigDecimal("0.00");

   public static String getDescriptionClass() {
      return "Машина";
   }

   public String getNum() {
      return this.num;
   }

   public void setNum(String num) {
      this.num = num;
   }

   public BigDecimal getVolume() {
      return this.volume;
   }

   public void setVolume(BigDecimal volume) {
      this.volume = volume;
   }
}
