package ru.perm.v.el59.office.analisebest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import ru.perm.v.el59.office.dto.BestTag;
import ru.perm.v.el59.office.util.UnZip;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbfToBestTagConvertor {

    private static Logger LOGGER = Logger.getLogger(DbfToBestTagConvertor.class);
    private final static String PREFIX_DBF_PATH = "jdbc:dbf:////";
    private Connection connection;

    public List<BestTag> prepareBestTagsFromByteArray(String filename, byte[] data)
            throws Exception {
        File tmpFile = File.createTempFile(filename, "zip");
        tmpFile.deleteOnExit();
        FileUtils.writeByteArrayToFile(tmpFile, data);
        return prepareBestTagsFromDirectory(tmpFile);
    }

    /**
     * Получение бестовских ценников из файла с dbf-файлами mlabel,mkart. Код
     * магазина берется из имени файла Пример: 07258.zip - код магазина 07258
     * (ПБТ)
     *
     * @param zipFile - zip-файл с mlabel,mkart
     * @return
     * @throws Exception
     */
    public List<BestTag> prepareBestTagsFromDirectory(File zipFile) throws Exception {
        // Проверка имени файла
        String shopCod = getShopCodFromZip(zipFile);
        String directory = unzip(zipFile, shopCod);

        ArrayList<BestTag> ret = new ArrayList();
        setConnection(directory);
        PreparedStatement pstmt = getConnection()
                .prepareStatement("select ml.grup,ml.nnum,ml.name,ml.annot,ml.ocena3,ml.ocena2 " +
                        "from mlabel ml, mkart mk where ml.nnum=mk.nnum and ml.grup=mk.grup and mk.koltek>0 and " +
                        "ml.ocena2 is not null and ml.ocena3 is not null " +
                        "order by ml.nnum");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            BestTag tag = new BestTag(rs.getString(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getBigDecimal(5),
                    rs.getBigDecimal(6),
                    ""
            );
            ret.add(tag);
        }
        pstmt.close();
        closeConnection();
        deleteTempDir(directory);
        return ret;
    }

    private void deleteTempDir(String directory) {
        try {
            FileUtils.deleteDirectory(new File(directory));
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    /**
     * Получение кода магазина из названия файла
     *
     * @param zipFile
     * @return
     * @throws Exception - при ошибочном имени файла
     */
    public String getShopCodFromZip(File zipFile) throws Exception {
        Pattern p = Pattern.compile("(\\d{5})");
        Matcher m = p.matcher(zipFile.getName());
        if (m.find()) {
            return m.group(0);
        }
        throw new Exception(
                "В названии zip-файла должны не найден код магазина. Файл:"
                        + zipFile.getName());

    }

    /**
     * Распаковка zip-файла
     *
     * @param zipFile
     * @param shopCod
     * @return каталог с распакованными файлами
     * @throws Exception
     */
    public String unzip(File zipFile, String shopCod) throws Exception {
        if (!zipFile.exists()) {
            throw new Exception("Zip-файл " + zipFile + " не существует.");
        }
        String tmpDir = FileUtils.getTempDirectoryPath();
        tmpDir = tmpDir + File.separator + shopCod;
        UnZip.unzipMyZip(zipFile.getAbsolutePath(), tmpDir);
        return tmpDir;
    }

    private Connection getConnection() throws Exception {
        if (connection == null) {
            throw new Exception("Connection DBF is null");
        }
        return connection;
    }

    private void setConnection(String tmpDir) throws SQLException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        Class.forName("com.hxtt.sql.dbf.DBFDriver").newInstance();
        connection = DriverManager.getConnection(PREFIX_DBF_PATH + tmpDir
                + "?charSet=Cp866&loadIndices=false", "", "");
    }

    private void closeConnection() throws SQLException {
        if (connection != null) {
            connection.commit();
            connection.close();
            if (!connection.isClosed()) {
                LOGGER.error("Соединение с dbf не закрылось");
            }
        }
    }

}
