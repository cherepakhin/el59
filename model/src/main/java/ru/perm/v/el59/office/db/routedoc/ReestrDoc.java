package ru.perm.v.el59.office.db.routedoc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.office.db.DocFile;
import ru.perm.v.el59.office.db.Manager;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class ReestrDoc extends AEntity {
   private static final long serialVersionUID = 950583606385671735L;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "Создал",
      visible = true
   )
   private Manager manager;
   @UI(
      readonly = false,
      title = "Согласован",
      visible = true,
      width = 5
   )
   private Boolean agreed = false;
   @UI(
      readonly = false,
      title = "Дата согласования",
      visible = true,
      width = 10
   )
   private Date dateAgreed;
   @UI(
      readonly = false,
      title = "Согласовал",
      visible = true
   )
   private Manager managerAgreed;
   @UI(
      readonly = false,
      title = "Оплачен",
      visible = true,
      width = 5
   )
   private Boolean paid = false;
   @UI(
      readonly = false,
      title = "Сумма",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summa = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Согл.сумма",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaAgree = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Опл.сумма",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaPay = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Документы",
      visible = true,
      complex = true,
      width = 10
   )
   private List<DocFile> listDocFile = new ArrayList();

   public static String getDescriptionClass() {
      return "Реестр счетов на оплату";
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Manager getManager() {
      return this.manager;
   }

   public void setManager(Manager manager) {
      this.manager = manager;
   }

   public Boolean getAgreed() {
      return this.agreed;
   }

   public void setAgreed(Boolean agreed) {
      this.agreed = agreed;
   }

   public Boolean getPaid() {
      return this.paid;
   }

   public void setPaid(Boolean paid) {
      this.paid = paid;
   }

   public Date getDateAgreed() {
      return this.dateAgreed;
   }

   public void setDateAgreed(Date dateAgreed) {
      this.dateAgreed = dateAgreed;
   }

   public Manager getManagerAgreed() {
      return this.managerAgreed;
   }

   public void setManagerAgreed(Manager managerAgreed) {
      this.managerAgreed = managerAgreed;
   }

   public BigDecimal getSumma() {
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
   }

   public BigDecimal getSummaAgree() {
      return this.summaAgree;
   }

   public void setSummaAgree(BigDecimal summaAgree) {
      this.summaAgree = summaAgree;
   }

   public BigDecimal getSummaPay() {
      return this.summaPay;
   }

   public void setSummaPay(BigDecimal summaPay) {
      this.summaPay = summaPay;
   }

   public List<DocFile> getListDocFile() {
      return this.listDocFile;
   }

   public void setListDocFile(List<DocFile> listDocFile) {
      this.listDocFile = listDocFile;
   }
}
