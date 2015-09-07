/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
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
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.base.permission.OrgRangeIncludeSubOrgFactory;
import com.kingdee.eas.base.permission.OrgRangeType;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.PositionCollection;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.org.client.f7.CompanyBizUnitF7;
import com.kingdee.eas.basedata.org.client.f7.CompanyF7;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.CommonUtilFacadeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.client.ExpenseTypePromptBox;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithBgItemFactory;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeCollection;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.client.ContractTypePromptSelector;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCContractParamUI;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.TableUtils;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractWithoutTextBgEntryCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextBgEntryInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.FeeEntryInfo;
import com.kingdee.eas.fdc.contract.FeeTypeEnum;
import com.kingdee.eas.fdc.contract.PayContentTypeInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.TraEntryInfo;
import com.kingdee.eas.fdc.contract.UrgentDegreeEnum;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ContractBillLinkProgContEditUI;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractEditUI;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillTypeInfo;
import com.kingdee.eas.fi.cas.client.CasRecPayHandler;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.ma.budget.BgCtrlTypeEnum;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgControlSchemeCollection;
import com.kingdee.eas.ma.budget.BgControlSchemeFactory;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultInfo;
import com.kingdee.eas.ma.budget.BgHelper;
import com.kingdee.eas.ma.budget.BgItemCollection;
import com.kingdee.eas.ma.budget.BgItemFactory;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgItemObject;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.ma.budget.client.BgBalanceViewUI;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * 描述:无文本合同编辑界面
 * @author liupd  date:2006-10-10 <p>
 * @version EAS5.1.3
 */
public class ContractWithoutTextEditUI extends
		AbstractContractWithoutTextEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractWithoutTextEditUI.class);
	
	//	是否第一次加载,用来控制表格的加载显示
	private boolean isFirstLoad=true;
	//当前单据是否有附件列表
	private boolean isHasAttachment = false;
	//付款申请单
	private PayRequestBillInfo prbi  = new PayRequestBillInfo();
	
	//付款类型
	private PaymentTypeCollection paymentTypes = null;	
	
	//合同类型
	private ContractTypeCollection contractType = null ;

	//启用财务一体化
	protected boolean isFinacial = false;
	
	//项目合同签约总金额超过项目
	private String controlCost ;
	//控制方式
	private String controlType ;
	
	//是否显示“合同费用项目”
	private boolean isShowCharge = false;
	
	// 累计发票金额
	private BigDecimal allInvoiceAmt = FDCHelper.ZERO;
	
	// 房地产付款单强制不进入出纳系统  at 2010-5-10
	protected boolean isNotEnterCAS = false;

	// 付款申请单及无文本合同发票号、发票金额必录
	private boolean isInvoiceRequired = false;
	
	// 通过合同月度滚动付款计划控制无文本合同
	private String CONTROLNOCONTRACT = "不控制";

	// 责任人是否可以选择其他部门的人员
	private boolean canSelecOtherOrgPerson() {
		boolean canSelectOtherOrgPerson = false;
		try {
			canSelectOtherOrgPerson = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_SELECTPERSON);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return canSelectOtherOrgPerson;
	}
	boolean isFeeTraEntry=false;
	/**
	 * output class constructor
	 */
	public ContractWithoutTextEditUI() throws Exception {
		super();
		jbInit() ;
	}

	private void jbInit() {	    
		titleMain = getUITitle();
	}
	
	public void onShow() throws Exception {
		super.onShow();
		
		setFocus();
		txtPaymentRequestBillNumber.setRequired(false);
		txtPaymentRequestBillNumber.setEditable(false);
		this.btnViewProgramContract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showlist"));
		this.btnProgram.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_associatecreate"));
		this.pkbookedDate.setEnabled(false);
	}
	protected void afterSubmitAddNew() {
		clearPayRequestFieldCtrl();
		super.afterSubmitAddNew();
		setFocus();
	}
	
	private void setFocus() {
		if(txtNumber.isEnabled() && txtNumber.isEditable()) {
			txtNumber.requestFocus();
		}
		else {
			txtbillName.requestFocus();
		}
	}
	
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return ContractWithoutTextFactory.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
		return null;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return null;
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		ContractWithoutTextInfo objectValue = new ContractWithoutTextInfo();
		objectValue.setCreator((SysContext.getSysContext().getCurrentUserInfo()));
		objectValue.setCreateTime(new Timestamp(serverDate.getTime()));
//		objectValue.setSignDate(this.serverDate);

		objectValue.setSourceType(SourceTypeEnum.ADDNEW);
		objectValue.setFeeType(FeeTypeEnum.FEE);
		
		objectValue.setIsCostSplit(true);
		objectValue.setConSplitExecState(ConSplitExecStateEnum.COMMON);
		objectValue.setCurrency(this.baseCurrency);
		//项目Id
		BOSUuid projId = (BOSUuid) getUIContext().get("projectId");
		if(projId != null) { 
			CurProjectInfo projInfo = curProject;			
			objectValue.setCurProject(projInfo);
			objectValue.setCU(projInfo.getCU());		
		}
		
		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);
		
		//设置无文本合同拆分初始化状态为未拆分
		objectValue.setSplitState(CostSplitStateEnum.NOSPLIT);
		objectValue.setInvoiceDate(serverDate);
		
