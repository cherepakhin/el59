package ru.perm.v.el59.office.db;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.ui.UI;

public class Manager extends AEntity {
   private static final long serialVersionUID = 1L;
   @UI(
      readonly = false,
      title = "Пароль",
      visible = true,
      width = 10
   )
   private String password;
   private Collection<Role> roles = new HashSet();
   @UI(
      readonly = true,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private String shop;
   @UI(
      readonly = false,
      title = "Почта",
      visible = true,
      width = 15
   )
   private String email;
   @UI(
      readonly = false,
      title = "Работает?",
      visible = true,
      width = 4
   )
   private Boolean worked;

   public static String getDescriptionClass() {
      return "Менеджер";
   }

   public Collection<Role> getRoles() {
      return this.roles;
   }

   public void setRoles(Collection<Role> roles) {
      this.roles = roles;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Manager() {
      this.name = "?";
      this.password = "?";
      this.shop = "?";
      this.email = "?";
      this.worked = true;
   }

   public String toString() {
      return this.name;
   }

   public String getShop() {
      if (this.shop == null) {
         this.shop = "";
      }

      return this.shop;
   }

   public void setShop(String shop) {
      this.shop = shop;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Manager)) return false;
      if (!super.equals(o)) return false;
      Manager manager = (Manager) o;
      return Objects.equals(password, manager.password) && Objects.equals(roles, manager.roles) && Objects.equals(shop, manager.shop) && Objects.equals(email, manager.email) && Objects.equals(worked, manager.worked);
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), password, roles, shop, email, worked);
   }
}
