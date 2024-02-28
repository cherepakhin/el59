package ru.perm.v.el59.office.iproviders;

import java.io.IOException;
import java.util.List;
import ru.el59.office.db.Tovar;

public interface ICommerceMLCatalog {
   boolean uploadFile(byte[] var1, String var2) throws IOException;

   void emptyDestDirectory() throws IOException;

   boolean setTovarInfo(Tovar var1, String var2) throws Exception;

   List<String> findNameGood(String var1);
}
