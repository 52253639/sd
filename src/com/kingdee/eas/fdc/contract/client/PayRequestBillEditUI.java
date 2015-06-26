/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.ExtendParser;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnit;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.client.f7.CompanyBizUnitF7;
import com.kingdee.eas.basedata.org.client.f7.CompanyF7;
import com.kingdee.eas.basedata.org.client.f7.CostCenterF7;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BgCtrlBizCollHandler;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.CommonUtilFacadeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.ExpenseTypeSubjectMappingCollection;
import com.kingdee.eas.cp.bc.ExpenseTypeSubjectMappingFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeSubjectMappingInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.cp.bc.client.ExpenseTypePromptBox;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithBgItemFactory;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.TypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.util.TableUtils;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PayContentTypeCollection;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntry;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.UrgentDegreeEnum;
import com.kingdee.eas.fdc.finance.ConPayPlanSplitFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctFacadeFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum;
import com.kingdee.eas.fdc.finance.FDCBudgetConstants;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategy.FDCBudgetParam;
import com.kingdee.eas.fdc.finance.client.ContractAssociateAcctPlanUI;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.finance.client.FDCMonthReqMoneyUI;
import com.kingdee.eas.fdc.finance.client.PayReqAcctPayUI;
import com.kingdee.eas.fdc.finance.client.PaymentBillEditUI;
import com.kingdee.eas.fdc.material.client.MaterialConfirmBillSimpleListUI;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryCollection;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryFactory;
import com.kingdee.eas.fdc.schedule.FDCSCHUtils;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.AbstractCoreBillEditUI;
import com.kingdee.eas.framework.client.workflow.DefaultWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUISupport;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.ma.budget.BgCtrlTypeEnum;
import com.kingdee.eas.ma.budget.BgItemCollection;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgControlItemMapCollection;
import com.kingdee.eas.ma.budget.BgControlItemMapFactory;
import com.kingdee.eas.ma.budget.BgControlSchemeCollection;
import com.kingdee.eas.ma.budget.BgControlSchemeFactory;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.BgCtrlParamInfo;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultInfo;
import com.kingdee.eas.ma.budget.BgItemFactory;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgItemObject;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.ma.budget.client.BudgetCtrlClientCaller;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * 付款申请单 编辑界面
 */
public class PayRequestBillEditUI extends AbstractPayRequestBillEditUI implements IWorkflowUISupport {

	private static final long serialVersionUID = 1L;

	private BigDecimal totalPayAmtByReqId = FDCHelper.ZERO;

	private static final Logger logger = CoreUIObject.getLogger(PayRequestBillEditUI.class);

	private boolean isFirstLoad = true;// 是否第一次加载,用来控制表格的加载显示

	private static final Color noEditColor = PayReqTableHelper.noEditColor;

	protected int rowIndex = 4;// 合同内工程款行号

	protected int columnIndex = 4;// 合同内工程款列号

	private boolean isMoreSettlement = false;
	// 合同（合同或无文本）是否进入动态成本
	protected boolean isCostSplitContract = false;

	// 付款申请单提交时，是否检查合同未完全拆分
	private boolean checkAllSplit = true;
	// 已实现为0时只能选择预付款的控制
	private boolean isRealizedZeroCtrl = false;

	// 完工工程量的确认是否严格控制
	private boolean isFillBillControlStrict = false;

	// 月度滚动计划控制付款申请策略
	private String CONTROLPAYREQUEST = "不控制";
	/**
	 * 调整扣款项窗口
	 */
	private IUIWindow deductUIwindow = null;

	/**
	 * 用于绑定cell进行值操作的map key为属性的info属性名，value为cell的引用
	 */
	HashMap bindCellMap = new HashMap(20);

	private PayReqTableHelper tableHelper = new PayReqTableHelper(this);

	// 是否使用预算
	protected boolean isMbgCtrl = false;
	// 房地产付款单强制不进入出纳系统
	protected boolean isNotEnterCAS = false;
	protected FDCBudgetParam fdcBudgetParam = null;
	// 付款比例,已经结算
	private BigDecimal payScale;
	// 供应商
	// private SupplierCompanyInfoInfo supplierCompanyInfoInfo ;未使用，暂注释
	// 设置付款次数为合同的付款次数 从付款单中过滤
	private int payTimes = 0;
	// 变更单
	private ContractChangeBillCollection contractChangeBillCollection = null;
	// 付款单
	private BillBaseCollection paymentBillCollection = null;
	// 付款申请单对应的奖励项
	private GuerdonOfPayReqBillCollection guerdonOfPayReqBillCollection = null;
	// 奖励单
	private GuerdonBillCollection guerdonBillCollection = null;
	// 付款申请单对应的违约金
	private CompensationOfPayReqBillCollection compensationOfPayReqBillCollection = null;
	// 付款申请单对应的甲供材扣款
	private PartAOfPayReqBillCollection partAOfPayReqBillCollection = null;
	// 付款申请单对应的甲村确认单金额
	private PartAConfmOfPayReqBillCollection partAConfmOfPayReqBillCollection = null;
	// 扣款类型
	private DeductTypeCollection deductTypeCollection = null;
	// 工程项目对应的成本中心
	private FullOrgUnitInfo costOrg = null;

	// 是否加载过初始数据
	private boolean hasFetchInit = false;

	// 累计请款超过合同最新造价严格控制
	private boolean isControlCost = false;

	// 用途字段受控
	protected int usageLegth = 90;

	// 付款申请单收款银行和收款账号为必录项
	private boolean isBankRequire = false;

	// 申请单进度款付款比例自动为100%
	private boolean isAutoComplete = false;

	// 甲供材系统参数
	private boolean partAParam = false;

	// 付款进度比例
	private boolean payProcess = false;

	// 付款申请单合同ID
	private static String payReqContractId = null;

	// 付款申请单合同是否甲供材合同
	public static boolean isPartACon = false;

	// 简单模式一体化
	private boolean isSimpleFinancial = false;

	// 最新造价原币
	private BigDecimal lastestPriceOriginal = FDCHelper.ZERO;

	// 累计发票金额/
	private BigDecimal allInvoiceAmt = FDCHelper.ZERO;

	// 累计已完工工程量/
	private BigDecimal allCompletePrjAmt = FDCHelper.ZERO;

	// 简单财务成本一体化处理扣款、违约、奖励
	private boolean isSimpleFinancialExtend = false;

	// 工程量确认流程与付款流程是否分离
	protected boolean isSeparate = false;

	// 启用发票管理
	private boolean invoiceMgr = false;

	// 简单模式处理发票
	private boolean isSimpleInvoice = false;

	// 付款申请单及无文本合同发票号、发票金额必录
	private boolean isInvoiceRequired = false;

	// 甲供材合同，相关的材料确认单的累计确认金额，需要 写入已 完工工程量，一旦 有了这个金额，不能在修改已完工工程量
	// 相关的材料确认单IDS ，保存时，更新这些材料确认单的付款申请单ID
	private BigDecimal confirmAmts = FDCHelper.ZERO;
	private PayRequestBillConfirmEntryCollection confirmBillEntry = null;

	// 是否允许付款申请单累计发票金额大于合同最新造价
	private boolean isOverrun = false;
	/**
	 * 责任人是否可以选择其他部门的人员,用这个参数来控制用款部门是否可以选择替他组织的用款部门
	 */
	private boolean canSelectOtherOrgPerson = false;

	private final String fncResPath = "com.kingdee.eas.fdc.finance.client.FinanceResource";

	private String allPaidMoreThanConPrice() {
		IParamControl ipctr = null;
		try {
			ipctr = ParamControlFactory.getRemoteInstance();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String retValue = "";
		try {
			retValue = ipctr.getParamValue(null, "FDC444_ALLPAIDMORETHANCONPRICE");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return retValue;
	}

	/*	
	*//**
	 * 财务帐务以发票金额为准计开发成本
	 */
	/*
	 * private boolean isSimpleInvoice() { boolean retValue = false; String
	 * companyID =
	 * SysContext.getSysContext().getCurrentFIUnit().getId().toString(); try {
	 * retValue = FDCUtils.getDefaultFDCParamByKey(null, companyID,
	 * FDCConstants.FDC_PARAM_SIMPLEINVOICE); } catch (EASBizException e) {
	 * e.printStackTrace(); } catch (BOSException e) { e.printStackTrace(); }
	 * return retValue; }
	 */

	// 合同完工工程量取进度系统工程量填报数据
	private boolean isFromProjectFillBill = false;

	// 第一次全部请款，第二次请款为0
	private boolean isZeroComplete = false;

	// 此单据是否有附件
	private boolean isHasAttchment = false;
	// 预付款次数
	private int advancePaymentNumber = 1;
	//
	private boolean isControlPay = false;

	/**
	 * output class constructor
	 */
	public PayRequestBillEditUI() throws Exception {
		super();
		jbInit();
	}

	private void jbInit() {
		titleMain = getUITitle();
	}

	/**
	 * 覆盖父类的方法以控制工具栏按钮的显示
	 * 
	 * @author sxhong Date 2006-9-13
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#initUIToolBarLayout()
	 */
	public void initUIToolBarLayout() {
		this.toolBar.add(btnAddNew);
		this.toolBar.add(btnEdit);
		this.toolBar.add(btnSave);
		this.toolBar.add(btnSubmit);
		this.toolBar.add(btnCopy);
		this.toolBar.add(btnRemove);
		this.toolBar.add(btnCancelCancel);
		this.toolBar.add(btnCancel);
		this.toolBar.add(btnAttachment);
		this.toolBar.add(separatorFW1);
		this.toolBar.add(btnPageSetup);
		this.toolBar.add(separatorFW3);
		this.toolBar.add(btnFirst);
		this.toolBar.add(btnPre);
		this.toolBar.add(btnNext);
		this.toolBar.add(btnLast);

		// 套打
		btnTaoPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		this.toolBar.add(btnTaoPrint);
		this.toolBar.add(btnPrint);
		this.toolBar.add(btnPrintPreview);
		this.toolBar.add(separatorFW2);
		// 付款计划
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		this.toolBar.add(btnAudit);
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.toolBar.add(btnUnAudit);
		btnPaymentPlan.setIcon(EASResource.getIcon("imgTbtn_showdata"));
		this.toolBar.add(btnPaymentPlan);

		actionClose.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_close"));
		actionUnClose.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_fclose"));

		this.btnSetTotal.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
		this.toolBar.add(this.btnSetTotal);
		this.toolBar.add(btnClose);
		this.toolBar.add(btnUnclose);

		this.toolBar.add(separatorFW4);
		// 调整扣款项按钮
		actionAdjustDeduct.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_showdata"));
		this.toolBar.add(btnAdjustDeduct);
		this.toolBar.add(separatorFW5);
		btnCalc.setIcon(EASResource.getIcon("imgTbtn_counter"));
		this.toolBar.add(btnCalc);

		this.toolBar.add(btnTraceUp);
		this.toolBar.add(btnTraceDown);

		this.toolBar.add(btnViewContract);
		this.toolBar.add(btnViewPayDetail);
		this.toolBar.add(btnContractExecInfo);

		this.toolBar.add(btnAuditResult);
		actionAuditResult.setVisible(true);
		actionAuditResult.setEnabled(true);

		actionContractAttachment.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_view"));
		this.toolBar.add(btnContractAttachment);
		actionContractAttachment.setVisible(true);
		actionContractAttachment.setEnabled(true);

		this.toolBar.add(this.btnViewMaterialConfirm);
	}

	// 变化事件
	protected void controlDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e, ControlDateChangeListener listener) throws Exception {
		if ("bookedDate".equals(listener.getShortCut())) {
			bookedDate_dataChanged(e);
		} else if ("amount".equals(listener.getShortCut())) {
			currencydataChanged(e);
		} else if ("completePrjAmt".equals(listener.getShortCut()) || "paymentProp".equals(listener.getShortCut())) {
			setPropPrjAmount(listener.getShortCut(), e);
		}
	}

	// 业务日期变化事件
	ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener("bookedDate");

	ControlDateChangeListener amountListener = new ControlDateChangeListener("amount");

	ControlDateChangeListener completePrjAmtListener = new ControlDateChangeListener("completePrjAmt");

	ControlDateChangeListener paymentProptListener = new ControlDateChangeListener("paymentProp");

	// 监听器
	protected void attachListeners() {
		txtcompletePrjAmt.addDataChangeListener(completePrjAmtListener);
		prmtcurrency.addDataChangeListener(amountListener);
		txtpaymentProportion.addDataChangeListener(paymentProptListener);
		pkbookedDate.addDataChangeListener(bookedDateChangeListener);

		// 用款部门
		addDataChangeListener(prmtuseDepartment);
		addDataChangeListener(prmtPayment);
		addDataChangeListener(txtAmount);
		addDataChangeListener(pkpayDate);

		addDataChangeListener(prmtsettlementType);
		addDataChangeListener(prmtrealSupplier);
		addDataChangeListener(prmtsupplier);
		addDataChangeListener(txtpaymentProportion);

		addDataChangeListener(this.cbIsBgControl);
		addDataChangeListener(this.prmtApplier);
		addDataChangeListener(this.prmtApplierOrgUnit);
		addDataChangeListener(this.prmtCostedCompany);
		addDataChangeListener(this.prmtCostedDept);
	}

	protected void detachListeners() {
		txtcompletePrjAmt.removeDataChangeListener(completePrjAmtListener);
		prmtcurrency.removeDataChangeListener(amountListener);
		txtpaymentProportion.removeDataChangeListener(paymentProptListener);
		pkbookedDate.removeDataChangeListener(bookedDateChangeListener);

		removeDataChangeListener(prmtuseDepartment);
		removeDataChangeListener(prmtPayment);
		removeDataChangeListener(txtAmount);
		removeDataChangeListener(pkpayDate);
		removeDataChangeListener(prmtsettlementType);
		removeDataChangeListener(prmtrealSupplier);
		removeDataChangeListener(prmtsupplier);
		removeDataChangeListener(txtpaymentProportion);

		removeDataChangeListener(this.cbIsBgControl);
		removeDataChangeListener(this.prmtApplier);
		removeDataChangeListener(this.prmtApplierOrgUnit);
		removeDataChangeListener(this.prmtCostedCompany);
		removeDataChangeListener(this.prmtCostedDept);
	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		if ((hasFetchInit && !this.getOprtState().equals(OprtState.VIEW)) || this.getUIContext().get("PayRequestFullListUI") != null) {
			try {
				fetchInitData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		detachListeners();

		// 处理付款比例及已完工工程量的加载
		initPaymentProp();
		// setAutoNumber();
		if (editData.getUrgentDegree() == null)
			editData.setUrgentDegree(UrgentDegreeEnum.NORMAL);
		super.loadFields();
		if (OprtState.ADDNEW.equals(getOprtState())) {
			txtpaymentProportion.setValue(FDCHelper.ZERO);
			txtcompletePrjAmt.setValue(FDCHelper.ZERO);
		}
		// 更新本位币显示
		// setAmount();

		// 设置付款次数为合同的付款次数 从付款单中过滤
		if (editData.getState() != FDCBillStateEnum.AUDITTED) {
			editData.setPayTimes(payTimes);
		}

		if (editData.getState() != null && !editData.getState().equals(FDCBillStateEnum.SAVED)) {
			btnSave.setEnabled(false);
		}

		// if (editData.getUrgentDegree() == UrgentDegreeEnum.URGENT) {
		// chkUrgency.setSelected(true);
		// } else {
		// chkUrgency.setSelected(false);
		// }

		if (editData.getCurProject() != null) {
			CurProjectInfo curProjectInfo = editData.getCurProject();
			txtProj.setText(curProjectInfo.getDisplayName());
		}
		if (editData.getOrgUnit() != null) {
			txtOrg.setText(editData.getOrgUnit().getDisplayName());
		}

		//
		if (editData.getContractId() != null && PayReqUtils.isConWithoutTxt(editData.getContractId())) {
			actionAdjustDeduct.setEnabled(false);
		} else {
			actionAdjustDeduct.setEnabled(true);
		}

		//
		if (editData.getCapitalAmount() == null && editData.getAmount() != null) {
			// 大写金额为本位币金额
			BigDecimal localamount = editData.getAmount();
			if (localamount.compareTo(FDCConstants.ZERO) != 0) {
				localamount = localamount.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			String cap = FDCClientHelper.getChineseFormat(localamount, false);
			// FDCHelper.transCap((CurrencyInfo) value, amount);
			txtcapitalAmount.setText(cap);
			if (localamount.compareTo(FDCConstants.ZERO) == 0) {
				txtcapitalAmount.setText(null);
			}
			editData.setCapitalAmount(cap);
		}

		hasFetchInit = true;

		loadInvoiceAmt();

		loadAllCompletePrjAmt();

		this.loadBgEntryTable();

		TableUtils.getFootRow(this.kdtBgEntry, new String[] { "requestAmount", "amount" });

		attachListeners();

		// 收款帐号修改成F7选择，但是元数据定义的是自有属性， 所以没有进行数据绑定，需要手动装载。 by zhiyuan_tang
		// 2010/12/07 R101026-193
		if (editData.getRealSupplier() != null) {
			txtrecAccount.setValue(getSupplierCompanyBankInfoByAccount(editData.getRealSupplier().getId().toString(), editData.getRecBank(), editData.getRecAccount()));
		}
		txtrecAccount.setText(editData.getRecAccount());

		this.txtContractInfo.setText(editData.getContractNo() + " " + editData.getContractName());
		isShowBgAmount = true;
		
		this.cbIsBgControl.setEnabled(false);
		this.chkIsPay.setEnabled(false);
		

	    try {
			fillAttachmnetTable();
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 设置精度
	protected void setPrecision() {
		CurrencyInfo currency = editData.getCurrency();
		Date bookedDate = (Date) editData.getBookedDate();

		ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, currency.getId(), company, bookedDate);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
		int exPrecision = curPrecision;

		if (exchangeRate != null) {
			exPrecision = exchangeRate.getPrecision();
		}

		txtexchangeRate.setPrecision(exPrecision);
		txtAmount.setPrecision(curPrecision);
		BigDecimal exRate = editData.getExchangeRate();
		txtexchangeRate.setValue(exRate);
		txtAmount.setValue(editData.getOriginalAmount());
	}

	public void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);

		String contractId = editData.getContractId();

		/**
		 * 给工作流中的状态做判断 by renliang 2010-5-26
		 */
		if (getUIContext().get("isFromWorkflow") != null && getUIContext().get("isFromWorkflow").toString().equals("true") && getOprtState().equals(OprtState.EDIT) && actionSave.isVisible()) {
			if (!editData.getState().equals(FDCBillStateEnum.SUBMITTED) || !editData.getState().equals(FDCBillStateEnum.SAVED)) {
				editData.setState(FDCBillStateEnum.SUBMITTED);
			}

		}

		// 将分录内的数据存储到info
		if (PayReqUtils.isContractBill(contractId) && (editData.getState() == FDCBillStateEnum.SAVED || editData.getState() == FDCBillStateEnum.SUBMITTED)) {
			try {
				tableHelper.updateDynamicValue(editData, contractBill, contractChangeBillCollection, paymentBillCollection);
			} catch (Exception e1) {
				handUIException(e1);
			}
			PayReqUtils.getValueFromCell(editData, bindCellMap);
			if (isAdvance()) {
				tableHelper.updateLstAdvanceAmt(editData, true);
			}
		} else {
			// 显示本申请单的付款金额
			if (editData != null && editData.getId() != null && editData.getState() == FDCBillStateEnum.AUDITTED) {
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("select sum(famount) as amount from t_cas_paymentbill where ffdcPayReqID=? and fbillStatus=?");
				builder.addParam(editData.getId().toString());
				builder.addParam(new Integer(BillStatusEnum.PAYED_VALUE));
				try {
					IRowSet rowSet = builder.executeQuery();
					if (rowSet.size() > 0) {
						rowSet.next();
						BigDecimal payedAmt = rowSet.getBigDecimal("amount");
						editData.setPayedAmt(FDCHelper.toBigDecimal(payedAmt));
						tableHelper.setCellValue(PayRequestBillContants.PAYEDAMT, payedAmt);
					}
				} catch (Exception e1) {
					handUIException(e1);
				}

			}
		}
		editData.setPayDate(DateTimeUtils.truncateDate(editData.getPayDate()));
		if (PayReqUtils.isContractBill(contractId)) {

			// 付款比例
			// if (!contractBill.isHasSettled()) {
			setCostAmount();
			// }

		}
		// setAmount();
		try {
			this.btnInputCollect_actionPerformed(null);
		} catch (Exception ex) {
			// TODO 自动生成 catch 块
			ex.printStackTrace();
		}
		// /**
		// * setCostAmount已处理
		// */
		// if (isSimpleFinancial) {
		// BigDecimal amount = FDCHelper
		// .toBigDecimal(((ICell) bindCellMap
		// .get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
		// .getValue());// txtBcAmount.getBigDecimalValue();
		// editData.setCompletePrjAmt(amount);
		// }
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		try {
			if (editData != null) {// 第一次保存时初始状态
				// 付款申请单增加存储本位币币别，以方便预算系统能取到该字段值 by Cassiel_peng 2009-10-3
				CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
				CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
				if (baseCurrency != null) {
					// R110519-0204:编码空，预算余额取不到预算控制失效 by hpw 2011.6.2
					if (baseCurrency.getNumber() == null) {
						baseCurrency = CurrencyFactory.getRemoteInstance().getCurrencyInfo(new ObjectUuidPK(baseCurrency.getId()));
					}
					this.editData.setLocalCurrency(baseCurrency);
				}
			}
			editData.setLatestPrice(FDCHelper.toBigDecimal((FDCUtils.getLastAmt_Batch(null, new String[] { editData.getContractId().toString() }).get(editData.getContractId().toString()))));
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 收款帐号修改成F7选择，但是元数据定义的是自有属性， 所以没有进行数据绑定，需要手动保存一下。 by zhiyuan_tang
		// 2010/12/07 R101026-193
		if (txtrecAccount.getValue() instanceof String || txtrecAccount.getText() instanceof String) {
			editData.setRecAccount(txtrecAccount.getText());
		} else if (txtrecAccount.getValue() instanceof SupplierCompanyBankInfo) {
			SupplierCompanyBankInfo info = (SupplierCompanyBankInfo) txtrecAccount.getValue();
			editData.setRecAccount(info.getBankAccount());
		} else {
			editData.setRecAccount(null);
		}

		this.storeBgEntryTable();
		super.storeFields();
	}

	protected void fetchInitData() throws Exception {
		String contractBillId = (String) getUIContext().get("contractBillId");

		Map initparam = new HashMap();

		// if(editData != null && editData.getId() != null){
		// initparam.put("reqPayId", editData.getId().toString());
		// }else if(getUIContext().get("reqId") != null){
		// initparam.put("reqPayId", getUIContext().get("reqId"));
		// }

		if (contractBillId != null) {
			initparam.put("contractBillId", contractBillId);
		} else {
			if (editData != null && editData.getContractId() != null) {
				initparam.put("contractBillId", editData.getContractId());
			} else {
				initparam.put("ID", getUIContext().get("ID"));
			}
		}
		// Map initData =
		// ((IFDCBill)getBizInterface()).fetchInitData(initparam);
		Map initData = (Map) ActionCache.get("FDCBillEditUIHandler.initData");
		if (initData == null) {
			initData = ((IFDCBill) getBizInterface()).fetchInitData(initparam);
		}

		if (initData.get("totalPayAmtByReqId") != null) {
			totalPayAmtByReqId = (BigDecimal) initData.get("totalPayAmtByReqId");
		}

		// 本位币
		baseCurrency = (CurrencyInfo) initData.get(FDCConstants.FDC_INIT_CURRENCY);
		//
		company = (CompanyOrgUnitInfo) initData.get(FDCConstants.FDC_INIT_COMPANY);
		// 合同单据
		contractBill = (ContractBillInfo) initData.get(FDCConstants.FDC_INIT_CONTRACT);
		// 付款比例
		payScale = (BigDecimal) initData.get("payScale");
		// 供应商
		// supplierCompanyInfoInfo =
		// (SupplierCompanyInfoInfo)initData.get("supplierCompanyInfoInfo");
		// 设置付款次数为合同的付款次数 从付款单中过滤
		payTimes = ((Integer) initData.get("payTimes")).intValue();
		// 变更单
		contractChangeBillCollection = (ContractChangeBillCollection) initData.get("ContractChangeBillCollection");
		// 付款单
		paymentBillCollection = (BillBaseCollection) initData.get("PaymentBillCollection");
		// 付款申请单对应的奖励项
		guerdonOfPayReqBillCollection = (GuerdonOfPayReqBillCollection) initData.get("GuerdonOfPayReqBillCollection");
		// 奖励单
		guerdonBillCollection = (GuerdonBillCollection) initData.get("GuerdonBillCollection");
		// 付款申请单对应的违约金
		compensationOfPayReqBillCollection = (CompensationOfPayReqBillCollection) initData.get("CompensationOfPayReqBillCollection");
		// 付款申请单对应的甲供材扣款
		partAOfPayReqBillCollection = (PartAOfPayReqBillCollection) initData.get("PartAOfPayReqBillCollection");
		// 付款申请单对应的甲供材确认单金额
		partAConfmOfPayReqBillCollection = (PartAConfmOfPayReqBillCollection) initData.get("PartAConfmOfPayReqBillCollection");
		// 扣款类型
		deductTypeCollection = (DeductTypeCollection) initData.get("DeductTypeCollection");
		// 工程项目对应的成本中心
		costOrg = (FullOrgUnitInfo) initData.get("FullOrgUnitInfo");

		// 日期
		bookedDate = (Date) initData.get(FDCConstants.FDC_INIT_DATE);
		if (bookedDate == null) {
			bookedDate = new Date();
		}
		serverDate = (Date) initData.get("serverDate");
		if (serverDate == null) {
			serverDate = bookedDate;
		}
		// 当前期间
		curPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_PERIOD);

		curProject = (CurProjectInfo) initData.get(FDCConstants.FDC_INIT_PROJECT);

		orgUnitInfo = (FullOrgUnitInfo) initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		if (orgUnitInfo == null) {
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		}
		lastestPriceOriginal = (BigDecimal) initData.get("lastestPriceOriginal");

		// 甲供确认单的确认金额
		confirmAmts = FDCHelper.toBigDecimal(initData.get("confirmAmts"));
		// 甲供确认分录
		confirmBillEntry = (PayRequestBillConfirmEntryCollection) initData.get("confirmBillEntry");
		this.isCostSplitContract = isCostSplit();
	}

	/**
	 * 
	 * @return 合同或无文本合同为进入动态成本时返回true，否则返回false
	 * @throws Exception
	 */
	private boolean isCostSplit() throws Exception {
		if (contractBill != null && contractBill.isIsCoseSplit()) {
			return true;
		}
		String contractBillId = (String) getUIContext().get("contractBillId");

		if (PayReqUtils.isConWithoutTxt(contractBillId)) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isCostSplit");
			ContractWithoutTextInfo withoutTextInfo = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)), selector);
			if (withoutTextInfo != null && withoutTextInfo.isIsCostSplit()) {
				return true;
			}
		}
		return false;
	}

	// 子类可以重载
	protected void fetchInitParam() throws Exception {

		// 是否启用预算控制
		if (orgUnitInfo != null) {
			// HashMap param =
			// FDCUtils.getDefaultFDCParam(null,orgUnitInfo.getId().toString());
			Map param = null;// (Map)ActionCache.get(
			// "FDCBillEditUIHandler.orgParamItem");

			/*
			 * if (param == null) { param = FDCUtils.getDefaultFDCParam(null,
			 * orgUnitInfo.getId().toString()); }
			 */
			// update by renliang
			param = FDCUtils.getDefaultFDCParam(null, orgUnitInfo.getId().toString());

			isMbgCtrl = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_STARTMG).toString()).booleanValue();

			// 付款申请单付款金额不允许超过可付款额度
			isControlCost = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_OUTPAYAMOUNT).toString()).booleanValue();

			// 申请单进度款付款比例自动为100%
			isAutoComplete = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_PAYPROGRESS).toString()).booleanValue();

			if (param.get(FDCConstants.FDC_PARAM_SELECTPERSON) != null) {
				canSelectOtherOrgPerson = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString()).booleanValue();
			}
			if (param.get(FDCConstants.FDC_PARAM_ADVANCEPAYMENTNUMBER) != null) {
				advancePaymentNumber = Integer.valueOf(param.get(FDCConstants.FDC_PARAM_ADVANCEPAYMENTNUMBER).toString()).intValue();
			}
			if (param.get(FDCConstants.FDC_PARAM_ISCONTROLPAYMENT) != null) {
				isControlPay = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_ISCONTROLPAYMENT).toString()).booleanValue();
			}

			if (param.get(FDCConstants.FDC_PARAM_MORESETTER) != null) {
				isMoreSettlement = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_MORESETTER).toString()).booleanValue();
			}

		}

		if (company == null) {
			return;
		}
		// 启用成本财务一体化
		// HashMap param =
		// FDCUtils.getDefaultFDCParam(null,company.getId().toString());
		Map paramItem = null;// (Map)ActionCache.get(

		// update by renliang
		paramItem = FDCUtils.getDefaultFDCParam(null, company.getId().toString());

		if (paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION) != null) {
			isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
		}

		// 启用成本月结
		if (paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION) != null) {
			isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
		}

		// 简单模式的一体化
		if (paramItem.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL) != null) {
			isSimpleFinancial = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL).toString()).booleanValue();
			// isAutoComplete = isAutoComplete;//||isSimpleFinancial;
		}

		if (paramItem.get(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND) != null) {
			isSimpleFinancialExtend = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND).toString()).booleanValue();
		}

		// 用途字段受控
		if (paramItem.get("CS050") != null) {
			usageLegth = Integer.valueOf(paramItem.get("CS050").toString()).intValue();
		}

		// 付款申请单收款银行和收款账号为必录项
		if (paramItem.get(FDCConstants.FDC_PARAM_BANKREQURE) != null) {
			isBankRequire = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_BANKREQURE).toString()).booleanValue();
		}

		// 房地产付款单强制进入出纳系统
		if (paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS) != null) {
			isNotEnterCAS = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS).toString()).booleanValue();
		}

		// 甲供材
		if (paramItem.get(FDCConstants.FDC_PARAM_CREATEPARTADEDUCT) != null) {
			partAParam = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_CREATEPARTADEDUCT).toString()).booleanValue();
		}
		fdcBudgetParam = FDCBudgetParam.getInstance(paramItem);

		HashMap paramMap = FDCUtils.getDefaultFDCParam(null, null);
		checkAllSplit = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_CHECKALLSPLIT);
		isRealizedZeroCtrl = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_REALIZEDZEROCTRL);
		// isRealizedZeroCtrl=true;
		if (paramItem.get(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT) != null) {
			isSeparate = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT).toString()).booleanValue();
		}
		if (paramItem.get(FDCConstants.FDC_PARAM_INVOICEREQUIRED) != null) {
			isInvoiceRequired = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INVOICEREQUIRED).toString()).booleanValue();
		}
		if (paramItem.get(FDCConstants.FDC_PARAM_INVOICEMRG) != null) {
			invoiceMgr = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INVOICEMRG).toString()).booleanValue();
		}
		if (paramItem.get(FDCConstants.FDC_PARAM_OVERRUNCONPRICE) != null) {
			isOverrun = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_OVERRUNCONPRICE).toString()).booleanValue();
		}

		// 合同完工工程量取进度系统工程量填报数据
		if (paramItem.get(FDCConstants.FDC_PARAM_PROJECTFILLBILL) != null) {
			boolean tempBoolean = (Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_PROJECTFILLBILL).toString()).booleanValue());
			isFromProjectFillBill = tempBoolean && (!isSeparate) && (!isAutoComplete);
		}

		if (paramItem.get(FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT) != null) {
			boolean tempBoolean = (Boolean.valueOf(paramItem.get(FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT).toString()).booleanValue());
			isFillBillControlStrict = tempBoolean && (!isSeparate) && (!isAutoComplete);
		}
		if (paramItem.get(FDCConstants.FDC_PARAM_SIMPLEINVOICE) != null) {
			isSimpleInvoice = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_SIMPLEINVOICE).toString()).booleanValue();
		}

		String org = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		CONTROLPAYREQUEST = ParamManager.getParamValue(null, new ObjectUuidPK(org), "FDC325_CONTROLPAYREQUEST");
		if ("0".equals(CONTROLPAYREQUEST)) {
			CONTROLPAYREQUEST = "严格控制";
		} else if ("1".equals(CONTROLPAYREQUEST)) {
			CONTROLPAYREQUEST = "提示控制";
		} else {
			CONTROLPAYREQUEST = "不控制";
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		pkbookedDate_dataChanged(null);
		if (getOprtState().equals(OprtState.EDIT) || getOprtState().equals(OprtState.ADDNEW)) {
			try {
				Map param = new HashMap();
				param.put("ContractBillId", contractBill.getId().toString());
				Map totalSettle = ContractFacadeFactory.getRemoteInstance().getTotalSettlePrice(param);
				if (totalSettle != null) {
					editData.setTotalSettlePrice((BigDecimal) totalSettle.get("SettlePrice"));
				} else {
					editData.setTotalSettlePrice(FDCConstants.ZERO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.txtTotalSettlePrice.setValue(editData.getTotalSettlePrice());
			prmtPlanHasCon.setEnabled(false);
		} else {
			prmtPlanHasCon.setEnabled(false);
			prmtPlanHasCon.setEditable(false);
		}

		if (confirmBillEntry != null && editData != null) {
			editData.put("confirmEntry", confirmBillEntry);
		}
		fillAttachmnetList();
		tableHelper = new PayReqTableHelper(this);
		kdtEntrys = tableHelper.createPayRequetBillTable(deductTypeCollection);
		kdtEntrys.addKDTEditListener(new KDTEditAdapter() {
			// 编辑结束后
			public void editStopped(KDTEditEvent e) {
				try {
					kdtEntrys_editStopped(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		if (isFirstLoad) {// 第一次加载时初始化表格的内容,以后不会改变
			if (!getOprtState().equals(OprtState.ADDNEW)) {
				tableHelper.updateLstReqAmt(editData, false);
			}
			getDetailTable().getScriptManager().setAutoRun(false);
			PayReqUtils.setValueToCell(editData, bindCellMap);
		}
		if (isAdvance()) {
			tableHelper.updateLstAdvanceAmt(editData, false);
			// 公式被吃,重写
			kdtEntrys.getCell(5, 6).setExpressions("=sum(D6,F6)");
		}
		if (isFirstLoad)
			isFirstLoad = false;
		if (txtexchangeRate.getNumberValue() == null) {
			txtexchangeRate.setValue(FDCConstants.ONE);
		}

		// boolean close = editData.isHasClosed();
		// actionClose.setVisible(!close);
		// actionClose.setEnabled(!close);
		// actionUnClose.setVisible(close);
		// actionUnClose.setEnabled(close);

		actionClose.setVisible(false);
		actionUnClose.setVisible(false);

		btnAttachment.setText(getRes("btnAttachment"));

		actionTraceDown.setVisible(true);
		actionTraceDown.setEnabled(true);
		actionTraceUp.setVisible(true);
		actionTraceUp.setEnabled(true);

		actionAuditResult.setVisible(true);
		actionAuditResult.setEnabled(true);

		getDetailTable().getScriptManager().setAutoRun(true);
		kdtEntrys.getScriptManager().runAll();
		this.tableHelper.setBeforeAction();
		// 累计发票金额
		txtInvoiceAmt.setPrecision(2);
		txtInvoiceAmt.setSupportedEmpty(true);
		txtAllInvoiceAmt.setSupportedEmpty(true);
		txtInvoiceAmt.setMinimumValue(FDCHelper.MIN_VALUE);
		txtInvoiceAmt.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
		txtInvoiceAmt.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				BigDecimal invoiceAmt = txtInvoiceAmt.getBigDecimalValue();
				if (!FDCHelper.isEmpty(invoiceAmt) && invoiceAmt.compareTo(FDCHelper.ZERO) == 0) {
					// 为零时非必录
					txtInvoiceNumber.setRequired(false);
				} else if (isInvoiceRequired) {
					txtInvoiceNumber.setRequired(true);
				}
				txtAllInvoiceAmt.setNumberValue(allInvoiceAmt.add(FDCHelper.toBigDecimal(invoiceAmt)));
			}
		});
		if (isInvoiceRequired) {
			txtInvoiceNumber.setRequired(true);
			txtInvoiceAmt.setRequired(true);
		}
		calAllCompletePrjAmt();
		// 增加原币金额的可录入范围
		txtAmount.setPrecision(2);
		txtAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		txtAmount.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
		// 修改关于本位币金额计算时有错误，只支持汇率小数点后两位
		txtBcAmount.setPrecision(2); // added by Owen_wen 统一改为2位小数，详情请见
		// 提单号R100520-107
		txtBcAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		txtBcAmount.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));

		txtattachment.setNegatived(false);
		txtattachment.setPrecision(0);
		txtattachment.setRemoveingZeroInDispaly(true);
		txtattachment.setRemoveingZeroInEdit(true);

		txtcapitalAmount.setEditable(false);

		txtMoneyDesc.setMaxLength(500);
		txtProcess.setMaxLength(255);

		txtUsage.setMaxLength(usageLegth);
		prmtDesc.setMaxLength(150);

		txtpaymentProportion.setPrecision(2);
		txtpaymentProportion.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		txtpaymentProportion.setMinimumValue(FDCHelper.ZERO);
		txtpaymentProportion.setRoundingMode(BigDecimal.ROUND_HALF_EVEN);
		txtpaymentProportion.setRemoveingZeroInEdit(false);
		txtpaymentProportion.setRemoveingZeroInDispaly(false);
		txtpaymentProportion.setSupportedEmpty(false);
		txtcompletePrjAmt.setPrecision(2);
		txtcompletePrjAmt.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE.multiply(new BigDecimal("100000")));
		txtcompletePrjAmt.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
		if (isAutoComplete) {
			txtcompletePrjAmt.setRequired(false);
			txtpaymentProportion.setEditable(false);
		}

		if (getOprtState() != OprtState.ADDNEW) {
			tableHelper.reloadGuerdonValue(editData, null);
			tableHelper.reloadCompensationValue(editData, null);
			tableHelper.updateLstReqAmt(editData, true);
		}
//		prmtsupplier.setEditable(false);

		String cuid = this.curProject.getCU().getId().toString();

		FDCClientUtils.initSupplierF7(this, prmtsupplier, cuid);
		FDCClientUtils.initSupplierF7(this, prmtrealSupplier, cuid);

		prmtPayment.addDataChangeListener(new DataChangeListener() {
			// 付完结算款后才能付保修款，付完结算款后不能付进度款
			public void dataChanged(DataChangeEvent eventObj) {
				prmtPayment_dataChanged(eventObj);
			}
		});
		if (PayReqUtils.isConWithoutTxt(editData.getContractId())) {
			actionAddNew.setEnabled(false);
			actionCopy.setEnabled(false);
		}
//		prmtsupplier.setEditable(false);
//		prmtsupplier.setEnabled(false);

		String cu = null;
		if (editData != null && editData.getCU() != null) {
			cu = editData.getCU().getId().toString();
		} else {
			cu = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		}
		FDCClientUtils.setRespDeptF7(prmtuseDepartment, this, canSelectOtherOrgPerson ? null : cu);

		DataChangeEvent e = new DataChangeEvent(pkpayDate, this.editData.getPayDate(), null);
		pkpayDate_dataChanged(e);

		if (!fdcBudgetParam.isBgSysCtrl()) {
			actionViewMbgBalance.setVisible(false);
			this.menuItemViewMbgBalance.setVisible(false);
			actionViewMbgBalance.setEnabled(false);
		} else {
			actionViewMbgBalance.setVisible(true);
			this.menuItemViewMbgBalance.setVisible(true);
			actionViewMbgBalance.setEnabled(true);
		}

		ExtendParser parserCity = new ExtendParser(prmtDesc);
		prmtDesc.setCommitParser(parserCity);

		// 根据参数设置是否必录
		txtrecBank.setRequired(isBankRequire);
		txtrecAccount.setRequired(isBankRequire);

		setPrecision();

		actionPrintPreview.setVisible(true);
		actionPrintPreview.setEnabled(true);
		actionPrint.setVisible(true);
		actionPrint.setEnabled(true);

		if (PayReqUtils.isContractBill(editData.getContractId()) && isNotEnterCAS) {
			chkIsPay.setEnabled(false);
			// 添加判断，保证显示正确
			if (OprtState.ADDNEW.equals(getOprtState())) {
				chkIsPay.setSelected(false);
			}
		}

		if (getOprtState() != OprtState.VIEW) {
			this.storeFields();
		}

		if (contractBill != null && PayReqUtils.isContractBill(editData.getContractId())) {
			isPartACon = this.contractBill.isIsPartAMaterialCon();
		}
		/**
		 * 系统参数设置为真的时候，隐藏进度付款比例和本期完工工程量金额
		 */
		if (fdcBudgetParam.isAcctCtrl() && contractBill != null && contractBill.isIsCoseSplit()) {
			// 关联签定与待签定改进
			actionAssociateAcctPay.setVisible(false);
			actionAssociateAcctPay.setEnabled(false);
			actionAssociateUnSettled.setVisible(false);
			actionAssociateUnSettled.setEnabled(false);
			actionMonthReq.setVisible(true);
			actionMonthReq.setEnabled(true);
		} else {
			actionAssociateAcctPay.setVisible(false);
			actionAssociateAcctPay.setEnabled(false);
			actionAssociateUnSettled.setVisible(false);
			actionAssociateUnSettled.setEnabled(false);
			actionMonthReq.setVisible(false);
			actionMonthReq.setEnabled(false);
		}

		// 业务日期判断为空时取期间中断
		if (pkbookedDate != null && pkbookedDate.isSupportedEmpty()) {
			pkbookedDate.setSupportedEmpty(false);
		}
		this.prmtcurrency.setEditable(false);
		this.prmtcurrency.setEnabled(false);

		Object value = prmtcurrency.getValue();
		if (value instanceof CurrencyInfo) {
			// 本位币处理
			CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
			CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
			BOSUuid srcid = ((CurrencyInfo) value).getId();
			if (baseCurrency != null) {
				if (srcid.equals(baseCurrency.getId())) {
					txtexchangeRate.setValue(FDCConstants.ONE);
					txtexchangeRate.setEditable(false);
					// return;
				} else
					txtexchangeRate.setEditable(true);
			}
		}

		this.getDetailTable().setAfterAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					BigDecimal oriAmt = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex, columnIndex).getValue());
					if (FDCHelper.ZERO.compareTo(oriAmt) == 0) {
						getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(null);
					}
					oriAmt = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex + 1, columnIndex).getValue());
					if (FDCHelper.ZERO.compareTo(oriAmt) == 0) {
						getDetailTable().getCell(rowIndex + 1, columnIndex + 1).setValue(null);
					}
				}
			}

		});

		this.actionViewMaterialConfirm.setVisible(true);
		if (FDCHelper.ZERO.compareTo(this.confirmAmts) != 0) {
			this.setConfirmBillEntryAndPrjAmt();
		}
		/**
		 * 完工工程量从进度系统取值
		 */
		if (isFromProjectFillBill) {
			actionCopy.setEnabled(false);
			txtcompletePrjAmt.setEditable(false);
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes().setLocked(true);
			if (getOprtState().equals(OprtState.ADDNEW)) {
				fromProjectFill();
			} else if (FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue()).compareTo(FDCHelper.ZERO) == 0) {
				txtpaymentProportion.setEditable(false);
				kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes().setLocked(false);
			}
		}

		// 付款单为暂估款类型时其他可录入金额字段仅发票金额可录入(对应的合同内工程款等其他款项不可录入)
		Object obj = prmtPayment.getValue();
		if (obj != null && obj instanceof PaymentTypeInfo) {
			String tempID = PaymentTypeInfo.tempID;// 暂估款
			PaymentTypeInfo type = (PaymentTypeInfo) obj;
			if (type.getPayType().getId().toString().equals(tempID)) {
				this.kdtEntrys.getStyleAttributes().setLocked(true);
				if (this.kdtEntrys.getCell(4, 4) != null) {
					this.kdtEntrys.getCell(4, 4).getStyleAttributes().setLocked(true);
				}
			}
		}

		// 本申请单累计实付款实时取值
		if (kdtEntrys.getCell(2, 6) != null) {
			if (editData.getId() != null) {
				totalPayAmtByReqId = getTotalPayAmtByThisReq(editData.getId().toString());
			}

			kdtEntrys.getCell(2, 6).setValue(totalPayAmtByReqId);
		}
		// 合同修订和变更都需要将金额同步反映到单据状态为保存或者是提交的付款申请单中的变更指令金额中去 by cassiel 2010-08-06
		if (!FDCBillStateEnum.AUDITTED.equals(this.editData.getState())) {
			tableHelper.updateDynamicValue(editData, contractBill, contractChangeBillCollection, paymentBillCollection);
		}
		reloadPartADeductDetails();

		ExtendParser parserAccountFrom = new ExtendParser(txtrecAccount);
		txtrecAccount.setCommitParser(parserAccountFrom);
		txtrecAccount.setMaxLength(80);

		initPrmtPlanUnCon();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isBizUnit", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isSealUp", Boolean.FALSE));
		CompanyOrgUnitInfo com = (CompanyOrgUnitInfo) this.prmtCostedCompany.getValue();
		if (com != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", getCostedDeptIdSet(com), CompareType.INCLUDE));
		}
		view.setFilter(filter);
		this.prmtCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
		this.prmtCostedDept.setEntityViewInfo(view);
		this.prmtCostedDept.setEditFormat("$number$");
		this.prmtCostedDept.setDisplayFormat("$name$");
		this.prmtCostedDept.setCommitFormat("$number$");

		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isSealUp", Boolean.FALSE));
		view.setFilter(filter);
		this.prmtCostedCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");
		CompanyF7 comp = new CompanyF7(this);
		prmtCostedCompany.setEntityViewInfo(view);
		prmtCostedCompany.setSelector(comp);
		prmtCostedCompany.setDisplayFormat("$name$");
		prmtCostedCompany.setEditFormat("$number$");
		prmtCostedCompany.setCommitFormat("$number$");

		FDCClientUtils.setPersonF7(this.prmtApplier, this, null);
		
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("FDC_ISOTHERCOSTEDDEPT", null);
		HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
		if(hmAllParam.get("FDC_ISOTHERCOSTEDDEPT")!=null){
			isOtherCostedDept=Boolean.parseBoolean(hmAllParam.get("FDC_ISOTHERCOSTEDDEPT").toString());
		}else{
			isOtherCostedDept=true;
		}
		if(!isOtherCostedDept){
			setRespDept(prmtCostedDept, this, canSelectOtherOrgPerson ? null : cu);
			this.prmtCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
			this.contCostedCompany.setVisible(false);
			this.prmtCostedCompany.setRequired(false);
		}

		setBgEditState();

		if (this.contractBill != null) {
			Set id = new HashSet();
			for (int i = 0; i < this.contractBill.getContractType().getEntry().size(); i++) {
				if (this.contractBill.getContractType().getEntry().get(i).getPayContentType() != null) {
					id.add(this.contractBill.getContractType().getEntry().get(i).getPayContentType().getId().toString());
				}
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			if (id.size() > 0) {
				filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.INCLUDE));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			view.setFilter(filter);
			this.prmtPayContentType.setEntityViewInfo(view);
		} else {
			this.prmtPayContentType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.prmtPayContentType.setEnabled(false);
		}
		this.prmtPayContentType.setRequired(true);
		
		initBgEntryTable();
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		
		this.tblAttachement.checkParsed();
		KDWorkButton btnAttachment = new KDWorkButton();

		this.actionAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnAttachment = (KDWorkButton) this.contAttachment.add(this.actionAttachment);
		btnAttachment.setText("附件管理");
		btnAttachment.setSize(new Dimension(140, 19));
		
		this.pkbookedDate.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.pkbookedDate.setEnabled(false);
	}
	  public void setRespDept(KDBizPromptBox bizPromptBox, CoreUIObject ui, String cuId)
      {
/* <-MISALIGNED-> */ /*  69*/        bizPromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
/* <-MISALIGNED-> */ /*  71*/        EntityViewInfo view = new EntityViewInfo();
/* <-MISALIGNED-> */ /*  73*/        SorterItemCollection sorc = view.getSorter();
/* <-MISALIGNED-> */ /*  74*/        SorterItemInfo sort = new SorterItemInfo("number");
/* <-MISALIGNED-> */ /*  75*/        sorc.add(sort);/*  77*/        FilterInfo filter = new FilterInfo();

/*  79*/        FilterItemCollection fic = filter.getFilterItems();
/*  80*/        fic.add(new FilterItemInfo("isFreeze", new Integer(0)));
/*  81*/        fic.add(new FilterItemInfo("isSealUp", new Integer(0)));

/*  83*/        if(cuId != null)
/*  84*/            fic.add(new FilterItemInfo("CU.id", cuId));




/*  89*/        try
          {
/* <-MISALIGNED-> */ /*  89*/            Set authorizedOrgs = new HashSet();
/* <-MISALIGNED-> */ /*  90*/            Map orgs = (Map)ActionCache.get("FDCBillEditUIHandler.authorizedOrgs");
/* <-MISALIGNED-> */ /*  91*/            if(orgs == null)
/* <-MISALIGNED-> */ /*  92*/                orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null, null, null);/*  94*/            if(orgs != null)
              {/*  95*/                Set orgSet = orgs.keySet();
/*  96*/                for(Iterator it = orgSet.iterator(); it.hasNext(); authorizedOrgs.add(it.next()));
              }



/* 101*/            FilterInfo filterID = new FilterInfo();
/* 102*/            filterID.getFilterItems().add(new FilterItemInfo("id", authorizedOrgs, CompareType.INCLUDE));

/* 104*/            filter.mergeFilter(filterID, "and");
          }
