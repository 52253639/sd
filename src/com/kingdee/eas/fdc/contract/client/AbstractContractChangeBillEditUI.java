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
public abstract class AbstractContractChangeBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractChangeBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConductDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeSubject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUrgentDegree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contJobType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSpecialtyType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsDeduct;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDeductAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBudgetAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalanceAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMainSupp;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDeductReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGraphCount;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelBase;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelRegister;
    protected com.kingdee.bos.ctrl.swing.KDLabel lableVisa;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNouse;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBookedDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCbPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOffer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConstrUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVisaType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConstrSite;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConductUnit;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tbpContractChange;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalanceType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblAttachmentContainer;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsSureChangeAmt;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsImportChange;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConstructPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewMainSupp;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsChangeContract;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbIsChangeContractYes;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbIsChangeContractNo;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbIsReceiptsNo;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbIsReceiptsYes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsReceipts;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsBigChange;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbIsBigChangeYes;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbIsBigChangeNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReaDesc;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup2;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsFee;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractName;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboState;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtChangeType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtConductDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtChangeReason;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractBill;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtChangeSubject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboUrgentDegree;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtJobType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSpecialtyType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDeductAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBudgetAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBalanceAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMainSupp;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDeductReason;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboGraphCount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAuditNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtValidReason;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNouse;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbookedDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox cbPeriod;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBudgetOriAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtExRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOriBalanceAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboOffer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtConstrUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtVisaType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConstrSite;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtConductUnit;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlEntrys;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlExecute;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnEntrys;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnExecute;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblVisa;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contThisTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCondition;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtThisTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCondition;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBalanceType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbAttachment;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOriginalContactNum;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtConstructPrice;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtNewMainSupp;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCostUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea txtReaDesc;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbIsFee;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewChangeAuditAttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisPatch;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnVisa;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAuditAttachment;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDisPatch;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemVisa;
    protected com.kingdee.eas.fdc.contract.ContractChangeBillInfo editData = null;
    protected ActionDisPatch actionDisPatch = null;
    protected ActionSplit actionSplit = null;
    protected ActionVisa actionVisa = null;
    protected ActionViewChangeAudtiAttachment actionViewChangeAudtiAttachment = null;
    protected ActionViewAttachment actionViewAttachment = null;
    /**
     * output class constructor
     */
    public AbstractContractChangeBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractChangeBillEditUI.class.getName());
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
        //actionAudit
        actionAudit.setEnabled(false);
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
        //actionDisPatch
        this.actionDisPatch = new ActionDisPatch(this);
        getActionManager().registerAction("actionDisPatch", actionDisPatch);
         this.actionDisPatch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplit
        this.actionSplit = new ActionSplit(this);
        getActionManager().registerAction("actionSplit", actionSplit);
         this.actionSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionVisa
        this.actionVisa = new ActionVisa(this);
        getActionManager().registerAction("actionVisa", actionVisa);
         this.actionVisa.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewChangeAudtiAttachment
        this.actionViewChangeAudtiAttachment = new ActionViewChangeAudtiAttachment(this);
        getActionManager().registerAction("actionViewChangeAudtiAttachment", actionViewChangeAudtiAttachment);
         this.actionViewChangeAudtiAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAttachment
        this.actionViewAttachment = new ActionViewAttachment(this);
        getActionManager().registerAction("actionViewAttachment", actionViewAttachment);
         this.actionViewAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConductDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeSubject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUrgentDegree = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contJobType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSpecialtyType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsDeduct = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contDeductAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBudgetAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBalanceAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMainSupp = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDeductReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGraphCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labelBase = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.labelRegister = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lableVisa = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contAuditNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNouse = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contBookedDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCbPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOffer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConstrUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVisaType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConstrSite = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConductUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tbpContractChange = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contBalanceType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblAttachmentContainer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewAttachment = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsSureChangeAmt = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsImportChange = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contConstructPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewMainSupp = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIsChangeContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.rbIsChangeContractYes = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbIsChangeContractNo = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbIsReceiptsNo = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbIsReceiptsYes = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.contIsReceipts = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIsBigChange = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.rbIsBigChangeYes = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbIsBigChangeNo = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.contCostUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReaDesc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDButtonGroup2 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDButtonGroup3 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.contIsFee = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtChangeType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtConductDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtChangeReason = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtContractBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtChangeSubject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboUrgentDegree = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtJobType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSpecialtyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDeductAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBudgetAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBalanceAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtMainSupp = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDeductReason = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboGraphCount = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtAuditNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtValidReason = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNouse = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkbookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBudgetOriAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtExRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOriBalanceAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboOffer = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtConstrUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtVisaType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtConstrSite = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtConductUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pnlEntrys = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlExecute = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.ctnEntrys = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.ctnExecute = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblVisa = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contThisTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCondition = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtThisTime = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCondition = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBalanceType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.cmbAttachment = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtOriginalContactNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtConstructPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtNewMainSupp = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCostUnit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtReaDesc = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.cbIsFee = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnViewChangeAuditAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDisPatch = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnVisa = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAuditAttachment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDisPatch = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemVisa = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contAuditor.setName("contAuditor");
        this.contName.setName("contName");
        this.contContractName.setName("contContractName");
        this.contState.setName("contState");
        this.contAuditTime.setName("contAuditTime");
        this.contChangeType.setName("contChangeType");
        this.contConductDept.setName("contConductDept");
        this.contChangeReason.setName("contChangeReason");
        this.contContractBill.setName("contContractBill");
        this.contChangeSubject.setName("contChangeSubject");
        this.contUrgentDegree.setName("contUrgentDegree");
        this.contCurProject.setName("contCurProject");
        this.contJobType.setName("contJobType");
        this.contSpecialtyType.setName("contSpecialtyType");
        this.chkIsDeduct.setName("chkIsDeduct");
        this.contDeductAmount.setName("contDeductAmount");
        this.contBudgetAmount.setName("contBudgetAmount");
        this.contBalanceAmount.setName("contBalanceAmount");
        this.contMainSupp.setName("contMainSupp");
        this.contDeductReason.setName("contDeductReason");
        this.contGraphCount.setName("contGraphCount");
        this.labelBase.setName("labelBase");
        this.labelRegister.setName("labelRegister");
        this.lableVisa.setName("lableVisa");
        this.contAuditNumber.setName("contAuditNumber");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contNouse.setName("contNouse");
        this.kDSeparator8.setName("kDSeparator8");
        this.contBookedDate.setName("contBookedDate");
        this.contCbPeriod.setName("contCbPeriod");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.contOffer.setName("contOffer");
        this.contConstrUnit.setName("contConstrUnit");
        this.contVisaType.setName("contVisaType");
        this.contConstrSite.setName("contConstrSite");
        this.contConductUnit.setName("contConductUnit");
        this.tbpContractChange.setName("tbpContractChange");
        this.contBalanceType.setName("contBalanceType");
        this.lblAttachmentContainer.setName("lblAttachmentContainer");
        this.btnViewAttachment.setName("btnViewAttachment");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.chkIsSureChangeAmt.setName("chkIsSureChangeAmt");
        this.chkIsImportChange.setName("chkIsImportChange");
        this.contConstructPrice.setName("contConstructPrice");
        this.contNewMainSupp.setName("contNewMainSupp");
        this.contOrg.setName("contOrg");
        this.contIsChangeContract.setName("contIsChangeContract");
        this.rbIsChangeContractYes.setName("rbIsChangeContractYes");
        this.rbIsChangeContractNo.setName("rbIsChangeContractNo");
        this.rbIsReceiptsNo.setName("rbIsReceiptsNo");
        this.rbIsReceiptsYes.setName("rbIsReceiptsYes");
        this.contIsReceipts.setName("contIsReceipts");
        this.contIsBigChange.setName("contIsBigChange");
        this.rbIsBigChangeYes.setName("rbIsBigChangeYes");
        this.rbIsBigChangeNo.setName("rbIsBigChangeNo");
        this.contCostUnit.setName("contCostUnit");
        this.contReaDesc.setName("contReaDesc");
        this.contIsFee.setName("contIsFee");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateDate.setName("pkCreateDate");
        this.txtNumber.setName("txtNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtName.setName("txtName");
        this.txtContractName.setName("txtContractName");
        this.comboState.setName("comboState");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtChangeType.setName("prmtChangeType");
        this.prmtConductDept.setName("prmtConductDept");
        this.prmtChangeReason.setName("prmtChangeReason");
        this.prmtContractBill.setName("prmtContractBill");
        this.txtChangeSubject.setName("txtChangeSubject");
        this.comboUrgentDegree.setName("comboUrgentDegree");
        this.prmtCurProject.setName("prmtCurProject");
        this.prmtJobType.setName("prmtJobType");
        this.prmtSpecialtyType.setName("prmtSpecialtyType");
        this.txtDeductAmount.setName("txtDeductAmount");
        this.txtBudgetAmount.setName("txtBudgetAmount");
        this.txtBalanceAmount.setName("txtBalanceAmount");
        this.prmtMainSupp.setName("prmtMainSupp");
        this.txtDeductReason.setName("txtDeductReason");
        this.comboGraphCount.setName("comboGraphCount");
        this.txtAuditNumber.setName("txtAuditNumber");
        this.prmtValidReason.setName("prmtValidReason");
        this.txtNouse.setName("txtNouse");
        this.pkbookedDate.setName("pkbookedDate");
        this.cbPeriod.setName("cbPeriod");
        this.prmtCurrency.setName("prmtCurrency");
        this.txtBudgetOriAmount.setName("txtBudgetOriAmount");
        this.txtExRate.setName("txtExRate");
        this.txtOriBalanceAmount.setName("txtOriBalanceAmount");
        this.comboOffer.setName("comboOffer");
        this.prmtConstrUnit.setName("prmtConstrUnit");
        this.prmtVisaType.setName("prmtVisaType");
        this.txtConstrSite.setName("txtConstrSite");
        this.prmtConductUnit.setName("prmtConductUnit");
        this.pnlEntrys.setName("pnlEntrys");
        this.pnlExecute.setName("pnlExecute");
        this.ctnEntrys.setName("ctnEntrys");
        this.kdtEntrys.setName("kdtEntrys");
        this.ctnExecute.setName("ctnExecute");
        this.tblVisa.setName("tblVisa");
        this.contThisTime.setName("contThisTime");
        this.contCondition.setName("contCondition");
        this.txtThisTime.setName("txtThisTime");
        this.txtCondition.setName("txtCondition");
        this.txtBalanceType.setName("txtBalanceType");
        this.cmbAttachment.setName("cmbAttachment");
        this.txtOriginalContactNum.setName("txtOriginalContactNum");
        this.txtConstructPrice.setName("txtConstructPrice");
        this.prmtNewMainSupp.setName("prmtNewMainSupp");
        this.txtOrg.setName("txtOrg");
        this.txtCostUnit.setName("txtCostUnit");
        this.txtReaDesc.setName("txtReaDesc");
        this.cbIsFee.setName("cbIsFee");
        this.btnViewChangeAuditAttachment.setName("btnViewChangeAuditAttachment");
        this.btnDisPatch.setName("btnDisPatch");
        this.btnSplit.setName("btnSplit");
        this.btnVisa.setName("btnVisa");
        this.menuItemAuditAttachment.setName("menuItemAuditAttachment");
        this.menuItemDisPatch.setName("menuItemDisPatch");
        this.menuItemSplit.setName("menuItemSplit");
        this.menuItemVisa.setName("menuItemVisa");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnAddNew.setEnabled(false);		
        this.btnAddNew.setVisible(false);		
        this.btnSubmit.setEnabled(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setEnabled(false);		
        this.btnRemove.setVisible(false);		
        this.menuItemAddNew.setEnabled(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemSubmit.setEnabled(false);		
        this.menuItemSubmit.setVisible(false);		
        this.menuItemCopy.setEnabled(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuItemRemove.setEnabled(false);		
        this.menuItemRemove.setVisible(false);		
        this.menuSubmitOption.setEnabled(false);		
        this.menuSubmitOption.setVisible(false);		
        this.chkMenuItemSubmitAndAddNew.setEnabled(false);		
        this.chkMenuItemSubmitAndAddNew.setVisible(false);		
        this.chkMenuItemSubmitAndPrint.setEnabled(false);		
        this.chkMenuItemSubmitAndPrint.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnWorkFlowG.setEnabled(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnCreateTo.setEnabled(false);		
        this.btnAddLine.setEnabled(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setEnabled(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnVoucher.setEnabled(false);		
        this.btnDelVoucher.setEnabled(false);		
        this.menuItemCreateFrom.setEnabled(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCreateTo.setEnabled(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemCopyFrom.setEnabled(false);		
        this.menuItemTraceUp.setEnabled(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setEnabled(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.MenuItemVoucher.setEnabled(false);		
        this.menuItemDelVoucher.setEnabled(false);		
        this.menuTable1.setEnabled(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setEnabled(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemInsertLine.setEnabled(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setEnabled(false);		
        this.menuItemRemoveLine.setVisible(false);		
        this.menuWorkflow.setEnabled(false);		
        this.menuWorkflow.setVisible(false);		
        this.menuItemStartWorkFlow.setEnabled(false);		
        this.MenuItemWFG.setEnabled(false);		
        this.MenuItemWFG.setVisible(false);		
        this.menuItemMultiapprove.setEnabled(false);		
        this.menuItemMultiapprove.setVisible(false);		
        this.menuItemNextPerson.setEnabled(false);		
        this.menuItemNextPerson.setVisible(false);		
        this.menuItemAuditResult.setEnabled(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.kDSeparator5.setToolTipText(resHelper.getString("kDSeparator5.toolTipText"));		
        this.kDMenuItemSendMessage.setEnabled(false);		
        this.kDMenuItemSendMessage.setVisible(false);		
        this.btnAudit.setEnabled(false);		
        this.btnAudit.setVisible(false);		
        this.btnUnAudit.setEnabled(false);		
        this.btnUnAudit.setVisible(false);		
        this.menuItemAudit.setEnabled(false);		
        this.menuItemAudit.setVisible(false);		
        this.menuItemUnAudit.setEnabled(false);		
        this.menuItemUnAudit.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(80);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(80);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(80);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contContractName		
        this.contContractName.setBoundLabelText(resHelper.getString("contContractName.boundLabelText"));		
        this.contContractName.setBoundLabelLength(100);		
        this.contContractName.setBoundLabelUnderline(true);
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);		
        this.contState.setVisible(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(80);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contChangeType		
        this.contChangeType.setBoundLabelText(resHelper.getString("contChangeType.boundLabelText"));		
        this.contChangeType.setBoundLabelLength(100);		
        this.contChangeType.setBoundLabelUnderline(true);
        // contConductDept		
        this.contConductDept.setBoundLabelText(resHelper.getString("contConductDept.boundLabelText"));		
        this.contConductDept.setBoundLabelLength(100);		
        this.contConductDept.setBoundLabelUnderline(true);
        // contChangeReason		
        this.contChangeReason.setBoundLabelText(resHelper.getString("contChangeReason.boundLabelText"));		
        this.contChangeReason.setBoundLabelLength(100);		
        this.contChangeReason.setBoundLabelUnderline(true);
        // contContractBill		
        this.contContractBill.setBoundLabelText(resHelper.getString("contContractBill.boundLabelText"));		
        this.contContractBill.setBoundLabelLength(100);		
        this.contContractBill.setBoundLabelUnderline(true);
        // contChangeSubject		
        this.contChangeSubject.setBoundLabelText(resHelper.getString("contChangeSubject.boundLabelText"));		
        this.contChangeSubject.setBoundLabelLength(100);		
        this.contChangeSubject.setBoundLabelUnderline(true);		
        this.contChangeSubject.setVisible(false);
        // contUrgentDegree		
        this.contUrgentDegree.setBoundLabelText(resHelper.getString("contUrgentDegree.boundLabelText"));		
        this.contUrgentDegree.setBoundLabelLength(100);		
        this.contUrgentDegree.setBoundLabelUnderline(true);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);
        // contJobType		
        this.contJobType.setBoundLabelText(resHelper.getString("contJobType.boundLabelText"));		
        this.contJobType.setBoundLabelLength(100);		
        this.contJobType.setBoundLabelUnderline(true);
        // contSpecialtyType		
        this.contSpecialtyType.setBoundLabelText(resHelper.getString("contSpecialtyType.boundLabelText"));		
        this.contSpecialtyType.setBoundLabelLength(100);		
        this.contSpecialtyType.setBoundLabelUnderline(true);
        // chkIsDeduct		
        this.chkIsDeduct.setText(resHelper.getString("chkIsDeduct.text"));		
        this.chkIsDeduct.setEnabled(false);		
        this.chkIsDeduct.setVisible(false);
        // contDeductAmount		
        this.contDeductAmount.setBoundLabelText(resHelper.getString("contDeductAmount.boundLabelText"));		
        this.contDeductAmount.setBoundLabelLength(100);		
        this.contDeductAmount.setBoundLabelUnderline(true);		
        this.contDeductAmount.setVisible(false);
        // contBudgetAmount		
        this.contBudgetAmount.setBoundLabelText(resHelper.getString("contBudgetAmount.boundLabelText"));		
        this.contBudgetAmount.setBoundLabelLength(100);		
        this.contBudgetAmount.setBoundLabelUnderline(true);
        // contBalanceAmount		
        this.contBalanceAmount.setBoundLabelText(resHelper.getString("contBalanceAmount.boundLabelText"));		
        this.contBalanceAmount.setBoundLabelLength(100);		
        this.contBalanceAmount.setBoundLabelUnderline(true);
        // contMainSupp		
        this.contMainSupp.setBoundLabelText(resHelper.getString("contMainSupp.boundLabelText"));		
        this.contMainSupp.setBoundLabelLength(100);		
        this.contMainSupp.setBoundLabelUnderline(true);		
        this.contMainSupp.setEnabled(false);
        // contDeductReason		
        this.contDeductReason.setBoundLabelText(resHelper.getString("contDeductReason.boundLabelText"));		
        this.contDeductReason.setBoundLabelLength(100);		
        this.contDeductReason.setBoundLabelUnderline(true);		
        this.contDeductReason.setVisible(false);
        // contGraphCount		
        this.contGraphCount.setBoundLabelText(resHelper.getString("contGraphCount.boundLabelText"));		
        this.contGraphCount.setBoundLabelLength(100);		
        this.contGraphCount.setBoundLabelUnderline(true);		
        this.contGraphCount.setVisible(false);
        // labelBase		
        this.labelBase.setText(resHelper.getString("labelBase.text"));
        // labelRegister		
        this.labelRegister.setText(resHelper.getString("labelRegister.text"));
        // lableVisa		
        this.lableVisa.setText(resHelper.getString("lableVisa.text"));
        // contAuditNumber		
        this.contAuditNumber.setBoundLabelText(resHelper.getString("contAuditNumber.boundLabelText"));		
        this.contAuditNumber.setBoundLabelLength(100);		
        this.contAuditNumber.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setVisible(false);
        // contNouse		
        this.contNouse.setBoundLabelText(resHelper.getString("contNouse.boundLabelText"));		
        this.contNouse.setBoundLabelLength(100);		
        this.contNouse.setBoundLabelUnderline(true);		
        this.contNouse.setEnabled(false);		
        this.contNouse.setVisible(false);
        // kDSeparator8
        // contBookedDate		
        this.contBookedDate.setBoundLabelText(resHelper.getString("contBookedDate.boundLabelText"));		
        this.contBookedDate.setBoundLabelUnderline(true);		
        this.contBookedDate.setBoundLabelLength(100);
        // contCbPeriod		
        this.contCbPeriod.setBoundLabelText(resHelper.getString("contCbPeriod.boundLabelText"));		
        this.contCbPeriod.setBoundLabelLength(100);		
        this.contCbPeriod.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // contOffer		
        this.contOffer.setBoundLabelText(resHelper.getString("contOffer.boundLabelText"));		
        this.contOffer.setBoundLabelLength(100);		
        this.contOffer.setBoundLabelUnderline(true);
        // contConstrUnit		
        this.contConstrUnit.setBoundLabelText(resHelper.getString("contConstrUnit.boundLabelText"));		
        this.contConstrUnit.setBoundLabelLength(100);		
        this.contConstrUnit.setBoundLabelUnderline(true);		
        this.contConstrUnit.setVisible(false);
        // contVisaType		
        this.contVisaType.setBoundLabelText(resHelper.getString("contVisaType.boundLabelText"));		
        this.contVisaType.setBoundLabelLength(100);		
        this.contVisaType.setBoundLabelUnderline(true);		
        this.contVisaType.setVisible(false);
        // contConstrSite		
        this.contConstrSite.setBoundLabelText(resHelper.getString("contConstrSite.boundLabelText"));		
        this.contConstrSite.setBoundLabelLength(100);		
        this.contConstrSite.setBoundLabelUnderline(true);		
        this.contConstrSite.setVisible(false);
        // contConductUnit		
        this.contConductUnit.setBoundLabelText(resHelper.getString("contConductUnit.boundLabelText"));		
        this.contConductUnit.setBoundLabelLength(100);		
        this.contConductUnit.setBoundLabelUnderline(true);		
        this.contConductUnit.setVisible(false);
        // tbpContractChange
        // contBalanceType		
        this.contBalanceType.setBoundLabelText(resHelper.getString("contBalanceType.boundLabelText"));		
        this.contBalanceType.setBoundLabelLength(100);		
        this.contBalanceType.setBoundLabelUnderline(true);		
        this.contBalanceType.setVisible(false);
        // lblAttachmentContainer		
        this.lblAttachmentContainer.setBoundLabelText(resHelper.getString("lblAttachmentContainer.boundLabelText"));		
        this.lblAttachmentContainer.setBoundLabelLength(100);		
        this.lblAttachmentContainer.setBoundLabelUnderline(true);
        // btnViewAttachment
        this.btnViewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAttachment.setText(resHelper.getString("btnViewAttachment.text"));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setEnabled(false);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setVisible(false);
        // chkIsSureChangeAmt		
        this.chkIsSureChangeAmt.setText(resHelper.getString("chkIsSureChangeAmt.text"));		
        this.chkIsSureChangeAmt.setVisible(false);
        // chkIsImportChange		
        this.chkIsImportChange.setText(resHelper.getString("chkIsImportChange.text"));		
        this.chkIsImportChange.setVisible(false);
        // contConstructPrice		
        this.contConstructPrice.setBoundLabelText(resHelper.getString("contConstructPrice.boundLabelText"));		
        this.contConstructPrice.setBoundLabelLength(100);		
        this.contConstructPrice.setBoundLabelUnderline(true);		
        this.contConstructPrice.setVisible(false);
        // contNewMainSupp		
        this.contNewMainSupp.setBoundLabelText(resHelper.getString("contNewMainSupp.boundLabelText"));		
        this.contNewMainSupp.setBoundLabelLength(100);		
        this.contNewMainSupp.setBoundLabelUnderline(true);		
        this.contNewMainSupp.setVisible(false);
        // contOrg		
        this.contOrg.setBoundLabelText(resHelper.getString("contOrg.boundLabelText"));		
        this.contOrg.setBoundLabelLength(100);		
        this.contOrg.setBoundLabelUnderline(true);
        // contIsChangeContract		
        this.contIsChangeContract.setBoundLabelText(resHelper.getString("contIsChangeContract.boundLabelText"));		
        this.contIsChangeContract.setBoundLabelLength(100);		
        this.contIsChangeContract.setBoundLabelUnderline(true);
        // rbIsChangeContractYes		
        this.rbIsChangeContractYes.setText(resHelper.getString("rbIsChangeContractYes.text"));
        // rbIsChangeContractNo		
        this.rbIsChangeContractNo.setText(resHelper.getString("rbIsChangeContractNo.text"));
        // rbIsReceiptsNo		
        this.rbIsReceiptsNo.setText(resHelper.getString("rbIsReceiptsNo.text"));
        // rbIsReceiptsYes		
        this.rbIsReceiptsYes.setText(resHelper.getString("rbIsReceiptsYes.text"));
        // contIsReceipts		
        this.contIsReceipts.setBoundLabelText(resHelper.getString("contIsReceipts.boundLabelText"));		
        this.contIsReceipts.setBoundLabelLength(100);		
        this.contIsReceipts.setBoundLabelUnderline(true);
        // contIsBigChange		
        this.contIsBigChange.setBoundLabelText(resHelper.getString("contIsBigChange.boundLabelText"));		
        this.contIsBigChange.setBoundLabelLength(100);		
        this.contIsBigChange.setBoundLabelUnderline(true);
        // rbIsBigChangeYes		
        this.rbIsBigChangeYes.setText(resHelper.getString("rbIsBigChangeYes.text"));
        // rbIsBigChangeNo		
        this.rbIsBigChangeNo.setText(resHelper.getString("rbIsBigChangeNo.text"));
        // contCostUnit		
        this.contCostUnit.setBoundLabelText(resHelper.getString("contCostUnit.boundLabelText"));		
        this.contCostUnit.setBoundLabelLength(100);		
        this.contCostUnit.setBoundLabelUnderline(true);
        // contReaDesc		
        this.contReaDesc.setBoundLabelText(resHelper.getString("contReaDesc.boundLabelText"));		
        this.contReaDesc.setBoundLabelLength(100);		
        this.contReaDesc.setBoundLabelUnderline(true);
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.rbIsChangeContractYes);
        this.kDButtonGroup1.add(this.rbIsChangeContractNo);
        // kDButtonGroup2
        this.kDButtonGroup2.add(this.rbIsReceiptsNo);
        this.kDButtonGroup2.add(this.rbIsReceiptsYes);
        // kDButtonGroup3
        this.kDButtonGroup3.add(this.rbIsBigChangeYes);
        this.kDButtonGroup3.add(this.rbIsBigChangeNo);
        // contIsFee		
        this.contIsFee.setBoundLabelText(resHelper.getString("contIsFee.boundLabelText"));		
        this.contIsFee.setBoundLabelLength(100);		
        this.contIsFee.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$name$");
        // pkCreateDate		
        this.pkCreateDate.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setEnabled(false);
        // txtContractName		
        this.txtContractName.setEnabled(false);		
        this.txtContractName.setRequired(true);
        // comboState		
        this.comboState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.comboState.setEnabled(false);		
        this.comboState.setRequired(true);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // prmtChangeType		
        this.prmtChangeType.setDisplayFormat("$name$");		
        this.prmtChangeType.setEditFormat("$number$");		
        this.prmtChangeType.setCommitFormat("$number$");		
        this.prmtChangeType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ChangeTypeQuery");		
        this.prmtChangeType.setRequired(true);		
        this.prmtChangeType.setEditable(true);
        this.prmtChangeType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtChangeType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtConductDept		
        this.prmtConductDept.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.prmtConductDept.setCommitFormat("$number$");		
        this.prmtConductDept.setDisplayFormat("$name$");		
        this.prmtConductDept.setEditFormat("$number$");		
        this.prmtConductDept.setRequired(true);		
        this.prmtConductDept.setEditable(true);
        // prmtChangeReason		
        this.prmtChangeReason.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ChangeReasonQuery");		
        this.prmtChangeReason.setCommitFormat("$number$");		
        this.prmtChangeReason.setDisplayFormat("$name$");		
        this.prmtChangeReason.setEditFormat("$number$");		
        this.prmtChangeReason.setRequired(true);		
        this.prmtChangeReason.setEditable(true);
        // prmtContractBill		
        this.prmtContractBill.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBillQuery");		
        this.prmtContractBill.setCommitFormat("$number$");		
        this.prmtContractBill.setDisplayFormat("$number$");		
        this.prmtContractBill.setEditFormat("$number$");		
        this.prmtContractBill.setRequired(true);		
        this.prmtContractBill.setEditable(true);
        this.prmtContractBill.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtContractBill_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtContractBill.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtContractBill_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtChangeSubject		
        this.txtChangeSubject.setMaxLength(80);
        // comboUrgentDegree		
        this.comboUrgentDegree.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum").toArray());		
        this.comboUrgentDegree.setRequired(true);
        // prmtCurProject		
        this.prmtCurProject.setDisplayFormat("$name$");		
        this.prmtCurProject.setEditFormat("$number$");		
        this.prmtCurProject.setCommitFormat("$number$");		
        this.prmtCurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtCurProject.setRequired(true);		
        this.prmtCurProject.setEnabled(false);		
        this.prmtCurProject.setEditable(true);
        // prmtJobType		
        this.prmtJobType.setDisplayFormat("$name$");		
        this.prmtJobType.setEditFormat("$number$");		
        this.prmtJobType.setCommitFormat("$number$");		
        this.prmtJobType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7JobTypeQuery");		
        this.prmtJobType.setRequired(true);		
        this.prmtJobType.setEditable(true);
        // prmtSpecialtyType		
        this.prmtSpecialtyType.setDisplayFormat("$name$");		
        this.prmtSpecialtyType.setEditFormat("$number$");		
        this.prmtSpecialtyType.setCommitFormat("$number$");		
        this.prmtSpecialtyType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7SpecialtyTypeQuery");		
        this.prmtSpecialtyType.setRequired(true);		
        this.prmtSpecialtyType.setEditable(true);		
        this.prmtSpecialtyType.setEnabledMultiSelection(true);
        this.prmtSpecialtyType.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtSpecialtyType_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtDeductAmount		
        this.txtDeductAmount.setDataType(1);		
        this.txtDeductAmount.setPrecision(2);		
        this.txtDeductAmount.setSupportedEmpty(true);		
        this.txtDeductAmount.setEnabled(false);
        // txtBudgetAmount		
        this.txtBudgetAmount.setDataType(1);		
        this.txtBudgetAmount.setPrecision(2);		
        this.txtBudgetAmount.setSupportedEmpty(true);
        this.txtBudgetAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                try {
                    txtBudgetAmount_focusGained(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtBalanceAmount		
        this.txtBalanceAmount.setDataType(1);		
        this.txtBalanceAmount.setPrecision(2);		
        this.txtBalanceAmount.setSupportedEmpty(true);		
        this.txtBalanceAmount.setEditable(false);
        // prmtMainSupp		
        this.prmtMainSupp.setDisplayFormat("$name$");		
        this.prmtMainSupp.setEditFormat("$number$");		
        this.prmtMainSupp.setCommitFormat("$number$");		
        this.prmtMainSupp.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtMainSupp.setRequired(true);		
        this.prmtMainSupp.setEditable(true);		
        this.prmtMainSupp.setEnabled(false);
        // txtDeductReason		
        this.txtDeductReason.setMaxLength(80);		
        this.txtDeductReason.setEnabled(false);
        // comboGraphCount		
        this.comboGraphCount.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.GraphCountEnum").toArray());		
        this.comboGraphCount.setRequired(true);
        // txtAuditNumber		
        this.txtAuditNumber.setEnabled(false);
        // prmtValidReason		
        this.prmtValidReason.setEnabled(false);		
        this.prmtValidReason.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7InvalidCostReasonQuery");
        // txtNouse		
        this.txtNouse.setDataType(1);		
        this.txtNouse.setPrecision(2);		
        this.txtNouse.setEnabled(false);
        // pkbookedDate		
        this.pkbookedDate.setEnabled(false);
        // cbPeriod		
        this.cbPeriod.setEnabled(false);
        // prmtCurrency		
        this.prmtCurrency.setEnabled(false);
        // txtBudgetOriAmount		
        this.txtBudgetOriAmount.setDataType(1);
        this.txtBudgetOriAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtBudgetOriAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtExRate		
        this.txtExRate.setDataType(1);
        this.txtExRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtExRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtOriBalanceAmount		
        this.txtOriBalanceAmount.setDataType(1);		
        this.txtOriBalanceAmount.setEditable(false);
        // comboOffer		
        this.comboOffer.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.OfferEnum").toArray());
        this.comboOffer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboOffer_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtConstrUnit		
        this.prmtConstrUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");		
        this.prmtConstrUnit.setEditFormat("$number$");		
        this.prmtConstrUnit.setCommitFormat("$number$");		
        this.prmtConstrUnit.setDisplayFormat("$number$ $name$");
        // prmtVisaType		
        this.prmtVisaType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7VisaTypeQuery");		
        this.prmtVisaType.setCommitFormat("$number$");		
        this.prmtVisaType.setEditFormat("$number$");		
        this.prmtVisaType.setDisplayFormat("$name$");
        // txtConstrSite
        // prmtConductUnit		
        this.prmtConductUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");		
        this.prmtConductUnit.setEditFormat("$number$");		
        this.prmtConductUnit.setDisplayFormat("$number$ $name$");		
        this.prmtConductUnit.setCommitFormat("$number$");
        // pnlEntrys
        // pnlExecute
        // ctnEntrys		
        this.ctnEntrys.setTitle(resHelper.getString("ctnEntrys.title"));		
        this.ctnEntrys.setEnableActive(false);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"changeContent\" t:width=\"726\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"isBack\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"effect\" t:width=\"255\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{changeContent}</t:Cell><t:Cell>$Resource{isBack}</t:Cell><t:Cell>$Resource{effect}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));		
        this.kdtEntrys.setMinimumSize(new Dimension(10,10));		
        this.kdtEntrys.setPreferredSize(new Dimension(1013,629));

                this.kdtEntrys.putBindContents("editData",new String[] {"number","changeContent","isBack","effect"});


        // ctnExecute		
        this.ctnExecute.setTitle(resHelper.getString("ctnExecute.title"));		
        this.ctnExecute.setEnableActive(false);
        // tblVisa
		String tblVisaStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"isAllExe\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"isPartExe\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"isNoExe\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"discription\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{isAllExe}</t:Cell><t:Cell>$Resource{isPartExe}</t:Cell><t:Cell>$Resource{isNoExe}</t:Cell><t:Cell>$Resource{discription}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblVisa.setFormatXml(resHelper.translateString("tblVisa",tblVisaStrXML));

                this.tblVisa.putBindContents("editData",new String[] {"","","isAllExe","isPartExe","isNoExe",""});


        // contThisTime		
        this.contThisTime.setBoundLabelText(resHelper.getString("contThisTime.boundLabelText"));		
        this.contThisTime.setBoundLabelLength(100);		
        this.contThisTime.setBoundLabelUnderline(true);
        // contCondition		
        this.contCondition.setBoundLabelText(resHelper.getString("contCondition.boundLabelText"));		
        this.contCondition.setBoundLabelLength(100);		
        this.contCondition.setBoundLabelUnderline(true);
        // txtThisTime		
        this.txtThisTime.setMaxLength(80);
        // txtCondition		
        this.txtCondition.setMaxLength(80);
        // txtBalanceType		
        this.txtBalanceType.setMaxLength(80);		
        this.txtBalanceType.setEnabled(false);
        // cmbAttachment
        // txtOriginalContactNum		
        this.txtOriginalContactNum.setEnabled(false);
        // txtConstructPrice		
        this.txtConstructPrice.setDataType(1);
        // prmtNewMainSupp		
        this.prmtNewMainSupp.setDisplayFormat("$name$");		
        this.prmtNewMainSupp.setEditFormat("$number$");		
        this.prmtNewMainSupp.setCommitFormat("$number$");		
        this.prmtNewMainSupp.setEnabledMultiSelection(true);
        // txtOrg		
        this.txtOrg.setMaxLength(80);		
        this.txtOrg.setRequired(true);		
        this.txtOrg.setEditable(false);
        // txtCostUnit
        // txtReaDesc
        // cbIsFee		
        this.cbIsFee.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.app.YesOrNoEnum").toArray());		
        this.cbIsFee.setRequired(true);
        // btnViewChangeAuditAttachment
        this.btnViewChangeAuditAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewChangeAudtiAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewChangeAuditAttachment.setText(resHelper.getString("btnViewChangeAuditAttachment.text"));
        // btnDisPatch
        this.btnDisPatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisPatch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisPatch.setText(resHelper.getString("btnDisPatch.text"));		
        this.btnDisPatch.setToolTipText(resHelper.getString("btnDisPatch.toolTipText"));
        // btnSplit
        this.btnSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplit.setText(resHelper.getString("btnSplit.text"));		
        this.btnSplit.setToolTipText(resHelper.getString("btnSplit.toolTipText"));
        // btnVisa
        this.btnVisa.setAction((IItemAction)ActionProxyFactory.getProxy(actionVisa, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnVisa.setText(resHelper.getString("btnVisa.text"));		
        this.btnVisa.setToolTipText(resHelper.getString("btnVisa.toolTipText"));		
        this.btnVisa.setVisible(false);
        // menuItemAuditAttachment
        this.menuItemAuditAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewChangeAudtiAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAuditAttachment.setText(resHelper.getString("menuItemAuditAttachment.text"));
        // menuItemDisPatch
        this.menuItemDisPatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisPatch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDisPatch.setText(resHelper.getString("menuItemDisPatch.text"));
        // menuItemSplit
        this.menuItemSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplit.setText(resHelper.getString("menuItemSplit.text"));
        // menuItemVisa
        this.menuItemVisa.setAction((IItemAction)ActionProxyFactory.getProxy(actionVisa, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemVisa.setText(resHelper.getString("menuItemVisa.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtNumber,txtName,prmtCurProject,prmtChangeReason,prmtChangeType,prmtJobType,comboUrgentDegree,prmtSpecialtyType,cbPeriod,prmtConductDept,txtChangeSubject,comboGraphCount,prmtConductUnit,comboOffer,prmtConstrUnit,txtConstrSite,prmtVisaType,kdtEntrys,prmtContractBill,chkIsDeduct,txtDeductAmount,txtDeductReason,txtBudgetOriAmount,prmtValidReason}));
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
        this.setBounds(new Rectangle(10, 10, 1013, 690));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 690));
        kDSeparator6.setBounds(new Rectangle(57, 507, 943, 8));
        this.add(kDSeparator6, new KDLayout.Constraints(57, 507, 943, 8, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator7.setBounds(new Rectangle(57, 237, 943, 8));
        this.add(kDSeparator7, new KDLayout.Constraints(57, 237, 943, 8, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator5.setBounds(new Rectangle(60, 13, 943, 8));
        this.add(kDSeparator5, new KDLayout.Constraints(60, 13, 943, 8, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(17, 638, 220, 19));
        this.add(contCreator, new KDLayout.Constraints(17, 638, 220, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(257, 638, 220, 19));
        this.add(contCreateTime, new KDLayout.Constraints(257, 638, 220, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(20, 44, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(20, 44, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(527, 638, 220, 19));
        this.add(contAuditor, new KDLayout.Constraints(527, 638, 220, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(369, 44, 270, 19));
        this.add(contName, new KDLayout.Constraints(369, 44, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractName.setBounds(new Rectangle(369, 520, 270, 19));
        this.add(contContractName, new KDLayout.Constraints(369, 520, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contState.setBounds(new Rectangle(704, 215, 270, 19));
        this.add(contState, new KDLayout.Constraints(704, 215, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(767, 638, 220, 19));
        this.add(contAuditTime, new KDLayout.Constraints(767, 638, 220, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contChangeType.setBounds(new Rectangle(369, 66, 270, 19));
        this.add(contChangeType, new KDLayout.Constraints(369, 66, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contConductDept.setBounds(new Rectangle(369, 110, 270, 19));
        this.add(contConductDept, new KDLayout.Constraints(369, 110, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChangeReason.setBounds(new Rectangle(721, 66, 270, 19));
        this.add(contChangeReason, new KDLayout.Constraints(721, 66, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contContractBill.setBounds(new Rectangle(20, 520, 270, 19));
        this.add(contContractBill, new KDLayout.Constraints(20, 520, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChangeSubject.setBounds(new Rectangle(734, 182, 270, 19));
        this.add(contChangeSubject, new KDLayout.Constraints(734, 182, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contUrgentDegree.setBounds(new Rectangle(721, 44, 270, 19));
        this.add(contUrgentDegree, new KDLayout.Constraints(721, 44, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurProject.setBounds(new Rectangle(369, 22, 270, 19));
        this.add(contCurProject, new KDLayout.Constraints(369, 22, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contJobType.setBounds(new Rectangle(369, 88, 270, 19));
        this.add(contJobType, new KDLayout.Constraints(369, 88, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSpecialtyType.setBounds(new Rectangle(721, 88, 270, 19));
        this.add(contSpecialtyType, new KDLayout.Constraints(721, 88, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsDeduct.setBounds(new Rectangle(7, 706, 270, 19));
        this.add(chkIsDeduct, new KDLayout.Constraints(7, 706, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDeductAmount.setBounds(new Rectangle(-9, 562, 270, 19));
        this.add(contDeductAmount, new KDLayout.Constraints(-9, 562, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBudgetAmount.setBounds(new Rectangle(721, 564, 270, 19));
        this.add(contBudgetAmount, new KDLayout.Constraints(721, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBalanceAmount.setBounds(new Rectangle(721, 586, 270, 19));
        this.add(contBalanceAmount, new KDLayout.Constraints(721, 586, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contMainSupp.setBounds(new Rectangle(721, 520, 270, 19));
        this.add(contMainSupp, new KDLayout.Constraints(721, 520, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDeductReason.setBounds(new Rectangle(352, 620, 270, 19));
        this.add(contDeductReason, new KDLayout.Constraints(352, 620, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contGraphCount.setBounds(new Rectangle(782, 197, 270, 19));
        this.add(contGraphCount, new KDLayout.Constraints(782, 197, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labelBase.setBounds(new Rectangle(10, 4, 50, 19));
        this.add(labelBase, new KDLayout.Constraints(10, 4, 50, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        labelRegister.setBounds(new Rectangle(7, 497, 50, 19));
        this.add(labelRegister, new KDLayout.Constraints(7, 497, 50, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        lableVisa.setBounds(new Rectangle(7, 229, 50, 19));
        this.add(lableVisa, new KDLayout.Constraints(7, 229, 50, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contAuditNumber.setBounds(new Rectangle(20, 542, 270, 19));
        this.add(contAuditNumber, new KDLayout.Constraints(20, 542, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(369, 641, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(369, 641, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNouse.setBounds(new Rectangle(721, 616, 270, 19));
        this.add(contNouse, new KDLayout.Constraints(721, 616, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator8.setBounds(new Rectangle(7, 621, 996, 8));
        this.add(kDSeparator8, new KDLayout.Constraints(7, 621, 996, 8, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBookedDate.setBounds(new Rectangle(20, 66, 270, 19));
        this.add(contBookedDate, new KDLayout.Constraints(20, 66, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCbPeriod.setBounds(new Rectangle(20, 88, 270, 19));
        this.add(contCbPeriod, new KDLayout.Constraints(20, 88, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(369, 542, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(369, 542, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(369, 564, 270, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(369, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(721, 542, 270, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(721, 542, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer8.setBounds(new Rectangle(369, 586, 270, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(369, 586, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOffer.setBounds(new Rectangle(20, 110, 270, 19));
        this.add(contOffer, new KDLayout.Constraints(20, 110, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contConstrUnit.setBounds(new Rectangle(799, 167, 270, 19));
        this.add(contConstrUnit, new KDLayout.Constraints(799, 167, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contVisaType.setBounds(new Rectangle(863, 130, 270, 19));
        this.add(contVisaType, new KDLayout.Constraints(863, 130, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contConstrSite.setBounds(new Rectangle(905, 143, 270, 19));
        this.add(contConstrSite, new KDLayout.Constraints(905, 143, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contConductUnit.setBounds(new Rectangle(721, 110, 270, 19));
        this.add(contConductUnit, new KDLayout.Constraints(721, 110, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        tbpContractChange.setBounds(new Rectangle(20, 249, 966, 239));
        this.add(tbpContractChange, new KDLayout.Constraints(20, 249, 966, 239, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBalanceType.setBounds(new Rectangle(142, 708, 153, 19));
        this.add(contBalanceType, new KDLayout.Constraints(142, 708, 153, 19, 0));
        lblAttachmentContainer.setBounds(new Rectangle(20, 202, 413, 19));
        this.add(lblAttachmentContainer, new KDLayout.Constraints(20, 202, 413, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewAttachment.setBounds(new Rectangle(468, 202, 102, 21));
        this.add(btnViewAttachment, new KDLayout.Constraints(468, 202, 102, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(29, 587, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(29, 587, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkIsSureChangeAmt.setBounds(new Rectangle(28, 577, 140, 19));
        this.add(chkIsSureChangeAmt, new KDLayout.Constraints(28, 577, 140, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkIsImportChange.setBounds(new Rectangle(807, 143, 140, 19));
        this.add(chkIsImportChange, new KDLayout.Constraints(807, 143, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE));
        contConstructPrice.setBounds(new Rectangle(721, 641, 270, 19));
        this.add(contConstructPrice, new KDLayout.Constraints(721, 641, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNewMainSupp.setBounds(new Rectangle(92, 562, 270, 19));
        this.add(contNewMainSupp, new KDLayout.Constraints(92, 562, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrg.setBounds(new Rectangle(20, 22, 270, 19));
        this.add(contOrg, new KDLayout.Constraints(20, 22, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsChangeContract.setBounds(new Rectangle(20, 133, 101, 19));
        this.add(contIsChangeContract, new KDLayout.Constraints(20, 133, 101, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        rbIsChangeContractYes.setBounds(new Rectangle(124, 133, 51, 19));
        this.add(rbIsChangeContractYes, new KDLayout.Constraints(124, 133, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        rbIsChangeContractNo.setBounds(new Rectangle(205, 133, 51, 19));
        this.add(rbIsChangeContractNo, new KDLayout.Constraints(205, 133, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        rbIsReceiptsNo.setBounds(new Rectangle(205, 156, 51, 19));
        this.add(rbIsReceiptsNo, new KDLayout.Constraints(205, 156, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        rbIsReceiptsYes.setBounds(new Rectangle(124, 156, 51, 19));
        this.add(rbIsReceiptsYes, new KDLayout.Constraints(124, 156, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsReceipts.setBounds(new Rectangle(20, 156, 101, 19));
        this.add(contIsReceipts, new KDLayout.Constraints(20, 156, 101, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsBigChange.setBounds(new Rectangle(20, 179, 101, 19));
        this.add(contIsBigChange, new KDLayout.Constraints(20, 179, 101, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        rbIsBigChangeYes.setBounds(new Rectangle(124, 179, 51, 19));
        this.add(rbIsBigChangeYes, new KDLayout.Constraints(124, 179, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        rbIsBigChangeNo.setBounds(new Rectangle(205, 179, 51, 19));
        this.add(rbIsBigChangeNo, new KDLayout.Constraints(205, 179, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostUnit.setBounds(new Rectangle(369, 133, 625, 19));
        this.add(contCostUnit, new KDLayout.Constraints(369, 133, 625, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contReaDesc.setBounds(new Rectangle(369, 156, 625, 43));
        this.add(contReaDesc, new KDLayout.Constraints(369, 156, 625, 43, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contIsFee.setBounds(new Rectangle(721, 22, 270, 19));
        this.add(contIsFee, new KDLayout.Constraints(721, 22, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateDate);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contName
        contName.setBoundEditor(txtName);
        //contContractName
        contContractName.setBoundEditor(txtContractName);
        //contState
        contState.setBoundEditor(comboState);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contChangeType
        contChangeType.setBoundEditor(prmtChangeType);
        //contConductDept
        contConductDept.setBoundEditor(prmtConductDept);
        //contChangeReason
        contChangeReason.setBoundEditor(prmtChangeReason);
        //contContractBill
        contContractBill.setBoundEditor(prmtContractBill);
        //contChangeSubject
        contChangeSubject.setBoundEditor(txtChangeSubject);
        //contUrgentDegree
        contUrgentDegree.setBoundEditor(comboUrgentDegree);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurProject);
        //contJobType
        contJobType.setBoundEditor(prmtJobType);
        //contSpecialtyType
        contSpecialtyType.setBoundEditor(prmtSpecialtyType);
        //contDeductAmount
        contDeductAmount.setBoundEditor(txtDeductAmount);
        //contBudgetAmount
        contBudgetAmount.setBoundEditor(txtBudgetAmount);
        //contBalanceAmount
        contBalanceAmount.setBoundEditor(txtBalanceAmount);
        //contMainSupp
        contMainSupp.setBoundEditor(prmtMainSupp);
        //contDeductReason
        contDeductReason.setBoundEditor(txtDeductReason);
        //contGraphCount
        contGraphCount.setBoundEditor(comboGraphCount);
        //contAuditNumber
        contAuditNumber.setBoundEditor(txtAuditNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prmtValidReason);
        //contNouse
        contNouse.setBoundEditor(txtNouse);
        //contBookedDate
        contBookedDate.setBoundEditor(pkbookedDate);
        //contCbPeriod
        contCbPeriod.setBoundEditor(cbPeriod);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(prmtCurrency);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtBudgetOriAmount);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(txtExRate);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtOriBalanceAmount);
        //contOffer
        contOffer.setBoundEditor(comboOffer);
        //contConstrUnit
        contConstrUnit.setBoundEditor(prmtConstrUnit);
        //contVisaType
        contVisaType.setBoundEditor(prmtVisaType);
        //contConstrSite
        contConstrSite.setBoundEditor(txtConstrSite);
        //contConductUnit
        contConductUnit.setBoundEditor(prmtConductUnit);
        //tbpContractChange
        tbpContractChange.add(pnlEntrys, resHelper.getString("pnlEntrys.constraints"));
        tbpContractChange.add(pnlExecute, resHelper.getString("pnlExecute.constraints"));
        //pnlEntrys
pnlEntrys.setLayout(new BorderLayout(0, 0));        pnlEntrys.add(ctnEntrys, BorderLayout.CENTER);
        //ctnEntrys
ctnEntrys.getContentPane().setLayout(new BorderLayout(0, 0));        ctnEntrys.getContentPane().add(kdtEntrys, BorderLayout.CENTER);
        //pnlExecute
pnlExecute.setLayout(new BorderLayout(0, 0));        pnlExecute.add(ctnExecute, BorderLayout.CENTER);
        //ctnExecute
        ctnExecute.getContentPane().setLayout(new KDLayout());
        ctnExecute.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 965, 206));        tblVisa.setBounds(new Rectangle(0, 0, 945, 150));
        ctnExecute.getContentPane().add(tblVisa, new KDLayout.Constraints(0, 0, 945, 150, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contThisTime.setBounds(new Rectangle(0, 163, 446, 19));
        ctnExecute.getContentPane().add(contThisTime, new KDLayout.Constraints(0, 163, 446, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCondition.setBounds(new Rectangle(516, 163, 446, 19));
        ctnExecute.getContentPane().add(contCondition, new KDLayout.Constraints(516, 163, 446, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contThisTime
        contThisTime.setBoundEditor(txtThisTime);
        //contCondition
        contCondition.setBoundEditor(txtCondition);
        //contBalanceType
        contBalanceType.setBoundEditor(txtBalanceType);
        //lblAttachmentContainer
        lblAttachmentContainer.setBoundEditor(cmbAttachment);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtOriginalContactNum);
        //contConstructPrice
        contConstructPrice.setBoundEditor(txtConstructPrice);
        //contNewMainSupp
        contNewMainSupp.setBoundEditor(prmtNewMainSupp);
        //contOrg
        contOrg.setBoundEditor(txtOrg);
        //contCostUnit
        contCostUnit.setBoundEditor(txtCostUnit);
        //contReaDesc
        contReaDesc.setBoundEditor(txtReaDesc);
        //contIsFee
        contIsFee.setBoundEditor(cbIsFee);

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
        menuFile.add(menuItemAuditAttachment);
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
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemDisPatch);
        menuBiz.add(menuItemSplit);
        menuBiz.add(menuItemVisa);
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
        this.toolBar.add(btnViewChangeAuditAttachment);
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
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnDisPatch);
        this.toolBar.add(btnSplit);
        this.toolBar.add(btnVisa);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isDeduct", boolean.class, this.chkIsDeduct, "selected");
		dataBinder.registerBinding("isSureChangeAmt", boolean.class, this.chkIsSureChangeAmt, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateDate, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.comboState, "selectedItem");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("changeType", com.kingdee.eas.fdc.basedata.ChangeTypeInfo.class, this.prmtChangeType, "data");
		dataBinder.registerBinding("conductDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtConductDept, "data");
		dataBinder.registerBinding("changeReason", com.kingdee.eas.fdc.basedata.ChangeReasonInfo.class, this.prmtChangeReason, "data");
		dataBinder.registerBinding("contractBill", com.kingdee.eas.fdc.contract.ContractBillInfo.class, this.prmtContractBill, "data");
		dataBinder.registerBinding("changeSubject", String.class, this.txtChangeSubject, "text");
		dataBinder.registerBinding("urgentDegree", com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum.class, this.comboUrgentDegree, "selectedItem");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtCurProject, "data");
		dataBinder.registerBinding("jobType", com.kingdee.eas.fdc.basedata.JobTypeInfo.class, this.prmtJobType, "data");
		dataBinder.registerBinding("deductAmount", java.math.BigDecimal.class, this.txtDeductAmount, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtBudgetAmount, "value");
		dataBinder.registerBinding("balanceAmount", java.math.BigDecimal.class, this.txtBalanceAmount, "value");
		dataBinder.registerBinding("mainSupp", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtMainSupp, "data");
		dataBinder.registerBinding("deductReason", String.class, this.txtDeductReason, "text");
		dataBinder.registerBinding("graphCount", com.kingdee.eas.fdc.contract.GraphCountEnum.class, this.comboGraphCount, "selectedItem");
		dataBinder.registerBinding("changeAuditNumber", String.class, this.txtAuditNumber, "text");
		dataBinder.registerBinding("invalidCostReason", com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo.class, this.prmtValidReason, "data");
		dataBinder.registerBinding("costNouse", java.math.BigDecimal.class, this.txtNouse, "value");
		dataBinder.registerBinding("bookedDate", java.util.Date.class, this.pkbookedDate, "value");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.cbPeriod, "data");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtCurrency, "data");
		dataBinder.registerBinding("originalAmount", java.math.BigDecimal.class, this.txtBudgetOriAmount, "value");
		dataBinder.registerBinding("exRate", java.math.BigDecimal.class, this.txtExRate, "value");
		dataBinder.registerBinding("oriBalanceAmount", java.math.BigDecimal.class, this.txtOriBalanceAmount, "value");
		dataBinder.registerBinding("offer", com.kingdee.eas.fdc.contract.OfferEnum.class, this.comboOffer, "selectedItem");
		dataBinder.registerBinding("constrUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtConstrUnit, "data");
		dataBinder.registerBinding("visaType", com.kingdee.eas.fdc.basedata.VisaTypeInfo.class, this.prmtVisaType, "data");
		dataBinder.registerBinding("constrSite", String.class, this.txtConstrSite, "text");
		dataBinder.registerBinding("conductUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtConductUnit, "data");
		dataBinder.registerBinding("entrys.changeContent", String.class, this.kdtEntrys, "changeContent.text");
		dataBinder.registerBinding("entrys.isBack", boolean.class, this.kdtEntrys, "isBack.text");
		dataBinder.registerBinding("entrys.number", String.class, this.kdtEntrys, "number.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.contract.ContractChangeEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.effect", String.class, this.kdtEntrys, "effect.text");
		dataBinder.registerBinding("entrys.isAllExe", boolean.class, this.tblVisa, "isAllExe.text");
		dataBinder.registerBinding("entrys.isPartExe", boolean.class, this.tblVisa, "isPartExe.text");
		dataBinder.registerBinding("entrys.isNoExe", boolean.class, this.tblVisa, "isNoExe.text");
		dataBinder.registerBinding("disThisTime", String.class, this.txtThisTime, "text");
		dataBinder.registerBinding("impleCondition", String.class, this.txtCondition, "text");
		dataBinder.registerBinding("balanceType", String.class, this.txtBalanceType, "text");
		dataBinder.registerBinding("originalContactNum", String.class, this.txtOriginalContactNum, "text");
		dataBinder.registerBinding("constructPrice", java.math.BigDecimal.class, this.txtConstructPrice, "value");
		dataBinder.registerBinding("costUnit", String.class, this.txtCostUnit, "text");
		dataBinder.registerBinding("reaDesc", String.class, this.txtReaDesc, "_multiLangItem");
		dataBinder.registerBinding("isFee", com.kingdee.eas.fdc.contract.app.YesOrNoEnum.class, this.cbIsFee, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_VIEW, this.actionSplit, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionSave, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.kdtEntrys, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractChangeBillEditUIHandler";
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
        this.txtNumber.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.contract.ContractChangeBillInfo)ov;
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
		getValidateHelper().registerBindProperty("isDeduct", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isSureChangeAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conductDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeSubject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("urgentDegree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jobType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("deductAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("balanceAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mainSupp", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("deductReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("graphCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeAuditNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invalidCostReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costNouse", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oriBalanceAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("offer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("constrUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("visaType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("constrSite", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conductUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.changeContent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.isBack", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.effect", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.isAllExe", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.isPartExe", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.isNoExe", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("disThisTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("impleCondition", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("balanceType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalContactNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("constructPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reaDesc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isFee", ValidateHelper.ON_SAVE);    		
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
		            this.actionSplit.setEnabled(true);
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		            this.actionSave.setEnabled(false);
		            this.actionAudit.setVisible(false);
		            this.actionUnAudit.setVisible(false);
		            this.actionDisPatch.setVisible(false);
		            this.actionVisa.setVisible(false);
		            this.kdtEntrys.setEnabled(false);
        }
    }

    /**
     * output prmtChangeType_dataChanged method
     */
    protected void prmtChangeType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtContractBill_dataChanged method
     */
    protected void prmtContractBill_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtContractBill_willShow method
     */
    protected void prmtContractBill_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtSpecialtyType_willShow method
     */
    protected void prmtSpecialtyType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output txtBudgetAmount_focusGained method
     */
    protected void txtBudgetAmount_focusGained(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtBudgetOriAmount_dataChanged method
     */
    protected void txtBudgetOriAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtExRate_dataChanged method
     */
    protected void txtExRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboOffer_itemStateChanged method
     */
    protected void comboOffer_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("isDeduct"));
        sic.add(new SelectorItemInfo("isSureChangeAmt"));
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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("auditTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("changeType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("changeType.id"));
        	sic.add(new SelectorItemInfo("changeType.number"));
        	sic.add(new SelectorItemInfo("changeType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("conductDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("conductDept.id"));
        	sic.add(new SelectorItemInfo("conductDept.number"));
        	sic.add(new SelectorItemInfo("conductDept.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("changeReason.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("changeReason.id"));
        	sic.add(new SelectorItemInfo("changeReason.number"));
        	sic.add(new SelectorItemInfo("changeReason.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractBill.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractBill.id"));
        	sic.add(new SelectorItemInfo("contractBill.number"));
        	sic.add(new SelectorItemInfo("contractBill.name"));
		}
        sic.add(new SelectorItemInfo("changeSubject"));
        sic.add(new SelectorItemInfo("urgentDegree"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("curProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("curProject.id"));
        	sic.add(new SelectorItemInfo("curProject.number"));
        	sic.add(new SelectorItemInfo("curProject.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("jobType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("jobType.id"));
        	sic.add(new SelectorItemInfo("jobType.number"));
        	sic.add(new SelectorItemInfo("jobType.name"));
		}
        sic.add(new SelectorItemInfo("deductAmount"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("balanceAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("mainSupp.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("mainSupp.id"));
        	sic.add(new SelectorItemInfo("mainSupp.number"));
        	sic.add(new SelectorItemInfo("mainSupp.name"));
		}
        sic.add(new SelectorItemInfo("deductReason"));
        sic.add(new SelectorItemInfo("graphCount"));
        sic.add(new SelectorItemInfo("changeAuditNumber"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("invalidCostReason.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("invalidCostReason.id"));
        	sic.add(new SelectorItemInfo("invalidCostReason.number"));
        	sic.add(new SelectorItemInfo("invalidCostReason.name"));
		}
        sic.add(new SelectorItemInfo("costNouse"));
        sic.add(new SelectorItemInfo("bookedDate"));
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
			sic.add(new SelectorItemInfo("currency.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("currency.id"));
        	sic.add(new SelectorItemInfo("currency.number"));
        	sic.add(new SelectorItemInfo("currency.name"));
		}
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("exRate"));
        sic.add(new SelectorItemInfo("oriBalanceAmount"));
        sic.add(new SelectorItemInfo("offer"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("constrUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("constrUnit.id"));
        	sic.add(new SelectorItemInfo("constrUnit.number"));
        	sic.add(new SelectorItemInfo("constrUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("visaType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("visaType.id"));
        	sic.add(new SelectorItemInfo("visaType.number"));
        	sic.add(new SelectorItemInfo("visaType.name"));
		}
        sic.add(new SelectorItemInfo("constrSite"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("conductUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("conductUnit.id"));
        	sic.add(new SelectorItemInfo("conductUnit.number"));
        	sic.add(new SelectorItemInfo("conductUnit.name"));
		}
    	sic.add(new SelectorItemInfo("entrys.changeContent"));
    	sic.add(new SelectorItemInfo("entrys.isBack"));
    	sic.add(new SelectorItemInfo("entrys.number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entrys.effect"));
    	sic.add(new SelectorItemInfo("entrys.isAllExe"));
    	sic.add(new SelectorItemInfo("entrys.isPartExe"));
    	sic.add(new SelectorItemInfo("entrys.isNoExe"));
        sic.add(new SelectorItemInfo("disThisTime"));
        sic.add(new SelectorItemInfo("impleCondition"));
        sic.add(new SelectorItemInfo("balanceType"));
        sic.add(new SelectorItemInfo("originalContactNum"));
        sic.add(new SelectorItemInfo("constructPrice"));
        sic.add(new SelectorItemInfo("costUnit"));
        sic.add(new SelectorItemInfo("reaDesc"));
        sic.add(new SelectorItemInfo("isFee"));
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
     * output actionDisPatch_actionPerformed method
     */
    public void actionDisPatch_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplit_actionPerformed method
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionVisa_actionPerformed method
     */
    public void actionVisa_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewChangeAudtiAttachment_actionPerformed method
     */
    public void actionViewChangeAudtiAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewAttachment_actionPerformed method
     */
    public void actionViewAttachment_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionDisPatch(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDisPatch() {
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
	public RequestContext prepareActionVisa(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionVisa() {
    	return false;
    }
	public RequestContext prepareActionViewChangeAudtiAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewChangeAudtiAttachment() {
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

    /**
     * output ActionDisPatch class
     */     
    protected class ActionDisPatch extends ItemAction {     
    
        public ActionDisPatch()
        {
            this(null);
        }

        public ActionDisPatch(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDisPatch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisPatch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisPatch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractChangeBillEditUI.this, "ActionDisPatch", "actionDisPatch_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractContractChangeBillEditUI.this, "ActionSplit", "actionSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionVisa class
     */     
    protected class ActionVisa extends ItemAction {     
    
        public ActionVisa()
        {
            this(null);
        }

        public ActionVisa(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionVisa.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVisa.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVisa.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractChangeBillEditUI.this, "ActionVisa", "actionVisa_actionPerformed", e);
        }
    }

    /**
     * output ActionViewChangeAudtiAttachment class
     */     
    protected class ActionViewChangeAudtiAttachment extends ItemAction {     
    
        public ActionViewChangeAudtiAttachment()
        {
            this(null);
        }

        public ActionViewChangeAudtiAttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewChangeAudtiAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewChangeAudtiAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewChangeAudtiAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractChangeBillEditUI.this, "ActionViewChangeAudtiAttachment", "actionViewChangeAudtiAttachment_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractChangeBillEditUI.this, "ActionViewAttachment", "actionViewAttachment_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractChangeBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}