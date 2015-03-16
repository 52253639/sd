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
public abstract class AbstractInviteDocumentsEditUI extends com.kingdee.eas.fdc.invite.client.BaseInviteEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInviteDocumentsEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPane2;
    protected com.kingdee.bos.ctrl.swing.KDContainer contContractModel;
    protected com.kingdee.bos.ctrl.swing.KDContainer contInviteBaseFile;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractModel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteBaseFile;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScope;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPay;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFormula;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDiff;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOther;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtScope;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane6;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtPay;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtFormula;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane4;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDiff;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane8;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtOther;
    protected com.kingdee.eas.fdc.invite.InviteDocumentsInfo editData = null;
    protected ActionAgreementText actionAgreementText = null;
    protected ActionViewContrntSelf actionViewContrntSelf = null;
    protected ActionViewContractModel actionViewContractModel = null;
    protected ActionAddBaseFile actionAddBaseFile = null;
    protected ActionDelBaseFile actionDelBaseFile = null;
    protected ShowProjects showProjects = null;
    protected ActionUpLoad actionUpLoad = null;
    protected ActionInviteUpLoad actionInviteUpLoad = null;
    protected ActionDownLoad actionDownLoad = null;
    protected ActionInviteDownLoad actionInviteDownLoad = null;
    /**
     * output class constructor
     */
    public AbstractInviteDocumentsEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInviteDocumentsEditUI.class.getName());
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
        actionAudit.setEnabled(true);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
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
        //actionAgreementText
        this.actionAgreementText = new ActionAgreementText(this);
        getActionManager().registerAction("actionAgreementText", actionAgreementText);
         this.actionAgreementText.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContrntSelf
        this.actionViewContrntSelf = new ActionViewContrntSelf(this);
        getActionManager().registerAction("actionViewContrntSelf", actionViewContrntSelf);
         this.actionViewContrntSelf.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContractModel
        this.actionViewContractModel = new ActionViewContractModel(this);
        getActionManager().registerAction("actionViewContractModel", actionViewContractModel);
         this.actionViewContractModel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddBaseFile
        this.actionAddBaseFile = new ActionAddBaseFile(this);
        getActionManager().registerAction("actionAddBaseFile", actionAddBaseFile);
         this.actionAddBaseFile.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelBaseFile
        this.actionDelBaseFile = new ActionDelBaseFile(this);
        getActionManager().registerAction("actionDelBaseFile", actionDelBaseFile);
         this.actionDelBaseFile.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //showProjects
        this.showProjects = new ShowProjects(this);
        getActionManager().registerAction("showProjects", showProjects);
         this.showProjects.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpLoad
        this.actionUpLoad = new ActionUpLoad(this);
        getActionManager().registerAction("actionUpLoad", actionUpLoad);
         this.actionUpLoad.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInviteUpLoad
        this.actionInviteUpLoad = new ActionInviteUpLoad(this);
        getActionManager().registerAction("actionInviteUpLoad", actionInviteUpLoad);
         this.actionInviteUpLoad.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDownLoad
        this.actionDownLoad = new ActionDownLoad(this);
        getActionManager().registerAction("actionDownLoad", actionDownLoad);
         this.actionDownLoad.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInviteDownLoad
        this.actionInviteDownLoad = new ActionInviteDownLoad(this);
        getActionManager().registerAction("actionInviteDownLoad", actionInviteDownLoad);
         this.actionInviteDownLoad.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPane2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contContractModel = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contInviteBaseFile = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.prmtContractModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtInviteBaseFile = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contScope = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPay = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFormula = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDiff = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOther = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtScope = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane6 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtPay = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtFormula = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane4 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDiff = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane8 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtOther = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDPanel1.setName("kDPanel1");
        this.kDPane2.setName("kDPane2");
        this.contContractModel.setName("contContractModel");
        this.contInviteBaseFile.setName("contInviteBaseFile");
        this.prmtContractModel.setName("prmtContractModel");
        this.prmtInviteBaseFile.setName("prmtInviteBaseFile");
        this.contScope.setName("contScope");
        this.contPay.setName("contPay");
        this.contFormula.setName("contFormula");
        this.contDiff.setName("contDiff");
        this.contOther.setName("contOther");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtScope.setName("txtScope");
        this.kDScrollPane6.setName("kDScrollPane6");
        this.txtPay.setName("txtPay");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtFormula.setName("txtFormula");
        this.kDScrollPane4.setName("kDScrollPane4");
        this.txtDiff.setName("txtDiff");
        this.kDScrollPane8.setName("kDScrollPane8");
        this.txtOther.setName("txtOther");
        // CoreUI		
        this.setMinimumSize(new Dimension(1030,600));		
        this.setPreferredSize(new Dimension(1030,600));		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);		
        this.contAuditTime.setEnabled(false);		
        this.contRespDept.setBoundLabelText(resHelper.getString("contRespDept.boundLabelText"));		
        this.contRespDept.setBoundLabelLength(100);		
        this.contRespDept.setBoundLabelUnderline(true);		
        this.contEntry.setTitle(resHelper.getString("contEntry.title"));		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setCommitFormat("$name$");		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");		
        this.pkCreateTime.setEnabled(false);		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.txtNumber.setRequired(true);		
        this.pkAuditTime.setEnabled(false);		
        this.prmtRespDept.setRequired(true);
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

        

        // kDTabbedPane1
        // kDPanel1
        // kDPane2
        // contContractModel		
        this.contContractModel.setTitle(resHelper.getString("contContractModel.title"));
        // contInviteBaseFile		
        this.contInviteBaseFile.setTitle(resHelper.getString("contInviteBaseFile.title"));
        // prmtContractModel		
        this.prmtContractModel.setCommitFormat("$number$");		
        this.prmtContractModel.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractModelQuery");		
        this.prmtContractModel.setDisplayFormat("$name$");		
        this.prmtContractModel.setEditFormat("$name$");
        this.prmtContractModel.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtContractModel_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtInviteBaseFile		
        this.prmtInviteBaseFile.setCommitFormat("$number$");		
        this.prmtInviteBaseFile.setQueryInfo("com.kingdee.eas.fdc.invite.app.InviteBaseFileQuery");		
        this.prmtInviteBaseFile.setDisplayFormat("$name$");		
        this.prmtInviteBaseFile.setEditFormat("$name$");
        this.prmtInviteBaseFile.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtInviteBaseFile_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contScope		
        this.contScope.setBoundLabelText(resHelper.getString("contScope.boundLabelText"));		
        this.contScope.setBoundLabelLength(180);		
        this.contScope.setBoundLabelUnderline(true);
        // contPay		
        this.contPay.setBoundLabelText(resHelper.getString("contPay.boundLabelText"));		
        this.contPay.setBoundLabelLength(180);		
        this.contPay.setBoundLabelUnderline(true);
        // contFormula		
        this.contFormula.setBoundLabelText(resHelper.getString("contFormula.boundLabelText"));		
        this.contFormula.setBoundLabelLength(180);		
        this.contFormula.setBoundLabelUnderline(true);
        // contDiff		
        this.contDiff.setBoundLabelText(resHelper.getString("contDiff.boundLabelText"));		
        this.contDiff.setBoundLabelLength(180);		
        this.contDiff.setBoundLabelUnderline(true);
        // contOther		
        this.contOther.setBoundLabelText(resHelper.getString("contOther.boundLabelText"));		
        this.contOther.setBoundLabelLength(180);		
        this.contOther.setBoundLabelUnderline(true);
        // kDScrollPane1
        // txtScope		
        this.txtScope.setRequired(true);		
        this.txtScope.setMaxLength(50000);
        // kDScrollPane6
        // txtPay		
        this.txtPay.setRequired(true);		
        this.txtPay.setMaxLength(50000);
        // kDScrollPane2
        // txtFormula		
        this.txtFormula.setMaxLength(50000);
        // kDScrollPane4
        // txtDiff		
        this.txtDiff.setRequired(true);		
        this.txtDiff.setMaxLength(50000);
        // kDScrollPane8
        // txtOther		
        this.txtOther.setMaxLength(50000);
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
        this.setBounds(new Rectangle(10, 10, 1030, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1030, 600));
        contCreator.setBounds(new Rectangle(13, 551, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(13, 551, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(357, 551, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(357, 551, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(13, 573, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(13, 573, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(876, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(876, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(357, 573, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(357, 573, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(357, 13, 270, 19));
        this.add(contName, new KDLayout.Constraints(357, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRespDept.setBounds(new Rectangle(702, 551, 270, 19));
        this.add(contRespDept, new KDLayout.Constraints(702, 551, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnShowProject.setBounds(new Rectangle(702, 13, 166, 19));
        this.add(btnShowProject, new KDLayout.Constraints(702, 13, 166, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contProject.setBounds(new Rectangle(10, 13, 270, 19));
        this.add(contProject, new KDLayout.Constraints(10, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane1.setBounds(new Rectangle(10, 37, 1012, 508));
        this.add(kDTabbedPane1, new KDLayout.Constraints(10, 37, 1012, 508, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contName
        contName.setBoundEditor(txtName);
        //contRespDept
        contRespDept.setBoundEditor(prmtRespDept);
        //contProject
        contProject.setBoundEditor(txtProject);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPane2, resHelper.getString("kDPane2.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1011, 475));        contEntry.setBounds(new Rectangle(2, 2, 632, 471));
        kDPanel1.add(contEntry, new KDLayout.Constraints(2, 2, 632, 471, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractModel.setBounds(new Rectangle(637, 63, 368, 50));
        kDPanel1.add(contContractModel, new KDLayout.Constraints(637, 63, 368, 50, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInviteBaseFile.setBounds(new Rectangle(637, 9, 368, 50));
        kDPanel1.add(contInviteBaseFile, new KDLayout.Constraints(637, 9, 368, 50, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contEntry
contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contEntry.getContentPane().add(kdtEntry, BorderLayout.CENTER);
        //contContractModel
contContractModel.getContentPane().setLayout(new BorderLayout(0, 0));        contContractModel.getContentPane().add(prmtContractModel, BorderLayout.CENTER);
        //contInviteBaseFile
contInviteBaseFile.getContentPane().setLayout(new BorderLayout(0, 0));        contInviteBaseFile.getContentPane().add(prmtInviteBaseFile, BorderLayout.CENTER);
        //kDPane2
        kDPane2.setLayout(new KDLayout());
        kDPane2.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1011, 475));        contScope.setBounds(new Rectangle(3, 2, 1000, 92));
        kDPane2.add(contScope, new KDLayout.Constraints(3, 2, 1000, 92, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contPay.setBounds(new Rectangle(3, 96, 1000, 92));
        kDPane2.add(contPay, new KDLayout.Constraints(3, 96, 1000, 92, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contFormula.setBounds(new Rectangle(3, 284, 1000, 92));
        kDPane2.add(contFormula, new KDLayout.Constraints(3, 284, 1000, 92, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contDiff.setBounds(new Rectangle(3, 190, 1000, 92));
        kDPane2.add(contDiff, new KDLayout.Constraints(3, 190, 1000, 92, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contOther.setBounds(new Rectangle(3, 378, 1000, 92));
        kDPane2.add(contOther, new KDLayout.Constraints(3, 378, 1000, 92, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contScope
        contScope.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtScope, null);
        //contPay
        contPay.setBoundEditor(kDScrollPane6);
        //kDScrollPane6
        kDScrollPane6.getViewport().add(txtPay, null);
        //contFormula
        contFormula.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtFormula, null);
        //contDiff
        contDiff.setBoundEditor(kDScrollPane4);
        //kDScrollPane4
        kDScrollPane4.getViewport().add(txtDiff, null);
        //contOther
        contOther.setBoundEditor(kDScrollPane8);
        //kDScrollPane8
        kDScrollPane8.getViewport().add(txtOther, null);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("respDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtRespDept, "data");
		dataBinder.registerBinding("inviteProject.name", String.class, this.txtProject, "text");
		dataBinder.registerBinding("contractModel", com.kingdee.eas.fdc.contract.ContractModelInfo.class, this.prmtContractModel, "data");
		dataBinder.registerBinding("inviteBaseFile", com.kingdee.eas.fdc.invite.InviteBaseFileInfo.class, this.prmtInviteBaseFile, "data");
		dataBinder.registerBinding("scope", String.class, this.txtScope, "text");
		dataBinder.registerBinding("payMethod", String.class, this.txtPay, "text");
		dataBinder.registerBinding("formula", String.class, this.txtFormula, "text");
		dataBinder.registerBinding("diff", String.class, this.txtDiff, "text");
		dataBinder.registerBinding("other", String.class, this.txtOther, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.InviteDocumentsEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.InviteDocumentsInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("respDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractModel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteBaseFile", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scope", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payMethod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("formula", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("diff", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("other", ValidateHelper.ON_SAVE);    		
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
     * output prmtContractModel_dataChanged method
     */
    protected void prmtContractModel_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtInviteBaseFile_dataChanged method
     */
    protected void prmtInviteBaseFile_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("respDept.*"));
        sic.add(new SelectorItemInfo("inviteProject.name"));
        sic.add(new SelectorItemInfo("contractModel.*"));
        sic.add(new SelectorItemInfo("inviteBaseFile.*"));
        sic.add(new SelectorItemInfo("scope"));
        sic.add(new SelectorItemInfo("payMethod"));
        sic.add(new SelectorItemInfo("formula"));
        sic.add(new SelectorItemInfo("diff"));
        sic.add(new SelectorItemInfo("other"));
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
     * output actionAgreementText_actionPerformed method
     */
    public void actionAgreementText_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContrntSelf_actionPerformed method
     */
    public void actionViewContrntSelf_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContractModel_actionPerformed method
     */
    public void actionViewContractModel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddBaseFile_actionPerformed method
     */
    public void actionAddBaseFile_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelBaseFile_actionPerformed method
     */
    public void actionDelBaseFile_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output showProjects_actionPerformed method
     */
    public void showProjects_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpLoad_actionPerformed method
     */
    public void actionUpLoad_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInviteUpLoad_actionPerformed method
     */
    public void actionInviteUpLoad_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDownLoad_actionPerformed method
     */
    public void actionDownLoad_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInviteDownLoad_actionPerformed method
     */
    public void actionInviteDownLoad_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionViewContrntSelf(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContrntSelf() {
    	return false;
    }
	public RequestContext prepareActionViewContractModel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContractModel() {
    	return false;
    }
	public RequestContext prepareActionAddBaseFile(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddBaseFile() {
    	return false;
    }
	public RequestContext prepareActionDelBaseFile(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelBaseFile() {
    	return false;
    }
	public RequestContext prepareShowProjects(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareShowProjects() {
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
	public RequestContext prepareActionInviteUpLoad(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInviteUpLoad() {
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
	public RequestContext prepareActionInviteDownLoad(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInviteDownLoad() {
    	return false;
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
            innerActionPerformed("eas", AbstractInviteDocumentsEditUI.this, "ActionAgreementText", "actionAgreementText_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContrntSelf class
     */     
    protected class ActionViewContrntSelf extends ItemAction {     
    
        public ActionViewContrntSelf()
        {
            this(null);
        }

        public ActionViewContrntSelf(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewContrntSelf.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContrntSelf.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContrntSelf.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteDocumentsEditUI.this, "ActionViewContrntSelf", "actionViewContrntSelf_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContractModel class
     */     
    protected class ActionViewContractModel extends ItemAction {     
    
        public ActionViewContractModel()
        {
            this(null);
        }

        public ActionViewContractModel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewContractModel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContractModel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContractModel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteDocumentsEditUI.this, "ActionViewContractModel", "actionViewContractModel_actionPerformed", e);
        }
    }

    /**
     * output ActionAddBaseFile class
     */     
    protected class ActionAddBaseFile extends ItemAction {     
    
        public ActionAddBaseFile()
        {
            this(null);
        }

        public ActionAddBaseFile(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddBaseFile.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddBaseFile.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddBaseFile.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteDocumentsEditUI.this, "ActionAddBaseFile", "actionAddBaseFile_actionPerformed", e);
        }
    }

    /**
     * output ActionDelBaseFile class
     */     
    protected class ActionDelBaseFile extends ItemAction {     
    
        public ActionDelBaseFile()
        {
            this(null);
        }

        public ActionDelBaseFile(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelBaseFile.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelBaseFile.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelBaseFile.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteDocumentsEditUI.this, "ActionDelBaseFile", "actionDelBaseFile_actionPerformed", e);
        }
    }

    /**
     * output ShowProjects class
     */     
    protected class ShowProjects extends ItemAction {     
    
        public ShowProjects()
        {
            this(null);
        }

        public ShowProjects(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ShowProjects.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ShowProjects.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ShowProjects.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteDocumentsEditUI.this, "ShowProjects", "showProjects_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractInviteDocumentsEditUI.this, "ActionUpLoad", "actionUpLoad_actionPerformed", e);
        }
    }

    /**
     * output ActionInviteUpLoad class
     */     
    protected class ActionInviteUpLoad extends ItemAction {     
    
        public ActionInviteUpLoad()
        {
            this(null);
        }

        public ActionInviteUpLoad(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInviteUpLoad.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInviteUpLoad.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInviteUpLoad.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteDocumentsEditUI.this, "ActionInviteUpLoad", "actionInviteUpLoad_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractInviteDocumentsEditUI.this, "ActionDownLoad", "actionDownLoad_actionPerformed", e);
        }
    }

    /**
     * output ActionInviteDownLoad class
     */     
    protected class ActionInviteDownLoad extends ItemAction {     
    
        public ActionInviteDownLoad()
        {
            this(null);
        }

        public ActionInviteDownLoad(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInviteDownLoad.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInviteDownLoad.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInviteDownLoad.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteDocumentsEditUI.this, "ActionInviteDownLoad", "actionInviteDownLoad_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "InviteDocumentsEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}