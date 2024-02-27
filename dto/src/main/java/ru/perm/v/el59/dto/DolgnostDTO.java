package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DolgnostDTO implements Serializable {
   private static final long serialVersionUID = -7657923784208010020L;
   private Long n;
   private String name;
   private BigDecimal tarif;
   private List<String> listShopRight = new ArrayList();

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

   public BigDecimal getTarif() {
      return this.tarif;
   }

   public void setTarif(BigDecimal tarif) {
      this.tarif = tarif;
   }

   public List<String> getListShopRight() {
      return this.listShopRight;
   }

   public void setListShopRight(List<String> listShopRight) {
      this.listShopRight = listShopRight;
   }
}
