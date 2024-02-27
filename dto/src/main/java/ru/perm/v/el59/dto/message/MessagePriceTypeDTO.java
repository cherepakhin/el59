package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.PriceTypeDTO;

public class MessagePriceTypeDTO extends MessageEntity<PriceTypeDTO> {
    private static final long serialVersionUID = 4749006572505982042L;
    PriceTypeDTO entity;

    public PriceTypeDTO getEntity() {
        return this.entity;
    }

    public void setEntity(PriceTypeDTO entity) {
        this.entity = entity;
    }
}
