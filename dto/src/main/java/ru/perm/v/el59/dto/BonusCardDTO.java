package ru.perm.v.el59.dto;

import java.util.Date;

public class BonusCardDTO extends AEntityDTO {
   private static final long serialVersionUID = 6754478723160446298L;
   private String stroke;
   private Date ddate;

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
}
