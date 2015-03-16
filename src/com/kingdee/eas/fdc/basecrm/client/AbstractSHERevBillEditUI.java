/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

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
public abstract class AbstractSHERevBillEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSHERevBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contexchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrevAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer controom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrevBillType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayCustomerName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreceiptNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrelateBizBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrelateFromBizBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRelateFromCust;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdAddnewLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdInserLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdRemoveLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDSelectTransDetail;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdSelectRefundDetail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReceipt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoice;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcurrency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsellProject;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtexchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditTime;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtrevAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtroom;
    protected com.kingdee.bos.ctrl.swing.KDComboBox revBillType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpayCustomerName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtreceiptNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtinvoiceNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRelateBizBill;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRelateFromBizBill;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomerEntry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRelateFromCust;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtReceipt;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSubmitAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMakeInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRecycle;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuSubmitAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuMakeInvoice;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuRecycle;
    protected com.kingdee.eas.fdc.basecrm.SHERevBillInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionMakeInvoice actionMakeInvoice = null;
    protected ActionRecycle actionRecycle = null;
    protected ActionSubmitAudit actionSubmitAudit = null;
    /**
     * output class constructor
     */
    public AbstractSHERevBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSHERevBillEditUI.class.getName());
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
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMakeInvoice
        this.actionMakeInvoice = new ActionMakeInvoice(this);
        getActionManager().registerAction("actionMakeInvoice", actionMakeInvoice);
         this.actionMakeInvoice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRecycle
        this.actionRecycle = new ActionRecycle(this);
        getActionManager().registerAction("actionRecycle", actionRecycle);
         this.actionRecycle.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSubmitAudit
        this.actionSubmitAudit = new ActionSubmitAudit(this);
        getActionManager().registerAction("actionSubmitAudit", actionSubmitAudit);
         this.actionSubmitAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contexchangeRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrevAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.controom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrevBillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayCustomerName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreceiptNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continvoiceNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrelateBizBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrelateFromBizBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomerEntry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRelateFromCust = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdAddnewLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdInserLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdRemoveLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSelectTransDetail = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdSelectRefundDetail = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contReceipt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtexchangeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkauditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtrevAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtroom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.revBillType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtpayCustomerName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtreceiptNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtinvoiceNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtRelateBizBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRelateFromBizBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCustomerEntry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRelateFromCust = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtReceipt = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtInvoice = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnSubmitAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnMakeInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRecycle = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuSubmitAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuMakeInvoice = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuRecycle = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contcurrency.setName("contcurrency");
        this.contsellProject.setName("contsellProject");
        this.contexchangeRate.setName("contexchangeRate");
        this.contauditTime.setName("contauditTime");
        this.contrevAmount.setName("contrevAmount");
        this.controom.setName("controom");
        this.contrevBillType.setName("contrevBillType");
        this.contpayCustomerName.setName("contpayCustomerName");
        this.contreceiptNumber.setName("contreceiptNumber");
        this.continvoiceNumber.setName("continvoiceNumber");
        this.contrelateBizBill.setName("contrelateBizBill");
        this.contrelateFromBizBill.setName("contrelateFromBizBill");
        this.contCustomerEntry.setName("contCustomerEntry");
        this.contRelateFromCust.setName("contRelateFromCust");
        this.kdtEntrys.setName("kdtEntrys");
        this.kdAddnewLine.setName("kdAddnewLine");
        this.kdInserLine.setName("kdInserLine");
        this.kdRemoveLine.setName("kdRemoveLine");
        this.kDSelectTransDetail.setName("kDSelectTransDetail");
        this.kdSelectRefundDetail.setName("kdSelectRefundDetail");
        this.contReceipt.setName("contReceipt");
        this.contInvoice.setName("contInvoice");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtcurrency.setName("prmtcurrency");
        this.prmtsellProject.setName("prmtsellProject");
        this.txtexchangeRate.setName("txtexchangeRate");
        this.pkauditTime.setName("pkauditTime");
        this.txtrevAmount.setName("txtrevAmount");
        this.prmtroom.setName("prmtroom");
        this.revBillType.setName("revBillType");
        this.txtpayCustomerName.setName("txtpayCustomerName");
        this.txtreceiptNumber.setName("txtreceiptNumber");
        this.txtinvoiceNumber.setName("txtinvoiceNumber");
        this.prmtRelateBizBill.setName("prmtRelateBizBill");
        this.prmtRelateFromBizBill.setName("prmtRelateFromBizBill");
        this.prmtCustomerEntry.setName("prmtCustomerEntry");
        this.prmtRelateFromCust.setName("prmtRelateFromCust");
        this.prmtReceipt.setName("prmtReceipt");
        this.prmtInvoice.setName("prmtInvoice");
        this.btnSubmitAudit.setName("btnSubmitAudit");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnMakeInvoice.setName("btnMakeInvoice");
        this.btnRecycle.setName("btnRecycle");
        this.menuSubmitAudit.setName("menuSubmitAudit");
        this.menuAudit.setName("menuAudit");
        this.menuUnAudit.setName("menuUnAudit");
        this.menuMakeInvoice.setName("menuMakeInvoice");
        this.menuRecycle.setName("menuRecycle");
        // CoreUI		
        this.btnAddLine.setVisible(false);		
        this.btnCopyLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.separator1.setVisible(false);		
        this.separator3.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewDoProccess.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);		
        this.btnCreateTo.setVisible(true);		
        this.menuItemCreateTo.setVisible(true);		
        this.menuItemCopyLine.setVisible(false);
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
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);		
        this.contLastUpdateUser.setVisible(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);		
        this.contLastUpdateTime.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contcurrency		
        this.contcurrency.setBoundLabelText(resHelper.getString("contcurrency.boundLabelText"));		
        this.contcurrency.setBoundLabelLength(100);		
        this.contcurrency.setBoundLabelUnderline(true);		
        this.contcurrency.setVisible(true);
        // contsellProject		
        this.contsellProject.setBoundLabelText(resHelper.getString("contsellProject.boundLabelText"));		
        this.contsellProject.setBoundLabelLength(100);		
        this.contsellProject.setBoundLabelUnderline(true);		
        this.contsellProject.setVisible(true);
        // contexchangeRate		
        this.contexchangeRate.setBoundLabelText(resHelper.getString("contexchangeRate.boundLabelText"));		
        this.contexchangeRate.setBoundLabelLength(100);		
        this.contexchangeRate.setBoundLabelUnderline(true);		
        this.contexchangeRate.setVisible(true);
        // contauditTime		
        this.contauditTime.setBoundLabelText(resHelper.getString("contauditTime.boundLabelText"));		
        this.contauditTime.setBoundLabelLength(100);		
        this.contauditTime.setBoundLabelUnderline(true);		
        this.contauditTime.setVisible(true);
        // contrevAmount		
        this.contrevAmount.setBoundLabelText(resHelper.getString("contrevAmount.boundLabelText"));		
        this.contrevAmount.setBoundLabelLength(100);		
        this.contrevAmount.setBoundLabelUnderline(true);		
        this.contrevAmount.setVisible(true);
        // controom		
        this.controom.setBoundLabelText(resHelper.getString("controom.boundLabelText"));		
        this.controom.setBoundLabelLength(100);		
        this.controom.setBoundLabelUnderline(true);		
        this.controom.setVisible(true);
        // contrevBillType		
        this.contrevBillType.setBoundLabelText(resHelper.getString("contrevBillType.boundLabelText"));		
        this.contrevBillType.setBoundLabelLength(100);		
        this.contrevBillType.setBoundLabelUnderline(true);		
        this.contrevBillType.setVisible(true);
        // contpayCustomerName		
        this.contpayCustomerName.setBoundLabelText(resHelper.getString("contpayCustomerName.boundLabelText"));		
        this.contpayCustomerName.setBoundLabelLength(100);		
        this.contpayCustomerName.setBoundLabelUnderline(true);		
        this.contpayCustomerName.setVisible(true);
        // contreceiptNumber		
        this.contreceiptNumber.setBoundLabelText(resHelper.getString("contreceiptNumber.boundLabelText"));		
        this.contreceiptNumber.setBoundLabelLength(100);		
        this.contreceiptNumber.setBoundLabelUnderline(true);		
        this.contreceiptNumber.setVisible(true);
        // continvoiceNumber		
        this.continvoiceNumber.setBoundLabelText(resHelper.getString("continvoiceNumber.boundLabelText"));		
        this.continvoiceNumber.setBoundLabelLength(100);		
        this.continvoiceNumber.setBoundLabelUnderline(true);		
        this.continvoiceNumber.setVisible(true);
        // contrelateBizBill		
        this.contrelateBizBill.setBoundLabelText(resHelper.getString("contrelateBizBill.boundLabelText"));		
        this.contrelateBizBill.setBoundLabelLength(100);		
        this.contrelateBizBill.setBoundLabelUnderline(true);
        // contrelateFromBizBill		
        this.contrelateFromBizBill.setBoundLabelText(resHelper.getString("contrelateFromBizBill.boundLabelText"));		
        this.contrelateFromBizBill.setBoundLabelLength(100);		
        this.contrelateFromBizBill.setBoundLabelUnderline(true);
        // contCustomerEntry		
        this.contCustomerEntry.setBoundLabelText(resHelper.getString("contCustomerEntry.boundLabelText"));		
        this.contCustomerEntry.setBoundLabelLength(100);		
        this.contCustomerEntry.setBoundLabelUnderline(true);
        // contRelateFromCust		
        this.contRelateFromCust.setBoundLabelText(resHelper.getString("contRelateFromCust.boundLabelText"));		
        this.contRelateFromCust.setBoundLabelLength(100);		
        this.contRelateFromCust.setBoundLabelUnderline(true);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"settlementType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" /><t:Column t:key=\"settlementNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" /><t:Column t:key=\"revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"hasRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"hasTransferAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" t:styleID=\"sCol6\" /><t:Column t:key=\"leftAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" t:styleID=\"sCol7\" /><t:Column t:key=\"receiptNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" /><t:Column t:key=\"invoiceNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" /><t:Column t:key=\"revAccountBank\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:configured=\"false\" /><t:Column t:key=\"revAccountNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:configured=\"false\" /><t:Column t:key=\"customerBankNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:configured=\"false\" /><t:Column t:key=\"transferFromEntryId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:configured=\"false\" t:styleID=\"sCol13\" /><t:Column t:key=\"hasMapedAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:configured=\"false\" t:styleID=\"sCol14\" /><t:Column t:key=\"hasMakeInvoiceAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:configured=\"false\" t:styleID=\"sCol15\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">id</t:Cell><t:Cell t:configured=\"false\">收款款项</t:Cell><t:Cell t:configured=\"false\">结算方式</t:Cell><t:Cell t:configured=\"false\">结算单号</t:Cell><t:Cell t:configured=\"false\">收款金额</t:Cell><t:Cell t:configured=\"false\">已退金额</t:Cell><t:Cell t:configured=\"false\">已转金额</t:Cell><t:Cell t:configured=\"false\">剩余金额</t:Cell><t:Cell t:configured=\"false\">收据号</t:Cell><t:Cell t:configured=\"false\">发票号</t:Cell><t:Cell t:configured=\"false\">入账银行账户</t:Cell><t:Cell t:configured=\"false\">入账银行帐号</t:Cell><t:Cell t:configured=\"false\">客户银行帐号</t:Cell><t:Cell t:configured=\"false\">转出收款明细id</t:Cell><t:Cell t:configured=\"false\">已对冲金额</t:Cell><t:Cell t:configured=\"false\">已开票金额</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","moneyDefine","settlementType","settlementNumber","revAmount","hasRefundmentAmount","hasTransferAmount","","receiptNumber","invoiceNumber","revAccountBank","revAccountNumber","customerBankNumber","transferFromEntryId","hasMapedAmount","hasMakeInvoiceAmount"});


        this.kdtEntrys.checkParsed();
        final KDBizPromptBox kdtEntrys_moneyDefine_PromptBox = new KDBizPromptBox();
        kdtEntrys_moneyDefine_PromptBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
        kdtEntrys_moneyDefine_PromptBox.setVisible(true);
        kdtEntrys_moneyDefine_PromptBox.setEditable(true);
        kdtEntrys_moneyDefine_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_moneyDefine_PromptBox.setEditFormat("$number$");
        kdtEntrys_moneyDefine_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_moneyDefine_CellEditor = new KDTDefaultCellEditor(kdtEntrys_moneyDefine_PromptBox);
        this.kdtEntrys.getColumn("moneyDefine").setEditor(kdtEntrys_moneyDefine_CellEditor);
        ObjectValueRender kdtEntrys_moneyDefine_OVR = new ObjectValueRender();
        kdtEntrys_moneyDefine_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("moneyDefine").setRenderer(kdtEntrys_moneyDefine_OVR);
        final KDBizPromptBox kdtEntrys_settlementType_PromptBox = new KDBizPromptBox();
        kdtEntrys_settlementType_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");
        kdtEntrys_settlementType_PromptBox.setVisible(true);
        kdtEntrys_settlementType_PromptBox.setEditable(true);
        kdtEntrys_settlementType_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_settlementType_PromptBox.setEditFormat("$number$");
        kdtEntrys_settlementType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_settlementType_CellEditor = new KDTDefaultCellEditor(kdtEntrys_settlementType_PromptBox);
        this.kdtEntrys.getColumn("settlementType").setEditor(kdtEntrys_settlementType_CellEditor);
        ObjectValueRender kdtEntrys_settlementType_OVR = new ObjectValueRender();
        kdtEntrys_settlementType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("settlementType").setRenderer(kdtEntrys_settlementType_OVR);
        KDTextField kdtEntrys_settlementNumber_TextField = new KDTextField();
        kdtEntrys_settlementNumber_TextField.setName("kdtEntrys_settlementNumber_TextField");
        kdtEntrys_settlementNumber_TextField.setMaxLength(44);
        KDTDefaultCellEditor kdtEntrys_settlementNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_settlementNumber_TextField);
        this.kdtEntrys.getColumn("settlementNumber").setEditor(kdtEntrys_settlementNumber_CellEditor);
        KDFormattedTextField kdtEntrys_revAmount_TextField = new KDFormattedTextField();
        kdtEntrys_revAmount_TextField.setName("kdtEntrys_revAmount_TextField");
        kdtEntrys_revAmount_TextField.setVisible(true);
        kdtEntrys_revAmount_TextField.setEditable(true);
        kdtEntrys_revAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_revAmount_TextField.setDataType(1);
        	kdtEntrys_revAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_revAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_revAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_revAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_revAmount_TextField);
        this.kdtEntrys.getColumn("revAmount").setEditor(kdtEntrys_revAmount_CellEditor);
        KDFormattedTextField kdtEntrys_hasRefundmentAmount_TextField = new KDFormattedTextField();
        kdtEntrys_hasRefundmentAmount_TextField.setName("kdtEntrys_hasRefundmentAmount_TextField");
        kdtEntrys_hasRefundmentAmount_TextField.setVisible(true);
        kdtEntrys_hasRefundmentAmount_TextField.setEditable(true);
        kdtEntrys_hasRefundmentAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_hasRefundmentAmount_TextField.setDataType(1);
        	kdtEntrys_hasRefundmentAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_hasRefundmentAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_hasRefundmentAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_hasRefundmentAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_hasRefundmentAmount_TextField);
        this.kdtEntrys.getColumn("hasRefundmentAmount").setEditor(kdtEntrys_hasRefundmentAmount_CellEditor);
        KDFormattedTextField kdtEntrys_hasTransferAmount_TextField = new KDFormattedTextField();
        kdtEntrys_hasTransferAmount_TextField.setName("kdtEntrys_hasTransferAmount_TextField");
        kdtEntrys_hasTransferAmount_TextField.setVisible(true);
        kdtEntrys_hasTransferAmount_TextField.setEditable(true);
        kdtEntrys_hasTransferAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_hasTransferAmount_TextField.setDataType(1);
        	kdtEntrys_hasTransferAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_hasTransferAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_hasTransferAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_hasTransferAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_hasTransferAmount_TextField);
        this.kdtEntrys.getColumn("hasTransferAmount").setEditor(kdtEntrys_hasTransferAmount_CellEditor);
        KDTextField kdtEntrys_receiptNumber_TextField = new KDTextField();
        kdtEntrys_receiptNumber_TextField.setName("kdtEntrys_receiptNumber_TextField");
        kdtEntrys_receiptNumber_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_receiptNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_receiptNumber_TextField);
        this.kdtEntrys.getColumn("receiptNumber").setEditor(kdtEntrys_receiptNumber_CellEditor);
        KDTextField kdtEntrys_invoiceNumber_TextField = new KDTextField();
        kdtEntrys_invoiceNumber_TextField.setName("kdtEntrys_invoiceNumber_TextField");
        kdtEntrys_invoiceNumber_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_invoiceNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_invoiceNumber_TextField);
        this.kdtEntrys.getColumn("invoiceNumber").setEditor(kdtEntrys_invoiceNumber_CellEditor);
        final KDBizPromptBox kdtEntrys_revAccountBank_PromptBox = new KDBizPromptBox();
        kdtEntrys_revAccountBank_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7AccountBankQuery");
        kdtEntrys_revAccountBank_PromptBox.setVisible(true);
        kdtEntrys_revAccountBank_PromptBox.setEditable(true);
        kdtEntrys_revAccountBank_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_revAccountBank_PromptBox.setEditFormat("$number$");
        kdtEntrys_revAccountBank_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_revAccountBank_CellEditor = new KDTDefaultCellEditor(kdtEntrys_revAccountBank_PromptBox);
        this.kdtEntrys.getColumn("revAccountBank").setEditor(kdtEntrys_revAccountBank_CellEditor);
        ObjectValueRender kdtEntrys_revAccountBank_OVR = new ObjectValueRender();
        kdtEntrys_revAccountBank_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("revAccountBank").setRenderer(kdtEntrys_revAccountBank_OVR);
        KDTextField kdtEntrys_revAccountNumber_TextField = new KDTextField();
        kdtEntrys_revAccountNumber_TextField.setName("kdtEntrys_revAccountNumber_TextField");
        kdtEntrys_revAccountNumber_TextField.setMaxLength(44);
        KDTDefaultCellEditor kdtEntrys_revAccountNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_revAccountNumber_TextField);
        this.kdtEntrys.getColumn("revAccountNumber").setEditor(kdtEntrys_revAccountNumber_CellEditor);
        KDTextField kdtEntrys_customerBankNumber_TextField = new KDTextField();
        kdtEntrys_customerBankNumber_TextField.setName("kdtEntrys_customerBankNumber_TextField");
        kdtEntrys_customerBankNumber_TextField.setMaxLength(44);
        KDTDefaultCellEditor kdtEntrys_customerBankNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_customerBankNumber_TextField);
        this.kdtEntrys.getColumn("customerBankNumber").setEditor(kdtEntrys_customerBankNumber_CellEditor);
        KDTextField kdtEntrys_transferFromEntryId_TextField = new KDTextField();
        kdtEntrys_transferFromEntryId_TextField.setName("kdtEntrys_transferFromEntryId_TextField");
        kdtEntrys_transferFromEntryId_TextField.setMaxLength(44);
        KDTDefaultCellEditor kdtEntrys_transferFromEntryId_CellEditor = new KDTDefaultCellEditor(kdtEntrys_transferFromEntryId_TextField);
        this.kdtEntrys.getColumn("transferFromEntryId").setEditor(kdtEntrys_transferFromEntryId_CellEditor);
        KDFormattedTextField kdtEntrys_hasMapedAmount_TextField = new KDFormattedTextField();
        kdtEntrys_hasMapedAmount_TextField.setName("kdtEntrys_hasMapedAmount_TextField");
        kdtEntrys_hasMapedAmount_TextField.setVisible(true);
        kdtEntrys_hasMapedAmount_TextField.setEditable(true);
        kdtEntrys_hasMapedAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_hasMapedAmount_TextField.setDataType(1);
        	kdtEntrys_hasMapedAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_hasMapedAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_hasMapedAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_hasMapedAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_hasMapedAmount_TextField);
        this.kdtEntrys.getColumn("hasMapedAmount").setEditor(kdtEntrys_hasMapedAmount_CellEditor);
        KDFormattedTextField kdtEntrys_hasMakeInvoiceAmount_TextField = new KDFormattedTextField();
        kdtEntrys_hasMakeInvoiceAmount_TextField.setName("kdtEntrys_hasMakeInvoiceAmount_TextField");
        kdtEntrys_hasMakeInvoiceAmount_TextField.setVisible(true);
        kdtEntrys_hasMakeInvoiceAmount_TextField.setEditable(true);
        kdtEntrys_hasMakeInvoiceAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_hasMakeInvoiceAmount_TextField.setDataType(1);
        	kdtEntrys_hasMakeInvoiceAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntrys_hasMakeInvoiceAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntrys_hasMakeInvoiceAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntrys_hasMakeInvoiceAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_hasMakeInvoiceAmount_TextField);
        this.kdtEntrys.getColumn("hasMakeInvoiceAmount").setEditor(kdtEntrys_hasMakeInvoiceAmount_CellEditor);
        // kdAddnewLine
        this.kdAddnewLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kdAddnewLine.setText(resHelper.getString("kdAddnewLine.text"));		
        this.kdAddnewLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // kdInserLine
        this.kdInserLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionInsertLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kdInserLine.setText(resHelper.getString("kdInserLine.text"));		
        this.kdInserLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_insert"));
        // kdRemoveLine
        this.kdRemoveLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kdRemoveLine.setText(resHelper.getString("kdRemoveLine.text"));		
        this.kdRemoveLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        // kDSelectTransDetail		
        this.kDSelectTransDetail.setText(resHelper.getString("kDSelectTransDetail.text"));		
        this.kDSelectTransDetail.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_selectfill"));		
        this.kDSelectTransDetail.setVisible(false);
        this.kDSelectTransDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDSelectTransDetail_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kdSelectRefundDetail		
        this.kdSelectRefundDetail.setText(resHelper.getString("kdSelectRefundDetail.text"));		
        this.kdSelectRefundDetail.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_selectfill"));		
        this.kdSelectRefundDetail.setVisible(false);
        this.kdSelectRefundDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kdSelectRefundDetail_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contReceipt		
        this.contReceipt.setBoundLabelText(resHelper.getString("contReceipt.boundLabelText"));		
        this.contReceipt.setBoundLabelLength(100);		
        this.contReceipt.setBoundLabelUnderline(true);
        // contInvoice		
        this.contInvoice.setBoundLabelText(resHelper.getString("contInvoice.boundLabelText"));		
        this.contInvoice.setBoundLabelLength(100);		
        this.contInvoice.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kDDateCreateTime		
        this.kDDateCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);		
        this.prmtLastUpdateUser.setVisible(false);
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setEnabled(false);		
        this.kDDateLastUpdateTime.setVisible(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // pkBizDate		
        this.pkBizDate.setEnabled(true);
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // prmtcurrency		
        this.prmtcurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.prmtcurrency.setEditable(true);		
        this.prmtcurrency.setDisplayFormat("$name$");		
        this.prmtcurrency.setEditFormat("$number$");		
        this.prmtcurrency.setCommitFormat("$number$");		
        this.prmtcurrency.setRequired(false);		
        this.prmtcurrency.setEnabled(false);
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
        // prmtsellProject		
        this.prmtsellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtsellProject.setEditable(true);		
        this.prmtsellProject.setDisplayFormat("$name$");		
        this.prmtsellProject.setEditFormat("$number$");		
        this.prmtsellProject.setCommitFormat("$number$");		
        this.prmtsellProject.setRequired(false);
        // txtexchangeRate		
        this.txtexchangeRate.setHorizontalAlignment(2);		
        this.txtexchangeRate.setDataType(1);		
        this.txtexchangeRate.setSupportedEmpty(true);		
        this.txtexchangeRate.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtexchangeRate.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtexchangeRate.setPrecision(10);		
        this.txtexchangeRate.setRequired(false);		
        this.txtexchangeRate.setEnabled(false);
        // pkauditTime		
        this.pkauditTime.setRequired(false);		
        this.pkauditTime.setEnabled(false);
        // txtrevAmount		
        this.txtrevAmount.setHorizontalAlignment(2);		
        this.txtrevAmount.setDataType(1);		
        this.txtrevAmount.setSupportedEmpty(true);		
        this.txtrevAmount.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtrevAmount.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtrevAmount.setPrecision(10);		
        this.txtrevAmount.setRequired(false);		
        this.txtrevAmount.setEnabled(false);
        // prmtroom		
        this.prmtroom.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomQuery");		
        this.prmtroom.setEditable(true);		
        this.prmtroom.setDisplayFormat("$name$");		
        this.prmtroom.setEditFormat("$number$");		
        this.prmtroom.setCommitFormat("$number$");		
        this.prmtroom.setRequired(false);
        // revBillType		
        this.revBillType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basecrm.RevBillTypeEnum").toArray());		
        this.revBillType.setRequired(false);
        this.revBillType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    revBillType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtpayCustomerName		
        this.txtpayCustomerName.setHorizontalAlignment(2);		
        this.txtpayCustomerName.setMaxLength(44);		
        this.txtpayCustomerName.setRequired(false);
        // txtreceiptNumber		
        this.txtreceiptNumber.setHorizontalAlignment(2);		
        this.txtreceiptNumber.setMaxLength(44);		
        this.txtreceiptNumber.setRequired(false);
        // txtinvoiceNumber		
        this.txtinvoiceNumber.setHorizontalAlignment(2);		
        this.txtinvoiceNumber.setMaxLength(44);		
        this.txtinvoiceNumber.setRequired(false);
        // prmtRelateBizBill		
        this.prmtRelateBizBill.setDisplayFormat("$number$");		
        this.prmtRelateBizBill.setEditFormat("$number$");		
        this.prmtRelateBizBill.setCommitFormat("$number$");		
        this.prmtRelateBizBill.setDefaultF7UIName("com.kingdee.eas.fdc.basecrm.client.RelateBillQueryUI");
        this.prmtRelateBizBill.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRelateBizBill_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtRelateFromBizBill		
        this.prmtRelateFromBizBill.setDisplayFormat("$number$");		
        this.prmtRelateFromBizBill.setEditFormat("$number$");		
        this.prmtRelateFromBizBill.setCommitFormat("$number$");		
        this.prmtRelateFromBizBill.setDefaultF7UIName("com.kingdee.eas.fdc.basecrm.client.RelateBillQueryUI");		
        this.prmtRelateFromBizBill.setEnabled(false);
        this.prmtRelateFromBizBill.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRelateFromBizBill_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCustomerEntry		
        this.prmtCustomerEntry.setDisplayFormat("$name$");		
        this.prmtCustomerEntry.setEditFormat("$number$");		
        this.prmtCustomerEntry.setCommitFormat("$number$");		
        this.prmtCustomerEntry.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECustomerQuery");		
        this.prmtCustomerEntry.setEnabledMultiSelection(true);		
        this.prmtCustomerEntry.setRequired(true);
        // prmtRelateFromCust		
        this.prmtRelateFromCust.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECustomerQuery");		
        this.prmtRelateFromCust.setDisplayFormat("$name$");		
        this.prmtRelateFromCust.setEditFormat("$number$");		
        this.prmtRelateFromCust.setCommitFormat("$number$");		
        this.prmtRelateFromCust.setEnabled(false);
        // prmtReceipt		
        this.prmtReceipt.setDisplayFormat("$number$");		
        this.prmtReceipt.setEditFormat("$number$");		
        this.prmtReceipt.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChequeDetailEntryQuery");		
        this.prmtReceipt.setCommitFormat("$number$");
        // prmtInvoice		
        this.prmtInvoice.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChequeDetailEntryQuery");		
        this.prmtInvoice.setCommitFormat("$number$");		
        this.prmtInvoice.setDisplayFormat("$number$");		
        this.prmtInvoice.setEditFormat("$number$");
        // btnSubmitAudit
        this.btnSubmitAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmitAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSubmitAudit.setText(resHelper.getString("btnSubmitAudit.text"));		
        this.btnSubmitAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_suitbest"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnMakeInvoice
        this.btnMakeInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionMakeInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMakeInvoice.setText(resHelper.getString("btnMakeInvoice.text"));		
        this.btnMakeInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_returnticket"));
        // btnRecycle
        this.btnRecycle.setAction((IItemAction)ActionProxyFactory.getProxy(actionRecycle, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRecycle.setText(resHelper.getString("btnRecycle.text"));		
        this.btnRecycle.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelreturnticket"));
        // menuSubmitAudit
        this.menuSubmitAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmitAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuSubmitAudit.setText(resHelper.getString("menuSubmitAudit.text"));		
        this.menuSubmitAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_suitbest"));
        // menuAudit
        this.menuAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuAudit.setText(resHelper.getString("menuAudit.text"));		
        this.menuAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // menuUnAudit
        this.menuUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuUnAudit.setText(resHelper.getString("menuUnAudit.text"));		
        this.menuUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // menuMakeInvoice
        this.menuMakeInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionMakeInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuMakeInvoice.setText(resHelper.getString("menuMakeInvoice.text"));		
        this.menuMakeInvoice.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_returnticket"));
        // menuRecycle
        this.menuRecycle.setAction((IItemAction)ActionProxyFactory.getProxy(actionRecycle, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuRecycle.setText(resHelper.getString("menuRecycle.text"));		
        this.menuRecycle.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelreturnticket"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtNumber,pkBizDate,prmtsellProject,prmtcurrency,txtexchangeRate,txtrevAmount,prmtroom,revBillType,txtpayCustomerName,txtreceiptNumber,txtinvoiceNumber,txtDescription,kdtEntrys,prmtAuditor,pkauditTime,prmtCreator,kDDateCreateTime,prmtLastUpdateUser,kDDateLastUpdateTime}));
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
        this.setBounds(new Rectangle(0, 0, 1015, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1015, 629));
        contCreator.setBounds(new Rectangle(14, 575, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(14, 575, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(371, 575, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(371, 575, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_BOTTOM));
        contLastUpdateUser.setBounds(new Rectangle(729, 599, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(729, 599, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateTime.setBounds(new Rectangle(729, 575, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(729, 575, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(13, 9, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(13, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(373, 9, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(373, 9, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        contDescription.setBounds(new Rectangle(13, 141, 990, 32));
        this.add(contDescription, new KDLayout.Constraints(13, 141, 990, 32, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(14, 600, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(14, 600, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcurrency.setBounds(new Rectangle(13, 35, 270, 19));
        this.add(contcurrency, new KDLayout.Constraints(13, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsellProject.setBounds(new Rectangle(733, 9, 270, 19));
        this.add(contsellProject, new KDLayout.Constraints(733, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contexchangeRate.setBounds(new Rectangle(373, 35, 270, 19));
        this.add(contexchangeRate, new KDLayout.Constraints(373, 35, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        contauditTime.setBounds(new Rectangle(371, 600, 270, 19));
        this.add(contauditTime, new KDLayout.Constraints(371, 600, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_BOTTOM));
        contrevAmount.setBounds(new Rectangle(733, 35, 270, 19));
        this.add(contrevAmount, new KDLayout.Constraints(733, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        controom.setBounds(new Rectangle(13, 61, 270, 19));
        this.add(controom, new KDLayout.Constraints(13, 61, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrevBillType.setBounds(new Rectangle(13, 87, 270, 19));
        this.add(contrevBillType, new KDLayout.Constraints(13, 87, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpayCustomerName.setBounds(new Rectangle(13, 113, 270, 19));
        this.add(contpayCustomerName, new KDLayout.Constraints(13, 113, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contreceiptNumber.setBounds(new Rectangle(373, 113, 270, 19));
        this.add(contreceiptNumber, new KDLayout.Constraints(373, 113, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        continvoiceNumber.setBounds(new Rectangle(733, 114, 270, 19));
        this.add(continvoiceNumber, new KDLayout.Constraints(733, 114, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contrelateBizBill.setBounds(new Rectangle(373, 87, 270, 19));
        this.add(contrelateBizBill, new KDLayout.Constraints(373, 87, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        contrelateFromBizBill.setBounds(new Rectangle(733, 87, 270, 19));
        this.add(contrelateFromBizBill, new KDLayout.Constraints(733, 87, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCustomerEntry.setBounds(new Rectangle(373, 61, 270, 19));
        this.add(contCustomerEntry, new KDLayout.Constraints(373, 61, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        contRelateFromCust.setBounds(new Rectangle(733, 63, 270, 19));
        this.add(contRelateFromCust, new KDLayout.Constraints(733, 63, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntrys.setBounds(new Rectangle(6, 209, 997, 357));
        this.add(kdtEntrys, new KDLayout.Constraints(6, 209, 997, 357, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kdAddnewLine.setBounds(new Rectangle(764, 182, 73, 19));
        this.add(kdAddnewLine, new KDLayout.Constraints(764, 182, 73, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        kdInserLine.setBounds(new Rectangle(841, 182, 73, 19));
        this.add(kdInserLine, new KDLayout.Constraints(841, 182, 73, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        kdRemoveLine.setBounds(new Rectangle(918, 182, 73, 19));
        this.add(kdRemoveLine, new KDLayout.Constraints(918, 182, 73, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSelectTransDetail.setBounds(new Rectangle(900, 182, 108, 19));
        this.add(kDSelectTransDetail, new KDLayout.Constraints(900, 182, 108, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        kdSelectRefundDetail.setBounds(new Rectangle(900, 182, 108, 19));
        this.add(kdSelectRefundDetail, new KDLayout.Constraints(900, 182, 108, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        contReceipt.setBounds(new Rectangle(373, 113, 270, 19));
        this.add(contReceipt, new KDLayout.Constraints(373, 113, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        contInvoice.setBounds(new Rectangle(733, 114, 270, 19));
        this.add(contInvoice, new KDLayout.Constraints(733, 114, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDateLastUpdateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contcurrency
        contcurrency.setBoundEditor(prmtcurrency);
        //contsellProject
        contsellProject.setBoundEditor(prmtsellProject);
        //contexchangeRate
        contexchangeRate.setBoundEditor(txtexchangeRate);
        //contauditTime
        contauditTime.setBoundEditor(pkauditTime);
        //contrevAmount
        contrevAmount.setBoundEditor(txtrevAmount);
        //controom
        controom.setBoundEditor(prmtroom);
        //contrevBillType
        contrevBillType.setBoundEditor(revBillType);
        //contpayCustomerName
        contpayCustomerName.setBoundEditor(txtpayCustomerName);
        //contreceiptNumber
        contreceiptNumber.setBoundEditor(txtreceiptNumber);
        //continvoiceNumber
        continvoiceNumber.setBoundEditor(txtinvoiceNumber);
        //contrelateBizBill
        contrelateBizBill.setBoundEditor(prmtRelateBizBill);
        //contrelateFromBizBill
        contrelateFromBizBill.setBoundEditor(prmtRelateFromBizBill);
        //contCustomerEntry
        contCustomerEntry.setBoundEditor(prmtCustomerEntry);
        //contRelateFromCust
        contRelateFromCust.setBoundEditor(prmtRelateFromCust);
        //contReceipt
        contReceipt.setBoundEditor(prmtReceipt);
        //contInvoice
        contInvoice.setBoundEditor(prmtInvoice);

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
        menuFile.add(menuSubmitAudit);
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
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuAudit);
        menuBiz.add(menuUnAudit);
        menuBiz.add(menuMakeInvoice);
        menuBiz.add(menuRecycle);
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
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnSubmitAudit);
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
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
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
        this.toolBar.add(btnMakeInvoice);
        this.toolBar.add(btnRecycle);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.revAmount", java.math.BigDecimal.class, this.kdtEntrys, "revAmount.text");
		dataBinder.registerBinding("entrys.moneyDefine", java.lang.Object.class, this.kdtEntrys, "moneyDefine.text");
		dataBinder.registerBinding("entrys.settlementType", java.lang.Object.class, this.kdtEntrys, "settlementType.text");
		dataBinder.registerBinding("entrys.settlementNumber", String.class, this.kdtEntrys, "settlementNumber.text");
		dataBinder.registerBinding("entrys.hasRefundmentAmount", java.math.BigDecimal.class, this.kdtEntrys, "hasRefundmentAmount.text");
		dataBinder.registerBinding("entrys.hasTransferAmount", java.math.BigDecimal.class, this.kdtEntrys, "hasTransferAmount.text");
		dataBinder.registerBinding("entrys.revAccountBank", java.lang.Object.class, this.kdtEntrys, "revAccountBank.text");
		dataBinder.registerBinding("entrys.revAccountNumber", String.class, this.kdtEntrys, "revAccountNumber.text");
		dataBinder.registerBinding("entrys.customerBankNumber", String.class, this.kdtEntrys, "customerBankNumber.text");
		dataBinder.registerBinding("entrys.receiptNumber", String.class, this.kdtEntrys, "receiptNumber.text");
		dataBinder.registerBinding("entrys.invoiceNumber", String.class, this.kdtEntrys, "invoiceNumber.text");
		dataBinder.registerBinding("entrys.transferFromEntryId", String.class, this.kdtEntrys, "transferFromEntryId.text");
		dataBinder.registerBinding("entrys.hasMapedAmount", java.math.BigDecimal.class, this.kdtEntrys, "hasMapedAmount.text");
		dataBinder.registerBinding("entrys.hasMakeInvoiceAmount", java.math.BigDecimal.class, this.kdtEntrys, "hasMakeInvoiceAmount.text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtcurrency, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtsellProject, "data");
		dataBinder.registerBinding("exchangeRate", java.math.BigDecimal.class, this.txtexchangeRate, "value");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkauditTime, "value");
		dataBinder.registerBinding("revAmount", java.math.BigDecimal.class, this.txtrevAmount, "value");
		dataBinder.registerBinding("room", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.prmtroom, "data");
		dataBinder.registerBinding("revBillType", com.kingdee.eas.fdc.basecrm.RevBillTypeEnum.class, this.revBillType, "selectedItem");
		dataBinder.registerBinding("payCustomerName", String.class, this.txtpayCustomerName, "text");
		dataBinder.registerBinding("receiptNumber", String.class, this.txtreceiptNumber, "text");
		dataBinder.registerBinding("invoiceNumber", String.class, this.txtinvoiceNumber, "text");
		dataBinder.registerBinding("relateFromCust", com.kingdee.eas.fdc.sellhouse.SHECustomerInfo.class, this.prmtRelateFromCust, "data");
		dataBinder.registerBinding("receipt", com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo.class, this.prmtReceipt, "data");
		dataBinder.registerBinding("invoice", com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo.class, this.prmtInvoice, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basecrm.app.SHERevBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.basecrm.SHERevBillInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Sale",editData.getString("number"));
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
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Sale");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
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
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.revAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.moneyDefine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.settlementNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.hasRefundmentAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.hasTransferAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.revAccountBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.revAccountNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.customerBankNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.receiptNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.invoiceNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.transferFromEntryId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.hasMapedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.hasMakeInvoiceAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revBillType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payCustomerName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("receiptNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relateFromCust", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("receipt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoice", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kDSelectTransDetail_actionPerformed method
     */
    protected void kDSelectTransDetail_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdSelectRefundDetail_actionPerformed method
     */
    protected void kdSelectRefundDetail_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtcurrency_dataChanged method
     */
    protected void prmtcurrency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output revBillType_itemStateChanged method
     */
    protected void revBillType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtRelateBizBill_dataChanged method
     */
    protected void prmtRelateBizBill_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtRelateFromBizBill_dataChanged method
     */
    protected void prmtRelateFromBizBill_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("entrys.id"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
    sic.add(new SelectorItemInfo("entrys.revAmount"));
        sic.add(new SelectorItemInfo("entrys.moneyDefine.*"));
//        sic.add(new SelectorItemInfo("entrys.moneyDefine.number"));
        sic.add(new SelectorItemInfo("entrys.settlementType.*"));
//        sic.add(new SelectorItemInfo("entrys.settlementType.number"));
    sic.add(new SelectorItemInfo("entrys.settlementNumber"));
    sic.add(new SelectorItemInfo("entrys.hasRefundmentAmount"));
    sic.add(new SelectorItemInfo("entrys.hasTransferAmount"));
        sic.add(new SelectorItemInfo("entrys.revAccountBank.*"));
//        sic.add(new SelectorItemInfo("entrys.revAccountBank.number"));
    sic.add(new SelectorItemInfo("entrys.revAccountNumber"));
    sic.add(new SelectorItemInfo("entrys.customerBankNumber"));
    sic.add(new SelectorItemInfo("entrys.receiptNumber"));
    sic.add(new SelectorItemInfo("entrys.invoiceNumber"));
    sic.add(new SelectorItemInfo("entrys.transferFromEntryId"));
    sic.add(new SelectorItemInfo("entrys.hasMapedAmount"));
    sic.add(new SelectorItemInfo("entrys.hasMakeInvoiceAmount"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("currency.*"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("exchangeRate"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("revAmount"));
        sic.add(new SelectorItemInfo("room.*"));
        sic.add(new SelectorItemInfo("revBillType"));
        sic.add(new SelectorItemInfo("payCustomerName"));
        sic.add(new SelectorItemInfo("receiptNumber"));
        sic.add(new SelectorItemInfo("invoiceNumber"));
        sic.add(new SelectorItemInfo("relateFromCust.*"));
        sic.add(new SelectorItemInfo("receipt.*"));
        sic.add(new SelectorItemInfo("invoice.*"));
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
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMakeInvoice_actionPerformed method
     */
    public void actionMakeInvoice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRecycle_actionPerformed method
     */
    public void actionRecycle_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSubmitAudit_actionPerformed method
     */
    public void actionSubmitAudit_actionPerformed(ActionEvent e) throws Exception
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
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionMakeInvoice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMakeInvoice() {
    	return false;
    }
	public RequestContext prepareActionRecycle(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRecycle() {
    	return false;
    }
	public RequestContext prepareActionSubmitAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmitAudit() {
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
            innerActionPerformed("eas", AbstractSHERevBillEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAudit class
     */     
    protected class ActionUnAudit extends ItemAction {     
    
        public ActionUnAudit()
        {
            this(null);
        }

        public ActionUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHERevBillEditUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionMakeInvoice class
     */     
    protected class ActionMakeInvoice extends ItemAction {     
    
        public ActionMakeInvoice()
        {
            this(null);
        }

        public ActionMakeInvoice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMakeInvoice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMakeInvoice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMakeInvoice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHERevBillEditUI.this, "ActionMakeInvoice", "actionMakeInvoice_actionPerformed", e);
        }
    }

    /**
     * output ActionRecycle class
     */     
    protected class ActionRecycle extends ItemAction {     
    
        public ActionRecycle()
        {
            this(null);
        }

        public ActionRecycle(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRecycle.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRecycle.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRecycle.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHERevBillEditUI.this, "ActionRecycle", "actionRecycle_actionPerformed", e);
        }
    }

    /**
     * output ActionSubmitAudit class
     */     
    protected class ActionSubmitAudit extends ItemAction {     
    
        public ActionSubmitAudit()
        {
            this(null);
        }

        public ActionSubmitAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSubmitAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmitAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmitAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHERevBillEditUI.this, "ActionSubmitAudit", "actionSubmitAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basecrm.client", "SHERevBillEditUI");
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
        return com.kingdee.eas.fdc.basecrm.client.SHERevBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.basecrm.SHERevBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.basecrm.SHERevBillInfo objectValue = new com.kingdee.eas.fdc.basecrm.SHERevBillInfo();
				if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Sale")) != null && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Sale")).getBoolean("isBizUnit"))
			objectValue.put("saleOrgUnit",com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Sale")));
 
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/basecrm/SHERevBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.basecrm.app.SHERevBillQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntrys;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("revBillType","gathering");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}