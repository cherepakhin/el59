package ru.el59.office.critery;

import ru.el59.office.db.Doc;

public class DocItemCritery extends ADocItemCritery {
   private static final long serialVersionUID = 3232554811241361282L;
   public Doc doc;

   public DocItemCritery() {
   }

   public DocItemCritery(String name) {
      super(name);
   }
}
