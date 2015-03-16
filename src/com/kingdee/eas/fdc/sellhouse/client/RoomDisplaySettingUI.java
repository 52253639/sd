/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFontChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.PropertyCollection;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BaseRoomSetting;
import com.kingdee.eas.fdc.sellhouse.CasSetting;
import com.kingdee.eas.fdc.sellhouse.ChangeBalanceObjectEnum;
import com.kingdee.eas.fdc.sellhouse.FaithAmountSetting;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PreMoneySetting;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomDisplaySettingUI extends AbstractRoomDisplaySettingUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomDisplaySettingUI.class);

	RoomDisplaySetting setting = new RoomDisplaySetting();

	Map proNameMap = new HashMap();		//房间的名称和别名的映射
	Map proAliasMap = new HashMap();	//房间的别名和名称的映射
	
	
	/**
	 * output class constructor
	 */
	public RoomDisplaySettingUI() throws Exception {
		super();
	}

	private KDFontChooser contFont = new KDFontChooser();

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		//取消此参数的使用 xin_wang 2010.09.08
		this.isPreToOtherMoney.setVisible(false);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID))
		{
			MsgBox.showInfo("非集团用户不能修改显示设置!");
			this.abort();
		}
		Rectangle rectangle = this.labelFont.getBounds();
		rectangle.x += 88;
		rectangle.x -= 3;
		rectangle.width = 600;
		contFont.setBounds(new Rectangle(rectangle));
		this.kDPanel1.add(contFont, new KDLayout.Constraints(rectangle.x, rectangle.y,rectangle.width, rectangle.height, 0));
		EntityObjectInfo entity = MetaDataLoaderFactory.getRemoteMetaDataLoader().getEntity(new RoomInfo().getBOSType());
		PropertyCollection pros = entity.getInheritedProperties();
		
		int tempDebug = 0;
		
		for (int i = 0; i < pros.size(); i++) 
		{
			PropertyInfo pro = pros.get(i);
			comboRoomField.addItem(pro.getAlias());
			this.proNameMap.put(pro.getName(), pro.getAlias());
			this.proAliasMap.put(pro.getAlias(), pro.getName());
			
			if(pro.getAlias().equalsIgnoreCase("编码"))
				tempDebug = i;
			
		}
		this.comboAttachDis.addItem("显示绑定房间个数");
		this.comboAttachDis.addItem("显示具体房间");
		this.comboAttachDis.addItem("不显示绑定房间");
		//增加认购收款颜色默认与认购颜色相同   by zgy 2010-12-10  飞鹰计划（需求：刘威）
		this.comboSinReColor.setColor(setting.getBaseRoomSetting().getSinReColor());
		
		this.comboInitColor.setColor(setting.getBaseRoomSetting().getInitColor());
		this.comboOnShowColor.setColor(setting.getBaseRoomSetting().getOnShowColor());
		this.comboPrePurColor.setColor(setting.getBaseRoomSetting().getPrePurColor());
		this.comboPurColor.setColor(setting.getBaseRoomSetting().getPurColor());
		this.comboSignColor.setColor(setting.getBaseRoomSetting().getSignColor());
		this.comboKeepDownColor.setColor(setting.getBaseRoomSetting().getKeepDownColor());
		this.comboNoSellColor.setColor(setting.getBaseRoomSetting().getNoSellhouseColor());
		this.comboSincerPurColor.setColor(setting.getBaseRoomSetting().getSincerPurColor());
		this.txtRoomHeight.setValue(new Integer(setting.getRoomHeight()));
		this.txtRoomWidth.setValue(new Integer(setting.getRoomWidth()));
		this.comboRoomField.setSelectedItem(proNameMap.get(setting.getDisplayField()));
		this.comboAttachDis.setSelectedIndex(setting.getAttachDisType());
		this.contFont.setSelectionFont(setting.getFont());
		this.comboFrontColor.setColor(setting.getFrontColor());
		
		this.comboRoomField.setSelectedIndex(tempDebug);
		this.comboRoomField.setEnabled(false);
		this.txtRoomHeight.setNegatived(false);
		this.txtRoomWidth.setNegatived(false);
		this.cbIsAuditDate.setSelected(setting.getBaseRoomSetting().isAuditDate());
