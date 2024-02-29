package ru.perm.v.el59.office.analisebest;

import org.apache.commons.io.filefilter.SuffixFileFilter;
//import java.util.logging.Logger; 
import org.jboss.logging.Logger;
import ru.perm.v.el59.dto.BestTag;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Подготовка файла-протокола для БЕСТ. Протокол описывает у каких товаров
 * изменились названия, аннотации, ценник
 *
 * @author vasi
 */
public class ProtocolForTag {

    private static Logger LOGGER = Logger.getLogger(ProtocolForTag.class);
    private ICreatotBestTag creatorBestTag;

    /**
     * Проанализировать zip-файлы в каталоге catalog
     *
     * @param catalog - каталог с zip-файлами БЕСТ с mlabel,mkart
     */
    public void analise(String catalog) {
        DbfToBestTagConvertor convertor = new DbfToBestTagConvertor();
        File dir = new File(catalog);
        String[] files = dir.list(new SuffixFileFilter(".zip"));
        for (int i = 0; i < files.length; i++) {
            try {
                File zipFile = new File(files[i]);
                String shopCod = convertor.getShopCodFromZip(zipFile);
                List<BestTag> bestTags = convertor.prepareBestTagsFromDirectory(zipFile);
                analise(bestTags, shopCod);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(e);
            }
        }
    }

    /**
     * Обработать список ценников БЕСТ и получить протокол изменений
     *
     * @param bestTags - ценники БЕСТ
     * @param shopCod  - код магазина
     * @return протокол изменений
     */
    public List<BestTag> analise(List<BestTag> bestTags, String shopCod) {
        ArrayList<BestTag> ret = new ArrayList<BestTag>();
        for (BestTag shopTag : bestTags) {
            BestTag officeTag;
            try {
                officeTag = getCreatorBestTag().getBestTag(shopTag.nnum, shopCod);
                // Такое возможно, когда нет цены на товар
                if (officeTag == null) {
                    Logger.getLogger(this.getClass().getName()).severe(String.format("Ценник для nnum=%d null", shopTag.nnum));
                    continue;
                }
                // КОСТЫЛЬ. Поле grup(группа) в бесте кривая.
                officeTag.group = shopTag.group;
                if (officeTag != null && !officeTag.equals(shopTag)) {
                    // Вид ценника не меняется при смене
                    // label=0 (Можно все) на label=2 (Оснновной товар >20% и <25%)
                    // и наоборот
                    int labelOffice = officeTag.label.intValue();
                    int labelShop = shopTag.label.intValue();
                    if ((labelShop == 0 && labelOffice == 2) || (labelShop == 2 && labelOffice == 0)) {
                        continue;
                    }

                    ret.add(officeTag);
                }
            } catch (Exception e) {
                LOGGER.error(e);
                e.printStackTrace();
            }
        }
        return ret;
    }

    public ICreatotBestTag getCreatorBestTag() {
        return creatorBestTag;
    }

    public void setCreatorBestTag(ICreatotBestTag creatorBestTag) {
        this.creatorBestTag = creatorBestTag;
    }
}
