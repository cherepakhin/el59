package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShopDTO implements Serializable {
   private static final long serialVersionUID = 2471611109675878233L;
   private String cod = "";
   private String name = "";
   private String email = "";
   private String system = "";
   private String podrazdelenie = "";
   private Boolean worked = true;
   private BigDecimal space = new BigDecimal("0.00");
   private String prefix = "";
   private String address = "";
   private String phone = "";
   private String fullname = "";
   private String director = "";
   private Integer workfrom = 8;
   private Integer workto = 20;
   private String inn = "";
   private String kpp = "";
   private String bank = "";
   private String bik = "";
   private String okpo = "";
   private String okdp = "";
   private String ogrn = "";
   private String rorder = "";
   private String korder = "";
   private String sinonim = "";

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

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getSystem() {
      return this.system;
   }

   public void setSystem(String system) {
      this.system = system;
   }

   public String getPodrazdelenie() {
      return this.podrazdelenie;
   }

   public void setPodrazdelenie(String podrazdelenie) {
      this.podrazdelenie = podrazdelenie;
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }

   public BigDecimal getSpace() {
      return this.space;
   }

   public void setSpace(BigDecimal space) {
      this.space = space;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public void setPrefix(String prefix) {
      this.prefix = prefix;
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

   public String getFullname() {
      return this.fullname;
   }

   public void setFullname(String fullname) {
      this.fullname = fullname;
   }

   public String getDirector() {
      return this.director;
   }

   public void setDirector(String director) {
      this.director = director;
   }

   public Integer getWorkfrom() {
      return this.workfrom;
   }

   public void setWorkfrom(Integer workfrom) {
      this.workfrom = workfrom;
   }

   public Integer getWorkto() {
      return this.workto;
   }

   public void setWorkto(Integer workto) {
      this.workto = workto;
   }

   public String getInn() {
      return this.inn;
   }

   public void setInn(String inn) {
      this.inn = inn;
   }

   public String getKpp() {
      return this.kpp;
   }

   public void setKpp(String kpp) {
      this.kpp = kpp;
   }

   public String getBank() {
      return this.bank;
   }

   public void setBank(String bank) {
      this.bank = bank;
   }

   public String getOkpo() {
      return this.okpo;
   }

   public void setOkpo(String okpo) {
      this.okpo = okpo;
   }

   public String getOkdp() {
      return this.okdp;
   }

   public void setOkdp(String okdp) {
      this.okdp = okdp;
   }

   public String getOgrn() {
      return this.ogrn;
   }

   public void setOgrn(String ogrn) {
      this.ogrn = ogrn;
   }

   public String getRorder() {
      return this.rorder;
   }

   public void setRorder(String rorder) {
      this.rorder = rorder;
   }

   public String getKorder() {
      return this.korder;
   }

   public void setKorder(String korder) {
      this.korder = korder;
   }

   public String getBik() {
      return this.bik;
   }

   public void setBik(String bik) {
      this.bik = bik;
   }

   public String getSinonim() {
      return this.sinonim;
   }

   public void setSinonim(String sinonim) {
      this.sinonim = sinonim;
   }
}
