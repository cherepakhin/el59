package ru.perm.v.el59.office.iproviders;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import ru.perm.v.el59.office.db.Shop;

public interface ILoaderMdocm {
   String load(Shop var1, byte[] var2, Date var3, Date var4) throws IOException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, Exception;

   void deleteMove(Shop var1, Date var2, Date var3) throws Exception;

   String load(String var1, byte[] var2, Integer var3) throws IOException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, Exception;
}
