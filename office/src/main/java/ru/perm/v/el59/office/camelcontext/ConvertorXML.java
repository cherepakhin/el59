package ru.perm.v.el59.office.camelcontext;

import java.io.StringWriter;

import org.dozer.Mapper;

import ru.perm.v.el59.office.dto.message.MessageEntity;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public class ConvertorXML<T, DTO> implements IConvertorXML<T> {
	private static XStream xstream;
	private Mapper mapper;

	protected static XStream getXStream() {
		if (xstream == null) {
			xstream = new XStream(new DomDriver());
			xstream.setMode(XStream.NO_REFERENCES);
			xstream.alias("message", MessageEntity.class);
			xstream.aliasField("command", MessageEntity.class, "typeCommand");
			xstream.aliasSystemAttribute("type", "class");
		}
		return xstream;
	}

	protected String generateResponse(final XStream xstream) {
		StringWriter writer = new StringWriter();
		xstream.marshal(this, new PrettyPrintWriter(writer) {
			@Override
			public void addAttribute(final String key, final String value) {
				if (!key.equals("class")) {
					super.addAttribute(key, value);
				}
			}
		});
		return writer.toString();
	}

	@Override
	public String getXML(MessageEntity<T> m) throws Exception {
		XStream xstream = getXStream();
		xstream.alias("entity", m.getEntity().getClass());
		xstream.alias(m.getEntity().getClass().getSimpleName(), m.getEntity()
				.getClass());
		String ret = xstream.toXML(m);
		return ret;
	}

	public MessageEntity<DTO> createMessageDTO(MessageEntity<T> fromMessage,
			DTO dto) {
		MessageEntity<DTO> mdto = new MessageEntity<DTO>();
		mdto.setClassName(fromMessage.getEntity().getClass().getSimpleName());
		mdto.setTypeCommand(fromMessage.getTypeCommand());
		mdto.setShopCod(fromMessage.getShopCod());
		mdto.setEntity(dto);
		return mdto;
	}

	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

}