/* 106*/        catch(Exception e)
          {/* 107*/            e.printStackTrace();
          }

/* 110*/        view.setFilter(filter);
/* 111*/        bizPromptBox.setEntityViewInfo(view);
/* 112*/        CostCenterF7 f7 = new CostCenterF7(ui);
/* 113*/        f7.showCheckBoxOfShowingAllOUs();
/* 114*/        f7.setRootUnitID(cuId);
/* 115*/        if(cuId != null)
/* 116*/            f7.setCurrentCUID(cuId);

/* 118*/        bizPromptBox.setSelector(f7);
/* 119*/        SelectorItemCollection sic = bizPromptBox.getSelectorCollection();
/* 120*/        if(sic == null)
          {/* 121*/            sic = new SelectorItemCollection();
/* 122*/            sic.add(new SelectorItemInfo("number"));
/* 123*/            sic.add(new SelectorItemInfo("name"));
/* 124*/            bizPromptBox.setSelectorCollection(sic);
          }
/* 126*/        sic.add(new SelectorItemInfo("isLeaf"));
/* 127*/        sic.add(new SelectorItemInfo("displayName"));
      }
	boolean isOtherCostedDept=true;
	protected Set getCostedDeptIdSet(CompanyOrgUnitInfo com) throws EASBizException, BOSException {
		if (com == null)
			return null;
		Set id = new HashSet();
		String longNumber = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(com.getId())).getLongNumber();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber + "!%", CompareType.LIKE));
		view.setFilter(filter);
		FullOrgUnitCollection col = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
		for (int i = 0; i < col.size(); i++) {
			if (col.get(i).getPartCostCenter() != null) {
				id.add(col.get(i).getId().toString());
			}
		}
		return id;
	}

	protected void btnSetTotal_actionPerformed(ActionEvent e) throws Exception {
		Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
		if (cell instanceof ICell) {
			Object value = ((ICell) cell).getValue();
			if (value != null) {
				try {
					txtAmount.setValue(new BigDecimal(value.toString()));
				} catch (NumberFormatException e1) {
					super.handUIException(e1);
				}
				// setAmount();
			}
		}
		cell = bindCellMap.get(PayRequestBillContants.CURPAIDLOCAL);
		if (cell instanceof ICell) {
			Object value = ((ICell) cell).getValue();
			if (value != null) {
				try {
					BigDecimal localamount = FDCHelper.toBigDecimal(value);
					txtBcAmount.setValue(localamount);
					if (localamount.compareTo(FDCConstants.ZERO) != 0) {
						localamount = localamount.setScale(2, BigDecimal.ROUND_HALF_UP);
						// 大写金额为本位币金额
						String cap = FDCClientHelper.getChineseFormat(localamount, false);
						// FDCHelper.transCap((CurrencyInfo) value, amount);
						txtcapitalAmount.setText(cap);
					} else {
						txtcapitalAmount.setText(null);
					}
				} catch (NumberFormatException e1) {
					super.handUIException(e1);
				}

			}
		}
		DataChangeEvent de = new DataChangeEvent(pkpayDate, new Date(), null);
		pkpayDate_dataChanged(de);
	}

	protected void loadBgEntryTable() {
		PayRequestBillBgEntryCollection col = editData.getBgEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtBgEntry.removeRows();
		for (int i = 0; i < col.size(); i++) {
			PayRequestBillBgEntryInfo entry = col.get(i);
			IRow row = this.kdtBgEntry.addRow();
			row.setUserObject(entry);
			if (entry.getExpenseType() != null) {
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
			if (entry.getBgItem() != null) {
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

	protected void storeBgEntryTable() {
		BigDecimal amount=FDCHelper.ZERO;
		editData.getBgEntry().clear();
		for (int i = 0; i < this.kdtBgEntry.getRowCount(); i++) {
			IRow row = this.kdtBgEntry.getRow(i);
			PayRequestBillBgEntryInfo entry = (PayRequestBillBgEntryInfo) row.getUserObject();
			entry.setSeq(i);
			entry.setExpenseType((ExpenseTypeInfo) row.getCell("expenseType").getValue());
			entry.setAccountView((AccountViewInfo) row.getCell("accountView").getValue());
			entry.setRequestAmount((BigDecimal) row.getCell("requestAmount").getValue());
			entry.setAmount((BigDecimal) row.getCell("amount").getValue());
			entry.setBgItem((BgItemInfo) row.getCell("bgItem").getValue());
			editData.getBgEntry().add(entry);
			
			amount=FDCHelper.add(amount,row.getCell("requestAmount").getValue());
		}
//		Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
//		if (cell != null && cell instanceof ICell) {
//			this.kdtEntrys.getRow(rowIndex).getCell(columnIndex).setValue(amount);
//			KDTEditEvent ee = new KDTEditEvent(cell);
//			ee.setColIndex(columnIndex);
//			ee.setRowIndex(rowIndex);
//			ee.setValue(amount);
//			try {
//				this.kdtEntrys_editStopped(ee);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	protected void initBgEntryTable() {
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton) this.contBgEntry.add(this.actionAddLine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) this.contBgEntry.add(this.actionInsertLine);
		btnInsertRowinfo.setText("插入行");
		btnInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) this.contBgEntry.add(this.actionRemoveLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));

		this.kdtBgEntry.checkParsed();

		CompanyOrgUnitInfo curCompany = company;
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox dialog = new AccountPromptBox(this, curCompany, view.getFilter(), false, true);
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
		f7Box.setDisplayFormat("$displayName$");
		f7Box.setEditFormat("$name$");
		f7Box.setCommitFormat("$name$");
		NewBgItemDialog bgItemDialog = new NewBgItemDialog(this);
		bgItemDialog.setMulSelect(false);
		bgItemDialog.setSelectCombinItem(false);
		f7Box.setSelector(bgItemDialog);
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtBgEntry.getColumn("bgItem").setEditor(f7Editor);
		this.kdtBgEntry.getColumn("bgItem").getStyleAttributes().setLocked(true);
		this.kdtBgEntry.getColumn("bgItem").getStyleAttributes().setHided(true);
	}

	protected void kdtBgEntry_editStopped(KDTEditEvent e) throws Exception {
		if (this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("requestAmount")) {
			if (this.kdtBgEntry.getRow(e.getRowIndex()).getCell("requestAmount").getValue() != null) {
				this.kdtBgEntry.getRow(e.getRowIndex()).getCell("amount").setValue(this.kdtBgEntry.getRow(e.getRowIndex()).getCell("requestAmount").getValue());
			}
		} else if (this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("bgItem")) {
			if (this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").getValue() instanceof BgItemObject) {
				BgItemObject bgItem = (BgItemObject) this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").getValue();
				if (bgItem != null) {
					if (!bgItem.getResult().get(0).isIsLeaf()) {
						FDCMsgBox.showWarning(this, "请选择明细预算项目！");
						this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").setValue(null);
					} else {
						this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").setValue(bgItem.getResult().get(0));
					}
				}
			}
			getBgAmount();
		} else if (this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("expenseType")) {
			ExpenseTypeInfo info = (ExpenseTypeInfo) this.kdtBgEntry.getRow(e.getRowIndex()).getCell("expenseType").getValue();
			if (info != null) {
				BgItemInfo bgItem = null;

				FDCSQLBuilder _builder = new FDCSQLBuilder();
				_builder.appendSql(" select bgItem.fid id from T_BG_BgItem bgItem where bgItem.fnumber in(");
				_builder.appendSql(" select map.fbgItemCombinKey from T_BG_BgControlItemMap map left join T_BG_BgControlScheme scheme on scheme.fid=map.FBgCtrlSchemeID");
				_builder.appendSql(" where scheme.fboName='com.kingdee.eas.fi.cas.app.PaymentBill' and scheme.fisValid=1 and scheme.fisSysDefault=0");
				_builder.appendSql(" and scheme.fcostCenterId='" + this.editData.getOrgUnit().getId().toString() + "'");
				_builder.appendSql(" and SUBSTRING(map.fbillItemCombinValue,charindex(':',map.fbillItemCombinValue)+1,length(map.fbillItemCombinValue)-charindex(':',map.fbillItemCombinValue)) ='"
						+ info.getNumber() + "'");
				_builder.appendSql(" group by map.fbgItemCombinKey)");
				final IRowSet rowSet = _builder.executeQuery();
				while (rowSet.next()) {
					bgItem = BgItemFactory.getRemoteInstance().getBgItemInfo(new ObjectUuidPK(rowSet.getString("id")));
					break;
				}
				if (info.getNumber().indexOf("YXFY") >= 0) {
					HashMap hmParamIn = new HashMap();
					hmParamIn.put("CIFI_SPLITCOSTACCOUNT", editData.getCurProject().getFullOrgUnit().getId().toString());
					HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
					if (hmAllParam.get("CIFI_SPLITCOSTACCOUNT") != null && Boolean.valueOf(hmAllParam.get("CIFI_SPLITCOSTACCOUNT").toString()).booleanValue()) {
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						SelectorItemCollection sic = new SelectorItemCollection();
						sic.add(new SelectorItemInfo("costAccount.name"));
						filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", editData.getContractId()));
						view.setFilter(filter);
						view.setSelector(sic);
						ContractCostSplitEntryCollection splitCol = ContractCostSplitEntryFactory.getRemoteInstance().getContractCostSplitEntryCollection(view);
						if (splitCol.size() > 0) {
							Set costAccount = new HashSet();
							for (int i = 0; i < splitCol.size(); i++) {
								if (splitCol.get(i).getCostAccount() != null)
									costAccount.add(splitCol.get(i).getCostAccount().getName());
							}
							if (costAccount.size() == 0) {
								FDCMsgBox.showWarning(this, "合同未拆分，不能进行付款申请单操作！");
								bgItem = null;
								this.kdtBgEntry.getRow(e.getRowIndex()).getCell("expenseType").setValue(null);
							} else if (!costAccount.contains(info.getName())) {
								FDCMsgBox.showWarning(this, "合同拆分对应成本科目与选择费用类别不对应，请重新选择！");
								bgItem = null;
								this.kdtBgEntry.getRow(e.getRowIndex()).getCell("expenseType").setValue(null);
							}
						} else {
							FDCMsgBox.showWarning(this, "合同未拆分，不能进行付款申请单操作！");
							bgItem = null;
							this.kdtBgEntry.getRow(e.getRowIndex()).getCell("expenseType").setValue(null);
						}
					}
				}
				this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").setValue(bgItem);
			} else {
				this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").setValue(null);
			}
			getBgAmount();
		}
		if (this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("requestAmount") || this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("amount")) {
			Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
			if (cell != null && cell instanceof ICell) {
				BigDecimal amount = TableUtils.getColumnValueSum(this.kdtBgEntry, "requestAmount");
				this.kdtEntrys.getRow(rowIndex).getCell(columnIndex).setValue(amount);
				e.setColIndex(columnIndex);
				e.setRowIndex(rowIndex);
				e.setValue(amount);
				this.kdtEntrys_editStopped(e);
			}
			TableUtils.getFootRow(this.kdtBgEntry, new String[] { "requestAmount", "amount" });
		}
	}

	private KDTextField txtBgAmount = null;

	protected void kdtBgEntry_tableSelectChanged(KDTSelectEvent e) throws Exception {
		getBgAmount();
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtBgEntry.addRow();
		PayRequestBillBgEntryInfo entry = new PayRequestBillBgEntryInfo();
		row.setUserObject(entry);
	}

	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = null;
		if (this.kdtBgEntry.getSelectManager().size() > 0) {
			int top = this.kdtBgEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(this.kdtBgEntry))
				row = this.kdtBgEntry.addRow();
			else
				row = this.kdtBgEntry.addRow(top);
		} else {
			row = this.kdtBgEntry.addRow();
		}
		PayRequestBillBgEntryInfo entry = new PayRequestBillBgEntryInfo();
		row.setUserObject(entry);
	}

	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		if (this.kdtBgEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtBgEntry)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (FDCMsgBox.isYes(FDCMsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")))) {
			int top = this.kdtBgEntry.getSelectManager().get().getBeginRow();
			int bottom = this.kdtBgEntry.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (this.kdtBgEntry.getRow(top) == null) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				this.kdtBgEntry.removeRow(top);
				Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
				if (cell != null && cell instanceof ICell) {
					BigDecimal amount = TableUtils.getColumnValueSum(this.kdtBgEntry, "requestAmount");
					this.kdtEntrys.getRow(rowIndex).getCell(columnIndex).setValue(amount);
					setAmountChange(amount);
					calAllCompletePrjAmt();
					if (isFirstLoad || (!isAutoComplete && !txtpaymentProportion.isRequired()))
						return;
					// 如果是0付款，则不填入已完工数据
					if (amount.compareTo(FDCHelper.ZERO) != 0)
						setCompletePrjAmt();
				}
				TableUtils.getFootRow(this.kdtBgEntry, new String[] { "requestAmount", "amount" });
			}
		}
	}

	protected void prmtCostedCompany_dataChanged(DataChangeEvent e) throws Exception {
		if(!isOtherCostedDept)return;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isBizUnit", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isSealUp", Boolean.FALSE));
		CompanyOrgUnitInfo com = (CompanyOrgUnitInfo) this.prmtCostedCompany.getValue();
		if (com != null) {
			if (!com.isIsLeaf()) {
				FDCMsgBox.showWarning(this, "请选择明细预算承担公司！");
				this.prmtCostedCompany.setValue(null);
				return;
			}
			Set id = getCostedDeptIdSet(com);
			if (this.prmtCostedDept.getValue() != null) {
				if (id.contains(((CostCenterOrgUnitInfo) prmtCostedDept.getValue()).getId().toString())) {
					return;
				} else {
					this.prmtCostedDept.setValue(null);
				}
			}
			filter.getFilterItems().add(new FilterItemInfo("id", getCostedDeptIdSet(com), CompareType.INCLUDE));
			this.prmtCostedDept.setEnabled(true);
		} else {
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

	private boolean isShowBgAmount = false;

	protected BigDecimal getMonthActPayAmount(String id, int year, int month) throws BOSException, SQLException {
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(famount) payAmount from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='" + id + "'");
		_builder.appendSql(" and famount is not null and year(fpayDate)=" + year + " and month(fpayDate)=" + month);
		final IRowSet rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if (rowSet.getBigDecimal("payAmount") != null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}

	protected BigDecimal getAccActOnLoadPayAmount(String contractId, String id, Date bizDate) throws BOSException, SQLException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(bizDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;

		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(famount) payAmount from T_CON_PayRequestBill where fstate in('2SUBMITTED','3AUDITTING','4AUDITTED') and fhasClosed=0 and famount is not null");
		_builder.appendSql(" and fid not in(select ffdcPayReqID from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='" + contractId + "') and FContractId='" + contractId + "'");
		_builder.appendSql(" and year(fbookedDate)='" + year + "' and month(fbookedDate)='" + month + "'");
		if (id != null) {
			_builder.appendSql(" and fid!='" + id + "'");
		}
		final IRowSet rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if (rowSet.getBigDecimal("payAmount") != null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}

	protected BigDecimal getAccActOnLoadBgAmount(String bgItemNumber, boolean isFromWorkFlow) throws BOSException, SQLException {
		if (this.prmtCostedDept.getValue() == null)
			return FDCHelper.ZERO;

		String bgComItem = "";
		Set bgComItemSet = new HashSet();
		bgComItemSet.add(bgItemNumber);
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select distinct t2.fformula fformula from T_BG_BgTemplateCtrlSetting t1 left join T_BG_BgTemplateCtrlSetting t2 on t1.fgroupno=t2.fgroupno where t1.FOrgUnitId ='"
				+ ((CostCenterOrgUnitInfo) this.prmtCostedDept.getValue()).getId().toString() + "'");
		_builder.appendSql(" and t1.fgroupno!='-1' and t2.fgroupno!='-1' and t1.fformula like '%" + bgItemNumber + "%' and t2.fformula not like '%" + bgItemNumber + "%'");
		IRowSet rowSet = _builder.executeQuery();

		while (rowSet.next()) {
			if (rowSet.getString("fformula") != null) {
				String formula = rowSet.getString("fformula");
				bgComItemSet.add(formula.substring(9, formula.indexOf(",") - 1));
			}
		}
		Object[] bgObject = bgComItemSet.toArray();
		for (int i = 0; i < bgObject.length; i++) {
			if (i == 0) {
				bgComItem = bgComItem + "'" + bgObject[i].toString() + "'";
			} else {
				bgComItem = bgComItem + ",'" + bgObject[i].toString() + "'";
			}
		}

		_builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(entry.frequestAmount-isnull(entry.factPayAmount,0))payAmount from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
		_builder.appendSql(" left join T_BG_BgItem bgItem on bgItem.fid=entry.fbgitemid ");
		_builder.appendSql(" where bill.fisBgControl=1 and bill.FCostedDeptId='" + ((CostCenterOrgUnitInfo) this.prmtCostedDept.getValue()).getId().toString() + "' and bgItem.fnumber in(" + bgComItem+")");
		if (isFromWorkFlow) {
			_builder.appendSql(" and bill.fstate in('3AUDITTING','4AUDITTED') ");
		} else {
			_builder.appendSql(" and bill.fstate in('2SUBMITTED','3AUDITTING','4AUDITTED') ");
		}
		_builder.appendSql(" and bill.fhasClosed=0 and bill.famount is not null");

		if (editData.getId() != null) {
			_builder.appendSql(" and bill.fid!='" + editData.getId().toString() + "'");
		}
		rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if (rowSet.getBigDecimal("payAmount") != null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}

	protected void getPayPlanValue() throws BOSException, SQLException, EASBizException {
		this.txtPayPlanValue.setText(null);
		if (!STATUS_ADDNEW.equals(getOprtState()) && !STATUS_EDIT.equals(getOprtState())) {
			return;
		}
		if (this.contractBill == null || this.contractBill.getProgrammingContract() == null || this.pkbookedDate.getValue() == null || PayReqUtils.isConWithoutTxt(this.editData.getContractId())) {
			return;
		}
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("CIFI_PAYPLAN", editData.getCurProject().getFullOrgUnit().getId().toString());
		HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
		if (hmAllParam.get("CIFI_PAYPLAN") == null || !Boolean.valueOf(hmAllParam.get("CIFI_PAYPLAN").toString()).booleanValue()) {
			return;
		}
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal actPayAmount = FDCHelper.ZERO;
		BigDecimal accActOnLoadPayAmount = FDCHelper.ZERO;

		Calendar cal = Calendar.getInstance();
		cal.setTime((Date) this.pkbookedDate.getValue());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;

		actPayAmount = getMonthActPayAmount(this.editData.getContractId(), year, month);
		String id = null;
		if (this.editData.getId() != null) {
			id = this.editData.getId().toString();
		}
		accActOnLoadPayAmount = getAccActOnLoadPayAmount(this.editData.getContractId(), id, (Date) this.pkbookedDate.getValue());

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("contractbill.id", this.editData.getContractId()));
		filter.getFilterItems().add(new FilterItemInfo("head.state", FDCBillStateEnum.CONFIRMED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("head.isLatest", Boolean.TRUE));

		SorterItemInfo bizDateSort = new SorterItemInfo("head.bizDate");
		bizDateSort.setSortType(SortType.DESCEND);
		view.getSorter().add(bizDateSort);
		view.getSelector().add("dateEntry.*");
		view.setFilter(filter);

		ProjectMonthPlanGatherEntryCollection col = ProjectMonthPlanGatherEntryFactory.getRemoteInstance().getProjectMonthPlanGatherEntryCollection(view);
		if (col.size() > 0) {
			ProjectMonthPlanGatherEntryInfo ppEntry = col.get(0);
			for (int i = 0; i < ppEntry.getDateEntry().size(); i++) {
				int comYear = ppEntry.getDateEntry().get(i).getYear();
				int comMonth = ppEntry.getDateEntry().get(i).getMonth();
				if (comYear == year && comMonth == month) {
					amount = ppEntry.getDateEntry().get(i).getAmount();
				}
			}
		}
		BigDecimal canAmount = amount.subtract(actPayAmount).subtract(accActOnLoadPayAmount);
		if (amount.toString().equals("0E-10")) {
			amount = FDCHelper.ZERO;
		}
		if (actPayAmount.toString().equals("0E-10")) {
			actPayAmount = FDCHelper.ZERO;
		}
		if (accActOnLoadPayAmount.toString().equals("0E-10")) {
			accActOnLoadPayAmount = FDCHelper.ZERO;
		}
		if (canAmount.toString().equals("0E-10")) {
			canAmount = FDCHelper.ZERO;
		}
		this.txtPayPlanValue.setText("本月付款计划金额：" + amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "   已付金额：" + actPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "   在途金额："
				+ accActOnLoadPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "   计划余额: " + canAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

	}

	protected void getBgAmount() throws EASBizException, BOSException, SQLException {
		if (isShowBgAmount) {

			String amount = null;
			if (this.kdtBgEntry.getSelectManager().size() > 0) {
				int top = this.kdtBgEntry.getSelectManager().get().getTop();
				if (isTableColumnSelected(this.kdtBgEntry)) {
					if (txtBgAmount != null) {
						txtBgAmount.setVisible(false);
					}
					return;
				}
				if (this.kdtBgEntry.getRow(top) == null) {
					if (txtBgAmount != null) {
						txtBgAmount.setVisible(false);
					}
					return;
				}
				if (this.kdtBgEntry.getRow(top).getCell("expenseType").getValue() != null && this.kdtBgEntry.getRow(top).getCell("bgItem").getValue() != null && this.prmtCostedDept.getValue() != null
						&& this.prmtCostedCompany.getValue() != null) {
					if (txtBgAmount == null) {
						txtBgAmount = new KDTextField();
						txtBgAmount.setEnabled(false);
						this.contBgEntry.add(txtBgAmount);
						txtBgAmount.setBounds(400, 2, 400, 18);
					}
					this.storeFields();
					BgItemInfo bgItem = (BgItemInfo) this.kdtBgEntry.getRow(top).getCell("bgItem").getValue();

					IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getRemoteInstance();
					BgCtrlResultCollection coll = iBgControlFacade.getBudget("com.kingdee.eas.fi.cas.app.PaymentBill", new BgCtrlParamCollection(), createTempPaymentBill());
					if (coll.size() > 0) {
						for (int i = 0; i < coll.size(); i++) {
							if (bgItem.getNumber().equals(coll.get(i).getItemCombinNumber())) {
								BigDecimal balanceAmount = FDCHelper.ZERO;
								if (coll.get(i).getBalance() != null) {
									balanceAmount = coll.get(i).getBalance();
								}
								BigDecimal balance = balanceAmount.subtract(getAccActOnLoadBgAmount(bgItem.getNumber(), true));
								amount = "部门:" + editData.getCostedDept().getName() + " 预算项目:" + bgItem.getName() + " 预算余额:" + balance.toString();
								break;
							}
						}
					}
					if (amount != null) {
						txtBgAmount.setVisible(true);
						txtBgAmount.setText(amount);
					} else {
						txtBgAmount.setVisible(false);
					}
				} else {
					if (txtBgAmount != null) {
						txtBgAmount.setVisible(false);
					}
				}
			}
		}
	}

	protected void cbIsInvoice_itemStateChanged(ItemEvent e) throws Exception {
		if (this.cbIsInvoice.isSelected()) {
			this.txtInvoiceNumber.setEnabled(true);
			this.txtInvoiceAmt.setEnabled(true);
			this.pkInvoiceDate.setEnabled(true);
		} else {
			this.txtInvoiceNumber.setEnabled(false);
			this.txtInvoiceAmt.setEnabled(false);
			this.pkInvoiceDate.setEnabled(false);
		}
	}

	protected void cbIsBgControl_itemStateChanged(ItemEvent e) throws Exception {
		if (this.cbIsBgControl.isSelected()) {
			if (!this.getOprtState().equals(OprtState.VIEW)) {
				if (this.kdtBgEntry.getRowCount() == 0) {
					IRow row = this.kdtBgEntry.addRow();
					PayRequestBillBgEntryInfo entry = new PayRequestBillBgEntryInfo();
					row.setUserObject(entry);
				}
				// if(this.contractBill!=null&&this.contractBill.getProgrammingContract()!=null){
				// SelectorItemCollection sel=new SelectorItemCollection();
				// sel.add("costEntries.costAccount.longNumber");
				// ProgrammingContractInfo
				// pc=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new
				// ObjectUuidPK(this.contractBill.getProgrammingContract().getId()),sel);
				// Set costAccount=new HashSet();
				// for(int i=0;i<pc.getCostEntries().size();i++){
				// costAccount.add(pc.getCostEntries().get(i).getCostAccount().getLongNumber());
				// }
				// row.getCell("bgItem").setValue(CostAccountWithBgItemFactory.getRemoteInstance().getBgItem(costAccount));
				// }
			}
			// this.actionAddLine.setEnabled(true);
			// this.actionInsertLine.setEnabled(true);
			// this.actionRemoveLine.setEnabled(true);
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.kdtBgEntry.getColumn("expenseType").getStyleAttributes().setLocked(false);
			this.kdtBgEntry.getColumn("accountView").getStyleAttributes().setLocked(false);
			this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setLocked(false);

			// this.prmtApplier.setEnabled(true);
			this.prmtApplierOrgUnit.setEnabled(true);
			this.prmtCostedCompany.setEnabled(true);
			this.prmtCostedDept.setEnabled(true);
			Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
			if (cell != null && cell instanceof ICell) {
				this.kdtEntrys.getRow(rowIndex).getCell(columnIndex).getStyleAttributes().setLocked(true);

				BigDecimal amount = TableUtils.getColumnValueSum(this.kdtBgEntry, "requestAmount");
				this.kdtEntrys.getRow(rowIndex).getCell(columnIndex).setValue(amount);
				KDTEditEvent ee = new KDTEditEvent(cell);
				ee.setColIndex(columnIndex);
				ee.setRowIndex(rowIndex);
				ee.setValue(amount);
				this.kdtEntrys_editStopped(ee);
			}
			if (this.prmtCostedCompany.getValue() != null) {
				this.prmtCostedDept.setEnabled(true);
			} else {
				this.prmtCostedDept.setEnabled(false);
			}
		} else {
			if (!this.getOprtState().equals(OprtState.VIEW)) {
				this.kdtBgEntry.removeRows();
			}

			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);

			this.kdtBgEntry.getColumn("expenseType").getStyleAttributes().setLocked(true);
			this.kdtBgEntry.getColumn("accountView").getStyleAttributes().setLocked(true);
			this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setLocked(true);
			// this.prmtApplier.setEnabled(false);
			this.prmtApplierOrgUnit.setEnabled(false);
			this.prmtCostedCompany.setEnabled(false);
			this.prmtCostedDept.setEnabled(false);
			Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
			if (cell != null && cell instanceof ICell) {
				this.kdtEntrys.getRow(rowIndex).getCell(columnIndex).getStyleAttributes().setLocked(false);
			}
		}
	}

	protected EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo) {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null) {
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}

	private void initPrmtPlanUnCon() throws BOSException {
		if (prmtPlanHasCon.getValue() != null) {
			contPlanHasCon.setVisible(true);
			contPlanUnCon.setVisible(false);
		} else if (prmtPlanUnCon.getValue() != null) {
			contPlanHasCon.setVisible(false);
			contPlanUnCon.setVisible(true);
		}

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("unConName");
		sic.add("parent.id");
		sic.add("parent.number");
		sic.add("parent.name");
		sic.add("parent.year");
		sic.add("parent.month");
		sic.add("parent.deptment.name");
		prmtPlanUnCon.setSelectorCollection(sic);

		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select DISTINCT uc.FID as fid from T_FNC_FDCDepConPayPlanUC as uc ");
		sql.appendSql(" left join T_FNC_FDCDepConPayPlanBill as head on head.FID = uc.FParentId ");
		sql.appendSql(" left join T_FNC_FDCDepConPayPlanUE as ue on ue.FParentId = uc.FID ");
		sql.appendSql(" where (head.FState = '4AUDITTED' or head.FState = '10PUBLISH') ");
		sql.appendSql(" and uc.FProjectID = ");
		sql.appendParam(editData.getCurProject().getId().toString());
		sql.appendSql(" and ue.FMonth >= ");
		sql.appendParam(firstDay);
		sql.appendSql(" and ue.FMonth <= ");
		sql.appendParam(lastDay);
		IRowSet rs = sql.executeQuery();
		Set ids = new HashSet();
		try {
			while (rs.next()) {
				ids.add(rs.getString("fid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (ids.size() < 1) {
			filter.getFilterItems().add(new FilterItemInfo("id", "11"));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", ids, CompareType.INCLUDE));
		}
		view.setFilter(filter);
		prmtPlanUnCon.setEntityViewInfo(view);
	}

	/**
	 * 选择待签订时，校验是否被其余不同合同下的申请单选择过<br>
	 * 如果已被选择过，提示不让选<br>
	 * 因为一个计划控制一个合同
	 */
	protected void prmtPlanUnCon_dataChanged(DataChangeEvent e) throws Exception {
		if (!STATUS_ADDNEW.equals(getOprtState()) && !STATUS_EDIT.equals(getOprtState())) {
			return;
		}
		FDCDepConPayPlanUnsettledConInfo plan = (FDCDepConPayPlanUnsettledConInfo) prmtPlanUnCon.getValue();
		String conID = editData.getContractId();
		if (plan != null && conID != null) {
			String planID = plan.getId().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("planUnCon.id", planID));
			filter.getFilterItems().add(new FilterItemInfo("contractId", conID, CompareType.NOTEQUALS));
			if (getBizInterface().exists(filter)) {
				FDCMsgBox.showWarning(this, "该待签订合同付款计划已被其他合同下的付款申请单引用，请重新选择。");
				prmtPlanUnCon.setDataNoNotify(e.getOldValue());
			}

			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("planUnCon.id");
			sic.add("planUnCon.UnConNumber");
			view.setSelector(sic);
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractId", conID));
			if (STATUS_EDIT.equals(getOprtState())) {
				filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
			}
			view.setFilter(filter);
			PayRequestBillCollection col = ((IPayRequestBill) getBizInterface()).getPayRequestBillCollection(view);
			if (col != null && col.size() > 0) {
				for (int i = 0; i < col.size(); i++) {
					FDCDepConPayPlanUnsettledConInfo info = col.get(i).getPlanUnCon();
					if (info != null && !info.getId().toString().equals(planID)) {
						String num = info.getUnConNumber();
						FDCMsgBox.showWarning(this, "该合同下存在付款申请单选择 ‘" + num + "’ 作为待签订合同滚动付款计划，请使用统一计划控制付款！");
						prmtPlanUnCon.setDataNoNotify(e.getOldValue());
						break;
					}
				}
			}
		}
	}

	private void reloadPartADeductDetails() throws Exception {
		if (partAParam) {
			tableHelper.reloadPartAValue(editData, null);
		} else {
			tableHelper.reloadPartAConfmValue(editData, null);
		}
	}

	/**
	 * 完工工程量来自进度系统工程量填报逻辑
	 * 
	 * @throws Exception
	 */
	private void fromProjectFill() throws Exception {
		detachListeners();
		String contractId = this.contractBill.getId().toString();
		BigDecimal sum = FDCHelper.ZERO;
		List idList = new ArrayList();
		if (isFillBillControlStrict) {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(" select fid, sum(FTotalQty) sum from t_fpm_projectfillbill where ");
			builder.appendParam("fcontractid", contractId);
			builder.appendSql(" and");
			builder.appendParam(" fstate", FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" and fobjectid is null");
			// builder.appendParam("fobjectid", "");
			builder.appendSql(" group by fid");
			IRowSet rs = builder.executeQuery();
			while (rs.next()) {
				idList.add(rs.getString("fid"));
				sum = FDCHelper.add(sum, rs.getBigDecimal("sum"));
			}
		} else {
			Set wbsIds = new HashSet();
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("wbs.id");
			view.setFilter(new FilterInfo());
			view.getFilter().getFilterItems().add(new FilterItemInfo("parent.contract.id", contractId));
			view.getFilter().getFilterItems().add(new FilterItemInfo("parent.isEnabled", Boolean.TRUE));
			ContractAndTaskRelEntryCollection entries = ContractAndTaskRelEntryFactory.getRemoteInstance().getContractAndTaskRelEntryCollection(view);
			for (int i = 0; i < entries.size(); ++i) {
				wbsIds.add(entries.get(i).getWbs().getId().toString());
			}
			EntityViewInfo taskView = new EntityViewInfo();
			taskView.getSelector().add("workLoad");
			taskView.setFilter(new FilterInfo());
			taskView.getFilter().getFilterItems().add(new FilterItemInfo("wbs.id", wbsIds, CompareType.INCLUDE));
			taskView.getFilter().getFilterItems().add(new FilterItemInfo("schedule.baseVer.isLatestVer", Boolean.TRUE));
			for (int i = 0; i < entries.size(); ++i) {
				wbsIds.add(entries.get(i).getWbs().getId().toString());
			}
			FDCScheduleTaskCollection tasks = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(taskView);
			for (int i = 0; i < tasks.size(); ++i) {
				sum = FDCHelper.add(sum, tasks.get(i).getWorkLoad());
			}
		}

		if (this.editData.getId() == null) {
			editData.setId(BOSUuid.create(editData.getBOSType()));
		}
		if (idList.size() > 0) {// 貌似没用 by zhiqiao_yang at 20110310
			FDCSCHUtils.updateTaskRef(null, isFromProjectFillBill, isFillBillControlStrict, false, contractId, new IObjectPK[] { new ObjectUuidPK(editData.getId()) }, idList);
		}
		if (sum.compareTo(FDCHelper.ZERO) == 0) {
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes().setLocked(false);
			txtpaymentProportion.setEditable(false);
			txtcompletePrjAmt.setEditable(false);
			txtpaymentProportion.setValue(FDCHelper.ZERO);
			txtcompletePrjAmt.setValue(FDCHelper.ZERO);
		} else {
			this.txtcompletePrjAmt.setValue(sum);
			BigDecimal allCompleteAmt = FDCHelper.toBigDecimal(txtAllCompletePrjAmt.getBigDecimalValue());
			this.txtAllCompletePrjAmt.setValue(FDCHelper.add(allCompleteAmt, sum));
			this.txtpaymentProportion.setValue(FDCHelper.ONE_HUNDRED);
			ICell local = (ICell) getDetailTable().getCell(rowIndex, columnIndex + 1);
			local.setValue(sum);
			BigDecimal rate = FDCHelper.toBigDecimal(this.txtexchangeRate.getBigDecimalValue());
			ICell ori = (ICell) getDetailTable().getCell(rowIndex, columnIndex);
			ori.setValue(FDCHelper.divide(sum, rate));
			btnInputCollect_actionPerformed(null);
		}
		attachListeners();
	}

	// 业务日期变化引起,期间的变化
	protected void bookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		String projectId = this.editData.getCurProject().getId().toString();
		fetchPeriod(e, pkbookedDate, cbPeriod, projectId, false);
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		
		this.prmtrealSupplier.setEnabled(false);
		if (editData != null) {
			String contractId = editData.getContractId();
			if (contractId == null || PayReqUtils.isConWithoutTxt(contractId)) {
				// 无文本不允许修改
				actionEdit.setEnabled(false);
				actionAdjustDeduct.setEnabled(false);
			} else {
				actionAdjustDeduct.setEnabled(true);
			}
		}
		if ((!getOprtState().equals(OprtState.ADDNEW) && !getOprtState().equals(OprtState.EDIT))) {
			final StyleAttributes sa = kdtEntrys.getStyleAttributes();
			sa.setLocked(true);
			sa.setBackground(noEditColor);
			btnInputCollect.setEnabled(false);
			kdtEntrys.setEnabled(false);
			// actionAudit.setEnabled(false);
			// actionUnAudit.setEnabled(false);
			actionRemove.setEnabled(false);
			// this.getActionMap().

		}
		if (editData == null || editData.getState() == null)
			return;
		if (editData.getState().equals(FDCBillStateEnum.AUDITTED) || editData.getState().equals(FDCBillStateEnum.AUDITTING)) {
			actionEdit.setEnabled(false);
			btnEdit.setEnabled(false);
		}

		menuTable1.setVisible(false);

		actionCopyFrom.setVisible(false);
		actionCopyFrom.setEnabled(false);
		menuItemCopyFrom.setVisible(false);
		menuItemCreateFrom.setVisible(false);
		// contsupplier.setEnabled(enabled);
		if (getOprtState() == AbstractCoreBillEditUI.STATUS_FINDVIEW) {
			actionUnClose.setEnabled(false);
			actionClose.setEnabled(false);
			// actionAudit.setEnabled(false);
			// actionUnAudit.setEnabled(false);
			actionTraceDown.setEnabled(false);
			actionTraceUp.setEnabled(false);
			btnUnclose.setEnabled(false);
			btnClose.setEnabled(false);
			btnAudit.setEnabled(false);
			btnUnAudit.setEnabled(false);
			btnTraceDown.setEnabled(false);
			btnTraceUp.setEnabled(false);
			// actionUnAudit.setVisible(false);
			// actionAudit.setVisible(false);

		} else if (getOprtState() == AbstractPayRequestBillEditUI.STATUS_CLOSE) {
			// if (editData.isHasClosed()) {
			// actionUnClose.setVisible(true);
			// } else {
			// actionClose.setVisible(true);
			// }
			actionClose.setVisible(false);
			actionUnClose.setVisible(false);
			// actionAudit.setVisible(false);
			// actionUnAudit.setVisible(false);
			actionTraceDown.setVisible(false);
			actionTraceUp.setVisible(false);
			actionExitCurrent.setVisible(true);
			actionSave.setEnabled(false);
			menuItemSave.setVisible(false);
			actionSave.setVisible(false);
			actionSubmit.setEnabled(false);
			actionEdit.setEnabled(false);
			actionEdit.setVisible(false);
			actionRemove.setEnabled(false);
			actionCopy.setEnabled(false);
			actionCopy.setVisible(false);
			actionCopyFrom.setVisible(false);
			actionAttachment.setVisible(false);
			actionCopyFrom.setEnabled(false);
			menuEdit.setVisible(false);
			menuTool.setVisible(false);
			lockUIForViewStatus();
		}

		if (getOprtState().equals(OprtState.ADDNEW))
			btnTraceUp.setEnabled(false);

		if (getOprtState() != OprtState.ADDNEW && getOprtState() != OprtState.EDIT) {
			mergencyState.setEnabled(false);

			chkIsPay.setEnabled(false);
			this.cbIsInvoice.setEnabled(false);
		}

		// actionAddLine.setEnabled(false);
		// actionRemoveLine.setEnabled(false);
		// actionInsertLine.setEnabled(false);

		if (PayReqUtils.isConWithoutTxt(editData.getContractId())) {
			actionAddNew.setEnabled(false);
			actionCopy.setEnabled(false);
		}
		setBgEditState();
		
	}

	public void setBgEditState() {
		if (this.cbIsBgControl.isSelected()) {
			if (getOprtState().equals(OprtState.VIEW)) {
				this.actionAddLine.setEnabled(false);
				this.actionInsertLine.setEnabled(false);
				this.actionRemoveLine.setEnabled(false);
			} else {
				// this.actionAddLine.setEnabled(true);
				// this.actionInsertLine.setEnabled(true);
				// this.actionRemoveLine.setEnabled(true);

				this.actionAddLine.setEnabled(false);
				this.actionInsertLine.setEnabled(false);
				this.actionRemoveLine.setEnabled(false);

				Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
				if (cell != null && cell instanceof ICell) {
					this.kdtEntrys.getRow(rowIndex).getCell(columnIndex).getStyleAttributes().setLocked(true);
				}
			}
			this.kdtBgEntry.getColumn("expenseType").getStyleAttributes().setLocked(false);
			this.kdtBgEntry.getColumn("accountView").getStyleAttributes().setLocked(false);
			this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setLocked(false);
			// this.prmtApplier.setEnabled(true);
			this.prmtApplierOrgUnit.setEnabled(true);
			this.prmtCostedCompany.setEnabled(true);
			this.prmtCostedDept.setEnabled(true);
		} else {
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);

			this.kdtBgEntry.getColumn("expenseType").getStyleAttributes().setLocked(true);
			this.kdtBgEntry.getColumn("accountView").getStyleAttributes().setLocked(true);
			this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setLocked(true);

			// this.prmtApplier.setEnabled(false);
			this.prmtApplierOrgUnit.setEnabled(false);
			this.prmtCostedCompany.setEnabled(false);
			this.prmtCostedDept.setEnabled(false);

			if (!getOprtState().equals(OprtState.VIEW)) {
				Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
				if (cell != null && cell instanceof ICell) {
					this.kdtEntrys.getRow(rowIndex).getCell(columnIndex).getStyleAttributes().setLocked(false);
				}
			}
		}
		if (this.cbIsBgControl.isSelected() && this.prmtCostedCompany.getValue() != null) {
			this.prmtCostedDept.setEnabled(true);
		} else {
			this.prmtCostedDept.setEnabled(false);
		}
		if(!isOtherCostedDept){
			this.prmtCostedDept.setEnabled(true);
		}
		if (this.cbIsInvoice.isSelected()) {
			this.txtInvoiceNumber.setEnabled(true);
			this.txtInvoiceAmt.setEnabled(true);
			this.pkInvoiceDate.setEnabled(true);
		} else {
			this.txtInvoiceNumber.setEnabled(false);
			this.txtInvoiceAmt.setEnabled(false);
			this.pkInvoiceDate.setEnabled(false);
		}
	}

	public void onShow() throws Exception {

		// 显示info的数据到表格
		super.onShow();

		String contractId = editData.getContractId();
		payReqContractId = contractId;
		if ((!getOprtState().equals(OprtState.ADDNEW) && !getOprtState().equals(OprtState.EDIT))) {
			final StyleAttributes sa = kdtEntrys.getStyleAttributes();
			sa.setLocked(true);
			sa.setBackground(noEditColor);
			btnInputCollect.setEnabled(false);
			kdtEntrys.setEnabled(false);
			kdtEntrys.getCell(this.rowIndex, this.columnIndex).getStyleAttributes().setLocked(true);

		}

		// Add by zhiyuan_tang 2010/07/30 处理在查看界面点修改，本次申请原币可用的BUG
		if (isPartACon && this.editData.getConfirmEntry().size() > 0) { // 有材料确认分录时，要置不可编辑
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes().setLocked(true);
		}

		// 工作流中可以复制合同名称
		if (editData != null && editData.getId() != null && FDCUtils.isRunningWorkflow(editData.getId().toString())) {
			kdtEntrys.setEnabled(true);
		}
		// new add by renliang at 2010-5-20
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				editAuditPayColumn();
			}
		});

		if (contractId == null || PayReqUtils.isConWithoutTxt(contractId)) {
			// 无文本不允许修改
			actionEdit.setEnabled(false);
			actionAdjustDeduct.setEnabled(false);
		} else {
			actionAdjustDeduct.setEnabled(true);
		}

		if (editData.getState() == null)
			return;
		if (editData.getState().equals(FDCBillStateEnum.AUDITTED) || editData.getState().equals(FDCBillStateEnum.AUDITTING)) {
			actionEdit.setEnabled(false);
			btnEdit.setEnabled(false);
		}

		menuTable1.setVisible(false);

		actionCopyFrom.setVisible(false);
		actionCopyFrom.setEnabled(false);
		menuItemCopyFrom.setVisible(false);
		menuItemCreateFrom.setVisible(false);

		if (getOprtState() == AbstractCoreBillEditUI.STATUS_FINDVIEW) {
			actionUnClose.setEnabled(false);
			actionClose.setEnabled(false);
			// actionAudit.setEnabled(false);
			// actionUnAudit.setEnabled(false);
			actionTraceDown.setEnabled(false);
			actionTraceUp.setEnabled(false);
			btnUnclose.setEnabled(false);
			btnClose.setEnabled(false);
			btnAudit.setEnabled(false);
			btnUnAudit.setEnabled(false);
			btnTraceDown.setEnabled(false);
			btnTraceUp.setEnabled(false);

		} else if (getOprtState() == AbstractPayRequestBillEditUI.STATUS_CLOSE) {
			// if (editData.isHasClosed()) {
			// actionUnClose.setVisible(true);
			// } else {
			// actionClose.setVisible(true);
			// }
			actionClose.setVisible(false);
			actionUnClose.setVisible(false);
			// actionAudit.setVisible(false);
			// actionUnAudit.setVisible(false);
			actionTraceDown.setVisible(false);
			actionTraceUp.setVisible(false);
			actionAttachment.setVisible(false);
			actionExitCurrent.setVisible(true);
			actionSave.setEnabled(false);
			actionSave.setVisible(false);
			menuItemSave.setVisible(false);
			actionSubmit.setEnabled(false);
			actionEdit.setEnabled(false);
			actionEdit.setVisible(false);
			actionRemove.setEnabled(false);
			actionCopy.setEnabled(false);
			actionCopy.setVisible(false);
			actionCopyFrom.setVisible(false);
			actionCopyFrom.setEnabled(false);
			menuEdit.setVisible(false);
			menuTool.setVisible(false);
			lockUIForViewStatus();
		}

		if (getOprtState() != OprtState.ADDNEW && getOprtState() != OprtState.EDIT) {
			mergencyState.setEnabled(false);
			chkIsPay.setEnabled(false);
			this.cbIsInvoice.setEnabled(false);
		}

		// onload也调用了,但是有些单元格表达式没有被计算,不得以再次调用,该死的table
		kdtEntrys.getScriptManager().runAll();

		// actionAddLine.setEnabled(false);
		// actionRemoveLine.setEnabled(false);
		// actionInsertLine.setEnabled(false);

		// 查看状态下也可以反审核
		// actionUnAudit.setEnabled(editData.getState().equals(
		// FDCBillStateEnum.AUDITTED));

		Boolean disableSplit = (Boolean) getUIContext().get("disableSplit");
		if (disableSplit != null && disableSplit.booleanValue()) {
			actionUnAudit.setVisible(false);
			actionAudit.setVisible(false);
		}
		btnContractExecInfo.setVisible(true);
		btnContractExecInfo.setEnabled(true);

		// 如果是无文本合同，将"合同执行信息"屏蔽掉 by Cassiel_peng 2009-10-2
		if (PayReqUtils.isConWithoutTxt(this.editData.getContractId())) {
			this.btnContractExecInfo.setVisible(false);
			this.menuItemContractExecInfo.setVisible(false);
		}

		// 月度请款已经将"关联成本科目付款计划"与"关联待签订合同"合并到一起，这两个菜单需要隐藏 by Cassiel_peng
		// 2009-10-28
		this.menuItemAssociateAcctPay.setVisible(false);
		this.menuItemAssociateUnSettled.setVisible(false);

		if (getOprtState() == AbstractCoreBillEditUI.STATUS_FINDVIEW) {
			try {
				if (editData != null && editData.getId() != null && FDCUtils.isRunningWorkflow(editData.getId().toString()) && FDCBillStateEnum.AUDITTING.equals(editData.getState())) {
					btnUnAudit.setEnabled(true);
					btnUnAudit.setVisible(true);
					btnAudit.setEnabled(false);
					btnAudit.setVisible(false);

				}
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 避免在新增单据（未作修改）直接关闭时，出现保存提示, 已由基本框架处理
		handleOldData();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("number");
		sic.add("state");
		sic.add("name");
		sic.add("payDate");
		sic.add("recBank");
		sic.add("recAccount");
		sic.add("moneyDesc");
		sic.add("contractNo");
		sic.add("description");
		sic.add("urgentDegree");
		sic.add("attachment");
		sic.add("bookedDate");
		sic.add("originalAmount");
		sic.add("amount");
		sic.add("exchangeRate");
		sic.add("process");
		sic.add("lastUpdateTime");

		sic.add("paymentProportion");
		sic.add("costAmount");
		sic.add("grtAmount");
		sic.add("capitalAmount");

		// 1
		sic.add("contractName");
		sic.add("changeAmt");
		sic.add("payTimes");
		// sic.add("curPlannedPayment");
		sic.add("curReqPercent");

		// 2
		sic.add("settleAmt");
		sic.add("curBackPay");
		sic.add("allReqPercent");

		//
		sic.add("guerdonOriginalAmt");
		sic.add("compensationOriginalAmt");

		// 合同内工程款
		sic.add("lstPrjAllPaidAmt");
		sic.add("lstPrjAllReqAmt");
		sic.add("projectPriceInContract");
		sic.add("projectPriceInContractOri");
		sic.add("prjAllReqAmt");

		// 预付款
		sic.add("prjPayEntry.lstAdvanceAllPaid");
		sic.add("prjPayEntry.lstAdvanceAllReq");
		sic.add("prjPayEntry.advance");
		sic.add("prjPayEntry.locAdvance");
		sic.add("prjPayEntry.advanceAllReq");
		sic.add("prjPayEntry.advanceAllPaid");

		// 甲供
		sic.add("lstAMatlAllPaidAmt");
		sic.add("lstAMatlAllReqAmt");
		sic.add("payPartAMatlAmt");
		sic.add("payPartAMatlOriAmt");
		sic.add("payPartAMatlAllReqAmt");

		// 实付
		sic.add("curPaid");

		// 5
		sic.add("contractPrice");
		sic.add("latestPrice");
		sic.add("payedAmt");
		sic.add("imageSchedule");
		sic.add("completePrjAmt");
		//
		sic.add("contractId");
		sic.add("hasPayoff");
		sic.add("hasClosed");
		sic.add("isPay");
		sic.add("auditTime");
		sic.add("createTime");
		sic.add("fivouchered");
		sic.add("sourceType");

		sic.add("isDifferPlace");
		sic.add("usage");

		// totalSettlePrice
		sic.add("totalSettlePrice");

		// 发票
		sic.add("invoiceNumber");
		sic.add("invoiceAmt");
		sic.add("allInvoiceAmt");
		sic.add("invoiceDate");

		sic.add(new SelectorItemInfo("entrys.amount"));
		sic.add(new SelectorItemInfo("entrys.payPartAMatlAmt"));
		sic.add(new SelectorItemInfo("entrys.projectPriceInContract"));
		sic.add(new SelectorItemInfo("entrys.parent.id"));
		sic.add(new SelectorItemInfo("entrys.paymentBill.id"));
		sic.add(new SelectorItemInfo("entrys.advance"));
		sic.add(new SelectorItemInfo("entrys.locAdvance"));

		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("orgUnit.displayName"));

		sic.add(new SelectorItemInfo("CU.name"));

		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("creator.name"));

		sic.add(new SelectorItemInfo("useDepartment.number"));
		sic.add(new SelectorItemInfo("useDepartment.name"));

		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));
		sic.add(new SelectorItemInfo("curProject.codingNumber"));

		sic.add(new SelectorItemInfo("currency.number"));
		sic.add(new SelectorItemInfo("currency.name"));
		// 付款申请单增加存储本位币币别，以方便预算系统能取到该字段值 by Cassiel_peng 2009-10-5
		sic.add(new SelectorItemInfo("localCurrency.id"));
		sic.add(new SelectorItemInfo("localCurrency.number"));
		sic.add(new SelectorItemInfo("localCurrency.name"));

		sic.add(new SelectorItemInfo("supplier.number"));
		sic.add(new SelectorItemInfo("supplier.name"));

		sic.add(new SelectorItemInfo("realSupplier.number"));
		sic.add(new SelectorItemInfo("realSupplier.name"));

		sic.add(new SelectorItemInfo("settlementType.number"));
		sic.add(new SelectorItemInfo("settlementType.name"));

		sic.add(new SelectorItemInfo("paymentType.number"));
		sic.add(new SelectorItemInfo("paymentType.name"));
		sic.add(new SelectorItemInfo("paymentType.payType.id"));

		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		sic.add(new SelectorItemInfo("contractBase.number"));
		sic.add(new SelectorItemInfo("contractBase.name"));

		// 计划项目
		sic.add(new SelectorItemInfo("planHasCon.contract.id"));
		sic.add(new SelectorItemInfo("planHasCon.contractName"));
		sic.add(new SelectorItemInfo("planHasCon.head.deptment.name"));
		sic.add(new SelectorItemInfo("planHasCon.head.year"));
		sic.add(new SelectorItemInfo("planHasCon.head.month"));

		sic.add(new SelectorItemInfo("planUnCon.unConName"));
		sic.add(new SelectorItemInfo("planUnCon.parent.deptment.name"));
		sic.add(new SelectorItemInfo("planUnCon.parent.year"));
		sic.add(new SelectorItemInfo("planUnCon.parent.month"));

		sic.add("isBgControl");
		sic.add("applier.*");
		sic.add("applierOrgUnit.*");
		sic.add("applierCompany.*");
		sic.add("costedDept.*");
		sic.add("costedCompany.id");
		sic.add("costedCompany.name");
		sic.add("costedCompany.number");
		sic.add("bgEntry.*");
		sic.add("bgEntry.accountView.*");
		sic.add("bgEntry.expenseType.*");
		sic.add("bgEntry.bgItem.*");
		sic.add("isInvoice");
		sic.add("payBillType.*");
		sic.add("payContentType.*");
		sic.add("person.*");
		return sic;
	}

	private void checkPrjPriceInConSettlePriceForSubmit() throws EASBizException, BOSException {
		storeFields();
		Set payReqBillSet = new HashSet();
		payReqBillSet.add(this.editData);
		boolean isCanPass = PayReqUtils.checkProjectPriceInContract(payReqBillSet, this.txtBcAmount.getBigDecimalValue());
		if (!isCanPass) {
			FDCMsgBox.showError("合同实付款不能大于合同结算价【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
			SysUtil.abort();
		}
	}

	/**
	 * 在合同最终结算后，付款申请单提交、审批时，如果合同实付款(不含本次) 加 本次付款申请单合同内工程款本期发生大于合同结算价给出相应提示 by
	 * cassiel_peng 2009-12-03
	 */
	private void checkPrjPriceInConSettlePriceForUnClose() throws Exception {
		storeFields();
		// 取得该合同的实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计
		BigDecimal totalPrjPriceInCon = FDCHelper.toBigDecimal(ContractClientUtils.getPayAmt(this.editData.getContractId()), 2);
		/**
		 * 在关闭请款单的时候,用合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 +
		 * 未关闭的付款申请单合同内工程款合计公式取数的时候需要注意：
		 * 对于当前正要关闭的请款单来说无论是否已经生成过对应的付款单都应该取的是请款单的而非付款单合同内工程款
		 * 。因为需要考虑反关闭成功之后某些逻辑已经不能满足
		 */
		BigDecimal actualPayAmt = totalPrjPriceInCon;
		BigDecimal payAmt = FDCHelper.ZERO;// 该请款单对应的付款单的合同内工程款
		EntityViewInfo _view = new EntityViewInfo();
		_view.getSelector().add("projectPriceInContract");
		FilterInfo _filter = new FilterInfo();
		_filter.getFilterItems().add(new FilterItemInfo("contractBillId", this.editData.getContractId()));
		_filter.getFilterItems().add(new FilterItemInfo("fdcPayReqID", this.editData.getId().toString()));
		_view.setFilter(_filter);
		PaymentBillCollection paymentColl = PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(_view);
		if (paymentColl != null && paymentColl.size() > 0) {// 生成过付款单
			for (Iterator iter = paymentColl.iterator(); iter.hasNext();) {
				PaymentBillInfo payment = (PaymentBillInfo) iter.next();
				payAmt = FDCHelper.toBigDecimal(FDCHelper.add(payAmt, payment.getProjectPriceInContract()));
			}
			actualPayAmt = FDCHelper.toBigDecimal(FDCHelper.add(this.editData.getProjectPriceInContract(), FDCHelper.subtract(totalPrjPriceInCon, payAmt)));
		}

		BigDecimal conSettPrice = FDCHelper.ZERO;// 合同结算价
		SelectorItemCollection _selector = new SelectorItemCollection();
		_selector.add("settleAmt");
		_selector.add("hasSettled");
		_selector.add("amount");
		ContractBillInfo contractBill = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(this.editData.getContractId())), _selector);
		if (contractBill != null) {
			conSettPrice = FDCHelper.toBigDecimal(contractBill.getSettleAmt(), 2);
		}
		if (actualPayAmt.compareTo(conSettPrice) > 1) {
			FDCMsgBox.showError("合同实付款不能大于合同结算价【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
			SysUtil.abort();
		}

		// 合同已最终结算后，反关闭时需要校验：进度款+结算款不能超过合同结算价-保修金 by cassiel_peng 2009-12-08
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if (contractBill.isHasSettled()) {
			BigDecimal settAmtSubtractGuarante = FDCHelper.ZERO;// 合同结算价-保修金
			builder.appendSql("select (fsettleprice-fqualityGuarante) as amount from t_con_contractsettlementbill where fcontractbillid=?");
			builder.addParam(this.editData.getContractId());
			IRowSet rowSet;
			try {
				rowSet = builder.executeQuery();
				try {
					if (rowSet.next()) {
						settAmtSubtractGuarante = rowSet.getBigDecimal("amount");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
			// 进度款+结算款(【已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】)
			BigDecimal processSettlementPrice = FDCHelper.ZERO;
			builder.clear();
			builder.appendSql("select sum(合同实付款) as 合同实付款  from  \n ");
			builder.appendSql("( \n ");
			builder.appendSql("(select req.FProjectPriceInContract as 合同实付款 from T_Con_PayRequestBill req \n ");
			builder.appendSql("inner join t_fdc_paymentType pType on req.fpaymenttype = pType.fid \n ");
			builder.appendSql("and req.FContractId=? and req.FHasClosed=0 \n ");
			builder.addParam(this.editData.getContractId());
			builder.appendSql("and pType.FPayTypeId in (?,?) )\n ");// 除去保修款类型不算
			builder.addParam(PaymentTypeInfo.progressID);
			builder.addParam(PaymentTypeInfo.settlementID);
			builder.appendSql("union all\n ");
			builder.appendSql("(select pay.FProjectPriceInContract as 合同实付款 from T_CAS_PaymentBill pay \n  ");
			builder.appendSql("inner join T_Con_PayRequestbill req on pay.FFdcPayReqID=req.FID \n ");
			builder.appendSql("inner  join t_fdc_paymentType payType on payType.fid = pay.FFdcPayTypeID \n ");
			builder.appendSql("and pay.FContractBillId=req.FContractId \n ");
			builder.appendSql("and req.FContractId=? and req.FHasClosed=1 \n ");
			builder.addParam(this.editData.getContractId());
			builder.appendSql("and payType.fpayTypeId in (?,?)) \n ");
			builder.addParam(PaymentTypeInfo.progressID);
			builder.addParam(PaymentTypeInfo.settlementID);
			builder.appendSql(") \n ");
			IRowSet rowSet1 = builder.executeQuery();
			if (rowSet1.next()) {
				processSettlementPrice = FDCHelper.toBigDecimal(rowSet1.getBigDecimal("合同实付款"), 2);
			}

			BigDecimal finalProcessSettlementPrice = processSettlementPrice;

			if (paymentColl != null && paymentColl.size() > 0) {// 生成过付款单
				finalProcessSettlementPrice = FDCHelper.toBigDecimal(FDCHelper.add(FDCHelper.subtract(processSettlementPrice, payAmt), this.editData.getProjectPriceInContract()));
			}
			if (finalProcessSettlementPrice.compareTo(settAmtSubtractGuarante) == 1) {
				FDCMsgBox.showError("进度款+结算款不能超过合同结算价-保修金！");
				SysUtil.abort();
			}
		}

		// 反关闭时需要校验：合同实付款不能大于合同最新造价 by cassiel_peng 2009-12-08
		BigDecimal latestPrice = FDCHelper.ZERO;// 合同最新造价
		if (contractBill.isHasSettled()) {// 如果已最终结算，合同最新造价取结算价
			latestPrice = conSettPrice;
		} else {// 未最终结算，取合同金额+变更金额
			// 合同的变更金额
			BigDecimal changeAmt = FDCHelper.ZERO;
			builder.clear();
			builder.appendSql("select FContractBillID,sum(fchangeAmount) as changeAmount from ( ");
			builder.appendSql("select FContractBillID,FBalanceAmount as fchangeAmount from T_CON_ContractChangeBill ");
			builder.appendSql("where FHasSettled=1 and ");
			builder.appendSql("FContractBillID=?");
			builder.addParam(this.editData.getContractId());
			builder.appendSql(" and (");
			builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
			builder.appendSql(" ) union all ");
			builder.appendSql("select FContractBillID,FAmount as fchangeAmount from T_CON_ContractChangeBill ");
			builder.appendSql("where FHasSettled=0 and ");
			builder.appendSql("FContractBillID=?");
			builder.addParam(this.editData.getContractId());
			builder.appendSql(" and (");
			builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
			builder.appendSql(" )) change group by FContractBillID");

			IRowSet rowSet2 = builder.executeQuery();
			if (rowSet2.next()) {
				changeAmt = FDCHelper.toBigDecimal(rowSet2.getBigDecimal("changeAmount"));
			}

			latestPrice = FDCHelper.toBigDecimal(FDCHelper.add(contractBill.getAmount(), changeAmt), 2);
		}
		if (actualPayAmt.compareTo(latestPrice) == 1) {
			if (contractBill.isHasSettled() || isControlCost) {// 若合同已经最终结算或者启用了参数
				// “
				// 累计请款超过合同最新造价严格控制
				// ”
				FDCMsgBox.showError("合同实付款不能大于合同最新造价【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
				SysUtil.abort();
			} else if (!contractBill.isHasSettled() && !isControlCost) {// 若合同还未最终结算且未启用参数
				// “
				// 累计请款超过合同最新造价严格控制
				// ”时
				FDCMsgBox.showWarning("合同实付款不能大于合同最新造价【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
			}
		}
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {

		// 保证在工作流中单据的状态不能改变by renliang 地下的判断方式真。。
		if (getUIContext().get("isFromWorkflow") != null && getUIContext().get("isFromWorkflow").toString().equals("true") && getOprtState().equals(OprtState.EDIT) && actionSave.isVisible()) {
			// /System.err.println("***getOprtState()***:"+getOprtState());
		} else {
			if (editData != null) {// 第一次保存时初始状态
				editData.setState(FDCBillStateEnum.SAVED);
			}
		}

		btnInputCollect_actionPerformed(null);
		super.actionSave_actionPerformed(e);
		kdtEntrys.getScriptManager().setAutoRun(true);
		kdtEntrys.getScriptManager().runAll();
		setBgEditState();
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		btnInputCollect_actionPerformed(null);
		if (isMoreSettlement) {
			check();
		}

		checkTempSmallerThanZero();
		// 检查拆分状态
		checkContractSplitState();
		// 为啥要在这里将单据状态显示地设置为"提交"呢？如果不设置为"提交",貌似合同内工程款保存会有误 by cassiel_peng
		// 2009-12-06
		editData.setState(FDCBillStateEnum.SUBMITTED);
		checkPrjPriceInConSettlePriceForSubmit();
		planAcctCtrl();
		super.actionSubmit_actionPerformed(e);
		kdtEntrys.getScriptManager().setAutoRun(true);
		kdtEntrys.getScriptManager().runAll();
		setBgEditState();
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {

		if (getOprtState() != OprtState.VIEW) {
			if (editData.getState() == null) {
				editData.setState(FDCBillStateEnum.SAVED);
			}
			storeFields();
		}
		if (editData == null || StringUtils.isEmpty(editData.getString("id"))) {
			FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.basedata.client.FdcResource", "cantPrint"));
			return;
		}

		// super.actionPrint_actionPerformed(e);
		KDNoteHelper appHlp = new KDNoteHelper();
		editData.setBoolean("isCompletePrjAmtVisible", contcompletePrjAmt.isVisible());
		editData.setBigDecimal("allCompletePrjAmt", txtAllCompletePrjAmt.getBigDecimalValue());
		PayRequestBillInfo billData = (PayRequestBillInfo) editData.clone();
		billData.setAllInvoiceAmt(this.txtAllInvoiceAmt.getBigDecimalValue()); // 为了让套打显示
		// 的“累计发票金额”与界面一致。
		appHlp.print("/bim/fdc/finance/payrequest", new PayRequestBillRowsetProvider(billData, bindCellMap, curProject, contractBill), SwingUtilities.getWindowAncestor(this));
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {

		if (getOprtState() != OprtState.VIEW) {
			if (editData.getState() == null) {
				editData.setState(FDCBillStateEnum.SAVED);
			}
			storeFields();
		}
		if (editData == null || StringUtils.isEmpty(editData.getString("id"))) {
			FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.basedata.client.FdcResource", "cantPrint"));
			return;
		}

		// super.actionPrintPreview_actionPerformed(e);
		KDNoteHelper appHlp = new KDNoteHelper();
		editData.setBoolean("isCompletePrjAmtVisible", contcompletePrjAmt.isVisible());
		editData.setBigDecimal("AllCompletePrjAmt", txtAllCompletePrjAmt.getBigDecimalValue());

		PayRequestBillInfo billData = (PayRequestBillInfo) editData.clone();
		billData.setAllInvoiceAmt(this.txtAllInvoiceAmt.getBigDecimalValue()); // 为了让套打显示
		// 的“累计发票金额”与界面一致。
		billData.setPayedAmt(totalPayAmtByReqId); // 为了让套打显示的“本申请单已付金额”与界面一致
		appHlp.printPreview("/bim/fdc/finance/payrequest", new PayRequestBillRowsetProvider(billData, bindCellMap, curProject, contractBill), SwingUtilities.getWindowAncestor(this));
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		boolean isView = (getOprtState() == OprtState.VIEW);
		super.actionCopy_actionPerformed(e);
		final Timestamp createTime = new Timestamp(System.currentTimeMillis());
		editData.setCreateTime(createTime);
		dateCreateTime.setValue(createTime);
		editData.setAuditor(null);
		editData.setAuditTime(null);
		editData.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		editData.setPaymentType(null);
		prmtPayment.setData(null);
		editData.getEntrys().clear();
		editData.setHasClosed(false);
		deductUIwindow = null;

		if (PayReqUtils.isContractBill(editData.getContractId())) {

			if (!contractBill.isHasSettled()) {
				editData.setPaymentProportion(contractBill.getPayScale());
			} else {
				if (!isSimpleFinancial) {
					editData.setPaymentProportion(new BigDecimal("100"));
					editData.setCompletePrjAmt(contractBill.getSettleAmt());
				}
				editData.setCostAmount(contractBill.getSettleAmt());
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.clear();
				builder.appendSql("select fqualityGuarante as amount from t_con_contractsettlementbill where fcontractbillid=");
				builder.appendParam(editData.getContractId());
				IRowSet rowSet = builder.executeQuery();
				if (rowSet.size() == 1) {
					rowSet.next();
					BigDecimal amount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
					editData.setGrtAmount(amount);
					txtGrtAmount.setValue(amount);
				}
			}
			tableHelper.updateDynamicValue(editData, contractBill, contractChangeBillCollection, paymentBillCollection);
			tableHelper.reloadDeductTable(editData, getDetailTable(), deductTypeCollection);
			tableHelper.updateGuerdonValue(editData, editData.getContractId(), guerdonOfPayReqBillCollection, guerdonBillCollection);
			tableHelper.updateCompensationValue(editData, editData.getContractId(), compensationOfPayReqBillCollection);

			reloadPartADeductDetails();
			((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACT)).setValue(null);
			((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI)).setValue(null);
			if (this.isAdvance()) {
				((ICell) bindCellMap.get(PayRequestBillContants.ADVANCE)).setValue(null);
				((ICell) bindCellMap.get(PayRequestBillContants.LOCALADVANCE)).setValue(null);
			}
			((ICell) bindCellMap.get(PayRequestBillContants.ADDPROJECTAMT)).setValue(null);
			((ICell) bindCellMap.get(PayRequestBillContants.PAYPARTAMATLAMT)).setValue(null);

			if (isView) {
				setTableCellColorAndEdit();
			}
		}
		// txtcapitalAmount.setEditable(false);
		btnSave.setEnabled(true);
		txtAmount.setValue(null);
		// this.mergencyState.setSelected(false);
		chkIsPay.setSelected(editData.isIsPay());
		editData.setSourceType(SourceTypeEnum.ADDNEW);
		setEditState();
		if (isFromProjectFillBill) {
			if (getOprtState().equals(OprtState.ADDNEW)) {
				fromProjectFill();
			}
			// txtcompletePrjAmt.setValue(FDCHelper.ZERO);
			// txtpaymentProportion.setValue(FDCHelper.ZERO);
			txtpaymentProportion.setEditable(true);
			txtcompletePrjAmt.setEditable(false);
		}
		// Add by zhiyuan_tang 2010/07/30 处理在查看界面点修改，本次申请原币可用的BUG
		if (isPartACon) {
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes().setLocked(true);
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());
		super.actionRemove_actionPerformed(e);

		if (getOprtState().equals(OprtState.ADDNEW)) {
			btnTraceUp.setEnabled(false);
			((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI)).getStyleAttributes().setLocked(false);
			((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI)).getStyleAttributes().setBackground(Color.WHITE);
			if (this.isAdvance()) {
				((ICell) bindCellMap.get(PayRequestBillContants.ADVANCE)).getStyleAttributes().setLocked(false);
				((ICell) bindCellMap.get(PayRequestBillContants.ADVANCE)).getStyleAttributes().setBackground(Color.WHITE);
			}
			btnInputCollect.setEnabled(true);
			kdtEntrys.setEditable(true);
			kdtEntrys.setEnabled(true);
		}
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkContractSplitState();
		this.checkParamForAddNew();
		boolean isView = (getOprtState() == OprtState.VIEW);
		CurrencyInfo currency = editData.getCurrency();
		SupplierInfo realSupplier = editData.getRealSupplier();

		confirmAmts = FDCHelper.ZERO;

		((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI)).getStyleAttributes().setLocked(false);
		// 如果直接新增,重新获得一下初始数据
		// fetchInitData();

		super.actionAddNew_actionPerformed(e);
		// 设置本位币
		prmtcurrency.setValue(null);
		prmtcurrency.setValue(currency);
		prmtrealSupplier.setValue(null);
		prmtrealSupplier.setValue(realSupplier);

		// 本位币处理
		CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
		BOSUuid srcid = currency.getId();
		if (baseCurrency != null) {
			if (srcid.equals(baseCurrency.getId())) {
				/*
				 * if (exchangeRate instanceof
				 * BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1) {
				 * FDCMsgBox.showWarning(this,"你选择的是本位币，但是汇率不等于1"); }
				 */
				txtexchangeRate.setValue(FDCConstants.ONE);
				txtexchangeRate.setEditable(false);
				// return;
			} else
				txtexchangeRate.setEditable(true);
		}

		if (PayReqUtils.isContractBill(editData.getContractId())) {
			tableHelper.updateDynamicValue(editData, contractBill, contractChangeBillCollection, paymentBillCollection);
			tableHelper.reloadDeductTable(editData, getDetailTable(), deductTypeCollection);
			// tableHelper.reloadGuerdonValue(editData, null);
			tableHelper.reloadCompensationValue(editData, null);
			if (partAParam) {
				tableHelper.reloadPartAValue(editData, null);
			} else {
				tableHelper.reloadPartAConfmValue(editData, null);
			}
			((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACT)).setValue(null);
			((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI)).setValue(null);
			if (this.isAdvance()) {
				// tableHelper.updateLstAdvanceAmt(editData, false);
				((ICell) bindCellMap.get(PayRequestBillContants.ADVANCE)).setValue(null);
				((ICell) bindCellMap.get(PayRequestBillContants.LOCALADVANCE)).setValue(null);
			}
			((ICell) bindCellMap.get(PayRequestBillContants.ADDPROJECTAMT)).setValue(null);
			((ICell) bindCellMap.get(PayRequestBillContants.PAYPARTAMATLAMT)).setValue(null);

			if (isView) {
				setTableCellColorAndEdit();
			}
		} else {
			FDCMsgBox.showInfo("当前付款申请单关联了无文本合同。无文本合同付款申请单不能直接生成，它由无文本合同自动生成");
			SysUtil.abort();
		}
		// txtcapitalAmount.setEditable(false);
		btnSave.setEnabled(true);
		deductUIwindow = null;

		this.cbIsInvoice.setEnabled(true);
//		this.chkIsPay.setEnabled(true);
		this.mergencyState.setEnabled(true);
//		prmtsupplier.setEnabled(false);
		txtcapitalAmount.setEnabled(false);

		if (isFromProjectFillBill) {

			if (getOprtState().equals(OprtState.ADDNEW)) {
				fromProjectFill();
			}
			txtcompletePrjAmt.setEditable(false);
			if (FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue()).compareTo(FDCHelper.ZERO) == 0) {
				txtpaymentProportion.setEditable(false);
			}
		}
		// 部分结算时，因结算值没带出不能提交
		if (getOprtState().equals(OprtState.EDIT) || getOprtState().equals(OprtState.ADDNEW)) {
			try {
				Map param = new HashMap();
				param.put("ContractBillId", contractBill.getId().toString());
				Map totalSettle = ContractFacadeFactory.getRemoteInstance().getTotalSettlePrice(param);
				if (totalSettle != null) {
					editData.setTotalSettlePrice((BigDecimal) totalSettle.get("SettlePrice"));
				} else {
					editData.setTotalSettlePrice(FDCConstants.ZERO);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			this.txtTotalSettlePrice.setValue(editData.getTotalSettlePrice());
		}
		// Add by zhiyuan_tang 2010/07/30 处理在查看界面点修改，本次申请原币可用的BUG
		if (isPartACon) {
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);

		// R110530-0639: 查看点修改，付款帐号值被清空了
		txtrecAccount.setText(editData.getRecAccount());
		/*
		 * if(editData.getState()!=null&&!editData.getState().equals(FDCBillStateEnum
		 * .SAVED)){ btnSave.setEnabled(false); }
		 */
		setEditState();
		// 付款单为暂估款类型时其他可录入金额字段仅发票金额可录入(对应的合同内工程款等其他款项不可录入)
		Object obj = prmtPayment.getValue();
		if (obj != null && obj instanceof PaymentTypeInfo) {
			String tempID = PaymentTypeInfo.tempID;// 暂估款
			PaymentTypeInfo type = (PaymentTypeInfo) obj;
			if (type.getPayType().getId().toString().equals(tempID)) {
				this.kdtEntrys.setEnabled(true);
				if (this.kdtEntrys.getCell(4, 4) != null) {
					this.kdtEntrys.getCell(4, 4).getStyleAttributes().setLocked(true);
				}
			}
		}
//		prmtsupplier.setEditable(false);
//		prmtsupplier.setEnabled(false);
		txtcapitalAmount.setEditable(false);
		prmtcurrency.setEnabled(false);
		setAmount();

		PaymentTypeInfo type = this.editData.getPaymentType();
		if (type != null && !type.getPayType().getId().toString().equals(PaymentTypeInfo.progressID)) {
			this.txtpaymentProportion.setEditable(false);
			this.txtcompletePrjAmt.setEditable(false);
			if (isSimpleFinancial && contractBill != null && contractBill.isHasSettled()) {
				this.txtpaymentProportion.setEditable(true);
				this.txtcompletePrjAmt.setEditable(true);
			}
		}
		if (isFromProjectFillBill) {
			txtcompletePrjAmt.setEditable(false);
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes().setLocked(true);
			if (FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue()).compareTo(FDCHelper.ZERO) == 0) {
				kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes().setLocked(false);
				txtpaymentProportion.setEditable(false);
			}
		}

		mergencyState.setEnabled(true);
//		chkIsPay.setEnabled(true);
		this.cbIsInvoice.setEnabled(true);
		deductUIwindow = null;
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())) {
			// 须保存警告
			FDCMsgBox.showWarning(this, getRes("beforeAttachment"));
			SysUtil.abort();
		}
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = getSelectBOID();
        
        if(boID == null)
        {
            return;
        } 
        if(this.editData.getContractId()!=null){
        	if (PayReqUtils.isConWithoutTxt(this.editData.getContractId())) {
        		boID=this.editData.getContractId();
        	}
        }
        boolean isEdit = false;
        if(STATUS_ADDNEW.equals(getOprtState())||STATUS_EDIT.equals(getOprtState()))
        {
            isEdit = true;
        }
        
        if(isEdit){
	        String orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
	        String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
	        String creatorId=this.editData.getCreator()!=null?this.editData.getCreator().getId().toString():null;
			
	        String uiName = "com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI";
			boolean hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
    				new ObjectUuidPK(userId),
    				new ObjectUuidPK(orgId),
    				new MetaDataPK(uiName),
    				new MetaDataPK("ActionAttamentCtrl") );
			//如果未启用参数则有权限的用户才能进行附件维护,如果已经启用了参数则制单人等于当前用户且有权限才能进行 维护
        	if(hasFunctionPermission){
        		if(creatorId!=null){
        			boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
        			if(creatorCtrl&&!creatorId.equals(userId)){
        				//制单人要等于当前用户才行
        				isEdit=false;
        			}
        		}
        	}else{
        		isEdit=false;
        	}
	    }
        acm.showAttachmentListUIByBoID(boID,this,isEdit);

        fillAttachmnetTable();
	}

	/*
	 * 几个添加的工具栏按钮
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkContractSplitState();
		// super.actionAudit_actionPerformed(e);
		if (isMoreSettlement) {
			check();
		}

		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED, "cantAudit");
		checkPrjPriceInConSettlePriceForSubmit();
		checkAmt(editData);
		// auditAndOpenPayment()代替audit()取得付款单的BOSUudi eric_wang 2010.05.19
		BOSUuid billId = PayRequestBillFactory.getRemoteInstance().auditAndOpenPayment(this.editData.getId());
		// PayRequestBillFactory.getRemoteInstance().audit(editData.getId());
		editData.setState(FDCBillStateEnum.AUDITTED);
		bizPromptAuditor.setValue(SysContext.getSysContext().getCurrentUserInfo());
		pkauditDate.setValue(DateTimeUtils.truncateDate(new Date()));
		FDCClientUtils.showOprtOK(this);
		actionAudit.setEnabled(false);
		actionAudit.setVisible(false);
		actionUnAudit.setVisible(true);
		actionUnAudit.setEnabled(true);
		actionEdit.setEnabled(false);// 禁用修改
		actionSubmit.setEnabled(false);
		actionRemove.setEnabled(false);
		// 打开付款单 eric_wang 2010.05.19
		try {
			if (isOpenPaymentBillEditUI()) {
				UIContext uiContext = new UIContext(this);
				if (billId != null) {
					// 取消弹出框 by Eric_Wang 2010.05.21
					// int result = FDCMsgBox.showConfirm2New(null,
					// "已经生成对应的付款单，是否打开付款单？");
					// if (JOptionPane.YES_OPTION == result) {
					uiContext.put(UIContext.ID, billId);
					IUIFactory uiFactory = null;
					uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
					IUIWindow dialog = uiFactory.create(PaymentBillEditUI.class.getName(), uiContext, null, OprtState.EDIT);
					dialog.show();
					// }
				}
			}
		} catch (Throwable e1) {
			this.handUIException(e1);
		}

	}

	/**
	 * 增加参数FDC801_ISOPENPAYMENTEDITUI是否开启判断 eric_wang 2010.05.19
	 * 
	 * @return
	 */
	private boolean isOpenPaymentBillEditUI() {
		boolean isOpenPaymentBillEditUI = false;
		try {
			isOpenPaymentBillEditUI = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_ISOPENPAYMENTEDITUI);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isOpenPaymentBillEditUI;
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (FDCBillStateEnum.AUDITTING.equals(editData.getState()) && STATUS_FINDVIEW.endsWith(getOprtState()) && editData.getId() != null && FDCUtils.isRunningWorkflow(editData.getId().toString())) {
			PayRequestBillFactory.getRemoteInstance().setUnAudited2Auditing(editData.getId());
			FDCMsgBox.showWarning("工作流中反审批至审批中状态成功！");
			destroyWindow();
			return;
		}
		// super.actionAudit_actionPerformed(e);
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, "cantUnAudit");
		PayRequestBillFactory.getRemoteInstance().unAudit(editData.getId());
		editData.setState(FDCBillStateEnum.SUBMITTED);

		bizPromptAuditor.setValue(null);
		pkauditDate.setValue(null);
		FDCClientUtils.showOprtOK(this);
		actionAudit.setEnabled(true);
		actionAudit.setVisible(true);
		actionUnAudit.setVisible(false);
		actionUnAudit.setEnabled(false);
		if (getOprtState() == OprtState.EDIT) {
			// 启用提交等
			actionSubmit.setEnabled(true);
			actionRemove.setEnabled(true);
		} else {
			actionEdit.setEnabled(true);
		}
	}

	public void actionTaoPrint_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionTaoPrint_actionPerformed(e);

	}

	public void actionAdjustDeduct_actionPerformed(ActionEvent e) throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())) {
			// 须保存警告
			FDCMsgBox.showWarning(getRes("beforeAdjustDeduct"));
			SysUtil.abort();
		}

		// 币别
		// CurrencyInfo cur = editData.getCurrency();
		// if (!cur.getId().toString().equals(baseCurrency.getId().toString()))
		// {
		// // 须保存警告
		// FDCMsgBox.showWarning("外币不允许调整款项");
		// SysUtil.abort();
		// }
		showSelectDeductList(e);
	}

	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())) {
			// 须保存警告
			FDCMsgBox.showWarning(getRes("beforeClose"));
			SysUtil.abort();
		}
		if (!editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
			FDCMsgBox.showError("当前单据的状态不能执行关闭操作！");
			return;
		}

		super.actionClose_actionPerformed(e);
		if (editData != null && editData.getId() != null && editData.isHasClosed()) {
			FDCMsgBox.showWarning(this, "付款申请单已经关闭，不需要再关闭");
			SysUtil.abort();
		}
		editData.setHasClosed(true);
		PayRequestBillFactory.getRemoteInstance().close(new ObjectUuidPK(editData.getId()));

		actionClose.setVisible(false);
		actionClose.setEnabled(false);
		actionUnClose.setVisible(true);
		actionUnClose.setEnabled(true);
		this.storeFields();
		this.initOldData(this.editData);
		if (isAutoComplete && !isSeparate) {
			// 已完工自动100%,且非工程量模式时,考虑实付金额与申请金额不等情况时 by hpw 2009-12-14
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("update t_con_payrequestbill set fcompleteprjamt=(select sum(flocalamount) from t_cas_paymentbill where t_con_payrequestbill.fid=ffdcpayreqid) where fid=? ");
			builder.addParam(editData.getId().toString());
			builder.execute();
		}

		FDCMsgBox.showInfo(getRes("closeSuccess"));
	}

	public void actionUnClose_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.getId() != null && !editData.isHasClosed()) {
			FDCMsgBox.showWarning(this, "付款申请单未关闭，不需要反关闭");
			SysUtil.abort();
		}
		if (!isSeparate && contractBill != null) {
			checkIsUnClose();
			checkPrjPriceInConSettlePriceForUnClose();
		}
		// 反关闭申请单时，如果累计实付款+未付申请金额超过了付款提示比例时，不允许反关闭，给出提示。
		if (this.editData.getContractId() == null) {
			return;
		}
		String contractId = this.editData.getContractId();
		BigDecimal totalpayAmountLocal = FDCHelper.ZERO;// 累计金额=实付款+申请未付数
		BigDecimal payAmtLocal = FDCHelper.ZERO;// 实付款
		BigDecimal noPayAmtLocal = FDCHelper.ZERO;// 申请未付数

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("contractId", contractId);
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE, CompareType.NOTEQUALS));

		view.getSelector().add("hasClosed");
		view.getSelector().add("number");
		view.getSelector().add("amount");
		view.getSelector().add("state");
		view.getSelector().add("originalamount");
		view.getSelector().add("completePrjAmt");
		view.getSelector().add("projectPriceInContract");
		// view.getSelector().add("costAmount");
		view.getSelector().add("entrys.paymentBill.billStatus");
		view.getSelector().add("entrys.paymentBill.amount");
		view.getSelector().add("entrys.paymentBill.localAmt");
		view.getSelector().add("paymentType.payType.id");
		PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
		for (int i = 0; i < c.size(); i++) {
			final PayRequestBillInfo info = c.get(i);
			// 未关闭的需要用请款单的总额减去该请款单对应付款单的已付款总额,需要注意一种特殊情况:已经关闭但是整好是本身这张单据需要做反关闭操作也要如此处理
			if ((!info.isHasClosed()) || (info.isHasClosed() && this.editData.getId() != null && info.getId().toString().equals(this.editData.getId().toString()))) {
				BigDecimal totalThisPayReq = FDCHelper.toBigDecimal(info.getAmount());
				BigDecimal temp = FDCHelper.ZERO;
				BigDecimal temp1 = FDCHelper.ZERO;
				int tempInt = info.getEntrys().size();
				for (int j = 0; j < tempInt; j++) {
					PaymentBillInfo payment = info.getEntrys().get(j).getPaymentBill();
					if (payment != null && payment.getBillStatus() == BillStatusEnum.PAYED) { // 并且该付款单已经付款
						temp = FDCHelper.add(temp, payment.getAmount());
					}
				}
				temp1 = FDCHelper.subtract(totalThisPayReq, temp);
				noPayAmtLocal = FDCHelper.add(noPayAmtLocal, temp1);
			} else {// 已关闭

			}
		}

		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql("select sum(FAmount) sumCount from t_cas_paymentbill where fcontractbillid=? ");
		_builder.addParam(contractId);
		final IRowSet rowSet1 = _builder.executeQuery();
		if (rowSet1.size() == 1) {
			rowSet1.next();
			payAmtLocal = FDCHelper.toBigDecimal(rowSet1.getBigDecimal("sumCount"));
		}

		totalpayAmountLocal = FDCHelper.add(payAmtLocal, noPayAmtLocal);
		_builder.clear();
		_builder.appendSql("select fpayPercForWarn from t_con_contractbill where fid=");
		_builder.appendParam(this.editData.getContractId());
		final IRowSet rowSet = _builder.executeQuery();
		BigDecimal payRate = FDCHelper.ZERO;
		BigDecimal payPercForWarn = FDCHelper.ZERO;
		BigDecimal conLastestPrice = FDCHelper.ZERO;
		Map map = new HashMap();
		if (rowSet.size() == 1) {
			rowSet.next();
			payPercForWarn = FDCHelper.toBigDecimal(rowSet.getBigDecimal("fpayPercForWarn"), 2);
		}
		// 合同最新造价
		map = FDCUtils.getLastAmt_Batch(null, new String[] { this.editData.getContractId() });
		if (map != null && map.size() == 1) {
			conLastestPrice = (BigDecimal) map.get(this.editData.getContractId());
		}
		payRate = FDCHelper.divide(FDCHelper.multiply(conLastestPrice, payPercForWarn), FDCHelper.ONE_HUNDRED);

		if (totalpayAmountLocal.compareTo(payRate) > 0) {
			String str = "本币：当前单据合同下的累计实付款+未付的申请单超过了付款提示比例:";
			str = str + "\n累计金额:" + totalpayAmountLocal + " 其中,实付数：" + FDCHelper.toBigDecimal(payAmtLocal, 2) + "  申请未付数:" + FDCHelper.toBigDecimal(noPayAmtLocal, 2);
			str = str + "\n付款提示比例金额：" + payRate + "(" + conLastestPrice + "*" + payPercForWarn + "%)";
			if ("0".equals(allPaidMoreThanConPrice())) {// 严格控制
				FDCMsgBox.showDetailAndOK(this, "超过付款提示比例,请查看详细信息", str, 1);
				SysUtil.abort();
			} else if ("1".equals(allPaidMoreThanConPrice())) {// 提示控制
				FDCMsgBox.showDetailAndOK(this, "超过付款提示比例,请查看详细信息", str, 1);
			}
		}

		BigDecimal amount = FDCHelper.toBigDecimal(editData.getAmount());
		BigDecimal paidAmount = FDCHelper.ZERO;
		PayRequestBillEntryInfo entryInfo;
		for (Iterator iter = editData.getEntrys().iterator(); iter.hasNext();) {
			entryInfo = (PayRequestBillEntryInfo) iter.next();
			paidAmount = paidAmount.add(entryInfo.getAmount());
		}

		// 无文本合同允许录入负数，允许多次付款，所以做比较时，改用绝对值比较，不影响正数合同的逻辑
		// added by Owen_wen 2011-05-18 R110130-022
		if (amount.abs().compareTo(paidAmount.abs()) <= 0) {
			FDCMsgBox.showWarning(getRes("canntUnClosed"));
			SysUtil.abort();
		}

		PayRequestBillFactory.getRemoteInstance().unClose(new ObjectUuidPK(editData.getId()));
		editData.setHasClosed(false);
		actionClose.setVisible(true);
		actionClose.setEnabled(true);
		actionUnClose.setVisible(false);
		actionUnClose.setEnabled(false);
		this.storeFields();
		this.initOldData(this.editData);
		if (isAutoComplete && !isSeparate) {
			// 已完工自动100%,且非工程量模式时,考虑实付金额与申请金额不等情况时 by hpw 2009-12-14
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("update t_con_payrequestbill set fcompleteprjamt=fprojectpriceincontract where fid=? ");
			builder.addParam(editData.getId().toString());
			builder.execute();
		}
		FDCMsgBox.showInfo(getRes("unCloseSuccess"));

	}

	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		// 取数据库内的最新数据
		editData = (PayRequestBillInfo) getBizInterface().getValue(new ObjectUuidPK(editData.getId()));
		switch (editData.getEntrys().size()) {
		case 0: {
			FDCMsgBox.showError(getRes("notraceDownBill"));
			SysUtil.abort();
			break;
			// super.actionTraceDown_actionPerformed(e);
		}
		case 1: {
			IUIWindow traceDownUIwindow = null;
			UIContext uiContext = new UIContext(this);
			String uiName = "com.kingdee.eas.fdc.finance.client.PaymentBillEditUI";
			String paymentId = editData.getEntrys().get(0).getPaymentBill().getId().toString();
			uiContext.put(UIContext.ID, paymentId);
			try {
				traceDownUIwindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiName, uiContext, null, OprtState.VIEW, WinStyle.SHOW_KINGDEELOGO);
			} catch (Exception e1) {
				handUIException(e1);
				break;
			}
			if (traceDownUIwindow != null) {
				traceDownUIwindow.show();
			}
			break;
		}
		default: {
			PayReqUtils.traceDownFDCPaymentBill(editData, this);
		}
		}
	}

	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		// TODO 上查合同付款计划
		// super.actionTraceUp_actionPerformed(e);
		if (editData != null && editData.getId() != null && PayReqUtils.isContractBill(editData.getContractId())) {
			String contractId = editData.getContractId();
			ContractPayPlanEditUI.showEditUI(this, contractId, "VIEW");
		} else {
			FDCMsgBox.showWarning(this, "没有合同计划");
		}
	}

	/**
	 * 显示调整款项列表界面
	 * 
	 * @param e
	 * @throws Exception
	 */
	private void showSelectDeductList(ActionEvent e) throws Exception {

		boolean canAdjust = checkCanSubmit();
		String state = canAdjust ? getOprtState() : OprtState.VIEW;

		// uiWindow=null;//暂时每次都实例一个UIWindow
		deductUIwindow = null; // 每次都实例一个UIWindow,否则会缓存UI，导致脏数据。数据的正确性远比性能更重要 by
		// zhiyuan_tang
		if (deductUIwindow == null) {
			UIContext uiContext = new UIContext(this);

			uiContext.put("contractBillId", editData.getContractId());
			uiContext.put("payRequestBillId", editData.getId().toString());
			uiContext.put("createTime", editData.getCreateTime().clone());
			uiContext.put("billState", state);
			uiContext.put("exRate", contractBill.getExRate());
			// FilterInfo filter = getDeductFilter();
			/*
			 * 过滤出DeductOfPayReqBillEntry内含有的相同合同且不在本申请单的扣款单, 已放入DeductListUI中
			 */
			// uiContext.put("defaultFilter", filter);
			// uiContext.put("selectSet", selectSet);
			try {
				deductUIwindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create("com.kingdee.eas.fdc.contract.client.DeductListUI", uiContext, null, null);

			} catch (Exception e1) {
				handUIException(e1);
			}

		} else {
			IUIObject myui = deductUIwindow.getUIObject();
			if (myui instanceof DeductListUI) {
				DeductListUI u = (DeductListUI) myui;
				myui.getUIContext().put("billState", state);
				u.beforeExcutQuery(null);
				u.execQuery();
			}

		}
		if (deductUIwindow == null) {
			return;
		}
		deductUIwindow.show();

		IUIObject myui = deductUIwindow.getUIObject();
		if (myui instanceof DeductListUI) {
			DeductListUI u = (DeductListUI) myui;
			if (u.isOkClicked()) {
				try {
					DeductOfPayReqBillFactory.getRemoteInstance().reCalcAmount(editData.getId().toString());
					tableHelper.reloadDeductTable(editData, getDetailTable(), deductTypeCollection);
					tableHelper.reloadGuerdonValue(editData, u.getGuerdonData());
					tableHelper.reloadCompensationValue(editData, u.getCompensationData());
					if (PayReqUtils.isContractBill(editData.getContractId())) {
						if (partAParam) {
							tableHelper.reloadPartAValue(editData, u.getPartAData());
						} else {
							tableHelper.reloadPartAConfmValue(editData, u.getPartAConfmData());
						}
					}
					tableHelper.updateDynamicValue(editData, contractBill, contractChangeBillCollection, paymentBillCollection);
					this.getDetailTable().getScriptManager().runAll();
				} catch (Exception e1) {
					handUIException(e1);
				}
			}
		}
	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI.class.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.contract.PayRequestBillFactory.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return new com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		PayRequestBillInfo objectValue = new PayRequestBillInfo();
		objectValue.setCreator((UserInfo) (SysContext.getSysContext().getCurrentUserInfo()));
		// objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		try {
			objectValue.setCreateTime(FDCDateHelper.getServerTimeStamp());
			// objectValue.setSignDate(FDCDateHelper.getServerTimeStamp());
			// objectValue.setPayDate(new
			// Date(FDCDateHelper.getServerTimeStamp().getTime()));
		} catch (BOSException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		}
		objectValue.setSourceType(SourceTypeEnum.ADDNEW);
		// objectValue.setPayDate(new Date());
		// 付款日期
		// objectValue.setPayDate(DateTimeUtils.truncateDate(new Date()));
		objectValue.setHasClosed(false);
		// 本位币
		objectValue.setCurrency(baseCurrency);
		String contractBillId = (String) getUIContext().get("contractBillId");
		if (contractBillId == null) {
			contractBillId = this.editData.getContractId();
		}
		if (contractBillId != null && contractBillId.trim().length() > 1) {
			// 无文本
			if (PayReqUtils.isConWithoutTxt(contractBillId)) {
				createNewData_WithoutTextContract(objectValue, contractBillId);
			} else {
				// 有文本
				if (BOSUuid.read(contractBillId).getType().equals(contractBill.getBOSType())) {
					createNewdata_Contract(objectValue, contractBillId);
				}
			}
		}

		// 已完工工程量金额
		objectValue.setCostAmount(FDCConstants.ZERO);

		// 组织
		objectValue.setOrgUnit(contractBill.getOrgUnit());
		// editData.setCU(curProject.getCU());

		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);

		// 是否需要付款
