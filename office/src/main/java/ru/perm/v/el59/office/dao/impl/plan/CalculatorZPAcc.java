package ru.perm.v.el59.office.dao.impl.plan;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ru.perm.v.el59.office.db.Move;
import ru.perm.v.el59.office.db.plan.UserZP;

/**
 * <b>Калькулятор для аксессуаров</b>
 *<pre>
Мотивация при продаже АКСЕССУАРОВ
1.	План продаж по аксессуарам - 11% от личного т/о;
2.	При выполнении плана продаж аксессуаров до 70% - отчисление в ПРЭФ - 2% от объема проданных аксессуаров.
3.	При выполнении плана продаж по аксессуарам от 70% до 100% - 5%
4.	При выполнении плана продаж по аксессуарам от 100% и свыше - 11%
5.	Для сумм отчислений от проданных аксессуаров с маржей ниже 35% применить поправочный коэффициент 0,5
</pre>
 *@author vasi
 *
 */
public class CalculatorZPAcc extends ACalculatorZPFofBonusK implements ICalculatorForBonusK{

//	private BigDecimal percentOfSale = new BigDecimal("0.08");
	
	private BigDecimal percentSaleFofBigK = new BigDecimal("1.00");
	private BigDecimal bigK = new BigDecimal("0.11"); 
	
	private BigDecimal percentSaleFofMiddleK = new BigDecimal("0.70");
	private BigDecimal middleK = new BigDecimal("0.05"); 

	private BigDecimal smallK = new BigDecimal("0.02");
	
	private BigDecimal minMarga = new BigDecimal("0.35");
	private BigDecimal decreaseK = new BigDecimal("0.5");

	@Override
	public UserZP calcZP(UserZP userZP, Move move) throws Exception {
		BigDecimal saleAll = userZP.getSummaOut();
		BigDecimal saleAcc = userZP.getSummaAcc();
		
		// Вычисление процента от плана
		BigDecimal planSale = saleAll.multiply(userZP.getPlan().getPlanPercentAcc()).setScale(2, RoundingMode.HALF_UP);
		BigDecimal resultK = smallK;
		if(planSale.compareTo(BigDecimal.ZERO)!=0) {
			BigDecimal kExecution = saleAcc.divide(planSale,RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
			if(kExecution.compareTo(percentSaleFofBigK)>=0) {
				resultK=bigK;
			} else {
				if(kExecution.compareTo(percentSaleFofMiddleK)>=0) {
					resultK=middleK;
				} 
			}
		}
		
		// Корректировка процента от маржи
		BigDecimal summain = move.getSummain();
		if(move.getCenaInOnDate().compareTo(BigDecimal.ZERO)!=0) {
			summain=move.getCenaInOnDate().multiply(move.getQty()).setScale(2,RoundingMode.HALF_UP);
		}
		
		// Внимание!!! Считается МАРЖА.
		BigDecimal marga = new BigDecimal("1.00");
		if(summain.compareTo(BigDecimal.ZERO)!=0) {
			if(move.getSummaout().compareTo(BigDecimal.ZERO)==0) {
				marga=BigDecimal.ZERO;
			} else {
				marga=move.getSummaout().subtract(summain).divide(move.getSummaout(),RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
			}
		}
		
		if(marga.compareTo(minMarga)<0) {
			resultK=resultK.multiply(decreaseK);
		}

		BigDecimal zp=move.getSummaout().multiply(resultK).setScale(2, RoundingMode.HALF_UP);
		if(move.getOperation().getZnak()<0) {
			userZP.setSummaAccBonus(userZP.getSummaAccBonus().add(zp));
			move.setZpAcc(zp);
		} else {
			userZP.setSummaAccBonus(userZP.getSummaAccBonus().subtract(zp));
			move.setZpAcc(zp.negate());
		}
		return userZP;
	}

	@Override
	protected UserZP changeSumInOut(UserZP u, Move m, BigDecimal summaIn) {
		if(m.getOperation().getZnak()<0) {
			u.setSummaAcc(u.getSummaAcc().add(m.getSummaout()).setScale(2, RoundingMode.HALF_UP));
		} else {
			u.setSummaAcc(u.getSummaAcc().subtract(m.getSummaout()).setScale(2, RoundingMode.HALF_UP));
		}
		return u;
	}

	public BigDecimal getPercentSaleFofBigK() {
		return percentSaleFofBigK;
	}

	public void setPercentSaleFofBigK(BigDecimal percentSaleFofBigK) {
		this.percentSaleFofBigK = percentSaleFofBigK;
	}

	public BigDecimal getBigK() {
		return bigK;
	}

	public void setBigK(BigDecimal bigK) {
		this.bigK = bigK;
	}

	public BigDecimal getPercentSaleFofMiddleK() {
		return percentSaleFofMiddleK;
	}

	public void setPercentSaleFofMiddleK(BigDecimal percentSaleFofMiddleK) {
		this.percentSaleFofMiddleK = percentSaleFofMiddleK;
	}

	public BigDecimal getMiddleK() {
		return middleK;
	}

	public void setMiddleK(BigDecimal middleK) {
		this.middleK = middleK;
	}

	public BigDecimal getSmallK() {
		return smallK;
	}

	public void setSmallK(BigDecimal smallK) {
		this.smallK = smallK;
	}

	public BigDecimal getDecreaseK() {
		return decreaseK;
	}

	public void setDecreaseK(BigDecimal decreaseK) {
		this.decreaseK = decreaseK;
	}

	public BigDecimal getMinMarga() {
		return minMarga;
	}

	public void setMinMarga(BigDecimal minMarga) {
		this.minMarga = minMarga;
	}
}
