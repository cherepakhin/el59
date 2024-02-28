package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import ru.perm.v.el59.office.db.dto.TovarDTO;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class Tovar extends AUIBean implements Serializable, Cloneable, ITovar {
   private static final long serialVersionUID = 1155758415709756386L;
   @UI(
      readonly = false,
      title = "Н.номер",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private Integer nnum = 0;
   private GroupTovar group;
   @UI(
      readonly = true,
      title = "Название",
      visible = true,
      width = 30
   )
   private String name = "";
   @UI(
      readonly = true,
      title = "Бренд",
      visible = true,
      width = 30
   )
   private String brand = "";
   @UI(
      readonly = true,
      title = "Классификатор",
      visible = true,
      width = 30
   )
   private String thing = "";
   @UI(
      readonly = true,
      title = "Ед.изм.",
      visible = true,
      width = 30
   )
   private String ed = "";
   @UI(
      readonly = true,
      title = "Категория",
      visible = true,
      width = 3,
      justify = Justify.RIGHT
   )
   private Integer category = 0;
   @UI(
      readonly = true,
      title = "Бух.себ-ть.",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal cena0 = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Дата создания",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private Date dateinsert = new Date();
   @UI(
      readonly = true,
      title = "Дата изменения",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private Date dateChanged = new Date();
   private Boolean checked = false;
   @UI(
      readonly = true,
      title = "Файл",
      visible = true,
      width = 30
   )
   private String file = "";
   @UI(
      readonly = true,
      title = "Себ-ть,$.",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal cenainue = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Комментарий",
      visible = true,
      width = 10,
      justify = Justify.LEFT
   )
   private String comment = "";
   private Integer typetovar = 0;
   @UI(
      readonly = true,
      title = "Себ-ть,руб.",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal cenainrub;
   @UI(
      readonly = true,
      title = "Изменил",
      visible = true,
      justify = Justify.LEFT
   )
   private Manager manager;
   @UI(
      readonly = true,
      title = "Оригинал",
      visible = true,
      width = 10
   )
   private Integer parentnnum;
   private String annotation = "";
   @UI(
      readonly = true,
      title = "Прайс",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private Price price;
   @UI(
      readonly = true,
      title = "Остаток",
      visible = true,
      width = 6,
      justify = Justify.RIGHT
   )
   private BigDecimal qtyRest = new BigDecimal("0.00");
   @UI(
      title = "Изменен",
      visible = true,
      width = 4
   )
   private boolean changed = false;

   public BigDecimal getCenainrub() {
      return this.cenainrub;
   }

   public void setCenainrub(BigDecimal cenainrub) {
      this.cenainrub = cenainrub;
   }

   public BigDecimal getCenainue() {
      return this.cenainue;
   }

   public void setCenainue(BigDecimal cenainue) {
      this.cenainue = cenainue;
   }

   public GroupTovar getGroup() {
      return this.group;
   }

   public void setGroup(GroupTovar group) {
      this.group = group;
   }

   public Boolean getChecked() {
      return this.checked;
   }

   public void setChecked(Boolean checked) {
      this.checked = checked;
   }

   public Date getDateinsert() {
      return this.dateinsert;
   }

   public void setDateinsert(Date dateinsert) {
      this.dateinsert = dateinsert;
   }

   public Integer getCategory() {
      if (this.category == null) {
         this.category = 0;
      }

      return this.category;
   }

   public void setCategory(Integer category) {
      this.category = category;
   }

   public Tovar() {
      this.nnum = 0;
      this.name = new String("");
      this.category = 0;
      this.cena0 = new BigDecimal("0.00");
      Attr a = new Attr();
      a.setN(0);
      this.dateinsert = new Date();
      this.checked = false;
      this.ed = "шт.";
      this.cenainue = new BigDecimal("0.00");
      this.cenainrub = new BigDecimal("0.00");
   }

   public Integer getNnum() {
      return this.nnum;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         Tovar other = (Tovar)obj;
         if (this.nnum == null) {
            if (other.nnum != null) {
               return false;
            }
         } else if (!this.nnum.equals(other.nnum)) {
            return false;
         }

         return true;
      }
   }

   public void setNnum(Integer nnum) {
      this.nnum = nnum;
   }

   public String getEd() {
      return this.ed;
   }

   public void setEd(String ed) {
      this.ed = ed;
   }

   public int hashCode() {
      int result = 31 + (this.nnum == null ? 0 : this.nnum.hashCode());
      return result;
   }

   public long hashSum() {
      long ret = (long)this.nnum;
      return ret;
   }

   public BigDecimal getCena0() {
      return this.cena0;
   }

   public void setCena0(BigDecimal cena0) {
      this.cena0 = cena0;
   }

   public String getFile() {
      return this.file == null ? "" : this.file;
   }

   public void setFile(String file) {
      this.file = file;
   }

   public String getBrand() {
      return this.brand == null ? "" : this.brand;
   }

   public void setBrand(String brand) {
      this.brand = brand;
   }

   public String getThing() {
      return this.thing == null ? "" : this.thing;
   }

   public void setThing(String thing) {
      this.thing = thing;
   }

   public String toString() {
      return this.nnum.toString();
   }

   public Integer getTypetovar() {
      return this.typetovar;
   }

   public void setTypetovar(Integer typetovar) {
      this.typetovar = typetovar;
   }

   public Integer getParentnnum() {
      return this.parentnnum;
   }

   public void setParentnnum(Integer parentnnum) {
      this.parentnnum = parentnnum;
   }

   public TovarDTO getDTO() {
      return new TovarDTO(this);
   }

   public Tovar clone() throws CloneNotSupportedException {
      Tovar newTovar = new Tovar();
      newTovar.setBrand(this.brand);
      newTovar.setDateinsert(new Date());
      newTovar.setName(this.name);
      newTovar.setEd(this.ed);
      newTovar.setGroup(this.group);
      newTovar.setThing(this.thing);
      return newTovar;
   }

   public String getAnnotation() {
      return this.annotation;
   }

   public void setAnnotation(String annotation) {
      this.annotation = annotation;
   }

   public Price getPrice() {
      return this.price;
   }

   public void setPrice(Price price) {
      this.price = price;
   }

   public Manager getManager() {
      return this.manager;
   }

   public void setManager(Manager manager) {
      this.manager = manager;
   }

   public Date getDateChanged() {
      return this.dateChanged;
   }

   public void setDateChanged(Date dateChanged) {
      this.dateChanged = dateChanged;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public BigDecimal getQtyRest() {
      return this.qtyRest;
   }

   public void setQtyRest(BigDecimal qtyRest) {
      this.qtyRest = qtyRest;
   }

   public Tovar getTovar() {
      return this;
   }

   public boolean isChanged() {
      return this.changed;
   }

   public void setChanged(boolean changed) {
      this.changed = changed;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public static String getDescriptionClass() {
      return "Товар";
   }
}
