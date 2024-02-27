package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BestTags implements Serializable {
   private static final long serialVersionUID = -4569865616152975641L;
   List<BestTag> tags = new ArrayList();

   public BestTags() {
   }

   public BestTags(List<BestTag> list) {
      this.tags = list;
   }

   public List<BestTag> getTags() {
      return this.tags;
   }

   public void setTags(List<BestTag> tags) {
      this.tags = tags;
   }
}
