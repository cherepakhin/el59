package ru.perm.v.el59.office.iproviders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequestMessage implements Serializable {
   private static final long serialVersionUID = -8729102637313092336L;
   public static final Integer TDOCTYPE = 0;
   public static final Integer VALUETYPE = 1;
   public Integer typeRequest;
   public List<RequestItem> listRequestItem;

   public RequestMessage() {
      this.listRequestItem = new ArrayList();
   }

   public RequestMessage(Integer typeRequest) {
      this();
      this.typeRequest = typeRequest;
   }
}