//		if (isNotEnterCAS)
//			objectValue.setIsPay(false);
//		else
			objectValue.setIsPay(true);

		if (isAutoComplete) {
			objectValue.setPaymentProportion(FDCConstants.ONE_HUNDRED);
		}

		// 计算累计结算加

		// 开票日期
		objectValue.setInvoiceDate(serverDate);

		objectValue.setIsBgControl(true);
		PayRequestBillBgEntryInfo entry = new PayRequestBillBgEntryInfo();
		objectValue.getBgEntry().add(entry);

		objectValue.setIsInvoice(true);
		// try {
		// BizCollBillBaseInfo baseInfo =
		// CommonUtilFacadeFactory.getRemoteInstance().forLoanBillCreateNewData();
		// objectValue.setApplierOrgUnit(baseInfo.getOrgUnit());
		// objectValue.setApplier(baseInfo.getApplier());
		// objectValue.setApplierCompany(baseInfo.getApplierCompany());
		// objectValue.setCostedDept(SysContext.getSysContext().getCurrentCostUnit());
		// objectValue.setCostedCompany(SysContext.getSysContext().getCurrentFIUnit());
		// } catch (EASBizException e) {
		// e.printStackTrace();
		// } catch (BOSException e) {
		// e.printStackTrace();
		// }
		objectValue.setApplier(SysContext.getSysContext().getCurrentUserInfo().getPerson());
		return objectValue;
	}

	private void reloadDynamicValue() {

		try {
			if (PayReqUtils.isContractBill(editData.getContractId())) {

				tableHelper.updateDynamicValue(editData, contractBill, contractChangeBillCollection, paymentBillCollection);
				tableHelper.reloadDeductTable(editData, getDetailTable(), deductTypeCollection);
				tableHelper.updateGuerdonValue(editData, editData.getContractId(), guerdonOfPayReqBillCollection, guerdonBillCollection);
				tableHelper.updateCompensationValue(editData, editData.getContractId(), compensationOfPayReqBillCollection);

				reloadPartADeductDetails();
				((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACT)).setValue(null);
				((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI)).setValue(null);
				if (this.isAdvance()) {
					((ICell) bindCellMap.get(PayRequestBillContants.ADVANCE)).setValue(null);
					((ICell) bindCellMap.get(PayRequestBillContants.LOCALADVANCE)).setValue(null);
				}
				((ICell) bindCellMap.get(PayRequestBillContants.ADDPROJECTAMT)).setValue(null);
				((ICell) bindCellMap.get(PayRequestBillContants.PAYPARTAMATLAMT)).setValue(null);
			}
			handleCodingRule();
		} catch (Exception e) {
			handUIException(e);
		}
	}

	private void createNewData_WithoutTextContract(com.kingdee.eas.fdc.contract.PayRequestBillInfo objectValue, String contractBillId) {
		ContractWithoutTextInfo withoutTextInfo;
		try {
			withoutTextInfo = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)));
			objectValue.setContractId(contractBillId);
			objectValue.setSource(withoutTextInfo.getBOSType().toString());
			objectValue.setAmount(withoutTextInfo.getAmount());// 原币金额
			objectValue.setContractPrice(withoutTextInfo.getAmount());//
			objectValue.setContractName(withoutTextInfo.getName());// 合同名
			objectValue.setContractNo(withoutTextInfo.getNumber());
			// 无文本合同名称
			// kdtEntrys.getCell(1,1).setValue(withoutTextInfo.getBillName());
			// 无文本合同造价
			// kdtEntrys.getCell(0,5).setValue(withoutTextInfo.getAmount());
			// 带出无文本合同收款单位
			objectValue.setSupplier((SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK((withoutTextInfo.getReceiveUnit().getId())))));
			objectValue.setRealSupplier((SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK((withoutTextInfo.getReceiveUnit().getId())))));
			// 带出无文本合同工程项目
			CurProjectInfo curProject = withoutTextInfo.getCurProject();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("name");
			selector.add("displayName");
			selector.add("codingNumber");
			selector.add("fullOrgUnit.name");
			curProject = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(curProject.getId()), selector);
			objectValue.setCurProject(curProject);
			objectValue.setCU(withoutTextInfo.getCU());
			objectValue.setCompletePrjAmt(withoutTextInfo.getAmount());
			objectValue.setCostAmount(withoutTextInfo.getAmount());

		} catch (Exception e) {
			handUIException(e);
		}

	}

	// 合同的申请单
	private void createNewdata_Contract(PayRequestBillInfo objectValue, String contractBillId) {
		try {
			// 合同号
			objectValue.setContractId(contractBillId);
			objectValue.setSource(contractBill.getBOSType().toString());
			objectValue.setContractNo(contractBill.getNumber());
			objectValue.setContractName(contractBill.getName());
			objectValue.setContractPrice(contractBill.getAmount());
			objectValue.setCurrency(contractBill.getCurrency());

			objectValue.setExchangeRate(contractBill.getExRate());
			// objectValue.setUseDepartment(contractBill.getRespDept());

			// 付款比例
			if (!contractBill.isHasSettled()) {
				objectValue.setPaymentProportion(contractBill.getPayScale());
			} else {
				if (!isSimpleFinancial) {
					objectValue.setPaymentProportion(new BigDecimal("100"));
					objectValue.setCompletePrjAmt(contractBill.getSettleAmt());
					txtcompletePrjAmt.setValue(contractBill.getSettleAmt());
				}
				objectValue.setCostAmount(contractBill.getSettleAmt());
				// 已经结算的付款比例
				objectValue.setGrtAmount(payScale);
				txtGrtAmount.setValue(payScale);
			}

			// 上期累计实付
			objectValue.setLstPrjAllPaidAmt(contractBill.getPrjPriceInConPaid());
			objectValue.setLstAddPrjAllPaidAmt(contractBill.getAddPrjAmtPaid());
			objectValue.setLstAMatlAllPaidAmt(contractBill.getPaidPartAMatlAmt());

			// 带出合同乙方
			objectValue.setSupplier(contractBill.getPartB());
			objectValue.setRealSupplier(contractBill.getPartB());
			CurProjectInfo curProject = contractBill.getCurProject();
			// 启用编码规则取长编码 改在服务端
			/*
			 * if (curProject.getLongNumber() == null ||
			 * curProject.getCodingNumber() == null) { SelectorItemCollection
			 * sic = new SelectorItemCollection(); sic.add("id");
			 * sic.add("longNumber"); sic.add("codingNumber"); String pk =
			 * curProject.getId().toString(); CurProjectInfo prj =
			 * CurProjectFactory.getRemoteInstance() .getCurProjectInfo(new
			 * ObjectUuidPK(pk), sic);
			 * curProject.setLongNumber(prj.getLongNumber());
			 * curProject.setCodingNumber(prj.getCodingNumber()); }
			 */

			// 根据实际收款单位，得到付款帐户和付款银行
			if (contractBill.getPartB() != null) {
				String supperid = contractBill.getPartB().getId().toString();
				PayReqUtils.fillBank(objectValue, supperid, curProject.getCU().getId().toString());
			}

			objectValue.setCurProject(curProject);
			objectValue.setCU(contractBill.getCU());

			// 调整款项选择并保存重新取数和数据库一致，去掉选择,提交新增时集合与数据库数据不一致，应再重新取一次 by hpw
			// 2009-08-1
			this.fetchInitData();
			tableHelper.updateDynamicValue(objectValue, contractBill, contractChangeBillCollection, paymentBillCollection);
			tableHelper.updateGuerdonValue(objectValue, contractBillId, guerdonOfPayReqBillCollection, guerdonBillCollection);
			tableHelper.updateCompensationValue(objectValue, contractBillId, compensationOfPayReqBillCollection);
			if (partAParam) {
				tableHelper.updatePartAValue(editData, contractBillId, partAOfPayReqBillCollection);
			} else {
				tableHelper.updatePartAConfmValue(editData, contractBillId, partAConfmOfPayReqBillCollection);
			}
			if (/* STATUS_ADDNEW.equals(getOprtState()) && */editData != null && !FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
				reloadDynamicValue();
			}

			if (contractBill.getContractType().getEntry().size() == 1 && contractBill.getContractType().getEntry().get(0).getPayContentType() != null) {
				objectValue.setPayContentType(contractBill.getContractType().getEntry().get(0).getPayContentType());
			}
		} catch (Exception e) {
			super.handUIException(e);
		}
	}

	private void setAutoNumber() {
		if (editData.getNumber() == null) {
			String sysNumber = null;
			try {
				if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit() != null) {
					// 应该传成本中心，与单据实体的主业务组织要对应
					sysNumber = com.kingdee.eas.framework.FrameWorkUtils.getCodeRuleClient(editData, ((com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo) com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentCostUnit()).getId().toString());

				}
			} catch (Exception e) {
				handUIException(e);
			}

			if (sysNumber != null && sysNumber.trim().length() > 0) {
				editData.setNumber(sysNumber);
				txtPaymentRequestBillNumber.setEnabled(false);
			} else {
				txtPaymentRequestBillNumber.setEnabled(true);
			}
		} else {
			if (editData.getNumber().trim().length() > 0) {
				txtPaymentRequestBillNumber.setText(editData.getNumber());
				// txtPaymentRequestBillNumber.setEnabled(false);
			} else {
				txtPaymentRequestBillNumber.setEnabled(true);
			}
		} // end if
	}

	protected boolean checkCanSubmit() throws Exception {
		if (isIncorporation && ((FDCBillInfo) editData).getPeriod() == null) {
			FDCMsgBox.showWarning(this, "启用成本月结期间不能为空，请在基础资料维护期间后，重新选择业务日期");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}

	protected void verifyInput(ActionEvent e) throws Exception {

		super.verifyInput(e);

		/*
		 * //付款类别的控制 1. 必须合同结算之后才能做结算款类别的付款申请单 2. 付了结算款之后才能付保修款（已控制） 3.
		 * 保修款总金额不能大于结算单上的质保金
		 */

		/*
		 * if(e.getSource()!=btnSubmit){
		 * if(editData.getNumber()==null||editData.getNumber().length()<1){
		 * FDCMsgBox.showWarning(this, getRes("NullNumber")); SysUtil.abort();
		 * }else{ return; } }
		 */
		/*
		 * 检查原币金额与单元格内的发生额实付金额是否一致
		 */
		if (!isSaveAction()) {
			Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
			if (cell instanceof ICell) {
				Object value = ((ICell) cell).getValue();
				if (value != null) {
					try {
						BigDecimal cellAmount = FDCHelper.toBigDecimal(value);
						BigDecimal amount = txtAmount.getBigDecimalValue();
						/*
						 * if (cellAmount.doubleValue() < 0 || amount == null ||
						 * amount.doubleValue() < 0 || (cellAmount.doubleValue()
						 * > 0 && cellAmount .compareTo(amount) != 0)) {
						 * FDCMsgBox.showWarning(this, getRes("verifyAmount"));
						 * SysUtil.abort(); }
						 */
						// 支持负数
						if (amount == null || (cellAmount.compareTo(amount) != 0)) {
							FDCMsgBox.showWarning(this, "原币金额与单元格内的发生额实付金额不符合，请检查后保存！");
							SysUtil.abort();
						}
						BigDecimal completePrj = txtcompletePrjAmt.getBigDecimalValue();
						Object obj = prmtPayment.getValue();
						PaymentTypeInfo type = null;
						if (obj != null && obj instanceof PaymentTypeInfo) {
							type = (PaymentTypeInfo) obj;
						}
						if (completePrj == null)
							completePrj = FDCHelper.ZERO;
						if (!isSimpleInvoice) {// 非发票校验
							if (FDCHelper.ZERO.compareTo(completePrj) == 0 && FDCHelper.ZERO.compareTo(amount) == 0) {
								String msg = "已完工工程量和实付金额不能同时为0！";
								if (isSimpleFinancial && type != null && !type.getPayType().getId().toString().equals(PaymentTypeInfo.tempID)) {
									// 暂估款的话不能有这个提示 by cassiel_peng 2010-03-23
									msg = "实付金额不能为0!";
								}
								if ((txtcompletePrjAmt.isRequired() && contcompletePrjAmt.isVisible()) && type != null && !type.getPayType().getId().toString().equals(PaymentTypeInfo.tempID)) {
									// 暂估款的话不能有这个提示"已完工工程量和实付金额不能同时为0！" by
									// cassiel_peng 2010-03-26
									FDCMsgBox.showWarning(this, msg);
									SysUtil.abort();
								}
							}
						}
						if (FDCHelper.ZERO.compareTo(amount) == 0) {
							String msg = null;
							// 预付款,发票为零时校验
							if (isAdvance() && FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(txtInvoiceAmt.getBigDecimalValue())) == 0) {
								if (FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex, columnIndex).getValue())) == 0
										&& FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex + 1, columnIndex).getValue())) == 0) {
									msg = "实付款与预付款不能同时为 0!";
									FDCMsgBox.showWarning(this, msg);
									SysUtil.abort();
								}

							} else if ((isInvoiceRequired || invoiceMgr) && FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(txtInvoiceAmt.getBigDecimalValue())) == 0) {
								FDCMsgBox.showWarning(this, "发票金额与原币金额不能同时为0，不允许提交!");
								SysUtil.abort();
							} else if (!(isInvoiceRequired || invoiceMgr) && (FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(txtInvoiceAmt.getBigDecimalValue())) == 0)) {// 非发票为0不能提交
								if (type != null && !type.getPayType().getId().toString().equals(PaymentTypeInfo.tempID)) {
									FDCMsgBox.showWarning(this, "实付金额不能为0!");
									SysUtil.abort();
								}
							}
						}

					} catch (NumberFormatException e1) {
						super.handUIException(e1);
					}
				}
			}
		}

		if (!isSaveAction()) {
			checkAmt(editData);
			checkCompletePrjAmt();
			if (this.isAdvance()) {
				tableHelper.checkAdvance(editData, this.bindCellMap);
			}
			// 发票是否必录
			if (isInvoiceRequired) {
				boolean isNotInput = false;
				if (txtInvoiceAmt.getBigDecimalValue() == null) {
					// 为零时可不录
					if (!FDCHelper.ZERO.equals(txtInvoiceAmt.getBigDecimalValue())) {
						isNotInput = true;
					}
				} else if (FDCHelper.ZERO.compareTo(txtInvoiceAmt.getBigDecimalValue()) != 0 && FDCHelper.isEmpty(txtInvoiceNumber.getText())) {
					isNotInput = true;
				}
				if (isNotInput) {
					FDCMsgBox.showWarning(this, "发票号与发票金额必须录入!");
					SysUtil.abort();
				}
			}
		}

		BigDecimal lastestPrice = FDCHelper.toBigDecimal(editData.getLatestPrice(), 2);
		if (txtpaymentProportion.isRequired() && txtcompletePrjAmt.isRequired()) {
			BigDecimal propAmt = txtpaymentProportion.getBigDecimalValue();
			BigDecimal completeAmt = FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue(), 2);
			if (propAmt != null) {
				if (propAmt.compareTo(FDCHelper.ZERO) <= 0 || propAmt.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
					// FDCMsgBox.showError(this, "付款比例必须大于0,小于等于100%");
					// SysUtil.abort();
					// } else if (FDCHelper.toBigDecimal(completeAmt).signum()
					// == 0) {
					// FDCMsgBox.showError(this, "已完工工程量必须大于0");
					// SysUtil.abort();
				} else if (!(FDCHelper.toBigDecimal(completeAmt).signum() == 0)) {
					// BigDecimal amount = FDCHelper
					// .toBigDecimal(((ICell) bindCellMap
					// .get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
					// .getValue());
					// BigDecimal amount = txtBcAmount.getBigDecimalValue();
					// BigDecimal tmpAmt =
					// amount.setScale(4,BigDecimal.ROUND_HALF_UP
					// ).divide(completeAmt,
					// BigDecimal.ROUND_HALF_UP).multiply(
					// FDCHelper.ONE_HUNDRED);
					// if (tmpAmt.compareTo(propAmt) != 0) {
					// FDCMsgBox.showError(this,
					// "付款比例＝原币金额/已完工工程量 *100% 关系不成立");
					// SysUtil.abort();
					// }
					Object amount = ((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACT)).getValue();
					if (amount == null) {
						FDCMsgBox.showError(this, "申请金额不允许为空！");
						SysUtil.abort();
					}
					if (isSaveAction()) {
						if (isSimpleFinancial) {
							if (FDCHelper.toBigDecimal(amount, 2).compareTo(lastestPrice) > 0) {
								int ok = FDCMsgBox.showConfirm2(this, "实付金额大于合同最新造价,是否保存？");
								if (ok != FDCMsgBox.OK) {
									SysUtil.abort();
								}
							}
						}
						if (completeAmt.compareTo(lastestPrice) > 0) {
							int ok = FDCMsgBox.showConfirm2(this, "已完工工程量金额大于合同最新造价,是否保存？");
							if (ok != FDCMsgBox.OK) {
								SysUtil.abort();
							}
						}
					}

				}
			}
		}

		if (isRealizedZeroCtrl) {
			PaymentTypeInfo type = (PaymentTypeInfo) prmtPayment.getValue();
			if (FDCHelper.isNullZero(txtTotalSettlePrice.getBigDecimalValue()) && type.getName() != null && !type.getName().equals("预付款")) {
				FDCMsgBox.showError(prmtPayment, "已实现产值为0只允许选择\"预付款\"！");
				SysUtil.abort();
			}
		}

		if (FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(txtAllInvoiceAmt.getBigDecimalValue())) == 1) {
			FDCMsgBox.showError(this, "累计发票金额不能小于零！");
			SysUtil.abort();
		}
		if (FDCHelper.toBigDecimal(txtAllInvoiceAmt.getBigDecimalValue()).setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(lastestPrice) == 1) {
			if (isOverrun) {
				int ok = FDCMsgBox.showConfirm2(this, "累计发票金额大于合同最新造价，是否提交?");
				if (ok != FDCMsgBox.OK) {
					SysUtil.abort();
				}
			} else {
				FDCMsgBox.showWarning(this, "累计发票金额不能超过合同最新造价！");
				SysUtil.abort();
			}
		}

		// // R110510-0022：工作流中提交审批决策结果时e.getActionCommand()为空，导致报中断
		// if (e != null && e.getActionCommand() != null &&
		// e.getActionCommand().endsWith("ActionSubmit")) {
		// // 通过合同月度滚动付款计划控制无文本合同
		// // 当参数为“严格控制时”和“提示控制”时，无文本合同编辑界面的“计划项目”为必填字段。当参数为“不控制”时，为非必填字段。
		// if ("严格控制".equals(CONTROLPAYREQUEST) ||
		// "提示控制".equals(CONTROLPAYREQUEST)) {
		// if (prmtPlanHasCon.getValue() == null && prmtPlanUnCon.getValue() ==
		// null && isCostSplitContract) {
		// FDCMsgBox.showWarning(this, "计划项目不能为空");
		// abort();
		// }
		// }
		// // 11.6.24 不进入动态成本，就不鸟这个控制 add by emanon
		// if (isCostSplitContract) {
		// // 当参数为"严格控制"时，无文本合同提交时校验：如果无文本合同的"本位币金额"大于"当月可用余额”，
		// // 则提示用户："申请金额大于计划可以余额，不能提交"，"确定"后终止提交操作。当参数为"提示控制"时，
		// // 无文本合同提交时校验：如果付款申请单的"本位币金额"大于"可用余额"，则提示用户：申请金额大于计划可以余额
		// // ，"确定"后提交成功，"取消"后放弃本次操作。当参数为"不控制"时，不做任何校验
		// BigDecimal bgBalance = getBgBalance();
		// BigDecimal bcAmount = txtBcAmount.getBigDecimalValue();
		// if ("严格控制".equals(CONTROLPAYREQUEST)) {
		// if (bcAmount.compareTo(bgBalance) > 0) {
		// FDCMsgBox.showWarning(this, "本次申请原币金额大于本期可用预算，不能提交");
		// abort();
		// }
		// }
		// if ("提示控制".equals(CONTROLPAYREQUEST)) {
		// if (bcAmount.compareTo(bgBalance) > 0) {
		// int result = FDCMsgBox.showConfirm2(this, "本次申请原币金额大于本期可用预算，是否提交？");
		// if (result != FDCMsgBox.OK) {
		// SysUtil.abort();
		// }
		// }
		// }
		// }
		// }
	}

	protected PaymentBillInfo createTempPaymentBill() throws BOSException {
		PaymentBillInfo pay = new PaymentBillInfo();
		for (int i = 0; i < this.kdtBgEntry.getRowCount(); i++) {
			PaymentBillEntryInfo entry = new PaymentBillEntryInfo();
			BigDecimal requestAmount = (BigDecimal) this.kdtBgEntry.getRow(i).getCell("requestAmount").getValue();

			entry.setAmount(requestAmount);
			entry.setLocalAmt(requestAmount);
			entry.setActualAmt(requestAmount);
			entry.setActualLocAmt(requestAmount);
			entry.setCurrency((CurrencyInfo) this.prmtcurrency.getValue());
			entry.setExpenseType((ExpenseTypeInfo) this.kdtBgEntry.getRow(i).getCell("expenseType").getValue());
			entry.setCostCenter((CostCenterOrgUnitInfo) this.prmtCostedDept.getValue());
			pay.getEntries().add(entry);
		}
		pay.setCompany((CompanyOrgUnitInfo) this.prmtCostedCompany.getValue());
		pay.setCostCenter((CostCenterOrgUnitInfo) this.prmtCostedDept.getValue());
		pay.setPayDate(FDCCommonServerHelper.getServerTimeStamp());
		pay.setCurrency((CurrencyInfo) this.prmtcurrency.getValue());

		return pay;
	}

	protected void verifyInputForSubmint() throws Exception {

		// 付款账号的校验需要自己做，特殊处理一下
		if (!FDCHelper.isEmpty(txtrecAccount.getText())) {
			txtrecAccount.setValue(txtrecAccount.getText());
		} else {
			txtrecAccount.setValue(null);
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtPayContentType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtuseDepartment);

		if (this.cbIsBgControl.isSelected()) {
			FDCClientVerifyHelper.verifyEmpty(this, this.prmtApplier);
			// FDCClientVerifyHelper.verifyEmpty(this, this.prmtApplierCompany);
			// FDCClientVerifyHelper.verifyEmpty(this, this.prmtApplierOrgUnit);
			FDCClientVerifyHelper.verifyEmpty(this, this.prmtCostedCompany);
			FDCClientVerifyHelper.verifyEmpty(this, this.prmtCostedDept);
			if (this.kdtBgEntry.getRowCount() == 0) {
				FDCMsgBox.showWarning(this, "费用清单不能为空！");
				SysUtil.abort();
			}
			// if (getUIContext().get("isFromWorkflow") != null
			// &&getUIContext().get("approveIsPass")!=null&&
			// getOprtState().equals(OprtState.EDIT)) {
			// this.editData.put("isFromWorkflow",getUIContext().get("isFromWorkflow").toString());
			// this.editData.put("approveIsPass",getUIContext().get("approveIsPass").toString());
			// }
			IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getRemoteInstance();
			BgCtrlResultCollection coll = iBgControlFacade.getBudget("com.kingdee.eas.fi.cas.app.PaymentBill", new BgCtrlParamCollection(), createTempPaymentBill());
			Map bgItemMap = new HashMap();
			boolean isWarning = true;
			for (int i = 0; i < this.kdtBgEntry.getRowCount(); i++) {
				IRow row = this.kdtBgEntry.getRow(i);

				if (row.getCell("expenseType").getValue() == null) {
					FDCMsgBox.showWarning(this, "费用清单费用类别不能为空！");
					this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("expenseType"));
					SysUtil.abort();
				}
				if (row.getCell("bgItem").getValue() == null) {
					FDCMsgBox.showWarning(this, "费用清单预算项目不能为空！");
					this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("bgItem"));
					SysUtil.abort();
				}
				if (row.getCell("requestAmount").getValue() == null) {
					FDCMsgBox.showWarning(this, "费用清单申请金额不能为空！");
					this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("requestAmount"));
					SysUtil.abort();
				}
				if (((BigDecimal) row.getCell("requestAmount").getValue()).compareTo(FDCHelper.ZERO) <= 0) {
					FDCMsgBox.showWarning(this, "费用清单申请金额必须大于0！");
					this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("requestAmount"));
					SysUtil.abort();
				}
				BgItemInfo bgItem = (BgItemInfo) row.getCell("bgItem").getValue();
				for (int j = 0; j < coll.size(); j++) {
					if (bgItem.getNumber().equals(coll.get(j).getItemCombinNumber())) {
						if (BgCtrlTypeEnum.NoCtrl.equals(coll.get(j).getCtrlType())) {
							break;
						}
						if (getUIContext().get("isFromWorkflow") != null && getUIContext().get("isFromWorkflow").toString().equals("true") && getUIContext().get("approveIsPass") != null
								&& getUIContext().get("approveIsPass").toString().equals("false") && getOprtState().equals(OprtState.EDIT)) {
							break;
						}
						BigDecimal balanceAmount = FDCHelper.ZERO;
						BigDecimal requestAmount = (BigDecimal) row.getCell("requestAmount").getValue();
						if (coll.get(j).getBalance() != null) {
							balanceAmount = coll.get(j).getBalance();
						}
						if (bgItemMap.containsKey(bgItem.getNumber())) {
							BigDecimal sumAmount = (BigDecimal) bgItemMap.get(bgItem.getNumber());
							balanceAmount = balanceAmount.subtract(sumAmount);
							bgItemMap.put(bgItem.getNumber(), sumAmount.add(requestAmount));
						} else {
							bgItemMap.put(bgItem.getNumber(), requestAmount);
						}
						BigDecimal balance = balanceAmount.subtract(getAccActOnLoadBgAmount(bgItem.getNumber(), true));
						if (requestAmount.compareTo(balance) > 0) {
							FDCMsgBox.showWarning(this, bgItem.getName() + "超过预算余额！");
							SysUtil.abort();
						}
						if (isWarning && (getUIContext().get("isFromWorkflow") == null || getUIContext().get("isFromWorkflow").toString().equals("false"))) {
							BigDecimal endBalance = balanceAmount.subtract(getAccActOnLoadBgAmount(bgItem.getNumber(), false));
							if (requestAmount.compareTo(endBalance) > 0) {
								if (FDCMsgBox.showConfirm2(this, "你发起的单据已申请（已确认+在途）的累计金额已超过预算；\n本次申请有可能不被通过，请确认是否提交？") != FDCMsgBox.YES) {
									SysUtil.abort();
								} else {
									isWarning = false;
								}
							}
						}
					}
				}
			}
		}
		if (this.contractBill != null) {
			Map param = new HashMap();
			param.put("isContract", Boolean.TRUE);
			param.put("contractId", this.editData.getContractId());
			if (editData.getId() != null) {
				param.put("billId", editData.getId().toString());
			}
			if (editData.getCurProject().getFullOrgUnit() != null) {
				param.put("orgId", editData.getCurProject().getFullOrgUnit().getId().toString());
			}
			param.put("bizDate", this.pkbookedDate.getValue());
			param.put("amount", this.txtAmount.getBigDecimalValue());
			Map result = ProjectMonthPlanGatherFactory.getRemoteInstance().checkPass(param);
			if (result != null && result.get("isPass") != null) {
				if (!((Boolean) result.get("isPass")).booleanValue() && result.get("warning") != null) {
					FDCMsgBox.showWarning(this, result.get("warning").toString());
					SysUtil.abort();
				}
			}
		}
