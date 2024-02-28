package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import ru.el59.dao.AEntity;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class Price extends AEntity implements Serializable, ITovar {
   private static final long serialVersionUID = 1033907143400661202L;
   @UI(
      readonly = true,
      title = "Товар",
      visible = true,
      width = 0
   )
   private Tovar tovar;
   @UI(
      title = "Тип прайса",
      visible = true,
      width = 0,
      readonly = false
   )
   private PriceType priceType;
   @UI(
      title = "Изменил",
      visible = true,
      width = 0,
      readonly = false
   )
   private Manager manager;
   @UI(
      readonly = true,
      title = "Дата изменения",
      visible = true,
      width = 0
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "Цена",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal cena = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Наценка",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal nacenka = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Копейки",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal kop = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Маржа",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal k = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Новая цена",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal newcena = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Изменена",
      visible = true,
      width = 5
   )
   private Boolean changed = false;
   @UI(
      readonly = false,
      title = "Срок действия",
      visible = true,
      width = 10
   )
   private Date limitDate;

   public static String getDescriptionClass() {
      return "Прайс";
   }

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
   }

   public BigDecimal getCena() {
      return this.cena;
   }

   public void setCena(BigDecimal cena) {
      this.cena = cena;
      this.getK();
   }

   public BigDecimal getNacenka() {
      if (this.cena != null && this.getTovar().getCenainrub() != null) {
         this.nacenka = this.cena.subtract(this.getTovar().getCenainrub());
      } else {
         this.nacenka = new BigDecimal("0.00");
      }

      return this.nacenka;
   }

   public void setNacenka(BigDecimal nacenka) {
      this.nacenka = nacenka;
      this.newcena = this.getTovar().getCenainrub().add(nacenka);
      this.k = this.calcK();
      this.setChanged(true);
   }

   private BigDecimal calcK() {
      BigDecimal _k = new BigDecimal("0.00");
      if (this.cena == null) {
         return _k;
      } else {
         if (this.getTovar().getCena0() == null) {
            this.getTovar().setCena0(BigDecimal.ZERO);
         }

         if (this.getTovar().getCenainrub() == null) {
            this.getTovar().setCenainrub(BigDecimal.ZERO);
         }

         if (this.getTovar().getCena0().compareTo(BigDecimal.ZERO) == 0 && this.getTovar().getCenainrub().compareTo(BigDecimal.ZERO) == 0) {
            return _k;
         } else if (this.getTovar().getCenainrub().compareTo(BigDecimal.ZERO) != 0) {
            _k = this._getK(this.cena, this.getTovar().getCenainrub());
            return _k;
         } else if (this.getTovar().getCena0().compareTo(BigDecimal.ZERO) != 0) {
            _k = this._getK(this.cena, this.getTovar().getCena0());
            return _k;
         } else {
            if (this.newcena != null && this.newcena.compareTo(BigDecimal.ZERO) != 0) {
               BigDecimal c = new BigDecimal("0.00");
               if (this.getTovar().getCenainrub().compareTo(BigDecimal.ZERO) != 0) {
                  c = this.getTovar().getCenainrub();
               }

               if (this.getTovar().getCena0().compareTo(BigDecimal.ZERO) != 0) {
                  c = this.getTovar().getCena0();
               }

               if (c.compareTo(BigDecimal.ZERO) != 0) {
                  _k = this._getK(this.newcena, c);
                  return _k;
               }
            }

            return _k;
         }
      }
   }

   private BigDecimal _getK(BigDecimal _cena, BigDecimal ss) {
      if (_cena.compareTo(BigDecimal.ZERO) != 0 && ss.compareTo(BigDecimal.ZERO) != 0) {
         BigDecimal _k = _cena.subtract(ss).divide(_cena, RoundingMode.HALF_UP).multiply(new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP);
         return _k;
      } else {
         return BigDecimal.ZERO;
      }
   }

   public BigDecimal getK() {
      return this.calcK();
   }

   public void setK(BigDecimal _k) {
      this.k = _k;
   }

   public BigDecimal getNewcena() {
      return this.newcena;
   }

   public void setNewcena(BigDecimal newcena) {
      this.newcena = newcena;
      this.k = this.calcK();
      this.setDdate(new Date());
      this.setChanged(true);
   }

   public String getComment() {
      return this.getTovar().getComment();
   }

   public void setComment(String comment) {
      this.getTovar().setComment(comment);
      this.setChanged(true);
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date _ddate) {
      this.ddate = _ddate;
   }

   public Boolean getChanged() {
      return this.changed;
   }

   public void setChanged(Boolean changed) {
      this.changed = changed;
   }

   public void save() {
      if (this.changed) {
         this.cena = this.newcena;
         this.newcena = new BigDecimal("0.00");
         this.getK();
         this.getNacenka();
         this.setDdate(new Date());
         this.changed = false;
      }

   }

   public BigDecimal getKop() {
      return this.cena != null ? this.cena.subtract(this.cena.setScale(0, RoundingMode.HALF_UP)) : new BigDecimal("0.00");
   }

   public Manager getManager() {
      return this.manager;
   }

   public void setManager(Manager manager) {
      this.manager = manager;
   }

   public void setKop(BigDecimal kop) {
      this.kop = kop;
   }

   public Date getLimitDate() {
      return this.limitDate;
   }

   public void setLimitDate(Date limitDate) {
      this.limitDate = limitDate;
   }

   public PriceType getPriceType() {
      return this.priceType;
   }

   public void setPriceType(PriceType priceType) {
      this.priceType = priceType;
   }
}
