package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class FunctionSetting implements Serializable {

	private Boolean isBasePrice;				//启用强制底价控制
	private Boolean isSignGathering;			//启用签约收款控制
	private Boolean isMortagage;				//启用抵押关联控制
	private Boolean isActGathering;				//认购业务实际收款为准
	private Boolean isSincerSellOrder;			//启用允许诚意认购未开盘房间	
	private Boolean isSincerPrice;				//启用允许诚意金必须入账 
	private Boolean isHouseMoney;				//定金是否隶属房款
	private Boolean isEditPurAndSignDate;	    //认购/签约日期是否允许编辑
	private Boolean	isAdjustPrices;				//退房是否必须调价
	private Boolean isLoanReceiving;            //按揭款项必须按揭办理完成后才能收款
//	private Boolean isChoose;					//是否启用选房
//	private Integer chooseTime;						//房间锁定时间（分钟）
	
	public Boolean getIsLoanReceiving() {
		return isLoanReceiving;
	}
	public void setIsLoanReceiving(Boolean isLoanReceiving) {
		this.isLoanReceiving = isLoanReceiving;
	}
	public Boolean getIsEditPurAndSignDate() {
		return isEditPurAndSignDate;
	}
	public void setIsEditPurAndSignDate(Boolean isEditPurAndSignDate) {
		this.isEditPurAndSignDate = isEditPurAndSignDate;
	}
	public Boolean getIsAdjustPrices() {
		return isAdjustPrices;
	}
	public void setIsAdjustPrices(Boolean isAdjustPrices) {
		this.isAdjustPrices = isAdjustPrices;
	}
	public Boolean getIsBasePrice() {
		return isBasePrice;
	}
	public void setIsBasePrice(Boolean isBasePrice) {
		this.isBasePrice = isBasePrice;
	}
	public Boolean getIsSignGathering() {
		return isSignGathering;
	}
	public void setIsSignGathering(Boolean isSignGathering) {
		this.isSignGathering = isSignGathering;
	}
	public Boolean getIsMortagage() {
		return isMortagage;
	}
	public void setIsMortagage(Boolean isMortagage) {
		this.isMortagage = isMortagage;
	}
	public Boolean getIsActGathering() {
		return isActGathering;
	}
	public void setIsActGathering(Boolean isActGathering) {
		this.isActGathering = isActGathering;
	}
	public Boolean getIsSincerSellOrder() {
		return isSincerSellOrder;
	}
	public void setIsSincerSellOrder(Boolean isSincerSellOrder) {
		this.isSincerSellOrder = isSincerSellOrder;
	}
	public Boolean getIsSincerPrice() {
		return isSincerPrice;
	}
	public void setIsSincerPrice(Boolean isSincerPrice) {
		this.isSincerPrice = isSincerPrice;
	}
	public Boolean getIsHouseMoney() {
		return isHouseMoney;
	}
	public void setIsHouseMoney(Boolean isHouseMoney) {
		this.isHouseMoney = isHouseMoney;
	}
//	public Boolean getIsChoose() {
//		return isChoose;
//	}
//	public void setIsChoose(Boolean isChoose) {
//		this.isChoose = isChoose;
//	}
//	public Integer getChooseTime() {
//		return chooseTime;
//	}
//	public void setChooseTime(Integer chooseTime) {
//		this.chooseTime = chooseTime;
//	}
//	
	
	
	
	
}
