/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.ExtendParser;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDFrame;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.multiapprove.MultiApproveCollection;
import com.kingdee.eas.base.multiapprove.MultiApproveFactory;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.basedata.assistant.AccountBankCollection;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.AccountType;
import com.kingdee.eas.basedata.assistant.BankFactory;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.assistant.CityCollection;
import com.kingdee.eas.basedata.assistant.CityFactory;
import com.kingdee.eas.basedata.assistant.CityInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.ExchangeTableInfo;
import com.kingdee.eas.basedata.assistant.IBank;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.auxacct.IAsstActType;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.TypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayReqPrjPayEntryInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.client.AbstractSplitInvokeStrategy;
import com.kingdee.eas.fdc.contract.client.BudgetViewUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI;
import com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI;
import com.kingdee.eas.fdc.contract.client.PaymentSplitInvokeStrategy;
import com.kingdee.eas.fdc.contract.client.SplitInvokeStrategyFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo;
import com.kingdee.eas.fdc.finance.FDCPaymentBillCollection;
import com.kingdee.eas.fdc.finance.FDCPaymentBillFactory;
import com.kingdee.eas.fdc.finance.FDCPaymentBillInfo;
import com.kingdee.eas.fdc.finance.FinanceException;
import com.kingdee.eas.fdc.finance.PaymentFacadeFactory;
import com.kingdee.eas.fdc.finance.PaymentPrjPayEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitHelper;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageEditUI;
import com.kingdee.eas.fi.cas.BatchFetchParamFacadeFactory;
import com.kingdee.eas.fi.cas.BgCtrlPaymentBillHandler;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.BizTypeEnum;
import com.kingdee.eas.fi.cas.CashDataTypeEnum;
import com.kingdee.eas.fi.cas.CashManagementFactory;
import com.kingdee.eas.fi.cas.CashSysParamConstants;
import com.kingdee.eas.fi.cas.ICashManagement;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.RecPayException;
import com.kingdee.eas.fi.cas.RecPayHelper;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.fi.cas.UrgentDegreeEnum;
import com.kingdee.eas.fi.cas.client.CasRecPayHandler;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fi.gl.common.GLf7Utils;
import com.kingdee.eas.fm.be.BankInterfaceTypeEnum;
import com.kingdee.eas.fm.common.CompanyF7Factory;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.fm.common.FMException;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.fm.common.FMSysDefinedEnum;
import com.kingdee.eas.fm.common.client.FMClientHelper;
import com.kingdee.eas.fm.common.client.FMClientVerifyHelper;
import com.kingdee.eas.fm.fpl.FPItemDirectionEnum;
import com.kingdee.eas.fm.fpl.FpItemInfo;
import com.kingdee.eas.fm.fs.InnerAccountInfo;
import com.kingdee.eas.fm.fs.SettBizTypeEnum;
import com.kingdee.eas.fm.fs.SettBizTypeInfo;
import com.kingdee.eas.fm.nt.ChequeInfo;
import com.kingdee.eas.fm.nt.ChequeStatusEnum;
import com.kingdee.eas.fm.nt.NTTypeGroupEnum;
import com.kingdee.eas.fm.nt.client.ChequeEditUI;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IBTPBillEdit;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * 房地产成本管理付款单编辑界面 version: 5.1.3 金蝶国际软件集团有限公司版权所有
 */

public class PaymentBillEditUI extends AbstractPaymentBillEditUI {

	private static final Logger logger = CoreUIObject
			.getLogger(PaymentBillEditUI.class);
	/**是否第一次加载,用来控制表格的加载显示*/
//	private boolean isFirstLoad = true;
	/**计算器窗口*/
//	private Window calcDiag = null; 

	private static final Color noEditColor = new Color(232, 232, 227);

	/**取得当前公司*/
	private CompanyOrgUnitInfo currentCompany ;
	
	/**当前组织*/
	protected FullOrgUnitInfo orgUnitInfo;
	
	/**当前本位币*/
	private CurrencyInfo baseCurrency ;
	
	/**工程项目*/
	private CurProjectInfo curProject ;

	/**初始数据*/
	private Map initData ;
	
    /**用途字段受控*/
    protected int usageLegth = 90;
    
    /**核算项目*/
	private AsstActTypeInfo supplierAssitType;

	private AsstActTypeInfo innerAccountActType;
	
	/** 收付款单处理类 */
	private CasRecPayHandler handler = new CasRecPayHandler();

	/** 用于绑定cell进行值操作的map key为属性的info属性名，value为cell的引用 */
	private HashMap bindCellMap = new HashMap(20);

	// 无文本合同改为允许一次请款，多次付款了。 Added by Owen_wen 2011-05-12 R110130-022
	// private boolean canModifyAmount = true;
	
	/**付款单审批后可以修改金额(不包括无文本合同生成的付款单)*/
//	private boolean canModifyAmountAfterAuit = false;
	
	/** 付款单审批后修改付款账户与付款科目 */
	private boolean canModifyAccountAfterAuit = false;
	
	/** 付款单提交、审批时，是否校验合同未完全拆分 */
	private boolean checkAllSplit = true;
	/** 付款单提交校验付款是否完全拆分 */
	private boolean checkPaymentAllSplit = false;
	/**生成凭证时是否校验拆分*/
	private boolean isPaymentSplit = true;
    /** 界面标题：主标题，新增，修改，查看，凭证索引*/
    protected String titleMain, titleNew, titleModify, titleView, billIndex = "";
    
    private boolean isBaseCurrency = true;
	
    /**甲供参数,当为true时，生成甲供主合同列表页签*/
    private boolean isPartA = FDCUtils.getDefaultFDCParamByKey(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString(), FDCConstants.FDC_PARAM_CREATEPARTADEDUCT);
    
    private PaymentBillTableHelper paymentBillTableHelper = new PaymentBillTableHelper(this);
    
    /** 是否甲供材合同 */
	private boolean isPartAMaterialCon = false;

	/** 合同内工程款原币 */
	private BigDecimal amtInContractOri = FDCHelper.ZERO;

	/** 付款单合同是否已结算 */
	private boolean hasSettled = false;

	private String contractId = null;
	
	/** 付款明细表 */
	private PaymentBillDetailHelper detailHelper = null;
	/**调整模式*/
	private boolean isAdjustVourcherModel = false;
	/** 简单模式一体化 */
	private boolean isSimpleFinancial = true;
	private boolean isSimpleFinancialExtend = true;
	private boolean isFinancial = false;
	/** 简单一体化处理发票*/
	private boolean isSimpleInvoice = false;
	/** 申请单进度款付款比例自动为100% */
	private boolean isAutoComplete = false;
	/** 工程量确认流程与付款流程是否分离 */
	private boolean isSeparate = false;
	
	/** 是否进入动态成本 */
	private boolean isCostSplit = false;	
	
	/**
	 * output class constructor
	 */
	public PaymentBillEditUI() throws Exception {
		super();
		
		jbInit() ;
	}
	
    public String getStr(String name) {
        return getStr(FDCConstants.VoucherResource, name);
    }
    
    public String getStr(String resFile,String name) {
        return EASResource.getString(resFile, name);
    }

	private void jbInit() {
	    titleMain = getUITitle();
	    titleNew = getStr("titleNew");
	    titleModify = getStr("titleModify");
	    titleView = getStr("titleView");
	}
	
    public void setDataObject(IObjectValue dataObject) {
    	
    	PaymentBillInfo info = (PaymentBillInfo)dataObject;
		//应生成的付款单是保存在财务会计里面的出纳的，但是房地产的付款单在审批之后是完全不允许被修改
    	if(info==null){
    		return ;
    	}
		BillStatusEnum billState = info.getBillStatus();
    	if (this.getBOTPViewStatus() == 1) {
    		if (billState==null){
    			info.setBillStatus(BillStatusEnum.SAVE);
    		}
    	}else{
			if (billState.equals(BillStatusEnum.PAYED)) {
				this.setOprtState(STATUS_VIEW);			
			}	
			
			if (billState.equals(BillStatusEnum.AUDITED)){
				if(!canModifyAccountAfterAuit){
					this.setOprtState(STATUS_VIEW);			
				}
			}
    	}
    	
    	if(this.getUIContext().get("ChequeReimBurse")!=null&&((Integer)this.getUIContext().get("ChequeReimBurse")).intValue()==1)
    	{
    		this.setOprtState(STATUS_EDIT);	
    	}
        super.setDataObject(dataObject);
    }

	/**
	 * 根据付款单的单据状态设定可以执行的操作
	 * 
	 * @author:zhonghui_luo 2006-10-13 14:22:04
	 * 
	 */
	public void setOprtStateByBillStatus() {
		actionViewBgBalance.setEnabled(true);
		// actionViewBgBalance.setVisible(false);
		
		setButtonStatus(this.getOprtState());
		
		setNumber();
		// btnInputCollect.setEnabled(canModifyAmount &&
		// !STATUS_VIEW.equals(this.oprtState));
		btnInputCollect.setEnabled(!STATUS_VIEW.equals(this.oprtState));
		
		if(prmtPayerAccountBank.getValue() != null && prmtPayerAccountBank.isEnabled()){
			prmtPayerAccount.setEnabled(false);
		}else{
			prmtPayerAccount.setEnabled(true);
		}
		this.comboPayeeType.setEnabled(false);
//		this.prmtPayee.setEnabled(false);
		
		this.prmtActFdcPayeeName.setEnabled(false);
	}

    protected void setButtonStatus(String oprtType){
    	this.pkBizDate.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.pkBizDate.setEnabled(false);	
		if (STATUS_FINDVIEW.equals(this.oprtState)) {
			actionEdit.setEnabled(false);
			actionSave.setEnabled(false);
			actionSubmit.setEnabled(false);
			actionClosePayReq.setVisible(false);
			actionCancelClosePayReq.setVisible(false);
			actionAudit.setVisible(false);
			actionAntiAudit.setVisible(false);
			actionAudit.setEnabled(false);
			actionAntiAudit.setEnabled(false);
			
			actionPay.setVisible(false);
			actionRefresh.setVisible(false);
			actionCreateFrom.setVisible(false);
			actionTraceUp.setVisible(false);
			if(checkPaymentAllSplit){
				actionSplit.setVisible(true);
				actionSplit.setEnabled(true);
			}else{
				actionSplit.setVisible(false);
				actionSplit.setEnabled(false);
			}
			
		} else {
			actionPay.setVisible(false);
			if(checkPaymentAllSplit){
				actionSplit.setVisible(true);
				actionSplit.setEnabled(true);
			}else{
				actionSplit.setVisible(false);
				actionSplit.setEnabled(false);
			}
			if (editData != null && editData.getBillStatus() != null) {
				if (editData.getBillStatus() == BillStatusEnum.SUBMIT||editData.getBillStatus() == BillStatusEnum.APPROVED) {
					actionSave.setEnabled(false);
					actionAudit.setEnabled(true);
					actionAudit.setVisible(true);
					actionEdit.setEnabled(true);
					actionAntiAudit.setEnabled(false);
					actionAntiAudit.setVisible(false);
					// txtNumber.setEnabled(true);
				} else if (editData.getBillStatus() == BillStatusEnum.AUDITING) {
					actionEdit.setEnabled(false);
					actionAntiAudit.setEnabled(false);
					actionAntiAudit.setVisible(false);
					actionSave.setEnabled(false);
					actionSubmit.setEnabled(false);
					actionRemove.setEnabled(false);
				} else if (editData.getBillStatus() == BillStatusEnum.AUDITED) {
					actionEdit.setEnabled(false);
					actionAudit.setEnabled(false);
					actionAntiAudit.setEnabled(true);
					actionAudit.setVisible(false);
					actionAntiAudit.setVisible(true);
					actionSave.setEnabled(false);
					actionSubmit.setEnabled(false);
					actionRemove.setEnabled(false);
				} else if (editData.getBillStatus() == BillStatusEnum.PAYED) {
					actionEdit.setEnabled(false);
					actionSave.setEnabled(false);
					actionAudit.setVisible(false);
					actionAntiAudit.setVisible(false);
					actionAudit.setEnabled(false);
					actionAntiAudit.setEnabled(false);
					actionSubmit.setEnabled(false);
					actionRemove.setEnabled(false);
				} else {
					actionAntiAudit.setEnabled(false);
					actionAntiAudit.setVisible(false);
					actionEdit.setEnabled(true);
				}
			}
			if (STATUS_EDIT.equals(this.oprtState)
					|| STATUS_ADDNEW.equals(this.oprtState)) {
				// txtNumber.setEnabled(false);
				actionRefresh.setEnabled(true);
				actionEdit.setEnabled(false);
				if (editData.getBillStatus() == BillStatusEnum.AUDITED)
					actionPay.setEnabled(true);
				else
					actionPay.setEnabled(false);
			}
			if (STATUS_ADDNEW.equals(this.oprtState)) {
				actionRefresh.setEnabled(false);
				actionSave.setEnabled(true);
				actionPay.setEnabled(false);
			}
			if (STATUS_VIEW.equals(this.oprtState) && editData != null
					&& editData.getBillStatus() == BillStatusEnum.AUDITED) {
				actionPay.setEnabled(true);
				kdtEntries.getStyleAttributes().setLocked(true);
			}
			
			if (STATUS_VIEW.equals(this.oprtState)) {
				actionCreateFrom.setEnabled(false);
			}else{
//				actionAudit.setEnabled(false);
//				actionAntiAudit.setEnabled(false);
//				actionAudit.setVisible(false);
//				actionAntiAudit.setVisible(false);
			}
			
			if(!canModifyAccountAfterAuit){
				return ;
			}
			//特殊情况,审批后可以修改日期			
			PaymentBillInfo info = (PaymentBillInfo) getDataObject();
			if(info!=null){
				BillStatusEnum billStatus = BillStatusEnum.SAVE;
				if (info.getBillStatus() != null) {
					billStatus = info.getBillStatus();
				}
				
				if (billStatus.equals(BillStatusEnum.AUDITED) && getOprtState().equals(OprtState.EDIT)){
					boolean isVoucher = info.isFiVouchered();
					boolean isNoText = ((BOSUuid.read(editData.getContractBillId()).getType())
							.equals(new ContractWithoutTextInfo().getBOSType())) ;
					
					boolean canModifyBizDate = (billStatus.equals(BillStatusEnum.SAVE)
							|| billStatus.equals(BillStatusEnum.SUBMIT) 
							|| billStatus.equals(BillStatusEnum.AUDITED)
							|| billStatus.equals(BillStatusEnum.APPROVED))
							&& !isVoucher && !isNoText;
//					pkBizDate.setEnabled(canModifyBizDate);	
					if(prmtPayerAccountBank.getValue() != null){
						prmtPayerAccountBank.setEnabled(canModifyBizDate);						
						prmtPayerAccount.setEnabled(false);
					}else{
						prmtPayerAccountBank.setEnabled(false);
						prmtPayerAccount.setEnabled(canModifyBizDate);
					}
					btnInputCollect.setEnabled(canModifyBizDate);
					//2009-2-5 启用参数FDC302_MODIFY后，应允许收款银行也可以修改，否则当收款银行为空，而把付款科目改为银行科目时，无法提交。
					txtPayeeBank.setEnabled(canModifyBizDate);
					//kdtEntries.getStyleAttributes().setLocked(!canModifyBizDate);					
					//kdtEntries.setEnabled(canModifyBizDate);		
					
					int last = getDetailTable().getRowCount() - 1;
					if(last>0){
						// 形象进度
						getDetailTable().getCell(last, 5).getStyleAttributes().setLocked(true);
						getDetailTable().getCell(last, 5).getStyleAttributes().setBackground(noEditColor);
						
						if(!canModifyBizDate){
							Set set = bindCellMap.keySet();
							for (Iterator iter = set.iterator(); iter.hasNext();) {
								Object obj = bindCellMap.get(iter.next());
								if (obj instanceof ICell) {
									ICell cell = (ICell) obj;
									if (!cell.getStyleAttributes().isLocked()) {
										cell.getStyleAttributes().setLocked(true);
									}
								}
							}
							//甲供
							getDetailTable().getCell(last - 4, 4).getStyleAttributes().setLocked(true);
						}
					}
					
					actionSubmit.setEnabled(canModifyBizDate);
					
					comboFeeType.setEnabled(false);
					prmtuseDepartment.setEnabled(false);
					txtrecProvince.setEnabled(false);
					txtrecCity.setEnabled(false);

					difPlace.setEnabled(false);
//					txtPayeeBank.setEnabled(false);
					txtPayeeAccountBank.setEnabled(false);
					prmtCurrency.setEnabled(false);
					prmtSettleBizType.setEnabled(false);
					mergencyState.setEnabled(false);

					txtAccessoryAmt.setEnabled(false);
					txtSummary.setEnabled(false);
					txtUsage.setEnabled(false);
					prmtFpItem.setEnabled(false);
					prmtSettlementType.setEnabled(false);
					contDescription.setEnabled(false);
					f7SettleNumber.setEnabled(false);
					
				}
			}
		}
    }
	protected void f7SettleNumber_dataChanged(DataChangeEvent e)
			throws Exception {
	}

	/**
	 * 
	 * 修改：由于可以录入文本，所以直接把willCommit中关于过滤得删掉
	 * 
	 * @author:peng_peng1
	 * @see com.kingdee.eas.fi.cas.client.AbstractCasPaymentBillUI#f7SettleNumber_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent)
	 */
	protected void f7SettleNumber_willCommit(CommitEvent e) throws Exception {
		super.f7SettleNumber_willCommit(e);
		ExtendParser parser = new ExtendParser(f7SettleNumber);
		f7SettleNumber.setCommitParser(parser);
		f7SettleNumber.setCommitFormat(null);

	}
	

	protected void f7SettleNumber_willShow(SelectorEvent e) throws Exception {
		super.f7SettleNumber_willShow(e);

		SettlementTypeInfo settType = (SettlementTypeInfo) prmtSettlementType
				.getData();
		if (settType != null) {
			if (settType.getNtType() != null) {//
				
				if (NTTypeGroupEnum.PaymentCheque.equals(settType.getNtType()
						.getSuperGroup())
					) {//R100130-010
					EntityViewInfo evi = new EntityViewInfo();
					evi.setFilter(getChequeFilter());
					
					f7SettleNumber.setEntityViewInfo(evi);
					f7SettleNumber.getQueryAgent().resetRuntimeEntityView();
					if(f7SettleNumber.isEnabledMultiSelection()){
						String tempSettle=null;
						if(f7SettleNumber.getData() instanceof String){
							tempSettle=(String)f7SettleNumber.getData();
							ChequeInfo cheque=new ChequeInfo();
							cheque.setNumber(tempSettle);
							f7SettleNumber.setData(new Object[]{cheque});
						}
						f7SettleNumber.setEnabledMultiSelection(false);
						if(f7SettleNumber.getData() instanceof ChequeInfo){
							f7SettleNumber.setData(tempSettle);
						}
					}
					return;
				}
			}

			// 如果没有票据类型，或者不为应收票据，设置为空
			e.setCanceled(true);
			f7SettleNumber.setCommitFormat(null);
		} else {
			e.setCanceled(true);
			f7SettleNumber.setCommitFormat(null);
		}
	}
	private FilterInfo getChequeFilter() throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("keepCompany.id", currentCompany.getId()
						.toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("company.id", currentCompany.getId()
						.toString()));
		filter.getFilterItems().add(new FilterItemInfo("state", new Integer(ChequeStatusEnum.NEW_VALUE)));
		filter.setMaskString("(#0 OR #1) AND #2");
		if(prmtPayerAccountBank.getValue()!=null)
		{
			AccountBankInfo accountBankInfo = (AccountBankInfo) prmtPayerAccountBank
					.getValue();
			FilterInfo filterAccBank = new FilterInfo();
			filterAccBank.getFilterItems().add(
					new FilterItemInfo("bankAcct.id", accountBankInfo.getId()
							.toString()));
			
			if(accountBankInfo.getBank()==null){
				AccountBankInfo acbInfo=AccountBankFactory.getRemoteInstance().getAccountBankInfo(new ObjectUuidPK(accountBankInfo.getId()));
				accountBankInfo.setBank(acbInfo.getBank());
			}
			filterAccBank.getFilterItems().add(
					new FilterItemInfo("bank.id", accountBankInfo.getBank().getId()
							.toString()));
			filterAccBank.setMaskString("#0 OR #1");
			filter.mergeFilter(filterAccBank, "AND");
		}
		SettlementTypeInfo settType = (SettlementTypeInfo) prmtSettlementType.getData();
		if (settType != null&&settType.getNtType() != null) {
			FilterInfo filterNtType=new FilterInfo();
			filterNtType.getFilterItems().add(
					new FilterItemInfo("ntType.id", settType.getNtType().getId()
							.toString()));
			filter.mergeFilter(filterNtType, "AND");
		}
		return filter;
	}
    
//    private void lockDetailTable(){
//    	
//		if(!canModifyAccountAfterAuit){
//			return ;
//		}
//		
//		//特殊情况,审批后可以修改日期			
//		PaymentBillInfo info = (PaymentBillInfo) getDataObject();
//		if(info!=null){
//			BillStatusEnum billStatus = BillStatusEnum.SAVE;
//			if (info.getBillStatus() != null) {
//				billStatus = info.getBillStatus();
//			}
//			
//			if (billStatus.equals(BillStatusEnum.AUDITED) && getOprtState().equals(OprtState.EDIT)){
//				boolean isVoucher = info.isFiVouchered();
//				
//				boolean canModifyBizDate = (billStatus.equals(BillStatusEnum.SAVE)
//						|| billStatus.equals(BillStatusEnum.SUBMIT) 
//						|| billStatus.equals(BillStatusEnum.AUDITED)
//						|| billStatus.equals(BillStatusEnum.APPROVED))
//						&& !isVoucher;
//				getDetailTable().setEnabled(canModifyBizDate);
//				getDetailTable().setEditable(canModifyBizDate);
//				getDetailTable().getCell(4,4).getStyleAttributes().setLocked(!canModifyBizDate);
//				setTableCellColorAndEdit(!canModifyBizDate) ;
//				// 去掉付款计划的格:
//				int lastRowIdx = kdtEntries.getRowCount() - 1;
//				for (int i = 4; i < kdtEntries.getColumnCount(); i++) {
//					StyleAttributes styleAttributes = kdtEntries.getCell(lastRowIdx - 1, i)
//							.getStyleAttributes();
//					styleAttributes.setLocked(true);
//					styleAttributes.setBackground(noEditColor);
//				}
//				kdtEntries.getCell(lastRowIdx - 1, 1).getStyleAttributes().setLocked(true);
//				kdtEntries.getCell(lastRowIdx - 1, 1).getStyleAttributes().setBackground(noEditColor);
//				
//				kdtEntries.getCell(9,4).getStyleAttributes().setLocked(true);
//				kdtEntries.getCell(lastRowIdx - 1,6).getStyleAttributes().setLocked(!canModifyBizDate);
//			}
//		}
//    }
    
	protected void setNumber() {
		PaymentBillInfo info = (PaymentBillInfo) getEditData();
		txtNumber.setEnabled(false);
		if (info==null || info.getBillStatus() == null) {
			return;
		}
		BillStatusEnum billStatus = info.getBillStatus();
		if (billStatus.equals(BillStatusEnum.SAVE)
				|| billStatus.equals(BillStatusEnum.SUBMIT)) {
			if (info.getCompany() == null) {
				info.setCompany(currentCompany);
			}
			FMClientHelper.initNumber(info, txtNumber, info.getCompany()
					.getId().toString());

			txtNumber.setRequired(txtNumber.isEnabled());
		}
	}
	/**
	 * 设置本位币金额,大小写
	 * 
	 * @author:zhonghui_luo 2006-10-13 14:22:04
	 */
	private void setAmount() {
//		txtAmount.setPrecision(2);
		txtLocalAmt.setPrecision(2);
//		txtExchangeRate.setPrecision(2);
		txtAmount.setRemoveingZeroInDispaly(false);
		txtLocalAmt.setRemoveingZeroInDispaly(false);
		txtExchangeRate.setRemoveingZeroInDispaly(false);
		
		if(this.editData.getFdcPayReqID()!=null&&prmtCurrency.getValue()!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("currency.id");
			sel.add("originalAmount");
			sel.add("amount");
			
			try {
				PayRequestBillInfo pay = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
				KDTable table = getDetailTable();
				BigDecimal localamount =FDCHelper.ZERO;
				BigDecimal amount=FDCHelper.ZERO;
				BigDecimal exchangeRate = toBigDecimal(txtExchangeRate.getNumberValue());
				
				CurrencyInfo currency=(CurrencyInfo)prmtCurrency.getValue();
				if(!pay.getCurrency().getId().toString().equals(currency.getId().toString())){
					BigDecimal actPayAmount=FDCHelper.ZERO;
					BigDecimal actPayLocAmount=FDCHelper.ZERO;
					FDCSQLBuilder _builder = new FDCSQLBuilder();
					_builder.appendSql(" select sum(pb.famount) actPayAmount,sum(pb.flocalAmount) actPayLocAmount from t_cas_paymentbill pb");
					_builder.appendSql(" where pb.fFdcPayReqID='"+pay.getId().toString()+"'");
					if(editData.getId()!=null){
						_builder.appendSql(" and pb.fid!='"+editData.getId().toString()+"'");
					}
					final IRowSet rowSet = _builder.executeQuery();
					while (rowSet.next()) {
						if(rowSet.getBigDecimal("actPayAmount")!=null){
							actPayAmount=rowSet.getBigDecimal("actPayAmount");
						}
						if(rowSet.getBigDecimal("actPayLocAmount")!=null){
							actPayLocAmount=rowSet.getBigDecimal("actPayLocAmount");
						}
					}
					
					if(table.getCell(4, 4)!=null){
						table.getCell(4, 4).getStyleAttributes().setLocked(true);
						table.getCell(4, 4).setValue(pay.getOriginalAmount().subtract(actPayAmount));
					}
					localamount=pay.getAmount().subtract(actPayLocAmount);
					this.txtLocalAmt.setValue(localamount);
					this.editData.setLocalAmt(localamount);
					
					amount=localamount.divide(FDCHelper.toBigDecimal(exchangeRate),2,BigDecimal.ROUND_HALF_UP);	
					txtAmount.setValue(amount);
					this.editData.setAmount(amount);
				}else{
					if(table.getCell(4, 4)!=null){
						table.getCell(4, 4).getStyleAttributes().setLocked(false);
					}
					Object cell = bindCellMap.get(PaymentBillContants.CURPAID);
					if (cell instanceof ICell) {
						Object value = ((ICell) cell).getValue();
						if (value != null&&value instanceof BigDecimal) {
							amount=(BigDecimal) value;
							this.txtAmount.setValue(amount);
							this.editData.setAmount(amount);
							
							if (exchangeRate instanceof BigDecimal)
							{			
								localamount=amount.multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP);
								this.txtLocalAmt.setValue(localamount);
								this.editData.setLocalAmt(localamount);
							}
						}
					}else{
						if (exchangeRate instanceof BigDecimal)
						{			
							amount=this.txtAmount.getBigDecimalValue();
							localamount=amount.multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP);
							this.txtLocalAmt.setValue(localamount);
							this.editData.setLocalAmt(localamount);
						}
					}
				}
				if(localamount.compareTo(FDCConstants.ZERO)!=0){
					localamount = localamount.setScale(2,BigDecimal.ROUND_HALF_UP);
					//大写金额为本位币金额
					String cap = FDCClientHelper.getChineseFormat(localamount,false);
					txtcapitalAmount.setText(cap);
					this.editData.setCapitalAmount(cap);
				}else{
					txtcapitalAmount.setText(null);
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 覆盖父类的方法以控制工具栏按钮的显示
	 * 
	 * @author:zhonghui_luo 2006-10-13 14:22:04
	 * 
	 */
	public void initUIToolBarLayout() {
		super.initUIToolBarLayout();
		actionCalculator.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_counter"));
		actionClosePayReq.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_close"));
		actionCancelClosePayReq.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_fclose"));
		actionPay.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_payment"));
		actionAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_AUDIT);
		actionAntiAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_UNAUDIT);
		actionRefresh.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_refresh"));
		actionPaymentPlan.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_showdata"));
		btnTaoPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		actionPaymentPlan.setVisible(false);
		actionCopy.setVisible(false);
		actionAuditResult.setVisible(false);
		actionTraceDown.setVisible(false);
		actionWorkFlowG.setVisible(false);
		actionCopyFrom.setVisible(false);
		actionAddLine.setVisible(false);
		actionInsertLine.setVisible(false);
		actionRemoveLine.setVisible(false);
		actionVoucher.setVisible(false);
		actionDelVoucher.setVisible(false);
		actionMultiapprove.setVisible(false);
		btnTaoPrint.setVisible(false);
		this.btnWFViewdoProccess.setVisible(false);
		this.btnWFViewSubmitProccess.setVisible(false);
	}
	
	//获取初始化数据
	protected  void fetchInitData() throws Exception{

		Map ctx = this.getUIContext();
		String contractBillId = (String) ctx.get("contractBillId");

		Map initparam = new HashMap();
		
		if (this.getBOTPViewStatus() == 1) {
			String srcId = (String)ctx.get("srcBillID");
			initparam.put("srcBillID",srcId);
		}else{
			initparam.put("ID",getUIContext().get("ID"));
			if(contractBillId!=null){
				initparam.put("contractBillId",contractBillId);
			}
		}
		if(editData!=null &&editData.getCreateTime()!=null){
			initparam.put("createTime",editData.getCreateTime());
			initparam.put("FdcPayReqID",editData.getFdcPayReqID());
		}
		initData = PaymentFacadeFactory.getRemoteInstance().fetchInitData(initparam);
		if(contractBillId==null){
			contractBillId = (String)initData.get("contractBillId");
		}
		if(initData==null){
			MsgBox.showWarning(this,"不是房地产单据");
			SysUtil.abort();
		}
		
		//公司
		if (currentCompany == null) {
			currentCompany = (CompanyOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_COMPANY);
		}
		if(orgUnitInfo==null){
			orgUnitInfo = (FullOrgUnitInfo) initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		}else{
			orgUnitInfo =SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		}
		//本位币
		if (baseCurrency == null) {
			baseCurrency = (CurrencyInfo)initData.get(FDCConstants.FDC_INIT_CURRENCY);
		}

		curProject = (CurProjectInfo) initData.get(FDCConstants.FDC_INIT_PROJECT);
		
//		//合同单据
//		if(initData.get(FDCConstants.FDC_INIT_CONTRACT) instanceof ContractBillInfo){
//			contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
//		}	else{
//			text = (ContractWithoutTextInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
//		}
//		
//		info = (PayRequestBillInfo)initData.get("PayRequestBillInfo");
//		payReqCol =(PayRequestBillCollection) initData.get("PayRequestBillCollection");
//		payCol = (PaymentBillCollection)initData.get("PaymentBillCollection");		
		
		
		//是否启用预算控制
		if(currentCompany!=null){
			HashMap param = FDCUtils.getDefaultCashOrGLParam(null,currentCompany.getId().toString());
			if(param.get("CS050")!=null){
				usageLegth = Integer.valueOf(param.get("CS050").toString()).intValue();
			}		
			
			if(param.get("CS034")!=null){
				initData.put("PARAM_CS034", param.get("CS034"));
			}
			
			if(param.get("CS036")!=null){
				initData.put("PARAM_CS036", param.get("CS036"));
			}
			
			//canModifyAccountAfterAuit
			if(param.get(FDCConstants.FDC_PARAM_MODIFYACOUNT)!=null){
				canModifyAccountAfterAuit =Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_MODIFYACOUNT).toString()).booleanValue();
			}	
			/*isSimpleFinancial = FDCUtils.getDefaultFDCParamByKey(null,
					currentCompany.getId().toString(),
					FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
			isAutoComplete = FDCUtils.getDefaultFDCParamByKey(null,
					currentCompany.getId().toString(),
					FDCConstants.FDC_PARAM_PAYPROGRESS);
			isPaymentSplit = FDCUtils.getDefaultFDCParamByKey(null,currentCompany.getId().toString(), FDCConstants.FDC_PARAM_CHECKPAYMENTSPLIT);
			isSeparate = FDCUtils.getDefaultFDCParamByKey(null, currentCompany
					.getId().toString(),
					FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);*/	
			//财务组织参数(批量取数)
			HashMap fiParam = FDCUtils.getDefaultFDCParam(null, currentCompany.getId().toString());
			isFinancial = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_FINACIAL);
			isAdjustVourcherModel = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
			isSimpleFinancial = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
			isSimpleFinancialExtend = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND);
			isSimpleInvoice = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_SIMPLEINVOICE);
//			isAutoComplete = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_PAYPROGRESS);
			isPaymentSplit = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_CHECKPAYMENTSPLIT);
			isSeparate = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);	
		}
		
		//成本中心参数
		if(orgUnitInfo==null){//非工程项目树如查询等界面时组织为空
			orgUnitInfo =SysContext.getSysContext().getCurrentCostUnit().castToFullOrgUnitInfo();
		}
		HashMap paramMap = FDCUtils.getDefaultFDCParam(null, orgUnitInfo.getId().toString());
		checkPaymentAllSplit = isPaymentSplit&&FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_CHECKPAYMENTALLSPLIT);
		isAutoComplete = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_PAYPROGRESS);//Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_PAYPROGRESS).toString()).booleanValue();
		
		isCostSplit = FDCUtils.isCostSplit(null,contractBillId);
	}
	
	//监听器
	Map listenersMap = new HashMap();
    //加载完数据后加上监听器
    protected void attachListeners() {
    	
    	EventListener[] listeners = (EventListener[] )listenersMap.get(prmtuseDepartment);
		for(int i=0;i<listeners.length;i++){
			prmtuseDepartment.addDataChangeListener((DataChangeListener)listeners[i]);
		}
    	
    	listeners = (EventListener[] )listenersMap.get(prmtCurrency);
		for(int i=0;i<listeners.length;i++){
			prmtCurrency.addDataChangeListener((DataChangeListener)listeners[i]);
		}
		
    	listeners = (EventListener[] )listenersMap.get(prmtPayerAccountBank);
		for(int i=0;i<listeners.length;i++){
			prmtPayerAccountBank.addDataChangeListener((DataChangeListener)listeners[i]);
		}
		
		//txtrecCity
    	listeners = (EventListener[] )listenersMap.get(txtrecCity);
		for(int i=0;i<listeners.length;i++){
			txtrecCity.addDataChangeListener((DataChangeListener)listeners[i]);
		}
		
    	listeners = (EventListener[] )listenersMap.get(txtrecProvince);
		for(int i=0;i<listeners.length;i++){
			txtrecProvince.addDataChangeListener((DataChangeListener)listeners[i]);
		}
		
		listeners = (EventListener[] )listenersMap.get(comboPayeeType);
		for(int i=0;i<listeners.length;i++){
			((KDComboBox)comboPayeeType).addItemListener((ItemListener)listeners[i]);
		}
				
		listeners = (EventListener[] )listenersMap.get(txtInvoiceAmt);
		for(int i=0;i<listeners.length;i++){
			txtInvoiceAmt.addDataChangeListener((DataChangeListener)listeners[i]);
		}
    }
    
    //注销监听器
    protected void detachListeners() {
    	
		EventListener[] listeners = prmtuseDepartment.getListeners(DataChangeListener.class);	
		for(int i=0;i<listeners.length;i++){
			prmtuseDepartment.removeDataChangeListener((DataChangeListener)listeners[i]);
		}		
		listenersMap.put(prmtuseDepartment,listeners );
    	
		listeners = prmtCurrency.getListeners(DataChangeListener.class);	
		for(int i=0;i<listeners.length;i++){
			prmtCurrency.removeDataChangeListener((DataChangeListener)listeners[i]);
		}		
		listenersMap.put(prmtCurrency,listeners );
		
		listeners = prmtPayerAccountBank.getListeners(DataChangeListener.class);	
		for(int i=0;i<listeners.length;i++){
			prmtPayerAccountBank.removeDataChangeListener((DataChangeListener)listeners[i]);
		}		
		listenersMap.put(prmtPayerAccountBank,listeners );
		
		listeners = txtrecProvince.getListeners(DataChangeListener.class);	
		for(int i=0;i<listeners.length;i++){
			txtrecProvince.removeDataChangeListener((DataChangeListener)listeners[i]);
		}		
		listenersMap.put(txtrecProvince,listeners );
		
		listeners = txtrecCity.getListeners(DataChangeListener.class);	
		for(int i=0;i<listeners.length;i++){
			txtrecCity.removeDataChangeListener((DataChangeListener)listeners[i]);
		}		
		listenersMap.put(txtrecCity,listeners );
		
		listeners = comboPayeeType.getListeners(ItemListener.class);	
		for(int i=0;i<listeners.length;i++){
			((KDComboBox)comboPayeeType).removeItemListener((ItemListener)listeners[i]);
		}
		listenersMap.put(comboPayeeType,listeners );
		
		listeners = txtInvoiceAmt.getListeners(DataChangeListener.class);	
		for(int i=0;i<listeners.length;i++){
			txtInvoiceAmt.removeDataChangeListener((DataChangeListener)listeners[i]);
		}
		listenersMap.put(txtInvoiceAmt,listeners );
    }

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		
		//先注销监听器
		detachListeners();		

		if(editData==null){
			SysUtil.abort();
		}
		
