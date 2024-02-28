package ru.perm.v.el59.office.db.web;

import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class SummarySite extends AEntity implements Comparable<SummarySite> {
   private static final long serialVersionUID = 547903211211377037L;
   @UI(
      readonly = false,
      title = "К-во",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private Long qty;

   public Long getQty() {
      return this.qty;
   }

   public void setQty(Long qty) {
      this.qty = qty;
   }

   public int compareTo(SummarySite o) {
      return this.name.compareToIgnoreCase(o.getName());
   }
}