/*      这里有大问题，构建的树是按登录人的组织结构产生的，因而导致保存的项目是不全的
  		this.kDTable1.checkParsed();
		Map projectMap = SHEHelper.createTreeByBaseProject(this.actionOnLoad,this.kDTable1,MoneySysTypeEnum.SalehouseSys);
		this.kDTable2.checkParsed();
		Map projectPreMap = SHEHelper.createTreeByBaseProject(this.actionOnLoad,this.kDTable2,MoneySysTypeEnum.SalehouseSys);
*/		
		this.kDTable1.checkParsed();
		Map projectMap = new HashMap();
		this.kDTable2.checkParsed();
		Map projectPreMap = new HashMap();
		this.kDTable3.checkParsed();
		Map faithAmtMap = new HashMap();
		SellProjectCollection spColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection(
								"select name,orgUnit.name where isForShe = 1 order by orgUnit.number ");
		for(int i=0;i<spColl.size();i++) {
			SellProjectInfo spInfo = spColl.get(i);
			IRow row1 = this.kDTable1.addRow();
			row1.getCell("name").setValue(spInfo.getName());
			row1.setUserObject(spInfo);
			projectMap.put(spInfo.getId().toString(), row1);
			
			IRow row2 = this.kDTable2.addRow();
			row2.getCell("name").setValue(spInfo.getName());
			row2.setUserObject(spInfo);
			projectPreMap.put(spInfo.getId().toString(), row2);
			
			IRow row3 = this.kDTable3.addRow();
			row3.getCell("name").setValue(spInfo.getName());
			row3.setUserObject(spInfo);
			faithAmtMap.put(spInfo.getId().toString(), row3);
		    
		}
		
		this.initTable();
		
		
		initF7QuitMoneyFilter();
		initF7SincerFilter();		
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
		
		
		Set projectSet = projectMap.keySet();
		Iterator proIter = projectSet.iterator();
		while(proIter.hasNext()) {
			String sellProId = (String)proIter.next();
			IRow thisRow = (IRow)projectMap.get(sellProId);
			if(thisRow!=null) {
				FunctionSetting proSet = (FunctionSetting)setting.getFunctionSetMap().get(sellProId);
				//修改  认购业务以实际收款为准  参数默认为不勾选  by  zgy  2010-12-10
				thisRow.getCell("actGathering").setValue(proSet==null?Boolean.FALSE:proSet.getIsActGathering());
				thisRow.getCell("basePrice").setValue(proSet==null?Boolean.FALSE:proSet.getIsBasePrice());
				thisRow.getCell("mortagage").setValue(proSet==null?Boolean.FALSE:proSet.getIsMortagage());
				thisRow.getCell("signGathering").setValue(proSet==null?Boolean.FALSE:proSet.getIsSignGathering());
				thisRow.getCell("isSincerSellOrder").setValue(proSet==null?Boolean.FALSE:proSet.getIsSincerSellOrder());
				thisRow.getCell("isSincerPrice").setValue(proSet==null?Boolean.FALSE:proSet.getIsSincerPrice());
				thisRow.getCell("isHouseMoney").setValue(proSet==null?Boolean.TRUE:proSet.getIsHouseMoney());
				thisRow.getCell("isEditPurAndSignDate").setValue(proSet==null?Boolean.TRUE:proSet.getIsEditPurAndSignDate());
				thisRow.getCell("isAdjustPrices").setValue(proSet==null?Boolean.FALSE:proSet.getIsAdjustPrices());
				thisRow.getCell("isLoanReceiving").setValue(proSet==null?Boolean.FALSE:proSet.getIsLoanReceiving());
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
		
		//初始化默认诚意金额
		this.kDTable3.getColumn("faithAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		Set  faithAmtSet = faithAmtMap.keySet();
		Iterator faithAmtIter = faithAmtSet.iterator();
		while(faithAmtIter.hasNext()){
			String sellProId = (String)faithAmtIter.next();
			IRow faithAmtRow = (IRow) faithAmtMap.get(sellProId);
			if(faithAmtRow != null){
				FaithAmountSetting faithSet = (FaithAmountSetting)setting.getFaithAmountSetMap().get(sellProId);
				faithAmtRow.getCell("faithAmount").setValue(faithSet==null?new BigDecimal(0.00):faithSet.getFaithAmount());
			}
		}
		//初始化是否预定金自动转定金 Eric_wang 2010.08.12
		this.isPreToOtherMoney.setSelected(setting.getIsPreToOtherMoneyMap().get("isPreToOtherMoney")==null?true:((Boolean)setting.getIsPreToOtherMoneyMap().get("isPreToOtherMoney")).booleanValue());
		
		
	}


	/*
	 * 认购业务与实际收款为准参数  与 选房功能互斥
	 * by zgy 2010-12-28
	 */

	 protected void kDTable1_activeCellChanged(KDTActiveCellEvent e)
			throws Exception {
		if (kDTable1.getRowCount() != 0) {
			if (kDTable1.getColumn("actGathering").getColumnIndex() == e.getColumnIndex()) {
				IRow ir = kDTable1.getRow(e.getRowIndex());
				SellProjectInfo sellProInfo = (SellProjectInfo) ir.getUserObject();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProInfo.getId()));
				if (PurchaseFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("该项目下已经发生认购业务，认购业务与实际收款为准参数不能修改!");
					kDTable1.getRow(e.getRowIndex()).getCell("actGathering").getStyleAttributes().setLocked(true);
					return;
				}
			}
		}
		
	}

	protected void initF7QuitMoneyFilter()
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(
				new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType", MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType", MoneyTypeEnum.ELSEAMOUNT_VALUE));
		filter.setMaskString("#0 and (#1 or #2)");
		view.setFilter(filter);
		this.f7QuitMoneyType.setEntityViewInfo(view);
		this.f7ChangeMoneyType.setEntityViewInfo(view);
	}
	
	protected void initF7SincerFilter()
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(
				new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType", MoneyTypeEnum.PREMONEY_VALUE));;
		view.setFilter(filter);
		this.f7ChangeRoomMoney.setEntityViewInfo(view);
		this.f7SincerMoneyType.setEntityViewInfo(view);
	}
	
	protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
		super.btnNo_actionPerformed(e);
		this.destroyWindow();
	}

	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		super.btnYes_actionPerformed(e);
		BaseRoomSetting baseRoomSet = new BaseRoomSetting();
		//增加认购收款颜色默认与认购颜色相同   by zgy 2010-12-10  飞鹰计划（需求：刘威）
		baseRoomSet.setSinReColor(this.comboSinReColor.getColor());
		
		baseRoomSet.setInitColor(this.comboInitColor.getColor());
		baseRoomSet.setOnShowColor(this.comboOnShowColor.getColor());
		baseRoomSet.setPrePurColor(this.comboPrePurColor.getColor());
		baseRoomSet.setPurColor(this.comboPurColor.getColor());
		baseRoomSet.setSignColor(this.comboSignColor.getColor());
		baseRoomSet.setKeepDownColor(this.comboKeepDownColor.getColor());
		baseRoomSet.setNoSellhouseColor(this.comboNoSellColor.getColor());
		baseRoomSet.setSincerPurColor(this.comboSincerPurColor.getColor());
		baseRoomSet.setRoomHeight(this.txtRoomHeight.getBigDecimalValue().intValue());
		baseRoomSet.setRoomWidth(this.txtRoomWidth.getBigDecimalValue().intValue());
		baseRoomSet.setDisplayField((String) proAliasMap.get(this.comboRoomField
				.getSelectedItem()));
		baseRoomSet.setAttachDisType(this.comboAttachDis.getSelectedIndex());		
		baseRoomSet.setFont(this.contFont.getSelectionFont());
		baseRoomSet.setFrontColor(this.comboFrontColor.getColor());
		baseRoomSet.setAuditDate(this.cbIsAuditDate.isSelected());
		setting.setBaseRoomSetting(baseRoomSet);

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
				funcSet.setIsLoanReceiving((Boolean)row.getCell("isLoanReceiving").getValue());
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
		
		//诚意默认金额
		setting.getFaithAmountSetMap().clear();
		for(int i=0;i<this.kDTable3.getRowCount();i++) {
			IRow row  = this.kDTable3.getRow(i);
			SellProjectInfo sellProInfo = (SellProjectInfo)row.getUserObject();
			if(sellProInfo!= null){
				FaithAmountSetting faitSet = new FaithAmountSetting();
				BigDecimal faithAmount = row.getCell("faithAmount").getValue()==null?new BigDecimal("0.00"):new BigDecimal(String.valueOf(row.getCell("faithAmount").getValue()));
				if(faithAmount!=null &&faithAmount.compareTo(new BigDecimal(0))<0){
					MsgBox.showInfo("项目("+sellProInfo.getName()+")的默认金额不能小于0！");
					return;
				}
				faitSet.setFaithAmount(faithAmount);
				setting.getFaithAmountSetMap().put(sellProInfo.getId().toString(), faitSet);
			}
		}
		//预定转认购时，预定金自动转定金
		setting.getIsPreToOtherMoneyMap().clear();
		setting.getIsPreToOtherMoneyMap().put("isPreToOtherMoney", Boolean.valueOf(this.isPreToOtherMoney.isSelected()));
		
		setting.save();
		this.destroyWindow();
	}
	
	private void initTable()
	{
		 KDCheckBox item = new KDCheckBox();
		    item.addActionListener(new ActionListener()
		    		{
						public void actionPerformed(ActionEvent arg0)
						{
							//这个用法太神奇了，只是为了让复选框的 焦点转移，以便第二次单击的时候，来触发 table_Clicked事件
							//kDPanel1.requestFocus();
						}
		    		});
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