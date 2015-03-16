package com.kingdee.eas.fdc.sellhouse;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 供 客户端 和 服务器端 公共调用的帮助类
 * */
public class SHEComHelper {
	private static final Logger log = Logger.getLogger(SHEComHelper.class);
	/**
	 * 将数值进行取整操作后返回
	 * @param srcAmount 原始金额
	 * @param isToInteger 是否取整
	 * @param toIntegerType 取整方式
	 * @param digit 取整位数
	 * @return 取整后的金额
	 * */
	public static BigDecimal getAmountAfterToInteger(BigDecimal srcAmount, boolean isToInteger, ToIntegerTypeEnum toIntegerType, DigitEnum digit){
		if(isToInteger){
			BigDecimal maxValue = null;
			int roundingMode = 0;//取舍方式
			
			if(ToIntegerTypeEnum.AbnegateZero.equals(toIntegerType)){
				roundingMode = BigDecimal.ROUND_DOWN;
			}else if(ToIntegerTypeEnum.Carry.equals(toIntegerType)){
				roundingMode = BigDecimal.ROUND_UP;
			}else if(ToIntegerTypeEnum.Round.equals(toIntegerType)){
				roundingMode = BigDecimal.ROUND_HALF_UP;
			}else{
				log.error("oh my god.error impossible.");
				return srcAmount;
			}
			
			if(DigitEnum.EntryDigit.equals(digit)){
				maxValue = new BigDecimal("1");
			}else if(DigitEnum.TenDigit.equals(digit)){
				maxValue = new BigDecimal("10");
			}else if(DigitEnum.HundredDigit.equals(digit)){
				maxValue = new BigDecimal("100");
			}else if(DigitEnum.ThousandDigit.equals(digit)){
				maxValue = new BigDecimal("1000");
			}else if(DigitEnum.TenThousandDigit.equals(digit)){
				maxValue = new BigDecimal("10000");
			}else if(DigitEnum.LakhDigit.equals(digit)){
				maxValue = new BigDecimal("100000");
			}else if(DigitEnum.TenthDigit.equals(digit)){
				maxValue = new BigDecimal("0.1");
			}else{
				log.error("oh my god.error impossible.");
				return srcAmount;
			}
			
			//舍零方式下：合同总金额比取整的位数还少，则不进行取整操作.否则合同进行取整后就变成0了
			//进位方式下：合同总金额比取整的位数大太多，则不进行取整操作.
			if(srcAmount.compareTo(maxValue) < 0){
				log.info("too small amount.");
				return srcAmount;
			}
			
			srcAmount = srcAmount.divide(maxValue, 0, roundingMode).multiply(maxValue);
		}
		return srcAmount;
	}

	
	
	public static ICoreBase getRevListBizInterface(Context ctx, RevListTypeEnum revListType) throws BOSException {
		if(revListType==null) return null;
		if(revListType.equals(RevListTypeEnum.sincerityPur)) {
			return ctx==null?SincerReceiveEntryFactory.getRemoteInstance():SincerReceiveEntryFactory.getLocalInstance(ctx);
		}else if(revListType.equals(RevListTypeEnum.purchaseRev)) {
			return ctx==null?PurchasePayListEntryFactory.getRemoteInstance():PurchasePayListEntryFactory.getLocalInstance(ctx);
		}else if(revListType.equals(RevListTypeEnum.purElseRev)) {
			return ctx==null?PurchaseElsePayListEntryFactory.getRemoteInstance():PurchaseElsePayListEntryFactory.getLocalInstance(ctx);
		}else if(revListType.equals(RevListTypeEnum.fdcCustomerRev)) {
			return ctx==null?CusRevListFactory.getRemoteInstance():CusRevListFactory.getLocalInstance(ctx);
		}else if(revListType.equals(RevListTypeEnum.areaCompensate)) {
			return ctx==null?AreaCompensateRevListFactory.getRemoteInstance():AreaCompensateRevListFactory.getLocalInstance(ctx);
		}
		
		return null;
	}
	
	
}
