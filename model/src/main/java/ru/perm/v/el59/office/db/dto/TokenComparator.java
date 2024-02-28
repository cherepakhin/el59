package ru.perm.v.el59.office.db.dto;

import java.io.Serializable;
import java.util.Comparator;

public class TokenComparator implements Comparator, Serializable {
   private static final long serialVersionUID = -6421055167916650479L;

   public int compare(Object arg0, Object arg1) {
      Token t0 = (Token)arg0;
      Token t1 = (Token)arg1;
      if (t0.weight > t1.weight) {
         return 1;
      } else {
         return t0.weight < t1.weight ? -1 : 0;
      }
   }
}
