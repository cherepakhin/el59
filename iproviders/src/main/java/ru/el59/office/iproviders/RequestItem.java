package ru.el59.office.iproviders;

import java.io.Serializable;

public class RequestItem implements Serializable {
   private static final long serialVersionUID = 7527549498857074786L;
   public String title = "";
   public Object request = "";

   public RequestItem() {
   }

   public RequestItem(String title, Object request) {
      this.title = title;
      this.request = request;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public Object getRequest() {
      return this.request;
   }

   public void setRequest(Object request) {
      this.request = request;
   }
}
