/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractPaymentBillEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPaymentBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCashier;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tbpPaymentBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAccountant;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApprover;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCashier;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlPayBillHeader;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlPayBillEntry;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlPayBillPartA;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlPayBillDetail;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlPayBillMaterial;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContraNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFdcPayType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayeeAccountBank1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayeeBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActFdcPayeeName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayerAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayerBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDayaccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConceit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAccessoryAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLocalAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBillStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettlementNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettlementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contExchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSourceType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contuseDepartment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcapitalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInputCollect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFdcPayeeName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayerAccountBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpaymentProportion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcompletePrjAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettleBizType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFeeType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFpItem;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayeeArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayeeAccountBank2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAllCompletePrjAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQualityGuarante;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAllInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgentCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanItem;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayBillType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDifBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFRecCountry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractNo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFdcPayType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox txtPayeeAccountBank;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayeeBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtActFdcPayeeName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayerAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayerBank;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDayaccount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConceit;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAccessoryAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLocalAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBillStatus;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SettleNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSettlementType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtExchangeRate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurrency;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSourceType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtuseDepartment;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcapitalAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDesc;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayee;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayerAccountBank;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtpaymentProportion;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcompletePrjAmt;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSettleBizType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox comboFeeType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbookedDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox cbPeriod;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox cbProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea txtSummary;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox txtrecProvince;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox txtrecCity;
    protected com.kingdee.bos.ctrl.swing.KDComboBox difPlace;
    protected com.kingdee.bos.ctrl.swing.KDComboBox mergencyState;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFpItem;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayeeArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtUsage;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayeeAccountBank1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPayeeType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAllCompletePrjAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtQualityGuarante;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAllInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkInvoiceDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtInvoiceAmt;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAgentCompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPlanItem;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayBillType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox difBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFRecCountry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntries;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnPartAEntrys;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPartA;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtDetail;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMaterial;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAccountant;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtApprover;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAntiAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApprove;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUntiApprove;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTaoPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCalc;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRefresh;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPaymentPlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClosePayReq;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelClosePayReq;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPay;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewBalance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChequeReimburse;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRefresh;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBalance;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPay;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClosePayReq;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelClosePayReq;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAntiAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChequeReimburse;
    protected com.kingdee.eas.fi.cas.PaymentBillInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionAntiAudit actionAntiAudit = null;
    protected ActionTaoPrint acitonTaoPrint = null;
    protected ActionPaymentPlan actionPaymentPlan = null;
    protected ActionRefresh actionRefresh = null;
    protected ActionClosePayReq actionClosePayReq = null;
    protected ActionCancelClosePayReq actionCancelClosePayReq = null;
    protected ActionPay actionPay = null;
    protected ActionSplit actionSplit = null;
    protected ActionViewBgBalance actionViewBgBalance = null;
    protected ActionChequeReimburse actionChequeReimburse = null;
    protected actionViewBudget actionViewBudget = null;
    protected ActionApprove actionApprove = null;
    protected ActionUntiApprove actionUntiApprove = null;
    /**
     * output class constructor
     */
    public AbstractPaymentBillEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractPaymentBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionWorkflowList
        actionWorkflowList.setEnabled(false);
        actionWorkflowList.setDaemonRun(false);

        _tempStr = resHelper.getString("actionWorkflowList.SHORT_DESCRIPTION");
        actionWorkflowList.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionWorkflowList.LONG_DESCRIPTION");
        actionWorkflowList.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionWorkflowList.NAME");
        actionWorkflowList.putValue(ItemAction.NAME, _tempStr);
         this.actionWorkflowList.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
        this.actionAudit.setBindWorkFlow(true);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAntiAudit
        this.actionAntiAudit = new ActionAntiAudit(this);
        getActionManager().registerAction("actionAntiAudit", actionAntiAudit);
        this.actionAntiAudit.setBindWorkFlow(true);
         this.actionAntiAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //acitonTaoPrint
        this.acitonTaoPrint = new ActionTaoPrint(this);
        getActionManager().registerAction("acitonTaoPrint", acitonTaoPrint);
         this.acitonTaoPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPaymentPlan
        this.actionPaymentPlan = new ActionPaymentPlan(this);
        getActionManager().registerAction("actionPaymentPlan", actionPaymentPlan);
         this.actionPaymentPlan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRefresh
        this.actionRefresh = new ActionRefresh(this);
        getActionManager().registerAction("actionRefresh", actionRefresh);
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClosePayReq
        this.actionClosePayReq = new ActionClosePayReq(this);
        getActionManager().registerAction("actionClosePayReq", actionClosePayReq);
         this.actionClosePayReq.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelClosePayReq
        this.actionCancelClosePayReq = new ActionCancelClosePayReq(this);
        getActionManager().registerAction("actionCancelClosePayReq", actionCancelClosePayReq);
         this.actionCancelClosePayReq.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPay
        this.actionPay = new ActionPay(this);
        getActionManager().registerAction("actionPay", actionPay);
         this.actionPay.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplit
        this.actionSplit = new ActionSplit(this);
        getActionManager().registerAction("actionSplit", actionSplit);
         this.actionSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBgBalance
        this.actionViewBgBalance = new ActionViewBgBalance(this);
        getActionManager().registerAction("actionViewBgBalance", actionViewBgBalance);
         this.actionViewBgBalance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChequeReimburse
        this.actionChequeReimburse = new ActionChequeReimburse(this);
        getActionManager().registerAction("actionChequeReimburse", actionChequeReimburse);
         this.actionChequeReimburse.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBudget
        this.actionViewBudget = new actionViewBudget(this);
        getActionManager().registerAction("actionViewBudget", actionViewBudget);
         this.actionViewBudget.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionApprove
        this.actionApprove = new ActionApprove(this);
        getActionManager().registerAction("actionApprove", actionApprove);
         this.actionApprove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUntiApprove
        this.actionUntiApprove = new ActionUntiApprove(this);
        getActionManager().registerAction("actionUntiApprove", actionUntiApprove);
         this.actionUntiApprove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCashier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tbpPaymentBill = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contAccountant = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApprover = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCashier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pnlPayBillHeader = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlPayBillEntry = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlPayBillPartA = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlPayBillDetail = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlPayBillMaterial = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContraNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFdcPayType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayeeAccountBank1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayeeBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActFdcPayeeName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayerAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayerBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDayaccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConceit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAccessoryAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLocalAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBillStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettlementNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettlementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contExchangeRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSourceType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contuseDepartment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcapitalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnInputCollect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contFdcPayeeName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayerAccountBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpaymentProportion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcompletePrjAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettleBizType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFeeType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFpItem = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayeeArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayeeAccountBank2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAllCompletePrjAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQualityGuarante = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAllInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgentCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanItem = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayBillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDifBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFRecCountry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtContractNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtFdcPayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPayeeAccountBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPayeeBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtActFdcPayeeName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPayerAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPayerBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDayaccount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtConceit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAccessoryAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLocalAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboBillStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7SettleNumber = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSettlementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtExchangeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboSourceType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtuseDepartment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcapitalAmount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtDesc = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPayee = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPayerAccountBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtpaymentProportion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtcompletePrjAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtSettleBizType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboFeeType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkbookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cbProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSummary = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.txtrecProvince = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtrecCity = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.difPlace = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.mergencyState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtFpItem = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPayeeArea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtUsage = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPayeeAccountBank1 = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboPayeeType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtAllCompletePrjAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtQualityGuarante = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAllInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkInvoiceDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtInvoiceNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtAgentCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPlanItem = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtPayBillType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.difBank = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtFRecCountry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.ctnPartAEntrys = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtPartA = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtDetail = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblMaterial = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtAccountant = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtApprover = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAntiAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnApprove = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUntiApprove = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTaoPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCalc = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRefresh = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPaymentPlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClosePayReq = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancelClosePayReq = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPay = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewBalance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChequeReimburse = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemRefresh = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewBalance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPay = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemClosePayReq = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCancelClosePayReq = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAntiAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemChequeReimburse = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contAuditor.setName("contAuditor");
        this.contCashier.setName("contCashier");
        this.tbpPaymentBill.setName("tbpPaymentBill");
        this.contAccountant.setName("contAccountant");
        this.contApprover.setName("contApprover");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtCashier.setName("prmtCashier");
        this.pnlPayBillHeader.setName("pnlPayBillHeader");
        this.pnlPayBillEntry.setName("pnlPayBillEntry");
        this.pnlPayBillPartA.setName("pnlPayBillPartA");
        this.pnlPayBillDetail.setName("pnlPayBillDetail");
        this.pnlPayBillMaterial.setName("pnlPayBillMaterial");
        this.contCompany.setName("contCompany");
        this.contContraNo.setName("contContraNo");
        this.contFdcPayType.setName("contFdcPayType");
        this.contPayeeAccountBank1.setName("contPayeeAccountBank1");
        this.contPayeeBank.setName("contPayeeBank");
        this.contActFdcPayeeName.setName("contActFdcPayeeName");
        this.contPayerAccount.setName("contPayerAccount");
        this.contPayerBank.setName("contPayerBank");
        this.contDayaccount.setName("contDayaccount");
        this.contConceit.setName("contConceit");
        this.contAccessoryAmt.setName("contAccessoryAmt");
        this.contLocalAmt.setName("contLocalAmt");
        this.contAmount.setName("contAmount");
        this.contBillStatus.setName("contBillStatus");
        this.contSettlementNumber.setName("contSettlementNumber");
        this.contSettlementType.setName("contSettlementType");
        this.contExchangeRate.setName("contExchangeRate");
        this.contCurrency.setName("contCurrency");
        this.contSourceType.setName("contSourceType");
        this.contBizDate.setName("contBizDate");
        this.contNumber.setName("contNumber");
        this.contuseDepartment.setName("contuseDepartment");
        this.contcapitalAmount.setName("contcapitalAmount");
        this.contDescription.setName("contDescription");
        this.btnInputCollect.setName("btnInputCollect");
        this.contFdcPayeeName.setName("contFdcPayeeName");
        this.contPayerAccountBank.setName("contPayerAccountBank");
        this.contpaymentProportion.setName("contpaymentProportion");
        this.contcompletePrjAmt.setName("contcompletePrjAmt");
        this.contSettleBizType.setName("contSettleBizType");
        this.contFeeType.setName("contFeeType");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.contFpItem.setName("contFpItem");
        this.contPayeeArea.setName("contPayeeArea");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.contPayeeAccountBank2.setName("contPayeeAccountBank2");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.contAllCompletePrjAmt.setName("contAllCompletePrjAmt");
        this.contQualityGuarante.setName("contQualityGuarante");
        this.contAllInvoiceAmt.setName("contAllInvoiceAmt");
        this.contInvoiceDate.setName("contInvoiceDate");
        this.contInvoiceNumber.setName("contInvoiceNumber");
        this.contInvoiceAmt.setName("contInvoiceAmt");
        this.contAgentCompany.setName("contAgentCompany");
        this.contPlanItem.setName("contPlanItem");
        this.contPayBillType.setName("contPayBillType");
        this.contDifBank.setName("contDifBank");
        this.contFRecCountry.setName("contFRecCountry");
        this.prmtCompany.setName("prmtCompany");
        this.txtContractNo.setName("txtContractNo");
        this.prmtFdcPayType.setName("prmtFdcPayType");
        this.txtPayeeAccountBank.setName("txtPayeeAccountBank");
        this.txtPayeeBank.setName("txtPayeeBank");
        this.prmtActFdcPayeeName.setName("prmtActFdcPayeeName");
        this.prmtPayerAccount.setName("prmtPayerAccount");
        this.prmtPayerBank.setName("prmtPayerBank");
        this.txtDayaccount.setName("txtDayaccount");
        this.txtConceit.setName("txtConceit");
        this.txtAccessoryAmt.setName("txtAccessoryAmt");
        this.txtLocalAmt.setName("txtLocalAmt");
        this.txtAmount.setName("txtAmount");
        this.comboBillStatus.setName("comboBillStatus");
        this.f7SettleNumber.setName("f7SettleNumber");
        this.prmtSettlementType.setName("prmtSettlementType");
        this.txtExchangeRate.setName("txtExchangeRate");
        this.prmtCurrency.setName("prmtCurrency");
        this.comboSourceType.setName("comboSourceType");
        this.pkBizDate.setName("pkBizDate");
        this.txtNumber.setName("txtNumber");
        this.prmtuseDepartment.setName("prmtuseDepartment");
        this.txtcapitalAmount.setName("txtcapitalAmount");
        this.prmtDesc.setName("prmtDesc");
        this.prmtPayee.setName("prmtPayee");
        this.prmtPayerAccountBank.setName("prmtPayerAccountBank");
        this.txtpaymentProportion.setName("txtpaymentProportion");
        this.txtcompletePrjAmt.setName("txtcompletePrjAmt");
        this.prmtSettleBizType.setName("prmtSettleBizType");
        this.comboFeeType.setName("comboFeeType");
        this.pkbookedDate.setName("pkbookedDate");
        this.cbPeriod.setName("cbPeriod");
        this.cbProject.setName("cbProject");
        this.txtSummary.setName("txtSummary");
        this.txtrecProvince.setName("txtrecProvince");
        this.txtrecCity.setName("txtrecCity");
        this.difPlace.setName("difPlace");
        this.mergencyState.setName("mergencyState");
        this.prmtFpItem.setName("prmtFpItem");
        this.prmtPayeeArea.setName("prmtPayeeArea");
        this.txtUsage.setName("txtUsage");
        this.txtPayeeAccountBank1.setName("txtPayeeAccountBank1");
        this.comboPayeeType.setName("comboPayeeType");
        this.txtAllCompletePrjAmt.setName("txtAllCompletePrjAmt");
        this.txtQualityGuarante.setName("txtQualityGuarante");
        this.txtAllInvoiceAmt.setName("txtAllInvoiceAmt");
        this.pkInvoiceDate.setName("pkInvoiceDate");
        this.txtInvoiceNumber.setName("txtInvoiceNumber");
        this.txtInvoiceAmt.setName("txtInvoiceAmt");
        this.prmtAgentCompany.setName("prmtAgentCompany");
        this.txtPlanItem.setName("txtPlanItem");
        this.prmtPayBillType.setName("prmtPayBillType");
        this.difBank.setName("difBank");
        this.prmtFRecCountry.setName("prmtFRecCountry");
        this.kdtEntries.setName("kdtEntries");
        this.ctnPartAEntrys.setName("ctnPartAEntrys");
        this.kdtPartA.setName("kdtPartA");
        this.kdtDetail.setName("kdtDetail");
        this.tblMaterial.setName("tblMaterial");
        this.prmtAccountant.setName("prmtAccountant");
        this.prmtApprover.setName("prmtApprover");
        this.btnAudit.setName("btnAudit");
        this.btnAntiAudit.setName("btnAntiAudit");
        this.btnApprove.setName("btnApprove");
        this.btnUntiApprove.setName("btnUntiApprove");
        this.btnTaoPrint.setName("btnTaoPrint");
        this.btnCalc.setName("btnCalc");
        this.btnRefresh.setName("btnRefresh");
        this.btnPaymentPlan.setName("btnPaymentPlan");
        this.btnClosePayReq.setName("btnClosePayReq");
        this.btnCancelClosePayReq.setName("btnCancelClosePayReq");
        this.btnPay.setName("btnPay");
        this.btnSplit.setName("btnSplit");
        this.btnViewBalance.setName("btnViewBalance");
        this.btnChequeReimburse.setName("btnChequeReimburse");
        this.menuItemRefresh.setName("menuItemRefresh");
        this.menuItemViewBalance.setName("menuItemViewBalance");
        this.menuItemPay.setName("menuItemPay");
        this.menuItemClosePayReq.setName("menuItemClosePayReq");
        this.menuItemCancelClosePayReq.setName("menuItemCancelClosePayReq");
        this.menuItemAudit.setName("menuItemAudit");
        this.menuItemAntiAudit.setName("menuItemAntiAudit");
        this.menuItemSplit.setName("menuItemSplit");
        this.menuItemChequeReimburse.setName("menuItemChequeReimburse");
        // CoreUI		
        this.setPreferredSize(new Dimension(890,600));		
        this.menuItemCalculator.setVisible(true);		
        this.btnAddNew.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuSubmitOption.setEnabled(false);		
        this.menuSubmitOption.setVisible(false);		
        this.chkMenuItemSubmitAndAddNew.setEnabled(false);		
        this.chkMenuItemSubmitAndAddNew.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnAuditResult.setVisible(true);		
        this.btnAuditResult.setEnabled(true);		
        this.menuItemCreateFrom.setEnabled(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setEnabled(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuItemTraceDown.setEnabled(false);		
        this.menuTable1.setVisible(false);		
        this.separatorFW6.setVisible(true);		
        this.separatorFW6.setEnabled(true);		
        this.separatorFW8.setEnabled(false);		
        this.separatorFW8.setVisible(false);		
        this.separatorFW7.setEnabled(false);		
        this.separatorFW7.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(120);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(120);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(120);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contCashier		
        this.contCashier.setBoundLabelText(resHelper.getString("contCashier.boundLabelText"));		
        this.contCashier.setBoundLabelLength(120);		
        this.contCashier.setBoundLabelUnderline(true);
        // tbpPaymentBill		
        this.tbpPaymentBill.setPreferredSize(new Dimension(870,500));
        // contAccountant		
        this.contAccountant.setBoundLabelText(resHelper.getString("contAccountant.boundLabelText"));		
        this.contAccountant.setBoundLabelLength(120);		
        this.contAccountant.setBoundLabelUnderline(true);
        // contApprover		
        this.contApprover.setBoundLabelText(resHelper.getString("contApprover.boundLabelText"));		
        this.contApprover.setBoundLabelLength(120);		
        this.contApprover.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setCommitFormat("$number$");		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setRequired(true);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);		
        this.pkCreateTime.setRequired(true);
        // prmtAuditor		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$number$");		
        this.prmtAuditor.setCommitFormat("$number$");		
        this.prmtAuditor.setEnabled(false);
        // prmtCashier		
        this.prmtCashier.setDisplayFormat("$name$");		
        this.prmtCashier.setEditFormat("$number$");		
        this.prmtCashier.setCommitFormat("$number$");		
        this.prmtCashier.setEnabled(false);
        // pnlPayBillHeader		
        this.pnlPayBillHeader.setPreferredSize(new Dimension(870,510));
        // pnlPayBillEntry		
        this.pnlPayBillEntry.setPreferredSize(new Dimension(870,500));
        // pnlPayBillPartA		
        this.pnlPayBillPartA.setBorder(null);
        // pnlPayBillDetail		
        this.pnlPayBillDetail.setPreferredSize(new Dimension(870,500));
        // pnlPayBillMaterial		
        this.pnlPayBillMaterial.setToolTipText(resHelper.getString("pnlPayBillMaterial.toolTipText"));
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(120);		
        this.contCompany.setBoundLabelUnderline(true);
        // contContraNo		
        this.contContraNo.setBoundLabelLength(120);		
        this.contContraNo.setBoundLabelText(resHelper.getString("contContraNo.boundLabelText"));		
        this.contContraNo.setBoundLabelUnderline(true);
        // contFdcPayType		
        this.contFdcPayType.setBoundLabelText(resHelper.getString("contFdcPayType.boundLabelText"));		
        this.contFdcPayType.setBoundLabelLength(120);		
        this.contFdcPayType.setBoundLabelUnderline(true);
        // contPayeeAccountBank1		
        this.contPayeeAccountBank1.setBoundLabelText(resHelper.getString("contPayeeAccountBank1.boundLabelText"));		
        this.contPayeeAccountBank1.setBoundLabelLength(100);		
        this.contPayeeAccountBank1.setBoundLabelUnderline(true);
        // contPayeeBank		
        this.contPayeeBank.setBoundLabelText(resHelper.getString("contPayeeBank.boundLabelText"));		
        this.contPayeeBank.setBoundLabelLength(100);		
        this.contPayeeBank.setBoundLabelUnderline(true);
        // contActFdcPayeeName		
        this.contActFdcPayeeName.setBoundLabelText(resHelper.getString("contActFdcPayeeName.boundLabelText"));		
        this.contActFdcPayeeName.setBoundLabelLength(100);		
        this.contActFdcPayeeName.setBoundLabelUnderline(true);
        // contPayerAccount		
        this.contPayerAccount.setBoundLabelText(resHelper.getString("contPayerAccount.boundLabelText"));		
        this.contPayerAccount.setBoundLabelLength(100);		
        this.contPayerAccount.setBoundLabelUnderline(true);
        // contPayerBank		
        this.contPayerBank.setBoundLabelText(resHelper.getString("contPayerBank.boundLabelText"));		
        this.contPayerBank.setBoundLabelLength(100);		
        this.contPayerBank.setBoundLabelUnderline(true);
        // contDayaccount		
        this.contDayaccount.setBoundLabelText(resHelper.getString("contDayaccount.boundLabelText"));		
        this.contDayaccount.setBoundLabelLength(100);		
        this.contDayaccount.setBoundLabelUnderline(true);
        // contConceit		
        this.contConceit.setBoundLabelText(resHelper.getString("contConceit.boundLabelText"));		
        this.contConceit.setBoundLabelLength(100);		
        this.contConceit.setBoundLabelUnderline(true);
        // contAccessoryAmt		
        this.contAccessoryAmt.setBoundLabelText(resHelper.getString("contAccessoryAmt.boundLabelText"));		
        this.contAccessoryAmt.setBoundLabelLength(100);		
        this.contAccessoryAmt.setBoundLabelUnderline(true);
        // contLocalAmt		
        this.contLocalAmt.setBoundLabelText(resHelper.getString("contLocalAmt.boundLabelText"));		
        this.contLocalAmt.setBoundLabelLength(100);		
        this.contLocalAmt.setBoundLabelUnderline(true);
        // contAmount		
        this.contAmount.setBoundLabelText(resHelper.getString("contAmount.boundLabelText"));		
        this.contAmount.setBoundLabelLength(100);		
        this.contAmount.setBoundLabelUnderline(true);
        // contBillStatus		
        this.contBillStatus.setBoundLabelText(resHelper.getString("contBillStatus.boundLabelText"));		
        this.contBillStatus.setBoundLabelLength(120);		
        this.contBillStatus.setBoundLabelUnderline(true);		
        this.contBillStatus.setEnabled(false);
        // contSettlementNumber		
        this.contSettlementNumber.setBoundLabelText(resHelper.getString("contSettlementNumber.boundLabelText"));		
        this.contSettlementNumber.setBoundLabelLength(100);		
        this.contSettlementNumber.setBoundLabelUnderline(true);
        // contSettlementType		
        this.contSettlementType.setBoundLabelText(resHelper.getString("contSettlementType.boundLabelText"));		
        this.contSettlementType.setBoundLabelLength(100);		
        this.contSettlementType.setBoundLabelUnderline(true);
        // contExchangeRate		
        this.contExchangeRate.setBoundLabelText(resHelper.getString("contExchangeRate.boundLabelText"));		
        this.contExchangeRate.setBoundLabelLength(100);		
        this.contExchangeRate.setBoundLabelUnderline(true);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // contSourceType		
        this.contSourceType.setBoundLabelText(resHelper.getString("contSourceType.boundLabelText"));		
        this.contSourceType.setBoundLabelLength(120);		
        this.contSourceType.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contuseDepartment		
        this.contuseDepartment.setBoundLabelText(resHelper.getString("contuseDepartment.boundLabelText"));		
        this.contuseDepartment.setBoundLabelLength(100);		
        this.contuseDepartment.setBoundLabelUnderline(true);
        // contcapitalAmount		
        this.contcapitalAmount.setBoundLabelText(resHelper.getString("contcapitalAmount.boundLabelText"));		
        this.contcapitalAmount.setBoundLabelLength(100);		
        this.contcapitalAmount.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // btnInputCollect		
        this.btnInputCollect.setText(resHelper.getString("btnInputCollect.text"));		
        this.btnInputCollect.setBackground(new java.awt.Color(212,208,200));
        this.btnInputCollect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnInputCollect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnInputCollect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    btnInputCollect_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // contFdcPayeeName		
        this.contFdcPayeeName.setBoundLabelText(resHelper.getString("contFdcPayeeName.boundLabelText"));		
        this.contFdcPayeeName.setBoundLabelLength(100);		
        this.contFdcPayeeName.setBoundLabelUnderline(true);
        // contPayerAccountBank		
        this.contPayerAccountBank.setBoundLabelText(resHelper.getString("contPayerAccountBank.boundLabelText"));		
        this.contPayerAccountBank.setBoundLabelLength(100);		
        this.contPayerAccountBank.setBoundLabelUnderline(true);
        // contpaymentProportion		
        this.contpaymentProportion.setBoundLabelText(resHelper.getString("contpaymentProportion.boundLabelText"));		
        this.contpaymentProportion.setBoundLabelLength(100);		
        this.contpaymentProportion.setBoundLabelUnderline(true);
        // contcompletePrjAmt		
        this.contcompletePrjAmt.setBoundLabelText(resHelper.getString("contcompletePrjAmt.boundLabelText"));		
        this.contcompletePrjAmt.setBoundLabelLength(100);		
        this.contcompletePrjAmt.setBoundLabelUnderline(true);
        // contSettleBizType		
        this.contSettleBizType.setBoundLabelText(resHelper.getString("contSettleBizType.boundLabelText"));		
        this.contSettleBizType.setBoundLabelLength(100);		
        this.contSettleBizType.setBoundLabelUnderline(true);		
        this.contSettleBizType.setToolTipText(resHelper.getString("contSettleBizType.toolTipText"));
        // contFeeType		
        this.contFeeType.setBoundLabelText(resHelper.getString("contFeeType.boundLabelText"));		
        this.contFeeType.setToolTipText(resHelper.getString("contFeeType.toolTipText"));		
        this.contFeeType.setBoundLabelLength(100);		
        this.contFeeType.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(120);		
        this.kDLabelContainer3.setEnabled(false);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setToolTipText(resHelper.getString("kDLabelContainer5.toolTipText"));
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setToolTipText(resHelper.getString("kDLabelContainer6.toolTipText"));
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // contFpItem		
        this.contFpItem.setBoundLabelText(resHelper.getString("contFpItem.boundLabelText"));		
        this.contFpItem.setBoundLabelLength(100);		
        this.contFpItem.setBoundLabelUnderline(true);
        // contPayeeArea		
        this.contPayeeArea.setBoundLabelText(resHelper.getString("contPayeeArea.boundLabelText"));		
        this.contPayeeArea.setBoundLabelLength(100);		
        this.contPayeeArea.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // contPayeeAccountBank2		
        this.contPayeeAccountBank2.setBoundLabelText(resHelper.getString("contPayeeAccountBank2.boundLabelText"));		
        this.contPayeeAccountBank2.setBoundLabelLength(100);		
        this.contPayeeAccountBank2.setBoundLabelUnderline(true);		
        this.contPayeeAccountBank2.setVisible(false);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // contAllCompletePrjAmt		
        this.contAllCompletePrjAmt.setBoundLabelText(resHelper.getString("contAllCompletePrjAmt.boundLabelText"));		
        this.contAllCompletePrjAmt.setBoundLabelLength(100);		
        this.contAllCompletePrjAmt.setBoundLabelUnderline(true);
        // contQualityGuarante		
        this.contQualityGuarante.setBoundLabelText(resHelper.getString("contQualityGuarante.boundLabelText"));		
        this.contQualityGuarante.setBoundLabelLength(100);		
        this.contQualityGuarante.setBoundLabelUnderline(true);
        // contAllInvoiceAmt		
        this.contAllInvoiceAmt.setBoundLabelText(resHelper.getString("contAllInvoiceAmt.boundLabelText"));		
        this.contAllInvoiceAmt.setBoundLabelLength(100);		
        this.contAllInvoiceAmt.setBoundLabelUnderline(true);
        // contInvoiceDate		
        this.contInvoiceDate.setBoundLabelText(resHelper.getString("contInvoiceDate.boundLabelText"));		
        this.contInvoiceDate.setBoundLabelLength(100);		
        this.contInvoiceDate.setBoundLabelUnderline(true);
        // contInvoiceNumber		
        this.contInvoiceNumber.setBoundLabelText(resHelper.getString("contInvoiceNumber.boundLabelText"));		
        this.contInvoiceNumber.setBoundLabelLength(100);		
        this.contInvoiceNumber.setBoundLabelUnderline(true);
        // contInvoiceAmt		
        this.contInvoiceAmt.setBoundLabelText(resHelper.getString("contInvoiceAmt.boundLabelText"));		
        this.contInvoiceAmt.setBoundLabelLength(100);		
        this.contInvoiceAmt.setBoundLabelUnderline(true);
        // contAgentCompany		
        this.contAgentCompany.setBoundLabelText(resHelper.getString("contAgentCompany.boundLabelText"));		
        this.contAgentCompany.setBoundLabelLength(120);		
        this.contAgentCompany.setBoundLabelUnderline(true);
        // contPlanItem		
        this.contPlanItem.setBoundLabelText(resHelper.getString("contPlanItem.boundLabelText"));		
        this.contPlanItem.setBoundLabelUnderline(true);		
        this.contPlanItem.setBoundLabelLength(100);
        // contPayBillType		
        this.contPayBillType.setBoundLabelText(resHelper.getString("contPayBillType.boundLabelText"));		
        this.contPayBillType.setBoundLabelLength(120);		
        this.contPayBillType.setBoundLabelUnderline(true);
        // contDifBank		
        this.contDifBank.setBoundLabelText(resHelper.getString("contDifBank.boundLabelText"));		
        this.contDifBank.setBoundLabelLength(100);		
        this.contDifBank.setBoundLabelUnderline(true);
        // contFRecCountry		
        this.contFRecCountry.setBoundLabelText(resHelper.getString("contFRecCountry.boundLabelText"));		
        this.contFRecCountry.setBoundLabelLength(100);		
        this.contFRecCountry.setBoundLabelUnderline(true);		
        this.contFRecCountry.setVisible(true);		
        this.contFRecCountry.setBoundLabelAlignment(7);		
        this.contFRecCountry.setForeground(new java.awt.Color(0,0,0));
        // prmtCompany		
        this.prmtCompany.setEnabled(false);		
        this.prmtCompany.setRequired(true);
        // txtContractNo		
        this.txtContractNo.setEnabled(false);		
        this.txtContractNo.setRequired(true);
        // prmtFdcPayType		
        this.prmtFdcPayType.setDisplayFormat("$name$");		
        this.prmtFdcPayType.setEditFormat("$number$");		
        this.prmtFdcPayType.setCommitFormat("$number$");		
        this.prmtFdcPayType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");		
        this.prmtFdcPayType.setRequired(true);		
        this.prmtFdcPayType.setEnabled(false);
        // txtPayeeAccountBank		
        this.txtPayeeAccountBank.setCommitFormat("$simpleCode$;$number$;$name$;$bankaccountnumber$");		
        this.txtPayeeAccountBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7AccountBankQuery");		
        this.txtPayeeAccountBank.setDisplayFormat("$bankaccountnumber$");		
        this.txtPayeeAccountBank.setEditFormat("$bankaccountnumber$");
        this.txtPayeeAccountBank.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    txtPayeeAccountBank_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtPayeeAccountBank.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    txtPayeeAccountBank_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtPayeeAccountBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtPayeeAccountBank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPayeeBank		
        this.txtPayeeBank.setMaxLength(80);		
        this.txtPayeeBank.setRequired(true);
        // prmtActFdcPayeeName		
        this.prmtActFdcPayeeName.setDisplayFormat("$name$");		
        this.prmtActFdcPayeeName.setEditFormat("$number$");		
        this.prmtActFdcPayeeName.setCommitFormat("$number$");		
        this.prmtActFdcPayeeName.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtActFdcPayeeName.setRequired(true);
        this.prmtActFdcPayeeName.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtActFdcPayeeName_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtPayerAccount		
        this.prmtPayerAccount.setDisplayFormat("$name$");		
        this.prmtPayerAccount.setEditFormat("$number$");		
        this.prmtPayerAccount.setCommitFormat("$number$");		
        this.prmtPayerAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
        this.prmtPayerAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtPayerAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtPayerAccount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtPayerAccount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtPayerAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtPayerAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtPayerBank		
        this.prmtPayerBank.setDisplayFormat("$name$");		
        this.prmtPayerBank.setEditFormat("$number$");		
        this.prmtPayerBank.setCommitFormat("$number$");		
        this.prmtPayerBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7BankQuery");		
        this.prmtPayerBank.setEnabled(false);
        // txtDayaccount		
        this.txtDayaccount.setDataType(1);		
        this.txtDayaccount.setRequired(true);		
        this.txtDayaccount.setEnabled(false);
        // txtConceit		
        this.txtConceit.setEnabled(false);
        // txtAccessoryAmt
        // txtLocalAmt		
        this.txtLocalAmt.setDataType(1);		
        this.txtLocalAmt.setRequired(true);		
        this.txtLocalAmt.setEnabled(false);
        // txtAmount		
        this.txtAmount.setDataType(1);		
        this.txtAmount.setRequired(true);		
        this.txtAmount.setPrecision(2);		
        this.txtAmount.setSupportedEmpty(true);		
        this.txtAmount.setEnabled(false);
        this.txtAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                try {
                    txtAmount_focusGained(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // comboBillStatus		
        this.comboBillStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.fi.cas.BillStatusEnum").toArray());		
        this.comboBillStatus.setEnabled(false);		
        this.comboBillStatus.setRequired(true);
        // f7SettleNumber		
        this.f7SettleNumber.setQueryInfo("com.kingdee.eas.fm.nt.app.ChequeQuery");		
        this.f7SettleNumber.setDisplayFormat("$number$");		
        this.f7SettleNumber.setEditFormat("$number$");		
        this.f7SettleNumber.setCommitFormat("$number$");
        this.f7SettleNumber.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SettleNumber_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.f7SettleNumber.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    f7SettleNumber_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.f7SettleNumber.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    f7SettleNumber_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtSettlementType		
        this.prmtSettlementType.setDisplayFormat("$name$");		
        this.prmtSettlementType.setEditFormat("$number$");		
        this.prmtSettlementType.setCommitFormat("$number$");		
        this.prmtSettlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7SettlementTypeQuery");
        this.prmtSettlementType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSettlementType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtExchangeRate		
        this.txtExchangeRate.setDataType(4);		
        this.txtExchangeRate.setRequired(true);
        this.txtExchangeRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtExchangeRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCurrency		
        this.prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery");		
        this.prmtCurrency.setCommitFormat("$number$");		
        this.prmtCurrency.setDisplayFormat("$name$");		
        this.prmtCurrency.setEditFormat("$number$");		
        this.prmtCurrency.setRequired(true);
        this.prmtCurrency.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCurrency_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboSourceType		
        this.comboSourceType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fi.cas.SourceTypeEnum").toArray());		
        this.comboSourceType.setEnabled(false);		
        this.comboSourceType.setRequired(true);
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        // txtNumber
        // prmtuseDepartment		
        this.prmtuseDepartment.setDisplayFormat("$name$");		
        this.prmtuseDepartment.setEditFormat("$number$");		
        this.prmtuseDepartment.setCommitFormat("$number$");		
        this.prmtuseDepartment.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.prmtuseDepartment.setRequired(true);
        // txtcapitalAmount		
        this.txtcapitalAmount.setEnabled(false);
        // prmtDesc		
        this.prmtDesc.setDisplayFormat("$description$");		
        this.prmtDesc.setEditFormat("$number$");		
        this.prmtDesc.setCommitFormat("$number$;$name$");		
        this.prmtDesc.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7VoucherAbstract");
        // prmtPayee		
        this.prmtPayee.setDisplayFormat("$name$");		
        this.prmtPayee.setEditFormat("$number$");		
        this.prmtPayee.setCommitFormat("$simpleCode$;$number$;$name$");		
        this.prmtPayee.setRequired(true);
        this.prmtPayee.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtPayee_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtPayee.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtPayee_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtPayee.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtPayee_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtPayerAccountBank		
        this.prmtPayerAccountBank.setDisplayFormat("$bankaccountnumber$");		
        this.prmtPayerAccountBank.setEditFormat("$number$");		
        this.prmtPayerAccountBank.setCommitFormat("$number$");		
        this.prmtPayerAccountBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7AccountBankQuery");
        this.prmtPayerAccountBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtPayerAccountBank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtPayerAccountBank.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtPayerAccountBank_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtPayerAccountBank.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtPayerAccountBank_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtpaymentProportion		
        this.txtpaymentProportion.setPrecision(2);		
        this.txtpaymentProportion.setEditable(false);		
        this.txtpaymentProportion.setEnabled(false);		
        this.txtpaymentProportion.setDataType(1);		
        this.txtpaymentProportion.setSupportedEmpty(true);
        // txtcompletePrjAmt		
        this.txtcompletePrjAmt.setDataType(1);		
        this.txtcompletePrjAmt.setEditable(false);		
        this.txtcompletePrjAmt.setPrecision(2);		
        this.txtcompletePrjAmt.setSupportedEmpty(true);		
        this.txtcompletePrjAmt.setEnabled(false);
        // prmtSettleBizType		
        this.prmtSettleBizType.setDisplayFormat("$name$");		
        this.prmtSettleBizType.setEditFormat("$number$");		
        this.prmtSettleBizType.setCommitFormat("$number$");		
        this.prmtSettleBizType.setQueryInfo("com.kingdee.eas.fm.fs.app.SettBizTypeQuery");
        this.prmtSettleBizType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSettleBizType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtSettleBizType.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtSettleBizType_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtSettleBizType.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtSettleBizType_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboFeeType		
        this.comboFeeType.setQueryInfo("com.kingdee.eas.fi.cas.F7FeeTypeQuery");		
        this.comboFeeType.setDisplayFormat("$number$ $name$");		
        this.comboFeeType.setEditFormat("$number$");		
        this.comboFeeType.setEditable(true);		
        this.comboFeeType.setCommitFormat("$number$;$name$");		
        this.comboFeeType.setVisible(true);		
        this.comboFeeType.setEnabled(true);		
        this.comboFeeType.setRequired(true);
        // pkbookedDate		
        this.pkbookedDate.setEnabled(false);
        // cbPeriod		
        this.cbPeriod.setEnabled(false);
        // cbProject		
        this.cbProject.setEnabled(false);
        // txtSummary
        // txtrecProvince		
        this.txtrecProvince.setDisplayFormat("$name$");		
        this.txtrecProvince.setEditFormat("$name$");		
        this.txtrecProvince.setCommitFormat("$number$;$name$");		
        this.txtrecProvince.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7ProvinceQuery");
        this.txtrecProvince.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtrecProvince_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtrecCity		
        this.txtrecCity.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CityQuery");		
        this.txtrecCity.setCommitFormat("$number$;$name$");		
        this.txtrecCity.setEditFormat("$name$");		
        this.txtrecCity.setDisplayFormat("$name$");
        this.txtrecCity.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtrecCity_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtrecCity.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    txtrecCity_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtrecCity.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    txtrecCity_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // difPlace		
        this.difPlace.addItems(EnumUtils.getEnumList("com.kingdee.eas.fi.cas.DifPlaceEnum").toArray());
        // mergencyState		
        this.mergencyState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fi.cas.IsMergencyEnum").toArray());
        // prmtFpItem		
        this.prmtFpItem.setCommitFormat("$number$");		
        this.prmtFpItem.setDisplayFormat("$name$");		
        this.prmtFpItem.setEditFormat("$number$");		
        this.prmtFpItem.setEditable(true);
        // prmtPayeeArea		
        this.prmtPayeeArea.setDisplayFormat("$name$");		
        this.prmtPayeeArea.setEditFormat("$number$");		
        this.prmtPayeeArea.setCommitFormat("$number$");		
        this.prmtPayeeArea.setQueryInfo("com.kingdee.eas.fm.be.F7OpenAreaQuery");
        this.prmtPayeeArea.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtPayeeArea_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtPayeeArea.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtPayeeArea_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtUsage
        // txtPayeeAccountBank1
        // comboPayeeType
        this.comboPayeeType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comboPayeeType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.comboPayeeType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboPayeeType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtAllCompletePrjAmt		
        this.txtAllCompletePrjAmt.setPrecision(2);		
        this.txtAllCompletePrjAmt.setEnabled(false);		
        this.txtAllCompletePrjAmt.setDataType(1);
        // txtQualityGuarante		
        this.txtQualityGuarante.setEnabled(false);		
        this.txtQualityGuarante.setPrecision(2);		
        this.txtQualityGuarante.setDataType(1);
        // txtAllInvoiceAmt		
        this.txtAllInvoiceAmt.setEnabled(false);		
        this.txtAllInvoiceAmt.setPrecision(2);		
        this.txtAllInvoiceAmt.setDataType(1);		
        this.txtAllInvoiceAmt.setSupportedEmpty(true);
        // pkInvoiceDate		
        this.pkInvoiceDate.setEnabled(false);
        // txtInvoiceNumber
        // txtInvoiceAmt		
        this.txtInvoiceAmt.setSupportedEmpty(true);		
        this.txtInvoiceAmt.setPrecision(2);		
        this.txtInvoiceAmt.setDataType(1);
        this.txtInvoiceAmt.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtInvoiceAmt_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtAgentCompany		
        this.prmtAgentCompany.setDisplayFormat(" $name$");		
        this.prmtAgentCompany.setEditFormat(" $number$");		
        this.prmtAgentCompany.setCommitFormat("$number$;$name$");		
        this.prmtAgentCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");
        // txtPlanItem		
        this.txtPlanItem.setMinimumSize(new Dimension(17,2));		
        this.txtPlanItem.setPreferredSize(new Dimension(17,2));
        // prmtPayBillType		
        this.prmtPayBillType.setQueryInfo("com.kingdee.eas.fi.cas.F7PaymentBillTypeQuery");		
        this.prmtPayBillType.setCommitFormat("$number$");		
        this.prmtPayBillType.setEditFormat("$number$");		
        this.prmtPayBillType.setDisplayFormat("$number$ $name$");		
        this.prmtPayBillType.setRequired(true);
        // difBank		
        this.difBank.addItems(EnumUtils.getEnumList("com.kingdee.eas.fi.cas.DifBankEnum").toArray());
        // prmtFRecCountry		
        this.prmtFRecCountry.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CountryQuery");		
        this.prmtFRecCountry.setVisible(true);		
        this.prmtFRecCountry.setEditable(true);		
        this.prmtFRecCountry.setDisplayFormat("$number$ $name$");		
        this.prmtFRecCountry.setEditFormat("$number$");		
        this.prmtFRecCountry.setCommitFormat("$number$");		
        this.prmtFRecCountry.setRequired(false);		
        this.prmtFRecCountry.setEnabled(true);		
        this.prmtFRecCountry.setForeground(new java.awt.Color(0,0,0));
        // kdtEntries
		String kdtEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol0\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntries.setFormatXml(resHelper.translateString("kdtEntries",kdtEntriesStrXML));

                this.kdtEntries.putBindContents("editData",new String[] {"id"});


        // ctnPartAEntrys		
        this.ctnPartAEntrys.setTitle(resHelper.getString("ctnPartAEntrys.title"));		
        this.ctnPartAEntrys.setEnableActive(false);
        // kdtPartA
		String kdtPartAStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>###,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"conNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"conName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"desc\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"contractId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"paymentId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{conNumber}</t:Cell><t:Cell>$Resource{conName}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{desc}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{contractId}</t:Cell><t:Cell>$Resource{paymentId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtPartA.setFormatXml(resHelper.translateString("kdtPartA",kdtPartAStrXML));
        this.kdtPartA.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtPartA_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kdtDetail
		String kdtDetailStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"amtType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"detailAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"periodCostAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"periodPayAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"oppAcct\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"project\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{amtType}</t:Cell><t:Cell>$Resource{detailAmt}</t:Cell><t:Cell>$Resource{periodCostAmt}</t:Cell><t:Cell>$Resource{periodPayAmt}</t:Cell><t:Cell>$Resource{oppAcct}</t:Cell><t:Cell>$Resource{project}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtDetail.setFormatXml(resHelper.translateString("kdtDetail",kdtDetailStrXML));

        

        // tblMaterial
		String tblMaterialStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"reqAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"paidAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"confirmPaidAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"confirmAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"allConfirmAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"materialContractNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"materialContractName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"mainContractNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"mainContractName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"materialContractId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"mainContractId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /></t:ColumnGroup><t:Head><t:Row t:name=\"head1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{reqAmount}</t:Cell><t:Cell>$Resource{paidAmount}</t:Cell><t:Cell>$Resource{confirmPaidAmt}</t:Cell><t:Cell>$Resource{confirmAmt}</t:Cell><t:Cell>$Resource{allConfirmAmt}</t:Cell><t:Cell>$Resource{materialContractNumber}</t:Cell><t:Cell>$Resource{materialContractName}</t:Cell><t:Cell>$Resource{mainContractNumber}</t:Cell><t:Cell>$Resource{mainContractName}</t:Cell><t:Cell>$Resource{materialContractId}</t:Cell><t:Cell>$Resource{mainContractId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMaterial.setFormatXml(resHelper.translateString("tblMaterial",tblMaterialStrXML));

        

        // prmtAccountant		
        this.prmtAccountant.setDisplayFormat("$name$");		
        this.prmtAccountant.setEditFormat("$number$");		
        this.prmtAccountant.setCommitFormat("$number$");		
        this.prmtAccountant.setEnabled(false);
        // prmtApprover		
        this.prmtApprover.setDisplayFormat("$name$");		
        this.prmtApprover.setEditFormat("$number$");		
        this.prmtApprover.setCommitFormat("$number$");		
        this.prmtApprover.setEnabled(false);
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));
        // btnAntiAudit
        this.btnAntiAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAntiAudit.setText(resHelper.getString("btnAntiAudit.text"));		
        this.btnAntiAudit.setToolTipText(resHelper.getString("btnAntiAudit.toolTipText"));
        // btnApprove
        this.btnApprove.setAction((IItemAction)ActionProxyFactory.getProxy(actionApprove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnApprove.setText(resHelper.getString("btnApprove.text"));
        // btnUntiApprove
        this.btnUntiApprove.setAction((IItemAction)ActionProxyFactory.getProxy(actionUntiApprove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUntiApprove.setText(resHelper.getString("btnUntiApprove.text"));
        // btnTaoPrint
        this.btnTaoPrint.setAction((IItemAction)ActionProxyFactory.getProxy(acitonTaoPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTaoPrint.setText(resHelper.getString("btnTaoPrint.text"));		
        this.btnTaoPrint.setToolTipText(resHelper.getString("btnTaoPrint.toolTipText"));
        // btnCalc
        this.btnCalc.setAction((IItemAction)ActionProxyFactory.getProxy(actionCalculator, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCalc.setText(resHelper.getString("btnCalc.text"));		
        this.btnCalc.setToolTipText(resHelper.getString("btnCalc.toolTipText"));
        // btnRefresh
        this.btnRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefresh.setText(resHelper.getString("btnRefresh.text"));		
        this.btnRefresh.setToolTipText(resHelper.getString("btnRefresh.toolTipText"));
        // btnPaymentPlan
        this.btnPaymentPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionPaymentPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPaymentPlan.setText(resHelper.getString("btnPaymentPlan.text"));		
        this.btnPaymentPlan.setToolTipText(resHelper.getString("btnPaymentPlan.toolTipText"));
        // btnClosePayReq
        this.btnClosePayReq.setAction((IItemAction)ActionProxyFactory.getProxy(actionClosePayReq, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClosePayReq.setText(resHelper.getString("btnClosePayReq.text"));		
        this.btnClosePayReq.setToolTipText(resHelper.getString("btnClosePayReq.toolTipText"));
        // btnCancelClosePayReq
        this.btnCancelClosePayReq.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelClosePayReq, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelClosePayReq.setText(resHelper.getString("btnCancelClosePayReq.text"));		
        this.btnCancelClosePayReq.setToolTipText(resHelper.getString("btnCancelClosePayReq.toolTipText"));
        // btnPay
        this.btnPay.setAction((IItemAction)ActionProxyFactory.getProxy(actionPay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPay.setText(resHelper.getString("btnPay.text"));		
        this.btnPay.setToolTipText(resHelper.getString("btnPay.toolTipText"));
        // btnSplit
        this.btnSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplit.setText(resHelper.getString("btnSplit.text"));		
        this.btnSplit.setToolTipText(resHelper.getString("btnSplit.toolTipText"));
        // btnViewBalance
        this.btnViewBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBudget, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBalance.setText(resHelper.getString("btnViewBalance.text"));		
        this.btnViewBalance.setToolTipText(resHelper.getString("btnViewBalance.toolTipText"));
        // btnChequeReimburse
        this.btnChequeReimburse.setAction((IItemAction)ActionProxyFactory.getProxy(actionChequeReimburse, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChequeReimburse.setText(resHelper.getString("btnChequeReimburse.text"));		
        this.btnChequeReimburse.setVisible(false);
        // menuItemRefresh
        this.menuItemRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRefresh.setText(resHelper.getString("menuItemRefresh.text"));
        // menuItemViewBalance
        this.menuItemViewBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewBalance.setText(resHelper.getString("menuItemViewBalance.text"));
        // menuItemPay
        this.menuItemPay.setAction((IItemAction)ActionProxyFactory.getProxy(actionPay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPay.setText(resHelper.getString("menuItemPay.text"));
        // menuItemClosePayReq
        this.menuItemClosePayReq.setAction((IItemAction)ActionProxyFactory.getProxy(actionClosePayReq, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClosePayReq.setText(resHelper.getString("menuItemClosePayReq.text"));		
        this.menuItemClosePayReq.setMnemonic(67);
        // menuItemCancelClosePayReq
        this.menuItemCancelClosePayReq.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelClosePayReq, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelClosePayReq.setText(resHelper.getString("menuItemCancelClosePayReq.text"));		
        this.menuItemCancelClosePayReq.setMnemonic(82);
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setMnemonic(65);
        // menuItemAntiAudit
        this.menuItemAntiAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAntiAudit.setText(resHelper.getString("menuItemAntiAudit.text"));		
        this.menuItemAntiAudit.setMnemonic(85);
        // menuItemSplit
        this.menuItemSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplit.setText(resHelper.getString("menuItemSplit.text"));		
        this.menuItemSplit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_split"));
        // menuItemChequeReimburse
        this.menuItemChequeReimburse.setAction((IItemAction)ActionProxyFactory.getProxy(actionChequeReimburse, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemChequeReimburse.setText(resHelper.getString("menuItemChequeReimburse.text"));		
        this.menuItemChequeReimburse.setVisible(false);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 890, 700));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 890, 700));
        contCreator.setBounds(new Rectangle(20, 626, 385, 19));
        this.add(contCreator, new KDLayout.Constraints(20, 626, 385, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(485, 626, 385, 19));
        this.add(contCreateTime, new KDLayout.Constraints(485, 626, 385, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(20, 648, 385, 19));
        this.add(contAuditor, new KDLayout.Constraints(20, 648, 385, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCashier.setBounds(new Rectangle(20, 670, 385, 19));
        this.add(contCashier, new KDLayout.Constraints(20, 670, 385, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tbpPaymentBill.setBounds(new Rectangle(10, 0, 870, 612));
        this.add(tbpPaymentBill, new KDLayout.Constraints(10, 0, 870, 612, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAccountant.setBounds(new Rectangle(485, 670, 385, 19));
        this.add(contAccountant, new KDLayout.Constraints(485, 670, 385, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contApprover.setBounds(new Rectangle(485, 648, 385, 19));
        this.add(contApprover, new KDLayout.Constraints(485, 648, 385, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contCashier
        contCashier.setBoundEditor(prmtCashier);
        //tbpPaymentBill
        tbpPaymentBill.add(pnlPayBillHeader, resHelper.getString("pnlPayBillHeader.constraints"));
        tbpPaymentBill.add(pnlPayBillEntry, resHelper.getString("pnlPayBillEntry.constraints"));
        tbpPaymentBill.add(pnlPayBillPartA, resHelper.getString("pnlPayBillPartA.constraints"));
        tbpPaymentBill.add(pnlPayBillDetail, resHelper.getString("pnlPayBillDetail.constraints"));
        tbpPaymentBill.add(pnlPayBillMaterial, resHelper.getString("pnlPayBillMaterial.constraints"));
        //pnlPayBillHeader
        pnlPayBillHeader.setLayout(new KDLayout());
        pnlPayBillHeader.putClientProperty("OriginalBounds", new Rectangle(0, 0, 869, 579));        kDSeparator5.setBounds(new Rectangle(8, 91, 850, 5));
        pnlPayBillHeader.add(kDSeparator5, new KDLayout.Constraints(8, 91, 850, 5, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCompany.setBounds(new Rectangle(10, 3, 385, 19));
        pnlPayBillHeader.add(contCompany, new KDLayout.Constraints(10, 3, 385, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContraNo.setBounds(new Rectangle(10, 47, 385, 19));
        pnlPayBillHeader.add(contContraNo, new KDLayout.Constraints(10, 47, 385, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFdcPayType.setBounds(new Rectangle(10, 25, 385, 19));
        pnlPayBillHeader.add(contFdcPayType, new KDLayout.Constraints(10, 25, 385, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayeeAccountBank1.setBounds(new Rectangle(300, 229, 270, 19));
        pnlPayBillHeader.add(contPayeeAccountBank1, new KDLayout.Constraints(300, 229, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayeeBank.setBounds(new Rectangle(300, 207, 270, 19));
        pnlPayBillHeader.add(contPayeeBank, new KDLayout.Constraints(300, 207, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contActFdcPayeeName.setBounds(new Rectangle(585, 185, 270, 19));
        pnlPayBillHeader.add(contActFdcPayeeName, new KDLayout.Constraints(585, 185, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPayerAccount.setBounds(new Rectangle(9, 207, 270, 19));
        pnlPayBillHeader.add(contPayerAccount, new KDLayout.Constraints(9, 207, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayerBank.setBounds(new Rectangle(10, 185, 270, 19));
        pnlPayBillHeader.add(contPayerBank, new KDLayout.Constraints(10, 185, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDayaccount.setBounds(new Rectangle(585, 119, 270, 19));
        pnlPayBillHeader.add(contDayaccount, new KDLayout.Constraints(585, 119, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contConceit.setBounds(new Rectangle(300, 340, 555, 19));
        pnlPayBillHeader.add(contConceit, new KDLayout.Constraints(300, 340, 555, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAccessoryAmt.setBounds(new Rectangle(10, 274, 268, 19));
        pnlPayBillHeader.add(contAccessoryAmt, new KDLayout.Constraints(10, 274, 268, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLocalAmt.setBounds(new Rectangle(300, 274, 270, 19));
        pnlPayBillHeader.add(contLocalAmt, new KDLayout.Constraints(300, 274, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmount.setBounds(new Rectangle(300, 251, 270, 19));
        pnlPayBillHeader.add(contAmount, new KDLayout.Constraints(300, 251, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBillStatus.setBounds(new Rectangle(10, 69, 385, 19));
        pnlPayBillHeader.add(contBillStatus, new KDLayout.Constraints(10, 69, 385, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSettlementNumber.setBounds(new Rectangle(300, 362, 270, 19));
        pnlPayBillHeader.add(contSettlementNumber, new KDLayout.Constraints(300, 362, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSettlementType.setBounds(new Rectangle(10, 362, 270, 19));
        pnlPayBillHeader.add(contSettlementType, new KDLayout.Constraints(10, 362, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contExchangeRate.setBounds(new Rectangle(585, 229, 270, 19));
        pnlPayBillHeader.add(contExchangeRate, new KDLayout.Constraints(585, 229, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurrency.setBounds(new Rectangle(585, 207, 270, 19));
        pnlPayBillHeader.add(contCurrency, new KDLayout.Constraints(585, 207, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSourceType.setBounds(new Rectangle(475, 25, 385, 19));
        pnlPayBillHeader.add(contSourceType, new KDLayout.Constraints(475, 25, 385, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(300, 119, 270, 19));
        pnlPayBillHeader.add(contBizDate, new KDLayout.Constraints(300, 119, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(300, 97, 270, 19));
        pnlPayBillHeader.add(contNumber, new KDLayout.Constraints(300, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contuseDepartment.setBounds(new Rectangle(585, 97, 270, 19));
        pnlPayBillHeader.add(contuseDepartment, new KDLayout.Constraints(585, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contcapitalAmount.setBounds(new Rectangle(585, 274, 270, 19));
        pnlPayBillHeader.add(contcapitalAmount, new KDLayout.Constraints(585, 274, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(300, 296, 555, 19));
        pnlPayBillHeader.add(contDescription, new KDLayout.Constraints(300, 296, 555, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnInputCollect.setBounds(new Rectangle(585, 251, 100, 19));
        pnlPayBillHeader.add(btnInputCollect, new KDLayout.Constraints(585, 251, 100, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFdcPayeeName.setBounds(new Rectangle(300, 185, 270, 19));
        pnlPayBillHeader.add(contFdcPayeeName, new KDLayout.Constraints(300, 185, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayerAccountBank.setBounds(new Rectangle(10, 163, 270, 19));
        pnlPayBillHeader.add(contPayerAccountBank, new KDLayout.Constraints(10, 163, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpaymentProportion.setBounds(new Rectangle(10, 296, 270, 19));
        pnlPayBillHeader.add(contpaymentProportion, new KDLayout.Constraints(10, 296, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcompletePrjAmt.setBounds(new Rectangle(10, 318, 270, 19));
        pnlPayBillHeader.add(contcompletePrjAmt, new KDLayout.Constraints(10, 318, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSettleBizType.setBounds(new Rectangle(10, 229, 270, 19));
        pnlPayBillHeader.add(contSettleBizType, new KDLayout.Constraints(10, 229, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFeeType.setBounds(new Rectangle(10, 141, 270, 19));
        pnlPayBillHeader.add(contFeeType, new KDLayout.Constraints(10, 141, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(10, 97, 270, 19));
        pnlPayBillHeader.add(kDLabelContainer1, new KDLayout.Constraints(10, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(10, 119, 270, 19));
        pnlPayBillHeader.add(kDLabelContainer2, new KDLayout.Constraints(10, 119, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(476, 47, 385, 19));
        pnlPayBillHeader.add(kDLabelContainer3, new KDLayout.Constraints(476, 47, 385, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer4.setBounds(new Rectangle(10, 450, 846, 58));
        pnlPayBillHeader.add(kDLabelContainer4, new KDLayout.Constraints(10, 450, 846, 58, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(300, 141, 270, 19));
        pnlPayBillHeader.add(kDLabelContainer5, new KDLayout.Constraints(300, 141, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(585, 141, 270, 19));
        pnlPayBillHeader.add(kDLabelContainer6, new KDLayout.Constraints(585, 141, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer7.setBounds(new Rectangle(585, 163, 270, 19));
        pnlPayBillHeader.add(kDLabelContainer7, new KDLayout.Constraints(585, 163, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer8.setBounds(new Rectangle(10, 251, 270, 19));
        pnlPayBillHeader.add(kDLabelContainer8, new KDLayout.Constraints(10, 251, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFpItem.setBounds(new Rectangle(10, 340, 270, 19));
        pnlPayBillHeader.add(contFpItem, new KDLayout.Constraints(10, 340, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayeeArea.setBounds(new Rectangle(300, 384, 270, 19));
        pnlPayBillHeader.add(contPayeeArea, new KDLayout.Constraints(300, 384, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer9.setBounds(new Rectangle(300, 318, 555, 19));
        pnlPayBillHeader.add(kDLabelContainer9, new KDLayout.Constraints(300, 318, 555, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPayeeAccountBank2.setBounds(new Rectangle(300, 229, 270, 19));
        pnlPayBillHeader.add(contPayeeAccountBank2, new KDLayout.Constraints(300, 229, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer10.setBounds(new Rectangle(300, 163, 270, 19));
        pnlPayBillHeader.add(kDLabelContainer10, new KDLayout.Constraints(300, 163, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAllCompletePrjAmt.setBounds(new Rectangle(10, 406, 270, 19));
        pnlPayBillHeader.add(contAllCompletePrjAmt, new KDLayout.Constraints(10, 406, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contQualityGuarante.setBounds(new Rectangle(300, 406, 270, 19));
        pnlPayBillHeader.add(contQualityGuarante, new KDLayout.Constraints(300, 406, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAllInvoiceAmt.setBounds(new Rectangle(585, 406, 270, 19));
        pnlPayBillHeader.add(contAllInvoiceAmt, new KDLayout.Constraints(585, 406, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInvoiceDate.setBounds(new Rectangle(10, 428, 270, 19));
        pnlPayBillHeader.add(contInvoiceDate, new KDLayout.Constraints(10, 428, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoiceNumber.setBounds(new Rectangle(300, 428, 270, 19));
        pnlPayBillHeader.add(contInvoiceNumber, new KDLayout.Constraints(300, 428, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoiceAmt.setBounds(new Rectangle(585, 428, 270, 19));
        pnlPayBillHeader.add(contInvoiceAmt, new KDLayout.Constraints(585, 428, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAgentCompany.setBounds(new Rectangle(475, 3, 385, 19));
        pnlPayBillHeader.add(contAgentCompany, new KDLayout.Constraints(475, 3, 385, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPlanItem.setBounds(new Rectangle(10, 340, 270, 19));
        pnlPayBillHeader.add(contPlanItem, new KDLayout.Constraints(10, 340, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayBillType.setBounds(new Rectangle(476, 69, 385, 19));
        pnlPayBillHeader.add(contPayBillType, new KDLayout.Constraints(476, 69, 385, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDifBank.setBounds(new Rectangle(585, 384, 270, 19));
        pnlPayBillHeader.add(contDifBank, new KDLayout.Constraints(585, 384, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFRecCountry.setBounds(new Rectangle(10, 384, 270, 19));
        pnlPayBillHeader.add(contFRecCountry, new KDLayout.Constraints(10, 384, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCompany
        contCompany.setBoundEditor(prmtCompany);
        //contContraNo
        contContraNo.setBoundEditor(txtContractNo);
        //contFdcPayType
        contFdcPayType.setBoundEditor(prmtFdcPayType);
        //contPayeeAccountBank1
        contPayeeAccountBank1.setBoundEditor(txtPayeeAccountBank);
        //contPayeeBank
        contPayeeBank.setBoundEditor(txtPayeeBank);
        //contActFdcPayeeName
        contActFdcPayeeName.setBoundEditor(prmtActFdcPayeeName);
        //contPayerAccount
        contPayerAccount.setBoundEditor(prmtPayerAccount);
        //contPayerBank
        contPayerBank.setBoundEditor(prmtPayerBank);
        //contDayaccount
        contDayaccount.setBoundEditor(txtDayaccount);
        //contConceit
        contConceit.setBoundEditor(txtConceit);
        //contAccessoryAmt
        contAccessoryAmt.setBoundEditor(txtAccessoryAmt);
        //contLocalAmt
        contLocalAmt.setBoundEditor(txtLocalAmt);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contBillStatus
        contBillStatus.setBoundEditor(comboBillStatus);
        //contSettlementNumber
        contSettlementNumber.setBoundEditor(f7SettleNumber);
        //contSettlementType
        contSettlementType.setBoundEditor(prmtSettlementType);
        //contExchangeRate
        contExchangeRate.setBoundEditor(txtExchangeRate);
        //contCurrency
        contCurrency.setBoundEditor(prmtCurrency);
        //contSourceType
        contSourceType.setBoundEditor(comboSourceType);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contuseDepartment
        contuseDepartment.setBoundEditor(prmtuseDepartment);
        //contcapitalAmount
        contcapitalAmount.setBoundEditor(txtcapitalAmount);
        //contDescription
        contDescription.setBoundEditor(prmtDesc);
        //contFdcPayeeName
        contFdcPayeeName.setBoundEditor(prmtPayee);
        //contPayerAccountBank
        contPayerAccountBank.setBoundEditor(prmtPayerAccountBank);
        //contpaymentProportion
        contpaymentProportion.setBoundEditor(txtpaymentProportion);
        //contcompletePrjAmt
        contcompletePrjAmt.setBoundEditor(txtcompletePrjAmt);
        //contSettleBizType
        contSettleBizType.setBoundEditor(prmtSettleBizType);
        //contFeeType
        contFeeType.setBoundEditor(comboFeeType);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(pkbookedDate);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(cbPeriod);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(cbProject);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtSummary);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtrecProvince);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtrecCity);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(difPlace);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(mergencyState);
        //contFpItem
        contFpItem.setBoundEditor(prmtFpItem);
        //contPayeeArea
        contPayeeArea.setBoundEditor(prmtPayeeArea);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtUsage);
        //contPayeeAccountBank2
        contPayeeAccountBank2.setBoundEditor(txtPayeeAccountBank1);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(comboPayeeType);
        //contAllCompletePrjAmt
        contAllCompletePrjAmt.setBoundEditor(txtAllCompletePrjAmt);
        //contQualityGuarante
        contQualityGuarante.setBoundEditor(txtQualityGuarante);
        //contAllInvoiceAmt
        contAllInvoiceAmt.setBoundEditor(txtAllInvoiceAmt);
        //contInvoiceDate
        contInvoiceDate.setBoundEditor(pkInvoiceDate);
        //contInvoiceNumber
        contInvoiceNumber.setBoundEditor(txtInvoiceNumber);
        //contInvoiceAmt
        contInvoiceAmt.setBoundEditor(txtInvoiceAmt);
        //contAgentCompany
        contAgentCompany.setBoundEditor(prmtAgentCompany);
        //contPlanItem
        contPlanItem.setBoundEditor(txtPlanItem);
        //contPayBillType
        contPayBillType.setBoundEditor(prmtPayBillType);
        //contDifBank
        contDifBank.setBoundEditor(difBank);
        //contFRecCountry
        contFRecCountry.setBoundEditor(prmtFRecCountry);
        //pnlPayBillEntry
        pnlPayBillEntry.setLayout(new KDLayout());
        pnlPayBillEntry.putClientProperty("OriginalBounds", new Rectangle(0, 0, 869, 579));        kdtEntries.setBounds(new Rectangle(10, 10, 850, 450));
        pnlPayBillEntry.add(kdtEntries, new KDLayout.Constraints(10, 10, 850, 450, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //pnlPayBillPartA
        pnlPayBillPartA.setLayout(new KDLayout());
        pnlPayBillPartA.putClientProperty("OriginalBounds", new Rectangle(0, 0, 869, 579));        ctnPartAEntrys.setBounds(new Rectangle(0, 0, 718, 468));
        pnlPayBillPartA.add(ctnPartAEntrys, new KDLayout.Constraints(0, 0, 718, 468, 0));
        //ctnPartAEntrys
        ctnPartAEntrys.getContentPane().setLayout(new KDLayout());
        ctnPartAEntrys.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 718, 468));        kdtPartA.setBounds(new Rectangle(0, 0, 738, 438));
        ctnPartAEntrys.getContentPane().add(kdtPartA, new KDLayout.Constraints(0, 0, 738, 438, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlPayBillDetail
pnlPayBillDetail.setLayout(new BorderLayout(0, 0));        pnlPayBillDetail.add(kdtDetail, BorderLayout.CENTER);
        //pnlPayBillMaterial
pnlPayBillMaterial.setLayout(new BorderLayout(0, 0));        pnlPayBillMaterial.add(tblMaterial, BorderLayout.CENTER);
        //contAccountant
        contAccountant.setBoundEditor(prmtAccountant);
        //contApprover
        contApprover.setBoundEditor(prmtApprover);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(menuItemRefresh);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemViewBalance);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemPay);
        menuBiz.add(menuItemClosePayReq);
        menuBiz.add(menuItemCancelClosePayReq);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemAntiAudit);
        menuBiz.add(menuItemSplit);
        menuBiz.add(menuItemChequeReimburse);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDMenuItemSendMessage);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnAntiAudit);
        this.toolBar.add(btnApprove);
        this.toolBar.add(btnUntiApprove);
        this.toolBar.add(btnTaoPrint);
        this.toolBar.add(btnCalc);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnPaymentPlan);
        this.toolBar.add(btnClosePayReq);
        this.toolBar.add(btnCancelClosePayReq);
        this.toolBar.add(btnPay);
        this.toolBar.add(btnSplit);
        this.toolBar.add(btnViewBalance);
        this.toolBar.add(btnChequeReimburse);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("cashier", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCashier, "data");
		dataBinder.registerBinding("company", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtCompany, "data");
		dataBinder.registerBinding("fdcPayType", com.kingdee.eas.fdc.basedata.PaymentTypeInfo.class, this.prmtFdcPayType, "data");
		dataBinder.registerBinding("payeeBank", String.class, this.txtPayeeBank, "text");
		dataBinder.registerBinding("actFdcPayeeName", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtActFdcPayeeName, "data");
		dataBinder.registerBinding("payerAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtPayerAccount, "data");
		dataBinder.registerBinding("payerBank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.prmtPayerBank, "data");
		dataBinder.registerBinding("dayaccount", java.math.BigDecimal.class, this.txtDayaccount, "value");
		dataBinder.registerBinding("conceit", String.class, this.txtConceit, "text");
		dataBinder.registerBinding("accessoryAmt", int.class, this.txtAccessoryAmt, "value");
		dataBinder.registerBinding("localAmt", java.math.BigDecimal.class, this.txtLocalAmt, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("billStatus", com.kingdee.eas.fi.cas.BillStatusEnum.class, this.comboBillStatus, "selectedItem");
		dataBinder.registerBinding("settlementNumber", String.class, this.f7SettleNumber, "data");
		dataBinder.registerBinding("settlementType", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.prmtSettlementType, "data");
		dataBinder.registerBinding("exchangeRate", java.math.BigDecimal.class, this.txtExchangeRate, "value");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtCurrency, "data");
		dataBinder.registerBinding("sourceType", com.kingdee.eas.fi.cas.SourceTypeEnum.class, this.comboSourceType, "selectedItem");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("useDepartment", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtuseDepartment, "data");
		dataBinder.registerBinding("capitalAmount", String.class, this.txtcapitalAmount, "text");
		dataBinder.registerBinding("description", String.class, this.prmtDesc, "data");
		dataBinder.registerBinding("payeeID", String.class, this.prmtPayee, "userObject");
		dataBinder.registerBinding("payerAccountBank", com.kingdee.eas.basedata.assistant.AccountBankInfo.class, this.prmtPayerAccountBank, "data");
		dataBinder.registerBinding("bizType", com.kingdee.eas.fm.fs.SettBizTypeInfo.class, this.prmtSettleBizType, "data");
		dataBinder.registerBinding("feeType", com.kingdee.eas.fi.cas.FeeTypeEnum.class, this.comboFeeType, "data");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.cbProject, "data");
		dataBinder.registerBinding("summary", String.class, this.txtSummary, "_multiLangItem");
		dataBinder.registerBinding("recProvince", String.class, this.txtrecProvince, "data");
		dataBinder.registerBinding("recCity", String.class, this.txtrecCity, "data");
		dataBinder.registerBinding("isDifferPlace", com.kingdee.eas.fi.cas.DifPlaceEnum.class, this.difPlace, "selectedItem");
		dataBinder.registerBinding("isEmergency", com.kingdee.eas.fi.cas.IsMergencyEnum.class, this.mergencyState, "selectedItem");
		dataBinder.registerBinding("payeeArea", com.kingdee.eas.fm.be.OpenAreaInfo.class, this.prmtPayeeArea, "data");
		dataBinder.registerBinding("usage", String.class, this.txtUsage, "text");
		dataBinder.registerBinding("agentPayCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtAgentCompany, "data");
		dataBinder.registerBinding("payBillType", com.kingdee.eas.fi.cas.PaymentBillTypeInfo.class, this.prmtPayBillType, "data");
		dataBinder.registerBinding("isDifBank", com.kingdee.eas.fi.cas.DifBankEnum.class, this.difBank, "selectedItem");
		dataBinder.registerBinding("FRecCountry", com.kingdee.eas.basedata.assistant.CountryInfo.class, this.prmtFRecCountry, "data");
		dataBinder.registerBinding("entries", com.kingdee.eas.fi.cas.PaymentBillEntryInfo.class, this.kdtEntries, "userObject");
		dataBinder.registerBinding("entries.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntries, "id.text");
		dataBinder.registerBinding("accountant", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAccountant, "data");
		dataBinder.registerBinding("approver", com.kingdee.eas.base.permission.UserInfo.class, this.prmtApprover, "data");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionFirst, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionPre, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionNext, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionLast, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionPrint, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionPrintPreview, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionCreateFrom, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionPay, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionEdit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionCancelClosePayReq, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionRemove, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionTraceUp, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAntiAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionPaymentPlan, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionClosePayReq, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionRefresh, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.PaymentBillEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fi.cas.PaymentBillInfo)ov;
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cashier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fdcPayType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payeeBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actFdcPayeeName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payerAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payerBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dayaccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conceit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accessoryAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("localAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sourceType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("useDepartment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("capitalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payeeID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payerAccountBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("feeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("summary", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recProvince", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recCity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isDifferPlace", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isEmergency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payeeArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("usage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("agentPayCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payBillType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isDifBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("FRecCountry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accountant", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("approver", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		            this.actionFirst.setVisible(false);
		            this.actionFirst.setEnabled(false);
		            this.actionPre.setVisible(false);
		            this.actionPre.setEnabled(false);
		            this.actionNext.setVisible(false);
		            this.actionNext.setEnabled(false);
		            this.actionLast.setVisible(false);
		            this.actionLast.setEnabled(false);
		            this.actionPrint.setVisible(false);
		            this.actionPrint.setEnabled(false);
		            this.actionPrintPreview.setVisible(false);
		            this.actionPrintPreview.setEnabled(false);
		            this.actionCreateFrom.setVisible(false);
		            this.actionCreateFrom.setEnabled(false);
		            this.actionPay.setVisible(false);
		            this.actionPay.setEnabled(false);
		            this.actionAudit.setVisible(false);
		            this.actionAudit.setEnabled(false);
		            this.actionEdit.setEnabled(false);
		            this.actionCancelClosePayReq.setVisible(false);
		            this.actionCancelClosePayReq.setEnabled(false);
		            this.actionRemove.setEnabled(false);
		            this.actionTraceUp.setVisible(false);
		            this.actionTraceUp.setEnabled(false);
		            this.actionAntiAudit.setVisible(false);
		            this.actionAntiAudit.setEnabled(false);
		            this.actionPaymentPlan.setEnabled(false);
		            this.actionClosePayReq.setVisible(false);
		            this.actionClosePayReq.setEnabled(false);
		            this.actionRefresh.setVisible(false);
		            this.actionRefresh.setEnabled(false);
        }
    }

    /**
     * output btnInputCollect_actionPerformed method
     */
    protected void btnInputCollect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnInputCollect_mouseClicked method
     */
    protected void btnInputCollect_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output txtPayeeAccountBank_willCommit method
     */
    protected void txtPayeeAccountBank_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output txtPayeeAccountBank_willShow method
     */
    protected void txtPayeeAccountBank_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output txtPayeeAccountBank_dataChanged method
     */
    protected void txtPayeeAccountBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtActFdcPayeeName_dataChanged method
     */
    protected void prmtActFdcPayeeName_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtPayerAccount_willCommit method
     */
    protected void prmtPayerAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtPayerAccount_dataChanged method
     */
    protected void prmtPayerAccount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtPayerAccount_willShow method
     */
    protected void prmtPayerAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output txtAmount_dataChanged method
     */
    protected void txtAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtAmount_focusGained method
     */
    protected void txtAmount_focusGained(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output f7SettleNumber_dataChanged method
     */
    protected void f7SettleNumber_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7SettleNumber_willShow method
     */
    protected void f7SettleNumber_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output f7SettleNumber_willCommit method
     */
    protected void f7SettleNumber_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtSettlementType_dataChanged method
     */
    protected void prmtSettlementType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtExchangeRate_dataChanged method
     */
    protected void txtExchangeRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCurrency_dataChanged method
     */
    protected void prmtCurrency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtPayee_willCommit method
     */
    protected void prmtPayee_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtPayee_dataChanged method
     */
    protected void prmtPayee_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtPayee_willShow method
     */
    protected void prmtPayee_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtPayerAccountBank_dataChanged method
     */
    protected void prmtPayerAccountBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtPayerAccountBank_willCommit method
     */
    protected void prmtPayerAccountBank_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtPayerAccountBank_willShow method
     */
    protected void prmtPayerAccountBank_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtSettleBizType_dataChanged method
     */
    protected void prmtSettleBizType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtSettleBizType_willShow method
     */
    protected void prmtSettleBizType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtSettleBizType_willCommit method
     */
    protected void prmtSettleBizType_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output txtrecProvince_dataChanged method
     */
    protected void txtrecProvince_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtrecCity_dataChanged method
     */
    protected void txtrecCity_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
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
    }

    /**
     * output prmtPayeeArea_willCommit method
     */
    protected void prmtPayeeArea_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtPayeeArea_willShow method
     */
    protected void prmtPayeeArea_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output comboPayeeType_actionPerformed method
     */
    protected void comboPayeeType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboPayeeType_itemStateChanged method
     */
    protected void comboPayeeType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output txtInvoiceAmt_dataChanged method
     */
    protected void txtInvoiceAmt_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtPartA_editStopped method
     */
    protected void kdtPartA_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("cashier.*"));
        sic.add(new SelectorItemInfo("company.*"));
        sic.add(new SelectorItemInfo("fdcPayType.*"));
        sic.add(new SelectorItemInfo("payeeBank"));
        sic.add(new SelectorItemInfo("actFdcPayeeName.*"));
        sic.add(new SelectorItemInfo("payerAccount.*"));
        sic.add(new SelectorItemInfo("payerBank.*"));
        sic.add(new SelectorItemInfo("dayaccount"));
        sic.add(new SelectorItemInfo("conceit"));
        sic.add(new SelectorItemInfo("accessoryAmt"));
        sic.add(new SelectorItemInfo("localAmt"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("billStatus"));
        sic.add(new SelectorItemInfo("settlementNumber.*"));
        sic.add(new SelectorItemInfo("settlementType.*"));
        sic.add(new SelectorItemInfo("exchangeRate"));
        sic.add(new SelectorItemInfo("currency.*"));
        sic.add(new SelectorItemInfo("sourceType"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("useDepartment.*"));
        sic.add(new SelectorItemInfo("capitalAmount"));
        sic.add(new SelectorItemInfo("description.*"));
        sic.add(new SelectorItemInfo("payeeID"));
        sic.add(new SelectorItemInfo("payerAccountBank.*"));
        sic.add(new SelectorItemInfo("bizType.*"));
        sic.add(new SelectorItemInfo("feeType.*"));
        sic.add(new SelectorItemInfo("curProject.*"));
        sic.add(new SelectorItemInfo("summary"));
        sic.add(new SelectorItemInfo("recProvince.*"));
        sic.add(new SelectorItemInfo("recCity.*"));
        sic.add(new SelectorItemInfo("isDifferPlace"));
        sic.add(new SelectorItemInfo("isEmergency"));
        sic.add(new SelectorItemInfo("payeeArea.*"));
        sic.add(new SelectorItemInfo("usage"));
        sic.add(new SelectorItemInfo("agentPayCompany.*"));
        sic.add(new SelectorItemInfo("payBillType.*"));
        sic.add(new SelectorItemInfo("isDifBank"));
        sic.add(new SelectorItemInfo("FRecCountry.*"));
        sic.add(new SelectorItemInfo("entries.*"));
//        sic.add(new SelectorItemInfo("entries.number"));
    sic.add(new SelectorItemInfo("entries.id"));
        sic.add(new SelectorItemInfo("accountant.*"));
        sic.add(new SelectorItemInfo("approver.*"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionWorkflowList_actionPerformed method
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAntiAudit_actionPerformed method
     */
    public void actionAntiAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTaoPrint_actionPerformed method
     */
    public void actionTaoPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPaymentPlan_actionPerformed method
     */
    public void actionPaymentPlan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRefresh_actionPerformed method
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClosePayReq_actionPerformed method
     */
    public void actionClosePayReq_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelClosePayReq_actionPerformed method
     */
    public void actionCancelClosePayReq_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPay_actionPerformed method
     */
    public void actionPay_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplit_actionPerformed method
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewBgBalance_actionPerformed method
     */
    public void actionViewBgBalance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChequeReimburse_actionPerformed method
     */
    public void actionChequeReimburse_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewBudget_actionPerformed method
     */
    public void actionViewBudget_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionApprove_actionPerformed method
     */
    public void actionApprove_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUntiApprove_actionPerformed method
     */
    public void actionUntiApprove_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareactionWorkflowList(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareactionWorkflowList(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionWorkflowList() {
    	return false;
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionAntiAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAntiAudit() {
    	return false;
    }
	public RequestContext prepareActionTaoPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTaoPrint() {
    	return false;
    }
	public RequestContext prepareActionPaymentPlan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPaymentPlan() {
    	return false;
    }
	public RequestContext prepareActionRefresh(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefresh() {
    	return false;
    }
	public RequestContext prepareActionClosePayReq(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClosePayReq() {
    	return false;
    }
	public RequestContext prepareActionCancelClosePayReq(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelClosePayReq() {
    	return false;
    }
	public RequestContext prepareActionPay(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPay() {
    	return false;
    }
	public RequestContext prepareActionSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSplit() {
    	return false;
    }
	public RequestContext prepareActionViewBgBalance(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewBgBalance() {
    	return false;
    }
	public RequestContext prepareActionChequeReimburse(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChequeReimburse() {
    	return false;
    }
	public RequestContext prepareactionViewBudget(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionViewBudget() {
    	return false;
    }
	public RequestContext prepareActionApprove(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionApprove() {
    	return false;
    }
	public RequestContext prepareActionUntiApprove(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUntiApprove() {
    	return false;
    }

    /**
     * output ActionAudit class
     */     
    protected class ActionAudit extends ItemAction {     
    
        public ActionAudit()
        {
            this(null);
        }

        public ActionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl A"));
            _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionAntiAudit class
     */     
    protected class ActionAntiAudit extends ItemAction {     
    
        public ActionAntiAudit()
        {
            this(null);
        }

        public ActionAntiAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
            _tempStr = resHelper.getString("ActionAntiAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "ActionAntiAudit", "actionAntiAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionTaoPrint class
     */     
    protected class ActionTaoPrint extends ItemAction {     
    
        public ActionTaoPrint()
        {
            this(null);
        }

        public ActionTaoPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionTaoPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTaoPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTaoPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "ActionTaoPrint", "actionTaoPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionPaymentPlan class
     */     
    protected class ActionPaymentPlan extends ItemAction {     
    
        public ActionPaymentPlan()
        {
            this(null);
        }

        public ActionPaymentPlan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPaymentPlan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPaymentPlan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPaymentPlan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "ActionPaymentPlan", "actionPaymentPlan_actionPerformed", e);
        }
    }

    /**
     * output ActionRefresh class
     */     
    protected class ActionRefresh extends ItemAction {     
    
        public ActionRefresh()
        {
            this(null);
        }

        public ActionRefresh(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRefresh.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "ActionRefresh", "actionRefresh_actionPerformed", e);
        }
    }

    /**
     * output ActionClosePayReq class
     */     
    protected class ActionClosePayReq extends ItemAction {     
    
        public ActionClosePayReq()
        {
            this(null);
        }

        public ActionClosePayReq(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl C"));
            _tempStr = resHelper.getString("ActionClosePayReq.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClosePayReq.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClosePayReq.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "ActionClosePayReq", "actionClosePayReq_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelClosePayReq class
     */     
    protected class ActionCancelClosePayReq extends ItemAction {     
    
        public ActionCancelClosePayReq()
        {
            this(null);
        }

        public ActionCancelClosePayReq(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift R"));
            _tempStr = resHelper.getString("ActionCancelClosePayReq.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelClosePayReq.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelClosePayReq.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "ActionCancelClosePayReq", "actionCancelClosePayReq_actionPerformed", e);
        }
    }

    /**
     * output ActionPay class
     */     
    protected class ActionPay extends ItemAction {     
    
        public ActionPay()
        {
            this(null);
        }

        public ActionPay(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPay.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPay.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPay.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "ActionPay", "actionPay_actionPerformed", e);
        }
    }

    /**
     * output ActionSplit class
     */     
    protected class ActionSplit extends ItemAction {     
    
        public ActionSplit()
        {
            this(null);
        }

        public ActionSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "ActionSplit", "actionSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionViewBgBalance class
     */     
    protected class ActionViewBgBalance extends ItemAction {     
    
        public ActionViewBgBalance()
        {
            this(null);
        }

        public ActionViewBgBalance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewBgBalance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBgBalance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBgBalance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "ActionViewBgBalance", "actionViewBgBalance_actionPerformed", e);
        }
    }

    /**
     * output ActionChequeReimburse class
     */     
    protected class ActionChequeReimburse extends ItemAction {     
    
        public ActionChequeReimburse()
        {
            this(null);
        }

        public ActionChequeReimburse(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt shift W"));
            _tempStr = resHelper.getString("ActionChequeReimburse.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChequeReimburse.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChequeReimburse.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "ActionChequeReimburse", "actionChequeReimburse_actionPerformed", e);
        }
    }

    /**
     * output actionViewBudget class
     */     
    protected class actionViewBudget extends ItemAction {     
    
        public actionViewBudget()
        {
            this(null);
        }

        public actionViewBudget(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setDaemonRun(true);
            _tempStr = resHelper.getString("actionViewBudget.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionViewBudget.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionViewBudget.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "actionViewBudget", "actionViewBudget_actionPerformed", e);
        }
    }

    /**
     * output ActionApprove class
     */     
    protected class ActionApprove extends ItemAction {     
    
        public ActionApprove()
        {
            this(null);
        }

        public ActionApprove(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionApprove.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApprove.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApprove.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "ActionApprove", "actionApprove_actionPerformed", e);
        }
    }

    /**
     * output ActionUntiApprove class
     */     
    protected class ActionUntiApprove extends ItemAction {     
    
        public ActionUntiApprove()
        {
            this(null);
        }

        public ActionUntiApprove(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUntiApprove.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUntiApprove.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUntiApprove.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentBillEditUI.this, "ActionUntiApprove", "actionUntiApprove_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "PaymentBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}