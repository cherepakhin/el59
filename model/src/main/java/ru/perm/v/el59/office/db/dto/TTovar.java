package ru.perm.v.el59.office.db.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ru.perm.v.el59.office.db.Tovar;
import ru.el59.ui.AUIBean;
import ru.el59.ui.UI;

public class TTovar extends AUIBean implements Serializable {
   private static final long serialVersionUID = -4700433099353967817L;
   @UI(
      readonly = false,
      title = "Исходное товар",
      visible = true,
      complex = true,
      width = 30
   )
   private ItemDTO itemDTO = new ItemDTO();
   @UI(
      readonly = false,
      title = "Выбранный товар",
      visible = true,
      complex = true,
      width = 30
   )
   private Tovar selected = new Tovar();
   private ArrayList<Tovar> listTovar = new ArrayList();
   private List<String> words = new ArrayList();
   @UI(
      readonly = false,
      title = "Уже был обработан",
      visible = true,
      complex = true,
      width = 3
   )
   private String label = "";
   protected static String descriptionClass = "Товар-Аналог";

   public ItemDTO getItemDTO() {
      return this.itemDTO;
   }

   public void setItemDTO(ItemDTO itemDTO) {
      this.itemDTO = itemDTO;
   }

   public Tovar getSelected() {
      return this.selected;
   }

   public void setSelected(Tovar selected) {
      this.selected = selected;
   }

   public ArrayList<Tovar> getListTovar() {
      return this.listTovar;
   }

   public void setListTovar(ArrayList<Tovar> listTovar) {
      this.listTovar = listTovar;
   }

   public List<String> getWords() {
      return this.words;
   }

   public void setWords(List<String> words) {
      this.words = words;
   }

   public String getLabel() {
      return this.label;
   }

   public void setLabel(String label) {
      this.label = label;
   }
}
