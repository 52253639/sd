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
public abstract class AbstractRoomPriceBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomPriceBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRooms;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnScheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHighestRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHighestBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLowestBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLowestRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriceBillType;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToIntegerType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsAutoToInteger;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDigit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoomByList;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomSelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Building;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalCount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtHighestRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtHighestBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLowestBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLowestRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPriceBillType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboToIntegerType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboDigit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboTopriceType;
    protected com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo editData = null;
    protected ActionAddRoomByList actionAddRoomByList = null;
    /**
     * output class constructor
     */
    public AbstractRoomPriceBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomPriceBillEditUI.class.getName());
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
        //actionAddRoomByList
        this.actionAddRoomByList = new ActionAddRoomByList(this);
        getActionManager().registerAction("actionAddRoomByList", actionAddRoomByList);
         this.actionAddRoomByList.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tblRooms = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnScheme = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contTotalPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingAvgPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomAvgPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHighestRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHighestBuildingPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLowestBuildingPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLowestRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnImportPrice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contPriceBillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAddRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contToIntegerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsAutoToInteger = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contDigit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAddRoomByList = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.prmtRoomSelect = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Building = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTotalPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBuildingAvgPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomAvgPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtHighestRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtHighestBuildingPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLowestBuildingPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLowestRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboPriceBillType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboToIntegerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboDigit = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboTopriceType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contName.setName("contName");
        this.contBuilding.setName("contBuilding");
        this.tblRooms.setName("tblRooms");
        this.btnScheme.setName("btnScheme");
        this.contTotalPrice.setName("contTotalPrice");
        this.contTotalBuildingArea.setName("contTotalBuildingArea");
        this.contBuildingAvgPrice.setName("contBuildingAvgPrice");
        this.contRoomAvgPrice.setName("contRoomAvgPrice");
        this.contTotalCount.setName("contTotalCount");
        this.contHighestRoomPrice.setName("contHighestRoomPrice");
        this.contHighestBuildingPrice.setName("contHighestBuildingPrice");
        this.contTotalRoomArea.setName("contTotalRoomArea");
        this.contLowestBuildingPrice.setName("contLowestBuildingPrice");
        this.contLowestRoomPrice.setName("contLowestRoomPrice");
        this.btnImportPrice.setName("btnImportPrice");
        this.contPriceBillType.setName("contPriceBillType");
        this.btnAddRoom.setName("btnAddRoom");
        this.btnDelRoom.setName("btnDelRoom");
        this.contToIntegerType.setName("contToIntegerType");
        this.chkIsAutoToInteger.setName("chkIsAutoToInteger");
        this.contDigit.setName("contDigit");
        this.btnAddRoomByList.setName("btnAddRoomByList");
        this.prmtRoomSelect.setName("prmtRoomSelect");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateDate.setName("pkCreateDate");
        this.txtNumber.setName("txtNumber");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.txtName.setName("txtName");
        this.f7Building.setName("f7Building");
        this.txtTotalPrice.setName("txtTotalPrice");
        this.txtTotalBuildingArea.setName("txtTotalBuildingArea");
        this.txtBuildingAvgPrice.setName("txtBuildingAvgPrice");
        this.txtRoomAvgPrice.setName("txtRoomAvgPrice");
        this.txtTotalCount.setName("txtTotalCount");
        this.txtHighestRoomPrice.setName("txtHighestRoomPrice");
        this.txtHighestBuildingPrice.setName("txtHighestBuildingPrice");
        this.txtTotalRoomArea.setName("txtTotalRoomArea");
        this.txtLowestBuildingPrice.setName("txtLowestBuildingPrice");
        this.txtLowestRoomPrice.setName("txtLowestRoomPrice");
        this.comboPriceBillType.setName("comboPriceBillType");
        this.comboToIntegerType.setName("comboToIntegerType");
        this.comboDigit.setName("comboDigit");
        this.comboTopriceType.setName("comboTopriceType");
        // CoreUI		
        this.setPreferredSize(new Dimension(750,629));		
        this.menuSubmitOption.setVisible(false);
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
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // tblRooms
		String tblRoomsStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"sumAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"buildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"isCalByRoom\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"priceType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"roomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"roomId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{sumAmount}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{buildingPrice}</t:Cell><t:Cell>$Resource{isCalByRoom}</t:Cell><t:Cell>$Resource{priceType}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{roomPrice}</t:Cell><t:Cell>$Resource{roomId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblRooms.setFormatXml(resHelper.translateString("tblRooms",tblRoomsStrXML));
        this.tblRooms.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblRooms_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblRooms.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblRooms_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnScheme		
        this.btnScheme.setText(resHelper.getString("btnScheme.text"));
        this.btnScheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnScheme_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contTotalPrice		
        this.contTotalPrice.setBoundLabelText(resHelper.getString("contTotalPrice.boundLabelText"));		
        this.contTotalPrice.setBoundLabelLength(100);		
        this.contTotalPrice.setBoundLabelUnderline(true);
        // contTotalBuildingArea		
        this.contTotalBuildingArea.setBoundLabelText(resHelper.getString("contTotalBuildingArea.boundLabelText"));		
        this.contTotalBuildingArea.setBoundLabelUnderline(true);		
        this.contTotalBuildingArea.setBoundLabelLength(100);
        // contBuildingAvgPrice		
        this.contBuildingAvgPrice.setBoundLabelText(resHelper.getString("contBuildingAvgPrice.boundLabelText"));		
        this.contBuildingAvgPrice.setBoundLabelLength(100);		
        this.contBuildingAvgPrice.setBoundLabelUnderline(true);
        // contRoomAvgPrice		
        this.contRoomAvgPrice.setBoundLabelText(resHelper.getString("contRoomAvgPrice.boundLabelText"));		
        this.contRoomAvgPrice.setBoundLabelLength(100);		
        this.contRoomAvgPrice.setBoundLabelUnderline(true);
        // contTotalCount		
        this.contTotalCount.setBoundLabelText(resHelper.getString("contTotalCount.boundLabelText"));		
        this.contTotalCount.setBoundLabelLength(100);		
        this.contTotalCount.setBoundLabelUnderline(true);
        // contHighestRoomPrice		
        this.contHighestRoomPrice.setBoundLabelText(resHelper.getString("contHighestRoomPrice.boundLabelText"));		
        this.contHighestRoomPrice.setBoundLabelLength(100);		
        this.contHighestRoomPrice.setBoundLabelUnderline(true);
        // contHighestBuildingPrice		
        this.contHighestBuildingPrice.setBoundLabelText(resHelper.getString("contHighestBuildingPrice.boundLabelText"));		
        this.contHighestBuildingPrice.setBoundLabelLength(100);		
        this.contHighestBuildingPrice.setBoundLabelUnderline(true);
        // contTotalRoomArea		
        this.contTotalRoomArea.setBoundLabelText(resHelper.getString("contTotalRoomArea.boundLabelText"));		
        this.contTotalRoomArea.setBoundLabelLength(100);		
        this.contTotalRoomArea.setBoundLabelUnderline(true);
        // contLowestBuildingPrice		
        this.contLowestBuildingPrice.setBoundLabelText(resHelper.getString("contLowestBuildingPrice.boundLabelText"));		
        this.contLowestBuildingPrice.setBoundLabelLength(100);		
        this.contLowestBuildingPrice.setBoundLabelUnderline(true);
        // contLowestRoomPrice		
        this.contLowestRoomPrice.setBoundLabelText(resHelper.getString("contLowestRoomPrice.boundLabelText"));		
        this.contLowestRoomPrice.setBoundLabelUnderline(true);		
        this.contLowestRoomPrice.setBoundLabelLength(100);
        // btnImportPrice		
        this.btnImportPrice.setText(resHelper.getString("btnImportPrice.text"));
        this.btnImportPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnImportPrice_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contPriceBillType		
        this.contPriceBillType.setBoundLabelText(resHelper.getString("contPriceBillType.boundLabelText"));		
        this.contPriceBillType.setBoundLabelLength(100);		
        this.contPriceBillType.setBoundLabelUnderline(true);
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
        // btnDelRoom		
        this.btnDelRoom.setText(resHelper.getString("btnDelRoom.text"));
        this.btnDelRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contToIntegerType		
        this.contToIntegerType.setBoundLabelText(resHelper.getString("contToIntegerType.boundLabelText"));		
        this.contToIntegerType.setBoundLabelLength(100);		
        this.contToIntegerType.setBoundLabelUnderline(true);
        // chkIsAutoToInteger		
        this.chkIsAutoToInteger.setText(resHelper.getString("chkIsAutoToInteger.text"));
        this.chkIsAutoToInteger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsAutoToInteger_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contDigit		
        this.contDigit.setBoundLabelText(resHelper.getString("contDigit.boundLabelText"));		
        this.contDigit.setBoundLabelUnderline(true);		
        this.contDigit.setBoundLabelLength(100);
        // btnAddRoomByList
        this.btnAddRoomByList.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRoomByList, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRoomByList.setText(resHelper.getString("btnAddRoomByList.text"));
        // prmtRoomSelect		
        this.prmtRoomSelect.setVisible(false);		
        this.prmtRoomSelect.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSelectByListQuery");
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setCommitFormat("$number$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setDisplayFormat("$name$");
        // pkCreateDate
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(300);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // f7Building		
        this.f7Building.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");		
        this.f7Building.setDisplayFormat("$name$");		
        this.f7Building.setEditFormat("$number$");		
        this.f7Building.setCommitFormat("$number$");		
        this.f7Building.setEnabledMultiSelection(true);
        this.f7Building.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Building_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtTotalPrice		
        this.txtTotalPrice.setDataType(1);		
        this.txtTotalPrice.setEnabled(false);		
        this.txtTotalPrice.setForeground(new java.awt.Color(0,0,255));
        // txtTotalBuildingArea		
        this.txtTotalBuildingArea.setEnabled(false);		
        this.txtTotalBuildingArea.setDataType(1);
        // txtBuildingAvgPrice		
        this.txtBuildingAvgPrice.setDataType(1);
        // txtRoomAvgPrice		
        this.txtRoomAvgPrice.setDataType(1);
        // txtTotalCount
        // txtHighestRoomPrice		
        this.txtHighestRoomPrice.setDataType(1);
        // txtHighestBuildingPrice		
        this.txtHighestBuildingPrice.setDataType(1);
        // txtTotalRoomArea		
        this.txtTotalRoomArea.setDataType(1);
        // txtLowestBuildingPrice		
        this.txtLowestBuildingPrice.setDataType(1);
        // txtLowestRoomPrice		
        this.txtLowestRoomPrice.setDataType(1);
        // comboPriceBillType		
        this.comboPriceBillType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomPriceTypeEnum").toArray());
        this.comboPriceBillType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboPriceBillType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboToIntegerType		
        this.comboToIntegerType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum").toArray());
        this.comboToIntegerType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboToIntegerType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboDigit		
        this.comboDigit.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.DigitEnum").toArray());
        this.comboDigit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboDigit_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboTopriceType		
        this.comboTopriceType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum").toArray());
        this.comboTopriceType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboTopriceType_itemStateChanged(e);
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
        this.setBounds(new Rectangle(10, 10, 770, 720));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 770, 720));
        contCreator.setBounds(new Rectangle(433, 35, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(433, 35, 270, 19, 0));
        contCreateTime.setBounds(new Rectangle(10, 35, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(10, 35, 270, 19, 0));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, 0));
        contDescription.setBounds(new Rectangle(10, 185, 693, 56));
        this.add(contDescription, new KDLayout.Constraints(10, 185, 693, 56, 0));
        contName.setBounds(new Rectangle(433, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(433, 10, 270, 19, 0));
        contBuilding.setBounds(new Rectangle(10, 272, 270, 19));
        this.add(contBuilding, new KDLayout.Constraints(10, 272, 270, 19, 0));
        tblRooms.setBounds(new Rectangle(8, 354, 728, 360));
        this.add(tblRooms, new KDLayout.Constraints(8, 354, 728, 360, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnScheme.setBounds(new Rectangle(370, 331, 88, 19));
        this.add(btnScheme, new KDLayout.Constraints(370, 331, 88, 19, 0));
        contTotalPrice.setBounds(new Rectangle(10, 60, 270, 19));
        this.add(contTotalPrice, new KDLayout.Constraints(10, 60, 270, 19, 0));
        contTotalBuildingArea.setBounds(new Rectangle(10, 85, 270, 19));
        this.add(contTotalBuildingArea, new KDLayout.Constraints(10, 85, 270, 19, 0));
        contBuildingAvgPrice.setBounds(new Rectangle(10, 110, 270, 19));
        this.add(contBuildingAvgPrice, new KDLayout.Constraints(10, 110, 270, 19, 0));
        contRoomAvgPrice.setBounds(new Rectangle(433, 110, 270, 19));
        this.add(contRoomAvgPrice, new KDLayout.Constraints(433, 110, 270, 19, 0));
        contTotalCount.setBounds(new Rectangle(433, 60, 270, 19));
        this.add(contTotalCount, new KDLayout.Constraints(433, 60, 270, 19, 0));
        contHighestRoomPrice.setBounds(new Rectangle(433, 135, 270, 19));
        this.add(contHighestRoomPrice, new KDLayout.Constraints(433, 135, 270, 19, 0));
        contHighestBuildingPrice.setBounds(new Rectangle(10, 135, 270, 19));
        this.add(contHighestBuildingPrice, new KDLayout.Constraints(10, 135, 270, 19, 0));
        contTotalRoomArea.setBounds(new Rectangle(433, 85, 270, 19));
        this.add(contTotalRoomArea, new KDLayout.Constraints(433, 85, 270, 19, 0));
        contLowestBuildingPrice.setBounds(new Rectangle(10, 160, 270, 19));
        this.add(contLowestBuildingPrice, new KDLayout.Constraints(10, 160, 270, 19, 0));
        contLowestRoomPrice.setBounds(new Rectangle(433, 160, 270, 19));
        this.add(contLowestRoomPrice, new KDLayout.Constraints(433, 160, 270, 19, 0));
        btnImportPrice.setBounds(new Rectangle(278, 331, 88, 19));
        this.add(btnImportPrice, new KDLayout.Constraints(278, 331, 88, 19, 0));
        contPriceBillType.setBounds(new Rectangle(11, 247, 270, 19));
        this.add(contPriceBillType, new KDLayout.Constraints(11, 247, 270, 19, 0));
        btnAddRoom.setBounds(new Rectangle(370, 331, 134, 19));
        this.add(btnAddRoom, new KDLayout.Constraints(370, 331, 134, 19, 0));
        btnDelRoom.setBounds(new Rectangle(650, 331, 87, 19));
        this.add(btnDelRoom, new KDLayout.Constraints(650, 331, 87, 19, 0));
        contToIntegerType.setBounds(new Rectangle(433, 272, 270, 19));
        this.add(contToIntegerType, new KDLayout.Constraints(433, 272, 270, 19, 0));
        chkIsAutoToInteger.setBounds(new Rectangle(433, 247, 140, 19));
        this.add(chkIsAutoToInteger, new KDLayout.Constraints(433, 247, 140, 19, 0));
        contDigit.setBounds(new Rectangle(433, 300, 270, 19));
        this.add(contDigit, new KDLayout.Constraints(433, 300, 270, 19, 0));
        btnAddRoomByList.setBounds(new Rectangle(510, 331, 134, 19));
        this.add(btnAddRoomByList, new KDLayout.Constraints(510, 331, 134, 19, 0));
        prmtRoomSelect.setBounds(new Rectangle(10, 330, 170, 19));
        this.add(prmtRoomSelect, new KDLayout.Constraints(10, 330, 170, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(13, 301, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(13, 301, 270, 19, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateDate);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contName
        contName.setBoundEditor(txtName);
        //contBuilding
        contBuilding.setBoundEditor(f7Building);
        //contTotalPrice
        contTotalPrice.setBoundEditor(txtTotalPrice);
        //contTotalBuildingArea
        contTotalBuildingArea.setBoundEditor(txtTotalBuildingArea);
        //contBuildingAvgPrice
        contBuildingAvgPrice.setBoundEditor(txtBuildingAvgPrice);
        //contRoomAvgPrice
        contRoomAvgPrice.setBoundEditor(txtRoomAvgPrice);
        //contTotalCount
        contTotalCount.setBoundEditor(txtTotalCount);
        //contHighestRoomPrice
        contHighestRoomPrice.setBoundEditor(txtHighestRoomPrice);
        //contHighestBuildingPrice
        contHighestBuildingPrice.setBoundEditor(txtHighestBuildingPrice);
        //contTotalRoomArea
        contTotalRoomArea.setBoundEditor(txtTotalRoomArea);
        //contLowestBuildingPrice
        contLowestBuildingPrice.setBoundEditor(txtLowestBuildingPrice);
        //contLowestRoomPrice
        contLowestRoomPrice.setBoundEditor(txtLowestRoomPrice);
        //contPriceBillType
        contPriceBillType.setBoundEditor(comboPriceBillType);
        //contToIntegerType
        contToIntegerType.setBoundEditor(comboToIntegerType);
        //contDigit
        contDigit.setBoundEditor(comboDigit);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(comboTopriceType);

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
		dataBinder.registerBinding("isAutoToInteger", boolean.class, this.chkIsAutoToInteger, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateDate, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("toIntegerType", com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum.class, this.comboToIntegerType, "selectedItem");
		dataBinder.registerBinding("digit", com.kingdee.eas.fdc.sellhouse.DigitEnum.class, this.comboDigit, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomPriceBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo)ov;
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
		getValidateHelper().registerBindProperty("isAutoToInteger", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("toIntegerType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("digit", ValidateHelper.ON_SAVE);    		
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
     * output tblRooms_editStopped method
     */
    protected void tblRooms_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblRooms_tableClicked method
     */
    protected void tblRooms_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnScheme_actionPerformed method
     */
    protected void btnScheme_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnImportPrice_actionPerformed method
     */
    protected void btnImportPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnAddRoom_actionPerformed method
     */
    protected void btnAddRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDelRoom_actionPerformed method
     */
    protected void btnDelRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkIsAutoToInteger_actionPerformed method
     */
    protected void chkIsAutoToInteger_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7Building_dataChanged method
     */
    protected void f7Building_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output comboPriceBillType_itemStateChanged method
     */
    protected void comboPriceBillType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboToIntegerType_itemStateChanged method
     */
    protected void comboToIntegerType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboDigit_itemStateChanged method
     */
    protected void comboDigit_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboTopriceType_itemStateChanged method
     */
    protected void comboTopriceType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isAutoToInteger"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("toIntegerType"));
        sic.add(new SelectorItemInfo("digit"));
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
     * output actionAddRoomByList_actionPerformed method
     */
    public void actionAddRoomByList_actionPerformed(ActionEvent e) throws Exception
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
            innerActionPerformed("eas", AbstractRoomPriceBillEditUI.this, "ActionAddRoomByList", "actionAddRoomByList_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomPriceBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}