//		PersonInfo person = SysContext.getSysContext().getCurrentUserInfo().getPerson();
//		PositionCollection positionCol;
//		positionCol = null;
//		AdminOrgUnitInfo adminInfo = null;
//		try {
//			positionCol = PersonFactory.getRemoteInstance().getPositions(person.getId());
//			PositionInfo position = null;
//			if(positionCol.size() > 0){
//				position = positionCol.get(0);
//			}
//			if(position!=null && position.getAdminOrgUnit()!=null){
//				adminInfo = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(position.getAdminOrgUnit().getId().toString()));
//			}
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} 
//		objectValue.setUseDepartment(adminInfo);
		
		objectValue.setIsInvoice(true);
		objectValue.setIsBgControl(true);
		objectValue.setIsNeedPaid(true);
		objectValue.getBgEntry().add(new ContractWithoutTextBgEntryInfo());
		try {
			BizCollBillBaseInfo baseInfo = CommonUtilFacadeFactory.getRemoteInstance().forLoanBillCreateNewData();
			objectValue.setApplierOrgUnit(baseInfo.getOrgUnit());
			objectValue.setApplier(baseInfo.getApplier());
			objectValue.setApplierCompany(baseInfo.getApplierCompany());
			objectValue.setCostedDept(SysContext.getSysContext().getCurrentCostUnit());
			objectValue.setCostedCompany(SysContext.getSysContext().getCurrentFIUnit());
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		objectValue.setApplier(SysContext.getSysContext().getCurrentUserInfo().getPerson());
		
		return objectValue;
	}

	private void clearPayRequestFieldCtrl() {
		prmtPayment.setValue(null);
		prmtuseDepartment.setValue(null);
		txtPaymentRequestBillNumber.setText(null);
		pksignDate.setValue(null);
		prmtreceiveUnit.setValue(null);
		prmtrealSupplier.setValue(null);
		txtBank.setText(null);
		prmtsettlementType.setValue(null);
		txtBankAcct.setText(null);
		chkUrgency.setSelected(false);
		chkNeedPaid.setSelected(true);
		txtNoPaidReason.setText(null);
		prmtAccount.setValue(null);
		prmtcurrency.setValue(null);
		txtexchangeRate.setValue(null);
		txtamount.setValue(null);
		txtcapitalAmount.setText(null);
		txtBcAmount.setValue(null);
		txtPaymentProportion.setValue(null);
		txtcompletePrjAmt.setValue(null);
		txtMoneyDesc.setText(null);
		txtattachment.setText(null);
	}
	
	//子类可以重载
	protected  void fetchInitParam() throws Exception{
		super.fetchInitParam();
		//项目Id
		BOSUuid projId = (BOSUuid) getUIContext().get("projectId");
		if(projId != null) { 
			CurProjectInfo projInfo = curProject;	
			//传入的参数为其所在工程项目对应的财务组织id，而不是获得当前登陆财务组织的id
			//启用成本财务一体化
			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,projInfo.getFullOrgUnit().getId().toString());
//			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentFIUnit().getId().toString());
			if(paramItem.get(FDCConstants.FDC_PARAM_FINACIAL)!=null){
				isFinacial = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_FINACIAL).toString()).booleanValue();
			}
			if(paramItem.get(FDCConstants.FDC_PARAM_INVOICEREQUIRED)!=null){
				isInvoiceRequired = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INVOICEREQUIRED).toString()).booleanValue();
			}
		}
		//是否显示“合同费用项目”
		isShowCharge = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_CHARGETYPE);			
		
		String org = SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();
		CONTROLNOCONTRACT = ParamManager.getParamValue(null, new ObjectUuidPK(
				org), "FDC326_CONTROLNOCONTRACT");
		if ("0".equals(CONTROLNOCONTRACT)) {
			CONTROLNOCONTRACT = "严格控制";
		} else if ("1".equals(CONTROLNOCONTRACT)) {
			CONTROLNOCONTRACT = "提示控制";
		} else if ("2".equals(CONTROLNOCONTRACT)) {
			CONTROLNOCONTRACT = "不控制";
		}
	}
	
	/**
	 * new update by renliang at 2010-5-12
	 */
	public void isNotEnterCAS(){
		
		CurProjectInfo info = editData.getCurProject();
		
		if(info!=null){
			Map paramItem = null;
			try {
				paramItem = FDCUtils.getDefaultFDCParam(null, info.getFullOrgUnit().getId().toString());
			} catch (EASBizException e) {
				
				logger.info(e.getMessage());
			} catch (BOSException e) {
			
				logger.info(e.getMessage());
			}
			if(paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS)!=null)	{
				this.isNotEnterCAS = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS).toString()).booleanValue();
			}
		}
		
	}
	
	
	//子类可以重载
	protected  void fetchInitData() throws Exception{

		super.fetchInitData();
		
		//查找付款类型
		paymentTypes = (PaymentTypeCollection)ActionCache.get("ContractWithoutTextEditUIHandler.paymentTypes");
		if(paymentTypes==null){
			String sSql = "select * where IsEnabled=1 and payType.id ='Ga7RLQETEADgAAC6wKgOlOwp3Sw='";			
			paymentTypes = PaymentTypeFactory.getRemoteInstance().getPaymentTypeCollection(sSql);
		}

		//查找合同类型
		contractType = (ContractTypeCollection)ActionCache.get("ContractWithoutTextEditUIHandler.contractType");
		if(contractType==null){
			String contractSql = "select * where IsEnabled = 1" ;
			contractType = ContractTypeFactory.getRemoteInstance().getContractTypeCollection(contractSql) ;
		}
		
		Map param = (Map)ActionCache.get("FDCBillEditUIHandler.orgParamItem");
		if(param==null){
			param = FDCUtils.getDefaultFDCParam(null,orgUnitInfo.getId().toString());	
		}

		if(param.get(FDCConstants.FDC_PARAM_OUTCOST)!=null){
			controlCost = param.get(FDCConstants.FDC_PARAM_OUTCOST).toString();
		}
		if(param.get(FDCConstants.FDC_PARAM_CONTROLTYPE)!=null){
			controlType = param.get(FDCConstants.FDC_PARAM_CONTROLTYPE).toString();
		}
	}
	
	public void onLoad() throws Exception {
		this.comboPayeeType.addItem(new KDComboBoxMultiColumnItem(new String[] {"供应商" }));
		this.comboPayeeType.addItem(new KDComboBoxMultiColumnItem(new String[] {"职员" }));
		
		super.onLoad();
		//new update by renliang at 2010-5-14
        isNotEnterCAS();
	    if (isNotEnterCAS) {
	    	chkNeedPaid.setEnabled(false);
			// 添加判断，保证显示正确
			if (OprtState.ADDNEW.equals(getOprtState())) {
				chkNeedPaid.setSelected(true);
			}			
		}
	    
		//onLoad后续逻辑抽象为一个方法
		fillAttachmentList();
		afterOnload();
		this.prmtPayment.setEnabled(false);
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isBizUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		CompanyOrgUnitInfo com=(CompanyOrgUnitInfo)this.prmtCostedCompany.getValue();
		if(com!=null){
			filter.getFilterItems().add(new FilterItemInfo("id",getCostedDeptIdSet(com),CompareType.INCLUDE));
		}
		view.setFilter(filter);
        this.prmtCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
        this.prmtCostedDept.setEntityViewInfo(view);
        this.prmtCostedDept.setEditFormat("$number$");
        this.prmtCostedDept.setDisplayFormat("$name$");
        this.prmtCostedDept.setCommitFormat("$number$");
        
        view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		view.setFilter(filter);
        this.prmtCostedCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");
        CompanyF7 comp = new CompanyF7(this);
        prmtCostedCompany.setEntityViewInfo(view);
        prmtCostedCompany.setSelector(comp);
        prmtCostedCompany.setDisplayFormat("$name$");
        prmtCostedCompany.setEditFormat("$number$");
        prmtCostedCompany.setCommitFormat("$number$");
        
		FDCClientUtils.setPersonF7(this.prmtApplier, this,null);
		
        setBgEditState();
        //初始化合约框架  为什么getSelector里能带出ID。。。
        initProgrammingContract();
        if(this.prmtContractType.getValue()!=null){
        	this.prmtPayContentType.setEnabled(true);
        }else{
        	this.prmtPayContentType.setEnabled(false);
        }
        if("供应商".equals(this.comboPayeeType.getSelectedItem().toString())){
			prmtrealSupplier.setRequired(true);
			prmtrealSupplier.setEnabled(true);
		}else{
			prmtrealSupplier.setRequired(false);
			prmtrealSupplier.setEnabled(false);
		}
        initBgEntryTable();
        
        this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		
		this.tblAttachement.checkParsed();
		KDWorkButton btnAttachment = new KDWorkButton();

		this.actionAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnAttachment = (KDWorkButton) this.contAttachment.add(this.actionAttachment);
		btnAttachment.setText("附件管理");
		btnAttachment.setSize(new Dimension(140, 19));
		
		
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("FDC_ISFEETRAENTRY", null);
		try {
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			if(hmAllParam.get("FDC_ISFEETRAENTRY")!=null){
				isFeeTraEntry=Boolean.parseBoolean(hmAllParam.get("FDC_ISFEETRAENTRY").toString());
			}else{
				isFeeTraEntry=false;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		cbFeeType_itemStateChanged(null);
		
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setPrecision(2);
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		
		this.kdtFeeEntry.getColumn("amount").setEditor(weight);
		this.kdtTraEntry.getColumn("airFee").setEditor(weight);
		this.kdtTraEntry.getColumn("carFee").setEditor(weight);
		this.kdtTraEntry.getColumn("cityFee").setEditor(weight);
		this.kdtTraEntry.getColumn("otherFee").setEditor(weight);
		this.kdtTraEntry.getColumn("liveFee").setEditor(weight);
		this.kdtTraEntry.getColumn("allowance").setEditor(weight);
		this.kdtTraEntry.getColumn("other").setEditor(weight);
		this.kdtTraEntry.getColumn("total").setEditor(weight);
		
		hmParamIn = new HashMap();
		hmParamIn.put("FDC_ISOTHERCOSTEDDEPT", null);
		HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
		if(hmAllParam.get("FDC_ISOTHERCOSTEDDEPT")!=null){
			isOtherCostedDept=Boolean.parseBoolean(hmAllParam.get("FDC_ISOTHERCOSTEDDEPT").toString());
		}else{
			isOtherCostedDept=true;
		}
		if(!isOtherCostedDept){
			this.prmtCostedCompany.setEnabled(false);
			this.prmtCostedCompany.setRequired(false);
			this.prmtCostedDept.setRequired(false);
		}
		
		this.btnAccountView.setIcon(EASResource.getIcon("imgTbtn_associatecreate"));
		
		this.actionCopy.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
	}
	public void actionAccountView_actionPerformed(ActionEvent e)throws Exception {
		if(!ContractWithoutTextFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getId()))){
			FDCMsgBox.showWarning(this,"请先保存单据！");
			SysUtil.abort();
		}
		
		String splitBillID = null;

		FDCBillInfo billInfo = null;
		CoreBaseCollection coll = null;

		String editName = null;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("conWithoutText.id", this.editData.getId().toString()));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
			editName = com.kingdee.eas.fdc.finance.client.PaymentSplitEditUI.class.getName();
			coll = PaymentSplitFactory.getRemoteInstance().getCollection(view);

		boolean isSplited = false;
		boolean isAudited = false;

		if (coll.size() > 0) {
			billInfo = (FDCBillInfo) coll.get(0);
			splitBillID = billInfo.getId().toString();
			isSplited = true;

			if (billInfo.getState().equals(FDCBillStateEnum.AUDITTED)) {
				isAudited = true;
			}
		}

		UIContext uiContext = new UIContext(this);
		String oprtState;

		if (isSplited) {
			uiContext.put(UIContext.ID, splitBillID);

			if (isAudited) {
				oprtState = OprtState.VIEW;
			} else {
				oprtState = OprtState.EDIT;
			}
		} else {
			uiContext.put("costBillID", this.editData.getId().toString());
			oprtState = OprtState.ADDNEW;
		}
		uiContext.put("amount", this.txtamount.getBigDecimalValue());
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(editName, uiContext, null, oprtState);
		uiWin.show();
	}
	boolean isOtherCostedDept=true;
	protected Set getCostedDeptIdSet(CompanyOrgUnitInfo com) throws EASBizException, BOSException{
		if(com==null) return null;
		Set id=new HashSet();
		String longNumber=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(com.getId())).getLongNumber();
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber",longNumber+"!%",CompareType.LIKE));
		view.setFilter(filter);
		FullOrgUnitCollection col=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
		for(int i=0;i<col.size();i++){
			if(col.get(i).getPartCostCenter()!=null){
				id.add(col.get(i).getId().toString());
			}
		}
		return id;
	}
	private void initProgrammingContract() throws EASBizException, BOSException {
		if(this.editData.getProgrammingContract()!=null && this.editData.getProgrammingContract().getId() != null){
			ProgrammingContractInfo proContInfo = getProContInfo(this.editData.getProgrammingContract().getId().toString());
			this.editData.setProgrammingContract(proContInfo);
			this.txtPCName.setText(this.editData.getProgrammingContract().getName());
		}
	}

	protected void loadBgEntryTable(){
		ContractWithoutTextBgEntryCollection col=editData.getBgEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtBgEntry.removeRows();
		for(int i=0;i<col.size();i++){
			ContractWithoutTextBgEntryInfo entry=col.get(i);
			IRow row=this.kdtBgEntry.addRow();
			row.setUserObject(entry);
			if(entry.getExpenseType()!=null){
				try {
					row.getCell("expenseType").setValue(ExpenseTypeFactory.getRemoteInstance().getExpenseTypeInfo(new ObjectUuidPK(entry.getExpenseType().getId())));
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
			row.getCell("accountView").setValue(entry.getAccountView());
			row.getCell("requestAmount").setValue(entry.getRequestAmount());
			row.getCell("amount").setValue(entry.getAmount());
			if(entry.getBgItem()!=null){
				try {
					row.getCell("bgItem").setValue(BgItemFactory.getRemoteInstance().getBgItemInfo(new ObjectUuidPK(entry.getBgItem().getId())));
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	protected void storeBgEntryTable(){
		BigDecimal amount=FDCHelper.ZERO;
		editData.getBgEntry().clear();
    	for(int i=0;i<this.kdtBgEntry.getRowCount();i++){
    		IRow row = this.kdtBgEntry.getRow(i);
    		ContractWithoutTextBgEntryInfo entry=(ContractWithoutTextBgEntryInfo) row.getUserObject();
    		entry.setSeq(i);
    		entry.setExpenseType((ExpenseTypeInfo)row.getCell("expenseType").getValue());
    		entry.setAccountView((AccountViewInfo)row.getCell("accountView").getValue());
    		entry.setRequestAmount((BigDecimal)row.getCell("requestAmount").getValue());
    		entry.setAmount((BigDecimal)row.getCell("amount").getValue());
    		entry.setBgItem((BgItemInfo)row.getCell("bgItem").getValue());
    		editData.getBgEntry().add(entry);
    		
    		amount=FDCHelper.add(amount,row.getCell("requestAmount").getValue());
    	}
    	this.txtamount.setValue(amount);
	}
	protected void initBgEntryTable(){
		KDWorkButton btnAddRowinfo=new KDWorkButton();
    	KDWorkButton btnInsertRowinfo=new KDWorkButton();
    	KDWorkButton btnDeleteRowinfo=new KDWorkButton();
    	
    	this.actionAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)this.contBgEntry.add(this.actionAddLine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));
		
		this.actionInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton)this.contBgEntry.add(this.actionInsertLine);
		btnInsertRowinfo.setText("插入行");
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		
		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton)this.contBgEntry.add(this.actionRemoveLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.kdtBgEntry.checkParsed();
		
		CompanyOrgUnitInfo curCompany = company;
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox dialog = new AccountPromptBox(this, curCompany,view.getFilter(), false, true);
		KDBizPromptBox f7Box = new KDBizPromptBox();
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDialog(dialog);
		f7Box.setEditFormat("$number$");
        f7Box.setDisplayFormat("$name$");
        f7Box.setCommitFormat("$number$");
        
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtBgEntry.getColumn("accountView").setEditor(f7Editor);
		
		f7Box = new KDBizPromptBox();
		f7Box.setQueryInfo("com.kingdee.eas.cp.bc.app.F7ExpenseTypeQuery");
        ExpenseTypePromptBox selector = new ExpenseTypePromptBox(this);
        selector.setMultiSelect(false);
        f7Box.setSelector(selector);
        f7Box.setEnabledMultiSelection(false);
        f7Box.setEditFormat("$number$");
        f7Box.setDisplayFormat("$typeName$-$number$");
        f7Box.setCommitFormat("$number$");
        f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtBgEntry.getColumn("expenseType").setEditor(f7Editor);
		
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setPrecision(2);
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		this.kdtBgEntry.getColumn("requestAmount").setEditor(weight);
		this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtBgEntry.getColumn("amount").setEditor(weight);
		this.kdtBgEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtBgEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtBgEntry.getColumn("amount").getStyleAttributes().setHided(true);
		
		f7Box = new KDBizPromptBox();
		f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$name$");
		f7Box.setCommitFormat("$name$");
		NewBgItemDialog bgItemDialog=new NewBgItemDialog(this);
		bgItemDialog.setMulSelect(false);
		bgItemDialog.setSelectCombinItem(false);
		f7Box.setSelector(bgItemDialog);
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtBgEntry.getColumn("bgItem").setEditor(f7Editor);
		this.kdtBgEntry.getColumn("bgItem").getStyleAttributes().setLocked(true);
		this.kdtBgEntry.getColumn("bgItem").getStyleAttributes().setHided(true);
		
		
		this.actionAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)this.contFeeEntry.add(this.actionAddLine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));
		
		this.actionInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton)this.contFeeEntry.add(this.actionInsertLine);
		btnInsertRowinfo.setText("插入行");
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		
		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton)this.contFeeEntry.add(this.actionRemoveLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.actionAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)this.contTraEntry.add(this.actionAddLine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));
		
		this.actionInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton)this.contTraEntry.add(this.actionInsertLine);
		btnInsertRowinfo.setText("插入行");
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		
		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton)this.contTraEntry.add(this.actionRemoveLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
	}

	protected void kdtBgEntry_editStopped(KDTEditEvent e) throws Exception {
		if(this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("requestAmount")){
			if(this.kdtBgEntry.getRow(e.getRowIndex()).getCell("requestAmount").getValue()!=null){
				this.kdtBgEntry.getRow(e.getRowIndex()).getCell("amount").setValue(this.kdtBgEntry.getRow(e.getRowIndex()).getCell("requestAmount").getValue());
			}
		}
//		else if(this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("expenseType")){
//			getBgAmount();
//		}
		else if(this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("bgItem")){
			if(this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").getValue() instanceof BgItemObject){
				BgItemObject bgItem=(BgItemObject) this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").getValue();
				if(bgItem!=null){
					if(!bgItem.getResult().get(0).isIsLeaf()){
						FDCMsgBox.showWarning(this,"请选择明细预算项目！");
						this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").setValue(null);
					}else{
						this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").setValue(bgItem.getResult().get(0));
					}
				}
			}
			getBgAmount();
		}else if(this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("expenseType")){
			ExpenseTypeInfo info=(ExpenseTypeInfo) this.kdtBgEntry.getRow(e.getRowIndex()).getCell("expenseType").getValue();
			if(info!=null){
				BgItemInfo bgItem=null;
				FDCSQLBuilder _builder = new FDCSQLBuilder();
				_builder.appendSql(" select bgItem.fid id from T_BG_BgItem bgItem where bgItem.fnumber in(");
				_builder.appendSql(" select map.fbgItemCombinKey from T_BG_BgControlItemMap map left join T_BG_BgControlScheme scheme on scheme.fid=map.FBgCtrlSchemeID");
				_builder.appendSql(" where scheme.fboName='com.kingdee.eas.fi.cas.app.PaymentBill' and scheme.fisValid=1 and scheme.fisSysDefault=0");
				_builder.appendSql(" and scheme.fcostCenterId='"+this.editData.getOrgUnit().getId().toString()+"'");
				_builder.appendSql(" and SUBSTRING(map.fbillItemCombinValue,charindex(':',map.fbillItemCombinValue)+1,length(map.fbillItemCombinValue)-charindex(':',map.fbillItemCombinValue)) ='"+info.getNumber()+"'");
				_builder.appendSql(" group by map.fbgItemCombinKey)");
				final IRowSet rowSet = _builder.executeQuery();
				while (rowSet.next()) {
					bgItem=BgItemFactory.getRemoteInstance().getBgItemInfo(new ObjectUuidPK(rowSet.getString("id")));
					break;
				}
				this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").setValue(bgItem);
			}
			getBgAmount();
		}
		if(this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("requestAmount")||this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("amount")){
			BigDecimal amount =TableUtils.getColumnValueSum(this.kdtBgEntry, "requestAmount");
			this.txtamount.setValue(amount);
			TableUtils.getFootRow(this.kdtBgEntry,new String[]{"requestAmount","amount"});
		}
	}
	private KDTextField txtBgAmount=null;
	protected void kdtBgEntry_tableSelectChanged(KDTSelectEvent e) throws Exception {
		getBgAmount();
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		if(this.kDTabbedPane1.getSelectedComponent().equals(this.contBgEntry)){
			IRow row=this.kdtBgEntry.addRow();
			ContractWithoutTextBgEntryInfo entry=new ContractWithoutTextBgEntryInfo();
			row.setUserObject(entry);
		}else if(this.kDTabbedPane1.getSelectedComponent().equals(this.contFeeEntry)){
			IRow row=this.kdtFeeEntry.addRow();
			FeeEntryInfo entry=new FeeEntryInfo();
			row.setUserObject(entry);
		}else if(this.kDTabbedPane1.getSelectedComponent().equals(this.contTraEntry)){
			IRow row=this.kdtTraEntry.addRow();
			TraEntryInfo entry=new TraEntryInfo();
			row.setUserObject(entry);
		}
	}
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		if(this.kDTabbedPane1.getSelectedComponent().equals(this.contBgEntry)){
			 IRow row = null;
			 if(this.kdtBgEntry.getSelectManager().size() > 0){
				 int top = this.kdtBgEntry.getSelectManager().get().getTop();
				 if(isTableColumnSelected(this.kdtBgEntry))
					 row = this.kdtBgEntry.addRow();
				 else
					 row = this.kdtBgEntry.addRow(top);
		     }else{
		    	 row = this.kdtBgEntry.addRow();
		     }
			 ContractWithoutTextBgEntryInfo entry=new ContractWithoutTextBgEntryInfo();
			 row.setUserObject(entry);
		}else if(this.kDTabbedPane1.getSelectedComponent().equals(this.contFeeEntry)){
			 IRow row = null;
			 if(this.kdtFeeEntry.getSelectManager().size() > 0){
				 int top = this.kdtFeeEntry.getSelectManager().get().getTop();
				 if(isTableColumnSelected(this.kdtFeeEntry))
					 row = this.kdtFeeEntry.addRow();
				 else
					 row = this.kdtFeeEntry.addRow(top);
		     }else{
		    	 row = this.kdtFeeEntry.addRow();
		     }
			 FeeEntryInfo entry=new FeeEntryInfo();
			 row.setUserObject(entry);
		}else if(this.kDTabbedPane1.getSelectedComponent().equals(this.contTraEntry)){
			 IRow row = null;
			 if(this.kdtTraEntry.getSelectManager().size() > 0){
				 int top = this.kdtTraEntry.getSelectManager().get().getTop();
				 if(isTableColumnSelected(this.kdtTraEntry))
					 row = this.kdtTraEntry.addRow();
				 else
					 row = this.kdtTraEntry.addRow(top);
		     }else{
		    	 row = this.kdtTraEntry.addRow();
		     }
			 TraEntryInfo entry=new TraEntryInfo();
			 row.setUserObject(entry);
		}
	}
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		if(this.kDTabbedPane1.getSelectedComponent().equals(this.contBgEntry)){
			if(this.kdtBgEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtBgEntry)){
	            FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	            return;
	        }
	        if(FDCMsgBox.isYes(FDCMsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")))){
	            int top = this.kdtBgEntry.getSelectManager().get().getBeginRow();
	            int bottom = this.kdtBgEntry.getSelectManager().get().getEndRow();
	            for(int i = top; i <= bottom; i++){
	                if(this.kdtBgEntry.getRow(top) == null)
	                {
	                    FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	                    return;
	                }
	                this.kdtBgEntry.removeRow(top);
	                BigDecimal amount =TableUtils.getColumnValueSum(this.kdtBgEntry, "requestAmount");
	    			this.txtamount.setValue(amount);
	                TableUtils.getFootRow(this.kdtBgEntry,new String[]{"requestAmount","amount"});
	            }
	        }
		}else if(this.kDTabbedPane1.getSelectedComponent().equals(this.contFeeEntry)){
			if(this.kdtFeeEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtFeeEntry)){
	            FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	            return;
	        }
	        if(FDCMsgBox.isYes(FDCMsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")))){
	            int top = this.kdtFeeEntry.getSelectManager().get().getBeginRow();
	            int bottom = this.kdtFeeEntry.getSelectManager().get().getEndRow();
	            for(int i = top; i <= bottom; i++){
	                if(this.kdtFeeEntry.getRow(top) == null)
	                {
	                    FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	                    return;
	                }
	                this.kdtFeeEntry.removeRow(top);
	                TableUtils.getFootRow(this.kdtBgEntry,new String[]{"amount"});
	            }
	        }
		}else if(this.kDTabbedPane1.getSelectedComponent().equals(this.contTraEntry)){
			if(this.kdtTraEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtTraEntry)){
	            FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	            return;
	        }
	        if(FDCMsgBox.isYes(FDCMsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")))){
	            int top = this.kdtTraEntry.getSelectManager().get().getBeginRow();
	            int bottom = this.kdtTraEntry.getSelectManager().get().getEndRow();
	            for(int i = top; i <= bottom; i++){
	                if(this.kdtTraEntry.getRow(top) == null)
	                {
	                    FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	                    return;
	                }
	                this.kdtTraEntry.removeRow(top);
	                TableUtils.getFootRow(this.kdtTraEntry,new String[]{"days","airFee","carFee","cityFee","otherFee","persons","liveDays","liveFee","allowance","other","total"});
	            }
	        }
		}
	}
	protected void prmtCostedCompany_dataChanged(DataChangeEvent e) throws Exception {
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isBizUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		CompanyOrgUnitInfo com=(CompanyOrgUnitInfo)this.prmtCostedCompany.getValue();
		if(com!=null){
			if(!com.isIsLeaf()){
				FDCMsgBox.showWarning(this,"请选择明细预算承担公司！");
				this.prmtCostedCompany.setValue(null);
				return;
			}
			Set id=getCostedDeptIdSet(com);
			if(this.prmtCostedDept.getValue()!=null){
				if(id.contains(((CostCenterOrgUnitInfo)prmtCostedDept.getValue()).getId().toString())){
					return;
				}else{
					this.prmtCostedDept.setValue(null);
				}
			}
			filter.getFilterItems().add(new FilterItemInfo("id",getCostedDeptIdSet(com),CompareType.INCLUDE));
			this.prmtCostedDept.setEnabled(true);
		}else{
			this.prmtCostedDept.setValue(null);
			this.prmtCostedDept.setEnabled(false);
		}
		view.setFilter(filter);
        this.prmtCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
        this.prmtCostedDept.setEntityViewInfo(view);
        this.prmtCostedDept.setEditFormat("$number$");
        this.prmtCostedDept.setDisplayFormat("$name$");
        this.prmtCostedDept.setCommitFormat("$number$");
	}
	protected void prmtCostedDept_dataChanged(DataChangeEvent e) throws Exception {
         getBgAmount();
	}
	private boolean isShowBgAmount=false;
	
	protected BigDecimal getAccActOnLoadBgAmount(String bgItemNumber,boolean isFromWorkFlow) throws BOSException, SQLException{
		if(this.prmtCostedDept.getValue()==null) return FDCHelper.ZERO;
		
		String bgComItem="";
		Set bgComItemSet=new HashSet();
		bgComItemSet.add(bgItemNumber);
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select distinct t2.fformula fformula from T_BG_BgTemplateCtrlSetting t1 left join T_BG_BgTemplateCtrlSetting t2 on t1.fgroupno=t2.fgroupno where t1.FOrgUnitId ='"+((CostCenterOrgUnitInfo)this.prmtCostedDept.getValue()).getId().toString()+"'");
		_builder.appendSql(" and t1.fgroupno!='-1' and t2.fgroupno!='-1' and t1.fformula like '%"+bgItemNumber+"%' and t2.fformula not like '%"+bgItemNumber+"%'");
		IRowSet rowSet = _builder.executeQuery();
		
		while(rowSet.next()){
			if(rowSet.getString("fformula")!=null){
				String formula=rowSet.getString("fformula");
				bgComItemSet.add(formula.substring(9, formula.indexOf(",")-1));
			}
		}
		Object[] bgObject = bgComItemSet.toArray();
    	for(int i=0;i<bgObject.length;i++){
        	if(i==0){
        		bgComItem=bgComItem+"'"+bgObject[i].toString()+"'";
        	}else{
        		bgComItem=bgComItem+",'"+bgObject[i].toString()+"'";
        	}
        }
		_builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(entry.frequestAmount-isnull(entry.factPayAmount,0))payAmount from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
		_builder.appendSql(" left join T_BG_BgItem bgItem on bgItem.fid=entry.fbgitemid ");
		_builder.appendSql(" where bill.fisBgControl=1 and bill.FCostedDeptId='"+((CostCenterOrgUnitInfo)this.prmtCostedDept.getValue()).getId().toString()+"' and bgItem.fnumber in("+bgComItem+")");
		if(isFromWorkFlow){
			_builder.appendSql(" and bill.fstate in('3AUDITTING','4AUDITTED') ");
		}else{
			_builder.appendSql(" and bill.fstate in('2SUBMITTED','3AUDITTING','4AUDITTED') ");
		}
		_builder.appendSql(" and bill.fhasClosed=0 and bill.famount is not null");
		
		if(editData.getId()!=null){
			_builder.appendSql(" and bill.fcontractid!='"+editData.getId().toString()+"'");
		}
		rowSet = _builder.executeQuery();
		while(rowSet.next()){
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	protected void getPayPlanValue() throws BOSException, SQLException, EASBizException{
		this.txtPayPlanValue.setText(null);
		if (!STATUS_ADDNEW.equals(getOprtState()) && !STATUS_EDIT.equals(getOprtState())) {
			return;
		}
		if(this.editData.getProgrammingContract()==null||this.pkbookedDate.getValue()==null){
			return;
		}
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("CIFI_PAYPLAN", editData.getCurProject().getFullOrgUnit().getId().toString());
		HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
		if(hmAllParam.get("CIFI_PAYPLAN")==null||!Boolean.valueOf(hmAllParam.get("CIFI_PAYPLAN").toString()).booleanValue()){
			return;
		}
		BigDecimal amount=FDCHelper.ZERO;
	
		Calendar cal = Calendar.getInstance();
		cal.setTime((Date)this.pkbookedDate.getValue());
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	
    	filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",this.editData.getProgrammingContract().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.CONFIRMED_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
    	
    	SorterItemInfo bizDateSort=new SorterItemInfo("head.bizDate");
    	bizDateSort.setSortType(SortType.DESCEND);
    	view.getSorter().add(bizDateSort);
    	view.getSelector().add("dateEntry.*");
    	view.setFilter(filter);
    	
    	ProjectMonthPlanGatherEntryCollection col=ProjectMonthPlanGatherEntryFactory.getRemoteInstance().getProjectMonthPlanGatherEntryCollection(view);
    	if(col.size()>0){
    		ProjectMonthPlanGatherEntryInfo ppEntry=col.get(0);
    		for(int i=0;i<ppEntry.getDateEntry().size();i++){
    			int comYear=ppEntry.getDateEntry().get(i).getYear();
    			int comMonth=ppEntry.getDateEntry().get(i).getMonth();
    			if(comYear==year&&comMonth==month){
    				amount=ppEntry.getDateEntry().get(i).getAmount();
    			}
    		}
    	}
    	if(amount.toString().equals("0E-10")){
    		amount=FDCHelper.ZERO;
    	}
		this.txtPayPlanValue.setText("本月付款计划金额："+amount.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
	}
	protected PaymentBillInfo createTempPaymentBill() throws BOSException{
		PaymentBillInfo pay=new PaymentBillInfo();
		for(int i=0;i<this.kdtBgEntry.getRowCount();i++){
			PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
			BigDecimal requestAmount=(BigDecimal) this.kdtBgEntry.getRow(i).getCell("requestAmount").getValue();
			
			entry.setAmount(requestAmount);
			entry.setLocalAmt(requestAmount);
            entry.setActualAmt(requestAmount);
            entry.setActualLocAmt(requestAmount);
            entry.setCurrency((CurrencyInfo) this.prmtcurrency.getValue());
            entry.setExpenseType((ExpenseTypeInfo) this.kdtBgEntry.getRow(i).getCell("expenseType").getValue());
            entry.setCostCenter((CostCenterOrgUnitInfo) this.prmtCostedDept.getValue());
            pay.getEntries().add(entry);
		}
		pay.setCompany((CompanyOrgUnitInfo)this.prmtCostedCompany.getValue());
		pay.setCostCenter((CostCenterOrgUnitInfo) this.prmtCostedDept.getValue());
		pay.setPayDate(FDCCommonServerHelper.getServerTimeStamp());
		pay.setCurrency((CurrencyInfo) this.prmtcurrency.getValue());
		
		return pay;
	}
	protected void getBgAmount() throws EASBizException, BOSException, SQLException{
		if(isShowBgAmount){
			
			String amount=null;
	        if(this.kdtBgEntry.getSelectManager().size() > 0)
	        {
	            int top = this.kdtBgEntry.getSelectManager().get().getTop();
	            if(isTableColumnSelected(this.kdtBgEntry)){
	            	if(txtBgAmount!=null){
	            		txtBgAmount.setVisible(false);
	            	}
	                return;
	            }
	            if(this.kdtBgEntry.getRow(top) == null){
	            	if(txtBgAmount!=null){
	            		txtBgAmount.setVisible(false);
	            	}
	                return;
	            }
	            if(this.kdtBgEntry.getRow(top).getCell("expenseType").getValue() != null && this.kdtBgEntry.getRow(top).getCell("bgItem").getValue() != null && this.prmtCostedDept.getValue() != null
						&& this.prmtCostedCompany.getValue() != null){
	            	if(txtBgAmount==null){
	            		txtBgAmount=new KDTextField();
	            		txtBgAmount.setEnabled(false);
	            		this.contBgEntry.add(txtBgAmount);
	            		txtBgAmount.setBounds(400, 2, 400, 18);
	            	}
	            	try {
						beforeStoreFields(null);
					} catch (Exception e) {
						e.printStackTrace();
					}
					this.storeFields();
					BgItemInfo bgItem=(BgItemInfo)this.kdtBgEntry.getRow(top).getCell("bgItem").getValue();
					
	    			IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getRemoteInstance();
	    	        BgCtrlResultCollection coll = iBgControlFacade.getBudget("com.kingdee.eas.fi.cas.app.PaymentBill", new BgCtrlParamCollection(),createTempPaymentBill());
	    	        if(coll.size()>0){
	    	        	for(int i=0;i<coll.size();i++){
	    	        		if(bgItem.getNumber().equals(coll.get(i).getItemCombinNumber())){
	    	        			BigDecimal balanceAmount=FDCHelper.ZERO;
	    						if(coll.get(i).getBalance() != null){
	    							balanceAmount=coll.get(i).getBalance();
	    						}
	    	        			BigDecimal balance=balanceAmount.subtract(getAccActOnLoadBgAmount(bgItem.getNumber(),true));
	    	        			amount="部门:"+editData.getCostedDept().getName()+" 预算项目:"+bgItem.getName()+" 预算余额:"+balance.toString();
	    	        			break;
	    	        		}
	    	        	}
	    	        }
	            	if(amount!=null){
	            		txtBgAmount.setVisible(true);
	                	txtBgAmount.setText(amount);
	            	}else{
	            		txtBgAmount.setVisible(false);
	            	}
	            }else{
	            	if(txtBgAmount!=null){
	            		txtBgAmount.setVisible(false);
	            	}
	            }
	        }
		}
	}
	protected void cbIsInvoice_itemStateChanged(ItemEvent e) throws Exception {
		if(this.cbIsInvoice.isSelected()){
			this.txtInvoiceNumber.setEnabled(true);
			this.txtInvoiceAmt.setEnabled(true);
			this.pkInvoiceDate.setEnabled(true);
		}else{
			this.txtInvoiceNumber.setEnabled(false);
			this.txtInvoiceAmt.setEnabled(false);
			this.pkInvoiceDate.setEnabled(false);
		}
	}
	protected void cbIsBgControl_itemStateChanged(ItemEvent e) throws Exception {
		if(this.cbIsBgControl.isSelected()){
			if(!this.getOprtState().equals(OprtState.VIEW)){
				if(this.kdtBgEntry.getRowCount()==0){
					IRow row=this.kdtBgEntry.addRow();
					ContractWithoutTextBgEntryInfo entry=new ContractWithoutTextBgEntryInfo();
					row.setUserObject(entry);
				}
//				if(this.editData.getProgrammingContract()!=null){
//					SelectorItemCollection sel=new SelectorItemCollection();
//					sel.add("costEntries.costAccount.longNumber");
//					ProgrammingContractInfo pc=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(this.editData.getProgrammingContract().getId()),sel);
//					Set costAccount=new HashSet();
//					for(int i=0;i<pc.getCostEntries().size();i++){
//						costAccount.add(pc.getCostEntries().get(i).getCostAccount().getLongNumber());
//					}
//					row.getCell("bgItem").setValue(CostAccountWithBgItemFactory.getRemoteInstance().getBgItem(costAccount));
//				}
			}
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
			
			this.kdtBgEntry.getColumn("expenseType").getStyleAttributes().setLocked(false);
			this.kdtBgEntry.getColumn("accountView").getStyleAttributes().setLocked(false);
			this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setLocked(false);
			
//			this.prmtApplier.setEnabled(true);
			this.prmtApplierOrgUnit.setEnabled(true);
			this.prmtCostedCompany.setEnabled(true);
			this.prmtCostedDept.setEnabled(true);
			
			BigDecimal amount =TableUtils.getColumnValueSum(this.kdtBgEntry, "requestAmount");
			this.txtamount.setValue(amount);
			this.txtamount.setEnabled(false);
			
			if(this.prmtCostedCompany.getValue()!=null){
				this.prmtCostedDept.setEnabled(true);
			}else{
				this.prmtCostedDept.setEnabled(false);
			}
		}else{
			if(!this.getOprtState().equals(OprtState.VIEW)){
				this.kdtBgEntry.removeRows();
			}
			
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			
			this.kdtBgEntry.getColumn("expenseType").getStyleAttributes().setLocked(true);
			this.kdtBgEntry.getColumn("accountView").getStyleAttributes().setLocked(true);
			this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setLocked(true);
//			this.prmtApplier.setEnabled(false);
			this.prmtApplierOrgUnit.setEnabled(false);
			this.prmtCostedCompany.setEnabled(false);
			this.prmtCostedDept.setEnabled(false);
			
			this.txtamount.setEnabled(true);
		}
	}
	protected EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo){
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null){
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}
	private void afterOnload() throws BOSException, EASBizException {		
		
		txtAllInvoiceAmt.setMaximumValue(FDCHelper.MAX_DECIMAL);
		
		//设置合同类型F7
		prmtContractType.setSelector(new ContractTypePromptSelector(this));
		
		//根据参数是否显示合同费用项目
		if(!isShowCharge){
			this.conConCharge.setVisible(false);
			this.prmtConCharge.setRequired(false);
			this.actionViewBgBalance.setVisible(false);
			this.actionViewBgBalance.setEnabled(false);
			this.menuItemViewBgBalance.setVisible(false);
			this.menuItemViewBgBalance.setEnabled(false);
		}else{
			this.conConCharge.setVisible(true);
			this.prmtConCharge.setRequired(true);
			this.actionViewBgBalance.setVisible(true);
			this.actionViewBgBalance.setEnabled(true);
			this.menuItemViewBgBalance.setVisible(true);
			this.menuItemViewBgBalance.setEnabled(true);
		}
		//只显示已启用的合同费用项目
		EntityViewInfo view = this.prmtConCharge.getEntityViewInfo();
		if(view == null){
			view = new EntityViewInfo();
		}
		FilterInfo filterCharge = new FilterInfo();
		filterCharge.getFilterItems().add(new FilterItemInfo("isEnabled",new Integer(1)));
		view.setFilter(filterCharge);
		this.prmtConCharge.setEntityViewInfo(view);
		
		OrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit();
		EntityViewInfo v = new EntityViewInfo();
		FilterInfo filter  = new FilterInfo();
		//当前可用的工程项目
		Set projLeafNodesIdSet = (Set)getUIContext().get("projLeafNodesIdSet");
		if(projLeafNodesIdSet!=null && projLeafNodesIdSet.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("id", projLeafNodesIdSet, CompareType.INCLUDE));			
		}else{
			filter.getFilterItems().add(new FilterItemInfo("isLeaf",new Integer(1)));
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.longNumber", orgUnitInfo.getLongNumber() + "%",CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.isCostOrgUnit", Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("id", " select b.fid from T_FDC_ProjectWithCostCenterOU a "
					+" inner join t_fdc_curproject p on a.fcurprojectid=p.fid "
					+" inner join t_fdc_curproject b on charindex(p.FLongNumber || '!',b.FLongNumber || '!')=1 "
								+" where p.flevel=1 ", CompareType.INNER));
		}

		v.setFilter(filter);
		prmtcurProject.setEntityViewInfo(v);
		
		txtNumber.setMaxLength(80);
    	txtbillName.setMaxLength(80);
    	txtNoPaidReason.setMaxLength(40);
    	txtInvoiceNumber.setMaxLength(80);

    	txtMoneyDesc.setLineWrap(true);
    	this.cbPeriod.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PeriodQuery");
    	
    	//增加原币金额的可录入范围
//        txtamount.setPrecision(2);
        txtamount.setMinimumValue(FDCHelper.MIN_VALUE);
        txtamount.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
        if(txtamount.getText()==null||"".equals(txtamount.getText())){
        	txtamount.setValue(FDCHelper.ZERO);
        }
        txtBcAmount.setPrecision(2);
        txtBcAmount.setMinimumValue(FDCHelper.MIN_VALUE);
        txtBcAmount.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
        
//        txtexchangeRate.setPrecision(2);
        txtexchangeRate.setMinimumValue(FDCHelper.MIN_VALUE);
        txtexchangeRate.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
    	
        txtcompletePrjAmt.setPrecision(2);
        //txtcompletePrjAmt.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
        txtcompletePrjAmt.setMinimumValue(FDCHelper.ZERO);
        txtcompletePrjAmt.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE.multiply(FDCHelper.TEN));
        
    	//if(isFirstLoad) isFirstLoad=false;
    	if(isFirstLoad) isFirstLoad=false;
    	PayRequestBillCollection prbc ;
    	if(!STATUS_ADDNEW.equals(getOprtState())) {
	    	//加载付款申请单内容
    		EntityViewInfo evi = new EntityViewInfo();
    		FilterInfo filterInfo = new FilterInfo();
    		filterInfo.getFilterItems().add(new FilterItemInfo("contractId",editData.getId().toString()));    		
    		//SelectorItemCollection selector = new SelectorItemCollection();
    		evi.getSelector().add(new SelectorItemInfo("*"));
    		evi.getSelector().add(new SelectorItemInfo("useDepartment.*"));
    		evi.getSelector().add(new SelectorItemInfo("currency.*"));
    		evi.getSelector().add(new SelectorItemInfo("supplier.*"));
    		evi.getSelector().add(new SelectorItemInfo("settlementType.*"));
    		evi.getSelector().add(new SelectorItemInfo("curProject.*"));
    		evi.getSelector().add(new SelectorItemInfo("paymentType.*"));
    		evi.getSelector().add(new SelectorItemInfo("realSupplier.*"));
    		evi.setFilter(filterInfo);
    		prbc = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(evi);
    		if(prbc.size()!=0){
    			this.prbi = prbc.get(0);
    		}
    		this.prmtPayment.setData(prbi.getPaymentType());
    		this.prmtuseDepartment.setData(prbi.getUseDepartment());
    		this.txtPaymentRequestBillNumber.setText(prbi.getNumber());
    		this.pksignDate.setValue(prbi.getPayDate());
    		this.prmtrealSupplier.setData(prbi.getRealSupplier());
    		this.prmtcurrency.setData(prbi.getCurrency());
    		if(prbi.getExchangeRate()!=null){
    			this.txtexchangeRate.setText(prbi.getExchangeRate().toString());
    		}
    		if(prbi.getCapitalAmount()!=null){
    			this.txtcapitalAmount.setText(prbi.getCapitalAmount());
    		}else{
    			BigDecimal localamount = editData.getAmount();
    			if(localamount.compareTo(FDCConstants.ZERO)!=0){
    				localamount = localamount.setScale(2,BigDecimal.ROUND_HALF_UP);
    			}
    			this.txtcapitalAmount.setText(FDCClientHelper.getChineseFormat(localamount,false));
    		}
    		if(prbi.getPaymentProportion()!=null){
//    			this.txtPaymentProportion.setText(prbi.getPaymentProportion().toString());
    			this.txtPaymentProportion.setValue(prbi.getPaymentProportion());
    		}
    		if(prbi.getCompletePrjAmt()!=null){
//    			this.txtcompletePrjAmt.setText(prbi.getCompletePrjAmt().toString());
    			txtcompletePrjAmt.setValue(prbi.getCompletePrjAmt());
    		}
    		this.txtMoneyDesc.setText(prbi.getMoneyDesc()); 
    		if(prbi.getOriginalAmount()!=null){
    			txtamount.setValue(prbi.getOriginalAmount());
    			//this.txtamount.setText(prbi.getAmount().toString());//原币金额
//    			this.setAmount();
    		}
    		
    		if(chkUrgency!=null){
    			if(prbi.getUrgentDegree()==UrgentDegreeEnum.URGENT){
    	    		chkUrgency.setSelected(true);
    	    	}else{
    	    		chkUrgency.setSelected(false);
    	    	}
    		}
    		
   			this.txtattachment.setText(String.valueOf(prbi.getAttachment()));   			
			txtBankAcct.setText(prbi.getRecAccount());
			txtBank.setText(prbi.getRecBank());
    			
    		//提交后,不允许修改项目
    		if(!this.editData.getState().getValue().equals(FDCBillStateEnum.SAVED_VALUE)){
    			this.prmtcurProject.setEnabled(false);
    		}
    		//add by jianxing_zhou
    		addListener();
    	}
        
    	this.txtPaymentProportion.setEditable(false);
		this.txtcompletePrjAmt.setEditable(false);		
		this.cbPeriod.setEnabled(false);
		
		if(getOprtState()!=OprtState.ADDNEW&&getOprtState()!=OprtState.EDIT){
			chkUrgency.setEnabled(false);
			if(getOprtState()==OprtState.VIEW){
				chkCostsplit.setEnabled(false);
			}
			
			chkNeedPaid.setEnabled(false);
			txtNoPaidReason.setEnabled(false);
			prmtAccount.setEnabled(false);
		
		}
		
		String cuId = editData.getCU().getId().toString();	
		FDCClientUtils.setRespDeptF7(prmtuseDepartment, this, canSelecOtherOrgPerson() ? null :cuId);
		//初始化供应商F7
		FDCClientUtils.initSupplierF7(this, prmtrealSupplier, cuId);

		//在启用财务成本一体化参数时，若勾选“无需付款”，则出来“贷方科目”字段，审批后，该字段金额自动填入“付款科目”，相应的付款单亦自动“已付款”状态；
		//若不启用一体化参数，若勾选“无需付款”，无文本合同审批后，对应的付款单自动变为“已付款”。
		if(isFinacial){
			kDLabelContainer16.setVisible(true);
		}else{
			kDLabelContainer16.setVisible(false);
		}
		
	    setPrecision();
		
		txtBank.setRequired(false);
		txtBankAcct.setRequired(false); 
		
		//无文本合同录入时设置合同类型为必录，付款类型非必录
		prmtContractType.setRequired(true);
		prmtPayment.setRequired(true);
		prmtsettlementType.setRequired(false);
		handleOldData();
		this.actionPrint.setEnabled(true);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionPrintPreview.setVisible(true);
        
        //业务日期判断为空时取期间中断
        if(pkbookedDate!=null&&pkbookedDate.isSupportedEmpty()){
            pkbookedDate.setSupportedEmpty(false);
        }
		//累计发票金额
        txtInvoiceAmt.setPrecision(2);
		txtInvoiceAmt.setSupportedEmpty(true);
		txtAllInvoiceAmt.setSupportedEmpty(true);
		txtInvoiceAmt.setMinimumValue(FDCHelper.MIN_VALUE);
		txtInvoiceAmt.setEnabled(false);
		txtAllInvoiceAmt.setEnabled(false);
        txtInvoiceAmt.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
        
		txtInvoiceAmt.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				BigDecimal invoiceAmt = txtInvoiceAmt.getBigDecimalValue();
				txtAllInvoiceAmt.setValue(allInvoiceAmt.add(FDCHelper.toBigDecimal(invoiceAmt)));
			}
		});
		/**
		 * 使发票号是否必录与参数的值关联，当参数值为false时，发票号为非必录项。
		 * -- by jiadong 2010-06-29
		 */
