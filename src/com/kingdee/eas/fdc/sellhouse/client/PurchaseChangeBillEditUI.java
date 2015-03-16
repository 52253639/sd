/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RevListInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.MoneyDefine;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioOldEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioOldEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListOldEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListOldEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentOldEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentOldEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class PurchaseChangeBillEditUI extends AbstractPurchaseChangeBillEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PurchaseChangeBillEditUI.class);
	private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	private boolean isFromSellControl = false;
	private String purchaseId = null;
	private String src = "LISTUI";
	RoomDisplaySetting setting = new RoomDisplaySetting();

	private AgioParam newAgioParam = new AgioParam();

	private boolean isEarnestInHouseAmount = true;
	
	private Number newAigio = null;

	/*
	 * output class constructor
	 */
	public PurchaseChangeBillEditUI() throws Exception {
		super();
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("oldPriceAccountType");
		sic.add("newPriceAccountType");
		sic.add("oldIsAutoToInteger");
		sic.add("newIsAutoToInteger");
		sic.add("oldToIntegerType");
		sic.add("newToIntegerType");
		sic.add("oldDigit");
		sic.add("newDigit");
		sic.add("newSpecialAgioType");
		sic.add("oldSpecialAgioType");
		sic.add("newDiscount");
		sic.add("reqDiscount");
		sic.add("oldIsBasePriceSell");
		sic.add("newIsBasePriceSell");
		sic.add("purchase.specialAgioType");
		sic.add("purchase.id");
		sic.add("purchase.isEarnestInHouseAmount");
		sic.add("purchase.room.*");
		sic.add("purchase.room.sellOrder.*");
		sic.add("purchase.room.roomModel.*");
		sic.add("purchase.room.building.*");
		sic.add("purchase.room.building.subarea.*");
		sic.add("purchase.sellProject.*");
		sic.add("purchase.customerInfo.*");
		//new add by renliang at 2011-3-3
		sic.add("purchase.customerInfo.customer.certificateName.id");
		sic.add("purchase.customerInfo.customer.certificateName.number");
		sic.add("purchase.customerInfo.customer.certificateName.name");
		sic.add("purchase.customerInfo.customer.certificateName.customerType");
		sic.add("purchase.customerInfo.customer.certificateName.description");
		sic.add("purchase.purCustomer.*");
		//new update by renliang at 2011-3-3
		sic.add("purchase.purCustomer.certificateName.id");
		sic.add("purchase.purCustomer.certificateName.number");
		sic.add("purchase.purCustomer.certificateName.name");
		sic.add("purchase.purCustomer.certificateName.customerType");
		sic.add("purchase.purCustomer.certificateName.description");
		
		sic.add("CU");
		sic.add("orgUnit");

		sic.add("state");
		sic.add("newPayListEntrys.*");
		sic.add("newPayListEntrys.moneyDefine.*");
		sic.add("newPayListEntrys.currency.name");
		sic.add("oldPayListEntrys.*");
		sic.add("oldPayListEntrys.moneyDefine.*");
		sic.add("oldPayListEntrys.currency.name");
		sic.add("oldDiscountEntrys.*");
		sic.add("oldDiscountEntrys.agio.*");
		sic.add("newDiscountEntrys.*");
		sic.add("newDiscountEntrys.agio.*");
		sic.add("newRoomAttachmentEntry.*");
		sic.add("newRoomAttachmentEntry.attachmentEntry.*");
		sic.add("oldRoomAttachmentEntry.*");
		sic.add("oldRoomAttachmentEntry.attachmentEntry.*");
		sic.add("newRoomAttachmentEntry.*");
		sic.add("newRoomAttachmentEntry.attachmentEntry.*");
		sic.add("newRoomAttachmentEntry.attachmentEntry.room.*");
		sic.add("newRoomAttachmentEntry.attachmentEntry.room.building.name");
		sic.add("oldRoomAttachmentEntry.*");
		sic.add("oldRoomAttachmentEntry.attachmentEntry.*");
		sic.add("oldRoomAttachmentEntry.attachmentEntry.room.*");
		sic.add("oldRoomAttachmentEntry.attachmentEntry.room.building.name");

		return sic;
	}

	protected PurchaseInfo purchaseInfo;

	boolean onLoaded = false;

	public boolean isModify() {
		onLoaded = false;
		return super.isModify();
	}

	private void addF7PayTypeFilter() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("startDate", new Date(),
						CompareType.LESS_EQUALS));
		if (purchaseInfo.getSellProject() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("project.id", purchaseInfo
							.getSellProject().getId().toString()));
		}
		this.prmtNewPayType.setEntityViewInfo(view);
	}

	private void initButtonStyle() {
		if (!SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()
				&& getOprtState().equals(OprtState.VIEW)) {
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionSave.setEnabled(false);
			this.actionSubmit.setEnabled(false);
		}
	}

	public void onLoad() throws Exception {
		this.comboDigit.addItem(SHEHelper.TEN_THOUSHAND_DIGIT);
		this.comboDigit.addItem(SHEHelper.THOUSHAND_DIGIT);

		super.onLoad();

		if (!SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()
				&& !getOprtState().equals(OprtState.VIEW)) {
			FDCMsgBox.showError("当前组织不能进行认购单变更！");
			abort();
		}
		initButtonStyle();
		if (getUIContext().get("src") != null) {
			src = (String) getUIContext().get("src");
		}

		if ("LISTUI".equals(src)) {
			this.actionSelectRoom.setEnabled(true);
		} else {
			this.actionSelectRoom.setEnabled(false);
		}

		// if(isFromSellControl){
		// this.actionSelectRoom.setEnabled(false);
		// }else {
		//			
		// }

		kDTNewPayList.checkParsed();
		txtChangeReason.setMaxLength(255);
		this.pkChangeDate.setVisible(true);

		// this.kDScrollPane4.setPreferredSize(new Dimension(200,200));
		// this.kDPanel3.setPreferredSize(new Dimension(1000,715));

		// this.kDScrollPane4.repaint();
		if (purchaseInfo == null) {
			purchaseInfo = editData.getPurchase();
		}

		if (editData.getPurchase() != null) {
			purchaseId = editData.getPurchase().getId().toString();
			// purchaseInfo = editData.getPurchase();
			initRoomInfo(purchaseInfo);
			initCustomerInfo(purchaseInfo);
			addF7PayTypeFilter();
			initCompensateAmount();
		} else {

		}

		if (!OprtState.ADDNEW.equals(oprtState)) {
			initPayList(editData.getNewPayListEntrys(), kDTNewPayList);
			initPayList(editData.getOldPayListEntrys(), kDTOldPayList);

			initNewRoomAttachment(editData.getNewRoomAttachmentEntry());
			initOldRoomAttachment(editData.getOldRoomAttachmentEntry());
			kDTOldPayList.getStyleAttributes().setLocked(true);
			// this.btnChooseRoom.setEnabled(false);
		}

		kDTNewPayList.getSelectManager().setSelectMode(
				KDTSelectManager.CELL_SELECT);
		initNewPayListColumnEditor();

		chkNewIsFitmentToContract.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				countLastestDealAmount();
			}

		});

		txtNewFitmentAmount.addDataChangeListener(new DataChangeListener() {

			public void dataChanged(DataChangeEvent eventObj) {
				countLastestDealAmount();
			}

		});

		prmtNewPayType.addDataChangeListener(new DataChangeListener() {

			public void dataChanged(DataChangeEvent eventObj) {
				if (eventObj.getOldValue() instanceof SHEPayTypeInfo
						&& eventObj.getNewValue() instanceof SHEPayTypeInfo) {
					if (!((SHEPayTypeInfo) eventObj.getOldValue()).getId()
							.toString().equals(
									((SHEPayTypeInfo) eventObj.getNewValue())
											.getId().toString())) {
						countLastestDealAmount();
					}
				} else {
					countLastestDealAmount();
				}

			}

		});

		txtReqDiscount.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				vitfyAgio(txtReqDiscount);

				BigDecimal reqAmount = (BigDecimal) PurchaseChangeBillEditUI.this.txtReqDiscount
						.getNumberValue();
				PurchaseChangeBillEditUI.this.txtNewDiscount
						.setNumberValue(reqAmount);
			}

		});

		txtNewDiscount.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				vitfyAgio(txtNewDiscount);
				countLastestDealAmount();
			}

		});

		this.menuBiz.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionUnAudit.setVisible(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionMultiapprove.setVisible(false);
		this.actionAudit.setEnabled(true);
		this.actionAudit.setVisible(true);
		this.actionAuditResult.setEnabled(true);
		this.actionAuditResult.setVisible(true);
		this.actionAttachment.setEnabled(true);
		this.actionAddNew.setEnabled(false);
		this.actionAddNew.setVisible(false);

		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);

		if (!FDCBillStateEnum.SAVED.equals(this.editData.getState())) {
			actionSave.setEnabled(false);
		}
		if ("writeNewAgio".equals(oprtState)) {
			txtNumber.setEnabled(false);
			kDCNewSellType.setEnabled(false);
			txtNewDiscount.setRequired(true);
			txtNewDiscount.setEnabled(true);
			txtReqDiscount.setEnabled(false);
			this.contSpecialAgioType.setEnabled(false);
		}
		if (getUIContext().get("isFromWorkflow") instanceof Boolean) {
			if (!((Boolean) getUIContext().get("isFromWorkflow"))
					.booleanValue()) {
				txtNewDiscount.setEnabled(false);
			}
		} else {
			txtNewDiscount.setEnabled(false);
		}

		this.prmtPurchaseChangeReason.setRequired(true);
		this.storeFields();

		this.initOldData(this.editData);
		onLoaded = true;
		setUITitle("认购变更");
		this.txtRoomNumber.setVisible(true);
		this.kDDateNewPurReqDate.setVisible(true);
		this.kDLabelContainer27.setVisible(true);
		this.pkChangeDate.setTimeEnabled(false);
		this.kDDateNewPurSigDate.setEnabled(true);

	}

	public void onShow() throws Exception {
		super.onShow();
		if (this.txtNumber.isEnabled()) {
			this.txtNumber.requestFocus();
		}
	}

	// 计算最新的成交总价
	void countLastestDealAmount() {
		initNewAgioRadio();
		try {
			updatePayList();
		} catch (Exception e) {
			ExceptionHandler.handle(e);
			abort();
		}
	}

	private void initNewAgioRadio() {
		RoomInfo roomInfo = null;
		if(this.editData.getPurchase()!=null){
			roomInfo = this.editData.getPurchase().getRoom();
		}
			
		SellTypeEnum sellType = (SellTypeEnum) this.kDCNewSellType
				.getSelectedItem();

		BigDecimal fitmentAmount = (BigDecimal) this.txtNewFitmentAmount
				.getNumberValue();
		if (fitmentAmount == null)
			fitmentAmount = FDCHelper.ZERO;
		BigDecimal attachmentAmount = (BigDecimal) this.txtNewAttachmentAmount
				.getNumberValue();
		if (attachmentAmount == null)
			attachmentAmount = FDCHelper.ZERO;
		BigDecimal areaCompensateAmount = (BigDecimal) this.txtNewCompensateAmount
				.getNumberValue();
		if (areaCompensateAmount == null)
			areaCompensateAmount = FDCHelper.ZERO;
		SpecialAgioEnum splType = (SpecialAgioEnum) this.comboSpecialAgioType
				.getSelectedItem();
		boolean isFitmentToContract = this.chkNewIsFitmentToContract
				.isSelected();
		BigDecimal splAgio = (BigDecimal) this.txtNewDiscount.getNumberValue();

//		PurchaseParam purParam = AgioSelectUI.getPurchaseAgioParam(
//				this.newAgioParam, roomInfo, sellType, isFitmentToContract,
//				fitmentAmount, attachmentAmount, areaCompensateAmount, splType,
//				splAgio);
//		if (purParam != null) {
//			this.kDTextArea2.setText(purParam.getAgioDes());
//			this.txtEndNewAgio.setValue(purParam.getFinalAgio());
//			this.txtNewDealAmount.setValue(purParam.getDealTotalAmount());
//			this.txtNewContractAmount.setValue(purParam
//					.getContractTotalAmount());
//			
//			//newAigio = this.txtEndNewAgio.getNumberValue();
//		}
	}

	// 初始化现售补差
	void initCompensateAmount() {
		if (purchaseInfo.getRoom().isIsActualAreaAudited()) {
			kDCNewSellType.setEnabled(true);
		} else {
			kDCNewSellType.setEnabled(false);
			txtNewCompensateAmount.setValue(null);
		}
		if (SellTypeEnum.PreSell.equals(kDCOldSellType.getSelectedItem())) {
			txtOldCompensateAmount.setValue(null);
		}
		kDCNewSellType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (SellTypeEnum.PreSell.equals(arg0.getItem())) {
					txtNewCompensateAmount.setValue(null);
				} else if (SellTypeEnum.LocaleSell.equals(arg0.getItem())) {
					if (purchaseInfo.getAreaCompensateAmount() == null) {
						if (purchaseInfo.getRoom() != null
								&& purchaseInfo.getRoom()
										.isIsActualAreaAudited()) {
							txtNewCompensateAmount
									.setValue(SHEHelper
											.getCompensateAmount(purchaseInfo
													.getRoom()));
						}
					} else {
						txtNewCompensateAmount.setValue(purchaseInfo
								.getAreaCompensateAmount());
					}
				}
				countLastestDealAmount();
			}
		});
	}

	// 初始化新的付款明细表单元格的编辑器
	void initNewPayListColumnEditor() {
		IColumn column = kDTNewPayList.getColumn("moneyName");
		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setDisplayFormat("$name$");
		f7Box.setDisplayFormat("$number$");
		f7Box.setDisplayFormat("$number$");
		f7Box
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("sysType",
						MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType",
						MoneyTypeEnum.EARNESTMONEY_VALUE));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("moneyType",
								MoneyTypeEnum.FISRTAMOUNT_VALUE));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("moneyType",
								MoneyTypeEnum.HOUSEAMOUNT_VALUE));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("moneyType",
								MoneyTypeEnum.LOANAMOUNT_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType",
						MoneyTypeEnum.ACCFUNDAMOUNT_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5)");
		f7Box.setEntityViewInfo(view);
		column.setEditor(new KDTDefaultCellEditor(f7Box));

		column = kDTNewPayList.getColumn("currency");
		column.getStyleAttributes().setLocked(true);

		column = kDTNewPayList.getColumn("date");
		column.setEditor(new KDTDefaultCellEditor(new KDDatePicker()));

		column = kDTNewPayList.getColumn("amount");
		KDFormattedTextField formattedTextField = new KDFormattedTextField();
		formattedTextField.setDataType(BigDecimal.class);
		formattedTextField.setPrecision(2);
		column.setEditor(new KDTDefaultCellEditor(formattedTextField));

		column = kDTNewPayList.getColumn("term");
		formattedTextField = new KDFormattedTextField();
		formattedTextField.setDataType(Integer.class);
		column.setEditor(new KDTDefaultCellEditor(formattedTextField));

		column = kDTNewPayList.getColumn("jiange");
		formattedTextField = new KDFormattedTextField();
		formattedTextField.setDataType(Integer.class);
		column.setEditor(new KDTDefaultCellEditor(formattedTextField));
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PurchaseChangeFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		PurchaseChangeInfo changeInfo = new PurchaseChangeInfo();
		changeInfo.setLastUpdateUser(userInfo);
		changeInfo.setChangeDate(SHEHelper.getCurrentTime());
		changeInfo.setState(FDCBillStateEnum.SAVED);
		String id = null;
		if (getUIContext().containsKey("purchaseID")) {
			id = getUIContext().get("purchaseID").toString();
		}
		// 销售控制表中会传入房间关联的认购单ID
		if (!StringUtils.isEmpty(id)) {
			isFromSellControl = true;
			try {
				SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
				selectorItemCollection.add("*");
				selectorItemCollection.add("room.*");
				selectorItemCollection.add("payType.*");
				selectorItemCollection.add("room.sellOrder.name");
				selectorItemCollection.add("room.building.name");
				selectorItemCollection.add("room.building.subarea.name");
				selectorItemCollection.add("room.building.sellProject.name");
				selectorItemCollection.add("room.roomModel.name");
				selectorItemCollection.add("customerInfo.*");
				selectorItemCollection.add("customerInfo.customer.*");
				selectorItemCollection.add("payListEntry.*");
				selectorItemCollection.add("agioEntrys.*");
				selectorItemCollection.add("agioEntrys.agio.*");
				selectorItemCollection.add("payListEntry.moneyDefine.*");
				selectorItemCollection.add("payListEntry.currency.name");
				selectorItemCollection.add("attachmentEntries.*");
				selectorItemCollection
						.add("attachmentEntries.attachmentEntry.*");
				selectorItemCollection
						.add("attachmentEntries.attachmentEntry.room.*");
				selectorItemCollection
						.add("attachmentEntries.attachmentEntry.room.building.name");
				purchaseInfo = PurchaseFactory.getRemoteInstance()
						.getPurchaseInfo(new ObjectUuidPK(id),
								selectorItemCollection);
				changeInfo.setOldPayType(purchaseInfo.getPayType());
				changeInfo.setNewPayType(purchaseInfo.getPayType());
				changeInfo.setPurchase(purchaseInfo);
				changeInfo.setOldContractAmount(purchaseInfo
						.getContractTotalAmount());
				changeInfo.setNewContractAmount(purchaseInfo
						.getContractTotalAmount());
				changeInfo.setOldDealAmount(purchaseInfo.getDealAmount());
				changeInfo.setNewDealAmount(purchaseInfo.getDealAmount());
				changeInfo.setOldLoanAmount(purchaseInfo.getLoanAmount());
				changeInfo.setNewLoanAmount(purchaseInfo.getLoanAmount());
				changeInfo.setOldAccuFundAmount(purchaseInfo
						.getAccumulationFundAmount());
				changeInfo.setNewAccuFundAmount(purchaseInfo
						.getAccumulationFundAmount());
				changeInfo.setOldAttachmentAmount(purchaseInfo
						.getAttachmentAmount());
				changeInfo.setNewAttachmentAmount(purchaseInfo
						.getAttachmentAmount());
				changeInfo.setOldFitmentAmount(purchaseInfo.getFitmentAmount());
				changeInfo.setNewFitmentAmount(purchaseInfo.getFitmentAmount());
				changeInfo.setOldIsFitmentToContract(purchaseInfo
						.isIsFitmentToContract());
				changeInfo.setNewIsFitmentToContract(purchaseInfo
						.isIsFitmentToContract());
				changeInfo.setOldCompensateAmount(purchaseInfo
						.getAreaCompensateAmount());
				changeInfo.setNewCompensateAmount(purchaseInfo
						.getAreaCompensateAmount());
				changeInfo.setOldPriceAccountType(purchaseInfo
						.getPriceAccountType());
				changeInfo.setNewPriceAccountType(purchaseInfo
						.getPriceAccountType());
				changeInfo.setOldSpecialAgioType(purchaseInfo
						.getSpecialAgioType());
				changeInfo.setOldPurchaseDate(purchaseInfo.getPurchaseDate());
				changeInfo.setOldPlanSignDate(purchaseInfo.getPlanSignDate());
				changeInfo.setNewPurchaseDate(purchaseInfo.getPurchaseDate());
				changeInfo.setNewPlanSignDate(purchaseInfo.getPlanSignDate());

				changeInfo.setNewSpecialAgioType(purchaseInfo
						.getSpecialAgioType()); //	
				changeInfo.setNewDiscount(purchaseInfo.getSpecialAgio()); //

				if (purchaseInfo.getSellType() != null) {
					changeInfo.setOldSellType(purchaseInfo.getSellType());
					changeInfo.setNewSellType(purchaseInfo.getSellType());
				}
				changeInfo.setOldDiscount(purchaseInfo.getSpecialAgio());
				// ---特殊折扣不提供折上折，将原特殊折扣带过来 zhicheng_jin 081129
				changeInfo.setReqDiscount(purchaseInfo.getSpecialAgio());
				// ----------------

				changeInfo.setOldIsBasePriceSell(purchaseInfo
						.isIsBasePriceSell());
				changeInfo.setNewIsBasePriceSell(purchaseInfo
						.isIsBasePriceSell());

				changeInfo.setOldIsAutoToInteger(purchaseInfo
						.isIsAutoToInteger());
				changeInfo.setNewIsAutoToInteger(purchaseInfo
						.isIsAutoToInteger());
				changeInfo.setOldToIntegerType(purchaseInfo.getToIntegerType());
				changeInfo.setNewToIntegerType(purchaseInfo.getToIntegerType());
				changeInfo.setOldDigit(purchaseInfo.getDigit());
				changeInfo.setNewDigit(purchaseInfo.getDigit());

				changeInfo.setOldBuildingPrice(purchaseInfo
						.getContractBuildPrice());
				changeInfo.setNewRoomPrice(purchaseInfo.getContractRoomPrice());
				changeInfo.setOldDealBuildingPrice(purchaseInfo
						.getDealBuildPrice());
				changeInfo.setOldDealRoomPrice(purchaseInfo.getDealRoomPrice());

				initPayList(purchaseInfo.getPayListEntry(), kDTNewPayList);
				initPayList(purchaseInfo.getPayListEntry(), kDTOldPayList);
				kDTOldPayList.getStyleAttributes().setLocked(true);

				// 初始化付款明细
				PurchaseChangePayListOldEntryCollection payListOldEntryCollection = new PurchaseChangePayListOldEntryCollection();
				for (int i = 0; purchaseInfo.getPayListEntry() != null
						&& i < purchaseInfo.getPayListEntry().size(); i++) {
					PurchasePayListEntryInfo purchasePayListEntryInfo = purchaseInfo
							.getPayListEntry().get(i);
					PurchaseChangePayListOldEntryInfo purchaseChangePayListOldEntryInfo = new PurchaseChangePayListOldEntryInfo();
					changeObjectValue(purchasePayListEntryInfo,
							purchaseChangePayListOldEntryInfo);
					// --为使得已收款的付款明的ID不变，将认购单分录的ID存在变更单新付款明细分录的number字段上,便于后面对应
					// zhicheng_jin 090423
					purchaseChangePayListOldEntryInfo
							.setNumber(purchasePayListEntryInfo.getId()
									.toString());
					// ------
					payListOldEntryCollection
							.add(purchaseChangePayListOldEntryInfo);
				}
				changeInfo.getOldPayListEntrys().addCollection(
						payListOldEntryCollection);

				// 初始化折扣
				PurchaseChangeAgioOldEntryCollection agioOldEntryCollection = new PurchaseChangeAgioOldEntryCollection();
				for (int i = 0; purchaseInfo.getAgioEntrys() != null
						&& i < purchaseInfo.getAgioEntrys().size(); i++) {
					PurchaseAgioEntryInfo purchaseAgioEntryInfo = purchaseInfo
							.getAgioEntrys().get(i);
					PurchaseChangeAgioOldEntryInfo purchaseChangeAgioOldEntryInfo = new PurchaseChangeAgioOldEntryInfo();
					changeObjectValue(purchaseAgioEntryInfo,
							purchaseChangeAgioOldEntryInfo);
					agioOldEntryCollection.add(purchaseChangeAgioOldEntryInfo);
				}
				changeInfo.getOldDiscountEntrys().addCollection(
						agioOldEntryCollection);

				PurchaseChangeAgioEntryCollection agioEntryCollection = new PurchaseChangeAgioEntryCollection();
				for (int i = 0; purchaseInfo.getAgioEntrys() != null
						&& i < purchaseInfo.getAgioEntrys().size(); i++) {
					PurchaseAgioEntryInfo purchaseAgioEntryInfo = purchaseInfo
							.getAgioEntrys().get(i);
					PurchaseChangeAgioEntryInfo purchaseChangeAgioEntryInfo = new PurchaseChangeAgioEntryInfo();
					changeObjectValue(purchaseAgioEntryInfo,
							purchaseChangeAgioEntryInfo);
					agioEntryCollection.add(purchaseChangeAgioEntryInfo);
				}
				changeInfo.getNewDiscountEntrys().addCollection(
						agioEntryCollection);

				// 附属房产
				PurchaseChangeRoomAttachmentEntryCollection attachmentEntryCollection = new PurchaseChangeRoomAttachmentEntryCollection();
				for (int i = 0; purchaseInfo.getAttachmentEntries() != null
						&& i < purchaseInfo.getAttachmentEntries().size(); i++) {
					PurchaseRoomAttachmentEntryInfo purchaseRoomAttachmentEntryInfo = purchaseInfo
							.getAttachmentEntries().get(i);
					PurchaseChangeRoomAttachmentEntryInfo purchaseChangeRoomAttachmentEntryInfo = new PurchaseChangeRoomAttachmentEntryInfo();
					changeObjectValue(purchaseRoomAttachmentEntryInfo,
							purchaseChangeRoomAttachmentEntryInfo);
					attachmentEntryCollection
							.add(purchaseChangeRoomAttachmentEntryInfo);
				}
				changeInfo.getNewRoomAttachmentEntry().addCollection(
						attachmentEntryCollection);

				PurchaseChangeRoomAttachmentOldEntryCollection attachmentOldEntryCollection = new PurchaseChangeRoomAttachmentOldEntryCollection();
				for (int i = 0; purchaseInfo.getAttachmentEntries() != null
						&& i < purchaseInfo.getAttachmentEntries().size(); i++) {
					PurchaseRoomAttachmentEntryInfo purchaseRoomAttachmentEntryInfo = purchaseInfo
							.getAttachmentEntries().get(i);
					PurchaseChangeRoomAttachmentOldEntryInfo purchaseChangeRoomAttachmentOldEntryInfo = new PurchaseChangeRoomAttachmentOldEntryInfo();
					changeObjectValue(purchaseRoomAttachmentEntryInfo,
							purchaseChangeRoomAttachmentOldEntryInfo);
					attachmentOldEntryCollection
							.add(purchaseChangeRoomAttachmentOldEntryInfo);
				}
				changeInfo.getOldRoomAttachmentEntry().addCollection(
						attachmentOldEntryCollection);

				initNewRoomAttachment(attachmentEntryCollection);
				initOldRoomAttachment(attachmentOldEntryCollection);

				this.purchaseId = purchaseInfo.getId().toString();
			} catch (EASBizException e) {
				this.handleException(e);
			} catch (BOSException e) {
				this.handleException(e);
			}
		}

		changeInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		changeInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit()
				.castToFullOrgUnitInfo());
		changeInfo.setBookedDate(new Date());

		return changeInfo;
	}

	public void actionAssoPurchase_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionAssoPurchase_actionPerformed(e);
		if (!StringUtils.isEmpty(purchaseId)) {
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			uiContext.put(UIContext.ID, purchaseId);

			IUIFactory uiFactory = UIFactory
					.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(PurchaseEditUI.class
					.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

	// 计算折扣，折扣描述
	public void initOldAgioDescription() {
		AgioEntryCollection agioEntryColl = new AgioEntryCollection();
		for (int i = 0; i < editData.getOldDiscountEntrys().size(); i++)
			agioEntryColl.add(editData.getOldDiscountEntrys().get(i));

//		String agioDes = SHEHelper.getAgioDes(agioEntryColl,
//				this.editData.getOldSpecialAgioType(), this.editData
//						.getOldDiscount(),
//				this.editData.isOldIsAutoToInteger(), this.editData
//						.isOldIsBasePriceSell(), this.editData
//						.getOldToIntegerType(), this.editData.getOldDigit());

//		kDTextArea1.setText(agioDes);

		SellTypeEnum sellType = (SellTypeEnum) this.kDCOldSellType
				.getSelectedItem();
		boolean isFitmentToContract = this.chkOldIsFitmentToContract
				.isSelected();
		BigDecimal fitmentAmount = (BigDecimal) this.txtOldFitmentAmount
				.getNumberValue();
		if (fitmentAmount == null)
			fitmentAmount = FDCHelper.ZERO;
		BigDecimal attachmentAmount = (BigDecimal) this.txtOldAttachmentAmount
				.getNumberValue();
		if (attachmentAmount == null)
			attachmentAmount = FDCHelper.ZERO;
		BigDecimal areaCompensateAmount = (BigDecimal) this.txtOldCompensateAmount
				.getNumberValue();
		if (areaCompensateAmount == null)
			areaCompensateAmount = FDCHelper.ZERO;
		SpecialAgioEnum splType = (SpecialAgioEnum) this.comboOldSpecialAgioType
				.getSelectedItem();
		BigDecimal splAgio = (BigDecimal) this.txtOldDiscount.getNumberValue();

		AgioParam oldAgioParam = new AgioParam();
		oldAgioParam.setAgios(agioEntryColl);
		oldAgioParam.setBasePriceSell(this.editData.isOldIsBasePriceSell());
		oldAgioParam.setDigit(this.editData.getOldDigit());
		oldAgioParam.setPriceAccountType((PriceAccountTypeEnum) this.editData
				.getOldPriceAccountType());
		oldAgioParam.setToInteger(this.editData.isOldIsAutoToInteger());
		oldAgioParam.setToIntegerType(this.editData.getOldToIntegerType());
//		PurchaseParam purParam = AgioSelectUI.getPurchaseAgioParam(
//				oldAgioParam, purchaseInfo.getRoom(), sellType,
//				isFitmentToContract, fitmentAmount, attachmentAmount,
//				areaCompensateAmount, splType, splAgio);
//		this.txtEndOldAgio.setValue(purParam.getFinalAgio());
	}

	// base1是有数据的 base2是空的
	void changeObjectValue(CoreBaseInfo base1, CoreBaseInfo base2) {
		Enumeration enumeration = base1.keys();
		while (enumeration.hasMoreElements()) {
			String obj = enumeration.nextElement().toString();
			if (!"id".equals(obj)) {
				base2.put(obj, base1.get(obj));
			}
		}
	}

	/**
	 * 修改新增界面的房间初始化方法
	 * 
	 * @param roomInfo
	 */
	void initRoomInfo(RoomInfo roomInfo) {
		txtRoomNumber.setText(roomInfo.getNumber());
		txtProjectName.setText(roomInfo.getBuilding().getSellProject()
				.getName());
		if (roomInfo.getBuilding().getSubarea() != null) {
			txtSubarea.setText(roomInfo.getBuilding().getSubarea().getName());
		}
		spiUnit.setValue(new Integer(roomInfo.getBuildUnit() == null ? 0
				: roomInfo.getBuildUnit().getSeq()));
		txtBuilding.setText(roomInfo.getBuilding().getName());
		txtSellOrder.setText(roomInfo.getSellOrder().getName());
		f7RoomModel.setValue(roomInfo.getRoomModel());
		if (roomInfo.getBuildingArea() != null) {
			txtBuildingArea.setText(roomInfo.getBuildingArea().setScale(2,
					BigDecimal.ROUND_HALF_UP).toString());
		}
		if (roomInfo.getRoomArea() != null) {
			txtRoomArea.setText(roomInfo.getRoomArea().setScale(2,
					BigDecimal.ROUND_HALF_UP).toString());
		}
		if (roomInfo.getStandardTotalAmount() != null) {
			txtStandardTotalAmount.setText(roomInfo.getStandardTotalAmount()
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		}
		if (roomInfo.getStandardTotalAmount() != null
				&& roomInfo.getBuildingArea() != null) {
			txtStandardBuildingPrice.setText((roomInfo.getStandardTotalAmount()
					.divide(roomInfo.getBuildingArea(), 2,
							BigDecimal.ROUND_HALF_UP)).toString());
		}
		if (roomInfo.getStandardTotalAmount() != null
				&& roomInfo.getRoomArea() != null) {
			txtStandardRoomPrice
					.setText((roomInfo.getStandardTotalAmount().divide(roomInfo
							.getRoomArea(), 2, BigDecimal.ROUND_HALF_UP))
							.toString());
		}
	}

	// 初始化房间信息
	void initRoomInfo(PurchaseInfo purchaseInfo) {
		RoomInfo roomInfo = purchaseInfo.getRoom();
		txtRoomNumber.setText(roomInfo.getNumber());
		if (roomInfo.getBuilding().getSellProject() != null)
			txtProjectName.setText(roomInfo.getBuilding().getSellProject()
					.getName());
		if (roomInfo.getBuilding().getSubarea() != null) {
			txtSubarea.setText(roomInfo.getBuilding().getSubarea().getName());
		}
		spiUnit.setValue(new Integer(roomInfo.getBuildUnit() == null ? 0
				: roomInfo.getBuildUnit().getSeq()));
		txtBuilding.setText(roomInfo.getBuilding().getName());

		FunctionSetting proSet = (FunctionSetting)setting.getFunctionSetMap().get(String.valueOf(roomInfo.getBuilding().getSellProject().getId()));
		
		if(roomInfo.getSellOrder()==null){
			if(!proSet.getIsSincerSellOrder().booleanValue() ){
				txtSellOrder.setText(roomInfo.getSellOrder().getName());
			}
		}else{
			txtSellOrder.setText(roomInfo.getSellOrder().getName());
		}
			
		f7RoomModel.setValue(roomInfo.getRoomModel());
		if (roomInfo.getBuildingArea() != null) {
			txtBuildingArea.setText(roomInfo.getBuildingArea().setScale(2,
					BigDecimal.ROUND_HALF_UP).toString());
		}
		if (roomInfo.getRoomArea() != null) {
			txtRoomArea.setText(roomInfo.getRoomArea().setScale(2,
					BigDecimal.ROUND_HALF_UP).toString());
		}
		if (roomInfo.getStandardTotalAmount() != null) {
			txtStandardTotalAmount.setText(roomInfo.getStandardTotalAmount()
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		}
		if (roomInfo.getStandardTotalAmount() != null
				&& roomInfo.getBuildingArea() != null) {
			txtStandardBuildingPrice.setText((roomInfo.getStandardTotalAmount()
					.divide(roomInfo.getBuildingArea(), 2,
							BigDecimal.ROUND_HALF_UP)).toString());
		}
		if (roomInfo.getStandardTotalAmount() != null
				&& roomInfo.getRoomArea() != null) {
			txtStandardRoomPrice
					.setText((roomInfo.getStandardTotalAmount().divide(roomInfo
							.getRoomArea(), 2, BigDecimal.ROUND_HALF_UP))
							.toString());
		}
	}
	
	/*
	 *分录对客户序号进行排列 
	 */
	private void sortBySeq(PurchaseCustomerInfoCollection customerInfos) {
		if (customerInfos == null || customerInfos.size() <= 1)
			return;
		Object[] objs = customerInfos.toArray();
		Arrays.sort(objs, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				PurchaseCustomerInfoInfo tmp0 = (PurchaseCustomerInfoInfo) arg0;
				PurchaseCustomerInfoInfo tmp1 = (PurchaseCustomerInfoInfo) arg1;
				if (tmp0 == null || tmp1 == null) {
					return 0;
				}
				return tmp0.getSeq() - tmp1.getSeq();
			}
		});
		customerInfos.clear();

		for (int i = 0; i < objs.length; i++) {
			customerInfos.add((PurchaseCustomerInfoInfo) objs[i]);
		}
	}
	/*
	 * 交易表对客户序号进行排列
	 */
	private void sortBySeq_purcustomer(PurCustomerCollection purCustomers) {
		if (purCustomers == null || purCustomers.size() <= 1)
			return;
		Object[] objs = purCustomers.toArray();
		Arrays.sort(objs, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				PurCustomerInfo tmp0 = (PurCustomerInfo) arg0;
				PurCustomerInfo tmp1 = (PurCustomerInfo) arg1;
				if (tmp0 == null || tmp1 == null) {
					return 0;
				}
				return tmp0.getSeq() - tmp1.getSeq();
			}
		});
		purCustomers.clear();

		for (int i = 0; i < objs.length; i++) {
			purCustomers.add((PurCustomerInfo) objs[i]);
		}
	}
	// 初始化客户信息
	void initCustomerInfo(PurchaseInfo info) {
		
		PurchaseCustomerInfoCollection collection = info.getCustomerInfo();
		sortBySeq(collection); // 按序号排序
 		//加载新交易数据  by zgy 
 		PurCustomerCollection purCustomerInfos = info.getPurCustomer();
 		sortBySeq_purcustomer(purCustomerInfos);
 		
		kDTCustomer.checkParsed();
		//发现以前的bug，重复选择追加，不是重复加载。有问题，by zgy 2010-12-19
		kDTCustomer.removeRows();
		for (int i = 0; i < collection.size(); i++) {
			IObjectValue objectValue = collection.getObject(i);
			IRow row = kDTCustomer.addRow();
			row.getCell("propertyPercent").setValue(
					objectValue.get("propertyPercent"));
			FDCCustomerInfo customerInfo2 = (FDCCustomerInfo) objectValue
					.get("customer");
			//by tim_gao 存放对象，不是字段,便于客户查询2010-9-24
			row.getCell("customer").setValue(customerInfo2);
			row.getCell("id").setValue(customerInfo2.getId().toString());
			row.getCell("postalcode").setValue(customerInfo2.getPostalcode());
			row.getCell("phone").setValue(customerInfo2.getPhone());
			row.getCell("certificateName").setValue(
					customerInfo2.getCertificateName());
			row.getCell("certificateNumber").setValue(
					customerInfo2.getCertificateNumber());
			row.getCell("mailAddress").setValue(customerInfo2.getMailAddress());
			row.getCell("bookDate").setValue(customerInfo2.getCreateTime());
			row.getCell("des").setValue(customerInfo2.getDescription());
			
			//维持以前的数据 ，增加再在的逻辑，如果存在覆盖旧数据
			//by zgy   2010-12-18
			if(purCustomerInfos!=null){
				PurCustomerInfo pCinfo  = purCustomerInfos.get(i);
				if(pCinfo!=null){
					try {
						if(pCinfo.getPropertyPercent()==null){
							pCinfo  = PurCustomerFactory.getRemoteInstance().getPurCustomerInfo(new ObjectUuidPK(pCinfo.getId().toString()));
						}
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
					row.getCell("propertyPercent").setValue(pCinfo.getPropertyPercent()); 
					row.getCell("des").setValue(pCinfo.getDescription());
					row.getCell("customer").setValue(pCinfo.getCustomerName());
					row.getCell("id").setValue(pCinfo.getCustomerID());
					row.getCell("postalcode").setValue(pCinfo.getPostalcode());
					row.getCell("phone").setValue(pCinfo.getTel());
					row.getCell("certificateName").setValue(pCinfo.getCertificateName());
					row.getCell("certificateNumber").setValue(pCinfo.getCertificateNumber());
					row.getCell("mailAddress").setValue(pCinfo.getMailAddress());
					row.getCell("bookDate").setValue(pCinfo.getCreateTime());
				}
			}
		}
		
		kDTCustomer.setEnabled(false);
	}

	void initPayList(AbstractObjectCollection coreBaseCollection, KDTable table) {
		table.checkParsed();
		table.removeRows();
		if (coreBaseCollection != null) {
			CRMHelper.sortCollection(coreBaseCollection, "seq", true);
			table.addRows(coreBaseCollection.size());
			for (int i = 0; i < coreBaseCollection.size(); i++) {
				CoreBaseInfo objectValue = (CoreBaseInfo) coreBaseCollection
						.getObject(i);
				
				IRow rowOld = table.getRow(i);
				if ("kDTNewPayList".equals(table.getName())) {
					PurchaseChangePayListEntryInfo purchaseChangePayListEntryInfo = new PurchaseChangePayListEntryInfo();
					changeObjectValue(objectValue,
							purchaseChangePayListEntryInfo);
					// --为使得已收款的付款明的ID不变，将认购单分录的ID存在变更单新付款明细分录的number字段上,便于后面对应
					// zhicheng_jin 090423
					if (objectValue instanceof PurchasePayListEntryInfo)
						purchaseChangePayListEntryInfo.setNumber(objectValue.getId().toString());
					// --
					rowOld.setUserObject(purchaseChangePayListEntryInfo);
				} else if ("kDTOldPayList".equals(table.getName())) {
					PurchaseChangePayListOldEntryInfo purchaseChangePayListEntryInfo = new PurchaseChangePayListOldEntryInfo();
					changeObjectValue(objectValue,
							purchaseChangePayListEntryInfo);
					if (objectValue instanceof PurchasePayListEntryInfo)
						purchaseChangePayListEntryInfo.setNumber(objectValue.getId().toString());
					rowOld.setUserObject(purchaseChangePayListEntryInfo);
				}

				rowOld.getCell("moneyName").setValue(objectValue.get("moneyDefine"));
				rowOld.getCell("date").setValue(objectValue.get("appDate"));

				rowOld.getCell("currency").setValue(objectValue.get("currency"));
				if (objectValue.get("appAmount") instanceof BigDecimal) {
					rowOld.getCell("amount").setValue(((BigDecimal) objectValue.get("appAmount")).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				rowOld.getCell("term").setValue(objectValue.get("term"));
				rowOld.getCell("jiange").setValue(objectValue.get("monthInterval"));
				rowOld.getCell("des").setValue(objectValue.get("description"));

				BigDecimal apAmount = objectValue.getBigDecimal("appAmount");
				BigDecimal actAmount = objectValue.getBigDecimal("actRevAmount");
				if (apAmount == null) {
					apAmount = FDCHelper.ZERO;
				}
				if (actAmount == null) {
					actAmount = FDCHelper.ZERO;
				}
				boolean isFinishedPay  = false;
				//如果该笔款项全部收清，则变更时不能更改款项金额 
				if(objectValue instanceof RevListInfo && ((RevListInfo)objectValue).getRemainAmount().compareTo(apAmount) == 0){
					RevListInfo revlist =(RevListInfo)objectValue;
					isFinishedPay = true;
				}
				
				
				if (actAmount.compareTo(FDCHelper.ZERO) != 0) {
					rowOld.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
					rowOld.getCell("moneyName").getStyleAttributes().setLocked(true);
					rowOld.getCell("date").getStyleAttributes().setLocked(true);
					rowOld.getCell("currency").getStyleAttributes().setLocked(true);
					rowOld.getCell("term").getStyleAttributes().setLocked(true);
					rowOld.getCell("jiange").getStyleAttributes().setLocked(true);

					if (!OprtState.ADDNEW.equals(oprtState)) {
						if (actAmount.compareTo(apAmount) == 0) {
							rowOld.getCell("amount").getStyleAttributes().setLocked(true);
						}
					}
					
					if(isFinishedPay){
						rowOld.getCell("amount").getStyleAttributes().setLocked(true);
					}
					//是预定金必锁
					if(objectValue instanceof RevListInfo){
						RevListInfo revlist =(RevListInfo)objectValue;
						if(MoneyTypeEnum.PreconcertMoney.equals(revlist.getMoneyDefine().getMoneyType())){
							rowOld.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
							rowOld.getStyleAttributes().setLocked(true);
						}
					}
				}
			}
		}
	}

	public boolean destroyWindow() {
		boolean b = super.destroyWindow();
		if (getUIContext().get(UIContext.OWNER) instanceof PurchaseChangeBillListUI) {
			((PurchaseChangeBillListUI) getUIContext().get(UIContext.OWNER)).kDTable1
					.removeRows();
		}
		return b;
	}

	/**
	 * 对于已结算的期间，不允许进行收款及修改
	 * */
	private void verifyBalance() {
		Date bizDate = (Date) this.pkChangeDate.getValue();
		if (bizDate == null) {
			MsgBox.showInfo("变更时间不能为空。");
			this.abort();
		}
		Date balanceEndDate = null;
		SellProjectInfo sellProject = null;
		PurchaseChangeInfo info = this.editData;
		if (info != null) {
			if (info.getPurchase() != null) {
				if (info.getPurchase().getSellProject() != null) {
					sellProject = info.getPurchase().getSellProject();
				}
			}
		}
		if (sellProject != null) {
			try {
				balanceEndDate = getLastEndDate(sellProject.getId().toString());
			} catch (Exception e) {
				handleException(e);
				e.printStackTrace();
			}
			SHEHelper.verifyBalance(bizDate, balanceEndDate);
		}
	}

	/***
	 * 按销售项目取上次结算的截止日期。
	 * **/

	private Date getLastEndDate(String sellProjectId) throws Exception {
		Date lastEndDate = null;
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder
				.appendSql("select FBalanceEndDate from T_SHE_SellProject where FID = ?");
		detailBuilder.addParam(sellProjectId);
		try {
			IRowSet detailSet = detailBuilder.executeQuery();
			if (detailSet.next()) {
				lastEndDate = detailSet.getDate("FBalanceEndDate");
			}
		} catch (Exception e) {
			handleException(e);
			e.printStackTrace();
		}
		return lastEndDate;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		verifyBalance();
		super.verifyInput(e);
		if (this.prmtPurchaseChangeReason.getValue() == null
				|| "".equals(this.prmtPurchaseChangeReason.getValue()
						.toString())) {
			MsgBox.showInfo("变更原因必须录入！");
			abort();
		}
		
		Date purdate = null;
		Date signdate = null;
		if (this.kDDateNewPurReqDate.getValue() != null) {
			purdate = (Date) this.kDDateNewPurReqDate.getValue();
		} else {
			this.kDDateNewPurReqDate.setValue(editData.getPurchase()
					.getPurchaseDate());
		}

		if (this.kDDateNewPurSigDate.getValue() != null) {
			signdate = (Date) this.kDDateNewPurSigDate.getValue();
		} else {
			this.kDDateNewPurSigDate.setValue(editData.getPurchase()
					.getPlanSignDate());
		}
		if (signdate != null && purdate != null) {
			if (DateTimeUtils.truncateDate(signdate).before(
					DateTimeUtils.truncateDate(purdate))) {
				FDCMsgBox.showError("约定签约日期不得早于认购日期！");
				abort();
			}
		}

		verifyPayListTab();

		// verifyDealAmount();
	}

	private void verifyPayListTab() throws BOSException {
		
		BigDecimal dealAmount = this.txtNewContractAmount.getBigDecimalValue();
		
//		boolean result = true;
//		
//		Number numberAigo = this.txtEndNewAgio.getNumberValue();
//		
//		if(newAigio!=null && !numberAigo.equals(newAigio)){
//			result = false;
//		}
//		
		BigDecimal totalAmount = FDCHelper.ZERO;
		BigDecimal newAigioAmount = FDCHelper.ZERO;
		
		Date curDate = null;
		for (int i = 0; i < this.kDTNewPayList.getRowCount(); i++) {
			IRow row = this.kDTNewPayList.getRow(i);

			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(
					"moneyName").getValue();
			if (moneyName == null) {
				MsgBox.showInfo("付款明细,第" + (row.getRowIndex() + 1)
						+ "行款项名称没有录入!");
				this.abort();
			}

		// 预收款和预订金不统计进去，预定金到这里的时候都转到了房款里 ，所以不要再计算一次
			if (moneyName.getMoneyType().equals(MoneyTypeEnum.PreMoney)|| moneyName.getMoneyType().equals(
							MoneyTypeEnum.PreconcertMoney)){
				Object amount = row.getCell("amount").getValue();
				if(amount!=null){
					newAigioAmount = FDCHelper.add(newAigioAmount, (BigDecimal) amount);
				}
				continue;
			}
				
			
			Object amount = row.getCell("amount").getValue();
			if (amount == null || !(amount instanceof BigDecimal)
					|| ((BigDecimal) amount).compareTo(FDCHelper.ZERO) == 0) {
				MsgBox.showInfo("付款明细,第" + (row.getRowIndex() + 1)
						+ "行应收金额没有录入!");
				this.abort();
			}

			if (!isEarnestInHouseAmount
					&& moneyName.getMoneyType().equals(
							MoneyTypeEnum.EarnestMoney)) {
				// 如果定金不计入房款，则不同统计定金
			} else {
				totalAmount = totalAmount.add((BigDecimal) amount);
			}

			Date date = (Date) row.getCell("date").getValue();
			if (date == null && !moneyName.getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)) {
				MsgBox.showInfo("付款明细,第" + (row.getRowIndex() + 1)	+ "行日期必须录入!");
				this.abort();
			}
			if(date != null){
				date = FDCDateHelper.getDayBegin(date);
				if (curDate == null) {
					curDate = date;
				} else {
					if (date.before(curDate)) {
						MsgBox.showInfo("付款明细,第" + (row.getRowIndex() + 1)
								+ "行日期录入不能小于前面行的日期!");
						this.abort();
					} else {
						curDate = date;
					}
				}
			}	
			
			
			
		}

//		if (dealAmount == null) {
//			dealAmount = FDCHelper.ZERO;
//		}
//		if(!result){
//			dealAmount = FDCHelper.subtract(dealAmount, newAigioAmount);
//		}
		if (totalAmount.compareTo(dealAmount) != 0) {
			MsgBox.showInfo("付款明细总额("+totalAmount+")不等于成交金额(" +dealAmount+")!" 
					);
			logger.info("*****认购单提交-----付款明细总额不等于成交金额!(" + totalAmount + "!="
					+ dealAmount + ")");
			this.abort();
		}

		verifyBalance();
	}

	public void loadFields() {
		PurchaseChangeInfo purChangeInfo = this.editData;
		// 来自销控表界面操作
		if (isFromSellControl || getOprtState().equals(OprtState.VIEW)
				|| getOprtState().equals(OprtState.EDIT)) {
			this.isEarnestInHouseAmount = purChangeInfo.getPurchase()
					.isIsEarnestInHouseAmount();

			this.newAgioParam.setPriceAccountType(purChangeInfo
					.getNewPriceAccountType());
			this.newAgioParam
					.setToInteger(purChangeInfo.isNewIsAutoToInteger());
			this.newAgioParam.setBasePriceSell(purChangeInfo
					.isNewIsBasePriceSell());
			this.newAgioParam.setToIntegerType(purChangeInfo
					.getNewToIntegerType());
			this.newAgioParam.setDigit(purChangeInfo.getNewDigit());
			AgioEntryCollection agioEntryColl = new AgioEntryCollection();
			for (int i = 0; i < purChangeInfo.getNewDiscountEntrys().size(); i++)
				agioEntryColl.add(purChangeInfo.getNewDiscountEntrys().get(i));
			this.newAgioParam.setAgios(agioEntryColl);

			IObjectPK objectPK = new ObjectUuidPK(this.editData.getPurchase()
					.getId().toString());
			SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
			selectorItemCollection.add("*");
			selectorItemCollection.add("room.*");
			selectorItemCollection.add("room.sellOrder.name");
			selectorItemCollection.add("room.building.name");
			selectorItemCollection.add("room.buildUnit.*");
			selectorItemCollection.add("room.building.subarea.name");
			selectorItemCollection.add("room.building.sellProject.name");
			selectorItemCollection.add("room.roomModel.name");
			selectorItemCollection.add("customerInfo.*");
			selectorItemCollection.add("customerInfo.customer.*");
			selectorItemCollection.add("payListEntry.*");
			selectorItemCollection.add("payListEntry.moneyDefine.*");
			selectorItemCollection.add("payListEntry.currency.name");
			selectorItemCollection.add("dealCurrency.*");

			try {
				purchaseInfo = PurchaseFactory.getRemoteInstance()
						.getPurchaseInfo(objectPK, selectorItemCollection);
			} catch (EASBizException e) {
				handleException(e);
			} catch (BOSException e) {
				handleException(e);
			}

			this.comboPriceAccount.setSelectedItem(purChangeInfo
					.getNewPriceAccountType());
			this.comboPriceAccount.setEnabled(false);
			this.txtOldDiscount.setValue(purChangeInfo.getOldDiscount());
			if (purChangeInfo.getPurchase().getSpecialAgioType() == null) {
				this.comboOldSpecialAgioType
						.setSelectedItem(SpecialAgioEnum.DaZhe);
				this.comboOldSpecialAgioType.setEnabled(false);
			} else {
				this.comboOldSpecialAgioType.setSelectedItem(purChangeInfo
						.getOldSpecialAgioType());
			}
			if (purChangeInfo.getNewSpecialAgioType() == null) {
				this.comboSpecialAgioType
						.setSelectedItem(SpecialAgioEnum.DaZhe);
			} else {
				this.comboSpecialAgioType.setSelectedItem(purChangeInfo
						.getNewSpecialAgioType());
			}
			initOldAgioDescription();
			if ("ADDNEW".equals(this.getOprtState())) {
				this.txtNewDiscount.setValue(null);
				this.txtReqDiscount.setValue(null);
			} else {
				this.txtNewDiscount.setValue(purChangeInfo.getNewDiscount());
				this.txtReqDiscount.setValue(purChangeInfo.getReqDiscount());
			}
			initNewAgioRadio();
		}

		super.loadFields();
	}

	protected void btnChooseRoom_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		if (getOprtState().equals(OprtState.VIEW)) {
			return;
		}
		super.btnChooseRoom_actionPerformed(e);
		BuildingInfo buildingInfo = null;
		BuildingUnitInfo buildUnit = null;
		SellProjectInfo sellProject = null;

		if (getUIContext().get("building") != null) {
			if (getUIContext().get("building") instanceof BuildingInfo)
				buildingInfo = (BuildingInfo) getUIContext().get("building");
		}

		if (getUIContext().get("unit") != null) {
			if (getUIContext().get("unit") instanceof BuildingUnitInfo)
				buildUnit = (BuildingUnitInfo) getUIContext().get("unit");
		}

		if (getUIContext().get("sellProject") != null) {
			if (getUIContext().get("sellProject") instanceof SellProjectInfo)
				sellProject = (SellProjectInfo) getUIContext().get(
						"sellProject");
		}

		RoomInfo room = RoomSelectUI.showOneRoomSelectUI(this, buildingInfo,
				buildUnit, MoneySysTypeEnum.SalehouseSys, null, sellProject);
		if (room == null) {
			return;
		}
		if (!canChange(room.getSellState(), getCanChangeState())) {
			FDCMsgBox.showError("非认购、签约状态的房间，不可进行变更操作！");
			abort();
		}

		purchaseId = room.getLastPurchase().getId().toString();
		int unAudittedNumber = 0;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select count(*) from t_she_purchasechange where  fpurchaseid = ? and fstate in('1SAVED','2SUBMITTED')");
		builder.addParam(purchaseId);
		RowSet rs = builder.executeQuery();
		while (rs.next()) {
			unAudittedNumber = rs.getInt(1);
		}

		if (unAudittedNumber > 0) {
			FDCMsgBox.showError("此房间有变更单处于变更中，不能再次进行变更！");
			abort();
		}

		RoomLoanCollection roomLoan = RoomLoanFactory.getRemoteInstance()
				.getRoomLoanCollection(
						"where purchase = '" + purchaseId
								+ "' and aFMortgagedState = 1");
		if (roomLoan != null && roomLoan.size() > 0) {
			FDCMsgBox.showWarning("此房间所对应的认购单已经生成公积金/按揭单，不能再进行变更！");
			SysUtil.abort();
		}

		SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
		selectorItemCollection.add("*");
		selectorItemCollection.add("room.*");
		selectorItemCollection.add("payType.*");
		selectorItemCollection.add("room.sellOrder.name");
		selectorItemCollection.add("room.building.name");
		selectorItemCollection.add("room.building.subarea.name");
		selectorItemCollection.add("room.building.sellProject.name");
		selectorItemCollection.add("room.roomModel.name");
		selectorItemCollection.add("customerInfo.*");
		selectorItemCollection.add("customerInfo.customer.*");
		selectorItemCollection.add("payListEntry.*");
		selectorItemCollection.add("agioEntrys.*");
		selectorItemCollection.add("agioEntrys.agio.*");
		selectorItemCollection.add("payListEntry.moneyDefine.*");
		selectorItemCollection.add("payListEntry.currency.name");
		selectorItemCollection.add("attachmentEntries.*");
		selectorItemCollection.add("attachmentEntries.attachmentEntry.*");
		selectorItemCollection.add("attachmentEntries.attachmentEntry.room.*");
		selectorItemCollection.add("purCustomer.*");
		//new add by renliang at 2011-3-3
		selectorItemCollection.add("customerInfo.customer.certificateName.id");
		selectorItemCollection.add("customerInfo.customer.certificateName.number");
		selectorItemCollection.add("customerInfo.customer.certificateName.name");
		selectorItemCollection.add("customerInfo.customer.certificateName.customerType");
		selectorItemCollection.add("customerInfo.customer.certificateName.description");
		selectorItemCollection.add("purCustomer.*");
		//new update by renliang at 2011-3-3
		selectorItemCollection.add("purCustomer.certificateName.id");
		selectorItemCollection.add("purCustomer.certificateName.number");
		selectorItemCollection.add("purCustomer.certificateName.name");
		selectorItemCollection.add("purCustomer.certificateName.customerType");
		selectorItemCollection.add("purCustomer.certificateName.description");
		selectorItemCollection
				.add("attachmentEntries.attachmentEntry.room.building.name");
		purchaseInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(
				new ObjectUuidPK(purchaseId), selectorItemCollection);
		// 初始化editData中的相关数据，由于是后加载，必须把控件上的值也同步绑定上。
		editData.setOldPayType(purchaseInfo.getPayType());
		editData.setNewPayType(purchaseInfo.getPayType());
		editData.setPurchase(purchaseInfo);
		editData.setOldContractAmount(purchaseInfo.getContractTotalAmount());
		editData.setNewContractAmount(purchaseInfo.getContractTotalAmount());
		editData.setOldDealAmount(purchaseInfo.getDealAmount());
		editData.setNewDealAmount(purchaseInfo.getDealAmount());
		editData.setOldLoanAmount(purchaseInfo.getLoanAmount());
		editData.setNewLoanAmount(purchaseInfo.getLoanAmount());
		editData.setOldAccuFundAmount(purchaseInfo.getAccumulationFundAmount());
		editData.setNewAccuFundAmount(purchaseInfo.getAccumulationFundAmount());
		editData.setOldAttachmentAmount(purchaseInfo.getAttachmentAmount());
		editData.setNewAttachmentAmount(purchaseInfo.getAttachmentAmount());
		editData.setOldFitmentAmount(purchaseInfo.getFitmentAmount());
		editData.setNewFitmentAmount(purchaseInfo.getFitmentAmount());
		editData.setOldIsFitmentToContract(purchaseInfo.isIsFitmentToContract());
		editData.setNewIsFitmentToContract(purchaseInfo.isIsFitmentToContract());
		editData.setOldCompensateAmount(purchaseInfo.getAreaCompensateAmount());
		editData.setNewCompensateAmount(purchaseInfo.getAreaCompensateAmount());
		editData.setOldPriceAccountType(purchaseInfo.getPriceAccountType());
		editData.setNewPriceAccountType(purchaseInfo.getPriceAccountType());
		editData.setOldSpecialAgioType(purchaseInfo.getSpecialAgioType());
		editData.setOldPurchaseDate(purchaseInfo.getPurchaseDate());
		editData.setOldPlanSignDate(purchaseInfo.getPlanSignDate());
		editData.setNewSpecialAgioType(purchaseInfo.getSpecialAgioType()); //	
		editData.setNewDiscount(purchaseInfo.getSpecialAgio()); //
		editData.setOldDigit(purchaseInfo.getDigit());
		editData.setNewDigit(purchaseInfo.getDigit());
		

		if (editData.getPurchase().getSellType() != null) {
			editData.setOldSellType(purchaseInfo.getSellType());
			editData.setNewSellType(purchaseInfo.getSellType());
		}
		editData.setOldDiscount(purchaseInfo.getSpecialAgio());
		// ---特殊折扣不提供折上折，将原特殊折扣带过来 zhicheng_jin 081129
		editData.setReqDiscount(purchaseInfo.getSpecialAgio());
		// ----------------

		editData.setOldIsBasePriceSell(purchaseInfo.isIsBasePriceSell());
		editData.setNewIsBasePriceSell(purchaseInfo.isIsBasePriceSell());

		editData.setOldIsAutoToInteger(purchaseInfo.isIsAutoToInteger());
		editData.setNewIsAutoToInteger(purchaseInfo.isIsAutoToInteger());
		editData.setOldToIntegerType(purchaseInfo.getToIntegerType());
		editData.setNewToIntegerType(purchaseInfo.getToIntegerType());
		editData.setOldDigit(purchaseInfo.getDigit());
		editData.setNewDigit(purchaseInfo.getDigit());

		editData.setOldBuildingPrice(purchaseInfo.getContractBuildPrice());
		editData.setNewRoomPrice(purchaseInfo.getContractRoomPrice());
		editData.setOldDealBuildingPrice(purchaseInfo.getDealBuildPrice());
		editData.setOldDealRoomPrice(purchaseInfo.getDealRoomPrice());
		this.editData.getPurchase().setRoom(room);
		editData.setPurchase(purchaseInfo);
 
		initRoomInfo(purchaseInfo);
		initCustomerInfo(purchaseInfo);

		this.kDDateOldPurReqDate.setValue(purchaseInfo.getPurchaseDate());
		this.kDDateSigDate.setValue(purchaseInfo.getPlanSignDate());

		this.kDDateNewPurReqDate.setValue(purchaseInfo.getPurchaseDate());
		this.kDDateNewPurSigDate.setValue(purchaseInfo.getPlanSignDate());

		
		// 初始化付款明细
		PurchaseChangePayListOldEntryCollection payListOldEntryCollection = new PurchaseChangePayListOldEntryCollection();
		for (int i = 0; purchaseInfo.getPayListEntry() != null
				&& i < purchaseInfo.getPayListEntry().size(); i++) {
			PurchasePayListEntryInfo purchasePayListEntryInfo = purchaseInfo
					.getPayListEntry().get(i);
			PurchaseChangePayListOldEntryInfo purchaseChangePayListOldEntryInfo = new PurchaseChangePayListOldEntryInfo();
			changeObjectValue(purchasePayListEntryInfo,
					purchaseChangePayListOldEntryInfo);
			// --为使得已收款的付款明的ID不变，将认购单分录的ID存在变更单新付款明细分录的number字段上,便于后面对应
			// zhicheng_jin 090423
			purchaseChangePayListOldEntryInfo
					.setNumber(purchasePayListEntryInfo.getId().toString());
			// ------
			payListOldEntryCollection.add(purchaseChangePayListOldEntryInfo);
		}
		editData.getOldPayListEntrys().clear();
		editData.getOldPayListEntrys().addCollection(payListOldEntryCollection);

		// 初始化折扣
		PurchaseChangeAgioOldEntryCollection agioOldEntryCollection = new PurchaseChangeAgioOldEntryCollection();
		for (int i = 0; purchaseInfo.getAgioEntrys() != null
				&& i < purchaseInfo.getAgioEntrys().size(); i++) {
			PurchaseAgioEntryInfo purchaseAgioEntryInfo = purchaseInfo
					.getAgioEntrys().get(i);
			PurchaseChangeAgioOldEntryInfo purchaseChangeAgioOldEntryInfo = new PurchaseChangeAgioOldEntryInfo();
			changeObjectValue(purchaseAgioEntryInfo,
					purchaseChangeAgioOldEntryInfo);
			agioOldEntryCollection.add(purchaseChangeAgioOldEntryInfo);
		}
		editData.getOldDiscountEntrys().clear();
		editData.getOldDiscountEntrys().addCollection(agioOldEntryCollection);

		PurchaseChangeAgioEntryCollection agioEntryCollection = new PurchaseChangeAgioEntryCollection();
		for (int i = 0; purchaseInfo.getAgioEntrys() != null
				&& i < purchaseInfo.getAgioEntrys().size(); i++) {
			PurchaseAgioEntryInfo purchaseAgioEntryInfo = purchaseInfo
					.getAgioEntrys().get(i);
			PurchaseChangeAgioEntryInfo purchaseChangeAgioEntryInfo = new PurchaseChangeAgioEntryInfo();
			changeObjectValue(purchaseAgioEntryInfo,
					purchaseChangeAgioEntryInfo);
			agioEntryCollection.add(purchaseChangeAgioEntryInfo);
		}
		editData.getNewDiscountEntrys().clear();
		editData.getNewDiscountEntrys().addCollection(agioEntryCollection);

		// 附属房产
		PurchaseChangeRoomAttachmentEntryCollection attachmentEntryCollection = new PurchaseChangeRoomAttachmentEntryCollection();
		for (int i = 0; purchaseInfo.getAttachmentEntries() != null
				&& i < purchaseInfo.getAttachmentEntries().size(); i++) {
			PurchaseRoomAttachmentEntryInfo purchaseRoomAttachmentEntryInfo = purchaseInfo
					.getAttachmentEntries().get(i);
			PurchaseChangeRoomAttachmentEntryInfo purchaseChangeRoomAttachmentEntryInfo = new PurchaseChangeRoomAttachmentEntryInfo();
			changeObjectValue(purchaseRoomAttachmentEntryInfo,
					purchaseChangeRoomAttachmentEntryInfo);
			attachmentEntryCollection
					.add(purchaseChangeRoomAttachmentEntryInfo);
		}
		editData.getNewRoomAttachmentEntry().clear();
		editData.getNewRoomAttachmentEntry().addCollection(
				attachmentEntryCollection);

		PurchaseChangeRoomAttachmentOldEntryCollection attachmentOldEntryCollection = new PurchaseChangeRoomAttachmentOldEntryCollection();
		for (int i = 0; purchaseInfo.getAttachmentEntries() != null
				&& i < purchaseInfo.getAttachmentEntries().size(); i++) {
			PurchaseRoomAttachmentEntryInfo purchaseRoomAttachmentEntryInfo = purchaseInfo
					.getAttachmentEntries().get(i);
			PurchaseChangeRoomAttachmentOldEntryInfo purchaseChangeRoomAttachmentOldEntryInfo = new PurchaseChangeRoomAttachmentOldEntryInfo();
			changeObjectValue(purchaseRoomAttachmentEntryInfo,
					purchaseChangeRoomAttachmentOldEntryInfo);
			attachmentOldEntryCollection
					.add(purchaseChangeRoomAttachmentOldEntryInfo);
		}
		editData.getOldRoomAttachmentEntry().clear();
		editData.getOldRoomAttachmentEntry().addCollection(
				attachmentOldEntryCollection);
        this.isFromSellControl =true;
		
		this.loadFields();
		initPayList(purchaseInfo.getPayListEntry(), kDTNewPayList);
		initPayList(purchaseInfo.getPayListEntry(), kDTOldPayList);
		kDTOldPayList.getStyleAttributes().setLocked(true);
		addF7PayTypeFilter();
		initCompensateAmount();
		initOtherInfo();
		initOldAgioDescription();
		
		// 统一新旧折扣不统一问题 update by renliang
		if (this.txtEndOldAgio.getNumberValue() != null) {
			this.txtEndNewAgio.setValue(this.txtEndOldAgio.getNumberValue());
		}
		
	}

	public void initOtherInfo() {
		this.txtOldContractAmount.setValue(editData.getPurchase()
				.getContractTotalAmount());
		this.txtOldDealAmount.setValue(editData.getPurchase().getDealAmount());
		this.txtOldLoanAmount.setValue(editData.getPurchase().getLoanAmount());
		this.txtOldAccuFundAmount.setValue(editData.getPurchase()
				.getAccumulationFundAmount());
		this.txtOldFitmentAmount.setValue(editData.getPurchase()
				.getFitmentAmount());
		this.chkOldIsFitmentToContract.setSelected(editData.getPurchase()
				.isIsFitmentToContract());
		this.txtOldAttachmentAmount.setValue(editData.getPurchase()
				.getAttachmentAmount());
		this.kDCOldSellType.setSelectedItem(editData.getPurchase()
				.getSellType());
		this.prmtOldPayType.setData(editData.getPurchase().getPayType());
		this.txtOldDiscount.setValue(editData.getPurchase().getSpecialAgio());
		this.comboOldSpecialAgioType.setSelectedItem(editData.getPurchase()
				.getSpecialAgioType());
		this.comboDigit.setSelectedItem(purchaseInfo.getDigit());

		this.txtNewContractAmount.setValue(editData.getPurchase()
				.getContractTotalAmount());
		this.txtNewDealAmount.setValue(editData.getPurchase().getDealAmount());
		this.txtNewLoanAmount.setValue(editData.getPurchase().getLoanAmount());
		this.txtNewAccuFundAmount.setValue(editData.getPurchase()
				.getAccumulationFundAmount());
		this.txtNewFitmentAmount.setValue(editData.getPurchase()
				.getFitmentAmount());
		this.chkNewIsFitmentToContract.setSelected(editData.getPurchase()
				.isIsFitmentToContract());
		this.txtNewAttachmentAmount.setValue(editData.getPurchase()
				.getAttachmentAmount());
		this.kDCNewSellType.setSelectedItem(editData.getPurchase()
				.getSellType());
		this.prmtNewPayType.setData(editData.getPurchase().getPayType());
		this.txtNewDiscount.setValue(editData.getPurchase().getSpecialAgio());
		this.txtReqDiscount.setValue(editData.getPurchase().getSpecialAgio());
		this.comboSpecialAgioType.setSelectedItem(editData.getPurchase()
				.getSpecialAgioType());
		this.kDDateNewPurReqDate.setValue(purchaseInfo.getPurchaseDate());
		this.kDDateNewPurSigDate.setValue(editData.getPurchase()
				.getPlanSignDate());
		this.kDTextArea2.setText(kDTextArea1.getText());
	}

	public boolean canChange(RoomSellStateEnum roomState,
			RoomSellStateEnum[] state) {
		boolean canOprt = false;
		for (int i = 0; i < state.length; i++) {
			if (roomState.equals(state[i])) {
				return true;
			}
		}
		return canOprt;
	}

	public RoomSellStateEnum[] getCanChangeState() {
		return new RoomSellStateEnum[] { RoomSellStateEnum.Purchase,
				RoomSellStateEnum.Sign };
	}

	public void storeFields() {
		super.storeFields();
		if ("writeNewAgio".equals(oprtState) && onLoaded) {
			if (txtNewDiscount.getBigDecimalValue() == null) {
				txtNewDiscount.requestFocus();
				MsgBox.showWarning("请录入核准特殊折扣！");
				abort();
			}
		}
		RoomInfo roomInfo = null;
		if (this.editData.getPurchase() == null) {

		} else {
			roomInfo = this.editData.getPurchase().getRoom();
		}

		SellTypeEnum sellType = (SellTypeEnum) this.kDCNewSellType
				.getSelectedItem();

		BigDecimal fitmentAmount = (BigDecimal) this.txtNewFitmentAmount
				.getNumberValue();
		if (fitmentAmount == null)
			fitmentAmount = FDCHelper.ZERO;
		BigDecimal attachmentAmount = (BigDecimal) this.txtNewAttachmentAmount
				.getNumberValue();
		if (attachmentAmount == null)
			attachmentAmount = FDCHelper.ZERO;
		BigDecimal areaCompensateAmount = (BigDecimal) this.txtNewCompensateAmount
				.getNumberValue();
		if (areaCompensateAmount == null)
			areaCompensateAmount = FDCHelper.ZERO;
		SpecialAgioEnum splType = (SpecialAgioEnum) this.comboSpecialAgioType
				.getSelectedItem();
		boolean isFitmentToContract = this.chkNewIsFitmentToContract
				.isSelected();
		BigDecimal splAgio = (BigDecimal) this.txtNewDiscount.getNumberValue();

//		PurchaseParam purParam = AgioSelectUI.getPurchaseAgioParam(
//				this.newAgioParam, roomInfo, sellType, isFitmentToContract,
//				fitmentAmount, attachmentAmount, areaCompensateAmount, splType,
//				splAgio);
//		this.editData.setNewBuildingPrice(purParam.getContractBuildPrice());
//		this.editData.setNewRoomPrice(purParam.getContractRoomPrice());
//		this.editData.setNewDealBuildingPrice(purParam.getDealBuildPrice());
//		this.editData.setNewDealRoomPrice(purParam.getDealRoomPrice());
//		this.editData.setOldDealPrice((BigDecimal) this.txtOldDealAmount
//				.getNumberValue());
//		if(editData.getPurchase() != null && editData.getPurchase().getRoom() != null){
//			if(editData.getPurchase().getRoom().isIsCalByRoomArea()){
//				this.editData.setNewDealPrice(purParam.getDealRoomPrice());
//			}else{
//				this.editData.setNewDealPrice(purParam.getDealBuildPrice());
//			}
//		}
		
		

		// this.editData.setNewIsPriceSetStand(newIsPriceSetStand);
		this.editData.setNewPriceAccountType(this.newAgioParam
				.getPriceAccountType());
		this.editData.setNewIsAutoToInteger(this.newAgioParam.isToInteger());
		this.editData
				.setNewIsBasePriceSell(this.newAgioParam.isBasePriceSell());
		this.editData.setNewToIntegerType(this.newAgioParam.getToIntegerType());
		this.editData.setNewDigit(this.newAgioParam.getDigit());

		this.editData
				.setOldSpecialAgioType((SpecialAgioEnum) this.comboOldSpecialAgioType
						.getSelectedItem());
		this.editData
				.setNewSpecialAgioType((SpecialAgioEnum) this.comboSpecialAgioType
						.getSelectedItem());
		this.editData.setNewDiscount(this.txtNewDiscount.getBigDecimalValue());
		this.editData.setReqDiscount(this.txtReqDiscount.getBigDecimalValue());

		this.editData.setOldPurchaseDate((Date) this.kDDateOldPurReqDate
				.getValue());
		this.editData.setOldPlanSignDate((Date) this.kDDateSigDate.getValue());

		// 付款明细
		PurchaseChangePayListEntryCollection payListEntryCollection = new PurchaseChangePayListEntryCollection();
		for (int i = 0; i < kDTNewPayList.getRowCount(); i++) {
			IRow row = kDTNewPayList.getRow(i);

			PurchaseChangePayListEntryInfo purchaseChangePayListEntryInfo;
			if (row.getUserObject() instanceof PurchaseChangePayListEntryInfo) {
				purchaseChangePayListEntryInfo = (PurchaseChangePayListEntryInfo) row
						.getUserObject();
			} else {
				purchaseChangePayListEntryInfo = new PurchaseChangePayListEntryInfo();
			}

			purchaseChangePayListEntryInfo.setMoneyDefine((MoneyDefineInfo) row
					.getCell("moneyName").getValue());
			purchaseChangePayListEntryInfo.setAppDate((Date) row
					.getCell("date").getValue());
			purchaseChangePayListEntryInfo.setAppAmount((BigDecimal) row
					.getCell("amount").getValue());
			purchaseChangePayListEntryInfo.setCurrency((CurrencyInfo) row
					.getCell("currency").getValue());
			if (row.getCell("term").getValue() != null) {
				purchaseChangePayListEntryInfo.setTerm(((Integer) row.getCell(
						"term").getValue()).intValue());
			}
			if (row.getCell("jiange").getValue() != null) {
				purchaseChangePayListEntryInfo.setMonthInterval(((Integer) row
						.getCell("jiange").getValue()).intValue());
			}
			if (row.getCell("des").getValue() == null) {
				purchaseChangePayListEntryInfo.setDescription(null);
			} else {
				purchaseChangePayListEntryInfo.setDescription(row
						.getCell("des").getValue().toString());
			}
			purchaseChangePayListEntryInfo.setSeq(i);
			payListEntryCollection.add(purchaseChangePayListEntryInfo);
		}
		editData.getNewPayListEntrys().clear();
		editData.getNewPayListEntrys().addCollection(payListEntryCollection);
	}

	private void addNewPayListRow(int rowIndex) {
		PurchaseChangePayListEntryInfo entry = new PurchaseChangePayListEntryInfo();
		CurrencyInfo currency = null;
		if(purchaseInfo != null){
			currency = purchaseInfo.getDealCurrency();
		}
		entry.setCurrency(currency);
		entry.setTerm(1);
		entry.setMonthInterval(1);
		IRow row = this.kDTNewPayList.addRow(rowIndex);
		row.setUserObject(entry);
		row.getCell("moneyName").setValue(entry.getMoneyDefine());
		row.getCell("date").setValue(entry.getAppDate());
		row.getCell("amount").setValue(entry.getAppAmount());
		row.getCell("currency").setValue(entry.getCurrency());
		row.getCell("term").setValue(new Integer(entry.getTerm()));
		row.getCell("jiange").setValue(new Integer(entry.getMonthInterval()));
		row.getCell("des").setValue(entry.getDescription());
		BigDecimal actAmount = entry.getActRevAmount();
		if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) != 0) {
			row.getStyleAttributes().setBackground(
					FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			row.getCell("moneyName").getStyleAttributes().setLocked(true);
			row.getCell("date").getStyleAttributes().setLocked(true);
			row.getCell("amount").getStyleAttributes().setLocked(true);
			row.getCell("currency").getStyleAttributes().setLocked(true);
			row.getCell("term").getStyleAttributes().setLocked(true);
			row.getCell("jiange").getStyleAttributes().setLocked(true);
		}
	}

	// 增加
	protected void kDWorkButton2_actionPerformed(ActionEvent e)
			throws Exception {
		addNewPayListRow(this.kDTNewPayList.getRowCount());
	}

	// 插入
	protected void kDWorkButton5_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(kDTNewPayList);
		if (row == null)
			return;
		addNewPayListRow(row.getRowIndex());
		
	}

	// 删除
	protected void kDWorkButton4_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(kDTNewPayList);
		if (row == null || row.getStyleAttributes().isLocked()) {
			return;
		}
		PurchaseChangePayListEntryInfo entry = (PurchaseChangePayListEntryInfo) row
				.getUserObject();
		BigDecimal actAmount = entry.getActRevAmount();
		if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) != 0) {
			MsgBox.showInfo("该分录已经付款,不能删除!");
			return;
		}

		kDTNewPayList.removeRow(row.getRowIndex());
	}

	// 更新付款明细
	private void updatePayList() throws BOSException {
		// 删除未付过款的明细
		List toDelRowIds = new ArrayList();
		for (int i = 0; i < this.kDTNewPayList.getRowCount(); i++) {
			IRow row = this.kDTNewPayList.getRow(i);
			if (row.getStyleAttributes().isLocked())
				continue;
			PurchaseChangePayListEntryInfo entry = (PurchaseChangePayListEntryInfo) row
					.getUserObject();
			BigDecimal actAmount = entry.getActRevAmount();
			// BigDecimal apAmount = entry.getApAmount();
			if (actAmount == null) {
				toDelRowIds.add(new Integer(i));
			}
		}
		for (int i = toDelRowIds.size() - 1; i >= 0; i--) {
			Integer tmp = (Integer) toDelRowIds.get(i);
			this.kDTNewPayList.removeRow(tmp.intValue());
		}

		SHEPayTypeInfo payType = (SHEPayTypeInfo) prmtNewPayType.getValue();
		if (payType != null) {
			PayListEntryCollection payList = PayListEntryFactory
					.getRemoteInstance().getPayListEntryCollection(
							"select *,moneyDefine.*,currency.* where head = '"
									+ payType.getId().toString()
									+ "' order by seq");

			BigDecimal eareatMoney = FDCHelper.ZERO;
			BigDecimal remain = this.txtNewContractAmount.getBigDecimalValue();
			if (remain == null) {
				remain = FDCHelper.ZERO;
			}
			List toAddRowPurEntry = new ArrayList();
			boolean[] hasRelated = new boolean[kDTNewPayList.getRowCount()];// 标识现有各行是否已被关联

			for (int i = 0; i < payList.size(); i++) {
				PayListEntryInfo entry = payList.get(i);
				MoneyDefineInfo moneyType = (MoneyDefineInfo) entry.getMoneyDefine();
				if(moneyType.getMoneyType() == MoneyTypeEnum.ReplaceFee){
					continue;
				}
				PurchaseChangePayListEntryInfo purEntry = new PurchaseChangePayListEntryInfo();
				Date curDate = new Date(); // 应付日期 根据付款方案的时间： ‘签认购书’ ‘或 指定时间’
				if (entry.getBizTime().equals(BizTimeEnum.AppTime)) {
					curDate = entry.getAppDate();
				} else {
					curDate = new Date();
					int monthLimit = entry.getMonthLimit();
					int dayLimit = entry.getDayLimit();
					Calendar cal = Calendar.getInstance();
					cal.setTime(curDate);
					cal.add(Calendar.MONTH, monthLimit);
					cal.add(Calendar.DATE, dayLimit);
					curDate = cal.getTime();
				}
				purEntry.setAppDate(curDate);

				BigDecimal amount = FDCHelper.ZERO;
				if (entry.getValue() != null) { // 应付金额 看付款方案的金额是否设定
												// ，如果设定了就按设定值，否则按比例值算
					amount = entry.getValue();
				} else {
					BigDecimal proportion = entry.getProportion();
					amount = this.txtNewContractAmount.getBigDecimalValue();
					if (amount == null) {
						amount = FDCHelper.ZERO;
					}
					amount = amount.multiply(proportion).divide(
							new BigDecimal("100.0"), 3,
							BigDecimal.ROUND_HALF_UP);
				}
				MoneyDefineInfo moneyDefine = entry.getMoneyDefine();
				if (moneyDefine.getMoneyType().equals(
						MoneyTypeEnum.EarnestMoney)) {
					eareatMoney = eareatMoney.add(amount);
				} else {
					if (isEarnestInHouseAmount) {
						amount = amount.subtract(eareatMoney);
					} else {
					}
					eareatMoney = FDCHelper.ZERO;
					if (amount.compareTo(FDCHelper.ZERO) < 0) {
						amount = FDCHelper.ZERO;
					}
				}

				// 定金不计入房款时，对于定金款项，不从剩余金额 remain中减
				if (!isEarnestInHouseAmount
						&& moneyDefine.getMoneyType().equals(
								MoneyTypeEnum.EarnestMoney)) {
				} else {
					if (amount.compareTo(remain) > 0) {
						amount = remain;
						remain = FDCHelper.ZERO;
					} else {
						remain = remain.subtract(amount);
					}
				}

				if (i == payList.size() - 1) {
					amount = amount.add(remain);
				}
				purEntry.setAppAmount(amount);

				purEntry.setMoneyDefine(moneyDefine);
				purEntry.setCurrency(entry.getCurrency());
				purEntry.setTerm(entry.getTerm());
				purEntry.setMonthInterval(entry.getMonthInterval());

				// 付款方案中的 明细如果不存在 则增加 ；否则修改现有的同款项明细
				boolean isExist = false;
				for (int j = 0; j < kDTNewPayList.getRowCount(); j++) {
					if (hasRelated[j]) {
						continue;
					}

					IRow row = kDTNewPayList.getRow(j);

					PurchaseChangePayListEntryInfo oldEntry = (PurchaseChangePayListEntryInfo) row
							.getUserObject();
					// 新付款计划中与已收款的付款分录有对应项
					if (oldEntry.getMoneyDefine().getMoneyType().equals(
							moneyDefine.getMoneyType())) {
						hasRelated[j] = true;
						isExist = true;
						BigDecimal actPayAmount = oldEntry.getActRevAmount();
						if (actPayAmount == null) {
							actPayAmount = FDCHelper.ZERO;
						}
						if (oldEntry.getAppAmount().compareTo(actPayAmount) == 0) {
							break;
						}
						if (oldEntry.getActRevAmount() == null
								|| amount.compareTo(actPayAmount) > 0) {
							purEntry
									.setActRevAmount(oldEntry.getActRevAmount());
							// --为实现使得已收款的付款明的ID不变 zhicheng_jin 090423
							purEntry.setNumber(oldEntry.getNumber());
							// --
							row.setUserObject(purEntry);
							row.getCell("date").setValue(purEntry.getAppDate());
							row.getCell("amount").setValue(
									purEntry.getAppAmount());
							row.getCell("currency").setValue(
									purEntry.getCurrency());
							row.getCell("term").setValue(
									new Integer(purEntry.getTerm()));
							row.getCell("jiange").setValue(
									new Integer(purEntry.getMonthInterval()));
							row.getCell("des").setValue(
									purEntry.getDescription());
						}
						break;
					}
				}
				if (!isExist) {
					toAddRowPurEntry.add(purEntry);
				}
				// addPayListEntryRow(purEntry);
			}
			for (int i = 0; i < toAddRowPurEntry.size(); i++) {
				addPayListEntryRow((PurchaseChangePayListEntryInfo) toAddRowPurEntry
						.get(i));
			}
			verifyAndAdjustPayList();
			/*
			 * ---增加千位调整后,默认不再进行万位调整 zhicheng_jin 090311
			 * addLittleLoanAmountToFisrtAmount(); ---------
			 */
			updateLoanAndAFAmount();
		}
	}

	// 当合同总价和付款明细的金额合计不相等时 调整用的
	private void verifyAndAdjustPayList() {
		BigDecimal contractAmount = this.txtNewContractAmount
				.getBigDecimalValue();

		BigDecimal count = FDCHelper.ZERO;
		IRow loanRow = null; // 贷款行
		IRow lastRow = null; // 最后一行
		for (int i = 0; i < kDTNewPayList.getRowCount(); i++) {
			IRow row = kDTNewPayList.getRow(i);
			PurchaseChangePayListEntryInfo oldEntry = (PurchaseChangePayListEntryInfo) row
					.getUserObject();
			BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
			//添加  有预定金也不要计算在内xin_wang 2010.12.02
			if (MoneyTypeEnum.PreconcertMoney.equals(oldEntry.getMoneyDefine().getMoneyType())||(!isEarnestInHouseAmount
					&& oldEntry.getMoneyDefine().getMoneyType().equals(
							MoneyTypeEnum.EarnestMoney))) {

			} else {
				count = count.add(amount);
			}
			if (oldEntry.getMoneyDefine().getMoneyType().equals(
					MoneyTypeEnum.LoanAmount)) {
				loanRow = row;
			}
			if (i == kDTNewPayList.getRowCount() - 1) {
				lastRow = row;
			}
		}

		BigDecimal diff = contractAmount.subtract(count);
		if (diff.compareTo(FDCHelper.ZERO) != 0) { // 如果不相等
			IRow toAdjustRow = loanRow;
			if (loanRow != null) {
				PurchaseChangePayListEntryInfo loanPay = (PurchaseChangePayListEntryInfo) loanRow
						.getUserObject();
				BigDecimal actPayAmount = loanPay.getActRevAmount();
				if (actPayAmount == null) {
					actPayAmount = FDCHelper.ZERO;
				}
				if (loanPay.getAppAmount().compareTo(actPayAmount) == 0) {
					toAdjustRow = lastRow;
				}
			}

			if (toAdjustRow == null) {
				logger.warn("全空了还收鬼的钱呀.");
				return;
			}

			BigDecimal oldLoanAmount = (BigDecimal) toAdjustRow.getCell(
					"amount").getValue();
			BigDecimal toLoanAmount = oldLoanAmount.add(diff);
			if (toLoanAmount.compareTo(FDCHelper.ZERO) < 0) {
				toLoanAmount = FDCHelper.ZERO;
			}
			toAdjustRow.getCell("amount").setValue(toLoanAmount);
		}
		verifyBalance();
	}

	// 按揭贷款，公积金
	private void updateLoanAndAFAmount() {
		BigDecimal loanAmount = FDCHelper.ZERO;
		BigDecimal afAmount = FDCHelper.ZERO;
		for (int i = 0; i < this.kDTNewPayList.getRowCount(); i++) {
			IRow row = this.kDTNewPayList.getRow(i);
			if (row.getStyleAttributes().isLocked())
				continue;
			if (!(row.getCell("moneyName").getValue() instanceof MoneyDefineInfo)) {
				continue;
			}
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(
					"moneyName").getValue();
			BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
			if (amount == null) {
				amount = FDCHelper.ZERO;
			}
			if (moneyName != null) {
				if (moneyName.getMoneyType().equals(MoneyTypeEnum.LoanAmount)) {
					loanAmount = loanAmount.add(amount);
				} else if (moneyName.getMoneyType().equals(
						MoneyTypeEnum.AccFundAmount)) {
					afAmount = afAmount.add(amount);
				}
			}
		}
		this.txtNewLoanAmount.setValue(loanAmount);
		this.txtNewAccuFundAmount.setValue(afAmount);
	}

	/**
	 * 如果按按揭款大于1W，将按揭款的尾数(万以下)加入到首期款中 modified by zhicheng_jin 2008-09-09
	 */
	private void addLittleLoanAmountToFisrtAmount() {
		BigDecimal totalLittleLoanAmount = FDCHelper.ZERO;
		IRow firstAmountRow = null;
		IRow lastRowBeforeLoan = null;
		BigDecimal littleUnit = null;// 尾数标准,可能改为可配置
		Object digit = this.comboDigit.getSelectedItem();
		if (digit.equals(SHEHelper.THOUSHAND_DIGIT)) {
			littleUnit = new BigDecimal("1000");
		} else {
			littleUnit = new BigDecimal("10000");
		}
		Map toReducedRows = new HashMap();
		boolean beforeLoan = true;
		for (int i = 0; i < this.kDTNewPayList.getRowCount(); i++) {
			IRow row = this.kDTNewPayList.getRow(i);
			if (row.getStyleAttributes().isLocked())
				continue;
			Object obj = row.getCell("moneyName").getValue();
			if (!(obj instanceof MoneyDefineInfo)) {
				continue;
			}
			MoneyDefineInfo moneyDefineInfo = (MoneyDefineInfo) obj;

			MoneyTypeEnum moneyType = moneyDefineInfo.getMoneyType();
			if (moneyType.equals(MoneyTypeEnum.FisrtAmount)) {
				firstAmountRow = row;
			} else if (moneyType.equals(MoneyTypeEnum.LoanAmount)) {
				beforeLoan = false;
				BigDecimal loanAmount = (BigDecimal) row.getCell("amount")
						.getValue();
				if (loanAmount.compareTo(littleUnit) <= 0) {
					continue;
				}
				BigDecimal littleAmount = SHEHelper.remainder(loanAmount,
						littleUnit);
				toReducedRows.put(row, loanAmount.subtract(littleAmount));
				totalLittleLoanAmount = totalLittleLoanAmount.add(littleAmount);
			} else if (moneyType.equals((MoneyTypeEnum.AccFundAmount))) {// 公积金
				beforeLoan = false;
				BigDecimal accFundAmount = (BigDecimal) row.getCell("amount")
						.getValue();
				if (accFundAmount.compareTo(littleUnit) <= 0) {
					continue;
				}
				BigDecimal littleAmount = SHEHelper.remainder(accFundAmount,
						littleUnit);
				toReducedRows.put(row, accFundAmount.subtract(littleAmount));
				totalLittleLoanAmount = totalLittleLoanAmount.add(littleAmount);
			} else {
				if (beforeLoan)
					lastRowBeforeLoan = row;
			}
		}
		// 将按揭的尾数加入到首期款上
		// 如果没有首期款项,则将尾数加到按揭,公积金款项前的最后一行
		IRow toAddedRow = firstAmountRow != null ? firstAmountRow
				: lastRowBeforeLoan;

		if (toAddedRow != null) {
			PurchaseChangePayListEntryInfo firstPay = (PurchaseChangePayListEntryInfo) toAddedRow
					.getUserObject();
			// 如果首期已经付清，则不再做变化
			BigDecimal actPayAmount = firstPay.getActRevAmount();
			if (actPayAmount == null) {
				actPayAmount = FDCHelper.ZERO;
			}
			if (firstPay.getAppAmount().compareTo(actPayAmount) == 0) {
				return;
			}
			Set to = toReducedRows.keySet();
			for (Iterator itor = to.iterator(); itor.hasNext();) {
				IRow row = (IRow) itor.next();
				BigDecimal value = (BigDecimal) toReducedRows.get(row);
				row.getCell("amount").setValue(value);
			}

			BigDecimal srcFirstAmount = (BigDecimal) toAddedRow.getCell(
					"amount").getValue();
			toAddedRow.getCell("amount").setValue(
					srcFirstAmount.add(totalLittleLoanAmount));
		}
	}

	// 添加付款明细行
	private void addPayListEntryRow(PurchaseChangePayListEntryInfo entry) {
		kDTNewPayList.checkParsed();

		IRow row = this.kDTNewPayList.addRow();
		row.setUserObject(entry);
		row.getCell("moneyName").setValue(entry.getMoneyDefine());
		row.getCell("date").setValue(entry.getAppDate());
		row.getCell("amount").setValue(entry.getAppAmount());
		row.getCell("currency").setValue(entry.getCurrency());
		row.getCell("term").setValue(new Integer(entry.getTerm()));
		row.getCell("jiange").setValue(new Integer(entry.getMonthInterval()));
		row.getCell("des").setValue(entry.getDescription());
		BigDecimal apAmount = entry.getAppAmount();
		BigDecimal actAmount = entry.getActRevAmount();
		if (apAmount == null) {
			apAmount = FDCHelper.ZERO;
		}
		if (actAmount == null) {
			actAmount = FDCHelper.ZERO;
		}
		if (actAmount.compareTo(FDCHelper.ZERO) != 0) {
			row.getStyleAttributes().setBackground(
					FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			row.getCell("moneyName").getStyleAttributes().setLocked(true);
			row.getCell("date").getStyleAttributes().setLocked(true);
			row.getCell("currency").getStyleAttributes().setLocked(true);
			row.getCell("term").getStyleAttributes().setLocked(true);
			row.getCell("jiange").getStyleAttributes().setLocked(true);
			if (actAmount.compareTo(apAmount) == 0) {
				row.getCell("amount").getStyleAttributes().setLocked(true);

			}
		}
	}

	// 折扣选择
	protected void kDWorkButton1_actionPerformed(ActionEvent e)
			throws Exception {
		
		this.newAigio = this.txtEndNewAgio.getNumberValue();
		RoomInfo room =null;
		if(purchaseInfo != null){
			room = purchaseInfo.getRoom();
		}
//		RoomInfo room = purchaseInfo.getRoom();
		if (room == null) {
			MsgBox.showInfo("请先选择房间!");
			return;
		}

		AgioEntryCollection agioEntryColl = new AgioEntryCollection();
		for (int i = 0; i < editData.getNewDiscountEntrys().size(); i++)
			agioEntryColl.add(editData.getNewDiscountEntrys().get(i));

//		AgioParam agioParam = AgioSelectUI
//				.showUI(this, room.getId().toString(), agioEntryColl,
//						this.newAgioParam, true);
//
//		if (agioParam != null) {
//			this.newAgioParam = agioParam;
//
//			if (agioParam.getAgios() != null) {
//				editData.getNewDiscountEntrys().clear();
//				for (int i = 0; i < agioParam.getAgios().size(); i++) {
//					PurchaseChangeAgioEntryInfo agioInfo = (PurchaseChangeAgioEntryInfo) agioParam
//							.getAgios().get(i);
//					agioInfo.setHead(this.editData);
//					editData.getNewDiscountEntrys().add(agioInfo);
//				}
//
//				vitfyAgio(txtNewDiscount);
//
//				this.comboPriceAccount.setSelectedItem(this.newAgioParam
//						.getPriceAccountType());
//				countLastestDealAmount();
//			}
//		}
		this.txtEndNewAgio.setPrecision(2);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verityBeforSubmit();

		super.actionSubmit_actionPerformed(e);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("priceAccountType");
		PurchaseFactory.getRemoteInstance().updatePartial(
				this.editData.getPurchase(), sels);
		this.btnAudit.setVisible(true);
		this.btnAudit.setEnabled(true);
		this.setOprtState(STATUS_VIEW);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		verityBeforSubmit();

		super.actionSave_actionPerformed(e);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("priceAccountType");
		PurchaseFactory.getRemoteInstance().updatePartial(
				this.editData.getPurchase(), sels);
	}

	private void verityBeforSubmit() throws Exception {
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable()
				&& StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("变更单业务编号不能为空!");
			this.abort();
		}
		if (this.txtName.isEnabled() && this.txtName.isEditable()
				&& StringUtils.isEmpty(this.txtName.getText())) {
			MsgBox.showInfo("变更单名称不能为空!");
			this.abort();
		}
	
		/**
		 * 判断房间是否为空
		 * @author liang_ren969
		 * @date at 2010-12-21
		 */
		FDCClientVerifyHelper.verifyEmpty(this, this.txtRoomNumber);
	
		updateLoanAndAFAmount();

		if (this.newAgioParam.isBasePriceSell()) {
			if (MsgBox.showConfirm2New(this, "已经是底价销售，确认要打折/优惠吗?") == MsgBox.NO) {
				SysUtil.abort();
			}
		}
		PurchaseChangeInfo purChangeInfo = this.editData;
		PurchaseInfo purInfo = purChangeInfo.getPurchase();
		purInfo.setPriceAccountType(this.newAgioParam.getPriceAccountType());
		RoomLoanCollection roomLoan = RoomLoanFactory.getRemoteInstance()
				.getRoomLoanCollection(
						"where purchase = '" + purInfo.getId().toString()
								+ "' and aFMortgagedState = 1");
		if (roomLoan != null && roomLoan.size() > 0) {
			MsgBox.showWarning("认购单已经生成公积金/按揭单，不能再进行变更！");
			SysUtil.abort();
			return;
		}
	}

	// 附属房产
	protected void kDWorkButton3_actionPerformed(ActionEvent e)
			throws Exception {
		if(purchaseInfo==null){
			MsgBox.showInfo("请先选择房间!");
			return;
		}
		RoomInfo room = purchaseInfo.getRoom();
		if (room == null) {
			MsgBox.showInfo("请先选择房间!");
			return;
		}
		String roomId = room.getId().toString();
		if (roomId != null) {
			PurchaseChangeRoomAttachmentEntryCollection resList = PurchaseChangeRoomBindUI
					.showEditUI(this, roomId, editData
							.getNewRoomAttachmentEntry());
			if (resList != null) {
				editData.getNewRoomAttachmentEntry().clear();
				editData.getNewRoomAttachmentEntry().addCollection(resList);
				initNewRoomAttachment(resList);
				countLastestDealAmount();
			}
		}
	}

	// 初始化新的附属房产信息
	void initNewRoomAttachment(AbstractObjectCollection collection) {
		BigDecimal attachAmount = FDCHelper.ZERO;
		String attText = "";
		for (int i = 0; i < collection.size(); i++) {
			IObjectValue aRoom = collection.getObject(i);
			if (aRoom.get("attachmentEntry") instanceof RoomAttachmentEntryInfo) {
				attText += ((RoomAttachmentEntryInfo) aRoom
						.get("attachmentEntry")).getRoom().getNumber()
						+ " ";
			}

			if (aRoom.get("mergeAmount") instanceof BigDecimal) {
				attachAmount = attachAmount.add((BigDecimal) aRoom
						.get("mergeAmount"));
			}
		}

		txtNewAttachmentAmount.setValue(attachAmount.setScale(2,
				BigDecimal.ROUND_HALF_UP));
		txtNewAttachmentDescription.setText(attText);

	}

	// 初始化旧的附属房产信息
	void initOldRoomAttachment(AbstractObjectCollection collection) {
		BigDecimal attachAmount = FDCHelper.ZERO;
		String attText = "";
		for (int i = 0; i < collection.size(); i++) {
			IObjectValue aRoom = collection.getObject(i);
			if (aRoom.get("attachmentEntry") instanceof RoomAttachmentEntryInfo) {
				attText += ((RoomAttachmentEntryInfo) aRoom
						.get("attachmentEntry")).getRoom().getNumber()
						+ " ";
			}

			if (aRoom.get("mergeAmount") instanceof BigDecimal) {
				attachAmount = attachAmount.add((BigDecimal) aRoom
						.get("mergeAmount"));
			}
		}

		txtOldAttachmentAmount.setValue(attachAmount.setScale(2,
				BigDecimal.ROUND_HALF_UP));
		txtOldAttachmentDescription.setText(attText);

	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			this.kDTNewPayList.getStyleAttributes().setLocked(false);
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			this.kDTNewPayList.getStyleAttributes().setLocked(false);
		} else if (STATUS_VIEW.equals(this.oprtState)) {
			this.kDTNewPayList.getStyleAttributes().setLocked(true);
		} else if (STATUS_FINDVIEW.equals(this.oprtState)) {
			this.kDTNewPayList.getStyleAttributes().setLocked(true);
		} else if (STATUS_WRITENEWAGIO.equals(this.oprtState)) {
			this.kDTNewPayList.getStyleAttributes().setLocked(false);
			this.kDWorkButton2.setEnabled(true);
			this.kDWorkButton5.setEnabled(true);
			this.kDWorkButton4.setEnabled(true);
		}

		if (STATUS_VIEW.equals(this.oprtState)
				|| STATUS_FINDVIEW.equals(this.oprtState)) {
			this.comboDigit.setVisible(false);
			this.btnWAdjust.setVisible(false);
		} else {
			this.comboDigit.setVisible(true);
			this.btnWAdjust.setVisible(true);
		}
	}

	protected void btnWAdjust_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		addLittleLoanAmountToFisrtAmount();
		// --万位调整时也要更新按揭金额和公积金金额 by zhicheng_jin 2009-06-10
		updateLoanAndAFAmount();
	}

	// 合同金額小于累計已付款不允許提交
	// zhicheng_jin modify:增加其他应收款项后，其他应收是不计入合同总价的。该校验不正确。
	// 实际上变更中，已收款的明细行，变更后的应付金额不能小于实付。故该校验可以省略。
	public void verifyDealAmount() throws BOSException {
		BigDecimal total = new BigDecimal("0");
		ReceivingBillCollection receiveBillCollection = ReceivingBillFactory
				.getRemoteInstance().getReceivingBillCollection(
						"where fdcReceiveBill.purchase = '"
								+ purchaseInfo.getId().toString() + "'");
		for (int i = 0; i < receiveBillCollection.size(); i++) {
			ReceivingBillInfo receivingBillInfo = receiveBillCollection.get(i);
			total = total.add(receivingBillInfo.getActRecAmt());
		}
		if (total.compareTo(this.txtNewContractAmount.getBigDecimalValue()) > 0) {
			MsgBox.showWarning("合同金额小于累计已付款("
					+ total.setScale(2, BigDecimal.ROUND_HALF_UP)
					+ ")，不允许保存和提交！");
			abort();
		}
		verifyBalance();
	}

	protected KDTable getDetailTable() {
		// TODO 自动生成方法存根
		return null;
	}

	// 校验折扣最大值
	private void vitfyAgio(KDFormattedTextField txtDiscount) {
		if (txtDiscount.getBigDecimalValue() == null) {
			return;
		}
		BigDecimal agioValue = txtDiscount.getBigDecimalValue();
		if (SpecialAgioEnum.DaZhe.equals(this.comboSpecialAgioType
				.getSelectedItem())) {
			if (agioValue.compareTo(new BigDecimal(100)) == 1) {
				MsgBox.showInfo("打折最大折扣不能超过100");
				txtDiscount.setValue(null);
				abort();
			}
			if (agioValue.compareTo(new BigDecimal(0)) == -1
					|| agioValue.compareTo(new BigDecimal(0)) == 0) {
				MsgBox.showInfo("打折折扣不能小于0");
				txtDiscount.setValue(null);
				abort();
			}
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		verifyBalance();
		super.actionEdit_actionPerformed(e);
		if ("LISTUI".equals(src)) {
			this.btnChooseRoom.setEnabled(true);
		} else {
			this.btnChooseRoom.setEnabled(false);
		}
		initNewPayListColumnEditor();
		this.txtNewDiscount.setEnabled(false);
		for (int i = 0; i < this.kDTNewPayList.getRowCount(); i++) {
			IRow row = this.kDTNewPayList.getRow(i);
			PurchaseChangePayListEntryInfo entry = (PurchaseChangePayListEntryInfo) row
					.getUserObject();
			BigDecimal actAmount = entry.getActRevAmount();
			if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) != 0) {
				row.getStyleAttributes().setLocked(true);
				row.getCell("amount").getStyleAttributes().setLocked(false);
			}
			if(entry.getAppAmount()!=null && entry.getActRevAmount()!=null ){
				if( entry.getAppAmount().compareTo(entry.getActRevAmount()) == 0){
					row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
				}
			}
			/**
			 * 加上非空的判断
			 * by renliang at 2010-11-15
			 */
			BigDecimal actRevAmount = new BigDecimal("0.00");
			BigDecimal appAmount = new BigDecimal("0.00");
			
			if(entry.getAppAmount()!=null){
				appAmount = entry.getAppAmount();
			}
			if(entry.getActRevAmount()!=null){
				actRevAmount = entry.getActRevAmount();
			}	
			
			if(appAmount.compareTo(actRevAmount) == 0){
				row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				row.getStyleAttributes().setLocked(true);

			}
			
			if(MoneyTypeEnum.PreconcertMoney.equals(entry.getMoneyDefine().getMoneyType())){
				row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				row.getStyleAttributes().setLocked(true);
			}
			
		}

	}

	protected void comboSpecialAgioType_itemStateChanged(ItemEvent e)
			throws Exception {
		this.txtNewDiscount.setValue(null);
		this.txtReqDiscount.setValue(null);
	}

	protected void kDTNewPayList_editStopped(KDTEditEvent e) throws Exception {
		super.kDTNewPayList_editStopped(e);
		PurchaseInfo purchase = editData.getPurchase();
		if (editData.getPurchase() != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			purchase = PurchaseFactory.getRemoteInstance().getPurchaseInfo(
					new ObjectUuidPK(editData.getPurchase().getId()), sic);
		}
		if (purchase != null) {
			boolean isEarnestInHouseAmount = purchase
					.isIsEarnestInHouseAmount();
			BigDecimal contractAmount = this.txtNewContractAmount
					.getBigDecimalValue();
			if (contractAmount != null && e.getRowIndex() >= 0) {
				IRow changedRow = this.kDTNewPayList.getRow(e.getRowIndex());
				BigDecimal changedAmount = (BigDecimal) changedRow.getCell(
						"amount").getValue();
				if (changedAmount == null)
					changedAmount = FDCHelper.ZERO;
				IRow nextRow = this.kDTNewPayList.getRow(e.getRowIndex() + 1);
				if (nextRow != null) {
					BigDecimal nextAmount = contractAmount;
					for (int i = 0; i < this.kDTNewPayList.getRowCount(); i++) {
						if (i != (e.getRowIndex() + 1)) {
							IRow perRow = this.kDTNewPayList.getRow(i);
							BigDecimal purAmount = (BigDecimal) perRow.getCell(
									"amount").getValue();
							if (purAmount == null)
								purAmount = FDCHelper.ZERO;

							MoneyDefineInfo moneyDefine = (MoneyDefineInfo) perRow
									.getCell("moneyName").getValue();

							if (moneyDefine != null
									&& moneyDefine.getMoneyType().equals(
											MoneyTypeEnum.PreconcertMoney)) {
								continue;
							}

							if (!isEarnestInHouseAmount
									&& moneyDefine != null
									&& moneyDefine.getMoneyType().equals(
											MoneyTypeEnum.EarnestMoney)) {
								continue;
							}
							nextAmount = nextAmount.subtract(purAmount);
						}
					}
					nextRow.getCell("amount").setValue(nextAmount);
				}
			}

			this.updateLoanAndAFAmount();
		}
	}

	/**
	 * @author tim_gao
	 * @see 增加查询客户信息按钮
	 * @param e
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public void btnSearch_actionPerformed(java.awt.event.ActionEvent e) throws EASBizException, BOSException{
	      //write your code here
 		 int index = this.kDTCustomer.getSelectManager().getActiveRowIndex();
		 IRow row = this.kDTCustomer.getRow(index);
		 String id  = null;
		 FDCCustomerInfo customerInfo = null;
		 if(row == null){
			 MsgBox.showWarning("请在客户信息下选择行!");
		 } else{
			 //by zgy 替换原来逻辑，根据分录id列来取客户数，从交易表中取数，不从客户资料取数，防止客户资料与交易表同步更新
			 if(row.getCell("id").getValue()!=null){
				 id = row.getCell("id").getValue().toString();
				 customerInfo = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
			 }else{
				 customerInfo = (FDCCustomerInfo)row.getCell("customer").getValue();
			 }	 
			 if(customerInfo!=null){	 
				 String customerId = customerInfo.getId().toString();
				 UIContext uiContext = new UIContext(this);
				 uiContext.put(UIContext.ID,  customerId);
				 uiContext.put("CustomerView",  "CustomerView");
				 try {
					 IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create (CustomerEditUI.class.getName(), uiContext, null, OprtState.VIEW);
					 uiWindow.show();
				 } catch (UIException ee) {
					 logger.error(ee.getMessage()+"获得客户信息失败！");
				 }
			 }	
		 }
	}
}