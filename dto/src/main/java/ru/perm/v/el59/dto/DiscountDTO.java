package ru.perm.v.el59.dto;

import java.io.Serializable;

public class DiscountDTO implements Serializable {
   private static final long serialVersionUID = 1831305617334828991L;
   private Long n;
   private String name;
   private String val;
   private String fromdate;
   private String todate;
   private Boolean ondetail;

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String getName() {
      return this.name;
   }

   public String getVal() {
      return this.val;
   }

   public void setVal(String val) {
      this.val = val;
   }

   public String getFromdate() {
      return this.fromdate;
   }

   public void setFromdate(String fromdate) {
      this.fromdate = fromdate;
   }

   public String getTodate() {
      return this.todate;
   }

   public void setTodate(String todate) {
      this.todate = todate;
   }

   public Boolean getOndetail() {
      return this.ondetail;
   }

   public void setOndetail(Boolean ondetail) {
      this.ondetail = ondetail;
   }

   public void setName(String name) {
      this.name = name;
   }
}
