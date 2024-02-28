package ru.el59.office.db;

public class CheckSrcDstMove {
   private CheckMove src = new CheckMove();
   private CheckMove dst = new CheckMove();

   public CheckMove getSrc() {
      return this.src;
   }

   public void setSrc(CheckMove src) {
      this.src = src;
   }

   public CheckMove getDst() {
      return this.dst;
   }

   public void setDst(CheckMove dst) {
      this.dst = dst;
   }
}
