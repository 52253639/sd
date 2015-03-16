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
public abstract class AbstractContractWithoutTextEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractWithoutTextEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbillName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreceiveUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbankAcct;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsettlementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkCostsplit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkUrgency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkNeedPaid;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conConCharge;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAllInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblAttachmentContainer;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanProject;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewBudget;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplierOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsBgControl;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplierCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostedCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostedDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplier;
    protected com.kingdee.bos.ctrl.swing.KDContainer contBgEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayBillType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsInvoice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conContrarctRule;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayContentType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayeeType;
    protected com.kingdee.bos.ctrl.swing.KDLabel txtPayPlanValue;
    protected com.kingdee.bos.ctrl.swing.KDContainer contAttachment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcurProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbillName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtamount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pksignDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtreceiveUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBank;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBankAcct;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsettlementType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtuseDepartment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrealSupplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPaymentProportion;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPaymentRequestBillNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtexchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcapitalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBcAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtattachment;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrg;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbookedDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox cbPeriod;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcompletePrjAmt;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtMoneyDesc;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtNoPaidReason;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtConCharge;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkInvoiceDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAllInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbAttachment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPlanProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtApplierOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtApplierCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostedCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostedDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtApplier;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtBgEntry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayBillType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPCName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayContentType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPayeeType;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAttachement;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewBgBalance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewProgramContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProgram;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBgBalance;
    protected com.kingdee.eas.fdc.contract.ContractWithoutTextInfo editData = null;
    protected ActionViewBgBalance actionViewBgBalance = null;
    protected ActionViewAttachment actionViewAttachment = null;
    protected actionViewBudget actionViewBudget = null;
    protected ActionProgram actionProgram = null;
    protected ActionViewProgramContract actionViewProgramContract = null;
    /**
     * output class constructor
     */
    public AbstractContractWithoutTextEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractWithoutTextEditUI.class.getName());
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
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrint
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionDelVoucher
        actionDelVoucher.setEnabled(true);
        actionDelVoucher.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionDelVoucher.SHORT_DESCRIPTION");
        actionDelVoucher.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionDelVoucher.LONG_DESCRIPTION");
        actionDelVoucher.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionDelVoucher.NAME");
        actionDelVoucher.putValue(ItemAction.NAME, _tempStr);
        this.actionDelVoucher.setBindWorkFlow(true);
         this.actionDelVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        actionAudit.setEnabled(true);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionAudit.setBindWorkFlow(true);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        actionUnAudit.setEnabled(true);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBgBalance
        this.actionViewBgBalance = new ActionViewBgBalance(this);
        getActionManager().registerAction("actionViewBgBalance", actionViewBgBalance);
         this.actionViewBgBalance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAttachment
        this.actionViewAttachment = new ActionViewAttachment(this);
        getActionManager().registerAction("actionViewAttachment", actionViewAttachment);
         this.actionViewAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBudget
        this.actionViewBudget = new actionViewBudget(this);
        getActionManager().registerAction("actionViewBudget", actionViewBudget);
         this.actionViewBudget.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProgram
        this.actionProgram = new ActionProgram(this);
        getActionManager().registerAction("actionProgram", actionProgram);
         this.actionProgram.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewProgramContract
        this.actionViewProgramContract = new ActionViewProgramContract(this);
        getActionManager().registerAction("actionViewProgramContract", actionViewProgramContract);
         this.actionViewProgramContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbillName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreceiveUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbankAcct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsettlementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkCostsplit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkUrgency = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkNeedPaid = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer17 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conConCharge = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAllInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblAttachmentContainer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewAttachment = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contPlanProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewBudget = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contApplierOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsBgControl = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contApplierCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCostedCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCostedDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBgEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contPayBillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsInvoice = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.conContrarctRule = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayContentType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayeeType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPayPlanValue = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contAttachment = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtbillName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtamount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pksignDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtreceiveUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBankAcct = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtsettlementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkauditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtPayment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtuseDepartment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtrealSupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPaymentProportion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPaymentRequestBillNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtexchangeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtcapitalAmount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBcAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtattachment = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkbookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcompletePrjAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtMoneyDesc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtNoPaidReason = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtContractType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtConCharge = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtInvoiceNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkInvoiceDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAllInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.cmbAttachment = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtPlanProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtApplierOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtApplierCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCostedCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCostedDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtApplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtBgEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtPayBillType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPCName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtPayContentType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboPayeeType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblAttachement = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnViewBgBalance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewProgramContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnProgram = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewBgBalance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contAuditor.setName("contAuditor");
        this.contcurProject.setName("contcurProject");
        this.contbillName.setName("contbillName");
        this.contamount.setName("contamount");
        this.contpayDate.setName("contpayDate");
        this.contreceiveUnit.setName("contreceiveUnit");
        this.contbank.setName("contbank");
        this.contbankAcct.setName("contbankAcct");
        this.contsettlementType.setName("contsettlementType");
        this.contauditTime.setName("contauditTime");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.chkCostsplit.setName("chkCostsplit");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.chkUrgency.setName("chkUrgency");
        this.contOrg.setName("contOrg");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.chkNeedPaid.setName("chkNeedPaid");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.kDLabelContainer17.setName("kDLabelContainer17");
        this.conConCharge.setName("conConCharge");
        this.contInvoiceNumber.setName("contInvoiceNumber");
        this.contInvoiceDate.setName("contInvoiceDate");
        this.contInvoiceAmt.setName("contInvoiceAmt");
        this.contAllInvoiceAmt.setName("contAllInvoiceAmt");
        this.lblAttachmentContainer.setName("lblAttachmentContainer");
        this.btnViewAttachment.setName("btnViewAttachment");
        this.contPlanProject.setName("contPlanProject");
        this.btnViewBudget.setName("btnViewBudget");
        this.kDSeparator8.setName("kDSeparator8");
        this.kDSeparator9.setName("kDSeparator9");
        this.contApplierOrgUnit.setName("contApplierOrgUnit");
        this.cbIsBgControl.setName("cbIsBgControl");
        this.contApplierCompany.setName("contApplierCompany");
        this.contCostedCompany.setName("contCostedCompany");
        this.contCostedDept.setName("contCostedDept");
        this.contApplier.setName("contApplier");
        this.contBgEntry.setName("contBgEntry");
        this.contPayBillType.setName("contPayBillType");
        this.cbIsInvoice.setName("cbIsInvoice");
        this.conContrarctRule.setName("conContrarctRule");
        this.contPayContentType.setName("contPayContentType");
        this.contPayeeType.setName("contPayeeType");
        this.txtPayPlanValue.setName("txtPayPlanValue");
        this.contAttachment.setName("contAttachment");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.txtNumber.setName("txtNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtcurProject.setName("prmtcurProject");
        this.txtbillName.setName("txtbillName");
        this.txtamount.setName("txtamount");
        this.pksignDate.setName("pksignDate");
        this.prmtreceiveUnit.setName("prmtreceiveUnit");
        this.txtBank.setName("txtBank");
        this.txtBankAcct.setName("txtBankAcct");
        this.prmtsettlementType.setName("prmtsettlementType");
        this.pkauditTime.setName("pkauditTime");
        this.prmtPayment.setName("prmtPayment");
        this.prmtuseDepartment.setName("prmtuseDepartment");
        this.prmtrealSupplier.setName("prmtrealSupplier");
        this.prmtcurrency.setName("prmtcurrency");
        this.txtPaymentProportion.setName("txtPaymentProportion");
        this.txtPaymentRequestBillNumber.setName("txtPaymentRequestBillNumber");
        this.txtexchangeRate.setName("txtexchangeRate");
        this.txtcapitalAmount.setName("txtcapitalAmount");
        this.txtBcAmount.setName("txtBcAmount");
        this.txtattachment.setName("txtattachment");
        this.txtOrg.setName("txtOrg");
        this.pkbookedDate.setName("pkbookedDate");
        this.cbPeriod.setName("cbPeriod");
        this.txtcompletePrjAmt.setName("txtcompletePrjAmt");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtMoneyDesc.setName("txtMoneyDesc");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtNoPaidReason.setName("txtNoPaidReason");
        this.prmtAccount.setName("prmtAccount");
        this.prmtContractType.setName("prmtContractType");
        this.prmtConCharge.setName("prmtConCharge");
        this.txtInvoiceNumber.setName("txtInvoiceNumber");
        this.pkInvoiceDate.setName("pkInvoiceDate");
        this.txtInvoiceAmt.setName("txtInvoiceAmt");
        this.txtAllInvoiceAmt.setName("txtAllInvoiceAmt");
        this.cmbAttachment.setName("cmbAttachment");
        this.prmtPlanProject.setName("prmtPlanProject");
        this.prmtApplierOrgUnit.setName("prmtApplierOrgUnit");
        this.prmtApplierCompany.setName("prmtApplierCompany");
        this.prmtCostedCompany.setName("prmtCostedCompany");
        this.prmtCostedDept.setName("prmtCostedDept");
        this.prmtApplier.setName("prmtApplier");
        this.kdtBgEntry.setName("kdtBgEntry");
        this.prmtPayBillType.setName("prmtPayBillType");
        this.txtPCName.setName("txtPCName");
        this.prmtPayContentType.setName("prmtPayContentType");
        this.comboPayeeType.setName("comboPayeeType");
        this.tblAttachement.setName("tblAttachement");
        this.btnViewBgBalance.setName("btnViewBgBalance");
        this.btnViewProgramContract.setName("btnViewProgramContract");
        this.btnProgram.setName("btnProgram");
        this.menuItemViewBgBalance.setName("menuItemViewBgBalance");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,650));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemSubmit.setToolTipText(resHelper.getString("menuItemSubmit.toolTipText"));		
        this.menuSubmitOption.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnCopyFrom.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.separator1.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.separator2.setVisible(false);		
        this.separator3.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewDoProccess.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setToolTipText(resHelper.getString("btnUnAudit.toolTipText"));
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));
        this.menuItemUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setToolTipText(resHelper.getString("menuItemUnAudit.toolTipText"));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelAlignment(7);		
        this.contNumber.setVisible(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contcurProject		
        this.contcurProject.setBoundLabelText(resHelper.getString("contcurProject.boundLabelText"));		
        this.contcurProject.setBoundLabelLength(100);		
        this.contcurProject.setBoundLabelUnderline(true);		
        this.contcurProject.setVisible(true);		
        this.contcurProject.setBoundLabelAlignment(7);
        // contbillName		
        this.contbillName.setBoundLabelText(resHelper.getString("contbillName.boundLabelText"));		
        this.contbillName.setBoundLabelLength(100);		
        this.contbillName.setBoundLabelUnderline(true);		
        this.contbillName.setVisible(true);		
        this.contbillName.setBoundLabelAlignment(7);
        // contamount		
        this.contamount.setBoundLabelText(resHelper.getString("contamount.boundLabelText"));		
        this.contamount.setBoundLabelLength(100);		
        this.contamount.setBoundLabelUnderline(true);		
        this.contamount.setVisible(true);		
        this.contamount.setBoundLabelAlignment(7);
        // contpayDate		
        this.contpayDate.setBoundLabelText(resHelper.getString("contpayDate.boundLabelText"));		
        this.contpayDate.setBoundLabelLength(100);		
        this.contpayDate.setBoundLabelUnderline(true);		
        this.contpayDate.setVisible(true);		
        this.contpayDate.setBoundLabelAlignment(7);
        // contreceiveUnit		
        this.contreceiveUnit.setBoundLabelText(resHelper.getString("contreceiveUnit.boundLabelText"));		
        this.contreceiveUnit.setBoundLabelLength(100);		
        this.contreceiveUnit.setBoundLabelUnderline(true);		
        this.contreceiveUnit.setVisible(true);		
        this.contreceiveUnit.setBoundLabelAlignment(7);
        // contbank		
        this.contbank.setBoundLabelText(resHelper.getString("contbank.boundLabelText"));		
        this.contbank.setBoundLabelLength(100);		
        this.contbank.setBoundLabelUnderline(true);		
        this.contbank.setVisible(true);
        // contbankAcct		
        this.contbankAcct.setBoundLabelText(resHelper.getString("contbankAcct.boundLabelText"));		
        this.contbankAcct.setBoundLabelLength(100);		
        this.contbankAcct.setBoundLabelUnderline(true);		
        this.contbankAcct.setVisible(true);
        // contsettlementType		
        this.contsettlementType.setBoundLabelText(resHelper.getString("contsettlementType.boundLabelText"));		
        this.contsettlementType.setBoundLabelLength(100);		
        this.contsettlementType.setBoundLabelUnderline(true);		
        this.contsettlementType.setVisible(true);
        // contauditTime		
        this.contauditTime.setBoundLabelText(resHelper.getString("contauditTime.boundLabelText"));		
        this.contauditTime.setBoundLabelLength(100);		
        this.contauditTime.setBoundLabelUnderline(true);		
        this.contauditTime.setVisible(true);		
        this.contauditTime.setBoundLabelAlignment(7);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setVisible(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setVisible(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setVisible(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setVisible(false);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);		
        this.kDLabelContainer9.setBoundLabelAlignment(7);		
        this.kDLabelContainer9.setVisible(false);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setVisible(true);		
        this.kDLabelContainer3.setBoundLabelAlignment(7);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);		
        this.kDLabelContainer10.setBoundLabelAlignment(7);		
        this.kDLabelContainer10.setVisible(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);		
        this.kDLabelContainer11.setVisible(true);		
        this.kDLabelContainer11.setBoundLabelAlignment(7);
        // chkCostsplit		
        this.chkCostsplit.setText(resHelper.getString("chkCostsplit.text"));
        this.chkCostsplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkCostsplit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);		
        this.kDLabelContainer12.setVisible(false);		
        this.kDLabelContainer12.setBoundLabelAlignment(7);
        // chkUrgency		
        this.chkUrgency.setText(resHelper.getString("chkUrgency.text"));
        // contOrg		
        this.contOrg.setBoundLabelText(resHelper.getString("contOrg.boundLabelText"));		
        this.contOrg.setBoundLabelLength(100);		
        this.contOrg.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // chkNeedPaid		
        this.chkNeedPaid.setText(resHelper.getString("chkNeedPaid.text"));
        this.chkNeedPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkNeedPaid_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelUnderline(true);		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setVisible(false);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(100);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(100);		
        this.kDLabelContainer15.setBoundLabelUnderline(true);		
        this.kDLabelContainer15.setEnabled(false);
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(100);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);		
        this.kDLabelContainer16.setVisible(false);
        // kDLabelContainer17		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelLength(100);		
        this.kDLabelContainer17.setBoundLabelUnderline(true);
        // conConCharge		
        this.conConCharge.setBoundLabelText(resHelper.getString("conConCharge.boundLabelText"));		
        this.conConCharge.setBoundLabelLength(100);		
        this.conConCharge.setBoundLabelUnderline(true);		
        this.conConCharge.setVisible(false);
        // contInvoiceNumber		
        this.contInvoiceNumber.setBoundLabelText(resHelper.getString("contInvoiceNumber.boundLabelText"));		
        this.contInvoiceNumber.setBoundLabelLength(100);		
        this.contInvoiceNumber.setBoundLabelUnderline(true);
        // contInvoiceDate		
        this.contInvoiceDate.setBoundLabelText(resHelper.getString("contInvoiceDate.boundLabelText"));		
        this.contInvoiceDate.setBoundLabelLength(100);		
        this.contInvoiceDate.setBoundLabelUnderline(true);
        // contInvoiceAmt		
        this.contInvoiceAmt.setBoundLabelText(resHelper.getString("contInvoiceAmt.boundLabelText"));		
        this.contInvoiceAmt.setBoundLabelLength(100);		
        this.contInvoiceAmt.setBoundLabelUnderline(true);
        // contAllInvoiceAmt		
        this.contAllInvoiceAmt.setBoundLabelText(resHelper.getString("contAllInvoiceAmt.boundLabelText"));		
        this.contAllInvoiceAmt.setBoundLabelLength(100);		
        this.contAllInvoiceAmt.setBoundLabelUnderline(true);		
        this.contAllInvoiceAmt.setVisible(false);
        // lblAttachmentContainer		
        this.lblAttachmentContainer.setBoundLabelText(resHelper.getString("lblAttachmentContainer.boundLabelText"));		
        this.lblAttachmentContainer.setBoundLabelLength(100);		
        this.lblAttachmentContainer.setBoundLabelUnderline(true);		
        this.lblAttachmentContainer.setVisible(false);
        // btnViewAttachment
        this.btnViewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAttachment.setText(resHelper.getString("btnViewAttachment.text"));		
        this.btnViewAttachment.setVisible(false);
        // contPlanProject		
        this.contPlanProject.setBoundLabelText(resHelper.getString("contPlanProject.boundLabelText"));		
        this.contPlanProject.setBoundLabelUnderline(true);		
        this.contPlanProject.setBoundLabelLength(100);		
        this.contPlanProject.setVisible(false);
        // btnViewBudget
        this.btnViewBudget.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBudget, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBudget.setText(resHelper.getString("btnViewBudget.text"));		
        this.btnViewBudget.setVisible(false);
        // kDSeparator8
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
        // contCostedDept		
        this.contCostedDept.setBoundLabelText(resHelper.getString("contCostedDept.boundLabelText"));		
        this.contCostedDept.setBoundLabelLength(100);		
        this.contCostedDept.setBoundLabelUnderline(true);
        // contApplier		
        this.contApplier.setBoundLabelText(resHelper.getString("contApplier.boundLabelText"));		
        this.contApplier.setBoundLabelLength(100);		
        this.contApplier.setBoundLabelUnderline(true);
        // contBgEntry		
        this.contBgEntry.setTitle(resHelper.getString("contBgEntry.title"));		
        this.contBgEntry.setAutoscrolls(true);		
        this.contBgEntry.setEnableActive(false);
        // contPayBillType		
        this.contPayBillType.setBoundLabelText(resHelper.getString("contPayBillType.boundLabelText"));		
        this.contPayBillType.setBoundLabelLength(100);		
        this.contPayBillType.setBoundLabelUnderline(true);		
        this.contPayBillType.setVisible(false);
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
        // conContrarctRule		
        this.conContrarctRule.setBoundLabelText(resHelper.getString("conContrarctRule.boundLabelText"));		
        this.conContrarctRule.setBoundLabelLength(100);		
        this.conContrarctRule.setBoundLabelUnderline(true);
        // contPayContentType		
        this.contPayContentType.setBoundLabelText(resHelper.getString("contPayContentType.boundLabelText"));		
        this.contPayContentType.setBoundLabelLength(100);		
        this.contPayContentType.setBoundLabelUnderline(true);
        // contPayeeType		
        this.contPayeeType.setBoundLabelText(resHelper.getString("contPayeeType.boundLabelText"));		
        this.contPayeeType.setBoundLabelLength(100);		
        this.contPayeeType.setBoundLabelUnderline(true);
        // txtPayPlanValue		
        this.txtPayPlanValue.setForeground(new java.awt.Color(255,0,0));
        // contAttachment		
        this.contAttachment.setTitle(resHelper.getString("contAttachment.title"));
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");
        // kDDateCreateTime		
        this.kDDateCreateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(true);
        this.txtNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtNumber_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$number$");		
        this.prmtAuditor.setCommitFormat("$number$");
        // prmtcurProject		
        this.prmtcurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtcurProject.setVisible(true);		
        this.prmtcurProject.setEditable(true);		
        this.prmtcurProject.setDisplayFormat("$displayname$");		
        this.prmtcurProject.setEditFormat("$number$");		
        this.prmtcurProject.setCommitFormat("$number$");		
        this.prmtcurProject.setRequired(true);
        this.prmtcurProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtcurProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtbillName		
        this.txtbillName.setVisible(true);		
        this.txtbillName.setHorizontalAlignment(2);		
        this.txtbillName.setMaxLength(100);		
        this.txtbillName.setRequired(true);		
        this.txtbillName.setEnabled(true);
        // txtamount		
        this.txtamount.setVisible(true);		
        this.txtamount.setHorizontalAlignment(2);		
        this.txtamount.setDataType(1);		
        this.txtamount.setSupportedEmpty(true);		
        this.txtamount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtamount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtamount.setPrecision(2);		
        this.txtamount.setRequired(true);		
        this.txtamount.setEnabled(true);
        this.txtamount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtamount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtamount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtamount_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // pksignDate		
        this.pksignDate.setVisible(true);		
        this.pksignDate.setEnabled(false);		
        this.pksignDate.setRequired(true);
        // prmtreceiveUnit		
        this.prmtreceiveUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtreceiveUnit.setVisible(true);		
        this.prmtreceiveUnit.setEditable(true);		
        this.prmtreceiveUnit.setDisplayFormat("$number$ $name$");		
        this.prmtreceiveUnit.setEditFormat("$number$");		
        this.prmtreceiveUnit.setCommitFormat("$number$");		
        this.prmtreceiveUnit.setRequired(true);
        this.prmtreceiveUnit.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtreceiveUnit_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtBank		
        this.txtBank.setRequired(true);
        // txtBankAcct		
        this.txtBankAcct.setRequired(true);
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
        // pkauditTime		
        this.pkauditTime.setVisible(true);		
        this.pkauditTime.setEnabled(false);
        this.pkauditTime.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkauditTime_dataChanged(e);
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
        // prmtuseDepartment		
        this.prmtuseDepartment.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.prmtuseDepartment.setVisible(true);		
        this.prmtuseDepartment.setEditable(true);		
        this.prmtuseDepartment.setDisplayFormat("$number$ $name$");		
        this.prmtuseDepartment.setEditFormat("$number$");		
        this.prmtuseDepartment.setCommitFormat("$number$");		
        this.prmtuseDepartment.setRequired(true);
        // prmtrealSupplier		
        this.prmtrealSupplier.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtrealSupplier.setVisible(true);		
        this.prmtrealSupplier.setEditable(true);		
        this.prmtrealSupplier.setDisplayFormat("$number$ $name$");		
        this.prmtrealSupplier.setEditFormat("$number$");		
        this.prmtrealSupplier.setCommitFormat("$number$");		
        this.prmtrealSupplier.setRequired(true);
        this.prmtrealSupplier.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtrealSupplier_dataChanged(e);
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
        this.prmtcurrency.setDisplayFormat("$number$ $name$");		
        this.prmtcurrency.setEditFormat("$number$");		
        this.prmtcurrency.setCommitFormat("$number$");		
        this.prmtcurrency.setRequired(true);
        this.prmtcurrency.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtcurrency_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPaymentProportion		
        this.txtPaymentProportion.setDataType(1);		
        this.txtPaymentProportion.setEnabled(false);
        this.txtPaymentProportion.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtpaymentProportion_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPaymentRequestBillNumber		
        this.txtPaymentRequestBillNumber.setMaxLength(80);		
        this.txtPaymentRequestBillNumber.setVisible(true);		
        this.txtPaymentRequestBillNumber.setEnabled(true);		
        this.txtPaymentRequestBillNumber.setHorizontalAlignment(2);		
        this.txtPaymentRequestBillNumber.setRequired(true);		
        this.txtPaymentRequestBillNumber.setText(resHelper.getString("txtPaymentRequestBillNumber.text"));
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
        // txtcapitalAmount		
        this.txtcapitalAmount.setMaxLength(80);		
        this.txtcapitalAmount.setVisible(true);		
        this.txtcapitalAmount.setEnabled(true);		
        this.txtcapitalAmount.setHorizontalAlignment(2);		
        this.txtcapitalAmount.setRequired(false);		
        this.txtcapitalAmount.setText(resHelper.getString("txtcapitalAmount.text"));		
        this.txtcapitalAmount.setEditable(false);
        // txtBcAmount		
        this.txtBcAmount.setDataType(1);		
        this.txtBcAmount.setRequired(true);		
        this.txtBcAmount.setPrecision(2);		
        this.txtBcAmount.setEnabled(false);
        // txtattachment		
        this.txtattachment.setVisible(true);		
        this.txtattachment.setDataType(0);		
        this.txtattachment.setSupportedEmpty(true);		
        this.txtattachment.setRequired(false);		
        this.txtattachment.setEnabled(true);		
        this.txtattachment.setPrecision(0);
        // txtOrg		
        this.txtOrg.setEditable(false);
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
        // cbPeriod		
        this.cbPeriod.setEnabled(false);
        // txtcompletePrjAmt		
        this.txtcompletePrjAmt.setDataType(1);		
        this.txtcompletePrjAmt.setEnabled(false);
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
        // kDScrollPane1		
        this.kDScrollPane1.setAutoscrolls(true);
        // txtMoneyDesc		
        this.txtMoneyDesc.setToolTipText(resHelper.getString("txtMoneyDesc.toolTipText"));		
        this.txtMoneyDesc.setText(resHelper.getString("txtMoneyDesc.text"));		
        this.txtMoneyDesc.setMaxLength(1000);		
        this.txtMoneyDesc.setWrapStyleWord(true);		
        this.txtMoneyDesc.setLineWrap(true);
        // kDScrollPane2
        // txtNoPaidReason		
        this.txtNoPaidReason.setEnabled(false);
        // prmtAccount		
        this.prmtAccount.setEnabled(false);		
        this.prmtAccount.setDisplayFormat("$name$");		
        this.prmtAccount.setEditFormat("$number$");		
        this.prmtAccount.setCommitFormat("$number$");		
        this.prmtAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
        this.prmtAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtAccount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtAccount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtContractType		
        this.prmtContractType.setCommitFormat("$number$");		
        this.prmtContractType.setDisplayFormat("$name$");		
        this.prmtContractType.setEditFormat("$number$");		
        this.prmtContractType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ContractTypeQuery");		
        this.prmtContractType.setRequired(true);		
        this.prmtContractType.setDefaultF7UIName("com.kingdee.eas.fdc.basedata.client.ContractTypeF7UI");
        this.prmtContractType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtContractType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtConCharge		
        this.prmtConCharge.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ContractChargeTypeQuery");		
        this.prmtConCharge.setDisplayFormat("$name$");		
        this.prmtConCharge.setEditFormat("$number$");		
        this.prmtConCharge.setCommitFormat("$number$");
        // txtInvoiceNumber		
        this.txtInvoiceNumber.setRequired(true);
        // pkInvoiceDate
        // txtInvoiceAmt		
        this.txtInvoiceAmt.setSupportedEmpty(true);		
        this.txtInvoiceAmt.setEnabled(false);
        // txtAllInvoiceAmt		
        this.txtAllInvoiceAmt.setSupportedEmpty(true);		
        this.txtAllInvoiceAmt.setEnabled(false);		
        this.txtAllInvoiceAmt.setDataType(1);
        // cmbAttachment
        // prmtPlanProject
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
        // prmtPayBillType		
        this.prmtPayBillType.setQueryInfo("com.kingdee.eas.fi.cas.F7PaymentBillTypeQuery");		
        this.prmtPayBillType.setCommitFormat("$number$");		
        this.prmtPayBillType.setEditFormat("$number$");		
        this.prmtPayBillType.setDisplayFormat("$number$ $name$");		
        this.prmtPayBillType.setVisible(false);
        // txtPCName		
        this.txtPCName.setEnabled(false);
        // prmtPayContentType		
        this.prmtPayContentType.setDisplayFormat("$name$");		
        this.prmtPayContentType.setEditFormat("$longNumber$");		
        this.prmtPayContentType.setCommitFormat("$longNumber$");		
        this.prmtPayContentType.setQueryInfo("com.kingdee.eas.fdc.contract.app.PayContentTypeQuery");		
        this.prmtPayContentType.setRequired(true);
        // comboPayeeType
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
        // btnViewBgBalance
        this.btnViewBgBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBgBalance.setText(resHelper.getString("btnViewBgBalance.text"));
        // btnViewProgramContract
        this.btnViewProgramContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewProgramContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewProgramContract.setText(resHelper.getString("btnViewProgramContract.text"));
        // btnProgram
        this.btnProgram.setAction((IItemAction)ActionProxyFactory.getProxy(actionProgram, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProgram.setText(resHelper.getString("btnProgram.text"));
        // menuItemViewBgBalance
        this.menuItemViewBgBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewBgBalance.setText(resHelper.getString("menuItemViewBgBalance.text"));		
        this.menuItemViewBgBalance.setToolTipText(resHelper.getString("menuItemViewBgBalance.toolTipText"));
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
        this.setBounds(new Rectangle(0, 0, 1013, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 600));
        kDSeparator6.setBounds(new Rectangle(11, 419, 995, 8));
        this.add(kDSeparator6, new KDLayout.Constraints(11, 419, 995, 8, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator5.setBounds(new Rectangle(11, 59, 991, 10));
        this.add(kDSeparator5, new KDLayout.Constraints(11, 59, 991, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(15, 552, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(15, 552, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(371, 552, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(371, 552, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(15, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(15, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(15, 574, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(15, 574, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcurProject.setBounds(new Rectangle(371, 32, 270, 19));
        this.add(contcurProject, new KDLayout.Constraints(371, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbillName.setBounds(new Rectangle(371, 10, 270, 19));
        this.add(contbillName, new KDLayout.Constraints(371, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contamount.setBounds(new Rectangle(371, 150, 270, 19));
        this.add(contamount, new KDLayout.Constraints(371, 150, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpayDate.setBounds(new Rectangle(730, 69, 270, 19));
        this.add(contpayDate, new KDLayout.Constraints(730, 69, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contreceiveUnit.setBounds(new Rectangle(371, 91, 270, 19));
        this.add(contreceiveUnit, new KDLayout.Constraints(371, 91, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbank.setBounds(new Rectangle(730, 91, 270, 19));
        this.add(contbank, new KDLayout.Constraints(730, 91, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contbankAcct.setBounds(new Rectangle(730, 113, 270, 19));
        this.add(contbankAcct, new KDLayout.Constraints(730, 113, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contsettlementType.setBounds(new Rectangle(730, 150, 270, 19));
        this.add(contsettlementType, new KDLayout.Constraints(730, 150, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contauditTime.setBounds(new Rectangle(371, 574, 270, 19));
        this.add(contauditTime, new KDLayout.Constraints(371, 574, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(15, 113, 270, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(15, 113, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(730, 255, 270, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(730, 255, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer7.setBounds(new Rectangle(371, 113, 270, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(371, 113, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(15, 150, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(15, 150, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(851, 320, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(851, 320, 270, 19, 0));
        kDLabelContainer9.setBounds(new Rectangle(850, 367, 270, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(850, 367, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(15, 172, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(15, 172, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer10.setBounds(new Rectangle(730, 172, 270, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(730, 172, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer11.setBounds(new Rectangle(371, 172, 270, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(371, 172, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkCostsplit.setBounds(new Rectangle(371, 454, 121, 19));
        this.add(chkCostsplit, new KDLayout.Constraints(371, 454, 121, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer12.setBounds(new Rectangle(852, 352, 270, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(852, 352, 270, 19, 0));
        chkUrgency.setBounds(new Rectangle(15, 454, 58, 19));
        this.add(chkUrgency, new KDLayout.Constraints(15, 454, 58, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrg.setBounds(new Rectangle(15, 32, 270, 19));
        this.add(contOrg, new KDLayout.Constraints(15, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(15, 69, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(15, 69, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer13.setBounds(new Rectangle(15, 91, 270, 19));
        this.add(kDLabelContainer13, new KDLayout.Constraints(15, 91, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkNeedPaid.setBounds(new Rectangle(184, 454, 75, 19));
        this.add(chkNeedPaid, new KDLayout.Constraints(184, 454, 75, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer8.setBounds(new Rectangle(852, 334, 270, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(852, 334, 270, 19, 0));
        kDLabelContainer14.setBounds(new Rectangle(15, 477, 490, 66));
        this.add(kDLabelContainer14, new KDLayout.Constraints(15, 477, 490, 66, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer15.setBounds(new Rectangle(510, 477, 490, 66));
        this.add(kDLabelContainer15, new KDLayout.Constraints(510, 477, 490, 66, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer16.setBounds(new Rectangle(849, 424, 469, 19));
        this.add(kDLabelContainer16, new KDLayout.Constraints(849, 424, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer17.setBounds(new Rectangle(730, 32, 270, 19));
        this.add(kDLabelContainer17, new KDLayout.Constraints(730, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conConCharge.setBounds(new Rectangle(852, 400, 469, 19));
        this.add(conConCharge, new KDLayout.Constraints(852, 400, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInvoiceNumber.setBounds(new Rectangle(15, 217, 270, 19));
        this.add(contInvoiceNumber, new KDLayout.Constraints(15, 217, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoiceDate.setBounds(new Rectangle(730, 217, 270, 19));
        this.add(contInvoiceDate, new KDLayout.Constraints(730, 217, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInvoiceAmt.setBounds(new Rectangle(371, 217, 270, 19));
        this.add(contInvoiceAmt, new KDLayout.Constraints(371, 217, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAllInvoiceAmt.setBounds(new Rectangle(854, 371, 270, 19));
        this.add(contAllInvoiceAmt, new KDLayout.Constraints(854, 371, 270, 19, 0));
        lblAttachmentContainer.setBounds(new Rectangle(856, 375, 378, 19));
        this.add(lblAttachmentContainer, new KDLayout.Constraints(856, 375, 378, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewAttachment.setBounds(new Rectangle(931, 343, 86, 21));
        this.add(btnViewAttachment, new KDLayout.Constraints(931, 343, 86, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPlanProject.setBounds(new Rectangle(846, 285, 270, 19));
        this.add(contPlanProject, new KDLayout.Constraints(846, 285, 270, 19, 0));
        btnViewBudget.setBounds(new Rectangle(919, 313, 93, 21));
        this.add(btnViewBudget, new KDLayout.Constraints(919, 313, 93, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator8.setBounds(new Rectangle(11, 141, 991, 10));
        this.add(kDSeparator8, new KDLayout.Constraints(11, 141, 991, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator9.setBounds(new Rectangle(11, 245, 995, 10));
        this.add(kDSeparator9, new KDLayout.Constraints(11, 245, 995, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contApplierOrgUnit.setBounds(new Rectangle(291, 249, 270, 19));
        this.add(contApplierOrgUnit, new KDLayout.Constraints(291, 249, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cbIsBgControl.setBounds(new Rectangle(15, 255, 140, 19));
        this.add(cbIsBgControl, new KDLayout.Constraints(15, 255, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contApplierCompany.setBounds(new Rectangle(371, 255, 270, 19));
        this.add(contApplierCompany, new KDLayout.Constraints(371, 255, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostedCompany.setBounds(new Rectangle(371, 277, 270, 19));
        this.add(contCostedCompany, new KDLayout.Constraints(371, 277, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostedDept.setBounds(new Rectangle(730, 277, 270, 19));
        this.add(contCostedDept, new KDLayout.Constraints(730, 277, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contApplier.setBounds(new Rectangle(15, 277, 270, 19));
        this.add(contApplier, new KDLayout.Constraints(15, 277, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBgEntry.setBounds(new Rectangle(15, 300, 625, 110));
        this.add(contBgEntry, new KDLayout.Constraints(15, 300, 625, 110, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayBillType.setBounds(new Rectangle(878, 39, 270, 19));
        this.add(contPayBillType, new KDLayout.Constraints(878, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        cbIsInvoice.setBounds(new Rectangle(15, 195, 140, 19));
        this.add(cbIsInvoice, new KDLayout.Constraints(15, 195, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conContrarctRule.setBounds(new Rectangle(730, 10, 270, 19));
        this.add(conContrarctRule, new KDLayout.Constraints(730, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPayContentType.setBounds(new Rectangle(371, 195, 270, 19));
        this.add(contPayContentType, new KDLayout.Constraints(371, 195, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayeeType.setBounds(new Rectangle(371, 69, 270, 19));
        this.add(contPayeeType, new KDLayout.Constraints(371, 69, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtPayPlanValue.setBounds(new Rectangle(15, 430, 625, 19));
        this.add(txtPayPlanValue, new KDLayout.Constraints(15, 430, 625, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAttachment.setBounds(new Rectangle(648, 300, 352, 110));
        this.add(contAttachment, new KDLayout.Constraints(648, 300, 352, 110, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contcurProject
        contcurProject.setBoundEditor(prmtcurProject);
        //contbillName
        contbillName.setBoundEditor(txtbillName);
        //contamount
        contamount.setBoundEditor(txtamount);
        //contpayDate
        contpayDate.setBoundEditor(pksignDate);
        //contreceiveUnit
        contreceiveUnit.setBoundEditor(prmtreceiveUnit);
        //contbank
        contbank.setBoundEditor(txtBank);
        //contbankAcct
        contbankAcct.setBoundEditor(txtBankAcct);
        //contsettlementType
        contsettlementType.setBoundEditor(prmtsettlementType);
        //contauditTime
        contauditTime.setBoundEditor(pkauditTime);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(prmtPayment);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(prmtuseDepartment);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(prmtrealSupplier);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prmtcurrency);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtPaymentProportion);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtPaymentRequestBillNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtexchangeRate);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(txtcapitalAmount);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(txtBcAmount);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtattachment);
        //contOrg
        contOrg.setBoundEditor(txtOrg);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(pkbookedDate);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(cbPeriod);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtcompletePrjAmt);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtMoneyDesc, null);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtNoPaidReason, null);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(prmtAccount);
        //kDLabelContainer17
        kDLabelContainer17.setBoundEditor(prmtContractType);
        //conConCharge
        conConCharge.setBoundEditor(prmtConCharge);
        //contInvoiceNumber
        contInvoiceNumber.setBoundEditor(txtInvoiceNumber);
        //contInvoiceDate
        contInvoiceDate.setBoundEditor(pkInvoiceDate);
        //contInvoiceAmt
        contInvoiceAmt.setBoundEditor(txtInvoiceAmt);
        //contAllInvoiceAmt
        contAllInvoiceAmt.setBoundEditor(txtAllInvoiceAmt);
        //lblAttachmentContainer
        lblAttachmentContainer.setBoundEditor(cmbAttachment);
        //contPlanProject
        contPlanProject.setBoundEditor(prmtPlanProject);
        //contApplierOrgUnit
        contApplierOrgUnit.setBoundEditor(prmtApplierOrgUnit);
        //contApplierCompany
        contApplierCompany.setBoundEditor(prmtApplierCompany);
        //contCostedCompany
        contCostedCompany.setBoundEditor(prmtCostedCompany);
        //contCostedDept
        contCostedDept.setBoundEditor(prmtCostedDept);
        //contApplier
        contApplier.setBoundEditor(prmtApplier);
        //contBgEntry
contBgEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contBgEntry.getContentPane().add(kdtBgEntry, BorderLayout.CENTER);
        //contPayBillType
        contPayBillType.setBoundEditor(prmtPayBillType);
        //conContrarctRule
        conContrarctRule.setBoundEditor(txtPCName);
        //contPayContentType
        contPayContentType.setBoundEditor(prmtPayContentType);
        //contPayeeType
        contPayeeType.setBoundEditor(comboPayeeType);
        //contAttachment
contAttachment.getContentPane().setLayout(new BorderLayout(0, 0));        contAttachment.getContentPane().add(tblAttachement, BorderLayout.CENTER);

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
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
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
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        menuEdit.add(menuItemEnterToNextRow);
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
        menuView.add(menuItemViewBgBalance);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
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
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnViewBgBalance);
        this.toolBar.add(btnViewProgramContract);
        this.toolBar.add(btnProgram);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isCostSplit", boolean.class, this.chkCostsplit, "selected");
		dataBinder.registerBinding("isNeedPaid", boolean.class, this.chkNeedPaid, "selected");
		dataBinder.registerBinding("isBgControl", boolean.class, this.cbIsBgControl, "selected");
		dataBinder.registerBinding("isInvoice", boolean.class, this.cbIsInvoice, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtcurProject, "data");
		dataBinder.registerBinding("name", String.class, this.txtbillName, "text");
		dataBinder.registerBinding("originalAmount", java.math.BigDecimal.class, this.txtamount, "value");
		dataBinder.registerBinding("signDate", java.util.Date.class, this.pksignDate, "value");
		dataBinder.registerBinding("bank", String.class, this.txtBank, "text");
		dataBinder.registerBinding("bankAcct", String.class, this.txtBankAcct, "text");
		dataBinder.registerBinding("settlementType", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.prmtsettlementType, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkauditTime, "value");
		dataBinder.registerBinding("useDepartment", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtuseDepartment, "data");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtcurrency, "data");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtBcAmount, "value");
		dataBinder.registerBinding("bookedDate", java.util.Date.class, this.pkbookedDate, "value");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.cbPeriod, "data");
		dataBinder.registerBinding("noPaidReason", String.class, this.txtNoPaidReason, "text");
		dataBinder.registerBinding("account", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtAccount, "data");
		dataBinder.registerBinding("contractType", com.kingdee.eas.fdc.basedata.ContractTypeInfo.class, this.prmtContractType, "data");
		dataBinder.registerBinding("conChargeType", com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo.class, this.prmtConCharge, "data");
		dataBinder.registerBinding("invoiceNumber", String.class, this.txtInvoiceNumber, "text");
		dataBinder.registerBinding("invoiceDate", java.util.Date.class, this.pkInvoiceDate, "value");
		dataBinder.registerBinding("invoiceAmt", java.math.BigDecimal.class, this.txtInvoiceAmt, "value");
		dataBinder.registerBinding("allInvoiceAmt", double.class, this.txtAllInvoiceAmt, "value");
		dataBinder.registerBinding("fdcDepConPlan", com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo.class, this.prmtPlanProject, "data");
		dataBinder.registerBinding("applierOrgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtApplierOrgUnit, "data");
		dataBinder.registerBinding("applierCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtApplierCompany, "data");
		dataBinder.registerBinding("costedCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtCostedCompany, "data");
		dataBinder.registerBinding("costedDept", com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo.class, this.prmtCostedDept, "data");
		dataBinder.registerBinding("applier", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtApplier, "data");
		dataBinder.registerBinding("payBillType", com.kingdee.eas.fi.cas.PaymentBillTypeInfo.class, this.prmtPayBillType, "data");
		dataBinder.registerBinding("payContentType", com.kingdee.eas.fdc.contract.PayContentTypeInfo.class, this.prmtPayContentType, "data");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionEdit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionUnAudit, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractWithoutTextEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"CostCenter",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("CostCenter");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("CostCenter");
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
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("isCostSplit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isNeedPaid", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isBgControl", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isInvoice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("signDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankAcct", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("useDepartment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("noPaidReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("account", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conChargeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("allInvoiceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fdcDepConPlan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applierOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applierCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payBillType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payContentType", ValidateHelper.ON_SAVE);    		
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
		            this.actionEdit.setEnabled(false);
		            this.actionAudit.setVisible(false);
		            this.actionAudit.setEnabled(false);
		            this.actionUnAudit.setVisible(false);
		            this.actionUnAudit.setEnabled(false);
        }
    }

    /**
     * output chkCostsplit_actionPerformed method
     */
    protected void chkCostsplit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkNeedPaid_actionPerformed method
     */
    protected void chkNeedPaid_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output cbIsBgControl_itemStateChanged method
     */
    protected void cbIsBgControl_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output cbIsInvoice_itemStateChanged method
     */
    protected void cbIsInvoice_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output txtNumber_focusLost method
     */
    protected void txtNumber_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output prmtcurProject_dataChanged method
     */
    protected void prmtcurProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtamount_dataChanged method
     */
    protected void txtamount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtamount_focusLost method
     */
    protected void txtamount_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output prmtreceiveUnit_dataChanged method
     */
    protected void prmtreceiveUnit_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtsettlementType_dataChanged method
     */
    protected void prmtsettlementType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkauditTime_dataChanged method
     */
    protected void pkauditTime_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtrealSupplier_dataChanged method
     */
    protected void prmtrealSupplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtcurrency_dataChanged method
     */
    protected void prmtcurrency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtpaymentProportion_dataChanged method
     */
    protected void txtpaymentProportion_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
     * output pkbookedDate_dataChanged method
     */
    protected void pkbookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtcompletePrjAmt_dataChanged method
     */
    protected void txtcompletePrjAmt_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtAccount_willCommit method
     */
    protected void prmtAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtAccount_willShow method
     */
    protected void prmtAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtAccount_dataChanged method
     */
    protected void prmtAccount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtContractType_dataChanged method
     */
    protected void prmtContractType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
     * output prmtCostedDept_dataChanged method
     */
    protected void prmtCostedDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtApplier_dataChanged method
     */
    protected void prmtApplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
     * output comboPayeeType_itemStateChanged method
     */
    protected void comboPayeeType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("isCostSplit"));
        sic.add(new SelectorItemInfo("isNeedPaid"));
        sic.add(new SelectorItemInfo("isBgControl"));
        sic.add(new SelectorItemInfo("isInvoice"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("curProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("curProject.id"));
        	sic.add(new SelectorItemInfo("curProject.number"));
        	sic.add(new SelectorItemInfo("curProject.name"));
        	sic.add(new SelectorItemInfo("curProject.displayname"));
		}
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("signDate"));
        sic.add(new SelectorItemInfo("bank"));
        sic.add(new SelectorItemInfo("bankAcct"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("settlementType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("settlementType.id"));
        	sic.add(new SelectorItemInfo("settlementType.number"));
        	sic.add(new SelectorItemInfo("settlementType.name"));
		}
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
			sic.add(new SelectorItemInfo("currency.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("currency.id"));
        	sic.add(new SelectorItemInfo("currency.number"));
        	sic.add(new SelectorItemInfo("currency.name"));
		}
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("bookedDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("period.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("period.id"));
        	sic.add(new SelectorItemInfo("period.number"));
		}
        sic.add(new SelectorItemInfo("noPaidReason"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("account.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("account.id"));
        	sic.add(new SelectorItemInfo("account.number"));
        	sic.add(new SelectorItemInfo("account.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractType.id"));
        	sic.add(new SelectorItemInfo("contractType.number"));
        	sic.add(new SelectorItemInfo("contractType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("conChargeType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("conChargeType.id"));
        	sic.add(new SelectorItemInfo("conChargeType.number"));
        	sic.add(new SelectorItemInfo("conChargeType.name"));
		}
        sic.add(new SelectorItemInfo("invoiceNumber"));
        sic.add(new SelectorItemInfo("invoiceDate"));
        sic.add(new SelectorItemInfo("invoiceAmt"));
        sic.add(new SelectorItemInfo("allInvoiceAmt"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("fdcDepConPlan.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("fdcDepConPlan.id"));
		}
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
			sic.add(new SelectorItemInfo("costedDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("costedDept.id"));
        	sic.add(new SelectorItemInfo("costedDept.number"));
        	sic.add(new SelectorItemInfo("costedDept.name"));
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
			sic.add(new SelectorItemInfo("payBillType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("payBillType.id"));
        	sic.add(new SelectorItemInfo("payBillType.number"));
        	sic.add(new SelectorItemInfo("payBillType.name"));
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
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionDelVoucher_actionPerformed method
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
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
     * output actionViewBgBalance_actionPerformed method
     */
    public void actionViewBgBalance_actionPerformed(ActionEvent e) throws Exception
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
    	

    /**
     * output actionProgram_actionPerformed method
     */
    public void actionProgram_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewProgramContract_actionPerformed method
     */
    public void actionViewProgramContract_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionDelVoucher(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionDelVoucher(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelVoucher() {
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
	public RequestContext prepareActionProgram(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProgram() {
    	return false;
    }
	public RequestContext prepareActionViewProgramContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewProgramContract() {
    	return false;
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
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionViewBgBalance", "actionViewBgBalance_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionViewAttachment", "actionViewAttachment_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "actionViewBudget", "actionViewBudget_actionPerformed", e);
        }
    }

    /**
     * output ActionProgram class
     */     
    protected class ActionProgram extends ItemAction {     
    
        public ActionProgram()
        {
            this(null);
        }

        public ActionProgram(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionProgram.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgram.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgram.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionProgram", "actionProgram_actionPerformed", e);
        }
    }

    /**
     * output ActionViewProgramContract class
     */     
    protected class ActionViewProgramContract extends ItemAction {     
    
        public ActionViewProgramContract()
        {
            this(null);
        }

        public ActionViewProgramContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewProgramContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewProgramContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewProgramContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionViewProgramContract", "actionViewProgramContract_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractWithoutTextEditUI");
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
        return com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.ContractWithoutTextFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.ContractWithoutTextInfo objectValue = new com.kingdee.eas.fdc.contract.ContractWithoutTextInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/contract/ContractWithoutText";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractWithoutTextQuery");
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