/**
 * 收款单，包含售楼的，租赁的，物业的
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.propertymgmt.DepositBillCollection;
import com.kingdee.eas.fdc.propertymgmt.DepositBillFactory;
import com.kingdee.eas.fdc.propertymgmt.DepositWithdrawBillCollection;
import com.kingdee.eas.fdc.propertymgmt.DepositWithdrawBillFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillRecordCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillRecordInfo;
import com.kingdee.eas.fdc.sellhouse.GatheringEnum;
import com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.ISHEPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastCollection;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastFactory;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayQuomodoEnum;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RecordTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.tenancy.SincerObligateInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IReceivingBill;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.fi.cas.client.CasReceivingBillListUI;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IObjectBase;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;


public class ReceiveBillEidtUI extends AbstractReceiveBillEidtUI
{
	

	private static final long serialVersionUID = -2007340879665818577L;

	private static final Logger logger = CoreUIObject.getLogger(ReceiveBillEidtUI.class);

	/** 收款对象(新)KEY */
	public static final String KEY_GATHERING = "gathering";
	/** 诚意认购KEY */
	public static final String KEY_SIN_PURCHASE = "sinPurchase";
	
	/** 付款计划里面的序号 */
	protected int seq = 0;
	
	/** 点击所属系统次数 */
	protected int clickComboSysNum = 0;
	
	protected BuildingInfo buildingInfo = null;
	protected BuildingUnitInfo buildUnit = null;
	protected SellProjectInfo sellProjectInfo = null;
	
	//--- 以下4项为物业系统选择付款明细后返回的集合的key ---
	public static final String Key_generalColl = "generalColl";
	public static final String Key_measureColl = "measureColl";
	public static final String Key_temporaryColl = "temporaryColl";
	public static final String Key_depositBillColl = "depositBillColl";
    //-----
	
	/**
     * 所属系统
     */
	public static final String KEY_MONEYTSYSTYPE = "moneySysTypeEnum";
	
	/** 合同参数 供租赁合同调用收款单使用 */
	public static final String KEY_TENANCYBILLID ="tenancyBillId";
	
	/** 房间参数    供租赁合同调用收款单使用 */
	public static final String KEY_ROOMID = "roomId";
	
	/** 收款单类型 在上下文此参数中传  ReceiveBillTypeEnum 相应的枚举过来 */
	public static final String KEY_BILLTYPE = "billTypeEnum";
	
	Map timeMap = new HashMap();

	/**
	 * @deprecated
	 * 当前收款对象
	 */
	protected GatheringObjectEnum gatheringObject = null;
	
	/**
	 * 收款对象   新的
	 */
	protected GatheringEnum gatheringEnum = null;
	/**
	 * 应收金额
	 */
	private BigDecimal appAmount = FDCHelper.ZERO;
	/**
	 * 实收金额
	 */
	protected BigDecimal actAmount = FDCHelper.ZERO;

	/**
	 * 系统客户id
	 */
	public static final String Key_SysCustomerId = "sysCustomerId";
	
	public static final String Key_SysCustomer = "sysCustomer";
	
	/**
	 * 传入付款明细选择界面 的 房间 TODO 
	 */
	public static final String Key_Room = "room";
	
	/** 传入付款明细选择界面的配套资源 */
	public static final String KEY_TEN_ATTACH = "tenAttach";
	
	/**
	 * 由付款明细选择界面传过来的参数
	 */
	public static final String KEY_PurchasePayListColl = "purchasePayListColl";
	
	/**
	 * 付款明细选择界面传过来的 特殊 款项
	 */
	public static final String Key_SpecifyPayListColl = "specifyPayListColl";
	
	public static final String Key_WithdrawColl = "withdrawColl";
	/**
	 * 付款明细选择界面传过来的 其他付款明细
	 */
	public static final String KEY_PurchaseElsePayListColl = "purchaseElsePayListColl";
	/**
	 * 付款明细选择界面传过来的 诚意认购付款明细
	 * */
	public static final String KEY_SinPurchasePayListColl = "sinPurchasePayListColl";
	
	/**
	 * 付款明细选择界面传过来的 租赁款项明细
	 */
	public static final String KEY_TenEntryPayColl = "tenRoomEntryPayColl";
	
	/**
	 * 付款明细界面选择按钮
	 */
	public static final String KEY_OPT = "YesOrNo";
	
	/**
	 * 是否是红冲收款，从付款明细选择界面传过来的参数
	 */
	public static final String Key_IsSettlement ="isSettlement";
	
	public static final String KEY_SettlementType = "settlementType";
	/**
	 * 红冲金额
	 */
	public static final String KEY_CounteractAmount = "counteractAmount";
	
	public static final String KEY_CounteractAmount_FOR_SHE = "counteractAmountForSHE";
	
	public static final String KEY_HongChongRecEntryColl = "hongChongRecEntryColl";
	/**
	 * 与付款选择明细界面交互的租赁合同
	 */
	public static final String KEY_TenancyBill = "tenancyBill";
	
	/**
	 * 是否是红冲收款
	 */
	public boolean isSettlement = false;
	
	/** 红冲单的 付款明细 */
	protected FDCReceiveBillEntryCollection hongChongFdcRecEntryColl;
	
	protected ReceiveBillTypeEnum recBillType;
	
	protected MarketDisplaySetting marketSetting = new MarketDisplaySetting();
	
    /** 当前激活的行 */
	protected int currentActiveRowIndex = 0;
	/**
	 * 修改的时候，存放旧的收款值
	 */
	Map oldGatheringAmountMap = new HashMap();
	/**
	 * 修改的时候，存放旧的退款值
	 */
	Map oldRefundmentAmountMap = new HashMap();
	
	//add by jiyu_guan
	String oldReceiptNum;
	
	public ReceiveBillEidtUI() throws Exception
	{
		super();
	}
	
	protected boolean isLoadField = false;
	
	
	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.SalehouseSys; //默认售楼
	}
	
	protected void kDTable1_editStopped(KDTEditEvent e) throws Exception {
		if (this.kDTable1.getRowCount() < 1) {
			return;
		}
		super.kDTable1_editStopped(e);
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if (rowIndex < 0) {
			return;
		}
		if (colIndex < 0) {
			return;
		}
		IRow row = this.kDTable1.getRow(rowIndex);
		if (colIndex == row.getCell("gatheringAccount").getColumnIndex()) {
			if(row.getCell("gatheringAccount").getValue() instanceof AccountBankInfo){
				AccountBankInfo info = (AccountBankInfo)row.getCell("gatheringAccount").getValue();
				if(info!=null){
					row.getCell("gatheringNumber").setValue(info.getBankAccountNumber());
				}
			}			
		}	
		else{return;}
	}
	
	public void loadFields()
	{
		isLoadField = true;
		super.loadFields();
//		this.setNumberEnabled();
		ReceivingBillInfo recBillInfo = (ReceivingBillInfo) this.editData;
		FDCReceiveBillInfo fdcRecBillInfo = recBillInfo.getFdcReceiveBill();
		this.seq = fdcRecBillInfo.getSeq();
		this.recBillType = fdcRecBillInfo.getBillTypeEnum();
		this.gatheringEnum = fdcRecBillInfo.getGathering();
		
		
		this.updateUITitle(this.recBillType);

		if (this.recBillType != null) {
			this.getUIContext().put(KEY_BILLTYPE, this.recBillType);
		}
		if (OprtState.EDIT == this.getOprtState()) {
			this.ComboGatheringOjbect.setEnabled(false);
			this.comboBelongSys.setEnabled(false);
			this.comboGathering.setEnabled(false);
		}
		
		this.txtNumber.setText(recBillInfo.getNumber());
		this.pkBillDate.setValue(recBillInfo.getBizDate());
		this.f7SellProject.setValue(fdcRecBillInfo.getSellProject());
		this.ComboGatheringOjbect.setSelectedItem(fdcRecBillInfo.getGatheringObject());
		this.comboGathering.setSelectedItem(fdcRecBillInfo.getGathering());
		
		this.f7Room.setValue(fdcRecBillInfo.getRoom());
		this.f7Accessorial.setValue(fdcRecBillInfo.getTenAttach());
		
		this.f7Purchase.setValue(fdcRecBillInfo.getPurchase());
		this.f7TenancyContract.setValue(fdcRecBillInfo.getTenancyContract());
		
		this.f7Currency.setValue(recBillInfo.getCurrency());
		this.f7SinPurchase.setValue(fdcRecBillInfo.getSinPurchase());
		this.f7SinObligate.setValue(fdcRecBillInfo.getSinObligate());
		
		this.txtDes.setText(recBillInfo.getDescription());
		this.txtReceiptNumber.setText(fdcRecBillInfo.getReceiptNumber());
		this.f7Cheque.setValue(fdcRecBillInfo.getCheque());
		this.pkCreateTime.setValue(recBillInfo.getCreateTime());
		
		if (fdcRecBillInfo.getInvoice() != null) {
			this.txtInvoice.setText(fdcRecBillInfo.getInvoice().getNumber());
			this.txtInvoiceAmount.setValue(fdcRecBillInfo.getInvoice().getAmount());
		}

		if (recBillInfo.getBillStatus() != null && !recBillInfo.getBillStatus().equals(BillStatusEnum.SAVE)) {
			this.actionSave.setEnabled(false);
		}
		
		this.f7Creator.setValue(recBillInfo.getCreator());
		this.pkAuditTime.setValue(recBillInfo.getAuditDate());
		this.f7Auditor.setValue(recBillInfo.getAuditor());
		
		//设置所属系统，如果所属系统为空，则为原来售楼的旧数据察看
		MoneySysTypeEnum moneySys = fdcRecBillInfo.getMoneySysType();
		if(moneySys == null){
			moneySys = MoneySysTypeEnum.SalehouseSys;
		}

		this.comboBelongSys.setSelectedItem(moneySys);
		//根据所属系统设置收款对象的可选项
		setGatheringItemsBySys(getSystemType());
		
		if (recBillInfo.getAmount() != null) {
			BigDecimal temp = recBillInfo.getAmount();
			if (temp.compareTo(FDCHelper.ZERO) < 0) {
				//增加红冲后，这里可能需要修改 TODO
				if (ReceiveBillTypeEnum.refundment.equals(this.recBillType) || ReceiveBillTypeEnum.settlement.equals(this.recBillType)) {
					this.txtReceiveAmount.setValue(FDCHelper.ZERO.subtract(temp));
				}
			} else {
				this.txtReceiveAmount.setValue(temp);
			}
			
			// 退款不让选票据
			if (ReceiveBillTypeEnum.refundment.equals(fdcRecBillInfo.getBillTypeEnum())) {
				this.f7Purchase.setEnabled(false);
				this.f7Room.setEnabled(false);
				this.f7Cheque.setEnabled(false);
				this.comboBelongSys.setEnabled(false);
				this.ComboGatheringOjbect.setEnabled(false);
			}
			
			if (this.getOprtState().equals(OprtState.EDIT)) {
				if (fdcRecBillInfo.getCheque() != null) {
					if (marketSetting.getMarkInvoice() == 32) {
						txtReceiveAmount.setEditable(false);
						f7Cheque.setEnabled(false);
					}
				}
			}
		}

		this.loadFieldsForSys(recBillInfo,fdcRecBillInfo);
		
		
		// 防止从消控那边过来，先调 f7PurchaseDataChanged,后掉loadFileds,导致冲掉 认购单 取得的客户
		if (OprtState.ADDNEW != this.getOprtState()) {
			CustomerEntryCollection customerEntryColl = fdcRecBillInfo.getCustomerEntrys();
			if (customerEntryColl != null) {
				Object[] o = new Object[customerEntryColl.size()];
				for (int i = 0; i < customerEntryColl.size(); i++) {
					CustomerEntryInfo customerEntryInfo = customerEntryColl.get(i);
					if (customerEntryInfo != null)
						o[i] = customerEntryInfo.getCustomer();
				}
				this.f7Customer.setValue(o);
			}
		}
		
		// add by jiyu_guan
		loadFeildsForRecord();
		oldReceiptNum = fdcRecBillInfo.getReceiptNumber();
		
		isLoadField = false;
	}

	protected void loadFieldsForSys(ReceivingBillInfo receivingBillInfo,FDCReceiveBillInfo fdcReceiveBillInfo)
	{
		
	}	
	

	protected void setAuditButtonStatus(String oprtType)
	{
		// super.setAuditButtonStatus(oprtType);
	}

	/**
	 * 修改按钮
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) this.editData;
		FDCReceiveBillInfo fdcRev = receivingBillInfo.getFdcReceiveBill();
		if(fdcRev != null && getSystemType().equals(MoneySysTypeEnum.SalehouseSys)){  //只是针对售楼系统才需要
			SellProjectInfo sellProject = fdcRev.getSellProject();
			if(sellProject != null){
				SHEHelper.verifyBalance(receivingBillInfo.getBizDate(), sellProject.getBalanceEndDate());
			}
		}
		
		String billId = this.editData.getId().toString();
		
		if(FDCUtils.isRunningWorkflow(billId)){
			MsgBox.showInfo("这条单据正在工作流中，不能修改！");
			return;
		}
		
		String temp[] = SHEHelper.isReceiveBillCanRemoveOrEdit(billId);
		if("false".equalsIgnoreCase(temp[0]))
		{
			if(temp.length == 4){
				MsgBox.showInfo(temp[2]);
				this.abort();
			}
			
			if(temp.length > 2 && temp[2] != null)
			{
				MsgBox.showInfo(temp[2]);
			}
			else
			{
				MsgBox.showInfo("该单据不能修改金额！");
			}
			
			super.actionEdit_actionPerformed(e);
			
			this.btnDelete.setEnabled(false);
			
			int count = this.kDTable1.getRowCount();
			if(count > 0)
			{
				for(int i = 0; i < count; i ++)
				{
					IRow row = this.kDTable1.getRow(i);
					row.getCell("gatheringAmount").getStyleAttributes().setLocked(true);
				}
			}
		}
		else
		{
			super.actionEdit_actionPerformed(e);
		}
		
		this.f7Room.setEnabled(false);
		this.comboBelongSys.setEnabled(false);
		this.comboGathering.setEnabled(false);
		
		this.f7Purchase.setEnabled(false);
		this.actionSubmit.setEnabled(true);
		this.actionAudit.setEnabled(false);
		this.actionRec.setEnabled(true);
		this.actionVoucher.setEnabled(true);
		this.f7TenancyContract.setEnabled(false);
		this.ComboGatheringOjbect.setEnabled(false);
		this.txtNumber.setEnabled(false);
		this.btnChoose.setEnabled(false);
		this.pkBillDate.setEnabled(false);
		
		this.kDTable1.getColumn("moneyType").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("settlementType").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("appAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("actAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setLocked(true);

		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		if (fdcReceiveBillInfo.getCheque() != null)
		{
			if (marketSetting.getMarkInvoice() == 32)
			{
				txtReceiveAmount.setEditable(false);
				f7Cheque.setEnabled(false);
			}
		}
		
		
		saveTheOldValueOfRev();
	}

	
	//将修改前的值存放起来   -- 在编辑状态打开和修改时触发	
	private void saveTheOldValueOfRev(){
		this.oldGatheringAmountMap.clear();
		this.oldRefundmentAmountMap.clear();			
		for(int i = 0 ; i < this.kDTable1.getRowCount(); i ++)	{
			IRow row = this.kDTable1.getRow(i);			
			String id = (String)row.getCell("id").getValue();
			if(id!=null) {
				this.oldGatheringAmountMap.put(id, this.getBigDecimalCellValue(row, "gatheringAmount"));
				this.oldRefundmentAmountMap.put(id, this.getBigDecimalCellValue(row, "refundmentAmount"));
			}
		}
	}
	
	
	
	protected void checkBeforeEditOrRemove(String warning)
	{
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) this.editData;
		BillStatusEnum state = receivingBillInfo.getBillStatus();
		if (state != null && (state == BillStatusEnum.AUDITING || state == BillStatusEnum.AUDITED))
		{
			MsgBox.showWarning(this, "已审核,不能修改!");
			SysUtil.abort();
		}
	}

	protected void setSaveActionStatus()
	{
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) this.editData;
		BillStatusEnum state = receivingBillInfo.getBillStatus();
		if (!state.equals(BillStatusEnum.SAVE))
		{
			actionSave.setEnabled(false);
		}
	}
	

	

	

	

	
	
	/**
	 * 将售楼的几个特定款项添加到表格当中
	 * @param counteractMap 
	 * @throws BOSException 
	 */
	private void addRowBySpecifyPayListCollInSHE(Collection coll,Map settlementMap,PurchaseInfo  purchase, Map counteractMap) throws BOSException
	{
		Iterator it = coll.iterator();
		while(it.hasNext())
		{
			Map map = (HashMap)it.next();
			String id = map.get("id").toString();
			MoneyDefineInfo money = (MoneyDefineInfo) map.get("moneyName");
			
			IRow row = this.kDTable1.addRow();
			
			row.getCell("id").setValue(id);
			row.getCell("moneyType").setValue(money);
			
			setAccountByMoneyDefine(money, row);
			
			BigDecimal actAmount = FDCHelper.ZERO;
			
			if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
			{
				actAmount = getActAmountByCustomerInSHE(money, this.getCustomerFromView());
			}
			else
			{
				actAmount = this.getActAmountByMoneyInSHE(money,purchase);
			}
			
			BigDecimal appAmount = this.getAppAmountByMoneyInSHE(money, purchase);
			
			row.getCell("appAmount").setValue(appAmount);
			
			if(actAmount != null)
				actAmount = actAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			row.getCell("actAmount").setValue(actAmount);
			
			BigDecimal canCounteractAmount = FDCHelper.ZERO;
			
			if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
			{
			canCounteractAmount = this.getCanCounteractAmount(money, getCustomerFromView());	
			}
			else
				{
				canCounteractAmount = this.getCanCounteractAmount(money, purchase);
				}
			
			
			
			
			
			
			row.getCell("canRefundmentAmount").setValue(canCounteractAmount);
			
			BigDecimal hasRefundmentAmount = FDCHelper.ZERO;
			
			if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
			{
				hasRefundmentAmount = this.getHasRefundmentAmountInSHE(money, this.getCustomerFromView());
			}
			else
			{
				hasRefundmentAmount = this.getHasRefundmentAmountInSHE(money, purchase);
			}
			if(hasRefundmentAmount != null)
				hasRefundmentAmount = hasRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			row.getCell("hasRefundmentAmount").setValue(hasRefundmentAmount);

			if(settlementMap.get(id) != null)
			{
				Object[] o =  (Object[]) settlementMap.get(id);
				
				if(o != null)
				{
					for(int j = 0; j < o.length; j ++)
					{
						if(j == 0)
						{
							row.getCell("settlementType").setValue(o[j]);
							
							setGatheringAmountOfCounteract(id, row, (SettlementTypeInfo)o[j], counteractMap);
						}
						else
						{
							IRow tempRow = this.kDTable1.addRow();
							
							tempRow.getCell("id").setValue(id);
							tempRow.getCell("moneyType").setValue(money);
							tempRow.getCell("settlementType").setValue(o[j]);
							//根据款项自动带出科目
							setAccountByMoneyDefine(money, tempRow);
							
							setGatheringAmountOfCounteract(id, tempRow, (SettlementTypeInfo)o[j], counteractMap);
						}
					}
					//融合单元格
					this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(),0,row.getRowIndex() + o.length - 1,0);
					
					this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(),row.getCell("appAmount").getColumnIndex(),row.getRowIndex() + o.length - 1,row.getCell("appAmount").getColumnIndex());
				
					this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(),row.getCell("actAmount").getColumnIndex(),row.getRowIndex() + o.length - 1,row.getCell("actAmount").getColumnIndex());
					
					this.kDTable1.getMergeManager().mergeBlock(
							row.getRowIndex(),
							row.getCell("canRefundmentAmount").getColumnIndex(),
							row.getRowIndex() + o.length - 1,
							row.getCell("canRefundmentAmount").getColumnIndex());
					
					this.kDTable1.getMergeManager().mergeBlock(
							row.getRowIndex(),
							row.getCell("hasRefundmentAmount").getColumnIndex(),
							row.getRowIndex() + o.length - 1,
							row.getCell("hasRefundmentAmount").getColumnIndex());
				}
			}
		}
	}
	

	
	
	public SelectorItemCollection getSelectors()
	{
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("recBillType.*");
		sels.add("creator.id");
		sels.add("creator.name");
		sels.add("creator.number");			
		//sels.add("auditor.*");
		sels.add("auditor.id");
		sels.add("auditor.name");
		sels.add("auditor.number");		
		//sels.add("currency.*");		
		sels.add("currency.id");
		sels.add("currency.name");
		sels.add("currency.number");		
		sels.add("fdcReceiveBillEntry.*");
		sels.add("fdcReceiveBillEntry.moneyDefine.*");
//		sels.add("fdcReceiveBillEntry.account.*");
		sels.add("fdcReceiveBillEntry.account.id");
		sels.add("fdcReceiveBillEntry.account.name");
		sels.add("fdcReceiveBillEntry.account.number");		
//		sels.add("fdcReceiveBillEntry.revAccountBank.*");
		sels.add("fdcReceiveBillEntry.revAccountBank.id");
		sels.add("fdcReceiveBillEntry.revAccountBank.name");
		sels.add("fdcReceiveBillEntry.revAccountBank.number");		
		sels.add("fdcReceiveBillEntry.revAccountBank.bankAccountNumber");		
//		sels.add("fdcReceiveBillEntry.revBank.*");
		sels.add("fdcReceiveBillEntry.revBank.id");
		sels.add("fdcReceiveBillEntry.revBank.name");
		sels.add("fdcReceiveBillEntry.revBank.number");			
//		sels.add("fdcReceiveBillEntry.settlementType.*");
		sels.add("fdcReceiveBillEntry.settlementType.id");
		sels.add("fdcReceiveBillEntry.settlementType.name");
		sels.add("fdcReceiveBillEntry.settlementType.number");		
//		sels.add("fdcReceiveBillEntry.oppSubject.*");
		sels.add("fdcReceiveBillEntry.oppSubject.id");
		sels.add("fdcReceiveBillEntry.oppSubject.name");
		sels.add("fdcReceiveBillEntry.oppSubject.number");		
		//sels.add("oppAccount.*");
		sels.add("oppAccount.id");
		sels.add("oppAccount.name");
		sels.add("oppAccount.number");		
		//sels.add("project.*");
		sels.add("project.id");
		sels.add("project.name");
		sels.add("project.number");		
//		sels.add("payeeBank.*");
		sels.add("payeeBank.id");
		sels.add("payeeBank.name");
		sels.add("payeeBank.number");		
//		sels.add("payeeAccountBank.*");
		sels.add("payeeAccountBank.id");
		sels.add("payeeAccountBank.name");
		sels.add("payeeAccountBank.number");	
		//sels.add("payeeAccount.*");
		sels.add("payeeAccount.id");
		sels.add("payeeAccount.name");
		sels.add("payeeAccount.number");	
//		sels.add("settlementType.*");
		sels.add("settlementType.id");
		sels.add("settlementType.name");
		sels.add("settlementType.number");		
		sels.add("fdcReceiveBill.*");	
		//sels.add("fdcReceiveBill.invoice.*");
		sels.add("fdcReceiveBill.invoice.id");
		sels.add("fdcReceiveBill.invoice.name");
		sels.add("fdcReceiveBill.invoice.number");		
		//sels.add("fdcReceiveBill.cheque.*");
		sels.add("fdcReceiveBill.cheque.id");
		sels.add("fdcReceiveBill.cheque.name");
		sels.add("fdcReceiveBill.cheque.number");		
		sels.add("fdcReceiveBill.moneyDefine.*");
		//sels.add("fdcReceiveBill.moneyDefine.revBillType.*");
		sels.add("fdcReceiveBill.moneyDefine.revBillType.id");
		sels.add("fdcReceiveBill.moneyDefine.revBillType.name");
		sels.add("fdcReceiveBill.moneyDefine.revBillType.number");		
		//sels.add("fdcReceiveBill.room.*");
		sels.add("fdcReceiveBill.room.id");
		sels.add("fdcReceiveBill.room.name");
		sels.add("fdcReceiveBill.room.number");	
		//sels.add("fdcReceiveBill.room.building.*");
		sels.add("fdcReceiveBill.room.building.id");
		sels.add("fdcReceiveBill.room.building.name");
		sels.add("fdcReceiveBill.room.building.number");		
//		sels.add("fdcReceiveBill.room.building.sellProject.*");
		sels.add("fdcReceiveBill.room.building.sellProject.id");
		sels.add("fdcReceiveBill.room.building.sellProject.name");
		sels.add("fdcReceiveBill.room.building.sellProject.number");		
//		sels.add("fdcReceiveBill.room.sellOrder.*");
		sels.add("fdcReceiveBill.room.sellOrder.id");
		sels.add("fdcReceiveBill.room.sellOrder.name");
		sels.add("fdcReceiveBill.room.sellOrder.number");		
//		sels.add("fdcReceiveBill.sellProject.*");
		sels.add("fdcReceiveBill.sellProject.id");
		sels.add("fdcReceiveBill.sellProject.name");
		sels.add("fdcReceiveBill.sellProject.number");		
		//sels.add("fdcReceiveBill.sellProject.project.*");
		sels.add("fdcReceiveBill.sellProject.project.id");
		sels.add("fdcReceiveBill.sellProject.project.name");
		sels.add("fdcReceiveBill.sellProject.project.number");		
//		sels.add("fdcReceiveBill.customerEntrys.customer.*");
		sels.add("fdcReceiveBill.customerEntrys.customer.id");
		sels.add("fdcReceiveBill.customerEntrys.customer.name");
		sels.add("fdcReceiveBill.customerEntrys.customer.number");		
		sels.add("fdcReceiveBill.tenancyContract.*");		
		//sels.add("fdcReceiveBill.asstActType.*");
		sels.add("fdcReceiveBill.asstActType.id");
		sels.add("fdcReceiveBill.asstActType.name");
		sels.add("fdcReceiveBill.asstActType.number");		
		// add by jiyu_guan
		sels.add("fdcReceiveBill.invoice.amount");
		return sels;
	}

	/**
	 * 重写取值方法
	 * 只是为了能够取得认购单的相关属性，觉得好像没有必要这样写
	 */
	protected IObjectValue getValue(IObjectPK pk) throws Exception
	{
		ReceivingBillInfo rev = (ReceivingBillInfo) super.getValue(pk);
		if (rev.getFdcReceiveBill().getPurchase() != null) {
			PurchaseInfo pur = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(rev.getFdcReceiveBill().getPurchase().getId()),this.getPurchaseSelectorItemColl());
			rev.getFdcReceiveBill().setPurchase(pur);
		}
		
		if(rev.getFdcReceiveBill().getSinPurchase() != null){
			SincerityPurchaseInfo sinPur = getSinPurchase(rev.getFdcReceiveBill().getSinPurchase().getId().toString());
			rev.getFdcReceiveBill().setSinPurchase(sinPur);
		}
		return rev;
	}

	/**
	 * 设置票据的过滤条件,专供onload事件中调用
	 */
	private void setF7ChequeFilterAsOnload()
	{
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("keeper.id", SysContext.getSysContext().getCurrentUserInfo().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("status", new Integer(ChequeStatusEnum.BOOKED_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("chequeType", ChequeTypeEnum.RECEIPT_VALUE));
		viewInfo.setFilter(filter);
		viewInfo.getSorter().add(new SorterItemInfo("number"));
		this.f7Cheque.setEntityViewInfo(viewInfo);
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("applyRec.*");
		this.f7Cheque.setSelectorCollection(sels);
	}
	/**
	 * 在onload事件中设值一些初始值
	 */
	private void setInitValueAsOnload()
	{
		 buildingInfo = (BuildingInfo) this.getUIContext().get("building");
		 buildUnit = (BuildingUnitInfo) this.getUIContext().get("unit");
		 sellProjectInfo = (SellProjectInfo)this.getUIContext().get("sellProjectInfo");
	}
	
	public void onLoad() throws Exception
	{
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuBiz);
		
		ItemAction[] hideAction = new ItemAction[]{this.actionAddLine,this.actionCopy,this.actionCopyFrom,this.actionCreateFrom,
				this.actionCreateTo,this.actionFirst,this.actionNext,this.actionRemoveLine,this.actionNextPerson,this.actionAuditResult,
				this.actionMultiapprove,this.actionAttachment,this.actionDelVoucher,this.actionLast,this.actionPre,this.actionSave,
				this.actionTraceDown,this.actionTraceUp,this.actionWorkFlowG,this.actionUnAudit,this.actionAddNew,this.actionRemove,
				this.actionInsertLine};
       FDCClientHelper.setActionVisible(hideAction,false);
		
       if(this.getUIContext().get("billId")!=null)
		{
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("room.*");
			sels.add("room.building.*");
			sels.add("room.building.sellProject.*");
			PurchaseInfo purInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK((BOSUuid)this.getUIContext().get("billId")),sels);
			this.f7Room.setValue(purInfo.getRoom());
		}
		this.setInitValueAsOnload();
		
		this.initControl();
		this.initEntryTableColumnEditor();
		
		this.loadEntryTableData();//TODO 这个应该放到loadField函数中去调呀
		
		this.setMoneySysValueAsOnload(getSystemType());
		this.setGatheringObjectAsOnload(this.gatheringObject);

		//以下是一些F7的过滤设置等信息
		this.setF7ChequeFilterAsOnload();
		this.setF7PurchaseSelectorItemCollAsOnload();
		this.initOldData(this.editData);
		
		this.f7Accessorial.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e){
				setF7TenAttachFilter();
			}
		});
		
		if(this.getUIContext().get("isEdit") instanceof Boolean)
		{
			Boolean debug = (Boolean)this.getUIContext().get("isEdit");
			if (debug.booleanValue())
			{
				this.getUIContext().put("isEdit", Boolean.FALSE);
				ActionEvent e = this.actionEdit.getActionEvent();
				this.actionEdit_actionPerformed(e);
			}
		}
		
		setControlByGathering((GatheringEnum) this.comboGathering.getSelectedItem());
		
		//当不是新增状态时，发票号不可修改
		if(STATUS_ADDNEW.equals(getOprtState())){
			f7Cheque.setEnabled(true);
			txtReceiptNumber.setEnabled(true);
		}else{
			f7Cheque.setEnabled(false);
			txtReceiptNumber.setEnabled(false);
		}
		
		
		if(this.getOprtState().equals(OprtState.EDIT))
			saveTheOldValueOfRev();
		
		this.kDTable1.getColumn("gatheringNumber").getStyleAttributes().setLocked(true);
		
	}

	private void setF7TenAttachFilter() {
		TenancyBillInfo tenancy = (TenancyBillInfo) this.f7TenancyContract.getValue();
		
		this.f7Accessorial.getQueryAgent().setDefaultFilterInfo(null);
		this.f7Accessorial.getQueryAgent().setHasCUDefaultFilter(false);
		this.f7Accessorial.getQueryAgent().resetRuntimeEntityView();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(tenancy == null){
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id", tenancy.getId().toString(), CompareType.EQUALS));
		}
		view.setFilter(filter);
		this.f7Accessorial.setEntityViewInfo(view);
	}
	
	protected void fetchInitData()
	{
	}





	


	/**
	 * 初始化一些界面等信息
	 */
	private void initControl()
	{
		this.kDLabelContainer2.setVisible(false);
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) this.editData;
		if (getUIContext().get(UIContext.OWNER) instanceof CasReceivingBillListUI)
		{
			if (this.getOprtState().equals("EDIT"))
			{
				MsgBox.showInfo("出纳系统不能修改来自售楼系统的收款单!");
				this.abort();
			} else
			{
				this.actionEdit.setVisible(false);
			}
		}
		if(!BillStatusEnum.SAVE.equals(receivingBillInfo.getBillStatus()) && !BillStatusEnum.SUBMIT.equals(receivingBillInfo.getBillStatus())
				&& !BillStatusEnum.AUDITING.equals(receivingBillInfo.getBillStatus()) )
		{
			this.actionEdit.setEnabled(false);
		}
		if(BillStatusEnum.AUDITED.equals(receivingBillInfo.getBillStatus()))
		{
			this.actionAudit.setEnabled(false);
		}
		else if(BillStatusEnum.SUBMIT.equals(receivingBillInfo.getBillStatus()) && OprtState.VIEW.equalsIgnoreCase(this.getOprtState()))
		{
			this.actionAudit.setEnabled(true);
		}
		if(BillStatusEnum.RECED.equals(receivingBillInfo.getBillStatus()))
		{
			this.actionRec.setEnabled(false);
		}
		
		CompanyOrgUnitInfo companyOrgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
		if (companyOrgUnitInfo.getAccountTable() == null)
		{
			MsgBox.showInfo("当前财务组织未设置科目表.");
			this.abort();
		}
		if (marketSetting.getMarkInvoice() == 32)
		{
			this.txtReceiptNumber.setVisible(false);
		} else
		{
			this.f7Cheque.setVisible(false);
		}
		this.actionUnAudit.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAddNew.setEnabled(false);
		
		this.actionCopy.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		
		SHEHelper.setTextFormat(this.txtReceiveAmount);
		this.txtReceiveAmount.setNegatived(true);
		
		this.txtNumber.setRequired(true);
		this.f7Purchase.setRequired(true);
		this.pkBillDate.setRequired(true);
		this.f7Customer.setRequired(true);
		this.txtReceiveAmount.setRequired(true);
	
		this.f7Room.setEditable(false);
		this.f7Salesman.setEnabled(false);
		this.f7SellOrder.setEnabled(false);
		this.f7Currency.setEnabled(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.f7TenancyContract.setEnabled(false);
		
		this.btnDelete.setVisible(false);
		
		if (this.getOprtState().equals("VIEW"))
		{
			this.txtNumber.setEnabled(false);
		}
		if(ReceiveBillTypeEnum.refundment.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			this.setUITitle(new String("退款单"));
			this.contReceiveAmount.setBoundLabelText("退款金额");
		}
		else if(ReceiveBillTypeEnum.settlement.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			this.setUITitle(new String("结转单"));
		}
		else
		{
			this.setUITitle(new String("收款单"));
		}
		if(!this.getOprtState().equalsIgnoreCase("ADDNEW"))
		{
			this.txtNumber.setEnabled(false);
		}
	}

	public void storeFields()
	{
		super.storeFields();
		
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) this.editData;

		receivingBillInfo.setBizDate((Date) this.pkBillDate.getValue());
		receivingBillInfo.setNumber(this.txtNumber.getText());
		
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		
		//针对物业，红冲收款时的2张收款单都是 结转收款.
		//后来收款单类型修改为红冲收款和转款后,对于售楼系统的红冲收款,正数的收款类型为转款.物业的保持原有逻辑不变
		if(MoneySysTypeEnum.SalehouseSys.equals(getSystemType()) && isSettlement){
			fdcReceiveBillInfo.setBillTypeEnum(ReceiveBillTypeEnum.transfer);
		}else{
			if(this.getUIContext().get(KEY_BILLTYPE) != null)
			{
				fdcReceiveBillInfo.setBillTypeEnum((ReceiveBillTypeEnum) this.getUIContext().get(KEY_BILLTYPE));
			}	
		}
		
		fdcReceiveBillInfo.setMoneySysType((MoneySysTypeEnum) this.comboBelongSys.getSelectedItem());
		fdcReceiveBillInfo.setSellProject((SellProjectInfo) this.f7SellProject.getValue());
		fdcReceiveBillInfo.setRoom((RoomInfo) this.f7Room.getValue());
		fdcReceiveBillInfo.setTenAttach((TenAttachResourceEntryInfo) this.f7Accessorial.getValue());
		fdcReceiveBillInfo.setGatheringObject((GatheringObjectEnum) this.ComboGatheringOjbect.getSelectedItem());
		
		if(MoneySysTypeEnum.SalehouseSys.equals(getSystemType()))
		{
			fdcReceiveBillInfo.setPurchase((PurchaseInfo) this.f7Purchase.getValue());
		}
		else if(MoneySysTypeEnum.TenancySys.equals(getSystemType()))
		{
			fdcReceiveBillInfo.setTenancyContract((TenancyBillInfo) this.f7TenancyContract.getValue());
		}
		//这个变量没有很实际的意义，只是用来辅助后台收款，确切的对上哪一条明细记录
	
	    fdcReceiveBillInfo.setSeq(this.seq);
		
	    fdcReceiveBillInfo.setGathering((GatheringEnum) this.comboGathering.getSelectedItem());
	    fdcReceiveBillInfo.setSinPurchase((SincerityPurchaseInfo) this.f7SinPurchase.getValue());
	    fdcReceiveBillInfo.setSinObligate((SincerObligateInfo) this.f7SinObligate.getValue());
	    
		CustomerInfo customer = new CustomerInfo();
		//这样写是因为客户那边是多选的，多选、单选时候所得到的结果是不一样的
		if (this.f7Customer.getValue() instanceof Object[])
		{
			Object o[] = (Object[]) this.f7Customer.getValue();
			fdcReceiveBillInfo.getCustomerEntrys().clear();
			StringBuffer cusNameDes = new StringBuffer();
			if (o != null && o.length > 0)
			{
				if (o[0] instanceof CustomerInfo)
				{
					customer = (CustomerInfo) o[0];

					if (customer != null)
					{
						receivingBillInfo.setPayerID(customer.getId().toString());
						receivingBillInfo.setPayerNumber(customer.getNumber());
						// rev.setPayerName(customer.getName());
					}
				}
				
				for (int i = 0; i < o.length; i++)
				{
					if (o[i] instanceof CustomerInfo)
					{
						CustomerEntryInfo customerEntryInfo = new	CustomerEntryInfo();
						CustomerInfo cus = (CustomerInfo) o[i];
						customerEntryInfo.setCustomer(cus);

						fdcReceiveBillInfo.getCustomerEntrys().add(customerEntryInfo);
						if (i != 0)
						{
							cusNameDes.append(";");
						}
						cusNameDes.append(cus.getName());
					}
				}
			}
			receivingBillInfo.setPayerName(cusNameDes.toString());

		} else
		{
			customer = (CustomerInfo) this.f7Customer.getValue();
			if (customer != null)
			{
				receivingBillInfo.setPayerID(customer.getId().toString());
				receivingBillInfo.setPayerNumber(customer.getNumber());
				receivingBillInfo.setPayerName(customer.getName());
			}

			fdcReceiveBillInfo.getCustomerEntrys().clear();

			CustomerEntryInfo customerEntryInfo = new CustomerEntryInfo();
			customerEntryInfo.setCustomer(customer);
			fdcReceiveBillInfo.getCustomerEntrys().add(customerEntryInfo);
		}

		if(this.f7Currency.getValue() != null)
		{
			receivingBillInfo.setCurrency(((CurrencyInfo) this.f7Currency.getValue()));
		}
		
		BigDecimal receiveAmount = this.txtReceiveAmount.getBigDecimalValue();
		if(ReceiveBillTypeEnum.refundment.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			if(receiveAmount != null)
			{
				receiveAmount = FDCHelper.ZERO.subtract(receiveAmount);
			}
		}
		receivingBillInfo.setAmount(receiveAmount);
		
		
		receivingBillInfo.setActRecAmt(receiveAmount);

	
		receivingBillInfo.setDescription(this.txtDes.getText());
		if (this.f7Cheque.isVisible())
		{
			ChequeInfo chequeInfo = (ChequeInfo) this.f7Cheque.getValue();
			fdcReceiveBillInfo.setCheque(chequeInfo);
			fdcReceiveBillInfo.setReceiptNumber(chequeInfo == null ? null : chequeInfo.getNumber());
		} else
		{
			fdcReceiveBillInfo.setReceiptNumber(this.txtReceiptNumber.getText());
		}

		// --修改BT 304182
		// 分录
		if (receivingBillInfo.getEntries().isEmpty())
		{
			ReceivingBillEntryInfo entry = new ReceivingBillEntryInfo();
			
			
			entry.setAmount(receiveAmount);
			entry.setActualAmt(receiveAmount);
			receivingBillInfo.getEntries().add(entry);
		} else
		{
			ReceivingBillEntryInfo entry = receivingBillInfo.getEntries().get(0);
			entry.setAmount(receiveAmount);
			entry.setActualAmt(receiveAmount);
		}
		// rev.getEntries().clear();
		// --------------------------------------------

		// 房地产收款单分录
		FDCReceiveBillEntryCollection fdcReceiveBillEntryColl = new FDCReceiveBillEntryCollection();
		for (int i = 0; i < kDTable1.getRowCount(); i++)
		{
			FDCReceiveBillEntryInfo receiveBillEntryInfo = new FDCReceiveBillEntryInfo();
			IRow row = kDTable1.getRow(i);
			
			Object tempObj = row.getCell("counteractAmount").getValue();
			if(tempObj instanceof BigDecimal)
			{
				BigDecimal counteractAmount = (BigDecimal)tempObj;
				receiveBillEntryInfo.setCounteractAmount(counteractAmount);
			}
			
			
			String payListId = (String)row.getCell("id").getValue();
			receiveBillEntryInfo.setPayListId(payListId);
			
			MoneyDefineInfo moneyDefineInfo = (MoneyDefineInfo)row.getCell("moneyType").getValue();
			
			receiveBillEntryInfo.setMoneyDefine(moneyDefineInfo);
			
			receivingBillInfo.setRecBillType(moneyDefineInfo.getRevBillType());
			
		    receiveBillEntryInfo.setSettlementType((SettlementTypeInfo) row.getCell("settlementType").getValue());
			
			BigDecimal gatheringAmount = (BigDecimal) row.getCell("gatheringAmount").getValue();
			if(gatheringAmount == null)
				gatheringAmount = FDCHelper.ZERO;
			
			if(ReceiveBillTypeEnum.refundment.equals(this.recBillType))
			{
				BigDecimal temp = (BigDecimal)row.getCell("refundmentAmount").getValue();
				if(temp == null)
					temp = FDCHelper.ZERO;
				if(temp.compareTo(FDCHelper.ZERO) > 0)
				{
					temp = FDCHelper.ZERO.subtract(temp);
				}
				receiveBillEntryInfo.setAmount(temp);
			}
			else
			{
				receiveBillEntryInfo.setAmount(gatheringAmount);
			}
			
			receiveBillEntryInfo.setSettlementNumber((String) row.getCell("settlementNumber").getValue());
			
			AccountBankInfo bankInfo = (AccountBankInfo) row.getCell("gatheringAccount").getValue();
			
			receiveBillEntryInfo.setRevAccountBank(bankInfo);
			
			if(bankInfo != null)
			{
				receiveBillEntryInfo.setRevBank(bankInfo.getBank());
			}
			
			receiveBillEntryInfo.setAccount((AccountViewInfo) row.getCell("gatheringSubject").getValue());
			
			receiveBillEntryInfo.setOppSubject((AccountViewInfo) row.getCell("oppSubject").getValue());
			
			receiveBillEntryInfo.setBankNumber((String)row.getCell("paymentAccount").getValue());
			
			receiveBillEntryInfo.setSeq(i);
			
			//如果是物业的预收款，则直接把可退金额写上去
			//售楼的诚意金预收款，也直接把可退金额写上去
			if((MoneySysTypeEnum.SalehouseSys.equals(getSystemType()) || 
					MoneySysTypeEnum.ManageSys.equals(getSystemType())) && MoneyTypeEnum.PreMoney.equals(moneyDefineInfo.getMoneyType()))
			{
				BigDecimal temp =(BigDecimal) row.getCell("gatheringAmount").getValue();
				receiveBillEntryInfo.setCanCounteractAmount(temp);
			}
			
			fdcReceiveBillEntryColl.add(receiveBillEntryInfo);
		}
		receivingBillInfo.getFdcReceiveBillEntry().clear();
		receivingBillInfo.getFdcReceiveBillEntry().addCollection(fdcReceiveBillEntryColl);
		
		// add by jiyu_guan
		if (f7Cheque.getValue() == null
				&& (txtReceiptNumber.getText() == null || txtReceiptNumber
						.getText().equals(""))) {
			fdcReceiveBillInfo.setReceiptState(ReceiptStatusEnum.UnMake);
		} else {
			fdcReceiveBillInfo.setReceiptState(ReceiptStatusEnum.HasMake);
		}
	}
	
	/**
	 * 单据标题
	 * @param type
	 */
	protected void updateUITitle(ReceiveBillTypeEnum type)
	{
		if(type == null)
			return;
		
		if(ReceiveBillTypeEnum.settlement.equals(type))
		{
			this.setUITitle("结转单");
		}
		else if(ReceiveBillTypeEnum.refundment.equals(type))
		{
			this.setUITitle("退款单");
		}
	}
	

	protected IObjectValue createNewData()
	{
		ReceivingBillInfo receivingBillInfo = new ReceivingBillInfo();
		try
		{
			CompanyOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentFIUnit();
			CurrencyInfo baseCurrency = CurrencyFactory.getRemoteInstance()
					.getCurrencyInfo(new ObjectUuidPK(BOSUuid.read(currentCompany.getBaseCurrency().getId().toString())));
			receivingBillInfo.setCurrency(baseCurrency);
			receivingBillInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
			receivingBillInfo.setCreateTime(FDCCommonServerHelper.getServerTimeStamp());
			receivingBillInfo.setPayerType(getCustomerType());
		} catch (Exception e)
		{
			super.handUIException(e);
		}
		receivingBillInfo.setIsInitializeBill(false);
		receivingBillInfo.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
		receivingBillInfo.setBizDate(new Date());
		receivingBillInfo.setSourceType(SourceTypeEnum.FDC);
		receivingBillInfo.setSourceSysType(SourceTypeEnum.FDC);
		FDCReceiveBillInfo fdcReceiveBillInfo = new FDCReceiveBillInfo();
		receivingBillInfo.setFdcReceiveBill(fdcReceiveBillInfo);
		fdcReceiveBillInfo.setPayQuomodo(PayQuomodoEnum.CASH);
		fdcReceiveBillInfo.setIsBlankOut(false);
		if (this.getUIContext().get(KEY_BILLTYPE) != null)
		{
			fdcReceiveBillInfo.setBillTypeEnum((ReceiveBillTypeEnum) this.getUIContext().get(KEY_BILLTYPE));
		} else
		{
			fdcReceiveBillInfo.setBillTypeEnum(ReceiveBillTypeEnum.gathering);
		}
		fdcReceiveBillInfo.setId(BOSUuid.create(fdcReceiveBillInfo.getBOSType()));
		
		MoneySysTypeEnum moneySysTypeEnum = (MoneySysTypeEnum) this.getUIContext().get(KEY_MONEYTSYSTYPE);
		//为了兼容以前的一些数据什么的，如果moneySysTypeEnum 为空，也认为是来自售楼系统， 这就要求 从租赁和其他系统过来的，一定得带上参数
		if(moneySysTypeEnum == null){
			moneySysTypeEnum = MoneySysTypeEnum.SalehouseSys;
		}
		fdcReceiveBillInfo.setMoneySysType(moneySysTypeEnum);
		

		try {
			return this.createNewDataForSys(receivingBillInfo, fdcReceiveBillInfo);
		} catch (Exception e) {
			this.handleException(e);
			this.abort();
		}

		return null;
	}

	
	protected IObjectValue createNewDataForSys(ReceivingBillInfo receivingBillInfo, FDCReceiveBillInfo fdcReceiveBillInfo) throws EASBizException, BOSException, UuidException
	{
		return receivingBillInfo;
	}
	

	


	private AsstActTypeInfo getCustomerType() throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		String maskString = filter.getMaskString();
		filter.getFilterItems().add(
				new FilterItemInfo("realtionDataObject", "T_BD_Customer",CompareType.EQUALS));
		if (maskString != null)
		{
			filter.setMaskString("(" + maskString + ") AND #2");
		}
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filter);
		AsstActTypeCollection asstActTypeCollection = AsstActTypeFactory.getRemoteInstance().getAsstActTypeCollection(evi);
		if (asstActTypeCollection != null && asstActTypeCollection.size() == 1)
		{
			return asstActTypeCollection.get(0);
		}
		return null;
	}



	protected void setNumberTextEnabled()
	{
		if (getNumberCtrl() != null)
		{
			OrgUnitInfo currentFIUnit = SysContext.getSysContext()
					.getCurrentFIUnit();

			if (currentFIUnit != null)
			{
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(
						this.editData, currentFIUnit.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
			}
		}
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return ReceivingBillFactory.getRemoteInstance();
	}

	// TODO 确认该函数没用了吧？界面上隐藏了SAVE
	public void actionSave_actionPerformed(ActionEvent e) throws Exception
	{
		this.storeFields();
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) this.editData;
		receivingBillInfo.setBillStatus(BillStatusEnum.SAVE);
		FDCReceiveBillFactory.getRemoteInstance().submitByCasRev(receivingBillInfo);
		IObjectPK pk = runSave();
		if (pk == null)
		{
			return;
		}

		if (OprtState.ADDNEW.equals(getOprtState()))
		{
			// int currentIndex =idList.getCurrentIndex() + 1;
			int size = idList.size();
			idList.add(size, pk.toString());
			// idList.setCurrentIndex(currentIndex);
		}

		setOprtState(OprtState.EDIT);
		setDataObject(getValue(pk));
		// 为业务系统使用当前暂存后的editData来使用。2005-11-14 by psu_s
		showMessageForStatus();
		showSaveSuccess();
		// 要在getValue后，工作流会用到值对象中的数据
		if (wfContext.isBindWorkFlow())
		{
			wfContext.addToDataMap(pk.toString(), editData);
		}
		setSave(true);
		setSaved(true);
		loadFields();
		this.storeFields();
		this.initOldData(this.editData);
	}

	/**
	 * 判断编码是否重复
	 */
	protected void checkNumberDup(IObjectValue model)throws BOSException, EASBizException 
	{
		String number = model.getString("number");
		if (number == null || number.length() <= 0) {
			// 允许空编码保存
			throw new EASBizException(EASBizException.CHECKNUMBLANK);
		}
		model.setString("number", number.trim());
		FilterInfo filter = FMHelper.getCheckNumberDupFilter(model, false);
		if (ReceivingBillFactory.getRemoteInstance().exists(filter)) 
		{
			MsgBox.showInfo("编码 "+ number+" 在系统中已经存在！");
			this.abort();
		}
	}
	
	/**
	 * 根据新的付款明细来生成一个 红冲单
	 * （对于换房后的新房间红冲收款：红冲单的房间对象和认购单对象应该是原房间上的）
	 * @param recBill
	 * @param fdcRecEntryColl
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private ReceivingBillInfo generateHongChongRecBill(ReceivingBillInfo recBill,FDCReceiveBillEntryCollection fdcRecEntryColl) throws EASBizException, BOSException
	{
		recBill.getFdcReceiveBillEntry().clear();
		
		//去掉原有的ID
		String hongChongEntryId = null;
		FDCReceiveBillEntryCollection tempColl = new FDCReceiveBillEntryCollection();
		for(int i = 0; i < fdcRecEntryColl.size(); i ++)
		{
			FDCReceiveBillEntryInfo info = fdcRecEntryColl.get(i);
			this.updateCanCounteractAmount(info);
			String FCounteractId = info.getId() == null ? null : info.getId().toString();
			info.setId(null);
			info.setFCounteractId(FCounteractId);
			
			tempColl.add(info);
			if(i==0)  hongChongEntryId = FCounteractId;
		}
		
		if(hongChongEntryId!=null) {
			FDCReceiveBillEntryInfo entryInfo = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryInfo(
					"select receivingBill.fdcReceiveBill.room,receivingBill.fdcReceiveBill.purchase where id = '"+hongChongEntryId+"' ");
			if(entryInfo!=null && entryInfo.getReceivingBill()!=null && entryInfo.getReceivingBill().getFdcReceiveBill()!=null) {
				recBill.getFdcReceiveBill().setRoom(entryInfo.getReceivingBill().getFdcReceiveBill().getRoom());
				recBill.getFdcReceiveBill().setPurchase(entryInfo.getReceivingBill().getFdcReceiveBill().getPurchase());
			}
		}
		
		
		recBill.getFdcReceiveBillEntry().addCollection(tempColl);
		
		return recBill;
	}
	
	//更新被红冲的收款单分录的可红冲金额和已红冲金额
	private void updateCanCounteractAmount(FDCReceiveBillEntryInfo info) throws EASBizException, BOSException
	{
		BigDecimal canCounteractAmount = info.getCanCounteractAmount();
		BigDecimal counteractAmount = info.getCounteractAmount();
		if(canCounteractAmount == null)
			canCounteractAmount = FDCHelper.ZERO;
		if(counteractAmount == null)
			counteractAmount = FDCHelper.ZERO;
		
		BigDecimal temp = info.getAmount();
		if(temp == null)
			temp = FDCHelper.ZERO;
		else
			temp = temp.abs();
		
		info.setCanCounteractAmount(canCounteractAmount.subtract(temp));
		info.setCounteractAmount(counteractAmount.add(temp));
		
		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("canCounteractAmount");
		selColl.add("counteractAmount");
		
		FDCReceiveBillEntryFactory.getRemoteInstance().updatePartial(info, selColl);
		
		//TODO 对于售楼系统,同时还要更新相应的诚意认购付款明细的可退金额.
		if(MoneySysTypeEnum.SalehouseSys.equals(getSystemType())){
			String payId = info.getPayListId();
			if(payId == null){
				return;
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("id", payId));
			SincerReceiveEntryCollection sinPays = SincerReceiveEntryFactory.getRemoteInstance().getSincerReceiveEntryCollection(view);
			
			if(sinPays == null  ||  sinPays.isEmpty()){
				return;
			}
			
			SincerReceiveEntryInfo sinPay = sinPays.get(0);
			BigDecimal canRefundmentAmountOfPay = FDCHelper.toBigDecimal(sinPay.getCanRefundmentAmount());
			
//			sinPay.setHasRefundmentAmount(canRefundmentAmountOfPay.subtract(temp));
			
			SelectorItemCollection upSinPaySels = new SelectorItemCollection();
			upSinPaySels.add("canRefundmentAmount");
			SincerReceiveEntryFactory.getRemoteInstance().updatePartial(sinPay, upSinPaySels);
		}
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
	{
		this.verifyUIData();
		ReceivingBillInfo recevingBillInfo = (ReceivingBillInfo) this.editData;
		recevingBillInfo.setBillStatus(BillStatusEnum.SUBMIT);
		this.storeFields();
		String receivingBillId = FDCReceiveBillFactory.getRemoteInstance().submitByCasRev(recevingBillInfo);
		//如果是退款的话也把对应认购上的分单分录复制一份到对应的退款单上
		if(ReceiveBillTypeEnum.refundment.equals(this.recBillType))
		{			
			FDCReceiveBillInfo fdcRecInfo = recevingBillInfo.getFdcReceiveBill();
			PurchaseInfo purInfo = fdcRecInfo.getPurchase();
			if(purInfo!=null)  {
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("*");
				sels.add("distillCommisionEntry.*");
				purInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(purInfo.getId()), sels);
				if(purInfo.getDistillCommisionEntry().size()>0)
				{
					CoreBaseCollection recComColl = new CoreBaseCollection();
					PurDistillCommisionEntryCollection purComColl = purInfo.getDistillCommisionEntry();
					//ReceiveDistillCommisionEntryCollection recComColl = new ReceiveDistillCommisionEntryCollection();
					for(int j=0;j<purComColl.size();j++)
					{
						PurDistillCommisionEntryInfo purComInfo = purComColl.get(j);
						ReceiveDistillCommisionEntryInfo recComInfo = new ReceiveDistillCommisionEntryInfo();
						recComInfo.setSharePercent(purComInfo.getSharePercent());
						recComInfo.setTakePercentage(purComInfo.getTakePercentage());
						recComInfo.setDistillCommisionLevel(purComInfo.getDistillCommisionLevel());
						recComInfo.setIsAchieveCommision(purComInfo.isIsAchieveCommision());
						recComInfo.setUser(purComInfo.getUser());
						recComInfo.setMarketUnit(purComInfo.getMarketUnit());
						recComInfo.setHead(fdcRecInfo);
						recComColl.add(recComInfo);
					}
					ReceiveDistillCommisionEntryFactory.getRemoteInstance().addnew(recComColl);
				}
			}
		}
		//如果是结转收款，则需要生成一张是 结转类型的收款单
		if(ReceiveBillTypeEnum.settlement.equals(this.recBillType) || this.isSettlement)
		{
			ReceivingBillInfo tmp = (ReceivingBillInfo) recevingBillInfo.clone();
			tmp.setId(null);
			ReceivingBillInfo settlementBillInfo = this.generateHongChongRecBill(tmp,this.hongChongFdcRecEntryColl);
			
			settlementBillInfo.getFdcReceiveBill().setId(null);
			settlementBillInfo.getFdcReceiveBill().setBillTypeEnum(ReceiveBillTypeEnum.settlement);
			String settlementId = FDCReceiveBillFactory.getRemoteInstance().submitByCasRev(settlementBillInfo);
			
			//将结转单的ID关联到房地产收款单上
			ReceivingBillInfo rec = ReceivingBillFactory.getRemoteInstance().getReceivingBillInfo(new ObjectUuidPK(receivingBillId));
			FDCReceiveBillInfo fdcRev = rec.getFdcReceiveBill();
			
			ReceivingBillInfo settRev = new ReceivingBillInfo();
			settRev.setId(BOSUuid.read(settlementId));
			
			fdcRev.setSettlementBill(settRev);
			
			SelectorItemCollection upSels = new SelectorItemCollection();
			upSels.add("settlementBill.id");
			FDCReceiveBillFactory.getRemoteInstance().updatePartial(fdcRev, upSels);
			
//			fdcRev.setSettlementBill(item)
//			recevingBillInfo.getFdcReceiveBill().setSettlementBill(item);
//			rece
		}
		
		this.setOprtState(OprtState.VIEW);
	
		if (receivingBillId == null)
		{
			MsgBox.showInfo("系统错误");
			this.abort();
		}
		showSubmitSuccess();
		
		//add by jiyu_guan
		updateRecord(receivingBillId);

		
		this.getUIContext().put("ID", receivingBillId);
		this.onLoad();
		this.actionSave.setEnabled(false);
		this.actionSubmit.setEnabled(false);
		this.actionAudit.setEnabled(true);
		this.actionEdit.setEnabled(true);

		this.storeFields();
		this.initOldData(this.editData);
		
		//add by jiyu_guan
		loadFeildsForRecord();
	}
	/**
	 * 将分录的验证和主界面的验证合在一起了
	 * @throws BOSException
	 * @throws Exception
	 */
	public void verifyUIData() throws BOSException, Exception
	{
		this.verifySubmit();
		this.verifyEntryTable();
	}
	
	/**
	 * 获得经过seq 排序的付款明细
	 * @param purchase
	 * @return
	 */
	private PurchasePayListEntryCollection getPurchaseCollOrderBySeq(PurchaseInfo purchase)
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("head.id",purchase.getId().toString()));
		
		SorterItemCollection sorColl = new SorterItemCollection();
		sorColl.add(new SorterItemInfo("seq"));
		
		view.setSorter(sorColl);
		
		PurchasePayListEntryCollection payColl = null;
		
		try
		{
			payColl = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		return payColl;
	}
	/**
	 * 校验分录里面为是认购单的分录
	 *
	 */
	private void verifyPurchasePayListEntry()
	{
		PurchaseInfo  purchase = (PurchaseInfo) this.f7Purchase.getValue();
		PurchasePayListEntryCollection payColl = this.getPurchaseCollOrderBySeq(purchase);
		List tempList = new ArrayList();
		for(int i = 0; i< payColl.size(); i ++)
		{
			tempList.add(payColl.get(i).getId().toString());
		}
		Map map = new HashMap();
		for(int i = 0; i < this.kDTable1.getRowCount(); i ++)
		{
			IRow row = this.kDTable1.getRow(i);
			String id = (String)row.getCell("id").getValue();
			if(tempList.contains(id))
			{
				map.put(id,new Integer(i));
			}
		}
		Set keySet = map.keySet();
		
		List list = new ArrayList();
		for(int i = 0; i < payColl.size(); i ++)
		{
			String tempId = payColl.get(i).getId().toString();
			if(keySet.contains(tempId))
			{
				list.add(tempId);
			}
		}
		int size = list.size();
		boolean debug = true;
		int debugIndex = 0;
		for(int i = 0; i < size; i ++)
		{
			String id = list.get(i).toString();
			if(i < size - 1)
			{
				Integer rowIndex = (Integer)map.get(id);
				IRow  row = this.kDTable1.getRow(rowIndex.intValue());
				BigDecimal appAmount = (BigDecimal)row.getCell("appAmount").getValue();
				BigDecimal actAmount = (BigDecimal)row.getCell("actAmount").getValue();
				BigDecimal gatheringAmount = (BigDecimal)row.getCell("gatheringAmount").getValue();
				if(appAmount == null)
					appAmount = FDCHelper.ZERO;
				if(actAmount == null)
					actAmount = FDCHelper.ZERO;
				if((actAmount.add(gatheringAmount)).compareTo(appAmount) < 0)
				{
					debug = false;
					debugIndex = rowIndex.intValue();
					break;
				}
			}
		}
		if(!debug)
		{
			//MsgBox.showWarning("第 "+ (debugIndex +1) +" 行付款明细，需交齐全款！");
			//this.abort();
		}
	}

	//根据收款对象确定非空项
	private void verifyGatheringObject(){
		GatheringEnum gathering = (GatheringEnum) this.comboGathering.getSelectedItem();
		if(GatheringEnum.SinPurchase.equals(gathering)){
			canNotNull(this.f7SinPurchase, "诚意认购单");
		}else if(GatheringEnum.SaleRoom.equals(gathering)){
			canNotNull(this.f7Room, "房间");
			canNotNull(this.f7Purchase, "认购单");
		}else if(GatheringEnum.ObligatedRoom.equals(gathering)){
			canNotNull(this.f7Room, "房间");
			canNotNull(this.f7SinObligate, "诚意预留单");
		}else if(GatheringEnum.ObligatedAttach.equals(gathering)){
//			canNotNull(this.f7, "配套资源");
			canNotNull(this.f7SinObligate, "诚意预留单");
		}else if(GatheringEnum.TenRoom.equals(gathering)){
			canNotNull(this.f7Room, "房间");
			canNotNull(this.f7TenancyContract, "租赁合同");
		}else if(GatheringEnum.TenAttach.equals(gathering)){
			canNotNull(this.f7Accessorial, "配套资源");
			canNotNull(this.f7TenancyContract, "租赁合同");
		}else if(GatheringEnum.WuYeRoomFee.equals(gathering)){
			canNotNull(this.f7Room, "房间");
		}
	}
	
	private void canNotNull(KDBizPromptBox f7, String msg){
		if(f7.getValue() == null){
			MsgBox.showInfo(this, msg + "不能为空!");
			this.abort();
		}
	}
	
	private void verifySubmit() throws BOSException, EASBizException
	{
		if(GatheringObjectEnum.equipment.equals(this.ComboGatheringOjbect.getSelectedItem()))
		{
			MsgBox.showInfo("暂无该类型的收款操作！");
			this.abort();
		}
		if (this.pkBillDate.getValue() == null)
		{
			MsgBox.showInfo("业务日期必须录入");
			this.abort();
		}
		if (this.f7Customer.getValue() == null)
		{
			MsgBox.showInfo("客户必须录入");
			this.abort();
		}

		// 在设置了编码规则为不可编辑，且不显示时，就不能对编码做非空验证了
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable()
				&& StringUtils.isEmpty(this.txtNumber.getText()))
		{
			MsgBox.showInfo("收款编码必须录入");
			this.abort();
		}
		
		//结算控制
		verifyBalance();
		
		verifyGatheringObject();
		
		GatheringEnum gathering = (GatheringEnum) this.comboGathering.getSelectedItem();
		if(ReceiveBillTypeEnum.gathering.equals(this.recBillType)  &&  GatheringEnum.SinPurchase.equals(gathering)){
			//如果存在该诚意认购单的退款，不允许再进行收款
			SincerityPurchaseInfo sinPurchase = (SincerityPurchaseInfo) this.f7SinPurchase.getValue();
			FilterInfo refundFilter = new FilterInfo();
			refundFilter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.sinPurchase.id", sinPurchase.getId().toString()));
			refundFilter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.billTypeEnum", ReceiveBillTypeEnum.REFUNDMENT_VALUE));
			if(ReceivingBillFactory.getRemoteInstance().exists(refundFilter)){
				MsgBox.showInfo("诚意认购单已经退过款,无法再收款!");
				this.abort();
			}
		}
		
		/*
		 *在特定系统中才做的校验 
		 */
		if(MoneySysTypeEnum.SalehouseSys.equals(getSystemType()))
		{
			PurchaseInfo purchase = (PurchaseInfo) this.f7Purchase.getValue();
//			 --------081031 变更中的认购单不允许收款 by zhicheng_jin-------
			if (purchase != null  &&  PurchaseStateEnum.PurchaseChange
					.equals(purchase.getPurchaseState()))
			{
				MsgBox.showInfo("认购单在变更流程中,不能收款!");
				this.abort();
			}
//			 --------------------------
		} 
		
		BigDecimal receiveAmount = this.txtReceiveAmount.getBigDecimalValue();
		
		if (!this.getOprtState().equals("EDIT"))
		{
			BigDecimal apAmount = this.appAmount;
			if (apAmount == null)
			{
				apAmount = FDCHelper.ZERO;
			}
			BigDecimal actAmount = this.actAmount;
			if (actAmount == null)
			{
				actAmount = FDCHelper.ZERO;
			}
		}		
	}

	/**
	 * 对于已结算的期间，不允许进行收款及修改
	 * */
	private void verifyBalance() {
		Date bizDate = (Date) this.pkBillDate.getValue();
		if(bizDate==null){
			MsgBox.showInfo("业务日期不能为空！");
			this.abort();
		}
		
		if(getSystemType().equals(MoneySysTypeEnum.SalehouseSys)) { //只是针对售楼系统才需要
			SellProjectInfo sellProject = (SellProjectInfo) this.f7SellProject.getValue();
			if(sellProject != null){
				Date balanceEndDate = sellProject.getBalanceEndDate();
				SHEHelper.verifyBalance(bizDate, balanceEndDate);
			}
		}
	}
	
	protected boolean checkCanSubmit() throws Exception
	{
		return true;
	}





	



	




	public void actionAudit_actionPerformed(ActionEvent e) throws Exception
	{
		Set set = new HashSet();
		set.add(this.editData.getId().toString());
		((IReceivingBill) getBizInterface()).audit(set);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		this.setOprtState("VIEW");

		this.onLoad();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id",set,CompareType.INCLUDE));

		EntityViewInfo ev = new EntityViewInfo();
		ev.setFilter(filter);
		
		ev.getSelector().add("*");
		ev.getSelector().add("fdcReceiveBill.*");
		ev.getSelector().add("fdcReceiveBill.purchase.*");
		
		ReceivingBillCollection recColl = ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection(ev);
		for(int i=0;i<recColl.size();i++)
		{
			FDCReceiveBillInfo fdcRecInfo = recColl.get(i).getFdcReceiveBill();
			PurchaseInfo purInfo = fdcRecInfo.getPurchase();
			if(purInfo!=null)  {
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("*");
				sels.add("distillCommisionEntry.*");
				purInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(purInfo.getId()), sels);
				if(purInfo.getDistillCommisionEntry().size()>0)
				{
					CoreBaseCollection recComColl = new CoreBaseCollection();
					PurDistillCommisionEntryCollection purComColl = purInfo.getDistillCommisionEntry();
					//ReceiveDistillCommisionEntryCollection recComColl = new ReceiveDistillCommisionEntryCollection();
					for(int j=0;j<purComColl.size();j++)
					{
						PurDistillCommisionEntryInfo purComInfo = purComColl.get(j);
						ReceiveDistillCommisionEntryInfo recComInfo = new ReceiveDistillCommisionEntryInfo();
						recComInfo.setSharePercent(purComInfo.getSharePercent());
						recComInfo.setTakePercentage(purComInfo.getTakePercentage());
						recComInfo.setDistillCommisionLevel(purComInfo.getDistillCommisionLevel());
						recComInfo.setIsAchieveCommision(purComInfo.isIsAchieveCommision());
						recComInfo.setUser(purComInfo.getUser());
						recComInfo.setMarketUnit(purComInfo.getMarketUnit());
						recComInfo.setHead(fdcRecInfo);
						recComColl.add(recComInfo);
					}
					ReceiveDistillCommisionEntryFactory.getRemoteInstance().addnew(recComColl);
				}
			}
		}
	}

	public void actionRec_actionPerformed(ActionEvent e) throws Exception
	{
		// 网络对象互斥
		// MutexUtils.setMutex(this, getSelectedKeyValue());
		ReceivingBillInfo receiveBill = (ReceivingBillInfo) this.editData;

		if (!BillStatusEnum.AUDITED.equals(receiveBill.getBillStatus()))
		{
			MsgBox.showInfo("只有已审批状态的收款单才能收款!");
			return;
		}
		Set idSet = new HashSet();
		idSet.add(receiveBill.getId().toString());
		((IReceivingBill) getBizInterface()).rec(idSet);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);

		/* 不能这么写呀
		//反写诚意认购单的收款状态
		GatheringEnum gathering = (GatheringEnum) this.getUIContext().get(KEY_GATHERING);
		if (gathering!=null && GatheringEnum.SinPurchase.equals(gathering)) {
			SincerityPurchaseInfo sinPur = (SincerityPurchaseInfo) this.getUIContext().get(KEY_SIN_PURCHASE);
			if(sinPur!=null) {
				sinPur.setIsRev(true);
				sinPur.setRevDate(new Date());
				SincerityPurchaseFactory.getRemoteInstance().update(new ObjectUuidPK(sinPur.getId()),sinPur);
			}
		}	
		*/
		
		this.setOprtState("VIEW");
		this.onLoad();
	}

	/**
	 * 生成凭证
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
	{
		ReceivingBillInfo rev = (ReceivingBillInfo) this.editData;
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("billStatus");
		sels.add("fdcReceiveBill.billTypeEnum");
		sels.add("fdcReceiveBill.adjustSrcBill.fdcReceiveBill.billTypeEnum");
		
		ReceivingBillInfo receivingBill = ReceivingBillFactory.getRemoteInstance().getReceivingBillInfo(new ObjectUuidPK(rev.getId().toString()), sels);
		if (!BillStatusEnum.RECED.equals(receivingBill.getBillStatus()))
		{
			//TODO 这里需要读出纳 一个参数进行控制
			MsgBox.showInfo("只有已收款状态的收款单才能能生成凭证!");
			this.abort();
		}
		
		if(receivingBill.getFdcReceiveBill() != null  &&  ReceiveBillTypeEnum.settlement.equals(receivingBill.getFdcReceiveBill().getBillTypeEnum())){
			MsgBox.showInfo(this, "红冲单不能生成凭证!");
			this.abort();
		}
		
		FDCReceiveBillInfo fdcRev = receivingBill.getFdcReceiveBill();
		if(fdcRev != null  &&  ReceiveBillTypeEnum.adjust.equals(fdcRev.getBillTypeEnum())){
			ReceivingBillInfo adjustSrcRev = fdcRev.getAdjustSrcBill();
			if(adjustSrcRev != null  &&  adjustSrcRev.getFdcReceiveBill() != null){
				if(ReceiveBillTypeEnum.settlement.equals(adjustSrcRev.getFdcReceiveBill().getBillTypeEnum())){
					MsgBox.showInfo(this, "红冲单生成的调整单不能生成凭证!");
					this.abort();
				}
			}
		}
		
		super.actionVoucher_actionPerformed(e);

		this.setOprtState("VIEW");
		this.onLoad();
	}

	protected void attachListeners()
	{
	}

	protected void detachListeners()
	{
	}

	protected KDTextField getNumberCtrl()
	{
		return this.txtNumber;
	}

	private EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo)
	{
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------转6.0后修改,科目不按CU隔离,根据财务组织进行隔离
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}
	
	/**
	 * 初始化表格
	 */
	protected void initEntryTableColumnEditor()
	{
		this.kDTable1.checkParsed();
		
		this.kDTable1.getColumn("moneyType").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("settlementType").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("appAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("actAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("derateAmount").getStyleAttributes().setLocked(true);
		
		this.kDTable1.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.kDTable1.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.kDTable1.getColumn("refundmentAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("refundmentAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		if (ReceiveBillTypeEnum.refundment.equals(this.recBillType))
		{
			this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setHided(true);
			this.kDTable1.getColumn("refundmentAmount").getStyleAttributes().setHided(false);
			this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setHided(false);
			this.kDTable1.getColumn("appAmount").getStyleAttributes().setHided(true);
			this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setHided(false);
		}
		else
		{
			this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setHided(false);
			this.kDTable1.getColumn("refundmentAmount").getStyleAttributes().setHided(true);
			this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setHided(true);
			this.kDTable1.getColumn("appAmount").getStyleAttributes().setHided(false);
			this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setHided(true);
		}
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		// 金額
		formattedTextField.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				if (ReceiveBillTypeEnum.refundment.equals(recBillType)) {
					kDTable1.getRow(currentActiveRowIndex).getCell("refundmentAmount").setValue(eventObj.getNewValue());
				} else {
					//jetdai 2009-12-11
					//是零星收款时就不用它的逻辑
//					if(!ReceiveBillTypeEnum.partial.equals(recBillType))
//						kDTable1.getRow(row).getCell("gatheringAmount").setValue(eventObj.getNewValue());
				}
				updateAmountTotal();
			}
		});
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setMinimumValue(FDCHelper.MIN_DECIMAL);
		formattedTextField.setMaximumValue(new BigDecimal(Integer.MAX_VALUE));
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kDTable1.getColumn("gatheringAmount").setEditor(numberEditor);
		
		this.kDTable1.getColumn("appAmount").setEditor(numberEditor);
		this.kDTable1.getColumn("actAmount").setEditor(numberEditor);
		
		this.kDTable1.getColumn("refundmentAmount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kDTable1.getColumn("refundmentAmount").setEditor(numberEditor);
		
		this.kDTable1.getColumn("hasRefundmentAmount").setEditor(numberEditor);
		this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setLocked(true);
		
		this.kDTable1.getColumn("canRefundmentAmount").setEditor(numberEditor);
		this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setLocked(true);

		// 收款科目
		KDBizPromptBox gatheringSubject = new KDBizPromptBox();
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext().getCurrentFIUnit();
		
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox opseelect = new AccountPromptBox(this, curCompany,view.getFilter(), false, true);
		gatheringSubject.setEntityViewInfo(view);
		gatheringSubject.setSelector(opseelect);
		gatheringSubject.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
		gatheringSubject.setEditFormat("$number$");
		gatheringSubject.setCommitFormat("$number$");

		ICellEditor f7Editor = new KDTDefaultCellEditor(gatheringSubject);
		this.kDTable1.getColumn("gatheringSubject").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kDTable1.getColumn("gatheringSubject").setEditor(f7Editor);
		
		//对方科目
		this.kDTable1.getColumn("oppSubject").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kDTable1.getColumn("oppSubject").setEditor(f7Editor);
		
//		 付款账户
		KDBizPromptBox paymentAccount = new KDBizPromptBox();
		paymentAccount.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7AccountBankQuery");
		paymentAccount.setEditFormat("$number$");
		paymentAccount.setCommitFormat("$number$");
	
		CompanyOrgUnitInfo companyOrgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
		EntityViewInfo view2 = new EntityViewInfo();
		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(new FilterItemInfo("company.id", companyOrgUnitInfo.getId().toString()));
		view2.setFilter(filter2);
		paymentAccount.setEntityViewInfo(view2);
		ICellEditor f7paymentAccountEditor = new KDTDefaultCellEditor(paymentAccount);
		this.kDTable1.getColumn("gatheringAccount").setEditor(f7paymentAccountEditor);
		
		// 結算號
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.kDTable1.getColumn("settlementNumber").setEditor(txtEditor);
		
		this.kDTable1.getColumn("paymentAccount").setEditor(txtEditor);
	}
	
	/*
	 * 取得收款单的分录，以 payListId 分组
	 */
	private FDCReceiveBillEntryCollection getFdcReceiveBillEntryColl(String recId) throws BOSException
	{
		SorterItemCollection sorColl = new SorterItemCollection();
		sorColl.add(new SorterItemInfo("seq"));
		sorColl.add(new SorterItemInfo("payListId"));
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		view.getSelector().add("*");
		view.getSelector().add("settlementType.*");
		view.getSelector().add("moneyDefine.*");
		view.getSelector().add("account.*");
		view.getSelector().add("revAccountBank.*");
		view.getSelector().add("revBank.*");
		view.getSelector().add("oppSubject.*");
		
		view.setSorter(sorColl);
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.id",recId));
		return FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
	}

	/**
	 * 加载分录里面的数据
	 */
	private void loadEntryTableData() throws BOSException, EASBizException, UuidException
	{
		if (!(this.editData instanceof ReceivingBillInfo)) {
			return;
		}
		this.kDTable1.removeRows();
		ReceivingBillInfo billInfo = (ReceivingBillInfo) this.editData;
		PurchaseInfo purchase = billInfo.getFdcReceiveBill().getPurchase();

		FDCReceiveBillEntryCollection entryCollection = new FDCReceiveBillEntryCollection();
		if (billInfo.getId() != null) {
			entryCollection = this.getFdcReceiveBillEntryColl(billInfo.getId().toString());
		}

		Set payListIdSet = new HashSet();
		int tempTop = 0;
		String tempReference = "";
		// 用来存放手工生成的ID
		Map idMap = new HashMap();

		for (int i = 0; i < entryCollection.size(); i++) {
			FDCReceiveBillEntryInfo entryInfo = entryCollection.get(i);
			String payListId = entryInfo.getPayListId();

			if (payListId != null) {
				if (BOSUuid.isValid(payListId, true)) {
					Timestamp ts = this.getLastUpdateTime(payListId);
					timeMap.put(payListId, ts);
				}
			}
			String temp = payListId;

			// 创建这个 假BOSUuid 只是为了不报 ID 无效的错误
			if (!BOSUuid.isValid(payListId, true)) {
				// 如果已经生成了的话，就不需要再重新生成了
				if (idMap.get(temp) != null) {
					payListId = idMap.get(temp).toString();
				} else {
					payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
					idMap.put(temp, payListId);
				}
			}
			payListIdSet.add(payListId);

			IRow row = this.kDTable1.addRow();
			
			//融合零星收款
			if(MoneySysTypeEnum.ManageSys.equals(getSystemType()))
			{
				row.getCell("isAddNew").setValue(Boolean.valueOf(entryInfo.isIsPartial()));
				if(entryInfo.isIsPartial())
				{
					row.getCell("moneyType").getStyleAttributes().setBackground(FDCTableHelper.warnColor);
				}
			}
			row.getCell("settlementType").setValue(entryInfo.getSettlementType());
			row.getCell("id").setValue(payListId);
			row.getCell("moneyType").setValue(entryInfo.getMoneyDefine());
			row.getCell("gatheringAccount").setValue(entryInfo.getRevAccountBank());			
			if(entryInfo.getRevAccountBank()!=null && entryInfo.getRevAccountBank().getBankAccountNumber()!=null){
				row.getCell("gatheringNumber").setValue(entryInfo.getRevAccountBank().getBankAccountNumber());
			}			
			row.getCell("gatheringSubject").setValue(entryInfo.getAccount());
			
			row.getCell("oppSubject").setValue(entryInfo.getOppSubject());

			BigDecimal amount = entryInfo.getAmount();
			if (amount != null)
				amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
			if ((ReceiveBillTypeEnum.settlement.equals(billInfo.getFdcReceiveBill().getBillTypeEnum()) || ReceiveBillTypeEnum.refundment.equals(billInfo.getFdcReceiveBill().getBillTypeEnum()))
					&& amount != null) {
				row.getCell("gatheringAmount").setValue(FDCHelper.ZERO.subtract(amount));
				row.getCell("refundmentAmount").setValue(FDCHelper.ZERO.subtract(amount));
			} else {
				row.getCell("gatheringAmount").setValue(amount);
				row.getCell("refundmentAmount").setValue(amount);
			}
			row.getCell("settlementNumber").setValue(entryInfo.getSettlementNumber());
			row.getCell("paymentAccount").setValue(entryInfo.getBankNumber());
			
			//防止被红冲金额丢失
			BigDecimal counteractAmount = entryInfo.getCounteractAmount();
			row.getCell("counteractAmount").setValue(counteractAmount);
			
			

			// 进行融合处理
			// 如果是结转单，就不必进行融合处理了
			if (!ReceiveBillTypeEnum.settlement.equals(this.recBillType)  &&  !ReceiveBillTypeEnum.transfer.equals(this.recBillType)) {
				if (!tempReference.equalsIgnoreCase(temp)) {
					tempTop = i;
					tempReference = temp;
					
					//modify jetdai 2009-12-12
					//做如下判断，否则测试不下去
					if(temp==null){
						tempReference="";
					}
					//********** end ************
				} else {
					// 融合单元格
					this.kDTable1.getMergeManager().mergeBlock(tempTop, 0, i, 0);
					this.kDTable1.getMergeManager().mergeBlock(tempTop, row.getCell("appAmount").getColumnIndex(), i, row.getCell("appAmount").getColumnIndex());
					this.kDTable1.getMergeManager().mergeBlock(tempTop, row.getCell("actAmount").getColumnIndex(), i, row.getCell("actAmount").getColumnIndex());
					this.kDTable1.getMergeManager().mergeBlock(tempTop, row.getCell("canRefundmentAmount").getColumnIndex(), i, row.getCell("canRefundmentAmount").getColumnIndex());
					this.kDTable1.getMergeManager().mergeBlock(tempTop, row.getCell("hasRefundmentAmount").getColumnIndex(), i, row.getCell("hasRefundmentAmount").getColumnIndex());
					this.kDTable1.getMergeManager().mergeBlock(tempTop, row.getCell("derateAmount").getColumnIndex(), i, row.getCell("derateAmount").getColumnIndex());
				}
			}
		}

		// 取到收款单的明细，所对应的认购单的明细
		if (this.kDTable1.getRowCount() > 0) {
			SincerReceiveEntryCollection sinPayListColl = new SincerReceiveEntryCollection();			
			PurchasePayListEntryCollection payListEntryColl = new PurchasePayListEntryCollection();
			PurchaseElsePayListEntryCollection elsePayListEntryColl = new PurchaseElsePayListEntryCollection();
			TenancyRoomPayListEntryCollection tenRoomPayEntryColl = new TenancyRoomPayListEntryCollection(); 
			PPMGeneralARCollection generalColl = new PPMGeneralARCollection();
			PPMMeasureARCollection measureColl = new PPMMeasureARCollection();
			PPMTemporaryCollection temporaryColl = new PPMTemporaryCollection();
			DepositBillCollection depositColl = new DepositBillCollection();
			DepositWithdrawBillCollection withdrawColl = new DepositWithdrawBillCollection();			
			
			if(this.getSystemType().equals(MoneySysTypeEnum.SalehouseSys)) {
				sinPayListColl = this.getSincerReceiveEntryCollection(payListIdSet);
				payListEntryColl = this.getPurchasePayListEntryColl(payListIdSet);
				elsePayListEntryColl = this.getPurchaseElsePayListEntryColl(payListIdSet);
			}else if(this.getSystemType().equals(MoneySysTypeEnum.TenancySys)) {
				tenRoomPayEntryColl = this.getTenRoomPayEntryColl(payListIdSet);
			}else if(this.getSystemType().equals(MoneySysTypeEnum.ManageSys)) {
				 generalColl = this.getGeneralColl(payListIdSet);
				 measureColl = this.getMeasureColl(payListIdSet);
				 temporaryColl = this.getTemporaryColl(payListIdSet);
				 depositColl = this.getDepositColl(payListIdSet);	
				 withdrawColl = this.getWithdrawColl(payListIdSet);	
			}

			for (int i = 0; i < this.kDTable1.getRowCount(); i++) {
				IRow row = this.kDTable1.getRow(i);
				MoneyDefineInfo moneyDefine = (MoneyDefineInfo) row.getCell("moneyType").getValue();

				String payListId = (String) row.getCell("id").getValue();

				// 标示这个款项是不是 如 预收款 一样是特殊的款项
				boolean specialMoneyDebug = false;

				//找诚意认购的付款明细
				for(int j=0; j<sinPayListColl.size(); j++){
					if (sinPayListColl.get(j).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true; 

						BigDecimal apAmount = sinPayListColl.get(j).getApAmount();
						if (apAmount != null)
							apAmount = apAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

						row.getCell("appAmount").setValue(apAmount);

						BigDecimal actPayAmount = sinPayListColl.get(j).getActPayAmount();
						if (actPayAmount != null)
							actPayAmount = actPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(actPayAmount);

						BigDecimal refundmentAmount = sinPayListColl.get(j).getRefundmentAmount();
						if (refundmentAmount != null)
							refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("hasRefundmentAmount").setValue(refundmentAmount);

						BigDecimal canRefundmentAmount = sinPayListColl.get(j).getCanRefundmentAmount();
						if (canRefundmentAmount != null)
							canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

						row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);
					}
				}
				
				// 找认购单的付款明细
				for (int j = 0; j < payListEntryColl.size(); j++) {
					if (payListEntryColl.get(j).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal apAmount = payListEntryColl.get(j).getApAmount();
						if (apAmount != null)
							apAmount = apAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

						row.getCell("appAmount").setValue(apAmount);

						BigDecimal actPayAmount = payListEntryColl.get(j).getActPayAmount();
						if (actPayAmount != null)
							actPayAmount = actPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(actPayAmount);

						BigDecimal refundmentAmount = payListEntryColl.get(j).getRefundmentAmount();
						if (refundmentAmount != null)
							refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("hasRefundmentAmount").setValue(refundmentAmount);

						BigDecimal canRefundmentAmount = payListEntryColl.get(j).getCanRefundmentAmount();
						if (canRefundmentAmount != null)
							canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

						row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);
					}
				}
				// 找其他明细
				for (int k = 0; k < elsePayListEntryColl.size(); k++) {
					if (elsePayListEntryColl.get(k).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal apAmount = elsePayListEntryColl.get(k).getApAmount();
						if (apAmount != null)
							apAmount = apAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(apAmount);

						BigDecimal actPayAmount = elsePayListEntryColl.get(k).getActPayAmount();
						if (actPayAmount != null)
							actPayAmount = actPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(actPayAmount);

						BigDecimal refundmentAmount = elsePayListEntryColl.get(k).getRefundmentAmount();
						if (refundmentAmount != null)
							refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("hasRefundmentAmount").setValue(refundmentAmount);

						BigDecimal canRefundmentAmount = elsePayListEntryColl.get(k).getCanRefundmentAmount();
						if (canRefundmentAmount != null)
							canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

						row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);
					}
				}
				// 找租赁的付款明细
				for (int m = 0; m < tenRoomPayEntryColl.size(); m++) {
					if (tenRoomPayEntryColl.get(m).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal appAmount2 = tenRoomPayEntryColl.get(m).getAppAmount();
						if (appAmount2 != null)
							appAmount2 = appAmount2.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(appAmount2);

						BigDecimal actAmount2 = tenRoomPayEntryColl.get(m).getActAmount();
						if (actAmount2 != null)
							actAmount2 = actAmount2.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(actAmount2);

						BigDecimal refundmentAmount = tenRoomPayEntryColl.get(m).getLimitAmount();  //TODO  Temp modify by Jeegan***************
						if (refundmentAmount != null)
							refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("hasRefundmentAmount").setValue(refundmentAmount);

						BigDecimal canRefundmentAmount = tenRoomPayEntryColl.get(m).getCanRefundmentAmount();
						if (canRefundmentAmount != null)
							canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

						row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);
					}
				}

				for (int n = 0; n < generalColl.size(); n++) {
					if (generalColl.get(n).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal arAmout = generalColl.get(n).getArAmout();
						if (arAmout != null)
							arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(arAmout);

						BigDecimal payedAmount = generalColl.get(n).getPayedAmount();
						if (payedAmount != null)
							payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(payedAmount);
						
						BigDecimal derateAmount = generalColl.get(n).getDerateAmount();
						if(derateAmount != null)
							derateAmount = derateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("derateAmount").setValue(derateAmount);
					}
				}
				for (int t = 0; t < measureColl.size(); t++) {
					if (measureColl.get(t).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;
						BigDecimal arAmout = measureColl.get(t).getArAmout();
						if (arAmout != null)
							arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(arAmout);

						BigDecimal payedAmount = measureColl.get(t).getPayedAmount();
						if (payedAmount != null)
							payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(payedAmount);
						
						//jetdai 2009-12-04  此处在序时簿查看打开单据时有时有NULL值
						BigDecimal derateAmount = new BigDecimal(0);
						if(generalColl.get(t)!=null){
							derateAmount = generalColl.get(t).getDerateAmount();
						}
						
						if(derateAmount != null)
							derateAmount = derateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("derateAmount").setValue(derateAmount);
					}
				}
				for (int c = 0; c < temporaryColl.size(); c++) {
					if (temporaryColl.get(c).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal arAmout = temporaryColl.get(c).getArAmout();
						if (arAmout != null)
							arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(arAmout);

						BigDecimal payedAmount = temporaryColl.get(c).getPayedAmount();
						if (payedAmount != null)
							payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(payedAmount);
						
						if(generalColl!=null && generalColl.get(c)!=null){
							BigDecimal derateAmount = generalColl.get(c).getDerateAmount();
							if(derateAmount != null)
								derateAmount = derateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
							row.getCell("derateAmount").setValue(derateAmount);
						}
					}
				}
				for (int q = 0; q < depositColl.size(); q++) {
					if (depositColl.get(q).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal arAmout = depositColl.get(q).getDepositAmount();
						if (arAmout != null)
							arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(arAmout);

						BigDecimal payedAmount = depositColl.get(q).getActGatheringAmo();
						if (payedAmount != null)
							payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(payedAmount);
					}
				}
				for (int w = 0; w < withdrawColl.size(); w++) {
					if (withdrawColl.get(w).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal arAmout = withdrawColl.get(w).getDepositBill().getDepositAmount();
						if (arAmout != null)
							arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(arAmout);

						BigDecimal payedAmount = withdrawColl.get(w).getDepositBill().getActGatheringAmo();
						if (payedAmount != null)
							payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(payedAmount);

						BigDecimal refundmentAmount = withdrawColl.get(w).getActRefundmentAm();
						if (refundmentAmount != null)
							refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						else
							refundmentAmount = FDCHelper.ZERO;
						row.getCell("hasRefundmentAmount").setValue(refundmentAmount);

						BigDecimal canRefundmentAmount = withdrawColl.get(w).getReturnAmount();
						if (canRefundmentAmount != null)
							canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						else
							canRefundmentAmount = FDCHelper.ZERO;

						if (canRefundmentAmount.compareTo(refundmentAmount) > 0) {
							row.getCell("canRefundmentAmount").setValue(canRefundmentAmount.subtract(refundmentAmount));
						}
					}
				}

				// 特定款项
				if (!specialMoneyDebug && MoneySysTypeEnum.SalehouseSys.equals(getSystemType()))
				{
					BigDecimal actAmount = null;

					if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
					{
						actAmount = getActAmountByCustomerInSHE(moneyDefine, getCustomerFromView());
					}
					else
					{
						actAmount = this.getActAmountByMoneyInSHE(moneyDefine, purchase);
					}
					
					if (actAmount != null)
						actAmount = actAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
					row.getCell("actAmount").setValue(actAmount);

					BigDecimal appAmount = null;
					
					if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
					{
						
					}
					else
					{
						appAmount = this.getAppAmountByMoneyInSHE(moneyDefine, purchase);
					}
					
					
					
					row.getCell("appAmount").setValue(appAmount);

					BigDecimal hasRefundmentAmount = null;
					
					
					if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
					{
						hasRefundmentAmount = this.getHasRefundmentAmountInSHE(moneyDefine, getCustomerFromView());
					}
					else
					{
						hasRefundmentAmount = this.getHasRefundmentAmountInSHE(moneyDefine, purchase);
					}
					
					
					
					if (hasRefundmentAmount != null)
						hasRefundmentAmount = hasRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
					row.getCell("hasRefundmentAmount").setValue(hasRefundmentAmount);

					BigDecimal canRefundmentAmount = null;
					
					
					if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
					{
						canRefundmentAmount = this.getCanCounteractAmount(moneyDefine, getCustomerFromView());
					}
					else
					{
						canRefundmentAmount = this.getCanCounteractAmount(moneyDefine, purchase);
					}
					
					
					if (canRefundmentAmount != null)
						canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

					row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);
				}
			}
		}
	}
	
	/**
	 * 取得特定ID 的付款明细
	 */
	private SincerReceiveEntryCollection getSincerReceiveEntryCollection(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return SincerReceiveEntryFactory.getRemoteInstance().getSincerReceiveEntryCollection(view);		
	}
	
	/**
	 * 取得特定ID 的付款明细
	 */
	private PurchasePayListEntryCollection getPurchasePayListEntryColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);		
	}
	
	/**
	 * 取得特定ID 的 其他付款明细
	 * @throws BOSException 
	 */
	private PurchaseElsePayListEntryCollection getPurchaseElsePayListEntryColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return PurchaseElsePayListEntryFactory.getRemoteInstance().getPurchaseElsePayListEntryCollection(view);
	}
	
	/**
	 * 取得特定ID 的租赁付款明细
	 */
	private TenancyRoomPayListEntryCollection getTenRoomPayEntryColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection(view);
	}
	private PPMGeneralARCollection getGeneralColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return PPMGeneralARFactory.getRemoteInstance().getPPMGeneralARCollection(view);
	}
	
	private PPMMeasureARCollection getMeasureColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return PPMMeasureARFactory.getRemoteInstance().getPPMMeasureARCollection(view);
	}
	private DepositWithdrawBillCollection getWithdrawColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return DepositWithdrawBillFactory.getRemoteInstance().getDepositWithdrawBillCollection(view);
	}
	
	private DepositBillCollection getDepositColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return DepositBillFactory.getRemoteInstance().getDepositBillCollection(view);
	}
	
	private PPMTemporaryCollection getTemporaryColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		//*********  begin ******************
		//jetdai 2009-12-13
		//收款单 类型是  零星时，临时应收 必须加个判断 为 审批   4AUDITTED
		if(this.recBillType!=null && this.recBillType.equals(ReceiveBillTypeEnum.partial)){
			ReceivingBillInfo recBillInfo = (ReceivingBillInfo) this.editData;
			FDCReceiveBillInfo fdcRecBillInfo = recBillInfo.getFdcReceiveBill();
			if (!(recBillInfo.getBillStatus() != null && recBillInfo.getBillStatus().equals(BillStatusEnum.AUDITED))) {
				filter.getFilterItems().add(new FilterItemInfo("state","4AUDITTED",CompareType.EQUALS));
				filter.setMaskString("#0 and #1");
			}
			
			
		}
		//*********  end  **********************
		return PPMTemporaryFactory.getRemoteInstance().getPPMTemporaryCollection(view);
	}
	
	/* 这里面只考虑了认购单的收款明细和其它应收，是有漏洞的...   */
	private Timestamp getLastUpdateTime(String id) 	{
		try {
			if(PurchasePayListEntryFactory.getRemoteInstance().exists(new ObjectUuidPK(BOSUuid.read(id)))) {
				return PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryInfo("select lastUpdateTime where id = '"+id+"'").getLastUpdateTime();
			 }
			
			if(PurchaseElsePayListEntryFactory.getRemoteInstance().exists(new ObjectUuidPK(BOSUuid.read(id)))) {
				return PurchaseElsePayListEntryFactory.getRemoteInstance().getPurchaseElsePayListEntryInfo("select lastUpdateTime where id = '"+id+"'").getLastUpdateTime();
			}
		} catch (EASBizException e) {
			logger.info("通过id查找收款明细异常.id="+id);
		} catch (BOSException e) {
			logger.info("通过id查找收款明细异常.id="+id);
		} catch (UuidException e) {
			logger.info("通过id查找收款明细异常.id="+id);
		}
		return null;
	}

	/**
	 * 检验分录中的信息
	 * @throws Exception
	 */
	protected void verifyEntryTable() throws Exception
	{		
		int rowCount = this.kDTable1.getRowCount();
		
		
		String topId = "";
		for (int i = 0; i < rowCount; i++)		{
			IRow row = this.kDTable1.getRow(i);
			
			String id = (String)row.getCell("id").getValue();
			BigDecimal gatheringAmount =  this.getBigDecimalCellValue(row, "gatheringAmount");
			
			//在结算方式多选的情况下，一个款项明细会被分区多行显示 ;因而算收款金额应该统计向下相同id的行的收款合计
			for(int ii=(i+1);ii<rowCount;ii++) {
				IRow nextRow = this.kDTable1.getRow(ii);
				if(nextRow!=null){
					String nextid = (String)nextRow.getCell("id").getValue();
					if(nextid!=null && id!=null && nextid.equals(id)) {
						BigDecimal nextGaAmount =  this.getBigDecimalCellValue(nextRow, "gatheringAmount");
						gatheringAmount = gatheringAmount.add(nextGaAmount);
					}
				}
				break;
			}
			gatheringAmount = gatheringAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			if(id!=null && id.equals(topId))  continue;
			else if(id!=null)  topId = id;
			
			
			//校验在界面弹出来之后，明细数据有没有被修改过			
			if(BOSUuid.isValid(id,true)) {
				if(timeMap.get(id) != null)				{
					Timestamp  old = (Timestamp) timeMap.get(id);					
					Timestamp news = this.getLastUpdateTime(id);					
					if(news != null)					{
						if(!old.equals(news))						{
							MsgBox.showWarning("该认购单中的付款明细发生了更新操作，请重新打开收款单界面！");
							this.abort();
						}
					}
				}
			}
			
			if (!(row.getCell("moneyType").getValue() instanceof MoneyDefineInfo))	{
				MsgBox.showWarning("第  " + (i + 1) + " 行款项名称不能为空！");
				abort();
			}
			if (!(row.getCell("settlementType").getValue() instanceof SettlementTypeInfo))			{
				MsgBox.showWarning("第  " + (i + 1) + " 行结算方式不能为空！");
				abort();
			}
			if (!(row.getCell("gatheringSubject").getValue() instanceof AccountViewInfo))			{
				MsgBox.showWarning("第  " + (i + 1) + " 行收款科目不能为空！");
				abort();
			}
			if (!(row.getCell("oppSubject").getValue() instanceof AccountViewInfo))			{
				MsgBox.showWarning("第  " + (i + 1) + " 行对方科目不能为空！");
				abort();
			}
			
			BigDecimal appAmount = this.getBigDecimalCellValue(row, "appAmount");
			appAmount = appAmount.abs().setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal actAmount = this.getBigDecimalCellValue(row, "actAmount");
				actAmount = actAmount.abs().setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal derateAmount = this.getBigDecimalCellValue(row, "derateAmount");	//减免金额
				derateAmount = derateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal refundmentAmount = this.getBigDecimalCellValue(row, "refundmentAmount");
				refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);			
			BigDecimal canRefundmentAmount = this.getBigDecimalCellValue(row, "canRefundmentAmount");
				canRefundmentAmount = canRefundmentAmount.abs().setScale(2, BigDecimal.ROUND_HALF_UP);
				
			MoneyDefineInfo money = (MoneyDefineInfo)row.getCell("moneyType").getValue();
			if(MoneyTypeEnum.CompensateAmount.equals(money.getMoneyType()))			{
				BigDecimal tempApp = this.getBigDecimalCellValue(row, "appAmount");
				if(tempApp.compareTo(FDCHelper.ZERO) >= 0)		{
					if(gatheringAmount.compareTo(FDCHelper.ZERO) < 0)		{
						MsgBox.showWarning("补差金额为正数，收款金额也必须为正数！");
						this.abort();
					}
				}else if(tempApp.compareTo(FDCHelper.ZERO) < 0)		{
					if(gatheringAmount.compareTo(FDCHelper.ZERO) >= 0)			{
						MsgBox.showWarning("补差金额为负数，收款金额也必须为负数！");
						this.abort();
					}
				}
			}
			
			//退款的校验和收款的校验应该分开来。
			boolean hasErrorInfo = false;
			if(ReceiveBillTypeEnum.refundment.equals(this.recBillType))		{  //退款情况
				if(FDCHelper.ZERO.compareTo(refundmentAmount) >= 0)		{
					MsgBox.showWarning("第 " + (i + 1) +" 行退款金额必须大于零！" );
					this.abort();
				}
				if(OprtState.EDIT.equalsIgnoreCase(this.getOprtState()))		{
					BigDecimal oldRefundmentAmount = (BigDecimal)this.oldRefundmentAmountMap.get(id);					
					if(refundmentAmount.subtract(oldRefundmentAmount).compareTo(canRefundmentAmount)>0)
						hasErrorInfo = true;
				}else if(OprtState.ADDNEW.equalsIgnoreCase(this.getOprtState()))	{
					if(refundmentAmount.compareTo(canRefundmentAmount)>0)
						hasErrorInfo = true;
				}
				if(hasErrorInfo) {
					MsgBox.showWarning("款项 "+ money +" ，退款金额不能大于可退金额！");
					this.abort();
				}
			}else{				//收款情况
				if(!MoneyTypeEnum.CompensateAmount.equals(money.getMoneyType()) && FDCHelper.ZERO.compareTo(gatheringAmount) >= 0){
					MsgBox.showWarning("第  " + (i + 1) + " 行收款金额必须大于零！");
					abort();
				}
				
				//售楼的预订金和物业的预收款，均没有应收.也不用控制实收<应收
				if((MoneySysTypeEnum.SalehouseSys.equals(getSystemType()) && !MoneyTypeEnum.PreconcertMoney.equals(money.getMoneyType()) ) 
						||	(MoneySysTypeEnum.ManageSys.equals(getSystemType()) && !MoneyTypeEnum.PreMoney.equals(money.getMoneyType())))	{				
					//区分新增，修改状态下的校验
					if(OprtState.EDIT.equalsIgnoreCase(this.getOprtState()))  {					
	                    BigDecimal oldGatheringAmount = (BigDecimal)this.oldGatheringAmountMap.get(id);
	                    
	                    //融合零星收款
	                    if(oldGatheringAmount == null)
	                    {
	                    	oldGatheringAmount = FDCHelper.ZERO;
	                    }
							if ((gatheringAmount.abs().add(actAmount.abs().subtract(oldGatheringAmount.abs()))).compareTo(appAmount.abs().subtract(derateAmount.abs())) > 0)
								hasErrorInfo = true;
					}else if(OprtState.ADDNEW.equalsIgnoreCase(this.getOprtState()))	{
							if ((gatheringAmount.abs().add(actAmount.abs())).compareTo(appAmount.abs().subtract(derateAmount.abs())) > 0)
								hasErrorInfo = true;
					}
					if(hasErrorInfo) {
						MsgBox.showWarning("款项 "+ money +" ，收款金额+已收金额，不能大于应收金额！");
						this.abort();
					}
				}
			}
			
		}
		
		//（收款或转款）且售楼系统 且 收款对象为房间 时 校验收款明细
		if((ReceiveBillTypeEnum.gathering.equals(this.recBillType)  ||  ReceiveBillTypeEnum.transfer.equals(this.recBillType))
				&& MoneySysTypeEnum.SalehouseSys.equals(this.getSystemType())&& GatheringEnum.SaleRoom.equals(this.comboGathering.getSelectedItem()))		{
			this.verifyPurchasePayListEntry();
		}
	}
	

	/**
	 * 获得某个款项它的可红冲金额
	 * @param money
	 * @param purchase
	 * @return
	 */
	private BigDecimal getCanCounteractAmount(MoneyDefineInfo money,CustomerInfo customer)
	{
		if(money == null || customer == null)
			return FDCHelper.ZERO;

		
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("head");
		view.getSelector().add("head.id");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("customer",customer.getId().toString()));
		
		CustomerEntryCollection customerColl = null;
		try
		{
			customerColl = CustomerEntryFactory.getRemoteInstance().getCustomerEntryCollection(view);
		} catch (BOSException e1)
		{
			super.handUIExceptionAndAbort(e1);
		}
		
		Set fdcBillIdSet = new HashSet();
		
		for(int i = 0; i < customerColl.size(); i ++)
		{
			if(customerColl.get(i) != null && customerColl.get(i).getHead() != null && customerColl.get(i).getHead().getId() != null)
			{
				fdcBillIdSet.add(customerColl.get(i).getHead().getId().toString());
			}
		}
		
		//若付款明细里面都没有相应的款项
		view = new EntityViewInfo();
	    filter = new FilterInfo();
		view.setFilter(filter);

		
	filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		
		if(fdcBillIdSet != null && fdcBillIdSet.size() > 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id", fdcBillIdSet,CompareType.INCLUDE));
		}
		else
		{
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id",null));
		}
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.GATHERING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.TRANSFER_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));
		
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.gathering",GatheringEnum.CUSTOMERREV_VALUE));
		
		filter.setMaskString("#0 and #1 and (#2 or #3) and #4 and #5");
		
		FDCReceiveBillEntryCollection recBillEntryColl = null;
		try
		{
			recBillEntryColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIExceptionAndAbort(e);
		}
		BigDecimal sumAmount = FDCHelper.ZERO;
		for (int i = 0; i < recBillEntryColl.size(); i++)
		{
			FDCReceiveBillEntryInfo info = recBillEntryColl.get(i);
			BigDecimal amount = info.getCanCounteractAmount();
			if(amount == null)
			{
				amount = FDCHelper.ZERO;
			}
			sumAmount = sumAmount.add(amount);
		}
		if(sumAmount != null)
			sumAmount = sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		return sumAmount;
	}
	
	
	
	/**
	 * 获得某个款项它的可红冲金额
	 * @param money
	 * @param purchase
	 * @return
	 */
	private BigDecimal getCanCounteractAmount(MoneyDefineInfo money,PurchaseInfo purchase)
	{
		if(money == null || purchase == null)
			return FDCHelper.ZERO;

		//若付款明细里面都没有相应的款项
		EntityViewInfo view = new EntityViewInfo();
	    FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", purchase.getId().toString()));
		
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));
		
		FDCReceiveBillEntryCollection recBillEntryColl = null;
		try
		{
			recBillEntryColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIExceptionAndAbort(e);
		}
		BigDecimal sumAmount = FDCHelper.ZERO;
		for (int i = 0; i < recBillEntryColl.size(); i++)
		{
			FDCReceiveBillEntryInfo info = recBillEntryColl.get(i);
			BigDecimal amount = info.getCanCounteractAmount();
			if(amount == null)
			{
				amount = FDCHelper.ZERO;
			}
			sumAmount = sumAmount.add(amount);
		}
		if(sumAmount != null)
			sumAmount = sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		return sumAmount;
	}
	
	private BigDecimal getHasRefundmentAmountInSHE(MoneyDefineInfo money,CustomerInfo customer)
	{
		if(money == null || customer == null)
			return FDCHelper.ZERO;
		
		
		
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("head");
		view.getSelector().add("head.id");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("customer",customer.getId().toString()));
		
		CustomerEntryCollection customerColl = null;
		try
		{
			customerColl = CustomerEntryFactory.getRemoteInstance().getCustomerEntryCollection(view);
		} catch (BOSException e1)
		{
			super.handUIExceptionAndAbort(e1);
		}
		
		Set fdcBillIdSet = new HashSet();
		
		for(int i = 0; i < customerColl.size(); i ++)
		{
			if(customerColl.get(i) != null && customerColl.get(i).getHead() != null && customerColl.get(i).getHead().getId() != null)
			{
				fdcBillIdSet.add(customerColl.get(i).getHead().getId().toString());
			}
		}
		
		
		
		
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.setFilter(filter);
		

		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		
		if(fdcBillIdSet != null && fdcBillIdSet.size() > 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id", fdcBillIdSet,CompareType.INCLUDE));
		}
		else
		{
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id",null));
		}
		
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.gathering",GatheringEnum.CUSTOMERREV_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.REFUNDMENT_VALUE));
		
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));

		FDCReceiveBillEntryCollection recBillEntryColl = null;
		try
		{
			recBillEntryColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIExceptionAndAbort(e);
		}
		BigDecimal sumAmount = FDCHelper.ZERO;
		for (int i = 0; i < recBillEntryColl.size(); i++)
		{
			FDCReceiveBillEntryInfo info = recBillEntryColl.get(i);
			BigDecimal amount = info.getAmount();
			if(amount == null)
			{
				amount = FDCHelper.ZERO;
			}
			sumAmount = sumAmount.add(amount);
		}
		if(sumAmount != null)
			sumAmount = sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		else
			sumAmount = FDCHelper.ZERO;
		
		if(sumAmount.compareTo(FDCHelper.ZERO) < 0)
			sumAmount = FDCHelper.ZERO.subtract(sumAmount);
		//显示正值
		return sumAmount;
	}
	
	
	private BigDecimal getHasRefundmentAmountInSHE(MoneyDefineInfo money,PurchaseInfo purchase)
	{
		if(money == null || purchase == null)
			return FDCHelper.ZERO;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", purchase.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.REFUNDMENT_VALUE));
		
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));

		FDCReceiveBillEntryCollection recBillEntryColl = null;
		try
		{
			recBillEntryColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIExceptionAndAbort(e);
		}
		BigDecimal sumAmount = FDCHelper.ZERO;
		for (int i = 0; i < recBillEntryColl.size(); i++)
		{
			FDCReceiveBillEntryInfo info = recBillEntryColl.get(i);
			BigDecimal amount = info.getAmount();
			if(amount == null)
			{
				amount = FDCHelper.ZERO;
			}
			sumAmount = sumAmount.add(amount);
		}
		if(sumAmount != null)
			sumAmount = sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		else
			sumAmount = FDCHelper.ZERO;
		
		if(sumAmount.compareTo(FDCHelper.ZERO) < 0)
			sumAmount = FDCHelper.ZERO.subtract(sumAmount);
		//显示正值
		return sumAmount;
	}
	
	private BigDecimal getAppAmountByMoneyInSHE(MoneyDefineInfo money,PurchaseInfo purchase)
	{
		BigDecimal returnValue = FDCHelper.ZERO;
		if(money == null || purchase == null)
			return FDCHelper.ZERO;
		
		if(MoneyTypeEnum.CompensateAmount.equals(money.getMoneyType()))
		{
			RoomInfo room = purchase.getRoom();
			
			RoomAreaCompensateInfo roomAreaCompensationInfo = SHEHelper.getRoomAreaCompensation(room);
			returnValue = roomAreaCompensationInfo.getCompensateAmount();
		}
		else if(MoneyTypeEnum.PreconcertMoney.equals(money.getMoneyType())	)
		{
			returnValue = purchase.getPrePurchaseAmount();
		}
		
		if(returnValue == null)
			returnValue = FDCHelper.ZERO;
		else
			returnValue = returnValue.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		return returnValue;
	}
	
	/**
	 * 获得售楼系统中某个特定款项，它的实收金额
	 * @param money
	 * @return
	 * @throws BOSException 
	 */
	private BigDecimal getActAmountByMoneyInSHE(MoneyDefineInfo money,PurchaseInfo purchase) throws BOSException
	{
		if(money == null || purchase == null)
			return FDCHelper.ZERO;

		//若付款明细里面都没有相应的款项
		EntityViewInfo view = new EntityViewInfo();
	    FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", purchase.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.GATHERING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.TRANSFER_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));
		
		filter.setMaskString("#0 and #1 and (#2 or #3) and #4");
		
		FDCReceiveBillEntryCollection recBillEntryColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
		
		BigDecimal sumAmount = FDCHelper.ZERO;
		for (int i = 0; i < recBillEntryColl.size(); i++)
		{
			FDCReceiveBillEntryInfo info = recBillEntryColl.get(i);
			BigDecimal amount = info.getAmount();
			if(amount == null)
			{
				amount = FDCHelper.ZERO;
			}
			sumAmount = sumAmount.add(amount);
		}
		if(sumAmount != null)
			sumAmount = sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		return sumAmount;
	}

	/**
	 * 根据款项类型 和 款项科目对照表。带出科目设置到 对方科目列上
	 * 并根据对照表设置的是否允许修改，设置是否锁定对方科目列
	 * */
	protected void setAccountByMoneyDefine(MoneyDefineInfo moneyDefine, IRow row) {
		if(moneyDefine == null  ||  row == null  ||  moneyAccountMapping == null){
			return;
		}
		MoneySubjectContrastInfo moneySubject = (MoneySubjectContrastInfo) moneyAccountMapping.get(moneyDefine.getId().toString());
		if(moneySubject == null){
			return;
		}
		
		AccountViewInfo account = moneySubject.getAccountView();
		boolean isChanged = moneySubject.isIsChanged();
		if(account != null){
			//带出对照表中设置的 科目 设置到对方科目上
			ICell cell = row.getCell("oppSubject");
			cell.setValue(account);
			if(!isChanged){
				cell.getStyleAttributes().setLocked(true);
			}
		}
	}
	
	private void addRowByPayListInSHE(IObjectCollection payColl, Map settlementMap, Map counteractMap) {
		for (int i = 0; i < payColl.size(); i++) {
			IRow row = this.kDTable1.addRow();
			ISHEPayListEntryInfo info = (ISHEPayListEntryInfo) payColl.getObject(i);

			timeMap.put(info.getId().toString(), info.getLastUpdateTime());
			
			MoneyDefineInfo moneyDefine = info.getMoneyDefine();
			//根据款项自动带出科目
			setAccountByMoneyDefine(moneyDefine, row);
			
			row.getCell("moneyType").setValue(info.getMoneyDefine());
			BigDecimal apAmount = info.getApAmount();
			if (apAmount != null)
				apAmount = apAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			else
				apAmount = FDCHelper.ZERO;
			row.getCell("appAmount").setValue(apAmount);

			BigDecimal actPayAmount = info.getActPayAmount();
			if (actPayAmount != null)
				actPayAmount = actPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			else
				actPayAmount = FDCHelper.ZERO;
			row.getCell("actAmount").setValue(actPayAmount);

			BigDecimal refundmentAmount = info.getRefundmentAmount();
			if (refundmentAmount != null)
				refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			else
				refundmentAmount = FDCHelper.ZERO;
			row.getCell("hasRefundmentAmount").setValue(refundmentAmount);

			BigDecimal canRefundmentAmount = info.getCanRefundmentAmount();
			if (canRefundmentAmount != null)
				canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			else
				canRefundmentAmount = FDCHelper.ZERO;
			row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);

			String id = info.getId().toString();
			row.getCell("id").setValue(id);
			if (settlementMap.get(id) != null) {
				if (settlementMap.get(id) instanceof Object[]) {
					Object[] o = (Object[]) settlementMap.get(id);
					if (o != null) {
						for (int j = 0; j < o.length; j++) {
							if (j == 0) {
								row.getCell("settlementType").setValue(o[j]);

								// 如果只有一行的話，就帶出收款金額或者退款金額
								if (o.length == 1) {
									BigDecimal gatheringAmount = apAmount.subtract(actPayAmount);

									row.getCell("refundmentAmount").setValue(canRefundmentAmount);
									row.getCell("gatheringAmount").setValue(gatheringAmount);
									updateAmountTotal();
								}
								
								setGatheringAmountOfCounteract(id, row, (SettlementTypeInfo)o[j], counteractMap);
							} else {
								IRow tempRow = this.kDTable1.addRow();
								tempRow.getCell("id").setValue(id);
								tempRow.getCell("moneyType").setValue(moneyDefine);
								tempRow.getCell("settlementType").setValue(o[j]);
								//根据款项自动带出科目
								setAccountByMoneyDefine(moneyDefine, tempRow);
								
								setGatheringAmountOfCounteract(id, tempRow, (SettlementTypeInfo)o[j], counteractMap);
							}
						}
						// 融合单元格
						this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(), 0, row.getRowIndex() + o.length - 1, 0);

						this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(), row.getCell("appAmount").getColumnIndex(), row.getRowIndex() + o.length - 1,
								row.getCell("appAmount").getColumnIndex());

						this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(), row.getCell("actAmount").getColumnIndex(), row.getRowIndex() + o.length - 1,
								row.getCell("actAmount").getColumnIndex());

						this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(), row.getCell("hasRefundmentAmount").getColumnIndex(), row.getRowIndex() + o.length - 1,
								row.getCell("hasRefundmentAmount").getColumnIndex());

						this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(), row.getCell("canRefundmentAmount").getColumnIndex(), row.getRowIndex() + o.length - 1,
								row.getCell("canRefundmentAmount").getColumnIndex());
					}
				} else if (settlementMap.get(id) instanceof Object) {
					Object o = settlementMap.get(id);
					if (o != null) {
						row.getCell("settlementType").setValue(o);
					}
					setGatheringAmountOfCounteract(id, row, (SettlementTypeInfo)o, counteractMap);
				}
			}
		}
	}

	private void setGatheringAmountOfCounteract(String id, IRow row, SettlementTypeInfo settlementType, Map counteractMap) {
		// 红冲金额
		if (this.isSettlement && counteractMap != null) {
			Map counteractSettlementMap = (Map) counteractMap.get(id);
			if(counteractSettlementMap == null){
				logger.error("逻辑错误！");
				return;
			}
			
			Object[] objs = (Object[]) counteractSettlementMap.get(settlementType.getId().toString());
			if(objs == null){
				return;
			}
			
			row.getCell("gatheringAmount").setValue((BigDecimal)objs[0]);
			Set set = (Set) objs[1];
			if(set == null){
				return;
			}
			
			AccountViewInfo oppSubject = (AccountViewInfo) set.iterator().next();			
			row.getCell("gatheringSubject").setValue(oppSubject);
			if(oppSubject != null){
				row.getCell("gatheringSubject").getStyleAttributes().setLocked(true);
			}
			
			updateAmountTotal();
		}
	}

	//缓存款项 科目 对应表
	protected Map moneyAccountMapping = null;
	//初始化款项 科目 对应表
	protected void initMoneyAccountMapping() throws BOSException{
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
	
	protected void verifyChoose() {}
	
	
	protected void btnChoose_actionPerformed(ActionEvent e) throws Exception
	{
		this.verifyChoose();
		this.kDTable1.removeRows();
		
		if(moneyAccountMapping == null){
			initMoneyAccountMapping();
		}
		
		GatheringEnum selectedItem = (GatheringEnum) this.comboGathering.getSelectedItem();
		
		UIContext uiContext = new UIContext(ui);
		
		uiContext.put(KEY_MONEYTSYSTYPE, this.getSystemType());
		uiContext.put(KEY_BILLTYPE,this.recBillType);
		uiContext.put(KEY_GATHERING, selectedItem);			
		
		PurchaseInfo purchase = (PurchaseInfo) this.f7Purchase.getValue();
		RoomInfo room = (RoomInfo) this.f7Room.getValue();
		SincerityPurchaseInfo sinPurchase = (SincerityPurchaseInfo) this.f7SinPurchase.getValue();
		
		uiContext.put(Key_SysCustomer, getCustomerFromView());
		uiContext.put("purchase", purchase);
		uiContext.put("sinPurchase", sinPurchase);
		uiContext.put(Key_Room, room);
		
//		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SelectPaymentListUI.class.getName(), uiContext, null,OprtState.VIEW);
		uiWindow.show();
		
		if (uiWindow.getUIObject().getUIContext().get(KEY_OPT) == null ||
				!((Boolean) uiWindow.getUIObject().getUIContext().get(KEY_OPT)).booleanValue()){
			return;
		}
		
		Map settlementMap = (HashMap) uiWindow.getUIObject().getUIContext().get(KEY_SettlementType);
		Boolean isSettlement = (Boolean) uiWindow.getUIObject().getUIContext().get(Key_IsSettlement);
		if(isSettlement == null)
			isSettlement = Boolean.FALSE;
		//设置全局参数
		this.isSettlement = isSettlement.booleanValue();
		
		if (isSettlement.booleanValue()) {
			this.cbIsSettlement.setSelected(true);
			this.hongChongFdcRecEntryColl = (FDCReceiveBillEntryCollection) uiWindow.getUIObject().getUIContext().get(KEY_HongChongRecEntryColl);
		} else {
			this.cbIsSettlement.setSelected(false);
		}
		
		// 添加表格行
		if (this.isSettlement) {
			this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setLocked(true);
		} else {
			this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setLocked(false);
		}
		
		//以下分系统添加付款明细到列表中
		//获得选择的收款明细记录
		PurchasePayListEntryCollection payListColl = (PurchasePayListEntryCollection) uiWindow.getUIObject().getUIContext().get(KEY_PurchasePayListColl);
		PurchaseElsePayListEntryCollection elsePayListColl = (PurchaseElsePayListEntryCollection) uiWindow.getUIObject().getUIContext().get(KEY_PurchaseElsePayListColl);
		Collection coll = (Collection) uiWindow.getUIObject().getUIContext().get(Key_SpecifyPayListColl);
		SincerReceiveEntryCollection sinPurPayLists = (SincerReceiveEntryCollection) uiWindow.getUIObject().getUIContext().get(KEY_SinPurchasePayListColl);	
		
		Map counteractMapForSHE = (HashMap) uiWindow.getUIObject().getUIContext().get(KEY_CounteractAmount_FOR_SHE);
		
		if(GatheringEnum.SinPurchase.equals(this.comboGathering.getSelectedItem())){
			this.addRowByPayListInSHE(sinPurPayLists, settlementMap, counteractMapForSHE);
		}else if(GatheringEnum.SaleRoom.equals(this.comboGathering.getSelectedItem())){
			this.addRowBySpecifyPayListCollInSHE(coll,settlementMap,purchase, counteractMapForSHE);
			
			this.addRowByPayListInSHE(payListColl, settlementMap, counteractMapForSHE);
			this.addRowByPayListInSHE(elsePayListColl, settlementMap, counteractMapForSHE);
		}//支持一下 暂定款的退款
		else if(GatheringEnum.CustomerRev.equals(this.comboGathering.getSelectedItem())){
			this.addRowBySpecifyPayListCollInSHE(coll,settlementMap,purchase, counteractMapForSHE);
		}
		
		else{
			logger.warn("暂不支持的收款对象");
		}
	}
	
	protected CustomerInfo getCustomerFromView() {
		// 这样写是因为客户那边是多选的，多选、单选时候所得到的结果是不一样的
		if (this.f7Customer.getValue() instanceof Object[]) {
			Object[] o = (Object[]) this.f7Customer.getValue();
			if (o != null && o.length > 0) {
				if (o[0] instanceof CustomerInfo) {
					return (CustomerInfo) o[0];
				}
			}
		} else {
			return (CustomerInfo) this.f7Customer.getValue();
		}
		return null;
	}
	
	protected void btnDelete_actionPerformed(ActionEvent e) throws Exception
	{
		int activeRowIndex = this.kDTable1.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex == -1)
		{
			activeRowIndex = this.kDTable1.getRowCount();
		}
		this.kDTable1.removeRow(activeRowIndex);
	}

	public void setOprtState(String oprtType)
	{
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState))
		{
			this.kDTable1.getStyleAttributes().setLocked(false);
		} else if (STATUS_EDIT.equals(this.oprtState))
		{
			this.kDTable1.getStyleAttributes().setLocked(false);
		} else if (STATUS_VIEW.equals(this.oprtState))
		{
			this.kDTable1.getStyleAttributes().setLocked(true);
		} else if (STATUS_FINDVIEW.equals(this.oprtState))
		{
			this.kDTable1.getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * 是否新增显示
	 * 
	 * @param caller
	 * @param orgId
	 * @return
	 */
	protected   boolean isCodingRuleAddView(IObjectValue caller, String orgId) {
		ICodingRuleManager iCodingRuleManager;
		boolean isAddView = false;
		try {
			iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if(SysContext.getSysContext().getCurrentFIUnit() != null){
				isAddView = iCodingRuleManager.isAddView(caller, SysContext
						.getSysContext().getCurrentFIUnit().getId().toString());	
			}
		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}
		return isAddView;
	}
	
	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException
	{
		String currentOrgId = "";
		if(SysContext.getSysContext().getCurrentFIUnit() != null){
			currentOrgId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();	
		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		/*
		 * 2008-09-27如果不是新增状态，直接返回 然后分别判断成本中心和当前组织是否存在编码规则
		 */
		if (!STATUS_ADDNEW.equals(this.oprtState))
		{
			return;
		}
		boolean isExist = true;
		if (currentOrgId.length() == 0
				|| !iCodingRuleManager.isExist(editData, currentOrgId))
		{
			isExist = false;
		}
		
		if (isExist)
		{
			boolean isAddView = this.isCodingRuleAddView(editData,currentOrgId);
			if (isAddView)
			{
				getNumberByCodingRule(editData, currentOrgId);
			} else
			{
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId))
				{ // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId))
					{
						// 启用了断号支持功能,同时启用了用户选择断号功能
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager.isDHExist(editData, currentOrgId))
						{
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null)
						{
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}
			setNumberTextEnabled();
		}
	}
	protected KDTable getDetailTable()
	{
		return null;
	}
	
	/**
	 * 通过所选择的资金系统来控制界面上一些显示
	 * @param moneySysTypeEnum
	 * @param itemEvent
	 * @author laiquan_luo
	 */
	protected void  changeUIByMoneySys(MoneySysTypeEnum moneySysTypeEnum,ItemEvent itemEvent)
	{
		//售楼系统   暂时把物业系统和售楼系统共用 一个房间选择界面
		if(MoneySysTypeEnum.SalehouseSys.equals(moneySysTypeEnum))
		{
			f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,buildingInfo,buildUnit,MoneySysTypeEnum.SalehouseSys, null));
			this.contPurchase.setVisible(true);
			if(this.f7Purchase.getValue() == null)
			{
				this.f7Purchase.setEnabled(false);
			}
			this.lcTenancyContract.setVisible(false);
		//	this.btnChooseRoom.setVisible(true);
		//	this.contRoom.setVisible(true);
			this.contSalesman.setBoundLabelText("销售顾问");
			
			if(this.kDTable1.getColumn("derateAmount") != null)
			this.kDTable1.getColumn("derateAmount").getStyleAttributes().setHided(true);
		}
	}
	/**
	 * 界面载入的时候，选择哪个系统
	 */
	private void setMoneySysValueAsOnload(MoneySysTypeEnum moneySysTypeEnum)
	{
		//默认售楼系统
		if(moneySysTypeEnum == null)
		{
			this.comboBelongSys.setSelectedItem(MoneySysTypeEnum.SalehouseSys);
			this.changeUIByMoneySys(MoneySysTypeEnum.SalehouseSys,null);
		}
		else
		{
			this.comboBelongSys.setSelectedItem(moneySysTypeEnum);
			this.changeUIByMoneySys(moneySysTypeEnum,null);
		}
	}
	/**
	 * 界面载入的时候，选择哪个收款对象
	 */
	private void setGatheringObjectAsOnload(GatheringObjectEnum gatheringObjectEnum)
	{
		//默认是房间
		if(this.gatheringObject == null || GatheringObjectEnum.room.equals(this.gatheringObject))
		{
			this.lcRoom.setVisible(true);
			this.lcAccessorialResource.setVisible(false);
		}
		else if(GatheringObjectEnum.accessorialResource.equals(this.gatheringObject))
		{
			this.lcAccessorialResource.setVisible(true);
			this.lcRoom.setVisible(false);
		}
		else
		{
			MsgBox.showInfo("暂时没有该收款对象的操作！");
			this.abort();
		}
	}
	/**
	 * 当所属系统发生改变的时候，将界面上的某些值清掉
	 *
	 */
	private void resetValueAsMoneySysTypeChange()
	{
		this.f7Room.setValue(null);
		this.f7Purchase.setValue(null);
		this.f7TenancyContract.setValue(null);
		this.txtReceiveAmount.setValue(null);
		this.f7Customer.setValue(null);
		this.f7Salesman.setValue(null);
		this.f7SellOrder.setValue(null);
		this.f7SellProject.setValue(null);
		
		setGatheringItemsBySys((MoneySysTypeEnum) this.comboBelongSys.getSelectedItem());
		
		setControlByGathering((GatheringEnum) this.comboGathering.getSelectedItem());
	}
	protected void setGatheringItemsBySys(MoneySysTypeEnum currentSys) {
		this.comboGathering.removeAllItems();
		if(MoneySysTypeEnum.SalehouseSys.equals(currentSys)){
			this.comboGathering.addItem(GatheringEnum.SaleRoom);
			this.comboGathering.addItem(GatheringEnum.SinPurchase);
			this.comboGathering.addItem(GatheringEnum.CustomerRev);
		}else if(MoneySysTypeEnum.TenancySys.equals(currentSys)){
			this.comboGathering.addItem(GatheringEnum.TenRoom);
			this.comboGathering.addItem(GatheringEnum.TenAttach);
			
			this.comboGathering.addItem(GatheringEnum.ObligatedRoom);
			this.comboGathering.addItem(GatheringEnum.ObligatedAttach);
		}else if(MoneySysTypeEnum.ManageSys.equals(currentSys)){
			this.comboGathering.addItem(GatheringEnum.WuYeRoomFee);
		}
	}

	

	
	protected Set getQueryBillStates() {
		//退款和收款，所取得合同不一样 
		Set tempSet = new HashSet();
		if(ReceiveBillTypeEnum.refundment.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			//终止和作废合同
			tempSet.add(TenancyBillStateEnum.BLANKOUT_VALUE);
			tempSet.add(TenancyBillStateEnum.EXPIRATION_VALUE);
		}
		else if(this.getUIContext().get(KEY_BILLTYPE) == null ||ReceiveBillTypeEnum.gathering.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			tempSet.add(TenancyBillStateEnum.AUDITED_VALUE);
//			tempSet.add(TenancyBillStateEnum.DEPOSITREVED_VALUE);
			tempSet.add(TenancyBillStateEnum.PARTEXECUTED_VALUE);
			tempSet.add(TenancyBillStateEnum.EXECUTING_VALUE);
			tempSet.add(TenancyBillStateEnum.EXPIRATION_VALUE);
			
			tempSet.add(TenancyBillStateEnum.CONTINUETENANCYING_VALUE);
			tempSet.add(TenancyBillStateEnum.REJIGGERTENANCYING_VALUE);
			tempSet.add(TenancyBillStateEnum.CHANGENAMING_VALUE);
			tempSet.add(TenancyBillStateEnum.QUITTENANCYING_VALUE);
		}
		return tempSet;
	}


	
	/**
	 * 所属系统下拉框
	 */
	protected void comboBelongSys_itemStateChanged(ItemEvent e) throws Exception
	{
//		防止界面重新 onload，加的一个判断
		if (!e.getItem().equals(comboBelongSys.getSelectedItem()))
		{
			if(this.clickComboSysNum > 0)
			{
				this.resetValueAsMoneySysTypeChange();
			}
			this.changeUIByMoneySys((MoneySysTypeEnum) comboBelongSys.getSelectedItem(), e);			
		}
	}
	protected void comboBelongSys_actionPerformed(ActionEvent e) throws Exception
	{
		this.clickComboSysNum ++;
	}
	/**
	 * 将分录的每一行收款统计
	 */
	protected void updateAmountTotal()
	{
		if(this.kDTable1.getRowCount()<1)
			return;
		BigDecimal receiveAmount = FDCHelper.ZERO;
		
		for(int i=0;i<this.kDTable1.getRowCount();i++)
		{
			IRow row = this.kDTable1.getRow(i);
			if(ReceiveBillTypeEnum.refundment.equals(this.recBillType))
			{
				receiveAmount = receiveAmount.add(row.getCell("refundmentAmount").getValue()==null? FDCHelper.ZERO:new BigDecimal(row.getCell("refundmentAmount").getValue().toString()));
			}
			else
			{
				receiveAmount = receiveAmount.add(row.getCell("gatheringAmount").getValue()==null? FDCHelper.ZERO:new BigDecimal(row.getCell("gatheringAmount").getValue().toString()));
			}
		}
		this.txtReceiveAmount.setValue(receiveAmount);
	}
	protected void kDTable1_activeCellChanged(KDTActiveCellEvent e) throws Exception
	{
//		设置激活行全局变量
		currentActiveRowIndex = e.getRowIndex();
	}
	/**
	 * 收款对象的改变事件
	 */
	protected void ComboGatheringOjbect_itemStateChanged(ItemEvent e) throws Exception
	{
		GatheringObjectEnum gatheringObjectEnum = (GatheringObjectEnum) this.ComboGatheringOjbect.getSelectedItem();
	}
	

	protected void f7SellProject_dataChanged(DataChangeEvent e) throws Exception
	{
	}
	
	protected void comboGathering_itemStateChanged(ItemEvent e) throws Exception {
		if(isLoadField)
			return;
		
		if (!e.getItem().equals(this.comboGathering.getSelectedItem())){
			GatheringEnum selectedItem = (GatheringEnum) this.comboGathering.getSelectedItem();
			
			this.gatheringEnum = selectedItem;
			
			setControlByGathering(selectedItem);
			
			this.f7Room.setValue(null);
			this.f7Purchase.setValue(null);
			this.f7SinPurchase.setValue(null);
		}
	}
	
	protected void setControlByGathering(GatheringEnum gathering) {
		if(GatheringEnum.SinPurchase.equals(gathering)){
			setControlVisible(true, false, false, false, true, false);
			this.f7Room.setRequired(false);
		}else if(GatheringEnum.SaleRoom.equals(gathering)){
			setControlVisible(true, false, true, false, false, false);
			this.f7Room.setRequired(true);
		}else if(GatheringEnum.ObligatedRoom.equals(gathering)){
			setControlVisible(true, false, false, false, false, true);
			this.f7Room.setRequired(true);
		}else if(GatheringEnum.ObligatedAttach.equals(gathering)){
			setControlVisible(false, true, false, false, false, true);
			this.f7Room.setRequired(false);
		}else if(GatheringEnum.TenRoom.equals(gathering)){
			setControlVisible(true, false, false, true, false, false);
			this.f7Room.setRequired(true);
		}else if(GatheringEnum.TenAttach.equals(gathering)){
			setControlVisible(false, true, false, true, false, false);
			this.f7Room.setRequired(false);
		}else if(GatheringEnum.WuYeRoomFee.equals(gathering)){
			setControlVisible(true, false, false, false, false, false);
			this.f7Room.setRequired(true);
		}
		else if(GatheringEnum.CustomerRev.equals(gathering))
		{
			setControlVisible(true, false, false, false, false, false);
			this.f7Room.setRequired(false);
		}
	}
	
	protected void setControlVisible(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6) {
		this.lcRoom.setVisible(b1);
		this.lcAccessorialResource.setVisible(b2);
		
		this.contPurchase.setVisible(b3);
		this.lcTenancyContract.setVisible(b4);
		
		this.contSinPurchase.setVisible(b5);
		this.contSinObligate.setVisible(b6);
		
		if(b1)
			this.ComboGatheringOjbect.setSelectedItem(GatheringObjectEnum.room);
		else if(b2)
			this.ComboGatheringOjbect.setSelectedItem(GatheringObjectEnum.accessorialResource);
	}
	

	

	
	protected void f7SinObligate_dataChanged(DataChangeEvent e) throws Exception {
		//TODO 
	}
	
	
		
	
	
	/**
	 * 获得客户对象的某个款项的收款
	 * @param money
	 * @param customer
	 * @return
	 * @throws BOSException
	 */
	private BigDecimal getActAmountByCustomerInSHE(MoneyDefineInfo money,CustomerInfo customer) throws BOSException
	{
		if(money == null || customer == null)
			return FDCHelper.ZERO;
		
		BigDecimal sumAmount = FDCHelper.ZERO;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select sum(case when FdcEntry.famount is null then 0 else FdcEntry.famount end) sumAmount ");
		builder.appendSql("from T_SHE_FDCReceiveBillEntry FdcEntry ");
		builder.appendSql("left outer join T_CAS_ReceivingBill CasBill on CasBill.fid = FdcEntry.FReceivingBillID ");
		builder.appendSql("left outer join T_SHE_FDCReceiveBill FdcBill on CasBill.FFdcReceiveBillID = FdcBill.fid ");
		//builder.appendSql("left outer join T_SHE_Moneydefine Define on FdcEntry.FmoneyDefineID = Define.fid ");
		builder.appendSql("where CasBill.fbillStatus != '"+BillStatusEnum.SAVE_VALUE+"' ");
		builder.appendSql("and CasBill.FFdcReceiveBillID in (select fheadId from T_SHE_CustomerEntry where FCustomerId = '"+customer.getId()+"') ");
		builder.appendSql("and FdcBill.fbillTypeEnum in ('"+ReceiveBillTypeEnum.GATHERING_VALUE+"','"+ReceiveBillTypeEnum.TRANSFER_VALUE+"') ");
		builder.appendSql("and FdcEntry.FmoneyDefineID = '"+money.getId()+"'");
		builder.appendSql("and FdcBill.Fgathering = '"+GatheringEnum.CUSTOMERREV_VALUE+"'");
		IRowSet retSet = builder.executeQuery();
		try {
			if(retSet.next()) {
				sumAmount = retSet.getBigDecimal("sumAmount");
				if(sumAmount==null) sumAmount = FDCHelper.ZERO;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		return sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	
	
	
	/**
	 * 获得认购单的过滤属性
	 */
	protected SelectorItemCollection getPurchaseSelectorItemColl()
	{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("payType.*");
		sels.add("payType.loanLoanData.*");
		sels.add("dealCurrency.*");
		sels.add("customerInfo.*");
		sels.add("customerInfo.customer.sysCustomer.*");
		sels.add("sincerityPurchase.*");
		sels.add("purchaseCurrency.*");
		sels.add("salesman.name");
		sels.add("room.*");
		sels.add("room.building.name");
		sels.add("room.building.number");
		
		sels.add("payListEntry.*");
		sels.add("payListEntry.moneyDefine.*");
		sels.add("payListEntry.moneyDefine.revBillType.*");
		sels.add("payListEntry.currency.*");
		sels.add("elsePayListEntry.*");
		sels.add("elsePayListEntry.moneyDefine.*");
		sels.add("elsePayListEntry.moneyDefine.revBillType.*");
		sels.add("elsePayListEntry.currency.*");

		return sels;
	}
	
	
	/**
	 * 设置认购单的过滤属性
	 * @author laiquan_luo
	 */
	protected void setF7PurchaseSelectorItemCollAsOnload()
	{
		SelectorItemCollection sels = getPurchaseSelectorItemColl();
		this.f7Purchase.setSelectorCollection(sels);
	}
	
	protected SincerityPurchaseInfo getSinPurchase(String id) throws EASBizException, BOSException{
		if(id == null){
			return null;
		}
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("name");
		sels.add("number");
		sels.add("room.id");
		sels.add("room.name");
		sels.add("room.number");
		sels.add("customer.id");
		sels.add("customer.name");
		sels.add("customer.number");
		sels.add("salesman.id");
		sels.add("salesman.name");
		sels.add("salesman.number");
		sels.add("sellProject.id");
		sels.add("sellProject.name");
		sels.add("sellProject.number");
		sels.add("sellOrder.id");
		sels.add("sellOrder.name");
		sels.add("sellOrder.number");
		sels.add("room.building.sellProject");
		sels.add("customer.sysCustomer.id");
		sels.add("customer.sysCustomer.name");
		sels.add("customer.sysCustomer.number");
		sels.add("sincerPriceEntrys.id");
		sels.add("sincerPriceEntrys.moneyDefine.id");
		sels.add("sincerPriceEntrys.moneyDefine.name");		
		return SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(id), sels);
	}	
	
	/**
	 * 装载操作记录到页面 add by jiyu_guan
	 */
	private void loadFeildsForRecord() {
		boolean isVisble = false;
		if (!STATUS_ADDNEW.equals(getOprtState())) {
			try {
				IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory
						.getRemoteInstance();
				FDCReceiveBillRecordCollection col = (FDCReceiveBillRecordCollection) facade
						.getRecord(editData.getId());
				if (col != null && col.size() > 0) {
					isVisble = true;
					tblRecord.checkParsed();
					tblRecord.removeRows();

					FDCReceiveBillRecordInfo record;
					IRow row;
					for (int i = 0; i < col.size(); i++) {
						record = col.get(i);
						if (record != null) {
							row = tblRecord.addRow();
							row.getCell("id").setValue(record.getId());
							row.getCell("recordType").setValue(
									record.getRecordType());
							row.getCell("content")
									.setValue(record.getContent());
							row.getCell("operatingUser").setValue(
									record.getOperatingUser().getName());
							row.getCell("operatingUser").setUserObject(
									record.getOperatingUser());
							row.getCell("operatingDate").setValue(
									record.getOperatingDate());
							row.getCell("description").setValue(
									record.getDescription());
						}
					}
				}
			} catch (BOSException e) {
				handUIException(e);
			}
		}
		// 设置操作记录页签是否显示
		kDTabbedPane1.getComponent(0).setVisible(isVisble);
	}
	
	/**
	 * 当保存修改时，收据改变需要即时更新操作记录 add by jiyu_guan
	 */
	private void updateRecord(String receivingBillId) {
		String curReceiptNum = null;
		if (this.f7Cheque.isVisible()) {
			ChequeInfo chequeInfo = (ChequeInfo) this.f7Cheque.getValue();
			curReceiptNum = chequeInfo == null ? null : chequeInfo.getNumber();
		} else {
			curReceiptNum = this.txtReceiptNumber.getText();
		}

		try {
			IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory
					.getRemoteInstance();
			int billType = 0;
			String pk = receivingBillId;
			RecordTypeEnum recordType = null;
			String content = null;
			String description = null;
			if (oldReceiptNum == null || oldReceiptNum.equals("")) {
				// 开收据
				if (curReceiptNum != null && !curReceiptNum.equals("")) {
					recordType = RecordTypeEnum.MakeOutReceipt;
					content = "开收据" + curReceiptNum;
				} else {
					return;
				}
			} else {
				// 回收收据
				if (curReceiptNum == null || curReceiptNum.equals("")) {
					recordType = RecordTypeEnum.RetakeReceipt;
					content = "回收收据" + curReceiptNum;
				}
				// 换收据
				else if (!oldReceiptNum.equals(curReceiptNum)) {
					recordType = RecordTypeEnum.ChangeReceipt;
					content = "收据" + oldReceiptNum + "换为收据" + curReceiptNum;
				}
			}

			facade.updateRecord(billType, pk, recordType, content, description);
		} catch (BOSException e) {
			handUIException(e);
		}
	}
	
	

    //重写该方法，简单处理收款单编码设置为新增显示时中断的问题。
	//其实应该在FDCBillEditUI中修改，但
    protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
        try {
            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
            if (orgId == null || orgId.trim().length() == 0)
            {
//              当前用户所属组织不存在时，缺省实现是用集团的
                 orgId = OrgConstants.DEF_CU_ID;
            }
            if (iCodingRuleManager.isExist(caller, orgId))
            {
                String number = "";
                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
                { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
                    if (iCodingRuleManager.isUserSelect(caller, orgId))
                    {
                        // 启用了断号支持功能,同时启用了用户选择断号功能
                        // KDBizPromptBox pb = new KDBizPromptBox();
                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
                                caller, orgId, null, null);
                        // pb.setSelector(intermilNOF7);
                        // 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
                        Object object = null;
                        if (iCodingRuleManager.isDHExist(caller, orgId))
                        {
                            intermilNOF7.show();
                            object = intermilNOF7.getData();
                        }
                        if (object != null)
                        {
                            number = object.toString();
                        }
                        else
                        {
                            // 如果没有使用用户选择功能,直接getNumber用于保存,为什么不是read?是因为使用用户选择功能也是get!
                            number = iCodingRuleManager
                                    .getNumber(caller, orgId);
                        }
                    }
                    else
                    {
                        // 只启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
                        number = iCodingRuleManager.readNumber(caller, orgId);
                    }
                }
                else
                {
                    number =  prepareNumberForAddnew(iCodingRuleManager, editData, orgId, orgId, null); 
                    	//iCodingRuleManager.getNumber(caller, orgId);
                }

                // 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
                prepareNumber(caller, number);
                if (iCodingRuleManager.isModifiable(caller, orgId))
                {
                    //如果启用了用户可修改
                    setNumberTextEnabled();
                }
                return;
            }
        } catch (Exception err) {
            //获取编码规则出错，现可以手工输入编码！
            handleCodingRuleError(err);

            //把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
            setNumberTextEnabled();
        }

        //把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
        setNumberTextEnabled();
    }
    
	//编码规则中启用了“新增显示”,必须检验是否已经重复
	protected String prepareNumberForAddnew (ICodingRuleManager iCodingRuleManager,ObjectBaseInfo info,String orgId,String costerId,String bindingProperty)throws Exception{
		String number = null;
		FilterInfo filter = null;
		int i=0;
		do {
			//如果编码重复重新取编码
			if(bindingProperty!=null){
				number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
			}else{
				number = iCodingRuleManager.getNumber(editData, orgId);
			}
			
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("number", number));				
//			filter.getFilterItems()
//					.add(new FilterItemInfo("orgUnit.id", costerId));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
			}
			i++;
		}while (((IObjectBase)getBizInterface()).exists(filter) && i<1000);
		
		return number;
	}
	
	
	
	private BigDecimal getBigDecimalCellValue(IRow row,String cellName){
		BigDecimal retValue = FDCHelper.ZERO;
		if(row!=null && row.getCell(cellName).getValue()!=null){
			if(row.getCell(cellName).getValue() instanceof BigDecimal)
				retValue = (BigDecimal)row.getCell(cellName).getValue();
		}
		return retValue;
	}
	
	
    
}
