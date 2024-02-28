package ru.perm.v.el59.office.dao.impl.service;

import ru.perm.v.el59.office.db.service.ATDoc;
import ru.perm.v.el59.office.db.service.TDocToSC;
import ru.perm.v.el59.office.iproviders.RequestItem;
import ru.perm.v.el59.office.iproviders.RequestMessage;

public class HandlerTDocToSC extends HandlerTDoc<TDocToSC> {

	private final static String SC = "Сервисный центр";
	private final static String NACL = "Номер накладной о приеме";

	public HandlerTDocToSC(Class<TDocToSC> type) {
		super(type);
	}

	@Override
	public RequestMessage afterCreate() {
		return createRequest(new TDocToSC());
	}

	private RequestMessage createRequest(TDocToSC doc) {
		RequestMessage req = new RequestMessage(RequestMessage.VALUETYPE);
		req.listRequestItem.add(new RequestItem(SC, doc.getSc()));
		req.listRequestItem.add(new RequestItem(NACL, doc.getNacl()));
		return req;
	}

	@Override
	public boolean isComplete(ATDoc content) {
		TDocToSC doc = (TDocToSC) content;
		return (!doc.getSc().equals("") && !doc.getNacl().equals(""));
	}

	@Override
	public RequestMessage tune(ATDoc content, RequestMessage requestMessage)
			throws Exception {
		TDocToSC doc = (TDocToSC) content;
		if (requestMessage == null) {
			if (!isComplete(content))
				return createRequest(doc);
			return null;
		}
		if (requestMessage.typeRequest.equals(RequestMessage.VALUETYPE)) {
			for (RequestItem requestItem : requestMessage.listRequestItem) {
				if (requestItem.title.equals(SC)) {
					String sc = (String) requestItem.request;
					doc.setSc(sc);
					getDao().update(doc);
				}
				if (requestItem.title.equals(NACL)) {
					String nacl = (String) requestItem.request;
					doc.setNacl(nacl);
					getDao().update(doc);
				}
			}
		}
		return super.tune(content, requestMessage);
	}

}
