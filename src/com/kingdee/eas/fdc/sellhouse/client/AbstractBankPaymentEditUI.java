/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractBankPaymentEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBankPaymentEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPaymentBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPaymentAccountBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettlementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettlementNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRevBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRevAccountBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPaymentDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPaymentAmout;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRevAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOppAccount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtBankPaymentEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDelPaymentEntry;
    protected com.kingdee.bos.ctrl.swing.KDButton btnselectPaymentEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyDefine;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPaymentBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPaymentAccountBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSettlementType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSettlementNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRevBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRevAccountBank;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPaymentDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPaymentAmout;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRevAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOppAccount;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtAreaDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCreateCheque;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClearCheque;
    protected com.kingdee.eas.fdc.sellhouse.BankPaymentInfo editData = null;
    protected ActionCreateCheque actionCreateCheque = null;
    protected ActionClearCheque actionClearCheque = null;
    /**
     * output class constructor
     */
    public AbstractBankPaymentEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBankPaymentEditUI.class.getName());
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
        //actionCreateCheque
        this.actionCreateCheque = new ActionCreateCheque(this);
        getActionManager().registerAction("actionCreateCheque", actionCreateCheque);
         this.actionCreateCheque.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClearCheque
        this.actionClearCheque = new ActionClearCheque(this);
        getActionManager().registerAction("actionClearCheque", actionClearCheque);
         this.actionClearCheque.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPaymentBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPaymentAccountBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettlementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettlementNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRevBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRevAccountBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPaymentDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPaymentAmout = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRevAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOppAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtBankPaymentEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnDelPaymentEntry = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnselectPaymentEntry = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPaymentBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPaymentAccountBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSettlementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSettlementNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtRevBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRevAccountBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkPaymentDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtPaymentAmout = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtRevAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtOppAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAreaDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnCreateCheque = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClearCheque = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contAuditor.setName("contAuditor");
        this.contName.setName("contName");
        this.contAuditTime.setName("contAuditTime");
        this.contProject.setName("contProject");
        this.contPaymentBank.setName("contPaymentBank");
        this.contPaymentAccountBank.setName("contPaymentAccountBank");
        this.contSettlementType.setName("contSettlementType");
        this.contSettlementNumber.setName("contSettlementNumber");
        this.contRevBank.setName("contRevBank");
        this.contRevAccountBank.setName("contRevAccountBank");
        this.contPaymentDate.setName("contPaymentDate");
        this.contPaymentAmout.setName("contPaymentAmout");
        this.contRevAccount.setName("contRevAccount");
        this.contOppAccount.setName("contOppAccount");
        this.kdtBankPaymentEntry.setName("kdtBankPaymentEntry");
        this.contDescription.setName("contDescription");
        this.btnDelPaymentEntry.setName("btnDelPaymentEntry");
        this.btnselectPaymentEntry.setName("btnselectPaymentEntry");
        this.contMoneyDefine.setName("contMoneyDefine");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtName.setName("txtName");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtProject.setName("prmtProject");
        this.prmtPaymentBank.setName("prmtPaymentBank");
        this.prmtPaymentAccountBank.setName("prmtPaymentAccountBank");
        this.prmtSettlementType.setName("prmtSettlementType");
        this.txtSettlementNumber.setName("txtSettlementNumber");
        this.prmtRevBank.setName("prmtRevBank");
        this.prmtRevAccountBank.setName("prmtRevAccountBank");
        this.pkPaymentDate.setName("pkPaymentDate");
        this.txtPaymentAmout.setName("txtPaymentAmout");
        this.prmtRevAccount.setName("prmtRevAccount");
        this.prmtOppAccount.setName("prmtOppAccount");
        this.txtAreaDescription.setName("txtAreaDescription");
        this.prmtMoneyDefine.setName("prmtMoneyDefine");
        this.btnCreateCheque.setName("btnCreateCheque");
        this.btnClearCheque.setName("btnClearCheque");
        // CoreUI
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
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contPaymentBank		
        this.contPaymentBank.setBoundLabelText(resHelper.getString("contPaymentBank.boundLabelText"));		
        this.contPaymentBank.setBoundLabelLength(100);		
        this.contPaymentBank.setBoundLabelUnderline(true);
        // contPaymentAccountBank		
        this.contPaymentAccountBank.setBoundLabelText(resHelper.getString("contPaymentAccountBank.boundLabelText"));		
        this.contPaymentAccountBank.setBoundLabelLength(100);		
        this.contPaymentAccountBank.setBoundLabelUnderline(true);
        // contSettlementType		
        this.contSettlementType.setBoundLabelText(resHelper.getString("contSettlementType.boundLabelText"));		
        this.contSettlementType.setBoundLabelLength(100);		
        this.contSettlementType.setBoundLabelUnderline(true);
        // contSettlementNumber		
        this.contSettlementNumber.setBoundLabelText(resHelper.getString("contSettlementNumber.boundLabelText"));		
        this.contSettlementNumber.setBoundLabelLength(100);		
        this.contSettlementNumber.setBoundLabelUnderline(true);
        // contRevBank		
        this.contRevBank.setBoundLabelText(resHelper.getString("contRevBank.boundLabelText"));		
        this.contRevBank.setBoundLabelLength(100);		
        this.contRevBank.setBoundLabelUnderline(true);
        // contRevAccountBank		
        this.contRevAccountBank.setBoundLabelText(resHelper.getString("contRevAccountBank.boundLabelText"));		
        this.contRevAccountBank.setBoundLabelLength(100);		
        this.contRevAccountBank.setBoundLabelUnderline(true);
        // contPaymentDate		
        this.contPaymentDate.setBoundLabelText(resHelper.getString("contPaymentDate.boundLabelText"));		
        this.contPaymentDate.setBoundLabelLength(100);		
        this.contPaymentDate.setBoundLabelUnderline(true);
        // contPaymentAmout		
        this.contPaymentAmout.setBoundLabelText(resHelper.getString("contPaymentAmout.boundLabelText"));		
        this.contPaymentAmout.setBoundLabelLength(100);		
        this.contPaymentAmout.setBoundLabelUnderline(true);
        // contRevAccount		
        this.contRevAccount.setBoundLabelText(resHelper.getString("contRevAccount.boundLabelText"));		
        this.contRevAccount.setBoundLabelLength(100);		
        this.contRevAccount.setBoundLabelUnderline(true);		
        this.contRevAccount.setEnabled(false);		
        this.contRevAccount.setVisible(false);
        // contOppAccount		
        this.contOppAccount.setBoundLabelText(resHelper.getString("contOppAccount.boundLabelText"));		
        this.contOppAccount.setBoundLabelLength(100);		
        this.contOppAccount.setBoundLabelUnderline(true);		
        this.contOppAccount.setEnabled(false);		
        this.contOppAccount.setVisible(false);
        // kdtBankPaymentEntry
		String kdtBankPaymentEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"cusomter\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"signManagerName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"paymentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"receiptDisName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"invoiceDisName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{cusomter}</t:Cell><t:Cell>$Resource{signManagerName}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{paymentAmount}</t:Cell><t:Cell>$Resource{receiptDisName}</t:Cell><t:Cell>$Resource{invoiceDisName}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtBankPaymentEntry.setFormatXml(resHelper.translateString("kdtBankPaymentEntry",kdtBankPaymentEntryStrXML));

        

        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // btnDelPaymentEntry		
        this.btnDelPaymentEntry.setText(resHelper.getString("btnDelPaymentEntry.text"));
        this.btnDelPaymentEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelPaymentEntry_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnselectPaymentEntry		
        this.btnselectPaymentEntry.setText(resHelper.getString("btnselectPaymentEntry.text"));
        this.btnselectPaymentEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnselectPaymentEntry_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contMoneyDefine		
        this.contMoneyDefine.setBoundLabelText(resHelper.getString("contMoneyDefine.boundLabelText"));		
        this.contMoneyDefine.setBoundLabelLength(100);		
        this.contMoneyDefine.setBoundLabelUnderline(true);
        // prmtCreator
        // pkCreateTime
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        this.txtNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtNumber_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // prmtAuditor
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(80);
        // pkAuditTime
        // prmtProject		
        this.prmtProject.setDisplayFormat("$name$");		
        this.prmtProject.setEditFormat("$number$");		
        this.prmtProject.setCommitFormat("$number$");		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
        // prmtPaymentBank		
        this.prmtPaymentBank.setDisplayFormat("$name$");		
        this.prmtPaymentBank.setEditFormat("$number$");		
        this.prmtPaymentBank.setCommitFormat("$number$");		
        this.prmtPaymentBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.BankQuery");
        this.prmtPaymentBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtPaymentBank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtPaymentAccountBank		
        this.prmtPaymentAccountBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.AccountBankQuery");		
        this.prmtPaymentAccountBank.setCommitFormat("$number$");		
        this.prmtPaymentAccountBank.setEditFormat("$number$");		
        this.prmtPaymentAccountBank.setDisplayFormat("$name$");
        this.prmtPaymentAccountBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtPaymentAccountBank_dataChanged(e);
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
        // prmtRevBank		
        this.prmtRevBank.setDisplayFormat("$name$");		
        this.prmtRevBank.setEditFormat("$number$");		
        this.prmtRevBank.setCommitFormat("$number$");		
        this.prmtRevBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.BankQuery");
        this.prmtRevBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRevBank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtRevAccountBank		
        this.prmtRevAccountBank.setCommitFormat("$number$");		
        this.prmtRevAccountBank.setEditFormat("$number$");		
        this.prmtRevAccountBank.setDisplayFormat("$name$");		
        this.prmtRevAccountBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.AccountBankQuery");
        this.prmtRevAccountBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRevAccountBank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkPaymentDate		
        this.pkPaymentDate.setRequired(true);
        // txtPaymentAmout
        // prmtRevAccount		
        this.prmtRevAccount.setCommitFormat("$number$");		
        this.prmtRevAccount.setEditFormat("$number$");		
        this.prmtRevAccount.setDisplayFormat("$name$");		
        this.prmtRevAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
        // prmtOppAccount		
        this.prmtOppAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");		
        this.prmtOppAccount.setCommitFormat("$number$");		
        this.prmtOppAccount.setEditFormat("$number$");		
        this.prmtOppAccount.setDisplayFormat("$name$");
        // txtAreaDescription		
        this.txtAreaDescription.setMaxLength(80);
        // prmtMoneyDefine		
        this.prmtMoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
        // btnCreateCheque		
        this.btnCreateCheque.setText(resHelper.getString("btnCreateCheque.text"));
        // btnClearCheque		
        this.btnClearCheque.setText(resHelper.getString("btnClearCheque.text"));
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
        this.setBounds(new Rectangle(10, 10, 1000, 600));
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(12, 543, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(364, 543, 270, 19));
        this.add(contCreateTime, null);
        contNumber.setBounds(new Rectangle(12, 15, 270, 19));
        this.add(contNumber, null);
        contAuditor.setBounds(new Rectangle(12, 565, 270, 19));
        this.add(contAuditor, null);
        contName.setBounds(new Rectangle(364, 15, 270, 19));
        this.add(contName, null);
        contAuditTime.setBounds(new Rectangle(364, 565, 270, 19));
        this.add(contAuditTime, null);
        contProject.setBounds(new Rectangle(717, 15, 270, 19));
        this.add(contProject, null);
        contPaymentBank.setBounds(new Rectangle(364, 37, 270, 19));
        this.add(contPaymentBank, null);
        contPaymentAccountBank.setBounds(new Rectangle(12, 37, 270, 19));
        this.add(contPaymentAccountBank, null);
        contSettlementType.setBounds(new Rectangle(717, 37, 270, 19));
        this.add(contSettlementType, null);
        contSettlementNumber.setBounds(new Rectangle(717, 59, 270, 19));
        this.add(contSettlementNumber, null);
        contRevBank.setBounds(new Rectangle(364, 59, 270, 19));
        this.add(contRevBank, null);
        contRevAccountBank.setBounds(new Rectangle(12, 59, 270, 19));
        this.add(contRevAccountBank, null);
        contPaymentDate.setBounds(new Rectangle(717, 81, 270, 19));
        this.add(contPaymentDate, null);
        contPaymentAmout.setBounds(new Rectangle(364, 81, 270, 19));
        this.add(contPaymentAmout, null);
        contRevAccount.setBounds(new Rectangle(691, 117, 270, 19));
        this.add(contRevAccount, null);
        contOppAccount.setBounds(new Rectangle(684, 141, 270, 19));
        this.add(contOppAccount, null);
        kdtBankPaymentEntry.setBounds(new Rectangle(12, 192, 973, 333));
        this.add(kdtBankPaymentEntry, null);
        contDescription.setBounds(new Rectangle(12, 103, 623, 61));
        this.add(contDescription, null);
        btnDelPaymentEntry.setBounds(new Rectangle(879, 166, 108, 21));
        this.add(btnDelPaymentEntry, null);
        btnselectPaymentEntry.setBounds(new Rectangle(754, 166, 108, 21));
        this.add(btnselectPaymentEntry, null);
        contMoneyDefine.setBounds(new Rectangle(12, 81, 270, 19));
        this.add(contMoneyDefine, null);
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
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contPaymentBank
        contPaymentBank.setBoundEditor(prmtPaymentBank);
        //contPaymentAccountBank
        contPaymentAccountBank.setBoundEditor(prmtPaymentAccountBank);
        //contSettlementType
        contSettlementType.setBoundEditor(prmtSettlementType);
        //contSettlementNumber
        contSettlementNumber.setBoundEditor(txtSettlementNumber);
        //contRevBank
        contRevBank.setBoundEditor(prmtRevBank);
        //contRevAccountBank
        contRevAccountBank.setBoundEditor(prmtRevAccountBank);
        //contPaymentDate
        contPaymentDate.setBoundEditor(pkPaymentDate);
        //contPaymentAmout
        contPaymentAmout.setBoundEditor(txtPaymentAmout);
        //contRevAccount
        contRevAccount.setBoundEditor(prmtRevAccount);
        //contOppAccount
        contOppAccount.setBoundEditor(prmtOppAccount);
        //contDescription
        contDescription.setBoundEditor(txtAreaDescription);
        //contMoneyDefine
        contMoneyDefine.setBoundEditor(prmtMoneyDefine);

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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
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
        this.toolBar.add(btnCreateCheque);
        this.toolBar.add(btnClearCheque);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("paymentBank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.prmtPaymentBank, "data");
		dataBinder.registerBinding("paymentAccountBank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.prmtPaymentAccountBank, "data");
		dataBinder.registerBinding("settlementType", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.prmtSettlementType, "data");
		dataBinder.registerBinding("settlementNumber", String.class, this.txtSettlementNumber, "text");
		dataBinder.registerBinding("revBank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.prmtRevBank, "data");
		dataBinder.registerBinding("revAccountBank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.prmtRevAccountBank, "data");
		dataBinder.registerBinding("paymentDate", java.util.Date.class, this.pkPaymentDate, "value");
		dataBinder.registerBinding("paymentAmout", java.math.BigDecimal.class, this.txtPaymentAmout, "value");
		dataBinder.registerBinding("revAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtRevAccount, "data");
		dataBinder.registerBinding("oppAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtOppAccount, "data");
		dataBinder.registerBinding("description", String.class, this.txtAreaDescription, "text");
		dataBinder.registerBinding("moneyDefine", com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo.class, this.prmtMoneyDefine, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.BankPaymentEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.BankPaymentInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paymentBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paymentAccountBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revAccountBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paymentDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paymentAmout", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oppAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("moneyDefine", ValidateHelper.ON_SAVE);    		
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
     * output btnDelPaymentEntry_actionPerformed method
     */
    protected void btnDelPaymentEntry_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnselectPaymentEntry_actionPerformed method
     */
    protected void btnselectPaymentEntry_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtNumber_actionPerformed method
     */
    protected void txtNumber_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtPaymentBank_dataChanged method
     */
    protected void prmtPaymentBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output prmtPaymentAccountBank_dataChanged method
     */
    protected void prmtPaymentAccountBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output prmtRevBank_dataChanged method
     */
    protected void prmtRevBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output prmtRevAccountBank_dataChanged method
     */
    protected void prmtRevAccountBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("paymentBank.*"));
        sic.add(new SelectorItemInfo("paymentAccountBank.*"));
        sic.add(new SelectorItemInfo("settlementType.*"));
        sic.add(new SelectorItemInfo("settlementNumber"));
        sic.add(new SelectorItemInfo("revBank.*"));
        sic.add(new SelectorItemInfo("revAccountBank.*"));
        sic.add(new SelectorItemInfo("paymentDate"));
        sic.add(new SelectorItemInfo("paymentAmout"));
        sic.add(new SelectorItemInfo("revAccount.*"));
        sic.add(new SelectorItemInfo("oppAccount.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("moneyDefine.*"));
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
     * output actionCreateCheque_actionPerformed method
     */
    public void actionCreateCheque_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClearCheque_actionPerformed method
     */
    public void actionClearCheque_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionCreateCheque(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreateCheque() {
    	return false;
    }
	public RequestContext prepareActionClearCheque(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClearCheque() {
    	return false;
    }

    /**
     * output ActionCreateCheque class
     */     
    protected class ActionCreateCheque extends ItemAction {     
    
        public ActionCreateCheque()
        {
            this(null);
        }

        public ActionCreateCheque(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCreateCheque.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateCheque.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateCheque.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBankPaymentEditUI.this, "ActionCreateCheque", "actionCreateCheque_actionPerformed", e);
        }
    }

    /**
     * output ActionClearCheque class
     */     
    protected class ActionClearCheque extends ItemAction {     
    
        public ActionClearCheque()
        {
            this(null);
        }

        public ActionClearCheque(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionClearCheque.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearCheque.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearCheque.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBankPaymentEditUI.this, "ActionClearCheque", "actionClearCheque_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BankPaymentEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}