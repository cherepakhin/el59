package ru.el59.office.db.dto.elxml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Units implements Serializable {
   private static final long serialVersionUID = 9026244794100641508L;
   private String Is_Base;
   private String Unit_Code;
   private Integer Numerator;
   private Integer Denominator;
   private String Lenght;
   private String Height;
   private String Width;
   private String LHW_Unit;
   private String Weight;
   private String W_Unit;
   private String Volume;
   private String V_Unit;
   private String Weight_Net;
   private List<BarCodes> barcodes = new ArrayList();

   public String getIs_Base() {
      return this.Is_Base;
   }

   public void setIs_Base(String isBase) {
      this.Is_Base = isBase;
   }

   public String getUnit_Code() {
      return this.Unit_Code;
   }

   public void setUnit_Code(String unitCode) {
      this.Unit_Code = unitCode;
   }

   public Integer getNumerator() {
      return this.Numerator;
   }

   public void setNumerator(Integer numerator) {
      this.Numerator = numerator;
   }

   public Integer getDenominator() {
      return this.Denominator;
   }

   public void setDenominator(Integer denominator) {
      this.Denominator = denominator;
   }

   public String getLenght() {
      return this.Lenght;
   }

   public void setLenght(String lenght) {
      this.Lenght = lenght;
   }

   public String getHeight() {
      return this.Height;
   }

   public void setHeight(String height) {
      this.Height = height;
   }

   public String getWidth() {
      return this.Width;
   }

   public void setWidth(String width) {
      this.Width = width;
   }

   public String getLHW_Unit() {
      return this.LHW_Unit;
   }

   public void setLHW_Unit(String lHWUnit) {
      this.LHW_Unit = lHWUnit;
   }

   public String getWeight() {
      return this.Weight;
   }

   public void setWeight(String weight) {
      this.Weight = weight;
   }

   public String getW_Unit() {
      return this.W_Unit;
   }

   public void setW_Unit(String wUnit) {
      this.W_Unit = wUnit;
   }

   public String getVolume() {
      return this.Volume;
   }

   public void setVolume(String volume) {
      this.Volume = volume;
   }

   public String getV_Unit() {
      return this.V_Unit;
   }

   public void setV_Unit(String vUnit) {
      this.V_Unit = vUnit;
   }

   public String getWeight_Net() {
      return this.Weight_Net;
   }

   public void setWeight_Net(String weightNet) {
      this.Weight_Net = weightNet;
   }

   public List<BarCodes> getBarcodes() {
      return this.barcodes;
   }

   public void setBarcodes(List<BarCodes> barcodes) {
      this.barcodes = barcodes;
   }
}
