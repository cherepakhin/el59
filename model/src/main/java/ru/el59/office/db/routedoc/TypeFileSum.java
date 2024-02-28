package ru.el59.office.db.routedoc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import ru.el59.office.db.DocFile;
import ru.el59.office.db.TypeFile;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class TypeFileSum extends AUIBean implements Serializable {
   private static final long serialVersionUID = -778579189589328231L;
   @UI(
      readonly = false,
      title = "Тип файла",
      visible = true,
      complex = true,
      width = 10
   )
   private TypeFile typeFile;
   @UI(
      readonly = false,
      title = "Сумма",
      visible = true,
      complex = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summa = new BigDecimal("0.00");
   private List<DocFile> listDocFile = new ArrayList();
   private Boolean sending = false;

   public TypeFileSum(TypeFile typeFile) {
      this.typeFile = typeFile;
   }

   public TypeFile getTypeFile() {
      return this.typeFile;
   }

   public void setTypeFile(TypeFile typeFile) {
      this.typeFile = typeFile;
   }

   public BigDecimal getSumma() {
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
   }

   public List<DocFile> getListDocFile() {
      return this.listDocFile;
   }

   public void setListDocFile(List<DocFile> listDocFile) {
      this.listDocFile = listDocFile;
   }

   public Boolean getSending() {
      return this.sending;
   }

   public void setSending(Boolean sending) {
      this.sending = sending;
   }
}
