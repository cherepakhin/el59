package ru.perm.v.el59.office.db;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class DocFile extends AEntity implements Comparable<DocFile> {
   private static final long serialVersionUID = -8103945447071551459L;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "Комментарий",
      visible = true,
      width = 16
   )
   private String comment = "";
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
      title = "Создал",
      visible = true
   )
   private Manager manager;
   @UI(
      readonly = false,
      title = "Согласован",
      visible = true,
      width = 4
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
      title = "Документ",
      visible = true
   )
   private Doc doc;
   @UI(
      readonly = false,
      title = "Б/У",
      visible = true,
      complex = true,
      width = 4
   )
   private Boolean bu = false;
   @UI(
      readonly = false,
      title = "Отослан",
      visible = true,
      complex = true,
      width = 10
   )
   private Date dateSending;
   @UI(
      readonly = false,
      title = "Тип файла",
      visible = true
   )
   private TypeFile typeFile;
   @UI(
      readonly = false,
      title = "Номер док-та",
      visible = true,
      complex = true,
      width = 10
   )
   private String numdoc = "";
   private byte[] body;

   public static String getDescriptionClass() {
      return "Файл документа";
   }

   public DocFile() {
      this.summa = new BigDecimal("0.00");
      Calendar c = Calendar.getInstance();
      c.set(1, 2001);
      c.set(2, 0);
      c.set(5, 1);
      c.set(10, 0);
      c.set(12, 0);
      c.set(13, 0);
      c.set(14, 0);
      this.dateAgreed = c.getTime();
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Manager getManager() {
      return this.manager;
   }

   public void setManager(Manager manager) {
      this.manager = manager;
   }

   public byte[] getBody() {
      return this.body;
   }

   public void setBody(byte[] body) {
      this.body = body;
   }

   public Doc getDoc() {
      return this.doc;
   }

   public void setDoc(Doc doc) {
      this.doc = doc;
   }

   public BigDecimal getSumma() {
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
   }

   public TypeFile getTypeFile() {
      return this.typeFile;
   }

   public void setTypeFile(TypeFile typeFile) {
      this.typeFile = typeFile;
   }

   public Boolean getBu() {
      return this.bu;
   }

   public void setBu(Boolean bu) {
      this.bu = bu;
   }

   public Date getDateSending() {
      return this.dateSending;
   }

   public void setDateSending(Date dateSending) {
      this.dateSending = dateSending;
   }

   public int compareTo(DocFile o) {
      int ret = this.ddate.compareTo(o.getDdate());
      if (ret == 0) {
         int ret1 = this.doc.compareTo(o.getDoc());
         return ret1 == 0 ? this.typeFile.compareTo(o.getTypeFile()) : ret1;
      } else {
         return ret;
      }
   }

   public Boolean getAgreed() {
      return this.agreed;
   }

   public void setAgreed(Boolean agreed) {
      this.agreed = agreed;
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

   public String getNumdoc() {
      return this.numdoc;
   }

   public void setNumdoc(String numdoc) {
      this.numdoc = numdoc;
   }
}
