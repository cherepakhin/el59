package ru.perm.v.el59.office.analisebest;

import org.apache.camel.Body;
import ru.perm.v.el59.dto.BestTag;
import ru.perm.v.el59.dto.message.MessageBestTags;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.dto.FileAttach;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.emailer.IEmailer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Отправка изменений ценников в магазин
 *
 * @author vasi
 */
public class SenderBestTags {

    private BestTagToPriceConvertor bestTagToPriceConvertor;
    private IShopProvider shopProvider;
    private IEmailer emailer;

    private String mailMessage = "";
    private String mailSubject = "";

    public Object process(@Body Object body) {
        MessageBestTags messageBestTags = (MessageBestTags) body;
        if (messageBestTags.getEntity() != null) {
            try {
                List<BestTag> bestTags = messageBestTags.getEntity().getTags();
                if (bestTags.size() > 0) {
                    byte[] protocolZip = getBestTagToPriceConvertor()
                            .createZip(messageBestTags.getEntity().getTags());
                    FileAttach f = new FileAttach();
                    f.name = "price.zip";
                    f.body = protocolZip;
                    List<FileAttach> attachFiles = new ArrayList<FileAttach>();
                    attachFiles.add(f);
                    Shop shop = getShopProvider().read(
                            messageBestTags.getShopCod());
                    // Для тестов
/*					emailer.send(null, "bob1970@yandex.ru", getMailMessage(),
							getMailSubject()+"("+shop.getName()+")", attachFiles);*/
                    // Отсылка директору
                    //TODO : send email
//                    emailer.send(shop.getEmail() + ";bob1970@yandex.ru", getMailMessage(),
//                            getMailSubject() + "(" + shop.getName() + ")", attachFiles, true);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
                Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            } catch (SQLException e) {
                e.printStackTrace();
                Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            }
        }
        return body;
    }

    public BestTagToPriceConvertor getBestTagToPriceConvertor() {
        return bestTagToPriceConvertor;
    }

    public void setBestTagToPriceConvertor(
            BestTagToPriceConvertor bestTagToPriceConvertor) {
        this.bestTagToPriceConvertor = bestTagToPriceConvertor;
    }

    public IShopProvider getShopProvider() {
        return shopProvider;
    }

    public void setShopProvider(IShopProvider shopProvider) {
        this.shopProvider = shopProvider;
    }

    public IEmailer getEmailer() {
        return emailer;
    }

    public void setEmailer(IEmailer emailer) {
        this.emailer = emailer;
    }

    public String getMailMessage() {
        return mailMessage;
    }

    public void setMailMessage(String mailMessage) {
        this.mailMessage = mailMessage;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

}
