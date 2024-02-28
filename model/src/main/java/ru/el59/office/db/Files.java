package ru.el59.office.db;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Files implements Serializable {
   private static final long serialVersionUID = 3102787368793997033L;
   private Integer n;
   private String name;
   private Timestamp data;
   private Long len;
   private Timestamp dwrite;
   private String tip;
   private Date mindate;
   private Date maxdate;
   private String comment;
   private Shop shop;

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Long getLen() {
      return this.len;
   }

   public void setLen(Long l) {
      this.len = l;
   }

   public Date getMaxdate() {
      return this.maxdate;
   }

   public void setMaxdate(Date maxdate) {
      this.maxdate = maxdate;
   }

   public Date getMindate() {
      return this.mindate;
   }

   public void setMindate(Date mindate) {
      this.mindate = mindate;
   }

   public Integer getN() {
      return this.n;
   }

   public void setN(Integer n) {
      this.n = n;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public String getTip() {
      return this.tip;
   }

   public void setTip(String tip) {
      this.tip = tip;
   }

   public Timestamp getData() {
      return this.data;
   }

   public void setData(Timestamp data) {
      this.data = data;
   }

   public Timestamp getDwrite() {
      return this.dwrite;
   }

   public void setDwrite(Timestamp dwrite) {
      this.dwrite = dwrite;
   }

   public String getOutPath() {
      SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
      String ss = sdf.format(this.getMaxdate());
      String[] dmy = ss.split("\\.");
      return dmy[2] + File.separator + dmy[1] + File.separator + dmy[0] + File.separator + this.getTip();
   }
}
