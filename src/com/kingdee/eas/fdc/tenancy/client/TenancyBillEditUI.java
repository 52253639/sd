/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.RevListInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractChangeBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefine;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.CustomerCardUI;
import com.kingdee.eas.fdc.sellhouse.client.CustomerEditUI;
import com.kingdee.eas.fdc.sellhouse.client.RoomEditUI;
import com.kingdee.eas.fdc.sellhouse.client.RoomSelectUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.AgencyContractInfo;
import com.kingdee.eas.fdc.tenancy.AgencyInfo;
import com.kingdee.eas.fdc.tenancy.AttachDealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.AttachDealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceCollection;
import com.kingdee.eas.fdc.tenancy.AttachResourceFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceInfo;
import com.kingdee.eas.fdc.tenancy.BizStateEnum;
import com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum;
import com.kingdee.eas.fdc.tenancy.CommisionStandardEnum;
import com.kingdee.eas.fdc.tenancy.DateEnum;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.DepositDealBillFactory;
import com.kingdee.eas.fdc.tenancy.EquipmentEntryCollection;
import com.kingdee.eas.fdc.tenancy.EquipmentEntryInfo;
import com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum;
import com.kingdee.eas.fdc.tenancy.FlagAtTermEnum;
import com.kingdee.eas.fdc.tenancy.FreeTenancyTypeEnum;
import com.kingdee.eas.fdc.tenancy.HandleStateEnum;
import com.kingdee.eas.fdc.tenancy.IDealAmountInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.IncreaseStyleEnum;
import com.kingdee.eas.fdc.tenancy.IncreasedRentEntryCollection;
import com.kingdee.eas.fdc.tenancy.IncreasedRentEntryInfo;
import com.kingdee.eas.fdc.tenancy.IncreasedRentTypeEnum;
import com.kingdee.eas.fdc.tenancy.LiquidatedInfo;
import com.kingdee.eas.fdc.tenancy.MoneyEnum;
import com.kingdee.eas.fdc.tenancy.MoreRoomsTypeEnum;
import com.kingdee.eas.fdc.tenancy.OccurreStateEnum;
import com.kingdee.eas.fdc.tenancy.OtherBillFactory;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.RentCountTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentFreeBillInfo;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryFactory;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryInfo;
import com.kingdee.eas.fdc.tenancy.RentRemissionFactory;
import com.kingdee.eas.fdc.tenancy.RentStartTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.SinCustomerEntrysCollection;
import com.kingdee.eas.fdc.tenancy.SinCustomerEntrysInfo;
import com.kingdee.eas.fdc.tenancy.SinObligateAttachEntryCollection;
import com.kingdee.eas.fdc.tenancy.SinObligateAttachEntryInfo;
import com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryCollection;
import com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryInfo;
import com.kingdee.eas.fdc.tenancy.SincerObligateInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenBillRoomStateEnum;
import com.kingdee.eas.fdc.tenancy.TenLiquidatedCollection;
import com.kingdee.eas.fdc.tenancy.TenLiquidatedInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyModeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyModificationFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomChargeEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomChargeEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * 租赁合同编辑界面
 */
public class TenancyBillEditUI extends AbstractTenancyBillEditUI implements TenancyBillConstant {
	private static final long serialVersionUID = 3940285291180126710L;
	private static final Logger log = CoreUIObject.getLogger(TenancyBillEditUI.class);
	private SellProjectInfo sellProjectInfo = null;
	private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	BigDecimal rentArea = FDCHelper.ZERO;//TODO 很恶心的变量
	private TenancyBillInfo oldTenancyBill = null;// 改续租转名时的原合同
	private MoneyDefineCollection monDefineColl = new MoneyDefineCollection();
	Map roomDisMap = new HashMap();
	boolean isFirstEdit = false;
	String oldState = "";

	/** 主要对分录进行获取.在getSelector中已经设置 */
	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		TenancyBillInfo tenancy = (TenancyBillInfo) super.getValue(pk);

