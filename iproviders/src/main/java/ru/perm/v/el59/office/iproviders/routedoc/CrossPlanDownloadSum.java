package ru.perm.v.el59.office.iproviders.routedoc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ru.el59.office.db.Shop;
import ru.el59.office.db.routedoc.PlanDownloadSum;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.UI;

public class CrossPlanDownloadSum extends AUIBean implements Serializable, Comparable<CrossPlanDownloadSum> {
   private static final long serialVersionUID = -5104792862009592169L;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      complex = true,
      width = 10
   )
   private Shop shop;
   private List<PlanDownloadSum> listPlanDownloadSum = new ArrayList();

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public List<PlanDownloadSum> getListPlanDownloadSum() {
      return this.listPlanDownloadSum;
   }

   public void setListPlanDownloadSum(List<PlanDownloadSum> listPlanDownloadSum) {
      this.listPlanDownloadSum = listPlanDownloadSum;
   }

   public int compareTo(CrossPlanDownloadSum o) {
      return this.shop.getName().compareTo(o.getShop().getName());
   }
}
