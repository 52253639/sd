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
public abstract class AbstractRoomNewCreateUI extends CoreUIObject
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomNewCreateUI.class);
    protected ResourceBundleHelper resHelper = null;
    protected com.kingdee.bos.ctrl.swing.KDToolBar RoomCreateUI_toolbar;
    protected com.kingdee.bos.ctrl.swing.KDButton btnYes;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSubarea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSerialNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOutlook;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorHeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator1;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreatePrinciple;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contListSeparator;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDButton btnActivateRooms;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDisplayName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiFloor;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSubarea;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiSerialNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtApportionArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Direction;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Sight;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuilding;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModel;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFloorHeight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualRoomArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ProductType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cBPrinciple;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cBListSeparator;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane pnlGraph;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton addFloor;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton addColumn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton delColumn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton delFloor;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDisplayName;
    /**
     * output class constructor
     */
    public AbstractRoomNewCreateUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractRoomNewCreateUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.toolBar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.menuBar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.btnYes = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSubarea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSerialNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApportionArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOutlook = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHouseProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBalconyArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorHeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator1 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreatePrinciple = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contListSeparator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnActivateRooms = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contDisplayName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.spiFloor = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiUnit = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtSubarea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.spiSerialNumber = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtApportionArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7Direction = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Sight = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBuilding = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7RoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboHouseProperty = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBalconyArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFloorHeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActualRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7ProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cBPrinciple = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cBListSeparator = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pnlGraph = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.addFloor = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.addColumn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.delColumn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.delFloor = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddUnit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtDisplayName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.setName("RoomCreateUI");
        this.toolBar.setName("RoomCreateUI_toolbar");
        this.menuBar.setName("RoomCreateUI_menubar");
        this.btnYes.setName("btnYes");
        this.btnNo.setName("btnNo");
        this.contNumber.setName("contNumber");
        this.contProject.setName("contProject");
        this.contFloor.setName("contFloor");
        this.contUnit.setName("contUnit");
        this.contSubarea.setName("contSubarea");
        this.contSerialNumber.setName("contSerialNumber");
        this.contBuildingArea.setName("contBuildingArea");
        this.contRoomArea.setName("contRoomArea");
        this.contApportionArea.setName("contApportionArea");
        this.contFace.setName("contFace");
        this.contOutlook.setName("contOutlook");
        this.contBuilding.setName("contBuilding");
        this.contRoomModel.setName("contRoomModel");
        this.contHouseProperty.setName("contHouseProperty");
        this.contCompany.setName("contCompany");
        this.contBalconyArea.setName("contBalconyArea");
        this.contFloorHeight.setName("contFloorHeight");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.kDSeparator1.setName("kDSeparator1");
        this.kDSeparator2.setName("kDSeparator2");
        this.contActualBuildingArea.setName("contActualBuildingArea");
        this.contActualRoomArea.setName("contActualRoomArea");
        this.contProductType.setName("contProductType");
        this.contCreatePrinciple.setName("contCreatePrinciple");
        this.contListSeparator.setName("contListSeparator");
        this.kDContainer1.setName("kDContainer1");
        this.btnActivateRooms.setName("btnActivateRooms");
        this.contDisplayName.setName("contDisplayName");
        this.txtNumber.setName("txtNumber");
        this.txtProject.setName("txtProject");
        this.spiFloor.setName("spiFloor");
        this.spiUnit.setName("spiUnit");
        this.txtSubarea.setName("txtSubarea");
        this.spiSerialNumber.setName("spiSerialNumber");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.txtApportionArea.setName("txtApportionArea");
        this.f7Direction.setName("f7Direction");
        this.f7Sight.setName("f7Sight");
        this.txtBuilding.setName("txtBuilding");
        this.f7RoomModel.setName("f7RoomModel");
        this.comboHouseProperty.setName("comboHouseProperty");
        this.txtCompany.setName("txtCompany");
        this.txtBalconyArea.setName("txtBalconyArea");
        this.txtFloorHeight.setName("txtFloorHeight");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.txtActualBuildingArea.setName("txtActualBuildingArea");
        this.txtActualRoomArea.setName("txtActualRoomArea");
        this.f7ProductType.setName("f7ProductType");
        this.cBPrinciple.setName("cBPrinciple");
        this.cBListSeparator.setName("cBListSeparator");
        this.pnlGraph.setName("pnlGraph");
        this.addFloor.setName("addFloor");
        this.addColumn.setName("addColumn");
        this.delColumn.setName("delColumn");
        this.delFloor.setName("delFloor");
        this.btnAddUnit.setName("btnAddUnit");
        this.txtDisplayName.setName("txtDisplayName");
        // RoomCreateUI		
        this.setPreferredSize(new Dimension(900,600));
        // RoomCreateUI_toolbar
        // RoomCreateUI_menubar
        // btnYes		
        this.btnYes.setText(resHelper.getString("btnYes.text"));
        this.btnYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnYes_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnNo		
        this.btnNo.setText(resHelper.getString("btnNo.text"));
        this.btnNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNo_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contFloor		
        this.contFloor.setBoundLabelText(resHelper.getString("contFloor.boundLabelText"));		
        this.contFloor.setBoundLabelLength(100);		
        this.contFloor.setBoundLabelUnderline(true);
        // contUnit		
        this.contUnit.setBoundLabelText(resHelper.getString("contUnit.boundLabelText"));		
        this.contUnit.setBoundLabelLength(100);		
        this.contUnit.setBoundLabelUnderline(true);
        // contSubarea		
        this.contSubarea.setBoundLabelText(resHelper.getString("contSubarea.boundLabelText"));		
        this.contSubarea.setBoundLabelLength(100);		
        this.contSubarea.setBoundLabelUnderline(true);		
        this.contSubarea.setEnabled(false);		
        this.contSubarea.setVisible(false);
        // contSerialNumber		
        this.contSerialNumber.setBoundLabelText(resHelper.getString("contSerialNumber.boundLabelText"));		
        this.contSerialNumber.setBoundLabelLength(100);		
        this.contSerialNumber.setBoundLabelUnderline(true);
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelLength(100);		
        this.contBuildingArea.setBoundLabelUnderline(true);		
        this.contBuildingArea.setEnabled(false);
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelLength(100);		
        this.contRoomArea.setBoundLabelUnderline(true);		
        this.contRoomArea.setEnabled(false);
        // contApportionArea		
        this.contApportionArea.setBoundLabelText(resHelper.getString("contApportionArea.boundLabelText"));		
        this.contApportionArea.setBoundLabelLength(100);		
        this.contApportionArea.setBoundLabelUnderline(true);		
        this.contApportionArea.setEnabled(false);
        // contFace		
        this.contFace.setBoundLabelText(resHelper.getString("contFace.boundLabelText"));		
        this.contFace.setBoundLabelLength(100);		
        this.contFace.setBoundLabelUnderline(true);		
        this.contFace.setEnabled(false);
        // contOutlook		
        this.contOutlook.setBoundLabelText(resHelper.getString("contOutlook.boundLabelText"));		
        this.contOutlook.setBoundLabelLength(100);		
        this.contOutlook.setBoundLabelUnderline(true);		
        this.contOutlook.setEnabled(false);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // contRoomModel		
        this.contRoomModel.setBoundLabelText(resHelper.getString("contRoomModel.boundLabelText"));		
        this.contRoomModel.setBoundLabelLength(100);		
        this.contRoomModel.setBoundLabelUnderline(true);		
        this.contRoomModel.setEnabled(false);
        // contHouseProperty		
        this.contHouseProperty.setBoundLabelText(resHelper.getString("contHouseProperty.boundLabelText"));		
        this.contHouseProperty.setBoundLabelLength(100);		
        this.contHouseProperty.setBoundLabelUnderline(true);		
        this.contHouseProperty.setEnabled(false);
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);
        // contBalconyArea		
        this.contBalconyArea.setBoundLabelText(resHelper.getString("contBalconyArea.boundLabelText"));		
        this.contBalconyArea.setBoundLabelLength(100);		
        this.contBalconyArea.setBoundLabelUnderline(true);		
        this.contBalconyArea.setEnabled(false);
        // contFloorHeight		
        this.contFloorHeight.setBoundLabelText(resHelper.getString("contFloorHeight.boundLabelText"));		
        this.contFloorHeight.setBoundLabelLength(100);		
        this.contFloorHeight.setBoundLabelUnderline(true);		
        this.contFloorHeight.setEnabled(false);		
        this.contFloorHeight.setVisible(false);
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(100);		
        this.contBuildingProperty.setBoundLabelUnderline(true);		
        this.contBuildingProperty.setEnabled(false);
        // kDSeparator1
        // kDSeparator2
        // contActualBuildingArea		
        this.contActualBuildingArea.setBoundLabelText(resHelper.getString("contActualBuildingArea.boundLabelText"));		
        this.contActualBuildingArea.setBoundLabelUnderline(true);		
        this.contActualBuildingArea.setBoundLabelLength(100);		
        this.contActualBuildingArea.setEnabled(false);
        // contActualRoomArea		
        this.contActualRoomArea.setBoundLabelText(resHelper.getString("contActualRoomArea.boundLabelText"));		
        this.contActualRoomArea.setBoundLabelUnderline(true);		
        this.contActualRoomArea.setBoundLabelLength(100);		
        this.contActualRoomArea.setEnabled(false);
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);		
        this.contProductType.setEnabled(false);
        // contCreatePrinciple		
        this.contCreatePrinciple.setBoundLabelText(resHelper.getString("contCreatePrinciple.boundLabelText"));		
        this.contCreatePrinciple.setBoundLabelLength(100);		
        this.contCreatePrinciple.setBoundLabelUnderline(true);
        // contListSeparator		
        this.contListSeparator.setBoundLabelText(resHelper.getString("contListSeparator.boundLabelText"));		
        this.contListSeparator.setBoundLabelLength(100);		
        this.contListSeparator.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // btnActivateRooms		
        this.btnActivateRooms.setText(resHelper.getString("btnActivateRooms.text"));
        this.btnActivateRooms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnActivateRoomsNo_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contDisplayName		
        this.contDisplayName.setBoundLabelText(resHelper.getString("contDisplayName.boundLabelText"));		
        this.contDisplayName.setBoundLabelLength(100);		
        this.contDisplayName.setBoundLabelUnderline(true);
        // txtNumber
        // txtProject		
        this.txtProject.setEnabled(false);
        // spiFloor		
        this.spiFloor.setEnabled(false);
        // spiUnit		
        this.spiUnit.setEnabled(false);
        // txtSubarea		
        this.txtSubarea.setEnabled(false);
        // spiSerialNumber		
        this.spiSerialNumber.setEnabled(false);
        // txtBuildingArea		
        this.txtBuildingArea.setDataType(1);		
        this.txtBuildingArea.setEnabled(false);
        this.txtBuildingArea.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtBuildingArea_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtRoomArea		
        this.txtRoomArea.setDataType(1);		
        this.txtRoomArea.setEnabled(false);
        this.txtRoomArea.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtRoomArea_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtApportionArea		
        this.txtApportionArea.setEnabled(false);		
        this.txtApportionArea.setDataType(1);
        // f7Direction		
        this.f7Direction.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");		
        this.f7Direction.setEnabled(false);
        // f7Sight		
        this.f7Sight.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery");		
        this.f7Sight.setEnabled(false);
        // txtBuilding		
        this.txtBuilding.setEnabled(false);
        // f7RoomModel		
        this.f7RoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");		
        this.f7RoomModel.setEnabled(false);
        this.f7RoomModel.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7RoomModel_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboHouseProperty		
        this.comboHouseProperty.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.HousePropertyEnum").toArray());		
        this.comboHouseProperty.setEnabled(false);
        this.comboHouseProperty.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboHouseProperty_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtCompany		
        this.txtCompany.setEnabled(false);
        // txtBalconyArea		
        this.txtBalconyArea.setDataType(1);		
        this.txtBalconyArea.setEnabled(false);
        this.txtBalconyArea.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtBalconyArea_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtFloorHeight		
        this.txtFloorHeight.setDataType(1);
        this.txtFloorHeight.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtFloorHeight_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7BuildingProperty		
        this.f7BuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");		
        this.f7BuildingProperty.setEnabled(false);
        this.f7BuildingProperty.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7BuildingProperty_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtActualBuildingArea		
        this.txtActualBuildingArea.setDataType(1);		
        this.txtActualBuildingArea.setEnabled(false);
        // txtActualRoomArea		
        this.txtActualRoomArea.setDataType(1);		
        this.txtActualRoomArea.setEnabled(false);
        // f7ProductType		
        this.f7ProductType.setDisplayFormat("$name$");		
        this.f7ProductType.setEditFormat("$number$");		
        this.f7ProductType.setCommitFormat("$number$");		
        this.f7ProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");		
        this.f7ProductType.setEnabled(false);
        // cBPrinciple		
        this.cBPrinciple.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomCreatePrincipleEnum").toArray());
        this.cBPrinciple.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cBPrinciple_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.cBPrinciple.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cBPrinciple_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // cBListSeparator		
        this.cBListSeparator.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomListSeparatorEnum").toArray());
        this.cBListSeparator.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cBListSeparator_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pnlGraph
        // addFloor		
        this.addFloor.setText(resHelper.getString("addFloor.text"));
        this.addFloor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    addFloor_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // addColumn		
        this.addColumn.setText(resHelper.getString("addColumn.text"));
        this.addColumn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    addColumn_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // delColumn		
        this.delColumn.setText(resHelper.getString("delColumn.text"));
        this.delColumn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    delColumn_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // delFloor		
        this.delFloor.setText(resHelper.getString("delFloor.text"));
        this.delFloor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    delFloor_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAddUnit		
        this.btnAddUnit.setText(resHelper.getString("btnAddUnit.text"));
        this.btnAddUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddUnit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtDisplayName		
        this.txtDisplayName.setEnabled(false);
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
		list.add(this.toolBar);
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 900, 610));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 900, 610));
        btnYes.setBounds(new Rectangle(89, 567, 73, 21));
        this.add(btnYes, new KDLayout.Constraints(89, 567, 73, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnNo.setBounds(new Rectangle(440, 567, 73, 21));
        this.add(btnNo, new KDLayout.Constraints(440, 567, 73, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(620, 122, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(620, 122, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contProject.setBounds(new Rectangle(620, 44, 270, 19));
        this.add(contProject, new KDLayout.Constraints(620, 44, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFloor.setBounds(new Rectangle(620, 172, 270, 19));
        this.add(contFloor, new KDLayout.Constraints(620, 172, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contUnit.setBounds(new Rectangle(620, 146, 270, 19));
        this.add(contUnit, new KDLayout.Constraints(620, 146, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSubarea.setBounds(new Rectangle(617, 391, 270, 19));
        this.add(contSubarea, new KDLayout.Constraints(617, 391, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSerialNumber.setBounds(new Rectangle(620, 197, 270, 19));
        this.add(contSerialNumber, new KDLayout.Constraints(620, 197, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuildingArea.setBounds(new Rectangle(620, 231, 270, 19));
        this.add(contBuildingArea, new KDLayout.Constraints(620, 231, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRoomArea.setBounds(new Rectangle(620, 258, 270, 19));
        this.add(contRoomArea, new KDLayout.Constraints(620, 258, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contApportionArea.setBounds(new Rectangle(620, 284, 270, 19));
        this.add(contApportionArea, new KDLayout.Constraints(620, 284, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFace.setBounds(new Rectangle(620, 420, 270, 19));
        this.add(contFace, new KDLayout.Constraints(620, 420, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOutlook.setBounds(new Rectangle(620, 445, 270, 19));
        this.add(contOutlook, new KDLayout.Constraints(620, 445, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuilding.setBounds(new Rectangle(620, 71, 270, 19));
        this.add(contBuilding, new KDLayout.Constraints(620, 71, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRoomModel.setBounds(new Rectangle(620, 470, 270, 19));
        this.add(contRoomModel, new KDLayout.Constraints(620, 470, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contHouseProperty.setBounds(new Rectangle(620, 522, 270, 19));
        this.add(contHouseProperty, new KDLayout.Constraints(620, 522, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCompany.setBounds(new Rectangle(620, 17, 270, 19));
        this.add(contCompany, new KDLayout.Constraints(620, 17, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBalconyArea.setBounds(new Rectangle(620, 310, 270, 19));
        this.add(contBalconyArea, new KDLayout.Constraints(620, 310, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFloorHeight.setBounds(new Rectangle(620, 396, 270, 19));
        this.add(contFloorHeight, new KDLayout.Constraints(620, 396, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuildingProperty.setBounds(new Rectangle(620, 496, 270, 19));
        this.add(contBuildingProperty, new KDLayout.Constraints(620, 496, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator1.setBounds(new Rectangle(610, 221, 284, 10));
        this.add(kDSeparator1, new KDLayout.Constraints(610, 221, 284, 10, 0));
        kDSeparator2.setBounds(new Rectangle(610, 389, 284, 10));
        this.add(kDSeparator2, new KDLayout.Constraints(610, 389, 284, 10, 0));
        contActualBuildingArea.setBounds(new Rectangle(620, 336, 270, 19));
        this.add(contActualBuildingArea, new KDLayout.Constraints(620, 336, 270, 19, 0));
        contActualRoomArea.setBounds(new Rectangle(620, 359, 270, 19));
        this.add(contActualRoomArea, new KDLayout.Constraints(620, 359, 270, 19, 0));
        contProductType.setBounds(new Rectangle(620, 550, 270, 19));
        this.add(contProductType, new KDLayout.Constraints(620, 550, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreatePrinciple.setBounds(new Rectangle(23, 529, 270, 19));
        this.add(contCreatePrinciple, new KDLayout.Constraints(23, 529, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contListSeparator.setBounds(new Rectangle(326, 529, 270, 19));
        this.add(contListSeparator, new KDLayout.Constraints(326, 529, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer1.setBounds(new Rectangle(11, 14, 597, 509));
        this.add(kDContainer1, new KDLayout.Constraints(11, 14, 597, 509, 0));
        btnActivateRooms.setBounds(new Rectangle(255, 568, 91, 21));
        this.add(btnActivateRooms, new KDLayout.Constraints(255, 568, 91, 21, 0));
        contDisplayName.setBounds(new Rectangle(620, 97, 270, 19));
        this.add(contDisplayName, new KDLayout.Constraints(620, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contProject
        contProject.setBoundEditor(txtProject);
        //contFloor
        contFloor.setBoundEditor(spiFloor);
        //contUnit
        contUnit.setBoundEditor(spiUnit);
        //contSubarea
        contSubarea.setBoundEditor(txtSubarea);
        //contSerialNumber
        contSerialNumber.setBoundEditor(spiSerialNumber);
        //contBuildingArea
        contBuildingArea.setBoundEditor(txtBuildingArea);
        //contRoomArea
        contRoomArea.setBoundEditor(txtRoomArea);
        //contApportionArea
        contApportionArea.setBoundEditor(txtApportionArea);
        //contFace
        contFace.setBoundEditor(f7Direction);
        //contOutlook
        contOutlook.setBoundEditor(f7Sight);
        //contBuilding
        contBuilding.setBoundEditor(txtBuilding);
        //contRoomModel
        contRoomModel.setBoundEditor(f7RoomModel);
        //contHouseProperty
        contHouseProperty.setBoundEditor(comboHouseProperty);
        //contCompany
        contCompany.setBoundEditor(txtCompany);
        //contBalconyArea
        contBalconyArea.setBoundEditor(txtBalconyArea);
        //contFloorHeight
        contFloorHeight.setBoundEditor(txtFloorHeight);
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(f7BuildingProperty);
        //contActualBuildingArea
        contActualBuildingArea.setBoundEditor(txtActualBuildingArea);
        //contActualRoomArea
        contActualRoomArea.setBoundEditor(txtActualRoomArea);
        //contProductType
        contProductType.setBoundEditor(f7ProductType);
        //contCreatePrinciple
        contCreatePrinciple.setBoundEditor(cBPrinciple);
        //contListSeparator
        contListSeparator.setBoundEditor(cBListSeparator);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(11, 14, 597, 509));        pnlGraph.setBounds(new Rectangle(3, 24, 578, 455));
        kDContainer1.getContentPane().add(pnlGraph, new KDLayout.Constraints(3, 24, 578, 455, 0));
        addFloor.setBounds(new Rectangle(258, 2, 70, 20));
        kDContainer1.getContentPane().add(addFloor, new KDLayout.Constraints(258, 2, 70, 20, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        addColumn.setBounds(new Rectangle(424, 2, 70, 20));
        kDContainer1.getContentPane().add(addColumn, new KDLayout.Constraints(424, 2, 70, 20, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        delColumn.setBounds(new Rectangle(509, 2, 70, 20));
        kDContainer1.getContentPane().add(delColumn, new KDLayout.Constraints(509, 2, 70, 20, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        delFloor.setBounds(new Rectangle(341, 2, 70, 20));
        kDContainer1.getContentPane().add(delFloor, new KDLayout.Constraints(341, 2, 70, 20, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddUnit.setBounds(new Rectangle(10, 2, 85, 20));
        kDContainer1.getContentPane().add(btnAddUnit, new KDLayout.Constraints(10, 2, 85, 20, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contDisplayName
        contDisplayName.setBoundEditor(txtDisplayName);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomNewCreateUIHandler";
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
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output btnYes_actionPerformed method
     */
    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnNo_actionPerformed method
     */
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnActivateRoomsNo_actionPerformed method
     */
    protected void btnActivateRoomsNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtBuildingArea_dataChanged method
     */
    protected void txtBuildingArea_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtRoomArea_dataChanged method
     */
    protected void txtRoomArea_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7RoomModel_dataChanged method
     */
    protected void f7RoomModel_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output comboHouseProperty_itemStateChanged method
     */
    protected void comboHouseProperty_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtBalconyArea_dataChanged method
     */
    protected void txtBalconyArea_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtFloorHeight_dataChanged method
     */
    protected void txtFloorHeight_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7BuildingProperty_dataChanged method
     */
    protected void f7BuildingProperty_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output cBPrinciple_itemStateChanged method
     */
    protected void cBPrinciple_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output cBPrinciple_actionPerformed method
     */
    protected void cBPrinciple_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output cBListSeparator_itemStateChanged method
     */
    protected void cBListSeparator_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output addFloor_actionPerformed method
     */
    protected void addFloor_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output addColumn_actionPerformed method
     */
    protected void addColumn_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output delColumn_actionPerformed method
     */
    protected void delColumn_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output delFloor_actionPerformed method
     */
    protected void delFloor_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddUnit_actionPerformed method
     */
    protected void btnAddUnit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomNewCreateUI");
    }




}