		if (tenancy.getOldTenancyBill() != null) {
			String oldTenancyId = tenancy.getOldTenancyBill().getId().toString();
			TenancyBillInfo oldTenancy = TenancyHelper.getTenancyBillInfo(oldTenancyId);
			//			TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(oldTenancyId), this.getSelectors());
			tenancy.setOldTenancyBill(oldTenancy);
			
			oldTenancyBill = oldTenancy;
		}
		TenancyRoomEntryCollection trc=tenancy.getTenancyRoomList();
		//增加对房间分录按roomLongNUm 正序排列 eirc_wang 2010.7.06
		CRMHelper.sortCollection(trc, "roomLongNum", true);
		TenancyHelper.sortPayListOfTenancy(tenancy);
		return tenancy;
	}

	public TenancyBillEditUI() throws Exception {
		super();
	}
	private boolean isLoad=false;
	public void loadFields() {
		isLoad=true;
		detachListeners();
		// --在loadFields之前设置该值为null,否则控件会取当前时间填充		
		this.pkStartDate.setValue(null);
		this.pkEndDate.setValue(null);
		super.loadFields();		
		TenancyBillInfo tenBill = this.editData;
		oldTenancyBill = tenBill.getOldTenancyBill();
		this.comboRentCountType.setSelectedItem(tenBill.getRentCountType());		
		this.txtDaysPerYear.setValue(new Integer(tenBill.getDaysPerYear()));

		this.chkIsAutoToInteger.setSelected(tenBill.isIsAutoToInteger());
		this.chkIsAutoToIntegerForFee.setSelected(tenBill.isIsAutoToIntegerFee());
		
		this.comboToIntegerType.setSelectedItem(tenBill.getToIntegerType());
		this.comboToIntegerTypeFee.setSelectedItem(tenBill.getToIntegetTypeFee());
		
		this.comboDigit.setSelectedItem(tenBill.getDigit());
		this.comboDigitFee.setSelectedItem(tenBill.getDigitFee());
		
		
		this.comboRentStartType.setSelectedItem(tenBill.getRentStartType());
		this.pkStartDateLimit.setValue(tenBill.getStartDateLimit());
		this.chkIsFreeContract.setSelected(tenBill.isIsFreeContract());
		this.txtLeaseCount.setValue(tenBill.getLeaseCount());
		this.pkFristLeaseDate.setValue(tenBill.getFristRevDate());
		this.f7BussinessDepartMent.setValue(tenBill.getOrgUnit());
		this.DPickFromMonth.setValue(tenBill.getSecondRevDate());
		
		if(MoreRoomsTypeEnum.StandardRentSetting.equals(tenBill.getMoreRoomsType()))
		{
			this.contMoreRoomsType.setVisible(true);
			this.comboMoreRoomsType.setSelectedItem(tenBill.getMoreRoomsType());
		}							
		this.tblRoom.removeRows();
		this.tblAttachRes.removeRows();
		this.tabMiddle.removeAll();		

		addRoomRows(tenBill.getTenancyRoomList());
		addAttachResRows(tenBill.getTenAttachResourceList());

		loadIncreasedRents(tenBill.getIncreasedRents());
		loadFreeRents(tenBill.getRentFrees());
		
		//add by yangfan 
		loadLiquidated(tenBill.getTenLiquidated());
		
		loadOtherPayList(tenBill);
		//by tim_gao 2010-9-10
		//当续租 状态时 ，租金设置从旧的合同带出租金相关
		if(TenancyContractTypeEnum.ContinueTenancy.equals(this.comboTenancyType.getSelectedItem())&&OprtState.ADDNEW.equals(this.getOprtState())){
			
			loadTblRentSetbyTrans(tenBill.getOldTenancyBill().getTenancyRoomList());
		}		
		
		if(isDynamicStartDate() && this.pkStartDate.getValue()==null)
		{
			loadDynamicTblRentSet(tenBill.getTenancyRoomList(), tenBill.getTenAttachResourceList());
		}
		//这里这么写是因为如果直接写else在租赁控制新增租赁合同时会调用loadTblRentSet，
		else if(this.pkStartDate.getValue()!=null && this.pkEndDate.getValue()!=null)
		{
			
			loadTblRentSet(tenBill.getTenancyRoomList(), tenBill.getTenAttachResourceList());
			try {
				reSetRentSetInfo(tenBill.getTenancyRoomList());
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		loadCustomers(tenBill.getTenCustomerList());
		setFirstLeaseEndDateVisible();
		setVisibleAboutRentCountType(tenBill.getRentCountType());
		setAgencySourceVisible();
		setToIntegerVisable(tenBill.isIsAutoToInteger());
		setToIntegerFeeVisable(tenBill.isIsAutoToIntegerFee());
		setIsbtnPayList(tenBill.isIsFreeContract());

		this.btnInsert.setEnabled(false);
		this.btnAddPayList.setEnabled(false);
		this.btnDelPayList.setEnabled(false);

		//转名续租改租合同只能选择固定开始日期类型合同
		if("ADDNEW".equals(this.getOprtState()) && (TenancyContractTypeEnum.RejiggerTenancy.equals(this.comboTenancyType.getSelectedItem())
				|| TenancyContractTypeEnum.ContinueTenancy.equals(this.comboTenancyType.getSelectedItem())
				||TenancyContractTypeEnum.PriceChange.equals(this.comboTenancyType.getSelectedItem())))
		{
			this.comboRentStartType.setEnabled(false);
		}
		if ("ADDNEW".equals(this.getOprtState()) && TenancyContractTypeEnum.ChangeName.equals(this.comboTenancyType.getSelectedItem())) {
			try {
				this.comboRentStartType.setEnabled(false);
				updatePayListInfo();
			} catch (BOSException e) {
				this.handleException(e);
			}
		} else {
			List leaseList = getLeaseListFromView();
			updatePayList(tenBill.getTenancyRoomList(), tenBill.getTenAttachResourceList(), leaseList);// 生成付款明细列表
			updateTotalRent();
		}
		try {
			isFreeConSet(this.chkIsFreeContract.isSelected());
		} catch (BOSException e) {
			this.handleException(e);
		}
		
		if(this.tblRoom.getRowCount()>0){
			this.prmtRentFreeBill.setEnabled(true);
			TenancyRoomEntryInfo entry=(TenancyRoomEntryInfo) this.tblRoom.getRow(0).getUserObject();
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			if(this.editData.getId()!=null){
				filter.getFilterItems().add(new FilterItemInfo("id","select frentFreeBillId from T_TEN_TenancyBill where fid!='"+this.editData.getId().toString()+"' and frentFreeBillId is not null",CompareType.NOTINNER));
			}else{
				filter.getFilterItems().add(new FilterItemInfo("id","select frentFreeBillId from T_TEN_TenancyBill where frentFreeBillId is not null",CompareType.NOTINNER));
			}
			filter.getFilterItems().add(new FilterItemInfo("room.id",entry.getRoom().getId().toString()));
			view.setFilter(filter);
			prmtRentFreeBill.setEntityViewInfo(view);
		}else{
			this.prmtRentFreeBill.setEnabled(false);
		}
		loadTotalPayList();
		attachListeners();
		isLoad=false;
	}
	private void initTotalPayList(){
		this.tblTotal.checkParsed();
		this.tblTotal.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		this.tblTotal.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setPrecision(2);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblTotal.getColumn("appAmount").setEditor(numberEditor);
		this.tblTotal.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblTotal.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblTotal.getColumn("actRevAmount").setEditor(numberEditor);
		this.tblTotal.getColumn("actRevAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblTotal.getColumn("actRevAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblTotal.getColumn("actRevAmount").getStyleAttributes().setLocked(true);
	}
	private void loadTotalPayList(){
		TenBillOtherPayCollection otherPayList = new TenBillOtherPayCollection();
		for (int i = 0; i < this.editData.getOtherPayList().size(); i++) {
			TenBillOtherPayInfo entry=this.editData.getOtherPayList().get(i);
			otherPayList.add(entry);
		}
		if(this.editData.getTenancyRoomList().size()>0){
			for (int i = 0; i < this.editData.getTenancyRoomList().get(0).getPayList().size(); i++) {
				TenancyRoomPayListEntryInfo tenOtherInfo = this.editData.getTenancyRoomList().get(0).getRoomPayList().get(i);
				TenBillOtherPayInfo entry=new TenBillOtherPayInfo();
				entry.setMoneyDefine(tenOtherInfo.getMoneyDefine());
				entry.setAppAmount(tenOtherInfo.getAppAmount());
				entry.setActRevAmount(tenOtherInfo.getActRevAmount());
				otherPayList.add(entry);
			}
		}
		CRMHelper.sortCollection(otherPayList, "moneyDefine.number", true);
		
		this.tblTotal.removeRows();
		Map addRow=new HashMap();
		for (int i = 0; i < otherPayList.size(); i++) {
			TenBillOtherPayInfo entry=otherPayList.get(i);
			if(entry.getMoneyDefine()!=null){
				if(addRow.containsKey(entry.getMoneyDefine().getNumber())){
					IRow row=(IRow) addRow.get(entry.getMoneyDefine().getNumber());
					row.getCell("appAmount").setValue(FDCHelper.add(row.getCell("appAmount").getValue(), entry.getAppAmount()));
					row.getCell("actRevAmount").setValue(FDCHelper.add(row.getCell("actRevAmount").getValue(), entry.getActRevAmount()));
				}else{
					IRow row=this.tblTotal.addRow();
					row.getCell("moneyDefine").setValue(entry.getMoneyDefine().getName());
					row.getCell("appAmount").setValue(entry.getAppAmount());
					row.getCell("actRevAmount").setValue(entry.getActRevAmount());
					addRow.put(entry.getMoneyDefine().getNumber(), row);
				}
			}
		}
		CRMClientHelper.getFootRow(this.tblTotal, new String[]{"appAmount","actRevAmount"});
	}
	/**
	 * @author tim_gao
	 * @see 加载租房设置中，房子押金等 信息
	 * @param TenancyRoomEntryCollection
	 * 
	 */
	public void loadTblRentSetbyTrans(TenancyRoomEntryCollection tenRooms){
		Iterator it = tenRooms.iterator();
		this.tblRentSet.removeRows();
		while(it.hasNext()){
			
			TenancyRoomEntryInfo tenRoomEntryInfo = (TenancyRoomEntryInfo)it.next();
			IRow row = this.tblRentSet.addRow();
			row.setUserObject(tenRoomEntryInfo);
			row.getCell(C_ROOM_ROOM).setValue(tenRoomEntryInfo.getRoomLongNum());
			
				int sum = tenRoomEntryInfo.getDealAmounts().size();
			if(sum>0)
			{
				for(int i=0;i<sum;i++)
				{	
						
						DealAmountEntryInfo dealMoneyInfo = tenRoomEntryInfo.getDealAmounts().get(i);
						//判断目录的押金款才显示
						if(row.getCell(dealMoneyInfo.getMoneyDefine().getNumber())!=null){
							row.getCell(dealMoneyInfo.getMoneyDefine().getNumber()).setValue(dealMoneyInfo.getAmount());
						}
						
					
				}
			}
			
			row.getCell(C_RENT_FIRST_RENT).setValue(tenRoomEntryInfo.getFirstPayAmount());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(tenRoomEntryInfo.getTenancyModel());
			row.getCell(C_RENT_RENT_TYPE).setValue(tenRoomEntryInfo.getDealRentType());
			
		}
		
	}
	
	private void loadOtherPayList(TenancyBillInfo info) {
		this.tblOtherPayList.removeRows();
		TenBillOtherPayCollection otherPayList = info.getOtherPayList();
		CRMHelper.sortCollection(otherPayList, "leaseSeq", true);
//		Map payMap = new TreeMap();
		for (int i = 0; i < otherPayList.size(); i++) {
			TenBillOtherPayInfo tenOtherInfo = otherPayList.get(i);
			addOtherPayListEntryRow(tenOtherInfo);
//			payMap.put(new Integer(tenOtherInfo.getSeq()), tenOtherInfo);
		}
		
//		Set set = payMap.keySet();
//		for (Iterator iter = set.iterator(); iter.hasNext();) {
//			Integer key = (Integer) iter.next();
//			TenBillOtherPayInfo tenOtherInfo = (TenBillOtherPayInfo) payMap.get(key);
//			addOtherPayListEntryRow(tenOtherInfo);
//		}
	}

	private void loadFreeRents(RentFreeEntryCollection rentFrees) {
		this.tblFree.removeRows();
		for(int i=0; i<rentFrees.size(); i++){
			RentFreeEntryInfo rentFree = rentFrees.get(i);

			IRow row = this.tblFree.addRow();
			row.setUserObject(rentFree);

			row.getCell(C_FREE_START_DATE).setValue(rentFree.getFreeStartDate());
			row.getCell(C_FREE_END_DATE).setValue(rentFree.getFreeEndDate());
			row.getCell(C_FREE_TENANCY_TYPE).setValue(rentFree.getFreeTenancyType());
			row.getCell(C_FREE_DES).setValue(rentFree.getDescription());
		}
	}

	private void loadIncreasedRents(IncreasedRentEntryCollection increaseds) {
		this.tblIncrease.removeRows();
		for(int i=0; i<increaseds.size(); i++){
			IncreasedRentEntryInfo increased = increaseds.get(i);

			IRow row = this.tblIncrease.addRow();
			row.setUserObject(increased);

			row.getCell(C_INC_INCREASE_DATE).setValue(increased.getIncreaseDate());
			row.getCell(C_INC_INCREASE_TYPE).setValue(increased.getIncreaseType());
			row.getCell(C_INC_VALUE).setValue(increased.getValue());
			row.getCell(C_INC_INCREASESTYLE).setValue(increased.getIncreaseStyle());
		}
	}

	/**
	 * 加载租赁客户信息 
	 * @param tenCustomerList
	 */
	private void loadCustomers(TenancyCustomerEntryCollection tenCustomerList) {
		//modified by weiqiang_chen PBG053457
		this.tblCustomer.removeRows();
		for (int i = 0; i < tenCustomerList.size(); i++) {
			TenancyCustomerEntryInfo tenCustomer = tenCustomerList.get(i);
			IRow row = this.tblCustomer.addRow();
			row.setHeight(20);
			row.setUserObject(tenCustomer);
			row.getCell(C_CUS_PROPERTY_PERCENT).setValue(tenCustomer.getPropertyPercent());
			row.getCell(C_CUS_DES).setValue(tenCustomer.getDescription());
			FDCCustomerInfo customer = tenCustomer.getFdcCustomer();
			if (customer != null) {
				row.getCell(C_CUS_CUSTOMER).setValue(customer);
				row.getCell(C_CUS_POSTALCODE).setValue(customer.getPostalcode());
				row.getCell(C_CUS_PHONE).setValue(customer.getPhone());
				row.getCell(C_CUS_CERTIFICATE_NAME).setValue(customer.getCertificateName());
				row.getCell(C_CUS_CERTIFICATE_NUMBER).setValue(customer.getCertificateNumber());
				row.getCell(C_CUS_MAIL_ADDRESS).setValue(customer.getMailAddress());
				row.getCell(C_CUS_BOOK_DATE).setValue(customer.getCreateTime());
			}
		}
	}	
	/**
	 * 加载违约金计算方案设置 add by yangfan
	 */
	private void loadLiquidated(TenLiquidatedCollection collection){
		this.tblLiquidated.removeRows();
		TreeMap map=new TreeMap();
		for(int i=0;i<collection.size();i++){
			TenLiquidatedInfo liinfo=collection.get(i);
			map.put(liinfo.getMoneyDefine().getNumber(), liinfo);
		}
		Iterator it=map.entrySet().iterator();
	    while(it.hasNext()){
	    	Map.Entry entry =(Map.Entry) it.next();
	    	IRow row = this.tblLiquidated.addRow();
	    	TenLiquidatedInfo liq=(TenLiquidatedInfo)entry.getValue();
			row.setUserObject(liq);
			row.getCell("moneyDefine").setValue(liq.getMoneyDefine());
			row.getCell("liquidated").setValue(liq.getLiquidated());
		}

	}
	/** 根据单据的状态来设置一些控件的状态 */
	private void initControlByBillState() {
		TenancyBillInfo ten = this.editData;
		TenancyBillStateEnum state = ten.getTenancyState();
		if (state == null || TenancyBillStateEnum.Saved.equals(state)) {
			this.actionSave.setEnabled(true);
		} else {
			this.actionSave.setEnabled(false);
		}

		if (state != null && !TenancyBillStateEnum.Saved.equals(state) && !TenancyBillStateEnum.Submitted.equals(state)) {
			this.actionEdit.setEnabled(false);
		} else {
			if (this.getOprtState().equals("VIEW")) {
				this.actionEdit.setEnabled(true);
			}
		}

		TenancyContractTypeEnum tenType = this.getCurrentTenancyType();

		// 新租
		if (TenancyContractTypeEnum.NewTenancy.equals(tenType)) {
			this.txtChangeDes.setEnabled(false);
		}

		// 续租
		if (TenancyContractTypeEnum.ContinueTenancy.equals(tenType)) {
			this.pkStartDate.setEnabled(false);// 起始日期只能是原合同结束日期
			this.tblCustomer.getStyleAttributes().setLocked(true);
			this.actionAddCustomer.setEnabled(false);
			this.btnNewCustomer.setEnabled(false);
			this.actionRemoveCustomer.setEnabled(false);

		}

		// 改租
		if (TenancyContractTypeEnum.RejiggerTenancy.equals(tenType)) {
			this.tblCustomer.getStyleAttributes().setLocked(true);
			this.actionAddCustomer.setEnabled(false);
			this.btnNewCustomer.setEnabled(false);
			this.actionRemoveCustomer.setEnabled(false);
		}

		// 转名
		if (TenancyContractTypeEnum.ChangeName.equals(tenType)) {
			this.tblRoom.getStyleAttributes().setLocked(true);
			this.tblAttachRes.getStyleAttributes().setLocked(true);
			this.actionAddRoom.setEnabled(false);
			this.actionRemoveRoom.setEnabled(false);
			this.btnAddAttachRes.setEnabled(false);
			this.btnRemoveAttachRes.setEnabled(false);
			this.comboChargeDateType.setEnabled(false);
			this.spinLeaseTime.setEnabled(false);
			this.spinChargeOffsetDays.setEnabled(false);
		}
		
		// 价格变更
		if (TenancyContractTypeEnum.PriceChange.equals(tenType)) {
			this.tblRoom.getStyleAttributes().setLocked(true);
			this.tblAttachRes.getStyleAttributes().setLocked(true);
			this.tblCustomer.getStyleAttributes().setLocked(true);
			this.actionAddCustomer.setEnabled(false);
			this.btnNewCustomer.setEnabled(false);
			this.actionRemoveCustomer.setEnabled(false);
			this.actionAddRoom.setEnabled(false);
			this.actionRemoveRoom.setEnabled(false);
			this.btnAddAttachRes.setEnabled(false);
			this.btnRemoveAttachRes.setEnabled(false);
			this.comboChargeDateType.setEnabled(false);
			this.spinLeaseTime.setEnabled(false);
			this.spinChargeOffsetDays.setEnabled(false);
			this.comboFirstLeaseType.setEnabled(false);
		
		}

		// 改租和转名时才允许录入扣款
		if (TenancyContractTypeEnum.RejiggerTenancy.equals(tenType) || TenancyContractTypeEnum.ChangeName.equals(tenType)) {
			this.txtDeductionAmount.setEnabled(true);
		} else {
			this.txtDeductionAmount.setEnabled(false);
		}
		//TODO 扣款可能修改为针对房间扣款，目前暂不实现扣款功能
		this.contDeductionAmount.setVisible(false);
	}

	/** 根据操作状态来设置相关控件的状态 */
	private void initControlByOprtState() {
		if (this.getOprtState().equals("VIEW") || this.getOprtState().equals("FINDVIEW")) {
			this.actionAddCustomer.setEnabled(false);
			this.btnNewCustomer.setEnabled(false);
			this.actionAddRoom.setEnabled(false);
			this.actionRemoveCustomer.setEnabled(false);
			this.actionRemoveRoom.setEnabled(false);
			this.btnAddAttachRes.setEnabled(false);
			this.btnRemoveAttachRes.setEnabled(false);

			this.btnAddOtherPaylist.setEnabled(false);
			this.btnDelOtherPaylist.setEnabled(false);
			
			this.btnAddIncrease.setEnabled(false);
			this.btnRmIncrease.setEnabled(false);
			this.btnAddFree.setEnabled(false);
			this.btnRmFree.setEnabled(false);
			
			// 只允许在察看状态下才能进行押金结转
			this.actionCarryForward.setEnabled(true);

			for (int i = 0; i < tabMiddle.getTabCount(); i++) {
				TenancyPropertyUI ui = (TenancyPropertyUI) this.tabMiddle.getComponentAt(i);
				ui.setEditable(false);
			}
		} else {
			this.actionAddCustomer.setEnabled(true);
			this.btnNewCustomer.setEnabled(true);
			this.actionRemoveCustomer.setEnabled(true);
			this.actionAddRoom.setEnabled(true);
			this.actionRemoveRoom.setEnabled(true);
			this.btnAddAttachRes.setEnabled(true);
			this.btnRemoveAttachRes.setEnabled(true);

			this.btnAddOtherPaylist.setEnabled(isEdit);
			this.btnDelOtherPaylist.setEnabled(isEdit);
			this.tblOtherPayList.setEnabled(isEdit);
			
//			this.btnAddIncrease.setEnabled(true);
//			this.btnRmIncrease.setEnabled(true);
			this.btnAddFree.setEnabled(true);
			this.btnRmFree.setEnabled(true);
			
			this.actionCarryForward.setEnabled(false);

			for (int i = 0; i < tabMiddle.getTabCount(); i++) {
				TenancyPropertyUI ui = (TenancyPropertyUI) this.tabMiddle.getComponentAt(i);
				ui.setEditable(true);
			}
		}
		if (this.getOprtState().equals("EDIT")){
			this.f7SincerObligate.setEnabled(false);
		}
	}

	public void onLoad() throws Exception {
		oldState = this.oprtState;
		if (!saleOrg.isIsBizUnit()){
			this.actionEdit.setEnabled(false);
		}
		initControl();
		initRoomTable();
		initAttachResTable();
		initF7Bussinss();
//		//TODO 暂时屏蔽配套资源
//		if(tabbedPaneRoom.getTabCount() == 2){
//			this.tabbedPaneRoom.remove(1);
//		}
		tabbedPaneRoom.setVisible(false);
		initCustomeTable();
		initRentSetTable();
		initPayListTable();
		initOtherPayList();
		//add by yangfan
		initLiquidatedTable();
		initTotalPayList();
		tabbedPaneContract.remove(panelContractInfo);
		tabbedPaneContract.remove(kDPaneLongContract);
		tabbedPaneContract.remove(panelCommissionSetting);
		btnViewRoomInfo.setVisible(false);
		btnViewCustInfo.setVisible(false);
		btnNewCustomer.setVisible(false);
		
		this.contfirstTermTenAmo.setVisible(false);
		this.contDepositAmount.setVisible(false);
		this.contRemainDepositAmount.setVisible(false);

		this.contStartDateLimit.setVisible(false);
		this.contMoreRoomsType.setVisible(false);

		super.onLoad(); 
		this.actionAuditResult.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.txtLeaseCount.setDataType(0);
		initControlByOprtState();
		initControlByBillState();

		setSincerFilter();
		this.storeFields();
		initOldData(this.editData); 
		this.actionCarryForward.setVisible(false);

		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);

		this.actionAudit.setVisible(true); 
		actionAudit.setEnabled(true);
		if (!saleOrg.isIsBizUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
		this.txtName.setMaxLength(80);
		this.txtNumber.setMaxLength(80);
		this.actionAttachment.setVisible(true);
		this.actionAuditResult.setVisible(true);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		//滞纳金比例 ,总成交租金进行 隐藏 2010.12.14 xin_wang
		this.contLateFeeAmount.setVisible(false);
		//由租售项目的属性来确认是否显示物业页签 2010.12.14 xin_wang
		SellProjectInfo sellProject = (SellProjectInfo)this.f7SellProject.getValue();
		if(sellProject!=null){
			if(!sellProject.isIsForPPM()){
				this.tabbedPaneContract.remove(panelProperty);
			}
		}
		//根据不同的收租日期类型 显示不同的控件 2010.12.15 xin_wang
		if(ChargeDateTypeEnum.FixedDate.equals((ChargeDateTypeEnum)comboChargeDateType.getSelectedItem())){
			this.contFromMonth.setVisible(true);
			this.contChargeOffsetDays.setVisible(false);
		}else{
			this.contFromMonth.setVisible(false);
			this.contChargeOffsetDays.setVisible(true);
		}
		//add by yangfan
		initLiquidatedTable();
		this.cbRateDate.setEnabled(false);
		this.cbReliefDate.setEnabled(false);
		this.cbStandardDate.setEnabled(false);
		this.cbRateDate.setSelectedItem(DateEnum.DAY);
		this.cbReliefDate.setSelectedItem(DateEnum.DAY);
		this.cbStandardDate.setSelectedItem(DateEnum.DAY);
		
		this.txtStandard.setPrecision(0);
		this.txtStandard.setRemoveingZeroInDispaly(false);
		this.txtStandard.setRemoveingZeroInEdit(false);
		this.txtStandard.setNegatived(false);
		this.txtStandard.setHorizontalAlignment(JTextField.RIGHT);
		this.txtStandard.setSupportedEmpty(true);

		this.txtRelief.setPrecision(0);
		this.txtRelief.setRemoveingZeroInDispaly(false);
		this.txtRelief.setRemoveingZeroInEdit(false);
		this.txtRelief.setNegatived(false);
		this.txtRelief.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRelief.setSupportedEmpty(true);
		
		this.txtRate.setRemoveingZeroInDispaly(false);
		this.txtRate.setRemoveingZeroInEdit(false);
		this.txtRate.setNegatived(false);
		this.txtRate.setHorizontalAlignment(2);
		this.txtRate.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRate.setSupportedEmpty(true);
		
		txtRate.setRequired(true);
    	txtRelief.setRequired(true);
    	txtStandard.setRequired(true);
    	
    	if(this.chkIsMDLiquidated.getSelected()==32){
			this.panLiquidated.setVisible(false);
			this.panMDLiquidated.setVisible(true);
		}else{
			this.panLiquidated.setVisible(true);
			this.panMDLiquidated.setVisible(false);
		}
    	this.panMDLiquidated.setBounds(26, 49, 490, 355);
    	setContMoreRoomsTypeVisiable();


    	KDBizPromptBox f7Customer = new KDBizPromptBox();
		f7Customer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
		f7Customer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(this.editData.getSellProject(),userInfo));
		f7Customer.setEditable(true);
		f7Customer.setDisplayFormat("$name$");
		f7Customer.setEditFormat("$number$");
		f7Customer.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Customer);
		this.tblCustomer.getColumn(C_CUS_CUSTOMER).setEditor(f7Editor);
		
		this.actionAddCollectProtocol.setVisible(false);
		
		this.txtName.setEnabled(false);
		
		this.tabbedPaneContract.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				if(tabbedPaneContract.getSelectedIndex()==0&&(getOprtState().equals("ADDNEW") || getOprtState().equals("EDIT"))){
					storePayList();
					storeOtherPayList();
					loadTotalPayList();
				}
			}
		});
	}
	private void initF7Bussinss() {
		this.f7BussinessDepartMent.setQueryInfo("com.kingdee.eas.basedata.org.app.OUQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isSaleOrgUnit","1"));
		filter.getFilterItems().add(new FilterItemInfo("partSale.isBizUnit","1"));
		view.setFilter(filter);
		this.f7BussinessDepartMent.setEntityViewInfo(view);
		
	}

	protected void pkTenancyDate_dataChanged(DataChangeEvent e) throws Exception {
//		updatePayListInfo();
	}
	
	
	protected void pkFristLeaseDate_dataChanged(DataChangeEvent e) throws Exception {
		updatePayListInfo();
	}
	private void initPayListTable() {
		FDCTableHelper.disableDelete(this.tblPayList);
		afterPressDeleteButton();
		this.tblPayList.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);		
		this.tblPayList.setEditable(false);
	}

	private KDWorkButton btnAddIncrease = null;
	private KDWorkButton btnRmIncrease = null;
	private KDWorkButton btnAddFree = null;
	private KDWorkButton btnRmFree = null;

	private void initRentSetTable() throws BOSException {
		this.tblIncrease.checkParsed();
		this.tblFree.checkParsed();
//		this.tblRentSet.checkParsed();
//		this.tblRentSet.getColumn("actdayprice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblRentSet.getColumn("actdayprice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
//		this.tblRentSet.getColumn("actdayprice").getStyleAttributes().setHided(true);
//		this.tblIncrease.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
//		this.tblFree.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
//		this.tblRentSet.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("moneyType", "DepositAmount"));
		filter.getFilterItems().add(new FilterItemInfo("sysType", "TenancySys"));
		view.setFilter(filter);
		monDefineColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
		//初始化款项类型为押金
		if(monDefineColl.size()>1)
		{
			for(int i=0;i<monDefineColl.size();i++)
			{					
				MoneyDefineInfo moneyInfo = monDefineColl.get(i);
				if(i==0)
				{
					IColumn columnMoney = this.tblRentSet.getColumn("deposit");
					columnMoney.setKey(moneyInfo.getNumber());
					IRow headRow0 = this.tblRentSet.getHeadRow(0);
					IRow headRow1 = this.tblRentSet.getHeadRow(1);
					headRow0.getCell(moneyInfo.getNumber()).setValue("押金");
					headRow1.getCell(moneyInfo.getNumber()).setValue(moneyInfo);
				}else
				{
					IColumn columnMoney = this.tblRentSet.addColumn(2);
					columnMoney.setKey(moneyInfo.getNumber());
					IRow headRow0 = this.tblRentSet.getHeadRow(0);
					IRow headRow1 = this.tblRentSet.getHeadRow(1);
					headRow0.getCell(moneyInfo.getNumber()).setValue("押金");
					headRow1.getCell(moneyInfo.getNumber()).setValue(moneyInfo);
				}											
			}
		}else if(monDefineColl.size()==1)
		{
			IColumn columnMoney = this.tblRentSet.getColumn("deposit");
			columnMoney.setKey(((MoneyDefineInfo)monDefineColl.getObject(0)).getNumber());//setKey
		}
		
		this.tblRentSet.getHeadMergeManager().mergeBlock(0, 0, 1, this.tblRentSet.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);

		this.tblRentSet.getColumn(C_RENT_ROOM).getStyleAttributes().setLocked(true);

		for(int i=0;i<monDefineColl.size();i++)
		{
			MoneyDefineInfo moneyInfo = monDefineColl.get(i);
			this.tblRentSet.getColumn(moneyInfo.getNumber()).setEditor(createFormattedCellEditor());
			this.tblRentSet.getColumn(moneyInfo.getNumber()).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			this.tblRentSet.getColumn(moneyInfo.getNumber()).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		}
//		this.tblRentSet.getColumn(C_RENT_DEPOSIT).setEditor(createFormattedCellEditor());
//		this.tblRentSet.getColumn(C_RENT_DEPOSIT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblRentSet.getColumn(C_RENT_DEPOSIT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRentSet.getColumn(C_RENT_FIRST_RENT).setEditor(createFormattedCellEditor());
		this.tblRentSet.getColumn(C_RENT_FIRST_RENT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRentSet.getColumn(C_RENT_FIRST_RENT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblRentSet.getColumn(C_RENT_TENANCY_MODEL).setEditor(createComboCellEditor(TenancyModeEnum.getEnumList()));
//		this.tblRentSet.getColumn(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(true);
		this.tblRentSet.getColumn(C_RENT_RENT_TYPE).setEditor(createComboCellEditor(RentTypeEnum.getEnumList()));
//		this.tblRentSet.getColumn(C_RENT_RENT_TYPE).getStyleAttributes().setLocked(true);

		this.tblIncrease.getColumn(C_INC_INCREASE_DATE).setEditor(createDateCellEditor());
		this.tblIncrease.getColumn(C_INC_INCREASE_DATE).getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		this.tblIncrease.getColumn(C_INC_INCREASE_TYPE).setEditor(createComboCellEditor(IncreasedRentTypeEnum.getEnumList()));
		this.tblIncrease.getColumn(C_INC_INCREASESTYLE).setEditor(createComboCellEditor(IncreaseStyleEnum.getEnumList()));
		this.tblIncrease.getColumn(C_INC_VALUE).setEditor(createFormattedCellEditor());
		this.tblIncrease.getColumn(C_INC_VALUE).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblIncrease.getColumn(C_INC_VALUE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblFree.getColumn(C_FREE_START_DATE).setEditor(createDateCellEditor());
		this.tblFree.getColumn(C_FREE_START_DATE).getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		this.tblFree.getColumn(C_FREE_END_DATE).setEditor(createDateCellEditor());
		this.tblFree.getColumn(C_FREE_END_DATE).getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		this.tblFree.getColumn(C_FREE_TENANCY_TYPE).setEditor(createComboCellEditor(FreeTenancyTypeEnum.getEnumList()));
		this.tblFree.getColumn(C_FREE_DES).setEditor(createTxtCellEditor(255, true));
		
		ItemAction actionAddIncrease = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddIncrease_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};
		ItemAction actionRmIncrease = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRmIncrease_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};
		ItemAction actionAddFree = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddFree_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};
		ItemAction actionRmFree = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRmFree_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};

		btnAddIncrease = initWorkBtn1(actionAddIncrease, "imgTbtn_sortstandard", this.containerIncrease, "添加");
		btnRmIncrease = initWorkBtn1(actionRmIncrease, "imgTbtn_sortstandard", this.containerIncrease, "删除");
		
		btnAddFree = initWorkBtn1(actionAddFree, "imgTbtn_sortstandard", this.containerFree, "添加");
		btnRmFree = initWorkBtn1(actionRmFree, "imgTbtn_sortstandard", this.containerFree, "删除");
		
		btnAddFree.setVisible(isFreeAdd);
		btnRmFree.setVisible(isFreeAdd);
		this.tblFree.setEnabled(isFreeAdd);
		
		contRentFreeBill.setVisible(!isFreeAdd);
	}

	private KDTDefaultCellEditor createDateCellEditor() {
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		return dateEditor;
	}

	private KDWorkButton initWorkBtn1(Action action, String iconName, KDContainer parentContainer, String text) {
		action.putValue(Action.SMALL_ICON, EASResource.getIcon(iconName));
		KDWorkButton btn = new KDWorkButton();
		btn = (KDWorkButton) parentContainer.add(action);
		btn.setToolTipText(text);
		btn.setText(text);
		return btn;
	}

	private void actionAddIncrease_actionPerformed(ActionEvent e) {
		IRow row = this.tblIncrease.addRow();
		row.getCell(C_INC_INCREASE_TYPE).setValue(IncreasedRentTypeEnum.Percent);
		row.getCell(C_INC_INCREASESTYLE).setValue(IncreaseStyleEnum.Rent);
	}


	private void actionRmIncrease_actionPerformed(ActionEvent e) throws BOSException {
		int activeRowIndex = this.tblIncrease.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblIncrease.getRowCount();
		}
		this.tblIncrease.removeRow(activeRowIndex);

		reSetRentSetInfo(getTenRoomListFromView());
		updatePayListInfo();
	}

	private void actionAddFree_actionPerformed(ActionEvent e) {
		IRow row = this.tblFree.addRow();
		row.getCell("freeTenancyType").setValue(FreeTenancyTypeEnum.FreeTenNotMoney);
		
	}

	private void actionRmFree_actionPerformed(ActionEvent e) throws BOSException {
		int activeRowIndex = this.tblFree.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblFree.getRowCount();
		}
		this.tblFree.removeRow(activeRowIndex);
		updatePayListInfo();
	}

	private TenancyContractTypeEnum getCurrentTenancyType() {
		if (this.comboTenancyType.getSelectedItem() == null) {
			log.error("控制不严谨，笨呀。");
			this.abort();
		}
		return (TenancyContractTypeEnum) this.comboTenancyType.getSelectedItem();
	}

	/**
	 * 根据房间封装一个租赁房间分录 <br>
	 * 将标准租金,建筑面积,房间长编码,户型等信息备份在租赁房间分录上 <br>
	 * 默认设置成交价格为房间的标准价格,设置到期标记为未知,设置租赁房间状态为新租
	 * @param room 房间对象
	 * @return 租赁房间分录对象
	 * */
	private TenancyRoomEntryInfo roomToTenRoomEntry(RoomInfo room) {
		if (room == null) {
			return null;
		}
		TenancyRoomEntryInfo tenRoom = new TenancyRoomEntryInfo();
		tenRoom.setRoom(room);
		tenRoom.setTenancyModel(room.getTenancyModel());

		tenRoom.setHandleState(HandleStateEnum.NoHandleRoom);

		tenRoom.setStandardRentType(room.getRentType());
		tenRoom.setStandardRoomRent(room.getStandardRent());
//		tenRoom.setStandardRoomRentPrice(room.getStandardRentPrice());

		// 执行租价默认赋标准租金价格
		tenRoom.setDealRentType(room.getRentType());
		if(tenRoom.getDealRoomRent() != null){
			tenRoom.setDepositAmount(new BigDecimal("2").multiply(tenRoom.getDealRoomRent()));
		}
		tenRoom.setFirstPayAmount(tenRoom.getDealRoomRent());

		// 快照房间的建筑面积和实测面积
		// 优先取实测建筑,如果没有则取建筑面积
		//取房间的计租面积  by zhendui_ai (上面两句话的逻辑不要了)
		BigDecimal buildingArea = room.getTenancyArea();
//		if (buildingArea == null || buildingArea.compareTo(FDCHelper.ZERO) == 0) {
//			buildingArea = room.getTenancyArea();
//		}
		tenRoom.setBuildingArea(buildingArea);
		//租金
		tenRoom.setDealRoomRent(room.getStandardRent());
		//成交租金单价
		
		if(room.getStandardRent()!=null&&buildingArea!=null){
			tenRoom.setDealRoomRentPrice(room.getStandardRent().divide(buildingArea, 2,BigDecimal.ROUND_HALF_UP));
		}
		//标准租金单价
		if(room.getStandardRent()!=null&&buildingArea!=null){
			tenRoom.setStandardRoomRentPrice(room.getStandardRent().divide(buildingArea, 2,BigDecimal.ROUND_HALF_UP));
		}
		//日租金
		if(room.getDayPrice()!=null){
			tenRoom.setDayPrice(room.getDayPrice());
		}
		
		BigDecimal roomArea = room.getActualRoomArea();
		if (roomArea == null || roomArea.compareTo(FDCHelper.ZERO) == 0) {
			roomArea = room.getRoomArea();
		}
		tenRoom.setRoomArea(roomArea);

		//快照房间  装修标准，朝向，景观，户型，房屋形式，建筑性质，产品类型，产品描述，房屋用途
		if(room.getDecoraStd()!=null)
		{
			tenRoom.setFitmentStandard(room.getDecoraStd().getName());
		}		
		tenRoom.setDirection(room.getDirection());
		tenRoom.setSight(room.getSight());
		tenRoom.setRoomModel(room.getRoomModel());
		tenRoom.setRoomForm(room.getRoomForm());

		tenRoom.setBuildingProperty(room.getBuildingProperty());
		tenRoom.setProductType(room.getProductType());
		tenRoom.setProductDetail(room.getProductDetail());
		tenRoom.setRoomUsage(room.getRoomUsage());

		//设置默认的房间到期标志和房间租赁状态
		tenRoom.setFlagAtTerm(FlagAtTermEnum.Unknown);
		tenRoom.setTenRoomState(TenancyStateEnum.newTenancy);

		// 获得长编码 //该长编码可能要在Room上加字段,此处直接从房间信息上getLongNumber
//		StringBuffer sbRoomLongNum = new StringBuffer();
//		BuildingInfo building = room.getBuilding();
//		SellProjectInfo sellPro = (building == null) ? null : building.getSellProject();
//
//		final String spitStr = "-";
//		if (sellPro != null) {
//			sbRoomLongNum.append(sellPro.getName());
//			sbRoomLongNum.append(spitStr);
//		}
//		if (building != null) {
//			sbRoomLongNum.append(building.getName());
//			sbRoomLongNum.append(spitStr);
//		}
//		if (room.getUnit() != 0) {
//			sbRoomLongNum.append(room.getUnit());
//			sbRoomLongNum.append(spitStr);
//		}
//		sbRoomLongNum.append(room.getNumber());

		tenRoom.setRoomLongNum(room.getName());

		return tenRoom;
	}

	private void addRoomRows(TenancyRoomEntryCollection tenRooms) {
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			addRoomRow(tenRoom);
		}
	}

	/**
	 * 根据一项租赁房间分录对象在房间列表中增加一行
	 * 
	 * @param tenancyRoom
	 * */
	private void addRoomRow(TenancyRoomEntryInfo tenancyRoom) {
		if (tenancyRoom == null) {
			return;
		}
		RoomInfo room = tenancyRoom.getRoom();
		// if(room == null){//这里ROOM可能为Null的
		// return;
		// }
		
		IRow row = this.tblRoom.addRow();
		row.setUserObject(tenancyRoom);

		row.getCell(C_ROOM_TEN_ROOM_STATE).setValue(tenancyRoom.getTenRoomState());
		row.getCell(C_ROOM_ROOM).setValue(tenancyRoom.getRoom().getName());

		row.getCell(C_ROOM_STANDARD_RENT).setValue(tenancyRoom.getStandardRoomRent());
		row.getCell(C_ROOM_STANDARD_RENT_TYPE).setValue(tenancyRoom.getStandardRentType());
		row.getCell(C_ROOM_STANDARD_RENT_PRICE).setValue(tenancyRoom.getStandardRoomRentPrice());
		row.getCell(C_ROOM_DEAL_RENT).setValue(tenancyRoom.getDealRoomRent());
		row.getCell(C_ROOM_DEAL_RENT_TYPE).setValue(tenancyRoom.getDealRentType());

		row.getCell(C_ROOM_DEAL_RENT_PRICE).setValue(tenancyRoom.getDealRoomRentPrice());
		row.getCell(C_ROOM_FLAG_AT_TERM).setValue(tenancyRoom.getFlagAtTerm());
		row.getCell(C_ROOM_DES).setValue(tenancyRoom.getDescription());
		row.getCell(C_ROOM_BUILDING_AREA).setValue(tenancyRoom.getBuildingArea());

		row.getCell(C_ROOM_FITMENT).setValue(tenancyRoom.getFitmentStandard());
		row.getCell(C_ROOM_ROOM_MODEL).setValue(tenancyRoom.getRoomModel() == null ? null : tenancyRoom.getRoomModel().getName());
		row.getCell(C_ROOM_DIRECTION).setValue(tenancyRoom.getDirection() == null ? null : tenancyRoom.getDirection().getName());
		if (room != null) {
			row.getCell(C_ROOM_FLOOR).setValue(new Integer(room.getFloor()));
		}

		row.getCell(C_ROOM_ACT_DELIVER_DATE).setValue(tenancyRoom.getActDeliveryRoomDate());
		row.getCell(C_ROOM_ACT_QUIT_DATE).setValue(tenancyRoom.getActQuitTenDate());
		//增加日租金  xin_wang 2010.12.28
		row.getCell(C_ROOM_DAYPRICE).setValue(tenancyRoom.getDayPrice_());
		// 默认标准日租金 by huanghefh
		if(row.getCell("standardRentType")!=null&&row.getCell("standardRentType").getValue()!=null&&((RentTypeEnum)row.getCell("standardRentType").getValue()).getValue()!=null){
			String rentTypeName = ((RentTypeEnum)row.getCell("standardRentType").getValue()).getValue();
			if(tenancyRoom.getStandardRoomRentPrice()!=null&&row.getCell("dayPrice")!=null){
				row.getCell("dayPrice").setValue(toDayPrice(rentTypeName,tenancyRoom.getStandardRoomRentPrice()));
			}
		}
		// 如果房间有物业属性，增加物业页签中的一项
		// 由于租赁的房间可能被合并，故房间可能为空，但察看时仍需要看到其物业页签
		if (room == null || (room != null && room.isIsForPPM())) {
			TenancyPropertyUI com = null;
			try {
				com = (TenancyPropertyUI) UIFactoryHelper.initUIObject(TenancyPropertyUI.class.getName(), this.getUIContext(), null, "VIEW");
				com.setRoomInfo(tenancyRoom);
			} catch (UIException e) {
				this.handleException(e);
			}
			com.loadTenEntrys();
			this.tabMiddle.add(tenancyRoom.getRoomLongNum(), com);
		}

		//更新租金设置Table
		//		updateTblRentSetRow();
	}
	boolean isEdit=true;
	boolean isFreeAdd=false;
	boolean isPeriodShow=true;
	/** 初始化控件基础属性,主要设置菜单,控件可否编辑(且状态不会变化) */
	private void initControl() {
		this.pkStartDate.setRequired(true);
		this.pkEndDate.setRequired(true);
		this.txtPromissoryAgentFee.setEnabled(false);
		this.pkPromissoryAppPayDate.setEnabled(false);
		this.actionAuditResult.setVisible(false);
		this.menuTable1.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionSave.setVisible(false);

		this.actionCreateFrom.setVisible(false);

		this.actionAddLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);

		this.txtTotalBuildingArea.setEditable(false);
		this.txtTotalRoomStandardRent.setEditable(false);
		this.txtTotalRoomDealRent.setEditable(false);

		this.txtTotalAttachResStandardRent.setEditable(false);
		this.txtTotalAttachResDealRent.setEditable(false);

		this.f7SellProject.setEnabled(false);

		this.f7OldContract.setEnabled(false);
		this.comboTenancyType.setEnabled(false);

		// this.f7SellProject.setEnabled(false);
		this.txtFirstPayRent.setEnabled(false);
		this.txtDepositAmount.setEnabled(false);
		this.txtRemainDepositAmount.setEnabled(false);
		this.txtLeaseCount.setEnabled(false);
		this.txtDealTotalRent.setEnabled(false);
		this.txtStandardTotalRent.setEnabled(false);

		SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 100000, 1);
		this.spinChargeOffsetDays.setModel(model);
		model = new SpinnerNumberModel(1, 1, 100000, 1);
		this.spinLeaseTime.setModel(model);

		SHEHelper.setTextFormat(this.txtPromissoryAgentFee);
		SHEHelper.setTextFormat(this.txtAgentFee);

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

		if (spinChargeOffsetDaysChangeListener == null) {
			spinChargeOffsetDaysChangeListener = new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					try {
						spinChargeOffsetDays_stateChanged(e);
					} catch (Exception exc) {
						handUIException(exc);
					} finally {
					}
				}
			};
			this.spinChargeOffsetDays.addChangeListener(spinChargeOffsetDaysChangeListener);
		}
		
		try {
			HashMap hmParamIn = new HashMap();
			hmParamIn.put("ISOTHEREDIT", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			hmParamIn.put("ISFREEADD", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			hmParamIn.put("ISPERIODSHOW", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
		
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			
			if(hmAllParam.get("ISOTHEREDIT")!=null){
				isEdit=Boolean.valueOf(hmAllParam.get("ISOTHEREDIT").toString()).booleanValue();
			}
			if(hmAllParam.get("ISFREEADD")!=null){
				isFreeAdd=Boolean.valueOf(hmAllParam.get("ISFREEADD").toString()).booleanValue();
			}
			if(hmAllParam.get("ISPERIODSHOW")!=null){
				isPeriodShow=Boolean.valueOf(hmAllParam.get("ISPERIODSHOW").toString()).booleanValue();
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	//注KDSpinner控件的界面更新通过内部的一个ChangeListener来完成,所有不能直接将所有的listener删除,这里定义业务处理监听器
	// ,便于在detachListeners()时去掉监听
	private ChangeListener spinLeaseTimeChangeListener = null;
	private ChangeListener spinChargeOffsetDaysChangeListener = null;

	/** 初始化房间列表 */
	private void initRoomTable() {
		this.tblRoom.checkParsed();
		this.tblRoom.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		this.tblRoom.getColumn(C_ROOM_DEAL_RENT).getStyleAttributes().setHided(true);
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT_TYPE).getStyleAttributes().setHided(true);
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT_PRICE).getStyleAttributes().setHided(true);

		this.tblRoom.getColumn(C_ROOM_TEN_ROOM_STATE).setEditor(createTxtCellEditor(80, false));
		this.tblRoom.getColumn(C_ROOM_ROOM).setEditor(createTxtCellEditor(80, false));
		this.tblRoom.getColumn(C_ROOM_FLOOR).setEditor(createTxtCellEditor(80, false));

		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT).setEditor(createFormattedCellEditor(false));
		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRoom.getColumn(C_ROOM_DAYPRICE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		KDComboBox comboField = new KDComboBox();
		List list = RentTypeEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT_TYPE).setEditor(comboEditor);
		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT_TYPE).getStyleAttributes().setLocked(true);

		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT_PRICE).setEditor(createFormattedCellEditor(false));
		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT_PRICE).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT_PRICE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblRoom.getColumn(C_ROOM_DEAL_RENT).setEditor(createFormattedCellEditor());
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		//by huanghefh
		this.tblRoom.getColumn("dayPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn("dayPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRoom.getColumn("dayPrice").getStyleAttributes().setLocked(true);
		comboField = new KDComboBox();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT_TYPE).setEditor(comboEditor);

		this.tblRoom.getColumn(C_ROOM_DEAL_RENT_PRICE).setEditor(createFormattedCellEditor());
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT_PRICE).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT_PRICE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		comboField = new KDComboBox();
		list = FlagAtTermEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblRoom.getColumn(C_ROOM_FLAG_AT_TERM).setEditor(comboEditor);
		this.tblRoom.getColumn(C_ROOM_DES).setEditor(createTxtCellEditor(80, true));

		this.tblRoom.getColumn(C_ROOM_BUILDING_AREA).setEditor(createFormattedCellEditor(false));
		this.tblRoom.getColumn(C_ROOM_BUILDING_AREA).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn(C_ROOM_BUILDING_AREA).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblRoom.getColumn(C_ROOM_FITMENT).setEditor(createTxtCellEditor(80, false));
		this.tblRoom.getColumn(C_ROOM_ROOM_MODEL).setEditor(createTxtCellEditor(80, false));
		this.tblRoom.getColumn(C_ROOM_DIRECTION).setEditor(createTxtCellEditor(80, false));
		this.tblRoom.getColumn(C_ROOM_ACT_DELIVER_DATE).getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		this.tblRoom.getColumn(C_ROOM_ACT_DELIVER_DATE).getStyleAttributes().setLocked(true);
		this.tblRoom.getColumn(C_ROOM_ACT_QUIT_DATE).getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		this.tblRoom.getColumn(C_ROOM_ACT_QUIT_DATE).getStyleAttributes().setLocked(true);
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionAddRoom.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)this.panelRoom.add(this.actionAddRoom);
		btnAddRowinfo.setText("添加");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionRemoveRoom.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) panelRoom.add(this.actionRemoveRoom);
		btnDeleteRowinfo.setText("删除");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
	}



	/** 初始化配套资源列表 */
	private void initAttachResTable() {
		this.tblAttachRes.checkParsed();
		this.tblAttachRes.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		// TODO


		this.tblAttachRes.getStyleAttributes().setLocked(true);
	}

	/** 初始化客户列表 
	 * @throws BOSException 
	 * @throws EASBizException */
	private void initCustomeTable() throws EASBizException, BOSException {
		this.tblCustomer.checkParsed();
		this.tblCustomer.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblCustomer.getColumn(C_CUS_BOOK_DATE).getStyleAttributes().setLocked(true);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblCustomer.getColumn(C_CUS_PROPERTY_PERCENT).setEditor(numberEditor);
		this.tblCustomer.getColumn(C_CUS_PROPERTY_PERCENT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblCustomer.getColumn(C_CUS_PROPERTY_PERCENT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblCustomer.getColumn(C_CUS_BOOK_DATE).getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn(C_CUS_DES).setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn(C_CUS_PHONE).setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn(C_CUS_MAIL_ADDRESS).setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn(C_CUS_POSTALCODE).setEditor(txtEditor);
		KDComboBox comboField = new KDComboBox();
		List list = CertifacateNameEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblCustomer.getColumn(C_CUS_CERTIFICATE_NAME).setEditor(comboEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn(C_CUS_CERTIFICATE_NUMBER).setEditor(txtEditor);
		this.tblCustomer.getColumn(C_CUS_CERTIFICATE_NUMBER).setRequired(true);
		// setCustomerInfoEnable(true);
		
		this.tblCustomer.getColumn(C_CUS_CUSTOMER).getStyleAttributes().setFontColor(Color.BLUE);
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionAddCustomer.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)this.panelCustomer.add(this.actionAddCustomer);
		btnAddRowinfo.setText("添加");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionRemoveCustomer.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) panelCustomer.add(this.actionRemoveCustomer);
		btnDeleteRowinfo.setText("删除");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
	}
	/**
	 * 初始化违约金方案设置 add by yangfan
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void initLiquidatedTable() throws EASBizException, BOSException {
		this.tblLiquidated.checkParsed();
		this.tblLiquidated.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblLiquidated.getColumn(C_LIQUI_MONEYDEFINE).getStyleAttributes().setLocked(true);
		
		KDBizPromptBox f7Customer = new KDBizPromptBox();
		f7Customer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
		f7Customer.setEditable(true);
		f7Customer.setDisplayFormat("$name$");
		f7Customer.setEditFormat("$number$");
		f7Customer.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Customer);
		this.tblLiquidated.getColumn(C_LIQUI_MONEYDEFINE).setEditor(f7Editor);
		
		
		for(int i=0;i<tblLiquidated.getRowCount();i++){
			
			KDBizPromptBox f7Customer1 = new KDBizPromptBox();
			f7Customer1.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.LiquidatedQuery");
			f7Customer1.setEditable(true);
			f7Customer1.setDisplayFormat("$name$");
			f7Customer1.setEditFormat("$number$");
			f7Customer1.setCommitFormat("$number$");
			
			if(this.f7SellProject.getValue()!=null&&tblLiquidated.getRow(i).getCell(C_LIQUI_MONEYDEFINE).getValue()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();

				filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id", ((MoneyDefineInfo)tblLiquidated.getRow(i).getCell(C_LIQUI_MONEYDEFINE).getValue()).getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", "1"));
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", ((SellProjectInfo)this.f7SellProject.getValue()).getId().toString()));
				
				view.setFilter(filter);
				
				f7Customer1.setEntityViewInfo(view);
			}
			ICellEditor f7Editor1 = new KDTDefaultCellEditor(f7Customer1);
			this.tblLiquidated.getRow(i).getCell(C_LIQUI_LIQUIDATED).setEditor(f7Editor1);
		}
	}
	
	/**
	 * 获得租期集合
	 * @return List<List<Date[2]>>,其中date[2]表示一个月的首日和尾日,List<Date[2]>表示月份集合,即1个期
	 * */
	private List getLeaseListFromView() {
		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkEndDate.getValue();
		FirstLeaseTypeEnum firstLeaseType = (FirstLeaseTypeEnum) this.comboFirstLeaseType.getSelectedItem();
		Date firstLeaseEndDate = (Date) this.pkFirstLeaseEndDate.getValue();
		int leaseTime = this.spinLeaseTime.getIntegerVlaue().intValue();
		return TenancyHelper.getLeaseList(startDate, endDate, firstLeaseType, firstLeaseEndDate, leaseTime);
	}

	protected void pkFirstLeaseEndDate_dataChanged(DataChangeEvent e) throws Exception {		
		//如果是完整首期，不进行租金刷新。其实如果是完整首期，首期结束日期控件压根都隐藏了
		if(isWholeFirstLeaseFromView()){
			return;
		}
		updatePayListInfo();
	}

	/** 根据界面上的值返回是否是完整首期 */
	private boolean isWholeFirstLeaseFromView(){
		return FirstLeaseTypeEnum.WholeFirstLease.equals(this.comboFirstLeaseType.getSelectedItem());
	}

	/**
	 * 根据首期类型设置控件
	 */
	protected void comboFirstLeaseType_itemStateChanged(ItemEvent e) throws Exception {
		setFirstLeaseEndDateVisible();
		updatePayListInfo();
	}

	private void setFirstLeaseEndDateVisible() {
		if(FirstLeaseTypeEnum.UserDefined.equals(this.comboFirstLeaseType.getSelectedItem())){
			this.contFirstLeaseEndDate.setVisible(true);
		}else{
			this.contFirstLeaseEndDate.setVisible(false);
		}
	}

	/**
	 * 租金递增事件
	 */
	protected void tblIncrease_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		String colKey = this.tblIncrease.getColumnKey(colIndex);
		// 当值没有变化时,不进行更新操作
		if (!valueChanged(e)) {
			return;
		}

		IRow row = this.tblIncrease.getRow(e.getRowIndex());
		Date startDate = (Date) this.pkStartDate.getValue();
		startDate = FDCDateHelper.getDayBegin(startDate);
		Date endDate = (Date)row.getCell("increaseDate").getValue();
		if(endDate==null)
		{
			MsgBox.showInfo("递增日期不能为空");
			this.abort();
		}
		endDate = FDCDateHelper.getDayBegin(endDate);
		if (!startDate.before(endDate)) {
			MsgBox.showInfo(this, "递增列表的递增日期必须大于合同起始日期！");
			row.getCell("increaseDate").setValue(null);
			this.abort();
		}
		ICell cell = row.getCell(colIndex);
		if(cell == null){
			return;
		}
		
		if(C_INC_INCREASE_TYPE.equals(colKey)  &&  IncreasedRentTypeEnum.Handwork.equals(cell.getValue())){
			row.getCell(C_INC_VALUE).setValue(null);
			row.getCell(C_INC_VALUE).getStyleAttributes().setLocked(true);
		}else{
			row.getCell(C_INC_VALUE).getStyleAttributes().setLocked(false);
		}

		//重置租金分录
		reSetRentSetInfo(getTenRoomListFromView());

		updatePayListInfo();
	}

	/**
	 * 免租table编辑事件
	 */
	protected void tblFree_editStopped(KDTEditEvent e) throws Exception {
		updatePayListInfo();
	}

	/**
	 * 将租金设置分录的对象其实都维护在租赁房间上。这里只需要更新租金设置分录的值
	 * 在udpatePaylistInfo时getTenRoomListFromView获得了租赁房间即获得了该租金设置信息
	 * */
	protected void tblRentSet_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		String colKey = this.tblRentSet.getColumnKey(colIndex);
		// 当值没有变化时,不进行更新操作
		if (!valueChanged(e)) {
			return;
		}
		IRow row = this.tblRentSet.getRow(e.getRowIndex());
		if(row.getUserObject()==null)
		{
			for(int k=0;k<monDefineColl.size();k++)
			{
				MoneyDefineInfo moneyInfo = monDefineColl.get(k);
				if(moneyInfo.getNumber().equals(colKey)){
					BigDecimal deposit = (BigDecimal) e.getValue();
					BigDecimal rentGather = new BigDecimal(0);
					int j=0;
					for(int i=0;i<tblRentSet.getRowCount();i++)
					{
						IRow row2 = tblRentSet.getRow(i);
						if(row2.getUserObject() instanceof TenancyRoomEntryInfo)
						{
							++j;
							ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2.getUserObject();
							BigDecimal area = tenEntry.getBuildingArea();
							//BigDecimal desposit2 = deposit.multiply(area).divide(rentArea, 2,BigDecimal.ROUND_HALF_UP);
							BigDecimal desposit2 = new BigDecimal(0);
							//rentGather = rentGather.add(desposit2);
							if(j!=tblRoom.getRowCount())
							{
								desposit2 = deposit.multiply(area).divide(rentArea,2,BigDecimal.ROUND_HALF_UP);
								rentGather = rentGather.add(desposit2);

							}else
							{
								desposit2 = deposit.subtract(rentGather);
							}
							row2.getCell(colKey).setValue(desposit2);
							tenEntry.setDepositAmount(desposit2);
						}
					}
				}
			}
			if(C_RENT_FIRST_RENT.equals(colKey))
			{
				BigDecimal firstRent = (BigDecimal) e.getValue();
				BigDecimal rentGather = new BigDecimal(0);
				int j=0;
				for(int i=0;i<tblRentSet.getRowCount();i++)
				{
					IRow row2 = tblRentSet.getRow(i);
					if(row2.getUserObject() instanceof TenancyRoomEntryInfo)
					{
						++j;
						ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2.getUserObject();
						BigDecimal area = tenEntry.getBuildingArea();
						//BigDecimal firstRent2 = firstRent.multiply(area).divide(rentArea, 2,BigDecimal.ROUND_HALF_UP);
						BigDecimal firstRent2 = new BigDecimal(0);
						rentGather = rentGather.add(firstRent2);
						if(j!=tblRoom.getRowCount())
						{
							firstRent2 = firstRent.multiply(area).divide(rentArea,2,BigDecimal.ROUND_HALF_UP);
							rentGather = rentGather.add(firstRent2);

						}else
						{
							firstRent2 = firstRent.subtract(rentGather);
						}
						row2.getCell(C_RENT_FIRST_RENT).setValue(firstRent2);
						tenEntry.setFirstPayAmount(firstRent2);
					}
				}
			}else if(C_RENT_RENT_TYPE.equals(colKey))
			{
				RentTypeEnum rentType= (RentTypeEnum) e.getValue();
				for(int i=0;i<tblRentSet.getRowCount();i++)
				{
					IRow row2 = tblRentSet.getRow(i);
					if(row2.getUserObject() instanceof TenancyRoomEntryInfo)
					{
						ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2.getUserObject();
						row2.getCell(C_RENT_RENT_TYPE).setValue(rentType);
						tenEntry.setDealRentType(rentType);
					}
				}
				reSetRentSetInfo(getTenRoomListFromView());
			}else if(colKey.startsWith(PREFIX_C_RENT_RENT))
			{
				BigDecimal rentGather = new BigDecimal(0);
				BigDecimal amount = (BigDecimal) e.getValue();
				ICell priceCell = row.getCell(colIndex - 1);
				BigDecimal price = amount.divide(rentArea,2,BigDecimal.ROUND_HALF_UP);
				priceCell.setValue(price);
				int j=0;
				for(int i=0;i<tblRentSet.getRowCount();i++)
				{
					IRow row2 = tblRentSet.getRow(i);					
					if(row2.getUserObject() instanceof TenancyRoomEntryInfo)
					{
						//把尾数问题在最后一个房间解决
						++j;
						ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2.getUserObject();
						BigDecimal area = tenEntry.getBuildingArea();
						BigDecimal amount2 = new BigDecimal(0);
						//如果是最后一个房间。租金就不是乘百分比了。而是用汇总租金减去上面房间的租金之和，这样
						//租金汇总起来就不会有小数问题了
						if(j!=tblRoom.getRowCount())
						{
							amount2 = amount.multiply(area).divide(rentArea,2,BigDecimal.ROUND_HALF_UP);
							rentGather = rentGather.add(amount2);

						}else
						{
							amount2 = amount.subtract(rentGather);
						}
						row2.getCell(colKey).setValue(amount2);
						BigDecimal amountPrice = amount2.divide(area, 2,BigDecimal.ROUND_HALF_UP);
						tenEntry.setDealRent(amount2);
						tenEntry.setDealRentPrice(amountPrice);
					}

				}
				reSetRentSetInfo(getTenRoomListFromView());
			}else if(colKey.startsWith(PREFIX_C_RENT_PERIODICITY))
			{
				BigDecimal periodictityGather = new BigDecimal(0);
				BigDecimal amount = (BigDecimal) e.getValue();
				row.getCell(colKey).setValue(amount);
				ICell priceCell = row.getCell(colIndex - 1);
				BigDecimal price = amount.divide(rentArea,2,BigDecimal.ROUND_HALF_UP);
				priceCell.setValue(price);
				int j=0;
				for(int i=0;i<tblRentSet.getRowCount();i++)
				{					
					IRow row2 = tblRentSet.getRow(i);					
					if(row2.getUserObject() instanceof TenancyRoomEntryInfo)
					{
						++j;
						IDealAmountInfo deal = (IDealAmountInfo)row2.getCell(colKey).getUserObject();
						ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2.getUserObject();
						BigDecimal area = tenEntry.getBuildingArea();
						BigDecimal amount2 = new BigDecimal(0);
						if(j!=tblRoom.getRowCount())
						{
							amount2 = amount.multiply(area).divide(rentArea,2,BigDecimal.ROUND_HALF_UP);
							periodictityGather = periodictityGather.add(amount2);
						}else
						{
							amount2 = amount.subtract(periodictityGather);
						}
						row2.getCell(colKey).setValue(amount2);
						BigDecimal amountPrice = amount2.divide(area, 2,BigDecimal.ROUND_HALF_UP);
						deal.setAmount(amount2);
						deal.setPriceAmount(amountPrice);
						//						tenEntry.setDealRent(amount2);
						//						tenEntry.setDealRentPrice(amountPrice);
					}

				}
				reSetRentSetInfo(getTenRoomListFromView());
			}
		}else
		{
			ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row.getUserObject();

			for(int i=0;i<monDefineColl.size();i++)
			{
				MoneyDefineInfo moneyInfo = monDefineColl.get(i);
				if(moneyInfo.getNumber().equals(colKey))
				{
					BigDecimal depositAmount = (BigDecimal) e.getValue();
					row.getCell(colKey).setValue(depositAmount);
					IObjectCollection dealAmounts = tenEntry.getDealAmounts_();
					for(int j=0; j<dealAmounts.size(); j++){
						IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(j);
						MoneyDefineInfo money = dealAmount.getMoneyDefine();
						if(money.equals(moneyInfo))
						{
							dealAmount.setAmount(depositAmount);
						}						
					}	
					reSetRentSetInfo(getTenRoomListFromView());
				}			
			}
			 if(C_RENT_FIRST_RENT.equals(colKey)){
				tenEntry.setFirstPayAmount((BigDecimal) e.getValue());
//								return;
			}else if(C_RENT_RENT_TYPE.equals(colKey)){
				RentTypeEnum rentType= (RentTypeEnum) e.getValue();
				tenEntry.setDealRentType(rentType);

				//虽然一个租赁房间多时间段的租金类别是统一设置的，但数据结构上仍是一个租金分录设置1个租金类别

				IObjectCollection dealAmounts = tenEntry.getDealAmounts_();
				for(int i=0; i<dealAmounts.size(); i++){
					IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(i);
					dealAmount.setRentType(rentType);
				}
			}else if(C_RENT_TENANCY_MODEL.equals(colKey)){//配套资源的这列被锁了，能修改的肯定是房间列
				TenancyModeEnum tenModel = (TenancyModeEnum) e.getValue();
				tenEntry.setTenancyModel(tenModel);
				//还要设置该行的单价列和总价列哪个可编辑
				if(TenancyModeEnum.TenancyRentModel.equals(tenModel)){
					for(int i=0; i<this.tblRentSet.getColumnCount(); i++){
						String tColKey = this.tblRentSet.getColumnKey(i);
						ICell cell = row.getCell(tColKey);
						if(tColKey.startsWith(PREFIX_C_RENT_RENT_PRICE)){
							cell.getStyleAttributes().setLocked(true);
						}else if(tColKey.startsWith(PREFIX_C_RENT_RENT)){
							DealAmountEntryInfo dealAmount = (DealAmountEntryInfo) cell.getUserObject();
							if(dealAmount.isIsHandwork()){
								cell.getStyleAttributes().setLocked(false);
							}
						}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY)){
							cell.getStyleAttributes().setLocked(false);
						}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)){
							cell.getStyleAttributes().setLocked(true);
						}
					}
				}else{
					for(int i=0; i<this.tblRentSet.getColumnCount(); i++){
						String tColKey = this.tblRentSet.getColumnKey(i);
						ICell cell = row.getCell(tColKey);
						if(tColKey.startsWith(PREFIX_C_RENT_RENT_PRICE)){
							DealAmountEntryInfo dealAmount = (DealAmountEntryInfo) cell.getUserObject();
							if(dealAmount.isIsHandwork()){
								cell.getStyleAttributes().setLocked(false);
							}
						}else if(tColKey.startsWith(PREFIX_C_RENT_RENT)){
							cell.getStyleAttributes().setLocked(true);
						}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY)){
							cell.getStyleAttributes().setLocked(true);
						}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)){
							cell.getStyleAttributes().setLocked(false);
						}
					}
				}
				reSetRentSetInfo(getTenRoomListFromView());
			}else if(colKey.startsWith(PREFIX_C_RENT_RENT) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY)){
				ICell cell = row.getCell(colIndex);
				IDealAmountInfo dealAmount = (IDealAmountInfo) cell.getUserObject();

				BigDecimal amount = (BigDecimal) e.getValue();
				if(amount==null)amount = new BigDecimal(0);
				dealAmount.setAmount(amount);

				BigDecimal area = tenEntry.getBuildingArea();
				BigDecimal price = null;
				//还要反算单价
				if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
					if(amount == null){
						amount = FDCHelper.ZERO;
					}
					price = amount.divide(area, 2, BigDecimal.ROUND_HALF_UP);

					ICell priceCell = row.getCell(colIndex - 1);
					priceCell.setValue(price);
					dealAmount.setPriceAmount(price);
				}

				//如果是第一定价期的列，需要更新到租赁房间的成交总价上去. TODO 这里用colIndex=6来判断不太好 
				if(colIndex == 7+monDefineColl.size()){
					tenEntry.setDealRent(amount);
					tenEntry.setDealRentPrice(price);
					//这里不好处理，简单处理，修改了第一定价期的列，才重置租金分录	
					//					reSetRentSetInfo(getTenRoomListFromView());
				}
				reSetRentSetInfo(getTenRoomListFromView());
			}else if(colKey.startsWith(PREFIX_C_RENT_RENT_PRICE) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)){
				ICell cell = row.getCell(colIndex);
				DealAmountEntryInfo dealAmount = (DealAmountEntryInfo) cell.getUserObject();

				BigDecimal price = (BigDecimal) e.getValue();
				dealAmount.setPriceAmount(price);

				TenancyModeEnum tenancyModel = dealAmount.getTenancyRoom().getTenancyModel();
				BigDecimal area = tenEntry.getBuildingArea();
				
				//改 by zhendui_ai   也就是现在没有了
//				if(TenancyModeEnum.RoomAreaMode.equals(tenancyModel)){
//					area = tenEntry.getRoomArea();
//				}
				if(area == null) area = FDCHelper.ZERO;
				BigDecimal amount = null;
				//还要反算总价
				if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
					if(price == null){
						price = FDCHelper.ZERO;
					}

					amount = price.multiply(area).setScale(2, BigDecimal.ROUND_HALF_UP);

					ICell amountCell = row.getCell(colIndex + 1);
					amountCell.setValue(amount);
					dealAmount.setAmount(amount);
				}

				//如果是第一定价期的列，需要更新到租赁房间的成交总价上去. TODO 这里用colIndex=6来判断不太好 
				if(colIndex == 6+monDefineColl.size()){
					tenEntry.setDealRent(amount);
					tenEntry.setDealRentPrice(price);
					//这里不好处理，简单处理，修改了第一定价期的列，才重置租金分录	
					//					reSetRentSetInfo(getTenRoomListFromView());
				}
				//因为周期性费用也需要递增，所以改了周期性费用也需要重置租金分录
				reSetRentSetInfo(getTenRoomListFromView());
			}
		}
		if(!this.chkIsFreeContract.isSelected())
		{
			updatePayListInfo();
		}
	}
	//日单价计算 @by huanghefh
	public BigDecimal toDayPrice(String type,BigDecimal price){
		if(type==null) return new BigDecimal(0);
		if(RentTypeEnum.RENTBYYEAR_VALUE.equals(type)) return price.divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP);
		if(RentTypeEnum.RENTBYQUARTER_VALUE.equals(type)) return price.divide(new BigDecimal(90), 2, BigDecimal.ROUND_HALF_UP);
		if(RentTypeEnum.RENTBYMONTH_VALUE.equals(type)) return price.divide(new BigDecimal(30), 2, BigDecimal.ROUND_HALF_UP);
		if(RentTypeEnum.RENTBYWEEK_VALUE.equals(type)) return price.divide(new BigDecimal(7), 2, BigDecimal.ROUND_HALF_UP);
		if(RentTypeEnum.RENTBYDAY_VALUE.equals(type)) return price;
		
		return price;
	}
	

	private boolean valueChanged(KDTEditEvent e){
		if (e.getOldValue() != null) {
			if(e.getOldValue() instanceof BigDecimal  &&  e.getValue() != null){
				return ((BigDecimal)e.getOldValue()).compareTo((BigDecimal)e.getValue()) != 0;
			}

			if (e.getOldValue().equals(e.getValue())) {				
				return false;
			}
		}else{
			if(e.getValue() == null){
				return false;
			}
		}
		return true;
	}

	/**
	 * 从tblRoom界面上获取租赁房间集合
	 * */
	private TenancyRoomEntryCollection getTenRoomListFromView() {
		TenancyRoomEntryCollection tenancyRooms = new TenancyRoomEntryCollection();// 房间

		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			TenancyRoomEntryInfo tenancyRoom = (TenancyRoomEntryInfo) row.getUserObject();

			//			BigDecimal dealRent = (BigDecimal) row.getCell(C_ROOM_DEAL_RENT).getValue();
			//			tenancyRoom.setDealRoomRent(dealRent);
			//
			//			RentTypeEnum dealRentType = (RentTypeEnum) row.getCell(C_ROOM_DEAL_RENT_TYPE).getValue();
			//			tenancyRoom.setDealRentType(dealRentType);
			//
			//			BigDecimal dealRentPrice = (BigDecimal) row.getCell(C_ROOM_DEAL_RENT_PRICE).getValue();
			//			tenancyRoom.setDealRoomRentPrice(dealRentPrice);
			// 设置租赁房间状态
			row.getCell(C_ROOM_TEN_ROOM_STATE).setValue(tenancyRoom.getTenRoomState());

			tenancyRooms.add(tenancyRoom);
		}
		setTenRoomsState(tenancyRooms);
		return tenancyRooms;
	}

	/**
	 * 从tblAttachRes界面上获得附属资源集合
	 * */
	private TenAttachResourceEntryCollection getTenAttachResListFromView() {
		TenAttachResourceEntryCollection tenAttachReses = new TenAttachResourceEntryCollection();
		for (int i = 0; i < this.tblAttachRes.getRowCount(); i++) {
			IRow row = this.tblAttachRes.getRow(i);
			TenAttachResourceEntryInfo tenAttachRes = (TenAttachResourceEntryInfo) row.getUserObject();
			// TODO 附属资源分录赋值
			//			row.getCell(C_ATTACH_TEN_ROOM_STATE).setValue(tenancyRoom.getTenRoomState());
			tenAttachReses.add(tenAttachRes);
		}
		if(tenAttachReses.size()>0)
		{
			setTenAttachsState(tenAttachReses);
		}		
		return tenAttachReses;
	}

	/**
	 * 根据租赁房间和租赁配套资源刷新付款明细列表的列
	 * @param tenancyRooms
	 * @param tenAttachReses
	 */
	private void updatePayListColumn(TenancyRoomEntryCollection tenancyRooms, TenAttachResourceEntryCollection tenAttachReses) {
		// 付款分录信息都记录在tenancyRoomInfo的付款分录集合中
		this.tblPayList.removeRows();
		this.tblPayList.removeColumns();
		this.tblPayList.getTreeColumn().setDepth(2);

		IColumn column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_LEASE_SEQ);
		column.setEditor(createTxtCellEditor(80, false));

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_MONEY_DEFINE);
		column.setEditor(createTxtCellEditor(80, false));

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_START_DATE);
		column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		column.getStyleAttributes().setLocked(true);

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_END_DATE);
		column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		column.getStyleAttributes().setLocked(true);

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_APP_PAY_DATE);
		column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);

		// column = this.tblPayList.addColumn();
		// column.setKey(C_PAYS_FREE_DAYS);
		// column.getStyleAttributes().setNumberFormat();

		for (int i = 0; i < tenancyRooms.size(); i++) {
			TenancyRoomEntryInfo tenancyRoom = tenancyRooms.get(i);
			column = this.tblPayList.addColumn();
			// column.setUserObject(tenancyRoom);// 将租赁房间邦定在列上,当修改金额时。。TODO

			StringBuffer key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ROOM).append(i).append(POSTFIX_C_PAYS_APP_AMOUNT);
			column.setKey(key.toString());
			column.setEditor(this.createFormattedCellEditor());
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

		for (int i = 0; i < tenAttachReses.size(); i++) {
			TenAttachResourceEntryInfo tenAttachRes = tenAttachReses.get(i);
			column = this.tblPayList.addColumn();
			// column.setUserObject(tenAttachRes);//将租赁资源邦定在列上,当修改金额时。。TODO
			StringBuffer key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ATTACH_RES).append(i).append(POSTFIX_C_PAYS_APP_AMOUNT);
			column.setKey(key.toString());
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

			column = this.tblPayList.addColumn();
			key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ATTACH_RES).append(i).append(POSTFIX_C_PAYS_ACT_AMOUNT);
			column.setKey(key.toString());
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

			column = this.tblPayList.addColumn();
			key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ATTACH_RES).append(i).append(POSTFIX_C_PAYS_ACT_PAY_DATE);
			column.setKey(key.toString());
			column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
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

	/**
	 * 根据租期和租赁配套资源的价格,将各配套资源的付款明细注入到租赁房间中
	 * @param digit 
	 * @param toIntegerType 
	 * @param isToInteger 
	 * @param daysPerYear 
	 * @param rentCountType 
	 * */
	private void fillTenAttachResPayList(TenAttachResourceEntryCollection tenAttachReses, List leaseList, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear, 
			boolean isToInteger, ToIntegerTypeEnum toIntegerType, DigitEnum digit,boolean isToIntegerFee, ToIntegerTypeEnum toIntegerTypeFee, DigitEnum digitFee) throws BOSException {
		try {
			TenancyHelper.fillTenEntryPayList(tenAttachReses, TenAttachResourcePayListEntryCollection.class, TenAttachResourcePayListEntryInfo.class, this.getDepositMoneyDefine(), this.getRentMoneyDefine(), leaseList, rentFrees, rentCountType, daysPerYear, 
					isToInteger, toIntegerType, digit,isToIntegerFee, toIntegerTypeFee, digitFee, (ChargeDateTypeEnum) this.comboChargeDateType.getSelectedItem(), this.spinChargeOffsetDays.getIntegerVlaue().intValue(),(Date)this.pkTenancyDate.getValue(),(Date)this.DPickFromMonth.getValue(),this.spinLeaseTime.getIntegerVlaue().intValue());
		} catch (InstantiationException e) {
			throw new BOSException(e);
		} catch (IllegalAccessException e) {
			throw new BOSException(e);
		}
	}

	/**
	 * 设置配套资源付款明细
	 * @param tenAttachReses
	 * @throws BOSException
	 */
	private void fillTenAttachResPayList(TenAttachResourceEntryCollection tenAttachReses) throws BOSException
	{
		for (int i = 0; i < tenAttachReses.size(); i++) {
			TenAttachResourceEntryInfo tenAttach = tenAttachReses.get(i);

			TenAttachResourcePayListEntryCollection payList = tenAttach.getAttachResPayList();

			TenAttachResourcePayListEntryCollection newPayList = new TenAttachResourcePayListEntryCollection();
			TenAttachResourcePayListEntryInfo attachDepositPay = null;
			TenAttachResourcePayListEntryInfo attachFirstPay = null;
			if(payList.size()>0)
			{
				attachDepositPay = payList.get(0);
				attachFirstPay = payList.get(1);
			}else
			{
				attachDepositPay = new TenAttachResourcePayListEntryInfo();
				attachFirstPay = new TenAttachResourcePayListEntryInfo();
				attachDepositPay.setTenAttachRes(tenAttach);
				attachFirstPay.setTenAttachRes(tenAttach);
			}
			attachDepositPay.setAppAmount(tenAttach.getDepositAmount());
			attachDepositPay.setLeaseSeq(1);
			attachDepositPay.setSeq(0);
			attachDepositPay.setAppPayDate(null);
			attachDepositPay.setStartDate(null);
			attachDepositPay.setEndDate(null);
			attachDepositPay.setMoneyDefine(getDepositMoneyDefine());
			newPayList.add(attachDepositPay);
			payList.clear();
			payList.addCollection(newPayList);
		}
	}

	/**
	 * 设置租赁房间付款明细
	 * @param tenancyRooms
	 * @throws BOSException
	 */
	private void fillTenRoomPayList(TenancyRoomEntryCollection tenancyRooms) throws BOSException {		
		for (int i = 0; i < tenancyRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoomEntry = tenancyRooms.get(i);
			TenancyRoomPayListEntryCollection roomPayList = tenRoomEntry.getRoomPayList();
			TenancyRoomPayListEntryCollection newRoomPayList = new TenancyRoomPayListEntryCollection();
			TenancyRoomPayListEntryInfo roomDepositPay = null;
			if (roomPayList.size() > 0) {// 如果原本就有付款明细,则押金和首期租金肯定同时存在
				// 这种处理方式主要是为了保留原有付款明细记录的ID值,使得最终提交认租单时,
				// 对该条付款明细记录执行的是update操作
				roomDepositPay = roomPayList.get(0);// 押金
			} else {
				roomDepositPay = new TenancyRoomPayListEntryInfo();// 押金
				roomDepositPay.setTenRoom(tenRoomEntry);
			}
			roomDepositPay.setAppAmount(tenRoomEntry.getDepositAmount());
			roomDepositPay.setLeaseSeq(1);
			roomDepositPay.setSeq(0);
			roomDepositPay.setAppPayDate(null);
			roomDepositPay.setStartDate(null);
			roomDepositPay.setEndDate(null);
			roomDepositPay.setMoneyDefine(getDepositMoneyDefine());
			newRoomPayList.add(roomDepositPay);
			roomPayList.clear();
			roomPayList.addCollection(newRoomPayList);
		}

	}
	/**
	 * 根据租期和租赁房间价格,将各房间付款明细注入到租赁房间中
	 * @param tenancyRooms
	 * @param leaseList
	 *            租期集合
	 * @param rentFrees
	 *            免租分录集合
	 * @param digit 
	 * @param toIntegerType 
	 * @param isToInteger 
	 * @param daysPerYear 
	 * @param rentCountType 
	 * @throws BOSException
	 * */
	private void fillTenRoomPayList(TenancyRoomEntryCollection tenancyRooms, List leaseList, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear,
			boolean isToInteger, ToIntegerTypeEnum toIntegerType, DigitEnum digit,boolean isToIntegerFee, ToIntegerTypeEnum toIntegerTypeFee, DigitEnum digitFee) throws BOSException {
		try {//添加两个参数  收租周期、第二期应收日期
			TenancyHelper.fillTenEntryPayList(tenancyRooms, TenancyRoomPayListEntryCollection.class, TenancyRoomPayListEntryInfo.class, this.getDepositMoneyDefine(), this.getRentMoneyDefine(), leaseList, rentFrees, rentCountType, daysPerYear, 
					isToInteger, toIntegerType, digit,isToIntegerFee, toIntegerTypeFee, digitFee, (ChargeDateTypeEnum) this.comboChargeDateType.getSelectedItem(), this.spinChargeOffsetDays.getIntegerVlaue().intValue(),(Date)this.pkFristLeaseDate.getValue(),(Date)this.DPickFromMonth.getValue(),this.spinLeaseTime.getIntegerVlaue().intValue());
		} catch (InstantiationException e) {
			throw new BOSException(e);
		} catch (IllegalAccessException e) {
			throw new BOSException(e);
		}
	}

	private MoneyDefineInfo depositMoneyName = null;// 押金款项类型
	private MoneyDefineInfo rentMoneyName = null;// 租金款项类型

	private MoneyDefineInfo getDepositMoneyDefine() throws BOSException {
		if(depositMoneyName == null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.DepositAmount));
			filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.TenancySys));
			view.setFilter(filter);
			MoneyDefineCollection moneyNames = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
			if (!moneyNames.isEmpty()) {
				depositMoneyName = moneyNames.get(0);
			}
		}
		if (depositMoneyName == null) {
			MsgBox.showInfo(this, "请先定义押金类型的款项类型！");
			abort();
		}
		return depositMoneyName;
	}

	protected void pkStartDate_dataChanged(DataChangeEvent e) throws Exception {
		//重置租赁房间租金分录
		reSetRentSetInfo(getTenRoomListFromView());
		updatePayListInfo();
		if(this.pkStartDate.getValue()!=null && this.pkEndDate.getValue()!=null)
		{
			isFreeConSet(this.chkIsFreeContract.isSelected());
		}		
	}

	protected void pkEndDate_dataChanged(DataChangeEvent e) throws Exception {
		//重置租赁房间租金分录
		reSetRentSetInfo(getTenRoomListFromView());
		updatePayListInfo();
		if(this.pkStartDate.getValue()!=null && this.pkEndDate.getValue()!=null)
		{
			isFreeConSet(this.chkIsFreeContract.isSelected());
		}
	}

	protected void tblRoom_editStopped(KDTEditEvent e) throws Exception {
		super.tblRoom_editStopped(e);

//		/* 房间列表不再进行租金的设置，故不需要再刷新合同付款明细信息
		// 如果修改房间租金,则需要反写租金单价,更新付款明细
		int colIndex = e.getColIndex();
		String colKey = this.tblRoom.getColumnKey(colIndex);

		if (!C_ROOM_FLAG_AT_TERM.equals(colKey)) {
			return;
		}
		// 当值没有变化时,不进行更新操作
		if (e.getOldValue() != null) {
			if (e.getOldValue().equals(e.getValue())) {
				return;
			}
		}

		IRow row = this.tblRoom.getRow(e.getRowIndex());
		TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo) row.getUserObject();

		tenRoom.setFlagAtTerm((FlagAtTermEnum)row.getCell(C_ROOM_FLAG_AT_TERM).getValue());
		
