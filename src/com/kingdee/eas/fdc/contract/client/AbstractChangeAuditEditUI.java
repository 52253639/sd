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
public abstract class AbstractChangeAuditEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChangeAuditEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUrgentDegree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWFType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSpecialtyType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeSubject;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tbpChangAudit;
    protected com.kingdee.bos.ctrl.swing.KDContainer contAheadDisPatch;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConductDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOffer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReaDesc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConstrUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDesignUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConstrSite;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConductUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblAttachmentContainer;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsImportChange;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbIsChangeContractYes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsChangeContract;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbIsChangeContractNo;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbIsReceiptsYes;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbIsReceiptsNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsReceipts;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostUnit;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbIsBigChangeYes;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbIsBigChangeNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsBigChange;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup2;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsFee;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDesc;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboChangeState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboUrgentDegree;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtWFType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSpecialtyType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtChangeSubject;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlContent;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlSupp;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnEntrys;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGraphCount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboGraphCount;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnSuppEntrys;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSuppEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmoutA;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDutyAmout;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNoUse;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReason;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbxNoUse;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalCost;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmountA;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDutyAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNoUse;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bmptResaon;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAheadReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConnectType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contValidator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAheadReason;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConnectType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtValidator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrg;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtConductDept;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbookedDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox cbPeriod;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboOffer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea txtReaDesc;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtConstrUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDesignUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConstrSite;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtConductUnit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbAttachment;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCostUnit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbIsFee;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea txtDesc;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPassHistory;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRegister;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisPatch;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRegister;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDispatch;
    protected com.kingdee.eas.fdc.contract.ChangeAuditBillInfo editData = null;
    protected ActionAddSupp actionAddSupp = null;
    protected ActionDelSupp actionDelSupp = null;
    protected ActionRegister actionRegister = null;
    protected ActionDisPatch actionDisPatch = null;
    protected ActionAheadDisPatch actionAheadDisPatch = null;
    protected ActionViewAttachment actionViewAttachment = null;
    protected ActionPassHistory actionPassHistory = null;
    /**
     * output class constructor
     */
    public AbstractChangeAuditEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChangeAuditEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        String _tempStr = null;
        actionSave.setEnabled(true);
        actionSave.setDaemonRun(false);

        actionSave.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl S"));
        _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
        actionSave.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
        actionSave.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.NAME");
        actionSave.putValue(ItemAction.NAME, _tempStr);
        this.actionSave.setBindWorkFlow(true);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmit
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
        //actionAttachment
        actionAttachment.setEnabled(true);
        actionAttachment.setDaemonRun(false);

        actionAttachment.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift H"));
        _tempStr = resHelper.getString("ActionAttachment.SHORT_DESCRIPTION");
        actionAttachment.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.LONG_DESCRIPTION");
        actionAttachment.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.NAME");
        actionAttachment.putValue(ItemAction.NAME, _tempStr);
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
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
        //actionAddSupp
        this.actionAddSupp = new ActionAddSupp(this);
        getActionManager().registerAction("actionAddSupp", actionAddSupp);
         this.actionAddSupp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelSupp
        this.actionDelSupp = new ActionDelSupp(this);
        getActionManager().registerAction("actionDelSupp", actionDelSupp);
         this.actionDelSupp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRegister
        this.actionRegister = new ActionRegister(this);
        getActionManager().registerAction("actionRegister", actionRegister);
        this.actionRegister.setBindWorkFlow(true);
         this.actionRegister.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisPatch
        this.actionDisPatch = new ActionDisPatch(this);
        getActionManager().registerAction("actionDisPatch", actionDisPatch);
         this.actionDisPatch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAheadDisPatch
        this.actionAheadDisPatch = new ActionAheadDisPatch(this);
        getActionManager().registerAction("actionAheadDisPatch", actionAheadDisPatch);
         this.actionAheadDisPatch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAttachment
        this.actionViewAttachment = new ActionViewAttachment(this);
        getActionManager().registerAction("actionViewAttachment", actionViewAttachment);
         this.actionViewAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPassHistory
        this.actionPassHistory = new ActionPassHistory(this);
        getActionManager().registerAction("actionPassHistory", actionPassHistory);
         this.actionPassHistory.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUrgentDegree = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWFType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSpecialtyType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeSubject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tbpChangAudit = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contAheadDisPatch = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConductDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOffer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReaDesc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConstrUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDesignUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConstrSite = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConductUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblAttachmentContainer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewAttachment = new com.kingdee.bos.ctrl.swing.KDButton();
        this.chkIsImportChange = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.rbIsChangeContractYes = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.contIsChangeContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.rbIsChangeContractNo = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbIsReceiptsYes = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbIsReceiptsNo = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.contIsReceipts = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCostUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.rbIsBigChangeYes = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbIsBigChangeNo = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.contIsBigChange = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDButtonGroup2 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDButtonGroup3 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.contIsFee = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDesc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAuditType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtChangeReason = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboChangeState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboUrgentDegree = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtWFType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSpecialtyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtChangeSubject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pnlContent = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlSupp = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.ctnEntrys = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contGraphCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboGraphCount = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.ctnSuppEntrys = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtSuppEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contTotalCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmoutA = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDutyAmout = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNoUse = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbxNoUse = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtTotalCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAmountA = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDutyAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNoUse = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.bmptResaon = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contAheadReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConnectType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contValidator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAheadReason = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtConnectType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtValidator = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtConductDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkbookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboOffer = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtReaDesc = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.prmtConstrUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDesignUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtConstrSite = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtConductUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cmbAttachment = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtCostUnit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.cbIsFee = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtDesc = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.btnPassHistory = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRegister = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDisPatch = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemRegister = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDispatch = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contAuditor.setName("contAuditor");
        this.contName.setName("contName");
        this.contAuditTime.setName("contAuditTime");
        this.contCurProject.setName("contCurProject");
        this.contAuditType.setName("contAuditType");
        this.contChangeReason.setName("contChangeReason");
        this.contChangeState.setName("contChangeState");
        this.contUrgentDegree.setName("contUrgentDegree");
        this.contWFType.setName("contWFType");
        this.contSpecialtyType.setName("contSpecialtyType");
        this.contChangeSubject.setName("contChangeSubject");
        this.tbpChangAudit.setName("tbpChangAudit");
        this.contAheadDisPatch.setName("contAheadDisPatch");
        this.contOrg.setName("contOrg");
        this.contConductDept.setName("contConductDept");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contOffer.setName("contOffer");
        this.contReaDesc.setName("contReaDesc");
        this.contConstrUnit.setName("contConstrUnit");
        this.contDesignUnit.setName("contDesignUnit");
        this.contConstrSite.setName("contConstrSite");
        this.contConductUnit.setName("contConductUnit");
        this.lblAttachmentContainer.setName("lblAttachmentContainer");
        this.btnViewAttachment.setName("btnViewAttachment");
        this.chkIsImportChange.setName("chkIsImportChange");
        this.rbIsChangeContractYes.setName("rbIsChangeContractYes");
        this.contIsChangeContract.setName("contIsChangeContract");
        this.rbIsChangeContractNo.setName("rbIsChangeContractNo");
        this.rbIsReceiptsYes.setName("rbIsReceiptsYes");
        this.rbIsReceiptsNo.setName("rbIsReceiptsNo");
        this.contIsReceipts.setName("contIsReceipts");
        this.contCostUnit.setName("contCostUnit");
        this.rbIsBigChangeYes.setName("rbIsBigChangeYes");
        this.rbIsBigChangeNo.setName("rbIsBigChangeNo");
        this.contIsBigChange.setName("contIsBigChange");
        this.contIsFee.setName("contIsFee");
        this.contDesc.setName("contDesc");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtName.setName("txtName");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtCurProject.setName("prmtCurProject");
        this.prmtAuditType.setName("prmtAuditType");
        this.prmtChangeReason.setName("prmtChangeReason");
        this.comboChangeState.setName("comboChangeState");
        this.comboUrgentDegree.setName("comboUrgentDegree");
        this.prmtWFType.setName("prmtWFType");
        this.prmtSpecialtyType.setName("prmtSpecialtyType");
        this.txtChangeSubject.setName("txtChangeSubject");
        this.pnlContent.setName("pnlContent");
        this.pnlSupp.setName("pnlSupp");
        this.ctnEntrys.setName("ctnEntrys");
        this.kdtEntrys.setName("kdtEntrys");
        this.contGraphCount.setName("contGraphCount");
        this.comboGraphCount.setName("comboGraphCount");
        this.ctnSuppEntrys.setName("ctnSuppEntrys");
        this.kdtSuppEntry.setName("kdtSuppEntry");
        this.contTotalCost.setName("contTotalCost");
        this.contAmoutA.setName("contAmoutA");
        this.contDutyAmout.setName("contDutyAmout");
        this.contNoUse.setName("contNoUse");
        this.contReason.setName("contReason");
        this.cbxNoUse.setName("cbxNoUse");
        this.txtTotalCost.setName("txtTotalCost");
        this.txtAmountA.setName("txtAmountA");
        this.txtDutyAmount.setName("txtDutyAmount");
        this.txtNoUse.setName("txtNoUse");
        this.bmptResaon.setName("bmptResaon");
        this.contAheadReason.setName("contAheadReason");
        this.contConnectType.setName("contConnectType");
        this.contValidator.setName("contValidator");
        this.txtAheadReason.setName("txtAheadReason");
        this.txtConnectType.setName("txtConnectType");
        this.txtValidator.setName("txtValidator");
        this.txtOrg.setName("txtOrg");
        this.prmtConductDept.setName("prmtConductDept");
        this.pkbookedDate.setName("pkbookedDate");
        this.cbPeriod.setName("cbPeriod");
        this.comboOffer.setName("comboOffer");
        this.txtReaDesc.setName("txtReaDesc");
        this.prmtConstrUnit.setName("prmtConstrUnit");
        this.prmtDesignUnit.setName("prmtDesignUnit");
        this.txtConstrSite.setName("txtConstrSite");
        this.prmtConductUnit.setName("prmtConductUnit");
        this.cmbAttachment.setName("cmbAttachment");
        this.txtCostUnit.setName("txtCostUnit");
        this.cbIsFee.setName("cbIsFee");
        this.txtDesc.setName("txtDesc");
        this.btnPassHistory.setName("btnPassHistory");
        this.btnRegister.setName("btnRegister");
        this.btnDisPatch.setName("btnDisPatch");
        this.menuItemRegister.setName("menuItemRegister");
        this.menuItemDispatch.setName("menuItemDispatch");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.btnAttachment.setToolTipText(resHelper.getString("btnAttachment.toolTipText"));		
        this.chkMenuItemSubmitAndPrint.setVisible(false);		
        this.chkMenuItemSubmitAndPrint.setEnabled(false);		
        this.MenuItemAttachment.setText(resHelper.getString("MenuItemAttachment.text"));		
        this.MenuItemAttachment.setToolTipText(resHelper.getString("MenuItemAttachment.toolTipText"));		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnAddLine.setEnabled(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setEnabled(false);		
        this.btnRemoveLine.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);
        // contAuditType		
        this.contAuditType.setBoundLabelText(resHelper.getString("contAuditType.boundLabelText"));		
        this.contAuditType.setBoundLabelLength(100);		
        this.contAuditType.setBoundLabelUnderline(true);
        // contChangeReason		
        this.contChangeReason.setBoundLabelText(resHelper.getString("contChangeReason.boundLabelText"));		
        this.contChangeReason.setBoundLabelLength(100);		
        this.contChangeReason.setBoundLabelUnderline(true);
        // contChangeState		
        this.contChangeState.setBoundLabelText(resHelper.getString("contChangeState.boundLabelText"));		
        this.contChangeState.setBoundLabelLength(60);		
        this.contChangeState.setBoundLabelUnderline(true);		
        this.contChangeState.setVisible(false);
        // contUrgentDegree		
        this.contUrgentDegree.setBoundLabelText(resHelper.getString("contUrgentDegree.boundLabelText"));		
        this.contUrgentDegree.setBoundLabelLength(100);		
        this.contUrgentDegree.setBoundLabelUnderline(true);
        // contWFType		
        this.contWFType.setBoundLabelText(resHelper.getString("contWFType.boundLabelText"));		
        this.contWFType.setBoundLabelLength(100);		
        this.contWFType.setBoundLabelUnderline(true);
        // contSpecialtyType		
        this.contSpecialtyType.setBoundLabelText(resHelper.getString("contSpecialtyType.boundLabelText"));		
        this.contSpecialtyType.setBoundLabelLength(100);		
        this.contSpecialtyType.setBoundLabelUnderline(true);
        // contChangeSubject		
        this.contChangeSubject.setBoundLabelText(resHelper.getString("contChangeSubject.boundLabelText"));		
        this.contChangeSubject.setBoundLabelLength(100);		
        this.contChangeSubject.setBoundLabelUnderline(true);		
        this.contChangeSubject.setVisible(false);
        // tbpChangAudit
        // contAheadDisPatch		
        this.contAheadDisPatch.setTitle(resHelper.getString("contAheadDisPatch.title"));		
        this.contAheadDisPatch.setEnableActive(false);
        // contOrg		
        this.contOrg.setBoundLabelText(resHelper.getString("contOrg.boundLabelText"));		
        this.contOrg.setBoundLabelLength(100);		
        this.contOrg.setBoundLabelUnderline(true);
        // contConductDept		
        this.contConductDept.setBoundLabelText(resHelper.getString("contConductDept.boundLabelText"));		
        this.contConductDept.setBoundLabelLength(100);		
        this.contConductDept.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(100);
        // contOffer		
        this.contOffer.setBoundLabelText(resHelper.getString("contOffer.boundLabelText"));		
        this.contOffer.setBoundLabelLength(100);		
        this.contOffer.setBoundLabelUnderline(true);
        // contReaDesc		
        this.contReaDesc.setBoundLabelText(resHelper.getString("contReaDesc.boundLabelText"));		
        this.contReaDesc.setBoundLabelLength(100);		
        this.contReaDesc.setBoundLabelUnderline(true);
        // contConstrUnit		
        this.contConstrUnit.setBoundLabelText(resHelper.getString("contConstrUnit.boundLabelText"));		
        this.contConstrUnit.setBoundLabelLength(100);		
        this.contConstrUnit.setBoundLabelUnderline(true);		
        this.contConstrUnit.setVisible(false);
        // contDesignUnit		
        this.contDesignUnit.setBoundLabelText(resHelper.getString("contDesignUnit.boundLabelText"));		
        this.contDesignUnit.setBoundLabelLength(100);		
        this.contDesignUnit.setBoundLabelUnderline(true);		
        this.contDesignUnit.setVisible(false);
        // contConstrSite		
        this.contConstrSite.setBoundLabelText(resHelper.getString("contConstrSite.boundLabelText"));		
        this.contConstrSite.setBoundLabelLength(100);		
        this.contConstrSite.setBoundLabelUnderline(true);		
        this.contConstrSite.setVisible(false);
        // contConductUnit		
        this.contConductUnit.setBoundLabelText(resHelper.getString("contConductUnit.boundLabelText"));		
        this.contConductUnit.setBoundLabelLength(100);		
        this.contConductUnit.setBoundLabelUnderline(true);
        // lblAttachmentContainer		
        this.lblAttachmentContainer.setBoundLabelText(resHelper.getString("lblAttachmentContainer.boundLabelText"));		
        this.lblAttachmentContainer.setBoundLabelLength(100);		
        this.lblAttachmentContainer.setBoundLabelUnderline(true);
        // btnViewAttachment
        this.btnViewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAttachment.setText(resHelper.getString("btnViewAttachment.text"));
        // chkIsImportChange		
        this.chkIsImportChange.setText(resHelper.getString("chkIsImportChange.text"));		
        this.chkIsImportChange.setVisible(false);
        // rbIsChangeContractYes		
        this.rbIsChangeContractYes.setText(resHelper.getString("rbIsChangeContractYes.text"));
        // contIsChangeContract		
        this.contIsChangeContract.setBoundLabelText(resHelper.getString("contIsChangeContract.boundLabelText"));		
        this.contIsChangeContract.setBoundLabelLength(100);		
        this.contIsChangeContract.setBoundLabelUnderline(true);
        // rbIsChangeContractNo		
        this.rbIsChangeContractNo.setText(resHelper.getString("rbIsChangeContractNo.text"));
        // rbIsReceiptsYes		
        this.rbIsReceiptsYes.setText(resHelper.getString("rbIsReceiptsYes.text"));
        // rbIsReceiptsNo		
        this.rbIsReceiptsNo.setText(resHelper.getString("rbIsReceiptsNo.text"));
        // contIsReceipts		
        this.contIsReceipts.setBoundLabelText(resHelper.getString("contIsReceipts.boundLabelText"));		
        this.contIsReceipts.setBoundLabelLength(100);		
        this.contIsReceipts.setBoundLabelUnderline(true);
        // contCostUnit		
        this.contCostUnit.setBoundLabelText(resHelper.getString("contCostUnit.boundLabelText"));		
        this.contCostUnit.setBoundLabelLength(100);		
        this.contCostUnit.setBoundLabelUnderline(true);
        // rbIsBigChangeYes		
        this.rbIsBigChangeYes.setText(resHelper.getString("rbIsBigChangeYes.text"));
        // rbIsBigChangeNo		
        this.rbIsBigChangeNo.setText(resHelper.getString("rbIsBigChangeNo.text"));
        // contIsBigChange		
        this.contIsBigChange.setBoundLabelText(resHelper.getString("contIsBigChange.boundLabelText"));		
        this.contIsBigChange.setBoundLabelLength(100);		
        this.contIsBigChange.setBoundLabelUnderline(true);
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.rbIsChangeContractYes);
        this.kDButtonGroup1.add(this.rbIsChangeContractNo);
        // kDButtonGroup2
        this.kDButtonGroup2.add(this.rbIsReceiptsYes);
        this.kDButtonGroup2.add(this.rbIsReceiptsNo);
        // kDButtonGroup3
        this.kDButtonGroup3.add(this.rbIsBigChangeYes);
        this.kDButtonGroup3.add(this.rbIsBigChangeNo);
        // contIsFee		
        this.contIsFee.setBoundLabelText(resHelper.getString("contIsFee.boundLabelText"));		
        this.contIsFee.setBoundLabelLength(100);		
        this.contIsFee.setBoundLabelUnderline(true);
        // contDesc		
        this.contDesc.setBoundLabelText(resHelper.getString("contDesc.boundLabelText"));		
        this.contDesc.setBoundLabelLength(100);		
        this.contDesc.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$number$");		
        this.prmtAuditor.setCommitFormat("$name$");
        // txtName		
        this.txtName.setRequired(true);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // prmtCurProject		
        this.prmtCurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtCurProject.setCommitFormat("$name$");		
        this.prmtCurProject.setDisplayFormat("$name$");		
        this.prmtCurProject.setEditFormat("$name$");		
        this.prmtCurProject.setEnabled(false);
        // prmtAuditType		
        this.prmtAuditType.setDisplayFormat("$name$");		
        this.prmtAuditType.setEditFormat("$number$");		
        this.prmtAuditType.setCommitFormat("$number$");		
        this.prmtAuditType.setRequired(true);		
        this.prmtAuditType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ChangeTypeQuery");		
        this.prmtAuditType.setEditable(true);
        this.prmtAuditType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtAuditType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtChangeReason		
        this.prmtChangeReason.setEditable(true);		
        this.prmtChangeReason.setDisplayFormat("$name$");		
        this.prmtChangeReason.setEditFormat("$number$");		
        this.prmtChangeReason.setCommitFormat("$number$");		
        this.prmtChangeReason.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ChangeReasonQuery");		
        this.prmtChangeReason.setRequired(true);
        // comboChangeState		
        this.comboChangeState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.ChangeBillStateEnum").toArray());		
        this.comboChangeState.setEnabled(false);
        // comboUrgentDegree		
        this.comboUrgentDegree.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum").toArray());		
        this.comboUrgentDegree.setRequired(true);
        // prmtWFType		
        this.prmtWFType.setDisplayFormat("$name$");		
        this.prmtWFType.setEditFormat("$number$");		
        this.prmtWFType.setRequired(true);		
        this.prmtWFType.setQueryInfo("com.kingdee.eas.fdc.contract.app.ChangeWFTypeQuery");		
        this.prmtWFType.setEditable(true);		
        this.prmtWFType.setCommitFormat("$number$");
        // prmtSpecialtyType		
        this.prmtSpecialtyType.setDisplayFormat("$name$");		
        this.prmtSpecialtyType.setEditFormat("$number$");		
        this.prmtSpecialtyType.setRequired(true);		
        this.prmtSpecialtyType.setCommitFormat("$number$");		
        this.prmtSpecialtyType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7SpecialtyTypeQuery");		
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
        // txtChangeSubject		
        this.txtChangeSubject.setMaxLength(80);
        // pnlContent		
        this.pnlContent.setAutoscrolls(true);
        // pnlSupp		
        this.pnlSupp.setAutoscrolls(true);
        // ctnEntrys		
        this.ctnEntrys.setTitle(resHelper.getString("ctnEntrys.title"));		
        this.ctnEntrys.setAutoscrolls(true);		
        this.ctnEntrys.setEnableActive(false);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"changeContent\" t:width=\"750\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"isBack\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"effect\" t:width=\"255\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"seq\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{changeContent}</t:Cell><t:Cell>$Resource{isBack}</t:Cell><t:Cell>$Resource{effect}</t:Cell><t:Cell>$Resource{seq}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEntrys_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","number","changeContent","isBack","effect","seq"});


        // contGraphCount		
        this.contGraphCount.setBoundLabelText(resHelper.getString("contGraphCount.boundLabelText"));		
        this.contGraphCount.setBoundLabelLength(100);		
        this.contGraphCount.setBoundLabelUnderline(true);		
        this.contGraphCount.setPreferredSize(new Dimension(270,19));
        // comboGraphCount		
        this.comboGraphCount.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.GraphCountEnum").toArray());		
        this.comboGraphCount.setRequired(true);
        // ctnSuppEntrys		
        this.ctnSuppEntrys.setTitle(resHelper.getString("ctnSuppEntrys.title"));		
        this.ctnSuppEntrys.setEnableActive(false);
        // kdtSuppEntry
		String kdtSuppEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"supp\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"thing\" t:width=\"140\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"name\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"content\" t:width=\"500\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{supp}</t:Cell><t:Cell>$Resource{thing}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{content}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtSuppEntry.setFormatXml(resHelper.translateString("kdtSuppEntry",kdtSuppEntryStrXML));
        this.kdtSuppEntry.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtSuppEntry_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtSuppEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtSuppEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtSuppEntry_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // contTotalCost		
        this.contTotalCost.setBoundLabelText(resHelper.getString("contTotalCost.boundLabelText"));		
        this.contTotalCost.setBoundLabelLength(100);		
        this.contTotalCost.setBoundLabelUnderline(true);
        // contAmoutA		
        this.contAmoutA.setBoundLabelText(resHelper.getString("contAmoutA.boundLabelText"));		
        this.contAmoutA.setBoundLabelLength(100);		
        this.contAmoutA.setBoundLabelUnderline(true);		
        this.contAmoutA.setVisible(false);
        // contDutyAmout		
        this.contDutyAmout.setBoundLabelText(resHelper.getString("contDutyAmout.boundLabelText"));		
        this.contDutyAmout.setBoundLabelLength(100);		
        this.contDutyAmout.setBoundLabelUnderline(true);		
        this.contDutyAmout.setEnabled(false);		
        this.contDutyAmout.setVisible(false);
        // contNoUse		
        this.contNoUse.setBoundLabelText(resHelper.getString("contNoUse.boundLabelText"));		
        this.contNoUse.setBoundLabelLength(100);		
        this.contNoUse.setBoundLabelUnderline(true);		
        this.contNoUse.setVisible(false);
        // contReason		
        this.contReason.setBoundLabelText(resHelper.getString("contReason.boundLabelText"));		
        this.contReason.setBoundLabelLength(100);		
        this.contReason.setBoundLabelUnderline(true);		
        this.contReason.setVisible(false);
        // cbxNoUse		
        this.cbxNoUse.setText(resHelper.getString("cbxNoUse.text"));		
        this.cbxNoUse.setVisible(false);
        this.cbxNoUse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cbxNoUse_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtTotalCost		
        this.txtTotalCost.setDataType(1);		
        this.txtTotalCost.setPrecision(2);		
        this.txtTotalCost.setEnabled(false);
        // txtAmountA		
        this.txtAmountA.setDataType(1);		
        this.txtAmountA.setPrecision(2);
        // txtDutyAmount		
        this.txtDutyAmount.setDataType(1);		
        this.txtDutyAmount.setPrecision(2);		
        this.txtDutyAmount.setEnabled(false);
        // txtNoUse		
        this.txtNoUse.setDataType(1);		
        this.txtNoUse.setPrecision(2);
        // bmptResaon		
        this.bmptResaon.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7InvalidCostReasonQuery");		
        this.bmptResaon.setCommitFormat("$number$");		
        this.bmptResaon.setEditFormat("$number$");		
        this.bmptResaon.setDisplayFormat("$name$");
        // contAheadReason		
        this.contAheadReason.setBoundLabelText(resHelper.getString("contAheadReason.boundLabelText"));		
        this.contAheadReason.setBoundLabelLength(100);		
        this.contAheadReason.setBoundLabelUnderline(true);
        // contConnectType		
        this.contConnectType.setBoundLabelText(resHelper.getString("contConnectType.boundLabelText"));		
        this.contConnectType.setBoundLabelLength(100);		
        this.contConnectType.setBoundLabelUnderline(true);
        // contValidator		
        this.contValidator.setBoundLabelText(resHelper.getString("contValidator.boundLabelText"));		
        this.contValidator.setBoundLabelLength(100);		
        this.contValidator.setBoundLabelUnderline(true);
        // txtAheadReason		
        this.txtAheadReason.setEnabled(false);
        // txtConnectType		
        this.txtConnectType.setEnabled(false);
        // txtValidator		
        this.txtValidator.setEnabled(false);
        // txtOrg		
        this.txtOrg.setMaxLength(80);		
        this.txtOrg.setRequired(true);		
        this.txtOrg.setEditable(false);
        // prmtConductDept		
        this.prmtConductDept.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.prmtConductDept.setCommitFormat("$number$");		
        this.prmtConductDept.setDisplayFormat("$name$");		
        this.prmtConductDept.setEditFormat("$number$");		
        this.prmtConductDept.setRequired(true);		
        this.prmtConductDept.setEditable(true);
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
        // txtReaDesc
        // prmtConstrUnit		
        this.prmtConstrUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");		
        this.prmtConstrUnit.setCommitFormat("$number$");		
        this.prmtConstrUnit.setEditFormat("$number$");		
        this.prmtConstrUnit.setDisplayFormat("$number$ $name$");
        // prmtDesignUnit		
        this.prmtDesignUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");		
        this.prmtDesignUnit.setCommitFormat("$number$");		
        this.prmtDesignUnit.setEditFormat("$number$");		
        this.prmtDesignUnit.setDisplayFormat("$number$ $name$");
        // txtConstrSite
        // prmtConductUnit		
        this.prmtConductUnit.setDisplayFormat("$number$ $name$");		
        this.prmtConductUnit.setEditFormat("$number$");		
        this.prmtConductUnit.setCommitFormat("$number$");		
        this.prmtConductUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");
        // cmbAttachment
        // txtCostUnit
        // cbIsFee		
        this.cbIsFee.setRequired(true);		
        this.cbIsFee.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.app.YesOrNoEnum").toArray());
        // txtDesc
        // btnPassHistory
        this.btnPassHistory.setAction((IItemAction)ActionProxyFactory.getProxy(actionPassHistory, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPassHistory.setText(resHelper.getString("btnPassHistory.text"));
        this.btnPassHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnPassHistory_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRegister
        this.btnRegister.setAction((IItemAction)ActionProxyFactory.getProxy(actionRegister, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRegister.setText(resHelper.getString("btnRegister.text"));		
        this.btnRegister.setToolTipText(resHelper.getString("btnRegister.toolTipText"));
        // btnDisPatch
        this.btnDisPatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisPatch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisPatch.setText(resHelper.getString("btnDisPatch.text"));
        // menuItemRegister
        this.menuItemRegister.setAction((IItemAction)ActionProxyFactory.getProxy(actionRegister, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRegister.setText(resHelper.getString("menuItemRegister.text"));
        // menuItemDispatch
        this.menuItemDispatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisPatch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDispatch.setText(resHelper.getString("menuItemDispatch.text"));
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
        kDSeparator6.setBounds(new Rectangle(8, 625, 990, 5));
        this.add(kDSeparator6, new KDLayout.Constraints(8, 625, 990, 5, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator7.setBounds(new Rectangle(8, 224, 990, 10));
        this.add(kDSeparator7, new KDLayout.Constraints(8, 224, 990, 10, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(9, 633, 470, 19));
        this.add(contCreator, new KDLayout.Constraints(9, 633, 470, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(9, 657, 470, 19));
        this.add(contCreateTime, new KDLayout.Constraints(9, 657, 470, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(12, 30, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(12, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(527, 632, 470, 19));
        this.add(contAuditor, new KDLayout.Constraints(527, 632, 470, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contName.setBounds(new Rectangle(377, 30, 270, 19));
        this.add(contName, new KDLayout.Constraints(377, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(527, 657, 470, 19));
        this.add(contAuditTime, new KDLayout.Constraints(527, 657, 470, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurProject.setBounds(new Rectangle(377, 8, 270, 19));
        this.add(contCurProject, new KDLayout.Constraints(377, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditType.setBounds(new Rectangle(377, 52, 270, 19));
        this.add(contAuditType, new KDLayout.Constraints(377, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChangeReason.setBounds(new Rectangle(730, 52, 270, 19));
        this.add(contChangeReason, new KDLayout.Constraints(730, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contChangeState.setBounds(new Rectangle(955, 179, 158, 19));
        this.add(contChangeState, new KDLayout.Constraints(955, 179, 158, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contUrgentDegree.setBounds(new Rectangle(730, 30, 270, 19));
        this.add(contUrgentDegree, new KDLayout.Constraints(730, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contWFType.setBounds(new Rectangle(377, 74, 270, 19));
        this.add(contWFType, new KDLayout.Constraints(377, 74, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSpecialtyType.setBounds(new Rectangle(730, 74, 270, 19));
        this.add(contSpecialtyType, new KDLayout.Constraints(730, 74, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contChangeSubject.setBounds(new Rectangle(966, 140, 270, 19));
        this.add(contChangeSubject, new KDLayout.Constraints(966, 140, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        tbpChangAudit.setBounds(new Rectangle(9, 228, 993, 327));
        this.add(tbpChangAudit, new KDLayout.Constraints(9, 228, 993, 327, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAheadDisPatch.setBounds(new Rectangle(10, 565, 991, 53));
        this.add(contAheadDisPatch, new KDLayout.Constraints(10, 565, 991, 53, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrg.setBounds(new Rectangle(12, 8, 270, 19));
        this.add(contOrg, new KDLayout.Constraints(12, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contConductDept.setBounds(new Rectangle(377, 96, 270, 19));
        this.add(contConductDept, new KDLayout.Constraints(377, 96, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(12, 52, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(12, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(12, 74, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(12, 74, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOffer.setBounds(new Rectangle(12, 96, 270, 19));
        this.add(contOffer, new KDLayout.Constraints(12, 96, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contReaDesc.setBounds(new Rectangle(377, 142, 270, 43));
        this.add(contReaDesc, new KDLayout.Constraints(377, 142, 270, 43, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contConstrUnit.setBounds(new Rectangle(957, 158, 270, 19));
        this.add(contConstrUnit, new KDLayout.Constraints(957, 158, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDesignUnit.setBounds(new Rectangle(918, 199, 270, 19));
        this.add(contDesignUnit, new KDLayout.Constraints(918, 199, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contConstrSite.setBounds(new Rectangle(921, 117, 270, 19));
        this.add(contConstrSite, new KDLayout.Constraints(921, 117, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contConductUnit.setBounds(new Rectangle(730, 96, 270, 19));
        this.add(contConductUnit, new KDLayout.Constraints(730, 96, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        lblAttachmentContainer.setBounds(new Rectangle(13, 193, 379, 19));
        this.add(lblAttachmentContainer, new KDLayout.Constraints(13, 193, 379, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewAttachment.setBounds(new Rectangle(482, 188, 101, 21));
        this.add(btnViewAttachment, new KDLayout.Constraints(482, 188, 101, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkIsImportChange.setBounds(new Rectangle(920, 139, 99, 19));
        this.add(chkIsImportChange, new KDLayout.Constraints(920, 139, 99, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        rbIsChangeContractYes.setBounds(new Rectangle(117, 119, 51, 19));
        this.add(rbIsChangeContractYes, new KDLayout.Constraints(117, 119, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsChangeContract.setBounds(new Rectangle(13, 119, 101, 19));
        this.add(contIsChangeContract, new KDLayout.Constraints(13, 119, 101, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        rbIsChangeContractNo.setBounds(new Rectangle(198, 119, 51, 19));
        this.add(rbIsChangeContractNo, new KDLayout.Constraints(198, 119, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        rbIsReceiptsYes.setBounds(new Rectangle(117, 142, 51, 19));
        this.add(rbIsReceiptsYes, new KDLayout.Constraints(117, 142, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        rbIsReceiptsNo.setBounds(new Rectangle(198, 142, 51, 19));
        this.add(rbIsReceiptsNo, new KDLayout.Constraints(198, 142, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsReceipts.setBounds(new Rectangle(13, 142, 101, 19));
        this.add(contIsReceipts, new KDLayout.Constraints(13, 142, 101, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostUnit.setBounds(new Rectangle(377, 119, 625, 19));
        this.add(contCostUnit, new KDLayout.Constraints(377, 119, 625, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        rbIsBigChangeYes.setBounds(new Rectangle(117, 165, 51, 19));
        this.add(rbIsBigChangeYes, new KDLayout.Constraints(117, 165, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        rbIsBigChangeNo.setBounds(new Rectangle(198, 165, 51, 19));
        this.add(rbIsBigChangeNo, new KDLayout.Constraints(198, 165, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsBigChange.setBounds(new Rectangle(15, 165, 101, 19));
        this.add(contIsBigChange, new KDLayout.Constraints(15, 165, 101, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsFee.setBounds(new Rectangle(730, 8, 270, 19));
        this.add(contIsFee, new KDLayout.Constraints(730, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDesc.setBounds(new Rectangle(730, 142, 272, 43));
        this.add(contDesc, new KDLayout.Constraints(730, 142, 272, 43, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contName
        contName.setBoundEditor(txtName);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurProject);
        //contAuditType
        contAuditType.setBoundEditor(prmtAuditType);
        //contChangeReason
        contChangeReason.setBoundEditor(prmtChangeReason);
        //contChangeState
        contChangeState.setBoundEditor(comboChangeState);
        //contUrgentDegree
        contUrgentDegree.setBoundEditor(comboUrgentDegree);
        //contWFType
        contWFType.setBoundEditor(prmtWFType);
        //contSpecialtyType
        contSpecialtyType.setBoundEditor(prmtSpecialtyType);
        //contChangeSubject
        contChangeSubject.setBoundEditor(txtChangeSubject);
        //tbpChangAudit
        tbpChangAudit.add(pnlContent, resHelper.getString("pnlContent.constraints"));
        tbpChangAudit.add(pnlSupp, resHelper.getString("pnlSupp.constraints"));
        //pnlContent
pnlContent.setLayout(new BorderLayout(0, 0));        pnlContent.add(ctnEntrys, BorderLayout.CENTER);
        //ctnEntrys
        ctnEntrys.getContentPane().setLayout(new KDLayout());
        ctnEntrys.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 992, 294));        kdtEntrys.setBounds(new Rectangle(0, 0, 973, 196));
        ctnEntrys.getContentPane().add(kdtEntrys, new KDLayout.Constraints(0, 0, 973, 196, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contGraphCount.setBounds(new Rectangle(5, 216, 270, 19));
        ctnEntrys.getContentPane().add(contGraphCount, new KDLayout.Constraints(5, 216, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contGraphCount
        contGraphCount.setBoundEditor(comboGraphCount);
        //pnlSupp
pnlSupp.setLayout(new BorderLayout(0, 0));        pnlSupp.add(ctnSuppEntrys, BorderLayout.CENTER);
        //ctnSuppEntrys
        ctnSuppEntrys.getContentPane().setLayout(new KDLayout());
        ctnSuppEntrys.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 992, 294));        kdtSuppEntry.setBounds(new Rectangle(0, 0, 980, 194));
        ctnSuppEntrys.getContentPane().add(kdtSuppEntry, new KDLayout.Constraints(0, 0, 980, 194, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contTotalCost.setBounds(new Rectangle(4, 214, 270, 19));
        ctnSuppEntrys.getContentPane().add(contTotalCost, new KDLayout.Constraints(4, 214, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmoutA.setBounds(new Rectangle(698, 214, 270, 19));
        ctnSuppEntrys.getContentPane().add(contAmoutA, new KDLayout.Constraints(698, 214, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDutyAmout.setBounds(new Rectangle(352, 214, 270, 19));
        ctnSuppEntrys.getContentPane().add(contDutyAmout, new KDLayout.Constraints(352, 214, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNoUse.setBounds(new Rectangle(352, 238, 270, 19));
        ctnSuppEntrys.getContentPane().add(contNoUse, new KDLayout.Constraints(352, 238, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contReason.setBounds(new Rectangle(698, 238, 270, 19));
        ctnSuppEntrys.getContentPane().add(contReason, new KDLayout.Constraints(698, 238, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        cbxNoUse.setBounds(new Rectangle(5, 238, 270, 19));
        ctnSuppEntrys.getContentPane().add(cbxNoUse, new KDLayout.Constraints(5, 238, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contTotalCost
        contTotalCost.setBoundEditor(txtTotalCost);
        //contAmoutA
        contAmoutA.setBoundEditor(txtAmountA);
        //contDutyAmout
        contDutyAmout.setBoundEditor(txtDutyAmount);
        //contNoUse
        contNoUse.setBoundEditor(txtNoUse);
        //contReason
        contReason.setBoundEditor(bmptResaon);
        //contAheadDisPatch
        contAheadDisPatch.getContentPane().setLayout(new KDLayout());
        contAheadDisPatch.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 565, 991, 53));        contAheadReason.setBounds(new Rectangle(580, 2, 400, 19));
        contAheadDisPatch.getContentPane().add(contAheadReason, new KDLayout.Constraints(580, 2, 400, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contConnectType.setBounds(new Rectangle(290, 2, 270, 19));
        contAheadDisPatch.getContentPane().add(contConnectType, new KDLayout.Constraints(290, 2, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contValidator.setBounds(new Rectangle(0, 2, 270, 19));
        contAheadDisPatch.getContentPane().add(contValidator, new KDLayout.Constraints(0, 2, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contAheadReason
        contAheadReason.setBoundEditor(txtAheadReason);
        //contConnectType
        contConnectType.setBoundEditor(txtConnectType);
        //contValidator
        contValidator.setBoundEditor(txtValidator);
        //contOrg
        contOrg.setBoundEditor(txtOrg);
        //contConductDept
        contConductDept.setBoundEditor(prmtConductDept);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(pkbookedDate);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(cbPeriod);
        //contOffer
        contOffer.setBoundEditor(comboOffer);
        //contReaDesc
        contReaDesc.setBoundEditor(txtReaDesc);
        //contConstrUnit
        contConstrUnit.setBoundEditor(prmtConstrUnit);
        //contDesignUnit
        contDesignUnit.setBoundEditor(prmtDesignUnit);
        //contConstrSite
        contConstrSite.setBoundEditor(txtConstrSite);
        //contConductUnit
        contConductUnit.setBoundEditor(prmtConductUnit);
        //lblAttachmentContainer
        lblAttachmentContainer.setBoundEditor(cmbAttachment);
        //contCostUnit
        contCostUnit.setBoundEditor(txtCostUnit);
        //contIsFee
        contIsFee.setBoundEditor(cbIsFee);
        //contDesc
        contDesc.setBoundEditor(txtDesc);

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
        menuBiz.add(menuItemRegister);
        menuBiz.add(menuItemDispatch);
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
        menuWorkflow.add(kDSeparator5);
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
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnPassHistory);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnRegister);
        this.toolBar.add(btnDisPatch);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtCurProject, "data");
		dataBinder.registerBinding("auditType", com.kingdee.eas.fdc.basedata.ChangeTypeInfo.class, this.prmtAuditType, "data");
		dataBinder.registerBinding("changeReason", com.kingdee.eas.fdc.basedata.ChangeReasonInfo.class, this.prmtChangeReason, "data");
		dataBinder.registerBinding("changeState", com.kingdee.eas.fdc.contract.ChangeBillStateEnum.class, this.comboChangeState, "selectedItem");
		dataBinder.registerBinding("urgentDegree", com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum.class, this.comboUrgentDegree, "selectedItem");
		dataBinder.registerBinding("wfType", com.kingdee.eas.fdc.contract.ChangeWFTypeInfo.class, this.prmtWFType, "data");
		dataBinder.registerBinding("changeSubject", String.class, this.txtChangeSubject, "text");
		dataBinder.registerBinding("entrys.changeContent", String.class, this.kdtEntrys, "changeContent.text");
		dataBinder.registerBinding("entrys.isBack", boolean.class, this.kdtEntrys, "isBack.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.number", String.class, this.kdtEntrys, "number.text");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys.seq", int.class, this.kdtEntrys, "seq.text");
		dataBinder.registerBinding("entrys.effect", String.class, this.kdtEntrys, "effect.text");
		dataBinder.registerBinding("graphCount", com.kingdee.eas.fdc.contract.GraphCountEnum.class, this.comboGraphCount, "selectedItem");
		dataBinder.registerBinding("isNoUse", boolean.class, this.cbxNoUse, "selected");
		dataBinder.registerBinding("totalCost", java.math.BigDecimal.class, this.txtTotalCost, "value");
		dataBinder.registerBinding("amountA", java.math.BigDecimal.class, this.txtAmountA, "value");
		dataBinder.registerBinding("amountDutySupp", java.math.BigDecimal.class, this.txtDutyAmount, "value");
		dataBinder.registerBinding("costNouse", java.math.BigDecimal.class, this.txtNoUse, "value");
		dataBinder.registerBinding("invalidCostReason", com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo.class, this.bmptResaon, "data");
		dataBinder.registerBinding("aheadReason", String.class, this.txtAheadReason, "text");
		dataBinder.registerBinding("connectType", String.class, this.txtConnectType, "text");
		dataBinder.registerBinding("validator", String.class, this.txtValidator, "text");
		dataBinder.registerBinding("conductDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtConductDept, "data");
		dataBinder.registerBinding("bookedDate", java.util.Date.class, this.pkbookedDate, "value");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.cbPeriod, "data");
		dataBinder.registerBinding("offer", com.kingdee.eas.fdc.contract.OfferEnum.class, this.comboOffer, "selectedItem");
		dataBinder.registerBinding("reaDesc", String.class, this.txtReaDesc, "_multiLangItem");
		dataBinder.registerBinding("constrUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtConstrUnit, "data");
		dataBinder.registerBinding("designUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtDesignUnit, "data");
		dataBinder.registerBinding("constrSite", String.class, this.txtConstrSite, "text");
		dataBinder.registerBinding("conductUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtConductUnit, "data");
		dataBinder.registerBinding("costUnit", String.class, this.txtCostUnit, "text");
		dataBinder.registerBinding("isFee", com.kingdee.eas.fdc.contract.app.YesOrNoEnum.class, this.cbIsFee, "selectedItem");
		dataBinder.registerBinding("description", String.class, this.txtDesc, "_multiLangItem");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionAddLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionRemoveLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionAddSupp, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionDelSupp, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionUnAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAddSupp, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionDelSupp, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ChangeAuditEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.ChangeAuditBillInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("urgentDegree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("wfType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeSubject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.changeContent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.isBack", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.effect", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("graphCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isNoUse", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountA", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountDutySupp", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costNouse", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invalidCostReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aheadReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("connectType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("validator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conductDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("offer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reaDesc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("constrUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("designUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("constrSite", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conductUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isFee", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    		
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
		            this.actionAddLine.setEnabled(false);
		            this.actionRemoveLine.setEnabled(false);
		            this.actionAddSupp.setEnabled(false);
		            this.actionDelSupp.setEnabled(false);
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		            this.actionAudit.setEnabled(false);
		            this.actionUnAudit.setEnabled(false);
		            this.actionAddSupp.setEnabled(false);
		            this.actionDelSupp.setEnabled(false);
        }
    }

    /**
     * output prmtAuditType_dataChanged method
     */
    protected void prmtAuditType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtSpecialtyType_willShow method
     */
    protected void prmtSpecialtyType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_tableClicked method
     */
    protected void kdtEntrys_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtSuppEntry_editStopped method
     */
    protected void kdtSuppEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtSuppEntry_editStopping method
     */
    protected void kdtSuppEntry_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtSuppEntry_tableClicked method
     */
    protected void kdtSuppEntry_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output cbxNoUse_actionPerformed method
     */
    protected void cbxNoUse_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output pkbookedDate_dataChanged method
     */
    protected void pkbookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboOffer_itemStateChanged method
     */
    protected void comboOffer_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output btnPassHistory_actionPerformed method
     */
    protected void btnPassHistory_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("auditTime"));
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
			sic.add(new SelectorItemInfo("auditType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditType.id"));
        	sic.add(new SelectorItemInfo("auditType.number"));
        	sic.add(new SelectorItemInfo("auditType.name"));
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
        sic.add(new SelectorItemInfo("changeState"));
        sic.add(new SelectorItemInfo("urgentDegree"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("wfType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("wfType.id"));
        	sic.add(new SelectorItemInfo("wfType.number"));
        	sic.add(new SelectorItemInfo("wfType.name"));
		}
        sic.add(new SelectorItemInfo("changeSubject"));
    	sic.add(new SelectorItemInfo("entrys.changeContent"));
    	sic.add(new SelectorItemInfo("entrys.isBack"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("entrys.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.id"));
    	sic.add(new SelectorItemInfo("entrys.seq"));
    	sic.add(new SelectorItemInfo("entrys.effect"));
        sic.add(new SelectorItemInfo("graphCount"));
        sic.add(new SelectorItemInfo("isNoUse"));
        sic.add(new SelectorItemInfo("totalCost"));
        sic.add(new SelectorItemInfo("amountA"));
        sic.add(new SelectorItemInfo("amountDutySupp"));
        sic.add(new SelectorItemInfo("costNouse"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("invalidCostReason.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("invalidCostReason.id"));
        	sic.add(new SelectorItemInfo("invalidCostReason.number"));
        	sic.add(new SelectorItemInfo("invalidCostReason.name"));
		}
        sic.add(new SelectorItemInfo("aheadReason"));
        sic.add(new SelectorItemInfo("connectType"));
        sic.add(new SelectorItemInfo("validator"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("conductDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("conductDept.id"));
        	sic.add(new SelectorItemInfo("conductDept.number"));
        	sic.add(new SelectorItemInfo("conductDept.name"));
		}
        sic.add(new SelectorItemInfo("bookedDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("period.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("period.id"));
        	sic.add(new SelectorItemInfo("period.number"));
		}
        sic.add(new SelectorItemInfo("offer"));
        sic.add(new SelectorItemInfo("reaDesc"));
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
			sic.add(new SelectorItemInfo("designUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("designUnit.id"));
        	sic.add(new SelectorItemInfo("designUnit.number"));
        	sic.add(new SelectorItemInfo("designUnit.name"));
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
        sic.add(new SelectorItemInfo("costUnit"));
        sic.add(new SelectorItemInfo("isFee"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
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
     * output actionAddSupp_actionPerformed method
     */
    public void actionAddSupp_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelSupp_actionPerformed method
     */
    public void actionDelSupp_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRegister_actionPerformed method
     */
    public void actionRegister_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisPatch_actionPerformed method
     */
    public void actionDisPatch_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAheadDisPatch_actionPerformed method
     */
    public void actionAheadDisPatch_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewAttachment_actionPerformed method
     */
    public void actionViewAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPassHistory_actionPerformed method
     */
    public void actionPassHistory_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSave(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
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
	public RequestContext prepareActionAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAttachment(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachment() {
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
	public RequestContext prepareActionAddSupp(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddSupp() {
    	return false;
    }
	public RequestContext prepareActionDelSupp(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelSupp() {
    	return false;
    }
	public RequestContext prepareActionRegister(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRegister() {
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
	public RequestContext prepareActionAheadDisPatch(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAheadDisPatch() {
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
	public RequestContext prepareActionPassHistory(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPassHistory() {
    	return false;
    }

    /**
     * output ActionAddSupp class
     */     
    protected class ActionAddSupp extends ItemAction {     
    
        public ActionAddSupp()
        {
            this(null);
        }

        public ActionAddSupp(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddSupp.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddSupp.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddSupp.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChangeAuditEditUI.this, "ActionAddSupp", "actionAddSupp_actionPerformed", e);
        }
    }

    /**
     * output ActionDelSupp class
     */     
    protected class ActionDelSupp extends ItemAction {     
    
        public ActionDelSupp()
        {
            this(null);
        }

        public ActionDelSupp(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelSupp.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelSupp.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelSupp.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChangeAuditEditUI.this, "ActionDelSupp", "actionDelSupp_actionPerformed", e);
        }
    }

    /**
     * output ActionRegister class
     */     
    protected class ActionRegister extends ItemAction {     
    
        public ActionRegister()
        {
            this(null);
        }

        public ActionRegister(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRegister.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRegister.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRegister.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChangeAuditEditUI.this, "ActionRegister", "actionRegister_actionPerformed", e);
        }
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
            innerActionPerformed("eas", AbstractChangeAuditEditUI.this, "ActionDisPatch", "actionDisPatch_actionPerformed", e);
        }
    }

    /**
     * output ActionAheadDisPatch class
     */     
    protected class ActionAheadDisPatch extends ItemAction {     
    
        public ActionAheadDisPatch()
        {
            this(null);
        }

        public ActionAheadDisPatch(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAheadDisPatch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAheadDisPatch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAheadDisPatch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChangeAuditEditUI.this, "ActionAheadDisPatch", "actionAheadDisPatch_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractChangeAuditEditUI.this, "ActionViewAttachment", "actionViewAttachment_actionPerformed", e);
        }
    }

    /**
     * output ActionPassHistory class
     */     
    protected class ActionPassHistory extends ItemAction {     
    
        public ActionPassHistory()
        {
            this(null);
        }

        public ActionPassHistory(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPassHistory.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPassHistory.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPassHistory.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChangeAuditEditUI.this, "ActionPassHistory", "actionPassHistory_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ChangeAuditEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}