package ru.perm.v.el59.office.analisebest;

import org.apache.camel.Body;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                    emailer.send(null, shop.getEmail() + ";bob1970@yandex.ru", getMailMessage(),
                            getMailSubject() + "(" + shop.getName() + ")", attachFiles, true);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
                Logger.getLogger(this.getClass()).error(e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Logger.getLogger(this.getClass()).error(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Logger.getLogger(this.getClass()).error(e);
            } catch (IOException e) {
                e.printStackTrace();
                Logger.getLogger(this.getClass()).error(e);
            } catch (SQLException e) {
                e.printStackTrace();
                Logger.getLogger(this.getClass()).error(e);
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
