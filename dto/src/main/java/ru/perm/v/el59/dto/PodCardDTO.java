package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.util.Date;

public class PodCardDTO implements Serializable {
   private static final long serialVersionUID = -694006784971728224L;
   private Long n;
   private String name;
   private String stroke;
   private Date ddate;

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getStroke() {
      return this.stroke;
   }

   public void setStroke(String stroke) {
      this.stroke = stroke;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }
}
