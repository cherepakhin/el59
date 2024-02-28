package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import ru.el59.dao.AEntity;

public class OpGroup extends AEntity implements Serializable {
   private static final long serialVersionUID = 3021824537062909058L;
   private Collection<Operation> operation = new HashSet();

   public static String getDescriptionClass() {
      return "Группа операций";
   }

   public Collection<Operation> getOperation() {
      return this.operation;
   }

   public void setOperation(Collection<Operation> operation) {
      this.operation = operation;
   }

   public void addOperation(Operation _operation) {
      if (this.operation == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         _operation.setOpgroup(this);
         this.operation.add(_operation);
      }
   }
}
