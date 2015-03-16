/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

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
public abstract class AbstractSupplierStockEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierStockEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedMain;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDBasicInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDFile;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDapprove;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPostNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contServiceArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkMail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProvince;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contManagePeopleCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPeopleCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWebSite;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterpriseKind;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterpriseMaster;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRegisterMoney;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBusinessNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMainPeopleCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDevelopPeopleCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCity;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkFax;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierType;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane3;
    protected com.kingdee.bos.ctrl.swing.KDPanel serviceAreaPane;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contServiceType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCurrency;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPostNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkMail;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkPhone;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProvince;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtManagePeopleCount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPeopleCount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWebSite;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBuildDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboEnterpriseKind;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEnterpriseMaster;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRegisterMoney;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBusinessNum;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMainPeopleCount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDevelopPeopleCount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCity;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkFax;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAddress;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplierType;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWorkClass;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBankLinkPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBankCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyPercent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNetMoney;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBankName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreditLevel;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMainWork;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane4;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtLinkPerson;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtYearSale;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBankLinkPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBankCount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMoneyPercent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNetMoney;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBankName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCreditLevel;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtMainWork;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtWorkClass;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRemark;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtServiceType;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDConAptitudeFile;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtAptitudeFile;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDConAchievement;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtAchievement;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton submitTwo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInsertLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRowApro;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInsertLineAppr;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelRowApro;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kdMenuSubmitTwo;
    protected com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo editData = null;
    protected ActionSubmitTwo actionSubmitTwo = null;
    protected ActionPersonAddLine actionPersonAddLine = null;
    protected ActionPersonDeleteLine actionPersonDeleteLine = null;
    protected ActionFileDelLine actionFileDelLine = null;
    protected ActionApproveAddLine actionApproveAddLine = null;
    protected ActionApproveDelLine actionApproveDelLine = null;
    protected ActionFileAddLine actionFileAddLine = null;
    protected ActionInsertLine actionInsertLine = null;
    protected ActionInsertLineAppr actionInsertLineAppr = null;
    protected ActionPersonInsertLine actionPersonInsertLine = null;
    /**
     * output class constructor
     */
    public AbstractSupplierStockEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSupplierStockEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmitTwo
        this.actionSubmitTwo = new ActionSubmitTwo(this);
        getActionManager().registerAction("actionSubmitTwo", actionSubmitTwo);
         this.actionSubmitTwo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPersonAddLine
        this.actionPersonAddLine = new ActionPersonAddLine(this);
        getActionManager().registerAction("actionPersonAddLine", actionPersonAddLine);
         this.actionPersonAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPersonDeleteLine
        this.actionPersonDeleteLine = new ActionPersonDeleteLine(this);
        getActionManager().registerAction("actionPersonDeleteLine", actionPersonDeleteLine);
         this.actionPersonDeleteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFileDelLine
        this.actionFileDelLine = new ActionFileDelLine(this);
        getActionManager().registerAction("actionFileDelLine", actionFileDelLine);
         this.actionFileDelLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionApproveAddLine
        this.actionApproveAddLine = new ActionApproveAddLine(this);
        getActionManager().registerAction("actionApproveAddLine", actionApproveAddLine);
         this.actionApproveAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionApproveDelLine
        this.actionApproveDelLine = new ActionApproveDelLine(this);
        getActionManager().registerAction("actionApproveDelLine", actionApproveDelLine);
         this.actionApproveDelLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFileAddLine
        this.actionFileAddLine = new ActionFileAddLine(this);
        getActionManager().registerAction("actionFileAddLine", actionFileAddLine);
         this.actionFileAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInsertLine
        this.actionInsertLine = new ActionInsertLine(this);
        getActionManager().registerAction("actionInsertLine", actionInsertLine);
         this.actionInsertLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInsertLineAppr
        this.actionInsertLineAppr = new ActionInsertLineAppr(this);
        getActionManager().registerAction("actionInsertLineAppr", actionInsertLineAppr);
         this.actionInsertLineAppr.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPersonInsertLine
        this.actionPersonInsertLine = new ActionPersonInsertLine(this);
        getActionManager().registerAction("actionPersonInsertLine", actionPersonInsertLine);
         this.actionPersonInsertLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedMain = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDBasicInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDFile = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDapprove = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPostNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contServiceArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkMail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProvince = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contManagePeopleCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPeopleCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWebSite = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnterpriseKind = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnterpriseMaster = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRegisterMoney = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBusinessNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMainPeopleCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDevelopPeopleCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkFax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane3 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.serviceAreaPane = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contServiceType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboCurrency = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtPostNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkMail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtProvince = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtManagePeopleCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPeopleCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtWebSite = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBuildDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboEnterpriseKind = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtEnterpriseMaster = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRegisterMoney = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBusinessNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtMainPeopleCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDevelopPeopleCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtCity = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtLinkFax = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtSupplierType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWorkClass = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBankLinkPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBankCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyPercent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNetMoney = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBankName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreditLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contMainWork = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane4 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kdtLinkPerson = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtYearSale = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtBankLinkPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBankCount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtMoneyPercent = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNetMoney = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBankName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCreditLevel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtMainWork = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtWorkClass = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtServiceType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDConAptitudeFile = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtAptitudeFile = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDConAchievement = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtAchievement = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.submitTwo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInsertLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddRowApro = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInsertLineAppr = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelRowApro = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdMenuSubmitTwo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDTabbedMain.setName("kDTabbedMain");
        this.kDBasicInfo.setName("kDBasicInfo");
        this.kDFile.setName("kDFile");
        this.kDapprove.setName("kDapprove");
        this.contCurrency.setName("contCurrency");
        this.contPostNumber.setName("contPostNumber");
        this.contServiceArea.setName("contServiceArea");
        this.contLinkMail.setName("contLinkMail");
        this.contLinkPhone.setName("contLinkPhone");
        this.contProvince.setName("contProvince");
        this.contManagePeopleCount.setName("contManagePeopleCount");
        this.contPeopleCount.setName("contPeopleCount");
        this.contWebSite.setName("contWebSite");
        this.contBuildDate.setName("contBuildDate");
        this.contEnterpriseKind.setName("contEnterpriseKind");
        this.contEnterpriseMaster.setName("contEnterpriseMaster");
        this.contRegisterMoney.setName("contRegisterMoney");
        this.contBusinessNum.setName("contBusinessNum");
        this.contMainPeopleCount.setName("contMainPeopleCount");
        this.contDevelopPeopleCount.setName("contDevelopPeopleCount");
        this.contCity.setName("contCity");
        this.contLinkFax.setName("contLinkFax");
        this.contAddress.setName("contAddress");
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.contSupplierType.setName("contSupplierType");
        this.kDScrollPane3.setName("kDScrollPane3");
        this.serviceAreaPane.setName("serviceAreaPane");
        this.contServiceType.setName("contServiceType");
        this.comboCurrency.setName("comboCurrency");
        this.txtPostNumber.setName("txtPostNumber");
        this.txtLinkMail.setName("txtLinkMail");
        this.txtLinkPhone.setName("txtLinkPhone");
        this.prmtProvince.setName("prmtProvince");
        this.txtManagePeopleCount.setName("txtManagePeopleCount");
        this.txtPeopleCount.setName("txtPeopleCount");
        this.txtWebSite.setName("txtWebSite");
        this.pkBuildDate.setName("pkBuildDate");
        this.comboEnterpriseKind.setName("comboEnterpriseKind");
        this.txtEnterpriseMaster.setName("txtEnterpriseMaster");
        this.txtRegisterMoney.setName("txtRegisterMoney");
        this.txtBusinessNum.setName("txtBusinessNum");
        this.txtMainPeopleCount.setName("txtMainPeopleCount");
        this.txtDevelopPeopleCount.setName("txtDevelopPeopleCount");
        this.prmtCity.setName("prmtCity");
        this.txtLinkFax.setName("txtLinkFax");
        this.txtAddress.setName("txtAddress");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.prmtSupplierType.setName("prmtSupplierType");
        this.kDPanel1.setName("kDPanel1");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.contRemark.setName("contRemark");
        this.contWorkClass.setName("contWorkClass");
        this.contBankLinkPhone.setName("contBankLinkPhone");
        this.contBankCount.setName("contBankCount");
        this.contMoneyPercent.setName("contMoneyPercent");
        this.contNetMoney.setName("contNetMoney");
        this.contBankName.setName("contBankName");
        this.contCreditLevel.setName("contCreditLevel");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.contMainWork.setName("contMainWork");
        this.kDScrollPane4.setName("kDScrollPane4");
        this.kdtLinkPerson.setName("kdtLinkPerson");
        this.kdtYearSale.setName("kdtYearSale");
        this.txtBankLinkPhone.setName("txtBankLinkPhone");
        this.txtBankCount.setName("txtBankCount");
        this.txtMoneyPercent.setName("txtMoneyPercent");
        this.txtNetMoney.setName("txtNetMoney");
        this.txtBankName.setName("txtBankName");
        this.txtCreditLevel.setName("txtCreditLevel");
        this.txtMainWork.setName("txtMainWork");
        this.txtWorkClass.setName("txtWorkClass");
        this.txtRemark.setName("txtRemark");
        this.prmtServiceType.setName("prmtServiceType");
        this.kDConAptitudeFile.setName("kDConAptitudeFile");
        this.kdtAptitudeFile.setName("kdtAptitudeFile");
        this.kDConAchievement.setName("kDConAchievement");
        this.kdtAchievement.setName("kdtAchievement");
        this.submitTwo.setName("submitTwo");
        this.btnAddRow.setName("btnAddRow");
        this.btnInsertLine.setName("btnInsertLine");
        this.btnDeleteRow.setName("btnDeleteRow");
        this.btnAddRowApro.setName("btnAddRowApro");
        this.btnInsertLineAppr.setName("btnInsertLineAppr");
        this.btnDelRowApro.setName("btnDelRowApro");
        this.kdMenuSubmitTwo.setName("kdMenuSubmitTwo");
        // CoreUI		
        this.setAutoscrolls(true);		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancelCancel.setEnabled(false);		
        this.btnCancel.setVisible(false);		
        this.btnCancel.setEnabled(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemSave.setVisible(false);		
        this.menuEdit.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.btnAttachment.setVisible(true);		
        this.menuItemEdit.setVisible(false);		
        this.menuItemRemove.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.menuBiz.setVisible(false);
        // kDTabbedMain
        this.kDTabbedMain.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDTabbedMain_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kDTabbedMain.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                try {
                    kDTabbedMain_focusGained(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDBasicInfo		
        this.kDBasicInfo.setAutoscrolls(true);
        // kDFile
        // kDapprove
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelUnderline(true);
        // contPostNumber		
        this.contPostNumber.setBoundLabelText(resHelper.getString("contPostNumber.boundLabelText"));		
        this.contPostNumber.setBoundLabelLength(100);		
        this.contPostNumber.setBoundLabelUnderline(true);
        // contServiceArea		
        this.contServiceArea.setBoundLabelText(resHelper.getString("contServiceArea.boundLabelText"));		
        this.contServiceArea.setBoundLabelUnderline(true);
        // contLinkMail		
        this.contLinkMail.setBoundLabelText(resHelper.getString("contLinkMail.boundLabelText"));		
        this.contLinkMail.setBoundLabelLength(100);		
        this.contLinkMail.setBoundLabelUnderline(true);
        // contLinkPhone		
        this.contLinkPhone.setBoundLabelText(resHelper.getString("contLinkPhone.boundLabelText"));		
        this.contLinkPhone.setBoundLabelLength(100);		
        this.contLinkPhone.setBoundLabelUnderline(true);
        // contProvince		
        this.contProvince.setBoundLabelText(resHelper.getString("contProvince.boundLabelText"));		
        this.contProvince.setBoundLabelLength(100);		
        this.contProvince.setBoundLabelUnderline(true);
        // contManagePeopleCount		
        this.contManagePeopleCount.setBoundLabelText(resHelper.getString("contManagePeopleCount.boundLabelText"));		
        this.contManagePeopleCount.setBoundLabelLength(100);		
        this.contManagePeopleCount.setBoundLabelUnderline(true);
        // contPeopleCount		
        this.contPeopleCount.setBoundLabelText(resHelper.getString("contPeopleCount.boundLabelText"));		
        this.contPeopleCount.setBoundLabelLength(100);		
        this.contPeopleCount.setBoundLabelUnderline(true);
        // contWebSite		
        this.contWebSite.setBoundLabelText(resHelper.getString("contWebSite.boundLabelText"));		
        this.contWebSite.setBoundLabelLength(100);		
        this.contWebSite.setBoundLabelUnderline(true);
        // contBuildDate		
        this.contBuildDate.setBoundLabelText(resHelper.getString("contBuildDate.boundLabelText"));		
        this.contBuildDate.setBoundLabelLength(100);		
        this.contBuildDate.setBoundLabelUnderline(true);
        // contEnterpriseKind		
        this.contEnterpriseKind.setBoundLabelText(resHelper.getString("contEnterpriseKind.boundLabelText"));		
        this.contEnterpriseKind.setBoundLabelLength(100);		
        this.contEnterpriseKind.setBoundLabelUnderline(true);
        // contEnterpriseMaster		
        this.contEnterpriseMaster.setBoundLabelText(resHelper.getString("contEnterpriseMaster.boundLabelText"));		
        this.contEnterpriseMaster.setBoundLabelLength(100);		
        this.contEnterpriseMaster.setBoundLabelUnderline(true);
        // contRegisterMoney		
        this.contRegisterMoney.setBoundLabelText(resHelper.getString("contRegisterMoney.boundLabelText"));		
        this.contRegisterMoney.setBoundLabelLength(100);		
        this.contRegisterMoney.setBoundLabelUnderline(true);
        // contBusinessNum		
        this.contBusinessNum.setBoundLabelText(resHelper.getString("contBusinessNum.boundLabelText"));		
        this.contBusinessNum.setBoundLabelLength(100);		
        this.contBusinessNum.setBoundLabelUnderline(true);
        // contMainPeopleCount		
        this.contMainPeopleCount.setBoundLabelText(resHelper.getString("contMainPeopleCount.boundLabelText"));		
        this.contMainPeopleCount.setBoundLabelLength(100);		
        this.contMainPeopleCount.setBoundLabelUnderline(true);
        // contDevelopPeopleCount		
        this.contDevelopPeopleCount.setBoundLabelText(resHelper.getString("contDevelopPeopleCount.boundLabelText"));		
        this.contDevelopPeopleCount.setBoundLabelLength(100);		
        this.contDevelopPeopleCount.setBoundLabelUnderline(true);
        // contCity		
        this.contCity.setBoundLabelText(resHelper.getString("contCity.boundLabelText"));		
        this.contCity.setBoundLabelLength(100);		
        this.contCity.setBoundLabelUnderline(true);
        // contLinkFax		
        this.contLinkFax.setBoundLabelText(resHelper.getString("contLinkFax.boundLabelText"));		
        this.contLinkFax.setBoundLabelLength(100);		
        this.contLinkFax.setBoundLabelUnderline(true);
        // contAddress		
        this.contAddress.setBoundLabelText(resHelper.getString("contAddress.boundLabelText"));		
        this.contAddress.setBoundLabelLength(100);		
        this.contAddress.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelUnderline(true);		
        this.contName.setBoundLabelLength(100);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contSupplierType		
        this.contSupplierType.setBoundLabelText(resHelper.getString("contSupplierType.boundLabelText"));		
        this.contSupplierType.setBoundLabelLength(100);		
        this.contSupplierType.setBoundLabelUnderline(true);
        // kDScrollPane3		
        this.kDScrollPane3.setAutoscrolls(true);
        // serviceAreaPane
        // contServiceType		
        this.contServiceType.setBoundLabelText(resHelper.getString("contServiceType.boundLabelText"));		
        this.contServiceType.setBoundLabelLength(100);		
        this.contServiceType.setBoundLabelUnderline(true);
        // comboCurrency		
        this.comboCurrency.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.CurrencyEnum").toArray());
        // txtPostNumber		
        this.txtPostNumber.setMaxLength(80);
        // txtLinkMail		
        this.txtLinkMail.setMaxLength(80);		
        this.txtLinkMail.setRequired(true);
        // txtLinkPhone		
        this.txtLinkPhone.setMaxLength(80);		
        this.txtLinkPhone.setRequired(true);
        // prmtProvince		
        this.prmtProvince.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7ProvinceQuery");		
        this.prmtProvince.setRequired(true);
        this.prmtProvince.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtProvince_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtManagePeopleCount		
        this.txtManagePeopleCount.setDataVerifierType(12);		
        this.txtManagePeopleCount.setSupportedEmpty(true);
        // txtPeopleCount		
        this.txtPeopleCount.setDataVerifierType(12);		
        this.txtPeopleCount.setSupportedEmpty(true);
        // txtWebSite		
        this.txtWebSite.setMaxLength(80);
        // pkBuildDate		
        this.pkBuildDate.setRequired(true);
        // comboEnterpriseKind		
        this.comboEnterpriseKind.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.EnterpriseKindEnum").toArray());
        // txtEnterpriseMaster		
        this.txtEnterpriseMaster.setMaxLength(80);
        // txtRegisterMoney
        // txtBusinessNum		
        this.txtBusinessNum.setMaxLength(80);		
        this.txtBusinessNum.setRequired(true);
        // txtMainPeopleCount		
        this.txtMainPeopleCount.setDataVerifierType(12);		
        this.txtMainPeopleCount.setSupportedEmpty(true);
        // txtDevelopPeopleCount		
        this.txtDevelopPeopleCount.setDataVerifierType(12);		
        this.txtDevelopPeopleCount.setSupportedEmpty(true);
        // prmtCity		
        this.prmtCity.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CityQuery");
        this.prmtCity.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtCity_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtLinkFax		
        this.txtLinkFax.setMaxLength(80);		
        this.txtLinkFax.setRequired(true);
        // txtAddress		
        this.txtAddress.setMaxLength(80);
        // txtName		
        this.txtName.setRequired(true);
        this.txtName.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    txtName_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    txtName_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // prmtSupplierType		
        this.prmtSupplierType.setRequired(true);		
        this.prmtSupplierType.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.CSSPGroupQuery");
        // kDPanel1		
        this.kDPanel1.setAutoscrolls(true);		
        this.kDPanel1.setMaximumSize(new Dimension(967,586));		
        this.kDPanel1.setMinimumSize(new Dimension(967,586));		
        this.kDPanel1.setPreferredSize(new Dimension(967,586));
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setAutoscrolls(true);		
        this.kDContainer1.setEnableActive(false);
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));		
        this.kDContainer2.setAutoscrolls(true);		
        this.kDContainer2.setEnableActive(false);
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelUnderline(true);
        // contWorkClass		
        this.contWorkClass.setBoundLabelText(resHelper.getString("contWorkClass.boundLabelText"));		
        this.contWorkClass.setBoundLabelUnderline(true);
        // contBankLinkPhone		
        this.contBankLinkPhone.setBoundLabelText(resHelper.getString("contBankLinkPhone.boundLabelText"));		
        this.contBankLinkPhone.setBoundLabelLength(100);		
        this.contBankLinkPhone.setBoundLabelUnderline(true);
        // contBankCount		
        this.contBankCount.setBoundLabelText(resHelper.getString("contBankCount.boundLabelText"));		
        this.contBankCount.setBoundLabelLength(100);		
        this.contBankCount.setBoundLabelUnderline(true);
        // contMoneyPercent		
        this.contMoneyPercent.setBoundLabelText(resHelper.getString("contMoneyPercent.boundLabelText"));		
        this.contMoneyPercent.setBoundLabelLength(100);		
        this.contMoneyPercent.setBoundLabelUnderline(true);
        // contNetMoney		
        this.contNetMoney.setBoundLabelText(resHelper.getString("contNetMoney.boundLabelText"));		
        this.contNetMoney.setBoundLabelLength(100);		
        this.contNetMoney.setBoundLabelUnderline(true);
        // contBankName		
        this.contBankName.setBoundLabelText(resHelper.getString("contBankName.boundLabelText"));		
        this.contBankName.setBoundLabelLength(100);		
        this.contBankName.setBoundLabelUnderline(true);
        // contCreditLevel		
        this.contCreditLevel.setBoundLabelText(resHelper.getString("contCreditLevel.boundLabelText"));		
        this.contCreditLevel.setBoundLabelLength(100);		
        this.contCreditLevel.setBoundLabelUnderline(true);
        // kDScrollPane1
        // kDScrollPane2
        // contMainWork		
        this.contMainWork.setBoundLabelText(resHelper.getString("contMainWork.boundLabelText"));		
        this.contMainWork.setBoundLabelUnderline(true);
        // kDScrollPane4
        // kdtLinkPerson
		String kdtLinkPersonStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"personName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"position\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"workPhone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"personFax\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isDefault\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"email\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contact\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{personName}</t:Cell><t:Cell>$Resource{position}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{workPhone}</t:Cell><t:Cell>$Resource{personFax}</t:Cell><t:Cell>$Resource{isDefault}</t:Cell><t:Cell>$Resource{email}</t:Cell><t:Cell>$Resource{contact}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtLinkPerson.setFormatXml(resHelper.translateString("kdtLinkPerson",kdtLinkPersonStrXML));
        this.kdtLinkPerson.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtLinkPerson_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtLinkPerson.putBindContents("editData",new String[] {"personName","position","phone","workPhone","personFax","isDefault","email","contact"});


        // kdtYearSale
		String kdtYearSaleStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"year\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saleCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{year}</t:Cell><t:Cell>$Resource{saleCount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtYearSale.setFormatXml(resHelper.translateString("kdtYearSale",kdtYearSaleStrXML));
        this.kdtYearSale.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtYearSale_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // txtBankLinkPhone		
        this.txtBankLinkPhone.setMaxLength(80);
        // txtBankCount		
        this.txtBankCount.setMaxLength(80);
        // txtMoneyPercent		
        this.txtMoneyPercent.setMaxLength(80);
        // txtNetMoney
        // txtBankName		
        this.txtBankName.setMaxLength(80);
        // txtCreditLevel		
        this.txtCreditLevel.setMaxLength(80);
        // txtMainWork		
        this.txtMainWork.setMaxLength(500);
        // txtWorkClass		
        this.txtWorkClass.setMaxLength(500);
        // txtRemark		
        this.txtRemark.setMaxLength(500);
        // prmtServiceType		
        this.prmtServiceType.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7SupplierServiceTypeQuery");		
        this.prmtServiceType.setRequired(true);
        this.prmtServiceType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtServiceType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDConAptitudeFile		
        this.kDConAptitudeFile.setTitle(resHelper.getString("kDConAptitudeFile.title"));		
        this.kDConAptitudeFile.setEnableActive(false);
        // kdtAptitudeFile
		String kdtAptitudeFileStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"aptNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"aptName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"aptLevel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"awardUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isHaveAttach\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{aptNumber}</t:Cell><t:Cell>$Resource{aptName}</t:Cell><t:Cell>$Resource{aptLevel}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{awardUnit}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{isHaveAttach}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtAptitudeFile.setFormatXml(resHelper.translateString("kdtAptitudeFile",kdtAptitudeFileStrXML));

                this.kdtAptitudeFile.putBindContents("editData",new String[] {"aptNumber","aptName","aptLevel","endDate","awardUnit","remark","isHaveAttach","id"});


        // kDConAchievement		
        this.kDConAchievement.setEnableActive(false);		
        this.kDConAchievement.setTitle(resHelper.getString("kDConAchievement.title"));
        // kdtAchievement
		String kdtAchievementStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"projectName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"clientName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"clientAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"clientLinkPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractPay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"peopleCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isHaveAttach\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{projectName}</t:Cell><t:Cell>$Resource{clientName}</t:Cell><t:Cell>$Resource{clientAddress}</t:Cell><t:Cell>$Resource{clientLinkPerson}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{contractPay}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{peopleCount}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{isHaveAttach}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtAchievement.setFormatXml(resHelper.translateString("kdtAchievement",kdtAchievementStrXML));

                this.kdtAchievement.putBindContents("editData",new String[] {"projectName","clientName","clientAddress","clientLinkPerson","phone","contractPay","startDate","endDate","peopleCount","remark","isHaveAttach","id"});


        // submitTwo
        this.submitTwo.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmitTwo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.submitTwo.setText(resHelper.getString("submitTwo.text"));		
        this.submitTwo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_submit"));		
        this.submitTwo.setToolTipText(resHelper.getString("submitTwo.toolTipText"));
        // btnAddRow
        this.btnAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionFileAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRow.setText(resHelper.getString("btnAddRow.text"));
        // btnInsertLine
        this.btnInsertLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionInsertLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInsertLine.setText(resHelper.getString("btnInsertLine.text"));
        // btnDeleteRow
        this.btnDeleteRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionFileDelLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeleteRow.setText(resHelper.getString("btnDeleteRow.text"));
        // btnAddRowApro
        this.btnAddRowApro.setAction((IItemAction)ActionProxyFactory.getProxy(actionApproveAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRowApro.setText(resHelper.getString("btnAddRowApro.text"));
        // btnInsertLineAppr
        this.btnInsertLineAppr.setAction((IItemAction)ActionProxyFactory.getProxy(actionInsertLineAppr, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInsertLineAppr.setText(resHelper.getString("btnInsertLineAppr.text"));
        // btnDelRowApro
        this.btnDelRowApro.setAction((IItemAction)ActionProxyFactory.getProxy(actionApproveDelLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelRowApro.setText(resHelper.getString("btnDelRowApro.text"));
        // kdMenuSubmitTwo
        this.kdMenuSubmitTwo.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmitTwo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kdMenuSubmitTwo.setText(resHelper.getString("kdMenuSubmitTwo.text"));		
        this.kdMenuSubmitTwo.setMnemonic(84);
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
        this.setBounds(new Rectangle(10, 10, 1013, 768));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 768));
        kDTabbedMain.setBounds(new Rectangle(7, 8, 998, 748));
        this.add(kDTabbedMain, new KDLayout.Constraints(7, 8, 998, 748, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDTabbedMain
        kDTabbedMain.add(kDBasicInfo, resHelper.getString("kDBasicInfo.constraints"));
        kDTabbedMain.add(kDFile, resHelper.getString("kDFile.constraints"));
        kDTabbedMain.add(kDapprove, resHelper.getString("kDapprove.constraints"));
        //kDBasicInfo
        kDBasicInfo.setLayout(new KDLayout());
        kDBasicInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 997, 715));        contCurrency.setBounds(new Rectangle(186, 68, 93, 18));
        kDBasicInfo.add(contCurrency, new KDLayout.Constraints(186, 68, 93, 18, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPostNumber.setBounds(new Rectangle(9, 187, 270, 19));
        kDBasicInfo.add(contPostNumber, new KDLayout.Constraints(9, 187, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contServiceArea.setBounds(new Rectangle(9, 217, 50, 19));
        kDBasicInfo.add(contServiceArea, new KDLayout.Constraints(9, 217, 50, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkMail.setBounds(new Rectangle(707, 157, 270, 19));
        kDBasicInfo.add(contLinkMail, new KDLayout.Constraints(707, 157, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLinkPhone.setBounds(new Rectangle(358, 157, 270, 19));
        kDBasicInfo.add(contLinkPhone, new KDLayout.Constraints(358, 157, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProvince.setBounds(new Rectangle(358, 127, 270, 19));
        kDBasicInfo.add(contProvince, new KDLayout.Constraints(358, 127, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contManagePeopleCount.setBounds(new Rectangle(9, 127, 270, 19));
        kDBasicInfo.add(contManagePeopleCount, new KDLayout.Constraints(9, 127, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPeopleCount.setBounds(new Rectangle(9, 97, 270, 19));
        kDBasicInfo.add(contPeopleCount, new KDLayout.Constraints(9, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contWebSite.setBounds(new Rectangle(9, 157, 270, 19));
        kDBasicInfo.add(contWebSite, new KDLayout.Constraints(9, 157, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuildDate.setBounds(new Rectangle(707, 37, 270, 19));
        kDBasicInfo.add(contBuildDate, new KDLayout.Constraints(707, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contEnterpriseKind.setBounds(new Rectangle(358, 67, 270, 19));
        kDBasicInfo.add(contEnterpriseKind, new KDLayout.Constraints(358, 67, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEnterpriseMaster.setBounds(new Rectangle(358, 37, 270, 19));
        kDBasicInfo.add(contEnterpriseMaster, new KDLayout.Constraints(358, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRegisterMoney.setBounds(new Rectangle(9, 67, 163, 19));
        kDBasicInfo.add(contRegisterMoney, new KDLayout.Constraints(9, 67, 163, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBusinessNum.setBounds(new Rectangle(707, 67, 270, 19));
        kDBasicInfo.add(contBusinessNum, new KDLayout.Constraints(707, 67, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contMainPeopleCount.setBounds(new Rectangle(358, 97, 270, 19));
        kDBasicInfo.add(contMainPeopleCount, new KDLayout.Constraints(358, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDevelopPeopleCount.setBounds(new Rectangle(707, 98, 270, 19));
        kDBasicInfo.add(contDevelopPeopleCount, new KDLayout.Constraints(707, 98, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCity.setBounds(new Rectangle(707, 127, 270, 19));
        kDBasicInfo.add(contCity, new KDLayout.Constraints(707, 127, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLinkFax.setBounds(new Rectangle(358, 187, 270, 19));
        kDBasicInfo.add(contLinkFax, new KDLayout.Constraints(358, 187, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAddress.setBounds(new Rectangle(707, 187, 270, 19));
        kDBasicInfo.add(contAddress, new KDLayout.Constraints(707, 187, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contName.setBounds(new Rectangle(358, 7, 270, 19));
        kDBasicInfo.add(contName, new KDLayout.Constraints(358, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(707, 7, 270, 19));
        kDBasicInfo.add(contNumber, new KDLayout.Constraints(707, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSupplierType.setBounds(new Rectangle(9, 7, 270, 19));
        kDBasicInfo.add(contSupplierType, new KDLayout.Constraints(9, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane3.setBounds(new Rectangle(4, 248, 985, 460));
        kDBasicInfo.add(kDScrollPane3, new KDLayout.Constraints(4, 248, 985, 460, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        serviceAreaPane.setBounds(new Rectangle(107, 212, 870, 25));
        kDBasicInfo.add(serviceAreaPane, new KDLayout.Constraints(107, 212, 870, 25, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contServiceType.setBounds(new Rectangle(9, 37, 270, 19));
        kDBasicInfo.add(contServiceType, new KDLayout.Constraints(9, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCurrency
        contCurrency.setBoundEditor(comboCurrency);
        //contPostNumber
        contPostNumber.setBoundEditor(txtPostNumber);
        //contLinkMail
        contLinkMail.setBoundEditor(txtLinkMail);
        //contLinkPhone
        contLinkPhone.setBoundEditor(txtLinkPhone);
        //contProvince
        contProvince.setBoundEditor(prmtProvince);
        //contManagePeopleCount
        contManagePeopleCount.setBoundEditor(txtManagePeopleCount);
        //contPeopleCount
        contPeopleCount.setBoundEditor(txtPeopleCount);
        //contWebSite
        contWebSite.setBoundEditor(txtWebSite);
        //contBuildDate
        contBuildDate.setBoundEditor(pkBuildDate);
        //contEnterpriseKind
        contEnterpriseKind.setBoundEditor(comboEnterpriseKind);
        //contEnterpriseMaster
        contEnterpriseMaster.setBoundEditor(txtEnterpriseMaster);
        //contRegisterMoney
        contRegisterMoney.setBoundEditor(txtRegisterMoney);
        //contBusinessNum
        contBusinessNum.setBoundEditor(txtBusinessNum);
        //contMainPeopleCount
        contMainPeopleCount.setBoundEditor(txtMainPeopleCount);
        //contDevelopPeopleCount
        contDevelopPeopleCount.setBoundEditor(txtDevelopPeopleCount);
        //contCity
        contCity.setBoundEditor(prmtCity);
        //contLinkFax
        contLinkFax.setBoundEditor(txtLinkFax);
        //contAddress
        contAddress.setBoundEditor(txtAddress);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contSupplierType
        contSupplierType.setBoundEditor(prmtSupplierType);
        //kDScrollPane3
        kDScrollPane3.getViewport().add(kDPanel1, null);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(9, 111, 967, 586));        kDContainer1.setBounds(new Rectangle(11, 6, 947, 96));
        kDPanel1.add(kDContainer1, new KDLayout.Constraints(11, 6, 947, 96, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer2.setBounds(new Rectangle(11, 119, 947, 118));
        kDPanel1.add(kDContainer2, new KDLayout.Constraints(11, 119, 947, 118, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contRemark.setBounds(new Rectangle(11, 492, 37, 19));
        kDPanel1.add(contRemark, new KDLayout.Constraints(11, 492, 37, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT));
        contWorkClass.setBounds(new Rectangle(11, 398, 58, 19));
        kDPanel1.add(contWorkClass, new KDLayout.Constraints(11, 398, 58, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT));
        contBankLinkPhone.setBounds(new Rectangle(687, 277, 270, 19));
        kDPanel1.add(contBankLinkPhone, new KDLayout.Constraints(687, 277, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBankCount.setBounds(new Rectangle(11, 277, 270, 19));
        kDPanel1.add(contBankCount, new KDLayout.Constraints(11, 277, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMoneyPercent.setBounds(new Rectangle(351, 249, 270, 19));
        kDPanel1.add(contMoneyPercent, new KDLayout.Constraints(351, 249, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNetMoney.setBounds(new Rectangle(11, 249, 270, 19));
        kDPanel1.add(contNetMoney, new KDLayout.Constraints(11, 249, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBankName.setBounds(new Rectangle(687, 249, 270, 19));
        kDPanel1.add(contBankName, new KDLayout.Constraints(687, 249, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreditLevel.setBounds(new Rectangle(351, 277, 270, 19));
        kDPanel1.add(contCreditLevel, new KDLayout.Constraints(351, 277, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(12, 333, 945, 60));
        kDPanel1.add(kDScrollPane1, new KDLayout.Constraints(12, 333, 945, 60, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane2.setBounds(new Rectangle(13, 428, 944, 60));
        kDPanel1.add(kDScrollPane2, new KDLayout.Constraints(13, 428, 944, 60, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contMainWork.setBounds(new Rectangle(11, 303, 62, 19));
        kDPanel1.add(contMainWork, new KDLayout.Constraints(11, 303, 62, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT));
        kDScrollPane4.setBounds(new Rectangle(13, 519, 943, 60));
        kDPanel1.add(kDScrollPane4, new KDLayout.Constraints(13, 519, 943, 60, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kdtLinkPerson, BorderLayout.CENTER);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(kdtYearSale, BorderLayout.CENTER);
        //contBankLinkPhone
        contBankLinkPhone.setBoundEditor(txtBankLinkPhone);
        //contBankCount
        contBankCount.setBoundEditor(txtBankCount);
        //contMoneyPercent
        contMoneyPercent.setBoundEditor(txtMoneyPercent);
        //contNetMoney
        contNetMoney.setBoundEditor(txtNetMoney);
        //contBankName
        contBankName.setBoundEditor(txtBankName);
        //contCreditLevel
        contCreditLevel.setBoundEditor(txtCreditLevel);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtMainWork, null);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtWorkClass, null);
        //kDScrollPane4
        kDScrollPane4.getViewport().add(txtRemark, null);
        serviceAreaPane.setLayout(null);        //contServiceType
        contServiceType.setBoundEditor(prmtServiceType);
        //kDFile
        kDFile.setLayout(new KDLayout());
        kDFile.putClientProperty("OriginalBounds", new Rectangle(0, 0, 997, 715));        kDConAptitudeFile.setBounds(new Rectangle(4, 4, 985, 707));
        kDFile.add(kDConAptitudeFile, new KDLayout.Constraints(4, 4, 985, 707, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDConAptitudeFile
kDConAptitudeFile.getContentPane().setLayout(new BorderLayout(0, 0));        kDConAptitudeFile.getContentPane().add(kdtAptitudeFile, BorderLayout.CENTER);
        //kDapprove
        kDapprove.setLayout(new KDLayout());
        kDapprove.putClientProperty("OriginalBounds", new Rectangle(0, 0, 997, 715));        kDConAchievement.setBounds(new Rectangle(3, 5, 985, 704));
        kDapprove.add(kDConAchievement, new KDLayout.Constraints(3, 5, 985, 704, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDConAchievement
kDConAchievement.getContentPane().setLayout(new BorderLayout(0, 0));        kDConAchievement.getContentPane().add(kdtAchievement, BorderLayout.CENTER);

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
        menuFile.add(kdMenuSubmitTwo);
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
        menuEdit.add(menuItemReset);
        menuEdit.add(kDSeparator4);
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
        this.toolBar.add(submitTwo);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnAddRow);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnDeleteRow);
        this.toolBar.add(btnAddRowApro);
        this.toolBar.add(btnInsertLineAppr);
        this.toolBar.add(btnDelRowApro);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("currency", com.kingdee.eas.fdc.invite.supplier.CurrencyEnum.class, this.comboCurrency, "selectedItem");
		dataBinder.registerBinding("postNumber", String.class, this.txtPostNumber, "text");
		dataBinder.registerBinding("linkMail", String.class, this.txtLinkMail, "text");
		dataBinder.registerBinding("linkPhone", String.class, this.txtLinkPhone, "text");
		dataBinder.registerBinding("province", com.kingdee.eas.basedata.assistant.ProvinceInfo.class, this.prmtProvince, "data");
		dataBinder.registerBinding("managePeopleCount", java.math.BigDecimal.class, this.txtManagePeopleCount, "value");
		dataBinder.registerBinding("peopleCount", java.math.BigDecimal.class, this.txtPeopleCount, "value");
		dataBinder.registerBinding("webSite", String.class, this.txtWebSite, "text");
		dataBinder.registerBinding("buildDate", java.util.Date.class, this.pkBuildDate, "value");
		dataBinder.registerBinding("enterpriseKind", com.kingdee.eas.fdc.invite.supplier.EnterpriseKindEnum.class, this.comboEnterpriseKind, "selectedItem");
		dataBinder.registerBinding("enterpriseMaster", String.class, this.txtEnterpriseMaster, "text");
		dataBinder.registerBinding("registerMoney", java.math.BigDecimal.class, this.txtRegisterMoney, "value");
		dataBinder.registerBinding("businessNum", String.class, this.txtBusinessNum, "text");
		dataBinder.registerBinding("mainPeopleCount", java.math.BigDecimal.class, this.txtMainPeopleCount, "value");
		dataBinder.registerBinding("developPeopleCount", java.math.BigDecimal.class, this.txtDevelopPeopleCount, "value");
		dataBinder.registerBinding("city", com.kingdee.eas.basedata.assistant.CityInfo.class, this.prmtCity, "data");
		dataBinder.registerBinding("linkFax", String.class, this.txtLinkFax, "text");
		dataBinder.registerBinding("address", String.class, this.txtAddress, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("supplierType", com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo.class, this.prmtSupplierType, "data");
		dataBinder.registerBinding("linkPerson.personName", String.class, this.kdtLinkPerson, "personName.text");
		dataBinder.registerBinding("linkPerson.position", String.class, this.kdtLinkPerson, "position.text");
		dataBinder.registerBinding("linkPerson.phone", String.class, this.kdtLinkPerson, "phone.text");
		dataBinder.registerBinding("linkPerson.workPhone", String.class, this.kdtLinkPerson, "workPhone.text");
		dataBinder.registerBinding("linkPerson.personFax", String.class, this.kdtLinkPerson, "personFax.text");
		dataBinder.registerBinding("linkPerson.isDefault", boolean.class, this.kdtLinkPerson, "isDefault.text");
		dataBinder.registerBinding("linkPerson.email", String.class, this.kdtLinkPerson, "email.text");
		dataBinder.registerBinding("linkPerson", com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonInfo.class, this.kdtLinkPerson, "userObject");
		dataBinder.registerBinding("linkPerson.contact", String.class, this.kdtLinkPerson, "contact.text");
		dataBinder.registerBinding("bankLinkPhone", String.class, this.txtBankLinkPhone, "text");
		dataBinder.registerBinding("bankCount", String.class, this.txtBankCount, "text");
		dataBinder.registerBinding("moneyPercent", String.class, this.txtMoneyPercent, "text");
		dataBinder.registerBinding("netMoney", java.math.BigDecimal.class, this.txtNetMoney, "value");
		dataBinder.registerBinding("bankName", String.class, this.txtBankName, "text");
		dataBinder.registerBinding("creditLevel", String.class, this.txtCreditLevel, "text");
		dataBinder.registerBinding("mainWork", String.class, this.txtMainWork, "text");
		dataBinder.registerBinding("workClass", String.class, this.txtWorkClass, "text");
		dataBinder.registerBinding("remark", String.class, this.txtRemark, "text");
		dataBinder.registerBinding("aptitudeFile.aptNumber", String.class, this.kdtAptitudeFile, "aptNumber.text");
		dataBinder.registerBinding("aptitudeFile.aptName", String.class, this.kdtAptitudeFile, "aptName.text");
		dataBinder.registerBinding("aptitudeFile.aptLevel", String.class, this.kdtAptitudeFile, "aptLevel.text");
		dataBinder.registerBinding("aptitudeFile.endDate", java.util.Date.class, this.kdtAptitudeFile, "endDate.text");
		dataBinder.registerBinding("aptitudeFile.awardUnit", String.class, this.kdtAptitudeFile, "awardUnit.text");
		dataBinder.registerBinding("aptitudeFile.remark", String.class, this.kdtAptitudeFile, "remark.text");
		dataBinder.registerBinding("aptitudeFile.isHaveAttach", boolean.class, this.kdtAptitudeFile, "isHaveAttach.text");
		dataBinder.registerBinding("aptitudeFile", com.kingdee.eas.fdc.invite.supplier.AptitudeFileInfo.class, this.kdtAptitudeFile, "userObject");
		dataBinder.registerBinding("aptitudeFile.id", com.kingdee.bos.util.BOSUuid.class, this.kdtAptitudeFile, "id.text");
		dataBinder.registerBinding("achievement.projectName", String.class, this.kdtAchievement, "projectName.text");
		dataBinder.registerBinding("achievement.clientName", String.class, this.kdtAchievement, "clientName.text");
		dataBinder.registerBinding("achievement.clientAddress", String.class, this.kdtAchievement, "clientAddress.text");
		dataBinder.registerBinding("achievement.clientLinkPerson", String.class, this.kdtAchievement, "clientLinkPerson.text");
		dataBinder.registerBinding("achievement.phone", String.class, this.kdtAchievement, "phone.text");
		dataBinder.registerBinding("achievement.contractPay", java.math.BigDecimal.class, this.kdtAchievement, "contractPay.text");
		dataBinder.registerBinding("achievement.startDate", java.util.Date.class, this.kdtAchievement, "startDate.text");
		dataBinder.registerBinding("achievement.endDate", java.util.Date.class, this.kdtAchievement, "endDate.text");
		dataBinder.registerBinding("achievement.peopleCount", java.math.BigDecimal.class, this.kdtAchievement, "peopleCount.text");
		dataBinder.registerBinding("achievement.remark", String.class, this.kdtAchievement, "remark.text");
		dataBinder.registerBinding("achievement.isHaveAttach", boolean.class, this.kdtAchievement, "isHaveAttach.text");
		dataBinder.registerBinding("achievement", com.kingdee.eas.fdc.invite.supplier.AchievementInfo.class, this.kdtAchievement, "userObject");
		dataBinder.registerBinding("achievement.id", com.kingdee.bos.util.BOSUuid.class, this.kdtAchievement, "id.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.SupplierStockEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)ov;
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
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("postNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkMail", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("province", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("managePeopleCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("peopleCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("webSite", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("enterpriseKind", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("enterpriseMaster", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("registerMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("businessNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mainPeopleCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("developPeopleCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("city", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkFax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplierType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkPerson.personName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkPerson.position", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkPerson.phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkPerson.workPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkPerson.personFax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkPerson.isDefault", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkPerson.email", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkPerson.contact", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankLinkPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("moneyPercent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("netMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creditLevel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mainWork", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("workClass", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aptitudeFile.aptNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aptitudeFile.aptName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aptitudeFile.aptLevel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aptitudeFile.endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aptitudeFile.awardUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aptitudeFile.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aptitudeFile.isHaveAttach", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aptitudeFile", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aptitudeFile.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievement.projectName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievement.clientName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievement.clientAddress", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievement.clientLinkPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievement.phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievement.contractPay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievement.startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievement.endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievement.peopleCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievement.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievement.isHaveAttach", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievement", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievement.id", ValidateHelper.ON_SAVE);    		
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
     * output kDTabbedMain_stateChanged method
     */
    protected void kDTabbedMain_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kDTabbedMain_focusGained method
     */
    protected void kDTabbedMain_focusGained(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output prmtProvince_dataChanged method
     */
    protected void prmtProvince_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCity_willShow method
     */
    protected void prmtCity_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output txtName_propertyChange method
     */
    protected void txtName_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output txtName_itemStateChanged method
     */
    protected void txtName_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output kdtLinkPerson_editStopped method
     */
    protected void kdtLinkPerson_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtYearSale_editStopped method
     */
    protected void kdtYearSale_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output prmtServiceType_dataChanged method
     */
    protected void prmtServiceType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("currency"));
        sic.add(new SelectorItemInfo("postNumber"));
        sic.add(new SelectorItemInfo("linkMail"));
        sic.add(new SelectorItemInfo("linkPhone"));
        sic.add(new SelectorItemInfo("province.*"));
        sic.add(new SelectorItemInfo("managePeopleCount"));
        sic.add(new SelectorItemInfo("peopleCount"));
        sic.add(new SelectorItemInfo("webSite"));
        sic.add(new SelectorItemInfo("buildDate"));
        sic.add(new SelectorItemInfo("enterpriseKind"));
        sic.add(new SelectorItemInfo("enterpriseMaster"));
        sic.add(new SelectorItemInfo("registerMoney"));
        sic.add(new SelectorItemInfo("businessNum"));
        sic.add(new SelectorItemInfo("mainPeopleCount"));
        sic.add(new SelectorItemInfo("developPeopleCount"));
        sic.add(new SelectorItemInfo("city.*"));
        sic.add(new SelectorItemInfo("linkFax"));
        sic.add(new SelectorItemInfo("address"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("supplierType.*"));
    sic.add(new SelectorItemInfo("linkPerson.personName"));
    sic.add(new SelectorItemInfo("linkPerson.position"));
    sic.add(new SelectorItemInfo("linkPerson.phone"));
    sic.add(new SelectorItemInfo("linkPerson.workPhone"));
    sic.add(new SelectorItemInfo("linkPerson.personFax"));
    sic.add(new SelectorItemInfo("linkPerson.isDefault"));
    sic.add(new SelectorItemInfo("linkPerson.email"));
        sic.add(new SelectorItemInfo("linkPerson.*"));
//        sic.add(new SelectorItemInfo("linkPerson.number"));
    sic.add(new SelectorItemInfo("linkPerson.contact"));
        sic.add(new SelectorItemInfo("bankLinkPhone"));
        sic.add(new SelectorItemInfo("bankCount"));
        sic.add(new SelectorItemInfo("moneyPercent"));
        sic.add(new SelectorItemInfo("netMoney"));
        sic.add(new SelectorItemInfo("bankName"));
        sic.add(new SelectorItemInfo("creditLevel"));
        sic.add(new SelectorItemInfo("mainWork"));
        sic.add(new SelectorItemInfo("workClass"));
        sic.add(new SelectorItemInfo("remark"));
    sic.add(new SelectorItemInfo("aptitudeFile.aptNumber"));
    sic.add(new SelectorItemInfo("aptitudeFile.aptName"));
    sic.add(new SelectorItemInfo("aptitudeFile.aptLevel"));
    sic.add(new SelectorItemInfo("aptitudeFile.endDate"));
    sic.add(new SelectorItemInfo("aptitudeFile.awardUnit"));
    sic.add(new SelectorItemInfo("aptitudeFile.remark"));
    sic.add(new SelectorItemInfo("aptitudeFile.isHaveAttach"));
        sic.add(new SelectorItemInfo("aptitudeFile.*"));
//        sic.add(new SelectorItemInfo("aptitudeFile.number"));
    sic.add(new SelectorItemInfo("aptitudeFile.id"));
    sic.add(new SelectorItemInfo("achievement.projectName"));
    sic.add(new SelectorItemInfo("achievement.clientName"));
    sic.add(new SelectorItemInfo("achievement.clientAddress"));
    sic.add(new SelectorItemInfo("achievement.clientLinkPerson"));
    sic.add(new SelectorItemInfo("achievement.phone"));
    sic.add(new SelectorItemInfo("achievement.contractPay"));
    sic.add(new SelectorItemInfo("achievement.startDate"));
    sic.add(new SelectorItemInfo("achievement.endDate"));
    sic.add(new SelectorItemInfo("achievement.peopleCount"));
    sic.add(new SelectorItemInfo("achievement.remark"));
    sic.add(new SelectorItemInfo("achievement.isHaveAttach"));
        sic.add(new SelectorItemInfo("achievement.*"));
//        sic.add(new SelectorItemInfo("achievement.number"));
    sic.add(new SelectorItemInfo("achievement.id"));
        return sic;
    }        
    	

    /**
     * output actionSubmitTwo_actionPerformed method
     */
    public void actionSubmitTwo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPersonAddLine_actionPerformed method
     */
    public void actionPersonAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPersonDeleteLine_actionPerformed method
     */
    public void actionPersonDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFileDelLine_actionPerformed method
     */
    public void actionFileDelLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionApproveAddLine_actionPerformed method
     */
    public void actionApproveAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionApproveDelLine_actionPerformed method
     */
    public void actionApproveDelLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFileAddLine_actionPerformed method
     */
    public void actionFileAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInsertLine_actionPerformed method
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInsertLineAppr_actionPerformed method
     */
    public void actionInsertLineAppr_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPersonInsertLine_actionPerformed method
     */
    public void actionPersonInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmitTwo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmitTwo() {
    	return false;
    }
	public RequestContext prepareActionPersonAddLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPersonAddLine() {
    	return false;
    }
	public RequestContext prepareActionPersonDeleteLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPersonDeleteLine() {
    	return false;
    }
	public RequestContext prepareActionFileDelLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFileDelLine() {
    	return false;
    }
	public RequestContext prepareActionApproveAddLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionApproveAddLine() {
    	return false;
    }
	public RequestContext prepareActionApproveDelLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionApproveDelLine() {
    	return false;
    }
	public RequestContext prepareActionFileAddLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFileAddLine() {
    	return false;
    }
	public RequestContext prepareActionInsertLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInsertLine() {
    	return false;
    }
	public RequestContext prepareActionInsertLineAppr(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInsertLineAppr() {
    	return false;
    }
	public RequestContext prepareActionPersonInsertLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPersonInsertLine() {
    	return false;
    }

    /**
     * output ActionSubmitTwo class
     */     
    protected class ActionSubmitTwo extends ItemAction {     
    
        public ActionSubmitTwo()
        {
            this(null);
        }

        public ActionSubmitTwo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSubmitTwo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmitTwo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmitTwo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockEditUI.this, "ActionSubmitTwo", "actionSubmitTwo_actionPerformed", e);
        }
    }

    /**
     * output ActionPersonAddLine class
     */     
    protected class ActionPersonAddLine extends ItemAction {     
    
        public ActionPersonAddLine()
        {
            this(null);
        }

        public ActionPersonAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPersonAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPersonAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPersonAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockEditUI.this, "ActionPersonAddLine", "actionPersonAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionPersonDeleteLine class
     */     
    protected class ActionPersonDeleteLine extends ItemAction {     
    
        public ActionPersonDeleteLine()
        {
            this(null);
        }

        public ActionPersonDeleteLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPersonDeleteLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPersonDeleteLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPersonDeleteLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockEditUI.this, "ActionPersonDeleteLine", "actionPersonDeleteLine_actionPerformed", e);
        }
    }

    /**
     * output ActionFileDelLine class
     */     
    protected class ActionFileDelLine extends ItemAction {     
    
        public ActionFileDelLine()
        {
            this(null);
        }

        public ActionFileDelLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionFileDelLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFileDelLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFileDelLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockEditUI.this, "ActionFileDelLine", "actionFileDelLine_actionPerformed", e);
        }
    }

    /**
     * output ActionApproveAddLine class
     */     
    protected class ActionApproveAddLine extends ItemAction {     
    
        public ActionApproveAddLine()
        {
            this(null);
        }

        public ActionApproveAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionApproveAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproveAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproveAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockEditUI.this, "ActionApproveAddLine", "actionApproveAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionApproveDelLine class
     */     
    protected class ActionApproveDelLine extends ItemAction {     
    
        public ActionApproveDelLine()
        {
            this(null);
        }

        public ActionApproveDelLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionApproveDelLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproveDelLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproveDelLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockEditUI.this, "ActionApproveDelLine", "actionApproveDelLine_actionPerformed", e);
        }
    }

    /**
     * output ActionFileAddLine class
     */     
    protected class ActionFileAddLine extends ItemAction {     
    
        public ActionFileAddLine()
        {
            this(null);
        }

        public ActionFileAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionFileAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFileAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFileAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockEditUI.this, "ActionFileAddLine", "actionFileAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionInsertLine class
     */     
    protected class ActionInsertLine extends ItemAction {     
    
        public ActionInsertLine()
        {
            this(null);
        }

        public ActionInsertLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionInsertLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsertLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsertLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockEditUI.this, "ActionInsertLine", "actionInsertLine_actionPerformed", e);
        }
    }

    /**
     * output ActionInsertLineAppr class
     */     
    protected class ActionInsertLineAppr extends ItemAction {     
    
        public ActionInsertLineAppr()
        {
            this(null);
        }

        public ActionInsertLineAppr(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionInsertLineAppr.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsertLineAppr.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsertLineAppr.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockEditUI.this, "ActionInsertLineAppr", "actionInsertLineAppr_actionPerformed", e);
        }
    }

    /**
     * output ActionPersonInsertLine class
     */     
    protected class ActionPersonInsertLine extends ItemAction {     
    
        public ActionPersonInsertLine()
        {
            this(null);
        }

        public ActionPersonInsertLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPersonInsertLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPersonInsertLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPersonInsertLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockEditUI.this, "ActionPersonInsertLine", "actionPersonInsertLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "SupplierStockEditUI");
    }




}