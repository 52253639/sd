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
public abstract class AbstractContractBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane3;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel mainPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel ecoItemPanel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlandDeveloper;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractPropert;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpartB;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpartC;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractName;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contExRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLocalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGrtAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProj;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGrtRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsPartAMaterialCon;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conContrarctRule;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgAmtBig;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmtBig;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateOrg;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup btnSettelGroup;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDes;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRespDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsignDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractSource;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractWFType;
    protected com.kingdee.bos.ctrl.swing.KDContainer contAttachment;
    protected com.kingdee.bos.ctrl.swing.KDContainer contMode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSrcAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNeedPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNeedDept;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtamount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtlandDeveloper;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcontractType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox contractPropert;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpartB;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpartC;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcontractName;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlInviteInfo;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane pnlDetail;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlCost;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelInvite;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSupplyEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCoopLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriceType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsSubMainContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conMainContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conEffectiveStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conEffectiveEndDate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInformation;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlowestPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlowerPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthigherPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmiddlePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthighestPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbasePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsecondPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contwinPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contwinUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfileNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contquantity;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtlowestPriceUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtlowerPriceUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtmiddlePriceUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmthigherPriceUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmthighestPriceUni;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblOverRateContainer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayPercForWarn;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchgPercForWarn;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcostProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStampTaxRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStampTaxAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayScale;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFwContractTemp;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttachmentNameList;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conChargeType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCoopLevel;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPriceType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMainContract;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpEffectStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpEffectiveEndDate;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtInformation;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtlowestPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtlowerPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txthigherPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtmiddlePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txthighestPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtbasePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtsecondPrice;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinviteType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtwinPrice;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtwinUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfileNo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtquantity;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOverAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtpayPercForWarn;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtchgPercForWarn;
    protected com.kingdee.bos.ctrl.swing.KDComboBox costProperty;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStampTaxRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStampTaxAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPayScale;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboAttachmentNameList;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCharge;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblDetail;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCost;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblInvite;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtExRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLocalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtGrtAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCurrency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCreator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProj;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtGrtRate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox cbPeriod;
    protected com.kingdee.bos.ctrl.swing.KDTextField textFwContract;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrgAmtBig;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAmtBig;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbookedDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreateOrg;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDes;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsOpen;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsStandardContract;
    protected com.kingdee.bos.ctrl.swing.KDLabel labSettleType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnAType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnBType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton BtnCType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsPartAMaterialCon;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkCostSplit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRespDept;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pksignDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbOrgType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox contractSource;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractWFType;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAttachement;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtModel;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSrcAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtNeedPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtNeedDept;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer contPayItem;
    protected com.kingdee.bos.ctrl.swing.KDContainer contBailItem;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblEconItem;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBailOriAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBailRate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBail;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBailOriAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBailRate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProgram;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAccountView;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelSplit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContent;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnContractPlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewCost;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewBgBalance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewProgramContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAgreement;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewContent;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBgBalance;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewProg;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDelSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemContractPayPlan;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem enuItemViewCost;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemProgram;
    protected com.kingdee.eas.fdc.contract.ContractBillInfo editData = null;
    protected ActionSplit actionSplit = null;
    protected ActionViewContent actionViewContent = null;
    protected ActionContractPlan actionContractPlan = null;
    protected ActionDelSplit actionDelSplit = null;
    protected ActionViewCost actionViewCost = null;
    protected ActionViewBgBalance actionViewBgBalance = null;
    protected ActionViewAttachmentSelf actionViewAttachmentSelf = null;
    protected ActionViewContentSelf actionViewContentSelf = null;
    protected ActionProgram actionProgram = null;
    protected ActionViewProgramCon actionViewProgramCon = null;
    protected ActionAgreementText actionAgreementText = null;
    protected ActionUpLoad actionUpLoad = null;
    protected ActionDownLoad actionDownLoad = null;
    protected ActionAccountView actionAccountView = null;
    /**
     * output class constructor
     */
    public AbstractContractBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractBillEditUI.class.getName());
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
        //actionSplit
        this.actionSplit = new ActionSplit(this);
        getActionManager().registerAction("actionSplit", actionSplit);
        this.actionSplit.setBindWorkFlow(true);
         this.actionSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContent
        this.actionViewContent = new ActionViewContent(this);
        getActionManager().registerAction("actionViewContent", actionViewContent);
         this.actionViewContent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionContractPlan
        this.actionContractPlan = new ActionContractPlan(this);
        getActionManager().registerAction("actionContractPlan", actionContractPlan);
         this.actionContractPlan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelSplit
        this.actionDelSplit = new ActionDelSplit(this);
        getActionManager().registerAction("actionDelSplit", actionDelSplit);
         this.actionDelSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewCost
        this.actionViewCost = new ActionViewCost(this);
        getActionManager().registerAction("actionViewCost", actionViewCost);
         this.actionViewCost.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBgBalance
        this.actionViewBgBalance = new ActionViewBgBalance(this);
        getActionManager().registerAction("actionViewBgBalance", actionViewBgBalance);
         this.actionViewBgBalance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAttachmentSelf
        this.actionViewAttachmentSelf = new ActionViewAttachmentSelf(this);
        getActionManager().registerAction("actionViewAttachmentSelf", actionViewAttachmentSelf);
         this.actionViewAttachmentSelf.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContentSelf
        this.actionViewContentSelf = new ActionViewContentSelf(this);
        getActionManager().registerAction("actionViewContentSelf", actionViewContentSelf);
         this.actionViewContentSelf.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProgram
        this.actionProgram = new ActionProgram(this);
        getActionManager().registerAction("actionProgram", actionProgram);
         this.actionProgram.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewProgramCon
        this.actionViewProgramCon = new ActionViewProgramCon(this);
        getActionManager().registerAction("actionViewProgramCon", actionViewProgramCon);
         this.actionViewProgramCon.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAgreementText
        this.actionAgreementText = new ActionAgreementText(this);
        getActionManager().registerAction("actionAgreementText", actionAgreementText);
         this.actionAgreementText.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpLoad
        this.actionUpLoad = new ActionUpLoad(this);
        getActionManager().registerAction("actionUpLoad", actionUpLoad);
         this.actionUpLoad.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDownLoad
        this.actionDownLoad = new ActionDownLoad(this);
        getActionManager().registerAction("actionDownLoad", actionDownLoad);
         this.actionDownLoad.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAccountView
        this.actionAccountView = new ActionAccountView(this);
        getActionManager().registerAction("actionAccountView", actionAccountView);
         this.actionAccountView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDScrollPane3 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.tabPanel = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.mainPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.ecoItemPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlandDeveloper = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractPropert = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpartB = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpartC = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contExRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLocalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGrtAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRespPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProj = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGrtRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIsPartAMaterialCon = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conContrarctRule = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgAmtBig = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmtBig = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSettelGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.contDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contRespDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsignDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractSource = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractWFType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttachment = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contMode = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contSrcAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNeedPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNeedDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtamount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtlandDeveloper = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcontractType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contractPropert = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtpartB = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtpartC = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcontractName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pnlInviteInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlDetail = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.pnlCost = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelInvite = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtSupplyEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCoopLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPriceType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsSubMainContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.conMainContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conEffectiveStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conEffectiveEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.conInformation = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlowestPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlowerPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthigherPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmiddlePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthighestPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbasePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsecondPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contwinPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contwinUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfileNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contquantity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtlowestPriceUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtlowerPriceUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtmiddlePriceUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmthigherPriceUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmthighestPriceUni = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.lblPrice = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblUnit = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblOverRateContainer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayPercForWarn = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchgPercForWarn = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcostProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStampTaxRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStampTaxAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayScale = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtFwContractTemp = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contAttachmentNameList = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.conChargeType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboCoopLevel = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboPriceType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtMainContract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdpEffectStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdpEffectiveEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtInformation = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtlowestPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtlowerPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txthigherPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtmiddlePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txthighestPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtbasePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtsecondPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtinviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtwinPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtwinUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtfileNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtquantity = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOverAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtpayPercForWarn = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtchgPercForWarn = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.costProperty = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtStampTaxRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStampTaxAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPayScale = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboAttachmentNameList = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtCharge = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblDetail = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblCost = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblInvite = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtExRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLocalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtGrtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboCurrency = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtRespPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtCreator = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProj = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtGrtRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.cbPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.textFwContract = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOrgAmtBig = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAmtBig = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkbookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCreateOrg = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDes = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.chkIsOpen = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsStandardContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.labSettleType = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.btnAType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnBType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.BtnCType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.chkIsPartAMaterialCon = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkCostSplit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtRespDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pksignDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbOrgType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contractSource = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtContractWFType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblAttachement = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSrcAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtInviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtNeedPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtNeedDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.contPayItem = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contBailItem = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblEconItem = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contBailOriAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBailRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tblBail = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtBailOriAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBailRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnProgram = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAccountView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContent = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnContractPlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewCost = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewBgBalance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewProgramContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAgreement = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewContent = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewBgBalance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewProg = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDelSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemContractPayPlan = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.enuItemViewCost = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemProgram = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDScrollPane3.setName("kDScrollPane3");
        this.tabPanel.setName("tabPanel");
        this.mainPanel.setName("mainPanel");
        this.ecoItemPanel.setName("ecoItemPanel");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contamount.setName("contamount");
        this.contlandDeveloper.setName("contlandDeveloper");
        this.contcontractType.setName("contcontractType");
        this.contcontractPropert.setName("contcontractPropert");
        this.contpartB.setName("contpartB");
        this.contpartC.setName("contpartC");
        this.contcontractName.setName("contcontractName");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contExRate.setName("contExRate");
        this.contLocalAmount.setName("contLocalAmount");
        this.contGrtAmount.setName("contGrtAmount");
        this.contCurrency.setName("contCurrency");
        this.contRespPerson.setName("contRespPerson");
        this.contCreator.setName("contCreator");
        this.contOrg.setName("contOrg");
        this.contProj.setName("contProj");
        this.contGrtRate.setName("contGrtRate");
        this.contIsPartAMaterialCon.setName("contIsPartAMaterialCon");
        this.conContrarctRule.setName("conContrarctRule");
        this.contOrgAmtBig.setName("contOrgAmtBig");
        this.contAmtBig.setName("contAmtBig");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contCreateOrg.setName("contCreateOrg");
        this.contDes.setName("contDes");
        this.kDPanel1.setName("kDPanel1");
        this.contRespDept.setName("contRespDept");
        this.contsignDate.setName("contsignDate");
        this.contOrgType.setName("contOrgType");
        this.contcontractSource.setName("contcontractSource");
        this.contContractWFType.setName("contContractWFType");
        this.contAttachment.setName("contAttachment");
        this.contMode.setName("contMode");
        this.contSrcAmount.setName("contSrcAmount");
        this.contInviteType.setName("contInviteType");
        this.contNeedPerson.setName("contNeedPerson");
        this.contNeedDept.setName("contNeedDept");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.txtNumber.setName("txtNumber");
        this.txtamount.setName("txtamount");
        this.prmtlandDeveloper.setName("prmtlandDeveloper");
        this.prmtcontractType.setName("prmtcontractType");
        this.contractPropert.setName("contractPropert");
        this.prmtpartB.setName("prmtpartB");
        this.prmtpartC.setName("prmtpartC");
        this.txtcontractName.setName("txtcontractName");
        this.pnlInviteInfo.setName("pnlInviteInfo");
        this.pnlDetail.setName("pnlDetail");
        this.pnlCost.setName("pnlCost");
        this.panelInvite.setName("panelInvite");
        this.kdtSupplyEntry.setName("kdtSupplyEntry");
        this.contRemark.setName("contRemark");
        this.contCoopLevel.setName("contCoopLevel");
        this.contPriceType.setName("contPriceType");
        this.chkIsSubMainContract.setName("chkIsSubMainContract");
        this.conMainContract.setName("conMainContract");
        this.conEffectiveStartDate.setName("conEffectiveStartDate");
        this.conEffectiveEndDate.setName("conEffectiveEndDate");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.conInformation.setName("conInformation");
        this.contlowestPrice.setName("contlowestPrice");
        this.contlowerPrice.setName("contlowerPrice");
        this.conthigherPrice.setName("conthigherPrice");
        this.contmiddlePrice.setName("contmiddlePrice");
        this.conthighestPrice.setName("conthighestPrice");
        this.contbasePrice.setName("contbasePrice");
        this.contsecondPrice.setName("contsecondPrice");
        this.continviteType.setName("continviteType");
        this.contwinPrice.setName("contwinPrice");
        this.contwinUnit.setName("contwinUnit");
        this.contfileNo.setName("contfileNo");
        this.contquantity.setName("contquantity");
        this.prmtlowestPriceUnit.setName("prmtlowestPriceUnit");
        this.prmtlowerPriceUnit.setName("prmtlowerPriceUnit");
        this.prmtmiddlePriceUnit.setName("prmtmiddlePriceUnit");
        this.prmthigherPriceUnit.setName("prmthigherPriceUnit");
        this.prmthighestPriceUni.setName("prmthighestPriceUni");
        this.lblPrice.setName("lblPrice");
        this.lblUnit.setName("lblUnit");
        this.lblOverRateContainer.setName("lblOverRateContainer");
        this.contpayPercForWarn.setName("contpayPercForWarn");
        this.contchgPercForWarn.setName("contchgPercForWarn");
        this.contcostProperty.setName("contcostProperty");
        this.contStampTaxRate.setName("contStampTaxRate");
        this.contStampTaxAmt.setName("contStampTaxAmt");
        this.contPayScale.setName("contPayScale");
        this.prmtFwContractTemp.setName("prmtFwContractTemp");
        this.contAttachmentNameList.setName("contAttachmentNameList");
        this.btnViewAttachment.setName("btnViewAttachment");
        this.conChargeType.setName("conChargeType");
        this.txtRemark.setName("txtRemark");
        this.comboCoopLevel.setName("comboCoopLevel");
        this.comboPriceType.setName("comboPriceType");
        this.prmtMainContract.setName("prmtMainContract");
        this.kdpEffectStartDate.setName("kdpEffectStartDate");
        this.kdpEffectiveEndDate.setName("kdpEffectiveEndDate");
        this.txtInformation.setName("txtInformation");
        this.txtlowestPrice.setName("txtlowestPrice");
        this.txtlowerPrice.setName("txtlowerPrice");
        this.txthigherPrice.setName("txthigherPrice");
        this.txtmiddlePrice.setName("txtmiddlePrice");
        this.txthighestPrice.setName("txthighestPrice");
        this.txtbasePrice.setName("txtbasePrice");
        this.txtsecondPrice.setName("txtsecondPrice");
        this.prmtinviteType.setName("prmtinviteType");
        this.txtwinPrice.setName("txtwinPrice");
        this.prmtwinUnit.setName("prmtwinUnit");
        this.txtfileNo.setName("txtfileNo");
        this.txtquantity.setName("txtquantity");
        this.txtOverAmt.setName("txtOverAmt");
        this.txtpayPercForWarn.setName("txtpayPercForWarn");
        this.txtchgPercForWarn.setName("txtchgPercForWarn");
        this.costProperty.setName("costProperty");
        this.txtStampTaxRate.setName("txtStampTaxRate");
        this.txtStampTaxAmt.setName("txtStampTaxAmt");
        this.txtPayScale.setName("txtPayScale");
        this.comboAttachmentNameList.setName("comboAttachmentNameList");
        this.prmtCharge.setName("prmtCharge");
        this.tblDetail.setName("tblDetail");
        this.tblCost.setName("tblCost");
        this.tblInvite.setName("tblInvite");
        this.txtExRate.setName("txtExRate");
        this.txtLocalAmount.setName("txtLocalAmount");
        this.txtGrtAmount.setName("txtGrtAmount");
        this.comboCurrency.setName("comboCurrency");
        this.prmtRespPerson.setName("prmtRespPerson");
        this.txtCreator.setName("txtCreator");
        this.txtOrg.setName("txtOrg");
        this.txtProj.setName("txtProj");
        this.txtGrtRate.setName("txtGrtRate");
        this.cbPeriod.setName("cbPeriod");
        this.textFwContract.setName("textFwContract");
        this.txtOrgAmtBig.setName("txtOrgAmtBig");
        this.txtAmtBig.setName("txtAmtBig");
        this.pkbookedDate.setName("pkbookedDate");
        this.prmtCreateOrg.setName("prmtCreateOrg");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtDes.setName("txtDes");
        this.chkIsOpen.setName("chkIsOpen");
        this.chkIsStandardContract.setName("chkIsStandardContract");
        this.labSettleType.setName("labSettleType");
        this.btnAType.setName("btnAType");
        this.btnBType.setName("btnBType");
        this.BtnCType.setName("BtnCType");
        this.chkIsPartAMaterialCon.setName("chkIsPartAMaterialCon");
        this.chkCostSplit.setName("chkCostSplit");
        this.prmtRespDept.setName("prmtRespDept");
        this.pksignDate.setName("pksignDate");
        this.cbOrgType.setName("cbOrgType");
        this.contractSource.setName("contractSource");
        this.prmtContractWFType.setName("prmtContractWFType");
        this.tblAttachement.setName("tblAttachement");
        this.prmtModel.setName("prmtModel");
        this.txtSrcAmount.setName("txtSrcAmount");
        this.prmtInviteType.setName("prmtInviteType");
        this.prmtNeedPerson.setName("prmtNeedPerson");
        this.prmtNeedDept.setName("prmtNeedDept");
        this.kDContainer1.setName("kDContainer1");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.contPayItem.setName("contPayItem");
        this.contBailItem.setName("contBailItem");
        this.tblEconItem.setName("tblEconItem");
        this.contBailOriAmount.setName("contBailOriAmount");
        this.contBailRate.setName("contBailRate");
        this.tblBail.setName("tblBail");
        this.txtBailOriAmount.setName("txtBailOriAmount");
        this.txtBailRate.setName("txtBailRate");
        this.btnProgram.setName("btnProgram");
        this.btnAccountView.setName("btnAccountView");
        this.btnSplit.setName("btnSplit");
        this.btnDelSplit.setName("btnDelSplit");
        this.btnViewContent.setName("btnViewContent");
        this.btnContractPlan.setName("btnContractPlan");
        this.btnViewCost.setName("btnViewCost");
        this.btnViewBgBalance.setName("btnViewBgBalance");
        this.btnViewProgramContract.setName("btnViewProgramContract");
        this.btnAgreement.setName("btnAgreement");
        this.menuItemViewContent.setName("menuItemViewContent");
        this.menuItemViewBgBalance.setName("menuItemViewBgBalance");
        this.menuItemViewProg.setName("menuItemViewProg");
        this.menuItemSplit.setName("menuItemSplit");
        this.menuItemDelSplit.setName("menuItemDelSplit");
        this.menuItemContractPayPlan.setName("menuItemContractPayPlan");
        this.enuItemViewCost.setName("enuItemViewCost");
        this.menuItemProgram.setName("menuItemProgram");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,650));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));		
        this.btnCopy.setVisible(false);		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuSubmitOption.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnCopyFrom.setVisible(false);		
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
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));		
        this.menuItemAudit.setMnemonic(65);		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setToolTipText(resHelper.getString("menuItemUnAudit.toolTipText"));		
        this.menuItemUnAudit.setMnemonic(85);
        // kDScrollPane3
        // tabPanel		
        this.tabPanel.setPreferredSize(new Dimension(1013,768));
        // mainPanel		
        this.mainPanel.setAutoscrolls(true);
        // ecoItemPanel		
        this.ecoItemPanel.setVisible(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setBoundLabelAlignment(7);		
        this.contCreateTime.setVisible(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelAlignment(7);		
        this.contNumber.setVisible(true);
        // contamount		
        this.contamount.setBoundLabelText(resHelper.getString("contamount.boundLabelText"));		
        this.contamount.setBoundLabelLength(100);		
        this.contamount.setBoundLabelUnderline(true);		
        this.contamount.setVisible(true);		
        this.contamount.setBoundLabelAlignment(7);
        // contlandDeveloper		
        this.contlandDeveloper.setBoundLabelText(resHelper.getString("contlandDeveloper.boundLabelText"));		
        this.contlandDeveloper.setBoundLabelLength(100);		
        this.contlandDeveloper.setBoundLabelUnderline(true);		
        this.contlandDeveloper.setVisible(true);		
        this.contlandDeveloper.setBoundLabelAlignment(7);
        // contcontractType		
        this.contcontractType.setBoundLabelText(resHelper.getString("contcontractType.boundLabelText"));		
        this.contcontractType.setBoundLabelLength(100);		
        this.contcontractType.setBoundLabelUnderline(true);		
        this.contcontractType.setVisible(true);		
        this.contcontractType.setBoundLabelAlignment(7);
        // contcontractPropert		
        this.contcontractPropert.setBoundLabelText(resHelper.getString("contcontractPropert.boundLabelText"));		
        this.contcontractPropert.setBoundLabelLength(100);		
        this.contcontractPropert.setBoundLabelUnderline(true);		
        this.contcontractPropert.setVisible(true);		
        this.contcontractPropert.setBoundLabelAlignment(7);
        // contpartB		
        this.contpartB.setBoundLabelText(resHelper.getString("contpartB.boundLabelText"));		
        this.contpartB.setBoundLabelLength(100);		
        this.contpartB.setBoundLabelUnderline(true);		
        this.contpartB.setVisible(true);		
        this.contpartB.setBoundLabelAlignment(7);
        // contpartC		
        this.contpartC.setBoundLabelText(resHelper.getString("contpartC.boundLabelText"));		
        this.contpartC.setBoundLabelLength(100);		
        this.contpartC.setBoundLabelUnderline(true);		
        this.contpartC.setVisible(true);		
        this.contpartC.setBoundLabelAlignment(7);
        // contcontractName		
        this.contcontractName.setBoundLabelText(resHelper.getString("contcontractName.boundLabelText"));		
        this.contcontractName.setBoundLabelLength(100);		
        this.contcontractName.setBoundLabelUnderline(true);		
        this.contcontractName.setVisible(true);		
        this.contcontractName.setBoundLabelAlignment(7);
        // kDTabbedPane1
        // contExRate		
        this.contExRate.setBoundLabelText(resHelper.getString("contExRate.boundLabelText"));		
        this.contExRate.setBoundLabelLength(100);		
        this.contExRate.setBoundLabelUnderline(true);
        // contLocalAmount		
        this.contLocalAmount.setBoundLabelText(resHelper.getString("contLocalAmount.boundLabelText"));		
        this.contLocalAmount.setBoundLabelLength(100);		
        this.contLocalAmount.setBoundLabelUnderline(true);
        // contGrtAmount		
        this.contGrtAmount.setBoundLabelText(resHelper.getString("contGrtAmount.boundLabelText"));		
        this.contGrtAmount.setBoundLabelLength(100);		
        this.contGrtAmount.setBoundLabelUnderline(true);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // contRespPerson		
        this.contRespPerson.setBoundLabelText(resHelper.getString("contRespPerson.boundLabelText"));		
        this.contRespPerson.setBoundLabelLength(100);		
        this.contRespPerson.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setBoundLabelAlignment(7);		
        this.contCreator.setVisible(true);
        // contOrg		
        this.contOrg.setBoundLabelText(resHelper.getString("contOrg.boundLabelText"));		
        this.contOrg.setBoundLabelLength(100);		
        this.contOrg.setBoundLabelUnderline(true);
        // contProj		
        this.contProj.setBoundLabelText(resHelper.getString("contProj.boundLabelText"));		
        this.contProj.setBoundLabelLength(100);		
        this.contProj.setBoundLabelUnderline(true);
        // contGrtRate		
        this.contGrtRate.setBoundLabelText(resHelper.getString("contGrtRate.boundLabelText"));		
        this.contGrtRate.setBoundLabelLength(100);		
        this.contGrtRate.setBoundLabelUnderline(true);
        // contIsPartAMaterialCon		
        this.contIsPartAMaterialCon.setBoundLabelText(resHelper.getString("contIsPartAMaterialCon.boundLabelText"));		
        this.contIsPartAMaterialCon.setBoundLabelUnderline(true);		
        this.contIsPartAMaterialCon.setBoundLabelLength(100);
        // conContrarctRule		
        this.conContrarctRule.setBoundLabelText(resHelper.getString("conContrarctRule.boundLabelText"));		
        this.conContrarctRule.setBoundLabelUnderline(true);		
        this.conContrarctRule.setBoundLabelLength(100);
        // contOrgAmtBig		
        this.contOrgAmtBig.setBoundLabelText(resHelper.getString("contOrgAmtBig.boundLabelText"));		
        this.contOrgAmtBig.setBoundLabelLength(100);		
        this.contOrgAmtBig.setBoundLabelUnderline(true);
        // contAmtBig		
        this.contAmtBig.setBoundLabelText(resHelper.getString("contAmtBig.boundLabelText"));		
        this.contAmtBig.setBoundLabelLength(100);		
        this.contAmtBig.setBoundLabelUnderline(true);		
        this.contAmtBig.setEnabled(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // contCreateOrg		
        this.contCreateOrg.setBoundLabelText(resHelper.getString("contCreateOrg.boundLabelText"));		
        this.contCreateOrg.setBoundLabelLength(100);		
        this.contCreateOrg.setBoundLabelUnderline(true);
        // btnSettelGroup
        this.btnSettelGroup.add(this.btnAType);
        this.btnSettelGroup.add(this.btnBType);
        this.btnSettelGroup.add(this.BtnCType);
        // contDes		
        this.contDes.setBoundLabelText(resHelper.getString("contDes.boundLabelText"));		
        this.contDes.setBoundLabelLength(100);		
        this.contDes.setBoundLabelUnderline(true);
        // kDPanel1		
        this.kDPanel1.setBorder(BorderFactory.createLineBorder(new Color(192,192,192),1));
        // contRespDept		
        this.contRespDept.setBoundLabelText(resHelper.getString("contRespDept.boundLabelText"));		
        this.contRespDept.setBoundLabelLength(100);		
        this.contRespDept.setBoundLabelUnderline(true);
        // contsignDate		
        this.contsignDate.setBoundLabelText(resHelper.getString("contsignDate.boundLabelText"));		
        this.contsignDate.setBoundLabelLength(100);		
        this.contsignDate.setBoundLabelUnderline(true);		
        this.contsignDate.setVisible(false);		
        this.contsignDate.setBoundLabelAlignment(7);
        // contOrgType		
        this.contOrgType.setBoundLabelText(resHelper.getString("contOrgType.boundLabelText"));		
        this.contOrgType.setBoundLabelUnderline(true);		
        this.contOrgType.setBoundLabelLength(100);
        // contcontractSource		
        this.contcontractSource.setBoundLabelText(resHelper.getString("contcontractSource.boundLabelText"));		
        this.contcontractSource.setBoundLabelLength(100);		
        this.contcontractSource.setBoundLabelUnderline(true);		
        this.contcontractSource.setBoundLabelAlignment(7);
        // contContractWFType		
        this.contContractWFType.setBoundLabelText(resHelper.getString("contContractWFType.boundLabelText"));		
        this.contContractWFType.setBoundLabelLength(100);		
        this.contContractWFType.setBoundLabelUnderline(true);
        // contAttachment		
        this.contAttachment.setTitle(resHelper.getString("contAttachment.title"));
        // contMode		
        this.contMode.setTitle(resHelper.getString("contMode.title"));
        // contSrcAmount		
        this.contSrcAmount.setBoundLabelText(resHelper.getString("contSrcAmount.boundLabelText"));		
        this.contSrcAmount.setBoundLabelLength(100);		
        this.contSrcAmount.setBoundLabelUnderline(true);
        // contInviteType		
        this.contInviteType.setBoundLabelText(resHelper.getString("contInviteType.boundLabelText"));		
        this.contInviteType.setBoundLabelLength(100);		
        this.contInviteType.setBoundLabelUnderline(true);
        // contNeedPerson		
        this.contNeedPerson.setBoundLabelText(resHelper.getString("contNeedPerson.boundLabelText"));		
        this.contNeedPerson.setBoundLabelLength(100);		
        this.contNeedPerson.setBoundLabelUnderline(true);
        // contNeedDept		
        this.contNeedDept.setBoundLabelText(resHelper.getString("contNeedDept.boundLabelText"));		
        this.contNeedDept.setBoundLabelLength(100);		
        this.contNeedDept.setBoundLabelUnderline(true);
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setVisible(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(true);
        // txtamount		
        this.txtamount.setVisible(true);		
        this.txtamount.setDataType(1);		
        this.txtamount.setSupportedEmpty(true);		
        this.txtamount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtamount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtamount.setEnabled(true);		
        this.txtamount.setRequired(true);
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
            public void focusGained(java.awt.event.FocusEvent e) {
                try {
                    txtamount_focusGained(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // prmtlandDeveloper		
        this.prmtlandDeveloper.setQueryInfo("com.kingdee.eas.fdc.basedata.app.LandDeveloperQuery");		
        this.prmtlandDeveloper.setVisible(true);		
        this.prmtlandDeveloper.setEditable(true);		
        this.prmtlandDeveloper.setDisplayFormat("$number$ $name$");		
        this.prmtlandDeveloper.setEditFormat("$number$");		
        this.prmtlandDeveloper.setCommitFormat("$number$");		
        this.prmtlandDeveloper.setRequired(true);
        // prmtcontractType		
        this.prmtcontractType.setVisible(true);		
        this.prmtcontractType.setEditable(true);		
        this.prmtcontractType.setDisplayFormat("$number$ $name$");		
        this.prmtcontractType.setEditFormat("$number$");		
        this.prmtcontractType.setCommitFormat("$number$");		
        this.prmtcontractType.setRequired(true);		
        this.prmtcontractType.setDefaultF7UIName("com.kingdee.eas.fdc.basedata.client.ContractTypeF7UI");		
        this.prmtcontractType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ContractTypeQuery");
        this.prmtcontractType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtcontractType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtcontractType.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtcontractType_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contractPropert		
        this.contractPropert.setVisible(true);		
        this.contractPropert.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.ContractPropertyEnum").toArray());		
        this.contractPropert.setEnabled(true);
        this.contractPropert.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    contractPropert_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtpartB		
        this.prmtpartB.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");		
        this.prmtpartB.setVisible(true);		
        this.prmtpartB.setEditable(true);		
        this.prmtpartB.setDisplayFormat("$number$ $name$");		
        this.prmtpartB.setEditFormat("$number$");		
        this.prmtpartB.setCommitFormat("$number$");		
        this.prmtpartB.setRequired(true);
        // prmtpartC		
        this.prmtpartC.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");		
        this.prmtpartC.setVisible(true);		
        this.prmtpartC.setEditable(true);		
        this.prmtpartC.setDisplayFormat("$number$ $name$");		
        this.prmtpartC.setEditFormat("$number$");		
        this.prmtpartC.setCommitFormat("$number$");
        // txtcontractName		
        this.txtcontractName.setVisible(true);		
        this.txtcontractName.setHorizontalAlignment(2);		
        this.txtcontractName.setMaxLength(100);		
        this.txtcontractName.setEnabled(true);		
        this.txtcontractName.setRequired(true);
        // pnlInviteInfo		
        this.pnlInviteInfo.setVisible(false);
        // pnlDetail
        // pnlCost
        // panelInvite
        // kdtSupplyEntry
		String kdtSupplyEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"contractPropert\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bookedDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"partB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"localAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{contractPropert}</t:Cell><t:Cell>$Resource{contractType}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{bookedDate}</t:Cell><t:Cell>$Resource{partB}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{localAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtSupplyEntry.setFormatXml(resHelper.translateString("kdtSupplyEntry",kdtSupplyEntryStrXML));
        this.kdtSupplyEntry.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtSupplyEntry_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        this.kdtSupplyEntry.checkParsed();
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelLength(100);		
        this.contRemark.setBoundLabelUnderline(true);		
        this.contRemark.setVisible(false);
        // contCoopLevel		
        this.contCoopLevel.setBoundLabelText(resHelper.getString("contCoopLevel.boundLabelText"));		
        this.contCoopLevel.setBoundLabelLength(100);		
        this.contCoopLevel.setBoundLabelUnderline(true);
        // contPriceType		
        this.contPriceType.setBoundLabelText(resHelper.getString("contPriceType.boundLabelText"));		
        this.contPriceType.setBoundLabelLength(100);		
        this.contPriceType.setBoundLabelUnderline(true);		
        this.contPriceType.setVisible(false);
        // chkIsSubMainContract		
        this.chkIsSubMainContract.setText(resHelper.getString("chkIsSubMainContract.text"));
        // conMainContract		
        this.conMainContract.setBoundLabelText(resHelper.getString("conMainContract.boundLabelText"));		
        this.conMainContract.setBoundLabelUnderline(true);		
        this.conMainContract.setBoundLabelLength(100);
        // conEffectiveStartDate		
        this.conEffectiveStartDate.setBoundLabelText(resHelper.getString("conEffectiveStartDate.boundLabelText"));		
        this.conEffectiveStartDate.setBoundLabelUnderline(true);		
        this.conEffectiveStartDate.setBoundLabelLength(100);
        // conEffectiveEndDate		
        this.conEffectiveEndDate.setBoundLabelText(resHelper.getString("conEffectiveEndDate.boundLabelText"));		
        this.conEffectiveEndDate.setBoundLabelLength(100);		
        this.conEffectiveEndDate.setBoundLabelUnderline(true);
        // kDScrollPane1
        // conInformation		
        this.conInformation.setBoundLabelText(resHelper.getString("conInformation.boundLabelText"));		
        this.conInformation.setBoundLabelUnderline(true);
        // contlowestPrice		
        this.contlowestPrice.setBoundLabelText(resHelper.getString("contlowestPrice.boundLabelText"));		
        this.contlowestPrice.setBoundLabelLength(100);		
        this.contlowestPrice.setBoundLabelUnderline(true);		
        this.contlowestPrice.setBoundLabelAlignment(7);
        // contlowerPrice		
        this.contlowerPrice.setBoundLabelText(resHelper.getString("contlowerPrice.boundLabelText"));		
        this.contlowerPrice.setBoundLabelLength(100);		
        this.contlowerPrice.setBoundLabelUnderline(true);		
        this.contlowerPrice.setBoundLabelAlignment(7);
        // conthigherPrice		
        this.conthigherPrice.setBoundLabelText(resHelper.getString("conthigherPrice.boundLabelText"));		
        this.conthigherPrice.setBoundLabelLength(100);		
        this.conthigherPrice.setBoundLabelUnderline(true);		
        this.conthigherPrice.setVisible(true);		
        this.conthigherPrice.setBoundLabelAlignment(7);
        // contmiddlePrice		
        this.contmiddlePrice.setBoundLabelText(resHelper.getString("contmiddlePrice.boundLabelText"));		
        this.contmiddlePrice.setBoundLabelLength(100);		
        this.contmiddlePrice.setBoundLabelUnderline(true);		
        this.contmiddlePrice.setBoundLabelAlignment(7);
        // conthighestPrice		
        this.conthighestPrice.setBoundLabelText(resHelper.getString("conthighestPrice.boundLabelText"));		
        this.conthighestPrice.setBoundLabelLength(100);		
        this.conthighestPrice.setBoundLabelUnderline(true);		
        this.conthighestPrice.setVisible(true);		
        this.conthighestPrice.setBoundLabelAlignment(7);
        // contbasePrice		
        this.contbasePrice.setBoundLabelText(resHelper.getString("contbasePrice.boundLabelText"));		
        this.contbasePrice.setBoundLabelLength(100);		
        this.contbasePrice.setBoundLabelUnderline(true);		
        this.contbasePrice.setVisible(true);		
        this.contbasePrice.setBoundLabelAlignment(7);
        // contsecondPrice		
        this.contsecondPrice.setBoundLabelText(resHelper.getString("contsecondPrice.boundLabelText"));		
        this.contsecondPrice.setBoundLabelLength(100);		
        this.contsecondPrice.setBoundLabelUnderline(true);		
        this.contsecondPrice.setVisible(true);		
        this.contsecondPrice.setBoundLabelAlignment(7);
        // continviteType		
        this.continviteType.setBoundLabelText(resHelper.getString("continviteType.boundLabelText"));		
        this.continviteType.setBoundLabelLength(100);		
        this.continviteType.setBoundLabelUnderline(true);		
        this.continviteType.setVisible(true);		
        this.continviteType.setBoundLabelAlignment(7);
        // contwinPrice		
        this.contwinPrice.setBoundLabelText(resHelper.getString("contwinPrice.boundLabelText"));		
        this.contwinPrice.setBoundLabelLength(100);		
        this.contwinPrice.setBoundLabelUnderline(true);		
        this.contwinPrice.setVisible(true);		
        this.contwinPrice.setBoundLabelAlignment(7);
        // contwinUnit		
        this.contwinUnit.setBoundLabelText(resHelper.getString("contwinUnit.boundLabelText"));		
        this.contwinUnit.setBoundLabelLength(100);		
        this.contwinUnit.setBoundLabelUnderline(true);		
        this.contwinUnit.setVisible(true);		
        this.contwinUnit.setBoundLabelAlignment(7);
        // contfileNo		
        this.contfileNo.setBoundLabelText(resHelper.getString("contfileNo.boundLabelText"));		
        this.contfileNo.setBoundLabelLength(100);		
        this.contfileNo.setBoundLabelUnderline(true);		
        this.contfileNo.setVisible(true);		
        this.contfileNo.setBoundLabelAlignment(7);
        // contquantity		
        this.contquantity.setBoundLabelText(resHelper.getString("contquantity.boundLabelText"));		
        this.contquantity.setBoundLabelLength(100);		
        this.contquantity.setBoundLabelUnderline(true);		
        this.contquantity.setVisible(true);		
        this.contquantity.setBoundLabelAlignment(7);
        // prmtlowestPriceUnit		
        this.prmtlowestPriceUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtlowestPriceUnit.setVisible(true);		
        this.prmtlowestPriceUnit.setEditable(true);		
        this.prmtlowestPriceUnit.setDisplayFormat("$number$  $name$");		
        this.prmtlowestPriceUnit.setEditFormat("$number$");		
        this.prmtlowestPriceUnit.setCommitFormat("$number$");
        // prmtlowerPriceUnit		
        this.prmtlowerPriceUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtlowerPriceUnit.setVisible(true);		
        this.prmtlowerPriceUnit.setEditable(true);		
        this.prmtlowerPriceUnit.setDisplayFormat("$number$ $name$");		
        this.prmtlowerPriceUnit.setEditFormat("$number$");		
        this.prmtlowerPriceUnit.setCommitFormat("$number$");
        // prmtmiddlePriceUnit		
        this.prmtmiddlePriceUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtmiddlePriceUnit.setVisible(true);		
        this.prmtmiddlePriceUnit.setEditable(true);		
        this.prmtmiddlePriceUnit.setDisplayFormat("$number$ $name$");		
        this.prmtmiddlePriceUnit.setEditFormat("$number$");		
        this.prmtmiddlePriceUnit.setCommitFormat("$number$");
        // prmthigherPriceUnit		
        this.prmthigherPriceUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmthigherPriceUnit.setVisible(true);		
        this.prmthigherPriceUnit.setEditable(true);		
        this.prmthigherPriceUnit.setDisplayFormat("$number$ $name$");		
        this.prmthigherPriceUnit.setEditFormat("$number$");		
        this.prmthigherPriceUnit.setCommitFormat("$number$");
        // prmthighestPriceUni		
        this.prmthighestPriceUni.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmthighestPriceUni.setVisible(true);		
        this.prmthighestPriceUni.setEditable(true);		
        this.prmthighestPriceUni.setDisplayFormat("$number$ $name$");		
        this.prmthighestPriceUni.setEditFormat("$number$");		
        this.prmthighestPriceUni.setCommitFormat("$number$");
        // lblPrice		
        this.lblPrice.setText(resHelper.getString("lblPrice.text"));
        // lblUnit		
        this.lblUnit.setText(resHelper.getString("lblUnit.text"));
        // lblOverRateContainer		
        this.lblOverRateContainer.setBoundLabelText(resHelper.getString("lblOverRateContainer.boundLabelText"));		
        this.lblOverRateContainer.setBoundLabelLength(100);		
        this.lblOverRateContainer.setBoundLabelUnderline(true);		
        this.lblOverRateContainer.setVisible(false);
        // contpayPercForWarn		
        this.contpayPercForWarn.setBoundLabelText(resHelper.getString("contpayPercForWarn.boundLabelText"));		
        this.contpayPercForWarn.setBoundLabelLength(100);		
        this.contpayPercForWarn.setBoundLabelUnderline(true);		
        this.contpayPercForWarn.setVisible(false);		
        this.contpayPercForWarn.setBoundLabelAlignment(7);
        // contchgPercForWarn		
        this.contchgPercForWarn.setBoundLabelText(resHelper.getString("contchgPercForWarn.boundLabelText"));		
        this.contchgPercForWarn.setBoundLabelLength(100);		
        this.contchgPercForWarn.setBoundLabelUnderline(true);		
        this.contchgPercForWarn.setVisible(false);		
        this.contchgPercForWarn.setBoundLabelAlignment(7);
        // contcostProperty		
        this.contcostProperty.setBoundLabelText(resHelper.getString("contcostProperty.boundLabelText"));		
        this.contcostProperty.setBoundLabelLength(100);		
        this.contcostProperty.setBoundLabelUnderline(true);		
        this.contcostProperty.setVisible(false);		
        this.contcostProperty.setBoundLabelAlignment(7);
        // contStampTaxRate		
        this.contStampTaxRate.setBoundLabelText(resHelper.getString("contStampTaxRate.boundLabelText"));		
        this.contStampTaxRate.setBoundLabelLength(100);		
        this.contStampTaxRate.setBoundLabelUnderline(true);		
        this.contStampTaxRate.setVisible(false);
        // contStampTaxAmt		
        this.contStampTaxAmt.setBoundLabelText(resHelper.getString("contStampTaxAmt.boundLabelText"));		
        this.contStampTaxAmt.setBoundLabelLength(100);		
        this.contStampTaxAmt.setBoundLabelUnderline(true);		
        this.contStampTaxAmt.setVisible(false);
        // contPayScale		
        this.contPayScale.setBoundLabelText(resHelper.getString("contPayScale.boundLabelText"));		
        this.contPayScale.setBoundLabelLength(100);		
        this.contPayScale.setBoundLabelUnderline(true);		
        this.contPayScale.setVisible(false);
        // prmtFwContractTemp		
        this.prmtFwContractTemp.setQueryInfo("com.kingdee.eas.fdc.contract.programming.app.ProgrammingContractF7Query");		
        this.prmtFwContractTemp.setCommitFormat("$number$");		
        this.prmtFwContractTemp.setDisplayFormat("$name$");		
        this.prmtFwContractTemp.setEditFormat("$number$");		
        this.prmtFwContractTemp.setVisible(false);
        // contAttachmentNameList		
        this.contAttachmentNameList.setBoundLabelText(resHelper.getString("contAttachmentNameList.boundLabelText"));		
        this.contAttachmentNameList.setBoundLabelLength(100);		
        this.contAttachmentNameList.setBoundLabelUnderline(true);
        // btnViewAttachment
        this.btnViewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachmentSelf, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAttachment.setText(resHelper.getString("btnViewAttachment.text"));		
        this.btnViewAttachment.setVisible(false);
        this.btnViewAttachment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnViewAttachment_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // conChargeType		
        this.conChargeType.setBoundLabelText(resHelper.getString("conChargeType.boundLabelText"));		
        this.conChargeType.setBoundLabelLength(100);		
        this.conChargeType.setBoundLabelUnderline(true);
        // txtRemark
        // comboCoopLevel		
        this.comboCoopLevel.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.CoopLevelEnum").toArray());
        // comboPriceType		
        this.comboPriceType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.PriceTypeEnum").toArray());
        // prmtMainContract		
        this.prmtMainContract.setDisplayFormat("$name$");		
        this.prmtMainContract.setEditFormat("$number$");		
        this.prmtMainContract.setCommitFormat("$number$");		
        this.prmtMainContract.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7MainContractBillQuery");
        this.prmtMainContract.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtMainContract_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdpEffectStartDate
        // kdpEffectiveEndDate
        // txtInformation
        // txtlowestPrice		
        this.txtlowestPrice.setVisible(true);		
        this.txtlowestPrice.setHorizontalAlignment(2);		
        this.txtlowestPrice.setDataType(1);		
        this.txtlowestPrice.setSupportedEmpty(true);		
        this.txtlowestPrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtlowestPrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtlowestPrice.setPrecision(2);		
        this.txtlowestPrice.setEnabled(true);
        // txtlowerPrice		
        this.txtlowerPrice.setVisible(true);		
        this.txtlowerPrice.setHorizontalAlignment(2);		
        this.txtlowerPrice.setDataType(1);		
        this.txtlowerPrice.setSupportedEmpty(true);		
        this.txtlowerPrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtlowerPrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtlowerPrice.setPrecision(2);		
        this.txtlowerPrice.setEnabled(true);
        // txthigherPrice		
        this.txthigherPrice.setVisible(true);		
        this.txthigherPrice.setHorizontalAlignment(2);		
        this.txthigherPrice.setDataType(1);		
        this.txthigherPrice.setSupportedEmpty(true);		
        this.txthigherPrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txthigherPrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txthigherPrice.setPrecision(2);		
        this.txthigherPrice.setEnabled(true);
        // txtmiddlePrice		
        this.txtmiddlePrice.setVisible(true);		
        this.txtmiddlePrice.setHorizontalAlignment(2);		
        this.txtmiddlePrice.setDataType(1);		
        this.txtmiddlePrice.setSupportedEmpty(true);		
        this.txtmiddlePrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtmiddlePrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtmiddlePrice.setPrecision(2);		
        this.txtmiddlePrice.setEnabled(true);
        // txthighestPrice		
        this.txthighestPrice.setVisible(true);		
        this.txthighestPrice.setHorizontalAlignment(2);		
        this.txthighestPrice.setDataType(1);		
        this.txthighestPrice.setSupportedEmpty(true);		
        this.txthighestPrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txthighestPrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txthighestPrice.setPrecision(2);		
        this.txthighestPrice.setEnabled(true);
        // txtbasePrice		
        this.txtbasePrice.setVisible(true);		
        this.txtbasePrice.setHorizontalAlignment(2);		
        this.txtbasePrice.setDataType(1);		
        this.txtbasePrice.setSupportedEmpty(true);		
        this.txtbasePrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtbasePrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtbasePrice.setPrecision(2);		
        this.txtbasePrice.setEnabled(true);
        // txtsecondPrice		
        this.txtsecondPrice.setVisible(true);		
        this.txtsecondPrice.setHorizontalAlignment(2);		
        this.txtsecondPrice.setDataType(1);		
        this.txtsecondPrice.setSupportedEmpty(true);		
        this.txtsecondPrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtsecondPrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtsecondPrice.setPrecision(2);		
        this.txtsecondPrice.setEnabled(true);
        // prmtinviteType		
        this.prmtinviteType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.InviteTypeQuery");		
        this.prmtinviteType.setVisible(true);		
        this.prmtinviteType.setEditable(true);		
        this.prmtinviteType.setDisplayFormat("$number$ $name$");		
        this.prmtinviteType.setEditFormat("$number$");		
        this.prmtinviteType.setCommitFormat("$number$");
        // txtwinPrice		
        this.txtwinPrice.setVisible(true);		
        this.txtwinPrice.setHorizontalAlignment(2);		
        this.txtwinPrice.setDataType(1);		
        this.txtwinPrice.setSupportedEmpty(true);		
        this.txtwinPrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtwinPrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtwinPrice.setPrecision(2);		
        this.txtwinPrice.setEnabled(true);
        // prmtwinUnit		
        this.prmtwinUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtwinUnit.setVisible(true);		
        this.prmtwinUnit.setEditable(true);		
        this.prmtwinUnit.setDisplayFormat("$number$ $name$");		
        this.prmtwinUnit.setEditFormat("$number$");		
        this.prmtwinUnit.setCommitFormat("$number$");
        // txtfileNo		
        this.txtfileNo.setVisible(true);		
        this.txtfileNo.setHorizontalAlignment(2);		
        this.txtfileNo.setMaxLength(100);		
        this.txtfileNo.setEnabled(true);
        // txtquantity		
        this.txtquantity.setVisible(true);		
        this.txtquantity.setHorizontalAlignment(2);		
        this.txtquantity.setDataType(1);		
        this.txtquantity.setSupportedEmpty(true);		
        this.txtquantity.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtquantity.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtquantity.setPrecision(4);		
        this.txtquantity.setEnabled(true);
        // txtOverAmt		
        this.txtOverAmt.setDataType(5);		
        this.txtOverAmt.setDataVerifierType(12);
        // txtpayPercForWarn		
        this.txtpayPercForWarn.setVisible(true);		
        this.txtpayPercForWarn.setHorizontalAlignment(2);		
        this.txtpayPercForWarn.setDataType(1);		
        this.txtpayPercForWarn.setSupportedEmpty(true);		
        this.txtpayPercForWarn.setMinimumValue( new java.math.BigDecimal(0.0));		
        this.txtpayPercForWarn.setMaximumValue( new java.math.BigDecimal(100.0));		
        this.txtpayPercForWarn.setPrecision(2);		
        this.txtpayPercForWarn.setEnabled(true);		
        this.txtpayPercForWarn.setRequired(true);
        // txtchgPercForWarn		
        this.txtchgPercForWarn.setVisible(true);		
        this.txtchgPercForWarn.setHorizontalAlignment(2);		
        this.txtchgPercForWarn.setDataType(1);		
        this.txtchgPercForWarn.setSupportedEmpty(true);		
        this.txtchgPercForWarn.setMinimumValue( new java.math.BigDecimal(0.0));		
        this.txtchgPercForWarn.setMaximumValue( new java.math.BigDecimal(100.0));		
        this.txtchgPercForWarn.setPrecision(2);		
        this.txtchgPercForWarn.setEnabled(true);		
        this.txtchgPercForWarn.setRequired(true);
        // costProperty		
        this.costProperty.setVisible(true);		
        this.costProperty.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.CostPropertyEnum").toArray());		
        this.costProperty.setEnabled(true);		
        this.costProperty.setRequired(true);
        this.costProperty.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    costProperty_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtStampTaxRate		
        this.txtStampTaxRate.setDataType(1);		
        this.txtStampTaxRate.setPrecision(2);		
        this.txtStampTaxRate.setSupportedEmpty(true);
        this.txtStampTaxRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtStampTaxRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtStampTaxAmt		
        this.txtStampTaxAmt.setDataType(1);		
        this.txtStampTaxAmt.setPrecision(2);		
        this.txtStampTaxAmt.setSupportedEmpty(true);
        // txtPayScale		
        this.txtPayScale.setDataType(1);		
        this.txtPayScale.setPrecision(2);		
        this.txtPayScale.setSupportedEmpty(true);
        // comboAttachmentNameList		
        this.comboAttachmentNameList.setEditable(true);
        // prmtCharge		
        this.prmtCharge.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ContractChargeTypeQuery");		
        this.prmtCharge.setDisplayFormat("$name$");		
        this.prmtCharge.setEditFormat("$number$");		
        this.prmtCharge.setCommitFormat("$number$");
        // tblDetail
		String tblDetailStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"detail\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"content\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"desc\" t:width=\"500\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"rowKey\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"dataType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"detailDef.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{detail}</t:Cell><t:Cell>$Resource{content}</t:Cell><t:Cell>$Resource{desc}</t:Cell><t:Cell>$Resource{rowKey}</t:Cell><t:Cell>$Resource{dataType}</t:Cell><t:Cell>$Resource{detailDef.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblDetail.setFormatXml(resHelper.translateString("tblDetail",tblDetailStrXML));
        this.tblDetail.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblDetail_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.tblDetail.checkParsed();
        // tblCost
		String tblCostStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"acctNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"acctName\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"aimCost\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"hasHappen\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"intendingHappen\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"dynamicCost\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"chayi\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{acctNumber}</t:Cell><t:Cell>$Resource{acctName}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{hasHappen}</t:Cell><t:Cell>$Resource{intendingHappen}</t:Cell><t:Cell>$Resource{dynamicCost}</t:Cell><t:Cell>$Resource{chayi}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblCost.setFormatXml(resHelper.translateString("tblCost",tblCostStrXML));
        this.tblCost.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblCost_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        this.tblCost.checkParsed();
        // tblInvite
		String tblInviteStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"respDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{respDept}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createDate}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblInvite.setFormatXml(resHelper.translateString("tblInvite",tblInviteStrXML));
        this.tblInvite.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblInvite_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        this.tblInvite.checkParsed();
        // txtExRate		
        this.txtExRate.setRequired(true);		
        this.txtExRate.setPrecision(10);		
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
        // txtLocalAmount		
        this.txtLocalAmount.setDataType(1);		
        this.txtLocalAmount.setPrecision(2);		
        this.txtLocalAmount.setRequired(true);		
        this.txtLocalAmount.setEditable(false);
        // txtGrtAmount		
        this.txtGrtAmount.setDataType(1);		
        this.txtGrtAmount.setPrecision(2);
        // comboCurrency
        this.comboCurrency.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboCurrency_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtRespPerson		
        this.prmtRespPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtRespPerson.setDisplayFormat("$name$");		
        this.prmtRespPerson.setEditFormat("$number$");		
        this.prmtRespPerson.setCommitFormat("$number$");		
        this.prmtRespPerson.setRequired(true);		
        this.prmtRespPerson.setDefaultF7UIName("com.kingdee.eas.basedata.person.client.PersonF7UI");
        // txtCreator		
        this.txtCreator.setEditable(false);
        this.txtCreator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtCreator_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtOrg		
        this.txtOrg.setEditable(false);
        // txtProj		
        this.txtProj.setEditable(false);
        // txtGrtRate		
        this.txtGrtRate.setRequired(true);		
        this.txtGrtRate.setPrecision(2);		
        this.txtGrtRate.setDataType(5);
        this.txtGrtRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtGrtRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cbPeriod		
        this.cbPeriod.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PeriodQuery");		
        this.cbPeriod.setEnabled(false);
        // textFwContract		
        this.textFwContract.setEnabled(false);
        // txtOrgAmtBig		
        this.txtOrgAmtBig.setEnabled(false);
        // txtAmtBig		
        this.txtAmtBig.setEnabled(false);
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
        // prmtCreateOrg		
        this.prmtCreateOrg.setDefaultF7UIName("com.kingdee.eas.basedata.org.client.f7.AdminF7");		
        this.prmtCreateOrg.setCommitFormat("$number$");		
        this.prmtCreateOrg.setDisplayFormat("$name$");		
        this.prmtCreateOrg.setEditFormat("$number");
        // kDScrollPane2		
        this.kDScrollPane2.setPreferredSize(new Dimension(0,57));		
        this.kDScrollPane2.setAutoscrolls(true);
        // txtDes		
        this.txtDes.setFocusCycleRoot(true);		
        this.txtDes.setMaxLength(1000);		
        this.txtDes.setPreferredSize(new Dimension(0,200));		
        this.txtDes.setLineWrap(true);
        // chkIsOpen		
        this.chkIsOpen.setText(resHelper.getString("chkIsOpen.text"));		
        this.chkIsOpen.setVisible(false);
        // chkIsStandardContract		
        this.chkIsStandardContract.setText(resHelper.getString("chkIsStandardContract.text"));		
        this.chkIsStandardContract.setEnabled(false);
        // labSettleType		
        this.labSettleType.setText(resHelper.getString("labSettleType.text"));
        // btnAType		
        this.btnAType.setText(resHelper.getString("btnAType.text"));
        // btnBType		
        this.btnBType.setText(resHelper.getString("btnBType.text"));
        // BtnCType		
        this.BtnCType.setText(resHelper.getString("BtnCType.text"));
        // chkIsPartAMaterialCon		
        this.chkIsPartAMaterialCon.setText(resHelper.getString("chkIsPartAMaterialCon.text"));
        this.chkIsPartAMaterialCon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    chkIsPartAMaterialCon_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // chkCostSplit		
        this.chkCostSplit.setText(resHelper.getString("chkCostSplit.text"));		
        this.chkCostSplit.setToolTipText(resHelper.getString("chkCostSplit.toolTipText"));
        this.chkCostSplit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    chkCostSplit_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // prmtRespDept		
        this.prmtRespDept.setDisplayFormat("$name$");		
        this.prmtRespDept.setEditFormat("$number");		
        this.prmtRespDept.setDefaultF7UIName("com.kingdee.eas.basedata.org.client.f7.AdminF7");		
        this.prmtRespDept.setCommitFormat("$number$");		
        this.prmtRespDept.setEditable(true);		
        this.prmtRespDept.setRequired(true);
        // pksignDate		
        this.pksignDate.setVisible(true);		
        this.pksignDate.setEnabled(true);
        // cbOrgType		
        this.cbOrgType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum").toArray());		
        this.cbOrgType.setRequired(true);
        // contractSource		
        this.contractSource.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ContractSourceQuery");		
        this.contractSource.setCommitFormat("$number$");		
        this.contractSource.setDisplayFormat("$number$ $name$");		
        this.contractSource.setEditFormat("$number$");		
        this.contractSource.setRequired(true);
        this.contractSource.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    contractSource_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.contractSource.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    contractSource_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtContractWFType		
        this.prmtContractWFType.setDisplayFormat("$name$");		
        this.prmtContractWFType.setEditFormat("$longNumber$");		
        this.prmtContractWFType.setCommitFormat("$longNumber$");		
        this.prmtContractWFType.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractWFQuery");		
        this.prmtContractWFType.setRequired(true);
        this.prmtContractWFType.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtContractWFType_willShow(e);
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
        // prmtModel		
        this.prmtModel.setCommitFormat("$number$");		
        this.prmtModel.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractModelQuery");		
        this.prmtModel.setDisplayFormat("$name$");		
        this.prmtModel.setEditFormat("$name$");
        this.prmtModel.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtModel_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtModel.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtModel_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtSrcAmount		
        this.txtSrcAmount.setEnabled(false);
        // prmtInviteType		
        this.prmtInviteType.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteTypeQuery");		
        this.prmtInviteType.setEditFormat("$number$");		
        this.prmtInviteType.setDisplayFormat("$name$");		
        this.prmtInviteType.setCommitFormat("$number$");		
        this.prmtInviteType.setRequired(true);
        // prmtNeedPerson		
        this.prmtNeedPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtNeedPerson.setDisplayFormat("$name$");		
        this.prmtNeedPerson.setEditFormat("$number$");		
        this.prmtNeedPerson.setCommitFormat("$number$");		
        this.prmtNeedPerson.setRequired(true);		
        this.prmtNeedPerson.setDefaultF7UIName("com.kingdee.eas.basedata.person.client.PersonF7UI");
        // prmtNeedDept		
        this.prmtNeedDept.setDisplayFormat("$name$");		
        this.prmtNeedDept.setEditFormat("$number");		
        this.prmtNeedDept.setDefaultF7UIName("com.kingdee.eas.basedata.org.client.f7.AdminF7");		
        this.prmtNeedDept.setCommitFormat("$number$");		
        this.prmtNeedDept.setEditable(true);		
        this.prmtNeedDept.setRequired(true);
        // kDContainer1		
        this.kDContainer1.setEnableActive(false);
        // kDSplitPane1		
        this.kDSplitPane1.setOrientation(0);		
        this.kDSplitPane1.setResizeWeight(0.5);
        // contPayItem		
        this.contPayItem.setTitle(resHelper.getString("contPayItem.title"));
        // contBailItem		
        this.contBailItem.setTitle(resHelper.getString("contBailItem.title"));
        // tblEconItem
		String tblEconItemStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>###.00</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>###,##0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"payType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"payCondition\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"payRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"payAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"desc\" t:width=\"390\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{payType}</t:Cell><t:Cell>$Resource{payCondition}</t:Cell><t:Cell>$Resource{payRate}</t:Cell><t:Cell>$Resource{payAmount}</t:Cell><t:Cell>$Resource{desc}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblEconItem.setFormatXml(resHelper.translateString("tblEconItem",tblEconItemStrXML));
        this.tblEconItem.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblEconItem_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblEconItem.putBindContents("editData",new String[] {"payItemDate","paymentType","payCondition","prop","amount","desc"});


        this.tblEconItem.checkParsed();
        KDDatePicker tblEconItem_date_DatePicker = new KDDatePicker();
        tblEconItem_date_DatePicker.setName("tblEconItem_date_DatePicker");
        tblEconItem_date_DatePicker.setVisible(true);
        tblEconItem_date_DatePicker.setEditable(true);
        KDTDefaultCellEditor tblEconItem_date_CellEditor = new KDTDefaultCellEditor(tblEconItem_date_DatePicker);
        this.tblEconItem.getColumn("date").setEditor(tblEconItem_date_CellEditor);
        final KDBizPromptBox tblEconItem_payType_PromptBox = new KDBizPromptBox();
        tblEconItem_payType_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
        tblEconItem_payType_PromptBox.setVisible(true);
        tblEconItem_payType_PromptBox.setEditable(true);
        tblEconItem_payType_PromptBox.setDisplayFormat("$number$");
        tblEconItem_payType_PromptBox.setEditFormat("$number$");
        tblEconItem_payType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor tblEconItem_payType_CellEditor = new KDTDefaultCellEditor(tblEconItem_payType_PromptBox);
        this.tblEconItem.getColumn("payType").setEditor(tblEconItem_payType_CellEditor);
        ObjectValueRender tblEconItem_payType_OVR = new ObjectValueRender();
        tblEconItem_payType_OVR.setFormat(new BizDataFormat("$name$"));
        this.tblEconItem.getColumn("payType").setRenderer(tblEconItem_payType_OVR);
        KDTextField tblEconItem_payCondition_TextField = new KDTextField();
        tblEconItem_payCondition_TextField.setName("tblEconItem_payCondition_TextField");
        tblEconItem_payCondition_TextField.setMaxLength(200);
        KDTDefaultCellEditor tblEconItem_payCondition_CellEditor = new KDTDefaultCellEditor(tblEconItem_payCondition_TextField);
        this.tblEconItem.getColumn("payCondition").setEditor(tblEconItem_payCondition_CellEditor);
        KDFormattedTextField tblEconItem_payRate_TextField = new KDFormattedTextField();
        tblEconItem_payRate_TextField.setName("tblEconItem_payRate_TextField");
        tblEconItem_payRate_TextField.setVisible(true);
        tblEconItem_payRate_TextField.setEditable(true);
        tblEconItem_payRate_TextField.setHorizontalAlignment(2);
        tblEconItem_payRate_TextField.setDataType(1);
        	tblEconItem_payRate_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	tblEconItem_payRate_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        tblEconItem_payRate_TextField.setPrecision(10);
        KDTDefaultCellEditor tblEconItem_payRate_CellEditor = new KDTDefaultCellEditor(tblEconItem_payRate_TextField);
        this.tblEconItem.getColumn("payRate").setEditor(tblEconItem_payRate_CellEditor);
        KDFormattedTextField tblEconItem_payAmount_TextField = new KDFormattedTextField();
        tblEconItem_payAmount_TextField.setName("tblEconItem_payAmount_TextField");
        tblEconItem_payAmount_TextField.setVisible(true);
        tblEconItem_payAmount_TextField.setEditable(true);
        tblEconItem_payAmount_TextField.setHorizontalAlignment(2);
        tblEconItem_payAmount_TextField.setDataType(1);
        	tblEconItem_payAmount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	tblEconItem_payAmount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        tblEconItem_payAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor tblEconItem_payAmount_CellEditor = new KDTDefaultCellEditor(tblEconItem_payAmount_TextField);
        this.tblEconItem.getColumn("payAmount").setEditor(tblEconItem_payAmount_CellEditor);
        KDTextField tblEconItem_desc_TextField = new KDTextField();
        tblEconItem_desc_TextField.setName("tblEconItem_desc_TextField");
        tblEconItem_desc_TextField.setMaxLength(250);
        KDTDefaultCellEditor tblEconItem_desc_CellEditor = new KDTDefaultCellEditor(tblEconItem_desc_TextField);
        this.tblEconItem.getColumn("desc").setEditor(tblEconItem_desc_CellEditor);
        // contBailOriAmount		
        this.contBailOriAmount.setBoundLabelText(resHelper.getString("contBailOriAmount.boundLabelText"));		
        this.contBailOriAmount.setBoundLabelLength(140);		
        this.contBailOriAmount.setBoundLabelUnderline(true);
        // contBailRate		
        this.contBailRate.setBoundLabelText(resHelper.getString("contBailRate.boundLabelText"));		
        this.contBailRate.setBoundLabelLength(140);		
        this.contBailRate.setBoundLabelUnderline(true);
        // tblBail
		String tblBailStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>###.00</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###,##0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"bailDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bailCondition\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bailRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"bailAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"desc\" t:width=\"460\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{bailDate}</t:Cell><t:Cell>$Resource{bailCondition}</t:Cell><t:Cell>$Resource{bailRate}</t:Cell><t:Cell>$Resource{bailAmount}</t:Cell><t:Cell>$Resource{desc}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblBail.setFormatXml(resHelper.translateString("tblBail",tblBailStrXML));
        this.tblBail.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblBail_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblBail.putBindContents("editData",new String[] {"ail.entry.bailDate","ail.entry.bailConditon","ail.entry.prop","ail.entry.amount","ail.entry.desc"});


        this.tblBail.checkParsed();
        KDDatePicker tblBail_bailDate_DatePicker = new KDDatePicker();
        tblBail_bailDate_DatePicker.setName("tblBail_bailDate_DatePicker");
        tblBail_bailDate_DatePicker.setVisible(true);
        tblBail_bailDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor tblBail_bailDate_CellEditor = new KDTDefaultCellEditor(tblBail_bailDate_DatePicker);
        this.tblBail.getColumn("bailDate").setEditor(tblBail_bailDate_CellEditor);
        KDTextField tblBail_bailCondition_TextField = new KDTextField();
        tblBail_bailCondition_TextField.setName("tblBail_bailCondition_TextField");
        tblBail_bailCondition_TextField.setMaxLength(200);
        KDTDefaultCellEditor tblBail_bailCondition_CellEditor = new KDTDefaultCellEditor(tblBail_bailCondition_TextField);
        this.tblBail.getColumn("bailCondition").setEditor(tblBail_bailCondition_CellEditor);
        KDFormattedTextField tblBail_bailRate_TextField = new KDFormattedTextField();
        tblBail_bailRate_TextField.setName("tblBail_bailRate_TextField");
        tblBail_bailRate_TextField.setVisible(true);
        tblBail_bailRate_TextField.setEditable(true);
        tblBail_bailRate_TextField.setHorizontalAlignment(2);
        tblBail_bailRate_TextField.setDataType(1);
        	tblBail_bailRate_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	tblBail_bailRate_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        tblBail_bailRate_TextField.setPrecision(10);
        KDTDefaultCellEditor tblBail_bailRate_CellEditor = new KDTDefaultCellEditor(tblBail_bailRate_TextField);
        this.tblBail.getColumn("bailRate").setEditor(tblBail_bailRate_CellEditor);
        KDFormattedTextField tblBail_bailAmount_TextField = new KDFormattedTextField();
        tblBail_bailAmount_TextField.setName("tblBail_bailAmount_TextField");
        tblBail_bailAmount_TextField.setVisible(true);
        tblBail_bailAmount_TextField.setEditable(true);
        tblBail_bailAmount_TextField.setHorizontalAlignment(2);
        tblBail_bailAmount_TextField.setDataType(1);
        	tblBail_bailAmount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	tblBail_bailAmount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        tblBail_bailAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor tblBail_bailAmount_CellEditor = new KDTDefaultCellEditor(tblBail_bailAmount_TextField);
        this.tblBail.getColumn("bailAmount").setEditor(tblBail_bailAmount_CellEditor);
        KDTextField tblBail_desc_TextField = new KDTextField();
        tblBail_desc_TextField.setName("tblBail_desc_TextField");
        tblBail_desc_TextField.setMaxLength(250);
        KDTDefaultCellEditor tblBail_desc_CellEditor = new KDTDefaultCellEditor(tblBail_desc_TextField);
        this.tblBail.getColumn("desc").setEditor(tblBail_desc_CellEditor);
        // txtBailOriAmount		
        this.txtBailOriAmount.setDataType(1);
        this.txtBailOriAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtBailOriAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtBailRate		
        this.txtBailRate.setDataType(1);
        this.txtBailRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtBailRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnProgram
        this.btnProgram.setAction((IItemAction)ActionProxyFactory.getProxy(actionProgram, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProgram.setText(resHelper.getString("btnProgram.text"));		
        this.btnProgram.setToolTipText(resHelper.getString("btnProgram.toolTipText"));		
        this.btnProgram.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_associatecreate"));
        // btnAccountView
        this.btnAccountView.setAction((IItemAction)ActionProxyFactory.getProxy(actionAccountView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAccountView.setText(resHelper.getString("btnAccountView.text"));
        // btnSplit
        this.btnSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplit.setText(resHelper.getString("btnSplit.text"));		
        this.btnSplit.setToolTipText(resHelper.getString("btnSplit.toolTipText"));
        // btnDelSplit
        this.btnDelSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelSplit.setText(resHelper.getString("btnDelSplit.text"));		
        this.btnDelSplit.setToolTipText(resHelper.getString("btnDelSplit.toolTipText"));
        // btnViewContent
        this.btnViewContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContent.setText(resHelper.getString("btnViewContent.text"));		
        this.btnViewContent.setToolTipText(resHelper.getString("btnViewContent.toolTipText"));
        // btnContractPlan
        this.btnContractPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionContractPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnContractPlan.setText(resHelper.getString("btnContractPlan.text"));
        // btnViewCost
        this.btnViewCost.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewCost, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewCost.setText(resHelper.getString("btnViewCost.text"));		
        this.btnViewCost.setToolTipText(resHelper.getString("btnViewCost.toolTipText"));
        // btnViewBgBalance
        this.btnViewBgBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBgBalance.setText(resHelper.getString("btnViewBgBalance.text"));		
        this.btnViewBgBalance.setToolTipText(resHelper.getString("btnViewBgBalance.toolTipText"));
        // btnViewProgramContract
        this.btnViewProgramContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewProgramCon, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewProgramContract.setText(resHelper.getString("btnViewProgramContract.text"));		
        this.btnViewProgramContract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showlist"));
        // btnAgreement
        this.btnAgreement.setAction((IItemAction)ActionProxyFactory.getProxy(actionAgreementText, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAgreement.setText(resHelper.getString("btnAgreement.text"));		
        this.btnAgreement.setToolTipText(resHelper.getString("btnAgreement.toolTipText"));		
        this.btnAgreement.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcredence"));
        // menuItemViewContent
        this.menuItemViewContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewContent.setText(resHelper.getString("menuItemViewContent.text"));		
        this.menuItemViewContent.setToolTipText(resHelper.getString("menuItemViewContent.toolTipText"));
        // menuItemViewBgBalance
        this.menuItemViewBgBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewBgBalance.setText(resHelper.getString("menuItemViewBgBalance.text"));		
        this.menuItemViewBgBalance.setVerticalTextPosition(3);		
        this.menuItemViewBgBalance.setToolTipText(resHelper.getString("menuItemViewBgBalance.toolTipText"));
        // menuItemViewProg
        this.menuItemViewProg.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewProgramCon, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewProg.setText(resHelper.getString("menuItemViewProg.text"));		
        this.menuItemViewProg.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showlist"));
        // menuItemSplit
        this.menuItemSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplit.setText(resHelper.getString("menuItemSplit.text"));		
        this.menuItemSplit.setToolTipText(resHelper.getString("menuItemSplit.toolTipText"));		
        this.menuItemSplit.setMnemonic(76);
        // menuItemDelSplit
        this.menuItemDelSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDelSplit.setText(resHelper.getString("menuItemDelSplit.text"));		
        this.menuItemDelSplit.setMnemonic(69);
        // menuItemContractPayPlan
        this.menuItemContractPayPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionContractPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemContractPayPlan.setText(resHelper.getString("menuItemContractPayPlan.text"));		
        this.menuItemContractPayPlan.setMnemonic(80);
        this.menuItemContractPayPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    menuItemContractPayPlan_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // enuItemViewCost
        this.enuItemViewCost.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewCost, new Class[] { IItemAction.class }, getServiceContext()));		
        this.enuItemViewCost.setText(resHelper.getString("enuItemViewCost.text"));		
        this.enuItemViewCost.setToolTipText(resHelper.getString("enuItemViewCost.toolTipText"));		
        this.enuItemViewCost.setMnemonic(79);
        // menuItemProgram
        this.menuItemProgram.setAction((IItemAction)ActionProxyFactory.getProxy(actionProgram, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemProgram.setText(resHelper.getString("menuItemProgram.text"));		
        this.menuItemProgram.setToolTipText(resHelper.getString("menuItemProgram.toolTipText"));		
        this.menuItemProgram.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_associatecreate"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtOrg,txtProj,prmtcontractType,txtNumber,txtcontractName,prmtlandDeveloper,prmtpartB,prmtpartC,contractPropert,pksignDate,comboCurrency,txtExRate,prmtRespDept,txtamount,txtLocalAmount,prmtRespPerson,txtGrtRate,txtGrtAmount,pkbookedDate,contractSource,txtpayPercForWarn,cbPeriod,costProperty,txtchgPercForWarn,txtCreator,chkCostSplit,chkIsPartAMaterialCon,kDDateCreateTime,txtPayScale,txtStampTaxAmt,txtStampTaxRate,comboAttachmentNameList,btnViewAttachment,txtlowestPrice,txtlowerPrice,txtmiddlePrice,txthigherPrice,txthighestPrice,txtbasePrice,txtsecondPrice,prmtinviteType,txtwinPrice,prmtwinUnit,txtfileNo,txtquantity,lblPrice,lblUnit,prmtlowestPriceUnit,prmtlowerPriceUnit,prmtmiddlePriceUnit,prmthigherPriceUnit,prmthighestPriceUni,txtRemark,comboCoopLevel,comboPriceType,tblDetail,tblCost,prmtCharge,tblEconItem,txtBailOriAmount,txtBailRate,tblBail}));
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
this.setLayout(new BorderLayout(0, 0));
        this.add(kDScrollPane3, BorderLayout.CENTER);
        //kDScrollPane3
        kDScrollPane3.getViewport().add(tabPanel, null);
        //tabPanel
        tabPanel.add(mainPanel, resHelper.getString("mainPanel.constraints"));
        tabPanel.add(ecoItemPanel, resHelper.getString("ecoItemPanel.constraints"));
        //mainPanel
        mainPanel.setLayout(new KDLayout());
        mainPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1012, 735));        contCreateTime.setBounds(new Rectangle(631, 446, 276, 19));
        mainPanel.add(contCreateTime, new KDLayout.Constraints(631, 446, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(531, 31, 470, 19));
        mainPanel.add(contNumber, new KDLayout.Constraints(531, 31, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contamount.setBounds(new Rectangle(320, 119, 276, 19));
        mainPanel.add(contamount, new KDLayout.Constraints(320, 119, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contlandDeveloper.setBounds(new Rectangle(7, 75, 470, 19));
        mainPanel.add(contlandDeveloper, new KDLayout.Constraints(7, 75, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractType.setBounds(new Rectangle(7, 31, 470, 19));
        mainPanel.add(contcontractType, new KDLayout.Constraints(7, 31, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractPropert.setBounds(new Rectangle(531, 97, 470, 19));
        mainPanel.add(contcontractPropert, new KDLayout.Constraints(531, 97, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contpartB.setBounds(new Rectangle(531, 75, 470, 19));
        mainPanel.add(contpartB, new KDLayout.Constraints(531, 75, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contpartC.setBounds(new Rectangle(7, 97, 470, 19));
        mainPanel.add(contpartC, new KDLayout.Constraints(7, 97, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractName.setBounds(new Rectangle(7, 53, 471, 19));
        mainPanel.add(contcontractName, new KDLayout.Constraints(7, 53, 471, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane1.setBounds(new Rectangle(0, 476, 1000, 256));
        mainPanel.add(kDTabbedPane1, new KDLayout.Constraints(0, 476, 1000, 256, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contExRate.setBounds(new Rectangle(320, 219, 276, 19));
        mainPanel.add(contExRate, new KDLayout.Constraints(320, 219, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLocalAmount.setBounds(new Rectangle(7, 241, 276, 19));
        mainPanel.add(contLocalAmount, new KDLayout.Constraints(7, 241, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contGrtAmount.setBounds(new Rectangle(320, 185, 276, 19));
        mainPanel.add(contGrtAmount, new KDLayout.Constraints(320, 185, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrency.setBounds(new Rectangle(7, 219, 276, 19));
        mainPanel.add(contCurrency, new KDLayout.Constraints(7, 219, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRespPerson.setBounds(new Rectangle(7, 285, 276, 19));
        mainPanel.add(contRespPerson, new KDLayout.Constraints(7, 285, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(320, 446, 276, 19));
        mainPanel.add(contCreator, new KDLayout.Constraints(320, 446, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrg.setBounds(new Rectangle(7, 9, 470, 19));
        mainPanel.add(contOrg, new KDLayout.Constraints(7, 9, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProj.setBounds(new Rectangle(531, 9, 470, 19));
        mainPanel.add(contProj, new KDLayout.Constraints(531, 9, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contGrtRate.setBounds(new Rectangle(320, 163, 276, 19));
        mainPanel.add(contGrtRate, new KDLayout.Constraints(320, 163, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsPartAMaterialCon.setBounds(new Rectangle(7, 263, 276, 19));
        mainPanel.add(contIsPartAMaterialCon, new KDLayout.Constraints(7, 263, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conContrarctRule.setBounds(new Rectangle(531, 53, 470, 19));
        mainPanel.add(conContrarctRule, new KDLayout.Constraints(531, 53, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgAmtBig.setBounds(new Rectangle(320, 141, 276, 19));
        mainPanel.add(contOrgAmtBig, new KDLayout.Constraints(320, 141, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmtBig.setBounds(new Rectangle(320, 241, 276, 19));
        mainPanel.add(contAmtBig, new KDLayout.Constraints(320, 241, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(320, 263, 276, 19));
        mainPanel.add(kDLabelContainer1, new KDLayout.Constraints(320, 263, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateOrg.setBounds(new Rectangle(7, 446, 276, 19));
        mainPanel.add(contCreateOrg, new KDLayout.Constraints(7, 446, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDes.setBounds(new Rectangle(7, 395, 900, 49));
        mainPanel.add(contDes, new KDLayout.Constraints(7, 395, 900, 49, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel1.setBounds(new Rectangle(7, 119, 276, 88));
        mainPanel.add(kDPanel1, new KDLayout.Constraints(7, 119, 276, 88, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRespDept.setBounds(new Rectangle(320, 285, 276, 19));
        mainPanel.add(contRespDept, new KDLayout.Constraints(320, 285, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsignDate.setBounds(new Rectangle(940, 391, 223, 19));
        mainPanel.add(contsignDate, new KDLayout.Constraints(940, 391, 223, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgType.setBounds(new Rectangle(320, 351, 276, 19));
        mainPanel.add(contOrgType, new KDLayout.Constraints(320, 351, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractSource.setBounds(new Rectangle(7, 329, 276, 19));
        mainPanel.add(contcontractSource, new KDLayout.Constraints(7, 329, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractWFType.setBounds(new Rectangle(7, 351, 276, 19));
        mainPanel.add(contContractWFType, new KDLayout.Constraints(7, 351, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAttachment.setBounds(new Rectangle(631, 175, 368, 192));
        mainPanel.add(contAttachment, new KDLayout.Constraints(631, 175, 368, 192, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contMode.setBounds(new Rectangle(631, 119, 368, 50));
        mainPanel.add(contMode, new KDLayout.Constraints(631, 119, 368, 50, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSrcAmount.setBounds(new Rectangle(320, 329, 276, 19));
        mainPanel.add(contSrcAmount, new KDLayout.Constraints(320, 329, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteType.setBounds(new Rectangle(7, 373, 276, 19));
        mainPanel.add(contInviteType, new KDLayout.Constraints(7, 373, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNeedPerson.setBounds(new Rectangle(7, 307, 276, 19));
        mainPanel.add(contNeedPerson, new KDLayout.Constraints(7, 307, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNeedDept.setBounds(new Rectangle(320, 307, 276, 19));
        mainPanel.add(contNeedDept, new KDLayout.Constraints(320, 307, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contamount
        contamount.setBoundEditor(txtamount);
        //contlandDeveloper
        contlandDeveloper.setBoundEditor(prmtlandDeveloper);
        //contcontractType
        contcontractType.setBoundEditor(prmtcontractType);
        //contcontractPropert
        contcontractPropert.setBoundEditor(contractPropert);
        //contpartB
        contpartB.setBoundEditor(prmtpartB);
        //contpartC
        contpartC.setBoundEditor(prmtpartC);
        //contcontractName
        contcontractName.setBoundEditor(txtcontractName);
        //kDTabbedPane1
        kDTabbedPane1.add(pnlInviteInfo, resHelper.getString("pnlInviteInfo.constraints"));
        kDTabbedPane1.add(pnlDetail, resHelper.getString("pnlDetail.constraints"));
        kDTabbedPane1.add(pnlCost, resHelper.getString("pnlCost.constraints"));
        kDTabbedPane1.add(panelInvite, resHelper.getString("panelInvite.constraints"));
        kDTabbedPane1.add(kdtSupplyEntry, resHelper.getString("kdtSupplyEntry.constraints"));
        //pnlInviteInfo
        pnlInviteInfo.setLayout(null);        contRemark.setBounds(new Rectangle(8, 88, 270, 19));
        pnlInviteInfo.add(contRemark, null);
        contCoopLevel.setBounds(new Rectangle(31, 80, 270, 19));
        pnlInviteInfo.add(contCoopLevel, null);
        contPriceType.setBounds(new Rectangle(26, 162, 270, 19));
        pnlInviteInfo.add(contPriceType, null);
        chkIsSubMainContract.setBounds(new Rectangle(30, 25, 118, 19));
        pnlInviteInfo.add(chkIsSubMainContract, null);
        conMainContract.setBounds(new Rectangle(31, 51, 270, 19));
        pnlInviteInfo.add(conMainContract, null);
        conEffectiveStartDate.setBounds(new Rectangle(327, 25, 270, 19));
        pnlInviteInfo.add(conEffectiveStartDate, null);
        conEffectiveEndDate.setBounds(new Rectangle(701, 24, 270, 19));
        pnlInviteInfo.add(conEffectiveEndDate, null);
        kDScrollPane1.setBounds(new Rectangle(330, 75, 642, 58));
        pnlInviteInfo.add(kDScrollPane1, null);
        conInformation.setBounds(new Rectangle(326, 51, 270, 19));
        pnlInviteInfo.add(conInformation, null);
        contlowestPrice.setBounds(new Rectangle(8, 33, 270, 19));
        pnlInviteInfo.add(contlowestPrice, null);
        contlowerPrice.setBounds(new Rectangle(8, 60, 270, 19));
        pnlInviteInfo.add(contlowerPrice, null);
        conthigherPrice.setBounds(new Rectangle(8, 114, 270, 19));
        pnlInviteInfo.add(conthigherPrice, null);
        contmiddlePrice.setBounds(new Rectangle(8, 87, 270, 19));
        pnlInviteInfo.add(contmiddlePrice, null);
        conthighestPrice.setBounds(new Rectangle(8, 141, 270, 19));
        pnlInviteInfo.add(conthighestPrice, null);
        contbasePrice.setBounds(new Rectangle(8, 33, 270, 19));
        pnlInviteInfo.add(contbasePrice, null);
        contsecondPrice.setBounds(new Rectangle(8, 60, 270, 19));
        pnlInviteInfo.add(contsecondPrice, null);
        continviteType.setBounds(new Rectangle(636, 114, 346, 19));
        pnlInviteInfo.add(continviteType, null);
        contwinPrice.setBounds(new Rectangle(636, 33, 346, 19));
        pnlInviteInfo.add(contwinPrice, null);
        contwinUnit.setBounds(new Rectangle(636, 60, 346, 19));
        pnlInviteInfo.add(contwinUnit, null);
        contfileNo.setBounds(new Rectangle(636, 141, 346, 19));
        pnlInviteInfo.add(contfileNo, null);
        contquantity.setBounds(new Rectangle(636, 87, 346, 19));
        pnlInviteInfo.add(contquantity, null);
        prmtlowestPriceUnit.setBounds(new Rectangle(298, 33, 292, 19));
        pnlInviteInfo.add(prmtlowestPriceUnit, null);
        prmtlowerPriceUnit.setBounds(new Rectangle(298, 60, 292, 19));
        pnlInviteInfo.add(prmtlowerPriceUnit, null);
        prmtmiddlePriceUnit.setBounds(new Rectangle(298, 87, 292, 19));
        pnlInviteInfo.add(prmtmiddlePriceUnit, null);
        prmthigherPriceUnit.setBounds(new Rectangle(298, 114, 292, 19));
        pnlInviteInfo.add(prmthigherPriceUnit, null);
        prmthighestPriceUni.setBounds(new Rectangle(298, 141, 292, 19));
        pnlInviteInfo.add(prmthighestPriceUni, null);
        lblPrice.setBounds(new Rectangle(169, 8, 58, 19));
        pnlInviteInfo.add(lblPrice, null);
        lblUnit.setBounds(new Rectangle(431, 8, 65, 19));
        pnlInviteInfo.add(lblUnit, null);
        lblOverRateContainer.setBounds(new Rectangle(36, 209, 270, 19));
        pnlInviteInfo.add(lblOverRateContainer, null);
        contpayPercForWarn.setBounds(new Rectangle(305, 208, 270, 19));
        pnlInviteInfo.add(contpayPercForWarn, null);
        contchgPercForWarn.setBounds(new Rectangle(598, 206, 270, 19));
        pnlInviteInfo.add(contchgPercForWarn, null);
        contcostProperty.setBounds(new Rectangle(70, 223, 270, 19));
        pnlInviteInfo.add(contcostProperty, null);
        contStampTaxRate.setBounds(new Rectangle(358, 228, 270, 19));
        pnlInviteInfo.add(contStampTaxRate, null);
        contStampTaxAmt.setBounds(new Rectangle(715, 228, 270, 19));
        pnlInviteInfo.add(contStampTaxAmt, null);
        contPayScale.setBounds(new Rectangle(702, 187, 270, 19));
        pnlInviteInfo.add(contPayScale, null);
        prmtFwContractTemp.setBounds(new Rectangle(7, 186, 170, 19));
        pnlInviteInfo.add(prmtFwContractTemp, null);
        contAttachmentNameList.setBounds(new Rectangle(317, 163, 636, 19));
        pnlInviteInfo.add(contAttachmentNameList, null);
        btnViewAttachment.setBounds(new Rectangle(511, 184, 87, 21));
        pnlInviteInfo.add(btnViewAttachment, null);
        conChargeType.setBounds(new Rectangle(539, 3, 270, 19));
        pnlInviteInfo.add(conChargeType, null);
        //contRemark
        contRemark.setBoundEditor(txtRemark);
        //contCoopLevel
        contCoopLevel.setBoundEditor(comboCoopLevel);
        //contPriceType
        contPriceType.setBoundEditor(comboPriceType);
        //conMainContract
        conMainContract.setBoundEditor(prmtMainContract);
        //conEffectiveStartDate
        conEffectiveStartDate.setBoundEditor(kdpEffectStartDate);
        //conEffectiveEndDate
        conEffectiveEndDate.setBoundEditor(kdpEffectiveEndDate);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtInformation, null);
        //contlowestPrice
        contlowestPrice.setBoundEditor(txtlowestPrice);
        //contlowerPrice
        contlowerPrice.setBoundEditor(txtlowerPrice);
        //conthigherPrice
        conthigherPrice.setBoundEditor(txthigherPrice);
        //contmiddlePrice
        contmiddlePrice.setBoundEditor(txtmiddlePrice);
        //conthighestPrice
        conthighestPrice.setBoundEditor(txthighestPrice);
        //contbasePrice
        contbasePrice.setBoundEditor(txtbasePrice);
        //contsecondPrice
        contsecondPrice.setBoundEditor(txtsecondPrice);
        //continviteType
        continviteType.setBoundEditor(prmtinviteType);
        //contwinPrice
        contwinPrice.setBoundEditor(txtwinPrice);
        //contwinUnit
        contwinUnit.setBoundEditor(prmtwinUnit);
        //contfileNo
        contfileNo.setBoundEditor(txtfileNo);
        //contquantity
        contquantity.setBoundEditor(txtquantity);
        //lblOverRateContainer
        lblOverRateContainer.setBoundEditor(txtOverAmt);
        //contpayPercForWarn
        contpayPercForWarn.setBoundEditor(txtpayPercForWarn);
        //contchgPercForWarn
        contchgPercForWarn.setBoundEditor(txtchgPercForWarn);
        //contcostProperty
        contcostProperty.setBoundEditor(costProperty);
        //contStampTaxRate
        contStampTaxRate.setBoundEditor(txtStampTaxRate);
        //contStampTaxAmt
        contStampTaxAmt.setBoundEditor(txtStampTaxAmt);
        //contPayScale
        contPayScale.setBoundEditor(txtPayScale);
        //contAttachmentNameList
        contAttachmentNameList.setBoundEditor(comboAttachmentNameList);
        //conChargeType
        conChargeType.setBoundEditor(prmtCharge);
        //pnlDetail
        pnlDetail.getViewport().add(tblDetail, null);
        //pnlCost
        pnlCost.setLayout(new KDLayout());
        pnlCost.putClientProperty("OriginalBounds", new Rectangle(0, 0, 999, 223));        tblCost.setBounds(new Rectangle(10, 10, 965, 247));
        pnlCost.add(tblCost, new KDLayout.Constraints(10, 10, 965, 247, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        //panelInvite
panelInvite.setLayout(new BorderLayout(0, 0));        panelInvite.add(tblInvite, BorderLayout.CENTER);
        //contExRate
        contExRate.setBoundEditor(txtExRate);
        //contLocalAmount
        contLocalAmount.setBoundEditor(txtLocalAmount);
        //contGrtAmount
        contGrtAmount.setBoundEditor(txtGrtAmount);
        //contCurrency
        contCurrency.setBoundEditor(comboCurrency);
        //contRespPerson
        contRespPerson.setBoundEditor(prmtRespPerson);
        //contCreator
        contCreator.setBoundEditor(txtCreator);
        //contOrg
        contOrg.setBoundEditor(txtOrg);
        //contProj
        contProj.setBoundEditor(txtProj);
        //contGrtRate
        contGrtRate.setBoundEditor(txtGrtRate);
        //contIsPartAMaterialCon
        contIsPartAMaterialCon.setBoundEditor(cbPeriod);
        //conContrarctRule
        conContrarctRule.setBoundEditor(textFwContract);
        //contOrgAmtBig
        contOrgAmtBig.setBoundEditor(txtOrgAmtBig);
        //contAmtBig
        contAmtBig.setBoundEditor(txtAmtBig);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(pkbookedDate);
        //contCreateOrg
        contCreateOrg.setBoundEditor(prmtCreateOrg);
        //contDes
        contDes.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtDes, null);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(7, 119, 276, 88));        chkIsOpen.setBounds(new Rectangle(134, 12, 129, 19));
        kDPanel1.add(chkIsOpen, new KDLayout.Constraints(134, 12, 129, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsStandardContract.setBounds(new Rectangle(17, 12, 101, 19));
        kDPanel1.add(chkIsStandardContract, new KDLayout.Constraints(17, 12, 101, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labSettleType.setBounds(new Rectangle(17, 32, 79, 19));
        kDPanel1.add(labSettleType, new KDLayout.Constraints(17, 32, 79, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAType.setBounds(new Rectangle(108, 32, 40, 19));
        kDPanel1.add(btnAType, new KDLayout.Constraints(108, 32, 40, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnBType.setBounds(new Rectangle(160, 32, 45, 19));
        kDPanel1.add(btnBType, new KDLayout.Constraints(160, 32, 45, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        BtnCType.setBounds(new Rectangle(212, 32, 44, 19));
        kDPanel1.add(BtnCType, new KDLayout.Constraints(212, 32, 44, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsPartAMaterialCon.setBounds(new Rectangle(134, 53, 126, 19));
        kDPanel1.add(chkIsPartAMaterialCon, new KDLayout.Constraints(134, 53, 126, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkCostSplit.setBounds(new Rectangle(17, 53, 99, 19));
        kDPanel1.add(chkCostSplit, new KDLayout.Constraints(17, 53, 99, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contRespDept
        contRespDept.setBoundEditor(prmtRespDept);
        //contsignDate
        contsignDate.setBoundEditor(pksignDate);
        //contOrgType
        contOrgType.setBoundEditor(cbOrgType);
        //contcontractSource
        contcontractSource.setBoundEditor(contractSource);
        //contContractWFType
        contContractWFType.setBoundEditor(prmtContractWFType);
        //contAttachment
contAttachment.getContentPane().setLayout(new BorderLayout(0, 0));        contAttachment.getContentPane().add(tblAttachement, BorderLayout.CENTER);
        //contMode
contMode.getContentPane().setLayout(new BorderLayout(0, 0));        contMode.getContentPane().add(prmtModel, BorderLayout.CENTER);
        //contSrcAmount
        contSrcAmount.setBoundEditor(txtSrcAmount);
        //contInviteType
        contInviteType.setBoundEditor(prmtInviteType);
        //contNeedPerson
        contNeedPerson.setBoundEditor(prmtNeedPerson);
        //contNeedDept
        contNeedDept.setBoundEditor(prmtNeedDept);
        //ecoItemPanel
ecoItemPanel.setLayout(new BorderLayout(0, 0));        ecoItemPanel.add(kDContainer1, BorderLayout.CENTER);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDSplitPane1, BorderLayout.CENTER);
        //kDSplitPane1
        kDSplitPane1.add(contPayItem, "top");
        kDSplitPane1.add(contBailItem, "bottom");
        //contPayItem
contPayItem.getContentPane().setLayout(new BorderLayout(0, 0));        contPayItem.getContentPane().add(tblEconItem, BorderLayout.CENTER);
        //contBailItem
        contBailItem.getContentPane().setLayout(new KDLayout());
        contBailItem.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 1011, 361));        contBailOriAmount.setBounds(new Rectangle(5, 8, 463, 19));
        contBailItem.getContentPane().add(contBailOriAmount, new KDLayout.Constraints(5, 8, 463, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBailRate.setBounds(new Rectangle(544, 8, 450, 19));
        contBailItem.getContentPane().add(contBailRate, new KDLayout.Constraints(544, 8, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        tblBail.setBounds(new Rectangle(3, 40, 995, 218));
        contBailItem.getContentPane().add(tblBail, new KDLayout.Constraints(3, 40, 995, 218, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contBailOriAmount
        contBailOriAmount.setBoundEditor(txtBailOriAmount);
        //contBailRate
        contBailRate.setBoundEditor(txtBailRate);

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
        menuView.add(kDSeparator7);
        menuView.add(menuItemViewContent);
        menuView.add(menuItemLocate);
        menuView.add(menuItemViewBgBalance);
        menuView.add(menuItemViewProg);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemSplit);
        menuBiz.add(menuItemDelSplit);
        menuBiz.add(menuItemContractPayPlan);
        menuBiz.add(enuItemViewCost);
        menuBiz.add(menuItemProgram);
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
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnProgram);
        this.toolBar.add(btnAccountView);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnSplit);
        this.toolBar.add(btnDelSplit);
        this.toolBar.add(btnViewContent);
        this.toolBar.add(btnContractPlan);
        this.toolBar.add(btnViewCost);
        this.toolBar.add(btnViewBgBalance);
        this.toolBar.add(btnViewProgramContract);
        this.toolBar.add(btnAgreement);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("originalAmount", java.math.BigDecimal.class, this.txtamount, "value");
		dataBinder.registerBinding("landDeveloper", com.kingdee.eas.fdc.basedata.LandDeveloperInfo.class, this.prmtlandDeveloper, "data");
		dataBinder.registerBinding("contractType", com.kingdee.eas.fdc.basedata.ContractTypeInfo.class, this.prmtcontractType, "data");
		dataBinder.registerBinding("contractPropert", com.kingdee.eas.fdc.contract.ContractPropertyEnum.class, this.contractPropert, "selectedItem");
		dataBinder.registerBinding("partB", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtpartB, "data");
		dataBinder.registerBinding("partC", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtpartC, "data");
		dataBinder.registerBinding("name", String.class, this.txtcontractName, "text");
		dataBinder.registerBinding("isSubContract", boolean.class, this.chkIsSubMainContract, "selected");
		dataBinder.registerBinding("lowestPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtlowestPriceUnit, "data");
		dataBinder.registerBinding("lowerPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtlowerPriceUnit, "data");
		dataBinder.registerBinding("middlePriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtmiddlePriceUnit, "data");
		dataBinder.registerBinding("higherPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmthigherPriceUnit, "data");
		dataBinder.registerBinding("highestPriceUni", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmthighestPriceUni, "data");
		dataBinder.registerBinding("remark", String.class, this.txtRemark, "text");
		dataBinder.registerBinding("coopLevel", com.kingdee.eas.fdc.contract.CoopLevelEnum.class, this.comboCoopLevel, "selectedItem");
		dataBinder.registerBinding("priceType", com.kingdee.eas.fdc.contract.PriceTypeEnum.class, this.comboPriceType, "selectedItem");
		dataBinder.registerBinding("mainContract", com.kingdee.eas.fdc.contract.ContractBillCollection.class, this.prmtMainContract, "data");
		dataBinder.registerBinding("effectiveStartDate", java.util.Date.class, this.kdpEffectStartDate, "value");
		dataBinder.registerBinding("effectiveEndDate", java.util.Date.class, this.kdpEffectiveEndDate, "value");
		dataBinder.registerBinding("information", String.class, this.txtInformation, "text");
		dataBinder.registerBinding("lowestPrice", java.math.BigDecimal.class, this.txtlowestPrice, "value");
		dataBinder.registerBinding("lowerPrice", java.math.BigDecimal.class, this.txtlowerPrice, "value");
		dataBinder.registerBinding("higherPrice", java.math.BigDecimal.class, this.txthigherPrice, "value");
		dataBinder.registerBinding("middlePrice", java.math.BigDecimal.class, this.txtmiddlePrice, "value");
		dataBinder.registerBinding("highestPrice", java.math.BigDecimal.class, this.txthighestPrice, "value");
		dataBinder.registerBinding("basePrice", java.math.BigDecimal.class, this.txtbasePrice, "value");
		dataBinder.registerBinding("secondPrice", java.math.BigDecimal.class, this.txtsecondPrice, "value");
		dataBinder.registerBinding("winPrice", java.math.BigDecimal.class, this.txtwinPrice, "value");
		dataBinder.registerBinding("winUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtwinUnit, "data");
		dataBinder.registerBinding("fileNo", String.class, this.txtfileNo, "text");
		dataBinder.registerBinding("quantity", java.math.BigDecimal.class, this.txtquantity, "value");
		dataBinder.registerBinding("payPercForWarn", java.math.BigDecimal.class, this.txtpayPercForWarn, "value");
		dataBinder.registerBinding("chgPercForWarn", java.math.BigDecimal.class, this.txtchgPercForWarn, "value");
		dataBinder.registerBinding("costProperty", com.kingdee.eas.fdc.contract.CostPropertyEnum.class, this.costProperty, "selectedItem");
		dataBinder.registerBinding("payScale", java.math.BigDecimal.class, this.txtPayScale, "value");
		dataBinder.registerBinding("conChargeType", com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo.class, this.prmtCharge, "data");
		dataBinder.registerBinding("exRate", java.math.BigDecimal.class, this.txtExRate, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtLocalAmount, "value");
		dataBinder.registerBinding("grtAmount", java.math.BigDecimal.class, this.txtGrtAmount, "value");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.comboCurrency, "selectedItem");
		dataBinder.registerBinding("respPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtRespPerson, "data");
		dataBinder.registerBinding("creator.name", String.class, this.txtCreator, "text");
		dataBinder.registerBinding("grtRate", java.math.BigDecimal.class, this.txtGrtRate, "value");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.cbPeriod, "data");
		dataBinder.registerBinding("programmingContract.name", String.class, this.textFwContract, "text");
		dataBinder.registerBinding("bookedDate", java.util.Date.class, this.pkbookedDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDes, "text");
		dataBinder.registerBinding("isOpenContract", boolean.class, this.chkIsOpen, "selected");
		dataBinder.registerBinding("isStardContract", boolean.class, this.chkIsStandardContract, "selected");
		dataBinder.registerBinding("isPartAMaterialCon", boolean.class, this.chkIsPartAMaterialCon, "selected");
		dataBinder.registerBinding("isCoseSplit", boolean.class, this.chkCostSplit, "selected");
		dataBinder.registerBinding("respDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtRespDept, "data");
		dataBinder.registerBinding("orgType", com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum.class, this.cbOrgType, "selectedItem");
		dataBinder.registerBinding("contractSourceId", com.kingdee.eas.fdc.basedata.ContractSourceInfo.class, this.contractSource, "data");
		dataBinder.registerBinding("contractWFType", com.kingdee.eas.fdc.contract.ContractWFTypeInfo.class, this.prmtContractWFType, "data");
		dataBinder.registerBinding("model", com.kingdee.eas.base.attachment.AttachmentInfo.class, this.prmtModel, "data");
		dataBinder.registerBinding("srcAmount", java.math.BigDecimal.class, this.txtSrcAmount, "value");
		dataBinder.registerBinding("inviteType", com.kingdee.eas.fdc.basedata.InviteTypeInfo.class, this.prmtInviteType, "data");
		dataBinder.registerBinding("needPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtNeedPerson, "data");
		dataBinder.registerBinding("needDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtNeedDept, "data");
		dataBinder.registerBinding("payItems", com.kingdee.eas.fdc.contract.ContractPayItemInfo.class, this.tblEconItem, "userObject");
		dataBinder.registerBinding("payItems.payItemDate", java.util.Date.class, this.tblEconItem, "date.text");
		dataBinder.registerBinding("payItems.payCondition", String.class, this.tblEconItem, "payCondition.text");
		dataBinder.registerBinding("payItems.prop", java.math.BigDecimal.class, this.tblEconItem, "payRate.text");
		dataBinder.registerBinding("payItems.amount", java.math.BigDecimal.class, this.tblEconItem, "payAmount.text");
		dataBinder.registerBinding("payItems.desc", String.class, this.tblEconItem, "desc.text");
		dataBinder.registerBinding("payItems.paymentType", com.kingdee.eas.fdc.basedata.PaymentTypeInfo.class, this.tblEconItem, "payType.text");
		dataBinder.registerBinding("bail.entry.bailDate", java.util.Date.class, this.tblBail, "bailDate.text");
		dataBinder.registerBinding("bail.entry.bailConditon", String.class, this.tblBail, "bailCondition.text");
		dataBinder.registerBinding("bail.entry.prop", java.math.BigDecimal.class, this.tblBail, "bailRate.text");
		dataBinder.registerBinding("bail.entry.amount", java.math.BigDecimal.class, this.tblBail, "bailAmount.text");
		dataBinder.registerBinding("bail.entry.desc", String.class, this.tblBail, "desc.text");
		dataBinder.registerBinding("bail.entry", com.kingdee.eas.fdc.contract.ContractBailEntryInfo.class, this.tblBail, "userObject");
		dataBinder.registerBinding("bail.amount", java.math.BigDecimal.class, this.txtBailOriAmount, "value");
		dataBinder.registerBinding("bail.prop", java.math.BigDecimal.class, this.txtBailRate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionUnAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionSplit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionViewContent, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionContractPlan, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAttachment, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionWorkFlowG, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractBillEditUIHandler";
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
        this.txtOrg.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.contract.ContractBillInfo)ov;
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
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("landDeveloper", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractPropert", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("partB", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("partC", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isSubContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowestPriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowerPriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("middlePriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("higherPriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("highestPriceUni", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("coopLevel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mainContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("effectiveStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("effectiveEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("information", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowestPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowerPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("higherPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("middlePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("highestPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("basePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("secondPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("winPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("winUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fileNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("quantity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payPercForWarn", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chgPercForWarn", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costProperty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payScale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conChargeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("grtAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("respPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("grtRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("programmingContract.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isOpenContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isStardContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isPartAMaterialCon", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isCoseSplit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("respDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractSourceId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractWFType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("model", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("srcAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("needPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("needDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.payItemDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.payCondition", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.prop", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.desc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.paymentType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.bailDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.bailConditon", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.prop", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.desc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.prop", ValidateHelper.ON_SAVE);    		
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
		            this.actionAudit.setVisible(false);
		            this.actionAudit.setEnabled(false);
		            this.actionUnAudit.setVisible(false);
		            this.actionUnAudit.setEnabled(false);
		            this.actionSplit.setVisible(false);
		            this.actionSplit.setEnabled(false);
		            this.actionViewContent.setVisible(false);
		            this.actionViewContent.setEnabled(false);
		            this.actionContractPlan.setVisible(false);
		            this.actionContractPlan.setEnabled(false);
		            this.actionAttachment.setVisible(false);
		            this.actionAttachment.setEnabled(false);
		            this.actionWorkFlowG.setVisible(false);
		            this.actionWorkFlowG.setEnabled(false);
        }
    }

    /**
     * output txtamount_dataChanged method
     */
    protected void txtamount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtamount_focusGained method
     */
    protected void txtamount_focusGained(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output prmtcontractType_dataChanged method
     */
    protected void prmtcontractType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtcontractType_willShow method
     */
    protected void prmtcontractType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output contractPropert_itemStateChanged method
     */
    protected void contractPropert_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output kdtSupplyEntry_tableClicked method
     */
    protected void kdtSupplyEntry_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnViewAttachment_actionPerformed method
     */
    protected void btnViewAttachment_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtMainContract_willShow method
     */
    protected void prmtMainContract_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output costProperty_itemStateChanged method
     */
    protected void costProperty_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output txtStampTaxRate_dataChanged method
     */
    protected void txtStampTaxRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblDetail_editStopping method
     */
    protected void tblDetail_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblCost_tableClicked method
     */
    protected void tblCost_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblInvite_tableClicked method
     */
    protected void tblInvite_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output txtExRate_dataChanged method
     */
    protected void txtExRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboCurrency_itemStateChanged method
     */
    protected void comboCurrency_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output txtCreator_actionPerformed method
     */
    protected void txtCreator_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtGrtRate_dataChanged method
     */
    protected void txtGrtRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkbookedDate_dataChanged method
     */
    protected void pkbookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output chkIsPartAMaterialCon_mouseClicked method
     */
    protected void chkIsPartAMaterialCon_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output chkCostSplit_mouseClicked method
     */
    protected void chkCostSplit_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output contractSource_willShow method
     */
    protected void contractSource_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output contractSource_dataChanged method
     */
    protected void contractSource_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtContractWFType_willShow method
     */
    protected void prmtContractWFType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
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
     * output prmtModel_dataChanged method
     */
    protected void prmtModel_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtModel_willShow method
     */
    protected void prmtModel_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output tblEconItem_editStopped method
     */
    protected void tblEconItem_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblBail_editStopped method
     */
    protected void tblBail_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output txtBailOriAmount_dataChanged method
     */
    protected void txtBailOriAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtBailRate_dataChanged method
     */
    protected void txtBailRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output menuItemContractPayPlan_actionPerformed method
     */
    protected void menuItemContractPayPlan_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("originalAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("landDeveloper.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("landDeveloper.id"));
        	sic.add(new SelectorItemInfo("landDeveloper.number"));
        	sic.add(new SelectorItemInfo("landDeveloper.name"));
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
        sic.add(new SelectorItemInfo("contractPropert"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("partB.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("partB.id"));
        	sic.add(new SelectorItemInfo("partB.number"));
        	sic.add(new SelectorItemInfo("partB.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("partC.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("partC.id"));
        	sic.add(new SelectorItemInfo("partC.number"));
        	sic.add(new SelectorItemInfo("partC.name"));
		}
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("isSubContract"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lowestPriceUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lowestPriceUnit.id"));
        	sic.add(new SelectorItemInfo("lowestPriceUnit.number"));
        	sic.add(new SelectorItemInfo("lowestPriceUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lowerPriceUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lowerPriceUnit.id"));
        	sic.add(new SelectorItemInfo("lowerPriceUnit.number"));
        	sic.add(new SelectorItemInfo("lowerPriceUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("middlePriceUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("middlePriceUnit.id"));
        	sic.add(new SelectorItemInfo("middlePriceUnit.number"));
        	sic.add(new SelectorItemInfo("middlePriceUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("higherPriceUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("higherPriceUnit.id"));
        	sic.add(new SelectorItemInfo("higherPriceUnit.number"));
        	sic.add(new SelectorItemInfo("higherPriceUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("highestPriceUni.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("highestPriceUni.id"));
        	sic.add(new SelectorItemInfo("highestPriceUni.number"));
        	sic.add(new SelectorItemInfo("highestPriceUni.name"));
		}
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("coopLevel"));
        sic.add(new SelectorItemInfo("priceType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("mainContract.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("mainContract.id"));
        	sic.add(new SelectorItemInfo("mainContract.number"));
        	sic.add(new SelectorItemInfo("mainContract.name"));
		}
        sic.add(new SelectorItemInfo("effectiveStartDate"));
        sic.add(new SelectorItemInfo("effectiveEndDate"));
        sic.add(new SelectorItemInfo("information"));
        sic.add(new SelectorItemInfo("lowestPrice"));
        sic.add(new SelectorItemInfo("lowerPrice"));
        sic.add(new SelectorItemInfo("higherPrice"));
        sic.add(new SelectorItemInfo("middlePrice"));
        sic.add(new SelectorItemInfo("highestPrice"));
        sic.add(new SelectorItemInfo("basePrice"));
        sic.add(new SelectorItemInfo("secondPrice"));
        sic.add(new SelectorItemInfo("winPrice"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("winUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("winUnit.id"));
        	sic.add(new SelectorItemInfo("winUnit.number"));
        	sic.add(new SelectorItemInfo("winUnit.name"));
		}
        sic.add(new SelectorItemInfo("fileNo"));
        sic.add(new SelectorItemInfo("quantity"));
        sic.add(new SelectorItemInfo("payPercForWarn"));
        sic.add(new SelectorItemInfo("chgPercForWarn"));
        sic.add(new SelectorItemInfo("costProperty"));
        sic.add(new SelectorItemInfo("payScale"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("conChargeType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("conChargeType.id"));
        	sic.add(new SelectorItemInfo("conChargeType.number"));
        	sic.add(new SelectorItemInfo("conChargeType.name"));
		}
        sic.add(new SelectorItemInfo("exRate"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("grtAmount"));
        sic.add(new SelectorItemInfo("currency"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("respPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("respPerson.id"));
        	sic.add(new SelectorItemInfo("respPerson.number"));
        	sic.add(new SelectorItemInfo("respPerson.name"));
		}
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("grtRate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("period.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("period.id"));
        	sic.add(new SelectorItemInfo("period.number"));
		}
        sic.add(new SelectorItemInfo("programmingContract.name"));
        sic.add(new SelectorItemInfo("bookedDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isOpenContract"));
        sic.add(new SelectorItemInfo("isStardContract"));
        sic.add(new SelectorItemInfo("isPartAMaterialCon"));
        sic.add(new SelectorItemInfo("isCoseSplit"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("respDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("respDept.id"));
        	sic.add(new SelectorItemInfo("respDept.number"));
        	sic.add(new SelectorItemInfo("respDept.name"));
		}
        sic.add(new SelectorItemInfo("orgType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractSourceId.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractSourceId.id"));
        	sic.add(new SelectorItemInfo("contractSourceId.number"));
        	sic.add(new SelectorItemInfo("contractSourceId.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractWFType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractWFType.id"));
        	sic.add(new SelectorItemInfo("contractWFType.number"));
        	sic.add(new SelectorItemInfo("contractWFType.name"));
        	sic.add(new SelectorItemInfo("contractWFType.longNumber"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("model.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("model.id"));
        	sic.add(new SelectorItemInfo("model.number"));
        	sic.add(new SelectorItemInfo("model.name"));
		}
        sic.add(new SelectorItemInfo("srcAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("inviteType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("inviteType.id"));
        	sic.add(new SelectorItemInfo("inviteType.number"));
        	sic.add(new SelectorItemInfo("inviteType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("needPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("needPerson.id"));
        	sic.add(new SelectorItemInfo("needPerson.number"));
        	sic.add(new SelectorItemInfo("needPerson.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("needDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("needDept.id"));
        	sic.add(new SelectorItemInfo("needDept.number"));
        	sic.add(new SelectorItemInfo("needDept.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payItems.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("payItems.payItemDate"));
    	sic.add(new SelectorItemInfo("payItems.payCondition"));
    	sic.add(new SelectorItemInfo("payItems.prop"));
    	sic.add(new SelectorItemInfo("payItems.amount"));
    	sic.add(new SelectorItemInfo("payItems.desc"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payItems.paymentType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("payItems.paymentType.id"));
			sic.add(new SelectorItemInfo("payItems.paymentType.name"));
        	sic.add(new SelectorItemInfo("payItems.paymentType.number"));
		}
    	sic.add(new SelectorItemInfo("bail.entry.bailDate"));
    	sic.add(new SelectorItemInfo("bail.entry.bailConditon"));
    	sic.add(new SelectorItemInfo("bail.entry.prop"));
    	sic.add(new SelectorItemInfo("bail.entry.amount"));
    	sic.add(new SelectorItemInfo("bail.entry.desc"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("bail.entry.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("bail.entry.id"));
		}
        sic.add(new SelectorItemInfo("bail.amount"));
        sic.add(new SelectorItemInfo("bail.prop"));
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
     * output actionViewContent_actionPerformed method
     */
    public void actionViewContent_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionContractPlan_actionPerformed method
     */
    public void actionContractPlan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelSplit_actionPerformed method
     */
    public void actionDelSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewCost_actionPerformed method
     */
    public void actionViewCost_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewBgBalance_actionPerformed method
     */
    public void actionViewBgBalance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewAttachmentSelf_actionPerformed method
     */
    public void actionViewAttachmentSelf_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContentSelf_actionPerformed method
     */
    public void actionViewContentSelf_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionProgram_actionPerformed method
     */
    public void actionProgram_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewProgramCon_actionPerformed method
     */
    public void actionViewProgramCon_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAgreementText_actionPerformed method
     */
    public void actionAgreementText_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpLoad_actionPerformed method
     */
    public void actionUpLoad_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDownLoad_actionPerformed method
     */
    public void actionDownLoad_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAccountView_actionPerformed method
     */
    public void actionAccountView_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionViewContent(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContent() {
    	return false;
    }
	public RequestContext prepareActionContractPlan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionContractPlan() {
    	return false;
    }
	public RequestContext prepareActionDelSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelSplit() {
    	return false;
    }
	public RequestContext prepareActionViewCost(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewCost() {
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
	public RequestContext prepareActionViewAttachmentSelf(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewAttachmentSelf() {
    	return false;
    }
	public RequestContext prepareActionViewContentSelf(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContentSelf() {
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
	public RequestContext prepareActionViewProgramCon(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewProgramCon() {
    	return false;
    }
	public RequestContext prepareActionAgreementText(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAgreementText() {
    	return false;
    }
	public RequestContext prepareActionUpLoad(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpLoad() {
    	return false;
    }
	public RequestContext prepareActionDownLoad(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDownLoad() {
    	return false;
    }
	public RequestContext prepareActionAccountView(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAccountView() {
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
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionSplit", "actionSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContent class
     */     
    protected class ActionViewContent extends ItemAction {     
    
        public ActionViewContent()
        {
            this(null);
        }

        public ActionViewContent(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewContent.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContent.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContent.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionViewContent", "actionViewContent_actionPerformed", e);
        }
    }

    /**
     * output ActionContractPlan class
     */     
    protected class ActionContractPlan extends ItemAction {     
    
        public ActionContractPlan()
        {
            this(null);
        }

        public ActionContractPlan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift P"));
            _tempStr = resHelper.getString("ActionContractPlan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractPlan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractPlan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionContractPlan", "actionContractPlan_actionPerformed", e);
        }
    }

    /**
     * output ActionDelSplit class
     */     
    protected class ActionDelSplit extends ItemAction {     
    
        public ActionDelSplit()
        {
            this(null);
        }

        public ActionDelSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionDelSplit", "actionDelSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionViewCost class
     */     
    protected class ActionViewCost extends ItemAction {     
    
        public ActionViewCost()
        {
            this(null);
        }

        public ActionViewCost(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewCost.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewCost.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewCost.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionViewCost", "actionViewCost_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionViewBgBalance", "actionViewBgBalance_actionPerformed", e);
        }
    }

    /**
     * output ActionViewAttachmentSelf class
     */     
    protected class ActionViewAttachmentSelf extends ItemAction {     
    
        public ActionViewAttachmentSelf()
        {
            this(null);
        }

        public ActionViewAttachmentSelf(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewAttachmentSelf.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAttachmentSelf.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAttachmentSelf.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionViewAttachmentSelf", "actionViewAttachmentSelf_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContentSelf class
     */     
    protected class ActionViewContentSelf extends ItemAction {     
    
        public ActionViewContentSelf()
        {
            this(null);
        }

        public ActionViewContentSelf(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewContentSelf.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContentSelf.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContentSelf.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionViewContentSelf", "actionViewContentSelf_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionProgram", "actionProgram_actionPerformed", e);
        }
    }

    /**
     * output ActionViewProgramCon class
     */     
    protected class ActionViewProgramCon extends ItemAction {     
    
        public ActionViewProgramCon()
        {
            this(null);
        }

        public ActionViewProgramCon(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewProgramCon.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewProgramCon.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewProgramCon.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionViewProgramCon", "actionViewProgramCon_actionPerformed", e);
        }
    }

    /**
     * output ActionAgreementText class
     */     
    protected class ActionAgreementText extends ItemAction {     
    
        public ActionAgreementText()
        {
            this(null);
        }

        public ActionAgreementText(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAgreementText.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAgreementText.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAgreementText.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionAgreementText", "actionAgreementText_actionPerformed", e);
        }
    }

    /**
     * output ActionUpLoad class
     */     
    protected class ActionUpLoad extends ItemAction {     
    
        public ActionUpLoad()
        {
            this(null);
        }

        public ActionUpLoad(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUpLoad.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpLoad.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpLoad.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionUpLoad", "actionUpLoad_actionPerformed", e);
        }
    }

    /**
     * output ActionDownLoad class
     */     
    protected class ActionDownLoad extends ItemAction {     
    
        public ActionDownLoad()
        {
            this(null);
        }

        public ActionDownLoad(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDownLoad.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownLoad.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownLoad.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionDownLoad", "actionDownLoad_actionPerformed", e);
        }
    }

    /**
     * output ActionAccountView class
     */     
    protected class ActionAccountView extends ItemAction {     
    
        public ActionAccountView()
        {
            this(null);
        }

        public ActionAccountView(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAccountView.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAccountView.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAccountView.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionAccountView", "actionAccountView_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractBillEditUI");
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
        return com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.ContractBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.ContractBillInfo objectValue = new com.kingdee.eas.fdc.contract.ContractBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/contract/ContractBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractBillQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return tblDetail;
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