package ru.perm.v.el59.office.db.dto.elxml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Goods implements Serializable {
   private static final long serialVersionUID = 88783916941006077L;
   private List<Good> goods = new ArrayList();

   public Goods() {
      this.goods = new ArrayList();
   }

   public List<Good> getGoods() {
      return this.goods;
   }

   public void setGoods(List<Good> goods) {
      this.goods = goods;
   }
}
