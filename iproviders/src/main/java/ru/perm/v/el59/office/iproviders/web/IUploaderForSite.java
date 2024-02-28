package ru.perm.v.el59.office.iproviders.web;

import java.io.IOException;

public interface IUploaderForSite {
   void upload() throws IOException;

   void runScript() throws IOException;
}