//		if(isInvoiceRequired){
			txtInvoiceNumber.setRequired(isInvoiceRequired);
//		}
			
		this.btnViewBudget.setEnabled(true);
		// 计划项目
		try {
//			initFDCDepConPayPlanF7();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    protected void prmtcurProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	if(!this.isIncorporation){
    		return ;
    	}
    	
    	if(e.getNewValue()!=null
    			&& !GlUtils.isEqual(e.getOldValue() ,e.getNewValue())		
    	){
    		CurProjectInfo projectInfo = (CurProjectInfo)e.getNewValue();
    		curProject = projectInfo;
    		editData.setCurProject(projectInfo);
    		
    		//bookedDate_dataChanged(e);
    		getUIContext().put("projectId",BOSUuid.read(curProject.getId().toString()));
    		this.fetchInitData();
    		
			editData.setBookedDate(bookedDate);
			editData.setPeriod(curPeriod);
			
			pkbookedDate.setValue(bookedDate);
			cbPeriod.setValue(curPeriod);
    	}
    }

    protected void bookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	if(editData.getCurProject()==null){
    		return ;
    	}
    	//e.getOldValue();
    	String projectId = this.editData.getCurProject().getId().toString();    	
    	fetchPeriod(e,pkbookedDate,cbPeriod, projectId,  false);
    	
    	prmtcurrency_dataChanged(null);
    }
    
	public void storeFields() {
		if("供应商".equals(this.comboPayeeType.getSelectedItem().toString())){
    		this.editData.setReceiveUnit((SupplierInfo)prmtreceiveUnit.getData());//收款单位
    		this.editData.setPerson(null);
    		this.cbFeeType.setSelectedItem(null);
		}else{
			this.editData.setPerson((PersonInfo)prmtreceiveUnit.getData());
			this.editData.setReceiveUnit(null);
		}
		this.storeBgEntryTable();
		
		if(FeeTypeEnum.FEE.equals(cbFeeType.getSelectedItem())){
			this.kdtTraEntry.removeRows();
		}else if(FeeTypeEnum.TRA.equals(cbFeeType.getSelectedItem())){
			this.kdtFeeEntry.removeRows();
		}else{
			this.kdtTraEntry.removeRows();
			this.kdtFeeEntry.removeRows();
		}
		super.storeFields();
	}
    protected void beforeStoreFields(ActionEvent e) throws Exception {
    	setAmount();
    	super.beforeStoreFields(e);
    	this.prbi.setPaymentType((PaymentTypeInfo)prmtPayment.getData());//付款类型
    	this.prbi.setUseDepartment((AdminOrgUnitInfo)prmtuseDepartment.getData());//用款部门
    	this.prbi.setNumber(txtPaymentRequestBillNumber.getText());//付款申请单编码
    	if(FDCHelper.isEmpty(prbi.getNumber())){
    		this.prbi.setNumber(txtNumber.getText());
    	}
    	this.prbi.setPayDate(pksignDate.getSqlDate());//付款日期
    	if("供应商".equals(this.comboPayeeType.getSelectedItem().toString())){
    		if(prmtreceiveUnit.getData()!=null &&prmtreceiveUnit.getData() instanceof PersonInfo){
    			prmtreceiveUnit.setValue(null);
				FDCMsgBox.showWarning(this,"实际收款单位请选择供应商基础资料！");
				SysUtil.abort();
			}
    		this.prbi.setSupplier((SupplierInfo)prmtreceiveUnit.getData());//收款单位
    		this.prbi.setPerson(null);
		}else{
			if(prmtreceiveUnit.getData()!=null &&prmtreceiveUnit.getData() instanceof SupplierInfo){
				prmtreceiveUnit.setValue(null);
				FDCMsgBox.showWarning(this,"实际收款单位请选择人员基础资料！");
				SysUtil.abort();
			}
			this.prbi.setPerson((PersonInfo)prmtreceiveUnit.getData());
			this.prbi.setSupplier(null);
		}
    	this.prbi.setRealSupplier((SupplierInfo)prmtrealSupplier.getData());//实际收款单位
    	this.prbi.setSettlementType((SettlementTypeInfo)prmtsettlementType.getData());//结算方式    	
    	this.prbi.setRecBank(txtBank.getText());//收款银行
    	this.prbi.setRecAccount(txtBankAcct.getText());//收款帐号
    	//是否加急界面改成chkbox
    	if(this.chkUrgency.isSelected()){
    		this.prbi.setUrgentDegree(UrgentDegreeEnum.URGENT);
    	}else{
    		this.prbi.setUrgentDegree(UrgentDegreeEnum.NORMAL);
    	}
//    	this.prbi.setUrgentDegree((UrgentDegreeEnum)combUrgentDegree.getSelectedItem());//紧急程度
    	this.prbi.setCurrency((CurrencyInfo)prmtcurrency.getData());//币别
    	this.prbi.setLatestPrice(txtBcAmount.getBigDecimalValue());//最新造价
    	this.prbi.setExchangeRate(txtexchangeRate.getBigDecimalValue());//汇率
    	this.prbi.setCapitalAmount(txtcapitalAmount.getText());//大写金额
    	this.prbi.setPaymentProportion(txtPaymentProportion.getBigDecimalValue());//付款比例
    	this.prbi.setCompletePrjAmt(txtcompletePrjAmt.getBigDecimalValue());//已完工工程量金额
    	this.prbi.setAmount(txtBcAmount.getBigDecimalValue());//原币金额
    	this.prbi.setOriginalAmount(txtamount.getBigDecimalValue());//原币金额
//    	this.prbi.setMoneyDesc(txtDescription.getText());//备注
//    	prbi.setUsage(txtMoneyDesc.getText());//款项说明
    	//提单：无文本合同提交后，再查看该单据时，填写的“款项说明字段信息”查看不到   以上注释行导致 by Cassiel_peng 2009-10-10 
    	this.prbi.setMoneyDesc(txtMoneyDesc.getText());//款项说明
    	this.prbi.setUsage(txtMoneyDesc.getText());//用途  也不晓得要赋个啥值，看之前注释的那两行代码也是想把款项说明的值赋给它，那就这样吧！
    	
    	if(txtattachment.getIntegerValue()!=null){
    		this.prbi.setAttachment(txtattachment.getIntegerValue().intValue());//附件
    	}
    	this.prbi.setCurProject((CurProjectInfo) this.prmtcurProject.getValue());//工程项目
    	prbi.setSourceType(SourceTypeEnum.ADDNEW);
    	prbi.setInvoiceNumber(txtInvoiceNumber.getText());//发票号
    	prbi.setInvoiceDate(pkInvoiceDate.getSqlDate());//发票日期
    	prbi.setInvoiceAmt(txtInvoiceAmt.getBigDecimalValue());//发票金额
    	prbi.setAllInvoiceAmt(txtAllInvoiceAmt.getBigDecimalValue());//累计发票金额
    	
    	prbi.setIsBgControl(this.cbIsBgControl.isSelected());
    	prbi.setApplier((PersonInfo) this.prmtApplier.getData());
    	prbi.setApplierCompany((CompanyOrgUnitInfo) this.prmtApplierCompany.getData());
    	prbi.setApplierOrgUnit((AdminOrgUnitInfo) this.prmtApplierOrgUnit.getData());
    	prbi.setCostedCompany((CompanyOrgUnitInfo) this.prmtCostedCompany.getData());
    	prbi.setCostedDept((CostCenterOrgUnitInfo) this.prmtCostedDept.getData());
    	prbi.setPayBillType((PaymentBillTypeInfo) this.prmtPayBillType.getData());
    	prbi.setPayContentType((PayContentTypeInfo) this.prmtPayContentType.getData());
    	prbi.setLocalCurrency(this.baseCurrency);
    	prbi.getBgEntry().clear();
    	for(int i=0;i<this.kdtBgEntry.getRowCount();i++){
    		IRow row = this.kdtBgEntry.getRow(i);
    		PayRequestBillBgEntryInfo entry=new PayRequestBillBgEntryInfo();
    		entry.setSeq(i);
    		entry.setExpenseType((ExpenseTypeInfo)row.getCell("expenseType").getValue());
    		entry.setAccountView((AccountViewInfo)row.getCell("accountView").getValue());
    		entry.setRequestAmount((BigDecimal)row.getCell("requestAmount").getValue());
    		entry.setAmount((BigDecimal)row.getCell("amount").getValue());
    		entry.setBgItem((BgItemInfo)row.getCell("bgItem").getValue());
    		prbi.getBgEntry().add(entry);
    	}
    	this.editData.put("PayRequestBillInfo",prbi);//传值	
    	
    	// 需求:无文本合同序时簿和无文本合同查询序时簿界面增加用款部门列,以前是单独保存在付款申请单上的一个用款部门，满足不了需求   by cassiel_peng 2011-03-15
    	this.editData.setUseDepartment((AdminOrgUnitInfo)prmtuseDepartment.getData());//用款部门

    	//业务日期默认未设置，如果预算控制时以业务日期控制，会导致打印预览中显示的业务日期无内容，手动设置一下
    	editData.setBizDate((Date)this.pkbookedDate.getValue());// added by Owen_wen
    }
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// 保存前反写所关联的框架合约“是否引用”字段
    	updateProgrammingContract(this.editData.getProgrammingContract(),0);
		//合同类型不能为空
		if(prmtContractType.getData() == null)
    	{
    		MsgBox.showWarning("合同类型不能为空！");
    		SysUtil.abort() ;
    	}
		//付款类型不能为空
		if(prmtPayment.getData() == null)
		{
			MsgBox.showWarning("付款类别不能为空！");
    		SysUtil.abort() ;
		}
    	super.actionSave_actionPerformed(e);
		// 保存后反写写所关联的框架合约状态
    	updateProgrammingContract(this.editData.getProgrammingContract(),1);
