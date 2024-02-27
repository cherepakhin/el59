package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SetTovarDTO implements Serializable {
   private static final long serialVersionUID = 7629924672479178748L;
   private Long n;
   private String name;
   private List<Integer> tovars = new ArrayList();

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

   public List<Integer> getTovars() {
      return this.tovars;
   }

   public void setTovars(List<Integer> tovars) {
      this.tovars = tovars;
   }
}