// 无文本合同改为允许一次请款，多次付款了。 Added by Owen_wen 2011-05-12 R110130-022
		// 如果是无文本合同，那么不能修改金额
		// if (editData.getContractBillId() != null&&
		// PayReqUtils.isConWithoutTxt(editData.getContractBillId())) {
		// canModifyAmount = false;
		// }else{
		// canModifyAmount = true;
		// }
		// btnInputCollect.setEnabled(canModifyAmount);
		boolean isExist = false;
		if(initData.get("isExist")!=null){
			isExist=((Boolean)initData.get("isExist")).booleanValue();
		}
		PayRequestBillInfo info = (PayRequestBillInfo)initData.get("PayRequestBillInfo");
		// 如果是单据转换,则设置付款单的金额
		// 付款单金额 = 对应付款申请单的金额 - 此付款单申请单所有分录金额之和		
		if (this.getBOTPViewStatus() == 1) {
			if(info==null){
				try {
					fetchInitData();
				} catch (Exception e) {
					e.printStackTrace();
					this.handleException(e);
				}
			}else{
				this.editData.setIsNeedPay(info.isIsPay());			
			}		
			
			BigDecimal amou = FDCHelper.ZERO; //原币小计
			BigDecimal amouLocal = FDCHelper.ZERO;//本币小计
			BigDecimal total =FDCHelper.ZERO; //所有原币
			BigDecimal totalLocal = FDCHelper.ZERO;//所有本币
			BigDecimal paya = FDCHelper.ZERO; //new BigDecimal(0);
			BigDecimal payaTo = FDCHelper.ZERO; //new BigDecimal(0);
			BigDecimal incon = FDCHelper.ZERO; //new BigDecimal(0);
			BigDecimal inconTo = FDCHelper.ZERO; //new BigDecimal(0);
			BigDecimal add = FDCHelper.ZERO; //new BigDecimal(0);
			BigDecimal addTo = FDCHelper.ZERO; //new BigDecimal(0);
			//申请单分录 -- 申请金额 原币
			BigDecimal addLocal = FDCHelper.ZERO;
			BigDecimal addConInPrj = FDCHelper.ZERO;
			BigDecimal addAdvance = FDCHelper.ZERO;
			BigDecimal addLocAdvance = FDCHelper.ZERO;
			if (editData.getFdcPayReqID() != null) {
				
				if(info.getUrgentDegree()!=null&&UrgentDegreeEnum.URGENT_VALUE.endsWith(String.valueOf(info.getUrgentDegree().getValue()))){
					editData.put("isEmergency", new Integer(1));
				}else{
					editData.put("isEmergency", new Integer(0));
				}
				
				if (info.getCompletePrjAmt() != null){
					if(isExist){
						//已存在付款单
						txtcompletePrjAmt.setValue(FDCHelper.ZERO);
					}else{
						txtcompletePrjAmt.setValue(info.getCompletePrjAmt());
					}
				}
				if (info.getPaymentProportion() != null){
					txtpaymentProportion.setValue(info.getPaymentProportion());
				}
				if (info.getProjectPriceInContractOri() != null){
					editData.setAddProjectAmt(info.getProjectPriceInContractOri());
				}
				//如果没有字段带过来:收款人类型、名称、摘要、备注、附件数
				if(supplierAssitType!=null){
					editData.setPayeeType(supplierAssitType);
				}
				
				//
				if(info.getSupplier()!=null){
					editData.setPayeeID(info.getSupplier().getId().toString());
					editData.setPayeeNumber(info.getSupplier().getNumber());
					editData.setPayeeName(info.getSupplier().getName());
				}
				editData.setActFdcPayeeName(info.getRealSupplier());
				
				editData.setDescription(info.getDescription());
				editData.setSummary(info.getMoneyDesc());
				
				editData.setAccessoryAmt(info.getAttachment());
				
				//付款类型
				editData.setFdcPayType(info.getPaymentType());
				
				//新加２个字段
//				editData.setUsage(info.getUsage());
				editData.setIsDifferPlace(info.getIsDifferPlace());

				total = info.getOriginalAmount();
				totalLocal = info.getAmount();
				payaTo = info.getPayPartAMatlAmt();
				inconTo = info.getProjectPriceInContract();
				addTo = info.getAddProjectAmt();
				
				if (this.editData.getFdcPayReqID() != null) {
					PayRequestBillEntryCollection payReqEntry = info.getEntrys();
					if(payReqEntry!=null){
						Iterator itor;
						for (itor = payReqEntry.iterator(); itor.hasNext();) {
							PayRequestBillEntryInfo entry = (PayRequestBillEntryInfo) itor.next();
							if (entry.getOriginalAmount() != null)
								amou = amou.add(entry.getOriginalAmount());
							if (entry.getAmount() != null){
//								amou = amou.add(entry.getAmount());
								amouLocal = amouLocal.add(entry.getAmount());
							}
							if(entry.getAddProjectAmt() != null)
								addLocal = addLocal.add(entry.getAddProjectAmt());
							if(entry.getProjectPriceInContract() != null)
								addConInPrj = addConInPrj.add(entry.getProjectPriceInContract());
							if (entry.getPayPartAMatlAmt() != null)
								paya = paya.add(entry.getPayPartAMatlAmt());
							if (entry.getProjectPriceInContract() != null)
								incon = incon.add(entry.getProjectPriceInContract());
							if (entry.getAddProjectAmt() != null)
								add = add.add(entry.getAddProjectAmt());
							if (entry.getAdvance() != null) {
								addAdvance = addAdvance.add(entry.getAdvance());
							}
							if (entry.getLocAdvance() != null) {
								addLocAdvance = addLocAdvance.add(entry
										.getLocAdvance());
							}
						}	
					}
					
				}				
				
				if(info!=null){
					pkbookedDate.setValue(info.getBookedDate());
					cbPeriod.setValue(info.getPeriod());
					editData.setBizDate(info.getBookedDate());
				}
			}
			editData.setAddProjectAmt(FDCHelper.subtract(info.getProjectPriceInContractOri(),addLocal));
			editData.setAmount(FDCHelper.subtract(total, amou));
//			editData.setLocalAmt(FDCHelper.subtract(totalLocal, amouLocal));
			editData.setProjectPriceInContract(FDCHelper.subtract(info.getProjectPriceInContract(),addConInPrj));
			this.txtAmount.setValue(FDCHelper.subtract(total, amou));
//			this.txtLocalAmt.setValue(FDCHelper.subtract(totalLocal, amouLocal));
			// ((ICell)bindCellMap.get(PaymentBillContants.PROJECTPRICEINCONTRACT)).setValue(editData.getAmount());
			if(this.isAdvance()){
				PaymentPrjPayEntryInfo prjPayEntry = editData.getPrjPayEntry();
				if(prjPayEntry==null){
					prjPayEntry = new PaymentPrjPayEntryInfo();
					editData.setPrjPayEntry(prjPayEntry);
				}
				if(info.getPrjPayEntry()!=null){
					editData.getPrjPayEntry().setAdvance(FDCHelper.subtract(info.getPrjPayEntry().getAdvance(), addAdvance));
					editData.getPrjPayEntry().setLocAdvance(FDCHelper.subtract(info.getPrjPayEntry().getLocAdvance(), addLocAdvance));
					editData.getPrjPayEntry().setLstAdvanceAllPaid(info.getPrjPayEntry().getLstAdvanceAllPaid());
					editData.getPrjPayEntry().setLstAdvanceAllReq(info.getPrjPayEntry().getAdvanceAllReq());
				}
			}
			if (payaTo != null)
				editData.setPayPartAMatlAmt(payaTo.subtract(paya));
			
			// 本期发生不应该扣减，本期发生指当前申请付款金额
			// if (inconTo != null)
			// editData.setProjectPriceInContract(inconTo.subtract(incon));
//			if (addTo != null)
//				editData.setAddProjectAmt(addTo.subtract(add));
			
		} else if (editData.getFdcPayReqID() != null) {
//			editData.setAddProjectAmt(info.getProjectPriceInContractOri());
			if (info.getCompletePrjAmt() != null)
				if(isExist){//已存在付款单
					txtcompletePrjAmt.setValue(FDCHelper.ZERO);
				}else{
					txtcompletePrjAmt.setValue(info.getCompletePrjAmt());
				}
			//预付款全部给第一张,存在时不处理多张
			if(info.get("prjPayEntry")!=null){
				PayReqPrjPayEntryInfo payReqEntry = (PayReqPrjPayEntryInfo)info.get("prjPayEntry");
				PaymentPrjPayEntryInfo payEntry = editData.getPrjPayEntry();
				if(payEntry==null){
					payEntry = new PaymentPrjPayEntryInfo();
					editData.setPrjPayEntry(payEntry);
					payEntry.setLstAdvanceAllPaid(payReqEntry.getLstAdvanceAllPaid());
					payEntry.setLstAdvanceAllReq(payReqEntry.getAdvanceAllReq());
					payEntry.setAdvance(payReqEntry.getAdvance());
					payEntry.setLocAdvance(payReqEntry.getLocAdvance());
				}
				
			}
			if (info.getPaymentProportion() != null)
					txtpaymentProportion.setValue(info.getPaymentProportion());				
			
			if(info!=null){
				pkbookedDate.setValue(info.getBookedDate());
				cbPeriod.setValue(info.getPeriod());
			}
		}
		
		//第一张处理发票
		if(!isExist){
			PaymentPrjPayEntryInfo payEntry = editData.getPrjPayEntry();
			if(payEntry==null){
				payEntry = new PaymentPrjPayEntryInfo();
				editData.setPrjPayEntry(payEntry);
			}
		//////////////////////////////////////////////////////////////////////////////////////////
			editData.getPrjPayEntry().setInvoiceNumber(info.getInvoiceNumber());
			editData.getPrjPayEntry().setInvoiceAmt(info.getInvoiceAmt());
//			txtInvoiceNumber.setText(info.getInvoiceNumber());
//			txtInvoiceAmt.setValue(info.getInvoiceAmt());
//			pkInvoiceDate.setValue(info.getInvoiceDate());
			
		}else{
//			pkInvoiceDate.setValue(null);
		}
		
//		txtAllInvoiceAmt.setValue(info.getAllInvoiceAmt());
	
		//处理发票 by zhiyuan_tang
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentbill.id", editData.getId()));
		view.setFilter(filter);
		FDCPaymentBillCollection fdcPaymentCol = null;
		try {
			fdcPaymentCol = FDCPaymentBillFactory.getRemoteInstance().getFDCPaymentBillCollection(view);
		} catch (BOSException e1) {
			logger.error(e1.getStackTrace(), e1);
		}
		
		if (fdcPaymentCol != null) {
			for (Iterator it = fdcPaymentCol.iterator(); it.hasNext();) {
				FDCPaymentBillInfo fdcPaymentInfo = (FDCPaymentBillInfo)it.next();
				txtInvoiceNumber.setText(fdcPaymentInfo.getInvoiceNumber());
				txtInvoiceAmt.setValue(fdcPaymentInfo.getInvoiceAmt());
				pkInvoiceDate.setValue(fdcPaymentInfo.getInvoiceDate());

				PaymentPrjPayEntryInfo payEntry = editData.getPrjPayEntry();
				if (payEntry == null) {
					payEntry = new PaymentPrjPayEntryInfo();
					editData.setPrjPayEntry(payEntry);
				}
				editData.getPrjPayEntry().setInvoiceNumber(info.getInvoiceNumber());
				editData.getPrjPayEntry().setInvoiceAmt(info.getInvoiceAmt());
			}
		}
		
		BigDecimal allInvoiceAmt = (BigDecimal) initData.get("FDCPaymentAllInvoicAmt");
		if (allInvoiceAmt != null) {
			txtAllInvoiceAmt.setValue(allInvoiceAmt);
		}
		
		
		// 打回意见(暂时去除）
		getConceit();
		// 设置计划项目的过滤条件
//		EntityViewInfo evi = CasRecPayHandler
//				.getFpItemEvi(FPItemDirectionEnum.OUTWARD_MOVEMENT);
//		prmtFpItem.setEntityViewInfo(evi);
		
		
		if (this.editData.getBillStatus() == null)
			this.editData.setBillStatus(BillStatusEnum.SAVE);
		this.editData.setSourceType(SourceTypeEnum.FDC);
		if (this.editData.getSettleBizType() == null)
			this.editData.setSettleBizType(SettBizTypeEnum.PAYOUTSIDE);
		
		if (this.editData.getCompany() == null)
			this.editData.setCompany(SysContext.getSysContext().getCurrentFIUnit());
		//当第一个人（一般是出纳人员与申请单生成付款单时不是同一人）修改时赋值
//		if (this.editData.getCreator() == null)
//			this.editData.setCreator((SysContext.getSysContext().getCurrentUserInfo()));
//		if (this.editData.getCreateTime() == null)
//			this.editData.setCreateTime(new Timestamp(System.currentTimeMillis()));
		if (this.editData.getUrgentDegree() == null)
			this.editData.setUrgentDegree(UrgentDegreeEnum.NORMAL);
//		if (this.editData.getPayDate() == null)
//			this.editData.setPayDate(DateTimeUtils.truncateDate(new Date()));
		if (this.editData.getBizDate() == null)
			this.editData.setBizDate(DateTimeUtils.truncateDate(new Date()));
		if (this.editData.getDayaccount() == null)
			this.editData.setDayaccount(FDCHelper.ZERO);
		if (this.editData.getCU() == null)
			this.editData.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		if ((this.editData.getFdcPayReqID() == null)
				|| (STATUS_FINDVIEW.equals(this.oprtState))) {
			this.actionAntiAudit.setEnabled(false);
			this.actionClosePayReq.setEnabled(false);
			this.actionCancelClosePayReq.setEnabled(false);
			this.btnClosePayReq.setEnabled(false);
			this.btnCancelClosePayReq.setEnabled(false);
			actionTraceUp.setEnabled(false);
		} else {
			this.actionClosePayReq.setEnabled(true);
			this.actionCancelClosePayReq.setEnabled(true);
			this.btnClosePayReq.setEnabled(true);
			this.btnCancelClosePayReq.setEnabled(true);
			this.actionTraceUp.setEnabled(true);
		}
		// if (this.getBOTPViewStatus() == 0) {
		String contractBillId = this.editData.getContractBillId();
		if (contractBillId != null) {
			if ((BOSUuid.read(contractBillId).getType())
					.equals(new ContractBillInfo().getBOSType())) {
				ContractBillInfo contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
				String str = contractBill.getNumber();
				this.txtContractNo.setText(str + "");
				if (str != null)
					editData.setContractNo(str);
				BOSUuid conno = contractBill.getId();
				this.editData.setContractBillId(conno + "");
				this.editData.setCurProject(curProject);
				
			} else if ((BOSUuid.read(contractBillId).getType())
					.equals(new ContractWithoutTextInfo().getBOSType())) {
				ContractWithoutTextInfo text = (ContractWithoutTextInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
				String str = text.getNumber();
				this.txtContractNo.setText(str + "");
				if (str != null)
					editData.setContractNo(str);
				BOSUuid conno = text.getId();

				this.editData.setContractBillId(conno + "");
					
				// 带出无文本合同工程项目
				this.editData.setCurProject(curProject);
					
			}
		}

		// 币别为空时设置币别为本位币
		if (this.editData.getCurrency() == null) {
			if (baseCurrency != null) {
				this.editData.setCurrency(baseCurrency);				
				this.editData.setExchangeRate(FDCConstants.ONE);
			}
		}
		if (this.editData.getCurrency() != null) {
			if (editData.getCurrency().getId().toString().equals(baseCurrency.getId().toString())) {
				// 是本位币时将汇率选择框置灰
				txtExchangeRate.setValue(FDCConstants.ONE);
				txtExchangeRate.setRequired(false);
				txtExchangeRate.setEditable(false);
				txtExchangeRate.setEnabled(false);
				isBaseCurrency = true;
			}
			else{
				isBaseCurrency = false;
//				canModifyAmount = false;
//				btnInputCollect.setEnabled(canModifyAmount);
//				this.prmtCurrency.setEnabled(false);
//				this.txtExchangeRate.setEnabled(true);
				this.txtLocalAmt.setValue(editData.getLocalAmt());
			}
		}
		
		//计划项目
//		this.prmtFpItem.setQueryInfo("com.kingdee.eas.fm.fpl.FpItemQuery");
		
		// 初始化分录
		if (editData.getUrgentDegree() == null)
			editData.setUrgentDegree(UrgentDegreeEnum.NORMAL);
		
		

		super.loadFields();
		
		// 更新本位币显示
		if(editData.getLocalAmt()==null){
			setAmount();
		}

		// 设置付款次数为合同的付款次数 从付款单中过滤
//		PaymentBillCollection payCol = (PaymentBillCollection)initData.get("PaymentBillCollection");		
//		if(payCol!=null){
//			editData.setPayTimes(payCol.size());
//		}
//		
//		((ICell)bindCellMap.get(PaymentBillContants.PAYTIMES)).setValue(payCol.size()+"");
//		
		/*
		 * // 隐藏以前的kdtEntries用自己的kdtable替代 Rectangle kdtRectangle =
		 * kdtEntries.getBounds(); kdtEntries.setEnabled(false);
		 * kdtEntries.setVisible(false); kdtEntries = createPaymentBillTable();
		 * kdtEntries.setBounds(kdtRectangle); //
		 * pnlPayBillEntry.add(kdtEntries, null);
		 * pnlPayBillEntry.add(kdtEntries, new KDLayout.Constraints(10, 10, 850,
		 * 450, KDLayout.Constraints.ANCHOR_TOP |
		 * KDLayout.Constraints.ANCHOR_BOTTOM_SCALE |
		 * KDLayout.Constraints.ANCHOR_LEFT_SCALE |
		 * KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
		 *  // 多页签显示 sortPanel(); kdtEntries.getScriptManager().runAll(); //
		 * 避免在新增单据（未作修改）直接关闭时，出现保存提示
		 * 
		 * 
		 * this.storeFields(); this.initOldData(this.editData);
		 */
		prmtPayerAccountBank.setValue(editData.getPayerAccountBank());
		prmtPayerAccount.setValue(editData.getPayerAccount());
		txtPayeeAccountBank.setText(editData.getPayeeAccountBank());
		
		this.txtDayaccount.setEnabled(false);
		// addBalanceListener();
		setNumber();
		
		if (txtNumber.isEnabled()) {
			txtNumber.requestFocusInWindow();
		} else {
			pkBizDate.requestFocusInWindow();
		}
		
		refreshUITitle();

//		//
		prmtActFdcPayeeName.setRequired(false);
		if(!editData.isIsNeedPay()){
			prmtPayee.setRequired(true);
			
//			prmtFdcPayeeName.setRequired(false);
//			prmtActFdcPayeeName.setRequired(false);
//			txtPayeeBank.setRequired(false);
			//txtPayeeAccountBank.setRequired(false);			
			prmtSettlementType.setRequired(false);
			prmtPayerAccount.setRequired(false);
		}else{
//			txtPayeeAccountBank.setRequired(true);
			prmtPayerAccount.setRequired(true);
		}
		
//		if(editData.getIsEmergency().getValue()==1){
//			mergencyState.setSelectedItem(editData.getIsEmergency());
//		}
		
		FMClientHelper.setSelectObject(comboPayeeType, editData.getPayeeType());
		if (editData.getPayeeType() == null) {
			comboPayeeType.setSelectedItem(FMSysDefinedEnum.OTHER);
		}
		
		// 设置收款人
		String payeeId = editData.getPayeeID();
		String payeeNumber = editData.getPayeeNumber();
		String payeeName = editData.getPayeeName();
		handler.fillPayeeOrPayer(prmtPayee, payeeId, payeeNumber, payeeName);
		
		Object  obj = comboPayeeType.getSelectedItem();
		if(obj instanceof AsstActTypeInfo ){
			AsstActTypeInfo asstActTypeInfo = (AsstActTypeInfo)obj;
			if ("provider".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())||"person".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())){
//				prmtPayee.setEnabled(false);
			}
		}
		
		try {
			province_dataChanged( editData.getRecProvince());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//设置预算项目:最初EAS系统的资金计划是单独的一个模块，资金计划控制付款时是通过在付款单上加“计划项目”与资金计划模块的“计划项目”来关联，从而实现对付款单的控制，
		//现在资金计划通过预算系统来编制，而预算系统是通过关联“预算项目”来实现对付款单的控制的，所以需要将付款单上原来的“计划项目”改为现有预算系统中的“预算项目”
		//by cassiel 2010-09-29
		if(isFpItem){
			this.prmtFpItem.setValue(this.editData.getFpItem());
		}else{
			FDCDealFPOrBudgetItemUtil.loadFieldsBgItem(prmtFpItem, editData, FDCDealFPOrBudgetItemUtil.getBgItemProps());
		}
		
		//加载完数据后加上
		attachListeners(); 
	}
	
	
    protected void refreshUITitle() {
    	if(titleMain==null){
    		return;
    	}
    	
        String state = getOprtState();
        if (OprtState.ADDNEW.equals(state)) {
            billIndex = "";
            this.setUITitle(titleMain + " - " + titleNew);
        } else {
            if (OprtState.EDIT.equals(state)) {
                this.setUITitle(titleMain + " - " + titleModify + billIndex);
            } else {
                boolean isFromMsgCenter = false;
                Map uiContext = getUIContext();
                if (uiContext != null) {
                    isFromMsgCenter = Boolean.TRUE.equals(uiContext.get("isFromWorkflow"));
                }
                if (!isFromMsgCenter) {
                    this.setUITitle(titleMain + " - " + titleView + billIndex);
                }
            }
        }
    }
    
	/**
	 * 获取工作流的最新审批意见，在编辑界面的打回意见进行显示
	 * 
	 * @author:zhonghui_luo 2006-10-13 14:29:32
	 * 
	 */
	protected void getConceit() {
		EntityViewInfo vi = new EntityViewInfo();
		SorterItemInfo sortItemInfo = new SorterItemInfo("createTime");
		sortItemInfo.setSortType(SortType.DESCEND);
		vi.getSorter().add(sortItemInfo);
		FilterInfo fi = new FilterInfo();
		FilterItemCollection it = fi.getFilterItems();
		it.add(new FilterItemInfo("billId", this.editData.getId()));
		vi.setFilter(fi);
		// SelectorItemCollection selector = new SelectorItemCollection();
		// selector.add("opinion");
		// vi.getSelector().add(new SelectorItemInfo("opinion"));
		try {
			MultiApproveCollection c = MultiApproveFactory.getRemoteInstance()
					.getMultiApproveCollection(vi);
			Iterator iter = c.iterator();
			if (iter.hasNext()) {
				MultiApproveInfo info = (MultiApproveInfo) iter.next();
				String str = info.getOpinion();
				editData.setConceit(str);
			}
		} catch (BOSException e) {
			super.handUIException(e);
		}
	}	

	//设置精度
	protected void setPrecision(){
		CurrencyInfo currency = editData.getCurrency();	    	
    	Date bookedDate = (Date)editData.getBizDate();
    	
    	ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, currency.getId(),this.currentCompany,bookedDate);
		} catch (Exception e) {			
			e.printStackTrace();
			return ;
		} 
    	
    	int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
    	int exPrecision = curPrecision;
    	
    	if(exchangeRate!=null){
    		exPrecision = exchangeRate.getPrecision();
    	}
    		
    	txtExchangeRate.setPrecision(exPrecision);
    	txtAmount.setPrecision(curPrecision);
    	BigDecimal exRate =  editData.getExchangeRate();    	
    	txtExchangeRate.setValue(exRate);
    	txtAmount.setValue(editData.getAmount());
	}
	
	private String getSettleNumber() {
		StringBuffer sb = new StringBuffer();
		Object obj = f7SettleNumber.getData();
		if (obj == null) {
			return null;
		}

		if(obj instanceof Object[]) {
			Object[] billColl = (Object[]) obj;

			for (int i = 0; i < billColl.length; i++) {
				if(billColl[i] instanceof ChequeInfo){
					ChequeInfo receivableBill = (ChequeInfo) billColl[i];
					sb.append(receivableBill.getNumber());
				}
				if (i != billColl.length - 1) {
					sb.append(";");
				}
			}
		} else {
			sb.append(obj.toString());
		}
		return sb.toString();

	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		// 将分录内的数据存储到info
		// if(this.getBOTPViewStatus()==0){
		if (editData.getContractBillId() != null) {
			if ((BOSUuid.read(editData.getContractBillId()).getType())
					.equals(new ContractBillInfo().getBOSType())) {
				updateDynamicValue();
				kdtEntries.getScriptManager().runAll();
				PaymentBillClientUtils.getValueFromCell(editData, bindCellMap);
			}
		}
		// }
		
		editData.setSettlementNumber(getSettleNumber());
		
		if (prmtPayerAccountBank.getValue() != null) {
			editData.setPayerAccountBank((AccountBankInfo) prmtPayerAccountBank.getValue());
		}
		
		// 设置实际收款帐号
//		String actbankAccountNumber = getActRecAccountNumber();
//		editData.setActRecAccountBank(actbankAccountNumber);
		
		//设置预算项目:最初EAS系统的资金计划是单独的一个模块，资金计划控制付款时是通过在付款单上加“计划项目”与资金计划模块的“计划项目”来关联，从而实现对付款单的控制，
		//现在资金计划通过预算系统来编制，而预算系统是通过关联“预算项目”来实现对付款单的控制的，所以需要将付款单上原来的“计划项目”改为由预算系统的参数BG056来控制
		//by cassiel 2010-09-29
		if(isFpItem){
			this.editData.setFpItem((FpItemInfo)this.prmtFpItem.getValue());
		}else{
			FDCDealFPOrBudgetItemUtil.storeFieldsBgItem(prmtFpItem, editData, FDCDealFPOrBudgetItemUtil.getBgItemProps());
		}
		
		//设置收款帐号(王震PT032486修改ActRecAccountBank自有属性为关联属性)
		String bankAccountNumber = getAccountNumber();
		AccountBankInfo accountBank = getAccountBankByAccountNumber(bankAccountNumber);
		editData.setPayeeAccountBank(bankAccountNumber);
		editData.setActRecAccountBank(accountBank);
		//代理付款公司
		editData.setAgentPayCompany((CompanyOrgUnitInfo)prmtAgentCompany.getValue());

		editData.setPayeeAccountBankO(getAccountBankByAccountNumber(bankAccountNumber));
		if (editData.getPayeeAccountBankO() != null) {
			editData.setOppInnerAcct(editData.getPayeeAccountBankO().getInnerAcct());
		} else {
			editData.setOppInnerAcct(null);
		}
		
//		editData.setPayDate(DateTimeUtils.truncateDate(editData.getPayDate()));
		if (editData.getPayerAccount() != null) {
			if (editData.getPayerAccount().isIsBank()) {
				editData.setFundType(BizTypeEnum.BANK);
			} else if (editData.getPayerAccount().isIsCash()) {
				editData.setFundType(BizTypeEnum.CASH);
			} else {
				editData.setFundType(BizTypeEnum.OTHER);
			}
		}
		
		if (comboPayeeType.getSelectedItem() == null
				|| comboPayeeType.getSelectedItem() instanceof FMSysDefinedEnum) {

			editData.setPayeeType(null);
		} else {
			editData.setPayeeType((AsstActTypeInfo) comboPayeeType
					.getSelectedItem());
		}
//		editData.setAddProjectAmt(amtInContractOri);
		// editData.setFeeType((FeeTypeEnum)comboFeeType.getSelectedItem());
		
		super.storeFields();
		
		// 设置收款人
		// 设置收款人
		if (prmtPayee.getData() ==null){
			editData.setPayeeNumber(null);
			editData.setPayeeName(null);
			editData.setPayeeID(null);
		}
		else if (prmtPayee.getData() instanceof String) {

			editData.setPayeeNumber((String) prmtPayee.getData());
			editData.setPayeeName((String) prmtPayee.getData());

		} else if (prmtPayee.getData() instanceof DataBaseInfo) {

			DataBaseInfo info = (DataBaseInfo) prmtPayee.getData();
			if (info != null) {
				if (info.getId() != null) {
					editData.setPayeeID(info.getId().toString());
				}
				editData.setPayeeNumber(info.getNumber());
				editData.setPayeeName(info.getName());
			}
		}
		
//		editData.setBizDate(editData.getPayDate());
		editData.setBizDate(editData.getBizDate());
		
//		editData.setIsEmergency((IsMergencyEnum)mergencyState.getSelectedItem());

		
		//同步标准
		SettlementTypeInfo settType = (SettlementTypeInfo) prmtSettlementType
		.getData();
		if (settType != null&&settType.getNtType() != null) {
			if (NTTypeGroupEnum.PaymentCheque.equals(settType.getNtType()
					.getSuperGroup())
				) {//R100130-010
				Object o=f7SettleNumber.getData();
				if(o instanceof Object[]){
					Object[] chequeCol=(Object[])o;
					if(chequeCol!=null&&chequeCol.length>0&&chequeCol[0] instanceof ChequeInfo){
						ChequeInfo chequeInfo=(ChequeInfo)chequeCol[0];
						editData.setCheque(chequeInfo);
						editData.setSettlementNumber(chequeInfo.getNumber());
					}
				}else if(o instanceof ChequeInfo){
					editData.setCheque((ChequeInfo)o);
					editData.setSettlementNumber(((ChequeInfo)o).getNumber());
				}
				
			}
		}
		//当第一个人（一般是出纳人员与申请单生成付款单时不是同一人）修改时赋值
		if (this.editData.getCreator() == null)
			this.editData.setCreator((SysContext.getSysContext().getCurrentUserInfo()));
		if (this.editData.getCreateTime() == null)
			this.editData.setCreateTime(new Timestamp(System.currentTimeMillis()));
		
		
		if(this.txtLocalAmt.getBigDecimalValue()!=null&&this.txtLocalAmt.getBigDecimalValue().compareTo(FDCConstants.ZERO)!=0){
			BigDecimal localamount = this.txtLocalAmt.getBigDecimalValue().setScale(2,BigDecimal.ROUND_HALF_UP);
			//大写金额为本位币金额
			String cap = FDCClientHelper.getChineseFormat(localamount,false);
			txtcapitalAmount.setText(cap);
			this.editData.setCapitalAmount(cap);
		}else{
			txtcapitalAmount.setText(null);
		}
	}
	
    protected void comboPayeeType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }
	
	/**
	 * 描述：收款人类型变化时,设置收款人名称的属性 当收款人类型为其他时,则收款人名称可手工录入且F7选择为空
	 */
	protected void comboPayeeType_itemStateChanged(ItemEvent e)
			throws Exception {

		if (e.getStateChange() == 2) {
			return;
		}

		if (comboPayeeType.getSelectedItem() == null) {
			return;
		}

		prmtPayee.setValue(null);
		
		txtPayeeBank.setText(null);
		
		txtPayeeAccountBank.setValue(null);
		
//		txtPayeeAcctName.setText(null);

		if (comboPayeeType.getSelectedItem() instanceof AsstActTypeInfo) {

			AsstActTypeInfo info = (AsstActTypeInfo) comboPayeeType
					.getSelectedItem();
			prmtPayee.setSelector(null);
			prmtPayee.setFilterInfoProducer(null);
			GLf7Utils.assignSelector(prmtPayee, info, this);
			if (info.getAsstHGAttribute().equalsIgnoreCase("innerAccount")) {// 内部帐户，
																				// 不安公司隔离

				info.setIsForCompany(false);
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("closed", "0"));
				filter.getFilterItems().add(new FilterItemInfo("isUsed", "1"));
				EntityViewInfo evi = new EntityViewInfo();
				evi.setFilter(filter);
				prmtPayee.setEntityViewInfo(evi);
				prmtPayee.setCommitParser(new KDBizPromptBox.DefaultParser(
						"$simpleCode$;$number$;$name$", prmtPayee));
				txtPayeeAccountBank.setEnabled(false);
			}
			else if(info.getAsstHGAttribute().equalsIgnoreCase("bankAccount"))
			{
				prmtPayee.setCommitParser(new KDBizPromptBox.DefaultParser(
						"$simpleCode$;$number$;$name$", prmtPayee));
				txtPayeeAccountBank.setEnabled(false);
			}
			else
			{
				prmtPayee.setCommitParser(new KDBizPromptBox.DefaultParser(
						"$number$;$name$", prmtPayee));
				txtPayeeAccountBank.setEnabled(true);
			}
			prmtPayee.setDisplayFormat("$number$ $name$");

			//如果是供应商则不能修改
			Object  obj = comboPayeeType.getSelectedItem();
			if(obj instanceof AsstActTypeInfo ){
				AsstActTypeInfo asstActTypeInfo = (AsstActTypeInfo)obj;
				if ("provider".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())){				
					
					// 设置收款人
					if(editData.getActFdcPayeeName()!=null){
//						prmtPayee.setEnabled(false);
						String payeeId = editData.getActFdcPayeeName().getId().toString();
						String payeeNumber = editData.getActFdcPayeeName().getNumber();
						String payeeName = editData.getActFdcPayeeName().getName();
						handler.fillPayeeOrPayer(prmtPayee, payeeId, payeeNumber, payeeName);
					}else if("person".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())){
//						prmtPayee.setEnabled(false);
					}else{
						prmtPayee.setEnabled(true);
					}
				}else{
					prmtPayee.setEnabled(true);
				}
			}
		}

		else if (comboPayeeType.getSelectedItem() instanceof FMSysDefinedEnum) {
			ExtendParser parser = new ExtendParser(prmtPayee);
			prmtPayee.setEditFormat("$name$");
			prmtPayee.setCommitParser(parser);
			prmtPayee.setDisplayFormat("$name$");
			txtPayeeAccountBank.setEnabled(true);
		}