//    	ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo()
//    	PayRequestBillFactory.getRemoteInstance().addnew(this.prbi);
		modify=false;
		setBgEditState();
    }
	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
	
		
		//附件数据会在点击修改的时候丢失，是因为没有绑定
		String obj = txtattachment.getText();
		super.actionEdit_actionPerformed(e);
		
		
		chkUrgency.setEnabled(true);
		chkCostsplit.setEnabled(true);
		
//		chkNeedPaid.setEnabled(true);
		if(chkNeedPaid.isSelected()){
			txtNoPaidReason.setEnabled(true);
			prmtAccount.setEnabled(true);
		}else{
			txtNoPaidReason.setEnabled(false);	
			prmtAccount.setEnabled(false);
			txtNoPaidReason.setText(null);
			prmtAccount.setValue(null);
		}
		
		if(!this.editData.getState().getValue().equals(FDCBillStateEnum.SAVED_VALUE)){
			this.prmtcurProject.setEnabled(false);
		};
		this.txtattachment.setText(obj);
    	txtPaymentProportion.setEditable(false);
		txtcompletePrjAmt.setEditable(false);
		setAmount();
		this.menuBiz.setVisible(false);

		//new update by renliang at 2010-5-12
		if(this.isNotEnterCAS){
			chkNeedPaid.setEnabled(false);
			chkNeedPaid.setSelected(true);
		}
		setBgEditState();
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// 提交前反写所关联的框架合约“是否引用”字段
		updateProgrammingContract(this.editData.getProgrammingContract(),0);
		//合同类型不能为空
		if(prmtContractType.getData() == null)
    	{
    		MsgBox.showWarning("合同类型不能为空！");
    		SysUtil.abort() ;
    	}
		//付款类型不能为空
		if(prmtPayment.getData() == null)
		{
			MsgBox.showWarning("付款类别不能为空！");
    		SysUtil.abort() ;
		}
		
		super.actionSubmit_actionPerformed(e);
		modify=false;
		// 提交后反写写所关联的框架合约状态
		updateProgrammingContract(this.editData.getProgrammingContract(),1);
		//new update by renliang at 2010-5-12
		if(isNotEnterCAS){
			this.chkNeedPaid.setEnabled(false);
			this.chkNeedPaid.setSelected(true);
		}
		setBgEditState();
		
		
	}
	/**
	 * 更新规划合约"是否被引用"字段
	 * 
	 * @param proContId
	 * @param isCiting
	 */
	private void updateProgrammingContract(ProgrammingContractInfo pc, int isCiting) {
		try {
			String oldPCId=null;
			FDCSQLBuilder buildSQL = new FDCSQLBuilder();
			IRowSet iRowSet=null;
			if(this.editData.getId()!=null){
				buildSQL.appendSql("select fprogrammingContract from T_CON_ContractWithoutText where fid='" + this.editData.getId().toString() + "'");
				iRowSet = buildSQL.executeQuery();
				while (iRowSet.next()) {
					oldPCId=iRowSet.getString("fprogrammingContract");
				}
			}
			if(isCiting==0){
				if(oldPCId==null||(pc!=null&&oldPCId.equals(pc.getId().toString()))){
					return;
				}
				buildSQL = new FDCSQLBuilder();
				buildSQL.appendSql("select count(*) count from T_CON_ContractWithoutText where fprogrammingContract='" + oldPCId + "' and fid!='" + this.editData.getId().toString() + "'");
				int count = 0;
				iRowSet = buildSQL.executeQuery();
				if (iRowSet.next()) {
					count = iRowSet.getInt("count");
				}
				if(count>0){
					return;
				}
				buildSQL = new FDCSQLBuilder();
				buildSQL.appendSql("update T_CON_ProgrammingContract set FIsWTCiting = " + isCiting + " where FID = '" + oldPCId + "'");
				buildSQL.executeUpdate();
			}else if(pc!=null){
				buildSQL = new FDCSQLBuilder();
				buildSQL.appendSql("update T_CON_ProgrammingContract set FIsWTCiting = " + isCiting + " where FID = '" + pc.getId().toString() + "'");
				buildSQL.executeUpdate();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    protected void chkNeedPaid_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	if(chkNeedPaid.isSelected()){
    		txtNoPaidReason.setEnabled(true);
    		prmtAccount.setEnabled(true);
//    		prmtreceiveUnit.setRequired(false);
//    		prmtrealSupplier.setRequired(false);
    		txtBank.setRequired(false);
    		txtBankAcct.setRequired(false); 
    		prmtsettlementType.setRequired(false);
    	}else{
    		txtNoPaidReason.setText(null);
    		txtNoPaidReason.setEnabled(false);
    		prmtAccount.setEnabled(false);
    		prmtAccount.setValue(null);
//    		prmtreceiveUnit.setRequired(true);
//    		prmtrealSupplier.setRequired(true);
//    		txtBank.setRequired(true);
//    		txtBankAcct.setRequired(true);
//    		prmtsettlementType.setRequired(true);
    	}
    	txtBank.repaint();
    	txtBankAcct.repaint();
    }
	/**
	 * 
	 * 描述：返回编码控件（子类必须实现）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-10-13 <p>
	 */
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	protected void chkgenPaymentBill_itemStateChanged(ItemEvent e) throws Exception {
//		if(e.getStateChange() == ItemEvent.SELECTED) {
//			chkgenPaymentReque.setSelected(true);
//		}
		
	}
	protected void chkgenPaymentReque_itemStateChanged(ItemEvent e) throws Exception {
//		if(e.getStateChange() == ItemEvent.DESELECTED) {
//			chkgenPaymentBill.setSelected(false);
//		}
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
//		if(STATUS_ADDNEW.equals(oprtType)) {
//			prmtcurProject.setEnabled(true);
//		}
//		else {
			this.prmtcurProject.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			prmtcurProject.setEnabled(false);
//		}
			setProgAndAccountState((ContractTypeInfo) prmtContractType.getValue());
		
		txtPaymentRequestBillNumber.setEditable(false);
		setBgEditState();
	}
	private void setProgAndAccountState(ContractTypeInfo contractType){
		if(STATUS_ADDNEW.equals(this.getOprtState()) ||STATUS_EDIT.equals(this.getOprtState())){
			if(contractType!=null&&contractType.isIsAccountView()){
				this.btnAccountView.setEnabled(true);
				this.btnProgram.setEnabled(false);
			}else{
				if(this.editData!=null&&this.editData.getCurrency()!=null&&this.editData.getCurProject().isIsWholeAgeStage()){
					this.btnProgram.setEnabled(false);
				}else{
					this.btnProgram.setEnabled(true);
				}
				this.btnAccountView.setEnabled(false);
			}
		}else{
			btnProgram.setEnabled(false);
			btnAccountView.setEnabled(false);
		}
		btnAccountView.setVisible(false);
	}
	public void setBgEditState(){
		if(this.cbIsBgControl.isSelected()){
			if (getOprtState().equals(OprtState.VIEW)){
				this.actionAddLine.setEnabled(false);
				this.actionInsertLine.setEnabled(false);
				this.actionRemoveLine.setEnabled(false);
			}else{
				this.actionAddLine.setEnabled(true);
				this.actionInsertLine.setEnabled(true);
				this.actionRemoveLine.setEnabled(true);
				
				this.txtamount.setEnabled(false);
			}
			this.kdtBgEntry.getColumn("expenseType").getStyleAttributes().setLocked(false);
			this.kdtBgEntry.getColumn("accountView").getStyleAttributes().setLocked(false);
			this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setLocked(false);
//			this.prmtApplier.setEnabled(true);
			this.prmtApplierOrgUnit.setEnabled(true);
			this.prmtCostedCompany.setEnabled(true);
			this.prmtCostedDept.setEnabled(true);
		}else{
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			
			this.kdtBgEntry.getColumn("expenseType").getStyleAttributes().setLocked(true);
			this.kdtBgEntry.getColumn("accountView").getStyleAttributes().setLocked(true);
			this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setLocked(true);
//			this.prmtApplier.setEnabled(false);
			this.prmtApplierOrgUnit.setEnabled(false);
			this.prmtCostedCompany.setEnabled(false);
			this.prmtCostedDept.setEnabled(false);
			
			if (!getOprtState().equals(OprtState.VIEW)){
				this.txtamount.setEnabled(true);
			}
		}
		if(this.cbIsBgControl.isSelected()&&this.prmtCostedCompany.getValue()!=null){
			this.prmtCostedDept.setEnabled(true);
		}else{
			this.prmtCostedDept.setEnabled(false);
		}
		if(this.cbIsInvoice.isSelected()){
			this.txtInvoiceNumber.setEnabled(true);
			this.txtInvoiceAmt.setEnabled(true);
			this.pkInvoiceDate.setEnabled(true);
		}else{
			this.txtInvoiceNumber.setEnabled(false);
			this.txtInvoiceAmt.setEnabled(false);
			this.pkInvoiceDate.setEnabled(false);
		}
		if(!isOtherCostedDept){
			this.prmtCostedCompany.setEnabled(false);
		}
	}
	/**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
        //之前调用onLoad 会导致父类CoreBillEditUI中多次增加目录菜单“传阅意见查看”
        //故把onLoad后续方法抽象出来 单独执行
        afterOnload();
        prmtrealSupplier.setEnabled(false);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionPre_actionPerformed(e);
        //之前调用onLoad 会导致父类CoreBillEditUI中多次增加目录菜单“传阅意见查看”
        //故把onLoad后续方法抽象出来 单独执行
    	afterOnload();
    	prmtrealSupplier.setEnabled(false);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
        //之前调用onLoad 会导致父类CoreBillEditUI中多次增加目录菜单“传阅意见查看”
        //故把onLoad后续方法抽象出来 单独执行
        afterOnload();
        prmtrealSupplier.setEnabled(false);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionLast_actionPerformed(e);
        //之前调用onLoad 会导致父类CoreBillEditUI中多次增加目录菜单“传阅意见查看”
        //故把onLoad后续方法抽象出来 单独执行
    	afterOnload();
    	prmtrealSupplier.setEnabled(false);
    }

	protected void prmtreceiveUnit_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtreceiveUnit_dataChanged(e);
		
		if(e.getNewValue() != null) {
			if("供应商".equals(this.comboPayeeType.getSelectedItem().toString())){
//				prmtrealSupplier.setData(prmtreceiveUnit.getData());
				prmtrealSupplier.setEnabled(true);
			}else{
				prmtrealSupplier.setData(null);
				prmtrealSupplier.setEnabled(false);
			}
		}else{
			prmtrealSupplier.setData(null);
			prmtrealSupplier.setEnabled(false);
		}
		if("供应商".equals(this.comboPayeeType.getSelectedItem().toString())){
			prmtrealSupplier.setRequired(true);
			prmtrealSupplier.setEnabled(true);
		}else{
			prmtrealSupplier.setRequired(false);
			prmtrealSupplier.setEnabled(false);
		}
	}
    /**
     * output prmtrealSupplier_dataChanged method
     */
    protected void prmtrealSupplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {

		super.prmtrealSupplier_dataChanged(e);
		Object newValue = e.getNewValue();
		if (newValue instanceof SupplierInfo)
		{
			//首次加载
			if(isFirstLoad&&!getOprtState().equals(OprtState.ADDNEW)) return;
			//用newValue.equalse(e.getOldValue()) 会出错,因为比较的是堆栈的值
			if((e.getOldValue() instanceof SupplierInfo)&&
				((SupplierInfo)e.getOldValue()).getId().equals(((SupplierInfo)newValue).getId())) {
				return;
			}
//			e.setOldValue(newValue);
			SupplierInfo supplier = (SupplierInfo) newValue;
			BOSUuid supplierid = supplier.getId();
			BOSUuid FIid = SysContext.getSysContext().getCurrentFIUnit().getId();
			SupplierCompanyInfoInfo companyInfo = SupplierFactory.getRemoteInstance().getCompanyInfo(new ObjectUuidPK(supplierid), new ObjectUuidPK(FIid));
			if (companyInfo != null)
			{
				txtBankAcct.setText(companyInfo.getBankAccount());
				txtBank.setText(companyInfo.getBankName());
			} else
			{
//				logger.info(getRes("canntGetFiOrg"));
			}

		}
	
    }
	protected void setFieldsNull(AbstractObjectValue newData) {
		// TODO Auto-generated method stub
		super.setFieldsNull(newData);
		ContractWithoutTextInfo info = (ContractWithoutTextInfo)newData;
		info.setCurProject(editData.getCurProject());
		
		info.setProgrammingContract(null);
		this.txtPCName.setText(null);
	}

	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectorItemCollection = super.getSelectors();
		selectorItemCollection.add("curProject.id");
		selectorItemCollection.add("curProject.displayName");
		selectorItemCollection.add("curProject.CU.number");
		selectorItemCollection.add("curProject.fullOrgUnit.name");
		
		
		selectorItemCollection.add("orgUnit.id");
		selectorItemCollection.add("state");
		selectorItemCollection.add("currency.number");
		selectorItemCollection.add("currency.name");
		selectorItemCollection.add("amount");
		selectorItemCollection.add("originalAmount");
		
		selectorItemCollection.add("period.number");
		selectorItemCollection.add("period.periodNumber");
		selectorItemCollection.add("period.periodYear");
		selectorItemCollection.add("period.beginDate");
		
		selectorItemCollection.add("fdcDepConPlan.id");
		selectorItemCollection.add("fdcDepConPlan.head.number");
		selectorItemCollection.add("fdcDepConPlan.head.name");
		selectorItemCollection.add("lastUpdateTime");
		
		selectorItemCollection.add("company.*");
		
		selectorItemCollection.add("bgEntry.*");
		selectorItemCollection.add("bgEntry.accountView.*");
		selectorItemCollection.add("btEntry.expenseType.*");
		selectorItemCollection.add("btEntry.bgItem.*");
		
		selectorItemCollection.add("programmingContract.id");
		selectorItemCollection.add("programmingContract.amount");
		selectorItemCollection.add("programmingContract.name");
		selectorItemCollection.add("programmingContract.number");
		selectorItemCollection.add("programmingContract.balance");
//		selectorItemCollection.add(new SelectorItemInfo("programmingContract.programming.*"));
		selectorItemCollection.add("programmingContract.programming.project.id");
		selectorItemCollection.add("programmingContract.contractType.id");
		selectorItemCollection.add("person.*");
		selectorItemCollection.add("receiveUnit.*");
		return selectorItemCollection;
	}
	   
    
    protected void controlDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,ControlDateChangeListener listener) throws Exception
    {
    	if("bookedDate".equals(listener.getShortCut())){
    		bookedDate_dataChanged(e);
    	}
    }
    
    //业务日期变化事件
    ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener("bookedDate");
    
    
    protected void attachListeners() {   	
    	pkbookedDate.addDataChangeListener(bookedDateChangeListener);
    	
    	addDataChangeListener(txtamount);
    	//Add by zhiyuan_tang 2010/07/20 
    	//解决汇率光标离开，发票金额、本位币金额、本期完工工程量计算错误，添加汇率的dataChange事件
    	addDataChangeListener(txtexchangeRate);
    	
    	addDataChangeListener(txtPaymentProportion);
    	addDataChangeListener(prmtcurProject);
    	addDataChangeListener(prmtcurrency);
    	addDataChangeListener(prmtrealSupplier);
    	addDataChangeListener(prmtreceiveUnit);
    	addDataChangeListener(prmtsettlementType);
    	
//    	addDataChangeListener(this.cbIsBgControl);
		addDataChangeListener(this.prmtApplier);
		addDataChangeListener(this.prmtApplierOrgUnit);
		addDataChangeListener(this.prmtCostedCompany);
		addDataChangeListener(this.prmtCostedDept);
		addDataChangeListener(this.prmtContractType);
		addDataChangeListener(this.comboPayeeType);
    }
    
    protected void detachListeners() {
    	pkbookedDate.removeDataChangeListener(bookedDateChangeListener);
    	
    	removeDataChangeListener(txtamount);
    	removeDataChangeListener(txtPaymentProportion);
    	removeDataChangeListener(prmtcurProject);
    	removeDataChangeListener(prmtcurrency);
    	removeDataChangeListener(prmtrealSupplier);
    	removeDataChangeListener(prmtreceiveUnit);
    	removeDataChangeListener(prmtsettlementType);
    	
//    	removeDataChangeListener(this.cbIsBgControl);
		removeDataChangeListener(this.prmtApplier);
		removeDataChangeListener(this.prmtApplierOrgUnit);
		removeDataChangeListener(this.prmtCostedCompany);
		removeDataChangeListener(this.prmtCostedDept);
		removeDataChangeListener(this.prmtContractType);
		removeDataChangeListener(this.comboPayeeType);
    }
		
	public void loadFields() {
		
		//loadFields 最好注销监听器,以免loadFields触发事件
		detachListeners();
		
		super.loadFields();
		
		this.loadBgEntryTable();
		TableUtils.getFootRow(this.kdtBgEntry,new String[]{"requestAmount","amount"});
		TableUtils.getFootRow(this.kdtFeeEntry,new String[]{"amount"});
		TableUtils.getFootRow(this.kdtTraEntry,new String[]{"days","airFee","carFee","cityFee","otherFee","persons","liveDays","liveFee","allowance","other","total"});
        
		setSaveActionStatus();
		if(getOprtState()==OprtState.ADDNEW){		
			this.menuBiz.setVisible(false);
			chkNeedPaid.setSelected(false);
			chkUrgency.setSelected(false);
			//TODO		
			if(paymentTypes!=null && paymentTypes.size()>0){
				prmtPayment.setData(paymentTypes.get(0));
			}
			//当存在无文本合同类型则是合同默认值为无文本合同类型
//			if(contractType!=null && contractType.size()> 0){
//				for(int i = 0; i < contractType.size(); ++i)
//				{
//					if(contractType.get(i).getId().toString().equalsIgnoreCase("eJ6R8AEcEADgAAAAwKgRlawXT3w="))
//					{
//						prmtContractType.setData(contractType.get(i));
//						break ;
//					}
//				}
//			}
			
			prmtrealSupplier.setEnabled(false);			
		}
		
		txtPaymentProportion.setValue(new BigDecimal(100));
		txtcompletePrjAmt.setValue(txtBcAmount.getBigDecimalValue());
		
		if(editData != null && editData.getCurProject() != null) {			
			//String projId = editData.getCurProject().getId().toString();			
			//FullOrgUnitInfo costOrg = FDCClientUtils.getCostOrgByProj(projId);
			
			txtOrg.setText(this.orgUnitInfo.getDisplayName());
			editData.setOrgUnit(orgUnitInfo);
			editData.setCompany(orgUnitInfo);
			editData.setCU(editData.getCurProject().getCU());
		}
		
		//币别选择
		if (this.editData.getCurrency() != null) {
			if (editData.getCurrency().getId().toString().equals(baseCurrency.getId().toString())) {
				// 是本位币时将汇率选择框置灰
				txtexchangeRate.setValue(FDCConstants.ONE);
				txtexchangeRate.setRequired(false);
				txtexchangeRate.setEditable(false);
				txtexchangeRate.setEnabled(false);
			}
		}
		
		//无需付款选择变化
		try {
			chkNeedPaid_actionPerformed(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(this.editData.getReceiveUnit()!=null){
			this.prmtreceiveUnit.setValue(this.editData.getReceiveUnit());
			this.comboPayeeType.setSelectedIndex(0);
		}else if(this.editData.getPerson()!=null){
			this.prmtreceiveUnit.setValue(this.editData.getPerson());
			this.comboPayeeType.setSelectedIndex(1);
		}else{
			this.comboPayeeType.setSelectedIndex(0);
		}
		if(editData!=null&&editData.getCU()!=null){
			String cuId = editData.getCU().getId().toString();	
			if("供应商".equals(this.comboPayeeType.getSelectedItem().toString())){
				FDCClientUtils.initSupplierF7(this, prmtreceiveUnit, cuId);
			}else{
				FDCClientUtils.setPersonF7(prmtreceiveUnit, this,null);
			}
		}
	    attachListeners() ;
	    if(STATUS_EDIT.equals(getOprtState()) && !this.actionAudit.isVisible()
	    		&& !this.actionUnAudit.isVisible()){
	    	this.menuBiz.setVisible(false);
	    }
	    isShowBgAmount=true;
	    
	    this.cbIsBgControl.setEnabled(false);
	    this.chkNeedPaid.setEnabled(false);
	    
	    try {
			fillAttachmnetTable();
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setProgAndAccountState((ContractTypeInfo) prmtContractType.getValue());
	}
	
	protected void comboPayeeType_itemStateChanged(ItemEvent e) throws Exception {
		if (e.getStateChange() == 2) {
			return;
		}
		if (this.comboPayeeType.getSelectedItem() == null) {
			return;
		}
		if(editData!=null&&editData.getCU()!=null){
			prmtreceiveUnit.setData(null);
			prmtrealSupplier.setData(null);
			String cuId = editData.getCU().getId().toString();	
			if("供应商".equals(this.comboPayeeType.getSelectedItem().toString())){
				prmtreceiveUnit.setEntityViewInfo(null);
				FDCClientUtils.initSupplierF7(this, prmtreceiveUnit, cuId);
			}else{
				prmtreceiveUnit.setEntityViewInfo(null);
				FDCClientUtils.setPersonF7(prmtreceiveUnit, this,null);
			}
		}
		if("供应商".equals(this.comboPayeeType.getSelectedItem().toString())){
			prmtrealSupplier.setRequired(true);
			prmtrealSupplier.setEnabled(true);
		}else{
			prmtrealSupplier.setRequired(false);
			prmtrealSupplier.setEnabled(false);
		}
		
		cbFeeType_itemStateChanged(null);
	}

	//设置精度
	protected void setPrecision(){
		CurrencyInfo currency = editData.getCurrency();	    	
    	Date bookedDate = (Date)editData.getBookedDate();
    	
    	ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, currency.getId(),company,bookedDate);
		} catch (Exception e) {			
			e.printStackTrace();
			return ;
		} 
    	
    	int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
    	int exPrecision = curPrecision;
    	
    	if(exchangeRate!=null){
    		exPrecision = exchangeRate.getPrecision();
    	}
    		
    	txtexchangeRate.setPrecision(exPrecision);
    	BigDecimal exRate =  prbi.getExchangeRate();    
    	if(exRate!=null){
    		txtexchangeRate.setValue(exRate);
    	}
    	txtamount.setPrecision(curPrecision);
      	txtamount.setValue(editData.getOriginalAmount());
	}
	
	protected void verifyInputForSave()throws Exception  {
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, txtbillName);
		FDCClientVerifyHelper.verifyEmpty(this, prmtcurProject);
		checkAmountForSave();
		
	}

	protected boolean checkCanSubmit() throws Exception {		
		if(isIncorporation && ((FDCBillInfo)editData).getPeriod()==null){
			MsgBox.showWarning(this,"启用成本月结期间不能为空，请在基础资料维护期间后，重新选择业务日期");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}
	
	private void checkAmountForSave(){
		BigDecimal bd = txtamount.getBigDecimalValue();
		if(FDCHelper.toBigDecimal(bd).compareTo(FDCHelper.ZERO) == 0){
			MsgBox.showWarning(this, "原币金额不能为零!");
			txtamount.requestFocus(true);
			SysUtil.abort();
		}
		
	}
	
	private void checkAmountForSubmit() throws BOSException, SQLException{
		// 11.6.24 不进入动态成本，就不鸟这个控制 add by emanon
		if (chkCostsplit.isSelected()) {
			if ("严格控制".equals(CONTROLNOCONTRACT)
					|| "提示控制".equals(CONTROLNOCONTRACT)) {
				if (prmtPlanProject.getValue() == null) {
					MsgBox.showWarning(this, "计划项目不能为空");
					abort();
				}
			}
			if ("严格控制".equals(CONTROLNOCONTRACT)) {
				BigDecimal getBgBalance = getBgBalance();
				BigDecimal bcAmount = txtBcAmount.getBigDecimalValue();
				if (bcAmount.compareTo(getBgBalance) > 0) {
					MsgBox.showWarning(this, "申请金额大于本期可用预算，不能提交");
					abort();
				}
			}
			if ("提示控制".equals(CONTROLNOCONTRACT)) {
				BigDecimal getBgBalance = getBgBalance();
				BigDecimal bcAmount = txtBcAmount.getBigDecimalValue();
				if (bcAmount.compareTo(getBgBalance) > 0) {
					int result = MsgBox
							.showConfirm2(this, "申请金额大于本期可用预算，是否提交？");
					if (result != MsgBox.OK) {
						SysUtil.abort();
					}
				}
			}
		}
	}
	protected BigDecimal getAccActOnLoadPcAmount(ProgrammingContractInfo pc) throws BOSException, SQLException{
		if(pc==null) return FDCHelper.ZERO;
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(t.amount) amount from (select sum(bill.famount) amount from T_CON_ContractBill bill ");
		_builder.appendSql(" where bill.fProgrammingContract='"+pc.getId().toString()+"' and bill.fstate in('2SUBMITTED','3AUDITTING')");
		
		_builder.appendSql(" union all select sum(bill.famount) amount from T_CON_ContractWithoutText bill where 1=1");
		if(this.editData.getId()!=null){
			_builder.appendSql(" and bill.fid!='"+this.editData.getId()+"'");
		}
		_builder.appendSql(" and bill.fProgrammingContract='"+pc.getId().toString()+"' and bill.fstate in('2SUBMITTED','3AUDITTING'))t");
		
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			if(rowSet.getBigDecimal("amount")!=null)
				return rowSet.getBigDecimal("amount");
		}
		return FDCHelper.ZERO;
	}
	private void verifyContractProgrammingPara() throws BOSException, SQLException, EASBizException {
		ProgrammingContractInfo pc = (ProgrammingContractInfo) this.editData.getProgrammingContract();
		if(pc != null && this.editData.getId() !=null){
			SelectorItemCollection sick = new SelectorItemCollection();
			sick.add("*");
			pc = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(pc.getId()), sick);
		}
		String param = null;
		BOSUuid idcu = editData.getCurProject().getId();
		ObjectUuidPK pk = new ObjectUuidPK(editData.getOrgUnit().getId());
		param = ContextHelperFactory.getRemoteInstance().getStringParam("FDC228_ISSTRICTCONTROL", pk);
		if (!com.kingdee.util.StringUtils.isEmpty(param)) {
			int i = Integer.parseInt(param);
			boolean isAbort=false;
			if(pc==null){
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("programming.project.id", idcu));
				filter.getFilterItems().add(new FilterItemInfo("programming.isLatest", new Integer(1), CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("programming.state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
				if(editData.getContractType()!=null){
					filter.getFilterItems().add(new FilterItemInfo("contractType.id", editData.getContractType().getId().toString(), CompareType.EQUALS));
				}
				isAbort=ProgrammingContractFactory.getRemoteInstance().exists(filter);
			}
			switch (i) {
			case 0:
				// 严格控制时
				if (pc != null) {
					BigDecimal amount = this.editData.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal onLoadAmount=getAccActOnLoadPcAmount(pc).setScale(2, BigDecimal.ROUND_HALF_UP);
					String detail="规划余额："+balance+"\n占用金额："+onLoadAmount;
					
					if (amount.compareTo(FDCHelper.subtract(balance, onLoadAmount)) > 0) {
						FDCMsgBox.showDetailAndOK(this, "合同签约金额超过关联的合约的（规划余额-占用金额），不允许提交！", detail, 2);
						SysUtil.abort();
					}
				} else if(isAbort){
					FDCMsgBox.showWarning(this, "未关联框架合约，不允许提交！");
					SysUtil.abort();
				}
				break;
			case 1:
				// 提示控制时
				if (pc != null ) {
					BigDecimal amount = this.editData.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal onLoadAmount=getAccActOnLoadPcAmount(pc).setScale(2, BigDecimal.ROUND_HALF_UP);
					String detail="规划余额："+balance+"\n占用金额："+onLoadAmount;
					
					if (amount.compareTo(FDCHelper.subtract(balance, onLoadAmount)) > 0) {
						if(FDCMsgBox.showConfirm3a(this, "合同签约金额超过关联的合约的（规划余额-占用金额），请确认是否提交？",detail)== FDCMsgBox.CANCEL){
							SysUtil.abort();
						}
					}
				} else if(isAbort) {
					FDCMsgBox.showWarning(this, "未关联框架合约，不允许提交！");
					SysUtil.abort();
				}
				break;
			case 2:
				// 不控制时
				if (pc != null) {
					BigDecimal amount = this.editData.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal onLoadAmount=getAccActOnLoadPcAmount(pc).setScale(2, BigDecimal.ROUND_HALF_UP);
					String detail="规划余额："+balance+"\n占用金额："+onLoadAmount;
					
					if (amount.compareTo(FDCHelper.subtract(balance, onLoadAmount)) > 0) {
						if(FDCMsgBox.showConfirm3a(this, "合同签约金额超过关联的合约的（规划余额-占用金额），请确认是否提交？",detail)== FDCMsgBox.CANCEL){
							SysUtil.abort();
						}
					}
				}
				break;
			}
		}
	}
	protected void verifyInputForSubmint()throws Exception {
//		checkAmountForSubmit();
		checkAmount();
		//预算控制
		checkMbgCtrlBalance();

		FDCClientVerifyHelper.verifyEmpty(this, this.prmtContractType);
//		ContractTypeInfo ct=(ContractTypeInfo)this.prmtContractType.getValue();
//		if(ct.isIsAccountView()){
//			if(!PaymentSplitFactory.getRemoteInstance().exists("select * from where conWithoutText.id='"+this.editData.getId().toString()+"' and splitState='3ALLSPLIT'")){
//				FDCMsgBox.showWarning(this,"请先关联成本科目，并且完全拆分！");
//				SysUtil.abort();
//			}
//		}else{
//			verifyContractProgrammingPara();
//		}
		
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtuseDepartment);
		if(this.cbIsBgControl.isSelected()){
			FDCClientVerifyHelper.verifyEmpty(this, this.prmtApplier);
//			FDCClientVerifyHelper.verifyEmpty(this, this.prmtApplierCompany);
//			FDCClientVerifyHelper.verifyEmpty(this, this.prmtApplierOrgUnit);
			FDCClientVerifyHelper.verifyEmpty(this, this.prmtCostedCompany);
			FDCClientVerifyHelper.verifyEmpty(this, this.prmtCostedDept);
			if(this.kdtBgEntry.getRowCount()==0){
				FDCMsgBox.showWarning(this,"费用清单不能为空！");
				SysUtil.abort();
			}
//			if (getUIContext().get("isFromWorkflow") != null &&getUIContext().get("approveIsPass")!=null&& getOprtState().equals(OprtState.EDIT)) {
//				this.editData.put("isFromWorkflow",getUIContext().get("isFromWorkflow").toString());
//				this.editData.put("approveIsPass",getUIContext().get("approveIsPass").toString());
//			}
			IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getRemoteInstance();
			BgCtrlResultCollection coll = iBgControlFacade.getBudget("com.kingdee.eas.fi.cas.app.PaymentBill", new BgCtrlParamCollection(), createTempPaymentBill());
			Map bgItemMap=new HashMap();
			boolean isWarning=true;
			for (int i = 0; i < this.kdtBgEntry.getRowCount(); i++) {
				IRow row = this.kdtBgEntry.getRow(i);
					
				if (row.getCell("expenseType").getValue() == null) {
					FDCMsgBox.showWarning(this,"费用清单预算项目不能为空！");
					this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("expenseType"));
					SysUtil.abort();
				}
				if (row.getCell("bgItem").getValue() == null) {
					FDCMsgBox.showWarning(this,"费用清单预算项目不能为空！");
					this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("bgItem"));
					SysUtil.abort();
				}
				if (row.getCell("requestAmount").getValue() == null) {
					FDCMsgBox.showWarning(this,"费用清单申请金额不能为空！");
					this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("requestAmount"));
					SysUtil.abort();
				}
				if (((BigDecimal)row.getCell("requestAmount").getValue()).compareTo(FDCHelper.ZERO)<=0) {
					FDCMsgBox.showWarning(this,"费用清单申请金额必须大于0！");
					this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("requestAmount"));
					SysUtil.abort();
				}
				BgItemInfo bgItem=(BgItemInfo) row.getCell("bgItem").getValue();
				for (int j = 0; j < coll.size(); j++) {
					if (bgItem.getNumber().equals(coll.get(j).getItemCombinNumber())) {
						if(BgCtrlTypeEnum.NoCtrl.equals(coll.get(j).getCtrlType())){
							break;
						}
						if (getUIContext().get("isFromWorkflow") != null && getUIContext().get("isFromWorkflow").toString().equals("true") &&getUIContext().get("approveIsPass")!=null&&getUIContext().get("approveIsPass").toString().equals("false")&& getOprtState().equals(OprtState.EDIT)) {
							break;
						}
						BigDecimal balanceAmount=FDCHelper.ZERO;
						BigDecimal requestAmount=(BigDecimal)row.getCell("requestAmount").getValue();
						if(coll.get(j).getBalance() != null){
							balanceAmount=coll.get(j).getBalance();
						}
						if(bgItemMap.containsKey(bgItem.getNumber())){
							BigDecimal sumAmount=(BigDecimal) bgItemMap.get(bgItem.getNumber());
							balanceAmount=balanceAmount.subtract(sumAmount);
							bgItemMap.put(bgItem.getNumber(), sumAmount.add(requestAmount));
						}else{
							bgItemMap.put(bgItem.getNumber(), requestAmount);
						}
						BigDecimal balance=balanceAmount.subtract(getAccActOnLoadBgAmount(bgItem.getNumber().toString(),true));
						if(requestAmount.compareTo(balance)>0){
							FDCMsgBox.showWarning(this, bgItem.getName()+"超过预算余额！");
							SysUtil.abort();
						}
						if (isWarning&&(getUIContext().get("isFromWorkflow") == null || getUIContext().get("isFromWorkflow").toString().equals("false"))) {
							BigDecimal endBalance=balanceAmount.subtract(getAccActOnLoadBgAmount(bgItem.getNumber(),false));
							if(requestAmount.compareTo(endBalance)>0){
								if(FDCMsgBox.showConfirm2(this, "你发起的单据已申请（已确认+在途）的累计金额已超过预算；\n本次申请有可能不被通过，请确认是否提交？")!= FDCMsgBox.YES){
									SysUtil.abort();
								}else{
									isWarning=false;
								}
							}
						}
					}
				}
			}
		}
		if(isFeeTraEntry){
			FDCClientVerifyHelper.verifyEmpty(this, this.cbFeeType);
			if(FeeTypeEnum.FEE.equals(this.cbFeeType.getSelectedItem())){
				if(this.kdtFeeEntry.getRowCount()==0){
					FDCMsgBox.showWarning(this,"费用明细不能为空！");
					SysUtil.abort();
				}
				BigDecimal total =TableUtils.getColumnValueSum(this.kdtFeeEntry, "amount");
				if(this.txtamount.getBigDecimalValue().compareTo(total)!=0){
					FDCMsgBox.showWarning(this,"费用明细的合计金额不等于原币金额！");
					SysUtil.abort();
				}
			}else if(FeeTypeEnum.TRA.equals(this.cbFeeType.getSelectedItem())){
				if(this.kdtTraEntry.getRowCount()==0){
					FDCMsgBox.showWarning(this,"费用明细不能为空！");
					SysUtil.abort();
				}
				BigDecimal total =TableUtils.getColumnValueSum(this.kdtTraEntry, "total");
				if(this.txtamount.getBigDecimalValue().compareTo(total)!=0){
					FDCMsgBox.showWarning(this,"费用明细的合计金额不等于原币金额！");
					SysUtil.abort();
				}
			}
		}
		if(this.editData.getCurProject()!=null){
			ContractTypeInfo ct=(ContractTypeInfo) this.prmtContractType.getValue();
			String[] contractType=null;
        	HashMap hmParamIn = new HashMap();
        	hmParamIn.put("PAYPLAN_NOTEXT", null);
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			if(hmAllParam.get("PAYPLAN_NOTEXT")!=null&&!"".equals(hmAllParam.get("PAYPLAN_NOTEXT"))){
				contractType=hmAllParam.get("PAYPLAN_NOTEXT").toString().split(";");
			}
			boolean isCheck=false;
			if(contractType!=null&&contractType.length>0){
				for(int i=0;i<contractType.length;i++){
        			if(ct.getLongNumber().indexOf(contractType[i])>=0){
        				isCheck=true;
        			}
        		}
			}
			if(isCheck){
				Map param=new HashMap();
				param.put("isContract", Boolean.FALSE);
				param.put("contractId", this.editData.getCurProject().getId().toString());
				if (editData.getId() != null) {
					param.put("billId", editData.getId().toString());
				}
				if(editData.getCurProject().getFullOrgUnit()!=null){
					param.put("orgId", editData.getCurProject().getFullOrgUnit().getId().toString());
				}
				if(editData.getCurProject()!=null){
					param.put("curProjectId", editData.getCurProject().getId().toString());
				}
				param.put("bizDate", this.pkbookedDate.getValue());
				param.put("amount", this.txtamount.getBigDecimalValue());
				Map result=ProjectMonthPlanGatherFactory.getRemoteInstance().checkPass(param);
				if(result!=null&&result.get("isPass")!=null){
					if(!((Boolean)result.get("isPass")).booleanValue()&&result.get("warning")!=null){
						FDCMsgBox.showWarning(this,result.get("warning").toString());
						SysUtil.abort();
					}
				}
			}
		}
		super.verifyInputForSubmint();
	}
	private void checkAmount() throws Exception {
    	String projectId = editData.getCurProject().getId().toString();
    	
    	if(editData.isIsCostSplit() && ( FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType)
    			|| FDCContractParamUI.RD_CONTROL.equalsIgnoreCase(controlType))){
    		BigDecimal amiCost  = null;
    		//获取成本控制金额
    		String msg = null;
    		if(!FDCContractParamUI.RD_DYMIC.equalsIgnoreCase(controlCost)){
    			amiCost  = FDCCostRptFacadeFactory.getRemoteInstance().getAimCost(projectId);
    			msg = "目标成本";
    		}else{
    			amiCost  = FDCCostRptFacadeFactory.getRemoteInstance().getDynCost(projectId);
    			msg = "动态成本";
    		}
    		//获取项目合同签约总金额，审核状态的数据
    		String id = editData.getId()!=null? editData.getId().toString():null;
    		BigDecimal signAmount  = ContractFacadeFactory.getRemoteInstance().getProjectAmount(projectId,id); 
    		
    		if(amiCost.compareTo(signAmount.add(editData.getAmount()))<0){
    			//提示  严格控制
    			FDCMsgBox.showWarning(this,"项目合同签约总金额已经超过了"+msg);
	    		if(!FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType)){
	    			SysUtil.abort();
	    		}
    		}
    		
    	}
	}
	   // 提交时，控制预算余额
    private void checkMbgCtrlBalance() throws BOSException, EASBizException{
		//根据参数是否显示合同费用项目
        if (!isShowCharge || editData.getConChargeType() == null) {
			return;
		}

		StringBuffer buffer = new StringBuffer("");
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		editData.setBizDate((Date) this.pkbookedDate.getValue());
		BgCtrlResultCollection bgCtrlResultCollection = iCtrl.getBudget(
				FDCConstants.ContractWithoutText, null, editData);

		if (bgCtrlResultCollection != null) {
			for (int j = 0, count = bgCtrlResultCollection.size(); j < count; j++) {
				BgCtrlResultInfo bgCtrlResultInfo = bgCtrlResultCollection
						.get(j);

				BigDecimal balance = bgCtrlResultInfo.getBalance();
				if (balance != null
						&& balance.compareTo(bgCtrlResultInfo.getReqAmount()) < 0) {
					buffer.append(
							bgCtrlResultInfo.getItemName() + "("
									+ bgCtrlResultInfo.getOrgUnitName() + ")")
							.append(
									EASResource.getString(
											FDCConstants.VoucherResource,
											"BalanceNotEnagh")
											+ "\r\n");
				}
			}
		}

		if (buffer.length() > 0) {
			int result = MsgBox.showConfirm2(this, buffer.toString()
					+ "\r\n"
					+ EASResource.getString(FDCConstants.VoucherResource,
							"isGoOn"));
			if (result == MsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
    }
	protected void verifyInput(ActionEvent e) throws Exception {
//		if(!chkCostsplit.isSelected()){
//			MsgBox.showInfo(this,ContractClientUtils.getRes("NotEntryCost"));
//		}
		
		// 如果启用财务一体化,必须录入期间
		if(this.isIncorporation && cbPeriod.getValue()==null){
			MsgBox.showConfirm2(this,"启用成本月结,必须录入期间");
			SysUtil.abort();
		}		

		checkAmountForSave();
		
		super.verifyInput(e);
		
		checkProjStatus();
	}
	
	private void checkProjStatus() {
//		如果项目状态已关闭，则不能选择是否成本拆分 
		if(editData != null && editData.getCurProject() != null) {
			BOSUuid id = editData.getCurProject().getId();
			
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("projectStatus");
			CurProjectInfo curProjectInfo = null;
			try {
				curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(id), selectors);
				
			
			}catch (Exception ex) {
				handUIException(ex);
			}
			
			if(curProjectInfo.getProjectStatus() != null) {
				String closedState = ProjectStatusInfo.closeID;
				String transferState = ProjectStatusInfo.transferID;
				String projStateId = curProjectInfo.getProjectStatus().getId().toString();
				if(projStateId != null && (projStateId.equals(closedState)||projStateId.equals(transferState))) {
					if(chkCostsplit.isSelected()) {
						MsgBox.showWarning(this, "该合同所在的工程项目已经处于关闭或中转状态，不能进入动态成本，请取消\"进入动态成本\"的选择再保存/提交");
						SysUtil.abort();
					}
				}
			}
		}
	}
	
	protected void checkRef(String id) {
		ContractWithoutTextClientUtils.checkRef(this, id);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    /**
     * output txtamount_dataChanged method
     */
    protected void txtamount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.txtamount_dataChanged(e);
		setAmount();
		setPropPrjAmount("amount",e);
		txtcompletePrjAmt.setValue(txtBcAmount.getBigDecimalValue());
    }

    /**
     * output txtamount_focusLost method
     */
    protected void txtamount_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtNumber_focusLost method
     */
    protected void txtNumber_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    	txtPaymentRequestBillNumber.setText(txtNumber.getText());
    }
    
    /**
     * output prmtcurrency_dataChanged method
     */
    protected void prmtcurrency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
		super.prmtcurrency_dataChanged(e);
		//Object newValue = e.getNewValue();
		if (e==null || e.getNewValue() instanceof CurrencyInfo)
		{
			if(isFirstLoad || (STATUS_VIEW.equals(getOprtState()) || STATUS_FINDVIEW.equals(getOprtState()))) return;
			CurrencyInfo currency = (CurrencyInfo)prmtcurrency.getValue();	 
			if(currency==null){
				//设置汇率的值，在录入界面点新增时可能的情况
				return;
			}
			BOSUuid srcid = currency.getId();
			Date bookedDate = (Date)pkbookedDate.getValue();
			
	    	ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,srcid,company,bookedDate);
	    	
	    	int curPrecision = FDCClientHelper.getPrecOfCurrency(srcid);
	    	BigDecimal exRate = FDCHelper.ONE;
	    	int exPrecision = curPrecision;
	    	
	    	if(exchangeRate!=null){
	    		exRate = exchangeRate.getConvertRate();
	    		exPrecision = exchangeRate.getPrecision();
	    	}
	    	txtexchangeRate.setValue(exRate);
	    	txtexchangeRate.setPrecision(exPrecision);
	    	txtamount.setPrecision(curPrecision);
