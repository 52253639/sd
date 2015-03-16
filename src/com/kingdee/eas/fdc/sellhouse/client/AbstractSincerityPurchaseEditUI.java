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
public abstract class AbstractSincerityPurchaseEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSincerityPurchaseEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalesman;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSincerityAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArrangeNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBookDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contValidDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModelType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectName;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isReceiveEnterAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellOrder;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommerce;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIntentionRoom;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Currency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Salesman;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSincerityAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Room;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiRoomArrangeNum;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dptBookDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dptValidDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Customer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModelType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox pkCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellOrder;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Commerce;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlSincerPrice;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlChangeNameRecord;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSincerPrice;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAddRow;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDelRow;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblChangeRecord;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox intentionRooms;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSincerPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSincerPrintPreview;
    protected com.kingdee.eas.framework.CoreBillBaseInfo editData = null;
    protected ActionAddRow actionAddRow = null;
    protected ActionDelRow actionDelRow = null;
    protected ActionSincerPrint actionSincerPrint = null;
    protected ActionSincerPrintPreview actionSincerPrintPreview = null;
    /**
     * output class constructor
     */
    public AbstractSincerityPurchaseEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSincerityPurchaseEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddRow
        this.actionAddRow = new ActionAddRow(this);
        getActionManager().registerAction("actionAddRow", actionAddRow);
         this.actionAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelRow
        this.actionDelRow = new ActionDelRow(this);
        getActionManager().registerAction("actionDelRow", actionDelRow);
         this.actionDelRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSincerPrint
        this.actionSincerPrint = new ActionSincerPrint(this);
        getActionManager().registerAction("actionSincerPrint", actionSincerPrint);
         this.actionSincerPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSincerPrintPreview
        this.actionSincerPrintPreview = new ActionSincerPrintPreview(this);
        getActionManager().registerAction("actionSincerPrintPreview", actionSincerPrintPreview);
         this.actionSincerPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSalesman = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSincerityAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArrangeNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBookDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contValidDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModelType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isReceiveEnterAccount = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contSellOrder = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCommerce = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contIntentionRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Currency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Salesman = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSincerityAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7Room = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.spiRoomArrangeNum = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.dptBookDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dptValidDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Customer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7RoomModelType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtProjectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7SellOrder = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Commerce = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pnlSincerPrice = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlChangeNameRecord = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblSincerPrice = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddRow = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnDelRow = new com.kingdee.bos.ctrl.swing.KDButton();
        this.tblChangeRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.intentionRooms = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnSincerPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSincerPrintPreview = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contNumber.setName("contNumber");
        this.contCurrency.setName("contCurrency");
        this.contSalesman.setName("contSalesman");
        this.contSincerityAmount.setName("contSincerityAmount");
        this.contRoom.setName("contRoom");
        this.contRoomArrangeNum.setName("contRoomArrangeNum");
        this.contDescription.setName("contDescription");
        this.contBookDate.setName("contBookDate");
        this.contValidDate.setName("contValidDate");
        this.contCustomer.setName("contCustomer");
        this.contRoomModelType.setName("contRoomModelType");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contProjectNumber.setName("contProjectNumber");
        this.contProjectName.setName("contProjectName");
        this.isReceiveEnterAccount.setName("isReceiveEnterAccount");
        this.contSellOrder.setName("contSellOrder");
        this.contCommerce.setName("contCommerce");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contIntentionRoom.setName("contIntentionRoom");
        this.txtNumber.setName("txtNumber");
        this.f7Currency.setName("f7Currency");
        this.f7Salesman.setName("f7Salesman");
        this.txtSincerityAmount.setName("txtSincerityAmount");
        this.f7Room.setName("f7Room");
        this.spiRoomArrangeNum.setName("spiRoomArrangeNum");
        this.txtDescription.setName("txtDescription");
        this.dptBookDate.setName("dptBookDate");
        this.dptValidDate.setName("dptValidDate");
        this.f7Customer.setName("f7Customer");
        this.f7RoomModelType.setName("f7RoomModelType");
        this.pkCreator.setName("pkCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtProjectNumber.setName("txtProjectNumber");
        this.txtProjectName.setName("txtProjectName");
        this.f7SellOrder.setName("f7SellOrder");
        this.f7Commerce.setName("f7Commerce");
        this.pnlSincerPrice.setName("pnlSincerPrice");
        this.pnlChangeNameRecord.setName("pnlChangeNameRecord");
        this.tblSincerPrice.setName("tblSincerPrice");
        this.btnAddRow.setName("btnAddRow");
        this.btnDelRow.setName("btnDelRow");
        this.tblChangeRecord.setName("tblChangeRecord");
        this.intentionRooms.setName("intentionRooms");
        this.btnSincerPrint.setName("btnSincerPrint");
        this.btnSincerPrintPreview.setName("btnSincerPrintPreview");
        // CoreUI		
        this.setPreferredSize(new Dimension(700,530));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // contSalesman		
        this.contSalesman.setBoundLabelText(resHelper.getString("contSalesman.boundLabelText"));		
        this.contSalesman.setBoundLabelLength(100);		
        this.contSalesman.setBoundLabelUnderline(true);
        // contSincerityAmount		
        this.contSincerityAmount.setBoundLabelText(resHelper.getString("contSincerityAmount.boundLabelText"));		
        this.contSincerityAmount.setBoundLabelLength(100);		
        this.contSincerityAmount.setBoundLabelUnderline(true);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contRoomArrangeNum		
        this.contRoomArrangeNum.setBoundLabelText(resHelper.getString("contRoomArrangeNum.boundLabelText"));		
        this.contRoomArrangeNum.setBoundLabelLength(100);		
        this.contRoomArrangeNum.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contBookDate		
        this.contBookDate.setBoundLabelText(resHelper.getString("contBookDate.boundLabelText"));		
        this.contBookDate.setBoundLabelLength(100);		
        this.contBookDate.setBoundLabelUnderline(true);
        // contValidDate		
        this.contValidDate.setBoundLabelText(resHelper.getString("contValidDate.boundLabelText"));		
        this.contValidDate.setBoundLabelLength(100);		
        this.contValidDate.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // contRoomModelType		
        this.contRoomModelType.setBoundLabelText(resHelper.getString("contRoomModelType.boundLabelText"));		
        this.contRoomModelType.setBoundLabelUnderline(true);		
        this.contRoomModelType.setBoundLabelLength(100);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contProjectNumber		
        this.contProjectNumber.setBoundLabelText(resHelper.getString("contProjectNumber.boundLabelText"));		
        this.contProjectNumber.setBoundLabelLength(100);		
        this.contProjectNumber.setBoundLabelUnderline(true);
        // contProjectName		
        this.contProjectName.setBoundLabelText(resHelper.getString("contProjectName.boundLabelText"));		
        this.contProjectName.setBoundLabelLength(100);		
        this.contProjectName.setBoundLabelUnderline(true);
        // isReceiveEnterAccount		
        this.isReceiveEnterAccount.setText(resHelper.getString("isReceiveEnterAccount.text"));
        this.isReceiveEnterAccount.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    isReceiveEnterAccount_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contSellOrder		
        this.contSellOrder.setBoundLabelText(resHelper.getString("contSellOrder.boundLabelText"));		
        this.contSellOrder.setBoundLabelLength(100);		
        this.contSellOrder.setBoundLabelUnderline(true);
        // contCommerce		
        this.contCommerce.setBoundLabelText(resHelper.getString("contCommerce.boundLabelText"));		
        this.contCommerce.setBoundLabelLength(100);		
        this.contCommerce.setBoundLabelUnderline(true);
        // kDTabbedPane1
        // contIntentionRoom		
        this.contIntentionRoom.setBoundLabelText(resHelper.getString("contIntentionRoom.boundLabelText"));		
        this.contIntentionRoom.setBoundLabelLength(100);		
        this.contIntentionRoom.setBoundLabelUnderline(true);
        // txtNumber
        // f7Currency		
        this.f7Currency.setCommitFormat("$number$");		
        this.f7Currency.setDisplayFormat("$name$");		
        this.f7Currency.setEditFormat("$number$");		
        this.f7Currency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");
        // f7Salesman		
        this.f7Salesman.setDisplayFormat("$name$");		
        this.f7Salesman.setEditFormat("$number$");		
        this.f7Salesman.setCommitFormat("$number$");		
        this.f7Salesman.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
        this.f7Salesman.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Salesman_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtSincerityAmount		
        this.txtSincerityAmount.setDataType(1);
        // f7Room		
        this.f7Room.setDisplayFormat("$displayName$");		
        this.f7Room.setEditFormat("$number$");		
        this.f7Room.setCommitFormat("$number$");
        this.f7Room.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Room_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // spiRoomArrangeNum
        // txtDescription
        // dptBookDate
        // dptValidDate
        // f7Customer		
        this.f7Customer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");		
        this.f7Customer.setEditFormat("$number$");		
        this.f7Customer.setDisplayFormat("$name$");		
        this.f7Customer.setCommitFormat("$number$");
        this.f7Customer.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Customer_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7RoomModelType		
        this.f7RoomModelType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery");		
        this.f7RoomModelType.setDisplayFormat("$name$");		
        this.f7RoomModelType.setEditFormat("$number$");		
        this.f7RoomModelType.setCommitFormat("$number$");
        // pkCreator		
        this.pkCreator.setCommitFormat("$number$");		
        this.pkCreator.setEditFormat("$number$");		
        this.pkCreator.setDisplayFormat("$name$");
        // pkCreateTime
        // txtProjectNumber
        // txtProjectName
        // f7SellOrder		
        this.f7SellOrder.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellOrderQuery");		
        this.f7SellOrder.setCommitFormat("$number$");		
        this.f7SellOrder.setEditFormat("$number$");		
        this.f7SellOrder.setDisplayFormat("$name$");
        this.f7SellOrder.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SellOrder_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7Commerce		
        this.f7Commerce.setDisplayFormat("$name$");		
        this.f7Commerce.setEditFormat("$number$");		
        this.f7Commerce.setCommitFormat("$number$");		
        this.f7Commerce.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CommerceChanceQuery");
        this.f7Commerce.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Commerce_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.f7Commerce.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    f7Commerce_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pnlSincerPrice
        // pnlChangeNameRecord
        // tblSincerPrice
		String tblSincerPriceStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyTypeName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"refundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"canRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyTypeName}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{refundmentAmount}</t:Cell><t:Cell>$Resource{canRefundmentAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSincerPrice.setFormatXml(resHelper.translateString("tblSincerPrice",tblSincerPriceStrXML));
        this.tblSincerPrice.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    tblSincerPrice_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblSincerPrice.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblSincerPrice_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnAddRow
        this.btnAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRow.setText(resHelper.getString("btnAddRow.text"));
        // btnDelRow
        this.btnDelRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelRow.setText(resHelper.getString("btnDelRow.text"));
        // tblChangeRecord
		String tblChangeRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"oldCustomer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"newCustomer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"changeDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"operator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{oldCustomer}</t:Cell><t:Cell>$Resource{newCustomer}</t:Cell><t:Cell>$Resource{changeDate}</t:Cell><t:Cell>$Resource{operator}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblChangeRecord.setFormatXml(resHelper.translateString("tblChangeRecord",tblChangeRecordStrXML));

        

        // intentionRooms		
        this.intentionRooms.setDisplayFormat("$number$");		
        this.intentionRooms.setEditFormat("$number$");		
        this.intentionRooms.setCommitFormat("$number$");
        this.intentionRooms.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    intentionRooms_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnSincerPrint
        this.btnSincerPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionSincerPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSincerPrint.setText(resHelper.getString("btnSincerPrint.text"));		
        this.btnSincerPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // btnSincerPrintPreview
        this.btnSincerPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionSincerPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSincerPrintPreview.setText(resHelper.getString("btnSincerPrintPreview.text"));		
        this.btnSincerPrintPreview.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_printpreview"));
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
        this.setBounds(new Rectangle(10, 10, 700, 530));
        this.setLayout(null);
        contNumber.setBounds(new Rectangle(13, 39, 270, 19));
        this.add(contNumber, null);
        contCurrency.setBounds(new Rectangle(394, 157, 270, 19));
        this.add(contCurrency, null);
        contSalesman.setBounds(new Rectangle(394, 210, 270, 19));
        this.add(contSalesman, null);
        contSincerityAmount.setBounds(new Rectangle(12, 157, 270, 19));
        this.add(contSincerityAmount, null);
        contRoom.setBounds(new Rectangle(14, 102, 270, 19));
        this.add(contRoom, null);
        contRoomArrangeNum.setBounds(new Rectangle(395, 102, 270, 19));
        this.add(contRoomArrangeNum, null);
        contDescription.setBounds(new Rectangle(13, 267, 654, 19));
        this.add(contDescription, null);
        contBookDate.setBounds(new Rectangle(12, 130, 270, 19));
        this.add(contBookDate, null);
        contValidDate.setBounds(new Rectangle(394, 129, 270, 19));
        this.add(contValidDate, null);
        contCustomer.setBounds(new Rectangle(394, 37, 270, 19));
        this.add(contCustomer, null);
        contRoomModelType.setBounds(new Rectangle(13, 211, 270, 19));
        this.add(contRoomModelType, null);
        contCreator.setBounds(new Rectangle(14, 238, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(394, 235, 270, 19));
        this.add(contCreateTime, null);
        contProjectNumber.setBounds(new Rectangle(14, 14, 270, 19));
        this.add(contProjectNumber, null);
        contProjectName.setBounds(new Rectangle(395, 13, 270, 19));
        this.add(contProjectName, null);
        isReceiveEnterAccount.setBounds(new Rectangle(10, 295, 140, 19));
        this.add(isReceiveEnterAccount, null);
        contSellOrder.setBounds(new Rectangle(14, 72, 270, 19));
        this.add(contSellOrder, null);
        contCommerce.setBounds(new Rectangle(395, 71, 270, 19));
        this.add(contCommerce, null);
        kDTabbedPane1.setBounds(new Rectangle(11, 324, 656, 185));
        this.add(kDTabbedPane1, null);
        contIntentionRoom.setBounds(new Rectangle(13, 182, 270, 19));
        this.add(contIntentionRoom, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contCurrency
        contCurrency.setBoundEditor(f7Currency);
        //contSalesman
        contSalesman.setBoundEditor(f7Salesman);
        //contSincerityAmount
        contSincerityAmount.setBoundEditor(txtSincerityAmount);
        //contRoom
        contRoom.setBoundEditor(f7Room);
        //contRoomArrangeNum
        contRoomArrangeNum.setBoundEditor(spiRoomArrangeNum);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contBookDate
        contBookDate.setBoundEditor(dptBookDate);
        //contValidDate
        contValidDate.setBoundEditor(dptValidDate);
        //contCustomer
        contCustomer.setBoundEditor(f7Customer);
        //contRoomModelType
        contRoomModelType.setBoundEditor(f7RoomModelType);
        //contCreator
        contCreator.setBoundEditor(pkCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contProjectNumber
        contProjectNumber.setBoundEditor(txtProjectNumber);
        //contProjectName
        contProjectName.setBoundEditor(txtProjectName);
        //contSellOrder
        contSellOrder.setBoundEditor(f7SellOrder);
        //contCommerce
        contCommerce.setBoundEditor(f7Commerce);
        //kDTabbedPane1
        kDTabbedPane1.add(pnlSincerPrice, resHelper.getString("pnlSincerPrice.constraints"));
        kDTabbedPane1.add(pnlChangeNameRecord, resHelper.getString("pnlChangeNameRecord.constraints"));
        //pnlSincerPrice
        pnlSincerPrice.setLayout(null);        tblSincerPrice.setBounds(new Rectangle(3, 28, 636, 119));
        pnlSincerPrice.add(tblSincerPrice, null);
        btnAddRow.setBounds(new Rectangle(483, 3, 73, 21));
        pnlSincerPrice.add(btnAddRow, null);
        btnDelRow.setBounds(new Rectangle(562, 3, 73, 21));
        pnlSincerPrice.add(btnDelRow, null);
        //pnlChangeNameRecord
        pnlChangeNameRecord.setLayout(null);        tblChangeRecord.setBounds(new Rectangle(5, 3, 640, 143));
        pnlChangeNameRecord.add(tblChangeRecord, null);
        //contIntentionRoom
        contIntentionRoom.setBoundEditor(intentionRooms);

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
        this.toolBar.add(btnSincerPrint);
        this.toolBar.add(btnSincerPrintPreview);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.pkCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SincerityPurchaseEditUIHandler";
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
        this.editData = (com.kingdee.eas.framework.CoreBillBaseInfo)ov;
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
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    		
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
     * output isReceiveEnterAccount_stateChanged method
     */
    protected void isReceiveEnterAccount_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7Salesman_dataChanged method
     */
    protected void f7Salesman_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7Room_dataChanged method
     */
    protected void f7Room_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7Customer_dataChanged method
     */
    protected void f7Customer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7SellOrder_dataChanged method
     */
    protected void f7SellOrder_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7Commerce_dataChanged method
     */
    protected void f7Commerce_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7Commerce_stateChanged method
     */
    protected void f7Commerce_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblSincerPrice_activeCellChanged method
     */
    protected void tblSincerPrice_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblSincerPrice_editStopped method
     */
    protected void tblSincerPrice_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output intentionRooms_dataChanged method
     */
    protected void intentionRooms_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        return sic;
    }        
    	

    /**
     * output actionAddRow_actionPerformed method
     */
    public void actionAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelRow_actionPerformed method
     */
    public void actionDelRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSincerPrint_actionPerformed method
     */
    public void actionSincerPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSincerPrintPreview_actionPerformed method
     */
    public void actionSincerPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddRow() {
    	return false;
    }
	public RequestContext prepareActionDelRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelRow() {
    	return false;
    }
	public RequestContext prepareActionSincerPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSincerPrint() {
    	return false;
    }
	public RequestContext prepareActionSincerPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSincerPrintPreview() {
    	return false;
    }

    /**
     * output ActionAddRow class
     */     
    protected class ActionAddRow extends ItemAction {     
    
        public ActionAddRow()
        {
            this(null);
        }

        public ActionAddRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseEditUI.this, "ActionAddRow", "actionAddRow_actionPerformed", e);
        }
    }

    /**
     * output ActionDelRow class
     */     
    protected class ActionDelRow extends ItemAction {     
    
        public ActionDelRow()
        {
            this(null);
        }

        public ActionDelRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseEditUI.this, "ActionDelRow", "actionDelRow_actionPerformed", e);
        }
    }

    /**
     * output ActionSincerPrint class
     */     
    protected class ActionSincerPrint extends ItemAction {     
    
        public ActionSincerPrint()
        {
            this(null);
        }

        public ActionSincerPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSincerPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSincerPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSincerPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseEditUI.this, "ActionSincerPrint", "actionSincerPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionSincerPrintPreview class
     */     
    protected class ActionSincerPrintPreview extends ItemAction {     
    
        public ActionSincerPrintPreview()
        {
            this(null);
        }

        public ActionSincerPrintPreview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSincerPrintPreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSincerPrintPreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSincerPrintPreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityPurchaseEditUI.this, "ActionSincerPrintPreview", "actionSincerPrintPreview_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SincerityPurchaseEditUI");
    }




}