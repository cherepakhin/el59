package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.TovarBonusDTO;

public class MessageTovarBonusDTO extends MessageEntity<TovarBonusDTO> {
    private static final long serialVersionUID = 8886112218667465291L;
    TovarBonusDTO entity;

    public TovarBonusDTO getEntity() {
        return this.entity;
    }

    public void setEntity(TovarBonusDTO entity) {
        this.entity = entity;
    }
}