//		RentTypeEnum rentType = (RentTypeEnum) row.getCell(C_ROOM_DEAL_RENT_TYPE).getValue();
//		BigDecimal rent = (BigDecimal) row.getCell(C_ROOM_DEAL_RENT).getValue();
//		BigDecimal area = (BigDecimal) row.getCell(C_ROOM_BUILDING_AREA).getValue();
//
//		tenRoom.setDealRentType(rentType);
//		tenRoom.setDealRoomRent(rent);
//		if (rent == null) {
//			rent = FDCHelper.ZERO;
//		}
//
//		if (C_ROOM_DEAL_RENT.equals(colKey)) {
//			BigDecimal rentPrice = null;
//			if (area != null && FDCHelper.ZERO.compareTo(area) != 0) {
//				rentPrice = rent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
//			}
//			row.getCell(C_ROOM_DEAL_RENT_PRICE).setValue(rentPrice);
//			tenRoom.setDealRoomRentPrice(rentPrice);
//		}
//		updatePayListInfo();
//		 */
	}

	protected void tblAttachRes_editStopped(KDTEditEvent e) throws Exception {
		super.tblAttachRes_editStopped(e);
	}

	protected void comboFlagAtTerm_itemStateChanged(ItemEvent e) throws Exception {
		super.comboFlagAtTerm_itemStateChanged(e);
		// TODO 将对房间分录的到期标志做反写
		// TODO 此需求暂不处理
	}

	protected void comboChargeDateType_itemStateChanged(ItemEvent e) throws Exception {
		if(ChargeDateTypeEnum.FixedDate.equals((ChargeDateTypeEnum)comboChargeDateType.getSelectedItem())){
			this.contFromMonth.setVisible(true);
			this.contChargeOffsetDays.setVisible(false);
		}else{
			this.contFromMonth.setVisible(false);
			this.contChargeOffsetDays.setVisible(true);
		}
		updatePayListInfo();
	}

	private void spinLeaseTime_stateChanged(ChangeEvent e) throws Exception {
		updatePayListInfo();
	}

	private void spinChargeOffsetDays_stateChanged(ChangeEvent e) throws Exception {
		updatePayListInfo();
	}
	boolean isEditPayList=false;
	protected void tblPayList_editStopped(KDTEditEvent e) throws Exception {		
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
		isEditPayList=true;
		updatePayListInfo();
		isEditPayList=false;
	}
	/**
	 * @修改人: 高明
	 * @修改时间: 2010-9-7
	 * @描述: 当存在相同客户时提示不能添加
	 */
	protected void tblCustomerInfo_editStopped(KDTEditEvent e) throws Exception {
		super.tblCustomerInfo_editStopped(e);
		
		if (e.getColIndex() == 1) {
			IRow row = this.tblCustomer.getRow(e.getRowIndex());
			int i=row.getRowIndex()-1;
			FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell(C_CUS_CUSTOMER).getValue();
			if(customer==null){
				return;
			}
			boolean flag=true;
			// 对比相同客户 tim_gao 2010-9-7
			while(i>=0&&flag){
				FDCCustomerInfo compra = (FDCCustomerInfo)this.tblCustomer.getRow(i).getCell(C_CUS_CUSTOMER).getValue();
				if(customer.getId().toString().equals(compra.getId().toString())){
					MsgBox.showWarning(this, "与第"+(i+1)+"行客户相同,禁止添加!");
					flag=false;
				}
				i--;
			}
			if (customer != null&&flag) {
				row.getCell(C_CUS_PHONE).setValue(customer.getPhone());
				row.getCell(C_CUS_POSTALCODE).setValue(customer.getPostalcode());
				row.getCell(C_CUS_CERTIFICATE_NAME).setValue(customer.getCertificateName());
				row.getCell(C_CUS_CERTIFICATE_NUMBER).setValue(customer.getCertificateNumber());
				row.getCell(C_CUS_MAIL_ADDRESS).setValue(customer.getMailAddress());
				row.getCell(C_CUS_BOOK_DATE).setValue(customer.getCreateTime());
			} else {
				row.getCell(C_CUS_PHONE).setValue(null);
				row.getCell(C_CUS_POSTALCODE).setValue(null);
				row.getCell(C_CUS_CERTIFICATE_NAME).setValue(null);
				row.getCell(C_CUS_CERTIFICATE_NUMBER).setValue(null);
				row.getCell(C_CUS_MAIL_ADDRESS).setValue(null);
				row.getCell(C_CUS_BOOK_DATE).setValue(null);
			}
		}
	}

	/**
	 * 计算各个房间,配套资源整个出租期的租金.更新到成交租金,标准租金和房间总租金,配套资源总租金
	 * */
	private void updateTotalRent() {
		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkEndDate.getValue();

		boolean hasRentDate = true;
		//如果起始和结束日期为空，
		if (startDate == null || endDate == null) {
			hasRentDate = false;
		}else{
			startDate = FDCDateHelper.getDayBegin(startDate);
			endDate = FDCDateHelper.getDayBegin(endDate);	
		}

		BigDecimal totalRoomStandardRent = FDCHelper.ZERO;
		BigDecimal totalRoomDealRent = FDCHelper.ZERO;
		BigDecimal totalBuildingArea = FDCHelper.ZERO;

		BigDecimal totalAttachStandardRent = FDCHelper.ZERO;
		BigDecimal totalAttachDealRent = FDCHelper.ZERO;

		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			TenancyRoomEntryInfo tenancyRoom = (TenancyRoomEntryInfo) row.getUserObject();
			RentTypeEnum standardRentType = tenancyRoom.getStandardRentType();
			standardRentType = standardRentType==null?RentTypeEnum.RentByMonth:standardRentType;
			BigDecimal standardRent = tenancyRoom.getStandardRoomRent();
			standardRent = standardRent==null?FDCHelper.ZERO:standardRent;
			if(hasRentDate){
				BigDecimal rent2 = new BigDecimal(0); 
				if(TenancyHelper.getRentBetweenDate(startDate, endDate, standardRentType, standardRent)!=null)
				{
					rent2 = TenancyHelper.getRentBetweenDate(startDate, endDate, standardRentType, standardRent);
				}
				totalRoomStandardRent = totalRoomStandardRent.add(rent2);
				BigDecimal totalRent = FDCHelper.ZERO;
				if(tenancyRoom.getRoomTotalRent()!=null)
				{
					totalRent = tenancyRoom.getRoomTotalRent();
				}
				totalRoomDealRent = totalRoomDealRent.add(totalRent);
			}

			BigDecimal buildingArea = tenancyRoom.getBuildingArea();
			totalBuildingArea = totalBuildingArea.add(buildingArea == null ? FDCHelper.ZERO : buildingArea);
		}

		for (int i = 0; i < this.tblAttachRes.getRowCount(); i++) {
			IRow row = this.tblAttachRes.getRow(i);
			TenAttachResourceEntryInfo tenAttach = (TenAttachResourceEntryInfo) row.getUserObject();

			RentTypeEnum standardRentType = tenAttach.getStandardRentType();
			BigDecimal standardRent = tenAttach.getStandardAttachResRent();
			if(hasRentDate){
				totalAttachStandardRent = totalRoomStandardRent.add(TenancyHelper.getRentBetweenDate(startDate, endDate, standardRentType, standardRent));
				BigDecimal toalRent = new BigDecimal(0);
				if(tenAttach.getAttachTotalRent()==null)
				{
					toalRent = new BigDecimal(0);
				}else
				{
					toalRent = tenAttach.getAttachTotalRent();
				}
				totalAttachDealRent = totalRoomDealRent.add(toalRent);
			}
		}

		this.txtTotalRoomStandardRent.setValue(totalRoomStandardRent);
		this.txtTotalRoomDealRent.setValue(totalRoomDealRent);
		this.txtTotalBuildingArea.setValue(totalBuildingArea);

		this.txtTotalAttachResStandardRent.setValue(totalAttachStandardRent);
		this.txtTotalAttachResDealRent.setValue(totalAttachDealRent);

		BigDecimal totalStandardRent = totalRoomStandardRent.add(totalAttachStandardRent);
		BigDecimal totalDealRent = totalRoomDealRent.add(totalAttachDealRent);

		this.txtStandardTotalRent.setValue(totalStandardRent);
		this.txtDealTotalRent.setValue(getDealTotalRent());
	}

	//合同总成交金额应该包括房间成交租金+周期性费用+配套资源成交租金+周期性费用
	//合同标准租金=房间标准租金+配套资源标准租金(标准租金没有标准周期性费用)
	//房间总成交金额=房间成交租金+周期性费用
	//房间总标准租金=房间标准租金
	private BigDecimal getDealTotalRent()
	{
		BigDecimal totalAmount = new BigDecimal(0);
		List appPayColKeys = getAppPayColKeys();
		for(int i=0;i<tblPayList.getRowCount();i++)
		{
			IRow row = this.tblPayList.getRow(i);
			if(row.getUserObject()!=null)
			{
				for (int j = 0; j < appPayColKeys.size(); j++) 
				{
					String key = (String) appPayColKeys.get(j);
					ICell cell = row.getCell(key);
					BigDecimal appAmount = (BigDecimal) cell.getValue();
					if(appAmount==null)
					{
						appAmount = new BigDecimal(0);
					}
					TenancyRoomPayListEntryInfo tenPayInfo = (TenancyRoomPayListEntryInfo)row.getCell(key).getUserObject();
					if(tenPayInfo!=null)
					{
						if(MoneyTypeEnum.RentAmount.equals(tenPayInfo.getMoneyDefine().getMoneyType()))
						{
							totalAmount = totalAmount.add(appAmount);
						}
					}				
				}
			}
		}
		return totalAmount;
	}

	/**
	 * 更新押金和首付租金
	 * */
	private void updateDepositAndFirstPayRent() {
		BigDecimal depositeAmount = FDCHelper.ZERO;
		BigDecimal firstPayAmount = FDCHelper.ZERO;

		if (this.tblPayList.getRowCount() != 0) {
			IRow depositeRow = this.tblPayList.getRow(0);
			BigDecimal tmp = (BigDecimal) depositeRow.getCell(C_PAYS_TOTAL_APP_PAY).getValue();
			if (tmp != null) {
				depositeAmount = tmp;
			}
			if(this.tblPayList.getRowCount()>1)
			{
				IRow firstPayRow = this.tblPayList.getRow(1);
				tmp = (BigDecimal) firstPayRow.getCell(C_PAYS_TOTAL_APP_PAY).getValue();
				if (tmp != null) {
					firstPayAmount = tmp;
				}
			}						
		}

		this.txtDepositAmount.setValue(depositeAmount);
		this.txtFirstPayRent.setValue(firstPayAmount);
	}

	/**
	 * 更新押金
	 * */
	private void updateDeposit() {
		BigDecimal depositeAmount = FDCHelper.ZERO;

		if (this.tblPayList.getRowCount() != 0) {
			IRow depositeRow = this.tblPayList.getRow(0);
			BigDecimal tmp = (BigDecimal) depositeRow.getCell(C_PAYS_TOTAL_APP_PAY).getValue();
			if (tmp != null) {
				depositeAmount = tmp;
			}
		}

		this.txtDepositAmount.setValue(depositeAmount);
	}

	/**
	 * 根据是否是自由式合同才确定是否需要更新付款明细
	 * 更新付款明细相关的信息 涉及 {增加房间,减少房间,增加附属资源,较少附属资源,修改房间实际租金,修改附属资源实际租金
	 * 修改租赁起始时间,结束时间,计租期间长度,免租天数,免租日是否计入租期 修改收租日类型,收租日偏移天数} 这些操作均需要执行该更新操作
	 * @throws BOSException
	 * */
	private void updatePayListInfo(boolean isFreeContract) throws BOSException
	{		
		RentCountTypeEnum rentCountType = (RentCountTypeEnum) this.comboRentCountType.getSelectedItem();
		int daysPerYear = this.txtDaysPerYear.getIntegerValue() == null ? 360 : this.txtDaysPerYear.getIntegerValue().intValue();
		boolean isToInteger = this.chkIsAutoToInteger.isSelected();
		ToIntegerTypeEnum toIntegerType = (ToIntegerTypeEnum) this.comboToIntegerType.getSelectedItem();
		DigitEnum digit = (DigitEnum) this.comboDigit.getSelectedItem();
		//租金设置 周期性费用设置
		boolean isToIntegerFee = this.chkIsAutoToIntegerForFee.isSelected();
		ToIntegerTypeEnum toIntegerTypeFee = (ToIntegerTypeEnum) this.comboToIntegerTypeFee.getSelectedItem();
		DigitEnum digitFee = (DigitEnum) this.comboDigitFee.getSelectedItem();
		
		RentFreeEntryCollection rentFrees = getRentFreesFromView();

		TenAttachResourceEntryCollection tenAttachReses = getTenAttachResListFromView();
		TenancyRoomEntryCollection tenancyRooms = getTenRoomListFromView();
		TenancyBillInfo tenRoomInfo = this.editData;

		List leaseList = getLeaseListFromView();
		// 获得租期数据		
		if (leaseList == null) {
			leaseList = new ArrayList();
		}

		if(isDynamicStartDate() && this.pkStartDate.getValue()==null)
		{
			this.txtLeaseCount.setValue(tenRoomInfo.getLeaseCount());
			fillTenRoomPayList(tenancyRooms);
			fillTenAttachResPayList(tenAttachReses);
			updatePayList(tenancyRooms, tenAttachReses, leaseList);
//			updateDeposit();
		}else
		{
			// 更新租期数
			updateLeaseCount(leaseList);
			if(!isFreeContract && (this.getOprtState().equals("ADDNEW") || this.getOprtState().equals("EDIT")))
			{
				fillTenRoomPayList(tenancyRooms, leaseList, rentFrees, rentCountType, daysPerYear, isToInteger, toIntegerType, digit,isToIntegerFee,toIntegerTypeFee,digitFee  );
				fillTenAttachResPayList(tenAttachReses, leaseList, rentFrees, rentCountType, daysPerYear, isToInteger, toIntegerType, digit,isToIntegerFee,toIntegerTypeFee,digitFee);
			}else
			{				
				fillFreeConTenRoomPayList(tenancyRooms);
			}
			// 更新付款明细列表
			if(this.getOprtState().equals("VIEW"))
			{
				tenancyRooms = this.editData.getTenancyRoomList();
			}
			if(!isEditPayList){
				updatePayList(tenancyRooms, tenAttachReses, leaseList);
			}
			// 更新合同押金和首付租金
//			updateDepositAndFirstPayRent();

			// 更新总标准租金,成交租金信息
			updateTotalRent();

			// 更新约定代理费
			updatePromissoryAgentFee();
		}
	}

	//自由式合同付款明细
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
	/**
	 * 更新付款明细相关的信息 涉及 {增加房间,减少房间,增加附属资源,较少附属资源,修改房间实际租金,修改附属资源实际租金
	 * 修改租赁起始时间,结束时间,计租期间长度,免租天数,免租日是否计入租期 修改收租日类型,收租日偏移天数} 这些操作均需要执行该更新操作
	 * @throws BOSException
	 * */
	public void updatePayListInfo() throws BOSException {
		updatePayListInfo(this.chkIsFreeContract.isSelected());
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

		for (int i = 0; i < tenAttachReses.size(); i++) {
			String attNum = tenAttachReses.get(i).getAttachResource().getName();
			headRow.getCell(PREFIX_C_PAYS_ATTACH_RES + i + POSTFIX_C_PAYS_APP_AMOUNT).setValue(attNum);
			headRow.getCell(PREFIX_C_PAYS_ATTACH_RES + i + POSTFIX_C_PAYS_ACT_AMOUNT).setValue(attNum);
			headRow.getCell(PREFIX_C_PAYS_ATTACH_RES + i + POSTFIX_C_PAYS_ACT_PAY_DATE).setValue(attNum);
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

		for (int i = 0; i < tenAttachReses.size(); i++) {
			headRow.getCell(PREFIX_C_PAYS_ATTACH_RES + i + POSTFIX_C_PAYS_APP_AMOUNT).setValue("应收金额");
			headRow.getCell(PREFIX_C_PAYS_ATTACH_RES + i + POSTFIX_C_PAYS_ACT_AMOUNT).setValue("实收金额");
			headRow.getCell(PREFIX_C_PAYS_ATTACH_RES + i + POSTFIX_C_PAYS_ACT_PAY_DATE).setValue("实收日期");
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
		if(!isEditPayList){
			cbMD.removeAllItems();
			cbMD.addItem(new KDComboBoxMultiColumnItem(new String[] { "" }));
		}
		Set md=new HashSet();
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
				if(!isEditPayList&&!md.contains(tPay.getMoneyDefine().getName())){
					cbMD.addItem(new KDComboBoxMultiColumnItem(new String[] { tPay.getMoneyDefine().getName() }));
					md.add(tPay.getMoneyDefine().getName());
				}
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
					if(!isEditPayList&&!md.contains(tPay.getMoneyDefine().getName())){
						cbMD.addItem(new KDComboBoxMultiColumnItem(new String[] { tPay.getMoneyDefine().getName() }));
						md.add(tPay.getMoneyDefine().getName());
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
						log.error("逻辑错误或脏数据,tenancyRoom.id:" + tenancyRoom.getId());
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

				for (int j = 0; j < tenAttachReses.size(); j++) {
					TenAttachResourceEntryInfo tenAttachRes = tenAttachReses.get(j);

					TenAttachResourcePayListEntryCollection tmpPayList = tenAttachRes.getAttachResPayList();
					TenAttachResourcePayListEntryInfo tmpPayEntry = tmpPayList.get(seq);
					if (tmpPayEntry == null) {
						log.error("逻辑错误或脏数据,tenancyRoom.id:" + tenAttachRes.getId());
						continue;
					}

					row.getCell(PREFIX_C_PAYS_ATTACH_RES + j + POSTFIX_C_PAYS_APP_AMOUNT).setValue(tmpPayEntry.getAppAmount());
					// tmpPayEntry只存储了tenancyRoom的ID值,这里设置tenancyRoom对象,
					// 便于在verifyPayList()时获得租赁房间的级联字段
					tmpPayEntry.setTenAttachRes(tenAttachRes);
					row.getCell(PREFIX_C_PAYS_ATTACH_RES + j + POSTFIX_C_PAYS_APP_AMOUNT).setUserObject(tmpPayEntry);
					row.getCell(PREFIX_C_PAYS_ATTACH_RES + j + POSTFIX_C_PAYS_ACT_AMOUNT).setValue(tmpPayEntry.getActAmount());
					row.getCell(PREFIX_C_PAYS_ATTACH_RES + j + POSTFIX_C_PAYS_ACT_PAY_DATE).setValue(tmpPayEntry.getActPayDate());

					totalAppPayThisLeas = totalAppPayThisLeas.add(tmpPayEntry.getAppAmount() == null ? FDCHelper.ZERO : tmpPayEntry.getAppAmount());
					totalActPayThisLeas = totalActPayThisLeas.add(tmpPayEntry.getActAmount() == null ? FDCHelper.ZERO : tmpPayEntry.getActAmount());
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

	/**
	 * 从界面上取值封装成免租分录集合
	 * */
	private RentFreeEntryCollection getRentFreesFromView() {
		RentFreeEntryCollection rentFrees = new RentFreeEntryCollection();
		for(int i=0; i<this.tblFree.getRowCount(); i++){
			IRow row = this.tblFree.getRow(i);
			Date startDate = (Date) row.getCell(C_FREE_START_DATE).getValue();
			Date endDate = (Date) row.getCell(C_FREE_END_DATE).getValue();
			FreeTenancyTypeEnum fttEnum = (FreeTenancyTypeEnum)row.getCell(C_FREE_TENANCY_TYPE).getValue();
			String des = (String) row.getCell(C_FREE_DES).getValue();

			if(startDate == null  ||  endDate == null){
				continue;
			}

			startDate = FDCDateHelper.getDayBegin(startDate);
			endDate = FDCDateHelper.getDayBegin(endDate);

			if(startDate.after(endDate)){
				//TODO 是否需要界面提示
				continue;
			}

			RentFreeEntryInfo rentFree = new RentFreeEntryInfo();
			rentFree.setFreeStartDate(startDate);
			rentFree.setFreeEndDate(endDate);
			rentFree.setFreeTenancyType(fttEnum);
			rentFree.setDescription(des);

			rentFrees.add(rentFree);
		}
		return rentFrees;
	}

	private boolean isDynamicStartDate()
	{
		return RentStartTypeEnum.DynamicStartDate.equals(this.comboRentStartType.getSelectedItem());
	}

	/** @deprecated 请调用带2个参数的该方法 */
	private void reSetRentSetInfo(TenancyRoomEntryCollection tenRooms) throws BOSException {
		reSetRentSetInfo(tenRooms, getTenAttachResListFromView());
	}

	/**
	 * 重新根据定价时间期生成租金分录,挂在租赁房间下。并刷新到界面。 <br>
	 * 以下情况才需要调用：修改租赁起始，结束日期；修改租金递增列表的日期；增加和删除房间
	 * {@link #pkStartDate_dataChanged(DataChangeEvent)} {@link #pkEndDate_dataChanged(DataChangeEvent)}
	 * {@link #tblIncrease_editStopped(KDTEditEvent)}
	 * {@link #btnAddRoom_actionPerformed(ActionEvent)} {@link #btnRemoveRoom_actionPerformed(ActionEvent)} <br>
	 * 一般重置租金后，均需要刷新付款明细，即需要调用{@link #updatePayListInfo()}
	 * */
	private void reSetRentSetInfo(TenancyRoomEntryCollection tenRooms, TenAttachResourceEntryCollection tenAttaches) throws BOSException {
		boolean isDynamicStartDate = isDynamicStartDate();
		IncreasedRentEntryCollection increasedRents = getIncreasedRentsFromView();
		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkEndDate.getValue();

		//保存已设置的周期性费用的值
		Map mapPrice = new HashMap();
		Map mapRent = new HashMap();	
		int m =0;	
		int n = 0;
		for(int i=0;i<tblRentSet.getRowCount();i++)
		{			
			IRow row = tblRentSet.getRow(i);
			String moneyName = "";
			String moneyPriceName = "";
			for(int j=0;j<tblRentSet.getColumnCount();j++)
			{
				IColumn column = this.tblRentSet.getColumn(j);
				String colKey = column.getKey();
				//				if(colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE))
				//				{
				//					String str = colKey.substring(PREFIX_C_RENT_PERIODICITY_PRICE.length(),colKey.length());
				//					StringBuffer strBuff = new StringBuffer(str);
				//					strBuff.append(i);
				//					BigDecimal price = (BigDecimal)row.getCell(colKey).getValue();
				//					if(price==null)
				//					{
				//						price = new BigDecimal(0);
				//					}
				//					mapPrice.put(strBuff.toString(), price);
				//				}else 
				if(colKey.startsWith(PREFIX_C_RENT_PERIODICITY))
				{
					//因为递增会把时间分段，当有多个周期性费用递增时用时间无法和保存时的时间一致
					//所以用款项类型加上第几行第几列来判断才是唯一的
					int index1 =colKey.indexOf("-");
					int index = colKey.indexOf("(");
					String str = colKey.substring(index1+1,index);
					if(!str.equals(moneyName))
					{
						m=0;
					}else
					{
						m++;
					}
					moneyName = str;
					StringBuffer strBuff = new StringBuffer(str);
					strBuff.append(i);
					strBuff.append(m);
					BigDecimal rent = (BigDecimal)row.getCell(colKey).getValue();
					if(rent==null)
					{
						rent = new BigDecimal(0);
					}
					mapRent.put(strBuff.toString(), rent);
				}else if(colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE))
				{
					//因为递增会把时间分段，当有多个周期性费用递增时用时间无法和保存时的时间一致
					//所以用款项类型加上第几行第几列来判断才是唯一的
					int index1 =colKey.indexOf("-");
					int index = colKey.indexOf("(");
					String str = colKey.substring(index1+1,index);
					str = str+"price";
					if(!str.equals(moneyPriceName))
					{
						n=0;
					}else
					{
						n++;
					}
					moneyPriceName = str;
					StringBuffer strBuff = new StringBuffer(str);
					strBuff.append(i);
					strBuff.append(n);
					BigDecimal rent = (BigDecimal)row.getCell(colKey).getValue();
					if(rent==null)
					{
						rent = new BigDecimal(0);
					}
					mapRent.put(strBuff.toString(), rent);
				}
			}
		}
		MoneyDefineCollection periodicityMoneys=new MoneyDefineCollection();
		if(isPeriodShow){
			//增加周期型费用
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.PeriodicityAmount));
			filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.TenancySys));
			//原来没有是否启用状态，周期性费用需要处理了
			//		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			view.setFilter(filter);
			periodicityMoneys = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
		}
	
		/*
		 * 逻辑介绍主要考虑两种情况，一种是新增，一种是新增的时候，房间一个一个的添加，
		 */
		TenancyRoomEntryCollection tenColl = getTenRoomListFromView();
		for(Iterator itor = tenColl.iterator();itor.hasNext();){
			Map map = new HashMap();
			TenancyRoomEntryInfo tenRoominfo = (TenancyRoomEntryInfo)itor.next();
			boolean flag =false;
			for(int i=0;i<tblRentSet.getRowCount();i++){
				IRow row = tblRentSet.getRow(i);
				TenancyRoomEntryInfo tenRoom =null;
				if(row.getUserObject()!=null){
					tenRoom = (TenancyRoomEntryInfo)row.getUserObject();
				}
				if(tenRoominfo.getRoom().getId().equals(tenRoom.getRoom().getId())){
					for(int j=0;j<monDefineColl.size();j++)
					{
							MoneyDefineInfo moneyInfo = monDefineColl.get(j);
							BigDecimal disAmount = new BigDecimal(0);
							if(row.getCell(moneyInfo.getNumber()).getValue()==null)
							{
								map.put(moneyInfo, disAmount);
							}else
							{
								disAmount = (BigDecimal)row.getCell(moneyInfo.getNumber()).getValue();
								map.put(moneyInfo, disAmount);
							}
						}
						roomDisMap.put(tenRoom.getRoom().getId().toString(), map);
						flag =true;
						break;
				}
			}
			if(flag){
				continue;
			}else{
				BigDecimal disAmount = new BigDecimal(0);
				for(int j=0;j<monDefineColl.size();j++)
					{
						MoneyDefineInfo moneyInfo = monDefineColl.get(j);
						
						map.put(moneyInfo, disAmount);
					}
					roomDisMap.put(tenRoominfo.getRoom().getId().toString(), map);
			}
		}

		try {
			isFirstEdit = TenancyHelper.reSetRentSetInfo(tenRooms,roomDisMap,increasedRents, startDate, endDate, periodicityMoneys, 
					DealAmountEntryCollection.class, DealAmountEntryInfo.class, getRentMoneyDefine(), isDynamicStartDate,mapPrice,mapRent,this.getOprtState(),isFirstEdit,oldState);
			isFirstEdit = TenancyHelper.reSetRentSetInfo(tenAttaches,roomDisMap,increasedRents, startDate, endDate, periodicityMoneys, 
					AttachDealAmountEntryCollection.class, AttachDealAmountEntryInfo.class, getRentMoneyDefine(), isDynamicStartDate,mapPrice,mapRent,this.getOprtState(),isFirstEdit,oldState);
		} catch (InstantiationException e) {
			throw new BOSException(e);
		} catch (IllegalAccessException e) {
			throw new BOSException(e);
		}

		if(isDynamicStartDate  &&  startDate == null){
			loadDynamicTblRentSet(tenRooms, tenAttaches);
		}else{
			loadTblRentSet(tenRooms, tenAttaches);
		}	
	}

	/**
	 * 从界面上取数并封装成租金递增分录集合 <br>
	 * 未设置递增日期和数值的均视为无效记录舍弃
	 * */
	private IncreasedRentEntryCollection getIncreasedRentsFromView() {
		IncreasedRentEntryCollection increasedRents = new IncreasedRentEntryCollection();
		for (int i = 0; i < this.tblIncrease.getRowCount(); i++) {
			IRow row = this.tblIncrease.getRow(i);
			Date increaseDate = (Date) row.getCell(C_INC_INCREASE_DATE).getValue();
			if (increaseDate == null) {
				continue;
			}

			IncreasedRentTypeEnum increasedRentType = (IncreasedRentTypeEnum) row.getCell(C_INC_INCREASE_TYPE).getValue();
			BigDecimal value = (BigDecimal) row.getCell(C_INC_VALUE).getValue();

			if (!IncreasedRentTypeEnum.Handwork.equals(increasedRentType)  &&  value == null) {
				continue;
			}

			IncreaseStyleEnum increaseStyle = (IncreaseStyleEnum) row.getCell(C_INC_INCREASESTYLE).getValue();

			IncreasedRentEntryInfo increasedRent = new IncreasedRentEntryInfo();
			increasedRent.setIncreaseDate(increaseDate);
			increasedRent.setIncreaseType(increasedRentType);
			increasedRent.setValue(value);
			increasedRent.setIncreaseStyle(increaseStyle);

			increasedRents.add(increasedRent);
		}
		return increasedRents;
	}

	/**
	 * 收租起始日期为动态开始日期时加载租金信息
	 * @param tenRooms
	 * @param tenAttaches
	 */
	private void loadDynamicTblRentSet(TenancyRoomEntryCollection tenRooms, TenAttachResourceEntryCollection tenAttaches) {
		this.tblRentSet.removeRows();
		/* 汇总押金 */
		BigDecimal depositGather = FDCHelper.ZERO;
		/* 汇总首租 */
		BigDecimal firstRentGather = FDCHelper.ZERO;
		RentTypeEnum rentTypeGather = RentTypeEnum.RentByMonth;
		rentArea = FDCHelper.ZERO;
		Map rentMap = new HashMap();
		Map rentPriceMap = new HashMap();
		BigDecimal standardRentGateher = FDCHelper.ZERO;
		//先将租金列删除，再根据设置的递增列表动态生成tblRentSet列
		List toDelColIndexes = new ArrayList();
		for (int i = 0; i < this.tblRentSet.getColumnCount(); i++) {
			IColumn column = this.tblRentSet.getColumn(i);
			String colKey = column.getKey();
			if (colKey.startsWith(PREFIX_C_RENT_RENT_PRICE) || colKey.startsWith(PREFIX_C_RENT_RENT)  
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				toDelColIndexes.add(new Integer(i));
			}
		}

		for (int i = toDelColIndexes.size() - 1; i >=0 ; i--) {
			int colIndex = ((Integer) toDelColIndexes.get(i)).intValue();
			this.tblRentSet.removeColumn(colIndex);
		}
		Map despositMap = new HashMap();
		//增加第一行时进行动态增加列
		boolean isFirstRow = true;
		for(int i=0; i<tenRooms.size(); i++){
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			IRow row = this.tblRentSet.addRow();
			row.setUserObject(tenRoom);
			row.getCell(C_RENT_ROOM).setValue(tenRoom.getRoomLongNum());
			TenancyModeEnum tenancyMode = TenancyModeEnum.TenancyRentModel;
			if(tenRoom.getTenancyModel()!=null)
			{
				tenancyMode = tenRoom.getTenancyModel();
			}
			row.getCell(C_RENT_TENANCY_MODEL).setValue(tenancyMode);
			RentTypeEnum rentType = RentTypeEnum.RentByMonth;
			if(tenRoom.getDealRentType() != null)
			{
				rentType=tenRoom.getDealRentType();
			}else{
				tenRoom.setDealRentType(rentType);
			}
			row.getCell(C_RENT_RENT_TYPE).setValue(rentType);
//			row.getCell(C_RENT_DEPOSIT).setValue(tenRoom.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenRoom.getFirstPayAmount());
			if(tenRoom.getDepositAmount()!=null)
			{
				depositGather = depositGather.add(tenRoom.getDepositAmount());
			}
			if(tenRoom.getFirstPayAmount()!=null)
			{
				firstRentGather = firstRentGather.add(tenRoom.getFirstPayAmount());
			}

			if(tenRoom.getBuildingArea()!=null)
			{
				rentArea = rentArea.add(tenRoom.getBuildingArea());
			}	
			//因为汇总行的租金类型总会和房间行的一样
			if(tenRoom.getDealRentType()!=null)
			{
				rentTypeGather = tenRoom.getDealRentType();
			}
			DealAmountEntryCollection dealAmounts = tenRoom.getDealAmounts();
			for(int k=0;k<dealAmounts.size();k++)
			{
				DealAmountEntryInfo dealAmount = dealAmounts.get(k);
				String moneyNumber = dealAmount.getMoneyDefine().getNumber();
				//把汇总行押金存起来
				if(despositMap.get(moneyNumber)==null)
				{
					depositGather = dealAmount.getAmount();
					despositMap.put(moneyNumber,depositGather);
				}else
				{
					BigDecimal amount = (BigDecimal)despositMap.get(moneyNumber);
					amount = amount ==null?new BigDecimal(0):amount;
					if(dealAmount.getAmount()==null)
					{
						depositGather = amount.add(new BigDecimal(0));
						despositMap.put(moneyNumber,depositGather);
					}else
					{
						depositGather = amount.add(dealAmount.getAmount());
						despositMap.put(moneyNumber,depositGather);
					}
					
				}
				{
					for(int j=0;j<monDefineColl.size();j++)
					{
						MoneyDefineInfo moneyInfo = monDefineColl.get(j);
						if(moneyNumber.equals(moneyInfo.getNumber()))
						{
							row.getCell(moneyNumber).setValue(dealAmount.getAmount());
						}					
					}
				}				
			}
			if(isFirstRow){
				for(int j=0; j<dealAmounts.size(); j++){
					DealAmountEntryInfo dealAmount = dealAmounts.get(j);
					if(!MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType()))
					{
						String keyRentPrice = PREFIX_C_RENT_RENT_PRICE;
						String keyRent = PREFIX_C_RENT_RENT;
						addTblRentSetColumn(keyRentPrice, keyRent, "租金设置");
					}					
				}
				isFirstRow = false;
			}

			for(int j=0; j<dealAmounts.size(); j++){
				DealAmountEntryInfo dealAmount = dealAmounts.get(j);
				if(!MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType()))
				{
				String moneyName = dealAmount.getMoneyDefine().getName();

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE;
				String keyRent = PREFIX_C_RENT_RENT;

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if(cellRentPrice != null  &&  cellRent != null){
					cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());

					if(rentMap.get(keyRent)!=null)
					{
						BigDecimal rent = (BigDecimal)rentMap.get(keyRent);
						rent = rent==null?new BigDecimal(0):rent;
						BigDecimal amount = new BigDecimal(0);
						if(dealAmount.getAmount()!=null)
						{
							amount = dealAmount.getAmount();
						}
						standardRentGateher = rent.add(amount);
						rentMap.put(keyRent, standardRentGateher);
						rentPriceMap.put(keyRent, keyRentPrice);
					}else
					{
						standardRentGateher = dealAmount.getAmount();
						standardRentGateher = standardRentGateher==null?new BigDecimal(0):standardRentGateher;
						rentMap.put(keyRent,standardRentGateher);
						rentPriceMap.put(keyRent, keyRentPrice);
					}

					//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
					cellRentPrice.setUserObject(dealAmount);
					cellRent.setUserObject(dealAmount);					

					//如果不是按总价计算，则锁定总价不可编辑
					if(!TenancyModeEnum.TenancyRentModel.equals(tenancyMode)){
						cellRent.getStyleAttributes().setLocked(true);
					}else{
						cellRentPrice.getStyleAttributes().setLocked(true);
					}
				}else{
					keyRentPrice = PREFIX_C_RENT_PERIODICITY_PRICE + moneyName;
					keyRent = PREFIX_C_RENT_PERIODICITY + moneyName;

					cellRentPrice = row.getCell(keyRentPrice);
					cellRent = row.getCell(keyRent);

					if(cellRentPrice != null  &&  cellRent != null){
						cellRentPrice.setValue(dealAmount.getPriceAmount());
						cellRent.setValue(dealAmount.getAmount());

						//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
						cellRentPrice.setUserObject(dealAmount);
						cellRent.setUserObject(dealAmount);

						//如果不是按总价计算，则锁定总价不可编辑
						if(!TenancyModeEnum.TenancyRentModel.equals(tenRoom.getTenancyModel())){
							cellRent.getStyleAttributes().setLocked(true);
						}else{
							cellRentPrice.getStyleAttributes().setLocked(true);
						}
					}
				}
			  }
			}
		}

		//刚开始加汇总行的时候默认是汇总房间的数据锁定非汇总的行
		if(isStandardRentSetting())
		{
			IRow rowGather = tblRentSet.addRow();
			rowGather.getStyleAttributes().setBackground(Color.YELLOW);
			rowGather.getCell(C_RENT_ROOM).setValue("租金汇总");
			rowGather.getCell(C_RENT_TENANCY_MODEL).setValue(TenancyModeEnum.TenancyRentModel);
			rowGather.getCell(C_RENT_RENT_TYPE).setValue(rentTypeGather);
			for(int i=0;i<monDefineColl.size();i++)
			{
				MoneyDefineInfo moneyInfo = monDefineColl.get(i);
				if(despositMap.get(moneyInfo.getNumber())!=null)
				{
					BigDecimal amount = (BigDecimal)despositMap.get(moneyInfo.getNumber());
					rowGather.getCell(moneyInfo.getNumber()).setValue(amount);
				}
			}
//			rowGather.getCell(C_RENT_DEPOSIT).setValue(depositGather);
			rowGather.getCell(C_RENT_FIRST_RENT).setValue(firstRentGather);
			rowGather.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(true);

			setRentAndPriceGather(rentMap,rentPriceMap,rowGather);
			lockTblRent();
		}

		for(int i=0; i<tenAttaches.size(); i++){
			TenAttachResourceEntryInfo tenAttach = tenAttaches.get(i);
			IRow row = this.tblRentSet.addRow();

			row.setUserObject(tenAttach);
			row.getCell(C_RENT_ROOM).setValue(tenAttach.getAttachLongNum());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(TenancyModeEnum.TenancyRentModel);
			row.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(true);
			row.getCell(C_RENT_RENT_TYPE).setValue(tenAttach.getDealRentType());
//			row.getCell(C_RENT_DEPOSIT).setValue(tenAttach.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenAttach.getFirstPayAmount());

			AttachDealAmountEntryCollection dealAmounts = tenAttach.getDealAmounts();

			if(isFirstRow){
				for(int j=0; j<dealAmounts.size(); j++){				
					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE;
					String keyRent = PREFIX_C_RENT_RENT;
					addTblRentSetColumn(keyRentPrice, keyRent, "租金设置");
				}
				isFirstRow = false;
			}
			for(int j=0; j<dealAmounts.size(); j++){
				AttachDealAmountEntryInfo dealAmount = dealAmounts.get(j);
				String moneyName = dealAmount.getMoneyDefine().getName();
				//String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE;
				String keyRent = PREFIX_C_RENT_RENT;			

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if(cellRentPrice != null  &&  cellRent != null){
					//					cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());

					//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
					//					cellRentPrice.setUserObject(dealAmount);
					cellRent.setUserObject(dealAmount);

					if(dealAmount.isIsHandwork()){
						//如果不是按总价计算，则锁定总价不可编辑
						cellRentPrice.getStyleAttributes().setLocked(true);
					}else{
						cellRentPrice.getStyleAttributes().setLocked(true);
						cellRent.getStyleAttributes().setLocked(true);
					}
				}else{
					keyRentPrice = PREFIX_C_RENT_PERIODICITY_PRICE + moneyName;
					keyRent = PREFIX_C_RENT_PERIODICITY + moneyName;

					cellRentPrice = row.getCell(keyRentPrice);
					cellRent = row.getCell(keyRent);

					if(cellRentPrice != null  &&  cellRent != null){
						//						cellRentPrice.setValue(dealAmount.getPriceAmount());
						cellRent.setValue(dealAmount.getAmount());

						//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
						cellRentPrice.setUserObject(dealAmount);
						cellRent.setUserObject(dealAmount);

						//如果不是按总价计算，则锁定总价不可编辑
						cellRentPrice.getStyleAttributes().setLocked(true);
					}
				}
			}
		}

		this.tblRentSet.getHeadMergeManager().mergeBlock(0, 0, 1, tblRentSet.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
		this.tblRentSet.setAutoscrolls(true);
	}

	//设置汇总行中租金和租金单价的值
	private void setRentAndPriceGather(Map rentMap,Map rentPriceMap,IRow rowGather)
	{
		Set rentSet = rentMap.keySet();
		Iterator rentIter = rentSet.iterator();
		while(rentIter.hasNext())
		{
			String rentKey = (String)rentIter.next();
			rowGather.getCell(rentKey).setValue(rentMap.get(rentKey));
			String rentPriceKey = (String)rentPriceMap.get(rentKey);
			BigDecimal value = new BigDecimal(0);
			if(rentMap.get(rentKey)!=null)
			{
				value = (BigDecimal)rentMap.get(rentKey);
			}
			rowGather.getCell(rentPriceKey).setValue(value.divide(rentArea, 2,BigDecimal.ROUND_HALF_UP));
			rowGather.getCell(rentKey).setUserObject(rentMap.get(rentKey));
			rowGather.getCell(rentPriceKey).getStyleAttributes().setLocked(true);
		}
	}

	//锁定非汇总房间行
	private void lockTblRent()
	{
		for(int k=0;k<tblRentSet.getRowCount();k++)
		{
			IRow row2 = tblRentSet.getRow(k);
			if(row2.getUserObject() instanceof TenancyRoomEntryInfo)
			{
				for (int i = 0; i < this.tblRentSet.getColumnCount(); i++) {
					IColumn column = this.tblRentSet.getColumn(i);
					String colKey = column.getKey();
					row2.getCell(colKey).getStyleAttributes().setLocked(true);
				}
			}
		}
	}

	//是否需要增加汇总行
	private boolean isStandardRentSetting()
	{
		//当房间数量大于1并且房产计算方式是租金统一设置的时候才需要增加汇总行,配套资源不进行租金汇总设置
		if((MoreRoomsTypeEnum.StandardRentSetting.equals((MoreRoomsTypeEnum)this.comboMoreRoomsType.getSelectedItem()))&& this.tblRoom.getRowCount()>1)
		{
			return true;
		}
		return false;
	}

	/**
	 * 根据租赁房间和租赁房间的成交价格分录.更新租金设置Table
	 * @param tenAttaches 
	 * */
	private void loadTblRentSet(TenancyRoomEntryCollection tenRooms, TenAttachResourceEntryCollection tenAttaches) {
		
		this.tblRentSet.removeRows();
		/* 汇总押金 */
		BigDecimal depositGather = FDCHelper.ZERO;
		/* 汇总首租 */
		BigDecimal firstRentGather = FDCHelper.ZERO;
		RentTypeEnum rentTypeGather = RentTypeEnum.RentByMonth;
		rentArea = FDCHelper.ZERO;
		Map rentMap = new HashMap();
		Map rentPriceMap = new HashMap();
		Map otherRentMap =  new HashMap();
		Map otherRentPriceMap = new HashMap();
		BigDecimal standardRentGateher = FDCHelper.ZERO;
		//先将租金列删除，再根据设置的递增列表动态生成tblRentSet列
		List toDelColIndexes = new ArrayList();
		for (int i = 0; i < this.tblRentSet.getColumnCount(); i++) {
			IColumn column = this.tblRentSet.getColumn(i);
			String colKey = column.getKey();
			if (colKey.startsWith(PREFIX_C_RENT_RENT_PRICE) || colKey.startsWith(PREFIX_C_RENT_RENT)  
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				toDelColIndexes.add(new Integer(i));
			}
		}

		for (int i = toDelColIndexes.size() - 1; i >=0 ; i--) {
			int colIndex = ((Integer) toDelColIndexes.get(i)).intValue();
			this.tblRentSet.removeColumn(colIndex);
		}

		Map despositMap = new HashMap();
		//增加第一行时进行动态增加列
		boolean isFirstRow = true;
		for(int i=0; i<tenRooms.size(); i++){
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			IRow row = this.tblRentSet.addRow();
			row.setUserObject(tenRoom);
			row.getCell(C_RENT_ROOM).setValue(tenRoom.getRoomLongNum());
			TenancyModeEnum tenancyMode = TenancyModeEnum.TenancyRentModel;
			if(tenRoom.getTenancyModel()!=null)
			{
				tenancyMode = tenRoom.getTenancyModel();
			}else
			{
				tenRoom.setTenancyModel(tenancyMode);
			}
			row.getCell(C_RENT_TENANCY_MODEL).setValue(tenancyMode);	
			RentTypeEnum rentType = RentTypeEnum.RentByMonth;
			if(tenRoom.getDealRentType() != null)
			{
				rentType=tenRoom.getDealRentType();
			}else{
				tenRoom.setDealRentType(rentType);
			}
			row.getCell(C_RENT_RENT_TYPE).setValue(rentType);
//			row.getCell(C_RENT_DEPOSIT).setValue(tenRoom.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenRoom.getFirstPayAmount());
//			if(tenRoom.getDepositAmount()!=null)
//			{
//				depositGather = depositGather.add(tenRoom.getDepositAmount());
//			}
			if(tenRoom.getFirstPayAmount()!=null)
			{
				firstRentGather = firstRentGather.add(tenRoom.getFirstPayAmount());
			}

			if(tenRoom.getBuildingArea()!=null)
			{
				rentArea = rentArea.add(tenRoom.getBuildingArea());
			}	
			//因为汇总行的租金类型总会和房间行的一样
			//if(tenRoom.getDealRentType()!=null)
			//{
			rentTypeGather = tenRoom.getDealRentType();
			//}
			DealAmountEntryCollection dealAmounts = tenRoom.getDealAmounts();
			for(int k=0;k<dealAmounts.size();k++)
			{
				DealAmountEntryInfo dealAmount = dealAmounts.get(k);
				String moneyNumber = dealAmount.getMoneyDefine().getNumber();
				//把汇总行押金存起来
				if(despositMap.get(moneyNumber)==null)
				{
					depositGather = dealAmount.getAmount();
					despositMap.put(moneyNumber,depositGather);
				}else
				{
					BigDecimal amount = (BigDecimal)despositMap.get(moneyNumber);
					amount = amount ==null?new BigDecimal(0):amount;
					if(dealAmount.getAmount()==null)
					{
						depositGather = amount.add(new BigDecimal(0));
						despositMap.put(moneyNumber,depositGather);
					}else
					{
						depositGather = amount.add(dealAmount.getAmount());
						despositMap.put(moneyNumber,depositGather);
					}
					
				}
				//如果只有一种押金类型，赋值
//				if(monDefineColl.size()==1)
//				{
//					MoneyDefineInfo moneyInfo = monDefineColl.get(0);
//					if(moneyNumber.equals(moneyInfo.getNumber()))
//					{
//						if(dealAmount.getAmount()==null || dealAmount.getAmount().compareTo(new BigDecimal(0))==0)
//						{
//							row.getCell(moneyNumber).setValue(tenRoom.getDepositAmount());
//						}else
//						{
//							row.getCell(moneyNumber).setValue(dealAmount.getAmount());
//						}
//						
//					}
//				}else
				{
					for(int j=0;j<monDefineColl.size();j++)
					{
						MoneyDefineInfo moneyInfo = monDefineColl.get(j);
						if(moneyNumber.equals(moneyInfo.getNumber()))
						{
							row.getCell(moneyNumber).setValue(dealAmount.getAmount());
						}					
					}
				}				
			}
			if(isFirstRow){
				//周期性费用
				DealAmountEntryCollection periodicityMoneys = new DealAmountEntryCollection();
				for(int j=0; j<dealAmounts.size(); j++){
					DealAmountEntryInfo dealAmount = dealAmounts.get(j);
					if(MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType()))
					{
						continue;
					}
					String moneyName = dealAmount.getMoneyDefine().getName();
					if(MoneyTypeEnum.PeriodicityAmount.equals(dealAmount.getMoneyDefine().getMoneyType())){
						periodicityMoneys.add(dealAmount);
						continue;
					}
					String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
					String keyRent = PREFIX_C_RENT_RENT + dateDes;

					addTblRentSetColumn(keyRentPrice, keyRent, dateDes);
				}

				for(int j=0; j<periodicityMoneys.size(); j++){
					DealAmountEntryInfo dealAmount = periodicityMoneys.get(j);
					String moneyName = dealAmount.getMoneyDefine().getName();
					String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());
					String key1 = PREFIX_C_RENT_PERIODICITY_PRICE + dateDes;
					String key2 = PREFIX_C_RENT_PERIODICITY + dateDes;

					addTblRentPeriodicityColumn(key1, key2, dateDes);
				}
				isFirstRow = false;
			}

			for(int j=0; j<dealAmounts.size(); j++){
				DealAmountEntryInfo dealAmount = dealAmounts.get(j);
				if(MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType()))
				{
					continue;
				}
				String moneyName = dealAmount.getMoneyDefine().getName();
				String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
				String keyRent = PREFIX_C_RENT_RENT + dateDes;

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if(cellRentPrice != null  &&  cellRent != null){
					cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());
					//带出实际租金日单价： @by huanghefh   
					if((RentTypeEnum)row.getCell("rentType").getValue()!=null){
						String rentTypeName = ((RentTypeEnum)row.getCell("rentType").getValue()).getValue();
//						row.getCell("actdayprice").setValue(toDayPrice(rentTypeName,dealAmount.getPriceAmount()));
					}
					//把租金汇总的键值和单价存起来在新增汇总行时调用
					if(rentMap.get(keyRent)!=null)
					{
						BigDecimal rent = (BigDecimal)rentMap.get(keyRent);
						rent = rent==null?new BigDecimal(0):rent;
						BigDecimal amount = new BigDecimal(0);
						if(dealAmount.getAmount()!=null)
						{
							amount = dealAmount.getAmount();
						}
						standardRentGateher = rent.add(amount);
						rentMap.put(keyRent, standardRentGateher);
						rentPriceMap.put(keyRent, keyRentPrice);
					}else
					{
						standardRentGateher = dealAmount.getAmount();
						standardRentGateher = standardRentGateher==null?new BigDecimal(0):standardRentGateher;
						rentMap.put(keyRent,standardRentGateher);
						rentPriceMap.put(keyRent, keyRentPrice);
					}
					//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
					cellRentPrice.setUserObject(dealAmount);
					cellRent.setUserObject(dealAmount);

					if(dealAmount.isIsHandwork()){
						//如果不是按总价计算，则锁定总价不可编辑
						if(!TenancyModeEnum.TenancyRentModel.equals(tenancyMode)){
							cellRent.getStyleAttributes().setLocked(true);
						}else{
							cellRentPrice.getStyleAttributes().setLocked(true);
						}
					}else{
						cellRentPrice.getStyleAttributes().setLocked(true);
						cellRent.getStyleAttributes().setLocked(true);
					}
				}else{
					keyRentPrice = PREFIX_C_RENT_PERIODICITY_PRICE + dateDes;
					keyRent = PREFIX_C_RENT_PERIODICITY + dateDes;

					cellRentPrice = row.getCell(keyRentPrice);
					cellRent = row.getCell(keyRent);

					if(cellRentPrice != null  &&  cellRent != null){
						cellRentPrice.setValue(dealAmount.getPriceAmount());
						cellRent.setValue(dealAmount.getAmount());

						if(otherRentMap.get(keyRent)!=null)
						{
							BigDecimal rent = (BigDecimal)otherRentMap.get(keyRent);
							rent = rent==null?new BigDecimal(0):rent;
							BigDecimal amount = new BigDecimal(0);
							if(dealAmount.getAmount()!=null)
							{
								amount = dealAmount.getAmount();
							}
							standardRentGateher = rent.add(amount);
							otherRentMap.put(keyRent, standardRentGateher);
							otherRentPriceMap.put(keyRent, keyRentPrice);
						}else
						{
							standardRentGateher = dealAmount.getAmount();
							standardRentGateher = standardRentGateher==null?new BigDecimal(0):standardRentGateher;
							otherRentMap.put(keyRent,standardRentGateher);
							otherRentPriceMap.put(keyRent, keyRentPrice);
						}

						//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
						cellRentPrice.setUserObject(dealAmount);
						cellRent.setUserObject(dealAmount);

						//如果不是按总价计算，则锁定总价不可编辑
						if(!TenancyModeEnum.TenancyRentModel.equals(tenancyMode)){
							cellRent.getStyleAttributes().setLocked(true);
						}else{
							cellRentPrice.getStyleAttributes().setLocked(true);
						}
					}
				}
			}
		}

		//刚开始加汇总行的时候默认是汇总房间的数据锁定非汇总的行
		if(isStandardRentSetting())
		{
			IRow rowGather = tblRentSet.addRow();
			rowGather.getStyleAttributes().setBackground(Color.YELLOW);
			rowGather.getCell(C_RENT_ROOM).setValue("租金汇总");
			rowGather.getCell(C_RENT_TENANCY_MODEL).setValue(TenancyModeEnum.TenancyRentModel);
			rowGather.getCell(C_RENT_RENT_TYPE).setValue(rentTypeGather);
			for(int i=0;i<monDefineColl.size();i++)
			{
				MoneyDefineInfo moneyInfo = monDefineColl.get(i);
				if(despositMap.get(moneyInfo.getNumber())!=null)
				{
					BigDecimal amount = (BigDecimal)despositMap.get(moneyInfo.getNumber());
					rowGather.getCell(moneyInfo.getNumber()).setValue(amount);
				}
			}
//			rowGather.getCell(C_RENT_DEPOSIT).setValue(depositGather);
			rowGather.getCell(C_RENT_FIRST_RENT).setValue(firstRentGather);
			rowGather.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(true);

			setRentAndPriceGather(rentMap,rentPriceMap,rowGather);
			setRentAndPriceGather(otherRentMap,otherRentPriceMap,rowGather);


			//			Set otherRentSet = otherRentMap.keySet();
			//			Iterator otherRent = otherRentSet.iterator();
			//			while(otherRent.hasNext())
			//			{
			//				String otherKey = (String)otherRent.next();
			//				rowGather.getCell(otherKey).setValue(otherRentMap.get(otherKey));
			//				String otherRentPriceKey = (String)otherRentPriceMap.get(otherKey);
			//				BigDecimal value = new BigDecimal(0);
			//				if(rentMap.get(otherKey)!=null)
			//				{
			//					value = (BigDecimal)rentMap.get(otherKey);
			//				}
			//				rowGather.getCell(otherRentPriceKey).setValue(value.divide(rentArea, 2,BigDecimal.ROUND_HALF_UP));
			//				rowGather.getCell(otherKey).setUserObject(value);
			////				rowGather.getCell(otherKey).getStyleAttributes().setLocked(true);
			//				rowGather.getCell(otherRentPriceKey).getStyleAttributes().setLocked(true);
			//			}
			lockTblRent();
		}

		for(int i=0; i<tenAttaches.size(); i++){
			TenAttachResourceEntryInfo tenAttach = tenAttaches.get(i);
			IRow row = this.tblRentSet.addRow();

			row.setUserObject(tenAttach);
			row.getCell(C_RENT_ROOM).setValue(tenAttach.getAttachLongNum());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(TenancyModeEnum.TenancyRentModel);
			row.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(true);
			row.getCell(C_RENT_RENT_TYPE).setValue(tenAttach.getDealRentType());
//			row.getCell(C_RENT_DEPOSIT).setValue(tenAttach.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenAttach.getFirstPayAmount());

			AttachDealAmountEntryCollection dealAmounts = tenAttach.getDealAmounts();
			if(isFirstRow){
				//周期性费用
				AttachDealAmountEntryCollection periodicityMoneys = new AttachDealAmountEntryCollection();
				for(int j=0; j<dealAmounts.size(); j++){
					AttachDealAmountEntryInfo dealAmount = dealAmounts.get(j);
					String moneyName = dealAmount.getMoneyDefine().getName();
					if(MoneyTypeEnum.PeriodicityAmount.equals(dealAmount.getMoneyDefine().getMoneyType())){
						periodicityMoneys.add(dealAmount);
						continue;
					}
					String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
					String keyRent = PREFIX_C_RENT_RENT + dateDes;

					addTblRentSetColumn(keyRentPrice, keyRent, dateDes);
				}

				for(int j=0; j<periodicityMoneys.size(); j++){
					AttachDealAmountEntryInfo dealAmount = periodicityMoneys.get(j);
					String moneyName = dealAmount.getMoneyDefine().getName();
					String key1 = PREFIX_C_RENT_PERIODICITY_PRICE + moneyName;
					String key2 = PREFIX_C_RENT_PERIODICITY + moneyName;
					String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

					addTblRentPeriodicityColumn(key1, key2, dateDes);
				}
				isFirstRow = false;
			}

			for(int j=0; j<dealAmounts.size(); j++){
				AttachDealAmountEntryInfo dealAmount = dealAmounts.get(j);
				String moneyName = dealAmount.getMoneyDefine().getName();
				String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
				String keyRent = PREFIX_C_RENT_RENT + dateDes;

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if(cellRentPrice != null  &&  cellRent != null){
					//					cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());
					//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
					//					cellRentPrice.setUserObject(dealAmount);
					cellRent.setUserObject(dealAmount);

					if(dealAmount.isIsHandwork()){
						//如果不是按总价计算，则锁定总价不可编辑
						cellRentPrice.getStyleAttributes().setLocked(true);
					}else{
						cellRentPrice.getStyleAttributes().setLocked(true);
						cellRent.getStyleAttributes().setLocked(true);
					}
				}else{
					keyRentPrice = PREFIX_C_RENT_PERIODICITY_PRICE + moneyName;
					keyRent = PREFIX_C_RENT_PERIODICITY + moneyName;

					cellRentPrice = row.getCell(keyRentPrice);
					cellRent = row.getCell(keyRent);

					if(cellRentPrice != null  &&  cellRent != null){
						//						cellRentPrice.setValue(dealAmount.getPriceAmount());
						cellRent.setValue(dealAmount.getAmount());
						//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
						cellRentPrice.setUserObject(dealAmount);
						cellRent.setUserObject(dealAmount);

						//如果不是按总价计算，则锁定总价不可编辑
						cellRentPrice.getStyleAttributes().setLocked(true);
					}
				}
			}
		}

		this.tblRentSet.getHeadMergeManager().mergeBlock(0, 0, 1, tblRentSet.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
		this.tblRentSet.setAutoscrolls(true);
	
	}

	private void addTblRentPeriodicityColumn(String key1, String key2, String des) {
		IColumn col = this.tblRentSet.addColumn();
		col.setKey(key1);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		col = this.tblRentSet.addColumn();
		col.setKey(key2);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		IRow headRow0 = this.tblRentSet.getHeadRow(0);
		headRow0.getCell(key1).setValue(des);
		headRow0.getCell(key2).setValue(des);

		IRow headRow1 = this.tblRentSet.getHeadRow(1);
		headRow1.getCell(key1).setValue("金额单价");
		headRow1.getCell(key2).setValue("金额");
	}

	private String getDateDes(String moneyName, Date startDate, Date endDate) {
		String dateDes = null;
		if(startDate == null  ||  endDate == null){
			return null;
		}
		if(startDate.equals(endDate)){
			dateDes = FDCDateHelper.DateToString(startDate);
		}else{
			dateDes = FDCDateHelper.DateToString(startDate) + "至" + FDCDateHelper.DateToString(endDate);
		}
		return moneyName + "(" + dateDes + ")";
	}

	private void addTblRentSetColumn(String keyRentType, String keyRent, String headRowDes) {
		IColumn col = this.tblRentSet.addColumn();
		col.setKey(keyRentType);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		col = this.tblRentSet.addColumn();
		col.setKey(keyRent);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		IRow headRow0 = this.tblRentSet.getHeadRow(0);
		headRow0.getCell(keyRentType).setValue(headRowDes);
		headRow0.getCell(keyRent).setValue(headRowDes);

		IRow headRow1 = this.tblRentSet.getHeadRow(1);
		headRow1.getCell(keyRentType).setValue("租金单价");
		headRow1.getCell(keyRent).setValue("租金");
	}

	/**
	 * 根据成交租金总价和中介合同佣金标准更新约定代理费
	 * */
	private void updatePromissoryAgentFee() {
		BigDecimal dealTotalRent = this.txtDealTotalRent.getBigDecimalValue();
		if (dealTotalRent == null) {
			dealTotalRent = FDCHelper.ZERO;
		}

		AgencyContractInfo agencyContract = (AgencyContractInfo) this.f7AgencyContract.getValue();
		if (agencyContract != null) {
			CommisionStandardEnum comStand = agencyContract.getCommisionStandard();
			BigDecimal value = agencyContract.getValue();
			if (value == null)
				value = FDCHelper.ZERO;
			BigDecimal agentFee = FDCHelper.ZERO;
			if (CommisionStandardEnum.Percentum.equals(comStand)) {
				agentFee = dealTotalRent.multiply(value).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			} else if (CommisionStandardEnum.AptoticAmount.equals(comStand)) {
				agentFee = agencyContract.getValue();
			}
			this.txtPromissoryAgentFee.setValue(agentFee);

			if (this.txtAgentFee.getBigDecimalValue() == null) {
				this.txtAgentFee.setValue(agentFee);
			}
		}
	}

	/**
	 * 设置租赁房间的租赁状态 <br>
	 * 新租或转名：房间租赁状态为"新租" <br>
	 * 续租：房间新合同上存在,旧合同上存在.新合同上的房间租赁状态为"续租"; <br>
	 * 房间新合同上存在,旧合同上不存在.且旧合同上的房间在新合同上都存在,新合同上该房间租赁状态为"扩租"; <br>
	 * 房间新合同上存在,旧合同上不存在.而旧合同上的房间在新合同上不都存在,新合同上的房间租赁状态为"新租"; <br>
	 * 改租：房间新合同上存在,旧合同上存在.新合同上的房间租赁状态与原合同上该房间的租赁状态相同; <br>
	 * 房间新合同上存在,旧合同上不存在.且旧合同上的房间在新合同上都存在,新合同上该房间租赁状态为"扩租"; <br>
	 * 房间新合同上存在,旧合同上不存在.而旧合同上的房间在新合同上不都存在,新合同上的房间租赁状态为"新租";
	 * 
	 * @param tenancyRooms
	 * */
	private void setTenRoomsState(TenancyRoomEntryCollection tenancyRooms) {
		TenancyContractTypeEnum currentTenancyType = getCurrentTenancyType();
		// 改租或者续租时,需要根据原合同的房间判断设置租赁房间状态
		if (TenancyContractTypeEnum.ContinueTenancy.equals(currentTenancyType) || TenancyContractTypeEnum.RejiggerTenancy.equals(currentTenancyType)) {
			Map oldTenRoomMap = new HashMap();
			TenancyRoomEntryCollection oldTenRooms = oldTenancyBill.getTenancyRoomList();
			for (int j = 0; j < oldTenRooms.size(); j++) {
				TenancyRoomEntryInfo oldTenRoom = oldTenRooms.get(j);
				if (oldTenRoom.getRoom() != null) {
					oldTenRoomMap.put(oldTenRoom.getRoom().getId().toString(), oldTenRoom);
				}
			}

			Set oldTenRoomIds = oldTenRoomMap.keySet();
			Set newTenRoomIds = new HashSet();

			for (int i = 0; i < tenancyRooms.size(); i++) {
				TenancyRoomEntryInfo tenRoom = tenancyRooms.get(i);
				if (tenRoom.getRoom() != null) {
					newTenRoomIds.add(tenRoom.getRoom().getId().toString());
				}
			}

			// 改租或续租时,原合同上的房间是否都在新合同上
			boolean isAllOldTenRoomsInNewTen = newTenRoomIds.containsAll(oldTenRoomIds);

			for (int i = 0; i < tenancyRooms.size(); i++) {
				TenancyRoomEntryInfo tenRoom = tenancyRooms.get(i);
				TenancyStateEnum tenRoomState = TenancyStateEnum.newTenancy;
				if (tenRoom.getRoom() != null) {
					String roomId = tenRoom.getRoom().getId().toString();
					if (oldTenRoomIds.contains(roomId)) {// 如果在原合同中存在,续租时为"续租";
						// 改租时为原合同房间分录上的租赁状态
						if (TenancyContractTypeEnum.ContinueTenancy.equals(currentTenancyType)) {
							tenRoomState = TenancyStateEnum.continueTenancy;
						} else if (TenancyContractTypeEnum.RejiggerTenancy.equals(currentTenancyType)) {
							TenancyRoomEntryInfo oldTenRoom = (TenancyRoomEntryInfo) oldTenRoomMap.get(roomId);
							tenRoomState = oldTenRoom.getTenRoomState();
						}
					} else {// 房间不在原合同中,如果原合同的所有房间都在新合同上,则为扩租;否则为新租
						if (isAllOldTenRoomsInNewTen) {
							tenRoomState = TenancyStateEnum.enlargeTenancy;
						} else {
							tenRoomState = TenancyStateEnum.newTenancy;
						}
					}
				}
				tenRoom.setTenRoomState(tenRoomState);
			}
		} else {// 新租或者更名时,房间租赁状态都为"新租"
			for (int i = 0; i < tenancyRooms.size(); i++) {
				TenancyRoomEntryInfo tenRoom = tenancyRooms.get(i);
				tenRoom.setTenRoomState(TenancyStateEnum.newTenancy);
			}
		}
	}

	/**
	 * 设置租赁房间的租赁状态 <br>
	 * 新租或转名：房间租赁状态为"新租" <br>
	 * 续租：房间新合同上存在,旧合同上存在.新合同上的房间租赁状态为"续租"; <br>
	 * 房间新合同上存在,旧合同上不存在.且旧合同上的房间在新合同上都存在,新合同上该房间租赁状态为"扩租"; <br>
	 * 房间新合同上存在,旧合同上不存在.而旧合同上的房间在新合同上不都存在,新合同上的房间租赁状态为"新租"; <br>
	 * 改租：房间新合同上存在,旧合同上存在.新合同上的房间租赁状态与原合同上该房间的租赁状态相同; <br>
	 * 房间新合同上存在,旧合同上不存在.且旧合同上的房间在新合同上都存在,新合同上该房间租赁状态为"扩租"; <br>
	 * 房间新合同上存在,旧合同上不存在.而旧合同上的房间在新合同上不都存在,新合同上的房间租赁状态为"新租";
	 * */
	private void setTenAttachsState(TenAttachResourceEntryCollection tenAttachs) {
		TenancyContractTypeEnum currentTenancyType = getCurrentTenancyType();
		// 改租或者续租时,需要根据原合同的房间判断设置租赁房间状态
		if (TenancyContractTypeEnum.ContinueTenancy.equals(currentTenancyType) || TenancyContractTypeEnum.RejiggerTenancy.equals(currentTenancyType)) {
			Map oldTenAttachMap = new HashMap();
			TenAttachResourceEntryCollection oldTenAttachs = oldTenancyBill.getTenAttachResourceList();

			for (int j = 0; j < oldTenAttachs.size(); j++) {
				TenAttachResourceEntryInfo oldTenAttach = oldTenAttachs.get(j);
				if (oldTenAttach.getAttachResource() != null) {
					oldTenAttachMap.put(oldTenAttach.getAttachResource().getId().toString(), oldTenAttach);
				}
			}

			Set oldTenAttachIds = oldTenAttachMap.keySet();
			Set newTenAttachIds = new HashSet();

			for (int i = 0; i < tenAttachs.size(); i++) {
				TenAttachResourceEntryInfo tenAttach = oldTenAttachs.get(i);
				if (tenAttach.getAttachResource() != null) {
					newTenAttachIds.add(tenAttach.getAttachResource().getId().toString());
				}
			}

			// 改租或续租时,原合同上的房间是否都在新合同上
			boolean isAllOldTenRoomsInNewTen = newTenAttachIds.containsAll(oldTenAttachIds);

			for (int i = 0; i < tenAttachs.size(); i++) {
				TenAttachResourceEntryInfo tenAttach = tenAttachs.get(i);
				TenancyStateEnum tenAttachState = TenancyStateEnum.newTenancy;
				if (tenAttach.getAttachResource() != null) {
					String attachId = tenAttach.getAttachResource().getId().toString();
					if (oldTenAttachIds.contains(attachId)) {// 如果在原合同中存在,续租时为"续租";
						// 改租时为原合同房间分录上的租赁状态
						if (TenancyContractTypeEnum.ContinueTenancy.equals(currentTenancyType)) {
							tenAttachState = TenancyStateEnum.continueTenancy;
						} else if (TenancyContractTypeEnum.RejiggerTenancy.equals(currentTenancyType)) {
							TenAttachResourceEntryInfo oldTenAttach = (TenAttachResourceEntryInfo) oldTenAttachMap.get(attachId);
							tenAttachState = oldTenAttach.getTenAttachState();
						}
					} else {// 房间不在原合同中,如果原合同的所有房间都在新合同上,则为扩租;否则为新租
						if (isAllOldTenRoomsInNewTen) {
							tenAttachState = TenancyStateEnum.enlargeTenancy;
						} else {
							tenAttachState = TenancyStateEnum.newTenancy;
						}
					}
				}
				tenAttach.setTenAttachState(tenAttachState);
			}
		} else {// 新租或者更名时,房间租赁状态都为"新租"
			for (int i = 0; i < tenAttachs.size(); i++) {
				TenAttachResourceEntryInfo tenAttach = tenAttachs.get(i);
				tenAttach.setTenAttachState(TenancyStateEnum.newTenancy);
			}
		}
	}

	/**
	 * 更新租期数,租期数精确到2位小数
	 * @param leaseList
	 *            租期集合 注：只有最后1个租期可能不是完整的租期,最后1期的月份数/租期长度=最后1期的数量.
	 * */
	private void updateLeaseCount(List leaseList) {
		// 租期不区分小数 090701
		this.txtLeaseCount.setValue(new Integer(leaseList.size()));
	}

	/** TODO 付款方案可能会做成基础资料,抽取这个函数便于扩展 */
	private MoneyDefineInfo[] getPayType() {
		MoneyDefineInfo[] payType = new MoneyDefineInfo[4];

		return null;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		verifyBaseInfo();
		if(!isDynamicStartDate())
		{
			// verifyBaseInfo中已经校验了pkStartDate与pkEndDate不能为空
			Date startDate = FDCDateHelper.getDayBegin((Date) this.pkStartDate.getValue());
			Date endDate = FDCDateHelper.getDayBegin((Date) this.pkEndDate.getValue());
			verifySpecial(startDate);
			verifyRoom(startDate, endDate);
			verifyCustomer();
			verifyRentSet();
			checkPayList();
			viftyOtherPayList();
		}		

		//		verifyPayList();付款明细中不允许手输了，所以这里不存在不相等的情况，不用再校验
		
		
		
		//add by yangfan 
		if(this.chkIsMDLiquidated.getSelected()==16&&this.chkIsAccLiquidated.getSelected()==32){
			if(StringUtils.isEmpty(this.txtRate.getText())){
				MsgBox.showInfo("违约金比例必须录入！");
				abort();
			}
			if(this.txtRate.getBigDecimalValue().compareTo(FDCHelper.ZERO) < 0||this.txtRate.getBigDecimalValue().compareTo(FDCHelper.ZERO) == 0){
				MsgBox.showInfo("违约金比例必须大于0！");
				abort();
			}
//			if(StringUtils.isEmpty(this.txtRelief.getText())){
//				MsgBox.showInfo("违约金减免必须录入！");
//				abort();
//			}
//			if(this.txtRelief.getIntegerValue()<=0){
//				MsgBox.showInfo("违约金减免必须大于0！");
//				abort();
//			}
//			if(StringUtils.isEmpty(this.txtStandard.getText())){
//				MsgBox.showInfo("违约金计算标准必须录入！");
//				abort();
//			}
//			if(this.txtStandard.getIntegerValue()<=0){
//				MsgBox.showInfo("违约金计算标准必须必须大于0！");
//				abort();
//			}
		}
		
		String room="";
		if(this.tblRoom.getRowCount()>0){
			TenancyRoomEntryInfo entry=(TenancyRoomEntryInfo) this.tblRoom.getRow(0).getUserObject();
			room=room+entry.getRoom().getName();
		}
		if(this.pkTenancyDate.getValue()!=null){
			room=room+"("+FDCDateHelper.DateToString((Date) this.pkTenancyDate.getValue())+")";
		}
		this.txtName.setText(room);
	}
	
	
	/**
	 * 添加 对客户信息的验证
	 */
	private void verifyCustomer() throws BOSException {
		BigDecimal percent = FDCHelper.ZERO;
		Map customerMap = new HashMap();
		if (this.tblCustomer.getRowCount() == 0) {
			MsgBox.showInfo("没有租赁客户,请添加!");
			this.abort();
		}

		for (int i = 0; i < this.tblCustomer.getRowCount(); i++) {
			IRow row = this.tblCustomer.getRow(i);
			if (row.getCell("propertyPercent").getValue() == null) {
				MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户没有设置出租比例!");
				this.abort();
			}

			FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell("customer").getValue();
			if (customer == null) {
				MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户没有录入!");
				this.abort();
			}
//			if (customer.getCertificateName() == null || customer.getCertificateNumber() == null) {
//				MsgBox.showInfo("客户" + customer.getName() + "证件不能为空!!");
//				CustomerCardUI.addTheCustomerAuthority(customer, this);
//				setCustomerRowData(customer, row);
//				this.abort();
//			}
			if (customerMap.containsKey(customer)) {
				MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户重复!");
				this.abort();
			} else {
				customerMap.put(customer, customer);
			}
			percent = percent.add((BigDecimal) row.getCell("propertyPercent").getValue());
		}
		if (percent.compareTo(new BigDecimal("100")) != 0) {
			MsgBox.showInfo("出租比例不等100%!");
			this.abort();
		}
	}
	private void setCustomerRowData(FDCCustomerInfo customer, IRow row) {
		if (customer != null) {
			row.getCell("customer").setValue(customer);

			row.getCell("phone").setValue(customer.getPhone());
			row.getCell("postalcode").setValue(customer.getPostalcode());
			row.getCell("certificateName").setValue(customer.getCertificateName());
			row.getCell("certificateNumber").setValue(customer.getCertificateNumber());
			row.getCell("mailAddress").setValue(customer.getMailAddress());
			row.getCell("bookDate").setValue(customer.getCreateTime());
		} else {
			row.getCell("phone").setValue(null);
			row.getCell("postalcode").setValue(null);
			row.getCell("certificateName").setValue(null);
			row.getCell("certificateNumber").setValue(null);
			row.getCell("mailAddress").setValue(null);
			row.getCell("bookDate").setValue(null);
		}
	}
	
	private void verifyRentSet()
	{
		for(int i=0;i<tblRentSet.getRowCount();i++)
		{
			IRow row = tblRentSet.getRow(i);
			if(row.getCell(C_RENT_RENT_TYPE).getValue()==null)
			{
				MsgBox.showInfo("租金设置里计租周期不能为空");
				abort();
			}
		}
	}

	/**
	 * 校验房间.主要是校验房间是否与其他合同有租期上的时间冲突
	 * TODO 这块校验目前客户的需求不是很高
	 * */
	private void verifyRoom(Date startDate, Date endDate) throws BOSException {
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = tblRoom.getRow(i);
			TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo) row.getUserObject();

			//			BigDecimal tenRoomDealRent = tenRoom.getDealRoomRent();
			//			if (tenRoomDealRent == null || tenRoomDealRent.compareTo(FDCHelper.ZERO) == 0) {
			//				MsgBox.showInfo(this, "第" + (i + 1) + "行房间没设置成交租金.");
			//				this.abort();
			//			}

			BigDecimal roomTotalRent = tenRoom.getRoomTotalRent();
			if(roomTotalRent == null) roomTotalRent = FDCHelper.ZERO;
			BigDecimal firstPayAmount = tenRoom.getFirstPayAmount();
			if(firstPayAmount == null) firstPayAmount = FDCHelper.ZERO;

