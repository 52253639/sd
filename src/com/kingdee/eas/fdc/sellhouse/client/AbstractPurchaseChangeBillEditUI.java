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
public abstract class AbstractPurchaseChangeBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPurchaseChangeBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurchaseChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeDate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane4;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSearch;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane3;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPurchaseChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkChangeDate;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewPayType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewDealAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewLoanAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewAccuFundAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewFitmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSpecialAgioType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkNewIsFitmentToContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReqDiscount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer18;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblNewAttachmentDescription;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTNewPayList;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWAdjust;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer20;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboDigit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriceAccount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewDiscount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer27;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer28;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtNewPayType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewDealAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewLoanAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewAccuFundAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewFitmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSpecialAgioType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtReqDiscount;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea2;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewAttachmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewCompensateAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox kDCNewSellType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtEndNewAgio;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNewAttachmentDescription;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewContractAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPriceAccount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewDiscount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateNewPurReqDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateNewPurSigDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldDiscount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldFitmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldAttachmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldAccuFundAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldLoanAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldDealAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldPayType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOldIsFitmentToContract;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTOldPayList;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer19;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer21;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldSpecialAgioType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer25;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer26;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldDiscount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldFitmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldAttachmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldAccuFundAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldLoanAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldDealAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOldPayType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtEndOldAgio;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea1;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldCompensateAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox kDCOldSellType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOldAttachmentDescription;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldContractAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboOldSpecialAgioType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateOldPurReqDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateSigDate;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer containttxtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer22;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer23;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer24;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChooseRoom;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSubarea;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuilding;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellOrder;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtStandardBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtStandardRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAssoPurchase;
    protected com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo editData = null;
    protected ActionSelectRoom actionSelectRoom = null;
    protected ActionAssoPurchase actionAssoPurchase = null;
    public final static String STATUS_WRITENEWAGIO = "writeNewAgio";
    /**
     * output class constructor
     */
    public AbstractPurchaseChangeBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPurchaseChangeBillEditUI.class.getName());
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
        //actionAddNew
        actionAddNew.setEnabled(false);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
        actionAddNew.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
        actionAddNew.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.NAME");
        actionAddNew.putValue(ItemAction.NAME, _tempStr);
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSelectRoom
        this.actionSelectRoom = new ActionSelectRoom(this);
        getActionManager().registerAction("actionSelectRoom", actionSelectRoom);
         this.actionSelectRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAssoPurchase
        this.actionAssoPurchase = new ActionAssoPurchase(this);
        getActionManager().registerAction("actionAssoPurchase", actionAssoPurchase);
         this.actionAssoPurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer17 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPurchaseChangeReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane4 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.btnSearch = new com.kingdee.bos.ctrl.swing.KDButton();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane3 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtChangeReason = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtPurchaseChangeReason = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkChangeDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contNewPayType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewDealAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewLoanAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewAccuFundAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewFitmentAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSpecialAgioType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkNewIsFitmentToContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contReqDiscount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDWorkButton1 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDWorkButton2 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDWorkButton4 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDWorkButton3 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer18 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblNewAttachmentDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTNewPayList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnWAdjust = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer20 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboDigit = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contPriceAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDWorkButton5 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contNewDiscount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer27 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer28 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtNewPayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNewDealAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNewLoanAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNewAccuFundAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNewFitmentAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboSpecialAgioType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtReqDiscount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDTextArea2 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtNewAttachmentAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNewCompensateAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDCNewSellType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtEndNewAgio = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNewAttachmentDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNewContractAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboPriceAccount = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtNewDiscount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDDateNewPurReqDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDDateNewPurSigDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contOldDiscount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldFitmentAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldAttachmentAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldAccuFundAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldLoanAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldDealAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldPayType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkOldIsFitmentToContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDTOldPayList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer19 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer21 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldSpecialAgioType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer25 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer26 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtOldDiscount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOldFitmentAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOldAttachmentAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOldAccuFundAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOldLoanAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOldDealAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtOldPayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtEndOldAgio = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDTextArea1 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtOldCompensateAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDCOldSellType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtOldAttachmentDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOldContractAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboOldSpecialAgioType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDDateOldPurReqDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDDateSigDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDTCustomer = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.containttxtRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer22 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer23 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer24 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnChooseRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRoomNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSubarea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.spiUnit = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtBuilding = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7RoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSellOrder = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtStandardTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStandardBuildingPrice = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtStandardRoomPrice = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnAssoPurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contNumber.setName("contNumber");
        this.contChangeReason.setName("contChangeReason");
        this.kDLabelContainer17.setName("kDLabelContainer17");
        this.contPurchaseChangeReason.setName("contPurchaseChangeReason");
        this.contChangeDate.setName("contChangeDate");
        this.kDScrollPane4.setName("kDScrollPane4");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.btnSearch.setName("btnSearch");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.txtNumber.setName("txtNumber");
        this.kDScrollPane3.setName("kDScrollPane3");
        this.txtChangeReason.setName("txtChangeReason");
        this.txtName.setName("txtName");
        this.prmtPurchaseChangeReason.setName("prmtPurchaseChangeReason");
        this.pkChangeDate.setName("pkChangeDate");
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel4.setName("kDPanel4");
        this.kDPanel1.setName("kDPanel1");
        this.contNewPayType.setName("contNewPayType");
        this.contNewDealAmount.setName("contNewDealAmount");
        this.contNewLoanAmount.setName("contNewLoanAmount");
        this.contNewAccuFundAmount.setName("contNewAccuFundAmount");
        this.contNewFitmentAmount.setName("contNewFitmentAmount");
        this.contSpecialAgioType.setName("contSpecialAgioType");
        this.chkNewIsFitmentToContract.setName("chkNewIsFitmentToContract");
        this.contReqDiscount.setName("contReqDiscount");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDWorkButton1.setName("kDWorkButton1");
        this.kDWorkButton2.setName("kDWorkButton2");
        this.kDWorkButton4.setName("kDWorkButton4");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.kDWorkButton3.setName("kDWorkButton3");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.kDLabelContainer18.setName("kDLabelContainer18");
        this.lblNewAttachmentDescription.setName("lblNewAttachmentDescription");
        this.kDTNewPayList.setName("kDTNewPayList");
        this.btnWAdjust.setName("btnWAdjust");
        this.kDLabelContainer20.setName("kDLabelContainer20");
        this.comboDigit.setName("comboDigit");
        this.contPriceAccount.setName("contPriceAccount");
        this.kDWorkButton5.setName("kDWorkButton5");
        this.contNewDiscount.setName("contNewDiscount");
        this.kDLabelContainer27.setName("kDLabelContainer27");
        this.kDLabelContainer28.setName("kDLabelContainer28");
        this.prmtNewPayType.setName("prmtNewPayType");
        this.txtNewDealAmount.setName("txtNewDealAmount");
        this.txtNewLoanAmount.setName("txtNewLoanAmount");
        this.txtNewAccuFundAmount.setName("txtNewAccuFundAmount");
        this.txtNewFitmentAmount.setName("txtNewFitmentAmount");
        this.comboSpecialAgioType.setName("comboSpecialAgioType");
        this.txtReqDiscount.setName("txtReqDiscount");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.kDTextArea2.setName("kDTextArea2");
        this.txtNewAttachmentAmount.setName("txtNewAttachmentAmount");
        this.txtNewCompensateAmount.setName("txtNewCompensateAmount");
        this.kDCNewSellType.setName("kDCNewSellType");
        this.txtEndNewAgio.setName("txtEndNewAgio");
        this.txtNewAttachmentDescription.setName("txtNewAttachmentDescription");
        this.txtNewContractAmount.setName("txtNewContractAmount");
        this.comboPriceAccount.setName("comboPriceAccount");
        this.txtNewDiscount.setName("txtNewDiscount");
        this.kDDateNewPurReqDate.setName("kDDateNewPurReqDate");
        this.kDDateNewPurSigDate.setName("kDDateNewPurSigDate");
        this.contOldDiscount.setName("contOldDiscount");
        this.contOldFitmentAmount.setName("contOldFitmentAmount");
        this.contOldAttachmentAmount.setName("contOldAttachmentAmount");
        this.contOldAccuFundAmount.setName("contOldAccuFundAmount");
        this.contOldLoanAmount.setName("contOldLoanAmount");
        this.contOldDealAmount.setName("contOldDealAmount");
        this.contOldPayType.setName("contOldPayType");
        this.chkOldIsFitmentToContract.setName("chkOldIsFitmentToContract");
        this.kDTOldPayList.setName("kDTOldPayList");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.kDLabelContainer19.setName("kDLabelContainer19");
        this.kDLabelContainer21.setName("kDLabelContainer21");
        this.contOldSpecialAgioType.setName("contOldSpecialAgioType");
        this.kDLabelContainer25.setName("kDLabelContainer25");
        this.kDLabelContainer26.setName("kDLabelContainer26");
        this.txtOldDiscount.setName("txtOldDiscount");
        this.txtOldFitmentAmount.setName("txtOldFitmentAmount");
        this.txtOldAttachmentAmount.setName("txtOldAttachmentAmount");
        this.txtOldAccuFundAmount.setName("txtOldAccuFundAmount");
        this.txtOldLoanAmount.setName("txtOldLoanAmount");
        this.txtOldDealAmount.setName("txtOldDealAmount");
        this.prmtOldPayType.setName("prmtOldPayType");
        this.txtEndOldAgio.setName("txtEndOldAgio");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDTextArea1.setName("kDTextArea1");
        this.txtOldCompensateAmount.setName("txtOldCompensateAmount");
        this.kDCOldSellType.setName("kDCOldSellType");
        this.txtOldAttachmentDescription.setName("txtOldAttachmentDescription");
        this.txtOldContractAmount.setName("txtOldContractAmount");
        this.comboOldSpecialAgioType.setName("comboOldSpecialAgioType");
        this.kDDateOldPurReqDate.setName("kDDateOldPurReqDate");
        this.kDDateSigDate.setName("kDDateSigDate");
        this.kDPanel2.setName("kDPanel2");
        this.kDTCustomer.setName("kDTCustomer");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.containttxtRoomArea.setName("containttxtRoomArea");
        this.kDLabelContainer22.setName("kDLabelContainer22");
        this.kDLabelContainer23.setName("kDLabelContainer23");
        this.kDLabelContainer24.setName("kDLabelContainer24");
        this.btnChooseRoom.setName("btnChooseRoom");
        this.txtProjectName.setName("txtProjectName");
        this.txtRoomNumber.setName("txtRoomNumber");
        this.txtSubarea.setName("txtSubarea");
        this.spiUnit.setName("spiUnit");
        this.txtBuilding.setName("txtBuilding");
        this.f7RoomModel.setName("f7RoomModel");
        this.txtSellOrder.setName("txtSellOrder");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.txtStandardTotalAmount.setName("txtStandardTotalAmount");
        this.txtStandardBuildingPrice.setName("txtStandardBuildingPrice");
        this.txtStandardRoomPrice.setName("txtStandardRoomPrice");
        this.btnAssoPurchase.setName("btnAssoPurchase");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,650));		
        this.setAutoscrolls(true);		
        this.btnAddNew.setVisible(false);		
        this.btnAddNew.setEnabled(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnRemove.setEnabled(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.menuItemAddNew.setEnabled(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemCopy.setEnabled(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.menuBiz.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(true);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.btnMultiapprove.setVisible(true);		
        this.menuTable1.setVisible(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contChangeReason		
        this.contChangeReason.setBoundLabelText(resHelper.getString("contChangeReason.boundLabelText"));		
        this.contChangeReason.setBoundLabelLength(100);		
        this.contChangeReason.setBoundLabelUnderline(true);
        // kDLabelContainer17		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelLength(100);		
        this.kDLabelContainer17.setBoundLabelUnderline(true);
        // contPurchaseChangeReason		
        this.contPurchaseChangeReason.setBoundLabelText(resHelper.getString("contPurchaseChangeReason.boundLabelText"));		
        this.contPurchaseChangeReason.setBoundLabelUnderline(true);		
        this.contPurchaseChangeReason.setBoundLabelLength(100);
        // contChangeDate		
        this.contChangeDate.setBoundLabelText(resHelper.getString("contChangeDate.boundLabelText"));		
        this.contChangeDate.setBoundLabelLength(100);		
        this.contChangeDate.setBoundLabelUnderline(true);
        // kDScrollPane4		
        this.kDScrollPane4.setPreferredSize(new Dimension(989,470));		
        this.kDScrollPane4.setAutoscrolls(true);
        // kDTabbedPane1		
        this.kDTabbedPane1.setBackground(new java.awt.Color(236,236,232));
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
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setDisplayFormat("$name$");		
        this.prmtLastUpdateUser.setEditFormat("$number$");		
        this.prmtLastUpdateUser.setCommitFormat("$number$");		
        this.prmtLastUpdateUser.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // kDScrollPane3
        // txtChangeReason
        // txtName		
        this.txtName.setRequired(true);
        // prmtPurchaseChangeReason		
        this.prmtPurchaseChangeReason.setDisplayFormat("$name$");		
        this.prmtPurchaseChangeReason.setEditFormat("$number$");		
        this.prmtPurchaseChangeReason.setCommitFormat("$number$");		
        this.prmtPurchaseChangeReason.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.PurchaseChangeReasonQuery");
        // pkChangeDate		
        this.pkChangeDate.setTimeEnabled(true);		
        this.pkChangeDate.setRequired(true);		
        this.pkChangeDate.setDisplay(false);
        // kDPanel3		
        this.kDPanel3.setAutoscrolls(true);		
        this.kDPanel3.setPreferredSize(new Dimension(989,470));
        // kDPanel4		
        this.kDPanel4.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(192,192,192),1), resHelper.getString("kDPanel4.border.title")));
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(192,192,192),1), resHelper.getString("kDPanel1.border.title")));
        // contNewPayType		
        this.contNewPayType.setBoundLabelText(resHelper.getString("contNewPayType.boundLabelText"));		
        this.contNewPayType.setBoundLabelLength(100);		
        this.contNewPayType.setBoundLabelUnderline(true);
        // contNewDealAmount		
        this.contNewDealAmount.setBoundLabelText(resHelper.getString("contNewDealAmount.boundLabelText"));		
        this.contNewDealAmount.setBoundLabelLength(100);		
        this.contNewDealAmount.setBoundLabelUnderline(true);
        // contNewLoanAmount		
        this.contNewLoanAmount.setBoundLabelText(resHelper.getString("contNewLoanAmount.boundLabelText"));		
        this.contNewLoanAmount.setBoundLabelLength(100);		
        this.contNewLoanAmount.setBoundLabelUnderline(true);
        // contNewAccuFundAmount		
        this.contNewAccuFundAmount.setBoundLabelText(resHelper.getString("contNewAccuFundAmount.boundLabelText"));		
        this.contNewAccuFundAmount.setBoundLabelLength(100);		
        this.contNewAccuFundAmount.setBoundLabelUnderline(true);
        // contNewFitmentAmount		
        this.contNewFitmentAmount.setBoundLabelText(resHelper.getString("contNewFitmentAmount.boundLabelText"));		
        this.contNewFitmentAmount.setBoundLabelLength(100);		
        this.contNewFitmentAmount.setBoundLabelUnderline(true);
        // contSpecialAgioType		
        this.contSpecialAgioType.setBoundLabelText(resHelper.getString("contSpecialAgioType.boundLabelText"));		
        this.contSpecialAgioType.setBoundLabelLength(100);		
        this.contSpecialAgioType.setBoundLabelUnderline(true);
        // chkNewIsFitmentToContract		
        this.chkNewIsFitmentToContract.setText(resHelper.getString("chkNewIsFitmentToContract.text"));
        this.chkNewIsFitmentToContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkNewIsFitmentToContract_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contReqDiscount		
        this.contReqDiscount.setBoundLabelText(resHelper.getString("contReqDiscount.boundLabelText"));		
        this.contReqDiscount.setBoundLabelLength(80);		
        this.contReqDiscount.setBoundLabelUnderline(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // kDWorkButton1		
        this.kDWorkButton1.setText(resHelper.getString("kDWorkButton1.text"));
        this.kDWorkButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDWorkButton1_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDWorkButton2		
        this.kDWorkButton2.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        this.kDWorkButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDWorkButton2_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDWorkButton4		
        this.kDWorkButton4.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        this.kDWorkButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDWorkButton4_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);
        // kDWorkButton3		
        this.kDWorkButton3.setText(resHelper.getString("kDWorkButton3.text"));
        this.kDWorkButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDWorkButton3_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(100);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(100);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);
        // kDLabelContainer18		
        this.kDLabelContainer18.setBoundLabelText(resHelper.getString("kDLabelContainer18.boundLabelText"));		
        this.kDLabelContainer18.setBoundLabelLength(100);		
        this.kDLabelContainer18.setBoundLabelUnderline(true);
        // lblNewAttachmentDescription		
        this.lblNewAttachmentDescription.setBoundLabelText(resHelper.getString("lblNewAttachmentDescription.boundLabelText"));		
        this.lblNewAttachmentDescription.setBoundLabelLength(100);		
        this.lblNewAttachmentDescription.setBoundLabelUnderline(true);
        // kDTNewPayList
		String kDTNewPayListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"term\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"jiange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{term}</t:Cell><t:Cell>$Resource{jiange}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTNewPayList.setFormatXml(resHelper.translateString("kDTNewPayList",kDTNewPayListStrXML));		
        this.kDTNewPayList.setMinimumSize(new Dimension(457,100));
        this.kDTNewPayList.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kDTNewPayList_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnWAdjust		
        this.btnWAdjust.setText(resHelper.getString("btnWAdjust.text"));
        this.btnWAdjust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnWAdjust_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer20		
        this.kDLabelContainer20.setBoundLabelText(resHelper.getString("kDLabelContainer20.boundLabelText"));		
        this.kDLabelContainer20.setBoundLabelLength(100);		
        this.kDLabelContainer20.setBoundLabelUnderline(true);
        // comboDigit
        // contPriceAccount		
        this.contPriceAccount.setBoundLabelText(resHelper.getString("contPriceAccount.boundLabelText"));		
        this.contPriceAccount.setBoundLabelLength(100);		
        this.contPriceAccount.setBoundLabelUnderline(true);
        // kDWorkButton5		
        this.kDWorkButton5.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_insert"));
        this.kDWorkButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDWorkButton5_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contNewDiscount		
        this.contNewDiscount.setBoundLabelText(resHelper.getString("contNewDiscount.boundLabelText"));		
        this.contNewDiscount.setBoundLabelLength(80);		
        this.contNewDiscount.setBoundLabelUnderline(true);
        // kDLabelContainer27		
        this.kDLabelContainer27.setBoundLabelText(resHelper.getString("kDLabelContainer27.boundLabelText"));		
        this.kDLabelContainer27.setBoundLabelUnderline(true);		
        this.kDLabelContainer27.setBoundLabelLength(100);
        // kDLabelContainer28		
        this.kDLabelContainer28.setBoundLabelText(resHelper.getString("kDLabelContainer28.boundLabelText"));		
        this.kDLabelContainer28.setBoundLabelLength(100);		
        this.kDLabelContainer28.setBoundLabelUnderline(true);
        // prmtNewPayType		
        this.prmtNewPayType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHEPayTypeQuery");
        // txtNewDealAmount		
        this.txtNewDealAmount.setEnabled(false);		
        this.txtNewDealAmount.setDataType(1);		
        this.txtNewDealAmount.setPrecision(2);
        // txtNewLoanAmount		
        this.txtNewLoanAmount.setEnabled(false);		
        this.txtNewLoanAmount.setDataType(1);		
        this.txtNewLoanAmount.setPrecision(2);
        // txtNewAccuFundAmount		
        this.txtNewAccuFundAmount.setEnabled(false);		
        this.txtNewAccuFundAmount.setDataType(1);		
        this.txtNewAccuFundAmount.setPrecision(2);
        // txtNewFitmentAmount		
        this.txtNewFitmentAmount.setDataType(1);		
        this.txtNewFitmentAmount.setPrecision(2);
        // comboSpecialAgioType		
        this.comboSpecialAgioType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum").toArray());
        this.comboSpecialAgioType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboSpecialAgioType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtReqDiscount		
        this.txtReqDiscount.setSupportedEmpty(true);		
        this.txtReqDiscount.setSelectionEnd(100);		
        this.txtReqDiscount.setDataType(1);		
        this.txtReqDiscount.setPrecision(2);
        // kDScrollPane2
        // kDTextArea2		
        this.kDTextArea2.setEditable(false);
        // txtNewAttachmentAmount		
        this.txtNewAttachmentAmount.setEnabled(false);		
        this.txtNewAttachmentAmount.setDataType(1);		
        this.txtNewAttachmentAmount.setPrecision(2);
        // txtNewCompensateAmount		
        this.txtNewCompensateAmount.setEnabled(false);		
        this.txtNewCompensateAmount.setDataType(1);		
        this.txtNewCompensateAmount.setPrecision(2);
        // kDCNewSellType		
        this.kDCNewSellType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SellTypeEnum").toArray());
        // txtEndNewAgio		
        this.txtEndNewAgio.setEnabled(false);		
        this.txtEndNewAgio.setDataType(1);		
        this.txtEndNewAgio.setPrecision(2);
        // txtNewAttachmentDescription		
        this.txtNewAttachmentDescription.setEnabled(false);
        // txtNewContractAmount		
        this.txtNewContractAmount.setEnabled(false);		
        this.txtNewContractAmount.setDataType(1);		
        this.txtNewContractAmount.setPrecision(2);
        // comboPriceAccount		
        this.comboPriceAccount.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum").toArray());		
        this.comboPriceAccount.setEnabled(false);
        // txtNewDiscount		
        this.txtNewDiscount.setDataType(1);		
        this.txtNewDiscount.setPrecision(2);
        // kDDateNewPurReqDate
        // kDDateNewPurSigDate		
        this.kDDateNewPurSigDate.setEnabled(false);
        // contOldDiscount		
        this.contOldDiscount.setBoundLabelText(resHelper.getString("contOldDiscount.boundLabelText"));		
        this.contOldDiscount.setBoundLabelLength(80);		
        this.contOldDiscount.setBoundLabelUnderline(true);
        // contOldFitmentAmount		
        this.contOldFitmentAmount.setBoundLabelText(resHelper.getString("contOldFitmentAmount.boundLabelText"));		
        this.contOldFitmentAmount.setBoundLabelLength(100);		
        this.contOldFitmentAmount.setBoundLabelUnderline(true);
        // contOldAttachmentAmount		
        this.contOldAttachmentAmount.setBoundLabelText(resHelper.getString("contOldAttachmentAmount.boundLabelText"));		
        this.contOldAttachmentAmount.setBoundLabelLength(100);		
        this.contOldAttachmentAmount.setBoundLabelUnderline(true);
        // contOldAccuFundAmount		
        this.contOldAccuFundAmount.setBoundLabelText(resHelper.getString("contOldAccuFundAmount.boundLabelText"));		
        this.contOldAccuFundAmount.setBoundLabelLength(100);		
        this.contOldAccuFundAmount.setBoundLabelUnderline(true);
        // contOldLoanAmount		
        this.contOldLoanAmount.setBoundLabelText(resHelper.getString("contOldLoanAmount.boundLabelText"));		
        this.contOldLoanAmount.setBoundLabelLength(100);		
        this.contOldLoanAmount.setBoundLabelUnderline(true);
        // contOldDealAmount		
        this.contOldDealAmount.setBoundLabelText(resHelper.getString("contOldDealAmount.boundLabelText"));		
        this.contOldDealAmount.setBoundLabelLength(100);		
        this.contOldDealAmount.setBoundLabelUnderline(true);
        // contOldPayType		
        this.contOldPayType.setBoundLabelText(resHelper.getString("contOldPayType.boundLabelText"));		
        this.contOldPayType.setBoundLabelLength(100);		
        this.contOldPayType.setBoundLabelUnderline(true);
        // chkOldIsFitmentToContract		
        this.chkOldIsFitmentToContract.setText(resHelper.getString("chkOldIsFitmentToContract.text"));		
        this.chkOldIsFitmentToContract.setEnabled(false);
        // kDTOldPayList
		String kDTOldPayListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"term\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"jiange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{term}</t:Cell><t:Cell>$Resource{jiange}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTOldPayList.setFormatXml(resHelper.translateString("kDTOldPayList",kDTOldPayListStrXML));		
        this.kDTOldPayList.setMinimumSize(new Dimension(457,100));

        

        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(100);		
        this.kDLabelContainer15.setBoundLabelUnderline(true);
        // kDLabelContainer19		
        this.kDLabelContainer19.setBoundLabelText(resHelper.getString("kDLabelContainer19.boundLabelText"));		
        this.kDLabelContainer19.setBoundLabelLength(100);		
        this.kDLabelContainer19.setBoundLabelUnderline(true);
        // kDLabelContainer21		
        this.kDLabelContainer21.setBoundLabelText(resHelper.getString("kDLabelContainer21.boundLabelText"));		
        this.kDLabelContainer21.setBoundLabelLength(100);		
        this.kDLabelContainer21.setBoundLabelUnderline(true);
        // contOldSpecialAgioType		
        this.contOldSpecialAgioType.setBoundLabelText(resHelper.getString("contOldSpecialAgioType.boundLabelText"));		
        this.contOldSpecialAgioType.setBoundLabelLength(100);		
        this.contOldSpecialAgioType.setBoundLabelUnderline(true);
        // kDLabelContainer25		
        this.kDLabelContainer25.setBoundLabelText(resHelper.getString("kDLabelContainer25.boundLabelText"));		
        this.kDLabelContainer25.setBoundLabelLength(100);		
        this.kDLabelContainer25.setBoundLabelUnderline(true);		
        this.kDLabelContainer25.setEnabled(false);
        // kDLabelContainer26		
        this.kDLabelContainer26.setBoundLabelText(resHelper.getString("kDLabelContainer26.boundLabelText"));		
        this.kDLabelContainer26.setBoundLabelUnderline(true);		
        this.kDLabelContainer26.setBoundLabelLength(100);		
        this.kDLabelContainer26.setEnabled(false);
        // txtOldDiscount		
        this.txtOldDiscount.setEnabled(false);		
        this.txtOldDiscount.setDataType(1);		
        this.txtOldDiscount.setPrecision(2);
        // txtOldFitmentAmount		
        this.txtOldFitmentAmount.setEnabled(false);		
        this.txtOldFitmentAmount.setDataType(1);		
        this.txtOldFitmentAmount.setPrecision(2);
        // txtOldAttachmentAmount		
        this.txtOldAttachmentAmount.setEnabled(false);		
        this.txtOldAttachmentAmount.setDataType(1);		
        this.txtOldAttachmentAmount.setPrecision(2);
        // txtOldAccuFundAmount		
        this.txtOldAccuFundAmount.setEnabled(false);		
        this.txtOldAccuFundAmount.setDataType(1);		
        this.txtOldAccuFundAmount.setPrecision(2);
        // txtOldLoanAmount		
        this.txtOldLoanAmount.setEnabled(false);		
        this.txtOldLoanAmount.setDataType(1);		
        this.txtOldLoanAmount.setPrecision(2);
        // txtOldDealAmount		
        this.txtOldDealAmount.setEnabled(false);		
        this.txtOldDealAmount.setDataType(1);		
        this.txtOldDealAmount.setPrecision(2);
        // prmtOldPayType		
        this.prmtOldPayType.setEnabled(false);
        // txtEndOldAgio		
        this.txtEndOldAgio.setDataType(1);		
        this.txtEndOldAgio.setEnabled(false);		
        this.txtEndOldAgio.setPrecision(2);
        // kDScrollPane1
        // kDTextArea1		
        this.kDTextArea1.setEditable(false);
        // txtOldCompensateAmount		
        this.txtOldCompensateAmount.setEnabled(false);		
        this.txtOldCompensateAmount.setDataType(1);
        // kDCOldSellType		
        this.kDCOldSellType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SellTypeEnum").toArray());		
        this.kDCOldSellType.setEnabled(false);
        // txtOldAttachmentDescription		
        this.txtOldAttachmentDescription.setEditable(false);		
        this.txtOldAttachmentDescription.setRequired(true);		
        this.txtOldAttachmentDescription.setEnabled(false);
        // txtOldContractAmount		
        this.txtOldContractAmount.setEnabled(false);		
        this.txtOldContractAmount.setDataType(1);		
        this.txtOldContractAmount.setPrecision(2);
        // comboOldSpecialAgioType		
        this.comboOldSpecialAgioType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum").toArray());
        // kDDateOldPurReqDate		
        this.kDDateOldPurReqDate.setEnabled(false);
        // kDDateSigDate		
        this.kDDateSigDate.setEnabled(false);
        // kDPanel2		
        this.kDPanel2.setBorder(null);		
        this.kDPanel2.setAutoscrolls(true);
        // kDTCustomer
		String kDTCustomerStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"propertyPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"certificateName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"certificateNumber\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"mailAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"postalcode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"bookDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{propertyPercent}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{certificateName}</t:Cell><t:Cell>$Resource{certificateNumber}</t:Cell><t:Cell>$Resource{mailAddress}</t:Cell><t:Cell>$Resource{postalcode}</t:Cell><t:Cell>$Resource{bookDate}</t:Cell><t:Cell>$Resource{des}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTCustomer.setFormatXml(resHelper.translateString("kDTCustomer",kDTCustomerStrXML));
        this.kDTCustomer.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblCustomerInfo_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // containttxtRoomArea		
        this.containttxtRoomArea.setBoundLabelText(resHelper.getString("containttxtRoomArea.boundLabelText"));		
        this.containttxtRoomArea.setBoundLabelLength(100);		
        this.containttxtRoomArea.setBoundLabelUnderline(true);
        // kDLabelContainer22		
        this.kDLabelContainer22.setBoundLabelText(resHelper.getString("kDLabelContainer22.boundLabelText"));		
        this.kDLabelContainer22.setBoundLabelLength(100);		
        this.kDLabelContainer22.setBoundLabelUnderline(true);
        // kDLabelContainer23		
        this.kDLabelContainer23.setBoundLabelText(resHelper.getString("kDLabelContainer23.boundLabelText"));		
        this.kDLabelContainer23.setBoundLabelLength(100);		
        this.kDLabelContainer23.setBoundLabelUnderline(true);
        // kDLabelContainer24		
        this.kDLabelContainer24.setBoundLabelText(resHelper.getString("kDLabelContainer24.boundLabelText"));		
        this.kDLabelContainer24.setBoundLabelLength(100);		
        this.kDLabelContainer24.setBoundLabelUnderline(true);
        // btnChooseRoom
        this.btnChooseRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelectRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChooseRoom.setText(resHelper.getString("btnChooseRoom.text"));		
        this.btnChooseRoom.setMargin(new java.awt.Insets(0,0,0,0));		
        this.btnChooseRoom.setHorizontalTextPosition(0);
        this.btnChooseRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnChooseRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtProjectName		
        this.txtProjectName.setEnabled(false);
        // txtRoomNumber		
        this.txtRoomNumber.setEnabled(false);
        // txtSubarea		
        this.txtSubarea.setEnabled(false);
        // spiUnit		
        this.spiUnit.setEnabled(false);
        // txtBuilding		
        this.txtBuilding.setEnabled(false);
        // f7RoomModel		
        this.f7RoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");		
        this.f7RoomModel.setEnabled(false);
        // txtSellOrder		
        this.txtSellOrder.setEnabled(false);
        // txtBuildingArea		
        this.txtBuildingArea.setEnabled(false);
        // txtRoomArea		
        this.txtRoomArea.setEnabled(false);
        // txtStandardTotalAmount		
        this.txtStandardTotalAmount.setDataType(1);		
        this.txtStandardTotalAmount.setEnabled(false);
        // txtStandardBuildingPrice		
        this.txtStandardBuildingPrice.setEnabled(false);
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
        // btnAssoPurchase
        this.btnAssoPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssoPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAssoPurchase.setText(resHelper.getString("btnAssoPurchase.text"));		
        this.btnAssoPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_view"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 740));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 740));
        contLastUpdateUser.setBounds(new Rectangle(364, 5, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(364, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(15, 5, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(15, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChangeReason.setBounds(new Rectangle(364, 30, 639, 48));
        this.add(contChangeReason, new KDLayout.Constraints(364, 30, 639, 48, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer17.setBounds(new Rectangle(15, 56, 270, 19));
        this.add(kDLabelContainer17, new KDLayout.Constraints(15, 56, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPurchaseChangeReason.setBounds(new Rectangle(15, 30, 270, 19));
        this.add(contPurchaseChangeReason, new KDLayout.Constraints(15, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChangeDate.setBounds(new Rectangle(733, 4, 270, 19));
        this.add(contChangeDate, new KDLayout.Constraints(733, 4, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane4.setBounds(new Rectangle(15, 251, 990, 483));
        this.add(kDScrollPane4, new KDLayout.Constraints(15, 251, 990, 483, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane1.setBounds(new Rectangle(15, 100, 990, 147));
        this.add(kDTabbedPane1, new KDLayout.Constraints(15, 100, 990, 147, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnSearch.setBounds(new Rectangle(918, 79, 70, 21));
        this.add(btnSearch, new KDLayout.Constraints(918, 79, 70, 21, KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contChangeReason
        contChangeReason.setBoundEditor(kDScrollPane3);
        //kDScrollPane3
        kDScrollPane3.getViewport().add(txtChangeReason, null);
        //kDLabelContainer17
        kDLabelContainer17.setBoundEditor(txtName);
        //contPurchaseChangeReason
        contPurchaseChangeReason.setBoundEditor(prmtPurchaseChangeReason);
        //contChangeDate
        contChangeDate.setBoundEditor(pkChangeDate);
        //kDScrollPane4
        kDScrollPane4.getViewport().add(kDPanel3, null);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(17, 261, 989, 470));        kDPanel4.setBounds(new Rectangle(501, 1, 487, 465));
        kDPanel3.add(kDPanel4, new KDLayout.Constraints(501, 1, 487, 465, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(-2, 1, 487, 465));
        kDPanel3.add(kDPanel1, new KDLayout.Constraints(-2, 1, 487, 465, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDPanel4
        kDPanel4.setLayout(new KDLayout());
        kDPanel4.putClientProperty("OriginalBounds", new Rectangle(501, 1, 487, 465));        contNewPayType.setBounds(new Rectangle(14, 284, 220, 19));
        kDPanel4.add(contNewPayType, new KDLayout.Constraints(14, 284, 220, 19, 0));
        contNewDealAmount.setBounds(new Rectangle(247, 41, 220, 19));
        kDPanel4.add(contNewDealAmount, new KDLayout.Constraints(247, 41, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNewLoanAmount.setBounds(new Rectangle(13, 65, 220, 19));
        kDPanel4.add(contNewLoanAmount, new KDLayout.Constraints(13, 65, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNewAccuFundAmount.setBounds(new Rectangle(247, 65, 220, 19));
        kDPanel4.add(contNewAccuFundAmount, new KDLayout.Constraints(247, 65, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNewFitmentAmount.setBounds(new Rectangle(13, 113, 220, 19));
        kDPanel4.add(contNewFitmentAmount, new KDLayout.Constraints(13, 113, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSpecialAgioType.setBounds(new Rectangle(14, 256, 268, 19));
        kDPanel4.add(contSpecialAgioType, new KDLayout.Constraints(14, 256, 268, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkNewIsFitmentToContract.setBounds(new Rectangle(247, 113, 190, 19));
        kDPanel4.add(chkNewIsFitmentToContract, new KDLayout.Constraints(247, 113, 190, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contReqDiscount.setBounds(new Rectangle(299, 256, 167, 19));
        kDPanel4.add(contReqDiscount, new KDLayout.Constraints(299, 256, 167, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer11.setBounds(new Rectangle(13, 185, 455, 40));
        kDPanel4.add(kDLabelContainer11, new KDLayout.Constraints(13, 185, 455, 40, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDWorkButton1.setBounds(new Rectangle(247, 161, 89, 19));
        kDPanel4.add(kDWorkButton1, new KDLayout.Constraints(247, 161, 89, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDWorkButton2.setBounds(new Rectangle(378, 284, 22, 19));
        kDPanel4.add(kDWorkButton2, new KDLayout.Constraints(378, 284, 22, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDWorkButton4.setBounds(new Rectangle(434, 284, 22, 19));
        kDPanel4.add(kDWorkButton4, new KDLayout.Constraints(434, 284, 22, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer12.setBounds(new Rectangle(247, 137, 220, 19));
        kDPanel4.add(kDLabelContainer12, new KDLayout.Constraints(247, 137, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDWorkButton3.setBounds(new Rectangle(215, 137, 17, 19));
        kDPanel4.add(kDWorkButton3, new KDLayout.Constraints(215, 137, 17, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer14.setBounds(new Rectangle(247, 88, 220, 19));
        kDPanel4.add(kDLabelContainer14, new KDLayout.Constraints(247, 88, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer16.setBounds(new Rectangle(13, 89, 220, 19));
        kDPanel4.add(kDLabelContainer16, new KDLayout.Constraints(13, 89, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer18.setBounds(new Rectangle(13, 161, 220, 19));
        kDPanel4.add(kDLabelContainer18, new KDLayout.Constraints(13, 161, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblNewAttachmentDescription.setBounds(new Rectangle(13, 137, 200, 19));
        kDPanel4.add(lblNewAttachmentDescription, new KDLayout.Constraints(13, 137, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTNewPayList.setBounds(new Rectangle(12, 314, 455, 124));
        kDPanel4.add(kDTNewPayList, new KDLayout.Constraints(12, 314, 455, 124, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnWAdjust.setBounds(new Rectangle(296, 284, 58, 19));
        kDPanel4.add(btnWAdjust, new KDLayout.Constraints(296, 284, 58, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer20.setBounds(new Rectangle(13, 41, 220, 19));
        kDPanel4.add(kDLabelContainer20, new KDLayout.Constraints(13, 41, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        comboDigit.setBounds(new Rectangle(246, 284, 50, 19));
        kDPanel4.add(comboDigit, new KDLayout.Constraints(246, 284, 50, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPriceAccount.setBounds(new Rectangle(13, 230, 270, 21));
        kDPanel4.add(contPriceAccount, new KDLayout.Constraints(13, 230, 270, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDWorkButton5.setBounds(new Rectangle(406, 284, 22, 19));
        kDPanel4.add(kDWorkButton5, new KDLayout.Constraints(406, 284, 22, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNewDiscount.setBounds(new Rectangle(297, 230, 168, 19));
        kDPanel4.add(contNewDiscount, new KDLayout.Constraints(297, 230, 168, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer27.setBounds(new Rectangle(16, 18, 220, 19));
        kDPanel4.add(kDLabelContainer27, new KDLayout.Constraints(16, 18, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer28.setBounds(new Rectangle(248, 15, 220, 19));
        kDPanel4.add(kDLabelContainer28, new KDLayout.Constraints(248, 15, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNewPayType
        contNewPayType.setBoundEditor(prmtNewPayType);
        //contNewDealAmount
        contNewDealAmount.setBoundEditor(txtNewDealAmount);
        //contNewLoanAmount
        contNewLoanAmount.setBoundEditor(txtNewLoanAmount);
        //contNewAccuFundAmount
        contNewAccuFundAmount.setBoundEditor(txtNewAccuFundAmount);
        //contNewFitmentAmount
        contNewFitmentAmount.setBoundEditor(txtNewFitmentAmount);
        //contSpecialAgioType
        contSpecialAgioType.setBoundEditor(comboSpecialAgioType);
        //contReqDiscount
        contReqDiscount.setBoundEditor(txtReqDiscount);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(kDTextArea2, null);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtNewAttachmentAmount);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(txtNewCompensateAmount);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(kDCNewSellType);
        //kDLabelContainer18
        kDLabelContainer18.setBoundEditor(txtEndNewAgio);
        //lblNewAttachmentDescription
        lblNewAttachmentDescription.setBoundEditor(txtNewAttachmentDescription);
        //kDLabelContainer20
        kDLabelContainer20.setBoundEditor(txtNewContractAmount);
        //contPriceAccount
        contPriceAccount.setBoundEditor(comboPriceAccount);
        //contNewDiscount
        contNewDiscount.setBoundEditor(txtNewDiscount);
        //kDLabelContainer27
        kDLabelContainer27.setBoundEditor(kDDateNewPurReqDate);
        //kDLabelContainer28
        kDLabelContainer28.setBoundEditor(kDDateNewPurSigDate);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(-2, 1, 487, 465));        contOldDiscount.setBounds(new Rectangle(291, 259, 175, 19));
        kDPanel1.add(contOldDiscount, new KDLayout.Constraints(291, 259, 175, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOldFitmentAmount.setBounds(new Rectangle(12, 130, 220, 19));
        kDPanel1.add(contOldFitmentAmount, new KDLayout.Constraints(12, 130, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOldAttachmentAmount.setBounds(new Rectangle(242, 158, 220, 19));
        kDPanel1.add(contOldAttachmentAmount, new KDLayout.Constraints(242, 158, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOldAccuFundAmount.setBounds(new Rectangle(242, 74, 220, 19));
        kDPanel1.add(contOldAccuFundAmount, new KDLayout.Constraints(242, 74, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOldLoanAmount.setBounds(new Rectangle(12, 74, 220, 19));
        kDPanel1.add(contOldLoanAmount, new KDLayout.Constraints(12, 74, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOldDealAmount.setBounds(new Rectangle(242, 46, 220, 19));
        kDPanel1.add(contOldDealAmount, new KDLayout.Constraints(242, 46, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOldPayType.setBounds(new Rectangle(12, 285, 220, 19));
        kDPanel1.add(contOldPayType, new KDLayout.Constraints(12, 285, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        chkOldIsFitmentToContract.setBounds(new Rectangle(242, 130, 187, 19));
        kDPanel1.add(chkOldIsFitmentToContract, new KDLayout.Constraints(242, 130, 187, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTOldPayList.setBounds(new Rectangle(10, 312, 457, 130));
        kDPanel1.add(kDTOldPayList, new KDLayout.Constraints(10, 312, 457, 130, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer9.setBounds(new Rectangle(12, 186, 220, 19));
        kDPanel1.add(kDLabelContainer9, new KDLayout.Constraints(12, 186, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer10.setBounds(new Rectangle(12, 212, 450, 40));
        kDPanel1.add(kDLabelContainer10, new KDLayout.Constraints(12, 212, 450, 40, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer13.setBounds(new Rectangle(242, 102, 220, 19));
        kDPanel1.add(kDLabelContainer13, new KDLayout.Constraints(242, 102, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer15.setBounds(new Rectangle(12, 102, 220, 19));
        kDPanel1.add(kDLabelContainer15, new KDLayout.Constraints(12, 102, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer19.setBounds(new Rectangle(12, 158, 220, 19));
        kDPanel1.add(kDLabelContainer19, new KDLayout.Constraints(12, 158, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer21.setBounds(new Rectangle(12, 46, 220, 19));
        kDPanel1.add(kDLabelContainer21, new KDLayout.Constraints(12, 46, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOldSpecialAgioType.setBounds(new Rectangle(13, 259, 270, 19));
        kDPanel1.add(contOldSpecialAgioType, new KDLayout.Constraints(13, 259, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer25.setBounds(new Rectangle(12, 19, 220, 19));
        kDPanel1.add(kDLabelContainer25, new KDLayout.Constraints(12, 19, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer26.setBounds(new Rectangle(242, 19, 220, 19));
        kDPanel1.add(kDLabelContainer26, new KDLayout.Constraints(242, 19, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contOldDiscount
        contOldDiscount.setBoundEditor(txtOldDiscount);
        //contOldFitmentAmount
        contOldFitmentAmount.setBoundEditor(txtOldFitmentAmount);
        //contOldAttachmentAmount
        contOldAttachmentAmount.setBoundEditor(txtOldAttachmentAmount);
        //contOldAccuFundAmount
        contOldAccuFundAmount.setBoundEditor(txtOldAccuFundAmount);
        //contOldLoanAmount
        contOldLoanAmount.setBoundEditor(txtOldLoanAmount);
        //contOldDealAmount
        contOldDealAmount.setBoundEditor(txtOldDealAmount);
        //contOldPayType
        contOldPayType.setBoundEditor(prmtOldPayType);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtEndOldAgio);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(kDTextArea1, null);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(txtOldCompensateAmount);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(kDCOldSellType);
        //kDLabelContainer19
        kDLabelContainer19.setBoundEditor(txtOldAttachmentDescription);
        //kDLabelContainer21
        kDLabelContainer21.setBoundEditor(txtOldContractAmount);
        //contOldSpecialAgioType
        contOldSpecialAgioType.setBoundEditor(comboOldSpecialAgioType);
        //kDLabelContainer25
        kDLabelContainer25.setBoundEditor(kDDateOldPurReqDate);
        //kDLabelContainer26
        kDLabelContainer26.setBoundEditor(kDDateSigDate);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane1.add(kDTCustomer, resHelper.getString("kDTCustomer.constraints"));
        //kDPanel2
        kDPanel2.setLayout(null);        kDLabelContainer1.setBounds(new Rectangle(353, 8, 270, 19));
        kDPanel2.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(24, 8, 177, 19));
        kDPanel2.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(670, 8, 270, 19));
        kDPanel2.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(24, 33, 270, 19));
        kDPanel2.add(kDLabelContainer4, null);
        kDLabelContainer5.setBounds(new Rectangle(353, 33, 270, 19));
        kDPanel2.add(kDLabelContainer5, null);
        kDLabelContainer6.setBounds(new Rectangle(669, 33, 270, 19));
        kDPanel2.add(kDLabelContainer6, null);
        kDLabelContainer7.setBounds(new Rectangle(24, 60, 270, 19));
        kDPanel2.add(kDLabelContainer7, null);
        kDLabelContainer8.setBounds(new Rectangle(353, 60, 270, 19));
        kDPanel2.add(kDLabelContainer8, null);
        containttxtRoomArea.setBounds(new Rectangle(669, 60, 270, 19));
        kDPanel2.add(containttxtRoomArea, null);
        kDLabelContainer22.setBounds(new Rectangle(24, 90, 270, 19));
        kDPanel2.add(kDLabelContainer22, null);
        kDLabelContainer23.setBounds(new Rectangle(353, 90, 270, 19));
        kDPanel2.add(kDLabelContainer23, null);
        kDLabelContainer24.setBounds(new Rectangle(669, 90, 270, 19));
        kDPanel2.add(kDLabelContainer24, null);
        btnChooseRoom.setBounds(new Rectangle(209, 8, 85, 19));
        kDPanel2.add(btnChooseRoom, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtProjectName);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtRoomNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtSubarea);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(spiUnit);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtBuilding);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(f7RoomModel);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(txtSellOrder);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtBuildingArea);
        //containttxtRoomArea
        containttxtRoomArea.setBoundEditor(txtRoomArea);
        //kDLabelContainer22
        kDLabelContainer22.setBoundEditor(txtStandardTotalAmount);
        //kDLabelContainer23
        kDLabelContainer23.setBoundEditor(txtStandardBuildingPrice);
        //kDLabelContainer24
        kDLabelContainer24.setBoundEditor(txtStandardRoomPrice);

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
        this.toolBar.add(btnAssoPurchase);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("changeReason", String.class, this.txtChangeReason, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("purchaseChangeReason", com.kingdee.eas.fdc.sellhouse.PurchaseChangeReasonInfo.class, this.prmtPurchaseChangeReason, "data");
		dataBinder.registerBinding("changeDate", java.util.Date.class, this.pkChangeDate, "value");
		dataBinder.registerBinding("newIsFitmentToContract", boolean.class, this.chkNewIsFitmentToContract, "selected");
		dataBinder.registerBinding("newPayType", com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo.class, this.prmtNewPayType, "data");
		dataBinder.registerBinding("newDealAmount", java.math.BigDecimal.class, this.txtNewDealAmount, "value");
		dataBinder.registerBinding("newLoanAmount", java.math.BigDecimal.class, this.txtNewLoanAmount, "value");
		dataBinder.registerBinding("newAccuFundAmount", java.math.BigDecimal.class, this.txtNewAccuFundAmount, "value");
		dataBinder.registerBinding("newFitmentAmount", java.math.BigDecimal.class, this.txtNewFitmentAmount, "value");
		dataBinder.registerBinding("reqDiscount", java.math.BigDecimal.class, this.txtReqDiscount, "value");
		dataBinder.registerBinding("newAttachmentAmount", java.math.BigDecimal.class, this.txtNewAttachmentAmount, "value");
		dataBinder.registerBinding("newCompensateAmount", java.math.BigDecimal.class, this.txtNewCompensateAmount, "value");
		dataBinder.registerBinding("newSellType", com.kingdee.eas.fdc.sellhouse.SellTypeEnum.class, this.kDCNewSellType, "selectedItem");
		dataBinder.registerBinding("newContractAmount", java.math.BigDecimal.class, this.txtNewContractAmount, "value");
		dataBinder.registerBinding("newPurchaseDate", java.util.Date.class, this.kDDateNewPurReqDate, "value");
		dataBinder.registerBinding("newPlanSignDate", java.util.Date.class, this.kDDateNewPurSigDate, "value");
		dataBinder.registerBinding("oldIsFitmentToContract", boolean.class, this.chkOldIsFitmentToContract, "selected");
		dataBinder.registerBinding("oldDiscount", java.math.BigDecimal.class, this.txtOldDiscount, "value");
		dataBinder.registerBinding("oldFitmentAmount", java.math.BigDecimal.class, this.txtOldFitmentAmount, "value");
		dataBinder.registerBinding("oldAttachmentAmount", java.math.BigDecimal.class, this.txtOldAttachmentAmount, "value");
		dataBinder.registerBinding("oldAccuFundAmount", java.math.BigDecimal.class, this.txtOldAccuFundAmount, "value");
		dataBinder.registerBinding("oldLoanAmount", java.math.BigDecimal.class, this.txtOldLoanAmount, "value");
		dataBinder.registerBinding("oldDealAmount", java.math.BigDecimal.class, this.txtOldDealAmount, "value");
		dataBinder.registerBinding("oldPayType", com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo.class, this.prmtOldPayType, "data");
		dataBinder.registerBinding("oldCompensateAmount", java.math.BigDecimal.class, this.txtOldCompensateAmount, "value");
		dataBinder.registerBinding("oldSellType", com.kingdee.eas.fdc.sellhouse.SellTypeEnum.class, this.kDCOldSellType, "selectedItem");
		dataBinder.registerBinding("oldContractAmount", java.math.BigDecimal.class, this.txtOldContractAmount, "value");
		dataBinder.registerBinding("oldPurchaseDate", java.util.Date.class, this.kDDateOldPurReqDate, "value");
		dataBinder.registerBinding("oldPlanSignDate", java.util.Date.class, this.kDDateSigDate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_EDIT, this.kDWorkButton1, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.kDWorkButton2, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.kDWorkButton3, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.kDWorkButton4, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.chkNewIsFitmentToContract, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.btnWAdjust, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.kDWorkButton5, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.kDWorkButton1, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.kDWorkButton2, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.kDWorkButton3, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.kDWorkButton4, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.chkNewIsFitmentToContract, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.btnWAdjust, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.kDWorkButton5, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.kDWorkButton1, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.kDWorkButton2, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.kDWorkButton3, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.kDWorkButton4, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.chkNewIsFitmentToContract, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnWAdjust, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.kDWorkButton5, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtRoomArea, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.kDTextArea1, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.pkChangeDate, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtNewCompensateAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.prmtOldPayType, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.f7RoomModel, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.chkOldIsFitmentToContract, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.kDCOldSellType, ActionStateConst.DISABLED);
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtReqDiscount, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtSellOrder, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtNewAttachmentAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtNewAttachmentDescription, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtProjectName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.spiUnit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtSubarea, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtEndNewAgio, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.kDTextArea2, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.prmtLastUpdateUser, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtBuildingArea, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtNewAccuFundAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.kDTOldPayList, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtEndOldAgio, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtOldDealAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtOldAttachmentDescription, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtBuilding, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtOldAccuFundAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtOldAttachmentAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtChangeReason, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.prmtNewPayType, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtOldFitmentAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.kDCNewSellType, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtOldLoanAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtNewFitmentAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtNewDealAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtNewLoanAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtOldCompensateAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.txtOldDiscount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.chkNewIsFitmentToContract, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.kDWorkButton1, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.kDWorkButton2, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.kDWorkButton3, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.kDWorkButton4, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.btnWAdjust, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_WRITENEWAGIO, this.kDWorkButton5, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.PurchaseChangeBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo)ov;
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
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("purchaseChangeReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newIsFitmentToContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newPayType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newDealAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newLoanAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newAccuFundAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newFitmentAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reqDiscount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newAttachmentAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newCompensateAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newSellType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newContractAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newPurchaseDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newPlanSignDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldIsFitmentToContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldDiscount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldFitmentAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldAttachmentAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldAccuFundAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldLoanAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldDealAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldPayType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldCompensateAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldSellType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldContractAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldPurchaseDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldPlanSignDate", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.kDWorkButton1.setEnabled(true);
		            this.kDWorkButton2.setEnabled(true);
		            this.kDWorkButton3.setEnabled(true);
		            this.kDWorkButton4.setEnabled(true);
		            this.chkNewIsFitmentToContract.setEnabled(true);
		            this.btnWAdjust.setEnabled(true);
		            this.kDWorkButton5.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.kDWorkButton1.setEnabled(false);
		            this.kDWorkButton2.setEnabled(false);
		            this.kDWorkButton3.setEnabled(false);
		            this.kDWorkButton4.setEnabled(false);
		            this.chkNewIsFitmentToContract.setEnabled(false);
		            this.btnWAdjust.setEnabled(false);
		            this.kDWorkButton5.setEnabled(false);
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		            this.kDWorkButton1.setEnabled(false);
		            this.kDWorkButton2.setEnabled(false);
		            this.kDWorkButton3.setEnabled(false);
		            this.kDWorkButton4.setEnabled(false);
		            this.chkNewIsFitmentToContract.setEnabled(false);
		            this.btnWAdjust.setEnabled(false);
		            this.kDWorkButton5.setEnabled(false);
        } else if (STATUS_WRITENEWAGIO.equals(this.oprtState)) {
		            this.txtRoomArea.setEnabled(false);
		            this.kDTextArea1.setEnabled(false);
		            this.pkChangeDate.setEnabled(false);
		            this.txtNewCompensateAmount.setEnabled(false);
		            this.prmtOldPayType.setEnabled(false);
		            this.f7RoomModel.setEnabled(false);
		            this.chkOldIsFitmentToContract.setEnabled(false);
		            this.kDCOldSellType.setEnabled(false);
		            this.txtReqDiscount.setEnabled(true);
		            this.txtNumber.setEnabled(false);
		            this.txtSellOrder.setEnabled(false);
		            this.txtNewAttachmentAmount.setEnabled(false);
		            this.txtNewAttachmentDescription.setEnabled(false);
		            this.txtProjectName.setEnabled(false);
		            this.spiUnit.setEnabled(false);
		            this.txtName.setEnabled(false);
		            this.txtSubarea.setEnabled(false);
		            this.txtEndNewAgio.setEnabled(false);
		            this.kDTextArea2.setEnabled(false);
		            this.prmtLastUpdateUser.setEnabled(false);
		            this.txtBuildingArea.setEnabled(false);
		            this.txtNewAccuFundAmount.setEnabled(false);
		            this.kDTOldPayList.setEnabled(false);
		            this.txtEndOldAgio.setEnabled(false);
		            this.txtOldDealAmount.setEnabled(false);
		            this.txtOldAttachmentDescription.setEnabled(false);
		            this.txtBuilding.setEnabled(false);
		            this.txtOldAccuFundAmount.setEnabled(false);
		            this.txtOldAttachmentAmount.setEnabled(false);
		            this.txtChangeReason.setEnabled(false);
		            this.prmtNewPayType.setEnabled(false);
		            this.txtOldFitmentAmount.setEnabled(false);
		            this.kDCNewSellType.setEnabled(false);
		            this.txtOldLoanAmount.setEnabled(false);
		            this.txtNewFitmentAmount.setEnabled(false);
		            this.txtNewDealAmount.setEnabled(false);
		            this.txtNewLoanAmount.setEnabled(false);
		            this.txtOldCompensateAmount.setEnabled(false);
		            this.txtOldDiscount.setEnabled(false);
		            this.chkNewIsFitmentToContract.setEnabled(false);
		            this.kDWorkButton1.setEnabled(false);
		            this.kDWorkButton2.setEnabled(false);
		            this.kDWorkButton3.setEnabled(false);
		            this.kDWorkButton4.setEnabled(false);
		            this.btnWAdjust.setEnabled(false);
		            this.kDWorkButton5.setEnabled(false);
        }
    }

    /**
     * output btnSearch_actionPerformed method
     */
    protected void btnSearch_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkNewIsFitmentToContract_actionPerformed method
     */
    protected void chkNewIsFitmentToContract_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDWorkButton1_actionPerformed method
     */
    protected void kDWorkButton1_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDWorkButton2_actionPerformed method
     */
    protected void kDWorkButton2_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDWorkButton4_actionPerformed method
     */
    protected void kDWorkButton4_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDWorkButton3_actionPerformed method
     */
    protected void kDWorkButton3_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDTNewPayList_editStopped method
     */
    protected void kDTNewPayList_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnWAdjust_actionPerformed method
     */
    protected void btnWAdjust_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDWorkButton5_actionPerformed method
     */
    protected void kDWorkButton5_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboSpecialAgioType_itemStateChanged method
     */
    protected void comboSpecialAgioType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
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
     * output btnChooseRoom_actionPerformed method
     */
    protected void btnChooseRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtStandardBuildingPrice_actionPerformed method
     */
    protected void txtStandardBuildingPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("changeReason"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("purchaseChangeReason.*"));
        sic.add(new SelectorItemInfo("changeDate"));
        sic.add(new SelectorItemInfo("newIsFitmentToContract"));
        sic.add(new SelectorItemInfo("newPayType.*"));
        sic.add(new SelectorItemInfo("newDealAmount"));
        sic.add(new SelectorItemInfo("newLoanAmount"));
        sic.add(new SelectorItemInfo("newAccuFundAmount"));
        sic.add(new SelectorItemInfo("newFitmentAmount"));
        sic.add(new SelectorItemInfo("reqDiscount"));
        sic.add(new SelectorItemInfo("newAttachmentAmount"));
        sic.add(new SelectorItemInfo("newCompensateAmount"));
        sic.add(new SelectorItemInfo("newSellType"));
        sic.add(new SelectorItemInfo("newContractAmount"));
        sic.add(new SelectorItemInfo("newPurchaseDate"));
        sic.add(new SelectorItemInfo("newPlanSignDate"));
        sic.add(new SelectorItemInfo("oldIsFitmentToContract"));
        sic.add(new SelectorItemInfo("oldDiscount"));
        sic.add(new SelectorItemInfo("oldFitmentAmount"));
        sic.add(new SelectorItemInfo("oldAttachmentAmount"));
        sic.add(new SelectorItemInfo("oldAccuFundAmount"));
        sic.add(new SelectorItemInfo("oldLoanAmount"));
        sic.add(new SelectorItemInfo("oldDealAmount"));
        sic.add(new SelectorItemInfo("oldPayType.*"));
        sic.add(new SelectorItemInfo("oldCompensateAmount"));
        sic.add(new SelectorItemInfo("oldSellType"));
        sic.add(new SelectorItemInfo("oldContractAmount"));
        sic.add(new SelectorItemInfo("oldPurchaseDate"));
        sic.add(new SelectorItemInfo("oldPlanSignDate"));
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
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    	

    /**
     * output actionSelectRoom_actionPerformed method
     */
    public void actionSelectRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAssoPurchase_actionPerformed method
     */
    public void actionAssoPurchase_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionSelectRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSelectRoom() {
    	return false;
    }
	public RequestContext prepareActionAssoPurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAssoPurchase() {
    	return false;
    }

    /**
     * output ActionSelectRoom class
     */     
    protected class ActionSelectRoom extends ItemAction {     
    
        public ActionSelectRoom()
        {
            this(null);
        }

        public ActionSelectRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSelectRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseChangeBillEditUI.this, "ActionSelectRoom", "actionSelectRoom_actionPerformed", e);
        }
    }

    /**
     * output ActionAssoPurchase class
     */     
    protected class ActionAssoPurchase extends ItemAction {     
    
        public ActionAssoPurchase()
        {
            this(null);
        }

        public ActionAssoPurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAssoPurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssoPurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssoPurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseChangeBillEditUI.this, "ActionAssoPurchase", "actionAssoPurchase_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "PurchaseChangeBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}