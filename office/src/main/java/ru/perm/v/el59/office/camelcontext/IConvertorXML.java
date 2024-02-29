package ru.perm.v.el59.office.camelcontext;


import ru.perm.v.el59.dto.message.MessageEntity;

public interface IConvertorXML<T> {
	public String getXML(MessageEntity<T> m) throws Exception;
}