//			if(firstPayAmount.compareTo(roomTotalRent) > 0){
//				MsgBox.showInfo(tenRoom.getRoomLongNum() + " 设置的首租大于了该房间所有租金.");
//				abort();
//			}

			RoomInfo room = tenRoom.getRoom();

			// 找到这个房间所有非保存,非终止,非作废状态的租赁房间记录.在本地验证是否与新增的合同存在时间冲突
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);

			filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState", TenancyBillStateEnum.Saved, CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState", TenancyBillStateEnum.Expiration, CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState", TenancyBillStateEnum.BlankOut, CompareType.NOTEQUALS));

			if (this.editData.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("tenancy.id", this.editData.getId().toString(), CompareType.NOTEQUALS));
			}
			filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));

			TenancyRoomEntryCollection tenRooms = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
			if (tenRooms != null && !tenRooms.isEmpty()) {

				TenancyRoomEntryInfo lastTenBeforeThisTenBill = null;// 距离-
				// 新增的合同租赁日期之前
				// -
				// 最近的合同
				// -
				// 的房间记录
				// 同一房间下未终止的有效合同数量有限,这里遍历判断是否存在时间冲突
				for (int j = 0; j < tenRooms.size(); j++) {
					TenancyRoomEntryInfo tmpTenRoom = tenRooms.get(j);

					TenancyBillInfo tenBill = tmpTenRoom.getTenancy();

					// 如果查到的租赁房间就是新增合同的原合同,则忽略下面的校验
					if (oldTenancyBill != null && tenBill.getId().toString().equals(oldTenancyBill.getId().toString())) {
						continue;
					}

					Date tmpStartDate = tmpTenRoom.getStartDate();
					Date tmpEndDate = tmpTenRoom.getEndDate();
					// 新增合同的起始时间在已有合同的租赁时间之间 或则
					// 新增合同的结束时间在已有合同的租赁时间之间 或则
					// 新增合同的租赁时间包含了已有合同的租赁时间;则说明租赁时间冲突
					//因为如果以前是动态开始日期的租赁合同的现在改成固定开始日期，那么开始时间就有可能为空
					if(tmpStartDate!=null && tmpEndDate!=null)
					{
						if ((!startDate.before(tmpStartDate) && !startDate.after(tmpEndDate)) || (!endDate.before(tmpStartDate) && !endDate.after(tmpEndDate))
								|| (startDate.before(tmpStartDate) && endDate.after(tmpEndDate))) {
							MsgBox.showInfo(this, "第" + (i + 1) + "行房间租赁时间冲突.");
							this.abort();
						}

						if (!startDate.before(tmpEndDate)) {
							if (lastTenBeforeThisTenBill == null) {
								lastTenBeforeThisTenBill = tmpTenRoom;
							} else {
								if(lastTenBeforeThisTenBill.getTenancy()!= null && lastTenBeforeThisTenBill.getTenancy().getEndDate()!=null){
									if (!tmpEndDate.before(lastTenBeforeThisTenBill.getTenancy().getEndDate())) {
										lastTenBeforeThisTenBill = tmpTenRoom;
									}
								}
							}
						}
					}					
				}

				// 如果新增的合同租赁日期之前最近的租赁房间分录不是到期退租状态,不允许进行出租
				if (lastTenBeforeThisTenBill != null) {
					// 如果最近的一个合同就是新增合同的原合同,则忽略下面的校验
					if (oldTenancyBill != null && lastTenBeforeThisTenBill.getId().toString().equals(oldTenancyBill.getId().toString())) {
						continue;
					}

					FlagAtTermEnum flagAtTerm = lastTenBeforeThisTenBill.getFlagAtTerm();
					if (!FlagAtTermEnum.QuitAtTerm.equals(flagAtTerm)) {
						MsgBox.showInfo(this, "第" + (i + 1) + "行的房间不适合出租.");
						this.abort();
					}
				}
			}
		}
	}

	/**
	 * 对改续租或转名操作进行相关验证
	 * */
	private void verifySpecial(Date startDate) throws EASBizException, BOSException {
		TenancyContractTypeEnum tenType = getCurrentTenancyType();
		if (!TenancyContractTypeEnum.NewTenancy.equals(tenType)) {
			if (oldTenancyBill == null) {
				MsgBox.showInfo(this, "error impossiable!");
				this.abort();
			}

			//如果原合同存在提交状态或审批中状态的减免单或变更单，不允许进行改续租或转名
			if(TenancyHelper.existRentRemissionByTenBill(RentRemissionFactory.getRemoteInstance(), oldTenancyBill.getId().toString())){
				MsgBox.showInfo(this, "原合同正在进行租金减免，不能执行该操作！");
				this.abort();
			}

			if(TenancyHelper.existTenancyModificationByTenBill(TenancyModificationFactory.getRemoteInstance(), oldTenancyBill.getId().toString())){
				MsgBox.showInfo(this, "原合同正在进行变更，不能执行该操作！");
				this.abort();
			}

			// 原合同的第一天不能改租或更名
//			if (!startDate.after(oldTenancyBill.getStartDate())) {
//				MsgBox.showInfo(this, "合同起始日期应晚于原合同起始日期！");
//				this.abort();
//			}

			// 已提交退租单的合同不能再进行改续租或更名操作
			if (TenancyHelper.existQuitTenBillByTenBill(QuitTenancyFactory.getRemoteInstance(), oldTenancyBill.getId().toString(), null)) {
				MsgBox.showInfo(this, "原合同已经有退租单！");
				this.abort();
			}

			// 已经进行了改续租或更名的合同不能再执行该操作
			String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(oldTenancyBill.getId().toString());
			if (targetTenId != null) {
				if (this.editData.getId() == null) {
					MsgBox.showInfo(this, "原合同已经改续租或改租！");
					this.abort();
				} else {// 需要把当前提交的合同除外
					if (!this.editData.getId().toString().equals(targetTenId)) {
						MsgBox.showInfo(this, "原合同已经改续租或改租！");
						this.abort();
					}
				}
			}

			// 改租或更名的起始日期一定要在原合同日期之间
			if (TenancyContractTypeEnum.RejiggerTenancy.equals(tenType) || TenancyContractTypeEnum.ChangeName.equals(tenType)) {
				Date oldStartDate = oldTenancyBill.getStartDate();
				oldStartDate = FDCDateHelper.getDayBegin(oldStartDate);
				Date oldEndDate = oldTenancyBill.getEndDate();
				oldEndDate = FDCDateHelper.getDayBegin(oldEndDate);
				if ((startDate.before(oldStartDate)&&!startDate.equals(oldStartDate)) || (startDate.after(oldEndDate)&&!startDate.equals(oldEndDate))) {
					MsgBox.showInfo(this, "改租或转名合同的起始日期必须在原合同租赁日期之间!");
					this.abort();
				}
			}

			TenancyBillStateEnum oldTenBillState = oldTenancyBill.getTenancyState();
			if(this.getOprtState().equalsIgnoreCase("ADDNEW") &&  !TenancyBillStateEnum.Executing.equals(oldTenBillState)){
				MsgBox.showInfo(this, "只有执行中的合同可以" + tenType.getAlias() + "！");
				this.abort();
			}
		}
	}

	private void verifyBaseInfo() {
		if (this.txtNumber.isEditable() && this.txtNumber.isEnabled() && StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo(this, "合同编码不能为空！");
			this.abort();
		}

//		if (StringUtils.isEmpty(this.txtName.getText())) {
//			MsgBox.showInfo(this, "合同名称不能为空！");
//			this.abort();
//		}

		if (this.f7SellProject.getValue() == null) {
			MsgBox.showInfo(this, "租售项目不能为空！");
			this.abort();
		}

		if (this.tblRoom.getRowCount() == 0 && this.tblAttachRes.getRowCount() == 0) {
			MsgBox.showInfo(this, "没有租赁的资源！");
			this.abort();
		}

//		if (this.tblCustomer.getRowCount() == 0) {
//			MsgBox.showInfo(this, "至少要有一个租赁客户！");
//			this.abort();
//		}
//		for(int i=0;i<tblCustomer.getRowCount();i++)
//		{
//			IRow row = tblCustomer.getRow(i);
//			if(row.getCell(C_CUS_CERTIFICATE_NUMBER).getValue()==null)
//			{
//				MsgBox.showInfo("第"+(i+1)+"行客户的证件号码不能为空");
//				this.abort();
//			}
//		}


		if(FirstLeaseTypeEnum.UserDefined.equals(comboFirstLeaseType.getSelectedItem())
				&& pkFirstLeaseEndDate.getValue() == null){
			MsgBox.showInfo(this, "自定义首期类型，首期截止日期不能为空！");
			this.abort();
		}
		if(RentStartTypeEnum.DynamicStartDate.equals((RentStartTypeEnum)this.comboRentStartType.getSelectedItem()))
		{
			if(this.txtLeaseCount.getIntegerValue()==null || this.txtLeaseCount.getIntegerValue().intValue()<=0)
			{
				MsgBox.showInfo("动态收租起始日期的合同租期比须大于0");
				this.abort();
			}
		}else
		{
			Date startDate = (Date) this.pkStartDate.getValue();
			Date endDate = (Date) this.pkEndDate.getValue();
			if (startDate == null || endDate == null) {
				MsgBox.showInfo(this, "租赁起始时间和结束时间不能为空！");
				this.abort();
			}

			startDate = FDCDateHelper.getDayBegin(startDate);
			endDate = FDCDateHelper.getDayBegin(endDate);
			if (startDate.after(endDate)) {
				MsgBox.showInfo(this, "租赁起始时间不能迟于结束时间！");
				this.abort();
			}
		}
		
		if (this.cbTenBillRoomState.getSelectedItem() == null) {
			MsgBox.showInfo(this, "房源状态不能为空！");
			this.abort();
		}
	}

	public void actionAddRoom_actionPerformed(ActionEvent e) throws Exception {
		if(this.tblRoom.getRowCount()>0){
			MsgBox.showInfo(this, "只能选择一套房间！");
			this.abort();
		}
		//获得选择的房间
		BuildingInfo buildingInfo = (BuildingInfo) this.getUIContext().get("building");
		BuildingUnitInfo buildUnit = (BuildingUnitInfo) this.getUIContext().get("buildUnit");

		RoomInfo room = RoomSelectUI.showOneRoomSelectUI(this, buildingInfo, buildUnit, MoneySysTypeEnum.TenancySys, null, sellProjectInfo);
		if (room == null || room.isEmpty()) {
			return;
		}

		//判断是否选择了重复的房间
		Set existRoomIds = new HashSet();
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo) row.getUserObject();
			if (tenRoom == null || tenRoom.getRoom() == null) {
				continue;
			}
			String id = tenRoom.getRoom().getId().toString();
			existRoomIds.add(id);
		}

		TenancyRoomEntryCollection tenRooms = new TenancyRoomEntryCollection();
