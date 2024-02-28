package ru.perm.v.el59.office.report;

import java.io.Serializable;
import ru.el59.office.db.MoveSummary;
import ru.el59.office.db.Rest;
import ru.el59.ui.AUIBean;
import ru.el59.ui.IUIBean;
import ru.el59.ui.UI;

public class LowSale extends AUIBean implements Serializable, IUIBean {
   private static final long serialVersionUID = -362052338543948L;
   @UI(
      readonly = true,
      title = "Остаток на начало",
      visible = true,
      width = 0
   )
   private Rest rest0;
   private MoveSummary prihod;
   private MoveSummary sale;
   private MoveSummary rashod;
   private MoveSummary vozvrat;
   private Rest restend;
   private MoveSummary restEndCalc;
   private MoveSummary diff;
   protected static String descriptionClass = "Приход-Расход-Остаток";

   public LowSale() {
      Rest r = new Rest();
      this.rest0 = r;
      this.restend = r;
      MoveSummary m = new MoveSummary();
      this.prihod = m;
      this.sale = m;
      this.rashod = m;
      this.vozvrat = m;
      this.restEndCalc = m;
      this.diff = m;
   }

   public Rest getRest0() {
      return this.rest0;
   }

   public void setRest0(Rest rest0) {
      this.rest0 = rest0;
   }

   public MoveSummary getPrihod() {
      return this.prihod;
   }

   public void setPrihod(MoveSummary prihod) {
      this.prihod = prihod;
   }

   public MoveSummary getSale() {
      return this.sale;
   }

   public void setSale(MoveSummary sale) {
      this.sale = sale;
   }

   public MoveSummary getRashod() {
      return this.rashod;
   }

   public void setRashod(MoveSummary rashod) {
      this.rashod = rashod;
   }

   public MoveSummary getVozvrat() {
      return this.vozvrat;
   }

   public void setVozvrat(MoveSummary vozvrat) {
      this.vozvrat = vozvrat;
   }

   public Rest getRestend() {
      return this.restend;
   }

   public void setRestend(Rest restend) {
      this.restend = restend;
   }

   public MoveSummary getRestEndCalc() {
      return this.restEndCalc;
   }

   public void setRestEndCalc(MoveSummary restEndCalc) {
      this.restEndCalc = restEndCalc;
   }

   public MoveSummary getDiff() {
      return this.diff;
   }

   public void setDiff(MoveSummary diff) {
      this.diff = diff;
   }
}
