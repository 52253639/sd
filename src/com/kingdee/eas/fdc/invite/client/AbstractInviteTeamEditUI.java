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
public abstract class AbstractInviteTeamEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInviteTeamEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFileNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFileNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFileName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFileName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTxtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteProjectNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbAttachments;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeletePerson;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtInviteTeamPerson;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField kDTextField1;
    protected com.kingdee.eas.fdc.invite.InviteTeamInfo editData = null;
    protected ActionAddPerson actionAddPerson = null;
    protected ActionDeletePerson actionDeletePerson = null;
    /**
     * output class constructor
     */
    public AbstractInviteTeamEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInviteTeamEditUI.class.getName());
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
        //actionAddPerson
        this.actionAddPerson = new ActionAddPerson(this);
        getActionManager().registerAction("actionAddPerson", actionAddPerson);
         this.actionAddPerson.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeletePerson
        this.actionDeletePerson = new ActionDeletePerson(this);
        getActionManager().registerAction("actionDeletePerson", actionDeletePerson);
         this.actionDeletePerson.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contInviteProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtInviteProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtInviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contFileNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtFileNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contFileName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtFileName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contTxtNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contInviteProjectNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtInviteProjectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cmbAttachments = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnDeletePerson = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtInviteTeamPerson = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddPerson = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTextField1 = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contDescription.setName("contDescription");
        this.txtDescription.setName("txtDescription");
        this.contAuditor.setName("contAuditor");
        this.prmtAuditor.setName("prmtAuditor");
        this.contState.setName("contState");
        this.comboState.setName("comboState");
        this.contAuditTime.setName("contAuditTime");
        this.pkAuditTime.setName("pkAuditTime");
        this.contInviteProject.setName("contInviteProject");
        this.prmtInviteProject.setName("prmtInviteProject");
        this.contDept.setName("contDept");
        this.prmtDept.setName("prmtDept");
        this.contInviteType.setName("contInviteType");
        this.prmtInviteType.setName("prmtInviteType");
        this.contFileNumber.setName("contFileNumber");
        this.txtFileNumber.setName("txtFileNumber");
        this.contFileName.setName("contFileName");
        this.txtFileName.setName("txtFileName");
        this.pkCreateTime.setName("pkCreateTime");
        this.contTxtNumber.setName("contTxtNumber");
        this.txtNumber.setName("txtNumber");
        this.contInviteProjectNumber.setName("contInviteProjectNumber");
        this.contProject.setName("contProject");
        this.contRemark.setName("contRemark");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.txtInviteProjectNumber.setName("txtInviteProjectNumber");
        this.prmtProject.setName("prmtProject");
        this.cmbAttachments.setName("cmbAttachments");
        this.btnDeletePerson.setName("btnDeletePerson");
        this.txtRemark.setName("txtRemark");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDContainer1.setName("kDContainer1");
        this.kdtInviteTeamPerson.setName("kdtInviteTeamPerson");
        this.btnAddPerson.setName("btnAddPerson");
        this.kDTextField1.setName("kDTextField1");
        // CoreUI
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setVisible(false);
        // txtDescription		
        this.txtDescription.setMaxLength(1000);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);		
        this.contState.setVisible(false);
        // comboState		
        this.comboState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // contInviteProject		
        this.contInviteProject.setBoundLabelText(resHelper.getString("contInviteProject.boundLabelText"));		
        this.contInviteProject.setBoundLabelLength(100);		
        this.contInviteProject.setBoundLabelUnderline(true);
        // prmtInviteProject		
        this.prmtInviteProject.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteProjectQuery");		
        this.prmtInviteProject.setDisplayFormat("$name$");		
        this.prmtInviteProject.setEditFormat("$number$");		
        this.prmtInviteProject.setRequired(true);
        // contDept		
        this.contDept.setBoundLabelText(resHelper.getString("contDept.boundLabelText"));		
        this.contDept.setBoundLabelLength(100);		
        this.contDept.setBoundLabelUnderline(true);
        // prmtDept
        // contInviteType		
        this.contInviteType.setBoundLabelText(resHelper.getString("contInviteType.boundLabelText"));		
        this.contInviteType.setBoundLabelLength(100);		
        this.contInviteType.setBoundLabelUnderline(true);
        // prmtInviteType		
        this.prmtInviteType.setEnabled(false);
        // contFileNumber		
        this.contFileNumber.setBoundLabelText(resHelper.getString("contFileNumber.boundLabelText"));		
        this.contFileNumber.setBoundLabelLength(100);		
        this.contFileNumber.setBoundLabelUnderline(true);		
        this.contFileNumber.setVisible(false);
        // txtFileNumber		
        this.txtFileNumber.setMaxLength(40);
        // contFileName		
        this.contFileName.setBoundLabelText(resHelper.getString("contFileName.boundLabelText"));		
        this.contFileName.setBoundLabelLength(100);		
        this.contFileName.setBoundLabelUnderline(true);
        // txtFileName		
        this.txtFileName.setMaxLength(80);		
        this.txtFileName.setRequired(true);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // contTxtNumber		
        this.contTxtNumber.setBoundLabelText(resHelper.getString("contTxtNumber.boundLabelText"));		
        this.contTxtNumber.setBoundLabelLength(100);		
        this.contTxtNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // contInviteProjectNumber		
        this.contInviteProjectNumber.setBoundLabelText(resHelper.getString("contInviteProjectNumber.boundLabelText"));		
        this.contInviteProjectNumber.setBoundLabelLength(100);		
        this.contInviteProjectNumber.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelUnderline(true);		
        this.contProject.setBoundLabelLength(100);
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelUnderline(true);		
        this.contRemark.setBoundLabelLength(100);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelLength(100);
        // txtInviteProjectNumber		
        this.txtInviteProjectNumber.setEnabled(false);
        // prmtProject		
        this.prmtProject.setDisplayFormat("$name$");		
        this.prmtProject.setEditFormat("$number$");		
        this.prmtProject.setEnabled(false);
        // cmbAttachments
        this.cmbAttachments.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cmbAttachments_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnDeletePerson
        this.btnDeletePerson.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeletePerson, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeletePerson.setText(resHelper.getString("btnDeletePerson.text"));		
        this.btnDeletePerson.setSelectedIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));		
        this.btnDeletePerson.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        // txtRemark		
        this.txtRemark.setMaxLength(1000);
        // kDScrollPane1
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setTitleLength(130);
        // kdtInviteTeamPerson
		String kdtInviteTeamPersonStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"person\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"dept\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"deptParent\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"job\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{person}</t:Cell><t:Cell>$Resource{dept}</t:Cell><t:Cell>$Resource{deptParent}</t:Cell><t:Cell>$Resource{job}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtInviteTeamPerson.setFormatXml(resHelper.translateString("kdtInviteTeamPerson",kdtInviteTeamPersonStrXML));

                this.kdtInviteTeamPerson.putBindContents("editData",new String[] {"id","person","dept","deptParent","job"});


        // btnAddPerson
        this.btnAddPerson.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddPerson, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddPerson.setText(resHelper.getString("btnAddPerson.text"));		
        this.btnAddPerson.setSelectedIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));		
        this.btnAddPerson.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // kDTextField1
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
        contCreator.setBounds(new Rectangle(10, 570, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 570, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(10, 596, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(10, 596, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(366, 566, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(366, 566, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(712, 570, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(712, 570, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contState.setBounds(new Rectangle(367, 542, 270, 19));
        this.add(contState, new KDLayout.Constraints(367, 542, 270, 19, 0));
        contAuditTime.setBounds(new Rectangle(712, 592, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(712, 592, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInviteProject.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contInviteProject, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDept.setBounds(new Rectangle(367, 35, 270, 19));
        this.add(contDept, new KDLayout.Constraints(367, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteType.setBounds(new Rectangle(712, 35, 270, 19));
        this.add(contInviteType, new KDLayout.Constraints(712, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFileNumber.setBounds(new Rectangle(368, 587, 270, 19));
        this.add(contFileNumber, new KDLayout.Constraints(368, 587, 270, 19, 0));
        contFileName.setBounds(new Rectangle(10, 60, 973, 19));
        this.add(contFileName, new KDLayout.Constraints(10, 60, 973, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contTxtNumber.setBounds(new Rectangle(10, 35, 270, 19));
        this.add(contTxtNumber, new KDLayout.Constraints(10, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteProjectNumber.setBounds(new Rectangle(367, 12, 270, 19));
        this.add(contInviteProjectNumber, new KDLayout.Constraints(367, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProject.setBounds(new Rectangle(712, 10, 270, 19));
        this.add(contProject, new KDLayout.Constraints(712, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRemark.setBounds(new Rectangle(10, 85, 100, 24));
        this.add(contRemark, new KDLayout.Constraints(10, 85, 100, 24, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer4.setBounds(new Rectangle(10, 543, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(10, 543, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnDeletePerson.setBounds(new Rectangle(906, 206, 70, 19));
        this.add(btnDeletePerson, new KDLayout.Constraints(906, 206, 70, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane1.setBounds(new Rectangle(9, 115, 974, 86));
        this.add(kDScrollPane1, new KDLayout.Constraints(9, 115, 974, 86, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer1.setBounds(new Rectangle(10, 207, 964, 320));
        this.add(kDContainer1, new KDLayout.Constraints(10, 207, 964, 320, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnAddPerson.setBounds(new Rectangle(826, 206, 70, 19));
        this.add(btnAddPerson, new KDLayout.Constraints(826, 206, 70, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contState
        contState.setBoundEditor(comboState);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contInviteProject
        contInviteProject.setBoundEditor(prmtInviteProject);
        //contDept
        contDept.setBoundEditor(prmtDept);
        //contInviteType
        contInviteType.setBoundEditor(prmtInviteType);
        //contFileNumber
        contFileNumber.setBoundEditor(txtFileNumber);
        //contFileName
        contFileName.setBoundEditor(txtFileName);
        //contTxtNumber
        contTxtNumber.setBoundEditor(txtNumber);
        //contInviteProjectNumber
        contInviteProjectNumber.setBoundEditor(txtInviteProjectNumber);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contRemark
        contRemark.setBoundEditor(kDTextField1);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(cmbAttachments);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtRemark, null);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kdtInviteTeamPerson, BorderLayout.CENTER);

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
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.comboState, "selectedItem");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("inviteProject", com.kingdee.eas.fdc.invite.InviteProjectInfo.class, this.prmtInviteProject, "data");
		dataBinder.registerBinding("dept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtDept, "data");
		dataBinder.registerBinding("inviteProject.inviteType", com.kingdee.eas.fdc.invite.InviteTypeInfo.class, this.prmtInviteType, "data");
		dataBinder.registerBinding("fileNumber", String.class, this.txtFileNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtFileName, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("inviteProject.number", String.class, this.txtInviteProjectNumber, "text");
		dataBinder.registerBinding("inviteProject.project", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("remark", String.class, this.txtRemark, "text");
		dataBinder.registerBinding("inviteTeamPerson.job", String.class, this.kdtInviteTeamPerson, "job.text");
		dataBinder.registerBinding("inviteTeamPerson", com.kingdee.eas.fdc.invite.InviteTeamPersonInfo.class, this.kdtInviteTeamPerson, "userObject");
		dataBinder.registerBinding("inviteTeamPerson.person", com.kingdee.eas.basedata.person.PersonInfo.class, this.kdtInviteTeamPerson, "person.text");
		dataBinder.registerBinding("inviteTeamPerson.dept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.kdtInviteTeamPerson, "dept.text");
		dataBinder.registerBinding("inviteTeamPerson.deptParent", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.kdtInviteTeamPerson, "deptParent.text");
		dataBinder.registerBinding("inviteTeamPerson.id", com.kingdee.bos.util.BOSUuid.class, this.kdtInviteTeamPerson, "id.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.InviteTeamEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.InviteTeamInfo)ov;
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
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.inviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fileNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteTeamPerson.job", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteTeamPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteTeamPerson.person", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteTeamPerson.dept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteTeamPerson.deptParent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteTeamPerson.id", ValidateHelper.ON_SAVE);    		
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
     * output cmbAttachments_itemStateChanged method
     */
    protected void cmbAttachments_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("inviteProject.*"));
        sic.add(new SelectorItemInfo("dept.*"));
        sic.add(new SelectorItemInfo("inviteProject.inviteType"));
        sic.add(new SelectorItemInfo("fileNumber"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("inviteProject.number"));
        sic.add(new SelectorItemInfo("inviteProject.project"));
        sic.add(new SelectorItemInfo("remark"));
    sic.add(new SelectorItemInfo("inviteTeamPerson.job"));
        sic.add(new SelectorItemInfo("inviteTeamPerson.*"));
//        sic.add(new SelectorItemInfo("inviteTeamPerson.number"));
        sic.add(new SelectorItemInfo("inviteTeamPerson.person.*"));
//        sic.add(new SelectorItemInfo("inviteTeamPerson.person.number"));
        sic.add(new SelectorItemInfo("inviteTeamPerson.dept.*"));
//        sic.add(new SelectorItemInfo("inviteTeamPerson.dept.number"));
        sic.add(new SelectorItemInfo("inviteTeamPerson.deptParent.*"));
//        sic.add(new SelectorItemInfo("inviteTeamPerson.deptParent.number"));
    sic.add(new SelectorItemInfo("inviteTeamPerson.id"));
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
     * output actionAddPerson_actionPerformed method
     */
    public void actionAddPerson_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeletePerson_actionPerformed method
     */
    public void actionDeletePerson_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionAddPerson(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddPerson() {
    	return false;
    }
	public RequestContext prepareActionDeletePerson(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeletePerson() {
    	return false;
    }

    /**
     * output ActionAddPerson class
     */     
    protected class ActionAddPerson extends ItemAction {     
    
        public ActionAddPerson()
        {
            this(null);
        }

        public ActionAddPerson(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddPerson.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddPerson.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddPerson.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteTeamEditUI.this, "ActionAddPerson", "actionAddPerson_actionPerformed", e);
        }
    }

    /**
     * output ActionDeletePerson class
     */     
    protected class ActionDeletePerson extends ItemAction {     
    
        public ActionDeletePerson()
        {
            this(null);
        }

        public ActionDeletePerson(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDeletePerson.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeletePerson.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeletePerson.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteTeamEditUI.this, "ActionDeletePerson", "actionDeletePerson_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "InviteTeamEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}