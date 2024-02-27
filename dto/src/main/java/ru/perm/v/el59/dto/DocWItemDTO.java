package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DocWItemDTO implements Serializable {
   private static final long serialVersionUID = -4682227377729721300L;
   private String numdoc;
   private String shopCod;
   private Integer nnum;
   private BigDecimal qty = new BigDecimal("0.00");
   private BigDecimal summa = new BigDecimal("0.00");
   public static String descriptionClass = "����� � ��������� W-��������";

   public String getNumdoc() {
      return this.numdoc;
   }

   public void setNumdoc(String numdoc) {
      this.numdoc = numdoc;
   }

   public String getShopCod() {
      return this.shopCod;
   }

   public void setShopCod(String shopCod) {
      this.shopCod = shopCod;
   }

   public BigDecimal getSumma() {
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
   }

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }

   public Integer getNnum() {
      return this.nnum;
   }

   public void setNnum(Integer nnum) {
      this.nnum = nnum;
   }
}
