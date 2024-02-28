package ru.perm.v.el59.office.db.routedoc;

import java.math.BigDecimal;
import ru.el59.dao.AEntity;
import ru.perm.v.el59.office.db.Contragent;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class SupplierPriority extends AEntity {
   private static final long serialVersionUID = 6384155809309537462L;
   @UI(
      title = "Контрагент",
      visible = true
   )
   private Contragent contragent;
   @UI(
      title = "Приоритет",
      visible = true,
      readonly = false,
      justify = Justify.RIGHT
   )
   private BigDecimal priority = new BigDecimal("0.00");

   public static String getDescriptionClass() {
      return "Очередность загрузки";
   }

   public Contragent getContragent() {
      return this.contragent;
   }

   public void setContragent(Contragent contragent) {
      this.contragent = contragent;
   }

   public BigDecimal getPriority() {
      return this.priority;
   }

   public void setPriority(BigDecimal priority) {
      this.priority = priority;
   }
}
