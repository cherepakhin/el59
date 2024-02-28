package ru.el59.office.db.service;

import java.io.Serializable;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.Move;
import ru.el59.ui.UI;

public class LOPT extends ATDoc implements Serializable {
   private static final long serialVersionUID = -6287996902080167442L;
   @UI(
      readonly = false,
      title = "Выписка",
      visible = true,
      width = 10,
      complex = true
   )
   private Move move = new Move();
   @UI(
      readonly = false,
      title = "Серийный номер",
      visible = true,
      width = 10
   )
   private String serialnumber = "";
   @UI(
      readonly = false,
      title = "Внешний вид",
      visible = true,
      width = 10
   )
   private String vheshvid = "";
   @UI(
      readonly = false,
      title = "Комплектность",
      visible = true,
      width = 10
   )
   private String complect = "";
   @UI(
      readonly = false,
      title = "Вид гарантии",
      visible = true,
      width = 10
   )
   private Warranty warranty;
   private Contragent contragent;
   @UI(
      readonly = false,
      title = "Покупатель",
      visible = true,
      width = 20
   )
   private Client client = new Client();
   @UI(
      readonly = false,
      title = "Неисправность",
      visible = true,
      width = 20
   )
   private String complaint = "";
   @UI(
      readonly = false,
      title = "Клиентский?",
      visible = true,
      width = 4
   )
   private Boolean isclient = true;
   @UI(
      readonly = false,
      title = "Ремонт?",
      visible = true,
      width = 4
   )
   private Boolean isrepair = false;
   @UI(
      readonly = false,
      title = "В магазине?",
      visible = true,
      width = 4
   )
   private Boolean isinshop = true;
   protected static String descriptionClass = "Квитанция";

   public String getDescriptionClass() {
      return descriptionClass;
   }

   public String getComplaint() {
      return this.complaint;
   }

   public void setComplaint(String complaint) {
      this.complaint = complaint;
   }

   public String getSerialnumber() {
      return this.serialnumber;
   }

   public void setSerialnumber(String serialnumber) {
      this.serialnumber = serialnumber;
   }

   public Move getMove() {
      return this.move;
   }

   public void setMove(Move move) {
      this.move = move;
   }

   public String getVheshvid() {
      return this.vheshvid;
   }

   public void setVheshvid(String vheshvid) {
      this.vheshvid = vheshvid;
   }

   public String getComplect() {
      return this.complect;
   }

   public void setComplect(String complect) {
      this.complect = complect;
   }

   public Warranty getWarranty() {
      return this.warranty;
   }

   public void setWarranty(Warranty warranty) {
      this.warranty = warranty;
   }

   public Client getClient() {
      return this.client;
   }

   public void setClient(Client client) {
      this.client = client;
   }

   public String toString() {
      String ret = "";
      if (this.tdoc != null) {
         ret = ret + this.tdoc.toString() + " ";
      }

      if (this.move != null) {
         ret = ret + "Выписка " + this.move.toString() + " ";
      }

      if (this.client != null) {
         ret = ret + "Покупатель " + this.client.toString();
      }

      return ret;
   }

   public Boolean getIsclient() {
      if (this.isclient == null) {
         this.isclient = true;
      }

      return this.isclient;
   }

   public void setIsclient(Boolean isclient) {
      this.isclient = isclient;
   }

   public Boolean getIsrepair() {
      if (this.isrepair == null) {
         this.isrepair = true;
      }

      return this.isrepair;
   }

   public void setIsrepair(Boolean isrepair) {
      this.isrepair = isrepair;
   }

   public Boolean getIsinshop() {
      if (this.isinshop == null) {
         this.isinshop = true;
      }

      return this.isinshop;
   }

   public void setIsinshop(Boolean isinshop) {
      this.isinshop = isinshop;
   }

   public Contragent getContragent() {
      return this.contragent;
   }

   public void setContragent(Contragent contragent) {
      this.contragent = contragent;
   }

   public String getContent() {
      return this.toString();
   }
}
