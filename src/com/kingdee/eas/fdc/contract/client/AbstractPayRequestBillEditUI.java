/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractPayRequestBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPayRequestBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contattachment;
    protected com.kingdee.bos.ctrl.swing.KDButton btnInputCollect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpaymentProportion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAllInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAllPaymentProportion;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProcess;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanHasCon;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanUnCon;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblAttachmentContainer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDesc;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcontractNo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtattachment;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtpaymentProportion;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtUsage;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalSettlePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAllInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAllPaymentProportion;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProcess;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPlanHasCon;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPlanUnCon;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpaymentRequestBillNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProj;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayBillType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsettlementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrealSupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrecBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrecAccount;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contexchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBcAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGrtAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcapitalAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsInvoice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceDate;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplierOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsBgControl;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplierCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostedCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostedDept;
    protected com.kingdee.bos.ctrl.swing.KDContainer contBgEntry;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsPay;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contuseDepartment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayContentType;
    protected com.kingdee.bos.ctrl.swing.KDLabel txtPayPlanValue;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewBudget;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer contAttachment;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPaymentRequestBillNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractInfo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProj;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayBillType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbookedDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsettlementType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox cbPeriod;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsupplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrealSupplier;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkpayDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtrecBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox txtrecAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtexchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBcAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtGrtAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcapitalAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkInvoiceDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtApplierOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtApplierCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostedCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtApplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostedDept;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtBgEntry;
    protected com.kingdee.bos.ctrl.swing.KDComboBox mergencyState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox difPlace;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea txtMoneyDesc;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtuseDepartment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayContentType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcompletePrjAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAllCompletePrjAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcompletePrjAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAllCompletePrjAmt;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAttachement;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbAttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTaoPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCalc;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPaymentPlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdjustDeduct;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSetTotal;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClose;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnclose;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewMaterialConfirm;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewPayDetail;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnContractAttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnContractExecInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemContractAttachement;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewPayDetail;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemContractExecInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewMaterialConfirm;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClose;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnClose;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAdjustDeduct;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewMbgBalance;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAssociateAcctPay;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAssociateUnSettled;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemMonthReq;
    protected com.kingdee.eas.fdc.contract.PayRequestBillInfo editData = null;
    protected ActionCalc actionCalc = null;
    protected ActionTaoPrint actionTaoPrint = null;
    protected ActionPaymentPlan actionPaymentPlan = null;
    protected ActionAdjustDeduct actionAdjustDeduct = null;
    protected ActionClose actionClose = null;
    protected ActionUnClose actionUnClose = null;
    protected ActionViewContract actionViewContract = null;
    protected ActionViewMbgBalance actionViewMbgBalance = null;
    protected ActionViewPayDetail actionViewPayDetail = null;
    protected ActionAssociateAcctPay actionAssociateAcctPay = null;
    protected ActionAssociateUnSettled actionAssociateUnSettled = null;
    protected ActionContractAttachment actionContractAttachment = null;
    protected ActionViewMaterialConfirm actionViewMaterialConfirm = null;
    protected ActionContractExecInfo actionContractExecInfo = null;
    protected ActionMonthReq actionMonthReq = null;
    protected ActionViewAttachment actionViewAttachment = null;
    protected actionViewBudget actionViewBudget = null;
    public final static String STATUS_CLOSE = "CLOSE";
    /**
     * output class constructor
     */
    public AbstractPayRequestBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPayRequestBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl s"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionRemove
        actionRemove.setEnabled(false);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
        this.actionRemove.setBindWorkFlow(true);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        actionAudit.setEnabled(true);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        actionUnAudit.setEnabled(false);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCalc
        this.actionCalc = new ActionCalc(this);
        getActionManager().registerAction("actionCalc", actionCalc);
         this.actionCalc.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTaoPrint
        this.actionTaoPrint = new ActionTaoPrint(this);
        getActionManager().registerAction("actionTaoPrint", actionTaoPrint);
         this.actionTaoPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPaymentPlan
        this.actionPaymentPlan = new ActionPaymentPlan(this);
        getActionManager().registerAction("actionPaymentPlan", actionPaymentPlan);
         this.actionPaymentPlan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAdjustDeduct
        this.actionAdjustDeduct = new ActionAdjustDeduct(this);
        getActionManager().registerAction("actionAdjustDeduct", actionAdjustDeduct);
         this.actionAdjustDeduct.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClose
        this.actionClose = new ActionClose(this);
        getActionManager().registerAction("actionClose", actionClose);
         this.actionClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnClose
        this.actionUnClose = new ActionUnClose(this);
        getActionManager().registerAction("actionUnClose", actionUnClose);
         this.actionUnClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContract
        this.actionViewContract = new ActionViewContract(this);
        getActionManager().registerAction("actionViewContract", actionViewContract);
         this.actionViewContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewMbgBalance
        this.actionViewMbgBalance = new ActionViewMbgBalance(this);
        getActionManager().registerAction("actionViewMbgBalance", actionViewMbgBalance);
         this.actionViewMbgBalance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewPayDetail
        this.actionViewPayDetail = new ActionViewPayDetail(this);
        getActionManager().registerAction("actionViewPayDetail", actionViewPayDetail);
         this.actionViewPayDetail.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAssociateAcctPay
        this.actionAssociateAcctPay = new ActionAssociateAcctPay(this);
        getActionManager().registerAction("actionAssociateAcctPay", actionAssociateAcctPay);
         this.actionAssociateAcctPay.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAssociateUnSettled
        this.actionAssociateUnSettled = new ActionAssociateUnSettled(this);
        getActionManager().registerAction("actionAssociateUnSettled", actionAssociateUnSettled);
         this.actionAssociateUnSettled.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionContractAttachment
        this.actionContractAttachment = new ActionContractAttachment(this);
        getActionManager().registerAction("actionContractAttachment", actionContractAttachment);
         this.actionContractAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewMaterialConfirm
        this.actionViewMaterialConfirm = new ActionViewMaterialConfirm(this);
        getActionManager().registerAction("actionViewMaterialConfirm", actionViewMaterialConfirm);
         this.actionViewMaterialConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionContractExecInfo
        this.actionContractExecInfo = new ActionContractExecInfo(this);
        getActionManager().registerAction("actionContractExecInfo", actionContractExecInfo);
         this.actionContractExecInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMonthReq
        this.actionMonthReq = new ActionMonthReq(this);
        getActionManager().registerAction("actionMonthReq", actionMonthReq);
         this.actionMonthReq.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAttachment
        this.actionViewAttachment = new ActionViewAttachment(this);
        getActionManager().registerAction("actionViewAttachment", actionViewAttachment);
         this.actionViewAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBudget
        this.actionViewBudget = new actionViewBudget(this);
        getActionManager().registerAction("actionViewBudget", actionViewBudget);
         this.actionViewBudget.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contattachment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnInputCollect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contpaymentProportion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAllInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAllPaymentProportion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewAttachment = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contProcess = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanHasCon = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanUnCon = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.lblAttachmentContainer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtDesc = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcontractNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtattachment = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtpaymentProportion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtUsage = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTotalSettlePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAllInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAllPaymentProportion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtProcess = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtPlanHasCon = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPlanUnCon = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contpaymentRequestBillNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractInfo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProj = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayBillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsettlementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrealSupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrecBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrecAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contcurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contexchangeRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBcAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGrtAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcapitalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsInvoice = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contInvoiceNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contApplierOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsBgControl = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contApplierCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCostedCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCostedDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBgEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDSeparator10 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsPay = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contuseDepartment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayContentType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPayPlanValue = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.btnViewBudget = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contAttachment = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.txtPaymentRequestBillNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractInfo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProj = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtPayBillType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkbookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtPayment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsettlementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cbPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtrealSupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkpayDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtrecBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtrecAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtexchangeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBcAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtGrtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtcapitalAmount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInvoiceNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkInvoiceDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtApplierOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtApplierCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCostedCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtApplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCostedDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtBgEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.mergencyState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.difPlace = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtMoneyDesc = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.bizPromptCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkauditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtuseDepartment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPayContentType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contcompletePrjAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAllCompletePrjAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtcompletePrjAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAllCompletePrjAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.tblAttachement = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.cmbAttachment = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnTaoPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCalc = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPaymentPlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAdjustDeduct = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSetTotal = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnclose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewMaterialConfirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewPayDetail = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnContractAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnContractExecInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemContractAttachement = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewContract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewPayDetail = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemContractExecInfo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewMaterialConfirm = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemClose = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnClose = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAdjustDeduct = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewMbgBalance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAssociateAcctPay = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAssociateUnSettled = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemMonthReq = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contDescription.setName("contDescription");
        this.contcontractNo.setName("contcontractNo");
        this.contattachment.setName("contattachment");
        this.btnInputCollect.setName("btnInputCollect");
        this.contpaymentProportion.setName("contpaymentProportion");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.contAllInvoiceAmt.setName("contAllInvoiceAmt");
        this.contAllPaymentProportion.setName("contAllPaymentProportion");
        this.btnViewAttachment.setName("btnViewAttachment");
        this.contProcess.setName("contProcess");
        this.contPlanHasCon.setName("contPlanHasCon");
        this.contPlanUnCon.setName("contPlanUnCon");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.lblAttachmentContainer.setName("lblAttachmentContainer");
        this.prmtDesc.setName("prmtDesc");
        this.txtcontractNo.setName("txtcontractNo");
        this.txtattachment.setName("txtattachment");
        this.txtpaymentProportion.setName("txtpaymentProportion");
        this.txtUsage.setName("txtUsage");
        this.txtTotalSettlePrice.setName("txtTotalSettlePrice");
        this.txtAllInvoiceAmt.setName("txtAllInvoiceAmt");
        this.txtAllPaymentProportion.setName("txtAllPaymentProportion");
        this.txtProcess.setName("txtProcess");
        this.prmtPlanHasCon.setName("prmtPlanHasCon");
        this.prmtPlanUnCon.setName("prmtPlanUnCon");
        this.kDPanel1.setName("kDPanel1");
        this.contpaymentRequestBillNumber.setName("contpaymentRequestBillNumber");
        this.contOrg.setName("contOrg");
        this.contContractInfo.setName("contContractInfo");
        this.contProj.setName("contProj");
        this.contPayBillType.setName("contPayBillType");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contPayment.setName("contPayment");
        this.contsettlementType.setName("contsettlementType");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contsupplier.setName("contsupplier");
        this.contrealSupplier.setName("contrealSupplier");
        this.contpayDate.setName("contpayDate");
        this.contrecBank.setName("contrecBank");
        this.contrecAccount.setName("contrecAccount");
        this.kDSeparator8.setName("kDSeparator8");
        this.contcurrency.setName("contcurrency");
        this.contexchangeRate.setName("contexchangeRate");
        this.contBcAmount.setName("contBcAmount");
        this.contAmount.setName("contAmount");
        this.contGrtAmount.setName("contGrtAmount");
        this.contcapitalAmount.setName("contcapitalAmount");
        this.cbIsInvoice.setName("cbIsInvoice");
        this.contInvoiceNumber.setName("contInvoiceNumber");
        this.contInvoiceAmt.setName("contInvoiceAmt");
        this.contInvoiceDate.setName("contInvoiceDate");
        this.kDSeparator9.setName("kDSeparator9");
        this.contApplierOrgUnit.setName("contApplierOrgUnit");
        this.cbIsBgControl.setName("cbIsBgControl");
        this.contApplierCompany.setName("contApplierCompany");
        this.contCostedCompany.setName("contCostedCompany");
        this.contApplier.setName("contApplier");
        this.contCostedDept.setName("contCostedDept");
        this.contBgEntry.setName("contBgEntry");
        this.kDSeparator10.setName("kDSeparator10");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.chkIsPay.setName("chkIsPay");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kdtEntrys.setName("kdtEntrys");
        this.contCreator.setName("contCreator");
        this.contAuditor.setName("contAuditor");
        this.contCreateTime.setName("contCreateTime");
        this.contauditDate.setName("contauditDate");
        this.contuseDepartment.setName("contuseDepartment");
        this.contPayContentType.setName("contPayContentType");
        this.txtPayPlanValue.setName("txtPayPlanValue");
        this.btnViewBudget.setName("btnViewBudget");
        this.kDContainer1.setName("kDContainer1");
        this.contAttachment.setName("contAttachment");
        this.txtPaymentRequestBillNumber.setName("txtPaymentRequestBillNumber");
        this.txtOrg.setName("txtOrg");
        this.txtContractInfo.setName("txtContractInfo");
        this.txtProj.setName("txtProj");
        this.prmtPayBillType.setName("prmtPayBillType");
        this.pkbookedDate.setName("pkbookedDate");
        this.prmtPayment.setName("prmtPayment");
        this.prmtsettlementType.setName("prmtsettlementType");
        this.cbPeriod.setName("cbPeriod");
        this.prmtsupplier.setName("prmtsupplier");
        this.prmtrealSupplier.setName("prmtrealSupplier");
        this.pkpayDate.setName("pkpayDate");
        this.txtrecBank.setName("txtrecBank");
        this.txtrecAccount.setName("txtrecAccount");
        this.prmtcurrency.setName("prmtcurrency");
        this.txtexchangeRate.setName("txtexchangeRate");
        this.txtBcAmount.setName("txtBcAmount");
        this.txtAmount.setName("txtAmount");
        this.txtGrtAmount.setName("txtGrtAmount");
        this.txtcapitalAmount.setName("txtcapitalAmount");
        this.txtInvoiceNumber.setName("txtInvoiceNumber");
        this.txtInvoiceAmt.setName("txtInvoiceAmt");
        this.pkInvoiceDate.setName("pkInvoiceDate");
        this.prmtApplierOrgUnit.setName("prmtApplierOrgUnit");
        this.prmtApplierCompany.setName("prmtApplierCompany");
        this.prmtCostedCompany.setName("prmtCostedCompany");
        this.prmtApplier.setName("prmtApplier");
        this.prmtCostedDept.setName("prmtCostedDept");
        this.kdtBgEntry.setName("kdtBgEntry");
        this.mergencyState.setName("mergencyState");
        this.difPlace.setName("difPlace");
        this.txtMoneyDesc.setName("txtMoneyDesc");
        this.bizPromptCreator.setName("bizPromptCreator");
        this.bizPromptAuditor.setName("bizPromptAuditor");
        this.dateCreateTime.setName("dateCreateTime");
        this.pkauditDate.setName("pkauditDate");
        this.prmtuseDepartment.setName("prmtuseDepartment");
        this.prmtPayContentType.setName("prmtPayContentType");
        this.contcompletePrjAmt.setName("contcompletePrjAmt");
        this.contAllCompletePrjAmt.setName("contAllCompletePrjAmt");
        this.txtcompletePrjAmt.setName("txtcompletePrjAmt");
        this.txtAllCompletePrjAmt.setName("txtAllCompletePrjAmt");
        this.tblAttachement.setName("tblAttachement");
        this.cmbAttachment.setName("cmbAttachment");
        this.btnTaoPrint.setName("btnTaoPrint");
        this.btnCalc.setName("btnCalc");
        this.btnPaymentPlan.setName("btnPaymentPlan");
        this.btnAdjustDeduct.setName("btnAdjustDeduct");
        this.btnSetTotal.setName("btnSetTotal");
        this.btnClose.setName("btnClose");
        this.btnUnclose.setName("btnUnclose");
        this.btnViewContract.setName("btnViewContract");
        this.btnViewMaterialConfirm.setName("btnViewMaterialConfirm");
        this.btnViewPayDetail.setName("btnViewPayDetail");
        this.btnContractAttachment.setName("btnContractAttachment");
        this.btnContractExecInfo.setName("btnContractExecInfo");
        this.menuItemContractAttachement.setName("menuItemContractAttachement");
        this.menuItemViewContract.setName("menuItemViewContract");
        this.menuItemViewPayDetail.setName("menuItemViewPayDetail");
        this.menuItemContractExecInfo.setName("menuItemContractExecInfo");
        this.menuItemViewMaterialConfirm.setName("menuItemViewMaterialConfirm");
        this.menuItemClose.setName("menuItemClose");
        this.menuItemUnClose.setName("menuItemUnClose");
        this.menuItemAdjustDeduct.setName("menuItemAdjustDeduct");
        this.menuItemViewMbgBalance.setName("menuItemViewMbgBalance");
        this.menuItemAssociateAcctPay.setName("menuItemAssociateAcctPay");
        this.menuItemAssociateUnSettled.setName("menuItemAssociateUnSettled");
        this.menuItemMonthReq.setName("menuItemMonthReq");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,768));		
        this.btnPrint.setEnabled(false);		
        this.btnPrintPreview.setEnabled(false);		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.rMenuItemSubmit.setEnabled(true);		
        this.rMenuItemSubmitAndAddNew.setEnabled(true);		
        this.rMenuItemSubmitAndPrint.setEnabled(true);		
        this.chkMenuItemSubmitAndPrint.setVisible(false);		
        this.btnNextPerson.setEnabled(true);		
        this.btnNextPerson.setVisible(true);		
        this.menuTable1.setVisible(false);		
        this.menuTable1.setEnabled(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setBoundLabelAlignment(7);		
        this.contDescription.setVisible(false);
        // contcontractNo		
        this.contcontractNo.setBoundLabelText(resHelper.getString("contcontractNo.boundLabelText"));		
        this.contcontractNo.setBoundLabelLength(120);		
        this.contcontractNo.setBoundLabelUnderline(true);		
        this.contcontractNo.setVisible(false);		
        this.contcontractNo.setBoundLabelAlignment(7);
        // contattachment		
        this.contattachment.setBoundLabelText(resHelper.getString("contattachment.boundLabelText"));		
        this.contattachment.setBoundLabelLength(100);		
        this.contattachment.setBoundLabelUnderline(true);		
        this.contattachment.setVisible(false);		
        this.contattachment.setBoundLabelAlignment(7);
        // btnInputCollect		
        this.btnInputCollect.setText(resHelper.getString("btnInputCollect.text"));		
        this.btnInputCollect.setVisible(false);
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
        // contpaymentProportion		
        this.contpaymentProportion.setBoundLabelText(resHelper.getString("contpaymentProportion.boundLabelText"));		
        this.contpaymentProportion.setBoundLabelUnderline(true);		
        this.contpaymentProportion.setBoundLabelLength(120);		
        this.contpaymentProportion.setVisible(false);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setVisible(false);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(120);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setVisible(false);
        // contAllInvoiceAmt		
        this.contAllInvoiceAmt.setBoundLabelText(resHelper.getString("contAllInvoiceAmt.boundLabelText"));		
        this.contAllInvoiceAmt.setBoundLabelLength(100);		
        this.contAllInvoiceAmt.setBoundLabelUnderline(true);		
        this.contAllInvoiceAmt.setVisible(false);
        // contAllPaymentProportion		
        this.contAllPaymentProportion.setBoundLabelText(resHelper.getString("contAllPaymentProportion.boundLabelText"));		
        this.contAllPaymentProportion.setBoundLabelUnderline(true);		
        this.contAllPaymentProportion.setBoundLabelLength(140);		
        this.contAllPaymentProportion.setEnabled(false);		
        this.contAllPaymentProportion.setVisible(false);
        // btnViewAttachment
        this.btnViewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAttachment.setText(resHelper.getString("btnViewAttachment.text"));		
        this.btnViewAttachment.setVisible(false);
        // contProcess		
        this.contProcess.setBoundLabelText(resHelper.getString("contProcess.boundLabelText"));		
        this.contProcess.setBoundLabelLength(100);		
        this.contProcess.setBoundLabelUnderline(true);		
        this.contProcess.setVisible(false);
        // contPlanHasCon		
        this.contPlanHasCon.setBoundLabelText(resHelper.getString("contPlanHasCon.boundLabelText"));		
        this.contPlanHasCon.setBoundLabelLength(100);		
        this.contPlanHasCon.setBoundLabelUnderline(true);		
        this.contPlanHasCon.setVisible(false);
        // contPlanUnCon		
        this.contPlanUnCon.setBoundLabelText(resHelper.getString("contPlanUnCon.boundLabelText"));		
        this.contPlanUnCon.setBoundLabelLength(100);		
        this.contPlanUnCon.setBoundLabelUnderline(true);		
        this.contPlanUnCon.setVisible(false);
        // kDScrollPane1
        // lblAttachmentContainer		
        this.lblAttachmentContainer.setBoundLabelText(resHelper.getString("lblAttachmentContainer.boundLabelText"));		
        this.lblAttachmentContainer.setBoundLabelLength(100);		
        this.lblAttachmentContainer.setBoundLabelUnderline(true);		
        this.lblAttachmentContainer.setVisible(false);
        // prmtDesc		
        this.prmtDesc.setDisplayFormat("$name$");		
        this.prmtDesc.setEditFormat("$number$");		
        this.prmtDesc.setCommitFormat("$number$;$name$");		
        this.prmtDesc.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7VoucherAbstract");
        // txtcontractNo		
        this.txtcontractNo.setVisible(true);		
        this.txtcontractNo.setHorizontalAlignment(2);		
        this.txtcontractNo.setMaxLength(100);		
        this.txtcontractNo.setRequired(true);		
        this.txtcontractNo.setEnabled(false);
        // txtattachment		
        this.txtattachment.setVisible(true);		
        this.txtattachment.setDataType(0);		
        this.txtattachment.setSupportedEmpty(true);		
        this.txtattachment.setRequired(false);		
        this.txtattachment.setEnabled(true);		
        this.txtattachment.setPrecision(0);
        // txtpaymentProportion		
        this.txtpaymentProportion.setDataType(1);
        this.txtpaymentProportion.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtpaymentProportion_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtUsage
        // txtTotalSettlePrice		
        this.txtTotalSettlePrice.setDataType(1);		
        this.txtTotalSettlePrice.setEnabled(false);
        // txtAllInvoiceAmt		
        this.txtAllInvoiceAmt.setEnabled(false);
        // txtAllPaymentProportion		
        this.txtAllPaymentProportion.setDataType(1);		
        this.txtAllPaymentProportion.setEnabled(false);
        // txtProcess
        // prmtPlanHasCon		
        this.prmtPlanHasCon.setDisplayFormat("$contractName$ $head.year$ 年 $head.month$ 月付款计划");		
        this.prmtPlanHasCon.setEnabled(false);
        // prmtPlanUnCon		
        this.prmtPlanUnCon.setQueryInfo("com.kingdee.eas.fdc.finance.app.F7FDCDepConPayPlanUnsettledConQuery");		
        this.prmtPlanUnCon.setDisplayFormat("待签订 $unConName$ $parent.year$ 年 $parent.month$ 月付款计划");		
        this.prmtPlanUnCon.setEditFormat("$parent.number$");		
        this.prmtPlanUnCon.setCommitFormat("$parent.number$");
        this.prmtPlanUnCon.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtPlanUnCon_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDPanel1		
        this.kDPanel1.setMinimumSize(new Dimension(1013,850));		
        this.kDPanel1.setMaximumSize(new Dimension(1013,850));		
        this.kDPanel1.setPreferredSize(new Dimension(1013,850));
        // contpaymentRequestBillNumber		
        this.contpaymentRequestBillNumber.setBoundLabelText(resHelper.getString("contpaymentRequestBillNumber.boundLabelText"));		
        this.contpaymentRequestBillNumber.setBoundLabelLength(100);		
        this.contpaymentRequestBillNumber.setBoundLabelUnderline(true);		
        this.contpaymentRequestBillNumber.setBoundLabelAlignment(7);		
        this.contpaymentRequestBillNumber.setVisible(true);
        // contOrg		
        this.contOrg.setBoundLabelText(resHelper.getString("contOrg.boundLabelText"));		
        this.contOrg.setBoundLabelLength(100);		
        this.contOrg.setBoundLabelUnderline(true);		
        this.contOrg.setBoundLabelAlignment(7);		
        this.contOrg.setVisible(true);
        // contContractInfo		
        this.contContractInfo.setBoundLabelText(resHelper.getString("contContractInfo.boundLabelText"));		
        this.contContractInfo.setBoundLabelLength(100);		
        this.contContractInfo.setBoundLabelUnderline(true);
        // contProj		
        this.contProj.setBoundLabelText(resHelper.getString("contProj.boundLabelText"));		
        this.contProj.setBoundLabelLength(100);		
        this.contProj.setBoundLabelUnderline(true);		
        this.contProj.setBoundLabelAlignment(7);		
        this.contProj.setVisible(true);
        // contPayBillType		
        this.contPayBillType.setBoundLabelText(resHelper.getString("contPayBillType.boundLabelText"));		
        this.contPayBillType.setBoundLabelLength(100);		
        this.contPayBillType.setBoundLabelUnderline(true);		
        this.contPayBillType.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // contPayment		
        this.contPayment.setBoundLabelText(resHelper.getString("contPayment.boundLabelText"));		
        this.contPayment.setBoundLabelLength(100);		
        this.contPayment.setBoundLabelUnderline(true);		
        this.contPayment.setVisible(true);		
        this.contPayment.setBoundLabelAlignment(7);
        // contsettlementType		
        this.contsettlementType.setBoundLabelText(resHelper.getString("contsettlementType.boundLabelText"));		
        this.contsettlementType.setBoundLabelLength(100);		
        this.contsettlementType.setBoundLabelUnderline(true);		
        this.contsettlementType.setVisible(true);		
        this.contsettlementType.setBoundLabelAlignment(7);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // contsupplier		
        this.contsupplier.setBoundLabelText(resHelper.getString("contsupplier.boundLabelText"));		
        this.contsupplier.setBoundLabelLength(100);		
        this.contsupplier.setBoundLabelUnderline(true);		
        this.contsupplier.setVisible(true);		
        this.contsupplier.setBoundLabelAlignment(7);
        // contrealSupplier		
        this.contrealSupplier.setBoundLabelText(resHelper.getString("contrealSupplier.boundLabelText"));		
        this.contrealSupplier.setBoundLabelLength(100);		
        this.contrealSupplier.setBoundLabelUnderline(true);		
        this.contrealSupplier.setVisible(true);		
        this.contrealSupplier.setBoundLabelAlignment(7);
        // contpayDate		
        this.contpayDate.setBoundLabelText(resHelper.getString("contpayDate.boundLabelText"));		
        this.contpayDate.setBoundLabelLength(100);		
        this.contpayDate.setBoundLabelUnderline(true);		
        this.contpayDate.setVisible(true);		
        this.contpayDate.setBoundLabelAlignment(7);
        // contrecBank		
        this.contrecBank.setBoundLabelText(resHelper.getString("contrecBank.boundLabelText"));		
        this.contrecBank.setBoundLabelLength(100);		
        this.contrecBank.setBoundLabelUnderline(true);		
        this.contrecBank.setVisible(true);		
        this.contrecBank.setBoundLabelAlignment(7);
        // contrecAccount		
        this.contrecAccount.setBoundLabelText(resHelper.getString("contrecAccount.boundLabelText"));		
        this.contrecAccount.setBoundLabelLength(100);		
        this.contrecAccount.setBoundLabelUnderline(true);		
        this.contrecAccount.setVisible(true);		
        this.contrecAccount.setBoundLabelAlignment(7);
        // kDSeparator8
        // contcurrency		
        this.contcurrency.setBoundLabelText(resHelper.getString("contcurrency.boundLabelText"));		
        this.contcurrency.setBoundLabelLength(100);		
        this.contcurrency.setBoundLabelUnderline(true);		
        this.contcurrency.setVisible(true);		
        this.contcurrency.setBoundLabelAlignment(7);
        // contexchangeRate		
        this.contexchangeRate.setBoundLabelText(resHelper.getString("contexchangeRate.boundLabelText"));		
        this.contexchangeRate.setBoundLabelLength(100);		
        this.contexchangeRate.setBoundLabelUnderline(true);		
        this.contexchangeRate.setVisible(true);		
        this.contexchangeRate.setBoundLabelAlignment(7);
        // contBcAmount		
        this.contBcAmount.setBoundLabelText(resHelper.getString("contBcAmount.boundLabelText"));		
        this.contBcAmount.setBoundLabelLength(100);		
        this.contBcAmount.setBoundLabelUnderline(true);		
        this.contBcAmount.setVisible(true);		
        this.contBcAmount.setBoundLabelAlignment(7);
        // contAmount		
        this.contAmount.setBoundLabelText(resHelper.getString("contAmount.boundLabelText"));		
        this.contAmount.setBoundLabelLength(100);		
        this.contAmount.setBoundLabelUnderline(true);		
        this.contAmount.setVisible(true);		
        this.contAmount.setBoundLabelAlignment(7);
        // contGrtAmount		
        this.contGrtAmount.setBoundLabelText(resHelper.getString("contGrtAmount.boundLabelText"));		
        this.contGrtAmount.setBoundLabelLength(100);		
        this.contGrtAmount.setBoundLabelUnderline(true);
        // contcapitalAmount		
        this.contcapitalAmount.setBoundLabelText(resHelper.getString("contcapitalAmount.boundLabelText"));		
        this.contcapitalAmount.setBoundLabelLength(100);		
        this.contcapitalAmount.setBoundLabelUnderline(true);		
        this.contcapitalAmount.setBoundLabelAlignment(7);		
        this.contcapitalAmount.setVisible(true);
        // cbIsInvoice		
        this.cbIsInvoice.setText(resHelper.getString("cbIsInvoice.text"));
        this.cbIsInvoice.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbIsInvoice_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contInvoiceNumber		
        this.contInvoiceNumber.setBoundLabelText(resHelper.getString("contInvoiceNumber.boundLabelText"));		
        this.contInvoiceNumber.setBoundLabelUnderline(true);		
        this.contInvoiceNumber.setBoundLabelLength(100);
        // contInvoiceAmt		
        this.contInvoiceAmt.setBoundLabelText(resHelper.getString("contInvoiceAmt.boundLabelText"));		
        this.contInvoiceAmt.setBoundLabelLength(100);		
        this.contInvoiceAmt.setBoundLabelUnderline(true);
        // contInvoiceDate		
        this.contInvoiceDate.setBoundLabelText(resHelper.getString("contInvoiceDate.boundLabelText"));		
        this.contInvoiceDate.setBoundLabelLength(100);		
        this.contInvoiceDate.setBoundLabelUnderline(true);
        // kDSeparator9
        // contApplierOrgUnit		
        this.contApplierOrgUnit.setBoundLabelText(resHelper.getString("contApplierOrgUnit.boundLabelText"));		
        this.contApplierOrgUnit.setBoundLabelLength(100);		
        this.contApplierOrgUnit.setBoundLabelUnderline(true);		
        this.contApplierOrgUnit.setVisible(false);
        // cbIsBgControl		
        this.cbIsBgControl.setText(resHelper.getString("cbIsBgControl.text"));
        this.cbIsBgControl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbIsBgControl_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contApplierCompany		
        this.contApplierCompany.setBoundLabelText(resHelper.getString("contApplierCompany.boundLabelText"));		
        this.contApplierCompany.setBoundLabelLength(100);		
        this.contApplierCompany.setBoundLabelUnderline(true);		
        this.contApplierCompany.setVisible(false);
        // contCostedCompany		
        this.contCostedCompany.setBoundLabelText(resHelper.getString("contCostedCompany.boundLabelText"));		
        this.contCostedCompany.setBoundLabelLength(100);		
        this.contCostedCompany.setBoundLabelUnderline(true);
        // contApplier		
        this.contApplier.setBoundLabelText(resHelper.getString("contApplier.boundLabelText"));		
        this.contApplier.setBoundLabelLength(100);		
        this.contApplier.setBoundLabelUnderline(true);
        // contCostedDept		
        this.contCostedDept.setBoundLabelText(resHelper.getString("contCostedDept.boundLabelText"));		
        this.contCostedDept.setBoundLabelLength(100);		
        this.contCostedDept.setBoundLabelUnderline(true);
        // contBgEntry		
        this.contBgEntry.setTitle(resHelper.getString("contBgEntry.title"));		
        this.contBgEntry.setAutoscrolls(true);		
        this.contBgEntry.setEnableActive(false);
        // kDSeparator10
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // chkIsPay		
        this.chkIsPay.setText(resHelper.getString("chkIsPay.text"));		
        this.chkIsPay.setSelected(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup /><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" /></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));

        

        this.kdtEntrys.checkParsed();
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setBoundLabelAlignment(7);		
        this.contCreator.setVisible(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setBoundLabelAlignment(7);		
        this.contAuditor.setVisible(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setVisible(true);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setBoundLabelAlignment(7);
        // contauditDate		
        this.contauditDate.setBoundLabelText(resHelper.getString("contauditDate.boundLabelText"));		
        this.contauditDate.setBoundLabelLength(100);		
        this.contauditDate.setBoundLabelUnderline(true);		
        this.contauditDate.setVisible(true);		
        this.contauditDate.setBoundLabelAlignment(7);
        // contuseDepartment		
        this.contuseDepartment.setBoundLabelText(resHelper.getString("contuseDepartment.boundLabelText"));		
        this.contuseDepartment.setBoundLabelLength(100);		
        this.contuseDepartment.setBoundLabelUnderline(true);		
        this.contuseDepartment.setBoundLabelAlignment(7);
        // contPayContentType		
        this.contPayContentType.setBoundLabelText(resHelper.getString("contPayContentType.boundLabelText"));		
        this.contPayContentType.setBoundLabelLength(100);		
        this.contPayContentType.setBoundLabelUnderline(true);
        // txtPayPlanValue		
        this.txtPayPlanValue.setForeground(new java.awt.Color(255,0,0));
        // btnViewBudget
        this.btnViewBudget.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBudget, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBudget.setText(resHelper.getString("btnViewBudget.text"));		
        this.btnViewBudget.setVisible(false);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // contAttachment		
        this.contAttachment.setTitle(resHelper.getString("contAttachment.title"));
        // txtPaymentRequestBillNumber		
        this.txtPaymentRequestBillNumber.setMaxLength(80);		
        this.txtPaymentRequestBillNumber.setVisible(true);		
        this.txtPaymentRequestBillNumber.setEnabled(true);		
        this.txtPaymentRequestBillNumber.setHorizontalAlignment(2);		
        this.txtPaymentRequestBillNumber.setRequired(true);
        // txtOrg		
        this.txtOrg.setMaxLength(80);		
        this.txtOrg.setVisible(true);		
        this.txtOrg.setEnabled(true);		
        this.txtOrg.setHorizontalAlignment(2);		
        this.txtOrg.setRequired(false);		
        this.txtOrg.setEditable(false);
        // txtContractInfo		
        this.txtContractInfo.setEnabled(false);
        // txtProj		
        this.txtProj.setMaxLength(80);		
        this.txtProj.setVisible(true);		
        this.txtProj.setEnabled(true);		
        this.txtProj.setHorizontalAlignment(2);		
        this.txtProj.setRequired(false);		
        this.txtProj.setEditable(false);
        // prmtPayBillType		
        this.prmtPayBillType.setQueryInfo("com.kingdee.eas.fi.cas.F7PaymentBillTypeQuery");		
        this.prmtPayBillType.setCommitFormat("$number$");		
        this.prmtPayBillType.setEditFormat("$number$");		
        this.prmtPayBillType.setDisplayFormat("$number$ $name$");		
        this.prmtPayBillType.setVisible(false);
        // pkbookedDate
        this.pkbookedDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkbookedDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtPayment		
        this.prmtPayment.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");		
        this.prmtPayment.setVisible(true);		
        this.prmtPayment.setEditable(true);		
        this.prmtPayment.setDisplayFormat("$number$ $name$");		
        this.prmtPayment.setEditFormat("$number$");		
        this.prmtPayment.setCommitFormat("$number$");		
        this.prmtPayment.setRequired(true);
        this.prmtPayment.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtPayment_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtPayment.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtPayment_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtsettlementType		
        this.prmtsettlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");		
        this.prmtsettlementType.setVisible(true);		
        this.prmtsettlementType.setEditable(true);		
        this.prmtsettlementType.setDisplayFormat("$number$ $name$");		
        this.prmtsettlementType.setEditFormat("$number$");		
        this.prmtsettlementType.setCommitFormat("$number$");
        this.prmtsettlementType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtsettlementType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cbPeriod		
        this.cbPeriod.setEnabled(false);
        // prmtsupplier		
        this.prmtsupplier.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtsupplier.setVisible(true);		
        this.prmtsupplier.setDisplayFormat("$name$");		
        this.prmtsupplier.setEditFormat("$number$");		
        this.prmtsupplier.setCommitFormat("$number$");		
        this.prmtsupplier.setRequired(true);
        this.prmtsupplier.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    supplier_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtrealSupplier		
        this.prmtrealSupplier.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtrealSupplier.setVisible(true);		
        this.prmtrealSupplier.setEditable(true);		
        this.prmtrealSupplier.setDisplayFormat("$name$");		
        this.prmtrealSupplier.setEditFormat("$number$");		
        this.prmtrealSupplier.setCommitFormat("$number$");		
        this.prmtrealSupplier.setRequired(true);
        this.prmtrealSupplier.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    realSupplier_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkpayDate		
        this.pkpayDate.setVisible(true);		
        this.pkpayDate.setEnabled(false);		
        this.pkpayDate.setRequired(true);
        this.pkpayDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkpayDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtrecBank		
        this.txtrecBank.setMaxLength(80);
        // txtrecAccount		
        this.txtrecAccount.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7SupplierCompanyBankQuery");		
        this.txtrecAccount.setCommitFormat("$bankAccount");		
        this.txtrecAccount.setDisplayFormat("$bankAccount");		
        this.txtrecAccount.setEditFormat("$bankAccount");
        this.txtrecAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    txtrecAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtrecAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    txtrecAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtrecAccount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtrecAccount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtcurrency		
        this.prmtcurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.prmtcurrency.setVisible(true);		
        this.prmtcurrency.setEditable(true);		
        this.prmtcurrency.setDisplayFormat("$name$");		
        this.prmtcurrency.setEditFormat("$number$");		
        this.prmtcurrency.setCommitFormat("$number$");		
        this.prmtcurrency.setRequired(true);
        this.prmtcurrency.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    currency_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtexchangeRate		
        this.txtexchangeRate.setDataType(1);		
        this.txtexchangeRate.setRequired(true);		
        this.txtexchangeRate.setPrecision(3);
        this.txtexchangeRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtexchangeRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtexchangeRate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtexchangeRate_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtBcAmount		
        this.txtBcAmount.setDataType(1);		
        this.txtBcAmount.setRequired(true);		
        this.txtBcAmount.setPrecision(2);		
        this.txtBcAmount.setEnabled(false);
        // txtAmount		
        this.txtAmount.setDataType(1);		
        this.txtAmount.setPrecision(2);		
        this.txtAmount.setSupportedEmpty(true);		
        this.txtAmount.setVisible(true);		
        this.txtAmount.setEnabled(false);		
        this.txtAmount.setRequired(true);
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
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtAmount_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtGrtAmount		
        this.txtGrtAmount.setDataType(1);		
        this.txtGrtAmount.setEditable(false);		
        this.txtGrtAmount.setPrecision(2);		
        this.txtGrtAmount.setSupportedEmpty(true);		
        this.txtGrtAmount.setEnabled(false);
        // txtcapitalAmount		
        this.txtcapitalAmount.setMaxLength(80);		
        this.txtcapitalAmount.setVisible(true);		
        this.txtcapitalAmount.setEnabled(true);		
        this.txtcapitalAmount.setHorizontalAlignment(2);		
        this.txtcapitalAmount.setRequired(false);
        // txtInvoiceNumber
        // txtInvoiceAmt		
        this.txtInvoiceAmt.setSupportedEmpty(true);		
        this.txtInvoiceAmt.setPrecision(2);
        // pkInvoiceDate
        // prmtApplierOrgUnit
        this.prmtApplierOrgUnit.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtApplierOrgUnit_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtApplierCompany		
        this.prmtApplierCompany.setEnabled(false);
        // prmtCostedCompany		
        this.prmtCostedCompany.setRequired(true);
        this.prmtCostedCompany.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCostedCompany_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtApplier		
        this.prmtApplier.setRequired(true);
        this.prmtApplier.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtApplier_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCostedDept		
        this.prmtCostedDept.setRequired(true);
        this.prmtCostedDept.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCostedDept_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdtBgEntry
		String kdtBgEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"expenseType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"0\" /><t:Column t:key=\"bgItem\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"1\" /><t:Column t:key=\"accountView\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"requestAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{expenseType}</t:Cell><t:Cell>$Resource{bgItem}</t:Cell><t:Cell>$Resource{accountView}</t:Cell><t:Cell>$Resource{requestAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtBgEntry.setFormatXml(resHelper.translateString("kdtBgEntry",kdtBgEntryStrXML));
        this.kdtBgEntry.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    kdtBgEntry_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtBgEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtBgEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.kdtBgEntry.checkParsed();
        // mergencyState		
        this.mergencyState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.UrgentDegreeEnum").toArray());
        // difPlace		
        this.difPlace.addItems(EnumUtils.getEnumList("com.kingdee.eas.fi.cas.DifPlaceEnum").toArray());
        // txtMoneyDesc
        // bizPromptCreator		
        this.bizPromptCreator.setEnabled(false);		
        this.bizPromptCreator.setVisible(true);		
        this.bizPromptCreator.setEditable(true);		
        this.bizPromptCreator.setDisplayFormat("$name$");		
        this.bizPromptCreator.setEditFormat("$number$");		
        this.bizPromptCreator.setCommitFormat("$number$");		
        this.bizPromptCreator.setRequired(false);
        // bizPromptAuditor		
        this.bizPromptAuditor.setEnabled(false);		
        this.bizPromptAuditor.setVisible(true);		
        this.bizPromptAuditor.setEditable(true);		
        this.bizPromptAuditor.setDisplayFormat("$name$");		
        this.bizPromptAuditor.setEditFormat("$number$");		
        this.bizPromptAuditor.setCommitFormat("$number$");		
        this.bizPromptAuditor.setRequired(false);
        // dateCreateTime		
        this.dateCreateTime.setEnabled(false);		
        this.dateCreateTime.setVisible(true);
        // pkauditDate		
        this.pkauditDate.setVisible(true);		
        this.pkauditDate.setEnabled(false);
        // prmtuseDepartment		
        this.prmtuseDepartment.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.prmtuseDepartment.setVisible(true);		
        this.prmtuseDepartment.setEditable(true);		
        this.prmtuseDepartment.setDisplayFormat("$name$");		
        this.prmtuseDepartment.setEditFormat("$number$");		
        this.prmtuseDepartment.setCommitFormat("$number$");		
        this.prmtuseDepartment.setRequired(true);
        // prmtPayContentType		
        this.prmtPayContentType.setDisplayFormat("$name$");		
        this.prmtPayContentType.setEditFormat("$longNumber$");		
        this.prmtPayContentType.setCommitFormat("$longNumber$");		
        this.prmtPayContentType.setQueryInfo("com.kingdee.eas.fdc.contract.app.PayContentTypeQuery");		
        this.prmtPayContentType.setRequired(true);
        // contcompletePrjAmt		
        this.contcompletePrjAmt.setBoundLabelText(resHelper.getString("contcompletePrjAmt.boundLabelText"));		
        this.contcompletePrjAmt.setBoundLabelUnderline(true);		
        this.contcompletePrjAmt.setBoundLabelLength(120);
        // contAllCompletePrjAmt		
        this.contAllCompletePrjAmt.setBoundLabelText(resHelper.getString("contAllCompletePrjAmt.boundLabelText"));		
        this.contAllCompletePrjAmt.setBoundLabelUnderline(true);		
        this.contAllCompletePrjAmt.setBoundLabelLength(120);		
        this.contAllCompletePrjAmt.setEnabled(false);
        // txtcompletePrjAmt		
        this.txtcompletePrjAmt.setDataType(1);
        this.txtcompletePrjAmt.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtcompletePrjAmt_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtAllCompletePrjAmt		
        this.txtAllCompletePrjAmt.setDataType(1);		
        this.txtAllCompletePrjAmt.setEnabled(false);
        // tblAttachement
		String tblAttachementStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sTable\"><c:Alignment horizontal=\"left\" /><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"2\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\" t:styleID=\"sTable\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"type\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblAttachement.setFormatXml(resHelper.translateString("tblAttachement",tblAttachementStrXML));
        this.tblAttachement.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblAttachement_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        this.tblAttachement.checkParsed();
        // cmbAttachment
        // btnTaoPrint
        this.btnTaoPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionTaoPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTaoPrint.setText(resHelper.getString("btnTaoPrint.text"));		
        this.btnTaoPrint.setEnabled(false);
        // btnCalc
        this.btnCalc.setAction((IItemAction)ActionProxyFactory.getProxy(actionCalculator, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCalc.setText(resHelper.getString("btnCalc.text"));
        // btnPaymentPlan
        this.btnPaymentPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionPaymentPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPaymentPlan.setText(resHelper.getString("btnPaymentPlan.text"));
        // btnAdjustDeduct
        this.btnAdjustDeduct.setAction((IItemAction)ActionProxyFactory.getProxy(actionAdjustDeduct, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAdjustDeduct.setText(resHelper.getString("btnAdjustDeduct.text"));		
        this.btnAdjustDeduct.setToolTipText(resHelper.getString("btnAdjustDeduct.toolTipText"));
        // btnSetTotal		
        this.btnSetTotal.setText(resHelper.getString("btnSetTotal.text"));
        this.btnSetTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSetTotal_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnClose
        this.btnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClose.setText(resHelper.getString("btnClose.text"));		
        this.btnClose.setToolTipText(resHelper.getString("btnClose.toolTipText"));
        // btnUnclose
        this.btnUnclose.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnclose.setText(resHelper.getString("btnUnclose.text"));		
        this.btnUnclose.setToolTipText(resHelper.getString("btnUnclose.toolTipText"));
        // btnViewContract
        this.btnViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContract.setText(resHelper.getString("btnViewContract.text"));
        // btnViewMaterialConfirm
        this.btnViewMaterialConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewMaterialConfirm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewMaterialConfirm.setText(resHelper.getString("btnViewMaterialConfirm.text"));		
        this.btnViewMaterialConfirm.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_linkviewbill"));
        // btnViewPayDetail
        this.btnViewPayDetail.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPayDetail, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewPayDetail.setText(resHelper.getString("btnViewPayDetail.text"));
        // btnContractAttachment
        this.btnContractAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionContractAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnContractAttachment.setText(resHelper.getString("btnContractAttachment.text"));		
        this.btnContractAttachment.setToolTipText(resHelper.getString("btnContractAttachment.toolTipText"));		
        this.btnContractAttachment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_view"));
        // btnContractExecInfo
        this.btnContractExecInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionContractExecInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnContractExecInfo.setText(resHelper.getString("btnContractExecInfo.text"));
        // menuItemContractAttachement
        this.menuItemContractAttachement.setAction((IItemAction)ActionProxyFactory.getProxy(actionContractAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemContractAttachement.setText(resHelper.getString("menuItemContractAttachement.text"));		
        this.menuItemContractAttachement.setToolTipText(resHelper.getString("menuItemContractAttachement.toolTipText"));		
        this.menuItemContractAttachement.setMnemonic(65);
        // menuItemViewContract
        this.menuItemViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewContract.setText(resHelper.getString("menuItemViewContract.text"));
        // menuItemViewPayDetail
        this.menuItemViewPayDetail.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPayDetail, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewPayDetail.setText(resHelper.getString("menuItemViewPayDetail.text"));
        // menuItemContractExecInfo
        this.menuItemContractExecInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionContractExecInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemContractExecInfo.setText(resHelper.getString("menuItemContractExecInfo.text"));
        // menuItemViewMaterialConfirm
        this.menuItemViewMaterialConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewMaterialConfirm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewMaterialConfirm.setText(resHelper.getString("menuItemViewMaterialConfirm.text"));		
        this.menuItemViewMaterialConfirm.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_linkviewbill"));
        // menuItemClose
        this.menuItemClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClose.setText(resHelper.getString("menuItemClose.text"));		
        this.menuItemClose.setToolTipText(resHelper.getString("menuItemClose.toolTipText"));
        // menuItemUnClose
        this.menuItemUnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnClose.setText(resHelper.getString("menuItemUnClose.text"));		
        this.menuItemUnClose.setToolTipText(resHelper.getString("menuItemUnClose.toolTipText"));
        // menuItemAdjustDeduct
        this.menuItemAdjustDeduct.setAction((IItemAction)ActionProxyFactory.getProxy(actionAdjustDeduct, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAdjustDeduct.setText(resHelper.getString("menuItemAdjustDeduct.text"));		
        this.menuItemAdjustDeduct.setToolTipText(resHelper.getString("menuItemAdjustDeduct.toolTipText"));
        // menuItemViewMbgBalance
        this.menuItemViewMbgBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewMbgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewMbgBalance.setText(resHelper.getString("menuItemViewMbgBalance.text"));		
        this.menuItemViewMbgBalance.setToolTipText(resHelper.getString("menuItemViewMbgBalance.toolTipText"));
        // menuItemAssociateAcctPay
        this.menuItemAssociateAcctPay.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssociateAcctPay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAssociateAcctPay.setText(resHelper.getString("menuItemAssociateAcctPay.text"));
        // menuItemAssociateUnSettled
        this.menuItemAssociateUnSettled.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssociateUnSettled, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAssociateUnSettled.setText(resHelper.getString("menuItemAssociateUnSettled.text"));
        // menuItemMonthReq
        this.menuItemMonthReq.setAction((IItemAction)ActionProxyFactory.getProxy(actionMonthReq, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemMonthReq.setText(resHelper.getString("menuItemMonthReq.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtuseDepartment,pkbookedDate,txtPaymentRequestBillNumber,pkpayDate,prmtPayment,difPlace,prmtsettlementType,prmtsupplier,txtrecBank,prmtDesc,prmtrealSupplier,txtrecAccount,txtUsage,txtpaymentProportion,txtcompletePrjAmt,btnInputCollect,mergencyState,chkIsPay,txtInvoiceNumber,txtInvoiceAmt,pkInvoiceDate,prmtPlanHasCon,prmtPlanUnCon,btnViewBudget,cmbAttachment,btnViewAttachment,txtattachment,txtProcess,txtMoneyDesc,kdtEntrys}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(0, 0, 1013, 768));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 768));
        contDescription.setBounds(new Rectangle(951, 366, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(951, 366, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractNo.setBounds(new Rectangle(938, 358, 470, 19));
        this.add(contcontractNo, new KDLayout.Constraints(938, 358, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contattachment.setBounds(new Rectangle(791, 338, 140, 19));
        this.add(contattachment, new KDLayout.Constraints(791, 338, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnInputCollect.setBounds(new Rectangle(884, 323, 98, 21));
        this.add(btnInputCollect, new KDLayout.Constraints(884, 323, 98, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contpaymentProportion.setBounds(new Rectangle(899, 379, 270, 19));
        this.add(contpaymentProportion, new KDLayout.Constraints(899, 379, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(937, 386, 270, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(937, 386, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(850, 394, 270, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(850, 394, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAllInvoiceAmt.setBounds(new Rectangle(880, 408, 270, 19));
        this.add(contAllInvoiceAmt, new KDLayout.Constraints(880, 408, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAllPaymentProportion.setBounds(new Rectangle(964, 303, 270, 19));
        this.add(contAllPaymentProportion, new KDLayout.Constraints(964, 303, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewAttachment.setBounds(new Rectangle(789, 366, 98, 21));
        this.add(btnViewAttachment, new KDLayout.Constraints(789, 366, 98, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProcess.setBounds(new Rectangle(936, 344, 270, 19));
        this.add(contProcess, new KDLayout.Constraints(936, 344, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPlanHasCon.setBounds(new Rectangle(846, 346, 270, 19));
        this.add(contPlanHasCon, new KDLayout.Constraints(846, 346, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlanUnCon.setBounds(new Rectangle(819, 330, 270, 19));
        this.add(contPlanUnCon, new KDLayout.Constraints(819, 330, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(0, 0, 1013, 768));
        this.add(kDScrollPane1, new KDLayout.Constraints(0, 0, 1013, 768, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        lblAttachmentContainer.setBounds(new Rectangle(699, 360, 270, 19));
        this.add(lblAttachmentContainer, new KDLayout.Constraints(699, 360, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contDescription
        contDescription.setBoundEditor(prmtDesc);
        //contcontractNo
        contcontractNo.setBoundEditor(txtcontractNo);
        //contattachment
        contattachment.setBoundEditor(txtattachment);
        //contpaymentProportion
        contpaymentProportion.setBoundEditor(txtpaymentProportion);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtUsage);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(txtTotalSettlePrice);
        //contAllInvoiceAmt
        contAllInvoiceAmt.setBoundEditor(txtAllInvoiceAmt);
        //contAllPaymentProportion
        contAllPaymentProportion.setBoundEditor(txtAllPaymentProportion);
        //contProcess
        contProcess.setBoundEditor(txtProcess);
        //contPlanHasCon
        contPlanHasCon.setBoundEditor(prmtPlanHasCon);
        //contPlanUnCon
        contPlanUnCon.setBoundEditor(prmtPlanUnCon);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(kDPanel1, null);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 850));        kDSeparator6.setBounds(new Rectangle(7, 52, 995, 10));
        kDPanel1.add(kDSeparator6, new KDLayout.Constraints(7, 52, 995, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contpaymentRequestBillNumber.setBounds(new Rectangle(5, 5, 270, 19));
        kDPanel1.add(contpaymentRequestBillNumber, new KDLayout.Constraints(5, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrg.setBounds(new Rectangle(5, 27, 270, 19));
        kDPanel1.add(contOrg, new KDLayout.Constraints(5, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractInfo.setBounds(new Rectangle(370, 5, 270, 19));
        kDPanel1.add(contContractInfo, new KDLayout.Constraints(370, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProj.setBounds(new Rectangle(370, 27, 270, 19));
        kDPanel1.add(contProj, new KDLayout.Constraints(370, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayBillType.setBounds(new Rectangle(735, 27, 270, 19));
        kDPanel1.add(contPayBillType, new KDLayout.Constraints(735, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(5, 59, 270, 19));
        kDPanel1.add(kDLabelContainer1, new KDLayout.Constraints(5, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayment.setBounds(new Rectangle(5, 81, 270, 19));
        kDPanel1.add(contPayment, new KDLayout.Constraints(5, 81, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsettlementType.setBounds(new Rectangle(5, 103, 270, 19));
        kDPanel1.add(contsettlementType, new KDLayout.Constraints(5, 103, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(370, 59, 270, 19));
        kDPanel1.add(kDLabelContainer2, new KDLayout.Constraints(370, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsupplier.setBounds(new Rectangle(370, 81, 270, 19));
        kDPanel1.add(contsupplier, new KDLayout.Constraints(370, 81, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrealSupplier.setBounds(new Rectangle(370, 103, 270, 19));
        kDPanel1.add(contrealSupplier, new KDLayout.Constraints(370, 103, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpayDate.setBounds(new Rectangle(735, 59, 270, 19));
        kDPanel1.add(contpayDate, new KDLayout.Constraints(735, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contrecBank.setBounds(new Rectangle(735, 81, 270, 19));
        kDPanel1.add(contrecBank, new KDLayout.Constraints(735, 81, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contrecAccount.setBounds(new Rectangle(735, 103, 270, 19));
        kDPanel1.add(contrecAccount, new KDLayout.Constraints(735, 103, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator8.setBounds(new Rectangle(7, 129, 995, 10));
        kDPanel1.add(kDSeparator8, new KDLayout.Constraints(7, 129, 995, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contcurrency.setBounds(new Rectangle(5, 137, 270, 19));
        kDPanel1.add(contcurrency, new KDLayout.Constraints(5, 137, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contexchangeRate.setBounds(new Rectangle(5, 159, 270, 19));
        kDPanel1.add(contexchangeRate, new KDLayout.Constraints(5, 159, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBcAmount.setBounds(new Rectangle(370, 159, 270, 19));
        kDPanel1.add(contBcAmount, new KDLayout.Constraints(370, 159, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmount.setBounds(new Rectangle(370, 137, 270, 19));
        kDPanel1.add(contAmount, new KDLayout.Constraints(370, 137, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contGrtAmount.setBounds(new Rectangle(735, 137, 270, 19));
        kDPanel1.add(contGrtAmount, new KDLayout.Constraints(735, 137, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contcapitalAmount.setBounds(new Rectangle(735, 159, 270, 19));
        kDPanel1.add(contcapitalAmount, new KDLayout.Constraints(735, 159, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        cbIsInvoice.setBounds(new Rectangle(5, 182, 140, 19));
        kDPanel1.add(cbIsInvoice, new KDLayout.Constraints(5, 182, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoiceNumber.setBounds(new Rectangle(5, 205, 270, 19));
        kDPanel1.add(contInvoiceNumber, new KDLayout.Constraints(5, 205, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoiceAmt.setBounds(new Rectangle(370, 205, 270, 19));
        kDPanel1.add(contInvoiceAmt, new KDLayout.Constraints(370, 205, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoiceDate.setBounds(new Rectangle(735, 205, 270, 19));
        kDPanel1.add(contInvoiceDate, new KDLayout.Constraints(735, 205, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator9.setBounds(new Rectangle(7, 231, 995, 10));
        kDPanel1.add(kDSeparator9, new KDLayout.Constraints(7, 231, 995, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contApplierOrgUnit.setBounds(new Rectangle(370, 240, 270, 19));
        kDPanel1.add(contApplierOrgUnit, new KDLayout.Constraints(370, 240, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cbIsBgControl.setBounds(new Rectangle(5, 240, 140, 19));
        kDPanel1.add(cbIsBgControl, new KDLayout.Constraints(5, 240, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contApplierCompany.setBounds(new Rectangle(370, 240, 270, 19));
        kDPanel1.add(contApplierCompany, new KDLayout.Constraints(370, 240, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostedCompany.setBounds(new Rectangle(370, 263, 270, 19));
        kDPanel1.add(contCostedCompany, new KDLayout.Constraints(370, 263, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contApplier.setBounds(new Rectangle(5, 263, 270, 19));
        kDPanel1.add(contApplier, new KDLayout.Constraints(5, 263, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostedDept.setBounds(new Rectangle(735, 263, 270, 19));
        kDPanel1.add(contCostedDept, new KDLayout.Constraints(735, 263, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBgEntry.setBounds(new Rectangle(5, 290, 359, 134));
        kDPanel1.add(contBgEntry, new KDLayout.Constraints(5, 290, 359, 134, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator10.setBounds(new Rectangle(7, 431, 995, 10));
        kDPanel1.add(kDSeparator10, new KDLayout.Constraints(7, 431, 995, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer4.setBounds(new Rectangle(5, 463, 166, 19));
        kDPanel1.add(kDLabelContainer4, new KDLayout.Constraints(5, 463, 166, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkIsPay.setBounds(new Rectangle(195, 463, 80, 19));
        kDPanel1.add(chkIsPay, new KDLayout.Constraints(195, 463, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(370, 463, 270, 19));
        kDPanel1.add(kDLabelContainer5, new KDLayout.Constraints(370, 463, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(5, 485, 1000, 19));
        kDPanel1.add(kDLabelContainer3, new KDLayout.Constraints(5, 485, 1000, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntrys.setBounds(new Rectangle(5, 511, 1000, 294));
        kDPanel1.add(kdtEntrys, new KDLayout.Constraints(5, 511, 1000, 294, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(5, 808, 270, 19));
        kDPanel1.add(contCreator, new KDLayout.Constraints(5, 808, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(5, 829, 270, 19));
        kDPanel1.add(contAuditor, new KDLayout.Constraints(5, 829, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(370, 808, 270, 19));
        kDPanel1.add(contCreateTime, new KDLayout.Constraints(370, 808, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contauditDate.setBounds(new Rectangle(370, 829, 270, 19));
        kDPanel1.add(contauditDate, new KDLayout.Constraints(370, 829, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contuseDepartment.setBounds(new Rectangle(735, 240, 270, 19));
        kDPanel1.add(contuseDepartment, new KDLayout.Constraints(735, 240, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPayContentType.setBounds(new Rectangle(735, 5, 270, 19));
        kDPanel1.add(contPayContentType, new KDLayout.Constraints(735, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        txtPayPlanValue.setBounds(new Rectangle(5, 441, 635, 19));
        kDPanel1.add(txtPayPlanValue, new KDLayout.Constraints(5, 441, 635, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewBudget.setBounds(new Rectangle(890, 294, 100, 21));
        kDPanel1.add(btnViewBudget, new KDLayout.Constraints(890, 294, 100, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer1.setBounds(new Rectangle(370, 290, 298, 134));
        kDPanel1.add(kDContainer1, new KDLayout.Constraints(370, 290, 298, 134, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAttachment.setBounds(new Rectangle(690, 290, 315, 134));
        kDPanel1.add(contAttachment, new KDLayout.Constraints(690, 290, 315, 134, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contpaymentRequestBillNumber
        contpaymentRequestBillNumber.setBoundEditor(txtPaymentRequestBillNumber);
        //contOrg
        contOrg.setBoundEditor(txtOrg);
        //contContractInfo
        contContractInfo.setBoundEditor(txtContractInfo);
        //contProj
        contProj.setBoundEditor(txtProj);
        //contPayBillType
        contPayBillType.setBoundEditor(prmtPayBillType);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(pkbookedDate);
        //contPayment
        contPayment.setBoundEditor(prmtPayment);
        //contsettlementType
        contsettlementType.setBoundEditor(prmtsettlementType);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(cbPeriod);
        //contsupplier
        contsupplier.setBoundEditor(prmtsupplier);
        //contrealSupplier
        contrealSupplier.setBoundEditor(prmtrealSupplier);
        //contpayDate
        contpayDate.setBoundEditor(pkpayDate);
        //contrecBank
        contrecBank.setBoundEditor(txtrecBank);
        //contrecAccount
        contrecAccount.setBoundEditor(txtrecAccount);
        //contcurrency
        contcurrency.setBoundEditor(prmtcurrency);
        //contexchangeRate
        contexchangeRate.setBoundEditor(txtexchangeRate);
        //contBcAmount
        contBcAmount.setBoundEditor(txtBcAmount);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contGrtAmount
        contGrtAmount.setBoundEditor(txtGrtAmount);
        //contcapitalAmount
        contcapitalAmount.setBoundEditor(txtcapitalAmount);
        //contInvoiceNumber
        contInvoiceNumber.setBoundEditor(txtInvoiceNumber);
        //contInvoiceAmt
        contInvoiceAmt.setBoundEditor(txtInvoiceAmt);
        //contInvoiceDate
        contInvoiceDate.setBoundEditor(pkInvoiceDate);
        //contApplierOrgUnit
        contApplierOrgUnit.setBoundEditor(prmtApplierOrgUnit);
        //contApplierCompany
        contApplierCompany.setBoundEditor(prmtApplierCompany);
        //contCostedCompany
        contCostedCompany.setBoundEditor(prmtCostedCompany);
        //contApplier
        contApplier.setBoundEditor(prmtApplier);
        //contCostedDept
        contCostedDept.setBoundEditor(prmtCostedDept);
        //contBgEntry
contBgEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contBgEntry.getContentPane().add(kdtBgEntry, BorderLayout.CENTER);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(mergencyState);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(difPlace);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtMoneyDesc);
        //contCreator
        contCreator.setBoundEditor(bizPromptCreator);
        //contAuditor
        contAuditor.setBoundEditor(bizPromptAuditor);
        //contCreateTime
        contCreateTime.setBoundEditor(dateCreateTime);
        //contauditDate
        contauditDate.setBoundEditor(pkauditDate);
        //contuseDepartment
        contuseDepartment.setBoundEditor(prmtuseDepartment);
        //contPayContentType
        contPayContentType.setBoundEditor(prmtPayContentType);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(null);        contcompletePrjAmt.setBounds(new Rectangle(17, 14, 270, 19));
        kDContainer1.getContentPane().add(contcompletePrjAmt, null);
        contAllCompletePrjAmt.setBounds(new Rectangle(17, 36, 270, 19));
        kDContainer1.getContentPane().add(contAllCompletePrjAmt, null);
        //contcompletePrjAmt
        contcompletePrjAmt.setBoundEditor(txtcompletePrjAmt);
        //contAllCompletePrjAmt
        contAllCompletePrjAmt.setBoundEditor(txtAllCompletePrjAmt);
        //contAttachment
contAttachment.getContentPane().setLayout(new BorderLayout(0, 0));        contAttachment.getContentPane().add(tblAttachement, BorderLayout.CENTER);
        //lblAttachmentContainer
        lblAttachmentContainer.setBoundEditor(cmbAttachment);

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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(menuItemContractAttachement);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemSendMail);
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
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
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
        menuView.add(kDSeparator5);
        menuView.add(menuItemLocate);
        menuView.add(menuItemViewContract);
        menuView.add(menuItemViewPayDetail);
        menuView.add(menuItemContractExecInfo);
        menuView.add(menuItemViewMaterialConfirm);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemClose);
        menuBiz.add(menuItemUnClose);
        menuBiz.add(menuItemAdjustDeduct);
        menuBiz.add(kDSeparator7);
        menuBiz.add(menuItemViewMbgBalance);
        menuBiz.add(menuItemAssociateAcctPay);
        menuBiz.add(menuItemAssociateUnSettled);
        menuBiz.add(menuItemMonthReq);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnNumberSign);
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
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnTaoPrint);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnCalc);
        this.toolBar.add(btnPaymentPlan);
        this.toolBar.add(btnAdjustDeduct);
        this.toolBar.add(btnSetTotal);
        this.toolBar.add(btnClose);
        this.toolBar.add(btnUnclose);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnViewContract);
        this.toolBar.add(btnViewMaterialConfirm);
        this.toolBar.add(btnViewPayDetail);
        this.toolBar.add(btnContractAttachment);
        this.toolBar.add(btnContractExecInfo);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("description", String.class, this.prmtDesc, "data");
		dataBinder.registerBinding("contractNo", String.class, this.txtcontractNo, "text");
		dataBinder.registerBinding("attachment", int.class, this.txtattachment, "value");
		dataBinder.registerBinding("paymentProportion", java.math.BigDecimal.class, this.txtpaymentProportion, "value");
		dataBinder.registerBinding("usage", String.class, this.txtUsage, "text");
		dataBinder.registerBinding("totalSettlePrice", java.math.BigDecimal.class, this.txtTotalSettlePrice, "value");
		dataBinder.registerBinding("allInvoiceAmt", java.math.BigDecimal.class, this.txtAllInvoiceAmt, "value");
		dataBinder.registerBinding("process", String.class, this.txtProcess, "text");
		dataBinder.registerBinding("planHasCon", com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo.class, this.prmtPlanHasCon, "data");
		dataBinder.registerBinding("planUnCon", com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo.class, this.prmtPlanUnCon, "data");
		dataBinder.registerBinding("isInvoice", boolean.class, this.cbIsInvoice, "selected");
		dataBinder.registerBinding("isBgControl", boolean.class, this.cbIsBgControl, "selected");
		dataBinder.registerBinding("isPay", boolean.class, this.chkIsPay, "selected");
		dataBinder.registerBinding("number", String.class, this.txtPaymentRequestBillNumber, "text");
		dataBinder.registerBinding("payBillType", com.kingdee.eas.fi.cas.PaymentBillTypeInfo.class, this.prmtPayBillType, "data");
		dataBinder.registerBinding("bookedDate", java.util.Date.class, this.pkbookedDate, "value");
		dataBinder.registerBinding("paymentType", com.kingdee.eas.fdc.basedata.PaymentTypeInfo.class, this.prmtPayment, "data");
		dataBinder.registerBinding("settlementType", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.prmtsettlementType, "data");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.cbPeriod, "data");
		dataBinder.registerBinding("supplier", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtsupplier, "data");
		dataBinder.registerBinding("realSupplier", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtrealSupplier, "data");
		dataBinder.registerBinding("payDate", java.util.Date.class, this.pkpayDate, "value");
		dataBinder.registerBinding("recBank", String.class, this.txtrecBank, "text");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtcurrency, "data");
		dataBinder.registerBinding("exchangeRate", java.math.BigDecimal.class, this.txtexchangeRate, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtBcAmount, "value");
		dataBinder.registerBinding("originalAmount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("grtAmount", java.math.BigDecimal.class, this.txtGrtAmount, "value");
		dataBinder.registerBinding("capitalAmount", String.class, this.txtcapitalAmount, "text");
		dataBinder.registerBinding("invoiceNumber", String.class, this.txtInvoiceNumber, "text");
		dataBinder.registerBinding("invoiceAmt", java.math.BigDecimal.class, this.txtInvoiceAmt, "value");
		dataBinder.registerBinding("invoiceDate", java.util.Date.class, this.pkInvoiceDate, "value");
		dataBinder.registerBinding("applierOrgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtApplierOrgUnit, "data");
		dataBinder.registerBinding("applierCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtApplierCompany, "data");
		dataBinder.registerBinding("costedCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtCostedCompany, "data");
		dataBinder.registerBinding("applier", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtApplier, "data");
		dataBinder.registerBinding("costedDept", com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo.class, this.prmtCostedDept, "data");
		dataBinder.registerBinding("urgentDegree", com.kingdee.eas.fdc.contract.UrgentDegreeEnum.class, this.mergencyState, "selectedItem");
		dataBinder.registerBinding("isDifferPlace", com.kingdee.eas.fi.cas.DifPlaceEnum.class, this.difPlace, "selectedItem");
		dataBinder.registerBinding("moneyDesc", String.class, this.txtMoneyDesc, "_multiLangItem");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptCreator, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptAuditor, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.dateCreateTime, "value");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkauditDate, "value");
		dataBinder.registerBinding("useDepartment", com.kingdee.eas.basedata.org.OUPartAdminInfo.class, this.prmtuseDepartment, "data");
		dataBinder.registerBinding("payContentType", com.kingdee.eas.fdc.contract.PayContentTypeInfo.class, this.prmtPayContentType, "data");
		dataBinder.registerBinding("completePrjAmt", java.math.BigDecimal.class, this.txtcompletePrjAmt, "value");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.actionAdjustDeduct, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.actionAttachment, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.actionAttachment, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_VIEW, this.actionCopy, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionAdjustDeduct, ActionStateConst.DISABLED);
	        getActionManager().registerUIState(STATUS_VIEW, this.actionAddNew, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnUnAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnClose, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnUnclose, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnTraceUp, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnTraceDown, ActionStateConst.DISABLED);
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnAdjustDeduct, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionNext, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionCreateTo, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionCopy, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionDelVoucher, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionPre, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionOnLoad, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionCalculator, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionCreateFrom, ActionStateConst.DISABLED);
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionUnClose, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionViewSubmitProccess, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionPersonalSite, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionCancel, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionEdit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionPrintPreview, ActionStateConst.DISABLED);
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionExitCurrent, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionAddLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionPrint, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionViewDoProccess, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionAbout, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionAddNew, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionWorkFlowG, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionSubmitOption, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionUnAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionFirst, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionRemoveLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionRemove, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionCalc, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionTraceDown, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionPageSetup, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionExportSelected, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionTraceUp, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionRegProduct, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionAuditResult, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionAttachment, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionCancelCancel, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionTaoPrint, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionExport, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionSubmit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionSendMessage, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionInsertLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionAdjustDeduct, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionVoucher, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionCopyFrom, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionLast, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionHelp, ActionStateConst.DISABLED);
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionClose, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionNextPerson, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionSave, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionPaymentPlan, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionMultiapprove, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CLOSE, this.actionStartWorkFlow, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.PayRequestBillEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.prmtuseDepartment.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.contract.PayRequestBillInfo)ov;
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("CostCenter");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
		}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("attachment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paymentProportion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("usage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalSettlePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("allInvoiceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("process", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planHasCon", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planUnCon", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isInvoice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isBgControl", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isPay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payBillType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paymentType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("realSupplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("grtAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("capitalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applierOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applierCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("urgentDegree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isDifferPlace", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("moneyDesc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("useDepartment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payContentType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("completePrjAmt", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.actionAdjustDeduct.setVisible(true);
		            this.actionAdjustDeduct.setEnabled(true);
		            this.actionAttachment.setVisible(true);
		            this.actionAttachment.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.actionAttachment.setVisible(true);
		            this.actionAttachment.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.actionCopy.setVisible(true);
		            this.actionCopy.setEnabled(true);
		            this.actionAdjustDeduct.setVisible(true);
		            this.actionAdjustDeduct.setEnabled(false);
		            this.actionAddNew.setVisible(true);
		            this.actionAddNew.setEnabled(true);
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		            this.btnAudit.setEnabled(false);
		            this.btnUnAudit.setEnabled(false);
		            this.btnClose.setEnabled(false);
		            this.btnUnclose.setEnabled(false);
		            this.btnTraceUp.setEnabled(false);
		            this.btnTraceDown.setEnabled(false);
		            this.btnAdjustDeduct.setVisible(true);
		            this.btnAdjustDeduct.setEnabled(true);
        } else if (STATUS_CLOSE.equals(this.oprtState)) {
		            this.actionNext.setVisible(false);
		            this.actionNext.setEnabled(false);
		            this.actionCreateTo.setVisible(false);
		            this.actionCreateTo.setEnabled(false);
		            this.actionCopy.setVisible(false);
		            this.actionCopy.setEnabled(false);
		            this.actionDelVoucher.setVisible(false);
		            this.actionDelVoucher.setEnabled(false);
		            this.actionPre.setVisible(false);
		            this.actionPre.setEnabled(false);
		            this.actionOnLoad.setVisible(false);
		            this.actionOnLoad.setEnabled(false);
		            this.actionCalculator.setVisible(false);
		            this.actionCalculator.setEnabled(false);
		            this.actionCreateFrom.setVisible(false);
		            this.actionCreateFrom.setEnabled(false);
		            this.actionUnClose.setVisible(true);
		            this.actionUnClose.setEnabled(true);
		            this.actionViewSubmitProccess.setVisible(false);
		            this.actionViewSubmitProccess.setEnabled(false);
		            this.actionPersonalSite.setVisible(false);
		            this.actionPersonalSite.setEnabled(false);
		            this.actionCancel.setVisible(false);
		            this.actionCancel.setEnabled(false);
		            this.actionAudit.setVisible(false);
		            this.actionAudit.setEnabled(false);
		            this.actionEdit.setVisible(false);
		            this.actionEdit.setEnabled(false);
		            this.actionPrintPreview.setVisible(false);
		            this.actionPrintPreview.setEnabled(false);
		            this.actionExitCurrent.setVisible(true);
		            this.actionExitCurrent.setEnabled(true);
		            this.actionAddLine.setVisible(false);
		            this.actionAddLine.setEnabled(false);
		            this.actionPrint.setVisible(false);
		            this.actionPrint.setEnabled(false);
		            this.actionViewDoProccess.setVisible(false);
		            this.actionViewDoProccess.setEnabled(false);
		            this.actionAbout.setVisible(false);
		            this.actionAbout.setEnabled(false);
		            this.actionAddNew.setVisible(false);
		            this.actionAddNew.setEnabled(false);
		            this.actionWorkFlowG.setVisible(false);
		            this.actionWorkFlowG.setEnabled(false);
		            this.actionSubmitOption.setVisible(false);
		            this.actionSubmitOption.setEnabled(false);
		            this.actionUnAudit.setVisible(false);
		            this.actionUnAudit.setEnabled(false);
		            this.actionFirst.setVisible(false);
		            this.actionFirst.setEnabled(false);
		            this.actionRemoveLine.setVisible(false);
		            this.actionRemoveLine.setEnabled(false);
		            this.actionRemove.setVisible(false);
		            this.actionRemove.setEnabled(false);
		            this.actionCalc.setVisible(false);
		            this.actionCalc.setEnabled(false);
		            this.actionTraceDown.setVisible(false);
		            this.actionTraceDown.setEnabled(false);
		            this.actionPageSetup.setVisible(false);
		            this.actionPageSetup.setEnabled(false);
		            this.actionExportSelected.setVisible(false);
		            this.actionExportSelected.setEnabled(false);
		            this.actionTraceUp.setVisible(false);
		            this.actionTraceUp.setEnabled(false);
		            this.actionRegProduct.setVisible(false);
		            this.actionRegProduct.setEnabled(false);
		            this.actionAuditResult.setVisible(false);
		            this.actionAuditResult.setEnabled(false);
		            this.actionAttachment.setVisible(false);
		            this.actionAttachment.setEnabled(false);
		            this.actionCancelCancel.setVisible(false);
		            this.actionCancelCancel.setEnabled(false);
		            this.actionTaoPrint.setVisible(false);
		            this.actionTaoPrint.setEnabled(false);
		            this.actionExport.setVisible(false);
		            this.actionExport.setEnabled(false);
		            this.actionSubmit.setVisible(false);
		            this.actionSubmit.setEnabled(false);
		            this.actionSendMessage.setVisible(false);
		            this.actionSendMessage.setEnabled(false);
		            this.actionInsertLine.setVisible(false);
		            this.actionInsertLine.setEnabled(false);
		            this.actionAdjustDeduct.setVisible(false);
		            this.actionAdjustDeduct.setEnabled(false);
		            this.actionVoucher.setVisible(false);
		            this.actionVoucher.setEnabled(false);
		            this.actionCopyFrom.setVisible(false);
		            this.actionCopyFrom.setEnabled(false);
		            this.actionLast.setVisible(false);
		            this.actionLast.setEnabled(false);
		            this.actionHelp.setVisible(false);
		            this.actionHelp.setEnabled(false);
		            this.actionClose.setVisible(true);
		            this.actionClose.setEnabled(true);
		            this.actionNextPerson.setVisible(false);
		            this.actionNextPerson.setEnabled(false);
		            this.actionSave.setVisible(false);
		            this.actionSave.setEnabled(false);
		            this.actionPaymentPlan.setVisible(false);
		            this.actionPaymentPlan.setEnabled(false);
		            this.actionMultiapprove.setVisible(false);
		            this.actionMultiapprove.setEnabled(false);
		            this.actionStartWorkFlow.setVisible(false);
		            this.actionStartWorkFlow.setEnabled(false);
        }
    }

    /**
     * output btnInputCollect_actionPerformed method
     */
    protected void btnInputCollect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtpaymentProportion_dataChanged method
     */
    protected void txtpaymentProportion_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtPlanUnCon_dataChanged method
     */
    protected void prmtPlanUnCon_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output cbIsInvoice_itemStateChanged method
     */
    protected void cbIsInvoice_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output cbIsBgControl_itemStateChanged method
     */
    protected void cbIsBgControl_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output pkbookedDate_dataChanged method
     */
    protected void pkbookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtPayment_willShow method
     */
    protected void prmtPayment_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtPayment_willCommit method
     */
    protected void prmtPayment_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtsettlementType_dataChanged method
     */
    protected void prmtsettlementType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output supplier_dataChanged method
     */
    protected void supplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output realSupplier_dataChanged method
     */
    protected void realSupplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output pkpayDate_dataChanged method
     */
    protected void pkpayDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtrecAccount_willCommit method
     */
    protected void txtrecAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output txtrecAccount_willShow method
     */
    protected void txtrecAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output txtrecAccount_dataChanged method
     */
    protected void txtrecAccount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output currency_dataChanged method
     */
    protected void currency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtexchangeRate_focusLost method
     */
    protected void txtexchangeRate_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtexchangeRate_dataChanged method
     */
    protected void txtexchangeRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtAmount_focusLost method
     */
    protected void txtAmount_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtAmount_dataChanged method
     */
    protected void txtAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtApplierOrgUnit_dataChanged method
     */
    protected void prmtApplierOrgUnit_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCostedCompany_dataChanged method
     */
    protected void prmtCostedCompany_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtApplier_dataChanged method
     */
    protected void prmtApplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCostedDept_dataChanged method
     */
    protected void prmtCostedDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtBgEntry_editStopped method
     */
    protected void kdtBgEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtBgEntry_tableSelectChanged method
     */
    protected void kdtBgEntry_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output txtcompletePrjAmt_dataChanged method
     */
    protected void txtcompletePrjAmt_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblAttachement_tableClicked method
     */
    protected void tblAttachement_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnSetTotal_actionPerformed method
     */
    protected void btnSetTotal_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("contractNo"));
        sic.add(new SelectorItemInfo("attachment"));
        sic.add(new SelectorItemInfo("paymentProportion"));
        sic.add(new SelectorItemInfo("usage"));
        sic.add(new SelectorItemInfo("totalSettlePrice"));
        sic.add(new SelectorItemInfo("allInvoiceAmt"));
        sic.add(new SelectorItemInfo("process"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("planHasCon.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("planHasCon.id"));
        	sic.add(new SelectorItemInfo("planHasCon.number"));
        	sic.add(new SelectorItemInfo("planHasCon.name"));
        	sic.add(new SelectorItemInfo("planHasCon.contractName"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("planUnCon.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("planUnCon.id"));
        	sic.add(new SelectorItemInfo("planUnCon.number"));
        	sic.add(new SelectorItemInfo("planUnCon.name"));
        	sic.add(new SelectorItemInfo("planUnCon.unConName"));
		}
        sic.add(new SelectorItemInfo("isInvoice"));
        sic.add(new SelectorItemInfo("isBgControl"));
        sic.add(new SelectorItemInfo("isPay"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payBillType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("payBillType.id"));
        	sic.add(new SelectorItemInfo("payBillType.number"));
        	sic.add(new SelectorItemInfo("payBillType.name"));
		}
        sic.add(new SelectorItemInfo("bookedDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("paymentType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("paymentType.id"));
        	sic.add(new SelectorItemInfo("paymentType.number"));
        	sic.add(new SelectorItemInfo("paymentType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("settlementType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("settlementType.id"));
        	sic.add(new SelectorItemInfo("settlementType.number"));
        	sic.add(new SelectorItemInfo("settlementType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("period.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("period.id"));
        	sic.add(new SelectorItemInfo("period.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("supplier.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("supplier.id"));
        	sic.add(new SelectorItemInfo("supplier.number"));
        	sic.add(new SelectorItemInfo("supplier.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("realSupplier.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("realSupplier.id"));
        	sic.add(new SelectorItemInfo("realSupplier.number"));
        	sic.add(new SelectorItemInfo("realSupplier.name"));
		}
        sic.add(new SelectorItemInfo("payDate"));
        sic.add(new SelectorItemInfo("recBank"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("currency.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("currency.id"));
        	sic.add(new SelectorItemInfo("currency.number"));
        	sic.add(new SelectorItemInfo("currency.name"));
		}
        sic.add(new SelectorItemInfo("exchangeRate"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("grtAmount"));
        sic.add(new SelectorItemInfo("capitalAmount"));
        sic.add(new SelectorItemInfo("invoiceNumber"));
        sic.add(new SelectorItemInfo("invoiceAmt"));
        sic.add(new SelectorItemInfo("invoiceDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("applierOrgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("applierOrgUnit.id"));
        	sic.add(new SelectorItemInfo("applierOrgUnit.number"));
        	sic.add(new SelectorItemInfo("applierOrgUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("applierCompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("applierCompany.id"));
        	sic.add(new SelectorItemInfo("applierCompany.number"));
        	sic.add(new SelectorItemInfo("applierCompany.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costedCompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("costedCompany.id"));
        	sic.add(new SelectorItemInfo("costedCompany.number"));
        	sic.add(new SelectorItemInfo("costedCompany.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("applier.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("applier.id"));
        	sic.add(new SelectorItemInfo("applier.number"));
        	sic.add(new SelectorItemInfo("applier.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costedDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("costedDept.id"));
        	sic.add(new SelectorItemInfo("costedDept.number"));
        	sic.add(new SelectorItemInfo("costedDept.name"));
		}
        sic.add(new SelectorItemInfo("urgentDegree"));
        sic.add(new SelectorItemInfo("isDifferPlace"));
        sic.add(new SelectorItemInfo("moneyDesc"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("useDepartment.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("useDepartment.id"));
        	sic.add(new SelectorItemInfo("useDepartment.number"));
        	sic.add(new SelectorItemInfo("useDepartment.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payContentType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("payContentType.id"));
        	sic.add(new SelectorItemInfo("payContentType.number"));
        	sic.add(new SelectorItemInfo("payContentType.name"));
        	sic.add(new SelectorItemInfo("payContentType.longNumber"));
		}
        sic.add(new SelectorItemInfo("completePrjAmt"));
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
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
    	

    /**
     * output actionCalc_actionPerformed method
     */
    public void actionCalc_actionPerformed(ActionEvent e) throws Exception
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
     * output actionAdjustDeduct_actionPerformed method
     */
    public void actionAdjustDeduct_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClose_actionPerformed method
     */
    public void actionClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnClose_actionPerformed method
     */
    public void actionUnClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContract_actionPerformed method
     */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewMbgBalance_actionPerformed method
     */
    public void actionViewMbgBalance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewPayDetail_actionPerformed method
     */
    public void actionViewPayDetail_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAssociateAcctPay_actionPerformed method
     */
    public void actionAssociateAcctPay_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAssociateUnSettled_actionPerformed method
     */
    public void actionAssociateUnSettled_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionContractAttachment_actionPerformed method
     */
    public void actionContractAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewMaterialConfirm_actionPerformed method
     */
    public void actionViewMaterialConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionContractExecInfo_actionPerformed method
     */
    public void actionContractExecInfo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMonthReq_actionPerformed method
     */
    public void actionMonthReq_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewAttachment_actionPerformed method
     */
    public void actionViewAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewBudget_actionPerformed method
     */
    public void actionViewBudget_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionRemove(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRemove(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemove() {
    	return false;
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionUnAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionCalc(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalc() {
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
	public RequestContext prepareActionAdjustDeduct(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAdjustDeduct() {
    	return false;
    }
	public RequestContext prepareActionClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClose() {
    	return false;
    }
	public RequestContext prepareActionUnClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnClose() {
    	return false;
    }
	public RequestContext prepareActionViewContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContract() {
    	return false;
    }
	public RequestContext prepareActionViewMbgBalance(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewMbgBalance() {
    	return false;
    }
	public RequestContext prepareActionViewPayDetail(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewPayDetail() {
    	return false;
    }
	public RequestContext prepareActionAssociateAcctPay(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAssociateAcctPay() {
    	return false;
    }
	public RequestContext prepareActionAssociateUnSettled(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAssociateUnSettled() {
    	return false;
    }
	public RequestContext prepareActionContractAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionContractAttachment() {
    	return false;
    }
	public RequestContext prepareActionViewMaterialConfirm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewMaterialConfirm() {
    	return false;
    }
	public RequestContext prepareActionContractExecInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionContractExecInfo() {
    	return false;
    }
	public RequestContext prepareActionMonthReq(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMonthReq() {
    	return false;
    }
	public RequestContext prepareActionViewAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewAttachment() {
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

    /**
     * output ActionCalc class
     */     
    protected class ActionCalc extends ItemAction {     
    
        public ActionCalc()
        {
            this(null);
        }

        public ActionCalc(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCalc.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalc.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalc.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionCalc", "actionCalc_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionTaoPrint", "actionTaoPrint_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionPaymentPlan", "actionPaymentPlan_actionPerformed", e);
        }
    }

    /**
     * output ActionAdjustDeduct class
     */     
    protected class ActionAdjustDeduct extends ItemAction {     
    
        public ActionAdjustDeduct()
        {
            this(null);
        }

        public ActionAdjustDeduct(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAdjustDeduct.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdjustDeduct.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdjustDeduct.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionAdjustDeduct", "actionAdjustDeduct_actionPerformed", e);
        }
    }

    /**
     * output ActionClose class
     */     
    protected class ActionClose extends ItemAction {     
    
        public ActionClose()
        {
            this(null);
        }

        public ActionClose(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionClose", "actionClose_actionPerformed", e);
        }
    }

    /**
     * output ActionUnClose class
     */     
    protected class ActionUnClose extends ItemAction {     
    
        public ActionUnClose()
        {
            this(null);
        }

        public ActionUnClose(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionUnClose", "actionUnClose_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContract class
     */     
    protected class ActionViewContract extends ItemAction {     
    
        public ActionViewContract()
        {
            this(null);
        }

        public ActionViewContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionViewContract", "actionViewContract_actionPerformed", e);
        }
    }

    /**
     * output ActionViewMbgBalance class
     */     
    protected class ActionViewMbgBalance extends ItemAction {     
    
        public ActionViewMbgBalance()
        {
            this(null);
        }

        public ActionViewMbgBalance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift M"));
            _tempStr = resHelper.getString("ActionViewMbgBalance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewMbgBalance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewMbgBalance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionViewMbgBalance", "actionViewMbgBalance_actionPerformed", e);
        }
    }

    /**
     * output ActionViewPayDetail class
     */     
    protected class ActionViewPayDetail extends ItemAction {     
    
        public ActionViewPayDetail()
        {
            this(null);
        }

        public ActionViewPayDetail(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewPayDetail.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPayDetail.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPayDetail.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionViewPayDetail", "actionViewPayDetail_actionPerformed", e);
        }
    }

    /**
     * output ActionAssociateAcctPay class
     */     
    protected class ActionAssociateAcctPay extends ItemAction {     
    
        public ActionAssociateAcctPay()
        {
            this(null);
        }

        public ActionAssociateAcctPay(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAssociateAcctPay.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssociateAcctPay.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssociateAcctPay.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionAssociateAcctPay", "actionAssociateAcctPay_actionPerformed", e);
        }
    }

    /**
     * output ActionAssociateUnSettled class
     */     
    protected class ActionAssociateUnSettled extends ItemAction {     
    
        public ActionAssociateUnSettled()
        {
            this(null);
        }

        public ActionAssociateUnSettled(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAssociateUnSettled.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssociateUnSettled.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssociateUnSettled.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionAssociateUnSettled", "actionAssociateUnSettled_actionPerformed", e);
        }
    }

    /**
     * output ActionContractAttachment class
     */     
    protected class ActionContractAttachment extends ItemAction {     
    
        public ActionContractAttachment()
        {
            this(null);
        }

        public ActionContractAttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionContractAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionContractAttachment", "actionContractAttachment_actionPerformed", e);
        }
    }

    /**
     * output ActionViewMaterialConfirm class
     */     
    protected class ActionViewMaterialConfirm extends ItemAction {     
    
        public ActionViewMaterialConfirm()
        {
            this(null);
        }

        public ActionViewMaterialConfirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewMaterialConfirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewMaterialConfirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewMaterialConfirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionViewMaterialConfirm", "actionViewMaterialConfirm_actionPerformed", e);
        }
    }

    /**
     * output ActionContractExecInfo class
     */     
    protected class ActionContractExecInfo extends ItemAction {     
    
        public ActionContractExecInfo()
        {
            this(null);
        }

        public ActionContractExecInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionContractExecInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractExecInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractExecInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionContractExecInfo", "actionContractExecInfo_actionPerformed", e);
        }
    }

    /**
     * output ActionMonthReq class
     */     
    protected class ActionMonthReq extends ItemAction {     
    
        public ActionMonthReq()
        {
            this(null);
        }

        public ActionMonthReq(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("Ctrl Shift R"));
            _tempStr = resHelper.getString("ActionMonthReq.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMonthReq.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMonthReq.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionMonthReq", "actionMonthReq_actionPerformed", e);
        }
    }

    /**
     * output ActionViewAttachment class
     */     
    protected class ActionViewAttachment extends ItemAction {     
    
        public ActionViewAttachment()
        {
            this(null);
        }

        public ActionViewAttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "ActionViewAttachment", "actionViewAttachment_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractPayRequestBillEditUI.this, "actionViewBudget", "actionViewBudget_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "PayRequestBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.PayRequestBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.PayRequestBillInfo objectValue = new com.kingdee.eas.fdc.contract.PayRequestBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/contract/PayRequestBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.PayRequestBillQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtBgEntry;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
				vo.put("originalAmount",new java.math.BigDecimal(0));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}