//			txtexchangeRate.setValue(FDCClientHelper.getLocalExRateBySrcCurcy(this, srcid,company,bookedDate));
	    	
	    	
			setAmount();
			
			if (srcid.toString().equals(baseCurrency.getId().toString())) {
				// 是本位币时将汇率选择框置灰
				txtexchangeRate.setValue(FDCConstants.ONE);
				txtexchangeRate.setRequired(false);
				txtexchangeRate.setEditable(false);
				txtexchangeRate.setEnabled(false);
			}else{
				txtexchangeRate.setRequired(true);
				txtexchangeRate.setEditable(true);
				txtexchangeRate.setEnabled(true);
			}

		}
		getBgAmount();
    }

    /**
     * output txtpaymentProportion_dataChanged method
     */
    protected void txtpaymentProportion_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.txtpaymentProportion_dataChanged(e);
    	setPropPrjAmount("paymentProp",e);
    }

    /**
     * output txtcompletePrjAmt_dataChanged method
     */
    protected void txtcompletePrjAmt_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.txtcompletePrjAmt_dataChanged(e);
    	setPropPrjAmount("completePrjAmt",e);
    }

    /**
     * output txtexchangeRate_focusLost method
     */
    protected void txtexchangeRate_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    	/*Delete by zhiyuan_tang 2010/07/20
    	 * 为解决汇率光标离开，发票金额、本位币金额、本期完工工程量计算错误，添加汇率的dataChange事件。
    	 * 原来focusLost事件的处理也一并移到dataChange事件中
    	*/
