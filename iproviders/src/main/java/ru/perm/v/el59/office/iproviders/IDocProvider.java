package ru.perm.v.el59.office.iproviders;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.perm.v.el59.office.critery.DocCritery;
import ru.el59.office.db.Doc;
import ru.el59.office.db.DocFile;
import ru.el59.office.db.Manager;
import ru.el59.office.db.TypeDoc;
import ru.el59.office.db.dto.DocItemDTO;

public interface IDocProvider extends IGenericDao<Doc, Long> {
   void checkDocItem(DocItemDTO var1) throws Exception;

   void recalc(Doc var1) throws Exception;

   Long create(String var1, String var2, String var3, Manager var4) throws Exception;

   byte[] getBody(DocFile var1) throws IOException;

   DocFile addDocFile(DocFile var1) throws Exception;

   List<DocFile> getListDocFile(Doc var1);

   TypeDoc getTypeDocOrderSupplier();

   String update(Doc var1, Manager var2) throws Exception;

   DocFile addDocDbfFile(DocFile var1) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, Exception;

   void setDateSending(DocFile var1) throws Exception;

   String deleteDocFile(DocFile var1, Manager var2) throws Exception;

   List<Doc> getFreeForPathPage(DocCritery var1);
}
