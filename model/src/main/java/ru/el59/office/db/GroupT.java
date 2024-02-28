package ru.el59.office.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ru.el59.ui.AUIBean;
import ru.el59.ui.UI;

public class GroupT extends AUIBean implements Serializable {
   private static final long serialVersionUID = 8342478468281404248L;
   private String cod = "";
   @UI(
      readonly = true,
      title = "Группа",
      visible = true,
      width = 20
   )
   private String name = "";
   @UI(
      readonly = true,
      title = "Входит в группу",
      visible = true,
      width = 20
   )
   private GroupT parent;
   private List<GroupT> childs = new ArrayList();
   private List<Tovar> relationTovar = new ArrayList();
   @UI(
      readonly = false,
      title = "Картинка",
      visible = true,
      width = 20
   )
   private String pathImage = "";
   private GroupT currentParent;

   public static String getDescriptionClass() {
      return "Группы товаров";
   }

   public boolean hasChids() {
      return this.childs.size() > 0;
   }

   public List<GroupT> getChilds() {
      return this.childs;
   }

   public void setChilds(List<GroupT> childs) {
      this.childs = childs;
   }

   public void addChild(GroupT child) {
      child.setParent(this);
      this.childs.add(child);
   }

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

   public GroupT getParent() {
      return this.parent;
   }

   public void setParent(GroupT parent) {
      this.parent = parent;
   }

   public GroupT getRoot() {
      return this.parent.getName().equals("Все") ? this : this.parent.getRoot();
   }

   public String toString() {
      return this.name;
   }

   public boolean in(GroupT biggroup) {
      String s = biggroup.getCod();
      s = s.replace("00", "");
      return this.cod.startsWith(s);
   }

   public int hashCode() {
      int result = 31 + (this.cod == null ? 0 : this.cod.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         GroupT other = (GroupT)obj;
         if (this.cod == null) {
            if (other.cod != null) {
               return false;
            }
         } else if (!this.cod.equals(other.cod)) {
            return false;
         }

         return true;
      }
   }

   public GroupT getCurrentParent() {
      return this.currentParent;
   }

   public void setCurrentParent(GroupT currentParent) {
      this.currentParent = currentParent;
   }

   public List<Tovar> getRelationTovar() {
      return this.relationTovar;
   }

   public void setRelationTovar(List<Tovar> relationTovar) {
      this.relationTovar = relationTovar;
   }

   public String getPathImage() {
      return this.pathImage;
   }

   public void setPathImage(String pathImage) {
      this.pathImage = pathImage;
   }
}
