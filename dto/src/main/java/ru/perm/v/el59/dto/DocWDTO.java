package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DocWDTO implements Serializable {
   private static final long serialVersionUID = -4682227377729721300L;
   private String numdoc;
   private String shopCod;
   private Date ddate = new Date();
   private BigDecimal summa = new BigDecimal("0.00");
   private TypeDocDTO typedoc;
   private ContragentDTO contragent;
   protected static String descriptionClass = "Выписка W-магазина";
   private String comment = "";

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

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public BigDecimal getSumma() {
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
   }

   public TypeDocDTO getTypedoc() {
      return this.typedoc;
   }

   public void setTypedoc(TypeDocDTO typedoc) {
      this.typedoc = typedoc;
   }

   public ContragentDTO getContragent() {
      return this.contragent;
   }

   public void setContragent(ContragentDTO contragent) {
      this.contragent = contragent;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }
}
