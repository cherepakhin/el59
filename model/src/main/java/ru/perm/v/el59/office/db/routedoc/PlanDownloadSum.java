package ru.perm.v.el59.office.db.routedoc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import ru.el59.dao.AEntity;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Doc;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.Shop;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class PlanDownloadSum extends AEntity implements Comparable<PlanDownloadSum> {
   private static final long serialVersionUID = 8102674760388429190L;
   @UI(
      readonly = false,
      title = "Общий план",
      visible = true,
      complex = true,
      width = 10
   )
   private PlanDownload planDownload;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      complex = true,
      width = 10
   )
   private Shop shop;
   @UI(
      readonly = false,
      title = "Поставщик",
      visible = true,
      complex = true,
      width = 10
   )
   private Contragent contragent;
   @UI(
      readonly = false,
      title = "Автор",
      visible = true,
      complex = true,
      width = 10
   )
   private Manager manager;
   @UI(
      readonly = false,
      title = "План",
      visible = true,
      complex = false,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal plan = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Документы",
      visible = true,
      complex = true,
      width = 10
   )
   private List<Doc> listdoc = new ArrayList();
   @UI(
      readonly = false,
      title = "Заказы",
      visible = true,
      complex = true,
      width = 10
   )
   private TypeFileSum order;
   @UI(
      readonly = false,
      title = "Счета",
      visible = true,
      complex = true,
      width = 10
   )
   private TypeFileSum invoice;
   @UI(
      readonly = false,
      title = "Приходы",
      visible = true,
      complex = true,
      width = 10
   )
   private TypeFileSum receive;

   public static String getDescriptionClass() {
      return "Анализ погрузок";
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public Contragent getContragent() {
      return this.contragent;
   }

   public void setContragent(Contragent contragent) {
      this.contragent = contragent;
   }

   public List<Doc> getListdoc() {
      return this.listdoc;
   }

   public void setListdoc(List<Doc> listdoc) {
      this.listdoc = listdoc;
   }

   public TypeFileSum getOrder() {
      return this.order;
   }

   public void setOrder(TypeFileSum order) {
      this.order = order;
   }

   public TypeFileSum getInvoice() {
      return this.invoice;
   }

   public void setInvoice(TypeFileSum invoice) {
      this.invoice = invoice;
   }

   public TypeFileSum getReceive() {
      return this.receive;
   }

   public void setReceive(TypeFileSum receive) {
      this.receive = receive;
   }

   public BigDecimal getPlan() {
      return this.plan;
   }

   public void setPlan(BigDecimal plan) {
      this.plan = plan;
   }

   public Manager getManager() {
      return this.manager;
   }

   public void setManager(Manager manager) {
      this.manager = manager;
   }

   public int compareTo(PlanDownloadSum o) {
      int ret = this.shop.compareTo(o.getShop());
      return ret == 0 ? this.contragent.compareTo(o.getContragent()) : ret;
   }

   public PlanDownload getPlanDownload() {
      return this.planDownload;
   }

   public void setPlanDownload(PlanDownload planDownload) {
      this.planDownload = planDownload;
   }
}
