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
public abstract class AbstractContractSettlementBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractSettlementBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbillName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsettlePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbuildArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contgetFeeCriteria;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contunitPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continfoPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqualityGuarante;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDContainer contDetailInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProj;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGuaranceAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblAttachmentContainer;
    protected com.kingdee.bos.ctrl.swing.KDButton viewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConstructPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgAuditCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostDertCost;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnAType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnBType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton BtnCType;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup btnSettelGroup;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpartB;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractOriginalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDeduct;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToReward;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contJGC;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGJ;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCreator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbillName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSettlePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtbuildArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtgetFeeCriteria;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtunitPrice;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtinfoPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtqualityGuarante;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcontractNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcontractName;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabTop;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelCollection;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelCompensation;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelDeduct;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelGuerdon;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelSettlementbill;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCompensationBill;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblDeduct;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblGuerdon;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSettlementBill;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtqualityGuaranteRate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProj;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbFinalSettle;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbookedDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox cbPeriod;
    protected com.kingdee.bos.ctrl.swing.KDTextField tetQualityTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtExchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOriginalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalOriginalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalSettlePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtGuaranceAmt;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbAttchment;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtConstructPrice;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtORGAuditCost;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtCostAuditCost;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmttpartB;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractOriginalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLastPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPayAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtChangeAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDeduct;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtToReward;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtJGC;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtGJ;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplit;
    protected com.kingdee.eas.fdc.contract.ContractSettlementBillInfo editData = null;
    protected ActionSplit actionSplit = null;
    protected ActionViewAttachment actionViewAttachment = null;
    /**
     * output class constructor
     */
    public AbstractContractSettlementBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractSettlementBillEditUI.class.getName());
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
        //actionCopy
        actionCopy.setEnabled(true);
        actionCopy.setDaemonRun(false);

        actionCopy.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift C"));
        _tempStr = resHelper.getString("ActionCopy.SHORT_DESCRIPTION");
        actionCopy.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCopy.LONG_DESCRIPTION");
        actionCopy.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCopy.NAME");
        actionCopy.putValue(ItemAction.NAME, _tempStr);
         this.actionCopy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionCopy.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionCopy.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAddNew
        actionAddNew.setEnabled(true);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl n"));
        _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
        actionAddNew.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
        actionAddNew.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.NAME");
        actionAddNew.putValue(ItemAction.NAME, _tempStr);
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
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
        //actionSplit
        this.actionSplit = new ActionSplit(this);
        getActionManager().registerAction("actionSplit", actionSplit);
        this.actionSplit.setBindWorkFlow(true);
         this.actionSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAttachment
        this.actionViewAttachment = new ActionViewAttachment(this);
        getActionManager().registerAction("actionViewAttachment", actionViewAttachment);
         this.actionViewAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbillName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsettlePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbuildArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contgetFeeCriteria = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contunitPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continfoPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqualityGuarante = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDetailInfo = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProj = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer17 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGuaranceAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblAttachmentContainer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.viewAttachment = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contConstructPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgAuditCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCostDertCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.btnAType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnBType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.BtnCType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnSettelGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contpartB = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractOriginalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contDeduct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToReward = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contJGC = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGJ = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.dateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bizPromptAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.bizPromptCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtbillName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSettlePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtbuildArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtgetFeeCriteria = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtunitPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtinfoPrice = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtqualityGuarante = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkauditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtcontractNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcontractName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tabTop = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.panelCollection = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelCompensation = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelDeduct = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelGuerdon = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelSettlementbill = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblCompensationBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblDeduct = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblGuerdon = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblSettlementBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtqualityGuaranteRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtProj = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOrgUnit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.cbFinalSettle = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkbookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tetQualityTime = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtExchangeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOriginalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalOriginalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalSettlePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtGuaranceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.cmbAttchment = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtConstructPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtORGAuditCost = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtCostAuditCost = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.prmttpartB = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtContractOriginalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLastPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPayAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtChangeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDeduct = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtToReward = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtJGC = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtGJ = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.contbillName.setName("contbillName");
        this.contsettlePrice.setName("contsettlePrice");
        this.contbuildArea.setName("contbuildArea");
        this.contgetFeeCriteria.setName("contgetFeeCriteria");
        this.contunitPrice.setName("contunitPrice");
        this.continfoPrice.setName("continfoPrice");
        this.contqualityGuarante.setName("contqualityGuarante");
        this.contauditTime.setName("contauditTime");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.contDetailInfo.setName("contDetailInfo");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.contProj.setName("contProj");
        this.contOrgUnit.setName("contOrgUnit");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.kDLabelContainer17.setName("kDLabelContainer17");
        this.contGuaranceAmt.setName("contGuaranceAmt");
        this.lblAttachmentContainer.setName("lblAttachmentContainer");
        this.viewAttachment.setName("viewAttachment");
        this.contConstructPrice.setName("contConstructPrice");
        this.contOrgAuditCost.setName("contOrgAuditCost");
        this.contCostDertCost.setName("contCostDertCost");
        this.kDLabel1.setName("kDLabel1");
        this.btnAType.setName("btnAType");
        this.btnBType.setName("btnBType");
        this.BtnCType.setName("BtnCType");
        this.kDLabel2.setName("kDLabel2");
        this.contpartB.setName("contpartB");
        this.contContractOriginalAmount.setName("contContractOriginalAmount");
        this.contContractAmount.setName("contContractAmount");
        this.contLastPrice.setName("contLastPrice");
        this.contPayAmount.setName("contPayAmount");
        this.contChangeAmount.setName("contChangeAmount");
        this.kDLabel3.setName("kDLabel3");
        this.kDSeparator8.setName("kDSeparator8");
        this.contDeduct.setName("contDeduct");
        this.contToReward.setName("contToReward");
        this.contJGC.setName("contJGC");
        this.contGJ.setName("contGJ");
        this.txtNumber.setName("txtNumber");
        this.dateCreateTime.setName("dateCreateTime");
        this.bizPromptAuditor.setName("bizPromptAuditor");
        this.txtDescription.setName("txtDescription");
        this.bizPromptCreator.setName("bizPromptCreator");
        this.txtbillName.setName("txtbillName");
        this.txtSettlePrice.setName("txtSettlePrice");
        this.txtbuildArea.setName("txtbuildArea");
        this.txtgetFeeCriteria.setName("txtgetFeeCriteria");
        this.txtunitPrice.setName("txtunitPrice");
        this.txtinfoPrice.setName("txtinfoPrice");
        this.txtqualityGuarante.setName("txtqualityGuarante");
        this.pkauditTime.setName("pkauditTime");
        this.txtcontractNumber.setName("txtcontractNumber");
        this.txtcontractName.setName("txtcontractName");
        this.tabTop.setName("tabTop");
        this.panelCollection.setName("panelCollection");
        this.panelCompensation.setName("panelCompensation");
        this.panelDeduct.setName("panelDeduct");
        this.panelGuerdon.setName("panelGuerdon");
        this.panelSettlementbill.setName("panelSettlementbill");
        this.tblCompensationBill.setName("tblCompensationBill");
        this.tblDeduct.setName("tblDeduct");
        this.tblGuerdon.setName("tblGuerdon");
        this.tblSettlementBill.setName("tblSettlementBill");
        this.txtqualityGuaranteRate.setName("txtqualityGuaranteRate");
        this.txtProj.setName("txtProj");
        this.txtOrgUnit.setName("txtOrgUnit");
        this.cbFinalSettle.setName("cbFinalSettle");
        this.pkbookedDate.setName("pkbookedDate");
        this.cbPeriod.setName("cbPeriod");
        this.tetQualityTime.setName("tetQualityTime");
        this.prmtCurrency.setName("prmtCurrency");
        this.txtExchangeRate.setName("txtExchangeRate");
        this.txtOriginalAmount.setName("txtOriginalAmount");
        this.txtTotalOriginalAmount.setName("txtTotalOriginalAmount");
        this.txtTotalSettlePrice.setName("txtTotalSettlePrice");
        this.txtGuaranceAmt.setName("txtGuaranceAmt");
        this.cmbAttchment.setName("cmbAttchment");
        this.txtConstructPrice.setName("txtConstructPrice");
        this.txtORGAuditCost.setName("txtORGAuditCost");
        this.txtCostAuditCost.setName("txtCostAuditCost");
        this.prmttpartB.setName("prmttpartB");
        this.txtContractOriginalAmount.setName("txtContractOriginalAmount");
        this.txtContractAmount.setName("txtContractAmount");
        this.txtLastPrice.setName("txtLastPrice");
        this.txtPayAmount.setName("txtPayAmount");
        this.txtChangeAmount.setName("txtChangeAmount");
        this.txtDeduct.setName("txtDeduct");
        this.txtToReward.setName("txtToReward");
        this.txtJGC.setName("txtJGC");
        this.txtGJ.setName("txtGJ");
        this.btnSplit.setName("btnSplit");
        this.menuItemSplit.setName("menuItemSplit");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnCopy.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.separatorFW7.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.separatorFW8.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
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
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setToolTipText(resHelper.getString("btnUnAudit.toolTipText"));
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));		
        this.menuItemAudit.setMnemonic(85);
        this.menuItemUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setToolTipText(resHelper.getString("menuItemUnAudit.toolTipText"));		
        this.menuItemUnAudit.setMnemonic(80);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setVisible(true);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelAlignment(7);		
        this.kDLabelContainer5.setVisible(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelAlignment(7);		
        this.kDLabelContainer6.setVisible(true);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setBoundLabelAlignment(7);		
        this.kDLabelContainer7.setVisible(true);
        // contbillName		
        this.contbillName.setBoundLabelText(resHelper.getString("contbillName.boundLabelText"));		
        this.contbillName.setBoundLabelLength(100);		
        this.contbillName.setBoundLabelUnderline(true);		
        this.contbillName.setVisible(true);		
        this.contbillName.setBoundLabelAlignment(7);
        // contsettlePrice		
        this.contsettlePrice.setBoundLabelText(resHelper.getString("contsettlePrice.boundLabelText"));		
        this.contsettlePrice.setBoundLabelLength(170);		
        this.contsettlePrice.setBoundLabelUnderline(true);		
        this.contsettlePrice.setVisible(true);		
        this.contsettlePrice.setBoundLabelAlignment(7);
        // contbuildArea		
        this.contbuildArea.setBoundLabelText(resHelper.getString("contbuildArea.boundLabelText"));		
        this.contbuildArea.setBoundLabelLength(100);		
        this.contbuildArea.setBoundLabelUnderline(true);		
        this.contbuildArea.setVisible(false);		
        this.contbuildArea.setBoundLabelAlignment(7);
        // contgetFeeCriteria		
        this.contgetFeeCriteria.setBoundLabelText(resHelper.getString("contgetFeeCriteria.boundLabelText"));		
        this.contgetFeeCriteria.setBoundLabelLength(100);		
        this.contgetFeeCriteria.setBoundLabelUnderline(true);		
        this.contgetFeeCriteria.setVisible(false);		
        this.contgetFeeCriteria.setBoundLabelAlignment(7);
        // contunitPrice		
        this.contunitPrice.setBoundLabelText(resHelper.getString("contunitPrice.boundLabelText"));		
        this.contunitPrice.setBoundLabelLength(100);		
        this.contunitPrice.setBoundLabelUnderline(true);		
        this.contunitPrice.setVisible(false);		
        this.contunitPrice.setBoundLabelAlignment(7);
        // continfoPrice		
        this.continfoPrice.setBoundLabelText(resHelper.getString("continfoPrice.boundLabelText"));		
        this.continfoPrice.setBoundLabelLength(100);		
        this.continfoPrice.setBoundLabelUnderline(true);		
        this.continfoPrice.setVisible(false);		
        this.continfoPrice.setBoundLabelAlignment(7);
        // contqualityGuarante		
        this.contqualityGuarante.setBoundLabelText(resHelper.getString("contqualityGuarante.boundLabelText"));		
        this.contqualityGuarante.setBoundLabelLength(100);		
        this.contqualityGuarante.setBoundLabelUnderline(true);		
        this.contqualityGuarante.setVisible(false);
        // contauditTime		
        this.contauditTime.setBoundLabelText(resHelper.getString("contauditTime.boundLabelText"));		
        this.contauditTime.setBoundLabelLength(100);		
        this.contauditTime.setBoundLabelUnderline(true);		
        this.contauditTime.setVisible(true);		
        this.contauditTime.setBoundLabelAlignment(7);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);		
        this.kDLabelContainer9.setBoundLabelAlignment(7);		
        this.kDLabelContainer9.setVisible(true);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);		
        this.kDLabelContainer10.setVisible(true);		
        this.kDLabelContainer10.setBoundLabelAlignment(7);
        // contDetailInfo		
        this.contDetailInfo.setTitle(resHelper.getString("contDetailInfo.title"));
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // contProj		
        this.contProj.setBoundLabelText(resHelper.getString("contProj.boundLabelText"));		
        this.contProj.setBoundLabelLength(100);		
        this.contProj.setBoundLabelUnderline(true);		
        this.contProj.setBoundLabelAlignment(7);		
        this.contProj.setVisible(true);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);		
        this.contOrgUnit.setVisible(true);		
        this.contOrgUnit.setBoundLabelAlignment(7);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelUnderline(true);		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setVisible(false);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(30);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(30);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelUnderline(true);		
        this.kDLabelContainer15.setBoundLabelLength(160);
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(100);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);		
        this.kDLabelContainer16.setVisible(false);
        // kDLabelContainer17		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelLength(100);		
        this.kDLabelContainer17.setBoundLabelUnderline(true);		
        this.kDLabelContainer17.setVisible(false);
        // contGuaranceAmt		
        this.contGuaranceAmt.setBoundLabelText(resHelper.getString("contGuaranceAmt.boundLabelText"));		
        this.contGuaranceAmt.setBoundLabelUnderline(true);		
        this.contGuaranceAmt.setBoundLabelLength(100);
        // lblAttachmentContainer		
        this.lblAttachmentContainer.setBoundLabelText(resHelper.getString("lblAttachmentContainer.boundLabelText"));		
        this.lblAttachmentContainer.setBoundLabelLength(100);		
        this.lblAttachmentContainer.setBoundLabelUnderline(true);
        // viewAttachment
        this.viewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.viewAttachment.setText(resHelper.getString("viewAttachment.text"));
        // contConstructPrice		
        this.contConstructPrice.setBoundLabelText(resHelper.getString("contConstructPrice.boundLabelText"));		
        this.contConstructPrice.setBoundLabelLength(100);		
        this.contConstructPrice.setBoundLabelUnderline(true);
        // contOrgAuditCost		
        this.contOrgAuditCost.setBoundLabelText(resHelper.getString("contOrgAuditCost.boundLabelText"));		
        this.contOrgAuditCost.setBoundLabelLength(130);		
        this.contOrgAuditCost.setBoundLabelUnderline(true);
        // contCostDertCost		
        this.contCostDertCost.setBoundLabelText(resHelper.getString("contCostDertCost.boundLabelText"));		
        this.contCostDertCost.setBoundLabelLength(130);		
        this.contCostDertCost.setBoundLabelUnderline(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));		
        this.kDLabel1.setVisible(false);
        // btnAType		
        this.btnAType.setText(resHelper.getString("btnAType.text"));		
        this.btnAType.setVisible(false);
        // btnBType		
        this.btnBType.setText(resHelper.getString("btnBType.text"));		
        this.btnBType.setVisible(false);
        // BtnCType		
        this.BtnCType.setText(resHelper.getString("BtnCType.text"));		
        this.BtnCType.setVisible(false);
        // btnSettelGroup
        this.btnSettelGroup.add(this.btnAType);
        this.btnSettelGroup.add(this.btnBType);
        this.btnSettelGroup.add(this.BtnCType);
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // contpartB		
        this.contpartB.setBoundLabelText(resHelper.getString("contpartB.boundLabelText"));		
        this.contpartB.setBoundLabelLength(100);		
        this.contpartB.setBoundLabelUnderline(true);		
        this.contpartB.setVisible(true);		
        this.contpartB.setBoundLabelAlignment(7);		
        this.contpartB.setEnabled(false);
        // contContractOriginalAmount		
        this.contContractOriginalAmount.setBoundLabelText(resHelper.getString("contContractOriginalAmount.boundLabelText"));		
        this.contContractOriginalAmount.setBoundLabelLength(100);		
        this.contContractOriginalAmount.setBoundLabelUnderline(true);		
        this.contContractOriginalAmount.setEnabled(false);
        // contContractAmount		
        this.contContractAmount.setBoundLabelText(resHelper.getString("contContractAmount.boundLabelText"));		
        this.contContractAmount.setBoundLabelLength(100);		
        this.contContractAmount.setBoundLabelUnderline(true);		
        this.contContractAmount.setEnabled(false);
        // contLastPrice		
        this.contLastPrice.setBoundLabelText(resHelper.getString("contLastPrice.boundLabelText"));		
        this.contLastPrice.setBoundLabelLength(100);		
        this.contLastPrice.setBoundLabelUnderline(true);		
        this.contLastPrice.setEnabled(false);
        // contPayAmount		
        this.contPayAmount.setBoundLabelText(resHelper.getString("contPayAmount.boundLabelText"));		
        this.contPayAmount.setBoundLabelLength(100);		
        this.contPayAmount.setBoundLabelUnderline(true);		
        this.contPayAmount.setEnabled(false);
        // contChangeAmount		
        this.contChangeAmount.setBoundLabelText(resHelper.getString("contChangeAmount.boundLabelText"));		
        this.contChangeAmount.setBoundLabelLength(100);		
        this.contChangeAmount.setBoundLabelUnderline(true);		
        this.contChangeAmount.setEnabled(false);
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // kDSeparator8
        // contDeduct		
        this.contDeduct.setBoundLabelText(resHelper.getString("contDeduct.boundLabelText"));		
        this.contDeduct.setBoundLabelLength(70);		
        this.contDeduct.setBoundLabelUnderline(true);		
        this.contDeduct.setEnabled(false);
        // contToReward		
        this.contToReward.setBoundLabelText(resHelper.getString("contToReward.boundLabelText"));		
        this.contToReward.setBoundLabelLength(70);		
        this.contToReward.setBoundLabelUnderline(true);		
        this.contToReward.setEnabled(false);
        // contJGC		
        this.contJGC.setBoundLabelText(resHelper.getString("contJGC.boundLabelText"));		
        this.contJGC.setBoundLabelLength(100);		
        this.contJGC.setBoundLabelUnderline(true);
        // contGJ		
        this.contGJ.setBoundLabelText(resHelper.getString("contGJ.boundLabelText"));		
        this.contGJ.setBoundLabelLength(100);		
        this.contGJ.setBoundLabelUnderline(true);		
        this.contGJ.setVisible(true);		
        this.contGJ.setBoundLabelAlignment(7);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setForeground(new java.awt.Color(60,60,60));
        // dateCreateTime		
        this.dateCreateTime.setEnabled(false);		
        this.dateCreateTime.setVisible(true);
        // bizPromptAuditor		
        this.bizPromptAuditor.setEnabled(false);		
        this.bizPromptAuditor.setVisible(true);		
        this.bizPromptAuditor.setEditable(true);		
        this.bizPromptAuditor.setDisplayFormat("$name$");		
        this.bizPromptAuditor.setEditFormat("$number$");		
        this.bizPromptAuditor.setCommitFormat("$number$");		
        this.bizPromptAuditor.setRequired(false);
        // txtDescription		
        this.txtDescription.setMaxLength(500);
        // bizPromptCreator		
        this.bizPromptCreator.setEnabled(false);		
        this.bizPromptCreator.setVisible(true);		
        this.bizPromptCreator.setEditable(true);		
        this.bizPromptCreator.setDisplayFormat("$name$");		
        this.bizPromptCreator.setEditFormat("$number$");		
        this.bizPromptCreator.setCommitFormat("$number$");		
        this.bizPromptCreator.setRequired(false);
        // txtbillName		
        this.txtbillName.setVisible(true);		
        this.txtbillName.setHorizontalAlignment(2);		
        this.txtbillName.setMaxLength(100);		
        this.txtbillName.setRequired(true);		
        this.txtbillName.setEnabled(true);		
        this.txtbillName.setForeground(new java.awt.Color(60,60,60));
        // txtSettlePrice		
        this.txtSettlePrice.setVisible(true);		
        this.txtSettlePrice.setHorizontalAlignment(2);		
        this.txtSettlePrice.setDataType(1);		
        this.txtSettlePrice.setSupportedEmpty(true);		
        this.txtSettlePrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtSettlePrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtSettlePrice.setPrecision(2);		
        this.txtSettlePrice.setRequired(true);		
        this.txtSettlePrice.setEnabled(true);
        this.txtSettlePrice.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtSettlePrice_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtSettlePrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtSettlePrice_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtbuildArea		
        this.txtbuildArea.setVisible(true);		
        this.txtbuildArea.setHorizontalAlignment(2);		
        this.txtbuildArea.setDataType(1);		
        this.txtbuildArea.setSupportedEmpty(true);		
        this.txtbuildArea.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtbuildArea.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtbuildArea.setPrecision(4);		
        this.txtbuildArea.setRequired(false);		
        this.txtbuildArea.setEnabled(true);
        this.txtbuildArea.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtbuildArea_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtgetFeeCriteria		
        this.txtgetFeeCriteria.setVisible(true);		
        this.txtgetFeeCriteria.setHorizontalAlignment(2);		
        this.txtgetFeeCriteria.setMaxLength(100);		
        this.txtgetFeeCriteria.setRequired(false);		
        this.txtgetFeeCriteria.setEnabled(true);
        // txtunitPrice		
        this.txtunitPrice.setVisible(true);		
        this.txtunitPrice.setHorizontalAlignment(2);		
        this.txtunitPrice.setDataType(1);		
        this.txtunitPrice.setSupportedEmpty(true);		
        this.txtunitPrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtunitPrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtunitPrice.setPrecision(2);		
        this.txtunitPrice.setRequired(false);		
        this.txtunitPrice.setEnabled(true);
        // txtinfoPrice		
        this.txtinfoPrice.setVisible(true);		
        this.txtinfoPrice.setHorizontalAlignment(2);		
        this.txtinfoPrice.setMaxLength(100);		
        this.txtinfoPrice.setRequired(false);		
        this.txtinfoPrice.setEnabled(true);
        // txtqualityGuarante		
        this.txtqualityGuarante.setVisible(true);		
        this.txtqualityGuarante.setHorizontalAlignment(2);		
        this.txtqualityGuarante.setDataType(1);		
        this.txtqualityGuarante.setSupportedEmpty(true);		
        this.txtqualityGuarante.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtqualityGuarante.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtqualityGuarante.setPrecision(2);		
        this.txtqualityGuarante.setRequired(true);		
        this.txtqualityGuarante.setEnabled(false);
        // pkauditTime		
        this.pkauditTime.setVisible(true);		
        this.pkauditTime.setEnabled(false);
        // txtcontractNumber		
        this.txtcontractNumber.setMaxLength(80);		
        this.txtcontractNumber.setVisible(true);		
        this.txtcontractNumber.setEnabled(false);		
        this.txtcontractNumber.setHorizontalAlignment(2);		
        this.txtcontractNumber.setRequired(false);
        // txtcontractName		
        this.txtcontractName.setVisible(true);		
        this.txtcontractName.setHorizontalAlignment(2);		
        this.txtcontractName.setMaxLength(100);		
        this.txtcontractName.setRequired(false);		
        this.txtcontractName.setEnabled(false);
        // tabTop
        // panelCollection
        // panelCompensation
        // panelDeduct
        // panelGuerdon
        // panelSettlementbill		
        this.panelSettlementbill.setBorder(null);
        // tblCompensationBill
		String tblCompensationBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"select\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"deductType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{select}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{deductType}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblCompensationBill.setFormatXml(resHelper.translateString("tblCompensationBill",tblCompensationBillStrXML));

        

        this.tblCompensationBill.checkParsed();
        // tblDeduct
		String tblDeductStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:NumberFormat>###,###.00</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"select\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"reason\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"deductTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{select}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{reason}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{deductTime}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblDeduct.setFormatXml(resHelper.translateString("tblDeduct",tblDeductStrXML));

        

        this.tblDeduct.checkParsed();
        // tblGuerdon
		String tblGuerdonStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>###,###.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"select\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"reason\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{select}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{reason}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblGuerdon.setFormatXml(resHelper.translateString("tblGuerdon",tblGuerdonStrXML));

        

        this.tblGuerdon.checkParsed();
        // tblSettlementBill
		String tblSettlementBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"billName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"settlePrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"qualityGuarante\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"bookedDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"period\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"buildArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"getFeeCriteria\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"unitPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"infoPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"auditor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"auditorTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"originalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{billName}</t:Cell><t:Cell>$Resource{settlePrice}</t:Cell><t:Cell>$Resource{qualityGuarante}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{bookedDate}</t:Cell><t:Cell>$Resource{period}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{buildArea}</t:Cell><t:Cell>$Resource{getFeeCriteria}</t:Cell><t:Cell>$Resource{unitPrice}</t:Cell><t:Cell>$Resource{infoPrice}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{auditor.name}</t:Cell><t:Cell>$Resource{auditorTime}</t:Cell><t:Cell>$Resource{originalAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSettlementBill.setFormatXml(resHelper.translateString("tblSettlementBill",tblSettlementBillStrXML));

        

        this.tblSettlementBill.checkParsed();
        // txtqualityGuaranteRate		
        this.txtqualityGuaranteRate.setEnabled(false);
        // txtProj		
        this.txtProj.setMaxLength(80);		
        this.txtProj.setVisible(true);		
        this.txtProj.setEnabled(false);		
        this.txtProj.setHorizontalAlignment(2);		
        this.txtProj.setRequired(false);
        // txtOrgUnit		
        this.txtOrgUnit.setVisible(true);		
        this.txtOrgUnit.setHorizontalAlignment(2);		
        this.txtOrgUnit.setMaxLength(100);		
        this.txtOrgUnit.setRequired(false);		
        this.txtOrgUnit.setEnabled(false);
        // cbFinalSettle
        this.cbFinalSettle.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbFinalSettle_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
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
        // tetQualityTime
        // prmtCurrency		
        this.prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.prmtCurrency.setCommitFormat("$number$");		
        this.prmtCurrency.setDisplayFormat("$name$");		
        this.prmtCurrency.setEditFormat("$number$");		
        this.prmtCurrency.setEnabled(false);
        // txtExchangeRate		
        this.txtExchangeRate.setDataType(1);
        // txtOriginalAmount
        this.txtOriginalAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                try {
                    txtOriginalAmount_focusGained(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtTotalOriginalAmount		
        this.txtTotalOriginalAmount.setEnabled(false);		
        this.txtTotalOriginalAmount.setDataType(1);
        // txtTotalSettlePrice		
        this.txtTotalSettlePrice.setEnabled(false);		
        this.txtTotalSettlePrice.setDataType(1);
        // txtGuaranceAmt
        // cmbAttchment
        // txtConstructPrice		
        this.txtConstructPrice.setDataType(1);		
        this.txtConstructPrice.setRequired(true);
        // txtORGAuditCost
        // txtCostAuditCost
        // prmttpartB		
        this.prmttpartB.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");		
        this.prmttpartB.setVisible(true);		
        this.prmttpartB.setEditable(true);		
        this.prmttpartB.setDisplayFormat("$number$ $name$");		
        this.prmttpartB.setEditFormat("$number$");		
        this.prmttpartB.setCommitFormat("$number$");		
        this.prmttpartB.setRequired(true);		
        this.prmttpartB.setEnabled(false);
        // txtContractOriginalAmount		
        this.txtContractOriginalAmount.setEnabled(false);		
        this.txtContractOriginalAmount.setDataType(1);		
        this.txtContractOriginalAmount.setPrecision(2);
        // txtContractAmount		
        this.txtContractAmount.setEnabled(false);		
        this.txtContractAmount.setDataType(1);		
        this.txtContractAmount.setPrecision(2);
        // txtLastPrice		
        this.txtLastPrice.setEnabled(false);		
        this.txtLastPrice.setDataType(1);		
        this.txtLastPrice.setPrecision(2);
        // txtPayAmount		
        this.txtPayAmount.setEnabled(false);		
        this.txtPayAmount.setDataType(1);		
        this.txtPayAmount.setPrecision(2);
        // txtChangeAmount		
        this.txtChangeAmount.setEnabled(false);		
        this.txtChangeAmount.setDataType(1);		
        this.txtChangeAmount.setPrecision(2);
        // txtDeduct		
        this.txtDeduct.setEnabled(false);		
        this.txtDeduct.setDataType(1);		
        this.txtDeduct.setPrecision(2);
        // txtToReward		
        this.txtToReward.setEnabled(false);		
        this.txtToReward.setDataType(1);		
        this.txtToReward.setPrecision(2);
        // txtJGC		
        this.txtJGC.setDataType(1);		
        this.txtJGC.setPrecision(2);
        // txtGJ		
        this.txtGJ.setVisible(true);		
        this.txtGJ.setHorizontalAlignment(2);		
        this.txtGJ.setMaxLength(100);		
        this.txtGJ.setRequired(false);
        // btnSplit
        this.btnSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplit.setText(resHelper.getString("btnSplit.text"));		
        this.btnSplit.setToolTipText(resHelper.getString("btnSplit.toolTipText"));		
        this.btnSplit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_split"));
        // menuItemSplit
        this.menuItemSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplit.setText(resHelper.getString("menuItemSplit.text"));		
        this.menuItemSplit.setMnemonic(70);		
        this.menuItemSplit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_split"));
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
        this.setBounds(new Rectangle(0, 0, 1013, 649));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 649));
        kDSeparator6.setBounds(new Rectangle(53, 63, 953, 10));
        this.add(kDSeparator6, new KDLayout.Constraints(53, 63, 953, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(9, 32, 243, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(9, 32, 243, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(573, 595, 430, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(573, 595, 430, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer5.setBounds(new Rectangle(12, 619, 430, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(12, 619, 430, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(9, 239, 633, 45));
        this.add(kDLabelContainer6, new KDLayout.Constraints(9, 239, 633, 45, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(12, 595, 430, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(12, 595, 430, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbillName.setBounds(new Rectangle(259, 32, 243, 19));
        this.add(contbillName, new KDLayout.Constraints(259, 32, 243, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsettlePrice.setBounds(new Rectangle(732, 172, 270, 19));
        this.add(contsettlePrice, new KDLayout.Constraints(732, 172, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contbuildArea.setBounds(new Rectangle(466, 594, 270, 19));
        this.add(contbuildArea, new KDLayout.Constraints(466, 594, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contgetFeeCriteria.setBounds(new Rectangle(446, 632, 270, 19));
        this.add(contgetFeeCriteria, new KDLayout.Constraints(446, 632, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contunitPrice.setBounds(new Rectangle(465, 616, 270, 19));
        this.add(contunitPrice, new KDLayout.Constraints(465, 616, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        continfoPrice.setBounds(new Rectangle(387, 629, 270, 19));
        this.add(continfoPrice, new KDLayout.Constraints(387, 629, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contqualityGuarante.setBounds(new Rectangle(735, 289, 270, 19));
        this.add(contqualityGuarante, new KDLayout.Constraints(735, 289, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contauditTime.setBounds(new Rectangle(573, 619, 430, 19));
        this.add(contauditTime, new KDLayout.Constraints(573, 619, 430, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer9.setBounds(new Rectangle(9, 72, 270, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(9, 72, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer10.setBounds(new Rectangle(370, 71, 270, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(370, 71, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDetailInfo.setBounds(new Rectangle(9, 292, 994, 293));
        this.add(contDetailInfo, new KDLayout.Constraints(9, 292, 994, 293, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer11.setBounds(new Rectangle(732, 235, 270, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(732, 235, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contProj.setBounds(new Rectangle(572, 10, 430, 19));
        this.add(contProj, new KDLayout.Constraints(572, 10, 430, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgUnit.setBounds(new Rectangle(9, 10, 430, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(9, 10, 430, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(732, 257, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(732, 257, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer3.setBounds(new Rectangle(509, 32, 243, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(509, 32, 243, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer8.setBounds(new Rectangle(759, 32, 243, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(759, 32, 243, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer12.setBounds(new Rectangle(427, 300, 270, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(427, 300, 270, 19, 0));
        kDLabelContainer13.setBounds(new Rectangle(9, 172, 133, 19));
        this.add(kDLabelContainer13, new KDLayout.Constraints(9, 172, 133, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer14.setBounds(new Rectangle(146, 172, 133, 19));
        this.add(kDLabelContainer14, new KDLayout.Constraints(146, 172, 133, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer15.setBounds(new Rectangle(370, 172, 270, 19));
        this.add(kDLabelContainer15, new KDLayout.Constraints(370, 172, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer16.setBounds(new Rectangle(733, 281, 270, 19));
        this.add(kDLabelContainer16, new KDLayout.Constraints(733, 281, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer17.setBounds(new Rectangle(727, 289, 270, 19));
        this.add(kDLabelContainer17, new KDLayout.Constraints(727, 289, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contGuaranceAmt.setBounds(new Rectangle(732, 213, 269, 19));
        this.add(contGuaranceAmt, new KDLayout.Constraints(732, 213, 269, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        lblAttachmentContainer.setBounds(new Rectangle(9, 217, 531, 19));
        this.add(lblAttachmentContainer, new KDLayout.Constraints(9, 217, 531, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        viewAttachment.setBounds(new Rectangle(552, 217, 83, 21));
        this.add(viewAttachment, new KDLayout.Constraints(552, 217, 83, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contConstructPrice.setBounds(new Rectangle(9, 150, 270, 19));
        this.add(contConstructPrice, new KDLayout.Constraints(9, 150, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrgAuditCost.setBounds(new Rectangle(370, 150, 270, 19));
        this.add(contOrgAuditCost, new KDLayout.Constraints(370, 150, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostDertCost.setBounds(new Rectangle(732, 150, 270, 19));
        this.add(contCostDertCost, new KDLayout.Constraints(732, 150, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabel1.setBounds(new Rectangle(714, 321, 79, 19));
        this.add(kDLabel1, new KDLayout.Constraints(714, 321, 79, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAType.setBounds(new Rectangle(793, 322, 40, 19));
        this.add(btnAType, new KDLayout.Constraints(793, 322, 40, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnBType.setBounds(new Rectangle(829, 322, 45, 19));
        this.add(btnBType, new KDLayout.Constraints(829, 322, 45, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        BtnCType.setBounds(new Rectangle(873, 319, 44, 19));
        this.add(BtnCType, new KDLayout.Constraints(873, 319, 44, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel2.setBounds(new Rectangle(5, 55, 50, 19));
        this.add(kDLabel2, new KDLayout.Constraints(5, 55, 50, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contpartB.setBounds(new Rectangle(732, 71, 270, 19));
        this.add(contpartB, new KDLayout.Constraints(732, 71, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contContractOriginalAmount.setBounds(new Rectangle(9, 94, 270, 19));
        this.add(contContractOriginalAmount, new KDLayout.Constraints(9, 94, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractAmount.setBounds(new Rectangle(370, 94, 270, 19));
        this.add(contContractAmount, new KDLayout.Constraints(370, 94, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastPrice.setBounds(new Rectangle(732, 94, 270, 19));
        this.add(contLastPrice, new KDLayout.Constraints(732, 94, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPayAmount.setBounds(new Rectangle(9, 116, 270, 19));
        this.add(contPayAmount, new KDLayout.Constraints(9, 116, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChangeAmount.setBounds(new Rectangle(370, 116, 270, 19));
        this.add(contChangeAmount, new KDLayout.Constraints(370, 116, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel3.setBounds(new Rectangle(5, 136, 50, 19));
        this.add(kDLabel3, new KDLayout.Constraints(5, 136, 50, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDSeparator8.setBounds(new Rectangle(53, 144, 953, 10));
        this.add(kDSeparator8, new KDLayout.Constraints(53, 144, 953, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDeduct.setBounds(new Rectangle(732, 116, 133, 19));
        this.add(contDeduct, new KDLayout.Constraints(732, 116, 133, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contToReward.setBounds(new Rectangle(869, 116, 133, 19));
        this.add(contToReward, new KDLayout.Constraints(869, 116, 133, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contJGC.setBounds(new Rectangle(7, 195, 270, 19));
        this.add(contJGC, new KDLayout.Constraints(7, 195, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contGJ.setBounds(new Rectangle(370, 195, 270, 19));
        this.add(contGJ, new KDLayout.Constraints(370, 195, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(dateCreateTime);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(bizPromptAuditor);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtDescription);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(bizPromptCreator);
        //contbillName
        contbillName.setBoundEditor(txtbillName);
        //contsettlePrice
        contsettlePrice.setBoundEditor(txtSettlePrice);
        //contbuildArea
        contbuildArea.setBoundEditor(txtbuildArea);
        //contgetFeeCriteria
        contgetFeeCriteria.setBoundEditor(txtgetFeeCriteria);
        //contunitPrice
        contunitPrice.setBoundEditor(txtunitPrice);
        //continfoPrice
        continfoPrice.setBoundEditor(txtinfoPrice);
        //contqualityGuarante
        contqualityGuarante.setBoundEditor(txtqualityGuarante);
        //contauditTime
        contauditTime.setBoundEditor(pkauditTime);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtcontractNumber);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(txtcontractName);
        //contDetailInfo
        contDetailInfo.getContentPane().setLayout(new KDLayout());
        contDetailInfo.getContentPane().putClientProperty("OriginalBounds", new Rectangle(9, 292, 994, 293));        tabTop.setBounds(new Rectangle(0, 0, 971, 290));
        contDetailInfo.getContentPane().add(tabTop, new KDLayout.Constraints(0, 0, 971, 290, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //tabTop
        tabTop.add(panelCollection, resHelper.getString("panelCollection.constraints"));
        tabTop.add(panelCompensation, resHelper.getString("panelCompensation.constraints"));
        tabTop.add(panelDeduct, resHelper.getString("panelDeduct.constraints"));
        tabTop.add(panelGuerdon, resHelper.getString("panelGuerdon.constraints"));
        tabTop.add(panelSettlementbill, resHelper.getString("panelSettlementbill.constraints"));
panelCollection.setLayout(new BorderLayout(0, 0));        //panelCompensation
panelCompensation.setLayout(new BorderLayout(0, 0));        panelCompensation.add(tblCompensationBill, BorderLayout.CENTER);
        //panelDeduct
panelDeduct.setLayout(new BorderLayout(0, 0));        panelDeduct.add(tblDeduct, BorderLayout.CENTER);
        //panelGuerdon
panelGuerdon.setLayout(new BorderLayout(0, 0));        panelGuerdon.add(tblGuerdon, BorderLayout.CENTER);
        //panelSettlementbill
panelSettlementbill.setLayout(new BorderLayout(0, 0));        panelSettlementbill.add(kDSeparator5, BorderLayout.SOUTH);
        panelSettlementbill.add(tblSettlementBill, BorderLayout.CENTER);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(txtqualityGuaranteRate);
        //contProj
        contProj.setBoundEditor(txtProj);
        //contOrgUnit
        contOrgUnit.setBoundEditor(txtOrgUnit);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(cbFinalSettle);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(pkbookedDate);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(cbPeriod);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(tetQualityTime);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(prmtCurrency);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(txtExchangeRate);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(txtOriginalAmount);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(txtTotalOriginalAmount);
        //kDLabelContainer17
        kDLabelContainer17.setBoundEditor(txtTotalSettlePrice);
        //contGuaranceAmt
        contGuaranceAmt.setBoundEditor(txtGuaranceAmt);
        //lblAttachmentContainer
        lblAttachmentContainer.setBoundEditor(cmbAttchment);
        //contConstructPrice
        contConstructPrice.setBoundEditor(txtConstructPrice);
        //contOrgAuditCost
        contOrgAuditCost.setBoundEditor(txtORGAuditCost);
        //contCostDertCost
        contCostDertCost.setBoundEditor(txtCostAuditCost);
        //contpartB
        contpartB.setBoundEditor(prmttpartB);
        //contContractOriginalAmount
        contContractOriginalAmount.setBoundEditor(txtContractOriginalAmount);
        //contContractAmount
        contContractAmount.setBoundEditor(txtContractAmount);
        //contLastPrice
        contLastPrice.setBoundEditor(txtLastPrice);
        //contPayAmount
        contPayAmount.setBoundEditor(txtPayAmount);
        //contChangeAmount
        contChangeAmount.setBoundEditor(txtChangeAmount);
        //contDeduct
        contDeduct.setBoundEditor(txtDeduct);
        //contToReward
        contToReward.setBoundEditor(txtToReward);
        //contJGC
        contJGC.setBoundEditor(txtJGC);
        //contGJ
        contGJ.setBoundEditor(txtGJ);

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
        menuBiz.add(kDSeparator7);
        menuBiz.add(menuItemSplit);
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
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
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
        this.toolBar.add(btnSplit);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.dateCreateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptAuditor, "data");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "_multiLangItem");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptCreator, "data");
		dataBinder.registerBinding("name", String.class, this.txtbillName, "text");
		dataBinder.registerBinding("curSettlePrice", java.math.BigDecimal.class, this.txtSettlePrice, "value");
		dataBinder.registerBinding("buildArea", java.math.BigDecimal.class, this.txtbuildArea, "value");
		dataBinder.registerBinding("getFeeCriteria", String.class, this.txtgetFeeCriteria, "text");
		dataBinder.registerBinding("unitPrice", java.math.BigDecimal.class, this.txtunitPrice, "value");
		dataBinder.registerBinding("infoPrice", String.class, this.txtinfoPrice, "text");
		dataBinder.registerBinding("qualityGuarante", java.math.BigDecimal.class, this.txtqualityGuarante, "value");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkauditTime, "value");
		dataBinder.registerBinding("contractBill.number", String.class, this.txtcontractNumber, "text");
		dataBinder.registerBinding("contractBill.name", String.class, this.txtcontractName, "text");
		dataBinder.registerBinding("qualityGuaranteRate", java.math.BigDecimal.class, this.txtqualityGuaranteRate, "value");
		dataBinder.registerBinding("isFinalSettle", com.kingdee.eas.base.commonquery.BooleanEnum.class, this.cbFinalSettle, "selectedItem");
		dataBinder.registerBinding("bookedDate", java.util.Date.class, this.pkbookedDate, "value");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.cbPeriod, "data");
		dataBinder.registerBinding("qualityTime", String.class, this.tetQualityTime, "text");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtCurrency, "data");
		dataBinder.registerBinding("exchangeRate", java.math.BigDecimal.class, this.txtExchangeRate, "value");
		dataBinder.registerBinding("curOriginalAmount", java.math.BigDecimal.class, this.txtOriginalAmount, "value");
		dataBinder.registerBinding("totalOriginalAmount", java.math.BigDecimal.class, this.txtTotalOriginalAmount, "value");
		dataBinder.registerBinding("totalSettlePrice", java.math.BigDecimal.class, this.txtTotalSettlePrice, "value");
		dataBinder.registerBinding("guaranteAmt", java.math.BigDecimal.class, this.txtGuaranceAmt, "value");
		dataBinder.registerBinding("constructPrice", java.math.BigDecimal.class, this.txtConstructPrice, "value");
		dataBinder.registerBinding("orgAuditCost", java.math.BigDecimal.class, this.txtORGAuditCost, "text");
		dataBinder.registerBinding("costDeptAuditCost", java.math.BigDecimal.class, this.txtCostAuditCost, "text");
		dataBinder.registerBinding("contractBill.partB", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmttpartB, "data");
		dataBinder.registerBinding("contractBill.originalAmount", java.math.BigDecimal.class, this.txtContractOriginalAmount, "value");
		dataBinder.registerBinding("contractBill.amount", java.math.BigDecimal.class, this.txtContractAmount, "value");
		dataBinder.registerBinding("JGC", java.math.BigDecimal.class, this.txtJGC, "value");
		dataBinder.registerBinding("GJ", String.class, this.txtGJ, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractSettlementBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.ContractSettlementBillInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curSettlePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("getFeeCriteria", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unitPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("infoPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qualityGuarante", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qualityGuaranteRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isFinalSettle", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qualityTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curOriginalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalOriginalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalSettlePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("guaranteAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("constructPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgAuditCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costDeptAuditCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.partB", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("JGC", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("GJ", ValidateHelper.ON_SAVE);    		
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
        }
    }

    /**
     * output txtSettlePrice_dataChanged method
     */
    protected void txtSettlePrice_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtSettlePrice_focusLost method
     */
    protected void txtSettlePrice_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtbuildArea_dataChanged method
     */
    protected void txtbuildArea_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output cbFinalSettle_itemStateChanged method
     */
    protected void cbFinalSettle_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output pkbookedDate_dataChanged method
     */
    protected void pkbookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtOriginalAmount_focusGained method
     */
    protected void txtOriginalAmount_focusGained(java.awt.event.FocusEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("curSettlePrice"));
        sic.add(new SelectorItemInfo("buildArea"));
        sic.add(new SelectorItemInfo("getFeeCriteria"));
        sic.add(new SelectorItemInfo("unitPrice"));
        sic.add(new SelectorItemInfo("infoPrice"));
        sic.add(new SelectorItemInfo("qualityGuarante"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("contractBill.number"));
        sic.add(new SelectorItemInfo("contractBill.name"));
        sic.add(new SelectorItemInfo("qualityGuaranteRate"));
        sic.add(new SelectorItemInfo("isFinalSettle"));
        sic.add(new SelectorItemInfo("bookedDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("period.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("period.id"));
        	sic.add(new SelectorItemInfo("period.number"));
		}
        sic.add(new SelectorItemInfo("qualityTime"));
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
        sic.add(new SelectorItemInfo("curOriginalAmount"));
        sic.add(new SelectorItemInfo("totalOriginalAmount"));
        sic.add(new SelectorItemInfo("totalSettlePrice"));
        sic.add(new SelectorItemInfo("guaranteAmt"));
        sic.add(new SelectorItemInfo("constructPrice"));
        sic.add(new SelectorItemInfo("orgAuditCost"));
        sic.add(new SelectorItemInfo("costDeptAuditCost"));
        sic.add(new SelectorItemInfo("contractBill.partB"));
        sic.add(new SelectorItemInfo("contractBill.originalAmount"));
        sic.add(new SelectorItemInfo("contractBill.amount"));
        sic.add(new SelectorItemInfo("JGC"));
        sic.add(new SelectorItemInfo("GJ"));
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
     * output actionCopy_actionPerformed method
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }
    	

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
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
     * output actionSplit_actionPerformed method
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionCopy(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCopy(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCopy() {
    	return false;
    }
	public RequestContext prepareActionAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAddNew(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNew() {
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
            innerActionPerformed("eas", AbstractContractSettlementBillEditUI.this, "ActionSplit", "actionSplit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractSettlementBillEditUI.this, "ActionViewAttachment", "actionViewAttachment_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractSettlementBillEditUI");
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
        return com.kingdee.eas.fdc.contract.client.ContractSettlementBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.ContractSettlementBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.ContractSettlementBillInfo objectValue = new com.kingdee.eas.fdc.contract.ContractSettlementBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/contract/ContractSettlementBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractSettlementBillQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return tblCompensationBill;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
				vo.put("qualityGuaranteRate",new java.math.BigDecimal(0));
		vo.put("exchangeRate",new java.math.BigDecimal(1));
		vo.put("originalAmount",new java.math.BigDecimal(0));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}