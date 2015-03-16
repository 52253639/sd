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
public abstract class AbstractSHESellProjectEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSHESellProjectEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdpBase;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdpShareOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdpShareRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSimpleName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTermBegin;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTermEnd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompanyOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectBase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkTermBegin;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkTermEnd;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCompanyOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProjectBase;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEndDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelRoom;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable2;
    protected com.kingdee.eas.fdc.sellhouse.SellProjectInfo editData = null;
    protected ActionBtnAdd actionBtnAdd = null;
    protected ActionBtnDel actionBtnDel = null;
    protected ActionBtnAddRoom actionBtnAddRoom = null;
    protected ActionBtnDelRoom actionBtnDelRoom = null;
    /**
     * output class constructor
     */
    public AbstractSHESellProjectEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSHESellProjectEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionBtnAdd
        this.actionBtnAdd = new ActionBtnAdd(this);
        getActionManager().registerAction("actionBtnAdd", actionBtnAdd);
         this.actionBtnAdd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBtnDel
        this.actionBtnDel = new ActionBtnDel(this);
        getActionManager().registerAction("actionBtnDel", actionBtnDel);
         this.actionBtnDel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBtnAddRoom
        this.actionBtnAddRoom = new ActionBtnAddRoom(this);
        getActionManager().registerAction("actionBtnAddRoom", actionBtnAddRoom);
         this.actionBtnAddRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBtnDelRoom
        this.actionBtnDelRoom = new ActionBtnDelRoom(this);
        getActionManager().registerAction("actionBtnDelRoom", actionBtnDelRoom);
         this.actionBtnDelRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kdpBase = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdpShareOrgUnit = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdpShareRoom = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSimpleName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTermBegin = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTermEnd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompanyOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectBase = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkTermBegin = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkTermEnd = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtCompanyOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProjectBase = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnAdd = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTable2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kdpBase.setName("kdpBase");
        this.kdpShareOrgUnit.setName("kdpShareOrgUnit");
        this.kdpShareRoom.setName("kdpShareRoom");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contSimpleName.setName("contSimpleName");
        this.contProject.setName("contProject");
        this.contTermBegin.setName("contTermBegin");
        this.contTermEnd.setName("contTermEnd");
        this.contDescription.setName("contDescription");
        this.contCompanyOrgUnit.setName("contCompanyOrgUnit");
        this.contProjectBase.setName("contProjectBase");
        this.contEndDate.setName("contEndDate");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.txtSimpleName.setName("txtSimpleName");
        this.prmtProject.setName("prmtProject");
        this.pkTermBegin.setName("pkTermBegin");
        this.pkTermEnd.setName("pkTermEnd");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.prmtCompanyOrgUnit.setName("prmtCompanyOrgUnit");
        this.prmtProjectBase.setName("prmtProjectBase");
        this.pkEndDate.setName("pkEndDate");
        this.btnAdd.setName("btnAdd");
        this.btnDel.setName("btnDel");
        this.kDTable1.setName("kDTable1");
        this.btnAddRoom.setName("btnAddRoom");
        this.btnDelRoom.setName("btnDelRoom");
        this.kDTable2.setName("kDTable2");
        // CoreUI
        // kDTabbedPane1
        // kdpBase
        // kdpShareOrgUnit
        // kdpShareRoom
        // contNumber		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contSimpleName		
        this.contSimpleName.setBoundLabelText(resHelper.getString("contSimpleName.boundLabelText"));		
        this.contSimpleName.setBoundLabelLength(100);		
        this.contSimpleName.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);		
        this.contProject.setEnabled(false);		
        this.contProject.setVisible(false);
        // contTermBegin		
        this.contTermBegin.setBoundLabelText(resHelper.getString("contTermBegin.boundLabelText"));		
        this.contTermBegin.setBoundLabelLength(100);		
        this.contTermBegin.setBoundLabelUnderline(true);
        // contTermEnd		
        this.contTermEnd.setBoundLabelText(resHelper.getString("contTermEnd.boundLabelText"));		
        this.contTermEnd.setBoundLabelLength(100);		
        this.contTermEnd.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contCompanyOrgUnit		
        this.contCompanyOrgUnit.setBoundLabelText(resHelper.getString("contCompanyOrgUnit.boundLabelText"));		
        this.contCompanyOrgUnit.setBoundLabelLength(100);		
        this.contCompanyOrgUnit.setBoundLabelUnderline(true);
        // contProjectBase		
        this.contProjectBase.setBoundLabelText(resHelper.getString("contProjectBase.boundLabelText"));		
        this.contProjectBase.setBoundLabelLength(100);		
        this.contProjectBase.setBoundLabelUnderline(true);		
        this.contProjectBase.setBackground(new java.awt.Color(255,0,0));		
        this.contProjectBase.setForeground(new java.awt.Color(255,0,0));
        // contEndDate		
        this.contEndDate.setBoundLabelText(resHelper.getString("contEndDate.boundLabelText"));		
        this.contEndDate.setBoundLabelLength(100);		
        this.contEndDate.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(80);
        // txtSimpleName		
        this.txtSimpleName.setMaxLength(80);
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtProject.setEnabled(false);		
        this.prmtProject.setVisible(false);
        // pkTermBegin
        // pkTermEnd
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // prmtCompanyOrgUnit		
        this.prmtCompanyOrgUnit.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FullOrgUnitQuery");
        // prmtProjectBase		
        this.prmtProjectBase.setDisplayFormat("$number$ $name$");		
        this.prmtProjectBase.setEditFormat("$number$");		
        this.prmtProjectBase.setCommitFormat("$number$");		
        this.prmtProjectBase.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7ProjectBaseQuery");		
        this.prmtProjectBase.setBackground(new java.awt.Color(255,255,255));		
        this.prmtProjectBase.setForeground(new java.awt.Color(255,0,0));
        // pkEndDate
        // btnAdd
        this.btnAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionBtnAdd, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAdd.setText(resHelper.getString("btnAdd.text"));
        // btnDel
        this.btnDel.setAction((IItemAction)ActionProxyFactory.getProxy(actionBtnDel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDel.setText(resHelper.getString("btnDel.text"));
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"shareId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"shareOrgUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shareOperationer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shareDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shareOpOrgUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{shareId}</t:Cell><t:Cell>$Resource{shareOrgUnit}</t:Cell><t:Cell>$Resource{shareOperationer}</t:Cell><t:Cell>$Resource{shareDate}</t:Cell><t:Cell>$Resource{shareOpOrgUnit}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));

        

        // btnAddRoom
        this.btnAddRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionBtnAddRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRoom.setText(resHelper.getString("btnAddRoom.text"));
        // btnDelRoom
        this.btnDelRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionBtnDelRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelRoom.setText(resHelper.getString("btnDelRoom.text"));
        // kDTable2
		String kDTable2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"roomNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomDesc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{roomNumber}</t:Cell><t:Cell>$Resource{roomName}</t:Cell><t:Cell>$Resource{roomType}</t:Cell><t:Cell>$Resource{roomDesc}</t:Cell><t:Cell>$Resource{roomId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable2.setFormatXml(resHelper.translateString("kDTable2",kDTable2StrXML));
        this.kDTable2.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kDTable2_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

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
        this.setBounds(new Rectangle(10, 10, 680, 450));
        this.setLayout(null);
        kDTabbedPane1.setBounds(new Rectangle(25, 22, 626, 388));
        this.add(kDTabbedPane1, null);
        //kDTabbedPane1
        kDTabbedPane1.add(kdpBase, resHelper.getString("kdpBase.constraints"));
        kDTabbedPane1.add(kdpShareOrgUnit, resHelper.getString("kdpShareOrgUnit.constraints"));
        kDTabbedPane1.add(kdpShareRoom, resHelper.getString("kdpShareRoom.constraints"));
        //kdpBase
        kdpBase.setLayout(new KDLayout());
        kdpBase.putClientProperty("OriginalBounds", new Rectangle(0, 0, 625, 355));        contNumber.setBounds(new Rectangle(31, 36, 270, 19));
        kdpBase.add(contNumber, new KDLayout.Constraints(31, 36, 270, 19, 0));
        contName.setBounds(new Rectangle(337, 36, 270, 19));
        kdpBase.add(contName, new KDLayout.Constraints(337, 36, 270, 19, 0));
        contSimpleName.setBounds(new Rectangle(31, 73, 270, 19));
        kdpBase.add(contSimpleName, new KDLayout.Constraints(31, 73, 270, 19, 0));
        contProject.setBounds(new Rectangle(337, 297, 270, 19));
        kdpBase.add(contProject, new KDLayout.Constraints(337, 297, 270, 19, 0));
        contTermBegin.setBounds(new Rectangle(31, 110, 270, 19));
        kdpBase.add(contTermBegin, new KDLayout.Constraints(31, 110, 270, 19, 0));
        contTermEnd.setBounds(new Rectangle(337, 110, 270, 19));
        kdpBase.add(contTermEnd, new KDLayout.Constraints(337, 110, 270, 19, 0));
        contDescription.setBounds(new Rectangle(31, 184, 576, 92));
        kdpBase.add(contDescription, new KDLayout.Constraints(31, 184, 576, 92, 0));
        contCompanyOrgUnit.setBounds(new Rectangle(337, 73, 270, 19));
        kdpBase.add(contCompanyOrgUnit, new KDLayout.Constraints(337, 73, 270, 19, 0));
        contProjectBase.setBounds(new Rectangle(31, 297, 270, 19));
        kdpBase.add(contProjectBase, new KDLayout.Constraints(31, 297, 270, 19, 0));
        contEndDate.setBounds(new Rectangle(31, 147, 270, 19));
        kdpBase.add(contEndDate, new KDLayout.Constraints(31, 147, 270, 19, 0));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contSimpleName
        contSimpleName.setBoundEditor(txtSimpleName);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contTermBegin
        contTermBegin.setBoundEditor(pkTermBegin);
        //contTermEnd
        contTermEnd.setBoundEditor(pkTermEnd);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contCompanyOrgUnit
        contCompanyOrgUnit.setBoundEditor(prmtCompanyOrgUnit);
        //contProjectBase
        contProjectBase.setBoundEditor(prmtProjectBase);
        //contEndDate
        contEndDate.setBoundEditor(pkEndDate);
        //kdpShareOrgUnit
        kdpShareOrgUnit.setLayout(new KDLayout());
        kdpShareOrgUnit.putClientProperty("OriginalBounds", new Rectangle(0, 0, 625, 355));        btnAdd.setBounds(new Rectangle(418, 23, 90, 20));
        kdpShareOrgUnit.add(btnAdd, new KDLayout.Constraints(418, 23, 90, 20, 0));
        btnDel.setBounds(new Rectangle(519, 23, 83, 20));
        kdpShareOrgUnit.add(btnDel, new KDLayout.Constraints(519, 23, 83, 20, 0));
        kDTable1.setBounds(new Rectangle(10, 47, 597, 300));
        kdpShareOrgUnit.add(kDTable1, new KDLayout.Constraints(10, 47, 597, 300, 0));
        //kdpShareRoom
        kdpShareRoom.setLayout(new KDLayout());
        kdpShareRoom.putClientProperty("OriginalBounds", new Rectangle(0, 0, 625, 355));        btnAddRoom.setBounds(new Rectangle(370, 24, 96, 20));
        kdpShareRoom.add(btnAddRoom, new KDLayout.Constraints(370, 24, 96, 20, 0));
        btnDelRoom.setBounds(new Rectangle(500, 24, 96, 20));
        kdpShareRoom.add(btnDelRoom, new KDLayout.Constraints(500, 24, 96, 20, 0));
        kDTable2.setBounds(new Rectangle(5, 55, 607, 291));
        kdpShareRoom.add(kDTable2, new KDLayout.Constraints(5, 55, 607, 291, 0));

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
        this.menuBar.add(menuTool);
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
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("termBegin", java.util.Date.class, this.pkTermBegin, "value");
		dataBinder.registerBinding("termEnd", java.util.Date.class, this.pkTermEnd, "value");
		dataBinder.registerBinding("companyOrgUnit", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtCompanyOrgUnit, "data");
		dataBinder.registerBinding("projectBase", com.kingdee.eas.fdc.basedata.ProjectBaseInfo.class, this.prmtProjectBase, "data");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.pkEndDate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SHESellProjectEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)ov;
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
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("termBegin", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("termEnd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("companyOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectBase", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    		
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
        }
    }

    /**
     * output kDTable2_tableClicked method
     */
    protected void kDTable2_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("termBegin"));
        sic.add(new SelectorItemInfo("termEnd"));
        sic.add(new SelectorItemInfo("companyOrgUnit.*"));
        sic.add(new SelectorItemInfo("projectBase.*"));
        sic.add(new SelectorItemInfo("endDate"));
        return sic;
    }        
    	

    /**
     * output actionBtnAdd_actionPerformed method
     */
    public void actionBtnAdd_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBtnDel_actionPerformed method
     */
    public void actionBtnDel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBtnAddRoom_actionPerformed method
     */
    public void actionBtnAddRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBtnDelRoom_actionPerformed method
     */
    public void actionBtnDelRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionBtnAdd(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBtnAdd() {
    	return false;
    }
	public RequestContext prepareActionBtnDel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBtnDel() {
    	return false;
    }
	public RequestContext prepareActionBtnAddRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBtnAddRoom() {
    	return false;
    }
	public RequestContext prepareActionBtnDelRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBtnDelRoom() {
    	return false;
    }

    /**
     * output ActionBtnAdd class
     */     
    protected class ActionBtnAdd extends ItemAction {     
    
        public ActionBtnAdd()
        {
            this(null);
        }

        public ActionBtnAdd(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBtnAdd.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnAdd.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnAdd.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHESellProjectEditUI.this, "ActionBtnAdd", "actionBtnAdd_actionPerformed", e);
        }
    }

    /**
     * output ActionBtnDel class
     */     
    protected class ActionBtnDel extends ItemAction {     
    
        public ActionBtnDel()
        {
            this(null);
        }

        public ActionBtnDel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBtnDel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnDel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnDel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHESellProjectEditUI.this, "ActionBtnDel", "actionBtnDel_actionPerformed", e);
        }
    }

    /**
     * output ActionBtnAddRoom class
     */     
    protected class ActionBtnAddRoom extends ItemAction {     
    
        public ActionBtnAddRoom()
        {
            this(null);
        }

        public ActionBtnAddRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBtnAddRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnAddRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnAddRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHESellProjectEditUI.this, "ActionBtnAddRoom", "actionBtnAddRoom_actionPerformed", e);
        }
    }

    /**
     * output ActionBtnDelRoom class
     */     
    protected class ActionBtnDelRoom extends ItemAction {     
    
        public ActionBtnDelRoom()
        {
            this(null);
        }

        public ActionBtnDelRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBtnDelRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnDelRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnDelRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHESellProjectEditUI.this, "ActionBtnDelRoom", "actionBtnDelRoom_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SHESellProjectEditUI");
    }




}