/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.core.fi.gl.KDSpinnerCellEditor;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.aimcost.ContractPhaseEnum;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigCollection;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigFactory;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.FieldTypeEnum;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEComHelper;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.DepositDealBillFactory;
import com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum;
import com.kingdee.eas.fdc.tenancy.IDealAmountInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.OtherBillEntryInfo;
import com.kingdee.eas.fdc.tenancy.OtherBillFactory;
import com.kingdee.eas.fdc.tenancy.OtherBillInfo;
import com.kingdee.eas.fdc.tenancy.RentCountTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class OtherBillEditUI extends AbstractOtherBillEditUI implements TenancyBillConstant 
{
    private static final Logger logger = CoreUIObject.getLogger(OtherBillEditUI.class);
    private boolean isFreeContract=false;
    public OtherBillEditUI() throws Exception
    {
        super();
    }
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
		sels.add("orgUnit.*");
		sels.add("tenancyAdviser.*");
		
		sels.add("tenancyRoomList.*");
		sels.add("tenancyRoomList.room.floor");
		sels.add("tenancyRoomList.room.isForPPM");
		sels.add("tenancyRoomList.room.number");

		sels.add("tenancyRoomList.room.building.name");
		sels.add("tenancyRoomList.room.building.number");
		sels.add("tenancyRoomList.room.roomModel.name");
		sels.add("tenancyRoomList.room.roomModel.number");
		sels.add("tenancyRoomList.room.buildingProperty.name");
		sels.add("tenancyRoomList.room.direction.number");
		sels.add("tenancyRoomList.room.direction.name");
		sels.add("tenancyRoomList.room.buildingProperty.number");
		sels.add("tenancyRoomList.room.building.sellProject.name");
		sels.add("tenancyRoomList.room.building.sellProject.number");
		sels.add("tenancyRoomList.roomPayList.*");
		sels.add("tenancyRoomList.roomPayList.currency.name");
		sels.add("tenancyRoomList.roomPayList.currency.number");

		sels.add("tenancyRoomList.roomPayList.moneyDefine.name");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.number");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.moneyType");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.sysType");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.isEnabled");

		sels.add("tenancyRoomList.dealAmounts.*");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.name");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.number");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.moneyType");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.sysType");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.isEnabled");
		TenancyBillInfo tenBill;
		try {
			tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(this.editData.getTenancyBill().getId()),sels);
			this.editData.setTenancyBill(tenBill);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		TenancyRoomEntryCollection tenancyRooms = this.editData.getTenancyBill().getTenancyRoomList();
		if(tenancyRooms.size()>0){
			TenancyRoomEntryInfo roomEntry=tenancyRooms.get(0);
			roomEntry.getPayList().clear();
			TenBillOtherPayCollection col=this.editData.getPayEntry();
			CRMHelper.sortCollection(col, "seq", true);
			for(int i=0;i<col.size();i++){
				TenancyRoomPayListEntryInfo entry=new TenancyRoomPayListEntryInfo();
				TenBillOtherPayInfo otherEntry=col.get(i);
				entry.setSeq(otherEntry.getSeq());
				entry.setAppDate(otherEntry.getAppDate());
				entry.setStartDate(otherEntry.getStartDate());
				entry.setEndDate(otherEntry.getEndDate());
				entry.setAppAmount(otherEntry.getAppAmount());
				entry.setLeaseSeq(otherEntry.getLeaseSeq());
				entry.setMoneyDefine(otherEntry.getMoneyDefine());
				
				entry.setActRevAmount(otherEntry.getActRevAmount());
				entry.setActRevDate(otherEntry.getActRevDate());
				
				roomEntry.getRoomPayList().add(entry);
			}
		}
			
		List leaseList = getLeaseListFromView();
		// 获得租期数据		
		if (leaseList == null) {
			leaseList = new ArrayList();
		}
		updatePayList(tenancyRooms, new TenAttachResourceEntryCollection(), leaseList);// 生成付款明细列表
		
		CRMClientHelper.getFootRow(this.kdtEntry, new String[]{"price","workload","amount"});
		
		setOprtState(this.oprtState);
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	public void storeFields()
    {
		TenancyRoomEntryCollection tenancyRooms = this.editData.getTenancyBill().getTenancyRoomList();
		this.editData.getPayEntry().clear();
		if(tenancyRooms.size()>0){
			CRMHelper.sortCollection(tenancyRooms, "seq", true);
			for(int i=0;i<tenancyRooms.get(0).getRoomPayList().size();i++){
				TenancyRoomPayListEntryInfo entry=tenancyRooms.get(0).getRoomPayList().get(i);
				TenBillOtherPayInfo otherEntry=new TenBillOtherPayInfo();
				otherEntry.setSeq(entry.getSeq());
				otherEntry.setAppDate(entry.getAppDate());
				otherEntry.setStartDate(entry.getStartDate());
				otherEntry.setEndDate(entry.getEndDate());
				otherEntry.setAppAmount(entry.getAppAmount());
				otherEntry.setLeaseSeq(entry.getLeaseSeq());
				otherEntry.setMoneyDefine(entry.getMoneyDefine());
				
				this.editData.getPayEntry().add(otherEntry);
			}
		}
        super.storeFields();
    }
	private ChangeListener spinLeaseTimeChangeListener = null;
	protected void attachListeners() {
		this.spinLeaseTime.addChangeListener(spinLeaseTimeChangeListener);

		this.addDataChangeListener(this.pkStartDate);
		this.addDataChangeListener(this.pkEndDate);
	}
	protected void detachListeners() {
		this.spinLeaseTime.removeChangeListener(spinLeaseTimeChangeListener);
		this.removeDataChangeListener(this.pkStartDate);
		this.removeDataChangeListener(this.pkEndDate);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return OtherBillFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		OtherBillInfo info=new OtherBillInfo();
		TenancyBillInfo ten = (TenancyBillInfo) this.getUIContext().get("tenancy");
		info.setId(BOSUuid.create(info.getBOSType()));
		info.setTenancyBill(ten);
		info.setOrgUnit(ten.getOrgUnit());
		info.setStartDate(ten.getStartDate());
		info.setEndDate(ten.getEndDate());
		info.setLeaseTime(ten.getLeaseTime());
		return info;
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("payEntry.*");
		sels.add("payEntry.moneyDefine.*");
		sels.add("state");
		sels.add("orgUnit.*");
		return sels;
	}
	public void onLoad() throws Exception {
		
		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 100000, 1);
		this.spinLeaseTime.setModel(model);

		if (spinLeaseTimeChangeListener == null) {
			spinLeaseTimeChangeListener = new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					try {
						spinLeaseTime_stateChanged(e);
					} catch (Exception exc) {
						handUIException(exc);
					} finally {
					}
				}
			};
			this.spinLeaseTime.addChangeListener(spinLeaseTimeChangeListener);
		}
		
		this.menuTable1.setVisible(false);
		this.actionAddNew.setVisible(false);
		super.onLoad();
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();
		
		this.actionAddLine.setVisible(true);
		this.actionInsertLine.setVisible(true);
		this.actionRemoveLine.setVisible(true);
		this.actionAttachment.setVisible(true);
		
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)contEntry.add(this.actionAddLine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionInsertLine);
		btnInsertRowinfo.setText("插入行");
		btnInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRemoveLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.kdtEntry.checkParsed();
		this.kdtEntry.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.TENANCYSYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.LATEFEE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.FITMENTAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ELSEAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.REPLACEFEE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.BREACHFEE_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5 or #6)");
		view.setFilter(filter);
		this.kdtEntry.getColumn("moneyDefine").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery", view));

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setPrecision(2);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.kdtEntry.getColumn("amount").setEditor(numberEditor);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("price").setEditor(numberEditor);
		this.kdtEntry.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("price").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("workload").setEditor(numberEditor);
		this.kdtEntry.getColumn("workload").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("workload").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("amount").setRequired(true);
        this.kdtEntry.getColumn("moneyDefine").setRequired(true);

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.kdtEntry.getColumn("description").setEditor(txtEditor);
		
		this.actionAuditResult.setVisible(true);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		
		FDCClientUtils.setRespDeptF7(this.prmtDept,null);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
		}
	}
	protected IObjectValue createNewDetailData(KDTable table) {
		OtherBillEntryInfo entry=new OtherBillEntryInfo();
		return entry;
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		setAuditButtonStatus(STATUS_VIEW);
		this.actionSave.setEnabled(false);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		}
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		setAuditButtonStatus(this.getOprtState());
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		isFreeContract=false;
		if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("moneyDefine")
				||this.kdtEntry.getColumnKey(e.getColIndex()).equals("amount")){
			updatePayListInfo();
		}
		CRMClientHelper.getFootRow(this.kdtEntry, new String[]{"price","workload","amount"});
	}
	protected void pkStartDate_dataChanged(DataChangeEvent e) throws Exception {
		Date startDate = (Date) this.pkStartDate.getValue();
		Date tenStartDate = (Date) this.editData.getTenancyBill().getStartDate();
		
		if(startDate!=null&&tenStartDate!=null){
			startDate = FDCDateHelper.getDayBegin(startDate);
			tenStartDate = FDCDateHelper.getDayBegin(tenStartDate);
			if (tenStartDate.after(startDate)) {
				FDCMsgBox.showWarning(this, "开始日期不能早于租赁合同开始日期！");
				this.removeDataChangeListener(this.pkStartDate);
				this.pkStartDate.setValue(e.getOldValue());
				this.addDataChangeListener(this.pkStartDate);
				return;
			}
		}
		updatePayListInfo();
	}
	protected void pkEndDate_dataChanged(DataChangeEvent e) throws Exception {
		Date endDate = (Date) this.pkEndDate.getValue();
		Date tenEndDate = (Date) this.editData.getTenancyBill().getEndDate();
		
		if(endDate!=null&&tenEndDate!=null){
			endDate = FDCDateHelper.getDayBegin(endDate);
			tenEndDate = FDCDateHelper.getDayBegin(tenEndDate);
			if (endDate.after(tenEndDate)) {
				FDCMsgBox.showWarning(this, "结束日期不能迟于租赁合同结束日期！");
				this.removeDataChangeListener(this.pkEndDate);
				this.pkEndDate.setValue(e.getOldValue());
				this.addDataChangeListener(this.pkEndDate);
				return;
			}
		}
		updatePayListInfo();
	}
	private void spinLeaseTime_stateChanged(ChangeEvent e) throws Exception {
		updatePayListInfo();
	}
	public void updatePayListInfo() throws BOSException
	{		
		RentCountTypeEnum rentCountType = this.editData.getTenancyBill().getRentCountType();
		int daysPerYear = this.editData.getTenancyBill().getDaysPerYear();
		boolean isToInteger = this.editData.getTenancyBill().isIsAutoToInteger();
		ToIntegerTypeEnum toIntegerType = this.editData.getTenancyBill().getToIntegerType();
		DigitEnum digit = this.editData.getTenancyBill().getDigit();
		//租金设置 周期性费用设置
		boolean isToIntegerFee = this.editData.getTenancyBill().isIsAutoToIntegerFee();
		ToIntegerTypeEnum toIntegerTypeFee = this.editData.getTenancyBill().getToIntegetTypeFee();
		DigitEnum digitFee = (DigitEnum) this.editData.getTenancyBill().getDigitFee();
		
		TenancyRoomEntryCollection tenancyRooms = this.editData.getTenancyBill().getTenancyRoomList();
		for(int i=0;i<tenancyRooms.size();i++){
			TenancyRoomEntryInfo entry=tenancyRooms.get(i);
			entry.getDealAmounts().clear();
			entry.getPayList().clear();
			for(int j=0;j<this.kdtEntry.getRowCount();j++){
				IRow row=this.kdtEntry.getRow(j);
				if(row.getCell("moneyDefine").getValue()==null) continue;
				DealAmountEntryInfo dealEntry=new DealAmountEntryInfo();
				dealEntry.setMoneyDefine((MoneyDefineInfo) row.getCell("moneyDefine").getValue());
				dealEntry.setStartDate((Date) this.pkStartDate.getValue());
				dealEntry.setEndDate((Date) this.pkEndDate.getValue());
				dealEntry.setAmount((BigDecimal) row.getCell("amount").getValue());
				dealEntry.setRentType(RentTypeEnum.RentByMonth);
				
				entry.getDealAmounts().add(dealEntry);
			}
		}
		List leaseList = getLeaseListFromView();
		// 获得租期数据		
		if (leaseList == null) {
			leaseList = new ArrayList();
		}
		if(!isFreeContract && (this.getOprtState().equals("ADDNEW") || this.getOprtState().equals("EDIT")))
		{
			fillTenRoomPayList(tenancyRooms, leaseList, new RentFreeEntryCollection(), rentCountType, daysPerYear, isToInteger, toIntegerType, digit,isToIntegerFee,toIntegerTypeFee,digitFee  );
		}else
		{				
			fillFreeConTenRoomPayList(tenancyRooms);
		}
		updatePayList(tenancyRooms, new TenAttachResourceEntryCollection(), leaseList);
	}
	protected void fillFreeConTenRoomPayList(TenancyRoomEntryCollection tenancyRooms)
	{
		if (tenancyRooms == null  ||  tenancyRooms.isEmpty()) {
			return;
		}
		// 先获得所有应付金额的列
		List appPayColKeys = getAppPayColKeys();
		BigDecimal oneEntryTotalRent = null;
		for(int i=0;i<tenancyRooms.size();i++)
		{
			 oneEntryTotalRent = FDCHelper.ZERO;
			
			TenancyRoomEntryInfo tenEntry = (TenancyRoomEntryInfo) tenancyRooms.getObject(i);
			TenancyRoomPayListEntryCollection newPayList = new TenancyRoomPayListEntryCollection();
			for(int j=0;j<tblPayList.getRowCount();j++)
			{
				IRow row = this.tblPayList.getRow(j);
				if(row.getUserObject()!=null)
				{
					ITenancyPayListInfo payListInfo =(ITenancyPayListInfo)row.getUserObject();
					Date appDate = (Date) row.getCell(C_PAYS_APP_PAY_DATE).getValue();
					if(appDate==null)return;
					appDate = FDCDateHelper.getDayBegin(appDate);
					Date startDate = (Date) row.getCell(C_PAYS_START_DATE).getValue();
					startDate = FDCDateHelper.getDayBegin(startDate);
					Date endDate = (Date) row.getCell(C_PAYS_END_DATE).getValue();
					endDate = FDCDateHelper.getDayBegin(endDate);
					int leaseSeq = payListInfo.getLeaseSeq();
					int seq = payListInfo.getSeq();
					MoneyDefineInfo money = payListInfo.getMoneyDefine();
					for (int m = 0; m < appPayColKeys.size(); m++) {
						String key = (String) appPayColKeys.get(m);
						ICell cell = row.getCell(key);
						BigDecimal appAmount = (BigDecimal) cell.getValue();
						TenancyRoomPayListEntryInfo tenPayInfo = (TenancyRoomPayListEntryInfo)row.getCell(key).getUserObject();
						TenancyRoomPayListEntryInfo tay  = new TenancyRoomPayListEntryInfo();
						if(tenEntry.getRoom()!=null && tenPayInfo!=null)
						{
							if(tenEntry.getRoom().getId().toString().equals(tenPayInfo.getTenRoom().getRoom().getId().toString()))
							{
								//如果是租金，需要汇总 xin_wang 2010.11.24
								if(money!=null){
									if(MoneyTypeEnum.RentAmount.equals(money.getMoneyType())){
										oneEntryTotalRent =oneEntryTotalRent.add(appAmount);
									}
								}
								tay.setTenRoom(tenPayInfo.getTenRoom());
								tay.setAppAmount(appAmount);
								tay.setAppPayDate(appDate);
								tay.setStartDate(startDate);
								tay.setEndDate(endDate);
								tay.setSeq(seq);
								tay.setMoneyDefine(money);
								tay.setLeaseSeq(leaseSeq);
								newPayList.add(tay);
							}
						}						
					}					
				}
			}
			tenEntry.getPayList().clear();
			tenEntry.getPayList().addObjectCollection(newPayList);
			tenEntry.setTotalRent(oneEntryTotalRent);
		}
	}
	private void fillTenRoomPayList(TenancyRoomEntryCollection tenancyRooms, List leaseList, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear,
			boolean isToInteger, ToIntegerTypeEnum toIntegerType, DigitEnum digit,boolean isToIntegerFee, ToIntegerTypeEnum toIntegerTypeFee, DigitEnum digitFee) throws BOSException {
		try {//添加两个参数  收租周期、第二期应收日期
			fillTenEntryPayList(tenancyRooms, TenancyRoomPayListEntryCollection.class, TenancyRoomPayListEntryInfo.class, null, null, leaseList, rentFrees, rentCountType, daysPerYear, 
					isToInteger, toIntegerType, digit,isToIntegerFee, toIntegerTypeFee, digitFee, this.editData.getTenancyBill().getChargeDateType(), this.editData.getTenancyBill().getChargeOffsetDays(),this.editData.getTenancyBill().getFristRevDate(),this.editData.getTenancyBill().getFixedDateFromMonth(),this.spinLeaseTime.getIntegerVlaue().intValue());
		} catch (InstantiationException e) {
			throw new BOSException(e);
		} catch (IllegalAccessException e) {
			throw new BOSException(e);
		}
	}
	public void fillTenEntryPayList(IObjectCollection tenEntrys, Class payColClazz, Class tenPayClazz, 
			MoneyDefineInfo depositMoney, MoneyDefineInfo rentMoney, 
			List leaseList, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear, 
			boolean isToInteger, ToIntegerTypeEnum toIntegerType, DigitEnum digit, boolean isToIntegerFee, ToIntegerTypeEnum toIntegerTypeFee, DigitEnum digitFee,ChargeDateTypeEnum chargeDateType, int chargeOffsetDays
			,Date pkTenancyDate,Date dPickFromMonth,int spinLeaseTime) throws BOSException, InstantiationException, IllegalAccessException {
		if (tenEntrys == null  ||  tenEntrys.isEmpty()) {
			return;
		}
		for (int i = 0; i < tenEntrys.size(); i++) {
			ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) tenEntrys.getObject(i);
			
			IObjectCollection dealAmounts = tenEntry.getDealAmounts_();
			IObjectCollection payList = tenEntry.getPayList();
			
			IObjectCollection newPayList = (IObjectCollection) payColClazz.newInstance();
			// 统计一个房间不包含押金的所有租金总合,提交时各付款明细的总合应和该值相等. 
			BigDecimal oneEntryTotalRent = FDCHelper.ZERO;
			int seq = 0;
			for (int j = 0; j < leaseList.size(); j++) {
				List monthes = (List) leaseList.get(j);// 月份集合

				Date[] firstMonth = (Date[]) monthes.get(0);// 该租期的首月
				Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);// 该租期的最后1月
				
				//如果存在周期性费用，则需要增加周期性费用的应收明细
				MoneyDefineInfo money = new MoneyDefineInfo();
				for(int k=0; k<dealAmounts.size(); k++){
					IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(k);
					if(dealAmount.getAmount() != null 
							//&&  dealAmount.getAmount().compareTo(FDCHelper.ZERO) > 0 除掉这个判断，不然会造成生成的应收明细错位 xin_wang 2010.11.22
							){
						if(dealAmount.getMoneyDefine().equals(money))
						{
							continue;
						}else
						{
							money = dealAmount.getMoneyDefine();
						}
						ITenancyPayListInfo pPay = (ITenancyPayListInfo) tenPayClazz.newInstance();
						BigDecimal amount = getRentOfThisLease(monthes, dealAmount.getMoneyDefine(), dealAmounts, rentFrees, rentCountType, daysPerYear);
						//取整 eric_wang 2010.08.25
						amount = SHEComHelper.getAmountAfterToInteger(amount, isToIntegerFee, toIntegerTypeFee, digitFee);
//						BigDecimal amount = TenancyHelper.getRentBetweenDate(firstMonth[0], lastMonth[1], dealAmount.getRentType(), dealAmount.getAmount());
						//add by pu_zhang 2010-10-20
						if(j==0){// 如果是第一租期，填写租赁合同日期
							setRoomPayParam(pPay, dealAmount.getMoneyDefine(), amount, j + 1, seq++, firstMonth[0], lastMonth[1], 0, chargeDateType, chargeOffsetDays,pkTenancyDate,dPickFromMonth,spinLeaseTime,j);
						}else{// 如果是非第一租期，租赁合同日期为空
							setRoomPayParam(pPay, dealAmount.getMoneyDefine(), amount, j + 1, seq++, firstMonth[0], lastMonth[1], 0, chargeDateType, chargeOffsetDays,null,dPickFromMonth,spinLeaseTime,j);
						}
						newPayList.addObject(pPay);	
					}
				}
			}
			payList.clear();
			payList.addObjectCollection(newPayList);
			tenEntry.setTotalRent(oneEntryTotalRent);
		}
	}
	private void setRoomPayParam(ITenancyPayListInfo payEntry, MoneyDefineInfo moneyDefine, BigDecimal leaseRent, 
			int leaseSeq, int seq, Date startDate, Date endDate, int freeDaysOfThisLease, ChargeDateTypeEnum chargeDateType, int chargeOffsetDays,Date pkTenancyDate,Date dPickFromMonth,int spinLeaseTime,int j) throws BOSException {
		// roomPayEntry.setCurrency(currency);
		// roomPayEntry.setMoneyDefine(moneyDefine);
		payEntry.setAppAmount(leaseRent);
		payEntry.setLeaseSeq(leaseSeq);
		payEntry.setSeq(seq);
		payEntry.setStartDate(startDate);
		payEntry.setEndDate(endDate);
		//该字段现在无用了。
//		roomPayEntry.setFreeDays(freeDaysOfThisLease);

		payEntry.setMoneyDefine(moneyDefine);

		// 设置应收日期
		Date appPayDate = null;
		if(pkTenancyDate!=null){//第一期设置的时候租赁合同的业务日期（应收日期为租赁合同的业务日期）
			appPayDate=pkTenancyDate;
		}else{//非第一期,按照偏移去设置应收日期
			if (ChargeDateTypeEnum.BeforeStartDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, -chargeOffsetDays);
			} else if (ChargeDateTypeEnum.AfterStartDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, chargeOffsetDays);
			} else if (ChargeDateTypeEnum.BeforeEndDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, -chargeOffsetDays);
			} else if (ChargeDateTypeEnum.AfterEndDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, chargeOffsetDays);
			} else if (ChargeDateTypeEnum.FixedDate.equals(chargeDateType)&&dPickFromMonth!=null) {
				appPayDate = TenancyHelper.addCalendar(dPickFromMonth, Calendar.MONTH, spinLeaseTime*(j-1));
			} else {
				appPayDate = startDate;
			}
		}
		payEntry.setAppPayDate(appPayDate);
	}
	private BigDecimal getRentOfThisLease(List monthes, MoneyDefineInfo rentMoney, IObjectCollection dealAmounts, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear) throws BOSException {
		Date[] firstMonth = (Date[]) monthes.get(0);
		Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);
		return TenancyHelper.getRentBetweenDate(rentMoney, firstMonth[0], lastMonth[1], dealAmounts, rentFrees, rentCountType, daysPerYear);
	}
	private List getAppPayColKeys() {
		List appPayColKeys = new ArrayList();
		for (int i = 0; i < this.tblPayList.getColumnCount(); i++) {
			IColumn col = this.tblPayList.getColumn(i);
			if (col.getKey().endsWith(POSTFIX_C_PAYS_APP_AMOUNT)) {// TODO
				// 这里将配套资源的应付也算上了
				appPayColKeys.add(col.getKey());
			}
		}
		return appPayColKeys;
	}
	private List getLeaseListFromView() {
		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkEndDate.getValue();
		FirstLeaseTypeEnum firstLeaseType = FirstLeaseTypeEnum.WholeFirstLease;
		Date firstLeaseEndDate = (Date) this.pkStartDate.getValue();
		int leaseTime = this.spinLeaseTime.getIntegerVlaue().intValue();
		return TenancyHelper.getLeaseList(startDate, endDate, firstLeaseType, firstLeaseEndDate, leaseTime);
	}
	private void updatePayList(TenancyRoomEntryCollection tenancyRooms, TenAttachResourceEntryCollection tenAttachReses, List leaseList) {
		updatePayListColumn(tenancyRooms, tenAttachReses);

		IRow headRow = this.tblPayList.addHeadRow();
		headRow.getCell(C_PAYS_MONEY_DEFINE).setValue("款项类型");
		headRow.getCell(C_PAYS_LEASE_SEQ).setValue("租期序号");
		headRow.getCell(C_PAYS_START_DATE).setValue("起始日期");
		headRow.getCell(C_PAYS_END_DATE).setValue("结束日期");
		headRow.getCell(C_PAYS_APP_PAY_DATE).setValue("应收日期");

		for (int i = 0; i < tenancyRooms.size(); i++) {
			String roomNum = tenancyRooms.get(i).getRoom().getName();
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_APP_AMOUNT).setValue(roomNum);
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_ACT_AMOUNT).setValue(roomNum);
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_ACT_PAY_DATE).setValue(roomNum);
		}
		headRow.getCell(C_PAYS_TOTAL_APP_PAY).setValue("应收合计");
		headRow.getCell(C_PAYS_TOTAL_ACT_PAY).setValue("实收合计");

		headRow = this.tblPayList.addHeadRow();
		headRow.getCell(C_PAYS_MONEY_DEFINE).setValue("款项类型");
		headRow.getCell(C_PAYS_LEASE_SEQ).setValue("租期序号");
		headRow.getCell(C_PAYS_START_DATE).setValue("起始日期");
		headRow.getCell(C_PAYS_END_DATE).setValue("结束日期");
		headRow.getCell(C_PAYS_APP_PAY_DATE).setValue("应收日期");

		for (int i = 0; i < tenancyRooms.size(); i++) {
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_APP_AMOUNT).setValue("应收金额");
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_ACT_AMOUNT).setValue("实收金额");
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_ACT_PAY_DATE).setValue("实收日期");
		}
		headRow.getCell(C_PAYS_TOTAL_APP_PAY).setValue("应收合计");
		headRow.getCell(C_PAYS_TOTAL_ACT_PAY).setValue("实收合计");

		tblPayList.getHeadMergeManager().mergeBlock(0, 0, 1, 7 + (tenancyRooms.size() + tenAttachReses.size()) * 3 - 1, KDTMergeManager.FREE_MERGE);

		//		if (leaseList == null || leaseList.isEmpty()) {
		//			return;
		//		}
		if (tenancyRooms.size() == 0 && tenAttachReses.size() == 0) {
			return;
		}

		//		Map leaseMap = parsePayListByLease();

		// 注意:这里统一如下处理,各个房间和附属资源的付款明细应该都是按照租期序号顺序排序的.

		//取一个房间来进行增加行
		ITenancyEntryInfo tenObj = tenancyRooms.get(0);
		if(tenObj == null){
			tenObj = tenAttachReses.get(0);
		}

		if(tenObj == null){
			return;
		}

		IObjectCollection oneObjPays = tenObj.getPayList();

		Map tmpMap = new TreeMap();
		for(int i=0; i<oneObjPays.size(); i++){
			ITenancyPayListInfo pay = (ITenancyPayListInfo) oneObjPays.getObject(i);
			int leaseSeq = pay.getLeaseSeq();

			if(tmpMap.get(new Integer(leaseSeq)) == null){
				List paysGroupByLease = new ArrayList();
				paysGroupByLease.add(pay);
				tmpMap.put(new Integer(leaseSeq), paysGroupByLease);
			}else{
				List tmpPays = (List)tmpMap.get(new Integer(leaseSeq));
				tmpPays.add(pay);
				Collections.sort(tmpPays, new Comparator(){
					public int compare(Object arg0, Object arg1) {
						ITenancyPayListInfo pay0 = (ITenancyPayListInfo)arg0;
						ITenancyPayListInfo pay1 = (ITenancyPayListInfo)arg1;
						if(pay0 == null  ||  pay1 == null){
							return 0;
						}
						return pay0.getSeq() - pay1.getSeq();
					}
				});
			}						
		}

		this.getUIContext().put("tmpMap", tmpMap);
		for(Iterator itor = tmpMap.keySet().iterator(); itor.hasNext(); ){
			Integer key = (Integer)itor.next();
			List pays = (List) tmpMap.get(key);
			if(pays.size() == 1){
				ITenancyPayListInfo tPay = (ITenancyPayListInfo) pays.get(0);
				IRow row = this.tblPayList.addRow();
				row.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
				row.setTreeLevel(0);
				row.setUserObject(tPay);

				row.getCell(C_PAYS_MONEY_DEFINE).setValue(tPay.getMoneyDefine());
				row.getCell(C_PAYS_LEASE_SEQ).setValue(new Integer(tPay.getLeaseSeq()));
				row.getCell(C_PAYS_START_DATE).setValue(tPay.getStartDate());
				row.getCell(C_PAYS_END_DATE).setValue(tPay.getEndDate());
				row.getCell(C_PAYS_APP_PAY_DATE).setValue(tPay.getAppPayDate());
			}else{
				IRow unionRow = this.tblPayList.addRow();
				unionRow.setTreeLevel(0);
				unionRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));

				StringBuffer moneyDes = new StringBuffer();
				for(int i=0; i<pays.size(); i++){
					ITenancyPayListInfo tPay = (ITenancyPayListInfo) pays.get(i);
					IRow entryRow = this.tblPayList.addRow();
					entryRow.setTreeLevel(1);
					entryRow.setUserObject(tPay);

					entryRow.getCell(C_PAYS_MONEY_DEFINE).setValue("  " + tPay.getMoneyDefine());
					entryRow.getCell(C_PAYS_LEASE_SEQ).setValue(key);
					entryRow.getCell(C_PAYS_START_DATE).setValue(tPay.getStartDate());
					entryRow.getCell(C_PAYS_END_DATE).setValue(tPay.getEndDate());
					entryRow.getCell(C_PAYS_APP_PAY_DATE).setValue(tPay.getAppPayDate());

					if(i != 0){
						moneyDes.append("；");
					}
					moneyDes.append(tPay.getMoneyDefine());

					if(i == 0){
						unionRow.getCell(C_PAYS_START_DATE).setValue(tPay.getStartDate());
						unionRow.getCell(C_PAYS_END_DATE).setValue(tPay.getEndDate());
						unionRow.getCell(C_PAYS_APP_PAY_DATE).setValue(tPay.getAppPayDate());
					}
				}

				unionRow.getCell(C_PAYS_MONEY_DEFINE).setValue(moneyDes.toString());
				unionRow.getCell(C_PAYS_LEASE_SEQ).setValue(key);
			}
		}

		Map unionRows = new HashMap();
		IRow currentUnionRow = null;
		for(int i=0; i<this.tblPayList.getRowCount(); i++){
			IRow row = this.tblPayList.getRow(i);

			//对于合计行，后面统一计算
			if(row.getUserObject() == null){
				unionRows.put(row, new ArrayList());
				currentUnionRow = row;
				continue;
			}else{
				if(row.getTreeLevel() == 1){
					List entryRows = (List) unionRows.get(currentUnionRow);
					entryRows.add(row);
				}else{
					currentUnionRow = null;
				}

				ITenancyPayListInfo onePay = (ITenancyPayListInfo) row.getUserObject();
				int seq = onePay.getSeq();

				BigDecimal totalAppPayThisLeas = FDCHelper.ZERO;
				BigDecimal totalActPayThisLeas = FDCHelper.ZERO;
				for (int j = 0; j < tenancyRooms.size(); j++) {
					TenancyRoomEntryInfo tenancyRoom = tenancyRooms.get(j);
					TenancyRoomPayListEntryCollection tmpPayList = tenancyRoom.getRoomPayList();
					TenancyRoomPayListEntryInfo tmpPayEntry = tmpPayList.get(seq);
					if (tmpPayEntry == null) {
						continue;
					}

					row.getCell(PREFIX_C_PAYS_ROOM + j + POSTFIX_C_PAYS_APP_AMOUNT).setValue(tmpPayEntry.getAppAmount());
					// tmpPayEntry只存储了tenancyRoom的ID值,这里设置tenancyRoom对象,
					// 便于在verifyPayList()时获得租赁房间的级联字段
					tmpPayEntry.setTenRoom(tenancyRoom);
					row.getCell(PREFIX_C_PAYS_ROOM + j + POSTFIX_C_PAYS_APP_AMOUNT).setUserObject(tmpPayEntry);// 应付金额可以修改
					// .
					// 这样设置便于在storePayList
					// (
					// )
					// 中保存应付金额
					row.getCell(PREFIX_C_PAYS_ROOM + j + POSTFIX_C_PAYS_ACT_AMOUNT).setValue(tmpPayEntry.getAllRemainAmount());
					row.getCell(PREFIX_C_PAYS_ROOM + j + POSTFIX_C_PAYS_ACT_PAY_DATE).setValue(tmpPayEntry.getActPayDate());

					totalAppPayThisLeas = totalAppPayThisLeas.add(tmpPayEntry.getAppAmount() == null ? FDCHelper.ZERO : tmpPayEntry.getAppAmount());
					//实收合计也取剩余金额而不是取实收金额
					totalActPayThisLeas = totalActPayThisLeas.add(tmpPayEntry.getAllRemainAmount() == null ? FDCHelper.ZERO : tmpPayEntry.getAllRemainAmount());
				}

				row.getCell(C_PAYS_TOTAL_APP_PAY).setValue(totalAppPayThisLeas);
				row.getCell(C_PAYS_TOTAL_ACT_PAY).setValue(totalActPayThisLeas);
			}
		}

		//合计行
		for(Iterator itor = unionRows.keySet().iterator(); itor.hasNext(); ){
			IRow unionRow = (IRow) itor.next();
			List entryRows = (List) unionRows.get(unionRow);

			for(int i=0; i<this.tblPayList.getColumnCount(); i++){
				String colKey = this.tblPayList.getColumn(i).getKey();
				if(colKey.equals(C_PAYS_TOTAL_APP_PAY) || colKey.equals(C_PAYS_TOTAL_ACT_PAY)
						|| colKey.endsWith(POSTFIX_C_PAYS_APP_AMOUNT) || colKey.endsWith(POSTFIX_C_PAYS_ACT_AMOUNT)){
					BigDecimal unionAmount = FDCHelper.ZERO;
					for(int j=0; j<entryRows.size(); j++){
						IRow entryRow = (IRow) entryRows.get(j);
						BigDecimal entryAmount = (BigDecimal) entryRow.getCell(colKey).getValue();
						if(entryAmount != null){
							unionAmount = unionAmount.add(entryAmount);
						}
					}
					unionRow.getCell(colKey).setValue(unionAmount);
				}
			}
		}

		this.tblPayList.getMergeManager().mergeBlock(0, 0, this.tblPayList.getRowCount() - 1, 0, KDTMergeManager.FREE_ROW_MERGE);
	}
	private void updatePayListColumn(TenancyRoomEntryCollection tenancyRooms, TenAttachResourceEntryCollection tenAttachReses) {
		// 付款分录信息都记录在tenancyRoomInfo的付款分录集合中
		this.tblPayList.removeRows();
		this.tblPayList.removeColumns();
		this.tblPayList.getTreeColumn().setDepth(2);

		IColumn column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_LEASE_SEQ);
		column.setEditor(TenancyClientHelper.createTxtCellEditor(80, false));

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_MONEY_DEFINE);
		column.setEditor(TenancyClientHelper.createTxtCellEditor(80, false));

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_START_DATE);
		column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_END_DATE);
		column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_APP_PAY_DATE);
		column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);

		for (int i = 0; i < tenancyRooms.size(); i++) {
			column = this.tblPayList.addColumn();

			StringBuffer key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ROOM).append(i).append(POSTFIX_C_PAYS_APP_AMOUNT);
			column.setKey(key.toString());
			column.setEditor(TenancyClientHelper.createFormattedCellEditor(true));
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

			column = this.tblPayList.addColumn();
			key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ROOM).append(i).append(POSTFIX_C_PAYS_ACT_AMOUNT);
			column.setKey(key.toString());
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			column.getStyleAttributes().setLocked(true);

			column = this.tblPayList.addColumn();
			key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ROOM).append(i).append(POSTFIX_C_PAYS_ACT_PAY_DATE);
			column.setKey(key.toString());
			column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
			column.getStyleAttributes().setLocked(true);
		}
		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_TOTAL_APP_PAY);
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		column.getStyleAttributes().setLocked(true);
		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_TOTAL_ACT_PAY);
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		column.getStyleAttributes().setLocked(true);
	}
	protected void tblPayList_editStopped(KDTEditEvent e) throws Exception {
		isFreeContract=true;
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		IColumn col = tblPayList.getColumn(colIndex);
		IRow row = tblPayList.getRow(rowIndex);

		String key = col.getKey();
		//汇总时间修改改变明细的时间
		if(C_PAYS_START_DATE.equals(key) || C_PAYS_END_DATE.equals(key) || C_PAYS_APP_PAY_DATE.equals(key))
		{
			Date date = new Date();
			if (e.getValue() != null) {
				date = (Date) e.getValue();
			}			
			int leaseSeq = ((Integer)row.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
			for(int i=0;i<tblPayList.getRowCount();i++)
			{
				IRow row2 = tblPayList.getRow(i);
				int seq = ((Integer)row2.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
				if(leaseSeq == seq)
				{
					if(row2.getUserObject()!=null)
					{
						ITenancyPayListInfo iTeninfo = (ITenancyPayListInfo)row2.getUserObject();
						if(C_PAYS_START_DATE.equals(key))
						{
							iTeninfo.setStartDate(date);
						}else if(C_PAYS_END_DATE.equals(key))
						{
							iTeninfo.setEndDate(date);

						}else{
							iTeninfo.setAppPayDate(date);							
						}
						row2.setUserObject(iTeninfo);
						row2.getCell(key).setValue(date);
					}
				}
			}
		}
		//明细行的应付金额更改改变汇总合计和付款金额合计
		if (key.startsWith(PREFIX_C_PAYS_ROOM) && key.endsWith(POSTFIX_C_PAYS_APP_AMOUNT)) {
			BigDecimal oldAppAmount = FDCHelper.ZERO;
			BigDecimal newAppAmount = FDCHelper.ZERO;
			if (e.getOldValue() != null) {
				oldAppAmount = (BigDecimal) e.getOldValue();
			}
			if (e.getValue() != null) {
				newAppAmount = (BigDecimal) e.getValue();
			}
			BigDecimal difAmount = newAppAmount.subtract(oldAppAmount);
			int leaseSeq = ((Integer)row.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
			for(int i=0;i<tblPayList.getRowCount();i++)
			{
				IRow row2 = tblPayList.getRow(i);
				int seq = ((Integer)row2.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
				if(leaseSeq==seq)
				{
					if(row2.getUserObject()==null)
					{
						BigDecimal huizong = (BigDecimal)row2.getCell(key).getValue();
						if (huizong == null) {
							huizong = FDCHelper.ZERO;
						}
						row2.getCell(key).setValue(huizong.add(difAmount));
					}
				}
			}

			for(int i=0;i<tblPayList.getRowCount();i++)
			{
				IRow row2 = tblPayList.getRow(i);
				int seq = ((Integer)row2.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
				if(leaseSeq==seq)
				{
					if(row2.getUserObject()==null)
					{
						BigDecimal oldTotalAppPayAmount = (BigDecimal) row2.getCell(C_PAYS_TOTAL_APP_PAY).getValue();
						if (oldTotalAppPayAmount == null) {
							oldTotalAppPayAmount = FDCHelper.ZERO;
						}

						row2.getCell(C_PAYS_TOTAL_APP_PAY).setValue(oldTotalAppPayAmount.add(difAmount));
					}
				}
			}

			BigDecimal oldTotalAppPayAmount = (BigDecimal) row.getCell(C_PAYS_TOTAL_APP_PAY).getValue();
			if (oldTotalAppPayAmount == null) {
				oldTotalAppPayAmount = FDCHelper.ZERO;
			}

			row.getCell(C_PAYS_TOTAL_APP_PAY).setValue(oldTotalAppPayAmount.add(difAmount));

			// 如果是修改的押金或者首租,需要修改合同信息中的的金额
			//updateDepositAndFirstPayRent();
		} else if (key.startsWith(PREFIX_C_PAYS_ATTACH_RES)) {
			// TenAttachResourcePayListEntryInfo pay =
			// (TenAttachResourcePayListEntryInfo) row.getUserObject();
			if (key.endsWith(POSTFIX_C_PAYS_APP_AMOUNT)) {
				// pay.setAppAmount((BigDecimal) row.getCell(key));
			}
		}
		 updatePayListInfo();
	}
	protected void verifyInputForSubmint() throws Exception {
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtDept);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkStartDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkEndDate);
		
		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkEndDate.getValue();
		
		startDate = FDCDateHelper.getDayBegin(startDate);
		endDate = FDCDateHelper.getDayBegin(endDate);
		if (startDate.after(endDate)) {
			FDCMsgBox.showWarning(this, "开始日期不能迟于结束日期！");
			SysUtil.abort();
		}
		
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row = this.kdtEntry.getRow(i);
			if(row.getCell("moneyDefine").getValue()==null){
				FDCMsgBox.showWarning(this,"款项名称不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("moneyDefine"));
				SysUtil.abort();
			}
			if(row.getCell("amount").getValue()==null||((BigDecimal)row.getCell("amount").getValue()).compareTo(FDCHelper.ZERO)==0){
				FDCMsgBox.showWarning(this,"收取金额不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("amount"));
				SysUtil.abort();
			}
		}
	}
	public void actionAttachment_actionPerformed(ActionEvent e)throws Exception{
		 AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		 String boID = getSelectBOID();
		 if(boID == null)
			 return;
		 boolean isEdit = false;
		 if("ADDNEW".equals(getOprtState()) || "EDIT".equals(getOprtState())){
			 isEdit = true;
       }
		 acm.showAttachmentListUIByBoID(boID, this, isEdit);
   }
	 public boolean destroyWindow() {
		 boolean b = super.destroyWindow();
		 if(b){
			 try {
				 if(!OtherBillFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getId().toString()))){
					 deleteAttachment(this.editData.getId().toString());
				 }
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
      }
      return b;
	}
	 protected void deleteAttachment(String id) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		for(int i=0;i<col.size();i++){
			EntityViewInfo attview=new EntityViewInfo();
			FilterInfo attfilter = new FilterInfo();
			
			attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
			attview.setFilter(attfilter);
			BoAttchAssoCollection attcol=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(attview);
			if(attcol.size()==1){
				BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(id);
			}else if(attcol.size()>1){
				BoAttchAssoFactory.getRemoteInstance().delete(filter);
			}
		}
	}
}