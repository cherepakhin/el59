package ru.perm.v.el59.office.util;

import org.apache.camel.Body;
import org.apache.commons.io.FileUtils;
import ru.perm.v.el59.office.db.dto.ChangeGroup;
import ru.perm.v.el59.office.iproviders.IHistoryTovarProvider;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Создание dbf-файла с товарами, пренесенными из группы в группу.
 *
 * @author vasi
 */
public class CreatorChangeNN {
    private IHistoryTovarProvider historyTovarProvider;
    private Integer qtyDays = -1;
    private String filenameSrc;
    private String filenameDst;

    public byte[] process(@Body Object body) {
        try {
            Logger.getLogger(this.getClass().getName()).info("Выгрузка ChangeNN.Начало");
            createDbf();
            byte[] data = FileUtils.readFileToByteArray(new File(filenameDst));
            Logger.getLogger(this.getClass().getName()).info("Выгрузка ChangeNN.Конец");
            return data;
        } catch (InstantiationException e) {
            Logger.getLogger(this.getClass().getName()).severe(
                    "Ошибка при выгрузке ChangeNN");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Logger.getLogger(this.getClass().getName()).severe(
                    "Ошибка при выгрузке ChangeNN");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(this.getClass().getName()).severe(
                    "Ошибка при выгрузке ChangeNN" + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).severe(
                    "Ошибка при выгрузке ChangeNN" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).severe(
                    "Ошибка при выгрузке ChangeNN" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void createDbf() throws InstantiationException,
            IllegalAccessException, ClassNotFoundException, SQLException,
            IOException {
        List<ChangeGroup> list = getListChangeGroup();
        File destFile = new File(getFilenameDst());
        File srcFile = new File(getFilenameSrc());

        FileUtils.copyFile(srcFile, destFile);
        Class.forName("com.hxtt.sql.dbf.DBFDriver").newInstance();
        Connection conn = DriverManager.getConnection(
                "jdbc:dbf:///" + destFile.getParent() + "?charSet=Cp866", "",
                "");
        // Statement stmt = conn.createStatement();
        PreparedStatement ps = conn.prepareStatement("delete from \""
                + destFile.getName() + "\"");
        ps.execute();

        ps = conn.prepareStatement("insert into \"" + destFile.getName()
                + "\" (" + "oper,grupnew,nnumnew,grup1,nnum1" + ") values ("
                + "?,?,?,?,?" + ")");
        for (ChangeGroup changeGroup : list) {
            String snnum = changeGroup.tovar.getNnum().toString();
            if (snnum.length() == 7) {
                snnum = '0' + snnum;
            }
            ps.setString(1, "G");
            ps.setString(2, changeGroup.newBestGroup.getBestAsString());
            ps.setString(3, snnum);
            ps.setString(4, changeGroup.oldBestGroup.getBestAsString());
            ps.setString(5, snnum);
            ps.execute();
        }
        /*
         * Date startDate1 = new GregorianCalendar(2013,0, 1, 0, 0).getTime();
         * Date endDate1 = new Date();
         *
         * long diff = endDate1.getTime() - startDate1.getTime(); Long v = diff
         * / (1000L*60L*60L*24L);
         */
        ps.setString(1, "V");
        ps.setString(2, "1");
        ps.setString(3, "");
        ps.setString(4, "");
        ps.setString(5, "");
        ps.execute();
        ps.close();
        conn.close();

    }

    private List<ChangeGroup> getListChangeGroup() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, getQtyDays());
        List<ChangeGroup> list = historyTovarProvider.getChangeGroup(
                cal.getTime(), new Date());
        return list;
    }

    public IHistoryTovarProvider getHistoryTovarProvider() {
        return historyTovarProvider;
    }

    public void setHistoryTovarProvider(
            IHistoryTovarProvider historyTovarProvider) {
        this.historyTovarProvider = historyTovarProvider;
    }

    public Integer getQtyDays() {
        return qtyDays;
    }

    public void setQtyDays(Integer qtyDays) {
        this.qtyDays = qtyDays;
    }

    public String getFilenameSrc() {
        return filenameSrc;
    }

    public void setFilenameSrc(String filenameSrc) {
        this.filenameSrc = filenameSrc;
    }

    public String getFilenameDst() {
        return filenameDst;
    }

    public void setFilenameDst(String filenameDst) {
        this.filenameDst = filenameDst;
    }

}
