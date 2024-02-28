package ru.perm.v.el59.office.camelcontext.receiver;

import java.util.List;

import org.apache.log4j.Logger;

import ru.perm.v.el59.office.critery.DocDetailCritery;
import ru.perm.v.el59.office.dto.DocDetailDTO;
import ru.perm.v.el59.office.dto.message.MessageDocDetailDTO;
import ru.perm.v.el59.office.iproviders.shopmodel.IDocDetailProvider;
import ru.perm.v.el59.office.iproviders.web.IDocWItemProvider;
import ru.perm.v.el59.office.shopmodel.DocDetail;

import com.thoughtworks.xstream.XStream;

public class ConvertorXmlDocDetail extends
		ConvertorFromXML<DocDetailDTO, DocDetail> {

	private IDocDetailProvider docDetailProvider;
	private IDocWItemProvider docWItemProvider;

	private String nameInInvoice;

	@Override
	public String fromXmlToMessageEntity(Object body) {
		String xml = (String) body;
		XStream xstream = getXStream();
		xstream.alias("message", MessageDocDetailDTO.class);
		xstream.aliasField("command", MessageDocDetailDTO.class, "typeCommand");
		xstream.alias("entity", type);
		xstream.alias(getEntity(), type);
		try {
			message = getMessageFromXml(xml);
			DocDetailDTO dto = message.getEntity();
			Logger.getLogger(this.getClass()).info(dto.toString());
			DocDetail docDetail = new DocDetail();
			docDetail = fillFromDTO(dto, docDetail, message.getShopCod());
			if (message.getShopCod() == null) {
				Logger.getLogger(this.getClass()).error(
						"ShopCod in message is null");
			}
			if (message.getTypeCommand() == null) {
				Logger.getLogger(this.getClass()).error(
						"TypeCommand in message is null");
			}
			if (message.getTypeCommand() == null) {
				Logger.getLogger(this.getClass()).error(
						"TypeCommand in message is null");
			}
			if (docDetail == null) {
				Logger.getLogger(this.getClass()).error(
						"DocDetail in message is null");
			}
			if (docDetail != null && docDetail.getNn() == null) {
				Logger.getLogger(this.getClass()).error(
						"docDetail.getNn() message is null");
			}
			if (docDetail != null && docDetail.getTovar() == null) {
				Logger.getLogger(this.getClass()).error(
						"docDetail.getTovar() in message is null");
			}
			if (docDetail != null && docDetail.getTovar() != null
					&& docDetail.getTovar().getNnum() == null) {
				Logger.getLogger(this.getClass()).error(
						"docDetail.getTovar().getNnum() in message is null");
			}

			Logger.getLogger(this.getClass()).info(
					String.format("Shop %s;Command %s;Nn %s,nnum %d ",
							message.getShopCod(), message.getTypeCommand(),
							docDetail.getNn(), docDetail.getTovar().getNnum()));
			doMessage(docDetail);
			return xml;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void createOrUpdate(DocDetail docDetail) throws Exception {
		DocDetailCritery critery = new DocDetailCritery();
		critery.nn = docDetail.getNn();
		critery.shops.add(docDetail.getShop());
		List<DocDetail> list = getDocDetailProvider().getByCritery(critery);
		if (list.size() > 0) {
			DocDetail d = list.get(0);
			d.setComment(docDetail.getComment());
			d.setDocTitle(docDetail.getDocTitle());
			d.setNn(docDetail.getNn());
			d.setQty(docDetail.getQty());
			d.setShop(docDetail.getShop());
			d.setSummain(docDetail.getSummain());
			d.setSummaout(docDetail.getSummaout());
			d.setTovar(docDetail.getTovar());
			d.setTypeStock(docDetail.getTypeStock());
			d.setK1(docDetail.getK1());
			d.setK2(docDetail.getK2());
			d.setPrice(docDetail.getPrice());
			d.setDeleted(docDetail.getDeleted());
			super.update(d);
		} else {
			super.create(docDetail);
		}
		getDocWItemProvider().linkToDocWItem(docDetail);
	}

	@Override
	protected void create(DocDetail o) throws Exception {
		createOrUpdate(o);
	}

	@Override
	protected void update(DocDetail o) throws Exception {
		createOrUpdate(o);
	}

	private DocDetail fillFromDTO(DocDetailDTO dto, DocDetail docDetail,
			String shopCod) {
		docDetail = getDocDetailProvider().getByDTO(dto, shopCod);
		return docDetail;
	}

	public IDocDetailProvider getDocDetailProvider() {
		return docDetailProvider;
	}

	public void setDocDetailProvider(IDocDetailProvider docDetailProvider) {
		this.docDetailProvider = docDetailProvider;
	}

	public String getNameInInvoice() {
		return nameInInvoice;
	}

	public void setNameInInvoice(String nameInInvoice) {
		this.nameInInvoice = nameInInvoice;
	}

	public IDocWItemProvider getDocWItemProvider() {
		return docWItemProvider;
	}

	public void setDocWItemProvider(IDocWItemProvider docWItemProvider) {
		this.docWItemProvider = docWItemProvider;
	}

}
