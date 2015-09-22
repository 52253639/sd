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
public abstract class AbstractVirtualRoomFullInfoUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractVirtualRoomFullInfoUI.class);
    protected com.kingdee.bos.ctrl.swing.KDToolBar kDToolBar1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabBizInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModelType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPricingType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomModelType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModel;
    protected com.kingdee.bos.ctrl.swing.KDComboBox boxRoomState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPricingType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActBuildArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane buyRoomPlanPane;
    protected com.kingdee.bos.ctrl.swing.KDPanel bizTotalFlow;
    protected com.kingdee.bos.ctrl.swing.KDPanel roomPropertyBook;
    protected com.kingdee.bos.ctrl.swing.KDPanel roomJoin;
    protected com.kingdee.bos.ctrl.swing.KDPanel sincerityPurchase;
    protected com.kingdee.bos.ctrl.swing.KDPanel keepDown;
    protected com.kingdee.bos.ctrl.swing.KDPanel roomLoanPanel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable totalFlowTable;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelCustomer1;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelCustomer2;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator6;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelCustomer3;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator7;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelCustomer4;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelCustomer5;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer6;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer7;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPBProcesser;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPBData;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer4;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer5;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblJoinProcesses;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblJoinData;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable sincerityPurchaseTable;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable keepDownTable;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblLoan;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected EntityViewInfo roomKeepDownBillQuery = null;
    protected IMetaDataPK roomKeepDownBillQueryPK;
    /**
     * output class constructor
     */
    public AbstractVirtualRoomFullInfoUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractVirtualRoomFullInfoUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        roomKeepDownBillQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "RoomKeepDownBillQuery");
        this.kDToolBar1 = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tabBizInfo = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.conNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModelType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHouseProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPricingType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardBuildPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealBuildPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSaleMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRoomModelType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7RoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.boxRoomState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboHouseProperty = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtPricingType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStandardRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStandardTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDealRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStandardBuildPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDealBuildPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSaleMan = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtActBuildArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDealTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.buyRoomPlanPane = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.bizTotalFlow = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.roomPropertyBook = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.roomJoin = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.sincerityPurchase = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.keepDown = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.roomLoanPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.totalFlowTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labelCustomer1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labelCustomer2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator6 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labelCustomer3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator7 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labelCustomer4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labelCustomer5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDContainer6 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer7 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblPBProcesser = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblPBData = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer4 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer5 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblJoinProcesses = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblJoinData = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.sincerityPurchaseTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.keepDownTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblLoan = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDToolBar1.setName("kDToolBar1");
        this.kDContainer1.setName("kDContainer1");
        this.tabBizInfo.setName("tabBizInfo");
        this.conNumber.setName("conNumber");
        this.contRoomModelType.setName("contRoomModelType");
        this.contRoomModel.setName("contRoomModel");
        this.contRoomState.setName("contRoomState");
        this.contHouseProperty.setName("contHouseProperty");
        this.contPricingType.setName("contPricingType");
        this.contBuildingArea.setName("contBuildingArea");
        this.contRoomArea.setName("contRoomArea");
        this.contStandardRoomPrice.setName("contStandardRoomPrice");
        this.contStandardTotalAmount.setName("contStandardTotalAmount");
        this.contDealRoomPrice.setName("contDealRoomPrice");
        this.contStandardBuildPrice.setName("contStandardBuildPrice");
        this.contDealBuildPrice.setName("contDealBuildPrice");
        this.contSaleMan.setName("contSaleMan");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.txtNumber.setName("txtNumber");
        this.txtRoomModelType.setName("txtRoomModelType");
        this.f7RoomModel.setName("f7RoomModel");
        this.boxRoomState.setName("boxRoomState");
        this.comboHouseProperty.setName("comboHouseProperty");
        this.txtPricingType.setName("txtPricingType");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.txtStandardRoomPrice.setName("txtStandardRoomPrice");
        this.txtStandardTotalAmount.setName("txtStandardTotalAmount");
        this.txtDealRoomPrice.setName("txtDealRoomPrice");
        this.txtStandardBuildPrice.setName("txtStandardBuildPrice");
        this.txtDealBuildPrice.setName("txtDealBuildPrice");
        this.txtSaleMan.setName("txtSaleMan");
        this.txtActBuildArea.setName("txtActBuildArea");
        this.txtActRoomArea.setName("txtActRoomArea");
        this.txtDealTotalAmount.setName("txtDealTotalAmount");
        this.buyRoomPlanPane.setName("buyRoomPlanPane");
        this.bizTotalFlow.setName("bizTotalFlow");
        this.roomPropertyBook.setName("roomPropertyBook");
        this.roomJoin.setName("roomJoin");
        this.sincerityPurchase.setName("sincerityPurchase");
        this.keepDown.setName("keepDown");
        this.roomLoanPanel.setName("roomLoanPanel");
        this.totalFlowTable.setName("totalFlowTable");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.labelCustomer1.setName("labelCustomer1");
        this.kDSeparator5.setName("kDSeparator5");
        this.labelCustomer2.setName("labelCustomer2");
        this.kDSeparator6.setName("kDSeparator6");
        this.labelCustomer3.setName("labelCustomer3");
        this.kDSeparator7.setName("kDSeparator7");
        this.labelCustomer4.setName("labelCustomer4");
        this.kDSeparator8.setName("kDSeparator8");
        this.labelCustomer5.setName("labelCustomer5");
        this.kDSeparator9.setName("kDSeparator9");
        this.kDContainer6.setName("kDContainer6");
        this.kDContainer7.setName("kDContainer7");
        this.tblPBProcesser.setName("tblPBProcesser");
        this.tblPBData.setName("tblPBData");
        this.kDContainer4.setName("kDContainer4");
        this.kDContainer5.setName("kDContainer5");
        this.tblJoinProcesses.setName("tblJoinProcesses");
        this.tblJoinData.setName("tblJoinData");
        this.sincerityPurchaseTable.setName("sincerityPurchaseTable");
        this.keepDownTable.setName("keepDownTable");
        this.kDContainer2.setName("kDContainer2");
        this.tblLoan.setName("tblLoan");
        // CoreUI
        // kDToolBar1
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // tabBizInfo
        // conNumber		
        this.conNumber.setBoundLabelText(resHelper.getString("conNumber.boundLabelText"));		
        this.conNumber.setBoundLabelLength(75);		
        this.conNumber.setBoundLabelUnderline(true);
        // contRoomModelType		
        this.contRoomModelType.setBoundLabelText(resHelper.getString("contRoomModelType.boundLabelText"));		
        this.contRoomModelType.setBoundLabelLength(75);		
        this.contRoomModelType.setBoundLabelUnderline(true);
        // contRoomModel		
        this.contRoomModel.setBoundLabelText(resHelper.getString("contRoomModel.boundLabelText"));		
        this.contRoomModel.setBoundLabelLength(75);		
        this.contRoomModel.setBoundLabelUnderline(true);
        // contRoomState		
        this.contRoomState.setBoundLabelText(resHelper.getString("contRoomState.boundLabelText"));		
        this.contRoomState.setBoundLabelLength(75);		
        this.contRoomState.setBoundLabelUnderline(true);
        // contHouseProperty		
        this.contHouseProperty.setBoundLabelText(resHelper.getString("contHouseProperty.boundLabelText"));		
        this.contHouseProperty.setBoundLabelLength(75);		
        this.contHouseProperty.setBoundLabelUnderline(true);
        // contPricingType		
        this.contPricingType.setBoundLabelText(resHelper.getString("contPricingType.boundLabelText"));		
        this.contPricingType.setBoundLabelLength(75);		
        this.contPricingType.setBoundLabelUnderline(true);
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelLength(75);		
        this.contBuildingArea.setBoundLabelUnderline(true);
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelLength(75);		
        this.contRoomArea.setBoundLabelUnderline(true);
        // contStandardRoomPrice		
        this.contStandardRoomPrice.setBoundLabelText(resHelper.getString("contStandardRoomPrice.boundLabelText"));		
        this.contStandardRoomPrice.setBoundLabelUnderline(true);		
        this.contStandardRoomPrice.setBoundLabelLength(75);
        // contStandardTotalAmount		
        this.contStandardTotalAmount.setBoundLabelText(resHelper.getString("contStandardTotalAmount.boundLabelText"));		
        this.contStandardTotalAmount.setBoundLabelLength(75);		
        this.contStandardTotalAmount.setBoundLabelUnderline(true);
        // contDealRoomPrice		
        this.contDealRoomPrice.setBoundLabelText(resHelper.getString("contDealRoomPrice.boundLabelText"));		
        this.contDealRoomPrice.setBoundLabelLength(75);		
        this.contDealRoomPrice.setBoundLabelUnderline(true);
        // contStandardBuildPrice		
        this.contStandardBuildPrice.setBoundLabelText(resHelper.getString("contStandardBuildPrice.boundLabelText"));		
        this.contStandardBuildPrice.setBoundLabelLength(75);		
        this.contStandardBuildPrice.setBoundLabelUnderline(true);
        // contDealBuildPrice		
        this.contDealBuildPrice.setBoundLabelText(resHelper.getString("contDealBuildPrice.boundLabelText"));		
        this.contDealBuildPrice.setBoundLabelLength(75);		
        this.contDealBuildPrice.setBoundLabelUnderline(true);
        // contSaleMan		
        this.contSaleMan.setBoundLabelText(resHelper.getString("contSaleMan.boundLabelText"));		
        this.contSaleMan.setBoundLabelLength(75);		
        this.contSaleMan.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(75);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(75);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelLength(75);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setEnabled(false);
        // txtRoomModelType		
        this.txtRoomModelType.setEnabled(false);
        // f7RoomModel		
        this.f7RoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");		
        this.f7RoomModel.setCommitFormat("$number$");		
        this.f7RoomModel.setDisplayFormat("$name$");		
        this.f7RoomModel.setEditFormat("$number$");		
        this.f7RoomModel.setEnabled(false);
        // boxRoomState		
        this.boxRoomState.setEnabled(false);		
        this.boxRoomState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum").toArray());
        // comboHouseProperty		
        this.comboHouseProperty.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.HousePropertyEnum").toArray());		
        this.comboHouseProperty.setEnabled(false);
        // txtPricingType		
        this.txtPricingType.setEnabled(false);
        // txtBuildingArea		
        this.txtBuildingArea.setDataType(1);		
        this.txtBuildingArea.setEnabled(false);
        // txtRoomArea		
        this.txtRoomArea.setDataType(1);		
        this.txtRoomArea.setEnabled(false);
        // txtStandardRoomPrice		
        this.txtStandardRoomPrice.setDataType(1);		
        this.txtStandardRoomPrice.setEnabled(false);
        // txtStandardTotalAmount		
        this.txtStandardTotalAmount.setDataType(1);		
        this.txtStandardTotalAmount.setEnabled(false);
        // txtDealRoomPrice		
        this.txtDealRoomPrice.setDataType(1);		
        this.txtDealRoomPrice.setEnabled(false);
        // txtStandardBuildPrice		
        this.txtStandardBuildPrice.setDataType(1);		
        this.txtStandardBuildPrice.setEnabled(false);
        // txtDealBuildPrice		
        this.txtDealBuildPrice.setDataType(1);		
        this.txtDealBuildPrice.setEnabled(false);
        // txtSaleMan		
        this.txtSaleMan.setEnabled(false);
        // txtActBuildArea		
        this.txtActBuildArea.setDataType(1);		
        this.txtActBuildArea.setEnabled(false);
        // txtActRoomArea		
        this.txtActRoomArea.setDataType(1);		
        this.txtActRoomArea.setEnabled(false);
        // txtDealTotalAmount		
        this.txtDealTotalAmount.setDataType(1);		
        this.txtDealTotalAmount.setEnabled(false);
        // buyRoomPlanPane
        // bizTotalFlow		
        this.bizTotalFlow.setAutoscrolls(true);
        // roomPropertyBook
        // roomJoin
        // sincerityPurchase
        // keepDown
        // roomLoanPanel
        // totalFlowTable
		String totalFlowTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"biz\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"apDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol2\" /><t:Column t:key=\"value\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"actDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol4\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{biz}</t:Cell><t:Cell>$Resource{apDate}</t:Cell><t:Cell>$Resource{value}</t:Cell><t:Cell>$Resource{actDate}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.totalFlowTable.setFormatXml(resHelper.translateString("totalFlowTable",totalFlowTableStrXML));
        this.totalFlowTable.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    totalFlowTable_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(30);
        // labelCustomer1		
        this.labelCustomer1.setOpaque(true);
        this.labelCustomer1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labelCustomer1_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator5
        // labelCustomer2		
        this.labelCustomer2.setOpaque(true);
        this.labelCustomer2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labelCustomer2_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator6
        // labelCustomer3		
        this.labelCustomer3.setOpaque(true);
        this.labelCustomer3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labelCustomer3_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator7
        // labelCustomer4		
        this.labelCustomer4.setOpaque(true);
        this.labelCustomer4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labelCustomer4_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator8
        // labelCustomer5		
        this.labelCustomer5.setOpaque(true);
        this.labelCustomer5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labelCustomer5_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator9
        // kDContainer6
        // kDContainer7
        // tblPBProcesser
		String tblPBProcesserStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"processName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"limitDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isFinish\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"person\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{processName}</t:Cell><t:Cell>$Resource{limitDate}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{actDate}</t:Cell><t:Cell>$Resource{person}</t:Cell><t:Cell>$Resource{des}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblPBProcesser.setFormatXml(resHelper.translateString("tblPBProcesser",tblPBProcesserStrXML));
        this.tblPBProcesser.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblPBProcesser_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // tblPBData
		String tblPBDataStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"dataName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isOk\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{dataName}</t:Cell><t:Cell>$Resource{isOk}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{des}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblPBData.setFormatXml(resHelper.translateString("tblPBData",tblPBDataStrXML));
        this.tblPBData.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblPBData_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDContainer4
        // kDContainer5
        // tblJoinProcesses
		String tblJoinProcessesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"processName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"limitDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isFinish\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"person\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{processName}</t:Cell><t:Cell>$Resource{limitDate}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{actDate}</t:Cell><t:Cell>$Resource{person}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblJoinProcesses.setFormatXml(resHelper.translateString("tblJoinProcesses",tblJoinProcessesStrXML));
        this.tblJoinProcesses.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblJoinProcesses_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // tblJoinData
		String tblJoinDataStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"dataName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isOk\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{dataName}</t:Cell><t:Cell>$Resource{isOk}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblJoinData.setFormatXml(resHelper.translateString("tblJoinData",tblJoinDataStrXML));
        this.tblJoinData.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblJoinData_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // sincerityPurchaseTable
		String sincerityPurchaseTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"sincerityState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"appointmentPeople\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appointmentPhone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"projectNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"invalidationDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"salesman\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{sincerityState}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{appointmentPeople}</t:Cell><t:Cell>$Resource{appointmentPhone}</t:Cell><t:Cell>$Resource{projectNum}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{invalidationDate}</t:Cell><t:Cell>$Resource{salesman}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.sincerityPurchaseTable.setFormatXml(resHelper.translateString("sincerityPurchaseTable",sincerityPurchaseTableStrXML));		
        this.sincerityPurchaseTable.setPreferredSize(new Dimension(900,900));
        this.sincerityPurchaseTable.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    sincerityPurchaseTable_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // keepDownTable
		String keepDownTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"reason\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"keepDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"IsExtended\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"handler\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{reason}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{keepDate}</t:Cell><t:Cell>$Resource{IsExtended}</t:Cell><t:Cell>$Resource{handler}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.keepDownTable.setFormatXml(resHelper.translateString("keepDownTable",keepDownTableStrXML));
        this.keepDownTable.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    keepDownTable_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDContainer2
        // tblLoan
		String tblLoanStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"loanNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"loanState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"curProcess\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"promiseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"afmType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"afmAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"afm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{loanNumber}</t:Cell><t:Cell>$Resource{loanState}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{curProcess}</t:Cell><t:Cell>$Resource{promiseDate}</t:Cell><t:Cell>$Resource{actFinishDate}</t:Cell><t:Cell>$Resource{afmType}</t:Cell><t:Cell>$Resource{afmAmount}</t:Cell><t:Cell>$Resource{afm}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblLoan.setFormatXml(resHelper.translateString("tblLoan",tblLoanStrXML));
        this.tblLoan.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblLoan_tableClicked(e);
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
		list.add(this.kDToolBar1);
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 500, 829));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 500, 829));
        kDContainer1.setBounds(new Rectangle(2, 1, 492, 236));
        this.add(kDContainer1, new KDLayout.Constraints(2, 1, 492, 236, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tabBizInfo.setBounds(new Rectangle(4, 248, 492, 573));
        this.add(tabBizInfo, new KDLayout.Constraints(4, 248, 492, 573, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(2, 1, 492, 236));        conNumber.setBounds(new Rectangle(6, 6, 474, 19));
        kDContainer1.getContentPane().add(conNumber, new KDLayout.Constraints(6, 6, 474, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomModelType.setBounds(new Rectangle(6, 28, 222, 19));
        kDContainer1.getContentPane().add(contRoomModelType, new KDLayout.Constraints(6, 28, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomModel.setBounds(new Rectangle(258, 28, 222, 19));
        kDContainer1.getContentPane().add(contRoomModel, new KDLayout.Constraints(258, 28, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomState.setBounds(new Rectangle(258, 50, 222, 19));
        kDContainer1.getContentPane().add(contRoomState, new KDLayout.Constraints(258, 50, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHouseProperty.setBounds(new Rectangle(6, 50, 222, 19));
        kDContainer1.getContentPane().add(contHouseProperty, new KDLayout.Constraints(6, 50, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPricingType.setBounds(new Rectangle(258, 116, 222, 19));
        kDContainer1.getContentPane().add(contPricingType, new KDLayout.Constraints(258, 116, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuildingArea.setBounds(new Rectangle(6, 72, 222, 19));
        kDContainer1.getContentPane().add(contBuildingArea, new KDLayout.Constraints(6, 72, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomArea.setBounds(new Rectangle(258, 72, 222, 19));
        kDContainer1.getContentPane().add(contRoomArea, new KDLayout.Constraints(258, 72, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStandardRoomPrice.setBounds(new Rectangle(258, 138, 222, 19));
        kDContainer1.getContentPane().add(contStandardRoomPrice, new KDLayout.Constraints(258, 138, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStandardTotalAmount.setBounds(new Rectangle(6, 116, 222, 19));
        kDContainer1.getContentPane().add(contStandardTotalAmount, new KDLayout.Constraints(6, 116, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDealRoomPrice.setBounds(new Rectangle(258, 182, 222, 19));
        kDContainer1.getContentPane().add(contDealRoomPrice, new KDLayout.Constraints(258, 182, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStandardBuildPrice.setBounds(new Rectangle(6, 138, 222, 19));
        kDContainer1.getContentPane().add(contStandardBuildPrice, new KDLayout.Constraints(6, 138, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDealBuildPrice.setBounds(new Rectangle(6, 182, 222, 19));
        kDContainer1.getContentPane().add(contDealBuildPrice, new KDLayout.Constraints(6, 182, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSaleMan.setBounds(new Rectangle(258, 160, 222, 19));
        kDContainer1.getContentPane().add(contSaleMan, new KDLayout.Constraints(258, 160, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(6, 94, 222, 19));
        kDContainer1.getContentPane().add(kDLabelContainer1, new KDLayout.Constraints(6, 94, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(258, 94, 222, 19));
        kDContainer1.getContentPane().add(kDLabelContainer2, new KDLayout.Constraints(258, 94, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel1.setBounds(new Rectangle(6, 163, 75, 19));
        kDContainer1.getContentPane().add(kDLabel1, new KDLayout.Constraints(6, 163, 75, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(6, 160, 222, 19));
        kDContainer1.getContentPane().add(kDLabelContainer4, new KDLayout.Constraints(6, 160, 222, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //conNumber
        conNumber.setBoundEditor(txtNumber);
        //contRoomModelType
        contRoomModelType.setBoundEditor(txtRoomModelType);
        //contRoomModel
        contRoomModel.setBoundEditor(f7RoomModel);
        //contRoomState
        contRoomState.setBoundEditor(boxRoomState);
        //contHouseProperty
        contHouseProperty.setBoundEditor(comboHouseProperty);
        //contPricingType
        contPricingType.setBoundEditor(txtPricingType);
        //contBuildingArea
        contBuildingArea.setBoundEditor(txtBuildingArea);
        //contRoomArea
        contRoomArea.setBoundEditor(txtRoomArea);
        //contStandardRoomPrice
        contStandardRoomPrice.setBoundEditor(txtStandardRoomPrice);
        //contStandardTotalAmount
        contStandardTotalAmount.setBoundEditor(txtStandardTotalAmount);
        //contDealRoomPrice
        contDealRoomPrice.setBoundEditor(txtDealRoomPrice);
        //contStandardBuildPrice
        contStandardBuildPrice.setBoundEditor(txtStandardBuildPrice);
        //contDealBuildPrice
        contDealBuildPrice.setBoundEditor(txtDealBuildPrice);
        //contSaleMan
        contSaleMan.setBoundEditor(txtSaleMan);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtActBuildArea);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtActRoomArea);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtDealTotalAmount);
        //tabBizInfo
        tabBizInfo.add(buyRoomPlanPane, resHelper.getString("buyRoomPlanPane.constraints"));
        tabBizInfo.add(bizTotalFlow, resHelper.getString("bizTotalFlow.constraints"));
        tabBizInfo.add(roomPropertyBook, resHelper.getString("roomPropertyBook.constraints"));
        tabBizInfo.add(roomJoin, resHelper.getString("roomJoin.constraints"));
        tabBizInfo.add(sincerityPurchase, resHelper.getString("sincerityPurchase.constraints"));
        tabBizInfo.add(keepDown, resHelper.getString("keepDown.constraints"));
        tabBizInfo.add(roomLoanPanel, resHelper.getString("roomLoanPanel.constraints"));
        //bizTotalFlow
        bizTotalFlow.setLayout(new KDLayout());
        bizTotalFlow.putClientProperty("OriginalBounds", new Rectangle(0, 0, 491, 540));        totalFlowTable.setBounds(new Rectangle(6, 37, 474, 500));
        bizTotalFlow.add(totalFlowTable, new KDLayout.Constraints(6, 37, 474, 500, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(12, 12, 30, 19));
        bizTotalFlow.add(kDLabelContainer3, new KDLayout.Constraints(12, 12, 30, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labelCustomer1.setBounds(new Rectangle(44, 10, 56, 19));
        bizTotalFlow.add(labelCustomer1, new KDLayout.Constraints(44, 10, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator5.setBounds(new Rectangle(44, 30, 56, 10));
        bizTotalFlow.add(kDSeparator5, new KDLayout.Constraints(44, 30, 56, 10, 0));
        labelCustomer2.setBounds(new Rectangle(101, 10, 56, 19));
        bizTotalFlow.add(labelCustomer2, new KDLayout.Constraints(101, 10, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator6.setBounds(new Rectangle(101, 30, 56, 10));
        bizTotalFlow.add(kDSeparator6, new KDLayout.Constraints(101, 30, 56, 10, 0));
        labelCustomer3.setBounds(new Rectangle(158, 10, 56, 19));
        bizTotalFlow.add(labelCustomer3, new KDLayout.Constraints(158, 10, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator7.setBounds(new Rectangle(158, 30, 56, 10));
        bizTotalFlow.add(kDSeparator7, new KDLayout.Constraints(158, 30, 56, 10, 0));
        labelCustomer4.setBounds(new Rectangle(215, 10, 56, 19));
        bizTotalFlow.add(labelCustomer4, new KDLayout.Constraints(215, 10, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator8.setBounds(new Rectangle(215, 30, 56, 10));
        bizTotalFlow.add(kDSeparator8, new KDLayout.Constraints(215, 30, 56, 10, 0));
        labelCustomer5.setBounds(new Rectangle(273, 10, 56, 19));
        bizTotalFlow.add(labelCustomer5, new KDLayout.Constraints(273, 10, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator9.setBounds(new Rectangle(273, 30, 56, 10));
        bizTotalFlow.add(kDSeparator9, new KDLayout.Constraints(273, 30, 56, 10, 0));
        //roomPropertyBook
        roomPropertyBook.setLayout(new KDLayout());
        roomPropertyBook.putClientProperty("OriginalBounds", new Rectangle(0, 0, 491, 540));        kDContainer6.setBounds(new Rectangle(8, 8, 470, 303));
        roomPropertyBook.add(kDContainer6, new KDLayout.Constraints(8, 8, 470, 303, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer7.setBounds(new Rectangle(8, 320, 470, 247));
        roomPropertyBook.add(kDContainer7, new KDLayout.Constraints(8, 320, 470, 247, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDContainer6
        kDContainer6.getContentPane().setLayout(new KDLayout());
        kDContainer6.getContentPane().putClientProperty("OriginalBounds", new Rectangle(8, 8, 470, 303));        tblPBProcesser.setBounds(new Rectangle(0, 0, 462, 277));
        kDContainer6.getContentPane().add(tblPBProcesser, new KDLayout.Constraints(0, 0, 462, 277, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDContainer7
        kDContainer7.getContentPane().setLayout(new KDLayout());
        kDContainer7.getContentPane().putClientProperty("OriginalBounds", new Rectangle(8, 320, 470, 247));        tblPBData.setBounds(new Rectangle(0, 0, 460, 221));
        kDContainer7.getContentPane().add(tblPBData, new KDLayout.Constraints(0, 0, 460, 221, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //roomJoin
        roomJoin.setLayout(new KDLayout());
        roomJoin.putClientProperty("OriginalBounds", new Rectangle(0, 0, 491, 540));        kDContainer4.setBounds(new Rectangle(8, 8, 470, 303));
        roomJoin.add(kDContainer4, new KDLayout.Constraints(8, 8, 470, 303, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer5.setBounds(new Rectangle(8, 320, 470, 247));
        roomJoin.add(kDContainer5, new KDLayout.Constraints(8, 320, 470, 247, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDContainer4
        kDContainer4.getContentPane().setLayout(new KDLayout());
        kDContainer4.getContentPane().putClientProperty("OriginalBounds", new Rectangle(8, 8, 470, 303));        tblJoinProcesses.setBounds(new Rectangle(0, 0, 462, 277));
        kDContainer4.getContentPane().add(tblJoinProcesses, new KDLayout.Constraints(0, 0, 462, 277, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDContainer5
        kDContainer5.getContentPane().setLayout(new KDLayout());
        kDContainer5.getContentPane().putClientProperty("OriginalBounds", new Rectangle(8, 320, 470, 247));        tblJoinData.setBounds(new Rectangle(0, 0, 460, 221));
        kDContainer5.getContentPane().add(tblJoinData, new KDLayout.Constraints(0, 0, 460, 221, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //sincerityPurchase
        sincerityPurchase.setLayout(new KDLayout());
        sincerityPurchase.putClientProperty("OriginalBounds", new Rectangle(0, 0, 491, 540));        sincerityPurchaseTable.setBounds(new Rectangle(6, 6, 474, 559));
        sincerityPurchase.add(sincerityPurchaseTable, new KDLayout.Constraints(6, 6, 474, 559, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //keepDown
        keepDown.setLayout(new KDLayout());
        keepDown.putClientProperty("OriginalBounds", new Rectangle(0, 0, 491, 540));        keepDownTable.setBounds(new Rectangle(2, 3, 482, 567));
        keepDown.add(keepDownTable, new KDLayout.Constraints(2, 3, 482, 567, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //roomLoanPanel
        roomLoanPanel.setLayout(new KDLayout());
        roomLoanPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 491, 540));        kDContainer2.setBounds(new Rectangle(8, 8, 470, 512));
        roomLoanPanel.add(kDContainer2, new KDLayout.Constraints(8, 8, 470, 512, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDContainer2
        kDContainer2.getContentPane().setLayout(new KDLayout());
        kDContainer2.getContentPane().putClientProperty("OriginalBounds", new Rectangle(8, 8, 470, 512));        tblLoan.setBounds(new Rectangle(0, 0, 469, 538));
        kDContainer2.getContentPane().add(tblLoan, new KDLayout.Constraints(0, 0, 469, 538, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnReset);



    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.VirtualRoomFullInfoUIHandler";
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
     * output setDataObject method
     */
    public void setDataObject(String key, IObjectValue dataObject)
    {
        super.setDataObject(key, dataObject);
        if (key.equalsIgnoreCase("roomKeepDownBillQuery")) {
            this.roomKeepDownBillQuery = (EntityViewInfo)dataObject;
		}
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
     * output totalFlowTable_tableClicked method
     */
    protected void totalFlowTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output labelCustomer1_mouseClicked method
     */
    protected void labelCustomer1_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output labelCustomer2_mouseClicked method
     */
    protected void labelCustomer2_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output labelCustomer3_mouseClicked method
     */
    protected void labelCustomer3_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output labelCustomer4_mouseClicked method
     */
    protected void labelCustomer4_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output labelCustomer5_mouseClicked method
     */
    protected void labelCustomer5_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output tblPBProcesser_tableClicked method
     */
    protected void tblPBProcesser_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblPBData_tableClicked method
     */
    protected void tblPBData_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblJoinProcesses_tableClicked method
     */
    protected void tblJoinProcesses_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblJoinData_tableClicked method
     */
    protected void tblJoinData_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output sincerityPurchaseTable_tableClicked method
     */
    protected void sincerityPurchaseTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output keepDownTable_tableClicked method
     */
    protected void keepDownTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblLoan_tableClicked method
     */
    protected void tblLoan_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
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
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "VirtualRoomFullInfoUI");
    }




}