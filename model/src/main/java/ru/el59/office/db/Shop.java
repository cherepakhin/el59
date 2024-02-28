package ru.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import ru.el59.office.entitysimple.IEntitySimple;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class Shop extends AUIBean implements Serializable, IEntitySimple, Comparable<Shop> {
   private static final long serialVersionUID = 5688843695592954202L;
   @UI(
      readonly = false,
      title = "Код",
      visible = true
   )
   private String cod;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true
   )
   private String name;
   @UI(
      readonly = false,
      title = "Эл.почта",
      width = 20,
      visible = true
   )
   private String email;
   @UI(
      readonly = false,
      title = "ИС",
      width = 20,
      visible = true
   )
   private String system;
   @UI(
      readonly = false,
      title = "Подразделение",
      width = 20,
      visible = true
   )
   private String podrazdelenie;
   @UI(
      readonly = false,
      title = "Работает?",
      visible = true
   )
   private Boolean worked;
   @UI(
      readonly = false,
      title = "Площадь",
      justify = Justify.RIGHT,
      visible = true
   )
   private BigDecimal space;
   private String prefix;
   @UI(
      readonly = false,
      title = "Адрес",
      width = 20,
      visible = true
   )
   private String address;
   @UI(
      readonly = false,
      title = "Телефон",
      width = 15,
      visible = true
   )
   private String phone;
   @UI(
      readonly = false,
      title = "Полное название",
      width = 30,
      visible = true
   )
   private String fullname;
   @UI(
      readonly = false,
      title = "Директор",
      width = 30,
      visible = true
   )
   private String director;
   @UI(
      readonly = true,
      title = "Гл.бух-р",
      width = 15,
      visible = true
   )
   private String buh;
   @UI(
      readonly = false,
      title = "Открытие",
      width = 3,
      visible = true
   )
   private Integer workfrom;
   @UI(
      readonly = false,
      title = "Закрытие",
      width = 3,
      visible = true
   )
   private Integer workto;
   @UI(
      readonly = false,
      title = "ИНН",
      width = 10,
      visible = true
   )
   private String inn;
   @UI(
      readonly = false,
      title = "КПП",
      width = 10,
      visible = true
   )
   private String kpp;
   @UI(
      readonly = false,
      title = "Банк",
      width = 15,
      visible = true
   )
   private String bank;
   @UI(
      readonly = false,
      title = "БИК",
      width = 10,
      visible = true
   )
   private String bik;
   @UI(
      readonly = false,
      title = "ОКПО",
      width = 10,
      visible = true
   )
   private String okpo;
   @UI(
      readonly = false,
      title = "ОКПД",
      width = 10,
      visible = true
   )
   private String okdp;
   @UI(
      readonly = false,
      title = "ОГРН",
      width = 10,
      visible = true
   )
   private String ogrn;
   @UI(
      readonly = false,
      title = "Расчетный счет",
      width = 30,
      visible = true
   )
   private String rorder;
   @UI(
      readonly = false,
      title = "Корр-ий счет",
      width = 30,
      visible = true
   )
   private String korder;
   @UI(
      readonly = true,
      title = "Коды магазинов-партнеров",
      visible = true
   )
   private String partnerShops;
   @UI(
      readonly = true,
      title = "Синоним",
      visible = true
   )
   private String sinonim;

   public Shop() {
      this.cod = "";
      this.name = "";
      this.email = "";
      this.system = "";
      this.podrazdelenie = "0";
      this.worked = true;
      this.space = new BigDecimal("0.00");
      this.prefix = "";
      this.address = "";
      this.phone = "";
      this.fullname = "";
      this.director = "";
      this.buh = "";
      this.workfrom = 8;
      this.workto = 20;
      this.inn = "";
      this.kpp = "";
      this.bank = "";
      this.bik = "";
      this.okpo = "";
      this.okdp = "";
      this.ogrn = "";
      this.rorder = "";
      this.korder = "";
      this.partnerShops = "";
      this.sinonim = "";
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

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getPrefix() {
      return this.prefix != null ? this.prefix : "";
   }

   public void setPrefix(String prefix) {
      this.prefix = prefix;
   }

   public BigDecimal getSpace() {
      return this.space;
   }

   public void setSpace(BigDecimal space) {
      this.space = space;
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }

   public String getPodrazdelenie() {
      return this.podrazdelenie;
   }

   public void setPodrazdelenie(String podrazdelenie) {
      this.podrazdelenie = podrazdelenie;
   }

   public String getSystem() {
      return this.system;
   }

   public void setSystem(String system) {
      this.system = system;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Shop(int id) {
      this();
   }

   public Shop(int id, String cod, String name) {
      this();
      this.cod = cod;
      this.name = name;
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

   public boolean equals(Object s) {
      if (s == null) {
         return false;
      } else if (!s.getClass().equals(Shop.class)) {
         return false;
      } else {
         return this.cod.equals(((Shop)s).cod);
      }
   }

   public int hashCode() {
      return Integer.parseInt(this.cod);
   }

   public String toString() {
      return this.getName();
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

   public static String getDescriptionClass() {
      return "Магазин";
   }

   public int compareTo(Shop o) {
      return this.name.compareToIgnoreCase(o.getName());
   }

   public String getPartnerShops() {
      return this.partnerShops;
   }

   public void setPartnerShops(String partnerShops) {
      this.partnerShops = partnerShops;
   }

   public String getBuh() {
      return this.buh;
   }

   public void setBuh(String buh) {
      this.buh = buh;
   }

   public String getSinonim() {
      return this.sinonim;
   }

   public void setSinonim(String sinonim) {
      this.sinonim = sinonim;
   }
}
