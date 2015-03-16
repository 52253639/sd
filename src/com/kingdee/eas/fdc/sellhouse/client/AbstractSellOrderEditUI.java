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
public abstract class AbstractSellOrderEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSellOrderEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkOrderDate;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel tabBaseInfo;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRooms;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer ≈Ã¥Œ±‡¬Î;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrderDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel tabPlan;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPlan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanRevAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPlanRevAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddPlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemovePlan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomCountSum;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomCountSum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomAreaSum;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomAreaSum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingAreaSum;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingAreaSum;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDes;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoomByList;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomSelect;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected ActionAddRoomByList actionAddRoomByList = null;
    /**
     * output class constructor
     */
    public AbstractSellOrderEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSellOrderEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddRoomByList
        this.actionAddRoomByList = new ActionAddRoomByList(this);
        getActionManager().registerAction("actionAddRoomByList", actionAddRoomByList);
         this.actionAddRoomByList.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.txtProjectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBuildingAvgPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkOrderDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.tabPanel = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.tabBaseInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblRooms = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contProjectNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.≈Ã¥Œ±‡¬Î = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrderDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingAvgPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAddRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tabPlan = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblPlan = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contPlanRevAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPlanRevAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomAvgPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRoomAvgPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnAddPlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemovePlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contRoomCountSum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRoomCountSum = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contRoomAreaSum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRoomAreaSum = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contBuildingAreaSum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBuildingAreaSum = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDes = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.btnAddRoomByList = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.prmtRoomSelect = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtProjectNumber.setName("txtProjectNumber");
        this.txtProjectName.setName("txtProjectName");
        this.txtNumber.setName("txtNumber");
        this.txtBuildingAvgPrice.setName("txtBuildingAvgPrice");
        this.txtName.setName("txtName");
        this.pkOrderDate.setName("pkOrderDate");
        this.tabPanel.setName("tabPanel");
        this.tabBaseInfo.setName("tabBaseInfo");
        this.tblRooms.setName("tblRooms");
        this.contProjectNumber.setName("contProjectNumber");
        this.contProjectName.setName("contProjectName");
        this.≈Ã¥Œ±‡¬Î.setName("≈Ã¥Œ±‡¬Î");
        this.contName.setName("contName");
        this.contOrderDate.setName("contOrderDate");
        this.contBuildingAvgPrice.setName("contBuildingAvgPrice");
        this.btnAddRoom.setName("btnAddRoom");
        this.btnRemoveRoom.setName("btnRemoveRoom");
        this.tabPlan.setName("tabPlan");
        this.tblPlan.setName("tblPlan");
        this.contPlanRevAmount.setName("contPlanRevAmount");
        this.txtPlanRevAmount.setName("txtPlanRevAmount");
        this.contDes.setName("contDes");
        this.contRoomAvgPrice.setName("contRoomAvgPrice");
        this.contTotalPrice.setName("contTotalPrice");
        this.txtRoomAvgPrice.setName("txtRoomAvgPrice");
        this.txtTotalPrice.setName("txtTotalPrice");
        this.btnAddPlan.setName("btnAddPlan");
        this.btnRemovePlan.setName("btnRemovePlan");
        this.contRoomCountSum.setName("contRoomCountSum");
        this.txtRoomCountSum.setName("txtRoomCountSum");
        this.contRoomAreaSum.setName("contRoomAreaSum");
        this.txtRoomAreaSum.setName("txtRoomAreaSum");
        this.contBuildingAreaSum.setName("contBuildingAreaSum");
        this.txtBuildingAreaSum.setName("txtBuildingAreaSum");
        this.txtDes.setName("txtDes");
        this.btnAddRoomByList.setName("btnAddRoomByList");
        this.prmtRoomSelect.setName("prmtRoomSelect");
        // CoreUI		
        this.setPreferredSize(new Dimension(700,600));
        // txtProjectNumber
        // txtProjectName
        // txtNumber
        // txtBuildingAvgPrice		
        this.txtBuildingAvgPrice.setEnabled(false);		
        this.txtBuildingAvgPrice.setDataType(1);
        // txtName
        // pkOrderDate
        // tabPanel
        // tabBaseInfo
        // tblRooms
		String tblRoomsStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol5\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"buildPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"roomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{roomNumber}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{buildPrice}</t:Cell><t:Cell>$Resource{roomPrice}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblRooms.setFormatXml(resHelper.translateString("tblRooms",tblRoomsStrXML));

        

        // contProjectNumber		
        this.contProjectNumber.setBoundLabelText(resHelper.getString("contProjectNumber.boundLabelText"));		
        this.contProjectNumber.setBoundLabelLength(100);		
        this.contProjectNumber.setBoundLabelUnderline(true);
        // contProjectName		
        this.contProjectName.setBoundLabelText(resHelper.getString("contProjectName.boundLabelText"));		
        this.contProjectName.setBoundLabelLength(100);		
        this.contProjectName.setBoundLabelUnderline(true);
        // ≈Ã¥Œ±‡¬Î		
        this.≈Ã¥Œ±‡¬Î.setBoundLabelText(resHelper.getString("≈Ã¥Œ±‡¬Î.boundLabelText"));		
        this.≈Ã¥Œ±‡¬Î.setBoundLabelLength(100);		
        this.≈Ã¥Œ±‡¬Î.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contOrderDate		
        this.contOrderDate.setBoundLabelText(resHelper.getString("contOrderDate.boundLabelText"));		
        this.contOrderDate.setBoundLabelLength(100);		
        this.contOrderDate.setBoundLabelUnderline(true);
        // contBuildingAvgPrice		
        this.contBuildingAvgPrice.setBoundLabelText(resHelper.getString("contBuildingAvgPrice.boundLabelText"));		
        this.contBuildingAvgPrice.setBoundLabelLength(100);		
        this.contBuildingAvgPrice.setBoundLabelUnderline(true);
        // btnAddRoom		
        this.btnAddRoom.setText(resHelper.getString("btnAddRoom.text"));
        this.btnAddRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRemoveRoom		
        this.btnRemoveRoom.setText(resHelper.getString("btnRemoveRoom.text"));
        this.btnRemoveRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRemoveRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tabPlan
        // tblPlan
		String tblPlanStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"pro\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"planAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{pro}</t:Cell><t:Cell>$Resource{planAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblPlan.setFormatXml(resHelper.translateString("tblPlan",tblPlanStrXML));
        this.tblPlan.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblPlan_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // contPlanRevAmount		
        this.contPlanRevAmount.setBoundLabelText(resHelper.getString("contPlanRevAmount.boundLabelText"));		
        this.contPlanRevAmount.setBoundLabelLength(100);		
        this.contPlanRevAmount.setBoundLabelUnderline(true);
        // txtPlanRevAmount		
        this.txtPlanRevAmount.setDataType(1);
        this.txtPlanRevAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtPlanRevAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contDes		
        this.contDes.setBoundLabelText(resHelper.getString("contDes.boundLabelText"));		
        this.contDes.setBoundLabelLength(100);		
        this.contDes.setBoundLabelUnderline(true);
        // contRoomAvgPrice		
        this.contRoomAvgPrice.setBoundLabelText(resHelper.getString("contRoomAvgPrice.boundLabelText"));		
        this.contRoomAvgPrice.setBoundLabelUnderline(true);		
        this.contRoomAvgPrice.setBoundLabelLength(100);
        // contTotalPrice		
        this.contTotalPrice.setBoundLabelText(resHelper.getString("contTotalPrice.boundLabelText"));		
        this.contTotalPrice.setBoundLabelLength(100);		
        this.contTotalPrice.setBoundLabelUnderline(true);
        // txtRoomAvgPrice		
        this.txtRoomAvgPrice.setEnabled(false);		
        this.txtRoomAvgPrice.setDataType(1);
        // txtTotalPrice		
        this.txtTotalPrice.setEnabled(false);		
        this.txtTotalPrice.setDataType(1);
        // btnAddPlan		
        this.btnAddPlan.setText(resHelper.getString("btnAddPlan.text"));
        this.btnAddPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddPlan_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRemovePlan		
        this.btnRemovePlan.setText(resHelper.getString("btnRemovePlan.text"));
        this.btnRemovePlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRemovePlan_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contRoomCountSum		
        this.contRoomCountSum.setBoundLabelText(resHelper.getString("contRoomCountSum.boundLabelText"));		
        this.contRoomCountSum.setBoundLabelLength(100);		
        this.contRoomCountSum.setBoundLabelUnderline(true);
        // txtRoomCountSum		
        this.txtRoomCountSum.setEnabled(false);		
        this.txtRoomCountSum.setDataType(5);
        // contRoomAreaSum		
        this.contRoomAreaSum.setBoundLabelText(resHelper.getString("contRoomAreaSum.boundLabelText"));		
        this.contRoomAreaSum.setBoundLabelLength(100);		
        this.contRoomAreaSum.setBoundLabelUnderline(true);
        // txtRoomAreaSum		
        this.txtRoomAreaSum.setEnabled(false);		
        this.txtRoomAreaSum.setDataType(1);
        // contBuildingAreaSum		
        this.contBuildingAreaSum.setBoundLabelText(resHelper.getString("contBuildingAreaSum.boundLabelText"));		
        this.contBuildingAreaSum.setBoundLabelLength(100);		
        this.contBuildingAreaSum.setBoundLabelUnderline(true);
        // txtBuildingAreaSum		
        this.txtBuildingAreaSum.setEnabled(false);		
        this.txtBuildingAreaSum.setDataType(1);
        // txtDes		
        this.txtDes.setLineWrap(true);		
        this.txtDes.setMaxLength(255);
        // btnAddRoomByList
        this.btnAddRoomByList.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRoomByList, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRoomByList.setText(resHelper.getString("btnAddRoomByList.text"));
        // prmtRoomSelect		
        this.prmtRoomSelect.setVisible(false);		
        this.prmtRoomSelect.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSelectByListQuery");
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
        this.setBounds(new Rectangle(10, 10, 700, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 700, 600));
        tabPanel.setBounds(new Rectangle(5, 10, 683, 583));
        this.add(tabPanel, new KDLayout.Constraints(5, 10, 683, 583, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //tabPanel
        tabPanel.add(tabBaseInfo, resHelper.getString("tabBaseInfo.constraints"));
        tabPanel.add(tabPlan, resHelper.getString("tabPlan.constraints"));
        //tabBaseInfo
        tabBaseInfo.setLayout(null);        tblRooms.setBounds(new Rectangle(4, 258, 671, 288));
        tabBaseInfo.add(tblRooms, null);
        contProjectNumber.setBounds(new Rectangle(14, 10, 270, 19));
        tabBaseInfo.add(contProjectNumber, null);
        contProjectName.setBounds(new Rectangle(374, 10, 270, 19));
        tabBaseInfo.add(contProjectName, null);
        ≈Ã¥Œ±‡¬Î.setBounds(new Rectangle(14, 36, 270, 19));
        tabBaseInfo.add(≈Ã¥Œ±‡¬Î, null);
        contName.setBounds(new Rectangle(374, 36, 270, 19));
        tabBaseInfo.add(contName, null);
        contOrderDate.setBounds(new Rectangle(14, 63, 270, 19));
        tabBaseInfo.add(contOrderDate, null);
        contBuildingAvgPrice.setBounds(new Rectangle(14, 89, 270, 19));
        tabBaseInfo.add(contBuildingAvgPrice, null);
        btnAddRoom.setBounds(new Rectangle(315, 234, 130, 19));
        tabBaseInfo.add(btnAddRoom, null);
        btnRemoveRoom.setBounds(new Rectangle(588, 234, 83, 19));
        tabBaseInfo.add(btnRemoveRoom, null);
        contDes.setBounds(new Rectangle(14, 171, 646, 60));
        tabBaseInfo.add(contDes, null);
        contRoomAvgPrice.setBounds(new Rectangle(374, 89, 270, 19));
        tabBaseInfo.add(contRoomAvgPrice, null);
        contTotalPrice.setBounds(new Rectangle(374, 63, 270, 19));
        tabBaseInfo.add(contTotalPrice, null);
        contRoomCountSum.setBounds(new Rectangle(14, 116, 270, 19));
        tabBaseInfo.add(contRoomCountSum, null);
        contRoomAreaSum.setBounds(new Rectangle(374, 116, 270, 19));
        tabBaseInfo.add(contRoomAreaSum, null);
        contBuildingAreaSum.setBounds(new Rectangle(14, 145, 270, 19));
        tabBaseInfo.add(contBuildingAreaSum, null);
        btnAddRoomByList.setBounds(new Rectangle(451, 234, 130, 19));
        tabBaseInfo.add(btnAddRoomByList, null);
        prmtRoomSelect.setBounds(new Rectangle(9, 236, 170, 19));
        tabBaseInfo.add(prmtRoomSelect, null);
        //contProjectNumber
        contProjectNumber.setBoundEditor(txtProjectNumber);
        //contProjectName
        contProjectName.setBoundEditor(txtProjectName);
        //≈Ã¥Œ±‡¬Î
        ≈Ã¥Œ±‡¬Î.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contOrderDate
        contOrderDate.setBoundEditor(pkOrderDate);
        //contBuildingAvgPrice
        contBuildingAvgPrice.setBoundEditor(txtBuildingAvgPrice);
        //contDes
        contDes.setBoundEditor(txtDes);
        //contRoomAvgPrice
        contRoomAvgPrice.setBoundEditor(txtRoomAvgPrice);
        //contTotalPrice
        contTotalPrice.setBoundEditor(txtTotalPrice);
        //contRoomCountSum
        contRoomCountSum.setBoundEditor(txtRoomCountSum);
        //contRoomAreaSum
        contRoomAreaSum.setBoundEditor(txtRoomAreaSum);
        //contBuildingAreaSum
        contBuildingAreaSum.setBoundEditor(txtBuildingAreaSum);
        //tabPlan
        tabPlan.setLayout(null);        tblPlan.setBounds(new Rectangle(0, 39, 677, 501));
        tabPlan.add(tblPlan, null);
        contPlanRevAmount.setBounds(new Rectangle(7, 9, 270, 19));
        tabPlan.add(contPlanRevAmount, null);
        btnAddPlan.setBounds(new Rectangle(445, 9, 75, 19));
        tabPlan.add(btnAddPlan, null);
        btnRemovePlan.setBounds(new Rectangle(536, 9, 75, 19));
        tabPlan.add(btnRemovePlan, null);
        //contPlanRevAmount
        contPlanRevAmount.setBoundEditor(txtPlanRevAmount);

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
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SellOrderEditUIHandler";
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
        this.editData = (com.kingdee.eas.framework.CoreBaseInfo)ov;
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
	 * ????????ßµ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
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
     * output btnAddRoom_actionPerformed method
     */
    protected void btnAddRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnRemoveRoom_actionPerformed method
     */
    protected void btnRemoveRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblPlan_editStopped method
     */
    protected void tblPlan_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtPlanRevAmount_dataChanged method
     */
    protected void txtPlanRevAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output btnAddPlan_actionPerformed method
     */
    protected void btnAddPlan_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnRemovePlan_actionPerformed method
     */
    protected void btnRemovePlan_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionAddRoomByList_actionPerformed method
     */
    public void actionAddRoomByList_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddRoomByList(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddRoomByList() {
    	return false;
    }

    /**
     * output ActionAddRoomByList class
     */     
    protected class ActionAddRoomByList extends ItemAction {     
    
        public ActionAddRoomByList()
        {
            this(null);
        }

        public ActionAddRoomByList(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddRoomByList.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRoomByList.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRoomByList.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellOrderEditUI.this, "ActionAddRoomByList", "actionAddRoomByList_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SellOrderEditUI");
    }




}