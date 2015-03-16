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
public abstract class AbstractSHEProjectEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSHEProjectEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdpBase;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdpOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdpRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSinpleName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prtProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpEndDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prtOrgUnit;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelRoom;
    protected com.kingdee.eas.fdc.sellhouse.SHEProjectInfo editData = null;
    protected ActionBtnAdd actionBtnAdd = null;
    protected ActionBtnDelete actionBtnDelete = null;
    protected ActionAddRoom ationAddRoom = null;
    protected ActionDeleteRoom actionDeleteRoom = null;
    /**
     * output class constructor
     */
    public AbstractSHEProjectEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSHEProjectEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionBtnAdd
        this.actionBtnAdd = new ActionBtnAdd(this);
        getActionManager().registerAction("actionBtnAdd", actionBtnAdd);
         this.actionBtnAdd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBtnDelete
        this.actionBtnDelete = new ActionBtnDelete(this);
        getActionManager().registerAction("actionBtnDelete", actionBtnDelete);
         this.actionBtnDelete.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //ationAddRoom
        this.ationAddRoom = new ActionAddRoom(this);
        getActionManager().registerAction("ationAddRoom", ationAddRoom);
         this.ationAddRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteRoom
        this.actionDeleteRoom = new ActionDeleteRoom(this);
        getActionManager().registerAction("actionDeleteRoom", actionDeleteRoom);
         this.actionDeleteRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kdpBase = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdpOrgUnit = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdpRoomModel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSinpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdpStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdpEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAdd = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTable2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kdpBase.setName("kdpBase");
        this.kdpOrgUnit.setName("kdpOrgUnit");
        this.kdpRoomModel.setName("kdpRoomModel");
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.txtDescription.setName("txtDescription");
        this.txtSinpleName.setName("txtSinpleName");
        this.prtProject.setName("prtProject");
        this.kdpStartDate.setName("kdpStartDate");
        this.kdpEndDate.setName("kdpEndDate");
        this.prtOrgUnit.setName("prtOrgUnit");
        this.kDTable1.setName("kDTable1");
        this.btnAdd.setName("btnAdd");
        this.btnDel.setName("btnDel");
        this.kDTable2.setName("kDTable2");
        this.btnAddRoom.setName("btnAddRoom");
        this.btnDelRoom.setName("btnDelRoom");
        // CoreUI
        // kDTabbedPane1
        // kdpBase
        // kdpOrgUnit
        // kdpRoomModel
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(80);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // txtDescription		
        this.txtDescription.setMaxLength(250);
        // txtSinpleName		
        this.txtSinpleName.setMaxLength(80);
        // prtProject		
        this.prtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
        // kdpStartDate
        // kdpEndDate
        // prtOrgUnit		
        this.prtOrgUnit.setEnabled(false);		
        this.prtOrgUnit.setRequired(true);		
        this.prtOrgUnit.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FullOrgUnitQuery");
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"shareId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"shareOrgUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shareOperationer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shareDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shareOpOrgUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{shareId}</t:Cell><t:Cell>$Resource{shareOrgUnit}</t:Cell><t:Cell>$Resource{shareOperationer}</t:Cell><t:Cell>$Resource{shareDate}</t:Cell><t:Cell>$Resource{shareOpOrgUnit}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));

        

        // btnAdd
        this.btnAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionBtnAdd, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAdd.setText(resHelper.getString("btnAdd.text"));
        // btnDel
        this.btnDel.setAction((IItemAction)ActionProxyFactory.getProxy(actionBtnDelete, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDel.setText(resHelper.getString("btnDel.text"));
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

        

        // btnAddRoom
        this.btnAddRoom.setAction((IItemAction)ActionProxyFactory.getProxy(ationAddRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRoom.setText(resHelper.getString("btnAddRoom.text"));
        // btnDelRoom
        this.btnDelRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelRoom.setText(resHelper.getString("btnDelRoom.text"));
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
        kDTabbedPane1.add(kdpOrgUnit, resHelper.getString("kdpOrgUnit.constraints"));
        kDTabbedPane1.add(kdpRoomModel, resHelper.getString("kdpRoomModel.constraints"));
        //kdpBase
        kdpBase.setLayout(new KDLayout());
        kdpBase.putClientProperty("OriginalBounds", new Rectangle(0, 0, 625, 355));        contName.setBounds(new Rectangle(330, 34, 270, 19));
        kdpBase.add(contName, new KDLayout.Constraints(330, 34, 270, 19, 0));
        contNumber.setBounds(new Rectangle(15, 34, 270, 19));
        kdpBase.add(contNumber, new KDLayout.Constraints(15, 34, 270, 19, 0));
        contDescription.setBounds(new Rectangle(15, 243, 586, 90));
        kdpBase.add(contDescription, new KDLayout.Constraints(15, 243, 586, 90, 0));
        kDLabelContainer1.setBounds(new Rectangle(15, 88, 270, 19));
        kdpBase.add(kDLabelContainer1, new KDLayout.Constraints(15, 88, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(330, 88, 270, 19));
        kdpBase.add(kDLabelContainer2, new KDLayout.Constraints(330, 88, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(15, 139, 270, 19));
        kdpBase.add(kDLabelContainer3, new KDLayout.Constraints(15, 139, 270, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(330, 139, 270, 19));
        kdpBase.add(kDLabelContainer4, new KDLayout.Constraints(330, 139, 270, 19, 0));
        kDLabelContainer5.setBounds(new Rectangle(15, 193, 270, 19));
        kdpBase.add(kDLabelContainer5, new KDLayout.Constraints(15, 193, 270, 19, 0));
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtSinpleName);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prtProject);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(kdpStartDate);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(kdpEndDate);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(prtOrgUnit);
        //kdpOrgUnit
        kdpOrgUnit.setLayout(new KDLayout());
        kdpOrgUnit.putClientProperty("OriginalBounds", new Rectangle(0, 0, 625, 355));        kDTable1.setBounds(new Rectangle(10, 47, 597, 300));
        kdpOrgUnit.add(kDTable1, new KDLayout.Constraints(10, 47, 597, 300, 0));
        btnAdd.setBounds(new Rectangle(418, 23, 90, 20));
        kdpOrgUnit.add(btnAdd, new KDLayout.Constraints(418, 23, 90, 20, 0));
        btnDel.setBounds(new Rectangle(519, 23, 83, 20));
        kdpOrgUnit.add(btnDel, new KDLayout.Constraints(519, 23, 83, 20, 0));
        //kdpRoomModel
        kdpRoomModel.setLayout(new KDLayout());
        kdpRoomModel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 625, 355));        kDTable2.setBounds(new Rectangle(5, 55, 607, 291));
        kdpRoomModel.add(kDTable2, new KDLayout.Constraints(5, 55, 607, 291, 0));
        btnAddRoom.setBounds(new Rectangle(370, 24, 96, 20));
        kdpRoomModel.add(btnAddRoom, new KDLayout.Constraints(370, 24, 96, 20, 0));
        btnDelRoom.setBounds(new Rectangle(500, 24, 96, 20));
        kdpRoomModel.add(btnDelRoom, new KDLayout.Constraints(500, 24, 96, 20, 0));

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
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("simpleName", String.class, this.txtSinpleName, "text");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prtProject, "data");
		dataBinder.registerBinding("startDate", java.util.Date.class, this.kdpStartDate, "value");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.kdpEndDate, "value");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prtOrgUnit, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SHEProjectEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.SHEProjectInfo)ov;
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
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("startDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        return sic;
    }        
    	

    /**
     * output actionBtnAdd_actionPerformed method
     */
    public void actionBtnAdd_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBtnDelete_actionPerformed method
     */
    public void actionBtnDelete_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddRoom_actionPerformed method
     */
    public void actionAddRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteRoom_actionPerformed method
     */
    public void actionDeleteRoom_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionBtnDelete(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBtnDelete() {
    	return false;
    }
	public RequestContext prepareActionAddRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddRoom() {
    	return false;
    }
	public RequestContext prepareActionDeleteRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeleteRoom() {
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
            innerActionPerformed("eas", AbstractSHEProjectEditUI.this, "ActionBtnAdd", "actionBtnAdd_actionPerformed", e);
        }
    }

    /**
     * output ActionBtnDelete class
     */     
    protected class ActionBtnDelete extends ItemAction {     
    
        public ActionBtnDelete()
        {
            this(null);
        }

        public ActionBtnDelete(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBtnDelete.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnDelete.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBtnDelete.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHEProjectEditUI.this, "ActionBtnDelete", "actionBtnDelete_actionPerformed", e);
        }
    }

    /**
     * output ActionAddRoom class
     */     
    protected class ActionAddRoom extends ItemAction {     
    
        public ActionAddRoom()
        {
            this(null);
        }

        public ActionAddRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHEProjectEditUI.this, "ActionAddRoom", "actionAddRoom_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteRoom class
     */     
    protected class ActionDeleteRoom extends ItemAction {     
    
        public ActionDeleteRoom()
        {
            this(null);
        }

        public ActionDeleteRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDeleteRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSHEProjectEditUI.this, "ActionDeleteRoom", "actionDeleteRoom_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SHEProjectEditUI");
    }




}