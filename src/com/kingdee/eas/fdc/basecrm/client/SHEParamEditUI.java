/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.param.IOtherParam;
import com.kingdee.eas.base.param.client.ICustomParamUI;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgTreeInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CasSetting;
import com.kingdee.eas.fdc.sellhouse.ChangeBalanceObjectEnum;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PreMoneySetting;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 * 
 * 
 * 20100506由于项目级别的设置不适合搬迁到系统参数中去，因而售楼设置搬迁到系统参数中的工作先暂停
 * 日后可考虑，吧项目的相关设置放到项目实体上去设置，其它的可以迁移到系统设置里去
 * 
 */
public class SHEParamEditUI extends AbstractSHEParamEditUI implements IOtherParam, ICustomParamUI
{
	private static final Logger logger = CoreUIObject.getLogger(SHEParamEditUI.class);
	
	protected SaleOrgUnitInfo curOrgUnit = null;
	protected HashMap paramHashMap = null;
	
	RoomDisplaySetting setting = new RoomDisplaySetting();
	
	public SHEParamEditUI() throws Exception {
		super();
	}

	public void otherParamSave() throws BOSException, EASBizException {
		//只有集团下才能修改
		if(!curOrgUnit.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showInfo("只有集团下才能修改");
			this.abort();
		}		
		
		
		if(!this.isQuitUpdate.isSelected() && this.f7QuitMoneyType.getValue()==null)	{
			MsgBox.showInfo("默认退房费用款项行允许单据修改没有勾选默认退房费用款项必录");
			return;
		}else if(!this.isChangeUpdate.isSelected() && this.f7ChangeMoneyType.getValue()==null)	{
			MsgBox.showInfo("默认换房费用款项行允许单据修改没有勾选默认换房费用款项必录");
			return;
		}else if(!this.idChangeRoomUpdate.isSelected() && this.f7ChangeRoomMoney.getValue()==null)	{
			MsgBox.showInfo("默认换房转款款项行允许单据修改没有勾选默认换房转款款项必录");
			return;
		}else if(!this.isChangeObjectUpdate.isSelected() && this.f7ChangeBalanceObject.getSelectedItem()==null)	{
			MsgBox.showInfo("默认换房结算对象行允许单据修改没有勾选默认换房结算对象必录");
			return;
		}else if(!this.isSincerUpdate.isSelected() && this.f7SincerMoneyType.getValue()==null)		{
			MsgBox.showInfo("默认诚意金额款项行允许单据修改没有勾选默认诚意金额款项必录");
			return;
		}	
		CasSetting casSet = new CasSetting(); 
		casSet.setQuitMoneyType((MoneyDefineInfo)this.f7QuitMoneyType.getValue());
		casSet.setChangeMoneyType((MoneyDefineInfo)this.f7ChangeMoneyType.getValue());
		casSet.setChangeRoomMoney((MoneyDefineInfo)this.f7ChangeRoomMoney.getValue());
		casSet.setChangeBalance((ChangeBalanceObjectEnum)this.f7ChangeBalanceObject.getSelectedItem());
		casSet.setSincerMoneyType((MoneyDefineInfo)this.f7SincerMoneyType.getValue());
		casSet.setIsQuitUpdate(new Boolean(this.isQuitUpdate.isSelected()));
		casSet.setIsChangeUpdate(new Boolean(this.isChangeUpdate.isSelected()));
		casSet.setIsChangeRoomUpdate(new Boolean(this.idChangeRoomUpdate.isSelected()));
		casSet.setIsChangeObjectUpdate(new Boolean(this.isChangeObjectUpdate.isSelected()));
		casSet.setIsSincerUpdate(new Boolean(this.isSincerUpdate.isSelected()));
		setting.setCasSetting(casSet);
		
		setting.getFunctionSetMap().clear();
		for(int i=0;i<this.kDTable1.getRowCount();i++) {
			IRow row = this.kDTable1.getRow(i);
			SellProjectInfo sellProInfo = (SellProjectInfo)row.getUserObject();
			if(sellProInfo!=null) {
				FunctionSetting funcSet = new FunctionSetting();
				funcSet.setIsActGathering((Boolean)row.getCell("actGathering").getValue());
				funcSet.setIsBasePrice((Boolean)row.getCell("basePrice").getValue());
				funcSet.setIsMortagage((Boolean)row.getCell("mortagage").getValue());
				funcSet.setIsSignGathering((Boolean)row.getCell("signGathering").getValue());
				funcSet.setIsSincerSellOrder((Boolean)row.getCell("isSincerSellOrder").getValue());
				funcSet.setIsSincerPrice((Boolean)row.getCell("isSincerPrice").getValue());
				funcSet.setIsHouseMoney((Boolean)row.getCell("isHouseMoney").getValue());
				funcSet.setIsEditPurAndSignDate((Boolean)row.getCell("isEditPurAndSignDate").getValue());
				funcSet.setIsAdjustPrices((Boolean)row.getCell("isAdjustPrices").getValue());
				setting.getFunctionSetMap().put(sellProInfo.getId().toString(), funcSet);
			}
		}
		
		setting.getPreMoneySetMap().clear();
		for(int i=0;i<this.kDTable2.getRowCount();i++) {
			IRow row = this.kDTable2.getRow(i);
			Boolean isLevelModify = (Boolean)row.getCell("isLevelModify").getValue();
			Boolean isStandModify = (Boolean)row.getCell("isStandModify").getValue();
			SellProjectInfo sellProInfo = (SellProjectInfo)row.getUserObject();
			if(sellProInfo!=null) {
				PreMoneySetting preSet = new PreMoneySetting();
				BigDecimal preLevelAmount = (BigDecimal)row.getCell("preLevelAmount").getValue();
				BigDecimal preStandAmount = (BigDecimal)row.getCell("preStandAmount").getValue();
				if(preLevelAmount==null || preStandAmount==null) {
					MsgBox.showInfo("项目("+sellProInfo.getName()+")的预订最低金额和预订标准金额必须设置金额！");
					return;
				}
				if(preLevelAmount.compareTo(new BigDecimal(0))<0 || preStandAmount.compareTo(new BigDecimal(0))<0){
					MsgBox.showInfo("项目("+sellProInfo.getName()+")的预订最低金额必须和预订标准金额不能小于0！");
					return;
				}
				if(preLevelAmount.compareTo(preStandAmount)>0){
					MsgBox.showInfo("项目("+sellProInfo.getName()+")的预订最低金额不能大于预订标准金额！");
					return;
				}
				if(!isStandModify.booleanValue() && preStandAmount.compareTo(new BigDecimal(0))<=0){
					MsgBox.showInfo("项目("+sellProInfo.getName()+")的预订标准金额若不能修改则必须>0！");
					return;
				}				
				preSet.setPreLevelAmount(preLevelAmount);
				preSet.setIsLevelModify(isLevelModify);
				preSet.setPreStandAmount(preStandAmount);
				preSet.setIsStandModify(isStandModify);
				setting.getPreMoneySetMap().put(sellProInfo.getId().toString(), preSet);
			}
		}
		
		
		try {
			//setting.saveNew();
		} catch (Exception e) {
			e.printStackTrace();
			this.handUIException(e);
			this.abort();
		}
	}

