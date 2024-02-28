package ru.perm.v.el59.office.db.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceDbf implements Serializable {
   private static final long serialVersionUID = -8656527635384787013L;
   private String grup = "00000";
   private String nnum = "";
   private String name = "";
   private BigDecimal cena;
   private BigDecimal cena1;

   public PriceDbf() {
      this.cena = BigDecimal.ZERO;
      this.cena1 = BigDecimal.ZERO;
   }

   public String getGrup() {
      return this.grup;
   }

   public void setGrup(String grup) {
      this.grup = grup;
   }

   public String getNnum() {
      return this.nnum;
   }

   public void setNnum(String nnum) {
      this.nnum = nnum;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public BigDecimal getCena() {
      return this.cena;
   }

   public void setCena(BigDecimal cena) {
      this.cena = cena;
   }

   public BigDecimal getCena1() {
      return this.cena1;
   }

   public void setCena1(BigDecimal cena1) {
      this.cena1 = cena1;
   }
}