//    	super.txtexchangeRate_focusLost(e);
//		setAmount();
    }
    
    //Add by zhiyuan_tang 2010/07/20 
	/**
	 * output txtexchangeRate_focusLost method
	 * @author zhiyuan_tang 2010/07/20 <p>
	 * 解决汇率光标离开，发票金额、本位币金额、本期完工工程量计算错误，添加汇率的dataChange事件<p>
	 */
    protected void txtexchangeRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.txtexchangeRate_dataChanged(e);
		setAmount();
		setPropPrjAmount("amount",e);
		txtcompletePrjAmt.setValue(txtBcAmount.getBigDecimalValue());
    }
    
	/**
	 * Description: 设置本位币金额,大小写等
	 * 
	 * @author sxhong Date 2006-9-6
	 */
	private void setAmount()
	{
		
		BigDecimal amount = (BigDecimal) txtamount.getNumberValue();
		Object exchangeRate = txtexchangeRate.getNumberValue();
		if(amount==null){
			amount = FDCConstants.ZERO;
		}
		if (amount != null)
		{
			Object value = prmtcurrency.getValue();
			boolean isBaseCurrency = false;
			
			if (value instanceof CurrencyInfo)
			{				
				//本位币处理
				CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
				CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
				BOSUuid srcid = ((CurrencyInfo) value).getId();

				if(baseCurrency!=null){
					if(srcid.equals(baseCurrency.getId())){
						isBaseCurrency = true;
						/*if (exchangeRate instanceof BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1)
						{
							MsgBox.showWarning(this,"你选择的是本位币，但是汇率不等于1");
						}*/
						txtBcAmount.setValue(amount);
						txtexchangeRate.setValue(new BigDecimal("1"));
						txtexchangeRate.setEditable(false);
						//return;
					}else{
						this.txtexchangeRate.setEditable(true);
						txtexchangeRate.setEnabled(true);
					}
				}

			}
			
			BigDecimal localamount = FDCConstants.ZERO;
			if (!isBaseCurrency && exchangeRate instanceof BigDecimal)
			{
				localamount = amount.multiply((BigDecimal)exchangeRate);				
				txtBcAmount.setValue(localamount);
			}else{
				localamount = amount;
			}
			//发票金额
//			txtInvoiceAmt.setValue(txtBcAmount.getBigDecimalValue());
//			txtAllInvoiceAmt.setValue(txtBcAmount.getBigDecimalValue());
			//本期已完工工程量
			txtcompletePrjAmt.setValue(txtBcAmount.getBigDecimalValue());
			//大写金额为本位币金额
			if(localamount.compareTo(FDCConstants.ZERO)!=0){
				localamount = localamount.setScale(2,BigDecimal.ROUND_HALF_UP);
				String cap = FDCClientHelper.getChineseFormat(localamount,false);
				//FDCHelper.transCap((CurrencyInfo) value, amount);
				txtcapitalAmount.setText(cap);
			}else{
				txtcapitalAmount.setText(null);
			}


		}
	}	
    /**
     * 设置付款比例，已完工工程量与原币金额之间的关系：
     * 				已完工工程量金额＝原币金额÷付款比例
     * @author sxhong  		Date 2007-3-12
     */
    private void setPropPrjAmount(String cause,DataChangeEvent e){
    	if(isFirstLoad||(!txtPaymentProportion.isRequired())) return;
//    	if(PayReqUtils.isContractWithoutText(editData.getcon))
    	BigDecimal amount=txtBcAmount.getBigDecimalValue();
    	BigDecimal paymentProp=txtPaymentProportion.getBigDecimalValue();
    	BigDecimal completePrj=txtcompletePrjAmt.getBigDecimalValue();
    	
    	if(amount==null) amount=FDCHelper.ZERO;
    	if(paymentProp==null) paymentProp=FDCHelper.ZERO;
    	if(completePrj==null) completePrj=FDCHelper.ZERO;
    	
    	if(cause.equals("amount")){
    		if(paymentProp.compareTo(FDCHelper.ZERO)==0){
    			if(completePrj.compareTo(FDCHelper.ZERO)==0){
    				return;
    			}else{
    				txtPaymentProportion.setValue(amount.setScale(4).divide(completePrj, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
    			}
    		}else{
    			txtcompletePrjAmt.setValue(amount.divide(paymentProp, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
    		}
    	}else if(cause.equals("completePrjAmt")){
    		/*
    		if(completePrj.subtract(amount).compareTo(new BigDecimal("0.01"))<0){
				MsgBox.showWarning(this, "付款比例必须大于0,请重新输入的已完工工程量，保证付款比例＝原币金额/已完工工程量>0 即0.01%");
				if(e.getOldValue() instanceof BigDecimal){
					if(((BigDecimal)e.getOldValue()).subtract(amount).compareTo(new BigDecimal("0.01"))>=0){
						txtcompletePrjAmt.setValue(e.getOldValue());
					}
						
				}
//				SysUtil.abort();
				return;
			}else{
				txtpaymentProportion.setValue(amount.divide(completePrj, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}*/
//    		if(completePrj.compareTo(FDCHelper.ZERO)==0){
//    			MsgBox.showWarning(this, "已完工工程量不能为0");
//    			SysUtil.abort();
//    		}
    		if(completePrj.compareTo(FDCHelper.ZERO)==0){
    			return;
    		}
    		txtPaymentProportion.setValue(amount.setScale(4).divide(completePrj, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
    	}else{
			if(paymentProp.compareTo(FDCHelper.ZERO)==0){
				return;
			}else{
    			txtcompletePrjAmt.setValue(amount.setScale(4).divide(paymentProp, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
    	}
    }
    
	
    /**
     * 覆盖抽象类处理编码规则的行为,统一在FDCBillEditUI.handCodingRule方法中处理
     */
    protected void setAutoNumberByOrg(String orgType) {
    	
    }
    protected void prmtsettlementType_dataChanged(DataChangeEvent e)
    		throws Exception {
//    	// TODO Auto-generated method stub
//    	super.prmtsettlementType_dataChanged(e);
		Object objNew=e.getNewValue();
//		if(objNew==null) return;
//		if(objNew instanceof SettlementTypeInfo
//				&&((SettlementTypeInfo)objNew).getId().toString().equals("e09a62cd-00fd-1000-e000-0b32c0a8100dE96B2B8E")){
//			//结算方式为付款则银行及账号为非必录
//			txtBank.setRequired(false);
//			txtBank.repaint();
//			txtBankAcct.setRequired(false);
//			txtBankAcct.repaint();
//		}else{
////			txtBank.setRequired(true);
////			txtBank.repaint();
////			txtBankAcct.setRequired(true);
////			txtBankAcct.repaint();
//		}
    	
    	if(objNew instanceof SettlementTypeInfo && ((SettlementTypeInfo) objNew).getName().equals("现金")){
    		txtBank.setRequired(false);
    		txtBankAcct.setRequired(false);
    		txtBank.setEnabled(false);
			txtBankAcct.setEnabled(false);
			txtBank.setText(null);
			txtBankAcct.setText(null);
		}else{
			txtBank.setRequired(true);
			txtBankAcct.setRequired(true);
			txtBank.setEnabled(true);
			txtBankAcct.setEnabled(true);
		}
    	txtBankAcct.repaint();
		txtBank.repaint();
    }
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAddNew_actionPerformed(e);
		
		//handleCodingRule();
		txtPaymentRequestBillNumber.setText("");
		
    	if(prmtcurrency.getValue()!=null){
    		CurrencyInfo cur = (CurrencyInfo)prmtcurrency.getValue();
    		if(!this.baseCurrency.getId().toString().equals(cur.getId().toString())){
    			//txtexchangeRate.setEnabled(false);
    			prmtcurrency.setValue(baseCurrency);
    		}
    	}
    	this.txtMoneyDesc.setText(null);
    	this.chkUrgency.setEnabled(true);
//		chkNeedPaid.setEnabled(true);
		if(chkNeedPaid.isSelected()){
			txtNoPaidReason.setEnabled(true);
		}else{
			txtNoPaidReason.setEnabled(false);	
			txtNoPaidReason.setText(null);
		}
				
    	this.chkCostsplit.setEnabled(true);
//    	prmtuseDepartment.setValue(null);
    	txtamount.setValue(FDCHelper.ZERO);
    	txtBcAmount.setValue(FDCHelper.ZERO);
    	
    	this.txtPaymentProportion.setEditable(false);
		this.txtcompletePrjAmt.setEditable(false);
		if(isNotEnterCAS){
			this.chkNeedPaid.setEnabled(false);
			this.chkNeedPaid.setSelected(true);
		}
	}
    
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception {

    	boolean isNeedPaid = chkNeedPaid.isSelected();
    	boolean chkUrgencyCopy = chkUrgency.isSelected();
    	String txtNoPaidReasonCopy = txtNoPaidReason.getText();
    	Object prmtAccountCopy = prmtAccount.getValue();
		String obj = txtattachment.getText();
		
    	super.actionCopy_actionPerformed(e);
    	//handleCodingRule();
    	txtPaymentRequestBillNumber.setText("");
    	prmtrealSupplier.setEnabled(true);
    	
    	if(prmtcurrency.getValue()!=null){
    		CurrencyInfo cur = (CurrencyInfo)prmtcurrency.getValue();
    		if(this.baseCurrency.getId().toString().equals(cur.getId().toString())){
    			txtexchangeRate.setEnabled(false);
    		}
    	}
    	
    	this.txtattachment.setText(obj);
    	this.chkUrgency.setEnabled(true);
    	this.chkCostsplit.setEnabled(true);
//		chkNeedPaid.setEnabled(true);
//		chkNeedPaid.setSelected(isNeedPaid);
		if(chkNeedPaid.isSelected()){
			txtNoPaidReason.setEnabled(true);
			txtNoPaidReason.setText(txtNoPaidReasonCopy);
			prmtAccount.setEnabled(true);
			prmtAccount.setValue(prmtAccountCopy);
		}else{
			txtNoPaidReason.setEnabled(false);	
			prmtAccount.setEnabled(false);
			txtNoPaidReason.setText(null);
			prmtAccount.setValue(null);
		}
		chkUrgency.setSelected(chkUrgencyCopy);
		//如果业务日期在当前期间之前的，设置业务日期为当前期间第一天
		Date bookedDate  = (Date)pkbookedDate.getValue();
		if(bookedDate!=null && canBookedPeriod!=null && bookedDate.before(canBookedPeriod.getBeginDate()) ){
			this.editData.setBookedDate(canBookedPeriod.getBeginDate());
			pkbookedDate.setValue(canBookedPeriod.getBeginDate());
		}
		editData.setSourceType(SourceTypeEnum.ADDNEW);
		//new update by renliang
		if(isNotEnterCAS){
//			this.chkNeedPaid.setEnabled(false);
//			this.chkNeedPaid.setSelected(true);
		}
		setBgEditState();
    }
    
    //修改 款项说明和附件 没有提示保存
    boolean modify;
    public boolean isModify(){
    	if (STATUS_VIEW.equals(getOprtState())) {
			return false;
		}
    	if(modify)return true;
    	return super.isModify();
    }
    
    private void addListener(){
    	txtMoneyDesc.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyTyped(KeyEvent e) {
				modify=true;
			}
    		
    	});
    	
    	txtattachment.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyTyped(KeyEvent e) {
				modify=true;
			}
    		
    	});
    }
    
	private void setPayerAcctEvi(CurrencyInfo currencyInfo) throws EASBizException, BOSException {
		String currencyId = currencyInfo.getId().toString();
		String companyId = company.getId().toString();
		String cuId = company.getCU().getId().toString();
		
		prmtAccount.getQueryAgent().resetRuntimeEntityView();
		
		// 根据付款方式取得过滤条件
		EntityViewInfo treeevi = CasRecPayHandler.getAccountViewEvi(cuId,
				companyId, currencyId, false);
		
		AccountPromptBox opseelect = new AccountPromptBox(this, company,
				treeevi.getFilter(), false, true);
		prmtAccount.setSelector(opseelect);
		EntityViewInfo evi = CasRecPayHandler.getAccountViewEvi(cuId,
				companyId, currencyId, true);
		
		prmtAccount.setEntityViewInfo(evi);
	}


    /**
     * output prmtAccount_willCommit method
     */
    protected void prmtAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
		CurrencyInfo currencyInfo = (CurrencyInfo) prmtcurrency.getValue();
		if (currencyInfo == null) {
			e.setCanceled(true);
			return;
		}
		setPayerAcctEvi(currencyInfo);
    }

    /**
     * output prmtAccount_willShow method
     */
    protected void prmtAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
		CurrencyInfo currencyInfo = (CurrencyInfo) prmtcurrency.getValue();
		if (currencyInfo == null) {
			e.setCanceled(true);
			return;
		}
		setPayerAcctEvi(currencyInfo);
    }

    /**
     * output prmtAccount_dataChanged method
     */
    protected void prmtAccount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	
    }
    
    protected boolean isUseMainMenuAsTitle(){
    	return false;
    }
    
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
			MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
			return;
        }          
        ContractWithoutTextPrintDataProvider data = new ContractWithoutTextPrintDataProvider(editData.getString("id"),getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
			MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
			return;
        }
        ContractWithoutTextPrintDataProvider data = new ContractWithoutTextPrintDataProvider(editData.getString("id"),getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	//获得无文本合同套打对应的目录
	protected String getTDFileName() {
    	return "/bim/fdc/contract/ContractWithoutText";
	}
	//对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractWithoutTextPrintQuery");
	}
    
	/**
	 * RPC改造，任何一次事件只有一次RPC
	 */
	
	public boolean isPrepareInit() {
    	return true;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
    }
	
	public RequestContext prepareActionSubmit(IItemAction itemAction)
	throws Exception {
		RequestContext request = super.prepareActionSubmit(itemAction);
		
		return request;
	}
	
    /**
     * EditUI提供的初始化handler参数方法
     */
    protected void PrepareHandlerParam(RequestContext request)
    {
        super.PrepareHandlerParam(request);
        

    }
	protected void initWorkButton() {
		super.initWorkButton();
		actionViewBgBalance.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sequencecheck"));
	
	}
	//查看预算余额
	public void actionViewBgBalance_actionPerformed(ActionEvent e) throws Exception {
		storeFields();
		//业务日期默认未设置，如果预算控制时以业务日期控制，会导致查看预算时查不到，手动设置一下
		editData.setBizDate((Date) this.pkbookedDate.getValue());
		ContractWithoutTextInfo info = editData;
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		BgCtrlResultCollection coll = iCtrl.getBudget(
				FDCConstants.ContractWithoutText, null, info);

		UIContext uiContext = new UIContext(this);
		uiContext.put(BgHelper.BGBALANCE, coll);

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(BgBalanceViewUI.class.getName(), uiContext, null,
						STATUS_VIEW);
		uiWindow.show();
		info=null;
	}

	 public void fillAttachmentList(){
	    	this.cmbAttachment.removeAllItems();
			String boId = null;
			if(this.editData.getId() == null){
				return;
			}else{
				boId = this.editData.getId().toString();
			}
			
			if(boId != null){
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("id"));
				sic.add(new SelectorItemInfo("attachment.id"));
				sic.add(new SelectorItemInfo("attachment.name"));
				
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("boID",boId));
				
				EntityViewInfo evi = new EntityViewInfo();
				evi.setFilter(filter);
				evi.setSelector(sic);
				BoAttchAssoCollection cols = null;
				 try {
					cols =BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
				} catch (BOSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(cols != null && cols.size()>0){
					for(Iterator it = cols.iterator();it.hasNext();){
						AttachmentInfo attachment = ((BoAttchAssoInfo)it.next()).getAttachment();
						this.cmbAttachment.addItem(attachment);
					}
					this.isHasAttachment = true;
				}else{
					this.isHasAttachment =false;
				}
			}
			this.btnViewAttachment.setEnabled(this.isHasAttachment);
