package ru.perm.v.el59.office.camelcontext.receiver;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.camel.Body;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.dto.message.MessageEntity;
import ru.perm.v.el59.office.dto.message.TypeCommand;
import ru.perm.v.el59.office.wscommand.ICommander;

public abstract class ConvertorFromXML<DTO, VO> {
	protected Class<DTO> type;
	protected IGenericDao dao;
	protected MessageEntity<DTO> message;
	protected String entity;
	protected ICommander commander;
	private XStream xstream;

	public abstract String fromXmlToMessageEntity(@Body Object body)
			throws Exception;

	public IGenericDao getDao() {
		return dao;
	}

	public void setDao(IGenericDao dao) {
		this.dao = dao;
	}

	public MessageEntity<DTO> getMessage() {
		return message;
	}

	public MessageEntity<DTO> getMessageFromXml(String xml) {
		message = (MessageEntity<DTO>) xstream.fromXML(xml);
		return message;
	}

	public void setMessage(MessageEntity<DTO> message) {
		this.message = message;
	}

	protected void doMessage(VO p) throws Exception {
		if (message.getTypeCommand().equals(TypeCommand.CREATE)) {
			create(p);
		}
		if (message.getTypeCommand().equals(TypeCommand.UPDATE)) {
			update(p);
		}
		if (message.getTypeCommand().equals(TypeCommand.DELETE)) {
			delete(p);
		}
		if (message.getTypeCommand().equals(TypeCommand.GET)) {
			get(p);
		}
	}

	protected void get(Object o) {
	}

	protected void delete(VO o) {
		try {
			getDao().delete(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void update(VO o) throws Exception {
		getDao().update(o);
	}

	protected void create(VO o) throws Exception {
		try {
			getDao().create(o);
		} catch (Exception e) {
			getMessage().setTypeCommand(TypeCommand.UPDATE);
		}
	}

	public Class<DTO> getType() {
		return type;
	}

	public void setType(Class<DTO> type) {
		this.type = type;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public ICommander getCommander() {
		return commander;
	}

	public void setCommander(ICommander commander) {
		this.commander = commander;
	}

	public XStream getXStream() {
		xstream = new XStream(new DomDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.registerConverter(new DateConverter("yyyy-MM-dd",
				new String[] { "yyyy-MM-dd" }));
		return xstream;
	}
}
