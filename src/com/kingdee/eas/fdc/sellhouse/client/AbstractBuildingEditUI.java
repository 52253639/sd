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
public abstract class AbstractBuildingEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBuildingEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel tabBuildingInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel tabWuyeInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConditionStandard;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Subarea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNetWorkCircs;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiFloorCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEstablishment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFireControl;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCeling;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorExplain;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupportWeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerLiftCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCargoLiftCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLiftExplain;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ConstructPart;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboConditionType;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiUnitCount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCompleteDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox comboBuildingStructure;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConditionType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNetWorkType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboNetWorkType;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiCustomerLiftCount;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiCargoLiftCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommunication;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSubarea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contJoinInDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompleteDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConstructPart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingHeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingTerraArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCodingType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingStructure;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingHeight;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingTerraArea;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtConditionStandard;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtNetWorkCircs;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane3;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtCommunication;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane4;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtFireControl;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane5;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtEstablishment;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane6;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtCeling;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane7;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtFloorExplain;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane8;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtLiftExplain;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane9;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtSupportWeight;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkJoinInDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdministrativeNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ProductType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAdministrativeNumber;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblUnitList;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnitCount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddUnit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveUnit;
    protected com.kingdee.bos.ctrl.swing.KDPanel tabUnitEntry;
    protected com.kingdee.bos.ctrl.swing.KDPanel tabRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spSubFloor;
    protected com.kingdee.bos.ctrl.swing.KDPanel tabBuildingFloorEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable floorTable;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractBuildingEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBuildingEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.tabPanel = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.tabBuildingInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tabWuyeInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contProjectNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtProjectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contConditionStandard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Subarea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contNetWorkCircs = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiFloorCount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.contEstablishment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFireControl = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCeling = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorExplain = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupportWeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomerLiftCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCargoLiftCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLiftExplain = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7ConstructPart = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboConditionType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.spiUnitCount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.pkCompleteDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboBuildingStructure = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contConditionType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNetWorkType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboNetWorkType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.spiCustomerLiftCount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiCargoLiftCount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.contCommunication = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSubarea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contJoinInDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompleteDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConstructPart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingHeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingTerraArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboCodingType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contBuildingStructure = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBuildingHeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBuildingTerraArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtConditionStandard = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtNetWorkCircs = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane3 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtCommunication = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane4 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtFireControl = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane5 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtEstablishment = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane6 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtCeling = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane7 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtFloorExplain = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane8 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtLiftExplain = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane9 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtSupportWeight = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.pkJoinInDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdministrativeNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7ProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAdministrativeNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tblRoomModel = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddRoomModel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteRoomModel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.tblUnitList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contUnitCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAddUnit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveUnit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tabUnitEntry = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tabRoomModel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spSubFloor = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.tabBuildingFloorEntry = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.floorTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tabPanel.setName("tabPanel");
        this.tabBuildingInfo.setName("tabBuildingInfo");
        this.tabWuyeInfo.setName("tabWuyeInfo");
        this.contProjectNumber.setName("contProjectNumber");
        this.txtProjectNumber.setName("txtProjectNumber");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contProjectName.setName("contProjectName");
        this.txtProjectName.setName("txtProjectName");
        this.contName.setName("contName");
        this.txtName.setName("txtName");
        this.contConditionStandard.setName("contConditionStandard");
        this.f7Subarea.setName("f7Subarea");
        this.contNetWorkCircs.setName("contNetWorkCircs");
        this.spiFloorCount.setName("spiFloorCount");
        this.contEstablishment.setName("contEstablishment");
        this.contFireControl.setName("contFireControl");
        this.contCeling.setName("contCeling");
        this.contFloorExplain.setName("contFloorExplain");
        this.contSupportWeight.setName("contSupportWeight");
        this.contCustomerLiftCount.setName("contCustomerLiftCount");
        this.contCargoLiftCount.setName("contCargoLiftCount");
        this.contLiftExplain.setName("contLiftExplain");
        this.f7ConstructPart.setName("f7ConstructPart");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.comboConditionType.setName("comboConditionType");
        this.spiUnitCount.setName("spiUnitCount");
        this.pkCompleteDate.setName("pkCompleteDate");
        this.comboBuildingStructure.setName("comboBuildingStructure");
        this.contConditionType.setName("contConditionType");
        this.contNetWorkType.setName("contNetWorkType");
        this.comboNetWorkType.setName("comboNetWorkType");
        this.spiCustomerLiftCount.setName("spiCustomerLiftCount");
        this.spiCargoLiftCount.setName("spiCargoLiftCount");
        this.contCommunication.setName("contCommunication");
        this.contSubarea.setName("contSubarea");
        this.contFloorCount.setName("contFloorCount");
        this.contJoinInDate.setName("contJoinInDate");
        this.contCompleteDate.setName("contCompleteDate");
        this.contConstructPart.setName("contConstructPart");
        this.contBuildingHeight.setName("contBuildingHeight");
        this.contBuildingTerraArea.setName("contBuildingTerraArea");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.comboCodingType.setName("comboCodingType");
        this.contBuildingStructure.setName("contBuildingStructure");
        this.txtBuildingHeight.setName("txtBuildingHeight");
        this.txtBuildingTerraArea.setName("txtBuildingTerraArea");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtConditionStandard.setName("txtConditionStandard");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtNetWorkCircs.setName("txtNetWorkCircs");
        this.kDScrollPane3.setName("kDScrollPane3");
        this.txtCommunication.setName("txtCommunication");
        this.kDScrollPane4.setName("kDScrollPane4");
        this.txtFireControl.setName("txtFireControl");
        this.kDScrollPane5.setName("kDScrollPane5");
        this.txtEstablishment.setName("txtEstablishment");
        this.kDScrollPane6.setName("kDScrollPane6");
        this.txtCeling.setName("txtCeling");
        this.kDScrollPane7.setName("kDScrollPane7");
        this.txtFloorExplain.setName("txtFloorExplain");
        this.kDScrollPane8.setName("kDScrollPane8");
        this.txtLiftExplain.setName("txtLiftExplain");
        this.kDScrollPane9.setName("kDScrollPane9");
        this.txtSupportWeight.setName("txtSupportWeight");
        this.pkJoinInDate.setName("pkJoinInDate");
        this.contProductType.setName("contProductType");
        this.contAdministrativeNumber.setName("contAdministrativeNumber");
        this.f7ProductType.setName("f7ProductType");
        this.txtAdministrativeNumber.setName("txtAdministrativeNumber");
        this.tblRoomModel.setName("tblRoomModel");
        this.btnAddRoomModel.setName("btnAddRoomModel");
        this.btnDeleteRoomModel.setName("btnDeleteRoomModel");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.tblUnitList.setName("tblUnitList");
        this.contUnitCount.setName("contUnitCount");
        this.btnAddUnit.setName("btnAddUnit");
        this.btnRemoveUnit.setName("btnRemoveUnit");
        this.tabUnitEntry.setName("tabUnitEntry");
        this.tabRoomModel.setName("tabRoomModel");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.spSubFloor.setName("spSubFloor");
        this.tabBuildingFloorEntry.setName("tabBuildingFloorEntry");
        this.floorTable.setName("floorTable");
        // CoreUI		
        this.setPreferredSize(new Dimension(650,450));
        // tabPanel
        // tabBuildingInfo		
        this.tabBuildingInfo.setBorder(null);
        // tabWuyeInfo
        // contProjectNumber		
        this.contProjectNumber.setBoundLabelText(resHelper.getString("contProjectNumber.boundLabelText"));		
        this.contProjectNumber.setBoundLabelLength(100);		
        this.contProjectNumber.setBoundLabelUnderline(true);
        // txtProjectNumber
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber
        // contProjectName		
        this.contProjectName.setBoundLabelText(resHelper.getString("contProjectName.boundLabelText"));		
        this.contProjectName.setBoundLabelLength(100);		
        this.contProjectName.setBoundLabelUnderline(true);
        // txtProjectName
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // txtName
        // contConditionStandard		
        this.contConditionStandard.setBoundLabelText(resHelper.getString("contConditionStandard.boundLabelText"));		
        this.contConditionStandard.setBoundLabelUnderline(true);		
        this.contConditionStandard.setBoundLabelLength(100);
        // f7Subarea		
        this.f7Subarea.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SubareaQuery");		
        this.f7Subarea.setCommitFormat("$number$");		
        this.f7Subarea.setEditFormat("$number$");		
        this.f7Subarea.setDisplayFormat("$name$");
        // contNetWorkCircs		
        this.contNetWorkCircs.setBoundLabelText(resHelper.getString("contNetWorkCircs.boundLabelText"));		
        this.contNetWorkCircs.setBoundLabelLength(100);		
        this.contNetWorkCircs.setBoundLabelUnderline(true);
        // spiFloorCount
        this.spiFloorCount.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spiFloorCount_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.spiFloorCount.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    spiFloorCount_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contEstablishment		
        this.contEstablishment.setBoundLabelText(resHelper.getString("contEstablishment.boundLabelText"));		
        this.contEstablishment.setBoundLabelLength(100);		
        this.contEstablishment.setBoundLabelUnderline(true);
        // contFireControl		
        this.contFireControl.setBoundLabelText(resHelper.getString("contFireControl.boundLabelText"));		
        this.contFireControl.setBoundLabelLength(100);		
        this.contFireControl.setBoundLabelUnderline(true);
        // contCeling		
        this.contCeling.setBoundLabelText(resHelper.getString("contCeling.boundLabelText"));		
        this.contCeling.setBoundLabelLength(100);		
        this.contCeling.setBoundLabelUnderline(true);
        // contFloorExplain		
        this.contFloorExplain.setBoundLabelText(resHelper.getString("contFloorExplain.boundLabelText"));		
        this.contFloorExplain.setBoundLabelLength(100);		
        this.contFloorExplain.setBoundLabelUnderline(true);
        // contSupportWeight		
        this.contSupportWeight.setBoundLabelText(resHelper.getString("contSupportWeight.boundLabelText"));		
        this.contSupportWeight.setBoundLabelLength(100);		
        this.contSupportWeight.setBoundLabelUnderline(true);
        // contCustomerLiftCount		
        this.contCustomerLiftCount.setBoundLabelText(resHelper.getString("contCustomerLiftCount.boundLabelText"));		
        this.contCustomerLiftCount.setBoundLabelLength(100);		
        this.contCustomerLiftCount.setBoundLabelUnderline(true);
        // contCargoLiftCount		
        this.contCargoLiftCount.setBoundLabelText(resHelper.getString("contCargoLiftCount.boundLabelText"));		
        this.contCargoLiftCount.setBoundLabelLength(100);		
        this.contCargoLiftCount.setBoundLabelUnderline(true);
        // contLiftExplain		
        this.contLiftExplain.setBoundLabelText(resHelper.getString("contLiftExplain.boundLabelText"));		
        this.contLiftExplain.setBoundLabelUnderline(true);		
        this.contLiftExplain.setBoundLabelLength(100);
        // f7ConstructPart		
        this.f7ConstructPart.setDisplayFormat("$name$");		
        this.f7ConstructPart.setEditFormat("$number$");		
        this.f7ConstructPart.setCommitFormat("$number$");		
        this.f7ConstructPart.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");
        // f7BuildingProperty		
        this.f7BuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");		
        this.f7BuildingProperty.setCommitFormat("$number$");		
        this.f7BuildingProperty.setEditFormat("$number$");		
        this.f7BuildingProperty.setDisplayFormat("$name$");		
        this.f7BuildingProperty.setVisible(false);
        // comboConditionType		
        this.comboConditionType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ConditionTypeEnum").toArray());
        // spiUnitCount		
        this.spiUnitCount.setEnabled(false);
        // pkCompleteDate
        // comboBuildingStructure		
        this.comboBuildingStructure.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingStructureQuery");		
        this.comboBuildingStructure.setCommitFormat("$number$");		
        this.comboBuildingStructure.setEditFormat("$number$");		
        this.comboBuildingStructure.setDisplayFormat("$name$");
        // contConditionType		
        this.contConditionType.setBoundLabelText(resHelper.getString("contConditionType.boundLabelText"));		
        this.contConditionType.setBoundLabelLength(100);		
        this.contConditionType.setBoundLabelUnderline(true);
        // contNetWorkType		
        this.contNetWorkType.setBoundLabelText(resHelper.getString("contNetWorkType.boundLabelText"));		
        this.contNetWorkType.setBoundLabelUnderline(true);		
        this.contNetWorkType.setBoundLabelLength(100);
        // comboNetWorkType		
        this.comboNetWorkType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.NetWorkTypeEnum").toArray());
        // spiCustomerLiftCount
        // spiCargoLiftCount
        // contCommunication		
        this.contCommunication.setBoundLabelText(resHelper.getString("contCommunication.boundLabelText"));		
        this.contCommunication.setBoundLabelUnderline(true);		
        this.contCommunication.setBoundLabelLength(100);
        // contSubarea		
        this.contSubarea.setBoundLabelText(resHelper.getString("contSubarea.boundLabelText"));		
        this.contSubarea.setBoundLabelLength(100);		
        this.contSubarea.setBoundLabelUnderline(true);
        // contFloorCount		
        this.contFloorCount.setBoundLabelText(resHelper.getString("contFloorCount.boundLabelText"));		
        this.contFloorCount.setBoundLabelLength(100);		
        this.contFloorCount.setBoundLabelUnderline(true);
        // contJoinInDate		
        this.contJoinInDate.setBoundLabelText(resHelper.getString("contJoinInDate.boundLabelText"));		
        this.contJoinInDate.setBoundLabelLength(100);		
        this.contJoinInDate.setBoundLabelUnderline(true);
        // contCompleteDate		
        this.contCompleteDate.setBoundLabelText(resHelper.getString("contCompleteDate.boundLabelText"));		
        this.contCompleteDate.setBoundLabelLength(100);		
        this.contCompleteDate.setBoundLabelUnderline(true);
        // contConstructPart		
        this.contConstructPart.setBoundLabelText(resHelper.getString("contConstructPart.boundLabelText"));		
        this.contConstructPart.setBoundLabelLength(100);		
        this.contConstructPart.setBoundLabelUnderline(true);
        // contBuildingHeight		
        this.contBuildingHeight.setBoundLabelText(resHelper.getString("contBuildingHeight.boundLabelText"));		
        this.contBuildingHeight.setBoundLabelLength(100);		
        this.contBuildingHeight.setBoundLabelUnderline(true);
        // contBuildingTerraArea		
        this.contBuildingTerraArea.setBoundLabelText(resHelper.getString("contBuildingTerraArea.boundLabelText"));		
        this.contBuildingTerraArea.setBoundLabelLength(100);		
        this.contBuildingTerraArea.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // comboCodingType		
        this.comboCodingType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CodingTypeEnum").toArray());
        this.comboCodingType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comboCodingType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contBuildingStructure		
        this.contBuildingStructure.setBoundLabelText(resHelper.getString("contBuildingStructure.boundLabelText"));		
        this.contBuildingStructure.setBoundLabelLength(100);		
        this.contBuildingStructure.setBoundLabelUnderline(true);
        // txtBuildingHeight		
        this.txtBuildingHeight.setDataType(4);
        // txtBuildingTerraArea
        // kDScrollPane1
        // txtConditionStandard		
        this.txtConditionStandard.setMaxLength(255);
        // kDScrollPane2
        // txtNetWorkCircs		
        this.txtNetWorkCircs.setMaxLength(255);
        // kDScrollPane3
        // txtCommunication		
        this.txtCommunication.setMaxLength(255);
        // kDScrollPane4
        // txtFireControl		
        this.txtFireControl.setMaxLength(255);
        // kDScrollPane5
        // txtEstablishment		
        this.txtEstablishment.setMaxLength(255);
        // kDScrollPane6
        // txtCeling		
        this.txtCeling.setMaxLength(255);
        // kDScrollPane7
        // txtFloorExplain		
        this.txtFloorExplain.setMaxLength(255);
        // kDScrollPane8
        // txtLiftExplain		
        this.txtLiftExplain.setMaxLength(255);
        // kDScrollPane9
        // txtSupportWeight		
        this.txtSupportWeight.setMaxLength(255);
        // pkJoinInDate
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);
        // contAdministrativeNumber		
        this.contAdministrativeNumber.setBoundLabelText(resHelper.getString("contAdministrativeNumber.boundLabelText"));		
        this.contAdministrativeNumber.setBoundLabelLength(100);		
        this.contAdministrativeNumber.setBoundLabelUnderline(true);
        // f7ProductType		
        this.f7ProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");		
        this.f7ProductType.setCommitFormat("$number$");		
        this.f7ProductType.setDisplayFormat("$name$");		
        this.f7ProductType.setEditFormat("$number$");
        // txtAdministrativeNumber		
        this.txtAdministrativeNumber.setMaxLength(80);
        // tblRoomModel
		String tblRoomModelStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"modelType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{modelType}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblRoomModel.setFormatXml(resHelper.translateString("tblRoomModel",tblRoomModelStrXML));

        

        // btnAddRoomModel		
        this.btnAddRoomModel.setText(resHelper.getString("btnAddRoomModel.text"));
        this.btnAddRoomModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddRoomModel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDeleteRoomModel		
        this.btnDeleteRoomModel.setText(resHelper.getString("btnDeleteRoomModel.text"));
        this.btnDeleteRoomModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeleteRoomModel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDTabbedPane1
        // tblUnitList
		String tblUnitListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"des\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblUnitList.setFormatXml(resHelper.translateString("tblUnitList",tblUnitListStrXML));

        

        // contUnitCount		
        this.contUnitCount.setBoundLabelText(resHelper.getString("contUnitCount.boundLabelText"));		
        this.contUnitCount.setBoundLabelLength(100);		
        this.contUnitCount.setBoundLabelUnderline(true);
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
        // btnRemoveUnit		
        this.btnRemoveUnit.setText(resHelper.getString("btnRemoveUnit.text"));
        this.btnRemoveUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRemoveUnit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tabUnitEntry
        // tabRoomModel
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(100);		
        this.contBuildingProperty.setBoundLabelUnderline(true);		
        this.contBuildingProperty.setVisible(false);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // spSubFloor		
        this.spSubFloor.setRequired(true);
        this.spSubFloor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    spSubFloor_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.spSubFloor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spSubFloor_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tabBuildingFloorEntry
        // floorTable
		String floorTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"floor\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"floorAlias\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isEnable\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{floor}</t:Cell><t:Cell>$Resource{floorAlias}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{isEnable}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.floorTable.setFormatXml(resHelper.translateString("floorTable",floorTableStrXML));

        

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
        this.setBounds(new Rectangle(10, 10, 650, 450));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 650, 450));
        tabPanel.setBounds(new Rectangle(10, 10, 629, 435));
        this.add(tabPanel, new KDLayout.Constraints(10, 10, 629, 435, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //tabPanel
        tabPanel.add(tabBuildingInfo, resHelper.getString("tabBuildingInfo.constraints"));
        tabPanel.add(tabWuyeInfo, resHelper.getString("tabWuyeInfo.constraints"));
        //tabBuildingInfo
        tabBuildingInfo.setLayout(null);        contProjectNumber.setBounds(new Rectangle(5, 10, 270, 19));
        tabBuildingInfo.add(contProjectNumber, null);
        contNumber.setBounds(new Rectangle(5, 35, 270, 19));
        tabBuildingInfo.add(contNumber, null);
        contProjectName.setBounds(new Rectangle(341, 10, 270, 19));
        tabBuildingInfo.add(contProjectName, null);
        contName.setBounds(new Rectangle(341, 35, 270, 19));
        tabBuildingInfo.add(contName, null);
        contSubarea.setBounds(new Rectangle(5, 60, 270, 19));
        tabBuildingInfo.add(contSubarea, null);
        contFloorCount.setBounds(new Rectangle(341, 60, 270, 19));
        tabBuildingInfo.add(contFloorCount, null);
        contJoinInDate.setBounds(new Rectangle(5, 135, 270, 19));
        tabBuildingInfo.add(contJoinInDate, null);
        contCompleteDate.setBounds(new Rectangle(341, 110, 270, 19));
        tabBuildingInfo.add(contCompleteDate, null);
        contConstructPart.setBounds(new Rectangle(5, 160, 270, 19));
        tabBuildingInfo.add(contConstructPart, null);
        contBuildingHeight.setBounds(new Rectangle(341, 135, 270, 19));
        tabBuildingInfo.add(contBuildingHeight, null);
        contBuildingTerraArea.setBounds(new Rectangle(341, 160, 270, 19));
        tabBuildingInfo.add(contBuildingTerraArea, null);
        kDLabelContainer1.setBounds(new Rectangle(5, 185, 270, 19));
        tabBuildingInfo.add(kDLabelContainer1, null);
        contBuildingStructure.setBounds(new Rectangle(341, 188, 270, 19));
        tabBuildingInfo.add(contBuildingStructure, null);
        contProductType.setBounds(new Rectangle(5, 110, 270, 19));
        tabBuildingInfo.add(contProductType, null);
        contAdministrativeNumber.setBounds(new Rectangle(341, 85, 270, 19));
        tabBuildingInfo.add(contAdministrativeNumber, null);
        kDTabbedPane1.setBounds(new Rectangle(5, 216, 612, 184));
        tabBuildingInfo.add(kDTabbedPane1, null);
        contBuildingProperty.setBounds(new Rectangle(304, 200, 270, 19));
        tabBuildingInfo.add(contBuildingProperty, null);
        kDLabelContainer2.setBounds(new Rectangle(6, 85, 270, 19));
        tabBuildingInfo.add(kDLabelContainer2, null);
        //contProjectNumber
        contProjectNumber.setBoundEditor(txtProjectNumber);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contProjectName
        contProjectName.setBoundEditor(txtProjectName);
        //contName
        contName.setBoundEditor(txtName);
        //contSubarea
        contSubarea.setBoundEditor(f7Subarea);
        //contFloorCount
        contFloorCount.setBoundEditor(spiFloorCount);
        //contJoinInDate
        contJoinInDate.setBoundEditor(pkJoinInDate);
        //contCompleteDate
        contCompleteDate.setBoundEditor(pkCompleteDate);
        //contConstructPart
        contConstructPart.setBoundEditor(f7ConstructPart);
        //contBuildingHeight
        contBuildingHeight.setBoundEditor(txtBuildingHeight);
        //contBuildingTerraArea
        contBuildingTerraArea.setBoundEditor(txtBuildingTerraArea);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(comboCodingType);
        //contBuildingStructure
        contBuildingStructure.setBoundEditor(comboBuildingStructure);
        //contProductType
        contProductType.setBoundEditor(f7ProductType);
        //contAdministrativeNumber
        contAdministrativeNumber.setBoundEditor(txtAdministrativeNumber);
        //kDTabbedPane1
        kDTabbedPane1.add(tabUnitEntry, resHelper.getString("tabUnitEntry.constraints"));
        kDTabbedPane1.add(tabRoomModel, resHelper.getString("tabRoomModel.constraints"));
        kDTabbedPane1.add(tabBuildingFloorEntry, resHelper.getString("tabBuildingFloorEntry.constraints"));
        //tabUnitEntry
        tabUnitEntry.setLayout(null);        tblUnitList.setBounds(new Rectangle(4, 30, 599, 117));
        tabUnitEntry.add(tblUnitList, null);
        contUnitCount.setBounds(new Rectangle(5, 7, 270, 19));
        tabUnitEntry.add(contUnitCount, null);
        btnAddUnit.setBounds(new Rectangle(418, 7, 85, 19));
        tabUnitEntry.add(btnAddUnit, null);
        btnRemoveUnit.setBounds(new Rectangle(516, 7, 85, 19));
        tabUnitEntry.add(btnRemoveUnit, null);
        //contUnitCount
        contUnitCount.setBoundEditor(spiUnitCount);
        //tabRoomModel
        tabRoomModel.setLayout(null);        tblRoomModel.setBounds(new Rectangle(7, 30, 596, 122));
        tabRoomModel.add(tblRoomModel, null);
        btnAddRoomModel.setBounds(new Rectangle(424, 6, 85, 19));
        tabRoomModel.add(btnAddRoomModel, null);
        btnDeleteRoomModel.setBounds(new Rectangle(520, 6, 82, 20));
        tabRoomModel.add(btnDeleteRoomModel, null);
        //tabBuildingFloorEntry
        tabBuildingFloorEntry.setLayout(new KDLayout());
        tabBuildingFloorEntry.putClientProperty("OriginalBounds", new Rectangle(0, 0, 611, 151));        floorTable.setBounds(new Rectangle(5, 3, 599, 147));
        tabBuildingFloorEntry.add(floorTable, new KDLayout.Constraints(5, 3, 599, 147, 0));
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(f7BuildingProperty);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(spSubFloor);
        //tabWuyeInfo
        tabWuyeInfo.setLayout(null);        contConditionStandard.setBounds(new Rectangle(6, 36, 275, 45));
        tabWuyeInfo.add(contConditionStandard, null);
        contNetWorkCircs.setBounds(new Rectangle(339, 35, 274, 45));
        tabWuyeInfo.add(contNetWorkCircs, null);
        contEstablishment.setBounds(new Rectangle(5, 147, 279, 44));
        tabWuyeInfo.add(contEstablishment, null);
        contFireControl.setBounds(new Rectangle(339, 88, 274, 47));
        tabWuyeInfo.add(contFireControl, null);
        contCeling.setBounds(new Rectangle(339, 144, 274, 45));
        tabWuyeInfo.add(contCeling, null);
        contFloorExplain.setBounds(new Rectangle(3, 203, 609, 29));
        tabWuyeInfo.add(contFloorExplain, null);
        contSupportWeight.setBounds(new Rectangle(339, 273, 270, 48));
        tabWuyeInfo.add(contSupportWeight, null);
        contCustomerLiftCount.setBounds(new Rectangle(4, 247, 278, 19));
        tabWuyeInfo.add(contCustomerLiftCount, null);
        contCargoLiftCount.setBounds(new Rectangle(339, 245, 270, 19));
        tabWuyeInfo.add(contCargoLiftCount, null);
        contLiftExplain.setBounds(new Rectangle(4, 277, 281, 47));
        tabWuyeInfo.add(contLiftExplain, null);
        contConditionType.setBounds(new Rectangle(7, 7, 275, 19));
        tabWuyeInfo.add(contConditionType, null);
        contNetWorkType.setBounds(new Rectangle(339, 6, 276, 19));
        tabWuyeInfo.add(contNetWorkType, null);
        contCommunication.setBounds(new Rectangle(5, 93, 278, 43));
        tabWuyeInfo.add(contCommunication, null);
        //contConditionStandard
        contConditionStandard.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtConditionStandard, null);
        //contNetWorkCircs
        contNetWorkCircs.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtNetWorkCircs, null);
        //contEstablishment
        contEstablishment.setBoundEditor(kDScrollPane5);
        //kDScrollPane5
        kDScrollPane5.getViewport().add(txtEstablishment, null);
        //contFireControl
        contFireControl.setBoundEditor(kDScrollPane4);
        //kDScrollPane4
        kDScrollPane4.getViewport().add(txtFireControl, null);
        //contCeling
        contCeling.setBoundEditor(kDScrollPane6);
        //kDScrollPane6
        kDScrollPane6.getViewport().add(txtCeling, null);
        //contFloorExplain
        contFloorExplain.setBoundEditor(kDScrollPane7);
        //kDScrollPane7
        kDScrollPane7.getViewport().add(txtFloorExplain, null);
        //contSupportWeight
        contSupportWeight.setBoundEditor(kDScrollPane9);
        //kDScrollPane9
        kDScrollPane9.getViewport().add(txtSupportWeight, null);
        //contCustomerLiftCount
        contCustomerLiftCount.setBoundEditor(spiCustomerLiftCount);
        //contCargoLiftCount
        contCargoLiftCount.setBoundEditor(spiCargoLiftCount);
        //contLiftExplain
        contLiftExplain.setBoundEditor(kDScrollPane8);
        //kDScrollPane8
        kDScrollPane8.getViewport().add(txtLiftExplain, null);
        //contConditionType
        contConditionType.setBoundEditor(comboConditionType);
        //contNetWorkType
        contNetWorkType.setBoundEditor(comboNetWorkType);
        //contCommunication
        contCommunication.setBoundEditor(kDScrollPane3);
        //kDScrollPane3
        kDScrollPane3.getViewport().add(txtCommunication, null);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.BuildingEditUIHandler";
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
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output spiFloorCount_stateChanged method
     */
    protected void spiFloorCount_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output spiFloorCount_propertyChange method
     */
    protected void spiFloorCount_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output comboCodingType_actionPerformed method
     */
    protected void comboCodingType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddRoomModel_actionPerformed method
     */
    protected void btnAddRoomModel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDeleteRoomModel_actionPerformed method
     */
    protected void btnDeleteRoomModel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddUnit_actionPerformed method
     */
    protected void btnAddUnit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnRemoveUnit_actionPerformed method
     */
    protected void btnRemoveUnit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output spSubFloor_propertyChange method
     */
    protected void spSubFloor_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output spSubFloor_stateChanged method
     */
    protected void spSubFloor_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BuildingEditUI");
    }




}