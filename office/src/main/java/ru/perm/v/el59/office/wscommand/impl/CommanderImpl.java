package ru.perm.v.el59.office.wscommand.impl;

import javax.jms.Destination;

import org.apache.commons.lang.StringUtils;
import org.springframework.jms.core.JmsTemplate;

import ru.perm.v.el59.office.dto.message.MessageEntity;
import ru.perm.v.el59.office.dto.message.TypeCommand;
import ru.perm.v.el59.office.wscommand.ICommander;

public class CommanderImpl implements ICommander {
	private Destination destination;
	private JmsTemplate jmsTemplate;

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	private void sendMessage(Object o, TypeCommand command, String shopcod) {
		MessageEntity m = new MessageEntity();
		m.setTypeCommand(command);
		m.setClassName(o.getClass().getSimpleName());
		m.setEntity(o);
		if (StringUtils.isEmpty(shopcod)) {
			shopcod = "*";
		}
		m.setShopCod(shopcod);
		getJmsTemplate().convertAndSend(destination, m);
	}

	@Override
	public void create(Object o, String shopcod) {
		sendMessage(o, TypeCommand.CREATE, shopcod);
	}

	@Override
	public void delete(Object o, String shopcod) {
		sendMessage(o, TypeCommand.DELETE, shopcod);
	}

	@Override
	public void update(Object o, String shopcod) {
		sendMessage(o, TypeCommand.UPDATE, shopcod);
	}

}
