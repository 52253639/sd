/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CasSetting;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastCollection;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastFactory;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.QuitNewPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.QuitNewPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitOldPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.QuitOldPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomChangeFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomChangeInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

public class QuitRoomChangeEditUI extends AbstractQuitRoomChangeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuitRoomChangeEditUI.class);

    private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
    
    public QuitRoomChangeEditUI() throws Exception
    {
        super();
    }
    
    protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
    
    public void onLoad() throws Exception {
    	initTable();
    	super.onLoad();
    	if(moneyAccountMapping == null){
			initMoneyAccountMapping();
		}
    	
    	this.actionFirst.setVisible(false);
    	this.actionNext.setVisible(false);
    	this.actionPre.setVisible(false);
    	this.actionLast.setVisible(false);
    	this.separatorFW3.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.menuView.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	this.actionAddLine.setVisible(false);
    	this.actionInsertLine.setVisible(false);
    	this.actionRemoveLine.setVisible(false);
    	this.menuTable1.setVisible(false);
    	
    	
    	this.actionCopy.setVisible(false);
    	this.actionPrint.setVisible(false);
    	this.actionPrintPreview.setVisible(false);
    	this.actionCancel.setVisible(false);
    	this.actionCancelCancel.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.txtNumber.setRequired(true);
    	this.txtQuitChangeReason.setRequired(true);
    	this.f7NewFeeAccount.setRequired(true);
    	this.f7NewFeeMoneyDefine.setRequired(true);
    	this.actionSave.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.menuBiz.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.menuSubmitOption.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionRemove.setVisible(false);
		
		this.storeFields();
		initOldData(this.editData);
    }
    
    private void initTable()
    {
    	SHEHelper.setTextFormat(txtOldTolActAmount);
    	SHEHelper.setTextFormat(txtOldCanRefundment);
    	SHEHelper.setTextFormat(txtOldFeeAmount);
    	this.kDTable1.checkParsed();
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setMinimumValue(FDCHelper.MIN_DECIMAL);
		formattedTextField.setMaximumValue(new BigDecimal(Integer.MAX_VALUE));
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);

		this.kDTable1.getColumn("canRefundmentAmount").setEditor(numberEditor);

		this.kDTable1.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kDTable1.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.kDTable1.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kDTable1.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.kDTable1.getColumn("moneyDefine").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("appAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("actAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setLocked(true);
		
		SHEHelper.setTextFormat(txtNewTolActAmount);
    	SHEHelper.setTextFormat(txtNewCanRefundment);
    	SHEHelper.setTextFormat(txtNewFeeAmount);
    	this.kDTable2.checkParsed();
		KDFormattedTextField formattedTextField2 = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField2.setPrecision(2);
		formattedTextField2.setSupportedEmpty(true);
		formattedTextField2.setMinimumValue(FDCHelper.MIN_DECIMAL);
		formattedTextField2.setMaximumValue(new BigDecimal(Integer.MAX_VALUE));
		ICellEditor numberEditor2 = new KDTDefaultCellEditor(formattedTextField2);

		this.kDTable2.getColumn("newCanRefundmentAmount").setEditor(numberEditor2);

		this.kDTable2.getColumn("newAppAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kDTable2.getColumn("newAppAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.kDTable2.getColumn("newActAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kDTable2.getColumn("newActAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.kDTable2.getColumn("newCanRefundmentAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kDTable2.getColumn("newCanRefundmentAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.kDTable2.getColumn("newMoneyDefine").getStyleAttributes().setLocked(true);
		this.kDTable2.getColumn("newAppAmount").getStyleAttributes().setLocked(true);
		this.kDTable2.getColumn("newActAmount").getStyleAttributes().setLocked(true);
		setMoneyDefineAndAcount();
		
		this.kDTable1.checkParsed();
		this.kDTable2.checkParsed();
    }
    
    /** 设置费用款项和会计科目 */
	private void setMoneyDefineAndAcount() {
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext().getCurrentFIUnit();
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox opseelect = new AccountPromptBox(this, curCompany, view.getFilter(), false, true);
		this.f7NewFeeAccount.setEntityViewInfo(view);
		this.f7OldFeeAccount.setEntityViewInfo(view);
		this.f7NewFeeAccount.setSelector(opseelect);
		this.f7NewFeeAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");

		// 设置费用款项的过滤 只能从 只能从手续费,其他两种类别中选择
		view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ELSEAMOUNT_VALUE));
		filter.setMaskString("#0 and (#1 or #2)");
		view.setFilter(filter);
		this.f7NewFeeMoneyDefine.setEntityViewInfo(view);
		this.f7OldFeeMoneyDefine.setEntityViewInfo(view);

		RoomDisplaySetting setting = new RoomDisplaySetting();
		CasSetting casSet = setting.getCasSetting();
		if(casSet!=null) {
			if (casSet.getQuitMoneyType() != null) {
				if (this.getOprtState().equals(OprtState.ADDNEW))
					this.f7NewFeeMoneyDefine.setValue(casSet.getQuitMoneyType());
			}
			// 是否允许退房费用款项的修改
			if (!casSet.getIsQuitUpdate().booleanValue()) {
				this.f7NewFeeMoneyDefine.setEnabled(false);
			}
		}

	}
	
	private EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo) {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------转6.0后修改,科目不按CU隔离,根据财务组织进行隔离
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null) {
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}
	/** 校验在 退房单打开 和提交的这段时间内 付款明细有没有发生过改变 */

	//缓存款项 科目 对应表
	private Map moneyAccountMapping = null;
	//初始化款项 科目 对应表
	private void initMoneyAccountMapping() throws BOSException{
		moneyAccountMapping = new HashMap();
		
		CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if(company != null){
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", company.getId().toString()));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", null));
		}
		
		view.getSelector().add("accountView.*");
		view.getSelector().add("moneyDefine.id");
		view.getSelector().add("isChanged");
		
		MoneySubjectContrastCollection moneySubjects = MoneySubjectContrastFactory.getRemoteInstance().getMoneySubjectContrastCollection(view);
		for(int i=0; i<moneySubjects.size(); i++){
			MoneySubjectContrastInfo moneySubject = moneySubjects.get(i);
			MoneyDefineInfo moneyDefine = moneySubject.getMoneyDefine();
			if(moneyDefine != null){
				moneyAccountMapping.put(moneyDefine.getId().toString(), moneySubject);
			}
		}
	}
	/**
	 * 根据款项类型 和 款项科目对照表。带出科目设置到 对方科目列上
	 * 并根据对照表设置的是否允许修改，设置是否锁定对方科目列
	 * */
	private void setAccountByMoneyDefine(MoneyDefineInfo moneyDefine) {
		if(moneyDefine == null  ||  moneyAccountMapping == null){
			return;
		}
		MoneySubjectContrastInfo moneySubject = (MoneySubjectContrastInfo) moneyAccountMapping.get(moneyDefine.getId().toString());
		if(moneySubject == null){
			return;
		}
		
		AccountViewInfo account = moneySubject.getAccountView();
		if(account != null){
			//带出对照表中设置的 科目 设置到对方科目上
			this.f7NewFeeAccount.setValue(account);
		}else
		{
			this.f7NewFeeAccount.setValue(null);
		}
	}
	
	private void setAccountByMoneyDefine2(MoneyDefineInfo moneyDefine) {
		if(moneyDefine == null  ||  moneyAccountMapping == null){
			return;
		}
		MoneySubjectContrastInfo moneySubject = (MoneySubjectContrastInfo) moneyAccountMapping.get(moneyDefine.getId().toString());
		if(moneySubject == null){
			return;
		}
		
		AccountViewInfo account = moneySubject.getAccountView();
		if(account != null){
			//带出对照表中设置的 科目 设置到对方科目上
			this.f7OldFeeAccount.setValue(account);
		}else
		{
			this.f7OldFeeAccount.setValue(null);
		}
	}
    
    protected void prmtQuitRoom_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmtQuitRoom_dataChanged(e);
    	QuitRoomInfo quitRoom = (QuitRoomInfo)this.prmtQuitRoom.getValue();
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("*");
    	sels.add("purchase.id");
    	sels.add("quitEntrys.*");
    	sels.add("quitEntrys.moneyDefine.*");
    	sels.add("feeMoneyDefine.*");
    	sels.add("feeAccount.*");
    	quitRoom = QuitRoomFactory.getRemoteInstance().getQuitRoomInfo(new ObjectUuidPK(quitRoom.getId().toString()),sels);
    	QuitPayListEntryCollection quitEntrys = null;
    	if(quitRoom!=null)    	{
			quitEntrys = quitRoom.getQuitEntrys();
			this.f7OldFeeAccount.setValue(quitRoom.getFeeAccount());
	    	this.f7OldFeeMoneyDefine.setValue(quitRoom.getFeeMoneyDefine());
	    	this.f7OldFeeAccount.setEnabled(false);
	    	this.f7OldFeeMoneyDefine.setEnabled(false);
	    	
    		loadQuitPayList(quitEntrys);
    		loadNewQuitPayList(quitEntrys);
    	}  	
    	loadCanRefundmentAmount(quitEntrys);
    	loadNewCanRefundmentAmount(quitEntrys);  	
    }
    
    /** 加载退房单的分录列表 */
	private void loadQuitPayList(QuitPayListEntryCollection quitEntrys) {
		this.kDTable1.checkParsed();
		this.kDTable1.removeRows(false);
		for (int i = 0; i < quitEntrys.size(); i++) {
			QuitPayListEntryInfo quitEntry = quitEntrys.get(i);
			IRow row = this.kDTable1.addRow();
			row.setUserObject(quitEntry);
			row.getCell("moneyDefine").setValue(quitEntry.getMoneyDefine().getName());
			row.getCell("appAmount").setValue(quitEntry.getApAmount());
			row.getCell("actAmount").setValue(quitEntry.getActPayAmount());
			row.getCell("canRefundmentAmount").setValue(quitEntry.getCanRefundmentAmount());
		}
	}
	
	/** 加载退房单的分录列表 */
	private void loadNewQuitPayList(QuitPayListEntryCollection quitEntrys) {
		this.kDTable2.checkParsed();
		this.kDTable2.removeRows(false);
		for (int i = 0; i < quitEntrys.size(); i++) {
			QuitPayListEntryInfo quitEntry = quitEntrys.get(i);
			IRow row = this.kDTable2.addRow();
			row.setUserObject(quitEntry);
			row.getCell("newMoneyDefine").setValue(quitEntry.getMoneyDefine().getName());
			row.getCell("newAppAmount").setValue(quitEntry.getApAmount());
			row.getCell("newActAmount").setValue(quitEntry.getActPayAmount());
			row.getCell("newCanRefundmentAmount").setValue(quitEntry.getCanRefundmentAmount());
		}
	}
	//新增时调用原退房单
	private void loadCanRefundmentAmount(QuitPayListEntryCollection quitEntrys) {
		this.txtOldCanRefundment.setUserObject(quitEntrys);
		StringBuffer sb = new StringBuffer();
		BigDecimal tolActPay = FDCHelper.ZERO;
		BigDecimal tolCanRefundment = FDCHelper.ZERO;
		for (int i = 0; i < quitEntrys.size(); i++) {
			QuitPayListEntryInfo quitEntry = quitEntrys.get(i);

			BigDecimal actPay = quitEntry.getActPayAmount();
			if (actPay != null)
				tolActPay = tolActPay.add(actPay);
			BigDecimal canRefundmentAmount = quitEntry.getCanRefundmentAmount();
			MoneyDefineInfo moneyDefine = quitEntry.getMoneyDefine();
			if (i != 0)
				sb.append(";");
			sb.append(moneyDefine.getName());
			sb.append("可退");
			if (canRefundmentAmount == null || canRefundmentAmount.compareTo(FDCHelper.ZERO) == 0) {
				canRefundmentAmount = FDCHelper.ZERO;
			} else {
				canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			}

			tolCanRefundment = tolCanRefundment.add(canRefundmentAmount);

			sb.append(canRefundmentAmount);
		}
		this.txtOldTolActAmount.setValue(tolActPay);
		this.txtOldCanRefundment.setValue(tolCanRefundment);
		this.txtOldFeeAmount.setValue(tolActPay.subtract(tolCanRefundment));
		this.txtOldTolActAmount.setEnabled(false);
		this.txtOldCanRefundment.setEnabled(false);
		this.txtOldFeeAmount.setEnabled(false);
	}
	
	//非新增时调用保存起来的分录。因为在审批时退房单已经被反写。现在只能根据保存在变更单上的分录来查
	private void loadCanRefundmentAmount2(QuitOldPayListEntryCollection quitEntrys) {
		this.txtOldCanRefundment.setUserObject(quitEntrys);
		StringBuffer sb = new StringBuffer();
		BigDecimal tolActPay = FDCHelper.ZERO;
		BigDecimal tolCanRefundment = FDCHelper.ZERO;
		for (int i = 0; i < quitEntrys.size(); i++) {
			QuitOldPayListEntryInfo quitEntry = quitEntrys.get(i);

			BigDecimal actPay = quitEntry.getActPayAmount();
			if (actPay != null)
				tolActPay = tolActPay.add(actPay);
			BigDecimal canRefundmentAmount = quitEntry.getCanRefundmentAmount();
			MoneyDefineInfo moneyDefine = quitEntry.getMoneyDefine();
			if (i != 0)
				sb.append(";");
			sb.append(moneyDefine.getName());
			sb.append("可退");
			if (canRefundmentAmount == null || canRefundmentAmount.compareTo(FDCHelper.ZERO) == 0) {
				canRefundmentAmount = FDCHelper.ZERO;
			} else {
				canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			}

			tolCanRefundment = tolCanRefundment.add(canRefundmentAmount);

			sb.append(canRefundmentAmount);
		}
		this.txtOldTolActAmount.setValue(tolActPay);
		this.txtOldCanRefundment.setValue(tolCanRefundment);
		this.txtOldFeeAmount.setValue(tolActPay.subtract(tolCanRefundment));
		this.txtOldTolActAmount.setEnabled(false);
		this.txtOldCanRefundment.setEnabled(false);
		this.txtOldFeeAmount.setEnabled(false);
	}
	
	//新增时调用原退房单
	private void loadNewCanRefundmentAmount(QuitPayListEntryCollection quitEntrys) {
		this.txtNewCanRefundment.setUserObject(quitEntrys);
		StringBuffer sb = new StringBuffer();
		BigDecimal tolActPay = FDCHelper.ZERO;
		BigDecimal tolCanRefundment = FDCHelper.ZERO;
		for (int i = 0; i < quitEntrys.size(); i++) {
			QuitPayListEntryInfo quitEntry = quitEntrys.get(i);

			BigDecimal actPay = quitEntry.getActPayAmount();
			if (actPay != null)
				tolActPay = tolActPay.add(actPay);
			BigDecimal canRefundmentAmount = quitEntry.getCanRefundmentAmount();
			MoneyDefineInfo moneyDefine = quitEntry.getMoneyDefine();
			if (i != 0)
				sb.append(";");
			sb.append(moneyDefine.getName());
			sb.append("可退");
			if (canRefundmentAmount == null || canRefundmentAmount.compareTo(FDCHelper.ZERO) == 0) {
				canRefundmentAmount = FDCHelper.ZERO;
			} else {
				canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			}

			tolCanRefundment = tolCanRefundment.add(canRefundmentAmount);

			sb.append(canRefundmentAmount);
		}
		this.txtNewTolActAmount.setValue(tolActPay);
		this.txtNewCanRefundment.setValue(tolCanRefundment);
		this.txtNewFeeAmount.setValue(tolActPay.subtract(tolCanRefundment));
		this.txtNewTolActAmount.setEnabled(false);
		this.txtNewCanRefundment.setEnabled(false);
		this.txtNewFeeAmount.setEnabled(false);
	}
	
	//非新增时调用保存起来的分录。因为在审批时退房单已经被反写。现在只能根据保存在变更单上的分录来查
	private void loadNewCanRefundmentAmount2(QuitNewPayListEntryCollection quitEntrys) {
		this.txtNewCanRefundment.setUserObject(quitEntrys);
		StringBuffer sb = new StringBuffer();
		BigDecimal tolActPay = FDCHelper.ZERO;
		BigDecimal tolCanRefundment = FDCHelper.ZERO;
		for (int i = 0; i < quitEntrys.size(); i++) {
			QuitNewPayListEntryInfo quitEntry = quitEntrys.get(i);

			BigDecimal actPay = quitEntry.getActPayAmount();
			if (actPay != null)
				tolActPay = tolActPay.add(actPay);
			BigDecimal canRefundmentAmount = quitEntry.getCanRefundmentAmount();
			MoneyDefineInfo moneyDefine = quitEntry.getMoneyDefine();
			if (i != 0)
				sb.append(";");
			sb.append(moneyDefine.getName());
			sb.append("可退");
			if (canRefundmentAmount == null || canRefundmentAmount.compareTo(FDCHelper.ZERO) == 0) {
				canRefundmentAmount = FDCHelper.ZERO;
			} else {
				canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			}

			tolCanRefundment = tolCanRefundment.add(canRefundmentAmount);

			sb.append(canRefundmentAmount);
		}
		this.txtNewTolActAmount.setValue(tolActPay);
		this.txtNewCanRefundment.setValue(tolCanRefundment);
		this.txtNewFeeAmount.setValue(tolActPay.subtract(tolCanRefundment));
		this.txtNewTolActAmount.setEnabled(false);
		this.txtNewCanRefundment.setEnabled(false);
		this.txtNewFeeAmount.setEnabled(false);
	}
	
	private void reflushTotalCanRefundmentAmount() {
		BigDecimal totalValue = FDCHelper.ZERO;
		for (int i = 0; i < this.kDTable2.getRowCount(); i++) {
			IRow row = this.kDTable2.getRow(i);
			BigDecimal value = (BigDecimal) row.getCell("newCanRefundmentAmount").getValue();
			if (value != null) {
				totalValue = totalValue.add(value);
			}
		}
		this.txtNewCanRefundment.setValue(totalValue);
		BigDecimal totalActAmount = this.txtNewTolActAmount.getBigDecimalValue();
		if (totalActAmount == null) {
			totalActAmount = FDCHelper.ZERO;
		}
		this.txtNewFeeAmount.setValue(totalActAmount.subtract(totalValue));
	}
	
	protected void kDTable2_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		IRow row = this.kDTable2.getRow(rowIndex);
		if (row == null) {
			return;
		}

		reflushTotalCanRefundmentAmount();
	}
	
	private void verifyQuitPayList() {
		for (int i = 0; i < this.kDTable2.getRowCount(); i++) {
			IRow row = this.kDTable2.getRow(i);
			QuitPayListEntryInfo quitEntry = (QuitPayListEntryInfo) row.getUserObject();
			BigDecimal canRefundmentAmount = (BigDecimal) row.getCell("newCanRefundmentAmount").getValue();
			BigDecimal maxCanRefundAmount = quitEntry.getMaxCanRefundAmount();
			if (canRefundmentAmount != null && maxCanRefundAmount != null) {
				if (canRefundmentAmount.compareTo(FDCHelper.ZERO) < 0) {
					maxCanRefundAmount = maxCanRefundAmount.setScale(2);
					MsgBox.showInfo(this, "第" + (i + 1) + "行可退金额小于0！");
					this.abort();
				}

				if (canRefundmentAmount.compareTo(maxCanRefundAmount) > 0) {
					maxCanRefundAmount = maxCanRefundAmount.setScale(2);
					MsgBox.showInfo(this, "第" + (i + 1) + "行可退金额超出最大可退金额 " + maxCanRefundAmount + "！");
					this.abort();
				}
			}
		}
	}

	boolean loadData = false;
	public void loadFields() {
		loadData = true;
		super.loadFields();
		QuitRoomChangeInfo quitRoomChangeInfo = this.editData;
		this.prmtQuitRoom.setValue(quitRoomChangeInfo.getQuitRoom());		
		if(!this.getOprtState().equals("ADDNEW"))
		{
			this.prmtRoom.setValue(quitRoomChangeInfo.getRoom());
			this.txtQuitChangeReason.setText(quitRoomChangeInfo.getQuitChangeReason());
			this.f7OldFeeAccount.setValue(quitRoomChangeInfo.getOldFeeAccount());
			this.f7OldFeeMoneyDefine.setValue(quitRoomChangeInfo.getOldFeeMoneyDefine());
			this.txtOldFeeAmount.setValue(quitRoomChangeInfo.getOldFeeAmount());
			
			
			QuitOldPayListEntryCollection oldPayColl = quitRoomChangeInfo.getQuitOldPayList();
			if(oldPayColl.size()>0)
			{
				this.kDTable1.removeRows();
			}
			for(int i=0;i<oldPayColl.size();i++)
			{
				QuitOldPayListEntryInfo quitOldPayInfo = oldPayColl.get(i);
				QuitPayListEntryInfo payInfo = this.setOldEntryObject(quitOldPayInfo);
				IRow row = kDTable1.addRow();
				row.setUserObject(payInfo);
				row.getCell("moneyDefine").setValue(quitOldPayInfo.getMoneyDefine().getName());
				row.getCell("appAmount").setValue(quitOldPayInfo.getApAmount());
				row.getCell("actAmount").setValue(quitOldPayInfo.getActPayAmount());
				row.getCell("canRefundmentAmount").setValue(quitOldPayInfo.getCanRefundmentAmount());
			}
			loadCanRefundmentAmount2(oldPayColl);
			this.f7NewFeeMoneyDefine.setValue(quitRoomChangeInfo.getNewFeeMoneyDefine());
			this.f7NewFeeAccount.setValue(quitRoomChangeInfo.getNewFeeAccount());
			//setAccountByMoneyDefine(quitRoomChangeInfo.getNewFeeMoneyDefine());
			this.txtNewFeeAmount.setValue(quitRoomChangeInfo.getNewFeeAmount());
			
			QuitNewPayListEntryCollection newPayColl = quitRoomChangeInfo.getQuitNewPayList();
			if(newPayColl.size()>0)
			{
				this.kDTable2.removeRows();
			}
			for(int i=0;i<newPayColl.size();i++)
			{
				QuitNewPayListEntryInfo quitNewPayInfo = newPayColl.get(i);
				QuitPayListEntryInfo payInfo = this.setNewEntryObject(quitNewPayInfo);
				IRow row = kDTable2.addRow();
				row.setUserObject(payInfo);
				row.getCell("newMoneyDefine").setValue(quitNewPayInfo.getMoneyDefine().getName());
				row.getCell("newAppAmount").setValue(quitNewPayInfo.getApAmount());
				row.getCell("newActAmount").setValue(quitNewPayInfo.getActPayAmount());
				row.getCell("newCanRefundmentAmount").setValue(quitNewPayInfo.getCanRefundmentAmount());
			}
			loadNewCanRefundmentAmount2(newPayColl);
		}
		loadData = false;
	}
	
	protected void f7NewFeeMoneyDefine_dataChanged(DataChangeEvent e)
			throws Exception {
		MoneyDefineInfo moneyInfo = (MoneyDefineInfo)this.f7NewFeeMoneyDefine.getValue();
		setAccountByMoneyDefine(moneyInfo);
		super.f7NewFeeMoneyDefine_dataChanged(e);
	}
	
	protected void f7OldFeeMoneyDefine_dataChanged(DataChangeEvent e)
			throws Exception {
		MoneyDefineInfo moneyInfo = (MoneyDefineInfo)this.f7OldFeeMoneyDefine.getValue();
		setAccountByMoneyDefine2(moneyInfo);
		super.f7OldFeeMoneyDefine_dataChanged(e);
	}
	
	private QuitPayListEntryInfo setNewEntryObject(QuitNewPayListEntryInfo oldPayInfo)
	{
		QuitPayListEntryInfo payInfo = new QuitPayListEntryInfo();
		payInfo.setMoneyDefine(oldPayInfo.getMoneyDefine());
		payInfo.setActPayAmount(oldPayInfo.getActPayAmount());
		payInfo.setApAmount(oldPayInfo.getApAmount());
		payInfo.setCanRefundmentAmount(oldPayInfo.getCanRefundmentAmount());
    	if(oldPayInfo.getMaxCanRefundAmount()!=null)
    	{
    		payInfo.setMaxCanRefundAmount(oldPayInfo.getMaxCanRefundAmount());
    	}
    	if(oldPayInfo.getSeq()!=0)
    	{
    		payInfo.setSeq(oldPayInfo.getSeq());
    	}
    	if(oldPayInfo.getFeeFromType()!=null)
    	{
    		payInfo.setFeeFromType(oldPayInfo.getFeeFromType());
    	}
		return payInfo;
	}
	
	private QuitPayListEntryInfo setOldEntryObject(QuitOldPayListEntryInfo oldPayInfo)
	{
		QuitPayListEntryInfo payInfo = new QuitPayListEntryInfo();
		payInfo.setMoneyDefine(oldPayInfo.getMoneyDefine());
		payInfo.setActPayAmount(oldPayInfo.getActPayAmount());
		payInfo.setApAmount(oldPayInfo.getApAmount());
		payInfo.setCanRefundmentAmount(oldPayInfo.getCanRefundmentAmount());
    	if(oldPayInfo.getMaxCanRefundAmount()!=null)
    	{
    		payInfo.setMaxCanRefundAmount(oldPayInfo.getMaxCanRefundAmount());
    	}
    	if(oldPayInfo.getSeq()!=0)
    	{
    		payInfo.setSeq(oldPayInfo.getSeq());
    	}
    	if(oldPayInfo.getFeeFromType()!=null)
    	{
    		payInfo.setFeeFromType(oldPayInfo.getFeeFromType());
    	}
		return payInfo;
	}
	
    public void storeFields()
    {
        super.storeFields();
        QuitRoomChangeInfo quitRoomChangeInfo =this.editData;
        quitRoomChangeInfo.setName(this.txtNumber.getText());
        quitRoomChangeInfo.setQuitRoom((QuitRoomInfo)this.prmtQuitRoom.getValue());
        quitRoomChangeInfo.setRoom((RoomInfo)this.prmtRoom.getValue());
        quitRoomChangeInfo.setQuitChangeReason(this.txtQuitChangeReason.getText());
        quitRoomChangeInfo.setOldFeeAccount((AccountViewInfo)this.f7OldFeeAccount.getValue());
        quitRoomChangeInfo.setOldFeeMoneyDefine((MoneyDefineInfo)this.f7OldFeeMoneyDefine.getValue());
        quitRoomChangeInfo.setOldFeeAmount(this.txtOldFeeAmount.getBigDecimalValue());
        QuitOldPayListEntryCollection oldPayColl = new QuitOldPayListEntryCollection();
        QuitNewPayListEntryCollection newPayColl = new QuitNewPayListEntryCollection();
        for(int i=0;i<kDTable1.getRowCount();i++)
        {
        	IRow row = (IRow) kDTable1.getRow(i);
        	QuitPayListEntryInfo quitPay = (QuitPayListEntryInfo) row.getUserObject();
        	QuitOldPayListEntryInfo quitOldPay = new QuitOldPayListEntryInfo();      	
        	quitOldPay.setMoneyDefine(quitPay.getMoneyDefine());
        	quitOldPay.setActPayAmount(quitPay.getActPayAmount());
        	quitOldPay.setApAmount(quitPay.getApAmount());
        	quitOldPay.setCanRefundmentAmount(quitPay.getCanRefundmentAmount());
        	if(quitPay.getMaxCanRefundAmount()!=null)
        	{
        		quitOldPay.setMaxCanRefundAmount(quitPay.getMaxCanRefundAmount());
        	}
        	if(quitPay.getSeq()!=0)
        	{
        		quitOldPay.setSeq(quitPay.getSeq());
        	}else
        	{
        		quitOldPay.setSeq(0);
        	}
        	if(quitPay.getFeeFromType()!=null)
        	{
        		quitOldPay.setFeeFromType(quitPay.getFeeFromType());
        	}
			oldPayColl.add(quitOldPay);
        }
        quitRoomChangeInfo.getQuitOldPayList().clear();
        quitRoomChangeInfo.getQuitOldPayList().addCollection(oldPayColl);
        
        quitRoomChangeInfo.setNewFeeAccount((AccountViewInfo)this.f7NewFeeAccount.getValue());
        quitRoomChangeInfo.setNewFeeMoneyDefine((MoneyDefineInfo)this.f7NewFeeMoneyDefine.getValue());
        quitRoomChangeInfo.setNewFeeAmount(this.txtNewFeeAmount.getBigDecimalValue());
        for(int i=0;i<kDTable2.getRowCount();i++)
        {
        	IRow row = (IRow) kDTable2.getRow(i);
        	QuitPayListEntryInfo quitPay = (QuitPayListEntryInfo) row.getUserObject();
        	QuitNewPayListEntryInfo quitNewPay = new QuitNewPayListEntryInfo();
        	quitNewPay.setMoneyDefine(quitPay.getMoneyDefine());
        	quitNewPay.setActPayAmount(quitPay.getActPayAmount());
        	quitNewPay.setApAmount(quitPay.getApAmount());
        	quitNewPay.setCanRefundmentAmount((BigDecimal) row.getCell("newCanRefundmentAmount").getValue());
        	if(quitPay.getMaxCanRefundAmount()!=null)
        	{
        		quitNewPay.setMaxCanRefundAmount(quitPay.getMaxCanRefundAmount());
        	}
        	if(quitPay.getSeq()!=0)
        	{
        		quitNewPay.setSeq(quitPay.getSeq());
        	}else
        	{
        		quitNewPay.setSeq(0);
        	}
        	if(quitPay.getFeeFromType()!=null)
        	{
        		quitNewPay.setFeeFromType(quitPay.getFeeFromType());
        	}
        	newPayColl.add(quitNewPay);
        }
        quitRoomChangeInfo.getQuitNewPayList().clear();
        quitRoomChangeInfo.getQuitNewPayList().addCollection(newPayColl);
        
        quitRoomChangeInfo.setCreateTime(this.pkCreateTime.getTimestamp());
        
        
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	QuitRoomInfo quitRoom = this.editData.getQuitRoom();
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("*");
    	sels.add("quitEntrys.*");
    	quitRoom = QuitRoomFactory.getRemoteInstance().getQuitRoomInfo(new ObjectUuidPK(quitRoom.getId().toString()),sels);
    	if(quitRoom.getIsBlance()==1)
    	{
    		MsgBox.showInfo("该退房单做过退房结算，不能进行退房变更!");
			this.abort();
    	}
    	if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("编码必须录入！");
			return;
		}
		if (StringUtils.isEmpty(this.txtQuitChangeReason.getText())) {
			MsgBox.showInfo("变更原因必须录入！");
			return;
		}
		if(quitRoom.getQuitEntrys().size()==0)
		{
			if (this.f7OldFeeMoneyDefine.getValue() == null) {
				MsgBox.showInfo("原单据中的费用款项必须录入！");
				return;
			}
			if (this.f7OldFeeAccount.getValue() == null) {
				MsgBox.showInfo("原单据中的费用科目必须录入！");
				return;
			}
		}
		if (this.f7NewFeeMoneyDefine.getValue() == null) {
			MsgBox.showInfo("变更单据中的费用款项必须录入！");
			return;
		}
		if (this.f7NewFeeAccount.getValue() == null) {
			MsgBox.showInfo("变更单据中的费用科目必须录入！");
			return;
		}
		verifyQuitPayList();
    	super.actionSubmit_actionPerformed(e);
    	this.actionSubmit.setEnabled(false);
    	this.actionRemove.setEnabled(false);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionEdit_actionPerformed(e);
    	if (this.editData.getAuditor() != null) {
			MsgBox.showInfo("退房变更单已经审核不能修改!");
			return;
		}
    	this.prmtCreator.setEnabled(false);
		this.prmtAuditor.setEnabled(false);
		this.pkAuditTime.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		this.txtNumber.setEnabled(false);
		this.prmtQuitRoom.setEnabled(false);
		this.prmtRoom.setEnabled(false);
		this.txtOldTolActAmount.setEnabled(false);
		this.txtOldCanRefundment.setEnabled(false);
		this.txtOldFeeAmount.setEnabled(false);
		this.txtNewTolActAmount.setEnabled(false);
		this.txtNewCanRefundment.setEnabled(false);
		this.txtNewFeeAmount.setEnabled(false);
		this.f7OldFeeMoneyDefine.setEnabled(false);
		this.f7OldFeeAccount.setEnabled(false);
    	
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	if (this.editData.getAuditor() != null) {
			MsgBox.showInfo("退房变更单已经审核不能删除!");
			return;
		}
    	super.actionRemove_actionPerformed(e);
    }

	protected IObjectValue createNewData() {
		QuitRoomChangeInfo quitRoomChangeInfo = new QuitRoomChangeInfo();
		QuitRoomInfo quitRoomInfo = (QuitRoomInfo)this.getUIContext().get("quitRoomInfo");
		quitRoomChangeInfo.setQuitRoom(quitRoomInfo);
		this.prmtQuitRoom.setValue(quitRoomInfo);
		this.prmtQuitRoom.setEnabled(false);
		quitRoomChangeInfo.setCreator(userInfo);
		if(quitRoomInfo.getRoom()!=null)
		{
			this.prmtRoom.setValue(quitRoomInfo.getRoom());
			this.prmtRoom.setEnabled(false);
		}		
		Timestamp currentTime = SHEHelper.getCurrentTime();
		quitRoomChangeInfo.setCreateTime(currentTime);
		quitRoomChangeInfo.setAuditor(null);
		quitRoomChangeInfo.setAuditTime(null);
		this.prmtCreator.setValue(userInfo);
		this.prmtAuditor.setValue(null);
		this.prmtCreator.setEnabled(false);
		this.prmtAuditor.setEnabled(false);
		this.pkAuditTime.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		quitRoomChangeInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		quitRoomChangeInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		quitRoomChangeInfo.setBookedDate(new Date());
		return quitRoomChangeInfo;
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("room.name");
		sels.add("quitRoom.feeMoneyDefine.*");
		sels.add("quitRoom.feeAccount.*");
		sels.add("quitRoom.*");
		sels.add("quitOldPayList.*");
		sels.add("quitOldPayList.moneyDefine.name");
		sels.add("quitNewPayList.*");
		sels.add("quitNewPayList.moneyDefine.name");
		sels.add("oldFeeMoneyDefine.name");
		sels.add("newFeeMoneyDefine.name");
		sels.add("oldFeeAccount.name");
		sels.add("newFeeAccount.name");
		return sels;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return QuitRoomChangeFactory.getRemoteInstance();
	}

	protected void attachListeners() {		
	}

	protected void detachListeners() {
		
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

}