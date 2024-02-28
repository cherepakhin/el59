package ru.perm.v.el59.office.iproviders.dao;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.UI;

import java.io.Serializable;

public class CommonCritery extends AUIBean implements Serializable {
    private static final long serialVersionUID = 3734119808222028537L;
    @UI(
            title = "Имя",
            visible = false
    )
    private String name = "";

    public CommonCritery() {
    }

    public CommonCritery(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}