//		for (int i = 0; i < rooms.size(); i++) {
//			RoomInfo room = rooms.get(i);
			room = SHEHelper.queryRoomInfo(room.getId().toString());
			if (existRoomIds.contains(room.getId().toString())) {
				MsgBox.showInfo(this, "房间" + room.getNumber() + "已存在于租赁房间列表中！");
				this.abort();
			}
			if (room.getTenancyState() == null || TenancyStateEnum.unTenancy.equals(room.getTenancyState())) {
				MsgBox.showInfo(this, "房间" + room.getNumber() + "未放租，不能出租！");
				this.abort();
			}
			if(TenancyStateEnum.sincerObligate.equals(room.getTenancyState())){
				MsgBox.showInfo(this,"房间已经被预留，请先进行取消预留操作！");
				this.abort();
			}
			if(TenancyStateEnum.keepTenancy.equals(room.getTenancyState()))
			{
				MsgBox.showInfo(this, "房间" + room.getNumber() + "已封存，不能出租！");
				this.abort();
			}
			
			if(room.getTenancyArea()==null || room.getTenancyArea().compareTo(new BigDecimal(0))==0)
			{
				MsgBox.showInfo(this,"房间" + room.getNumber() + "计租面积为空，不能出租");
				this.abort();
			}
			
			TenancyRoomEntryInfo tenancyRoom = roomToTenRoomEntry(room);
			
			tenRooms.add(tenancyRoom);
