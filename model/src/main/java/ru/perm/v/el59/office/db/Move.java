package ru.perm.v.el59.office.db;

import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Move extends AUIBean implements Serializable, ITovar {
    private static final long serialVersionUID = -3140596465749361673L;
    @UI(
            readonly = true,
            title = "id",
            visible = true,
            width = 0
    )
    private Long n;
    @UI(
            readonly = false,
            title = "Дата",
            visible = true,
            width = 10
    )
    private Date ddate;
    @UI(
            readonly = false,
            title = "Товар",
            visible = true,
            complex = true,
            width = 10
    )
    private Tovar tovar;
    @UI(
            readonly = false,
            title = "№ док-та",
            visible = true,
            width = 6
    )
    private String numdoc;
    @UI(
            readonly = false,
            title = "К-во",
            visible = true,
            width = 10,
            justify = Justify.RIGHT
    )
    private BigDecimal qty;
    @UI(
            readonly = false,
            title = "Вх.сумма",
            visible = true,
            width = 10,
            justify = Justify.RIGHT
    )
    private BigDecimal summain;
    @UI(
            readonly = false,
            title = "Вых.сумма",
            visible = true,
            width = 10,
            justify = Justify.RIGHT
    )
    private BigDecimal summaout;
    @UI(
            readonly = false,
            title = "Прайс",
            visible = true,
            width = 10,
            justify = Justify.RIGHT
    )
    private BigDecimal price;
    private String agent;
    @UI(
            readonly = false,
            title = "Продавец",
            visible = true,
            width = 15
    )
    private String seller;
    private Agent agentcode;
    private String subtype;
    @UI(
            readonly = false,
            title = "Операция",
            visible = true
    )
    private Operation operation;
    private String vid;
    private String codeoper;
    private String typeoper;
    private BigDecimal pribul;
    private Move parent;
    @UI(
            readonly = false,
            title = "Магазин",
            visible = true,
            complex = true,
            width = 10
    )
    private Shop shop;
    private String tostock;
    @UI(
            readonly = false,
            title = "Со склада",
            visible = true
    )
    private TypeStock fromstock;

    public static String getDescriptionClass() {
        return "Движения";
    }

    public Move getParent() {
        Move parent1 = this.parent;
        return parent1;
    }

    public void setParent(Move parent) {
        this.parent = parent;
    }

    public BigDecimal getPribul() {
        return this.summaout.subtract(this.summain);
    }

    public Agent getAgentcode() {
        return this.agentcode;
    }

    public void setAgentcode(Agent agentcode) {
        this.agentcode = agentcode;
    }

    public String getTypeoper() {
        return this.typeoper;
    }

    public void setTypeoper(String typeoper) {
        this.typeoper = typeoper;
    }

    public String getVid() {
        return this.vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getCodeoper() {
        return this.codeoper;
    }

    public void setCodeoper(String codeoper) {
        this.codeoper = codeoper;
    }

    public Move(Move m) {
        this();
        this.n = m.n;
        this.agent = m.agent;
        this.agentcode = m.agentcode;
        this.codeoper = m.codeoper;
        this.ddate = m.ddate;
        this.fromstock = m.fromstock;
        this.numdoc = m.numdoc;
        this.operation = m.operation;
        this.parent = m.parent;
        this.pribul = m.pribul;
        this.qty = m.qty;
        this.seller = m.seller;
        this.subtype = m.subtype;
        this.shop = m.shop;
        this.summain = m.summain;
        this.summaout = m.summaout;
        this.tostock = m.tostock;
        this.tovar = m.tovar;
        this.typeoper = m.typeoper;
        this.vid = m.vid;
    }

    public void convertToRub(BigDecimal rubOnDate) {
        this.summain = this.summain.multiply(rubOnDate);
        this.summaout = this.summaout.multiply(rubOnDate);
    }

    public Long getN() {
        return this.n;
    }

    public void setN(Long n) {
        this.n = n;
    }

    public Tovar getTovar() {
        return this.tovar;
    }

    public void setTovar(Tovar tovar) {
        this.tovar = tovar;
    }

    public Shop getShop() {
        return this.shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public BigDecimal getQty() {
        return this.qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getSummain() {
        return this.summain;
    }

    public void setSummain(BigDecimal summain) {
        this.summain = summain;
    }

    public BigDecimal getSummaout() {
        return this.summaout;
    }

    public void setSummaout(BigDecimal summaout) {
        this.summaout = summaout;
    }

    public String getAgent() {
        return this.agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getSeller() {
        return this.seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getNumdoc() {
        return this.numdoc;
    }

    public void setNumdoc(String numdoc) {
        this.numdoc = numdoc;
    }

    public String getSubtype() {
        return this.subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Date getDdate() {
        return this.ddate;
    }

    public void setDdate(Date ddate) {
        this.ddate = ddate;
    }

    public String getTostock() {
        return this.tostock;
    }

    public void setTostock(String tostock) {
        this.tostock = tostock;
    }

    public TypeStock getFromstock() {
        return this.fromstock;
    }

    public void setFromstock(TypeStock fromstock) {
        this.fromstock = fromstock;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public boolean isPrihod() {
        return this.vid.equals("1");
    }

    public int hashCode() {
        int result = 31 + (this.n == null ? 0 : this.n.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            Move other = (Move) obj;
            if (this.n == null) {
                if (other.n != null) {
                    return false;
                }
            } else if (!this.n.equals(other.n)) {
                return false;
            }

            return true;
        }
    }

    public void set0() {
        this.qty = new BigDecimal("0.00");
        this.summain = new BigDecimal("0.00");
        this.summaout = new BigDecimal("0.00");
    }

    public String toString() {
        String ret = "";
        if (this.numdoc != null) {
            ret = ret + " " + this.numdoc;
        }

        if (this.ddate != null) {
            ret = ret + " " + Vars.format(this.ddate);
        }

        if (this.operation != null) {
            ret = ret + " " + this.operation.getName();
        }

        if (this.tovar != null) {
            ret = ret + " " + this.tovar.toString();
        }

        if (this.summain != null) {
            ret = ret + " Вх.сумма=" + Vars.format(this.summain);
        }

        if (this.summaout != null) {
            ret = ret + " Вых.сумма=" + Vars.format(this.summaout);
        }

        return ret;
    }

    public Move() {
        this.ddate = new Date();
        this.numdoc = "";
        this.qty = new BigDecimal("0.00");
        this.summain = new BigDecimal("0.00");
        this.summaout = new BigDecimal("0.00");
        this.price = new BigDecimal("0.00");
        this.agent = "";
        this.seller = "";
        this.subtype = "";
        this.vid = "";
        this.codeoper = "";
        this.typeoper = "";
        this.pribul = new BigDecimal("0.00");
        this.numdoc = "";
        this.ddate = new Date();
        this.shop = new Shop();
        this.tovar = new Tovar();
        this.operation = new Operation();
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