//			this.viewAttachment.setEnabled(isHasAttachment);
	    }
	 
	 public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionAttachment_actionPerformed(e);
		fillAttachmnetTable();
	}
	 
	 public void actionViewAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionViewAttachment_actionPerformed(e);
		String attachId = null;
    	if(this.cmbAttachment.getSelectedItem() != null && this.cmbAttachment.getSelectedItem() instanceof AttachmentInfo){
    		attachId = ((AttachmentInfo)this.cmbAttachment.getSelectedItem()).getId().toString();
    		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    		acm.viewAttachment(attachId);
    	}
	}
	 
	protected void lockContainer(Container container) {
		 if(lblAttachmentContainer.getName().equals(container.getName())){
	        	return;
	        }
	        else{
	        	super.lockContainer(container);
	        }
	}
	
	/**
	 * 需求来源：R100806-236合同录入、变更、结算单据界面增加审批按钮
	 * <P>
	 * 为实现此BT需求，特重写以下方法
	 * 
	 * @author owen_wen 2011-03-08
	 */
	protected void setAuditButtonStatus(String oprtType) {
		if (STATUS_VIEW.equals(oprtType) || STATUS_EDIT.equals(oprtType)) {
			actionAudit.setVisible(true);
			actionUnAudit.setVisible(true);
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(true);

			FDCBillInfo bill = (FDCBillInfo) editData;
			if (editData != null) {
				if (FDCBillStateEnum.AUDITTED.equals(bill.getState())) {
					actionUnAudit.setVisible(true);
					actionUnAudit.setEnabled(true);
					actionAudit.setVisible(false);
					actionAudit.setEnabled(false);
				} else {
					actionUnAudit.setVisible(false);
					actionUnAudit.setEnabled(false);
					actionAudit.setVisible(true);
					actionAudit.setEnabled(true);
				}
			}
		} else {
			actionAudit.setVisible(false);
			actionUnAudit.setVisible(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		}
	}

	/**
	 * 需求来源：R100806-236合同录入、变更、结算单据界面增加审批按钮
	 * <P>
	 * 为实现此BT需求，特重写以下方法
	 * 
	 * @author owen_wen 2011-03-08
	 */
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception {

		isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);

		if (isSameUserForUnAudit && editData.getAuditor() != null) {

			if (!SysContext.getSysContext().getCurrentUserInfo().getId().equals(editData.getAuditor().getId())) {
				try {
					throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
				} catch (FDCBasedataException e) {
					handUIExceptionAndAbort(e);
				}
			}
		}
		// 检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		if (editData == null || editData.getState() == null || !editData.getState().equals(state)) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
	
	/**
	 * 描述：业务日期发生变化后需要传递到UIContext，通过UIContext在传递到计划项目F7
	 */
	protected void pkbookedDate_dataChanged(DataChangeEvent e) throws Exception {
		this.getUIContext().put("bookedDate", this.pkbookedDate.getSqlDate());
		Calendar cal = Calendar.getInstance();
		cal.setTime((Date) pkbookedDate.getValue());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		prmtPlanProject.setDisplayFormat("$payMattersName$ " + year + "年" + month + "月 无合同计划");
		this.prmtPlanProject.setData(null);
		
//		getBgAmount();
		getPayPlanValue();
	}

	/**
	 * 描述：初始化计划项目F7
	 * <p>
	 * added by cp 2011/03-04
	 */
	public void initFDCDepConPayPlanF7() throws Exception {
		if (chkCostsplit.isSelected() && !"不控制".equals(CONTROLNOCONTRACT)) {
//			prmtPlanProject.setRequired(true);
		}
		prmtPlanProject
				.setQueryInfo("com.kingdee.eas.fdc.finance.app.FDCDepConPayPlanNoContractF7Query");
		prmtPlanProject.setEditFormat("$payMattersName$");
		FDCDepConPayPlanSelector f7Selector = new FDCDepConPayPlanSelector(this);
		this.prmtPlanProject.setSelector(f7Selector);
	}

	/**
	 * 描述：查看预算
	 * <p>
	 * added by cp 2011/03-04
	 */
	public void actionViewBudget_actionPerformed(ActionEvent e)
			throws Exception {
		FDCDepConPayPlanNoContractInfo plan = (FDCDepConPayPlanNoContractInfo) prmtPlanProject
		.getValue();
		if (plan == null) {
			MsgBox.showWarning("无预算，暂不能查看");
			SysUtil.abort();
		}
		String payMatters = plan.getPayMatters();
		String planNum = plan.getHead().getNumber();

		Map uiContext = getUIContext();
		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();
		Map rt = getLocalBudget(firstDay, lastDay, projectId, planNum,
				payMatters);
		String curPrj = (String) rt.get("curProject");
		uiContext.put("curProject", curPrj);
		String curDept = (String) rt.get("curDept");
		uiContext.put("curDept", curDept);
		String planMonth = (String) rt.get("planMonth");
		uiContext.put("planMonth", planMonth);
		BigDecimal localBudget = (BigDecimal) rt.get("localBudget");
		uiContext.put("localBudget", localBudget);
		BigDecimal actPaied = getActPaied(firstDay, lastDay, projectId, planNum, payMatters);
		uiContext.put("actPaied", actPaied);
		BigDecimal floatFund = getFloatFund(firstDay, lastDay, projectId, planNum, payMatters);
		uiContext.put("floatFund", floatFund);
		BigDecimal bgBalance = localBudget.subtract(floatFund).subtract(actPaied);
		uiContext.put("bgBalance", bgBalance);
		BudgetViewUtils.showModelUI(uiContext);
	}

	/*
	 * 获取当月可用余额
	 */
	protected BigDecimal getBgBalance() throws BOSException, SQLException {
		FDCDepConPayPlanNoContractInfo plan = (FDCDepConPayPlanNoContractInfo) prmtPlanProject
				.getValue();
		if (plan != null) {
		String payMatters = plan.getPayMatters();
		String planNum = plan.getHead().getNumber();

		Map uiContext = getUIContext();
		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();
		Map rt = getLocalBudget(firstDay, lastDay, projectId, planNum,
				payMatters);
		String curDept = (String) rt.get("curDept");
		uiContext.put("curDept", curDept);
		String planMonth = (String) rt.get("planMonth");
		uiContext.put("planMonth", planMonth);
		BigDecimal localBudget = (BigDecimal) rt.get("localBudget");
		uiContext.put("localBudget", localBudget);
		BigDecimal actPaied = getActPaied(firstDay, lastDay, projectId, planNum, payMatters);
		uiContext.put("actPaied", actPaied);
		BigDecimal floatFund = getFloatFund(firstDay, lastDay, projectId, planNum, payMatters);
		uiContext.put("floatFund", floatFund);
		
		return localBudget.subtract(floatFund).subtract(actPaied);
		}else{
			return FDCHelper.ZERO;
		}
	}

	/**
	 * 计算已付
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @param planNum
	 * @param payMatters
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected BigDecimal getActPaied(Date firstDay, Date lastDay,
			String projectId, String planNum, String payMatters)
			throws BOSException, SQLException {
		BigDecimal actPaied = FDCHelper.ZERO;
		FDCSQLBuilder builderActPaied = new FDCSQLBuilder();
		builderActPaied
				.appendSql(" select sum(pay.FLocalAmount) as actPaied from t_cas_paymentbill as pay ");
		builderActPaied
				.appendSql(" left join T_CON_ContractWithoutText as con on con.FID = pay.FContractBillID ");
		builderActPaied
				.appendSql(" left join T_FNC_FDCDepConPayPlanNoCon as np on np.FID = con.FFdcDepConPlanID ");
		builderActPaied
				.appendSql(" left join T_FNC_FDCDepConPayPlanBill as pb on pb.FID = np.FHeadID ");
		builderActPaied.appendSql(" where pay.FBillStatus = 15 ");
		builderActPaied.appendSql(" and pay.FBizDate >= ");
		builderActPaied.appendParam(firstDay);
		builderActPaied.appendSql(" and pay.FBizDate <= ");
		builderActPaied.appendParam(lastDay);
		builderActPaied.appendSql(" and pay.FCurProjectID = ");
		builderActPaied.appendParam(projectId);
		builderActPaied.appendSql(" and pb.FNumber = ");
		builderActPaied.appendParam(planNum);
		builderActPaied.appendSql(" and np.FPayMatters = ");
		builderActPaied.appendParam(payMatters);
		// 查看状态查以前的
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
	protected BigDecimal getFloatFund(Date firstDay, Date lastDay,
			String projectId, String planNum, String payMatters) throws BOSException, SQLException {
		
		BigDecimal floatFund = FDCHelper.ZERO;
		FDCSQLBuilder builderFloatFund = new FDCSQLBuilder();
		builderFloatFund
				.appendSql(" select sum(con.FAmount) as floatFund from T_CON_ContractWithoutText as con ");
		builderFloatFund
				.appendSql(" left join T_FNC_FDCDepConPayPlanNoCon as np on np.FID = con.FFdcDepConPlanID ");
		builderFloatFund
				.appendSql(" left join T_FNC_FDCDepConPayPlanBill as pb on pb.FID = np.FHeadID ");
		builderFloatFund
				.appendSql(" where (con.FState = '2SUBMITTED' or con.FState = '3AUDITTING') ");
		builderFloatFund.appendSql(" and con.FBookedDate >= ");
		builderFloatFund.appendParam(firstDay);
		builderFloatFund.appendSql(" and con.FBookedDate <= ");
		builderFloatFund.appendParam(lastDay);
		builderFloatFund.appendSql(" and con.FCurProjectID = ");
		builderFloatFund.appendParam(projectId);
		builderFloatFund.appendSql(" and pb.FNumber =  ");
		builderFloatFund.appendParam(planNum);
		builderFloatFund.appendSql(" and np.FPayMatters = ");
		builderFloatFund.appendParam(payMatters);
		// 查看状态查以前的
		if (STATUS_VIEW.equals(getOprtState())) {
			builderFloatFund.appendSql(" and con.FLastUpdateTime <= ");
			builderFloatFund.appendSql("{ts '");
			builderFloatFund.appendSql(FMConstants.FORMAT_TIME.format(editData
					.getLastUpdateTime()));
			builderFloatFund.appendSql("' }");
			// 此处精确到时分秒，所以不能用下句
			// builderFloatFund.appendParam(editData.getLastUpdateTime());
		}
		if (editData.getId() != null) {
			builderFloatFund.appendSql(" and con.FID != ");
			builderFloatFund.appendParam(editData.getId().toString());
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
	protected Map getLocalBudget(Date firstDay, Date lastDay, String projectId,
			String planNum, String payMatters) throws BOSException,
			SQLException {
		Map rt = new HashMap();
		FDCDepConPayPlanNoContractInfo plan = (FDCDepConPayPlanNoContractInfo) prmtPlanProject
				.getValue();
		if (plan == null) {
			CurProjectInfo prj = (CurProjectInfo) prmtcurProject.getValue();
			String prjName = "";
			if (prj != null) {
				prjName = prj.getName();
			}
			rt.put("curProject", prjName);
			AdminOrgUnitInfo dpt = (AdminOrgUnitInfo) prmtuseDepartment
					.getValue();
			String curDept = "";
			if (dpt != null) {
				curDept = dpt.getName();
			}
			rt.put("curDept", curDept);
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM");
			String planMonth = ft.format(editData.getBookedDate());
			rt.put("planMonth", planMonth);
			rt.put("localBudget", FDCHelper.ZERO);
			return rt;
		}
		
		BigDecimal localBudget = FDCHelper.ZERO;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select prj.FName_l2 as prjName,admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
		builder.appendSql("from T_FNC_FDCDepConPayPlanNoCon as con ");
		builder
				.appendSql("left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FHeadID ");
		builder
				.appendSql("left join T_FNC_FDCDepConPayPlanNCEntry as entry on entry.FParentNC = con.FID ");
		builder
				.appendSql("left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
		builder.appendSql(" left join T_FDC_CurProject as prj on prj.FID = con.FProjectID ");
		builder
				.appendSql("where ");
		builder.appendSql(" entry.FMonth >= ");
		builder.appendParam(firstDay);
		builder.appendSql(" and entry.FMonth <= ");
		builder.appendParam(lastDay);
		// builder.appendSql(" and con.FProjectID = ");
		// builder.appendParam(projectId);
		builder.appendSql(" and con.FID = ");
		builder.appendParam(plan.getId().toString());
//		builder.appendSql(" and head.FNumber = ");
//		builder.appendParam(planNum);
		builder.appendSql(" order by head.FYear desc,head.FMonth DESC");
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			String curPrj = rowSet.getString("prjName");
			rt.put("curProject", curPrj);
			String curDept = rowSet.getString("curDept");
			rt.put("curDept", curDept);
			String planMonth = rowSet.getString("planMonth");
			rt.put("planMonth", planMonth);
			if (rowSet.getString("localBudget") != null) {
				localBudget = rowSet.getBigDecimal("localBudget");
				rt.put("localBudget", localBudget);
			}
		}
		return rt;
	}
	
	protected void chkCostsplit_actionPerformed(ActionEvent e) throws Exception {
		if (chkCostsplit.isSelected()) {
//			prmtPlanProject.setRequired(true);
		} else {
			prmtPlanProject.setRequired(false);
		}
	}
	
	public void actionViewProgramContract_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.editData.getProgrammingContract() == null) {
			MsgBox.showInfo(this, "该合同没有关联框架合约!");
			this.abort();
		}

		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ContractWithoutTextInfo  contractWithoutTextInfo  = (ContractWithoutTextInfo ) editData;
		ProgrammingContractInfo proContInfo = getProContInfo(contractWithoutTextInfo.getProgrammingContract().getId().toString());
		contractWithoutTextInfo.setProgrammingContract(proContInfo);
		uiContext.put("programmingContract", proContInfo);
		uiContext.put("programmingContractTemp", "programmingContractTemp");
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	
	private ProgrammingContractInfo getProContInfo(String id) throws EASBizException, BOSException {
		SelectorItemCollection selItemCol = new SelectorItemCollection();
		selItemCol.add("*");
		selItemCol.add("costEntries.*");
		selItemCol.add("costEntries.costAccount.*");
		selItemCol.add("costEntries.costAccount.curProject.*");
		selItemCol.add("economyEntries.*");
		selItemCol.add("economyEntries.paymentType.*");
		ProgrammingContractInfo pcInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(id), selItemCol);
		return pcInfo;
	}
	
	public void actionProgram_actionPerformed(ActionEvent e) throws Exception {
		showProgramContract();
		getPayPlanValue();
	}
	
	private void showProgramContract() throws Exception {
		
		IUIWindow uiWindow = null;
		UIContext uiContext = new UIContext(this);
		ContractWithoutTextInfo contractWithoutTextInfo = editData;
		if(this.prmtContractType.getValue()==null){
			FDCMsgBox.showWarning(this,"请先选择合同类型！");
			return;
		}
		contractWithoutTextInfo.setContractType((ContractTypeInfo) this.prmtContractType.getValue());
		uiContext.put("contractWithoutTextInfo", contractWithoutTextInfo);
		//是否全期合同
		uiContext.put("isWholeAgeStage", new Boolean(curProject.isIsWholeAgeStage()));
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillLinkProgContEditUI.class.getName(), uiContext, null,OprtState.EDIT);
		uiWindow.show();
		if (contractWithoutTextInfo.getProgrammingContract() != null) {
			this.txtPCName.setText(contractWithoutTextInfo.getProgrammingContract().getName());
		} else {
			this.txtPCName.setText(null);
		}
	}
	protected void prmtContractType_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        ContractTypeInfo ct=(ContractTypeInfo) this.prmtContractType.getValue();
        setProgAndAccountState(ct);
        
        Set id=new HashSet();
		if(ct!=null){
			if(this.editData.getProgrammingContract()!=null&&this.editData.getProgrammingContract().getContractType()!=null
					&&!this.editData.getProgrammingContract().getContractType().getId().equals(ct.getId())){
				this.editData.setProgrammingContract(null);
				this.txtPCName.setText(null);
				getPayPlanValue();
			}
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("entry.payContentType.*");
			sel.add("*");
			ct=ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new ObjectUuidPK(ct.getId()),sel);
			this.prmtPayContentType.setEnabled(true);
			
			for(int i=0;i<ct.getEntry().size();i++){
				if(ct.getEntry().get(i).getPayContentType()!=null){
					id.add(ct.getEntry().get(i).getPayContentType().getId().toString());
				}
			}
			if(ct.getEntry().size()==1&&ct.getEntry().get(0).getPayContentType()!=null){
				this.prmtPayContentType.setValue(ct.getEntry().get(0).getPayContentType());
			}
			
			chkCostsplit.setSelected(ct.isIsCost());
			
		}else{
			this.prmtPayContentType.setEnabled(false);
		}
		
		if(this.prmtPayContentType.getValue()!=null&&!id.contains(((PayContentTypeInfo)this.prmtPayContentType.getValue()).getId().toString())){
			this.prmtPayContentType.setValue(null);
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		if(id.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.INCLUDE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id",null));
		}
		view.setFilter(filter);
		this.prmtPayContentType.setEntityViewInfo(view);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("conWithoutText.id", editData.getId()));
//		view.setFilter(filter);
//		view.getSelector().add("id");
//		CoreBillBaseCollection coll = PaymentSplitFactory.getRemoteInstance()
//				.getCoreBillBaseCollection(view);
//		Iterator iter = coll.iterator();
//		if (iter.hasNext()) {
//			MsgBox.showWarning(this, "请先删除对应无合同拆分！");
//			SysUtil.abort();
//		}
		super.actionRemove_actionPerformed(e);
	}
	protected void tblAttachement_tableClicked(KDTMouseEvent e)throws Exception {
		if(e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2){
			IRow row  =  tblAttachement.getRow(e.getRowIndex());
			getFileGetter();
			Object selectObj= row.getCell("id").getValue();
			if(selectObj!=null){
				String attachId=selectObj.toString();
				fileGetter.viewAttachment(attachId);
			}
		}
	}
	private  FileGetter fileGetter;
	private  FileGetter getFileGetter() throws Exception {
        if (fileGetter == null)
            fileGetter = new FileGetter((IAttachment) AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
        return fileGetter;
    }
	public void fillAttachmnetTable() throws EASBizException, BOSException {
		this.tblAttachement.removeRows();
		String boId = null;
		if (this.editData.getId() == null) {
			return;
		} else {
			boId = this.editData.getId().toString();
		}
		if (boId != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));
			sic.add(new SelectorItemInfo("attachment.createTime"));
			sic.add(new SelectorItemInfo("attachment.attachID"));
			sic.add(new SelectorItemInfo("attachment.beizhu"));
			sic.add(new SelectorItemInfo("assoType"));
			sic.add(new SelectorItemInfo("boID"));

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", boId));
			EntityViewInfo evi = new EntityViewInfo();
			evi.getSorter().add(new SorterItemInfo("boID"));
			evi.getSorter().add(new SorterItemInfo("attachment.name"));
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			try {
				cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			boolean flag = false;
			if (cols != null && cols.size() > 0) {
				for (Iterator it = cols.iterator(); it.hasNext();) {
					BoAttchAssoInfo boaInfo = (BoAttchAssoInfo)it.next();
					AttachmentInfo attachment = boaInfo.getAttachment();
					IRow row = tblAttachement.addRow();
					row.getCell("id").setValue(attachment.getId().toString());
					row.getCell("seq").setValue(attachment.getAttachID());
					row.getCell("name").setValue(attachment.getName());
					row.getCell("date").setValue(attachment.getCreateTime());
					row.getCell("type").setValue(boaInfo.getAssoType());
				}
			}
		}
	}
	protected void cbFeeType_itemStateChanged(ItemEvent e) throws Exception {
		this.kDTabbedPane1.removeAll();
		this.kDTabbedPane1.add(this.contBgEntry,"费用清单");
		if(!"供应商".equals(this.comboPayeeType.getSelectedItem().toString())){
			if(!isFeeTraEntry){
				this.cbFeeType.setRequired(false);	
				this.contFeeType.setVisible(false);
			}else{
				this.cbFeeType.setRequired(true);
				this.contFeeType.setVisible(true);
				if(FeeTypeEnum.FEE.equals(cbFeeType.getSelectedItem())){
					this.kDTabbedPane1.add(this.contFeeEntry,"费用明细");
					if(this.kdtFeeEntry.getRowCount()==0)this.kdtFeeEntry.addRow();
				}else if(FeeTypeEnum.TRA.equals(cbFeeType.getSelectedItem())){
					this.kDTabbedPane1.add(this.contTraEntry,"费用明细");
					if(this.kdtTraEntry.getRowCount()==0)this.kdtTraEntry.addRow();
				}
			}
		}else{
			this.cbFeeType.setRequired(false);	
			this.contFeeType.setVisible(false);
		}
	}
	protected void kdtFeeEntry_editStopped(KDTEditEvent e) throws Exception {
		if(this.kdtFeeEntry.getColumnKey(e.getColIndex()).equals("amount")){
			TableUtils.getFootRow(this.kdtFeeEntry,new String[]{"amount"});
		}
	}
	protected void kdtTraEntry_editStopped(KDTEditEvent e) throws Exception {
		if(this.kdtTraEntry.getColumnKey(e.getColIndex()).equals("startDate")||this.kdtTraEntry.getColumnKey(e.getColIndex()).equals("endDate")){
			Date startDate=(Date) this.kdtTraEntry.getRow(e.getRowIndex()).getCell("startDate").getValue();
			Date endDate=(Date) this.kdtTraEntry.getRow(e.getRowIndex()).getCell("endDate").getValue();
			if(startDate!=null&&endDate!=null){
				if(startDate.after(endDate)){
					FDCMsgBox.showWarning(this,"开始日期不能晚于结束日期");
					this.kdtTraEntry.getRow(e.getRowIndex()).getCell(e.getColIndex()).setValue(e.getOldValue());
				}else{
					this.kdtTraEntry.getRow(e.getRowIndex()).getCell("days").setValue(FDCDateHelper.getDiffDays(startDate, endDate));
				}
			}
		}else{
			BigDecimal airFee=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("airFee").getValue());
			BigDecimal carFee=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("carFee").getValue());
			BigDecimal cityFee=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("cityFee").getValue());
			BigDecimal otherFee=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("otherFee").getValue());
			BigDecimal liveFee=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("liveFee").getValue());
			BigDecimal allowance=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("allowance").getValue());
			BigDecimal other=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("other").getValue());
			BigDecimal total=airFee.add(carFee).add(cityFee).add(otherFee).add(liveFee).add(allowance).add(other);
			this.kdtTraEntry.getRow(e.getRowIndex()).getCell("total").setValue(total);
		}
		TableUtils.getFootRow(this.kdtTraEntry,new String[]{"days","airFee","carFee","cityFee","otherFee","persons","liveDays","liveFee","allowance","other","total"});
	}
}