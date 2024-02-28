package ru.perm.v.el59.office.iproviders.critery;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupTovarCritery implements Serializable {
   private static final long serialVersionUID = 2571143086154710854L;
   public String cod;
   public String name;
   public String parent_cod;
   public String bonusk;
   public ArrayList<String> listNameGroupT = new ArrayList();
}
