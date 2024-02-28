package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class Units extends AUIBean implements Serializable {
   private static final long serialVersionUID = -1616208856209916466L;
   private TovarInfo tovarInfo;
   @UI(
      readonly = false,
      title = "Длина",
      visible = true,
      justify = Justify.RIGHT,
      width = 10
   )
   private BigDecimal lenght = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Высота",
      visible = true,
      justify = Justify.RIGHT,
      width = 10
   )
   private BigDecimal height = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Ширина",
      visible = true,
      justify = Justify.RIGHT,
      width = 10
   )
   private BigDecimal width = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Вес брутто",
      visible = true,
      justify = Justify.RIGHT,
      width = 10
   )
   private BigDecimal weight = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Вес нетто",
      visible = true,
      justify = Justify.RIGHT,
      width = 10
   )
   private BigDecimal weightNet = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Основная",
      visible = true,
      justify = Justify.RIGHT,
      width = 4
   )
   private Boolean isBase = true;

   public BigDecimal getLenght() {
      return this.lenght;
   }

   public void setLenght(BigDecimal lenght) {
      this.lenght = lenght;
   }

   public BigDecimal getHeight() {
      return this.height;
   }

   public void setHeight(BigDecimal height) {
      this.height = height;
   }

   public BigDecimal getWidth() {
      return this.width;
   }

   public void setWidth(BigDecimal width) {
      this.width = width;
   }

   public BigDecimal getWeight() {
      return this.weight;
   }

   public void setWeight(BigDecimal weight) {
      this.weight = weight;
   }

   public BigDecimal getWeightNet() {
      return this.weightNet;
   }

   public void setWeightNet(BigDecimal weightNet) {
      this.weightNet = weightNet;
   }

   public Boolean getIsBase() {
      return this.isBase;
   }

   public void setIsBase(Boolean isBase) {
      this.isBase = isBase;
   }

   public static String getDescriptionClass() {
      return "Габариты,Вес";
   }

   public TovarInfo getTovarInfo() {
      return this.tovarInfo;
   }

   public void setTovarInfo(TovarInfo tovarInfo) {
      this.tovarInfo = tovarInfo;
   }
}
