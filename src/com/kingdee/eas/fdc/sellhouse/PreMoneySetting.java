package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import java.math.BigDecimal;

public class PreMoneySetting implements Serializable{
	
	private BigDecimal preLevelAmount;	 //预订最低金额
	private Boolean isLevelModify;		//最低可否修改
	private BigDecimal preStandAmount;	//预订标准金额
	private Boolean isStandModify;		//标准可否修改
	
	
	public BigDecimal getPreLevelAmount() {
		return preLevelAmount;
	}
	public void setPreLevelAmount(BigDecimal preLevelAmount) {
		this.preLevelAmount = preLevelAmount;
	}
	public Boolean getIsLevelModify() {
		return isLevelModify;
	}
	public void setIsLevelModify(Boolean isLevelModify) {
		this.isLevelModify = isLevelModify;
	}
	public BigDecimal getPreStandAmount() {
		return preStandAmount;
	}
	public void setPreStandAmount(BigDecimal preStandAmount) {
		this.preStandAmount = preStandAmount;
	}
	public Boolean getIsStandModify() {
		return isStandModify;
	}
	public void setIsStandModify(Boolean isStandModify) {
		this.isStandModify = isStandModify;
	}
}
