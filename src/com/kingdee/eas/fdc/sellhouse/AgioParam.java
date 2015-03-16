package com.kingdee.eas.fdc.sellhouse;

import java.util.Collection;

import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.framework.DataBaseCollection;


/**
 *折扣参数 
 * @author jeegan_wang
 */
public class AgioParam {
	private AgioEntryCollection agios;			//认购的 折扣信息 或 认购变更的 折扣信息 
														//PurchaseAgioEntryCollection  PurchaseChangeAgioEntryCollection
	private boolean isToInteger;		//是否自动取整
	private boolean isBasePriceSell;	//是否低价销售
	private PriceAccountTypeEnum priceAccountType;	//价格计算方式：总价反算单价；单价反算总价 
	private ToIntegerTypeEnum toIntegerType;	//取整方式：四舍五入；舍零；进位
	private DigitEnum digit;					//位数：各位；十位；百位；千位；万位；十万位
	private SpecialDiscountInfo specialAgio;
	public AgioParam() {
		agios = new AgioEntryCollection();
		isToInteger = false;
		
		//默认取参数设置中的
		isBasePriceSell = false;
		priceAccountType = PriceAccountTypeEnum.StandSetPrice ;  
		toIntegerType = ToIntegerTypeEnum.Round;;
		digit = DigitEnum.EntryDigit;
		specialAgio=null;
	}
	
	/**
	 * 认购的 折扣信息  认购变更的 折扣信息
	 * @return
	 */
	public AgioEntryCollection getAgios() {
		return agios;
	}
	
	/**
	 * 认购的 折扣信息  认购变更的 折扣信息
	 * @param agios
	 */
	public void setAgios(AgioEntryCollection agios) {
		this.agios = agios;
	}
	
	
	/**
	 * 位数：各位；十位；百位；千位；万位；十万位
	 * @return
	 */
	public DigitEnum getDigit() {
		return digit;
	}
	/**
	 * 位数：各位；十位；百位；千位；万位；十万位
	 * @param digit
	 */
	public void setDigit(DigitEnum digit) {
		this.digit = digit;
	}
	
	/**
	 * 取整方式：四舍五入；舍零；进位
	 * @return
	 */
	public boolean isToInteger() {
		return isToInteger;
	}
	/**
	 * 取整方式：四舍五入；舍零；进位
	 * @param isToInteger
	 */
	public void setToInteger(boolean isToInteger) {
		this.isToInteger = isToInteger;
	}
	
	/**
	 * 是否低价销售
	 * @return
	 */
	public boolean isBasePriceSell(){
		return isBasePriceSell;
	}
	/**
	 * 是否低价销售
	 * @param isBasePriceSell
	 */
	public void setBasePriceSell(boolean isBasePriceSell){
		this.isBasePriceSell = isBasePriceSell;
	}
	
	
	/**
	 * 价格计算方式：总价反算单价；单价反算总价 
	 * @return
	 */
	public PriceAccountTypeEnum getPriceAccountType() {
		return priceAccountType;
	}
	/**
	 * 价格计算方式：总价反算单价；单价反算总价 
	 * @param priceAccountType
	 */
	public void setPriceAccountType(PriceAccountTypeEnum priceAccountType) {
		this.priceAccountType = priceAccountType;
	}
	
	/**
	 * 取整方式：四舍五入；舍零；进位
	 * @return
	 */
	public ToIntegerTypeEnum getToIntegerType() {
		return toIntegerType;
	}
	/**
	 * 取整方式：四舍五入；舍零；进位
	 * @param toIntegerType
	 */
	public void setToIntegerType(ToIntegerTypeEnum toIntegerType) {
		this.toIntegerType = toIntegerType;
	}
	
	
	
	public SpecialDiscountInfo getSpecialAgio() {
		return specialAgio;
	}
	public void setSpecialAgio(SpecialDiscountInfo specialAgio) {
		this.specialAgio = specialAgio;
	}
	
}