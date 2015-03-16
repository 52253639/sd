package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class CasSetting implements Serializable {

	private MoneyDefineInfo quitMoneyType;
    private MoneyDefineInfo changeMoneyType;
	private MoneyDefineInfo changeRoomMoney;
	private ChangeBalanceObjectEnum changeBalance;
	private MoneyDefineInfo sincerMoneyType;
	
	private Boolean isQuitUpdate;	//是否允许退房费用款项修改
	private Boolean isChangeUpdate;	//是否允许换房费用款项修改
	private Boolean isChangeRoomUpdate; //是否允许换房转款款项修改
	private Boolean isChangeObjectUpdate;  //是否允许换房结算对象修改
	private Boolean isSincerUpdate;  //是否允许诚意金额款项修改
	
	
	public MoneyDefineInfo getQuitMoneyType() {
		return quitMoneyType;
	}
	public void setQuitMoneyType(MoneyDefineInfo quitMoneyType) {
		this.quitMoneyType = quitMoneyType;
	}
	public MoneyDefineInfo getChangeMoneyType() {
		return changeMoneyType;
	}
	public void setChangeMoneyType(MoneyDefineInfo changeMoneyType) {
		this.changeMoneyType = changeMoneyType;
	}
	public MoneyDefineInfo getChangeRoomMoney() {
		return changeRoomMoney;
	}
	public void setChangeRoomMoney(MoneyDefineInfo changeRoomMoney) {
		this.changeRoomMoney = changeRoomMoney;
	}
	public ChangeBalanceObjectEnum getChangeBalance() {
		return changeBalance;
	}
	public void setChangeBalance(ChangeBalanceObjectEnum changeBalance) {
		this.changeBalance = changeBalance;
	}
	public MoneyDefineInfo getSincerMoneyType() {
		return sincerMoneyType;
	}
	public void setSincerMoneyType(MoneyDefineInfo sincerMoneyType) {
		this.sincerMoneyType = sincerMoneyType;
	}
	public Boolean getIsQuitUpdate() {
		return isQuitUpdate;
	}
	public void setIsQuitUpdate(Boolean isQuitUpdate) {
		this.isQuitUpdate = isQuitUpdate;
	}
	public Boolean getIsChangeUpdate() {
		return isChangeUpdate;
	}
	public void setIsChangeUpdate(Boolean isChangeUpdate) {
		this.isChangeUpdate = isChangeUpdate;
	}
	public Boolean getIsChangeRoomUpdate() {
		return isChangeRoomUpdate;
	}
	public void setIsChangeRoomUpdate(Boolean isChangeRoomUpdate) {
		this.isChangeRoomUpdate = isChangeRoomUpdate;
	}
	public Boolean getIsChangeObjectUpdate() {
		return isChangeObjectUpdate;
	}
	public void setIsChangeObjectUpdate(Boolean isChangeObjectUpdate) {
		this.isChangeObjectUpdate = isChangeObjectUpdate;
	}
	public Boolean getIsSincerUpdate() {
		return isSincerUpdate;
	}
	public void setIsSincerUpdate(Boolean isSincerUpdate) {
		this.isSincerUpdate = isSincerUpdate;
	}
	
	
	
	
	
}
