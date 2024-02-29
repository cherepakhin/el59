package ru.perm.v.el59.office.camelcontext.receiver;

import ru.el59.office.shopmodel.DocTitle;
import ru.perm.v.el59.dto.DocTitleDTO;

public class ConvertorXmlDocTitle extends
        ConvertorFromXML<DocTitleDTO, DocTitle> {

    @Override
    public String fromXmlToMessageEntity(Object body) {
        String xml = (String) body;
// TODO: check xml comment
//		XStream xstream = getXStream();
//		xstream.alias("message", MessageDocTitleDTO.class);
//		xstream.aliasField("command", MessageDocTitleDTO.class, "typeCommand");
//		xstream.alias("entity", type);
//		xstream.alias(getEntity(), type);
//		try {
//			message = getMessageFromXml(xml);
//			DocTitleDTO dto = message.getEntity();
//			DocTitle docTitle = new DocTitle();
//			docTitle = fillFromDTO(dto, docTitle, message.getShopCod());
//			Logger.getLogger(this.getClass().getName()).info(
//					String.format("Shop %s;Command %s;%s %s ", message
//							.getShopCod(), message.getTypeCommand(), dto
//							.getTypedoc().getName(), dto.getNumdoc()));
//			doMessage(docTitle);
//		} catch (Exception e) {
//			Logger.getLogger(this.getClass().getName()).error("Ошибка при создании doctitle ", e);
//			e.printStackTrace();
//		}
        return xml;
    }

    private void createOrUpdate(DocTitle docTitle) throws Exception {
//TODO:`commented from import
//		DocTitleCritery critery = new DocTitleCritery();
//		critery.nn = docTitle.getNn();
//		critery.shops.add(docTitle.getShop());
//		critery.deleted=true; // с удаленными
//		List<DocTitle> list = ((IDocTitleProvider) getDao()).getByCritery(critery);
//		if (list.size() > 0) {
//			DocTitle d = list.get(0);
//			d.setComment(docTitle.getComment());
//			d.setContragent(docTitle.getContragent());
//			d.setDdate(docTitle.getDdate());
//			d.setDeleted(docTitle.getDeleted());
//			d.setK1(docTitle.getK1());
//			d.setK2(docTitle.getK2());
//			d.setNumdoc(docTitle.getNumdoc());
//			d.setParent(docTitle.getParent());
//			d.setShop(docTitle.getShop());
//			d.setSummain(docTitle.getSummain());
//			d.setSummainold(docTitle.getSummainold());
//			d.setSummaout(docTitle.getSummaout());
//			d.setTypeDocShop(docTitle.getTypeDocShop());
//			d.setTypePrice(docTitle.getTypePrice());
//			d.setTypeStock(docTitle.getTypeStock());
//			super.update(d);
//		} else {
//			super.create(docTitle);
//		}
    }

    @Override
    protected void create(DocTitle o) throws Exception {
        createOrUpdate(o);
    }

    @Override
    protected void update(DocTitle o) throws Exception {
        createOrUpdate(o);
    }

    private DocTitle fillFromDTO(DocTitleDTO dto, DocTitle docTitle,
                                 String shopCod) {
        //TODO:`commented from import
//        docTitle = ((IDocTitleProvider) getDao()).getByDTO(dto, shopCod);
//        return docTitle;
        return null;
    }

}
