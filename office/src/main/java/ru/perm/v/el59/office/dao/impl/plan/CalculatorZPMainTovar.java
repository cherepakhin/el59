package ru.perm.v.el59.office.dao.impl.plan;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ru.perm.v.el59.office.db.Move;
import ru.perm.v.el59.office.db.plan.UserZP;

public class CalculatorZPMainTovar extends ACalculatorZPFofBonusK implements
		ICalculatorForBonusK {

	private BigDecimal percentMin = new BigDecimal("0.01");
	private BigDecimal percentStandart = new BigDecimal("0.02");
	private BigDecimal percentMax = new BigDecimal("0.03");

	private BigDecimal nacenkaMin = new BigDecimal("0.20");
	private BigDecimal nacenkaMax = new BigDecimal("0.25");

	@Override
	public UserZP calcZP(UserZP u, Move m) throws Exception {
		BigDecimal summaIn = getSummaIn(m);
		BigDecimal nacenka = new BigDecimal("1.00");
		if (summaIn.compareTo(BigDecimal.ZERO) != 0) {
			nacenka = m.getSummaout().divide(summaIn, RoundingMode.HALF_UP)
					.subtract(BigDecimal.ONE).setScale(3, RoundingMode.HALF_UP);
		}
		BigDecimal percent = null;
		if (nacenka.compareTo(nacenkaMin) < 0) {
			percent = percentMin;
		}
		if (percent == null && nacenka.compareTo(nacenkaMax) < 0) {
			percent = percentStandart;
		}
		if (percent == null && nacenka.compareTo(nacenkaMax) >= 0) {
			BigDecimal sumPyPrice = m.getPrice().multiply(m.getQty())
					.setScale(2, RoundingMode.HALF_UP);
			// Если продано не по прайсу, т.е. со скидкой, то стандартный
			// процент
			if (sumPyPrice.compareTo(BigDecimal.ZERO) != 0
					&& m.getSummaout().compareTo(sumPyPrice) < 0) {
				percent = percentStandart;
			} else {
				percent = percentMax;
			}
		}
		if (percent == null) {
			throw new Exception(
					String.format(
							"Не расчитан процент начисления ЗП для move.n=%d tovar.nnum=%d",
							m.getN(), m.getTovar().getNnum()));
		}
		if (percent != null) {
			BigDecimal zp = m.getSummaout().multiply(percent)
					.setScale(2, RoundingMode.HALF_UP);
			if (m.getOperation().getZnak() < 0) {
				u.setSummaMainTovarBonus(u.getSummaMainTovarBonus().add(zp));
				m.setZpKBT(zp);
			} else {
				u.setSummaMainTovarBonus(u.getSummaMainTovarBonus()
						.subtract(zp));
				m.setZpKBT(zp.negate());
			}
		}
		return u;
	}

	@Override
	protected UserZP changeSumInOut(UserZP u, Move m, BigDecimal summaIn) {
		try {
			if (m.getOperation().getZnak() < 0) {
				u.setSummaMainTovar(u.getSummaMainTovar().add(m.getSummaout()));
			} else {
				u.setSummaMainTovar(u.getSummaMainTovar().subtract(m.getSummaout()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

	public BigDecimal getPercentMin() {
		return percentMin;
	}

	public void setPercentMin(BigDecimal percentMin) {
		this.percentMin = percentMin;
	}

	public BigDecimal getPercentStandart() {
		return percentStandart;
	}

	public void setPercentStandart(BigDecimal percentStandart) {
		this.percentStandart = percentStandart;
	}

	public BigDecimal getPercentMax() {
		return percentMax;
	}

	public void setPercentMax(BigDecimal percentMax) {
		this.percentMax = percentMax;
	}

	public BigDecimal getNacenkaMin() {
		return nacenkaMin;
	}

	public void setNacenkaMin(BigDecimal nacenkaMin) {
		this.nacenkaMin = nacenkaMin;
	}

	public BigDecimal getNacenkaMax() {
		return nacenkaMax;
	}

	public void setNacenkaMax(BigDecimal nacenkaMax) {
		this.nacenkaMax = nacenkaMax;
	}

}
