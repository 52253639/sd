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
public abstract class AbstractPurchaseEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPurchaseEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlCustomerInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoomInfo;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabPurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSeller;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSincerityPurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSecondSeller;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conRecommendInsider;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conRecommendCard;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCustomerInfo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddNewCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDown;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUp;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSearch;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton modifyName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton modifyInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSubarea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloor;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSelectRoom;
    protected com.kingdee.bos.ctrl.swing.KDButton btnEditAttach;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellOrder;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttachmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsFitmentToContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentStandard;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttachRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAreaCompensateAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellType;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcFloor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSubarea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuilding;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomNumber;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiUnit;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiFloor;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField kDFormattedTextField2;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField kDFormattedTextField3;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField kDFormattedTextField4;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellOrder;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAttachmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFitmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFitmentStandard;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFitmentPrice;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAttachRoom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAreaCompensateAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSellType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingFloor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtStandardBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtStandardRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelPrePurchaseInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelPurchaseBill;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlPayList;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelOtherPay;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdPrePanelNorth;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPreLayTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrePurchaseAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrePurchaseCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrePurchaseDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrePurchaseChecker;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrePurchaseCheckTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPreValidDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPreLevelMoney;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPrePurchaseAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PrePurchaseCurrency;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPrePurchaseDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PrePurchaseChecker;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPrePurchaseCheckTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPreValidDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPreFinishDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsSellBySet;
    protected com.kingdee.bos.ctrl.swing.KDButton btnChooseAgio;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgioDes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgio;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurchaseAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurchaseCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurchaseDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSpecialAgio;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanSignTimeLimit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriceAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSpecialAgioType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSignDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PayType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAgioDes;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAgio;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPurchaseAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PurchaseCurrency;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPurchaseDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSpecialAgio;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7DealCurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPlanSignTimeLimit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPriceAccount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSpecialAgioType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkSignDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPayList;
    protected com.kingdee.bos.ctrl.swing.KDPanel northPanel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemovePayEntry;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddPayEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAFundAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLoanAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLittleAdjust;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboDigit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInsertPayEntry;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAFundAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLoanAmount;
    protected com.kingdee.bos.ctrl.swing.KDPanel panel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblOtherPay;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddOtherPay;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveOtherPay;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInsertOtherPay;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCreat;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Seller;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SincerityPurchase;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDes;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox pkCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7secondSeller;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRecommendInsider;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRecommendCard;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPurchasePrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPurchasePrintView;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton1;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemModifyName;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPurchasePrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPurchasePrintView;
    protected com.kingdee.eas.fdc.sellhouse.PurchaseInfo editData = null;
    protected ActionPurchasePrint actionPurchasePrint = null;
    protected ActionPurchasePrintview actionPurchasePrintview = null;
    protected ActionViewRoom actionViewRoom = null;
    protected ActionViewSign actionViewSign = null;
    protected ActionReceive actionReceive = null;
    protected ActionUp actionUp = null;
    protected ActionDown actionDown = null;
    protected ActionAddNewCustomer actionAddNewCustomer = null;
    protected ActionModifyName actionModifyName = null;
    protected actionCreat actionCreat = null;
    /**
     * output class constructor
     */
    public AbstractPurchaseEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPurchaseEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        String _tempStr = null;
        actionSave.setEnabled(true);
        actionSave.setDaemonRun(false);

        actionSave.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl S"));
        _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
        actionSave.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
        actionSave.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.NAME");
        actionSave.putValue(ItemAction.NAME, _tempStr);
        this.actionSave.setBindWorkFlow(true);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmit
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
        //actionFirst
        actionFirst.setEnabled(false);
        actionFirst.setDaemonRun(false);

        actionFirst.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl <"));
        _tempStr = resHelper.getString("ActionFirst.SHORT_DESCRIPTION");
        actionFirst.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionFirst.LONG_DESCRIPTION");
        actionFirst.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionFirst.NAME");
        actionFirst.putValue(ItemAction.NAME, _tempStr);
        this.actionFirst.setBindWorkFlow(true);
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPurchasePrint
        this.actionPurchasePrint = new ActionPurchasePrint(this);
        getActionManager().registerAction("actionPurchasePrint", actionPurchasePrint);
         this.actionPurchasePrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPurchasePrintview
        this.actionPurchasePrintview = new ActionPurchasePrintview(this);
        getActionManager().registerAction("actionPurchasePrintview", actionPurchasePrintview);
         this.actionPurchasePrintview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewRoom
        this.actionViewRoom = new ActionViewRoom(this);
        getActionManager().registerAction("actionViewRoom", actionViewRoom);
         this.actionViewRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewSign
        this.actionViewSign = new ActionViewSign(this);
        getActionManager().registerAction("actionViewSign", actionViewSign);
         this.actionViewSign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceive
        this.actionReceive = new ActionReceive(this);
        getActionManager().registerAction("actionReceive", actionReceive);
        this.actionReceive.setBindWorkFlow(true);
         this.actionReceive.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUp
        this.actionUp = new ActionUp(this);
        getActionManager().registerAction("actionUp", actionUp);
         this.actionUp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDown
        this.actionDown = new ActionDown(this);
        getActionManager().registerAction("actionDown", actionDown);
         this.actionDown.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddNewCustomer
        this.actionAddNewCustomer = new ActionAddNewCustomer(this);
        getActionManager().registerAction("actionAddNewCustomer", actionAddNewCustomer);
         this.actionAddNewCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionModifyName
        this.actionModifyName = new ActionModifyName(this);
        getActionManager().registerAction("actionModifyName", actionModifyName);
         this.actionModifyName.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCreat
        this.actionCreat = new actionCreat(this);
        getActionManager().registerAction("actionCreat", actionCreat);
         this.actionCreat.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.pnlCustomerInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlRoomInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tabPurchase = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSeller = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSincerityPurchase = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSecondSeller = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conRecommendInsider = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conRecommendCard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tblCustomerInfo = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddNewCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDown = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUp = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSearch = new com.kingdee.bos.ctrl.swing.KDButton();
        this.modifyName = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.modifyInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSubarea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSelectRoom = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnEditAttach = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellOrder = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttachmentAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFitmentAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsFitmentToContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contFitmentStandard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFitmentPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttachRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAreaCompensateAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewRoom = new com.kingdee.bos.ctrl.swing.KDButton();
        this.lcFloor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSubarea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBuilding = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRoomNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.spiUnit = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiFloor = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDFormattedTextField2 = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDFormattedTextField3 = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDFormattedTextField4 = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7RoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSellOrder = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAttachmentAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFitmentAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFitmentStandard = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtFitmentPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAttachRoom = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtStandardTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActualRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAreaCompensateAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboSellType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7BuildingFloor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtStandardBuildingPrice = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtStandardRoomPrice = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.panelPrePurchaseInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelPurchaseBill = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlPayList = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelOtherPay = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdPrePanelNorth = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblPreLayTime = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPrePurchaseAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPrePurchaseCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPrePurchaseDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPrePurchaseChecker = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPrePurchaseCheckTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPreValidDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPreLevelMoney = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPrePurchaseAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7PrePurchaseCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkPrePurchaseDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7PrePurchaseChecker = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkPrePurchaseCheckTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkPreValidDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkPreFinishDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contPayType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsSellBySet = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnChooseAgio = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contContractTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgioDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgio = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPurchaseAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPurchaseCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPurchaseDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSpecialAgio = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealBuildPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractBuildPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanSignTimeLimit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPriceAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSpecialAgioType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSignDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7PayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtContractTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAgioDes = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAgio = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPurchaseAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7PurchaseCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkPurchaseDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtSpecialAgio = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7DealCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDealTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDealBuildPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDealRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractBuildPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPlanSignTimeLimit = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboPriceAccount = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboSpecialAgioType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkSignDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.tblPayList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.northPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnRemovePayEntry = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddPayEntry = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contAFundAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLoanAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnLittleAdjust = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.comboDigit = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnInsertPayEntry = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtAFundAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLoanAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.panel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblOtherPay = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddOtherPay = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveOtherPay = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInsertOtherPay = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCreat = new com.kingdee.bos.ctrl.swing.KDButton();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Seller = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7SincerityPurchase = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDes = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.pkCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7secondSeller = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRecommendInsider = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtRecommendCard = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnPurchasePrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPurchasePrintView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDWorkButton1 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemModifyName = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPurchasePrint = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPurchasePrintView = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.pnlCustomerInfo.setName("pnlCustomerInfo");
        this.pnlRoomInfo.setName("pnlRoomInfo");
        this.tabPurchase.setName("tabPurchase");
        this.contNumber.setName("contNumber");
        this.contSeller.setName("contSeller");
        this.contCreateDate.setName("contCreateDate");
        this.contSincerityPurchase.setName("contSincerityPurchase");
        this.contDes.setName("contDes");
        this.contCreator.setName("contCreator");
        this.contSecondSeller.setName("contSecondSeller");
        this.conRecommendInsider.setName("conRecommendInsider");
        this.conRecommendCard.setName("conRecommendCard");
        this.tblCustomerInfo.setName("tblCustomerInfo");
        this.btnAddCustomer.setName("btnAddCustomer");
        this.btnDeleteCustomer.setName("btnDeleteCustomer");
        this.btnAddNewCustomer.setName("btnAddNewCustomer");
        this.btnDown.setName("btnDown");
        this.btnUp.setName("btnUp");
        this.btnSearch.setName("btnSearch");
        this.modifyName.setName("modifyName");
        this.modifyInfo.setName("modifyInfo");
        this.contProject.setName("contProject");
        this.contSubarea.setName("contSubarea");
        this.contBuilding.setName("contBuilding");
        this.contRoomNumber.setName("contRoomNumber");
        this.contUnit.setName("contUnit");
        this.contFloor.setName("contFloor");
        this.btnSelectRoom.setName("btnSelectRoom");
        this.btnEditAttach.setName("btnEditAttach");
        this.contBuildingArea.setName("contBuildingArea");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.contRoomArea.setName("contRoomArea");
        this.contRoomModel.setName("contRoomModel");
        this.contSellOrder.setName("contSellOrder");
        this.contAttachmentAmount.setName("contAttachmentAmount");
        this.contFitmentAmount.setName("contFitmentAmount");
        this.chkIsFitmentToContract.setName("chkIsFitmentToContract");
        this.contFitmentStandard.setName("contFitmentStandard");
        this.contFitmentPrice.setName("contFitmentPrice");
        this.contAttachRoom.setName("contAttachRoom");
        this.contStandardTotalAmount.setName("contStandardTotalAmount");
        this.contActualBuildingArea.setName("contActualBuildingArea");
        this.contActualRoomArea.setName("contActualRoomArea");
        this.contAreaCompensateAmount.setName("contAreaCompensateAmount");
        this.contSellType.setName("contSellType");
        this.btnViewRoom.setName("btnViewRoom");
        this.lcFloor.setName("lcFloor");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.txtProject.setName("txtProject");
        this.txtSubarea.setName("txtSubarea");
        this.txtBuilding.setName("txtBuilding");
        this.txtRoomNumber.setName("txtRoomNumber");
        this.spiUnit.setName("spiUnit");
        this.spiFloor.setName("spiFloor");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.kDFormattedTextField2.setName("kDFormattedTextField2");
        this.kDFormattedTextField3.setName("kDFormattedTextField3");
        this.kDFormattedTextField4.setName("kDFormattedTextField4");
        this.txtRoomArea.setName("txtRoomArea");
        this.f7RoomModel.setName("f7RoomModel");
        this.txtSellOrder.setName("txtSellOrder");
        this.txtAttachmentAmount.setName("txtAttachmentAmount");
        this.txtFitmentAmount.setName("txtFitmentAmount");
        this.txtFitmentStandard.setName("txtFitmentStandard");
        this.txtFitmentPrice.setName("txtFitmentPrice");
        this.txtAttachRoom.setName("txtAttachRoom");
        this.txtStandardTotalAmount.setName("txtStandardTotalAmount");
        this.txtActualBuildingArea.setName("txtActualBuildingArea");
        this.txtActualRoomArea.setName("txtActualRoomArea");
        this.txtAreaCompensateAmount.setName("txtAreaCompensateAmount");
        this.comboSellType.setName("comboSellType");
        this.f7BuildingFloor.setName("f7BuildingFloor");
        this.txtStandardBuildingPrice.setName("txtStandardBuildingPrice");
        this.txtStandardRoomPrice.setName("txtStandardRoomPrice");
        this.panelPrePurchaseInfo.setName("panelPrePurchaseInfo");
        this.panelPurchaseBill.setName("panelPurchaseBill");
        this.pnlPayList.setName("pnlPayList");
        this.panelOtherPay.setName("panelOtherPay");
        this.kdPrePanelNorth.setName("kdPrePanelNorth");
        this.tblPreLayTime.setName("tblPreLayTime");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contPrePurchaseAmount.setName("contPrePurchaseAmount");
        this.contPrePurchaseCurrency.setName("contPrePurchaseCurrency");
        this.contPrePurchaseDate.setName("contPrePurchaseDate");
        this.contPrePurchaseChecker.setName("contPrePurchaseChecker");
        this.contPrePurchaseCheckTime.setName("contPrePurchaseCheckTime");
        this.contPreValidDate.setName("contPreValidDate");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtPreLevelMoney.setName("txtPreLevelMoney");
        this.txtPrePurchaseAmount.setName("txtPrePurchaseAmount");
        this.f7PrePurchaseCurrency.setName("f7PrePurchaseCurrency");
        this.pkPrePurchaseDate.setName("pkPrePurchaseDate");
        this.f7PrePurchaseChecker.setName("f7PrePurchaseChecker");
        this.pkPrePurchaseCheckTime.setName("pkPrePurchaseCheckTime");
        this.pkPreValidDate.setName("pkPreValidDate");
        this.pkPreFinishDate.setName("pkPreFinishDate");
        this.contPayType.setName("contPayType");
        this.chkIsSellBySet.setName("chkIsSellBySet");
        this.btnChooseAgio.setName("btnChooseAgio");
        this.contContractTotalAmount.setName("contContractTotalAmount");
        this.contContractRoomPrice.setName("contContractRoomPrice");
        this.contAgioDes.setName("contAgioDes");
        this.contAgio.setName("contAgio");
        this.contPurchaseAmount.setName("contPurchaseAmount");
        this.contPurchaseCurrency.setName("contPurchaseCurrency");
        this.contPurchaseDate.setName("contPurchaseDate");
        this.contSpecialAgio.setName("contSpecialAgio");
        this.contDealCurrency.setName("contDealCurrency");
        this.contDealTotalAmount.setName("contDealTotalAmount");
        this.contDealBuildPrice.setName("contDealBuildPrice");
        this.contDealRoomPrice.setName("contDealRoomPrice");
        this.contContractBuildPrice.setName("contContractBuildPrice");
        this.contPlanSignTimeLimit.setName("contPlanSignTimeLimit");
        this.contPriceAccount.setName("contPriceAccount");
        this.contSpecialAgioType.setName("contSpecialAgioType");
        this.contSignDate.setName("contSignDate");
        this.f7PayType.setName("f7PayType");
        this.txtContractTotalAmount.setName("txtContractTotalAmount");
        this.txtContractRoomPrice.setName("txtContractRoomPrice");
        this.txtAgioDes.setName("txtAgioDes");
        this.txtAgio.setName("txtAgio");
        this.txtPurchaseAmount.setName("txtPurchaseAmount");
        this.f7PurchaseCurrency.setName("f7PurchaseCurrency");
        this.pkPurchaseDate.setName("pkPurchaseDate");
        this.txtSpecialAgio.setName("txtSpecialAgio");
        this.f7DealCurrency.setName("f7DealCurrency");
        this.txtDealTotalAmount.setName("txtDealTotalAmount");
        this.txtDealBuildPrice.setName("txtDealBuildPrice");
        this.txtDealRoomPrice.setName("txtDealRoomPrice");
        this.txtContractBuildPrice.setName("txtContractBuildPrice");
        this.txtPlanSignTimeLimit.setName("txtPlanSignTimeLimit");
        this.comboPriceAccount.setName("comboPriceAccount");
        this.comboSpecialAgioType.setName("comboSpecialAgioType");
        this.pkSignDate.setName("pkSignDate");
        this.tblPayList.setName("tblPayList");
        this.northPanel.setName("northPanel");
        this.btnRemovePayEntry.setName("btnRemovePayEntry");
        this.btnAddPayEntry.setName("btnAddPayEntry");
        this.contAFundAmount.setName("contAFundAmount");
        this.contLoanAmount.setName("contLoanAmount");
        this.btnLittleAdjust.setName("btnLittleAdjust");
        this.comboDigit.setName("comboDigit");
        this.btnInsertPayEntry.setName("btnInsertPayEntry");
        this.txtAFundAmount.setName("txtAFundAmount");
        this.txtLoanAmount.setName("txtLoanAmount");
        this.panel.setName("panel");
        this.tblOtherPay.setName("tblOtherPay");
        this.btnAddOtherPay.setName("btnAddOtherPay");
        this.btnRemoveOtherPay.setName("btnRemoveOtherPay");
        this.btnInsertOtherPay.setName("btnInsertOtherPay");
        this.btnCreat.setName("btnCreat");
        this.txtNumber.setName("txtNumber");
        this.f7Seller.setName("f7Seller");
        this.pkCreateDate.setName("pkCreateDate");
        this.f7SincerityPurchase.setName("f7SincerityPurchase");
        this.txtDes.setName("txtDes");
        this.pkCreator.setName("pkCreator");
        this.f7secondSeller.setName("f7secondSeller");
        this.prmtRecommendInsider.setName("prmtRecommendInsider");
        this.txtRecommendCard.setName("txtRecommendCard");
        this.btnPurchasePrint.setName("btnPurchasePrint");
        this.btnPurchasePrintView.setName("btnPurchasePrintView");
        this.kDWorkButton1.setName("kDWorkButton1");
        this.menuItemModifyName.setName("menuItemModifyName");
        this.menuItemPurchasePrint.setName("menuItemPurchasePrint");
        this.menuItemPurchasePrintView.setName("menuItemPurchasePrintView");
        // CoreUI		
        this.setPreferredSize(new Dimension(930,630));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));		
        this.menuSubmitOption.setVisible(false);		
        this.kDSeparator5.setVisible(false);
        // pnlCustomerInfo		
        this.pnlCustomerInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlCustomerInfo.border.title")));
        // pnlRoomInfo		
        this.pnlRoomInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlRoomInfo.border.title")));
        // tabPurchase		
        this.tabPurchase.setAutoscrolls(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contSeller		
        this.contSeller.setBoundLabelText(resHelper.getString("contSeller.boundLabelText"));		
        this.contSeller.setBoundLabelLength(100);		
        this.contSeller.setBoundLabelUnderline(true);
        // contCreateDate		
        this.contCreateDate.setBoundLabelText(resHelper.getString("contCreateDate.boundLabelText"));		
        this.contCreateDate.setBoundLabelLength(100);		
        this.contCreateDate.setBoundLabelUnderline(true);
        // contSincerityPurchase		
        this.contSincerityPurchase.setBoundLabelText(resHelper.getString("contSincerityPurchase.boundLabelText"));		
        this.contSincerityPurchase.setBoundLabelLength(100);		
        this.contSincerityPurchase.setBoundLabelUnderline(true);
        // contDes		
        this.contDes.setBoundLabelText(resHelper.getString("contDes.boundLabelText"));		
        this.contDes.setBoundLabelLength(100);		
        this.contDes.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setBoundLabelLength(100);
        // contSecondSeller		
        this.contSecondSeller.setBoundLabelText(resHelper.getString("contSecondSeller.boundLabelText"));		
        this.contSecondSeller.setBoundLabelLength(100);		
        this.contSecondSeller.setBoundLabelUnderline(true);
        // conRecommendInsider		
        this.conRecommendInsider.setBoundLabelText(resHelper.getString("conRecommendInsider.boundLabelText"));		
        this.conRecommendInsider.setBoundLabelLength(100);		
        this.conRecommendInsider.setBoundLabelUnderline(true);
        // conRecommendCard		
        this.conRecommendCard.setBoundLabelText(resHelper.getString("conRecommendCard.boundLabelText"));		
        this.conRecommendCard.setBoundLabelLength(100);		
        this.conRecommendCard.setBoundLabelUnderline(true);		
        this.conRecommendCard.setEnabled(false);
        // tblCustomerInfo
		String tblCustomerInfoStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"propertyPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"certificateNumber\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"certificateName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"mailAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"postalcode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"sex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"bookDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"salesId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"customerType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{propertyPercent}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{certificateNumber}</t:Cell><t:Cell>$Resource{certificateName}</t:Cell><t:Cell>$Resource{mailAddress}</t:Cell><t:Cell>$Resource{postalcode}</t:Cell><t:Cell>$Resource{sex}</t:Cell><t:Cell>$Resource{bookDate}</t:Cell><t:Cell>$Resource{des}</t:Cell><t:Cell>$Resource{salesId}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{customerType}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblCustomerInfo.setFormatXml(resHelper.translateString("tblCustomerInfo",tblCustomerInfoStrXML));
        this.tblCustomerInfo.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblCustomerInfo_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblCustomerInfo.putBindContents("editData",new String[] {"","","","","","","","","","","","alesman.id","",""});


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
        // btnDeleteCustomer		
        this.btnDeleteCustomer.setText(resHelper.getString("btnDeleteCustomer.text"));
        this.btnDeleteCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeleteCustomer_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAddNewCustomer
        this.btnAddNewCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNewCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddNewCustomer.setText(resHelper.getString("btnAddNewCustomer.text"));
        this.btnAddNewCustomer.addActionListener(new java.awt.event.ActionListener() {
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
        // btnDown
        this.btnDown.setAction((IItemAction)ActionProxyFactory.getProxy(actionDown, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDown.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_movedown"));		
        this.btnDown.setText(resHelper.getString("btnDown.text"));
        // btnUp
        this.btnUp.setAction((IItemAction)ActionProxyFactory.getProxy(actionUp, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUp.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_movetop"));		
        this.btnUp.setText(resHelper.getString("btnUp.text"));
        // btnSearch		
        this.btnSearch.setText(resHelper.getString("btnSearch.text"));
        this.btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSearch_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // modifyName
        this.modifyName.setAction((IItemAction)ActionProxyFactory.getProxy(actionModifyName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.modifyName.setText(resHelper.getString("modifyName.text"));
        // modifyInfo
        this.modifyInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionModifyName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.modifyInfo.setText(resHelper.getString("modifyInfo.text"));
        this.modifyInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    modifyInfo_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contSubarea		
        this.contSubarea.setBoundLabelText(resHelper.getString("contSubarea.boundLabelText"));		
        this.contSubarea.setBoundLabelLength(100);		
        this.contSubarea.setBoundLabelUnderline(true);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // contRoomNumber		
        this.contRoomNumber.setBoundLabelText(resHelper.getString("contRoomNumber.boundLabelText"));		
        this.contRoomNumber.setBoundLabelLength(100);		
        this.contRoomNumber.setBoundLabelUnderline(true);
        // contUnit		
        this.contUnit.setBoundLabelText(resHelper.getString("contUnit.boundLabelText"));		
        this.contUnit.setBoundLabelLength(100);		
        this.contUnit.setBoundLabelUnderline(true);
        // contFloor		
        this.contFloor.setBoundLabelText(resHelper.getString("contFloor.boundLabelText"));		
        this.contFloor.setBoundLabelLength(100);		
        this.contFloor.setBoundLabelUnderline(true);		
        this.contFloor.setVisible(false);
        // btnSelectRoom		
        this.btnSelectRoom.setText(resHelper.getString("btnSelectRoom.text"));		
        this.btnSelectRoom.setVerticalTextPosition(3);
        this.btnSelectRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSelectRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnEditAttach		
        this.btnEditAttach.setText(resHelper.getString("btnEditAttach.text"));
        this.btnEditAttach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnEditAttach_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelLength(100);		
        this.contBuildingArea.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelLength(100);		
        this.contRoomArea.setBoundLabelUnderline(true);
        // contRoomModel		
        this.contRoomModel.setBoundLabelText(resHelper.getString("contRoomModel.boundLabelText"));		
        this.contRoomModel.setBoundLabelLength(100);		
        this.contRoomModel.setBoundLabelUnderline(true);
        // contSellOrder		
        this.contSellOrder.setBoundLabelText(resHelper.getString("contSellOrder.boundLabelText"));		
        this.contSellOrder.setBoundLabelLength(100);		
        this.contSellOrder.setBoundLabelUnderline(true);
        // contAttachmentAmount		
        this.contAttachmentAmount.setBoundLabelText(resHelper.getString("contAttachmentAmount.boundLabelText"));		
        this.contAttachmentAmount.setBoundLabelLength(100);		
        this.contAttachmentAmount.setBoundLabelUnderline(true);
        // contFitmentAmount		
        this.contFitmentAmount.setBoundLabelText(resHelper.getString("contFitmentAmount.boundLabelText"));		
        this.contFitmentAmount.setBoundLabelLength(100);		
        this.contFitmentAmount.setBoundLabelUnderline(true);
        // chkIsFitmentToContract		
        this.chkIsFitmentToContract.setText(resHelper.getString("chkIsFitmentToContract.text"));
        this.chkIsFitmentToContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsFitmentToContract_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contFitmentStandard		
        this.contFitmentStandard.setBoundLabelText(resHelper.getString("contFitmentStandard.boundLabelText"));		
        this.contFitmentStandard.setBoundLabelUnderline(true);		
        this.contFitmentStandard.setBoundLabelLength(100);
        // contFitmentPrice		
        this.contFitmentPrice.setBoundLabelText(resHelper.getString("contFitmentPrice.boundLabelText"));		
        this.contFitmentPrice.setBoundLabelLength(100);		
        this.contFitmentPrice.setBoundLabelUnderline(true);
        // contAttachRoom		
        this.contAttachRoom.setBoundLabelText(resHelper.getString("contAttachRoom.boundLabelText"));		
        this.contAttachRoom.setBoundLabelLength(100);		
        this.contAttachRoom.setBoundLabelUnderline(true);
        // contStandardTotalAmount		
        this.contStandardTotalAmount.setBoundLabelText(resHelper.getString("contStandardTotalAmount.boundLabelText"));		
        this.contStandardTotalAmount.setBoundLabelLength(100);		
        this.contStandardTotalAmount.setBoundLabelUnderline(true);
        // contActualBuildingArea		
        this.contActualBuildingArea.setBoundLabelText(resHelper.getString("contActualBuildingArea.boundLabelText"));		
        this.contActualBuildingArea.setBoundLabelLength(100);		
        this.contActualBuildingArea.setBoundLabelUnderline(true);
        // contActualRoomArea		
        this.contActualRoomArea.setBoundLabelText(resHelper.getString("contActualRoomArea.boundLabelText"));		
        this.contActualRoomArea.setBoundLabelUnderline(true);		
        this.contActualRoomArea.setBoundLabelLength(100);
        // contAreaCompensateAmount		
        this.contAreaCompensateAmount.setBoundLabelText(resHelper.getString("contAreaCompensateAmount.boundLabelText"));		
        this.contAreaCompensateAmount.setBoundLabelLength(100);		
        this.contAreaCompensateAmount.setBoundLabelUnderline(true);
        // contSellType		
        this.contSellType.setBoundLabelText(resHelper.getString("contSellType.boundLabelText"));		
        this.contSellType.setBoundLabelUnderline(true);		
        this.contSellType.setBoundLabelLength(100);
        // btnViewRoom
        this.btnViewRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewRoom.setText(resHelper.getString("btnViewRoom.text"));		
        this.btnViewRoom.setVisible(false);
        // lcFloor		
        this.lcFloor.setBoundLabelText(resHelper.getString("lcFloor.boundLabelText"));		
        this.lcFloor.setBoundLabelLength(100);		
        this.lcFloor.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // txtProject		
        this.txtProject.setEnabled(false);
        // txtSubarea		
        this.txtSubarea.setEnabled(false);
        // txtBuilding		
        this.txtBuilding.setEnabled(false);
        // txtRoomNumber		
        this.txtRoomNumber.setEnabled(false);
        // spiUnit		
        this.spiUnit.setEnabled(false);
        // spiFloor		
        this.spiFloor.setEnabled(false);
        // txtBuildingArea		
        this.txtBuildingArea.setDataType(1);
        // kDFormattedTextField2
        // kDFormattedTextField3
        // kDFormattedTextField4
        // txtRoomArea		
        this.txtRoomArea.setDataType(1);
        // f7RoomModel		
        this.f7RoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");
        // txtSellOrder
        // txtAttachmentAmount		
        this.txtAttachmentAmount.setDataType(1);
        this.txtAttachmentAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtAttachmentAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtFitmentAmount		
        this.txtFitmentAmount.setDataType(1);
        this.txtFitmentAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtFitmentAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtFitmentStandard		
        this.txtFitmentStandard.setMaxLength(70);
        // txtFitmentPrice		
        this.txtFitmentPrice.setDataType(1);
        this.txtFitmentPrice.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtFitmentPrice_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtAttachRoom
        // txtStandardTotalAmount		
        this.txtStandardTotalAmount.setDataType(1);
        // txtActualBuildingArea		
        this.txtActualBuildingArea.setDataType(1);
        // txtActualRoomArea		
        this.txtActualRoomArea.setDataType(1);
        // txtAreaCompensateAmount		
        this.txtAreaCompensateAmount.setDataType(1);
        // comboSellType		
        this.comboSellType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SellTypeEnum").toArray());
        this.comboSellType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboSellType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7BuildingFloor		
        this.f7BuildingFloor.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingFloorEntryQuery");		
        this.f7BuildingFloor.setCommitFormat("$flooralias$");		
        this.f7BuildingFloor.setDisplayFormat("$flooralias$");		
        this.f7BuildingFloor.setEditFormat("$flooralias$");		
        this.f7BuildingFloor.setEnabled(false);
        // txtStandardBuildingPrice		
        this.txtStandardBuildingPrice.setEnabled(false);		
        this.txtStandardBuildingPrice.setHorizontalAlignment(4);
        this.txtStandardBuildingPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtStandardBuildingPrice_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtStandardRoomPrice		
        this.txtStandardRoomPrice.setEnabled(false);		
        this.txtStandardRoomPrice.setHorizontalAlignment(4);
        // panelPrePurchaseInfo		
        this.panelPrePurchaseInfo.setOpaque(false);		
        this.panelPrePurchaseInfo.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),1));
        // panelPurchaseBill		
        this.panelPurchaseBill.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),1));
        // pnlPayList		
        this.pnlPayList.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),1));
        // panelOtherPay
        // kdPrePanelNorth		
        this.kdPrePanelNorth.setPreferredSize(new Dimension(10,70));
        // tblPreLayTime
		String tblPreLayTimeStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"oldPrePur\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"oldPreValid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"newPrePur\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"newPreValid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{oldPrePur}</t:Cell><t:Cell>$Resource{oldPreValid}</t:Cell><t:Cell>$Resource{newPrePur}</t:Cell><t:Cell>$Resource{newPreValid}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblPreLayTime.setFormatXml(resHelper.translateString("tblPreLayTime",tblPreLayTimeStrXML));
        this.tblPreLayTime.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblPreLayTime_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // contPrePurchaseAmount		
        this.contPrePurchaseAmount.setBoundLabelText(resHelper.getString("contPrePurchaseAmount.boundLabelText"));		
        this.contPrePurchaseAmount.setBoundLabelLength(100);		
        this.contPrePurchaseAmount.setBoundLabelUnderline(true);
        // contPrePurchaseCurrency		
        this.contPrePurchaseCurrency.setBoundLabelText(resHelper.getString("contPrePurchaseCurrency.boundLabelText"));		
        this.contPrePurchaseCurrency.setBoundLabelLength(100);		
        this.contPrePurchaseCurrency.setBoundLabelUnderline(true);
        // contPrePurchaseDate		
        this.contPrePurchaseDate.setBoundLabelText(resHelper.getString("contPrePurchaseDate.boundLabelText"));		
        this.contPrePurchaseDate.setBoundLabelLength(100);		
        this.contPrePurchaseDate.setBoundLabelUnderline(true);
        // contPrePurchaseChecker		
        this.contPrePurchaseChecker.setBoundLabelText(resHelper.getString("contPrePurchaseChecker.boundLabelText"));		
        this.contPrePurchaseChecker.setBoundLabelLength(100);		
        this.contPrePurchaseChecker.setBoundLabelUnderline(true);
        // contPrePurchaseCheckTime		
        this.contPrePurchaseCheckTime.setBoundLabelText(resHelper.getString("contPrePurchaseCheckTime.boundLabelText"));		
        this.contPrePurchaseCheckTime.setBoundLabelUnderline(true);		
        this.contPrePurchaseCheckTime.setBoundLabelLength(100);
        // contPreValidDate		
        this.contPreValidDate.setBoundLabelText(resHelper.getString("contPreValidDate.boundLabelText"));		
        this.contPreValidDate.setBoundLabelLength(100);		
        this.contPreValidDate.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // txtPreLevelMoney		
        this.txtPreLevelMoney.setRequired(true);		
        this.txtPreLevelMoney.setDataType(1);		
        this.txtPreLevelMoney.setPrecision(2);
        // txtPrePurchaseAmount		
        this.txtPrePurchaseAmount.setDataType(1);		
        this.txtPrePurchaseAmount.setRequired(true);
        // f7PrePurchaseCurrency		
        this.f7PrePurchaseCurrency.setDisplayFormat("$name$");		
        this.f7PrePurchaseCurrency.setEditFormat("$number$");		
        this.f7PrePurchaseCurrency.setCommitFormat("$number$");		
        this.f7PrePurchaseCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.f7PrePurchaseCurrency.setEnabled(false);
        // pkPrePurchaseDate		
        this.pkPrePurchaseDate.setRequired(true);
        // f7PrePurchaseChecker		
        this.f7PrePurchaseChecker.setEnabled(false);
        // pkPrePurchaseCheckTime		
        this.pkPrePurchaseCheckTime.setEnabled(false);
        // pkPreValidDate
        // pkPreFinishDate		
        this.pkPreFinishDate.setEnabled(false);
        // contPayType		
        this.contPayType.setBoundLabelText(resHelper.getString("contPayType.boundLabelText"));		
        this.contPayType.setBoundLabelLength(100);		
        this.contPayType.setBoundLabelUnderline(true);
        // chkIsSellBySet		
        this.chkIsSellBySet.setText(resHelper.getString("chkIsSellBySet.text"));
        this.chkIsSellBySet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsSellBySet_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnChooseAgio		
        this.btnChooseAgio.setText(resHelper.getString("btnChooseAgio.text"));
        this.btnChooseAgio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnChooseAgio_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contContractTotalAmount		
        this.contContractTotalAmount.setBoundLabelText(resHelper.getString("contContractTotalAmount.boundLabelText"));		
        this.contContractTotalAmount.setBoundLabelLength(100);		
        this.contContractTotalAmount.setBoundLabelUnderline(true);
        // contContractRoomPrice		
        this.contContractRoomPrice.setBoundLabelText(resHelper.getString("contContractRoomPrice.boundLabelText"));		
        this.contContractRoomPrice.setBoundLabelLength(100);		
        this.contContractRoomPrice.setBoundLabelUnderline(true);
        // contAgioDes		
        this.contAgioDes.setBoundLabelText(resHelper.getString("contAgioDes.boundLabelText"));		
        this.contAgioDes.setBoundLabelLength(100);		
        this.contAgioDes.setBoundLabelUnderline(true);
        // contAgio		
        this.contAgio.setBoundLabelText(resHelper.getString("contAgio.boundLabelText"));		
        this.contAgio.setBoundLabelLength(100);		
        this.contAgio.setBoundLabelUnderline(true);
        // contPurchaseAmount		
        this.contPurchaseAmount.setBoundLabelText(resHelper.getString("contPurchaseAmount.boundLabelText"));		
        this.contPurchaseAmount.setBoundLabelLength(100);		
        this.contPurchaseAmount.setBoundLabelUnderline(true);
        // contPurchaseCurrency		
        this.contPurchaseCurrency.setBoundLabelText(resHelper.getString("contPurchaseCurrency.boundLabelText"));		
        this.contPurchaseCurrency.setBoundLabelLength(100);		
        this.contPurchaseCurrency.setBoundLabelUnderline(true);
        // contPurchaseDate		
        this.contPurchaseDate.setBoundLabelText(resHelper.getString("contPurchaseDate.boundLabelText"));		
        this.contPurchaseDate.setBoundLabelLength(100);		
        this.contPurchaseDate.setBoundLabelUnderline(true);
        // contSpecialAgio		
        this.contSpecialAgio.setBoundLabelText(resHelper.getString("contSpecialAgio.boundLabelText"));		
        this.contSpecialAgio.setBoundLabelLength(100);		
        this.contSpecialAgio.setBoundLabelUnderline(true);
        // contDealCurrency		
        this.contDealCurrency.setBoundLabelText(resHelper.getString("contDealCurrency.boundLabelText"));		
        this.contDealCurrency.setBoundLabelLength(100);		
        this.contDealCurrency.setBoundLabelUnderline(true);
        // contDealTotalAmount		
        this.contDealTotalAmount.setBoundLabelText(resHelper.getString("contDealTotalAmount.boundLabelText"));		
        this.contDealTotalAmount.setBoundLabelLength(100);		
        this.contDealTotalAmount.setBoundLabelUnderline(true);
        // contDealBuildPrice		
        this.contDealBuildPrice.setBoundLabelText(resHelper.getString("contDealBuildPrice.boundLabelText"));		
        this.contDealBuildPrice.setBoundLabelLength(100);		
        this.contDealBuildPrice.setBoundLabelUnderline(true);
        // contDealRoomPrice		
        this.contDealRoomPrice.setBoundLabelText(resHelper.getString("contDealRoomPrice.boundLabelText"));		
        this.contDealRoomPrice.setBoundLabelLength(100);		
        this.contDealRoomPrice.setBoundLabelUnderline(true);
        // contContractBuildPrice		
        this.contContractBuildPrice.setBoundLabelText(resHelper.getString("contContractBuildPrice.boundLabelText"));		
        this.contContractBuildPrice.setBoundLabelLength(100);		
        this.contContractBuildPrice.setBoundLabelUnderline(true);
        // contPlanSignTimeLimit		
        this.contPlanSignTimeLimit.setBoundLabelText(resHelper.getString("contPlanSignTimeLimit.boundLabelText"));		
        this.contPlanSignTimeLimit.setBoundLabelUnderline(true);		
        this.contPlanSignTimeLimit.setBoundLabelLength(100);
        // contPriceAccount		
        this.contPriceAccount.setBoundLabelText(resHelper.getString("contPriceAccount.boundLabelText"));		
        this.contPriceAccount.setBoundLabelLength(100);		
        this.contPriceAccount.setBoundLabelUnderline(true);
        // contSpecialAgioType		
        this.contSpecialAgioType.setBoundLabelText(resHelper.getString("contSpecialAgioType.boundLabelText"));		
        this.contSpecialAgioType.setBoundLabelLength(100);		
        this.contSpecialAgioType.setBoundLabelUnderline(true);
        // contSignDate		
        this.contSignDate.setBoundLabelText(resHelper.getString("contSignDate.boundLabelText"));		
        this.contSignDate.setBoundLabelLength(100);
        // f7PayType		
        this.f7PayType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHEPayTypeQuery");		
        this.f7PayType.setDisplayFormat("$name$");		
        this.f7PayType.setEditFormat("$number$");		
        this.f7PayType.setCommitFormat("$number$");
        this.f7PayType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7PayType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtContractTotalAmount		
        this.txtContractTotalAmount.setDataType(1);
        this.txtContractTotalAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtContractTotalAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtContractRoomPrice		
        this.txtContractRoomPrice.setDataType(1);
        // txtAgioDes
        // txtAgio		
        this.txtAgio.setDataType(1);
        // txtPurchaseAmount		
        this.txtPurchaseAmount.setDataType(1);
        // f7PurchaseCurrency		
        this.f7PurchaseCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.f7PurchaseCurrency.setCommitFormat("$number$");		
        this.f7PurchaseCurrency.setEditFormat("$number$");		
        this.f7PurchaseCurrency.setDisplayFormat("$name$");
        // pkPurchaseDate		
        this.pkPurchaseDate.setRequired(true);
        this.pkPurchaseDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkPurchaseDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtSpecialAgio		
        this.txtSpecialAgio.setDataType(1);
        this.txtSpecialAgio.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtSpecialAgio_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7DealCurrency		
        this.f7DealCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.f7DealCurrency.setDisplayFormat("$name$");		
        this.f7DealCurrency.setEditFormat("$number$");		
        this.f7DealCurrency.setCommitFormat("$number$");
        // txtDealTotalAmount		
        this.txtDealTotalAmount.setDataType(1);
        // txtDealBuildPrice		
        this.txtDealBuildPrice.setDataType(1);		
        this.txtDealBuildPrice.setSupportedEmpty(true);
        this.txtDealBuildPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtDealBuildPrice_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtDealRoomPrice		
        this.txtDealRoomPrice.setDataType(1);
        // txtContractBuildPrice		
        this.txtContractBuildPrice.setDataType(1);
        this.txtContractBuildPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtContractBuildPrice_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtPlanSignTimeLimit		
        this.txtPlanSignTimeLimit.setEnabled(false);
        // comboPriceAccount		
        this.comboPriceAccount.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum").toArray());
        // comboSpecialAgioType		
        this.comboSpecialAgioType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum").toArray());
        // pkSignDate		
        this.pkSignDate.setRequired(true);
        this.pkSignDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkSignDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblPayList
		String tblPayListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol6\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"term\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jiange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"actDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"hasRemitAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"refundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"hasToFeeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{term}</t:Cell><t:Cell>$Resource{jiange}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{actDate}</t:Cell><t:Cell>$Resource{hasRemitAmount}</t:Cell><t:Cell>$Resource{refundmentAmount}</t:Cell><t:Cell>$Resource{hasToFeeAmount}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblPayList.setFormatXml(resHelper.translateString("tblPayList",tblPayListStrXML));
        this.tblPayList.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblPayList_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblPayList.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblPayList_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // northPanel		
        this.northPanel.setPreferredSize(new Dimension(10,35));
        // btnRemovePayEntry		
        this.btnRemovePayEntry.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        this.btnRemovePayEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRemovePayEntry_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAddPayEntry		
        this.btnAddPayEntry.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        this.btnAddPayEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddPayEntry_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contAFundAmount		
        this.contAFundAmount.setBoundLabelText(resHelper.getString("contAFundAmount.boundLabelText"));		
        this.contAFundAmount.setBoundLabelLength(100);		
        this.contAFundAmount.setBoundLabelUnderline(true);
        // contLoanAmount		
        this.contLoanAmount.setBoundLabelText(resHelper.getString("contLoanAmount.boundLabelText"));		
        this.contLoanAmount.setBoundLabelLength(100);		
        this.contLoanAmount.setBoundLabelUnderline(true);
        // btnLittleAdjust		
        this.btnLittleAdjust.setText(resHelper.getString("btnLittleAdjust.text"));		
        this.btnLittleAdjust.setToolTipText(resHelper.getString("btnLittleAdjust.toolTipText"));
        this.btnLittleAdjust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnLittleAdjust_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // comboDigit
        // btnInsertPayEntry		
        this.btnInsertPayEntry.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_insert"));
        this.btnInsertPayEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnInsertPayEntry_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtAFundAmount		
        this.txtAFundAmount.setDataType(1);		
        this.txtAFundAmount.setEnabled(false);
        // txtLoanAmount		
        this.txtLoanAmount.setDataType(1);		
        this.txtLoanAmount.setEnabled(false);
        // panel		
        this.panel.setPreferredSize(new Dimension(10,35));
        // tblOtherPay
		String tblOtherPayStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"refundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"collectionID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{actDate}</t:Cell><t:Cell>$Resource{refundmentAmount}</t:Cell><t:Cell>$Resource{des}</t:Cell><t:Cell>$Resource{collectionID}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblOtherPay.setFormatXml(resHelper.translateString("tblOtherPay",tblOtherPayStrXML));
        this.tblOtherPay.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblOtherPay_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblOtherPay.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblOtherPay_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblOtherPay.putBindContents("editData",new String[] {"","","","","","","","","sheCollection"});


        // btnAddOtherPay		
        this.btnAddOtherPay.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        this.btnAddOtherPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddOtherPay_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRemoveOtherPay		
        this.btnRemoveOtherPay.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        this.btnRemoveOtherPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRemoveOtherPay_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnInsertOtherPay		
        this.btnInsertOtherPay.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_insert"));
        this.btnInsertOtherPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnInsertOtherPay_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnCreat
        this.btnCreat.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreat, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCreat.setText(resHelper.getString("btnCreat.text"));
        // txtNumber
        // f7Seller		
        this.f7Seller.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Seller.setDisplayFormat("$name$");		
        this.f7Seller.setEditFormat("$number$");		
        this.f7Seller.setCommitFormat("$number$");
        this.f7Seller.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Seller_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkCreateDate
        // f7SincerityPurchase		
        this.f7SincerityPurchase.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SincerityPurchaseQuery");		
        this.f7SincerityPurchase.setDisplayFormat("$number$");		
        this.f7SincerityPurchase.setEditFormat("$number$");		
        this.f7SincerityPurchase.setCommitFormat("$number$");
        this.f7SincerityPurchase.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SincerityPurchase_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtDes
        // pkCreator		
        this.pkCreator.setDisplayFormat("$name$");		
        this.pkCreator.setEditFormat("$number$");		
        this.pkCreator.setCommitFormat("$number$");
        // f7secondSeller		
        this.f7secondSeller.setDisplayFormat("$name$");		
        this.f7secondSeller.setEditFormat("$number$");		
        this.f7secondSeller.setCommitFormat("$number$");		
        this.f7secondSeller.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7secondSeller.setEnabledMultiSelection(true);
        // prmtRecommendInsider		
        this.prmtRecommendInsider.setDisplayFormat("$customerName$");		
        this.prmtRecommendInsider.setEditFormat("$customerName$");		
        this.prmtRecommendInsider.setCommitFormat("$customerName$");		
        this.prmtRecommendInsider.setQueryInfo("com.kingdee.eas.fdc.insider.app.InsiderQuery");
        this.prmtRecommendInsider.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRecommendInsider_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtRecommendCard		
        this.txtRecommendCard.setMaxLength(80);		
        this.txtRecommendCard.setEnabled(false);
        // btnPurchasePrint
        this.btnPurchasePrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchasePrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPurchasePrint.setText(resHelper.getString("btnPurchasePrint.text"));		
        this.btnPurchasePrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // btnPurchasePrintView
        this.btnPurchasePrintView.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchasePrintview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPurchasePrintView.setText(resHelper.getString("btnPurchasePrintView.text"));		
        this.btnPurchasePrintView.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // kDWorkButton1
        this.kDWorkButton1.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewSign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDWorkButton1.setText(resHelper.getString("kDWorkButton1.text"));		
        this.kDWorkButton1.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_view"));
        // menuItemModifyName
        this.menuItemModifyName.setAction((IItemAction)ActionProxyFactory.getProxy(actionModifyName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemModifyName.setText(resHelper.getString("menuItemModifyName.text"));		
        this.menuItemModifyName.setEnabled(false);		
        this.menuItemModifyName.setVisible(false);
        // menuItemPurchasePrint
        this.menuItemPurchasePrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchasePrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPurchasePrint.setText(resHelper.getString("menuItemPurchasePrint.text"));		
        this.menuItemPurchasePrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // menuItemPurchasePrintView
        this.menuItemPurchasePrintView.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchasePrintview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPurchasePrintView.setText(resHelper.getString("menuItemPurchasePrintView.text"));		
        this.menuItemPurchasePrintView.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
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
        this.setBounds(new Rectangle(10, 10, 930, 700));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 930, 700));
        kDSeparator5.setBounds(new Rectangle(2, 767, 944, 10));
        this.add(kDSeparator5, new KDLayout.Constraints(2, 767, 944, 10, 0));
        pnlCustomerInfo.setBounds(new Rectangle(4, 226, 921, 127));
        this.add(pnlCustomerInfo, new KDLayout.Constraints(4, 226, 921, 127, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlRoomInfo.setBounds(new Rectangle(4, 1, 921, 229));
        this.add(pnlRoomInfo, new KDLayout.Constraints(4, 1, 921, 229, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        tabPurchase.setBounds(new Rectangle(12, 441, 904, 253));
        this.add(tabPurchase, new KDLayout.Constraints(12, 441, 904, 253, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(10, 351, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 351, 270, 19, 0));
        contSeller.setBounds(new Rectangle(320, 351, 270, 19));
        this.add(contSeller, new KDLayout.Constraints(320, 351, 270, 19, 0));
        contCreateDate.setBounds(new Rectangle(615, 351, 270, 19));
        this.add(contCreateDate, new KDLayout.Constraints(615, 351, 270, 19, 0));
        contSincerityPurchase.setBounds(new Rectangle(11, 373, 270, 19));
        this.add(contSincerityPurchase, new KDLayout.Constraints(11, 373, 270, 19, 0));
        contDes.setBounds(new Rectangle(11, 418, 877, 19));
        this.add(contDes, new KDLayout.Constraints(11, 418, 877, 19, 0));
        contCreator.setBounds(new Rectangle(614, 373, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(614, 373, 270, 19, 0));
        contSecondSeller.setBounds(new Rectangle(319, 373, 270, 19));
        this.add(contSecondSeller, new KDLayout.Constraints(319, 373, 270, 19, 0));
        conRecommendInsider.setBounds(new Rectangle(11, 395, 270, 19));
        this.add(conRecommendInsider, new KDLayout.Constraints(11, 395, 270, 19, 0));
        conRecommendCard.setBounds(new Rectangle(320, 395, 270, 19));
        this.add(conRecommendCard, new KDLayout.Constraints(320, 395, 270, 19, 0));
        //pnlCustomerInfo
        pnlCustomerInfo.setLayout(new KDLayout());
        pnlCustomerInfo.putClientProperty("OriginalBounds", new Rectangle(4, 226, 921, 127));        tblCustomerInfo.setBounds(new Rectangle(10, 34, 897, 81));
        pnlCustomerInfo.add(tblCustomerInfo, new KDLayout.Constraints(10, 34, 897, 81, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnAddCustomer.setBounds(new Rectangle(380, 12, 67, 19));
        pnlCustomerInfo.add(btnAddCustomer, new KDLayout.Constraints(380, 12, 67, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        btnDeleteCustomer.setBounds(new Rectangle(451, 12, 67, 19));
        pnlCustomerInfo.add(btnDeleteCustomer, new KDLayout.Constraints(451, 12, 67, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        btnAddNewCustomer.setBounds(new Rectangle(296, 12, 80, 19));
        pnlCustomerInfo.add(btnAddNewCustomer, new KDLayout.Constraints(296, 12, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        btnDown.setBounds(new Rectangle(595, 12, 66, 19));
        pnlCustomerInfo.add(btnDown, new KDLayout.Constraints(595, 12, 66, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        btnUp.setBounds(new Rectangle(522, 12, 69, 19));
        pnlCustomerInfo.add(btnUp, new KDLayout.Constraints(522, 12, 69, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        btnSearch.setBounds(new Rectangle(835, 12, 70, 21));
        pnlCustomerInfo.add(btnSearch, new KDLayout.Constraints(835, 12, 70, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        modifyName.setBounds(new Rectangle(665, 12, 80, 19));
        pnlCustomerInfo.add(modifyName, new KDLayout.Constraints(665, 12, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        modifyInfo.setBounds(new Rectangle(750, 12, 80, 19));
        pnlCustomerInfo.add(modifyInfo, new KDLayout.Constraints(750, 12, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlRoomInfo
        pnlRoomInfo.setLayout(null);        contProject.setBounds(new Rectangle(19, 41, 270, 19));
        pnlRoomInfo.add(contProject, null);
        contSubarea.setBounds(new Rectangle(319, 41, 270, 19));
        pnlRoomInfo.add(contSubarea, null);
        contBuilding.setBounds(new Rectangle(19, 67, 270, 19));
        pnlRoomInfo.add(contBuilding, null);
        contRoomNumber.setBounds(new Rectangle(19, 17, 270, 19));
        pnlRoomInfo.add(contRoomNumber, null);
        contUnit.setBounds(new Rectangle(620, 41, 270, 19));
        pnlRoomInfo.add(contUnit, null);
        contFloor.setBounds(new Rectangle(319, 67, 270, 19));
        pnlRoomInfo.add(contFloor, null);
        btnSelectRoom.setBounds(new Rectangle(303, 13, 87, 23));
        pnlRoomInfo.add(btnSelectRoom, null);
        btnEditAttach.setBounds(new Rectangle(746, 11, 110, 23));
        pnlRoomInfo.add(btnEditAttach, null);
        contBuildingArea.setBounds(new Rectangle(19, 92, 270, 19));
        pnlRoomInfo.add(contBuildingArea, null);
        kDLabelContainer8.setBounds(new Rectangle(621, 275, 270, 19));
        pnlRoomInfo.add(kDLabelContainer8, null);
        kDLabelContainer9.setBounds(new Rectangle(621, 275, 270, 19));
        pnlRoomInfo.add(kDLabelContainer9, null);
        kDLabelContainer10.setBounds(new Rectangle(621, 275, 270, 19));
        pnlRoomInfo.add(kDLabelContainer10, null);
        contRoomArea.setBounds(new Rectangle(319, 92, 270, 19));
        pnlRoomInfo.add(contRoomArea, null);
        contRoomModel.setBounds(new Rectangle(620, 92, 270, 19));
        pnlRoomInfo.add(contRoomModel, null);
        contSellOrder.setBounds(new Rectangle(620, 67, 270, 19));
        pnlRoomInfo.add(contSellOrder, null);
        contAttachmentAmount.setBounds(new Rectangle(319, 143, 270, 19));
        pnlRoomInfo.add(contAttachmentAmount, null);
        contFitmentAmount.setBounds(new Rectangle(319, 168, 270, 19));
        pnlRoomInfo.add(contFitmentAmount, null);
        chkIsFitmentToContract.setBounds(new Rectangle(620, 143, 169, 19));
        pnlRoomInfo.add(chkIsFitmentToContract, null);
        contFitmentStandard.setBounds(new Rectangle(620, 168, 270, 19));
        pnlRoomInfo.add(contFitmentStandard, null);
        contFitmentPrice.setBounds(new Rectangle(19, 168, 270, 19));
        pnlRoomInfo.add(contFitmentPrice, null);
        contAttachRoom.setBounds(new Rectangle(434, 17, 270, 19));
        pnlRoomInfo.add(contAttachRoom, null);
        contStandardTotalAmount.setBounds(new Rectangle(19, 117, 270, 19));
        pnlRoomInfo.add(contStandardTotalAmount, null);
        contActualBuildingArea.setBounds(new Rectangle(319, 193, 270, 19));
        pnlRoomInfo.add(contActualBuildingArea, null);
        contActualRoomArea.setBounds(new Rectangle(620, 193, 270, 19));
        pnlRoomInfo.add(contActualRoomArea, null);
        contAreaCompensateAmount.setBounds(new Rectangle(19, 193, 270, 19));
        pnlRoomInfo.add(contAreaCompensateAmount, null);
        contSellType.setBounds(new Rectangle(19, 143, 270, 19));
        pnlRoomInfo.add(contSellType, null);
        btnViewRoom.setBounds(new Rectangle(304, 14, 86, 21));
        pnlRoomInfo.add(btnViewRoom, null);
        lcFloor.setBounds(new Rectangle(319, 67, 270, 19));
        pnlRoomInfo.add(lcFloor, null);
        kDLabelContainer3.setBounds(new Rectangle(318, 117, 270, 19));
        pnlRoomInfo.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(619, 117, 270, 19));
        pnlRoomInfo.add(kDLabelContainer4, null);
        //contProject
        contProject.setBoundEditor(txtProject);
        //contSubarea
        contSubarea.setBoundEditor(txtSubarea);
        //contBuilding
        contBuilding.setBoundEditor(txtBuilding);
        //contRoomNumber
        contRoomNumber.setBoundEditor(txtRoomNumber);
        //contUnit
        contUnit.setBoundEditor(spiUnit);
        //contFloor
        contFloor.setBoundEditor(spiFloor);
        //contBuildingArea
        contBuildingArea.setBoundEditor(txtBuildingArea);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(kDFormattedTextField2);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(kDFormattedTextField3);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(kDFormattedTextField4);
        //contRoomArea
        contRoomArea.setBoundEditor(txtRoomArea);
        //contRoomModel
        contRoomModel.setBoundEditor(f7RoomModel);
        //contSellOrder
        contSellOrder.setBoundEditor(txtSellOrder);
        //contAttachmentAmount
        contAttachmentAmount.setBoundEditor(txtAttachmentAmount);
        //contFitmentAmount
        contFitmentAmount.setBoundEditor(txtFitmentAmount);
        //contFitmentStandard
        contFitmentStandard.setBoundEditor(txtFitmentStandard);
        //contFitmentPrice
        contFitmentPrice.setBoundEditor(txtFitmentPrice);
        //contAttachRoom
        contAttachRoom.setBoundEditor(txtAttachRoom);
        //contStandardTotalAmount
        contStandardTotalAmount.setBoundEditor(txtStandardTotalAmount);
        //contActualBuildingArea
        contActualBuildingArea.setBoundEditor(txtActualBuildingArea);
        //contActualRoomArea
        contActualRoomArea.setBoundEditor(txtActualRoomArea);
        //contAreaCompensateAmount
        contAreaCompensateAmount.setBoundEditor(txtAreaCompensateAmount);
        //contSellType
        contSellType.setBoundEditor(comboSellType);
        //lcFloor
        lcFloor.setBoundEditor(f7BuildingFloor);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtStandardBuildingPrice);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtStandardRoomPrice);
        //tabPurchase
        tabPurchase.add(panelPrePurchaseInfo, resHelper.getString("panelPrePurchaseInfo.constraints"));
        tabPurchase.add(panelPurchaseBill, resHelper.getString("panelPurchaseBill.constraints"));
        tabPurchase.add(pnlPayList, resHelper.getString("pnlPayList.constraints"));
        tabPurchase.add(panelOtherPay, resHelper.getString("panelOtherPay.constraints"));
        //panelPrePurchaseInfo
panelPrePurchaseInfo.setLayout(new BorderLayout(0, 0));        panelPrePurchaseInfo.add(kdPrePanelNorth, BorderLayout.NORTH);
        panelPrePurchaseInfo.add(tblPreLayTime, BorderLayout.CENTER);
        //kdPrePanelNorth
        kdPrePanelNorth.setLayout(new KDLayout());
        kdPrePanelNorth.putClientProperty("OriginalBounds", new Rectangle(0, 0, 903, 220));        kDLabelContainer2.setBounds(new Rectangle(13, 2, 270, 19));
        kdPrePanelNorth.add(kDLabelContainer2, new KDLayout.Constraints(13, 2, 270, 19, 0));
        contPrePurchaseAmount.setBounds(new Rectangle(307, 2, 270, 19));
        kdPrePanelNorth.add(contPrePurchaseAmount, new KDLayout.Constraints(307, 2, 270, 19, 0));
        contPrePurchaseCurrency.setBounds(new Rectangle(602, 2, 270, 19));
        kdPrePanelNorth.add(contPrePurchaseCurrency, new KDLayout.Constraints(602, 2, 270, 19, 0));
        contPrePurchaseDate.setBounds(new Rectangle(13, 26, 270, 19));
        kdPrePanelNorth.add(contPrePurchaseDate, new KDLayout.Constraints(13, 26, 270, 19, 0));
        contPrePurchaseChecker.setBounds(new Rectangle(13, 50, 270, 19));
        kdPrePanelNorth.add(contPrePurchaseChecker, new KDLayout.Constraints(13, 50, 270, 19, 0));
        contPrePurchaseCheckTime.setBounds(new Rectangle(307, 50, 270, 19));
        kdPrePanelNorth.add(contPrePurchaseCheckTime, new KDLayout.Constraints(307, 50, 270, 19, 0));
        contPreValidDate.setBounds(new Rectangle(307, 26, 270, 19));
        kdPrePanelNorth.add(contPreValidDate, new KDLayout.Constraints(307, 26, 270, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(602, 26, 270, 19));
        kdPrePanelNorth.add(kDLabelContainer1, new KDLayout.Constraints(602, 26, 270, 19, 0));
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtPreLevelMoney);
        //contPrePurchaseAmount
        contPrePurchaseAmount.setBoundEditor(txtPrePurchaseAmount);
        //contPrePurchaseCurrency
        contPrePurchaseCurrency.setBoundEditor(f7PrePurchaseCurrency);
        //contPrePurchaseDate
        contPrePurchaseDate.setBoundEditor(pkPrePurchaseDate);
        //contPrePurchaseChecker
        contPrePurchaseChecker.setBoundEditor(f7PrePurchaseChecker);
        //contPrePurchaseCheckTime
        contPrePurchaseCheckTime.setBoundEditor(pkPrePurchaseCheckTime);
        //contPreValidDate
        contPreValidDate.setBoundEditor(pkPreValidDate);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(pkPreFinishDate);
        //panelPurchaseBill
        panelPurchaseBill.setLayout(null);        contPayType.setBounds(new Rectangle(20, 60, 270, 19));
        panelPurchaseBill.add(contPayType, null);
        chkIsSellBySet.setBounds(new Rectangle(316, 62, 116, 19));
        panelPurchaseBill.add(chkIsSellBySet, null);
        btnChooseAgio.setBounds(new Rectangle(791, 103, 82, 23));
        panelPurchaseBill.add(btnChooseAgio, null);
        contContractTotalAmount.setBounds(new Rectangle(20, 11, 270, 19));
        panelPurchaseBill.add(contContractTotalAmount, null);
        contContractRoomPrice.setBounds(new Rectangle(612, 10, 270, 19));
        panelPurchaseBill.add(contContractRoomPrice, null);
        contAgioDes.setBounds(new Rectangle(20, 109, 760, 19));
        panelPurchaseBill.add(contAgioDes, null);
        contAgio.setBounds(new Rectangle(20, 84, 270, 19));
        panelPurchaseBill.add(contAgio, null);
        contPurchaseAmount.setBounds(new Rectangle(11, -27, 270, 19));
        panelPurchaseBill.add(contPurchaseAmount, null);
        contPurchaseCurrency.setBounds(new Rectangle(317, -24, 270, 19));
        panelPurchaseBill.add(contPurchaseCurrency, null);
        contPurchaseDate.setBounds(new Rectangle(612, 58, 270, 19));
        panelPurchaseBill.add(contPurchaseDate, null);
        contSpecialAgio.setBounds(new Rectangle(613, 132, 270, 19));
        panelPurchaseBill.add(contSpecialAgio, null);
        contDealCurrency.setBounds(new Rectangle(317, -29, 270, 19));
        panelPurchaseBill.add(contDealCurrency, null);
        contDealTotalAmount.setBounds(new Rectangle(20, 35, 270, 19));
        panelPurchaseBill.add(contDealTotalAmount, null);
        contDealBuildPrice.setBounds(new Rectangle(316, 34, 270, 19));
        panelPurchaseBill.add(contDealBuildPrice, null);
        contDealRoomPrice.setBounds(new Rectangle(612, 34, 270, 19));
        panelPurchaseBill.add(contDealRoomPrice, null);
        contContractBuildPrice.setBounds(new Rectangle(316, 10, 270, 19));
        panelPurchaseBill.add(contContractBuildPrice, null);
        contPlanSignTimeLimit.setBounds(new Rectangle(316, 83, 270, 19));
        panelPurchaseBill.add(contPlanSignTimeLimit, null);
        contPriceAccount.setBounds(new Rectangle(18, 132, 270, 19));
        panelPurchaseBill.add(contPriceAccount, null);
        contSpecialAgioType.setBounds(new Rectangle(314, 133, 270, 19));
        panelPurchaseBill.add(contSpecialAgioType, null);
        contSignDate.setBounds(new Rectangle(612, 83, 270, 19));
        panelPurchaseBill.add(contSignDate, null);
        //contPayType
        contPayType.setBoundEditor(f7PayType);
        //contContractTotalAmount
        contContractTotalAmount.setBoundEditor(txtContractTotalAmount);
        //contContractRoomPrice
        contContractRoomPrice.setBoundEditor(txtContractRoomPrice);
        //contAgioDes
        contAgioDes.setBoundEditor(txtAgioDes);
        //contAgio
        contAgio.setBoundEditor(txtAgio);
        //contPurchaseAmount
        contPurchaseAmount.setBoundEditor(txtPurchaseAmount);
        //contPurchaseCurrency
        contPurchaseCurrency.setBoundEditor(f7PurchaseCurrency);
        //contPurchaseDate
        contPurchaseDate.setBoundEditor(pkPurchaseDate);
        //contSpecialAgio
        contSpecialAgio.setBoundEditor(txtSpecialAgio);
        //contDealCurrency
        contDealCurrency.setBoundEditor(f7DealCurrency);
        //contDealTotalAmount
        contDealTotalAmount.setBoundEditor(txtDealTotalAmount);
        //contDealBuildPrice
        contDealBuildPrice.setBoundEditor(txtDealBuildPrice);
        //contDealRoomPrice
        contDealRoomPrice.setBoundEditor(txtDealRoomPrice);
        //contContractBuildPrice
        contContractBuildPrice.setBoundEditor(txtContractBuildPrice);
        //contPlanSignTimeLimit
        contPlanSignTimeLimit.setBoundEditor(txtPlanSignTimeLimit);
        //contPriceAccount
        contPriceAccount.setBoundEditor(comboPriceAccount);
        //contSpecialAgioType
        contSpecialAgioType.setBoundEditor(comboSpecialAgioType);
        //contSignDate
        contSignDate.setBoundEditor(pkSignDate);
        //pnlPayList
pnlPayList.setLayout(new BorderLayout(0, 0));        pnlPayList.add(tblPayList, BorderLayout.CENTER);
        pnlPayList.add(northPanel, BorderLayout.NORTH);
        //northPanel
        northPanel.setLayout(new KDLayout());
        northPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 903, 220));        btnRemovePayEntry.setBounds(new Rectangle(812, 14, 22, 19));
        northPanel.add(btnRemovePayEntry, new KDLayout.Constraints(812, 14, 22, 19, 0));
        btnAddPayEntry.setBounds(new Rectangle(754, 14, 22, 19));
        northPanel.add(btnAddPayEntry, new KDLayout.Constraints(754, 14, 22, 19, 0));
        contAFundAmount.setBounds(new Rectangle(307, 14, 270, 19));
        northPanel.add(contAFundAmount, new KDLayout.Constraints(307, 14, 270, 19, 0));
        contLoanAmount.setBounds(new Rectangle(16, 14, 270, 19));
        northPanel.add(contLoanAmount, new KDLayout.Constraints(16, 14, 270, 19, 0));
        btnLittleAdjust.setBounds(new Rectangle(653, 14, 58, 19));
        northPanel.add(btnLittleAdjust, new KDLayout.Constraints(653, 14, 58, 19, 0));
        comboDigit.setBounds(new Rectangle(602, 14, 52, 19));
        northPanel.add(comboDigit, new KDLayout.Constraints(602, 14, 52, 19, 0));
        btnInsertPayEntry.setBounds(new Rectangle(783, 14, 22, 19));
        northPanel.add(btnInsertPayEntry, new KDLayout.Constraints(783, 14, 22, 19, 0));
        //contAFundAmount
        contAFundAmount.setBoundEditor(txtAFundAmount);
        //contLoanAmount
        contLoanAmount.setBoundEditor(txtLoanAmount);
        //panelOtherPay
panelOtherPay.setLayout(new BorderLayout(0, 0));        panelOtherPay.add(panel, BorderLayout.NORTH);
        panelOtherPay.add(tblOtherPay, BorderLayout.CENTER);
        //panel
        panel.setLayout(null);        btnAddOtherPay.setBounds(new Rectangle(729, 11, 22, 19));
        panel.add(btnAddOtherPay, null);
        btnRemoveOtherPay.setBounds(new Rectangle(790, 11, 22, 19));
        panel.add(btnRemoveOtherPay, null);
        btnInsertOtherPay.setBounds(new Rectangle(759, 11, 22, 19));
        panel.add(btnInsertOtherPay, null);
        btnCreat.setBounds(new Rectangle(578, 10, 109, 23));
        panel.add(btnCreat, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contSeller
        contSeller.setBoundEditor(f7Seller);
        //contCreateDate
        contCreateDate.setBoundEditor(pkCreateDate);
        //contSincerityPurchase
        contSincerityPurchase.setBoundEditor(f7SincerityPurchase);
        //contDes
        contDes.setBoundEditor(txtDes);
        //contCreator
        contCreator.setBoundEditor(pkCreator);
        //contSecondSeller
        contSecondSeller.setBoundEditor(f7secondSeller);
        //conRecommendInsider
        conRecommendInsider.setBoundEditor(prmtRecommendInsider);
        //conRecommendCard
        conRecommendCard.setBoundEditor(txtRecommendCard);

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
        menuEdit.add(menuItemModifyName);
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
        menuBiz.add(menuItemPurchasePrint);
        menuBiz.add(menuItemPurchasePrintView);
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
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(btnCopyLine);
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
        this.toolBar.add(btnPurchasePrint);
        this.toolBar.add(btnPurchasePrintView);
        this.toolBar.add(kDWorkButton1);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("salesman.id", com.kingdee.bos.util.BOSUuid.class, this.tblCustomerInfo, "salesId.text");
		dataBinder.registerBinding("snapTotalPrice", java.math.BigDecimal.class, this.txtStandardTotalAmount, "value");
		dataBinder.registerBinding("sellType", com.kingdee.eas.fdc.sellhouse.SellTypeEnum.class, this.comboSellType, "selectedItem");
		dataBinder.registerBinding("snapBuildPrice", java.math.BigDecimal.class, this.txtStandardBuildingPrice, "text");
		dataBinder.registerBinding("snapRoomPrice", java.math.BigDecimal.class, this.txtStandardRoomPrice, "text");
		dataBinder.registerBinding("prePurLevelAmount", java.math.BigDecimal.class, this.txtPreLevelMoney, "value");
		dataBinder.registerBinding("prePurchaseAmount", java.math.BigDecimal.class, this.txtPrePurchaseAmount, "value");
		dataBinder.registerBinding("prePurchaseCurrency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.f7PrePurchaseCurrency, "data");
		dataBinder.registerBinding("prePurchaseDate", java.util.Date.class, this.pkPrePurchaseDate, "value");
		dataBinder.registerBinding("prePurchaseValidDate", java.util.Date.class, this.pkPreValidDate, "value");
		dataBinder.registerBinding("toPrePurchaseDate", java.util.Date.class, this.pkPreFinishDate, "value");
		dataBinder.registerBinding("purchaseAmount", java.math.BigDecimal.class, this.txtPurchaseAmount, "value");
		dataBinder.registerBinding("purchaseCurrency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.f7PurchaseCurrency, "data");
		dataBinder.registerBinding("purchaseDate", java.util.Date.class, this.pkPurchaseDate, "value");
		dataBinder.registerBinding("planSignTimeLimit", int.class, this.txtPlanSignTimeLimit, "value");
		dataBinder.registerBinding("planSignDate", java.util.Date.class, this.pkSignDate, "value");
		dataBinder.registerBinding("elsePayListEntry.sheCollection", String.class, this.tblOtherPay, "collectionID.text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateDate, "value");
		dataBinder.registerBinding("sincerityPurchase", com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo.class, this.f7SincerityPurchase, "data");
		dataBinder.registerBinding("description", String.class, this.txtDes, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.pkCreator, "data");
		dataBinder.registerBinding("insider", com.kingdee.eas.fdc.insider.InsiderInfo.class, this.prmtRecommendInsider, "data");
		dataBinder.registerBinding("insider.insiderCode", String.class, this.txtRecommendCard, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.PurchaseEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)ov;
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
		getValidateHelper().registerBindProperty("salesman.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("snapTotalPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("snapBuildPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("snapRoomPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prePurLevelAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prePurchaseAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prePurchaseCurrency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prePurchaseDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prePurchaseValidDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("toPrePurchaseDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("purchaseAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("purchaseCurrency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("purchaseDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planSignTimeLimit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planSignDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("elsePayListEntry.sheCollection", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sincerityPurchase", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("insider", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("insider.insiderCode", ValidateHelper.ON_SAVE);    		
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
     * output tblCustomerInfo_editStopped method
     */
    protected void tblCustomerInfo_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
     * output btnDeleteCustomer_actionPerformed method
     */
    protected void btnDeleteCustomer_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnAddNewCustomer_actionPerformed method
     */
    protected void btnAddNewCustomer_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnSearch_actionPerformed method
     */
    protected void btnSearch_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output modifyInfo_mouseClicked method
     */
    protected void modifyInfo_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnSelectRoom_actionPerformed method
     */
    protected void btnSelectRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnEditAttach_actionPerformed method
     */
    protected void btnEditAttach_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output chkIsFitmentToContract_actionPerformed method
     */
    protected void chkIsFitmentToContract_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtAttachmentAmount_dataChanged method
     */
    protected void txtAttachmentAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtFitmentAmount_dataChanged method
     */
    protected void txtFitmentAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtFitmentPrice_dataChanged method
     */
    protected void txtFitmentPrice_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output comboSellType_itemStateChanged method
     */
    protected void comboSellType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output txtStandardBuildingPrice_actionPerformed method
     */
    protected void txtStandardBuildingPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblPreLayTime_tableClicked method
     */
    protected void tblPreLayTime_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output chkIsSellBySet_actionPerformed method
     */
    protected void chkIsSellBySet_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnChooseAgio_actionPerformed method
     */
    protected void btnChooseAgio_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7PayType_dataChanged method
     */
    protected void f7PayType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtContractTotalAmount_dataChanged method
     */
    protected void txtContractTotalAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkPurchaseDate_dataChanged method
     */
    protected void pkPurchaseDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtSpecialAgio_dataChanged method
     */
    protected void txtSpecialAgio_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtDealBuildPrice_actionPerformed method
     */
    protected void txtDealBuildPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtContractBuildPrice_actionPerformed method
     */
    protected void txtContractBuildPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output pkSignDate_dataChanged method
     */
    protected void pkSignDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblPayList_tableClicked method
     */
    protected void tblPayList_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblPayList_editStopped method
     */
    protected void tblPayList_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnRemovePayEntry_actionPerformed method
     */
    protected void btnRemovePayEntry_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnAddPayEntry_actionPerformed method
     */
    protected void btnAddPayEntry_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnLittleAdjust_actionPerformed method
     */
    protected void btnLittleAdjust_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnInsertPayEntry_actionPerformed method
     */
    protected void btnInsertPayEntry_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblOtherPay_tableClicked method
     */
    protected void tblOtherPay_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblOtherPay_editStopped method
     */
    protected void tblOtherPay_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnAddOtherPay_actionPerformed method
     */
    protected void btnAddOtherPay_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnRemoveOtherPay_actionPerformed method
     */
    protected void btnRemoveOtherPay_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnInsertOtherPay_actionPerformed method
     */
    protected void btnInsertOtherPay_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7Seller_dataChanged method
     */
    protected void f7Seller_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7SincerityPurchase_dataChanged method
     */
    protected void f7SincerityPurchase_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output prmtRecommendInsider_dataChanged method
     */
    protected void prmtRecommendInsider_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("salesman.id"));
        sic.add(new SelectorItemInfo("snapTotalPrice"));
        sic.add(new SelectorItemInfo("sellType"));
        sic.add(new SelectorItemInfo("snapBuildPrice"));
        sic.add(new SelectorItemInfo("snapRoomPrice"));
        sic.add(new SelectorItemInfo("prePurLevelAmount"));
        sic.add(new SelectorItemInfo("prePurchaseAmount"));
        sic.add(new SelectorItemInfo("prePurchaseCurrency.*"));
        sic.add(new SelectorItemInfo("prePurchaseDate"));
        sic.add(new SelectorItemInfo("prePurchaseValidDate"));
        sic.add(new SelectorItemInfo("toPrePurchaseDate"));
        sic.add(new SelectorItemInfo("purchaseAmount"));
        sic.add(new SelectorItemInfo("purchaseCurrency.*"));
        sic.add(new SelectorItemInfo("purchaseDate"));
        sic.add(new SelectorItemInfo("planSignTimeLimit"));
        sic.add(new SelectorItemInfo("planSignDate"));
    sic.add(new SelectorItemInfo("elsePayListEntry.sheCollection"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("sincerityPurchase.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("insider.*"));
        sic.add(new SelectorItemInfo("insider.insiderCode"));
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionFirst_actionPerformed method
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }
    	

    /**
     * output actionPurchasePrint_actionPerformed method
     */
    public void actionPurchasePrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPurchasePrintview_actionPerformed method
     */
    public void actionPurchasePrintview_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewRoom_actionPerformed method
     */
    public void actionViewRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewSign_actionPerformed method
     */
    public void actionViewSign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceive_actionPerformed method
     */
    public void actionReceive_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUp_actionPerformed method
     */
    public void actionUp_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDown_actionPerformed method
     */
    public void actionDown_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddNewCustomer_actionPerformed method
     */
    public void actionAddNewCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionModifyName_actionPerformed method
     */
    public void actionModifyName_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCreat_actionPerformed method
     */
    public void actionCreat_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSave(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
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
	public RequestContext prepareActionFirst(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionFirst(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFirst() {
    	return false;
    }
	public RequestContext prepareActionPurchasePrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPurchasePrint() {
    	return false;
    }
	public RequestContext prepareActionPurchasePrintview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPurchasePrintview() {
    	return false;
    }
	public RequestContext prepareActionViewRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewRoom() {
    	return false;
    }
	public RequestContext prepareActionViewSign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewSign() {
    	return false;
    }
	public RequestContext prepareActionReceive(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReceive() {
    	return false;
    }
	public RequestContext prepareActionUp(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUp() {
    	return false;
    }
	public RequestContext prepareActionDown(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDown() {
    	return false;
    }
	public RequestContext prepareActionAddNewCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNewCustomer() {
    	return false;
    }
	public RequestContext prepareActionModifyName(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionModifyName() {
    	return false;
    }
	public RequestContext prepareactionCreat(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionCreat() {
    	return false;
    }

    /**
     * output ActionPurchasePrint class
     */     
    protected class ActionPurchasePrint extends ItemAction {     
    
        public ActionPurchasePrint()
        {
            this(null);
        }

        public ActionPurchasePrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPurchasePrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchasePrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchasePrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseEditUI.this, "ActionPurchasePrint", "actionPurchasePrint_actionPerformed", e);
        }
    }

    /**
     * output ActionPurchasePrintview class
     */     
    protected class ActionPurchasePrintview extends ItemAction {     
    
        public ActionPurchasePrintview()
        {
            this(null);
        }

        public ActionPurchasePrintview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPurchasePrintview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchasePrintview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchasePrintview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseEditUI.this, "ActionPurchasePrintview", "actionPurchasePrintview_actionPerformed", e);
        }
    }

    /**
     * output ActionViewRoom class
     */     
    protected class ActionViewRoom extends ItemAction {     
    
        public ActionViewRoom()
        {
            this(null);
        }

        public ActionViewRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseEditUI.this, "ActionViewRoom", "actionViewRoom_actionPerformed", e);
        }
    }

    /**
     * output ActionViewSign class
     */     
    protected class ActionViewSign extends ItemAction {     
    
        public ActionViewSign()
        {
            this(null);
        }

        public ActionViewSign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewSign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewSign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewSign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseEditUI.this, "ActionViewSign", "actionViewSign_actionPerformed", e);
        }
    }

    /**
     * output ActionReceive class
     */     
    protected class ActionReceive extends ItemAction {     
    
        public ActionReceive()
        {
            this(null);
        }

        public ActionReceive(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionReceive.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceive.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceive.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseEditUI.this, "ActionReceive", "actionReceive_actionPerformed", e);
        }
    }

    /**
     * output ActionUp class
     */     
    protected class ActionUp extends ItemAction {     
    
        public ActionUp()
        {
            this(null);
        }

        public ActionUp(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUp.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUp.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUp.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseEditUI.this, "ActionUp", "actionUp_actionPerformed", e);
        }
    }

    /**
     * output ActionDown class
     */     
    protected class ActionDown extends ItemAction {     
    
        public ActionDown()
        {
            this(null);
        }

        public ActionDown(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDown.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDown.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDown.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseEditUI.this, "ActionDown", "actionDown_actionPerformed", e);
        }
    }

    /**
     * output ActionAddNewCustomer class
     */     
    protected class ActionAddNewCustomer extends ItemAction {     
    
        public ActionAddNewCustomer()
        {
            this(null);
        }

        public ActionAddNewCustomer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddNewCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddNewCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddNewCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseEditUI.this, "ActionAddNewCustomer", "actionAddNewCustomer_actionPerformed", e);
        }
    }

    /**
     * output ActionModifyName class
     */     
    protected class ActionModifyName extends ItemAction {     
    
        public ActionModifyName()
        {
            this(null);
        }

        public ActionModifyName(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionModifyName.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModifyName.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModifyName.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseEditUI.this, "ActionModifyName", "actionModifyName_actionPerformed", e);
        }
    }

    /**
     * output actionCreat class
     */     
    protected class actionCreat extends ItemAction {     
    
        public actionCreat()
        {
            this(null);
        }

        public actionCreat(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionCreat.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionCreat.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionCreat.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseEditUI.this, "actionCreat", "actionCreat_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "PurchaseEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}