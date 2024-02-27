package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BankActionDTO implements Serializable {
   private static final long serialVersionUID = 281114776161474189L;
   private Long n;
   private String name;
   private String bank;
   private List<String> typeprices = new ArrayList();
   private List<String> discounts = new ArrayList();
   private Boolean worked;

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getBank() {
      return this.bank;
   }

   public void setBank(String bank) {
      this.bank = bank;
   }

   public List<String> getTypeprices() {
      return this.typeprices;
   }

   public void setTypeprices(List<String> typeprices) {
      this.typeprices = typeprices;
   }

   public List<String> getDiscounts() {
      return this.discounts;
   }

   public void setDiscounts(List<String> discounts) {
      this.discounts = discounts;
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }
}
