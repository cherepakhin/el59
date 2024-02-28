package ru.perm.v.el59.office.dao.impl.service;

import java.util.List;

import ru.perm.v.el59.office.db.service.ATDoc;
import ru.perm.v.el59.office.db.service.LOPT;
import ru.perm.v.el59.office.db.service.TDocCallMaster;
import ru.perm.v.el59.office.iproviders.RequestItem;
import ru.perm.v.el59.office.iproviders.RequestMessage;

public class HandlerTDocCallMaster extends HandlerTDoc<TDocCallMaster> {

	private final static String MASTER = "Мастер";
	private final static String REPAIRED = "Отремонтировал";
	private final static String NOTREPAIRED = "Не отремонтировал";
	private final static String WARRANTY = "Гарантия производителя";
	private List<IHandlerTDoc> requestRepairAndPaid;
	private List<IHandlerTDoc> requestRepairAndWarranty;
	private List<IHandlerTDoc> requestNotRepairAndPaid;
	private List<IHandlerTDoc> requestNotRepairAndWarranty;

	public List<IHandlerTDoc> getRequestRepairAndPaid() {
		return requestRepairAndPaid;
	}

	public void setRequestRepairAndPaid(List<IHandlerTDoc> requestRepairAndPaid) {
		this.requestRepairAndPaid = requestRepairAndPaid;
	}

	public List<IHandlerTDoc> getRequestRepairAndWarranty() {
		return requestRepairAndWarranty;
	}

	public void setRequestRepairAndWarranty(
			List<IHandlerTDoc> requestRepairAndWarranty) {
		this.requestRepairAndWarranty = requestRepairAndWarranty;
	}

	public List<IHandlerTDoc> getRequestNotRepairAndPaid() {
		return requestNotRepairAndPaid;
	}

	public void setRequestNotRepairAndPaid(
			List<IHandlerTDoc> requestNotRepairAndPaid) {
		this.requestNotRepairAndPaid = requestNotRepairAndPaid;
	}

	public List<IHandlerTDoc> getRequestNotRepairAndWarranty() {
		return requestNotRepairAndWarranty;
	}

	public void setRequestNotRepairAndWarranty(
			List<IHandlerTDoc> requestNotRepairAndWarranty) {
		this.requestNotRepairAndWarranty = requestNotRepairAndWarranty;
	}

	public HandlerTDocCallMaster(Class<TDocCallMaster> type) {
		super(type);
	}

	@Override
	public RequestMessage afterCreate() {
		return createRequestMaster();
	}

	private boolean isCompeteNamemaster(TDocCallMaster callmaster) {
		return (callmaster.getMaster().length() > 1);
	}

	private boolean isCompeteRepair(TDocCallMaster callmaster) {
		return (callmaster.getResult().length() > 1);
	}

	@Override
	public boolean isComplete(ATDoc content) {
		TDocCallMaster callmaster = (TDocCallMaster) content;
		return (isCompeteNamemaster(callmaster) && isCompeteRepair(callmaster));
	}

	private RequestMessage createRequestMaster() {
		RequestMessage req = new RequestMessage(RequestMessage.VALUETYPE);
		req.listRequestItem.add(new RequestItem(MASTER, ""));
		return req;
	}

	private RequestMessage createRequestRepair() {
		RequestMessage req = new RequestMessage(RequestMessage.VALUETYPE);
		req.listRequestItem.add(new RequestItem(REPAIRED, false));
		req.listRequestItem.add(new RequestItem(NOTREPAIRED, false));
		return req;
	}

	@Override
	public RequestMessage tune(ATDoc content, RequestMessage requestMessage)
			throws Exception {
		TDocCallMaster callmaster = (TDocCallMaster) content;
		if (requestMessage == null) {
			if (!isCompeteNamemaster(callmaster))
				return createRequestMaster();
			if (!isCompeteRepair(callmaster))
				return createRequestRepair();
			return null;
		}
		if (requestMessage.typeRequest.equals(RequestMessage.VALUETYPE)) {
			for (RequestItem requestItem : requestMessage.listRequestItem) {
				if (requestItem.title.equals(MASTER)) {
					String nameMaster = (String) requestItem.request;
					if (nameMaster.length() > 2) {
						callmaster.setMaster(nameMaster);
						getDao().update(callmaster);
						return createRequestRepair();
					}
				}
				if (requestItem.title.equals(REPAIRED)
						|| requestItem.title.equals(NOTREPAIRED)) {
					Boolean issetrepair = (Boolean) requestItem.request;
					if (issetrepair) {
						callmaster.setResult(requestItem.title);
						getDao().update(callmaster);
						return null;
					}
				}
			}
		}
		return super.tune(content, requestMessage);
	}

	@Override
	public List<IHandlerTDoc> getNextTDoc(LOPT lopt, ATDoc content) {
		TDocCallMaster callmaster = (TDocCallMaster) content;
		if (callmaster.getResult().equals(REPAIRED)) {
			if (lopt.getWarranty().getName().equals(WARRANTY)) {
				return getRequestRepairAndPaid();
			} else {
				return getRequestRepairAndWarranty();
			}
		} else {
			if (lopt.getWarranty().getName().equals(WARRANTY)) {
				return getRequestNotRepairAndPaid();
			} else {
				return getRequestNotRepairAndWarranty();
			}
		}
	}
}
