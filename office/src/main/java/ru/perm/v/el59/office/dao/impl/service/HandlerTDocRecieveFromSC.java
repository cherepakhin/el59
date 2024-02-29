package ru.perm.v.el59.office.dao.impl.service;

import ru.perm.v.el59.office.db.service.ATDoc;
import ru.perm.v.el59.office.db.service.LOPT;
import ru.perm.v.el59.office.db.service.TDocReceiveFromSC;
import ru.perm.v.el59.office.iproviders.RequestItem;
import ru.perm.v.el59.office.iproviders.RequestMessage;

import java.util.List;

public class HandlerTDocRecieveFromSC extends HandlerTDoc<TDocReceiveFromSC> {

	private final static String REPAIRED = "Отремонтировали";
	private final static String NOTREPAIRED = "Не отремонтировали";
	private final static String WARRANTY = "Гарантия производителя";
	private List<IHandlerTDoc> requestRepairAndWarranty;
	private List<IHandlerTDoc> requestRepairAndPaid;
	private List<IHandlerTDoc> requestNotRepair;

	public List<IHandlerTDoc> getRequestRepairAndWarranty() {
		return requestRepairAndWarranty;
	}

	public void setRequestRepairAndWarranty(
			List<IHandlerTDoc> requestRepairAndWarranty) {
		this.requestRepairAndWarranty = requestRepairAndWarranty;
	}

	public List<IHandlerTDoc> getRequestRepairAndPaid() {
		return requestRepairAndPaid;
	}

	public void setRequestRepairAndPaid(List<IHandlerTDoc> requestRepairAndPaid) {
		this.requestRepairAndPaid = requestRepairAndPaid;
	}

	public List<IHandlerTDoc> getRequestNotRepair() {
		return requestNotRepair;
	}

	public void setRequestNotRepair(List<IHandlerTDoc> requestNotRepair) {
		this.requestNotRepair = requestNotRepair;
	}

	public HandlerTDocRecieveFromSC(Class<TDocReceiveFromSC> type) {
		super(type);
	}

	@Override
	public RequestMessage afterCreate() {
		return createRequest(new TDocReceiveFromSC());
	}

	private RequestMessage createRequest(TDocReceiveFromSC doc) {
		RequestMessage req = new RequestMessage(RequestMessage.VALUETYPE);
		req.listRequestItem.add(new RequestItem(REPAIRED, false));
		req.listRequestItem.add(new RequestItem(NOTREPAIRED, false));
		return req;
	}

	@Override
	public boolean isComplete(ATDoc content) {
		TDocReceiveFromSC doc = (TDocReceiveFromSC) content;
		return !doc.getResult().equals("");
	}

	@Override
	public RequestMessage tune(ATDoc content, RequestMessage requestMessage)
			throws Exception {
		TDocReceiveFromSC doc = (TDocReceiveFromSC) content;
		if (requestMessage == null) {
			if (!isComplete(content))
				return createRequest(doc);
			return null;
		}
		if (requestMessage.typeRequest.equals(RequestMessage.VALUETYPE)) {
			for (RequestItem requestItem : requestMessage.listRequestItem) {
				if (requestItem.title.equals(REPAIRED)) {
					Boolean isSet = (Boolean) requestItem.request;
					if (isSet) {
						doc.setResult(REPAIRED);
						getDao().update(doc);
					}
				}
				if (requestItem.title.equals(NOTREPAIRED)) {
					Boolean isSet = (Boolean) requestItem.request;
					if (isSet) {
						doc.setResult(NOTREPAIRED);
						getDao().update(doc);
					}
				}
			}
		}
		return super.tune(content, requestMessage);
	}

	@Override
	public List<IHandlerTDoc> getNextTDoc(LOPT lopt, ATDoc content) {
		TDocReceiveFromSC doc = (TDocReceiveFromSC) content;
		if (doc.getResult().equals(REPAIRED)) {
			if (lopt.getWarranty().getName().equals(WARRANTY)) {
				return getRequestRepairAndWarranty();
			} else {
				return getRequestRepairAndPaid();
			}
		} else {
			return getRequestNotRepair();
		}
	}

}
