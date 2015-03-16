/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractRoomPropertyEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomPropertyEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtRoomProepertyEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conPropertyObjectType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomResPropertyObject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomResBuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomResBuildingFloor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomResRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDTimePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox propertyObjectComboBox;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomResPropertyObject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomResBuilding;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomResBuildingFloor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomResRoom;
    protected com.kingdee.eas.fdc.tenancy.RoomPropertyMainInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRoomPropertyEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomPropertyEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtRoomProepertyEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.conPropertyObjectType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomResPropertyObject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomResBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomResBuildingFloor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomResRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDTimePicker();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.propertyObjectComboBox = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7RoomResPropertyObject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7RoomResBuilding = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7RoomResBuildingFloor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7RoomResRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.kdtRoomProepertyEntry.setName("kdtRoomProepertyEntry");
        this.conPropertyObjectType.setName("conPropertyObjectType");
        this.contRoomResPropertyObject.setName("contRoomResPropertyObject");
        this.contRoomResBuilding.setName("contRoomResBuilding");
        this.contRoomResBuildingFloor.setName("contRoomResBuildingFloor");
        this.contRoomResRoom.setName("contRoomResRoom");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.propertyObjectComboBox.setName("propertyObjectComboBox");
        this.f7RoomResPropertyObject.setName("f7RoomResPropertyObject");
        this.f7RoomResBuilding.setName("f7RoomResBuilding");
        this.f7RoomResBuildingFloor.setName("f7RoomResBuildingFloor");
        this.f7RoomResRoom.setName("f7RoomResRoom");
        // CoreUI
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // kdtRoomProepertyEntry
		String kdtRoomProepertyEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"roomResName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomResProperty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"address\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"area\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"propertyOwner\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"property\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"changeReason\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"propertyNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"certifiedTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"informationnote\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark2\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark3\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{roomResName}</t:Cell><t:Cell>$Resource{roomResProperty}</t:Cell><t:Cell>$Resource{address}</t:Cell><t:Cell>$Resource{area}</t:Cell><t:Cell>$Resource{propertyOwner}</t:Cell><t:Cell>$Resource{property}</t:Cell><t:Cell>$Resource{changeReason}</t:Cell><t:Cell>$Resource{propertyNumber}</t:Cell><t:Cell>$Resource{certifiedTime}</t:Cell><t:Cell>$Resource{informationnote}</t:Cell><t:Cell>$Resource{remark1}</t:Cell><t:Cell>$Resource{remark2}</t:Cell><t:Cell>$Resource{remark3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtRoomProepertyEntry.setFormatXml(resHelper.translateString("kdtRoomProepertyEntry",kdtRoomProepertyEntryStrXML));

                this.kdtRoomProepertyEntry.putBindContents("editData",new String[] {"roomResName","roomResProperty","address","area","propertyOwner","property","changeReason","propertyNumber","certifiedTime","informationnote","remark1","remark2","remark3"});


        // conPropertyObjectType		
        this.conPropertyObjectType.setBoundLabelText(resHelper.getString("conPropertyObjectType.boundLabelText"));		
        this.conPropertyObjectType.setBoundLabelLength(100);		
        this.conPropertyObjectType.setBoundLabelUnderline(true);
        // contRoomResPropertyObject		
        this.contRoomResPropertyObject.setBoundLabelText(resHelper.getString("contRoomResPropertyObject.boundLabelText"));		
        this.contRoomResPropertyObject.setBoundLabelLength(100);		
        this.contRoomResPropertyObject.setBoundLabelUnderline(true);		
        this.contRoomResPropertyObject.setVisible(false);		
        this.contRoomResPropertyObject.setEnabled(false);
        // contRoomResBuilding		
        this.contRoomResBuilding.setBoundLabelText(resHelper.getString("contRoomResBuilding.boundLabelText"));		
        this.contRoomResBuilding.setBoundLabelLength(100);		
        this.contRoomResBuilding.setBoundLabelUnderline(true);		
        this.contRoomResBuilding.setEnabled(false);		
        this.contRoomResBuilding.setVisible(false);
        // contRoomResBuildingFloor		
        this.contRoomResBuildingFloor.setBoundLabelText(resHelper.getString("contRoomResBuildingFloor.boundLabelText"));		
        this.contRoomResBuildingFloor.setBoundLabelLength(100);		
        this.contRoomResBuildingFloor.setBoundLabelUnderline(true);		
        this.contRoomResBuildingFloor.setEnabled(false);		
        this.contRoomResBuildingFloor.setVisible(false);
        // contRoomResRoom		
        this.contRoomResRoom.setBoundLabelText(resHelper.getString("contRoomResRoom.boundLabelText"));		
        this.contRoomResRoom.setBoundLabelLength(100);		
        this.contRoomResRoom.setBoundLabelUnderline(true);		
        this.contRoomResRoom.setEnabled(false);		
        this.contRoomResRoom.setVisible(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // txtName		
        this.txtName.setMaxLength(255);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // propertyObjectComboBox		
        this.propertyObjectComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.RoomPropertyTypeEnum").toArray());
        this.propertyObjectComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    propertyObjectComboBox_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // f7RoomResPropertyObject		
        this.f7RoomResPropertyObject.setDisplayFormat("$name$");		
        this.f7RoomResPropertyObject.setEditFormat("$number$");		
        this.f7RoomResPropertyObject.setEnabledMultiSelection(true);		
        this.f7RoomResPropertyObject.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.RoomPropertyReportProjectQuery");		
        this.f7RoomResPropertyObject.setEnabled(false);		
        this.f7RoomResPropertyObject.setVisible(false);
        this.f7RoomResPropertyObject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7RoomResPropertyObject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7RoomResBuilding		
        this.f7RoomResBuilding.setDisplayFormat("$name$");		
        this.f7RoomResBuilding.setEditFormat("$number$");		
        this.f7RoomResBuilding.setEnabledMultiSelection(true);		
        this.f7RoomResBuilding.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.RoomPropertyReportBuildingQuery");		
        this.f7RoomResBuilding.setVisible(false);		
        this.f7RoomResBuilding.setEnabled(false);
        this.f7RoomResBuilding.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7RoomResBuilding_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7RoomResBuildingFloor		
        this.f7RoomResBuildingFloor.setDisplayFormat("$name$");		
        this.f7RoomResBuildingFloor.setEditFormat("$number$");		
        this.f7RoomResBuildingFloor.setEnabledMultiSelection(true);		
        this.f7RoomResBuildingFloor.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.RoomPropertyReportBuildingFloorQuery");		
        this.f7RoomResBuildingFloor.setEnabled(false);		
        this.f7RoomResBuildingFloor.setVisible(false);
        this.f7RoomResBuildingFloor.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7RoomResBuildingFloor_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7RoomResRoom		
        this.f7RoomResRoom.setDisplayFormat("$name$");		
        this.f7RoomResRoom.setEditFormat("$number$");		
        this.f7RoomResRoom.setEnabledMultiSelection(true);		
        this.f7RoomResRoom.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.RoomPropertyReportRoomQuery");		
        this.f7RoomResRoom.setEnabled(false);		
        this.f7RoomResRoom.setVisible(false);
        this.f7RoomResRoom.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7RoomResRoom_dataChanged(e);
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(17, 591, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(352, 590, 270, 19));
        this.add(contCreateTime, null);
        contName.setBounds(new Rectangle(13, 13, 270, 19));
        this.add(contName, null);
        contNumber.setBounds(new Rectangle(323, 13, 270, 19));
        this.add(contNumber, null);
        kdtRoomProepertyEntry.setBounds(new Rectangle(10, 78, 964, 497));
        this.add(kdtRoomProepertyEntry, null);
        conPropertyObjectType.setBounds(new Rectangle(13, 48, 270, 19));
        this.add(conPropertyObjectType, null);
        contRoomResPropertyObject.setBounds(new Rectangle(324, 48, 270, 19));
        this.add(contRoomResPropertyObject, null);
        contRoomResBuilding.setBounds(new Rectangle(324, 48, 270, 19));
        this.add(contRoomResBuilding, null);
        contRoomResBuildingFloor.setBounds(new Rectangle(324, 48, 270, 19));
        this.add(contRoomResBuildingFloor, null);
        contRoomResRoom.setBounds(new Rectangle(324, 48, 270, 19));
        this.add(contRoomResRoom, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //conPropertyObjectType
        conPropertyObjectType.setBoundEditor(propertyObjectComboBox);
        //contRoomResPropertyObject
        contRoomResPropertyObject.setBoundEditor(f7RoomResPropertyObject);
        //contRoomResBuilding
        contRoomResBuilding.setBoundEditor(f7RoomResBuilding);
        //contRoomResBuildingFloor
        contRoomResBuildingFloor.setBoundEditor(f7RoomResBuildingFloor);
        //contRoomResRoom
        contRoomResRoom.setBoundEditor(f7RoomResRoom);

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
		dataBinder.registerBinding("roomProepertyEntry.roomResName", String.class, this.kdtRoomProepertyEntry, "roomResName.text");
		dataBinder.registerBinding("roomProepertyEntry.roomResProperty", String.class, this.kdtRoomProepertyEntry, "roomResProperty.text");
		dataBinder.registerBinding("roomProepertyEntry.address", String.class, this.kdtRoomProepertyEntry, "address.text");
		dataBinder.registerBinding("roomProepertyEntry.area", String.class, this.kdtRoomProepertyEntry, "area.text");
		dataBinder.registerBinding("roomProepertyEntry.propertyOwner", String.class, this.kdtRoomProepertyEntry, "propertyOwner.text");
		dataBinder.registerBinding("roomProepertyEntry.property", String.class, this.kdtRoomProepertyEntry, "property.text");
		dataBinder.registerBinding("roomProepertyEntry.changeReason", String.class, this.kdtRoomProepertyEntry, "changeReason.text");
		dataBinder.registerBinding("roomProepertyEntry.propertyNumber", String.class, this.kdtRoomProepertyEntry, "propertyNumber.text");
		dataBinder.registerBinding("roomProepertyEntry.certifiedTime", java.sql.Timestamp.class, this.kdtRoomProepertyEntry, "certifiedTime.text");
		dataBinder.registerBinding("roomProepertyEntry.informationnote", String.class, this.kdtRoomProepertyEntry, "informationnote.text");
		dataBinder.registerBinding("roomProepertyEntry.remark1", String.class, this.kdtRoomProepertyEntry, "remark1.text");
		dataBinder.registerBinding("roomProepertyEntry.remark2", String.class, this.kdtRoomProepertyEntry, "remark2.text");
		dataBinder.registerBinding("roomProepertyEntry.remark3", String.class, this.kdtRoomProepertyEntry, "remark3.text");
		dataBinder.registerBinding("roomProepertyEntry", com.kingdee.eas.fdc.tenancy.RoomPropertyReportInfo.class, this.kdtRoomProepertyEntry, "userObject");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("propertyOjbectType", com.kingdee.eas.fdc.tenancy.RoomPropertyTypeEnum.class, this.propertyObjectComboBox, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.RoomPropertyEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.tenancy.RoomPropertyMainInfo)ov;
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
		getValidateHelper().registerBindProperty("roomProepertyEntry.roomResName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomProepertyEntry.roomResProperty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomProepertyEntry.address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomProepertyEntry.area", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomProepertyEntry.propertyOwner", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomProepertyEntry.property", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomProepertyEntry.changeReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomProepertyEntry.propertyNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomProepertyEntry.certifiedTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomProepertyEntry.informationnote", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomProepertyEntry.remark1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomProepertyEntry.remark2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomProepertyEntry.remark3", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomProepertyEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("propertyOjbectType", ValidateHelper.ON_SAVE);    		
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
     * output propertyObjectComboBox_actionPerformed method
     */
    protected void propertyObjectComboBox_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7RoomResPropertyObject_dataChanged method
     */
    protected void f7RoomResPropertyObject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7RoomResBuilding_dataChanged method
     */
    protected void f7RoomResBuilding_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7RoomResBuildingFloor_dataChanged method
     */
    protected void f7RoomResBuildingFloor_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7RoomResRoom_dataChanged method
     */
    protected void f7RoomResRoom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("roomProepertyEntry.roomResName"));
    sic.add(new SelectorItemInfo("roomProepertyEntry.roomResProperty"));
    sic.add(new SelectorItemInfo("roomProepertyEntry.address"));
    sic.add(new SelectorItemInfo("roomProepertyEntry.area"));
    sic.add(new SelectorItemInfo("roomProepertyEntry.propertyOwner"));
    sic.add(new SelectorItemInfo("roomProepertyEntry.property"));
    sic.add(new SelectorItemInfo("roomProepertyEntry.changeReason"));
    sic.add(new SelectorItemInfo("roomProepertyEntry.propertyNumber"));
    sic.add(new SelectorItemInfo("roomProepertyEntry.certifiedTime"));
    sic.add(new SelectorItemInfo("roomProepertyEntry.informationnote"));
    sic.add(new SelectorItemInfo("roomProepertyEntry.remark1"));
    sic.add(new SelectorItemInfo("roomProepertyEntry.remark2"));
    sic.add(new SelectorItemInfo("roomProepertyEntry.remark3"));
        sic.add(new SelectorItemInfo("roomProepertyEntry.*"));
//        sic.add(new SelectorItemInfo("roomProepertyEntry.number"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("propertyOjbectType"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "RoomPropertyEditUI");
    }




}