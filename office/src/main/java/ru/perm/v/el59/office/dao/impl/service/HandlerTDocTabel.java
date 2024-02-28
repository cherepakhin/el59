package ru.perm.v.el59.office.dao.impl.service;

import ru.perm.v.el59.office.db.plan.TDocTabel;
import ru.perm.v.el59.office.iproviders.RequestItem;
import ru.perm.v.el59.office.iproviders.RequestMessage;

public class HandlerTDocTabel extends HandlerTDoc<TDocTabel> {

	private final static String COMMENT = "Комментарий";

	public HandlerTDocTabel(Class<TDocTabel> type) {
		super(type);
	}

	@Override
	public RequestMessage afterCreate() {
		return createRequest();
	}

	private RequestMessage createRequest() {
		RequestMessage req = new RequestMessage(RequestMessage.VALUETYPE);
		req.listRequestItem.add(new RequestItem(COMMENT, ""));
		return req;
	}
}
