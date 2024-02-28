package ru.perm.v.el59.office.analisebest;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.file.remote.RemoteFile;
import org.apache.log4j.Logger;
import ru.perm.v.el59.office.critery.RestCritery;
import ru.perm.v.el59.office.critery.TovarCritery;
import ru.perm.v.el59.office.db.HistoryTag;
import ru.perm.v.el59.office.db.RestCur;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.dto.BestTag;
import ru.perm.v.el59.office.dto.BestTags;
import ru.perm.v.el59.office.dto.message.MessageBestTags;
import ru.perm.v.el59.office.dto.message.TypeCommand;
import ru.perm.v.el59.office.iproviders.IHistoryTagProvider;
import ru.perm.v.el59.office.iproviders.IRestCurProvider;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.ITovarProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProtocolProcessor implements Processor {

    private static Logger LOGGER = Logger.getLogger(ProtocolProcessor.class);
    private static SimpleDateFormat df = new SimpleDateFormat("yyMMdd-HHmm");
    private IShopProvider shopProvider;
    private DbfToBestTagConvertor dbfToBestTagConvertor;
    private ProtocolForTag protocolForTag;
    private IHistoryTagProvider historyTagProvider;
    private IRestCurProvider restCurProvider;
    private ITovarProvider tovarProvider;

    @Override
    public void process(Exchange exch) throws Exception {
        Logger.getLogger(this.getClass()).info("Check for BestTags");
        Message in = exch.getIn();

        RemoteFile remoteFile = (RemoteFile) in.getBody();
        if (remoteFile != null) {
            ByteArrayOutputStream b = (ByteArrayOutputStream) remoteFile
                    .getBody();
            byte[] data = b.toByteArray();
            MessageBestTags message = new MessageBestTags();
            try {
                String shopCod = getDbfToBestTagConvertor().getShopCodFromZip(
                        new File(remoteFile.getFileName()));
                List<BestTag> bestTags = getDbfToBestTagConvertor()
                        .prepareBestTagsFromByteArray(remoteFile.getFileName(),
                                data);
                List<BestTag> protocolBestTags = protocolForTag.analise(
                        bestTags, shopCod);

                // Запись в историю
                if (protocolBestTags.size() > 0) {
                    for (BestTag bestTag : protocolBestTags) {
                        HistoryTag historyTag = new HistoryTag();
                        // Выбор категории
                        RestCritery restCritery = new RestCritery();
                        restCritery.tovarCritery = new TovarCritery();
                        restCritery.tovarCritery.nnum.add(bestTag.nnum);
                        restCritery.tovarCritery.arrshops.add(getShopProvider().read(shopCod));
                        List<RestCur> rests = getRestCurProvider().getByCritery(restCritery);
                        Integer category = 0;
                        if (rests.size() > 0) {
                            category = rests.get(0).getCategory();
                        }

                        historyTag.setCategory(category);
                        Tovar tovar = getTovarProvider().read(bestTag.nnum);
                        historyTag.setTovar(tovar);
                        historyTag.setCenaInOnDate(tovar.getCenainrub());
                        historyTag.setDdate(new Date());
                        historyTag.setLabel(bestTag.label);
                        historyTag.setPrice(bestTag.cena);
                        historyTag.setShop(getShopProvider().read(shopCod));
                        historyTag.setPriceName(bestTag.priceName);
                        getHistoryTagProvider().create(historyTag);
                    }
                }
                // Изменения названия сообщения, иначе дальше уходит название
                // файла (н.п. 07863.zip)
                if (!remoteFile.getFileName().isEmpty()) {
                    in.setHeader(
                            "CamelFileName",
                            String.format("besttags-%s-%s.xml", shopCod,
                                    df.format(new Date())));
                }

                message.setShopCod(shopCod);
                message.setTypeCommand(TypeCommand.CREATE);
                message.setClassName(BestTags.class.getSimpleName());
                message.setEntity(new BestTags());
                if (protocolBestTags.size() > 0) {
                    message.getEntity().setTags(protocolBestTags);
                } else {
                    Logger.getLogger(this.getClass()).info(
                            String.format("Нет изменений цен для магазина %s",
                                    shopCod));
                }

            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(e);
            }
            in.setBody(message);
        }
    }

    public DbfToBestTagConvertor getDbfToBestTagConvertor() {
        return dbfToBestTagConvertor;
    }

    public void setDbfToBestTagConvertor(
            DbfToBestTagConvertor dbfToBestTagConvertor) {
        this.dbfToBestTagConvertor = dbfToBestTagConvertor;
    }

    public ProtocolForTag getProtocolForTag() {
        return protocolForTag;
    }

    public void setProtocolForTag(ProtocolForTag protocolForTag) {
        this.protocolForTag = protocolForTag;
    }

    public IShopProvider getShopProvider() {
        return shopProvider;
    }

    public void setShopProvider(IShopProvider shopProvider) {
        this.shopProvider = shopProvider;
    }

    public IHistoryTagProvider getHistoryTagProvider() {
        return historyTagProvider;
    }

    public void setHistoryTagProvider(IHistoryTagProvider historyTagProvider) {
        this.historyTagProvider = historyTagProvider;
    }

    public IRestCurProvider getRestCurProvider() {
        return restCurProvider;
    }

    public void setRestCurProvider(IRestCurProvider restCurProvider) {
        this.restCurProvider = restCurProvider;
    }

    public ITovarProvider getTovarProvider() {
        return tovarProvider;
    }

    public void setTovarProvider(ITovarProvider tovarProvider) {
        this.tovarProvider = tovarProvider;
    }
}
