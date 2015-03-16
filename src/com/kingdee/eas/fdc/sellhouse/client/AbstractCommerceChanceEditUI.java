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
public abstract class AbstractCommerceChanceEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCommerceChanceEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSysType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSysType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFdcCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFdcCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhoneNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhoneNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommerceLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCommerceLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommerceStatus;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCommerceStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHopedBuildingProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHopedBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHopedProductType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHopedProductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHopedDirection;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHopedDirection;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHopedAreaRequirement;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHopedAreaRequirement;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHopedRoomModelType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHopedRoomModelType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHopedSightRequirement;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHopedSightRequirement;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHopedUnitPrice;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHopedUnitPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHopedTotalPrices;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHopedTotalPrices;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHopedFloor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHopedFloor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuyHouseReason;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuyHouseReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHopedPitch;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHopedPitch;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFirstPayProportion;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFirstPayProportion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIntendingDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkIntendingDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIntendingMoney;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtIntendingMoney;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleMan;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBargainOnCondition;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTerminateReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCertificateNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAddress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomerPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCertificateNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAddress;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblHopedRooms;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblContract;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDCreateDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommerceDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommerceIntention;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCommerceDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInsertRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteRoom;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCommerceIntention;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnModifyTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane3;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtBargainOnCondition;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtTerminateReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductDetail;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProductDetail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomFrom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomForm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFirstPayAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFirstPayAmount;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAddTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuModifyTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuDelTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCustomerInfo;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer4;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblQuestion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuestionPrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuestionPrint;
    protected com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo editData = null;
    protected ActionAddTrackRecord actionAddTrackRecord = null;
    protected ActionModifyTrackRecord actionModifyTrackRecord = null;
    protected ActionDelTrackRecord actionDelTrackRecord = null;
    protected ActionCustomerInfo actionCustomerInfo = null;
    protected ActionQuestionPrint actionQuestionPrint = null;
    /**
     * output class constructor
     */
    public AbstractCommerceChanceEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCommerceChanceEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddTrackRecord
        this.actionAddTrackRecord = new ActionAddTrackRecord(this);
        getActionManager().registerAction("actionAddTrackRecord", actionAddTrackRecord);
         this.actionAddTrackRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionModifyTrackRecord
        this.actionModifyTrackRecord = new ActionModifyTrackRecord(this);
        getActionManager().registerAction("actionModifyTrackRecord", actionModifyTrackRecord);
         this.actionModifyTrackRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelTrackRecord
        this.actionDelTrackRecord = new ActionDelTrackRecord(this);
        getActionManager().registerAction("actionDelTrackRecord", actionDelTrackRecord);
         this.actionDelTrackRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCustomerInfo
        this.actionCustomerInfo = new ActionCustomerInfo(this);
        getActionManager().registerAction("actionCustomerInfo", actionCustomerInfo);
         this.actionCustomerInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuestionPrint
        this.actionQuestionPrint = new ActionQuestionPrint(this);
        getActionManager().registerAction("actionQuestionPrint", actionQuestionPrint);
         this.actionQuestionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSysType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboSysType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contFdcCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtFdcCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contPhoneNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPhoneNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCommerceLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCommerceLevel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCommerceStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboCommerceStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contHopedBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHopedBuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contHopedProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHopedProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contHopedDirection = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHopedDirection = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contHopedAreaRequirement = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHopedAreaRequirement = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contHopedRoomModelType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHopedRoomModelType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contHopedSightRequirement = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHopedSightRequirement = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contHopedUnitPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHopedUnitPrice = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contHopedTotalPrices = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHopedTotalPrices = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contHopedFloor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHopedFloor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBuyHouseReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtBuyHouseReason = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contHopedPitch = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHopedPitch = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contFirstPayProportion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtFirstPayProportion = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contIntendingDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkIntendingDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contIntendingMoney = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtIntendingMoney = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contSaleMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtSaleMan = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBargainOnCondition = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTerminateReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomerPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCertificateNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCustomerPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCertificateNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tblHopedRooms = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblContract = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblTrackRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDCreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contCommerceDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCommerceIntention = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkCommerceDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnAddRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInsertRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.comboCommerceIntention = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnAddTrackRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnModifyTrackRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelTrackRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane3 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtBargainOnCondition = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtTerminateReason = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contProductDetail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProductDetail = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRoomFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtRoomForm = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contFirstPayAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtFirstPayAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.menuAddTrackRecord = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuModifyTrackRecord = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuDelTrackRecord = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnCustomerInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemCustomerInfo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDContainer4 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblQuestion = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnQuestionPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemQuestionPrint = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contName.setName("contName");
        this.txtName.setName("txtName");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contDescription.setName("contDescription");
        this.contSysType.setName("contSysType");
        this.comboSysType.setName("comboSysType");
        this.contFdcCustomer.setName("contFdcCustomer");
        this.prmtFdcCustomer.setName("prmtFdcCustomer");
        this.contSellProject.setName("contSellProject");
        this.prmtSellProject.setName("prmtSellProject");
        this.contPhoneNumber.setName("contPhoneNumber");
        this.txtPhoneNumber.setName("txtPhoneNumber");
        this.contCommerceLevel.setName("contCommerceLevel");
        this.prmtCommerceLevel.setName("prmtCommerceLevel");
        this.contCommerceStatus.setName("contCommerceStatus");
        this.comboCommerceStatus.setName("comboCommerceStatus");
        this.contHopedBuildingProperty.setName("contHopedBuildingProperty");
        this.prmtHopedBuildingProperty.setName("prmtHopedBuildingProperty");
        this.contHopedProductType.setName("contHopedProductType");
        this.prmtHopedProductType.setName("prmtHopedProductType");
        this.contHopedDirection.setName("contHopedDirection");
        this.prmtHopedDirection.setName("prmtHopedDirection");
        this.contHopedAreaRequirement.setName("contHopedAreaRequirement");
        this.prmtHopedAreaRequirement.setName("prmtHopedAreaRequirement");
        this.contHopedRoomModelType.setName("contHopedRoomModelType");
        this.prmtHopedRoomModelType.setName("prmtHopedRoomModelType");
        this.contHopedSightRequirement.setName("contHopedSightRequirement");
        this.prmtHopedSightRequirement.setName("prmtHopedSightRequirement");
        this.contHopedUnitPrice.setName("contHopedUnitPrice");
        this.prmtHopedUnitPrice.setName("prmtHopedUnitPrice");
        this.contHopedTotalPrices.setName("contHopedTotalPrices");
        this.prmtHopedTotalPrices.setName("prmtHopedTotalPrices");
        this.contHopedFloor.setName("contHopedFloor");
        this.prmtHopedFloor.setName("prmtHopedFloor");
        this.contBuyHouseReason.setName("contBuyHouseReason");
        this.prmtBuyHouseReason.setName("prmtBuyHouseReason");
        this.contHopedPitch.setName("contHopedPitch");
        this.prmtHopedPitch.setName("prmtHopedPitch");
        this.contFirstPayProportion.setName("contFirstPayProportion");
        this.prmtFirstPayProportion.setName("prmtFirstPayProportion");
        this.contIntendingDate.setName("contIntendingDate");
        this.pkIntendingDate.setName("pkIntendingDate");
        this.contIntendingMoney.setName("contIntendingMoney");
        this.txtIntendingMoney.setName("txtIntendingMoney");
        this.contSaleMan.setName("contSaleMan");
        this.prmtSaleMan.setName("prmtSaleMan");
        this.contBargainOnCondition.setName("contBargainOnCondition");
        this.contTerminateReason.setName("contTerminateReason");
        this.contCustomerPhone.setName("contCustomerPhone");
        this.contCertificateNumber.setName("contCertificateNumber");
        this.contAddress.setName("contAddress");
        this.txtCustomerPhone.setName("txtCustomerPhone");
        this.txtCertificateNumber.setName("txtCertificateNumber");
        this.txtAddress.setName("txtAddress");
        this.tblHopedRooms.setName("tblHopedRooms");
        this.tblContract.setName("tblContract");
        this.tblTrackRecord.setName("tblTrackRecord");
        this.kDCreateDate.setName("kDCreateDate");
        this.contCommerceDate.setName("contCommerceDate");
        this.contCommerceIntention.setName("contCommerceIntention");
        this.pkCommerceDate.setName("pkCommerceDate");
        this.btnAddRoom.setName("btnAddRoom");
        this.btnInsertRoom.setName("btnInsertRoom");
        this.btnDeleteRoom.setName("btnDeleteRoom");
        this.comboCommerceIntention.setName("comboCommerceIntention");
        this.kDContainer2.setName("kDContainer2");
        this.kDContainer3.setName("kDContainer3");
        this.kDContainer1.setName("kDContainer1");
        this.kDPanel1.setName("kDPanel1");
        this.btnAddTrackRecord.setName("btnAddTrackRecord");
        this.btnModifyTrackRecord.setName("btnModifyTrackRecord");
        this.btnDelTrackRecord.setName("btnDelTrackRecord");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.kDScrollPane3.setName("kDScrollPane3");
        this.txtBargainOnCondition.setName("txtBargainOnCondition");
        this.txtTerminateReason.setName("txtTerminateReason");
        this.contProductDetail.setName("contProductDetail");
        this.prmtProductDetail.setName("prmtProductDetail");
        this.contRoomFrom.setName("contRoomFrom");
        this.prmtRoomForm.setName("prmtRoomForm");
        this.contFirstPayAmount.setName("contFirstPayAmount");
        this.txtFirstPayAmount.setName("txtFirstPayAmount");
        this.menuAddTrackRecord.setName("menuAddTrackRecord");
        this.menuModifyTrackRecord.setName("menuModifyTrackRecord");
        this.menuDelTrackRecord.setName("menuDelTrackRecord");
        this.btnCustomerInfo.setName("btnCustomerInfo");
        this.menuItemCustomerInfo.setName("menuItemCustomerInfo");
        this.kDContainer4.setName("kDContainer4");
        this.tblQuestion.setName("tblQuestion");
        this.btnQuestionPrint.setName("btnQuestionPrint");
        this.menuItemQuestionPrint.setName("menuItemQuestionPrint");
        // CoreUI		
        this.btnAddLine.setText(resHelper.getString("btnAddLine.text"));		
        this.btnAddLine.setToolTipText(resHelper.getString("btnAddLine.toolTipText"));		
        this.btnAddLine.setEnabled(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setText(resHelper.getString("btnInsertLine.text"));		
        this.btnInsertLine.setToolTipText(resHelper.getString("btnInsertLine.toolTipText"));		
        this.btnInsertLine.setVisible(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnRemoveLine.setText(resHelper.getString("btnRemoveLine.text"));		
        this.btnRemoveLine.setToolTipText(resHelper.getString("btnRemoveLine.toolTipText"));		
        this.btnRemoveLine.setEnabled(false);		
        this.btnRemoveLine.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setText(resHelper.getString("menuItemAddLine.text"));		
        this.menuItemInsertLine.setText(resHelper.getString("menuItemInsertLine.text"));		
        this.menuItemRemoveLine.setText(resHelper.getString("menuItemRemoveLine.text"));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setQueryInfo("com.kingdee.eas.base.forewarn.UserQuery");		
        this.prmtCreator.setCommitFormat("$number$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // txtName
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contSysType		
        this.contSysType.setBoundLabelText(resHelper.getString("contSysType.boundLabelText"));		
        this.contSysType.setBoundLabelLength(100);		
        this.contSysType.setBoundLabelUnderline(true);
        // comboSysType		
        this.comboSysType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.MoneySysTypeEnum").toArray());		
        this.comboSysType.setRequired(true);		
        this.comboSysType.setEnabled(false);
        this.comboSysType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboSysType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contFdcCustomer		
        this.contFdcCustomer.setBoundLabelText(resHelper.getString("contFdcCustomer.boundLabelText"));		
        this.contFdcCustomer.setBoundLabelLength(100);		
        this.contFdcCustomer.setBoundLabelUnderline(true);
        // prmtFdcCustomer		
        this.prmtFdcCustomer.setRequired(true);		
        this.prmtFdcCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");		
        this.prmtFdcCustomer.setCommitFormat("$number$");		
        this.prmtFdcCustomer.setEditFormat("$number$");		
        this.prmtFdcCustomer.setDisplayFormat("$name$");
        this.prmtFdcCustomer.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtFdcCustomer_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // prmtSellProject		
        this.prmtSellProject.setRequired(true);		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtSellProject.setCommitFormat("$number$");		
        this.prmtSellProject.setEditFormat("$number$");		
        this.prmtSellProject.setDisplayFormat("$name$");
        this.prmtSellProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSellProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contPhoneNumber		
        this.contPhoneNumber.setBoundLabelText(resHelper.getString("contPhoneNumber.boundLabelText"));		
        this.contPhoneNumber.setBoundLabelLength(100);		
        this.contPhoneNumber.setBoundLabelUnderline(true);
        // txtPhoneNumber		
        this.txtPhoneNumber.setMaxLength(80);		
        this.txtPhoneNumber.setRequired(true);
        // contCommerceLevel		
        this.contCommerceLevel.setBoundLabelText(resHelper.getString("contCommerceLevel.boundLabelText"));		
        this.contCommerceLevel.setBoundLabelLength(100);		
        this.contCommerceLevel.setBoundLabelUnderline(true);
        // prmtCommerceLevel		
        this.prmtCommerceLevel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CommerceLevelQuery");		
        this.prmtCommerceLevel.setCommitFormat("$number$");		
        this.prmtCommerceLevel.setEditFormat("$number$");		
        this.prmtCommerceLevel.setDisplayFormat("$name$");		
        this.prmtCommerceLevel.setRequired(true);
        // contCommerceStatus		
        this.contCommerceStatus.setBoundLabelText(resHelper.getString("contCommerceStatus.boundLabelText"));		
        this.contCommerceStatus.setBoundLabelLength(100);		
        this.contCommerceStatus.setBoundLabelUnderline(true);
        // comboCommerceStatus		
        this.comboCommerceStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum").toArray());		
        this.comboCommerceStatus.setRequired(true);		
        this.comboCommerceStatus.setEnabled(false);
        // contHopedBuildingProperty		
        this.contHopedBuildingProperty.setBoundLabelText(resHelper.getString("contHopedBuildingProperty.boundLabelText"));		
        this.contHopedBuildingProperty.setBoundLabelLength(100);		
        this.contHopedBuildingProperty.setBoundLabelUnderline(true);
        // prmtHopedBuildingProperty		
        this.prmtHopedBuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");		
        this.prmtHopedBuildingProperty.setCommitFormat("$number$");		
        this.prmtHopedBuildingProperty.setEditFormat("$number$");		
        this.prmtHopedBuildingProperty.setDisplayFormat("$name$");
        // contHopedProductType		
        this.contHopedProductType.setBoundLabelText(resHelper.getString("contHopedProductType.boundLabelText"));		
        this.contHopedProductType.setBoundLabelLength(100);		
        this.contHopedProductType.setBoundLabelUnderline(true);
        // prmtHopedProductType		
        this.prmtHopedProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");		
        this.prmtHopedProductType.setCommitFormat("$number$");		
        this.prmtHopedProductType.setEditFormat("$number$");		
        this.prmtHopedProductType.setDisplayFormat("$name$");
        // contHopedDirection		
        this.contHopedDirection.setBoundLabelText(resHelper.getString("contHopedDirection.boundLabelText"));		
        this.contHopedDirection.setBoundLabelLength(100);		
        this.contHopedDirection.setBoundLabelUnderline(true);
        // prmtHopedDirection		
        this.prmtHopedDirection.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");		
        this.prmtHopedDirection.setCommitFormat("$number$");		
        this.prmtHopedDirection.setEditFormat("$number$");		
        this.prmtHopedDirection.setDisplayFormat("$name$");
        // contHopedAreaRequirement		
        this.contHopedAreaRequirement.setBoundLabelText(resHelper.getString("contHopedAreaRequirement.boundLabelText"));		
        this.contHopedAreaRequirement.setBoundLabelLength(100);		
        this.contHopedAreaRequirement.setBoundLabelUnderline(true);
        // prmtHopedAreaRequirement		
        this.prmtHopedAreaRequirement.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.AreaRequirementQuery");		
        this.prmtHopedAreaRequirement.setCommitFormat("$number$");		
        this.prmtHopedAreaRequirement.setEditFormat("$number$");		
        this.prmtHopedAreaRequirement.setDisplayFormat("$name$");
        // contHopedRoomModelType		
        this.contHopedRoomModelType.setBoundLabelText(resHelper.getString("contHopedRoomModelType.boundLabelText"));		
        this.contHopedRoomModelType.setBoundLabelLength(100);		
        this.contHopedRoomModelType.setBoundLabelUnderline(true);
        // prmtHopedRoomModelType		
        this.prmtHopedRoomModelType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery");		
        this.prmtHopedRoomModelType.setCommitFormat("$number$");		
        this.prmtHopedRoomModelType.setEditFormat("$number$");		
        this.prmtHopedRoomModelType.setDisplayFormat("$name$");
        // contHopedSightRequirement		
        this.contHopedSightRequirement.setBoundLabelText(resHelper.getString("contHopedSightRequirement.boundLabelText"));		
        this.contHopedSightRequirement.setBoundLabelLength(100);		
        this.contHopedSightRequirement.setBoundLabelUnderline(true);
        // prmtHopedSightRequirement		
        this.prmtHopedSightRequirement.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SightRequirementQuery");		
        this.prmtHopedSightRequirement.setCommitFormat("$number$");		
        this.prmtHopedSightRequirement.setEditFormat("$number$");		
        this.prmtHopedSightRequirement.setDisplayFormat("$name$");
        // contHopedUnitPrice		
        this.contHopedUnitPrice.setBoundLabelText(resHelper.getString("contHopedUnitPrice.boundLabelText"));		
        this.contHopedUnitPrice.setBoundLabelLength(100);		
        this.contHopedUnitPrice.setBoundLabelUnderline(true);
        // prmtHopedUnitPrice		
        this.prmtHopedUnitPrice.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedUnitPriceQuery");		
        this.prmtHopedUnitPrice.setCommitFormat("$number$");		
        this.prmtHopedUnitPrice.setEditFormat("$number$");		
        this.prmtHopedUnitPrice.setDisplayFormat("$name$");
        // contHopedTotalPrices		
        this.contHopedTotalPrices.setBoundLabelText(resHelper.getString("contHopedTotalPrices.boundLabelText"));		
        this.contHopedTotalPrices.setBoundLabelLength(100);		
        this.contHopedTotalPrices.setBoundLabelUnderline(true);
        // prmtHopedTotalPrices		
        this.prmtHopedTotalPrices.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedTotalPricesQuery");		
        this.prmtHopedTotalPrices.setCommitFormat("$number$");		
        this.prmtHopedTotalPrices.setEditFormat("$number$");		
        this.prmtHopedTotalPrices.setDisplayFormat("$name$");
        // contHopedFloor		
        this.contHopedFloor.setBoundLabelText(resHelper.getString("contHopedFloor.boundLabelText"));		
        this.contHopedFloor.setBoundLabelLength(100);		
        this.contHopedFloor.setBoundLabelUnderline(true);
        // prmtHopedFloor		
        this.prmtHopedFloor.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedFloorQuery");		
        this.prmtHopedFloor.setCommitFormat("$number$");		
        this.prmtHopedFloor.setEditFormat("$number$");		
        this.prmtHopedFloor.setDisplayFormat("$name$");
        // contBuyHouseReason		
        this.contBuyHouseReason.setBoundLabelText(resHelper.getString("contBuyHouseReason.boundLabelText"));		
        this.contBuyHouseReason.setBoundLabelLength(100);		
        this.contBuyHouseReason.setBoundLabelUnderline(true);
        // prmtBuyHouseReason		
        this.prmtBuyHouseReason.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuyHouseReasonQuery");		
        this.prmtBuyHouseReason.setCommitFormat("$number$");		
        this.prmtBuyHouseReason.setEditFormat("$number$");		
        this.prmtBuyHouseReason.setDisplayFormat("$name$");
        // contHopedPitch		
        this.contHopedPitch.setBoundLabelText(resHelper.getString("contHopedPitch.boundLabelText"));		
        this.contHopedPitch.setBoundLabelLength(100);		
        this.contHopedPitch.setBoundLabelUnderline(true);
        // prmtHopedPitch		
        this.prmtHopedPitch.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopePitchQuery");		
        this.prmtHopedPitch.setCommitFormat("$number$");		
        this.prmtHopedPitch.setEditFormat("$number$");		
        this.prmtHopedPitch.setDisplayFormat("$name$");
        // contFirstPayProportion		
        this.contFirstPayProportion.setBoundLabelText(resHelper.getString("contFirstPayProportion.boundLabelText"));		
        this.contFirstPayProportion.setBoundLabelLength(100);		
        this.contFirstPayProportion.setBoundLabelUnderline(true);
        // prmtFirstPayProportion		
        this.prmtFirstPayProportion.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.FirstPayProportionQuery");		
        this.prmtFirstPayProportion.setCommitFormat("$number$");		
        this.prmtFirstPayProportion.setEditFormat("$number$");		
        this.prmtFirstPayProportion.setDisplayFormat("$name$");
        // contIntendingDate		
        this.contIntendingDate.setBoundLabelText(resHelper.getString("contIntendingDate.boundLabelText"));		
        this.contIntendingDate.setBoundLabelLength(100);		
        this.contIntendingDate.setBoundLabelUnderline(true);
        // pkIntendingDate
        // contIntendingMoney		
        this.contIntendingMoney.setBoundLabelText(resHelper.getString("contIntendingMoney.boundLabelText"));		
        this.contIntendingMoney.setBoundLabelLength(100);		
        this.contIntendingMoney.setBoundLabelUnderline(true);
        // txtIntendingMoney		
        this.txtIntendingMoney.setDataType(1);
        // contSaleMan		
        this.contSaleMan.setBoundLabelText(resHelper.getString("contSaleMan.boundLabelText"));		
        this.contSaleMan.setBoundLabelLength(100);		
        this.contSaleMan.setBoundLabelUnderline(true);
        // prmtSaleMan		
        this.prmtSaleMan.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtSaleMan.setEditFormat("$number$");		
        this.prmtSaleMan.setCommitFormat("$number$");		
        this.prmtSaleMan.setDisplayFormat("$name$");		
        this.prmtSaleMan.setRequired(true);
        this.prmtSaleMan.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSaleMan_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contBargainOnCondition		
        this.contBargainOnCondition.setBoundLabelText(resHelper.getString("contBargainOnCondition.boundLabelText"));		
        this.contBargainOnCondition.setBoundLabelLength(100);		
        this.contBargainOnCondition.setBoundLabelUnderline(true);
        // contTerminateReason		
        this.contTerminateReason.setBoundLabelText(resHelper.getString("contTerminateReason.boundLabelText"));		
        this.contTerminateReason.setBoundLabelLength(100);		
        this.contTerminateReason.setBoundLabelUnderline(true);
        // contCustomerPhone		
        this.contCustomerPhone.setBoundLabelText(resHelper.getString("contCustomerPhone.boundLabelText"));		
        this.contCustomerPhone.setBoundLabelUnderline(true);		
        this.contCustomerPhone.setBoundLabelLength(100);		
        this.contCustomerPhone.setEnabled(false);
        // contCertificateNumber		
        this.contCertificateNumber.setBoundLabelText(resHelper.getString("contCertificateNumber.boundLabelText"));		
        this.contCertificateNumber.setBoundLabelLength(100);		
        this.contCertificateNumber.setBoundLabelUnderline(true);		
        this.contCertificateNumber.setEnabled(false);
        // contAddress		
        this.contAddress.setBoundLabelText(resHelper.getString("contAddress.boundLabelText"));		
        this.contAddress.setBoundLabelLength(100);		
        this.contAddress.setBoundLabelUnderline(true);		
        this.contAddress.setEnabled(false);
        // txtCustomerPhone		
        this.txtCustomerPhone.setEnabled(false);
        // txtCertificateNumber		
        this.txtCertificateNumber.setEnabled(false);
        // txtAddress		
        this.txtAddress.setEnabled(false);
        // tblHopedRooms
		String tblHopedRoomsStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"intentLevel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"intentLevelStr\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"roomModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"saleStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"tenancyStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"planToChange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{intentLevel}</t:Cell><t:Cell>$Resource{intentLevelStr}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{roomModel}</t:Cell><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{saleStatus}</t:Cell><t:Cell>$Resource{tenancyStatus}</t:Cell><t:Cell>$Resource{planToChange}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblHopedRooms.setFormatXml(resHelper.translateString("tblHopedRooms",tblHopedRoomsStrXML));
        this.tblHopedRooms.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblHopedRooms_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

                this.tblHopedRooms.putBindContents("editData",new String[] {"seq","","room.number","room.roomModel","room.building","room.unit","room.sellState","room.tenancyState","planToChange"});


        // tblContract
		String tblContractStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"status\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"UINAME\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dealPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{UINAME}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{dealPrice}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblContract.setFormatXml(resHelper.translateString("tblContract",tblContractStrXML));
        this.tblContract.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblContract_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // tblTrackRecord
		String tblTrackRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"eventDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"receptionType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"sellProject\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"trackDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"trackEvent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"trackResult\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"billNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"trackName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{eventDate}</t:Cell><t:Cell>$Resource{receptionType}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{sellProject}</t:Cell><t:Cell>$Resource{trackDes}</t:Cell><t:Cell>$Resource{trackEvent}</t:Cell><t:Cell>$Resource{trackResult}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{billNumber}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{trackName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblTrackRecord.setFormatXml(resHelper.translateString("tblTrackRecord",tblTrackRecordStrXML));
        this.tblTrackRecord.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblTrackRecord_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDCreateDate		
        this.kDCreateDate.setEnabled(false);
        // contCommerceDate		
        this.contCommerceDate.setBoundLabelText(resHelper.getString("contCommerceDate.boundLabelText"));		
        this.contCommerceDate.setBoundLabelLength(100);		
        this.contCommerceDate.setBoundLabelUnderline(true);
        // contCommerceIntention		
        this.contCommerceIntention.setBoundLabelText(resHelper.getString("contCommerceIntention.boundLabelText"));		
        this.contCommerceIntention.setBoundLabelLength(100);		
        this.contCommerceIntention.setBoundLabelUnderline(true);
        // pkCommerceDate		
        this.pkCommerceDate.setRequired(true);
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
        // btnInsertRoom		
        this.btnInsertRoom.setText(resHelper.getString("btnInsertRoom.text"));
        this.btnInsertRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnInsertRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDeleteRoom		
        this.btnDeleteRoom.setText(resHelper.getString("btnDeleteRoom.text"));
        this.btnDeleteRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeleteRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // comboCommerceIntention		
        this.comboCommerceIntention.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CommerceIntentionEnum").toArray());
        this.comboCommerceIntention.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comboCommerceIntention_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));		
        this.kDContainer2.setAutoscrolls(true);
        // kDContainer3		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));		
        this.kDContainer3.setAutoscrolls(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setAutoscrolls(true);
        // kDPanel1
        // btnAddTrackRecord
        this.btnAddTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddTrackRecord.setText(resHelper.getString("btnAddTrackRecord.text"));		
        this.btnAddTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
        // btnModifyTrackRecord
        this.btnModifyTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionModifyTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnModifyTrackRecord.setText(resHelper.getString("btnModifyTrackRecord.text"));		
        this.btnModifyTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // btnDelTrackRecord
        this.btnDelTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelTrackRecord.setText(resHelper.getString("btnDelTrackRecord.text"));		
        this.btnDelTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_delete"));
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // kDScrollPane2
        // kDScrollPane3
        // txtBargainOnCondition		
        this.txtBargainOnCondition.setMaxLength(80);
        // txtTerminateReason		
        this.txtTerminateReason.setMaxLength(80);		
        this.txtTerminateReason.setEnabled(false);
        // contProductDetail		
        this.contProductDetail.setBoundLabelText(resHelper.getString("contProductDetail.boundLabelText"));		
        this.contProductDetail.setBoundLabelLength(100);		
        this.contProductDetail.setBoundLabelUnderline(true);
        // prmtProductDetail		
        this.prmtProductDetail.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ProductDetialQuery");		
        this.prmtProductDetail.setDisplayFormat("$name$");		
        this.prmtProductDetail.setEditFormat("$number$");		
        this.prmtProductDetail.setCommitFormat("$number$");
        // contRoomFrom		
        this.contRoomFrom.setBoundLabelText(resHelper.getString("contRoomFrom.boundLabelText"));		
        this.contRoomFrom.setBoundLabelLength(100);		
        this.contRoomFrom.setBoundLabelUnderline(true);
        // prmtRoomForm		
        this.prmtRoomForm.setDisplayFormat("$name$");		
        this.prmtRoomForm.setEditFormat("$number$");		
        this.prmtRoomForm.setCommitFormat("$number$");		
        this.prmtRoomForm.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomFormQuery");
        // contFirstPayAmount		
        this.contFirstPayAmount.setBoundLabelText(resHelper.getString("contFirstPayAmount.boundLabelText"));		
        this.contFirstPayAmount.setBoundLabelLength(100);		
        this.contFirstPayAmount.setBoundLabelUnderline(true);
        // txtFirstPayAmount		
        this.txtFirstPayAmount.setDataType(1);
        // menuAddTrackRecord
        this.menuAddTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuAddTrackRecord.setText(resHelper.getString("menuAddTrackRecord.text"));		
        this.menuAddTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
        // menuModifyTrackRecord
        this.menuModifyTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionModifyTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuModifyTrackRecord.setText(resHelper.getString("menuModifyTrackRecord.text"));		
        this.menuModifyTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // menuDelTrackRecord
        this.menuDelTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuDelTrackRecord.setText(resHelper.getString("menuDelTrackRecord.text"));		
        this.menuDelTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_delete"));
        // btnCustomerInfo
        this.btnCustomerInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCustomerInfo.setText(resHelper.getString("btnCustomerInfo.text"));		
        this.btnCustomerInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showdata"));
        // menuItemCustomerInfo
        this.menuItemCustomerInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCustomerInfo.setText(resHelper.getString("menuItemCustomerInfo.text"));
        // kDContainer4		
        this.kDContainer4.setTitle(resHelper.getString("kDContainer4.title"));
        // tblQuestion
		String tblQuestionStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"printDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"title\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{printDate}</t:Cell><t:Cell>$Resource{title}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblQuestion.setFormatXml(resHelper.translateString("tblQuestion",tblQuestionStrXML));
        this.tblQuestion.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblQuestion_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnQuestionPrint
        this.btnQuestionPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuestionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuestionPrint.setText(resHelper.getString("btnQuestionPrint.text"));		
        this.btnQuestionPrint.setToolTipText(resHelper.getString("btnQuestionPrint.toolTipText"));		
        this.btnQuestionPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcredence"));
        // menuItemQuestionPrint
        this.menuItemQuestionPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuestionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuestionPrint.setText(resHelper.getString("menuItemQuestionPrint.text"));		
        this.menuItemQuestionPrint.setToolTipText(resHelper.getString("menuItemQuestionPrint.toolTipText"));		
        this.menuItemQuestionPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcredence"));
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
        this.setBounds(new Rectangle(10, 10, 880, 700));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 880, 700));
        contCreator.setBounds(new Rectangle(300, 270, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(300, 270, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contCreateTime.setBounds(new Rectangle(590, 270, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(590, 270, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contName.setBounds(new Rectangle(300, 54, 270, 19));
        this.add(contName, new KDLayout.Constraints(300, 54, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contNumber.setBounds(new Rectangle(10, 76, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(10, 230, 270, 37));
        this.add(contDescription, new KDLayout.Constraints(10, 230, 270, 37, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSysType.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contSysType, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFdcCustomer.setBounds(new Rectangle(300, 10, 270, 19));
        this.add(contFdcCustomer, new KDLayout.Constraints(300, 10, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contSellProject.setBounds(new Rectangle(590, 32, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(590, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPhoneNumber.setBounds(new Rectangle(590, 54, 270, 19));
        this.add(contPhoneNumber, new KDLayout.Constraints(590, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCommerceLevel.setBounds(new Rectangle(10, 98, 270, 19));
        this.add(contCommerceLevel, new KDLayout.Constraints(10, 98, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCommerceStatus.setBounds(new Rectangle(300, 76, 270, 19));
        this.add(contCommerceStatus, new KDLayout.Constraints(300, 76, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contHopedBuildingProperty.setBounds(new Rectangle(10, 120, 270, 19));
        this.add(contHopedBuildingProperty, new KDLayout.Constraints(10, 120, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHopedProductType.setBounds(new Rectangle(300, 98, 270, 19));
        this.add(contHopedProductType, new KDLayout.Constraints(300, 98, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contHopedDirection.setBounds(new Rectangle(590, 98, 270, 19));
        this.add(contHopedDirection, new KDLayout.Constraints(590, 98, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contHopedAreaRequirement.setBounds(new Rectangle(10, 142, 270, 19));
        this.add(contHopedAreaRequirement, new KDLayout.Constraints(10, 142, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHopedRoomModelType.setBounds(new Rectangle(590, 120, 270, 19));
        this.add(contHopedRoomModelType, new KDLayout.Constraints(590, 120, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contHopedSightRequirement.setBounds(new Rectangle(300, 120, 270, 19));
        this.add(contHopedSightRequirement, new KDLayout.Constraints(300, 120, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contHopedUnitPrice.setBounds(new Rectangle(300, 142, 270, 19));
        this.add(contHopedUnitPrice, new KDLayout.Constraints(300, 142, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contHopedTotalPrices.setBounds(new Rectangle(590, 142, 270, 19));
        this.add(contHopedTotalPrices, new KDLayout.Constraints(590, 142, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contHopedFloor.setBounds(new Rectangle(590, 186, 270, 19));
        this.add(contHopedFloor, new KDLayout.Constraints(590, 186, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuyHouseReason.setBounds(new Rectangle(10, 186, 270, 19));
        this.add(contBuyHouseReason, new KDLayout.Constraints(10, 186, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHopedPitch.setBounds(new Rectangle(300, 164, 270, 19));
        this.add(contHopedPitch, new KDLayout.Constraints(300, 164, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contFirstPayProportion.setBounds(new Rectangle(300, 208, 270, 19));
        this.add(contFirstPayProportion, new KDLayout.Constraints(300, 208, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contIntendingDate.setBounds(new Rectangle(10, 270, 270, 19));
        this.add(contIntendingDate, new KDLayout.Constraints(10, 270, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIntendingMoney.setBounds(new Rectangle(300, 186, 270, 19));
        this.add(contIntendingMoney, new KDLayout.Constraints(300, 186, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contSaleMan.setBounds(new Rectangle(590, 9, 270, 19));
        this.add(contSaleMan, new KDLayout.Constraints(590, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBargainOnCondition.setBounds(new Rectangle(300, 230, 270, 37));
        this.add(contBargainOnCondition, new KDLayout.Constraints(300, 230, 270, 37, KDLayout.Constraints.ANCHOR_CENTRE));
        contTerminateReason.setBounds(new Rectangle(590, 230, 270, 37));
        this.add(contTerminateReason, new KDLayout.Constraints(590, 230, 270, 37, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCustomerPhone.setBounds(new Rectangle(10, 54, 270, 19));
        this.add(contCustomerPhone, new KDLayout.Constraints(10, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCertificateNumber.setBounds(new Rectangle(10, 32, 270, 19));
        this.add(contCertificateNumber, new KDLayout.Constraints(10, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAddress.setBounds(new Rectangle(300, 32, 270, 19));
        this.add(contAddress, new KDLayout.Constraints(300, 32, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contCommerceDate.setBounds(new Rectangle(590, 76, 270, 19));
        this.add(contCommerceDate, new KDLayout.Constraints(590, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCommerceIntention.setBounds(new Rectangle(590, 208, 270, 19));
        this.add(contCommerceIntention, new KDLayout.Constraints(590, 208, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnAddRoom.setBounds(new Rectangle(10, 435, 83, 19));
        this.add(btnAddRoom, new KDLayout.Constraints(10, 435, 83, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnInsertRoom.setBounds(new Rectangle(102, 435, 83, 19));
        this.add(btnInsertRoom, new KDLayout.Constraints(102, 435, 83, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnDeleteRoom.setBounds(new Rectangle(196, 435, 83, 19));
        this.add(btnDeleteRoom, new KDLayout.Constraints(196, 435, 83, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer2.setBounds(new Rectangle(9, 295, 386, 138));
        this.add(kDContainer2, new KDLayout.Constraints(9, 295, 386, 138, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer3.setBounds(new Rectangle(398, 293, 462, 157));
        this.add(kDContainer3, new KDLayout.Constraints(398, 293, 462, 157, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer1.setBounds(new Rectangle(398, 456, 462, 233));
        this.add(kDContainer1, new KDLayout.Constraints(398, 456, 462, 233, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(557, 609, 402, 100));
        this.add(kDPanel1, new KDLayout.Constraints(557, 609, 402, 100, 0));
        contProductDetail.setBounds(new Rectangle(10, 164, 270, 19));
        this.add(contProductDetail, new KDLayout.Constraints(10, 164, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomFrom.setBounds(new Rectangle(590, 164, 270, 19));
        this.add(contRoomFrom, new KDLayout.Constraints(590, 164, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFirstPayAmount.setBounds(new Rectangle(10, 208, 270, 19));
        this.add(contFirstPayAmount, new KDLayout.Constraints(10, 208, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer4.setBounds(new Rectangle(9, 456, 386, 233));
        this.add(kDContainer4, new KDLayout.Constraints(9, 456, 386, 233, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDCreateDate);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contSysType
        contSysType.setBoundEditor(comboSysType);
        //contFdcCustomer
        contFdcCustomer.setBoundEditor(prmtFdcCustomer);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contPhoneNumber
        contPhoneNumber.setBoundEditor(txtPhoneNumber);
        //contCommerceLevel
        contCommerceLevel.setBoundEditor(prmtCommerceLevel);
        //contCommerceStatus
        contCommerceStatus.setBoundEditor(comboCommerceStatus);
        //contHopedBuildingProperty
        contHopedBuildingProperty.setBoundEditor(prmtHopedBuildingProperty);
        //contHopedProductType
        contHopedProductType.setBoundEditor(prmtHopedProductType);
        //contHopedDirection
        contHopedDirection.setBoundEditor(prmtHopedDirection);
        //contHopedAreaRequirement
        contHopedAreaRequirement.setBoundEditor(prmtHopedAreaRequirement);
        //contHopedRoomModelType
        contHopedRoomModelType.setBoundEditor(prmtHopedRoomModelType);
        //contHopedSightRequirement
        contHopedSightRequirement.setBoundEditor(prmtHopedSightRequirement);
        //contHopedUnitPrice
        contHopedUnitPrice.setBoundEditor(prmtHopedUnitPrice);
        //contHopedTotalPrices
        contHopedTotalPrices.setBoundEditor(prmtHopedTotalPrices);
        //contHopedFloor
        contHopedFloor.setBoundEditor(prmtHopedFloor);
        //contBuyHouseReason
        contBuyHouseReason.setBoundEditor(prmtBuyHouseReason);
        //contHopedPitch
        contHopedPitch.setBoundEditor(prmtHopedPitch);
        //contFirstPayProportion
        contFirstPayProportion.setBoundEditor(prmtFirstPayProportion);
        //contIntendingDate
        contIntendingDate.setBoundEditor(pkIntendingDate);
        //contIntendingMoney
        contIntendingMoney.setBoundEditor(txtIntendingMoney);
        //contSaleMan
        contSaleMan.setBoundEditor(prmtSaleMan);
        //contBargainOnCondition
        contBargainOnCondition.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtBargainOnCondition, null);
        //contTerminateReason
        contTerminateReason.setBoundEditor(kDScrollPane3);
        //kDScrollPane3
        kDScrollPane3.getViewport().add(txtTerminateReason, null);
        //contCustomerPhone
        contCustomerPhone.setBoundEditor(txtCustomerPhone);
        //contCertificateNumber
        contCertificateNumber.setBoundEditor(txtCertificateNumber);
        //contAddress
        contAddress.setBoundEditor(txtAddress);
        //contCommerceDate
        contCommerceDate.setBoundEditor(pkCommerceDate);
        //contCommerceIntention
        contCommerceIntention.setBoundEditor(comboCommerceIntention);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(tblHopedRooms, BorderLayout.CENTER);
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer3.getContentPane().add(tblContract, BorderLayout.CENTER);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblTrackRecord, BorderLayout.CENTER);
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(557, 609, 402, 100));        //contProductDetail
        contProductDetail.setBoundEditor(prmtProductDetail);
        //contRoomFrom
        contRoomFrom.setBoundEditor(prmtRoomForm);
        //contFirstPayAmount
        contFirstPayAmount.setBoundEditor(txtFirstPayAmount);
        //kDContainer4
kDContainer4.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer4.getContentPane().add(tblQuestion, BorderLayout.CENTER);

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
        menuFile.add(menuItemQuestionPrint);
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
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemSendMail);
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
        menuEdit.add(separator2);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(menuAddTrackRecord);
        menuEdit.add(menuModifyTrackRecord);
        menuEdit.add(menuDelTrackRecord);
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
        menuBiz.add(menuItemCustomerInfo);
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
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
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
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
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
        this.toolBar.add(btnAddTrackRecord);
        this.toolBar.add(btnModifyTrackRecord);
        this.toolBar.add(btnDelTrackRecord);
        this.toolBar.add(btnCustomerInfo);
        this.toolBar.add(btnQuestionPrint);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("sysType", com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.class, this.comboSysType, "selectedItem");
		dataBinder.registerBinding("fdcCustomer", com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo.class, this.prmtFdcCustomer, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("phoneNumber", String.class, this.txtPhoneNumber, "text");
		dataBinder.registerBinding("commerceLevel", com.kingdee.eas.fdc.sellhouse.CommerceLevelInfo.class, this.prmtCommerceLevel, "data");
		dataBinder.registerBinding("commerceStatus", com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum.class, this.comboCommerceStatus, "selectedItem");
		dataBinder.registerBinding("hopedBuildingProperty", com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo.class, this.prmtHopedBuildingProperty, "data");
		dataBinder.registerBinding("hopedProductType", com.kingdee.eas.fdc.basedata.ProductTypeInfo.class, this.prmtHopedProductType, "data");
		dataBinder.registerBinding("hopedDirection", com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo.class, this.prmtHopedDirection, "data");
		dataBinder.registerBinding("hopedAreaRequirement", com.kingdee.eas.fdc.sellhouse.AreaRequirementInfo.class, this.prmtHopedAreaRequirement, "data");
		dataBinder.registerBinding("hopedRoomModelType", com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo.class, this.prmtHopedRoomModelType, "data");
		dataBinder.registerBinding("hopedSightRequirement", com.kingdee.eas.fdc.sellhouse.SightRequirementInfo.class, this.prmtHopedSightRequirement, "data");
		dataBinder.registerBinding("hopedUnitPrice", com.kingdee.eas.fdc.sellhouse.HopedUnitPriceInfo.class, this.prmtHopedUnitPrice, "data");
		dataBinder.registerBinding("hopedTotalPrices", com.kingdee.eas.fdc.sellhouse.HopedTotalPricesInfo.class, this.prmtHopedTotalPrices, "data");
		dataBinder.registerBinding("hopedFloor", com.kingdee.eas.fdc.sellhouse.HopedFloorInfo.class, this.prmtHopedFloor, "data");
		dataBinder.registerBinding("buyHouseReason", com.kingdee.eas.fdc.sellhouse.BuyHouseReasonInfo.class, this.prmtBuyHouseReason, "data");
		dataBinder.registerBinding("hopedPitch", com.kingdee.eas.fdc.sellhouse.HopePitchInfo.class, this.prmtHopedPitch, "data");
		dataBinder.registerBinding("firstPayProportion", com.kingdee.eas.fdc.sellhouse.FirstPayProportionInfo.class, this.prmtFirstPayProportion, "data");
		dataBinder.registerBinding("intendingDate", java.util.Date.class, this.pkIntendingDate, "value");
		dataBinder.registerBinding("intendingMoney", java.math.BigDecimal.class, this.txtIntendingMoney, "value");
		dataBinder.registerBinding("saleMan", com.kingdee.eas.base.permission.UserInfo.class, this.prmtSaleMan, "data");
		dataBinder.registerBinding("fdcCustomer.phone", String.class, this.txtCustomerPhone, "text");
		dataBinder.registerBinding("fdcCustomer.certificateNumber", String.class, this.txtCertificateNumber, "text");
		dataBinder.registerBinding("fdcCustomer.mailAddress", String.class, this.txtAddress, "text");
		dataBinder.registerBinding("hopedRooms", com.kingdee.eas.fdc.sellhouse.CommerceRoomEntryInfo.class, this.tblHopedRooms, "userObject");
		dataBinder.registerBinding("hopedRooms.room.building", com.kingdee.eas.fdc.sellhouse.RoomCollection.class, this.tblHopedRooms, "building.text");
		dataBinder.registerBinding("hopedRooms.room.unit", int.class, this.tblHopedRooms, "unit.text");
		dataBinder.registerBinding("hopedRooms.room.number", String.class, this.tblHopedRooms, "number.text");
		dataBinder.registerBinding("hopedRooms.room.roomModel", com.kingdee.eas.fdc.sellhouse.RoomModelInfo.class, this.tblHopedRooms, "roomModel.text");
		dataBinder.registerBinding("hopedRooms.room.sellState", com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum.class, this.tblHopedRooms, "saleStatus.text");
		dataBinder.registerBinding("hopedRooms.room.tenancyState", com.kingdee.eas.fdc.tenancy.TenancyStateEnum.class, this.tblHopedRooms, "tenancyStatus.text");
		dataBinder.registerBinding("hopedRooms.seq", int.class, this.tblHopedRooms, "intentLevel.text");
		dataBinder.registerBinding("hopedRooms.planToChange", boolean.class, this.tblHopedRooms, "planToChange.text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDCreateDate, "value");
		dataBinder.registerBinding("commerceDate", java.util.Date.class, this.pkCommerceDate, "value");
		dataBinder.registerBinding("commerceIntention", com.kingdee.eas.fdc.sellhouse.CommerceIntentionEnum.class, this.comboCommerceIntention, "selectedItem");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("bargainOnCondition", String.class, this.txtBargainOnCondition, "text");
		dataBinder.registerBinding("terminateReason", String.class, this.txtTerminateReason, "text");
		dataBinder.registerBinding("productDetail", com.kingdee.eas.fdc.sellhouse.ProductDetialInfo.class, this.prmtProductDetail, "data");
		dataBinder.registerBinding("roomForm", com.kingdee.eas.fdc.sellhouse.RoomFormInfo.class, this.prmtRoomForm, "data");
		dataBinder.registerBinding("firstPayAmount", java.math.BigDecimal.class, this.txtFirstPayAmount, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CommerceChanceEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)ov;
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
		getValidateHelper().registerBindProperty("sysType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fdcCustomer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("phoneNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commerceLevel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commerceStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedBuildingProperty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedProductType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedDirection", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedAreaRequirement", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedRoomModelType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedSightRequirement", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedUnitPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedTotalPrices", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedFloor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buyHouseReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedPitch", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("firstPayProportion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("intendingDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("intendingMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleMan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fdcCustomer.phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fdcCustomer.certificateNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fdcCustomer.mailAddress", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedRooms", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedRooms.room.building", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedRooms.room.unit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedRooms.room.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedRooms.room.roomModel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedRooms.room.sellState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedRooms.room.tenancyState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedRooms.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hopedRooms.planToChange", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commerceDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commerceIntention", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bargainOnCondition", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("terminateReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("productDetail", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomForm", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("firstPayAmount", ValidateHelper.ON_SAVE);    		
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
     * output comboSysType_itemStateChanged method
     */
    protected void comboSysType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtFdcCustomer_dataChanged method
     */
    protected void prmtFdcCustomer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtSellProject_dataChanged method
     */
    protected void prmtSellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtSaleMan_dataChanged method
     */
    protected void prmtSaleMan_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblHopedRooms_tableClicked method
     */
    protected void tblHopedRooms_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblContract_tableClicked method
     */
    protected void tblContract_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblTrackRecord_tableClicked method
     */
    protected void tblTrackRecord_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnAddRoom_actionPerformed method
     */
    protected void btnAddRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnInsertRoom_actionPerformed method
     */
    protected void btnInsertRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDeleteRoom_actionPerformed method
     */
    protected void btnDeleteRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboCommerceIntention_actionPerformed method
     */
    protected void comboCommerceIntention_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblQuestion_tableClicked method
     */
    protected void tblQuestion_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("sysType"));
        sic.add(new SelectorItemInfo("fdcCustomer.*"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("phoneNumber"));
        sic.add(new SelectorItemInfo("commerceLevel.*"));
        sic.add(new SelectorItemInfo("commerceStatus"));
        sic.add(new SelectorItemInfo("hopedBuildingProperty.*"));
        sic.add(new SelectorItemInfo("hopedProductType.*"));
        sic.add(new SelectorItemInfo("hopedDirection.*"));
        sic.add(new SelectorItemInfo("hopedAreaRequirement.*"));
        sic.add(new SelectorItemInfo("hopedRoomModelType.*"));
        sic.add(new SelectorItemInfo("hopedSightRequirement.*"));
        sic.add(new SelectorItemInfo("hopedUnitPrice.*"));
        sic.add(new SelectorItemInfo("hopedTotalPrices.*"));
        sic.add(new SelectorItemInfo("hopedFloor.*"));
        sic.add(new SelectorItemInfo("buyHouseReason.*"));
        sic.add(new SelectorItemInfo("hopedPitch.*"));
        sic.add(new SelectorItemInfo("firstPayProportion.*"));
        sic.add(new SelectorItemInfo("intendingDate"));
        sic.add(new SelectorItemInfo("intendingMoney"));
        sic.add(new SelectorItemInfo("saleMan.*"));
        sic.add(new SelectorItemInfo("fdcCustomer.phone"));
        sic.add(new SelectorItemInfo("fdcCustomer.certificateNumber"));
        sic.add(new SelectorItemInfo("fdcCustomer.mailAddress"));
        sic.add(new SelectorItemInfo("hopedRooms.*"));
//        sic.add(new SelectorItemInfo("hopedRooms.number"));
        sic.add(new SelectorItemInfo("hopedRooms.room.building.*"));
//        sic.add(new SelectorItemInfo("hopedRooms.room.building.number"));
    sic.add(new SelectorItemInfo("hopedRooms.room.unit"));
    sic.add(new SelectorItemInfo("hopedRooms.room.number"));
        sic.add(new SelectorItemInfo("hopedRooms.room.roomModel.*"));
//        sic.add(new SelectorItemInfo("hopedRooms.room.roomModel.number"));
    sic.add(new SelectorItemInfo("hopedRooms.room.sellState"));
    sic.add(new SelectorItemInfo("hopedRooms.room.tenancyState"));
    sic.add(new SelectorItemInfo("hopedRooms.seq"));
    sic.add(new SelectorItemInfo("hopedRooms.planToChange"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("commerceDate"));
        sic.add(new SelectorItemInfo("commerceIntention"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("bargainOnCondition"));
        sic.add(new SelectorItemInfo("terminateReason"));
        sic.add(new SelectorItemInfo("productDetail.*"));
        sic.add(new SelectorItemInfo("roomForm.*"));
        sic.add(new SelectorItemInfo("firstPayAmount"));
        return sic;
    }        
    	

    /**
     * output actionAddTrackRecord_actionPerformed method
     */
    public void actionAddTrackRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionModifyTrackRecord_actionPerformed method
     */
    public void actionModifyTrackRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelTrackRecord_actionPerformed method
     */
    public void actionDelTrackRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCustomerInfo_actionPerformed method
     */
    public void actionCustomerInfo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuestionPrint_actionPerformed method
     */
    public void actionQuestionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddTrackRecord(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddTrackRecord() {
    	return false;
    }
	public RequestContext prepareActionModifyTrackRecord(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionModifyTrackRecord() {
    	return false;
    }
	public RequestContext prepareActionDelTrackRecord(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelTrackRecord() {
    	return false;
    }
	public RequestContext prepareActionCustomerInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCustomerInfo() {
    	return false;
    }
	public RequestContext prepareActionQuestionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuestionPrint() {
    	return false;
    }

    /**
     * output ActionAddTrackRecord class
     */     
    protected class ActionAddTrackRecord extends ItemAction {     
    
        public ActionAddTrackRecord()
        {
            this(null);
        }

        public ActionAddTrackRecord(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddTrackRecord.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTrackRecord.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTrackRecord.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChanceEditUI.this, "ActionAddTrackRecord", "actionAddTrackRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionModifyTrackRecord class
     */     
    protected class ActionModifyTrackRecord extends ItemAction {     
    
        public ActionModifyTrackRecord()
        {
            this(null);
        }

        public ActionModifyTrackRecord(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionModifyTrackRecord.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModifyTrackRecord.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModifyTrackRecord.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChanceEditUI.this, "ActionModifyTrackRecord", "actionModifyTrackRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionDelTrackRecord class
     */     
    protected class ActionDelTrackRecord extends ItemAction {     
    
        public ActionDelTrackRecord()
        {
            this(null);
        }

        public ActionDelTrackRecord(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelTrackRecord.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelTrackRecord.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelTrackRecord.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChanceEditUI.this, "ActionDelTrackRecord", "actionDelTrackRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionCustomerInfo class
     */     
    protected class ActionCustomerInfo extends ItemAction {     
    
        public ActionCustomerInfo()
        {
            this(null);
        }

        public ActionCustomerInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCustomerInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChanceEditUI.this, "ActionCustomerInfo", "actionCustomerInfo_actionPerformed", e);
        }
    }

    /**
     * output ActionQuestionPrint class
     */     
    protected class ActionQuestionPrint extends ItemAction {     
    
        public ActionQuestionPrint()
        {
            this(null);
        }

        public ActionQuestionPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionQuestionPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuestionPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuestionPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCommerceChanceEditUI.this, "ActionQuestionPrint", "actionQuestionPrint_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CommerceChanceEditUI");
    }




}