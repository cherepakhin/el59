package ru.perm.v.el59.office.dao.impl.service;

import ru.perm.v.el59.office.db.service.ATDoc;
import ru.perm.v.el59.office.db.service.TDocSendToSC;
import ru.perm.v.el59.office.iproviders.RequestItem;
import ru.perm.v.el59.office.iproviders.RequestMessage;

public class HandlerTDocSendToSC extends HandlerTDoc<TDocSendToSC> {

	private final static String SENDER = "Через кого?";

	public HandlerTDocSendToSC(Class<TDocSendToSC> type) {
		super(type);
	}

	@Override
	public RequestMessage afterCreate() {
		return createRequest(new TDocSendToSC());
	}

	private RequestMessage createRequest(TDocSendToSC doc) {
		RequestMessage req = new RequestMessage(RequestMessage.VALUETYPE);
		req.listRequestItem.add(new RequestItem(SENDER, doc.getSender()));
		return req;
	}

	@Override
	public boolean isComplete(ATDoc content) {
		TDocSendToSC doc = (TDocSendToSC) content;
		return doc.getSender().equals("");
	}

	@Override
	public RequestMessage tune(ATDoc content, RequestMessage requestMessage)
			throws Exception {
		TDocSendToSC doc = (TDocSendToSC) content;
		if (requestMessage == null) {
			if (!isComplete(content))
				return createRequest(doc);
			return null;
		}
		if (requestMessage.typeRequest.equals(RequestMessage.VALUETYPE)) {
			for (RequestItem requestItem : requestMessage.listRequestItem) {
				if (requestItem.title.equals(SENDER)) {
					String sender = (String) requestItem.request;
					doc.setSender(sender);
					getDao().update(doc);
				}
			}
		}
		return super.tune(content, requestMessage);
	}

}
