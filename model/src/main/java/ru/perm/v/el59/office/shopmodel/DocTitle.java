package ru.perm.v.el59.office.shopmodel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.TypeStock;
import ru.perm.v.el59.office.db.UserShop;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class DocTitle extends AUIBean implements Serializable, Comparable<DocTitle> {
   private static final long serialVersionUID = -7996914983135883652L;
   @UI(
      readonly = false,
      title = "Номер",
      visible = true,
      width = 5
   )
   protected Long n;
   @UI(
      readonly = false,
      title = "ID в магазине",
      visible = true,
      width = 5
   )
   private Long nn;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private Shop shop;
   @UI(
      readonly = false,
      title = "Номер док-та",
      visible = true,
      width = 7,
      justify = Justify.RIGHT
   )
   private String numdoc;
   @UI(
      readonly = false,
      title = "Создан",
      visible = true,
      width = 10
   )
   private Date ddatecreate = new Date();
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = true,
      title = "Себ-ть",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summain = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Себ-ть первоначальная",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summainold = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Сумма",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaout = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Документ",
      visible = true,
      width = 10
   )
   private TypeDocShop typeDocShop;
   @UI(
      readonly = false,
      title = "Автор",
      visible = true,
      width = 10
   )
   private UserShop userShop;
   @UI(
      readonly = false,
      title = "Контрагент",
      visible = true,
      width = 10
   )
   private Contragent contragent;
   @UI(
      readonly = false,
      title = "Состояние",
      visible = true,
      width = 10
   )
   private TypeDocStatusShop typeDocStatusShop;
   @UI(
      readonly = false,
      title = "Родитель",
      visible = true,
      width = 10
   )
   private DocTitle parent;
   @UI(
      readonly = false,
      title = "Склад",
      visible = true,
      width = 10
   )
   private TypeStock typeStock;
   protected static String descriptionClass = "Документ";
   @UI(
      readonly = false,
      title = "Предоплата",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   protected BigDecimal sumprepay = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Комментарий",
      visible = true,
      width = 20
   )
   private String comment = "";
   @UI(
      readonly = false,
      title = "Удален?",
      visible = true,
      width = 4
   )
   private Boolean deleted = false;
   @UI(
      readonly = false,
      title = "Отгружено",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   protected BigDecimal sumOutOrder = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Прайс",
      visible = true,
      width = 10
   )
   private TypePrice typePrice;
   @UI(
      readonly = false,
      title = "К1",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal k1 = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "К2",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal k2 = new BigDecimal("0.00");

   public DocTitle() {
      this.summain = new BigDecimal("0.00");
      this.sumOutOrder = new BigDecimal("0.00");
      this.comment = "";
      this.ddatecreate = new Date();
      this.deleted = false;
      this.k1 = new BigDecimal("0.00");
      this.k2 = new BigDecimal("0.00");
   }

   public Date getDdatecreate() {
      return this.ddatecreate;
   }

   public void setDdatecreate(Date ddatecreate) {
      this.ddatecreate = ddatecreate;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public BigDecimal getSummain() {
      return this.summain;
   }

   public void setSummain(BigDecimal summain) {
      this.summain = summain;
   }

   public BigDecimal getSummaout() {
      return this.summaout;
   }

   public void setSummaout(BigDecimal summaout) {
      this.summaout = summaout;
      this.k1 = summaout;
      if (this.k2 != null && this.k2.compareTo(BigDecimal.ZERO) > 0) {
         this.k1 = summaout.subtract(this.k2);
      }

   }

   public Contragent getContragent() {
      return this.contragent;
   }

   public void setContragent(Contragent contragent) {
      this.contragent = contragent;
   }

   public String getDescriptionClass() {
      return descriptionClass;
   }

   public DocTitle getParent() {
      return this.parent;
   }

   public void setParent(DocTitle parent) {
      this.parent = parent;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public String getNumdoc() {
      return this.numdoc;
   }

   public void setNumdoc(String numdoc) {
      this.numdoc = numdoc;
   }

   public TypeStock getTypeStock() {
      return this.typeStock;
   }

   public void setTypeStock(TypeStock typeStock) {
      this.typeStock = typeStock;
   }

   public Boolean getDeleted() {
      return this.deleted;
   }

   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }

   public BigDecimal getSumOutOrder() {
      return this.sumOutOrder;
   }

   public void setSumOutOrder(BigDecimal sumOutOrder) {
      this.sumOutOrder = sumOutOrder;
   }

   public int compareTo(DocTitle o) {
      if (this.getTypeDocShop().getName().equals(o.getTypeDocShop().getName())) {
         return this.getDdate().equals(o.getDdate()) ? this.getNumdoc().compareTo(o.getNumdoc()) : this.getDdate().compareTo(o.getDdate());
      } else {
         return this.getTypeDocShop().getName().compareTo(o.getTypeDocShop().getName());
      }
   }

   public TypePrice getTypePrice() {
      return this.typePrice;
   }

   public void setTypePrice(TypePrice typePrice) {
      this.typePrice = typePrice;
   }

   public BigDecimal getSumprepay() {
      return this.sumprepay;
   }

   public void setSumprepay(BigDecimal sumprepay) {
      this.sumprepay = sumprepay;
   }

   public BigDecimal getK1() {
      return this.k1;
   }

   public void setK1(BigDecimal k1) {
      this.k1 = k1;
   }

   public BigDecimal getK2() {
      return this.k2;
   }

   public void setK2(BigDecimal k2) {
      this.k2 = k2;
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (this.contragent == null ? 0 : this.contragent.hashCode());
      result = 31 * result + (this.ddate == null ? 0 : this.ddate.hashCode());
      result = 31 * result + (this.numdoc == null ? 0 : this.numdoc.hashCode());
      result = 31 * result + (this.parent == null ? 0 : this.parent.hashCode());
      result = 31 * result + (this.summain == null ? 0 : this.summain.hashCode());
      result = 31 * result + (this.summaout == null ? 0 : this.summaout.hashCode());
      result = 31 * result + (this.typePrice == null ? 0 : this.typePrice.hashCode());
      result = 31 * result + (this.typeStock == null ? 0 : this.typeStock.hashCode());
      result = 31 * result + (this.typeDocShop == null ? 0 : this.typeDocShop.hashCode());
      result = 31 * result + (this.userShop == null ? 0 : this.userShop.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         DocTitle other = (DocTitle)obj;
         if (this.contragent == null) {
            if (other.contragent != null) {
               return false;
            }
         } else if (!this.contragent.equals(other.contragent)) {
            return false;
         }

         if (this.ddate == null) {
            if (other.ddate != null) {
               return false;
            }
         } else if (!this.ddate.equals(other.ddate)) {
            return false;
         }

         if (this.numdoc == null) {
            if (other.numdoc != null) {
               return false;
            }
         } else if (!this.numdoc.equals(other.numdoc)) {
            return false;
         }

         if (this.parent == null) {
            if (other.parent != null) {
               return false;
            }
         } else if (!this.parent.equals(other.parent)) {
            return false;
         }

         if (this.summain == null) {
            if (other.summain != null) {
               return false;
            }
         } else if (this.summain.compareTo(other.summain) != 0) {
            return false;
         }

         if (this.summaout == null) {
            if (other.summaout != null) {
               return false;
            }
         } else if (this.summaout.compareTo(other.summaout) != 0) {
            return false;
         }

         if (this.typePrice == null) {
            if (other.typePrice != null) {
               return false;
            }
         } else if (!this.typePrice.equals(other.typePrice)) {
            return false;
         }

         if (this.typeStock == null) {
            if (other.typeStock != null) {
               return false;
            }
         } else if (!this.typeStock.equals(other.typeStock)) {
            return false;
         }

         if (this.typeDocShop == null) {
            if (other.typeDocShop != null) {
               return false;
            }
         } else if (!this.typeDocShop.equals(other.typeDocShop)) {
            return false;
         }

         if (this.userShop == null) {
            if (other.userShop != null) {
               return false;
            }
         } else if (!this.userShop.equals(other.userShop)) {
            return false;
         }

         return true;
      }
   }

   public BigDecimal getSummainold() {
      return this.summainold;
   }

   public void setSummainold(BigDecimal summainold) {
      this.summainold = summainold;
   }

   public Long getNn() {
      return this.nn;
   }

   public void setNn(Long nn) {
      this.nn = nn;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public TypeDocShop getTypeDocShop() {
      return this.typeDocShop;
   }

   public void setTypeDocShop(TypeDocShop typeDocShop) {
      this.typeDocShop = typeDocShop;
   }

   public UserShop getUserShop() {
      return this.userShop;
   }

   public void setUserShop(UserShop userShop) {
      this.userShop = userShop;
   }

   public TypeDocStatusShop getTypeDocStatusShop() {
      return this.typeDocStatusShop;
   }

   public void setTypeDocStatusShop(TypeDocStatusShop typeDocStatusShop) {
      this.typeDocStatusShop = typeDocStatusShop;
   }
}
