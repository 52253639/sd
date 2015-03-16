/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractCustomerManagementUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCustomerManagementUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelLeft;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelRight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSysType;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeViewMarketUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSysType;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMarketUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBookedDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBookedDateEnd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCertificateNumber;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkBoxAdapter;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkBoxShared;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkBoxDisEnable;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustCommStatus;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSubmit;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCommerceChance;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isForShe;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isForTen;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isForPpm;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox ckbAll;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkBoxTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker nowDate;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField days;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateBookedDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateBookedDateEnd;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomerName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCertificateNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCustCommStatus;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddCommerceChance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddTrackRecord;
    protected javax.swing.JToolBar.Separator separatorFW3;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClewAdd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLatencyAdd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnIntency;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClueQueryShow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportantTrack;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelImportantTrack;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerAdapter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerShare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerCancelShare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuestionPrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuestionPrint;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAddCustomer;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAddCommerceChance;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAddTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator4;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuClewAdd;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuLatencyAdd;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuIntenancyAdd;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClueQueryShow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportantTrack;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelImportantTrack;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAdapter;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShare;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelShare;
    protected ActionAddCustomer actionAddCustomer = null;
    protected ActionAddCommerceChance actionAddCommerceChance = null;
    protected ActionAddTrackRecord actionAddTrackRecord = null;
    protected ActionAddTotalAll actionAddTotalAll = null;
    protected ActionClueQueryShow actionClueQueryShow = null;
    protected ActionImportantTrack actionImportantTrack = null;
    protected ActionCancelImportantTrack actionCancelImportantTrack = null;
    protected ActionCustomerAdapter actionCustomerAdapter = null;
    protected ActionCustomerShare actionCustomerShare = null;
    protected ActionCustomerCancelShare actionCustomerCancelShare = null;
    protected ActionQuestionPrint actionQuestionPrint = null;
    /**
     * output class constructor
     */
    public AbstractCustomerManagementUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractCustomerManagementUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "CustomerQuery");
        //actionAddCustomer
        this.actionAddCustomer = new ActionAddCustomer(this);
        getActionManager().registerAction("actionAddCustomer", actionAddCustomer);
         this.actionAddCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddCommerceChance
        this.actionAddCommerceChance = new ActionAddCommerceChance(this);
        getActionManager().registerAction("actionAddCommerceChance", actionAddCommerceChance);
         this.actionAddCommerceChance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddTrackRecord
        this.actionAddTrackRecord = new ActionAddTrackRecord(this);
        getActionManager().registerAction("actionAddTrackRecord", actionAddTrackRecord);
         this.actionAddTrackRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddTotalAll
        this.actionAddTotalAll = new ActionAddTotalAll(this);
        getActionManager().registerAction("actionAddTotalAll", actionAddTotalAll);
         this.actionAddTotalAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClueQueryShow
        this.actionClueQueryShow = new ActionClueQueryShow(this);
        getActionManager().registerAction("actionClueQueryShow", actionClueQueryShow);
         this.actionClueQueryShow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportantTrack
        this.actionImportantTrack = new ActionImportantTrack(this);
        getActionManager().registerAction("actionImportantTrack", actionImportantTrack);
         this.actionImportantTrack.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelImportantTrack
        this.actionCancelImportantTrack = new ActionCancelImportantTrack(this);
        getActionManager().registerAction("actionCancelImportantTrack", actionCancelImportantTrack);
         this.actionCancelImportantTrack.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCustomerAdapter
        this.actionCustomerAdapter = new ActionCustomerAdapter(this);
        getActionManager().registerAction("actionCustomerAdapter", actionCustomerAdapter);
         this.actionCustomerAdapter.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCustomerShare
        this.actionCustomerShare = new ActionCustomerShare(this);
        getActionManager().registerAction("actionCustomerShare", actionCustomerShare);
         this.actionCustomerShare.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCustomerCancelShare
        this.actionCustomerCancelShare = new ActionCustomerCancelShare(this);
        getActionManager().registerAction("actionCustomerCancelShare", actionCustomerCancelShare);
         this.actionCustomerCancelShare.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuestionPrint
        this.actionQuestionPrint = new ActionQuestionPrint(this);
        getActionManager().registerAction("actionQuestionPrint", actionQuestionPrint);
         this.actionQuestionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDPanelLeft = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanelRight = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSysType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTreeViewMarketUnit = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboSysType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.treeMarketUnit = new com.kingdee.bos.ctrl.swing.KDTree();
        this.contCustomerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBookedDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBookedDateEnd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomerName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCertificateNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.checkBoxAdapter = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.checkBoxShared = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.checkBoxDisEnable = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contCustCommStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblCommerceChance = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblTrackRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.isForShe = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isForTen = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isForPpm = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.ckbAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.checkBoxTime = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.nowDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.days = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboCustomerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.dateBookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dateBookedDateEnd = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtCustomerName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCertificateNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboCustCommStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnAddCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddCommerceChance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddTrackRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separatorFW3 = new javax.swing.JToolBar.Separator();
        this.btnClewAdd = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnLatencyAdd = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnIntency = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClueQueryShow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportantTrack = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancelImportantTrack = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerAdapter = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerCancelShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnQuestionPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemQuestionPrint = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuAddCustomer = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuAddCommerceChance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuAddTrackRecord = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator4 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuClewAdd = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuLatencyAdd = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuIntenancyAdd = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemClueQueryShow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImportantTrack = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCancelImportantTrack = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAdapter = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemShare = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCancelShare = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDPanelLeft.setName("kDPanelLeft");
        this.kDPanelRight.setName("kDPanelRight");
        this.contSellProject.setName("contSellProject");
        this.contSysType.setName("contSysType");
        this.kDTreeViewMarketUnit.setName("kDTreeViewMarketUnit");
        this.prmtSellProject.setName("prmtSellProject");
        this.comboSysType.setName("comboSysType");
        this.treeMarketUnit.setName("treeMarketUnit");
        this.contCustomerType.setName("contCustomerType");
        this.contBookedDate.setName("contBookedDate");
        this.contBookedDateEnd.setName("contBookedDateEnd");
        this.contCustomerName.setName("contCustomerName");
        this.contPhone.setName("contPhone");
        this.contCertificateNumber.setName("contCertificateNumber");
        this.checkBoxAdapter.setName("checkBoxAdapter");
        this.checkBoxShared.setName("checkBoxShared");
        this.checkBoxDisEnable.setName("checkBoxDisEnable");
        this.contCustCommStatus.setName("contCustCommStatus");
        this.btnSubmit.setName("btnSubmit");
        this.tblCommerceChance.setName("tblCommerceChance");
        this.tblTrackRecord.setName("tblTrackRecord");
        this.isForShe.setName("isForShe");
        this.isForTen.setName("isForTen");
        this.isForPpm.setName("isForPpm");
        this.ckbAll.setName("ckbAll");
        this.kDLabel1.setName("kDLabel1");
        this.checkBoxTime.setName("checkBoxTime");
        this.nowDate.setName("nowDate");
        this.kDLabel2.setName("kDLabel2");
        this.days.setName("days");
        this.comboCustomerType.setName("comboCustomerType");
        this.dateBookedDate.setName("dateBookedDate");
        this.dateBookedDateEnd.setName("dateBookedDateEnd");
        this.txtCustomerName.setName("txtCustomerName");
        this.txtPhone.setName("txtPhone");
        this.txtCertificateNumber.setName("txtCertificateNumber");
        this.comboCustCommStatus.setName("comboCustCommStatus");
        this.btnAddCustomer.setName("btnAddCustomer");
        this.btnAddCommerceChance.setName("btnAddCommerceChance");
        this.btnAddTrackRecord.setName("btnAddTrackRecord");
        this.separatorFW3.setName("separatorFW3");
        this.btnClewAdd.setName("btnClewAdd");
        this.btnLatencyAdd.setName("btnLatencyAdd");
        this.btnIntency.setName("btnIntency");
        this.btnClueQueryShow.setName("btnClueQueryShow");
        this.btnImportantTrack.setName("btnImportantTrack");
        this.btnCancelImportantTrack.setName("btnCancelImportantTrack");
        this.btnCustomerAdapter.setName("btnCustomerAdapter");
        this.btnCustomerShare.setName("btnCustomerShare");
        this.btnCustomerCancelShare.setName("btnCustomerCancelShare");
        this.btnQuestionPrint.setName("btnQuestionPrint");
        this.menuItemQuestionPrint.setName("menuItemQuestionPrint");
        this.kDSeparator3.setName("kDSeparator3");
        this.menuAddCustomer.setName("menuAddCustomer");
        this.menuAddCommerceChance.setName("menuAddCommerceChance");
        this.menuAddTrackRecord.setName("menuAddTrackRecord");
        this.kDSeparator4.setName("kDSeparator4");
        this.menuClewAdd.setName("menuClewAdd");
        this.menuLatencyAdd.setName("menuLatencyAdd");
        this.menuIntenancyAdd.setName("menuIntenancyAdd");
        this.menuItemClueQueryShow.setName("menuItemClueQueryShow");
        this.menuItemImportantTrack.setName("menuItemImportantTrack");
        this.menuItemCancelImportantTrack.setName("menuItemCancelImportantTrack");
        this.menuItemAdapter.setName("menuItemAdapter");
        this.menuItemShare.setName("menuItemShare");
        this.menuItemCancelShare.setName("menuItemCancelShare");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"trackPhase\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"saleTrackPhase\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"tenancyTrackPhase\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"isImportantTrack\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"lastTrackDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"email\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"customerOrigin.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"receptionType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"customerGrade.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"workCompany\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"employment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"bookedPlace\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"certificateName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"certificateNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"mailAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"postalcode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"province.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"famillyEarning.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"project.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"customerType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"salesman.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"habitationArea.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"isEnabled\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" /><t:Column t:key=\"tradeTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" /><t:Column t:key=\"tenTradeTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{sex}</t:Cell><t:Cell>$Resource{trackPhase}</t:Cell><t:Cell>$Resource{saleTrackPhase}</t:Cell><t:Cell>$Resource{tenancyTrackPhase}</t:Cell><t:Cell>$Resource{isImportantTrack}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{lastTrackDate}</t:Cell><t:Cell>$Resource{email}</t:Cell><t:Cell>$Resource{customerOrigin.name}</t:Cell><t:Cell>$Resource{receptionType.name}</t:Cell><t:Cell>$Resource{customerGrade.name}</t:Cell><t:Cell>$Resource{workCompany}</t:Cell><t:Cell>$Resource{employment}</t:Cell><t:Cell>$Resource{bookedPlace}</t:Cell><t:Cell>$Resource{certificateName}</t:Cell><t:Cell>$Resource{certificateNumber}</t:Cell><t:Cell>$Resource{mailAddress}</t:Cell><t:Cell>$Resource{postalcode}</t:Cell><t:Cell>$Resource{province.name}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{famillyEarning.name}</t:Cell><t:Cell>$Resource{project.name}</t:Cell><t:Cell>$Resource{customerType}</t:Cell><t:Cell>$Resource{salesman.name}</t:Cell><t:Cell>$Resource{habitationArea.name}</t:Cell><t:Cell>$Resource{isEnabled}</t:Cell><t:Cell>$Resource{tradeTime}</t:Cell><t:Cell>$Resource{tenTradeTime}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblMain_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"number","name","sex","trackPhase","saleTrackPhase","tenancyTrackPhase","isImportantTrack","phone","lastTrackDate","email","customerOrigin.name","receptionType.name","customerGrade.name","workCompany","employment","bookedPlace","certificateName","certificateNumber","mailAddress","postalcode","province.name","id","famillyEarning.name","project.name","customerType","salesman.name","habitationArea.name","isEnabled","tradeTime","tenTradeTime","description","createTime","creator.name"});


        // kDPanelLeft		
        this.kDPanelLeft.setBorder(null);
        // kDPanelRight		
        this.kDPanelRight.setBorder(null);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(80);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contSysType		
        this.contSysType.setBoundLabelText(resHelper.getString("contSysType.boundLabelText"));		
        this.contSysType.setBoundLabelLength(80);		
        this.contSysType.setBoundLabelUnderline(true);
        // kDTreeViewMarketUnit
        // prmtSellProject		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtSellProject.setDisplayFormat("$name$");		
        this.prmtSellProject.setEditFormat("$number$");		
        this.prmtSellProject.setCommitFormat("$number$");
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
        // comboSysType		
        this.comboSysType.setActionCommand("comboBoxSysType");		
        this.comboSysType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.MoneySysTypeEnum").toArray());
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
        // treeMarketUnit
        // contCustomerType		
        this.contCustomerType.setBoundLabelText(resHelper.getString("contCustomerType.boundLabelText"));		
        this.contCustomerType.setBoundLabelLength(80);		
        this.contCustomerType.setBoundLabelUnderline(true);
        // contBookedDate		
        this.contBookedDate.setBoundLabelText(resHelper.getString("contBookedDate.boundLabelText"));		
        this.contBookedDate.setBoundLabelLength(80);		
        this.contBookedDate.setBoundLabelUnderline(true);
        // contBookedDateEnd		
        this.contBookedDateEnd.setBoundLabelText(resHelper.getString("contBookedDateEnd.boundLabelText"));		
        this.contBookedDateEnd.setBoundLabelLength(80);		
        this.contBookedDateEnd.setBoundLabelUnderline(true);
        // contCustomerName		
        this.contCustomerName.setBoundLabelText(resHelper.getString("contCustomerName.boundLabelText"));		
        this.contCustomerName.setBoundLabelLength(80);		
        this.contCustomerName.setBoundLabelUnderline(true);
        // contPhone		
        this.contPhone.setBoundLabelText(resHelper.getString("contPhone.boundLabelText"));		
        this.contPhone.setBoundLabelLength(80);		
        this.contPhone.setBoundLabelUnderline(true);
        // contCertificateNumber		
        this.contCertificateNumber.setBoundLabelText(resHelper.getString("contCertificateNumber.boundLabelText"));		
        this.contCertificateNumber.setBoundLabelLength(80);		
        this.contCertificateNumber.setBoundLabelUnderline(true);
        // checkBoxAdapter		
        this.checkBoxAdapter.setText(resHelper.getString("checkBoxAdapter.text"));		
        this.checkBoxAdapter.setSelected(true);
        this.checkBoxAdapter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    checkBoxAdapter_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // checkBoxShared		
        this.checkBoxShared.setText(resHelper.getString("checkBoxShared.text"));		
        this.checkBoxShared.setSelected(true);
        this.checkBoxShared.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    checkBoxShared_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // checkBoxDisEnable		
        this.checkBoxDisEnable.setText(resHelper.getString("checkBoxDisEnable.text"));
        // contCustCommStatus		
        this.contCustCommStatus.setBoundLabelText(resHelper.getString("contCustCommStatus.boundLabelText"));		
        this.contCustCommStatus.setBoundLabelLength(100);		
        this.contCustCommStatus.setBoundLabelUnderline(true);
        // btnSubmit		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));
        this.btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSubmit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblCommerceChance
		String tblCommerceChanceStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"sysType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sellProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saleMan.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceLevel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fdcCustomer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{sysType}</t:Cell><t:Cell>$Resource{sellProject.name}</t:Cell><t:Cell>$Resource{saleMan.name}</t:Cell><t:Cell>$Resource{commerceLevel.name}</t:Cell><t:Cell>$Resource{commerceStatus}</t:Cell><t:Cell>$Resource{fdcCustomer.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblCommerceChance.setFormatXml(resHelper.translateString("tblCommerceChance",tblCommerceChanceStrXML));
        this.tblCommerceChance.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblCommerceChance_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // tblTrackRecord
		String tblTrackRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"eventDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"head.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"trackResult\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"eventType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"receptionType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceChance.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{eventDate}</t:Cell><t:Cell>$Resource{head.name}</t:Cell><t:Cell>$Resource{trackResult}</t:Cell><t:Cell>$Resource{eventType.name}</t:Cell><t:Cell>$Resource{receptionType.name}</t:Cell><t:Cell>$Resource{commerceChance.name}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
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

        

        // isForShe		
        this.isForShe.setText(resHelper.getString("isForShe.text"));		
        this.isForShe.setSelected(true);
        // isForTen		
        this.isForTen.setText(resHelper.getString("isForTen.text"));
        // isForPpm		
        this.isForPpm.setText(resHelper.getString("isForPpm.text"));
        // ckbAll		
        this.ckbAll.setText(resHelper.getString("ckbAll.text"));
        this.ckbAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    ckbAll_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // checkBoxTime		
        this.checkBoxTime.setText(resHelper.getString("checkBoxTime.text"));		
        this.checkBoxTime.setSelected(true);
        this.checkBoxTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    checkBoxTime_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // nowDate
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // days
        // comboCustomerType		
        this.comboCustomerType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum").toArray());
        // dateBookedDate
        // dateBookedDateEnd
        // txtCustomerName		
        this.txtCustomerName.setMaxLength(40);
        // txtPhone		
        this.txtPhone.setMaxLength(40);
        // txtCertificateNumber		
        this.txtCertificateNumber.setMaxLength(40);
        // comboCustCommStatus		
        this.comboCustCommStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum").toArray());
        // btnAddCustomer
        this.btnAddCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddCustomer.setText(resHelper.getString("btnAddCustomer.text"));		
        this.btnAddCustomer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
        // btnAddCommerceChance
        this.btnAddCommerceChance.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCommerceChance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddCommerceChance.setText(resHelper.getString("btnAddCommerceChance.text"));		
        this.btnAddCommerceChance.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
        // btnAddTrackRecord
        this.btnAddTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddTrackRecord.setText(resHelper.getString("btnAddTrackRecord.text"));		
        this.btnAddTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
        // separatorFW3		
        this.separatorFW3.setOrientation(1);
        // btnClewAdd
        this.btnClewAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTotalAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClewAdd.setText(resHelper.getString("btnClewAdd.text"));		
        this.btnClewAdd.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));		
        this.btnClewAdd.setActionCommand("Clew");
        // btnLatencyAdd
        this.btnLatencyAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTotalAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLatencyAdd.setText(resHelper.getString("btnLatencyAdd.text"));		
        this.btnLatencyAdd.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));		
        this.btnLatencyAdd.setActionCommand("Latency");
        // btnIntency
        this.btnIntency.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTotalAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnIntency.setText(resHelper.getString("btnIntency.text"));		
        this.btnIntency.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));		
        this.btnIntency.setActionCommand("Intency");
        // btnClueQueryShow
        this.btnClueQueryShow.setAction((IItemAction)ActionProxyFactory.getProxy(actionClueQueryShow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClueQueryShow.setText(resHelper.getString("btnClueQueryShow.text"));		
        this.btnClueQueryShow.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_distributeuser"));
        // btnImportantTrack
        this.btnImportantTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportantTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportantTrack.setText(resHelper.getString("btnImportantTrack.text"));		
        this.btnImportantTrack.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_selectcompany"));
        // btnCancelImportantTrack
        this.btnCancelImportantTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelImportantTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelImportantTrack.setText(resHelper.getString("btnCancelImportantTrack.text"));		
        this.btnCancelImportantTrack.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_merge"));
        // btnCustomerAdapter
        this.btnCustomerAdapter.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerAdapter, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCustomerAdapter.setText(resHelper.getString("btnCustomerAdapter.text"));		
        this.btnCustomerAdapter.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_citecompany"));
        // btnCustomerShare
        this.btnCustomerShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCustomerShare.setText(resHelper.getString("btnCustomerShare.text"));		
        this.btnCustomerShare.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_sealup"));
        // btnCustomerCancelShare
        this.btnCustomerCancelShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerCancelShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCustomerCancelShare.setText(resHelper.getString("btnCustomerCancelShare.text"));		
        this.btnCustomerCancelShare.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deletesealup"));
        // btnQuestionPrint
        this.btnQuestionPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuestionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuestionPrint.setText(resHelper.getString("btnQuestionPrint.text"));		
        this.btnQuestionPrint.setToolTipText(resHelper.getString("btnQuestionPrint.toolTipText"));		
        this.btnQuestionPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcredence"));
        // menuItemQuestionPrint
        this.menuItemQuestionPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuestionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuestionPrint.setText(resHelper.getString("menuItemQuestionPrint.text"));		
        this.menuItemQuestionPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcredence"));		
        this.menuItemQuestionPrint.setToolTipText(resHelper.getString("menuItemQuestionPrint.toolTipText"));
        // kDSeparator3
        // menuAddCustomer
        this.menuAddCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuAddCustomer.setText(resHelper.getString("menuAddCustomer.text"));		
        this.menuAddCustomer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
        // menuAddCommerceChance
        this.menuAddCommerceChance.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCommerceChance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuAddCommerceChance.setText(resHelper.getString("menuAddCommerceChance.text"));		
        this.menuAddCommerceChance.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
        // menuAddTrackRecord
        this.menuAddTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuAddTrackRecord.setText(resHelper.getString("menuAddTrackRecord.text"));		
        this.menuAddTrackRecord.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
        // kDSeparator4
        // menuClewAdd
        this.menuClewAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTotalAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuClewAdd.setText(resHelper.getString("menuClewAdd.text"));		
        this.menuClewAdd.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));		
        this.menuClewAdd.setActionCommand("Clew");
        // menuLatencyAdd
        this.menuLatencyAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTotalAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuLatencyAdd.setText(resHelper.getString("menuLatencyAdd.text"));		
        this.menuLatencyAdd.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));		
        this.menuLatencyAdd.setActionCommand("Latency");
        // menuIntenancyAdd
        this.menuIntenancyAdd.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTotalAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuIntenancyAdd.setText(resHelper.getString("menuIntenancyAdd.text"));		
        this.menuIntenancyAdd.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));		
        this.menuIntenancyAdd.setActionCommand("Intency");
        // menuItemClueQueryShow
        this.menuItemClueQueryShow.setAction((IItemAction)ActionProxyFactory.getProxy(actionClueQueryShow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClueQueryShow.setText(resHelper.getString("menuItemClueQueryShow.text"));		
        this.menuItemClueQueryShow.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_distributeuser"));
        // menuItemImportantTrack
        this.menuItemImportantTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportantTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportantTrack.setText(resHelper.getString("menuItemImportantTrack.text"));		
        this.menuItemImportantTrack.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_selectcompany"));
        // menuItemCancelImportantTrack
        this.menuItemCancelImportantTrack.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelImportantTrack, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelImportantTrack.setText(resHelper.getString("menuItemCancelImportantTrack.text"));		
        this.menuItemCancelImportantTrack.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_merge"));
        // menuItemAdapter
        this.menuItemAdapter.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerAdapter, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAdapter.setText(resHelper.getString("menuItemAdapter.text"));		
        this.menuItemAdapter.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_citecompany"));
        // menuItemShare
        this.menuItemShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemShare.setText(resHelper.getString("menuItemShare.text"));		
        this.menuItemShare.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_sealup"));
        // menuItemCancelShare
        this.menuItemCancelShare.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerCancelShare, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelShare.setText(resHelper.getString("menuItemCancelShare.text"));		
        this.menuItemCancelShare.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deletesealup"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        kDPanelLeft.setBounds(new Rectangle(7, 5, 218, 618));
        this.add(kDPanelLeft, new KDLayout.Constraints(7, 5, 218, 618, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanelRight.setBounds(new Rectangle(229, 4, 780, 619));
        this.add(kDPanelRight, new KDLayout.Constraints(229, 4, 780, 619, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanelLeft
        kDPanelLeft.setLayout(new KDLayout());
        kDPanelLeft.putClientProperty("OriginalBounds", new Rectangle(7, 5, 218, 618));        contSellProject.setBounds(new Rectangle(12, 445, 201, 19));
        kDPanelLeft.add(contSellProject, new KDLayout.Constraints(12, 445, 201, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contSysType.setBounds(new Rectangle(12, 472, 201, 19));
        kDPanelLeft.add(contSysType, new KDLayout.Constraints(12, 472, 201, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTreeViewMarketUnit.setBounds(new Rectangle(5, 3, 212, 418));
        kDPanelLeft.add(kDTreeViewMarketUnit, new KDLayout.Constraints(5, 3, 212, 418, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contSysType
        contSysType.setBoundEditor(comboSysType);
        //kDTreeViewMarketUnit
        kDTreeViewMarketUnit.setTree(treeMarketUnit);
        //kDPanelRight
        kDPanelRight.setLayout(new KDLayout());
        kDPanelRight.putClientProperty("OriginalBounds", new Rectangle(229, 4, 780, 619));        tblMain.setBounds(new Rectangle(3, 141, 772, 268));
        kDPanelRight.add(tblMain, new KDLayout.Constraints(3, 141, 772, 268, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCustomerType.setBounds(new Rectangle(5, 5, 220, 19));
        kDPanelRight.add(contCustomerType, new KDLayout.Constraints(5, 5, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBookedDate.setBounds(new Rectangle(257, 5, 220, 19));
        kDPanelRight.add(contBookedDate, new KDLayout.Constraints(257, 5, 220, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contBookedDateEnd.setBounds(new Rectangle(505, 5, 220, 19));
        kDPanelRight.add(contBookedDateEnd, new KDLayout.Constraints(505, 5, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCustomerName.setBounds(new Rectangle(5, 28, 220, 19));
        kDPanelRight.add(contCustomerName, new KDLayout.Constraints(5, 28, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPhone.setBounds(new Rectangle(257, 28, 220, 19));
        kDPanelRight.add(contPhone, new KDLayout.Constraints(257, 28, 220, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contCertificateNumber.setBounds(new Rectangle(505, 28, 220, 19));
        kDPanelRight.add(contCertificateNumber, new KDLayout.Constraints(505, 28, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        checkBoxAdapter.setBounds(new Rectangle(5, 75, 140, 19));
        kDPanelRight.add(checkBoxAdapter, new KDLayout.Constraints(5, 75, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        checkBoxShared.setBounds(new Rectangle(257, 75, 140, 19));
        kDPanelRight.add(checkBoxShared, new KDLayout.Constraints(257, 75, 140, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        checkBoxDisEnable.setBounds(new Rectangle(505, 75, 140, 19));
        kDPanelRight.add(checkBoxDisEnable, new KDLayout.Constraints(505, 75, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCustCommStatus.setBounds(new Rectangle(5, 114, 220, 19));
        kDPanelRight.add(contCustCommStatus, new KDLayout.Constraints(5, 114, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnSubmit.setBounds(new Rectangle(258, 114, 95, 19));
        kDPanelRight.add(btnSubmit, new KDLayout.Constraints(258, 114, 95, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        tblCommerceChance.setBounds(new Rectangle(3, 412, 393, 199));
        kDPanelRight.add(tblCommerceChance, new KDLayout.Constraints(3, 412, 393, 199, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tblTrackRecord.setBounds(new Rectangle(400, 412, 376, 199));
        kDPanelRight.add(tblTrackRecord, new KDLayout.Constraints(400, 412, 376, 199, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        isForShe.setBounds(new Rectangle(5, 96, 140, 19));
        kDPanelRight.add(isForShe, new KDLayout.Constraints(5, 96, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        isForTen.setBounds(new Rectangle(257, 96, 140, 19));
        kDPanelRight.add(isForTen, new KDLayout.Constraints(257, 96, 140, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        isForPpm.setBounds(new Rectangle(505, 96, 140, 19));
        kDPanelRight.add(isForPpm, new KDLayout.Constraints(505, 96, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        ckbAll.setBounds(new Rectangle(505, 114, 140, 19));
        kDPanelRight.add(ckbAll, new KDLayout.Constraints(505, 114, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel1.setBounds(new Rectangle(214, 52, 53, 19));
        kDPanelRight.add(kDLabel1, new KDLayout.Constraints(214, 52, 53, 19, 0));
        checkBoxTime.setBounds(new Rectangle(5, 53, 52, 19));
        kDPanelRight.add(checkBoxTime, new KDLayout.Constraints(5, 53, 52, 19, 0));
        nowDate.setBounds(new Rectangle(60, 52, 151, 19));
        kDPanelRight.add(nowDate, new KDLayout.Constraints(60, 52, 151, 19, 0));
        kDLabel2.setBounds(new Rectangle(308, 52, 89, 19));
        kDPanelRight.add(kDLabel2, new KDLayout.Constraints(308, 52, 89, 19, 0));
        days.setBounds(new Rectangle(270, 52, 34, 19));
        kDPanelRight.add(days, new KDLayout.Constraints(270, 52, 34, 19, 0));
        //contCustomerType
        contCustomerType.setBoundEditor(comboCustomerType);
        //contBookedDate
        contBookedDate.setBoundEditor(dateBookedDate);
        //contBookedDateEnd
        contBookedDateEnd.setBoundEditor(dateBookedDateEnd);
        //contCustomerName
        contCustomerName.setBoundEditor(txtCustomerName);
        //contPhone
        contPhone.setBoundEditor(txtPhone);
        //contCertificateNumber
        contCertificateNumber.setBoundEditor(txtCertificateNumber);
        //contCustCommStatus
        contCustCommStatus.setBoundEditor(comboCustCommStatus);

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
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemQuestionPrint);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuAddCustomer);
        menuEdit.add(menuAddCommerceChance);
        menuEdit.add(menuAddTrackRecord);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuClewAdd);
        menuEdit.add(menuLatencyAdd);
        menuEdit.add(menuIntenancyAdd);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemQueryScheme);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemClueQueryShow);
        menuBiz.add(menuItemImportantTrack);
        menuBiz.add(menuItemCancelImportantTrack);
        menuBiz.add(menuItemAdapter);
        menuBiz.add(menuItemShare);
        menuBiz.add(menuItemCancelShare);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
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
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnAddCustomer);
        this.toolBar.add(btnAddCommerceChance);
        this.toolBar.add(btnAddTrackRecord);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnClewAdd);
        this.toolBar.add(btnLatencyAdd);
        this.toolBar.add(btnIntency);
        this.toolBar.add(btnClueQueryShow);
        this.toolBar.add(btnImportantTrack);
        this.toolBar.add(btnCancelImportantTrack);
        this.toolBar.add(btnCustomerAdapter);
        this.toolBar.add(btnCustomerShare);
        this.toolBar.add(btnCustomerCancelShare);
        this.toolBar.add(btnQuestionPrint);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.CustomerManagementUIHandler";
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
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output prmtSellProject_dataChanged method
     */
    protected void prmtSellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboSysType_itemStateChanged method
     */
    protected void comboSysType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output checkBoxAdapter_itemStateChanged method
     */
    protected void checkBoxAdapter_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output checkBoxShared_itemStateChanged method
     */
    protected void checkBoxShared_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output btnSubmit_actionPerformed method
     */
    protected void btnSubmit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblCommerceChance_tableClicked method
     */
    protected void tblCommerceChance_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblTrackRecord_tableClicked method
     */
    protected void tblTrackRecord_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output ckbAll_actionPerformed method
     */
    protected void ckbAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output checkBoxTime_actionPerformed method
     */
    protected void checkBoxTime_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("sex"));
        sic.add(new SelectorItemInfo("trackPhase"));
        sic.add(new SelectorItemInfo("isImportantTrack"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("lastTrackDate"));
        sic.add(new SelectorItemInfo("email"));
        sic.add(new SelectorItemInfo("customerOrigin.name"));
        sic.add(new SelectorItemInfo("receptionType.name"));
        sic.add(new SelectorItemInfo("customerGrade.name"));
        sic.add(new SelectorItemInfo("workCompany"));
        sic.add(new SelectorItemInfo("employment"));
        sic.add(new SelectorItemInfo("bookedPlace"));
        sic.add(new SelectorItemInfo("certificateName"));
        sic.add(new SelectorItemInfo("certificateNumber"));
        sic.add(new SelectorItemInfo("mailAddress"));
        sic.add(new SelectorItemInfo("postalcode"));
        sic.add(new SelectorItemInfo("province.name"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("famillyEarning.name"));
        sic.add(new SelectorItemInfo("project.name"));
        sic.add(new SelectorItemInfo("customerType"));
        sic.add(new SelectorItemInfo("salesman.name"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("tradeTime"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("habitationArea.name"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("saleTrackPhase"));
        sic.add(new SelectorItemInfo("tenancyTrackPhase"));
        sic.add(new SelectorItemInfo("tenTradeTime"));
        return sic;
    }        
    	

    /**
     * output actionAddCustomer_actionPerformed method
     */
    public void actionAddCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddCommerceChance_actionPerformed method
     */
    public void actionAddCommerceChance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddTrackRecord_actionPerformed method
     */
    public void actionAddTrackRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddTotalAll_actionPerformed method
     */
    public void actionAddTotalAll_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClueQueryShow_actionPerformed method
     */
    public void actionClueQueryShow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportantTrack_actionPerformed method
     */
    public void actionImportantTrack_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelImportantTrack_actionPerformed method
     */
    public void actionCancelImportantTrack_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCustomerAdapter_actionPerformed method
     */
    public void actionCustomerAdapter_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCustomerShare_actionPerformed method
     */
    public void actionCustomerShare_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCustomerCancelShare_actionPerformed method
     */
    public void actionCustomerCancelShare_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuestionPrint_actionPerformed method
     */
    public void actionQuestionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionAddCustomer class
     */     
    protected class ActionAddCustomer extends ItemAction {     
    
        public ActionAddCustomer()
        {
            this(null);
        }

        public ActionAddCustomer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerManagementUI.this, "ActionAddCustomer", "actionAddCustomer_actionPerformed", e);
        }
    }

    /**
     * output ActionAddCommerceChance class
     */     
    protected class ActionAddCommerceChance extends ItemAction {     
    
        public ActionAddCommerceChance()
        {
            this(null);
        }

        public ActionAddCommerceChance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddCommerceChance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCommerceChance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCommerceChance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerManagementUI.this, "ActionAddCommerceChance", "actionAddCommerceChance_actionPerformed", e);
        }
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
            innerActionPerformed("eas", AbstractCustomerManagementUI.this, "ActionAddTrackRecord", "actionAddTrackRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionAddTotalAll class
     */     
    protected class ActionAddTotalAll extends ItemAction {     
    
        public ActionAddTotalAll()
        {
            this(null);
        }

        public ActionAddTotalAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddTotalAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTotalAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTotalAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerManagementUI.this, "ActionAddTotalAll", "actionAddTotalAll_actionPerformed", e);
        }
    }

    /**
     * output ActionClueQueryShow class
     */     
    protected class ActionClueQueryShow extends ItemAction {     
    
        public ActionClueQueryShow()
        {
            this(null);
        }

        public ActionClueQueryShow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionClueQueryShow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClueQueryShow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClueQueryShow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerManagementUI.this, "ActionClueQueryShow", "actionClueQueryShow_actionPerformed", e);
        }
    }

    /**
     * output ActionImportantTrack class
     */     
    protected class ActionImportantTrack extends ItemAction {     
    
        public ActionImportantTrack()
        {
            this(null);
        }

        public ActionImportantTrack(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportantTrack.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportantTrack.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportantTrack.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerManagementUI.this, "ActionImportantTrack", "actionImportantTrack_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelImportantTrack class
     */     
    protected class ActionCancelImportantTrack extends ItemAction {     
    
        public ActionCancelImportantTrack()
        {
            this(null);
        }

        public ActionCancelImportantTrack(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCancelImportantTrack.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelImportantTrack.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelImportantTrack.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerManagementUI.this, "ActionCancelImportantTrack", "actionCancelImportantTrack_actionPerformed", e);
        }
    }

    /**
     * output ActionCustomerAdapter class
     */     
    protected class ActionCustomerAdapter extends ItemAction {     
    
        public ActionCustomerAdapter()
        {
            this(null);
        }

        public ActionCustomerAdapter(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCustomerAdapter.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerAdapter.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerAdapter.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerManagementUI.this, "ActionCustomerAdapter", "actionCustomerAdapter_actionPerformed", e);
        }
    }

    /**
     * output ActionCustomerShare class
     */     
    protected class ActionCustomerShare extends ItemAction {     
    
        public ActionCustomerShare()
        {
            this(null);
        }

        public ActionCustomerShare(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCustomerShare.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerShare.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerShare.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerManagementUI.this, "ActionCustomerShare", "actionCustomerShare_actionPerformed", e);
        }
    }

    /**
     * output ActionCustomerCancelShare class
     */     
    protected class ActionCustomerCancelShare extends ItemAction {     
    
        public ActionCustomerCancelShare()
        {
            this(null);
        }

        public ActionCustomerCancelShare(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCustomerCancelShare.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerCancelShare.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCustomerCancelShare.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerManagementUI.this, "ActionCustomerCancelShare", "actionCustomerCancelShare_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractCustomerManagementUI.this, "ActionQuestionPrint", "actionQuestionPrint_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "CustomerManagementUI");
    }




}