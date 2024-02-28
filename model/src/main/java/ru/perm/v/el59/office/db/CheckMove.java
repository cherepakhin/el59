package ru.perm.v.el59.office.db;

import java.math.BigDecimal;
import java.util.Date;

public class CheckMove {
   private Date ddate;
   private Shop shop;
   private String numdoc = "";
   private Operation operation;
   private BigDecimal qty = new BigDecimal("0.00");
   private BigDecimal sum = new BigDecimal("0.00");
   private Agent agent;
   private Integer typetovar;

   public Integer getTypetovar() {
      return this.typetovar;
   }

   public void setTypetovar(Integer typetovar) {
      this.typetovar = typetovar;
   }

   public Agent getAgent() {
      return this.agent;
   }

   public void setAgent(Agent agent) {
      this.agent = agent;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public String getNumdoc() {
      return this.numdoc;
   }

   public void setNumdoc(String numdoc) {
      this.numdoc = numdoc;
   }

   public Operation getOperation() {
      return this.operation;
   }

   public void setOperation(Operation operation) {
      this.operation = operation;
   }

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }

   public BigDecimal getSum() {
      return this.sum;
   }

   public void setSum(BigDecimal sum) {
      this.sum = sum;
   }

   public Move getMove() {
      Move move = new Move();
      move.setAgentcode(this.agent);
      move.setDdate(this.ddate);
      move.setShop(this.shop);
      move.setNumdoc(this.numdoc);
      move.setOperation(this.operation);
      move.setQty(this.qty);
      move.setSummaout(this.sum);
      return move;
   }
}
