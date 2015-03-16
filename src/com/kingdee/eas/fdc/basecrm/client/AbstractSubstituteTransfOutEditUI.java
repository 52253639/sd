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
public abstract class AbstractSubstituteTransfOutEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSubstituteTransfOutEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsaleOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrevUserName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrevBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrevAccountNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayAccountNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrevAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contoppAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsettlementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contexchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSearchShowEntry;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayUser;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsaleOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtmoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtpayAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtrevUserName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrevBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpayBank;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtrevAccountNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpayAccountNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrevAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtoppAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsettlementType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtexchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtbuilding;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpayUser;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuUnAudit;
    protected com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    /**
     * output class constructor
     */
    public AbstractSubstituteTransfOutEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSubstituteTransfOutEditUI.class.getName());
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
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contsaleOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrevUserName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrevBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrevAccountNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayAccountNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrevAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contoppAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsettlementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contexchangeRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSearchShowEntry = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteEntry = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contbuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsaleOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtmoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtpayAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtrevUserName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtrevBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtpayBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtrevAccountNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtpayAccountNumber = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtrevAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtoppAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsettlementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtexchangeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkauditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtbuilding = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtpayUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.kdtEntrys.setName("kdtEntrys");
        this.contsaleOrgUnit.setName("contsaleOrgUnit");
        this.contsellProject.setName("contsellProject");
        this.contmoneyDefine.setName("contmoneyDefine");
        this.contpayAmount.setName("contpayAmount");
        this.contrevUserName.setName("contrevUserName");
        this.contrevBank.setName("contrevBank");
        this.contpayBank.setName("contpayBank");
        this.contrevAccountNumber.setName("contrevAccountNumber");
        this.contpayAccountNumber.setName("contpayAccountNumber");
        this.contrevAccount.setName("contrevAccount");
        this.contoppAccount.setName("contoppAccount");
        this.contsettlementType.setName("contsettlementType");
        this.contcurrency.setName("contcurrency");
        this.contexchangeRate.setName("contexchangeRate");
        this.contauditDate.setName("contauditDate");
        this.btnSearchShowEntry.setName("btnSearchShowEntry");
        this.btnDeleteEntry.setName("btnDeleteEntry");
        this.contbuilding.setName("contbuilding");
        this.contpayUser.setName("contpayUser");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtsaleOrgUnit.setName("prmtsaleOrgUnit");
        this.prmtsellProject.setName("prmtsellProject");
        this.prmtmoneyDefine.setName("prmtmoneyDefine");
        this.txtpayAmount.setName("txtpayAmount");
        this.txtrevUserName.setName("txtrevUserName");
        this.prmtrevBank.setName("prmtrevBank");
        this.prmtpayBank.setName("prmtpayBank");
        this.txtrevAccountNumber.setName("txtrevAccountNumber");
        this.prmtpayAccountNumber.setName("prmtpayAccountNumber");
        this.prmtrevAccount.setName("prmtrevAccount");
        this.prmtoppAccount.setName("prmtoppAccount");
        this.prmtsettlementType.setName("prmtsettlementType");
        this.prmtcurrency.setName("prmtcurrency");
        this.txtexchangeRate.setName("txtexchangeRate");
        this.pkauditDate.setName("pkauditDate");
        this.prmtbuilding.setName("prmtbuilding");
        this.prmtpayUser.setName("prmtpayUser");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.menuAudit.setName("menuAudit");
        this.menuUnAudit.setName("menuUnAudit");
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
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"revBillType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"relateBillNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"relateBillEntryId\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /><t:Column t:key=\"relateBizId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" t:styleID=\"sCol7\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" t:styleID=\"sCol8\" /><t:Column t:key=\"actRevAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" t:styleID=\"sCol9\" /><t:Column t:key=\"payAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:configured=\"false\" t:styleID=\"sCol10\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">id</t:Cell><t:Cell t:configured=\"false\">关联单据类型</t:Cell><t:Cell t:configured=\"false\">关联单据编码</t:Cell><t:Cell t:configured=\"false\">关联交易单据明细id</t:Cell><t:Cell t:configured=\"false\">楼栋</t:Cell><t:Cell t:configured=\"false\">房间</t:Cell><t:Cell t:configured=\"false\">客户</t:Cell><t:Cell t:configured=\"false\">关联业务单据id</t:Cell><t:Cell t:configured=\"false\">应收金额</t:Cell><t:Cell t:configured=\"false\">实收金额</t:Cell><t:Cell t:configured=\"false\">支付金额</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

                this.kdtEntrys.putBindContents("editData",new String[] {"id","revBillType","relateBillNumber","relateBillEntryId","building","room","customer","relateBizId","appAmount","actRevAmount","payAmount"});


        this.kdtEntrys.checkParsed();
        KDComboBox kdtEntrys_revBillType_ComboBox = new KDComboBox();
        kdtEntrys_revBillType_ComboBox.setName("kdtEntrys_revBillType_ComboBox");
        kdtEntrys_revBillType_ComboBox.setVisible(true);
        kdtEntrys_revBillType_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basecrm.RelatBizType").toArray());
        KDTDefaultCellEditor kdtEntrys_revBillType_CellEditor = new KDTDefaultCellEditor(kdtEntrys_revBillType_ComboBox);
        this.kdtEntrys.getColumn("revBillType").setEditor(kdtEntrys_revBillType_CellEditor);
        KDTextField kdtEntrys_relateBillNumber_TextField = new KDTextField();
        kdtEntrys_relateBillNumber_TextField.setName("kdtEntrys_relateBillNumber_TextField");
        kdtEntrys_relateBillNumber_TextField.setMaxLength(44);
        KDTDefaultCellEditor kdtEntrys_relateBillNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_relateBillNumber_TextField);
        this.kdtEntrys.getColumn("relateBillNumber").setEditor(kdtEntrys_relateBillNumber_CellEditor);
        KDTextField kdtEntrys_relateBillEntryId_TextField = new KDTextField();
        kdtEntrys_relateBillEntryId_TextField.setName("kdtEntrys_relateBillEntryId_TextField");
        kdtEntrys_relateBillEntryId_TextField.setMaxLength(44);
        KDTDefaultCellEditor kdtEntrys_relateBillEntryId_CellEditor = new KDTDefaultCellEditor(kdtEntrys_relateBillEntryId_TextField);
        this.kdtEntrys.getColumn("relateBillEntryId").setEditor(kdtEntrys_relateBillEntryId_CellEditor);
        final KDBizPromptBox kdtEntrys_building_PromptBox = new KDBizPromptBox();
        kdtEntrys_building_PromptBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7BuildingQuery");
        kdtEntrys_building_PromptBox.setVisible(true);
        kdtEntrys_building_PromptBox.setEditable(true);
        kdtEntrys_building_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_building_PromptBox.setEditFormat("$number$");
        kdtEntrys_building_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_building_CellEditor = new KDTDefaultCellEditor(kdtEntrys_building_PromptBox);
        this.kdtEntrys.getColumn("building").setEditor(kdtEntrys_building_CellEditor);
        ObjectValueRender kdtEntrys_building_OVR = new ObjectValueRender();
        kdtEntrys_building_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("building").setRenderer(kdtEntrys_building_OVR);
        final KDBizPromptBox kdtEntrys_room_PromptBox = new KDBizPromptBox();
        kdtEntrys_room_PromptBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomQuery");
        kdtEntrys_room_PromptBox.setVisible(true);
        kdtEntrys_room_PromptBox.setEditable(true);
        kdtEntrys_room_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_room_PromptBox.setEditFormat("$number$");
        kdtEntrys_room_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_room_CellEditor = new KDTDefaultCellEditor(kdtEntrys_room_PromptBox);
        this.kdtEntrys.getColumn("room").setEditor(kdtEntrys_room_CellEditor);
        ObjectValueRender kdtEntrys_room_OVR = new ObjectValueRender();
        kdtEntrys_room_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("room").setRenderer(kdtEntrys_room_OVR);
        KDTextField kdtEntrys_customer_TextField = new KDTextField();
        kdtEntrys_customer_TextField.setName("kdtEntrys_customer_TextField");
        kdtEntrys_customer_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_customer_CellEditor = new KDTDefaultCellEditor(kdtEntrys_customer_TextField);
        this.kdtEntrys.getColumn("customer").setEditor(kdtEntrys_customer_CellEditor);
        KDTextField kdtEntrys_relateBizId_TextField = new KDTextField();
        kdtEntrys_relateBizId_TextField.setName("kdtEntrys_relateBizId_TextField");
        kdtEntrys_relateBizId_TextField.setMaxLength(44);
        KDTDefaultCellEditor kdtEntrys_relateBizId_CellEditor = new KDTDefaultCellEditor(kdtEntrys_relateBizId_TextField);
        this.kdtEntrys.getColumn("relateBizId").setEditor(kdtEntrys_relateBizId_CellEditor);
        KDFormattedTextField kdtEntrys_appAmount_TextField = new KDFormattedTextField();
        kdtEntrys_appAmount_TextField.setName("kdtEntrys_appAmount_TextField");
        kdtEntrys_appAmount_TextField.setVisible(true);
        kdtEntrys_appAmount_TextField.setEditable(true);
        kdtEntrys_appAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_appAmount_TextField.setDataType(1);
        	kdtEntrys_appAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_appAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_appAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_appAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_appAmount_TextField);
        this.kdtEntrys.getColumn("appAmount").setEditor(kdtEntrys_appAmount_CellEditor);
        KDFormattedTextField kdtEntrys_actRevAmount_TextField = new KDFormattedTextField();
        kdtEntrys_actRevAmount_TextField.setName("kdtEntrys_actRevAmount_TextField");
        kdtEntrys_actRevAmount_TextField.setVisible(true);
        kdtEntrys_actRevAmount_TextField.setEditable(true);
        kdtEntrys_actRevAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_actRevAmount_TextField.setDataType(1);
        	kdtEntrys_actRevAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_actRevAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_actRevAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_actRevAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_actRevAmount_TextField);
        this.kdtEntrys.getColumn("actRevAmount").setEditor(kdtEntrys_actRevAmount_CellEditor);
        KDFormattedTextField kdtEntrys_payAmount_TextField = new KDFormattedTextField();
        kdtEntrys_payAmount_TextField.setName("kdtEntrys_payAmount_TextField");
        kdtEntrys_payAmount_TextField.setVisible(true);
        kdtEntrys_payAmount_TextField.setEditable(true);
        kdtEntrys_payAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_payAmount_TextField.setDataType(1);
        	kdtEntrys_payAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntrys_payAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntrys_payAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntrys_payAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_payAmount_TextField);
        this.kdtEntrys.getColumn("payAmount").setEditor(kdtEntrys_payAmount_CellEditor);
        // contsaleOrgUnit		
        this.contsaleOrgUnit.setBoundLabelText(resHelper.getString("contsaleOrgUnit.boundLabelText"));		
        this.contsaleOrgUnit.setBoundLabelLength(100);		
        this.contsaleOrgUnit.setBoundLabelUnderline(true);		
        this.contsaleOrgUnit.setVisible(true);
        // contsellProject		
        this.contsellProject.setBoundLabelText(resHelper.getString("contsellProject.boundLabelText"));		
        this.contsellProject.setBoundLabelLength(100);		
        this.contsellProject.setBoundLabelUnderline(true);		
        this.contsellProject.setVisible(true);
        // contmoneyDefine		
        this.contmoneyDefine.setBoundLabelText(resHelper.getString("contmoneyDefine.boundLabelText"));		
        this.contmoneyDefine.setBoundLabelLength(100);		
        this.contmoneyDefine.setBoundLabelUnderline(true);		
        this.contmoneyDefine.setVisible(true);
        // contpayAmount		
        this.contpayAmount.setBoundLabelText(resHelper.getString("contpayAmount.boundLabelText"));		
        this.contpayAmount.setBoundLabelLength(100);		
        this.contpayAmount.setBoundLabelUnderline(true);		
        this.contpayAmount.setVisible(true);
        // contrevUserName		
        this.contrevUserName.setBoundLabelText(resHelper.getString("contrevUserName.boundLabelText"));		
        this.contrevUserName.setBoundLabelLength(100);		
        this.contrevUserName.setBoundLabelUnderline(true);		
        this.contrevUserName.setVisible(true);
        // contrevBank		
        this.contrevBank.setBoundLabelText(resHelper.getString("contrevBank.boundLabelText"));		
        this.contrevBank.setBoundLabelLength(100);		
        this.contrevBank.setBoundLabelUnderline(true);		
        this.contrevBank.setVisible(true);
        // contpayBank		
        this.contpayBank.setBoundLabelText(resHelper.getString("contpayBank.boundLabelText"));		
        this.contpayBank.setBoundLabelLength(100);		
        this.contpayBank.setBoundLabelUnderline(true);		
        this.contpayBank.setVisible(true);
        // contrevAccountNumber		
        this.contrevAccountNumber.setBoundLabelText(resHelper.getString("contrevAccountNumber.boundLabelText"));		
        this.contrevAccountNumber.setBoundLabelLength(100);		
        this.contrevAccountNumber.setBoundLabelUnderline(true);		
        this.contrevAccountNumber.setVisible(true);
        // contpayAccountNumber		
        this.contpayAccountNumber.setBoundLabelText(resHelper.getString("contpayAccountNumber.boundLabelText"));		
        this.contpayAccountNumber.setBoundLabelLength(100);		
        this.contpayAccountNumber.setBoundLabelUnderline(true);		
        this.contpayAccountNumber.setVisible(true);
        // contrevAccount		
        this.contrevAccount.setBoundLabelText(resHelper.getString("contrevAccount.boundLabelText"));		
        this.contrevAccount.setBoundLabelLength(100);		
        this.contrevAccount.setBoundLabelUnderline(true);		
        this.contrevAccount.setVisible(true);
        // contoppAccount		
        this.contoppAccount.setBoundLabelText(resHelper.getString("contoppAccount.boundLabelText"));		
        this.contoppAccount.setBoundLabelLength(100);		
        this.contoppAccount.setBoundLabelUnderline(true);		
        this.contoppAccount.setVisible(true);
        // contsettlementType		
        this.contsettlementType.setBoundLabelText(resHelper.getString("contsettlementType.boundLabelText"));		
        this.contsettlementType.setBoundLabelLength(100);		
        this.contsettlementType.setBoundLabelUnderline(true);		
        this.contsettlementType.setVisible(true);
        // contcurrency		
        this.contcurrency.setBoundLabelText(resHelper.getString("contcurrency.boundLabelText"));		
        this.contcurrency.setBoundLabelLength(100);		
        this.contcurrency.setBoundLabelUnderline(true);		
        this.contcurrency.setVisible(true);
        // contexchangeRate		
        this.contexchangeRate.setBoundLabelText(resHelper.getString("contexchangeRate.boundLabelText"));		
        this.contexchangeRate.setBoundLabelLength(100);		
        this.contexchangeRate.setBoundLabelUnderline(true);		
        this.contexchangeRate.setVisible(true);
        // contauditDate		
        this.contauditDate.setBoundLabelText(resHelper.getString("contauditDate.boundLabelText"));		
        this.contauditDate.setBoundLabelLength(100);		
        this.contauditDate.setBoundLabelUnderline(true);		
        this.contauditDate.setVisible(true);
        // btnSearchShowEntry		
        this.btnSearchShowEntry.setText(resHelper.getString("btnSearchShowEntry.text"));		
        this.btnSearchShowEntry.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showlist"));
        this.btnSearchShowEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSearchShowEntry_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDeleteEntry		
        this.btnDeleteEntry.setText(resHelper.getString("btnDeleteEntry.text"));		
        this.btnDeleteEntry.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        this.btnDeleteEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeleteEntry_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contbuilding		
        this.contbuilding.setBoundLabelText(resHelper.getString("contbuilding.boundLabelText"));		
        this.contbuilding.setBoundLabelLength(100);		
        this.contbuilding.setBoundLabelUnderline(true);		
        this.contbuilding.setVisible(true);
        // contpayUser		
        this.contpayUser.setBoundLabelText(resHelper.getString("contpayUser.boundLabelText"));		
        this.contpayUser.setBoundLabelLength(100);		
        this.contpayUser.setBoundLabelUnderline(true);		
        this.contpayUser.setVisible(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kDDateCreateTime		
        this.kDDateCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(44);		
        this.txtNumber.setRequired(true);
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);		
        this.pkBizDate.setRequired(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // prmtsaleOrgUnit		
        this.prmtsaleOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.SaleItemQuery");		
        this.prmtsaleOrgUnit.setVisible(true);		
        this.prmtsaleOrgUnit.setEditable(true);		
        this.prmtsaleOrgUnit.setDisplayFormat("$name$");		
        this.prmtsaleOrgUnit.setEditFormat("$number$");		
        this.prmtsaleOrgUnit.setCommitFormat("$number$");		
        this.prmtsaleOrgUnit.setRequired(true);		
        this.prmtsaleOrgUnit.setEnabled(false);
        		setOrgF7(prmtsaleOrgUnit,com.kingdee.eas.basedata.org.OrgType.getEnum("Sale"));
					
        // prmtsellProject		
        this.prmtsellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtsellProject.setVisible(true);		
        this.prmtsellProject.setEditable(true);		
        this.prmtsellProject.setDisplayFormat("$name$");		
        this.prmtsellProject.setEditFormat("$number$");		
        this.prmtsellProject.setCommitFormat("$number$");		
        this.prmtsellProject.setRequired(false);		
        this.prmtsellProject.setEnabled(false);
        // prmtmoneyDefine		
        this.prmtmoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.prmtmoneyDefine.setVisible(true);		
        this.prmtmoneyDefine.setEditable(true);		
        this.prmtmoneyDefine.setDisplayFormat("$name$");		
        this.prmtmoneyDefine.setEditFormat("$number$");		
        this.prmtmoneyDefine.setCommitFormat("$number$");		
        this.prmtmoneyDefine.setRequired(true);
        // txtpayAmount		
        this.txtpayAmount.setVisible(true);		
        this.txtpayAmount.setHorizontalAlignment(2);		
        this.txtpayAmount.setDataType(1);		
        this.txtpayAmount.setSupportedEmpty(true);		
        this.txtpayAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtpayAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtpayAmount.setPrecision(2);		
        this.txtpayAmount.setRequired(false);		
        this.txtpayAmount.setEnabled(false);
        // txtrevUserName		
        this.txtrevUserName.setVisible(true);		
        this.txtrevUserName.setHorizontalAlignment(2);		
        this.txtrevUserName.setMaxLength(44);		
        this.txtrevUserName.setRequired(true);
        // prmtrevBank		
        this.prmtrevBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.BankQuery");		
        this.prmtrevBank.setVisible(true);		
        this.prmtrevBank.setEditable(true);		
        this.prmtrevBank.setDisplayFormat("$name$");		
        this.prmtrevBank.setEditFormat("$number$");		
        this.prmtrevBank.setCommitFormat("$number$");		
        this.prmtrevBank.setRequired(true);
        // prmtpayBank		
        this.prmtpayBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.BankQuery");		
        this.prmtpayBank.setVisible(true);		
        this.prmtpayBank.setEditable(true);		
        this.prmtpayBank.setDisplayFormat("$name$");		
        this.prmtpayBank.setEditFormat("$number$");		
        this.prmtpayBank.setCommitFormat("$number$");		
        this.prmtpayBank.setRequired(true);
        // txtrevAccountNumber		
        this.txtrevAccountNumber.setVisible(true);		
        this.txtrevAccountNumber.setHorizontalAlignment(2);		
        this.txtrevAccountNumber.setMaxLength(44);		
        this.txtrevAccountNumber.setRequired(false);
        // prmtpayAccountNumber		
        this.prmtpayAccountNumber.setQueryInfo("com.kingdee.eas.basedata.assistant.app.AccountBankQuery");		
        this.prmtpayAccountNumber.setVisible(true);		
        this.prmtpayAccountNumber.setEditable(true);		
        this.prmtpayAccountNumber.setDisplayFormat("$bankAccountNumber$");		
        this.prmtpayAccountNumber.setEditFormat("$number$");		
        this.prmtpayAccountNumber.setCommitFormat("$number$");		
        this.prmtpayAccountNumber.setRequired(false);
        // prmtrevAccount		
        this.prmtrevAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtrevAccount.setVisible(true);		
        this.prmtrevAccount.setEditable(true);		
        this.prmtrevAccount.setDisplayFormat("$name$");		
        this.prmtrevAccount.setEditFormat("$number$");		
        this.prmtrevAccount.setCommitFormat("$number$");		
        this.prmtrevAccount.setRequired(false);
        // prmtoppAccount		
        this.prmtoppAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtoppAccount.setVisible(true);		
        this.prmtoppAccount.setEditable(true);		
        this.prmtoppAccount.setDisplayFormat("$name$");		
        this.prmtoppAccount.setEditFormat("$number$");		
        this.prmtoppAccount.setCommitFormat("$number$");		
        this.prmtoppAccount.setRequired(false);
        // prmtsettlementType		
        this.prmtsettlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");		
        this.prmtsettlementType.setVisible(true);		
        this.prmtsettlementType.setEditable(true);		
        this.prmtsettlementType.setDisplayFormat("$name$");		
        this.prmtsettlementType.setEditFormat("$number$");		
        this.prmtsettlementType.setCommitFormat("$number$");		
        this.prmtsettlementType.setRequired(false);
        // prmtcurrency		
        this.prmtcurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.prmtcurrency.setVisible(true);		
        this.prmtcurrency.setEditable(true);		
        this.prmtcurrency.setDisplayFormat("$name$");		
        this.prmtcurrency.setEditFormat("$number$");		
        this.prmtcurrency.setCommitFormat("$number$");		
        this.prmtcurrency.setRequired(false);		
        this.prmtcurrency.setEnabled(false);
        // txtexchangeRate		
        this.txtexchangeRate.setVisible(true);		
        this.txtexchangeRate.setHorizontalAlignment(2);		
        this.txtexchangeRate.setDataType(1);		
        this.txtexchangeRate.setSupportedEmpty(true);		
        this.txtexchangeRate.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtexchangeRate.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtexchangeRate.setPrecision(2);		
        this.txtexchangeRate.setRequired(false);		
        this.txtexchangeRate.setEnabled(false);
        // pkauditDate		
        this.pkauditDate.setVisible(true);		
        this.pkauditDate.setRequired(false);		
        this.pkauditDate.setEnabled(false);
        // prmtbuilding		
        this.prmtbuilding.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");		
        this.prmtbuilding.setVisible(true);		
        this.prmtbuilding.setEditable(true);		
        this.prmtbuilding.setDisplayFormat("$name$");		
        this.prmtbuilding.setEditFormat("$number$");		
        this.prmtbuilding.setCommitFormat("$number$");		
        this.prmtbuilding.setRequired(false);
        // prmtpayUser		
        this.prmtpayUser.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtpayUser.setVisible(true);		
        this.prmtpayUser.setEditable(true);		
        this.prmtpayUser.setDisplayFormat("$name$");		
        this.prmtpayUser.setEditFormat("$number$");		
        this.prmtpayUser.setCommitFormat("$number$");		
        this.prmtpayUser.setRequired(false);
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // menuAudit
        this.menuAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuAudit.setText(resHelper.getString("menuAudit.text"));		
        this.menuAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // menuUnAudit
        this.menuUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuUnAudit.setText(resHelper.getString("menuUnAudit.text"));		
        this.menuUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtsellProject,prmtmoneyDefine,txtpayAmount,txtrevUserName,prmtrevBank,txtrevAccountNumber,prmtpayBank,prmtpayAccountNumber,prmtrevAccount,prmtoppAccount,prmtsettlementType,prmtcurrency,txtexchangeRate,pkauditDate,prmtbuilding,prmtpayUser}));
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
        this.setBounds(new Rectangle(0, 0, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 629));
        contCreator.setBounds(new Rectangle(12, 577, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(12, 577, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(370, 577, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(370, 577, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_BOTTOM));
        contLastUpdateUser.setBounds(new Rectangle(729, 577, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(729, 577, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateTime.setBounds(new Rectangle(729, 604, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(729, 604, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(12, 12, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(12, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(12, 39, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(12, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(12, 175, 989, 46));
        this.add(contDescription, new KDLayout.Constraints(12, 175, 989, 46, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(12, 604, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(12, 604, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtEntrys.setBounds(new Rectangle(12, 250, 991, 317));
        this.add(kdtEntrys, new KDLayout.Constraints(12, 250, 991, 317, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contsaleOrgUnit.setBounds(new Rectangle(370, 12, 270, 19));
        this.add(contsaleOrgUnit, new KDLayout.Constraints(370, 12, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        contsellProject.setBounds(new Rectangle(729, 12, 270, 19));
        this.add(contsellProject, new KDLayout.Constraints(729, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contmoneyDefine.setBounds(new Rectangle(370, 39, 270, 19));
        this.add(contmoneyDefine, new KDLayout.Constraints(370, 39, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        contpayAmount.setBounds(new Rectangle(729, 147, 270, 19));
        this.add(contpayAmount, new KDLayout.Constraints(729, 147, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contrevUserName.setBounds(new Rectangle(12, 66, 270, 19));
        this.add(contrevUserName, new KDLayout.Constraints(12, 66, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrevBank.setBounds(new Rectangle(370, 66, 270, 19));
        this.add(contrevBank, new KDLayout.Constraints(370, 66, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        contpayBank.setBounds(new Rectangle(370, 93, 270, 19));
        this.add(contpayBank, new KDLayout.Constraints(370, 93, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        contrevAccountNumber.setBounds(new Rectangle(729, 66, 270, 19));
        this.add(contrevAccountNumber, new KDLayout.Constraints(729, 66, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contpayAccountNumber.setBounds(new Rectangle(729, 93, 270, 19));
        this.add(contpayAccountNumber, new KDLayout.Constraints(729, 93, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contrevAccount.setBounds(new Rectangle(12, 120, 270, 19));
        this.add(contrevAccount, new KDLayout.Constraints(12, 120, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contoppAccount.setBounds(new Rectangle(370, 120, 270, 19));
        this.add(contoppAccount, new KDLayout.Constraints(370, 120, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        contsettlementType.setBounds(new Rectangle(729, 120, 270, 19));
        this.add(contsettlementType, new KDLayout.Constraints(729, 120, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contcurrency.setBounds(new Rectangle(12, 147, 270, 19));
        this.add(contcurrency, new KDLayout.Constraints(12, 147, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contexchangeRate.setBounds(new Rectangle(370, 147, 270, 19));
        this.add(contexchangeRate, new KDLayout.Constraints(370, 147, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        contauditDate.setBounds(new Rectangle(370, 604, 270, 19));
        this.add(contauditDate, new KDLayout.Constraints(370, 604, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_BOTTOM));
        btnSearchShowEntry.setBounds(new Rectangle(799, 226, 84, 19));
        this.add(btnSearchShowEntry, new KDLayout.Constraints(799, 226, 84, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        btnDeleteEntry.setBounds(new Rectangle(896, 226, 62, 19));
        this.add(btnDeleteEntry, new KDLayout.Constraints(896, 226, 62, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        contbuilding.setBounds(new Rectangle(729, 39, 270, 19));
        this.add(contbuilding, new KDLayout.Constraints(729, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contpayUser.setBounds(new Rectangle(12, 93, 270, 19));
        this.add(contpayUser, new KDLayout.Constraints(12, 93, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
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
        //contsaleOrgUnit
        contsaleOrgUnit.setBoundEditor(prmtsaleOrgUnit);
        //contsellProject
        contsellProject.setBoundEditor(prmtsellProject);
        //contmoneyDefine
        contmoneyDefine.setBoundEditor(prmtmoneyDefine);
        //contpayAmount
        contpayAmount.setBoundEditor(txtpayAmount);
        //contrevUserName
        contrevUserName.setBoundEditor(txtrevUserName);
        //contrevBank
        contrevBank.setBoundEditor(prmtrevBank);
        //contpayBank
        contpayBank.setBoundEditor(prmtpayBank);
        //contrevAccountNumber
        contrevAccountNumber.setBoundEditor(txtrevAccountNumber);
        //contpayAccountNumber
        contpayAccountNumber.setBoundEditor(prmtpayAccountNumber);
        //contrevAccount
        contrevAccount.setBoundEditor(prmtrevAccount);
        //contoppAccount
        contoppAccount.setBoundEditor(prmtoppAccount);
        //contsettlementType
        contsettlementType.setBoundEditor(prmtsettlementType);
        //contcurrency
        contcurrency.setBoundEditor(prmtcurrency);
        //contexchangeRate
        contexchangeRate.setBoundEditor(txtexchangeRate);
        //contauditDate
        contauditDate.setBoundEditor(pkauditDate);
        //contbuilding
        contbuilding.setBoundEditor(prmtbuilding);
        //contpayUser
        contpayUser.setBoundEditor(prmtpayUser);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.basecrm.SubstituteTransfOutEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.revBillType", com.kingdee.util.enums.Enum.class, this.kdtEntrys, "revBillType.text");
		dataBinder.registerBinding("entrys.room", java.lang.Object.class, this.kdtEntrys, "room.text");
		dataBinder.registerBinding("entrys.payAmount", java.math.BigDecimal.class, this.kdtEntrys, "payAmount.text");
		dataBinder.registerBinding("entrys.relateBillNumber", String.class, this.kdtEntrys, "relateBillNumber.text");
		dataBinder.registerBinding("entrys.relateBillEntryId", String.class, this.kdtEntrys, "relateBillEntryId.text");
		dataBinder.registerBinding("entrys.building", com.kingdee.eas.fdc.sellhouse.BuildingInfo.class, this.kdtEntrys, "building.text");
		dataBinder.registerBinding("entrys.customer", String.class, this.kdtEntrys, "customer.text");
		dataBinder.registerBinding("entrys.relateBizId", String.class, this.kdtEntrys, "relateBizId.text");
		dataBinder.registerBinding("entrys.actRevAmount", java.math.BigDecimal.class, this.kdtEntrys, "actRevAmount.text");
		dataBinder.registerBinding("entrys.appAmount", java.math.BigDecimal.class, this.kdtEntrys, "appAmount.text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("saleOrgUnit", com.kingdee.eas.basedata.org.SaleOrgUnitInfo.class, this.prmtsaleOrgUnit, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtsellProject, "data");
		dataBinder.registerBinding("moneyDefine", com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo.class, this.prmtmoneyDefine, "data");
		dataBinder.registerBinding("payAmount", java.math.BigDecimal.class, this.txtpayAmount, "value");
		dataBinder.registerBinding("revUserName", String.class, this.txtrevUserName, "text");
		dataBinder.registerBinding("revBank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.prmtrevBank, "data");
		dataBinder.registerBinding("payBank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.prmtpayBank, "data");
		dataBinder.registerBinding("revAccountNumber", String.class, this.txtrevAccountNumber, "text");
		dataBinder.registerBinding("payAccountNumber", com.kingdee.eas.basedata.assistant.AccountBankInfo.class, this.prmtpayAccountNumber, "data");
		dataBinder.registerBinding("revAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtrevAccount, "data");
		dataBinder.registerBinding("oppAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtoppAccount, "data");
		dataBinder.registerBinding("settlementType", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.prmtsettlementType, "data");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtcurrency, "data");
		dataBinder.registerBinding("exchangeRate", java.math.BigDecimal.class, this.txtexchangeRate, "value");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.pkauditDate, "value");
		dataBinder.registerBinding("building", com.kingdee.eas.fdc.sellhouse.BuildingInfo.class, this.prmtbuilding, "data");
		dataBinder.registerBinding("payUser", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtpayUser, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basecrm.app.SubstituteTransfOutEditUIHandler";
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
        this.prmtsellProject.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo)ov;
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

	protected KDBizPromptBox getMainBizOrg() {
		return prmtsaleOrgUnit;
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
		getValidateHelper().registerBindProperty("entrys.revBillType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.payAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.relateBillNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.relateBillEntryId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.building", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.customer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.relateBizId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.actRevAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.appAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("moneyDefine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revUserName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revAccountNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payAccountNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oppAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("building", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payUser", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntrys_tableClicked method
     */
    protected void kdtEntrys_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnSearchShowEntry_actionPerformed method
     */
    protected void btnSearchShowEntry_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDeleteEntry_actionPerformed method
     */
    protected void btnDeleteEntry_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
    sic.add(new SelectorItemInfo("entrys.revBillType"));
        sic.add(new SelectorItemInfo("entrys.room.*"));
//        sic.add(new SelectorItemInfo("entrys.room.number"));
    sic.add(new SelectorItemInfo("entrys.payAmount"));
    sic.add(new SelectorItemInfo("entrys.relateBillNumber"));
    sic.add(new SelectorItemInfo("entrys.relateBillEntryId"));
        sic.add(new SelectorItemInfo("entrys.building.*"));
//        sic.add(new SelectorItemInfo("entrys.building.number"));
    sic.add(new SelectorItemInfo("entrys.customer"));
    sic.add(new SelectorItemInfo("entrys.relateBizId"));
    sic.add(new SelectorItemInfo("entrys.actRevAmount"));
    sic.add(new SelectorItemInfo("entrys.appAmount"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("saleOrgUnit.*"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("moneyDefine.*"));
        sic.add(new SelectorItemInfo("payAmount"));
        sic.add(new SelectorItemInfo("revUserName"));
        sic.add(new SelectorItemInfo("revBank.*"));
        sic.add(new SelectorItemInfo("payBank.*"));
        sic.add(new SelectorItemInfo("revAccountNumber"));
        sic.add(new SelectorItemInfo("payAccountNumber.*"));
        sic.add(new SelectorItemInfo("revAccount.*"));
        sic.add(new SelectorItemInfo("oppAccount.*"));
        sic.add(new SelectorItemInfo("settlementType.*"));
        sic.add(new SelectorItemInfo("currency.*"));
        sic.add(new SelectorItemInfo("exchangeRate"));
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("building.*"));
        sic.add(new SelectorItemInfo("payUser.*"));
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
            innerActionPerformed("eas", AbstractSubstituteTransfOutEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractSubstituteTransfOutEditUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basecrm.client", "SubstituteTransfOutEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.basecrm.client.SubstituteTransfOutEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.basecrm.SubstituteTransfOutFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo objectValue = new com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo();
				if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Sale")) != null && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Sale")).getBoolean("isBizUnit"))
			objectValue.put("saleOrgUnit",com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Sale")));
 
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/basecrm/SubstituteTransfOut";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.basecrm.app.SubstituteTransfOutQuery");
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
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}