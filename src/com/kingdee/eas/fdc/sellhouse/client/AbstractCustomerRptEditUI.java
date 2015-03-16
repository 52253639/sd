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
public abstract class AbstractCustomerRptEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCustomerRptEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tblNew;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelBaseInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelExtend;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelPropertyConsultant;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelChangeRecord;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdpCustomerDetial;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tblEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSimpleCode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInsiderOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInsiderCode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInsiderLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBookedPlace;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSellBill;
    protected com.kingdee.bos.ctrl.swing.KDContainer containLinkman;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblQuestion;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCommerceChance;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTrack;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkAll;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveLinkman;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddLinkman;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblLinkman;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCode;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleCode;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInsiderOrg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInsiderCode;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInsiderLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBookedPlace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOfficeTel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFax;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOtherTel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEmail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCertificateType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCertificateNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDayOfbirth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSex;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCorporate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIdentity;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCorporateTel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCountry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProvince;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCity;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRegion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMailAddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPostalCode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBookedAddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFirstLinkman;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIndustry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterpriceProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPropertyConsultant;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRecommended;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contF7Recommended;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRecommendDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOfficeTel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFax;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOtherTel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEmail;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCertificateType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCertificateNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDayOfbirth;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSex;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCorporate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtIdentity;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCorporateTel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCountry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProvince;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCity;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRegion;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMailAddress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPostalCode;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBookedAddress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFirstLinkman;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtIndustry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEnterpriceProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFirstConsultant;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRecommended;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Recommended;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkRecommendDate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDContainer personContainer;
    protected com.kingdee.bos.ctrl.swing.KDContainer enterpriceContainer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerOrigin;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNativePlace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMaritalStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHabitationStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWorkCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContactPreferences;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMotorcycles;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWorkArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHabitationArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomerOrigin;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtNativePlace;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboMaritalStatus;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHabitationStatus;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWorkCompany;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboContactPreferences;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMotorcycles;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtWorkArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHabitationArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterpriceSize;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBookedCapital;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBusinessScope;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEmployees;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaxRegistrationNoG;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCooperateModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaxRegistrationNoD;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEnterpriceSize;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBookedCapital;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBusinessScope;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEmployees;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaxRegistrationNoG;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCooperateModel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaxRegistrationNoD;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPropertyConsultant;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCustomerChange;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateWay;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreateUnit;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUnit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCreateWay;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtCustomerChangeDetial;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuestion;
    protected com.kingdee.eas.fdc.sellhouse.SHECustomerInfo editData = null;
    protected ActionAddLinkman actionAddLinkman = null;
    protected ActionRemoveLinkman actionRemoveLinkman = null;
    protected ActionCheckAll actionCheckAll = null;
    /**
     * output class constructor
     */
    public AbstractCustomerRptEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCustomerRptEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddLinkman
        this.actionAddLinkman = new ActionAddLinkman(this);
        getActionManager().registerAction("actionAddLinkman", actionAddLinkman);
         this.actionAddLinkman.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveLinkman
        this.actionRemoveLinkman = new ActionRemoveLinkman(this);
        getActionManager().registerAction("actionRemoveLinkman", actionRemoveLinkman);
         this.actionRemoveLinkman.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCheckAll
        this.actionCheckAll = new ActionCheckAll(this);
        getActionManager().registerAction("actionCheckAll", actionCheckAll);
         this.actionCheckAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tblNew = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.panelBaseInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelExtend = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelPropertyConsultant = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelChangeRecord = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdpCustomerDetial = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblEntry = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSimpleCode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInsiderOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInsiderCode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInsiderLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBookedPlace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblSellBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.containLinkman = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblQuestion = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblCommerceChance = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblTrack = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.checkAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnRemoveLinkman = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddLinkman = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblLinkman = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboCustomerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtCode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSimpleCode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtInsiderOrg = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtInsiderCode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInsiderLevel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtBookedPlace = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOfficeTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOtherTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEmail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCertificateType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCertificateNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDayOfbirth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSex = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCorporate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIdentity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCorporateTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCountry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProvince = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRegion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMailAddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPostalCode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBookedAddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFirstLinkman = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIndustry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnterpriceProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPropertyConsultant = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRecommended = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contF7Recommended = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRecommendDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOfficeTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtFax = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOtherTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEmail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboCertificateType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtCertificateNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkDayOfbirth = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboSex = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtCorporate = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtIdentity = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtCorporateTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCountry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProvince = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCity = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRegion = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtMailAddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPostalCode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBookedAddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtFirstLinkman = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtIndustry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtEnterpriceProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtFirstConsultant = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtRecommended = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Recommended = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkRecommendDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.personContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.enterpriceContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contCustomerOrigin = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNativePlace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMaritalStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHabitationStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWorkCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContactPreferences = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMotorcycles = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWorkArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHabitationArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCustomerOrigin = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtNativePlace = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboMaritalStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtHabitationStatus = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtWorkCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboContactPreferences = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtMotorcycles = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtWorkArea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtHabitationArea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contEnterpriceSize = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBookedCapital = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBusinessScope = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEmployees = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaxRegistrationNoG = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCooperateModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaxRegistrationNoD = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtEnterpriceSize = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBookedCapital = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBusinessScope = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEmployees = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTaxRegistrationNoG = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCooperateModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTaxRegistrationNoD = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tblPropertyConsultant = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblCustomerChange = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contCreateUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateWay = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreateUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtLastUpdateUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboCreateWay = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtCustomerChangeDetial = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnQuestion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblNew.setName("tblNew");
        this.panelBaseInfo.setName("panelBaseInfo");
        this.panelExtend.setName("panelExtend");
        this.panelPropertyConsultant.setName("panelPropertyConsultant");
        this.panelChangeRecord.setName("panelChangeRecord");
        this.kdpCustomerDetial.setName("kdpCustomerDetial");
        this.tblEntry.setName("tblEntry");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contCustomerType.setName("contCustomerType");
        this.contCode.setName("contCode");
        this.contSimpleCode.setName("contSimpleCode");
        this.contInsiderOrg.setName("contInsiderOrg");
        this.contInsiderCode.setName("contInsiderCode");
        this.contInsiderLevel.setName("contInsiderLevel");
        this.contBookedPlace.setName("contBookedPlace");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.tblSellBill.setName("tblSellBill");
        this.containLinkman.setName("containLinkman");
        this.tblQuestion.setName("tblQuestion");
        this.tblCommerceChance.setName("tblCommerceChance");
        this.tblTrack.setName("tblTrack");
        this.checkAll.setName("checkAll");
        this.btnRemoveLinkman.setName("btnRemoveLinkman");
        this.btnAddLinkman.setName("btnAddLinkman");
        this.tblLinkman.setName("tblLinkman");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.comboCustomerType.setName("comboCustomerType");
        this.txtCode.setName("txtCode");
        this.txtSimpleCode.setName("txtSimpleCode");
        this.prmtInsiderOrg.setName("prmtInsiderOrg");
        this.txtInsiderCode.setName("txtInsiderCode");
        this.txtInsiderLevel.setName("txtInsiderLevel");
        this.prmtBookedPlace.setName("prmtBookedPlace");
        this.contPhone.setName("contPhone");
        this.contTel.setName("contTel");
        this.contOfficeTel.setName("contOfficeTel");
        this.contFax.setName("contFax");
        this.contOtherTel.setName("contOtherTel");
        this.contEmail.setName("contEmail");
        this.contCertificateType.setName("contCertificateType");
        this.contCertificateNumber.setName("contCertificateNumber");
        this.contDayOfbirth.setName("contDayOfbirth");
        this.contSex.setName("contSex");
        this.contCorporate.setName("contCorporate");
        this.contIdentity.setName("contIdentity");
        this.contCorporateTel.setName("contCorporateTel");
        this.contCountry.setName("contCountry");
        this.contProvince.setName("contProvince");
        this.contCity.setName("contCity");
        this.contRegion.setName("contRegion");
        this.contMailAddress.setName("contMailAddress");
        this.contPostalCode.setName("contPostalCode");
        this.contBookedAddress.setName("contBookedAddress");
        this.contFirstLinkman.setName("contFirstLinkman");
        this.contIndustry.setName("contIndustry");
        this.contEnterpriceProperty.setName("contEnterpriceProperty");
        this.contPropertyConsultant.setName("contPropertyConsultant");
        this.contRecommended.setName("contRecommended");
        this.contF7Recommended.setName("contF7Recommended");
        this.contRecommendDate.setName("contRecommendDate");
        this.txtPhone.setName("txtPhone");
        this.txtTel.setName("txtTel");
        this.txtOfficeTel.setName("txtOfficeTel");
        this.txtFax.setName("txtFax");
        this.txtOtherTel.setName("txtOtherTel");
        this.txtEmail.setName("txtEmail");
        this.comboCertificateType.setName("comboCertificateType");
        this.txtCertificateNumber.setName("txtCertificateNumber");
        this.pkDayOfbirth.setName("pkDayOfbirth");
        this.comboSex.setName("comboSex");
        this.txtCorporate.setName("txtCorporate");
        this.prmtIdentity.setName("prmtIdentity");
        this.txtCorporateTel.setName("txtCorporateTel");
        this.prmtCountry.setName("prmtCountry");
        this.prmtProvince.setName("prmtProvince");
        this.prmtCity.setName("prmtCity");
        this.prmtRegion.setName("prmtRegion");
        this.txtMailAddress.setName("txtMailAddress");
        this.txtPostalCode.setName("txtPostalCode");
        this.txtBookedAddress.setName("txtBookedAddress");
        this.txtFirstLinkman.setName("txtFirstLinkman");
        this.prmtIndustry.setName("prmtIndustry");
        this.prmtEnterpriceProperty.setName("prmtEnterpriceProperty");
        this.prmtFirstConsultant.setName("prmtFirstConsultant");
        this.txtRecommended.setName("txtRecommended");
        this.f7Recommended.setName("f7Recommended");
        this.pkRecommendDate.setName("pkRecommendDate");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.personContainer.setName("personContainer");
        this.enterpriceContainer.setName("enterpriceContainer");
        this.contCustomerOrigin.setName("contCustomerOrigin");
        this.contNativePlace.setName("contNativePlace");
        this.contMaritalStatus.setName("contMaritalStatus");
        this.contHabitationStatus.setName("contHabitationStatus");
        this.contWorkCompany.setName("contWorkCompany");
        this.contContactPreferences.setName("contContactPreferences");
        this.contMotorcycles.setName("contMotorcycles");
        this.contWorkArea.setName("contWorkArea");
        this.contHabitationArea.setName("contHabitationArea");
        this.prmtCustomerOrigin.setName("prmtCustomerOrigin");
        this.prmtNativePlace.setName("prmtNativePlace");
        this.comboMaritalStatus.setName("comboMaritalStatus");
        this.prmtHabitationStatus.setName("prmtHabitationStatus");
        this.txtWorkCompany.setName("txtWorkCompany");
        this.comboContactPreferences.setName("comboContactPreferences");
        this.txtMotorcycles.setName("txtMotorcycles");
        this.prmtWorkArea.setName("prmtWorkArea");
        this.prmtHabitationArea.setName("prmtHabitationArea");
        this.contEnterpriceSize.setName("contEnterpriceSize");
        this.contBookedCapital.setName("contBookedCapital");
        this.contBusinessScope.setName("contBusinessScope");
        this.contEmployees.setName("contEmployees");
        this.contTaxRegistrationNoG.setName("contTaxRegistrationNoG");
        this.contCooperateModel.setName("contCooperateModel");
        this.contTaxRegistrationNoD.setName("contTaxRegistrationNoD");
        this.prmtEnterpriceSize.setName("prmtEnterpriceSize");
        this.txtBookedCapital.setName("txtBookedCapital");
        this.txtBusinessScope.setName("txtBusinessScope");
        this.txtEmployees.setName("txtEmployees");
        this.txtTaxRegistrationNoG.setName("txtTaxRegistrationNoG");
        this.prmtCooperateModel.setName("prmtCooperateModel");
        this.txtTaxRegistrationNoD.setName("txtTaxRegistrationNoD");
        this.tblPropertyConsultant.setName("tblPropertyConsultant");
        this.tblCustomerChange.setName("tblCustomerChange");
        this.contCreateUnit.setName("contCreateUnit");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contCreator.setName("contCreator");
        this.contLastUpdateUnit.setName("contLastUpdateUnit");
        this.contCreateWay.setName("contCreateWay");
        this.contProject.setName("contProject");
        this.prmtCreateUnit.setName("prmtCreateUnit");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.prmtCreator.setName("prmtCreator");
        this.prmtLastUpdateUnit.setName("prmtLastUpdateUnit");
        this.comboCreateWay.setName("comboCreateWay");
        this.prmtProject.setName("prmtProject");
        this.kdtCustomerChangeDetial.setName("kdtCustomerChangeDetial");
        this.btnQuestion.setName("btnQuestion");
        // CoreUI		
        this.setPreferredSize(new Dimension(1023,695));		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemSave.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.menuBiz.setVisible(false);
        // tblNew
        this.tblNew.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    tblNew_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // panelBaseInfo
        // panelExtend
        // panelPropertyConsultant
        // panelChangeRecord
        // kdpCustomerDetial		
        this.kdpCustomerDetial.setBorder(null);
        // tblEntry
        this.tblEntry.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    tblEntry_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contCustomerType		
        this.contCustomerType.setBoundLabelText(resHelper.getString("contCustomerType.boundLabelText"));		
        this.contCustomerType.setBoundLabelLength(100);		
        this.contCustomerType.setBoundLabelUnderline(true);
        // contCode		
        this.contCode.setBoundLabelText(resHelper.getString("contCode.boundLabelText"));		
        this.contCode.setBoundLabelLength(100);		
        this.contCode.setBoundLabelUnderline(true);
        // contSimpleCode		
        this.contSimpleCode.setBoundLabelText(resHelper.getString("contSimpleCode.boundLabelText"));		
        this.contSimpleCode.setBoundLabelLength(100);		
        this.contSimpleCode.setBoundLabelUnderline(true);
        // contInsiderOrg		
        this.contInsiderOrg.setBoundLabelText(resHelper.getString("contInsiderOrg.boundLabelText"));		
        this.contInsiderOrg.setBoundLabelLength(100);		
        this.contInsiderOrg.setBoundLabelUnderline(true);
        // contInsiderCode		
        this.contInsiderCode.setBoundLabelText(resHelper.getString("contInsiderCode.boundLabelText"));		
        this.contInsiderCode.setBoundLabelLength(100);		
        this.contInsiderCode.setBoundLabelUnderline(true);
        // contInsiderLevel		
        this.contInsiderLevel.setBoundLabelText(resHelper.getString("contInsiderLevel.boundLabelText"));		
        this.contInsiderLevel.setBoundLabelLength(100);		
        this.contInsiderLevel.setBoundLabelUnderline(true);
        // contBookedPlace		
        this.contBookedPlace.setBoundLabelText(resHelper.getString("contBookedPlace.boundLabelText"));		
        this.contBookedPlace.setBoundLabelLength(100);		
        this.contBookedPlace.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // tblSellBill
		String tblSellBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"orgUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"sellProject.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"room.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"customerNames\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"bizState\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"bizDate\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"payType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dealTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dealBuildPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dealRoomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saleMan.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{orgUnit.name}</t:Cell><t:Cell>$Resource{sellProject.name}</t:Cell><t:Cell>$Resource{room.name}</t:Cell><t:Cell>$Resource{customerNames}</t:Cell><t:Cell>$Resource{bizState}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{payType.name}</t:Cell><t:Cell>$Resource{lastAgio}</t:Cell><t:Cell>$Resource{dealTotalAmount}</t:Cell><t:Cell>$Resource{dealBuildPrice}</t:Cell><t:Cell>$Resource{dealRoomPrice}</t:Cell><t:Cell>$Resource{saleMan.name}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{type}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSellBill.setFormatXml(resHelper.translateString("tblSellBill",tblSellBillStrXML));
        this.tblSellBill.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblSellBill_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // containLinkman
        // tblQuestion
		String tblQuestionStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"orgUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sellProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"questionPaper.bizScene\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"inputDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"questionPaper.topric\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"bizDate\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{orgUnit.name}</t:Cell><t:Cell>$Resource{sellProject.name}</t:Cell><t:Cell>$Resource{questionPaper.bizScene}</t:Cell><t:Cell>$Resource{inputDate}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{questionPaper.topric}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

        

        // tblCommerceChance
		String tblCommerceChanceStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"sysType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"orgUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sellProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"status\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceLevel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceChanceStage.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"effectiveDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"commerceSrc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"worth\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"probability\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saleMan.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{sysType}</t:Cell><t:Cell>$Resource{orgUnit.name}</t:Cell><t:Cell>$Resource{sellProject.name}</t:Cell><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{commerceLevel.name}</t:Cell><t:Cell>$Resource{commerceChanceStage.name}</t:Cell><t:Cell>$Resource{effectiveDate}</t:Cell><t:Cell>$Resource{commerceSrc}</t:Cell><t:Cell>$Resource{worth}</t:Cell><t:Cell>$Resource{probability}</t:Cell><t:Cell>$Resource{saleMan.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

        

        // tblTrack
		String tblTrackStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"trackDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"trackType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"interactionType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"trackEvent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"trackContent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"propertyConsultant\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"createTime\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{trackDate}</t:Cell><t:Cell>$Resource{trackType}</t:Cell><t:Cell>$Resource{interactionType}</t:Cell><t:Cell>$Resource{trackEvent}</t:Cell><t:Cell>$Resource{trackContent}</t:Cell><t:Cell>$Resource{propertyConsultant}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblTrack.setFormatXml(resHelper.translateString("tblTrack",tblTrackStrXML));
        this.tblTrack.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblTrack_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // checkAll
        this.checkAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionCheckAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.checkAll.setText(resHelper.getString("checkAll.text"));
        // btnRemoveLinkman
        this.btnRemoveLinkman.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveLinkman, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemoveLinkman.setText(resHelper.getString("btnRemoveLinkman.text"));
        // btnAddLinkman
        this.btnAddLinkman.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLinkman, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddLinkman.setText(resHelper.getString("btnAddLinkman.text"));
        // tblLinkman
		String tblLinkmanStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"isAssociation\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"associationType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isEmergencyContact\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isPrincipal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"principalNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"relation\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"officeTel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fax\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"otherTel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"email\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"certificateType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"certificateNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dayOfBirth\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"mailAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bookedAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"postalCode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol22\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{isAssociation}</t:Cell><t:Cell>$Resource{associationType}</t:Cell><t:Cell>$Resource{isEmergencyContact}</t:Cell><t:Cell>$Resource{isPrincipal}</t:Cell><t:Cell>$Resource{principalNo}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{relation}</t:Cell><t:Cell>$Resource{sex}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{tel}</t:Cell><t:Cell>$Resource{officeTel}</t:Cell><t:Cell>$Resource{fax}</t:Cell><t:Cell>$Resource{otherTel}</t:Cell><t:Cell>$Resource{email}</t:Cell><t:Cell>$Resource{certificateType}</t:Cell><t:Cell>$Resource{certificateNumber}</t:Cell><t:Cell>$Resource{dayOfBirth}</t:Cell><t:Cell>$Resource{mailAddress}</t:Cell><t:Cell>$Resource{bookedAddress}</t:Cell><t:Cell>$Resource{postalCode}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

        

        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setEnabled(false);
        this.txtNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtNumber_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);		
        this.txtName.setEnabled(false);
        // comboCustomerType		
        this.comboCustomerType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basecrm.CustomerTypeEnum").toArray());		
        this.comboCustomerType.setRequired(true);		
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
        // txtCode		
        this.txtCode.setMaxLength(80);		
        this.txtCode.setDoubleBuffered(true);
        // txtSimpleCode		
        this.txtSimpleCode.setMaxLength(80);
        // prmtInsiderOrg		
        this.prmtInsiderOrg.setDisplayFormat("$name$");		
        this.prmtInsiderOrg.setEditFormat("$number$");		
        this.prmtInsiderOrg.setCommitFormat("$number$");		
        this.prmtInsiderOrg.setEnabled(false);
        // txtInsiderCode		
        this.txtInsiderCode.setMaxLength(80);		
        this.txtInsiderCode.setEnabled(false);
        // txtInsiderLevel		
        this.txtInsiderLevel.setMaxLength(80);		
        this.txtInsiderLevel.setEnabled(false);
        // prmtBookedPlace		
        this.prmtBookedPlace.setDisplayFormat("$name$");		
        this.prmtBookedPlace.setEditFormat("$number$");		
        this.prmtBookedPlace.setCommitFormat("$number$");		
        this.prmtBookedPlace.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CountryQuery");
        // contPhone		
        this.contPhone.setBoundLabelText(resHelper.getString("contPhone.boundLabelText"));		
        this.contPhone.setBoundLabelLength(100);		
        this.contPhone.setBoundLabelUnderline(true);
        // contTel		
        this.contTel.setBoundLabelText(resHelper.getString("contTel.boundLabelText"));		
        this.contTel.setBoundLabelLength(100);		
        this.contTel.setBoundLabelUnderline(true);
        // contOfficeTel		
        this.contOfficeTel.setBoundLabelText(resHelper.getString("contOfficeTel.boundLabelText"));		
        this.contOfficeTel.setBoundLabelLength(100);		
        this.contOfficeTel.setBoundLabelUnderline(true);
        // contFax		
        this.contFax.setBoundLabelText(resHelper.getString("contFax.boundLabelText"));		
        this.contFax.setBoundLabelLength(100);		
        this.contFax.setBoundLabelUnderline(true);
        // contOtherTel		
        this.contOtherTel.setBoundLabelText(resHelper.getString("contOtherTel.boundLabelText"));		
        this.contOtherTel.setBoundLabelLength(100);		
        this.contOtherTel.setBoundLabelUnderline(true);
        // contEmail		
        this.contEmail.setBoundLabelText(resHelper.getString("contEmail.boundLabelText"));		
        this.contEmail.setBoundLabelLength(100);		
        this.contEmail.setBoundLabelUnderline(true);
        // contCertificateType		
        this.contCertificateType.setBoundLabelText(resHelper.getString("contCertificateType.boundLabelText"));		
        this.contCertificateType.setBoundLabelLength(100);		
        this.contCertificateType.setBoundLabelUnderline(true);
        // contCertificateNumber		
        this.contCertificateNumber.setBoundLabelText(resHelper.getString("contCertificateNumber.boundLabelText"));		
        this.contCertificateNumber.setBoundLabelLength(100);		
        this.contCertificateNumber.setBoundLabelUnderline(true);
        // contDayOfbirth		
        this.contDayOfbirth.setBoundLabelText(resHelper.getString("contDayOfbirth.boundLabelText"));		
        this.contDayOfbirth.setBoundLabelLength(100);		
        this.contDayOfbirth.setBoundLabelUnderline(true);
        // contSex		
        this.contSex.setBoundLabelText(resHelper.getString("contSex.boundLabelText"));		
        this.contSex.setBoundLabelLength(100);		
        this.contSex.setBoundLabelUnderline(true);
        // contCorporate		
        this.contCorporate.setBoundLabelText(resHelper.getString("contCorporate.boundLabelText"));		
        this.contCorporate.setBoundLabelLength(100);		
        this.contCorporate.setBoundLabelUnderline(true);
        // contIdentity		
        this.contIdentity.setBoundLabelText(resHelper.getString("contIdentity.boundLabelText"));		
        this.contIdentity.setBoundLabelLength(100);		
        this.contIdentity.setBoundLabelUnderline(true);
        // contCorporateTel		
        this.contCorporateTel.setBoundLabelText(resHelper.getString("contCorporateTel.boundLabelText"));		
        this.contCorporateTel.setBoundLabelLength(100);		
        this.contCorporateTel.setBoundLabelUnderline(true);
        // contCountry		
        this.contCountry.setBoundLabelText(resHelper.getString("contCountry.boundLabelText"));		
        this.contCountry.setBoundLabelLength(100);		
        this.contCountry.setBoundLabelUnderline(true);
        // contProvince		
        this.contProvince.setBoundLabelText(resHelper.getString("contProvince.boundLabelText"));		
        this.contProvince.setBoundLabelLength(100);		
        this.contProvince.setBoundLabelUnderline(true);
        // contCity		
        this.contCity.setBoundLabelText(resHelper.getString("contCity.boundLabelText"));		
        this.contCity.setBoundLabelLength(100);		
        this.contCity.setBoundLabelUnderline(true);
        // contRegion		
        this.contRegion.setBoundLabelText(resHelper.getString("contRegion.boundLabelText"));		
        this.contRegion.setBoundLabelLength(100);		
        this.contRegion.setBoundLabelUnderline(true);
        // contMailAddress		
        this.contMailAddress.setBoundLabelText(resHelper.getString("contMailAddress.boundLabelText"));		
        this.contMailAddress.setBoundLabelLength(100);		
        this.contMailAddress.setBoundLabelUnderline(true);
        // contPostalCode		
        this.contPostalCode.setBoundLabelText(resHelper.getString("contPostalCode.boundLabelText"));		
        this.contPostalCode.setBoundLabelLength(100);		
        this.contPostalCode.setBoundLabelUnderline(true);
        // contBookedAddress		
        this.contBookedAddress.setBoundLabelText(resHelper.getString("contBookedAddress.boundLabelText"));		
        this.contBookedAddress.setBoundLabelLength(100);		
        this.contBookedAddress.setBoundLabelUnderline(true);
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
        // contPropertyConsultant		
        this.contPropertyConsultant.setBoundLabelText(resHelper.getString("contPropertyConsultant.boundLabelText"));		
        this.contPropertyConsultant.setBoundLabelLength(100);		
        this.contPropertyConsultant.setBoundLabelUnderline(true);
        // contRecommended		
        this.contRecommended.setBoundLabelText(resHelper.getString("contRecommended.boundLabelText"));		
        this.contRecommended.setBoundLabelLength(100);		
        this.contRecommended.setBoundLabelUnderline(true);		
        this.contRecommended.setEnabled(false);		
        this.contRecommended.setVisible(false);
        // contF7Recommended		
        this.contF7Recommended.setBoundLabelText(resHelper.getString("contF7Recommended.boundLabelText"));		
        this.contF7Recommended.setBoundLabelLength(100);		
        this.contF7Recommended.setBoundLabelUnderline(true);
        // contRecommendDate		
        this.contRecommendDate.setBoundLabelText(resHelper.getString("contRecommendDate.boundLabelText"));		
        this.contRecommendDate.setBoundLabelLength(100);		
        this.contRecommendDate.setBoundLabelUnderline(true);
        // txtPhone		
        this.txtPhone.setMaxLength(30);
        // txtTel		
        this.txtTel.setMaxLength(40);
        // txtOfficeTel		
        this.txtOfficeTel.setMaxLength(40);
        // txtFax		
        this.txtFax.setMaxLength(40);
        // txtOtherTel		
        this.txtOtherTel.setMaxLength(40);
        // txtEmail		
        this.txtEmail.setMaxLength(80);
        // comboCertificateType
        // txtCertificateNumber		
        this.txtCertificateNumber.setMaxLength(80);
        this.txtCertificateNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtCertificateNumber_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // pkDayOfbirth
        // comboSex		
        this.comboSex.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SexEnum").toArray());
        // txtCorporate		
        this.txtCorporate.setMaxLength(80);
        // prmtIdentity		
        this.prmtIdentity.setCommitFormat("$number$");		
        this.prmtIdentity.setDisplayFormat("$name$");		
        this.prmtIdentity.setEditFormat("$number$");		
        this.prmtIdentity.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
        // txtCorporateTel		
        this.txtCorporateTel.setMaxLength(80);
        // prmtCountry		
        this.prmtCountry.setDisplayFormat("$name$");		
        this.prmtCountry.setEditFormat("$number$");		
        this.prmtCountry.setCommitFormat("$number$");		
        this.prmtCountry.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CountryQuery");
        // prmtProvince		
        this.prmtProvince.setDisplayFormat("$name$");		
        this.prmtProvince.setEditFormat("$number$");		
        this.prmtProvince.setCommitFormat("$number$");		
        this.prmtProvince.setQueryInfo("com.kingdee.eas.basedata.assistant.app.ProvinceQuery");
        // prmtCity		
        this.prmtCity.setCommitFormat("$number$");		
        this.prmtCity.setDisplayFormat("$name$");		
        this.prmtCity.setEditFormat("$number$");		
        this.prmtCity.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CityQuery");
        // prmtRegion		
        this.prmtRegion.setDisplayFormat("$name$");		
        this.prmtRegion.setEditFormat("$number$");		
        this.prmtRegion.setCommitFormat("$number$");		
        this.prmtRegion.setQueryInfo("com.kingdee.eas.basedata.assistant.app.RegionQuery");
        // txtMailAddress		
        this.txtMailAddress.setMaxLength(80);
        // txtPostalCode		
        this.txtPostalCode.setMaxLength(10);
        // txtBookedAddress		
        this.txtBookedAddress.setMaxLength(80);
        // txtFirstLinkman		
        this.txtFirstLinkman.setMaxLength(80);		
        this.txtFirstLinkman.setRequired(true);
        // prmtIndustry		
        this.prmtIndustry.setDisplayFormat("$name$");		
        this.prmtIndustry.setEditFormat("$number$");		
        this.prmtIndustry.setCommitFormat("$number$");		
        this.prmtIndustry.setQueryInfo("com.kingdee.eas.basedata.assistant.app.IndustryQuery");
        // prmtEnterpriceProperty		
        this.prmtEnterpriceProperty.setDisplayFormat("$name$");		
        this.prmtEnterpriceProperty.setEditFormat("$number$");		
        this.prmtEnterpriceProperty.setCommitFormat("$number$");		
        this.prmtEnterpriceProperty.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
        // prmtFirstConsultant		
        this.prmtFirstConsultant.setDisplayFormat("$name$");		
        this.prmtFirstConsultant.setEditFormat("$number$");		
        this.prmtFirstConsultant.setCommitFormat("$number$");		
        this.prmtFirstConsultant.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtFirstConsultant.setRequired(true);
        // txtRecommended
        // f7Recommended		
        this.f7Recommended.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7SCSHECustomerQuery");		
        this.f7Recommended.setCommitFormat("$name$");		
        this.f7Recommended.setDisplayFormat("$name$");		
        this.f7Recommended.setEditFormat("$name$");
        this.f7Recommended.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Recommended_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkRecommendDate		
        this.pkRecommendDate.setEnabled(false);
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // personContainer		
        this.personContainer.setTitle(resHelper.getString("personContainer.title"));
        // enterpriceContainer		
        this.enterpriceContainer.setTitle(resHelper.getString("enterpriceContainer.title"));
        // contCustomerOrigin		
        this.contCustomerOrigin.setBoundLabelText(resHelper.getString("contCustomerOrigin.boundLabelText"));		
        this.contCustomerOrigin.setBoundLabelLength(100);		
        this.contCustomerOrigin.setBoundLabelUnderline(true);
        // contNativePlace		
        this.contNativePlace.setBoundLabelText(resHelper.getString("contNativePlace.boundLabelText"));		
        this.contNativePlace.setBoundLabelLength(100);		
        this.contNativePlace.setBoundLabelUnderline(true);
        // contMaritalStatus		
        this.contMaritalStatus.setBoundLabelText(resHelper.getString("contMaritalStatus.boundLabelText"));		
        this.contMaritalStatus.setBoundLabelLength(100);		
        this.contMaritalStatus.setBoundLabelUnderline(true);
        // contHabitationStatus		
        this.contHabitationStatus.setBoundLabelText(resHelper.getString("contHabitationStatus.boundLabelText"));		
        this.contHabitationStatus.setBoundLabelLength(100);		
        this.contHabitationStatus.setBoundLabelUnderline(true);
        // contWorkCompany		
        this.contWorkCompany.setBoundLabelText(resHelper.getString("contWorkCompany.boundLabelText"));		
        this.contWorkCompany.setBoundLabelLength(100);		
        this.contWorkCompany.setBoundLabelUnderline(true);
        // contContactPreferences		
        this.contContactPreferences.setBoundLabelText(resHelper.getString("contContactPreferences.boundLabelText"));		
        this.contContactPreferences.setBoundLabelLength(100);		
        this.contContactPreferences.setBoundLabelUnderline(true);
        // contMotorcycles		
        this.contMotorcycles.setBoundLabelText(resHelper.getString("contMotorcycles.boundLabelText"));		
        this.contMotorcycles.setBoundLabelLength(100);		
        this.contMotorcycles.setBoundLabelUnderline(true);
        // contWorkArea		
        this.contWorkArea.setBoundLabelText(resHelper.getString("contWorkArea.boundLabelText"));		
        this.contWorkArea.setBoundLabelLength(100);		
        this.contWorkArea.setBoundLabelUnderline(true);
        // contHabitationArea		
        this.contHabitationArea.setBoundLabelText(resHelper.getString("contHabitationArea.boundLabelText"));		
        this.contHabitationArea.setBoundLabelLength(100);		
        this.contHabitationArea.setBoundLabelUnderline(true);
        // prmtCustomerOrigin		
        this.prmtCustomerOrigin.setDisplayFormat("$name$");		
        this.prmtCustomerOrigin.setEditFormat("$number$");		
        this.prmtCustomerOrigin.setCommitFormat("$number$");		
        this.prmtCustomerOrigin.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
        // prmtNativePlace		
        this.prmtNativePlace.setDisplayFormat("$name$");		
        this.prmtNativePlace.setEditFormat("$number$");		
        this.prmtNativePlace.setCommitFormat("$number$");		
        this.prmtNativePlace.setQueryInfo("com.kingdee.eas.basedata.assistant.app.RegionQuery");
        // comboMaritalStatus		
        this.comboMaritalStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basecrm.MaritalStatusEnum").toArray());
        // prmtHabitationStatus		
        this.prmtHabitationStatus.setDisplayFormat("$name$");		
        this.prmtHabitationStatus.setEditFormat("$number$");		
        this.prmtHabitationStatus.setCommitFormat("$number$");		
        this.prmtHabitationStatus.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
        // txtWorkCompany		
        this.txtWorkCompany.setMaxLength(255);
        // comboContactPreferences		
        this.comboContactPreferences.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basecrm.ContactPreferencesEnum").toArray());
        // txtMotorcycles		
        this.txtMotorcycles.setMaxLength(80);
        // prmtWorkArea		
        this.prmtWorkArea.setDisplayFormat("$name$");		
        this.prmtWorkArea.setEditFormat("$number$");		
        this.prmtWorkArea.setCommitFormat("$number$");		
        this.prmtWorkArea.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECusAssistantDataQuery");
        // prmtHabitationArea		
        this.prmtHabitationArea.setDisplayFormat("$name$");		
        this.prmtHabitationArea.setEditFormat("$number$");		
        this.prmtHabitationArea.setCommitFormat("$number$");		
        this.prmtHabitationArea.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECusAssistantDataQuery");
        // contEnterpriceSize		
        this.contEnterpriceSize.setBoundLabelText(resHelper.getString("contEnterpriceSize.boundLabelText"));		
        this.contEnterpriceSize.setBoundLabelLength(100);		
        this.contEnterpriceSize.setBoundLabelUnderline(true);
        // contBookedCapital		
        this.contBookedCapital.setBoundLabelText(resHelper.getString("contBookedCapital.boundLabelText"));		
        this.contBookedCapital.setBoundLabelLength(100);		
        this.contBookedCapital.setBoundLabelUnderline(true);
        // contBusinessScope		
        this.contBusinessScope.setBoundLabelText(resHelper.getString("contBusinessScope.boundLabelText"));		
        this.contBusinessScope.setBoundLabelLength(100);		
        this.contBusinessScope.setBoundLabelUnderline(true);
        // contEmployees		
        this.contEmployees.setBoundLabelText(resHelper.getString("contEmployees.boundLabelText"));		
        this.contEmployees.setBoundLabelLength(100);		
        this.contEmployees.setBoundLabelUnderline(true);
        // contTaxRegistrationNoG		
        this.contTaxRegistrationNoG.setBoundLabelText(resHelper.getString("contTaxRegistrationNoG.boundLabelText"));		
        this.contTaxRegistrationNoG.setBoundLabelLength(100);		
        this.contTaxRegistrationNoG.setBoundLabelUnderline(true);
        // contCooperateModel		
        this.contCooperateModel.setBoundLabelText(resHelper.getString("contCooperateModel.boundLabelText"));		
        this.contCooperateModel.setBoundLabelLength(100);		
        this.contCooperateModel.setBoundLabelUnderline(true);
        // contTaxRegistrationNoD		
        this.contTaxRegistrationNoD.setBoundLabelText(resHelper.getString("contTaxRegistrationNoD.boundLabelText"));		
        this.contTaxRegistrationNoD.setBoundLabelLength(100);		
        this.contTaxRegistrationNoD.setBoundLabelUnderline(true);
        // prmtEnterpriceSize		
        this.prmtEnterpriceSize.setDisplayFormat("$name$");		
        this.prmtEnterpriceSize.setEditFormat("$number$");		
        this.prmtEnterpriceSize.setCommitFormat("$number$");		
        this.prmtEnterpriceSize.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
        // txtBookedCapital		
        this.txtBookedCapital.setMaxLength(80);
        // txtBusinessScope		
        this.txtBusinessScope.setMaxLength(80);
        // txtEmployees		
        this.txtEmployees.setMaxLength(80);
        // txtTaxRegistrationNoG		
        this.txtTaxRegistrationNoG.setMaxLength(80);
        // prmtCooperateModel		
        this.prmtCooperateModel.setDisplayFormat("$name$");		
        this.prmtCooperateModel.setEditFormat("$number$");		
        this.prmtCooperateModel.setCommitFormat("$number$");		
        this.prmtCooperateModel.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
        // txtTaxRegistrationNoD		
        this.txtTaxRegistrationNoD.setMaxLength(80);
        // tblPropertyConsultant
		String tblPropertyConsultantStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"CU.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"group.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{CU.name}</t:Cell><t:Cell>$Resource{group.name}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblPropertyConsultant.setFormatXml(resHelper.translateString("tblPropertyConsultant",tblPropertyConsultantStrXML));

        

        // tblCustomerChange
		String tblCustomerChangeStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"oldField\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"newField\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"orgUnit.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{oldField}</t:Cell><t:Cell>$Resource{newField}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{orgUnit.name}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblCustomerChange.setFormatXml(resHelper.translateString("tblCustomerChange",tblCustomerChangeStrXML));

        

        // contCreateUnit		
        this.contCreateUnit.setBoundLabelText(resHelper.getString("contCreateUnit.boundLabelText"));		
        this.contCreateUnit.setBoundLabelLength(100);		
        this.contCreateUnit.setBoundLabelUnderline(true);		
        this.contCreateUnit.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);
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
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);		
        this.contProject.setEnabled(false);
        // prmtCreateUnit		
        this.prmtCreateUnit.setEnabled(false);		
        this.prmtCreateUnit.setDisplayFormat("$name$");		
        this.prmtCreateUnit.setEditFormat("$number$");		
        this.prmtCreateUnit.setCommitFormat("$number$");
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);		
        this.prmtLastUpdateUser.setCommitFormat("$number$");		
        this.prmtLastUpdateUser.setEditFormat("$number$");		
        this.prmtLastUpdateUser.setDisplayFormat("$name$");
        // pkLastUpdateTime		
        this.pkLastUpdateTime.setEnabled(false);
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
        // prmtProject		
        this.prmtProject.setEnabled(false);		
        this.prmtProject.setCommitFormat("$number$");		
        this.prmtProject.setEditFormat("$number$");		
        this.prmtProject.setDisplayFormat("$name$");
        // kdtCustomerChangeDetial
		String kdtCustomerChangeDetialStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"sheCustomer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"company.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"propertyConsultant.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"reason\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"user.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"time\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{sheCustomer.name}</t:Cell><t:Cell>$Resource{company.name}</t:Cell><t:Cell>$Resource{propertyConsultant.name}</t:Cell><t:Cell>$Resource{reason}</t:Cell><t:Cell>$Resource{user.name}</t:Cell><t:Cell>$Resource{time}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtCustomerChangeDetial.setFormatXml(resHelper.translateString("kdtCustomerChangeDetial",kdtCustomerChangeDetialStrXML));

        

        // btnQuestion		
        this.btnQuestion.setText(resHelper.getString("btnQuestion.text"));		
        this.btnQuestion.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgMenumodule_icon_finance"));
        this.btnQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnQuestion_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
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
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1023, 695));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1023, 695));
        tblNew.setBounds(new Rectangle(4, 4, 1017, 688));
        this.add(tblNew, new KDLayout.Constraints(4, 4, 1017, 688, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //tblNew
        tblNew.add(panelBaseInfo, resHelper.getString("panelBaseInfo.constraints"));
        tblNew.add(panelExtend, resHelper.getString("panelExtend.constraints"));
        tblNew.add(panelPropertyConsultant, resHelper.getString("panelPropertyConsultant.constraints"));
        tblNew.add(panelChangeRecord, resHelper.getString("panelChangeRecord.constraints"));
        tblNew.add(kdpCustomerDetial, resHelper.getString("kdpCustomerDetial.constraints"));
        //panelBaseInfo
        panelBaseInfo.setLayout(new KDLayout());
        panelBaseInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1016, 655));        tblEntry.setBounds(new Rectangle(2, 370, 1007, 283));
        panelBaseInfo.add(tblEntry, new KDLayout.Constraints(2, 370, 1007, 283, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(10, 8, 270, 19));
        panelBaseInfo.add(contNumber, new KDLayout.Constraints(10, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(730, 8, 270, 19));
        panelBaseInfo.add(contName, new KDLayout.Constraints(730, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCustomerType.setBounds(new Rectangle(370, 8, 270, 19));
        panelBaseInfo.add(contCustomerType, new KDLayout.Constraints(370, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCode.setBounds(new Rectangle(370, 30, 270, 19));
        panelBaseInfo.add(contCode, new KDLayout.Constraints(370, 30, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSimpleCode.setBounds(new Rectangle(730, 30, 270, 19));
        panelBaseInfo.add(contSimpleCode, new KDLayout.Constraints(730, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInsiderOrg.setBounds(new Rectangle(10, 52, 270, 19));
        panelBaseInfo.add(contInsiderOrg, new KDLayout.Constraints(10, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInsiderCode.setBounds(new Rectangle(370, 52, 270, 19));
        panelBaseInfo.add(contInsiderCode, new KDLayout.Constraints(370, 52, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInsiderLevel.setBounds(new Rectangle(730, 52, 270, 19));
        panelBaseInfo.add(contInsiderLevel, new KDLayout.Constraints(730, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBookedPlace.setBounds(new Rectangle(10, 30, 270, 19));
        panelBaseInfo.add(contBookedPlace, new KDLayout.Constraints(10, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(2, 81, 1007, 211));
        panelBaseInfo.add(kDContainer1, new KDLayout.Constraints(2, 81, 1007, 211, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer2.setBounds(new Rectangle(2, 295, 1007, 69));
        panelBaseInfo.add(kDContainer2, new KDLayout.Constraints(2, 295, 1007, 69, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //tblEntry
        tblEntry.add(tblSellBill, resHelper.getString("tblSellBill.constraints"));
        tblEntry.add(containLinkman, resHelper.getString("containLinkman.constraints"));
        tblEntry.add(tblQuestion, resHelper.getString("tblQuestion.constraints"));
        tblEntry.add(tblCommerceChance, resHelper.getString("tblCommerceChance.constraints"));
        tblEntry.add(tblTrack, resHelper.getString("tblTrack.constraints"));
        //containLinkman
        containLinkman.getContentPane().setLayout(new KDLayout());
        containLinkman.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 1006, 250));        checkAll.setBounds(new Rectangle(3, 2, 88, 19));
        containLinkman.getContentPane().add(checkAll, new KDLayout.Constraints(3, 2, 88, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemoveLinkman.setBounds(new Rectangle(906, 2, 94, 19));
        containLinkman.getContentPane().add(btnRemoveLinkman, new KDLayout.Constraints(906, 2, 94, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddLinkman.setBounds(new Rectangle(802, 2, 94, 19));
        containLinkman.getContentPane().add(btnAddLinkman, new KDLayout.Constraints(802, 2, 94, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tblLinkman.setBounds(new Rectangle(3, 24, 997, 206));
        containLinkman.getContentPane().add(tblLinkman, new KDLayout.Constraints(3, 24, 997, 206, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contCustomerType
        contCustomerType.setBoundEditor(comboCustomerType);
        //contCode
        contCode.setBoundEditor(txtCode);
        //contSimpleCode
        contSimpleCode.setBoundEditor(txtSimpleCode);
        //contInsiderOrg
        contInsiderOrg.setBoundEditor(prmtInsiderOrg);
        //contInsiderCode
        contInsiderCode.setBoundEditor(txtInsiderCode);
        //contInsiderLevel
        contInsiderLevel.setBoundEditor(txtInsiderLevel);
        //contBookedPlace
        contBookedPlace.setBoundEditor(prmtBookedPlace);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(2, 81, 1007, 211));        contPhone.setBounds(new Rectangle(7, 30, 270, 19));
        kDContainer1.getContentPane().add(contPhone, new KDLayout.Constraints(7, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTel.setBounds(new Rectangle(367, 30, 270, 19));
        kDContainer1.getContentPane().add(contTel, new KDLayout.Constraints(367, 30, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOfficeTel.setBounds(new Rectangle(727, 30, 270, 19));
        kDContainer1.getContentPane().add(contOfficeTel, new KDLayout.Constraints(727, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFax.setBounds(new Rectangle(7, 52, 270, 19));
        kDContainer1.getContentPane().add(contFax, new KDLayout.Constraints(7, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOtherTel.setBounds(new Rectangle(367, 52, 270, 19));
        kDContainer1.getContentPane().add(contOtherTel, new KDLayout.Constraints(367, 52, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEmail.setBounds(new Rectangle(727, 52, 270, 19));
        kDContainer1.getContentPane().add(contEmail, new KDLayout.Constraints(727, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCertificateType.setBounds(new Rectangle(7, 74, 270, 19));
        kDContainer1.getContentPane().add(contCertificateType, new KDLayout.Constraints(7, 74, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCertificateNumber.setBounds(new Rectangle(367, 74, 270, 19));
        kDContainer1.getContentPane().add(contCertificateNumber, new KDLayout.Constraints(367, 74, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDayOfbirth.setBounds(new Rectangle(727, 74, 270, 19));
        kDContainer1.getContentPane().add(contDayOfbirth, new KDLayout.Constraints(727, 74, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSex.setBounds(new Rectangle(7, 96, 270, 19));
        kDContainer1.getContentPane().add(contSex, new KDLayout.Constraints(7, 96, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCorporate.setBounds(new Rectangle(7, 96, 270, 19));
        kDContainer1.getContentPane().add(contCorporate, new KDLayout.Constraints(7, 96, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIdentity.setBounds(new Rectangle(367, 96, 270, 19));
        kDContainer1.getContentPane().add(contIdentity, new KDLayout.Constraints(367, 96, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCorporateTel.setBounds(new Rectangle(367, 96, 270, 19));
        kDContainer1.getContentPane().add(contCorporateTel, new KDLayout.Constraints(367, 96, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCountry.setBounds(new Rectangle(727, 96, 270, 19));
        kDContainer1.getContentPane().add(contCountry, new KDLayout.Constraints(727, 96, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contProvince.setBounds(new Rectangle(7, 118, 270, 19));
        kDContainer1.getContentPane().add(contProvince, new KDLayout.Constraints(7, 118, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCity.setBounds(new Rectangle(367, 118, 270, 19));
        kDContainer1.getContentPane().add(contCity, new KDLayout.Constraints(367, 118, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRegion.setBounds(new Rectangle(727, 118, 270, 19));
        kDContainer1.getContentPane().add(contRegion, new KDLayout.Constraints(727, 118, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contMailAddress.setBounds(new Rectangle(7, 140, 270, 19));
        kDContainer1.getContentPane().add(contMailAddress, new KDLayout.Constraints(7, 140, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPostalCode.setBounds(new Rectangle(727, 140, 270, 19));
        kDContainer1.getContentPane().add(contPostalCode, new KDLayout.Constraints(727, 140, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBookedAddress.setBounds(new Rectangle(367, 140, 270, 19));
        kDContainer1.getContentPane().add(contBookedAddress, new KDLayout.Constraints(367, 140, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFirstLinkman.setBounds(new Rectangle(7, 8, 270, 19));
        kDContainer1.getContentPane().add(contFirstLinkman, new KDLayout.Constraints(7, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIndustry.setBounds(new Rectangle(367, 8, 270, 19));
        kDContainer1.getContentPane().add(contIndustry, new KDLayout.Constraints(367, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEnterpriceProperty.setBounds(new Rectangle(727, 8, 270, 19));
        kDContainer1.getContentPane().add(contEnterpriceProperty, new KDLayout.Constraints(727, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPropertyConsultant.setBounds(new Rectangle(7, 162, 270, 19));
        kDContainer1.getContentPane().add(contPropertyConsultant, new KDLayout.Constraints(7, 162, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRecommended.setBounds(new Rectangle(684, 183, 270, 19));
        kDContainer1.getContentPane().add(contRecommended, new KDLayout.Constraints(684, 183, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contF7Recommended.setBounds(new Rectangle(367, 162, 270, 19));
        kDContainer1.getContentPane().add(contF7Recommended, new KDLayout.Constraints(367, 162, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRecommendDate.setBounds(new Rectangle(727, 162, 270, 19));
        kDContainer1.getContentPane().add(contRecommendDate, new KDLayout.Constraints(727, 162, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contPhone
        contPhone.setBoundEditor(txtPhone);
        //contTel
        contTel.setBoundEditor(txtTel);
        //contOfficeTel
        contOfficeTel.setBoundEditor(txtOfficeTel);
        //contFax
        contFax.setBoundEditor(txtFax);
        //contOtherTel
        contOtherTel.setBoundEditor(txtOtherTel);
        //contEmail
        contEmail.setBoundEditor(txtEmail);
        //contCertificateType
        contCertificateType.setBoundEditor(comboCertificateType);
        //contCertificateNumber
        contCertificateNumber.setBoundEditor(txtCertificateNumber);
        //contDayOfbirth
        contDayOfbirth.setBoundEditor(pkDayOfbirth);
        //contSex
        contSex.setBoundEditor(comboSex);
        //contCorporate
        contCorporate.setBoundEditor(txtCorporate);
        //contIdentity
        contIdentity.setBoundEditor(prmtIdentity);
        //contCorporateTel
        contCorporateTel.setBoundEditor(txtCorporateTel);
        //contCountry
        contCountry.setBoundEditor(prmtCountry);
        //contProvince
        contProvince.setBoundEditor(prmtProvince);
        //contCity
        contCity.setBoundEditor(prmtCity);
        //contRegion
        contRegion.setBoundEditor(prmtRegion);
        //contMailAddress
        contMailAddress.setBoundEditor(txtMailAddress);
        //contPostalCode
        contPostalCode.setBoundEditor(txtPostalCode);
        //contBookedAddress
        contBookedAddress.setBoundEditor(txtBookedAddress);
        //contFirstLinkman
        contFirstLinkman.setBoundEditor(txtFirstLinkman);
        //contIndustry
        contIndustry.setBoundEditor(prmtIndustry);
        //contEnterpriceProperty
        contEnterpriceProperty.setBoundEditor(prmtEnterpriceProperty);
        //contPropertyConsultant
        contPropertyConsultant.setBoundEditor(prmtFirstConsultant);
        //contRecommended
        contRecommended.setBoundEditor(txtRecommended);
        //contF7Recommended
        contF7Recommended.setBoundEditor(f7Recommended);
        //contRecommendDate
        contRecommendDate.setBoundEditor(pkRecommendDate);
        //kDContainer2
        kDContainer2.getContentPane().setLayout(new KDLayout());
        kDContainer2.getContentPane().putClientProperty("OriginalBounds", new Rectangle(2, 295, 1007, 69));        kDScrollPane1.setBounds(new Rectangle(3, 0, 995, 49));
        kDContainer2.getContentPane().add(kDScrollPane1, new KDLayout.Constraints(3, 0, 995, 49, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //panelExtend
        panelExtend.setLayout(new KDLayout());
        panelExtend.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1016, 655));        personContainer.setBounds(new Rectangle(10, 10, 1000, 130));
        panelExtend.add(personContainer, new KDLayout.Constraints(10, 10, 1000, 130, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        enterpriceContainer.setBounds(new Rectangle(10, 145, 999, 130));
        panelExtend.add(enterpriceContainer, new KDLayout.Constraints(10, 145, 999, 130, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //personContainer
        personContainer.getContentPane().setLayout(new KDLayout());
        personContainer.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 10, 1000, 130));        contCustomerOrigin.setBounds(new Rectangle(0, 10, 270, 19));
        personContainer.getContentPane().add(contCustomerOrigin, new KDLayout.Constraints(0, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNativePlace.setBounds(new Rectangle(360, 10, 270, 19));
        personContainer.getContentPane().add(contNativePlace, new KDLayout.Constraints(360, 10, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMaritalStatus.setBounds(new Rectangle(710, 10, 270, 19));
        personContainer.getContentPane().add(contMaritalStatus, new KDLayout.Constraints(710, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHabitationStatus.setBounds(new Rectangle(0, 40, 270, 19));
        personContainer.getContentPane().add(contHabitationStatus, new KDLayout.Constraints(0, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contWorkCompany.setBounds(new Rectangle(360, 40, 270, 19));
        personContainer.getContentPane().add(contWorkCompany, new KDLayout.Constraints(360, 40, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContactPreferences.setBounds(new Rectangle(710, 40, 270, 19));
        personContainer.getContentPane().add(contContactPreferences, new KDLayout.Constraints(710, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMotorcycles.setBounds(new Rectangle(0, 70, 270, 19));
        personContainer.getContentPane().add(contMotorcycles, new KDLayout.Constraints(0, 70, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contWorkArea.setBounds(new Rectangle(360, 70, 270, 19));
        personContainer.getContentPane().add(contWorkArea, new KDLayout.Constraints(360, 70, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHabitationArea.setBounds(new Rectangle(710, 70, 270, 19));
        personContainer.getContentPane().add(contHabitationArea, new KDLayout.Constraints(710, 70, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCustomerOrigin
        contCustomerOrigin.setBoundEditor(prmtCustomerOrigin);
        //contNativePlace
        contNativePlace.setBoundEditor(prmtNativePlace);
        //contMaritalStatus
        contMaritalStatus.setBoundEditor(comboMaritalStatus);
        //contHabitationStatus
        contHabitationStatus.setBoundEditor(prmtHabitationStatus);
        //contWorkCompany
        contWorkCompany.setBoundEditor(txtWorkCompany);
        //contContactPreferences
        contContactPreferences.setBoundEditor(comboContactPreferences);
        //contMotorcycles
        contMotorcycles.setBoundEditor(txtMotorcycles);
        //contWorkArea
        contWorkArea.setBoundEditor(prmtWorkArea);
        //contHabitationArea
        contHabitationArea.setBoundEditor(prmtHabitationArea);
        //enterpriceContainer
        enterpriceContainer.getContentPane().setLayout(new KDLayout());
        enterpriceContainer.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 145, 999, 130));        contEnterpriceSize.setBounds(new Rectangle(0, 10, 270, 19));
        enterpriceContainer.getContentPane().add(contEnterpriceSize, new KDLayout.Constraints(0, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBookedCapital.setBounds(new Rectangle(360, 10, 270, 19));
        enterpriceContainer.getContentPane().add(contBookedCapital, new KDLayout.Constraints(360, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBusinessScope.setBounds(new Rectangle(710, 10, 270, 19));
        enterpriceContainer.getContentPane().add(contBusinessScope, new KDLayout.Constraints(710, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEmployees.setBounds(new Rectangle(0, 40, 270, 19));
        enterpriceContainer.getContentPane().add(contEmployees, new KDLayout.Constraints(0, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTaxRegistrationNoG.setBounds(new Rectangle(360, 40, 270, 19));
        enterpriceContainer.getContentPane().add(contTaxRegistrationNoG, new KDLayout.Constraints(360, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCooperateModel.setBounds(new Rectangle(0, 70, 270, 19));
        enterpriceContainer.getContentPane().add(contCooperateModel, new KDLayout.Constraints(0, 70, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTaxRegistrationNoD.setBounds(new Rectangle(710, 40, 270, 19));
        enterpriceContainer.getContentPane().add(contTaxRegistrationNoD, new KDLayout.Constraints(710, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contEnterpriceSize
        contEnterpriceSize.setBoundEditor(prmtEnterpriceSize);
        //contBookedCapital
        contBookedCapital.setBoundEditor(txtBookedCapital);
        //contBusinessScope
        contBusinessScope.setBoundEditor(txtBusinessScope);
        //contEmployees
        contEmployees.setBoundEditor(txtEmployees);
        //contTaxRegistrationNoG
        contTaxRegistrationNoG.setBoundEditor(txtTaxRegistrationNoG);
        //contCooperateModel
        contCooperateModel.setBoundEditor(prmtCooperateModel);
        //contTaxRegistrationNoD
        contTaxRegistrationNoD.setBoundEditor(txtTaxRegistrationNoD);
        //panelPropertyConsultant
        panelPropertyConsultant.setLayout(new KDLayout());
        panelPropertyConsultant.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1016, 655));        tblPropertyConsultant.setBounds(new Rectangle(10, 10, 996, 639));
        panelPropertyConsultant.add(tblPropertyConsultant, new KDLayout.Constraints(10, 10, 996, 639, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //panelChangeRecord
        panelChangeRecord.setLayout(new KDLayout());
        panelChangeRecord.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1016, 655));        tblCustomerChange.setBounds(new Rectangle(2, 3, 1007, 564));
        panelChangeRecord.add(tblCustomerChange, new KDLayout.Constraints(2, 3, 1007, 564, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateUnit.setBounds(new Rectangle(14, 575, 270, 19));
        panelChangeRecord.add(contCreateUnit, new KDLayout.Constraints(14, 575, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(726, 575, 270, 19));
        panelChangeRecord.add(contCreateTime, new KDLayout.Constraints(726, 575, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateUser.setBounds(new Rectangle(370, 597, 270, 19));
        panelChangeRecord.add(contLastUpdateUser, new KDLayout.Constraints(370, 597, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(726, 597, 270, 19));
        panelChangeRecord.add(contLastUpdateTime, new KDLayout.Constraints(726, 597, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(370, 575, 270, 19));
        panelChangeRecord.add(contCreator, new KDLayout.Constraints(370, 575, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUnit.setBounds(new Rectangle(14, 597, 270, 19));
        panelChangeRecord.add(contLastUpdateUnit, new KDLayout.Constraints(14, 597, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateWay.setBounds(new Rectangle(14, 619, 270, 19));
        panelChangeRecord.add(contCreateWay, new KDLayout.Constraints(14, 619, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProject.setBounds(new Rectangle(370, 619, 270, 19));
        panelChangeRecord.add(contProject, new KDLayout.Constraints(370, 619, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreateUnit
        contCreateUnit.setBoundEditor(prmtCreateUnit);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contLastUpdateUnit
        contLastUpdateUnit.setBoundEditor(prmtLastUpdateUnit);
        //contCreateWay
        contCreateWay.setBoundEditor(comboCreateWay);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //kdpCustomerDetial
        kdpCustomerDetial.setLayout(new KDLayout());
        kdpCustomerDetial.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1016, 655));        kdtCustomerChangeDetial.setBounds(new Rectangle(4, 3, 1004, 647));
        kdpCustomerDetial.add(kdtCustomerChangeDetial, new KDLayout.Constraints(4, 3, 1004, 647, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
        this.toolBar.add(btnQuestion);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("customerType", com.kingdee.eas.fdc.basecrm.CustomerTypeEnum.class, this.comboCustomerType, "selectedItem");
		dataBinder.registerBinding("code", String.class, this.txtCode, "text");
		dataBinder.registerBinding("simpleCode", String.class, this.txtSimpleCode, "text");
		dataBinder.registerBinding("insider", com.kingdee.eas.fdc.insider.InsiderInfo.class, this.prmtInsiderOrg, "data");
		dataBinder.registerBinding("bookedPlace", com.kingdee.eas.basedata.assistant.CountryInfo.class, this.prmtBookedPlace, "data");
		dataBinder.registerBinding("phone", String.class, this.txtPhone, "text");
		dataBinder.registerBinding("tel", String.class, this.txtTel, "text");
		dataBinder.registerBinding("officeTel", String.class, this.txtOfficeTel, "text");
		dataBinder.registerBinding("fax", String.class, this.txtFax, "text");
		dataBinder.registerBinding("otherTel", String.class, this.txtOtherTel, "text");
		dataBinder.registerBinding("email", String.class, this.txtEmail, "text");
		dataBinder.registerBinding("certificateNumber", String.class, this.txtCertificateNumber, "text");
		dataBinder.registerBinding("dayOfbirth", java.util.Date.class, this.pkDayOfbirth, "value");
		dataBinder.registerBinding("sex", com.kingdee.eas.fdc.sellhouse.SexEnum.class, this.comboSex, "selectedItem");
		dataBinder.registerBinding("corporate", String.class, this.txtCorporate, "text");
		dataBinder.registerBinding("identity", com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo.class, this.prmtIdentity, "data");
		dataBinder.registerBinding("corporateTel", String.class, this.txtCorporateTel, "text");
		dataBinder.registerBinding("country", com.kingdee.eas.basedata.assistant.CountryInfo.class, this.prmtCountry, "data");
		dataBinder.registerBinding("province", com.kingdee.eas.basedata.assistant.ProvinceInfo.class, this.prmtProvince, "data");
		dataBinder.registerBinding("city", com.kingdee.eas.basedata.assistant.CityInfo.class, this.prmtCity, "data");
		dataBinder.registerBinding("region", com.kingdee.eas.basedata.assistant.RegionInfo.class, this.prmtRegion, "data");
		dataBinder.registerBinding("mailAddress", String.class, this.txtMailAddress, "text");
		dataBinder.registerBinding("postalCode", String.class, this.txtPostalCode, "text");
		dataBinder.registerBinding("bookedAddress", String.class, this.txtBookedAddress, "text");
		dataBinder.registerBinding("firstLinkman", String.class, this.txtFirstLinkman, "text");
		dataBinder.registerBinding("industry", com.kingdee.eas.basedata.assistant.IndustryInfo.class, this.prmtIndustry, "data");
		dataBinder.registerBinding("enterpriceProperty", com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo.class, this.prmtEnterpriceProperty, "data");
		dataBinder.registerBinding("firstConsultant", com.kingdee.eas.base.permission.UserInfo.class, this.prmtFirstConsultant, "data");
		dataBinder.registerBinding("recommended", String.class, this.txtRecommended, "text");
		dataBinder.registerBinding("f7recommended", com.kingdee.eas.fdc.sellhouse.SHECustomerInfo.class, this.f7Recommended, "data");
		dataBinder.registerBinding("recommendDate", java.util.Date.class, this.pkRecommendDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("customerOrigin", com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo.class, this.prmtCustomerOrigin, "data");
		dataBinder.registerBinding("nativePlace", com.kingdee.eas.basedata.assistant.RegionInfo.class, this.prmtNativePlace, "data");
		dataBinder.registerBinding("maritalStatus", com.kingdee.eas.fdc.basecrm.MaritalStatusEnum.class, this.comboMaritalStatus, "selectedItem");
		dataBinder.registerBinding("habitationStatus", com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo.class, this.prmtHabitationStatus, "data");
		dataBinder.registerBinding("workCompany", String.class, this.txtWorkCompany, "text");
		dataBinder.registerBinding("contactPreferences", com.kingdee.eas.fdc.basecrm.ContactPreferencesEnum.class, this.comboContactPreferences, "selectedItem");
		dataBinder.registerBinding("motorcycles", int.class, this.txtMotorcycles, "text");
		dataBinder.registerBinding("workArea", com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo.class, this.prmtWorkArea, "data");
		dataBinder.registerBinding("habitationArea", com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo.class, this.prmtHabitationArea, "data");
		dataBinder.registerBinding("enterpriceSize", com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo.class, this.prmtEnterpriceSize, "data");
		dataBinder.registerBinding("bookedCapital", String.class, this.txtBookedCapital, "text");
		dataBinder.registerBinding("businessScope", String.class, this.txtBusinessScope, "text");
		dataBinder.registerBinding("employees", int.class, this.txtEmployees, "text");
		dataBinder.registerBinding("taxRegistrationNoG", String.class, this.txtTaxRegistrationNoG, "text");
		dataBinder.registerBinding("cooperateModel", com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo.class, this.prmtCooperateModel, "data");
		dataBinder.registerBinding("taxRegistrationNoD", String.class, this.txtTaxRegistrationNoD, "text");
		dataBinder.registerBinding("createUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtCreateUnit, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("lastUpdateUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtLastUpdateUnit, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtProject, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CustomerRptEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customerType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("code", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleCode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("insider", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedPlace", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("officeTel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("otherTel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("email", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("certificateNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dayOfbirth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("corporate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("identity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("corporateTel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("country", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("province", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("city", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("region", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mailAddress", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("postalCode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedAddress", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("firstLinkman", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("industry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("enterpriceProperty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("firstConsultant", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recommended", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("f7recommended", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recommendDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customerOrigin", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("nativePlace", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("maritalStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("habitationStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("workCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contactPreferences", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("motorcycles", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("workArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("habitationArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("enterpriceSize", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedCapital", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("businessScope", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("employees", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taxRegistrationNoG", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cooperateModel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taxRegistrationNoD", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    		
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
     * output tblNew_stateChanged method
     */
    protected void tblNew_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output tblEntry_stateChanged method
     */
    protected void tblEntry_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output tblSellBill_tableClicked method
     */
    protected void tblSellBill_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblQuestion_tableClicked method
     */
    protected void tblQuestion_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblCommerceChance_tableClicked method
     */
    protected void tblCommerceChance_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblTrack_tableClicked method
     */
    protected void tblTrack_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblLinkman_editStopped method
     */
    protected void tblLinkman_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output txtNumber_actionPerformed method
     */
    protected void txtNumber_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboCustomerType_itemStateChanged method
     */
    protected void comboCustomerType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output txtCertificateNumber_focusLost method
     */
    protected void txtCertificateNumber_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output f7Recommended_dataChanged method
     */
    protected void f7Recommended_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output btnQuestion_actionPerformed method
     */
    protected void btnQuestion_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("customerType"));
        sic.add(new SelectorItemInfo("code"));
        sic.add(new SelectorItemInfo("simpleCode"));
        sic.add(new SelectorItemInfo("insider.*"));
        sic.add(new SelectorItemInfo("bookedPlace.*"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("tel"));
        sic.add(new SelectorItemInfo("officeTel"));
        sic.add(new SelectorItemInfo("fax"));
        sic.add(new SelectorItemInfo("otherTel"));
        sic.add(new SelectorItemInfo("email"));
        sic.add(new SelectorItemInfo("certificateNumber"));
        sic.add(new SelectorItemInfo("dayOfbirth"));
        sic.add(new SelectorItemInfo("sex"));
        sic.add(new SelectorItemInfo("corporate"));
        sic.add(new SelectorItemInfo("identity.*"));
        sic.add(new SelectorItemInfo("corporateTel"));
        sic.add(new SelectorItemInfo("country.*"));
        sic.add(new SelectorItemInfo("province.*"));
        sic.add(new SelectorItemInfo("city.*"));
        sic.add(new SelectorItemInfo("region.*"));
        sic.add(new SelectorItemInfo("mailAddress"));
        sic.add(new SelectorItemInfo("postalCode"));
        sic.add(new SelectorItemInfo("bookedAddress"));
        sic.add(new SelectorItemInfo("firstLinkman"));
        sic.add(new SelectorItemInfo("industry.*"));
        sic.add(new SelectorItemInfo("enterpriceProperty.*"));
        sic.add(new SelectorItemInfo("firstConsultant.*"));
        sic.add(new SelectorItemInfo("recommended"));
        sic.add(new SelectorItemInfo("f7recommended.*"));
        sic.add(new SelectorItemInfo("recommendDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("customerOrigin.*"));
        sic.add(new SelectorItemInfo("nativePlace.*"));
        sic.add(new SelectorItemInfo("maritalStatus"));
        sic.add(new SelectorItemInfo("habitationStatus.*"));
        sic.add(new SelectorItemInfo("workCompany"));
        sic.add(new SelectorItemInfo("contactPreferences"));
        sic.add(new SelectorItemInfo("motorcycles"));
        sic.add(new SelectorItemInfo("workArea.*"));
        sic.add(new SelectorItemInfo("habitationArea.*"));
        sic.add(new SelectorItemInfo("enterpriceSize.*"));
        sic.add(new SelectorItemInfo("bookedCapital"));
        sic.add(new SelectorItemInfo("businessScope"));
        sic.add(new SelectorItemInfo("employees"));
        sic.add(new SelectorItemInfo("taxRegistrationNoG"));
        sic.add(new SelectorItemInfo("cooperateModel.*"));
        sic.add(new SelectorItemInfo("taxRegistrationNoD"));
        sic.add(new SelectorItemInfo("createUnit.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("lastUpdateUnit.*"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        return sic;
    }        
    	

    /**
     * output actionAddLinkman_actionPerformed method
     */
    public void actionAddLinkman_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveLinkman_actionPerformed method
     */
    public void actionRemoveLinkman_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCheckAll_actionPerformed method
     */
    public void actionCheckAll_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionCheckAll(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCheckAll() {
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractCustomerRptEditUI.this, "ActionAddLinkman", "actionAddLinkman_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractCustomerRptEditUI.this, "ActionRemoveLinkman", "actionRemoveLinkman_actionPerformed", e);
        }
    }

    /**
     * output ActionCheckAll class
     */     
    protected class ActionCheckAll extends ItemAction {     
    
        public ActionCheckAll()
        {
            this(null);
        }

        public ActionCheckAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCheckAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCheckAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCheckAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCustomerRptEditUI.this, "ActionCheckAll", "actionCheckAll_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CustomerRptEditUI");
    }




}