//		if (this.contractBill != null&&this.contractBill.getPartB()!=null&&this.contractBill.getContractType()!=null){
//			ContractTypeInfo contractType=ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new ObjectUuidPK(this.contractBill.getContractType().getId()));
//			boolean isHas=false;
//			FilterInfo filter=new FilterInfo();
//			Set number=new HashSet();
//			if(contractType.getLongNumber().indexOf("07YX")>=0){
//				filter=new FilterInfo();
//				number=new HashSet();
//				number.add("003");
//				number.add("004");
//				number.add("005");
//				filter.getFilterItems().add(new FilterItemInfo("supplier.sysSupplier.id",this.contractBill.getPartB().getId().toString()));
//				filter.getFilterItems().add(new FilterItemInfo("evaluationType.number",number,CompareType.INCLUDE));
//				filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
//				if(MarketSupplierReviewGatherFactory.getRemoteInstance().exists(filter)){
//					isHas=true;
//				}
//			}else{
//				isHas=true;
//			}
////			number.add("003");
////			number.add("004");
////			number.add("005");
////			filter.getFilterItems().add(new FilterItemInfo("supplier.sysSupplier.id",this.contractBill.getPartB().getId().toString()));
////			filter.getFilterItems().add(new FilterItemInfo("evaluationType.number",number,CompareType.INCLUDE));
////			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
////			filter.getFilterItems().add(new FilterItemInfo("orgUnit.number",this.editData.getOrgUnit().getNumber().substring(0,5)));
////			if(SupplierReviewGatherFactory.getRemoteInstance().exists(filter)){
////				isHas=true;
////			}
//			if(!isHas){
//				BigDecimal amount=FDCHelper.ZERO;
//				BigDecimal payAmount=FDCHelper.ZERO;
//				FDCSQLBuilder builder = new FDCSQLBuilder();
//				if(this.contractBill.isHasSettled()){
//					builder.appendSql("select (fsettleprice-fqualityGuarante) as amount from t_con_contractsettlementbill where fcontractbillid='"+this.contractBill.getId().toString()+"' ");
//					IRowSet rowSet = builder.executeQuery();
//					if (rowSet.size() == 1) {
//						rowSet.next();
//						amount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"), 2);
//					}
//				}else{
//					amount=FDCHelper.subtract(this.contractBill.getAmount(), this.contractBill.getGrtAmount());
//				}
//				builder.clear();
//				builder.appendSql("select sum(famount) as payAmount from T_CON_PayRequestBill where FContractId='"+this.contractBill.getId().toString()+"' ");
//				if(this.editData.getId()!=null){
//					builder.appendSql("and fid!='"+this.editData.getId().toString()+"'");
//				}
//				IRowSet rowSet = builder.executeQuery();
//				if (rowSet.size() == 1) {
//					rowSet.next();
//					payAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("payAmount"), 2);
//				}
//				if(FDCHelper.add(this.txtAmount.getBigDecimalValue(), payAmount).compareTo(amount)>0){
//					FDCMsgBox.showWarning(this,"供应商未进行履约评估！");
//					SysUtil.abort();
//				}
//			}
//		}
		
		super.verifyInputForSubmint();
	}

	/**
	 * Description:币种
	 * 
	 * @author sxhong Date 2006-8-30
	 * @param e
	 * @throws Exception
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#currency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent)
	 */
	protected void currencydataChanged(DataChangeEvent e) throws Exception {

		super.currency_dataChanged(e);
		Object newValue = e.getNewValue();
		if (newValue instanceof CurrencyInfo) {
			if (isFirstLoad && !getOprtState().equals(OprtState.ADDNEW))
				return;
			if (e.getOldValue() != null && ((CurrencyInfo) e.getOldValue()).getId().equals(((CurrencyInfo) newValue).getId())) {
				// 设置汇率的值，在录入界面点新增时可能的情况
				return;
			}
			BOSUuid srcid = ((CurrencyInfo) newValue).getId();
			Date bookedDate = (Date) pkbookedDate.getValue();
			// txtexchangeRate.setValue(FDCClientHelper.getLocalExRateBySrcCurcy(
			// this, srcid,company,bookedDate));

			ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, srcid, company, bookedDate);

			int curPrecision = FDCClientHelper.getPrecOfCurrency(srcid);
			BigDecimal exRate = FDCHelper.ONE;
			int exPrecision = curPrecision;

			if (exchangeRate != null) {
				exRate = exchangeRate.getConvertRate();
				exPrecision = exchangeRate.getPrecision();
			}
			txtexchangeRate.setValue(exRate);
			txtexchangeRate.setPrecision(exPrecision);
			txtAmount.setPrecision(curPrecision);

			setAmount();

			setPropPrjAmount("amount", null);

		}
	}

	protected void realSupplier_dataChanged(DataChangeEvent e) throws Exception {
		super.realSupplier_dataChanged(e);
		Object newValue = e.getNewValue();
		if (newValue instanceof SupplierInfo) {
			// 首次加载
			if (isFirstLoad && !getOprtState().equals(OprtState.ADDNEW))
				return;
			// 用newValue.equalse(e.getOldValue()) 会出错,因为比较的是堆栈的值
			if ((e.getOldValue() instanceof SupplierInfo) && ((SupplierInfo) e.getOldValue()).getId().equals(((SupplierInfo) newValue).getId()) && !getOprtState().equals(OprtState.ADDNEW)) {
				return;
			}

			SupplierInfo supplier = (SupplierInfo) newValue;
			BOSUuid supplierid = supplier.getId();

			// 供应商的获取
			String supperid = supplierid.toString();
			PayReqUtils.fillBank(editData, supperid, curProject.getCU().getId().toString());
			txtrecAccount.setText(editData.getRecAccount());
			txtrecBank.setText(editData.getRecBank());
		}
	}

	/**
	 * 描述：根据供应商ID，设置收款帐号的过滤信息
	 */
	private void setRecAccountFilter() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SupplierInfo supplier = (SupplierInfo) prmtrealSupplier.getValue();
		String cuId = curProject.getCU().getId().toString();
		if (supplier != null) {
			filter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("CU.id", cuId));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		view.setFilter(filter);
		txtrecAccount.setEntityViewInfo(view);
		txtrecAccount.getQueryAgent().resetRuntimeEntityView();
	}

	/**
	 * 描述：根据供应商，银行名称，帐号来获取SupplierCompanyBankInfo对象
	 * 
	 * @param supplierId
	 * @param bankName
	 * @param account
	 * @return
	 */
	private SupplierCompanyBankInfo getSupplierCompanyBankInfoByAccount(String supplierId, String bankName, String account) {
		if (supplierId == null || bankName == null || account == null) {
			return null;
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("supplierCompanyInfo.supplier.id", supplierId));
		filter.getFilterItems().add(new FilterItemInfo("bank", bankName));
		filter.getFilterItems().add(new FilterItemInfo("bankAccount", account));
		view.setFilter(filter);
		SupplierCompanyBankCollection col = null;
		try {
			col = SupplierCompanyBankFactory.getRemoteInstance().getSupplierCompanyBankCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if (col != null && col.size() > 0) {
			SupplierCompanyBankInfo info = col.get(0);
			return info;
		}
		return null;
	}

	protected void txtrecAccount_willCommit(CommitEvent e) throws Exception {
		setRecAccountFilter();
	}

	protected void txtrecAccount_willShow(SelectorEvent e) throws Exception {
		setRecAccountFilter();
	}

	protected void txtrecAccount_dataChanged(DataChangeEvent e) throws Exception {

		if (e.getNewValue() != null && !e.getNewValue().equals(e.getOldValue()) && e.getNewValue() instanceof SupplierCompanyBankInfo) {
			SupplierCompanyBankInfo acctbank = (SupplierCompanyBankInfo) e.getNewValue();
			txtrecBank.setText(acctbank.getBank());
		}
	}

	public IObjectPK runSubmit() throws Exception {
		// 预算检查时从数据库中取数，所以先保存一下 by hpw 2011.6.20
		this.btnSave.doClick();
		// 预算控制
		// checkMbgCtrlBalance();已经集成在checkFdcBudget方法 2011.6.5
		checkFdcBudget();
		return super.runSubmit();
	}

	protected void txtAmount_dataChanged(DataChangeEvent e) throws Exception {
		super.txtAmount_dataChanged(e);
		setAmount();
	}

	protected void btnInputCollect_actionPerformed(ActionEvent e) throws Exception {
		// 填入汇总数操作
		super.btnInputCollect_actionPerformed(e);
		Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
		if (cell instanceof ICell) {
			Object value = ((ICell) cell).getValue();
			if (value != null) {
				try {
					txtAmount.setValue(new BigDecimal(value.toString()));
				} catch (NumberFormatException e1) {
					super.handUIException(e1);
				}
				// setAmount();
			}
		}
		cell = bindCellMap.get(PayRequestBillContants.CURPAIDLOCAL);
		if (cell instanceof ICell) {
			Object value = ((ICell) cell).getValue();
			if (value != null) {
				try {
					BigDecimal localamount = FDCHelper.toBigDecimal(value);
					txtBcAmount.setValue(localamount);
					if (localamount.compareTo(FDCConstants.ZERO) != 0) {
						localamount = localamount.setScale(2, BigDecimal.ROUND_HALF_UP);
						// 大写金额为本位币金额
						String cap = FDCClientHelper.getChineseFormat(localamount, false);
						// FDCHelper.transCap((CurrencyInfo) value, amount);
						txtcapitalAmount.setText(cap);
					} else {
						txtcapitalAmount.setText(null);
					}
				} catch (NumberFormatException e1) {
					super.handUIException(e1);
				}

			}
		}
		DataChangeEvent de = new DataChangeEvent(pkpayDate, new Date(), null);
		pkpayDate_dataChanged(de);
	}

	/**
	 * Description: 设置本位币金额,大小写等
	 * 
	 * @author sxhong Date 2006-9-6
	 */
	private void setAmount() {

		BigDecimal amount = (BigDecimal) txtAmount.getNumberValue();
		Object exchangeRate = txtexchangeRate.getNumberValue();
		if (amount != null) {
			Object value = prmtcurrency.getValue();
			if (value instanceof CurrencyInfo) {
				// String cap = FDCHelper.transCap((CurrencyInfo) value,
				// amount);
				// txtcapitalAmount.setText(cap);

				// 本位币处理
				CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
				CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
				BOSUuid srcid = ((CurrencyInfo) value).getId();
				if (baseCurrency != null) {
					if (srcid.equals(baseCurrency.getId())) {
						/*
						 * if (exchangeRate instanceof
						 * BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1)
						 * { FDCMsgBox.showWarning(this,"你选择的是本位币，但是汇率不等于1"); }
						 */
						txtBcAmount.setValue(amount);
						txtexchangeRate.setValue(FDCConstants.ONE);
						txtexchangeRate.setEditable(false);
						// return;
					} else
						txtexchangeRate.setEditable(true);
				}
			}

			BigDecimal localamount = (BigDecimal) txtBcAmount.getNumberValue();
			if (exchangeRate instanceof BigDecimal) {
				localamount = amount.multiply((BigDecimal) exchangeRate);
				txtBcAmount.setValue(localamount);
			}

			if (localamount != null && localamount.compareTo(FDCConstants.ZERO) != 0) {
				localamount = localamount.setScale(2, BigDecimal.ROUND_HALF_UP);
				// 大写金额为本位币金额
				String cap = FDCClientHelper.getChineseFormat(localamount, false);
				// FDCHelper.transCap((CurrencyInfo) value, amount);
				txtcapitalAmount.setText(cap);
			} else {
				txtcapitalAmount.setText(null);
			}

		} else {
			// 汇率处理
			Object value = prmtcurrency.getValue();
			if (value instanceof CurrencyInfo) {
				// 本位币处理
				CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
				CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
				BOSUuid srcid = ((CurrencyInfo) value).getId();
				if (baseCurrency != null) {
					if (srcid.equals(baseCurrency.getId())) {
						/*
						 * if (exchangeRate instanceof
						 * BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1)
						 * { FDCMsgBox.showWarning(this,"你选择的是本位币，但是汇率不等于1"); }
						 */
						txtBcAmount.setValue(amount);
						txtexchangeRate.setValue(FDCConstants.ONE);
						txtexchangeRate.setEditable(false);
						return;
					} else
						txtexchangeRate.setEditable(true);
				}

			}
			txtBcAmount.setValue(FDCConstants.ZERO);
			txtcapitalAmount.setText(null);
		}
	}

	/*
	 * 得到资源文件
	 */
	private String getRes(String resName) {
		return PayReqUtils.getRes(resName);
	}

	/**
	 * 设置表格单元格的可编辑状态及颜色
	 * 
	 * @author sxhong Date 2006-9-28
	 */
	private void setTableCellColorAndEdit() {
		tableHelper.setTableCellColorAndEdit(getDetailTable());
	}

	public void beforeActionPerformed(ActionEvent e) {

		Object source = e.getSource();
		super.beforeActionPerformed(e);
		if (getOprtState().equals(OprtState.VIEW) && source.equals(btnEdit)) {
			// setTableCellColorAndEdit();
			actionAddNew.setEnabled(true);
		}
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		Object source = e.getSource();
		if (source == btnNext || source == btnPre || source == btnFirst || source == btnLast || source == menuItemNext || source == menuItemPre || source == menuItemLast || source == menuItemFirst
				|| source == btnRemove || source == menuItemRemove) {
			// isFirstLoad=true;
			try {
				// isFirstLoad=true;
				// editData=(PayRequestBillInfo)getDataObject();
				// onLoad();
				PayReqUtils.setValueToCell(editData, bindCellMap);
				tableHelper.reloadDeductTable(editData, getDetailTable(), deductTypeCollection);
				tableHelper.reloadGuerdonValue(editData, null);
				tableHelper.reloadCompensationValue(editData, null);
				if (PayReqUtils.isContractBill(editData.getContractId())) {
					if (partAParam) {
						tableHelper.reloadPartAValue(editData, null);
					} else {
						tableHelper.reloadPartAConfmValue(editData, null);
					}
				}
				setOprtState(getOprtState());
			} catch (Exception e1) {
				handUIException(e1);
			}

			// if (editData.isHasClosed()) {
			// actionUnClose.setEnabled(true);
			// actionUnClose.setVisible(true);
			// actionClose.setEnabled(false);
			// actionClose.setVisible(false);
			// } else {
			// actionClose.setEnabled(true);
			// actionClose.setVisible(true);
			// actionUnClose.setEnabled(false);
			// actionUnClose.setVisible(false);
			// }
			actionClose.setVisible(false);
			actionUnClose.setVisible(false);
			deductUIwindow = null;
		}

		if (source == btnRemove || source == menuItemRemove) {
			// 获取具有对象更新锁的对象ID
			// idList = (IIDList) getUIContext().get(UIContext.IDLIST);
			if (idList.size() == 0) {
				actionClose.setEnabled(false);
				actionUnClose.setEnabled(false);
				actionTraceDown.setEnabled(false);
			}
		}
		try {
			reloadPartADeductDetails();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		kdtEntrys.getScriptManager().setAutoRun(true);
		kdtEntrys.getScriptManager().runAll();
	}

	protected KDTextField getNumberCtrl() {
		return txtPaymentRequestBillNumber;
	}

	/**
	 * 设置编辑时界面的状态,主要是对表格的设置
	 * 
	 * @author sxhong Date 2007-1-28
	 */
	private void setEditState() {
		String contractId = editData.getContractId();
		if (contractId == null || PayReqUtils.isConWithoutTxt(contractId)) {

			final StyleAttributes sa = kdtEntrys.getStyleAttributes();
			sa.setLocked(true);
			sa.setBackground(noEditColor);
			btnInputCollect.setEnabled(false);
			kdtEntrys.setEnabled(false);
		} else {
			btnInputCollect.setEnabled(false);
			setTableCellColorAndEdit();

			// Add by zhiyuan_tang 2010/07/30 处理在查看界面点修改，本次申请原币可用的BUG
			if (isPartACon) {
				kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes().setLocked(true);
			}

			actionAdjustDeduct.setEnabled(true);
		}
		mergencyState.setEnabled(true);
//		chkIsPay.setEnabled(true);
		this.cbIsInvoice.setEnabled(true);
		prmtPlanUnCon.setEnabled(true);

		setBgEditState();
	}

	protected void afterSubmitAddNew() {
		super.afterSubmitAddNew();
		try {
			if (PayReqUtils.isContractBill(editData.getContractId())) {
				tableHelper.updateGuerdonValue(editData, editData.getContractId(), guerdonOfPayReqBillCollection, guerdonBillCollection);
				if (isAdvance()) {
					tableHelper.updateLstAdvanceAmt(editData, false);
				}
			}
			handleCodingRule();
		} catch (Exception e) {
			handUIException(e);
		}
		if (OprtState.ADDNEW.equals(getOprtState())) {
			txtpaymentProportion.setValue(FDCHelper.ZERO);
			txtcompletePrjAmt.setValue(FDCHelper.ZERO);
		}
		calAllCompletePrjAmt();
		if (isFromProjectFillBill) {
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes().setLocked(false);
			txtpaymentProportion.setEditable(false);
			txtcompletePrjAmt.setEditable(false);
		}
	}

	/**
	 * 设置付款比例，已完工工程量与原币金额之间的关系： 已完工工程量金额＝原币金额÷付款比例
	 * 
	 * @author sxhong Date 2007-3-12
	 */
	private void setPropPrjAmount(String cause, DataChangeEvent e) {
		if (isFirstLoad || (!txtpaymentProportion.isRequired()) || (isSeparate && contractBill != null && contractBill.isIsCoseSplit()))
			return;

		// 用本位币进行计算
		BigDecimal amount = FDCHelper.toBigDecimal(((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACT)).getValue());// txtBcAmount

		BigDecimal paymentProp = txtpaymentProportion.getBigDecimalValue();
		BigDecimal completePrj = txtcompletePrjAmt.getBigDecimalValue();

		if (amount == null)
			amount = FDCHelper.ZERO;
		if (paymentProp == null)
			paymentProp = FDCHelper.ZERO;
		if (completePrj == null)
			completePrj = FDCHelper.ZERO;
		if (cause.equals("amount")) {
			if (isFromProjectFillBill) {
				if (FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue()).compareTo(FDCHelper.ZERO) == 0) {
					kdtEntrys.getCell(rowIndex, columnIndex).setValue(null);
					kdtEntrys.getCell(rowIndex, columnIndex + 1).setValue(null);
					// return;用else代替return，直接return很可能不运行后面的代码 by hpw
					// 2010-03-23
				} else {
					kdtEntrys.getCell(rowIndex, columnIndex).setValue(FDCHelper.divide(kdtEntrys.getCell(rowIndex, columnIndex + 1).getValue(), txtexchangeRate.getBigDecimalValue()));
				}
			} else {

				if (paymentProp.compareTo(FDCHelper.ZERO) == 0) {
					if (completePrj.compareTo(FDCHelper.ZERO) == 0) {
						// return;
					} else {
						txtpaymentProportion.setRequired(false);
						txtpaymentProportion.setValue(amount.setScale(4, BigDecimal.ROUND_HALF_UP).divide(completePrj, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
						txtpaymentProportion.setRequired(true && !isAutoComplete);
					}
				} else {
					txtcompletePrjAmt.setValue(amount.setScale(4, BigDecimal.ROUND_HALF_UP).divide(paymentProp, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
				}
			}

		} else if (cause.equals("completePrjAmt")) {
			if (completePrj.compareTo(FDCHelper.ZERO) == 0) {
				txtpaymentProportion.setValue(FDCHelper.ZERO);
				// return;
			} else {
				txtpaymentProportion.setRequired(false);
				txtpaymentProportion.setValue(amount.setScale(4, BigDecimal.ROUND_HALF_UP).divide(completePrj, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
				txtpaymentProportion.setRequired(true && !isAutoComplete);
			}
		} else {
			//
			if (isFromProjectFillBill) {
				if (FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue()).compareTo(FDCHelper.ZERO) == 0) {
					kdtEntrys.getCell(rowIndex, columnIndex).setValue(null);
					kdtEntrys.getCell(rowIndex, columnIndex + 1).setValue(null);
					// return;
				} else {
					BigDecimal loadAmt = FDCHelper.divide(FDCHelper.multiply(txtcompletePrjAmt.getBigDecimalValue(), paymentProp), FDCHelper.ONE_HUNDRED);
					kdtEntrys.getCell(rowIndex, columnIndex + 1).setValue(loadAmt);
					kdtEntrys.getCell(rowIndex, columnIndex).setValue(FDCHelper.divide(loadAmt, txtexchangeRate.getBigDecimalValue()));
				}
			} else {
				if (paymentProp.compareTo(FDCHelper.ZERO) == 0) {
					txtcompletePrjAmt.setValue(FDCHelper.ZERO);
					// return;
				} else {
					txtcompletePrjAmt.setValue(amount.setScale(4, BigDecimal.ROUND_HALF_UP).divide(paymentProp, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
				}
			}
		}
		if (isAutoComplete) {
			txtcompletePrjAmt.setValue(amount);
			editData.setCompletePrjAmt(amount);
		}
		calAllCompletePrjAmt();
	}

	/**
	 * 处理付款比例及已完工工程量的加载 在onLoad中调用
	 * 
	 * @author sxhong Date 2007-3-13
	 */
	private void initPaymentProp() {
		String contractId = editData.getContractId();
		if (contractId != null && !PayReqUtils.isConWithoutTxt(contractId)) {
			txtpaymentProportion.setRequired(true && !isAutoComplete);
			txtcompletePrjAmt.setRequired(true && !isAutoComplete && !(isSeparate && contractBill != null && contractBill.isIsCoseSplit()));
			try {

				if (!contractBill.isHasSettled() && editData.getPaymentProportion() == null) {
					// editData.setPaymentProportion(contractBill.getPayScale());
					// txtpaymentProportion.setValue(contractBill.getPayScale());
				} else if (contractBill.isHasSettled() && editData.getState() != FDCBillStateEnum.AUDITTED) {
					if (isSimpleFinancial) {
						txtpaymentProportion.setEditable(true);
						txtcompletePrjAmt.setEditable(true);
						txtpaymentProportion.setRequired(true);
						txtcompletePrjAmt.setRequired(true);

					} else {
						editData.setPaymentProportion(null);
						editData.setCompletePrjAmt(contractBill.getSettleAmt());
						txtcompletePrjAmt.setValue(FDCHelper.toBigDecimal(contractBill.getSettleAmt()));
						txtpaymentProportion.setEditable(false);
						txtcompletePrjAmt.setEditable(false);
						txtpaymentProportion.setRequired(false);
						txtcompletePrjAmt.setRequired(false);
					}

				}

			} catch (Exception e) {
				handUIException(e);
			}
		} else {
			txtpaymentProportion.setEditable(false);
			txtcompletePrjAmt.setEditable(false);
		}
	}

	/**
	 * 付款申请单累计额大于合同金额最新造价时提醒（提交，审批）
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 */
	private void checkAmt(PayRequestBillInfo billInfo) throws Exception {
		BigDecimal latestPrice = FDCHelper.toBigDecimal(billInfo.getLatestPrice(), 2);
		// if (billInfo.getLatestPrice() == null) {//支持结算为零值
		// return;
		// }
		/*********
		 * 付款申请单的币别，必须和合同的币别相同 2008-11-14 周勇 币别是否是本币 如果是本币则比较本币 如果是外币则比较原币
		 */
		boolean isBaseCurrency = true;
		CurrencyInfo cur = (CurrencyInfo) this.prmtcurrency.getValue();
		if (!cur.getId().toString().equals(baseCurrency.getId().toString())) {
			isBaseCurrency = false;
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("contractId", billInfo.getContractId());
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE, CompareType.NOTEQUALS));

		view.getSelector().add("hasClosed");
		view.getSelector().add("amount");
		view.getSelector().add("state");
		view.getSelector().add("originalamount");
		view.getSelector().add("completePrjAmt");
		view.getSelector().add("projectPriceInContract");
		view.getSelector().add("entrys.paymentBill.billStatus");
		view.getSelector().add("entrys.paymentBill.amount");
		view.getSelector().add("entrys.paymentBill.localAmt");
		view.getSelector().add("paymentType.payType.id");
		PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);

		BigDecimal total = FDCHelper.ZERO;
		BigDecimal completeTotal = FDCHelper.ZERO;
		BigDecimal projectPriceInContractTotal = FDCHelper.ZERO;
		BigDecimal totalpayAmount = FDCHelper.ZERO;// 累计实付款+未付的申请单
		BigDecimal totalpayAmountLocal = FDCHelper.ZERO;// 累计实付款+未付的申请单
		BigDecimal noPayAmt = FDCHelper.ZERO;
		BigDecimal noPayAmtLocal = FDCHelper.ZERO;
		BigDecimal payAmt = FDCHelper.ZERO;
		BigDecimal payAmtLocal = FDCHelper.ZERO;
		BigDecimal noKeepAmt = FDCHelper.ZERO;// 进度款之和
		BigDecimal noKeepAmtLocal = FDCHelper.ZERO;// 进度款之和
		for (int i = 0; i < c.size(); i++) {
			final PayRequestBillInfo info = c.get(i);
			if (info.getId().equals(billInfo.getId())) {
				// 分已存及未存两种状态,统计在后面处理
				continue;
			}
			// 统计原币金额 ( 合同内工程款 + 奖励 - 扣款 )
			total = total.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
			// 本期完成金额（已完工+奖励-索赔-扣款）
			completeTotal = completeTotal.add(FDCHelper.toBigDecimal(info.getCompletePrjAmt()));
			// 合同内工程款 （ 本期发生原币 ）
			if(!info.isHasClosed()){
				projectPriceInContractTotal = projectPriceInContractTotal.add(FDCHelper.toBigDecimal(info.getProjectPriceInContract()));
			}
			boolean isKeepAmt = false;
			if (info.getPaymentType() != null && info.getPaymentType().getPayType() != null && info.getPaymentType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID)) {
				isKeepAmt = true;
			}
			BigDecimal temp = FDCHelper.ZERO;
			BigDecimal tempLocal = FDCHelper.ZERO;
			BigDecimal _tempActuallyPayOriAmt = FDCHelper.ZERO;// 累计实付款原币临时变量
			BigDecimal _tempActuallyPayLocalAmt = FDCHelper.ZERO;// 累计实付款原币临时变量
			if (info.isHasClosed()) {
				if (info.getEntrys().size() > 0) {
					PaymentBillInfo payment = info.getEntrys().get(0).getPaymentBill();
					if (payment != null) {
						_tempActuallyPayOriAmt = payment.getAmount();
						_tempActuallyPayLocalAmt = payment.getLocalAmt();
					} else {
						_tempActuallyPayOriAmt = FDCHelper.toBigDecimal(FDCHelper.add(_tempActuallyPayOriAmt, info.getOriginalAmount()));
						_tempActuallyPayLocalAmt = FDCHelper.toBigDecimal(FDCHelper.add(_tempActuallyPayLocalAmt, info.getAmount()));
					}
				}
			} else {
				_tempActuallyPayOriAmt = FDCHelper.toBigDecimal(FDCHelper.add(_tempActuallyPayOriAmt, info.getOriginalAmount()));
				_tempActuallyPayLocalAmt = FDCHelper.toBigDecimal(FDCHelper.add(_tempActuallyPayLocalAmt, info.getAmount()));
			}
			// 如果付款申请单已经审批，即已经有关联的付款单
			if (FDCBillStateEnum.AUDITTED.equals(info.getState())) {
				int tempInt = info.getEntrys().size();
				for (int j = 0; j < tempInt; j++) {
					PaymentBillInfo payment = info.getEntrys().get(j).getPaymentBill();
					if (payment != null && payment.getBillStatus() == BillStatusEnum.PAYED) { // 并且该付款单已经付款
						temp = temp.add(FDCHelper.toBigDecimal(payment.getAmount()));
						tempLocal = tempLocal.add(FDCHelper.toBigDecimal(payment.getLocalAmt()));
						payAmt = payAmt.add(FDCHelper.toBigDecimal(payment.getAmount()));
						payAmtLocal = payAmtLocal.add(FDCHelper.toBigDecimal(payment.getLocalAmt()));
					} else if (payment != null && payment.getBillStatus() != BillStatusEnum.PAYED) {// 未付款
						// ，
						// 需要记录一下申请未付金额
						if(!info.isHasClosed()){
							noPayAmt = FDCHelper.add(noPayAmt, info.getOriginalAmount());
							noPayAmtLocal = FDCHelper.add(noPayAmtLocal, info.getAmount());
						}
					}
					if (temp.compareTo(FDCHelper.ZERO) == 0) {
						temp = FDCHelper.toBigDecimal(info.getOriginalAmount());
					}
					if (tempLocal.compareTo(FDCHelper.ZERO) == 0) {
						tempLocal = FDCHelper.toBigDecimal(info.getAmount());
					}
				}
				if (!info.isHasClosed()) {// 已关闭的不应该包含进去
					// 请款单的请款金额 - 该请款单对应付款单的付款金额 = 申请未付金额
					noPayAmt = FDCHelper.add(noPayAmt, FDCHelper.subtract(info.getOriginalAmount(), temp));
					// 请款单的请款金额 - 该请款单对应付款单的付款金额 = 申请未付金额
					noPayAmtLocal = FDCHelper.add(noPayAmtLocal, FDCHelper.subtract(info.getAmount(), tempLocal));
				}
			} else {// 还没有付款单
				temp = FDCHelper.toBigDecimal(info.getOriginalAmount());
				tempLocal = FDCHelper.toBigDecimal(info.getAmount());
				if (!info.isHasClosed()) {// 已关闭的不应该包含进去
					noPayAmt = FDCHelper.add(noPayAmt, FDCHelper.toBigDecimal(info.getOriginalAmount()));
					noPayAmtLocal = FDCHelper.add(noPayAmtLocal, FDCHelper.toBigDecimal(info.getAmount()));
				}
			}
			if (!isKeepAmt) {
				// 如果付款申请单已经审批，即已经有关联的付款单.那么在进行"进度款+结算款不能超过合同结算价-保修金"校验的时候
				// 进度款和结算款就应该取付款单上的金额而不是再是请款单上的 by cassiel_peng 2009-12-06
				if (info.isHasClosed()) {
					if (info.getEntrys().size() > 0) {
						PaymentBillInfo payment = info.getEntrys().get(0).getPaymentBill();
						if (payment != null) {
							noKeepAmt = FDCHelper.toBigDecimal(FDCHelper.add(noKeepAmt, _tempActuallyPayOriAmt));
							noKeepAmtLocal = FDCHelper.toBigDecimal(FDCHelper.add(noKeepAmtLocal, _tempActuallyPayLocalAmt));
						} else {
							noKeepAmt = noKeepAmt.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
							noKeepAmtLocal = noKeepAmtLocal.add(FDCHelper.toBigDecimal(info.getAmount()));
						}
					}
				} else {
					noKeepAmt = noKeepAmt.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
					noKeepAmtLocal = noKeepAmtLocal.add(FDCHelper.toBigDecimal(info.getAmount()));
				}
			}
			// totalpayAmount = totalpayAmount.add(temp);
			// totalpayAmountLocal = totalpayAmountLocal.add(tempLocal);
		}

		total = total.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount()));
		// totalLocal =
		// totalLocal.add(FDCHelper.toBigDecimal(billInfo.getAmount()));

		completeTotal = completeTotal.add(FDCHelper.toBigDecimal(billInfo.getCompletePrjAmt()));
		if (contractBill != null && contractBill.isHasSettled()) {
			completeTotal = contractBill.getSettleAmt();
		}
		projectPriceInContractTotal = projectPriceInContractTotal.add(FDCHelper.toBigDecimal(billInfo.getProjectPriceInContract()));
		// 本币：当前单据合同下的累计实付款+未付的申请单超过了付款提示比例:
		// 累计金额:1100.00 其中,实付数：300.00 申请未付数:800.00
		// 付款提示比例金额：850.00(1000*85.00%)
		/**
		 * 付款申请单有类似与上文注释所示的逻辑控制，但是在中间的计算过程中将累计金额计算错误，导致下文注释所示代码演示的申请未付数=累计金额-
		 * 累计实付数错误 现在修正计算逻辑 by cassiel_peng 2010-03-29
		 */
		// totalpayAmount =
		// totalpayAmount.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount
		// ()));
		// totalpayAmountLocal =
		// totalpayAmountLocal.add(FDCHelper.toBigDecimal(billInfo
		// .getAmount()));
		// noPayAmt = totalpayAmount.subtract(payAmt);
		// noPayAmtLocal = totalpayAmountLocal.subtract(payAmtLocal);
		noPayAmt = FDCHelper.add(noPayAmt, billInfo.getOriginalAmount());
		noPayAmtLocal = FDCHelper.add(noPayAmtLocal, billInfo.getAmount());
		totalpayAmountLocal = FDCHelper.add(payAmtLocal, noPayAmtLocal);
		/*
		 * 需求: 软件需要进行控制，当该合同付最后一笔结算款的时候，
		 * 系统应该比较该合同累计已付款金额+该笔申请款金额的总额是否超过“合同结算金额-保修金额”， 若超过了，则系统需要进行提示，但不做强制控制；
		 */
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if (contractBill != null && contractBill.isHasSettled() && billInfo.getPaymentType() != null && billInfo.getPaymentType().getPayType() != null
				&& billInfo.getPaymentType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)) {
			builder.clear();
			if (isBaseCurrency) {
				builder.appendSql("select (fsettleprice-fqualityGuarante) as amount from t_con_contractsettlementbill where fcontractbillid=");
			} else {
				builder.appendSql("select foriginalamount*(1-isnull(fqualityGuaranteRate,0)/100) as amount from t_con_contractsettlementbill where fcontractbillid=");
			}
			builder.appendParam(billInfo.getContractId());
			builder.appendSql(" and FIsFinalSettle = 1 ");
			IRowSet rowSet = builder.executeQuery();
			/**
			 * "在进行“进度款+结算款不能超过合同结算价-保修金"的逻辑判断时出错，原因：之前系统不支持合同的多笔结算 by
			 * Cassiel_peng
			 */
			if (rowSet.size() == 1) {
				rowSet.next();
				BigDecimal amount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"), 2);
				if (isBaseCurrency) {
					noKeepAmtLocal = FDCHelper.add(noKeepAmtLocal, billInfo.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
					if (noKeepAmtLocal.compareTo(amount) > 0) {
						FDCMsgBox.showError(this, "本币：进度款+结算款不能超过合同结算价-保修金");
						SysUtil.abort();
					}
				} else {
					noKeepAmt = FDCHelper.add(noKeepAmt, billInfo.getOriginalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
					if (noKeepAmt.compareTo(amount) > 0) {
						FDCMsgBox.showError(this, "原币：进度款+结算款不能超过合同结算价-保修金");
						SysUtil.abort();
					}
				}

			}
		} else if (billInfo.getPaymentType() != null && billInfo.getPaymentType().getPayType() != null && billInfo.getPaymentType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID)) {
			builder.clear();
			if (isBaseCurrency) {
				builder.appendSql("select fqualityGuarante as amount from t_con_contractsettlementbill where fcontractbillid=");
			} else {
				/********
				 * 取原币的保修金金额
				 */
				builder.appendSql("select foriginalamount*isnull(fqualityGuaranteRate,0)/100 as amount from t_con_contractsettlementbill where fcontractbillid=");
			}

			builder.appendParam(billInfo.getContractId());
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.size() == 1) {
				rowSet.next();
				BigDecimal amount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"), 2);

				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.appendFilterItem("contractId", billInfo.getContractId());
				filter.appendFilterItem("paymentType.payType.id", PaymentTypeInfo.keepID);
				if (billInfo.getId() != null)
					filter.getFilterItems().add(new FilterItemInfo("id", billInfo.getId().toString(), CompareType.NOTEQUALS));
				if (billInfo.getId() != null)
					filter.appendFilterItem("id", billInfo.getId().toString());
				view.setFilter(filter);
				view.getSelector().add("amount");
				PayRequestBillCollection coll = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
				BigDecimal keepAmt = FDCHelper.ZERO;
				BigDecimal keepAmtLocal = FDCHelper.ZERO;
				for (Iterator iter = coll.iterator(); iter.hasNext();) {
					PayRequestBillInfo keepInfo = (PayRequestBillInfo) iter.next();
					if (keepInfo.getOriginalAmount() != null) {
						keepAmt = keepAmt.add(keepInfo.getOriginalAmount());
					}
					if (keepInfo.getAmount() != null) {
						keepAmtLocal = keepAmtLocal.add(keepInfo.getAmount());
					}
				}

				if (isBaseCurrency) {
					keepAmtLocal = FDCHelper.add(keepAmtLocal, billInfo.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
					if ((keepAmtLocal.compareTo(amount) > 0)) {
						FDCMsgBox.showError(this, "本币：合同累计已付保修款金额超过合同结算保修金额!”");
						SysUtil.abort();
					}
				} else {
					keepAmt = FDCHelper.add(keepAmt, billInfo.getOriginalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
					if ((keepAmt.compareTo(amount) > 0)) {
						FDCMsgBox.showError(this, "原币：合同累计已付保修款金额超过合同结算保修金额!”");
						SysUtil.abort();
					}
				}
			}
		}

		/**
		 * 付款提示比例算法：累计实付款+未付的申请金额 大于 合同金额*付款提示比例 时提示。 现修改为：累计实付款+未付的申请金额 大于
		 * 合同最新造价*付款提示比例 时提示 by cassiel_peng 2010-03-17 因为之前是合同金额*付款提示比例
		 * 所以是有本币和原币的区别的，但是现改为合同最新造价*付款提示比例 由于合同最新造价只有本币的概念，
		 * 所以下边的原币提示比例的校验已经没有实际意义了 by cassiel_peng 2010-03-17
		 */
//		if (contractBill != null && !contractBill.isHasSettled()) {// 未最终结算
//			builder.clear();
//			if (isBaseCurrency) {
//				// builder
//				// .appendSql(
//				// "select (famount * fpayPercForWarn)/100 as fsumamt,famount as amount,fpayPercForWarn from t_con_contractbill where fid="
//				// );
//				builder.appendSql("select fpayPercForWarn from t_con_contractbill where fid=");
//
//				builder.appendParam(billInfo.getContractId());
//				final IRowSet rowSet = builder.executeQuery();
//				BigDecimal payRate = FDCHelper.ZERO;
//				// BigDecimal contractAmt = FDCHelper.ZERO;
//				BigDecimal payPercForWarn = FDCHelper.ZERO;
//				BigDecimal conLastestPrice = FDCHelper.ZERO;
//				Map map = new HashMap();
//				if (rowSet.size() == 1) {
//					rowSet.next();
//					// payRate =
//					// FDCHelper.toBigDecimal(rowSet.getBigDecimal("fsumamt"),
//					// 2);
//					// contractAmt = FDCHelper.toBigDecimal(
//					// rowSet.getBigDecimal("amount"), 2);
//					payPercForWarn = FDCHelper.toBigDecimal(rowSet.getBigDecimal("fpayPercForWarn"), 2);
//				}
//				// 合同最新造价
//				map = FDCUtils.getLastAmt_Batch(null, new String[] { billInfo.getContractId() });
//				if (map != null && map.size() == 1) {
//					conLastestPrice = (BigDecimal) map.get(billInfo.getContractId());
//				}
//				payRate = FDCHelper.divide(FDCHelper.multiply(conLastestPrice, payPercForWarn), FDCHelper.ONE_HUNDRED);
//				totalpayAmountLocal = FDCHelper.toBigDecimal(totalpayAmountLocal, 2);
//				// totalpayAmount = FDCHelper.toBigDecimal(totalpayAmount, 2);
//
//				if (totalpayAmountLocal.compareTo(payRate) > 0) {
//					String str = "本币：当前单据合同下的累计实付款+未付的申请单超过了付款提示比例:";
//					str = str + "\n累计金额:" + totalpayAmountLocal + " 其中,实付数：" + FDCHelper.toBigDecimal(payAmtLocal, 2) + "  申请未付数:" + FDCHelper.toBigDecimal(noPayAmtLocal, 2);
//					str = str + "\n付款提示比例金额：" + payRate + "(" + conLastestPrice + "*" + payPercForWarn + "%)";
//					if ("0".equals(allPaidMoreThanConPrice())) {// 严格控制
//						FDCMsgBox.showDetailAndOK(this, "超过付款提示比例,请查看详细信息", str, 2);
//						SysUtil.abort();
//					} else if ("1".equals(allPaidMoreThanConPrice())) {// 提示控制
//						FDCMsgBox.showDetailAndOK(this, "超过付款提示比例,请查看详细信息", str, 1);
//					}
//				}
//			} else {
//				// builder
//				// .appendSql(
//				// "select (foriginalamount * fpayPercForWarn)/100 as fsumamt,foriginalamount as amount ,fpayPercForWarn from t_con_contractbill where fid="
//				// );
//			}
//
//			// if (isBaseCurrency) {
//			// } else {
//			// if (totalpayAmount.compareTo(payRate) > 0) {
//			// String str = "原币：当前单据合同下的累计实付款+未付的申请单超过了付款提示比例:";
//			// str = str + "\n累计金额:" + totalpayAmount + " 其中,实付数：" +
//			// FDCHelper.toBigDecimal(payAmt, 2) + "  申请未付数:" +
//			// FDCHelper.toBigDecimal(noPayAmt, 2);
//			// str = str + "\n付款提示比例金额：" + payRate + "(" + contractAmt + "*" +
//			// payPercForWarn + "%)";
//			// FDCMsgBox.showDetailAndOK(this, "超过付款提示比例,请查看详细信息", str, 1);
//			// }
//			// }
//		}
		/******
		 * 判断最新造价
		 */
		if (isBaseCurrency) {
			// if (totalLocal.compareTo(latestPrice) > 0) {
			// // 严格控制不允许提交
			// if (isControlCost) {
			// FDCMsgBox.showError(this, "合同下付款申请单的累计金额(本币)大于合同最新造价(本币)！");
			// SysUtil.abort();
			// } else {
			// int result = FDCMsgBox.showConfirm2(this,
			// "合同下付款申请单的累计金额(本币)大于合同最新造价(本币).是否提交?");
			// if (result != FDCMsgBox.OK) {
			// SysUtil.abort();
			// }
			// }
			// }
		} else {
			/**********
			 * 需要使用原币的最新造价比较
			 * 
			 */
			total = FDCHelper.toBigDecimal(total, 2);
			lastestPriceOriginal = FDCHelper.toBigDecimal(lastestPriceOriginal, 2);
			if (total.compareTo(lastestPriceOriginal) > 0) {
				// 严格控制不允许提交
				if (isControlCost) {
					FDCMsgBox.showError(this, "合同下付款申请单的累计金额(原币)大于合同最新造价(原币)！");
					SysUtil.abort();
				} else {
					int result = FDCMsgBox.showConfirm2(this, "合同下付款申请单的累计金额(原币)大于合同最新造价(原币).是否提交?");
					if (result != FDCMsgBox.OK) {
						SysUtil.abort();
					}
				}
			}
		}
		BigDecimal totalReqAmt = payAmtLocal.add(billInfo.getAmount());
		if (totalReqAmt.compareTo(latestPrice) == 1) {
			if (isControlCost) {
				FDCMsgBox.showWarning(this, "\"本次申请+累计实付\" 不能大于合同最新造价!");
				SysUtil.abort();
			}
		}

		if (isSimpleFinancial) {
			if (billInfo.getPaymentType() == null || billInfo.getPaymentType().getPayType() == null) {
				FDCMsgBox.showError(this, "付款类别有误");
				SysUtil.abort();
			}
			if (FDCHelper.toBigDecimal(projectPriceInContractTotal, 2).compareTo(latestPrice) > 0) {
				showMsg4TotalPayReqAmtMoreThanConPrice(isControlCost);
			}
			if (!isAutoComplete && contractBill != null && contractBill.isHasSettled()) {
				if (FDCHelper.toBigDecimal(completeTotal, 2).compareTo(latestPrice) > 0) {
					String msg = "合同下付款申请单的累计已完工工程量不能大于合同最新造价";
					FDCMsgBox.showWarning(this, msg);
					SysUtil.abort();
				}
			}
			return;
			// 简单模式到此结束
		}
		if (FDCHelper.toBigDecimal(completeTotal, 2).compareTo(latestPrice) > 0) {
			if (billInfo.getPaymentType() == null || billInfo.getPaymentType().getPayType() == null) {
				FDCMsgBox.showError(this, "付款类别有误");
				SysUtil.abort();
			}
			if (billInfo.getPaymentType().getPayType().getId().toString().equals(PaymentTypeInfo.progressID)) {
				String msg = "合同下付款申请单的累计已完工工程量大于合同最新造价，是否提交?";
				int result = FDCMsgBox.showConfirm2(this, msg);
				if (result != FDCMsgBox.OK) {
					SysUtil.abort();
				}
			}
		}
		if (isBaseCurrency) {
			// 此处如是查看状态时，当前单据的金额被重复统计了，后面也只判断修改状态减去，没有查看
			// 所以查看时，不加当前单据金额 edit by emanon
			// BigDecimal totalLocal =
			// FDCHelper.add(ContractClientUtils.getReqAmt
			// (billInfo.getContractId()), billInfo.getAmount());
			BigDecimal totalLocal = FDCHelper.ZERO;
			if (STATUS_VIEW.equals(getOprtState()) || STATUS_FINDVIEW.equals(getOprtState())) {
				totalLocal = ContractClientUtils.getReqAmt(billInfo.getContractId());
			} else {
				totalLocal = FDCHelper.add(ContractClientUtils.getReqAmt(billInfo.getContractId()), billInfo.getAmount());
			}
			// 避免在修改时重复记录，需要减去这张单据之前保存在数据库里的值。
			if (STATUS_EDIT.equals(getOprtState())) {
				EntityViewInfo _view = new EntityViewInfo();
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("amount");
				FilterInfo _filter = new FilterInfo();
				_filter.getFilterItems().add(new FilterItemInfo("fdcPayReqID", billInfo.getId().toString()));
				_view.setFilter(_filter);
				PayRequestBillInfo _tempReqInfo = null;
				PaymentBillInfo _tempPayInfo = null;
				if (billInfo.isHasClosed()) {
					if (PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(_view).size() > 0) {
						_tempPayInfo = PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(_view).get(0);
						totalLocal = FDCHelper.subtract(totalLocal, _tempPayInfo.getAmount());
					}
				} else {
					_tempReqInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(billInfo.getId()));
					totalLocal = FDCHelper.subtract(totalLocal, _tempReqInfo.getAmount());
				}
			}

			if (totalLocal.compareTo(latestPrice) > 0) {
				// 严格控制不允许提交
				showMsg4TotalPayReqAmtMoreThanConPrice(isControlCost);
			}
		}

	}

	/**
	 * 覆盖抽象类处理编码规则的行为,统一在FDCBillEditUI.handCodingRule方法中处理
	 */
	protected void setAutoNumberByOrg(String orgType) {

	}

	private void viewContract(String id) throws UIException {
		if (id == null)
			return;
		String editUIName = null;
		if (PayReqUtils.isContractBill(id)) {
			editUIName = com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class.getName();

		} else if (PayReqUtils.isConWithoutTxt(id)) {
			editUIName = com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class.getName();

		}

		if (editUIName == null)
			return;
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, id);
		uiContext.put("source", "listBase"); // 用于与工作流过来的区分
		// 创建UI对象并显示
		IUIWindow windows = this.getUIWindow();
		String type = UIFactoryName.NEWTAB;
		if (windows instanceof UIModelDialog || windows == null) {
			type = UIFactoryName.MODEL;
		}
		IUIWindow contractUiWindow = UIFactory.createUIFactory(type).create(editUIName, uiContext, null, "FINDVIEW");
		if (contractUiWindow != null) {
			contractUiWindow.show();
		}
	}

	public void actionViewContract_actionPerformed(ActionEvent e) throws Exception {
		// super.actionViewContract_actionPerformed(e);
		if (editData == null || editData.getContractId() == null) {
			return;
		}
		// tableHelper.debugCellExp();
		viewContract(editData.getContractId());
	}

	private void viewContractExecInfo(String id) throws UIException {
		if (id == null)
			return;
		String editUIName = null;
		if (PayReqUtils.isContractBill(id)) {
			editUIName = com.kingdee.eas.fdc.contract.client.ContractFullInfoUI.class.getName();
		}
		if (editUIName == null)
			return;
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, id);
		uiContext.put("source", "listBase"); // 用于与工作流过来的区分
		// 创建UI对象并显示
		IUIWindow windows = this.getUIWindow();
		String type = UIFactoryName.NEWTAB;
		if (windows instanceof UIModelDialog || windows == null) {
			type = UIFactoryName.MODEL;
		}
		IUIWindow contractUiWindow = UIFactory.createUIFactory(type).create(editUIName, uiContext, null, "FINDVIEW");
		if (contractUiWindow != null) {
			contractUiWindow.show();
		}
	}

	public void actionContractExecInfo_actionPerformed(ActionEvent e) throws Exception {
		String contractId = this.editData.getContractId();
		viewContractExecInfo(contractId);
	}

	// 查看预算余额
	public void actionViewMbgBalance_actionPerformed(ActionEvent e) throws Exception {

		PayRequestBillInfo payReqInfo = this.editData;
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		if (fdcBudgetParam.isAcctCtrl()) {
			if (this.editData.getId() == null) {
				return;
			}
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("payDate");
			selector.add("contractId");
			selector.add("contractNo");
			selector.add("contractName");
			selector.add("state");
			selector.add("amount");
			selector.add("acctPays.*");
			selector.add("acctPays.payRequestBill.id");
			selector.add("acctPays.costAccount.id");
			selector.add("acctPays.costAccount.codingNumber");
			// selector.add("acctPays.costAccount.longNumber");
			// selector.add("acctPays.costAccount.displayName");
			selector.add("acctPays.costAccount.name");
			selector.add("acctPays.period.*");
			selector.add("orgUnit.id");
			selector.add("orgUnit.number");
			selector.add("orgUnit.name");
			selector.add("currency.id");
			selector.add("currency.number");
			selector.add("localCurrency.id");
			selector.add("localCurrency.number");
			selector.add("localCurrency.name");

			payReqInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getId().toString()), selector);

			if (payReqInfo.getAcctPays() == null || payReqInfo.getAcctPays().size() == 0) {
				return;
			}
			// for(Iterator
			// iter=payReqInfo.getAcctPays().iterator();iter.hasNext();){
			// PayRequestAcctPayInfo info=(PayRequestAcctPayInfo)iter.next();
			// //处理longnumber以便与预算匹配
			// String lgNumber = info.getCostAccount().getLongNumber();
			// if(lgNumber!=null){
			// lgNumber=lgNumber.replace('!', '.');
			// }
			// info.getCostAccount().setLongNumber(lgNumber);
			//				
			// }

		}
		/*
		 * //接口1：根据界面参数取最新的预算余额 BgCtrlResultCollection coll =
		 * iCtrl.getBudget(FDCConstants.PayRequestBill, null, payReqInfo);
		 * //接口2：取数据库中的余额 BgCtrlResultCollection coll =
		 * BudgetCtrlFacadeFactory.getRemoteInstance
		 * ().getBudget(payReqInfo.getId().toString());
		 * 
		 * UIContext uiContext = new UIContext(this);
		 * uiContext.put(BgHelper.BGBALANCE, coll);
		 * 
		 * IUIWindow uiWindow =
		 * UIFactory.createUIFactory(UIFactoryName.MODEL).create
		 * (BgBalanceViewUI.class.getName(), uiContext, null, STATUS_VIEW);
		 * uiWindow.show();
		 */
		// 与1接口相同 by hpw 2011.6.2
		BudgetCtrlClientCaller.showBalanceViewUI(payReqInfo, this);

		payReqInfo = null;
	}

	protected void initWorkButton() {
		super.initWorkButton();

		menuItemViewPayDetail.setText(menuItemViewPayDetail.getText() + "(D)");
		menuItemViewPayDetail.setMnemonic('D');

		actionViewPayDetail.setEnabled(true);
		actionViewPayDetail.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sequencecheck"));
		menuItemViewPayDetail.setText(menuItemViewPayDetail.getText() + "(D)");
		menuItemViewPayDetail.setMnemonic('D');

		actionViewContract.setEnabled(true);
		actionViewContract.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sequencecheck"));
		actionViewMbgBalance.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sequencecheck"));
		actionAssociateUnSettled.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_settingrelating"));
		actionAssociateAcctPay.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_seerelating"));
		actionMonthReq.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_seerelating"));
		menuItemViewContract.setText(menuItemViewContract.getText() + "(V)");
		menuItemViewContract.setMnemonic('V');
		menuItemAdjustDeduct.setText(menuItemAdjustDeduct.getText() + "(T)");
		menuItemAdjustDeduct.setMnemonic('T');

		menuItemClose.setText(menuItemClose.getText() + "(C)");
		menuItemUnClose.setText(menuItemUnClose.getText() + "(F)");
		menuItemClose.setMnemonic('C');
		menuItemUnClose.setMnemonic('F');

		actionTaoPrint.setVisible(false);
		actionTaoPrint.setEnabled(false);

		actionPaymentPlan.setVisible(false);
		actionPaymentPlan.setVisible(false);
//		if ((isSeparate && contractBill != null && contractBill.isIsCoseSplit()) || this.isAutoComplete) {
//			txtcompletePrjAmt.setRequired(false);
//			contcompletePrjAmt.setVisible(false);
//			contpaymentProportion.setVisible(false);
//			contAllCompletePrjAmt.setVisible(false);
//			contAllPaymentProportion.setVisible(false);
//			contCompleteRate.setVisible(false);
//		}
		menuItemViewMaterialConfirm.setText(menuItemViewMaterialConfirm.getText() + "(M)");
		menuItemViewMaterialConfirm.setMnemonic('M');

		actionViewMaterialConfirm.setEnabled(true);
		actionViewMaterialConfirm.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sequencecheck"));

		this.menuItemContractExecInfo.setIcon(EASResource.getIcon("imgTbtn_execute"));
		this.btnContractExecInfo.setIcon(EASResource.getIcon("imgTbtn_execute"));

	}

	public void kdtEntrys_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
		if (e.getType() == 0) {
			return;
		}
	}

	/**
	 * 业务日期改变，取对应的合同滚动计划
	 */
	protected void pkbookedDate_dataChanged(DataChangeEvent e) throws Exception {
		StringBuffer format = new StringBuffer();
		Date bookDate = (Date) pkbookedDate.getValue();
		Calendar cal = Calendar.getInstance();
		cal.setTime(bookDate);
		format.append("$contractName$ ");
		format.append(cal.get(Calendar.YEAR));
		format.append("年");
		format.append(cal.get(Calendar.MONTH) + 1);
		format.append("月付款计划");
		prmtPlanHasCon.setDisplayFormat(format.toString());
		initPrmtPlanUnCon();

		if (STATUS_ADDNEW.equals(getOprtState()) || STATUS_EDIT.equals(getOprtState())) {
			String con = editData.getContractId();
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select con.FID as id,con.FContractID as conID,con.FContractName as conName from T_FNC_FDCDepConPayPlanContract as con ");
			builder.appendSql("left join T_FNC_FDCDepConPayPlanCE as cone on cone.FParentC = con.FID ");
			builder.appendSql("left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FHeadID ");
			builder.appendSql("where (head.FState = '4AUDITTED' or head.FState = '10PUBLISH') ");
			builder.appendSql(" and con.FContractID = ");
			builder.appendParam(con);
			builder.appendSql(" and cone.FMonth >= ");
			builder.appendParam(BudgetViewUtils.getFirstDay(bookDate));
			builder.appendSql(" and cone.FMonth <= ");
			builder.appendParam(BudgetViewUtils.getLastDay(bookDate));
			IRowSet rowSet = builder.executeQuery();
			if (rowSet != null && rowSet.size() >= 1) {
				rowSet.next();
				String id = rowSet.getString("id");
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("id");
				sic.add("contract.id");
				sic.add("contractName");
				sic.add("head.deptment.name");
				sic.add("head.year");
				sic.add("head.month");
				FDCDepConPayPlanContractInfo info = FDCDepConPayPlanContractFactory.getRemoteInstance().getFDCDepConPayPlanContractInfo(new ObjectUuidPK(id), sic);
				prmtPlanHasCon.setValue(info);
				contPlanHasCon.setVisible(true);
				prmtPlanHasCon.setEnabled(false);
				contPlanUnCon.setVisible(false);
				if (!"不控制".equals(CONTROLPAYREQUEST)) {
					prmtPlanHasCon.setRequired(true);
					prmtPlanUnCon.setRequired(false);
				}
			} else {
				contPlanHasCon.setVisible(false);
				// contPlanUnCon.setVisible(true);
				if (!"不控制".equals(CONTROLPAYREQUEST)) {
					prmtPlanHasCon.setRequired(false);
					prmtPlanUnCon.setRequired(true);
				}
			}
		}

		if (!isCostSplitContract) {
			prmtPlanHasCon.setRequired(false);
			prmtPlanUnCon.setRequired(false);
		}
		// getBgAmount();
		getPayPlanValue();
	}

	protected void pkpayDate_dataChanged(DataChangeEvent e) throws Exception {
		// if (this.getOprtState().equals(OprtState.VIEW) || (editData != null
		// && (editData.getState() == FDCBillStateEnum.AUDITTED ||
		// editData.getState() == FDCBillStateEnum.AUDITTING))) {
		// return;
		// }
		Object objNew = pkpayDate.getValue();
		Object objOld = e.getOldValue();
		BigDecimal planAmt = FDCHelper.ZERO;
		String contractId = editData.getContractId();
		String thisBillid = new String();
		Timestamp startTime = null;
		Timestamp endTime = null;
		if (objNew == null) {
			objNew = new Date();
		}

		if (objNew == null) {
			// PayRequestBillContants.CURPLANNEDPAYMENT
			tableHelper.setCellValue(PayRequestBillContants.CURPLANNEDPAYMENT, null);
		} else {
			if (objNew.equals(objOld)) {
				planAmt = FDCHelper.toBigDecimal(editData.getCurPlannedPayment());
			} else {
				/*
				 * Date dateNew = (Date) objNew; Date dateOld = objOld != null ?
				 * (Date) objOld : null; if (dateOld != null &&
				 * dateNew.getYear() == dateOld.getYear() && dateNew.getMonth()
				 * == dateOld.getMonth()) {
				 * planAmt=FDCHelper.toBigDecimal(editData
				 * .getCurPlannedPayment()); } else {}
				 */

				// 相同时期仍然从数据库内取以同步最新的付款计划
				if (contractId == null) {
					return;
				}
				Date dateNew = (Date) objNew;
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateNew);
				Date date = (Date) dateNew.clone();
				date = FDCDateHelper.getFirstDayOfMonth(date);
				startTime = new Timestamp(date.getTime());
				date = (Date) dateNew.clone();
				date = FDCDateHelper.getLastDayOfMonth(date);
				endTime = new Timestamp(date.getTime());

				FDCSQLBuilder build = new FDCSQLBuilder();
				build.appendSql("select FPayAmount from T_FNC_ContractPayPlan where fcontractId=");
				build.appendParam(contractId);
				build.appendSql(" and fpaydate>=");

				build.appendParam(startTime);
				build.appendSql(" and fpaydate<=");

				// date = (Date) date.clone();
				// date.setDate(cal.getMaximum(Calendar.DAY_OF_MONTH));

				build.appendParam(endTime);
				IRowSet rowSet = build.executeQuery();
				if (rowSet.size() > 0) {
					rowSet.next();
					planAmt = rowSet.getBigDecimal("FPayAmount");
					tableHelper.setCellValue(PayRequestBillContants.CURPLANNEDPAYMENT, planAmt);
				} else {
					// 付款计划不存在时，本期欠付款为空
					tableHelper.setCellValue(PayRequestBillContants.CURPLANNEDPAYMENT, null);
					tableHelper.setCellValue(PayRequestBillContants.CURBACKPAY, null);
					return;
				}

			}
		}
		/*
		 * 本期欠付款 :计算公式为“该合同本期付款计划-该合同的本期累计申请（不包含本次申请）-该合同本期累计实付”； by sxhong
		 * 2007/09/28
		 */

		/*****/
		/*
		 * EntityViewInfo view = new EntityViewInfo(); FilterInfo filter = new
		 * FilterInfo(); view.setFilter(filter);
		 * filter.appendFilterItem("contractId", editData.getContractId()); //
		 * 用时间过滤出所有之前的单 filter.getFilterItems().add( new
		 * FilterItemInfo("createTime", editData.getCreateTime(),
		 * CompareType.LESS)); view.getSelector().add("amount");
		 * view.getSelector().add("entrys.paymentBill.billStatus");
		 * view.getSelector().add("entrys.paymentBill.amount");
		 * //本期欠付款应该用付款单的本币计算，而不是原币
		 * view.getSelector().add("entrys.paymentBill.localAmt");
		 * PayRequestBillCollection c =
		 * PayRequestBillFactory.getRemoteInstance()
		 * .getPayRequestBillCollection(view); BigDecimal totalpayAmount =
		 * FDCHelper.ZERO;// 累计实付款+未付的申请单 for (int i = 0; i < c.size(); i++) {
		 * final PayRequestBillInfo info = c.get(i); if
		 * (info.getId().equals(editData.getId())) { // 排除本单据 continue; }
		 * BigDecimal temp = FDCHelper.ZERO; if (info.getEntrys().size() > 0) {
		 * for (int j = 0; j < info.getEntrys().size(); j++) { PaymentBillInfo
		 * payment = info.getEntrys().get(0) .getPaymentBill(); if (payment !=
		 * null && payment.getBillStatus() == BillStatusEnum.PAYED) { temp =
		 * temp.add(FDCHelper.toBigDecimal(payment .getLocalAmt())); } } if
		 * (temp.compareTo(FDCHelper.ZERO) == 0) { temp =
		 * FDCHelper.toBigDecimal(info.getAmount()); }
		 * 
		 * } else { temp = FDCHelper.toBigDecimal(info.getAmount()); }
		 * totalpayAmount = totalpayAmount.add(temp); }
		 */
		/*****/
		Date dateNew;
		dateNew = (Date) objNew;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateNew);
		Date date = (Date) dateNew.clone();
		date = FDCDateHelper.getFirstDayOfMonth(date);
		startTime = new Timestamp(date.getTime());
		date = (Date) dateNew.clone();
		date = FDCDateHelper.getLastDayOfMonth(date);
		endTime = new Timestamp(date.getTime());
		BigDecimal totalpayAmount = FDCHelper.ZERO;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select sum(pr.famount) as amount  from T_CON_PayRequestBill pr where pr.fstate='4AUDITTED' " + "	and fcontractid=");
		builder.appendParam(contractId);
		if (editData.getId() != null) {
			thisBillid = editData.getId().toString();
			builder.appendSql("	and pr.fid !=");
			builder.appendParam(thisBillid);
		}

		builder.appendSql(" and fpaydate>=");
		builder.appendParam(startTime);
		builder.appendSql(" and fpaydate<=");
		builder.appendParam(endTime);
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			totalpayAmount = rowSet.getBigDecimal("amount");
			if (totalpayAmount == null) {
				totalpayAmount = FDCHelper.ZERO;
			}
		}
		totalpayAmount = totalpayAmount.add(FDCHelper.toBigDecimal(txtBcAmount.getBigDecimalValue()));
		BigDecimal mustPayAmt = planAmt.subtract(totalpayAmount);
		tableHelper.setCellValue(PayRequestBillContants.CURBACKPAY, mustPayAmt);
	}

	protected void prmtsettlementType_dataChanged(DataChangeEvent e) throws Exception {
		// super.prmtsettlementType_dataChanged(e);
		Object objNew = e.getNewValue();
		// if (objNew == null) {
		// txtrecBank.setRequired(isBankRequire);
		// txtrecAccount.setRequired(isBankRequire);
		// txtrecAccount.repaint();
		// txtrecBank.repaint();
		// return;
		// }
		// if (objNew instanceof SettlementTypeInfo && ((SettlementTypeInfo)
		// objNew).getId().toString().equals("e09a62cd-00fd-1000-e000-0b32c0a8100dE96B2B8E"))
		// {
		// // 结算方式为付款则银行及账号为非必录
		// txtrecBank.setRequired(false);
		// txtrecAccount.setRequired(false);
		// } else {
		// txtrecBank.setRequired(isBankRequire);
		// txtrecAccount.setRequired(isBankRequire);
		// }
		if (objNew instanceof SettlementTypeInfo && ((SettlementTypeInfo) objNew).getName().equals("现金")) {
			txtrecBank.setRequired(false);
			txtrecAccount.setRequired(false);
			txtrecBank.setEnabled(false);
			txtrecAccount.setEnabled(false);
			txtrecBank.setText(null);
			txtrecAccount.setText(null);
		} else {
			txtrecBank.setRequired(true);
			txtrecAccount.setRequired(true);
			txtrecBank.setEnabled(true);
			txtrecAccount.setEnabled(true);
		}
		txtrecAccount.repaint();
		txtrecBank.repaint();
	}

	// 检查拆分状态
	private void checkContractSplitState() {
		// if(!isIncorporation){
		// return ;
		// }
		// 付款单提交时，是否检查合同未完全拆分
		if (!checkAllSplit) {
			return;
		}
		String contractBillId = (String) getUIContext().get("contractBillId");
		if (contractBillId != null) {
			if (!ContractClientUtils.getContractSplitState(contractBillId)) {
				// 对应的合同还未进行拆分，不能进行此操作！
				FDCMsgBox.showWarning(this, FDCSplitClientHelper.getRes("conNotSplited"));
				SysUtil.abort();
			}
		}
	}

	// 保存时，控制预算余额
	private void checkMbgCtrlBalance() throws Exception {
		try {
			if (!isMbgCtrl) {
				return;
			}

			StringBuffer buffer = new StringBuffer("");
			IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
			BgCtrlResultCollection bgCtrlResultCollection = iCtrl.getBudget(FDCConstants.PayRequestBill, null, editData);

			if (bgCtrlResultCollection != null) {
				for (int j = 0, count = bgCtrlResultCollection.size(); j < count; j++) {
					BgCtrlResultInfo bgCtrlResultInfo = bgCtrlResultCollection.get(j);

					BigDecimal balance = bgCtrlResultInfo.getBalance();
					if (balance != null && balance.compareTo(bgCtrlResultInfo.getReqAmount()) < 0) {
						buffer.append(bgCtrlResultInfo.getItemName() + "(" + bgCtrlResultInfo.getOrgUnitName() + ")").append(
								EASResource.getString(FDCConstants.VoucherResource, "BalanceNotEnagh") + "\r\n");
					}
				}
			}

			if (buffer.length() > 0) {
				// if(fdcBudgetParam.isStrictCtrl()){
				// FDCMsgBox.showWarning(this, buffer.toString() + "\r\n" +
				// EASResource.getString(FDCConstants.VoucherResource,
				// "BalanceNotEnagh"));
				// this.abort();
				// }
				int result = FDCMsgBox.showConfirm2(this, buffer.toString() + "\r\n" + EASResource.getString(FDCConstants.VoucherResource, "isGoOn"));
				if (result == FDCMsgBox.CANCEL) {
					SysUtil.abort();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存的时候进行预算检查
	 * 
	 * @throws Exception
	 */
	private void checkFdcBudget() throws Exception {
		// FDCBudgetPeriodInfo period=getFDCBudgetPeriod();
		try {
			if (getOprtState().equals(OprtState.ADDNEW) && isFromProjectFillBill && editData.getId() != null) {
				editData.put("isFill", Boolean.TRUE);
			}
			Map retMap = FDCBudgetAcctFacadeFactory.getRemoteInstance().invokeBudgetCtrl(this.editData, FDCBudgetConstants.STATE_SUBMIT);
			// 防止某些用户在未提交时修改参数，再取
			fetchInitParam();
			PayReqUtils.handleBudgetCtrl(this, retMap, fdcBudgetParam);
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}

	private FDCBudgetPeriodInfo getFDCBudgetPeriod() {
		FDCBudgetPeriodInfo period = null;
		if (fdcBudgetParam.isAcctCtrl()) {
			// 从数据库取
			period = (FDCBudgetPeriodInfo) this.editData.get("fdcPeriod");
			if (period == null && this.editData.getId() != null) {
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("select top 1 period.fid id ,period.fyear Pyear,period.fmonth Pmonth from T_FNC_PayRequestAcctPay acctPay ");
				builder.appendSql("inner join T_FNC_FDCBudgetPeriod period on period.fid=acctPay.fperiodid ");
				builder.appendSql("where FPayRequestBillId=?");
				builder.addParam(this.editData.getId().toString());
				try {
					IRowSet rowSet = builder.executeQuery();
					if (rowSet.next()) {
						int year = rowSet.getInt("Pyear");
						int month = rowSet.getInt("Pmonth");
						String id = rowSet.getString("id");
						period = FDCBudgetPeriodInfo.getPeriod(year, month, false);
						period.setId(BOSUuid.read(id));
					}
				} catch (Exception e) {
					handUIException(e);
				}
			}
		}
		if (period == null) {
			if (editData.getPayDate() == null && this.pkpayDate.getValue() == null) {
				FDCMsgBox.showWarning(this, "付款日期 不能为空!");
				SysUtil.abort();
			} else if (editData.getPayDate() == null && pkpayDate.getValue() != null) {
				editData.setPayDate((Date) pkpayDate.getValue());
			}
			period = FDCBudgetPeriodInfo.getPeriod(this.editData.getPayDate(), false);
		}

		return period;
	}

	protected void kdtEntrys_activeCellChanged(KDTActiveCellEvent e) throws Exception {
		if ((e.getRowIndex() == rowIndex) && (e.getColumnIndex() == columnIndex)) {
			// if(e.getSource().g)
		}
	}

	public void setAmountChange(BigDecimal originalAmount) {
		if (originalAmount == null) {
			getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(null);
		} else {
			BigDecimal amount = FDCHelper.multiply(originalAmount, txtexchangeRate.getBigDecimalValue());
			getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(FDCHelper.toBigDecimal(amount, 2));
		}

	}

	public void setAdvanceChange(BigDecimal originalAmount) {
		if (originalAmount == null) {
			getDetailTable().getCell(rowIndex + 1, columnIndex + 1).setValue(null);
		} else {
			BigDecimal amount = FDCHelper.multiply(originalAmount, txtexchangeRate.getBigDecimalValue());
			getDetailTable().getCell(rowIndex + 1, columnIndex + 1).setValue(FDCHelper.toBigDecimal(amount, 2));
		}

	}

	public void setCompletePrjAmt() {
		BigDecimal amount = null;
		if (getDetailTable().getCell(rowIndex, columnIndex + 1).getValue() != null) {
			amount = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex, columnIndex + 1).getValue());
		}
		BigDecimal paymentProp = txtpaymentProportion.getBigDecimalValue();
		BigDecimal completePrj = txtcompletePrjAmt.getBigDecimalValue();
		if (paymentProp == null)
			paymentProp = FDCHelper.ZERO;
		if (completePrj == null)
			completePrj = FDCHelper.ZERO;
		if (paymentProp.compareTo(FDCHelper.ZERO) == 0) {
			if (completePrj.compareTo(FDCHelper.ZERO) == 0) {
				return;
			} else {
				txtpaymentProportion.setRequired(false);

				txtpaymentProportion.setRequired(true && !isAutoComplete);

				String settlementID = PaymentTypeInfo.settlementID;
				PaymentTypeInfo type = (PaymentTypeInfo) prmtPayment.getValue();
				if (type != null && type.getPayType().getId().toString().equals(settlementID)) {
					txtpaymentProportion.setValue(FDCHelper.ZERO);
				} else {
					txtpaymentProportion.setValue(amount.setScale(4, BigDecimal.ROUND_HALF_UP).divide(completePrj, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
				}
			}
		} else {
			txtcompletePrjAmt.setValue(amount.setScale(4, BigDecimal.ROUND_HALF_UP).divide(paymentProp, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));

			String settlementID = PaymentTypeInfo.settlementID;
			PaymentTypeInfo type = (PaymentTypeInfo) prmtPayment.getValue();
			if (type != null && type.getPayType().getId().toString().equals(settlementID)) {
				txtpaymentProportion.setValue(FDCHelper.ZERO);
			}
		}
	}

	public void setPmtAmoutChange(BigDecimal originalAmount) {

		BigDecimal amount = FDCHelper.multiply(originalAmount, txtexchangeRate.getBigDecimalValue());

		((ICell) bindCellMap.get(PayRequestBillContants.PAYPARTAMATLAMT)).setValue(amount);

	}

	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		boolean isZeroPay = false;
		if ((e.getRowIndex() == rowIndex) && (e.getColIndex() == columnIndex)) {
			BigDecimal originalAmount = FDCHelper.toBigDecimal(e.getValue());
			if (FDCHelper.ZERO.compareTo(originalAmount) == 0) {
				BigDecimal cellValue = FDCHelper.toBigDecimal(kdtEntrys.getCell(rowIndex, columnIndex).getValue());
				if (FDCHelper.ZERO.compareTo(cellValue) != 0) {
					originalAmount = cellValue;
				} else {
					isZeroPay = true;
				}
			}
			// 单击cell按delete与双击再delete是两个事件都要重新计算
			if (e.getValue() == null) {
				originalAmount = null;
			}
			setAmountChange(originalAmount);
		} else if ((e.getRowIndex() == ((ICell) bindCellMap.get(PayRequestBillContants.PAYPARTAMATLAMTORI)).getRowIndex() + this.deductTypeCollection.size() + 1) && (e.getColIndex() == 4)) {
			// 录入甲供材
			BigDecimal originalAmount = FDCHelper.toBigDecimal(e.getValue());
			setPmtAmoutChange(originalAmount);
		} else if ((e.getRowIndex() == rowIndex + 1) && (e.getColIndex() == columnIndex)) {
			BigDecimal originalAmount = FDCHelper.toBigDecimal(e.getValue());
			// 单击cell按delete与双击再delete是两个事件都要重新计算
			if (e.getValue() == null) {
				originalAmount = null;
			}
			setAdvanceChange(originalAmount);
		}
		calAllCompletePrjAmt();
		if (isFirstLoad || (!isAutoComplete && !txtpaymentProportion.isRequired()))
			return;
		// 如果是0付款，则不填入已完工数据
		if (!isZeroPay)
			setCompletePrjAmt();
	}

	/**
	 * 
	 * 描述：简单财务成本一体化处理扣款、违约、奖励，则扣款、违约和奖励不影响财务成本的金额，以合同内工程款计入财务成本。
	 * 简单财务成本一体化不处理扣款、违约、奖励，则以实际付款数计入财务成本。
	 * 
	 */
	private void setCostAmount() {
		BigDecimal amount = FDCHelper.toBigDecimal(((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACT)).getValue());// txtBcAmount
		// .
		// getBigDecimalValue
		// (
		// )
		// ;
		BigDecimal totalAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap.get(PayRequestBillContants.CURPAIDLOCAL)).getValue());
		BigDecimal completeAmt = FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue());

		// if(this.isSimpleFinancial){
		// if(this.isSimpleFinancialExtend){
		// editData.setCompletePrjAmt(amount);
		// txtcompletePrjAmt.setValue(amount);
		// }
		// else{
		// editData.setCompletePrjAmt(totalAmt);
		// txtcompletePrjAmt.setValue(totalAmt);
		// }
		// }else{
		if (contractBill.isHasSettled()) {
			if (isSimpleFinancial) {
				editData.setCompletePrjAmt(completeAmt);
				txtcompletePrjAmt.setValue(completeAmt);
				editData.setPaymentProportion(txtpaymentProportion.getBigDecimalValue());
			} else {
				if (isAutoComplete) {
					editData.setPaymentProportion(FDCHelper.ONE_HUNDRED);
					editData.setCompletePrjAmt(contractBill.getSettleAmt());
					txtpaymentProportion.setValue(FDCHelper.ONE_HUNDRED);
					txtcompletePrjAmt.setValue(contractBill.getSettleAmt());
				}
			}
		} else {
			editData.setCompletePrjAmt(completeAmt);
			if (isAutoComplete) {
				txtcompletePrjAmt.setValue(amount);
				editData.setCompletePrjAmt(completeAmt);
			}
		}
		// }
	}

	// 改为成本中心参数，fetchInitParam中
	public int getAdvancePaymentNumber() {
		/*
		 * int advancePaymentNumber = 1;
		 * if(SysContext.getSysContext().getCurrentOrgUnit() != null){ Map
		 * paramMap = null; try { paramMap = FDCUtils.getDefaultFDCParam(null,
		 * null); } catch (EASBizException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (BOSException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * if(paramMap.get(FDCConstants.FDC_PARAM_ADVANCEPAYMENTNUMBER)!=null){
		 * advancePaymentNumber =
		 * Integer.valueOf(paramMap.get(FDCConstants.FDC_PARAM_ADVANCEPAYMENTNUMBER
		 * ).toString()).intValue(); } }
		 */
		return advancePaymentNumber;
	}

	private void prmtPayment_dataChanged(DataChangeEvent eventObj) {

		Object obj = eventObj.getNewValue();
		Object oldObj = eventObj.getOldValue();
		if (obj != null && obj instanceof PaymentTypeInfo) {
			PaymentTypeInfo _type = (PaymentTypeInfo) obj;
			if (!_type.getPayType().getId().toString().equals(PaymentTypeInfo.tempID)) {
				if (this.kdtEntrys.getCell(4, 4) != null) {
					// Add by zhiyuan_tang 2010/07/27
					// R100709-147 如果关联材料确认单时，金额不允许修改，必须通过关联材料确认单进行修改
					if (!isPartACon) {
						this.kdtEntrys.getCell(4, 4).getStyleAttributes().setLocked(false);
					}
					// 如果是甲供材合同的付款申请单，未关联到材料合同，允许修改金额。Added By Owen_wen
					// 2011-04-27
				}
			}
		}

		if (prmtPayment.getUserObject() != null && prmtPayment.getUserObject().equals("noExec")) {
			return;
		}
		if (obj instanceof PaymentTypeInfo) {
			if (isRealizedZeroCtrl) {
				PaymentTypeInfo type = (PaymentTypeInfo) obj;
				if (FDCHelper.isNullZero(txtTotalSettlePrice.getBigDecimalValue()) && type.getName() != null && !type.getName().equals("预付款")) {
					EventListener[] listeners = prmtPayment.getListeners(DataChangeListener.class);
					for (int i = 0; i < listeners.length; i++) {
						prmtPayment.removeDataChangeListener((DataChangeListener) listeners[i]);
					}
					prmtPayment.setData(eventObj.getOldValue());
					FDCMsgBox.showError(prmtPayment, "已实现产值为0只允许选择\"预付款\"！");
					for (int i = 0; i < listeners.length; i++) {
						prmtPayment.addDataChangeListener((DataChangeListener) listeners[i]);
					}
					setCellEnabled(oldObj);
					SysUtil.abort();
				}
			}
		}

		if (obj instanceof PaymentTypeInfo) {
			try {
				String settlementID = PaymentTypeInfo.settlementID;// "Ga7RLQETEADgAAC/wKgOlOwp3Sw="
				// ;//
				// 结算款
				String progressID = PaymentTypeInfo.progressID;// "Ga7RLQETEADgAAC6wKgOlOwp3Sw="
				// ;//
				// 进度款
				String keepID = PaymentTypeInfo.keepID;// "Ga7RLQETEADgAADDwKgOlOwp3Sw="
				// ;//
				// 保修款

				String tempID = PaymentTypeInfo.tempID;// "Bd2bh+CHRDenvdQS3D72ouwp3Sw="
				// 暂估款

				PaymentTypeInfo type = (PaymentTypeInfo) obj;
				// 付款单为暂估款类型时
				if (type.getPayType().getId().toString().equals(tempID)) {
					if (!isSimpleInvoice) {
						prmtPayment.setValue(null);
						prmtPayment.setText(null);
						FDCMsgBox.showWarning("启用“财务帐务以发票金额为准计开发成本”参数后才能选择本类别的付款类别，请先启用对应参数！");
						SysUtil.abort();
					} else {// 其他可录入金额字段仅发票金额可录入(对应的合同内工程款等其他款项不可录入)
						this.kdtEntrys.getStyleAttributes().setLocked(true);
						if (this.kdtEntrys.getCell(4, 4) != null) {
							this.kdtEntrys.getCell(4, 4).setValue(null);
							this.kdtEntrys.getCell(4, 5).setValue(null);
							this.kdtEntrys.getCell(4, 4).getStyleAttributes().setLocked(true);
						}
					}
				}

				// 保修款修改为只要结算就可以付

				/*
				 * filter.appendFilterItem("paymentType.payType.id",
				 * settlementID); filter.appendFilterItem("contractId",
				 * editData.getContractId()); if (type.getPayType() == null) {
				 * return; } if
				 * (type.getPayType().getId().toString().equals(keepID)) { if
				 * (!PayRequestBillFactory.getRemoteInstance() .exists(filter))
				 * { EventListener[] listeners =
				 * prmtPayment.getListeners(DataChangeListener.class); for(int
				 * i=0;i<listeners.length;i++){
				 * prmtPayment.removeDataChangeListener
				 * ((DataChangeListener)listeners[i]); }
				 * prmtPayment.setData(eventObj.getOldValue());
				 * FDCMsgBox.showError(prmtPayment,
				 * "付过结算款后才能付保修款,即存在付款类别的类型为“结算款”的付款申请单时才能选择“保修款”类型付款类别");
				 * for(int i=0;i<listeners.length;i++){
				 * prmtPayment.addDataChangeListener
				 * ((DataChangeListener)listeners[i]); } SysUtil.abort(); } }
				 */

				if (type.getName().equals("预付款")) {
					FilterInfo filter = new FilterInfo();
					filter.appendFilterItem("paymentType.name", "预付款");
					filter.appendFilterItem("contractId", this.editData.getContractId());
					EntityViewInfo evi = new EntityViewInfo();
					evi.setFilter(filter);

					PayRequestBillCollection cols = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(evi);
					int number = getAdvancePaymentNumber();
					if (cols.size() >= number) {
						FDCMsgBox.showWarning("一个合同只允许录" + number + "次预付款.");
						prmtPayment.setData(null);
						setCellEnabled(oldObj);
						abort();
					}

				}

				FilterInfo filter = new FilterInfo();
				filter.appendFilterItem("id", editData.getContractId());
				filter.appendFilterItem("hasSettled", Boolean.TRUE);
				if (type.getPayType().getId().toString().equals(keepID)) {
					if (!ContractBillFactory.getRemoteInstance().exists(filter)) {
						EventListener[] listeners = prmtPayment.getListeners(DataChangeListener.class);
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment.removeDataChangeListener((DataChangeListener) listeners[i]);
						}
						prmtPayment.setData(eventObj.getOldValue());
						FDCMsgBox.showError(prmtPayment, "合同结算后才能付保修款");
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment.addDataChangeListener((DataChangeListener) listeners[i]);
						}
						setCellEnabled(oldObj);
						SysUtil.abort();
					}
				}

				filter = new FilterInfo();
				filter.appendFilterItem("paymentType.payType.id", settlementID);
				filter.appendFilterItem("contractId", editData.getContractId());
				if (type.getPayType().getId().toString().equals(progressID)) {
					if (PayRequestBillFactory.getRemoteInstance().exists(filter)) {
						EventListener[] listeners = prmtPayment.getListeners(DataChangeListener.class);
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment.removeDataChangeListener((DataChangeListener) listeners[i]);
						}
						prmtPayment.setData(eventObj.getOldValue());
						FDCMsgBox.showError(prmtPayment, "付完结算款后不能付进度款,即存在付款类别的类型为“结算款”的付款申请单时不能选择“进度款”类型付款类别");
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment.addDataChangeListener((DataChangeListener) listeners[i]);
						}
						setCellEnabled(oldObj);
						SysUtil.abort();
					} else {
						FilterInfo myfilter = new FilterInfo();
						myfilter.appendFilterItem("id", editData.getContractId());
						myfilter.appendFilterItem("hasSettled", Boolean.TRUE);
						if (ContractBillFactory.getRemoteInstance().exists(myfilter)) {
							EventListener[] listeners = prmtPayment.getListeners(DataChangeListener.class);
							for (int i = 0; i < listeners.length; i++) {
								prmtPayment.removeDataChangeListener((DataChangeListener) listeners[i]);
							}
							prmtPayment.setData(eventObj.getOldValue());
							FDCMsgBox.showError(prmtPayment, "合同结算之后不能付进度款！");
							for (int i = 0; i < listeners.length; i++) {
								prmtPayment.addDataChangeListener((DataChangeListener) listeners[i]);
							}
							setCellEnabled(oldObj);
							SysUtil.abort();
						}
					}
				}

				if (type.getPayType().getId().toString().equals(settlementID)) {
					FilterInfo myfilter = new FilterInfo();
					myfilter.appendFilterItem("id", editData.getContractId());
					myfilter.appendFilterItem("hasSettled", Boolean.TRUE);
					if (!ContractBillFactory.getRemoteInstance().exists(myfilter)) {
						EventListener[] listeners = prmtPayment.getListeners(DataChangeListener.class);
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment.removeDataChangeListener((DataChangeListener) listeners[i]);
						}
						prmtPayment.setData(eventObj.getOldValue());
						FDCMsgBox.showError(prmtPayment, "合同必须结算之后才能做结算款类别的付款申请单");
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment.addDataChangeListener((DataChangeListener) listeners[i]);
						}
						setCellEnabled(oldObj);
						SysUtil.abort();
					}

					txtpaymentProportion.setValue(FDCConstants.ZERO);
				}
				// 当付款类别为结算款和保修款时，已完工工程量金额直接就等于结算金额。
				if (type.getPayType().getId().toString().equals(keepID) || type.getPayType().getId().toString().equals(settlementID)) {
					// 简单模式，合同结算，完工与比例可修改
					if (contractBill != null && contractBill.isHasSettled() && isSimpleFinancial) {
						txtpaymentProportion.setEditable(true);
						txtcompletePrjAmt.setEditable(true);
						txtpaymentProportion.setRequired(true);
					} else {
						txtpaymentProportion.setEditable(false);
						txtcompletePrjAmt.setEditable(false);
						txtcompletePrjAmt.setValue(FDCHelper.toBigDecimal(editData.getSettleAmt()));
						txtpaymentProportion.setRequired(false);
					}
				} else {
					txtpaymentProportion.setRequired(true && !isAutoComplete);
					txtpaymentProportion.setEditable(true && !isAutoComplete);
					txtcompletePrjAmt.setEditable(true);
				}

			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		if (isFromProjectFillBill) {

			txtcompletePrjAmt.setEditable(false);
			if (FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue()).compareTo(FDCHelper.ZERO) == 0)
				txtpaymentProportion.setEditable(false);
		}
	}

	private void setCellEnabled(Object oldObj) {
		if (oldObj != null && oldObj instanceof PaymentTypeInfo) {
			PaymentTypeInfo _type = (PaymentTypeInfo) oldObj;
			if (_type.getPayType().getId().toString().equals(PaymentTypeInfo.tempID)) {
				if (this.kdtEntrys.getCell(4, 4) != null) {
					this.kdtEntrys.getCell(4, 4).getStyleAttributes().setLocked(true);
				}
			}
		}
	}

	protected void initListener() {
		// super.initListener();
	}

	protected boolean isUseMainMenuAsTitle() {
		return false;
	}

	protected void planAcctCtrl() throws Exception {
		boolean hasUsed = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_ACCTBUDGET);
		if (hasUsed) {
			Map costAcctPlan = ConPayPlanSplitFactory.getRemoteInstance().getCostAcctPlan(editData.getCurProject().getId().toString(), editData.getPayDate());
			Map planSplitMap = (Map) costAcctPlan.get("planSplitMap");
			Map reqSplitMap = (Map) costAcctPlan.get("reqSplitMap");
			Map allPlanSplitMap = (Map) costAcctPlan.get("allPlanSplitMap");
			Map allReqSplitMap = (Map) costAcctPlan.get("allReqSplitMap");
			if (planSplitMap == null) {
				planSplitMap = new HashMap();
			}
			if (reqSplitMap == null) {
				reqSplitMap = new HashMap();
			}
			if (allPlanSplitMap == null) {
				allPlanSplitMap = new HashMap();
			}
			if (allReqSplitMap == null) {
				allReqSplitMap = new HashMap();
			}

			//
			for (Iterator iter = planSplitMap.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				BigDecimal planAmt = (BigDecimal) planSplitMap.get(key);
				BigDecimal reqAmt = (BigDecimal) reqSplitMap.get(key);
				if (FDCHelper.toBigDecimal(FDCNumberHelper.subtract(planAmt, reqAmt)).signum() < 0) {
					String acctId = key.substring(0, 44);
					CostAccountInfo costAccountInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(acctId));
					FDCMsgBox.showWarning(this, "'" + costAccountInfo.getName() + "' 科目上已经超付");
					return;
				}
			}
		}
	}

	/**
	 * 累计已完工工程量=之前所有状态的付款申请单已完工工程量金额+本次录入的已完工工程量金额<br>
	 * 累计付款比例=累计申请金额（付款情况况表中累计合同内工程款截至本期）/累计已完工工程量<br>
	 * 合同最终结算后：合同结算价
	 * <p>
	 * 界面显示“累计付款比例”已经改为“累计应付付款比例”，之前是词不达意，修改后客户易于理解。Modified by Owen_wen
	 * 2010-6-30 提单号：R100621-226
	 */
	private void loadAllCompletePrjAmt() {
		// if (isSimpleFinancial) {
		// return;
		// }
		allCompletePrjAmt = FDCHelper.ZERO;
		if (contractBill != null && contractBill.isHasSettled() && !isSimpleFinancial) {
			// 已完工工程量不能为0 eric_wang 2010.06.01
			BigDecimal settleAmt = contractBill.getSettleAmt();
			if (FDCHelper.ZERO.equals(settleAmt)) {
				// 弹出框提示
				FDCMsgBox.showError(this, "本币：进度款+结算款不能超过合同结算价-保修金");
				SysUtil.abort();
			} else {
				txtAllCompletePrjAmt.setValue(settleAmt);
				BigDecimal prjAllReqAmt = FDCHelper.toBigDecimal(editData.getPrjAllReqAmt(), 4);
				if (OprtState.ADDNEW.equals(getOprtState()) && bindCellMap.get(PayRequestBillContants.PRJALLREQAMT) != null) {
					prjAllReqAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap.get(PayRequestBillContants.PRJALLREQAMT)).getValue(), 4);
				}
				txtAllPaymentProportion.setValue(FDCHelper.divide(prjAllReqAmt, txtAllCompletePrjAmt.getBigDecimalValue(), 4, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("completePrjAmt");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("createTime", editData.getCreateTime(), CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("contractId", editData.getContractId()));
		view.setFilter(filter);

		PayRequestBillCollection payReqColl = null;
		try {
			payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);

			if (payReqColl != null) {
				for (int i = 0; i < payReqColl.size(); i++) {
					PayRequestBillInfo info = payReqColl.get(i);
					allCompletePrjAmt = allCompletePrjAmt.add(FDCHelper.toBigDecimal(info.getCompletePrjAmt()));
				}
			}
		} catch (BOSException e) {
			handUIException(e);
		}

		txtAllCompletePrjAmt.setValue(FDCHelper.add(allCompletePrjAmt, txtcompletePrjAmt.getBigDecimalValue()));
		if (FDCHelper.toBigDecimal(txtAllCompletePrjAmt.getBigDecimalValue()).compareTo(FDCHelper.ZERO) > 0) {
			BigDecimal prjAllReqAmt = FDCHelper.toBigDecimal(editData.getPrjAllReqAmt(), 4);
			if (OprtState.ADDNEW.equals(getOprtState()) && bindCellMap.get(PayRequestBillContants.PRJALLREQAMT) != null) {
				prjAllReqAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap.get(PayRequestBillContants.PRJALLREQAMT)).getValue(), 4);
			}
			txtAllPaymentProportion.setValue(FDCHelper.divide(prjAllReqAmt, txtAllCompletePrjAmt.getBigDecimalValue(), 4, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));

		}
		
		this.txtCompleteRate.setValue(FDCHelper.multiply(FDCHelper.divide(this.txtAllCompletePrjAmt.getBigDecimalValue(), this.editData.getLatestPrice(), 4, BigDecimal.ROUND_HALF_UP),new BigDecimal(100)));
	}

	private void calAllCompletePrjAmt() {
		// if (isSimpleFinancial) {
		// return;
		// }
		// 合同结算
		if (contractBill != null && contractBill.isHasSettled() && !isSimpleFinancial) {
			txtAllCompletePrjAmt.setValue(contractBill.getSettleAmt());
			BigDecimal prjAllReqAmt = FDCHelper.toBigDecimal(editData.getPrjAllReqAmt(), 4);
			if (OprtState.ADDNEW.equals(getOprtState()) && bindCellMap.get(PayRequestBillContants.PRJALLREQAMT) != null) {
				prjAllReqAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap.get(PayRequestBillContants.PRJALLREQAMT)).getValue(), 4);
			}
			txtAllPaymentProportion.setValue(FDCHelper.divide(prjAllReqAmt, txtAllCompletePrjAmt.getBigDecimalValue(), 4, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			return;
		}
		// 其它情况，包括合同结算+简单模式
		txtAllCompletePrjAmt.setValue(FDCHelper.add(allCompletePrjAmt, txtcompletePrjAmt.getBigDecimalValue()));
		if (bindCellMap.get(PayRequestBillContants.PRJALLREQAMT) != null && FDCHelper.toBigDecimal(txtAllCompletePrjAmt.getBigDecimalValue()).compareTo(FDCHelper.ZERO) > 0) {
			BigDecimal prjAllReqAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap.get(PayRequestBillContants.PRJALLREQAMT)).getValue(), 4);
			txtAllPaymentProportion.setValue(FDCHelper.divide(prjAllReqAmt, txtAllCompletePrjAmt.getBigDecimalValue(), 4, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
		}
	}

	/**
	 * 累计发票金额
	 */
	private void loadInvoiceAmt() {
		allInvoiceAmt = FDCHelper.ZERO;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("invoiceAmt");
		FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new FilterItemInfo("state",
		// FDCBillStateEnum.AUDITTED));
		if (editData.getCreateTime() == null) {
			editData.setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
		filter.getFilterItems().add(new FilterItemInfo("createTime", editData.getCreateTime(), CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("contractId", editData.getContractId()));
		view.setFilter(filter);

		PayRequestBillCollection payReqColl = null;
		try {
			payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
			// 为空时不能return，后边需要计算值
			if (payReqColl != null) {
				for (int i = 0; i < payReqColl.size(); i++) {
					PayRequestBillInfo info = payReqColl.get(i);
					allInvoiceAmt = allInvoiceAmt.add(FDCHelper.toBigDecimal(info.getInvoiceAmt()));
				}
			}
		} catch (BOSException e) {
			handUIException(e);
		}

		txtAllInvoiceAmt.setNumberValue(allInvoiceAmt.add(FDCHelper.toBigDecimal(txtInvoiceAmt.getBigDecimalValue())));
	}

	/**
	 * 
	 * 描述: 控制逻辑更改。针对付款类别类别为进度款的付款申请单，允许付款金额为0或者已实现为0的情况（但是总计的已完工必须大于已付款。
	 * 累计已完工=已审批的付款申请单完工工程量之和
	 * +本期完工工程量，累计已付款=所有的付款申请单合同内工程款之和+本期合同内工程款），不允许同时为0的情况。
	 * 对于不满足条件的付款申请单，在保存，提交时给出提示：累计已完工（已审批的付款申请单完工工程量之和+本期完工工程量）
	 * 小于了累计付款（所有的付款申请单合同内工程款之和+本期合同内工程款），不能执行本操作！
	 * 
	 * @author pengwei_hou Date: 2008-12-04
	 * @throws Exception
	 */
	private void checkCompletePrjAmt() throws Exception {
		if ((isSeparate && contractBill != null && contractBill.isIsCoseSplit())) {
			return;
		}
		String paymentType = editData.getPaymentType().getPayType().getId().toString();
		String progressID = TypeInfo.progressID;
		if (!paymentType.equals(progressID)) {
			return;
		}

		// 预付款类别的申请单提交工程量可以为零
		PaymentTypeInfo paymentTypeInfo = (PaymentTypeInfo) prmtPayment.getValue();
		if (paymentTypeInfo != null && "预付款".equals(paymentTypeInfo.getName())) { // 预付款不是预设数据
			return;
		}
		// 已完工工程量(已完工)
		BigDecimal completePrjAmt = FDCHelper.toBigDecimal(editData.getCompletePrjAmt());
		// 合同内工程款(已付款)
		BigDecimal allProjectPriceInContract = FDCHelper.toBigDecimal(editData.getProjectPriceInContract());

		if (completePrjAmt.compareTo(allProjectPriceInContract) >= 0) {
			return;
		}

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("projectPriceInContract");
		view.getSelector().add("completePrjAmt");
		view.getSelector().add("state");
		view.getSelector().add("entrys.projectPriceInContract");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractId", editData.getContractId()));
		filter.getFilterItems().add(new FilterItemInfo("paymentType.payType.id", progressID));
		filter.getFilterItems().add(new FilterItemInfo("createTime", editData.getCreateTime(), CompareType.LESS_EQUALS));
		// 不包括本次,本次在上面单独加取当前单据最新数据,以避免新增或修改时数据错误
		if (editData.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
		}
		view.setFilter(filter);
		PayRequestBillCollection payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);

		if (payReqColl != null) {
			for (int i = 0; i < payReqColl.size(); i++) {
				PayRequestBillInfo info = payReqColl.get(i);
				// 关闭状态的取付款单
				if (info.getEntrys() != null && info.getEntrys().size() != 0) {
					for (int j = 0; j < info.getEntrys().size(); j++) {
						PayRequestBillEntryInfo entry = info.getEntrys().get(j);
						allProjectPriceInContract = allProjectPriceInContract.add(FDCHelper.toBigDecimal(entry.getProjectPriceInContract()));
					}
				} else {
					allProjectPriceInContract = allProjectPriceInContract.add(FDCHelper.toBigDecimal(info.getProjectPriceInContract()));
				}
				if (info.getState() == FDCBillStateEnum.AUDITTED) {
					completePrjAmt = completePrjAmt.add(FDCHelper.toBigDecimal(info.getCompletePrjAmt()));
				}
			}
		}
		completePrjAmt = FDCHelper.toBigDecimal(completePrjAmt, 2);
		allProjectPriceInContract = FDCHelper.toBigDecimal(allProjectPriceInContract, 2);

		verifyCompletePrjAmt(completePrjAmt, allProjectPriceInContract);
	}

	private void verifyCompletePrjAmt(BigDecimal completePrjAmt, BigDecimal payAmt) {
		if (contractBill != null && contractBill.isIsCoseSplit() && completePrjAmt.compareTo(payAmt) < 0) {
			FDCMsgBox.showWarning(this, "累计已完工（已审批的付款申请单完工工程量之和+本期完工工程量）小于累计付款（所有的付款申请单合同内工程款之和+本期合同内工程款），不能执行本操作！");
			SysUtil.abort();
		}
	}

	protected void prmtPayment_willCommit(CommitEvent e) throws Exception {
		/***
		 * 42. 提单：R090609-207 刘业平 简单模式也可以选择结算款,保修款 by 周勇
		 */
		// if(isSimpleFinancial){
		// prmtPayment.getQueryAgent().resetRuntimeEntityView();
		// if(prmtPayment.getEntityViewInfo()!=null){
		// EntityViewInfo view = prmtPayment.getEntityViewInfo();
		// view.getFilter().getFilterItems().add(new
		// FilterItemInfo("payType.id",PaymentTypeInfo.progressID));
		// prmtPayment.setEntityViewInfo(view);
		// }else{
		// EntityViewInfo view = new EntityViewInfo();
		// view.setFilter(new FilterInfo());
		// view.getFilter().getFilterItems().add(new
		// FilterItemInfo("payType.id",PaymentTypeInfo.progressID));
		// prmtPayment.setEntityViewInfo(view);
		// }
		// }
	}

	protected void prmtPayment_willShow(SelectorEvent e) throws Exception {
		// if(isSimpleFinancial){
		// prmtPayment.getQueryAgent().resetRuntimeEntityView();
		// if(prmtPayment.getEntityViewInfo()!=null){
		// EntityViewInfo view = prmtPayment.getEntityViewInfo();
		// view.getFilter().getFilterItems().add(new
		// FilterItemInfo("payType.id",PaymentTypeInfo.progressID));
		// prmtPayment.setEntityViewInfo(view);
		// }else{
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new
		// FilterItemInfo("payType.id",PaymentTypeInfo.progressID));
		// view.setFilter(filter);
		// prmtPayment.setEntityViewInfo(view);
		// }
		// }
		// FilterInfo filter = new FilterInfo();
		// filter.appendFilterItem("paymentType.name", "预付款");
		// filter.appendFilterItem("contractId", this.editData.getContractId());
		// EntityViewInfo evi = new EntityViewInfo();
		// evi.setFilter(filter);
		//		
		// PayRequestBillCollection cols =
		// PayRequestBillFactory.getRemoteInstance
		// ().getPayRequestBillCollection(evi);
		// int number = getAdvancePaymentNumber();
		// if(cols.size() >= number){
		// prmtPayment.getQueryAgent().resetRuntimeEntityView();
		// EntityViewInfo viewinfo = null;
		// if(prmtPayment.getQueryAgent().getEntityViewInfo() != null){
		// viewinfo = prmtPayment.getQueryAgent().getEntityViewInfo();
		// FilterItemCollection collection =
		// viewinfo.getFilter().getFilterItems();
		// if(collection != null && collection.size() > 0 ){
		// for(int i=0;i<collection.size();i++){
		// if("paymentType.name".equalsIgnoreCase(collection.get(i).
		// getPropertyName())){
		// collection.remove(collection.get(i));
		// }
		// }
		//						
		// }
		// FilterItemInfo itemInfo = new FilterItemInfo("paymentType.name",
		// "预付款",CompareType.NOTEQUALS);
		// viewinfo.getFilter().getFilterItems().add(itemInfo);
		// prmtPayment.getQueryAgent().setEntityViewInfo(viewinfo);
		// }else{
		// viewinfo = new EntityViewInfo();
		// FilterInfo fi = new FilterInfo();
		// FilterItemInfo itemInfo = new FilterItemInfo("paymentType.name",
		// "预付款",CompareType.NOTEQUALS);
		// fi.getFilterItems().add(itemInfo);
		// viewinfo.setFilter(fi);
		// prmtPayment.getQueryAgent().setEntityViewInfo(viewinfo);
		// }
		// }

	}

	/**
	 * 本付款单的合同ID
	 * 
	 * @return contractId
	 */
	public void actionAssociateAcctPay_actionPerformed(ActionEvent e) throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState()) || this.editData == null || this.editData.getId() == null) {
			// 须保存警告
			FDCMsgBox.showWarning(getRes("saveBillFirst"));
			SysUtil.abort();
		}
		super.actionAssociateAcctPay_actionPerformed(e);
		FDCBudgetPeriodInfo period = getFDCBudgetPeriod();
		// this.editData.setAmount(txtAmount.getBigDecimalValue());
		this.editData.setAmount(FDCHelper.toBigDecimal(txtBcAmount.getBigDecimalValue()));
		PayReqAcctPayUI.showPayReqAcctPayUI(this, this.editData, period, getOprtState());
	}

	public static String getPayReqContractId() {
		return payReqContractId;
	}

	/**
	 * 月度请款：将关联成本科目付款计划与关联待签订合同合并到一起
	 */
	public void actionMonthReq_actionPerformed(ActionEvent e) throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState()) || this.editData == null || this.editData.getId() == null) {
			// 须保存警告
			FDCMsgBox.showWarning(getRes("saveBillFirst"));
			SysUtil.abort();
		}
		super.actionMonthReq_actionPerformed(e);
		this.editData.setAmount(FDCHelper.toBigDecimal(txtBcAmount.getBigDecimalValue()));
		FDCBudgetPeriodInfo period = getFDCBudgetPeriod();
		if (period == null) {
			period = FDCBudgetPeriodInfo.getCurrentPeriod(false);
		}
		FDCMonthReqMoneyUI.showFDCMonthReqMoneyUI(this, editData, period, getOprtState());
	}

	public void actionAssociateUnSettled_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.actionAssociateUnSettled_actionPerformed(e);
		String prjId = editData.getCurProject().getId().toString();
		String contractId = editData.getContractId();
		// if exist costaccount month plan it's not a unSettled contract
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("contractBill.id", contractId);
		filter.appendFilterItem("itemType", FDCBudgetAcctItemTypeEnum.CONTRACT_VALUE);
		filter.appendFilterItem("isAdd", Boolean.FALSE);
		filter.appendFilterItem("parent.state", FDCBillStateEnum.AUDITTED_VALUE);

		boolean isExistContractPlan = FDCMonthBudgetAcctEntryFactory.getRemoteInstance().exists(filter);
		if (isExistContractPlan) {
			FDCMsgBox.showWarning(this, "当前合同存在项目月度科目付款计划,不属于待签订合同,不需要做关联!");
			SysUtil.abort();
		}
		FDCBudgetPeriodInfo period = getFDCBudgetPeriod();
		if (period == null) {
			period = FDCBudgetPeriodInfo.getCurrentPeriod(false);
		}
		ContractAssociateAcctPlanUI.showContractAssociateAcctPlanUI(this, prjId, contractId, period, getOprtState());
	}

	public void actionViewPayDetail_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		if (editData == null || editData.getContractId() == null) {
			return;
		}
		// tableHelper.debugCellExp();
		viewPayDetail();

	}

	private void viewPayDetail() throws UIException {
		// if (editData.getId() == null)
		// return;
		String editUIName = null;

		editUIName = PayRequestViewPayDetailUI.class.getName();

		if (editUIName == null)
			return;
		UIContext uiContext = new UIContext(this);
		if (editData.getContractNo() == null) {
			return;
		}
		uiContext.put("contractId", editData.getContractId());
		uiContext.put("createTime", editData.getCreateTime());
		// 创建UI对象并显示
		IUIWindow windows = this.getUIWindow();
		String type = UIFactoryName.NEWWIN;
		if (windows instanceof UIModelDialog || windows == null) {
			type = UIFactoryName.MODEL;
		}
		IUIWindow contractUiWindow = UIFactory.createUIFactory(type).create(editUIName, uiContext, null, "FINDVIEW", WinStyle.SHOW_RESIZE);
		if (contractUiWindow != null) {
			contractUiWindow.show();
		}
	}

	public void txtexchangeRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		if (isFromProjectFillBill) {
			if (kdtEntrys.getCell(rowIndex, columnIndex) != null) {

				kdtEntrys.getCell(rowIndex, columnIndex).setValue(FDCHelper.divide(kdtEntrys.getCell(rowIndex, columnIndex + 1).getValue(), txtexchangeRate.getBigDecimalValue()));
			}
			return;
		}
		if (getDetailTable().getCell(rowIndex, columnIndex) != null) {
			BigDecimal oriAmount = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex, columnIndex).getValue());
			setAmountChange(oriAmount);
		}

		if (((ICell) bindCellMap.get(PayRequestBillContants.PAYPARTAMATLAMT)) != null) {
			BigDecimal oriPMTAmount = FDCHelper.toBigDecimal(((ICell) bindCellMap.get(PayRequestBillContants.PAYPARTAMATLAMT)).getValue());
			setPmtAmoutChange(oriPMTAmount);
		}

		btnInputCollect_actionPerformed(null);
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

	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
		RequestContext request = super.prepareActionSubmit(itemAction);

		return request;
	}

	/**
	 * EditUI提供的初始化handler参数方法
	 */
	protected void PrepareHandlerParam(RequestContext request) {
		super.PrepareHandlerParam(request);

	}

	protected void prepareInitDataHandlerParam(RequestContext request) {
		// 合同Id
		String contractBillId = (String) getUIContext().get("contractBillId");
		if (contractBillId == null) {
			request.put("FDCBillEditUIHandler.ID", getUIContext().get("ID"));
		} else {
			request.put("FDCBillEditUIHandler.contractBillId", contractBillId);
		}
		// TODO 如果在集团处理工作流单据，为空导致后续参数不对
		// 工程项目Id
		BOSUuid projectId = ((BOSUuid) getUIContext().get("projectId"));
		request.put("FDCBillEditUIHandler.projectId", projectId);

		// 合同类型ID
		BOSUuid typeId = (BOSUuid) getUIContext().get("contractTypeId");
		request.put("FDCBillEditUIHandler.typeId", typeId);
	}

	public void actionContractAttachment_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.actionContractAttachment_actionPerformed(e);

		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		if (editData.getContractId() != null) {
			acm.showAttachmentListUIByBoID(editData.getContractId(), this, false);
		} else {
			return;
		}
	}

	/**
	 * 预付款：工程量启用+合同
	 * 
	 * @return
	 */
	private boolean isAdvance() {
		try {
			return isSeparate && FDCUtils.isContractBill(null, editData.getContractId());
		} catch (EASBizException e) {
			this.handUIException(e);
		} catch (BOSException e) {
			this.handUIException(e);
		}
		return false;
	}

	public void actionViewMaterialConfirm_actionPerformed(ActionEvent e) throws Exception {

		// if (OprtState.ADDNEW.equals(getOprtState())) {
		// // 须保存警告
		// FDCMsgBox.showWarning("请先保存单据，再关联材料确认单");
		// SysUtil.abort();
		// }
		UIContext uiContext = new UIContext(this);
		uiContext.put("PayRequestBillInfo", editData);

		// TODO
		// 不能使用this.contractBill.getId().toString()，因为无文本生成的付款申请单contractBill为null；
		// 因为this.getUIContext().get("contractBillId")本来就有值，再放到uiContext中，如果可以共享UIContext可以替换此语，Owen_wen
		// 2010-11-15
		uiContext.put("contractBillId", this.getUIContext().get("contractBillId"));

		// 创建UI对象并显示
		String type = UIFactoryName.MODEL;
		IUIWindow contractUiWindow = UIFactory.createUIFactory(type).create(MaterialConfirmBillSimpleListUI.class.getName(), uiContext, null, this.getOprtState());
		if (contractUiWindow != null) {
			contractUiWindow.show();
		}
	}

	public void setConfirmBillEntryAndPrjAmt() {
		((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI)).setValue(confirmAmts);
		if (this.isAutoComplete == true && this.isSeparate == false) {
			this.txtpaymentProportion.setValue(FDCHelper.ONE_HUNDRED);
		}
		setAmountChange(confirmAmts);
		calAllCompletePrjAmt();
		if (FDCHelper.ZERO.compareTo(confirmAmts) != 0) {
			setCompletePrjAmt();
			((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI)).getStyleAttributes().setLocked(true);
		} else {
			setCompletePrjAmt();
			((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI)).getStyleAttributes().setLocked(false);
		}
	}

	public BigDecimal getConfirmAmts() {
		return confirmAmts;
	}

	public void setConfirmAmts(BigDecimal confirmAmts) {
		this.confirmAmts = confirmAmts;
	}

	/**
	 * 实现 IWorkflowUIEnhancement 接口必须实现的方法getWorkflowUIEnhancement by
	 * Cassiel_peng 2009-10-2
	 */
	public IWorkflowUIEnhancement getWorkflowUIEnhancement() {
		return new DefaultWorkflowUIEnhancement() {
			public List getApporveToolButtons(CoreUIObject uiObject) {

				List toolButtionsList = new ArrayList();
				btnContractExecInfo.setVisible(true);
				toolButtionsList.add(btnContractExecInfo);
				btnViewPayDetail.setVisible(true);
				toolButtionsList.add(btnViewPayDetail);
				btnViewContract.setVisible(true);
				toolButtionsList.add(btnViewContract);
				return toolButtionsList;
			}
		};
	}

	/*
	 * 读取当前单据的附件列表
	 */

	public void fillAttachmnetList() {
		this.cmbAttachment.removeAllItems();
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

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", boId));

			EntityViewInfo evi = new EntityViewInfo();
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			try {
				cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (cols != null && cols.size() > 0) {
				for (Iterator it = cols.iterator(); it.hasNext();) {
					AttachmentInfo attachment = ((BoAttchAssoInfo) it.next()).getAttachment();
					this.cmbAttachment.addItem(attachment);
				}
				this.isHasAttchment = true;
			} else {
				this.isHasAttchment = false;
			}
		}
		this.btnViewAttachment.setEnabled(this.isHasAttchment);
	}

	/*
	 * 查看附件
	 */
	public void actionViewAttachment_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionViewAttachment_actionPerformed(e);
		String attchId = null;
		if (this.cmbAttachment.getSelectedItem() != null && this.cmbAttachment.getSelectedItem() instanceof AttachmentInfo) {
			attchId = ((AttachmentInfo) this.cmbAttachment.getSelectedItem()).getId().toString();
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			acm.viewAttachment(attchId);

		}
	}

	protected void lockContainer(java.awt.Container container) {
		if (lblAttachmentContainer.getName().equals(container.getName())) {
			return;
		} else {
			super.lockContainer(container);
		}
	}

	// 对应付款单为暂估款类型时，提交时对发票金额进行判断，若小于0，则提示：暂估款类型的付款申请单的发票金额累计不能小于0，请修正后提交！
	// 审批时进行同样的校验及提示。
	private void checkTempSmallerThanZero() throws BOSException, EASBizException {
		if (contractBill == null) {
			return;
		}
		Object o = this.prmtPayment.getValue();
		if (o == null || !(o instanceof PaymentTypeInfo) || !(PaymentTypeInfo.tempID.equals(((PaymentTypeInfo) o).getPayType().getId().toString()))) {
			return;
		}
		BigDecimal totalInvoiceAmt = FDCHelper.ZERO;
		String contractId = contractBill.getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("invoiceAmt");
		filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
		filter.getFilterItems().add(new FilterItemInfo("paymentType.payType.id", PaymentTypeInfo.tempID));
		view.setFilter(filter);
		view.setSelector(selector);
		PayRequestBillCollection payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
		for (Iterator iter = payReqColl.iterator(); iter.hasNext();) {
			PayRequestBillInfo payRequest = (PayRequestBillInfo) iter.next();
			totalInvoiceAmt = FDCHelper.add(totalInvoiceAmt, payRequest.getInvoiceAmt());
		}
		// 如果ID为空说明尚未保存;如果不为空有可能是保存之后直接提交也有可能是保存或者是提交了之后重修又修改发票金额并重新来提交，所以必须进行校验
		if (this.editData.getId() != null && ("保存".equals(this.editData.getState().toString()) || "已提交".equals(this.editData.getState().toString()))) {
			PayRequestBillInfo billInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo((new ObjectUuidPK(this.editData.getId())));
			totalInvoiceAmt = FDCHelper.subtract(totalInvoiceAmt, billInfo.getInvoiceAmt());
			totalInvoiceAmt = FDCHelper.add(totalInvoiceAmt, this.txtInvoiceAmt.getBigDecimalValue());
		}
		if (FDCHelper.ZERO.compareTo(totalInvoiceAmt) == 1) {
			FDCMsgBox.showWarning("暂估款类型的付款申请单的累计发票金额不能小于0，请修正后提交！");
			SysUtil.abort();
		}
	}

	/**
	 * 在申请单提交、审批时，检查合同实付款是否大于已实现产值， 若大于，则提示“未结算合同的实付款不能大于已实现产值【合同实付款=已关闭的
	 * 付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申 请单合同内工程款
	 * 合计】”，若启用了本参数，则不能提交或审批通过，若未启用,则在提示之后允许提交、审批。 by jian_wen 2009.12.16
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 * */
	private void check() throws EASBizException, BOSException {
		// 如果对应合同已结算 直接返回
		if (contractBill.isHasSettled()) {
			return;
		}
		// 预付款类别的申请单提交时不按此参数控制
		PaymentTypeInfo paymentTypeInfo = (PaymentTypeInfo) prmtPayment.getValue();
		if (paymentTypeInfo != null && "预付款".equals(paymentTypeInfo.getName())) { // 预付款不是预设数据
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("paymentType.name", "%预付款%", CompareType.NOTLIKE));
			filter.getFilterItems().add(new FilterItemInfo("contractId", editData.getContractId()));
			if (editData.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
			}
			// 存在进度款时受控制
			if (!PayRequestBillFactory.getRemoteInstance().exists(filter)) {
				return;
			}
		}

		BigDecimal payAmt = ContractClientUtils.getPayAmt(contractBill.getId().toString());
		if (payAmt == null) {
			payAmt = FDCHelper.ZERO;
		}
		// 取本次录入的实付款
		BigDecimal amt = FDCHelper.ZERO;
		if (getDetailTable().getCell(rowIndex, columnIndex + 1).getValue() != null) {
			amt = new BigDecimal(getDetailTable().getCell(rowIndex, columnIndex + 1).getValue().toString());
		}
		payAmt = FDCHelper.add(payAmt, amt);
		if (editData.getId() != null) {
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("projectPriceInContract");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString()));
			view.setFilter(filter);
			PayRequestBillCollection payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
			if (payReqColl != null && payReqColl.size() > 0) {
				for (Iterator it = payReqColl.iterator(); it.hasNext();) {
					PayRequestBillInfo info = (PayRequestBillInfo) it.next();
					// 合同实付款 取出数据库所有记录后 加上本次录入 的 为了防止修改时重复记录了 所以要 减去修改前的值
					payAmt = FDCHelper.subtract(payAmt, info.getProjectPriceInContract());
				}
			}
		}

		if (payAmt != null && payAmt.compareTo(FDCHelper.toBigDecimal(txtTotalSettlePrice.getBigDecimalValue())) == 1) {

			if (isControlPay) {// 启用了参数 提示错误信息并中断
				FDCMsgBox.showError("未结算合同的实付款不能大于已实现产值【合同实付款=已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申 请单合同内工程款合计】");
				abort();
			} else {
				if (isMoreSettlement) {// 只启用了合同是否可进行多次结算 没启用 参数 就只 提示 但可以继续操作
					FDCMsgBox.showWarning("未结算合同的实付款不能大于已实现产值【合同实付款=已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申 请单合同内工程款合计】");
				}
			}
		}
	}

	/**
	 * 反关闭付款申请单时，如果合同实付款(不含本次)加本次付款申请单合同内工程款本期发生大于已实现产值，
	 * 若合同还未最终结算且启用了参数“未结算合同的实付款大于已实现产值时是否严格控制”且合同实付款大于累计
	 * 结算金额，则提示“未结算合同的实付款不能大于累计结算金额【合同实付款 =已关闭的付款申请单对应的付款 单合同内工程款合计 +
	 * 未关闭的付款申请单合同内工程款合计】”，则不能提交或审批通过，若未启用,则在提 示之后允许提交、审批。 by jian_wen
	 * 2009.12.25
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkIsUnClose() throws EASBizException, BOSException {
		boolean b = isControlPay;
		BigDecimal completePrjAmt = PayReqUtils.getConCompletePrjAmt(contractBill.getId().toString());
		if (editData.getState().equals(FDCBillStateEnum.SAVED) || editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {

		} else {
			completePrjAmt = FDCHelper.subtract(completePrjAmt, editData.getCompletePrjAmt());
		}

		// 取合同实付款(所有保存在数据库的值)
		BigDecimal payAmt = ContractClientUtils.getPayAmt(contractBill.getId().toString());
		if (payAmt == null) {
			payAmt = FDCHelper.ZERO;
		}
		// 加上本次反关闭时 申请单上的 合同内工程款本位币
		BigDecimal amt = FDCHelper.ZERO;
		if (getDetailTable().getCell(rowIndex, columnIndex + 1).getValue() != null) {
			amt = new BigDecimal(getDetailTable().getCell(rowIndex, columnIndex + 1).getValue().toString());
		}
		// 本次的
		// payAmt = FDCHelper.add(payAmt, amt);//重复
		// 还要减去 付款单关闭时 付款申请单对应的所有付款单内的合同内工程款本位币
		if (this.editData.getId() != null) {
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("projectPriceInContract");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("fdcPayReqID", editData.getId().toString()));
			view.setFilter(filter);
			PaymentBillCollection coll = PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(view);
			if (coll != null && coll.size() > 0) {
				for (Iterator it = coll.iterator(); it.hasNext();) {
					PaymentBillInfo info = (PaymentBillInfo) it.next();
					if (editData.getState().equals(FDCBillStateEnum.SAVED) || editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
						// 不做任何处理
					} else {
						payAmt = FDCHelper.subtract(payAmt, info.getProjectPriceInContract());
					}

				}
			}
		}
		verifyCompletePrjAmt(completePrjAmt, payAmt);
		if (isMoreSettlement) {
			if (payAmt != null && payAmt.compareTo(FDCHelper.toBigDecimal(txtTotalSettlePrice.getBigDecimalValue())) == 1) {

				if (b) {// 启用了参数 提示错误信息并中断
					FDCMsgBox.showError("未结算合同的实付款不能大于已实现产值【合同实付款=已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申 请单合同内工程款合计】");
					abort();
				} else {
					if (isMoreSettlement) {// 只启用了合同是否可进行多次结算 没启用 参数 就只 提示
						// 但可以继续操作
						FDCMsgBox.showWarning("未结算合同的实付款不能大于已实现产值【合同实付款=已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申 请单合同内工程款合计】");
					}
				}
			}
		}
	}

	public boolean destroyWindow() {
		if (getOprtState().equals(OprtState.ADDNEW) && isFromProjectFillBill && editData.getId() != null) {
			try {
				FDCSCHUtils.updateTaskRef(null, isFromProjectFillBill, isFillBillControlStrict, true, editData.getContractId(), new IObjectPK[] { new ObjectUuidPK(editData.getId()) }, null);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.destroyWindow();
	}

	/**
	 * 描述：付款申请单审批过程中可以修改发票金额、发票号、本期完工工程量、本次申请原币。
	 * <p>
	 * 
	 * @author liang_ren969 @date:2010-5-21
	 *         <p>
	 *         <br>
	 *         举例：
	 * 
	 *         <pre>
	 * 	  SwingUtilities.invokeLater(new Runnable(){&lt;p&gt;
	 * 		public void run() {&lt;p&gt;
	 * 			editPayAuditColumn();&lt;p&gt;
	 * 		}
	 * <p>
	 * 	  });
	 */
	private void editAuditPayColumn() {

		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true") && getOprtState().equals(OprtState.EDIT) && actionSave.isVisible()
				&& (editData.getState() == FDCBillStateEnum.SUBMITTED || editData.getState() == FDCBillStateEnum.AUDITTING)) {

			// 首先锁定界面上所有的空间
			// this.lockUIForViewStatus();

			// boolean s = isControlPay;

			/**
			 * 启用了参数--未结算合同的实付款大于已实现产值时是否严格控制
			 */
			// if(!s){
			// 首先给控件个访问的权限，然后设置是否可编辑就可以了！不能使用unloack方法，这样会使所用的控件处于edit的状态
			this.txtInvoiceNumber.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.txtInvoiceNumber.setEditable(true);

			this.txtInvoiceAmt.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.txtInvoiceAmt.setEditable(true);

			/**
			 * 系统参数设置为真的时候，本期完工工程量金额处于不可编辑的状态
			 */
			if (!isAutoComplete) {
				this.txtcompletePrjAmt.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.txtcompletePrjAmt.setEditable(true);
			}

			// R110513-0372：流程中打回后， 可以修改备注，用途和提交付款、实际收款单位
			this.txtUsage.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.txtUsage.setEditable(true);

			this.txtMoneyDesc.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.txtMoneyDesc.setEditable(true);

			if (!PayReqUtils.isContractBill(editData.getContractId()) || !isNotEnterCAS) {
//				this.chkIsPay.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//				this.chkIsPay.setEditable(true);
			}

//			this.prmtrealSupplier.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//			this.prmtrealSupplier.setEditable(true);

			if (this.kdtEntrys.getCell(4, 4) != null) {
				// Add by zhiyuan_tang 2010/07/27
				// R100709-147 关联材料确认单时，金额不允许修改，必须通过关联材料确认单进行修改.
				if (!isPartACon) {
					this.kdtEntrys.getCell(4, 4).getStyleAttributes().setLocked(false);
				} else {
					this.kdtEntrys.getCell(4, 4).getStyleAttributes().setLocked(true);
				}
				// this.kdtEntrys.getCell(4,
				// 4).getStyleAttributes().setLocked(false);
			}

			this.actionSave.setEnabled(true);
			// }

			this.kdtBgEntry.setEditable(true);
			setEditState();

		}

		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true") && getOprtState().equals(STATUS_EDIT)) {
			actionRemove.setEnabled(false);
		}
	}

	private BigDecimal getTotalPayAmtByThisReq(String reqPayId) {
		BigDecimal totalPayedAmt = FDCHelper.ZERO;
		if (reqPayId != null) {

			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select sum(flocalAmount) from T_CAS_PaymentBill where ffdcPayReqID = ?  and fbillstatus =15 ");
			builder.addParam(reqPayId);
			RowSet rs;
			try {
				rs = builder.executeQuery();
				while (rs.next()) {
					totalPayedAmt = FDCHelper.add(totalPayedAmt, rs.getBigDecimal(1));
				}
			} catch (BOSException e1) {
				// TODO Auto-generated catch block
				handUIException(e1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				handUIException(e);
			}
		}

		return totalPayedAmt;
	}

	/**
	 * 新增付款申请单前的参数检查
	 * <p>
	 * 合同是否上传正文控制付款申请单新增。参数值：不提示，提示控制，严格控制。
	 * 默认为不提示。参数值为不提示时，付款申请单新增不受合同是否上传正文的控制；参数值为提示控制时，
	 * 没有上传正文的合同新增付款申请单时，给出提示信息后，仍可以继续操作；参数值为严格控制时， 没有上传正文的合同新增付款申请单时，严格控制不能新增。
	 * 
	 * @author owen_wen 2010-12-07
	 * @throws BOSException
	 * @throws EASBizException
	 * @see PayRequestBillListUI.checkParamForAddNew()
	 */
	private void checkParamForAddNew() {
		// 已上传正文，新增付款申请单不需要检查
		if (ContractClientUtils.isUploadFile4Contract(getUIContext().get("contractBillId").toString()))
			return;

		String returnValue = "";
		BOSUuid companyId = SysContext.getSysContext().getCurrentFIUnit().getId();
		IObjectPK comPK = new ObjectUuidPK(companyId);
		try {
			returnValue = ParamControlFactory.getRemoteInstance().getParamValue(comPK, FDCConstants.FDC_PARAM_FDC324_NEWPAYREQWITHCONTRACTATT);
		} catch (EASBizException e) {
			logger.info("获取参数出现异常：" + e.getMessage());
			e.printStackTrace();
		} catch (BOSException e) {
			logger.info("获取参数出现异常：" + e.getMessage());
			e.printStackTrace();
		}

		if ("0".equals(returnValue)) { // 严格控制
			FDCMsgBox.showInfo("该合同没有上传正文，不能新增付款申请单！");
			SysUtil.abort();
		} else if ("1".equals(returnValue)) { // 提示控制
			int confirmResult = FDCMsgBox.showConfirm2New(this, "该合同没有上传正文，是否继续新增付款申请单？");
			if (confirmResult == FDCMsgBox.NO || confirmResult == FDCMsgBox.CANCEL) // 因为用户可以按ESC关闭窗口，此时confirmResult为FDCMsgBox.CANCEL
				SysUtil.abort();
		}
	}

	/**
	 * 查看预算
	 * 
	 * @author
	 * @date 2011-3-1
	 */
	public void actionViewBudget_actionPerformed(ActionEvent e) throws Exception {
		Map uiContext = getUIContext();
		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();

		String curProject = editData.getCurProject().getDisplayName();
		uiContext.put("curProject", curProject);
		Object obj = prmtPlanHasCon.getValue();
		if (obj == null) {
			obj = prmtPlanUnCon.getValue();
		}
		String curDept = "";
		if (obj == null) {
			FDCMsgBox.showWarning(this, "无预算，暂无法查看！");
			abort();
		} else if (obj instanceof FDCDepConPayPlanContractInfo) {
			curDept = ((FDCDepConPayPlanContractInfo) obj).getHead().getDeptment().getName();
		} else if (obj instanceof FDCDepConPayPlanUnsettledConInfo) {
			curDept = ((FDCDepConPayPlanUnsettledConInfo) obj).getParent().getDeptment().getName();
		}
		uiContext.put("curDept", curDept);
		// 要取界面上的业务日期，否则会出现修改业务日期后未保存，查看预算时月份显示不正确 Added by Owen_wen 2011-05-23
		String planMonth = DateTimeUtils.format(bookDate, "yyyy年MM月");
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

	/*
	 * 本期可用预算
	 */
	protected BigDecimal getBgBalance() throws Exception {
		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();
		BigDecimal actPaied = getActPaied(firstDay, lastDay, projectId);
		BigDecimal floatFund = getFloatFund(firstDay, lastDay, projectId);
		BigDecimal localBudget = getLocalBudget(firstDay, lastDay, projectId);
		return localBudget.subtract(actPaied).subtract(floatFund);
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
	protected BigDecimal getActPaied(Date firstDay, Date lastDay, String projectId) throws BOSException, SQLException {
		BigDecimal actPaied = FDCHelper.ZERO;
		FDCSQLBuilder builderActPaied = new FDCSQLBuilder();
		builderActPaied.appendSql("select sum(FLocalAmount) as actPaied " + "from t_cas_paymentbill  as pay where pay.FBillStatus = 15 " + "and pay.FBizDate >= ");
		builderActPaied.appendParam(firstDay);
		builderActPaied.appendSql(" and pay.FBizDate <= ");
		builderActPaied.appendParam(lastDay);
		builderActPaied.appendSql(" and pay.FCurProjectID = ");
		builderActPaied.appendParam(projectId);
		builderActPaied.appendSql(" and pay.FContractBillID = ");
		builderActPaied.appendParam(editData.getContractId());
		// 查看状态查以前的
		if (STATUS_VIEW.equals(getOprtState())) {
			builderActPaied.appendSql(" and pay.FLastUpdateTime <= ");
			builderActPaied.appendSql("{ts '");
			builderActPaied.appendSql(FMConstants.FORMAT_TIME.format(editData.getLastUpdateTime()));
			builderActPaied.appendSql("' }");
			// 此处精确到时分秒，所以不能用下句
			// builderActPaied.appendParam(editData.getLastUpdateTime());
		}
		// builderActPaied.appendSql(" and pay.FcontractNO = ");
		// builderActPaied.appendParam(editData.getContractNo());
		IRowSet rowSetActPaied = builderActPaied.executeQuery();
		while (rowSetActPaied.next()) {
			if (rowSetActPaied.getString("actPaied") != null) {
				actPaied = rowSetActPaied.getBigDecimal("actPaied");
			}
		}
		return actPaied;
	}

	/**
	 * 计算在途<br>
	 * 
	 * 11-06-08 传平需求，将在途改为已付款前所有金额累积。<br>
	 * 也就是说，未关闭的申请单金额+已关闭的申请单对应的付款单金额之和<br>
	 * by emanon
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected BigDecimal getFloatFund(Date firstDay, Date lastDay, String projectId) throws BOSException, SQLException {
		Set payStates = new HashSet();
		payStates.add(FDCBillStateEnum.SUBMITTED_VALUE);
		payStates.add(FDCBillStateEnum.AUDITTING_VALUE);
		payStates.add(FDCBillStateEnum.AUDITTED_VALUE);
		BigDecimal floatFund = FDCHelper.ZERO;
		FDCSQLBuilder builderFloatFund = new FDCSQLBuilder();
		builderFloatFund.appendSql(" select ");
		builderFloatFund.appendSql(" sum(case reqC when 1 then (case payS when 15 then 0 else payA end ) ");
		builderFloatFund.appendSql(" else (case payS when 15 then reqA-payA else reqA end) end) as floatFund ");
		builderFloatFund.appendSql(" from ( select  ");
		builderFloatFund.appendSql(" req.FAmount as reqA, ");
		builderFloatFund.appendSql(" req.FHasClosed as reqC, ");
		builderFloatFund.appendSql(" pay.FAmount as payA, ");
		builderFloatFund.appendSql(" pay.FBillStatus as payS ");
		builderFloatFund.appendSql(" from T_CON_PayRequestBill as req ");
		builderFloatFund.appendSql(" left join T_CAS_PaymentBill as pay ");
		builderFloatFund.appendSql(" on pay.FFdcPayReqID = req.FID ");
		builderFloatFund.appendSql(" where req.FState in ");
		builderFloatFund.appendSql(FMHelper.setTran2String(payStates));
		builderFloatFund.appendSql(" and req.FCurProjectID = ? ");
		builderFloatFund.addParam(projectId);
		builderFloatFund.appendSql(" and req.FBookedDate >= ? ");
		builderFloatFund.addParam(firstDay);
		builderFloatFund.appendSql(" and req.FBookedDate <= ? ");
		builderFloatFund.addParam(lastDay);
		builderFloatFund.appendSql(" and req.FContractID = ? ");
		builderFloatFund.addParam(editData.getContractId());
		// builderFloatFund.appendSql(" and (pay.FBillStatus != ? ");
		// builderFloatFund.addParam(new Integer(BillStatusEnum.PAYED_VALUE));
		// builderFloatFund.appendSql(" or pay.FID is null) ");
		if (editData.getId() != null) {
			builderFloatFund.appendSql(" and req.FID != ? ");
			builderFloatFund.addParam(editData.getId().toString());
		}
		// 查看状态查以前的
		if (STATUS_VIEW.equals(getOprtState())) {
			builderFloatFund.appendSql(" and req.FLastUpdateTime <= ");
			builderFloatFund.appendSql("{ts '");
			builderFloatFund.appendSql(FMConstants.FORMAT_TIME.format(editData.getLastUpdateTime()));
			builderFloatFund.appendSql("' }");
			// 此处精确到时分秒，所以不能用下句
			// builderFloatFund.appendParam(editData.getLastUpdateTime());
		}
		builderFloatFund.appendSql(" ) as tmp ");
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
	protected BigDecimal getLocalBudget(Date firstDay, Date lastDay, String projectId) throws BOSException, SQLException {
		BigDecimal localBudget = FDCHelper.ZERO;
		FDCDepConPayPlanContractInfo hPlan = (FDCDepConPayPlanContractInfo) prmtPlanHasCon.getValue();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if (hPlan != null) {
			builder.appendSql("select admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
			builder.appendSql("from T_FNC_FDCDepConPayPlanContract as con ");
			builder.appendSql("left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FHeadID ");
			builder.appendSql("left join T_FNC_FDCDepConPayPlanCE as entry on entry.FParentC = con.FID ");
			builder.appendSql("left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
			// builder
			// .appendSql("where (head.FState = '4AUDITTED' or head.FState = '10PUBLISH') ");
			builder.appendSql("where 1=1 ");
			builder.appendSql(" and entry.FMonth >= ");
			builder.appendParam(firstDay);
			builder.appendSql(" and entry.FMonth <= ");
			builder.appendParam(lastDay);
			builder.appendSql(" and con.FID = '");
			builder.appendSql(hPlan.getId().toString()).appendSql("' ");
			builder.appendSql(" and con.FProjectID = ");
			builder.appendParam(projectId);
			builder.appendSql("order by head.FYear desc,head.FMonth DESC");
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				if (rowSet.getString("localBudget") != null) {
					localBudget = rowSet.getBigDecimal("localBudget");
				}
			}
		} else {
			FDCDepConPayPlanUnsettledConInfo uPlan = (FDCDepConPayPlanUnsettledConInfo) prmtPlanUnCon.getValue();
			if (uPlan != null) {
				builder.appendSql(" select admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
				builder.appendSql(" from T_FNC_FDCDepConPayPlanUC as con ");
				builder.appendSql(" left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FParentID ");
				builder.appendSql(" left join T_FNC_FDCDepConPayPlanUE as entry on entry.FParentID = con.FID ");
				builder.appendSql(" left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
				builder.appendSql(" where (head.FState = '4AUDITTED' or head.FState = '10PUBLISH') ");
				builder.appendSql(" and entry.FMonth >= ");
				builder.appendParam(firstDay);
				builder.appendSql(" and entry.FMonth <= ");
				builder.appendParam(lastDay);
				builder.appendSql(" and con.FID = '");
				builder.appendSql(uPlan.getId().toString()).appendSql("' ");
				builder.appendSql(" and con.FProjectID = ");
				builder.appendParam(projectId);
				builder.appendSql("order by head.FYear desc,head.FMonth DESC");
				IRowSet rowSet = builder.executeQuery();
				while (rowSet.next()) {
					if (rowSet.getString("localBudget") != null) {
						localBudget = rowSet.getBigDecimal("localBudget");
					}
				}
			}
		}
		return localBudget;
	}

	/**
	 * 累计申请金额超过合同最新造价给出提示信息
	 * 
	 * @author owen_wen 2011-07-13
	 * @param isControlCost
	 *            启用参数 FDC071_OUTPAYAMOUNT “累计请款超过合同最新造价严格控制”
	 */
	private void showMsg4TotalPayReqAmtMoreThanConPrice(boolean isControlCost) {
		if (isControlCost) { // 严格控制
			FDCMsgBox.showWarning(this, EASResource.getString(fncResPath, "totalPayReqAmt>LastPriceCantSubmit"));
			SysUtil.abort();
		} else {
			if (FDCMsgBox.showConfirm2(this, EASResource.getString(fncResPath, "totalPayReqAmt>LastPriceCanSubmit")) != FDCMsgBox.OK) {
				SysUtil.abort();
			}
		}
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
	public void initUIContentLayout() {
		super.initUIContentLayout();
		kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 850));
	}
	
}