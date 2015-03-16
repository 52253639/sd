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
public abstract class AbstractNewSHERevBillEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractNewSHERevBillEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReceipt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoice;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAccountBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRevAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettlementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettlementNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCertificateNumber;
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
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRev;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdAddnewLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdInserLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdRemoveLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdSelectRefundDetail;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDSelectTransDetail;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAccountBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRevAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSettlementType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSettlementNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCertificateNumber;
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
    public AbstractNewSHERevBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractNewSHERevBillEditUI.class.getName());
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
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
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
        this.contReceipt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contAccountBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRevAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettlementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettlementNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCertificateNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.tblRev = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdAddnewLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdInserLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdRemoveLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdSelectRefundDetail = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSelectTransDetail = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.prmtAccountBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRevAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSettlementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSettlementNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCertificateNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
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
        this.contReceipt.setName("contReceipt");
        this.contInvoice.setName("contInvoice");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.contAccountBank.setName("contAccountBank");
        this.contRevAccount.setName("contRevAccount");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contSettlementType.setName("contSettlementType");
        this.contSettlementNumber.setName("contSettlementNumber");
        this.contCertificateNumber.setName("contCertificateNumber");
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
        this.tblRev.setName("tblRev");
        this.kdtEntrys.setName("kdtEntrys");
        this.kdAddnewLine.setName("kdAddnewLine");
        this.kdInserLine.setName("kdInserLine");
        this.kdRemoveLine.setName("kdRemoveLine");
        this.kdSelectRefundDetail.setName("kdSelectRefundDetail");
        this.kDSelectTransDetail.setName("kDSelectTransDetail");
        this.prmtAccountBank.setName("prmtAccountBank");
        this.prmtRevAccount.setName("prmtRevAccount");
        this.prmtBank.setName("prmtBank");
        this.prmtSettlementType.setName("prmtSettlementType");
        this.txtSettlementNumber.setName("txtSettlementNumber");
        this.txtCertificateNumber.setName("txtCertificateNumber");
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
        this.setPreferredSize(new Dimension(1015,629));		
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
        this.contpayCustomerName.setFocusable(false);
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
        // contReceipt		
        this.contReceipt.setBoundLabelText(resHelper.getString("contReceipt.boundLabelText"));		
        this.contReceipt.setBoundLabelLength(100);		
        this.contReceipt.setBoundLabelUnderline(true);
        // contInvoice		
        this.contInvoice.setBoundLabelText(resHelper.getString("contInvoice.boundLabelText"));		
        this.contInvoice.setBoundLabelLength(100);		
        this.contInvoice.setBoundLabelUnderline(true);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // contAccountBank		
        this.contAccountBank.setBoundLabelText(resHelper.getString("contAccountBank.boundLabelText"));		
        this.contAccountBank.setBoundLabelUnderline(true);		
        this.contAccountBank.setBoundLabelLength(100);
        // contRevAccount		
        this.contRevAccount.setBoundLabelText(resHelper.getString("contRevAccount.boundLabelText"));		
        this.contRevAccount.setBoundLabelLength(100);		
        this.contRevAccount.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // contSettlementType		
        this.contSettlementType.setBoundLabelText(resHelper.getString("contSettlementType.boundLabelText"));		
        this.contSettlementType.setBoundLabelLength(100);		
        this.contSettlementType.setBoundLabelUnderline(true);
        // contSettlementNumber		
        this.contSettlementNumber.setBoundLabelText(resHelper.getString("contSettlementNumber.boundLabelText"));		
        this.contSettlementNumber.setBoundLabelLength(100);		
        this.contSettlementNumber.setBoundLabelUnderline(true);
        // contCertificateNumber		
        this.contCertificateNumber.setBoundLabelText(resHelper.getString("contCertificateNumber.boundLabelText"));		
        this.contCertificateNumber.setBoundLabelLength(100);		
        this.contCertificateNumber.setBoundLabelUnderline(true);
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
        this.prmtCustomerEntry.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCustomerEntry_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
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
        // tblRev
		String tblRevStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"isSelect\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"revDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"canAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"canQuit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"quitAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{isSelect}</t:Cell><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{revDate}</t:Cell><t:Cell>$Resource{revAmount}</t:Cell><t:Cell>$Resource{canAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{canQuit}</t:Cell><t:Cell>$Resource{quitAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblRev.setFormatXml(resHelper.translateString("tblRev",tblRevStrXML));
        this.tblRev.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblRev_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"invoiceType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"receiptNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"invoiceNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"appRevAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"revAccountBank\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"customerBankNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"settlementType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"settlementNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{invoiceType}</t:Cell><t:Cell>$Resource{receiptNumber}</t:Cell><t:Cell>$Resource{invoiceNumber}</t:Cell><t:Cell>$Resource{revAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{appRevAmount}</t:Cell><t:Cell>$Resource{revAccountBank}</t:Cell><t:Cell>$Resource{customerBankNumber}</t:Cell><t:Cell>$Resource{settlementType}</t:Cell><t:Cell>$Resource{settlementNumber}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
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

                this.kdtEntrys.putBindContents("editData",new String[] {"id","moneyDefine","invoiceType","receiptNumber","invoiceNumber","revAmount","amount","","revAccountBank","customerBankNumber","settlementType","settlementNumber"});


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
        // prmtAccountBank		
        this.prmtAccountBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.AccountBankQuery");
        this.prmtAccountBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtAccountBank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtRevAccount		
        this.prmtRevAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
        // prmtBank		
        this.prmtBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.BankQuery");
        this.prmtBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtSettlementType		
        this.prmtSettlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");		
        this.prmtSettlementType.setCommitFormat("$number$");		
        this.prmtSettlementType.setEditFormat("$number$");		
        this.prmtSettlementType.setDisplayFormat("$name$");
        // txtSettlementNumber		
        this.txtSettlementNumber.setMaxLength(44);
        // txtCertificateNumber		
        this.txtCertificateNumber.setEnabled(false);
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
        contCreator.setBounds(new Rectangle(13, 583, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(13, 583, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(373, 583, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(373, 583, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_BOTTOM));
        contLastUpdateUser.setBounds(new Rectangle(733, 605, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(733, 605, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateTime.setBounds(new Rectangle(733, 583, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(733, 583, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(13, 9, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(13, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(373, 9, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(373, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(13, 141, 990, 32));
        this.add(contDescription, new KDLayout.Constraints(13, 141, 990, 32, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(13, 605, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(13, 605, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcurrency.setBounds(new Rectangle(373, 75, 270, 19));
        this.add(contcurrency, new KDLayout.Constraints(373, 75, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsellProject.setBounds(new Rectangle(733, 9, 270, 19));
        this.add(contsellProject, new KDLayout.Constraints(733, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contexchangeRate.setBounds(new Rectangle(733, 75, 270, 19));
        this.add(contexchangeRate, new KDLayout.Constraints(733, 75, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contauditTime.setBounds(new Rectangle(373, 605, 270, 19));
        this.add(contauditTime, new KDLayout.Constraints(373, 605, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_BOTTOM));
        contrevAmount.setBounds(new Rectangle(13, 75, 270, 19));
        this.add(contrevAmount, new KDLayout.Constraints(13, 75, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        controom.setBounds(new Rectangle(13, 31, 270, 19));
        this.add(controom, new KDLayout.Constraints(13, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrevBillType.setBounds(new Rectangle(13, 53, 270, 19));
        this.add(contrevBillType, new KDLayout.Constraints(13, 53, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpayCustomerName.setBounds(new Rectangle(733, 53, 270, 19));
        this.add(contpayCustomerName, new KDLayout.Constraints(733, 53, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contreceiptNumber.setBounds(new Rectangle(889, 125, 270, 19));
        this.add(contreceiptNumber, new KDLayout.Constraints(889, 125, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continvoiceNumber.setBounds(new Rectangle(877, 106, 270, 19));
        this.add(continvoiceNumber, new KDLayout.Constraints(877, 106, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contrelateBizBill.setBounds(new Rectangle(373, 53, 270, 19));
        this.add(contrelateBizBill, new KDLayout.Constraints(373, 53, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrelateFromBizBill.setBounds(new Rectangle(969, 58, 270, 19));
        this.add(contrelateFromBizBill, new KDLayout.Constraints(969, 58, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCustomerEntry.setBounds(new Rectangle(373, 31, 270, 19));
        this.add(contCustomerEntry, new KDLayout.Constraints(373, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRelateFromCust.setBounds(new Rectangle(935, 56, 270, 19));
        this.add(contRelateFromCust, new KDLayout.Constraints(935, 56, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contReceipt.setBounds(new Rectangle(926, 54, 270, 19));
        this.add(contReceipt, new KDLayout.Constraints(926, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoice.setBounds(new Rectangle(926, 108, 270, 19));
        this.add(contInvoice, new KDLayout.Constraints(926, 108, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(13, 183, 990, 160));
        this.add(kDPanel1, new KDLayout.Constraints(13, 183, 990, 160, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel2.setBounds(new Rectangle(13, 348, 990, 230));
        this.add(kDPanel2, new KDLayout.Constraints(13, 348, 990, 230, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAccountBank.setBounds(new Rectangle(13, 97, 270, 19));
        this.add(contAccountBank, new KDLayout.Constraints(13, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRevAccount.setBounds(new Rectangle(373, 97, 270, 19));
        this.add(contRevAccount, new KDLayout.Constraints(373, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(733, 97, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(733, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSettlementType.setBounds(new Rectangle(13, 119, 270, 19));
        this.add(contSettlementType, new KDLayout.Constraints(13, 119, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSettlementNumber.setBounds(new Rectangle(373, 119, 270, 19));
        this.add(contSettlementNumber, new KDLayout.Constraints(373, 119, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCertificateNumber.setBounds(new Rectangle(733, 31, 270, 19));
        this.add(contCertificateNumber, new KDLayout.Constraints(733, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
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
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(13, 183, 990, 160));        tblRev.setBounds(new Rectangle(11, 15, 965, 128));
        kDPanel1.add(tblRev, new KDLayout.Constraints(11, 15, 965, 128, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(13, 348, 990, 230));        kdtEntrys.setBounds(new Rectangle(11, 40, 964, 182));
        kDPanel2.add(kdtEntrys, new KDLayout.Constraints(11, 40, 964, 182, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdAddnewLine.setBounds(new Rectangle(655, 14, 91, 19));
        kDPanel2.add(kdAddnewLine, new KDLayout.Constraints(655, 14, 91, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdInserLine.setBounds(new Rectangle(769, 14, 91, 19));
        kDPanel2.add(kdInserLine, new KDLayout.Constraints(769, 14, 91, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdRemoveLine.setBounds(new Rectangle(884, 14, 91, 19));
        kDPanel2.add(kdRemoveLine, new KDLayout.Constraints(884, 14, 91, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdSelectRefundDetail.setBounds(new Rectangle(867, 14, 108, 19));
        kDPanel2.add(kdSelectRefundDetail, new KDLayout.Constraints(867, 14, 108, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSelectTransDetail.setBounds(new Rectangle(867, 14, 108, 19));
        kDPanel2.add(kDSelectTransDetail, new KDLayout.Constraints(867, 14, 108, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        //contAccountBank
        contAccountBank.setBoundEditor(prmtAccountBank);
        //contRevAccount
        contRevAccount.setBoundEditor(prmtRevAccount);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prmtBank);
        //contSettlementType
        contSettlementType.setBoundEditor(prmtSettlementType);
        //contSettlementNumber
        contSettlementNumber.setBoundEditor(txtSettlementNumber);
        //contCertificateNumber
        contCertificateNumber.setBoundEditor(txtCertificateNumber);

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
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.revAmount", java.math.BigDecimal.class, this.kdtEntrys, "revAmount.text");
		dataBinder.registerBinding("entrys.moneyDefine", java.lang.Object.class, this.kdtEntrys, "moneyDefine.text");
		dataBinder.registerBinding("entrys.settlementType", java.lang.Object.class, this.kdtEntrys, "settlementType.text");
		dataBinder.registerBinding("entrys.settlementNumber", String.class, this.kdtEntrys, "settlementNumber.text");
		dataBinder.registerBinding("entrys.revAccountBank", java.lang.Object.class, this.kdtEntrys, "revAccountBank.text");
		dataBinder.registerBinding("entrys.customerBankNumber", String.class, this.kdtEntrys, "customerBankNumber.text");
		dataBinder.registerBinding("entrys.receiptNumber", String.class, this.kdtEntrys, "receiptNumber.text");
		dataBinder.registerBinding("entrys.invoiceNumber", String.class, this.kdtEntrys, "invoiceNumber.text");
		dataBinder.registerBinding("entrys.amount", java.math.BigDecimal.class, this.kdtEntrys, "amount.text");
		dataBinder.registerBinding("entrys.invoiceType", com.kingdee.eas.fdc.basecrm.InvoiceTypeEnum.class, this.kdtEntrys, "invoiceType.text");
		dataBinder.registerBinding("accountBank", com.kingdee.eas.basedata.assistant.AccountBankInfo.class, this.prmtAccountBank, "data");
		dataBinder.registerBinding("revAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtRevAccount, "data");
		dataBinder.registerBinding("bank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.prmtBank, "data");
		dataBinder.registerBinding("settlementType", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.prmtSettlementType, "data");
		dataBinder.registerBinding("settlementNumber", String.class, this.txtSettlementNumber, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basecrm.app.NewSHERevBillEditUIHandler";
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
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
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
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.revAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.moneyDefine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.settlementNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.revAccountBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.customerBankNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.receiptNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.invoiceNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.invoiceType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accountBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementNumber", ValidateHelper.ON_SAVE);    		
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
     * output prmtCustomerEntry_dataChanged method
     */
    protected void prmtCustomerEntry_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblRev_editStopped method
     */
    protected void tblRev_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdSelectRefundDetail_actionPerformed method
     */
    protected void kdSelectRefundDetail_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDSelectTransDetail_actionPerformed method
     */
    protected void kDSelectTransDetail_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtAccountBank_dataChanged method
     */
    protected void prmtAccountBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtBank_dataChanged method
     */
    protected void prmtBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
    sic.add(new SelectorItemInfo("entrys.id"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
    sic.add(new SelectorItemInfo("entrys.revAmount"));
        sic.add(new SelectorItemInfo("entrys.moneyDefine.*"));
//        sic.add(new SelectorItemInfo("entrys.moneyDefine.number"));
        sic.add(new SelectorItemInfo("entrys.settlementType.*"));
//        sic.add(new SelectorItemInfo("entrys.settlementType.number"));
    sic.add(new SelectorItemInfo("entrys.settlementNumber"));
        sic.add(new SelectorItemInfo("entrys.revAccountBank.*"));
//        sic.add(new SelectorItemInfo("entrys.revAccountBank.number"));
    sic.add(new SelectorItemInfo("entrys.customerBankNumber"));
    sic.add(new SelectorItemInfo("entrys.receiptNumber"));
    sic.add(new SelectorItemInfo("entrys.invoiceNumber"));
    sic.add(new SelectorItemInfo("entrys.amount"));
    sic.add(new SelectorItemInfo("entrys.invoiceType"));
        sic.add(new SelectorItemInfo("accountBank.*"));
        sic.add(new SelectorItemInfo("revAccount.*"));
        sic.add(new SelectorItemInfo("bank.*"));
        sic.add(new SelectorItemInfo("settlementType.*"));
        sic.add(new SelectorItemInfo("settlementNumber"));
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
        super.actionPrint_actionPerformed(e);
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
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
            innerActionPerformed("eas", AbstractNewSHERevBillEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractNewSHERevBillEditUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractNewSHERevBillEditUI.this, "ActionMakeInvoice", "actionMakeInvoice_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractNewSHERevBillEditUI.this, "ActionRecycle", "actionRecycle_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractNewSHERevBillEditUI.this, "ActionSubmitAudit", "actionSubmitAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basecrm.client", "NewSHERevBillEditUI");
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
        return objectValue;
    }




}