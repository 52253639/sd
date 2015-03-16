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
public abstract class AbstractInviteSupplierEnterBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInviteSupplierEnterBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contResponsibleDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterCriterion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contResponsiblePerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contResponsibleDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInviteProjectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInviteRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conCurProjectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInviteTypeName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conAttachmentList;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane remarkScrollPanel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkResponsibleDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtResponsiblePerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtResponsibleDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteProjectRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCurProjectName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteTypeName;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbAttachmentList;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane enterScrollPanel;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtEnterCriterion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInviteExecuteInfo;
    protected com.kingdee.eas.fdc.invite.InviteSupplierEnterBillInfo editData = null;
    protected ActionViewAttachment actionViewAttachment = null;
    protected ActionInviteExecuteInfo actionInviteExecuteInfo = null;
    /**
     * output class constructor
     */
    public AbstractInviteSupplierEnterBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInviteSupplierEnterBillEditUI.class.getName());
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
        //actionViewAttachment
        this.actionViewAttachment = new ActionViewAttachment(this);
        getActionManager().registerAction("actionViewAttachment", actionViewAttachment);
         this.actionViewAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInviteExecuteInfo
        this.actionInviteExecuteInfo = new ActionInviteExecuteInfo(this);
        getActionManager().registerAction("actionInviteExecuteInfo", actionInviteExecuteInfo);
         this.actionInviteExecuteInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contResponsibleDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnterCriterion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contResponsiblePerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contResponsibleDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conInviteProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conInviteRespPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conCurProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conInviteTypeName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conAttachmentList = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewAttachment = new com.kingdee.bos.ctrl.swing.KDButton();
        this.remarkScrollPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkResponsibleDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtResponsiblePerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtResponsibleDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtInviteProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtInviteProjectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInviteProjectRespPerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCurProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInviteTypeName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.cmbAttachmentList = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.enterScrollPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtEnterCriterion = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnInviteExecuteInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contResponsibleDate.setName("contResponsibleDate");
        this.contRemark.setName("contRemark");
        this.contEnterCriterion.setName("contEnterCriterion");
        this.contResponsiblePerson.setName("contResponsiblePerson");
        this.contResponsibleDept.setName("contResponsibleDept");
        this.contInviteProject.setName("contInviteProject");
        this.conInviteProjectName.setName("conInviteProjectName");
        this.conInviteRespPerson.setName("conInviteRespPerson");
        this.conCurProjectName.setName("conCurProjectName");
        this.conInviteTypeName.setName("conInviteTypeName");
        this.conAttachmentList.setName("conAttachmentList");
        this.btnViewAttachment.setName("btnViewAttachment");
        this.remarkScrollPanel.setName("remarkScrollPanel");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.pkResponsibleDate.setName("pkResponsibleDate");
        this.prmtResponsiblePerson.setName("prmtResponsiblePerson");
        this.prmtResponsibleDept.setName("prmtResponsibleDept");
        this.prmtInviteProject.setName("prmtInviteProject");
        this.txtInviteProjectNumber.setName("txtInviteProjectNumber");
        this.txtInviteProjectRespPerson.setName("txtInviteProjectRespPerson");
        this.txtCurProjectName.setName("txtCurProjectName");
        this.txtInviteTypeName.setName("txtInviteTypeName");
        this.cmbAttachmentList.setName("cmbAttachmentList");
        this.txtRemark.setName("txtRemark");
        this.enterScrollPanel.setName("enterScrollPanel");
        this.txtEnterCriterion.setName("txtEnterCriterion");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.btnInviteExecuteInfo.setName("btnInviteExecuteInfo");
        // CoreUI		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuView.setVisible(false);		
        this.menuBiz.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.btnAudit.setVisible(false);		
        this.btnUnAudit.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contResponsibleDate		
        this.contResponsibleDate.setBoundLabelText(resHelper.getString("contResponsibleDate.boundLabelText"));		
        this.contResponsibleDate.setBoundLabelLength(100);		
        this.contResponsibleDate.setBoundLabelUnderline(true);
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelLength(100);		
        this.contRemark.setBoundLabelUnderline(true);
        // contEnterCriterion		
        this.contEnterCriterion.setBoundLabelLength(100);		
        this.contEnterCriterion.setBoundLabelUnderline(true);		
        this.contEnterCriterion.setBoundLabelText(resHelper.getString("contEnterCriterion.boundLabelText"));
        // contResponsiblePerson		
        this.contResponsiblePerson.setBoundLabelText(resHelper.getString("contResponsiblePerson.boundLabelText"));		
        this.contResponsiblePerson.setBoundLabelLength(100);		
        this.contResponsiblePerson.setBoundLabelUnderline(true);
        // contResponsibleDept		
        this.contResponsibleDept.setBoundLabelText(resHelper.getString("contResponsibleDept.boundLabelText"));		
        this.contResponsibleDept.setBoundLabelLength(100);		
        this.contResponsibleDept.setBoundLabelUnderline(true);
        // contInviteProject		
        this.contInviteProject.setBoundLabelText(resHelper.getString("contInviteProject.boundLabelText"));		
        this.contInviteProject.setBoundLabelLength(100);		
        this.contInviteProject.setBoundLabelUnderline(true);
        // conInviteProjectName		
        this.conInviteProjectName.setBoundLabelText(resHelper.getString("conInviteProjectName.boundLabelText"));		
        this.conInviteProjectName.setBoundLabelLength(100);		
        this.conInviteProjectName.setBoundLabelUnderline(true);
        // conInviteRespPerson		
        this.conInviteRespPerson.setBoundLabelText(resHelper.getString("conInviteRespPerson.boundLabelText"));		
        this.conInviteRespPerson.setBoundLabelUnderline(true);		
        this.conInviteRespPerson.setBoundLabelLength(100);
        // conCurProjectName		
        this.conCurProjectName.setBoundLabelText(resHelper.getString("conCurProjectName.boundLabelText"));		
        this.conCurProjectName.setBoundLabelUnderline(true);		
        this.conCurProjectName.setBoundLabelLength(100);
        // conInviteTypeName		
        this.conInviteTypeName.setBoundLabelText(resHelper.getString("conInviteTypeName.boundLabelText"));		
        this.conInviteTypeName.setBoundLabelUnderline(true);		
        this.conInviteTypeName.setBoundLabelLength(100);
        // conAttachmentList		
        this.conAttachmentList.setBoundLabelText(resHelper.getString("conAttachmentList.boundLabelText"));		
        this.conAttachmentList.setBoundLabelUnderline(true);		
        this.conAttachmentList.setBoundLabelLength(100);
        // btnViewAttachment
        this.btnViewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAttachment.setText(resHelper.getString("btnViewAttachment.text"));
        // remarkScrollPanel		
        this.remarkScrollPanel.setAutoscrolls(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // pkResponsibleDate
        // prmtResponsiblePerson		
        this.prmtResponsiblePerson.setDisplayFormat("$name$");		
        this.prmtResponsiblePerson.setEditFormat("$number$");		
        this.prmtResponsiblePerson.setCommitFormat("$number$");		
        this.prmtResponsiblePerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtResponsiblePerson.setRequired(true);
        // prmtResponsibleDept		
        this.prmtResponsibleDept.setDisplayFormat("$name$");		
        this.prmtResponsibleDept.setEditFormat("$number$");		
        this.prmtResponsibleDept.setCommitFormat("$number$");		
        this.prmtResponsibleDept.setQueryInfo("com.kingdee.eas.basedata.org.client.f7.AdminF7");
        // prmtInviteProject		
        this.prmtInviteProject.setDisplayFormat("$name$");		
        this.prmtInviteProject.setEditFormat("$number$");		
        this.prmtInviteProject.setCommitFormat("$number$");		
        this.prmtInviteProject.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteProjectQuery");		
        this.prmtInviteProject.setRequired(true);
        // txtInviteProjectNumber		
        this.txtInviteProjectNumber.setEnabled(false);
        // txtInviteProjectRespPerson		
        this.txtInviteProjectRespPerson.setEnabled(false);
        // txtCurProjectName		
        this.txtCurProjectName.setEnabled(false);
        // txtInviteTypeName		
        this.txtInviteTypeName.setEnabled(false);
        // cmbAttachmentList
        // txtRemark		
        this.txtRemark.setMaxLength(255);
        // enterScrollPanel
        // txtEnterCriterion		
        this.txtEnterCriterion.setMaxLength(255);		
        this.txtEnterCriterion.setRequired(true);
        // kDLabelContainer1
        // kDLabelContainer2
        // btnInviteExecuteInfo
        this.btnInviteExecuteInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionInviteExecuteInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInviteExecuteInfo.setText(resHelper.getString("btnInviteExecuteInfo.text"));		
        this.btnInviteExecuteInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_execute"));
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
        this.setBounds(new Rectangle(10, 10, 985, 520));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 985, 520));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(358, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(358, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contResponsibleDate.setBounds(new Rectangle(705, 10, 270, 19));
        this.add(contResponsibleDate, new KDLayout.Constraints(705, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRemark.setBounds(new Rectangle(10, 351, 270, 19));
        this.add(contRemark, new KDLayout.Constraints(10, 351, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contEnterCriterion.setBounds(new Rectangle(10, 175, 314, 19));
        this.add(contEnterCriterion, new KDLayout.Constraints(10, 175, 314, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contResponsiblePerson.setBounds(new Rectangle(358, 43, 270, 19));
        this.add(contResponsiblePerson, new KDLayout.Constraints(358, 43, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contResponsibleDept.setBounds(new Rectangle(10, 43, 270, 19));
        this.add(contResponsibleDept, new KDLayout.Constraints(10, 43, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteProject.setBounds(new Rectangle(10, 76, 270, 19));
        this.add(contInviteProject, new KDLayout.Constraints(10, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conInviteProjectName.setBounds(new Rectangle(358, 76, 270, 19));
        this.add(conInviteProjectName, new KDLayout.Constraints(358, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conInviteRespPerson.setBounds(new Rectangle(705, 76, 270, 19));
        this.add(conInviteRespPerson, new KDLayout.Constraints(705, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conCurProjectName.setBounds(new Rectangle(10, 109, 270, 19));
        this.add(conCurProjectName, new KDLayout.Constraints(10, 109, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conInviteTypeName.setBounds(new Rectangle(705, 43, 270, 19));
        this.add(conInviteTypeName, new KDLayout.Constraints(705, 43, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conAttachmentList.setBounds(new Rectangle(10, 142, 618, 19));
        this.add(conAttachmentList, new KDLayout.Constraints(10, 142, 618, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewAttachment.setBounds(new Rectangle(668, 142, 88, 21));
        this.add(btnViewAttachment, new KDLayout.Constraints(668, 142, 88, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        remarkScrollPanel.setBounds(new Rectangle(10, 375, 966, 135));
        this.add(remarkScrollPanel, new KDLayout.Constraints(10, 375, 966, 135, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        enterScrollPanel.setBounds(new Rectangle(10, 200, 966, 135));
        this.add(enterScrollPanel, new KDLayout.Constraints(10, 200, 966, 135, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(358, 109, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(358, 109, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(705, 109, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(705, 109, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contResponsibleDate
        contResponsibleDate.setBoundEditor(pkResponsibleDate);
        //contResponsiblePerson
        contResponsiblePerson.setBoundEditor(prmtResponsiblePerson);
        //contResponsibleDept
        contResponsibleDept.setBoundEditor(prmtResponsibleDept);
        //contInviteProject
        contInviteProject.setBoundEditor(prmtInviteProject);
        //conInviteProjectName
        conInviteProjectName.setBoundEditor(txtInviteProjectNumber);
        //conInviteRespPerson
        conInviteRespPerson.setBoundEditor(txtInviteProjectRespPerson);
        //conCurProjectName
        conCurProjectName.setBoundEditor(txtCurProjectName);
        //conInviteTypeName
        conInviteTypeName.setBoundEditor(txtInviteTypeName);
        //conAttachmentList
        conAttachmentList.setBoundEditor(cmbAttachmentList);
        //remarkScrollPanel
        remarkScrollPanel.getViewport().add(txtRemark, null);
        //enterScrollPanel
        enterScrollPanel.getViewport().add(txtEnterCriterion, null);

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
        this.toolBar.add(btnInviteExecuteInfo);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("responsibleDate", java.util.Date.class, this.pkResponsibleDate, "value");
		dataBinder.registerBinding("responsiblePerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtResponsiblePerson, "data");
		dataBinder.registerBinding("responsibleDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtResponsibleDept, "data");
		dataBinder.registerBinding("inviteProject", com.kingdee.eas.fdc.invite.InviteProjectInfo.class, this.prmtInviteProject, "data");
		dataBinder.registerBinding("inviteProject.number", String.class, this.txtInviteProjectNumber, "text");
		dataBinder.registerBinding("inviteProject.respPerson.name", String.class, this.txtInviteProjectRespPerson, "text");
		dataBinder.registerBinding("inviteProject.project.name", String.class, this.txtCurProjectName, "text");
		dataBinder.registerBinding("inviteProject.inviteType.name", String.class, this.txtInviteTypeName, "text");
		dataBinder.registerBinding("remark", String.class, this.txtRemark, "text");
		dataBinder.registerBinding("enterCriterion", String.class, this.txtEnterCriterion, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.InviteSupplierEnterBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.InviteSupplierEnterBillInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("responsibleDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("responsiblePerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("responsibleDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.respPerson.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.project.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.inviteType.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("enterCriterion", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("responsibleDate"));
        sic.add(new SelectorItemInfo("responsiblePerson.*"));
        sic.add(new SelectorItemInfo("responsibleDept.*"));
        sic.add(new SelectorItemInfo("inviteProject.*"));
        sic.add(new SelectorItemInfo("inviteProject.number"));
        sic.add(new SelectorItemInfo("inviteProject.respPerson.name"));
        sic.add(new SelectorItemInfo("inviteProject.project.name"));
        sic.add(new SelectorItemInfo("inviteProject.inviteType.name"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("enterCriterion"));
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
     * output actionViewAttachment_actionPerformed method
     */
    public void actionViewAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInviteExecuteInfo_actionPerformed method
     */
    public void actionInviteExecuteInfo_actionPerformed(ActionEvent e) throws Exception
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
            innerActionPerformed("eas", AbstractInviteSupplierEnterBillEditUI.this, "ActionViewAttachment", "actionViewAttachment_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractInviteSupplierEnterBillEditUI.this, "ActionInviteExecuteInfo", "actionInviteExecuteInfo_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "InviteSupplierEnterBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}