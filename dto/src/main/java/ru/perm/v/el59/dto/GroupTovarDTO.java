package ru.perm.v.el59.dto;

import java.io.Serializable;

public class GroupTovarDTO implements Serializable {
   private static final long serialVersionUID = -2821970668688399387L;
   private String cod;
   private String name;
   private String parent;
   private Integer m_level;
   private Integer bestcod;

   public String getCod() {
      return this.cod;
   }

   public void setCod(String cod) {
      this.cod = cod;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getParent() {
      return this.parent;
   }

   public void setParent(String parent) {
      this.parent = parent;
   }

   public Integer getM_level() {
      return this.m_level;
   }

   public void setM_level(Integer mLevel) {
      this.m_level = mLevel;
   }

   public Integer getBestcod() {
      return this.bestcod;
   }

   public void setBestcod(Integer bestcod) {
      this.bestcod = bestcod;
   }
}
