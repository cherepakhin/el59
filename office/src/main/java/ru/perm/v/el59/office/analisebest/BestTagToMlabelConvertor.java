package ru.perm.v.el59.office.analisebest;

import org.apache.camel.util.FileUtil;
import org.apache.commons.io.FileUtils;
import ru.perm.v.el59.dto.BestTag;
import ru.perm.v.el59.office.util.Zip;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Создает из списка ценников List<BestTag> dbf-файл mlabel
 *
 * @author vasi
 */
public class BestTagToMlabelConvertor {

    private final static String PREFIX_DBF_PATH = "jdbc:dbf:////";

    private String templateMlabelDBF = "";
    private String templateMlabelFPT = "";
    DecimalFormat df = new DecimalFormat("0.00");

    protected void copyTemplMlabel(String destDir)
            throws IOException {
        FileUtils.copyFileToDirectory(new File(templateMlabelDBF), new File(destDir));
        FileUtils.copyFileToDirectory(new File(templateMlabelFPT), new File(destDir));
    }

    public byte[] createZip(List<BestTag> bestTags) throws IOException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException, SQLException {
        String tmpDir = FileUtils.getTempDirectoryPath();
        copyTemplMlabel(tmpDir);

        Class.forName("com.hxtt.sql.dbf.DBFDriver").newInstance();
        Connection conn = DriverManager.getConnection(PREFIX_DBF_PATH +
                tmpDir + "?charSet=Cp866", "", "");
        Statement stmt = conn.createStatement();
        PreparedStatement ps = conn.prepareStatement("insert into mlabel(grup,nnum,name,ed,annot,ocena3,ocena2" +
                ") values (" + "?,?,?,?,?,?,?" + ")");
        for (BestTag bestTag : bestTags) {
            ps.setString(1, bestTag.group);
            String snnum = "";
            if (bestTag.nnum.toString().trim().length() < 8) {
                snnum = "0" + bestTag.nnum.toString().trim();
            } else {
                snnum = bestTag.nnum.toString().trim();
            }
            ps.setString(2, snnum);
            String name = bestTag.name.trim();
            if (name.length() > 34) {
                name = name.substring(0, 33);
            }
            ps.setString(3, name);
            ps.setString(4, "шт.");
            ps.setString(5, bestTag.annot);
            ps.setBigDecimal(6, bestTag.cena);
            ps.setBigDecimal(7, bestTag.label);
            ps.execute();
        }
        ps.close();
        stmt.close();
        File zipFile = new File(tmpDir + File.separator + "mlabel.zip");
        File mlabelDbf = new File(tmpDir + File.separator + "mlabel.dbf");
        File mlabelFpt = new File(tmpDir + File.separator + "mlabel.fpt");
        File[] files = {mlabelDbf, mlabelFpt};
        Zip.addFilesToZip(zipFile, files);
        byte[] data = FileUtils.readFileToByteArray(zipFile);
        FileUtil.deleteFile(mlabelDbf);
        FileUtil.deleteFile(mlabelFpt);
        FileUtil.deleteFile(zipFile);
        return data;
    }

    public String getTemplateMlabelDBF() {
        return templateMlabelDBF;
    }

    public void setTemplateMlabelDBF(String templateMlabelDBF) {
        this.templateMlabelDBF = templateMlabelDBF;
    }

    public String getTemplateMlabelFPT() {
        return templateMlabelFPT;
    }

    public void setTemplateMlabelFPT(String templateMlabelFPT) {
        this.templateMlabelFPT = templateMlabelFPT;
    }
}
