package com.kingdee.eas.fdc.sellhouse;

import java.math.BigDecimal;
import java.util.List;

import com.kingdee.eas.fdc.basedata.FDCHelper;

public class RateExpression {
  
	public RateExpression()
	{	
	}
	
	public static  BigDecimal amountPrincipal = FDCHelper.ZERO;
	public static  BigDecimal accrual = FDCHelper.ZERO;
	
	/**
	 * 等额本金按揭计算公式
	 *
	 * @param totalMoney  按揭总额
	 * @param requMonth   还款次数
	 * @param loanInterestRate 年利率
	 * @param countRequMoney  已还次数
	 * @return BigDecimal
	 */
	public static BigDecimal getAmountPrincipal(BigDecimal totalMoney, int requMonth,
			BigDecimal loanInterestRate, int countRequMoney)
	{
		// 已经归还本金额
		BigDecimal alreadyAmountPrincipal = totalMoney.divide(
				new BigDecimal(requMonth),BigDecimal.ROUND_HALF_UP).multiply(
						new BigDecimal(countRequMoney));
		amountPrincipal = totalMoney.divide(new BigDecimal(requMonth),10,
				BigDecimal.ROUND_HALF_UP).add(
						totalMoney.subtract(alreadyAmountPrincipal).multiply(
								loanInterestRate.divide(FDCHelper.ONE_HUNDRED,10,
										BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_UP)));
		return amountPrincipal;
	}
	
	/**
	 * 等额本息按揭计算公式
	 *
	 * @param totalMoney  按揭总额
	 * @param requMonth   还款次数
	 * @param loanInterestRate 年利率
	 * @return BigDecimal
	 */
	public static BigDecimal getAmountAccrual(BigDecimal totalMoney,int requMonth,BigDecimal loanInterestRate) {
		double rate = loanInterestRate.divide(FDCHelper.ONE_HUNDRED,10,BigDecimal.ROUND_HALF_UP)
		.divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1)).doubleValue();
		accrual = totalMoney.multiply(loanInterestRate.divide(FDCHelper.ONE_HUNDRED,10,BigDecimal.ROUND_HALF_UP)
				.divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(Math.pow(rate,requMonth))))
				.divide(new BigDecimal(Math.pow(rate,requMonth)-1),10,BigDecimal.ROUND_HALF_UP);
		return accrual ;
	}
	
}
