package ru.perm.v.el59.office.db.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import ru.perm.v.el59.office.db.*;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class MoveDTO extends AUIBean implements Serializable {
   private static final long serialVersionUID = 1988681165647840220L;
   @UI(
      readonly = true,
      title = "Номер",
      visible = true,
      width = 0
   )
   private Long n;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate;
   @UI(
      readonly = false,
      title = "Товар",
      visible = true,
      complex = true,
      width = 20
   )
   private TovarDTO tovar;
   @UI(
      readonly = false,
      title = "№ док-та",
      visible = true,
      width = 6,
      justify = Justify.RIGHT
   )
   private String numdoc;
   @UI(
      readonly = false,
      title = "К-во",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal qty;
   @UI(
      readonly = false,
      title = "Себ-ть",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summain;
   @UI(
      readonly = false,
      title = "Прод.ст-ть",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaout;
   private String agent;
   @UI(
      readonly = false,
      title = "Продавец",
      visible = true,
      width = 20
   )
   private String seller;
   private Agent agentcode;
   private String subtype;
   @UI(
      readonly = false,
      title = "Операция",
      visible = true
   )
   private Operation operation;
   private String vid;
   private String codeoper;
   private String typeoper;
   private BigDecimal pribul;
   private Move parent;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      complex = true,
      width = 10
   )
   private Shop shop;
   private String tostock;
   @UI(
      readonly = false,
      title = "Со склада",
      visible = true
   )
   private TypeStock fromstock;

   public MoveDTO() {
      this.numdoc = "";
      this.qty = new BigDecimal("0.00");
      this.summain = new BigDecimal("0.00");
      this.summaout = new BigDecimal("0.00");
      this.agent = "";
      this.seller = "";
      this.subtype = "";
      this.numdoc = "";
      this.ddate = new Date();
      this.tovar = new TovarDTO();
      this.operation = new Operation();
      this.shop = new Shop();
   }

   public MoveDTO(Move m) {
      this();
      this.n = m.getN();
      this.agent = m.getAgent();
      this.codeoper = m.getCodeoper();
      this.ddate = m.getDdate();
      this.fromstock = m.getFromstock();
      this.numdoc = m.getNumdoc();
      this.operation = m.getOperation();
      this.pribul = m.getPribul();
      this.qty = m.getQty();
      this.seller = m.getSeller();
      this.shop = m.getShop();
      this.subtype = m.getSubtype();
      this.summain = m.getSummain();
      this.summaout = m.getSummaout();
      this.tostock = m.getTostock();
      this.tovar = m.getTovar().getDTO();
      this.typeoper = m.getTypeoper();
      this.vid = m.getVid();
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public TovarDTO getTovar() {
      return this.tovar;
   }

   public void setTovar(TovarDTO tovar) {
      this.tovar = tovar;
   }

   public String getNumdoc() {
      return this.numdoc;
   }

   public void setNumdoc(String numdoc) {
      this.numdoc = numdoc;
   }

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }

   public BigDecimal getSummain() {
      return this.summain;
   }

   public void setSummain(BigDecimal summain) {
      this.summain = summain;
   }

   public BigDecimal getSummaout() {
      return this.summaout;
   }

   public void setSummaout(BigDecimal summaout) {
      this.summaout = summaout;
   }

   public String getAgent() {
      return this.agent;
   }

   public void setAgent(String agent) {
      this.agent = agent;
   }

   public String getSeller() {
      return this.seller;
   }

   public void setSeller(String seller) {
      this.seller = seller;
   }

   public Agent getAgentcode() {
      return this.agentcode;
   }

   public void setAgentcode(Agent agentcode) {
      this.agentcode = agentcode;
   }

   public String getSubtype() {
      return this.subtype;
   }

   public void setSubtype(String subtype) {
      this.subtype = subtype;
   }

   public Operation getOperation() {
      return this.operation;
   }

   public void setOperation(Operation operation) {
      this.operation = operation;
   }

   public String getVid() {
      return this.vid;
   }

   public void setVid(String vid) {
      this.vid = vid;
   }

   public String getCodeoper() {
      return this.codeoper;
   }

   public void setCodeoper(String codeoper) {
      this.codeoper = codeoper;
   }

   public String getTypeoper() {
      return this.typeoper;
   }

   public void setTypeoper(String typeoper) {
      this.typeoper = typeoper;
   }

   public BigDecimal getPribul() {
      return this.pribul;
   }

   public void setPribul(BigDecimal pribul) {
      this.pribul = pribul;
   }

   public Move getParent() {
      return this.parent;
   }

   public void setParent(Move parent) {
      this.parent = parent;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public String getTostock() {
      return this.tostock;
   }

   public void setTostock(String tostock) {
      this.tostock = tostock;
   }

   public TypeStock getFromstock() {
      return this.fromstock;
   }

   public void setFromstock(TypeStock fromstock) {
      this.fromstock = fromstock;
   }

   public static String getDescriptionClass() {
      return "Движение";
   }
}
