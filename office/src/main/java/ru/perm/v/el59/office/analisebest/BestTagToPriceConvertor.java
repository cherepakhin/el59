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
 * Создает из списка ценников List<BestTag> dbf-файл для подгрузки в программу Ценники
 *
 * @author vasi
 */
public class BestTagToPriceConvertor {

    private final static String PREFIX_DBF_PATH = "jdbc:dbf:////";

    private String templatePriceDBF = "price.dbf";
    DecimalFormat df = new DecimalFormat("0.00");
    private String sqlInsert = "insert into price(grup,nnum,name,cena,cena1" +
            ") values (" + "?,?,?,?,?" + ")";

    public byte[] createZip(List<BestTag> bestTags) throws IOException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException, SQLException {
        String tmpDir = FileUtils.getTempDirectoryPath();
        FileUtils.copyFileToDirectory(new File(getClass().getResource(templatePriceDBF).getFile()), new File(tmpDir));

        Class.forName("com.hxtt.sql.dbf.DBFDriver").newInstance();
        Connection conn = DriverManager.getConnection(PREFIX_DBF_PATH +
                tmpDir + "?charSet=Cp866", "", "");
        Statement stmt = conn.createStatement();
        PreparedStatement ps = conn.prepareStatement(getSqlInsert());
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
            ps.setBigDecimal(4, bestTag.cena);
            ps.setBigDecimal(5, bestTag.label);
            ps.execute();
        }
        ps.close();
        stmt.close();
        File zipFile = new File(tmpDir + File.separator + "price.zip");
        File priceDbf = new File(tmpDir + File.separator + "price.dbf");
        File[] files = {priceDbf};
        Zip.addFilesToZip(zipFile, files);
        byte[] data = FileUtils.readFileToByteArray(zipFile);
        FileUtil.deleteFile(priceDbf);
        FileUtil.deleteFile(zipFile);
        return data;
    }

    public String getTemplatePriceDBF() {
        return templatePriceDBF;
    }

    public void setTemplatePriceDBF(String templatePriceDBF) {
        this.templatePriceDBF = templatePriceDBF;
    }

    public String getSqlInsert() {
        return sqlInsert;
    }

    public void setSqlInsert(String sqlInsert) {
        this.sqlInsert = sqlInsert;
    }
}
