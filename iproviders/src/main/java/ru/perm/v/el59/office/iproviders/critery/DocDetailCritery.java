package ru.perm.v.el59.office.iproviders.critery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.db.TypeStock;

public class DocDetailCritery implements Serializable {
   private static final long serialVersionUID = 9172591513323844731L;
   public Long doctitle_n;
   public Long nn;
   private List<Tovar> tovars = new ArrayList();
   private Date fromdate;
   private Date todate;
   private String numdoc;
   private String description;
   private Boolean deleted;
   private String nameTypeDoc;
   private List<Integer> listNnums = new ArrayList();
   public List<TypeStock> typestocks = new ArrayList();
   public List<Shop> shops = new ArrayList();

   public Long getDoctitle_n() {
      return this.doctitle_n;
   }

   public void setDoctitle_n(Long doctitleN) {
      this.doctitle_n = doctitleN;
   }

   public List<Tovar> getTovars() {
      return this.tovars;
   }

   public void setTovars(List<Tovar> tovars) {
      this.tovars = tovars;
   }

   public Date getFromdate() {
      return this.fromdate;
   }

   public void setFromdate(Date fromdate) {
      this.fromdate = fromdate;
   }

   public Date getTodate() {
      return this.todate;
   }

   public void setTodate(Date todate) {
      this.todate = todate;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Boolean getDeleted() {
      return this.deleted;
   }

   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }

   public String getNameTypeDoc() {
      return this.nameTypeDoc;
   }

   public void setNameTypeDoc(String nameTypeDoc) {
      this.nameTypeDoc = nameTypeDoc;
   }

   public List<Integer> getListNnums() {
      return this.listNnums;
   }

   public void setListNnums(List<Integer> listNnums) {
      this.listNnums = listNnums;
   }

   public String getNumdoc() {
      return this.numdoc;
   }

   public void setNumdoc(String numdoc) {
      this.numdoc = numdoc;
   }

   public List<Shop> getShops() {
      return this.shops;
   }

   public void setShops(List<Shop> shops) {
      this.shops = shops;
   }

   public List<TypeStock> getTypeStocks() {
      return this.typestocks;
   }

   public void setTypeStocks(List<TypeStock> typestocks) {
      this.typestocks = typestocks;
   }
}
