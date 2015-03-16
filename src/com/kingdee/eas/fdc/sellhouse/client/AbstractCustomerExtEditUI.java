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
public abstract class AbstractCustomerExtEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCustomerExtEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabNew;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelBaseInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelCustomer;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelSale;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelTenancy;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelManager;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelCusService;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCustomerNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcSalesman;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcProvince;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCustomerOrigin;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcEmail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcBookedDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCertificateName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCertificateNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcMailAddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcWorkCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcReceptionType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCustomerGrade;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcEmployment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcSex;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcHabitationArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcBooker;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcTradeTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcFamillyEarning;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcPostalcode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcTrackPhase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcBookedPlace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcLastTrackDate;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterpriceProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterpriceSize;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIndustry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterpriceHomepage;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsForSHE;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsForTen;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsForPPM;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCountry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcFax;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcQQ;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcOfficeTel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCustomerManager;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCooperateMode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcBusinessScope;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcPhone2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcRoomModelType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcIntentionArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcAge;
    protected com.kingdee.bos.ctrl.swing.KDComboBox boxCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7salesman;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Province;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7CustomerOrigin;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEmail;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpBookedDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCertificateNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMailAddress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWorkCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ReceptionType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7CustomerGrade;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEmployment;
    protected com.kingdee.bos.ctrl.swing.KDComboBox boxSex;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7HabitationArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBooker;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinTradeTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7FamillyEarning;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPostalcode;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Project;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtareaDescription;
    protected com.kingdee.bos.ctrl.swing.KDComboBox boxTrackPhase;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBookedPlace;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpLastTrackDate;
    protected com.kingdee.bos.ctrl.swing.KDContainer containLinkman;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTrackRecord;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblShareSeller;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAdapterLog;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblLinkman;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox enterpriceProperty;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEnterpriceSize;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Industry;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEnterpriceHomepage;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTel;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinTenTradeTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7WorkArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Country;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFax;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtQQ;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOfficeTel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7CustomerManager;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7CooperateMode;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BusinessScope;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModelType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtIntentionArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAge;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblInsider;
    protected com.kingdee.bos.ctrl.swing.KDContainer containSaleRecord;
    protected com.kingdee.bos.ctrl.swing.KDContainer containSaleCommerce;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSaleRecord;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSaleCommerce;
    protected com.kingdee.bos.ctrl.swing.KDContainer containTenRecord;
    protected com.kingdee.bos.ctrl.swing.KDContainer containTenCommerce;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTenancyRecord;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTenancyCommerce;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable houseProperty;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable arrearsRecord;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable advanceReceipt;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer4;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer5;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer6;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable specialServer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable serverRecord;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable complaintRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddCommerceChance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddLine;
    protected javax.swing.JToolBar.Separator separatorFW4;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInsider;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerAdapter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerShare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerCancelShare;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAdapter;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShare;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelShare;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAddCommerceChance;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox boxCertificateName;
    protected com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo editData = null;
    protected ActionAddLinkman actionAddLinkman = null;
    protected ActionAddLine actionAddLine = null;
    protected ActionCustomerAdapter actionCustomerAdapter = null;
    protected ActionCustomerShare actionCustomerShare = null;
    protected ActionCustomerCancelShare actionCustomerCancelShare = null;
    protected ActionAddCommerceChance actionAddCommerceChance = null;
    protected ActionRemoveLinkman actionRemoveLinkman = null;
    protected ActionInsider actionInsider = null;
    /**
     * output class constructor
     */
    public AbstractCustomerExtEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCustomerExtEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddLinkman
        this.actionAddLinkman = new ActionAddLinkman(this);
        getActionManager().registerAction("actionAddLinkman", actionAddLinkman);
         this.actionAddLinkman.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddLine
        this.actionAddLine = new ActionAddLine(this);
        getActionManager().registerAction("actionAddLine", actionAddLine);
         this.actionAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
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
        //actionAddCommerceChance
        this.actionAddCommerceChance = new ActionAddCommerceChance(this);
        getActionManager().registerAction("actionAddCommerceChance", actionAddCommerceChance);
         this.actionAddCommerceChance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveLinkman
        this.actionRemoveLinkman = new ActionRemoveLinkman(this);
        getActionManager().registerAction("actionRemoveLinkman", actionRemoveLinkman);
         this.actionRemoveLinkman.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInsider
        this.actionInsider = new ActionInsider(this);
        getActionManager().registerAction("actionInsider", actionInsider);
         this.actionInsider.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tabNew = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.panelBaseInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelCustomer = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelSale = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelTenancy = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelManager = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelCusService = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.lcCustomerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcCustomerNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcSalesman = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcProvince = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcCustomerOrigin = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcEmail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcBookedDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcCertificateName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcCertificateNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcMailAddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcWorkCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcReceptionType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcCustomerGrade = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcEmployment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcSex = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcHabitationArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcBooker = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.lcTradeTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcFamillyEarning = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcPostalcode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcTrackPhase = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcBookedPlace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcLastTrackDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contEnterpriceProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnterpriceSize = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIndustry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnterpriceHomepage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsForSHE = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsForTen = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsForPPM = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcCountry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcFax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcQQ = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcOfficeTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcCustomerManager = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcCooperateMode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcBusinessScope = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcPhone2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcRoomModelType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcIntentionArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcAge = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.boxCustomerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7salesman = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Province = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7CustomerOrigin = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEmail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.dpBookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtCertificateNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtMailAddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtWorkCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7ReceptionType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7CustomerGrade = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtEmployment = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.boxSex = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7HabitationArea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBooker = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.spinTradeTime = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.f7FamillyEarning = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPostalcode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Project = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtareaDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.boxTrackPhase = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtBookedPlace = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.dpLastTrackDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.containLinkman = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblTrackRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblShareSeller = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblAdapterLog = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblLinkman = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.enterpriceProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtEnterpriceSize = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Industry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtEnterpriceHomepage = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.spinTenTradeTime = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.f7WorkArea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Country = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFax = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtQQ = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOfficeTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7CustomerManager = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7CooperateMode = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7BusinessScope = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPhone2 = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7RoomModelType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtIntentionArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAge = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.tblInsider = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.containSaleRecord = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.containSaleCommerce = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblSaleRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblSaleCommerce = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.containTenRecord = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.containTenCommerce = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblTenancyRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblTenancyCommerce = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.houseProperty = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.arrearsRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.advanceReceipt = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer4 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer5 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer6 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.specialServer = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.serverRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.complaintRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddCommerceChance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separatorFW4 = new javax.swing.JToolBar.Separator();
        this.btnInsider = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerAdapter = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerCancelShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAdapter = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemShare = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCancelShare = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuAddCommerceChance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.boxCertificateName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tabNew.setName("tabNew");
        this.panelBaseInfo.setName("panelBaseInfo");
        this.panelCustomer.setName("panelCustomer");
        this.panelSale.setName("panelSale");
        this.panelTenancy.setName("panelTenancy");
        this.panelManager.setName("panelManager");
        this.panelCusService.setName("panelCusService");
        this.lcCustomerType.setName("lcCustomerType");
        this.lcCustomerNumber.setName("lcCustomerNumber");
        this.lcSalesman.setName("lcSalesman");
        this.lcName.setName("lcName");
        this.lcProvince.setName("lcProvince");
        this.lcCustomerOrigin.setName("lcCustomerOrigin");
        this.lcPhone.setName("lcPhone");
        this.lcEmail.setName("lcEmail");
        this.lcBookedDate.setName("lcBookedDate");
        this.lcCertificateName.setName("lcCertificateName");
        this.lcCertificateNumber.setName("lcCertificateNumber");
        this.lcMailAddress.setName("lcMailAddress");
        this.lcWorkCompany.setName("lcWorkCompany");
        this.lcReceptionType.setName("lcReceptionType");
        this.lcCustomerGrade.setName("lcCustomerGrade");
        this.lcEmployment.setName("lcEmployment");
        this.lcSex.setName("lcSex");
        this.lcHabitationArea.setName("lcHabitationArea");
        this.lcBooker.setName("lcBooker");
        this.kDSeparator5.setName("kDSeparator5");
        this.lcTradeTime.setName("lcTradeTime");
        this.lcFamillyEarning.setName("lcFamillyEarning");
        this.lcPostalcode.setName("lcPostalcode");
        this.lcProject.setName("lcProject");
        this.lcDescription.setName("lcDescription");
        this.lcTrackPhase.setName("lcTrackPhase");
        this.lcBookedPlace.setName("lcBookedPlace");
        this.lcLastTrackDate.setName("lcLastTrackDate");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contEnterpriceProperty.setName("contEnterpriceProperty");
        this.contEnterpriceSize.setName("contEnterpriceSize");
        this.contIndustry.setName("contIndustry");
        this.contEnterpriceHomepage.setName("contEnterpriceHomepage");
        this.chkIsForSHE.setName("chkIsForSHE");
        this.chkIsForTen.setName("chkIsForTen");
        this.chkIsForPPM.setName("chkIsForPPM");
        this.contTel.setName("contTel");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.lcCountry.setName("lcCountry");
        this.lcFax.setName("lcFax");
        this.lcQQ.setName("lcQQ");
        this.lcOfficeTel.setName("lcOfficeTel");
        this.lcCustomerManager.setName("lcCustomerManager");
        this.lcCooperateMode.setName("lcCooperateMode");
        this.lcBusinessScope.setName("lcBusinessScope");
        this.lcPhone2.setName("lcPhone2");
        this.lcRoomModelType.setName("lcRoomModelType");
        this.lcIntentionArea.setName("lcIntentionArea");
        this.lcAge.setName("lcAge");
        this.boxCustomerType.setName("boxCustomerType");
        this.txtNumber.setName("txtNumber");
        this.f7salesman.setName("f7salesman");
        this.txtName.setName("txtName");
        this.f7Province.setName("f7Province");
        this.f7CustomerOrigin.setName("f7CustomerOrigin");
        this.txtPhone.setName("txtPhone");
        this.txtEmail.setName("txtEmail");
        this.dpBookedDate.setName("dpBookedDate");
        this.txtCertificateNumber.setName("txtCertificateNumber");
        this.txtMailAddress.setName("txtMailAddress");
        this.txtWorkCompany.setName("txtWorkCompany");
        this.f7ReceptionType.setName("f7ReceptionType");
        this.f7CustomerGrade.setName("f7CustomerGrade");
        this.txtEmployment.setName("txtEmployment");
        this.boxSex.setName("boxSex");
        this.f7HabitationArea.setName("f7HabitationArea");
        this.txtBooker.setName("txtBooker");
        this.spinTradeTime.setName("spinTradeTime");
        this.f7FamillyEarning.setName("f7FamillyEarning");
        this.txtPostalcode.setName("txtPostalcode");
        this.f7Project.setName("f7Project");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtareaDescription.setName("txtareaDescription");
        this.boxTrackPhase.setName("boxTrackPhase");
        this.txtBookedPlace.setName("txtBookedPlace");
        this.dpLastTrackDate.setName("dpLastTrackDate");
        this.containLinkman.setName("containLinkman");
        this.tblTrackRecord.setName("tblTrackRecord");
        this.tblShareSeller.setName("tblShareSeller");
        this.tblAdapterLog.setName("tblAdapterLog");
        this.tblLinkman.setName("tblLinkman");
        this.enterpriceProperty.setName("enterpriceProperty");
        this.txtEnterpriceSize.setName("txtEnterpriceSize");
        this.f7Industry.setName("f7Industry");
        this.txtEnterpriceHomepage.setName("txtEnterpriceHomepage");
        this.txtTel.setName("txtTel");
        this.spinTenTradeTime.setName("spinTenTradeTime");
        this.f7WorkArea.setName("f7WorkArea");
        this.f7Country.setName("f7Country");
        this.txtFax.setName("txtFax");
        this.txtQQ.setName("txtQQ");
        this.txtOfficeTel.setName("txtOfficeTel");
        this.f7CustomerManager.setName("f7CustomerManager");
        this.f7CooperateMode.setName("f7CooperateMode");
        this.f7BusinessScope.setName("f7BusinessScope");
        this.txtPhone2.setName("txtPhone2");
        this.f7RoomModelType.setName("f7RoomModelType");
        this.txtIntentionArea.setName("txtIntentionArea");
        this.txtAge.setName("txtAge");
        this.tblInsider.setName("tblInsider");
        this.containSaleRecord.setName("containSaleRecord");
        this.containSaleCommerce.setName("containSaleCommerce");
        this.tblSaleRecord.setName("tblSaleRecord");
        this.tblSaleCommerce.setName("tblSaleCommerce");
        this.containTenRecord.setName("containTenRecord");
        this.containTenCommerce.setName("containTenCommerce");
        this.tblTenancyRecord.setName("tblTenancyRecord");
        this.tblTenancyCommerce.setName("tblTenancyCommerce");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.kDContainer3.setName("kDContainer3");
        this.houseProperty.setName("houseProperty");
        this.arrearsRecord.setName("arrearsRecord");
        this.advanceReceipt.setName("advanceReceipt");
        this.kDContainer4.setName("kDContainer4");
        this.kDContainer5.setName("kDContainer5");
        this.kDContainer6.setName("kDContainer6");
        this.specialServer.setName("specialServer");
        this.serverRecord.setName("serverRecord");
        this.complaintRecord.setName("complaintRecord");
        this.btnAddCommerceChance.setName("btnAddCommerceChance");
        this.btnAddLine.setName("btnAddLine");
        this.separatorFW4.setName("separatorFW4");
        this.btnInsider.setName("btnInsider");
        this.btnCustomerAdapter.setName("btnCustomerAdapter");
        this.btnCustomerShare.setName("btnCustomerShare");
        this.btnCustomerCancelShare.setName("btnCustomerCancelShare");
        this.menuItemAdapter.setName("menuItemAdapter");
        this.menuItemShare.setName("menuItemShare");
        this.menuItemCancelShare.setName("menuItemCancelShare");
        this.menuAddCommerceChance.setName("menuAddCommerceChance");
        this.boxCertificateName.setName("boxCertificateName");
        // CoreUI		
        this.setPreferredSize(new Dimension(1016,600));
        // tabNew
        // panelBaseInfo		
        this.panelBaseInfo.setBorder(null);
        // panelCustomer
        // panelSale
        // panelTenancy
        // panelManager
        // panelCusService		
        this.panelCusService.setBorder(null);
        // lcCustomerType		
        this.lcCustomerType.setBoundLabelText(resHelper.getString("lcCustomerType.boundLabelText"));		
        this.lcCustomerType.setBoundLabelLength(100);		
        this.lcCustomerType.setBoundLabelUnderline(true);
        // lcCustomerNumber		
        this.lcCustomerNumber.setBoundLabelText(resHelper.getString("lcCustomerNumber.boundLabelText"));		
        this.lcCustomerNumber.setBoundLabelLength(100);		
        this.lcCustomerNumber.setBoundLabelUnderline(true);
        // lcSalesman		
        this.lcSalesman.setBoundLabelText(resHelper.getString("lcSalesman.boundLabelText"));		
        this.lcSalesman.setBoundLabelUnderline(true);		
        this.lcSalesman.setBoundLabelLength(100);
        // lcName		
        this.lcName.setBoundLabelText(resHelper.getString("lcName.boundLabelText"));		
        this.lcName.setBoundLabelLength(100);		
        this.lcName.setBoundLabelUnderline(true);
        // lcProvince		
        this.lcProvince.setBoundLabelText(resHelper.getString("lcProvince.boundLabelText"));		
        this.lcProvince.setBoundLabelLength(100);		
        this.lcProvince.setBoundLabelUnderline(true);
        // lcCustomerOrigin		
        this.lcCustomerOrigin.setBoundLabelText(resHelper.getString("lcCustomerOrigin.boundLabelText"));		
        this.lcCustomerOrigin.setBoundLabelUnderline(true);		
        this.lcCustomerOrigin.setBoundLabelLength(100);
        // lcPhone		
        this.lcPhone.setBoundLabelText(resHelper.getString("lcPhone.boundLabelText"));		
        this.lcPhone.setBoundLabelLength(100);		
        this.lcPhone.setBoundLabelUnderline(true);
        // lcEmail		
        this.lcEmail.setBoundLabelText(resHelper.getString("lcEmail.boundLabelText"));		
        this.lcEmail.setBoundLabelUnderline(true);		
        this.lcEmail.setBoundLabelLength(100);
        // lcBookedDate		
        this.lcBookedDate.setBoundLabelText(resHelper.getString("lcBookedDate.boundLabelText"));		
        this.lcBookedDate.setBoundLabelUnderline(true);		
        this.lcBookedDate.setBoundLabelLength(100);		
        this.lcBookedDate.setEnabled(false);
        // lcCertificateName		
        this.lcCertificateName.setBoundLabelText(resHelper.getString("lcCertificateName.boundLabelText"));		
        this.lcCertificateName.setBoundLabelLength(100);		
        this.lcCertificateName.setBoundLabelUnderline(true);
        // lcCertificateNumber		
        this.lcCertificateNumber.setBoundLabelText(resHelper.getString("lcCertificateNumber.boundLabelText"));		
        this.lcCertificateNumber.setBoundLabelLength(100);		
        this.lcCertificateNumber.setBoundLabelUnderline(true);
        // lcMailAddress		
        this.lcMailAddress.setBoundLabelText(resHelper.getString("lcMailAddress.boundLabelText"));		
        this.lcMailAddress.setBoundLabelLength(100);		
        this.lcMailAddress.setBoundLabelUnderline(true);
        // lcWorkCompany		
        this.lcWorkCompany.setBoundLabelText(resHelper.getString("lcWorkCompany.boundLabelText"));		
        this.lcWorkCompany.setBoundLabelLength(100);		
        this.lcWorkCompany.setBoundLabelUnderline(true);
        // lcReceptionType		
        this.lcReceptionType.setBoundLabelText(resHelper.getString("lcReceptionType.boundLabelText"));		
        this.lcReceptionType.setBoundLabelUnderline(true);		
        this.lcReceptionType.setBoundLabelLength(100);
        // lcCustomerGrade		
        this.lcCustomerGrade.setBoundLabelText(resHelper.getString("lcCustomerGrade.boundLabelText"));		
        this.lcCustomerGrade.setBoundLabelLength(100);		
        this.lcCustomerGrade.setBoundLabelUnderline(true);
        // lcEmployment		
        this.lcEmployment.setBoundLabelText(resHelper.getString("lcEmployment.boundLabelText"));		
        this.lcEmployment.setBoundLabelUnderline(true);		
        this.lcEmployment.setBoundLabelLength(100);
        // lcSex		
        this.lcSex.setBoundLabelText(resHelper.getString("lcSex.boundLabelText"));		
        this.lcSex.setBoundLabelUnderline(true);		
        this.lcSex.setBoundLabelLength(100);
        // lcHabitationArea		
        this.lcHabitationArea.setBoundLabelText(resHelper.getString("lcHabitationArea.boundLabelText"));		
        this.lcHabitationArea.setBoundLabelUnderline(true);		
        this.lcHabitationArea.setBoundLabelLength(100);
        // lcBooker		
        this.lcBooker.setBoundLabelText(resHelper.getString("lcBooker.boundLabelText"));		
        this.lcBooker.setBoundLabelUnderline(true);		
        this.lcBooker.setBoundLabelLength(100);		
        this.lcBooker.setEnabled(false);
        // kDSeparator5
        // lcTradeTime		
        this.lcTradeTime.setBoundLabelText(resHelper.getString("lcTradeTime.boundLabelText"));		
        this.lcTradeTime.setBoundLabelLength(100);		
        this.lcTradeTime.setBoundLabelUnderline(true);		
        this.lcTradeTime.setEnabled(false);
        // lcFamillyEarning		
        this.lcFamillyEarning.setBoundLabelText(resHelper.getString("lcFamillyEarning.boundLabelText"));		
        this.lcFamillyEarning.setBoundLabelUnderline(true);		
        this.lcFamillyEarning.setBoundLabelLength(100);
        // lcPostalcode		
        this.lcPostalcode.setBoundLabelText(resHelper.getString("lcPostalcode.boundLabelText"));		
        this.lcPostalcode.setBoundLabelUnderline(true);		
        this.lcPostalcode.setBoundLabelLength(100);
        // lcProject		
        this.lcProject.setBoundLabelText(resHelper.getString("lcProject.boundLabelText"));		
        this.lcProject.setBoundLabelLength(100);		
        this.lcProject.setBoundLabelUnderline(true);
        // lcDescription		
        this.lcDescription.setBoundLabelText(resHelper.getString("lcDescription.boundLabelText"));		
        this.lcDescription.setBoundLabelUnderline(true);		
        this.lcDescription.setBoundLabelLength(100);
        // lcTrackPhase		
        this.lcTrackPhase.setBoundLabelText(resHelper.getString("lcTrackPhase.boundLabelText"));		
        this.lcTrackPhase.setBoundLabelLength(100);		
        this.lcTrackPhase.setBoundLabelUnderline(true);		
        this.lcTrackPhase.setVisible(false);
        // lcBookedPlace		
        this.lcBookedPlace.setBoundLabelText(resHelper.getString("lcBookedPlace.boundLabelText"));		
        this.lcBookedPlace.setBoundLabelLength(100);		
        this.lcBookedPlace.setBoundLabelUnderline(true);
        // lcLastTrackDate		
        this.lcLastTrackDate.setBoundLabelText(resHelper.getString("lcLastTrackDate.boundLabelText"));		
        this.lcLastTrackDate.setBoundLabelLength(100);		
        this.lcLastTrackDate.setBoundLabelUnderline(true);
        // kDTabbedPane1
        // contEnterpriceProperty		
        this.contEnterpriceProperty.setBoundLabelText(resHelper.getString("contEnterpriceProperty.boundLabelText"));		
        this.contEnterpriceProperty.setBoundLabelLength(100);		
        this.contEnterpriceProperty.setBoundLabelUnderline(true);
        // contEnterpriceSize		
        this.contEnterpriceSize.setBoundLabelText(resHelper.getString("contEnterpriceSize.boundLabelText"));		
        this.contEnterpriceSize.setBoundLabelLength(100);		
        this.contEnterpriceSize.setBoundLabelUnderline(true);
        // contIndustry		
        this.contIndustry.setBoundLabelText(resHelper.getString("contIndustry.boundLabelText"));		
        this.contIndustry.setBoundLabelLength(100);		
        this.contIndustry.setBoundLabelUnderline(true);
        // contEnterpriceHomepage		
        this.contEnterpriceHomepage.setBoundLabelText(resHelper.getString("contEnterpriceHomepage.boundLabelText"));		
        this.contEnterpriceHomepage.setBoundLabelLength(100);		
        this.contEnterpriceHomepage.setBoundLabelUnderline(true);
        // chkIsForSHE		
        this.chkIsForSHE.setText(resHelper.getString("chkIsForSHE.text"));
        this.chkIsForSHE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsForSHE_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkIsForTen		
        this.chkIsForTen.setText(resHelper.getString("chkIsForTen.text"));
        this.chkIsForTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsForTen_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkIsForPPM		
        this.chkIsForPPM.setText(resHelper.getString("chkIsForPPM.text"));
        this.chkIsForPPM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsForPPM_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contTel		
        this.contTel.setBoundLabelText(resHelper.getString("contTel.boundLabelText"));		
        this.contTel.setBoundLabelLength(100);		
        this.contTel.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(100);
        // lcCountry		
        this.lcCountry.setBoundLabelText(resHelper.getString("lcCountry.boundLabelText"));		
        this.lcCountry.setBoundLabelLength(100);		
        this.lcCountry.setBoundLabelUnderline(true);
        // lcFax		
        this.lcFax.setBoundLabelText(resHelper.getString("lcFax.boundLabelText"));		
        this.lcFax.setBoundLabelLength(100);		
        this.lcFax.setBoundLabelUnderline(true);
        // lcQQ		
        this.lcQQ.setBoundLabelText(resHelper.getString("lcQQ.boundLabelText"));		
        this.lcQQ.setBoundLabelLength(100);		
        this.lcQQ.setBoundLabelUnderline(true);
        // lcOfficeTel		
        this.lcOfficeTel.setBoundLabelText(resHelper.getString("lcOfficeTel.boundLabelText"));		
        this.lcOfficeTel.setBoundLabelLength(100);		
        this.lcOfficeTel.setBoundLabelUnderline(true);
        // lcCustomerManager		
        this.lcCustomerManager.setBoundLabelText(resHelper.getString("lcCustomerManager.boundLabelText"));		
        this.lcCustomerManager.setBoundLabelLength(100);		
        this.lcCustomerManager.setBoundLabelUnderline(true);
        // lcCooperateMode		
        this.lcCooperateMode.setBoundLabelText(resHelper.getString("lcCooperateMode.boundLabelText"));		
        this.lcCooperateMode.setBoundLabelLength(100);		
        this.lcCooperateMode.setBoundLabelUnderline(true);
        // lcBusinessScope		
        this.lcBusinessScope.setBoundLabelText(resHelper.getString("lcBusinessScope.boundLabelText"));		
        this.lcBusinessScope.setBoundLabelLength(100);		
        this.lcBusinessScope.setBoundLabelUnderline(true);
        // lcPhone2		
        this.lcPhone2.setBoundLabelText(resHelper.getString("lcPhone2.boundLabelText"));		
        this.lcPhone2.setBoundLabelLength(100);		
        this.lcPhone2.setBoundLabelUnderline(true);
        // lcRoomModelType		
        this.lcRoomModelType.setBoundLabelText(resHelper.getString("lcRoomModelType.boundLabelText"));		
        this.lcRoomModelType.setBoundLabelLength(100);		
        this.lcRoomModelType.setBoundLabelUnderline(true);
        // lcIntentionArea		
        this.lcIntentionArea.setBoundLabelText(resHelper.getString("lcIntentionArea.boundLabelText"));		
        this.lcIntentionArea.setBoundLabelLength(100);		
        this.lcIntentionArea.setBoundLabelUnderline(true);
        // lcAge		
        this.lcAge.setBoundLabelText(resHelper.getString("lcAge.boundLabelText"));		
        this.lcAge.setBoundLabelLength(100);		
        this.lcAge.setBoundLabelUnderline(true);
        // boxCustomerType		
        this.boxCustomerType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum").toArray());		
        this.boxCustomerType.setSelectedIndex(0);
        this.boxCustomerType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    boxCustomerType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // f7salesman		
        this.f7salesman.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7salesman.setDisplayFormat("$name$");		
        this.f7salesman.setEditFormat("$number$");		
        this.f7salesman.setCommitFormat("$number$");
        this.f7salesman.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7salesman_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(80);
        // f7Province		
        this.f7Province.setQueryInfo("com.kingdee.eas.basedata.assistant.app.ProvinceQuery");		
        this.f7Province.setDisplayFormat("$name$");		
        this.f7Province.setEditFormat("$number$");		
        this.f7Province.setCommitFormat("$number$");
        // f7CustomerOrigin		
        this.f7CustomerOrigin.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerOriginQuery");		
        this.f7CustomerOrigin.setDisplayFormat("$name$");		
        this.f7CustomerOrigin.setEditFormat("$number$");		
        this.f7CustomerOrigin.setCommitFormat("$number$");
        // txtPhone		
        this.txtPhone.setRequired(true);		
        this.txtPhone.setMaxLength(80);
        this.txtPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtPhone_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtEmail		
        this.txtEmail.setMaxLength(80);
        // dpBookedDate
        // txtCertificateNumber		
        this.txtCertificateNumber.setMaxLength(80);
        // txtMailAddress		
        this.txtMailAddress.setMaxLength(80);
        // txtWorkCompany		
        this.txtWorkCompany.setMaxLength(80);
        // f7ReceptionType		
        this.f7ReceptionType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ReceptionTypeQuery");		
        this.f7ReceptionType.setDisplayFormat("$name$");		
        this.f7ReceptionType.setEditFormat("$number$");		
        this.f7ReceptionType.setCommitFormat("$number$");
        // f7CustomerGrade		
        this.f7CustomerGrade.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerGradeQuery");		
        this.f7CustomerGrade.setDisplayFormat("$name$");		
        this.f7CustomerGrade.setEditFormat("$number$");		
        this.f7CustomerGrade.setCommitFormat("$number$");
        // txtEmployment		
        this.txtEmployment.setMaxLength(80);
        // boxSex		
        this.boxSex.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SexEnum").toArray());		
        this.boxSex.setSelectedIndex(0);
        // f7HabitationArea		
        this.f7HabitationArea.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HabitationAreaQuery");		
        this.f7HabitationArea.setDisplayFormat("$name$");		
        this.f7HabitationArea.setEditFormat("$number$");		
        this.f7HabitationArea.setCommitFormat("$number$");
        // txtBooker		
        this.txtBooker.setMaxLength(80);
        // spinTradeTime
        // f7FamillyEarning		
        this.f7FamillyEarning.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.FamillyEarningQuery");		
        this.f7FamillyEarning.setDisplayFormat("$name$");		
        this.f7FamillyEarning.setEditFormat("$number$");		
        this.f7FamillyEarning.setCommitFormat("$number$");
        // txtPostalcode		
        this.txtPostalcode.setMaxLength(80);
        // f7Project		
        this.f7Project.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.f7Project.setDisplayFormat("$name$");		
        this.f7Project.setEditFormat("$number$");		
        this.f7Project.setCommitFormat("$number$");		
        this.f7Project.setRequired(true);
        this.f7Project.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Project_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDScrollPane1
        // txtareaDescription		
        this.txtareaDescription.setMaxLength(255);
        // boxTrackPhase		
        this.boxTrackPhase.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.TrackPhaseEnum").toArray());		
        this.boxTrackPhase.setSelectedIndex(0);
        // txtBookedPlace		
        this.txtBookedPlace.setMaxLength(80);
        // dpLastTrackDate
        // containLinkman		
        this.containLinkman.setEnableActive(false);		
        this.containLinkman.setTitleStyle(2);
        // tblTrackRecord
		String tblTrackRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"eventDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"head.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"trackType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"trackResult\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"eventType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"receptionType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"commerceChance.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"description\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"createTime\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{eventDate}</t:Cell><t:Cell>$Resource{head.name}</t:Cell><t:Cell>$Resource{trackType}</t:Cell><t:Cell>$Resource{trackResult}</t:Cell><t:Cell>$Resource{eventType.name}</t:Cell><t:Cell>$Resource{receptionType.name}</t:Cell><t:Cell>$Resource{commerceChance.name}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
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

        

        // tblShareSeller
		String tblShareSellerStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"shareModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shareObject\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"operationPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"isAgainShare\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"shareDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"isUpdate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"description\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{shareModel}</t:Cell><t:Cell>$Resource{shareObject}</t:Cell><t:Cell>$Resource{operationPerson}</t:Cell><t:Cell>$Resource{isAgainShare}</t:Cell><t:Cell>$Resource{shareDate}</t:Cell><t:Cell>$Resource{isUpdate}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblShareSeller.setFormatXml(resHelper.translateString("tblShareSeller",tblShareSellerStrXML));
        this.tblShareSeller.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblShareSeller_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblShareSeller.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    tblShareSeller_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblShareSeller.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblShareSeller_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // tblAdapterLog
		String tblAdapterLogStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"beforeSeller\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"afterSeller\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"operationPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"adapterDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isAdapterInter\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isAdapterFunction\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isEndAdapter\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{beforeSeller}</t:Cell><t:Cell>$Resource{afterSeller}</t:Cell><t:Cell>$Resource{operationPerson}</t:Cell><t:Cell>$Resource{adapterDate}</t:Cell><t:Cell>$Resource{isAdapterInter}</t:Cell><t:Cell>$Resource{isAdapterFunction}</t:Cell><t:Cell>$Resource{isEndAdapter}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblAdapterLog.setFormatXml(resHelper.translateString("tblAdapterLog",tblAdapterLogStrXML));

        

        // tblLinkman
		String tblLinkmanStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"customerName\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"relation\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"customerNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"customerSex\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"phone\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"description\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{customerName}</t:Cell><t:Cell>$Resource{relation}</t:Cell><t:Cell>$Resource{customerNumber}</t:Cell><t:Cell>$Resource{customerSex}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblLinkman.setFormatXml(resHelper.translateString("tblLinkman",tblLinkmanStrXML));
        this.tblLinkman.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblLinkman_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // enterpriceProperty		
        this.enterpriceProperty.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.NatureEnterpriseQuery");
        // txtEnterpriceSize		
        this.txtEnterpriceSize.setMaxLength(80);
        // f7Industry		
        this.f7Industry.setQueryInfo("com.kingdee.eas.basedata.assistant.app.IndustryQuery");		
        this.f7Industry.setCommitFormat("$number$");		
        this.f7Industry.setDisplayFormat("$name$");		
        this.f7Industry.setEditFormat("$number$");		
        this.f7Industry.setEditable(true);
        // txtEnterpriceHomepage		
        this.txtEnterpriceHomepage.setMaxLength(80);
        // txtTel		
        this.txtTel.setMaxLength(80);
        // spinTenTradeTime
        // f7WorkArea		
        this.f7WorkArea.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.WorkAreaQuery");		
        this.f7WorkArea.setCommitFormat("$number$");		
        this.f7WorkArea.setEditFormat("$number$");		
        this.f7WorkArea.setDisplayFormat("$name$");
        // f7Country		
        this.f7Country.setDisplayFormat("$name$");		
        this.f7Country.setEditFormat("$number$");		
        this.f7Country.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CountryQuery");		
        this.f7Country.setCommitFormat("$number$");
        // txtFax		
        this.txtFax.setMaxLength(80);
        // txtQQ		
        this.txtQQ.setMaxLength(20);
        // txtOfficeTel		
        this.txtOfficeTel.setMaxLength(80);
        // f7CustomerManager		
        this.f7CustomerManager.setDisplayFormat("$name$");		
        this.f7CustomerManager.setEditFormat("$number$");		
        this.f7CustomerManager.setCommitFormat("$number$");		
        this.f7CustomerManager.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
        // f7CooperateMode		
        this.f7CooperateMode.setDisplayFormat("$name$");		
        this.f7CooperateMode.setEditFormat("$number$");		
        this.f7CooperateMode.setCommitFormat("$number$");		
        this.f7CooperateMode.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.CooperateModeQuery");
        // f7BusinessScope		
        this.f7BusinessScope.setDisplayFormat("$name$");		
        this.f7BusinessScope.setEditFormat("$number$");		
        this.f7BusinessScope.setCommitFormat("$number$");		
        this.f7BusinessScope.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.BusinessScopeQuery");
        // txtPhone2		
        this.txtPhone2.setMaxLength(80);
        // f7RoomModelType		
        this.f7RoomModelType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery");		
        this.f7RoomModelType.setDisplayFormat("$name$");		
        this.f7RoomModelType.setEditFormat("$number$");
        // txtIntentionArea		
        this.txtIntentionArea.setDataType(1);
        // txtAge
        // tblInsider
		String tblInsiderStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"insider\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"insiderCode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"vipLevel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"requestDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"insiderRemove\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"insiderHortation\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{insider}</t:Cell><t:Cell>$Resource{insiderCode}</t:Cell><t:Cell>$Resource{vipLevel}</t:Cell><t:Cell>$Resource{requestDate}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{insiderRemove}</t:Cell><t:Cell>$Resource{insiderHortation}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblInsider.setFormatXml(resHelper.translateString("tblInsider",tblInsiderStrXML));

        

        // containSaleRecord		
        this.containSaleRecord.setTitle(resHelper.getString("containSaleRecord.title"));		
        this.containSaleRecord.setTitleStyle(2);		
        this.containSaleRecord.setEnableActive(false);
        // containSaleCommerce		
        this.containSaleCommerce.setTitleStyle(2);		
        this.containSaleCommerce.setTitle(resHelper.getString("containSaleCommerce.title"));		
        this.containSaleCommerce.setPreferredSize(new Dimension(19,255));		
        this.containSaleCommerce.setEnableActive(false);
        // tblSaleRecord
		String tblSaleRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"purchaseState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"room.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sincerityPurchase.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"prePurchaseAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"prePurchaseCurrency.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"prePurchaseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dealAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"dealPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"payType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"salesman.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"prePurchaseAuditor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"prePurchaseAuditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sellType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"specilAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{purchaseState}</t:Cell><t:Cell>$Resource{room.number}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{sincerityPurchase.number}</t:Cell><t:Cell>$Resource{prePurchaseAmount}</t:Cell><t:Cell>$Resource{prePurchaseCurrency.name}</t:Cell><t:Cell>$Resource{prePurchaseDate}</t:Cell><t:Cell>$Resource{dealAmount}</t:Cell><t:Cell>$Resource{dealPrice}</t:Cell><t:Cell>$Resource{payType.name}</t:Cell><t:Cell>$Resource{salesman.name}</t:Cell><t:Cell>$Resource{prePurchaseAuditor.name}</t:Cell><t:Cell>$Resource{prePurchaseAuditDate}</t:Cell><t:Cell>$Resource{sellType}</t:Cell><t:Cell>$Resource{specilAgio}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSaleRecord.setFormatXml(resHelper.translateString("tblSaleRecord",tblSaleRecordStrXML));
        this.tblSaleRecord.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblSaleRecord_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // tblSaleCommerce
		String tblSaleCommerceStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sysType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sellProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saleMan.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceLevel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fdcCustomer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{sysType}</t:Cell><t:Cell>$Resource{sellProject.name}</t:Cell><t:Cell>$Resource{saleMan.name}</t:Cell><t:Cell>$Resource{commerceLevel.name}</t:Cell><t:Cell>$Resource{commerceStatus}</t:Cell><t:Cell>$Resource{fdcCustomer.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSaleCommerce.setFormatXml(resHelper.translateString("tblSaleCommerce",tblSaleCommerceStrXML));
        this.tblSaleCommerce.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblSaleCommerce_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // containTenRecord		
        this.containTenRecord.setTitleStyle(2);		
        this.containTenRecord.setTitle(resHelper.getString("containTenRecord.title"));		
        this.containTenRecord.setEnableActive(false);
        // containTenCommerce		
        this.containTenCommerce.setTitle(resHelper.getString("containTenCommerce.title"));		
        this.containTenCommerce.setTitleStyle(2);		
        this.containTenCommerce.setPreferredSize(new Dimension(19,255));		
        this.containTenCommerce.setEnableActive(false);
        // tblTenancyRecord
		String tblTenancyRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"tenancyState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tenancyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tenRoomsDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tenAttachesDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tenCustomerDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tenancyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"oldTenancyBill.tenancyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"leaseCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"leaseTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"flagAtTerm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tenancyAdviser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"agency.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dealTotalRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"standardTotalRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"depositAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"firstPayRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"deliveryRoomDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"specialClause\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{tenancyState}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{tenancyName}</t:Cell><t:Cell>$Resource{tenRoomsDes}</t:Cell><t:Cell>$Resource{tenAttachesDes}</t:Cell><t:Cell>$Resource{tenCustomerDes}</t:Cell><t:Cell>$Resource{tenancyType}</t:Cell><t:Cell>$Resource{oldTenancyBill.tenancyName}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{leaseCount}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{leaseTime}</t:Cell><t:Cell>$Resource{flagAtTerm}</t:Cell><t:Cell>$Resource{tenancyAdviser.name}</t:Cell><t:Cell>$Resource{agency.name}</t:Cell><t:Cell>$Resource{dealTotalRent}</t:Cell><t:Cell>$Resource{standardTotalRent}</t:Cell><t:Cell>$Resource{depositAmount}</t:Cell><t:Cell>$Resource{firstPayRent}</t:Cell><t:Cell>$Resource{deliveryRoomDate}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{specialClause}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblTenancyRecord.setFormatXml(resHelper.translateString("tblTenancyRecord",tblTenancyRecordStrXML));
        this.tblTenancyRecord.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblTenancyRecord_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // tblTenancyCommerce
		String tblTenancyCommerceStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sysType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sellProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saleMan.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceLevel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fdcCustomer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{sysType}</t:Cell><t:Cell>$Resource{sellProject.name}</t:Cell><t:Cell>$Resource{saleMan.name}</t:Cell><t:Cell>$Resource{commerceLevel.name}</t:Cell><t:Cell>$Resource{commerceStatus}</t:Cell><t:Cell>$Resource{fdcCustomer.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblTenancyCommerce.setFormatXml(resHelper.translateString("tblTenancyCommerce",tblTenancyCommerceStrXML));
        this.tblTenancyCommerce.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblTenancyCommerce_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kDContainer3		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));
        // houseProperty
		String housePropertyStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /><c:NumberFormat>0.000</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /><c:NumberFormat>0.000</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /><c:NumberFormat>0.000</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /><c:NumberFormat>0.000</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"projectName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol1\" /><t:Column t:key=\"partition\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol2\" /><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol3\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol4\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol5\" /><t:Column t:key=\"buildingarea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"actualBuildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"actualRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"customerType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{projectName}</t:Cell><t:Cell>$Resource{partition}</t:Cell><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{buildingarea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{actualBuildingArea}</t:Cell><t:Cell>$Resource{actualRoomArea}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{customerType}</t:Cell><t:Cell>$Resource{phone}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.houseProperty.setFormatXml(resHelper.translateString("houseProperty",housePropertyStrXML));
        this.houseProperty.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    houseProperty_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.houseProperty.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    houseProperty_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.houseProperty.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    houseProperty_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // arrearsRecord
		String arrearsRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /><c:NumberFormat>0.00</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /><c:NumberFormat>0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"projectName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"fundName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"receiveFee\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"usableFee\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{projectName}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{fundName}</t:Cell><t:Cell>$Resource{receiveFee}</t:Cell><t:Cell>$Resource{usableFee}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.arrearsRecord.setFormatXml(resHelper.translateString("arrearsRecord",arrearsRecordStrXML));

        

        // advanceReceipt
		String advanceReceiptStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /><c:NumberFormat>0.00</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /><c:NumberFormat>0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"projectName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"roomNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"fundName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"payFeeDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"receivableFee\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"arrearageFee\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{projectName}</t:Cell><t:Cell>$Resource{roomNumber}</t:Cell><t:Cell>$Resource{fundName}</t:Cell><t:Cell>$Resource{payFeeDate}</t:Cell><t:Cell>$Resource{receivableFee}</t:Cell><t:Cell>$Resource{arrearageFee}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.advanceReceipt.setFormatXml(resHelper.translateString("advanceReceipt",advanceReceiptStrXML));

        

        // kDContainer4		
        this.kDContainer4.setTitle(resHelper.getString("kDContainer4.title"));
        // kDContainer5		
        this.kDContainer5.setTitle(resHelper.getString("kDContainer5.title"));
        // kDContainer6		
        this.kDContainer6.setTitle(resHelper.getString("kDContainer6.title"));
        // specialServer
		String specialServerStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"proeprtyItem\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"roomNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"servicesProject\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"appointments\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{proeprtyItem}</t:Cell><t:Cell>$Resource{roomNumber}</t:Cell><t:Cell>$Resource{servicesProject}</t:Cell><t:Cell>$Resource{appointments}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.specialServer.setFormatXml(resHelper.translateString("specialServer",specialServerStrXML));
        this.specialServer.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    specialServer_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.specialServer.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    specialServer_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // serverRecord
		String serverRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"propertyItem\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"roomNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"callTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"callcontent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"dealType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"questionSort\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"dealSchedule\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"dealDescription\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"dealTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"dealPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{propertyItem}</t:Cell><t:Cell>$Resource{roomNumber}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{callTime}</t:Cell><t:Cell>$Resource{callcontent}</t:Cell><t:Cell>$Resource{dealType}</t:Cell><t:Cell>$Resource{questionSort}</t:Cell><t:Cell>$Resource{dealSchedule}</t:Cell><t:Cell>$Resource{dealDescription}</t:Cell><t:Cell>$Resource{dealTime}</t:Cell><t:Cell>$Resource{dealPerson}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.serverRecord.setFormatXml(resHelper.translateString("serverRecord",serverRecordStrXML));

        

        // complaintRecord
		String complaintRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"propertyItem\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"roomNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"callTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"callContent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"dealType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"questionSort\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"dealSchedule\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"dealDescription\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"dealTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"dealPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{propertyItem}</t:Cell><t:Cell>$Resource{roomNumber}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{callTime}</t:Cell><t:Cell>$Resource{callContent}</t:Cell><t:Cell>$Resource{dealType}</t:Cell><t:Cell>$Resource{questionSort}</t:Cell><t:Cell>$Resource{dealSchedule}</t:Cell><t:Cell>$Resource{dealDescription}</t:Cell><t:Cell>$Resource{dealTime}</t:Cell><t:Cell>$Resource{dealPerson}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.complaintRecord.setFormatXml(resHelper.translateString("complaintRecord",complaintRecordStrXML));

        

        // btnAddCommerceChance
        this.btnAddCommerceChance.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCommerceChance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddCommerceChance.setText(resHelper.getString("btnAddCommerceChance.text"));		
        this.btnAddCommerceChance.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
        // btnAddLine
        this.btnAddLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddLine.setText(resHelper.getString("btnAddLine.text"));		
        this.btnAddLine.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // separatorFW4
        // btnInsider
        this.btnInsider.setAction((IItemAction)ActionProxyFactory.getProxy(actionInsider, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInsider.setText(resHelper.getString("btnInsider.text"));		
        this.btnInsider.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_distributebatch"));
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
        // menuAddCommerceChance
        this.menuAddCommerceChance.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCommerceChance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuAddCommerceChance.setText(resHelper.getString("menuAddCommerceChance.text"));		
        this.menuAddCommerceChance.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
        // boxCertificateName		
        this.boxCertificateName.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CertificateSearchQuery");
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
        this.setBounds(new Rectangle(10, 10, 1050, 580));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1050, 580));
        tabNew.setBounds(new Rectangle(13, 4, 1023, 573));
        this.add(tabNew, new KDLayout.Constraints(13, 4, 1023, 573, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //tabNew
        tabNew.add(panelBaseInfo, resHelper.getString("panelBaseInfo.constraints"));
        tabNew.add(panelCustomer, resHelper.getString("panelCustomer.constraints"));
        tabNew.add(panelSale, resHelper.getString("panelSale.constraints"));
        tabNew.add(panelTenancy, resHelper.getString("panelTenancy.constraints"));
        tabNew.add(panelManager, resHelper.getString("panelManager.constraints"));
        tabNew.add(panelCusService, resHelper.getString("panelCusService.constraints"));
        //panelBaseInfo
        panelBaseInfo.setLayout(new KDLayout());
        panelBaseInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1022, 540));        lcCustomerType.setBounds(new Rectangle(11, 30, 290, 19));
        panelBaseInfo.add(lcCustomerType, new KDLayout.Constraints(11, 30, 290, 19, 0));
        lcCustomerNumber.setBounds(new Rectangle(11, 7, 290, 19));
        panelBaseInfo.add(lcCustomerNumber, new KDLayout.Constraints(11, 7, 290, 19, 0));
        lcSalesman.setBounds(new Rectangle(700, 7, 290, 19));
        panelBaseInfo.add(lcSalesman, new KDLayout.Constraints(700, 7, 290, 19, 0));
        lcName.setBounds(new Rectangle(355, 7, 290, 19));
        panelBaseInfo.add(lcName, new KDLayout.Constraints(355, 7, 290, 19, 0));
        lcProvince.setBounds(new Rectangle(355, 155, 290, 19));
        panelBaseInfo.add(lcProvince, new KDLayout.Constraints(355, 155, 290, 19, 0));
        lcCustomerOrigin.setBounds(new Rectangle(701, 205, 290, 19));
        panelBaseInfo.add(lcCustomerOrigin, new KDLayout.Constraints(701, 205, 290, 19, 0));
        lcPhone.setBounds(new Rectangle(355, 82, 290, 19));
        panelBaseInfo.add(lcPhone, new KDLayout.Constraints(355, 82, 290, 19, 0));
        lcEmail.setBounds(new Rectangle(701, 180, 290, 19));
        panelBaseInfo.add(lcEmail, new KDLayout.Constraints(701, 180, 290, 19, 0));
        lcBookedDate.setBounds(new Rectangle(701, 257, 290, 19));
        panelBaseInfo.add(lcBookedDate, new KDLayout.Constraints(701, 257, 290, 19, 0));
        lcCertificateName.setBounds(new Rectangle(11, 205, 290, 19));
        panelBaseInfo.add(lcCertificateName, new KDLayout.Constraints(11, 205, 290, 19, 0));
        lcCertificateNumber.setBounds(new Rectangle(355, 205, 290, 19));
        panelBaseInfo.add(lcCertificateNumber, new KDLayout.Constraints(355, 205, 290, 19, 0));
        lcMailAddress.setBounds(new Rectangle(11, 180, 635, 19));
        panelBaseInfo.add(lcMailAddress, new KDLayout.Constraints(11, 180, 635, 19, 0));
        lcWorkCompany.setBounds(new Rectangle(355, 57, 290, 19));
        panelBaseInfo.add(lcWorkCompany, new KDLayout.Constraints(355, 57, 290, 19, 0));
        lcReceptionType.setBounds(new Rectangle(11, 231, 290, 19));
        panelBaseInfo.add(lcReceptionType, new KDLayout.Constraints(11, 231, 290, 19, 0));
        lcCustomerGrade.setBounds(new Rectangle(11, 81, 290, 19));
        panelBaseInfo.add(lcCustomerGrade, new KDLayout.Constraints(11, 81, 290, 19, 0));
        lcEmployment.setBounds(new Rectangle(11, 54, 290, 19));
        panelBaseInfo.add(lcEmployment, new KDLayout.Constraints(11, 54, 290, 19, 0));
        lcSex.setBounds(new Rectangle(355, 32, 290, 19));
        panelBaseInfo.add(lcSex, new KDLayout.Constraints(355, 32, 290, 19, 0));
        lcHabitationArea.setBounds(new Rectangle(355, 129, 290, 19));
        panelBaseInfo.add(lcHabitationArea, new KDLayout.Constraints(355, 129, 290, 19, 0));
        lcBooker.setBounds(new Rectangle(355, 257, 290, 19));
        panelBaseInfo.add(lcBooker, new KDLayout.Constraints(355, 257, 290, 19, 0));
        kDSeparator5.setBounds(new Rectangle(9, 79, 984, 10));
        panelBaseInfo.add(kDSeparator5, new KDLayout.Constraints(9, 79, 984, 10, 0));
        lcTradeTime.setBounds(new Rectangle(701, 231, 290, 19));
        panelBaseInfo.add(lcTradeTime, new KDLayout.Constraints(701, 231, 290, 19, 0));
        lcFamillyEarning.setBounds(new Rectangle(701, 32, 290, 19));
        panelBaseInfo.add(lcFamillyEarning, new KDLayout.Constraints(701, 32, 290, 19, 0));
        lcPostalcode.setBounds(new Rectangle(700, 155, 290, 19));
        panelBaseInfo.add(lcPostalcode, new KDLayout.Constraints(700, 155, 290, 19, 0));
        lcProject.setBounds(new Rectangle(701, 57, 290, 19));
        panelBaseInfo.add(lcProject, new KDLayout.Constraints(701, 57, 290, 19, 0));
        lcDescription.setBounds(new Rectangle(11, 282, 635, 22));
        panelBaseInfo.add(lcDescription, new KDLayout.Constraints(11, 282, 635, 22, 0));
        lcTrackPhase.setBounds(new Rectangle(756, 55, 290, 19));
        panelBaseInfo.add(lcTrackPhase, new KDLayout.Constraints(756, 55, 290, 19, 0));
        lcBookedPlace.setBounds(new Rectangle(11, 257, 290, 19));
        panelBaseInfo.add(lcBookedPlace, new KDLayout.Constraints(11, 257, 290, 19, 0));
        lcLastTrackDate.setBounds(new Rectangle(355, 231, 290, 19));
        panelBaseInfo.add(lcLastTrackDate, new KDLayout.Constraints(355, 231, 290, 19, 0));
        kDTabbedPane1.setBounds(new Rectangle(12, 359, 981, 182));
        panelBaseInfo.add(kDTabbedPane1, new KDLayout.Constraints(12, 359, 981, 182, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contEnterpriceProperty.setBounds(new Rectangle(355, 33, 290, 19));
        panelBaseInfo.add(contEnterpriceProperty, new KDLayout.Constraints(355, 33, 290, 19, 0));
        contEnterpriceSize.setBounds(new Rectangle(701, 32, 290, 19));
        panelBaseInfo.add(contEnterpriceSize, new KDLayout.Constraints(701, 32, 290, 19, 0));
        contIndustry.setBounds(new Rectangle(11, 55, 290, 19));
        panelBaseInfo.add(contIndustry, new KDLayout.Constraints(11, 55, 290, 19, 0));
        contEnterpriceHomepage.setBounds(new Rectangle(355, 59, 290, 19));
        panelBaseInfo.add(contEnterpriceHomepage, new KDLayout.Constraints(355, 59, 290, 19, 0));
        chkIsForSHE.setBounds(new Rectangle(11, 337, 140, 19));
        panelBaseInfo.add(chkIsForSHE, new KDLayout.Constraints(11, 337, 140, 19, 0));
        chkIsForTen.setBounds(new Rectangle(355, 337, 140, 19));
        panelBaseInfo.add(chkIsForTen, new KDLayout.Constraints(355, 337, 140, 19, 0));
        chkIsForPPM.setBounds(new Rectangle(701, 337, 140, 19));
        panelBaseInfo.add(chkIsForPPM, new KDLayout.Constraints(701, 337, 140, 19, 0));
        contTel.setBounds(new Rectangle(701, 82, 290, 19));
        panelBaseInfo.add(contTel, new KDLayout.Constraints(701, 82, 290, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(701, 282, 289, 19));
        panelBaseInfo.add(kDLabelContainer1, new KDLayout.Constraints(701, 282, 289, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(700, 129, 290, 19));
        panelBaseInfo.add(kDLabelContainer2, new KDLayout.Constraints(700, 129, 290, 19, 0));
        lcCountry.setBounds(new Rectangle(11, 154, 290, 19));
        panelBaseInfo.add(lcCountry, new KDLayout.Constraints(11, 154, 290, 19, 0));
        lcFax.setBounds(new Rectangle(11, 129, 290, 19));
        panelBaseInfo.add(lcFax, new KDLayout.Constraints(11, 129, 290, 19, 0));
        lcQQ.setBounds(new Rectangle(701, 105, 290, 19));
        panelBaseInfo.add(lcQQ, new KDLayout.Constraints(701, 105, 290, 19, 0));
        lcOfficeTel.setBounds(new Rectangle(11, 105, 290, 19));
        panelBaseInfo.add(lcOfficeTel, new KDLayout.Constraints(11, 105, 290, 19, 0));
        lcCustomerManager.setBounds(new Rectangle(701, 310, 290, 19));
        panelBaseInfo.add(lcCustomerManager, new KDLayout.Constraints(701, 310, 290, 19, 0));
        lcCooperateMode.setBounds(new Rectangle(11, 310, 290, 19));
        panelBaseInfo.add(lcCooperateMode, new KDLayout.Constraints(11, 310, 290, 19, 0));
        lcBusinessScope.setBounds(new Rectangle(355, 310, 290, 19));
        panelBaseInfo.add(lcBusinessScope, new KDLayout.Constraints(355, 310, 290, 19, 0));
        lcPhone2.setBounds(new Rectangle(355, 105, 290, 19));
        panelBaseInfo.add(lcPhone2, new KDLayout.Constraints(355, 105, 290, 19, 0));
        lcRoomModelType.setBounds(new Rectangle(11, 129, 289, 19));
        panelBaseInfo.add(lcRoomModelType, new KDLayout.Constraints(11, 129, 289, 19, 0));
        lcIntentionArea.setBounds(new Rectangle(11, 152, 291, 19));
        panelBaseInfo.add(lcIntentionArea, new KDLayout.Constraints(11, 152, 291, 19, 0));
        lcAge.setBounds(new Rectangle(355, 156, 290, 19));
        panelBaseInfo.add(lcAge, new KDLayout.Constraints(355, 156, 290, 19, 0));
        //lcCustomerType
        lcCustomerType.setBoundEditor(boxCustomerType);
        //lcCustomerNumber
        lcCustomerNumber.setBoundEditor(txtNumber);
        //lcSalesman
        lcSalesman.setBoundEditor(f7salesman);
        //lcName
        lcName.setBoundEditor(txtName);
        //lcProvince
        lcProvince.setBoundEditor(f7Province);
        //lcCustomerOrigin
        lcCustomerOrigin.setBoundEditor(f7CustomerOrigin);
        //lcPhone
        lcPhone.setBoundEditor(txtPhone);
        //lcEmail
        lcEmail.setBoundEditor(txtEmail);
        //lcBookedDate
        lcBookedDate.setBoundEditor(dpBookedDate);
        //lcCertificateName
        lcCertificateName.setBoundEditor(boxCertificateName);
        //lcCertificateNumber
        lcCertificateNumber.setBoundEditor(txtCertificateNumber);
        //lcMailAddress
        lcMailAddress.setBoundEditor(txtMailAddress);
        //lcWorkCompany
        lcWorkCompany.setBoundEditor(txtWorkCompany);
        //lcReceptionType
        lcReceptionType.setBoundEditor(f7ReceptionType);
        //lcCustomerGrade
        lcCustomerGrade.setBoundEditor(f7CustomerGrade);
        //lcEmployment
        lcEmployment.setBoundEditor(txtEmployment);
        //lcSex
        lcSex.setBoundEditor(boxSex);
        //lcHabitationArea
        lcHabitationArea.setBoundEditor(f7HabitationArea);
        //lcBooker
        lcBooker.setBoundEditor(txtBooker);
        //lcTradeTime
        lcTradeTime.setBoundEditor(spinTradeTime);
        //lcFamillyEarning
        lcFamillyEarning.setBoundEditor(f7FamillyEarning);
        //lcPostalcode
        lcPostalcode.setBoundEditor(txtPostalcode);
        //lcProject
        lcProject.setBoundEditor(f7Project);
        //lcDescription
        lcDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtareaDescription, null);
        //lcTrackPhase
        lcTrackPhase.setBoundEditor(boxTrackPhase);
        //lcBookedPlace
        lcBookedPlace.setBoundEditor(txtBookedPlace);
        //lcLastTrackDate
        lcLastTrackDate.setBoundEditor(dpLastTrackDate);
        //kDTabbedPane1
        kDTabbedPane1.add(containLinkman, resHelper.getString("containLinkman.constraints"));
        kDTabbedPane1.add(tblTrackRecord, resHelper.getString("tblTrackRecord.constraints"));
        kDTabbedPane1.add(tblShareSeller, resHelper.getString("tblShareSeller.constraints"));
        kDTabbedPane1.add(tblAdapterLog, resHelper.getString("tblAdapterLog.constraints"));
        //containLinkman
containLinkman.getContentPane().setLayout(new BorderLayout(0, 0));        containLinkman.getContentPane().add(tblLinkman, BorderLayout.CENTER);
        //contEnterpriceProperty
        contEnterpriceProperty.setBoundEditor(enterpriceProperty);
        //contEnterpriceSize
        contEnterpriceSize.setBoundEditor(txtEnterpriceSize);
        //contIndustry
        contIndustry.setBoundEditor(f7Industry);
        //contEnterpriceHomepage
        contEnterpriceHomepage.setBoundEditor(txtEnterpriceHomepage);
        //contTel
        contTel.setBoundEditor(txtTel);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(spinTenTradeTime);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(f7WorkArea);
        //lcCountry
        lcCountry.setBoundEditor(f7Country);
        //lcFax
        lcFax.setBoundEditor(txtFax);
        //lcQQ
        lcQQ.setBoundEditor(txtQQ);
        //lcOfficeTel
        lcOfficeTel.setBoundEditor(txtOfficeTel);
        //lcCustomerManager
        lcCustomerManager.setBoundEditor(f7CustomerManager);
        //lcCooperateMode
        lcCooperateMode.setBoundEditor(f7CooperateMode);
        //lcBusinessScope
        lcBusinessScope.setBoundEditor(f7BusinessScope);
        //lcPhone2
        lcPhone2.setBoundEditor(txtPhone2);
        //lcRoomModelType
        lcRoomModelType.setBoundEditor(f7RoomModelType);
        //lcIntentionArea
        lcIntentionArea.setBoundEditor(txtIntentionArea);
        //lcAge
        lcAge.setBoundEditor(txtAge);
        //panelCustomer
panelCustomer.setLayout(new BorderLayout(0, 0));        panelCustomer.add(tblInsider, BorderLayout.CENTER);
        //panelSale
panelSale.setLayout(new BorderLayout(0, 0));        panelSale.add(containSaleRecord, BorderLayout.CENTER);
        panelSale.add(containSaleCommerce, BorderLayout.SOUTH);
        //containSaleRecord
containSaleRecord.getContentPane().setLayout(new BorderLayout(0, 0));        containSaleRecord.getContentPane().add(tblSaleRecord, BorderLayout.CENTER);
        //containSaleCommerce
containSaleCommerce.getContentPane().setLayout(new BorderLayout(0, 0));        containSaleCommerce.getContentPane().add(tblSaleCommerce, BorderLayout.CENTER);
        //panelTenancy
panelTenancy.setLayout(new BorderLayout(0, 0));        panelTenancy.add(containTenRecord, BorderLayout.CENTER);
        panelTenancy.add(containTenCommerce, BorderLayout.SOUTH);
        //containTenRecord
containTenRecord.getContentPane().setLayout(new BorderLayout(0, 0));        containTenRecord.getContentPane().add(tblTenancyRecord, BorderLayout.CENTER);
        //containTenCommerce
containTenCommerce.getContentPane().setLayout(new BorderLayout(0, 0));        containTenCommerce.getContentPane().add(tblTenancyCommerce, BorderLayout.CENTER);
        //panelManager
        panelManager.setLayout(new KDLayout());
        panelManager.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1022, 540));        kDContainer1.setBounds(new Rectangle(0, 0, 1019, 179));
        panelManager.add(kDContainer1, new KDLayout.Constraints(0, 0, 1019, 179, 0));
        kDContainer2.setBounds(new Rectangle(0, 346, 1019, 191));
        panelManager.add(kDContainer2, new KDLayout.Constraints(0, 346, 1019, 191, 0));
        kDContainer3.setBounds(new Rectangle(0, 178, 1015, 168));
        panelManager.add(kDContainer3, new KDLayout.Constraints(0, 178, 1015, 168, 0));
        //kDContainer1
        kDContainer1.getContentPane().setLayout(null);        houseProperty.setBounds(new Rectangle(2, 2, 1012, 150));
        kDContainer1.getContentPane().add(houseProperty, null);
        //kDContainer2
        kDContainer2.getContentPane().setLayout(null);        arrearsRecord.setBounds(new Rectangle(2, 1, 1011, 171));
        kDContainer2.getContentPane().add(arrearsRecord, null);
        //kDContainer3
        kDContainer3.getContentPane().setLayout(null);        advanceReceipt.setBounds(new Rectangle(4, 2, 1002, 143));
        kDContainer3.getContentPane().add(advanceReceipt, null);
        //panelCusService
        panelCusService.setLayout(new KDLayout());
        panelCusService.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1022, 540));        kDContainer4.setBounds(new Rectangle(0, 0, 1019, 187));
        panelCusService.add(kDContainer4, new KDLayout.Constraints(0, 0, 1019, 187, 0));
        kDContainer5.setBounds(new Rectangle(-1, 187, 1019, 196));
        panelCusService.add(kDContainer5, new KDLayout.Constraints(-1, 187, 1019, 196, 0));
        kDContainer6.setBounds(new Rectangle(0, 384, 1019, 158));
        panelCusService.add(kDContainer6, new KDLayout.Constraints(0, 384, 1019, 158, 0));
        //kDContainer4
        kDContainer4.getContentPane().setLayout(null);        specialServer.setBounds(new Rectangle(-1, -2, 1012, 152));
        kDContainer4.getContentPane().add(specialServer, null);
        //kDContainer5
        kDContainer5.getContentPane().setLayout(null);        serverRecord.setBounds(new Rectangle(3, 0, 1005, 165));
        kDContainer5.getContentPane().add(serverRecord, null);
        //kDContainer6
        kDContainer6.getContentPane().setLayout(null);        complaintRecord.setBounds(new Rectangle(2, 3, 1008, 118));
        kDContainer6.getContentPane().add(complaintRecord, null);

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
        menuBiz.add(menuItemAdapter);
        menuBiz.add(menuItemShare);
        menuBiz.add(menuItemCancelShare);
        menuBiz.add(menuAddCommerceChance);
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
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(btnAddCommerceChance);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnInsider);
        this.toolBar.add(btnCustomerAdapter);
        this.toolBar.add(btnCustomerShare);
        this.toolBar.add(btnCustomerCancelShare);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isForSHE", boolean.class, this.chkIsForSHE, "selected");
		dataBinder.registerBinding("isForTen", boolean.class, this.chkIsForTen, "selected");
		dataBinder.registerBinding("isForPPM", boolean.class, this.chkIsForPPM, "selected");
		dataBinder.registerBinding("customerType", com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum.class, this.boxCustomerType, "selectedItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("salesman", com.kingdee.eas.base.permission.UserInfo.class, this.f7salesman, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("province", com.kingdee.eas.basedata.assistant.ProvinceInfo.class, this.f7Province, "data");
		dataBinder.registerBinding("customerOrigin", com.kingdee.eas.fdc.sellhouse.CustomerOriginInfo.class, this.f7CustomerOrigin, "data");
		dataBinder.registerBinding("phone", String.class, this.txtPhone, "text");
		dataBinder.registerBinding("email", String.class, this.txtEmail, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.dpBookedDate, "value");
		dataBinder.registerBinding("certificateNumber", String.class, this.txtCertificateNumber, "text");
		dataBinder.registerBinding("mailAddress", String.class, this.txtMailAddress, "text");
		dataBinder.registerBinding("workCompany", String.class, this.txtWorkCompany, "text");
		dataBinder.registerBinding("receptionType", com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo.class, this.f7ReceptionType, "data");
		dataBinder.registerBinding("customerGrade", com.kingdee.eas.fdc.sellhouse.CustomerGradeInfo.class, this.f7CustomerGrade, "data");
		dataBinder.registerBinding("employment", String.class, this.txtEmployment, "text");
		dataBinder.registerBinding("sex", com.kingdee.eas.fdc.sellhouse.SexEnum.class, this.boxSex, "selectedItem");
		dataBinder.registerBinding("habitationArea", com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo.class, this.f7HabitationArea, "data");
		dataBinder.registerBinding("creator.name", String.class, this.txtBooker, "text");
		dataBinder.registerBinding("tradeTime", long.class, this.spinTradeTime, "value");
		dataBinder.registerBinding("famillyEarning", com.kingdee.eas.fdc.sellhouse.FamillyEarningInfo.class, this.f7FamillyEarning, "data");
		dataBinder.registerBinding("postalcode", String.class, this.txtPostalcode, "text");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.f7Project, "data");
		dataBinder.registerBinding("description", String.class, this.txtareaDescription, "text");
		dataBinder.registerBinding("trackPhase", com.kingdee.eas.fdc.sellhouse.TrackPhaseEnum.class, this.boxTrackPhase, "selectedItem");
		dataBinder.registerBinding("bookedPlace", String.class, this.txtBookedPlace, "text");
		dataBinder.registerBinding("lastTrackDate", java.util.Date.class, this.dpLastTrackDate, "value");
		dataBinder.registerBinding("businessNature", com.kingdee.eas.fdc.tenancy.NatureEnterpriseInfo.class, this.enterpriceProperty, "data");
		dataBinder.registerBinding("enterpriceSize", String.class, this.txtEnterpriceSize, "text");
		dataBinder.registerBinding("industry", com.kingdee.eas.basedata.assistant.IndustryInfo.class, this.f7Industry, "data");
		dataBinder.registerBinding("enterpriceHomepage", String.class, this.txtEnterpriceHomepage, "text");
		dataBinder.registerBinding("tel", String.class, this.txtTel, "text");
		dataBinder.registerBinding("tenTradeTime", long.class, this.spinTenTradeTime, "value");
		dataBinder.registerBinding("country", com.kingdee.eas.basedata.assistant.CountryInfo.class, this.f7Country, "data");
		dataBinder.registerBinding("fax", String.class, this.txtFax, "text");
		dataBinder.registerBinding("QQ", String.class, this.txtQQ, "text");
		dataBinder.registerBinding("officeTel", String.class, this.txtOfficeTel, "text");
		dataBinder.registerBinding("customerManager", com.kingdee.eas.base.permission.UserInfo.class, this.f7CustomerManager, "data");
		dataBinder.registerBinding("cooperateMode", com.kingdee.eas.fdc.tenancy.CooperateModeInfo.class, this.f7CooperateMode, "data");
		dataBinder.registerBinding("phone2", String.class, this.txtPhone2, "text");
		dataBinder.registerBinding("roomModelType", com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo.class, this.f7RoomModelType, "data");
		dataBinder.registerBinding("intentionArea", java.math.BigDecimal.class, this.txtIntentionArea, "value");
		dataBinder.registerBinding("age", int.class, this.txtAge, "value");
		dataBinder.registerBinding("certificateName", com.kingdee.eas.fdc.sellhouse.CertificateInfo.class, this.boxCertificateName, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CustomerExtEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)ov;
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
		getValidateHelper().registerBindProperty("isForSHE", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isForTen", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isForPPM", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customerType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("salesman", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("province", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customerOrigin", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("email", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("certificateNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mailAddress", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("workCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("receptionType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customerGrade", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("employment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("habitationArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tradeTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("famillyEarning", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("postalcode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("trackPhase", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedPlace", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastTrackDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("businessNature", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("enterpriceSize", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("industry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("enterpriceHomepage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenTradeTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("country", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("QQ", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("officeTel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customerManager", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cooperateMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("phone2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomModelType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("intentionArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("age", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("certificateName", ValidateHelper.ON_SAVE);    		
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
     * output chkIsForSHE_actionPerformed method
     */
    protected void chkIsForSHE_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkIsForTen_actionPerformed method
     */
    protected void chkIsForTen_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkIsForPPM_actionPerformed method
     */
    protected void chkIsForPPM_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output boxCustomerType_itemStateChanged method
     */
    protected void boxCustomerType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output f7salesman_dataChanged method
     */
    protected void f7salesman_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtPhone_focusLost method
     */
    protected void txtPhone_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output f7Project_dataChanged method
     */
    protected void f7Project_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblTrackRecord_tableClicked method
     */
    protected void tblTrackRecord_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblShareSeller_tableSelectChanged method
     */
    protected void tblShareSeller_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output tblShareSeller_editStopped method
     */
    protected void tblShareSeller_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblShareSeller_activeCellChanged method
     */
    protected void tblShareSeller_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output tblLinkman_editStopped method
     */
    protected void tblLinkman_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblSaleRecord_tableClicked method
     */
    protected void tblSaleRecord_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblSaleCommerce_tableClicked method
     */
    protected void tblSaleCommerce_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblTenancyRecord_tableClicked method
     */
    protected void tblTenancyRecord_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblTenancyCommerce_tableClicked method
     */
    protected void tblTenancyCommerce_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output houseProperty_tableClicked method
     */
    protected void houseProperty_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output houseProperty_tableSelectChanged method
     */
    protected void houseProperty_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output houseProperty_editStopped method
     */
    protected void houseProperty_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output specialServer_tableClicked method
     */
    protected void specialServer_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output specialServer_tableSelectChanged method
     */
    protected void specialServer_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isForSHE"));
        sic.add(new SelectorItemInfo("isForTen"));
        sic.add(new SelectorItemInfo("isForPPM"));
        sic.add(new SelectorItemInfo("customerType"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("salesman.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("province.*"));
        sic.add(new SelectorItemInfo("customerOrigin.*"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("email"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("certificateNumber"));
        sic.add(new SelectorItemInfo("mailAddress"));
        sic.add(new SelectorItemInfo("workCompany"));
        sic.add(new SelectorItemInfo("receptionType.*"));
        sic.add(new SelectorItemInfo("customerGrade.*"));
        sic.add(new SelectorItemInfo("employment"));
        sic.add(new SelectorItemInfo("sex"));
        sic.add(new SelectorItemInfo("habitationArea.*"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("tradeTime"));
        sic.add(new SelectorItemInfo("famillyEarning.*"));
        sic.add(new SelectorItemInfo("postalcode"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("trackPhase"));
        sic.add(new SelectorItemInfo("bookedPlace"));
        sic.add(new SelectorItemInfo("lastTrackDate"));
        sic.add(new SelectorItemInfo("businessNature.*"));
        sic.add(new SelectorItemInfo("enterpriceSize"));
        sic.add(new SelectorItemInfo("industry.*"));
        sic.add(new SelectorItemInfo("enterpriceHomepage"));
        sic.add(new SelectorItemInfo("tel"));
        sic.add(new SelectorItemInfo("tenTradeTime"));
        sic.add(new SelectorItemInfo("country.*"));
        sic.add(new SelectorItemInfo("fax"));
        sic.add(new SelectorItemInfo("QQ"));
        sic.add(new SelectorItemInfo("officeTel"));
        sic.add(new SelectorItemInfo("customerManager.*"));
        sic.add(new SelectorItemInfo("cooperateMode.*"));
        sic.add(new SelectorItemInfo("phone2"));
        sic.add(new SelectorItemInfo("roomModelType.*"));
        sic.add(new SelectorItemInfo("intentionArea"));
        sic.add(new SelectorItemInfo("age"));
        sic.add(new SelectorItemInfo("certificateName.*"));
        return sic;
    }        
    	

    /**
     * output actionAddLinkman_actionPerformed method
     */
    public void actionAddLinkman_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddLine_actionPerformed method
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
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
     * output actionAddCommerceChance_actionPerformed method
     */
    public void actionAddCommerceChance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveLinkman_actionPerformed method
     */
    public void actionRemoveLinkman_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInsider_actionPerformed method
     */
    public void actionInsider_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddLinkman(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddLinkman() {
    	return false;
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
	public RequestContext prepareActionCustomerAdapter(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCustomerAdapter() {
    	return false;
    }
	public RequestContext prepareActionCustomerShare(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCustomerShare() {
    	return false;
    }
	public RequestContext prepareActionCustomerCancelShare(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCustomerCancelShare() {
    	return false;
    }
	public RequestContext prepareActionAddCommerceChance(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddCommerceChance() {
    	return false;
    }
	public RequestContext prepareActionRemoveLinkman(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveLinkman() {
    	return false;
    }
	public RequestContext prepareActionInsider(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInsider() {
    	return false;
    }

    /**
     * output ActionAddLinkman class
     */     
    protected class ActionAddLinkman extends ItemAction {     
    
        public ActionAddLinkman()
        {
            this(null);
        }

        public ActionAddLinkman(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddLinkman.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLinkman.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLinkman.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerExtEditUI.this, "ActionAddLinkman", "actionAddLinkman_actionPerformed", e);
        }
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
            innerActionPerformed("eas", AbstractCustomerExtEditUI.this, "ActionAddLine", "actionAddLine_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractCustomerExtEditUI.this, "ActionCustomerAdapter", "actionCustomerAdapter_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractCustomerExtEditUI.this, "ActionCustomerShare", "actionCustomerShare_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractCustomerExtEditUI.this, "ActionCustomerCancelShare", "actionCustomerCancelShare_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractCustomerExtEditUI.this, "ActionAddCommerceChance", "actionAddCommerceChance_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveLinkman class
     */     
    protected class ActionRemoveLinkman extends ItemAction {     
    
        public ActionRemoveLinkman()
        {
            this(null);
        }

        public ActionRemoveLinkman(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRemoveLinkman.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveLinkman.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveLinkman.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerExtEditUI.this, "ActionRemoveLinkman", "actionRemoveLinkman_actionPerformed", e);
        }
    }

    /**
     * output ActionInsider class
     */     
    protected class ActionInsider extends ItemAction {     
    
        public ActionInsider()
        {
            this(null);
        }

        public ActionInsider(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionInsider.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsider.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsider.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerExtEditUI.this, "ActionInsider", "actionInsider_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CustomerExtEditUI");
    }




}