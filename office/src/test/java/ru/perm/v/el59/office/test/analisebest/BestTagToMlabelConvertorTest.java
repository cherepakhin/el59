package ru.perm.v.el59.office.test.analisebest;


import org.junit.jupiter.api.Test;
import ru.perm.v.el59.dto.BestTag;
import ru.perm.v.el59.office.analisebest.BestTagToMlabelConvertor;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BestTagToMlabelConvertorTest {

    @Test
    public void test() {
        BestTagToMlabelConvertor convertor = new BestTagToMlabelConvertor();
        convertor.setTemplateMlabelDBF("/home/vasi/temp/templ/mlabel.dbf");
        convertor.setTemplateMlabelFPT("/home/vasi/temp/templ/mlabel.fpt");
        List<BestTag> bestTags = new ArrayList<BestTag>();
        bestTags.add(new BestTag("", 1, "1", "1", new BigDecimal("1.10"),
                new BigDecimal("1.00"), ""));
        bestTags.add(new BestTag("", 2, "2", "2", new BigDecimal("2.20"),
                new BigDecimal("2.00"), ""));
        bestTags.add(new BestTag("", 1, "11", "11", new BigDecimal("11.11"),
                new BigDecimal("1.00"), ""));
        bestTags.add(new BestTag("", 2, "21", "21", new BigDecimal("2.21"),
                new BigDecimal("2.00"), ""));
        try {
            convertor.createZip(bestTags);
            assertTrue(true);
        } catch (InstantiationException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

}
