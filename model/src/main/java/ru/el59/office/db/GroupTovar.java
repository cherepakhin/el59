package ru.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import ru.el59.ui.AUIBean;
import ru.el59.ui.IUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class GroupTovar extends AUIBean implements Serializable, IUIBean {
   private static final long serialVersionUID = -655763264219858206L;
   @UI(
      readonly = true,
      title = "Код группы",
      visible = true,
      width = 10
   )
   private String cod;
   @UI(
      readonly = false,
      title = "Группа",
      visible = true,
      width = 10
   )
   private String name = "";
   @UI(
      readonly = true,
      title = "Входит в группу",
      visible = true,
      width = 10
   )
   private GroupTovar parent;
   @UI(
      readonly = false,
      title = "Группа БЭСТ",
      visible = true,
      width = 6
   )
   private Integer bestcod = 0;
   @UI(
      readonly = true,
      title = "Группа Трейд",
      visible = true,
      width = 10
   )
   private String trade = "";
   @UI(
      readonly = true,
      title = "Метагруппа",
      visible = true,
      width = 8
   )
   private GroupTovar root;
   private BonusK bonusk = new BonusK();
   private GroupT groupT;
   @UI(
      readonly = false,
      title = "Наценка для расчета новой цены",
      visible = true,
      width = 6,
      justify = Justify.RIGHT
   )
   private BigDecimal factor = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Объем",
      visible = true,
      width = 6,
      justify = Justify.RIGHT
   )
   private BigDecimal volume = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Мин.наценка для 20 коп.",
      visible = true,
      width = 6,
      justify = Justify.RIGHT
   )
   private BigDecimal minNacenka = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Наценка для сайта",
      visible = true,
      width = 6,
      justify = Justify.RIGHT
   )
   private BigDecimal nacenkaForSite = new BigDecimal("0.00");
   private List<GroupTovar> childs = new ArrayList();
   private List<Tovar> relationTovar = new ArrayList();
   @UI(
      readonly = false,
      title = "Картинка",
      visible = true,
      width = 20
   )
   private String pathImage = "";
   private GroupTovar currentParent;

   public static String getDescriptionClass() {
      return "Группы товаров";
   }

   public boolean hasChids() {
      return this.childs.size() > 0;
   }

   public List<GroupTovar> getChilds() {
      return this.childs;
   }

   public void setChilds(List<GroupTovar> childs) {
      this.childs = childs;
   }

   public void addChild(GroupTovar child) {
      this.childs.add(child);
      child.setParent(this);
   }

   public String getTrade() {
      return this.trade;
   }

   public void setTrade(String trade) {
      this.trade = trade;
   }

   public Integer getBestcod() {
      return this.bestcod;
   }

   public void setBestcod(Integer bestcod) {
      this.bestcod = bestcod;
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

   public GroupTovar getParent() {
      return this.parent;
   }

   public void setParent(GroupTovar parent) {
      this.parent = parent;
   }

   public BonusK getBonusk() {
      return this.bonusk;
   }

   private BonusK getParentBonusK() {
      return this.parent != null ? this.parent.getBonusk() : new BonusK();
   }

   public void setBonusk(BonusK bonusk) {
      this.bonusk = bonusk;
   }

   public Integer getBest() {
      return this.bestcod != null && this.bestcod != 0 ? this.bestcod : this.getParentBest();
   }

   private Integer getParentBest() {
      return this.parent != null ? this.parent.getBest() : 0;
   }

   public GroupTovar getRoot() {
      return this.parent != null && !this.parent.getName().equals("Все") ? this.parent.getRoot() : this;
   }

   public String toString() {
      return this.name;
   }

   public String getBestAsString() {
      String scod = this.getBest().toString();
      if (scod.toString().length() < 5) {
         scod = "0" + scod;
      }

      return scod;
   }

   public boolean in(GroupTovar biggroup) {
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
         GroupTovar other = (GroupTovar)obj;
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

   public GroupT getGroupT() {
      return this.groupT != null ? this.groupT : this.getParentGroupT();
   }

   private GroupT getParentGroupT() {
      return this.parent != null ? this.parent.getGroupT() : null;
   }

   public void setGroupT(GroupT groupT) {
      this.groupT = groupT;
   }

   public BigDecimal getFactor() {
      return this.factor != null && this.factor.compareTo(BigDecimal.ZERO) != 0 ? this.factor : this.getParentFactor();
   }

   public BigDecimal getThisFactor() {
      return this.factor;
   }

   private BigDecimal getParentFactor() {
      return this.parent != null ? this.parent.getFactor() : null;
   }

   public void setFactor(BigDecimal factor) {
      this.factor = factor;
   }

   public BigDecimal getVolume() {
      return this.volume != null && this.volume.compareTo(BigDecimal.ZERO) != 0 ? this.volume : this.getParentVolume();
   }

   private BigDecimal getParentVolume() {
      return this.parent != null ? this.parent.getVolume() : new BigDecimal("0.00");
   }

   public void setVolume(BigDecimal volume) {
      this.volume = volume;
   }

   public BigDecimal getMinNacenka() {
      return this.minNacenka != null && this.minNacenka.compareTo(BigDecimal.ZERO) != 0 ? this.minNacenka : this.getParentminNacenka();
   }

   private BigDecimal getParentminNacenka() {
      return this.parent != null ? this.parent.getMinNacenka() : new BigDecimal("0.00");
   }

   public void setMinNacenka(BigDecimal minNacenka) {
      this.minNacenka = minNacenka;
   }

   public BigDecimal getNacenkaForSite() {
      return this.nacenkaForSite != null && this.nacenkaForSite.compareTo(BigDecimal.ZERO) != 0 ? this.nacenkaForSite : this.getParentNacenkaForSite();
   }

   public void setNacenkaForSite(BigDecimal nacenkaForSite) {
      this.nacenkaForSite = nacenkaForSite;
   }

   private BigDecimal getParentNacenkaForSite() {
      return this.parent != null ? this.parent.getNacenkaForSite() : new BigDecimal("0.00");
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

   public GroupTovar getCurrentParent() {
      return this.currentParent;
   }

   public void setCurrentParent(GroupTovar currentParent) {
      this.currentParent = currentParent;
   }
}