//		}

		//新增房间行
		addRoomRows(tenRooms);
		setContMoreRoomsTypeVisiable();

		//重置租金分录
		reSetRentSetInfo(getTenRoomListFromView());

		//更新付款明细等信息
		updatePayListInfo();
		
		this.prmtRentFreeBill.setEnabled(true);
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		if(this.editData.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id","select frentFreeBillId from T_TEN_TenancyBill where fid!='"+this.editData.getId().toString()+"' and frentFreeBillId is not null",CompareType.NOTINNER));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id","select frentFreeBillId from T_TEN_TenancyBill where frentFreeBillId is not null",CompareType.NOTINNER));
		}
		filter.getFilterItems().add(new FilterItemInfo("room.id",room.getId().toString()));
		view.setFilter(filter);
		prmtRentFreeBill.setEntityViewInfo(view);
	}	

	private void setContMoreRoomsTypeVisiable(){
		this.contMoreRoomsType.setVisible(this.tblRoom.getRowCount() > 1);
	}

	protected void btnAddAttachRes_actionPerformed(ActionEvent e) throws Exception {
		//获得选择的配套资源
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.AttachResourceQuery")));
		dlg.show();
		Object[] object = (Object[])dlg.getData();

		if (object == null  ||  object.length == 0) {
			return;
		}

		AttachResourceCollection attachReses = new AttachResourceCollection();
		for(int i=0; i<object.length; i++){
			AttachResourceInfo attachRes = (AttachResourceInfo) object[0];
			attachReses.add(attachRes);
		}

		//判断是否选择了重复的配套资源
		Set existAttachIds = new HashSet();
		for (int i = 0; i < this.tblAttachRes.getRowCount(); i++) {
			IRow row = this.tblAttachRes.getRow(i);
			TenAttachResourceEntryInfo tenAttach = (TenAttachResourceEntryInfo) row.getUserObject();
			if (tenAttach == null || tenAttach.getAttachResource() == null) {
				continue;
			}
			String id = tenAttach.getAttachResource().getId().toString();
			existAttachIds.add(id);
		}

		TenAttachResourceEntryCollection tenAttachReses = new TenAttachResourceEntryCollection();

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.name");
		sels.add("subarea.name");
		sels.add("building.name");

		for (int i = 0; i < attachReses.size(); i++) {
			AttachResourceInfo attach = attachReses.get(i);
			if (existAttachIds.contains(attach.getId().toString())) {
				MsgBox.showInfo(this, "配套资源" + attach.getNumber() + "已存在于租赁配套资源列表中！");
				this.abort();
			}

			if(attach.getRentType() == null  || attach.getStandardRent() == null){
				MsgBox.showInfo(this, "配套资源" + attach.getNumber() + "未定租，不能出租！");
				this.abort();
			}
			attach = AttachResourceFactory.getRemoteInstance().getAttachResourceInfo(new ObjectUuidPK(attach.getId().toString()), sels);
			TenAttachResourceEntryInfo tenAttach = attachToTenAttach(attach);
			tenAttachReses.add(tenAttach);
		}

		//新增配套资源行
		addAttachResRows(tenAttachReses);

		//重置租金分录
		reSetRentSetInfo(getTenRoomListFromView(), getTenAttachResListFromView());

		//更新付款明细等信息
		updatePayListInfo();
	}

	private void addAttachResRows(TenAttachResourceEntryCollection tenAttachReses) {
		for (int i = 0; i < tenAttachReses.size(); i++) {
			TenAttachResourceEntryInfo tenAttach = tenAttachReses.get(i);
			addAttachResRow(tenAttach);
		}
	}

	/**
	 * 根据一项租赁附属资源分录在附属资源列表中增加一行
	 * @param tenAttach
	 * */
	private void addAttachResRow(TenAttachResourceEntryInfo tenAttach) {
		if (tenAttach == null) {
			return;
		}
		AttachResourceInfo attach = tenAttach.getAttachResource();
		if (attach == null) {
			return;
		}

		IRow row = this.tblAttachRes.addRow();
		row.setUserObject(tenAttach);

		//		row.getCell(C_ATTACH_TEN_ROOM_STATE).setValue(tenAttach.getTenRoomState());
		row.getCell(C_ATTACH_ATTACH).setValue(tenAttach.getAttachLongNum());
		//TODO 这里应该也要快照的
		row.getCell(C_ATTACH_ATTACH_RES_TYPE).setValue(attach.getAttachType());
		row.getCell(C_ATTACH_ATTACH_RES_NAME).setValue(attach.getName());
		row.getCell(C_ATTACH_ATTACH_RES_DES).setValue(attach.getDescription());

		row.getCell(C_ATTACH_STANDARD_RENT).setValue(tenAttach.getStandardAttachResRent());
		row.getCell(C_ATTACH_STANDARD_RENT_TYPE).setValue(tenAttach.getStandardRentType());
		//		row.getCell(C_ATTACH_STANDARD_RENT_PRICE).setValue(tenAttach.getStandardAttachResRentPrice());
		//		row.getCell(C_ATTACH_DEAL_RENT).setValue(tenAttach.getDealAttachResRent());
		//		row.getCell(C_ATTACH_DEAL_RENT_TYPE).setValue(tenAttach.getDealRentType());

		//		if (attach != null) {
		//			row.getCell(C_ROOM_FLOOR).setValue(new Integer(attach.getFloor()));
		//			row.getCell(C_ROOM_FITMENT).setValue(attach.getFitmentStandard());
		//			row.getCell(C_ROOM_ROOM_MODEL).setValue(attach.getRoomModel() == null ? null : attach.getRoomModel().getName());
		//			row.getCell(C_ROOM_DIRECTION).setValue(attach.getDirection() == null ? null : attach.getDirection().getName());
		//		}

		row.getCell(C_ROOM_ACT_DELIVER_DATE).setValue(tenAttach.getActDeliveryAttachResDate());
		row.getCell(C_ROOM_ACT_QUIT_DATE).setValue(tenAttach.getActQuitTenDate());

		//更新租金设置Table
		//		updateTblRentSetRow();

	}

	private TenAttachResourceEntryInfo attachToTenAttach(AttachResourceInfo attach) {
		if (attach == null) {
			return null;
		}
		TenAttachResourceEntryInfo tenAttach = new TenAttachResourceEntryInfo();
		tenAttach.setAttachResource(attach);

		//		tenAttach.setTenancyModel(attach.getTenancyModel());
		tenAttach.setHandleState(HandleStateEnum.NoHandleRoom);

		tenAttach.setStandardRentType(attach.getRentType());
		tenAttach.setStandardAttachResRent(attach.getStandardRent());
		//		tenAttach.setStandardAttachResRentPrice(attach.getsta());

		// 执行租价默认赋标准租金价格
		tenAttach.setDealRentType(attach.getRentType());
		tenAttach.setDealAttachResRent(attach.getStandardRent());
		//		tenAttach.setDealRoomRentPrice(attach.getStandardRentPrice());

		if(tenAttach.getDealAttachResRent() != null){
			tenAttach.setDepositAmount(new BigDecimal("2").multiply(tenAttach.getDealAttachResRent()));
		}
		tenAttach.setFirstPayAmount(tenAttach.getDealAttachResRent());

		// 快照房间的建筑面积和实测面积
		// 优先取实测建筑,如果没有则取建筑面积


		//快照房间  装修标准，朝向，景观，户型，房屋形式，建筑性质，产品类型，产品描述，房屋用途


		//设置默认的房间到期标志和房间租赁状态
		tenAttach.setFlagAtTerm(FlagAtTermEnum.Unknown);
		tenAttach.setTenAttachState(TenancyStateEnum.newTenancy);

		// 获得长编码
		StringBuffer sbRoomLongNum = new StringBuffer();
		BuildingInfo building = attach.getBuilding();
		SellProjectInfo sellPro = attach.getSellProject();
		SubareaInfo subArea = attach.getSubarea();

		final String spitStr = "-";
		if (sellPro != null) {
			sbRoomLongNum.append(sellPro.getName());
			sbRoomLongNum.append(spitStr);
		}
		if(subArea != null){
			sbRoomLongNum.append(subArea.getName());
			sbRoomLongNum.append(spitStr);
		}
		if (building != null) {
			sbRoomLongNum.append(building.getName());
			sbRoomLongNum.append(spitStr);
		}
		sbRoomLongNum.append(attach.getNumber());
		tenAttach.setAttachLongNum(sbRoomLongNum.toString());
		return tenAttach;

	}

	public void actionAddCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddCustomer_actionPerformed(e);
		int activeRowIndex = this.tblCustomer.getSelectManager().getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.tblCustomer.addRow();
		} else {
			row = this.tblCustomer.addRow(activeRowIndex + 1);
		}
		row.setHeight(20);
		row.setUserObject(new TenancyCustomerEntryInfo());
		if (this.tblCustomer.getRowCount() == 1) {
			row.getCell(C_CUS_PROPERTY_PERCENT).setValue(new BigDecimal("100"));
		}
	}

	protected void btnAddNewCustomer_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		// 创建UI对象并显示
		String cte="EC";
		uiContext.put("ECT", cte);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerEditUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		
		CustomerEditUI cusEditUI = (CustomerEditUI) uiWindow.getUIObject();
		FDCCustomerInfo cus = (FDCCustomerInfo) cusEditUI.getUIContext().get(CustomerEditUI.KEY_SUBMITTED_DATA);
		if (cus != null) {
			addCustomerRow(cus);
		}
	}
	
	private void addCustomerRow(FDCCustomerInfo customer) {
		int activeRowIndex = this.tblCustomer.getSelectManager().getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.tblCustomer.addRow();
		} else {
			row = this.tblCustomer.addRow(activeRowIndex + 1);
		}
		row.setHeight(20);
		row.setUserObject(new TenancyCustomerEntryInfo());
		if (this.tblCustomer.getRowCount() == 1) {
			row.getCell(C_CUS_PROPERTY_PERCENT).setValue(new BigDecimal("100"));
		}
		row.getCell("customer").setValue(customer);
		row.getCell("phone").setValue(customer.getPhone());
		if(customer.getPostalcode()!=null){
			row.getCell("postalcode").setValue(customer.getPostalcode());
		}
		if(customer.getCertificateName()!=null){
			row.getCell("certificateName").setValue(customer.getCertificateName());
		}
		if(customer.getCertificateNumber()!=null){
			row.getCell("certificateNumber").setValue(customer.getCertificateNumber());
		}
		if(customer.getMailAddress()!=null){
			row.getCell("mailAddress").setValue(customer.getMailAddress());
		}
		row.getCell("bookDate").setValue(customer.getCreateTime());
		if(customer.getDescription()!=null){
			row.getCell("des").setValue(customer.getDescription());
		}
	}
	
	protected void chkIsByAgency_actionPerformed(ActionEvent e) throws Exception {
		super.chkIsByAgency_actionPerformed(e);
		setAgencySourceVisible();
	}

	protected void f7Agency_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Agency_dataChanged(e);
		setAgencySourceVisible();
	}

	protected void f7AgencyContract_dataChanged(DataChangeEvent e) throws Exception {
		super.f7AgencyContract_dataChanged(e);
		if (f7AgencyContract.getData() == null) {
			this.txtPromissoryAgentFee.setValue(null);
			this.pkPromissoryAppPayDate.setValue(null);
		} else {
			updatePromissoryAgentFee();
		}
	}

	/**
	 * 设置代理资料页签是否显示
	 * */
	private void setAgencySourceVisible() {
		boolean isByAgency = this.chkIsByAgency.isSelected();
		SellProjectInfo sellProject = (SellProjectInfo)this.f7SellProject.getValue();
		int i = 6;
		if(sellProject!=null){
			if(!sellProject.isIsForPPM()){
				i=5;
			}
		}
		if (isByAgency) {
			if (this.tabbedPaneContract.getTabCount() == i) {
				this.tabbedPaneContract.add(panelAgency, "代理资料", 4);
			}
			if (this.f7Agency.getData() == null) {
				this.f7AgencyContract.setData(null);
				this.f7AgencyContract.setEnabled(false);
			} else {
				this.f7AgencyContract.setEnabled(true);

				f7AgencyContract.getQueryAgent().setDefaultFilterInfo(null);
				f7AgencyContract.getQueryAgent().setHasCUDefaultFilter(false);
				f7AgencyContract.getQueryAgent().resetRuntimeEntityView();

				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);

				String sellProjectId = ((SellProjectInfo) this.f7SellProject.getData()).getId().toString();
				String agencyId = ((AgencyInfo) this.f7Agency.getData()).getId().toString();

				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
				filter.getFilterItems().add(new FilterItemInfo("agency.id", agencyId));
				this.f7AgencyContract.setEntityViewInfo(view);
			}
			if (f7AgencyContract.getData() == null) {
				this.txtPromissoryAgentFee.setValue(null);
				this.pkPromissoryAppPayDate.setValue(null);
			}
		} else {
			this.tabbedPaneContract.remove(panelAgency);
		}
	}

	public void actionRemoveRoom_actionPerformed(ActionEvent e) throws Exception {
		//删除房间列表上的纪录
		int activeRowIndex = this.tblRoom.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblRoom.getRowCount();
		}
		IRow row = this.tblRoom.getRow(activeRowIndex);
		if (row == null)
			return;
		TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo) row.getUserObject();

		for (int i = 0; i < this.tabMiddle.getTabCount(); i++) {
			String title = this.tabMiddle.getTitleAt(i);
			if (tenRoom.getRoomLongNum().equals(title)) {
				this.tabMiddle.remove(i);
				break;
			}
		}
		this.tblRoom.removeRow(activeRowIndex);
		if(this.tblRoom.getRowCount()>1)
		{
			this.contMoreRoomsType.setVisible(true);
		}else
		{
			this.contMoreRoomsType.setVisible(false);
		}
		//重置租金分录
		reSetRentSetInfo(getTenRoomListFromView());

		//删除房间，可能不需要重置租金分录。TODO 待验证
		updatePayListInfo();
		
		this.prmtRentFreeBill.setValue(null);
		this.prmtRentFreeBill.setEnabled(false);
	}

	protected void btnRemoveAttachRes_actionPerformed(ActionEvent e) throws Exception {
		super.btnRemoveAttachRes_actionPerformed(e);
		removeRow(this.tblAttachRes);
		//重置租金分录
		reSetRentSetInfo(getTenRoomListFromView());
		updatePayListInfo();
	}

	public void actionRemoveCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemoveCustomer_actionPerformed(e);
		removeRow(this.tblCustomer);
	}

	/** 删除table中的一行,有选中时删除选中行,否则删除最后一行 */
	private void removeRow(KDTable table) {
		int activeRowIndex = table.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = table.getRowCount();
		}
		table.removeRow(activeRowIndex);
	}

	public void storeFields() {
		super.storeFields();
		this.editData.setName(this.editData.getTenancyName());

		this.editData.setRentCountType((RentCountTypeEnum)this.comboRentCountType.getSelectedItem());
		if(this.txtDaysPerYear.getIntegerValue()!=null)
		{
			this.editData.setDaysPerYear(this.txtDaysPerYear.getIntegerValue().intValue());
		}	
		this.editData.setIsAutoToInteger(this.chkIsAutoToInteger.isSelected());
		this.editData.setIsAutoToIntegerFee(this.chkIsAutoToIntegerForFee.isSelected());
		
		this.editData.setToIntegerType((ToIntegerTypeEnum)this.comboToIntegerType.getSelectedItem());
		this.editData.setToIntegetTypeFee((ToIntegerTypeEnum)this.comboToIntegerTypeFee.getSelectedItem());
		
		this.editData.setDigit((DigitEnum)this.comboDigit.getSelectedItem());
		this.editData.setDigitFee((DigitEnum)this.comboDigitFee.getSelectedItem());
		
		this.editData.setRentStartType((RentStartTypeEnum)this.comboRentStartType.getSelectedItem());
		this.editData.setStartDateLimit((Date)this.pkStartDateLimit.getValue());
		this.editData.setMoreRoomsType((MoreRoomsTypeEnum)this.comboMoreRoomsType.getSelectedItem());
		this.editData.setIsFreeContract(this.chkIsFreeContract.isSelected());
		this.editData.setFristRevDate((Date)this.pkFristLeaseDate.getValue());
		this.editData.setSecondRevDate((Date)this.DPickFromMonth.getValue());
		this.editData.setOrgUnit((FullOrgUnitInfo)this.f7BussinessDepartMent.getValue());
		//现在storePayList不允许手工修改了，所以可以不用单独保存了
		if(this.chkIsFreeContract.isSelected()){
			fillFreeConTenRoomPayList(getTenRoomListFromView());
		}
		storeTenancyRooms();
		storeTenancyAttachResources();
		storeTenancyCustomer();
		
		//add by yangfan
		storeLiquidated();
		
		//保存递增分录和免租分录
		this.editData.getIncreasedRents().clear();
		this.editData.getIncreasedRents().addCollection(getIncreasedRentsFromView());
		this.editData.getRentFrees().clear();
		this.editData.getRentFrees().addCollection(getRentFreesFromView());
		//

		storeTenRoomPropertyEntrys();
		//storePayList();
		storeOtherPayList();
	}
	
	private void storeOtherPayList() {
		TenancyBillInfo ten = this.editData;
		ten.getOtherPayList().clear();
		for (int i = 0; i < this.tblOtherPayList.getRowCount(); i++) {
			IRow row = this.tblOtherPayList.getRow(i);
			TenBillOtherPayInfo tenOtherInfo = (TenBillOtherPayInfo) row.getUserObject();
			tenOtherInfo.setSeq(i);
			tenOtherInfo.setMoneyDefine((MoneyDefineInfo) row.getCell("moneyTypeName").getValue());
			tenOtherInfo.setAppDate((Date) row.getCell("appDate").getValue());
			tenOtherInfo.setStartDate((Date)row.getCell("startDate").getValue());
			tenOtherInfo.setEndDate((Date)row.getCell("endDate").getValue());
			tenOtherInfo.setCurrency((CurrencyInfo) row.getCell("currency").getValue());
			tenOtherInfo.setAppAmount((BigDecimal) row.getCell("appAmount").getValue());
			tenOtherInfo.setActRevAmount((BigDecimal)row.getCell("actRevAmount").getValue());
			tenOtherInfo.setActRevDate((Date) row.getCell("actRevDate").getValue());
			tenOtherInfo.setDescription((String) row.getCell("description").getValue());
			ten.getOtherPayList().add(tenOtherInfo);
		}
	}

	//保存物业页签相关信息
	private void storeTenRoomPropertyEntrys() {
		for (int i = 0; i < tabMiddle.getTabCount(); i++) {
			Component com = this.tabMiddle.getComponentAt(i);
			TenancyPropertyUI ui = (TenancyPropertyUI) com;
			ui.storeTenEntrys();
		}
	}

	//应收明细不支持手工录入时，该函数也没用
	private void storePayList() {
		// 先获得所有应付金额的列
		List appPayColKeys = getAppPayColKeys();

		TenancyRoomPayListEntryCollection newPayList = new TenancyRoomPayListEntryCollection();
		// 保存所有应付金额和应付日期
		for (int i = 0; i < tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			if(row.getUserObject()!=null)
			{
				ITenancyPayListInfo payListInfo =(ITenancyPayListInfo)row.getUserObject();
				Date appDate = (Date) row.getCell(C_PAYS_APP_PAY_DATE).getValue();
				appDate = FDCDateHelper.getDayBegin(appDate);
				Date startDate = (Date) row.getCell(C_PAYS_START_DATE).getValue();
				startDate = FDCDateHelper.getDayBegin(startDate);
				Date endDate = (Date) row.getCell(C_PAYS_END_DATE).getValue();
				endDate = FDCDateHelper.getDayBegin(endDate);
				int leaseSeq = payListInfo.getLeaseSeq();
				int seq = payListInfo.getSeq();
				MoneyDefineInfo money = payListInfo.getMoneyDefine();
				for (int j = 0; j < appPayColKeys.size(); j++) {
					String key = (String) appPayColKeys.get(j);
					ICell cell = row.getCell(key);
					BigDecimal appAmount = (BigDecimal) cell.getValue();
					Object ob = row.getCell(key).getUserObject();
					TenancyRoomPayListEntryInfo pay = new TenancyRoomPayListEntryInfo();// TODO
					//pay.setTenRoom(item);															// 未考虑配套资源
					pay.setAppAmount(appAmount);
					pay.setAppPayDate(appDate);
					pay.setStartDate(startDate);
					pay.setEndDate(endDate);
					pay.setSeq(seq);
					pay.setMoneyDefine(money);
					pay.setLeaseSeq(leaseSeq);
					newPayList.add(pay);
				}
			}			
		}
	}

	/**
	 * 获得所有应付金额的列
	 * @deprecated 该函数目前没用
	 * */
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

	private void storeTenancyCustomer() {
		TenancyBillInfo tenBill = this.editData;
		TenancyCustomerEntryCollection customerInfos = tenBill.getTenCustomerList();
		customerInfos.clear();
		StringBuffer tenCustomerDes = new StringBuffer();
		for (int i = 0; i < this.tblCustomer.getRowCount(); i++) {
			IRow row = this.tblCustomer.getRow(i);
			TenancyCustomerEntryInfo customerInfo = (TenancyCustomerEntryInfo) row.getUserObject();
			FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell(C_CUS_CUSTOMER).getValue();
			if(customer==null)
			{
				MsgBox.showInfo("租赁客户不能为空!");
				this.abort();
			}
			if (customer != null) {
				customer.setMailAddress((String) row.getCell(C_CUS_MAIL_ADDRESS).getValue());
				customer.setPhone((String) row.getCell(C_CUS_PHONE).getValue());
				customer.setPostalcode((String) row.getCell(C_CUS_POSTALCODE).getValue());
				if (row.getCell(C_CUS_CERTIFICATE_NAME).getValue() != null) {
//					customer.setCertificateName((CertifacateNameEnum) row.getCell(C_CUS_CERTIFICATE_NAME).getValue());
				}
				customer.setCertificateNumber((String) row.getCell(C_CUS_CERTIFICATE_NUMBER).getValue());
			}
			customerInfo.setFdcCustomer(customer);
			customerInfo.setPropertyPercent((BigDecimal) row.getCell(C_CUS_PROPERTY_PERCENT).getValue());
			customerInfo.setDescription((String) row.getCell(C_CUS_DES).getValue());
			customerInfos.add(customerInfo);
			if (i != 0) {
				tenCustomerDes.append(",");
			}
			tenCustomerDes.append(customer.getName());
		}
		tenBill.setTenCustomerDes(tenCustomerDes.toString());
	}

	private void storeTenancyAttachResources() {
		TenancyBillInfo tenBill = this.editData;
		TenAttachResourceEntryCollection tenAttachs = tenBill.getTenAttachResourceList();
		tenAttachs.clear();
		StringBuffer attachDes = new StringBuffer();
		// 因为在房间列表的edittstopped事件中,已经将变动的值更新到row.getUserObject中,
		// 所以这里只需要将userObject取出即可
		for (int i = 0; i < this.tblAttachRes.getRowCount(); i++) {
			IRow row = this.tblAttachRes.getRow(i);
			TenAttachResourceEntryInfo tenAttach = (TenAttachResourceEntryInfo) row.getUserObject();

			//			tenAttach.setDescription((String) row.getCell(C_ROOM_DES).getValue());
			tenAttach.setStartDate(tenBill.getStartDate());
			tenAttach.setEndDate(tenBill.getEndDate());
			tenAttachs.add(tenAttach);
			if (i != 0) {
				attachDes.append(",");
			}
			attachDes.append(tenAttach.getAttachLongNum());
		}
		tenBill.setTenAttachesDes(attachDes.toString());

	}

	private void storeTenancyRooms() {
		TenancyBillInfo tenBill = this.editData;
		TenancyRoomEntryCollection tenancyRooms = tenBill.getTenancyRoomList();
		tenancyRooms.clear();
		StringBuffer roomsDes = new StringBuffer();
		String roomDesString =null;
		// 因为在房间列表的edittstopped事件中,已经将变动的值更新到row.getUserObject中,
		// 所以这里只需要将userObject取出即可
		boolean flag= true;
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			TenancyRoomEntryInfo tenancyRoom = (TenancyRoomEntryInfo) row.getUserObject();

			tenancyRoom.setDescription((String) row.getCell(C_ROOM_DES).getValue());
			tenancyRoom.setStartDate(tenBill.getStartDate());
			tenancyRoom.setEndDate(tenBill.getEndDate());
			tenancyRooms.add(tenancyRoom);
			if (i != 0) {
				roomsDes.append(",");
			}
			
			roomsDes.append(tenancyRoom.getRoomLongNum());
			//租赁房间描述超过255时将只会被截断 eirc_wang 2010.07.01
			 if(flag){
				 if(roomsDes.toString().length()>250){
					roomDesString = roomsDes.substring(0, 250);
					roomDesString =roomDesString+"...";
					flag =false;
				}else
				{
					//当房间描述不超过255时却不给他赋值，导致序时薄出租房间显示全都为空
					roomDesString = roomsDes.toString();
				}
			 }
		}
		tenBill.setTenRoomsDes(roomDesString);
	}
	/**
	 * add by yangfan
	 */
	private void storeLiquidated(){
		TenancyBillInfo tenBill = this.editData;
		TenLiquidatedCollection collection=tenBill.getTenLiquidated();
		collection.clear();
		
		for (int i = 0; i < this.tblLiquidated.getRowCount(); i++) {
			IRow row = this.tblLiquidated.getRow(i);
			TenLiquidatedInfo info=(TenLiquidatedInfo)row.getUserObject();
			info.setMoneyDefine((MoneyDefineInfo)row.getCell(C_LIQUI_MONEYDEFINE).getValue());
			info.setLiquidated((LiquidatedInfo)row.getCell(C_LIQUI_LIQUIDATED).getValue());
			collection.add(info);
		}
	}
	
	/**
	 * 构建新对象,这里主要有2点操作： <br>
	 * 1.根据传入的KEY_ROOMS,构建租赁房间分录 <br>
	 * 2.如果合同是改租,续租或者更名,将原合同的相关信息带到新合同.
	 * */
	protected IObjectValue createNewData() {
		TenancyBillInfo tenancyBill = new TenancyBillInfo();
		tenancyBill.setId(BOSUuid.create(tenancyBill.getBOSType()));
		tenancyBill.setTenBillRoomState(TenBillRoomStateEnum.TEN);
		// 从租赁控制界面传来的房间
		RoomCollection rooms = (RoomCollection) this.getUIContext().get(KEY_ROOMS);
		if (rooms != null) {
			for (int i = 0; i < rooms.size(); i++) {
				RoomInfo room = rooms.get(i);
				//TODO 如果支持多个房间的租赁，这多个房间还必须是同一个项目的.
				if(TenancyStateEnum.sincerObligate.equals(room.getTenancyState())){
					MsgBox.showInfo(this,"房间已经被预留，请先进行取消预留操作！");
					this.abort();
				}
				if (i == 0) {
					SellProjectInfo sellProject = room.getBuilding().getSellProject();
					tenancyBill.setSellProject(sellProject);
				}
				TenancyRoomEntryInfo tenRoom = roomToTenRoomEntry(room);
				tenancyBill.getTenancyRoomList().add(tenRoom);
			}
		} else {
			tenancyBill.setSellProject((SellProjectInfo) this.getUIContext().get(KEY_SELL_PROJECT));
		}
		
		//诚意预留转认租
		SincerObligateInfo sinInfo = (SincerObligateInfo)this.getUIContext().get("sincerObligate");
		if(sinInfo!=null)
		{
			try {
				sinInfo = TenancyHelper.getSincerObligateInfo(sinInfo.getId()
						.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
				sinInfo.setBizState(BizStateEnum.TENANCY);
				tenancyBill.setSincerObligate(sinInfo);
				tenancyBill.setSellProject(sinInfo.getSellProject());
				TenancyRoomEntryCollection tenancyColl = tenancyBill.getTenancyRoomList();
				TenAttachResourceEntryCollection tenAttColl = tenancyBill.getTenAttachResourceList();
				TenancyCustomerEntryCollection tenCusColl = tenancyBill.getTenCustomerList();
				SinObligateRoomsEntryCollection sincerColl = sinInfo.getSinRoomList();
				SinObligateAttachEntryCollection sinAttColl = sinInfo.getSinAttachList();
				SinCustomerEntrysCollection sinCusColl = sinInfo.getSinCustomerList();
				for(int i=0;i<sincerColl.size();i++)
				{
					TenancyRoomEntryInfo tenInfo = new TenancyRoomEntryInfo();
					SinObligateRoomsEntryInfo sinRoomEntryInfo = sincerColl.get(i);
					tenInfo.setRoom(sinRoomEntryInfo.getRoom());
					tenInfo = roomToTenRoomEntry(sinRoomEntryInfo.getRoom());
					tenancyColl.add(tenInfo);	
				}
				for(int i=0;i<sinAttColl.size();i++)
				{
					TenAttachResourceEntryInfo tenAttachInfo = new TenAttachResourceEntryInfo();
					SinObligateAttachEntryInfo sinAttInfo = sinAttColl.get(i);
					tenAttachInfo.setAttachResource(sinAttInfo.getAttachResource());
					tenAttachInfo = attachToTenAttach(sinAttInfo.getAttachResource());
					tenAttColl.add(tenAttachInfo);
				}
				for(int i=0;i<sinCusColl.size();i++)
				{
					TenancyCustomerEntryInfo tenCusInfo = new TenancyCustomerEntryInfo();
					SinCustomerEntrysInfo sinCusInfo = sinCusColl.get(i);
					tenCusInfo.setFdcCustomer(sinCusInfo.getFdcCustomer());
					tenCusInfo.setPropertyPercent(sinCusInfo.getPropertyPercent());
					tenCusColl.add(tenCusInfo);
				}
		}

		// 取租赁类型,如果为空,默认是新租
		TenancyContractTypeEnum contractType = (TenancyContractTypeEnum) this.getUIContext().get(KEY_TENANCY_CONTRACT_TYPE);
		if (contractType == null) {
			contractType = TenancyContractTypeEnum.NewTenancy;
		}
		tenancyBill.setTenancyType(contractType);

		tenancyBill.setFlagAtTerm(FlagAtTermEnum.Unknown);
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		tenancyBill.setTenancyAdviser(user);
		// 默认收租日为租期前5天
		tenancyBill.setChargeDateType(ChargeDateTypeEnum.BeforeStartDate);
		tenancyBill.setChargeOffsetDays(5);

		tenancyBill.setStartDate(new Date());
		tenancyBill.setLeaseTime(1);
		tenancyBill.setTenancyDate(new Date());
		tenancyBill.setIsFreeDaysInLease(true);
		tenancyBill.setFirstLeaseType(FirstLeaseTypeEnum.WholeFirstLease);

		tenancyBill.setRentCountType(RentCountTypeEnum.ActDateCount);
		tenancyBill.setRentStartType(RentStartTypeEnum.SettledStartDate);

		tenancyBill.setCreator(user);
		tenancyBill.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		tenancyBill.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		tenancyBill.setBookedDate(new Date());
		tenancyBill.setCreateTime(new Timestamp(new Date().getTime()));
		//首期应收日期
		tenancyBill.setFristRevDate(new Date());
		tenancyBill.setMoreRoomsType(MoreRoomsTypeEnum.RoomRentSetting);
		tenancyBill.setIsFreeContract(false);
		//		tenancyBill.setRentCountType(item);
		//		tenancyBill.setRentStartType(item);


		// 获得原合同ID
		String oldTenancyBillId = (String) this.getUIContext().get(KEY_OLD_TENANCY_BILL_ID);
		if (TenancyContractTypeEnum.RejiggerTenancy.equals(contractType) || TenancyContractTypeEnum.ContinueTenancy.equals(contractType) || TenancyContractTypeEnum.ChangeName.equals(contractType)||TenancyContractTypeEnum.PriceChange.equals(contractType)) {
			setTenancyOfContinueOrChange(tenancyBill, contractType, oldTenancyBillId);
		}
		
		//取得销售项目 by renliang at 2010-6-29
		sellProjectInfo = getSellProjectFormUIContext();
		if(sellProjectInfo != null){
			tenancyBill.setSellProject(sellProjectInfo);
		}
		
		//add by yangfan
		if(TenancyContractTypeEnum.NewTenancy.equals(contractType)){
			try {
				MoneyDefineCollection mdcollection =MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection("select * where sysType='"+MoneySysTypeEnum.TENANCYSYS_VALUE+"' order by number");
				for(int i=0;i<mdcollection.size();i++){
					TenLiquidatedInfo info=new TenLiquidatedInfo();
					info.setMoneyDefine(mdcollection.get(i));
					tenancyBill.getTenLiquidated().add(info);
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
			
			tenancyBill.setRateDate(DateEnum.DAY);
			tenancyBill.setReliefDate(DateEnum.DAY);
	        tenancyBill.setStandardDate(DateEnum.DAY);
	        tenancyBill.setOccurred(OccurreStateEnum.PRIOR);
	        tenancyBill.setBit(MoneyEnum.YUAN);
		}
		return tenancyBill;
	}
	
	/**
	 * 取得销售项目
	 * @author liang_ren969
	 * @date 2010-6-29
	 * @return SellProjectInfo
	 */
	public SellProjectInfo getSellProjectFormUIContext(){
	    	if(getUIContext().get("sellProject") != null){
	    		sellProjectInfo = (SellProjectInfo) getUIContext().get("sellProject");
	    	}
	    	return sellProjectInfo;
	}
	
	protected void f7SincerObligate_dataChanged(DataChangeEvent e)
			throws Exception {
		SincerObligateInfo sincerInfo = (SincerObligateInfo)this.f7SincerObligate.getValue();
		if(sincerInfo==null){
			this.tblRoom.removeRows();
			this.tblCustomer.removeRows();
		}else{
			sincerInfo = TenancyHelper.getSincerObligateInfo(sincerInfo.getId()
					.toString());
			SinObligateRoomsEntryCollection sincerColl = sincerInfo.getSinRoomList();
			this.tblRoom.removeRows();
			tabMiddle.removeAll();
			//modified by weiqiang_chen PBG053457
//			Map roomMap= new HashMap();
//			for(int j = 0,n = tblRoom.getRowCount(); j < n;j ++){
//				IRow row = tblRoom.getRow(j);
//				TenancyRoomEntryInfo info = (TenancyRoomEntryInfo) row.getUserObject();
//				if(info != null && info.getRoom() != null){
//					roomMap.put(info.getRoom().getId().toString(), new Integer(j));
//				}
//			}
			for(int i=0;i<sincerColl.size();i++)
			{
				TenancyRoomEntryInfo tenInfo = new TenancyRoomEntryInfo();
				SinObligateRoomsEntryInfo sinRoomEntryInfo = sincerColl.get(i);
				tenInfo.setRoom(sinRoomEntryInfo.getRoom());
//				if(sinRoomEntryInfo.getRoom() != null && 
//						roomMap.containsKey(sinRoomEntryInfo.getRoom().getId().toString())){
//					continue;
//				}
				tenInfo = roomToTenRoomEntry(sinRoomEntryInfo.getRoom());
				addRoomRow(tenInfo)	;
			}
			//modified by weiqiang_chen PBG053457
//			Map  custmap = new HashMap();
//			for(int j = 0,n = tblCustomer.getRowCount();j < n;j++){
//				IRow row = tblCustomer.getRow(j);
//				TenancyCustomerEntryInfo info = (TenancyCustomerEntryInfo) row.getUserObject();
//				if(info != null && info.getFdcCustomer() != null){
//					custmap.put(info.getFdcCustomer().getId().toString(), new Integer(j));
//				}
//			}
			SinCustomerEntrysCollection sinCusColl = sincerInfo.getSinCustomerList();
			TenancyCustomerEntryCollection tenCusColl = new TenancyCustomerEntryCollection();
			for(int i=0;i<sinCusColl.size();i++)
			{
				TenancyCustomerEntryInfo tenCusInfo = new TenancyCustomerEntryInfo();
				SinCustomerEntrysInfo sinCusInfo = sinCusColl.get(i);
				tenCusInfo.setFdcCustomer(sinCusInfo.getFdcCustomer());
//				if(sinCusInfo.getFdcCustomer() != null && 
//						custmap.containsKey(sinCusInfo.getFdcCustomer().getId().toString())){
//					continue;
//				}
				tenCusInfo.setPropertyPercent(sinCusInfo.getPropertyPercent());
				tenCusColl.add(tenCusInfo);
			}
			loadCustomers(tenCusColl);
			}
	}
	
	/*
	 * 根据需求要求，给诚意字段进行过滤，只显示已预留状态的记录
	 * 修改人：赵国阳
	 * 修改日期：2010-04-20
	 */
	private void setSincerFilter()
	{
		SellProjectInfo projectInfo = (SellProjectInfo)this.getUIContext().get("sellProject");
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (projectInfo != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", projectInfo.getId()
							.toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("bizState",BizStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(
					new FilterItemInfo("bizState",BizStateEnum.SINCEROBLIGATED_VALUE));
//			filter.setMaskString("#0 and (#1 or #2)");
			filter.setMaskString("#0 and #2)");
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", null));
		}
		viewInfo.setFilter(filter);
		f7SincerObligate.setEntityViewInfo(viewInfo);
	}

	/**
	 * 如果是改租或则续租或者更名,将原合同的相关信息带入新合同
	 * */
	private void setTenancyOfContinueOrChange(TenancyBillInfo tenancyBill, TenancyContractTypeEnum contractType, String oldTenancyBillId) {
		if (oldTenancyBillId != null) {
			SelectorItemCollection sels = this.getSelectors();
			try {
//				oldTenancyBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(oldTenancyBillId), sels);
				oldTenancyBill = TenancyHelper.getTenancyBillInfo(oldTenancyBillId);
			} catch (EASBizException e) {
				handleException(e);
			} catch (BOSException e) {
				handleException(e);
			}
		}

		if (oldTenancyBill == null) {
			MsgBox.showInfo("改续租或更名没有原合同.");
			this.abort();
		}

		tenancyBill.setOldTenancyBill(oldTenancyBill);
		tenancyBill.setSellProject(oldTenancyBill.getSellProject());

		// 将原合同的客户信息带入新合同
		TenancyCustomerEntryCollection tenCustomers = oldTenancyBill.getTenCustomerList();
		for (int i = 0; i < tenCustomers.size(); i++) {
			TenancyCustomerEntryInfo tenCustomer = (TenancyCustomerEntryInfo) tenCustomers.get(i).clone();
			tenCustomer.setId(null);// 续改租租合同需要新增租赁客户记录,设置ID为null
			tenancyBill.getTenCustomerList().add(tenCustomer);
		}
		tenancyBill.setTenCustomerDes(oldTenancyBill.getTenCustomerDes());

		//将原合同的递增信息带入新合同
		IncreasedRentEntryCollection increaseds = oldTenancyBill.getIncreasedRents();
		for(int i=0; i<increaseds.size(); i++){
			IncreasedRentEntryInfo increased = increaseds.get(i);
			increased.setId(null);
			tenancyBill.getIncreasedRents().add(increased);
		}

		//将原合同的免租信息带入新合同
		RentFreeEntryCollection frees = oldTenancyBill.getRentFrees();
		for(int i=0; i<frees.size(); i++){
			RentFreeEntryInfo free = frees.get(i);
			free.setId(null);
			tenancyBill.getRentFrees().add(free);
		}
		

		// 将原合同的租赁房间带入新合同
		TenancyRoomEntryCollection tenRooms = oldTenancyBill.getTenancyRoomList();
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo) tenRooms.get(i).clone();
//			tenRoom.getRoomPayList().clear();// 改续租合同,新增租赁房间分录记录
//			tenRoom.getDealAmounts().clear();
			tenRoom.setId(null);
			tenRoom.setFlagAtTerm(FlagAtTermEnum.Unknown);
			tenRoom.setHandleState(HandleStateEnum.NoHandleRoom);
			tenRoom.setStartDate(null);
			tenRoom.setEndDate(null);
			tenRoom.setActDeliveryRoomDate(null);
			tenRoom.setActQuitTenDate(null);
			tenRoom.setRemainDepositAmount(null);
			tenRoom.setRoomTotalRent(null);
			if(TenancyContractTypeEnum.PriceChange.equals(contractType)){
				DealAmountEntryCollection DealAmounts = tenRoom.getDealAmounts();
				for(int k =0 ;k<DealAmounts.size();k++){
					DealAmountEntryInfo dealAmountEntryInfo = DealAmounts.get(k);
					dealAmountEntryInfo.setId(null);
					if(MoneyTypeEnum.RentAmount.equals(dealAmountEntryInfo.getMoneyDefine().getMoneyType())){
						//加入这个是因为： 在加载租金设置里租金成交分录的租金时 取得时房间分录上的成交租金
						tenRoom.setDealRent(dealAmountEntryInfo.getAmount());
					}
				}
					TenancyRoomPayListEntryCollection tenancyRoomPayLists = tenRoom.getRoomPayList();
					for(int p =0 ;p<tenancyRoomPayLists.size();p++){
						TenancyRoomPayListEntryInfo roomPayInfo = tenancyRoomPayLists.get(p);
						roomPayInfo.setId(null); 
					}
			}else{
				tenRoom.getDealAmounts().clear();
				tenRoom.getRoomPayList().clear();
			}

			// 将物业相关属性的ID清掉
			TenancyRoomChargeEntryCollection tenRoomCharges = tenRoom.getTenRoomCharges();
			for (int j = 0; j < tenRoomCharges.size(); j++) {
				TenancyRoomChargeEntryInfo tenRoomCharge = tenRoomCharges.get(j);
				tenRoomCharge.setId(null);
			}
			EquipmentEntryCollection eqs = tenRoom.getEquipments();
			for (int j = 0; j < eqs.size(); j++) {
				EquipmentEntryInfo eq = eqs.get(j);
				eq.setId(null);
			}

			tenancyBill.getTenancyRoomList().add(tenRoom);
		}
		tenancyBill.setTenRoomsDes(oldTenancyBill.getTenRoomsDes());

		// 将原合同的租赁配套资源带入新合同
		TenAttachResourceEntryCollection tenAttaches = oldTenancyBill.getTenAttachResourceList();
		for (int i = 0; i < tenAttaches.size(); i++) {
			TenAttachResourceEntryInfo tenAttach = (TenAttachResourceEntryInfo) tenAttaches.get(i).clone();
			tenAttach.getAttachResPayList().clear();
			tenAttach.getDealAmounts().clear();
			tenAttach.setId(null);
			tenAttach.setFlagAtTerm(FlagAtTermEnum.Unknown);
			tenAttach.setHandleState(HandleStateEnum.NoHandleRoom);
			tenAttach.setStartDate(null);
			tenAttach.setEndDate(null);
			tenAttach.setActDeliveryAttachResDate(null);
			tenAttach.setActQuitTenDate(null);
			tenAttach.setAttachTotalRent(null);
			tenancyBill.getTenAttachResourceList().add(tenAttach);
		}
		
		//add by yangfan
		TenLiquidatedCollection tenLiquidated=oldTenancyBill.getTenLiquidated();
		for(int i=0;i<tenLiquidated.size();i++){
			TenLiquidatedInfo tenLiqInfo=(TenLiquidatedInfo)tenLiquidated.get(i).clone();
			tenLiqInfo.setId(null);
			tenancyBill.getTenLiquidated().add(tenLiqInfo);
		}
		tenancyBill.setRate(oldTenancyBill.getRate());
		tenancyBill.setIsAccountLiquidated(oldTenancyBill.isIsAccountLiquidated());
		tenancyBill.setIsMDLiquidated(oldTenancyBill.isIsMDLiquidated());
		tenancyBill.setRateDate(oldTenancyBill.getRateDate());
		tenancyBill.setRelief(oldTenancyBill.getRelief());
		tenancyBill.setReliefDate(oldTenancyBill.getReliefDate());
		tenancyBill.setStandard(oldTenancyBill.getStandard());
		tenancyBill.setStandardDate(oldTenancyBill.getStandardDate());
		tenancyBill.setBit(oldTenancyBill.getBit());
		tenancyBill.setOccurred(oldTenancyBill.getOccurred());
		tenancyBill.setFirstLeaseEndDate(oldTenancyBill.getFirstLeaseEndDate());
		
		
		
		tenancyBill.setTenAttachesDes(oldTenancyBill.getTenAttachesDes());

		tenancyBill.setFirstLeaseType(oldTenancyBill.getFirstLeaseType());

		tenancyBill.setAgency(oldTenancyBill.getAgency());
		tenancyBill.setLeaseTime(oldTenancyBill.getLeaseTime());
		tenancyBill.setChargeDateType(oldTenancyBill.getChargeDateType());
		tenancyBill.setChargeOffsetDays(oldTenancyBill.getChargeOffsetDays());
		tenancyBill.setPayeeBank(oldTenancyBill.getPayeeBank());
		tenancyBill.setIsFreeContract(oldTenancyBill.isIsFreeContract());
		tenancyBill.setFristRevDate(oldTenancyBill.getFristRevDate());
		tenancyBill.setOrgUnit(oldTenancyBill.getOrgUnit());
		tenancyBill.setSecondRevDate(oldTenancyBill.getSecondRevDate());

		// 续租的起始日期一定是原合同的结束日期的后1天
		if (TenancyContractTypeEnum.ContinueTenancy.equals(contractType)) {
			tenancyBill.setStartDate(TenancyHelper.addCalendar(oldTenancyBill.getEndDate(), Calendar.DATE, 1));
		} else if (TenancyContractTypeEnum.ChangeName.equals(contractType)||TenancyContractTypeEnum.PriceChange.equals(contractType)) {
			tenancyBill.setStartDate(oldTenancyBill.getStartDate());
			tenancyBill.setEndDate(oldTenancyBill.getEndDate());
		} 

	}
	
	private void viftyOtherPayList()
	{
		if(tblOtherPayList.getRowCount()>0)
		{
			for(int i=0;i<tblOtherPayList.getRowCount();i++)
			{
				IRow row = this.tblOtherPayList.getRow(i);
				if(row.getCell("moneyTypeName").getValue()==null)
				{
					MsgBox.showInfo("其他应收第"+(i+1)+"行款项名称不能为空!");
					this.abort();
				}
				if(row.getCell("appDate").getValue()==null)
				{
					MsgBox.showInfo("其他应收第"+(i+1)+"行应收日期不能为空!");
					this.abort();
				}
				if(row.getCell("appAmount").getValue()==null || ((BigDecimal)row.getCell("appAmount").getValue()).compareTo(new BigDecimal(0))==0)
				{
					MsgBox.showInfo("其他应收第"+(i+1)+"行应收金额不能为空或者零!");
					this.abort();
				}
			}
		}
		
	}

	private void checkPayList()
	{
		IRow row = tblPayList.getRow(0);
		Date startDate = (Date)row.getCell(C_PAYS_START_DATE).getValue();
		startDate = FDCDateHelper.getDayBegin(startDate);
		Date conStratDate = (Date)this.pkStartDate.getValue();
		conStratDate = FDCDateHelper.getDayBegin(conStratDate);
		if(startDate.compareTo(conStratDate)!=0)
		{
			MsgBox.showInfo("收款明细第一行起始日期和合同起始日期必须一致");
			this.abort();
		}

		IRow row2 = tblPayList.getRow(tblPayList.getRowCount()-1);
		Date endDate = (Date)row2.getCell(C_PAYS_END_DATE).getValue();
		endDate = FDCDateHelper.getDayBegin(endDate);
		Date conEndDate = (Date)this.pkEndDate.getValue();
		conEndDate = FDCDateHelper.getDayBegin(conEndDate);
		if(endDate.compareTo(conEndDate)!=0)
		{
			MsgBox.showInfo("收款明细最后行结束日期和合同结束日期必须一致");
			this.abort();
		}

		//
		List payList = new ArrayList();
		if(tblPayList.getRow(1)==null)return;
		ITenancyPayListInfo tenPayInfo = (ITenancyPayListInfo)tblPayList.getRow(1).getUserObject();
		int seqq = tenPayInfo.getLeaseSeq();
		payList.add(tenPayInfo);
		for(int i=0;i<tblPayList.getRowCount();i++)
		{
			IRow row4 = tblPayList.getRow(i);
			if(row4.getUserObject()!=null)
			{
				ITenancyPayListInfo tenPayInfo2 = (ITenancyPayListInfo)row4.getUserObject();
				if(tenPayInfo2.getLeaseSeq()>seqq)
				{
					payList.add(tenPayInfo2);
					seqq++;
				}
			}
		}
		ITenancyPayListInfo payInfo = (ITenancyPayListInfo)payList.get(0);
		int seq = payInfo.getLeaseSeq();
		for(int i=0;i<payList.size();i++)
		{
			ITenancyPayListInfo payInfo2 = (ITenancyPayListInfo)payList.get(i);
			int seq2 = payInfo2.getLeaseSeq();
			if(seq2>seq)
			{
				Date stratDate = payInfo2.getStartDate();
				stratDate = FDCDateHelper.getDayBegin(stratDate);
				Date endDate2 = payInfo.getEndDate();
				Date nextDate = SHEHelper.getNextDay(endDate2);				
				if(nextDate.compareTo(stratDate)!=0)
				{					
					MsgBox.showInfo("第"+seq+"、"+seq2+"行起始结束时间首尾不能相连");
					this.abort();
				}else
				{
					payInfo = payInfo2;
					seq = seq2;
				}
			}
		}
		vifyDisAmount();
		vifyPayListAmount();
	}
	
	private void vifyPayListAmount()
	{
		boolean boo = false;
		for(int i=0;i<tblPayList.getRowCount();i++)
		{
			IRow row = tblPayList.getRow(i);
			if(row.getUserObject()!=null)
			{
				for(int j=0;j<getAppPayColKeys().size();j++)
				{
					String key = (String) getAppPayColKeys().get(j);
					ICell cell = row.getCell(key);
					BigDecimal appAmount = (BigDecimal) cell.getValue();
					if(appAmount==null || appAmount.compareTo(new BigDecimal(0))==0)
					{
						Object obj = row.getCell(C_PAYS_MONEY_DEFINE).getValue();
						if(obj instanceof String)
						{
							String des = ((String)obj).trim();
							if("租金".equals(des))
							{
								boo = true;
//								MsgBox.showInfo("付款明细行租金应付金额不能为空!");
//								this.abort();
							}
						}else if(obj instanceof MoneyDefineInfo)							
						{
							MoneyDefineInfo money = (MoneyDefineInfo)row.getCell(C_PAYS_MONEY_DEFINE).getValue();
							if(MoneyTypeEnum.RentAmount.equals(money.getMoneyType()))
							{
								boo = true;
//								MsgBox.showInfo("付款明细行租金应付金额不能为空!");
//								this.abort();
							}
						}																	
					}
				}
			}
		}
		if(boo)
		{
			int a = MsgBox.showConfirm2New(null,"收款明细有租金为零的行，确定提交吗?");
			if(JOptionPane.NO_OPTION==a)
			{
				this.abort();
			}
		}
	}

	//校验付款明细的押金是否和租金设置的押金一致
	private void vifyDisAmount()
	{	
		BigDecimal sumdisAmount = new BigDecimal(0);
		for(int i=0;i<tblPayList.getRowCount();i++)
		{
			IRow payListRow = tblPayList.getRow(i);
			if(payListRow.getUserObject()!=null)
			{
//				TenancyRoomEntryInfo tenEntry = (TenancyRoomEntryInfo) row.getUserObject();	
				//租金设置里的押金金额及款项类型都存在MAP里，
				for(int j=0;j<getAppPayColKeys().size();j++)
				{
					String key = (String) getAppPayColKeys().get(j);
					ICell cell = payListRow.getCell(key);
					BigDecimal appAmount = (BigDecimal) cell.getValue();
					appAmount = appAmount == null ?new BigDecimal(0):appAmount;
					Object obj = payListRow.getCell(C_PAYS_MONEY_DEFINE).getValue();
					String moneyName = "";
					if(obj instanceof String)
					{
						moneyName = ((String)obj).trim();
					}else if(obj instanceof MoneyDefineInfo)
					{
						MoneyDefineInfo money = (MoneyDefineInfo)obj;
						moneyName = money.getName();
					}
					TenancyRoomPayListEntryInfo tenPayInfo = (TenancyRoomPayListEntryInfo)payListRow.getCell(key).getUserObject();
					if(tenPayInfo!=null && tenPayInfo.getTenRoom()!=null && tenPayInfo.getTenRoom().getRoom()!=null)
					{
						if(roomDisMap.get(tenPayInfo.getTenRoom().getRoom().getId().toString())!=null)
						{
							Map map = (Map)roomDisMap.get(tenPayInfo.getTenRoom().getRoom().getId().toString());
							for(Iterator itor = map.keySet().iterator(); itor.hasNext();)
							{
								MoneyDefineInfo moneyInfo = (MoneyDefineInfo)itor.next();
								BigDecimal disAmount = (BigDecimal)map.get(moneyInfo);
								disAmount = disAmount==null?new BigDecimal(0):disAmount;
								if(moneyInfo.getName().equals(moneyName))
								{
									if(disAmount.compareTo(appAmount)!=0)
									{
										try {
										    RoomInfo roomInfo = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(tenPayInfo.getTenRoom().getRoom().getId().toString()));
											MsgBox.showInfo("房间"+roomInfo.getName()+"的"+moneyInfo.getName()+"设置和收款明细的押金不一致");
											this.abort();
									} catch (EASBizException e) {
										e.printStackTrace();
									} catch (BOSException e) {
										e.printStackTrace();
									}
									}else
									{
										sumdisAmount = sumdisAmount.add(disAmount);
									}								
								}
							}						
						}
					}				
				}
			}			
		}
		this.txtDepositAmount.setValue(sumdisAmount);
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		String id = this.editData.getId().toString();
		TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
		TenancyContractTypeEnum tenType = tenBill.getTenancyType();

		if (OtherBillFactory.getRemoteInstance().exists("select id from where tenancyBill.id='"+id+"'")) {
			MsgBox.showInfo(this, "存在其他合同，禁止反审批操作！");
			this.abort();
		}
		if (DepositDealBillFactory.getRemoteInstance().exists("select id from where tenancyBill.id='"+id+"'")) {
			MsgBox.showInfo(this, "存在押金处理申请单，禁止反审批操作！");
			this.abort();
		}
		if (!tenType.equals(TenancyContractTypeEnum.NewTenancy)) {
			MsgBox.showInfo(this, "只有新租合同才允许反审批！");
			this.abort();
		}
		
		if(!TenancyBillStateEnum.Audited.equals(tenBill.getTenancyState())){
			MsgBox.showInfo(this, "只有审批状态的合同才允许反审批！");
			this.abort();
		}
		
		boolean isjiaojie = false;
		TenancyRoomEntryCollection list = tenBill.getTenancyRoomList();
		for (int i = 0; i < list.size(); i++) {
			TenancyRoomEntryInfo entry = list.get(i);
			if (!entry.getHandleState().equals(HandleStateEnum.NoHandleRoom)) {
				isjiaojie = true;
				break;
			}
		}
		FilterInfo info = new FilterInfo();
		info.appendFilterItem("tenancyObj.id", tenBill.getId());
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(info);
		FDCReceivingBillCollection c = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillCollection(view);
		if (isjiaojie = false || c.size() == 0) {
			TenancyBillFactory.getRemoteInstance().antiAudit(tenBill.getId());
			MsgBox.showInfo(this, "反审批成功！");
		} else {
			MsgBox.showInfo(this, "只有未交接房间、未收款的新租合同才允许反审批！");
		}
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
//		super.actionAudit_actionPerformed(e);
		if(this.editData.getId()==null){
			MsgBox.showInfo(this, "请先提交单据");
			this.abort();
		}
		String id = this.editData.getId().toString();
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(!TenancyBillStateEnum.Submitted.equals(tenState)){
    		if(TenancyBillStateEnum.Audited.equals(tenState)){
    			MsgBox.showInfo(this, "合同已经审批！");
    			this.abort();
    		}else{
    			MsgBox.showInfo(this, "只有已提交的合同才能审批！");
    			this.abort();
    		}
    		
    	}
    	TenancyBillFactory.getRemoteInstance().audit(BOSUuid.read(id));
    	MsgBox.showInfo(this, "审批成功！");
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {		
		String oldOprt = this.oprtState;
		super.actionSubmit_actionPerformed(e);
		isFreeConSet(this.chkIsFreeContract.isSelected());
		// 商机关联业务单据包括,认租单 新增提交后增加商机跟进
		if (oldOprt.equals(OprtState.ADDNEW)) {
			this.getUIContext().remove(KEY_ROOMS);
			this.getUIContext().remove(KEY_OLD_TENANCY_BILL_ID);
			this.getUIContext().remove(KEY_TENANCY_CONTRACT_TYPE);

			List list = new ArrayList();
			for (int i = 0; i < this.tblCustomer.getRowCount(); i++) {
				IRow row = this.tblCustomer.getRow(i);
				FDCCustomerInfo fdcCust = (FDCCustomerInfo) row.getCell("customer").getValue();
				if (fdcCust != null)
					list.add(fdcCust);
			}
			CommerceHelper.addCommerceTrackRecord(this, list, (UserInfo) this.f7TenancyAdviser.getValue(), TrackRecordEnum.TenancyApp, this.editData.getNumber(), this.editData.getId() == null ? null
					: this.editData.getId().toString());
		}
		//审批服务
//		this.setOprtState(OprtState.VIEW);
		actionSubmit.setEnabled(true);
		actionEdit.setEnabled(false);
		this.actionAudit.setVisible(true); 
		actionAudit.setEnabled(true);
		
		//add by yangfan
		initLiquidatedTable();
	}


	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		//by huanghefh
		reSetRentSetInfo(getTenRoomListFromView());
		updatePayListInfo();
		if(this.pkStartDate.getValue()!=null && this.pkEndDate.getValue()!=null)
		{
			isFreeConSet(this.chkIsFreeContract.isSelected());
		}
		
		initControlByOprtState();
		initControlByBillState();
		this.btnAddIncrease.setEnabled(false);
		this.btnRmIncrease.setEnabled(false);
		this.btnAddFree.setEnabled(false);
		this.btnRmFree.setEnabled(false);
		/**
		 * 在其查看状态下，点击修改，可以修改开始和结束时间
		 * @author liang_ren969
		 */
		if(FDCBillStateEnum.SUBMITTED.equals(this.editData.getState()) && this.oprtState.equals("EDIT")){
			this.pkStartDate.setEditable(true);
			this.pkStartDate.setEnabled(true);
			this.pkEndDate.setEnabled(true);
			this.pkEndDate.setEditable(true);
		}
		isFreeConSet(this.chkIsFreeContract.isSelected());
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		/**
		 * 重置租售项目
		 * @author liang_ren969
		 * @date 2010-6-29
		 */
//		this.oprtState =OprtState.ADDNEW;
		if(editData.getSellProject()!=null){
	    
			sellProjectInfo = this.editData.getSellProject();
	    }
		
		this.editData.setSellProject(sellProjectInfo);
		
		this.getUIContext().clear();
		
		super.actionAddNew_actionPerformed(e);
		//根据状态处理action状态
		initControlByOprtState();
		
		//add by yangfan
		initLiquidatedTable();
		
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("TenancyType");
		sels.add("tenancyState");
		sels.add("rentCountType");
		
		sels.add("isAutoToInteger");
		sels.add("toIntegerType");
		sels.add("digit");
		
		sels.add("isAutoToIntegerFee");
		sels.add("toIntegetTypeFee");
		sels.add("digitFee");
		sels.add("firstLeaseEndDate");
		
		sels.add("rentStartType");
		sels.add("startDateLimit");
		sels.add("daysPerYear");
		sels.add("moreRoomsType");
		sels.add("isFreeContract");
		sels.add("state");
		
		sels.add("fristRevDate");
		sels.add("orgUnit");
		sels.add("orgUnit.*");
		sels.add("secondRevDate");

		sels.add("increasedRents.*");
		sels.add("rentFrees.*");
		
		sels.add("tenancyRoomList.*");
		sels.add("tenancyRoomList.room.floor");
		sels.add("tenancyRoomList.room.isForPPM");
		sels.add("tenancyRoomList.room.number");
		sels.add("tenancyRoomList.room.name");

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

		sels.add("tenAttachResourceList.*");
		sels.add("tenAttachResourceList.attachResource.attachType");
		sels.add("tenAttachResourceList.attachResource.name");
		sels.add("tenAttachResourceList.attachResource.number");
		sels.add("tenAttachResourceList.attachResource.description");

		sels.add("tenAttachResourceList.attachResource.sellProject.name");
		sels.add("tenAttachResourceList.attachResource.subarea.name");
		sels.add("tenAttachResourceList.attachResource.building.name");
		sels.add("tenAttachResourceList.attachResPayList.*");
		sels.add("tenAttachResourceList.attachResPayList.currency.number");
		sels.add("tenAttachResourceList.attachResPayList.currency.name");

		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.name");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.number");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.moneyType");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.sysType");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.isEnabled");

		sels.add("tenAttachResourceList.dealAmounts.*");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.name");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.number");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.moneyType");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.sysType");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.isEnabled");

		sels.add("tenancyRoomList.equipments.*");
		sels.add("tenancyRoomList.equipments.dev.*");
		sels.add("tenancyRoomList.tenRoomCharges.*");
		sels.add("tenancyRoomList.tenRoomCharges.chargeStandard.*");
		sels.add("tenancyRoomList.tenRoomCharges.chargeItem.name");

		sels.add("tenCustomerList.*");
		sels.add("tenCustomerList.fdcCustomer.name");
		sels.add("tenCustomerList.fdcCustomer.number");
		sels.add("tenCustomerList.fdcCustomer.postalcode");
		sels.add("tenCustomerList.fdcCustomer.phone");
		sels.add("tenCustomerList.fdcCustomer.certificateName");
		sels.add("tenCustomerList.fdcCustomer.certificateNumber");
		sels.add("tenCustomerList.fdcCustomer.mailAddress");
		sels.add("tenCustomerList.fdcCustomer.description");
		
		sels.add("otherPayList.*");
		sels.add("otherPayList.moneyDefine.*");
		sels.add("otherPayList.currency.*");
		sels.add("otherPayList.otherBill.*");

		//add by yangfan
		sels.add("tenLiquidated.*");
		sels.add("tenLiquidated.moneyDefine.*");
		sels.add("tenLiquidated.liquidated.*");
		sels.add("tenLiquidated.liquidated.name");
		sels.add("tenLiquidated.liquidated.number");
		sels.add("rate");
		sels.add("isAccountLiquidated");
		sels.add("isMDLiquidated");
		sels.add("rateDate");
		sels.add("relief");
		sels.add("reliefDate");
		sels.add("standard");
		sels.add("standardDate");
		sels.add("bit");
		sels.add("occurred");
		return sels;
	}

	private KDTDefaultCellEditor createTxtCellEditor(int length, boolean editable) {
		return TenancyClientHelper.createTxtCellEditor(length, editable);
	}

	private KDTDefaultCellEditor createComboCellEditor(List enumList) {
		return TenancyClientHelper.createComboCellEditor(enumList);
	}
	
	private KDTDefaultCellEditor createComboCellEditorAndSelectOne(List enumList) {
		return TenancyClientHelper.createComboCellEditorAndSelectOne(enumList);
	}

	private ICellEditor createFormattedCellEditor() {
		return TenancyClientHelper.createFormattedCellEditor(true);
	}

	private ICellEditor createFormattedCellEditor(boolean editable) {
		return TenancyClientHelper.createFormattedCellEditor(editable);
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected void attachListeners() {
		this.addItemListener(this.comboChargeDateType);
		this.spinChargeOffsetDays.addChangeListener(spinChargeOffsetDaysChangeListener);
		this.spinLeaseTime.addChangeListener(spinLeaseTimeChangeListener);

		this.addDataChangeListener(this.pkStartDate);
		this.addDataChangeListener(this.pkEndDate);
		this.addDataChangeListener(this.prmtRentFreeBill);
	}

	protected void detachListeners() {
		this.removeItemListener(this.comboChargeDateType);
		this.spinChargeOffsetDays.removeChangeListener(spinChargeOffsetDaysChangeListener);
		this.spinLeaseTime.removeChangeListener(spinLeaseTimeChangeListener);

		// 注意：这里不能将监听器全部去掉,KDSpin的界面刷新通过其内部的一个监听器实现的
		// this.removeChangeListener(this.spinFreeDays);
		// this.removeChangeListener(this.spinChargeOffsetDays);
		// this.removeChangeListener(this.spinLeaseTime);

		// TODO 实践证明,这2个监听器没有被去掉,待跟踪
		this.removeDataChangeListener(this.pkStartDate);
		this.removeDataChangeListener(this.pkEndDate);
		this.removeDataChangeListener(this.prmtRentFreeBill);
	}

	protected void addActionListener(AbstractButton com) {
		EventListener[] listeners = (EventListener[]) listenersMap.get(com);
		if (listeners != null && listeners.length > 0) {
			for (int i = 0; i < listeners.length; i++) {
				com.addActionListener((ActionListener) listeners[i]);
			}
		}
	}

	protected void addItemListener(JComboBox com) {
		EventListener[] listeners = (EventListener[]) listenersMap.get(com);
		if (listeners != null && listeners.length > 0) {
			for (int i = 0; i < listeners.length; i++) {
				com.addItemListener((ItemListener) listeners[i]);
			}
		}
	}

	protected void addChangeListener(JSpinner com) {
		EventListener[] listeners = (EventListener[]) listenersMap.get(com);
		if (listeners != null && listeners.length > 0) {
			for (int i = 0; i < listeners.length; i++) {
				com.addChangeListener((ChangeListener) listeners[i]);
			}
		}
	}

	protected void removeActionListener(AbstractButton com) {
		EventListener[] listeners = com.getListeners(ActionListener.class);
		if (listeners == null) {
			return;
		}
		for (int i = 0; i < listeners.length; i++) {
			com.removeActionListener((ActionListener) listeners[i]);
		}
		if (listeners.length > 0) {
			listenersMap.put(com, listeners);
		}
	}

	protected void removeItemListener(JComboBox com) {
		EventListener[] listeners = com.getListeners(ItemListener.class);
		if (listeners == null) {
			return;
		}
		for (int i = 0; i < listeners.length; i++) {
			com.removeItemListener((ItemListener) listeners[i]);
		}
		if (listeners.length > 0) {
			listenersMap.put(com, listeners);
		}
	}

	protected void removeChangeListener(JSpinner com) {
		EventListener[] listeners = com.getListeners(ChangeListener.class);
		if (listeners == null) {
			return;
		}
		for (int i = 0; i < listeners.length; i++) {
			com.removeChangeListener((ChangeListener) listeners[i]);
		}
		if (listeners.length > 0) {
			listenersMap.put(com, listeners);
		}
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	private MoneyDefineInfo getRentMoneyDefine() throws BOSException {
		if(rentMoneyName == null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.RentAmount));
			filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.TenancySys));
			view.setFilter(filter);
			MoneyDefineCollection moneyNames = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
			if (!moneyNames.isEmpty()) {
				rentMoneyName = moneyNames.get(0);
			}
		}
		if (rentMoneyName == null) {
			MsgBox.showInfo(this, "请先定义租金类型的款项类型！");
			abort();
		}
		return rentMoneyName;
	}

	private void setIsbtnPayList(boolean isFreeContract)
	{
		this.btnInsert.setEnabled(isFreeContract);
		this.btnAddPayList.setEnabled(isFreeContract);
		this.btnDelPayList.setEnabled(isFreeContract);
		TenancyContractTypeEnum currentTenancyType = getCurrentTenancyType();
		if(!TenancyContractTypeEnum.ChangeName.equals(currentTenancyType)){
			this.actionAddRoom.setEnabled(!isFreeContract);
			this.actionRemoveRoom.setEnabled(!isFreeContract);
		}
		

		for(int i=0;i<tblIncrease.getRowCount();i++)
		{
			IRow row = tblIncrease.getRow(i);
			row.getStyleAttributes().setLocked(isFreeContract);
		}
		
		for(int i=0;i<tblFree.getRowCount();i++)
		{
			IRow row = tblFree.getRow(i);
			row.getStyleAttributes().setLocked(isFreeContract);
		}
	}

	private void setToIntegerVisable(boolean isAutoToInteger) {
		this.contToIntegerType.setVisible(isAutoToInteger);
		this.contDigit.setVisible(isAutoToInteger);
	}
	private void setToIntegerFeeVisable(boolean isAutoToInteger) {
		this.contToIntegerType2.setVisible(isAutoToInteger);
		this.contDigit2.setVisible(isAutoToInteger);
	}

	protected void comboRentCountType_itemStateChanged(ItemEvent e) throws Exception {
		setVisibleAboutRentCountType((RentCountTypeEnum) this.comboRentCountType.getSelectedItem());
		updatePayListInfo();
	}

	protected void txtDaysPerYear_dataChanged(DataChangeEvent e)
	throws Exception {
		updatePayListInfo();
	}

	private void setVisibleAboutRentCountType(RentCountTypeEnum rentCountType){
		if(RentCountTypeEnum.ActDateCount.equals(rentCountType)){
			this.contDaysPerYear.setVisible(false);
		}else{
			this.contDaysPerYear.setVisible(true);
		}
	}

	protected void comboRentStartType_itemStateChanged(ItemEvent e) throws Exception {
		setVisibleAboutRentStartType((RentStartTypeEnum) this.comboRentStartType.getSelectedItem());
		updatePayListInfo();
	}

	private void setVisibleAboutRentStartType(RentStartTypeEnum rentStartType) {
		if(RentStartTypeEnum.SettledStartDate.equals(rentStartType)){
			this.contStartDateLimit.setVisible(false);
			this.pkStartDate.setEnabled(true);
			this.pkStartDate.setValue(new Date());
			this.pkEndDate.setEnabled(true);
			this.pkStartDate.setRequired(true);
			this.pkEndDate.setRequired(true);
			this.pkEndDate.setValue(new Date());
			this.txtLeaseCount.setEnabled(false);
//			this.btnAddIncrease.setEnabled(true);
//			this.btnRmIncrease.setEnabled(true);
			this.btnAddFree.setEnabled(true);
			this.btnRmFree.setEnabled(true);
			this.comboFirstLeaseType.setEnabled(true);
		}else{
			this.btnAddIncrease.setEnabled(false);
			this.btnRmIncrease.setEnabled(false);
			this.btnAddFree.setEnabled(false);
			this.btnRmFree.setEnabled(false);
			this.comboFirstLeaseType.setSelectedItem(FirstLeaseTypeEnum.WholeFirstLease);
			this.comboFirstLeaseType.setEnabled(false);
			this.contStartDateLimit.setVisible(true);
			this.txtLeaseCount.setEnabled(true);
			if("ADDNEW".equals(this.getOprtState()) || "EDIT".equals(this.getOprtState()))
			{
				this.btnAddIncrease.setEnabled(false);
				this.btnRmIncrease.setEnabled(false);
				this.btnAddFree.setEnabled(false);
				this.btnRmFree.setEnabled(false);
				this.comboFirstLeaseType.setSelectedItem(FirstLeaseTypeEnum.WholeFirstLease);
				this.comboFirstLeaseType.setEnabled(false);
				this.pkStartDate.setEnabled(false);
				this.pkStartDate.setValue(null);
				this.pkEndDate.setEnabled(false);
				this.pkEndDate.setValue(null);	
				this.pkStartDate.setRequired(false);
				this.pkEndDate.setRequired(false);
			}		
		}
	}

	protected void comboMoreRoomsType_itemStateChanged(ItemEvent e)
	throws Exception {
		reSetRentSetInfo(getTenRoomListFromView(), getTenAttachResListFromView());
	}

	protected void comboToIntegerType_itemStateChanged(ItemEvent e) throws Exception {
		updatePayListInfo();
	}
	protected void comboToIntegerTypeFee_itemStateChanged(ItemEvent e)
			throws Exception {
		updatePayListInfo();
	}

	protected void comboDigit_itemStateChanged(ItemEvent e) throws Exception {
		updatePayListInfo();
	}
	protected void comboDigitFee_itemStateChanged(ItemEvent e) throws Exception {
		updatePayListInfo();
	}

	protected void chkIsAutoToInteger_actionPerformed(ActionEvent e) throws Exception {
		setToIntegerVisable(this.chkIsAutoToInteger.isSelected());
		updatePayListInfo();
	}
	protected void chkIsAutoToIntegerForFee_actionPerformed(ActionEvent e)
			throws Exception {
		setToIntegerFeeVisable(this.chkIsAutoToIntegerForFee.isSelected());
		updatePayListInfo();
	}

	private void setOtherFreeContract(boolean isFreeContract)
	{
		this.contLeaseTime.setEnabled(isFreeContract);
		this.contFirstLeaseType.setEnabled(isFreeContract);
		this.contFirstLeaseEndDate.setEnabled(isFreeContract);
		this.contChargeDateType.setEnabled(isFreeContract);
		this.contChargeOffsetDays.setEnabled(isFreeContract);
		this.contRentStartType.setEnabled(isFreeContract);
		this.contStartDateLimit.setEnabled(isFreeContract);
//		this.btnAddIncrease.setEnabled(isFreeContract);
//		this.btnRmIncrease.setEnabled(isFreeContract);
		this.btnAddFree.setEnabled(isFreeContract);
		this.btnRmFree.setEnabled(isFreeContract);
		this.contRentCountType.setEnabled(isFreeContract);
		this.contDaysPerYear.setEnabled(isFreeContract);
		this.contMoreRoomsType.setEnabled(isFreeContract);
		this.chkIsAutoToInteger.setEnabled(isFreeContract);
		this.contToIntegerType.setEnabled(isFreeContract);
		this.contDigit.setEnabled(isFreeContract);
		//添加租金设置 周期期费用
		this.chkIsAutoToIntegerForFee.setEnabled(isFreeContract);
		this.contToIntegerType2.setEnabled(isFreeContract);
		this.contDigit2.setEnabled(isFreeContract);
		
	}
	
	private boolean isMoney(String colKey,MoneyDefineCollection monDefineColl)
	{
		boolean boo = false;
		for(int i=0;i<monDefineColl.size();i++)
		{
			MoneyDefineInfo money = monDefineColl.get(i);
			if(money.getNumber().equals(colKey))
			{
				boo = true;
				break;
			}
		}
		return boo;
	}

	//自由式合同控制付款明细行是否能编辑
	protected void isFreeConSet(boolean isFreeContract) throws BOSException
	{		
		if(isLoad)return;
		if(isFreeContract)
		{
			if(this.tblRoom.getRowCount()==0)
			{
				MsgBox.showInfo("请先选择租赁房间!");
				this.chkIsFreeContract.setSelected(false);
				this.abort();
			}
			if(this.pkStartDate.getValue()==null || this.pkEndDate.getValue()==null)
			{
				MsgBox.showInfo("请先确定开始、结束日期");
				this.chkIsFreeContract.setSelected(false);
				this.abort();
			}
			for(int i=0;i<this.tblRentSet.getRowCount();i++)
			{
				IRow row = tblRentSet.getRow(i);
				for (int j = 0; j < this.tblRentSet.getColumnCount(); j++) {
					IColumn column = this.tblRentSet.getColumn(j);					
					String colKey = column.getKey();
					if(!isMoney(colKey,monDefineColl) && !C_RENT_FIRST_RENT.equals(colKey))
					{
						row.getCell(colKey).getStyleAttributes().setLocked(isFreeContract);
					}	
				}
			}
			this.tblPayList.setEditable(true);
			for(int i=0;i<this.tblPayList.getRowCount();i++)
			{
				IRow row = tblPayList.getRow(i);
				for(int j=0;j<this.tblPayList.getColumnCount();j++)
				{
					IColumn column = this.tblPayList.getColumn(j);
					String colKey = column.getKey();
					if(C_PAYS_MONEY_DEFINE.equals(colKey))
					{
						String str = "";
						Object ob = row.getCell(colKey).getValue();
						if(ob instanceof String)
						{
							str = (String)ob;
						}else if(ob instanceof StringBuffer)
						{
							str = ((StringBuffer)ob).toString();
						}else if(ob instanceof MoneyDefineInfo)
						{
							str =  ((MoneyDefineInfo)ob).getName();
						}
						int a = ((Integer)row.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
						//如果是汇总行锁定应付金额不让编辑，由明细行的应付金额汇总
						if(str.split("；").length>1)
						{
							row.getCell(C_PAYS_START_DATE).getStyleAttributes().setLocked(false);
							row.getCell(C_PAYS_END_DATE).getStyleAttributes().setLocked(false);
							row.getCell(C_PAYS_APP_PAY_DATE).getStyleAttributes().setLocked(false);
							for(int k=0;k<tblRoom.getRowCount();k++)
							{
								row.getCell(PREFIX_C_PAYS_ROOM + k + POSTFIX_C_PAYS_APP_AMOUNT).getStyleAttributes().setLocked(true);
							}

						}else if(str.equals("租金") && a!=1)//如果没有周期性费用的时候租金即是汇总行又是明细行所以都让编辑
						{
							row.getCell(C_PAYS_START_DATE).getStyleAttributes().setLocked(false);
							row.getCell(C_PAYS_END_DATE).getStyleAttributes().setLocked(false);
							row.getCell(C_PAYS_APP_PAY_DATE).getStyleAttributes().setLocked(false);
							for(int k=0;k<tblRoom.getRowCount();k++)
							{
								row.getCell(PREFIX_C_PAYS_ROOM + k + POSTFIX_C_PAYS_APP_AMOUNT).getStyleAttributes().setLocked(false);
							}
						}
						else
						{
							//明细行只能编辑应付金额，开始日期、结束日期和应付日期随汇总行变动
							row.getCell(C_PAYS_START_DATE).getStyleAttributes().setLocked(true);
							row.getCell(C_PAYS_END_DATE).getStyleAttributes().setLocked(true);
							row.getCell(C_PAYS_APP_PAY_DATE).getStyleAttributes().setLocked(true);
							for(int k=0;k<tblRoom.getRowCount();k++)
							{
								row.getCell(PREFIX_C_PAYS_ROOM + k + POSTFIX_C_PAYS_APP_AMOUNT).getStyleAttributes().setLocked(false);
							}						
						}
					}
				}
			}
		}else
		{
			rentSetStyle();
			initPayListTable();
		}
		setIsbtnPayList(isFreeContract);
		setOtherFreeContract(!this.chkIsFreeContract.isSelected());
	}

	private void rentSetStyle()
	{
		for(int i=0;i<tblRentSet.getRowCount();i++)
		{
			IRow row = tblRentSet.getRow(i);
			for(int j=0;j<this.tblRentSet.getColumnCount();j++)
			{
				IColumn column = this.tblRentSet.getColumn(j);
				String colKey = column.getKey();
				if(C_RENT_RENT_TYPE.equals(colKey)){
					row.getCell(C_RENT_RENT_TYPE).getStyleAttributes().setLocked(false);
				}else if(C_RENT_TENANCY_MODEL.equals(colKey))
				{
					row.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(false);
					TenancyModeEnum tenModel = (TenancyModeEnum) row.getCell(C_RENT_TENANCY_MODEL).getValue();
					if(TenancyModeEnum.TenancyRentModel.equals(tenModel))
					{
						for(int m=0;m<tblRentSet.getColumnCount();m++)
						{
							String tColKey = this.tblRentSet.getColumnKey(m);
							ICell cell = row.getCell(tColKey);
							if(tColKey.startsWith(PREFIX_C_RENT_RENT_PRICE)){
								cell.getStyleAttributes().setLocked(true);
							}else if(tColKey.startsWith(PREFIX_C_RENT_RENT)){
								cell.getStyleAttributes().setLocked(false);
							}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY)){
								cell.getStyleAttributes().setLocked(false);
							}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)){
								cell.getStyleAttributes().setLocked(true);
							}
						}
					}else{
						for(int m=0; m<this.tblRentSet.getColumnCount(); m++){
							String tColKey = this.tblRentSet.getColumnKey(m);
							ICell cell = row.getCell(tColKey);
							if(tColKey.startsWith(PREFIX_C_RENT_RENT_PRICE)){
								cell.getStyleAttributes().setLocked(false);
							}else if(tColKey.startsWith(PREFIX_C_RENT_RENT)){
								cell.getStyleAttributes().setLocked(true);
							}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY)){
								cell.getStyleAttributes().setLocked(true);
							}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)){
								cell.getStyleAttributes().setLocked(false);
							}
						}
					}
				}
			}

		}
	}
	
	protected void btnAddOtherPaylist_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnAddOtherPaylist_actionPerformed(e);
		TenBillOtherPayInfo tenOtherInfo = new TenBillOtherPayInfo();
		addOtherPayListEntryRow(tenOtherInfo);
	}
	
	private void addOtherPayListEntryRow(TenBillOtherPayInfo tenOtherInfo) {
		if (tenOtherInfo == null)
			return;
		IRow row = this.tblOtherPayList.addRow();
		row.setUserObject(tenOtherInfo);
		row.getCell("leaseSeq").setValue(tenOtherInfo.getLeaseSeq()==0?null:tenOtherInfo.getLeaseSeq());
		row.getCell("moneyTypeName").setValue(tenOtherInfo.getMoneyDefine());
		row.getCell("appDate").setValue(tenOtherInfo.getAppDate());
		row.getCell("startDate").setValue(tenOtherInfo.getStartDate());
		row.getCell("endDate").setValue(tenOtherInfo.getEndDate());
		row.getCell("currency").setValue(tenOtherInfo.getCurrency());
		row.getCell("appAmount").setValue(tenOtherInfo.getAppAmount());
		row.getCell("actRevAmount").setValue(tenOtherInfo.getAllRemainAmount());
		row.getCell("actRevDate").setValue(tenOtherInfo.getActRevDate());
		row.getCell("description").setValue(tenOtherInfo.getDescription());
		
		if (tenOtherInfo.getActRevAmount() != null && tenOtherInfo.getActRevAmount().compareTo(FDCHelper.ZERO) > 0) {
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_SUBTOTAL_BG_COLOR);
		}
		if(tenOtherInfo.getOtherBill()!=null){
			row.getStyleAttributes().setLocked(true);
		}
	}
	
	protected void btnDelOtherPaylist_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnDelOtherPaylist_actionPerformed(e);
		int activeRowIndex = this.tblOtherPayList.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblOtherPayList.getRowCount();
		}
		IRow row = this.tblOtherPayList.getRow(activeRowIndex);
		if (row == null) {
			return;
		}
		TenBillOtherPayInfo entry = (TenBillOtherPayInfo) row.getUserObject();
		BigDecimal actAmount = entry.getActRevAmount();
		if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) != 0) {
			MsgBox.showInfo("该分录已经收款,不能删除!");
			return;
		}
		if(entry.getOtherBill()!=null){
			MsgBox.showInfo("该分录由其他合同生成,不能删除!");
			return;
		}
		this.tblOtherPayList.removeRow(activeRowIndex);
	}
	
	protected void chkIsFreeContract_actionPerformed(ActionEvent e)
	throws Exception {
		super.chkIsFreeContract_actionPerformed(e);
		updatePayListInfo(this.chkIsFreeContract.isSelected());
		isFreeConSet(this.chkIsFreeContract.isSelected());
	}

	//插入付款明细行
	protected void btnInsert_actionPerformed(ActionEvent e) throws Exception {
		super.btnInsert_actionPerformed(e);
		IRow selectRow = KDTableUtil.getSelectedRow(tblPayList);
		if(selectRow==null) return;
		ITenancyPayListInfo onePay = (ITenancyPayListInfo) selectRow.getUserObject();	
		if(onePay==null)
		{
			selectRow = tblPayList.getRow(tblPayList.getSelectManager().getActiveRowIndex()+1);
			onePay = (ITenancyPayListInfo) selectRow.getUserObject();
		}		
		if(onePay.getLeaseSeq()==1)
		{
			MsgBox.showInfo("第一租期不能插入");
			this.abort();
		}
		//取出选中租期共有几条付款明细
		int payCount = 0;
		Map tmpMap = new HashMap();
		List list = new ArrayList();
		for(int i=0;i<tblPayList.getRowCount();i++)
		{
			IRow row4 = tblPayList.getRow(i);
			if(row4.getUserObject()!=null)
			{
				ITenancyPayListInfo pay = (ITenancyPayListInfo) row4.getUserObject();
				if(pay.getLeaseSeq()==onePay.getLeaseSeq())
				{
					list.add(pay);
					tmpMap.put(new Integer(onePay.getLeaseSeq()), list);
				}				
			}
		}
		
		int rowCount = 0;
		List pays = (List) tmpMap.get(new Integer(onePay.getLeaseSeq()));		
		payCount = pays.size();
		if(payCount>1)
		{
			//一租期内付款明细有多行的时候
			//下面判断从哪一行开始插入的时候不会计算汇总行，所以在这加了
			rowCount = onePay.getLeaseSeq()-1;
		}else
		{
			//一租期内只有一条付款明细
			rowCount = tblPayList.getSelectManager().getActiveRowIndex();
		}
		//如果只有一种款项的话就直接增加一条记录
		if(pays.size() == 1){
			ITenancyPayListInfo tPay = (ITenancyPayListInfo) pays.get(0);
			if(tPay.getLeaseSeq()==onePay.getLeaseSeq())
			{
				IRow row = this.tblPayList.addRow(selectRow.getRowIndex());
				row.setTreeLevel(0);
				ITenancyPayListInfo roomPay = new TenancyRoomPayListEntryInfo();
				toSale(tPay, roomPay);
				roomPay.setLeaseSeq(onePay.getLeaseSeq());	
				roomPay.setSeq(tPay.getSeq());
				row.setUserObject(tPay);

				row.getCell(C_PAYS_MONEY_DEFINE).setValue(tPay.getMoneyDefine());
				row.getCell(C_PAYS_LEASE_SEQ).setValue(new Integer(tPay.getLeaseSeq()));
				row.getCell(C_PAYS_START_DATE).setValue(tPay.getStartDate());
				row.getCell(C_PAYS_START_DATE).getStyleAttributes().setLocked(false);
				row.getCell(C_PAYS_END_DATE).setValue(tPay.getEndDate());
				row.getCell(C_PAYS_END_DATE).getStyleAttributes().setLocked(false);
				row.getCell(C_PAYS_APP_PAY_DATE).setValue(tPay.getAppPayDate());
				for(int m=0;m<tblRoom.getRowCount();m++)
				{
					//给应付金额列存上该明细对应的房间以便在保存的时候取值
					TenancyRoomPayListEntryInfo ten = (TenancyRoomPayListEntryInfo)selectRow.getCell(PREFIX_C_PAYS_ROOM + m + POSTFIX_C_PAYS_APP_AMOUNT).getUserObject();
					row.getCell(PREFIX_C_PAYS_ROOM + m + POSTFIX_C_PAYS_APP_AMOUNT).setUserObject(ten);
				}
			}				
		}else{	
			//如果有多种还需要根据付款明细生成汇总行,根据租期序号判断从哪一行开始插入记录
			for(int m=0;m<tblPayList.getRowCount();m++)
			{
				IRow row5 = tblPayList.getRow(m);
				if(row5.getUserObject()!=null)
				{
					ITenancyPayListInfo tPay2 = (ITenancyPayListInfo) row5.getUserObject();
					if(tPay2.getLeaseSeq()<onePay.getLeaseSeq())
					{
						rowCount++;
					}
				}

			}
			StringBuffer moneyDes = new StringBuffer();
			//汇总行只能生成一条，而且位置又要在第一条所以就把第一条的位置记录下来，后面给他赋值
			int aa = rowCount;
			boolean boo = false;
			for(int i=0; i<pays.size(); i++){					
				ITenancyPayListInfo tPay = (ITenancyPayListInfo) pays.get(i);
				if(tPay.getLeaseSeq()==onePay.getLeaseSeq())
				{		
					IRow unionRow = null;
					if(!boo)
					{
						unionRow = this.tblPayList.addRow(aa);
						unionRow.setTreeLevel(0);
						boo = true;
					}																									
					IRow entryRow = this.tblPayList.addRow(rowCount+1);						
					entryRow.setTreeLevel(1);
					ITenancyPayListInfo roomPay = new TenancyRoomPayListEntryInfo();
					toSale(tPay, roomPay);
					roomPay.setLeaseSeq(onePay.getLeaseSeq());	
					roomPay.setSeq(tPay.getSeq());
					entryRow.setUserObject(tPay);
					rowCount++;

					entryRow.getCell(C_PAYS_MONEY_DEFINE).setValue("  " + tPay.getMoneyDefine());
					entryRow.getCell(C_PAYS_LEASE_SEQ).setValue(new Integer(onePay.getLeaseSeq()));
					entryRow.getCell(C_PAYS_START_DATE).setValue(tPay.getStartDate());
					entryRow.getCell(C_PAYS_END_DATE).setValue(tPay.getEndDate());
					entryRow.getCell(C_PAYS_APP_PAY_DATE).setValue(tPay.getAppPayDate());
					entryRow.getCell(C_PAYS_APP_PAY_DATE).getStyleAttributes().setLocked(true);
					for(int m=0;m<tblRoom.getRowCount();m++)
					{
						//给应付金额列存上该明细对应的房间以便在保存的时候取值
						TenancyRoomPayListEntryInfo ten = (TenancyRoomPayListEntryInfo)selectRow.getCell(PREFIX_C_PAYS_ROOM + m + POSTFIX_C_PAYS_APP_AMOUNT).getUserObject();
						entryRow.getCell(PREFIX_C_PAYS_ROOM + m + POSTFIX_C_PAYS_APP_AMOUNT).setUserObject(ten);
					}
					if(i != 0){
						moneyDes.append("；");
					}
					moneyDes.append(tPay.getMoneyDefine());
					//如果有多条的话就给汇总行赋值
					if(i!=0)
					{
						unionRow = tblPayList.getRow(aa);
						unionRow.getCell(C_PAYS_MONEY_DEFINE).setValue(moneyDes.toString());
						unionRow.getCell(C_PAYS_LEASE_SEQ).setValue(new Integer(onePay.getLeaseSeq()));	
						unionRow.getCell(C_PAYS_START_DATE).setValue(tPay.getStartDate());
						unionRow.getCell(C_PAYS_START_DATE).getStyleAttributes().setLocked(false);
						unionRow.getCell(C_PAYS_END_DATE).setValue(tPay.getEndDate());
						unionRow.getCell(C_PAYS_END_DATE).getStyleAttributes().setLocked(false);
						unionRow.getCell(C_PAYS_APP_PAY_DATE).setValue(tPay.getAppPayDate());
						for(int k=0;k<tblRoom.getRowCount();k++)
						{
							unionRow.getCell(PREFIX_C_PAYS_ROOM + k + POSTFIX_C_PAYS_APP_AMOUNT).getStyleAttributes().setLocked(true);
						}
					}																			
				}						
			}				
		}
		//		}
		//插入了记录后，给他后面的记录的租期序号都加1
		for(int i=rowCount+1;i<tblPayList.getRowCount();i++)
		{		
			IRow row = tblPayList.getRow(i);
			int inte = ((Integer)row.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
			if(row.getUserObject()!=null)
			{
				ITenancyPayListInfo object = (ITenancyPayListInfo) row.getUserObject();
				ITenancyPayListInfo roomPay = new TenancyRoomPayListEntryInfo();
				toSale(object, roomPay);
				roomPay.setLeaseSeq(object.getLeaseSeq()+1);
				roomPay.setSeq(object.getSeq()+payCount);				
				row.setUserObject(roomPay);
			}
			row.getCell(C_PAYS_LEASE_SEQ).setValue(new Integer(inte+1));			
		}
		this.tblPayList.getMergeManager().mergeBlock(0, 0, this.tblPayList.getRowCount() - 1, 0, KDTMergeManager.FREE_ROW_MERGE);
	}

	//增加付款明细行
	protected void btnAddPayList_actionPerformed(ActionEvent e)
	throws Exception {
		super.btnAddPayList_actionPerformed(e);
		//取出最后租期的付款明细
		List payList = new ArrayList();
		IRow row = this.tblPayList.getRow(tblPayList.getRowCount()-1);
		int leaseSeq = ((Integer)row.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
		ITenancyPayListInfo payInfo = (ITenancyPayListInfo)row.getUserObject();
		if(leaseSeq==1)
		{
			for(int i=0;i<tblPayList.getRowCount();i++)
			{
				IRow row2 = tblPayList.getRow(i);
				if(row2.getUserObject()!=null)
				{
					ITenancyPayListInfo payInfo2 = (ITenancyPayListInfo)row2.getUserObject();
					if(!payInfo2.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.DepositAmount))
					{
						payList.add(payInfo2);
					}
				}
			}
		}else
		{
			for(int i=0;i<tblPayList.getRowCount();i++)
			{
				IRow row2 = tblPayList.getRow(i);
				int seq = ((Integer)row2.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
				if(leaseSeq==seq)
				{
					if(row2.getUserObject()!=null)
					{
						ITenancyPayListInfo payInfo2 = (ITenancyPayListInfo)row2.getUserObject();
						payList.add(payInfo2);
					}
				}
			}
		}	

		if(payList.size()==1)
		{
			IRow entryRow = this.tblPayList.addRow(tblPayList.getRowCount());
			entryRow.setTreeLevel(1);
			ITenancyPayListInfo roomPay = new TenancyRoomPayListEntryInfo();
			toSale(payInfo, roomPay);
			roomPay.setLeaseSeq(leaseSeq+1);
			roomPay.setSeq(payInfo.getSeq()+1);
			entryRow.setUserObject(roomPay);

			entryRow.getCell(C_PAYS_MONEY_DEFINE).setValue(payInfo.getMoneyDefine());
			entryRow.getCell(C_PAYS_LEASE_SEQ).setValue(new Integer(leaseSeq+1));
			entryRow.getCell(C_PAYS_START_DATE).setValue(payInfo.getStartDate());
			entryRow.getCell(C_PAYS_START_DATE).getStyleAttributes().setLocked(false);
			entryRow.getCell(C_PAYS_END_DATE).setValue(payInfo.getEndDate());
			entryRow.getCell(C_PAYS_END_DATE).getStyleAttributes().setLocked(false);
			entryRow.getCell(C_PAYS_APP_PAY_DATE).setValue(payInfo.getAppPayDate());
			for(int m=0;m<tblRoom.getRowCount();m++)
			{
				//给应付金额列存上该明细对应的房间以便在保存的时候取值
				TenancyRoomPayListEntryInfo ten = (TenancyRoomPayListEntryInfo)row.getCell(PREFIX_C_PAYS_ROOM + m + POSTFIX_C_PAYS_APP_AMOUNT).getUserObject();
				entryRow.getCell(PREFIX_C_PAYS_ROOM + m + POSTFIX_C_PAYS_APP_AMOUNT).setUserObject(ten);
			}
		}else
		{
			int rowCount =tblPayList.getRowCount();
			int aa = rowCount;
			boolean boo = false;
			StringBuffer moneyDes = new StringBuffer();
			for(int i=0;i<payList.size();i++)
			{
				ITenancyPayListInfo payListInfo = (ITenancyPayListInfo)payList.get(i);
				IRow unionRow = null;
				if(!boo)
				{
					unionRow = this.tblPayList.addRow(aa);
					unionRow.setTreeLevel(0);
					boo = true;
				}
				IRow entryRow = this.tblPayList.addRow(rowCount+1);						
				entryRow.setTreeLevel(1);
				ITenancyPayListInfo roomPay = new TenancyRoomPayListEntryInfo();
				toSale(payListInfo, roomPay);
				roomPay.setLeaseSeq(leaseSeq+1);
				roomPay.setSeq(payListInfo.getSeq()+payList.size());
				entryRow.setUserObject(roomPay);
				rowCount++;

				entryRow.getCell(C_PAYS_MONEY_DEFINE).setValue("  " +payListInfo.getMoneyDefine());
				entryRow.getCell(C_PAYS_LEASE_SEQ).setValue(new Integer(leaseSeq+1));
				entryRow.getCell(C_PAYS_START_DATE).setValue(payListInfo.getStartDate());
				entryRow.getCell(C_PAYS_END_DATE).setValue(payListInfo.getEndDate());
				entryRow.getCell(C_PAYS_APP_PAY_DATE).setValue(payListInfo.getAppPayDate());
				entryRow.getCell(C_PAYS_APP_PAY_DATE).getStyleAttributes().setLocked(true);
				for(int m=0;m<tblRoom.getRowCount();m++)
				{
					//给应付金额列存上该明细对应的房间以便在保存的时候取值
					TenancyRoomPayListEntryInfo ten = (TenancyRoomPayListEntryInfo)row.getCell(PREFIX_C_PAYS_ROOM + m + POSTFIX_C_PAYS_APP_AMOUNT).getUserObject();
					entryRow.getCell(PREFIX_C_PAYS_ROOM + m + POSTFIX_C_PAYS_APP_AMOUNT).setUserObject(ten);
				}
				if(i != 0){
					moneyDes.append("；");
				}
				moneyDes.append(payListInfo.getMoneyDefine());
				//如果有多条的话就给汇总行赋值
				if(i!=0)
				{
					unionRow = tblPayList.getRow(aa);
					unionRow.getCell(C_PAYS_MONEY_DEFINE).setValue(moneyDes.toString());
					unionRow.getCell(C_PAYS_LEASE_SEQ).setValue(new Integer(leaseSeq+1));	
					unionRow.getCell(C_PAYS_START_DATE).setValue(payListInfo.getStartDate());
					unionRow.getCell(C_PAYS_START_DATE).getStyleAttributes().setLocked(false);
					unionRow.getCell(C_PAYS_END_DATE).setValue(payListInfo.getEndDate());
					unionRow.getCell(C_PAYS_END_DATE).getStyleAttributes().setLocked(false);
					unionRow.getCell(C_PAYS_APP_PAY_DATE).setValue(payListInfo.getAppPayDate());
					for(int k=0;k<tblRoom.getRowCount();k++)
					{								
						unionRow.getCell(PREFIX_C_PAYS_ROOM + k + POSTFIX_C_PAYS_APP_AMOUNT).getStyleAttributes().setLocked(true);					
					}
				}
			}
			this.tblPayList.getMergeManager().mergeBlock(0, 0, this.tblPayList.getRowCount() - 1, 0, KDTMergeManager.FREE_ROW_MERGE);
		}

	}

	//删除付款明细行
	protected void btnDelPayList_actionPerformed(ActionEvent e)
	throws Exception {
		super.btnDelPayList_actionPerformed(e);

		IRow selectRow = KDTableUtil.getSelectedRow(tblPayList);
		if(selectRow==null) return;
		ITenancyPayListInfo onePay = (ITenancyPayListInfo) selectRow.getUserObject();
		if(onePay==null)
		{
			selectRow = tblPayList.getRow(tblPayList.getSelectManager().getActiveRowIndex()+1);
			onePay = (ITenancyPayListInfo) selectRow.getUserObject();
			BigDecimal actAmount = onePay.getActAmount();
			if(actAmount!=null && actAmount.compareTo(FDCHelper.ZERO) != 0)
			{
				MsgBox.showInfo("该分录已经收款,不能删除!");
				return;
			}
		}		
		if(onePay.getLeaseSeq()==1)
		{
			MsgBox.showInfo("第一租期不能删除");
			this.abort();
		}

		//		if(((Integer)tblPayList.getRow(tblPayList.getRowCount()-1).getCell(C_PAYS_LEASE_SEQ).getValue()).intValue()==2)
		//		{
		//			MsgBox.showInfo("付款明细行只有2行，不允许删除");
		//			this.abort();
		//		}

		int leaseSeq = onePay.getLeaseSeq();
		int rowCount = 0;
		for(int i=0;i<tblPayList.getRowCount();i++)
		{
			IRow row = tblPayList.getRow(i);
			int seq = ((Integer)row.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
			if(seq<leaseSeq)
			{
				rowCount++;
			}else if(leaseSeq==seq)
			{
				this.tblPayList.removeRow(i);
				--i;				
			}
		}

		//确定1个租期内有几条付款明细
		int seqCount = 0;
		for(int i=tblPayList.getRowCount()-1;i>0;i--)
		{
			IRow row = tblPayList.getRow(i);
			if(row.getUserObject()==null)
			{
				seqCount = tblPayList.getRowCount()-1-i;
				break;
			}else if(row.getUserObject()!=null && i==1)
			{
				seqCount = 1;
			}
		}

		for(int i=rowCount;i<tblPayList.getRowCount();i++)
		{
			IRow row = tblPayList.getRow(i);
			int inte = ((Integer)row.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
			if(row.getUserObject()!=null)
			{
				ITenancyPayListInfo object = (ITenancyPayListInfo) row.getUserObject();
				ITenancyPayListInfo roomPay = new TenancyRoomPayListEntryInfo();
				toSale(object, roomPay);
				roomPay.setLeaseSeq(inte-1);
				roomPay.setSeq(object.getSeq()-seqCount);
				row.setUserObject(roomPay);
			}
			row.getCell(C_PAYS_LEASE_SEQ).setValue(new Integer(inte-1));	
		}
		this.tblPayList.getMergeManager().mergeBlock(0, 0, this.tblPayList.getRowCount() - 1, 0, KDTMergeManager.FREE_ROW_MERGE);
	}


	private ITenancyPayListInfo toSale(ITenancyPayListInfo object,ITenancyPayListInfo roomPay)
	{
		roomPay.setMoneyDefine(object.getMoneyDefine());
		roomPay.setStartDate(object.getStartDate());
		roomPay.setEndDate(object.getEndDate());
		roomPay.setAppPayDate(object.getAppPayDate());
		return roomPay;
	}

	protected void chkIsFreeContract_stateChanged(ChangeEvent e)
	throws Exception {				
	}

	private void initOtherPayList()
	{
		this.tblOtherPayList.checkParsed();
		this.tblOtherPayList.getStyleAttributes().setLocked(false);
		this.tblOtherPayList.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		this.tblOtherPayList.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

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
		this.tblOtherPayList.getColumn("moneyTypeName").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery", view));
		this.tblOtherPayList.getColumn("appDate").setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblOtherPayList.getColumn("appDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.tblOtherPayList.getColumn("appDate").setRequired(true);
		this.tblOtherPayList.getColumn("startDate").setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblOtherPayList.getColumn("startDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.tblOtherPayList.getColumn("endDate").setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblOtherPayList.getColumn("endDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.tblOtherPayList.getColumn("actRevDate").setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblOtherPayList.getColumn("actRevDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.tblOtherPayList.getColumn("actRevDate").getStyleAttributes()
		.setLocked(true);
		this.tblOtherPayList.getColumn("currency").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery", null));
		this.tblOtherPayList.getColumn("currency").getStyleAttributes().setLocked(true);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setPrecision(2);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblOtherPayList.getColumn("appAmount").setEditor(numberEditor);
		this.tblOtherPayList.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblOtherPayList.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblOtherPayList.getColumn("actRevAmount").setEditor(numberEditor);
		this.tblOtherPayList.getColumn("actRevAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblOtherPayList.getColumn("actRevAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblOtherPayList.getColumn("actRevAmount").getStyleAttributes().setLocked(true);

		this.tblOtherPayList.getColumn("appAmount").getStyleAttributes()
		.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
        this.tblOtherPayList.getColumn("moneyTypeName").getStyleAttributes()
		.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblOtherPayList.getColumn("description").setEditor(txtEditor);
		
		
		this.tblOtherPayList.getGroupManager().setGroup(true);
		
		this.tblOtherPayList.getColumn("leaseSeq").setGroup(true);
		this.tblOtherPayList.getColumn("leaseSeq").setMergeable(true);
		this.tblOtherPayList.getColumn("leaseSeq").getStyleAttributes().setLocked(true);
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
			"cantPrint"));
			return;
		}
		TenancyBillDataProvider data = new TenancyBillDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
	throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
			"cantPrint"));
			return;

		}
		TenancyBillDataProvider data = new TenancyBillDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	private void afterPressDeleteButton() {
		tblPayList.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
                                             /////或者这样，到这里的时候直接返回
					for (int i = 0; i < tblPayList.getSelectManager().size(); i++) {
						KDTSelectBlock block = tblPayList.getSelectManager()
								.get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block
								.getEndRow(); rowIndex++) {
							for (int colIndex = block.getBeginCol(); colIndex <= block
									.getEndCol(); colIndex++) {
								KDTEditEvent event = new KDTEditEvent(
										tblPayList, null, null, rowIndex,
										colIndex, true, 1);
								try {
									tblPayList_editStopped(event);
								} catch (Exception e1) {
									handleException(e1);
								}
							}
						}
					}
				}
			}
		});
	}

	protected String getTDFileName() {
		return "/bim/fdc/tenancy/TenancyBill";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
		"com.kingdee.eas.fdc.tenancy.app.TenancyBillPrintQuery");
	}
	
	/**
	 * 当实体里设置了编码规则可以用的时候，元数据生成的abstract类loadFields()里将会生成setAutoNumberByOrg(String orgType)方法来处理编码规则，
	 * 这样就与TenBillBase(也就是FDCBILL)里onLoad方法里的handleCodingRule() 方法冲突！也就是在新增的时候会调用两次编码规则的ICodingRuleManager.getNumber(),
	 * 造成了每次新增的时候都出现步长为：编码规则定义步长*2 (不考虑并发的情况)  
	 * 现在先屏蔽掉自动生成的编码规则处理方法！ xin_wang 2010.11.15
	 */
	protected void setAutoNumberByOrg(String orgType) {
//		super.setAutoNumberByOrg(orgType);
	}
	protected boolean isShowAttachmentAction() {
		return true;
	}
	
	protected void btnViewRoomInfo_actionPerformed(ActionEvent e)
			throws Exception {
		int activeRowIndex = this.tblRoom.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			MsgBox.showInfo("请选择一行房间！");
		}
		IRow row = tblRoom.getRow(activeRowIndex);
		if(row!=null){
			TenancyRoomEntryInfo tenancyRoom = (TenancyRoomEntryInfo)row.getUserObject();
			if(tenancyRoom.getRoom()!=null){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID",tenancyRoom.getRoom().getId().toString());
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.NEWTAB).create(RoomEditUI.class.getName(),
								uiContext, null, "VIEW");
				uiWindow.show();
			}
		}
	}
	protected void tblCustomer_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2
				&&tblCustomer.getColumn(e.getColIndex()).getKey().equals("customer")) {
			IRow row = tblCustomer.getRow(e.getRowIndex());
			if(row!=null){
				TenancyCustomerEntryInfo TenancyCustomer = (TenancyCustomerEntryInfo)row.getUserObject();
				if(TenancyCustomer.getFdcCustomer()!=null){
					UIContext uiContext = new UIContext(this);
					uiContext.put("ID",TenancyCustomer.getFdcCustomer().getId().toString());
					IUIWindow uiWindow = UIFactory.createUIFactory(
							UIFactoryName.NEWTAB).create(CustomerEditUI.class.getName(),uiContext, null, "VIEW");
					uiWindow.show();
				}
			}
		}
	}

	protected void btnViewCustInfo_actionPerformed(ActionEvent e)
			throws Exception {
		int activeRowIndex = this.tblCustomer.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			MsgBox.showInfo("请选择一行客户！");
		}
		IRow row = tblCustomer.getRow(activeRowIndex);
		if(row!=null){
			TenancyCustomerEntryInfo TenancyCustomer = (TenancyCustomerEntryInfo)row.getUserObject();
			if(TenancyCustomer.getFdcCustomer()!=null){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID",TenancyCustomer.getFdcCustomer().getId().toString());
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.NEWTAB).create(CustomerEditUI.class.getName(),
								uiContext, null, "VIEW");
				uiWindow.show();
			}
		}
	}
	
	protected void DPickFromMonth_dataChanged(DataChangeEvent e)
			throws Exception {
		updatePayListInfo();
	}
	//add by yangfan
	protected void chkIsMDLiquidated_actionPerformed(ActionEvent e)
			throws Exception {
		if(this.chkIsMDLiquidated.getSelected()==32){
			this.panLiquidated.setVisible(false);
			this.panMDLiquidated.setVisible(true);
		}else{
			this.panLiquidated.setVisible(true);
			this.panMDLiquidated.setVisible(false);
		}
		super.chkIsMDLiquidated_actionPerformed(e);
	}
	
	protected void txtRate_dataChanged(DataChangeEvent e) throws Exception {
		if(e.getNewValue()!=null&&((BigDecimal)e.getNewValue()).compareTo(new BigDecimal(100))==1){
			txtRate.setValue(e.getOldValue());
		}
		super.txtRate_dataChanged(e);
	}
	protected void prmtRentFreeBill_dataChanged(DataChangeEvent e)throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        RentFreeBillInfo info=(RentFreeBillInfo) this.prmtRentFreeBill.getValue();
        this.editData.getRentFrees().clear();
        if(info!=null){
        	RentFreeEntryCollection col=RentFreeEntryFactory.getRemoteInstance().getRentFreeEntryCollection("select * from where rentFreeBill.id='"+info.getId()+"' order by seq");
        	for(int i=0;i<col.size();i++){
        		this.editData.getRentFrees().add(col.get(i));
        	}
        }
        this.loadFreeRents(this.editData.getRentFrees());
		updatePayListInfo();
	}
	protected void cbMD_itemStateChanged(ItemEvent arg0) throws Exception {
		for(int i=0;i<this.tblPayList.getRowCount();i++){
			IRow row = this.tblPayList.getRow(i);
			ITenancyPayListInfo tPay = (ITenancyPayListInfo) row.getUserObject();
			if(this.cbMD.getSelectedItem().toString().equals("")){
				row.getStyleAttributes().setHided(false);
			}else{
				if(tPay!=null&&tPay.getMoneyDefine().getName().equals(this.cbMD.getSelectedItem().toString())){
					row.getStyleAttributes().setHided(false);
				}else{
					row.getStyleAttributes().setHided(true);
				}
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
				 if(!TenancyBillFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getId().toString()))){
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