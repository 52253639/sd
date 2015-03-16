package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.EventListener;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.insider.InsiderInfo;
import com.kingdee.eas.fdc.insider.client.CertificateUtil;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizFlowEnum;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BizListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CalculateTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CertificateFactory;
import com.kingdee.eas.fdc.sellhouse.CertificateInfo;
import com.kingdee.eas.fdc.sellhouse.CollectionCollection;
import com.kingdee.eas.fdc.sellhouse.CollectionFactory;
import com.kingdee.eas.fdc.sellhouse.CollectionInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FactorEnum;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.HoldEnum;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.IntegerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IntentionRoomsEntryCollection;
import com.kingdee.eas.fdc.sellhouse.IntentionRoomsEntryFactory;
import com.kingdee.eas.fdc.sellhouse.IntentionRoomsEntryInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.OperateEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PreMoneySetting;
import com.kingdee.eas.fdc.sellhouse.PreconcertPostponeCollection;
import com.kingdee.eas.fdc.sellhouse.PreconcertPostponeFactory;
import com.kingdee.eas.fdc.sellhouse.PreconcertPostponeInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseSaleManInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordEnum;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class PurchaseEditUI extends AbstractPurchaseEditUI {
	private static final String SHE_FDCCUSTOMER_EDIT = "she_purchase_modifyCustomer";
	private static final Logger logger = CoreUIObject.getLogger(PurchaseEditUI.class);

	private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	protected SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	
	
	private RoomDisplaySetting setting = new RoomDisplaySetting();
	private AgioParam currAgioParam = new AgioParam();
	private SellProjectInfo sellProjectCurrent ; 
	
	private KDBizPromptBox certificateName = new KDBizPromptBox();
	
	private boolean resultPerson = true;
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {		
		PurchaseFactory.getRemoteInstance().audit(editData.getId());
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		this.actionSubmit.setEnabled(false);
		this.actionSave.setEnabled(false);
		this.txtDes.setEditable(false);
		this.f7PayType.setEnabled(false);
		this.setOprtState(OprtState.VIEW);
		this.modifyName.setEnabled(false);
		this.modifyInfo.setEnabled(false);
	}
	
	/**
	 * 获得当前营销人员能够看到的营销顾问
	 */
    private UserCollection fullCustomer=null;

	
	protected void setAuditButtonStatus(String oprtType){
    	if(STATUS_VIEW.equals(oprtType)) {
    		actionAudit.setVisible(true);
    		actionUnAudit.setVisible(true);
    		actionAudit.setEnabled(true);
    		actionUnAudit.setEnabled(true);
    		if(editData!=null){
    			if(PurchaseStateEnum.PurchaseAudit.equals(editData.getPurchaseState())){
    	    		actionUnAudit.setVisible(true);
    	    		actionUnAudit.setEnabled(true);   
    	    		actionAudit.setVisible(false);
    	    		actionAudit.setEnabled(false);
    			}else{
    	    		actionUnAudit.setVisible(false);
    	    		actionUnAudit.setEnabled(false);   
    	    		actionAudit.setVisible(true);
    	    		actionAudit.setEnabled(true);
    			}
    		}
    	}else {
    		actionAudit.setVisible(false);
    		actionUnAudit.setVisible(false);
    		actionAudit.setEnabled(false);
    		actionUnAudit.setEnabled(false);
    	}
    }
	public void onLoad() throws Exception {
		
		initControl();
		super.onLoad();
		if(OprtState.ADDNEW.equals(this.getOprtState())){
			this.setUITitle("认购申请");
		}
		this.actionAuditResult.setVisible(false);
		tblCustomerInfo.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.txtPlanSignTimeLimit.setSupportedEmpty(false);
		this.txtPlanSignTimeLimit.setNegatived(false);
		this.txtPlanSignTimeLimit.setMaximumValue(new Integer(1000));
		txtPlanSignTimeLimit.setPrecision(0);
		//this.tblCustomerInfo.getColumn("certificateName").getStyleAttributes().setLocked(true);
		
		setDateEnable();
		if ("VIEW".equals(this.getOprtState())) {
			this.btnUp.setEnabled(false);
			this.btnDown.setEnabled(false);
			this.btnAddNewCustomer.setEnabled(false);
			this.btnAddCustomer.setEnabled(false);
			this.btnDeleteCustomer.setEnabled(false);
			this.modifyInfo.setEnabled(false);
			this.modifyName.setEnabled(false);
//			this.f7PayType.setEnabled(false);
		}

		// -------认购审批后编码不让修改,在initControlByBillState本来已经设置,但基类原因未生效,在这里补充控制----
		PurchaseInfo purchase = this.editData;
		PurchaseStateEnum state = purchase.getPurchaseState();
		if (state.equals(PurchaseStateEnum.PurchaseApply) || state.equals(PurchaseStateEnum.PurchaseAuditing) || state.equals(PurchaseStateEnum.PurchaseAudit)) {
			this.actionPrint.setVisible(true);
			this.actionPrintPreview.setVisible(true);
			this.actionPrint.setEnabled(true);
			this.actionPrintPreview.setEnabled(true);
		}
		if ("ADDNEW".equals(this.getOprtState())) {
			this.actionPrint.setVisible(false);
			this.actionPrintPreview.setVisible(false);
			this.actionPurchasePrint.setVisible(false);
			this.actionPurchasePrintview.setVisible(false);
			
			updataRoomContractAndDealAmount();
		}
		if ("EDIT".equals(this.getOprtState()) && PurchaseStateEnum.PurchaseAudit.equals(state)) {
			this.txtNumber.setEnabled(false);
			this.txtDes.setEnabled(false);
			this.modifyInfo.setEnabled(false);
			this.modifyName.setEnabled(false);
		}
		// -----------------------------

		this.storeFields();
		initOldData(this.editData);
		this.txtDes.setMaxLength(1000);
		this.actionAttachment.setVisible(false);

		this.addF7PayTypeFilter();

		this.chkMenuItemSubmitAndAddNew.setSelected(false);

		this.comboPriceAccount.setSelectedItem(currAgioParam.getPriceAccountType());
		this.comboPriceAccount.setEnabled(false);
		// TODO 临时隐藏
		// this.tabPurchase.remove(panelOtherPay);

		this.f7secondSeller.setEntityViewInfo(CommerceHelper.getPermitSalemanView());
		this.f7Seller.setEntityViewInfo(CommerceHelper.getPermitSalemanView());
		//审批和反审批的按钮控制
		
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);
		

		if("EDIT".equals(this.getOprtState()) && purchase.getPurchaseState().equals(PurchaseStateEnum.PrePurchaseApply) ) {
			SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
			Map preMoneySetMap = setting.getPreMoneySetMap();
			if(sellProject!=null){
				PreMoneySetting preMoneySet = (PreMoneySetting)preMoneySetMap.get(sellProject.getId()==null?null:sellProject.getId().toString());
				if(preMoneySet!=null) {
					if(!preMoneySet.getIsLevelModify().booleanValue()) {
						this.txtPreLevelMoney.setEnabled(false);
					}
					if(!preMoneySet.getIsStandModify().booleanValue()) {
						this.txtPrePurchaseAmount.setEnabled(false);
					}
				}
			}
		}
		if(txtRoomNumber.getText() != null && !"".equals(txtRoomNumber.getText())){
			showStandardPrice();			
			
		}
		
		setInsiderEntityView();
		
		//获得当前营销人员能够看到的营销顾问
		getSaleMans();
		this.btnAttachment.setVisible(true);
		if (!saleOrg.isIsBizUnit())
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		if ("VIEW".equals(this.getOprtState()) && PurchaseStateEnum.PurchaseAudit.equals(state)) {
			this.f7PayType.setAccessAuthority(0);
			this.f7PayType.setEnabled(false);
		}
		//是修改操作 且是认购申请
		if ("EDIT".equals(this.getOprtState()) && PurchaseStateEnum.PurchaseApply.equals(state)) {
			if(purchase.isIsAfterAudit()){
				setBtnStateWhenUnAudit();
			}
		}
		
		
//		增加table监听器，当修改了客户的时候去更新数据 xiaoao_liu
		this.tblCustomerInfo.addKDTEditListener(new KDTEditListener() {

			public void editCanceled(KDTEditEvent arg0) {
							
			}

			public void editStarted(KDTEditEvent arg0) {
			
			}

			public void editStarting(KDTEditEvent arg0) {
				if (arg0.getColIndex() == tblCustomerInfo.getColumn("certificateName")
						.getColumnIndex()) {
					try {
						int index = tblCustomerInfo.getSelectManager().getActiveRowIndex();
						IRow row = tblCustomerInfo.getRow(index);
						if(row==null){
			 				return;
						}
						
						CustomerTypeEnum type = (CustomerTypeEnum)row.getCell("customerType").getValue();
						if(type.equals(CustomerTypeEnum.PersonalCustomer)){
							initNewCertificateName(true,index);
						}else{
							initNewCertificateName(false,index);
						}
					} catch (Exception e1) {
						logger.error(e1.getMessage());
					}
				} 
			}

			public void editStopped(KDTEditEvent arg0) {
				if (arg0.getColIndex() == tblCustomerInfo.getColumn("customer").getColumnIndex()) {
					dealCustomerInfo();
				}
				
				
			}

			public void editStopping(KDTEditEvent arg0) {
			
			}

			public void editValueChanged(KDTEditEvent arg0) {
		
			}
			
		});

		checkPayedPlanState();
	}
	
	private void checkPayedPlanState() {
		boolean isAudit = PurchaseStateEnum.PurchaseAudit.equals(editData.getPurchaseState());
		boolean isAuditing = PurchaseStateEnum.PurchaseAuditing.equals(editData.getPurchaseState());
		boolean isViewState = "VIEW".equals(this.getOprtState());
		this.f7PayType.setEnabled(!checkIsPayed() && !isAudit && !isAuditing && !isViewState);
	}
	
	private boolean checkIsPayed(){
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			if(row.getCell("actAmount").getValue() !=null  
					&&  row.getCell("actAmount").getValue() instanceof BigDecimal
					&&  FDCHelper.ZERO.compareTo((BigDecimal)row.getCell("actAmount").getValue()) < 0
					&&  row.getCell("moneyName").getValue() !=null
					&&  !(MoneyTypeEnum.PreconcertMoney.equals(((MoneyDefineInfo)row.getCell("moneyName").getValue()).getMoneyType()))
			){
				return true;
			}
		}
		return false;
	}
	
	private void dealCustomerInfo(){
		
		int index = this.tblCustomerInfo.getSelectManager().getActiveRowIndex();
		IRow row  = this.tblCustomerInfo.getRow(index);
		if(row==null){
			return;
		}
		
		FDCCustomerInfo info  = null;
		if(row.getCell("customer").getValue()!=null){
			if(row.getCell("customer").getValue() instanceof FDCCustomerInfo){
				info=(FDCCustomerInfo)row.getCell("customer").getValue();
			}else{
				if(row.getCell("id").getValue()!=null){
					String id  = row.getCell("id").getValue().toString();
					try {
						info  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if(info!=null){
			setCustomerRowData(info,row);
		}
	}
	
	/**
	 * 设置标准总价、建筑/套内单价、建筑/套内实测单价
	 */
	public void showStandardPrice(){
		RoomInfo roomInfo = this.editData.getRoom();
		if(roomInfo==null) 
			roomInfo = (RoomInfo)(txtRoomNumber.getUserObject());
		
		BigDecimal standardTotalAmount = FDCHelper.ZERO;
		BigDecimal buildingArea = FDCHelper.ZERO;
		BigDecimal roomArea = FDCHelper.ZERO;
		if(roomInfo!=null){
			standardTotalAmount = roomInfo.getStandardTotalAmount();
			buildingArea = roomInfo.getBuildingArea();
			roomArea = roomInfo.getRoomArea();
			standardTotalAmount=standardTotalAmount==null?FDCHelper.ZERO:standardTotalAmount;
			buildingArea=buildingArea==null?FDCHelper.ONE:buildingArea;
		}
		//2010-4-12  lixin  修该标准总价来源于认购单　　原来的则是由房间中取的建筑面积计算而来　如果认购单中没有该值则由房间中获取
		if(editData.getSnapBuildPrice()==null || editData.getSnapBuildPrice()==FDCHelper.ZERO ){
			txtStandardBuildingPrice.setText((standardTotalAmount.divide(buildingArea,2,BigDecimal.ROUND_HALF_UP)).toString());
		}else{
			txtStandardBuildingPrice.setText(editData.getSnapBuildPrice().setScale(2).toString());
		}
		
		if(editData.getSnapRoomPrice()==null||editData.getSnapRoomPrice()==FDCHelper.ZERO||"".equals(editData.getSnapRoomPrice())){
			txtStandardRoomPrice.setText((standardTotalAmount.divide(roomArea,2,BigDecimal.ROUND_HALF_UP)).toString());
		}else{
			txtStandardRoomPrice.setText(editData.getSnapRoomPrice().setScale(2).toString());
		}
		this.txtStandardTotalAmount.setValue(standardTotalAmount);
		this.txtActualBuildingArea.setValue(roomInfo.getActualBuildingArea());
		this.txtActualRoomArea.setValue(roomInfo.getActualRoomArea());
	}

	/**
	 * 根据认购单的状态设置界面状态
	 * */
	private void initControlByBillState() {
		this.comboSpecialAgioType.setEnabled(false);
		PurchaseInfo purchase = this.editData;
		PurchaseStateEnum state = purchase.getPurchaseState();
		if(state==null)  return;
		this.setUITitle(state.getAlias());// TODO 这样有显示不正确的情况
		
		if (state.equals(PurchaseStateEnum.PrePurchaseApply)) {
			// this.actionWorkFlowG.setVisible(false);
			this.actionSubmit.setVisible(false);
			this.tabPurchase.remove(this.panelPurchaseBill);
			this.tabPurchase.remove(this.pnlPayList);
			this.getUIContext().put("isPrePurchase", Boolean.TRUE);
			this.btnCreat.setVisible(false);
		
		} else if (state.equals(PurchaseStateEnum.PrePurchaseCheck)) {
			this.getUIContext().put("isPrePurchase", Boolean.TRUE);
			this.actionWorkFlowG.setVisible(false);
			this.actionSave.setVisible(false);
			this.txtPrePurchaseAmount.setEnabled(false);
			this.txtPreLevelMoney.setEnabled(false);
			this.pkPrePurchaseDate.setEnabled(false);
			this.pkPreValidDate.setEnabled(false);
			this.f7PrePurchaseCurrency.setEnabled(false);
			if (this.getOprtState().equals("EDIT")) {
				this.tabPurchase.setSelectedComponent(this.panelPurchaseBill);
				this.actionPurchasePrint.setVisible(false);
				this.actionPurchasePrintview.setVisible(false);
			} else {
				// this.btnEdit.setText("转认购");
				// this.btnEdit.setToolTipText("转认购");
				this.tabPurchase.remove(this.panelPurchaseBill);
				this.tabPurchase.remove(this.pnlPayList);
				this.btnCreat.setVisible(false);
			}
			if(this.editData.getId()!=null){
				if(this.editData.getPrePurchaseDate()!=null){
					this.btnUp.setEnabled(false);
					this.btnDown.setEnabled(false);
					this.btnAddNewCustomer.setEnabled(false);
					this.btnAddCustomer.setEnabled(false);
					this.btnDeleteCustomer.setEnabled(false);
//					this.modifyInfo.setEnabled(false);
//					this.modifyName.setEnabled(false);
				}
			}else{
				if(!getOprtState().equals(this.STATUS_ADDNEW)){
					this.btnUp.setEnabled(false);
					this.btnDown.setEnabled(false);
					this.btnAddNewCustomer.setEnabled(false);
					this.btnAddCustomer.setEnabled(false);
					this.btnDeleteCustomer.setEnabled(false);
//					this.modifyInfo.setEnabled(false);
//					this.modifyName.setEnabled(false);
				}
			}
			
		} else if (state.equals(PurchaseStateEnum.PurchaseApply)) {
			this.actionSave.setVisible(false);

			// ---是否走预定流程通过预定日期来判断 zhicheng_jin 090226
			if (this.editData.getPrePurchaseDate() == null) {
				/*
				 * if (this.editData.getPrePurchaseAmount() == null ||
				 * this.editData.getPrePurchaseAmount().compareTo(
				 * FDCHelper.ZERO) == 0) { ------------------
				 */
				Boolean usedByChangeRoom = (Boolean) this.getUIContext().get("usedByChangRoom"); //换房时用到，否则会显示不出来
				if (usedByChangeRoom == null || !usedByChangeRoom.booleanValue())
					this.tabPurchase.remove(this.panelPrePurchaseInfo);
				
				this.actionPurchasePrint.setVisible(false);
				this.actionPurchasePrintview.setVisible(false);
			} else {
				this.txtPrePurchaseAmount.setEnabled(false);
				this.pkPrePurchaseDate.setEnabled(false);
				this.pkPreValidDate.setEnabled(false);
				this.f7PrePurchaseCurrency.setEnabled(false);
				this.tabPurchase.setSelectedComponent(this.panelPurchaseBill);
			}
			if(this.editData.getId()!=null){
				if(this.editData.getPrePurchaseDate()!=null){
					this.btnUp.setEnabled(false);
					this.btnDown.setEnabled(false);
					this.btnAddNewCustomer.setEnabled(false);
					this.btnAddCustomer.setEnabled(false);
					this.btnDeleteCustomer.setEnabled(false);
//					this.modifyInfo.setEnabled(false);
//					this.modifyName.setEnabled(false);
				}
			}
		} else if (state.equals(PurchaseStateEnum.PurchaseAudit)) {
			this.actionSave.setVisible(false);
			// this.actionAddNew.setEnabled(false);
			this.btnSelectRoom.setEnabled(false);
			this.btnEditAttach.setEnabled(false);
			this.btnChooseAgio.setEnabled(false);
//			this.f7PayType.setEnabled(false);
			this.txtFitmentAmount.setEnabled(false);
			this.txtFitmentPrice.setEnabled(false);
			this.chkIsFitmentToContract.setEnabled(false);
			this.txtFitmentStandard.setEnabled(false);
			this.txtLoanAmount.setEnabled(false);
			this.txtAFundAmount.setEnabled(false);
			this.f7Seller.setEnabled(false);
			this.txtNumber.setEnabled(false);
			this.txtSpecialAgio.setEnabled(false);
			this.f7SincerityPurchase.setEnabled(false);
			this.chkIsSellBySet.setEnabled(false);
			this.txtPurchaseAmount.setEnabled(false);
			this.f7PurchaseCurrency.setEnabled(false);
			this.pkPurchaseDate.setEnabled(false);
			this.f7DealCurrency.setEnabled(false);
			this.btnSelectRoom.setVisible(false);
			this.btnEditAttach.setVisible(false);
			/********************* 根据需要对控件设置不可编辑 *************************/
			this.btnUp.setEnabled(false);
			this.btnDown.setEnabled(false);		
			this.modifyInfo.setEnabled(false);
			this.modifyName.setEnabled(false);
			this.btnAddNewCustomer.setEnabled(false);
			this.btnAddCustomer.setEnabled(false);
			this.btnDeleteCustomer.setEnabled(false);
//			this.tblCustomerInfo.setEditable(false);
			this.f7secondSeller.setEnabled(false);
			this.txtPlanSignTimeLimit.setEnabled(false);
			this.pkPurchaseDate.setEnabled(false);			
			this.comboDigit.setEnabled(false);
			this.btnLittleAdjust.setEnabled(false);
			this.btnAddPayEntry.setEnabled(false);
			this.btnInsertPayEntry.setEnabled(false);
			this.btnRemovePayEntry.setEnabled(false);
			this.tblPayList.setEditable(false);
			/****************************** end ******************************/
			// this.tblCustomerInfo.getStyleAttributes().setLocked(false);
			if (this.editData.getPrePurchaseAmount() == null || this.editData.getPrePurchaseAmount().compareTo(FDCHelper.ZERO) == 0) {
				this.tabPurchase.remove(this.panelPrePurchaseInfo);
			} else {
				this.txtPreLevelMoney.setEnabled(false);
				this.txtPrePurchaseAmount.setEnabled(false);
				this.pkPrePurchaseDate.setEnabled(false);
				this.pkPreValidDate.setEnabled(false);
				this.f7PrePurchaseCurrency.setEnabled(false);
				this.tabPurchase.setSelectedComponent(this.panelPurchaseBill);
			}
		} else if (state.equals(PurchaseStateEnum.PurchaseAuditing)) {
			// 审批中不允许修改，能到这里的肯定是查看操作，锁定菜单即可
			this.actionSubmit.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionSave.setEnabled(false);
			if(this.editData.getId()!=null){
				if(this.editData.getPrePurchaseDate()!=null){
					this.btnUp.setEnabled(false);
					this.btnDown.setEnabled(false);
					this.btnAddNewCustomer.setEnabled(false);
					this.btnAddCustomer.setEnabled(false);
					this.btnDeleteCustomer.setEnabled(false);
					this.modifyInfo.setEnabled(false);
					this.modifyName.setEnabled(false);
					this.btnCreat.setVisible(false);
				}
			}
		} else {
			if (this.getOprtState().equals("EDIT")) {
				//合同直签的时候打开旧数据时会报错，所以做如下处理
				if(getUIContext().get("src")== null){
					this.abort();
				}
			} else {
				if (this.editData.getPrePurchaseAmount() == null || this.editData.getPrePurchaseAmount().compareTo(FDCHelper.ZERO) == 0) {
					this.tabPurchase.remove(this.panelPrePurchaseInfo);
					this.actionPurchasePrint.setVisible(false);
					this.actionPurchasePrintview.setVisible(false);
				}
			}
		}
		this.tblCustomerInfo.setEditable(!state.equals(PurchaseStateEnum.PurchaseAudit));
		
	}
	/**
	 * 设置购房推荐人过滤条件
	 */
	public void setInsiderEntityView(){
		Set set=new HashSet();
		for(int i=0;i<tblCustomerInfo.getRowCount();i++){
			IRow row=tblCustomerInfo.getRow(i);
			//by zgy 替换原来逻辑，根据分录id列来取客户数，从交易表中取数，不从客户资料取数，防止客户资料与交易表同步更新
			FDCCustomerInfo customer = null;
			if(row.getCell("id").getValue()!=null){
				String id  = row.getCell("id").getValue().toString();
				try {
					customer = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
					set.add(customer.getId().toString());
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}else{
				customer= (FDCCustomerInfo) row.getCell("customer").getValue();
			}
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		if(set.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id",set,CompareType.NOTINCLUDE));
		}
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",
				SysContext.getSysContext().getCurrentSaleUnit().getId().toString()));
		view.setFilter(filter);
		prmtRecommendInsider.setEntityViewInfo(view);
	}
	
	protected void prmtRecommendInsider_dataChanged(DataChangeEvent e)
			throws Exception {
		if(prmtRecommendInsider.getValue()!=null){
			InsiderInfo info=(InsiderInfo) prmtRecommendInsider.getValue();
			txtRecommendCard.setText(info.getInsiderCode());
		}else{
			txtRecommendCard.setText("");
		}
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String purchaseID = this.editData.getId().toString();
		HashMap parmterMap = this.checkPurchase(purchaseID,"认购");
		SpecialAgioEnum splType = (SpecialAgioEnum)this.comboSpecialAgioType.getSelectedItem();
		BigDecimal splAgio = (BigDecimal)this.txtSpecialAgio.getNumberValue();
		if(splType!=null){
			parmterMap.put("splType", splType);
		}
		if(splAgio !=null){
		    parmterMap.put("splAgio", splAgio);
		}
		parmterMap.put("currAgioParam", this.currAgioParam);
		PurchaseBookPrintDataProvider data = new PurchaseBookPrintDataProvider(purchaseID, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.PurchasePrintQuery"),parmterMap);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/purchase", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPrint_actionPerformed(e);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String purchaseID = this.editData.getId().toString();
		HashMap parmterMap = this.checkPurchase(purchaseID,"认购");
		SpecialAgioEnum splType = (SpecialAgioEnum)this.comboSpecialAgioType.getSelectedItem();
		BigDecimal splAgio = (BigDecimal)this.txtSpecialAgio.getNumberValue();
		if(splType!=null){
			parmterMap.put("splType", splType);
		}
		if(splAgio !=null){
		    parmterMap.put("splAgio", splAgio);
		}
		parmterMap.put("currAgioParam", this.currAgioParam);
		PurchaseBookPrintDataProvider data = new PurchaseBookPrintDataProvider(purchaseID, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.PurchasePrintQuery"),parmterMap);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/purchase", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPrintPreview_actionPerformed(e);
	}

	public void actionPurchasePrint_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String purchaseID = this.editData.getId().toString();
		HashMap parmterMap = this.checkPurchase(purchaseID,"预定");
		PurchasePrintDataProvider data = new PurchasePrintDataProvider(purchaseID, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.BeforehandPurchasePrintQuery"), parmterMap);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/purchase", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPurchasePrint_actionPerformed(e);
	}

	public HashMap checkPurchase(String purchaseID, String type) {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("customerInfo.*");
		sels.add("room.*");
		PurchaseInfo purchaseInfo = null;
		try {
			purchaseInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(BOSUuid.read(purchaseID)), sels);
		} catch (Exception e) {
			logger.error(e.getMessage());
			this.handleException(e);
		}
		BigDecimal prePurchaseAmount = purchaseInfo.getPrePurchaseAmount();
		HashMap parmterMap = new HashMap();
		ArrayList list = new ArrayList();
		if (prePurchaseAmount == null && "预定".equals(type)) {
			MsgBox.showInfo("预定金额为空,不能打印预定单!");
			this.abort();
		} else {
			RoomInfo room = purchaseInfo.getRoom();
			PurchaseCustomerInfoCollection purCusCollection = (PurchaseCustomerInfoCollection) purchaseInfo.getCustomerInfo();
			
			Object[] purCuses = purCusCollection.toArray();

			Arrays.sort(purCuses, new Comparator(){
				public int compare(Object arg0, Object arg1) {
					PurchaseCustomerInfoInfo purCus0 = (PurchaseCustomerInfoInfo) arg0;
					PurchaseCustomerInfoInfo purCus1 = (PurchaseCustomerInfoInfo) arg1;
					if(purCus0 == null  ||  purCus1 == null){
						return 0;
					}
					return purCus0.getSeq() - purCus1.getSeq();
				}
			});
			
			
			for (int i = 0; i < purCuses.length; i++) {
				PurchaseCustomerInfoInfo info = (PurchaseCustomerInfoInfo) purCuses[i];
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("*");
				selector.add("customer.*");
				FDCCustomerInfo customer = null;
				try {
					customer = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(info.getCustomer().getId().toString())), selector);
				} catch (Exception e) {
					logger.error(e.getMessage());
					handleException(e);
				}
				list.add(customer);
				parmterMap.put("customerList", list);
			}
			parmterMap.put("room", room);
			parmterMap.put("purchaseInfo", purchaseInfo);
			}
		//}
		return parmterMap;
	}

	/**
	 * 认购套打
	 */
	public void actionPurchasePrintview_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String purchaseID = this.editData.getId().toString();
		HashMap parmterMap = this.checkPurchase(purchaseID,"预定");
		PurchasePrintDataProvider data = new PurchasePrintDataProvider(purchaseID, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.BeforehandPurchasePrintQuery"), parmterMap);
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/purchase", data, SwingUtilities.getWindowAncestor(this));
		super.actionPurchasePrintview_actionPerformed(e);
	}

	/**
	 * 察看状态下锁定按钮，Table控件
	 * */
	private void initControlByState() {
		if (this.getOprtState().equals("VIEW") || this.getOprtState().equals("FINDVIEW")) {
			this.btnLittleAdjust.setEnabled(false);
			this.btnSelectRoom.setEnabled(false);
			this.btnEditAttach.setEnabled(false);
			this.btnChooseAgio.setEnabled(false);
			this.btnAddPayEntry.setEnabled(false);
			this.btnInsertPayEntry.setEnabled(false);
			this.btnRemovePayEntry.setEnabled(false);
			this.btnCreat.setEnabled(false);
			this.btnAddOtherPay.setEnabled(false);
			this.btnInsertOtherPay.setEnabled(false);
			this.btnRemoveOtherPay.setEnabled(false);
			this.tblCustomerInfo.getStyleAttributes().setLocked(true);
			
			this.tblPayList.getStyleAttributes().setLocked(true);
			this.tblOtherPay.getStyleAttributes().setLocked(true);
			this.btnUp.setEnabled(false);
			this.btnDown.setEnabled(false);
			this.btnAddNewCustomer.setEnabled(false);
			this.modifyInfo.setEnabled(false);
			this.modifyName.setEnabled(false);
			this.btnAddCustomer.setEnabled(false);
			this.btnDeleteCustomer.setEnabled(false);
			this.chkIsFitmentToContract.setEnabled(false);
			this.chkIsSellBySet.setEnabled(false);

			this.comboDigit.setVisible(false);
			this.btnLittleAdjust.setVisible(false);
		} else {
			this.btnLittleAdjust.setEnabled(true);
			this.btnSelectRoom.setEnabled(true);
			this.btnEditAttach.setEnabled(true);
			this.btnChooseAgio.setEnabled(true);
			this.btnAddPayEntry.setEnabled(true);
			this.btnInsertPayEntry.setEnabled(true);
			this.btnRemovePayEntry.setEnabled(true);
			this.btnCreat.setEnabled(true);
			this.btnAddOtherPay.setEnabled(true);
			this.btnInsertOtherPay.setEnabled(true);
			this.btnRemoveOtherPay.setEnabled(true);
			this.tblCustomerInfo.getStyleAttributes().setLocked(false);
			this.tblPayList.getStyleAttributes().setLocked(false);
			this.tblOtherPay.getStyleAttributes().setLocked(false);
			this.btnUp.setEnabled(true);
			this.btnDown.setEnabled(true);
			this.btnAddNewCustomer.setEnabled(true);
			this.modifyInfo.setEnabled(true);
			this.modifyName.setEnabled(true);
			this.btnAddCustomer.setEnabled(true);
			this.btnDeleteCustomer.setEnabled(true);
			this.chkIsFitmentToContract.setEnabled(true);
			this.chkIsSellBySet.setEnabled(true);
			this.comboDigit.setVisible(true);
			this.btnLittleAdjust.setVisible(true);
		}

		this.tblPayList.getColumn("refundmentAmount").getStyleAttributes().setLocked(true);
		this.tblPayList.getColumn("hasRemitAmount").getStyleAttributes().setLocked(true);
		this.tblPayList.getColumn("hasToFeeAmount").getStyleAttributes().setLocked(true);
		this.tblPayList.getColumn("actAmount").getStyleAttributes().setLocked(true);
		this.tblPayList.getColumn("currency").getStyleAttributes().setLocked(true);
		this.tblPayList.getColumn("actDate").getStyleAttributes().setLocked(true);
		this.tblOtherPay.getColumn("refundmentAmount").getStyleAttributes().setLocked(true);
		this.tblOtherPay.getColumn("actAmount").getStyleAttributes().setLocked(true);
		this.tblOtherPay.getColumn("currency").getStyleAttributes().setLocked(true);
		this.tblOtherPay.getColumn("actDate").getStyleAttributes().setLocked(true);
		
		
		if (this.getOprtState().equals("EDIT")) {
			this.btnSelectRoom.setEnabled(false);
		}

		// if("ADDNEW".equals(this.getOprtState())){
		// this.actionShowContract.setEnabled(false);
		// }else{
		// this.actionShowContract.setEnabled(true);
		// }
		
		if(this.editData.getId()!=null){
			if(this.editData.getPrePurchaseDate()!=null 
					&& !this.editData.getPurchaseState().equals(PurchaseStateEnum.PrePurchaseApply)){
				this.btnUp.setEnabled(false);
				this.btnDown.setEnabled(false);
				this.btnAddNewCustomer.setEnabled(false);
				this.btnAddCustomer.setEnabled(false);
				this.btnDeleteCustomer.setEnabled(false);
//				this.modifyInfo.setEnabled(false);
//				this.modifyName.setEnabled(false);
			}
		}else{
			if(!getOprtState().equals(this.STATUS_ADDNEW)){
				this.btnUp.setEnabled(false);
				this.btnDown.setEnabled(false);
				this.btnAddNewCustomer.setEnabled(false);
				this.btnAddCustomer.setEnabled(false);
				this.btnDeleteCustomer.setEnabled(false);
//				this.modifyInfo.setEnabled(false);
//				this.modifyName.setEnabled(false);
			}
		}
	}

	/**
	 * 初始化控件基础属性
	 * */
	private void initControl() {
		this.comboDigit.addItem(SHEHelper.TEN_THOUSHAND_DIGIT);
		this.comboDigit.addItem(SHEHelper.THOUSHAND_DIGIT);

		this.comboDigit.setSelectedItem(SHEHelper.THOUSHAND_DIGIT);
		
		// --------菜单项隐藏显示设置----------
//		this.actionAudit.setVisible(false);
//		this.btnAudit.setEnabled(true);100
//		this.actionUnAudit.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPurchasePrint.setEnabled(true);
		this.actionPurchasePrintview.setEnabled(true);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.menuBiz.setVisible(true);
		this.menuTable1.setVisible(false);
		this.btnPurchasePrint.setEnabled(true);
		this.btnPurchasePrintView.setEnabled(true);
		this.btnSave.setText("预定");
		this.btnSave.setToolTipText("预定");
		this.btnSubmit.setText("认购");
		this.btnSubmit.setToolTipText("认购");
		// -------控件基础设置------
		this.f7Seller.setRequired(true);
		this.f7PayType.setRequired(true);
		setTextFormat(this.txtContractTotalAmount);
		// setTextFormat(this.txtDealPrice);
		setTextFormat(this.txtPurchaseAmount);
		setTextFormat(this.txtPrePurchaseAmount);
		setTextFormat(this.txtAttachmentAmount);

		setTextFormat(this.txtFitmentAmount);
		setTextFormat(this.txtFitmentPrice);
		setTextFormat(this.txtLoanAmount);
		setTextFormat(this.txtAFundAmount);
		setTextFormat(this.txtAgio);

		setTextFormat(this.txtRoomArea);
		setTextFormat(this.txtBuildingArea);
		setTextFormat(this.txtStandardTotalAmount);
		setTextFormat(this.txtActualBuildingArea);
		setTextFormat(this.txtActualRoomArea);

		setTextFormat(this.txtAreaCompensateAmount);
		setTextFormat(this.txtSpecialAgio);

		setTextFormat(this.txtContractBuildPrice);
		setTextFormat(this.txtContractRoomPrice);
		setTextFormat(this.txtDealTotalAmount);
		setTextFormat(this.txtDealBuildPrice);
		setTextFormat(this.txtDealRoomPrice);

		// ------业务需求设置部分控件为不可编辑---------
		this.contSpecialAgio.setEnabled(false);
		this.f7DealCurrency.setEnabled(false);
		this.f7PrePurchaseCurrency.setEnabled(false);
		this.f7PurchaseCurrency.setEnabled(false);
		this.txtContractTotalAmount.setEnabled(false);
		// this.txtDealPrice.setEnabled(false);
		this.txtAgio.setEnabled(false);
		this.txtAgioDes.setEnabled(false);
		this.txtAttachmentAmount.setEnabled(false);
		this.txtNumber.setRequired(true);
		this.txtAttachRoom.setEnabled(false);
		this.txtBuildingArea.setEnabled(false);
		this.txtRoomArea.setEnabled(false);
		this.txtStandardTotalAmount.setEnabled(false);
		this.comboSellType.setEnabled(false);
		this.txtActualBuildingArea.setEnabled(false);
		this.txtActualRoomArea.setEnabled(false);
		this.txtAreaCompensateAmount.setEnabled(false);
		this.f7RoomModel.setEnabled(false);
		this.pkCreateDate.setEnabled(false);
		this.txtSellOrder.setEnabled(false);
		this.txtFitmentPrice.setEnabled(false);
		this.txtFitmentStandard.setEnabled(false);

		this.txtContractBuildPrice.setEnabled(false);
		this.txtContractRoomPrice.setEnabled(false);
		this.txtDealTotalAmount.setEnabled(false);
		this.txtDealBuildPrice.setEnabled(false);
		this.txtDealRoomPrice.setEnabled(false);

		// addF7PayTypeFilter();
		this.pkCreateDate.setEnabled(false);
		this.pkCreator.setEnabled(false);
		
		this.btnUp.setEnabled(true);
		this.btnDown.setEnabled(true);
		
		addF7SincerityPurchaseFilter();
		initPayTable();
		initCustomeTable();
		initOtherPayTable();
	}

	private void addF7SincerityPurchaseFilter() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		CtrlUnitInfo cu = SysContext.getSysContext().getCurrentCtrlUnit();
		filter.getFilterItems().add(new FilterItemInfo("CU.id", cu.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("sincerityState", SincerityPurchaseStateEnum.ARRANGENUM_VALUE));
		try {
			SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
			if (sellProject != null && sellProject.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", CommerceHelper.getPermitProjectIdSql(SysContext.getSysContext().getCurrentUserInfo()), CompareType.INNER));
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		view.setFilter(filter);
		this.f7SincerityPurchase.setEntityViewInfo(view);
	}

	public void addF7PayTypeFilter() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("startDate", new Date(), CompareType.LESS_EQUALS));
		/*
		 * CtrlUnitInfo cu = SysContext.getSysContext().getCurrentCtrlUnit();
		 * filter.getFilterItems().add(new FilterItemInfo("CU.id",
		 * cu.getId().toString()));
		 */
		String projectId = "null";
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		if (room!=null && room.getBuilding().getSellProject() != null) {
			projectId = room.getBuilding().getSellProject().getId().toString();
		}
		filter.getFilterItems().add(new FilterItemInfo("project.id", projectId));
		this.f7PayType.setEntityViewInfo(view);
	}
	
	private void initCustomeTable() {
		this.tblCustomerInfo.checkParsed();
		this.tblCustomerInfo.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
//		this.tblCustomerInfo.getColumn("bookDate").getStyleAttributes().setLocked(true);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblCustomerInfo.getColumn("propertyPercent").setEditor(numberEditor);
		this.tblCustomerInfo.getColumn("propertyPercent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblCustomerInfo.getColumn("propertyPercent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		String formatString = "yyyy-MM-dd";
		this.tblCustomerInfo.getColumn("bookDate").getStyleAttributes().setNumberFormat(formatString);

		try {
			this.initCustomerFilter(userInfo);
		} catch (BOSException e) {
			logger.error(e.getMessage());
			this.handleException(e);
			this.abort();
		} catch (EASBizException e) {
			this.handleException(e);
			this.abort();
		}

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomerInfo.getColumn("des").setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomerInfo.getColumn("phone").setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomerInfo.getColumn("mailAddress").setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomerInfo.getColumn("postalcode").setEditor(txtEditor);
		
		/*KDComboBox comboField = new KDComboBox();
		List list = CertifacateNameEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		textField.setMaxLength(80);
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblCustomerInfo.getColumn("certificateName").setEditor(comboEditor);*/
		/**
		 * 把证件名称又原来的枚举换成F7格式
		 * new update by renliang 2011-3-2
		 */
		initNewCertificateName(true,-2);
		
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomerInfo.getColumn("certificateNumber").setEditor(txtEditor);
		//设置性别单元格
		KDComboBox sexComboField = new KDComboBox();
		List sexList = SexEnum.getEnumList();
		for (int i = 0; i < sexList.size(); i++) {
			sexComboField.addItem(sexList.get(i));
		}
		textField.setMaxLength(80);
		KDTDefaultCellEditor sexComboEditor = new KDTDefaultCellEditor(sexComboField);
		this.tblCustomerInfo.getColumn("sex").setEditor(sexComboEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomerInfo.getColumn("des").setEditor(txtEditor);
		setCustomerInfoEnable(true);
		
	}
	
	/**
	 * 重新设置证件名称的选择模式
	 */
	private void initNewCertificateName(boolean isPerson,int rowIndex) {
		
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		if(isPerson){
			filterInfo.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.PERSONALCUSTOMER_VALUE, CompareType.EQUALS));
			filterInfo.setMaskString("#0");
		}else{
			filterInfo.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.ENTERPRICECUSTOMER_VALUE, CompareType.EQUALS));
			filterInfo.setMaskString("#0");
		}
		filterInfo.getFilterItems().add(new FilterItemInfo("customerType", "3Other", CompareType.EQUALS));
		filterInfo.setMaskString("#0 or #1");
		evi.setFilter(filterInfo);
		//KDBizPromptBox certificateName = new KDBizPromptBox();
		certificateName
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CertificateSearchQuery");
		certificateName.setEntityViewInfo(evi);
		certificateName.setEditable(false);
		certificateName.setDisplayFormat("$name$");
		certificateName.setEditFormat("$number$");
		certificateName.setCommitFormat("$number$");
		
		/*certificateName.addSelectorListener(new SelectorListener(){

			public void willShow(SelectorEvent e) {
				int index = tblCustomerInfo.getSelectManager().getActiveRowIndex();
				IRow row = tblCustomerInfo.getRow(index);
				if(row==null){
					return;
				}
				
				CustomerTypeEnum type = (CustomerTypeEnum)row.getCell("customerType").getValue();
				if(type.equals(CustomerTypeEnum.PersonalCustomer)){
					certificateName.setEntityViewInfo(null);
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filterInfo = new FilterInfo();
					filterInfo.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.PERSONALCUSTOMER_VALUE, CompareType.EQUALS));
					filterInfo.setMaskString("#0");
					filterInfo.getFilterItems().add(new FilterItemInfo("customerType", "3Other", CompareType.EQUALS));
					filterInfo.setMaskString("#0 or #1");
					evi.setFilter(filterInfo);
					certificateName.setEntityViewInfo(evi);
					ICellEditor f7Editor = new KDTDefaultCellEditor(certificateName);
					row.getCell("certificateName").setEditor(f7Editor);
					
				}else{
					certificateName.setEntityViewInfo(null);
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filterInfo = new FilterInfo();
					filterInfo.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.ENTERPRICECUSTOMER_VALUE, CompareType.EQUALS));
					filterInfo.setMaskString("#0");
					filterInfo.getFilterItems().add(new FilterItemInfo("customerType", "3Other", CompareType.EQUALS));
					filterInfo.setMaskString("#0 or #1");
					evi.setFilter(filterInfo);
					certificateName.setEntityViewInfo(evi);
					ICellEditor f7Editor = new KDTDefaultCellEditor(certificateName);
					row.getCell("certificateName").setEditor(f7Editor);
				}
			}
			
		});*/

		
		ICellEditor f7Editor = new KDTDefaultCellEditor(certificateName);
		if(rowIndex==-2){
			this.tblCustomerInfo.getColumn("certificateName").setEditor(f7Editor);
		}else{
			this.tblCustomerInfo.getRow(rowIndex).getCell("certificateName").setEditor(
					f7Editor);
		}
		
		
		//this.tblCustomerInfo.getColumn("certificateName").setRequired(true);
		
	}

	private void initCustomerFilter(UserInfo info) throws BOSException, EASBizException {
		KDBizPromptBox f7Customer = new KDBizPromptBox();
		f7Customer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
		f7Customer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(info, MoneySysTypeEnum.SalehouseSys));
		f7Customer.setEditable(true);
		f7Customer.setDisplayFormat("$name$");
		f7Customer.setEditFormat("$number$");
		f7Customer.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Customer);
		this.tblCustomerInfo.getColumn("customer").setEditor(f7Editor);
	}

	protected void f7Seller_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Seller_dataChanged(e);
		UserInfo info = (UserInfo) this.f7Seller.getValue();
		this.initCustomerFilter(info);
	}

	private void initPayTable() {
		this.tblPayList.checkParsed();
		this.tblPayList.getStyleAttributes().setLocked(false);
		this.tblPayList.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		this.tblPayList.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		String formatString = "yyyy-MM-dd";
		this.tblPayList.getColumn("date").getStyleAttributes().setNumberFormat(formatString);

		this.tblPayList.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPayList.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblPayList.getColumn("term").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPayList.getColumn("term").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
		this.tblPayList.getColumn("jiange").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPayList.getColumn("jiange").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));

		this.tblPayList.getColumn("term").getStyleAttributes().setHided(true);
		this.tblPayList.getColumn("jiange").getStyleAttributes().setHided(true);
		
		this.tblPayList.getColumn("hasToFeeAmount").getStyleAttributes().setNumberFormat("###,###.00");
		this.tblPayList.getColumn("hasToFeeAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		this.tblPayList.getColumn("date").setEditor(dateEditor);
		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setDisplayFormat("$name$");
		f7Box.setDisplayFormat("$number$");
		f7Box.setDisplayFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.EARNESTMONEY_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.FISRTAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.HOUSEAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.LOANAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ACCFUNDAMOUNT_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5)");
		f7Box.setEntityViewInfo(view);
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		this.tblPayList.getColumn("moneyName").setEditor(f7Editor);
		f7Box = new KDBizPromptBox();
		f7Box.setDisplayFormat("$name$");
		f7Box.setDisplayFormat("$number$");
		f7Box.setDisplayFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.tblPayList.getColumn("currency").setEditor(f7Editor);
		this.tblPayList.getColumn("currency").getStyleAttributes().setLocked(true);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblPayList.getColumn("term").setEditor(numberEditor);
		this.tblPayList.getColumn("jiange").setEditor(numberEditor);
		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblPayList.getColumn("amount").setEditor(numberEditor);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblPayList.getColumn("des").setEditor(txtEditor);

		this.tblPayList.getColumn("refundmentAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPayList.getColumn("refundmentAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPayList.getColumn("refundmentAmount").getStyleAttributes().setLocked(true);
		
		this.tblPayList.getColumn("hasRemitAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPayList.getColumn("hasRemitAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPayList.getColumn("hasRemitAmount").getStyleAttributes().setLocked(true);
		
		this.tblPayList.getColumn("actAmount").getStyleAttributes().setLocked(true);
		this.tblPayList.getColumn("actDate").getStyleAttributes().setLocked(true);
		this.tblPayList.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPayList.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblPayList.getColumn("moneyName").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblPayList.getColumn("date").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblPayList.getColumn("amount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
	}

	// 其它应付
	private void initOtherPayTable() {
		this.tblOtherPay.checkParsed();
		this.tblOtherPay.getStyleAttributes().setLocked(false);
		this.tblOtherPay.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		this.tblOtherPay.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.LATEFEE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.FITMENTAMOUNT_VALUE));
		// filter.getFilterItems().add(new FilterItemInfo("moneyType",
		// MoneyTypeEnum.TAXFEE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ELSEAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.REPLACEFEE_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5)");
		view.setFilter(filter);
		this.tblOtherPay.getColumn("moneyName").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery", view));
		this.tblOtherPay.getColumn("date").setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblOtherPay.getColumn("date").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.tblOtherPay.getColumn("currency").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery", null));
		this.tblOtherPay.getColumn("currency").getStyleAttributes().setLocked(true);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setPrecision(2);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblOtherPay.getColumn("amount").setEditor(numberEditor);
		this.tblOtherPay.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblOtherPay.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		// this.tblOtherPay.getColumn("actAmount").setEditor(numberEditor);
		this.tblOtherPay.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblOtherPay.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		if (!OprtState.VIEW.equals(oprtState) && !"FINDVIEW".equals(oprtState)) {
			this.tblOtherPay.getColumn("actAmount").getStyleAttributes().setHided(true);
			this.tblOtherPay.getColumn("actDate").getStyleAttributes().setHided(true);
		}

		this.tblPayList.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPayList.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblOtherPay.getColumn("actDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblOtherPay.getColumn("des").setEditor(txtEditor);

		this.tblOtherPay.getColumn("refundmentAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblOtherPay.getColumn("refundmentAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblOtherPay.getColumn("refundmentAmount").getStyleAttributes().setLocked(true);
		
		this.tblOtherPay.getColumn("moneyName").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblOtherPay.getColumn("date").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblOtherPay.getColumn("amount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);		
	}

	/**
	 * 销售方式改变事件,用来设置是否售前补差
	 * */
	protected void comboSellType_itemStateChanged(ItemEvent e) throws Exception {
		super.comboSellType_itemStateChanged(e);
		if (getOprtState().equals("VIEW") || getOprtState().equals("FINDVIEW")) {
			return;
		}

		updateAreaCompensateAmount();
		
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		updateFitmentPrice(room, this.txtFitmentAmount.getBigDecimalValue());
		
		updataRoomContractAndDealAmount();
	}

	/**
	 * 是否按套销售
	 */
	protected void chkIsSellBySet_actionPerformed(ActionEvent e) throws Exception {
		updateAreaCompensateAmount();
		
		updataRoomContractAndDealAmount();
	}
	
	/**
	成交总价、成交总价、房间建筑销售底价、房间套内销售底价、房间建筑销售底价、房间套内销售底价
	 */
	private void updataRoomContractAndDealAmount(){
		RoomInfo roomInfo = (RoomInfo) this.txtRoomNumber.getUserObject();
		if(roomInfo==null) roomInfo = this.editData.getRoom();
		
		SellTypeEnum sellType = (SellTypeEnum)this.comboSellType.getSelectedItem();
		
		BigDecimal fitmentAmount = (BigDecimal)this.txtFitmentAmount.getNumberValue();
		BigDecimal attachmentAmount = (BigDecimal)this.txtAttachmentAmount.getNumberValue();
		BigDecimal areaCompensateAmount = (BigDecimal)this.txtAreaCompensateAmount.getNumberValue();
		SpecialAgioEnum splType = (SpecialAgioEnum)this.comboSpecialAgioType.getSelectedItem();
		boolean isFitmentToContract = this.chkIsFitmentToContract.isSelected();
		BigDecimal splAgio = (BigDecimal)this.txtSpecialAgio.getNumberValue();
		
//		PurchaseParam purParam = AgioSelectUI.getPurchaseAgioParam(this.currAgioParam, roomInfo, 
//				 sellType, isFitmentToContract, fitmentAmount, attachmentAmount, areaCompensateAmount ,splType, splAgio);
//		if(purParam!=null) {
//			this.txtAgioDes.setText(purParam.getAgioDes());
//			this.txtAgio.setValue(purParam.getFinalAgio());
//			this.txtDealTotalAmount.setValue(purParam.getDealTotalAmount());
//			this.txtContractTotalAmount.setValue(purParam.getContractTotalAmount());
//			this.txtContractBuildPrice.setValue(purParam.getContractBuildPrice());
//			this.txtContractRoomPrice.setValue(purParam.getContractRoomPrice());
//			this.txtDealBuildPrice.setValue(purParam.getDealBuildPrice());
//			this.txtDealRoomPrice.setValue(purParam.getDealRoomPrice());
//		}
	}
	
	
	/**
	 * 更新补差金额
	 * 跟销售方式 和 是否按套销售 有关
	 */
	public void updateAreaCompensateAmount(){
		SellTypeEnum sellType = (SellTypeEnum) comboSellType.getSelectedItem();
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		BigDecimal areaCompensateAmount = new BigDecimal(0);
		if (SellTypeEnum.LocaleSell.equals(sellType)) {
			boolean isSelect  = this.chkIsSellBySet.isSelected();
			if(!isSelect)
				areaCompensateAmount = getCompensateAmount(room,true);
			
			this.contActualBuildingArea.setVisible(true);
			this.contActualRoomArea.setVisible(true);
			this.contAreaCompensateAmount.setVisible(true);
		}else{
			this.contActualBuildingArea.setVisible(false);
			this.contActualRoomArea.setVisible(false);
			this.contAreaCompensateAmount.setVisible(false);
		}
		this.txtAreaCompensateAmount.setNumberValue(areaCompensateAmount);
	}
	

	/**
	 * 获得补差款
	 * 如果是按套销售也不再差价
	 * 只有实测面积复核后，且销售方式为现售， 存在金额调整的问题(实测 和 预测的差值)
	 */
	private BigDecimal getCompensateAmount(RoomInfo roomInfo,boolean isLocaleSell) {
		BigDecimal areaCompensateAmount = new BigDecimal(0);
		if (isLocaleSell && roomInfo!=null && roomInfo.isIsActualAreaAudited()) {
			if (roomInfo.isIsCalByRoomArea()) {
				BigDecimal actualRoomArea = roomInfo.getActualRoomArea();
				if (actualRoomArea != null && roomInfo.getRoomPrice() != null && roomInfo.getRoomArea() != null) {
					areaCompensateAmount = roomInfo.getRoomPrice().multiply(actualRoomArea.subtract(roomInfo.getRoomArea()));
				}
			} else {
				BigDecimal actualBuildingArea = roomInfo.getActualBuildingArea();
				if (actualBuildingArea != null && roomInfo.getBuildPrice() != null && roomInfo.getBuildingArea() != null) {
					areaCompensateAmount = roomInfo.getBuildPrice().multiply(actualBuildingArea.subtract(roomInfo.getBuildingArea()));
				}
			}
		}
		return areaCompensateAmount;
	}

	public void loadFields() {
		
		EventListener[] listeners = this.f7PayType.getListeners(DataChangeListener.class);
		for (int i = 0; i < listeners.length; i++) {
			this.f7PayType.removeDataChangeListener((DataChangeListener) listeners[i]);
		}
		EventListener[] listenersPrePurchaseChecker = this.f7PrePurchaseChecker.getListeners(DataChangeListener.class);
		for (int i = 0; i < listenersPrePurchaseChecker.length; i++) {
			this.f7PrePurchaseChecker.removeDataChangeListener((DataChangeListener) listenersPrePurchaseChecker[i]);
		}
		EventListener[] listenersDealCurrency = this.f7DealCurrency.getListeners(DataChangeListener.class);
		for (int i = 0; i < listenersDealCurrency.length; i++) {
			this.f7DealCurrency.removeDataChangeListener((DataChangeListener) listenersDealCurrency[i]);
		}
		EventListener[] listenersSeller = this.f7Seller.getListeners(DataChangeListener.class);
		for (int i = 0; i < listenersSeller.length; i++) {
			this.f7Seller.removeDataChangeListener((DataChangeListener) listenersSeller[i]);
		}
		// 换房需要调用loadFields(),又不需要调用super.loadFields();
		Boolean usedByChangeRoom = (Boolean) this.getUIContext().get("usedByChangRoom");
		if (usedByChangeRoom == null || !usedByChangeRoom.booleanValue())
			super.loadFields();
		PurchaseInfo info = (PurchaseInfo) this.editData;
		
		currAgioParam.setToInteger(info.isIsAutoToInteger());
		currAgioParam.setBasePriceSell(info.isIsBasePriceSell());
		currAgioParam.setToIntegerType(info.getToIntegerType());
		currAgioParam.setDigit(info.getDigit());
		currAgioParam.setPriceAccountType(info.getPriceAccountType());
		AgioEntryCollection agioEntryColl = new AgioEntryCollection(); 
		for(int i=0;i<info.getAgioEntrys().size();i++)
			agioEntryColl.add(info.getAgioEntrys().get(i));
		this.txtAgio.setUserObject(agioEntryColl);
		currAgioParam.setAgios(agioEntryColl);

		setControlStateAboutRoom(info);

		if (getOprtState().equals("EDIT") && !isPrePurchase() && pkPurchaseDate.getValue() == null) {
			pkPurchaseDate.setValue(SHEHelper.getCurrentTime());
		}
		this.txtNumber.setText(info.getNumber());
		if (info.getPayType() != null) {
			this.f7PayType.setValue(info.getPayType());
		}
		
		
		
		this.txtAgioDes.setText(this.getAgioDes());
		if (this.txtAttachRoom.getUserObject() == null) {
			this.txtAttachRoom.setUserObject(info.getAttachmentEntries().clone());// 在setControlStateAboutRoom
			// (
			// )
			// 函数中已对附属房产分录进行取值和界面赋值
		}
		this.chkIsSellBySet.setSelected(info.isIsSellBySet());
		this.comboPriceAccount.setSelectedItem(currAgioParam.getPriceAccountType());
		this.comboPriceAccount.setEnabled(false);
		if (info.getPrePurchaseAuditor() != null) {
			this.f7PrePurchaseChecker.setValue(info.getPrePurchaseAuditor());
		}
		this.pkPrePurchaseCheckTime.setValue(info.getPrePurchaseAuditDate());
		this.comboSpecialAgioType.setSelectedItem(info.getSpecialAgioType());
		this.txtSpecialAgio.setValue(info.getSpecialAgio());
		this.txtFitmentStandard.setText(info.getFitmentStandard());
		this.chkIsFitmentToContract.setSelected(info.isIsFitmentToContract());
		this.txtFitmentPrice.setValue(info.getFitmentPrice());
		this.txtFitmentAmount.setValue(info.getFitmentAmount());
		if (info.getDealCurrency() != null) {
			this.f7DealCurrency.setValue(info.getDealCurrency());
		}
		this.txtAttachmentAmount.setValue(info.getAttachmentAmount());
		// this.txtDealPrice.setValue(info.getDealPrice());
		// this.txtDealTotalAmount.setValue(info.getDealAmount());
		this.txtLoanAmount.setValue(info.getLoanAmount());
		this.txtAFundAmount.setValue(info.getAccumulationFundAmount());
		if (info.getSalesman() != null) {
			this.f7Seller.setValue(info.getSalesman());
		}
		if(getOprtState().equals("ADDNEW")){
			SincerityPurchaseInfo sin = (SincerityPurchaseInfo) this.f7SincerityPurchase.getValue();
			if (sin != null) {
				UserInfo salesman = sin.getSalesman();
				if(salesman!= null && salesman.getId()!=null){
					try {
						this.f7Seller.setValue(UserFactory.getRemoteInstance().getUserInfo(new ObjectUuidPK(BOSUuid.read(String.valueOf(salesman.getId())))));
					} catch (EASBizException e) {
						e.printStackTrace();
						logger.error(e);
					} catch (BOSException e) {
						e.printStackTrace();
						logger.error(e);
					} catch (UuidException e) {
						e.printStackTrace();
						logger.error(e);
					}
				}
			}
		}
		if(info.getSecondSaleMan().size()>0)
		{
			Object[] ob = new Object[info.getSecondSaleMan().size()];
			for(int i=0;i<info.getSecondSaleMan().size();i++)
			{
				PurchaseSaleManInfo secondInfo = info.getSecondSaleMan().get(i);
				UserInfo user = secondInfo.getSecondMan();
				ob[i] = user;
			}
			this.f7secondSeller.setValue(ob);
		}
		
		this.pkCreateDate.setValue(info.getCreateTime());
		this.txtContractTotalAmount.setValue(info.getContractTotalAmount());
		this.txtStandardTotalAmount.setValue(info.getStandardTotalAmount());

		this.txtContractBuildPrice.setValue(info.getContractBuildPrice());
		this.txtContractRoomPrice.setValue(info.getContractRoomPrice());
		this.txtDealTotalAmount.setValue(info.getDealAmount());
		this.txtDealBuildPrice.setValue(info.getDealBuildPrice());
		this.txtDealRoomPrice.setValue(info.getDealRoomPrice());
		loadCustomers(info);
		//加个条件语句，来判断在点击“审批”按钮后是否更新单价。for wangpeng
		if(this.getUIContext().get("quitRoom")==null){
			if (this.txtRoomNumber.getUserObject() != null) {// 计算交易总价和打折信息
				updateTotalAmount((RoomInfo) this.txtRoomNumber.getUserObject());
			}
		}else{
			this.getUIContext().clear();
		}
		
		// 该记录最后加载
		loadPreDateLayOut(info);
		loadPayList(info);
		loadOtherPayList(info);
		updateLoanAndAFAmount();
//		if (!"ADDNEW".equals(oprtState)) {
			// 变更时合同总价尾数四舍五入，可能导致通过房间总价计算的合同总价和表中的总价不一致，需要重新加载. for zhenye
			//this.txtContractTotalAmount.setValue(info.getContractTotalAmount()
			// );
			//this.txtStandardTotalAmount.setValue(info.getStandardTotalAmount()
			// );
			//this.txtContractBuildPrice.setValue(info.getContractBuildPrice());
			// this.txtContractRoomPrice.setValue(info.getContractRoomPrice());
			// this.txtDealTotalAmount.setValue(info.getDealAmount());
			// this.txtDealBuildPrice.setValue(info.getDealBuildPrice());
			// this.txtDealRoomPrice.setValue(info.getDealRoomPrice());
//		}
		initControlByState();
		initControlByBillState();
		for (int i = 0; i < listeners.length; i++) {
			this.f7PayType.addDataChangeListener((DataChangeListener) listeners[i]);
		}
		for (int i = 0; i < listenersPrePurchaseChecker.length; i++) {
			this.f7PrePurchaseChecker.addDataChangeListener((DataChangeListener) listenersPrePurchaseChecker[i]);
		}
		for (int i = 0; i < listenersDealCurrency.length; i++) {
			this.f7DealCurrency.addDataChangeListener((DataChangeListener) listenersDealCurrency[i]);
		}
		for (int i = 0; i < listenersSeller.length; i++) {
			this.f7Seller.addDataChangeListener((DataChangeListener) listenersSeller[i]);
		}
		setAuditButtonStatus(this.getOprtState());
		if(getOprtState().equals(OprtState.ADDNEW)){
			modifyName.setEnabled(false);
			modifyInfo.setEnabled(false);
		}else{
			modifyName.setEnabled(true);
			modifyInfo.setEnabled(true);
		}
		if(this.editData.getId()!=null){
			if(this.editData.getPrePurchaseDate()!=null
					&& !this.editData.getPurchaseState().equals(PurchaseStateEnum.PrePurchaseApply)){
				this.btnUp.setEnabled(false);
				this.btnDown.setEnabled(false);
				this.btnAddNewCustomer.setEnabled(false);
				this.btnAddCustomer.setEnabled(false);
				this.btnDeleteCustomer.setEnabled(false);
//				this.modifyInfo.setEnabled(false);
//				this.modifyName.setEnabled(false);
			}
		}else{
			if(!getOprtState().equals(this.STATUS_ADDNEW)){
				this.btnUp.setEnabled(false);
				this.btnDown.setEnabled(false);
				this.btnAddNewCustomer.setEnabled(false);
				this.btnAddCustomer.setEnabled(false);
				this.btnDeleteCustomer.setEnabled(false);
//				this.modifyInfo.setEnabled(false);
//				this.modifyName.setEnabled(false);
			}
		}
//		setSeller();
		
		checkPayedPlanState();
	}

	
	private void loadPreDateLayOut(PurchaseInfo info) {
		this.tblPreLayTime.setVisible(false);
		this.tblPreLayTime.checkParsed();
		if(info==null || info.getId()==null)			return;
		
		try{
			PreconcertPostponeCollection prePostColl = PreconcertPostponeFactory.getRemoteInstance().getPreconcertPostponeCollection(
					"select id,number,state,originalDate,originalAvailab,nowDat,nowavailab where purchase.id = '"+info.getId()+"'");
			if(prePostColl.size()>0) {
				this.tblPreLayTime.setVisible(true);
				for(int i=0;i<prePostColl.size();i++) {
					PreconcertPostponeInfo prePostInfo = prePostColl.get(i); 
					IRow addRow = this.tblPreLayTime.addRow();
					addRow.getCell("id").setValue(prePostInfo.getId().toString());
					addRow.getCell("number").setValue(prePostInfo.getNumber());
					addRow.getCell("state").setValue(prePostInfo.getState());
					addRow.getCell("oldPrePur").setValue(prePostInfo.getOriginalDate());
					addRow.getCell("oldPreValid").setValue(prePostInfo.getOriginalAvailab());
					addRow.getCell("newPrePur").setValue(prePostInfo.getNowDat());
					addRow.getCell("newPreValid").setValue(prePostInfo.getNowavailab());
				}
			}
		}catch(Exception e){
			this.handleException(e);
		}
		
	}
	
	private void loadPayList(PurchaseInfo info) {
		PurchasePayListEntryCollection payEntrys = info.getPayListEntry();
		CRMHelper.sortCollection(payEntrys, "seq", true);
		this.tblPayList.removeRows();
		for(int i=0;i<payEntrys.size();i++) {
			PurchasePayListEntryInfo payEntry = payEntrys.get(i);
			addPayListEntryRow(payEntry);
		}
	}

	private void loadOtherPayList(PurchaseInfo info) {
		PurchaseElsePayListEntryCollection payEntrys = info.getElsePayListEntry();
		CRMHelper.sortCollection(payEntrys, "seq", true);
		this.tblOtherPay.removeRows();
		for(int i=0;i<payEntrys.size();i++) {
			PurchaseElsePayListEntryInfo payEntry = payEntrys.get(i);
			addOtherPayListEntryRow(payEntry);
		}
	}

	/**
	 * 设置与房间相关的控件的状态和Value
	 * */
	private void setControlStateAboutRoom(PurchaseInfo info) {
		RoomInfo room = info.getRoom();
		// setSincerityFilter(room);
		setValueAboutCompensate(info.getSellType(), room, info.getAreaCompensateAmount());
		if (room != null) {
			this.txtRoomNumber.setUserObject(room);
			this.txtRoomNumber.setText(room.getDisplayName());
			if (room.getSellOrder() != null) {
				this.txtSellOrder.setText(room.getSellOrder().getName());
			}
			this.txtProject.setText(room.getBuilding().getSellProject().getName());
			if (room.getBuilding().getSubarea() != null) {
				this.txtSubarea.setText(room.getBuilding().getSubarea().getName());
			}
			this.txtBuilding.setText(room.getBuilding().getName());
			this.spiUnit.setValue(new Integer(room.getBuildUnit() == null ? 0 : room.getBuildUnit().getSeq()));
			this.spiFloor.setValue(new Integer(room.getFloor()));
			this.f7BuildingFloor.setValue(room.getBuildingFloor());

			this.txtBuildingArea.setValue(room.getBuildingArea());
			this.txtRoomArea.setValue(room.getRoomArea());
			this.f7RoomModel.setValue(room.getRoomModel());
			//2010-4-12 lixin
//			this.txtStandardTotalAmount.setValue(room.getStandardTotalAmount());
			if(this.editData.getStandardTotalAmount()==null||editData.getStandardTotalAmount()==FDCHelper.ZERO||"".equals(editData.getStandardTotalAmount())){
				this.txtStandardTotalAmount.setValue(room.getStandardTotalAmount());	
			}
			try {
				this.updateAttachmentAmount(null);
			} catch (BOSException e) {
				this.handleException(e);
			}
		} else {
			this.txtRoomNumber.setUserObject(null);
			this.txtRoomNumber.setText(null);
			this.txtSellOrder.setText(null);
			this.txtProject.setText(null);
			this.txtSubarea.setText(null);
			this.txtBuilding.setText(null);
			this.spiUnit.setValue(new Integer(0));
			this.spiFloor.setValue(new Integer(0));
			this.txtBuildingArea.setValue(null);
			this.txtRoomArea.setValue(null);
			this.f7RoomModel.setValue(null);
			this.txtStandardTotalAmount.setValue(null);
			this.txtAttachRoom.setText(null);

			this.txtActualBuildingArea.setValue(null);
			this.txtActualRoomArea.setValue(null);
			this.txtAreaCompensateAmount.setValue(null);
		}
	}

	private static final String AUDITED = "1";

	//得到所有客户信息的数目
	int customerCount = 0;
	private void loadCustomers(PurchaseInfo info) {
		
		this.tblCustomerInfo.checkParsed();
		String formatString = "yyyy-MM-dd";
		this.tblCustomerInfo.getColumn("bookDate").getStyleAttributes().setNumberFormat(formatString);
		KDCheckBox seqBox = new KDCheckBox();
		seqBox.setVisible(true);
		seqBox.setEnabled(false);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(seqBox);
		this.tblCustomerInfo.getColumn("seq").setEditor(editor);
		
		this.tblCustomerInfo.removeRows();
		PurchaseCustomerInfoCollection customerInfos = info.getCustomerInfo();
		sortBySeq(customerInfos); // 按序号排序
 		//加载新交易数据  by zgy 
 		PurCustomerCollection purCustomerInfos = info.getPurCustomer();
 		sortBySeq_purcustomer(purCustomerInfos);
		customerCount = customerInfos.size();
		for (int i = 0; i < customerInfos.size(); i++) {
			PurchaseCustomerInfoInfo customerInfo = customerInfos.get(i);
			IRow row = this.tblCustomerInfo.addRow();
			//保存后的行不可编辑 by zgy 2010-12-18
			row.getStyleAttributes().setLocked(true);
			row.getCell("propertyPercent").getStyleAttributes().setLocked(false);
			row.getCell("des").getStyleAttributes().setLocked(false);
			//设置产权可编辑  by zgy 2010-12-18
			// 如果该客户已随认购单一起被审批，则不允许修改和删除
			if (AUDITED.equals(customerInfo.getNumber())) {
				row.getStyleAttributes().setLocked(true);
				row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				row.getCell("propertyPercent").getStyleAttributes().setLocked(false);
				row.getCell("propertyPercent").getStyleAttributes().setBackground(Color.WHITE);
				row.getCell("des").getStyleAttributes().setLocked(false);
			}
			row.setHeight(20);
			row.setUserObject(customerInfo);
			if(seqBox != null){
				if(i==0){
					tblCustomerInfo.getRow(i).getCell("seq").setValue(Boolean.TRUE);
				}else{
					tblCustomerInfo.getRow(i).getCell("seq").setValue(Boolean.FALSE);
				}
			}
			row.getCell("propertyPercent").setValue(customerInfo.getPropertyPercent());
			row.getCell("des").setValue(customerInfo.getDescription());
			FDCCustomerInfo customer = customerInfo.getCustomer();
			if (customer != null) {
				row.getCell("customer").setValue(customer);
                row.getCell("id").setValue(customer.getId().toString());
				row.getCell("postalcode").setValue(customer.getPostalcode());
				row.getCell("phone").setValue(customer.getPhone());
				//new update by renliang at 2011-3-2
				if(customer.getCertificateName()!=null){
					row.getCell("certificateName").setValue(customer.getCertificateName());
				}
				row.getCell("certificateNumber").setValue(customer.getCertificateNumber());
				row.getCell("mailAddress").setValue(customer.getMailAddress());
				row.getCell("bookDate").setValue(customer.getCreateTime());
				
				//给性别赋值
				row.getCell("sex").setValue(customer.getSex());
				/**
				 * 赋值给销售顾问id
				 *add by renliang at 2010-7-12	 
				 */
				row.getCell("salesId").setValue(customer.getSalesman().getId().toString());
				row.getCell("customerType").setValue(customer.getCustomerType());
			}
			//维持以前的数据 ，增加再在的逻辑，如果存在覆盖旧数据
			if(purCustomerInfos!=null){
				PurCustomerInfo pCinfo  = purCustomerInfos.get(i);
				if(pCinfo!=null){
					row.getCell("propertyPercent").setValue(pCinfo.getPropertyPercent()); 
					row.getCell("des").setValue(pCinfo.getDescription());
					//给性别赋值
					row.getCell("sex").setValue(pCinfo.getSex());
					row.getCell("customer").setValue(pCinfo.getCustomerName());
					row.getCell("id").setValue(pCinfo.getCustomerID());
					row.getCell("postalcode").setValue(pCinfo.getPostalcode());
					row.getCell("phone").setValue(pCinfo.getTel());
					if(pCinfo.getCertificateName()!=null){
						row.getCell("certificateName").setValue(pCinfo.getCertificateName());
					}
					row.getCell("certificateNumber").setValue(pCinfo.getCertificateNumber());
					row.getCell("mailAddress").setValue(pCinfo.getMailAddress());
					row.getCell("bookDate").setValue(pCinfo.getCreateTime());
				}
			}
		}
	}

	/**
	 * 将认购客户按照产权比例进行排序
	 * */
	private void sortByPropertyPercent(PurchaseCustomerInfoCollection customerInfos) {
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

				BigDecimal dec0 = tmp0.getPropertyPercent();
				BigDecimal dec1 = tmp1.getPropertyPercent();
				if (dec0 == null)
					dec0 = FDCHelper.ZERO;
				if (dec1 == null)
					dec1 = FDCHelper.ZERO;
				return dec1.compareTo(dec0);
			}
		});
		customerInfos.clear();

		for (int i = 0; i < objs.length; i++) {
			PurchaseCustomerInfoInfo purCustInfo = (PurchaseCustomerInfoInfo) objs[i];
			purCustInfo.setSeq(i);
			customerInfos.add(purCustInfo);
		}
	}

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
	/**
	 * 设置与补差相关的控件的值
	 * 
	 * @param sellType
	 *            当前销售方式
	 * @param room
	 *            当前认购的房间
	 * @param compensateAmount
	 *            面积补差的金额
	 * */
	private void setValueAboutCompensate(SellTypeEnum sellType, RoomInfo room, BigDecimal compensateAmount) {
		if (room == null) {
			this.txtActualBuildingArea.setValue(null);
			this.txtActualRoomArea.setValue(null);
			this.txtAreaCompensateAmount.setValue(null);
			this.comboSellType.setEnabled(false);
			this.contActualBuildingArea.setVisible(false);
			this.contActualRoomArea.setVisible(false);
			this.contAreaCompensateAmount.setVisible(false);
			return;
		}
		// 只有实测复核后，才补差
		if (room.isIsActualAreaAudited()) {
			if (this.getOprtState().equals("ADDNEW")) {
				this.comboSellType.setEnabled(true);
			}

			if (SellTypeEnum.LocaleSell.equals(sellType)) {
				this.txtActualBuildingArea.setValue(room.getActualBuildingArea());
				this.txtActualRoomArea.setValue(room.getActualRoomArea());
				this.txtAreaCompensateAmount.setValue(compensateAmount);

				this.contActualBuildingArea.setVisible(true);
				this.contActualRoomArea.setVisible(true);
				this.contAreaCompensateAmount.setVisible(true);
			} else {
				this.txtActualBuildingArea.setValue(null);
				this.txtActualRoomArea.setValue(null);
				this.txtAreaCompensateAmount.setValue(null);

				this.contActualBuildingArea.setVisible(false);
				this.contActualRoomArea.setVisible(false);
				this.contAreaCompensateAmount.setVisible(false);
			}
		} else {
			this.txtActualBuildingArea.setValue(null);
			this.txtActualRoomArea.setValue(null);
			this.txtAreaCompensateAmount.setValue(null);

			this.comboSellType.setEnabled(false);
			this.contActualBuildingArea.setVisible(false);
			this.contActualRoomArea.setVisible(false);
			this.contAreaCompensateAmount.setVisible(false);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.actionRemove.setEnabled(true);
		
		PurchaseStateEnum purState = this.editData.getPurchaseState();
		
		if (purState.equals(PurchaseStateEnum.PURCHASECHANGE_VALUE))
		{
			MsgBox.showInfo("认购单已变更,不可修改!");
			return;
		}
		
		if (purState.equals(PurchaseStateEnum.ChangeRoomBlankOut)
				|| purState.equals(PurchaseStateEnum.QuitRoomBlankOut)
				|| purState.equals(PurchaseStateEnum.NoPayBlankOut)
				|| purState.equals(PurchaseStateEnum.ManualBlankOut)
				|| purState.equals(PurchaseStateEnum.AdjustBlankOut)) {
			MsgBox.showInfo("已经作废,不能修改!");
			return;
		}
		
		if (purState.equals(PurchaseStateEnum.PurchaseChange)) {
			MsgBox.showInfo("认购单在变更流程中,不能修改!");
			return;
		}
		PurchaseInfo purchase = this.editData;
		PurchaseStateEnum state = purchase.getPurchaseState();
		if (state.equals(PurchaseStateEnum.PrePurchaseCheck)) {
			tabPurchase.add(panelPurchaseBill, resHelper.getString("panelPurchaseBill.constraints"));
			tabPurchase.add(pnlPayList, resHelper.getString("pnlPayList.constraints"));
		}
		
		verifyBalance(purchase.getPurchaseDate());
		 
		super.actionEdit_actionPerformed(e);
		this.pkCreateDate.setEnabled(false);
		this.pkCreator.setEnabled(false);
		setDateEnable();
		initControlByState();
		initControlByBillState();
		
		if(purchase.getPurchaseState().equals(PurchaseStateEnum.PrePurchaseApply) ) {
			SellProjectInfo sellProject = this.editData.getSellProject();
			Map preMoneySetMap = setting.getPreMoneySetMap();
			PreMoneySetting preMoneySet = (PreMoneySetting)preMoneySetMap.get(sellProject.getId()==null?null:sellProject.getId().toString());
			if(preMoneySet!=null) {
				if(!preMoneySet.getIsLevelModify().booleanValue()) {
					this.txtPreLevelMoney.setEnabled(false);
				}
				if(!preMoneySet.getIsStandModify().booleanValue()) {
					this.txtPrePurchaseAmount.setEnabled(false);
				}
			}
		}	
		//业务单据状态 为认购申请  和 已经审批过了 xin_wang 2010.09.21
		if(PurchaseStateEnum.PurchaseApply.equals(purchase.getPurchaseState())&&purchase.isIsAfterAudit()){
			setBtnStateWhenUnAudit();
		}

		this.storeFields();
		initOldData(this.editData);
		//this.tblCustomerInfo.getColumn("certificateName").getStyleAttributes().setLocked(true);
//		loadCustomerState();
		checkPayedPlanState();
		
		lockCustomerTable(true);
		
	}
	private void lockCustomerTable(boolean isLock) {
		boolean isEnable = isLock;
		for(int i =0; i< this.tblCustomerInfo.getRowCount(); i++){
			IRow row = this.tblCustomerInfo.getRow(i);
			lockCustomerRow(isEnable, row);
		}
	}
	private void lockCustomerRow(boolean isEnable, IRow row) {
		row.getCell("seq").getStyleAttributes().setLocked(isEnable);
		row.getCell("customer").getStyleAttributes().setLocked(isEnable);
		row.getCell("phone").getStyleAttributes().setLocked(isEnable);
		row.getCell("mailAddress").getStyleAttributes().setLocked(isEnable);
		row.getCell("postalcode").getStyleAttributes().setLocked(isEnable);
		row.getCell("certificateName").getStyleAttributes().setLocked(isEnable);
		row.getCell("certificateNumber").getStyleAttributes().setLocked(isEnable);
		row.getCell("bookDate").getStyleAttributes().setLocked(isEnable);
		row.getCell("sex").getStyleAttributes().setLocked(isEnable);
	}
	public void loadCustomerState(){
//		this.tblCustomerInfo.getStyleAttributes().setLocked(true);
		lockCustomerTable(true);
		this.tblCustomerInfo.getColumn("propertyPercent").getStyleAttributes().setLocked(false);
		this.tblCustomerInfo.getColumn("des").getStyleAttributes().setLocked(false);
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		setDateEnable();
		this.btnSelectRoom.setEnabled(true);
		this.btnEditAttach.setEnabled(true);
		this.btnChooseAgio.setEnabled(true);
		this.btnAddPayEntry.setEnabled(true);
		this.btnRemovePayEntry.setEnabled(true);
		this.chkIsFitmentToContract.setEnabled(true);
		this.chkIsSellBySet.setEnabled(true);
		this.btnSelectRoom.setVisible(true);
		this.btnEditAttach.setVisible(true);
		
		/************之前对修改已审批或预定复核状态时的单据控件做了灰显现在要把有些放出来***********/	
		this.txtFitmentAmount.setEnabled(true);
		this.f7Seller.setEnabled(true);
		this.f7secondSeller.setEnabled(true);
		this.f7SincerityPurchase.setEnabled(true);
		this.txtDes.setEnabled(true);
		this.f7PayType.setEnabled(true);
		this.txtPlanSignTimeLimit.setEnabled(true);
		this.pkPurchaseDate.setEnabled(true);
//		this.comboSpecialAgioType.setEnabled(true);
		this.comboDigit.setEnabled(true);
		this.btnLittleAdjust.setEnabled(true);
		this.btnAddPayEntry.setEnabled(true);
		this.btnInsertPayEntry.setEnabled(true);
		this.btnRemovePayEntry.setEnabled(true);
		this.tblPayList.setEditable(true);
		/***************************************** end ******************************************/
		
		this.storeFields();
		initOldData(this.editData);
	}

	public void verifySave() throws BOSException, EASBizException {
		verifyRoom();
		verifyCustomer();
		verifyBaseInfo();
		verifyPrePurchaseTab();
		verifyOtherPayListTab();
		// verifyPrePurchaseBalance();
	}



	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		String oldOprt = this.getOprtState();
		verifySave();
		super.actionSave_actionPerformed(e);
		CacheServiceFactory.getInstance().discardType(new PurchaseCustomerInfoInfo().getBOSType());

		addCommerceTrackRecord(oldOprt, TrackRecordEnum.DestineApp);
		

		this.actionPurchasePrint.setVisible(true);
		this.actionPurchasePrintview.setVisible(true);
		this.actionPurchasePrint.setEnabled(true);
		this.actionPurchasePrintview.setEnabled(true);
		
		setSeller();
		loadCustomerState();
	}

	protected void showSaveSuccess() {
		setMessageText("预定成功!");
		setNextMessageText("预定编辑");
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		setIsShowTextOnly(false);
		showMessage();
	}

	protected void showSubmitSuccess() {
		setMessageText("认购成功");
		setNextMessageText("认购编辑");
		setIsShowTextOnly(false);
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		showMessage();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		long s = System.currentTimeMillis();
		verifySubmit();
		logger.debug("verifySubmit spend:" + (System.currentTimeMillis() - s));
		if (this.editData.getPurchaseState().equals(PurchaseStateEnum.PurchaseAudit)) {
			// 如果是已经审核，则更新付款计划.以及客户
			this.storePayList();
			this.storeOtherPayList();
			this.storeCustomerInfo();
			PurchaseInfo purchase = this.editData;
			PurchaseFactory.getRemoteInstance().update(new ObjectUuidPK(purchase.getId()), purchase);
			this.showSubmitSuccess();
			this.handleOldData();
		} else {
			String oldOprt = this.oprtState;
			this.setOprtState("EDIT");
			super.actionSubmit_actionPerformed(e);

			addCommerceTrackRecord(oldOprt, TrackRecordEnum.PurchaseApp);
		}
		CacheServiceFactory.getInstance().discardType(new PurchaseCustomerInfoInfo().getBOSType());
		
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
		loadCustomerState();
//		setSeller();
	}

	// 商机关联业务单据包括,认购单 新增提交后增加商机跟进
	public void addCommerceTrackRecord(String oprtState, TrackRecordEnum trackResult) {
		if (oprtState.equals(OprtState.ADDNEW)) {
			List list = new ArrayList();
			for (int i = 0; i < this.tblCustomerInfo.getRowCount(); i++) {
				IRow row = this.tblCustomerInfo.getRow(i);
				//by zgy 替换原来逻辑，根据分录id列来取客户数，从交易表中取数，不从客户资料取数，防止客户资料与交易表同步更新
				FDCCustomerInfo fdcCust = null;
				if(row.getCell("id").getValue()!=null){
					String id  = row.getCell("id").getValue().toString();
					try {
						fdcCust = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
						if (fdcCust != null)
							list.add(fdcCust);
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}else{
					fdcCust = (FDCCustomerInfo) row.getCell("customer").getValue();
					if (fdcCust != null)
						list.add(fdcCust);
				}
			}
			CommerceHelper.addCommerceTrackRecord(this, list, (UserInfo) this.f7Seller.getValue(), trackResult, this.editData.getNumber(), this.editData.getId() == null ? null : this.editData.getId()
					.toString());
		}
	}

	public void verifySubmit() throws BOSException, EASBizException {
		//主要是为了用到该函数校验是否有设置参数		导致修改或提交签约单时的定金是否隶属于房款改变成了售楼设置里的参数，应该取本身的参数
		//setIsEarnestInHouseAmount(this.editData, this.editData.getSellProject()); 这个改动需要改定金的那行的颜色显示以及计算方式，所以要去掉
		
		verifyRoom();
		verifyCustomer();
		verifyBaseInfo();
		verifyPurchaseTab();
		verifyPayListTab();
		verifyOtherPayListTab();
		// verifyPurchaseBalance();
	}



	private void verifyPurchaseTab() {
		if (this.f7PayType.getValue() == null) {
			MsgBox.showInfo("付款方案不能为空!");
			this.abort();
		}
		if (this.pkPurchaseDate.getValue() == null) {
			MsgBox.showInfo("认购申请日期不能为空!");
			this.abort();
		}		
		BigDecimal dealAmount = this.txtContractTotalAmount.getBigDecimalValue();
		if (dealAmount == null || dealAmount.compareTo(FDCHelper.ZERO) <= 0) {
			MsgBox.showInfo("成交价等于0,系统不允许销售!");
			this.abort();
		}
		
		//new add by renliang at 2010-7-24
		if(pkSignDate.getValue()==null){
			MsgBox.showInfo("约定签约日期不能为空!");
			this.abort();
		}
	}

	private void verifyPayListTab() throws BOSException {
		boolean isEarnestInHouseAmount = this.editData.isIsEarnestInHouseAmount();
		BigDecimal dealAmount = this.txtContractTotalAmount.getBigDecimalValue();
		BigDecimal totalAmount = FDCHelper.ZERO;
		Date curDate = null;
		
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
				
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell("moneyName").getValue();
			if (moneyName == null) {
				MsgBox.showInfo("付款明细,第" + (row.getRowIndex() + 1) + "行款项名称没有录入!");
				this.abort();
			} 
			
			//预收款和预订金不统计进去
			if(moneyName.getMoneyType().equals(MoneyTypeEnum.PreMoney) || moneyName.getMoneyType().equals(MoneyTypeEnum.PreconcertMoney))
				continue;
				
			Object amount = row.getCell("amount").getValue();
			if (amount == null || !(amount instanceof BigDecimal) || ((BigDecimal)amount).compareTo(FDCHelper.ZERO) == 0) {
				MsgBox.showInfo("付款明细,第" + (row.getRowIndex() + 1) + "行应收金额没有录入!");
				this.abort();
			}
			
			if(!isEarnestInHouseAmount && moneyName.getMoneyType().equals(MoneyTypeEnum.EarnestMoney)){
				//如果定金不计入房款，则不同统计定金
			   }else{
//				   if(moneyName.getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
//					   totalAmount = totalAmount.add(FDCHelper.toBigDecimal(txtLoanAmount.getText()));
//				   }else if(moneyName.getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
//					   totalAmount = totalAmount.add(FDCHelper.toBigDecimal(txtAFundAmount.getText()));
//				   }else{
					   totalAmount = totalAmount.add((BigDecimal)amount);
					   System.out.println("********"+amount);
				   //}
			}
			
			Date date = (Date) row.getCell("date").getValue();
			if (date == null) {
				MsgBox.showInfo("付款明细,第" + (row.getRowIndex() + 1) + "行日期必须录入!");
				this.abort();
			}
			date = FDCDateHelper.getDayBegin(date);
			if (curDate == null) {
				curDate = date;
			} else {
				if (date.before(curDate)) {
					MsgBox.showInfo("付款明细,第" + (row.getRowIndex() + 1) + "行日期录入不能小于前面行的日期!");
					this.abort();
				} else {
					curDate = date;
				}
			}
		}

		if (dealAmount == null) {
			dealAmount = FDCHelper.ZERO;
		}
		if (totalAmount.compareTo(dealAmount) != 0) {
			MsgBox.showInfo("付款明细总额不等于成交金额!("+totalAmount+"!="+dealAmount+")");
			logger.info("*****认购单提交-----付款明细总额不等于成交金额!("+totalAmount+"!="+dealAmount+")");
			this.abort();
		}
	}

	/**
	 * 对于已结算的期间，不允许进行认购款及修改
	 * */
	private void verifyBalance(Date bizDate) {
		Date balanceEndDate = null;
		SellProjectInfo sellProject = (SellProjectInfo) this.txtProject.getUserObject();
		PurchaseInfo info = (PurchaseInfo) this.editData;
		if (sellProject == null) {
			if (info.getRoom() != null && info.getRoom().getBuilding() != null && info.getRoom().getBuilding().getSellProject() != null) {
				sellProject = info.getRoom().getBuilding().getSellProject();
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
		detailBuilder.appendSql("select FBalanceEndDate from T_SHE_SellProject where FID = ?");
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

	private void verifyOtherPayListTab() {
		// Set moneySet = new HashSet(); //款项名称不能重复收
		for (int i = 0; i < this.tblOtherPay.getRowCount(); i++) {
			IRow row = this.tblOtherPay.getRow(i); // MoneyDefine
			Object moneyDefine = row.getCell("moneyName").getValue();
			if (moneyDefine == null) {
				MsgBox.showInfo("其它应付,第" + (row.getRowIndex() + 1) + "行'款项名称'没有录入!");
				this.abort();
			}
			Object date = row.getCell("date").getValue();
			if (date == null) {
				MsgBox.showInfo("其它应付,第" + (row.getRowIndex() + 1) + "行'日期'没有录入!");
				this.abort();
			}
			Object amount = row.getCell("amount").getValue();
			if (amount == null || !(amount instanceof BigDecimal) || ((BigDecimal)amount).compareTo(FDCHelper.ZERO)<=0) {
				MsgBox.showInfo("其它应付,第" + (row.getRowIndex() + 1) + "行'金额'没有录入!");
				this.abort();
			}
		}

	}

	private void verifyRoom() {
		RoomInfo room = (RoomInfo) txtRoomNumber.getUserObject();
		if (txtRoomNumber.getUserObject() == null) {
			MsgBox.showInfo("没有选择房间!");
			this.abort();
		}
		// 增加抵押关联控制
		// 取得抵押控制设置
		HashMap functionSetMap = (HashMap)setting.getFunctionSetMap();
		if (functionSetMap == null)
			functionSetMap = new HashMap();
		if (room != null && room.getBuilding() != null && room.getBuilding().getSellProject() != null) {
			boolean debug = false;
			// 强制底价控制
			boolean basePriceDebug = false;
			String sellProjectId = room.getBuilding().getSellProject().getId().toString();

			FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(sellProjectId);
			if (funcSet == null) {
				logger.warn("没有该项目的参数设置...");
			} else {
				if(funcSet.getIsMortagage()!=null) 
					debug = funcSet.getIsMortagage().booleanValue();
				if(funcSet.getIsBasePrice()!=null)
					basePriceDebug = funcSet.getIsBasePrice().booleanValue();
			}
			// 底价强制判断
			if (basePriceDebug) {
				BigDecimal dealBuildingPrice = this.txtDealBuildPrice.getBigDecimalValue();
				if (dealBuildingPrice == null)
					dealBuildingPrice = FDCHelper.ZERO;
				else
					dealBuildingPrice = dealBuildingPrice.setScale(2, BigDecimal.ROUND_HALF_UP);

				BigDecimal temp = room.getBaseBuildingPrice();
				if (temp == null)
					temp = FDCHelper.ZERO;
				else
					temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);

				if (dealBuildingPrice.compareTo(temp) < 0) {
					MsgBox.showWarning("成交建筑单价不能低于建筑底价！");
					this.abort();
				}
			}

			// 抵押判断
			if (room.isIsMortagaged()) {
				if (debug) {
					MsgBox.showWarning("该房间已经设置抵押控制，不能进行认购操作！");
					this.abort();
				} else {
					if (MsgBox.showConfirm2New(this, "该房间已经抵押,是否继续?") != MsgBox.YES) {
						this.abort();
					}
				}
			}

		}
	}

	private void verifyBaseInfo() throws BOSException, EASBizException {
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("认购单业务编号不能为空!");
			this.abort();
		}else if(!this.txtNumber.isEnabled()){//编码规则启用断号时的 处理
			return ;
		}
		
	
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", this.txtNumber.getText()));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.ChangeRoomBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.ManualBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.NoPayBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.QuitRoomBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.AdjustBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", this.editData.getOrgUnit().getId()));
		if (this.editData.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
		}
		if (PurchaseFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showError("认购单业务编号重复!");
			this.abort();
		}
		if (this.f7Seller.getValue() == null) {
			MsgBox.showInfo("销售顾问必须录入!");
			this.abort();
		}
	}

	private void verifyPrePurchaseTab() {
		BigDecimal prePurAmount = this.txtPrePurchaseAmount.getBigDecimalValue();
		// if (prePurAmount != null && prePurAmount.compareTo(dealAmount) > 0) {
		// MsgBox.showInfo("预定金额大于成交价!");
		// this.abort();
		// }
		/**
		 * 取消预订标准金额不能为零的校验
		 * by renliang at 2010-9-10
		 */
		//		if ((prePurAmount == null || prePurAmount.compareTo(FDCHelper.ZERO) == 0)) {
		//			String errMsg = null;
		//			if(prePurAmount == null){
		//				errMsg = "预订标准金额不能为空!";
		//				FDCMsgBox.showError(errMsg);
		//				this.abort();
		//			}
		//			
		//			/*else if(prePurAmount.compareTo(FDCHelper.ZERO) == 0){
		//				errMsg = "预订标准金额不能为零!";
		//			}*/
		//			
		//			
		//			//FDCMsgBox.showError(errMsg);
		//			//this.abort();
		//		}
		if (prePurAmount == null) {
			MsgBox.showInfo("预订标准金额不能为空!");
			this.abort();
		}
		
		if(this.pkPrePurchaseDate.getValue()==null) {
			MsgBox.showInfo("预订申请日期不能为空！");
			this.abort();
		}
		
		/**
		 * 增加预定有效截止期必须大于或等于预定申请日期的校验
		 * by renliang at  2010-12-3
		 */
		if(this.pkPreValidDate.getValue()!=null){
			Date pkPrePurchaseDate = DateTimeUtils.truncateDate((Date)this.pkPrePurchaseDate.getValue());
			Date pkPreValidDate = DateTimeUtils.truncateDate((Date)this.pkPreValidDate.getValue());

			
			if(pkPreValidDate.compareTo(pkPrePurchaseDate) < 0 ){
				MsgBox.showInfo(this,"预定有效截止期必须大于或等于预定申请日期，请重新输入！");
				this.abort();
			}
		}
			
		BigDecimal preLevelAmount = this.txtPreLevelMoney.getBigDecimalValue();
		//if(preLevelAmount==null || prePurAmount.compareTo(FDCHelper.ZERO)<=0 || preLevelAmount.compareTo(FDCHelper.ZERO)<0) {
		if(preLevelAmount==null || prePurAmount.compareTo(FDCHelper.ZERO)<0 || preLevelAmount.compareTo(FDCHelper.ZERO)<0) {
			MsgBox.showInfo("请设置预订最低金额>=0和预订标准金额>=0！");
			this.abort();
		}
		if(preLevelAmount.compareTo(prePurAmount)>0) {
			MsgBox.showInfo("预订最低金额不能大于预订标准金额！");
			this.abort();
		}
	}

	private void verifyCustomer() throws BOSException, EASBizException {
		BigDecimal percent = FDCHelper.ZERO;
		Map customerMap = new HashMap();
		if (this.tblCustomerInfo.getRowCount() == 0) {
			MsgBox.showInfo("没有认购客户,请添加!");
			this.abort();
		}
		for (int i = 0; i < this.tblCustomerInfo.getRowCount(); i++) {
			IRow row = this.tblCustomerInfo.getRow(i);
			if (row.getCell("propertyPercent").getValue() == null) {
				MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户没有设置产权比例!");
				this.abort();
			}else{
				//添加单个产权比例不能等于0的判断  by renliang at 2010-12-22
				BigDecimal propertyPercent = (BigDecimal) row.getCell("propertyPercent").getValue();
				if (propertyPercent.compareTo(new BigDecimal("0")) == 0) {
					MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户的产权比例不能等于0!");
					this.abort();
				}
		
			}
			
			// 身份证件号码验证
			if (row.getCell("certificateNumber").getValue() == null) {
				MsgBox.showWarning("请填写证件号码!");
				SysUtil.abort();
			}
			//by zgy 替换原来逻辑，根据分录id列来取客户数，从交易表中取数，不从客户资料取数，防止客户资料与交易表同步更新
			FDCCustomerInfo customer = null;
			if(row.getCell("id").getValue()!=null){
				String id  = row.getCell("id").getValue().toString();
				if(id!=null){
					try {
						customer  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}

			}else{
				customer = (FDCCustomerInfo) row.getCell("customer").getValue();
			}
			if (customer == null) {
				MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户没有录入!");
				this.abort();
			}
			if (customerMap.containsKey(customer)) {
				MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户重复!");
				this.abort();
			} else {
				customerMap.put(customer, customer);
			}

			// 在客户证件和电话未进行修改时,不进行验证
			/*CertifacateNameEnum nameEnum = (CertifacateNameEnum) row.getCell("certificateName").getValue();
			String certificateNum = (String) row.getCell("certificateNumber").getValue();
			if (!FDCHelper.isEmpty(certificateNum) && nameEnum != null) {
				if (!nameEnum.equals(customer.getCertificateName()) || !certificateNum.equals(customer.getCertificateNumber())) {
					if (!FDCCustomerHelper.verifyCertificateNumber(nameEnum, certificateNum, customer.getId().toString())) {
						this.abort();
					}
				}
			}*/
			String certificateNum = (String) row.getCell("certificateNumber").getValue();
			
			if(row.getCell("certificateName").getValue()!=null){
				CertificateInfo info  = (CertificateInfo)row.getCell("certificateName").getValue();
				if("001".equals(info.getNumber())){
					if(certificateNum.trim().length()==15 || certificateNum.trim().length()==18){
						try {
							if(!CertificateUtil.CardValidate(certificateNum)){
								FDCMsgBox.showWarning(this,"身份证号码不符合规则，请重新录入");
								SysUtil.abort();
							}
						} catch (ParseException e) {
							logger.error(e.getMessage());
						}
					}
				}
			}
			//new update by renliang at 2011-3-2
			
			if(row.getCell("certificateName").getValue()!=null){
				CertificateInfo info =(CertificateInfo)row.getCell("certificateName").getValue();
				CertificateInfo infoNew = customer.getCertificateName();
				if(!FDCHelper.isEmpty(certificateNum) && info != null && infoNew !=null ){
					if(info.getId()!=null && infoNew.getId()!=null && info.getId().toString().equals(infoNew.getId().toString())){
						if (!FDCCustomerHelper.verifyCertificateNumber(info.getId().toString(), certificateNum, customer.getId().toString())) {
							this.abort();
						}
					}
				}
			}
			
			String phone = (String) row.getCell("phone").getValue();
			if (phone == null || phone.trim().equals("")) {
				MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户联系电话不能为空!");
				this.abort();
			}
//			if (!phone.equals(customer.getPhone())) {
//				FDCCustomerInfo otherCust = customer;
				FDCCustomerInfo otherCust = (FDCCustomerInfo) customer.clone();
				otherCust.setPhone(phone);
				Map rMap = FDCCustomerFactory.getRemoteInstance().verifySave(otherCust, false);
				otherCust = null;
				boolean isAbort = FDCCustomerHelper.verifyPhoneAndName(rMap, this);
				if (isAbort) {
//					row.getCell("phone").setValue(customer.getPhone());
					this.abort();
				}
//			}

			percent = percent.add((BigDecimal) row.getCell("propertyPercent").getValue());
		}
		if (percent.compareTo(new BigDecimal("100")) != 0) {
			MsgBox.showInfo("产权比例不等100%!");
			this.abort();
		}

		RoomInfo room = (RoomInfo) txtRoomNumber.getUserObject();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("customerInfo.*");
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.ChangeRoomBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.ManualBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.NoPayBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.QuitRoomBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.AdjustBlankOut, CompareType.NOTEQUALS));
		if (this.editData.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", this.editData.getId().toString(), CompareType.NOTEQUALS));
		}
		PurchaseCollection purchases = PurchaseFactory.getRemoteInstance().getPurchaseCollection(view);
		for (int i = 0; i < purchases.size(); i++) {
			PurchaseInfo pur = purchases.get(i);
			PurchaseCustomerInfoCollection customerInfos = pur.getCustomerInfo();
			for (int j = 0; j < customerInfos.size(); j++) {
				PurchaseCustomerInfoInfo customerInfoInfo = customerInfos.get(j);
				if (customerInfoInfo.getCustomer() != null) {
					String aCusId = customerInfoInfo.getCustomer().getId().toString();
					for (int k = 0; k < this.tblCustomerInfo.getRowCount(); k++) {
						IRow row = this.tblCustomerInfo.getRow(k);
						 //by zgy 替换原来逻辑，根据分录id列来取客户数，从交易表中取数，不从客户资料取数，防止客户资料与交易表同步更新
						if(row.getCell("id").getValue()!=null){
							String id  = row.getCell("id").getValue().toString();
							if(id!=null){
								try {
									FDCCustomerInfo	customer  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
									if (customer.getId().toString().equals(aCusId)) {
										MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户已经认购过了该房间!");
										this.abort();
									}
								} catch (EASBizException e) {
									e.printStackTrace();
								} catch (BOSException e) {
									e.printStackTrace();
								}
							}
						}else{
							FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell("customer").getValue();
							if (customer.getId().toString().equals(aCusId)) {
								MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户已经认购过了该房间!");
								this.abort();
							}
						}
					}
				}
			}
		}

		// 通过参数控制
		if (this.editData.getId() == null && purchases.size() > 0) {
			boolean debug = false;
			boolean debug2 =true;
			try {
				String id = room.getBuilding().getSellProject().getId().toString();
				HashMap functionSetMap = (HashMap)setting.getFunctionSetMap();
				FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(id);
				if (funcSet == null) {
					logger.warn("没有该销售项目对应的认购设置参数！");
				} else {
					if(funcSet.getIsActGathering()!=null){
						debug = funcSet.getIsActGathering().booleanValue();
					}
					if(funcSet.getIsActGathering().booleanValue()
							|| funcSet.getIsSignGathering().booleanValue()){
						debug2 = false ;
					}else{
						debug2 = true ;
					}	
				}
			} catch (Exception e1) {
				debug = true;
				logger.warn("获取该房间对应的销售项目，签约设置参数发生了错误，默认以实际收款为准！");
			}
			if (debug) {
				if (MsgBox.showConfirm2New(this, "已经有人预定/认购该房间,是否继续?") != MsgBox.YES) {
					this.abort();
				}
			} else if(!debug &&!debug2){
				MsgBox.showWarning("已经有人预定/认购该房间！");
				this.abort();
			}
		}
	}

	public void storeFields() {
		super.storeFields();

		PurchaseInfo info = (PurchaseInfo) this.editData;
		info.setIsAutoToInteger(this.currAgioParam.isToInteger());
		info.setIsBasePriceSell(this.currAgioParam.isBasePriceSell());
		info.setToIntegerType(this.currAgioParam.getToIntegerType());
		info.setDigit(this.currAgioParam.getDigit());
		info.setPriceAccountType(this.currAgioParam.getPriceAccountType());
        if(!StringUtils.isEmpty(this.txtNumber.getText())){
        	info.setNumber(this.txtNumber.getText());
        }
		info.setName(this.txtNumber.getText());
		info.setSpecialAgioType((SpecialAgioEnum)this.comboSpecialAgioType.getSelectedItem());
		info.setSpecialAgio(this.txtSpecialAgio.getBigDecimalValue());
		info.setRoom((RoomInfo) this.txtRoomNumber.getUserObject());
		info.setIsSellBySet(this.chkIsSellBySet.isSelected());
		Date purchaseDate = (Date) this.pkPurchaseDate.getValue();
		if(purchaseDate!=null) {
			info.setPurchaseDate(purchaseDate);
		}
		if (info.getRoom() != null && info.getRoom().getBuilding() != null && info.getRoom().getBuilding().getSellProject() != null) {
			SellProjectInfo project = info.getRoom().getBuilding().getSellProject();
			info.setSellProject(project);
			try {
				MarketingUnitInfo market = SHEHelper.getMarketingUnit(userInfo, project);
				info.setMarketUnit(market);
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}		
				
		info.setPurchaseCurrency((CurrencyInfo) this.f7PurchaseCurrency.getValue());
		info.setPurchaseAmount(this.txtPurchaseAmount.getBigDecimalValue());
		info.setFitmentStandard(this.txtFitmentStandard.getText());
		info.setIsFitmentToContract(this.chkIsFitmentToContract.isSelected());
		info.setFitmentPrice(this.txtFitmentPrice.getBigDecimalValue());
		info.setFitmentAmount(this.txtFitmentAmount.getBigDecimalValue());
		info.setPayType((SHEPayTypeInfo) this.f7PayType.getValue());
		info.setDealCurrency((CurrencyInfo) this.f7DealCurrency.getValue());
		info.setAttachmentAmount(this.txtAttachmentAmount.getBigDecimalValue());
		// info.setDealPrice(this.txtDealPrice.getBigDecimalValue());
		info.setContractTotalAmount(this.txtContractTotalAmount.getBigDecimalValue());
		info.setContractBuildPrice(this.txtContractBuildPrice.getBigDecimalValue());
		info.setContractRoomPrice(this.txtContractRoomPrice.getBigDecimalValue());
		info.setDealAmount(this.txtDealTotalAmount.getBigDecimalValue());
		info.setDealBuildPrice(this.txtDealBuildPrice.getBigDecimalValue());
		info.setDealRoomPrice(this.txtDealRoomPrice.getBigDecimalValue());
		if (info.getRoom() != null){
			if(info.getRoom().isIsCalByRoomArea())
				info.setDealPrice(info.getDealRoomPrice());
			else
				info.setDealPrice(info.getDealBuildPrice());
		}
		
		info.setStandardTotalAmount(this.txtStandardTotalAmount.getBigDecimalValue());

		info.setLoanAmount(this.txtLoanAmount.getBigDecimalValue());
		info.setPrePurchaseValidDate((Date) this.pkPreValidDate.getValue());
		info.setAreaCompensateAmount(this.txtAreaCompensateAmount.getBigDecimalValue());
		
		
		
		
		//添加签约时限的保存 by renliang at 2010-7-20
		int a = 0;
		if(this.txtPlanSignTimeLimit.getIntegerValue()==null)
		{
			info.setPlanSignTimeLimit(a);
		}else
		{
			info.setPlanSignTimeLimit(this.txtPlanSignTimeLimit.getIntegerValue().intValue());
		}
		
		
		
		info.getSecondSaleMan().clear();
		Object ob[] = (Object[])this.f7secondSeller.getValue();
		if(ob!=null)
		{
			for(int i=0;i<ob.length;i++)
			{
				if(ob[i] instanceof UserInfo)
				{
					PurchaseSaleManInfo secondInfo = new PurchaseSaleManInfo();
					UserInfo user = (UserInfo)ob[i];
					secondInfo.setSecondMan(user);
					info.getSecondSaleMan().add(secondInfo);
				}
			}
		}		
		info.setSalesman((UserInfo) this.f7Seller.getValue());
		info.setAccumulationFundAmount(this.txtAFundAmount.getBigDecimalValue());

		PurchaseRoomAttachmentEntryCollection purchaseRooms = (PurchaseRoomAttachmentEntryCollection) this.txtAttachRoom.getUserObject();
		info.getAttachmentEntries().clear();
		for (int i = 0; i < purchaseRooms.size(); i++) {
			info.getAttachmentEntries().add(purchaseRooms.get(i));
		}

		AgioEntryCollection agioEntrys = (AgioEntryCollection)this.txtAgio.getUserObject();
		info.getAgioEntrys().clear();
		for (int i = 0; i < agioEntrys.size(); i++) {
			PurchaseAgioEntryInfo entryInfo = (PurchaseAgioEntryInfo)agioEntrys.get(i);
			entryInfo.setHead(this.editData);
			info.getAgioEntrys().add(entryInfo);
		}
		storeCustomerInfo();
		storePayList();
		storeOtherPayList();

		// 清楚查看界面增加的頁簽和付款明細的列
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			if (tabActualPayTable != null) {
				tabPurchase.remove(tabActualPayTable);
				tabActualPayTable = null;
			}
			if (tabTotalFlowTable != null) {
				tabPurchase.remove(tabTotalFlowTable);
				tabTotalFlowTable = null;
			}

			if (tblPayList.getColumn("actAmount") != null) {
//				tblPayList.removeColumn(tblPayList.getColumn("actAmount").getColumnIndex());    在签约单需要获取实收金额，这里将这个删除列注释掉,改为隐藏列
				tblPayList.getColumn("actAmount").getStyleAttributes().setHided(true);
			}
			if (tblPayList.getColumn("actDate") != null) {
//				tblPayList.removeColumn(tblPayList.getColumn("actDate").getColumnIndex());
				tblPayList.getColumn("actDate").getStyleAttributes().setHided(true);
			}

			btnSelectRoom.setVisible(true);
			btnViewRoom.setVisible(false);
			this.tblOtherPay.getColumn("actAmount").getStyleAttributes().setHided(true);
			this.tblOtherPay.getColumn("actDate").getStyleAttributes().setHided(true);
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			if (tabActualPayTable != null) {
				tabPurchase.remove(tabActualPayTable);
				tabActualPayTable = null;
			}
			if (tabTotalFlowTable != null) {
				tabPurchase.remove(tabTotalFlowTable);
				tabTotalFlowTable = null;
			}
			if (tblPayList.getColumn("actAmount") != null) {
//				tblPayList.removeColumn(tblPayList.getColumn("actAmount").getColumnIndex());     在签约单需要获取实收金额，这里将这个删除列注释掉,改为隐藏列
//				tblPayList.getColumn("actAmount").getStyleAttributes().setHided(true);
			}
			if (tblPayList.getColumn("actDate") != null) {
//				tblPayList.removeColumn(tblPayList.getColumn("actDate").getColumnIndex());
//				tblPayList.getColumn("actDate").getStyleAttributes().setHided(true);
			}
			btnSelectRoom.setVisible(true);
			btnViewRoom.setVisible(false);
			this.tblOtherPay.getColumn("actAmount").getStyleAttributes().setHided(false);
			this.tblOtherPay.getColumn("actDate").getStyleAttributes().setHided(false);
		} else if (STATUS_VIEW.equals(this.oprtState) || STATUS_FINDVIEW.equals(this.oprtState)) {
			// 查看的时候显示下面两列 12-30 kuangbiao modify
			initInfo();
			addTotalFlowTable();
			addActPayTable();
			btnSelectRoom.setVisible(false);
			btnViewRoom.setVisible(true);
			this.tblOtherPay.getColumn("actAmount").getStyleAttributes().setHided(false);
			this.tblOtherPay.getColumn("actDate").getStyleAttributes().setHided(false);
			// end
		}
	}

	public void storeCustomerInfo() {
		PurchaseInfo pur = this.editData;
		PurchaseCustomerInfoCollection customerInfos = pur.getCustomerInfo();
		PurCustomerCollection purCuss = pur.getPurCustomer();
		//保存交易客户信息到交易信息表   by zgy 2010-12-03
		PurCustomerInfo purCusinfo = null;
		purCuss.clear();
		customerInfos.clear();
		PurchaseCustomerInfoInfo customerInfo = null;
		FDCCustomerInfo customer = null;
		for (int i = 0; i < this.tblCustomerInfo.getRowCount(); i++) {
			IRow row = this.tblCustomerInfo.getRow(i);
			purCusinfo = new PurCustomerInfo();   //by zgy   
			customerInfo = (PurchaseCustomerInfoInfo) row.getUserObject();
			 //by zgy 替换原来逻辑，根据分录id列来取客户数，从交易表中取数，不从客户资料取数，防止客户资料与交易表同步更新
			if(row.getCell("id").getValue()!=null){
				String id  = row.getCell("id").getValue().toString();
				if(id!=null){
					try {
						customer  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}
			}else{
				customer = (FDCCustomerInfo) row.getCell("customer").getValue();
			}
			if (customer != null) {
				customer.setMailAddress((String) row.getCell("mailAddress")
						.getValue());
				customer.setPhone((String) row.getCell("phone").getValue());
				customer.setPostalcode((String) row.getCell("postalcode")
						.getValue());
				if (row.getCell("certificateName").getValue() != null) {
					 CertificateInfo info= (CertificateInfo)row.getCell("certificateName").getValue();
					 if(info!=null){
						 customer.setCertificateName(info);
					 }
				}
				customer.setCertificateNumber((String) row.getCell(
						"certificateNumber").getValue());
				/**
				 * 添加性别 by xiu_xiaoao at 2010-9-19
				 */
				customer.setSex((SexEnum) row.getCell("sex").getValue());
				customer.setCustomerType((CustomerTypeEnum)row.getCell("customerType").getValue());
				if(row.getCell("bookDate").getValue() !=null && !"".equals(row.getCell("bookDate").getValue())){
					customer.setCreateTime(new Timestamp(((Date)row.getCell("bookDate").getValue()).getTime()));
				}
				// ---Number字段作为标志位，审批后不允许修改 -
				// customerInfo.setNumber(customer.getNumber());
				// customerInfo.setNumber(customer.getName());
				// -------------------
			}
			// 保存交易信息 by zgy
			purCusinfo.setSeq(i);
			purCusinfo.setParent(pur);
			purCusinfo.setPropertyPercent((BigDecimal) row.getCell(
					"propertyPercent").getValue());
			purCusinfo.setCustomerName(row.getCell("customer").getValue()
					.toString());
			purCusinfo.setCustomerID(customer.getId());
			purCusinfo.setTel((String) row.getCell("phone").getValue());
			purCusinfo.setPostalcode((String) row.getCell("postalcode")
					.getValue());
			purCusinfo.setMailAddress((String) row.getCell("mailAddress")
					.getValue());
			purCusinfo.setDescription((String) row.getCell("des").getValue());
			/*if (row.getCell("certificateName").getValue() != null) {
				purCusinfo.setCertificateName((CertifacateNameEnum) row
						.getCell("certificateName").getValue());
			}*/
			//new update by renliang at 2011-3-2
			if (row.getCell("certificateName").getValue() != null) {
				CertificateInfo info  = (CertificateInfo)row.getCell("certificateName").getValue();
				purCusinfo.setCertificateName(info);
			}
			purCusinfo.setCertificateNumber((String) row.getCell(
					"certificateNumber").getValue());
			purCusinfo.setSex((SexEnum) row.getCell("sex").getValue());
			if(row.getCell("bookDate").getValue() != null){
				if(row.getCell("bookDate").getValue() instanceof Date){
					purCusinfo.setCreateTime(new Timestamp(((Date)row.getCell("bookDate").getValue()).getTime()));
				}else if(row.getCell("bookDate").getValue() instanceof Timestamp){
					purCusinfo.setCreateTime((Timestamp)row.getCell("bookDate").getValue());
				}
			}
			

			customerInfo.setSeq(i);
			customerInfo.setCustomer(customer);
			customerInfo.setPropertyPercent((BigDecimal) row.getCell(
					"propertyPercent").getValue());
			customerInfo.setDescription((String) row.getCell("des").getValue());
			customerInfos.add(customerInfo);
			purCuss.add(purCusinfo);

		}
		// 认购客户按照产权比例进行排序 090310
		//sortByPropertyPercent(customerInfos);
		
		//认购客户按照客户排序
		sortBySeq(customerInfos);
		//保存时更新叙事部列表的客户名称列表  by zgy
		sortBySeq_purcustomer(purCuss);

		StringBuffer customerNames = new StringBuffer();
		StringBuffer customerPhones = new StringBuffer();
		StringBuffer customerIDCards = new StringBuffer();
		for (int i = 0; i < customerInfos.size(); i++) {
			PurchaseCustomerInfoInfo purchaseCustomer = customerInfos.get(i);
			PurCustomerInfo purCusInfo = purCuss.get(i);
			//by zgy 替换成客户交易表中取数
//			String addName = purchaseCustomer.getCustomer().getName();
			String addName = purCusInfo.getCustomerName();
			if (addName == null)
				addName = "";
			customerNames.append(addName + "; ");
			String addPhone = purchaseCustomer.getCustomer().getPhone();
			if (addPhone == null)
				addPhone = "";
			String addTel = purchaseCustomer.getCustomer().getTel();
			if (addTel == null)
				addTel = "";
			customerPhones.append(addPhone + "," + addTel + "; ");
			String addIDCards = purchaseCustomer.getCustomer().getCertificateNumber();
			if(addIDCards == null)
				addIDCards = "";
			customerIDCards.append(addIDCards +"; ");
		}
		if(customerIDCards.toString().length()>200)
		{
			MsgBox.showInfo("客户证件号码超长，请重新输入!");
			this.abort();
		}
		if(customerNames.toString().length()>80){
			MsgBox.showInfo("客户名称超长，请重新输入!");
			this.abort();
		}
		if(customerPhones.toString().length()>80){
			MsgBox.showInfo("客户电话超长，请重新输入!");
			this.abort();
		}
		if(customerNames.toString()!=null && customerNames.toString().length()>0){
			pur.setCustomerNames(customerNames.toString());
		}
		
		if(customerPhones.toString()!=null && customerPhones.toString().length()>0){
			pur.setCustomerPhones(customerPhones.toString());
		}
		if(customerIDCards.toString()!=null && customerIDCards.toString().length()>0){
			pur.setCustomerIDCards(customerIDCards.toString());
		}
		
		
	}

	private void storePayList() {
		PurchaseInfo pur = this.editData;
		pur.getPayListEntry().clear();
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			PurchasePayListEntryInfo entry = (PurchasePayListEntryInfo) row.getUserObject();
			entry.setSeq(i);
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell("moneyName").getValue();
			entry.setMoneyDefine(moneyName);
			entry.setAppDate((Date) row.getCell("date").getValue());
			entry.setCurrency((CurrencyInfo) row.getCell("currency").getValue());
			entry.setAppAmount((BigDecimal) row.getCell("amount").getValue());
			entry.setTerm(((Integer) row.getCell("term").getValue()).intValue());
			entry.setMonthInterval(((Integer) row.getCell("jiange").getValue()).intValue());
			entry.setDescription((String) row.getCell("des").getValue());
			if(entry.getId()==null) entry.setRevMoneyType(RevMoneyTypeEnum.AppRev);
			if(entry.getRevMoneyType()==null) entry.setRevMoneyType(RevMoneyTypeEnum.AppRev);
//			加入参数控制 eric_wang 2010.08.19
//			if(setting.getIsPreToOtherMoneyMap()!=null&&setting.getIsPreToOtherMoneyMap().get("isPreToOtherMoney")!=null&&(((Boolean)setting.getIsPreToOtherMoneyMap().get("isPreToOtherMoney")).booleanValue())){
			if(!moneyName.getMoneyType().equals(MoneyTypeEnum.PreconcertMoney))
			entry.setIsRemainCanRefundment(false);  //默认剩余金额不可退 (排除预订转认购的预订金情况)
//			}
			pur.getPayListEntry().add(entry);
		}
	}

	private void storeOtherPayList() {
		PurchaseInfo pur = this.editData;
		pur.getElsePayListEntry().clear();
		for (int i = 0; i < this.tblOtherPay.getRowCount(); i++) {
			IRow row = this.tblOtherPay.getRow(i);
			PurchaseElsePayListEntryInfo entry = (PurchaseElsePayListEntryInfo) row.getUserObject();
			entry.setSeq(i);
			entry.setMoneyDefine((MoneyDefineInfo) row.getCell("moneyName").getValue());
			entry.setAppDate((Date) row.getCell("date").getValue());
			entry.setCurrency((CurrencyInfo) row.getCell("currency").getValue());
			entry.setAppAmount((BigDecimal) row.getCell("amount").getValue());
			entry.setDescription((String) row.getCell("des").getValue());
			if(entry.getId()==null) entry.setRevMoneyType(RevMoneyTypeEnum.AppRev);
			if(entry.getRevMoneyType()==null) entry.setRevMoneyType(RevMoneyTypeEnum.AppRev);
			entry.setIsRemainCanRefundment(true);	//默认剩余金额可退
			entry.setIsCanRevBeyond(false);
			
			pur.getElsePayListEntry().add(entry);
		}
	}

	/**
	 * 判断是否是预定操作
	 */
	private boolean isPrePurchase() {
		Boolean bol = (Boolean) this.getUIContext().get("isPrePurchase");
		if (bol == null) {
			return false;
		}
		return bol.booleanValue();
	}

	protected IObjectValue createNewData() {
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		
		Map funcSetMap = setting.getFunctionSetMap();
		FunctionSetting funSet = (FunctionSetting)funcSetMap.get(sellProject==null?null:sellProject.getId().toString());
		if(funSet != null){
			if(!funSet.getIsEditPurAndSignDate().booleanValue()){
				this.pkPurchaseDate.setValue(new Date());
				this.pkPurchaseDate.setEnabled(false);
			}else{
				this.pkPurchaseDate.setEnabled(true);
			}
		}
		
		this.comboDigit.setSelectedItem(SHEHelper.THOUSHAND_DIGIT);
		
		PurchaseInfo purchase = new PurchaseInfo();
		//设置认购单创建时，设置的认购业务以实际收款为准参数值
		if(funSet != null){
			if(funSet.getIsActGathering().booleanValue()){
				purchase.setSourceFunction("true");
			}else{
				purchase.setSourceFunction("false");
			}
		}
		// ---默认设置为自动取整,百位舍零,总价反算单价
		purchase.setIsAutoToInteger(this.currAgioParam.isToInteger());
		purchase.setToIntegerType(this.currAgioParam.getToIntegerType());
		purchase.setDigit(this.currAgioParam.getDigit());
		purchase.setPriceAccountType(this.currAgioParam.getPriceAccountType());
		purchase.setIsBasePriceSell(this.currAgioParam.isBasePriceSell());
		
		// -----------------
		Timestamp currentTime = SHEHelper.getCurrentTime();
		purchase.setCreateTime(currentTime);
//		当房间 为实测符合的时候销售方式默认为现售  xiaoao_liu
		if(this.getUIContext().get("room")!=null){
			RoomInfo purroom=(RoomInfo)this.getUIContext().get("room");
			if(purroom.isIsActualAreaAudited()){
				purchase.setSellType(SellTypeEnum.LocaleSell);
			}else{
			    purchase.setSellType(SellTypeEnum.PreSell);// 默认为预售
			} 
		}else{
			purchase.setSellType(SellTypeEnum.PreSell);// 默认为预售
		}
		
		purchase.setIsSellBySet(false);
//		purchase.setIsAfterAudit(false);
		purchase.setPrePurchaseAuditDate(null);
		UserInfo userInfo = (UserInfo) this.getUIContext().get("salesMan");
		if(userInfo!=null)
		{
			purchase.setSalesman(userInfo);
		}else
		{
			purchase.setSalesman(SysContext.getSysContext().getCurrentUserInfo());
		}	
		if (isPrePurchase()) {
			purchase.setPurchaseState(PurchaseStateEnum.PrePurchaseApply);
			// 预定金默认为是 0￥
			purchase.setPrePurchaseAmount(FDCHelper.ZERO);
			// --预定流程才写入预定日期,便于后面判断是否预定流程 zhicheng_jin 090226
			purchase.setPrePurchaseDate(currentTime);
			// ------------
			purchase.setPrePurLevelAmount(FDCHelper.ZERO);
			
			Map preMoneySetMap = setting.getPreMoneySetMap();
			if(sellProject!=null){
				PreMoneySetting preMoneySet = (PreMoneySetting)preMoneySetMap.get(sellProject.getId()==null?null:sellProject.getId().toString());
				if(preMoneySet!=null) {
					purchase.setPrePurLevelAmount(preMoneySet.getPreLevelAmount());
					purchase.setPrePurchaseAmount(preMoneySet.getPreStandAmount());
					if(!preMoneySet.getIsLevelModify().booleanValue()) {
						this.txtPreLevelMoney.setEnabled(false);
					}
					if(!preMoneySet.getIsStandModify().booleanValue()) {
						this.txtPrePurchaseAmount.setEnabled(false);
					}
				}
			}
			this.tblPreLayTime.setVisible(false);
			if(funSet != null){
				if(!funSet.getIsEditPurAndSignDate().booleanValue()){
					this.pkPrePurchaseDate.setEnabled(false);
				}else{
					this.pkPrePurchaseDate.setEnabled(true);
				}
			}
		} else {
			purchase.setPurchaseState(PurchaseStateEnum.PurchaseApply);
			// 直接认购的,预订金标准不设置,也可认为是0
			// purchase.setPrePurchaseAmount(new BigDecimal("1000"));
			purchase.setPurchaseDate(currentTime);
		}
		try {
			CompanyOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentFIUnit();
			purchase.setOrgUnit(SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo());
			CurrencyInfo baseCurrency = CurrencyFactory.getRemoteInstance().getCurrencyInfo(new ObjectUuidPK(BOSUuid.read(currentCompany.getBaseCurrency().getId().toString())));
			purchase.setPrePurchaseCurrency(baseCurrency);
			purchase.setPurchaseCurrency(baseCurrency);
			purchase.setDealCurrency(baseCurrency);
		} catch (Exception e) {
			this.handleException(e);
		}
		/*
		 * PurchaseCustomerInfoInfo cuInfo = new PurchaseCustomerInfoInfo();
		 * cuInfo.setPropertyPercent(new BigDecimal("100"));
		 * purchase.getCustomerInfo().add(cuInfo);
		 */
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) this.getUIContext().get("sincerity");
		if (sin != null) {// 诚意认购转认购
			purchase.setSincerityPurchase(sin);

			RoomInfo room = sin.getRoom();
			if(room==null){
				IntentionRoomsEntryCollection roomEntry = sin.getIntentionRooms();
				if(roomEntry != null  &&  roomEntry.size() == 1){
	        		IntentionRoomsEntryInfo roomEntryInfo = roomEntry.get(0);
	        		try {
	        			SelectorItemCollection selector = new SelectorItemCollection();
	        			selector.add("*");
	    				selector.add("intentionRoom.*");
	    				selector.add("intentionRoom.building.*");
	    				selector.add("intentionRoom.building.sellProject.*");
	    				selector.add("intentionRoom.building.subarea.*");
	    				
	    				
	        			IntentionRoomsEntryInfo intentionRoomsEntryInfo = IntentionRoomsEntryFactory.getRemoteInstance().getIntentionRoomsEntryInfo(new ObjectUuidPK(BOSUuid.read(String.valueOf(roomEntryInfo.getId()))),selector);
		        		RoomInfo roomInfo= intentionRoomsEntryInfo.getIntentionRoom();
//						roomInfo = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(String.valueOf(roomInfo.getId()))));
//						setPurchaseRoomInfo(roomInfo,sellProject);
		        		room = roomInfo;
	        		
					} catch (EASBizException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error(e);
					} catch (BOSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error(e);
					} catch (UuidException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error(e);
					}
		    	}
			}
			
			
			// 房间为待售状态才能被认购
			//TODO 为了保证以前没有诚意认购状态时做的诚意认购单，待售状态在这也能认购
			if (room != null && (RoomSellStateEnum.SincerPurchase.equals(room.getSellState()) || RoomSellStateEnum.OnShow.equals(room.getSellState()))) {
				purchase.setRoom(room);
			}
			purchase.setPrePurchaseAmount(sin.getSincerityAmount());
			purchase.setEarnestBase(sin.getSincerityAmount());
			PurchaseCustomerInfoInfo cusInfo = new PurchaseCustomerInfoInfo();
			//2011-06-15
			//by tim_gao 先注销掉了 ， 因为  purchaseCustomer 上的 客户  和  sincerityPurchase 的客户类型不一致
			//一个是  FDCcustoerm 一个是 SHECustomer
//			cusInfo.setCustomer(sin.getCustomer().get(0).getCustomer());
			cusInfo.setPropertyPercent(new BigDecimal("100"));
			purchase.getCustomerInfo().clear();
			purchase.getCustomerInfo().add(cusInfo);
			
			
		} else {// 以销售控制为入口的认购,或者认购管理新增的认购
			purchase.setRoom((RoomInfo) this.getUIContext().get("room"));
		}

		if (purchase.getRoom() != null) {
			RoomInfo room = purchase.getRoom();
			verifyAddNewRoom(room);
		}
		PurchaseInfo copyPurchase = (PurchaseInfo) this.getUIContext().get("purchase");
		if (copyPurchase != null) {
			purchase = (PurchaseInfo) copyPurchase.clone();
		}
		purchase.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		purchase.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		purchase.setBookedDate(new Date());

		setIsEarnestInHouseAmount(purchase, sellProject);
		
		// if(sellProject == null || sellProject.getId() == null){
		// MsgBox.showInfo(this, "系统错误，传入的销售项目为空！");
		// this.abort();
		// }
		purchase.setSellProject(sellProject);
		purchase.setCreator(userInfo);
		try {
			purchase.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance().getServerTime());
		} catch (BOSException e) {
			handleException(e);
		}
		
		return purchase;
	}

	public void setIsEarnestInHouseAmount(PurchaseInfo purchase, SellProjectInfo sellProject) {
		if(sellProject != null){
			//从setting中获得参数：定金是否计入楼款
			Boolean isHouseMoney = new Boolean(true);
			Map functionSetMap = setting.getFunctionSetMap();
			FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(sellProject.getId().toString());
			if(funcSet == null){
				MsgBox.showInfo(this, "售楼设置中未设置该项目的定金是否隶属房款，此处默认隶属于房款！");
			}
			if(funcSet!=null) {
				isHouseMoney = funcSet.getIsHouseMoney();
				if(isHouseMoney == null){
					purchase.setIsEarnestInHouseAmount(true);
				}	
			}
			purchase.setIsEarnestInHouseAmount(isHouseMoney.booleanValue());
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PurchaseFactory.getRemoteInstance();
	}

	protected void btnSelectRoom_actionPerformed(ActionEvent e) throws Exception {
		BuildingInfo buildingInfo = (BuildingInfo) this.getUIContext().get("building");
		BuildingUnitInfo buildUnit = (BuildingUnitInfo) this.getUIContext().get("buildUnit");

		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		if (sellProject != null && sellProject.getId() != null) {
			sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(sellProject.getId().toString())));
		}
		RoomInfo room = RoomSelectUI.showOneRoomSelectUI(this, buildingInfo, buildUnit, MoneySysTypeEnum.SalehouseSys, null, sellProject);
		if (room == null) {
			return;
		}
		
		
		verifyAddNewRoom(room);
		if (room != null) {
			setIsEarnestInHouseAmount(this.editData, sellProject);
			
			this.txtRoomNumber.setUserObject(room);
			this.txtRoomNumber.setText(room.getDisplayName());
			if(room.getSellOrder()!=null){
				this.txtSellOrder.setText(room.getSellOrder().getName());
			}
			this.txtProject.setText(room.getBuilding().getSellProject().getName());
			this.txtProject.setUserObject(sellProject);
			if (room.getBuilding().getSubarea() != null) {
				this.txtSubarea.setText(room.getBuilding().getSubarea().getName());
			}
			this.txtBuilding.setText(room.getBuilding().getName());
			this.spiUnit.setValue(new Integer(room.getBuildUnit() == null ? 0 : room.getBuildUnit().getSeq()));
			this.spiFloor.setValue(new Integer(room.getFloor()));
			this.f7BuildingFloor.setValue(room.getBuildingFloor());

			this.txtBuildingArea.setValue(room.getBuildingArea());
			this.txtRoomArea.setValue(room.getRoomArea());
			this.f7RoomModel.setValue(room.getRoomModel());
			this.txtFitmentStandard.setText(room.getFitmentStandard());
			this.txtAgio.setUserObject(new AgioEntryCollection());
//			当房间 为实测符合的时候销售方式默认为现售
			this.comboSellType.setEnabled(true);
			if(room.isIsActualAreaAudited()){
				this.comboSellType.setSelectedItem(SellTypeEnum.LocaleSell);
			}else{
				//当选择房间以后，没有经过实测复核的房间的的销售方式为预收且不可编辑 update by renliang 2011-2-18
				this.comboSellType.setSelectedItem(SellTypeEnum.PreSell);
				this.comboSellType.setEnabled(false);
			}
			
			// setSincerityFilter(room);
			// this.f7SincerityPurchase.setValue(null);
			this.updateAttachmentAmount(null);
			
			this.updateAreaCompensateAmount();

			addF7PayTypeFilter();
			this.f7PayType.setValue(null);
			showStandardPrice();
		}
	}


	private void verifyAddNewRoom(RoomInfo room) {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		// view.getSelector().add("room.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("attachmentEntry.room.id", room.getId().toString()));
		PurchaseRoomAttachmentEntryCollection attaches = null;
		try {
			attaches = PurchaseRoomAttachmentEntryFactory.getRemoteInstance().getPurchaseRoomAttachmentEntryCollection(view);
		} catch (BOSException e) {
			this.handleException(e);
		}
		if (attaches.size() > 0) {
			MsgBox.showInfo("该房间已经被其他房间绑定并且并入合同,不能单独销售!");
			this.abort();
		}
		// --附属房产不能单独销售 zhicheng_jin 090324
		if (HousePropertyEnum.Attachment.equals(room.getHouseProperty())) {
			MsgBox.showInfo("附属房产不能单独销售!");
			this.abort();
		}
		// --
		if (room.getSellState().equals(RoomSellStateEnum.KeepDown)) {
			MsgBox.showInfo("房间已经被保留!");
			this.abort();
		}
		if (room.getSellState().equals(RoomSellStateEnum.Init)) {
			MsgBox.showInfo("房间没有开盘!");
			this.abort();
		}
		if (room.getSellState().equals(RoomSellStateEnum.PrePurchase) || room.getSellState().equals(RoomSellStateEnum.Purchase) || room.getSellState().equals(RoomSellStateEnum.Sign)) {
			MsgBox.showInfo("房间已经被认购!");
			this.abort();
		}
		if (room.getStandardTotalAmount() == null || room.getStandardTotalAmount().compareTo(FDCHelper.ZERO) == 0) {
			MsgBox.showInfo("房间尚未定价，请选择其它房间操作!");
			this.abort();
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectorItemCollection = super.getSelectors();
		selectorItemCollection.add("*");
		selectorItemCollection.add("payType.id");
		selectorItemCollection.add("payType.name");
		selectorItemCollection.add("payType.number");
		selectorItemCollection.add("sellProject.*");
		selectorItemCollection.add("customer.*");
		selectorItemCollection.add("dealCurrency.id");
		selectorItemCollection.add("dealCurrency.name");
		selectorItemCollection.add("dealCurrency.number");
		selectorItemCollection.add("customerInfo.*");
		selectorItemCollection.add("customerInfo.customer.*");
		//new add by renliang at 2011-3-3
		selectorItemCollection.add("customerInfo.customer.certificateName.id");
		selectorItemCollection.add("customerInfo.customer.certificateName.number");
		selectorItemCollection.add("customerInfo.customer.certificateName.name");
		selectorItemCollection.add("customerInfo.customer.certificateName.customerType");
		selectorItemCollection.add("customerInfo.customer.certificateName.description");
		selectorItemCollection.add("customerInfo.customer.customerType");
		
		selectorItemCollection.add("room.*");
		// selectorItemCollection.add("room.buildingFloor.*");
		selectorItemCollection.add("room.buildingFloor.id");
		selectorItemCollection.add("room.buildingFloor.floor");
		selectorItemCollection.add("room.buildingFloor.floorAlias");

		selectorItemCollection.add("room.lastSignContract.signDate");
		//by tim_gao 增加合同状态，做总揽页签上的判断2010-11-05
		selectorItemCollection.add("room.lastSignContract.state");
		selectorItemCollection.add("salesman.id");
		selectorItemCollection.add("salesman.name");
		selectorItemCollection.add("salesman.number");
		selectorItemCollection.add("room.roomModel.id");
		selectorItemCollection.add("room.roomModel.name");
		selectorItemCollection.add("room.roomModel.number");
		selectorItemCollection.add("room.building.name");
		// selectorItemCollection.add("room.buildUnit.*");
		selectorItemCollection.add("room.buildUnit.id");
		selectorItemCollection.add("room.buildUnit.name");
		selectorItemCollection.add("room.buildUnit.seq");

		selectorItemCollection.add("room.sellOrder.*");
		selectorItemCollection.add("room.building.subarea.name");
		selectorItemCollection.add("room.building.sellProject.name");
		selectorItemCollection.add("agioEntrys.*");
		
		selectorItemCollection.add("secondSaleMan.*");
		selectorItemCollection.add("secondSaleMan.secondMan.*");
		
		selectorItemCollection.add("elsePayListEntry.*");		

		selectorItemCollection.add("elsePayListEntry.moneyDefine.id");
		selectorItemCollection.add("elsePayListEntry.moneyDefine.name");
		selectorItemCollection.add("elsePayListEntry.moneyDefine.number");
		selectorItemCollection.add("elsePayListEntry.moneyDefine.moneyType");
		selectorItemCollection.add("elsePayListEntry.moneyDefine.sysType");
		selectorItemCollection.add("elsePayListEntry.moneyDefine.isEnabled");

		selectorItemCollection.add("elsePayListEntry.currency.id");
		selectorItemCollection.add("elsePayListEntry.currency.name");
		selectorItemCollection.add("elsePayListEntry.currency.number");
		
		selectorItemCollection.add("purCustomer.*");
		//new update by renliang at 2011-3-3
		selectorItemCollection.add("purCustomer.certificateName.id");
		selectorItemCollection.add("purCustomer.certificateName.number");
		selectorItemCollection.add("purCustomer.certificateName.name");
		selectorItemCollection.add("purCustomer.certificateName.customerType");
		selectorItemCollection.add("purCustomer.certificateName.description");
		
		return selectorItemCollection;
	}

	protected void f7PayType_dataChanged(DataChangeEvent e) throws Exception {
		if (e.getNewValue() == null) {
			this.btnAddPayEntry.setEnabled(false);
			this.btnRemovePayEntry.setEnabled(false);
			this.txtLoanAmount.setValue(null);
			this.txtAFundAmount.setValue(null);
			this.tblPayList.removeRows();

			this.updatePayList();
			return;
		}

		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		if (room == null) {
			MsgBox.showInfo("请先选择房间!");
			this.f7PayType.setValue(null);
			return;
		}
		super.f7PayType_dataChanged(e);
		SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();
		if (payType == null) {
			this.btnAddPayEntry.setEnabled(false);
			this.btnRemovePayEntry.setEnabled(false);
			this.txtLoanAmount.setValue(null);
			this.txtAFundAmount.setValue(null);
			this.tblPayList.removeRows();
		} else {
			EntityViewInfo view = new EntityViewInfo();
			view.getSorter().add(new SorterItemInfo("seq"));
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("seq");
			view.getSelector().add("moneyDefine.*");
			view.getSelector().add("currency.*");
			filter.getFilterItems().add(new FilterItemInfo("head.id", payType.getId().toString()));
			PayListEntryCollection payList = new PayListEntryCollection();
			payList = PayListEntryFactory.getRemoteInstance().getPayListEntryCollection(view);
			payType.getPayLists().clear();
			payType.getPayLists().addCollection(payList);
			
			//计算约定签约日期
			EntityViewInfo viewBiz = new EntityViewInfo();
			viewBiz.getSorter().add(new SorterItemInfo("seq"));
			FilterInfo filterbiz = new FilterInfo();
			viewBiz.setFilter(filterbiz);
			viewBiz.getSelector().add("*");
			filterbiz.getFilterItems().add(new FilterItemInfo("head.id", payType.getId().toString()));
			BizListEntryCollection bizList = new BizListEntryCollection();
			bizList = BizListEntryFactory.getRemoteInstance().getBizListEntryCollection(viewBiz);
			Date date = (Date)this.pkPurchaseDate.getValue();
			if(date==null){
				date = new Date();
			}
			if(bizList!=null && bizList.size()>0){
				BizListEntryInfo bizListInfo = new BizListEntryInfo();
				for(int i = 0; i < bizList.size(); i++){
					bizListInfo  = bizList.get(i);
					if(BizFlowEnum.Sign.equals(bizListInfo.getBizFlow()) ){
						if(BizTimeEnum.Purchase.equals(bizListInfo.getBizTime())){
							int monthLimit = bizListInfo.getMonthLimit();
							int dayLimit = bizListInfo.getDayLimit();
//								int days = monthLimit*30+dayLimit;
							date = addMonthsDays(date,monthLimit,dayLimit);
							break;
						}else if(BizTimeEnum.AppTime.equals(bizListInfo.getBizTime())){
							date = bizListInfo.getAppDate();
							break;
						}
						
					}
				}
				// 如果约定签约日期被隐藏，说明是直接签约，此处与认购申请日期一样
				if (contSignDate.isVisible()) {
					this.pkSignDate.setValue(date);
				}else{
					this.pkSignDate.setValue(pkPurchaseDate.getValue());
				}
			}
		}
		this.updatePayList();
	}
	public void actionCreat_actionPerformed(ActionEvent e) throws Exception {
		if(this.txtRoomNumber.getUserObject() == null){
			MsgBox.showInfo("请先选择房间！");
			this.abort();
		}
		if(this.tblCustomerInfo.getRowCount()==0){
			MsgBox.showInfo("请先选择客户！");
			this.abort();
		}
		if(this.f7PayType.getValue()==null){
			MsgBox.showInfo("请先选择付款方案！");
			this.abort();
		}
		this.tblOtherPay.removeRows();
		updateElsePayList();
	}
	
	public void updateElsePayList() throws BOSException{
		boolean isEarnestInHouseAmount = this.editData.isIsEarnestInHouseAmount();
		List toDelRowIds = new ArrayList();
		for (int i = 0; i < this.tblOtherPay.getRowCount(); i++) {
			IRow row = this.tblOtherPay.getRow(i);
			PurchaseElsePayListEntryInfo entry = (PurchaseElsePayListEntryInfo) row.getUserObject();
			BigDecimal actAmount = entry.getActPayAmount();
			// BigDecimal apAmount = entry.getApAmount();
			if (actAmount == null) {
				toDelRowIds.add(new Integer(i));
			}
		}

		for (int i = toDelRowIds.size() - 1; i >= 0; i--) {
			Integer tmp = (Integer) toDelRowIds.get(i);
			this.tblOtherPay.removeRow(tmp.intValue());
		}
		CertificateInfo certificateName = null;
		for(int i=0;i<this.tblCustomerInfo.getRowCount();i++){
			if(this.tblCustomerInfo.getRow(i).getCell("seq").getValue()==Boolean.TRUE){
				if(this.tblCustomerInfo.getRow(i).getCell("certificateName").getValue()!=null){
				certificateName = (CertificateInfo) this.tblCustomerInfo.getRow(i).getCell("certificateName").getValue();
				}
			}
		}
		SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();
		if (payType != null) {
			RoomInfo room =(RoomInfo) this.txtRoomNumber.getUserObject();
			PayListEntryCollection payList = payType.getPayLists();
			BigDecimal eareatMoney = FDCHelper.ZERO;
			BigDecimal remain = this.txtContractTotalAmount.getBigDecimalValue();
			if (remain == null) 	remain = FDCHelper.ZERO;
			
			CRMHelper.sortCollection(payList, "seq", true);
			List toAddRowPurEntry = new ArrayList();
			boolean[] hasRelated = new boolean[tblOtherPay.getRowCount()];// 标识现有各行是否已被关联
			for (int i = 0; i < payList.size(); i++) {
				PayListEntryInfo entry = payList.get(i);
				if(MoneyTypeEnum.ReplaceFee.equals(entry.getMoneyDefine().getMoneyType())){
				PurchaseElsePayListEntryInfo purEntry = new PurchaseElsePayListEntryInfo();
				Date curDate = new Date();
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
				BigDecimal amount = FDCHelper.ZERO;	//当前明细的应收金额
			    EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("moneyName.id",entry.getMoneyDefine().getId()));
				filter.getFilterItems().add(new FilterItemInfo("project.id",room.getBuilding().getSellProject().getId()));
				filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE ));
				
				view.getSelector().add("id");
				view.getSelector().add("number");
				view.getSelector().add("name");
				view.getSelector().add("calculateType");
				view.getSelector().add("hold");
				view.getSelector().add("integerType");
				view.getSelector().add("factor");
				view.getSelector().add("operate");
				view.getSelector().add("comparaValue");
				view.getSelector().add("fixedAmount");
				view.getSelector().add("formulaDescription");
				view.getSelector().add("compare");
				view.getSelector().add("stdFormulaScript");
				view.getSelector().add("moneyName.*");
				CollectionInfo collection =new CollectionInfo();
				String collectionid = "";
				CollectionCollection payList1 = CollectionFactory.getRemoteInstance().getCollectionCollection(view);
			 if( payList1!=null && payList1.size()>0){
				 //CollectionCollection c = coll;
				 collection = (CollectionInfo)payList1.get(0);
				 collectionid = collection.getId().toString();
					if (CalculateTypeEnum.GENERAL.equals(collection.getCalculateType()) && collection.getComparaValue()!=null) {
						if(FactorEnum.DEALTOTALALMOUNT.equals(collection.getFactor())){
							BigDecimal totalAmount =(BigDecimal) this.txtDealTotalAmount.getValue();
							if(OperateEnum.MULTIPLY.equals(collection.getOperate())){
								amount = totalAmount.multiply(collection.getComparaValue());
							}if(OperateEnum.ADD.equals(collection.getOperate())){
								amount = totalAmount.add(collection.getComparaValue());
							}if(OperateEnum.MINUS.equals(collection.getOperate())){
								amount = totalAmount.subtract(collection.getComparaValue());
							}if(OperateEnum.DIVIDE.equals(collection.getOperate()) && collection.getComparaValue().compareTo(new BigDecimal(0))!=0){
								//amount = totalAmount.divide(collection.getComparaValue());
								amount = FDCHelper.divide(totalAmount, collection.getComparaValue());
							}
							
						}else if(FactorEnum.STANDARTOATALMOUNT.equals(collection.getFactor())){
							BigDecimal totalAmount =(BigDecimal) this.txtStandardTotalAmount.getValue();
							if(OperateEnum.MULTIPLY.equals(collection.getOperate())){
								amount = totalAmount.multiply(collection.getComparaValue());
							}if(OperateEnum.ADD.equals(collection.getOperate())){
								amount = totalAmount.add(collection.getComparaValue());
							}if(OperateEnum.MINUS.equals(collection.getOperate())){
								amount = totalAmount.subtract(collection.getComparaValue());
							}if(OperateEnum.DIVIDE.equals(collection.getOperate()) && collection.getComparaValue().compareTo(new BigDecimal(0))!=0 ){
								//amount = totalAmount.divide(collection.getComparaValue());
								amount = FDCHelper.divide(totalAmount, collection.getComparaValue());
							}
						}else if(FactorEnum.BUILDINGAREA.equals(collection.getFactor())){
							BigDecimal totalAmount =(BigDecimal) this.txtBuildingArea.getValue();
							if(OperateEnum.MULTIPLY.equals(collection.getOperate())){
								amount = totalAmount.multiply(collection.getComparaValue());
							}if(OperateEnum.ADD.equals(collection.getOperate())){
								amount = totalAmount.add(collection.getComparaValue());
							}if(OperateEnum.MINUS.equals(collection.getOperate())){
								amount = totalAmount.subtract(collection.getComparaValue());
							}if(OperateEnum.DIVIDE.equals(collection.getOperate()) && collection.getComparaValue().compareTo(new BigDecimal(0))!=0){
								amount = FDCHelper.divide(totalAmount, collection.getComparaValue());
								//amount = totalAmount.divide(collection.getComparaValue());
							}
						}else if(FactorEnum.ROOMAREA.equals(collection.getFactor())){
							BigDecimal totalAmount =(BigDecimal) this.txtRoomArea.getValue();
							if(OperateEnum.MULTIPLY.equals(collection.getOperate())){
								amount = totalAmount.multiply(collection.getComparaValue());
							}if(OperateEnum.ADD.equals(collection.getOperate())){
								amount = totalAmount.add(collection.getComparaValue());
							}if(OperateEnum.MINUS.equals(collection.getOperate())){
								amount = totalAmount.subtract(collection.getComparaValue());
							}if(OperateEnum.DIVIDE.equals(collection.getOperate()) && collection.getComparaValue().compareTo(new BigDecimal(0))!=0){
								//amount = totalAmount.divide(collection.getComparaValue());
								amount = FDCHelper.divide(totalAmount, collection.getComparaValue());
							}
						}
//						}else if(FactorEnum.BUILDINGPROPERTY.equals(collection.getFactor())){
////							MsgBox.showInfo("建筑性质不能用于计算！");
////							this.abort();
//						}else if(FactorEnum.PROPERTYFEATURES.equals(collection.getFactor())){
////							MsgBox.showInfo("置业特征不能用于计算！");
////							this.abort();
//						}else if(FactorEnum.CERTIFICATENAME.equals(collection.getFactor())){
////							MsgBox.showInfo("证件名称不能用于计算！");
////							this.abort();
//						}
					} else if(CalculateTypeEnum.PARMAMNENT.equals(collection.getCalculateType())){
						amount = collection.getFixedAmount();
					}else if(CalculateTypeEnum.PATULOUS.equals(collection.getCalculateType())){
						Map map  = new HashMap();
						Map valuemap = new HashMap();
						RoomInfo RoomInfo = (RoomInfo) this.txtRoomNumber.getUserObject();
						valuemap.put("DealTotalAmount", (BigDecimal)this.txtDealTotalAmount.getValue());
						valuemap.put("StandardTotalAmount", (BigDecimal)this.txtStandardTotalAmount.getValue());
						valuemap.put("BuildingArea", (BigDecimal)this.txtBuildingArea.getValue());
						valuemap.put("RoomArea", (BigDecimal)this.txtRoomArea.getValue());
						valuemap.put("buildingProperty", (String)RoomInfo.getBuildingProperty().getName());
						valuemap.put("actualBuildingArea", (BigDecimal)this.txtActualBuildingArea.getValue());
						valuemap.put("actualRoomArea", (BigDecimal)this.txtActualRoomArea.getValue());
						if(RoomInfo.getLastAreaCompensate() != null){
						    valuemap.put("compensateAmount", (BigDecimal)RoomInfo.getLastAreaCompensate().getCompensateAmount());
						}else{
							valuemap.put("compensateAmount", new BigDecimal("0.00"));
						}
						if(null==certificateName){
							valuemap.put("certificateName", "null");
						}else{
						    valuemap.put("certificateName", (String)certificateName.getName());
						}
//						现在认购单上海没有置业特征这个字段，所以这里传入一个空值
						valuemap.put("PropertyFeatures", "null");
						String stdFormulaScript = collection.getStdFormulaScript();
						map.put("stdFormulaScript", stdFormulaScript);
						map.put("value", valuemap);
						Map result =CollectionFactory.getRemoteInstance().getArAmout(map);
//						Object  o =  result.get("result");
//						if( o instanceof Integer ){
//							amount = BigDecimal.valueOf();
//						}
						amount = new BigDecimal(result.get("result")!=null?result.get("result").toString():"0");
					}
					if (amount != null && collection.getHold() != null )
				      {
				        if (collection.getHold().equals(HoldEnum.YUAN))
				        	if(collection.getIntegerType().equals(IntegerTypeEnum.ROUND)){
				        	    amount = amount.setScale(0, 4);
				        	}else{
				        		amount = amount.setScale(0, 2);
				        	}
				        else if (collection.getHold().equals(HoldEnum.JIAO))
				        	if(collection.getIntegerType().equals(IntegerTypeEnum.ROUND)){
				        	    amount = amount.setScale(1, 4);
				        	}else{
				        		amount = amount.setScale(1, 2);
				        	}
				        else {
				        	if(collection.getIntegerType().equals(IntegerTypeEnum.ROUND)){
				        	    amount = amount.setScale(2, 4);
				        	}else{
				        		amount = amount.setScale(2, 2);
				        	}
				        }
				      }
			  }else {
				  amount = FDCHelper.ZERO;
			  }
				
				
				
				MoneyDefineInfo moneyDefine = entry.getMoneyDefine();
				purEntry.setAppAmount(amount);
				purEntry.setMoneyDefine(moneyDefine);
				purEntry.setCurrency(entry.getCurrency());	
				purEntry.setSheCollection(collectionid);
		

				boolean isExist = false;
				for (int j = 0; j < tblOtherPay.getRowCount(); j++) {
					if (hasRelated[j]) {
						continue;
					}

					IRow row = tblOtherPay.getRow(j);
					PurchaseElsePayListEntryInfo oldEntry = (PurchaseElsePayListEntryInfo) row.getUserObject();
					// 新付款计划中与已收款的付款分录有对应项
					if(oldEntry==null || oldEntry.getMoneyDefine()==null || oldEntry.getMoneyDefine().getMoneyType()==null)
						continue;
					if (oldEntry.getMoneyDefine().getMoneyType().equals(moneyDefine.getMoneyType())) {
						hasRelated[j] = true;
						isExist = true;
						BigDecimal oldActPayAmount = oldEntry.getActRevAmount();
						if (oldActPayAmount == null)	oldActPayAmount = FDCHelper.ZERO;
		
						if (oldEntry.getAppAmount().compareTo(oldActPayAmount) == 0) {
							break;
						}
						if (oldEntry.getActRevAmount() == null || amount.compareTo(oldActPayAmount) > 0) {
							// 因为分录如果已收款,分录ID可能被收款单关联,已有收款的明细分录的ID需保证不变
							// zhicheng_jin 090422
							// TODO 待验证：其实如果有收款,认购单肯定是不让修改的,这里可能根本不会用到
							purEntry.setId(oldEntry.getId());
							row.setUserObject(purEntry);
//							row.getCell("moneyName").setValue(purEntry.getMoneyDefine());
							row.getCell("date").setValue(purEntry.getAppDate());
							row.getCell("amount").setValue(purEntry.getApAmount());
							row.getCell("currency").setValue(purEntry.getCurrency());
							row.getCell("des").setValue(purEntry.getDescription());
						}
						break;
					}
				}
				if (!isExist) {
					toAddRowPurEntry.add(purEntry);
				}
			  }
			}
			for (int i = 0; i < toAddRowPurEntry.size(); i++) {
				addElsePayListEntryRow((PurchaseElsePayListEntryInfo) toAddRowPurEntry.get(i));
			}
		}
		
	}
	
	public void addElsePayListEntryRow(PurchaseElsePayListEntryInfo entry){
		IRow row = this.tblOtherPay.addRow();
		row.setUserObject(entry);
		row.getCell("moneyName").setValue(entry.getMoneyDefine());
		row.getCell("date").setValue(entry.getAppDate());
		row.getCell("amount").setValue(entry.getApAmount());
		row.getCell("currency").setValue(entry.getCurrency());
		row.getCell("des").setValue(entry.getDescription());
		row.getCell("refundmentAmount").setValue(entry.getHasRefundmentAmount());
		row.getCell("actDate").setValue(entry.getActRevDate());
		row.getCell("actAmount").setValue(entry.getActPayAmount());
		row.getCell("collectionID").setValue(entry.getSheCollection());

		// end
		BigDecimal apAmount = entry.getApAmount();
		BigDecimal actAmount = entry.getActPayAmount();
		if (apAmount == null) {
			apAmount = FDCHelper.ZERO;
		}
		if (actAmount == null) {
			actAmount = FDCHelper.ZERO;
		}
		if (actAmount.compareTo(FDCHelper.ZERO) != 0) {
			row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			row.getCell("moneyName").getStyleAttributes().setLocked(true);
			row.getCell("date").getStyleAttributes().setLocked(true);
			row.getCell("currency").getStyleAttributes().setLocked(true);
			if (actAmount.compareTo(apAmount) == 0) {
				row.getCell("amount").getStyleAttributes().setLocked(true);

			}
		}
		
		if(!this.editData.isIsEarnestInHouseAmount()  &&  entry.getMoneyDefine() != null  &&  MoneyTypeEnum.EarnestMoney.equals(entry.getMoneyDefine().getMoneyType())){
			row.getStyleAttributes().setBackground(Color.green);
		}
	}
	
	 /**
     * 增加月数和天数
     * @param date
     * @param day
     * @return
     */
    public static Date addMonthsDays(Date date,int month,int day)
    {
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH,month);
    	calendar.add(Calendar.DATE,day);
    	return calendar.getTime();
    }
	

	protected void f7SincerityPurchase_dataChanged(DataChangeEvent e) throws Exception {
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) this.f7SincerityPurchase.getValue();
		if (sin != null) {
			if (sin.getRoom() != null && this.getUIContext().get("purchase") == null) {
				BOSUuid roomUuid = sin.getRoom().getId();
				RoomInfo room = SHEHelper.queryRoomInfo(roomUuid.toString());
				// RoomInfo room =
				// RoomFactory.getRemoteInstance().getRoomInfo(new
				// ObjectUuidPK(roomUuid));
				if (room != null && RoomSellStateEnum.OnShow.equals(room.getSellState())) {
					this.txtRoomNumber.setUserObject(room);
					this.txtRoomNumber.setText(room.getDisplayName());
					this.txtSellOrder.setText(room.getSellOrder().getName());
					if(room.getBuilding()!=null && room.getBuilding().getSellProject()!=null){
						this.txtProject.setText(room.getBuilding().getSellProject().getName());
					}
					this.txtProject.setUserObject(room.getBuilding().getSellProject());
					if (room.getBuilding().getSubarea() != null) {
						this.txtSubarea.setText(room.getBuilding().getSubarea().getName());
					}
					if(room.getBuilding()!=null){
						this.txtBuilding.setText(room.getBuilding().getName());
					}
					this.spiUnit.setValue(new Integer(room.getBuildUnit() == null ? 0 : room.getBuildUnit().getSeq()));
					this.spiFloor.setValue(new Integer(room.getFloor()));
					this.f7BuildingFloor.setValue(room.getBuildingFloor());

					this.txtBuildingArea.setValue(room.getBuildingArea() == null ? FDCHelper.ZERO : room.getBuildingArea());
					this.txtRoomArea.setValue(room.getRoomArea() == null ? FDCHelper.ZERO : room.getRoomArea());
					this.f7RoomModel.setValue(room.getRoomModel());
					this.txtFitmentStandard.setText(room.getFitmentStandard());
					this.txtAgio.setUserObject(new AgioEntryCollection());
					this.comboSellType.setSelectedItem(SellTypeEnum.PreSell);

					addF7PayTypeFilter();
					this.f7PayType.setValue(null);
				}
			}

			this.tblCustomerInfo.removeRows();
			IRow row = this.tblCustomerInfo.addRow();
			row.setHeight(20);
			row.setUserObject(new PurchaseCustomerInfoInfo());
			row.getCell("propertyPercent").setValue(new BigDecimal("100"));
			//2011-06-15
			//by tim_gao 先注销掉了 ， 因为  purchaseCustomer 上的 客户  和  sincerityPurchase 的客户类型不一致
			//一个是  FDCcustoerm 一个是 SHECustomer
//			if (sin.getCustomer() != null) {
//				FDCCustomerInfo customer = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(sin.getCustomer().getId()));
//				setCustomerRowData(customer, row);
//			}
			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add("salesman.*");
			SincerityPurchaseInfo Info = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(sin.getId()),coll);
			
			this.f7Seller.setValue(Info.getSalesman());
			// this.txtPrePurchaseAmount.setValue(sin.getSincerityAmount());
			this.removeDataChangeListener(tblCustomerInfo);
			UserInfo salesman = sin.getSalesman();
			if(salesman!= null && salesman.getId()!=null){
				this.f7Seller.setValue(UserFactory.getRemoteInstance().getUserInfo(new ObjectUuidPK(BOSUuid.read(String.valueOf(salesman.getId())))));
			}
			this.addDataChangeListener(tblCustomerInfo);
		}
	}
    
  protected void addDataChangeListener(JComponent com) {
    	
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	} else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDTable){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDTable)com).removeKDTEditListener((KDTEditListener)listeners[i]);
	    		}
	    	}
    	}
		
    }
        
    protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
  	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	} 
    	else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	}else if(com instanceof KDTable){
    		listeners = com.getListeners(KDTEditListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDTable)com).removeKDTEditListener((KDTEditListener)listeners[i]);
    		}
    	}
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }
    
	public void setCustomerInfoEnable(boolean isEnable) {
		this.tblCustomerInfo.getColumn("seq").getStyleAttributes().setLocked(isEnable);
		this.tblCustomerInfo.getColumn("propertyPercent").getStyleAttributes().setLocked(!isEnable);
		this.tblCustomerInfo.getColumn("customer").getStyleAttributes().setLocked(!isEnable);
		this.tblCustomerInfo.getColumn("des").getStyleAttributes().setLocked(!isEnable);
		this.tblCustomerInfo.getColumn("phone").getStyleAttributes().setLocked(!isEnable);
		this.tblCustomerInfo.getColumn("mailAddress").getStyleAttributes().setLocked(!isEnable);
		this.tblCustomerInfo.getColumn("postalcode").getStyleAttributes().setLocked(!isEnable);
		this.tblCustomerInfo.getColumn("certificateName").getStyleAttributes().setLocked(!isEnable);
		this.tblCustomerInfo.getColumn("certificateNumber").getStyleAttributes().setLocked(!isEnable);
		//增加性别
		this.tblCustomerInfo.getColumn("sex").getStyleAttributes().setLocked(!isEnable);
		
		this.btnUp.setEnabled(isEnable);
		this.btnDown.setEnabled(isEnable);
		this.btnAddNewCustomer.setEnabled(isEnable);
		this.btnAddCustomer.setEnabled(isEnable);
		this.btnDeleteCustomer.setEnabled(isEnable);
		this.modifyInfo.setEnabled(isEnable);
		this.modifyName.setEnabled(isEnable);
	}

	protected void btnAddCustomer_actionPerformed(ActionEvent e) throws Exception {
		/*super.btnAddCustomer_actionPerformed(e);
		UserInfo info = (UserInfo) this.f7Seller.getValue();

		FDCCustomerInfo customer = SHEHelper.selectCustomer(this.getUIWindow(),info, saleOrg);
		if (customer != null)
			addCustomerRow(customer);*/
//		super.btnAddCustomer_actionPerformed(e);
		//为什么取f7Seller的值呢，是不是因为初始化时将当前用户的信息给了f7Seller的原因。
		UserInfo info = SysContext.getSysContext().getCurrentUserInfo();// (UserInfo) this.f7Seller.getValue();

		FDCCustomerInfo customer = SHEHelper.selectCustomer(this.getUIWindow(),info, saleOrg);
		if (customer != null){
			addCustomerRow(customer);
			setSellerDefaultValue(customer);
			CustomerTypeEnum type = customer.getCustomerType();
			if(type.equals(CustomerTypeEnum.PersonalCustomer)){
				initNewCertificateName(true,-2);
			}else{
				initNewCertificateName(false,-2);
			}
		}
		
		//标注必填项
		List list = new ArrayList();
		list.add("certificateNumber");
		setCustomerRequiredColor(this.tblCustomerInfo,list);
		
		setSeller();
		setInsiderEntityView();
		prmtRecommendInsider.setValue(null);
		
		
	}
	/**
	 * 描述：设置分录必填项
	 * @author pu_zhang  @date 2010-07-30
	 * 
	 */
	protected void setCustomerRequiredColor(KDTable table,List list){
		int j = list.size();
		for(int i=0; i<table.getRowCount();i++){
			IRow row = tblCustomerInfo.getRow(i);
			for(int k = 0; k<j;k++){
				row.getCell(String.valueOf(list.get(k))).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
			}
			
		}
	}
	
	/**
	 * 添加销售顾问
	 * @param customer
	 * @throws Exception
	 */
	protected void setSellerDefaultValue(FDCCustomerInfo customer) throws Exception{
		FDCCustomerInfo fullCustomer = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(customer.getId()), getSellerSelectors());
		UserInfo salesMan = fullCustomer.getSalesman();
		if(isAddMainCustomer()) {
			if(!isCorrectSaleMan(salesMan)){
				this.f7Seller.setValue(null);
			}else{
				this.f7Seller.setValue(salesMan);
			}
		}else{
			List list = new ArrayList();
			if(this.f7secondSeller.getValue() != null){
				Object[] o = (Object[])this.f7secondSeller.getValue();
				for (int i = 0; i < o.length; i++) {
					list.add(o[i]);
				}
			}
			if(isCorrectSaleMan(salesMan) && !list.contains(salesMan)){
				
				list.add(salesMan);
			}
			
			f7secondSeller.setValue(list.toArray());
		
		}
	}	
	/**
	 * 得到要选择出的字段
	 * @return
	 * @author liang_ren969
	 */
	public SelectorItemCollection getSellerSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("*");
		selectors.add("salesman.*");
		return selectors;
	}
	
	/**
	 * 判断销售顾问的正确性
	 * @param salesMan
	 * @return true or false
	 * @throws EASBizException 
	 * @author liang_ren969
	 */
	private boolean isCorrectSaleMan(UserInfo salesMan){
		if(fullCustomer!=null){
			if(fullCustomer.contains(salesMan)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}			
	/**
	 * 根据salesId来获得此销售顾问对象
	 * @param salesMan
	 * @return
	 */
	private UserInfo getSaleManbyId(String salesId){
		
		UserInfo salesMan = null;
		
		if(this.fullCustomer==null){
			getSaleMans();
		}
		
		int number = this.fullCustomer.size();
		
		for (int i = 0; i < number; i++) {
			UserInfo info = (UserInfo)fullCustomer.get(i);
			if(info.getId().toString().equals(salesId)){
				salesMan = info;
				break;
			}
		}
		return salesMan; 
	}
		
	/**
	 * 获得当前营销人员能够看到的营销顾问
	 * by renliang at 2010-7-12
	 * @return
	 */
	private void getSaleMans(){
		try {
			EntityViewInfo view = CommerceHelper.getPermitSalemanView();
			this.fullCustomer = UserFactory.getRemoteInstance().getUserCollection(view);
		} catch (BOSException e) {
			logger.error(e.getMessage());
		} catch (EASBizException e){
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 判断是否是主客户
	 * @return
	 */
	private boolean isAddMainCustomer(){
		return this.tblCustomerInfo.getRowCount() ==1;
	}
	protected void addCustomerRow(FDCCustomerInfo customer) {
		if (customer == null) {
			return;
		}
		int activeRowIndex = this.tblCustomerInfo.getSelectManager().getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.tblCustomerInfo.addRow();
		} else {
			row = this.tblCustomerInfo.addRow(activeRowIndex + 1);
		}

		setCustomerRowData(customer, row);

		row.setHeight(20);
		PurchaseCustomerInfoInfo purCus = new PurchaseCustomerInfoInfo();
		purCus.setCustomer(customer);
		row.setUserObject(purCus);
		if (this.tblCustomerInfo.getRowCount() == 1) {
			row.getCell("propertyPercent").setValue(new BigDecimal("100"));
		}
	}
	public void setCustomerRowData(FDCCustomerInfo customer, IRow row) {
		//根据但前行的索引判断是不是主客户
		if(row.getRowIndex()==0){
			row.getCell("seq").setValue(Boolean.TRUE);
		}else{
			row.getCell("seq").setValue(Boolean.FALSE);
		}
		
		if (customer != null) {
			row.getCell("customer").setValue(customer);
			
			row.getCell("phone").setValue(customer.getPhone());
			row.getCell("postalcode").setValue(customer.getPostalcode());
			if(customer.getCertificateName()!=null && customer.getCertificateName().getId()!=null){
				CertificateInfo info = null;
				try {
					info = CertificateFactory.getRemoteInstance()
							.getCertificateInfo(
									"select id,name,number where id='"
											+ customer.getCertificateName().getId()
													.toString() + "'");
				} catch (EASBizException e) {
					logger.error(e.getMessage());
				} catch (BOSException e) {
					logger.error(e.getMessage());
				}
				if (info != null) {
					row.getCell("certificateName").setValue(info);
				}
			}else{
				row.getCell("certificateName").setValue(customer.getCertificateName());
				//row.getCell("certificateName").getStyleAttributes().setLocked(false);
			}
			
			row.getCell("certificateNumber").setValue(customer.getCertificateNumber());
			row.getCell("mailAddress").setValue(customer.getMailAddress());
			row.getCell("bookDate").setValue(customer.getCreateTime());
			//增加性别
			row.getCell("sex").setValue(customer.getSex());
			//new add 销售顾问Id
			row.getCell("salesId").setValue(customer.getSalesman().getId().toString());
			//by tim_gao 增加顾客id
			row.getCell("id").setValue(customer.getId().toString());
			row.getCell("customerType").setValue(customer.getCustomerType());
		} else {
			row.getCell("phone").setValue(null);
			row.getCell("postalcode").setValue(null);
			row.getCell("certificateName").setValue(null);
			row.getCell("certificateNumber").setValue(null);
			row.getCell("mailAddress").setValue(null);
			row.getCell("bookDate").setValue(null);
			row.getCell("sex").setValue(null);
			row.getCell("id").setValue(null);
		}
	}

	protected void btnDeleteCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.btnDeleteCustomer_actionPerformed(e);
		int activeRowIndex = this.tblCustomerInfo.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblCustomerInfo.getRowCount();
		}
		// -----如果认购单已被审批，相应的客户不允许删除--
		IRow delRow = this.tblCustomerInfo.getRow(activeRowIndex);
		if (delRow != null) {
			PurchaseCustomerInfoInfo customerInfo = (PurchaseCustomerInfoInfo) delRow.getUserObject();
			if (customerInfo != null && AUDITED.equals(customerInfo.getNumber())) {
				MsgBox.showInfo(this, "该客户已随认购单一起被审批，不可删除");
				return;
			}
		}
		// ---------------
		this.tblCustomerInfo.removeRow(activeRowIndex);
		if(tblCustomerInfo.getRowCount() >=1 && activeRowIndex == 0){
			this.tblCustomerInfo.getRow(0).getCell("seq").setValue(Boolean.TRUE);
		}
		
		/**
		 * 重新设置主销售顾问和其他销售顾问
		 * by renliang at 2010-7-13
		 */
		this.f7Seller.setValue(null);	
		this.f7secondSeller.setValue(null);
		
		List secondSalesMen = new ArrayList();
		int num  = tblCustomerInfo.getRowCount();
		
		for (int i = num-1; i >=0 ; i--) {
			IRow row = tblCustomerInfo.getRow(i);
			//判断是否是主销售顾问
			boolean result = ((Boolean)row.getCell("seq").getValue()).booleanValue();
			//根据id得到销售顾问的对象
			UserInfo info  = getSaleManbyId(row.getCell("salesId").getValue().toString());
			if(info!=null){
				if(result){
					this.f7Seller.setValue(info);	
				}else{
					if(this.f7secondSeller.getValue()!=null){
						//secondSalesMen = new HashSet(Arrays.asList((Object[])this.f7secondSeller.getValue()));
						Object[] o = (Object[])this.f7secondSeller.getValue();
						for (int j = 0; j< o.length; i++) {
							secondSalesMen.add(o[i]);
						}
					}
					
					if(!secondSalesMen.contains(info)){
						secondSalesMen.add(info);
					}
					
				}
			}
		}
		
		this.f7secondSeller.setValue(secondSalesMen.toArray());
		setInsiderEntityView();
		prmtRecommendInsider.setValue(null);
	}

	protected void btnEditAttach_actionPerformed(ActionEvent e) throws Exception {
		super.btnEditAttach_actionPerformed(e);
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		if (room == null) {
			MsgBox.showInfo("请先选择房间!");
			return;
		}
		String roomId = room.getId().toString();
		if (roomId != null) {
			PurchaseRoomAttachmentEntryCollection resList = RoomBindUI.showEditUI(this, roomId, (PurchaseRoomAttachmentEntryCollection) this.txtAttachRoom.getUserObject());
			if (resList != null) {
				this.updateAttachmentAmount(resList);
			}
		}
	}
					
		/**
	 * 重新设置主销售顾问和其他销售顾问
	 */
	protected void setSeller(){
		/**
		 * 重新设置主销售顾问和其他销售顾问
		 * by renliang at 2010-7-13
		 */
		this.f7Seller.setValue(null);	
		this.f7secondSeller.setValue(null);
		
		List secondSalesMen = new ArrayList();
		int num  = tblCustomerInfo.getRowCount();
		
		for (int i = 0; i <num ; i++) {
			IRow row = tblCustomerInfo.getRow(i);
			//判断是否是主销售顾问
			boolean result = ((Boolean)row.getCell("seq").getValue()).booleanValue();
			//根据id得到销售顾问的对象
			UserInfo info  = getSaleManbyId(row.getCell("salesId").getValue().toString());
			if(info!=null){
				if(result){
					this.f7Seller.setValue(info);	
				}else{
					if(this.f7secondSeller.getValue()!=null){
						Object[] o = (Object[])this.f7secondSeller.getValue();
						for (int j = 0; j< o.length; i++) {
							secondSalesMen.add(o[i]);
						}
					}
					
					if(!secondSalesMen.contains(info)){
						secondSalesMen.add(info);
					}
					
				}
			}
		}
		
		this.f7secondSeller.setValue(secondSalesMen.toArray());
	
	}

	public void updateAttachmentAmount(PurchaseRoomAttachmentEntryCollection resList) throws BOSException {
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		if (room == null) {
			this.txtAttachRoom.setText(null);
			this.txtAttachmentAmount.setValue(null);
			return;
		}
		BigDecimal attachAmount = FDCHelper.ZERO;

		PurchaseRoomAttachmentEntryCollection attachs = new PurchaseRoomAttachmentEntryCollection();
		if (resList == null) {
			if (this.editData.getId() != null) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.getSelector().add("*");
				view.getSelector().add("attachmentEntry.*");
				view.getSelector().add("attachmentEntry.room.*");
				view.getSelector().add("attachmentEntry.room.building.*");
				view.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("attachmentEntry.head.id", room.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("head.id", this.editData.getId().toString()));
				attachs = PurchaseRoomAttachmentEntryFactory.getRemoteInstance().getPurchaseRoomAttachmentEntryCollection(view);
			}
		} else {
			attachs = resList;
		}
		this.txtAttachRoom.setUserObject(attachs);

		String attText = "";
		for (int i = 0; i < attachs.size(); i++) {
			PurchaseRoomAttachmentEntryInfo aRoom = attachs.get(i);
			if(attachs.size()==1)
			{
				attText += aRoom.getAttachmentEntry().getRoom().getNumber();
			}else
			{
				attText += aRoom.getAttachmentEntry().getRoom().getNumber();
				if(i!=attachs.size()-1)
				{
					attText = attText+";";
				}
			}
			
			if (aRoom.getMergeAmount() != null) {
				attachAmount = attachAmount.add(aRoom.getMergeAmount());
			}
		}
		this.txtAttachRoom.setText(attText);
		this.txtAttachmentAmount.setValue(attachAmount);
	}


	public String getAgioDes() {
//		return SHEHelper.getAgioDes((AgioEntryCollection)this.txtAgio.getUserObject(),
//				(SpecialAgioEnum)this.comboSpecialAgioType.getSelectedItem(), this.txtSpecialAgio.getBigDecimalValue(), 
//				this.currAgioParam.isToInteger(),false,this.currAgioParam.getToIntegerType(), this.currAgioParam.getDigit());
		return null;
	}

	
	/**
	 * 更新交易总价,以及打折数据
	 * */
	private void updateTotalAmount(RoomInfo room) {}




	/**
	 * 获取房间的有效面积
	 * 
	 * @param room
	 * @param isLocaleSell
	 *            是否是现售
	 * @return 用来进行计算的有效面积
	 * */
	private BigDecimal getValidArea(RoomInfo room, boolean isLocaleSell) {
		boolean isCalByRoomArea = room.isIsCalByRoomArea();
		BigDecimal area = null;
		if (isCalByRoomArea) {
			// 如果使现售,成交单价按照实测面积计算
			if (isLocaleSell) {
				area = room.getActualRoomArea();
			} else {
				area = room.getRoomArea();
			}
		} else {
			if (isLocaleSell) {
				area = room.getActualBuildingArea();
			} else {
				area = room.getBuildingArea();
			}
		}
		if (area == null) {
			area = FDCHelper.ZERO;
		}
		return area;
	}
	

	private void updateLoanAndAFAmount() {
		BigDecimal loanAmount = FDCHelper.ZERO;
		BigDecimal afAmount = FDCHelper.ZERO;

		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			if (!(row.getCell("moneyName").getValue() instanceof MoneyDefineInfo)) {
				continue;
			}
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell("moneyName").getValue();
			BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
			if (amount == null) {
				amount = FDCHelper.ZERO;
			}
			if (moneyName != null) {
				if (moneyName.getMoneyType().equals(MoneyTypeEnum.LoanAmount)) {
					loanAmount = loanAmount.add(amount);
				} else if (moneyName.getMoneyType().equals(MoneyTypeEnum.AccFundAmount)) {
					afAmount = afAmount.add(amount);
				}
			}
		}
		this.txtLoanAmount.setValue(loanAmount);
		this.txtAFundAmount.setValue(afAmount);
	}

	/**
	 * 如果按按揭款大于1W，将按揭款和公积金的尾数(万以下)加入到首期款中,如果没有首期款,则加到按揭,公积金款项前的最后一行 modified by
	 * zhicheng_jin 2008-09-09 增加千位调整 zhicheng_jin 090311
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
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			Object obj = row.getCell("moneyName").getValue();
			if (!(obj instanceof MoneyDefineInfo)) {
				continue;
			}
			MoneyDefineInfo moneyDefineInfo = (MoneyDefineInfo) obj;

			MoneyTypeEnum moneyType = moneyDefineInfo.getMoneyType();
			if (moneyType.equals(MoneyTypeEnum.FisrtAmount)) {// 首付
				firstAmountRow = row;
			} else if (moneyType.equals(MoneyTypeEnum.LoanAmount)) {// 按揭
				beforeLoan = false;
				BigDecimal loanAmount = (BigDecimal) row.getCell("amount").getValue();
				if (loanAmount.compareTo(littleUnit) <= 0) {
					continue;
				}
				BigDecimal littleAmount = SHEHelper.remainder(loanAmount, littleUnit);
				toReducedRows.put(row, loanAmount.subtract(littleAmount));
				totalLittleLoanAmount = totalLittleLoanAmount.add(littleAmount);
			} else if (moneyType.equals((MoneyTypeEnum.AccFundAmount))) {// 公积金
				beforeLoan = false;
				BigDecimal accFundAmount = (BigDecimal) row.getCell("amount").getValue();
				if (accFundAmount.compareTo(littleUnit) <= 0) {
					continue;
				}
				BigDecimal littleAmount = SHEHelper.remainder(accFundAmount, littleUnit);
				toReducedRows.put(row, accFundAmount.subtract(littleAmount));
				totalLittleLoanAmount = totalLittleLoanAmount.add(littleAmount);
			} else {
				if (beforeLoan)
					lastRowBeforeLoan = row;
			}
		}
		// 将按揭的尾数加入到首期款上
		// 如果没有首期款项,则将尾数加到按揭,公积金款项前的最后一行
		IRow toAddedRow = firstAmountRow != null ? firstAmountRow : lastRowBeforeLoan;

		if (toAddedRow != null) {
			PurchasePayListEntryInfo firstPay = (PurchasePayListEntryInfo) toAddedRow.getUserObject();
			// 如果已经付清，则不再做变化
			BigDecimal firstActPayAmount = firstPay.getActPayAmount();
			if (firstActPayAmount == null) {
				firstActPayAmount = FDCHelper.ZERO;
			}
			BigDecimal apAmount = firstPay.getApAmount();
			if (apAmount == null) {
				apAmount = FDCHelper.ZERO;
			}
			if (apAmount.compareTo(firstActPayAmount) == 0) {
				return;
			}
			Set to = toReducedRows.keySet();
			for (Iterator itor = to.iterator(); itor.hasNext();) {
				IRow row = (IRow) itor.next();
				BigDecimal value = (BigDecimal) toReducedRows.get(row);
				row.getCell("amount").setValue(value);
			}

			BigDecimal srcFirstAmount = (BigDecimal) toAddedRow.getCell("amount").getValue();
			toAddedRow.getCell("amount").setValue(srcFirstAmount.add(totalLittleLoanAmount));
		}
	}

	private void updatePayList() {
		boolean isEarnestInHouseAmount = this.editData.isIsEarnestInHouseAmount();
//		boolean isEarnestInHouseAmount = true;
		List toDelRowIds = new ArrayList();
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			PurchasePayListEntryInfo entry = (PurchasePayListEntryInfo) row.getUserObject();
			BigDecimal actAmount = entry.getActPayAmount();
			// BigDecimal apAmount = entry.getApAmount();
			if (actAmount == null || actAmount.compareTo(FDCHelper.ZERO) <=0 ) {
				toDelRowIds.add(new Integer(i));
			}
		}

		for (int i = toDelRowIds.size() - 1; i >= 0; i--) {
			Integer tmp = (Integer) toDelRowIds.get(i);
			this.tblPayList.removeRow(tmp.intValue());
		}

		SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();
		if (payType != null) {
			PayListEntryCollection payList = payType.getPayLists();
			BigDecimal eareatMoney = FDCHelper.ZERO;
			BigDecimal remain = this.txtContractTotalAmount.getBigDecimalValue();
			if (remain == null) 	remain = FDCHelper.ZERO;
			
			CRMHelper.sortCollection(payList, "seq", true);
			List toAddRowPurEntry = new ArrayList();
			boolean[] hasRelated = new boolean[tblPayList.getRowCount()];// 标识现有各行是否已被关联
			for (int i = 0; i < payList.size(); i++) {
				PayListEntryInfo entry = payList.get(i);
				if(!MoneyTypeEnum.ReplaceFee.equals(entry.getMoneyDefine().getMoneyType())){
				PurchasePayListEntryInfo purEntry = new PurchasePayListEntryInfo();
				Date curDate = new Date();
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
				BigDecimal amount = FDCHelper.ZERO;	//当前明细的应收金额
				if (entry.getValue() != null) {
					amount = entry.getValue();
				} else {
					BigDecimal proportion = entry.getProportion();
					amount = FDCHelper.toBigDecimal(this.txtContractTotalAmount.getBigDecimalValue());
					if (amount == null) {
						amount = FDCHelper.ZERO;
					}
					amount = amount.multiply(proportion).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
				}
				
				MoneyDefineInfo moneyDefine = entry.getMoneyDefine();
				if (moneyDefine.getMoneyType().equals(MoneyTypeEnum.EarnestMoney)) {
					eareatMoney = eareatMoney.add(amount);
				} else {
					//定金计入楼款时
					if(isEarnestInHouseAmount){
						amount = amount.subtract(eareatMoney);
					}else{
						
					}
					eareatMoney = FDCHelper.ZERO;
					if (amount.compareTo(FDCHelper.ZERO) < 0) {
						amount = FDCHelper.ZERO;
					}
				}
				//定金不计入房款时，对于定金款项，不从剩余金额 remain中减
				if(!isEarnestInHouseAmount  &&  moneyDefine.getMoneyType().equals(MoneyTypeEnum.EarnestMoney)){
					
				}else{
					if (amount.compareTo(remain) > 0) {
						amount = remain;
						remain = FDCHelper.ZERO;
					} else {
						remain = remain.subtract(amount);
					}	
				}
				
				if (i == payList.size() - 1) {  //最后一行
					amount = amount.add(remain);
				}
				purEntry.setAppAmount(amount);
				purEntry.setMoneyDefine(moneyDefine);
				purEntry.setCurrency(entry.getCurrency());
				purEntry.setTerm(entry.getTerm());
				purEntry.setMonthInterval(entry.getMonthInterval());

				boolean isExist = false;
				for (int j = 0; j < tblPayList.getRowCount(); j++) {
					if (hasRelated[j]) {
						continue;
					}

					IRow row = tblPayList.getRow(j);
					PurchasePayListEntryInfo oldEntry = (PurchasePayListEntryInfo) row.getUserObject();
					// 新付款计划中与已收款的付款分录有对应项
					if(oldEntry==null || oldEntry.getMoneyDefine()==null || oldEntry.getMoneyDefine().getMoneyType()==null)
						continue;
					if (oldEntry.getMoneyDefine().getMoneyType().equals(moneyDefine.getMoneyType())) {
						hasRelated[j] = true;
						isExist = true;
						BigDecimal oldActPayAmount = oldEntry.getActRevAmount();
						if (oldActPayAmount == null)	oldActPayAmount = FDCHelper.ZERO;
		
						if (oldEntry.getAppAmount().compareTo(oldActPayAmount) == 0) {
							break;
						}
						if (oldEntry.getActRevAmount() == null || amount.compareTo(oldActPayAmount) > 0) {
							// 因为分录如果已收款,分录ID可能被收款单关联,已有收款的明细分录的ID需保证不变
							// zhicheng_jin 090422
							// TODO 待验证：其实如果有收款,认购单肯定是不让修改的,这里可能根本不会用到
							purEntry.setId(oldEntry.getId());
							row.setUserObject(purEntry);
							row.getCell("date").setValue(purEntry.getAppDate());
							row.getCell("amount").setValue(purEntry.getApAmount());
							row.getCell("currency").setValue(purEntry.getCurrency());
							row.getCell("term").setValue(new Integer(purEntry.getTerm()));
							row.getCell("jiange").setValue(new Integer(purEntry.getMonthInterval()));
							row.getCell("des").setValue(purEntry.getDescription());
						}
						break;
					}
				}
				if (!isExist) {
					toAddRowPurEntry.add(purEntry);
				}
			  }
			}
			for (int i = 0; i < toAddRowPurEntry.size(); i++) {
				addPayListEntryRow((PurchasePayListEntryInfo) toAddRowPurEntry.get(i));
			}
			updateLoanAndAFAmount();
		}
	}


	private void addPayListEntryRow(PurchasePayListEntryInfo entry) {
		IRow row = this.tblPayList.addRow();
		row.setUserObject(entry);
		row.getCell("moneyName").setValue(entry.getMoneyDefine());
		row.getCell("date").setValue(entry.getAppDate());
		row.getCell("amount").setValue(entry.getApAmount());
		row.getCell("currency").setValue(entry.getCurrency());
		row.getCell("term").setValue(new Integer(entry.getTerm()));
		row.getCell("jiange").setValue(new Integer(entry.getMonthInterval()));
		row.getCell("des").setValue(entry.getDescription());
		row.getCell("refundmentAmount").setValue(entry.getHasRefundmentAmount());
		row.getCell("hasRemitAmount").setValue(entry.getHasTransferredAmount());
		row.getCell("hasToFeeAmount").setValue(entry.getHasToFeeAmount());
		row.getCell("actDate").setValue(entry.getActRevDate());
		row.getCell("actAmount").setValue(entry.getActPayAmount());

		// end
		BigDecimal apAmount = entry.getApAmount();
		BigDecimal actAmount = entry.getActPayAmount();
		if (apAmount == null) {
			apAmount = FDCHelper.ZERO;
		}
		if (actAmount == null) {
			actAmount = FDCHelper.ZERO;
		}
		if (actAmount.compareTo(FDCHelper.ZERO) != 0) {
			row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			row.getCell("moneyName").getStyleAttributes().setLocked(true);
			row.getCell("date").getStyleAttributes().setLocked(true);
			row.getCell("currency").getStyleAttributes().setLocked(true);
			row.getCell("term").getStyleAttributes().setLocked(true);
			row.getCell("jiange").getStyleAttributes().setLocked(true);
			if (actAmount.compareTo(apAmount) == 0) {
				row.getCell("amount").getStyleAttributes().setLocked(true);

			}
		}
		
		if(!this.editData.isIsEarnestInHouseAmount()  &&  entry.getMoneyDefine() != null  &&  MoneyTypeEnum.EarnestMoney.equals(entry.getMoneyDefine().getMoneyType())){
			row.getStyleAttributes().setBackground(Color.green);
		}
		//row.getCell("hasToFeeAmount").getEditor().s;
	}

	protected void chkIsFitmentToContract_actionPerformed(ActionEvent e) throws Exception {
		this.updataRoomContractAndDealAmount();
	}

	protected void txtAttachmentAmount_dataChanged(DataChangeEvent e) throws Exception {
		this.updataRoomContractAndDealAmount();
	}

	protected void txtFitmentAmount_dataChanged(DataChangeEvent e) throws Exception {
		BigDecimal fitAmount = this.txtFitmentAmount.getBigDecimalValue();
		if (fitAmount == null) {
			this.txtFitmentPrice.setValue(null);
			return;
		}
		RoomInfo room = (RoomInfo)this.txtRoomNumber.getUserObject();
		if (room == null) {
			return;
		}
		updateFitmentPrice(room, fitAmount);
		
		updataRoomContractAndDealAmount();
	}

	/**
	 * 更新装修单价
	 * 装修总价的修改 或 销售方式的修改会 影响
	 */
	private void updateFitmentPrice(RoomInfo room, BigDecimal fitAmount) {
		if (fitAmount == null || room == null) {
			return;
		}
		boolean isLocaleSell = SellTypeEnum.LocaleSell.equals(this.comboSellType.getSelectedItem());
		BigDecimal area = getValidArea(room, isLocaleSell);
		if (area.compareTo(FDCHelper.ZERO) != 0) {
			this.txtFitmentPrice.setValue(fitAmount.divide(area, 2, BigDecimal.ROUND_HALF_UP));
		}
	}

	protected void txtFitmentPrice_dataChanged(DataChangeEvent e) throws Exception {
		super.txtFitmentPrice_dataChanged(e);
	}

	protected void btnChooseAgio_actionPerformed(ActionEvent e) throws Exception {
		super.btnChooseAgio_actionPerformed(e);
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		if (room == null) {
			MsgBox.showInfo("请先选择房间!");
			return;
		}

//		AgioParam agioParam = AgioSelectUI.showUI(this, room.getId().toString(), 
//				(AgioEntryCollection)this.txtAgio.getUserObject(), this.currAgioParam ,false);
//		if (agioParam != null) {
//			this.currAgioParam = agioParam;
//			
//			updataRoomContractAndDealAmount();			
//			
//			this.txtAgio.setUserObject(agioParam.getAgios());
//			this.comboPriceAccount.setSelectedItem(this.currAgioParam.getPriceAccountType());		
//		}
		
		
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		PurchaseInfo objectValue = (PurchaseInfo) super.getValue(pk);
		if (objectValue.getPayType() != null) {
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("payLists.*");
			sels.add("payLists.moneyDefine.*");
			SHEPayTypeInfo payTypeInfo = SHEPayTypeFactory.getRemoteInstance().getSHEPayTypeInfo(new ObjectUuidPK(objectValue.getPayType().getId()), sels);
			objectValue.setPayType(payTypeInfo);
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSorter().add(new SorterItemInfo("seq"));
		view.getSelector().add("*");
		view.getSelector().add("agio.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("head", objectValue.getId().toString()));
		PurchaseAgioEntryCollection agios = PurchaseAgioEntryFactory.getRemoteInstance().getPurchaseAgioEntryCollection(view);
		objectValue.getAgioEntrys().clear();
		objectValue.getAgioEntrys().addCollection(agios);

		view = new EntityViewInfo();
		view.getSorter().add(new SorterItemInfo("seq"));
		view.getSelector().add("*");
		view.getSelector().add("currency.*");
		view.getSelector().add("moneyDefine.*");
		filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("head", objectValue.getId().toString()));
		PurchasePayListEntryCollection payLists = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);
		objectValue.getPayListEntry().clear();
		objectValue.getPayListEntry().addCollection(payLists);
		return objectValue;
	}

	protected void tblCustomerInfo_editStopped(KDTEditEvent e) throws Exception {
		super.tblCustomerInfo_editStopped(e);
		if (e.getColIndex() == 2) {
			IRow row = this.tblCustomerInfo.getRow(e.getRowIndex());
			 //by zgy 替换原来逻辑，根据分录id列来取客户数，从交易表中取数，不从客户资料取数，防止客户资料与交易表同步更新
			FDCCustomerInfo customer = null;
			if(row.getCell("id").getValue()!=null){
				String id  = row.getCell("id").getValue().toString();
				customer  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
			}else{
				customer = (FDCCustomerInfo) row.getCell("customer").getValue();
			}
			setCustomerRowData(customer, row);
		}
	}

	protected void btnAddPayEntry_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddPayEntry_actionPerformed(e);
		PurchasePayListEntryInfo entry = new PurchasePayListEntryInfo();
		entry.setCurrency((CurrencyInfo) this.f7DealCurrency.getValue());
		entry.setTerm(1);
		entry.setMonthInterval(1);
		this.addPayListEntryRow(entry);
	}
	
	
	protected void btnInsertPayEntry_actionPerformed(ActionEvent e) throws Exception {
		IRow row = KDTableUtil.getSelectedRow(this.tblPayList);
		if(row==null) return;
		
		IRow addRow = this.tblPayList.addRow(row.getRowIndex());
		PurchasePayListEntryInfo entry = new PurchasePayListEntryInfo();
		entry.setCurrency((CurrencyInfo) this.f7DealCurrency.getValue());
		entry.setTerm(1);
		entry.setMonthInterval(1);
		addRow.setUserObject(entry);
		addRow.getCell("currency").setValue(entry.getCurrency());
		addRow.getCell("term").setValue(new Integer(entry.getTerm()));
		addRow.getCell("jiange").setValue(new Integer(entry.getMonthInterval()));
	}

	private void addOtherPayListEntryRow(PurchaseElsePayListEntryInfo entry) {
		IRow row = this.tblOtherPay.addRow();
		row.setUserObject(entry);
		row.getCell("moneyName").setValue(entry.getMoneyDefine());
		row.getCell("date").setValue(entry.getAppDate());
		row.getCell("currency").setValue(entry.getCurrency());
		row.getCell("amount").setValue(entry.getApAmount());
		row.getCell("actAmount").setValue(entry.getActPayAmount());
		row.getCell("actDate").setValue(entry.getActRevDate());
		row.getCell("des").setValue(entry.getDescription());
		row.getCell("refundmentAmount").setValue(entry.getRefundmentAmount());

		if (entry == null)
			return;
		if (entry.getActPayAmount() != null && entry.getActPayAmount().compareTo(FDCHelper.ZERO) > 0) {
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		}
	}


	protected void btnAddOtherPay_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddOtherPay_actionPerformed(e);
		PurchaseElsePayListEntryInfo entry = new PurchaseElsePayListEntryInfo();
		entry.setCurrency((CurrencyInfo) this.f7DealCurrency.getValue());

		addOtherPayListEntryRow(entry);
	}

	protected void btnInsertOtherPay_actionPerformed(ActionEvent e) throws Exception {
		IRow row = KDTableUtil.getSelectedRow(this.tblOtherPay);
		if(row==null) return;
		
		IRow addRow = this.tblOtherPay.addRow(row.getRowIndex());
		PurchaseElsePayListEntryInfo entry = new PurchaseElsePayListEntryInfo();
		entry.setCurrency((CurrencyInfo) this.f7DealCurrency.getValue());		
		addRow.setUserObject(entry);
		addRow.getCell("currency").setValue(entry.getCurrency());
	}	
	
	protected void btnRemovePayEntry_actionPerformed(ActionEvent e) throws Exception {
		super.btnRemovePayEntry_actionPerformed(e);
		int activeRowIndex = this.tblPayList.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblPayList.getRowCount();
		}
		IRow row = this.tblPayList.getRow(activeRowIndex);
		if (row == null) {
			return;
		}
		PurchasePayListEntryInfo entry = (PurchasePayListEntryInfo) row.getUserObject();
		BigDecimal actAmount = entry.getActPayAmount();
		if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) != 0) {
			MsgBox.showInfo("该分录已经付款,不能删除!");
			return;
		}
		this.tblPayList.removeRow(activeRowIndex);
	}

	protected void btnRemoveOtherPay_actionPerformed(ActionEvent e) throws Exception {
		super.btnRemoveOtherPay_actionPerformed(e);

		int activeRowIndex = this.tblOtherPay.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblOtherPay.getRowCount();
		}
		IRow row = this.tblOtherPay.getRow(activeRowIndex);
		if (row == null) {
			return;
		}

		PurchaseElsePayListEntryInfo entry = (PurchaseElsePayListEntryInfo) row.getUserObject();
		BigDecimal actAmount = entry.getActPayAmount();
		if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) != 0) {
			MsgBox.showInfo("该分录已经付款,不能删除!");
			return;
		}
		this.tblOtherPay.removeRow(activeRowIndex);
	}

	protected void tblPayList_editStopped(KDTEditEvent e) throws Exception {
		super.tblPayList_editStopped(e);

		boolean isEarnestInHouseAmount = this.editData.isIsEarnestInHouseAmount();
		BigDecimal contractAmount = this.txtContractTotalAmount.getBigDecimalValue();
		if (contractAmount != null && e.getRowIndex() >= 0) {
			IRow changedRow = this.tblPayList.getRow(e.getRowIndex());
			BigDecimal changedAmount = (BigDecimal) changedRow.getCell("amount").getValue();
			if (changedAmount == null)
				changedAmount = FDCHelper.ZERO;
			IRow nextRow = this.tblPayList.getRow(e.getRowIndex() + 1);
			if (nextRow != null) {
				BigDecimal nextAmount = contractAmount;
				for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
					if (i != (e.getRowIndex() + 1)) {
						IRow perRow = this.tblPayList.getRow(i);
						BigDecimal purAmount = (BigDecimal) perRow.getCell("amount").getValue();
						if (purAmount == null)
							purAmount = FDCHelper.ZERO;
						
						MoneyDefineInfo moneyDefine = (MoneyDefineInfo) perRow.getCell("moneyName").getValue();
						
						if(moneyDefine != null) {
							if(!isEarnestInHouseAmount && moneyDefine.getMoneyType().equals(MoneyTypeEnum.EarnestMoney))
								continue;
							
							if(moneyDefine.getMoneyType().equals(MoneyTypeEnum.PreconcertMoney))
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

	protected void btnLittleAdjust_actionPerformed(ActionEvent e) throws Exception {
		super.btnLittleAdjust_actionPerformed(e);
		addLittleLoanAmountToFisrtAmount();
		updateLoanAndAFAmount();
	}

	protected void txtSpecialAgio_dataChanged(DataChangeEvent e) throws Exception {
		updataRoomContractAndDealAmount();	
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	private void setTextFormat(KDFormattedTextField textField) {
		SHEHelper.setTextFormat(textField);
	}

	public PurchaseEditUI() throws Exception {
		super();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	public void actionShowContract_actionPerformed(ActionEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("purchase.id", this.editData.getId().toString()));
		view.setFilter(filter);
		SorterItemInfo sortItem = new SorterItemInfo("purchase.id");
		sortItem.setSortType(SortType.DESCEND);
		view.getSorter().add(sortItem);
		RoomSignContractCollection signContracts = RoomSignContractFactory.getRemoteInstance().getRoomSignContractCollection(view);
		if (signContracts == null || signContracts.isEmpty()) {
			MsgBox.showInfo(this, "没有对应的签约合同!");
			return;
		}

		String contractId = signContracts.get(0).getId().toString();
		if (signContracts.size() > 1) {
			logger.error("不可能有多个签约的哦．．");
		}
		UIContext uiContext = new UIContext(this);
		// uiContext.put("room",room);
		uiContext.put("ID", contractId);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSignContractEditUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
		// }
	}

	public void actionViewSign_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() != null) {
			RoomSignContractCollection collection = RoomSignContractFactory.getRemoteInstance().getRoomSignContractCollection("where purchase = '" + this.editData.getId().toString() + "' order by createTime desc");
			if (collection.size() > 0) {
				UIContext context = new UIContext();
				context.put(UIContext.ID, collection.get(0).getId().toString());
				context.put(UIContext.OWNER, this);
				IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSignContractEditUI.class.getName(), context, null, "FINDVIEW");
				window.show();
			} else {
				MsgBox.showWarning("该认购单没有关联签约单！");
			}
		}
	}

	private PurchaseInfo purchase = null;
	private RoomSignContractInfo sign = null;

	void initInfo() {
		if (tabTotalFlowTable != null) {
			tabPurchase.remove(tabTotalFlowTable);
			tabTotalFlowTable = null;
		}
		if (tabActualPayTable != null) {
			tabPurchase.remove(tabActualPayTable);
			tabActualPayTable = null;
		}

		RoomInfo room = (RoomInfo) this.editData.getRoom();
		if (room.getSellState().equals(RoomSellStateEnum.PrePurchase) || room.getSellState().equals(RoomSellStateEnum.Purchase) || room.getSellState().equals(RoomSellStateEnum.Sign)) {
			purchase = room.getLastPurchase();
			sign = (RoomSignContractInfo)room.getLastSignContract();
		} else {
			purchase = null;
			sign = null;
		}
	}

	KDPanel tabTotalFlowTable = null;

	private void addTotalFlowTable() {
		PurchaseInfo purchase = this.editData;
		if (purchase == null) {
			return;
		}
		tabTotalFlowTable = new KDPanel();
		KDTable totalFlowTable;
		totalFlowTable = new KDTable();
		totalFlowTable.checkParsed();
		totalFlowTable.getStyleAttributes().setLocked(true);
		totalFlowTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		totalFlowTable.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
			public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
				// totalFlowTableClick(totalFlowTable, e);
			}
		});

		IColumn column = totalFlowTable.addColumn();
		column.setKey("biz");
		column = totalFlowTable.addColumn();
		column.setKey("apDate");
		String formatString = "yyyy-MM-dd";
		column.getStyleAttributes().setNumberFormat(formatString);
		column = totalFlowTable.addColumn();
		column.setKey("value");
		column = totalFlowTable.addColumn();
		column.setKey("actDate");
		formatString = "yyyy-MM-dd";
		column.getStyleAttributes().setNumberFormat(formatString);
		IRow headRow = totalFlowTable.addHeadRow();
		headRow.getCell("biz").setValue("业务名称");
		headRow.getCell("apDate").setValue("期限");
		headRow.getCell("value").setValue("是否完成");
		headRow.getCell("actDate").setValue("完成日期");
		RoomInfo room = (RoomInfo) purchase.getRoom();
		if (purchase != null && purchase.getPayType() != null) {
			if(!purchase.getPurchaseState().equals(PurchaseStateEnum.PurchaseApply)
					&& !purchase.getPurchaseState().equals(PurchaseStateEnum.PurchaseAuditing)
					&& !purchase.getPurchaseState().equals(PurchaseStateEnum.PurchaseAudit))
			return;
				
			room.setLastPurchase(purchase);		//这里只是为了传递认购单的付款方案信息进去而已
			
			RoomFullInfoUI.fillRowsToTotalFlowTable(totalFlowTable, room, purchase, sign);
		}
		tabTotalFlowTable.setLayout(new BorderLayout());
		tabTotalFlowTable.add(totalFlowTable);
		this.tabPurchase.add(tabTotalFlowTable, "总览");
	}

	protected KDPanel tabActualPayTable = null;

	protected void addActPayTable() {
		PurchaseInfo purchase = this.editData;
		if (this.purchase == null) {
			return;
		}
		tabActualPayTable = new KDPanel();
		KDTable actualPayTable = new KDTable();

		try {
			RoomInfo room = (RoomInfo) purchase.getRoom();
			if(room!=null) {
				room.setLastPurchase(purchase);		//这里只是为了传递认购单的付款方案信息进去而已
				
				Map tempMap = RoomFactory.getRemoteInstance().getRoomInfoCollectionMap(room, "ReceivingBillCollectionP");
				FDCReceivingBillCollection revs = (FDCReceivingBillCollection)tempMap.get("ReceivingBillCollectionP");
				if(revs!=null)
					SHEHelper.fillActPayTable(actualPayTable, revs,SHEReceivingBillEditUI.class);
			}
		} catch (EASBizException e) {
			this.handUIException(e);
		} catch (BOSException e) {
			this.handUIException(e);
		}

		tabActualPayTable.setLayout(new BorderLayout());
		tabActualPayTable.add(actualPayTable);
		this.tabPurchase.add(tabActualPayTable, "实收");
		
	}

	public void actionViewRoom_actionPerformed(ActionEvent e) throws Exception {
		UIContext context = new UIContext();
		context.put(UIContext.ID, this.editData.getRoom().getId().toString());
		context.put(UIContext.OWNER, this);
		IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomEditUI.class.getName(), context, null, "VIEW");
		window.show();
	}

	// 由于codingrule调用时的参数为业务对象。重写试下是不是这个对象太大导致传输数据过大 TODO 最好还是进行RPC改造
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			return;
		}
		boolean isExist = true;
		PurchaseInfo pur = new PurchaseInfo();
		if (currentOrgId.length() == 0 || !iCodingRuleManager.isExist(pur, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(pur, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(pur, currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(pur, currentOrgId);
			} else {
				String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(pur, currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(pur, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(pur, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager.isDHExist(pur, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}
			setNumberTextEnabled();
		}
	}

	protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if (orgId == null || orgId.trim().length() == 0) {
				orgId = OrgConstants.DEF_CU_ID;
			}
			if (iCodingRuleManager.isExist(caller, orgId)) {
				String number = "";
				if (iCodingRuleManager.isUseIntermitNumber(caller, orgId)) { // 此处的orgId与步骤1
					// ）
					// 的orgId时一致的
					// ，
					// 判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(caller, orgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(caller, orgId, null, null);
						Object object = null;
						if (iCodingRuleManager.isDHExist(caller, orgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						} else {
							number = iCodingRuleManager.getNumber(caller, orgId);
						}
					} else {
						// 只启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
						number = iCodingRuleManager.readNumber(caller, orgId);
					}
				} else {
					// 没有启用断号支持功能，此时获取了编码规则产生的编码
					String costCenterId = null;
					if (editData instanceof FDCBillInfo && ((FDCBillInfo) editData).getOrgUnit() != null) {
						costCenterId = ((FDCBillInfo) editData).getOrgUnit().getId().toString();
					} else {
						costCenterId = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
					}

					number = prepareNumberForAddnew(iCodingRuleManager, (FDCBillInfo) editData, orgId, costCenterId, null);
				}

				// 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
				prepareNumber(caller, number);
				if (iCodingRuleManager.isModifiable(caller, orgId)) {
					// 如果启用了用户可修改
					setNumberTextEnabled();
				}
				return;
			}
		} catch (Exception err) {
			handleCodingRuleError(err);

			setNumberTextEnabled();
		}

		// 把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
		setNumberTextEnabled();
	}

	protected void setNumberTextEnabled() {
		if (getNumberCtrl() != null) {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentSaleUnit();
			if (org != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(new PurchaseInfo(), org.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
			}
		}
	}

	//点击客户信息上移
	public void actionUp_actionPerformed(ActionEvent e) throws Exception {
		IRow selecRow = KDTableUtil.getSelectedRow(this.tblCustomerInfo);
		if(selecRow==null) return;
		if(selecRow.getRowIndex()==0){
			MsgBox.showInfo("已经是第一条客户信息！");
			return;
		}
		this.tblCustomerInfo.moveRow(selecRow.getRowIndex(), selecRow.getRowIndex()-1);
		KDTableUtil.setSelectedRow(this.tblCustomerInfo,selecRow.getRowIndex()-1);
		if(selecRow.getRowIndex()==1) {
			tblCustomerInfo.getRow(1).getCell("seq").setValue(Boolean.FALSE);
			tblCustomerInfo.getRow(0).getCell("seq").setValue(Boolean.TRUE);
		}
		
		setSeller();
	}
	
	//点击客户信息下移
	public void actionDown_actionPerformed(ActionEvent e) throws Exception {
		IRow selecRow = KDTableUtil.getSelectedRow(this.tblCustomerInfo);
		if(selecRow==null) return;
		if(selecRow.getRowIndex()==(this.tblCustomerInfo.getRowCount()-1)){
			MsgBox.showInfo("已经是最后一条客户信息！");
			return;
		}
//		这条语句对下移没用啊		
//		this.tblCustomerInfo.moveRow(selecRow.getRowIndex(), selecRow.getRowIndex()+1);
		tblCustomerInfo.removeRow(selecRow.getRowIndex());
		tblCustomerInfo.addRow(selecRow.getRowIndex()+1, selecRow);
		KDTableUtil.setSelectedRow(this.tblCustomerInfo,selecRow.getRowIndex()+1);
		if(selecRow.getRowIndex()==0) {
			tblCustomerInfo.getRow(1).getCell("seq").setValue(Boolean.FALSE);
			tblCustomerInfo.getRow(0).getCell("seq").setValue(Boolean.TRUE);
		}
		
		setSeller();
	}
	
	
	public void tblPreLayTime_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if(e.getType()!=1) return;
			
			IRow row = this.tblPreLayTime.getRow(e.getRowIndex());
			String id = (String)row.getCell("id").getValue();
			CommerceHelper.openTheUIWindow(this,PreconcertPostponeEditUI.class.getName(),id);
		}
	}
	
	public void setDateEnable(){
		
		if(this.getUIContext().get("sellProject") != null){
			sellProjectCurrent = (SellProjectInfo) this.getUIContext().get("sellProject");
		}else{
			sellProjectCurrent = this.editData.getSellProject();
		}
		if(sellProjectCurrent == null){
			return;
		}
		Map funcSetMap = setting.getFunctionSetMap();
		FunctionSetting funSet = (FunctionSetting)funcSetMap.get(sellProjectCurrent.getId()==null?null:sellProjectCurrent.getId().toString());
		if(funSet != null){
			if(!funSet.getIsEditPurAndSignDate().booleanValue()){
				this.pkPurchaseDate.setEnabled(false);
			}else{
				this.pkPurchaseDate.setEnabled(true);
			}
		}
	}

	
	protected void txtContractTotalAmount_dataChanged(DataChangeEvent e) throws Exception {
		this.updatePayList();
	}
	protected void pkPurchaseDate_dataChanged(DataChangeEvent e) throws Exception {
		super.pkPurchaseDate_dataChanged(e);
		if(this.pkPurchaseDate.getValue()==null || this.pkSignDate.getValue()==null){
			return;
		}
		Date purchaseDate = DateTimeUtils.truncateDate((Date)this.pkPurchaseDate.getValue());
		Date signDate = DateTimeUtils.truncateDate((Date)this.pkSignDate.getValue());
		if(purchaseDate.compareTo(signDate) == 1){
			MsgBox.showInfo(this,"约定签约日期必须在认购申请日期后，请重新输入！");
			pkPurchaseDate.setValue(e.getOldValue());
			SysUtil.abort();
		}
		//this.txtPlanSignTimeLimit.setText(Long.toString(DateTimeUtils.dateDiff("d",purchaseDate,signDate)));
		long diff = DateTimeUtils.dateDiff("d",purchaseDate,signDate);
		this.txtPlanSignTimeLimit.setValue(new Long(diff));
		this.txtPlanSignTimeLimit.setText(Long.toString(diff));
	}
	
	protected void pkSignDate_dataChanged(DataChangeEvent e) throws Exception {
		super.pkSignDate_dataChanged(e);
		if(this.pkPurchaseDate.getValue()==null || this.pkSignDate.getValue()==null){
			return;
		}
		Date purchaseDate = DateTimeUtils.truncateDate((Date)this.pkPurchaseDate.getValue());
		Date signDate = DateTimeUtils.truncateDate((Date)this.pkSignDate.getValue());
		if(purchaseDate.compareTo(signDate) == 1){
			MsgBox.showInfo(this,"约定签约日期必须在认购申请日期后，请重新输入！");
			pkSignDate.setValue(e.getOldValue());
			SysUtil.abort();
		}
		//this.txtPlanSignTimeLimit.setText(Long.toString(DateTimeUtils.dateDiff("d",purchaseDate,signDate)));
		long diff = DateTimeUtils.dateDiff("d",purchaseDate,signDate);
		this.txtPlanSignTimeLimit.setValue(new Long(diff));
		this.txtPlanSignTimeLimit.setText(Long.toString(diff));
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		
		super.actionUnAudit_actionPerformed(e);
		if (!saleOrg.isIsBizUnit())
		{
			this.actionEdit.setEnabled(false);
		}
//		setBtnStateWhenUnAudit();
		boolean isViewState = "VIEW".equals(this.getOprtState());
		this.modifyInfo.setEnabled(!isViewState);
		this.modifyName.setEnabled(!isViewState);
	
	}

	/*
	 * 反审批后一些按钮是不能用的 xin_wang 2010.09.21
	 */
	private void setBtnStateWhenUnAudit(){
		//客户界面
		this.btnUp.setEnabled(false);
		this.btnDown.setEnabled(false);
		this.btnAddNewCustomer.setEnabled(false);
		this.btnAddCustomer.setEnabled(false);
		this.btnDeleteCustomer.setEnabled(false);
//		this.modifyInfo.setEnabled(false);
//		this.modifyName.setEnabled(false);
		
		//认购页签
		this.f7PayType.setAccessAuthority(0);
		this.f7PayType.setEnabled(false);
		this.btnChooseAgio.setEnabled(false);
		//付款明细页签
		this.btnInsertPayEntry.setEnabled(false);
		this.btnRemovePayEntry.setEnabled(false);
		this.btnAddPayEntry.setEnabled(false);
		
		this.tblPayList.getStyleAttributes().setLocked(true);
		
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
 		 int index = this.tblCustomerInfo.getSelectManager().getActiveRowIndex();
		 IRow row = this.tblCustomerInfo.getRow(index);
		 String id  = null;
		 FDCCustomerInfo customerInfo = null;
		 
		 if(row == null){
			 MsgBox.showWarning("请选择行!");
		 }
		 else{
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
		 if(customerInfo.getCustomerType().equals(CustomerTypeEnum.PersonalCustomer)){
			 uiContext.put("isPerson",  "true");
		 }else{
			 uiContext.put("isPerson",  "false");
		 }
			try {
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create (CustomerEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			} catch (UIException ee) {
				logger.error(ee.getMessage()+"获得客户信息失败！");
				 }
			 }else{
				 MsgBox.showError("此行客户资料有误，请联系相关技术人员！");
				 abort();
			}
		 }	
	}
	
	public void actionAddNewCustomer_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAddNewCustomer_actionPerformed(e);
		UIContext uiContext = new UIContext(this);
		uiContext.put(CustomerEditUI.KEY_DESTORY_WINDOW, Boolean.TRUE);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerEditUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();

		CustomerEditUI cusEditUI = (CustomerEditUI) uiWindow.getUIObject();
		FDCCustomerInfo cus = (FDCCustomerInfo) cusEditUI.getUIContext().get(CustomerEditUI.KEY_SUBMITTED_DATA);
		if (cus != null) {
			addCustomerRow(cus);
			
			CustomerTypeEnum type = cus.getCustomerType();
			if(type.equals(CustomerTypeEnum.PersonalCustomer)){
				initNewCertificateName(true,-2);
			}else{
				initNewCertificateName(false,-2);
			}
			/**
			 * 修改新增客户后销售顾问不能带出的问题
			 * new update by renliang at 2010-7-24 
			 */
			UserInfo salesMan = cus.getSalesman();
			if(isAddMainCustomer()) {
				if(!isCorrectSaleMan(salesMan)){
					this.f7Seller.setValue(null);
				}else{
					this.f7Seller.setValue(salesMan);
				}
			}else{
				List list = new ArrayList();
				if(this.f7secondSeller.getValue() != null){
					Object[] o = (Object[])this.f7secondSeller.getValue();
					for (int i = 0; i < o.length; i++) {
						list.add(o[i]);
					}
				}
				if(isCorrectSaleMan(salesMan) && !list.contains(salesMan)){
					
					list.add(salesMan);
				}
				
				f7secondSeller.setValue(list.toArray());
			
			}
		}
		
		//标注必填项
		List list = new ArrayList();
		list.add("certificateNumber");
		setCustomerRequiredColor(this.tblCustomerInfo,list);
		
		setInsiderEntityView();
		prmtRecommendInsider.setValue(null);
	}
	/*
	 * 修改客户资料，反写认购单客户名称列表，反写客户资料信息，增加到客户交易表
	 *  by zgy  2010-12-6
	 */
	public void actionModifyName_actionPerformed(ActionEvent e)
			throws Exception {
		 int index = this.tblCustomerInfo.getSelectManager().getActiveRowIndex();
		 IRow row = this.tblCustomerInfo.getRow(index);
		 if(row == null){
			 MsgBox.showWarning("请选择行!");
		 }else{
			 checkModifyCustomerPermission();
			 KDWorkButton kd = (KDWorkButton)e.getSource();
			 if(kd.getName().equals("modifyInfo")){
				 row.getStyleAttributes().setLocked(true);
				 row.getStyleAttributes().setLocked(false);
				 row.getCell("customer").getStyleAttributes().setLocked(true);
//				 lockCustomerRow(false, row);
			
			 }else{
				 row.getStyleAttributes().setLocked(true);
				 row.getCell("propertyPercent").getStyleAttributes().setLocked(false);
				 row.getCell("des").getStyleAttributes().setLocked(false);
				 String id  = row.getCell("id").getValue().toString();
				 String name = row.getCell("customer").getValue().toString();
	//		     FDCCustomerInfo customerInfo  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
	//			 String customerId = customerInfo.getId().toString();
	//			 String customerName =customerInfo.getName().toString(); 
	
				 UIContext uiContext = new UIContext(this);
				 uiContext.put("id",  id);
				 uiContext.put("fid", this.editData.getId());
				 uiContext.put("name",name);
				 try {
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PurchaseCustomerModifyUI.class.getName(), uiContext, null, OprtState.ADDNEW);
					uiWindow.show();
				 } catch (UIException ee) {
					logger.error(ee.getMessage()+"获得客户信息失败！");
				 }
	//			 String customer_id  = row.getCell("id").getValue().toString();
	//		     FDCCustomerInfo customerInfo1  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(customer_id));
	//			 customerInfo1 = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(customerInfo1.getId()));
	//			 row.getCell("customer").setValue(customerInfo1);				
				 EntityViewInfo view = new EntityViewInfo();
				 FilterInfo filter = new FilterInfo();
				 SorterItemCollection sort = view.getSorter();
				 SorterItemInfo sortInfo = new SorterItemInfo("lastUpdateTime");
				 sortInfo.setSortType(SortType.DESCEND);
				 sort.add(sortInfo);
				 filter.getFilterItems().add(new FilterItemInfo("parent.id",this.editData.getId()));
				 filter.getFilterItems().add(new FilterItemInfo("customerID",id));
				 view.setFilter(filter);
				 PurCustomerCollection purCuss = PurCustomerFactory.getRemoteInstance().getPurCustomerCollection(view);
				 if(purCuss.size()!=0){
					 PurCustomerInfo pcinfo  = purCuss.get(0);
					 row.getCell("customer").setValue(pcinfo.getCustomerName());	
				 }
			 }	
		 }
	}
	
	
	private void checkModifyCustomerPermission() throws Exception{
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
		PermissionFactory.getRemoteInstance().checkFunctionPermission(new ObjectUuidPK(user.getId()), new ObjectUuidPK(org.getId()), SHE_FDCCUSTOMER_EDIT);
	}
	
	
	public boolean isModify() {
		return false ;
	}
}