	public void sendOrgInfo(OrgType orgType, OrgUnitInfo orgUnit, OrgTreeInfo orgTree) {
		try {
			if (curOrgUnit == null) {
				curOrgUnit = SysContext.getSysContext().getCurrentSaleUnit();
			}			
			
			initUIData();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	
	private void initUIData() throws Exception{
		CasSetting casSet = (CasSetting)setting.getCasSetting();
		if(casSet!=null) {
			this.f7QuitMoneyType.setValue(casSet.getQuitMoneyType());
			this.f7ChangeMoneyType.setValue(casSet.getChangeMoneyType());
			this.f7ChangeRoomMoney.setValue(casSet.getChangeRoomMoney());
			this.f7ChangeBalanceObject.setSelectedItem(casSet.getChangeBalance());
			this.f7SincerMoneyType.setValue(casSet.getSincerMoneyType());
			this.isQuitUpdate.setSelected(casSet.getIsQuitUpdate().booleanValue());
			this.isChangeUpdate.setSelected(casSet.getIsChangeUpdate().booleanValue());
			this.idChangeRoomUpdate.setSelected(casSet.getIsChangeRoomUpdate().booleanValue());
			this.isChangeObjectUpdate.setSelected(casSet.getIsChangeObjectUpdate().booleanValue());
			this.isSincerUpdate.setSelected(casSet.getIsSincerUpdate().booleanValue());
		}else{
			this.isQuitUpdate.setSelected(true);
			this.isChangeUpdate.setSelected(true);
			this.idChangeRoomUpdate.setSelected(true);
			this.isChangeObjectUpdate.setSelected(true);
			this.isSincerUpdate.setSelected(true);
		}
		
		this.kDTable1.checkParsed();
		Map projectMap = SHEHelper.createTreeByBaseProject(this.actionOnLoad,this.kDTable1,MoneySysTypeEnum.SalehouseSys);
		this.kDTable2.checkParsed();
		Map projectPreMap = SHEHelper.createTreeByBaseProject(this.actionOnLoad,this.kDTable2,MoneySysTypeEnum.SalehouseSys);

		Set projectSet = projectMap.keySet();
		Iterator proIter = projectSet.iterator();
		while(proIter.hasNext()) {
			String sellProId = (String)proIter.next();
			IRow thisRow = (IRow)projectMap.get(sellProId);
			if(thisRow!=null) {
				FunctionSetting proSet = (FunctionSetting)setting.getFunctionSetMap().get(sellProId);
				thisRow.getCell("actGathering").setValue(proSet==null?Boolean.TRUE:proSet.getIsActGathering());
				thisRow.getCell("basePrice").setValue(proSet==null?Boolean.FALSE:proSet.getIsBasePrice());
				thisRow.getCell("mortagage").setValue(proSet==null?Boolean.FALSE:proSet.getIsMortagage());
				thisRow.getCell("signGathering").setValue(proSet==null?Boolean.FALSE:proSet.getIsSignGathering());
				thisRow.getCell("isSincerSellOrder").setValue(proSet==null?Boolean.FALSE:proSet.getIsSincerSellOrder());
				thisRow.getCell("isSincerPrice").setValue(proSet==null?Boolean.FALSE:proSet.getIsSincerPrice());
				thisRow.getCell("isHouseMoney").setValue(proSet==null?Boolean.TRUE:proSet.getIsHouseMoney());
				thisRow.getCell("isEditPurAndSignDate").setValue(proSet==null?Boolean.TRUE:proSet.getIsEditPurAndSignDate());
				thisRow.getCell("isAdjustPrices").setValue(proSet==null?Boolean.FALSE:proSet.getIsAdjustPrices());
			}
		}
		
		this.kDTable2.getColumn("preLevelAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		this.kDTable2.getColumn("preStandAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		Set preProSet = projectPreMap.keySet();
		Iterator preIter = preProSet.iterator();
		while(preIter.hasNext()) {
			String sellProId = (String)preIter.next();
			IRow thisRow = (IRow)projectPreMap.get(sellProId);
			if(thisRow!=null) {
				PreMoneySetting preSet = (PreMoneySetting)setting.getPreMoneySetMap().get(sellProId);
				thisRow.getCell("preLevelAmount").setValue(preSet==null?new BigDecimal(0.00):preSet.getPreLevelAmount());
				thisRow.getCell("isLevelModify").setValue(preSet==null?Boolean.TRUE:preSet.getIsLevelModify());
				thisRow.getCell("preStandAmount").setValue(preSet==null?new BigDecimal(0.00):preSet.getPreStandAmount());
				thisRow.getCell("isStandModify").setValue(preSet==null?Boolean.TRUE:preSet.getIsStandModify());
			}
		}
	}
	
	
	public void onLoad() throws Exception {
		super.onLoad();
		

		
	}
	
	
	
	
	
	protected void isQuitUpdate_stateChanged(ChangeEvent e) throws Exception {
		super.isQuitUpdate_stateChanged(e);
		if(new Boolean(this.isQuitUpdate.isSelected()).equals(new Boolean(false)))
		{
			this.f7QuitMoneyType.setRequired(true);
		}else
		{
			this.f7QuitMoneyType.setRequired(false);
		}
	}
	
	protected void isChangeUpdate_stateChanged(ChangeEvent e) throws Exception {
		super.isChangeUpdate_stateChanged(e);
		if(new Boolean(this.isChangeUpdate.isSelected()).equals(new Boolean(false)))
		{
			this.f7ChangeMoneyType.setRequired(true);
		}else
		{
			this.f7ChangeMoneyType.setRequired(false);
		}
	}
	
	protected void isChangeObjectUpdate_stateChanged(ChangeEvent e)
			throws Exception {
		super.isChangeObjectUpdate_stateChanged(e);
		if(new Boolean(this.isChangeObjectUpdate.isSelected()).equals(new Boolean(false)))
		{
			this.f7ChangeBalanceObject.setRequired(true);
		}else
		{
			this.f7ChangeBalanceObject.setRequired(false);
		}
	}
	protected void isSincerUpdate_stateChanged(ChangeEvent e) throws Exception {
		super.isSincerUpdate_stateChanged(e);
		if(new Boolean(this.isSincerUpdate.isSelected()).equals(new Boolean(false)))
		{
			this.f7SincerMoneyType.setRequired(true);
		}else
		{
			this.f7SincerMoneyType.setRequired(false);
		}
	}
	protected void idChangeRoomUpdate_stateChanged(ChangeEvent e)
			throws Exception {
		super.idChangeRoomUpdate_stateChanged(e);
		if(new Boolean(this.idChangeRoomUpdate.isSelected()).equals(new Boolean(false)))
		{
			this.f7ChangeRoomMoney.setRequired(true);
		}else
		{
			this.f7ChangeRoomMoney.setRequired(false);
		}
	}
	
	
	
    

}