//		//因为initdatastatus()后此事件还会被触发2次,把原来的false状态设为true,，所以在此设置一次状态
//		if(!canModifyPayeeAccountBankTotal)
//		{
//		txtPayeeAccountBank.setEnabled(canModifyPayeeAccountBankTotal);
//		}
	}
	
	private String getAccountNumber() {
		String bankAccountNumber = null;
		
		if(txtPayeeAccountBank.isVisible()){
			if (txtPayeeAccountBank.getData() instanceof String
					|| txtPayeeAccountBank.getText() instanceof String) {
	
				bankAccountNumber = txtPayeeAccountBank.getText();
	
			} else if (txtPayeeAccountBank.getData() instanceof AccountBankInfo) {
	
				AccountBankInfo info = (AccountBankInfo) txtPayeeAccountBank
						.getData();
				if (info != null) {
					bankAccountNumber = info.getBankAccountNumber();
	
				}
			}
			else if((txtPayeeAccountBank.getData()  instanceof SupplierCompanyBankInfo || 
					txtPayeeAccountBank.getData()  instanceof CustomerCompanyBankInfo)) {
				ObjectBaseInfo obj = (ObjectBaseInfo) txtPayeeAccountBank.getData();
				bankAccountNumber = (String) obj.get("bankAccount");
			}
		}else{
			bankAccountNumber = txtPayeeAccountBank1.getText();
		}
		
		return bankAccountNumber;
	}


	private String getActRecAccountNumber() {
		String bankAccountNumber = null;
		if (prmtPayerAccountBank.getData() instanceof String
				|| prmtPayerAccountBank.getText() instanceof String) {

			bankAccountNumber = prmtPayerAccountBank.getText();

		} else if (prmtPayerAccountBank.getData() instanceof AccountBankInfo) {

			AccountBankInfo info = (AccountBankInfo) prmtPayerAccountBank.getData();
			if (info != null) {
				bankAccountNumber = info.getBankAccountNumber();

			}
		}
		return bankAccountNumber;
	}
	
	private AccountBankInfo getAccountBankByAccountNumber(String acctNumber) {
		if (FMHelper.isEmpty(acctNumber)) {
			return null;
		}
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("bankAccountNumber", acctNumber.trim()));
		try {
			AccountBankCollection banks = AccountBankFactory
					.getRemoteInstance().getAccountBankCollection(ev);
			if (banks != null && banks.size() > 0) {
				return banks.get(0);
			}
		} catch (BOSException e) {
			this.handUIException(e);
		}
		return null;
	}
	
	

	public void onShow() throws Exception {
		
		tHelper.getDisabledTables().add(this.getDetailTable());
		enableExportExcel(getDetailTable());
		
		// 显示info的数据到表格
		if (editData.getContractBillId() != null) {
			if ((BOSUuid.read(editData.getContractBillId()).getType())
					.equals(new ContractWithoutTextInfo().getBOSType())) {
				// final StyleAttributes sa = kdtEntries.getStyleAttributes();
				// sa.setLocked(true);
				// sa.setBackground(noEditColor);
				// btnInputCollect.setEnabled(false);
				// kdtEntries.setEnabled(false);
			}
		}
		super.onShow();
		
		detailHelper = new PaymentBillDetailHelper(this);
		detailHelper.createPaymentBillDetailTable();
		
		if (editData.getBillStatus() == null)
			return;
		if (txtNumber.isEnabled()) {
			txtNumber.requestFocusInWindow();
		} else {
			pkBizDate.requestFocusInWindow();
		}
		Boolean disableSplit = (Boolean) getUIContext().get("disableSplit");
		if (disableSplit != null && disableSplit.booleanValue()) {
			actionSplit.setVisible(false);
			actionSplit.setEnabled(false);
			
			actionSave.setVisible(false);
			actionSave.setEnabled(false);
		}
		if ((!getOprtState().equals(OprtState.ADDNEW) && !getOprtState()
				.equals(OprtState.EDIT))) {
//			final StyleAttributes sa = getDetailTable().getStyleAttributes();
//			sa.setLocked(true);
//			sa.setBackground(noEditColor);
//			getDetailTable().setEnabled(false);
//			getDetailTable().setEditable(false);
		}
		//actionEdit.setEnabled(actionEdit.isVisible());
		
		
		if(getOprtState().equals(OprtState.ADDNEW)){
			ContractWithoutTextInfo context = new ContractWithoutTextInfo();
			String contractBillId = this.editData.getContractBillId();
			if (contractBillId != null && contractBillId.trim().length() > 1) {
				if ((BOSUuid.read(contractBillId).getType()).equals(context.getBOSType())) {
					SelectorItemCollection sel=new SelectorItemCollection();
					sel.add("currency.id");
					sel.add("originalAmount");
					sel.add("amount");
					sel.add("actPaiedLocAmount");
					sel.add("actPaiedAmount");
					sel.add("supplier.*");
					sel.add("person.*");
					
					PayRequestBillInfo pay = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
					KDTable table = getDetailTable();
					BigDecimal localamount =FDCHelper.ZERO;
					BigDecimal amount=FDCHelper.ZERO;
					BigDecimal exchangeRate = toBigDecimal(txtExchangeRate.getNumberValue());
					
					BigDecimal actPayAmount=FDCHelper.ZERO;
					BigDecimal actPayLocAmount=FDCHelper.ZERO;
					FDCSQLBuilder _builder = new FDCSQLBuilder();
					_builder.appendSql(" select sum(pb.famount) actPayAmount,sum(pb.flocalAmount) actPayLocAmount from t_cas_paymentbill pb");
					_builder.appendSql(" where pb.fFdcPayReqID='"+pay.getId().toString()+"'");
					if(editData.getId()!=null){
						_builder.appendSql(" and pb.fid!='"+editData.getId().toString()+"'");
					}
					final IRowSet rowSet = _builder.executeQuery();
					while (rowSet.next()) {
						if(rowSet.getBigDecimal("actPayAmount")!=null){
							actPayAmount=rowSet.getBigDecimal("actPayAmount");
						}
						if(rowSet.getBigDecimal("actPayLocAmount")!=null){
							actPayLocAmount=rowSet.getBigDecimal("actPayLocAmount");
						}
					}
					
					table.getCell(4, 4).setValue(pay.getOriginalAmount().subtract(actPayAmount));
					localamount=pay.getAmount().subtract(actPayLocAmount);
					this.txtLocalAmt.setValue(localamount);
					this.editData.setLocalAmt(localamount);
					
					amount=localamount.divide(FDCHelper.toBigDecimal(exchangeRate),2,BigDecimal.ROUND_HALF_UP);	
					txtAmount.setValue(amount);
					this.editData.setAmount(amount);
					
					setProjectPriceInContract();

					if(this.prmtPayee.getValue()==null){
						AsstActTypeCollection asstActTypeColl = CasRecPayHandler.getAsstActTypeColl(null, false, false);
					     if(pay.getPerson()!=null){
					    	 for (int i = 0; i < asstActTypeColl.size(); i++) {
								if ("person".equalsIgnoreCase(asstActTypeColl.get(i).getAsstHGAttribute())) {
									this.comboPayeeType.setSelectedItem(asstActTypeColl.get(i));
								}
							 }
							 this.prmtPayee.setValue(pay.getPerson());
//							 this.prmtPayee.setEnabled(false);
					     }
					}
				}
			}
		}
		if(this.editData.getFdcPayReqID()!=null&&prmtCurrency.getValue()!=null){
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(" select pb.fid from t_cas_paymentbill pb");
			_builder.appendSql(" where pb.fFdcPayReqID='"+this.editData.getFdcPayReqID()+"'");
			if(editData.getId()!=null){
				_builder.appendSql(" and pb.fid!='"+editData.getId().toString()+"'");
			}
			final IRowSet rowSet = _builder.executeQuery();
			if (rowSet.size()>0) {
				this.prmtCurrency.setEnabled(false);
			}
		}
		this.actionViewBudget.setVisible(false);
	}

	/*
	 * 重载父类以添加自己的kdtable分录
	 * 
	 * 
	 */
	public void initUIContentLayout() {
		super.initUIContentLayout();
		pnlPayBillHeader.putClientProperty("OriginalBounds", new Rectangle(10,
				10, 870, 500));
		pnlPayBillEntry.putClientProperty("OriginalBounds", new Rectangle(10,
				10, 870, 500));
		tbpPaymentBill.add(pnlPayBillHeader, new KDLayout.Constraints(10, 10,
				850, 500, KDLayout.Constraints.ANCHOR_TOP
						| KDLayout.Constraints.ANCHOR_BOTTOM_SCALE
						| KDLayout.Constraints.ANCHOR_LEFT_SCALE
						| KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
		txtAmount.setPrecision(2);
		txtAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtAmount.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
		txtAmount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		
		txtLocalAmt.setPrecision(2);
		txtLocalAmt.setHorizontalAlignment(JTextField.RIGHT);
		txtLocalAmt.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
		txtLocalAmt.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		
		txtpaymentProportion.setPrecision(2);
		txtpaymentProportion.setRemoveingZeroInDispaly(false);
		txtpaymentProportion.setHorizontalAlignment(JTextField.RIGHT);
		
		txtcompletePrjAmt.setPrecision(2);
		txtcompletePrjAmt.setHorizontalAlignment(JTextField.RIGHT);
		txtcompletePrjAmt.setRemoveingZeroInDispaly(false);
		
		txtAllCompletePrjAmt.setPrecision(2);
		txtAllCompletePrjAmt.setRemoveingZeroInDispaly(false);
		
		txtAllInvoiceAmt.setPrecision(2);
		txtAllInvoiceAmt.setRemoveingZeroInDispaly(false);
		
		txtInvoiceAmt.setPrecision(2);
		txtInvoiceAmt.setRemoveingZeroInDispaly(false);
		
		txtExchangeRate.setRequired(true);
		ExtendParser parset = new ExtendParser(f7SettleNumber);
		f7SettleNumber.setCommitParser(parset);
		
	}

	/*
	 * 得到用于显示分录的table
	 */
	
	private boolean isFormContractQuery = false;
	
	private KDTable createPaymentBillTable() {
		isFormContractQuery = false;
		if(getUIContext().get("fromContractQuery") != null){
			isFormContractQuery = Boolean.valueOf(getUIContext().get("fromContractQuery").toString()).booleanValue();
		}
		
		logger.debug("初始化分录table:");
		// 6列1头部0行的表格
		KDTable table = new KDTable(8, 1, 0);
       
		// EXCEL风格的行列索引,测试用
//		 table.setHeadDisplayMode(KDTStyleConstants.HEAD_DISPLAY_EXCEL);
		// 隐藏行索引
		table.getIndexColumn().getStyleAttributes().setHided(true);
		table.setRefresh(false);
		table.getScriptManager().setAutoRun(false);
		StyleAttributes sa = table.getStyleAttributes();
		// 是否可编辑
		sa.setLocked(true);
		sa.setNumberFormat("###,##0.00");
		// 融合头部
		IRow headRow = table.getHeadRow(0);
//		headRow.getCell(0).setValue(PaymentBillClientUtils.getRes("payTable"));
		headRow.getCell(0).setValue("工程付款情况表");
		KDTMergeManager mm = table.getHeadMergeManager();
		mm.mergeBlock(0, 0, 0, 7, KDTMergeManager.SPECIFY_MERGE);
		
		BigDecimal temp = FDCHelper.ZERO;
		BigDecimal tempOri = FDCHelper.ZERO;
		if (this.editData.getPayPartAMatlAmt() != null)
			temp = this.editData.getPayPartAMatlAmt();
		//原币增加字段
		// 插入固定项
		initFixTable(table);
		if (editData.getContractBillId() != null) {
			if ((BOSUuid.read(editData.getContractBillId()).getType()).equals(new ContractBillInfo().getBOSType())) {
				updateDynamicValue();
				kdtEntries.getScriptManager().runAll();
				PaymentBillClientUtils.getValueFromCell(editData, bindCellMap);
			}
		}
		// 在第9行插入应扣款项分录
		int lstDeductTypeRowIdx = initDynamicTable(table);
		int lastRowIdx = table.getRowCount() - 1; // 最后一行
		
		// 检查是否是新增的付款单
//		try {
		PayRequestBillInfo info = (PayRequestBillInfo)initData.get("PayRequestBillInfo");
		if(info.getPayPartAMatlOriAmt() != null)
			tempOri = info.getPayPartAMatlOriAmt();
		if(FDCHelper.toBigDecimal(temp).compareTo(FDCHelper.ZERO)==0){
			tempOri = FDCHelper.ZERO;
		}
		PaymentBillCollection paymentColl =  (PaymentBillCollection)initData.get("PaymentBillCollection_paymentColl");
		Iterator iter = paymentColl.iterator();
		if (!iter.hasNext()) {
			ContractBillInfo con = new ContractBillInfo();
			String contractBillId = this.editData.getContractBillId();
			if (contractBillId != null && contractBillId.trim().length() > 1) {
				if ((BOSUuid.read(contractBillId).getType()).equals(con.getBOSType())) {
					ContractBillInfo contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
							
					if (contractBill.getAddPrjAmtPaid() != null) {
								// 增加工程款
						if ((contractBill.getAddPrjAmtPaid().compareTo(FDCHelper.ZERO)) == 0) {
							editData.setLstAddPrjAllPaidAmt(null);
									((ICell) bindCellMap.get(PaymentBillContants.LSTADDPRJALLPAIDAMT)).setValue(null);
									table.getCell(6, 2).setValue(null);
						} else {
							editData.setLstAddPrjAllPaidAmt(contractBill.getAddPrjAmtPaid());
							((ICell) bindCellMap.get(PaymentBillContants.LSTADDPRJALLPAIDAMT)).setValue(contractBill.getAddPrjAmtPaid());
							table.getCell(6, 2).setValue(contractBill.getAddPrjAmtPaid());
						}
					}
					
					if (contractBill.getPrjPriceInConPaid() != null) {
								// 合同内工程款
						if ((contractBill.getPrjPriceInConPaid().compareTo(FDCHelper.ZERO)) == 0) {
							editData.setLstPrjAllPaidAmt(null);
							((ICell) bindCellMap.get(PaymentBillContants.LSTPRJALLPAIDAMT)).setValue(null);
							table.getCell(4, 2).setValue(null);
						} else {
							editData.setLstPrjAllPaidAmt(contractBill.getPrjPriceInConPaid());
							((ICell) bindCellMap.get(PaymentBillContants.LSTPRJALLPAIDAMT)).setValue(contractBill.getPrjPriceInConPaid());
							table.getCell(4, 2).setValue(contractBill.getPrjPriceInConPaid());
						}
					}
					
					if (contractBill.getPaidPartAMatlAmt() != null) {
								// 甲供材
						if ((contractBill.getPaidPartAMatlAmt().compareTo(FDCHelper.ZERO)) == 0) {
							editData.setLstAMatlAllPaidAmt(null);
							((ICell) bindCellMap.get(PaymentBillContants.LSTAMATLALLPAIDAMT)).setValue(null);
							table.getCell(lstDeductTypeRowIdx + 1, 2).setValue(null);
						} else {
							editData.setLstAMatlAllPaidAmt(contractBill.getPaidPartAMatlAmt());
							((ICell) bindCellMap.get(PaymentBillContants.LSTAMATLALLPAIDAMT)).setValue(contractBill.getPaidPartAMatlAmt());
							table.getCell(lstDeductTypeRowIdx + 1, 2).setValue(contractBill.getPaidPartAMatlAmt());
						}
					}
					
					//变更签证
					BigDecimal amount = FDCHelper.ZERO;
					ContractChangeBillCollection collection =  (ContractChangeBillCollection)initData.get("ContractChangeBillCollection");
					ContractChangeBillInfo billInfo;
					for (Iterator itor = collection.iterator(); itor
									.hasNext();) {
						billInfo = (ContractChangeBillInfo) itor.next();
						if (billInfo.getAmount() != null)
							amount = amount.add(billInfo.getAmount());
					}
							
					// 设置变更签证金额
					if ((amount.compareTo(FDCHelper.ZERO)) == 0) {
						((ICell) bindCellMap
										.get(PaymentBillContants.CHANGEAMT))
										.setValue(null);
								editData.setChangeAmt(null);
					} else {
						((ICell) bindCellMap
									.get(PaymentBillContants.CHANGEAMT))
										.setValue(amount);
								editData.setChangeAmt(amount);
					}
							// 结算金额
					if(!isFormContractQuery){
						if (contractBill.getSettleAmt() != null) {
							if ((contractBill.getSettleAmt().compareTo(FDCHelper.ZERO)) == 0)
										((ICell) bindCellMap.get(PaymentBillContants.SETTLEAMT)).setValue(null);
							else
										((ICell) bindCellMap.get(PaymentBillContants.SETTLEAMT)).setValue(contractBill.getSettleAmt());
						}
					}else{
						if (contractBill.getSettleAmt() != null) {
							if ((contractBill.getSettleAmt().compareTo(FDCHelper.ZERO)) == 0)
										table.getCell(2, 7).setValue(null);
							else
								table.getCell(2, 7).setValue(contractBill.getSettleAmt());
						}
					}
							// editData.setSettleAmt(contractBill.getSettleAmt());
					//2009-1-20 不应该使用new BigDecimal(0),而应该用new BigDecimal("0")
					amount = FDCHelper.ZERO;
//					amount = new BigDecimal(0);

							// 根据结算单的状态来设置最新造价的值,已结算就为结算额
					if (!contractBill.isHasSettled()) {
								// 未最终结算,最新造价＝合同价＋补充合同价（需要进一步明确需求）＋该合同已审批的所有变更签证单的金额之和
						if (contractBill.getAmount() != null) {
							if (editData.getChangeAmt() != null)
								amount = contractBill.getAmount().add(
												editData.getChangeAmt());
							else
										amount = contractBill.getAmount();
						}else if (editData.getChangeAmt() != null)
									amount = editData.getChangeAmt();
						else
									amount = FDCHelper.ZERO;
					} else {
						if (contractBill.getSettleAmt() != null)
									amount = contractBill.getSettleAmt();
					}
					
					if ((amount.compareTo(FDCHelper.ZERO)) == 0) {
						editData.setLatestPrice(null);
						((ICell) bindCellMap
										.get(PaymentBillContants.LATESTPRICE))
										.setValue(null);
					} else {
						editData.setLatestPrice(amount);
						((ICell) bindCellMap
										.get(PaymentBillContants.LATESTPRICE))
										.setValue(amount);
					}
						
				}
			}
		}

		ContractBillInfo con = new ContractBillInfo();
		ContractWithoutTextInfo context = new ContractWithoutTextInfo();
		String contractBillId = this.editData.getContractBillId();
		if (contractBillId != null && contractBillId.trim().length() > 1) {
			try {
				if ((BOSUuid.read(contractBillId).getType()).equals(con
						.getBOSType())) {
					ContractBillInfo contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
					// 合同编号
					this.txtContractNo.setText(contractBill.getNumber());
					if (contractBill.getNumber() != null)
						editData.setContractNo(contractBill.getNumber());
					// 合同名称
					table.getCell(1, 1).setValue(contractBill.getName());
					// 合同造价
					/*if (contractBill.getAmount() != null) {
						if (((contractBill.getAmount())
								.compareTo(FDCHelper.ZERO)) == 0) {
							table.getCell(0, 6).setValue(null);
						} else
							table.getCell(0, 6).setValue(
									contractBill.getAmount());
					}*/
					// 单据审批后修订合同还取以前的造价
					
					if(!isFormContractQuery){
						if(FDCHelper.toBigDecimal(info.getContractPrice(),2).compareTo(FDCHelper.ZERO)==0){
							table.getCell(0, 6).setValue(null);
						}else{
							table.getCell(0, 6).setValue(info.getContractPrice());
						}
					}else{
						if(FDCHelper.toBigDecimal(info.getContractPrice(),2).compareTo(FDCHelper.ZERO)==0){
							table.getCell(0, 7).setValue(null);
						}else{
							table.getCell(0, 7).setValue(info.getContractPrice());
						}
					}
					
				} else if ((BOSUuid.read(contractBillId).getType())
						.equals(context.getBOSType())) {
					ContractWithoutTextInfo text = (ContractWithoutTextInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
					this.txtContractNo.setText(text.getNumber());
					if (text.getNumber() != null)
						editData.setContractNo(text.getNumber());
					// 合同名称
					table.getCell(1, 1).setValue(text.getName());
					// 合同造价
					if(!isFormContractQuery){
						if(FDCHelper.toBigDecimal(info.getContractPrice(),2).compareTo(FDCHelper.ZERO)==0){
							table.getCell(0, 6).setValue(null);
						}else{
							table.getCell(0, 6).setValue(info.getContractPrice());
						}
					}else{
						if(FDCHelper.toBigDecimal(info.getContractPrice(),2).compareTo(FDCHelper.ZERO)==0){
							table.getCell(0, 7).setValue(null);
						}else{
							table.getCell(0, 7).setValue(info.getContractPrice());
						}
					}
				}
			} catch (Exception e) {
				super.handUIException(e);
			} 
		}

		if (this.editData.getLstAMatlAllPaidAmt() != null) {
			if (this.editData.getLstAMatlAllPaidAmt().compareTo(FDCHelper.ZERO) == 0) {
				table.getCell(lstDeductTypeRowIdx + 1, 2).setValue(null);
			} else
				table.getCell(lstDeductTypeRowIdx + 1, 2).setValue(
						this.editData.getLstAMatlAllPaidAmt());
		}

		/**
		 * 
		 * 设置统计公式--进度款项,用sum内置函数来进行计算,用法类似于Excel 如sum(D3:D7);
		 * 
		 * 计算公式:
		 * 载至本期累计申请 = 载至上期累计申请
		 * 载至本期累计实付 = 载至上期累计实付 + 本期发生
		 * 
		 */
		IRow row = null;
		// 合同内工程款
		row = table.getRow(4);
		row.getCell(6).setExpressions("=sum(D5)");
		// row.getCell(5).setExpressions("=sum(D5,E5)");
		row.getCell(7).setExpressions("=sum(C5,F5)");
		
		// 预付款
		row = table.getRow(5);
		row.getCell(6).setExpressions("=sum(D6)");
		row.getCell(7).setExpressions("=sum(C6,F6)");
		// 工程款(被隐藏)
		row = table.getRow(6);
		row.getCell(6).setExpressions("=sum(D7)");
		// row.getCell(5).setExpressions("=sum(D6,E6)");
		row.getCell(7).setExpressions("=sum(C7,F7)");
		// 奖励
		row = table.getRow(7);
		row.getCell(6).setExpressions("=sum(D8)");
		// row.getCell(6).setExpressions("=sum(C7)");
		row.getCell(7).setExpressions("=sum(C8,F8)");
		// 违约
		row = table.getRow(8);
		row.getCell(6).setExpressions("=sum(D9)");
		row.getCell(7).setExpressions("=sum(C9,F9)");
		/*
		 * // 小计 row = table.getRow(7);
		 * row.getCell(2).setExpressions("=sum(c5,c7)");
		 * row.getCell(3).setExpressions("=sum(D5,D7)");
		 * row.getCell(4).setExpressions("=sum(E5,E7)");
		 * row.getCell(5).setExpressions("=sum(D8)"); //
		 * row.getCell(5).setExpressions("=sum(D7,E7)");
		 * row.getCell(6).setExpressions("=sum(C8,E8)");
		 */
		/*
		 * 甲供材款累计
		 */
		row = table.getRow(lstDeductTypeRowIdx + 1);
		//甲供材 本期申请 原币
		if ((tempOri.compareTo(FDCHelper.ZERO)) == 0)
			row.getCell(4).setValue(null);
		else
			row.getCell(4).setValue(tempOri);
		//甲供材 本期申请 本币
		if ((temp.compareTo(FDCHelper.ZERO)) == 0)
			row.getCell(5).setValue(null);
		else
			row.getCell(5).setValue(temp);
//		row.getCell(4).getStyleAttributes().setLocked(!isBaseCurrency);
		row.getCell(6).setExpressions("=D" + (lstDeductTypeRowIdx + 2));
		// row.getCell(5).setExpressions("=D"+(lstDeductTypeRowIdx+2)+"+E"+(lstDeductTypeRowIdx+2));
		row.getCell(7).setExpressions(
				"=(C" + (lstDeductTypeRowIdx + 2) + "+F"
						+ (lstDeductTypeRowIdx + 2) + ")");
		/**
		 * 实付款 因为要初化完后才能定位应扣款项小计的位置,故放到这里来计算. 实付款 实付款＝进度款小计－应扣款项小计-应扣甲供材
		 * 调整为:实付款＝合同内工程款+奖励-违约金-应扣款项小计-应扣甲供材 by sxhong 2007/09/28
		 */
		int paidRowIdx = lastRowIdx - 4;
		row = table.getRow(paidRowIdx);
		ICell cell = null;
		StringBuffer exp;
		for (char c = 'C'; c <= 'H'; c++) {
			cell = row.getCell(c - 'A');
			exp = new StringBuffer("=");
			exp.append(c).append(5).append("+"); // 合同内工程款
			exp.append(c).append(6).append("+"); //预付款
			exp.append(c).append(8).append("-"); // 奖励
			exp.append(c).append(9).append("-"); // 违约金
			exp.append(c).append(paidRowIdx - 1).append("-");// 甲供材
			exp.append(c).append(paidRowIdx); // 应扣款小计
			cell.setExpressions(exp.toString());
		}
		
		/**
		 * 
		 * 余款 余款＝最新造价－进度款小计 调整为 余款＝最新造价－合同内工程款 by sxhong 2007-9-28
		 * 
		 * 付款币别有可能是外币 公式调整为
		 * 余款 = 最新造价 - （合同内工程款*汇率）  kelvin_yang 2008-09-23
		 * 余款 = 最新造价 - （合同内工程款 + 预付款） 本币不能乘汇率 by hpw 2009-07-22
		 * 调整为 余款＝最新造价－实付款小计  by hpw 2009-7-24
		 * 余额=最新造价-进度款项截止本期累计实付 by cassiel_peng 2010-05-28
		 */
//		table.getCell(paidRowIdx + 1, 6).setExpressions("=F2-G8");
		
//		BigDecimal exRate = editData.getExchangeRate();
//		if(exRate==null || exRate == FDCHelper.ZERO){
//			exRate = FDCHelper.ONE;
//		}
//		BigDecimal exRate = FDCHelper.toBigDecimal(editData.getExchangeRate());
		
//		table.getCell(paidRowIdx + 1, 7).setExpressions("=F2-(G5*"+exRate+")");
//		table.getCell(paidRowIdx + 1, 7).setExpressions("=G2-(H5*"+exRate+")");
//		table.getCell(paidRowIdx + 1, 7).setExpressions("=G2 - (H5 + H6)");
		if(!isFormContractQuery){
			table.getCell(paidRowIdx + 1, 7).setExpressions("=G2 - H"+(lastRowIdx-3));
			table.getCell(paidRowIdx + 1, 7).setExpressions("=G2-H5");
		}else{
			table.getCell(paidRowIdx + 1, 7).setExpressions("=H2 - H"+(lastRowIdx-3));
			table.getCell(paidRowIdx + 1, 7).setExpressions("=H2-H5");
		}
		
		/**
		 * 
		 * 最新造价可能为零,不能直接使用计算公式的方法
		 * 
		 * 本次申请%=本期发生实付款/最新造价
		 * 累计申请%=本期累计申请/最新造价
		 * 
		 */
		row = table.getRow(lastRowIdx-1);
		if(!isFormContractQuery){
			if (editData.getLatestPrice() != null&& editData.getLatestPrice().compareTo(FDCHelper.ZERO) > 0) {
				//本次申请%
				exp = new StringBuffer("=(");
				exp.append("F").append(5).append("/");
				exp.append("G2)*100");
				row.getCell(1).setExpressions(exp.toString());
				//累计申请%
				exp = new StringBuffer("=(");
				exp.append("G").append(5).append("/");
				exp.append("G2)*100");
				row.getCell(3).setExpressions(exp.toString());
				
				// 应付申请，应付累计申请
				row = table.getRow(lastRowIdx);
				exp = new StringBuffer("=(");
				exp.append("F").append(lastRowIdx-3).append("/");
				exp.append("G2)*100");
				row.getCell(1).setExpressions(exp.toString());
				exp = new StringBuffer("=(");
				exp.append("G").append(lastRowIdx-3 ).append("/");
				exp.append("G2)*100");
				row.getCell(3).setExpressions(exp.toString());
			}
		}
		
		
		
		// 设置对齐方式
		sa = table.getColumn(1).getStyleAttributes();
		sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
		//项目名称,合同名称向左对齐
		table.getCell(0, 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		table.getCell(0, 7).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(1, 7).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(2, 7).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(1, 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		if(!isFormContractQuery){
			table.getCell(0, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			table.getCell(1, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			table.getCell(2, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		}
		table.getCell(2, 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(2, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		for (int i = 0; i < 2; i++) {
			row = table.getRow(lastRowIdx - i);
			for (int j = 1; j < 7; j += 2) {
				sa = row.getCell(j).getStyleAttributes();
				sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
		}
		kdtEntries = table;
		setTableCellColorAndEdit(true);
		table.setRefresh(true);
		// 自动调整大小
		table.setAutoResize(true);
		table.getScriptManager().setAutoRun(true);
		// 固定宽度
		table.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_FIXED);
		kdtEntries.getScriptManager().runAll();

		table.getRow(5).getStyleAttributes().setHided(!isAdvance());
		// 隐藏增加工程款行
		table.getRow(6).getStyleAttributes().setHided(true);

		// 去掉付款计划的格:
		for (int i = 4; i < table.getColumnCount(); i++) {
			StyleAttributes styleAttributes = table.getCell(lastRowIdx - 1, i)
					.getStyleAttributes();
			styleAttributes.setLocked(true);
			styleAttributes.setBackground(noEditColor);
		}
		table.getCell(lastRowIdx - 1, 1).getStyleAttributes().setLocked(true);
		table.getCell(lastRowIdx - 1, 1).getStyleAttributes().setBackground(noEditColor);
		if(isFormContractQuery){
			table.getCell(2,6).setValue("结算金额");
			table.getCell(4, 7).setValue(getPaiedAmt());
		}
		return table;
	}
	
	public BigDecimal getPaiedAmt(){
		BigDecimal paiedAmt = FDCHelper.ZERO;
		String cId = editData.getContractBillId();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select sum(famount) from t_cas_paymentbill where fbillstatus = 15 and fcontractbillid = ?");
		builder.addParam(cId);
		try {
			RowSet rs = builder.executeQuery();
			while(rs.next()){
				paiedAmt = rs.getBigDecimal(1);
			}
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paiedAmt;
	}
	
	/**
	 * 设置表格单元格的可编辑状态及颜色
	 * 
	 * @author sxhong Date 2006-9-28
	 */
	private void setTableCellColorAndEdit(boolean canModifyAmount) {

		// 设置计划付款行可以录入
		KDTable table = getDetailTable();
		IRow row;
		ICell cell;
		int lastRowIdx = table.getRowCount() - 1;
		table.setRefresh(false);
		table.setEditable(canModifyAmount && true);
		table.setEnabled(canModifyAmount && true);
		StyleAttributes sa;
		sa = table.getStyleAttributes();
		// sa.setLocked(false);
		sa.setBackground(Color.WHITE);
		/*
		 * 可编辑单元格
		 */
		// 本期发生
		boolean isTemp = editData.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.tempID);
		table.getCell(4, 4).getStyleAttributes().setLocked(isTemp?true:false);
		table.getCell(4, 5).getStyleAttributes().setLocked(canModifyAmount);
		//客户暂无要求，锁住
//		table.getCell(5, 4).getStyleAttributes().setLocked(false);
		table.getCell(5, 5).getStyleAttributes().setLocked(true);
		if(isBaseCurrency){
			table.getCell(6, 5).getStyleAttributes().setLocked(!canModifyAmount || false);
			// 甲供材
//			table.getCell(lastRowIdx - 4, 4).getStyleAttributes().setLocked(!canModifyAmount ||false);
			table.getCell(lastRowIdx - 4, 5).getStyleAttributes().setLocked(true);
			row = table.getRow(lastRowIdx - 1);
			row.getCell(1).getStyleAttributes().setLocked(!canModifyAmount ||false);
			row.getCell(3).getStyleAttributes().setLocked(!canModifyAmount ||false);
//			row.getCell(5).getStyleAttributes().setLocked(!canModifyAmount ||false);
			table.getCell(lastRowIdx, 6).getStyleAttributes().setLocked(!canModifyAmount ||false);
		}
		else{
//			table.getCell(5, 5).getStyleAttributes().setLocked(true);
			// 甲供材
			table.getCell(lastRowIdx - 4, 5).getStyleAttributes().setLocked(true);
			row = table.getRow(lastRowIdx - 1);
			row.getCell(1).getStyleAttributes().setLocked(true);
			row.getCell(3).getStyleAttributes().setLocked(true);
			row.getCell(5).getStyleAttributes().setLocked(true);
			table.getCell(lastRowIdx, 6).getStyleAttributes().setLocked(true);
		}
		table.getCell(lastRowIdx - 4, 6).getStyleAttributes().setLocked(true);
		/*
		 * 颜色
		 */
		for (int i = 4; i < table.getRowCount() - 2; i++) {
			row = table.getRow(i);
			for (int j = 2; j < table.getColumnCount(); j++) {
				cell = row.getCell(j);
				sa = cell.getStyleAttributes();
				sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
				if (sa.isLocked()) {
					sa.setBackground(noEditColor);
				}
			}
		}
		if(!isFormContractQuery){
			table.getCell(0, 6).getStyleAttributes().setBackground(noEditColor);
			table.getCell(1, 6).getStyleAttributes().setBackground(noEditColor);
			table.getCell(2, 6).getStyleAttributes().setBackground(noEditColor);
		}else{
			table.getCell(0, 7).getStyleAttributes().setBackground(noEditColor);
			table.getCell(1, 7).getStyleAttributes().setBackground(noEditColor);
			table.getCell(2, 7).getStyleAttributes().setBackground(noEditColor);
		}
		
		table.getCell(2, 1).getStyleAttributes().setBackground(noEditColor);
		table.getCell(2, 3).getStyleAttributes().setBackground(noEditColor);
		table.getCell(lastRowIdx, 1).getStyleAttributes().setBackground(noEditColor);
		table.getCell(lastRowIdx, 3).getStyleAttributes().setBackground(noEditColor);

		// 付款次数
		table.getCell(2, 1).getStyleAttributes().setBackground(noEditColor);
		table.setRefresh(true);
		btnInputCollect.setEnabled(canModifyAmount && !STATUS_VIEW.equals(this.oprtState));
		table.getCell(lastRowIdx - 1, 3).getStyleAttributes().setLocked(true);
		table.getCell(lastRowIdx - 1, 3).getStyleAttributes().setBackground(noEditColor);
		// table.repaint();
		// table.refresh();
		
		if(this.editData.getFdcPayReqID()!=null&&prmtCurrency.getValue()!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("currency.id");
			try {
				PayRequestBillInfo pay = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
				CurrencyInfo currency=(CurrencyInfo)prmtCurrency.getValue();
				if(!pay.getCurrency().getId().toString().equals(currency.getId().toString())){
					table.getCell(4, 4).getStyleAttributes().setLocked(true);
				}else{
					table.getCell(4, 4).getStyleAttributes().setLocked(false);
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 初始化表格的固定部分,初始化的时候加入绑定信息到bindCellmap
	 * 
	 */
	private void initFixTable(KDTable table) {
		// 添加15行,在第9行插入应扣款项分录
		table.addRows(15);
		IRow row;
		ICell cell;
		// 第一列
		// BindCellUtil.bindCell(table, rowIndex, colIndex, name, valueName,
		// valueProperty, bindCellMap)
		// 项目名称
		String str = PaymentBillClientUtils.getRes("projectName");
		if (editData.getCurProject() != null) {
			String name = null;
			if (curProject.getFullOrgUnit()!= null) {
				name = curProject.getFullOrgUnit().getName() + "\\"	+ editData.getCurProject().getDisplayName();				
			} else
				name = editData.getCurProject().getDisplayName();
			name = name.toString().replace('_', '\\');
			table.getCell(0, 1).setValue(name);
			table.getCell(0, 1).getStyleAttributes().setNumberFormat("@");
		}
		table.getCell(0, 0).setValue(str);

		// 合同名称
		str = PaymentBillClientUtils.getRes("contractName");
		table.getCell(1, 0).setValue(str);
		table.getCell(1, 1).getStyleAttributes().setNumberFormat("@");
		// table.getCell(1,1).setValue(contactName);
		// PaymentBillClientUtils.bindCell(table, 1, 0, "合同名称",
		// PaymentBillContants.CONTRACTNAME, bindCellMap);
		// 变更签证金额
		str = PaymentBillClientUtils.getRes("changeAmount");
		if (editData.getChangeAmt() != null) {
			if ((editData.getChangeAmt().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(2, 1).setValue(null);
			else
				table.getCell(2, 1).setValue(this.editData.getChangeAmt());
		}
		PaymentBillClientUtils.bindCell(table, 2, 0, str,PaymentBillContants.CHANGEAMT, bindCellMap, true);
		// 付款次数
		//如果是从合同查询界面，则不显示付款次数2010-06-18
		if(!isFormContractQuery){
			str = PaymentBillClientUtils.getRes("payTimes");
			PaymentBillClientUtils.bindCell(table, 3, 0, str,PaymentBillContants.PAYTIMES, bindCellMap);
		}
		
		// 进度款项
		str = PaymentBillClientUtils.getRes("scheduleAmt");
		table.getCell(4, 0).setValue(str);

		// 应扣款项下面的5行
		// 应扣甲供材款
		str = PaymentBillClientUtils.getRes("shouldSub");
		table.getCell(9, 0).setValue(str);
		// 实付款
		str = PaymentBillClientUtils.getRes("payAmt");
		table.getCell(10, 0).setValue(str);
		// 余款
		str = PaymentBillClientUtils.getRes("balance");
		table.getCell(11, 0).setValue(str);
		// 本期计划付款
		str = PaymentBillClientUtils.getRes("payThis");
		if (editData.getCurPlannedPayment() != null) {
			if ((editData.getCurPlannedPayment().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(12, 1).setValue(null);
			else
				table.getCell(12, 1).setValue(
						this.editData.getCurPlannedPayment());
		}
		PaymentBillClientUtils.bindCell(table, 12, 0, str,
				PaymentBillContants.CURPLANNEDPAYMENT, bindCellMap, true);
		// 本次申请%
		str = PaymentBillClientUtils.getRes("reqThis");
		PaymentBillClientUtils.bindCell(table, 13, 0, str,
				PaymentBillContants.CURREQPERCENT, bindCellMap);
		//应付申请
		table.getCell(14, 0).setValue("应付申请%");
		//累计应付申请
		table.getCell(14, 2).setValue("累计应付申请%");
		
		// 第二列
		// 进度款项
		// 合同内工程款
		str = PaymentBillClientUtils.getRes("amtInContract");
		table.getCell(4, 1).setValue(str);
		//预付款
		table.getCell(5, 1).setValue("预付款");
		// 增加工程款
		str = PaymentBillClientUtils.getRes("addAmt");
		table.getCell(6, 1).setValue(str);
		str = PaymentBillClientUtils.getRes("guerdon");
		table.getCell(7, 1).setValue(str);// 增加奖励单在6行，其后面的加一
		table.getCell(8, 1).setValue("违约金");// 用违约金替代小计
		/*
		 * // 小计 str = PaymentBillClientUtils.getRes("add"); table.getCell(7,
		 * 1).setValue(str);
		 */

		// 第三列
		// 结算金额
		str = PaymentBillClientUtils.getRes("subAmt");
		if(!isFormContractQuery){
			if (editData.getSettleAmt() != null) {
				if ((editData.getSettleAmt().compareTo(FDCHelper.ZERO)) == 0)
					table.getCell(2, 3).setValue(null);
				else
					table.getCell(2, 3).setValue(this.editData.getSettleAmt());
				PaymentBillClientUtils.bindCell(table, 2, 2, str,PaymentBillContants.SETTLEAMT, bindCellMap, true);
			}
		}else{
			if (editData.getSettleAmt() != null) {
				if ((editData.getSettleAmt().compareTo(FDCHelper.ZERO)) == 0)
					table.getCell(2, 7).setValue(null);
				else
					table.getCell(2, 7).setValue(this.editData.getSettleAmt());
			}
		}
	
		
		// 上期累计实付
		str = PaymentBillClientUtils.getRes("payLast");
		table.getCell(3, 2).setValue(str);
		// 本期欠付款
		str = PaymentBillClientUtils.getRes("loanThis");
		if (editData.getCurBackPay() != null) {
			if ((editData.getCurBackPay().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(12, 3).setValue(null);
			else
				table.getCell(12, 3).setValue(this.editData.getCurBackPay());
		}
		PaymentBillClientUtils.bindCell(table, 12, 2, str,PaymentBillContants.CURBACKPAY, bindCellMap, true);
		// BindCellUtil.bindCell(table, 11, 2, "累计申请％",
		// PaymentBillContants.ALLREQPERCENT, bindCellMap);
		// 累计申请％
		str = PaymentBillClientUtils.getRes("reqAll");
		table.getCell(13, 2).setValue(str);

		// 第四列
		// 上期累计申请
		str = PaymentBillClientUtils.getRes("reqLastAll");
		table.getCell(3, 3).setValue(str);

		// 第五列
		// 合同造价
		str = PaymentBillClientUtils.getRes("contraPrice");
		table.getCell(0, 5).setValue(str);
		// 最新造价
		str = PaymentBillClientUtils.getRes("latestPrice");
		if(!isFormContractQuery){
			if (editData.getLatestPrice() != null) {
				if ((editData.getLatestPrice().compareTo(FDCHelper.ZERO)) == 0)
					table.getCell(1, 6).setValue(null);
				else
					table.getCell(1, 6).setValue(this.editData.getLatestPrice());
			}
		}else{
			if (editData.getLatestPrice() != null) {
				if ((editData.getLatestPrice().compareTo(FDCHelper.ZERO)) == 0)
					table.getCell(1, 7).setValue(null);
				else
					table.getCell(1, 7).setValue(this.editData.getLatestPrice());
			}
		}
		PaymentBillClientUtils.bindCell(table, 1, 5, str,PaymentBillContants.LATESTPRICE, bindCellMap, true);
		// 本申请单已付金额
		str = PaymentBillClientUtils.getRes("payReq");
		BigDecimal temp = FDCHelper.ZERO;
		
		PaymentBillInfo billInfo = null;
		PaymentBillCollection payColHasPaid =  (PaymentBillCollection)initData.get("PaymentBillCollection_hasPaid");		
		for (Iterator itor = payColHasPaid.iterator(); itor.hasNext();) {
			billInfo = (PaymentBillInfo) itor.next();
			temp = temp.add(billInfo.getAmount());
		}
		
		
		if (temp != null) {
			if(!isFormContractQuery){
				if ((temp.compareTo(FDCHelper.ZERO)) == 0)
					table.getCell(2, 6).setValue(null);
				else
					table.getCell(2, 6).setValue(temp);
				
				PaymentBillClientUtils.bindCell(table, 2, 5, str,PaymentBillContants.PAYEDAMT, bindCellMap, true);
			}
//			else{
//				if ((temp.compareTo(FDCHelper.ZERO)) == 0)
//					table.getCell(2, 7).setValue(null);
//				else
//					table.getCell(2, 7).setValue(temp);
//			}
			
			
		}
	
		
		
		//本期发生(原币)
		
		str = PaymentBillClientUtils.getRes("accThis")+"(原币)";
		table.getCell(3, 4).setValue(str);
		
		// 本期发生
		str = PaymentBillClientUtils.getRes("accThis")+"(本币)";
		table.getCell(3, 5).setValue(str);
		
		if(isFormContractQuery){
			
			table.getColumn(2).getStyleAttributes().setHided(true);
			table.getColumn(3).getStyleAttributes().setHided(true);
			table.getColumn(4).getStyleAttributes().setHided(true);
			table.getColumn(5).getStyleAttributes().setHided(true);
			
		}
		
		/*
		 * // 付款计划 str = PaymentBillClientUtils.getRes("payPlan"); if
		 * (editData.getPaymentPlan() != null) { if
		 * ((editData.getPaymentPlan().compareTo(FDCHelper.ZERO)) == 0)
		 * table.getCell(11, 5).setValue(null); else table.getCell(11,
		 * 5).setValue(this.editData.getPaymentPlan()); }
		 * PaymentBillClientUtils.bindCell(table, 11, 4, str,
		 * PaymentBillContants.PAYMENTPLAN, bindCellMap, true);
		 */
		// 形象进度％
		str = PaymentBillClientUtils.getRes("imageShedule");
		if (editData.getImageSchedule() != null) {
			if ((editData.getImageSchedule().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(13, 6).setValue(null);
			else
				table.getCell(13, 6).setValue(this.editData.getImageSchedule());
		}
		PaymentBillClientUtils.bindCell(table, 13, 4, str,PaymentBillContants.IMAGESCHEDULE, bindCellMap, true);
		// 第六列
		// 本期累计申请
		str = PaymentBillClientUtils.getRes("reqAllThis");
		table.getCell(3, 6).setValue(str);
		// 第七列
		// 本期累计实付
		str = PaymentBillClientUtils.getRes("payAllThis");
		table.getCell(3, 7).setValue(str);

		// 特殊数据绑定进度款甲供材,实付款
		// 进度款项,
		row = table.getRow(4);
		cell = row.getCell(2);// 上期累计实付
		if (editData.getLstPrjAllPaidAmt() != null) {
			if ((editData.getLstPrjAllPaidAmt().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(4, 2).setValue(null);
			else
				table.getCell(4, 2).setValue(
						this.editData.getLstPrjAllPaidAmt());
		}
		PaymentBillClientUtils.bindCell(cell,PaymentBillContants.LSTPRJALLPAIDAMT, bindCellMap, true);
		// PaymentBillClientUtils.bindCell(cell,"contractBill.prjPriceInConPaid",
		// bindCellMap);
		cell = row.getCell(3);// 上期累计申请
		PaymentBillClientUtils.bindCell(cell,PaymentBillContants.LSTPRJALLREQAMT, bindCellMap, true);
		cell = row.getCell(4);// 发生额(原币)
		cell.getStyleAttributes().setLocked(!isBaseCurrency);
		if (editData.getAddProjectAmt() != null) {
			if ((editData.getAddProjectAmt().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(4, 4).setValue(null);
			else
				table.getCell(4, 4).setValue(editData.getAddProjectAmt());
		}
		PaymentBillClientUtils.bindCell(cell,PaymentBillContants.PROJECTPRICEINCONTRACTORI, bindCellMap, true);
		cell = row.getCell(5);// 发生额
//		cell.getStyleAttributes().setLocked(!isBaseCurrency);
		if (editData.getProjectPriceInContract() != null) {
			if ((editData.getProjectPriceInContract().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(4, 5).setValue(null);
			else
				table.getCell(4, 5).setValue(
						this.editData.getProjectPriceInContract());
		}
		PaymentBillClientUtils.bindCell(cell,PaymentBillContants.PROJECTPRICEINCONTRACT, bindCellMap, true);
		cell = row.getCell(6);// 累计申请
		PaymentBillClientUtils.bindCell(cell, PaymentBillContants.PRJALLREQAMT,
				bindCellMap, true);
		
		//预付款
		row = table.getRow(5);
		cell = row.getCell(2);// 预付款载至上期累计实付
		if (editData.getPrjPayEntry() != null && editData.getPrjPayEntry().getLstAdvanceAllPaid()!=null) {
			if (FDCHelper.ZERO.compareTo(editData.getPrjPayEntry().getLstAdvanceAllPaid()) == 0)
				table.getCell(5, 2).setValue(null);
			else
				table.getCell(5, 2).setValue(
						editData.getPrjPayEntry().getLstAdvanceAllPaid());
		}
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.LSTADVANCEALLPAID, bindCellMap, true);
		cell = row.getCell(3);// 预付款截至上期累计申请
		if (editData.getPrjPayEntry() != null && editData.getPrjPayEntry().getLstAdvanceAllReq()!=null) {
			if (FDCHelper.ZERO.compareTo(editData.getPrjPayEntry().getLstAdvanceAllReq()) == 0)
				table.getCell(5, 3).setValue(null);
			else
				table.getCell(5, 3).setValue(
						editData.getPrjPayEntry().getLstAdvanceAllReq());
		}
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.LSTADDPRJALLREQAMT, bindCellMap, true);
		if (editData.getPrjPayEntry() != null && editData.getPrjPayEntry().getAdvance()!=null) {
			if (FDCHelper.ZERO.compareTo(editData.getPrjPayEntry().getAdvance()) == 0)
				table.getCell(5, 4).setValue(null);
			else
				table.getCell(5, 4).setValue(
						editData.getPrjPayEntry().getAdvance());
		}
		cell = row.getCell(4);// 发生额（原币）
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.ADVANCE, bindCellMap, true);
		cell = row.getCell(5);// 发生额(本币)
		if (editData.getPrjPayEntry()!= null &&  editData.getPrjPayEntry().getLocAdvance()!=null) {
			if (FDCHelper.ZERO.compareTo(editData.getPrjPayEntry().getLocAdvance()) == 0)
				table.getCell(5, 5).setValue(null);
			else
				table.getCell(5, 5).setValue(editData.getPrjPayEntry().getLocAdvance());
		}
		cell = row.getCell(6);// 预付款截至本本期累计申请
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.ADVANCEALLREQ, bindCellMap, true);
		
		row = table.getRow(6);
		cell = row.getCell(2);// 合同内增加工程款累计实付
		if (editData.getLstAddPrjAllPaidAmt() != null) {
			if ((editData.getLstAddPrjAllPaidAmt().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(6, 2).setValue(null);
			else
				table.getCell(6, 2).setValue(
						this.editData.getLstAddPrjAllPaidAmt());
		}
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.LSTADDPRJALLPAIDAMT, bindCellMap, true);
		// PaymentBillClientUtils.bindCell(cell,"contractBill.addPrjAmtPaid",
		// bindCellMap);
		cell = row.getCell(3);// 上期累计申请
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.LSTADDPRJALLREQAMT, bindCellMap, true);
		cell = row.getCell(4);// 发生额（原币）
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.ADDPROJECTAMTORI, bindCellMap, true);
		
		cell = row.getCell(6);// 发生额(本币)
		cell.getStyleAttributes().setLocked(!isBaseCurrency);
		if (editData.getAddProjectAmt() != null) {
			if ((editData.getAddProjectAmt().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(6, 5).setValue(null);
			else
				table.getCell(6, 5).setValue(this.editData.getAddProjectAmt());
		}
//		PaymentBillClientUtils.bindCell(cell,
//				PaymentBillContants.ADDPROJECTAMT, bindCellMap, true);
		
		cell = row.getCell(6);// 累计申请
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.ADDPRJALLREQAMT, bindCellMap, true);
		
		// 甲供材
		row = table.getRow(9);
		cell = row.getCell(2);// 累计实付
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.LSTAMATLALLPAIDAMT, bindCellMap, true);
		cell = row.getCell(3);// 上期累计申请
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.LSTAMATLALLREQAMT, bindCellMap, true);
		cell = row.getCell(4);// 应扣甲供本期发生（原币）
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.PAYPARTAMATLAMTORI, bindCellMap, true);
		cell = row.getCell(5);//应扣甲供本期发生（本币）
//		cell.getStyleAttributes().setLocked(!isBaseCurrency);
		// table.getCell(7,4).setValue(this.editData.getPayPartAMatlAmt());
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.PAYPARTAMATLAMT, bindCellMap, true);
		cell = row.getCell(6);
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.PAYPARTAMATLALLREQAMT, bindCellMap, true);
		
		// 实付款(原币)
		row = table.getRow(10);
		cell = row.getCell(4);
		PaymentBillClientUtils.bindCell(cell, PaymentBillContants.CURPAID,bindCellMap, true);
		cell = row.getCell(5);               //实付款(本币)
		PaymentBillClientUtils.bindCell(cell, PaymentBillContants.CURPAIDLOCAL,
				bindCellMap, true);
		// paytimes
		if(bindCellMap.get(PaymentBillContants.PAYTIMES) != null){
			StyleAttributes sa = ((ICell) bindCellMap.get(PaymentBillContants.PAYTIMES)).getStyleAttributes();
			sa.setNumberFormat("###,##0");
			sa.setLocked(!isBaseCurrency);
			// 形象进度
			sa = ((ICell) bindCellMap.get(PaymentBillContants.IMAGESCHEDULE))
					.getStyleAttributes();
			sa.setLocked(!isBaseCurrency);

		}
		
		// 格式化付款次数
		
		// 融合表格
		KDTMergeManager mm = table.getMergeManager();
		// 融合前三行
		mm.mergeBlock(0, 1, 0, 3, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(1, 1, 1, 3, KDTMergeManager.SPECIFY_MERGE);
		//add by warship at 2010\05\09 将合同名称向左对齐
		table.getCell(1, 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		mm.mergeBlock(0, 4, 0, 5, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(1, 4, 1, 5, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(2, 4, 2, 5, KDTMergeManager.SPECIFY_MERGE);
		if(!isFormContractQuery){
			mm.mergeBlock(0, 6, 0, 7, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(1, 6, 1, 7, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(2, 6, 2, 7, KDTMergeManager.SPECIFY_MERGE);	
		}else{
			table.getCell(0,6).setValue("合同造价");
			table.getCell(1,6).setValue("最新造价");
			table.getCell(2,6).setValue("结算金额");
		}
		
//		
		
		// 付款次数
		// mm.mergeBlock(3, 1, 3, 2, KDTMergeManager.SPECIFY_MERGE);
		// 进度款项
		mm.mergeBlock(4, 0, 8, 0, KDTMergeManager.SPECIFY_MERGE);
		// mm.mergeBlock(4, 1, 4, 2, KDTMergeManager.SPECIFY_MERGE);
		// mm.mergeBlock(5, 1, 5, 2, KDTMergeManager.SPECIFY_MERGE);
		// mm.mergeBlock(6, 1, 6, 2, KDTMergeManager.SPECIFY_MERGE);
		if(isFormContractQuery){
//			mm.mergeBlock(3, 0, 3, 5);
		}
		// 应扣甲供材款
		mm.mergeBlock(9, 0, 9, 1, KDTMergeManager.SPECIFY_MERGE);
		// 实付款
		mm.mergeBlock(10, 0, 10, 1, KDTMergeManager.SPECIFY_MERGE);
		// 余款
		mm.mergeBlock(11, 0, 11, 6, KDTMergeManager.SPECIFY_MERGE);
		// 最後二行
		mm.mergeBlock(12, 5, 12, 7, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(13, 5, 13, 7, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(14, 5, 14, 7, KDTMergeManager.SPECIFY_MERGE);
		mm = null;
		
		
		if(isFormContractQuery){
			table.getRow(12).getStyleAttributes().setHided(true);
			table.getRow(13).getStyleAttributes().setHided(true);
			table.getRow(14).getStyleAttributes().setHided(true);
		}
	}

	/*
	 * 设定表格的格式
	 * 
	 */
	public static KDTDefaultCellEditor getCellNumberEdit() {
		KDFormattedTextField kdc = new KDFormattedTextField();
		kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		kdc.setPrecision(2);
		kdc.setMinimumValue(FDCHelper.MIN_VALUE);
		kdc.setMaximumValue(FDCHelper.MAX_VALUE);
		kdc.setHorizontalAlignment(JTextField.RIGHT);
		kdc.setSupportedEmpty(true);
		kdc.setVisible(true);
		kdc.setEnabled(true);
		// kdc.setRequired(false);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
		return editor;
	}

	/**
	 * 初始化分录的动态部分,即应扣款项, 扣款类型与金额都来自扣款单与申请单关联表(DeductBillOfPayReq)
	 * 根据付款申请单编码进行过滤。第一次单据关联时扣款全部携带，之后关联将不处理扣款项
	 */
	private int initDynamicTable(KDTable table) {
		// HashMap map=new HashMap();
		// 在第9行插入应扣款项分录,基础资料时用循环实现
		int base = 9;// 插入的基点
		int rows = 0;
		KDTMergeManager mm = table.getMergeManager();
		IRow row;
		String contractId = editData.getContractBillId();
		if (contractId == null || PayReqUtils.isConWithoutTxt(contractId)) {
			return base;
		}
		if (editData.getFdcPayReqID() != null) {
			// String payid = null;
			int times = 0;
			int timestemp = 0;
			// 是否已经有过其他付款单
			boolean had = false; 
			PayRequestBillEntryCollection payReqEntry = (PayRequestBillEntryCollection)initData.get("PayRequestBillCollection_hasPaid");
			if (payReqEntry.iterator().hasNext()) {
				PayRequestBillEntryInfo info = (PayRequestBillEntryInfo) (payReqEntry
						.iterator().next());
				if (info.getPayTimes() > 1)
					had = true;
				else {
					times = payReqEntry.size();
					if (times == 1) {
						had = false;
					} else {
						PayRequestBillEntryCollection payReqEntrytemp =  (PayRequestBillEntryCollection)initData.get("PayRequestBillCollection_c5");
						timestemp = payReqEntrytemp.size();
						if (timestemp == 0)
							had = false;
						else
							had = true;
					}
				}
			} else {
				PayRequestBillEntryCollection payReqEntrytemp =  (PayRequestBillEntryCollection)initData.get("PayRequestBillCollection_c5");
				timestemp = payReqEntrytemp.size();
				if (timestemp == 0)
					had = false;
				else
					had = true;
			}

			// 奖励
			GuerdonOfPayReqBillInfo geInfo = null;
			int count = 0;
			BigDecimal total = FDCHelper.ZERO;
			BigDecimal totalOri = FDCHelper.ZERO;
			
			GuerdonOfPayReqBillCollection c =  (GuerdonOfPayReqBillCollection)initData.get("GuerdonOfPayReqBillCollection");
			count = c.size();
			for (int i = 0; i < count; i++) {
				geInfo = c.get(i);
				if (geInfo.getAmount() != null)
					total = total.add(geInfo.getAmount());
				if(geInfo.getOriginalAmount() != null)
					totalOri = totalOri.add(geInfo.getOriginalAmount());
			}
			if (had == false) {
				if ((total.compareTo(FDCHelper.ZERO)) == 0)
					table.getCell(7, 5).setValue(null);
				else
					table.getCell(7, 5).setValue(total);
				if((totalOri.compareTo(FDCHelper.ZERO))==0)
					table.getCell(7, 4).setValue(null);
				else
					table.getCell(7, 4).setValue(totalOri);
			}
			
			// 合同内申请的奖励
			BigDecimal totalReq = FDCHelper.ZERO;
			GuerdonOfPayReqBillCollection deGePay = (GuerdonOfPayReqBillCollection)initData.get("GuerdonOfPayReqBillCollection_deGePay");
			count = deGePay.size();
			for (int temp = 0; temp < count; temp++) {
				geInfo = deGePay.get(temp);
				if (geInfo.getAmount() != null)
					totalReq = totalReq.add(geInfo.getAmount());
			}			
			if ((totalReq.compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(7, 3).setValue(null);
			else
				table.getCell(7, 3).setValue(totalReq);

			// 合同内已付的奖励
			BigDecimal totalPay = FDCHelper.ZERO;
			GuerdonOfPayReqBillCollection dePay =(GuerdonOfPayReqBillCollection)initData.get("GuerdonOfPayReqBillCollection_dePay");
			count = dePay.size();
			for (int temp = 0; temp < count; temp++) {
				geInfo = dePay.get(temp);
				if (geInfo.getAmount() != null)
					totalPay = totalPay.add(geInfo.getAmount());
			}
			
			//当前付款申请单已付的奖励
			GuerdonOfPayReqBillCollection getemp = (GuerdonOfPayReqBillCollection)initData.get("GuerdonOfPayReqBillCollection_getemp");
			count = getemp.size();
			for (int temp = 0; temp < count; temp++) {
				geInfo = getemp.get(temp);
				if (geInfo.getAmount() != null)
					totalPay = totalPay.subtract(geInfo.getAmount());
			}
			
			if ((totalPay.compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(7, 2).setValue(null);
			else
				table.getCell(7, 2).setValue(totalPay);

			/*
			 * 显示违约金 add by sxhong 2007/09/28
			 */
			BigDecimal amount = FDCHelper.ZERO;
			BigDecimal amountOri = FDCHelper.ZERO;
			BigDecimal lstPaidAmt = FDCHelper.ZERO;
			BigDecimal lstReqAmt = FDCHelper.ZERO;
			
			CompensationOfPayReqBillCollection comCol =  (CompensationOfPayReqBillCollection)initData.get("CompensationOfPayReqBillCollection");
			for (int i = 0; i < comCol.size(); i++) {
				CompensationOfPayReqBillInfo info = comCol.get(i);
				if (info.getAmount() != null) {
					if (info.getPayRequestBill().getId().toString().equals(
							editData.getFdcPayReqID())) {
						if (info.isHasPaid()
								&& info.getPaymentBill() != null
								&& info.getPaymentBill().getId() != null
								&& !(info.getPaymentBill().getId()
										.equals(editData.getId()))) {
							lstPaidAmt = info.getAmount().add(lstPaidAmt);
						}
						amount = amount.add(FDCHelper.toBigDecimal(info
								.getAmount()));
						amountOri = amountOri.add(FDCHelper.toBigDecimal(info
								.getOriginalAmount()));
					} else {
						if (info.isHasPaid()) {
							lstPaidAmt = info.getAmount().add(lstPaidAmt);
						}
					}
					// 付款单上期累计申请要包括本次申请的 by hpw 2009-07-18
					lstReqAmt = info.getAmount().add(lstReqAmt);
				}
			}				
				
			if (amount.compareTo(FDCHelper.ZERO) == 0) {
				amount = null;
			}
			if (amountOri.compareTo(FDCHelper.ZERO) == 0) {
				amountOri = null;
			}
			if (lstPaidAmt.compareTo(FDCHelper.ZERO) == 0) {
				lstPaidAmt = null;
			}
			if (lstReqAmt.compareTo(FDCHelper.ZERO) == 0) {
				lstReqAmt = null;
			}
			table.getCell(8, 2).setValue(lstPaidAmt);
			table.getCell(8, 3).setValue(lstReqAmt);

			if (had == false) {
				table.getCell(8, 5).setValue(amount);
				table.getCell(8, 4).setValue(amountOri);
			}
//			CompensationOfPayReqBillCollection comPay =  (CompensationOfPayReqBillCollection)initData.get("CompensationOfPayReqBillCollection_comPay");
//			for (int i = 0; i < comCol.size(); i++) {
//				CompensationOfPayReqBillInfo info = comCol.get(i);
//				if (info.getAmount() != null) {
//					if (info.getPayRequestBill().getId().toString()
//								.equals(editData.getFdcPayReqID())) {
//						amount = FDCHelper.subtract(amount,info.getAmount());
//						amountOri = FDCHelper.subtract(amountoRri,info.getOriginalAmount());
//					} else {
//						if (info.isHasPaid()) {
//							lstPaidAmt = info.getAmount().add(
//										lstPaidAmt);
//						}
//								// 付款单上期累计申请要包括本次申请的
//					}
//					lstReqAmt = info.getAmount().add(lstReqAmt);
//				}
//			}	

			// if((payid != null)&&(had == false)){
			// 从DeductOfPayReqBill内取出数据
			DeductOfPayReqBillInfo info = null;
			// 扣款
			DeductOfPayReqBillCollection deduCol = (DeductOfPayReqBillCollection)initData.get("DeductOfPayReqBillCollection");
			rows = deduCol.size();
			for (int i = 0; i < rows; i++) {
				info = deduCol.get(i);
				row = table.addRow(base + i);
				row.getCell(1).setValue(info.getDeductType().getName());
				row.getCell(1).getStyleAttributes().setNumberFormat("@");
				if (info.getAllPaidAmt() != null)
					row.getCell(2).setValue(info.getAllPaidAmt());
				if (info.getAllReqAmt() != null)
					row.getCell(3).setValue(info.getAllReqAmt());
				if ((this.editData.getFdcPayReqID() != null)
							&& (had == false)) {
					if (info.getAmount() != null) {
						if ((info.getAmount().compareTo(FDCHelper.ZERO)) == 0)
							row.getCell(5).setValue(null);
						else
							row.getCell(5).setValue(info.getAmount());
					}
					if (info.getOriginalAmount() != null) {
						if ((info.getOriginalAmount().compareTo(FDCHelper.ZERO)) == 0)
							row.getCell(4).setValue(null);
						else
							row.getCell(4).setValue(info.getOriginalAmount());
					}
				}
				
				// 合同内申请的扣款项
				DeductOfPayReqBillInfo infoPay = null;
				BigDecimal deAllReq = FDCHelper.ZERO;
				int rowsReq = 0;

				DeductOfPayReqBillCollection deduPay = (DeductOfPayReqBillCollection)info.get("DeductOfPayReqBillCollection_deduPay");
				rowsReq = deduPay.size();
				for (int temp = 0; temp < rowsReq; temp++) {
					infoPay = deduPay.get(temp);
					if (infoPay.getAmount() != null)
						deAllReq = deAllReq.add(infoPay.getAmount());
				}

				if ((deAllReq.compareTo(FDCHelper.ZERO)) == 0)
					row.getCell(3).setValue(null);
				else
					row.getCell(3).setValue(deAllReq);

					// 合同内已付的扣款项

				DeductOfPayReqBillInfo infoPaid = null;
				BigDecimal deAllPaid = FDCHelper.ZERO;
				int rowsPaid = 0;
				DeductOfPayReqBillCollection deduPay2 = (DeductOfPayReqBillCollection)info.get("DeductOfPayReqBillCollection_deduPay2");;
				rowsPaid = deduPay2.size();
				for (int temp = 0; temp < rowsPaid; temp++) {
					infoPaid = deduPay2.get(temp);
					if (infoPaid.getAmount() != null)
						deAllPaid = deAllPaid.add(infoPaid.getAmount());
				}

				//
				DeductOfPayReqBillCollection ctemp = (DeductOfPayReqBillCollection)info.get("DeductOfPayReqBillCollection_dctemp");
				Iterator itemp = ctemp.iterator();
				if (itemp.hasNext()) {
					DeductOfPayReqBillInfo infotemp = (DeductOfPayReqBillInfo) itemp
								.next();
					if (infotemp != null && infotemp.getAmount() != null) {
							deAllPaid = deAllPaid
								.subtract(infotemp.getAmount());
					}
				}

				if ((deAllPaid.compareTo(FDCHelper.ZERO)) == 0)
					row.getCell(2).setValue(null);
				else
					row.getCell(2).setValue(deAllPaid);
				row.getCell(6).setExpressions("=D" + (base + i + 1));
				if (((toBigDecimal(row.getCell(6).getValue()))
							.compareTo(FDCHelper.ZERO)) == 0)
					row.getCell(6).setValue(null);
					// row.getCell(5).setExpressions("=D"+(base+i+1)+"+E"+(base+i+1));
				if ((row.getCell(2).getValue() == null)
							&& (row.getCell(4).getValue() == null)) {
					row.getCell(7).setValue(null);
				} else
					row.getCell(7).setExpressions(
								"=C" + (base + i + 1) + "+F" + (base + i + 1));
			}
			

			// 最后一行小计
			int lastRowIdx = base + rows;
			row = table.addRow(lastRowIdx);
			row.getCell(1).setValue(PaymentBillClientUtils.getRes("add"));
			if(rows>0){
				if ((this.editData.getFdcPayReqID() != null)) {
					/*
					 * 小计计算
					 */
					StringBuffer exp;
					for (char ch = 'C'; ch <= 'H'; ch++) {
						exp = new StringBuffer("=sum(");
						exp.append(ch).append(base + 1).append(':');
						exp.append(ch).append(lastRowIdx);
						exp.append(')');
						row.getCell(ch - 'A').setExpressions(exp.toString());
						if (((toBigDecimal(row.getCell(ch - 'A').getValue()))
								.compareTo(FDCHelper.ZERO)) == 0)
							row.getCell(ch - 'A').setValue(null);
					}
				} else {
					row.getCell(2).setValue(null);
					row.getCell(3).setValue(null);
					row.getCell(4).setValue(null);
					row.getCell(5).setValue(null);
					row.getCell(6).setValue(null);
					row.getCell(7).setValue(null);
				}
				row.getCell(6).setExpressions("=D" + (lastRowIdx + 1));
				table.getCell(base, 0).setValue(
						PaymentBillClientUtils.getRes("amtDeduct"));
				mm.mergeBlock(base, 0, base + rows, 0,
						KDTMergeManager.SPECIFY_MERGE);
			}
			return lastRowIdx;
		} else {
			int lastRowIdx = base + rows;
			row = table.addRow(lastRowIdx);
			row.getCell(1).setValue(PaymentBillClientUtils.getRes("add"));
			row.getCell(2).setValue(null);
			row.getCell(3).setValue(null);
			row.getCell(4).setValue(null);
			row.getCell(5).setValue(null);
			row.getCell(6).setValue(null);
			row.getCell(7).setValue(null);
			table.getCell(base, 0).setValue(
					PaymentBillClientUtils.getRes("amtDeduct"));
			mm.mergeBlock(base, 0, base + rows, 0,
					KDTMergeManager.SPECIFY_MERGE);
			return lastRowIdx;
			// return base;
		}

	}

	// 反关闭付款申请单
	public void actionCancelClosePayReq_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.editData.getFdcPayReqNumber() != null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection item = filter.getFilterItems();
			// item.add(new FilterItemInfo("number",
			// this.editData.getFdcPayReqNumber()));
			item.add(new FilterItemInfo("id", this.editData.getFdcPayReqID()));
			filter.getFilterItems().add(
					new FilterItemInfo("hasClosed", Boolean.FALSE));
			view.setFilter(filter);
			try {
				PayRequestBillCollection c = PayRequestBillFactory
						.getRemoteInstance().getPayRequestBillCollection(view);
				Iterator iter = c.iterator();
				if (iter.hasNext()) {
					MsgBox.showWarning(this, PaymentBillClientUtils
							.getRes("hadUnClose"));
					SysUtil.abort();
				}
			} catch (BOSException exe) {
				super.handUIException(exe);// OprtState.EDIT);
			}
			IUIWindow testUI = null;
			int isYes = MsgBox.showConfirm2(this, PaymentBillClientUtils
					.getRes("cancleClose"));
			if (MsgBox.isYes(isYes)) {
				EntityViewInfo vi = new EntityViewInfo();
				FilterInfo fi = new FilterInfo();
				FilterItemCollection it = fi.getFilterItems();
				it
						.add(new FilterItemInfo("id", this.editData
								.getFdcPayReqID()));
				vi.setFilter(fi);
				try {
					PayRequestBillCollection c = PayRequestBillFactory
							.getRemoteInstance()
							.getPayRequestBillCollection(vi);
					Iterator iter = c.iterator();
					if (iter.hasNext()) {
						PayRequestBillInfo info = (PayRequestBillInfo) iter
								.next();
						UIContext uiContext = new UIContext(this);
						uiContext.put(UIContext.ID, info.getId());
						testUI = UIFactory
								.createUIFactory(UIFactoryName.NEWTAB)
								.create(
										"com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI",
										uiContext, null, "CLOSE");
						testUI.show();
					}
				} catch (BOSException ebos) {
					super.handUIException(ebos);
				}
			}
		}
		super.actionCancelClosePayReq_actionPerformed(e);
	}

	// 关闭付款申请单
	public void actionClosePayReq_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.editData.getFdcPayReqID() != null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection item = filter.getFilterItems();
			item.add(new FilterItemInfo("id", this.editData.getFdcPayReqID()));
			filter.getFilterItems().add(
					new FilterItemInfo("hasClosed", Boolean.TRUE));
			view.setFilter(filter);
			try {
				PayRequestBillCollection c = PayRequestBillFactory
						.getRemoteInstance().getPayRequestBillCollection(view);
				Iterator iter = c.iterator();
				if (iter.hasNext()) {
					MsgBox.showWarning(this, PaymentBillClientUtils
							.getRes("hadClose"));
					SysUtil.abort();
				}
			} catch (BOSException exe) {
				super.handUIException(exe);// OprtState.EDIT);
			}
			IUIWindow testUI = null;
			int isYes = MsgBox.showConfirm2(this, PaymentBillClientUtils
					.getRes("close"));
			if (MsgBox.isYes(isYes)) {
				EntityViewInfo vi = new EntityViewInfo();
				FilterInfo fi = new FilterInfo();
				FilterItemCollection it = fi.getFilterItems();
				it
						.add(new FilterItemInfo("id", this.editData
								.getFdcPayReqID()));
				vi.setFilter(fi);
				try {
					PayRequestBillCollection c = PayRequestBillFactory
							.getRemoteInstance()
							.getPayRequestBillCollection(vi);
					Iterator iter = c.iterator();
					if (iter.hasNext()) {
						PayRequestBillInfo info = (PayRequestBillInfo) iter
								.next();
						UIContext uiContext = new UIContext(this);
						uiContext.put(UIContext.ID, info.getId());
						try {
							testUI = UIFactory
									.createUIFactory(UIFactoryName.NEWTAB)
									.create(
											"com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI",
											uiContext, null, "CLOSE");
							testUI.show();
						} catch (BOSException exe) {
							super.handUIException(exe);// OprtState.EDIT);
						}
					}
				} catch (BOSException ebos) {
					super.handUIException(ebos);
				}
			}
		}
		super.actionClosePayReq_actionPerformed(e);
	}

	public void actionPay_actionPerformed(ActionEvent e) throws Exception {
		int isYes = MsgBox.showConfirm2(this, PaymentBillClientUtils
				.getRes("pay"));
		if (MsgBox.isYes(isYes)) {
			Set idSet = new HashSet();
			idSet.add(editData.getId());
			// 522收款时结算方式不能为空
			if (editData.getSettlementType() == null)
				throw new RecPayException(
						RecPayException.SETTLEMENTTYPE_CANNOT_BE_NULL);
			// 522付款时付款科目不能为空
			if (editData.getPayerAccount() == null)
				throw new RecPayException(
						RecPayException.PAYERACCOUNT_CANNOT_BE_NULL);
			PaymentBillFactory.getRemoteInstance().pay(idSet);
			syncDataFromDB();
			// actionPay.setEnabled(false);
		}
		super.actionPay_actionPerformed(e);
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		syncDataFromDB();
		setAmount();
		super.actionRefresh_actionPerformed(e);
	}

	protected void syncDataFromDB() throws Exception {
		// 由传递过来的ID获取值对象
		if (editData.getId() == null) {
			String s = EASResource.getString(FrameWorkClientUtils.strResource
					+ "Msg_IDIsNull");
			MsgBox.showError(s);
			SysUtil.abort();
		}
		IObjectPK pk = new ObjectUuidPK(BOSUuid.read(editData.getId()
				.toString()));
		setDataObject(getValue(pk));
		loadFields();
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		
		isMoving = true;
		if (txtNumber.isRequired() == true) {
			String txt = txtNumber.getText();
			if (txt == null || txt.trim().equals("")) {
				MsgBox.showError(this, PaymentBillClientUtils
						.getRes("noNumber"));
				SysUtil.abort();
			}
		}
		verifyBeforeSave();
		if (editData.getBillStatus() == null)
			editData.setBillStatus(BillStatusEnum.SAVE);
		PaymentBillClientUtils.getValueFromCell(editData, bindCellMap);
		if (getBOTPViewStatus() == 1) {
			getUIContext().put(BOTPViewS, new Integer(0));
		}
		
		if(PayReqUtils.isContractBill(editData.getContractBillId()) && isPartA && isPartAMaterialCon){
			paymentBillTableHelper.storePartAConList(editData);
		}
		
		FDCPaymentBillInfo fdcPayment = new FDCPaymentBillInfo();
		fdcPayment.setAllInvoiceAmt(txtAllInvoiceAmt.getBigDecimalValue());
		fdcPayment.setInvoiceAmt(txtInvoiceAmt.getBigDecimalValue());
		fdcPayment.setInvoiceNumber(txtInvoiceNumber.getStringValue());
		fdcPayment.setInvoiceDate((Date)pkInvoiceDate.getValue());
		
		super.actionSave_actionPerformed(e);
		
		// 付款单填写发票金额的要求 by cassiel 2010-10-7
		handInvoiceAmt(fdcPayment);
		detachListeners();
		txtAllInvoiceAmt.setValue(fdcPayment.getAllInvoiceAmt());
		txtInvoiceAmt.setValue(fdcPayment.getInvoiceAmt());
		txtInvoiceNumber.setStringValue(fdcPayment.getInvoiceNumber());
		pkInvoiceDate.setValue(fdcPayment.getInvoiceDate());
		attachListeners();
		
		setOprtStateByBillStatus();
		this.storeFields();
		this.initOldData(this.editData);
	}

	protected void verifyBeforeSave() throws Exception {
		boolean isMoreThanReqAmt = false;
		if (this.editData.getFdcPayReqNumber() == null) {
			MsgBox.showWarning(this, PaymentBillClientUtils.getRes("must"));
			SysUtil.abort();
		}
		FDCClientVerifyHelper.verifyEmpty(this, prmtCurrency);
		FDCClientVerifyHelper.verifyEmpty(this, prmtPayBillType);
		/**
		 * 检查原币金额与单元格内的发生额实付金额是否一致
		 * 改为检查原币金额(非本位币修改汇率时本币校验不准by hpw)
		 */
		Object cell = bindCellMap.get(PaymentBillContants.CURPAID);
		if (cell instanceof ICell) {
			Object value = ((ICell) cell).getValue();
			if (value != null) {
				try {
					BigDecimal cellAmount = FDCHelper.toBigDecimal(value,4);//先保留四位再保留两位保证相等 by hpw 
/*					if (txtAmount.getBigDecimalValue() != null
							&& txtAmount.getBigDecimalValue().signum() < 0) {
						MsgBox.showError(this, "原币金额为负不能保存！");
						SysUtil.abort();
						
					}*/
					// if(cellAmount.doubleValue()>0&&cellAmount.compareTo(txtAmount.getNumberValue())!=0){
					
					if(this.editData.getFdcPayReqID()!=null&&prmtCurrency.getValue()!=null){
						CurrencyInfo currency=(CurrencyInfo)prmtCurrency.getValue();
						SelectorItemCollection sel=new SelectorItemCollection();
						sel.add("currency.id");
						PayRequestBillInfo pay=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
						
						if(pay.getCurrency().getId().toString().equals(currency.getId().toString())){
							if(this.isAdvance()){
								paymentBillTableHelper.checkAdvance(editData, bindCellMap);
								if ((FDCHelper.toBigDecimal(cellAmount,2)
												.compareTo(FDCHelper.toBigDecimal(txtAmount.getBigDecimalValue(),2)) != 0)){
									MsgBox.showWarning(this, PaymentBillClientUtils
											.getRes("unequle"));
									SysUtil.abort();
								}
							} else {
								/*if (cellAmount.doubleValue() < 0
										|| (cellAmount.doubleValue() > 0 && (FDCHelper.toBigDecimal(cellAmount,2)
												.compareTo(FDCHelper.toBigDecimal(txtAmount.getBigDecimalValue(),2)) != 0))) {
									MsgBox.showWarning(this, PaymentBillClientUtils
											.getRes("unequle"));
									SysUtil.abort();
								}*/
								if (txtAmount.getBigDecimalValue() == null
										|| (PayReqUtils.isContractBill(editData.getContractBillId())&&FDCHelper
												.toBigDecimal(cellAmount, 2)
												.compareTo(
														FDCHelper
																.toBigDecimal(
																		txtAmount
																				.getBigDecimalValue(),
																		2)) != 0)) {
									MsgBox.showWarning(this, PaymentBillClientUtils
											.getRes("unequle"));
									SysUtil.abort();
								}
							}
						}
					}
				} catch (NumberFormatException e1) {
					super.handUIException(new Throwable("格式错误!"
							+ e1.getMessage()));
				}
			}
		}
		// 付款单原币金额大于零
/*		if (((new BigDecimal(0)).compareTo(txtAmount.getNumberValue())) >= 0) {
			// if(((this.editData.getAmount()).compareTo(new
			// BigDecimal(0)))<=0){
			MsgBox.showWarning(this, PaymentBillClientUtils
					.getRes("moreThanZero"));
			SysUtil.abort();
		}*/
		/*
		 * 比较付款单金额跟付款申请单金额
		 */
		if (this.editData.getFdcPayReqNumber() != null&&prmtCurrency.getValue()!=null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection items = filter.getFilterItems();
			// items.add(new FilterItemInfo("number",
			// this.editData.getFdcPayReqNumber()));
			items.add(new FilterItemInfo("id", this.editData.getFdcPayReqID()));
			view.setFilter(filter);
			try {
				PayRequestBillCollection c = PayRequestBillFactory
						.getRemoteInstance().getPayRequestBillCollection(view);
				Iterator iter = c.iterator();
				if (iter.hasNext()) {
					PayRequestBillInfo info = (PayRequestBillInfo) iter.next();
					//temp 申请单的金额
					BigDecimal temp = info.getOriginalAmount();
					//暂时改为本币
//						temp = FDCHelper.toBigDecimal(info.getAmount(),4);
					//查找该申请单已经付款金额,(不包括当前付款单)
					BigDecimal amou = FDCHelper.ZERO; //new BigDecimal(0);
					EntityViewInfo vi = new EntityViewInfo();
					FilterInfo fi = new FilterInfo();
					fi.getFilterItems().add(new FilterItemInfo("parent.id", info.getId().toString()));
					if(editData.getId()!=null){
						fi.getFilterItems().add(new FilterItemInfo("paymentBill.id", this.editData.getId().toString(),CompareType.NOTEQUALS));
					}					
					vi.setFilter(fi);
					try {
						PayRequestBillEntryCollection payReqEntry = 
							PayRequestBillEntryFactory.getRemoteInstance().getPayRequestBillEntryCollection(vi);
						Iterator itor=null;
						for (itor = payReqEntry.iterator(); itor.hasNext();) {
							PayRequestBillEntryInfo entry = (PayRequestBillEntryInfo) itor.next();
							FilterInfo paymentExist = new FilterInfo();
							paymentExist.getFilterItems().add(new FilterItemInfo("id",entry.getPaymentBill().getId().toString()));
							if (getBizInterface().exists(paymentExist)&&entry.getAmount() != null){
								amou = amou.add(entry.getAmount());
							}
						}
					} catch (BOSException e) {
						super.handUIException(e);
					}
					CurrencyInfo currency=(CurrencyInfo)prmtCurrency.getValue();
					if(info.getCurrency().getId().toString().equals(currency.getId().toString())){
						// 用绝对值来比较，因为允许无文本合同录入负数，Added by Owen_wen 2011-05-12
						if ((temp.setScale(2, BigDecimal.ROUND_HALF_UP).abs().compareTo(amou.add(txtAmount.getBigDecimalValue()).abs().setScale(2,
								BigDecimal.ROUND_HALF_UP))) == 1) {
							//付款金额小于申请单金额
							MsgBox.showInfo(this, PaymentBillClientUtils.getRes("lessthan"));
						} else if ((temp.setScale(2, BigDecimal.ROUND_HALF_UP).abs().compareTo(amou.add(txtAmount.getBigDecimalValue()).abs().setScale(2,
								BigDecimal.ROUND_HALF_UP))) == -1) {
							//付款金额大于申请单金额
							isMoreThanReqAmt = true;
							MsgBox.showWarning(this, PaymentBillClientUtils.getRes("morethan"));
						}
					}
				}
			} catch (Exception ebos) {
				super.handUIException(ebos);
			} finally{
				//付款金额大于申请单金额时仍提示成功,在些检查
				if(isMoreThanReqAmt){
					SysUtil.abort();
				}
			}
		}
		/*
		 * 比较主合同列表总金额是否与付款单申请金额相等
		 */
		if(PayReqUtils.isContractBill(editData.getContractBillId()) && isPartA && isPartAMaterialCon){
			paymentBillTableHelper.verifySave();
		}
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(this.getUIContext().get("ChequeReimBurse")!=null&&((Integer)this.getUIContext().get("ChequeReimBurse")).intValue()==1){
			storeFields();
			initOldData(editData);
			if(getEditUIMode(this).equals(UIFactoryName.MODEL)){
				getUIWindow().close();
			}
			UIContext uiContext = new UIContext(this.getUIContext().get(UIContext.OWNER));
			uiContext.put("PaymentBillForReimburse", editData);
			String chequeNumber=f7SettleNumber.getText();
			if(chequeNumber==null||chequeNumber.trim().equals("")){
				MsgBox.showInfo(this, "结算号不能为空！");
				SysUtil.abort();
			}else{
				String chequeID=null;
				Object oCheque=f7SettleNumber.getData();
				if(oCheque instanceof ChequeInfo){
					chequeID=((ChequeInfo)oCheque).getId().toString();
				}else{
					if(editData.getCheque()!=null)
						chequeID=editData.getCheque().getId().toString();
					else
						return;
				}
				/*
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("number",chequeNumber));
				EntityViewInfo evi=new EntityViewInfo();
				
				FilterInfo filter2 = new FilterInfo();
				filter2.getFilterItems().add(
						new FilterItemInfo("keepCompany.id", currentCompany.getId()
								.toString()));
				filter2.getFilterItems().add(
						new FilterItemInfo("company.id", currentCompany.getId()
								.toString()));
				filter2.getFilterItems().add(new FilterItemInfo("state", new Integer(ChequeStatusEnum.NEW_VALUE)));
				filter2.setMaskString("(#0 OR #1) AND #2");
				
				filter.mergeFilter(filter2, "AND");
				evi.setFilter(filter);
				ChequeCollection chequeColl=ChequeFactory.getRemoteInstance().getChequeCollection(evi);*/
				//if(chequeColl.size()>0){
					uiContext.put(UIContext.ID, chequeID);
					uiContext.put(UIModelDialogFactory.CANRESIZE, "true");
					IUIWindow uiWindow = null;
					// 创建UI对象并显示
					uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ChequeEditUI.class.getName(),
							uiContext, null, OprtState.EDIT);
					//uiWindow.getUIObject().getUIContentPane().setSize(new Dimension(800,800));
					//uiWindow.getUIObject().getUIContentPane().setPreferredSize(new Dimension(800,800));
					uiWindow.show();
				/*}else{
					//Set idSet=new HashSet(1);
					//idSet.add(editData.getId().toString());
					///((IPaymentBill) getBizInterface()).pay(idSet);
					//PaymentBillFactory.getRemoteInstance().chequeReimburse(chequeNumber, editData.getId().toString());
					MsgBox.showInfo(this, "支票已被使用，请选择其它支票！");
					SysUtil.abort();
					//this.getUIContext().put("ChequeReimBurse", new Integer(0));
				}*/
				//Component component = (Component) e.getSource();
				//FMClientHelper.showSuccessInfo(this, component);
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
				loadFields();
				setSave(true);
				setSaved(true);
			}

			return;
		}
		checkAmt();
		
		isMoving = true;

		//付款单提交时，是否检查合同拆分
		if(checkAllSplit){
			checkContractSplitState();			
		}
		if(checkPaymentAllSplit){
			String billId = editData.getId()==null?null:editData.getId().toString();
			Map param = new HashMap();
			param.put("isSimpleFinancial", Boolean.valueOf(isSimpleFinancial));
			param.put("isSimpleFinancialExtend", Boolean.valueOf(isSimpleFinancialExtend));
			param.put("isSimpleInvoice", Boolean.valueOf(isSimpleInvoice));
			param.put("isFinancial", Boolean.valueOf(isFinancial));
			param.put("isSeparate", Boolean.valueOf(isSeparate));
			
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("localAmt");
			PaymentBillInfo info = (PaymentBillInfo)getBizInterface().getValue(new ObjectUuidPK(billId), selector);
			if(FDCHelper.toBigDecimal(info.getLocalAmt(),2).compareTo(FDCHelper.toBigDecimal(txtLocalAmt.getBigDecimalValue(),2))!=0){
				FDCMsgBox.showWarning("此付款单对应的付款申请单存在多次付款，请提交后重新拆分！");
			}else{
				String msg = PaymentClientUtils.checkPaymentSplitState(billId,param);
				if(msg.length()!=0){
				FDCMsgBox.showWarning(this, msg);
				this.abort();
				}
			}
		}
		if (txtNumber.isRequired() == true) {
			String txt = txtNumber.getText();
			if (txt == null || txt.trim().equals("")) {
				MsgBox.showError(this, PaymentBillClientUtils
						.getRes("noNumber"));
				SysUtil.abort();
			}
		}
		this.setAmount();
		this.setProjectPriceInContract();
		if(this.isAdvance())
			this.setAdvance();
		if (editData.getBillStatus() == null)
			editData.setBillStatus(BillStatusEnum.SUBMIT);
		verifyBeforeSubmit();
		verifyBeforeSave();
		PaymentBillClientUtils.getValueFromCell(editData, bindCellMap);
		if (getBOTPViewStatus() == 1) {
			getUIContext().put(BOTPViewS, new Integer(0));
		}
		
		if(PayReqUtils.isContractBill(editData.getContractBillId()) && isPartA && isPartAMaterialCon){
			paymentBillTableHelper.storePartAConList(editData);
		}
		checkCanSubmit();
		
		FDCPaymentBillInfo fdcPayment = new FDCPaymentBillInfo();
		fdcPayment.setAllInvoiceAmt(txtAllInvoiceAmt.getBigDecimalValue());
		fdcPayment.setInvoiceAmt(txtInvoiceAmt.getBigDecimalValue());
		fdcPayment.setInvoiceNumber(txtInvoiceNumber.getStringValue());
		fdcPayment.setInvoiceDate((Date)pkInvoiceDate.getValue());
		
		super.actionSubmit_actionPerformed(e);
		
		// 付款单填写发票金额的要求 by cassiel 2010-10-7
		handInvoiceAmt(fdcPayment);
		detachListeners();
		txtAllInvoiceAmt.setValue(fdcPayment.getAllInvoiceAmt());
		txtInvoiceAmt.setValue(fdcPayment.getInvoiceAmt());
		txtInvoiceNumber.setStringValue(fdcPayment.getInvoiceNumber());
		pkInvoiceDate.setValue(fdcPayment.getInvoiceDate());
		attachListeners();
		
		editData.setBillStatus(BillStatusEnum.SUBMIT);
		setOprtStateByBillStatus();
		this.storeFields();
		this.initOldData(this.editData);
	}
	/**
	 * 付款单填写发票号与发票金额的要求 by cassiel 2010-10-7 <p>
	 * 
	 * 修改现有的控制逻辑：付款申请单的“发票号”和“发票金额”自动带到付款单后，允许用户修改发票号和发票金额，当付款单保存后，反写原付款申请单的“发票号”和“发票金额”。<p>
	 * 
	 * 并加上检验逻辑：比较付款单上的发票号、发票金额是否与付款申请单的一致，如有不同给出是否要保存的提示
	 * 
	 * @modified by Owen_wen 2010-10-26
	 * 
	 */
	private void handInvoiceAmt(FDCPaymentBillInfo fdcPayment) throws EASBizException, BOSException {
		/*
		// 发票号不同
		String numDiff = "付款单发票号与付款申请单不一致, 系统将自动修改付款申请单发票号，是否继续？"; 

		// 发票金额不同
		String amtDiff = "付款单发票金额与付款申请单不一致,系统将自动修改付款申请单发票金额， 是否继续？"; 
 		
		// 发票号和发票金额都不同
		String num_amt_Diff = "付款单的发票号和发票金额与付款申请单不一致，系统将自动修改付款申请单的发票号和发票金额，是否继续？"; 
		
		BigDecimal txtInvoiceAmt = this.txtInvoiceAmt.getBigDecimalValue();
		String txtInvoiceNum = this.txtInvoiceNumber.getStringValue();
		
		String payRequestBillId = this.editData.getFdcPayReqID();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("invoiceNumber");
		selector.add("invoiceAmt");
		selector.add("allInvoiceAmt");
		PayRequestBillInfo payReq = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(payRequestBillId), selector);
		
		boolean isNumSame = compareInvoiceNum(payReq);
		boolean isAmtSame = compareInvoiceAmt(payReq);
		
		int confirmResult = 0;
		if (!isNumSame && !isAmtSame){
			confirmResult = FDCMsgBox.showConfirm2New(this, num_amt_Diff);
			if (confirmResult == FDCMsgBox.NO)
				SysUtil.abort();
			else{
				payReq.setInvoiceNumber(txtInvoiceNum);
				payReq.setAllInvoiceAmt(this.txtAllInvoiceAmt.getBigDecimalValue());
				payReq.setInvoiceAmt(txtInvoiceAmt);
			}
		}
		
		if (!isNumSame && isAmtSame){
			confirmResult = FDCMsgBox.showConfirm2New(this, numDiff);
			if (confirmResult == FDCMsgBox.NO)
				SysUtil.abort();
			else{
				payReq.setInvoiceNumber(txtInvoiceNum);
			}
		}
		
		if (isNumSame && !isAmtSame){
			confirmResult = FDCMsgBox.showConfirm2New(this, amtDiff);
			if (confirmResult == FDCMsgBox.NO)
				SysUtil.abort();
			else{
				payReq.setAllInvoiceAmt(this.txtAllInvoiceAmt.getBigDecimalValue());
				payReq.setInvoiceAmt(txtInvoiceAmt);
			}
		}
		
		//只要发票号和发票金额只要有一个发生改变，更新付款申请单发票相关信息
		if (!isAmtSame || !isNumSame) 
			PayRequestBillFactory.getRemoteInstance().updatePartial(payReq, selector);		
		*/
//		FDCPaymentBillInfo fdcPayment = new FDCPaymentBillInfo();
//		fdcPayment.setAllInvoiceAmt(txtAllInvoiceAmt.getBigDecimalValue());
//		fdcPayment.setInvoiceAmt(txtInvoiceAmt.getBigDecimalValue());
//		fdcPayment.setInvoiceNumber(txtInvoiceNumber.getStringValue());
//		fdcPayment.setInvoiceDate(this.txt.getBigDecimalValue());
		FDCPaymentBillFactory.getRemoteInstance().updateInvoiceAmt(fdcPayment, (PaymentBillInfo)editData);

	}
	
	/**
	 * 比较付款单的发票号与付款申请单的是否一致
	 * @return 如果不同返回true，不同返回false
	 * @author owen_wen 2010-10-26
	 */
	private boolean compareInvoiceNum(PayRequestBillInfo payReq){
		String txtInvoiceNum = this.txtInvoiceNumber.getStringValue();
		String invoiceNum = payReq.getInvoiceNumber();
		
		if (StringUtils.isEmpty(txtInvoiceNum)){
			if (!StringUtils.isEmpty(invoiceNum))
				return false;
			else 
				return true;
		}else { // 两个都不为空时
			return txtInvoiceNum.equals(invoiceNum);
		}
	}
	
	/**
	 * 比较付款单的发票金额与付款申请单的是否一致
	 * @return 如果一致返回true，不同返回false
	 * @author owen_wen 2010-10-26
	 */
	private boolean compareInvoiceAmt(PayRequestBillInfo payReq){
		BigDecimal txtInvoiceAmt = this.txtInvoiceAmt.getBigDecimalValue();		
		BigDecimal invoiceAmt = payReq.getInvoiceAmt();
		
		if (txtInvoiceAmt == null){
			if (invoiceAmt != null) 
				return false;
			else
				return true;
		}else{
			if (invoiceAmt == null)
				return false;
			else 
				return (txtInvoiceAmt.compareTo(invoiceAmt) == 0);
		}
	}
	
	/**
	 * 参考FDCBillEditUI.checkCanSubmit
	 * @return
	 * @throws Exception
	 */
	protected void checkCanSubmit() throws Exception {

		if (editData.getId() == null) {
			return;
		}
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("billStatus");
		if(getBizInterface().exists(new ObjectUuidPK(editData.getId()))){
			PaymentBillInfo info = (PaymentBillInfo) getBizInterface().getValue(new ObjectUuidPK(editData.getId()), selector);
			if(BillStatusEnum.AUDITED.equals(info.getBillStatus())
					|| BillStatusEnum.AUDITING.equals(info.getBillStatus())){
				if(this.canModifyAccountAfterAuit&&BillStatusEnum.AUDITED.equals(info.getBillStatus())){
					return;
				}
				MsgBox.showWarning(this,"单据状态已经在审核中或者已审核，不能再提交!");
				SysUtil.abort();
			}
		}
		// 参数为不控制时不提示，其余情况仅提示不强制
		// "本次支付+本月累计实付"是否大于"计划项目"中的本月(付款单业务日期所在的月份)"最终批复金额"，
		// 如果大于，则提示用户："付款金额大于本月最终批复金额"，不做控制
		if (!"不控制".equals(paramConPayPlanCtrl)) {
			Date bookDate = (Date) pkbookedDate.getValue();
			Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
			Date lastDay = BudgetViewUtils.getLastDay(bookDate);
			String projectId = editData.getCurProject().getId().toString();
			BigDecimal localBudget = getLocalBudget(firstDay, lastDay,
					projectId);
			BigDecimal payedAmt = editData.getPayedAmt();
			BigDecimal curPaid = editData.getCurPaid();
			localBudget = localBudget == null ? FDCHelper.ZERO : localBudget;
			payedAmt = payedAmt == null ? FDCHelper.ZERO : payedAmt;
			curPaid = curPaid == null ? FDCHelper.ZERO : curPaid;
			if ((curPaid.add(payedAmt).compareTo(localBudget)) > 0) {
				MsgBox.showInfo("付款金额大于本月最终批复金额");
				// SysUtil.abort();
			}
		}
	}
	
	boolean isMoving = false;
	/**
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		super.actionFirst_actionPerformed(e);
		setOprtStateByBillStatus();
//		fetchInitData();
	}

	/**
	 * output actionPre_actionPerformed
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		super.actionPre_actionPerformed(e);
		setOprtStateByBillStatus();
//		fetchInitData();
	}

	/**
	 * output actionNext_actionPerformed
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		super.actionNext_actionPerformed(e);
		setOprtStateByBillStatus();
//		fetchInitData();
	}

	/**
	 * output actionLast_actionPerformed
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		super.actionLast_actionPerformed(e);
		setOprtStateByBillStatus();
//		fetchInitData();
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		PaymentBillPrintProvider data = new PaymentBillPrintProvider(editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	protected String getTDFileName() {
		return "/bim/fdc/finance/paymentBill";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.finance.app.PaymentBillForPrintQuery");
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		PaymentBillPrintProvider data = new PaymentBillPrintProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		//不允许用复制新增
//		super.actionCopy_actionPerformed(e);
//		actionSave.setEnabled(true);
//		setOprtStateByBillStatus();
//		
//		refreshUITitle() ;
	}
	
   

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkContractSplitState();
		super.actionAddNew_actionPerformed(e);
		actionSave.setEnabled(true);
		
		refreshUITitle() ;
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		
		int billState = editData.getBillStatus()!=null?editData.getBillStatus().getValue():10;
		int[] states =  new int[] { BillStatusEnum.SAVE_VALUE ,BillStatusEnum.SUBMIT_VALUE  };
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if (billState==(states[i])) {
				pass = true;
			}
		}
		if (!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
		
//		kdtEntries.getStyleAttributes().setLocked(false);
//		if(canModifyAmount){
		// getDetailTable().setEnabled(canModifyAmount);
		// getDetailTable().setEditable(canModifyAmount);
		//kdtEntries.getCell(4,5).getStyleAttributes().setLocked(!canModifyAmount
		// );
		// setTableCellColorAndEdit(canModifyAmount) ;
			// 去掉付款计划的格:
			int lastRowIdx = kdtEntries.getRowCount() - 1;
			for (int i = 4; i < kdtEntries.getColumnCount(); i++) {
				StyleAttributes styleAttributes = kdtEntries.getCell(lastRowIdx - 1, i)
						.getStyleAttributes();
				styleAttributes.setLocked(true);
				styleAttributes.setBackground(noEditColor);
			}
			kdtEntries.getCell(lastRowIdx - 1, 1).getStyleAttributes().setLocked(true);
			kdtEntries.getCell(lastRowIdx - 1, 1).getStyleAttributes().setBackground(noEditColor);
			
			kdtEntries.getCell(10,5).getStyleAttributes().setLocked(true);
		// kdtEntries.getCell(lastRowIdx -
		// 1,7).getStyleAttributes().setLocked(!canModifyAmount);
//		}
				
		super.actionEdit_actionPerformed(e);
		if (editData.getBillStatus() == null
				|| editData.getBillStatus().equals(BillStatusEnum.SAVE) || editData.getBillStatus().equals(BillStatusEnum.SUBMIT)) {
			actionSave.setEnabled(true);
		} else {
			actionSave.setEnabled(false);
		}
		setOprtStateByBillStatus();
		prmtCompany.setEditable(false);
		prmtFdcPayType.setEditable(false);
		prmtuseDepartment.setEditable(true);
		//prmtPayee.setEditable(false);
//		prmtActFdcPayeeName.setEditable(false);
		prmtCreator.setEditable(false);
		prmtAuditor.setEditable(false);
		prmtCashier.setEditable(false);
		prmtAccountant.setEditable(false);
		txtExchangeRate.setEditable(false);		
		
		Object newValue = prmtCurrency.getValue();
		if (newValue!=null && newValue instanceof CurrencyInfo) {
			BOSUuid srcid = ((CurrencyInfo) newValue).getId();
			if(!srcid.toString().equals(baseCurrency.getId().toString())){
				// 汇率也不可改
				txtExchangeRate.setEditable(true);				
			}			
		}
		
		Object  obj = comboPayeeType.getSelectedItem();
		if(obj instanceof AsstActTypeInfo ){
			AsstActTypeInfo asstActTypeInfo = (AsstActTypeInfo)obj;
			if ("provider".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())||"person".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())){
//				prmtPayee.setEnabled(false);
			}
		}
		
		province_dataChanged( editData.getRecProvince());
				
		refreshUITitle() ;
		
		if(isPartA && isPartAMaterialCon){
			paymentBillTableHelper.setPartAButtonStatus(getOprtState());
		}
		
		//R110225-039：付款单明细界面点击修改回导致收款账户信息丢失。原因是每次点修改时，都会跟据editData的内容重新加载一次。
		if (editData.getPayeeAccountBank() != null) {
			txtPayeeAccountBank.setText(editData.getPayeeAccountBank());
		}
		if(this.editData.getFdcPayReqID()!=null&&prmtCurrency.getValue()!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("currency.id");
			PayRequestBillInfo pay = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
			CurrencyInfo currency=(CurrencyInfo)prmtCurrency.getValue();
			if(!pay.getCurrency().getId().toString().equals(currency.getId().toString())){
				this.kdtEntries.getCell(4, 4).getStyleAttributes().setLocked(true);
			}else{
				this.kdtEntries.getCell(4, 4).getStyleAttributes().setLocked(false);
			}
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(" select pb.fid from t_cas_paymentbill pb");
			_builder.appendSql(" where pb.fFdcPayReqID='"+this.editData.getFdcPayReqID()+"'");
			if(editData.getId()!=null){
				_builder.appendSql(" and pb.fid!='"+editData.getId().toString()+"'");
			}
			final IRowSet rowSet = _builder.executeQuery();
			if (rowSet.size()>0) {
				this.prmtCurrency.setEnabled(false);
			}
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}

	protected void checkBeforeRemove() throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("paymentBill", editData.getId()));
		view.setFilter(filter);
		view.getSelector().add("id");
		CoreBillBaseCollection coll = PaymentSplitFactory.getRemoteInstance()
				.getCoreBillBaseCollection(view);
		Iterator iter = coll.iterator();
		if (iter.hasNext()) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("noRemove"));
			SysUtil.abort();
		}
	}

	/**
	 * output actionCreateFrom_actionPerformed
	 */
	public void actionCreateFrom_actionPerformed(ActionEvent e)
			throws Exception {
		this.getUIContext().put("contractbillid",
				this.editData.getContractBillId());
		IBTPBillEdit billEdit = this.getBillEdit();
		HashMap ctx = new HashMap();
		ctx.put("SourceBillType", "PayRequestBill");
		ctx.put("TargetBillType", "PaymentBill");
		// 传递合同id以进行付款申请单的过滤
		ctx.put("contractbillid", this.editData.getContractBillId());
		// billEdit.createFrom(ctx);
		billEdit.createFrom(null, ctx);
		this.getUIContext().put("BOTPViewStatus", new Integer(0));
		// super.actionCreateFrom_actionPerformed(e);
	}

	public int getBtpCreateFromType() {
		// return pullTypeSingleToSingle;
		return pullTypeSingleToMutil;
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		// super.actionTraceUp_actionPerformed(e);
//		check();
//		showPayreq();
		if(this.editData.getContractBillId()!=null&&this.editData.getFdcPayReqID()!=null){
			String id=this.editData.getFdcPayReqID();
			String className=PayRequestBillEditUI.class.getName();
			if(PayReqUtils.isConWithoutTxt(this.editData.getContractBillId())){
				id=this.editData.getContractBillId();
				className=ContractWithoutTextEditUI.class.getName();
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", id);
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(className, uiContext,null,OprtState.VIEW);
	        uiWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"无源单据！");
		}
	}
	/**
	 * 合同最终结算后，提交、审批时，比较已审批的付款单合计金额加上本次付款单金额大于合同结算价的大小
	 * by Cassiel_peng  2009-08-12		<p>
	 * 逻辑发生更改,如果合同实付款(不含本次) 加 本次付款单合同内工程款本期发生大于合同结算价，则给出相应提示
	 * by Cassiel_peng  2009-12-03 		<p>
	 */
	private void checkAmt() throws Exception{
		storeFields();
		Set paymentBillSet=new HashSet();
		paymentBillSet.add(this.editData);
		boolean isCanPass=PaymentBillClientUtils.checkForSaveSubmitAudit(paymentBillSet);
		if(!isCanPass){
			MsgBox.showError("合同实付款不能大于合同结算价【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
			SysUtil.abort();
		}
	}
	private void check() throws Exception {
		if (this.editData.getFdcPayReqID() == null) {
			MsgBox.showWarning(this, PaymentBillClientUtils
					.getRes("noSourceBill"));
			SysUtil.abort();
		} else {
			EntityViewInfo vi = new EntityViewInfo();
			FilterInfo fi = new FilterInfo();
			FilterItemCollection it = fi.getFilterItems();
			// it.add(new FilterItemInfo("number",
			// this.editData.getFdcPayReqNumber()));
			it.add(new FilterItemInfo("id", this.editData.getFdcPayReqID()));
			vi.setFilter(fi);
			PayRequestBillCollection c = PayRequestBillFactory
					.getRemoteInstance().getPayRequestBillCollection(vi);
			Iterator iter = c.iterator();
			if (!iter.hasNext()) {
				MsgBox.showWarning(this, PaymentBillClientUtils
						.getRes("noSourceBill"));
				SysUtil.abort();
			}
		}
	}

	private void showPayreq() throws Exception {
		Map uiContext = new HashMap();
		uiContext.put(UIContext.OWNER, this);
		String billID = editData.getFdcPayReqID().toString(); // 获取ID
		FilterInfo myFilter = new FilterInfo();
		myFilter.getFilterItems().add(new FilterItemInfo("id", billID));
		uiContext.put("MyFilter", myFilter); // 注意，这里的billID是44位长的BOSUuid，类型是String
		// IUIFactory uiFactory=UIFactory.createUIFactory(UIFactoryName.MODEL) ;
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow window = uiFactory.create(
				"com.kingdee.eas.fdc.contract.client.PayRequestFullListUI",
				uiContext, null);
		if (window instanceof UIModelDialog) {
			Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
			// ((JPanel)((UIModelDialog)window).getContentPane()).setSize(screenDim);
			int width = 1013;
			int height = 629;
			Dimension maxSize = new Dimension(1013, 629);
			if (screenDim.width > maxSize.getWidth()
					|| screenDim.height > maxSize.getHeight()) {
				screenDim = maxSize;
			}
			JPanel panel = ((JPanel) ((UIModelDialog) window).getContentPane());
			ListUI fullUI = (ListUI) window.getUIObject();
			// fullUI.setSize(new Dimension(690,300));
			fullUI.getMainTable().setBounds(
					new Rectangle(10, 10, width - 25, height - 50));
			fullUI.getMainTable().getColumn("id").getStyleAttributes()
					.setHided(true);
			KDTabbedPane tabPane = new KDTabbedPane();
			tabPane.addTab(PaymentBillClientUtils.getRes("payReqBill"), fullUI);
			panel.add(tabPane);
			panel.setPreferredSize(screenDim);
		} else {
			Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension maxSize = new Dimension(1013, 629);
			screenDim.setSize(maxSize);
			JPanel panel = ((JPanel) ((KDFrame) window).getContentPane());
			ListUI fullUI = (ListUI) window.getUIObject();
			fullUI.getMainTable().getColumn("id").getStyleAttributes()
					.setHided(true);
			KDTabbedPane tabPane = new KDTabbedPane();
			JPanel panel2 = new KDPanel();
			panel2.setLayout(new BorderLayout());
			panel2.add(fullUI, BorderLayout.CENTER);
			tabPane.addTab(PaymentBillClientUtils.getRes("payReqBill"), panel2);
			panel.setLayout(new BorderLayout());
			panel.add(tabPane, BorderLayout.CENTER);
			panel.setPreferredSize(screenDim);
		}
		window.show();
	}

	/*
	 * 添加的审核按钮
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		
		checkAmt();
		
		isMoving = true;
		//付款单提交时，是否检查合同拆分
		if(checkAllSplit){
			checkContractSplitState();			
		}
		//付款单提交时，是否检查合同拆分
		if(checkAllSplit){//历史有已提交未拆分的
			checkContractSplitState();			
		}
		super.actionAudit_actionPerformed(e);
		if (editData == null || editData.getId() == null) {
			return;
		}
		FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		List list = new ArrayList();
		list.add(editData.getId().toString());
		boolean isSuccess = PaymentBillFactory.getRemoteInstance().audit4FDC(list);
		if (isSuccess) {
			// actionAudit.setEnabled(false);
			prmtAuditor.setValue(SysContext.getSysContext().getCurrentUserInfo());
			FDCClientUtils.showOprtOK(this);
		}
		syncDataFromDB();
		setOprtStateByBillStatus();
		lockUIForViewStatus();
		if(this.editData.getId()!=null){
			AbstractSplitInvokeStrategy strategy = SplitInvokeStrategyFactory.createSplitStrategyByParam(this.editData.getId().toString(), this);
			strategy.invokeSplit();
		}
	}

	// 套打按钮
	public void actionTaoPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		List id = null;
		if (this.editData != null) {
			idList.add(editData.getId().toString());
		}
		if (idList == null || idList.size() == 0)
			return;
		com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(
				idList, getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
		super.actionTaoPrint_actionPerformed(e);
	}

	protected KDTable getDetailTable() {
		return kdtEntries;
		// return null;
	}

	public JPanel getPayBillEntryPanel() {
		return pnlPayBillEntry;
	}

	protected IObjectValue createNewData() {
		
		return null;
//		PaymentBillInfo req = new PaymentBillInfo();
//		req.setBillStatus(BillStatusEnum.SAVE);
//		req.setSourceType(SourceTypeEnum.FDC);
//		req.setSettleBizType(SettBizTypeEnum.PAYOUTSIDE);
//		req.setCompany(SysContext.getSysContext().getCurrentFIUnit());
//		req.setFdcPayeeType(FdcPayeeTypeEnum.OTHER);
//		req.setCreator(SysContext.getSysContext().getCurrentUserInfo());
//		req.setCreateTime(new Timestamp(System.currentTimeMillis()));
//		req.setUrgentDegree(UrgentDegreeEnum.NORMAL);
//		req.setDayaccount(FDCConstants.ZERO);
//		req.setPayDate(DateTimeUtils.truncateDate(new Date()));
//
//		if (baseCurrency != null) {
//			try {
//				req.setCurrency(CurrencyFactory.getRemoteInstance()
//								.getCurrencyInfo(new ObjectUuidPK(baseCurrency.getId())));
//			} catch (Exception e) {
//				handUIException(e);
//			}			
//			req.setExchangeRate(FDCConstants.ONE);
//		}
//		
//		String contractBillId = (String) getUIContext().get("contractBillId");
//		if (contractBillId != null && contractBillId.trim().length() > 1) {
//			try {
//				SelectorItemCollection selector = new SelectorItemCollection();
//				if ((BOSUuid.read(contractBillId).getType())
//						.equals(new ContractBillInfo().getBOSType())) {
//					// if(new
//					// BOSObjectTpye(BOSUuid.read(contractBillId).getType()) )
////					ContractBillInfo contractBill = ContractBillFactory
////							.getRemoteInstance().getContractBillInfo(
////									new ObjectUuidPK(BOSUuid
////											.read(contractBillId)));
//					// req.setContractBill(contractBill);
//					String str = contractBill.getNumber();
//					this.txtContractNo.setText(str + "");
//					if (str != null)
//						req.setContractNo(str);
//					BOSUuid conno = contractBill.getId();
//					req.setContractBillId(conno + "");
//					// 带出合同乙方
//					req.setActFdcPayeeName(SupplierFactory.getRemoteInstance()
//							.getSupplierInfo(
//									new ObjectUuidPK((contractBill.getPartB())
//											.getId())));
//					req.setFdcPayeeName(SupplierFactory.getRemoteInstance()
//							.getSupplierInfo(
//									new ObjectUuidPK((contractBill.getPartB())
//											.getId())));
//					// 带出工程项目
//					CurProjectInfo curProject = contractBill.getCurProject();
//					selector = new SelectorItemCollection();
//					selector.add("name");
//					curProject = CurProjectFactory.getRemoteInstance()
//							.getCurProjectInfo(
//									new ObjectUuidPK(curProject.getId()));// ,
//					// selector);
//					req.setCurProject(curProject);
//					BigDecimal amount = new BigDecimal("0");
//					FilterInfo filter = new FilterInfo();
//					filter.getFilterItems().add(
//							new FilterItemInfo("contractBill.id", contractBill
//									.getId()));
//					filter.getFilterItems().add(
//							new FilterItemInfo("state",
//									FDCBillStateEnum.AUDITTED_VALUE));
//					filter.getFilterItems().add(
//							new FilterItemInfo("state",
//									FDCBillStateEnum.VISA_VALUE));
//					filter.getFilterItems().add(
//							new FilterItemInfo("state",
//									FDCBillStateEnum.ANNOUNCE_VALUE));
//					filter.setMaskString("#0 and (#1 or #2 or #3)");
//					// 变更签证金额
//					EntityViewInfo view = new EntityViewInfo();
//					view.setFilter(filter);
//					view.getSelector().add("amount");
//					IObjectCollection collection = ContractChangeBillFactory
//							.getRemoteInstance()
//							.getContractChangeBillCollection(view);
//					ContractChangeBillInfo billInfo;
//
//					for (Iterator iter = collection.iterator(); iter.hasNext();) {
//						billInfo = (ContractChangeBillInfo) iter.next();
//						if (billInfo.getAmount() != null)
//							amount = amount.add(billInfo.getAmount());
//					}
//					req.setChangeAmt(amount);
//					// 结算金额
//					if (contractBill.getSettleAmt() != null) {
//						req.setSettleAmt(contractBill.getSettleAmt());
//					}
//					// 根据结算单的状态来设置最新造价的值
//					if (!contractBill.isHasSettled()) {
//						// 未最终结算,最新造价＝合同价＋补充合同价（需要进一步明确需求）＋该合同已审批的所有变更签证单的金额之和
//						if (contractBill.getAmount() != null) {
//							if (req.getChangeAmt() != null)
//								amount = contractBill.getAmount().add(
//										req.getChangeAmt());
//							else
//								amount = contractBill.getAmount();
//						} else if (req.getChangeAmt() != null)
//							amount = req.getChangeAmt();
//						else
//							amount = FDCHelper.ZERO;
//					} else {
//						if (contractBill.getSettleAmt() != null)
//							amount = contractBill.getSettleAmt();
//					}
//					req.setLatestPrice(amount);
//					req.setPayedAmt(new BigDecimal("0"));
//					req.setAmount(new BigDecimal("0"));
//
//				} else if ((BOSUuid.read(contractBillId).getType())
//						.equals(new ContractWithoutTextInfo().getBOSType())) {
//					ContractWithoutTextInfo text = ContractWithoutTextFactory
//							.getRemoteInstance().getContractWithoutTextInfo(
//									new ObjectUuidPK(BOSUuid
//											.read(contractBillId)));
//					String str = text.getNumber();
//					this.txtContractNo.setText(str + "");
//					if (str != null)
//						req.setContractNo(str);
//					BOSUuid conno = text.getId();
//					// req.setContractBill(conno);
//					req.setContractBillId(conno + "");
//					// 带出无文本合同收款单位
//					req.setFdcPayeeName(SupplierFactory.getRemoteInstance()
//							.getSupplierInfo(
//									new ObjectUuidPK((text.getReceiveUnit()
//											.getId()))));
//					req.setActFdcPayeeName(SupplierFactory.getRemoteInstance()
//							.getSupplierInfo(
//									new ObjectUuidPK((text.getReceiveUnit())
//											.getId())));
//					// 带出无文本合同工程项目
//					CurProjectInfo curProject = text.getCurProject();
//					selector = new SelectorItemCollection();
//					selector.add("name");
//					curProject = CurProjectFactory.getRemoteInstance()
//							.getCurProjectInfo(
//									new ObjectUuidPK(curProject.getId()));// ,
//					// selector);
//					req.setCurProject(curProject);
//					// String str=curProject.getName();
//					// String str=curProject.toString(
//				}
//			} catch (EASBizException e) {
//				super.handUIException(e);
//			} catch (BOSException e) {
//				super.handUIException(e);
//			} catch (UuidException e) {
//				super.handUIException(e);
//			}
//		}
//		// setAutoNumber();
//		return req;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		PaymentBillEntryInfo reqentry = new PaymentBillEntryInfo();
		return reqentry;
	}

    protected IObjectValue getValue(IObjectPK pk) throws Exception {
    	if(editData==null || isMoving){
    		getUIContext().put("contractBillId",null);
    		getUIContext().put("ID",pk.toString());
    		
    		fetchInitData();
    		return getBizInterface().getValue(pk, getSelectors());
    	}else{
    		return editData;
    	}
    }
    
	protected ICoreBase getBizInterface() throws Exception {
		return PaymentBillFactory.getRemoteInstance();
	}
	
    protected void txtrecCity_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
//		Object newValue = e.getNewValue();
////		txtrecProvince.setEnabled(true);
//		if (newValue instanceof CityInfo) {
//			CityInfo city = (CityInfo)newValue;
//			if(city.isIsDirCity()){				
//				txtrecCity.setEnabled(false);
//			}else{
//				txtrecCity.setEnabled(false);
//			}
//		}
    }
    
    protected void txtrecProvince_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
		Object newValue = e.getNewValue();
		
		if (newValue instanceof ProvinceInfo) {
			if(!GlUtils.isEqual(newValue,e.getOldValue())){
//				txtrecCity.setValue(null);
			}
			
			ProvinceInfo province = (ProvinceInfo) newValue;
			province_dataChanged( province.getId().toString());
			
		}else{
			txtrecCity.setEnabled(true);
//			txtrecCity.setValue(null);
		}
    }
    
    private void province_dataChanged(String provinceId)throws Exception{		
    	if(provinceId==null){
    		return ;
    	}
    	
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		SelectorItemCollection selector = ev.getSelector();
		selector.add("id");
		selector.add("isDirCity");
		selector.add("name");
		selector.add("number");
		
		filter.getFilterItems().add(
				new FilterItemInfo("isDirCity", Boolean.TRUE));
		if(BOSUuid.isValidLength(provinceId)){
			filter.getFilterItems().add(
				new FilterItemInfo("province.id",provinceId ));
		}else{
			filter.getFilterItems().add(
					new FilterItemInfo("province.name",provinceId ));
		}
		
		
		CityCollection col = CityFactory.getRemoteInstance().getCityCollection(ev);
		
		CityInfo city = null;
		if(col.size()>0){
			city = col.get(0);
		}
		if(city!=null && city.isIsDirCity()){				
//			txtrecCity.setEnabled(false);
//			txtrecCity.setValue(city);
//			txtrecCity.setValue(null);
		}else{
			txtrecCity.setEnabled(true);				
		}
    }
    
    /**
     * output txtrecCity_willCommit method
     */
    protected void txtrecCity_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output txtrecCity_willShow method
     */
    protected void txtrecCity_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    	Object obj = txtrecProvince.getData();
    	
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		SelectorItemCollection selector = new SelectorItemCollection();;
		selector.add("id");
		selector.add("isDirCity");
		selector.add("name");
		selector.add("province.name");
		
//		if(txtrecProvince.isEnabled()){
			if(obj instanceof String ){
				String province = (String) obj;
				if(province!=null){
					filter.getFilterItems().add(
							new FilterItemInfo("province.name", province));
				}
			}else if(obj instanceof ProvinceInfo ){
				ProvinceInfo province = (ProvinceInfo) obj;
				if(province!=null){
					filter.getFilterItems().add(
							new FilterItemInfo("province.id", province.getId().toString()));
				}
			}		
//		}

		txtrecCity.setSelectorCollection(selector);
    	txtrecCity.setEntityViewInfo(ev);		
    	txtrecCity.getQueryAgent().resetRuntimeEntityView();
    }

	// 根据币别自动获取汇率
	protected void prmtCurrency_dataChanged(DataChangeEvent e) throws Exception {
		// prmtPayerAccount.setValue(null);
		Object newValue = e.getNewValue();
		
		if (newValue instanceof CurrencyInfo) {
			BOSUuid srcid = ((CurrencyInfo) newValue).getId();			
			Date bookedDate = (Date)pkBizDate.getValue();
	    	
			ExchangeTableInfo baseExchangeTable = this.currentCompany.getBaseExchangeTable();
			if (baseExchangeTable != null) {
				if (baseCurrency != null) {
					if (srcid.equals(baseCurrency.getId())) {
						// 是本位币时将汇率选择框置灰
						// txtExchangeRate.setValue(null);
						txtExchangeRate.setValue(FDCHelper.ONE);
						txtExchangeRate.setRequired(false);
						txtExchangeRate.setEditable(false);
						txtExchangeRate.setEnabled(false);
						setAmount();
						return;
					}
					txtExchangeRate.setRequired(true);
					txtExchangeRate.setEditable(true);
					txtExchangeRate.setEnabled(true);
//					IExchangeRate iExchangeRate = ExchangeRateFactory
//							.getRemoteInstance();
//					ExchangeRateInfo exchangeRate = iExchangeRate
//							.getExchangeRate(new ObjectUuidPK(baseExchangeTable
//									.getId()), new ObjectUuidPK(srcid),
//									new ObjectUuidPK(baseCurrency.getId()),
//									new Date());
					
					ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,srcid,currentCompany,bookedDate);
					
			    	int curPrecision = FDCClientHelper.getPrecOfCurrency(srcid);
			    	BigDecimal exRate = FDCHelper.ONE;
			    	int exPrecision = curPrecision;
			    	
			    	if(exchangeRate!=null){
			    		exRate = exchangeRate.getConvertRate();
			    		exPrecision = exchangeRate.getPrecision();
			    	}
			    	txtExchangeRate.setValue(exRate);
			    	txtExchangeRate.setPrecision(exPrecision);
			    	txtAmount.setPrecision(curPrecision);
			    	
//					if (exchangeRate != null) {
//						txtExchangeRate.setValue(exchangeRate.getConvertRate());
//					} else {
//						txtExchangeRate.setValue(new BigDecimal(0));
//					}
					setAmount();

				} else {
					logger.info("当前组织没有设置本位币");
					MsgBox.showInfo(this, PaymentBillClientUtils
							.getRes("noBaseCurrency"));
				}
//
			} else {
				logger.info("当前组织没有汇率表");
				MsgBox.showInfo(this, PaymentBillClientUtils
						.getRes("noExchangeRateTable"));
			}

		}
		super.prmtCurrency_dataChanged(e);
	}

	protected void txtExchangeRate_dataChanged(DataChangeEvent e)
			throws Exception {
		super.txtExchangeRate_dataChanged(e);
		setAmount();
		setProjectPriceInContract();
		if(this.isAdvance())
			this.setAdvance();
	}
	/**
	 * 提交状态的付款单在拆分后，再次修改变更金额时给与客户提示   by Cassiel
	 */
	boolean isWarn = false;
	protected void btnInputCollect_mouseClicked(MouseEvent e) throws Exception {
		super.btnInputCollect_mouseClicked(e);
		if(STATUS_EDIT.equals(getOprtState())&&!isWarn){
			//付款拆分
			boolean isCostSplit = FDCSplitClientHelper.isBillSplited(
					getSelectBOID(), "T_FNC_PaymentSplit", "FPaymentBillID");
			//付款非成本科目拆分
			boolean isNoCostSplit = FDCSplitClientHelper.isBillSplited(
					getSelectBOID(), "T_FNC_PaymentNoCostSplit",
					"FPaymentBillID");
			if (isCostSplit || isNoCostSplit) {
				isWarn = true;
				MsgBox.showWarning("此付款单已经拆分，修改金额会导致付款金额与拆分不一致，修改金额后，将清除拆分");
			}
		}
	}
	/**
	 * 描述：响应“填入汇充数"按钮事件<p>
	 * 因为原币可能汇率不一，不能用setAmount根据汇率计算本位币,直接取工程情况表中的本币
	 */
	
	protected void btnInputCollect_actionPerformed(ActionEvent e)
			throws Exception {
		
		// 填入汇总数操作
		super.btnInputCollect_actionPerformed(e);
//		if(isBaseCurrency){
			Object cell = bindCellMap.get(PaymentBillContants.CURPAID);
			if (cell instanceof ICell) {
				Object value = ((ICell) cell).getValue();
				if (value != null) {
					try {
						// txtAmount.setValue(toBigDecimal(value));
						txtAmount.setValue(FDCHelper.toBigDecimal(value));
						editData.setAmount(FDCHelper.toBigDecimal(value));
					} catch (NumberFormatException e1) {
						super.handUIException(new Throwable("格式错误!"
								+ e1.getMessage()));
					}
//					setAmount();
				}
			}
			cell = bindCellMap.get(PaymentBillContants.CURPAIDLOCAL);
			if (cell instanceof ICell) {
				Object value = ((ICell) cell).getValue();
				if (value != null) {
					try {
						txtLocalAmt.setValue(FDCHelper.toBigDecimal(value));
						editData.setLocalAmt(FDCHelper.toBigDecimal(value));
					} catch (NumberFormatException e1) {
						super.handUIException(new Throwable("格式错误!"
								+ e1.getMessage()));
					}
					setAmount();
				}
			}
//		}
		
	}
	Map paramValue = new HashMap();
	private void preProcess() throws BOSException, EASBizException {

		paramValue.put("ui_name", "CasPaymentBillUI");
		paramValue.put("isFirtTime", Boolean.TRUE);
		paramValue.put(CashSysParamConstants.CS031, Boolean.FALSE);
		paramValue.put("ID", getUIContext().get(UIContext.ID));
		HashMap param = new HashMap();
		paramValue.put("param", param);
		Map resultMap = new HashMap();
		resultMap.put("isFirtTime", Boolean.TRUE);
		resultMap.put("ui_name", "CasPaymentBillUI");
		resultMap.put("ID", getUIContext().get(UIContext.ID));
		
		resultMap = BatchFetchParamFacadeFactory.getRemoteInstance()
				.fetchParam(resultMap);
		paramValue.putAll(resultMap);
	}
	
	/**
	 * 不同操作，设置不同的WorkButton状态 只设置action功能
	 */
	protected void initDataStatus() {
		super.initDataStatus();
		
		CompanyF7Factory.initCompanyBizUnitF7(this, prmtAgentCompany);
		
		String oprtS = getOprtState();
		boolean val = false;
        if(paramValue.get("CS024") == null)
            val = ContextHelperFactory.getRemoteInstance().getBooleanParam("CS024", new ObjectUuidPK(currentCompany.getId()));
        else
            val = ((Boolean)paramValue.get("CS024")).booleanValue();
        
		PaymentBillInfo info = (PaymentBillInfo)getDataObject();
		BillStatusEnum billStatus = BillStatusEnum.SAVE;
        if(info.getBillStatus() != null)
        	billStatus = info.getBillStatus();
		boolean canApprove = billStatus.equals(BillStatusEnum.SUBMIT) && val && !"FINDVIEW".equals(oprtS);
        actionApprove.setEnabled(canApprove);
        actionApprove.setVisible(canApprove);
        boolean canAntiApprove = billStatus.equals(BillStatusEnum.APPROVED) && val && !"FINDVIEW".equals(oprtS);
        actionUntiApprove.setEnabled(canAntiApprove);
        actionUntiApprove.setVisible(canAntiApprove);
        btnUntiApprove.setVisible(false);
        if(canAntiApprove){
        	this.txtPayeeBank.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.txtPayeeAccountBank.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.prmtPayee.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.prmtCurrency.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.prmtCurrency.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.txtPayeeAccountBank.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.prmtPayBillType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.txtPayeeBank.setEnabled(false);
        	this.txtPayeeAccountBank.setEnabled(false);
        	this.prmtPayee.setEnabled(false);
        	this.prmtCurrency.setEnabled(false);
        	this.prmtPayerAccount.setEnabled(false);
        	this.txtPayeeAccountBank.setEnabled(false);
        	this.prmtPayBillType.setEnabled(false);
        }
	}
	
	/**
	 * 描述：初始化UI部分控件 1、币别 2、收款人类型：客户、供应商、职员、公司、其他五种
	 */
	private void initUIProp() throws Exception {
		// 初始化付款人类型
		comboPayeeType.removeAllItems();
		
		AsstActTypeCollection asstActTypeColl = CasRecPayHandler.getAsstActTypeColl(null, false, false);
		comboPayeeType.addItem(FMSysDefinedEnum.OTHER);
		
		for (int i = 0; i < asstActTypeColl.size(); i++) {
			if ("innerAccount".equalsIgnoreCase(asstActTypeColl.get(i)
					.getAsstHGAttribute())) {
				innerAccountActType = asstActTypeColl.get(i);

			}
			if ("provider".equalsIgnoreCase(asstActTypeColl.get(i)
					.getAsstHGAttribute())) {
				supplierAssitType = asstActTypeColl.get(i);
			}
		}
		
		comboPayeeType.addItems(asstActTypeColl.toArray());
		

	}
	boolean isFpItem = false;
	public void onLoad() throws Exception {
		isFpItem =FDCDealFPOrBudgetItemUtil.getIsFpOrBg();
		
		// 资金计划回迁后，原计划项目不再使用，改用合同月度滚动计划控制付款
		// edit by emanon 这里注释掉
//		if (isFpItem) {// 界面显示计划项目
//			prmtFpItem.setQueryInfo("com.kingdee.eas.fm.fpl.FpItemQuery");
//			
//			Set set = new HashSet();
//			set.add(FPItemDirectionEnum.OUTWARD_MOVEMENT);
//			set.add(FPItemDirectionEnum.OTHER_VALUE);
//
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("direction", set, CompareType.INCLUDE));
//
//			EntityViewInfo evi = new EntityViewInfo();
//			evi.setFilter(filter);
//			
//			prmtFpItem.setEntityViewInfo(evi);
//		}else{//界面显示预算项目
//			NewBgItemDialog bgItemSelect = new NewBgItemDialog(this);
//			prmtFpItem.setSelector(bgItemSelect);
//			prmtFpItem.setSelector(bgItemSelect);
//		}
		
		//注册一个监听器   by Cassiel_peng
		btnInputCollect.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				try {
					btnInputCollect_mouseClicked(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// 如果是拉式生成，则不需要重新取数
		boolean notFirstTime = paramValue != null
				&& paramValue.get("isFirtTime") != null
				&& paramValue.get("isFirtTime").equals(Boolean.FALSE);
		boolean isCreateFrom = this.getBOTPViewStatus() == 1 && notFirstTime;
		if (!isCreateFrom) {
			preProcess();
		}
		
		fetchInitData();
		
		initUIProp();
		
		super.onLoad();
		
		if(this.editData.getFdcPayReqID()!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("hasClosed");
			PayRequestBillInfo pay = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
			if(pay.isHasClosed()){
				FDCMsgBox.showWarning(this,"付款申请单已关闭，禁止操作！");
				SysUtil.abort();
			}
		}
		contractId = editData.getContractBillId();
		
		// this.txtNumber.setEnabled(false);
		addBalanceListener();
		//付款单提交、审批时，是否校验合同未完全拆分
		checkAllSplit = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_CHECKALLSPLIT);
		// 隐藏以前的kdtEntries用自己的kdtable替代
		Rectangle kdtRectangle = kdtEntries.getBounds();
		kdtEntries.setEnabled(false);
		kdtEntries.setVisible(false);
		kdtEntries = createPaymentBillTable();
		kdtEntries.addKDTEditListener(new KDTEditAdapter(){
			public void editStopped(KDTEditEvent e) {
				try {
					kdtEntries_editStopped(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
//		if (this.getBOTPViewStatus() == 1) {//方法initFixTable(table)中已保证任何情况下取申请单合同内工程款
//			((ICell)bindCellMap.get(PaymentBillContants.PROJECTPRICEINCONTRACT)).setValue(editData.getAmount());
//		}
		
		kdtEntries.setBounds(kdtRectangle);
		// pnlPayBillEntry.add(kdtEntries, null);
		pnlPayBillEntry.add(kdtEntries, new KDLayout.Constraints(10, 10, 850,
				450, KDLayout.Constraints.ANCHOR_TOP
						| KDLayout.Constraints.ANCHOR_BOTTOM_SCALE
						| KDLayout.Constraints.ANCHOR_LEFT_SCALE
						| KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
		// 多页签显示
		sortPanel();
		kdtEntries.getScriptManager().runAll();
		afterPressDeleteButton();
		if (getOprtState() != null && !getOprtState().equals(OprtState.ADDNEW)
				&& !getOprtState().equals(OprtState.EDIT)) {
			// 锁定可录单元格
			Set set = bindCellMap.keySet();
			for (Iterator iter = set.iterator(); iter.hasNext();) {
				Object obj = bindCellMap.get(iter.next());
				if (obj instanceof ICell) {
					ICell cell = (ICell) obj;
					if (!cell.getStyleAttributes().isLocked()) {
						cell.getStyleAttributes().setLocked(true);
					}
				}
			}
			//			
			int last = getDetailTable().getRowCount() - 1;
			// 形象进度
			getDetailTable().getCell(last, 6).getStyleAttributes().setLocked(
					true);
			//甲供
			getDetailTable().getCell(last - 4, 5).getStyleAttributes()
					.setLocked(true);

		}else{			
//			//可以修改的情况下
//			boolean canModify = true;
//			BillStatusEnum billState = editData.getBillStatus();
//			if (canModifyAccountAfterAuit && getOprtState().equals(OprtState.EDIT) &&
//					billState!=null && billState.equals(BillStatusEnum.AUDITED)){
//				canModify = !editData.isFiVouchered();
//				
//				//			
//
//			}
		}
		
		String contractid = (String) getUIContext().get("contractBillId");
		if (contractid == null && editData != null && editData.getId() != null) {
			getUIContext().put("contractBillId", editData.getContractBillId());
		}
			
		for (int i = 0; i < getDetailTable().getColumnCount(); i++) {
			getDetailTable().getColumn(i).setSortable(false);
		}

		txtSummary.setMaxLength(500);    
		prmtDesc.setMaxLength(150); 
		f7SettleNumber.setMaxLength(80);  
		txtUsage.setMaxLength(usageLegth);
		
		pkBizDate.setSupportedEmpty(false);
		if(initData.get("PARAM_CS034")!=null&&initData.get("PARAM_CS034") instanceof String){
			String cs034 = (String)initData.get("PARAM_CS034");
			this.comboFeeType.setRequired(Boolean.valueOf(cs034).booleanValue());
		}
		if(initData.get("PARAM_CS036")!=null&&initData.get("PARAM_CS036") instanceof String){
			String cs036 = (String)initData.get("PARAM_CS036");
			this.txtUsage.setRequired(Boolean.valueOf(cs036).booleanValue());
		}
		
		// 避免在新增单据（未作修改）直接关闭时，出现保存提示
		this.storeFields();
		this.initOldData(this.editData);
		
		if (getUIContext().get("isMultiApprove") != null) {
			this.actionEdit.setVisible(false);
			this.actionSplit.setVisible(false);
		}
		
		String cu = null;
		if (editData != null && editData.getCU() != null) {
			cu = editData.getCU().getId().toString();
		} else {
			cu = SysContext.getSysContext().getCurrentCtrlUnit().getId()
					.toString();
		}
		FDCClientUtils.setRespDeptF7(prmtuseDepartment, this, cu);
		
		//txtPayeeAccountBank 可以录入文本
        ExtendParser parserAccountFrom = new ExtendParser(txtPayeeAccountBank);
        txtPayeeAccountBank.setCommitParser(parserAccountFrom);
        
		ExtendParser parserCity = new ExtendParser(txtrecCity);
		txtrecCity.setCommitParser(parserCity);
		
		ExtendParser parserProvince = new ExtendParser(prmtDesc);
		prmtDesc.setCommitParser(parserProvince);
		
		//初始化供应商F7
		String cuId = editData.getCU().getId().toString();
		FDCClientUtils.initSupplierF7(this, prmtPayee, cuId);
		
		if(STATUS_EDIT.equals(getOprtState())){
			if(prmtPayerAccount.getValue() instanceof AccountViewInfo){
				AccountViewInfo acc = (AccountViewInfo)prmtPayerAccount.getValue();
				if(acc.isIsBank()){
//					txtPayeeBank.setRequired(true);	
				}else{
					txtPayeeBank.setRequired(false);					
				}
			}
		}
		
		//设置精度
		setPrecision();
		
		setButtonStatus(this.getOprtState());
		
		amtInContractOri = FDCHelper.toBigDecimal(editData.getAddProjectAmt());
		if(prmtPayerAccountBank.getValue() != null && prmtPayerAccountBank.isEnabled()){
			prmtPayerAccount.setEnabled(false);
		}

		//甲供主合同列表
		initPartA();
		
		//进度款累计已完工
		loadAllCompletePrjAmt();
		
		//保修金
		loadQualityGuaranteAmt();
		//53外需求:在付款单、无文本合同界面，将收款帐号、收款银行变为非必录项目(万科必录，其它客户要求非必录) by hpw 2009-08-26
		txtPayeeBank.setRequired(false);
		resetConfirmBillEntrysPaidAmt(FDCHelper.toBigDecimal(editData.getAmount()));
		initMaterial();
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		selector.add("ntType.*");
		prmtSettlementType.setSelectorCollection(selector);
		SettlementTypeInfo settType = (SettlementTypeInfo) prmtSettlementType
		.getData();
		if((STATUS_VIEW==this.getOprtState()||STATUS_FINDVIEW==this.getOprtState())
				&&this.editData.getBillStatus()==BillStatusEnum.AUDITED&&
				(settType!=null&&settType.getNtType()!=null&&NTTypeGroupEnum.PaymentCheque.equals(settType.getNtType()
						.getSuperGroup()))){
			this.actionChequeReimburse.setVisible(true);
			this.actionChequeReimburse.setEnabled(true);
			actionChequeReimburse.putValue(Action.SMALL_ICON, EASResource
					.getIcon("imgTbtn_cancelverification"));
		}
		if(this.getUIContext().get("ChequeReimBurse")!=null&&((Integer)this.getUIContext().get("ChequeReimBurse")).intValue()==1){
			initChequeReimburseStatus();
		}
		
		initFDCDepPlan();
		
		this.btnInputCollect.setVisible(false);
		this.actionClosePayReq.setVisible(false);
		this.actionCancelClosePayReq.setVisible(false);
		
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		
		this.actionNextPerson.setVisible(false);
	}
	
	String paramConPayPlanCtrl;

	/**
	 * 初始化计划文本框，将原来的prmtFpItem隐藏
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void initFDCDepPlan() throws EASBizException, BOSException{
		// 查看预算图标
		menuItemViewBalance.setIcon(EASResource.getIcon("imgTbtn_fundcount"));
		btnViewBalance.setIcon(EASResource.getIcon("imgTbtn_fundcount"));
		
		//关于付款单界面"计划项目"的说明：当参数"通过合同月度付款计划控制合同付款申请及其控制策略"
		//参数值为"严格控制"和"提示控制"时，该字段值自动由付款申请单带入且不可修改，当参数为"不控制"时，
		//维持现状即F7取预算系统中的预算项目
		String org = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		paramConPayPlanCtrl = ParamManager.getParamValue(null, new ObjectUuidPK(
				org), "FDC325_CONTROLPAYREQUEST");
		if ("0".equals(paramConPayPlanCtrl)) {
			paramConPayPlanCtrl = "严格控制";
		} else if ("1".equals(paramConPayPlanCtrl)) {
			paramConPayPlanCtrl = "提示控制";
		} else {
			paramConPayPlanCtrl = "不控制";
		}
		if("严格控制".equals(paramConPayPlanCtrl) || "提示控制".equals(paramConPayPlanCtrl) ){
			contFpItem.setVisible(false);
			contPlanItem.setVisible(true);
			txtPlanItem.setEnabled(false);
			
			String conID = editData.getContractBillId();
			SelectorItemCollection sic = new SelectorItemCollection();
			Calendar cal = Calendar.getInstance();
			if (((new ContractWithoutTextInfo()).getBOSType()).equals(BOSUuid
					.read(conID).getType())) {
				sic.add("id");
				sic.add("bookedDate");
				sic.add("fdcDepConPlan.id");
				sic.add("fdcDepConPlan.id");
				sic.add("fdcDepConPlan.payMattersName");
				sic.add("fdcDepConPlan.head.deptment.name");
				ContractWithoutTextInfo con = ContractWithoutTextFactory
						.getRemoteInstance().getContractWithoutTextInfo(
								new ObjectUuidPK(conID), sic);
				if(con!=null&&con.getFdcDepConPlan()!=null){
					cal.setTime(con.getBookedDate());
					FDCDepConPayPlanNoContractInfo plan = con
							.getFdcDepConPlan();
					StringBuffer format = new StringBuffer();
					format.append("无合同 ");
					format.append(plan.getPayMattersName()).append(" ");
					format.append(cal.get(Calendar.YEAR));
					format.append("年");
					format.append(cal.get(Calendar.MONTH) + 1);
					format.append("月付款计划");
					txtPlanItem.setText(format.toString());
					txtPlanItem.setUserObject(plan);
				}
			} else {
				sic.add("id");
				sic.add("bookedDate");
				sic.add("planHasCon.id");
				sic.add("planHasCon.contractName");
				sic.add("planHasCon.head.deptment.name");
				sic.add("planUnCon.id");
				sic.add("planUnCon.unConName");
				sic.add("planUnCon.parent.deptment.name");
				String payRequestBillId = this.editData.getFdcPayReqID();
				PayRequestBillInfo payReq = PayRequestBillFactory
						.getRemoteInstance().getPayRequestBillInfo(
								new ObjectUuidPK(payRequestBillId), sic);
				cal.setTime(payReq.getBookedDate());
				if (payReq.getPlanHasCon() != null) {
					String contractName = payReq.getPlanHasCon()
							.getContractName();
					StringBuffer format = new StringBuffer();
					format.append(contractName).append(" ");
					format.append(cal.get(Calendar.YEAR));
					format.append("年");
					format.append(cal.get(Calendar.MONTH) + 1);
					format.append("月付款计划");
					txtPlanItem.setText(format.toString());
					txtPlanItem.setUserObject(payReq.getPlanHasCon());
				} else if (payReq.getPlanUnCon() != null) {
					String contractName = payReq.getPlanUnCon().getUnConName();
					StringBuffer format = new StringBuffer();
					format.append("待签订");
					format.append(contractName).append(" ");
					format.append(cal.get(Calendar.YEAR));
					format.append("年");
					format.append(cal.get(Calendar.MONTH) + 1);
					format.append("月付款计划");
					txtPlanItem.setText(format.toString());
					txtPlanItem.setUserObject(payReq.getPlanUnCon());
				}
			}
		} else {
			contPlanItem.setVisible(false);
			contFpItem.setVisible(true);
			isFpItem =FDCDealFPOrBudgetItemUtil.getIsFpOrBg();
			if (isFpItem) {// 界面显示计划项目
				prmtFpItem.setQueryInfo("com.kingdee.eas.fm.fpl.FpItemQuery");
				
				Set set = new HashSet();
				set.add(FPItemDirectionEnum.OUTWARD_MOVEMENT);
				set.add(FPItemDirectionEnum.OTHER_VALUE);
	
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("direction", set, CompareType.INCLUDE));
	
				EntityViewInfo evi = new EntityViewInfo();
				evi.setFilter(filter);
				
				prmtFpItem.setEntityViewInfo(evi);
			}else{//界面显示预算项目
				NewBgItemDialog bgItemSelect = new NewBgItemDialog(this);
				prmtFpItem.setSelector(bgItemSelect);
				prmtFpItem.setSelector(bgItemSelect);
			}
		}
	}
	
	public void actionChequeReimburse_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionChequeReimburse_actionPerformed(e);
		String tempOprState = getOprtState();
		setSave(false);
		// 应用网络互斥
		this.setOprtState(STATUS_EDIT);
		this.getUIContext().put("ChequeReimBurse", new Integer(1));
		IObjectValue val = (IObjectValue) getUIContext().get("CURRENT.VO");
		getUIContext().put("CURRENT.VO", null);
		try {
			setDataObject(val);
		} catch (Exception ex) {
			setOprtState(tempOprState);
			getUIContext().put("CURRENT.VO", val);
			throw ex;
		}

		unLockUI();

		// showMessageForStatus();
		setMessageText(getClassAlise() + " " + "票据填开");
		setIsShowTextOnly(true);
		showMessage();
		this.setUITitle(getClassAlise() + "-" + "修改");

		// lockUIComponent(false);
		// getActionManager().enableAllAction();
		initDataStatus();
		setDefaultFocused();

		initChequeReimburseStatus();
		
	}
	
	private void initChequeReimburseStatus(){
		actionSubmit.setEnabled(true);
		Component[] cmp=this.pnlPayBillDetail.getComponents();
		for(int i=0;i<cmp.length;i++){
			cmp[i].setEnabled(false);
		}
		cmp=this.pnlPayBillMaterial.getComponents();
		for(int i=0;i<cmp.length;i++){
			cmp[i].setEnabled(false);
		}
		cmp=this.pnlPayBillPartA.getComponents();
		for(int i=0;i<cmp.length;i++){
			cmp[i].setEnabled(false);
		}
		cmp=this.pnlPayBillEntry.getComponents();
		for(int i=0;i<cmp.length;i++){
			cmp[i].setEnabled(false);
		}
		cmp = this.pnlPayBillHeader.getComponents();
		for(int i=0;i<cmp.length;i++){
			cmp[i].setEnabled(false);
		}
		
		f7SettleNumber.setEnabled(true);
//		btnPayGroup.setVisible(false);
		ItemAction[] actions = new ItemAction[] { 
				this.actionSave,
				actionAddNew,
				actionRemove,
				actionEdit,
				actionAttachment,
				actionPre,
				actionNext,
				actionFirst,
				actionLast,
//				actionViewBdgBalance,
//				actionUntiApprove,
				actionAddLine,
				actionAntiAudit,
				actionViewBgBalance,
				actionTraceUp,
				actionTraceDown,
				actionWorkFlowG,
				actionCreateFrom,
				actionCreateTo,
//				actionDlFromSt,
				actionMultiapprove,
				actionViewDoProccess,
				actionAuditResult,
				actionNextPerson,
				actionVoucher,
				this.actionClosePayReq,
				this.actionCancelClosePayReq,
				this.actionSplit,
				this.actionChequeReimburse,
				this.actionRefresh,
				actionInsertLine, actionCopyLine, actionRemoveLine };
		FDCClientHelper.setActionEnable(actions, false);
	
}
	
	private EntityViewInfo getBizTypeEV(AccountBankInfo accBank) {
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		Set set = new HashSet();
		set.add(new Integer(SettBizTypeEnum.PAYINSIDE_VALUE));
		set.add(new Integer(SettBizTypeEnum.PAYOUTSIDE_VALUE));
//		set.add(new Integer(SettBizTypeEnum.PAYINSIDEDIF_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("type", set, CompareType.INCLUDE));
		if (accBank == null || accBank.getInnerAcct() == null) {
			filter.getFilterItems().add(new FilterItemInfo("id", null));

		}

		return ev;
	}

	/**
	 * 焦点事件绑定
	 */
	private void addBalanceListener() {
		FocusAdapter listener = new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				try {
					AccountBankInfo acctBank = (AccountBankInfo) prmtPayerAccountBank
							.getValue();
					AccountViewInfo acctView = (AccountViewInfo) prmtPayerAccount
							.getValue();
					CurrencyInfo currency = (CurrencyInfo) prmtCurrency
							.getValue();

					setBalanceValue(txtDayaccount, acctView, acctBank, currency);
				} catch (EASBizException e1) {
					e1.printStackTrace();
					SysUtil.abort();
				} catch (BOSException e1) {
					e1.printStackTrace();
					SysUtil.abort();
				}
			}

			public void focusLost(FocusEvent arg0) {
				try {
					AccountBankInfo acctBank = (AccountBankInfo) prmtPayerAccountBank
							.getValue();
					AccountViewInfo acctView = (AccountViewInfo) prmtPayerAccount
							.getValue();
					CurrencyInfo currency = (CurrencyInfo) prmtCurrency
							.getValue();

					setBalanceValue(txtDayaccount, acctView, acctBank, currency);
				} catch (EASBizException e1) {
					e1.printStackTrace();
					SysUtil.abort();
				} catch (BOSException e1) {
					e1.printStackTrace();
					SysUtil.abort();
				}
			}
		};
		prmtCurrency.addFocusListener(listener);
		prmtPayerAccount.getEditor().addFocusListener(listener);
		prmtPayerAccountBank.getEditor().addFocusListener(listener);

	}

	// 日记账余额的缓存map
	private Map balanceMap = new HashMap();

	private void setBalanceValue(KDFormattedTextField txtDayaccount,
			AccountViewInfo acctView, AccountBankInfo acctBank,
			CurrencyInfo currency) throws EASBizException, BOSException {
		if (currency == null)
			return;
		BigDecimal balance = FMConstants.ZERO;
		ICashManagement iCash = CashManagementFactory.getRemoteInstance();

		// 取得银行科目的日记账余额
		if (acctBank != null) {

			balance = (BigDecimal) balanceMap.get(acctBank.getId().toString()
					+ currency.getId().toString());

			if (balance == null) {
				balance = iCash.getBankData(currentCompany.getId().toString(),
						acctBank.getId().toString(), currency.getId()
								.toString(), new Date(), new Date(),
						CashDataTypeEnum.Y, true);

				if (balance != null) {
					balanceMap.put(acctBank.getId().toString()
							+ currency.getId().toString(), balance);
				}
			}
		} else if (acctView != null) {

			balance = (BigDecimal) balanceMap.get(acctView.getId().toString()
					+ currency.getId().toString());

			if (balance == null) {
				balance = iCash.getCashData(currentCompany.getId().toString(),
						acctView.getId().toString(), currency.getId()
								.toString(), new Date(), new Date(),
						CashDataTypeEnum.Y, true);

				if (balance != null) {
					balanceMap.put(acctView.getId().toString()
							+ currency.getId().toString(), balance);
				}
			}
		}

		/*balance = balance.setScale(currency.getPrecision(),
				BigDecimal.ROUND_HALF_UP);*/
		//update by renliang
		if(balance!=null){
			balance = balance.setScale(currency.getPrecision(),
					BigDecimal.ROUND_HALF_UP);
		}
		
		NumberFormat fm = NumberFormat.getInstance();
		fm.setGroupingUsed(true);
		if (!FMHelper.isEmpty(balance)) {
			txtDayaccount.setText(fm.format(balance));
		}
	}

	protected void txtAmount_dataChanged(DataChangeEvent e) throws Exception {
		super.txtAmount_dataChanged(e);
//		setAmount();
	}

	// 获取需要显示的属性
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();//super.getSelectors();
		
		//来源系统		
		sic.add("sourceType"); 
		
		//		
		sic.add("fiVouchered"); 
		//付款单编码
		sic.add("number");
		//合同号
		sic.add("contractBillId");
		sic.add("contractNo");
		//单据状态
		sic.add("billStatus");
		//付款日期
		sic.add("payDate");
		//业务日期
		sic.add("bizDate");	
		//收款银行
		sic.add(new SelectorItemInfo("payeeBank"));
		//收款帐户
		sic.add(new SelectorItemInfo("payeeAccountBank"));
		
		//日记账余额
		sic.add(new SelectorItemInfo("dayaccount"));
		//汇率
		sic.add(new SelectorItemInfo("exchangeRate"));
		//原币金额
		sic.add(new SelectorItemInfo("amount"));
		//本位币金额
		sic.add(new SelectorItemInfo("localAmt"));
		//备注
		sic.add(new SelectorItemInfo("summary"));
		//附件数
		sic.add(new SelectorItemInfo("accessoryAmt"));
		//款项说明
		sic.add(new SelectorItemInfo("description"));		
		//打回意见
		sic.add(new SelectorItemInfo("conceit"));		
//		payeeID	往来户ID
		sic.add(new SelectorItemInfo("payeeID"));
//		payeeNumber	往来户编码
		sic.add(new SelectorItemInfo("payeeNumber"));
//		payeeName	往来户名称
		sic.add(new SelectorItemInfo("payeeName"));
//		payeeBank	收款银行
		sic.add(new SelectorItemInfo("payeeBank"));
//		payeeAccountBank	收款账号
		sic.add(new SelectorItemInfo("payeeAccountBank"));		
		//fdcPayReqNumber
		sic.add(new SelectorItemInfo("fdcPayReqNumber"));
//		urgentDegree	紧急程度
		sic.add(new SelectorItemInfo("urgentDegree"));
//		latestPrice	最新造价
		sic.add(new SelectorItemInfo("latestPrice"));
//		addProjectAmt	增加工程款(本期发生)
		sic.add(new SelectorItemInfo("addProjectAmt"));
//		changeAmt	变更签证金额
		sic.add(new SelectorItemInfo("changeAmt"));
//		payedAmt	本申请单已付金额
		sic.add(new SelectorItemInfo("payedAmt"));
		sic.add(new SelectorItemInfo("actPayAmt"));
//		payPartAMatlAmt	应扣甲供材料款（本期发生）
		sic.add(new SelectorItemInfo("payPartAMatlAmt"));
//		payTimes	付款次数
		sic.add(new SelectorItemInfo("payTimes"));
//		projectPriceInContract	合同内工程款（本期发生）
		sic.add(new SelectorItemInfo("projectPriceInContract"));
		// 预付款(不美观后续再改)
		sic.add("prjPayEntry.lstAdvanceAllPaid");
		sic.add("prjPayEntry.lstAdvanceAllReq");
		sic.add("prjPayEntry.advance");
		sic.add("prjPayEntry.locAdvance");
		sic.add("prjPayEntry.advanceAllReq");
		sic.add("prjPayEntry.advanceAllPaid");
		sic.add("prjPayEntry.invoiceNumber");
		sic.add("prjPayEntry.invoiceAmt");
//		scheduleAmt	进度款
		sic.add(new SelectorItemInfo("scheduleAmt"));
//		settleAmt	结算金额
		sic.add(new SelectorItemInfo("settleAmt"));
//		curPlannedPayment	本期计划付款
		sic.add(new SelectorItemInfo("curPlannedPayment"));
//		curBackPay	本期欠付款
		sic.add(new SelectorItemInfo("curBackPay"));	
//		curReqPercent	本次申请%
		sic.add(new SelectorItemInfo("curReqPercent"));
//		allReqPercent	累计申请%
		sic.add(new SelectorItemInfo("allReqPercent"));
//		imageSchedule	形象进度
		sic.add(new SelectorItemInfo("imageSchedule"));
//		curPaid	实付款本期发生额
		sic.add(new SelectorItemInfo("curPaid"));
//		prjAllReqAmt	合同内工程累计申请
		sic.add(new SelectorItemInfo("prjAllReqAmt"));
//		addPrjAllReqAmt	增加工程款累计申请
		sic.add(new SelectorItemInfo("addPrjAllReqAmt"));
//		payPartAMatlAllReqAmt	甲供材累计申请款
		sic.add(new SelectorItemInfo("payPartAMatlAllReqAmt"));
//		lstPrjAllReqAmt	合同内工程款上期累计申请
		sic.add(new SelectorItemInfo("lstAddPrjAllReqAmt"));
//		lstAddPrjAllReqAmt	增加工程款上期累计
		sic.add(new SelectorItemInfo("lstAddPrjAllReqAmt"));
//		lstAMatlAllReqAmt	甲供材上期累计申请
		sic.add(new SelectorItemInfo("lstAMatlAllReqAmt"));
//		actFdcPayeeName	房地产实际收款单位全称
		sic.add(new SelectorItemInfo("actFdcPayeeName"));
//		fdcPayReqNumber	房地产付款申请单单据编号
		sic.add(new SelectorItemInfo("fdcPayReqNumber"));
//		lstPrjAllPaidAmt	上期合同内累计实付
		sic.add(new SelectorItemInfo("lstPrjAllPaidAmt"));
		//		lstAddPrjAllPaidAmt	增加工程款上期累计实付
		sic.add(new SelectorItemInfo("lstAddPrjAllPaidAmt"));
//		lstAMatlAllPaidAmt	甲供材上期累计实付
		sic.add(new SelectorItemInfo("lstAMatlAllPaidAmt"));
//		fdcPayReqID	付款申请单ID
		sic.add(new SelectorItemInfo("fdcPayReqID"));
//		isEmergency	是否加急
		sic.add(new SelectorItemInfo("isEmergency"));
		//capitalAmount 大写金额
		sic.add(new SelectorItemInfo("capitalAmount"));
		//结算号
		sic.add(new SelectorItemInfo("settlementNumber"));

		//省/市
		sic.add(new SelectorItemInfo("recCity"));
		sic.add(new SelectorItemInfo("recProvince"));
		//同城异地
		sic.add(new SelectorItemInfo("isDifferPlace"));
		//需要支付
		sic.add(new SelectorItemInfo("isNeedPay"));
		//用途
		sic.add(new SelectorItemInfo("usage"));
		
		sic.add(new SelectorItemInfo("payeeType.id"));
		sic.add(new SelectorItemInfo("payeeID"));
		sic.add(new SelectorItemInfo("payeeNumber"));
		sic.add(new SelectorItemInfo("payeeName"));
		
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("CU.name"));
		sic.add(new SelectorItemInfo("CU.number"));
		//公司
		sic.add(new SelectorItemInfo("company.id"));
		sic.add(new SelectorItemInfo("company.name"));
		sic.add(new SelectorItemInfo("company.number"));

		//代理付款公司
		sic.add(new SelectorItemInfo("agentPayCompany.id"));
		sic.add(new SelectorItemInfo("agentPayCompany.name"));
		sic.add(new SelectorItemInfo("agentPayCompany.number"));
		
		//付款类型
		sic.add("fdcPayType.number");
		sic.add("fdcPayType.name");
		sic.add("fdcPayType.payType.id");
		
		//用款部门
		sic.add(new SelectorItemInfo("useDepartment.number"));
		sic.add(new SelectorItemInfo("useDepartment.name"));

		//工程项目
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.longNumber"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.number"));


		//收款地区 payeeArea
		sic.add("payeeArea.number");
		sic.add("payeeArea.name");
		//收款单位全称
		sic.add("fdcPayeeName.number");
		sic.add("fdcPayeeName.name");		
		//费用类别
		sic.add(new SelectorItemInfo("feeType.number"));
		sic.add(new SelectorItemInfo("feeType.name"));		
		//付款科目
		sic.add(new SelectorItemInfo("payerAccount.id"));
		sic.add(new SelectorItemInfo("payerAccount.name"));
		sic.add(new SelectorItemInfo("payerAccount.number"));
		sic.add(new SelectorItemInfo("payerAccount.longNumber"));
		sic.add(new SelectorItemInfo("payerAccount.longName"));		
		//付款银行
		sic.add(new SelectorItemInfo("payerBank.id"));
		sic.add(new SelectorItemInfo("payerBank.name"));
		sic.add(new SelectorItemInfo("payerBank.number"));
		sic.add(new SelectorItemInfo("payerBank.longNumber"));
		//付款账号
		sic.add(new SelectorItemInfo("payerAccountBank.name"));
		sic.add(new SelectorItemInfo("payerAccountBank.number"));	
		sic.add(new SelectorItemInfo("payerAccountBank.bankaccountnumber"));
		sic.add(new SelectorItemInfo("payerAccountBank.isByCurrency"));
		sic.add(new SelectorItemInfo("payerAccountBank.currency.id"));
		sic.add(new SelectorItemInfo("payerAccountBank.bank.id"));
		sic.add(new SelectorItemInfo("payerAccountBank.bank.name"));
		sic.add(new SelectorItemInfo("payerAccountBank.bank.number"));
		sic.add(new SelectorItemInfo("payerAccountBank.bank.longNumber"));
		sic.add(new SelectorItemInfo("payerAccountBank.company.id"));
		
		//房地产实际收款单位全称
		sic.add(new SelectorItemInfo("actFdcPayeeName.id"));
		sic.add(new SelectorItemInfo("actFdcPayeeName.name"));
		sic.add(new SelectorItemInfo("actFdcPayeeName.number"));
		//结算方式
		sic.add(new SelectorItemInfo("settlementType.id"));
		sic.add(new SelectorItemInfo("settlementType.name"));
		sic.add(new SelectorItemInfo("settlementType.number"));
		
		// sic.add(new SelectorItemInfo("settlementType.ntType.*"));
		sic.add(new SelectorItemInfo("settlementType.ntType.id"));
		sic.add(new SelectorItemInfo("settlementType.ntType.group"));
		sic.add(new SelectorItemInfo("settlementType.ntType.isUsed"));
		sic.add(new SelectorItemInfo("settlementType.ntType.isRecNote"));
		sic.add(new SelectorItemInfo("settlementType.ntType.name"));
		sic.add(new SelectorItemInfo("settlementType.ntType.number"));
		sic.add(new SelectorItemInfo("settlementType.ntType.superGroup"));
		
		//支票
		sic.add(new SelectorItemInfo("cheque.*"));
		//币别
		sic.add(new SelectorItemInfo("currency.id"));
		sic.add(new SelectorItemInfo("currency.name"));
		sic.add(new SelectorItemInfo("currency.number"));

		//计划项目
		sic.add(new SelectorItemInfo("fpItem.number"));
		sic.add(new SelectorItemInfo("fpItem.name"));
		
		//业务种类
		sic.add(new SelectorItemInfo("bizType.type"));
		sic.add(new SelectorItemInfo("bizType.name"));
		sic.add(new SelectorItemInfo("bizType.number"));

		//制单人
		sic.add(new SelectorItemInfo("creator.id"));
		sic.add(new SelectorItemInfo("creator.number"));
		sic.add(new SelectorItemInfo("creator.name"));
		
		sic.add(new SelectorItemInfo("createTime"));
		//审核人
		sic.add(new SelectorItemInfo("auditor.id"));
		sic.add(new SelectorItemInfo("auditor.number"));
		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("auditDate"));

		//出纳
		sic.add(new SelectorItemInfo("cashier.id"));
		sic.add(new SelectorItemInfo("cashier.number"));
		sic.add(new SelectorItemInfo("cashier.name"));
		//会计
		sic.add(new SelectorItemInfo("accountant.id"));
		sic.add(new SelectorItemInfo("accountant.number"));
		sic.add(new SelectorItemInfo("accountant.name"));
		
		//计划项目
		sic.add(new SelectorItemInfo("entries.fpItem.number"));
		sic.add(new SelectorItemInfo("entries.fpItem.name"));
		sic.add(new SelectorItemInfo("entries.amount"));
		sic.add(new SelectorItemInfo("entries.localAmt"));
		sic.add(new SelectorItemInfo("entries.actualAmt"));
		sic.add(new SelectorItemInfo("entries.actualLocAmt"));
		
		//预算项目
		sic.add("outBgItemId");
		sic.add("outBgItemNumber");
		sic.add("outBgItemName");
		sic.add("lastUpdateTime");
		
//		sic.add(new SelectorItemInfo("contractBill.id"));
//		sic.add(new SelectorItemInfo("contractBill.number"));
//		sic.add(new SelectorItemInfo("contractBill.name"));

		sic.add(new SelectorItemInfo("oppAccount.id"));
		sic.add(new SelectorItemInfo("oppAccount.name"));
		sic.add(new SelectorItemInfo("oppAccount.number"));
		sic.add(new SelectorItemInfo("oppAccount.simpleName"));
		sic.add(new SelectorItemInfo("oppAccount.longNumber"));
		sic.add(new SelectorItemInfo("oppAccount.displayName"));
		sic.add(new SelectorItemInfo("oppAccount.longName"));
		sic.add("CU.name");
		sic.add("CU.number");
		
		sic.add("payBillType.*");
		
		sic.add("entries.*");
		
		
		sic.add("approver.*");
		
		sic.add(new SelectorItemInfo("isDifBank"));
	    sic.add(new SelectorItemInfo("FRecCountry.*"));
		return sic;
	}

	public IUIObject getInstance(Map uiContext) {		
		// 查看时获取info
		PaymentBillInfo billInfo = (PaymentBillInfo) uiContext
				.get(UIContext.INIT_DATAOBJECT);
		// 工作流审核时获取info
		if (billInfo == null && uiContext.get("DATAOBJECTS") != null) {
			billInfo = (PaymentBillInfo) ((Map) uiContext.get("DATAOBJECTS"))
					.get("billInfo");
		}
		// 工作流收款时获取info
		if (billInfo == null && uiContext.get(UIContext.ID) != null) {
			String id = null;
			if (uiContext.get(UIContext.ID) instanceof BOSUuid) {
				id = ((BOSUuid) uiContext.get(UIContext.ID)).toString();
			} else if (uiContext.get(UIContext.ID) instanceof String) {
				id = (String) uiContext.get(UIContext.ID);
			} else {
				id = uiContext.get(UIContext.ID).toString();
			}
			IObjectPK pk = new ObjectUuidPK(id);
			try {
				billInfo = PaymentBillFactory.getRemoteInstance()
						.getPaymentBillInfo(pk,this.getSelectors());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (billInfo != null
				&& (SourceTypeEnum.FDC.equals(billInfo.getSourceType()))) {
			if (Boolean.TRUE.equals(uiContext.get("GetInstanceOver"))) {
				this.editData = billInfo;
				return this;
			}
			// || SourceTypeEnum.FDC.equals(billInfo.getSourceSysType()))) {
			try {
				uiContext.put("GetInstanceOver", Boolean.TRUE);
				PaymentBillEditUI pay = (PaymentBillEditUI) UIFactoryHelper.initUIObject(PaymentBillEditUI.class.getName(), uiContext,
						null, this.oprtState);
//				PaymentBillEditUI pay = new PaymentBillEditUI();
				pay.editData = billInfo;
				return pay;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.getInstance(uiContext);
	}

	private void sortPanel() {
		tbpPaymentBill.removeAll();
		tbpPaymentBill.add(pnlPayBillHeader, resHelper
				.getString("pnlPayBillHeader.constraints"));
		tbpPaymentBill.add(pnlPayBillEntry, resHelper
				.getString("pnlPayBillEntry.constraints"));
		//甲供主合同列表页签
		if(PayReqUtils.isContractBill(editData.getContractBillId()) && isPartA){
			tbpPaymentBill.add(pnlPayBillPartA, resHelper.getString("pnlPayBillPartA.constraints"));
		}
		tbpPaymentBill.add(pnlPayBillDetail, resHelper.getString("pnlPayBillDetail.constraints"));
		tbpPaymentBill.add(pnlPayBillMaterial,resHelper.getString("pnlPayBillMaterial.constraints"));
	}

	protected void updateButtonStatus() {
		// super.updateButtonStatus();
		// 如果是虚体财务组织，则不能增、删、改
		if (!SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()) {
			// if
			// (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())
			// {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
			actionAddNew.setVisible(false);
			actionEdit.setVisible(false);
			actionRemove.setVisible(false);
			actionAttachment.setEnabled(false);
			actionAddLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			actionCreateFrom.setEnabled(false);
		}
		if ((!SysContext.getSysContext().getCurrentOrgUnit()
				.isIsCompanyOrgUnit())
				&& (SysContext.getSysContext().getCurrentOrgUnit()
						.isIsCostOrgUnit())) {
			actionCreateFrom.setEnabled(false);
			actionRemove.setEnabled(false);
		} else {
			setOprtStateByBillStatus();
		}
		
	}

	/**
	 * Object对象转换为BigDecimal对象
	 */
	private BigDecimal toBigDecimal(Object obj) {
		if (obj == null) {
			return BigDecimal.valueOf(0);
		} else {
			if (obj instanceof BigDecimal) {
				return (BigDecimal) obj;
			} else {
				String str = obj.toString().trim();
				if (str.matches("\\.?\\d*") || str.matches("\\d+\\.?\\d*")) {
					return new BigDecimal(str);
				}
			}
		}
		return BigDecimal.valueOf(0);
	}

	// 动态更新表格数据
	private void updateDynamicValue() {

		// 上次累计申请
		// 得到本合对应原付款申请单中累计申请额最大的
		PayRequestBillCollection payReqCol =(PayRequestBillCollection) initData.get("PayRequestBillCollection"); ;
		Iterator iter = payReqCol.iterator();
		if (iter.hasNext()) {
			PayRequestBillInfo info = (PayRequestBillInfo) iter.next();
			editData.setLstPrjAllReqAmt(info.getPrjAllReqAmt());
			editData.setLstAddPrjAllReqAmt(info.getAddPrjAllReqAmt());
			editData.setLstAMatlAllReqAmt(info
							.getPayPartAMatlAllReqAmt());
			editData.setLstAMatlAllPaidAmt(info.getLstAMatlAllPaidAmt());
			((ICell) bindCellMap
							.get(PaymentBillContants.LSTPRJALLREQAMT))
							.setValue(info.getPrjAllReqAmt());
			((ICell) bindCellMap
							.get(PaymentBillContants.LSTADDPRJALLREQAMT))
							.setValue(info.getAddPrjAllReqAmt());
			((ICell) bindCellMap
							.get(PaymentBillContants.LSTAMATLALLREQAMT))
							.setValue(info.getPayPartAMatlAllReqAmt());
			((ICell) bindCellMap
					.get(PaymentBillContants.LSTAMATLALLPAIDAMT))
					.setValue(info.getLstAMatlAllPaidAmt());
		}
			
		// 设置付款次数为合同的付款次数 从付款单中过滤
		PaymentBillCollection payCol = (PaymentBillCollection)initData.get("PaymentBillCollection");	
		editData.setPayTimes(payCol.size());
		
		if(bindCellMap.get(PaymentBillContants.PAYTIMES) != null){
			((ICell) bindCellMap.get(PaymentBillContants.PAYTIMES)).setValue(payCol
					.size()+ "");
		}
		
		//如果付款次数为0 手工设定上期申请 实付为0
		if(payCol.size() == 0){
//			editData.setLstPrjAllReqAmt(FDCConstants.ZERO);
			editData.setLstPrjAllPaidAmt(FDCConstants.ZERO);
			editData.setLstAddPrjAllPaidAmt(FDCConstants.ZERO);
			editData.setLstAMatlAllPaidAmt(FDCConstants.ZERO);
//			((ICell) bindCellMap
//					.get(PaymentBillContants.LSTPRJALLREQAMT))
//					.setValue(FDCConstants.ZERO);
			((ICell)bindCellMap
					.get(PaymentBillContants.LSTPRJALLPAIDAMT))
					.setValue(FDCConstants.ZERO);
			((ICell) bindCellMap
					.get(PaymentBillContants.LSTADDPRJALLPAIDAMT))
					.setValue(FDCConstants.ZERO);
			((ICell) bindCellMap
					.get(PaymentBillContants.LSTAMATLALLPAIDAMT))
					.setValue(FDCConstants.ZERO);
		}
		// 付款申请单的已付金额 by sxhong 2007/09/29
		BigDecimal amount = FDCHelper.ZERO;
		for (int i = 0; i < payCol.size(); i++) {
			if (payCol.get(i).getFdcPayReqID().equals(editData.getFdcPayReqID())) {
				amount = amount.add(payCol.get(i).getAmount());
			}
		}
		if(bindCellMap.get(PaymentBillContants.PAYEDAMT) != null){
			((ICell) bindCellMap.get(PaymentBillContants.PAYEDAMT)).setValue(amount);
		}
	}

	// 根据付款帐号自动得到付款银行
	protected void prmtPayerAccountBank_dataChanged(DataChangeEvent e)
			throws Exception {
		Object newValue = e.getNewValue();
		if (e.getOldValue() != null && e.getOldValue().equals(newValue)) {
			return;
		}
		boolean isNotNull = e.getNewValue() != null;
		if (!isNotNull) {
			// 银行科目需要清空
			prmtPayerAccount.setValue(null);
			prmtPayerBank.setValue(null);
			prmtSettleBizType.setData(null);
//			prmtSettleBizType.setEnabled(false);
			prmtPayerAccount.setEnabled(true);
			txtPayeeBank.setRequired(false);
			return;
		}
		if (newValue instanceof AccountBankInfo) {
			AccountBankInfo acc = (AccountBankInfo) prmtPayerAccountBank
					.getValue();
			IBank iBank = BankFactory.getRemoteInstance();
			BankInfo bankInfo = iBank.getBankInfo(new ObjectUuidPK(acc
					.getBank().getId()));
			if (acc != null) {
				prmtPayerAccount.setEnabled(false);
				prmtPayerBank.setValue(bankInfo);
				prmtPayerAccount.setValue(acc.getAccount());
				if(((AccountViewInfo)acc.getAccount()).isIsBank()==true){
//					txtPayeeBank.setRequired(true);
				}else{
					txtPayeeBank.setRequired(false);
				}
			} else {
				logger.info("得不到供应商的财务组织");
			}
			
//			if(acc.getInnerAcct()==null){
//				prmtSettleBizType.setValue(null);
//				
//				comboPayeeType.setSelectedItem(this.supplierAssitType);
//			}else{
//				comboPayeeType.setSelectedItem(innerAccountActType);
//			}
		}else{
			comboPayeeType.setSelectedItem(this.supplierAssitType);
		}
		
		
	}

	protected void prmtPayerAccountBank_willCommit(CommitEvent e)
			throws Exception {
//		AccountViewInfo account = (AccountViewInfo) prmtPayerAccount.getValue();
//		if (account != null) {
//			String accountId = account.getId().toString();
//			setPayerAcctBankEvi(accountId, false);
//		} else {
			setPayerAcctBankEvi(null, false);
//		}
	}

	private void setPayerAcctBankEvi(String accountId, boolean isCapitalUp) {

		Object payee = prmtPayee.getValue();

			String companyId = currentCompany.getId().toString();
	
			prmtPayerAccountBank.getQueryAgent().resetRuntimeEntityView();
	
			EntityViewInfo evi = CasRecPayHandler.getAccountBankEvi(companyId,
					accountId);
			/**
			 * ：非资金上划付款类型，付款账户不能选择收入户性质的账户。资金上划付款类型，可以选择所有收支性质的账户。
			 */
			if (isCapitalUp) {
				evi.getFilter().getFilterItems().add(
						new FilterItemInfo("accountType", new Integer(
								AccountType.PAYIN_VALUE)));
				evi.getFilter().getFilterItems().add(
						new FilterItemInfo("notOutPay", FMConstants.TRUE));
			} else {
				evi.getFilter().getFilterItems().add(
						new FilterItemInfo("accountType", new Integer(
								AccountType.PAYIN_VALUE), CompareType.NOTEQUALS));
				evi.getFilter().getFilterItems().add(
						new FilterItemInfo("notOutPay", FMConstants.FALSE));
			}
	
			prmtPayerAccountBank.setEntityViewInfo(evi);
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("*");
			selectors.add("bank.id");
			selectors.add("bank.name");
			selectors.add("bank.number");
			selectors.add("bank.longNumber");
			selectors.add("currency.id");
			selectors.add("account.number");
			selectors.add("account.name");
			selectors.add("account.isBank");
			prmtPayerAccountBank.setSelectorCollection(selectors);
		
	}

	protected void prmtPayerAccountBank_willShow(SelectorEvent e)
			throws Exception {
//		AccountViewInfo account = (AccountViewInfo) prmtPayerAccount.getValue();
		boolean istranup = false;
//		if (account != null) {
//			String accountId = account.getId().toString();
//			setPayerAcctBankEvi(accountId, istranup);
//		} else {
			setPayerAcctBankEvi(null, istranup);
//		}
	}

	public void actionAntiAudit_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		String id = getSelectBOID();
		if (id != null&&!checkPaymentAllSplit) {
			
			FDCClientUtils.checkBillInWorkflow(this, id);
			
			FilterInfo filterSett = new FilterInfo();
			filterSett.getFilterItems().add(
					new FilterItemInfo("paymentBill.id", id));
			filterSett.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			boolean hasSettleSplit = false;
			if (PaymentSplitFactory.getRemoteInstance().exists(filterSett)) {
				hasSettleSplit = true;
			}
			if (hasSettleSplit) {
				MsgBox.showWarning("付款单已经拆分,不能反审批!");
				SysUtil.abort();
				return;
			}			
			
			PaymentBillFactory.getRemoteInstance().antiAudit4FDC(
					BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		super.actionAntiAudit_actionPerformed(e);
		syncDataFromDB();
		setOprtStateByBillStatus();
	}

	// 提交之前做校验
	protected void verifyBeforeSubmit() throws Exception {
		if (this.editData.getFdcPayReqNumber() == null) {
			MsgBox.showWarning(this, PaymentBillClientUtils.getRes("must"));
			SysUtil.abort();
		}
		FDCClientVerifyHelper.verifyEmpty(this, prmtPayBillType);
		//校验必录项
		//由于收付类别和收款人名称无具体信息提示 设置一下
		if(this.comboFeeType.isRequired()){
			if(comboFeeType.getValue()==null){
				MsgBox.showWarning(this,"收付类别不能为空！");
				SysUtil.abort();
			}
		}
		if(this.prmtPayee.isRequired()){
			if(prmtPayee.getValue()==null){
				MsgBox.showWarning(this,"收款人名称不能为空！");
				SysUtil.abort();
			}
		}
		if(this.txtUsage.isRequired()){
			if(txtUsage.getText()==null){
				MsgBox.showWarning(this,"用途不能为空！");
				SysUtil.abort();
			}
		}
		
//		FDCClientVerifyHelper.verifyEmpty(this, comboFeeType);
		//如果必录设置Required true,非必录为false，这里不用注释
		FDCClientVerifyHelper.verifyEmpty(this, prmtPayerAccount);
		FDCClientVerifyHelper.verifyEmpty(this, prmtCurrency);
		FDCClientVerifyHelper.verifyUIControlEmpty(this);
		
		if(PayReqUtils.isContractBill(editData.getContractBillId()) && isPartA && isPartAMaterialCon){
			if (!isReferenceToPartAConfirm()) // 有关联才需要校验
				paymentBillTableHelper.verifySubmit();
		}
	}

	protected void prmtPayerAccount_dataChanged(DataChangeEvent e)
			throws Exception {
		//2009-1-12 有可能是从“审批”状态下的单据切换到“保存”状态下的单据，此时科目为空，应加上空值判断
		if(e.getNewValue() == null){
			prmtPayerAccountBank.setEnabled(true);
			return;
		}
		if(((AccountViewInfo)e.getNewValue()).isIsBank()==true){
//			txtPayeeBank.setRequired(true);
		}else{
			txtPayeeBank.setRequired(false);
		}
		//R110303-069：保存和已提交状态付款单应该能修改付款账号
		if(prmtPayerAccountBank.getValue() == null && prmtPayerAccount.isEnabled()){
			if (BillStatusEnum.SAVE.equals(editData.getBillStatus()) || BillStatusEnum.SUBMIT.equals(editData.getBillStatus())) {
				prmtPayerAccountBank.setEnabled(true);	
			} else {
				prmtPayerAccountBank.setEnabled(false);		
			}
		}
		
		if (OprtState.VIEW.equals(getOprtState())) {
			return;
		}
	}

	protected void prmtPayerAccount_willCommit(CommitEvent e) throws Exception {

		CurrencyInfo currencyInfo = (CurrencyInfo) prmtCurrency.getValue();
		if (currencyInfo == null) {
			e.setCanceled(true);
			return;
		}
		setPayerAcctEvi(currencyInfo);
	}

	protected void prmtPayerAccount_willShow(SelectorEvent e) throws Exception {

		CurrencyInfo currencyInfo = (CurrencyInfo) prmtCurrency.getValue();
		if (currencyInfo == null) {
			//币别为空时给出提示
			FDCMsgBox.showWarning(this, "请先选择币别!");
			prmtPayerAccount.setValue(null);
			e.setCanceled(true);
			return;
		}
		setPayerAcctEvi(currencyInfo);
	}

	private void setPayerAcctEvi(CurrencyInfo currencyInfo)
			throws EASBizException, BOSException {
		String currencyId = currencyInfo.getId().toString();
		String companyId = currentCompany.getId().toString();
		String cuId = currentCompany.getCU().getId().toString();

		prmtPayerAccount.getQueryAgent().resetRuntimeEntityView();

		// 根据付款方式取得过滤条件
		EntityViewInfo treeevi = CasRecPayHandler.getAccountViewEvi(cuId,
				companyId, currencyId, false);

		AccountPromptBox opseelect = new AccountPromptBox(this, currentCompany,
				treeevi.getFilter(), false, true);
		prmtPayerAccount.setSelector(opseelect);
		EntityViewInfo evi = CasRecPayHandler.getAccountViewEvi(cuId,
				companyId, currencyId, true);

		prmtPayerAccount.setEntityViewInfo(evi);
	}

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSplit_actionPerformed(e);
		String id = getSelectBOID();
		String contractId = editData.getContractBillId();
		if (id == null || contractId==null){
			return;
		}
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("localAmt");
		PaymentBillInfo info = (PaymentBillInfo)getBizInterface().getValue(new ObjectUuidPK(id), selector);
		if(FDCHelper.toBigDecimal(info.getLocalAmt(),2).compareTo(FDCHelper.toBigDecimal(txtLocalAmt.getBigDecimalValue(),2))!=0){
			FDCMsgBox.showWarning(this, "此付款单对应的付款申请单存在多次付款，请提交后重新拆分！");
			return;
		}
		Map param = new HashMap();
		param.put("isSimpleFinancial", Boolean.valueOf(isSimpleFinancial));
		param.put("isAdjustVourcherModel", Boolean.valueOf(isAdjustVourcherModel));
		PaymentSplitHelper.checkPaymentVouchered(id, contractId, param);
		
		
		AbstractSplitInvokeStrategy invokeStra = new PaymentSplitInvokeStrategy(
				id, this);
		invokeStra.invokeSplit();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		btnSplit.setIcon(FDCClientHelper.ICON_SPLIT);
		menuItemSplit.setAccelerator(KeyStroke.getKeyStroke("alt shift S"));
		actionViewBgBalance.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_check"));
		actionAddNew.setEnabled(false);
		actionAddLine.setEnabled(false);
		actionRemoveLine.setEnabled(false);
		actionInsertLine.setEnabled(false);
		txtAllCompletePrjAmt.setHorizontalAlignment(JTextField.RIGHT);
		txtQualityGuarante.setHorizontalAlignment(JTextField.RIGHT);
		txtInvoiceAmt.setHorizontalAlignment(JTextField.RIGHT);
		txtAllInvoiceAmt.setHorizontalAlignment(JTextField.RIGHT);
		//if(isSimpleFinancial || isAutoComplete || (isSeparate && isCostSplit)){
		if((isSeparate && isCostSplit) || isAutoComplete){
			contcompletePrjAmt.setVisible(false);
			contpaymentProportion.setVisible(false);
			contAllCompletePrjAmt.setVisible(false);
		}
		prmtCreator.setRequired(false);
		pkCreateTime.setRequired(false);
		actionApprove.putValue("SmallIcon", EASResource.getIcon("imgTbtn_auditing"));
	    actionUntiApprove.putValue("SmallIcon", EASResource.getIcon("imgTbtn_fauditing"));
	}

	/**
	 * 如单据已经拆分,在修改单据金额的时候给予提示重新拆分
	 */
	boolean amtWarned = false;

	protected void txtAmount_focusGained(FocusEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.txtAmount_focusGained(e);

		if (STATUS_EDIT.equals(getOprtState()) && !amtWarned) {
			boolean isCostSplit = FDCSplitClientHelper.isBillSplited(
					getSelectBOID(), "T_FNC_PaymentSplit", "FPaymentBillID");
			boolean isNoCostSplit = FDCSplitClientHelper.isBillSplited(
					getSelectBOID(), "T_FNC_PaymentNoCostSplit",
					"FPaymentBillID");
			if (isCostSplit || isNoCostSplit) {
				MsgBox.showConfirm2(this, ContractClientUtils
						.getRes("spltedWarningForAmtChg"));
			}
			amtWarned = true;
		}
	}

	public void actionViewBgBalance_actionPerformed(ActionEvent e)
			throws Exception {
		storeFields();
		PaymentBillInfo info = editData;
		BgCtrlParamCollection paramColl = BgCtrlPaymentBillHandler
				.getParamColl(info);

		String boName = RecPayHelper.BO_NAME_PAY;
		FMClientHelper.viewBgBalance(this, boName, paramColl, info);
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		
		if(initData==null){
			return ;
		}
		
		Object source = e.getSource();
		if (source == btnCreateFrom || source == menuItemCreateFrom) {
			if (txtNumber.isEnabled()) {
				txtNumber.requestFocusInWindow();
			} else {
				pkBizDate.requestFocusInWindow();
			}
		} else if (source == btnEdit || source == menuItemEdit) {
			prmtCompany.setEditable(false);
			prmtFdcPayType.setEditable(false);
			prmtuseDepartment.setEditable(false);
			//prmtPayee.setEditable(false);
//			prmtActFdcPayeeName.setEditable(false);
			prmtCreator.setEditable(false);
			prmtAuditor.setEditable(false);
			prmtCashier.setEditable(false);
			prmtAccountant.setEditable(false);
		}
		
		if (source == btnNext || source == menuItemNext 
				|| source == btnPre || source == menuItemPre
				|| source == btnFirst || source == menuItemFirst
				|| source == btnLast || source == menuItemLast) {
			// 隐藏以前的kdtEntries用自己的kdtable替代
			Rectangle kdtRectangle = kdtEntries.getBounds();
			kdtEntries.setEnabled(false);
			kdtEntries.setVisible(false);
			kdtEntries = createPaymentBillTable();
			kdtEntries.setBounds(kdtRectangle);
			// pnlPayBillEntry.add(kdtEntries, null);
			
	
			pnlPayBillEntry.add(kdtEntries, new KDLayout.Constraints(10, 10, 850,
					450, KDLayout.Constraints.ANCHOR_TOP
							| KDLayout.Constraints.ANCHOR_BOTTOM_SCALE
							| KDLayout.Constraints.ANCHOR_LEFT_SCALE
							| KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
			
			getDetailTable().setEnabled(false);
			getDetailTable().setEditable(false);
			kdtEntries.getCell(4,5).getStyleAttributes().setLocked(true);
			setTableCellColorAndEdit(false) ;
			// 去掉付款计划的格:
			int lastRowIdx = kdtEntries.getRowCount() - 1;
			for (int i = 4; i < kdtEntries.getColumnCount(); i++) {
				StyleAttributes styleAttributes = kdtEntries.getCell(lastRowIdx - 1, i)
						.getStyleAttributes();
				styleAttributes.setLocked(true);
				styleAttributes.setBackground(noEditColor);
			}
			kdtEntries.getCell(lastRowIdx - 1, 1).getStyleAttributes().setLocked(true);
			kdtEntries.getCell(lastRowIdx - 1, 1).getStyleAttributes().setBackground(noEditColor);
			
			kdtEntries.getCell(9,5).getStyleAttributes().setLocked(true);
			kdtEntries.getCell(lastRowIdx - 1,6).getStyleAttributes().setLocked(true);
//		}
		}
	}

	protected void prmtPayee_willShow(SelectorEvent e) throws Exception {
		if (comboPayeeType.getSelectedItem() == null
				|| comboPayeeType.getSelectedItem() instanceof FMSysDefinedEnum) {
			e.setCanceled(true);
			return;
		}
	}
	
	protected void prmtPayee_willCommit(SelectorEvent e) throws Exception {
		if (comboPayeeType.getSelectedItem() == null
				|| comboPayeeType.getSelectedItem() instanceof FMSysDefinedEnum) {
			e.setCanceled(true);
			return;
		}

	}
	
	private CustomerInfo getCustomer(Object objCust) {
		if (objCust instanceof CustomerInfo)
			return (CustomerInfo) objCust;
		else
			return null;
	}
	
	private SupplierInfo getSupplier(Object objCust) {
		if (objCust instanceof SupplierInfo)
			return (SupplierInfo) objCust;
		else
			return null;
	}
	
	protected void prmtPayee_dataChanged(DataChangeEvent e) throws Exception {

		Object payee = prmtPayee.getValue();
		if (payee instanceof SupplierInfo) {
			SupplierInfo supplier = (SupplierInfo) payee;
			// 根据收款人带出收款行和账号
			if (supplier != null) {
				
				SelectorItemCollection sic = super.getSelectors();
				sic.add(new SelectorItemInfo("*"));
				sic.add(new SelectorItemInfo("province.*"));
				sic.add(new SelectorItemInfo("city.*"));
			 
				SupplierInfo realSupplier=SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(supplier.getId()),sic);
				txtrecProvince.setData(realSupplier.getProvince());
				txtrecCity.setData(realSupplier.getCity());
				
				BOSUuid supplierid = supplier.getId();
				
				//供应商的获取			
				String supperid = supplierid.toString();			
				PayReqUtils.fillBank(editData,supperid,curProject.getCU().getId().toString());	
				txtPayeeAccountBank.setText(editData.getPayeeAccountBank());
				txtPayeeBank.setText(editData.getPayeeBank());
				
//				SupplierCompanyInfoInfo supplierCompany = SupplierFactory
//						.getRemoteInstance().getCompanyInfo(
//								new ObjectUuidPK(supplier.getId()),
//								new ObjectUuidPK(currentCompany.getId()));
//				if (supplierCompany != null) {
//
//					txtPayeeAccountBank.setText(supplierCompany
//							.getBankAccount());
//					txtPayeeBank.setText(supplierCompany.getBankName());
//					
//				} else {
//					txtPayeeAccountBank.setText(null);
//					txtPayeeBank.setText(null);
//				}

			}

		} else if (payee instanceof CustomerInfo) {

			CustomerInfo cust = this.getCustomer(prmtPayee.getData());

			// 根据收款人带出收款行和账号
			if (cust != null) {
				
				SelectorItemCollection sic = super.getSelectors();
				sic.add(new SelectorItemInfo("*"));
				sic.add(new SelectorItemInfo("province.*"));
				sic.add(new SelectorItemInfo("city.*"));
			 
				CustomerInfo realCust=CustomerFactory.getRemoteInstance().getCustomerInfo(new ObjectUuidPK(cust.getId()),sic);
				txtrecProvince.setData(realCust.getProvince());
				txtrecCity.setData(realCust.getCity());
				
				CustomerCompanyInfoInfo custCompany = CustomerFactory
						.getRemoteInstance().getCustomerCompanyInfo(
								new ObjectUuidPK(cust.getId()),
								new ObjectUuidPK(currentCompany.getId()));
				if (custCompany != null) {
					// txtPayeeAccountBank.setText(custCompany.getBankAccount());
					txtPayeeAccountBank.setText(custCompany.getBankAccount());
					txtPayeeBank.setText(custCompany.getBank());
				} else {
					txtPayeeAccountBank.setText(null);
					txtPayeeBank.setText(null);
				}

			}

		} else if (payee instanceof InnerAccountInfo) {

			InnerAccountInfo info = (InnerAccountInfo) payee;

			// 根据收款人带出收款行和账号
			if (info != null) {
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("innerAcct.id", info.getId()
								.toString()));
				ev.setFilter(filter);
				SelectorItemCollection selector = ev.getSelector();
				selector.add("*");
				selector.add("bank.*");

				AccountBankCollection accountBankColl = AccountBankFactory
						.getRemoteInstance().getAccountBankCollection(ev);
				if (accountBankColl != null && accountBankColl.size() == 1) {
					AccountBankInfo accountBankInfo = accountBankColl.get(0);
					txtPayeeBank.setText(accountBankInfo.getBank().getName());
					txtPayeeAccountBank.setText(accountBankInfo
							.getBankAccountNumber());
//					txtPayeeAcctName.setText(accountBankInfo.getName());

				}
			}

		} else if (payee instanceof AccountBankInfo) {

			AccountBankInfo info = (AccountBankInfo) payee;

			// 根据收款人带出收款行和账号
			if (info != null) {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("*");
				selector.add("bank.*");

				info = AccountBankFactory.getRemoteInstance()
						.getAccountBankInfo(new ObjectUuidPK(info.getId()),
								selector);
				txtPayeeBank.setText(info.getBank().getName());
				txtPayeeAccountBank.setText(info.getBankAccountNumber());
//				txtPayeeAcctName.setText(info.getName());

			}
		}

	}
	
	protected void prmtPayeeArea_willShow(SelectorEvent e) throws Exception {
		setPayeeAreaEvi();
	}

	protected void prmtPayeeArea_willCommit(CommitEvent e) throws Exception {
		setPayeeAreaEvi();
	}

	private void setPayeeAreaEvi() {
		AccountBankInfo bankaccount = (AccountBankInfo) prmtPayerAccountBank
				.getValue();
		BankInterfaceTypeEnum type = null;
		if (bankaccount != null) {
			type = bankaccount.getBankInterfaceType();

		}
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (type != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("bankInterfaceType", new Integer(type
							.getValue())));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));

		}
		ev.setFilter(filter);
		prmtPayeeArea.setEntityViewInfo(ev);
		prmtPayeeArea.getQueryAgent().resetRuntimeEntityView();

	}

	protected void verifyInput(ActionEvent e) throws Exception {
		checkIsAllInvoicAmtMoreThanConLatestPrice(editData, FDCHelper.toBigDecimal(this.txtInvoiceAmt.getBigDecimalValue()));
		if (!prmtSettleBizType.isEnabled()) {
			prmtSettleBizType.setRequired(false);
		}
		
		SettlementTypeInfo settType1 = (SettlementTypeInfo) prmtSettlementType
				.getData();
		if (settType1 != null) {
			if (settType1.getNtType() != null) {//
				if (NTTypeGroupEnum.PaymentCheque.equals(settType1.getNtType()
						.getSuperGroup())) {// R100130-010
					Object oCheque = f7SettleNumber.getData();
					if (oCheque instanceof ChequeInfo) {
						ChequeInfo cheque = (ChequeInfo) oCheque;
						if (cheque.getId() != null) {
							AccountBankInfo accountBankInfo = (AccountBankInfo) prmtPayerAccountBank
									.getValue();
							if (accountBankInfo != null
									&& (cheque.getBankAcct() == null || !cheque
											.getBankAcct().getId().equals(
													accountBankInfo.getId()))
									&& (cheque.getBank() == null || !cheque
											.getBank().getId().equals(
													accountBankInfo.getBank()
															.getId()))) {
								MsgBox.showWarning("付款账户或付款银行与票据不符，请返回修改！");
								SysUtil.abort();
							}
						}
					}
					/*
					 * String chequeNumber=f7SettleNumber.getText();
					 * if(chequeNumber==null||chequeNumber.trim().equals("")){
					 * }else{ FilterInfo filter=new FilterInfo();
					 * filter.getFilterItems().add(new
					 * FilterItemInfo("number",chequeNumber)); EntityViewInfo
					 * evi=new EntityViewInfo();
					 * 
					 * FilterInfo filter2 = new FilterInfo();
					 * filter2.getFilterItems().add( new
					 * FilterItemInfo("keepCompany.id", currentCompany.getId()
					 * .toString())); filter2.getFilterItems().add( new
					 * FilterItemInfo("company.id", currentCompany.getId()
					 * .toString())); filter2.getFilterItems().add(new
					 * FilterItemInfo("state", new
					 * Integer(ChequeStatusEnum.NEW_VALUE)));
					 * filter2.setMaskString("(#0 OR #1) AND #2");
					 * 
					 * filter.mergeFilter(filter2, "AND");
					 * evi.setFilter(filter); ChequeCollection
					 * chequeColl=ChequeFactory
					 * .getRemoteInstance().getChequeCollection(evi);
					 * if(chequeColl==null||chequeColl.size()==0){
					 * MsgBox.showWarning("支票已被使用，请选择其它支票！"); SysUtil.abort(); }
					 * }
					 */
				}
			}
		}

		// 付款帐户币别与付款币别的效验
		if (prmtPayerAccountBank.getValue() != null) {

			CurrencyInfo Crinfo = (CurrencyInfo) prmtCurrency.getValue();
			AccountBankInfo acctBankInfo = (AccountBankInfo) prmtPayerAccountBank
					.getValue();
			CurrencyInfo acctBankCrinfo = (CurrencyInfo) acctBankInfo
					.getCurrency();
			if (acctBankInfo.isIsByCurrency()) {
				if (!acctBankCrinfo.getId().equals(Crinfo.getId())) {

					MsgBox.showWarning(this, "付款账户的单一币别不等于付款币别");
					SysUtil.abort();
				}
			}

		}

		// 如果付款账户已设置银行接口，且收款人、收款银行、收款账户、省份、市/县任一为空时，付款单保存时提醒用户录入，
		// 如果用户确认不提交银企付款可以继续保存。
		AccountBankInfo acctBankInfo = (AccountBankInfo) prmtPayerAccountBank
				.getValue();
		if (acctBankInfo != null && acctBankInfo.isIsSetBankInterface()) {

			if (prmtPayee.getValue() == null
					|| FMHelper.isEmpty(txtPayeeBank.getText())
					|| FMHelper.isEmpty(txtPayeeAccountBank.getText())
					||( prmtPayeeArea.getValue() == null&&txtrecCity.getValue()==null) && prmtPayeeArea.getValue() == null || prmtFRecCountry.getValue() == null) {

				String msg = EASResource.getString(CasRecPayHandler.srcPath,
						"acctBankHasInterface");
				int result = MsgBox.showConfirm2(this, msg);
				if (result == MsgBox.CANCEL) {
					SysUtil.abort();
				}
			}
		}

		// 当结算方式不为空时,付款科目和付款帐户必须录一个
		if (prmtSettlementType.getValue() != null) {
			if (prmtPayerAccount.getValue() == null
					&& prmtPayerAccountBank.getValue() == null) {
				String msg = EASResource.getString(CasRecPayHandler.srcPath,
						"must_have_payer_AccountView_or_Accountbank");
				/***
				 * 建发提出，付款科目，可以不用必录；
				 * 此提示注释
				 */
//				MsgBox.showError(this, msg);
//				SysUtil.abort();
			}
		}
		 FMClientVerifyHelper.verifyAEqualsB(this, prmtCompany, prmtAgentCompany);
		 Map agentRelatedProps = new HashMap();
		 agentRelatedProps.put("ui", this);
		 agentRelatedProps.put("editData", editData);
		 // 代理收付的initDataStatus
		//如果不为代理收款，直接返回
			if(prmtAgentCompany.getValue()==null){
				return ;
			}
			
			//如果付款单的付款账户是外部银行账号、且关联有内部账户，只能选择对外收款类型的业务种类
			AccountBankInfo acctBank=(AccountBankInfo) prmtPayerAccountBank.getValue();
			
			if(acctBank!=null&&acctBank.getInnerAcct()!=null&&prmtSettleBizType.getValue()!=null){
				SettBizTypeInfo bizTypeInfo = (SettBizTypeInfo) prmtSettleBizType.getData();
				if(!SettBizTypeEnum.PAYOUTSIDE.equals(bizTypeInfo.getType())
						&& !SettBizTypeEnum.LINKPAY.equals(bizTypeInfo.getType())){
//					MsgBox.showError(CasRecPayHandler.srcPath, "payerAcctBankRelInnerAcct");
					MsgBox.showError("如果付款单的付款账户是外部银行账号、且关联有内部账户，只能选择对外收款类型的业务种类");
					SysUtil.abort();
				}
			}
	}

	protected void prmtSettlementType_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtSettlementType_dataChanged(e);
		Object objNew = e.getNewValue();
		if (objNew == null)
			return;
		if (objNew instanceof SettlementTypeInfo
				&& ((SettlementTypeInfo) objNew).getId().toString().equals(
						"e09a62cd-00fd-1000-e000-0b32c0a8100dE96B2B8E")) {
			// 结算方式为付款则银行及账号为非必录
//			txtPayeeBank.setRequired(false);
//			txtPayeeBank.repaint();
			//txtPayeeAccountBank.setRequired(false);
//			txtPayeeAccountBank.repaint();
		} else {
//			txtPayeeBank.setRequired(true);
//			txtPayeeBank.repaint();
			//txtPayeeAccountBank.setRequired(true);
//			txtPayeeAccountBank.repaint();
		}
		
		SettlementTypeInfo settType = (SettlementTypeInfo) prmtSettlementType
				.getData();
		if (settType != null) {
			if (settType.getNtType() != null) {//
				if (NTTypeGroupEnum.PaymentCheque.equals(settType.getNtType()
						.getSuperGroup())
					) {// R100130-010
					f7SettleNumber.setQueryInfo("com.kingdee.eas.fm.nt.app.ChequeQuery");
					if (f7SettleNumber.isEnabledMultiSelection()) {
						String tempSettle = null;
						if (f7SettleNumber.getData() instanceof String) {
							tempSettle = (String) f7SettleNumber.getData();
							ChequeInfo cheque = new ChequeInfo();
							cheque.setNumber(tempSettle);
							f7SettleNumber.setData(new Object[] { cheque });
						}
						f7SettleNumber.setEnabledMultiSelection(false);
						if (f7SettleNumber.getData() instanceof ChequeInfo) {
							f7SettleNumber.setData(tempSettle);
						}
					}
				} else {
					f7SettleNumber.setEnabledMultiSelection(true);
				}
			}
}
	}
	

    /**
     * output txtPayeeAccountBank_willCommit method
     */
    protected void txtPayeeAccountBank_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
		Object payee = prmtPayee.getValue();
		if (payee instanceof SupplierInfo) {
			SupplierInfo supply=(SupplierInfo)payee;
			initSupplierCompanyInfoBank(supply,txtPayeeAccountBank);
		}
		else if(payee instanceof CustomerInfo){
			CustomerInfo customer=(CustomerInfo)payee;
			initCustomerCompanyInfoBank(customer,txtPayeeAccountBank);
		}
		else {
			//initPayeeAcctBankASAcctBank();
			setPayerAcctBankEvi(null, false);
		}
    	
    }
    
	private void initCustomerCompanyInfoBank(CustomerInfo customer, KDBizPromptBox txtPayeeAccountBank) {
		txtPayeeAccountBank.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7CustomerCompanyInfoBankQuery");
		txtPayeeAccountBank.setHasCUDefaultFilter(false);
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		if(customer!=null) {
			filter.getFilterItems().add(new FilterItemInfo("customer.id",customer.getId()));
		}
		filter.getFilterItems().add(new FilterItemInfo("companyOrgUnit.id",currentCompany.getId()));
		view.setFilter(filter);
		txtPayeeAccountBank.setEntityViewInfo(view);
		txtPayeeAccountBank.setEditFormat("$bankAccount$");
		txtPayeeAccountBank.setDisplayFormat("$bankAccount$");
		txtPayeeAccountBank.getQueryAgent().resetRuntimeEntityView();
		
	}

	private void initSupplierCompanyInfoBank(SupplierInfo supply, KDBizPromptBox txtPayeeAccountBank) {
		txtPayeeAccountBank.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierCompanyInfoBankQuery");
		txtPayeeAccountBank.setHasCUDefaultFilter(false);
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		if(supply!=null) {
			filter.getFilterItems().add(new FilterItemInfo("supplier.id",supply.getId()));
		}
		filter.getFilterItems().add(new FilterItemInfo("companyOrgUnit.id",currentCompany.getId()));
		view.setFilter(filter);
		txtPayeeAccountBank.setEntityViewInfo(view);
		txtPayeeAccountBank.setEditFormat("$bankAccount$");
		txtPayeeAccountBank.setDisplayFormat("$bankAccount$");
		txtPayeeAccountBank.getQueryAgent().resetRuntimeEntityView();
		
	}

    /**
     * output txtPayeeAccountBank_willShow method
     */
    protected void txtPayeeAccountBank_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    	
		
		//如果是供应商则不能修改
		Object  obj = comboPayeeType.getSelectedItem();
		if(obj instanceof AsstActTypeInfo ){
			AsstActTypeInfo asstActTypeInfo = (AsstActTypeInfo)obj;
			if ("provider".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())){
				
				String payeeId = editData.getPayeeID();
				if(payeeId!=null){
					SupplierInfo supply = new SupplierInfo();
					supply.setId(BOSUuid.read(payeeId));
				
					initSupplierCompanyInfoBank(supply,txtPayeeAccountBank);
				}
			}else {
				//initPayeeAcctBankASAcctBank();
				setPayerAcctBankEvi(null, true);
			}
		}
		
//		Object payee = prmtPayee.getValue();
//		if (payee instanceof SupplierInfo) {
//			SupplierInfo supply=(SupplierInfo)payee;
//			initSupplierCompanyInfoBank(supply,txtPayeeAccountBank);
//		}
//		else if(payee instanceof CustomerInfo){
//			CustomerInfo customer=(CustomerInfo)payee;
//			initCustomerCompanyInfoBank(customer,txtPayeeAccountBank);
//		}
//		else {
//			//initPayeeAcctBankASAcctBank();
//			setPayerAcctBankEvi(null, true);
//		}
//    	
    }

    
    
    /**
     * output txtPayeeAccountBank_dataChanged method
     */
    protected void txtPayeeAccountBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
		if (e.getNewValue() != null && !e.getNewValue().equals(e.getOldValue())
				&& e.getNewValue() instanceof AccountBankInfo) {
			AccountBankInfo acctbank = (AccountBankInfo) e.getNewValue();
			editData.setPayeeAccountBankO(acctbank);
			if (acctbank.getBank() != null) {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
//				selector.add("company.*");
				selector.add("bank.number");
				selector.add("bank.name");
				AccountBankInfo bank = AccountBankFactory.getRemoteInstance()
						.getAccountBankInfo(new ObjectUuidPK(acctbank.getId()),
								selector);
//				if (!type.getId().toString().equals(
//						PaymentBillTypeInfo.TRANS_UP)) {
					txtPayeeBank.setText(bank.getBank().toString());

//				}
			}

		} else if (e.getNewValue() != null && !e.getNewValue().equals(e.getOldValue())
				&& e.getNewValue() instanceof SupplierCompanyBankInfo) {

			SupplierCompanyBankInfo accountbank = (SupplierCompanyBankInfo)e.getNewValue();
			//editData.setPayeeAccountBankO(accountbank.geta);
			txtPayeeBank.setText(accountbank.getBank());
			
		} else if (e.getNewValue() == null) {
			editData.setPayeeAccountBankO(null);
			txtPayeeBank.setText(null);
		}
    }

	private void checkContractSplitState() {
		String contractBillId = editData.getContractBillId();
		if (contractBillId == null) {
			contractBillId = (String) getUIContext().get("contractBillId");
		}
		if (contractBillId != null) {
			if (!ContractClientUtils.getContractSplitState(contractBillId)) {
				MsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("conNotSplited"));
				SysUtil.abort();
			}
		}
	}

	protected void prmtSettleBizType_dataChanged(DataChangeEvent e) throws Exception {
		
		SettBizTypeInfo bizTypeInfo = (SettBizTypeInfo) prmtSettleBizType.getValue();

		if (bizTypeInfo != null) {			
			if (SettBizTypeEnum.PAYINSIDE.equals(bizTypeInfo.getType())) {// 默认收款人类型未内部帐户	
				if (FMHelper.isEmpty(prmtPayee.getData())) {
					if (innerAccountActType != null) {
						FMClientHelper.setSelectObject(comboPayeeType,innerAccountActType);
					}
				}
			}
		}else{
			if (supplierAssitType != null) {
				FMClientHelper.setSelectObject(comboPayeeType,supplierAssitType);
			}
		}
		
		//FMClientHelper.setSelectObject(comboPayeeType,innerAccountActType);
	}
	
	protected void prmtSettleBizType_willCommit(CommitEvent e) throws Exception {
		super.prmtSettleBizType_willCommit(e);
		AccountBankInfo accBank = (AccountBankInfo) prmtPayerAccountBank
				.getData();
		prmtSettleBizType.setEntityViewInfo(getBizTypeEV(accBank));
		prmtSettleBizType.getQueryAgent().resetRuntimeEntityView();
	}
	
	protected void prmtSettleBizType_willShow(SelectorEvent e) throws Exception {
		super.prmtSettleBizType_willShow(e);
		AccountBankInfo accBank = (AccountBankInfo) prmtPayerAccountBank
				.getData();
		prmtSettleBizType.setEntityViewInfo(getBizTypeEV(accBank));
		prmtSettleBizType.getQueryAgent().resetRuntimeEntityView();
	}
	
    protected void initListener()
    {
    	//super.initListener();
    }
    
    public void checkModified() throws Exception {    	
    	super.checkModified() ;
    }
    
    protected boolean isUseMainMenuAsTitle(){
    	return false;
    }
    
    protected void kdtPartA_editStopped(KDTEditEvent e) throws Exception {
    	super.kdtPartA_editStopped(e);
    	paymentBillTableHelper.kdtPartA_editStopped(e);
    }
    
    /**
     * 当存在甲供主合同列表时，校验表中数据是否改变.<p>
     */
    public boolean isModify() {
    	
    	if(PayReqUtils.isContractBill(editData.getContractBillId()) && isPartA && isPartAMaterialCon){
    		return paymentBillTableHelper.isModify()?true:super.isModify();
    	}
    	try {
			if (editData != null && editData.getId() != null) {
				//删除后数据库中已不存在，直接返回false by hpw 2010-06-25
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString()));
				boolean isExist = getBizInterface().exists(filter);
				if (!isExist) {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
    	return super.isModify();
    }

    /**
     * 描述：汇率或合同内工程款发生变化时，重新计算本币
     */
    private void setProjectPriceInContract(){
    	Object object = bindCellMap
				.get(PaymentBillContants.PROJECTPRICEINCONTRACTORI);
		if (object instanceof ICell) {
			ICell cell = (ICell) object;
			if (cell.getValue() != null) {
				BigDecimal originalAmount = FDCHelper.toBigDecimal(cell
						.getValue());
				BigDecimal amount = originalAmount.multiply(FDCHelper
						.toBigDecimal(txtExchangeRate.getBigDecimalValue()));
				
				if(this.editData.getFdcPayReqID()!=null&&prmtCurrency.getValue()!=null){
					SelectorItemCollection sel=new SelectorItemCollection();
					sel.add("currency.id");
					sel.add("originalAmount");
					sel.add("amount");
					
					try {
						PayRequestBillInfo	pay = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
						KDTable table = getDetailTable();
						CurrencyInfo currency=(CurrencyInfo)prmtCurrency.getValue();
						BigDecimal localamount =FDCHelper.ZERO;
						BigDecimal exchangeRate = toBigDecimal(txtExchangeRate.getNumberValue());
						if(!pay.getCurrency().getId().toString().equals(currency.getId().toString())){
							amount=pay.getAmount();
						}
						
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}
				getDetailTable().getCell(cell.getRowIndex(),
						cell.getColumnIndex() + 1).setValue(amount);
				amtInContractOri = originalAmount;
				editData.setAddProjectAmt(originalAmount);//这样为什么保存不了叱?
				editData.setProjectPriceInContract(amount);
			}else{
				getDetailTable().getCell(cell.getRowIndex(),
						cell.getColumnIndex() + 1).setValue(null);
				editData.setAddProjectAmt(null);//这样为什么保存不了叱?
				editData.setProjectPriceInContract(null);
			}
			BigDecimal paidAmt = FDCHelper.toBigDecimal(cell.getValue());
			resetConfirmBillEntrysPaidAmt(paidAmt);
		}
    }

	private void resetConfirmBillEntrysPaidAmt(BigDecimal paidAmt) {
		if(this.initData.containsKey("PayRequestBillConfirmEntryCollection")){
			PayRequestBillConfirmEntryCollection colls = (PayRequestBillConfirmEntryCollection)initData.get("PayRequestBillConfirmEntryCollection");
			for(int i=0;i<colls.size();i++){
				PayRequestBillConfirmEntryInfo info = colls.get(i);
				if(paidAmt.compareTo(FDCHelper.toBigDecimal(info.getReqAmount()))>0){
					info.setPaidAmount(info.getReqAmount());
				}else{
					info.setPaidAmount(paidAmt);
				}
				paidAmt = paidAmt.subtract(FDCHelper.toBigDecimal(info.getPaidAmount()));
				if(!info.containsKey("notIncludeThisPaidAmt")){
					BigDecimal notIncludeThisPaidAmt = FDCHelper.toBigDecimal(info.getConfirmBill().getPaidAmt()).subtract(FDCHelper.toBigDecimal(info.getPaidAmount()));
					info.put("notIncludeThisPaidAmt", notIncludeThisPaidAmt);
				}
				BigDecimal confirmPaidAmt = FDCHelper.toBigDecimal(info.get("notIncludeThisPaidAmt")).add(info.getPaidAmount());
				info.getConfirmBill().setPaidAmt(confirmPaidAmt);
				if(paidAmt.compareTo(FDCHelper.ZERO)<=0)
					break;
			}
		}
	}
    /**
     * 描述：汇率或预付款原币发生变化时，重新计算本币
     */
    private void setAdvance(){
    	Object object = bindCellMap
				.get(PaymentBillContants.ADVANCE);
		if (object instanceof ICell) {
			ICell cell = (ICell) object;
			if (cell.getValue() != null) {
				BigDecimal originalAmount = FDCHelper.toBigDecimal(cell
						.getValue());
				BigDecimal amount = originalAmount.multiply(FDCHelper
						.toBigDecimal(txtExchangeRate.getBigDecimalValue()));
				getDetailTable().getCell(cell.getRowIndex(),
						cell.getColumnIndex() + 1).setValue(amount);
				//保存或提交时保存至editdata
				if(editData.getPrjPayEntry()==null){
					editData.setPrjPayEntry(new PaymentPrjPayEntryInfo());
				}
				editData.getPrjPayEntry().setAdvance(originalAmount);
				editData.getPrjPayEntry().setLocAdvance(amount);
			}
		}
    }
    
    protected void kdtEntries_editStopped(KDTEditEvent e) throws Exception {
    	int rowIndex = 4;
		int colIndex = 4;
		if (e.getRowIndex() == rowIndex
				&& e.getColIndex() == colIndex) {
			setProjectPriceInContract();
			setAmount();
		}
		if (e.getValue() != null && e.getRowIndex() == rowIndex+1
				&& e.getColIndex() == colIndex) {
			if(this.isAdvance())
				setAdvance();
		}
		this.initMaterial();
    }
    public void initMaterial() throws EASBizException, BOSException{
    	if(this.initData.containsKey("PayRequestBillConfirmEntryCollection")){
    		PayRequestBillConfirmEntryCollection mcBills = (PayRequestBillConfirmEntryCollection)initData.get("PayRequestBillConfirmEntryCollection");
    		if (mcBills!=null&&mcBills.size()>0){
        		paymentBillTableHelper.loadMaterialData(mcBills);
        	}else{
        		this.tbpPaymentBill.remove(this.pnlPayBillMaterial);
        	}
    	}
    	
    }
    
    /**
     * 描述：甲供页签初始化
     * @author pengwei_hou Date: 2008-12-06
     * @throws EASBizException
     * @throws BOSException
     */
    public void initPartA() throws EASBizException, BOSException{
    	
    	if (PayReqUtils.isContractBill(editData.getContractBillId())) {

			ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(
					"select isPartAMaterialCon, hasSettled where id = '" + editData.getContractBillId() + "'");
			
			isPartAMaterialCon = contractBillInfo.isIsPartAMaterialCon();
			hasSettled = contractBillInfo.isHasSettled();
			// 甲供材参数为true时
			if (isPartA && isPartAMaterialCon) {
				kdtPartA.checkParsed();
				paymentBillTableHelper.setPartATable();
				paymentBillTableHelper.setPartAMainContractF7(editData);
				paymentBillTableHelper.loadPartAData();
				paymentBillTableHelper.addTotalRow();
				paymentBillTableHelper.sumTable();
				paymentBillTableHelper.initCtn(ctnPartAEntrys);
				paymentBillTableHelper.setPartAButtonStatus(getOprtState());
				
				// 虽然是甲供材合同，但如果付款申请单没有关联到材料确认单时，也要隐藏甲供页签
				// 参见补丁需求 R101203-220 Added By Owen_wen 2011-01-14
				if (this.initData.containsKey("PayRequestBillConfirmEntryCollection")) {
					if (isReferenceToPartAConfirm()) {
						this.tbpPaymentBill.remove(this.pnlPayBillPartA);
					}
				}
			} else {
				this.tbpPaymentBill.remove(this.pnlPayBillPartA);
			}
		}
    }

	/**
	 * 甲供材合同，判断付款申请单有没有关联到材料确认单时
	 * 
	 * @return true 没有关联；false 关联到材料确认单
	 */
	private boolean isReferenceToPartAConfirm() {
		PayRequestBillConfirmEntryCollection mcBills = (PayRequestBillConfirmEntryCollection) initData.get("PayRequestBillConfirmEntryCollection");
		return (mcBills != null && mcBills.size() == 0);
	}

	/**
	 * 描述：合同下所有付款类型的类别为进度款的付款申请单（所有状态）本期已完工的累计值。
	 * 
	 * @author pengwei_hou Date: 2008-12-07
	 * @throws BOSException
	 */
    private void loadAllCompletePrjAmt() throws BOSException{
//    	String paymentType = editData.getFdcPayType().getPayType().getId().toString();
//    	String progressID = TypeInfo.progressID;
//    	if(!paymentType.equals(progressID)){
//    		return;
//    	}
    	BigDecimal allCompletePrjAmt = FDCHelper.ZERO;
    	EntityViewInfo view = new EntityViewInfo();
    	view.getSelector().add("completePrjAmt");
    	view.getSelector().add("state");
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
    	// 复杂模式的，进度款累计已完工不包含结算款的只需要进度款的
    	// 其他模式进度款累计已完工包含结算款的 by cassiel_peng 2010-05-31
    	if(isFinancial){
    		filter.getFilterItems().add(new FilterItemInfo("paymentType.payType.id", TypeInfo.progressID));
    	}
    	if(editData.getCreateTime()!=null){
    		
    		filter.getFilterItems().add(new FilterItemInfo("createTime",editData.getCreateTime(),CompareType.LESS_EQUALS));
    	}else{
    		filter.getFilterItems().add(new FilterItemInfo("createTime",new Timestamp(System.currentTimeMillis()),CompareType.LESS_EQUALS));
    	}
    	view.setFilter(filter);
    	PayRequestBillCollection payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
    	
    	if(payReqColl != null && payReqColl.size() > 0){
    		for(int i=0;i<payReqColl.size();i++){
    			PayRequestBillInfo info = payReqColl.get(i);
    			allCompletePrjAmt = allCompletePrjAmt.add(FDCHelper.toBigDecimal(info.getCompletePrjAmt()));
    		}
    	}
    	if(allCompletePrjAmt.compareTo(FDCHelper.ZERO)>0){
    		txtAllCompletePrjAmt.setValue(allCompletePrjAmt);
    	}
    }
    
    /**
     * 描述：合同最终结算前，为空。合同最终结算后，为结算单上保修金。
     * @author pengwei_hou Date: 2008-12-07
     * @throws BOSException 
     * @throws EASBizException 
     */
    private void loadQualityGuaranteAmt() throws EASBizException, BOSException{
    	if(contractId==null) return;
    	if(hasSettled){
    		String sql = "select qualityGuarante where contractBill = '" + contractId +"' and IsFinalSettle = 1";
    		ContractSettlementBillInfo info = ContractSettlementBillFactory
    				.getRemoteInstance().getContractSettlementBillInfo(sql);
    		if(info!=null){
    			BigDecimal qualityGuaranteAmt = FDCHelper.toBigDecimal(info.getQualityGuarante());
    			if(qualityGuaranteAmt.compareTo(FDCHelper.ZERO)>0){
    				txtQualityGuarante.setText(FDCHelper.toBigDecimal(qualityGuaranteAmt, 2).toString());
    			}
    		}
    	}else{
    		txtQualityGuarante.setValue(null);
    	}
    }
    
    public Object getDeductMap(){
    	return initData.get("DeductOfPayReqBillCollection");
    }
    
    /**
     * 预付款：工程量启用+合同
     * @return
     */
    protected boolean isAdvance(){
    	try {
			return isSeparate&&FDCUtils.isContractBill(null, editData.getContractBillId());
		} catch (EASBizException e) {
			this.handUIException(e);
		} catch (BOSException e) {
			this.handUIException(e);
		}
		return false;
    }
       
    
	private void afterPressDeleteButton(){
			
		kdtEntries.setBeforeAction(new BeforeActionListener(){
				public void beforeAction(BeforeActionEvent e)
				{
					if(BeforeActionEvent.ACTION_DELETE==e.getType()){
						
						for (int i = 0; i <kdtEntries.getSelectManager().size(); i++)
						{
							KDTSelectBlock block = kdtEntries.getSelectManager().get(i);
							for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
							{
								for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
									kdtEntries.getCell(rowIndex, colIndex).setValue(null);
										KDTEditEvent event = new KDTEditEvent(kdtEntries, null, null, rowIndex, colIndex, true, 1);
										try {
											kdtEntries_editStopped(event);
										} catch (Exception e1) {
											e1.printStackTrace();
										}
								}
							}
							
						}
					}
				}
			});
		}
	
	
	/**
	 * 发票金额变化时，累计发票金额也应该要做相应变化
	 * @author owen_wen
	 */
	protected void txtInvoiceAmt_dataChanged(DataChangeEvent e) throws Exception {
		BigDecimal oldValue = FDCHelper.toBigDecimal(e.getOldValue());
		BigDecimal newValue = FDCHelper.toBigDecimal(e.getNewValue());
		BigDecimal valueDiff = newValue.subtract(oldValue);
		this.txtAllInvoiceAmt.setValue(FDCHelper.toBigDecimal(txtAllInvoiceAmt.getBigDecimalValue()).add(valueDiff));
	}
	
	/**
	 * 查看预算
	 * 
	 * @author 
	 * @date 2011-3-1
	 */
	public void actionViewBudget_actionPerformed(ActionEvent e)
			throws Exception {
		Map uiContext = getUIContext();
		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();
		
		String curProject = editData.getCurProject().getDisplayName();
		uiContext.put("curProject", curProject);
		Object obj = txtPlanItem.getUserObject();
		String curDept = "";
		if (obj == null) {
			MsgBox.showWarning(this, "无预算，暂无法查看！");
			abort();
		} else if (obj instanceof FDCDepConPayPlanContractInfo) {
			curDept = ((FDCDepConPayPlanContractInfo) obj).getHead()
					.getDeptment().getName();
		} else if (obj instanceof FDCDepConPayPlanUnsettledConInfo) {
			curDept = ((FDCDepConPayPlanUnsettledConInfo) obj)
					.getParent().getDeptment().getName();
		} else if (obj instanceof FDCDepConPayPlanNoContractInfo) {
			curDept = ((FDCDepConPayPlanNoContractInfo) obj).getHead()
					.getDeptment().getName();
		}
		uiContext.put("curDept", curDept);
		String planMonth = DateTimeUtils.format(editData.getBizDate(), "yyyy-MM");
		uiContext.put("planMonth", planMonth);
		
		BigDecimal localBudget = getLocalBudget(firstDay, lastDay, projectId);
		uiContext.put("localBudget", localBudget);
		BigDecimal actPaied = getActPaied(firstDay, lastDay, projectId);
		uiContext.put("actPaied", actPaied);
		BigDecimal floatFund = getFloatFund(firstDay, lastDay, projectId);
		uiContext.put("floatFund", floatFund);
		BigDecimal bgBalance = localBudget.subtract(floatFund).subtract(actPaied);
		uiContext.put("bgBalance", bgBalance);
		
		BudgetViewUtils.showModelUI(uiContext);
	}
	
	/**
	 * 计算已付
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private BigDecimal getActPaied(Date firstDay, Date lastDay, String projectId)
			throws BOSException, SQLException {
		BigDecimal actPaied = FDCHelper.ZERO;
		FDCSQLBuilder builderActPaied = new FDCSQLBuilder();
		builderActPaied.appendSql("select sum(FLocalAmount) as actPaied "
				+ "from t_cas_paymentbill  as pay where pay.FBillStatus = 15 "
				+ "and pay.FBizDate >= ");
		builderActPaied.appendParam(firstDay);
		builderActPaied.appendSql(" and pay.FBizDate <= ");
		builderActPaied.appendParam(lastDay);
		builderActPaied.appendSql(" and pay.FCurProjectID = ");
		builderActPaied.appendParam(projectId);
		builderActPaied.appendSql(" and pay.FContractBillID = ");
		builderActPaied.appendParam(editData.getContractBillId());
		if(editData.getId()!=null){
			builderActPaied.appendSql(" and FID != ");
			builderActPaied.appendParam(editData.getId().toString());
		}
		// 查看状态查以前的（最后修改时间在当前单据的最后修改时间之前）
		// 但是审批不更新最后修改时间？那把审批时间也算上也不行，审批时间没有时分秒
		if (STATUS_VIEW.equals(getOprtState())) {
			builderActPaied.appendSql(" and pay.FLastUpdateTime <= ");
			builderActPaied.appendSql("{ts '");
			builderActPaied.appendSql(FMConstants.FORMAT_TIME.format(editData
					.getLastUpdateTime()));
			builderActPaied.appendSql("' }");
			// 此处精确到时分秒，所以不能用下句
			// builderActPaied.appendParam(editData.getLastUpdateTime());
		}
		IRowSet rowSetActPaied = builderActPaied.executeQuery();
		while (rowSetActPaied.next()) {
			if (rowSetActPaied.getString("actPaied") != null) {
				actPaied = rowSetActPaied.getBigDecimal("actPaied");
			}
		}
		return actPaied;
	}
	
	/**
	 * 计算在途
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private BigDecimal getFloatFund(Date firstDay, Date lastDay,
			String projectId) throws BOSException, SQLException {
		BigDecimal floatFund = FDCHelper.ZERO;
		FDCSQLBuilder builderFloatFund = new FDCSQLBuilder();
		builderFloatFund.appendSql(" select sum(FLocalAmount) as floatFund ");
		builderFloatFund.appendSql(" from t_cas_paymentbill ");
		builderFloatFund.appendSql(" where FbillStatus = 11 ");
		builderFloatFund.appendSql(" and FBizDate >= ");
		builderFloatFund.appendParam(firstDay);
		builderFloatFund.appendSql(" and FBizDate <= ");
		builderFloatFund.appendParam(lastDay);
		builderFloatFund.appendSql(" and FCurProjectID = ");
		builderFloatFund.appendParam(projectId);
		builderFloatFund.appendSql(" and FContractBillID = ");
		builderFloatFund.appendParam(editData.getContractBillId());
		if(editData.getId()!=null){
			builderFloatFund.appendSql(" and FID != ");
			builderFloatFund.appendParam(editData.getId().toString());
		}
		// 查看状态查以前的
		if (STATUS_VIEW.equals(getOprtState())) {
			builderFloatFund.appendSql(" and FLastUpdateTime <= ");
			builderFloatFund.appendSql("{ts '");
			builderFloatFund.appendSql(FMConstants.FORMAT_TIME.format(editData
					.getLastUpdateTime()));
			builderFloatFund.appendSql("' }");
			// 此处精确到时分秒，所以不能用下句
//			builderFloatFund.appendParam(editData.getLastUpdateTime());
		}
		IRowSet rowSetFloatFund = builderFloatFund.executeQuery();
		while (rowSetFloatFund.next()) {
			if (rowSetFloatFund.getString("floatFund") != null) {
				floatFund = rowSetFloatFund.getBigDecimal("floatFund");
			}
		}
		return floatFund;
	}
	
	/**
	 * 取预算，分已签订合同预算和待签订合同预算2种情况，根据界面F7值判断<br>
	 * 从合同滚动付款计划中取值
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private BigDecimal getLocalBudget(Date firstDay, Date lastDay,
			String projectId) throws BOSException, SQLException {
		BigDecimal localBudget = FDCHelper.ZERO;
		Object plan = txtPlanItem.getUserObject();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if (plan != null && plan instanceof FDCDepConPayPlanContractInfo) {
			builder
					.appendSql("select admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
			builder.appendSql("from T_FNC_FDCDepConPayPlanContract as con ");
			builder
					.appendSql("left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FHeadID ");
			builder
					.appendSql("left join T_FNC_FDCDepConPayPlanCE as entry on entry.FParentC = con.FID ");
			builder
					.appendSql("left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
			// builder
			// .appendSql(
			// "where (head.FState = '4AUDITTED' or head.FState = '10PUBLISH') "
			// );
			builder.appendSql("where 1=1 ");
			builder.appendSql(" and entry.FMonth >= ");
			builder.appendParam(firstDay);
			builder.appendSql(" and entry.FMonth <= ");
			builder.appendParam(lastDay);
			builder.appendSql(" and con.FID = '");
			builder.appendSql(
					((FDCDepConPayPlanContractInfo) plan).getId().toString())
					.appendSql("' ");
			builder.appendSql(" and con.FProjectID = ");
			builder.appendParam(projectId);
			builder.appendSql("order by head.FYear desc,head.FMonth DESC");
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				if (rowSet.getString("localBudget") != null) {
					localBudget = rowSet.getBigDecimal("localBudget");
				}
			}
		} else if (plan != null
				&& plan instanceof FDCDepConPayPlanUnsettledConInfo) {
			builder
					.appendSql(" select admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
			builder.appendSql(" from T_FNC_FDCDepConPayPlanUC as con ");
			builder
					.appendSql(" left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FParentID ");
			builder
					.appendSql(" left join T_FNC_FDCDepConPayPlanUE as entry on entry.FParentID = con.FID ");
			builder
					.appendSql(" left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
			builder
					.appendSql(" where ");
			builder.appendSql(" entry.FMonth >= ");
			builder.appendParam(firstDay);
			builder.appendSql(" and entry.FMonth <= ");
			builder.appendParam(lastDay);
			builder.appendSql(" and con.FID = '");
			builder.appendSql(
					((FDCDepConPayPlanUnsettledConInfo) plan).getId()
							.toString()).appendSql("' ");
			builder.appendSql(" and con.FProjectID = ");
			builder.appendParam(projectId);
			builder.appendSql("order by head.FYear desc,head.FMonth DESC");
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				if (rowSet.getString("localBudget") != null) {
					localBudget = rowSet.getBigDecimal("localBudget");
				}
			}
		} else if (plan != null
				&& plan instanceof FDCDepConPayPlanNoContractInfo) {
			builder
					.appendSql(" select admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
			builder.appendSql(" from T_FNC_FDCDepConPayPlanNoCon as con ");
			builder
					.appendSql(" left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FHeadID ");
			builder
					.appendSql(" left join T_FNC_FDCDepConPayPlanNCEntry as entry on entry.FParentNC = con.FID ");
			builder
					.appendSql(" left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
			builder
					.appendSql(" where  ");
			builder.appendSql(" entry.FMonth >= ");
			builder.appendParam(firstDay);
			builder.appendSql(" and entry.FMonth <= ");
			builder.appendParam(lastDay);
			builder.appendSql(" and con.FID = '");
			builder.appendSql(
					((FDCDepConPayPlanNoContractInfo) plan).getId().toString())
					.appendSql("' ");
			builder.appendSql("order by head.FYear desc,head.FMonth DESC");
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				if (rowSet.getString("localBudget") != null) {
					localBudget = rowSet.getBigDecimal("localBudget");
				}
			}
		}
		return localBudget;
	}
	
	/**
	 * 获取指定日期的第一天
	 * @param date
	 * @return 日期如2010-5-1 00:00:00.000
	 * @see Calendar#roll(int, int)
	 */
	public static Date getFirstDay(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE,0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}
	
	
	/**
	 * 获取指定日期的最后一天
	 * @param date
	 * @return 日期如：2010-5-31 23:59:59.999
	 * @see Calendar#roll(int, int)
	 */
	public static Date getLastDay(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.roll(Calendar.DAY_OF_MONTH,-1);
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		ca.set(Calendar.MILLISECOND, 999);
		
		return ca.getTime();
	}
	
	/**
	 * 描述：
	 */
	protected void pkbookedDate_dataChanged(DataChangeEvent e) throws Exception {
		actionViewBudget_actionPerformed(null);
	}

	/**
	 * 检查对应付款申请单的累计发票金额是否会超过合同最新造价
	 * 
	 * @author owen_wen
	 * @param ctx
	 * @param paymentBill
	 *            付款单
	 * @param thisInvoiceAmt
	 *            本次修改后的发票金额
	 * @throws EASBizException
	 */
	private void checkIsAllInvoicAmtMoreThanConLatestPrice(PaymentBillInfo paymentBill, BigDecimal thisInvoiceAmt) throws BOSException,
			EASBizException {
		boolean isAllowOverConPrice = FDCUtils.getBooleanValue4FDCParamByKey(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString(),
				FDCConstants.FDC_PARAM_OVERRUNCONPRICE);
		if (isAllowOverConPrice) // 如果允许累计发票金额大于合同最新造价
			return;
		PayRequestBillInfo payReqInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(
				new ObjectUuidPK(BOSUuid.read(paymentBill.getFdcPayReqID())));
		// 新的累计发票金额
		BigDecimal newAllInvoice = FDCNumberHelper.subtract(payReqInfo.getAllInvoiceAmt(), FDCHelper.toBigDecimal(payReqInfo.getInvoiceAmt())).add(
				thisInvoiceAmt);
		
		//R110725-0280:无文本合同审批生成的付款单提交报错，合同最新造价为NULL
		BigDecimal latestPrice = FDCNumberHelper.ZERO;
		if (FDCUtils.isContractBill(null, paymentBill.getContractBillId())) {		
			Map map = (Map) FDCUtils.getLastAmt_Batch(null, new String[] { paymentBill.getContractBillId() });
			latestPrice = (BigDecimal) map.get(paymentBill.getContractBillId());
		} else {
			//无文本合同等于付款申请单的金额即可，无需在取合同的金额
			ContractWithoutTextInfo info = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(paymentBill.getContractBillId())));
			latestPrice = info.getAmount();
		}

		if (newAllInvoice.compareTo(latestPrice) > 0) {
			// 申请单的发票累计金额超过合同的最新造价，给出提示信息
			throw new FinanceException(FinanceException.ALLINVOICEAMTMORETHANCONLATESTPRICE);
		}
	}
	public void actionApprove_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		checkIsAdjustPeriod();
        Set idSet = new HashSet();
        idSet.add(editData.getId().toString());
        ((IPaymentBill)getBizInterface()).approve(idSet);
        showMsg(e);
        actionApprove.setEnabled(false);
        syncDataFromDB();
	}

	public void actionUntiApprove_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		checkIsAdjustPeriod();
        Set idSet = new HashSet();
        idSet.add(editData.getId().toString());
        ((IPaymentBill)getBizInterface()).untiApprove(idSet);
        showMsg(e);
        syncDataFromDB();
	}
	public void checkIsAdjustPeriod()
    {
        PeriodInfo currentPeriod = (PeriodInfo)paramValue.get("currentPeriond");
        if(currentPeriod != null && currentPeriod.isIsAdjustPeriod())
        {
            MsgBox.showError(this, (new FMException(FMException.CURRPERIODISADJUST)).getMessage());
            SysUtil.abort();
        }
    }
	private void showMsg(ActionEvent e)
    {
        Component component = (Component)e.getSource();
        FMClientHelper.showSuccessMessage(this, component);
    }
}
