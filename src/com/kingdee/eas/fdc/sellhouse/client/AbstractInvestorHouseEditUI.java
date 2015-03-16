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
public abstract class AbstractInvestorHouseEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInvestorHouseEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldEnrollName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOldEnrollName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrderLocation;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrderLocation;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrderSourceClass;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboOrderSourceClass;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAssignBuilding;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAssignBuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAssignRoom;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAssignRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAreaDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAreaDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriceDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPriceDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChargeComplexion;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtChargeComplexion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOwnerShipState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboOwnerShipState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOwnerShipNew;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboOwnerShipNew;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHabitationArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHabitationArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalesman;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSalesman;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBooker;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBooker;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWuyeActuality;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWuyeActuality;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvestorHouseSource;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHouseResource;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDPanel investorHousePanel;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblLinkPerson;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelLine;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAddLine;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuDelLine;
    protected com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo editData = null;
    protected ActionAddLine actionAddLine = null;
    protected ActionDelLine actionDelLine = null;
    /**
     * output class constructor
     */
    public AbstractInvestorHouseEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInvestorHouseEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddLine
        this.actionAddLine = new ActionAddLine(this);
        getActionManager().registerAction("actionAddLine", actionAddLine);
         this.actionAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelLine
        this.actionDelLine = new ActionDelLine(this);
        getActionManager().registerAction("actionDelLine", actionDelLine);
         this.actionDelLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contOldEnrollName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtOldEnrollName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contOrderLocation = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtOrderLocation = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contOrderSourceClass = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboOrderSourceClass = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contAssignBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAssignBuilding = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contAssignRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAssignRoom = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contAreaDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAreaDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contPriceDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPriceDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contChargeComplexion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtChargeComplexion = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contOwnerShipState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboOwnerShipState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contOwnerShipNew = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboOwnerShipNew = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contHabitationArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHabitationArea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSalesman = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtSalesman = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtBuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBooker = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtBooker = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contWuyeActuality = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtWuyeActuality = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contInvestorHouseSource = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHouseResource = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.investorHousePanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.tblLinkPerson = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblTrackRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuAddLine = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuDelLine = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contName.setName("contName");
        this.txtName.setName("txtName");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contOldEnrollName.setName("contOldEnrollName");
        this.txtOldEnrollName.setName("txtOldEnrollName");
        this.contOrderLocation.setName("contOrderLocation");
        this.txtOrderLocation.setName("txtOrderLocation");
        this.contOrderSourceClass.setName("contOrderSourceClass");
        this.comboOrderSourceClass.setName("comboOrderSourceClass");
        this.contAssignBuilding.setName("contAssignBuilding");
        this.txtAssignBuilding.setName("txtAssignBuilding");
        this.contAssignRoom.setName("contAssignRoom");
        this.txtAssignRoom.setName("txtAssignRoom");
        this.contAreaDescription.setName("contAreaDescription");
        this.txtAreaDescription.setName("txtAreaDescription");
        this.contPriceDescription.setName("contPriceDescription");
        this.txtPriceDescription.setName("txtPriceDescription");
        this.contChargeComplexion.setName("contChargeComplexion");
        this.txtChargeComplexion.setName("txtChargeComplexion");
        this.contOwnerShipState.setName("contOwnerShipState");
        this.comboOwnerShipState.setName("comboOwnerShipState");
        this.contOwnerShipNew.setName("contOwnerShipNew");
        this.comboOwnerShipNew.setName("comboOwnerShipNew");
        this.contBizDate.setName("contBizDate");
        this.pkBizDate.setName("pkBizDate");
        this.contHabitationArea.setName("contHabitationArea");
        this.prmtHabitationArea.setName("prmtHabitationArea");
        this.contSalesman.setName("contSalesman");
        this.prmtSalesman.setName("prmtSalesman");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.prmtBuildingProperty.setName("prmtBuildingProperty");
        this.contBooker.setName("contBooker");
        this.prmtBooker.setName("prmtBooker");
        this.contWuyeActuality.setName("contWuyeActuality");
        this.txtWuyeActuality.setName("txtWuyeActuality");
        this.contInvestorHouseSource.setName("contInvestorHouseSource");
        this.contDescription.setName("contDescription");
        this.prmtHouseResource.setName("prmtHouseResource");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.investorHousePanel.setName("investorHousePanel");
        this.pkCreateTime.setName("pkCreateTime");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.tblLinkPerson.setName("tblLinkPerson");
        this.tblTrackRecord.setName("tblTrackRecord");
        this.btnAddLine.setName("btnAddLine");
        this.btnDelLine.setName("btnDelLine");
        this.menuAddLine.setName("menuAddLine");
        this.menuDelLine.setName("menuDelLine");
        // CoreUI
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");		
        this.prmtCreator.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // txtName		
        this.txtName.setMaxLength(80);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // contOldEnrollName		
        this.contOldEnrollName.setBoundLabelText(resHelper.getString("contOldEnrollName.boundLabelText"));		
        this.contOldEnrollName.setBoundLabelLength(100);		
        this.contOldEnrollName.setBoundLabelUnderline(true);
        // txtOldEnrollName		
        this.txtOldEnrollName.setMaxLength(80);
        // contOrderLocation		
        this.contOrderLocation.setBoundLabelText(resHelper.getString("contOrderLocation.boundLabelText"));		
        this.contOrderLocation.setBoundLabelLength(100);		
        this.contOrderLocation.setBoundLabelUnderline(true);
        // txtOrderLocation		
        this.txtOrderLocation.setMaxLength(80);
        // contOrderSourceClass		
        this.contOrderSourceClass.setBoundLabelText(resHelper.getString("contOrderSourceClass.boundLabelText"));		
        this.contOrderSourceClass.setBoundLabelLength(100);		
        this.contOrderSourceClass.setBoundLabelUnderline(true);
        // comboOrderSourceClass		
        this.comboOrderSourceClass.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.OrderSourceClassEnum").toArray());
        // contAssignBuilding		
        this.contAssignBuilding.setBoundLabelText(resHelper.getString("contAssignBuilding.boundLabelText"));		
        this.contAssignBuilding.setBoundLabelLength(100);		
        this.contAssignBuilding.setBoundLabelUnderline(true);
        // txtAssignBuilding		
        this.txtAssignBuilding.setMaxLength(80);
        // contAssignRoom		
        this.contAssignRoom.setBoundLabelText(resHelper.getString("contAssignRoom.boundLabelText"));		
        this.contAssignRoom.setBoundLabelLength(100);		
        this.contAssignRoom.setBoundLabelUnderline(true);
        // txtAssignRoom		
        this.txtAssignRoom.setMaxLength(80);
        // contAreaDescription		
        this.contAreaDescription.setBoundLabelText(resHelper.getString("contAreaDescription.boundLabelText"));		
        this.contAreaDescription.setBoundLabelLength(100);		
        this.contAreaDescription.setBoundLabelUnderline(true);
        // txtAreaDescription		
        this.txtAreaDescription.setMaxLength(80);
        // contPriceDescription		
        this.contPriceDescription.setBoundLabelText(resHelper.getString("contPriceDescription.boundLabelText"));		
        this.contPriceDescription.setBoundLabelLength(100);		
        this.contPriceDescription.setBoundLabelUnderline(true);
        // txtPriceDescription		
        this.txtPriceDescription.setMaxLength(80);
        // contChargeComplexion		
        this.contChargeComplexion.setBoundLabelText(resHelper.getString("contChargeComplexion.boundLabelText"));		
        this.contChargeComplexion.setBoundLabelLength(100);		
        this.contChargeComplexion.setBoundLabelUnderline(true);
        // txtChargeComplexion		
        this.txtChargeComplexion.setMaxLength(80);
        // contOwnerShipState		
        this.contOwnerShipState.setBoundLabelText(resHelper.getString("contOwnerShipState.boundLabelText"));		
        this.contOwnerShipState.setBoundLabelLength(100);		
        this.contOwnerShipState.setBoundLabelUnderline(true);
        // comboOwnerShipState		
        this.comboOwnerShipState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.OwnerShipStateEnum").toArray());
        // contOwnerShipNew		
        this.contOwnerShipNew.setBoundLabelText(resHelper.getString("contOwnerShipNew.boundLabelText"));		
        this.contOwnerShipNew.setBoundLabelLength(100);		
        this.contOwnerShipNew.setBoundLabelUnderline(true);
        // comboOwnerShipNew		
        this.comboOwnerShipNew.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.OwnerShipNewEnum").toArray());
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // pkBizDate
        // contHabitationArea		
        this.contHabitationArea.setBoundLabelText(resHelper.getString("contHabitationArea.boundLabelText"));		
        this.contHabitationArea.setBoundLabelLength(100);		
        this.contHabitationArea.setBoundLabelUnderline(true);
        // prmtHabitationArea		
        this.prmtHabitationArea.setCommitFormat("$number$");		
        this.prmtHabitationArea.setEditFormat("$number$");		
        this.prmtHabitationArea.setDisplayFormat("$name$");		
        this.prmtHabitationArea.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HabitationAreaQuery");
        // contSalesman		
        this.contSalesman.setBoundLabelText(resHelper.getString("contSalesman.boundLabelText"));		
        this.contSalesman.setBoundLabelLength(100);		
        this.contSalesman.setBoundLabelUnderline(true);
        // prmtSalesman		
        this.prmtSalesman.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtSalesman.setEditFormat("$number$");		
        this.prmtSalesman.setDisplayFormat("$name$");		
        this.prmtSalesman.setCommitFormat("$number$");
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(100);		
        this.contBuildingProperty.setBoundLabelUnderline(true);
        // prmtBuildingProperty		
        this.prmtBuildingProperty.setCommitFormat("$number$");		
        this.prmtBuildingProperty.setEditFormat("$number$");		
        this.prmtBuildingProperty.setDisplayFormat("$name$");		
        this.prmtBuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");
        // contBooker		
        this.contBooker.setBoundLabelText(resHelper.getString("contBooker.boundLabelText"));		
        this.contBooker.setBoundLabelLength(100);		
        this.contBooker.setBoundLabelUnderline(true);
        // prmtBooker		
        this.prmtBooker.setDisplayFormat("$name$");		
        this.prmtBooker.setEditFormat("$number$");		
        this.prmtBooker.setCommitFormat("$number$");		
        this.prmtBooker.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
        // contWuyeActuality		
        this.contWuyeActuality.setBoundLabelText(resHelper.getString("contWuyeActuality.boundLabelText"));		
        this.contWuyeActuality.setBoundLabelLength(100);		
        this.contWuyeActuality.setBoundLabelUnderline(true);
        // txtWuyeActuality		
        this.txtWuyeActuality.setMaxLength(80);
        // contInvestorHouseSource		
        this.contInvestorHouseSource.setBoundLabelText(resHelper.getString("contInvestorHouseSource.boundLabelText"));		
        this.contInvestorHouseSource.setBoundLabelLength(100);		
        this.contInvestorHouseSource.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // prmtHouseResource		
        this.prmtHouseResource.setCommitFormat("$number$");		
        this.prmtHouseResource.setEditFormat("$number$");		
        this.prmtHouseResource.setDisplayFormat("$name$");		
        this.prmtHouseResource.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7InvestorHouseSourceQuery");
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // investorHousePanel		
        this.investorHousePanel.setBorder(null);
        // pkCreateTime
        // kDTabbedPane1
        // tblLinkPerson
		String tblLinkPersonStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"isOwner\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"linkmanType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"paperName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"paperNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{isOwner}</t:Cell><t:Cell>$Resource{linkmanType}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{sex}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{paperName}</t:Cell><t:Cell>$Resource{paperNumber}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblLinkPerson.setFormatXml(resHelper.translateString("tblLinkPerson",tblLinkPersonStrXML));
        this.tblLinkPerson.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    tblLinkPerson_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // tblTrackRecord
		String tblTrackRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"trackDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"trackPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{trackDate}</t:Cell><t:Cell>$Resource{trackPerson}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblTrackRecord.setFormatXml(resHelper.translateString("tblTrackRecord",tblTrackRecordStrXML));

        

        // btnAddLine
        this.btnAddLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddLine.setText(resHelper.getString("btnAddLine.text"));		
        this.btnAddLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // btnDelLine
        this.btnDelLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelLine.setText(resHelper.getString("btnDelLine.text"));		
        this.btnDelLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        // menuAddLine
        this.menuAddLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuAddLine.setText(resHelper.getString("menuAddLine.text"));		
        this.menuAddLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // menuDelLine
        this.menuDelLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuDelLine.setText(resHelper.getString("menuDelLine.text"));		
        this.menuDelLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
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
        this.setBounds(new Rectangle(10, 10, 920, 629));
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(324, 293, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(10, 295, 270, 19));
        this.add(contCreateTime, null);
        contName.setBounds(new Rectangle(324, 7, 270, 19));
        this.add(contName, null);
        contNumber.setBounds(new Rectangle(8, 7, 270, 19));
        this.add(contNumber, null);
        contOldEnrollName.setBounds(new Rectangle(641, 6, 270, 19));
        this.add(contOldEnrollName, null);
        contOrderLocation.setBounds(new Rectangle(325, 41, 270, 19));
        this.add(contOrderLocation, null);
        contOrderSourceClass.setBounds(new Rectangle(641, 40, 270, 19));
        this.add(contOrderSourceClass, null);
        contAssignBuilding.setBounds(new Rectangle(8, 83, 270, 19));
        this.add(contAssignBuilding, null);
        contAssignRoom.setBounds(new Rectangle(325, 80, 270, 19));
        this.add(contAssignRoom, null);
        contAreaDescription.setBounds(new Rectangle(641, 81, 270, 19));
        this.add(contAreaDescription, null);
        contPriceDescription.setBounds(new Rectangle(8, 121, 270, 19));
        this.add(contPriceDescription, null);
        contChargeComplexion.setBounds(new Rectangle(323, 121, 270, 19));
        this.add(contChargeComplexion, null);
        contOwnerShipState.setBounds(new Rectangle(8, 206, 270, 19));
        this.add(contOwnerShipState, null);
        contOwnerShipNew.setBounds(new Rectangle(324, 206, 270, 19));
        this.add(contOwnerShipNew, null);
        contBizDate.setBounds(new Rectangle(9, 249, 270, 19));
        this.add(contBizDate, null);
        contHabitationArea.setBounds(new Rectangle(9, 44, 270, 19));
        this.add(contHabitationArea, null);
        contSalesman.setBounds(new Rectangle(8, 163, 270, 19));
        this.add(contSalesman, null);
        contBuildingProperty.setBounds(new Rectangle(641, 159, 270, 19));
        this.add(contBuildingProperty, null);
        contBooker.setBounds(new Rectangle(324, 248, 270, 19));
        this.add(contBooker, null);
        contWuyeActuality.setBounds(new Rectangle(642, 205, 270, 19));
        this.add(contWuyeActuality, null);
        contInvestorHouseSource.setBounds(new Rectangle(323, 162, 270, 19));
        this.add(contInvestorHouseSource, null);
        contDescription.setBounds(new Rectangle(11, 329, 585, 44));
        this.add(contDescription, null);
        investorHousePanel.setBounds(new Rectangle(5, 385, 909, 221));
        this.add(investorHousePanel, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contOldEnrollName
        contOldEnrollName.setBoundEditor(txtOldEnrollName);
        //contOrderLocation
        contOrderLocation.setBoundEditor(txtOrderLocation);
        //contOrderSourceClass
        contOrderSourceClass.setBoundEditor(comboOrderSourceClass);
        //contAssignBuilding
        contAssignBuilding.setBoundEditor(txtAssignBuilding);
        //contAssignRoom
        contAssignRoom.setBoundEditor(txtAssignRoom);
        //contAreaDescription
        contAreaDescription.setBoundEditor(txtAreaDescription);
        //contPriceDescription
        contPriceDescription.setBoundEditor(txtPriceDescription);
        //contChargeComplexion
        contChargeComplexion.setBoundEditor(txtChargeComplexion);
        //contOwnerShipState
        contOwnerShipState.setBoundEditor(comboOwnerShipState);
        //contOwnerShipNew
        contOwnerShipNew.setBoundEditor(comboOwnerShipNew);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contHabitationArea
        contHabitationArea.setBoundEditor(prmtHabitationArea);
        //contSalesman
        contSalesman.setBoundEditor(prmtSalesman);
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(prmtBuildingProperty);
        //contBooker
        contBooker.setBoundEditor(prmtBooker);
        //contWuyeActuality
        contWuyeActuality.setBoundEditor(txtWuyeActuality);
        //contInvestorHouseSource
        contInvestorHouseSource.setBoundEditor(prmtHouseResource);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //investorHousePanel
        investorHousePanel.setLayout(null);        kDTabbedPane1.setBounds(new Rectangle(10, 10, 899, 203));
        investorHousePanel.add(kDTabbedPane1, null);
        //kDTabbedPane1
        kDTabbedPane1.add(tblLinkPerson, resHelper.getString("tblLinkPerson.constraints"));
        kDTabbedPane1.add(tblTrackRecord, resHelper.getString("tblTrackRecord.constraints"));

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
        menuBiz.add(menuAddLine);
        menuBiz.add(menuDelLine);
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
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnDelLine);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("oldEnrollName", String.class, this.txtOldEnrollName, "text");
		dataBinder.registerBinding("orderLocation", String.class, this.txtOrderLocation, "text");
		dataBinder.registerBinding("orderSourceClass", com.kingdee.eas.fdc.sellhouse.OrderSourceClassEnum.class, this.comboOrderSourceClass, "selectedItem");
		dataBinder.registerBinding("assignBuilding", String.class, this.txtAssignBuilding, "text");
		dataBinder.registerBinding("assignRoom", String.class, this.txtAssignRoom, "text");
		dataBinder.registerBinding("areaDescription", String.class, this.txtAreaDescription, "text");
		dataBinder.registerBinding("priceDescription", String.class, this.txtPriceDescription, "text");
		dataBinder.registerBinding("chargeComplexion", String.class, this.txtChargeComplexion, "text");
		dataBinder.registerBinding("OwnerShipState", com.kingdee.eas.fdc.sellhouse.OwnerShipStateEnum.class, this.comboOwnerShipState, "selectedItem");
		dataBinder.registerBinding("ownerShipNew", com.kingdee.eas.fdc.sellhouse.OwnerShipNewEnum.class, this.comboOwnerShipNew, "selectedItem");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("habitationArea", com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo.class, this.prmtHabitationArea, "data");
		dataBinder.registerBinding("salesman", com.kingdee.eas.base.permission.UserInfo.class, this.prmtSalesman, "data");
		dataBinder.registerBinding("buildingProperty", com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo.class, this.prmtBuildingProperty, "data");
		dataBinder.registerBinding("booker", com.kingdee.eas.base.permission.UserInfo.class, this.prmtBooker, "data");
		dataBinder.registerBinding("wuyeActuality", String.class, this.txtWuyeActuality, "text");
		dataBinder.registerBinding("informationSource", com.kingdee.eas.fdc.sellhouse.InvestorHouseSourceInfo.class, this.prmtHouseResource, "data");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.InvestorHouseEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo)ov;
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
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldEnrollName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orderLocation", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orderSourceClass", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("assignBuilding", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("assignRoom", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("areaDescription", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceDescription", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chargeComplexion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OwnerShipState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ownerShipNew", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("habitationArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("salesman", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildingProperty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("booker", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("wuyeActuality", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("informationSource", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    		
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
     * output tblLinkPerson_activeCellChanged method
     */
    protected void tblLinkPerson_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("oldEnrollName"));
        sic.add(new SelectorItemInfo("orderLocation"));
        sic.add(new SelectorItemInfo("orderSourceClass"));
        sic.add(new SelectorItemInfo("assignBuilding"));
        sic.add(new SelectorItemInfo("assignRoom"));
        sic.add(new SelectorItemInfo("areaDescription"));
        sic.add(new SelectorItemInfo("priceDescription"));
        sic.add(new SelectorItemInfo("chargeComplexion"));
        sic.add(new SelectorItemInfo("OwnerShipState"));
        sic.add(new SelectorItemInfo("ownerShipNew"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("habitationArea.*"));
        sic.add(new SelectorItemInfo("salesman.*"));
        sic.add(new SelectorItemInfo("buildingProperty.*"));
        sic.add(new SelectorItemInfo("booker.*"));
        sic.add(new SelectorItemInfo("wuyeActuality"));
        sic.add(new SelectorItemInfo("informationSource.*"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        
    	

    /**
     * output actionAddLine_actionPerformed method
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelLine_actionPerformed method
     */
    public void actionDelLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddLine() {
    	return false;
    }
	public RequestContext prepareActionDelLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelLine() {
    	return false;
    }

    /**
     * output ActionAddLine class
     */     
    protected class ActionAddLine extends ItemAction {     
    
        public ActionAddLine()
        {
            this(null);
        }

        public ActionAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInvestorHouseEditUI.this, "ActionAddLine", "actionAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionDelLine class
     */     
    protected class ActionDelLine extends ItemAction {     
    
        public ActionDelLine()
        {
            this(null);
        }

        public ActionDelLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInvestorHouseEditUI.this, "ActionDelLine", "actionDelLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "InvestorHouseEditUI");
    }




}