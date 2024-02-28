package ru.el59.office.db.service;

import java.io.Serializable;
import ru.el59.ui.AUIBean;
import ru.el59.ui.UI;

public class ServiceCenter extends AUIBean implements Serializable {
   private static final long serialVersionUID = -6006556799395832138L;
   private Long n;
   @UI(
      readonly = false,
      title = "Название",
      visible = true,
      width = 25
   )
   private String name;
   @UI(
      readonly = false,
      title = "Адрес",
      visible = true,
      width = 25
   )
   private String address;
   @UI(
      readonly = false,
      title = "Телефон",
      visible = true,
      width = 15
   )
   private String phone;
   @UI(
      readonly = false,
      title = "Бренды",
      visible = true,
      width = 15
   )
   private String brands;
   protected static String descriptionClass = "Сервисные центры";
   protected String[] lines;

   public String getDescriptionClass() {
      return descriptionClass;
   }

   public ServiceCenter(int id) {
      this();
   }

   public ServiceCenter() {
      this.name = "";
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getBrands() {
      return this.brands;
   }

   public void setBrands(String brands) {
      this.brands = brands;
   }

   public String[] getLines() {
      return this.lines;
   }

   public void setLines(String[] lines) {
      this.lines = lines;
   }
}
