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
public abstract class AbstractInviteEntSuppChkBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInviteEntSuppChkBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHandlerDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHandlerPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conHandlerDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInviteProjectRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conCurProjectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conAttachmentList;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHandlerDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHandlerPerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkdHandlerDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteProjectRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCurProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteProjectNumber;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbAttachmentList;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInviteExecuteInfo;
    protected com.kingdee.eas.fdc.invite.InviteEntSuppChkBillInfo editData = null;
    protected ActionViewAttachment actionViewAttachment = null;
    protected ActionInviteExecuteInfo actionInviteExecuteInfo = null;
    /**
     * output class constructor
     */
    public AbstractInviteEntSuppChkBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInviteEntSuppChkBillEditUI.class.getName());
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
        this.contInviteProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHandlerDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHandlerPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conHandlerDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conInviteProjectRespPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conCurProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.conAttachmentList = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewAttachment = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtInviteProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtHandlerDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtHandlerPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkdHandlerDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtInviteProjectRespPerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInviteType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCurProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInviteProjectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.cmbAttachmentList = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.btnInviteExecuteInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contInviteProject.setName("contInviteProject");
        this.contHandlerDept.setName("contHandlerDept");
        this.contRemark.setName("contRemark");
        this.contHandlerPerson.setName("contHandlerPerson");
        this.conHandlerDate.setName("conHandlerDate");
        this.conInviteProjectRespPerson.setName("conInviteProjectRespPerson");
        this.conInviteType.setName("conInviteType");
        this.conCurProjectName.setName("conCurProjectName");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDContainer1.setName("kDContainer1");
        this.conAttachmentList.setName("conAttachmentList");
        this.btnViewAttachment.setName("btnViewAttachment");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.prmtInviteProject.setName("prmtInviteProject");
        this.prmtHandlerDept.setName("prmtHandlerDept");
        this.prmtHandlerPerson.setName("prmtHandlerPerson");
        this.pkdHandlerDate.setName("pkdHandlerDate");
        this.txtInviteProjectRespPerson.setName("txtInviteProjectRespPerson");
        this.txtInviteType.setName("txtInviteType");
        this.txtCurProject.setName("txtCurProject");
        this.txtInviteProjectNumber.setName("txtInviteProjectNumber");
        this.kdtEntry.setName("kdtEntry");
        this.cmbAttachmentList.setName("cmbAttachmentList");
        this.txtRemark.setName("txtRemark");
        this.btnInviteExecuteInfo.setName("btnInviteExecuteInfo");
        // CoreUI		
        this.btnCopy.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contInviteProject		
        this.contInviteProject.setBoundLabelText(resHelper.getString("contInviteProject.boundLabelText"));		
        this.contInviteProject.setBoundLabelLength(100);		
        this.contInviteProject.setBoundLabelUnderline(true);
        // contHandlerDept		
        this.contHandlerDept.setBoundLabelText(resHelper.getString("contHandlerDept.boundLabelText"));		
        this.contHandlerDept.setBoundLabelLength(100);		
        this.contHandlerDept.setBoundLabelUnderline(true);
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelLength(100);		
        this.contRemark.setBoundLabelUnderline(true);
        // contHandlerPerson		
        this.contHandlerPerson.setBoundLabelText(resHelper.getString("contHandlerPerson.boundLabelText"));		
        this.contHandlerPerson.setBoundLabelLength(100);		
        this.contHandlerPerson.setBoundLabelUnderline(true);
        // conHandlerDate		
        this.conHandlerDate.setBoundLabelText(resHelper.getString("conHandlerDate.boundLabelText"));		
        this.conHandlerDate.setBoundLabelLength(100);		
        this.conHandlerDate.setBoundLabelUnderline(true);
        // conInviteProjectRespPerson		
        this.conInviteProjectRespPerson.setBoundLabelText(resHelper.getString("conInviteProjectRespPerson.boundLabelText"));		
        this.conInviteProjectRespPerson.setBoundLabelUnderline(true);		
        this.conInviteProjectRespPerson.setBoundLabelLength(100);
        // conInviteType		
        this.conInviteType.setBoundLabelText(resHelper.getString("conInviteType.boundLabelText"));		
        this.conInviteType.setBoundLabelUnderline(true);		
        this.conInviteType.setBoundLabelLength(100);
        // conCurProjectName		
        this.conCurProjectName.setBoundLabelText(resHelper.getString("conCurProjectName.boundLabelText"));		
        this.conCurProjectName.setBoundLabelUnderline(true);		
        this.conCurProjectName.setBoundLabelLength(100);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelLength(100);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // conAttachmentList		
        this.conAttachmentList.setBoundLabelText(resHelper.getString("conAttachmentList.boundLabelText"));		
        this.conAttachmentList.setBoundLabelUnderline(true);		
        this.conAttachmentList.setBoundLabelLength(100);
        // btnViewAttachment
        this.btnViewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAttachment.setText(resHelper.getString("btnViewAttachment.text"));
        // kDLabelContainer1
        // kDLabelContainer2
        // kDScrollPane1
        // txtNumber		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setRequired(true);
        // prmtInviteProject		
        this.prmtInviteProject.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteProjectQuery");		
        this.prmtInviteProject.setDisplayFormat("$name$");		
        this.prmtInviteProject.setEditFormat("$number$");		
        this.prmtInviteProject.setCommitFormat("$number$");		
        this.prmtInviteProject.setRequired(true);
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
        // prmtHandlerDept		
        this.prmtHandlerDept.setQueryInfo("com.kingdee.eas.basedata.org.client.f7.AdminF7");		
        this.prmtHandlerDept.setCommitFormat("$number$");		
        this.prmtHandlerDept.setDisplayFormat("$name$");		
        this.prmtHandlerDept.setEditFormat("$number$");
        // prmtHandlerPerson		
        this.prmtHandlerPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtHandlerPerson.setDisplayFormat("$name$");		
        this.prmtHandlerPerson.setEditFormat("$number$");		
        this.prmtHandlerPerson.setCommitFormat("$number$");		
        this.prmtHandlerPerson.setRequired(true);
        // pkdHandlerDate
        // txtInviteProjectRespPerson		
        this.txtInviteProjectRespPerson.setEnabled(false);
        // txtInviteType		
        this.txtInviteType.setEnabled(false);
        // txtCurProject		
        this.txtCurProject.setEnabled(false);
        // txtInviteProjectNumber		
        this.txtInviteProjectNumber.setEnabled(false);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"supplier\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"0\" /><t:Column t:key=\"linkman\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"provider\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"siteInspection\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"sameProjct\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{supplier}</t:Cell><t:Cell>$Resource{linkman}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{provider}</t:Cell><t:Cell>$Resource{siteInspection}</t:Cell><t:Cell>$Resource{sameProjct}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

                this.kdtEntry.putBindContents("editData",new String[] {"supplier","linkman","phone","provider","siteInspection","sameProjct"});


        // cmbAttachmentList
        // txtRemark		
        this.txtRemark.setMaxLength(255);
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        contNumber.setBounds(new Rectangle(18, 12, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(18, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(377, 12, 270, 19));
        this.add(contName, new KDLayout.Constraints(377, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteProject.setBounds(new Rectangle(18, 70, 270, 19));
        this.add(contInviteProject, new KDLayout.Constraints(18, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHandlerDept.setBounds(new Rectangle(18, 41, 270, 19));
        this.add(contHandlerDept, new KDLayout.Constraints(18, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRemark.setBounds(new Rectangle(18, 161, 137, 19));
        this.add(contRemark, new KDLayout.Constraints(18, 161, 137, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contHandlerPerson.setBounds(new Rectangle(377, 41, 270, 19));
        this.add(contHandlerPerson, new KDLayout.Constraints(377, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conHandlerDate.setBounds(new Rectangle(728, 12, 270, 19));
        this.add(conHandlerDate, new KDLayout.Constraints(728, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conInviteProjectRespPerson.setBounds(new Rectangle(728, 70, 270, 19));
        this.add(conInviteProjectRespPerson, new KDLayout.Constraints(728, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conInviteType.setBounds(new Rectangle(728, 41, 270, 19));
        this.add(conInviteType, new KDLayout.Constraints(728, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conCurProjectName.setBounds(new Rectangle(18, 99, 270, 19));
        this.add(conCurProjectName, new KDLayout.Constraints(18, 99, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(377, 70, 270, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(377, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(18, 310, 984, 307));
        this.add(kDContainer1, new KDLayout.Constraints(18, 310, 984, 307, 0));
        conAttachmentList.setBounds(new Rectangle(18, 128, 526, 19));
        this.add(conAttachmentList, new KDLayout.Constraints(18, 128, 526, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewAttachment.setBounds(new Rectangle(574, 128, 89, 21));
        this.add(btnViewAttachment, new KDLayout.Constraints(574, 128, 89, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(377, 99, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(377, 99, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(728, 99, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(728, 99, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane1.setBounds(new Rectangle(18, 189, 984, 107));
        this.add(kDScrollPane1, new KDLayout.Constraints(18, 189, 984, 107, 0));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contInviteProject
        contInviteProject.setBoundEditor(prmtInviteProject);
        //contHandlerDept
        contHandlerDept.setBoundEditor(prmtHandlerDept);
        //contHandlerPerson
        contHandlerPerson.setBoundEditor(prmtHandlerPerson);
        //conHandlerDate
        conHandlerDate.setBoundEditor(pkdHandlerDate);
        //conInviteProjectRespPerson
        conInviteProjectRespPerson.setBoundEditor(txtInviteProjectRespPerson);
        //conInviteType
        conInviteType.setBoundEditor(txtInviteType);
        //conCurProjectName
        conCurProjectName.setBoundEditor(txtCurProject);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtInviteProjectNumber);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kdtEntry, BorderLayout.CENTER);
        //conAttachmentList
        conAttachmentList.setBoundEditor(cmbAttachmentList);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtRemark, null);

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
		dataBinder.registerBinding("inviteProject", com.kingdee.eas.fdc.invite.InviteProjectInfo.class, this.prmtInviteProject, "data");
		dataBinder.registerBinding("handlerDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtHandlerDept, "data");
		dataBinder.registerBinding("handlerPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtHandlerPerson, "data");
		dataBinder.registerBinding("handlerDate", java.util.Date.class, this.pkdHandlerDate, "value");
		dataBinder.registerBinding("inviteProject.respPerson.name", String.class, this.txtInviteProjectRespPerson, "text");
		dataBinder.registerBinding("inviteProject.inviteType.name", String.class, this.txtInviteType, "text");
		dataBinder.registerBinding("inviteProject.project.name", String.class, this.txtCurProject, "text");
		dataBinder.registerBinding("inviteProject.number", String.class, this.txtInviteProjectNumber, "text");
		dataBinder.registerBinding("entry.linkman", String.class, this.kdtEntry, "linkman.text");
		dataBinder.registerBinding("entry.phone", String.class, this.kdtEntry, "phone.text");
		dataBinder.registerBinding("entry.provider", String.class, this.kdtEntry, "provider.text");
		dataBinder.registerBinding("entry.siteInspection", String.class, this.kdtEntry, "siteInspection.text");
		dataBinder.registerBinding("entry.sameProjct", String.class, this.kdtEntry, "sameProjct.text");
		dataBinder.registerBinding("entry.supplier", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.kdtEntry, "supplier.text");
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.invite.InviteEntSuppChkBillEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("remark", String.class, this.txtRemark, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.InviteEntSuppChkBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.InviteEntSuppChkBillInfo)ov;
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
		getValidateHelper().registerBindProperty("inviteProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handlerDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handlerPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handlerDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.respPerson.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.inviteType.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.project.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.linkman", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.provider", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.siteInspection", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.sameProjct", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("inviteProject.*"));
        sic.add(new SelectorItemInfo("handlerDept.*"));
        sic.add(new SelectorItemInfo("handlerPerson.*"));
        sic.add(new SelectorItemInfo("handlerDate"));
        sic.add(new SelectorItemInfo("inviteProject.respPerson.name"));
        sic.add(new SelectorItemInfo("inviteProject.inviteType.name"));
        sic.add(new SelectorItemInfo("inviteProject.project.name"));
        sic.add(new SelectorItemInfo("inviteProject.number"));
    sic.add(new SelectorItemInfo("entry.linkman"));
    sic.add(new SelectorItemInfo("entry.phone"));
    sic.add(new SelectorItemInfo("entry.provider"));
    sic.add(new SelectorItemInfo("entry.siteInspection"));
    sic.add(new SelectorItemInfo("entry.sameProjct"));
        sic.add(new SelectorItemInfo("entry.supplier.*"));
//        sic.add(new SelectorItemInfo("entry.supplier.number"));
        sic.add(new SelectorItemInfo("entry.*"));
//        sic.add(new SelectorItemInfo("entry.number"));
        sic.add(new SelectorItemInfo("remark"));
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
            innerActionPerformed("eas", AbstractInviteEntSuppChkBillEditUI.this, "ActionViewAttachment", "actionViewAttachment_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractInviteEntSuppChkBillEditUI.this, "ActionInviteExecuteInfo", "actionInviteExecuteInfo_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "InviteEntSuppChkBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}