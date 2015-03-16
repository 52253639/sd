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
public abstract class AbstractSimulantSellUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSimulantSellUI.class);
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFloorHeight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboRoomState;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoomInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardPrice;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsCalByRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHouseProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOutlook;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorHeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSelectRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlSubscribe;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PayType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgios;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAreaComAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttachAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFitmentAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAttachAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAreaComAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAgio;
    protected com.kingdee.bos.ctrl.swing.KDButton btnChooseAgio;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Direcition;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Sight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLoanData;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7LoanData;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tabPayment;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSimuPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSimuPrintPreView;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboDigit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLittleAdjust;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCalc;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected ActionSimuPrint actionSimuPrint = null;
    protected ActionSimuPrintPreView actionSimuPrintPreView = null;
    protected ActionCalc actionCalc = null;
    /**
     * output class constructor
     */
    public AbstractSimulantSellUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSimulantSellUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSimuPrint
        this.actionSimuPrint = new ActionSimuPrint(this);
        getActionManager().registerAction("actionSimuPrint", actionSimuPrint);
         this.actionSimuPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSimuPrintPreView
        this.actionSimuPrintPreView = new ActionSimuPrintPreView(this);
        getActionManager().registerAction("actionSimuPrintPreView", actionSimuPrintPreView);
         this.actionSimuPrintPreView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCalc
        this.actionCalc = new ActionCalc(this);
        getActionManager().registerAction("actionCalc", actionCalc);
         this.actionCalc.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtApportionArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBalconyArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFloorHeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7RoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboHouseProperty = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActualRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboRoomState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtStandardPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStandardTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pnlRoomInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contStandardTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsCalByRoom = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contRoomState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contHouseProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOutlook = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorHeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBalconyArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApportionArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRoomName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSelectRoom = new com.kingdee.bos.ctrl.swing.KDButton();
        this.pnlSubscribe = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contPayType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7PayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contAgios = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAreaComAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttachAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFitmentAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtContractAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFitmentAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAttachAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAreaComAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAgio = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnChooseAgio = new com.kingdee.bos.ctrl.swing.KDButton();
        this.f7Direcition = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Sight = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contLoanData = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7LoanData = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.tabPayment = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTabbedPane2 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSimuPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSimuPrintPreView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.comboDigit = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnLittleAdjust = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCalc = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtBuildingArea.setName("txtBuildingArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.txtApportionArea.setName("txtApportionArea");
        this.txtBalconyArea.setName("txtBalconyArea");
        this.txtFloorHeight.setName("txtFloorHeight");
        this.f7RoomModel.setName("f7RoomModel");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.comboHouseProperty.setName("comboHouseProperty");
        this.txtActualBuildingArea.setName("txtActualBuildingArea");
        this.txtActualRoomArea.setName("txtActualRoomArea");
        this.comboRoomState.setName("comboRoomState");
        this.txtStandardPrice.setName("txtStandardPrice");
        this.txtStandardTotalAmount.setName("txtStandardTotalAmount");
        this.pnlRoomInfo.setName("pnlRoomInfo");
        this.contStandardTotalAmount.setName("contStandardTotalAmount");
        this.contStandardPrice.setName("contStandardPrice");
        this.chkIsCalByRoom.setName("chkIsCalByRoom");
        this.contRoomState.setName("contRoomState");
        this.contActualRoomArea.setName("contActualRoomArea");
        this.contActualBuildingArea.setName("contActualBuildingArea");
        this.kDSeparator5.setName("kDSeparator5");
        this.contHouseProperty.setName("contHouseProperty");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.contRoomModel.setName("contRoomModel");
        this.contOutlook.setName("contOutlook");
        this.contFace.setName("contFace");
        this.contFloorHeight.setName("contFloorHeight");
        this.contBalconyArea.setName("contBalconyArea");
        this.contApportionArea.setName("contApportionArea");
        this.contRoomArea.setName("contRoomArea");
        this.contBuildingArea.setName("contBuildingArea");
        this.txtRoomName.setName("txtRoomName");
        this.contRoom.setName("contRoom");
        this.btnSelectRoom.setName("btnSelectRoom");
        this.pnlSubscribe.setName("pnlSubscribe");
        this.contPayType.setName("contPayType");
        this.f7PayType.setName("f7PayType");
        this.contAgios.setName("contAgios");
        this.contAreaComAmount.setName("contAreaComAmount");
        this.contAttachAmount.setName("contAttachAmount");
        this.contFitmentAmount.setName("contFitmentAmount");
        this.contContractAmount.setName("contContractAmount");
        this.txtContractAmount.setName("txtContractAmount");
        this.txtFitmentAmount.setName("txtFitmentAmount");
        this.txtAttachAmount.setName("txtAttachAmount");
        this.txtAreaComAmount.setName("txtAreaComAmount");
        this.txtAgio.setName("txtAgio");
        this.btnChooseAgio.setName("btnChooseAgio");
        this.f7Direcition.setName("f7Direcition");
        this.f7Sight.setName("f7Sight");
        this.contLoanData.setName("contLoanData");
        this.f7LoanData.setName("f7LoanData");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.tabPayment.setName("tabPayment");
        this.kDTabbedPane2.setName("kDTabbedPane2");
        this.kDTable1.setName("kDTable1");
        this.btnSimuPrint.setName("btnSimuPrint");
        this.btnSimuPrintPreView.setName("btnSimuPrintPreView");
        this.comboDigit.setName("comboDigit");
        this.btnLittleAdjust.setName("btnLittleAdjust");
        this.btnCalc.setName("btnCalc");
        // CoreUI		
        this.setPreferredSize(new Dimension(900,650));
        // txtBuildingArea		
        this.txtBuildingArea.setDataType(1);
        // txtRoomArea		
        this.txtRoomArea.setDataType(1);
        // txtApportionArea		
        this.txtApportionArea.setDataType(1);
        // txtBalconyArea		
        this.txtBalconyArea.setDataType(1);
        // txtFloorHeight		
        this.txtFloorHeight.setDataType(1);
        // f7RoomModel		
        this.f7RoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");		
        this.f7RoomModel.setCommitFormat("$number$");		
        this.f7RoomModel.setDisplayFormat("$name$");		
        this.f7RoomModel.setEditFormat("$number$");
        // f7BuildingProperty		
        this.f7BuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");		
        this.f7BuildingProperty.setDisplayFormat("$name$");		
        this.f7BuildingProperty.setEditFormat("$number$");		
        this.f7BuildingProperty.setCommitFormat("$number$");
        // comboHouseProperty		
        this.comboHouseProperty.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.HousePropertyEnum").toArray());
        // txtActualBuildingArea		
        this.txtActualBuildingArea.setDataType(1);
        // txtActualRoomArea		
        this.txtActualRoomArea.setDataType(1);
        // comboRoomState		
        this.comboRoomState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum").toArray());
        // txtStandardPrice		
        this.txtStandardPrice.setDataType(1);
        // txtStandardTotalAmount		
        this.txtStandardTotalAmount.setDataType(1);
        // pnlRoomInfo		
        this.pnlRoomInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlRoomInfo.border.title")));		
        this.pnlRoomInfo.setPreferredSize(new Dimension(456,298));
        // contStandardTotalAmount		
        this.contStandardTotalAmount.setBoundLabelText(resHelper.getString("contStandardTotalAmount.boundLabelText"));		
        this.contStandardTotalAmount.setBoundLabelLength(50);		
        this.contStandardTotalAmount.setBoundLabelUnderline(true);
        // contStandardPrice		
        this.contStandardPrice.setBoundLabelText(resHelper.getString("contStandardPrice.boundLabelText"));		
        this.contStandardPrice.setBoundLabelLength(50);		
        this.contStandardPrice.setBoundLabelUnderline(true);
        // chkIsCalByRoom		
        this.chkIsCalByRoom.setText(resHelper.getString("chkIsCalByRoom.text"));
        // contRoomState		
        this.contRoomState.setBoundLabelText(resHelper.getString("contRoomState.boundLabelText"));		
        this.contRoomState.setBoundLabelLength(50);		
        this.contRoomState.setBoundLabelUnderline(true);
        // contActualRoomArea		
        this.contActualRoomArea.setBoundLabelText(resHelper.getString("contActualRoomArea.boundLabelText"));		
        this.contActualRoomArea.setBoundLabelUnderline(true);		
        this.contActualRoomArea.setBoundLabelLength(50);
        // contActualBuildingArea		
        this.contActualBuildingArea.setBoundLabelText(resHelper.getString("contActualBuildingArea.boundLabelText"));		
        this.contActualBuildingArea.setBoundLabelLength(50);		
        this.contActualBuildingArea.setBoundLabelUnderline(true);
        // kDSeparator5
        // contHouseProperty		
        this.contHouseProperty.setBoundLabelText(resHelper.getString("contHouseProperty.boundLabelText"));		
        this.contHouseProperty.setBoundLabelLength(50);		
        this.contHouseProperty.setBoundLabelUnderline(true);
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(50);		
        this.contBuildingProperty.setBoundLabelUnderline(true);
        // contRoomModel		
        this.contRoomModel.setBoundLabelText(resHelper.getString("contRoomModel.boundLabelText"));		
        this.contRoomModel.setBoundLabelLength(50);		
        this.contRoomModel.setBoundLabelUnderline(true);
        // contOutlook		
        this.contOutlook.setBoundLabelText(resHelper.getString("contOutlook.boundLabelText"));		
        this.contOutlook.setBoundLabelLength(50);		
        this.contOutlook.setBoundLabelUnderline(true);
        // contFace		
        this.contFace.setBoundLabelText(resHelper.getString("contFace.boundLabelText"));		
        this.contFace.setBoundLabelLength(50);		
        this.contFace.setBoundLabelUnderline(true);
        // contFloorHeight		
        this.contFloorHeight.setBoundLabelText(resHelper.getString("contFloorHeight.boundLabelText"));		
        this.contFloorHeight.setBoundLabelLength(50);		
        this.contFloorHeight.setBoundLabelUnderline(true);
        // contBalconyArea		
        this.contBalconyArea.setBoundLabelText(resHelper.getString("contBalconyArea.boundLabelText"));		
        this.contBalconyArea.setBoundLabelLength(50);		
        this.contBalconyArea.setBoundLabelUnderline(true);
        // contApportionArea		
        this.contApportionArea.setBoundLabelText(resHelper.getString("contApportionArea.boundLabelText"));		
        this.contApportionArea.setBoundLabelLength(50);		
        this.contApportionArea.setBoundLabelUnderline(true);
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelLength(50);		
        this.contRoomArea.setBoundLabelUnderline(true);
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelLength(50);		
        this.contBuildingArea.setBoundLabelUnderline(true);
        // txtRoomName
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(60);		
        this.contRoom.setBoundLabelUnderline(true);
        // btnSelectRoom		
        this.btnSelectRoom.setText(resHelper.getString("btnSelectRoom.text"));
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
        // pnlSubscribe		
        this.pnlSubscribe.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlSubscribe.border.title")));
        // contPayType		
        this.contPayType.setBoundLabelText(resHelper.getString("contPayType.boundLabelText"));		
        this.contPayType.setBoundLabelLength(100);		
        this.contPayType.setBoundLabelUnderline(true);
        // f7PayType		
        this.f7PayType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHEPayTypeQuery");
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
        // contAgios		
        this.contAgios.setBoundLabelText(resHelper.getString("contAgios.boundLabelText"));		
        this.contAgios.setBoundLabelUnderline(true);		
        this.contAgios.setBoundLabelLength(100);
        // contAreaComAmount		
        this.contAreaComAmount.setBoundLabelText(resHelper.getString("contAreaComAmount.boundLabelText"));		
        this.contAreaComAmount.setBoundLabelUnderline(true);		
        this.contAreaComAmount.setBoundLabelLength(100);
        // contAttachAmount		
        this.contAttachAmount.setBoundLabelText(resHelper.getString("contAttachAmount.boundLabelText"));		
        this.contAttachAmount.setBoundLabelLength(100);		
        this.contAttachAmount.setBoundLabelUnderline(true);
        // contFitmentAmount		
        this.contFitmentAmount.setBoundLabelText(resHelper.getString("contFitmentAmount.boundLabelText"));		
        this.contFitmentAmount.setBoundLabelUnderline(true);		
        this.contFitmentAmount.setBoundLabelLength(100);
        // contContractAmount		
        this.contContractAmount.setBoundLabelText(resHelper.getString("contContractAmount.boundLabelText"));		
        this.contContractAmount.setBoundLabelLength(100);		
        this.contContractAmount.setBoundLabelUnderline(true);
        // txtContractAmount		
        this.txtContractAmount.setDataType(1);
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
        // txtAttachAmount		
        this.txtAttachAmount.setDataType(1);
        this.txtAttachAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtAttachAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtAreaComAmount		
        this.txtAreaComAmount.setDataType(1);
        this.txtAreaComAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtAreaComAmount_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.txtAreaComAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtAreaComAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtAgio
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
        // f7Direcition		
        this.f7Direcition.setDisplayFormat("$name$");		
        this.f7Direcition.setEditFormat("$number$");		
        this.f7Direcition.setCommitFormat("$number$");		
        this.f7Direcition.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");
        // f7Sight		
        this.f7Sight.setDisplayFormat("$name$");		
        this.f7Sight.setEditFormat("$number$");		
        this.f7Sight.setCommitFormat("$number$");		
        this.f7Sight.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SightRequirementQuery");
        // contLoanData		
        this.contLoanData.setBoundLabelText(resHelper.getString("contLoanData.boundLabelText"));		
        this.contLoanData.setBoundLabelLength(100);		
        this.contLoanData.setBoundLabelUnderline(true);
        // f7LoanData		
        this.f7LoanData.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.LoanDataF7Query");		
        this.f7LoanData.setDisplayFormat("$name$");		
        this.f7LoanData.setEditFormat("$number$");		
        this.f7LoanData.setCommitFormat("$number$");
        this.f7LoanData.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7LoanData_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDTabbedPane1
        // tabPayment
		String tabPaymentStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"term\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"apAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{term}</t:Cell><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{apAmount}</t:Cell><t:Cell>$Resource{currency}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tabPayment.setFormatXml(resHelper.translateString("tabPayment",tabPaymentStrXML));

        

        // kDTabbedPane2
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"bizTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"month\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"day\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"pro\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"term\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"jiange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{bizTime}</t:Cell><t:Cell>$Resource{month}</t:Cell><t:Cell>$Resource{day}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{pro}</t:Cell><t:Cell>$Resource{term}</t:Cell><t:Cell>$Resource{jiange}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));
        this.kDTable1.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kDTable1_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnSimuPrint
        this.btnSimuPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionSimuPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSimuPrint.setText(resHelper.getString("btnSimuPrint.text"));		
        this.btnSimuPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // btnSimuPrintPreView
        this.btnSimuPrintPreView.setAction((IItemAction)ActionProxyFactory.getProxy(actionSimuPrintPreView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSimuPrintPreView.setText(resHelper.getString("btnSimuPrintPreView.text"));		
        this.btnSimuPrintPreView.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // comboDigit
        // btnLittleAdjust		
        this.btnLittleAdjust.setText(resHelper.getString("btnLittleAdjust.text"));
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
        // btnCalc
        this.btnCalc.setAction((IItemAction)ActionProxyFactory.getProxy(actionCalc, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCalc.setText(resHelper.getString("btnCalc.text"));		
        this.btnCalc.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_compute"));
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
        this.setBounds(new Rectangle(10, 10, 1000, 620));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1000, 620));
        pnlRoomInfo.setBounds(new Rectangle(13, 7, 456, 298));
        this.add(pnlRoomInfo, new KDLayout.Constraints(13, 7, 456, 298, 0));
        pnlSubscribe.setBounds(new Rectangle(475, 6, 343, 300));
        this.add(pnlSubscribe, new KDLayout.Constraints(475, 6, 343, 300, 0));
        kDTabbedPane1.setBounds(new Rectangle(18, 465, 790, 137));
        this.add(kDTabbedPane1, new KDLayout.Constraints(18, 465, 790, 137, KDLayout.Constraints.ANCHOR_TOP));
        kDTabbedPane2.setBounds(new Rectangle(18, 308, 790, 150));
        this.add(kDTabbedPane2, new KDLayout.Constraints(18, 308, 790, 150, 0));
        //pnlRoomInfo
        pnlRoomInfo.setLayout(null);        contStandardTotalAmount.setBounds(new Rectangle(232, 234, 199, 19));
        pnlRoomInfo.add(contStandardTotalAmount, null);
        contStandardPrice.setBounds(new Rectangle(17, 236, 207, 20));
        pnlRoomInfo.add(contStandardPrice, null);
        chkIsCalByRoom.setBounds(new Rectangle(16, 264, 154, 20));
        pnlRoomInfo.add(chkIsCalByRoom, null);
        contRoomState.setBounds(new Rectangle(231, 262, 199, 19));
        pnlRoomInfo.add(contRoomState, null);
        contActualRoomArea.setBounds(new Rectangle(236, 78, 195, 20));
        pnlRoomInfo.add(contActualRoomArea, null);
        contActualBuildingArea.setBounds(new Rectangle(19, 78, 207, 19));
        pnlRoomInfo.add(contActualBuildingArea, null);
        kDSeparator5.setBounds(new Rectangle(17, 126, 418, 10));
        pnlRoomInfo.add(kDSeparator5, null);
        contHouseProperty.setBounds(new Rectangle(232, 202, 199, 20));
        pnlRoomInfo.add(contHouseProperty, null);
        contBuildingProperty.setBounds(new Rectangle(17, 204, 207, 19));
        pnlRoomInfo.add(contBuildingProperty, null);
        contRoomModel.setBounds(new Rectangle(232, 169, 199, 19));
        pnlRoomInfo.add(contRoomModel, null);
        contOutlook.setBounds(new Rectangle(17, 169, 206, 19));
        pnlRoomInfo.add(contOutlook, null);
        contFace.setBounds(new Rectangle(232, 135, 199, 19));
        pnlRoomInfo.add(contFace, null);
        contFloorHeight.setBounds(new Rectangle(17, 136, 205, 19));
        pnlRoomInfo.add(contFloorHeight, null);
        contBalconyArea.setBounds(new Rectangle(235, 102, 196, 21));
        pnlRoomInfo.add(contBalconyArea, null);
        contApportionArea.setBounds(new Rectangle(20, 100, 205, 21));
        pnlRoomInfo.add(contApportionArea, null);
        contRoomArea.setBounds(new Rectangle(235, 55, 197, 19));
        pnlRoomInfo.add(contRoomArea, null);
        contBuildingArea.setBounds(new Rectangle(20, 55, 205, 20));
        pnlRoomInfo.add(contBuildingArea, null);
        contRoom.setBounds(new Rectangle(21, 20, 205, 19));
        pnlRoomInfo.add(contRoom, null);
        btnSelectRoom.setBounds(new Rectangle(232, 19, 78, 19));
        pnlRoomInfo.add(btnSelectRoom, null);
        //contStandardTotalAmount
        contStandardTotalAmount.setBoundEditor(txtStandardTotalAmount);
        //contStandardPrice
        contStandardPrice.setBoundEditor(txtStandardPrice);
        //contRoomState
        contRoomState.setBoundEditor(comboRoomState);
        //contActualRoomArea
        contActualRoomArea.setBoundEditor(txtActualRoomArea);
        //contActualBuildingArea
        contActualBuildingArea.setBoundEditor(txtActualBuildingArea);
        //contHouseProperty
        contHouseProperty.setBoundEditor(comboHouseProperty);
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(f7BuildingProperty);
        //contRoomModel
        contRoomModel.setBoundEditor(f7RoomModel);
        //contOutlook
        contOutlook.setBoundEditor(f7Sight);
        //contFace
        contFace.setBoundEditor(f7Direcition);
        //contFloorHeight
        contFloorHeight.setBoundEditor(txtFloorHeight);
        //contBalconyArea
        contBalconyArea.setBoundEditor(txtBalconyArea);
        //contApportionArea
        contApportionArea.setBoundEditor(txtApportionArea);
        //contRoomArea
        contRoomArea.setBoundEditor(txtRoomArea);
        //contBuildingArea
        contBuildingArea.setBoundEditor(txtBuildingArea);
        //contRoom
        contRoom.setBoundEditor(txtRoomName);
        //pnlSubscribe
        pnlSubscribe.setLayout(null);        contPayType.setBounds(new Rectangle(27, 25, 270, 19));
        pnlSubscribe.add(contPayType, null);
        contAgios.setBounds(new Rectangle(28, 91, 196, 19));
        pnlSubscribe.add(contAgios, null);
        contAreaComAmount.setBounds(new Rectangle(28, 124, 270, 19));
        pnlSubscribe.add(contAreaComAmount, null);
        contAttachAmount.setBounds(new Rectangle(28, 157, 270, 19));
        pnlSubscribe.add(contAttachAmount, null);
        contFitmentAmount.setBounds(new Rectangle(28, 190, 270, 19));
        pnlSubscribe.add(contFitmentAmount, null);
        contContractAmount.setBounds(new Rectangle(28, 228, 270, 19));
        pnlSubscribe.add(contContractAmount, null);
        btnChooseAgio.setBounds(new Rectangle(232, 91, 96, 21));
        pnlSubscribe.add(btnChooseAgio, null);
        contLoanData.setBounds(new Rectangle(28, 58, 270, 19));
        pnlSubscribe.add(contLoanData, null);
        comboDigit.setBounds(new Rectangle(29, 259, 170, 19));
        pnlSubscribe.add(comboDigit, null);
        btnLittleAdjust.setBounds(new Rectangle(210, 260, 86, 19));
        pnlSubscribe.add(btnLittleAdjust, null);
        //contPayType
        contPayType.setBoundEditor(f7PayType);
        //contAgios
        contAgios.setBoundEditor(txtAgio);
        //contAreaComAmount
        contAreaComAmount.setBoundEditor(txtAreaComAmount);
        //contAttachAmount
        contAttachAmount.setBoundEditor(txtAttachAmount);
        //contFitmentAmount
        contFitmentAmount.setBoundEditor(txtFitmentAmount);
        //contContractAmount
        contContractAmount.setBoundEditor(txtContractAmount);
        //contLoanData
        contLoanData.setBoundEditor(f7LoanData);
        //kDTabbedPane1
        kDTabbedPane1.add(tabPayment, resHelper.getString("tabPayment.constraints"));
        //kDTabbedPane2
        kDTabbedPane2.add(kDTable1, resHelper.getString("kDTable1.constraints"));

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
        this.toolBar.add(btnSimuPrint);
        this.toolBar.add(btnSimuPrintPreView);
        this.toolBar.add(btnCalc);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SimulantSellUIHandler";
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
     * output btnSelectRoom_actionPerformed method
     */
    protected void btnSelectRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7PayType_dataChanged method
     */
    protected void f7PayType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
     * output txtAttachAmount_dataChanged method
     */
    protected void txtAttachAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output txtAreaComAmount_actionPerformed method
     */
    protected void txtAreaComAmount_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtAreaComAmount_dataChanged method
     */
    protected void txtAreaComAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnChooseAgio_actionPerformed method
     */
    protected void btnChooseAgio_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7LoanData_dataChanged method
     */
    protected void f7LoanData_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kDTable1_editStopped method
     */
    protected void kDTable1_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnLittleAdjust_actionPerformed method
     */
    protected void btnLittleAdjust_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
     * output actionSimuPrint_actionPerformed method
     */
    public void actionSimuPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSimuPrintPreView_actionPerformed method
     */
    public void actionSimuPrintPreView_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCalc_actionPerformed method
     */
    public void actionCalc_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSimuPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSimuPrint() {
    	return false;
    }
	public RequestContext prepareActionSimuPrintPreView(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSimuPrintPreView() {
    	return false;
    }
	public RequestContext prepareActionCalc(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalc() {
    	return false;
    }

    /**
     * output ActionSimuPrint class
     */     
    protected class ActionSimuPrint extends ItemAction {     
    
        public ActionSimuPrint()
        {
            this(null);
        }

        public ActionSimuPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSimuPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSimuPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSimuPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSimulantSellUI.this, "ActionSimuPrint", "actionSimuPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionSimuPrintPreView class
     */     
    protected class ActionSimuPrintPreView extends ItemAction {     
    
        public ActionSimuPrintPreView()
        {
            this(null);
        }

        public ActionSimuPrintPreView(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSimuPrintPreView.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSimuPrintPreView.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSimuPrintPreView.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSimulantSellUI.this, "ActionSimuPrintPreView", "actionSimuPrintPreView_actionPerformed", e);
        }
    }

    /**
     * output ActionCalc class
     */     
    protected class ActionCalc extends ItemAction {     
    
        public ActionCalc()
        {
            this(null);
        }

        public ActionCalc(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCalc.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalc.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalc.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSimulantSellUI.this, "ActionCalc", "actionCalc_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SimulantSellUI");
    }




}