/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractButton;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.UIFocusTraversalPolicy;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.MarketUnitSaleManFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.CusClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCCommonPromptBox;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basecrm.client.RelateBillQueryUI;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.insider.InsiderInfo;
import com.kingdee.eas.fdc.sellhouse.AgioBillFactory;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;
import com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BankPaymentInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BizFlowEnum;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BizListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.IBaseTransaction;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MortagageControlEntryFactory;
import com.kingdee.eas.fdc.sellhouse.MortagageControlFactory;
import com.kingdee.eas.fdc.sellhouse.MortagageEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ProjectSet;
import com.kingdee.eas.fdc.sellhouse.PurAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPreChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEFunctionChooseRoomSetFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SignAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.fdc.sellhouse.AgioParam;
/**
 * output class name
 */
public abstract class BaseTransactionEditUI extends AbstractBaseTransactionEditUI {
	private static final Logger logger = CoreUIObject.getLogger(BaseTransactionEditUI.class);
	//================================附属房产列名====================================
	protected final static String AP_ROOMNUMBER = "roomNumber";// 房间
	protected final static String AP_BUILDINGAREA = "buildingArea";// 建筑面积
	protected final static String AP_ROOMAREA = "roomArea";// 套内面积
	protected final static String AP_BUILDINGPRICE = "buildingPrice";// 建筑单价
	protected final static String AP_ROOMPRICE = "roomPrice";// 套内单价
	protected final static String AP_STANDARDTOTALAMOUNT = "standardTotalAmount";// 标准总价
	protected final static String AP_AGIO = "agio";// 折扣
	protected final static String AP_ISMERGETOCONTRACT = "isMergeTocontract";// 是否合并入合同
	protected final static String AP_MERGEAMOUNT = "mergeAmount";// 合并金额
	//=================================收款单列名====================================
	protected final static String RB_NUMBER = "number";// 单据号
	protected final static String RB_BILLTYPE = "billType";// 单据类型
	protected final static String RB_CURRENCY = "currency";// 币别
	protected final static String RB_REVDATE= "revDate";// 收款日期
	protected final static String RB_REVPERSON = "revPerson";// 收款人
	protected final static String RB_AMOUNT = "amount";// 收款金额
	
	//=================================业务总揽列名====================================
	protected final static String BR_BIZNAME = "bizName";// 业务名称
	protected final static String BR_FINISHDATE = "finishDate";// 业务期限
	protected final static String BR_ISFINISH = "isFinish";// 是否完成
	protected final static String BR_ACTUALFINISHDATE = "actualFinishDate";// 完成日期
	//=================================付款明细列名====================================
	protected final static String PL_STATE = "state";// 状态
	protected final static String PL_MONEYNAME = "moneyName";// 款项名称
	protected final static String PL_APPDATE = "appDate";// 应收日期
	protected final static String PL_CURRENCY = "currency";// 币别
	protected final static String PL_APPAMOUNT = "appAmount";// 应收金额
	protected final static String PL_ACTAMOUNT = "actAmount";// 实收金额
	protected final static String PL_BALANCE = "balance";// 应收余额

	protected final static String PL_DES = "des";// 说明

	protected SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	
	protected List customer=new ArrayList();
	protected AgioParam currAgioParam=new AgioParam();
	protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
	protected boolean isDealAmountEdit=false;
	protected boolean isBizDateEdit=true;
	protected boolean isUseAgioScheme=true;
	protected boolean isAgioListCanEdit=true;
	
	protected boolean isQuitRoomAudit=false;
	protected boolean isChangeRoomAuidt=false;
	protected boolean isChangeAreaAuidt=false;
	protected MoneyDefineInfo fittmentMoneyDefine=SHEManageHelper.getFittmentMoneyDefine();;
	protected MoneyDefineInfo roomAttactMoneyDefine=SHEManageHelper.getRoomAttachMoneyDefine();
	protected CurrencyInfo currency=SHEManageHelper.getCurrencyInfo(null);
	protected int priceDigit=0;
	protected int digit=0;
	protected int toIntegerType=0;
	protected int priceToIntegerType=0;
	
	protected boolean isEditDealAmount=true;
	
	protected BaseTransactionInfo srcInfo=null;
	protected List saleMan=new ArrayList();
	
	private static final String CANTEDIT = "cantEdit";
	
	public BaseTransactionEditUI() throws Exception {
		super();
	}
	public void onLoad() throws Exception {
		initTblBizReview();
		initTblAttachProperty();
		initTblReceiveBill();
		initTblPayList();
		this.tblReceiveBill.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);	
		super.onLoad();
		initF7Room();
		initControl();
		initCustomer();
		setF7PayTypeFilter();
		setF7AgioSchemeFilter();
		
		SaleManPromptDialog comBox= new SaleManPromptDialog(this,saleMan,this.oprtState,editData.getId(),((BaseTransactionInfo)editData).getSellProject());
		comBox.setEnabledMultiSelection(true);
		this.f7Seller.setSelector(comBox);
		
