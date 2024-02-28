package ru.perm.v.el59.office.iproviders;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.perm.v.el59.office.iproviders.critery.DocSummaryCritery;
import ru.el59.office.db.Doc;
import ru.el59.office.db.DocFile;
import ru.el59.office.db.DocItem;
import ru.el59.office.db.Manager;
import ru.el59.office.db.TypeFile;
import ru.el59.office.db.routedoc.DocSummary;

public interface IDocFileProvider extends IGenericDao<DocFile, Long> {
   List<DocFile> getListDocFile(Doc var1);

   byte[] getBody(DocFile var1) throws IOException;

   void deleteByDoc(Doc var1) throws Exception;

   List<DocItem> loadDbf(DocFile var1) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;

   List<DocSummary> getListDocSummary(DocSummaryCritery var1);

   ArrayList<TypeFile> getListTypeFile();

   List<Manager> getAutorsDocFile();

   List<DocFile> refresh(List<DocFile> var1);

   String sendByEmail(Manager var1, String var2, String var3, String var4, List<DocFile> var5);

   String agree(DocFile var1, Boolean var2, Manager var3) throws Exception;

   String delete(DocFile var1, Manager var2) throws Exception;

   List<DocFile> getFreeForReestr(Date var1, Date var2);
}
