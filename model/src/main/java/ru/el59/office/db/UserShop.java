package ru.el59.office.db;

import java.io.Serializable;
import ru.el59.dao.AEntity;
import ru.el59.ui.UI;

public class UserShop extends AEntity implements Serializable {
   private static final long serialVersionUID = -6999178609272233998L;
   @UI(
      readonly = true,
      title = "Должность",
      visible = true,
      width = 10
   )
   private Dolgnost dolgnost;
   @UI(
      readonly = true,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private Shop shop;
   @UI(
      readonly = false,
      title = "Работает?",
      visible = true,
      width = 4
   )
   private Boolean worked = true;
   @UI(
      readonly = false,
      title = "Имя в БЭСТ",
      visible = true,
      width = 15
   )
   private String namebest = "";
   @UI(
      readonly = false,
      title = "Пароль",
      visible = true,
      width = 10
   )
   private String password = "";

   public UserShop() {
      this.n = -1L;
      this.dolgnost = new Dolgnost();
      this.shop = new Shop();
   }

   public Dolgnost getDolgnost() {
      return this.dolgnost;
   }

   public void setDolgnost(Dolgnost dolgnost) {
      this.dolgnost = dolgnost;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String toString() {
      return this.name;
   }

   public String getContent() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int hashCode() {
      int result = 31 + (this.n == null ? 0 : this.n.hashCode());
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
         UserShop other = (UserShop)obj;
         if (this.n == null) {
            if (other.n != null) {
               return false;
            }
         } else if (!this.n.equals(other.n)) {
            return false;
         }

         return true;
      }
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }

   public String getNamebest() {
      return this.namebest;
   }

   public void setNamebest(String namebest) {
      this.namebest = namebest;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public static String getDescriptionClass() {
      return "Сотрудник";
   }
}
