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
public abstract class AbstractRoomCreateUI extends CoreUIObject
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomCreateUI.class);
    protected ResourceBundleHelper resHelper = null;
    protected com.kingdee.bos.ctrl.swing.KDToolBar RoomCreateUI_toolbar;
    protected com.kingdee.bos.ctrl.swing.KDButton btnYes;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane pnlGraph;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSubarea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSubarea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSerialNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOutlook;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHouseProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorHeight;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFloorHeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator1;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiSerialNumber;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiFloor;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiUnit;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualRoomArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Direction;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Sight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ProductType;
    /**
     * output class constructor
     */
    public AbstractRoomCreateUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomCreateUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.toolBar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.menuBar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.btnYes = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.pnlGraph = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contFloor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSubarea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSubarea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contSerialNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApportionArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOutlook = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBuilding = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHouseProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7RoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboHouseProperty = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contBalconyArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBalconyArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contFloorHeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtFloorHeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDSeparator1 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.spiSerialNumber = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiFloor = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiUnit = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtApportionArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contActualRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtActualRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7Direction = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Sight = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7ProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.setName("RoomCreateUI");
        this.toolBar.setName("RoomCreateUI_toolbar");
        this.menuBar.setName("RoomCreateUI_menubar");
        this.btnYes.setName("btnYes");
        this.btnNo.setName("btnNo");
        this.pnlGraph.setName("pnlGraph");
        this.txtNumber.setName("txtNumber");
        this.contNumber.setName("contNumber");
        this.contProject.setName("contProject");
        this.txtProject.setName("txtProject");
        this.contFloor.setName("contFloor");
        this.contUnit.setName("contUnit");
        this.contSubarea.setName("contSubarea");
        this.txtSubarea.setName("txtSubarea");
        this.contSerialNumber.setName("contSerialNumber");
        this.contBuildingArea.setName("contBuildingArea");
        this.contRoomArea.setName("contRoomArea");
        this.contApportionArea.setName("contApportionArea");
        this.contFace.setName("contFace");
        this.contOutlook.setName("contOutlook");
        this.contBuilding.setName("contBuilding");
        this.txtBuilding.setName("txtBuilding");
        this.contRoomModel.setName("contRoomModel");
        this.contHouseProperty.setName("contHouseProperty");
        this.f7RoomModel.setName("f7RoomModel");
        this.contCompany.setName("contCompany");
        this.txtCompany.setName("txtCompany");
        this.comboHouseProperty.setName("comboHouseProperty");
        this.contBalconyArea.setName("contBalconyArea");
        this.txtBalconyArea.setName("txtBalconyArea");
        this.contFloorHeight.setName("contFloorHeight");
        this.txtFloorHeight.setName("txtFloorHeight");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.kDSeparator1.setName("kDSeparator1");
        this.kDSeparator2.setName("kDSeparator2");
        this.spiSerialNumber.setName("spiSerialNumber");
        this.spiFloor.setName("spiFloor");
        this.spiUnit.setName("spiUnit");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.txtApportionArea.setName("txtApportionArea");
        this.contActualBuildingArea.setName("contActualBuildingArea");
        this.txtActualBuildingArea.setName("txtActualBuildingArea");
        this.contActualRoomArea.setName("contActualRoomArea");
        this.txtActualRoomArea.setName("txtActualRoomArea");
        this.f7Direction.setName("f7Direction");
        this.f7Sight.setName("f7Sight");
        this.contProductType.setName("contProductType");
        this.f7ProductType.setName("f7ProductType");
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
        // pnlGraph
        // txtNumber
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // txtProject		
        this.txtProject.setEnabled(false);
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
        // txtSubarea		
        this.txtSubarea.setEnabled(false);
        // contSerialNumber		
        this.contSerialNumber.setBoundLabelText(resHelper.getString("contSerialNumber.boundLabelText"));		
        this.contSerialNumber.setBoundLabelLength(100);		
        this.contSerialNumber.setBoundLabelUnderline(true);
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelLength(100);		
        this.contBuildingArea.setBoundLabelUnderline(true);
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelLength(100);		
        this.contRoomArea.setBoundLabelUnderline(true);
        // contApportionArea		
        this.contApportionArea.setBoundLabelText(resHelper.getString("contApportionArea.boundLabelText"));		
        this.contApportionArea.setBoundLabelLength(100);		
        this.contApportionArea.setBoundLabelUnderline(true);
        // contFace		
        this.contFace.setBoundLabelText(resHelper.getString("contFace.boundLabelText"));		
        this.contFace.setBoundLabelLength(100);		
        this.contFace.setBoundLabelUnderline(true);
        // contOutlook		
        this.contOutlook.setBoundLabelText(resHelper.getString("contOutlook.boundLabelText"));		
        this.contOutlook.setBoundLabelLength(100);		
        this.contOutlook.setBoundLabelUnderline(true);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // txtBuilding		
        this.txtBuilding.setEnabled(false);
        // contRoomModel		
        this.contRoomModel.setBoundLabelText(resHelper.getString("contRoomModel.boundLabelText"));		
        this.contRoomModel.setBoundLabelLength(100);		
        this.contRoomModel.setBoundLabelUnderline(true);
        // contHouseProperty		
        this.contHouseProperty.setBoundLabelText(resHelper.getString("contHouseProperty.boundLabelText"));		
        this.contHouseProperty.setBoundLabelLength(100);		
        this.contHouseProperty.setBoundLabelUnderline(true);
        // f7RoomModel		
        this.f7RoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");
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
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);
        // txtCompany		
        this.txtCompany.setEnabled(false);
        // comboHouseProperty		
        this.comboHouseProperty.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.HousePropertyEnum").toArray());
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
        // contBalconyArea		
        this.contBalconyArea.setBoundLabelText(resHelper.getString("contBalconyArea.boundLabelText"));		
        this.contBalconyArea.setBoundLabelLength(100);		
        this.contBalconyArea.setBoundLabelUnderline(true);
        // txtBalconyArea		
        this.txtBalconyArea.setDataType(1);
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
        // contFloorHeight		
        this.contFloorHeight.setBoundLabelText(resHelper.getString("contFloorHeight.boundLabelText"));		
        this.contFloorHeight.setBoundLabelLength(100);		
        this.contFloorHeight.setBoundLabelUnderline(true);
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
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(100);		
        this.contBuildingProperty.setBoundLabelUnderline(true);
        // f7BuildingProperty		
        this.f7BuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");
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
        // kDSeparator1
        // kDSeparator2
        // spiSerialNumber		
        this.spiSerialNumber.setEnabled(false);
        // spiFloor		
        this.spiFloor.setEnabled(false);
        // spiUnit		
        this.spiUnit.setEnabled(false);
        // txtBuildingArea		
        this.txtBuildingArea.setDataType(1);
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
        // contActualBuildingArea		
        this.contActualBuildingArea.setBoundLabelText(resHelper.getString("contActualBuildingArea.boundLabelText"));		
        this.contActualBuildingArea.setBoundLabelUnderline(true);		
        this.contActualBuildingArea.setBoundLabelLength(100);
        // txtActualBuildingArea		
        this.txtActualBuildingArea.setDataType(1);
        // contActualRoomArea		
        this.contActualRoomArea.setBoundLabelText(resHelper.getString("contActualRoomArea.boundLabelText"));		
        this.contActualRoomArea.setBoundLabelUnderline(true);		
        this.contActualRoomArea.setBoundLabelLength(100);
        // txtActualRoomArea		
        this.txtActualRoomArea.setDataType(1);
        // f7Direction		
        this.f7Direction.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");
        // f7Sight		
        this.f7Sight.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SightRequirementQuery");
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);
        // f7ProductType		
        this.f7ProductType.setDisplayFormat("$name$");		
        this.f7ProductType.setEditFormat("$number$");		
        this.f7ProductType.setCommitFormat("$number$");		
        this.f7ProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");
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
        btnYes.setBounds(new Rectangle(186, 567, 73, 21));
        this.add(btnYes, new KDLayout.Constraints(186, 567, 73, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnNo.setBounds(new Rectangle(369, 567, 73, 21));
        this.add(btnNo, new KDLayout.Constraints(369, 567, 73, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlGraph.setBounds(new Rectangle(14, 25, 590, 532));
        this.add(pnlGraph, new KDLayout.Constraints(14, 25, 590, 532, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(621, 139, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(621, 139, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contProject.setBounds(new Rectangle(621, 62, 270, 19));
        this.add(contProject, new KDLayout.Constraints(621, 62, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFloor.setBounds(new Rectangle(621, 189, 270, 19));
        this.add(contFloor, new KDLayout.Constraints(621, 189, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contUnit.setBounds(new Rectangle(621, 163, 270, 19));
        this.add(contUnit, new KDLayout.Constraints(621, 163, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSubarea.setBounds(new Rectangle(621, 88, 270, 19));
        this.add(contSubarea, new KDLayout.Constraints(621, 88, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSerialNumber.setBounds(new Rectangle(621, 214, 270, 19));
        this.add(contSerialNumber, new KDLayout.Constraints(621, 214, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuildingArea.setBounds(new Rectangle(621, 248, 270, 19));
        this.add(contBuildingArea, new KDLayout.Constraints(621, 248, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRoomArea.setBounds(new Rectangle(621, 275, 270, 19));
        this.add(contRoomArea, new KDLayout.Constraints(621, 275, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contApportionArea.setBounds(new Rectangle(621, 301, 270, 19));
        this.add(contApportionArea, new KDLayout.Constraints(621, 301, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFace.setBounds(new Rectangle(621, 437, 270, 19));
        this.add(contFace, new KDLayout.Constraints(621, 437, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOutlook.setBounds(new Rectangle(621, 462, 270, 19));
        this.add(contOutlook, new KDLayout.Constraints(621, 462, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuilding.setBounds(new Rectangle(621, 114, 270, 19));
        this.add(contBuilding, new KDLayout.Constraints(621, 114, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRoomModel.setBounds(new Rectangle(621, 487, 270, 19));
        this.add(contRoomModel, new KDLayout.Constraints(621, 487, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contHouseProperty.setBounds(new Rectangle(621, 539, 270, 19));
        this.add(contHouseProperty, new KDLayout.Constraints(621, 539, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCompany.setBounds(new Rectangle(621, 37, 270, 19));
        this.add(contCompany, new KDLayout.Constraints(621, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBalconyArea.setBounds(new Rectangle(621, 327, 270, 19));
        this.add(contBalconyArea, new KDLayout.Constraints(621, 327, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFloorHeight.setBounds(new Rectangle(621, 413, 270, 19));
        this.add(contFloorHeight, new KDLayout.Constraints(621, 413, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuildingProperty.setBounds(new Rectangle(621, 513, 270, 19));
        this.add(contBuildingProperty, new KDLayout.Constraints(621, 513, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator1.setBounds(new Rectangle(611, 238, 287, 10));
        this.add(kDSeparator1, new KDLayout.Constraints(611, 238, 287, 10, 0));
        kDSeparator2.setBounds(new Rectangle(611, 406, 290, 10));
        this.add(kDSeparator2, new KDLayout.Constraints(611, 406, 290, 10, 0));
        contActualBuildingArea.setBounds(new Rectangle(621, 353, 270, 19));
        this.add(contActualBuildingArea, new KDLayout.Constraints(621, 353, 270, 19, 0));
        contActualRoomArea.setBounds(new Rectangle(621, 376, 270, 19));
        this.add(contActualRoomArea, new KDLayout.Constraints(621, 376, 270, 19, 0));
        contProductType.setBounds(new Rectangle(621, 567, 270, 19));
        this.add(contProductType, new KDLayout.Constraints(621, 567, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
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
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomCreateUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomCreateUI");
    }




}