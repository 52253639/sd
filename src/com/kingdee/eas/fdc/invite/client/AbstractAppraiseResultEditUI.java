/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

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
public abstract class AbstractAppraiseResultEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAppraiseResultEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProject;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProjectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProjectName;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInviteMode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInivteOrg;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteProject;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCurProjectName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteFrom;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteMode;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteProjectOrg;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPreHit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnPreHit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnHit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnHit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnVirtualPreSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPreHit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnPreHit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemHit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnHit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conAttachmentList;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combAttachmentList;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewInviteFile;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInviteExecuteInfo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewContract;
    protected com.kingdee.eas.fdc.invite.AppraiseResultInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionPreHit actionPreHit = null;
    protected ActionHit actionHit = null;
    protected ActionUnPreHit actionUnPreHit = null;
    protected ActionUnHit actionUnHit = null;
    protected ActionVirtualPreSplit actionVirtualPreSplit = null;
    protected ActionViewAttachment actionViewAttachment = null;
    protected ActionAssoViewBill actionAssoViewBill = null;
    protected ActionInviteExecuteInfo actionInviteExecuteInfo = null;
    protected ActionViewContract actionViewContract = null;
    /**
     * output class constructor
     */
    public AbstractAppraiseResultEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAppraiseResultEditUI.class.getName());
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
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPreHit
        this.actionPreHit = new ActionPreHit(this);
        getActionManager().registerAction("actionPreHit", actionPreHit);
         this.actionPreHit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionHit
        this.actionHit = new ActionHit(this);
        getActionManager().registerAction("actionHit", actionHit);
         this.actionHit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnPreHit
        this.actionUnPreHit = new ActionUnPreHit(this);
        getActionManager().registerAction("actionUnPreHit", actionUnPreHit);
         this.actionUnPreHit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnHit
        this.actionUnHit = new ActionUnHit(this);
        getActionManager().registerAction("actionUnHit", actionUnHit);
         this.actionUnHit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionVirtualPreSplit
        this.actionVirtualPreSplit = new ActionVirtualPreSplit(this);
        getActionManager().registerAction("actionVirtualPreSplit", actionVirtualPreSplit);
         this.actionVirtualPreSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAttachment
        this.actionViewAttachment = new ActionViewAttachment(this);
        getActionManager().registerAction("actionViewAttachment", actionViewAttachment);
         this.actionViewAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAssoViewBill
        this.actionAssoViewBill = new ActionAssoViewBill(this);
        getActionManager().registerAction("actionAssoViewBill", actionAssoViewBill);
         this.actionAssoViewBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInviteExecuteInfo
        this.actionInviteExecuteInfo = new ActionInviteExecuteInfo(this);
        getActionManager().registerAction("actionInviteExecuteInfo", actionInviteExecuteInfo);
         this.actionInviteExecuteInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContract
        this.actionViewContract = new ActionViewContract(this);
        getActionManager().registerAction("actionViewContract", actionViewContract);
         this.actionViewContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contCurProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.conInviteMode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conInivteOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtInviteProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtCurProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInviteFrom = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInviteProjectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtInviteMode = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtInviteProjectOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPreHit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnPreHit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnHit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnHit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnVirtualPreSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPreHit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnPreHit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemHit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnHit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.conAttachmentList = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.combAttachmentList = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnViewAttachment = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnViewInviteFile = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInviteExecuteInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewContract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.contInviteProject.setName("contInviteProject");
        this.kDContainer1.setName("kDContainer1");
        this.contCurProjectName.setName("contCurProjectName");
        this.contInviteFrom.setName("contInviteFrom");
        this.contInviteProjectName.setName("contInviteProjectName");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.kDLabel1.setName("kDLabel1");
        this.conInviteMode.setName("conInviteMode");
        this.conInivteOrg.setName("conInivteOrg");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtInviteProject.setName("prmtInviteProject");
        this.kdtEntrys.setName("kdtEntrys");
        this.txtCurProjectName.setName("txtCurProjectName");
        this.txtInviteFrom.setName("txtInviteFrom");
        this.txtInviteProjectNumber.setName("txtInviteProjectNumber");
        this.txtDescription.setName("txtDescription");
        this.prmtInviteMode.setName("prmtInviteMode");
        this.txtInviteProjectOrg.setName("txtInviteProjectOrg");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnPreHit.setName("btnPreHit");
        this.btnUnPreHit.setName("btnUnPreHit");
        this.btnHit.setName("btnHit");
        this.btnUnHit.setName("btnUnHit");
        this.btnVirtualPreSplit.setName("btnVirtualPreSplit");
        this.menuItemAudit.setName("menuItemAudit");
        this.menuItemUnAudit.setName("menuItemUnAudit");
        this.menuItemPreHit.setName("menuItemPreHit");
        this.menuItemUnPreHit.setName("menuItemUnPreHit");
        this.menuItemHit.setName("menuItemHit");
        this.menuItemUnHit.setName("menuItemUnHit");
        this.conAttachmentList.setName("conAttachmentList");
        this.combAttachmentList.setName("combAttachmentList");
        this.btnViewAttachment.setName("btnViewAttachment");
        this.btnViewInviteFile.setName("btnViewInviteFile");
        this.btnInviteExecuteInfo.setName("btnInviteExecuteInfo");
        this.btnViewContract.setName("btnViewContract");
        this.menuItemViewContract.setName("menuItemViewContract");
        // CoreUI		
        this.setMinimumSize(new Dimension(1000,600));		
        this.setPreferredSize(new Dimension(1000,600));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contInviteProject		
        this.contInviteProject.setBoundLabelText(resHelper.getString("contInviteProject.boundLabelText"));		
        this.contInviteProject.setBoundLabelLength(100);		
        this.contInviteProject.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);
        // contCurProjectName		
        this.contCurProjectName.setBoundLabelText(resHelper.getString("contCurProjectName.boundLabelText"));		
        this.contCurProjectName.setBoundLabelLength(100);		
        this.contCurProjectName.setBoundLabelUnderline(true);
        // contInviteFrom		
        this.contInviteFrom.setBoundLabelText(resHelper.getString("contInviteFrom.boundLabelText"));		
        this.contInviteFrom.setBoundLabelLength(100);		
        this.contInviteFrom.setBoundLabelUnderline(true);
        // contInviteProjectName		
        this.contInviteProjectName.setBoundLabelText(resHelper.getString("contInviteProjectName.boundLabelText"));		
        this.contInviteProjectName.setBoundLabelLength(100);		
        this.contInviteProjectName.setBoundLabelUnderline(true);
        // kDScrollPane2
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // conInviteMode		
        this.conInviteMode.setBoundLabelText(resHelper.getString("conInviteMode.boundLabelText"));		
        this.conInviteMode.setBoundLabelUnderline(true);		
        this.conInviteMode.setBoundLabelLength(100);
        // conInivteOrg		
        this.conInivteOrg.setBoundLabelText(resHelper.getString("conInivteOrg.boundLabelText"));		
        this.conInivteOrg.setBoundLabelUnderline(true);		
        this.conInivteOrg.setBoundLabelLength(100);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setCommitFormat("$number$");		
        this.prmtAuditor.setEditFormat("$number$");		
        this.prmtAuditor.setDisplayFormat("$name$");
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // prmtInviteProject		
        this.prmtInviteProject.setDisplayFormat("$number$");		
        this.prmtInviteProject.setEditFormat("$number$");		
        this.prmtInviteProject.setCommitFormat("$number$");		
        this.prmtInviteProject.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteProjectQuery");
        this.prmtInviteProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtInviteProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"preHit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"hit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"supplierNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"supplierName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"isPartIn\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"score\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"expertAppraiseSeq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"bidAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"treatAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"totalSeq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"effective\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{preHit}</t:Cell><t:Cell>$Resource{hit}</t:Cell><t:Cell>$Resource{supplierNumber}</t:Cell><t:Cell>$Resource{supplierName}</t:Cell><t:Cell>$Resource{isPartIn}</t:Cell><t:Cell>$Resource{score}</t:Cell><t:Cell>$Resource{expertAppraiseSeq}</t:Cell><t:Cell>$Resource{bidAmount}</t:Cell><t:Cell>$Resource{treatAmount}</t:Cell><t:Cell>$Resource{totalSeq}</t:Cell><t:Cell>$Resource{effective}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
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

                this.kdtEntrys.putBindContents("editData",new String[] {"preHit","hit","supplier.number","supplier.name","isPartIn","score","expertAppraiseSeq","bidAmount","treatAmount","totalSeq","effective","remark"});


        // txtCurProjectName		
        this.txtCurProjectName.setEnabled(false);
        // txtInviteFrom		
        this.txtInviteFrom.setEnabled(false);
        // txtInviteProjectNumber		
        this.txtInviteProjectNumber.setEnabled(false);
        // txtDescription
        // prmtInviteMode		
        this.prmtInviteMode.setCommitFormat("$number$");		
        this.prmtInviteMode.setDisplayFormat("$name$");		
        this.prmtInviteMode.setEditFormat("$number$");		
        this.prmtInviteMode.setQueryInfo("com.kingdee.eas.fdc.invite.app.InviteModeQuery");		
        this.prmtInviteMode.setEnabled(false);
        // txtInviteProjectOrg		
        this.txtInviteProjectOrg.setEnabled(false);
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnPreHit
        this.btnPreHit.setAction((IItemAction)ActionProxyFactory.getProxy(actionPreHit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPreHit.setText(resHelper.getString("btnPreHit.text"));		
        this.btnPreHit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_affirm"));		
        this.btnPreHit.setToolTipText(resHelper.getString("btnPreHit.toolTipText"));
        // btnUnPreHit
        this.btnUnPreHit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnPreHit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnPreHit.setText(resHelper.getString("btnUnPreHit.text"));		
        this.btnUnPreHit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_faffirm"));		
        this.btnUnPreHit.setToolTipText(resHelper.getString("btnUnPreHit.toolTipText"));
        // btnHit
        this.btnHit.setAction((IItemAction)ActionProxyFactory.getProxy(actionHit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnHit.setText(resHelper.getString("btnHit.text"));		
        this.btnHit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_distribute"));		
        this.btnHit.setToolTipText(resHelper.getString("btnHit.toolTipText"));
        // btnUnHit
        this.btnUnHit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnHit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnHit.setText(resHelper.getString("btnUnHit.text"));		
        this.btnUnHit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_undistribute"));		
        this.btnUnHit.setToolTipText(resHelper.getString("btnUnHit.toolTipText"));
        // btnVirtualPreSplit
        this.btnVirtualPreSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionVirtualPreSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnVirtualPreSplit.setText(resHelper.getString("btnVirtualPreSplit.text"));		
        this.btnVirtualPreSplit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_split"));		
        this.btnVirtualPreSplit.setToolTipText(resHelper.getString("btnVirtualPreSplit.toolTipText"));		
        this.btnVirtualPreSplit.setVisible(false);		
        this.btnVirtualPreSplit.setEnabled(false);
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // menuItemUnAudit
        this.menuItemUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // menuItemPreHit
        this.menuItemPreHit.setAction((IItemAction)ActionProxyFactory.getProxy(actionPreHit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPreHit.setText(resHelper.getString("menuItemPreHit.text"));		
        this.menuItemPreHit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_affirm"));
        // menuItemUnPreHit
        this.menuItemUnPreHit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnPreHit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnPreHit.setText(resHelper.getString("menuItemUnPreHit.text"));		
        this.menuItemUnPreHit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_faffirm"));
        // menuItemHit
        this.menuItemHit.setAction((IItemAction)ActionProxyFactory.getProxy(actionHit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemHit.setText(resHelper.getString("menuItemHit.text"));		
        this.menuItemHit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_distribute"));
        // menuItemUnHit
        this.menuItemUnHit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnHit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnHit.setText(resHelper.getString("menuItemUnHit.text"));		
        this.menuItemUnHit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_undistribute"));
        // conAttachmentList		
        this.conAttachmentList.setBoundLabelText(resHelper.getString("conAttachmentList.boundLabelText"));		
        this.conAttachmentList.setBoundLabelUnderline(true);		
        this.conAttachmentList.setBoundLabelLength(100);
        // combAttachmentList		
        this.combAttachmentList.setActionCommand("combAttachmentList");
        // btnViewAttachment
        this.btnViewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAttachment.setText(resHelper.getString("btnViewAttachment.text"));
        // btnViewInviteFile
        this.btnViewInviteFile.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssoViewBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewInviteFile.setText(resHelper.getString("btnViewInviteFile.text"));
        // btnInviteExecuteInfo
        this.btnInviteExecuteInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionInviteExecuteInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInviteExecuteInfo.setText(resHelper.getString("btnInviteExecuteInfo.text"));		
        this.btnInviteExecuteInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_execute"));
        // btnViewContract
        this.btnViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContract.setText(resHelper.getString("btnViewContract.text"));		
        this.btnViewContract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showlist"));
        // menuItemViewContract
        this.menuItemViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewContract.setText(resHelper.getString("menuItemViewContract.text"));
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
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1000, 600));
        contCreator.setBounds(new Rectangle(10, 540, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 540, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(720, 540, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(720, 540, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(10, 570, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(10, 570, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(720, 570, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(720, 570, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInviteProject.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contInviteProject, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(10, 252, 980, 280));
        this.add(kDContainer1, new KDLayout.Constraints(10, 252, 980, 280, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurProjectName.setBounds(new Rectangle(721, 10, 270, 19));
        this.add(contCurProjectName, new KDLayout.Constraints(721, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInviteFrom.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contInviteFrom, new KDLayout.Constraints(10, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteProjectName.setBounds(new Rectangle(365, 10, 270, 19));
        this.add(contInviteProjectName, new KDLayout.Constraints(365, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane2.setBounds(new Rectangle(10, 115, 980, 130));
        this.add(kDScrollPane2, new KDLayout.Constraints(10, 115, 980, 130, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabel1.setBounds(new Rectangle(10, 97, 100, 19));
        this.add(kDLabel1, new KDLayout.Constraints(10, 97, 100, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        conInviteMode.setBounds(new Rectangle(365, 40, 270, 19));
        this.add(conInviteMode, new KDLayout.Constraints(365, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conInivteOrg.setBounds(new Rectangle(721, 40, 270, 19));
        this.add(conInivteOrg, new KDLayout.Constraints(721, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conAttachmentList.setBounds(new Rectangle(10, 70, 628, 19));
        this.add(conAttachmentList, new KDLayout.Constraints(10, 70, 628, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewAttachment.setBounds(new Rectangle(721, 70, 90, 21));
        this.add(btnViewAttachment, new KDLayout.Constraints(721, 70, 90, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contInviteProject
        contInviteProject.setBoundEditor(prmtInviteProject);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kdtEntrys, BorderLayout.CENTER);
        //contCurProjectName
        contCurProjectName.setBoundEditor(txtCurProjectName);
        //contInviteFrom
        contInviteFrom.setBoundEditor(txtInviteFrom);
        //contInviteProjectName
        contInviteProjectName.setBoundEditor(txtInviteProjectNumber);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtDescription, null);
        //conInviteMode
        conInviteMode.setBoundEditor(prmtInviteMode);
        //conInivteOrg
        conInivteOrg.setBoundEditor(txtInviteProjectOrg);
        //conAttachmentList
        conAttachmentList.setBoundEditor(combAttachmentList);

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
        menuView.add(menuItemViewContract);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemPreHit);
        menuBiz.add(menuItemUnPreHit);
        menuBiz.add(menuItemHit);
        menuBiz.add(menuItemUnHit);
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
        this.toolBar.add(btnPreHit);
        this.toolBar.add(btnUnPreHit);
        this.toolBar.add(btnHit);
        this.toolBar.add(btnUnHit);
        this.toolBar.add(btnVirtualPreSplit);
        this.toolBar.add(btnViewInviteFile);
        this.toolBar.add(btnViewContract);
        this.toolBar.add(btnInviteExecuteInfo);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("inviteProject", com.kingdee.eas.fdc.invite.InviteProjectInfo.class, this.prmtInviteProject, "data");
		dataBinder.registerBinding("entrys.score", double.class, this.kdtEntrys, "score.text");
		dataBinder.registerBinding("entrys.isPartIn", boolean.class, this.kdtEntrys, "isPartIn.text");
		dataBinder.registerBinding("entrys.expertAppraiseSeq", int.class, this.kdtEntrys, "expertAppraiseSeq.text");
		dataBinder.registerBinding("entrys.bidAmount", java.math.BigDecimal.class, this.kdtEntrys, "bidAmount.text");
		dataBinder.registerBinding("entrys.treatAmount", java.math.BigDecimal.class, this.kdtEntrys, "treatAmount.text");
		dataBinder.registerBinding("entrys.totalSeq", int.class, this.kdtEntrys, "totalSeq.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.invite.AppraiseResultEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.supplier.number", String.class, this.kdtEntrys, "supplierNumber.text");
		dataBinder.registerBinding("entrys.supplier.name", String.class, this.kdtEntrys, "supplierName.text");
		dataBinder.registerBinding("entrys.preHit", boolean.class, this.kdtEntrys, "preHit.text");
		dataBinder.registerBinding("entrys.hit", boolean.class, this.kdtEntrys, "hit.text");
		dataBinder.registerBinding("entrys.effective", com.kingdee.eas.fdc.invite.EffectivenessEnum.class, this.kdtEntrys, "effective.text");
		dataBinder.registerBinding("entrys.remark", String.class, this.kdtEntrys, "remark.text");
		dataBinder.registerBinding("inviteProject.project.name", String.class, this.txtCurProjectName, "text");
		dataBinder.registerBinding("inviteProject.inviteForm", com.kingdee.eas.fdc.invite.InviteFormEnum.class, this.txtInviteFrom, "text");
		dataBinder.registerBinding("inviteProject.number", String.class, this.txtInviteProjectNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("inviteProject.inviteMode", com.kingdee.eas.fdc.invite.InviteModeInfo.class, this.prmtInviteMode, "data");
		dataBinder.registerBinding("inviteProject.orgUnit.name", String.class, this.txtInviteProjectOrg, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.AppraiseResultEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.AppraiseResultInfo)ov;
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
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.isPartIn", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.expertAppraiseSeq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.bidAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.treatAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.totalSeq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.supplier.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.supplier.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.preHit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.hit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.effective", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.project.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.inviteForm", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.inviteMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.orgUnit.name", ValidateHelper.ON_SAVE);    		
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
     * output prmtInviteProject_dataChanged method
     */
    protected void prmtInviteProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("inviteProject.*"));
    sic.add(new SelectorItemInfo("entrys.score"));
    sic.add(new SelectorItemInfo("entrys.isPartIn"));
    sic.add(new SelectorItemInfo("entrys.expertAppraiseSeq"));
    sic.add(new SelectorItemInfo("entrys.bidAmount"));
    sic.add(new SelectorItemInfo("entrys.treatAmount"));
    sic.add(new SelectorItemInfo("entrys.totalSeq"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
    sic.add(new SelectorItemInfo("entrys.supplier.number"));
    sic.add(new SelectorItemInfo("entrys.supplier.name"));
    sic.add(new SelectorItemInfo("entrys.preHit"));
    sic.add(new SelectorItemInfo("entrys.hit"));
    sic.add(new SelectorItemInfo("entrys.effective"));
    sic.add(new SelectorItemInfo("entrys.remark"));
        sic.add(new SelectorItemInfo("inviteProject.project.name"));
        sic.add(new SelectorItemInfo("inviteProject.inviteForm"));
        sic.add(new SelectorItemInfo("inviteProject.number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("inviteProject.inviteMode"));
        sic.add(new SelectorItemInfo("inviteProject.orgUnit.name"));
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
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPreHit_actionPerformed method
     */
    public void actionPreHit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionHit_actionPerformed method
     */
    public void actionHit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnPreHit_actionPerformed method
     */
    public void actionUnPreHit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnHit_actionPerformed method
     */
    public void actionUnHit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionVirtualPreSplit_actionPerformed method
     */
    public void actionVirtualPreSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewAttachment_actionPerformed method
     */
    public void actionViewAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAssoViewBill_actionPerformed method
     */
    public void actionAssoViewBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInviteExecuteInfo_actionPerformed method
     */
    public void actionInviteExecuteInfo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContract_actionPerformed method
     */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionPreHit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPreHit() {
    	return false;
    }
	public RequestContext prepareActionHit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHit() {
    	return false;
    }
	public RequestContext prepareActionUnPreHit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnPreHit() {
    	return false;
    }
	public RequestContext prepareActionUnHit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnHit() {
    	return false;
    }
	public RequestContext prepareActionVirtualPreSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionVirtualPreSplit() {
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
	public RequestContext prepareActionAssoViewBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAssoViewBill() {
    	return false;
    }
	public RequestContext prepareActionInviteExecuteInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInviteExecuteInfo() {
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
            innerActionPerformed("eas", AbstractAppraiseResultEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractAppraiseResultEditUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionPreHit class
     */     
    protected class ActionPreHit extends ItemAction {     
    
        public ActionPreHit()
        {
            this(null);
        }

        public ActionPreHit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPreHit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPreHit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPreHit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAppraiseResultEditUI.this, "ActionPreHit", "actionPreHit_actionPerformed", e);
        }
    }

    /**
     * output ActionHit class
     */     
    protected class ActionHit extends ItemAction {     
    
        public ActionHit()
        {
            this(null);
        }

        public ActionHit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionHit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAppraiseResultEditUI.this, "ActionHit", "actionHit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnPreHit class
     */     
    protected class ActionUnPreHit extends ItemAction {     
    
        public ActionUnPreHit()
        {
            this(null);
        }

        public ActionUnPreHit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnPreHit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnPreHit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnPreHit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAppraiseResultEditUI.this, "ActionUnPreHit", "actionUnPreHit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnHit class
     */     
    protected class ActionUnHit extends ItemAction {     
    
        public ActionUnHit()
        {
            this(null);
        }

        public ActionUnHit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnHit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnHit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnHit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAppraiseResultEditUI.this, "ActionUnHit", "actionUnHit_actionPerformed", e);
        }
    }

    /**
     * output ActionVirtualPreSplit class
     */     
    protected class ActionVirtualPreSplit extends ItemAction {     
    
        public ActionVirtualPreSplit()
        {
            this(null);
        }

        public ActionVirtualPreSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionVirtualPreSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVirtualPreSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVirtualPreSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAppraiseResultEditUI.this, "ActionVirtualPreSplit", "actionVirtualPreSplit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractAppraiseResultEditUI.this, "ActionViewAttachment", "actionViewAttachment_actionPerformed", e);
        }
    }

    /**
     * output ActionAssoViewBill class
     */     
    protected class ActionAssoViewBill extends ItemAction {     
    
        public ActionAssoViewBill()
        {
            this(null);
        }

        public ActionAssoViewBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAssoViewBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssoViewBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssoViewBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAppraiseResultEditUI.this, "ActionAssoViewBill", "actionAssoViewBill_actionPerformed", e);
        }
    }

    /**
     * output ActionInviteExecuteInfo class
     */     
    protected class ActionInviteExecuteInfo extends ItemAction {     
    
        public ActionInviteExecuteInfo()
        {
            this(null);
        }

        public ActionInviteExecuteInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionInviteExecuteInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInviteExecuteInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInviteExecuteInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAppraiseResultEditUI.this, "ActionInviteExecuteInfo", "actionInviteExecuteInfo_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractAppraiseResultEditUI.this, "ActionViewContract", "actionViewContract_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "AppraiseResultEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}