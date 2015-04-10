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
public abstract class AbstractTenancyBillEditUI extends com.kingdee.eas.fdc.tenancy.client.TenBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTenancyBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabbedPaneContract;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabbedPaneRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelTenancyRoomInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelContractInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelRentSet;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelPayList;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelProperty;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelAgency;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlOtherPayList;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelLiquidated;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPaneLongContract;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelCommissionSetting;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeDes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contname;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsByAgency;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboTenancyType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkTenancyDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7OldContract;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtChangeDes;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox kDBizPromptBox1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRentStartType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFlagAtTerm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChargeDateType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFirstLeaseType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFristRevDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLeaseTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChargeOffsetDays;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLeaseCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboRentStartType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboFlagAtTerm;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboChargeDateType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboFirstLeaseType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEndDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFristLeaseDate;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinLeaseTime;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinChargeOffsetDays;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLeaseCount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalRoomStandardRent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalRoomDealRent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewRoomInfo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveRoom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalRoomStandardRent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalRoomDealRent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalBuildingArea;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnNewCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewCustInfo;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Creator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSpecialClause;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttenRevBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardTotalRent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDeliveryRoomDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealTotalRent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDeductionAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFirstLeaseEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartDateLimit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSincerObligate;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDepositAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfirstTermTenAmo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemainDepositAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLateFeeAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuitRoomDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyAdviser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBussinessDepart;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSpecialClause;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7TenRevBank;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardTotalRent;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDeliveryRoomDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealTotalRent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDeductionAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFirstLeaseEndDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartDateLimit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SincerObligate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDepositAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFirstPayRent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRemainDepositAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker DPickFromMonth;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLateFeeAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker txtQuitRoomDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7TenancyAdviser;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BussinessDepartMent;
    protected com.kingdee.bos.ctrl.swing.KDContainer containerIncrease;
    protected com.kingdee.bos.ctrl.swing.KDContainer containerFree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRentCountType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDaysPerYear;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsAutoToInteger;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToIntegerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDigit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoreRoomsType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsAutoToIntegerForFee;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToIntegerType2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDigit2;
    protected com.kingdee.bos.ctrl.swing.KDContainer containerRentSet;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRentFreeBill;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblIncrease;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblFree;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboRentCountType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDaysPerYear;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboToIntegerType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboDigit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboMoreRoomsType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboToIntegerTypeFee;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboDigitFee;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRentSet;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRentFreeBill;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPayList;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelPayList;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddPayList;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInsert;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsFreeContract;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabMiddle;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgencyContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPromissoryAgentFee;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPromissoryAppPayDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgentFee;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettlementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAppPayDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgentDes;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Agency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7AgencyContract;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPromissoryAgentFee;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPromissoryAppPayDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAgentFee;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SettlementType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAppPayDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAgentDes;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblOtherPayList;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddOtherPaylist;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelOtherPaylist;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsAccLiquidated;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsMDLiquidated;
    protected com.kingdee.bos.ctrl.swing.KDPanel panMDLiquidated;
    protected com.kingdee.bos.ctrl.swing.KDPanel panLiquidated;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblLiquidated;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbRateDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRelief;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandard;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbReliefDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbStandardDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandard;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFinalAmt;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbFinalDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRelief;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbOccurred;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbBit;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFinalAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteLong;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddLong;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblLongContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox combStepCalSetting;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox combExceedAccumulation;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtMinRent;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7MoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtMinCommission;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtScale;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboMoneyType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboEnhanceType;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblStepCalculateSetting;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBusinessIncome;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelAttachRes;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAttachRes;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelAttachResInfo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddAttachRes;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveAttachRes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalAttachResStandardRent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalAttachResDealRent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalAttachResStandardRent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalAttachResDealRent;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCarryForward;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCollectProtocol;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCarryForward;
    protected com.kingdee.eas.fdc.tenancy.TenancyBillInfo editData = null;
    protected ActionCarryForward actionCarryForward = null;
    protected ActionAddCollectProtocol actionAddCollectProtocol = null;
    /**
     * output class constructor
     */
    public AbstractTenancyBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTenancyBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl s"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrint
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAddNew
        actionAddNew.setEnabled(true);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl n"));
        _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
        actionAddNew.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
        actionAddNew.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.NAME");
        actionAddNew.putValue(ItemAction.NAME, _tempStr);
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionCarryForward
        this.actionCarryForward = new ActionCarryForward(this);
        getActionManager().registerAction("actionCarryForward", actionCarryForward);
         this.actionCarryForward.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddCollectProtocol
        this.actionAddCollectProtocol = new ActionAddCollectProtocol(this);
        getActionManager().registerAction("actionAddCollectProtocol", actionAddCollectProtocol);
         this.actionAddCollectProtocol.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tabbedPaneContract = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.tabbedPaneRoom = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.panelTenancyRoomInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelContractInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelRentSet = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelPayList = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelProperty = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelAgency = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlOtherPayList = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelLiquidated = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPaneLongContract = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelCommissionSetting = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contTenancyType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contname = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsByAgency = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelRoom = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelCustomer = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboTenancyType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkTenancyDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7OldContract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtChangeDes = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDBizPromptBox1 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contRentStartType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFlagAtTerm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChargeDateType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFirstLeaseType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFristRevDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLeaseTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChargeOffsetDays = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLeaseCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboRentStartType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboFlagAtTerm = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboChargeDateType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboFirstLeaseType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkFristLeaseDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.spinLeaseTime = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spinChargeOffsetDays = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtLeaseCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.tblRoom = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contTotalRoomStandardRent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalRoomDealRent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewRoomInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtTotalRoomStandardRent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalRoomDealRent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.tblCustomer = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnNewCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewCustInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Creator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSpecialClause = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttenRevBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardTotalRent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDeliveryRoomDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealTotalRent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDeductionAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFirstLeaseEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStartDateLimit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSincerObligate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contDepositAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfirstTermTenAmo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemainDepositAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLateFeeAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuitRoomDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyAdviser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBussinessDepart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSpecialClause = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7TenRevBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtStandardTotalRent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkDeliveryRoomDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDealTotalRent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDeductionAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkFirstLeaseEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkStartDateLimit = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7SincerObligate = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDepositAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFirstPayRent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRemainDepositAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.DPickFromMonth = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtLateFeeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtQuitRoomDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7TenancyAdviser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7BussinessDepartMent = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.containerIncrease = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.containerFree = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contRentCountType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDaysPerYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsAutoToInteger = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contToIntegerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDigit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoreRoomsType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsAutoToIntegerForFee = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contToIntegerType2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDigit2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.containerRentSet = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contRentFreeBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tblIncrease = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblFree = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.comboRentCountType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtDaysPerYear = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboToIntegerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboDigit = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboMoreRoomsType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboToIntegerTypeFee = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboDigitFee = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblRentSet = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtRentFreeBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblPayList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnDelPayList = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddPayList = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInsert = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.chkIsFreeContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.tabMiddle = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contAgency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgencyContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPromissoryAgentFee = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPromissoryAppPayDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgentFee = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettlementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAppPayDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgentDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Agency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7AgencyContract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPromissoryAgentFee = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkPromissoryAppPayDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtAgentFee = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7SettlementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAppPayDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtAgentDes = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tblOtherPayList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddOtherPaylist = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelOtherPaylist = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.chkIsAccLiquidated = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsMDLiquidated = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.panMDLiquidated = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panLiquidated = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblLiquidated = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbRateDate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contRelief = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbReliefDate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbStandardDate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtStandard = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contFinalAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbFinalDate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRelief = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.cbOccurred = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbBit = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtFinalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnDeleteLong = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddLong = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblLongContract = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.combStepCalSetting = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.combExceedAccumulation = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtMinRent = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.f7MoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtMinCommission = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtScale = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.comboMoneyType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboEnhanceType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblStepCalculateSetting = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblBusinessIncome = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.panelAttachRes = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblAttachRes = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.panelAttachResInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnAddAttachRes = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveAttachRes = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contTotalAttachResStandardRent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalAttachResDealRent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTotalAttachResStandardRent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalAttachResDealRent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnCarryForward = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCollectProtocol = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemCarryForward = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.tabbedPaneContract.setName("tabbedPaneContract");
        this.tabbedPaneRoom.setName("tabbedPaneRoom");
        this.panelTenancyRoomInfo.setName("panelTenancyRoomInfo");
        this.kDPanel2.setName("kDPanel2");
        this.panelContractInfo.setName("panelContractInfo");
        this.panelRentSet.setName("panelRentSet");
        this.panelPayList.setName("panelPayList");
        this.panelProperty.setName("panelProperty");
        this.panelAgency.setName("panelAgency");
        this.pnlOtherPayList.setName("pnlOtherPayList");
        this.panelLiquidated.setName("panelLiquidated");
        this.kDPaneLongContract.setName("kDPaneLongContract");
        this.panelCommissionSetting.setName("panelCommissionSetting");
        this.contTenancyType.setName("contTenancyType");
        this.contTenancyDate.setName("contTenancyDate");
        this.contOldContract.setName("contOldContract");
        this.contNumber.setName("contNumber");
        this.contChangeDes.setName("contChangeDes");
        this.contname.setName("contname");
        this.chkIsByAgency.setName("chkIsByAgency");
        this.kDPanel1.setName("kDPanel1");
        this.panelRoom.setName("panelRoom");
        this.panelCustomer.setName("panelCustomer");
        this.contCreateTime.setName("contCreateTime");
        this.contCreator.setName("contCreator");
        this.comboTenancyType.setName("comboTenancyType");
        this.pkTenancyDate.setName("pkTenancyDate");
        this.f7OldContract.setName("f7OldContract");
        this.txtNumber.setName("txtNumber");
        this.txtChangeDes.setName("txtChangeDes");
        this.kDBizPromptBox1.setName("kDBizPromptBox1");
        this.txtName.setName("txtName");
        this.contRentStartType.setName("contRentStartType");
        this.contFlagAtTerm.setName("contFlagAtTerm");
        this.contChargeDateType.setName("contChargeDateType");
        this.contFirstLeaseType.setName("contFirstLeaseType");
        this.contEndDate.setName("contEndDate");
        this.contDescription.setName("contDescription");
        this.contFristRevDate.setName("contFristRevDate");
        this.contLeaseTime.setName("contLeaseTime");
        this.contChargeOffsetDays.setName("contChargeOffsetDays");
        this.contLeaseCount.setName("contLeaseCount");
        this.contStartDate.setName("contStartDate");
        this.comboRentStartType.setName("comboRentStartType");
        this.comboFlagAtTerm.setName("comboFlagAtTerm");
        this.comboChargeDateType.setName("comboChargeDateType");
        this.comboFirstLeaseType.setName("comboFirstLeaseType");
        this.pkEndDate.setName("pkEndDate");
        this.txtDescription.setName("txtDescription");
        this.pkFristLeaseDate.setName("pkFristLeaseDate");
        this.spinLeaseTime.setName("spinLeaseTime");
        this.spinChargeOffsetDays.setName("spinChargeOffsetDays");
        this.txtLeaseCount.setName("txtLeaseCount");
        this.pkStartDate.setName("pkStartDate");
        this.tblRoom.setName("tblRoom");
        this.contTotalRoomStandardRent.setName("contTotalRoomStandardRent");
        this.contTotalRoomDealRent.setName("contTotalRoomDealRent");
        this.contTotalBuildingArea.setName("contTotalBuildingArea");
        this.btnViewRoomInfo.setName("btnViewRoomInfo");
        this.btnAddRoom.setName("btnAddRoom");
        this.btnRemoveRoom.setName("btnRemoveRoom");
        this.txtTotalRoomStandardRent.setName("txtTotalRoomStandardRent");
        this.txtTotalRoomDealRent.setName("txtTotalRoomDealRent");
        this.txtTotalBuildingArea.setName("txtTotalBuildingArea");
        this.tblCustomer.setName("tblCustomer");
        this.btnNewCustomer.setName("btnNewCustomer");
        this.btnAddCustomer.setName("btnAddCustomer");
        this.btnRemoveCustomer.setName("btnRemoveCustomer");
        this.btnViewCustInfo.setName("btnViewCustInfo");
        this.pkCreateTime.setName("pkCreateTime");
        this.f7Creator.setName("f7Creator");
        this.contSpecialClause.setName("contSpecialClause");
        this.conttenRevBank.setName("conttenRevBank");
        this.contStandardTotalRent.setName("contStandardTotalRent");
        this.contDeliveryRoomDate.setName("contDeliveryRoomDate");
        this.contDealTotalRent.setName("contDealTotalRent");
        this.contDeductionAmount.setName("contDeductionAmount");
        this.contFirstLeaseEndDate.setName("contFirstLeaseEndDate");
        this.contStartDateLimit.setName("contStartDateLimit");
        this.contSincerObligate.setName("contSincerObligate");
        this.kDSeparator8.setName("kDSeparator8");
        this.kDSeparator9.setName("kDSeparator9");
        this.contDepositAmount.setName("contDepositAmount");
        this.contfirstTermTenAmo.setName("contfirstTermTenAmo");
        this.contRemainDepositAmount.setName("contRemainDepositAmount");
        this.contFromMonth.setName("contFromMonth");
        this.contLateFeeAmount.setName("contLateFeeAmount");
        this.contQuitRoomDate.setName("contQuitRoomDate");
        this.contTenancyAdviser.setName("contTenancyAdviser");
        this.contSellProject.setName("contSellProject");
        this.contBussinessDepart.setName("contBussinessDepart");
        this.txtSpecialClause.setName("txtSpecialClause");
        this.f7TenRevBank.setName("f7TenRevBank");
        this.txtStandardTotalRent.setName("txtStandardTotalRent");
        this.pkDeliveryRoomDate.setName("pkDeliveryRoomDate");
        this.txtDealTotalRent.setName("txtDealTotalRent");
        this.txtDeductionAmount.setName("txtDeductionAmount");
        this.pkFirstLeaseEndDate.setName("pkFirstLeaseEndDate");
        this.pkStartDateLimit.setName("pkStartDateLimit");
        this.f7SincerObligate.setName("f7SincerObligate");
        this.txtDepositAmount.setName("txtDepositAmount");
        this.txtFirstPayRent.setName("txtFirstPayRent");
        this.txtRemainDepositAmount.setName("txtRemainDepositAmount");
        this.DPickFromMonth.setName("DPickFromMonth");
        this.txtLateFeeAmount.setName("txtLateFeeAmount");
        this.txtQuitRoomDate.setName("txtQuitRoomDate");
        this.f7TenancyAdviser.setName("f7TenancyAdviser");
        this.f7SellProject.setName("f7SellProject");
        this.f7BussinessDepartMent.setName("f7BussinessDepartMent");
        this.containerIncrease.setName("containerIncrease");
        this.containerFree.setName("containerFree");
        this.contRentCountType.setName("contRentCountType");
        this.contDaysPerYear.setName("contDaysPerYear");
        this.chkIsAutoToInteger.setName("chkIsAutoToInteger");
        this.contToIntegerType.setName("contToIntegerType");
        this.contDigit.setName("contDigit");
        this.contMoreRoomsType.setName("contMoreRoomsType");
        this.chkIsAutoToIntegerForFee.setName("chkIsAutoToIntegerForFee");
        this.contToIntegerType2.setName("contToIntegerType2");
        this.contDigit2.setName("contDigit2");
        this.containerRentSet.setName("containerRentSet");
        this.contRentFreeBill.setName("contRentFreeBill");
        this.tblIncrease.setName("tblIncrease");
        this.tblFree.setName("tblFree");
        this.comboRentCountType.setName("comboRentCountType");
        this.txtDaysPerYear.setName("txtDaysPerYear");
        this.comboToIntegerType.setName("comboToIntegerType");
        this.comboDigit.setName("comboDigit");
        this.comboMoreRoomsType.setName("comboMoreRoomsType");
        this.comboToIntegerTypeFee.setName("comboToIntegerTypeFee");
        this.comboDigitFee.setName("comboDigitFee");
        this.tblRentSet.setName("tblRentSet");
        this.prmtRentFreeBill.setName("prmtRentFreeBill");
        this.tblPayList.setName("tblPayList");
        this.btnDelPayList.setName("btnDelPayList");
        this.btnAddPayList.setName("btnAddPayList");
        this.btnInsert.setName("btnInsert");
        this.chkIsFreeContract.setName("chkIsFreeContract");
        this.tabMiddle.setName("tabMiddle");
        this.contAgency.setName("contAgency");
        this.contAgencyContract.setName("contAgencyContract");
        this.contPromissoryAgentFee.setName("contPromissoryAgentFee");
        this.contPromissoryAppPayDate.setName("contPromissoryAppPayDate");
        this.contAgentFee.setName("contAgentFee");
        this.contSettlementType.setName("contSettlementType");
        this.contAppPayDate.setName("contAppPayDate");
        this.contAgentDes.setName("contAgentDes");
        this.f7Agency.setName("f7Agency");
        this.f7AgencyContract.setName("f7AgencyContract");
        this.txtPromissoryAgentFee.setName("txtPromissoryAgentFee");
        this.pkPromissoryAppPayDate.setName("pkPromissoryAppPayDate");
        this.txtAgentFee.setName("txtAgentFee");
        this.f7SettlementType.setName("f7SettlementType");
        this.pkAppPayDate.setName("pkAppPayDate");
        this.txtAgentDes.setName("txtAgentDes");
        this.tblOtherPayList.setName("tblOtherPayList");
        this.btnAddOtherPaylist.setName("btnAddOtherPaylist");
        this.btnDelOtherPaylist.setName("btnDelOtherPaylist");
        this.chkIsAccLiquidated.setName("chkIsAccLiquidated");
        this.chkIsMDLiquidated.setName("chkIsMDLiquidated");
        this.panMDLiquidated.setName("panMDLiquidated");
        this.panLiquidated.setName("panLiquidated");
        this.tblLiquidated.setName("tblLiquidated");
        this.contRate.setName("contRate");
        this.cbRateDate.setName("cbRateDate");
        this.contRelief.setName("contRelief");
        this.contStandard.setName("contStandard");
        this.contBit.setName("contBit");
        this.cbReliefDate.setName("cbReliefDate");
        this.cbStandardDate.setName("cbStandardDate");
        this.txtStandard.setName("txtStandard");
        this.contFinalAmt.setName("contFinalAmt");
        this.cbFinalDate.setName("cbFinalDate");
        this.txtRate.setName("txtRate");
        this.txtRelief.setName("txtRelief");
        this.cbOccurred.setName("cbOccurred");
        this.cbBit.setName("cbBit");
        this.txtFinalAmount.setName("txtFinalAmount");
        this.btnDeleteLong.setName("btnDeleteLong");
        this.btnAddLong.setName("btnAddLong");
        this.tblLongContract.setName("tblLongContract");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDContainer1.setName("kDContainer1");
        this.combStepCalSetting.setName("combStepCalSetting");
        this.combExceedAccumulation.setName("combExceedAccumulation");
        this.txtMinRent.setName("txtMinRent");
        this.f7MoneyDefine.setName("f7MoneyDefine");
        this.txtMinCommission.setName("txtMinCommission");
        this.txtScale.setName("txtScale");
        this.comboMoneyType.setName("comboMoneyType");
        this.comboEnhanceType.setName("comboEnhanceType");
        this.tblStepCalculateSetting.setName("tblStepCalculateSetting");
        this.tblBusinessIncome.setName("tblBusinessIncome");
        this.panelAttachRes.setName("panelAttachRes");
        this.tblAttachRes.setName("tblAttachRes");
        this.panelAttachResInfo.setName("panelAttachResInfo");
        this.btnAddAttachRes.setName("btnAddAttachRes");
        this.btnRemoveAttachRes.setName("btnRemoveAttachRes");
        this.contTotalAttachResStandardRent.setName("contTotalAttachResStandardRent");
        this.contTotalAttachResDealRent.setName("contTotalAttachResDealRent");
        this.txtTotalAttachResStandardRent.setName("txtTotalAttachResStandardRent");
        this.txtTotalAttachResDealRent.setName("txtTotalAttachResDealRent");
        this.btnCarryForward.setName("btnCarryForward");
        this.btnCollectProtocol.setName("btnCollectProtocol");
        this.menuItemCarryForward.setName("menuItemCarryForward");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.menuItemSave.setVisible(false);		
        this.menuItemSave.setEnabled(false);		
        this.menuSubmitOption.setEnabled(false);		
        this.menuSubmitOption.setVisible(false);		
        this.chkMenuItemSubmitAndAddNew.setEnabled(false);		
        this.chkMenuItemSubmitAndAddNew.setVisible(false);		
        this.chkMenuItemSubmitAndPrint.setEnabled(false);		
        this.chkMenuItemSubmitAndPrint.setVisible(false);
        // tabbedPaneContract
        // tabbedPaneRoom		
        this.tabbedPaneRoom.setMinimumSize(new Dimension(800,600));		
        this.tabbedPaneRoom.setPreferredSize(new Dimension(600,800));		
        this.tabbedPaneRoom.setAutoscrolls(true);		
        this.tabbedPaneRoom.setVisible(false);
        // panelTenancyRoomInfo		
        this.panelTenancyRoomInfo.setVisible(false);
        // kDPanel2
        // panelContractInfo		
        this.panelContractInfo.setBorder(null);		
        this.panelContractInfo.setVisible(false);
        // panelRentSet
        // panelPayList
        // panelProperty
        // panelAgency
        // pnlOtherPayList
        // panelLiquidated
        // kDPaneLongContract		
        this.kDPaneLongContract.setVisible(false);
        // panelCommissionSetting
        // contTenancyType		
        this.contTenancyType.setBoundLabelText(resHelper.getString("contTenancyType.boundLabelText"));		
        this.contTenancyType.setBoundLabelLength(100);		
        this.contTenancyType.setBoundLabelUnderline(true);
        // contTenancyDate		
        this.contTenancyDate.setBoundLabelText(resHelper.getString("contTenancyDate.boundLabelText"));		
        this.contTenancyDate.setBoundLabelLength(100);		
        this.contTenancyDate.setBoundLabelUnderline(true);
        // contOldContract		
        this.contOldContract.setBoundLabelText(resHelper.getString("contOldContract.boundLabelText"));		
        this.contOldContract.setBoundLabelLength(100);		
        this.contOldContract.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelAlignment(7);		
        this.contNumber.setVisible(true);
        // contChangeDes		
        this.contChangeDes.setBoundLabelText(resHelper.getString("contChangeDes.boundLabelText"));		
        this.contChangeDes.setBoundLabelUnderline(true);		
        this.contChangeDes.setBoundLabelLength(100);
        // contname		
        this.contname.setBoundLabelText(resHelper.getString("contname.boundLabelText"));		
        this.contname.setBoundLabelLength(100);		
        this.contname.setBoundLabelUnderline(true);		
        this.contname.setVisible(true);		
        this.contname.setBoundLabelAlignment(7);
        // chkIsByAgency		
        this.chkIsByAgency.setText(resHelper.getString("chkIsByAgency.text"));		
        this.chkIsByAgency.setVisible(false);
        this.chkIsByAgency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsByAgency_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // panelRoom		
        this.panelRoom.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelRoom.border.title")));
        // panelCustomer		
        this.panelCustomer.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelCustomer.border.title")));
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setVisible(true);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setBoundLabelAlignment(7);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setBoundLabelAlignment(7);		
        this.contCreator.setVisible(true);
        // comboTenancyType		
        this.comboTenancyType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum").toArray());
        // pkTenancyDate
        this.pkTenancyDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkTenancyDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7OldContract		
        this.f7OldContract.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");		
        this.f7OldContract.setDisplayFormat("$tenancyName$");		
        this.f7OldContract.setEditFormat("$number$");		
        this.f7OldContract.setCommitFormat("$number$");
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(true);
        // txtChangeDes
        // kDBizPromptBox1
        // txtName		
        this.txtName.setVisible(true);		
        this.txtName.setHorizontalAlignment(2);		
        this.txtName.setMaxLength(50);		
        this.txtName.setRequired(true);		
        this.txtName.setEnabled(true);
        // contRentStartType		
        this.contRentStartType.setBoundLabelText(resHelper.getString("contRentStartType.boundLabelText"));		
        this.contRentStartType.setBoundLabelUnderline(true);		
        this.contRentStartType.setBoundLabelLength(100);
        // contFlagAtTerm		
        this.contFlagAtTerm.setBoundLabelText(resHelper.getString("contFlagAtTerm.boundLabelText"));		
        this.contFlagAtTerm.setBoundLabelLength(100);		
        this.contFlagAtTerm.setBoundLabelUnderline(true);		
        this.contFlagAtTerm.setEnabled(false);
        // contChargeDateType		
        this.contChargeDateType.setBoundLabelText(resHelper.getString("contChargeDateType.boundLabelText"));		
        this.contChargeDateType.setBoundLabelLength(100);		
        this.contChargeDateType.setBoundLabelUnderline(true);
        // contFirstLeaseType		
        this.contFirstLeaseType.setBoundLabelText(resHelper.getString("contFirstLeaseType.boundLabelText"));		
        this.contFirstLeaseType.setBoundLabelLength(100);		
        this.contFirstLeaseType.setBoundLabelUnderline(true);		
        this.contFirstLeaseType.setVisible(true);		
        this.contFirstLeaseType.setBoundLabelAlignment(7);
        // contEndDate		
        this.contEndDate.setBoundLabelText(resHelper.getString("contEndDate.boundLabelText"));		
        this.contEndDate.setBoundLabelLength(100);		
        this.contEndDate.setBoundLabelUnderline(true);		
        this.contEndDate.setVisible(true);		
        this.contEndDate.setBoundLabelAlignment(7);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setBoundLabelAlignment(7);		
        this.contDescription.setVisible(true);
        // contFristRevDate		
        this.contFristRevDate.setBoundLabelText(resHelper.getString("contFristRevDate.boundLabelText"));		
        this.contFristRevDate.setBoundLabelLength(100);		
        this.contFristRevDate.setBoundLabelUnderline(true);
        // contLeaseTime		
        this.contLeaseTime.setBoundLabelText(resHelper.getString("contLeaseTime.boundLabelText"));		
        this.contLeaseTime.setBoundLabelLength(100);		
        this.contLeaseTime.setBoundLabelUnderline(true);
        // contChargeOffsetDays		
        this.contChargeOffsetDays.setBoundLabelText(resHelper.getString("contChargeOffsetDays.boundLabelText"));		
        this.contChargeOffsetDays.setBoundLabelLength(100);		
        this.contChargeOffsetDays.setBoundLabelUnderline(true);
        // contLeaseCount		
        this.contLeaseCount.setBoundLabelText(resHelper.getString("contLeaseCount.boundLabelText"));		
        this.contLeaseCount.setBoundLabelLength(100);		
        this.contLeaseCount.setBoundLabelUnderline(true);		
        this.contLeaseCount.setVisible(true);		
        this.contLeaseCount.setBoundLabelAlignment(7);
        // contStartDate		
        this.contStartDate.setBoundLabelText(resHelper.getString("contStartDate.boundLabelText"));		
        this.contStartDate.setBoundLabelLength(100);		
        this.contStartDate.setBoundLabelUnderline(true);		
        this.contStartDate.setVisible(true);		
        this.contStartDate.setBoundLabelAlignment(7);
        // comboRentStartType		
        this.comboRentStartType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.RentStartTypeEnum").toArray());
        this.comboRentStartType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboRentStartType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboFlagAtTerm		
        this.comboFlagAtTerm.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.FlagAtTermEnum").toArray());		
        this.comboFlagAtTerm.setEnabled(false);
        this.comboFlagAtTerm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboFlagAtTerm_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboChargeDateType		
        this.comboChargeDateType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum").toArray());
        this.comboChargeDateType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboChargeDateType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboFirstLeaseType		
        this.comboFirstLeaseType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum").toArray());
        this.comboFirstLeaseType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboFirstLeaseType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkEndDate		
        this.pkEndDate.setVisible(true);		
        this.pkEndDate.setEnabled(true);
        this.pkEndDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkEndDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtDescription		
        this.txtDescription.setMaxLength(80);		
        this.txtDescription.setVisible(true);		
        this.txtDescription.setEnabled(true);		
        this.txtDescription.setHorizontalAlignment(2);		
        this.txtDescription.setRequired(false);
        this.txtDescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtDescription_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // pkFristLeaseDate
        this.pkFristLeaseDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkFristLeaseDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // spinLeaseTime
        // spinChargeOffsetDays
        // txtLeaseCount		
        this.txtLeaseCount.setVisible(true);		
        this.txtLeaseCount.setHorizontalAlignment(2);		
        this.txtLeaseCount.setSupportedEmpty(true);		
        this.txtLeaseCount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtLeaseCount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtLeaseCount.setPrecision(0);		
        this.txtLeaseCount.setRequired(false);		
        this.txtLeaseCount.setEnabled(true);
        // pkStartDate		
        this.pkStartDate.setVisible(true);		
        this.pkStartDate.setEnabled(true);
        this.pkStartDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkStartDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblRoom
		String tblRoomStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.000</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"tenRoomState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"room\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"floor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"standardRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"standardRentType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"standardRentPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"dayPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"dealRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"dealRentType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"dealRentPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"flagAtTerm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"fitment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"roomModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"direction\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"actDeliverDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"actQuitDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"roomEntryId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol18\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{tenRoomState}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{floor}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{standardRent}</t:Cell><t:Cell>$Resource{standardRentType}</t:Cell><t:Cell>$Resource{standardRentPrice}</t:Cell><t:Cell>$Resource{dayPrice}</t:Cell><t:Cell>$Resource{dealRent}</t:Cell><t:Cell>$Resource{dealRentType}</t:Cell><t:Cell>$Resource{dealRentPrice}</t:Cell><t:Cell>$Resource{flagAtTerm}</t:Cell><t:Cell>$Resource{des}</t:Cell><t:Cell>$Resource{fitment}</t:Cell><t:Cell>$Resource{roomModel}</t:Cell><t:Cell>$Resource{direction}</t:Cell><t:Cell>$Resource{actDeliverDate}</t:Cell><t:Cell>$Resource{actQuitDate}</t:Cell><t:Cell>$Resource{roomEntryId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblRoom.setFormatXml(resHelper.translateString("tblRoom",tblRoomStrXML));
        this.tblRoom.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblRoom_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblRoom.putBindContents("editData",new String[] {"","","","","","","","","","","","","","","","","","","roomEntryId"});


        this.tblRoom.checkParsed();
        // contTotalRoomStandardRent		
        this.contTotalRoomStandardRent.setBoundLabelText(resHelper.getString("contTotalRoomStandardRent.boundLabelText"));		
        this.contTotalRoomStandardRent.setBoundLabelLength(100);		
        this.contTotalRoomStandardRent.setBoundLabelUnderline(true);		
        this.contTotalRoomStandardRent.setVisible(true);		
        this.contTotalRoomStandardRent.setBoundLabelAlignment(7);		
        this.contTotalRoomStandardRent.setFont(new java.awt.Font("Dialog",1,9));		
        this.contTotalRoomStandardRent.setForeground(new java.awt.Color(255,0,0));
        // contTotalRoomDealRent		
        this.contTotalRoomDealRent.setBoundLabelText(resHelper.getString("contTotalRoomDealRent.boundLabelText"));		
        this.contTotalRoomDealRent.setBoundLabelLength(100);		
        this.contTotalRoomDealRent.setBoundLabelUnderline(true);		
        this.contTotalRoomDealRent.setVisible(true);		
        this.contTotalRoomDealRent.setBoundLabelAlignment(7);		
        this.contTotalRoomDealRent.setFont(new java.awt.Font("Dialog",1,9));		
        this.contTotalRoomDealRent.setForeground(new java.awt.Color(255,0,0));
        // contTotalBuildingArea		
        this.contTotalBuildingArea.setBoundLabelText(resHelper.getString("contTotalBuildingArea.boundLabelText"));		
        this.contTotalBuildingArea.setBoundLabelLength(100);		
        this.contTotalBuildingArea.setBoundLabelUnderline(true);		
        this.contTotalBuildingArea.setVisible(true);		
        this.contTotalBuildingArea.setBoundLabelAlignment(7);		
        this.contTotalBuildingArea.setFont(new java.awt.Font("Dialog",1,9));		
        this.contTotalBuildingArea.setForeground(new java.awt.Color(255,0,0));
        // btnViewRoomInfo		
        this.btnViewRoomInfo.setText(resHelper.getString("btnViewRoomInfo.text"));
        this.btnViewRoomInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnViewRoomInfo_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
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
        // txtTotalRoomStandardRent		
        this.txtTotalRoomStandardRent.setVisible(true);		
        this.txtTotalRoomStandardRent.setHorizontalAlignment(2);		
        this.txtTotalRoomStandardRent.setDataType(1);		
        this.txtTotalRoomStandardRent.setSupportedEmpty(true);		
        this.txtTotalRoomStandardRent.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtTotalRoomStandardRent.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtTotalRoomStandardRent.setPrecision(2);		
        this.txtTotalRoomStandardRent.setRequired(false);		
        this.txtTotalRoomStandardRent.setEnabled(true);
        // txtTotalRoomDealRent		
        this.txtTotalRoomDealRent.setVisible(true);		
        this.txtTotalRoomDealRent.setHorizontalAlignment(2);		
        this.txtTotalRoomDealRent.setDataType(1);		
        this.txtTotalRoomDealRent.setSupportedEmpty(true);		
        this.txtTotalRoomDealRent.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtTotalRoomDealRent.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtTotalRoomDealRent.setPrecision(2);		
        this.txtTotalRoomDealRent.setRequired(false);		
        this.txtTotalRoomDealRent.setEnabled(true);
        // txtTotalBuildingArea		
        this.txtTotalBuildingArea.setVisible(true);		
        this.txtTotalBuildingArea.setHorizontalAlignment(2);		
        this.txtTotalBuildingArea.setDataType(1);		
        this.txtTotalBuildingArea.setSupportedEmpty(true);		
        this.txtTotalBuildingArea.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtTotalBuildingArea.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtTotalBuildingArea.setPrecision(3);		
        this.txtTotalBuildingArea.setRequired(false);		
        this.txtTotalBuildingArea.setEnabled(true);
        // tblCustomer
		String tblCustomerStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"propertyPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"postalcode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"certificateName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"certificateNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"mailAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"bookDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{propertyPercent}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{postalcode}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{certificateName}</t:Cell><t:Cell>$Resource{certificateNumber}</t:Cell><t:Cell>$Resource{mailAddress}</t:Cell><t:Cell>$Resource{bookDate}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblCustomer.setFormatXml(resHelper.translateString("tblCustomer",tblCustomerStrXML));
        this.tblCustomer.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblCustomer_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblCustomer.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblCustomerInfo_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.tblCustomer.checkParsed();
        // btnNewCustomer		
        this.btnNewCustomer.setText(resHelper.getString("btnNewCustomer.text"));
        this.btnNewCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddNewCustomer_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAddCustomer		
        this.btnAddCustomer.setText(resHelper.getString("btnAddCustomer.text"));
        this.btnAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddCustomer_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRemoveCustomer		
        this.btnRemoveCustomer.setText(resHelper.getString("btnRemoveCustomer.text"));
        this.btnRemoveCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRemoveCustomer_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnViewCustInfo		
        this.btnViewCustInfo.setText(resHelper.getString("btnViewCustInfo.text"));
        this.btnViewCustInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnViewCustInfo_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);		
        this.pkCreateTime.setVisible(true);
        // f7Creator		
        this.f7Creator.setEnabled(false);		
        this.f7Creator.setVisible(true);		
        this.f7Creator.setEditable(true);		
        this.f7Creator.setDisplayFormat("$name$");		
        this.f7Creator.setEditFormat("$number$");		
        this.f7Creator.setCommitFormat("$number$");		
        this.f7Creator.setRequired(false);		
        this.f7Creator.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
        // contSpecialClause		
        this.contSpecialClause.setBoundLabelText(resHelper.getString("contSpecialClause.boundLabelText"));		
        this.contSpecialClause.setBoundLabelLength(100);		
        this.contSpecialClause.setBoundLabelUnderline(true);		
        this.contSpecialClause.setVisible(true);		
        this.contSpecialClause.setBoundLabelAlignment(7);
        // conttenRevBank		
        this.conttenRevBank.setBoundLabelText(resHelper.getString("conttenRevBank.boundLabelText"));		
        this.conttenRevBank.setBoundLabelLength(100);		
        this.conttenRevBank.setBoundLabelUnderline(true);		
        this.conttenRevBank.setVisible(true);		
        this.conttenRevBank.setBoundLabelAlignment(7);
        // contStandardTotalRent		
        this.contStandardTotalRent.setBoundLabelText(resHelper.getString("contStandardTotalRent.boundLabelText"));		
        this.contStandardTotalRent.setBoundLabelLength(100);		
        this.contStandardTotalRent.setBoundLabelUnderline(true);		
        this.contStandardTotalRent.setVisible(true);		
        this.contStandardTotalRent.setBoundLabelAlignment(7);
        // contDeliveryRoomDate		
        this.contDeliveryRoomDate.setBoundLabelText(resHelper.getString("contDeliveryRoomDate.boundLabelText"));		
        this.contDeliveryRoomDate.setBoundLabelLength(100);		
        this.contDeliveryRoomDate.setBoundLabelUnderline(true);		
        this.contDeliveryRoomDate.setVisible(true);		
        this.contDeliveryRoomDate.setBoundLabelAlignment(7);
        // contDealTotalRent		
        this.contDealTotalRent.setBoundLabelText(resHelper.getString("contDealTotalRent.boundLabelText"));		
        this.contDealTotalRent.setBoundLabelLength(100);		
        this.contDealTotalRent.setBoundLabelUnderline(true);		
        this.contDealTotalRent.setVisible(true);		
        this.contDealTotalRent.setBoundLabelAlignment(7);
        // contDeductionAmount		
        this.contDeductionAmount.setBoundLabelText(resHelper.getString("contDeductionAmount.boundLabelText"));		
        this.contDeductionAmount.setBoundLabelUnderline(true);		
        this.contDeductionAmount.setBoundLabelLength(100);
        // contFirstLeaseEndDate		
        this.contFirstLeaseEndDate.setBoundLabelText(resHelper.getString("contFirstLeaseEndDate.boundLabelText"));		
        this.contFirstLeaseEndDate.setBoundLabelLength(100);		
        this.contFirstLeaseEndDate.setBoundLabelUnderline(true);
        // contStartDateLimit		
        this.contStartDateLimit.setBoundLabelText(resHelper.getString("contStartDateLimit.boundLabelText"));		
        this.contStartDateLimit.setBoundLabelLength(100);		
        this.contStartDateLimit.setBoundLabelUnderline(true);
        // contSincerObligate		
        this.contSincerObligate.setBoundLabelText(resHelper.getString("contSincerObligate.boundLabelText"));		
        this.contSincerObligate.setBoundLabelLength(100);		
        this.contSincerObligate.setBoundLabelUnderline(true);
        // kDSeparator8
        // kDSeparator9
        // contDepositAmount		
        this.contDepositAmount.setBoundLabelText(resHelper.getString("contDepositAmount.boundLabelText"));		
        this.contDepositAmount.setBoundLabelLength(100);		
        this.contDepositAmount.setBoundLabelUnderline(true);		
        this.contDepositAmount.setVisible(true);		
        this.contDepositAmount.setBoundLabelAlignment(7);
        // contfirstTermTenAmo		
        this.contfirstTermTenAmo.setBoundLabelText(resHelper.getString("contfirstTermTenAmo.boundLabelText"));		
        this.contfirstTermTenAmo.setBoundLabelLength(100);		
        this.contfirstTermTenAmo.setBoundLabelUnderline(true);		
        this.contfirstTermTenAmo.setVisible(true);		
        this.contfirstTermTenAmo.setBoundLabelAlignment(7);
        // contRemainDepositAmount		
        this.contRemainDepositAmount.setBoundLabelText(resHelper.getString("contRemainDepositAmount.boundLabelText"));		
        this.contRemainDepositAmount.setBoundLabelUnderline(true);		
        this.contRemainDepositAmount.setBoundLabelLength(100);
        // contFromMonth		
        this.contFromMonth.setBoundLabelText(resHelper.getString("contFromMonth.boundLabelText"));		
        this.contFromMonth.setBoundLabelLength(100);		
        this.contFromMonth.setBoundLabelUnderline(true);
        // contLateFeeAmount		
        this.contLateFeeAmount.setBoundLabelText(resHelper.getString("contLateFeeAmount.boundLabelText"));		
        this.contLateFeeAmount.setBoundLabelLength(100);		
        this.contLateFeeAmount.setBoundLabelUnderline(true);
        // contQuitRoomDate		
        this.contQuitRoomDate.setBoundLabelText(resHelper.getString("contQuitRoomDate.boundLabelText"));		
        this.contQuitRoomDate.setBoundLabelLength(100);		
        this.contQuitRoomDate.setBoundLabelUnderline(true);
        // contTenancyAdviser		
        this.contTenancyAdviser.setBoundLabelText(resHelper.getString("contTenancyAdviser.boundLabelText"));		
        this.contTenancyAdviser.setBoundLabelLength(100);		
        this.contTenancyAdviser.setBoundLabelUnderline(true);		
        this.contTenancyAdviser.setVisible(false);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setVisible(false);
        // contBussinessDepart		
        this.contBussinessDepart.setBoundLabelText(resHelper.getString("contBussinessDepart.boundLabelText"));		
        this.contBussinessDepart.setBoundLabelLength(100);		
        this.contBussinessDepart.setBoundLabelUnderline(true);		
        this.contBussinessDepart.setVisible(false);
        // txtSpecialClause		
        this.txtSpecialClause.setVisible(true);		
        this.txtSpecialClause.setHorizontalAlignment(2);		
        this.txtSpecialClause.setMaxLength(255);		
        this.txtSpecialClause.setRequired(false);		
        this.txtSpecialClause.setEnabled(true);
        // f7TenRevBank		
        this.f7TenRevBank.setVisible(true);		
        this.f7TenRevBank.setEditable(true);		
        this.f7TenRevBank.setRequired(false);		
        this.f7TenRevBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7BankQuery");		
        this.f7TenRevBank.setEditFormat("$number$");		
        this.f7TenRevBank.setDisplayFormat("$name$");		
        this.f7TenRevBank.setCommitFormat("$number$");
        // txtStandardTotalRent		
        this.txtStandardTotalRent.setVisible(true);		
        this.txtStandardTotalRent.setHorizontalAlignment(2);		
        this.txtStandardTotalRent.setDataType(1);		
        this.txtStandardTotalRent.setSupportedEmpty(true);		
        this.txtStandardTotalRent.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtStandardTotalRent.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtStandardTotalRent.setPrecision(2);		
        this.txtStandardTotalRent.setRequired(false);		
        this.txtStandardTotalRent.setEnabled(true);
        // pkDeliveryRoomDate		
        this.pkDeliveryRoomDate.setVisible(true);		
        this.pkDeliveryRoomDate.setEnabled(true);
        // txtDealTotalRent		
        this.txtDealTotalRent.setVisible(true);		
        this.txtDealTotalRent.setHorizontalAlignment(2);		
        this.txtDealTotalRent.setDataType(1);		
        this.txtDealTotalRent.setSupportedEmpty(true);		
        this.txtDealTotalRent.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtDealTotalRent.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtDealTotalRent.setPrecision(2);		
        this.txtDealTotalRent.setRequired(false);		
        this.txtDealTotalRent.setEnabled(true);
        // txtDeductionAmount		
        this.txtDeductionAmount.setDataType(1);		
        this.txtDeductionAmount.setPrecision(2);		
        this.txtDeductionAmount.setSupportedEmpty(true);		
        this.txtDeductionAmount.setHorizontalAlignment(2);
        // pkFirstLeaseEndDate
        this.pkFirstLeaseEndDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkFirstLeaseEndDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkStartDateLimit
        // f7SincerObligate		
        this.f7SincerObligate.setCommitFormat("$number$");		
        this.f7SincerObligate.setDisplayFormat("$name$");		
        this.f7SincerObligate.setEditFormat("$number$");		
        this.f7SincerObligate.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.SincerObligateQuery");
        this.f7SincerObligate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SincerObligate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtDepositAmount		
        this.txtDepositAmount.setVisible(true);		
        this.txtDepositAmount.setHorizontalAlignment(2);		
        this.txtDepositAmount.setDataType(1);		
        this.txtDepositAmount.setSupportedEmpty(true);		
        this.txtDepositAmount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtDepositAmount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtDepositAmount.setPrecision(2);		
        this.txtDepositAmount.setRequired(false);		
        this.txtDepositAmount.setEnabled(true);
        // txtFirstPayRent		
        this.txtFirstPayRent.setVisible(true);		
        this.txtFirstPayRent.setHorizontalAlignment(2);		
        this.txtFirstPayRent.setDataType(1);		
        this.txtFirstPayRent.setSupportedEmpty(true);		
        this.txtFirstPayRent.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtFirstPayRent.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtFirstPayRent.setPrecision(2);		
        this.txtFirstPayRent.setRequired(false);		
        this.txtFirstPayRent.setEnabled(true);
        // txtRemainDepositAmount		
        this.txtRemainDepositAmount.setDataType(1);
        // DPickFromMonth
        this.DPickFromMonth.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    DPickFromMonth_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtLateFeeAmount		
        this.txtLateFeeAmount.setDataType(1);
        // txtQuitRoomDate
        // f7TenancyAdviser		
        this.f7TenancyAdviser.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7TenancyAdviser.setDisplayFormat("$name$");		
        this.f7TenancyAdviser.setEditFormat("$number$");		
        this.f7TenancyAdviser.setCommitFormat("$number$");
        // f7SellProject		
        this.f7SellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.f7SellProject.setDisplayFormat("$name$");		
        this.f7SellProject.setEditFormat("$number$");		
        this.f7SellProject.setCommitFormat("$number$");		
        this.f7SellProject.setRequired(true);
        // f7BussinessDepartMent
        // containerIncrease		
        this.containerIncrease.setTitle(resHelper.getString("containerIncrease.title"));
        // containerFree		
        this.containerFree.setTitle(resHelper.getString("containerFree.title"));
        // contRentCountType		
        this.contRentCountType.setBoundLabelText(resHelper.getString("contRentCountType.boundLabelText"));		
        this.contRentCountType.setBoundLabelUnderline(true);		
        this.contRentCountType.setBoundLabelLength(100);
        // contDaysPerYear		
        this.contDaysPerYear.setBoundLabelText(resHelper.getString("contDaysPerYear.boundLabelText"));		
        this.contDaysPerYear.setBoundLabelLength(100);		
        this.contDaysPerYear.setBoundLabelUnderline(true);
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
        // contToIntegerType		
        this.contToIntegerType.setBoundLabelText(resHelper.getString("contToIntegerType.boundLabelText"));		
        this.contToIntegerType.setBoundLabelLength(100);		
        this.contToIntegerType.setBoundLabelUnderline(true);
        // contDigit		
        this.contDigit.setBoundLabelText(resHelper.getString("contDigit.boundLabelText"));		
        this.contDigit.setBoundLabelLength(100);		
        this.contDigit.setBoundLabelUnderline(true);
        // contMoreRoomsType		
        this.contMoreRoomsType.setBoundLabelText(resHelper.getString("contMoreRoomsType.boundLabelText"));		
        this.contMoreRoomsType.setBoundLabelLength(100);		
        this.contMoreRoomsType.setBoundLabelUnderline(true);
        // chkIsAutoToIntegerForFee		
        this.chkIsAutoToIntegerForFee.setText(resHelper.getString("chkIsAutoToIntegerForFee.text"));
        this.chkIsAutoToIntegerForFee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsAutoToIntegerForFee_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contToIntegerType2		
        this.contToIntegerType2.setBoundLabelText(resHelper.getString("contToIntegerType2.boundLabelText"));		
        this.contToIntegerType2.setBoundLabelLength(100);		
        this.contToIntegerType2.setBoundLabelUnderline(true);
        // contDigit2		
        this.contDigit2.setBoundLabelText(resHelper.getString("contDigit2.boundLabelText"));		
        this.contDigit2.setBoundLabelLength(100);		
        this.contDigit2.setBoundLabelUnderline(true);
        // containerRentSet
        // contRentFreeBill		
        this.contRentFreeBill.setBoundLabelText(resHelper.getString("contRentFreeBill.boundLabelText"));		
        this.contRentFreeBill.setBoundLabelLength(100);		
        this.contRentFreeBill.setBoundLabelUnderline(true);
        // tblIncrease
		String tblIncreaseStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"increaseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"increaseType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"increaseStyle\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"value\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{increaseDate}</t:Cell><t:Cell>$Resource{increaseType}</t:Cell><t:Cell>$Resource{increaseStyle}</t:Cell><t:Cell>$Resource{value}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblIncrease.setFormatXml(resHelper.translateString("tblIncrease",tblIncreaseStrXML));
        this.tblIncrease.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblIncrease_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.tblIncrease.checkParsed();
        // tblFree
		String tblFreeStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"freeStartDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"freeEndDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"freeTenancyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"isPresent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{freeStartDate}</t:Cell><t:Cell>$Resource{freeEndDate}</t:Cell><t:Cell>$Resource{freeTenancyType}</t:Cell><t:Cell>$Resource{isPresent}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblFree.setFormatXml(resHelper.translateString("tblFree",tblFreeStrXML));
        this.tblFree.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblFree_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.tblFree.checkParsed();
        // comboRentCountType		
        this.comboRentCountType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.RentCountTypeEnum").toArray());
        this.comboRentCountType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboRentCountType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtDaysPerYear
        this.txtDaysPerYear.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtDaysPerYear_dataChanged(e);
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
        // comboMoreRoomsType		
        this.comboMoreRoomsType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.MoreRoomsTypeEnum").toArray());
        this.comboMoreRoomsType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboMoreRoomsType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboToIntegerTypeFee		
        this.comboToIntegerTypeFee.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum").toArray());
        this.comboToIntegerTypeFee.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboToIntegerTypeFee_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboDigitFee		
        this.comboDigitFee.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.DigitEnum").toArray());
        this.comboDigitFee.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboDigitFee_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblRentSet
		String tblRentSetStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"deposit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"firstRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"tenancyModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"rentType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"discount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"exRentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{deposit}</t:Cell><t:Cell>$Resource{firstRent}</t:Cell><t:Cell>$Resource{tenancyModel}</t:Cell><t:Cell>$Resource{rentType}</t:Cell><t:Cell>$Resource{discount}</t:Cell><t:Cell>$Resource{exRentAmount}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room_Row2}</t:Cell><t:Cell>$Resource{deposit_Row2}</t:Cell><t:Cell>$Resource{firstRent_Row2}</t:Cell><t:Cell>$Resource{tenancyModel_Row2}</t:Cell><t:Cell>$Resource{rentType_Row2}</t:Cell><t:Cell>$Resource{discount_Row2}</t:Cell><t:Cell>$Resource{exRentAmount_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblRentSet.setFormatXml(resHelper.translateString("tblRentSet",tblRentSetStrXML));
        this.tblRentSet.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblRentSet_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.tblRentSet.checkParsed();
        // prmtRentFreeBill		
        this.prmtRentFreeBill.setDisplayFormat("$number$");		
        this.prmtRentFreeBill.setEditFormat("$number$");		
        this.prmtRentFreeBill.setCommitFormat("$number$");		
        this.prmtRentFreeBill.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.rentFreeBillQuery");
        this.prmtRentFreeBill.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRentFreeBill_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblPayList
        this.tblPayList.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblPayList_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.tblPayList.checkParsed();
        // btnDelPayList		
        this.btnDelPayList.setText(resHelper.getString("btnDelPayList.text"));
        this.btnDelPayList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelPayList_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAddPayList		
        this.btnAddPayList.setText(resHelper.getString("btnAddPayList.text"));
        this.btnAddPayList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddPayList_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnInsert		
        this.btnInsert.setText(resHelper.getString("btnInsert.text"));
        this.btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnInsert_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkIsFreeContract		
        this.chkIsFreeContract.setText(resHelper.getString("chkIsFreeContract.text"));
        this.chkIsFreeContract.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkIsFreeContract_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.chkIsFreeContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsFreeContract_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tabMiddle
        // contAgency		
        this.contAgency.setBoundLabelText(resHelper.getString("contAgency.boundLabelText"));		
        this.contAgency.setBoundLabelLength(100);		
        this.contAgency.setBoundLabelUnderline(true);
        // contAgencyContract		
        this.contAgencyContract.setBoundLabelText(resHelper.getString("contAgencyContract.boundLabelText"));		
        this.contAgencyContract.setBoundLabelLength(100);		
        this.contAgencyContract.setBoundLabelUnderline(true);
        // contPromissoryAgentFee		
        this.contPromissoryAgentFee.setBoundLabelText(resHelper.getString("contPromissoryAgentFee.boundLabelText"));		
        this.contPromissoryAgentFee.setBoundLabelLength(100);		
        this.contPromissoryAgentFee.setBoundLabelUnderline(true);
        // contPromissoryAppPayDate		
        this.contPromissoryAppPayDate.setBoundLabelText(resHelper.getString("contPromissoryAppPayDate.boundLabelText"));		
        this.contPromissoryAppPayDate.setBoundLabelLength(100);		
        this.contPromissoryAppPayDate.setBoundLabelUnderline(true);
        // contAgentFee		
        this.contAgentFee.setBoundLabelText(resHelper.getString("contAgentFee.boundLabelText"));		
        this.contAgentFee.setBoundLabelLength(100);		
        this.contAgentFee.setBoundLabelUnderline(true);
        // contSettlementType		
        this.contSettlementType.setBoundLabelText(resHelper.getString("contSettlementType.boundLabelText"));		
        this.contSettlementType.setBoundLabelLength(100);		
        this.contSettlementType.setBoundLabelUnderline(true);
        // contAppPayDate		
        this.contAppPayDate.setBoundLabelText(resHelper.getString("contAppPayDate.boundLabelText"));		
        this.contAppPayDate.setBoundLabelUnderline(true);		
        this.contAppPayDate.setBoundLabelLength(100);
        // contAgentDes		
        this.contAgentDes.setBoundLabelText(resHelper.getString("contAgentDes.boundLabelText"));		
        this.contAgentDes.setBoundLabelLength(100);		
        this.contAgentDes.setBoundLabelUnderline(true);
        // f7Agency		
        this.f7Agency.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.F7TenancyAgencyQuery");		
        this.f7Agency.setDisplayFormat("$name$");		
        this.f7Agency.setEditFormat("$number$");		
        this.f7Agency.setCommitFormat("$number$");
        this.f7Agency.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Agency_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7AgencyContract		
        this.f7AgencyContract.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.AgencyContractQuery");		
        this.f7AgencyContract.setCommitFormat("$number$");		
        this.f7AgencyContract.setDisplayFormat("$name$");		
        this.f7AgencyContract.setEditFormat("$number$");
        this.f7AgencyContract.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7AgencyContract_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPromissoryAgentFee		
        this.txtPromissoryAgentFee.setDataType(1);		
        this.txtPromissoryAgentFee.setPrecision(2);		
        this.txtPromissoryAgentFee.setSupportedEmpty(true);
        // pkPromissoryAppPayDate
        // txtAgentFee		
        this.txtAgentFee.setDataType(1);		
        this.txtAgentFee.setPrecision(2);		
        this.txtAgentFee.setSupportedEmpty(true);
        // f7SettlementType		
        this.f7SettlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");		
        this.f7SettlementType.setDisplayFormat("$name$");		
        this.f7SettlementType.setEditFormat("$number$");		
        this.f7SettlementType.setCommitFormat("$number$");
        // pkAppPayDate
        // txtAgentDes
        // tblOtherPayList
		String tblOtherPayListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"leaseSeq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"moneyTypeName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"2\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"actRevAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"actRevDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"isCollect\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{leaseSeq}</t:Cell><t:Cell>$Resource{moneyTypeName}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{actRevAmount}</t:Cell><t:Cell>$Resource{actRevDate}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{isCollect}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblOtherPayList.setFormatXml(resHelper.translateString("tblOtherPayList",tblOtherPayListStrXML));

        

        this.tblOtherPayList.checkParsed();
        // btnAddOtherPaylist		
        this.btnAddOtherPaylist.setText(resHelper.getString("btnAddOtherPaylist.text"));
        this.btnAddOtherPaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddOtherPaylist_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelOtherPaylist		
        this.btnDelOtherPaylist.setText(resHelper.getString("btnDelOtherPaylist.text"));
        this.btnDelOtherPaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelOtherPaylist_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkIsAccLiquidated		
        this.chkIsAccLiquidated.setText(resHelper.getString("chkIsAccLiquidated.text"));
        this.chkIsAccLiquidated.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsAccLiquidated_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkIsMDLiquidated		
        this.chkIsMDLiquidated.setText(resHelper.getString("chkIsMDLiquidated.text"));
        this.chkIsMDLiquidated.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsMDLiquidated_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // panMDLiquidated
        // panLiquidated
        // tblLiquidated
		String tblLiquidatedStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"liquidated\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{liquidated}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblLiquidated.setFormatXml(resHelper.translateString("tblLiquidated",tblLiquidatedStrXML));

        

        this.tblLiquidated.checkParsed();
        // contRate		
        this.contRate.setBoundLabelText(resHelper.getString("contRate.boundLabelText"));		
        this.contRate.setBoundLabelUnderline(true);		
        this.contRate.setBoundLabelLength(100);
        // cbRateDate		
        this.cbRateDate.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.DateEnum").toArray());		
        this.cbRateDate.setEnabled(false);
        // contRelief		
        this.contRelief.setBoundLabelText(resHelper.getString("contRelief.boundLabelText"));		
        this.contRelief.setBoundLabelUnderline(true);		
        this.contRelief.setBoundLabelLength(100);
        // contStandard		
        this.contStandard.setBoundLabelText(resHelper.getString("contStandard.boundLabelText"));		
        this.contStandard.setBoundLabelUnderline(true);		
        this.contStandard.setBoundLabelLength(100);
        // contBit		
        this.contBit.setBoundLabelText(resHelper.getString("contBit.boundLabelText"));		
        this.contBit.setBoundLabelUnderline(true);		
        this.contBit.setBoundLabelLength(100);
        // cbReliefDate		
        this.cbReliefDate.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.DateEnum").toArray());		
        this.cbReliefDate.setEnabled(false);
        // cbStandardDate		
        this.cbStandardDate.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.DateEnum").toArray());		
        this.cbStandardDate.setEnabled(false);
        // txtStandard		
        this.txtStandard.setPrecision(0);
        // contFinalAmt		
        this.contFinalAmt.setBoundLabelText(resHelper.getString("contFinalAmt.boundLabelText"));		
        this.contFinalAmt.setBoundLabelUnderline(true);		
        this.contFinalAmt.setBoundLabelLength(100);
        // cbFinalDate		
        this.cbFinalDate.setEnabled(false);
        // txtRate		
        this.txtRate.setDataType(1);		
        this.txtRate.setPrecision(2);
        this.txtRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtRelief		
        this.txtRelief.setPrecision(0);
        // cbOccurred		
        this.cbOccurred.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.OccurreStateEnum").toArray());
        // cbBit		
        this.cbBit.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.MoneyEnum").toArray());
        // txtFinalAmount		
        this.txtFinalAmount.setDataType(1);		
        this.txtFinalAmount.setPrecision(2);
        // btnDeleteLong		
        this.btnDeleteLong.setText(resHelper.getString("btnDeleteLong.text"));
        this.btnDeleteLong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeleteLong_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAddLong		
        this.btnAddLong.setText(resHelper.getString("btnAddLong.text"));
        this.btnAddLong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddLong_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblLongContract
		String tblLongContractStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"beginDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{beginDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblLongContract.setFormatXml(resHelper.translateString("tblLongContract",tblLongContractStrXML));
        this.tblLongContract.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblLongContract_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.tblLongContract.checkParsed();
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelAlignment(7);		
        this.kDLabelContainer3.setVisible(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);		
        this.kDLabelContainer4.setVisible(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // kDScrollPane1
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // combStepCalSetting		
        this.combStepCalSetting.setText(resHelper.getString("combStepCalSetting.text"));
        this.combStepCalSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    combStepCalSetting_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // combExceedAccumulation		
        this.combExceedAccumulation.setText(resHelper.getString("combExceedAccumulation.text"));
        // txtMinRent
        this.txtMinRent.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtMinRent_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtMinRent.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtMinRent_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // f7MoneyDefine		
        this.f7MoneyDefine.setCommitFormat("$number$");
        // txtMinCommission
        this.txtMinCommission.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtMinCommission_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtMinCommission.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtMinCommission_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtScale
        this.txtScale.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtScale_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboMoneyType		
        this.comboMoneyType.setEnabled(false);
        // comboEnhanceType
        this.comboEnhanceType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboEnhanceType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblStepCalculateSetting
		String tblStepCalculateSettingStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:NumberFormat>yyyy-mm-dd</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-mm-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"beginTime\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"endTime\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"beginQuantity\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"endQuantity\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"scale\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{beginTime}</t:Cell><t:Cell>$Resource{endTime}</t:Cell><t:Cell>$Resource{beginQuantity}</t:Cell><t:Cell>$Resource{endQuantity}</t:Cell><t:Cell>$Resource{scale}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{beginTime_Row2}</t:Cell><t:Cell>$Resource{endTime_Row2}</t:Cell><t:Cell>$Resource{beginQuantity_Row2}</t:Cell><t:Cell>$Resource{endQuantity_Row2}</t:Cell><t:Cell>$Resource{scale_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"0\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"0\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"0\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"0\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"1\" t:right=\"5\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblStepCalculateSetting.setFormatXml(resHelper.translateString("tblStepCalculateSetting",tblStepCalculateSettingStrXML));
        this.tblStepCalculateSetting.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblStepCalculateSetting_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.tblStepCalculateSetting.checkParsed();
        // tblBusinessIncome
		String tblBusinessIncomeStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"beginDate\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"endDate\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"arDate\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"taking\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commissions\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yeMention\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{beginDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{arDate}</t:Cell><t:Cell>$Resource{taking}</t:Cell><t:Cell>$Resource{commissions}</t:Cell><t:Cell>$Resource{yeMention}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblBusinessIncome.setFormatXml(resHelper.translateString("tblBusinessIncome",tblBusinessIncomeStrXML));

        

        this.tblBusinessIncome.checkParsed();
        // panelAttachRes
        // tblAttachRes
		String tblAttachResStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"attach\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"attachResType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"attachResName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"attachResDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"standardRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"standardRentType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"flagAtTerm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"actDeliverDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"actQuitDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{attach}</t:Cell><t:Cell>$Resource{attachResType}</t:Cell><t:Cell>$Resource{attachResName}</t:Cell><t:Cell>$Resource{attachResDes}</t:Cell><t:Cell>$Resource{standardRent}</t:Cell><t:Cell>$Resource{standardRentType}</t:Cell><t:Cell>$Resource{flagAtTerm}</t:Cell><t:Cell>$Resource{des}</t:Cell><t:Cell>$Resource{actDeliverDate}</t:Cell><t:Cell>$Resource{actQuitDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblAttachRes.setFormatXml(resHelper.translateString("tblAttachRes",tblAttachResStrXML));
        this.tblAttachRes.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblAttachRes_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.tblAttachRes.checkParsed();
        // panelAttachResInfo		
        this.panelAttachResInfo.setPreferredSize(new Dimension(10,35));
        // btnAddAttachRes		
        this.btnAddAttachRes.setText(resHelper.getString("btnAddAttachRes.text"));
        this.btnAddAttachRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddAttachRes_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRemoveAttachRes		
        this.btnRemoveAttachRes.setText(resHelper.getString("btnRemoveAttachRes.text"));
        this.btnRemoveAttachRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRemoveAttachRes_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contTotalAttachResStandardRent		
        this.contTotalAttachResStandardRent.setBoundLabelText(resHelper.getString("contTotalAttachResStandardRent.boundLabelText"));		
        this.contTotalAttachResStandardRent.setBoundLabelLength(100);		
        this.contTotalAttachResStandardRent.setBoundLabelUnderline(true);		
        this.contTotalAttachResStandardRent.setVisible(true);		
        this.contTotalAttachResStandardRent.setBoundLabelAlignment(7);
        // contTotalAttachResDealRent		
        this.contTotalAttachResDealRent.setBoundLabelText(resHelper.getString("contTotalAttachResDealRent.boundLabelText"));		
        this.contTotalAttachResDealRent.setBoundLabelLength(100);		
        this.contTotalAttachResDealRent.setBoundLabelUnderline(true);		
        this.contTotalAttachResDealRent.setVisible(true);		
        this.contTotalAttachResDealRent.setBoundLabelAlignment(7);
        // txtTotalAttachResStandardRent		
        this.txtTotalAttachResStandardRent.setVisible(true);		
        this.txtTotalAttachResStandardRent.setHorizontalAlignment(2);		
        this.txtTotalAttachResStandardRent.setDataType(1);		
        this.txtTotalAttachResStandardRent.setSupportedEmpty(true);		
        this.txtTotalAttachResStandardRent.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtTotalAttachResStandardRent.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtTotalAttachResStandardRent.setPrecision(2);		
        this.txtTotalAttachResStandardRent.setRequired(false);		
        this.txtTotalAttachResStandardRent.setEnabled(true);
        // txtTotalAttachResDealRent		
        this.txtTotalAttachResDealRent.setVisible(true);		
        this.txtTotalAttachResDealRent.setHorizontalAlignment(2);		
        this.txtTotalAttachResDealRent.setDataType(1);		
        this.txtTotalAttachResDealRent.setSupportedEmpty(true);		
        this.txtTotalAttachResDealRent.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtTotalAttachResDealRent.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtTotalAttachResDealRent.setPrecision(2);		
        this.txtTotalAttachResDealRent.setRequired(false);		
        this.txtTotalAttachResDealRent.setEnabled(true);
        // btnCarryForward
        this.btnCarryForward.setAction((IItemAction)ActionProxyFactory.getProxy(actionCarryForward, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCarryForward.setText(resHelper.getString("btnCarryForward.text"));		
        this.btnCarryForward.setToolTipText(resHelper.getString("btnCarryForward.toolTipText"));		
        this.btnCarryForward.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_synchronization"));
        // btnCollectProtocol
        this.btnCollectProtocol.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCollectProtocol, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCollectProtocol.setText(resHelper.getString("btnCollectProtocol.text"));
        // menuItemCarryForward
        this.menuItemCarryForward.setAction((IItemAction)ActionProxyFactory.getProxy(actionCarryForward, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCarryForward.setText(resHelper.getString("menuItemCarryForward.text"));		
        this.menuItemCarryForward.setToolTipText(resHelper.getString("menuItemCarryForward.toolTipText"));		
        this.menuItemCarryForward.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_synchronization"));
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
        this.setBounds(new Rectangle(0, 0, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 629));
        tabbedPaneContract.setBounds(new Rectangle(4, 4, 1006, 621));
        this.add(tabbedPaneContract, new KDLayout.Constraints(4, 4, 1006, 621, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        tabbedPaneRoom.setBounds(new Rectangle(964, 121, 46, 87));
        this.add(tabbedPaneRoom, new KDLayout.Constraints(964, 121, 46, 87, 0));
        panelTenancyRoomInfo.setBounds(new Rectangle(395, 141, 81, 23));
        this.add(panelTenancyRoomInfo, new KDLayout.Constraints(395, 141, 81, 23, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //tabbedPaneContract
        tabbedPaneContract.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        tabbedPaneContract.add(panelContractInfo, resHelper.getString("panelContractInfo.constraints"));
        tabbedPaneContract.add(panelRentSet, resHelper.getString("panelRentSet.constraints"));
        tabbedPaneContract.add(panelPayList, resHelper.getString("panelPayList.constraints"));
        tabbedPaneContract.add(panelProperty, resHelper.getString("panelProperty.constraints"));
        tabbedPaneContract.add(panelAgency, resHelper.getString("panelAgency.constraints"));
        tabbedPaneContract.add(pnlOtherPayList, resHelper.getString("pnlOtherPayList.constraints"));
        tabbedPaneContract.add(panelLiquidated, resHelper.getString("panelLiquidated.constraints"));
        tabbedPaneContract.add(kDPaneLongContract, resHelper.getString("kDPaneLongContract.constraints"));
        tabbedPaneContract.add(panelCommissionSetting, resHelper.getString("panelCommissionSetting.constraints"));
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1005, 588));        contTenancyType.setBounds(new Rectangle(18, 0, 236, 19));
        kDPanel2.add(contTenancyType, new KDLayout.Constraints(18, 0, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTenancyDate.setBounds(new Rectangle(18, 28, 236, 19));
        kDPanel2.add(contTenancyDate, new KDLayout.Constraints(18, 28, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOldContract.setBounds(new Rectangle(314, 0, 236, 19));
        kDPanel2.add(contOldContract, new KDLayout.Constraints(314, 0, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(314, 28, 236, 19));
        kDPanel2.add(contNumber, new KDLayout.Constraints(314, 28, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChangeDes.setBounds(new Rectangle(610, 0, 236, 19));
        kDPanel2.add(contChangeDes, new KDLayout.Constraints(610, 0, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contname.setBounds(new Rectangle(610, 28, 236, 19));
        kDPanel2.add(contname, new KDLayout.Constraints(610, 28, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkIsByAgency.setBounds(new Rectangle(906, 0, 102, 19));
        kDPanel2.add(chkIsByAgency, new KDLayout.Constraints(906, 0, 102, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(5, 59, 1008, 110));
        kDPanel2.add(kDPanel1, new KDLayout.Constraints(5, 59, 1008, 110, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        panelRoom.setBounds(new Rectangle(5, 180, 1008, 216));
        kDPanel2.add(panelRoom, new KDLayout.Constraints(5, 180, 1008, 216, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        panelCustomer.setBounds(new Rectangle(7, 407, 1002, 154));
        kDPanel2.add(panelCustomer, new KDLayout.Constraints(7, 407, 1002, 154, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(426, 566, 270, 19));
        kDPanel2.add(contCreateTime, new KDLayout.Constraints(426, 566, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(8, 565, 270, 19));
        kDPanel2.add(contCreator, new KDLayout.Constraints(8, 565, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contTenancyType
        contTenancyType.setBoundEditor(comboTenancyType);
        //contTenancyDate
        contTenancyDate.setBoundEditor(pkTenancyDate);
        //contOldContract
        contOldContract.setBoundEditor(f7OldContract);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contChangeDes
        contChangeDes.setBoundEditor(txtChangeDes);
        contChangeDes.setBoundEditor(kDBizPromptBox1);
        //contname
        contname.setBoundEditor(txtName);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(5, 59, 1008, 110));        contRentStartType.setBounds(new Rectangle(11, 71, 236, 19));
        kDPanel1.add(contRentStartType, new KDLayout.Constraints(11, 71, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFlagAtTerm.setBounds(new Rectangle(259, 71, 236, 19));
        kDPanel1.add(contFlagAtTerm, new KDLayout.Constraints(259, 71, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChargeDateType.setBounds(new Rectangle(507, 42, 236, 19));
        kDPanel1.add(contChargeDateType, new KDLayout.Constraints(507, 42, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFirstLeaseType.setBounds(new Rectangle(11, 42, 236, 19));
        kDPanel1.add(contFirstLeaseType, new KDLayout.Constraints(11, 42, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEndDate.setBounds(new Rectangle(259, 14, 236, 19));
        kDPanel1.add(contEndDate, new KDLayout.Constraints(259, 14, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(507, 71, 484, 19));
        kDPanel1.add(contDescription, new KDLayout.Constraints(507, 71, 484, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFristRevDate.setBounds(new Rectangle(259, 42, 236, 19));
        kDPanel1.add(contFristRevDate, new KDLayout.Constraints(259, 42, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLeaseTime.setBounds(new Rectangle(507, 14, 236, 19));
        kDPanel1.add(contLeaseTime, new KDLayout.Constraints(507, 14, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChargeOffsetDays.setBounds(new Rectangle(756, 42, 236, 19));
        kDPanel1.add(contChargeOffsetDays, new KDLayout.Constraints(756, 42, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLeaseCount.setBounds(new Rectangle(756, 14, 236, 19));
        kDPanel1.add(contLeaseCount, new KDLayout.Constraints(756, 14, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStartDate.setBounds(new Rectangle(11, 14, 236, 19));
        kDPanel1.add(contStartDate, new KDLayout.Constraints(11, 14, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contRentStartType
        contRentStartType.setBoundEditor(comboRentStartType);
        //contFlagAtTerm
        contFlagAtTerm.setBoundEditor(comboFlagAtTerm);
        //contChargeDateType
        contChargeDateType.setBoundEditor(comboChargeDateType);
        //contFirstLeaseType
        contFirstLeaseType.setBoundEditor(comboFirstLeaseType);
        //contEndDate
        contEndDate.setBoundEditor(pkEndDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contFristRevDate
        contFristRevDate.setBoundEditor(pkFristLeaseDate);
        //contLeaseTime
        contLeaseTime.setBoundEditor(spinLeaseTime);
        //contChargeOffsetDays
        contChargeOffsetDays.setBoundEditor(spinChargeOffsetDays);
        //contLeaseCount
        contLeaseCount.setBoundEditor(txtLeaseCount);
        //contStartDate
        contStartDate.setBoundEditor(pkStartDate);
        //panelRoom
        panelRoom.setLayout(new KDLayout());
        panelRoom.putClientProperty("OriginalBounds", new Rectangle(5, 180, 1008, 216));        tblRoom.setBounds(new Rectangle(10, 89, 982, 111));
        panelRoom.add(tblRoom, new KDLayout.Constraints(10, 89, 982, 111, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contTotalRoomStandardRent.setBounds(new Rectangle(14, 42, 240, 19));
        panelRoom.add(contTotalRoomStandardRent, new KDLayout.Constraints(14, 42, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTotalRoomDealRent.setBounds(new Rectangle(259, 42, 240, 19));
        panelRoom.add(contTotalRoomDealRent, new KDLayout.Constraints(259, 42, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTotalBuildingArea.setBounds(new Rectangle(503, 42, 240, 19));
        panelRoom.add(contTotalBuildingArea, new KDLayout.Constraints(503, 42, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewRoomInfo.setBounds(new Rectangle(750, 42, 107, 19));
        panelRoom.add(btnViewRoomInfo, new KDLayout.Constraints(750, 42, 107, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddRoom.setBounds(new Rectangle(860, 42, 67, 19));
        panelRoom.add(btnAddRoom, new KDLayout.Constraints(860, 42, 67, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemoveRoom.setBounds(new Rectangle(929, 42, 67, 19));
        panelRoom.add(btnRemoveRoom, new KDLayout.Constraints(929, 42, 67, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contTotalRoomStandardRent
        contTotalRoomStandardRent.setBoundEditor(txtTotalRoomStandardRent);
        //contTotalRoomDealRent
        contTotalRoomDealRent.setBoundEditor(txtTotalRoomDealRent);
        //contTotalBuildingArea
        contTotalBuildingArea.setBoundEditor(txtTotalBuildingArea);
        //panelCustomer
        panelCustomer.setLayout(new KDLayout());
        panelCustomer.putClientProperty("OriginalBounds", new Rectangle(7, 407, 1002, 154));        tblCustomer.setBounds(new Rectangle(10, 34, 978, 100));
        panelCustomer.add(tblCustomer, new KDLayout.Constraints(10, 34, 978, 100, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnNewCustomer.setBounds(new Rectangle(768, 12, 83, 19));
        panelCustomer.add(btnNewCustomer, new KDLayout.Constraints(768, 12, 83, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddCustomer.setBounds(new Rectangle(854, 12, 67, 19));
        panelCustomer.add(btnAddCustomer, new KDLayout.Constraints(854, 12, 67, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemoveCustomer.setBounds(new Rectangle(923, 12, 67, 19));
        panelCustomer.add(btnRemoveCustomer, new KDLayout.Constraints(923, 12, 67, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnViewCustInfo.setBounds(new Rectangle(654, 12, 111, 19));
        panelCustomer.add(btnViewCustInfo, new KDLayout.Constraints(654, 12, 111, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //panelContractInfo
        panelContractInfo.setLayout(new KDLayout());
        panelContractInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1005, 588));        contSpecialClause.setBounds(new Rectangle(588, 398, 270, 19));
        panelContractInfo.add(contSpecialClause, new KDLayout.Constraints(588, 398, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttenRevBank.setBounds(new Rectangle(730, 219, 270, 19));
        panelContractInfo.add(conttenRevBank, new KDLayout.Constraints(730, 219, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStandardTotalRent.setBounds(new Rectangle(683, 150, 270, 19));
        panelContractInfo.add(contStandardTotalRent, new KDLayout.Constraints(683, 150, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDeliveryRoomDate.setBounds(new Rectangle(749, 181, 270, 19));
        panelContractInfo.add(contDeliveryRoomDate, new KDLayout.Constraints(749, 181, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDealTotalRent.setBounds(new Rectangle(7, 139, 270, 19));
        panelContractInfo.add(contDealTotalRent, new KDLayout.Constraints(7, 139, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDeductionAmount.setBounds(new Rectangle(620, 10, 270, 19));
        panelContractInfo.add(contDeductionAmount, new KDLayout.Constraints(620, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFirstLeaseEndDate.setBounds(new Rectangle(315, 85, 270, 19));
        panelContractInfo.add(contFirstLeaseEndDate, new KDLayout.Constraints(315, 85, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStartDateLimit.setBounds(new Rectangle(670, 373, 270, 19));
        panelContractInfo.add(contStartDateLimit, new KDLayout.Constraints(670, 373, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSincerObligate.setBounds(new Rectangle(581, 326, 270, 19));
        panelContractInfo.add(contSincerObligate, new KDLayout.Constraints(581, 326, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator8.setBounds(new Rectangle(5, 59, 886, 8));
        panelContractInfo.add(kDSeparator8, new KDLayout.Constraints(5, 59, 886, 8, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator9.setBounds(new Rectangle(11, 134, 886, 10));
        panelContractInfo.add(kDSeparator9, new KDLayout.Constraints(11, 134, 886, 10, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contDepositAmount.setBounds(new Rectangle(618, 416, 113, 19));
        panelContractInfo.add(contDepositAmount, new KDLayout.Constraints(618, 416, 113, 19, 0));
        contfirstTermTenAmo.setBounds(new Rectangle(711, 416, 78, 19));
        panelContractInfo.add(contfirstTermTenAmo, new KDLayout.Constraints(711, 416, 78, 19, 0));
        contRemainDepositAmount.setBounds(new Rectangle(781, 433, 119, 19));
        panelContractInfo.add(contRemainDepositAmount, new KDLayout.Constraints(781, 433, 119, 19, 0));
        contFromMonth.setBounds(new Rectangle(315, 109, 270, 19));
        panelContractInfo.add(contFromMonth, new KDLayout.Constraints(315, 109, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLateFeeAmount.setBounds(new Rectangle(732, 254, 270, 19));
        panelContractInfo.add(contLateFeeAmount, new KDLayout.Constraints(732, 254, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contQuitRoomDate.setBounds(new Rectangle(748, 284, 270, 19));
        panelContractInfo.add(contQuitRoomDate, new KDLayout.Constraints(748, 284, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTenancyAdviser.setBounds(new Rectangle(802, 465, 270, 19));
        panelContractInfo.add(contTenancyAdviser, new KDLayout.Constraints(802, 465, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(793, 481, 270, 19));
        panelContractInfo.add(contSellProject, new KDLayout.Constraints(793, 481, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBussinessDepart.setBounds(new Rectangle(791, 507, 270, 19));
        panelContractInfo.add(contBussinessDepart, new KDLayout.Constraints(791, 507, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contSpecialClause
        contSpecialClause.setBoundEditor(txtSpecialClause);
        //conttenRevBank
        conttenRevBank.setBoundEditor(f7TenRevBank);
        //contStandardTotalRent
        contStandardTotalRent.setBoundEditor(txtStandardTotalRent);
        //contDeliveryRoomDate
        contDeliveryRoomDate.setBoundEditor(pkDeliveryRoomDate);
        //contDealTotalRent
        contDealTotalRent.setBoundEditor(txtDealTotalRent);
        //contDeductionAmount
        contDeductionAmount.setBoundEditor(txtDeductionAmount);
        //contFirstLeaseEndDate
        contFirstLeaseEndDate.setBoundEditor(pkFirstLeaseEndDate);
        //contStartDateLimit
        contStartDateLimit.setBoundEditor(pkStartDateLimit);
        //contSincerObligate
        contSincerObligate.setBoundEditor(f7SincerObligate);
        //contDepositAmount
        contDepositAmount.setBoundEditor(txtDepositAmount);
        //contfirstTermTenAmo
        contfirstTermTenAmo.setBoundEditor(txtFirstPayRent);
        //contRemainDepositAmount
        contRemainDepositAmount.setBoundEditor(txtRemainDepositAmount);
        //contFromMonth
        contFromMonth.setBoundEditor(DPickFromMonth);
        //contLateFeeAmount
        contLateFeeAmount.setBoundEditor(txtLateFeeAmount);
        //contQuitRoomDate
        contQuitRoomDate.setBoundEditor(txtQuitRoomDate);
        //contTenancyAdviser
        contTenancyAdviser.setBoundEditor(f7TenancyAdviser);
        //contSellProject
        contSellProject.setBoundEditor(f7SellProject);
        //contBussinessDepart
        contBussinessDepart.setBoundEditor(f7BussinessDepartMent);
        //panelRentSet
        panelRentSet.setLayout(new KDLayout());
        panelRentSet.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1005, 588));        containerIncrease.setBounds(new Rectangle(3, 114, 450, 132));
        panelRentSet.add(containerIncrease, new KDLayout.Constraints(3, 114, 450, 132, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        containerFree.setBounds(new Rectangle(456, 115, 450, 128));
        panelRentSet.add(containerFree, new KDLayout.Constraints(456, 115, 450, 128, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRentCountType.setBounds(new Rectangle(10, 7, 270, 19));
        panelRentSet.add(contRentCountType, new KDLayout.Constraints(10, 7, 270, 19, 0));
        contDaysPerYear.setBounds(new Rectangle(318, 7, 270, 19));
        panelRentSet.add(contDaysPerYear, new KDLayout.Constraints(318, 7, 270, 19, 0));
        chkIsAutoToInteger.setBounds(new Rectangle(11, 39, 140, 19));
        panelRentSet.add(chkIsAutoToInteger, new KDLayout.Constraints(11, 39, 140, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contToIntegerType.setBounds(new Rectangle(318, 37, 270, 19));
        panelRentSet.add(contToIntegerType, new KDLayout.Constraints(318, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDigit.setBounds(new Rectangle(624, 35, 270, 19));
        panelRentSet.add(contDigit, new KDLayout.Constraints(624, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contMoreRoomsType.setBounds(new Rectangle(623, 7, 270, 19));
        panelRentSet.add(contMoreRoomsType, new KDLayout.Constraints(623, 7, 270, 19, 0));
        chkIsAutoToIntegerForFee.setBounds(new Rectangle(9, 70, 270, 19));
        panelRentSet.add(chkIsAutoToIntegerForFee, new KDLayout.Constraints(9, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contToIntegerType2.setBounds(new Rectangle(318, 68, 270, 19));
        panelRentSet.add(contToIntegerType2, new KDLayout.Constraints(318, 68, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDigit2.setBounds(new Rectangle(626, 65, 270, 19));
        panelRentSet.add(contDigit2, new KDLayout.Constraints(626, 65, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        containerRentSet.setBounds(new Rectangle(1, 248, 906, 296));
        panelRentSet.add(containerRentSet, new KDLayout.Constraints(1, 248, 906, 296, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contRentFreeBill.setBounds(new Rectangle(633, 92, 270, 19));
        panelRentSet.add(contRentFreeBill, new KDLayout.Constraints(633, 92, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //containerIncrease
containerIncrease.getContentPane().setLayout(new BorderLayout(0, 0));        containerIncrease.getContentPane().add(tblIncrease, BorderLayout.CENTER);
        //containerFree
containerFree.getContentPane().setLayout(new BorderLayout(0, 0));        containerFree.getContentPane().add(tblFree, BorderLayout.CENTER);
        //contRentCountType
        contRentCountType.setBoundEditor(comboRentCountType);
        //contDaysPerYear
        contDaysPerYear.setBoundEditor(txtDaysPerYear);
        //contToIntegerType
        contToIntegerType.setBoundEditor(comboToIntegerType);
        //contDigit
        contDigit.setBoundEditor(comboDigit);
        //contMoreRoomsType
        contMoreRoomsType.setBoundEditor(comboMoreRoomsType);
        //contToIntegerType2
        contToIntegerType2.setBoundEditor(comboToIntegerTypeFee);
        //contDigit2
        contDigit2.setBoundEditor(comboDigitFee);
        //containerRentSet
containerRentSet.getContentPane().setLayout(new BorderLayout(0, 0));        containerRentSet.getContentPane().add(tblRentSet, BorderLayout.CENTER);
        //contRentFreeBill
        contRentFreeBill.setBoundEditor(prmtRentFreeBill);
        //panelPayList
        panelPayList.setLayout(new KDLayout());
        panelPayList.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1005, 588));        tblPayList.setBounds(new Rectangle(5, 34, 899, 506));
        panelPayList.add(tblPayList, new KDLayout.Constraints(5, 34, 899, 506, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnDelPayList.setBounds(new Rectangle(821, 11, 77, 19));
        panelPayList.add(btnDelPayList, new KDLayout.Constraints(821, 11, 77, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnAddPayList.setBounds(new Rectangle(743, 11, 76, 19));
        panelPayList.add(btnAddPayList, new KDLayout.Constraints(743, 11, 76, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnInsert.setBounds(new Rectangle(666, 11, 76, 19));
        panelPayList.add(btnInsert, new KDLayout.Constraints(666, 11, 76, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkIsFreeContract.setBounds(new Rectangle(7, 11, 151, 19));
        panelPayList.add(chkIsFreeContract, new KDLayout.Constraints(7, 11, 151, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelProperty
panelProperty.setLayout(new BorderLayout(0, 0));        panelProperty.add(tabMiddle, BorderLayout.CENTER);
        //panelAgency
        panelAgency.setLayout(null);        contAgency.setBounds(new Rectangle(10, 10, 270, 19));
        panelAgency.add(contAgency, null);
        contAgencyContract.setBounds(new Rectangle(316, 10, 270, 19));
        panelAgency.add(contAgencyContract, null);
        contPromissoryAgentFee.setBounds(new Rectangle(620, 10, 270, 19));
        panelAgency.add(contPromissoryAgentFee, null);
        contPromissoryAppPayDate.setBounds(new Rectangle(10, 40, 270, 19));
        panelAgency.add(contPromissoryAppPayDate, null);
        contAgentFee.setBounds(new Rectangle(316, 40, 270, 19));
        panelAgency.add(contAgentFee, null);
        contSettlementType.setBounds(new Rectangle(620, 40, 270, 19));
        panelAgency.add(contSettlementType, null);
        contAppPayDate.setBounds(new Rectangle(10, 70, 270, 19));
        panelAgency.add(contAppPayDate, null);
        contAgentDes.setBounds(new Rectangle(316, 70, 573, 19));
        panelAgency.add(contAgentDes, null);
        //contAgency
        contAgency.setBoundEditor(f7Agency);
        //contAgencyContract
        contAgencyContract.setBoundEditor(f7AgencyContract);
        //contPromissoryAgentFee
        contPromissoryAgentFee.setBoundEditor(txtPromissoryAgentFee);
        //contPromissoryAppPayDate
        contPromissoryAppPayDate.setBoundEditor(pkPromissoryAppPayDate);
        //contAgentFee
        contAgentFee.setBoundEditor(txtAgentFee);
        //contSettlementType
        contSettlementType.setBoundEditor(f7SettlementType);
        //contAppPayDate
        contAppPayDate.setBoundEditor(pkAppPayDate);
        //contAgentDes
        contAgentDes.setBoundEditor(txtAgentDes);
        //pnlOtherPayList
        pnlOtherPayList.setLayout(new KDLayout());
        pnlOtherPayList.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1005, 588));        tblOtherPayList.setBounds(new Rectangle(5, 48, 992, 536));
        pnlOtherPayList.add(tblOtherPayList, new KDLayout.Constraints(5, 48, 992, 536, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnAddOtherPaylist.setBounds(new Rectangle(819, 11, 79, 19));
        pnlOtherPayList.add(btnAddOtherPaylist, new KDLayout.Constraints(819, 11, 79, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnDelOtherPaylist.setBounds(new Rectangle(912, 11, 81, 19));
        pnlOtherPayList.add(btnDelOtherPaylist, new KDLayout.Constraints(912, 11, 81, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //panelLiquidated
        panelLiquidated.setLayout(null);        chkIsAccLiquidated.setBounds(new Rectangle(29, 18, 140, 19));
        panelLiquidated.add(chkIsAccLiquidated, null);
        chkIsMDLiquidated.setBounds(new Rectangle(200, 18, 181, 19));
        panelLiquidated.add(chkIsMDLiquidated, null);
        panMDLiquidated.setBounds(new Rectangle(26, 250, 491, 355));
        panelLiquidated.add(panMDLiquidated, null);
        panLiquidated.setBounds(new Rectangle(26, 49, 657, 195));
        panelLiquidated.add(panLiquidated, null);
        //panMDLiquidated
        panMDLiquidated.setLayout(null);        tblLiquidated.setBounds(new Rectangle(13, 12, 461, 321));
        panMDLiquidated.add(tblLiquidated, null);
        //panLiquidated
        panLiquidated.setLayout(null);        contRate.setBounds(new Rectangle(7, 19, 227, 19));
        panLiquidated.add(contRate, null);
        cbRateDate.setBounds(new Rectangle(237, 19, 65, 19));
        panLiquidated.add(cbRateDate, null);
        contRelief.setBounds(new Rectangle(345, 69, 227, 19));
        panLiquidated.add(contRelief, null);
        contStandard.setBounds(new Rectangle(7, 69, 165, 19));
        panLiquidated.add(contStandard, null);
        contBit.setBounds(new Rectangle(9, 119, 294, 19));
        panLiquidated.add(contBit, null);
        cbReliefDate.setBounds(new Rectangle(575, 69, 65, 19));
        panLiquidated.add(cbReliefDate, null);
        cbStandardDate.setBounds(new Rectangle(237, 69, 65, 19));
        panLiquidated.add(cbStandardDate, null);
        txtStandard.setBounds(new Rectangle(174, 69, 60, 19));
        panLiquidated.add(txtStandard, null);
        contFinalAmt.setBounds(new Rectangle(345, 19, 227, 19));
        panLiquidated.add(contFinalAmt, null);
        cbFinalDate.setBounds(new Rectangle(575, 19, 65, 19));
        panLiquidated.add(cbFinalDate, null);
        //contRate
        contRate.setBoundEditor(txtRate);
        //contRelief
        contRelief.setBoundEditor(txtRelief);
        //contStandard
        contStandard.setBoundEditor(cbOccurred);
        //contBit
        contBit.setBoundEditor(cbBit);
        //contFinalAmt
        contFinalAmt.setBoundEditor(txtFinalAmount);
        //kDPaneLongContract
        kDPaneLongContract.setLayout(null);        btnDeleteLong.setBounds(new Rectangle(807, 8, 91, 19));
        kDPaneLongContract.add(btnDeleteLong, null);
        btnAddLong.setBounds(new Rectangle(706, 9, 90, 18));
        kDPaneLongContract.add(btnAddLong, null);
        tblLongContract.setBounds(new Rectangle(4, 41, 797, 393));
        kDPaneLongContract.add(tblLongContract, null);
        //panelCommissionSetting
        panelCommissionSetting.setLayout(new KDLayout());
        panelCommissionSetting.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1005, 588));        kDLabelContainer1.setBounds(new Rectangle(632, 10, 270, 19));
        panelCommissionSetting.add(kDLabelContainer1, new KDLayout.Constraints(632, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer2.setBounds(new Rectangle(321, 45, 270, 19));
        panelCommissionSetting.add(kDLabelContainer2, new KDLayout.Constraints(321, 45, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(321, 10, 270, 19));
        panelCommissionSetting.add(kDLabelContainer3, new KDLayout.Constraints(321, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(11, 10, 270, 19));
        panelCommissionSetting.add(kDLabelContainer4, new KDLayout.Constraints(11, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(11, 45, 270, 19));
        panelCommissionSetting.add(kDLabelContainer5, new KDLayout.Constraints(11, 45, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(632, 45, 270, 19));
        panelCommissionSetting.add(kDLabelContainer6, new KDLayout.Constraints(632, 45, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane1.setBounds(new Rectangle(4, 100, 901, 205));
        panelCommissionSetting.add(kDScrollPane1, new KDLayout.Constraints(4, 100, 901, 205, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(4, 309, 901, 227));
        panelCommissionSetting.add(kDContainer1, new KDLayout.Constraints(4, 309, 901, 227, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        combStepCalSetting.setBounds(new Rectangle(11, 74, 140, 19));
        panelCommissionSetting.add(combStepCalSetting, new KDLayout.Constraints(11, 74, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        combExceedAccumulation.setBounds(new Rectangle(160, 74, 140, 19));
        panelCommissionSetting.add(combExceedAccumulation, new KDLayout.Constraints(160, 74, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtMinRent);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(f7MoneyDefine);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtMinCommission);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtScale);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(comboMoneyType);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(comboEnhanceType);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(tblStepCalculateSetting, null);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblBusinessIncome, BorderLayout.CENTER);
        //tabbedPaneRoom
        tabbedPaneRoom.add(panelAttachRes, resHelper.getString("panelAttachRes.constraints"));
        //panelAttachRes
panelAttachRes.setLayout(new BorderLayout(0, 0));        panelAttachRes.add(tblAttachRes, BorderLayout.CENTER);
        panelAttachRes.add(panelAttachResInfo, BorderLayout.NORTH);
        //panelAttachResInfo
        panelAttachResInfo.setLayout(new KDLayout());
        panelAttachResInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 45, 54));        btnAddAttachRes.setBounds(new Rectangle(746, 10, 67, 19));
        panelAttachResInfo.add(btnAddAttachRes, new KDLayout.Constraints(746, 10, 67, 19, 0));
        btnRemoveAttachRes.setBounds(new Rectangle(832, 10, 67, 19));
        panelAttachResInfo.add(btnRemoveAttachRes, new KDLayout.Constraints(832, 10, 67, 19, 0));
        contTotalAttachResStandardRent.setBounds(new Rectangle(10, 10, 270, 19));
        panelAttachResInfo.add(contTotalAttachResStandardRent, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contTotalAttachResDealRent.setBounds(new Rectangle(346, 10, 270, 19));
        panelAttachResInfo.add(contTotalAttachResDealRent, new KDLayout.Constraints(346, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        //contTotalAttachResStandardRent
        contTotalAttachResStandardRent.setBoundEditor(txtTotalAttachResStandardRent);
        //contTotalAttachResDealRent
        contTotalAttachResDealRent.setBoundEditor(txtTotalAttachResDealRent);
        panelTenancyRoomInfo.setLayout(new KDLayout());
        panelTenancyRoomInfo.putClientProperty("OriginalBounds", new Rectangle(395, 141, 81, 23));
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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
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
        menuBiz.add(menuItemCarryForward);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
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
        this.toolBar.add(btnCarryForward);
        this.toolBar.add(btnCollectProtocol);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isByAgency", boolean.class, this.chkIsByAgency, "selected");
		dataBinder.registerBinding("tenancyType", com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum.class, this.comboTenancyType, "selectedItem");
		dataBinder.registerBinding("tenancyDate", java.util.Date.class, this.pkTenancyDate, "value");
		dataBinder.registerBinding("oldTenancyBill", com.kingdee.eas.fdc.tenancy.TenancyBillInfo.class, this.f7OldContract, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("changeDes", String.class, this.txtChangeDes, "text");
		dataBinder.registerBinding("tenancyName", String.class, this.txtName, "text");
		dataBinder.registerBinding("flagAtTerm", com.kingdee.eas.fdc.tenancy.FlagAtTermEnum.class, this.comboFlagAtTerm, "selectedItem");
		dataBinder.registerBinding("chargeDateType", com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum.class, this.comboChargeDateType, "selectedItem");
		dataBinder.registerBinding("firstLeaseType", com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum.class, this.comboFirstLeaseType, "selectedItem");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.pkEndDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("leaseTime", int.class, this.spinLeaseTime, "value");
		dataBinder.registerBinding("chargeOffsetDays", int.class, this.spinChargeOffsetDays, "value");
		dataBinder.registerBinding("leaseCount", java.math.BigDecimal.class, this.txtLeaseCount, "value");
		dataBinder.registerBinding("startDate", java.util.Date.class, this.pkStartDate, "value");
		dataBinder.registerBinding("tenancyRoomList.roomEntryId", String.class, this.tblRoom, "roomEntryId.text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.f7Creator, "data");
		dataBinder.registerBinding("specialClause", String.class, this.txtSpecialClause, "text");
		dataBinder.registerBinding("payeeBank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.f7TenRevBank, "data");
		dataBinder.registerBinding("standardTotalRent", java.math.BigDecimal.class, this.txtStandardTotalRent, "value");
		dataBinder.registerBinding("deliveryRoomDate", java.util.Date.class, this.pkDeliveryRoomDate, "value");
		dataBinder.registerBinding("dealTotalRent", java.math.BigDecimal.class, this.txtDealTotalRent, "value");
		dataBinder.registerBinding("deductAmount", java.math.BigDecimal.class, this.txtDeductionAmount, "value");
		dataBinder.registerBinding("firstLeaseEndDate", java.util.Date.class, this.pkFirstLeaseEndDate, "value");
		dataBinder.registerBinding("sincerObligate", com.kingdee.eas.fdc.tenancy.SincerObligateInfo.class, this.f7SincerObligate, "data");
		dataBinder.registerBinding("depositAmount", java.math.BigDecimal.class, this.txtDepositAmount, "value");
		dataBinder.registerBinding("firstPayRent", java.math.BigDecimal.class, this.txtFirstPayRent, "value");
		dataBinder.registerBinding("remainDepositAmount", java.math.BigDecimal.class, this.txtRemainDepositAmount, "value");
		dataBinder.registerBinding("fixedDateFromMonth", java.util.Date.class, this.DPickFromMonth, "value");
		dataBinder.registerBinding("lateFeeAmount", java.math.BigDecimal.class, this.txtLateFeeAmount, "value");
		dataBinder.registerBinding("tenancyAdviser", com.kingdee.eas.base.permission.UserInfo.class, this.f7TenancyAdviser, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.crm.basedata.SellProjectInfo.class, this.f7SellProject, "data");
		dataBinder.registerBinding("rentFreeBill", com.kingdee.eas.fdc.tenancy.RentFreeBillInfo.class, this.prmtRentFreeBill, "data");
		dataBinder.registerBinding("agencyContract", com.kingdee.eas.fdc.tenancy.AgencyContractInfo.class, this.f7AgencyContract, "data");
		dataBinder.registerBinding("promissoryAgentFee", java.math.BigDecimal.class, this.txtPromissoryAgentFee, "value");
		dataBinder.registerBinding("promissoryAppPayDate", java.util.Date.class, this.pkPromissoryAppPayDate, "value");
		dataBinder.registerBinding("agentFee", java.math.BigDecimal.class, this.txtAgentFee, "value");
		dataBinder.registerBinding("settlementType", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.f7SettlementType, "data");
		dataBinder.registerBinding("appPayDate", java.util.Date.class, this.pkAppPayDate, "value");
		dataBinder.registerBinding("agentDes", String.class, this.txtAgentDes, "text");
		dataBinder.registerBinding("isAccountLiquidated", boolean.class, this.chkIsAccLiquidated, "selected");
		dataBinder.registerBinding("isMDLiquidated", boolean.class, this.chkIsMDLiquidated, "selected");
		dataBinder.registerBinding("rateDate", int.class, this.cbRateDate, "selectedItem");
		dataBinder.registerBinding("reliefDate", int.class, this.cbReliefDate, "selectedItem");
		dataBinder.registerBinding("standardDate", int.class, this.cbStandardDate, "selectedItem");
		dataBinder.registerBinding("standard", int.class, this.txtStandard, "value");
		dataBinder.registerBinding("rate", java.math.BigDecimal.class, this.txtRate, "value");
		dataBinder.registerBinding("relief", int.class, this.txtRelief, "value");
		dataBinder.registerBinding("occurred", int.class, this.cbOccurred, "selectedItem");
		dataBinder.registerBinding("bit", int.class, this.cbBit, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.TenancyBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Sale",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Sale");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("isByAgency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldTenancyBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeDes", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("flagAtTerm", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chargeDateType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("firstLeaseType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leaseTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chargeOffsetDays", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leaseCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyRoomList.roomEntryId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("specialClause", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payeeBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("standardTotalRent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("deliveryRoomDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dealTotalRent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("deductAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("firstLeaseEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sincerObligate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("depositAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("firstPayRent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remainDepositAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fixedDateFromMonth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lateFeeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyAdviser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rentFreeBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("agencyContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("promissoryAgentFee", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("promissoryAppPayDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("agentFee", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("appPayDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("agentDes", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isAccountLiquidated", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isMDLiquidated", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rateDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reliefDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("standardDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("standard", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relief", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("occurred", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bit", ValidateHelper.ON_SAVE);    		
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
     * output chkIsByAgency_actionPerformed method
     */
    protected void chkIsByAgency_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output pkTenancyDate_dataChanged method
     */
    protected void pkTenancyDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboRentStartType_itemStateChanged method
     */
    protected void comboRentStartType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboFlagAtTerm_itemStateChanged method
     */
    protected void comboFlagAtTerm_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboChargeDateType_itemStateChanged method
     */
    protected void comboChargeDateType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboFirstLeaseType_itemStateChanged method
     */
    protected void comboFirstLeaseType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output pkEndDate_dataChanged method
     */
    protected void pkEndDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtDescription_actionPerformed method
     */
    protected void txtDescription_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output pkFristLeaseDate_dataChanged method
     */
    protected void pkFristLeaseDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkStartDate_dataChanged method
     */
    protected void pkStartDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblRoom_editStopped method
     */
    protected void tblRoom_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnViewRoomInfo_actionPerformed method
     */
    protected void btnViewRoomInfo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
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
     * output tblCustomerInfo_editStopped method
     */
    protected void tblCustomerInfo_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblCustomer_tableClicked method
     */
    protected void tblCustomer_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnAddNewCustomer_actionPerformed method
     */
    protected void btnAddNewCustomer_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnAddCustomer_actionPerformed method
     */
    protected void btnAddCustomer_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnRemoveCustomer_actionPerformed method
     */
    protected void btnRemoveCustomer_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnViewCustInfo_actionPerformed method
     */
    protected void btnViewCustInfo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output pkFirstLeaseEndDate_dataChanged method
     */
    protected void pkFirstLeaseEndDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7SincerObligate_dataChanged method
     */
    protected void f7SincerObligate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output DPickFromMonth_dataChanged method
     */
    protected void DPickFromMonth_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output chkIsAutoToInteger_actionPerformed method
     */
    protected void chkIsAutoToInteger_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkIsAutoToIntegerForFee_actionPerformed method
     */
    protected void chkIsAutoToIntegerForFee_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblIncrease_editStopped method
     */
    protected void tblIncrease_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblFree_editStopped method
     */
    protected void tblFree_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output comboRentCountType_itemStateChanged method
     */
    protected void comboRentCountType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output txtDaysPerYear_dataChanged method
     */
    protected void txtDaysPerYear_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
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
     * output comboMoreRoomsType_itemStateChanged method
     */
    protected void comboMoreRoomsType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output comboToIntegerTypeFee_itemStateChanged method
     */
    protected void comboToIntegerTypeFee_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboDigitFee_itemStateChanged method
     */
    protected void comboDigitFee_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblRentSet_editStopped method
     */
    protected void tblRentSet_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output prmtRentFreeBill_dataChanged method
     */
    protected void prmtRentFreeBill_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblPayList_editStopped method
     */
    protected void tblPayList_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnDelPayList_actionPerformed method
     */
    protected void btnDelPayList_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnAddPayList_actionPerformed method
     */
    protected void btnAddPayList_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnInsert_actionPerformed method
     */
    protected void btnInsert_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output chkIsFreeContract_stateChanged method
     */
    protected void chkIsFreeContract_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output chkIsFreeContract_actionPerformed method
     */
    protected void chkIsFreeContract_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7Agency_dataChanged method
     */
    protected void f7Agency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7AgencyContract_dataChanged method
     */
    protected void f7AgencyContract_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output btnAddOtherPaylist_actionPerformed method
     */
    protected void btnAddOtherPaylist_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnDelOtherPaylist_actionPerformed method
     */
    protected void btnDelOtherPaylist_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output chkIsAccLiquidated_actionPerformed method
     */
    protected void chkIsAccLiquidated_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output chkIsMDLiquidated_actionPerformed method
     */
    protected void chkIsMDLiquidated_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtRate_dataChanged method
     */
    protected void txtRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output btnDeleteLong_actionPerformed method
     */
    protected void btnDeleteLong_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddLong_actionPerformed method
     */
    protected void btnAddLong_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblLongContract_editStopped method
     */
    protected void tblLongContract_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output combStepCalSetting_actionPerformed method
     */
    protected void combStepCalSetting_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtMinRent_dataChanged method
     */
    protected void txtMinRent_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtMinRent_focusLost method
     */
    protected void txtMinRent_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtMinCommission_dataChanged method
     */
    protected void txtMinCommission_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtMinCommission_focusLost method
     */
    protected void txtMinCommission_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtScale_dataChanged method
     */
    protected void txtScale_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboEnhanceType_itemStateChanged method
     */
    protected void comboEnhanceType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblStepCalculateSetting_editStopped method
     */
    protected void tblStepCalculateSetting_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblAttachRes_editStopped method
     */
    protected void tblAttachRes_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnAddAttachRes_actionPerformed method
     */
    protected void btnAddAttachRes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnRemoveAttachRes_actionPerformed method
     */
    protected void btnRemoveAttachRes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("isByAgency"));
        sic.add(new SelectorItemInfo("tenancyType"));
        sic.add(new SelectorItemInfo("tenancyDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("oldTenancyBill.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("oldTenancyBill.id"));
        	sic.add(new SelectorItemInfo("oldTenancyBill.number"));
        	sic.add(new SelectorItemInfo("oldTenancyBill.name"));
        	sic.add(new SelectorItemInfo("oldTenancyBill.tenancyName"));
		}
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("changeDes"));
        sic.add(new SelectorItemInfo("tenancyName"));
        sic.add(new SelectorItemInfo("flagAtTerm"));
        sic.add(new SelectorItemInfo("chargeDateType"));
        sic.add(new SelectorItemInfo("firstLeaseType"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("leaseTime"));
        sic.add(new SelectorItemInfo("chargeOffsetDays"));
        sic.add(new SelectorItemInfo("leaseCount"));
        sic.add(new SelectorItemInfo("startDate"));
    	sic.add(new SelectorItemInfo("tenancyRoomList.roomEntryId"));
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("specialClause"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payeeBank.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("payeeBank.id"));
        	sic.add(new SelectorItemInfo("payeeBank.number"));
        	sic.add(new SelectorItemInfo("payeeBank.name"));
		}
        sic.add(new SelectorItemInfo("standardTotalRent"));
        sic.add(new SelectorItemInfo("deliveryRoomDate"));
        sic.add(new SelectorItemInfo("dealTotalRent"));
        sic.add(new SelectorItemInfo("deductAmount"));
        sic.add(new SelectorItemInfo("firstLeaseEndDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("sincerObligate.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("sincerObligate.id"));
        	sic.add(new SelectorItemInfo("sincerObligate.number"));
        	sic.add(new SelectorItemInfo("sincerObligate.name"));
		}
        sic.add(new SelectorItemInfo("depositAmount"));
        sic.add(new SelectorItemInfo("firstPayRent"));
        sic.add(new SelectorItemInfo("remainDepositAmount"));
        sic.add(new SelectorItemInfo("fixedDateFromMonth"));
        sic.add(new SelectorItemInfo("lateFeeAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("tenancyAdviser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("tenancyAdviser.id"));
        	sic.add(new SelectorItemInfo("tenancyAdviser.number"));
        	sic.add(new SelectorItemInfo("tenancyAdviser.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("sellProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("sellProject.id"));
        	sic.add(new SelectorItemInfo("sellProject.number"));
        	sic.add(new SelectorItemInfo("sellProject.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("rentFreeBill.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("rentFreeBill.id"));
        	sic.add(new SelectorItemInfo("rentFreeBill.number"));
        	sic.add(new SelectorItemInfo("rentFreeBill.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("agencyContract.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("agencyContract.id"));
        	sic.add(new SelectorItemInfo("agencyContract.number"));
        	sic.add(new SelectorItemInfo("agencyContract.name"));
		}
        sic.add(new SelectorItemInfo("promissoryAgentFee"));
        sic.add(new SelectorItemInfo("promissoryAppPayDate"));
        sic.add(new SelectorItemInfo("agentFee"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("settlementType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("settlementType.id"));
        	sic.add(new SelectorItemInfo("settlementType.number"));
        	sic.add(new SelectorItemInfo("settlementType.name"));
		}
        sic.add(new SelectorItemInfo("appPayDate"));
        sic.add(new SelectorItemInfo("agentDes"));
        sic.add(new SelectorItemInfo("isAccountLiquidated"));
        sic.add(new SelectorItemInfo("isMDLiquidated"));
        sic.add(new SelectorItemInfo("rateDate"));
        sic.add(new SelectorItemInfo("reliefDate"));
        sic.add(new SelectorItemInfo("standardDate"));
        sic.add(new SelectorItemInfo("standard"));
        sic.add(new SelectorItemInfo("rate"));
        sic.add(new SelectorItemInfo("relief"));
        sic.add(new SelectorItemInfo("occurred"));
        sic.add(new SelectorItemInfo("bit"));
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
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    	

    /**
     * output actionCarryForward_actionPerformed method
     */
    public void actionCarryForward_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddCollectProtocol_actionPerformed method
     */
    public void actionAddCollectProtocol_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAddNew(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNew() {
    	return false;
    }
	public RequestContext prepareActionCarryForward(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCarryForward() {
    	return false;
    }
	public RequestContext prepareActionAddCollectProtocol(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddCollectProtocol() {
    	return false;
    }

    /**
     * output ActionCarryForward class
     */     
    protected class ActionCarryForward extends ItemAction {     
    
        public ActionCarryForward()
        {
            this(null);
        }

        public ActionCarryForward(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCarryForward.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCarryForward.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCarryForward.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillEditUI.this, "ActionCarryForward", "actionCarryForward_actionPerformed", e);
        }
    }

    /**
     * output ActionAddCollectProtocol class
     */     
    protected class ActionAddCollectProtocol extends ItemAction {     
    
        public ActionAddCollectProtocol()
        {
            this(null);
        }

        public ActionAddCollectProtocol(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddCollectProtocol.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCollectProtocol.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCollectProtocol.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyBillEditUI.this, "ActionAddCollectProtocol", "actionAddCollectProtocol_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "TenancyBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.tenancy.client.TenancyBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.tenancy.TenancyBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.tenancy.TenancyBillInfo objectValue = new com.kingdee.eas.fdc.tenancy.TenancyBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/tenancy/TenancyBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return tblRoom;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}