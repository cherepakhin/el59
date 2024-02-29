package ru.perm.v.el59.office.db;

import ru.perm.v.el59.ui.UI;

import java.io.Serializable;

public class Contragent extends ru.perm.v.el59.ui.AUIBean implements Serializable, Comparable<Contragent> {
    private static final long serialVersionUID = -4021971704404347197L;
    @UI(readonly = true, title = "Адрес", visible = true, width = 40)
    private String address = "";
    @UI(readonly = true, title = "Дополнительно", visible = true, width = 40)
    private String info = "";
    @UI(readonly = false, title = "Группа контрагента", visible = true, width = 10)
    private GroupContragent groupContragent;
    @UI(readonly = false, title = "ID в магазине", visible = true, width = 10)
    private Long nn;
    @UI(readonly = false, title = "Магазин", visible = true, width = 10)
    private Shop shop;
    @UI(readonly = false, title = "Краткое имя", visible = true, width = 20)
    private String shortname = "";
    @UI(readonly = false, title = "ИНН", visible = true, width = 10)
    private String inn = "";
    @UI(readonly = false, title = "КПП", visible = true, width = 6)
    private String kpp = "";
    @UI(readonly = false, title = "Банк", visible = true, width = 30)
    private String bank = "";
    @UI(readonly = false, title = "ОКПО", visible = true, width = 5)
    private String okpo = "";
    @UI(readonly = false, title = "ОКДП", visible = true, width = 5)
    private String okdp = "";
    @UI(readonly = false, title = "ОГРН", visible = true, width = 10)
    private String ogrn = "";
    @UI(readonly = false, title = "Телефон", visible = true, width = 15)
    private String phone = "";
    @UI(readonly = false, title = "E-mail", visible = true, width = 15)
    private String email = "";

    public static String getDescriptionClass() {
        return "Контрагент";
    }

    public Contragent() {
        this.address = "";
        this.info = "";
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getShortname() {
        return this.shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getInn() {
        return this.inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return this.kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getBank() {
        return this.bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getOkpo() {
        return this.okpo;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }

    public String getOkdp() {
        return this.okdp;
    }

    public void setOkdp(String okdp) {
        this.okdp = okdp;
    }

    public String getOgrn() {
        return this.ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public Shop getShop() {
        return this.shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public GroupContragent getGroupContragent() {
        return this.groupContragent;
    }

    public void setGroupContragent(GroupContragent groupContragent) {
        this.groupContragent = groupContragent;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int compareTo(Contragent o) {
        return this.compareTo(o);
    }
}
