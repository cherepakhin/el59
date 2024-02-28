package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.IUIBean;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class Rest extends AUIBean implements Serializable, IUIBean, ITovar {
   private static final long serialVersionUID = 9063643879106537306L;
   @UI(
      readonly = true,
      title = "№",
      visible = true,
      width = 10
   )
   private Long n;
   @UI(
      readonly = true,
      title = "Товар",
      visible = true,
      width = 0
   )
   private Tovar tovar = new Tovar();
   private Date ddate = new Date();
   private Date ddatecena = new Date();
   private Shop shop = new Shop();
   private TypeStock typeStock = new TypeStock();
   @UI(
      readonly = true,
      title = "К-во",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal qty = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Себ-ть",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal cenain = new BigDecimal("0.00");

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public Date getDdatecena() {
      return this.ddatecena;
   }

   public void setDdatecena(Date ddatecena) {
      this.ddatecena = ddatecena;
   }

   public BigDecimal getCenain() {
      return this.cenain;
   }

   public void setCenain(BigDecimal cenain) {
      this.cenain = cenain;
   }

   public RestCur getRestCur() {
      RestCur ret = new RestCur();
      ret.setCenain(this.cenain);
      ret.setQty(this.qty);
      ret.setShop(this.shop);
      ret.setTovar(this.tovar);
      ret.setTypeStock(this.typeStock);
      return ret;
   }

   public static String getDescriptionClass() {
      return "Остатки";
   }

   public TypeStock getTypeStock() {
      return this.typeStock;
   }

   public void setTypeStock(TypeStock typeStock) {
      this.typeStock = typeStock;
   }
}
