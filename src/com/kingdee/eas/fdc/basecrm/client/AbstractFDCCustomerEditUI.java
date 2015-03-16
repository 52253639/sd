/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

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
public abstract class AbstractFDCCustomerEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCCustomerEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabNew;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelBaseInfo;
    protected com.kingdee.bos.ctrl.swing.KDContainer contExtendInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelQuestion;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelSale;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelTenancy;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelInsider;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelCusService;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelManager;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelChangeRecord;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCustomerNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcName;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcSimCode;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer7;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNationality;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblDistributing;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRoomInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelLinkman;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCarInfo;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCommerce;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTrackRecord;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBankAccountInfo;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblLinkman;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkShowAll;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAdd;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDelete;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCode;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimCode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcOfficeTel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcOtherTel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcTel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcFax;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcEmail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCertificateName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCertificateNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDayOfbirth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSex;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCusStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCountry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProvince;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcMailAddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCertificateAddr;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcPostalcode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCity;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRegion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFirstLinkman;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIndustry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterpriceProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLegalPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLegalPersonTel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOfficeTel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOtherTel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFax;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEmail;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Certificate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCertificateNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpDayOfbirth;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSex;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7CusStatus;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Country;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Province;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMailAddress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCertificateAddr;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPostalcode;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7City;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Area;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFirstLinkman;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Industry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7EnterpriceProperty;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLegalPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLegalPersonTel;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Nationality;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerOrigin;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterpriceSize;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCooperateMode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMarriage;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHabitationInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFirstLinkType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCarCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWorkCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPersonArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCapital;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEmployeeCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaxNumG;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaxNumD;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7CustomerOrigin;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7EnterpriceSize;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7CooperateMode;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboMarriage;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7HabitationInfo;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboFirstLinkType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCarCount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWorkCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PersonArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCapital;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDealArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEmployeeCount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaxNumG;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaxNumD;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblQuestion;
    protected com.kingdee.bos.ctrl.swing.KDContainer containSaleRecord;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSaleRecord;
    protected com.kingdee.bos.ctrl.swing.KDContainer containSaleCommerce;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSaleCommerce;
    protected com.kingdee.bos.ctrl.swing.KDContainer containTenRecord;
    protected com.kingdee.bos.ctrl.swing.KDContainer containTenCommerce;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTenancyRecord;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTenancyCommerce;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblInsider;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer4;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer5;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer6;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable specialServer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable serverRecord;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable complaintRecord;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable houseProperty;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable arrearsRecord;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable advanceReceipt;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblChangeLog;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateWay;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreateUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUnit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCreateWay;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddCommerceChance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddLine;
    protected javax.swing.JToolBar.Separator separatorFW4;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInsider;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerAdapter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerShare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerCancelShare;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChangeCusName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpdateCus;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAdapter;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShare;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelShare;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAddCommerceChance;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected ActionAddLinkman actionAddLinkman = null;
    protected ActionAddLine actionAddLine = null;
    protected ActionCustomerAdapter actionCustomerAdapter = null;
    protected ActionCustomerShare actionCustomerShare = null;
    protected ActionCustomerCancelShare actionCustomerCancelShare = null;
    protected ActionAddCommerceChance actionAddCommerceChance = null;
    protected ActionRemoveLinkman actionRemoveLinkman = null;
    protected ActionInsider actionInsider = null;
    protected ActionChangeCusName actionChangeCusName = null;
    protected ActionUpdateCus actionupdateCus = null;
    /**
     * output class constructor
     */
    public AbstractFDCCustomerEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCCustomerEditUI.class.getName());
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
        //actionChangeCusName
        this.actionChangeCusName = new ActionChangeCusName(this);
        getActionManager().registerAction("actionChangeCusName", actionChangeCusName);
         this.actionChangeCusName.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionupdateCus
        this.actionupdateCus = new ActionUpdateCus(this);
        getActionManager().registerAction("actionupdateCus", actionupdateCus);
         this.actionupdateCus.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tabNew = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.panelBaseInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contExtendInfo = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.panelQuestion = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelSale = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelTenancy = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelInsider = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelCusService = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelManager = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelChangeRecord = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.lcCustomerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcCustomerNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.lcCode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcSimCode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer7 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer8 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contNationality = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboCustomerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tblDistributing = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblRoomInfo = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.panelLinkman = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblCarInfo = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblCommerce = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblTrackRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblBankAccountInfo = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblLinkman = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.chkShowAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnAdd = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnDelete = new com.kingdee.bos.ctrl.swing.KDButton();
        this.txtCode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSimCode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.lcPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcOfficeTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcOtherTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcFax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcEmail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCertificateName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcCertificateNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDayOfbirth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSex = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCusStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCountry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProvince = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcMailAddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcCertificateAddr = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcPostalcode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRegion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFirstLinkman = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIndustry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnterpriceProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLegalPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLegalPersonTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOfficeTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOtherTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtFax = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEmail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Certificate = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtCertificateNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.dpDayOfbirth = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboSex = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7CusStatus = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Country = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Province = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtMailAddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCertificateAddr = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPostalcode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7City = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Area = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFirstLinkman = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Industry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7EnterpriceProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtLegalPerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLegalPersonTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.f7Nationality = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCustomerOrigin = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnterpriceSize = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCooperateMode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMarriage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHabitationInfo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFirstLinkType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCarCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWorkCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPersonArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCapital = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEmployeeCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaxNumG = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaxNumD = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7CustomerOrigin = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7EnterpriceSize = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7CooperateMode = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboMarriage = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7HabitationInfo = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboFirstLinkType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtCarCount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtWorkCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7PersonArea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtCapital = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDealArea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEmployeeCount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTaxNumG = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTaxNumD = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tblQuestion = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.containSaleRecord = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblSaleRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.containSaleCommerce = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblSaleCommerce = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.containTenRecord = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.containTenCommerce = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblTenancyRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblTenancyCommerce = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblInsider = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer4 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer5 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer6 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.specialServer = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.serverRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.complaintRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.houseProperty = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.arrearsRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.advanceReceipt = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblChangeLog = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contCreateUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateWay = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreateUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtLastUpdateUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboCreateWay = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnAddCommerceChance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separatorFW4 = new javax.swing.JToolBar.Separator();
        this.btnInsider = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerAdapter = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerCancelShare = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChangeCusName = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUpdateCus = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAdapter = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemShare = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCancelShare = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuAddCommerceChance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.tabNew.setName("tabNew");
        this.panelBaseInfo.setName("panelBaseInfo");
        this.contExtendInfo.setName("contExtendInfo");
        this.panelQuestion.setName("panelQuestion");
        this.panelSale.setName("panelSale");
        this.panelTenancy.setName("panelTenancy");
        this.panelInsider.setName("panelInsider");
        this.panelCusService.setName("panelCusService");
        this.panelManager.setName("panelManager");
        this.panelChangeRecord.setName("panelChangeRecord");
        this.lcCustomerType.setName("lcCustomerType");
        this.lcCustomerNumber.setName("lcCustomerNumber");
        this.lcName.setName("lcName");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.lcCode.setName("lcCode");
        this.lcSimCode.setName("lcSimCode");
        this.kDContainer7.setName("kDContainer7");
        this.kDContainer8.setName("kDContainer8");
        this.contNationality.setName("contNationality");
        this.comboCustomerType.setName("comboCustomerType");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.tblDistributing.setName("tblDistributing");
        this.tblRoomInfo.setName("tblRoomInfo");
        this.panelLinkman.setName("panelLinkman");
        this.tblCarInfo.setName("tblCarInfo");
        this.tblCommerce.setName("tblCommerce");
        this.tblTrackRecord.setName("tblTrackRecord");
        this.tblBankAccountInfo.setName("tblBankAccountInfo");
        this.tblLinkman.setName("tblLinkman");
        this.chkShowAll.setName("chkShowAll");
        this.btnAdd.setName("btnAdd");
        this.btnDelete.setName("btnDelete");
        this.txtCode.setName("txtCode");
        this.txtSimCode.setName("txtSimCode");
        this.lcPhone.setName("lcPhone");
        this.lcOfficeTel.setName("lcOfficeTel");
        this.lcOtherTel.setName("lcOtherTel");
        this.lcTel.setName("lcTel");
        this.lcFax.setName("lcFax");
        this.lcEmail.setName("lcEmail");
        this.contCertificateName.setName("contCertificateName");
        this.lcCertificateNumber.setName("lcCertificateNumber");
        this.contDayOfbirth.setName("contDayOfbirth");
        this.contSex.setName("contSex");
        this.contCusStatus.setName("contCusStatus");
        this.contCountry.setName("contCountry");
        this.contProvince.setName("contProvince");
        this.lcMailAddress.setName("lcMailAddress");
        this.lcCertificateAddr.setName("lcCertificateAddr");
        this.lcPostalcode.setName("lcPostalcode");
        this.contCity.setName("contCity");
        this.contRegion.setName("contRegion");
        this.contFirstLinkman.setName("contFirstLinkman");
        this.contIndustry.setName("contIndustry");
        this.contEnterpriceProperty.setName("contEnterpriceProperty");
        this.contLegalPerson.setName("contLegalPerson");
        this.contLegalPersonTel.setName("contLegalPersonTel");
        this.txtPhone.setName("txtPhone");
        this.txtOfficeTel.setName("txtOfficeTel");
        this.txtOtherTel.setName("txtOtherTel");
        this.txtTel.setName("txtTel");
        this.txtFax.setName("txtFax");
        this.txtEmail.setName("txtEmail");
        this.f7Certificate.setName("f7Certificate");
        this.txtCertificateNumber.setName("txtCertificateNumber");
        this.dpDayOfbirth.setName("dpDayOfbirth");
        this.comboSex.setName("comboSex");
        this.f7CusStatus.setName("f7CusStatus");
        this.f7Country.setName("f7Country");
        this.f7Province.setName("f7Province");
        this.txtMailAddress.setName("txtMailAddress");
        this.txtCertificateAddr.setName("txtCertificateAddr");
        this.txtPostalcode.setName("txtPostalcode");
        this.f7City.setName("f7City");
        this.f7Area.setName("f7Area");
        this.txtFirstLinkman.setName("txtFirstLinkman");
        this.f7Industry.setName("f7Industry");
        this.f7EnterpriceProperty.setName("f7EnterpriceProperty");
        this.txtLegalPerson.setName("txtLegalPerson");
        this.txtLegalPersonTel.setName("txtLegalPersonTel");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtDescription.setName("txtDescription");
        this.f7Nationality.setName("f7Nationality");
        this.contCustomerOrigin.setName("contCustomerOrigin");
        this.contEnterpriceSize.setName("contEnterpriceSize");
        this.contCooperateMode.setName("contCooperateMode");
        this.contMarriage.setName("contMarriage");
        this.contHabitationInfo.setName("contHabitationInfo");
        this.contFirstLinkType.setName("contFirstLinkType");
        this.contCarCount.setName("contCarCount");
        this.contWorkCompany.setName("contWorkCompany");
        this.contPersonArea.setName("contPersonArea");
        this.contCapital.setName("contCapital");
        this.contDealArea.setName("contDealArea");
        this.contEmployeeCount.setName("contEmployeeCount");
        this.contTaxNumG.setName("contTaxNumG");
        this.contTaxNumD.setName("contTaxNumD");
        this.f7CustomerOrigin.setName("f7CustomerOrigin");
        this.f7EnterpriceSize.setName("f7EnterpriceSize");
        this.f7CooperateMode.setName("f7CooperateMode");
        this.comboMarriage.setName("comboMarriage");
        this.f7HabitationInfo.setName("f7HabitationInfo");
        this.comboFirstLinkType.setName("comboFirstLinkType");
        this.txtCarCount.setName("txtCarCount");
        this.txtWorkCompany.setName("txtWorkCompany");
        this.f7PersonArea.setName("f7PersonArea");
        this.txtCapital.setName("txtCapital");
        this.txtDealArea.setName("txtDealArea");
        this.txtEmployeeCount.setName("txtEmployeeCount");
        this.txtTaxNumG.setName("txtTaxNumG");
        this.txtTaxNumD.setName("txtTaxNumD");
        this.tblQuestion.setName("tblQuestion");
        this.containSaleRecord.setName("containSaleRecord");
        this.tblSaleRecord.setName("tblSaleRecord");
        this.containSaleCommerce.setName("containSaleCommerce");
        this.tblSaleCommerce.setName("tblSaleCommerce");
        this.containTenRecord.setName("containTenRecord");
        this.containTenCommerce.setName("containTenCommerce");
        this.tblTenancyRecord.setName("tblTenancyRecord");
        this.tblTenancyCommerce.setName("tblTenancyCommerce");
        this.tblInsider.setName("tblInsider");
        this.kDContainer4.setName("kDContainer4");
        this.kDContainer5.setName("kDContainer5");
        this.kDContainer6.setName("kDContainer6");
        this.specialServer.setName("specialServer");
        this.serverRecord.setName("serverRecord");
        this.complaintRecord.setName("complaintRecord");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.kDContainer3.setName("kDContainer3");
        this.houseProperty.setName("houseProperty");
        this.arrearsRecord.setName("arrearsRecord");
        this.advanceReceipt.setName("advanceReceipt");
        this.tblChangeLog.setName("tblChangeLog");
        this.contCreateUnit.setName("contCreateUnit");
        this.contCreator.setName("contCreator");
        this.contLastUpdateUnit.setName("contLastUpdateUnit");
        this.contCreateWay.setName("contCreateWay");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.prmtCreateUnit.setName("prmtCreateUnit");
        this.prmtCreator.setName("prmtCreator");
        this.prmtLastUpdateUnit.setName("prmtLastUpdateUnit");
        this.comboCreateWay.setName("comboCreateWay");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.btnAddCommerceChance.setName("btnAddCommerceChance");
        this.btnAddLine.setName("btnAddLine");
        this.separatorFW4.setName("separatorFW4");
        this.btnInsider.setName("btnInsider");
        this.btnCustomerAdapter.setName("btnCustomerAdapter");
        this.btnCustomerShare.setName("btnCustomerShare");
        this.btnCustomerCancelShare.setName("btnCustomerCancelShare");
        this.btnChangeCusName.setName("btnChangeCusName");
        this.btnUpdateCus.setName("btnUpdateCus");
        this.menuItemAdapter.setName("menuItemAdapter");
        this.menuItemShare.setName("menuItemShare");
        this.menuItemCancelShare.setName("menuItemCancelShare");
        this.menuAddCommerceChance.setName("menuAddCommerceChance");
        // CoreUI		
        this.setPreferredSize(new Dimension(1023,653));
        // tabNew
        // panelBaseInfo		
        this.panelBaseInfo.setBorder(null);
        // contExtendInfo		
        this.contExtendInfo.setTitle(resHelper.getString("contExtendInfo.title"));
        // panelQuestion
        // panelSale
        // panelTenancy
        // panelInsider
        // panelCusService		
        this.panelCusService.setBorder(null);
        // panelManager
        // panelChangeRecord
        // lcCustomerType		
        this.lcCustomerType.setBoundLabelText(resHelper.getString("lcCustomerType.boundLabelText"));		
        this.lcCustomerType.setBoundLabelLength(100);		
        this.lcCustomerType.setBoundLabelUnderline(true);
        // lcCustomerNumber		
        this.lcCustomerNumber.setBoundLabelText(resHelper.getString("lcCustomerNumber.boundLabelText"));		
        this.lcCustomerNumber.setBoundLabelLength(100);		
        this.lcCustomerNumber.setBoundLabelUnderline(true);
        // lcName		
        this.lcName.setBoundLabelText(resHelper.getString("lcName.boundLabelText"));		
        this.lcName.setBoundLabelLength(100);		
        this.lcName.setBoundLabelUnderline(true);
        // kDTabbedPane1
        // lcCode		
        this.lcCode.setBoundLabelText(resHelper.getString("lcCode.boundLabelText"));		
        this.lcCode.setBoundLabelLength(100);		
        this.lcCode.setBoundLabelUnderline(true);
        // lcSimCode		
        this.lcSimCode.setBoundLabelText(resHelper.getString("lcSimCode.boundLabelText"));		
        this.lcSimCode.setBoundLabelLength(100);		
        this.lcSimCode.setBoundLabelUnderline(true);
        // kDContainer7		
        this.kDContainer7.setTitle(resHelper.getString("kDContainer7.title"));
        // kDContainer8		
        this.kDContainer8.setTitle(resHelper.getString("kDContainer8.title"));
        // contNationality		
        this.contNationality.setBoundLabelText(resHelper.getString("contNationality.boundLabelText"));		
        this.contNationality.setBoundLabelLength(100);		
        this.contNationality.setBoundLabelUnderline(true);
        // comboCustomerType		
        this.comboCustomerType.setEnabled(false);
        this.comboCustomerType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboCustomerType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setEnabled(false);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(80);
        // tblDistributing
		String tblDistributingStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"org\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"cusNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"cusName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"cusType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"firstLinkman\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"birthday\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"cusStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"mainAddr\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"certificateAddr\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdater\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{org}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{cusNumber}</t:Cell><t:Cell>$Resource{cusName}</t:Cell><t:Cell>$Resource{cusType}</t:Cell><t:Cell>$Resource{firstLinkman}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{birthday}</t:Cell><t:Cell>$Resource{sex}</t:Cell><t:Cell>$Resource{cusStatus}</t:Cell><t:Cell>$Resource{mainAddr}</t:Cell><t:Cell>$Resource{certificateAddr}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdater}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblDistributing.setFormatXml(resHelper.translateString("tblDistributing",tblDistributingStrXML));

        

        // tblRoomInfo
		String tblRoomInfoStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"sys\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tenancyArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"feeArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isFeeObj\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"signDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tenancyBeginDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tenancyEndDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"joinDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"outDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"feeBeginDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"feeEndDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{sys}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{buildArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{tenancyArea}</t:Cell><t:Cell>$Resource{feeArea}</t:Cell><t:Cell>$Resource{isFeeObj}</t:Cell><t:Cell>$Resource{signDate}</t:Cell><t:Cell>$Resource{tenancyBeginDate}</t:Cell><t:Cell>$Resource{tenancyEndDate}</t:Cell><t:Cell>$Resource{joinDate}</t:Cell><t:Cell>$Resource{outDate}</t:Cell><t:Cell>$Resource{feeBeginDate}</t:Cell><t:Cell>$Resource{feeEndDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblRoomInfo.setFormatXml(resHelper.translateString("tblRoomInfo",tblRoomInfoStrXML));

        

        // panelLinkman
        // tblCarInfo
		String tblCarInfoStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"carNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"carColor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"carType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commDirver\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"parkingNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"iDCard\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{carNum}</t:Cell><t:Cell>$Resource{carColor}</t:Cell><t:Cell>$Resource{carType}</t:Cell><t:Cell>$Resource{commDirver}</t:Cell><t:Cell>$Resource{parkingNum}</t:Cell><t:Cell>$Resource{iDCard}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblCarInfo.setFormatXml(resHelper.translateString("tblCarInfo",tblCarInfoStrXML));

        

        // tblCommerce
		String tblCommerceStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"sys\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"org\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"step\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"validDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"source\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"preValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dealProbability\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"curBelong\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{sys}</t:Cell><t:Cell>$Resource{org}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{step}</t:Cell><t:Cell>$Resource{validDate}</t:Cell><t:Cell>$Resource{source}</t:Cell><t:Cell>$Resource{preValue}</t:Cell><t:Cell>$Resource{dealProbability}</t:Cell><t:Cell>$Resource{curBelong}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblCommerce.setFormatXml(resHelper.translateString("tblCommerce",tblCommerceStrXML));

        

        // tblTrackRecord
		String tblTrackRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"date\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"event\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"step\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"saleman\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{event}</t:Cell><t:Cell>$Resource{step}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{saleman}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

        

        // tblBankAccountInfo
		String tblBankAccountInfoStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"owner\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bank\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bankNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"accountType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"account\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isEnabled\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{owner}</t:Cell><t:Cell>$Resource{bank}</t:Cell><t:Cell>$Resource{bankNum}</t:Cell><t:Cell>$Resource{accountType}</t:Cell><t:Cell>$Resource{account}</t:Cell><t:Cell>$Resource{isEnabled}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblBankAccountInfo.setFormatXml(resHelper.translateString("tblBankAccountInfo",tblBankAccountInfoStrXML));

        

        // tblLinkman
		String tblLinkmanStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"isAssociation\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"associationType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"isEmergencyContact\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"isPrincipal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"principalNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"relation\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"sex\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"phone\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"tel\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"officeTel\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"fax\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"otherTel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"email\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"certificateType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"certificateNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dayOfBirth\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"mailAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bookedAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"postalCode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{isAssociation}</t:Cell><t:Cell>$Resource{associationType}</t:Cell><t:Cell>$Resource{isEmergencyContact}</t:Cell><t:Cell>$Resource{isPrincipal}</t:Cell><t:Cell>$Resource{principalNo}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{relation}</t:Cell><t:Cell>$Resource{sex}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{tel}</t:Cell><t:Cell>$Resource{officeTel}</t:Cell><t:Cell>$Resource{fax}</t:Cell><t:Cell>$Resource{otherTel}</t:Cell><t:Cell>$Resource{email}</t:Cell><t:Cell>$Resource{certificateType}</t:Cell><t:Cell>$Resource{certificateNumber}</t:Cell><t:Cell>$Resource{dayOfBirth}</t:Cell><t:Cell>$Resource{mailAddress}</t:Cell><t:Cell>$Resource{bookedAddress}</t:Cell><t:Cell>$Resource{postalCode}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblLinkman.setFormatXml(resHelper.translateString("tblLinkman",tblLinkmanStrXML));
        this.tblLinkman.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblLinkman_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblLinkman.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblLinkman_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // chkShowAll		
        this.chkShowAll.setText(resHelper.getString("chkShowAll.text"));
        this.chkShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkShowAll_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAdd		
        this.btnAdd.setText(resHelper.getString("btnAdd.text"));
        this.btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAdd_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelete		
        this.btnDelete.setText(resHelper.getString("btnDelete.text"));
        this.btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelete_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtCode		
        this.txtCode.setMaxLength(80);
        // txtSimCode		
        this.txtSimCode.setMaxLength(80);
        // lcPhone		
        this.lcPhone.setBoundLabelText(resHelper.getString("lcPhone.boundLabelText"));		
        this.lcPhone.setBoundLabelLength(100);		
        this.lcPhone.setBoundLabelUnderline(true);
        // lcOfficeTel		
        this.lcOfficeTel.setBoundLabelText(resHelper.getString("lcOfficeTel.boundLabelText"));		
        this.lcOfficeTel.setBoundLabelLength(100);		
        this.lcOfficeTel.setBoundLabelUnderline(true);
        // lcOtherTel		
        this.lcOtherTel.setBoundLabelText(resHelper.getString("lcOtherTel.boundLabelText"));		
        this.lcOtherTel.setBoundLabelLength(100);		
        this.lcOtherTel.setBoundLabelUnderline(true);
        // lcTel		
        this.lcTel.setBoundLabelText(resHelper.getString("lcTel.boundLabelText"));		
        this.lcTel.setBoundLabelLength(100);		
        this.lcTel.setBoundLabelUnderline(true);
        // lcFax		
        this.lcFax.setBoundLabelText(resHelper.getString("lcFax.boundLabelText"));		
        this.lcFax.setBoundLabelLength(100);		
        this.lcFax.setBoundLabelUnderline(true);
        // lcEmail		
        this.lcEmail.setBoundLabelText(resHelper.getString("lcEmail.boundLabelText"));		
        this.lcEmail.setBoundLabelUnderline(true);		
        this.lcEmail.setBoundLabelLength(100);
        // contCertificateName		
        this.contCertificateName.setBoundLabelText(resHelper.getString("contCertificateName.boundLabelText"));		
        this.contCertificateName.setBoundLabelLength(100);		
        this.contCertificateName.setBoundLabelUnderline(true);
        // lcCertificateNumber		
        this.lcCertificateNumber.setBoundLabelText(resHelper.getString("lcCertificateNumber.boundLabelText"));		
        this.lcCertificateNumber.setBoundLabelLength(100);		
        this.lcCertificateNumber.setBoundLabelUnderline(true);
        // contDayOfbirth		
        this.contDayOfbirth.setBoundLabelText(resHelper.getString("contDayOfbirth.boundLabelText"));		
        this.contDayOfbirth.setBoundLabelLength(100);		
        this.contDayOfbirth.setBoundLabelUnderline(true);
        // contSex		
        this.contSex.setBoundLabelText(resHelper.getString("contSex.boundLabelText"));		
        this.contSex.setBoundLabelUnderline(true);		
        this.contSex.setBoundLabelLength(100);
        // contCusStatus		
        this.contCusStatus.setBoundLabelText(resHelper.getString("contCusStatus.boundLabelText"));		
        this.contCusStatus.setBoundLabelLength(100);		
        this.contCusStatus.setBoundLabelUnderline(true);
        // contCountry		
        this.contCountry.setBoundLabelText(resHelper.getString("contCountry.boundLabelText"));		
        this.contCountry.setBoundLabelLength(100);		
        this.contCountry.setBoundLabelUnderline(true);
        // contProvince		
        this.contProvince.setBoundLabelText(resHelper.getString("contProvince.boundLabelText"));		
        this.contProvince.setBoundLabelLength(100);		
        this.contProvince.setBoundLabelUnderline(true);
        // lcMailAddress		
        this.lcMailAddress.setBoundLabelText(resHelper.getString("lcMailAddress.boundLabelText"));		
        this.lcMailAddress.setBoundLabelLength(100);		
        this.lcMailAddress.setBoundLabelUnderline(true);
        // lcCertificateAddr		
        this.lcCertificateAddr.setBoundLabelText(resHelper.getString("lcCertificateAddr.boundLabelText"));		
        this.lcCertificateAddr.setBoundLabelLength(100);		
        this.lcCertificateAddr.setBoundLabelUnderline(true);
        // lcPostalcode		
        this.lcPostalcode.setBoundLabelText(resHelper.getString("lcPostalcode.boundLabelText"));		
        this.lcPostalcode.setBoundLabelUnderline(true);		
        this.lcPostalcode.setBoundLabelLength(100);
        // contCity		
        this.contCity.setBoundLabelText(resHelper.getString("contCity.boundLabelText"));		
        this.contCity.setBoundLabelLength(100);		
        this.contCity.setBoundLabelUnderline(true);
        // contRegion		
        this.contRegion.setBoundLabelText(resHelper.getString("contRegion.boundLabelText"));		
        this.contRegion.setBoundLabelLength(100);		
        this.contRegion.setBoundLabelUnderline(true);
        // contFirstLinkman		
        this.contFirstLinkman.setBoundLabelText(resHelper.getString("contFirstLinkman.boundLabelText"));		
        this.contFirstLinkman.setBoundLabelLength(100);		
        this.contFirstLinkman.setBoundLabelUnderline(true);
        // contIndustry		
        this.contIndustry.setBoundLabelText(resHelper.getString("contIndustry.boundLabelText"));		
        this.contIndustry.setBoundLabelLength(100);		
        this.contIndustry.setBoundLabelUnderline(true);
        // contEnterpriceProperty		
        this.contEnterpriceProperty.setBoundLabelText(resHelper.getString("contEnterpriceProperty.boundLabelText"));		
        this.contEnterpriceProperty.setBoundLabelLength(100);		
        this.contEnterpriceProperty.setBoundLabelUnderline(true);
        // contLegalPerson		
        this.contLegalPerson.setBoundLabelText(resHelper.getString("contLegalPerson.boundLabelText"));		
        this.contLegalPerson.setBoundLabelUnderline(true);		
        this.contLegalPerson.setBoundLabelLength(100);
        // contLegalPersonTel		
        this.contLegalPersonTel.setBoundLabelText(resHelper.getString("contLegalPersonTel.boundLabelText"));		
        this.contLegalPersonTel.setBoundLabelUnderline(true);		
        this.contLegalPersonTel.setBoundLabelLength(100);
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
        // txtOfficeTel		
        this.txtOfficeTel.setMaxLength(80);
        // txtOtherTel		
        this.txtOtherTel.setMaxLength(80);
        // txtTel		
        this.txtTel.setMaxLength(80);
        // txtFax		
        this.txtFax.setMaxLength(80);
        // txtEmail		
        this.txtEmail.setMaxLength(80);
        // f7Certificate		
        this.f7Certificate.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
        // txtCertificateNumber		
        this.txtCertificateNumber.setMaxLength(80);
        this.txtCertificateNumber.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    txtCertificateNumber_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtCertificateNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtCertificateNumber_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // dpDayOfbirth
        // comboSex		
        this.comboSex.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SexEnum").toArray());		
        this.comboSex.setSelectedIndex(0);
        // f7CusStatus		
        this.f7CusStatus.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");		
        this.f7CusStatus.setEditFormat("$number$");		
        this.f7CusStatus.setDisplayFormat("$name$");		
        this.f7CusStatus.setCommitFormat("$number$");
        // f7Country		
        this.f7Country.setDisplayFormat("$name$");		
        this.f7Country.setEditFormat("$number$");		
        this.f7Country.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CountryQuery");		
        this.f7Country.setCommitFormat("$number$");
        this.f7Country.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Country_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7Province		
        this.f7Province.setQueryInfo("com.kingdee.eas.basedata.assistant.app.ProvinceQuery");		
        this.f7Province.setDisplayFormat("$name$");		
        this.f7Province.setEditFormat("$number$");		
        this.f7Province.setCommitFormat("$number$");
        this.f7Province.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Province_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtMailAddress		
        this.txtMailAddress.setMaxLength(80);
        // txtCertificateAddr		
        this.txtCertificateAddr.setMaxLength(80);
        // txtPostalcode		
        this.txtPostalcode.setMaxLength(80);
        // f7City		
        this.f7City.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CityQuery");		
        this.f7City.setDisplayFormat("$name$");		
        this.f7City.setEditFormat("$number$");		
        this.f7City.setCommitFormat("$number$");
        this.f7City.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7City_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7Area		
        this.f7Area.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7RegionQuery");		
        this.f7Area.setDisplayFormat("$name$");		
        this.f7Area.setEditFormat("$number$");		
        this.f7Area.setCommitFormat("$number$");
        this.f7Area.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Area_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtFirstLinkman		
        this.txtFirstLinkman.setMaxLength(80);
        // f7Industry		
        this.f7Industry.setQueryInfo("com.kingdee.eas.basedata.assistant.app.IndustryQuery");		
        this.f7Industry.setCommitFormat("$number$");		
        this.f7Industry.setDisplayFormat("$name$");		
        this.f7Industry.setEditFormat("$number$");		
        this.f7Industry.setEditable(true);
        // f7EnterpriceProperty		
        this.f7EnterpriceProperty.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");		
        this.f7EnterpriceProperty.setCommitFormat("$number$");		
        this.f7EnterpriceProperty.setDisplayFormat("$name$");		
        this.f7EnterpriceProperty.setEditFormat("$number$");
        // txtLegalPerson		
        this.txtLegalPerson.setMaxLength(80);
        // txtLegalPersonTel		
        this.txtLegalPersonTel.setMaxLength(80);
        // kDScrollPane2
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // f7Nationality		
        this.f7Nationality.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CountryQuery");
        // contCustomerOrigin		
        this.contCustomerOrigin.setBoundLabelText(resHelper.getString("contCustomerOrigin.boundLabelText"));		
        this.contCustomerOrigin.setBoundLabelUnderline(true);		
        this.contCustomerOrigin.setBoundLabelLength(100);
        // contEnterpriceSize		
        this.contEnterpriceSize.setBoundLabelText(resHelper.getString("contEnterpriceSize.boundLabelText"));		
        this.contEnterpriceSize.setBoundLabelLength(100);		
        this.contEnterpriceSize.setBoundLabelUnderline(true);
        // contCooperateMode		
        this.contCooperateMode.setBoundLabelText(resHelper.getString("contCooperateMode.boundLabelText"));		
        this.contCooperateMode.setBoundLabelLength(100);		
        this.contCooperateMode.setBoundLabelUnderline(true);
        // contMarriage		
        this.contMarriage.setBoundLabelText(resHelper.getString("contMarriage.boundLabelText"));		
        this.contMarriage.setBoundLabelLength(100);		
        this.contMarriage.setBoundLabelUnderline(true);
        // contHabitationInfo		
        this.contHabitationInfo.setBoundLabelText(resHelper.getString("contHabitationInfo.boundLabelText"));		
        this.contHabitationInfo.setBoundLabelLength(100);		
        this.contHabitationInfo.setBoundLabelUnderline(true);
        // contFirstLinkType		
        this.contFirstLinkType.setBoundLabelText(resHelper.getString("contFirstLinkType.boundLabelText"));		
        this.contFirstLinkType.setBoundLabelLength(100);		
        this.contFirstLinkType.setBoundLabelUnderline(true);
        // contCarCount		
        this.contCarCount.setBoundLabelText(resHelper.getString("contCarCount.boundLabelText"));		
        this.contCarCount.setBoundLabelLength(100);		
        this.contCarCount.setBoundLabelUnderline(true);
        // contWorkCompany		
        this.contWorkCompany.setBoundLabelText(resHelper.getString("contWorkCompany.boundLabelText"));		
        this.contWorkCompany.setBoundLabelLength(100);		
        this.contWorkCompany.setBoundLabelUnderline(true);
        // contPersonArea		
        this.contPersonArea.setBoundLabelText(resHelper.getString("contPersonArea.boundLabelText"));		
        this.contPersonArea.setBoundLabelLength(100);		
        this.contPersonArea.setBoundLabelUnderline(true);
        // contCapital		
        this.contCapital.setBoundLabelText(resHelper.getString("contCapital.boundLabelText"));		
        this.contCapital.setBoundLabelLength(100);		
        this.contCapital.setBoundLabelUnderline(true);
        // contDealArea		
        this.contDealArea.setBoundLabelText(resHelper.getString("contDealArea.boundLabelText"));		
        this.contDealArea.setBoundLabelLength(100);		
        this.contDealArea.setBoundLabelUnderline(true);
        // contEmployeeCount		
        this.contEmployeeCount.setBoundLabelText(resHelper.getString("contEmployeeCount.boundLabelText"));		
        this.contEmployeeCount.setBoundLabelLength(100);		
        this.contEmployeeCount.setBoundLabelUnderline(true);
        // contTaxNumG		
        this.contTaxNumG.setBoundLabelText(resHelper.getString("contTaxNumG.boundLabelText"));		
        this.contTaxNumG.setBoundLabelLength(100);		
        this.contTaxNumG.setBoundLabelUnderline(true);
        // contTaxNumD		
        this.contTaxNumD.setBoundLabelText(resHelper.getString("contTaxNumD.boundLabelText"));		
        this.contTaxNumD.setBoundLabelLength(100);		
        this.contTaxNumD.setBoundLabelUnderline(true);
        // f7CustomerOrigin		
        this.f7CustomerOrigin.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");		
        this.f7CustomerOrigin.setDisplayFormat("$name$");		
        this.f7CustomerOrigin.setEditFormat("$number$");		
        this.f7CustomerOrigin.setCommitFormat("$number$");
        // f7EnterpriceSize		
        this.f7EnterpriceSize.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");		
        this.f7EnterpriceSize.setCommitFormat("$number$");		
        this.f7EnterpriceSize.setDisplayFormat("$name$");		
        this.f7EnterpriceSize.setEditFormat("$number$");
        // f7CooperateMode		
        this.f7CooperateMode.setDisplayFormat("$name$");		
        this.f7CooperateMode.setEditFormat("$number$");		
        this.f7CooperateMode.setCommitFormat("$number$");		
        this.f7CooperateMode.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
        // comboMarriage
        // f7HabitationInfo		
        this.f7HabitationInfo.setDisplayFormat("$name$");		
        this.f7HabitationInfo.setEditFormat("$number$");		
        this.f7HabitationInfo.setCommitFormat("$number$");		
        this.f7HabitationInfo.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
        // comboFirstLinkType
        // txtCarCount		
        this.txtCarCount.setMaxLength(80);
        // txtWorkCompany		
        this.txtWorkCompany.setMaxLength(80);
        // f7PersonArea		
        this.f7PersonArea.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7RegionQuery");		
        this.f7PersonArea.setEditFormat("$number$");		
        this.f7PersonArea.setDisplayFormat("$name$");		
        this.f7PersonArea.setCommitFormat("$number$");
        // txtCapital		
        this.txtCapital.setMaxLength(80);
        // txtDealArea		
        this.txtDealArea.setMaxLength(80);
        // txtEmployeeCount		
        this.txtEmployeeCount.setMaxLength(80);
        // txtTaxNumG		
        this.txtTaxNumG.setMaxLength(80);
        // txtTaxNumD		
        this.txtTaxNumD.setMaxLength(80);
        // tblQuestion
		String tblQuestionStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"org\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizScene\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fillInDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creaotr\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateUser\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{org}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{bizScene}</t:Cell><t:Cell>$Resource{fillInDate}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{creaotr}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdateUser}</t:Cell><t:Cell>$Resource{lastUpdateDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

        

        // containSaleRecord		
        this.containSaleRecord.setTitle(resHelper.getString("containSaleRecord.title"));		
        this.containSaleRecord.setTitleStyle(2);		
        this.containSaleRecord.setEnableActive(false);
        // tblSaleRecord
		String tblSaleRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol5\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"org\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"billNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"payType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"agio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"dealTolAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"dealBuildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dealRoomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saleman\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{org}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{billNumber}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{payType}</t:Cell><t:Cell>$Resource{agio}</t:Cell><t:Cell>$Resource{dealTolAmount}</t:Cell><t:Cell>$Resource{dealBuildingPrice}</t:Cell><t:Cell>$Resource{dealRoomPrice}</t:Cell><t:Cell>$Resource{saleman}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

        

        // containSaleCommerce		
        this.containSaleCommerce.setTitleStyle(2);		
        this.containSaleCommerce.setTitle(resHelper.getString("containSaleCommerce.title"));		
        this.containSaleCommerce.setPreferredSize(new Dimension(19,255));		
        this.containSaleCommerce.setEnableActive(false);
        // tblSaleCommerce
		String tblSaleCommerceStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"oldTolAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"newTolAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"oldCustomer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"newCustomer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"oldRoom\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"newRoom\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"changer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"changeDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{oldTolAmount}</t:Cell><t:Cell>$Resource{newTolAmount}</t:Cell><t:Cell>$Resource{oldCustomer}</t:Cell><t:Cell>$Resource{newCustomer}</t:Cell><t:Cell>$Resource{oldRoom}</t:Cell><t:Cell>$Resource{newRoom}</t:Cell><t:Cell>$Resource{changer}</t:Cell><t:Cell>$Resource{changeDate}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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
		String tblTenancyCommerceStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sysType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sellProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saleMan.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceLevel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fdcCustomer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{sysType}</t:Cell><t:Cell>$Resource{sellProject.name}</t:Cell><t:Cell>$Resource{saleMan.name}</t:Cell><t:Cell>$Resource{commerceLevel.name}</t:Cell><t:Cell>$Resource{commerceStatus}</t:Cell><t:Cell>$Resource{fdcCustomer.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
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

        

        // tblInsider
		String tblInsiderStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"insider\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"insiderCode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"vipLevel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"requestDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"insiderRemove\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"insiderHortation\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{insider}</t:Cell><t:Cell>$Resource{insiderCode}</t:Cell><t:Cell>$Resource{vipLevel}</t:Cell><t:Cell>$Resource{requestDate}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{insiderRemove}</t:Cell><t:Cell>$Resource{insiderHortation}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblInsider.setFormatXml(resHelper.translateString("tblInsider",tblInsiderStrXML));

        

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

        

        // tblChangeLog
		String tblChangeLogStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"oldInfo\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"newInfo\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"changeDate\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"changer\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"org\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{oldInfo}</t:Cell><t:Cell>$Resource{newInfo}</t:Cell><t:Cell>$Resource{changeDate}</t:Cell><t:Cell>$Resource{changer}</t:Cell><t:Cell>$Resource{org}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblChangeLog.setFormatXml(resHelper.translateString("tblChangeLog",tblChangeLogStrXML));

        

        // contCreateUnit		
        this.contCreateUnit.setBoundLabelText(resHelper.getString("contCreateUnit.boundLabelText"));		
        this.contCreateUnit.setBoundLabelLength(100);		
        this.contCreateUnit.setBoundLabelUnderline(true);		
        this.contCreateUnit.setEnabled(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contLastUpdateUnit		
        this.contLastUpdateUnit.setBoundLabelText(resHelper.getString("contLastUpdateUnit.boundLabelText"));		
        this.contLastUpdateUnit.setBoundLabelLength(100);		
        this.contLastUpdateUnit.setBoundLabelUnderline(true);		
        this.contLastUpdateUnit.setEnabled(false);
        // contCreateWay		
        this.contCreateWay.setBoundLabelText(resHelper.getString("contCreateWay.boundLabelText"));		
        this.contCreateWay.setBoundLabelLength(100);		
        this.contCreateWay.setBoundLabelUnderline(true);		
        this.contCreateWay.setEnabled(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);
        // prmtCreateUnit		
        this.prmtCreateUnit.setEnabled(false);		
        this.prmtCreateUnit.setDisplayFormat("$name$");		
        this.prmtCreateUnit.setEditFormat("$number$");		
        this.prmtCreateUnit.setCommitFormat("$number$");
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setCommitFormat("$number$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setDisplayFormat("$name$");
        // prmtLastUpdateUnit		
        this.prmtLastUpdateUnit.setEnabled(false);		
        this.prmtLastUpdateUnit.setCommitFormat("$number$");		
        this.prmtLastUpdateUnit.setEditFormat("$number$");		
        this.prmtLastUpdateUnit.setDisplayFormat("$name$");
        // comboCreateWay		
        this.comboCreateWay.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basecrm.CreateWayEnum").toArray());		
        this.comboCreateWay.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);		
        this.prmtLastUpdateUser.setCommitFormat("$number$");		
        this.prmtLastUpdateUser.setEditFormat("$number$");		
        this.prmtLastUpdateUser.setDisplayFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // pkLastUpdateTime		
        this.pkLastUpdateTime.setEnabled(false);
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
        // btnChangeCusName
        this.btnChangeCusName.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeCusName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChangeCusName.setText(resHelper.getString("btnChangeCusName.text"));		
        this.btnChangeCusName.setToolTipText(resHelper.getString("btnChangeCusName.toolTipText"));
        // btnUpdateCus
        this.btnUpdateCus.setAction((IItemAction)ActionProxyFactory.getProxy(actionupdateCus, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpdateCus.setText(resHelper.getString("btnUpdateCus.text"));		
        this.btnUpdateCus.setToolTipText(resHelper.getString("btnUpdateCus.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 1023, 653));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1023, 653));
        tabNew.setBounds(new Rectangle(10, 10, 1013, 637));
        this.add(tabNew, new KDLayout.Constraints(10, 10, 1013, 637, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //tabNew
        tabNew.add(panelBaseInfo, resHelper.getString("panelBaseInfo.constraints"));
        tabNew.add(contExtendInfo, resHelper.getString("contExtendInfo.constraints"));
        tabNew.add(panelQuestion, resHelper.getString("panelQuestion.constraints"));
        tabNew.add(panelSale, resHelper.getString("panelSale.constraints"));
        tabNew.add(panelTenancy, resHelper.getString("panelTenancy.constraints"));
        tabNew.add(panelInsider, resHelper.getString("panelInsider.constraints"));
        tabNew.add(panelCusService, resHelper.getString("panelCusService.constraints"));
        tabNew.add(panelManager, resHelper.getString("panelManager.constraints"));
        tabNew.add(panelChangeRecord, resHelper.getString("panelChangeRecord.constraints"));
        //panelBaseInfo
        panelBaseInfo.setLayout(new KDLayout());
        panelBaseInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1012, 604));        lcCustomerType.setBounds(new Rectangle(380, 10, 270, 19));
        panelBaseInfo.add(lcCustomerType, new KDLayout.Constraints(380, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcCustomerNumber.setBounds(new Rectangle(10, 10, 270, 19));
        panelBaseInfo.add(lcCustomerNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcName.setBounds(new Rectangle(730, 10, 270, 19));
        panelBaseInfo.add(lcName, new KDLayout.Constraints(730, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane1.setBounds(new Rectangle(0, 421, 1015, 181));
        panelBaseInfo.add(kDTabbedPane1, new KDLayout.Constraints(0, 421, 1015, 181, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcCode.setBounds(new Rectangle(380, 40, 270, 19));
        panelBaseInfo.add(lcCode, new KDLayout.Constraints(380, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcSimCode.setBounds(new Rectangle(730, 40, 270, 19));
        panelBaseInfo.add(lcSimCode, new KDLayout.Constraints(730, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer7.setBounds(new Rectangle(0, 65, 1015, 240));
        panelBaseInfo.add(kDContainer7, new KDLayout.Constraints(0, 65, 1015, 240, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer8.setBounds(new Rectangle(0, 312, 1015, 101));
        panelBaseInfo.add(kDContainer8, new KDLayout.Constraints(0, 312, 1015, 101, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNationality.setBounds(new Rectangle(10, 40, 270, 19));
        panelBaseInfo.add(contNationality, new KDLayout.Constraints(10, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //lcCustomerType
        lcCustomerType.setBoundEditor(comboCustomerType);
        //lcCustomerNumber
        lcCustomerNumber.setBoundEditor(txtNumber);
        //lcName
        lcName.setBoundEditor(txtName);
        //kDTabbedPane1
        kDTabbedPane1.add(tblDistributing, resHelper.getString("tblDistributing.constraints"));
        kDTabbedPane1.add(tblRoomInfo, resHelper.getString("tblRoomInfo.constraints"));
        kDTabbedPane1.add(panelLinkman, resHelper.getString("panelLinkman.constraints"));
        kDTabbedPane1.add(tblCarInfo, resHelper.getString("tblCarInfo.constraints"));
        kDTabbedPane1.add(tblCommerce, resHelper.getString("tblCommerce.constraints"));
        kDTabbedPane1.add(tblTrackRecord, resHelper.getString("tblTrackRecord.constraints"));
        kDTabbedPane1.add(tblBankAccountInfo, resHelper.getString("tblBankAccountInfo.constraints"));
        //panelLinkman
        panelLinkman.setLayout(new KDLayout());
        panelLinkman.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1014, 148));        tblLinkman.setBounds(new Rectangle(-1, 32, 1011, 116));
        panelLinkman.add(tblLinkman, new KDLayout.Constraints(-1, 32, 1011, 116, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkShowAll.setBounds(new Rectangle(692, 9, 96, 19));
        panelLinkman.add(chkShowAll, new KDLayout.Constraints(692, 9, 96, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAdd.setBounds(new Rectangle(795, 6, 97, 21));
        panelLinkman.add(btnAdd, new KDLayout.Constraints(795, 6, 97, 21, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnDelete.setBounds(new Rectangle(903, 6, 96, 21));
        panelLinkman.add(btnDelete, new KDLayout.Constraints(903, 6, 96, 21, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //lcCode
        lcCode.setBoundEditor(txtCode);
        //lcSimCode
        lcSimCode.setBoundEditor(txtSimCode);
        //kDContainer7
        kDContainer7.getContentPane().setLayout(new KDLayout());
        kDContainer7.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 65, 1015, 240));        lcPhone.setBounds(new Rectangle(10, 40, 270, 19));
        kDContainer7.getContentPane().add(lcPhone, new KDLayout.Constraints(10, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcOfficeTel.setBounds(new Rectangle(730, 40, 270, 19));
        kDContainer7.getContentPane().add(lcOfficeTel, new KDLayout.Constraints(730, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcOtherTel.setBounds(new Rectangle(380, 70, 270, 19));
        kDContainer7.getContentPane().add(lcOtherTel, new KDLayout.Constraints(380, 70, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcTel.setBounds(new Rectangle(380, 40, 270, 19));
        kDContainer7.getContentPane().add(lcTel, new KDLayout.Constraints(380, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcFax.setBounds(new Rectangle(10, 70, 270, 19));
        kDContainer7.getContentPane().add(lcFax, new KDLayout.Constraints(10, 70, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcEmail.setBounds(new Rectangle(730, 70, 270, 19));
        kDContainer7.getContentPane().add(lcEmail, new KDLayout.Constraints(730, 70, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCertificateName.setBounds(new Rectangle(10, 100, 270, 19));
        kDContainer7.getContentPane().add(contCertificateName, new KDLayout.Constraints(10, 100, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcCertificateNumber.setBounds(new Rectangle(380, 100, 270, 19));
        kDContainer7.getContentPane().add(lcCertificateNumber, new KDLayout.Constraints(380, 100, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDayOfbirth.setBounds(new Rectangle(730, 100, 270, 19));
        kDContainer7.getContentPane().add(contDayOfbirth, new KDLayout.Constraints(730, 100, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSex.setBounds(new Rectangle(10, 130, 270, 19));
        kDContainer7.getContentPane().add(contSex, new KDLayout.Constraints(10, 130, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCusStatus.setBounds(new Rectangle(380, 130, 270, 19));
        kDContainer7.getContentPane().add(contCusStatus, new KDLayout.Constraints(380, 130, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCountry.setBounds(new Rectangle(730, 130, 270, 19));
        kDContainer7.getContentPane().add(contCountry, new KDLayout.Constraints(730, 130, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProvince.setBounds(new Rectangle(10, 160, 270, 19));
        kDContainer7.getContentPane().add(contProvince, new KDLayout.Constraints(10, 160, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcMailAddress.setBounds(new Rectangle(10, 190, 270, 19));
        kDContainer7.getContentPane().add(lcMailAddress, new KDLayout.Constraints(10, 190, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcCertificateAddr.setBounds(new Rectangle(380, 190, 270, 19));
        kDContainer7.getContentPane().add(lcCertificateAddr, new KDLayout.Constraints(380, 190, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcPostalcode.setBounds(new Rectangle(730, 190, 270, 19));
        kDContainer7.getContentPane().add(lcPostalcode, new KDLayout.Constraints(730, 190, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCity.setBounds(new Rectangle(380, 160, 270, 19));
        kDContainer7.getContentPane().add(contCity, new KDLayout.Constraints(380, 160, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRegion.setBounds(new Rectangle(730, 160, 270, 19));
        kDContainer7.getContentPane().add(contRegion, new KDLayout.Constraints(730, 160, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFirstLinkman.setBounds(new Rectangle(10, 10, 270, 19));
        kDContainer7.getContentPane().add(contFirstLinkman, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIndustry.setBounds(new Rectangle(380, 10, 270, 19));
        kDContainer7.getContentPane().add(contIndustry, new KDLayout.Constraints(380, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEnterpriceProperty.setBounds(new Rectangle(730, 10, 270, 19));
        kDContainer7.getContentPane().add(contEnterpriceProperty, new KDLayout.Constraints(730, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLegalPerson.setBounds(new Rectangle(10, 130, 270, 19));
        kDContainer7.getContentPane().add(contLegalPerson, new KDLayout.Constraints(10, 130, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLegalPersonTel.setBounds(new Rectangle(380, 130, 270, 19));
        kDContainer7.getContentPane().add(contLegalPersonTel, new KDLayout.Constraints(380, 130, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //lcPhone
        lcPhone.setBoundEditor(txtPhone);
        //lcOfficeTel
        lcOfficeTel.setBoundEditor(txtOfficeTel);
        //lcOtherTel
        lcOtherTel.setBoundEditor(txtOtherTel);
        //lcTel
        lcTel.setBoundEditor(txtTel);
        //lcFax
        lcFax.setBoundEditor(txtFax);
        //lcEmail
        lcEmail.setBoundEditor(txtEmail);
        //contCertificateName
        contCertificateName.setBoundEditor(f7Certificate);
        //lcCertificateNumber
        lcCertificateNumber.setBoundEditor(txtCertificateNumber);
        //contDayOfbirth
        contDayOfbirth.setBoundEditor(dpDayOfbirth);
        //contSex
        contSex.setBoundEditor(comboSex);
        //contCusStatus
        contCusStatus.setBoundEditor(f7CusStatus);
        //contCountry
        contCountry.setBoundEditor(f7Country);
        //contProvince
        contProvince.setBoundEditor(f7Province);
        //lcMailAddress
        lcMailAddress.setBoundEditor(txtMailAddress);
        //lcCertificateAddr
        lcCertificateAddr.setBoundEditor(txtCertificateAddr);
        //lcPostalcode
        lcPostalcode.setBoundEditor(txtPostalcode);
        //contCity
        contCity.setBoundEditor(f7City);
        //contRegion
        contRegion.setBoundEditor(f7Area);
        //contFirstLinkman
        contFirstLinkman.setBoundEditor(txtFirstLinkman);
        //contIndustry
        contIndustry.setBoundEditor(f7Industry);
        //contEnterpriceProperty
        contEnterpriceProperty.setBoundEditor(f7EnterpriceProperty);
        //contLegalPerson
        contLegalPerson.setBoundEditor(txtLegalPerson);
        //contLegalPersonTel
        contLegalPersonTel.setBoundEditor(txtLegalPersonTel);
        //kDContainer8
        kDContainer8.getContentPane().setLayout(new KDLayout());
        kDContainer8.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 312, 1015, 101));        kDScrollPane2.setBounds(new Rectangle(0, 0, 1015, 76));
        kDContainer8.getContentPane().add(kDScrollPane2, new KDLayout.Constraints(0, 0, 1015, 76, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtDescription, null);
        //contNationality
        contNationality.setBoundEditor(f7Nationality);
        //contExtendInfo
        contExtendInfo.getContentPane().setLayout(new KDLayout());
        contExtendInfo.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 1012, 604));        contCustomerOrigin.setBounds(new Rectangle(10, 10, 270, 19));
        contExtendInfo.getContentPane().add(contCustomerOrigin, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEnterpriceSize.setBounds(new Rectangle(10, 10, 270, 19));
        contExtendInfo.getContentPane().add(contEnterpriceSize, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCooperateMode.setBounds(new Rectangle(10, 70, 270, 19));
        contExtendInfo.getContentPane().add(contCooperateMode, new KDLayout.Constraints(10, 70, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMarriage.setBounds(new Rectangle(731, 10, 270, 19));
        contExtendInfo.getContentPane().add(contMarriage, new KDLayout.Constraints(731, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHabitationInfo.setBounds(new Rectangle(10, 40, 270, 19));
        contExtendInfo.getContentPane().add(contHabitationInfo, new KDLayout.Constraints(10, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFirstLinkType.setBounds(new Rectangle(731, 40, 270, 19));
        contExtendInfo.getContentPane().add(contFirstLinkType, new KDLayout.Constraints(731, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCarCount.setBounds(new Rectangle(10, 70, 270, 19));
        contExtendInfo.getContentPane().add(contCarCount, new KDLayout.Constraints(10, 70, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contWorkCompany.setBounds(new Rectangle(378, 40, 270, 19));
        contExtendInfo.getContentPane().add(contWorkCompany, new KDLayout.Constraints(378, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPersonArea.setBounds(new Rectangle(378, 10, 270, 19));
        contExtendInfo.getContentPane().add(contPersonArea, new KDLayout.Constraints(378, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCapital.setBounds(new Rectangle(378, 10, 270, 19));
        contExtendInfo.getContentPane().add(contCapital, new KDLayout.Constraints(378, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDealArea.setBounds(new Rectangle(731, 10, 270, 19));
        contExtendInfo.getContentPane().add(contDealArea, new KDLayout.Constraints(731, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEmployeeCount.setBounds(new Rectangle(10, 40, 270, 19));
        contExtendInfo.getContentPane().add(contEmployeeCount, new KDLayout.Constraints(10, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTaxNumG.setBounds(new Rectangle(378, 40, 270, 19));
        contExtendInfo.getContentPane().add(contTaxNumG, new KDLayout.Constraints(378, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTaxNumD.setBounds(new Rectangle(731, 40, 270, 19));
        contExtendInfo.getContentPane().add(contTaxNumD, new KDLayout.Constraints(731, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCustomerOrigin
        contCustomerOrigin.setBoundEditor(f7CustomerOrigin);
        //contEnterpriceSize
        contEnterpriceSize.setBoundEditor(f7EnterpriceSize);
        //contCooperateMode
        contCooperateMode.setBoundEditor(f7CooperateMode);
        //contMarriage
        contMarriage.setBoundEditor(comboMarriage);
        //contHabitationInfo
        contHabitationInfo.setBoundEditor(f7HabitationInfo);
        //contFirstLinkType
        contFirstLinkType.setBoundEditor(comboFirstLinkType);
        //contCarCount
        contCarCount.setBoundEditor(txtCarCount);
        //contWorkCompany
        contWorkCompany.setBoundEditor(txtWorkCompany);
        //contPersonArea
        contPersonArea.setBoundEditor(f7PersonArea);
        //contCapital
        contCapital.setBoundEditor(txtCapital);
        //contDealArea
        contDealArea.setBoundEditor(txtDealArea);
        //contEmployeeCount
        contEmployeeCount.setBoundEditor(txtEmployeeCount);
        //contTaxNumG
        contTaxNumG.setBoundEditor(txtTaxNumG);
        //contTaxNumD
        contTaxNumD.setBoundEditor(txtTaxNumD);
        //panelQuestion
        panelQuestion.setLayout(new KDLayout());
        panelQuestion.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1012, 604));        tblQuestion.setBounds(new Rectangle(0, 0, 1011, 597));
        panelQuestion.add(tblQuestion, new KDLayout.Constraints(0, 0, 1011, 597, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //panelSale
panelSale.setLayout(new BorderLayout(0, 0));        panelSale.add(containSaleRecord, BorderLayout.CENTER);
        //containSaleRecord
containSaleRecord.getContentPane().setLayout(new BorderLayout(0, 0));        containSaleRecord.getContentPane().add(tblSaleRecord, BorderLayout.CENTER);
        containSaleRecord.getContentPane().add(containSaleCommerce, BorderLayout.SOUTH);
        //containSaleCommerce
containSaleCommerce.getContentPane().setLayout(new BorderLayout(0, 0));        containSaleCommerce.getContentPane().add(tblSaleCommerce, BorderLayout.CENTER);
        //panelTenancy
panelTenancy.setLayout(new BorderLayout(0, 0));        panelTenancy.add(containTenRecord, BorderLayout.CENTER);
        panelTenancy.add(containTenCommerce, BorderLayout.SOUTH);
        //containTenRecord
containTenRecord.getContentPane().setLayout(new BorderLayout(0, 0));        containTenRecord.getContentPane().add(tblTenancyRecord, BorderLayout.CENTER);
        //containTenCommerce
containTenCommerce.getContentPane().setLayout(new BorderLayout(0, 0));        containTenCommerce.getContentPane().add(tblTenancyCommerce, BorderLayout.CENTER);
        //panelInsider
panelInsider.setLayout(new BorderLayout(0, 0));        panelInsider.add(tblInsider, BorderLayout.CENTER);
        //panelCusService
        panelCusService.setLayout(new KDLayout());
        panelCusService.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1012, 604));        kDContainer4.setBounds(new Rectangle(0, 0, 1019, 187));
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
        //panelManager
        panelManager.setLayout(new KDLayout());
        panelManager.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1012, 604));        kDContainer1.setBounds(new Rectangle(0, 0, 1019, 179));
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
        //panelChangeRecord
        panelChangeRecord.setLayout(new KDLayout());
        panelChangeRecord.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1012, 604));        tblChangeLog.setBounds(new Rectangle(10, 7, 975, 465));
        panelChangeRecord.add(tblChangeLog, new KDLayout.Constraints(10, 7, 975, 465, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateUnit.setBounds(new Rectangle(21, 490, 270, 19));
        panelChangeRecord.add(contCreateUnit, new KDLayout.Constraints(21, 490, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(351, 490, 270, 19));
        panelChangeRecord.add(contCreator, new KDLayout.Constraints(351, 490, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUnit.setBounds(new Rectangle(21, 520, 270, 19));
        panelChangeRecord.add(contLastUpdateUnit, new KDLayout.Constraints(21, 520, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateWay.setBounds(new Rectangle(21, 550, 270, 19));
        panelChangeRecord.add(contCreateWay, new KDLayout.Constraints(21, 550, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(351, 520, 270, 19));
        panelChangeRecord.add(contLastUpdateUser, new KDLayout.Constraints(351, 520, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(678, 490, 270, 19));
        panelChangeRecord.add(contCreateTime, new KDLayout.Constraints(678, 490, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(678, 520, 270, 19));
        panelChangeRecord.add(contLastUpdateTime, new KDLayout.Constraints(678, 520, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreateUnit
        contCreateUnit.setBoundEditor(prmtCreateUnit);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contLastUpdateUnit
        contLastUpdateUnit.setBoundEditor(prmtLastUpdateUnit);
        //contCreateWay
        contCreateWay.setBoundEditor(comboCreateWay);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);

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
        this.toolBar.add(btnChangeCusName);
        this.toolBar.add(btnUpdateCus);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basecrm.app.FDCCustomerEditUIHandler";
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
     * output comboCustomerType_itemStateChanged method
     */
    protected void comboCustomerType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblTrackRecord_tableClicked method
     */
    protected void tblTrackRecord_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblLinkman_editStopped method
     */
    protected void tblLinkman_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblLinkman_tableClicked method
     */
    protected void tblLinkman_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output chkShowAll_actionPerformed method
     */
    protected void chkShowAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAdd_actionPerformed method
     */
    protected void btnAdd_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDelete_actionPerformed method
     */
    protected void btnDelete_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtPhone_focusLost method
     */
    protected void txtPhone_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtCertificateNumber_propertyChange method
     */
    protected void txtCertificateNumber_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output txtCertificateNumber_focusLost method
     */
    protected void txtCertificateNumber_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output f7Country_dataChanged method
     */
    protected void f7Country_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7Province_dataChanged method
     */
    protected void f7Province_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7City_dataChanged method
     */
    protected void f7City_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7Area_dataChanged method
     */
    protected void f7Area_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblQuestion_tableClicked method
     */
    protected void tblQuestion_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
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
    	

    /**
     * output actionChangeCusName_actionPerformed method
     */
    public void actionChangeCusName_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpdateCus_actionPerformed method
     */
    public void actionUpdateCus_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionChangeCusName(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChangeCusName() {
    	return false;
    }
	public RequestContext prepareActionUpdateCus(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpdateCus() {
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
            innerActionPerformed("eas", AbstractFDCCustomerEditUI.this, "ActionAddLinkman", "actionAddLinkman_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCCustomerEditUI.this, "ActionAddLine", "actionAddLine_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCCustomerEditUI.this, "ActionCustomerAdapter", "actionCustomerAdapter_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCCustomerEditUI.this, "ActionCustomerShare", "actionCustomerShare_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCCustomerEditUI.this, "ActionCustomerCancelShare", "actionCustomerCancelShare_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCCustomerEditUI.this, "ActionAddCommerceChance", "actionAddCommerceChance_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCCustomerEditUI.this, "ActionRemoveLinkman", "actionRemoveLinkman_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCCustomerEditUI.this, "ActionInsider", "actionInsider_actionPerformed", e);
        }
    }

    /**
     * output ActionChangeCusName class
     */     
    protected class ActionChangeCusName extends ItemAction {     
    
        public ActionChangeCusName()
        {
            this(null);
        }

        public ActionChangeCusName(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionChangeCusName.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeCusName.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeCusName.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCCustomerEditUI.this, "ActionChangeCusName", "actionChangeCusName_actionPerformed", e);
        }
    }

    /**
     * output ActionUpdateCus class
     */     
    protected class ActionUpdateCus extends ItemAction {     
    
        public ActionUpdateCus()
        {
            this(null);
        }

        public ActionUpdateCus(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUpdateCus.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateCus.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateCus.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCCustomerEditUI.this, "ActionUpdateCus", "actionUpdateCus_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basecrm.client", "FDCCustomerEditUI");
    }




}