		this.f7Seller.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.f7Seller.setEditable(false);
		this.f7Seller.getQueryAgent().setEnabledMultiSelection(true);
	}
	private void initCustomer() {
		this.labelCustomer1.setForeground(Color.BLUE);
		this.labelCustomer2.setForeground(Color.BLUE);
		this.labelCustomer3.setForeground(Color.BLUE);
		this.labelCustomer4.setForeground(Color.BLUE);
		this.labelCustomer5.setForeground(Color.BLUE);
	}
	protected void initWorkButton() {
		super.initWorkButton();
			
        this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
       
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
        this.menuItemUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
        
        this.btnAddCustomer.setIcon(EASResource.getIcon("imgTbtn_addgroup"));
        this.menuItemAddCustomer.setIcon(EASResource.getIcon("imgTbtn_addgroup"));
        
        this.btnReceiveBill.setIcon(EASResource.getIcon("imgTbtn_monadismpostil"));
        this.menuItemReceiveBill.setIcon(EASResource.getIcon("imgTbtn_monadismpostil"));
        
        this.btnMemberApply.setIcon(EASResource.getIcon("imgTbtn_usercollocatemanage"));
        this.menuItemMemberApply.setIcon(EASResource.getIcon("imgTbtn_usercollocatemanage"));
        
        this.btnPointPresent.setIcon(EASResource.getIcon("imgTbtn_rightsandinterests"));
        this.menuItemPointPresent.setIcon(EASResource.getIcon("imgTbtn_rightsandinterests"));
	       
        this.btnSubmitAudit.setIcon(EASResource.getIcon("imgTbtn_suitbest"));
	}
	protected void addActionListener(AbstractButton com) {
		EventListener[] listeners = (EventListener[]) listenersMap.get(com);
		if (listeners != null && listeners.length > 0) {
			for (int i = 0; i < listeners.length; i++) {
				com.addActionListener((ActionListener) listeners[i]);
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
	protected void attachListeners() {
		addDataChangeListener(this.f7Room);
		addDataChangeListener(this.f7PayType);
		addDataChangeListener(this.f7AgioScheme);
		addDataChangeListener(this.f7RecommendPerson);
		addDataChangeListener(this.txtFitmentAmount);
		addDataChangeListener(this.comboValuationType);
		addDataChangeListener(this.comboSellType);
		addDataChangeListener(this.f7Seller);
		addActionListener(this.chkIsFitmentToContract);
		addDataChangeListener(this.txtDealTotalAmount);
		addDataChangeListener(this.pkBizDate);
	}

	protected void detachListeners() {
		removeDataChangeListener(this.f7Room);
		removeDataChangeListener(this.f7PayType);
		removeDataChangeListener(this.f7AgioScheme);
		removeDataChangeListener(this.f7RecommendPerson);
		removeDataChangeListener(this.txtFitmentAmount);
		removeDataChangeListener(this.comboValuationType);
		removeDataChangeListener(this.comboSellType);
		removeDataChangeListener(this.f7Seller);
		removeActionListener(this.chkIsFitmentToContract);
		removeDataChangeListener(this.txtDealTotalAmount);
		removeDataChangeListener(this.pkBizAdscriptionDate);
	}
	protected KDTable getDetailTable() {
		return null;
	}
	
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	/**
	 * 审批事件扩充状态校验
	 */
	protected void auditCheck(){
	}
	
	/**
	 * 反审批事件扩充状态校验
	 */
	protected void unAuditCheck(){
	}
	
	/**
	 * 作废事件扩充状态校验
	 */
    protected void invalidCheck(){
    }
    
   
    
    /**
     * 屏蔽掉FDCBILLEdit里的这个方法，因为这个这只是考虑到了成本模块，CRM这边无用
     */
    protected void fetchInitData() throws Exception {
    }
    
    /**
     * 返回业务状态枚举审批通过状态枚举值
     */
    protected abstract TransactionStateEnum getBizStateAuditEnum();
    
    /**
     * 返回业务状态枚举提交状态枚举值
     */
    protected abstract TransactionStateEnum getBizStateSubmitEnum();
    
    /**
     * 返回业务状态枚举作废状态枚举值
     */
    protected abstract TransactionStateEnum getBizStateInvalidEnum();
    protected void setAuditButtonStatus(String oprtType){
    
    	Boolean bol=(Boolean)this.getUIContext().get("canSelectCustomer");
    	this.f7Seller.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
    	
    	if(STATUS_VIEW.equals(oprtType)) {
    		actionAudit.setVisible(true);
    		actionUnAudit.setVisible(true);
    		actionAudit.setEnabled(true);
    		actionUnAudit.setEnabled(true);
    		
    		this.actionSubmitAudit.setEnabled(false);
    		
    		this.actionAddLine.setEnabled(false);
    		this.actionRemoveLine.setEnabled(false);
    		this.actionInsertLine.setEnabled(false);
    		this.actionChooseAgio.setEnabled(false);
    		this.actionCustomerSelect.setEnabled(false);
    		
    		this.f7Seller.setEnabled(false);
    		
    		BaseTransactionInfo bill = (BaseTransactionInfo)editData;
    		if(editData!=null){
    			if(getBizStateAuditEnum().equals(bill.getBizState())){
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
    		
    		this.actionSubmitAudit.setEnabled(true);
    		
    		this.actionAddLine.setEnabled(true);
    		this.actionRemoveLine.setEnabled(true);
    		this.actionInsertLine.setEnabled(true);
    		this.actionChooseAgio.setEnabled(true);
    		
    		if(bol!=null&&!bol.booleanValue()){
//    			this.actionCustomerSelect.setEnabled(false);
    			this.f7Seller.setEnabled(false);
    		}else{
    			if(this.srcInfo!=null){
    				this.f7Seller.setEnabled(false);
//        			this.actionCustomerSelect.setEnabled(false);
        		}else{
        			this.f7Seller.setEnabled(true);
        			this.actionCustomerSelect.setEnabled(true);
        		}
    		}
    	}
    	if(!STATUS_ADDNEW.equals(this.getOprtState())) {
			if(this.tblAttachProperty.getRowCount()>0){
				this.btnAddAPLine.setEnabled(false);
				this.btnRemoveAPLine.setEnabled(false);
			}
		}
    }
    protected boolean checkCanSubmit() throws Exception {
		
		if(editData.getId()==null){
			return true;
		}
		//检查是否在工作流中
//		FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		
		TransactionStateEnum state = ((BaseTransactionInfo)editData).getBizState();
		if (state != null
				&& (getBizStateAuditEnum().equals(state))) {
			return false;
		}else{
			return true;
		}
		
	}
    protected void checkBeforeEditOrRemove(String warning) {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		
		FDCBillStateEnum state = ((BaseTransactionInfo)editData).getState();
		TransactionStateEnum bizState = ((BaseTransactionInfo)editData).getBizState();
		
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || bizState==TransactionStateEnum.TOPRE||
						bizState==TransactionStateEnum.TOPUR||bizState==TransactionStateEnum.TOSIGN)) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("该单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("该单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
	}
 
    protected String prepareNumberForAddnew (ICodingRuleManager iCodingRuleManager,FDCBillInfo info,String orgId,String costerId,String bindingProperty)throws Exception{

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
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			filter.getFilterItems().add(new FilterItemInfo("isValid", new Boolean(false),CompareType.NOTEQUALS));	
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", costerId));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
			}
			i++;
		}while (((IFDCBill)getBizInterface()).exists(filter) && i<1000);
		
		return number;
	}
	protected void initControl(){
		
		if (!isSaleHouseOrg)
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionReceiveBill.setEnabled(false);
			this.actionAddCustomer.setEnabled(false);
			this.actionMemberApply.setEnabled(false);
			this.actionPointPresent.setEnabled(false);
		}
		
		this.actionMemberApply.setVisible(false);
		this.actionPointPresent.setVisible(false);
		
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.btnCalculator.setVisible(true);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		this.txtTackDesc.setMaxLength(2000);
		this.txtNumber.setMaxLength(80);
		this.txtContractNumber.setMaxLength(80);
		
		this.txtFitmentAmount.setSupportedEmpty(true);
		
		this.actionAddCustomer.setVisible(false);
		this.actionReceiveBill.setVisible(false);
		
		this.comboValuationType.setEnabled(false);
		
		if(editData!=null&&!FDCBillStateEnum.SAVED.equals(((FDCBillInfo)editData).getState())){
			this.f7Room.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.comboSellType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.tblAttachProperty.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			if(this.f7Room.getValue()!=null) this.f7Room.setEnabled(false);
			if(!STATUS_ADDNEW.equals(this.getOprtState())) {
//				if(this.comboSellType.getSelectedItem()!=null) this.comboSellType.setEnabled(false);
//				if(this.comboValuationType.getSelectedItem()!=null) this.comboValuationType.setEnabled(false);
				if(this.tblAttachProperty.getRowCount()>0){
					this.btnAddAPLine.setEnabled(false);
					this.btnRemoveAPLine.setEnabled(false);
					this.tblAttachProperty.getColumn(AP_ROOMNUMBER).getStyleAttributes().setLocked(true);
				}
			}
		}
		
//		RoomDisplaySetting set = new RoomDisplaySetting(null,SHEParamConstant.PROJECT_SET_MAP);
		Map detailSet= RoomDisplaySetting.getNewProjectSet(null,((BaseTransactionInfo)editData).getSellProject().getId().toString());
		if(detailSet!=null){
			isDealAmountEdit =((Boolean)detailSet.get(SHEParamConstant.T2_IS_DEAL_AMOUNT_EDIT)).booleanValue();
			isBizDateEdit=((Boolean)detailSet.get(SHEParamConstant.T2_IS_BIZ_DATE_EDITABLE)).booleanValue();
			isUseAgioScheme=((Boolean)detailSet.get(SHEParamConstant.T2_IS_ENABLE_AGIO)).booleanValue();
			isAgioListCanEdit=((Boolean)detailSet.get(SHEParamConstant.T2_IS_ENABLE_ADJUST_AGIO)).booleanValue();
			
			isQuitRoomAudit=((Boolean)detailSet.get(SHEParamConstant.T2_IS_RE_PRICE_OF_QUIT_ROOM)).booleanValue();
			isChangeRoomAuidt=((Boolean)detailSet.get(SHEParamConstant.T2_IS_RE_PRICE_OF_CHANGE_ROOM)).booleanValue();
			isChangeAreaAuidt=((Boolean)detailSet.get(SHEParamConstant.T2_IS_AREA_CHANGE_NEED_AUDIT)).booleanValue();
		}
		SHEFunction();
		setTxtFormate();
		
//		try {
//			OrgUnitInfo currentOrgUnit=SysContext.getSysContext().getCurrentOrgUnit();
//			UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();	
//			String currDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()); 
//			boolean isPermit =MarketUnitControlFactory.getRemoteInstance().exists("where orgUnit.id = '"+currentOrgUnit.getId()+"' and controler.id = '"+currentUserInfo.getId()+"' ");
//			if(isPermit){
//				this.f7Seller.setEnabled(true);
//			}else{
//				boolean isDuty= MarketUnitSaleManFactory.getRemoteInstance().exists("where orgUnit.id = '"+currentOrgUnit.getId()+"' and " +
//					"dutyPerson.id = '"+currentUserInfo.getId()+"' and member.id = '"+ currentUserInfo.getId() +"' " +
//						"and accessionDate <= {ts '"+currDate+"'} and dimissionDate >= {ts '"+currDate+"'} and sellProject = '"+((BaseTransactionInfo)editData).getSellProject().getId()+"'");
//				if(isDuty){
//					this.f7Seller.setEnabled(true);
//				}else{
//					this.f7Seller.setEnabled(false);
//				}
//			}
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}catch (EASBizException e) {
//			e.printStackTrace();
//		}
		this.txtAgioDes.setMaxLength(255);
	}
	/**
	 * 设置控件格式
	 */
	protected void setTxtFormate(){
		HashMap value = SHEManageHelper.getCRMConstants(SysContext.getSysContext().getCurrentOrgUnit().getId());
		BaseTransactionInfo info=(BaseTransactionInfo)editData;
		if(SHEManageHelper.setPrecision(info.getPriceDigit())==null){
			priceDigit=SHEManageHelper.setPrecision(value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString()).intValue();
		}else{
			priceDigit=SHEManageHelper.setPrecision(info.getPriceDigit()).intValue();
		}
		if(SHEManageHelper.setPrecision(info.getDigit())==null){
			digit=SHEManageHelper.setPrecision(value.get(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT).toString()).intValue();
		}else{
			digit=SHEManageHelper.setPrecision(info.getDigit()).intValue();
		}
		if(SHEManageHelper.setRoundingMode(info.getToIntegerType())==null){
			toIntegerType=SHEManageHelper.setRoundingMode(value.get(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE).toString()).intValue();
		}else{
			toIntegerType=SHEManageHelper.setRoundingMode(info.getToIntegerType()).intValue();
		}
		if(SHEManageHelper.setRoundingMode(info.getPriceToIntegerType())==null){
			priceToIntegerType=SHEManageHelper.setRoundingMode(value.get(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE).toString()).intValue();
		}else{
			priceToIntegerType=SHEManageHelper.setRoundingMode(info.getPriceToIntegerType()).intValue();
		}
		
		this.txtDealTotalAmount.setPrecision(digit);
		this.txtDealTotalAmount.setRoundingMode(toIntegerType);
		
		this.txtContractTotalAmount.setPrecision(digit);
		this.txtContractTotalAmount.setRoundingMode(toIntegerType);
		
		this.txtStandardTotalAmount.setPrecision(digit);
		this.txtStandardTotalAmount.setRoundingMode(toIntegerType);
		
		this.txtAttachPropertyTotalAmount.setPrecision(digit);
		this.txtAttachPropertyTotalAmount.setRoundingMode(toIntegerType);
		
		this.txtFitmentAmount1.setPrecision(digit);
		this.txtFitmentAmount1.setRoundingMode(toIntegerType);
		
		this.txtFitmentAmount.setPrecision(digit);
		this.txtFitmentAmount.setRoundingMode(toIntegerType);
		
		this.txtLoanAmount.setPrecision(digit);
		this.txtLoanAmount.setRoundingMode(toIntegerType);
		
		this.txtAFundAmount.setPrecision(digit);
		this.txtAFundAmount.setRoundingMode(toIntegerType);
		
		this.txtDealBuildPrice.setPrecision(priceDigit);
		this.txtDealBuildPrice.setRoundingMode(priceToIntegerType);
		
		this.txtDealRoomPrice.setPrecision(priceDigit);
		this.txtDealRoomPrice.setRoundingMode(priceToIntegerType);
		
		this.txtContractBuildPrice.setPrecision(priceDigit);
		this.txtContractBuildPrice.setRoundingMode(priceToIntegerType);
		
		this.txtContractRoomPrice.setPrecision(priceDigit);
		this.txtContractRoomPrice.setRoundingMode(priceToIntegerType);
		
		this.txtRoomPrice.setPrecision(priceDigit);
		this.txtRoomPrice.setRoundingMode(priceToIntegerType);
		
		this.txtBuildingPrice.setPrecision(priceDigit);
		this.txtBuildingPrice.setRoundingMode(priceToIntegerType);
		
		this.txtFitmentPrice.setPrecision(priceDigit);
		this.txtFitmentPrice.setRoundingMode(priceToIntegerType);
		
		this.tblPayList.getColumn(PL_APPAMOUNT).setEditor(SHEManageHelper.getNumberCellEditor(digit,toIntegerType));
		this.tblPayList.getColumn(PL_ACTAMOUNT).setEditor(SHEManageHelper.getNumberCellEditor(digit,toIntegerType));
		this.tblPayList.getColumn(PL_BALANCE).setEditor(SHEManageHelper.getNumberCellEditor(digit,toIntegerType));
		
		this.tblAttachProperty.getColumn(AP_STANDARDTOTALAMOUNT).setEditor(SHEManageHelper.getNumberCellEditor(digit,toIntegerType));
		this.tblAttachProperty.getColumn(AP_MERGEAMOUNT).setEditor(SHEManageHelper.getNumberCellEditor(digit,toIntegerType));
		this.tblAttachProperty.getColumn(AP_ROOMPRICE).setEditor(SHEManageHelper.getNumberCellEditor(priceDigit,priceToIntegerType));
		this.tblAttachProperty.getColumn(AP_BUILDINGPRICE).setEditor(SHEManageHelper.getNumberCellEditor(priceDigit,priceToIntegerType));
		
		this.tblPayList.getColumn(PL_APPAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(digit));
		this.tblPayList.getColumn(PL_ACTAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(digit));
		this.tblPayList.getColumn(PL_BALANCE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(digit));
		
		this.tblAttachProperty.getColumn(AP_STANDARDTOTALAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(digit));
		this.tblAttachProperty.getColumn(AP_MERGEAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(digit));
		this.tblAttachProperty.getColumn(AP_ROOMPRICE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(priceDigit));
		this.tblAttachProperty.getColumn(AP_BUILDINGPRICE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(priceDigit));
	}
	/**
	 * 设置值格式
	 */
	protected void setTxtFormateNumerValue(){
		BaseTransactionInfo info=(BaseTransactionInfo)editData;
		info.setDealTotalAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtDealTotalAmount.getBigDecimalValue()));
		info.setContractTotalAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtContractTotalAmount.getBigDecimalValue()));
		info.setStrdTotalAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtStandardTotalAmount.getBigDecimalValue()));
		info.setAttachmentAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtAttachPropertyTotalAmount.getBigDecimalValue()));
		info.setFitmentTotalAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtFitmentAmount.getBigDecimalValue()));
		info.setLoanAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtLoanAmount.getBigDecimalValue()));
		info.setAccFundAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtAFundAmount.getBigDecimalValue()));
		
		info.setDealBuildPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtDealBuildPrice.getBigDecimalValue()));
		info.setDealRoomPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtDealRoomPrice.getBigDecimalValue()));
		info.setContractBuildPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtContractBuildPrice.getBigDecimalValue()));
		info.setContractRoomPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtContractRoomPrice.getBigDecimalValue()));
		info.setStrdRoomPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtRoomPrice.getBigDecimalValue()));
		info.setStrdBuildingPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtBuildingPrice.getBigDecimalValue()));
		info.setFitmentPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtFitmentPrice.getBigDecimalValue()));
	}
	/**
	 * 初始化F7房间
	 */
	private void initF7Room() {
		SellProjectInfo sellProject=((BaseTransactionInfo)editData).getSellProject();
		BuildingInfo buildInfo = (BuildingInfo) this.getUIContext().get("building");
		BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo) this.getUIContext().get("buildUnit");
		
		Boolean bol=(Boolean)this.getUIContext().get("canSelectCustomer");
		if(bol!=null&&!bol.booleanValue()){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			
			if(sellProject!=null)
				try {
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", SHEManageHelper.getAllSellProjectCollection(null, sellProject),CompareType.INCLUDE));
				} catch (BOSException e) {
					e.printStackTrace();
				}
			
			filter.getFilterItems().add(new FilterItemInfo("sellState", RoomSellStateEnum.ONSHOW_VALUE));
			view.setFilter(filter);
			this.f7Room.setEntityViewInfo(view);
			
			KDBizPromptBox f7RoomBox = new KDBizPromptBox();
			f7RoomBox.setDisplayFormat("$name$");
			f7RoomBox.setEditFormat("$name$");
			f7RoomBox.setCommitFormat("$name$");
			f7RoomBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSelectQuery");
			f7RoomBox.setEntityViewInfo(view);
			KDTDefaultCellEditor f7RoomEditor = new KDTDefaultCellEditor(f7RoomBox);
			this.tblAttachProperty.getColumn(AP_ROOMNUMBER).setEditor(f7RoomEditor);
		}else{
			NewFDCRoomPromptDialog dialog=new NewFDCRoomPromptDialog(Boolean.FALSE, buildInfo, buildUnitInfo,
					MoneySysTypeEnum.SalehouseSys, null,sellProject);
			this.f7Room.setSelector(dialog);
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			if(sellProject!=null)
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId()));
			if(buildInfo!=null)
				filter.getFilterItems().add(new FilterItemInfo("building.id", buildInfo.getId()));
			if(buildUnitInfo!=null)
				filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnitInfo.getId()));
			view.setFilter(filter);
			this.f7Room.setEntityViewInfo(view);
			
			KDBizPromptBox f7RoomBox = new KDBizPromptBox();
			f7RoomBox.setDisplayFormat("$name$");
			f7RoomBox.setEditFormat("$name$");
			f7RoomBox.setCommitFormat("$name$");
			KDTDefaultCellEditor f7RoomEditor = new KDTDefaultCellEditor(f7RoomBox);
			this.tblAttachProperty.getColumn(AP_ROOMNUMBER).setEditor(f7RoomEditor);
			f7RoomBox.setSelector(dialog);
		}
	}
	private void initTblBizReview(){
		tblBizReview.checkParsed();
		tblBizReview.removeRows();
		tblBizReview.getStyleAttributes().setLocked(true);
		tblBizReview.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblBizReview.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
			public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
				totalFlowTableClick(tblBizReview, e);
			}
		});
	}
	private void totalFlowTableClick(final KDTable totalFlowTable,
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
		try {
			if (e.getButton() == 1 && e.getClickCount() == 2) {
				if(e.getType()!=1) return;
				IRow row = totalFlowTable.getRow(e.getRowIndex());
				
				RoomInfo room=(RoomInfo)this.f7Room.getValue();
				TransactionCollection tran=TransactionFactory.getRemoteInstance().getTransactionCollection("select billid from where room.id='"+room.getId()+"' and isValid=0 ");
				
				String tranBusinessOverViewId = row.getCell("id").getValue().toString();
//				BusinessTypeNameEnum bizFlow = (BusinessTypeNameEnum) row.getCell("bizName").getValue();
				String bizFlow = (String) row.getCell("bizName").getValue();
				Boolean isFinish = (Boolean) row.getCell("isFinish").getValue();
				
				PrePurchaseManageInfo prePurchaseManage = SHEManageHelper.getPrePurchaseManage(null,room,tran.get(0).getId().toString(),tran.get(0).getBillId().toString());
				PurchaseManageInfo purchaseManage = SHEManageHelper.getPurchaseManage(null,room,tran.get(0).getId().toString(),tran.get(0).getBillId().toString());
				SignManageInfo signManage = SHEManageHelper.getSignManage(null,room,tran.get(0).getId().toString(),tran.get(0).getBillId().toString());
				if(Boolean.TRUE.equals(isFinish)){
					if (bizFlow.equals(SHEManageHelper.PRELIMINARY)) {
						if (prePurchaseManage != null) {
							UIContext uiContext = new UIContext(this);
							uiContext.put("ID", prePurchaseManage.getId());
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
											PrePurchaseManageEditUI.class.getName(),
									uiContext, null, OprtState.VIEW);
							uiWindow.show();
						}
					}else if (bizFlow.equals(SHEManageHelper.PURCHASE)) {
						if (purchaseManage != null) {
							UIContext uiContext = new UIContext(this);
							uiContext.put("ID", purchaseManage.getId());
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
											PurchaseManageEditUI.class.getName(), uiContext,
									null, OprtState.VIEW);
							uiWindow.show();
						}
					} else if (bizFlow.equals(SHEManageHelper.SIGN)) {
						if (signManage != null) {
							UIContext uiContext = new UIContext(this);
							uiContext.put("ID", signManage.getId());
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
											SignManageEditUI.class.getName(),
									uiContext, null, OprtState.VIEW);
							uiWindow.show();
						}
					} else if (bizFlow.equals(SHEManageHelper.MORTGAGE)) {
						RoomLoanInfo roomLoan = SHEHelper.getRoomLoan(room);
						if(roomLoan != null){
							SHEHelper.toLoanBill(room,roomLoan,false);
						}
					} else if (bizFlow.equals(SHEManageHelper.ACCFUND)) {
						RoomLoanInfo roomLoan = SHEHelper.getRoomAccFund(room);
						if(roomLoan != null){
							SHEHelper.toLoanBill(room,roomLoan,false);
						}
					} else if (bizFlow.equals(SHEManageHelper.AREACOMPENSATE)) {
						RoomAreaCompensateInfo areaCompensation = SHEHelper.getRoomAreaCompensation(room);
						if (areaCompensation != null) {
							UIContext uiContext = new UIContext(this);
							uiContext.put("ID", areaCompensation.getId());
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
									RoomAreaCompensateEditUI.class.getName(),
									uiContext, null, "FINDVIEW");
							uiWindow.show();
						}
					} else if (bizFlow.equals(SHEManageHelper.JOIN)) {
						RoomJoinInfo joinIn = SHEHelper.getRoomJoinIn(room);
						if (joinIn != null) {
							SHEHelper.toJoinBill(room, joinIn, false);
						}
					} else if (bizFlow.equals(SHEManageHelper.INTEREST)) {
						RoomPropertyBookInfo propertyBook = SHEHelper.getRoomPropertyBook(room);
						if (propertyBook != null) {
							SHEHelper.toPropertyBill(room, propertyBook, false);
						}
					}else{//为已完成的付款明细时只能查看收款单
						SHEHelper.openRevBill(this, tranBusinessOverViewId,true);
					}
				}
				else {
					if (bizFlow.equals(SHEManageHelper.PRELIMINARY)) {
						if(purchaseManage != null || signManage != null){
							//已完成认购或签约
						}
					}else if (bizFlow.equals(SHEManageHelper.PURCHASE)) {
						if (signManage != null) {
							//已完成签约
						}else if(prePurchaseManage != null){
							//预定转认购
							SHEManageHelper.toTransactionBill(BOSUuid.read(prePurchaseManage.getId().toString()), prePurchaseManage.getBizState(),this,PurchaseManageEditUI.class.getName());
						}else{
							//新增认购单
							UIContext uiContext = new UIContext(this);
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
											PurchaseManageEditUI.class.getName(), uiContext,
									null, "ADDNEW");
							uiWindow.show();
						}
					} else if (bizFlow.equals(SHEManageHelper.SIGN)) {
						if (purchaseManage != null) {
							//认购转签约
							SHEManageHelper.toTransactionBill(BOSUuid.read(purchaseManage.getId().toString()), purchaseManage.getBizState(),this,SignManageEditUI.class.getName());
						}else if(prePurchaseManage != null){
							//预定转签约
							SHEManageHelper.toTransactionBill(BOSUuid.read(prePurchaseManage.getId().toString()), prePurchaseManage.getBizState(),this,SignManageEditUI.class.getName());
						}else{
							//新增签约单
							UIContext uiContext = new UIContext(this);
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
											SignManageEditUI.class.getName(),
									uiContext, null, "ADDNEW");
							uiWindow.show();
						}
					} else if (bizFlow.equals(SHEManageHelper.MORTGAGE)) {
						RoomLoanInfo roomLoan = SHEHelper.getRoomLoan(room);
						if(roomLoan != null){
							SHEHelper.toLoanBill(room,roomLoan,true);
						}
					} else if (bizFlow.equals(SHEManageHelper.ACCFUND)) {
						RoomLoanInfo roomLoan = SHEHelper.getRoomAccFund(room);
						if(roomLoan != null){
							SHEHelper.toLoanBill(room,roomLoan,true);
						}
					} else if (bizFlow.equals(SHEManageHelper.AREACOMPENSATE)) {
						RoomAreaCompensateInfo areaCompensation = SHEHelper.getRoomAreaCompensation(room);
						if (areaCompensation != null) {
							UIContext uiContext = new UIContext(this);
							uiContext.put("ID", areaCompensation.getId());
							IUIWindow uiWindow = UIFactory.createUIFactory(
									UIFactoryName.MODEL).create(
									RoomAreaCompensateEditUI.class.getName(),
									uiContext, null, "EDIT");
							uiWindow.show();
						}
					} else if (bizFlow.equals(SHEManageHelper.JOIN)) {
						RoomJoinInfo joinIn = SHEHelper.getRoomJoinIn(room);
						if (joinIn != null) {
							SHEHelper.toJoinBill(room, joinIn, true);
						}
					} else if (bizFlow.equals(SHEManageHelper.INTEREST)) {
						RoomPropertyBookInfo propertyBook = SHEHelper.getRoomPropertyBook(room);
						if (propertyBook != null) {
							SHEHelper.toPropertyBill(room, propertyBook, true);
						}
					}else{//为付款明细时新增收款单
						SHEHelper.openRevBill(this, tranBusinessOverViewId,false);
					}
				}
			}
		} catch (Exception exc) {
			handUIException(exc);
		} finally {
		}
	}
	/**
	 * 初始化付款明细
	 */
	private void initTblPayList() {
		this.tblPayList.checkParsed();
		
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.tblPayList.getColumn(PL_CURRENCY).setEditor(f7Editor);
		
		String formatString = "yyyy-MM-dd";
		this.tblPayList.getColumn(PL_APPDATE).getStyleAttributes().setNumberFormat(formatString);
		
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		this.tblPayList.getColumn(PL_APPDATE).setEditor(dateEditor);
		
		f7Box = new KDBizPromptBox();
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		
//		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("number", SHEManageHelper.RoomAttachMoneyDefineNum,CompareType.NOTEQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("number", SHEManageHelper.FittmentMoneyDefineNum,CompareType.NOTEQUALS));
//		
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.EARNESTMONEY_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.FISRTAMOUNT_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.HOUSEAMOUNT_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.LOANAMOUNT_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ACCFUNDAMOUNT_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ELSEAMOUNT_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.REPLACEFEE_VALUE));
//		
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.PRECONCERTMONEY_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.REFUNDMENT_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.FITMENTAMOUNT_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.LATEFEE_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.COMPENSATEAMOUNT_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.SINPUR_VALUE));
//		filter.setMaskString("#0 and #1 and #2 or (#3 or #4 or #5 or #6 or #7 or #8 or #9 or #10 or #11 or #12 or #13 or #14 or #15 or # 16)");
//		
//		view.setFilter(filter);
//		f7Box.setEntityViewInfo(view);
//		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.tblPayList.getColumn(PL_MONEYNAME).setEditor(SHEManageHelper.getMoneyTypeCellEditorForSHE());
		
		
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblPayList.getColumn(PL_DES).setEditor(txtEditor);
		
		this.tblPayList.getColumn(PL_MONEYNAME).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblPayList.getColumn(PL_APPAMOUNT).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblPayList.getColumn(PL_APPDATE).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		this.tblPayList.getColumn(PL_ACTAMOUNT).getStyleAttributes().setLocked(true);
		this.tblPayList.getColumn(PL_BALANCE).getStyleAttributes().setLocked(true);
		this.tblPayList.getColumn(PL_STATE).getStyleAttributes().setLocked(true);
		this.tblPayList.getColumn(PL_CURRENCY).getStyleAttributes().setLocked(true);
		
		this.tblPayList.getColumn(PL_ACTAMOUNT).getStyleAttributes().setHided(true);
		this.tblPayList.getColumn(PL_BALANCE).getStyleAttributes().setHided(true);
	}
	protected void loadReceiveBill() {
		this.tblReceiveBill.removeRows();
		if(editData!=null&&((BaseTransactionInfo)editData).getTransactionID()==null) return;
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("bizDate");
		sic.add("revBillType");
		sic.add("revAmount");
		sic.add("currency.name");
		sic.add("creator.name");
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("relateTransId",((BaseTransactionInfo)editData).getTransactionID()));
		filter.getFilterItems().add(new FilterItemInfo("isGathered", new Boolean(false)));
		view.setFilter(filter);
		view.setSelector(sic);
		try {
			SHERevBillCollection col = SHERevBillFactory.getRemoteInstance().getSHERevBillCollection(view);
			for(int i=0;i<col.size();i++){
				SHERevBillInfo entry = col.get(i);
				IRow row = this.tblReceiveBill.addRow();
				row.setUserObject(entry);
				row.getCell(RB_NUMBER).setValue(entry.getNumber());
				row.getCell(RB_REVDATE).setValue(entry.getBizDate());
				row.getCell(RB_BILLTYPE).setValue(entry.getRevBillType().getAlias());
				row.getCell(RB_REVPERSON).setValue(entry.getCreator().getName());
				if(entry.getCurrency()!=null){
					row.getCell(RB_CURRENCY).setValue(entry.getCurrency().getName());
				}
				row.getCell(RB_AMOUNT).setValue(entry.getRevAmount());
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(editData.getId()==null) return;
		SelectorItemCollection banksic = new SelectorItemCollection();
		banksic.add("payment.id");
		banksic.add("payment.number");
		banksic.add("payment.paymentDate");
		banksic.add("payment.paymentAmout");
		banksic.add("payment.creator.name");
		EntityViewInfo bankview=new EntityViewInfo();
		FilterInfo bankfilter = new FilterInfo();
		bankfilter.getFilterItems().add(new FilterItemInfo("signManager.id", editData.getId()));
		bankview.setFilter(bankfilter);
		bankview.setSelector(banksic);
		try {
			Set bank=new HashSet();
			BankPaymentEntryCollection bankcol = BankPaymentEntryFactory.getRemoteInstance().getBankPaymentEntryCollection(bankview);
			for(int i=0;i<bankcol.size();i++){
				BankPaymentEntryInfo entry = bankcol.get(i);
				if(bank.contains(entry.getPayment().getId())) continue;
				bank.add(entry.getPayment().getId());
				IRow row = this.tblReceiveBill.addRow();
				row.setUserObject(entry);
				row.getCell(RB_NUMBER).setValue(entry.getPayment().getNumber());
				row.getCell(RB_REVDATE).setValue(entry.getPayment().getPaymentDate());
				row.getCell(RB_BILLTYPE).setValue("收款");
				row.getCell(RB_REVPERSON).setValue(entry.getPayment().getCreator().getName());
				row.getCell(RB_CURRENCY).setValue("人民币");
				row.getCell(RB_AMOUNT).setValue(entry.getPayment().getPaymentAmout());
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化业务总揽
	 */
	protected void loadBizReview() {
		this.tblBizReview.removeRows();
		
		RoomInfo room=(RoomInfo)this.f7Room.getValue();
		if(room == null||((BaseTransactionInfo)editData).getTransactionID()==null){
			return;
		}
		
		TransactionInfo transactionInfo = null;
		try {
			transactionInfo = TransactionFactory.getRemoteInstance().getTransactionInfo(new ObjectUuidPK(((BaseTransactionInfo)editData).getTransactionID()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(transactionInfo != null){
			TranBusinessOverViewCollection col = transactionInfo.getTranBusinessOverView();
			if(col != null && col.size()> 0){
				CRMHelper.sortCollection(col, "finishDate", true);
				for(int i=0;i<col.size();i++){
					TranBusinessOverViewInfo info = col.get(i);
					IRow row = tblBizReview.addRow();
					row.getCell("id").setValue(info.getId());
					row.getCell("bizName").setValue(info.getBusinessName());
					row.getCell("finishDate").setValue(info.getFinishDate());
					BusTypeEnum type = info.getType();
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actRevAmount = FDCHelper.ZERO;
					if(info.getAppAmount() != null){
						appAmount = info.getAppAmount();
					}
					if(info.getActRevAmount() != null){
						actRevAmount = info.getActRevAmount();
					}
					if(BusTypeEnum.BUSINESS.equals(type)){
						if(info.isIsFinish()){
							row.getCell("isFinish").setValue(Boolean.TRUE);
						}else{
							row.getCell("isFinish").setValue(Boolean.FALSE);
						}
					}else if(BusTypeEnum.PAY.equals(type)){
						if(actRevAmount.subtract(appAmount).compareTo(FDCHelper.ZERO) == 0){
							row.getCell("isFinish").setValue(Boolean.TRUE);
						}else{
							row.getCell("isFinish").setValue(Boolean.FALSE);
						}
					}
					row.getCell("actualFinishDate").setValue(info.getActualFinishDate());
				}
			}
			
		}
	}

	/**
	 * 初始化收款单
	 */
	private void initTblReceiveBill() {
		this.tblReceiveBill.checkParsed();
		this.tblReceiveBill.getStyleAttributes().setLocked(true);

		this.tblReceiveBill.getColumn(RB_AMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblReceiveBill.getColumn(RB_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblReceiveBill.getColumn(RB_REVDATE).getStyleAttributes().setNumberFormat("yyyy-MM-dd");
	}

	/**
	 * 初始化附属财产
	 */
	private void initTblAttachProperty() {
		this.tblAttachProperty.checkParsed();
	
		//设置是否并入合同金额
		final KDCheckBox isMergeTocontractCheckBox = new KDCheckBox();
		isMergeTocontractCheckBox.setSelected(false);
		tblAttachProperty.getColumn(AP_ISMERGETOCONTRACT).setEditor(new KDTDefaultCellEditor(isMergeTocontractCheckBox));
		tblAttachProperty.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);	
		
		this.tblAttachProperty.getColumn(AP_BUILDINGAREA).setEditor(FDCClientHelper.getNumberCellEditor());
		this.tblAttachProperty.getColumn(AP_BUILDINGAREA).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblAttachProperty.getColumn(AP_BUILDINGAREA).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblAttachProperty.getColumn(AP_ROOMAREA).setEditor(FDCClientHelper.getNumberCellEditor());
		this.tblAttachProperty.getColumn(AP_ROOMAREA).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblAttachProperty.getColumn(AP_ROOMAREA).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblAttachProperty.getColumn(AP_AGIO).getStyleAttributes().setLocked(true);
		this.tblAttachProperty.getColumn(AP_AGIO).setRenderer(new ObjectValueRender(){
			public String getText(Object obj)
			{
				if(obj instanceof Object[]){
					
					return getAgioDes((Object[])obj);
				}
				return null;
			}
		});
		this.tblAttachProperty.getColumn(AP_BUILDINGAREA).getStyleAttributes().setLocked(true);
		this.tblAttachProperty.getColumn(AP_ROOMAREA).getStyleAttributes().setLocked(true);
		this.tblAttachProperty.getColumn(AP_STANDARDTOTALAMOUNT).getStyleAttributes().setLocked(true);
		this.tblAttachProperty.getColumn(AP_ROOMPRICE).getStyleAttributes().setLocked(true);
		this.tblAttachProperty.getColumn(AP_BUILDINGPRICE).getStyleAttributes().setLocked(true);
		this.tblAttachProperty.getColumn(AP_ROOMNUMBER).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
	}
	/**
	 * 初始化付款方案
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws BOSException 
	 */
	protected void setF7PayTypeFilter() throws EASBizException, BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("validDate", FDCDateHelper.addDays(FDCCommonServerHelper.getServerTime(), 1), CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", FDCCommonServerHelper.getServerTime(), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", null, CompareType.IS));
		
		filter.getFilterItems().add(new FilterItemInfo("project.id", SHEManageHelper.getAllParentSellProjectCollection(((BaseTransactionInfo)editData).getSellProject(),new HashSet()),CompareType.INCLUDE));
		filter.setMaskString("#0 and #1 and (#2 or #3) and #4 ");
		view.setFilter(filter);
		this.f7PayType.setEntityViewInfo(view);
	}
	protected void setF7AgioSchemeFilter() throws BOSException, EASBizException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("validDate", FDCDateHelper.addDays(new Date(), 1), CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", new Date(), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", null, CompareType.IS));

		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", SHEManageHelper.getAllParentSellProjectCollection(((BaseTransactionInfo)editData).getSellProject(),new HashSet()),CompareType.INCLUDE));
		filter.setMaskString("#0 and #1 and (#2 or #3) and #4 ");
		view.setFilter(filter);
		this.f7AgioScheme.setEntityViewInfo(view);
		
	}
	/**
	 *插入行，请实现createNewPayListData()；否则中断
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		if(this.f7Room.getValue()==null){
			FDCMsgBox.showWarning(this, "请先选择房间！");
			return;
		}
		insertLine(tblPayList);
	}
	
	/**
	 * 覆写父类方法
	 */
	protected void insertLine(KDTable table) {
		if (table == null) {
			return;
		}
		IRow row = null;
		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();
			if (isTableColumnSelected(table)) {
				row = table.addRow();
				row.setUserObject(createNewPayListData());
				row.getCell(PL_STATE).setValue(new Boolean(false));
				row.getCell(PL_CURRENCY).setValue(currency);
			} else {
				row = table.addRow(top);
				row.setUserObject(createNewPayListData());
				row.getCell(PL_STATE).setValue(new Boolean(false));
				row.getCell(PL_CURRENCY).setValue(currency);
			}
		} else {
			row = table.addRow();
			row.setUserObject(createNewPayListData());
			row.getCell(PL_STATE).setValue(new Boolean(false));
			row.getCell(PL_CURRENCY).setValue(currency);
		}
	}
	protected void removeLine(KDTable table) {
		if (table == null) {
			return;
		}
		if ((table.getSelectManager().size() == 0)){
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
			return;
		}
		if (confirmRemove()) {
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null){
				return;
			}
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				table.removeRow(rowIndex);
			}
			if (table.getRow(0) != null){
				table.getSelectManager().select(0, 0);
			}
		}
	}
	/**
	 * 删除行
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		if (this.btnRemoveAPLine.equals(e.getSource())) {
			removeLine(tblAttachProperty);
			afterRemoveLineFocusPolicy(tblAttachProperty);
			
			this.updateAmount();
		} else if (this.btnRemovePayLine.equals(e.getSource())) {
			removeLine(tblPayList);
			afterRemoveLineFocusPolicy(tblPayList);
		}
	}

	/**
	 * 删除行后的焦点策略
	 * 
	 * @return
	 */
	private void afterRemoveLineFocusPolicy(KDTable table) {
		if (table.getRowCount() == 0) {
			FocusTraversalPolicy policy = null;
			Container container = null;
			Component initComponent = null;
			if (this.getFocusTraversalPolicy() != null
					&& this.getFocusTraversalPolicy() instanceof UIFocusTraversalPolicy) {
				policy = this.getFocusTraversalPolicy();
				container = this;
				Component[] traverComponent = ((UIFocusTraversalPolicy) policy).getComponents();
				for (int i = 0; i < traverComponent.length; i++) {
					if (traverComponent[i] == table) {
						initComponent = traverComponent[i];
						break;
					}
				}
				if (initComponent == null) {
					initComponent = policy.getLastComponent(container);
					initComponent.requestFocusInWindow();
				} else if (initComponent != null) {
					Component component = policy.getComponentBefore(container, initComponent);
					while ((component instanceof IKDTextComponent) == false || component.isEnabled() == false) {
						component = policy.getComponentBefore(container, component);
					}
					component.requestFocusInWindow();
				}
			} else if (policy == null) {
				if (this.getUIWindow() instanceof Dialog) {
					policy = ((Dialog) uiWindow).getFocusTraversalPolicy();
					container = (Dialog) uiWindow;
				} else if (this.getUIWindow() instanceof Window) {
					policy = ((Window) uiWindow).getFocusTraversalPolicy();
					container = (Window) uiWindow;
				}

				if (policy != null) {
					try {
						Component component = policy.getComponentBefore(container, table);
						while ((component instanceof IKDTextComponent) == false || component.isEnabled() == false) {
							component = policy.getComponentBefore(container, component);
						}
						component.requestFocusInWindow();
					} catch (Exception ex) {
					}
				}
			}
		}
	}

	/**
	 *新增行,请实现createNewPayListData() and createNewApData()；否则中断
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		if(this.f7Room.getValue()==null){
			FDCMsgBox.showWarning(this, "请先选择房间！");
			return;
		}
		if (this.btnAddAPLine.equals(e.getSource())) {
			IRow row = tblAttachProperty.addRow();
			row.setUserObject(createNewApData());
			row.getCell(AP_ISMERGETOCONTRACT).setValue(new Boolean(false));
		} else if (this.btnAddPayline.equals(e.getSource())) {
			IRow row = tblPayList.addRow();
			row.setUserObject(createNewPayListData());
			row.getCell(PL_STATE).setValue(new Boolean(false));
			row.getCell(PL_CURRENCY).setValue(currency);
		}
	}

	/**
    * 
    */
	protected IObjectValue createNewDetailData(KDTable table) {
		if (tblAttachProperty.equals(table)) {
			return createNewApData();
		} else if (tblPayList.equals(table)) {
			return createNewPayListData();
		}
		return null;
	}

	/**
	 * 新增付款明细行时，需要New一个付款明细值对象,否则新增出中断
	 * 
	 * @return
	 */
	protected abstract IObjectValue createNewPayListData();

	/**
	 * 新增附属房产行时，需要New一个附属房产值对象,否则新增出中断
	 * 
	 * @return
	 */
	protected abstract IObjectValue createNewApData();
	
	
	
	
	protected IObjectValue createNewData() {
		return super.createNewData();
	}
	protected void tblPayList_editStopped(KDTEditEvent e) throws Exception {
		boolean isEarnestInHouseAmount = ((BaseTransactionInfo)this.editData).isIsEarnestInHouseAmount();
		BigDecimal contractAmount = this.txtContractTotalAmount.getBigDecimalValue();
		
		BigDecimal apAmount = this.tblPayList.getRow(e.getRowIndex()).getCell("appAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)this.tblPayList.getRow(e.getRowIndex()).getCell("appAmount").getValue();			
		BigDecimal actAmount = this.tblPayList.getRow(e.getRowIndex()).getCell("actAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)this.tblPayList.getRow(e.getRowIndex()).getCell("actAmount").getValue();			
		if (actAmount.compareTo(FDCHelper.ZERO) != 0) {
			if (actAmount.compareTo(apAmount) >= 0) {
				this.tblPayList.getRow(e.getRowIndex()).getCell("state").setValue(new Boolean(true));
			}else{
				this.tblPayList.getRow(e.getRowIndex()).getCell("state").setValue(new Boolean(false));
			}
		}else{
			this.tblPayList.getRow(e.getRowIndex()).getCell("state").setValue(new Boolean(false));
		}
		this.tblPayList.getRow(e.getRowIndex()).getCell("balance").setValue(apAmount.subtract(actAmount).compareTo(FDCHelper.ZERO)<0?FDCHelper.ZERO:apAmount.subtract(actAmount));
		
		if(!SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("ppl")){
			if (contractAmount != null && e.getRowIndex() >= 0) {
				contractAmount = contractAmount.setScale(digit, toIntegerType);
				IRow nextRow = this.tblPayList.getRow(e.getRowIndex() + 1);
				if (nextRow != null) {
					BigDecimal nextAmount = contractAmount;
					for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
						if (i != (e.getRowIndex() + 1)) {
							IRow perRow = this.tblPayList.getRow(i);
							BigDecimal purAmount = (BigDecimal) perRow.getCell(PL_APPAMOUNT).getValue();
							if (purAmount == null)
								purAmount = FDCHelper.ZERO;
							
							MoneyDefineInfo moneyDefine = (MoneyDefineInfo) perRow.getCell(PL_MONEYNAME).getValue();
							
							if(moneyDefine != null) {
								if(!SHEManageHelper.isMergerToContractMoneyType(moneyDefine.getMoneyType(),isEarnestInHouseAmount)){
									continue;
								}
							}
							nextAmount = nextAmount.subtract(purAmount);
						}
					}
					MoneyDefineInfo nextMoneyDefine= (MoneyDefineInfo) nextRow.getCell(PL_MONEYNAME).getValue();
					
					if(nextMoneyDefine != null) {
						if(SHEManageHelper.isMergerToContractMoneyType(nextMoneyDefine.getMoneyType(),isEarnestInHouseAmount)){
							nextRow.getCell(PL_APPAMOUNT).setValue(nextAmount);
							
							BigDecimal netactAmount = nextRow.getCell("actAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)nextRow.getCell("actAmount").getValue();			
							if (netactAmount.compareTo(FDCHelper.ZERO) != 0) {
								if (netactAmount.compareTo(nextAmount) >= 0) {
									nextRow.getCell("state").setValue(new Boolean(true));
								}else{
									nextRow.getCell("state").setValue(new Boolean(false));
								}
							}else{
								nextRow.getCell("state").setValue(new Boolean(false));
							}
							nextRow.getCell("balance").setValue(nextAmount.subtract(netactAmount).compareTo(FDCHelper.ZERO)<0?FDCHelper.ZERO:nextAmount.subtract(netactAmount));
						}
					}
				}
			}
		}
		this.updateLoanAndAFAmount();
	}
	protected void updateLoanAndAFAmount() {
		BigDecimal loanAmount = FDCHelper.ZERO;
		BigDecimal afAmount = FDCHelper.ZERO;

		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			if (!(row.getCell(PL_MONEYNAME).getValue() instanceof MoneyDefineInfo)) {
				continue;
			}
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(PL_MONEYNAME).getValue();
			BigDecimal amount = (BigDecimal) row.getCell(PL_APPAMOUNT).getValue();
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
	protected void tblReceiveBill_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = tblReceiveBill.getRow(e.getRowIndex());
			if (row.getUserObject() == null) {
				return;
			}else if(row.getUserObject() instanceof SHERevBillInfo){
				CRMClientHelper.openTheGatherRevBillWindow(this,((SHERevBillInfo)row.getUserObject()).getId().toString(), null,null ,null,null);
			
				this.loadReceiveBill();
				this.updateActRevAmount((BaseTransactionInfo)editData);
			}else if(row.getUserObject() instanceof BankPaymentInfo){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", ((BankPaymentInfo)row.getUserObject()).getId().toString());
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BankPaymentEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}

	}
	public void actionChooseAgio_actionPerformed(ActionEvent e) throws Exception {
		if (this.f7Room.getValue() == null) {
			FDCMsgBox.showWarning(this,"请先选择房间！");
			return;
		}
		RoomInfo room = (RoomInfo) this.f7Room.getValue();
		AgioParam agioParam = AgioSelectUI.showUI(this, room.getId().toString(), 
				(AgioEntryCollection)this.txtAgio.getUserObject(), this.currAgioParam ,this.editData,isAgioListCanEdit,false);
		if (agioParam != null&&!this.currAgioParam.equals(agioParam)) {
			this.currAgioParam = agioParam;
			this.txtAgio.setUserObject(agioParam.getAgios());
			this.updateAmount();
		}
	}
	protected String getAgioDes(Object[] values) {
		if(values==null) return null;
		AgioEntryCollection agioEntrys = new AgioEntryCollection();
		for (int i = 0; i < values.length; i++) {
			AgioEntryInfo entryInfo = (AgioEntryInfo)values[i];
			agioEntrys.add(entryInfo);
		}
		return SHEHelper.getAgioDes(agioEntrys,null,null,null,false,false,
				SHEHelper.DEFAULT_TO_INTEGER_TYPE, SHEHelper.DEFAULT_DIGIT);
	
	}
	public void customerSelect(Component owner,List customer,SellProjectInfo sellProject,boolean isBaseTranAdd) throws EASBizException, BOSException{
		boolean isCanEditMainCustomer=true;
		if(((BaseTransactionInfo)editData).getTransactionID()!=null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("relateTransId",((BaseTransactionInfo)editData).getTransactionID()));
			if(SHERevBillFactory.getRemoteInstance().exists(filter)){
				isCanEditMainCustomer=false;
			}
		}
		this.customer =SHEManageHelper.customerSelect(owner,customer,sellProject,isBaseTranAdd,isCanEditMainCustomer);
		Set customerId=new HashSet();
		for(int i=0;i<this.customer.size();i++){
			TranCustomerEntryInfo tranInfo=(TranCustomerEntryInfo)this.customer.get(i);
			customerId.add(tranInfo.getCustomer().getId().toString());
		}
		if(customerId.size()>0){
			EntityViewInfo entity=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("customer.id",customerId,CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("head.bizState",TransactionStateEnum.BAYNUMBER_VALUE));
			entity.setFilter(filter);
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("head.id");
			entity.setSelector(sel);
			SincerityPurchaseCustomerEntryCollection col=SincerityPurchaseCustomerEntryFactory.getRemoteInstance().getSincerityPurchaseCustomerEntryCollection(entity);
			boolean isExists=true;
			if(col.size()>0){
				if(((BaseTransactionInfo)editData).getSrcId()!=null&&this.srcInfo==null){
					for(int i=0;i<col.size();i++){
						if(col.get(i).getHead().getId().equals(((BaseTransactionInfo)editData).getSrcId())){
							isExists=false;
						}
					}
				}
				if(isExists){
					FDCMsgBox.showWarning(this,"客户存在预约排号单据！");
				}
			}
		}
	}
	protected void labelCustomer1_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labelCustomer1.getUserObject());
		}
	}
	protected void labelCustomer2_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)labelCustomer2.getUserObject());
		}
	}
	protected void labelCustomer3_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)labelCustomer3.getUserObject());
		}
	}
	
	protected void labelCustomer4_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)labelCustomer4.getUserObject());
		}
	}
	protected void labelCustomer5_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)labelCustomer5.getUserObject());
		}
	}
	public void actionAddCustomer_actionPerformed(ActionEvent e) throws Exception {
		SellProjectInfo sellProjectInfo = SHEManageHelper.getParentSellProject(null,((BaseTransactionInfo)editData).getSellProject());
		Map linkedCus = CusClientHelper.addNewCusBegin(this, sellProjectInfo.getId().toString());
		UIContext uiContext = new UIContext(this);
		if (linkedCus != null) {
			uiContext.putAll(linkedCus);
			uiContext.put("sellProject", sellProjectInfo);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerRptEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		}
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		auditCheck();
		if (!getBizStateSubmitEnum().equals(((BaseTransactionInfo)editData).getBizState())) {
			FDCMsgBox.showWarning("该单据不是提交状态，不能进行审批操作！");
			return;
		}
		if (isModify()) {
			FDCMsgBox.showWarning("单据已被修改，请先提交。");
			this.abort();
		}
		
		String id = getSelectBOID();
		FDCClientUtils.checkBillInWorkflow(this, id);
		if (id != null) {
			((IBaseTransaction) getBizInterface()).audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		
		setAuditButtonStatus(STATUS_VIEW);
		this.actionSave.setEnabled(false);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		unAuditCheck();
		if (!getBizStateAuditEnum().equals(((BaseTransactionInfo)editData).getBizState())) {
			FDCMsgBox.showWarning("该单据不是审批状态，不能进行反审批操作！");
			return;
		}
		String id = getSelectBOID();
		
		FilterInfo filter  = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("newId",id));
		if(ChangeManageFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning("该单据由变更单产生，不能进行反审批操作！");
			return;
		}
		FDCClientUtils.checkBillInWorkflow(this, id);
		if (id != null) {
			((IBaseTransaction) getBizInterface()).unAudit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setAuditButtonStatus(this.getOprtState());
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		}
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		setSaveAction(false);
		if(!checkCanSubmit()){
			MsgBox.showWarning(this,"单据状态已经在审批中或者已审批，不能再提交！");
			SysUtil.abort();
		}
		((FDCBillInfo)editData).setState(FDCBillStateEnum.SUBMITTED);
		
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	
	public void actionSubmitAudit_actionPerformed(ActionEvent e) throws Exception {
		actionSubmit_actionPerformed(e);
		actionAudit_actionPerformed(e);
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		((FDCBillInfo)editData).setState(FDCBillStateEnum.SAVED);
		super.actionSave_actionPerformed(e);
	}
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = getSelectBOID();
        if(boID == null)
        {
            return;
        } 
        acm.showAttachmentListUIByBoID(boID,this,true);
	}
	protected void verfiyOther(){
		
	}
	abstract protected boolean veriftExistsSameRoomBill(RoomInfo room) throws EASBizException, BOSException;
	
	protected void verifyInputForSubmint() throws Exception {
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.f7Room);
		FDCClientVerifyHelper.verifyEmpty(this, this.comboSellType);
		if(this.chkIsFitmentToContract.isSelected()){
			this.txtFitmentAmount.setRequired(true);
			FDCClientVerifyHelper.verifyEmptyAndNoZero(this, this.txtFitmentAmount);
		}else{
			this.txtFitmentAmount.setRequired(false);
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizAdscriptionDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.comboValuationType);
		FDCClientVerifyHelper.verifyEmpty(this, this.f7Seller);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtDealTotalAmount);
		verfiyOther();
		verifyCustomer();
		verifyAttachPropertyTab();
		verifyPayListTab();
		
		if(veriftExistsSameRoomBill((RoomInfo)this.f7Room.getValue())){
			FDCMsgBox.showWarning(this,"已存在该房间业务单据！");
			SysUtil.abort();
		}
		this.verifyAddNewRoom((RoomInfo)this.f7Room.getValue(), ((BaseTransactionInfo)editData).isIsBasePriceSell());
	
		for(int i=0;i<this.tblAttachProperty.getRowCount();i++){
			IRow row = this.tblAttachProperty.getRow(i);
			RoomInfo room = (RoomInfo)row.getCell(AP_ROOMNUMBER).getValue();
			this.verifyAddNewAttachRoom(room, row);
		}
		
		SHEManageHelper.addCommerceChance(this,this.customer,this.saleMan,(BaseTransactionInfo)editData);
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.f7Seller);
	}
	protected void verifyCustomer(){
		if(this.customer.size()==0){
			FDCMsgBox.showWarning(this,"客户不能为空！");
			SysUtil.abort();
		}
	}
    protected void verifyAttachPropertyTab() throws BOSException {
    	for (int i = 0; i < this.tblAttachProperty.getRowCount(); i++) {
    		IRow row = this.tblAttachProperty.getRow(i);
    		if (row.getCell(AP_ROOMNUMBER).getValue()==null) 
    		{
    			FDCMsgBox.showWarning(this,"附属房产信息房间不能为空！");
				this.tblAttachProperty.getEditManager().editCellAt(row.getRowIndex(), this.tblPayList.getColumnIndex(PL_MONEYNAME));
				SysUtil.abort();
    		}
    		Boolean isOk = new Boolean(row.getCell(AP_ISMERGETOCONTRACT).getValue().toString()); 
    		if(isOk.booleanValue()){
    			BigDecimal amount=(BigDecimal)row.getCell(AP_MERGEAMOUNT).getValue();
				if (amount==null){
					FDCMsgBox.showWarning(this,"附属房产信息并入金额不能为空！");
					this.tblAttachProperty.getEditManager().editCellAt(row.getRowIndex(), this.tblPayList.getColumnIndex(AP_MERGEAMOUNT));
					SysUtil.abort();
				}
				if (amount.compareTo(FDCHelper.ZERO)==0){
					FDCMsgBox.showWarning(this,"附属房产信息并入金额不能为0！");
					this.tblAttachProperty.getEditManager().editCellAt(row.getRowIndex(), this.tblPayList.getColumnIndex(AP_MERGEAMOUNT));
					SysUtil.abort();
				}
    		}
    	}
    }
    
    protected void verifyPayListTab() throws BOSException {
		if(this.tblPayList.getRowCount()==0){
			FDCMsgBox.showWarning(this,"客户付款明细款不能为空！");
			SysUtil.abort();
		}
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
				
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(PL_MONEYNAME).getValue();
			if (moneyName == null) {
				FDCMsgBox.showWarning(this,"客户付款明细款项名称不能为空！");
				this.tblPayList.getEditManager().editCellAt(row.getRowIndex(), this.tblPayList.getColumnIndex(PL_MONEYNAME));
				SysUtil.abort();
			} 
			BigDecimal amount = (BigDecimal)row.getCell(PL_APPAMOUNT).getValue();
			if (amount == null){
				FDCMsgBox.showWarning(this,"客户付款明细应收金额不能为空！");
				this.tblPayList.getEditManager().editCellAt(row.getRowIndex(), this.tblPayList.getColumnIndex(PL_APPAMOUNT));
				SysUtil.abort();
			}
//			if (amount.compareTo(FDCHelper.ZERO)==0){
//				FDCMsgBox.showWarning(this,"客户付款明细应收金额不能为0！");
//				this.tblPayList.getEditManager().editCellAt(row.getRowIndex(), this.tblPayList.getColumnIndex(PL_APPAMOUNT));
//				SysUtil.abort();
//			}
			Date date = (Date) row.getCell(PL_APPDATE).getValue();
			if (date == null) {
				FDCMsgBox.showWarning(this,"客户付款明细应收日期不能为空！");
				this.tblPayList.getEditManager().editCellAt(row.getRowIndex(), this.tblPayList.getColumnIndex(PL_APPDATE));
				SysUtil.abort();
			}
		}
	}
    protected void setRoomNull(String warning){
    	MsgBox.showWarning(this,warning);
		this.f7Room.setValue(null);
		SysUtil.abort();
    }
    protected void setSellTypeNull(String warning){
    	MsgBox.showWarning(this,warning);
		this.comboSellType.setSelectedItem(null);
		SysUtil.abort();
    }
    protected void verifyAddNewRoom(RoomInfo room,boolean isIsBasePriceSell) throws BOSException, EASBizException {
    	if(room==null) return;
    	
    	if (RoomSellStateEnum.Init.equals(room.getSellState())) {
			setRoomNull("房间没有开盘！");
		}
    	if (RoomSellStateEnum.KeepDown.equals(room.getSellState())) {
    		setRoomNull("房间已经被预留！");
		}
    	if (room.isIsMortagaged()) {
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
    		filter.getFilterItems().add(new FilterItemInfo("isSell", new Boolean(false)));
    		filter.getFilterItems().add(new FilterItemInfo("mortagageState", MortagageEnum.AUDITTED_VALUE));
    		
    		if(MortagageControlFactory.getRemoteInstance().exists(filter)){
    			setRoomNull("房间已被抵押，需解除抵押后才可销售！");
    		}
		}
    	if (room.getStandardTotalAmount() == null ) {
			setRoomNull("房间尚未定价，请选择其它房间操作！");
		}
    	if (HousePropertyEnum.Attachment.equals(room.getHouseProperty())) {
    		setRoomNull("附属房产不能单独销售！");
		}
    	if(isQuitRoomAudit&&ChangeStateEnum.QuitRoom.equals(room.getChangeState())){
    		setRoomNull("退房后房间必须进行价格审核！");
    	}
    	if(isChangeRoomAuidt&&ChangeStateEnum.ChangeRoom.equals(room.getChangeState())){
    		setRoomNull("换房后房间必须进行价格审核！");
    	}
		if(isChangeAreaAuidt&&(RoomPreChangeStateEnum.CHANGE.equals(room.getPreChangeState())||
				RoomPreChangeStateEnum.CHANGE.equals(room.getPlanChangeState())||RoomPreChangeStateEnum.CHANGE.equals(room.getActChangeState()))){
			setRoomNull("面积变更后房间必须进行价格审核！");
		}
    	if(isIsBasePriceSell){	
    		if(room.getBaseStandardPrice()==null) {
    			setRoomNull("已启用强制底价控制参数，该房间总价底价不存在，请检查！");
    		}
    	}
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("startDate", FDCDateHelper.getSQLEnd(FDCCommonServerHelper.getServerTime()),CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("endDate", FDCDateHelper.getSQLBegin(FDCCommonServerHelper.getServerTime()),CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("building.id", room.getBuilding().getId().toString()));
		if(SHEFunctionChooseRoomSetFactory.getRemoteInstance().exists(filter)){
			setRoomNull("已启用选房设置参数！");
		}
	}
    public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		
//		SaleManPromptDialog comBox;
//		try {
//			if(editData==null){
//				comBox = new SaleManPromptDialog(this,saleMan,oprtType,null,null);
//			}else{
//				comBox = new SaleManPromptDialog(this,saleMan,oprtType,editData.getId(),((BaseTransactionInfo)editData).getSellProject());
//			}
//			this.f7Seller.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//			this.f7Seller.setEditable(false);
//			this.f7Seller.getQueryAgent().setEnabledMultiSelection(true);
//			this.f7Seller.setSelector(comBox);
//		} catch (UIException e) {
//			e.printStackTrace();
//		}
    	
		setAfterSubmitEnable(oprtType);
		SHEFunction();
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			this.actionReceiveBill.setEnabled(true);
		}else{
			this.actionReceiveBill.setEnabled(false);
		}
		
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
	}
	private void SHEFunction(){
		if((STATUS_EDIT.equals(this.getOprtState())||STATUS_ADDNEW.equals(this.getOprtState()))){
			this.txtDealTotalAmount.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.pkBizAdscriptionDate.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.f7AgioScheme.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			if(isDealAmountEdit){
				this.txtDealTotalAmount.setEnabled(true);
			}else{
				this.txtDealTotalAmount.setEnabled(false);
			}
//			if(isBizDateEdit){
//				this.pkBizAdscriptionDate.setEnabled(true);
//			}else{
				this.pkBizAdscriptionDate.setEnabled(false);
//			}
			if(isUseAgioScheme){
				this.f7AgioScheme.setEnabled(true);
			}else{
				this.f7AgioScheme.setEnabled(false);
			}
		}
	}
    protected void setAfterSubmitEnable(String oprtType){
    	this.f7Room.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.comboSellType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.tblAttachProperty.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
    	if(STATUS_ADDNEW.equals(oprtType)){
			this.f7Room.setEnabled(true);
    		this.comboSellType.setEnabled(true);
    		this.comboValuationType.setEnabled(true);
    	}else{
    		if(editData!=null&&!FDCBillStateEnum.SAVED.equals(((FDCBillInfo)editData).getState())){
    			this.f7Room.setEnabled(false);
//    			this.comboSellType.setEnabled(false);
        		this.comboValuationType.setEnabled(false);
    		}
    	}
    }
    protected BigDecimal getMergeContractAttachmentTotalAmount(boolean isMerger){
		BigDecimal attachmentTotalAmount=FDCHelper.ZERO;
		for(int i=0;i<tblAttachProperty.getRowCount();i++){
			IRow row=tblAttachProperty.getRow(i);
			Boolean isOk = new Boolean(row.getCell(AP_ISMERGETOCONTRACT).getValue().toString()); 
			BigDecimal amount=row.getCell(AP_MERGEAMOUNT).getValue()==null?FDCHelper.ZERO:(BigDecimal)row.getCell(AP_MERGEAMOUNT).getValue();
			if(isMerger){
				if(isOk.booleanValue()){
					attachmentTotalAmount=attachmentTotalAmount.add(amount);
				}
			}else{
				if(!isOk.booleanValue()){
					attachmentTotalAmount=attachmentTotalAmount.add(amount);
				}
			}
		}
		return attachmentTotalAmount;
	}
    protected void updateActRevAmount(BaseTransactionInfo info){
    	for(int i=0;i<this.tblPayList.getRowCount();i++){
    		IRow row = this.tblPayList.getRow(i);
    		if(row.getUserObject()!=null&&((TranPayListEntryInfo)row.getUserObject()).getTanPayListEntryId()!=null){
    			
    			BigDecimal actAmount=SHEManageHelper.getActRevAmount(null,((TranPayListEntryInfo)row.getUserObject()).getTanPayListEntryId());
    			BigDecimal apAmount = ((TranPayListEntryInfo)row.getUserObject()).getAppAmount()==null?FDCHelper.ZERO:((TranPayListEntryInfo)row.getUserObject()).getAppAmount();
    			
    			row.getCell(PL_ACTAMOUNT).setValue(actAmount);
    			
    			if (actAmount.compareTo(FDCHelper.ZERO) != 0) {
    				if (actAmount.compareTo(apAmount) >= 0) {
    					row.getCell(PL_STATE).setValue(new Boolean(true));
    				}else{
    					row.getCell(PL_STATE).setValue(new Boolean(false));
    				}
    			}else{
    				row.getCell(PL_STATE).setValue(new Boolean(false));
    			}
    			row.getCell(PL_BALANCE).setValue(apAmount.subtract(actAmount).compareTo(FDCHelper.ZERO)<0?FDCHelper.ZERO:apAmount.subtract(actAmount));
    		}
    	}
    }
	protected void updatePayList(){
		BigDecimal contractTotalAmount=this.txtContractTotalAmount.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtContractTotalAmount.getBigDecimalValue();
		SHEManageHelper.updatePayList(contractTotalAmount, this.tblPayList, ((BaseTransactionInfo)this.editData).isIsEarnestInHouseAmount());
	}
    /**
     * 更新总价，单价
     */
    protected void updateAmount(){
    	RoomInfo room=(RoomInfo)this.f7Room.getValue();
		if(room==null) return;
//    	CalcTypeEnum valuationType=(CalcTypeEnum)this.comboValuationType.getSelectedItem();
//    	if(CalcTypeEnum.PriceByBuildingArea.equals(valuationType)){
//    		this.txtStandardTotalAmount.setValue(this.txtBuildingArea.getBigDecimalValue().multiply(this.txtBuildingPrice.getBigDecimalValue()));
//		}
//		if(CalcTypeEnum.PriceByRoomArea.equals(valuationType)){
//			this.txtStandardTotalAmount.setValue(this.txtRoomArea.getBigDecimalValue().multiply(this.txtRoomPrice.getBigDecimalValue()));
//		}
		
		BigDecimal standardTotalAmount=this.txtStandardTotalAmount.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtStandardTotalAmount.getBigDecimalValue();
		BigDecimal mergeAttachmentTotalAmount=this.getMergeContractAttachmentTotalAmount(true);
		BigDecimal totalattachmentTotalAmount=FDCHelper.ZERO;
		for(int i=0;i<tblAttachProperty.getRowCount();i++){
			IRow row=tblAttachProperty.getRow(i);
			if(row.getCell(AP_STANDARDTOTALAMOUNT).getValue()!=null){
				totalattachmentTotalAmount=totalattachmentTotalAmount.add((BigDecimal)row.getCell(AP_STANDARDTOTALAMOUNT).getValue());
			}
		}
		this.txtAttachPropertyTotalAmount.setValue(totalattachmentTotalAmount);
		this.txtDealTotalAmount.setValue(standardTotalAmount);
		
		this.txtDealBuildPrice.setValue(this.txtBuildingArea.getBigDecimalValue()==null||this.txtBuildingArea.getBigDecimalValue().compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:this.txtDealTotalAmount.getBigDecimalValue().divide(this.txtBuildingArea.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP));
		this.txtDealRoomPrice.setValue(this.txtRoomArea.getBigDecimalValue()==null||this.txtRoomArea.getBigDecimalValue().compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:this.txtDealTotalAmount.getBigDecimalValue().divide(this.txtRoomArea.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP));
		
		BigDecimal fitmentAmount=this.txtFitmentAmount.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtFitmentAmount.getBigDecimalValue();
		this.txtFitmentPrice.setValue(this.txtBuildingArea.getBigDecimalValue()==null||this.txtBuildingArea.getBigDecimalValue().compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:fitmentAmount.divide(this.txtBuildingArea.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP));
		
		if(this.chkIsFitmentToContract.isSelected()){
			this.txtContractTotalAmount.setValue(this.txtDealTotalAmount.getBigDecimalValue().add(fitmentAmount).add(mergeAttachmentTotalAmount));
		} else {
			this.txtContractTotalAmount.setValue(this.txtDealTotalAmount.getBigDecimalValue().add(mergeAttachmentTotalAmount));
		}
		
		this.txtContractBuildPrice.setValue(this.txtBuildingArea.getBigDecimalValue()==null||this.txtBuildingArea.getBigDecimalValue().compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:this.txtContractTotalAmount.getBigDecimalValue().divide(this.txtBuildingArea.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP));
		this.txtContractRoomPrice.setValue(this.txtRoomArea.getBigDecimalValue()==null||this.txtRoomArea.getBigDecimalValue().compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:this.txtContractTotalAmount.getBigDecimalValue().divide(this.txtRoomArea.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP));
    }
    /**
     * 选择折扣后更新
     */
    protected void updataRoomContractAndDealAmount(){
    	
    }
    /**
     * 根据销售方式判断房间面积是否复核
     */
    protected void checkSellType(RoomInfo room){
    	if(room==null||this.comboSellType.getSelectedItem()==null
    			||this.comboValuationType.getSelectedItem()==null) return;
    	
    	SellTypeEnum sellType=null;
    	
		sellType=(SellTypeEnum)this.comboSellType.getSelectedItem();
    	CalcTypeEnum valuationType=(CalcTypeEnum)this.comboValuationType.getSelectedItem();
    	if(CalcTypeEnum.PriceByBuildingArea.equals(valuationType)||CalcTypeEnum.PriceByTotalAmount.equals(valuationType)){
    		if(SellTypeEnum.PlanningSell.equals(sellType)){
        		if(!room.isIsPlan()){
        			setSellTypeNull("房间没有预估复核！");
        		}
        		if(room.getPlanBuildingArea()==null||room.getPlanBuildingArea().compareTo(FDCHelper.ZERO)<0){
        			setSellTypeNull("房间预估建筑面积必须大于0！");
        		}
        	}
        	if(SellTypeEnum.PreSell.equals(sellType)){
        		if(!room.isIsPre()){
        			setSellTypeNull("房间没有预售复核！");
        		}
        		if(room.getBuildingArea()==null||room.getBuildingArea().compareTo(FDCHelper.ZERO)<0){
        			setSellTypeNull("房间预售建筑面积必须大于0！");
        		}
        	}
        	if(SellTypeEnum.LocaleSell.equals(sellType)){
        		if(!room.isIsActualAreaAudited()){
        			setSellTypeNull("房间没有实测复核！");
        		}
        		if(room.getActualBuildingArea()==null||room.getActualBuildingArea().compareTo(FDCHelper.ZERO)<0){
        			setSellTypeNull("房间现售建筑面积必须大于0！");
        		}
        	}
		}
		if(CalcTypeEnum.PriceByRoomArea.equals(valuationType)){
			if(SellTypeEnum.PlanningSell.equals(sellType)){
        		if(!room.isIsPlan()){
        			setSellTypeNull("房间没有预估复核！");
        		}
        		if(room.getPlanRoomArea()==null||room.getPlanRoomArea().compareTo(FDCHelper.ZERO)<0){
        			setSellTypeNull("房间预估套内面积必须大于0！");
        		}
        	}
        	if(SellTypeEnum.PreSell.equals(sellType)){
        		if(!room.isIsPre()){
        			setSellTypeNull("房间没有预售复核！");
        		}
        		if(room.getRoomArea()==null||room.getRoomArea().compareTo(FDCHelper.ZERO)<0){
        			setSellTypeNull("房间预售套内面积必须大于0！");
        		}
        	}
        	if(SellTypeEnum.LocaleSell.equals(sellType)){
        		if(!room.isIsActualAreaAudited()){
        			setSellTypeNull("房间没有实测复核！");
        		}
        		if(room.getActualRoomArea()==null||room.getActualRoomArea().compareTo(FDCHelper.ZERO)<0){
        			setSellTypeNull("房间现售套内面积必须大于0！");
        		}
        	}
		}
    }
    protected void updateAttachRoomInfo(RoomInfo room,IRow row){
    	BigDecimal buildingArea = FDCHelper.ZERO;
		BigDecimal roomArea = FDCHelper.ZERO;
		if(SellTypeEnum.LocaleSell.equals(room.getSellType())){	
			buildingArea = room.getActualBuildingArea();
			roomArea = room.getActualRoomArea();
		}else if(SellTypeEnum.PreSell.equals(room.getSellType())){	
			buildingArea = room.getBuildingArea();
			roomArea = room.getRoomArea();
		}else {
			buildingArea = room.getPlanBuildingArea();
			roomArea = room.getPlanRoomArea();
		}
		row.getCell(AP_BUILDINGAREA).setValue(buildingArea);
		row.getCell(AP_ROOMAREA).setValue(roomArea);
		row.getCell(AP_BUILDINGPRICE).setValue(room.getBuildPrice());
		row.getCell(AP_ROOMPRICE).setValue(room.getRoomPrice());
		row.getCell(AP_STANDARDTOTALAMOUNT).setValue(room.getStandardTotalAmount());
		
		row.getCell(AP_AGIO).getStyleAttributes().setLocked(false);
    }
    protected void updateRoomArea(){
		SellTypeEnum sellType=(SellTypeEnum)this.comboSellType.getSelectedItem();
		RoomInfo room=(RoomInfo)this.f7Room.getValue();
		if(room==null) return;
		SHEManageHelper.setAreaBySellType((BaseTransactionInfo) editData, room, sellType, txtRoomArea, txtBuildingArea);
    }
	protected void updateRoomInfo(){
		if(this.f7Room.getValue()!=null){
			RoomInfo room=(RoomInfo)this.f7Room.getValue();
			if(room.getRoomModel()!=null)this.txtRoomModel.setText(room.getRoomModel().getName());
			
//			if(this.srcInfo!=null&&(srcInfo instanceof PrePurchaseManageInfo ||srcInfo instanceof PurchaseManageInfo)){
//				this.txtBuildingPrice.setValue(srcInfo.getStrdBuildingPrice());
//				this.txtRoomPrice.setValue(srcInfo.getStrdRoomPrice());
//				this.txtStandardTotalAmount.setValue(srcInfo.getStrdTotalAmount());
//			}else{
				this.txtBuildingPrice.setValue(room.getBuildPrice()==null?FDCHelper.ZERO:room.getBuildPrice());
				this.txtRoomPrice.setValue(room.getRoomPrice()==null?FDCHelper.ZERO:room.getRoomPrice());
				BigDecimal standardTotalAmount = room.getStandardTotalAmount()==null?FDCHelper.ZERO:room.getStandardTotalAmount();
				this.txtStandardTotalAmount.setValue(standardTotalAmount);
//			}
			
			if(this.txtAgio.getUserObject()==null){
				this.txtAgio.setUserObject(new AgioEntryCollection());
			}
			if(this.comboSellType.getSelectedItem()==null){
				if(room.isIsPre()){
					this.comboSellType.setSelectedItem(SellTypeEnum.PreSell);
				}else{
					this.comboSellType.setSelectedItem(room.getSellType());
				}
			}
			if(this.comboValuationType.getSelectedItem()==null){
				this.comboValuationType.setSelectedItem(room.getCalcType());
				
				if(CalcTypeEnum.PriceByBuildingArea.equals(comboValuationType.getSelectedItem())||CalcTypeEnum.PriceByRoomArea.equals(comboValuationType.getSelectedItem())){
					currAgioParam.setPriceAccountType(PriceAccountTypeEnum.PriceSetStand);
				}else{
					currAgioParam.setPriceAccountType(PriceAccountTypeEnum.StandSetPrice);
				}
			}
		}else{
			this.txtBuildingPrice.setValue(FDCHelper.ZERO);
			this.txtRoomPrice.setValue(FDCHelper.ZERO);
			this.txtStandardTotalAmount.setValue(FDCHelper.ZERO);
		}
	}
	protected void comboSellType_itemStateChanged(ItemEvent e) throws Exception {
		checkSellType((RoomInfo)this.f7Room.getValue());
		updateRoomArea();
		updateAmount();
	}
	
	protected void comboValuationType_itemStateChanged(ItemEvent e) throws Exception {
		if(comboValuationType.getSelectedItem()==null) return;
		
		checkSellType((RoomInfo)this.f7Room.getValue());
		
		if(CalcTypeEnum.PriceByBuildingArea.equals(comboValuationType.getSelectedItem())||CalcTypeEnum.PriceByRoomArea.equals(comboValuationType.getSelectedItem())){
			currAgioParam.setPriceAccountType(PriceAccountTypeEnum.PriceSetStand);
		}else{
			for(int i=0;i<currAgioParam.getAgios().size();i++){
				if(currAgioParam.getAgios().get(i).getAgio()==null) return;
				if (currAgioParam.getAgios().get(i).getAgio().equals(AgioCalTypeEnum.DanJia)) {
					if(PriceAccountTypeEnum.StandSetPrice.equals(this.currAgioParam.getPriceAccountType()))
					{
						FDCMsgBox.showWarning(this,"当计价方式是按标准总价时，不能选择单价优惠折扣类型!");
						comboValuationType.setSelectedItem(null);
						SysUtil.abort();
					}
				}
			}
			currAgioParam.setPriceAccountType(PriceAccountTypeEnum.StandSetPrice);
		}
		updateRoomArea();
		updateAmount();
	}
	protected void clearTblAttach(IRow row){
		if(row==null) return;
		row.getCell(AP_ROOMNUMBER).setValue(null);
		row.getCell(AP_BUILDINGAREA).setValue(null);
		row.getCell(AP_ROOMAREA).setValue(null);
		row.getCell(AP_BUILDINGPRICE).setValue(null);
		row.getCell(AP_ROOMPRICE).setValue(null);
		row.getCell(AP_STANDARDTOTALAMOUNT).setValue(null);
		row.getCell(AP_MERGEAMOUNT).setValue(null);
		row.getCell(AP_AGIO).setValue(null);
		row.getCell(AP_AGIO).getStyleAttributes().setLocked(true);
	}
	protected void verifyAddNewAttachRoom(RoomInfo room,IRow row) throws EASBizException, BOSException{
		if (room.getStandardTotalAmount() == null || room.getStandardTotalAmount().compareTo(FDCHelper.ZERO) == 0) {
			FDCMsgBox.showWarning(this,"附属房产房间尚未定价！");
			clearTblAttach(row);
			SysUtil.abort();
		}
		if (HousePropertyEnum.NoAttachment.equals(room.getHouseProperty())) {
			FDCMsgBox.showWarning(this,"不能绑定非附属房产！");
			clearTblAttach(row);
			SysUtil.abort();
		}
		if(!room.isIsForSHE()){
			FDCMsgBox.showWarning(this,"不能绑定非售楼属性的附属房产！");
			clearTblAttach(row);
			SysUtil.abort();
		}
		for (int i = 0; i < this.tblAttachProperty.getRowCount(); i++) {
			if(row==null) break;
			if(i==row.getRowIndex()) continue;
			if(this.tblAttachProperty.getRow(i).getCell(AP_ROOMNUMBER).getValue()==null) continue;
			RoomInfo aRoom = (RoomInfo) tblAttachProperty.getRow(i).getCell(AP_ROOMNUMBER).getValue();
			if (aRoom.getId().toString().equals(room.getId().toString())) {
				FDCMsgBox.showWarning(this,"该房间已经绑定！");
				clearTblAttach(row);
				SysUtil.abort();
			}
		}
		FilterInfo filter = new FilterInfo();
		
		if(editData.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("head.id", editData.getId(),CompareType.NOTEQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
		filter.getFilterItems().add(new FilterItemInfo("head.isValid", new Boolean(false)));
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", TransactionStateEnum.PCNULLIFY,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", TransactionStateEnum.CNNULLIFY,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", TransactionStateEnum.CRNULLIFY,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", TransactionStateEnum.QRNULLIFY,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", TransactionStateEnum.PURSAVED,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", TransactionStateEnum.PRESAVED,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", TransactionStateEnum.SIGNSAVED,CompareType.NOTEQUALS));
		
		if (SignRoomAttachmentEntryFactory.getRemoteInstance().exists(filter)
				||PurRoomAttachmentEntryFactory.getRemoteInstance().exists(filter)||
					PrePurchaseRoomAttachmentEntryFactory.getRemoteInstance().exists(filter)) {
			FDCMsgBox.showWarning(this,"该房间已经被其他房间绑定！");
			clearTblAttach(row);
			SysUtil.abort();
		}
		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
		filter.getFilterItems().add(new FilterItemInfo("head.id", ((RoomInfo)this.f7Room.getValue()).getId(),CompareType.NOTEQUALS));
		
		if(RoomAttachmentEntryFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,"该房间已经被其他房间绑定！");
			clearTblAttach(row);
			SysUtil.abort();
		}
	}
	//放到子类
	protected void getAttachRoom(RoomInfo room) throws EASBizException, BOSException{
		
	}
	
	protected void f7Seller_dataChanged(DataChangeEvent e) throws Exception {
		if(e.getNewValue()==null){
			this.saleMan.clear();
		}
	}
	protected void f7PayType_dataChanged(DataChangeEvent e) throws Exception {
		if (e.getNewValue() == null) {
			((BaseTransactionInfo)editData).setIsEarnestInHouseAmount(true);
			this.updateAmount();
			return;
		}
		if (this.f7Room.getValue() == null) {
			FDCMsgBox.showWarning(this,"请先选择房间！");
			this.f7PayType.setValue(null);
			return;
		}
		SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();

		((BaseTransactionInfo)editData).setIsEarnestInHouseAmount(payType.isIsTotal());
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		view.getSelector().add("*");
		view.getSelector().add("moneyDefine.*");
		view.getSelector().add("currency.*");
		filter.getFilterItems().add(new FilterItemInfo("head.id", payType.getId().toString()));
		
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("seq"));
		
		PayListEntryCollection payList = PayListEntryFactory.getRemoteInstance().getPayListEntryCollection(view);
		payType.getPayLists().clear();
		payType.getPayLists().addCollection(payList);
		
		//计算约定签约日期
		EntityViewInfo viewBiz = new EntityViewInfo();
		FilterInfo filterbiz = new FilterInfo();
		
		filterbiz.getFilterItems().add(new FilterItemInfo("head.id", payType.getId().toString()));
		viewBiz.setFilter(filterbiz);
		viewBiz.getSorter().add(new SorterItemInfo("seq"));
		
		BizListEntryCollection bizList = BizListEntryFactory.getRemoteInstance().getBizListEntryCollection(viewBiz);

		Date date = (Date)this.pkBizDate.getValue();
		if(date==null){
			date = new Date();
		}
		if(bizList.size()>0){
			BizListEntryInfo bizListInfo = null;
			for(int i = 0; i < bizList.size(); i++){
				bizListInfo  = bizList.get(i);
				if(BizFlowEnum.Sign.equals(bizListInfo.getBizFlow()) ){
					if(BizTimeEnum.Purchase.equals(bizListInfo.getBizTime())){
						int monthLimit = bizListInfo.getMonthLimit();
						int dayLimit = bizListInfo.getDayLimit();
						date = addMonthsDays(date,monthLimit,dayLimit);
						break;
					}else if(BizTimeEnum.AppTime.equals(bizListInfo.getBizTime())){
						date = bizListInfo.getAppDate();
						break;
					}
				}
			}
			this.pkBizDate.setValue(date);
		}
		this.updataRoomContractAndDealAmount();
	}
	protected void updatePayListByPayType() {
		
	}
	protected void f7Room_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		if(e.getNewValue()==null) return;
		checkSellType((RoomInfo)this.f7Room.getValue());
		RoomInfo room = (RoomInfo)e.getNewValue();
		verifyAddNewRoom(room,((BaseTransactionInfo)this.editData).isIsBasePriceSell());
		
		updateRoomInfo();
		updateRoomArea();
		
		getAttachRoom(room);
		updateAmount();
		if(room!=null){
			room=RoomFactory.getRemoteInstance().getRoomInfo("select building.sellProject.* from where id='"+room.getId()+"'");
			if(((BaseTransactionInfo)this.editData).getSellProject()!=null
				&&!((BaseTransactionInfo)this.editData).getSellProject().getId().equals(room.getBuilding().getSellProject().getId())){
				this.f7AgioScheme.setValue(null);
				this.f7PayType.setValue(null);
				((BaseTransactionInfo)editData).setSellProject(room.getBuilding().getSellProject());
				this.setF7AgioSchemeFilter();
				this.setF7PayTypeFilter();
			}
		}
	}
	protected void chkIsFitmentToContract_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		updateAmount();
	}
	protected void txtFitmentAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		this.txtFitmentAmount1.setValue(this.txtFitmentAmount.getBigDecimalValue());
		updateAmount();
	}
	protected IRow getRoomAttactMoneyDefineRow(){
		for(int i=0;i<this.tblPayList.getRowCount();i++){
			IRow row=this.tblPayList.getRow(i);
			if(this.roomAttactMoneyDefine.equals(row.getCell(PL_MONEYNAME).getValue())){
				return row;
			}
		}
		return null;
	}
	protected IRow getFittmentMoneyDefineRow(){
		for(int i=0;i<this.tblPayList.getRowCount();i++){
			IRow row=this.tblPayList.getRow(i);
			if(this.fittmentMoneyDefine.equals(row.getCell(PL_MONEYNAME).getValue())){
				return row;
			}
		}
		return null;
	}
	/**
	 * 是否并入合同总价，对应收明细的操作
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	protected void updateRoomAttactAndFittmentMD(){
		IRow row=getFittmentMoneyDefineRow();
		if(!this.chkIsFitmentToContract.isSelected()&&this.txtFitmentAmount.getBigDecimalValue()!=null&&this.txtFitmentAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO)>0){
			if(row!=null){
				row.getCell(PL_APPAMOUNT).setValue(this.txtFitmentAmount.getBigDecimalValue());
				row.getCell(PL_BALANCE).setValue(this.txtFitmentAmount.getBigDecimalValue());
			}else{
				row=this.tblPayList.addRow();
				row.setUserObject(this.createNewPayListData());
				row.getCell(PL_MONEYNAME).setValue(this.fittmentMoneyDefine);
				row.getCell(PL_APPAMOUNT).setValue(this.txtFitmentAmount.getBigDecimalValue());
				row.getCell(PL_BALANCE).setValue(this.txtFitmentAmount.getBigDecimalValue());
				row.getCell(PL_STATE).setValue(new Boolean(false));
				row.getCell(PL_CURRENCY).setValue(currency);
			}
		}else{
			if(row!=null){
				this.tblPayList.removeRow(row.getRowIndex());
			}
		}
		IRow raRow=getRoomAttactMoneyDefineRow();
		BigDecimal attachmentTotalAmount=getMergeContractAttachmentTotalAmount(false);
		
		if(attachmentTotalAmount.compareTo(FDCHelper.ZERO)>0){
			if(raRow!=null){
				raRow.getCell(PL_APPAMOUNT).setValue(attachmentTotalAmount);
				raRow.getCell(PL_BALANCE).setValue(attachmentTotalAmount);
			}else{
				row=this.tblPayList.addRow();
				row.setUserObject(this.createNewPayListData());
				row.getCell(PL_MONEYNAME).setValue(this.roomAttactMoneyDefine);
				row.getCell(PL_APPAMOUNT).setValue(attachmentTotalAmount);
				row.getCell(PL_BALANCE).setValue(attachmentTotalAmount);
				row.getCell(PL_STATE).setValue(new Boolean(false));
				row.getCell(PL_CURRENCY).setValue(currency);
			}
		}else{
			if(raRow!=null){
				this.tblPayList.removeRow(raRow.getRowIndex());
			}
		}
	}
	protected void f7AgioScheme_dataChanged(DataChangeEvent e) throws Exception {
		if(this.f7AgioScheme.getValue()!=null){
			AgioSchemeInfo agioScheme=(AgioSchemeInfo)this.f7AgioScheme.getValue();
			boolean existDJ=false;
			boolean existZJ=false;
			AgioEntryCollection agioEntryColl = currAgioParam.getAgios(); 
			Set id=new HashSet();
			for(int i=0;i<agioEntryColl.size();i++){
				if(agioEntryColl.get(i).getAgio()==null) return;
				id.add(agioEntryColl.get(i).getAgio());
				if(AgioCalTypeEnum.DanJia.equals(agioEntryColl.get(i).getAgio().getCalType())){
					existDJ=true;
				}
				if(AgioCalTypeEnum.ZongJia.equals(agioEntryColl.get(i).getAgio().getCalType())){
					existZJ=true;
				}
			}
			for(int i=0;i<agioScheme.getAgioSchemeEntry().size();i++){
				AgioBillInfo agioBill=AgioBillFactory.getRemoteInstance().getAgioBillInfo(new ObjectUuidPK(agioScheme.getAgioSchemeEntry().get(i).getAgioBill().getId()));
				
				if(AgioCalTypeEnum.DanJia.equals(agioBill.getCalType())){
					existDJ=true;
				}
				if(AgioCalTypeEnum.ZongJia.equals(agioBill.getCalType())){
					existZJ=true;
				}
			}
			if(existDJ&&existZJ){
				FDCMsgBox.showWarning(this,"总价优惠与单价优惠折扣类型不能同时选择!");
				this.f7AgioScheme.setValue(e.getOldValue());
				SysUtil.abort();
			}
			int count=agioEntryColl.size();
			
			for(int i=0;i<agioScheme.getAgioSchemeEntry().size();i++){
				AgioBillInfo agioBill=AgioBillFactory.getRemoteInstance().getAgioBillInfo(new ObjectUuidPK(agioScheme.getAgioSchemeEntry().get(i).getAgioBill().getId()));
				
				if(id.contains(agioBill)){
					continue;
				}
				if (agioBill.getCalType().equals(AgioCalTypeEnum.DanJia)) {
					if(PriceAccountTypeEnum.StandSetPrice.equals(this.currAgioParam.getPriceAccountType()))
					{
						FDCMsgBox.showWarning(this,"当计价方式是按标准总价时，不能选择单价优惠折扣类型!");
						this.f7AgioScheme.setValue(null);
						SysUtil.abort();
					}
				}
				AgioEntryInfo entry = null;
				if (this.editData instanceof PrePurchaseManageInfo) {
					entry=new PrePurchaseAgioEntryInfo();
				} else if(this.editData instanceof PurchaseManageInfo){
					entry=new PurAgioEntryInfo();
				}else if(this.editData instanceof SignManageInfo){
					entry=new SignAgioEntryInfo();
				}
				entry.setAgio(agioBill);
				entry.setPro(agioBill.getPro());
				entry.setAmount(agioBill.getAmount());
				entry.setSeq(count);
				entry.setOperate(agioScheme.getAgioSchemeEntry().get(i).getOperate());
				agioEntryColl.add(entry);
				
				count++;
			}
			this.txtAgio.setUserObject(agioEntryColl);
			this.updateAmount();
		}
	}
	protected void f7RecommendPerson_dataChanged(DataChangeEvent e) throws Exception {
		if(e.getNewValue()!=null){
			txtRecommendCard.setText(((InsiderInfo)e.getNewValue()).getInsiderNumber());
		}else{
			txtRecommendCard.setText(null);
		}
	}
	protected Date addMonthsDays(Date date,int month,int day)
    {
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH,month);
    	calendar.add(Calendar.DATE,day);
    	return calendar.getTime();
    }
	protected void setCustomerList(List customer){
		SelectorItemCollection sels =new SelectorItemCollection();
		sels.add("id");
		sels.add("name");
		sels.add("mailAddress");
		sels.add("certificateType.*");
		sels.add("certificateNumber");
		sels.add("phone");
		sels.add("tel");
		sels.add("customerType");
		for(int i=0;i<customer.size();i++){
			try {
				SHECustomerInfo entry = SHECustomerFactory.getRemoteInstance().getSHECustomerInfo(new ObjectUuidPK(((SHECustomerInfo)customer.get(i)).getId()),sels);
				TranCustomerEntryInfo tranInfo=new TranCustomerEntryInfo();
				if(i==0){
					tranInfo.setIsMain(true);
				}else{
					tranInfo.setIsMain(false);
				}
				tranInfo.setCustomer(entry);
				tranInfo.setName(entry.getName());
				tranInfo.setAddress(entry.getMailAddress());
				tranInfo.setCertificate(entry.getCertificateType());
				tranInfo.setCertificateNumber(entry.getCertificateNumber());
				tranInfo.setPhone(entry.getPhone());
				tranInfo.setTel(entry.getTel());
				this.customer.add(tranInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			
		}
	}
	protected void setCRMFunction(BaseTransactionInfo info){
		//定金是否隶属楼款在付款方案中定义
		//参数设置如果是转交易的话，下面会重新赋值，保持交易一致
		if(info.getSellProject()==null) return;
		info.setIsEarnestInHouseAmount(true);
//		RoomDisplaySetting set = new RoomDisplaySetting(null,SHEParamConstant.PROJECT_SET_MAP);
		Map detailSet = RoomDisplaySetting.getNewProjectSet(null,info.getSellProject().getId().toString());
		boolean isBasePriceSell=false;
		if(detailSet!=null){
			isBasePriceSell = ((Boolean)detailSet.get(SHEParamConstant.T2_IS_FORCE_LIMIT_PRICE)).booleanValue();
		}
		info.setIsBasePriceSell(isBasePriceSell);
		
		HashMap value = SHEManageHelper.getCRMConstants(SysContext.getSysContext().getCurrentOrgUnit().getId());
		
		if(info.getToIntegerType()==null){
			info.setToIntegerType(value.get(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE).toString());
		}
		if(info.getDigit()==null){
			info.setDigit(value.get(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT).toString());
		}
		if(info.getPriceToIntegerType()==null){
			info.setPriceToIntegerType(value.get(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE).toString());
		}
		if(info.getPriceDigit()==null){
			info.setPriceDigit(value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString());
		}
	}
	protected void pkBizDate_dataChanged(DataChangeEvent e) throws Exception {
		this.pkBizAdscriptionDate.setValue(this.pkBizDate.getValue());
	}
}