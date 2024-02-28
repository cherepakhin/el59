package ru.el59.office.db.service;

import java.io.Serializable;

public class TDocAct extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = -4568232219843618776L;

   public static String getDescriptionClass() {
      return "Акт неремонтопригодности";
   }

   public String getContent() {
      return getDescriptionClass();
   }
}
